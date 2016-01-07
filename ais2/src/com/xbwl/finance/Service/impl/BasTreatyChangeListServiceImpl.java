package com.xbwl.finance.Service.impl;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.Table;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.ralasafe.WebRalasafe;
import org.ralasafe.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.common.utils.AnnotationUtils;
import com.xbwl.common.utils.Rate;
import com.xbwl.common.utils.ReflectionUtils;
import com.xbwl.entity.BasTreatyChangeList;
import com.xbwl.finance.Service.IBasTreatyChangeListService;
import com.xbwl.finance.dao.IBasTreatyChangeListDao;

/**
 * @author CaoZhili time Aug 10, 2011 2:30:48 PM
 * 
 * 协议价修改记录表服务层实现类
 */
@Service("basTreatyChangeListServiceImpl")
@Transactional(rollbackFor = { Exception.class })
public class BasTreatyChangeListServiceImpl extends
		BaseServiceImpl<BasTreatyChangeList, Long> implements
		IBasTreatyChangeListService {

	@Resource(name = "basTreatyChangeListHibernateDaoImpl")
	private IBasTreatyChangeListDao basTreatyChangeListDao;

	private SimpleJdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new SimpleJdbcTemplate(dataSource);
	}

	@Override
	public IBaseDAO<BasTreatyChangeList, Long> getBaseDao() {

		return this.basTreatyChangeListDao;
	}

	public void saveRecord(Object entity, String chinaName, boolean isdelete)
			throws Exception {
		BasTreatyChangeList change = new BasTreatyChangeList();
		String tableName = AnnotationUtils.getValue(
				entity.getClass().getAnnotation(Table.class), "name")
				.toString();
		Object objId = ReflectionUtils.invokeGetterMethod(entity, "id");
		List<Map<String, Object>> list = jdbcTemplate.queryForList(
				"select * from " + tableName + " where id=?", objId);
		User user = WebRalasafe.getCurrentUser(ServletActionContext
				.getRequest());
		change.setChinaName(chinaName);
		change.setUpdateBefore(getUpdateBefore(list, entity, tableName));
		change.setUpdateFater(getUpdateAfter(entity));
		change.setDoNo(Long.valueOf(objId==null?"0":objId.toString()));
		change.setTableName(tableName);
		change.setUpdateName(user.get("name").toString());
		change.setUpdateTime(new Date());
		change.setUpdateContent(getUpdateContent(list, change, entity));

		if (isdelete) {
			change.setUpdateBefore(getUpdateAfter(entity));
			change.setUpdateFater("删除");
			change.setUpdateContent("删除");
		}

		if (change.getUpdateContent().toString().length() > 0) {
			this.basTreatyChangeListDao.save(change);
		}
	}

	/**
	 * 获取修改的内容
	 * 
	 * @param change
	 * @return
	 */
	private String getUpdateContent(List<Map<String, Object>> list,
			BasTreatyChangeList change, Object obj) throws Exception{
		StringBuffer content = new StringBuffer();
		Field[] fields = obj.getClass().getDeclaredFields();
		if (list.size() > 0) {
			Map<String, Object> mp = list.get(0);
			for (int i = 0; i < fields.length; i++) {
				if (fields[i].getAnnotation(Rate.class) != null) {
					Object value = mp
							.get(nameToTableParam(fields[i].getName()));

					Object res = ReflectionUtils.invokeGetterMethod(obj,
							fields[i].getName());
					if(null!=value && Timestamp.class.equals(value.getClass())){
						DateFormat format= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
						value = format.parse(value.toString()); 

						if (!value.equals(res)) {
							content.append(
									AnnotationUtils.getValue(fields[i]
									                                .getAnnotation(Rate.class), "mapping")
									                                + ":").append(format.format(value)).append("--")
									                                .append((res==null?"":format.format(res)));
							if (i != fields.length - 1) {
								content.append(",");
							}
						}
						
					}
					else if(null!=res && res.getClass()==String.class){
						if (null!=value && !value.equals(res)) {
							content.append(
									AnnotationUtils.getValue(fields[i]
									                                .getAnnotation(Rate.class), "mapping")
									                                + ":").append(value).append("--")
									                                .append(res);
							if (i != fields.length - 1) {
								content.append(",");
							}
						}
					}else if(null!=value && null!=res){
						value = ReflectionUtils.convertStringToObject(value + "",
								res.getClass());
						if (!value.equals(res)) {
							content.append(
									AnnotationUtils.getValue(fields[i]
									                                .getAnnotation(Rate.class), "mapping")
									                                + ":").append(value).append("--")
									                                .append(res);
							if (i != fields.length - 1) {
								content.append(",");
							}
						}
					}
				}
			}

		} else {
			content.append("新增");
		}
		return content.toString();
	}

	/**
	 * 获取修改之前的值
	 * 
	 * @param entity
	 * @param tableName
	 * @return
	 */
	private String getUpdateBefore(List<Map<String, Object>> list,
			Object entity, String tableName)throws Exception {

		StringBuffer updateBefore = new StringBuffer();

		Field[] fields = entity.getClass().getDeclaredFields();

		if (list.size() > 0) {
			Map<String, Object> mp = list.get(0);

			for (int i = 0; i < fields.length; i++) {
				if (fields[i].getAnnotation(Rate.class) != null) {
					Object value = mp
							.get(nameToTableParam(fields[i].getName()));
					if (null != value && value.toString().length() > 0) {
						updateBefore.append(
								AnnotationUtils.getValue(fields[i]
										.getAnnotation(Rate.class), "mapping")
										+ ":").append(value);
						if (i != fields.length - 1) {
							updateBefore.append(",");
						}
					}
				}
			}
		} else {
			updateBefore.append("新增");
		}
		return updateBefore.toString();
	}

	private String nameToTableParam(String name) throws Exception{

		StringBuffer param = new StringBuffer();
		char[] ch = name.toCharArray();
		for (int i = 0; i < ch.length; i++) {

			if (Character.isUpperCase(ch[i])) {
				param.append("_" + Character.toLowerCase(ch[i]));
			} else {
				param.append(ch[i]);
			}
		}
		return param.toString();
	}

	/**
	 * 获取修改之后的值
	 * 
	 * @param entity
	 * @return
	 */
	private String getUpdateAfter(Object entity)throws Exception{

		StringBuffer updateAfter = new StringBuffer();

		Field[] fields = entity.getClass().getDeclaredFields();

		for (int i = 0; i < fields.length; i++) {
			if (fields[i].getAnnotation(Rate.class) != null) {
			Object res = ReflectionUtils.invokeGetterMethod(entity, fields[i]
					.getName());
			if (null != res && res.toString().length() > 0) {
					updateAfter.append(
							AnnotationUtils.getValue(fields[i]
									.getAnnotation(Rate.class), "mapping")
									+ ":").append(res);
					if (i != fields.length - 1) {
						updateAfter.append(",");
					}
				}
			}
		}
		return updateAfter.toString();
	}

	public String getSqlService(Map<String, String> map)throws Exception {
		StringBuffer sql=new StringBuffer();
		
		sql.append("select table_name,china_name from bas_treaty_change_list where 1=1");
		sql.append(" group by table_name,china_name");
		
		String LIKES_CHINA_NAME =map.get("LIKES_CHINA_NAME")+"";
		map.put("LIKES_CHINA_NAME", "%"+LIKES_CHINA_NAME+"%");
		
		if(null!=LIKES_CHINA_NAME && !"".equals(LIKES_CHINA_NAME)){
			sql.append(" having  CHINA_NAME  like  :LIKES_CHINA_NAME ");
		}
		
		return sql.toString();
	}
}

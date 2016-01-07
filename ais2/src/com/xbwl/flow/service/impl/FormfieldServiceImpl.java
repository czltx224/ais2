package com.xbwl.flow.service.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.entity.FlowFormfield;
import com.xbwl.entity.FlowForminfo;
import com.xbwl.flow.dao.IFormfieldDao;
import com.xbwl.flow.service.IFormfieldService;

/**
 * 流程管理-表单字段服务层实现类
 *@author LiuHao
 *@time Feb 14, 2012 4:21:30 PM
 */
@Service("formfieldServiceImpl")
@Transactional(rollbackFor = { Exception.class })
public class FormfieldServiceImpl extends BaseServiceImpl<FlowFormfield,Long> implements
		IFormfieldService {
	@Resource(name="formfieldHibernateDaoImpl")
	private IFormfieldDao formfieldDao;
	@Override
	public IBaseDAO getBaseDao() {
		return formfieldDao;
	}
	public int findFieldByFormId(Long formId) throws Exception {
		List<FlowFormfield> list=formfieldDao.findBy("formId", formId);
		return list.size();
	}
	public void saveFormfield(FlowFormfield flowFormfield,String tableName) throws Exception {
		Connection con=null;
		try {
			if(flowFormfield.getId()==null){
				if(flowFormfield==null || flowFormfield.getFormId()==null){
					throw new ServiceException("数据异常，flowFormfield 对象为空，请联系系统管理员");
				}
				//已存在的最大列名
				int count=findFieldByFormId(flowFormfield.getFormId());
				//自动生成表单字段
				flowFormfield.setFieldName("field"+(count+1));
				
				con=SessionFactoryUtils.getDataSource(formfieldDao.getSessionFactory()).getConnection();
				CallableStatement cs = con.prepareCall("{call flow_createtable(?,?,?,?,?,?,?,?)}");
				cs.setString(1, "updateTable");
				cs.setString(2, tableName);
				cs.setString(3, flowFormfield.getFieldName());
				//字段类型(1:单行文本、2:整数、3:浮点型、4:数据字典)
				Long htmlType=flowFormfield.getHtmlType();
				if(htmlType == 1){
					cs.setString(4, "varchar2");
					cs.setLong(5, flowFormfield.getFieldAttr());
					cs.setString(6, "");
					cs.setLong(7, 0);
				}else if(htmlType == 2 || htmlType == 4){
					cs.setString(4, "number");
					cs.setLong(5, 10L);
					cs.setString(6, "");
					cs.setLong(7, 0);
				}else if(htmlType == 3){
					cs.setString(4, "number");
					cs.setLong(5, 10L);
					cs.setString(6, "decimal");
					cs.setLong(7, 2L);
				}
				cs.registerOutParameter(8, Types.VARCHAR);
				cs.executeUpdate();
				String returnMsg=cs.getString(8);
				if(!"true".equals(returnMsg)){
					throw new ServiceException(returnMsg);
				}
			}
			flowFormfield.setStatus(1L);
			formfieldDao.save(flowFormfield);
		} catch (Exception e) {
			throw new ServiceException(e);
		}finally{
			if(con!=null){
				con.close();
			}
		}
	}
	public FlowFormfield getInfoByLabelname(Long formId, String labelName)
	throws Exception {
		List<FlowFormfield> fieldList = formfieldDao.find("from FlowFormfield ff where ff.formId=? and ff.labelName='流程号'", formId);
		if(fieldList.size()!=1){
			throw new ServiceException("表单ID："+formId+"，对应的表单有多个或者没有'流程号'字段，请联系系统管理员！");
		}else{
			return fieldList.get(0);
		}
}
}

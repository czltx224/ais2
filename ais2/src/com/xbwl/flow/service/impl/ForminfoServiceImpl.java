package com.xbwl.flow.service.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.ralasafe.WebRalasafe;
import org.ralasafe.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.entity.FlowFormfield;
import com.xbwl.entity.FlowForminfo;
import com.xbwl.entity.OprChangeDetail;
import com.xbwl.entity.OprChangeMain;
import com.xbwl.entity.SysDepart;
import com.xbwl.flow.dao.IFormfieldDao;
import com.xbwl.flow.dao.IForminfoDao;
import com.xbwl.flow.service.IFormfieldService;
import com.xbwl.flow.service.IForminfoService;

/**
 * 流程管理-表单信息服务层实现类
 *@author LiuHao
 *@time Feb 14, 2012 4:30:08 PM
 */
@Service("forminfoServiceImpl")
@Transactional(rollbackFor = { Exception.class })
public class ForminfoServiceImpl extends BaseServiceImpl<FlowForminfo,Long> implements
		IForminfoService {
	@Resource(name="forminfoHibernateDaoImpl")
	private IForminfoDao forminfoDao;
	@Resource(name="formfieldServiceImpl")
	private IFormfieldService formfieldService;
	@Value("${workFlowbaseServiceImpl.workIdField}")
	private String workIdField;
	@Override
	public IBaseDAO getBaseDao() {
		return forminfoDao;
	}
	public void saveFormInfo(FlowForminfo flowForminfo) throws Exception {
		Connection con=null;
		try {
			//如果是修改或者表的类型为实际表的时候才新增表
			if(flowForminfo.getObjType()==1L && flowForminfo.getId() == null){
				con=SessionFactoryUtils.getDataSource(forminfoDao.getSessionFactory()).getConnection();
				Md5PasswordEncoder md5=new Md5PasswordEncoder();
				//自动生成表名，采用MD5的加密方式，为当前年月日时分秒
				String tableName=md5.encodePassword(new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()), "").substring(0, 10);
				//System.out.println(tableName);
				flowForminfo.setObjTablename("auto_"+tableName);
				
				
				CallableStatement cs = con.prepareCall("{call flow_createtable(?,?,?,?,?,?,?,?)}");
				cs.setString(1, "addTable");
				cs.setString(2, flowForminfo.getObjTablename());
				cs.setString(3, "");
				cs.setString(4, "");
				cs.setLong(5, 0);
				cs.setString(6, "");
				cs.setLong(7, 0);
				cs.registerOutParameter(8, Types.VARCHAR);
				cs.executeUpdate();
				String returnMsg=cs.getString(8);
				if(!"true".equals(returnMsg)){
					throw new ServiceException(returnMsg);
				}
			}
			flowForminfo.setStatus(1L);
			forminfoDao.save(flowForminfo);
		} catch (Exception e) {
			throw new ServiceException(e);
		}finally{
			if(con != null){
				con.close();
			}
		}
		
	}
	public List getFormByPipeId(Long pipeId,String formType) throws Exception {
		//主表
		Long table_type=1L;
		if("detail".equals(formType)){
			//明细表
			table_type=2L;
		}
		StringBuffer sql=new StringBuffer("select finfo.obj_tablename,ffield.field_name,ffield.label_name ");
		sql.append("from flow_forminfo finfo,flow_formfield ffield ");
		sql.append("where finfo.id=ffield.form_id ");
		sql.append("and finfo.id in(");
		sql.append("select fl.oid from flow_pipeinfo fp,flow_forminfo ff,flow_formlink fl ");
		sql.append("where fp.form_id=ff.id and ff.id=fl.pid and fp.id=?");
		sql.append(") and finfo.table_type=? and ffield.status=1 order by ffield.order_fields");
		return forminfoDao.createSQLQuery(sql.toString(), pipeId,table_type).list();
	}
	public List getFormValueByPipeId(Long pipeId, String formType,Long workflowId)throws Exception {
		List rList=new ArrayList();
		List list=this.getFormByPipeId(pipeId, formType);
		//表名去除重复
		Set<String> tableSet=new HashSet<String>();
		for(int i=0;i<list.size();i++){
			Map map=(Map)list.get(i);
			tableSet.add(map.get("OBJ_TABLENAME").toString());
		}
		Iterator<String> iter=tableSet.iterator();
		while(iter.hasNext()){
			String tableName=iter.next();
			List fieldValList=forminfoDao.createSQLQuery("select * from "+tableName+" t where t."+workIdField+"=?",workflowId).list();
			for (int i = 0; i < fieldValList.size(); i++) {
				rList.add(fieldValList.get(i));
			}
		}
		return rList;
	}
	public Map getAutoForm( Long workflowId,Long pipeId) throws Exception {
		List<Map> formlist = this.getMainForm(pipeId);
		if(formlist.size()<1){
			throw new ServiceException("此流程没有主表表单对应，请联系系统管理员！");
		}
		Map formMap=formlist.get(0);
		Long formId=Long.valueOf(formMap.get("ID")+"");
		String tableName=formMap.get("OBJ_TABLENAME").toString();
		FlowFormfield formField = formfieldService.getInfoByLabelname(formId, "流程号");
		String fieldName=formField.getFieldName();
		
		List<Map> list = this.createSQLQuery("select * from "+tableName+" mt where mt."+fieldName+"=?", workflowId).list();
		return list.get(0);
	}
	public List<Map> getMainForm(Long pipeId) throws Exception {
		return this.createSQLQuery("select fin.id,fin.obj_tablename from flow_formlink fl,flow_forminfo fio,flow_forminfo fin,flow_pipeinfo fp where fl.pid  =  fio.id   and   fl.oid  =  fin.id  and fp.form_id=fl.pid and fp.id=? and fin.table_type=1 ", pipeId).list();
	}
}

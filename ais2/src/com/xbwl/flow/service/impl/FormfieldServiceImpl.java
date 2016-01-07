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
 * ���̹���-���ֶη����ʵ����
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
					throw new ServiceException("�����쳣��flowFormfield ����Ϊ�գ�����ϵϵͳ����Ա");
				}
				//�Ѵ��ڵ��������
				int count=findFieldByFormId(flowFormfield.getFormId());
				//�Զ����ɱ��ֶ�
				flowFormfield.setFieldName("field"+(count+1));
				
				con=SessionFactoryUtils.getDataSource(formfieldDao.getSessionFactory()).getConnection();
				CallableStatement cs = con.prepareCall("{call flow_createtable(?,?,?,?,?,?,?,?)}");
				cs.setString(1, "updateTable");
				cs.setString(2, tableName);
				cs.setString(3, flowFormfield.getFieldName());
				//�ֶ�����(1:�����ı���2:������3:�����͡�4:�����ֵ�)
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
		List<FlowFormfield> fieldList = formfieldDao.find("from FlowFormfield ff where ff.formId=? and ff.labelName='���̺�'", formId);
		if(fieldList.size()!=1){
			throw new ServiceException("��ID��"+formId+"����Ӧ�ı��ж������û��'���̺�'�ֶΣ�����ϵϵͳ����Ա��");
		}else{
			return fieldList.get(0);
		}
}
}

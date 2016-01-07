package com.xbwl.flow.service.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.common.utils.ReflectUntil;
import com.xbwl.entity.FlowExport;
import com.xbwl.flow.dao.IFlowExportDao;
import com.xbwl.flow.service.IFlowExportService;

/**
 *@author LiuHao
 *@time Feb 20, 2012 11:04:35 AM
 */
@Service("flowExportServiceImpl")
@Transactional(rollbackFor = { Exception.class })
public class FlowExportServiceImpl extends BaseServiceImpl<FlowExport,Long> implements
		IFlowExportService {
	@Resource(name="flowExportHibernateDaoImpl")
	private IFlowExportDao flowExportDao;
	@Override
	public IBaseDAO getBaseDao() {
		return flowExportDao;
	}
	public void saveExports(List<FlowExport> exports) throws Exception {
		for(FlowExport fe:exports){
			if(fe.getId() != null){
				FlowExport export=flowExportDao.getAndInitEntity(fe.getId());
				Field[] fields=FlowExport.class.getDeclaredFields();
				//反射调用SET方法
				for(int i=0;i<fields.length;i++){
					String fieldName=fields[i].getName();
					Method getMethod=ReflectUntil.getInstance().getGetMethod(fe.getClass(), fieldName);
					Object getValue=getMethod.invoke(fe, new Object[0]);
					if(getValue!=null){
						Method setMethod=ReflectUntil.getInstance().getSetMethod(export.getClass(), fieldName);
						setMethod.invoke(export, getValue);
					}
				}
				flowExportDao.save(export);
			}else{
				fe.setStatus(1L);
				fe.setLinkFrom(1L);
				fe.setLinkTo(1L);
				//设置出口信息的坐标
				for (int i = 1; i <=5; i++) {
					fe.setX(i, -1L);
					fe.setY(i, -1L);
				}
				flowExportDao.save(fe);
			}
		}
	}
	public List<FlowExport> getExportByPipeid(Long pipeId) throws Exception {
		return this.find("from FlowExport fe where fe.pipeId=? and fe.status=1", pipeId);
	}
}

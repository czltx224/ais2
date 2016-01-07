
package com.xbwl.oper.exception.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.ralasafe.WebRalasafe;
import org.ralasafe.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.log.anno.ModuleName;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.orm.Page;
import com.xbwl.common.orm.PropertyFilter;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.common.utils.LogType;
import com.xbwl.common.utils.SmbFileUtils;
import com.xbwl.common.utils.SysFileUtils;
import com.xbwl.entity.OprException;
import com.xbwl.entity.OprStatus;
import com.xbwl.oper.exception.dao.IOprExceptionDao;
import com.xbwl.oper.exception.service.IOprExceptionService;
import com.xbwl.oper.stock.service.IOprStatusService;

/**
 * author shuw
 * time Aug 22, 2011 4:54:21 PM
 */
@Service("oprExceptionServiceImpl")
@Transactional(rollbackFor = { Exception.class })
public class OprExceptionServiceImpl extends BaseServiceImpl<OprException, Long>
		implements IOprExceptionService{

	@Resource(name="OprExceptionHibernateDaoImpl")
	private IOprExceptionDao oprExceptionDao;
	
	@Resource(name = "oprStatusServiceImpl")
	private IOprStatusService oprStatusService;
	
	@Value("${smb.smb_domain}")
	private String  domain;
	
	@Value("${smb.smb_username}")
	private String username;
		
	@Value("${smb.smb_password}")
	private String password;
		
	@Value("${smb.smb_path}")
	private String  path;                // =smb://127.0.0.1/images/
	
	@Value("${exception_file_load}")
	private String exceptionFileLoad;

	@Value("${exception_file_load_do}")
	private String exceptionFileLoadDo;
	
	@Override
	public IBaseDAO<OprException, Long> getBaseDao() {
		return oprExceptionDao;
	}

	@ModuleName(value="异常信息查询",logType=LogType.buss)
	public Page getAllByStationId(Page page, Long stationId, List<Long> stationIds,Long bussDepartId,Long departId,List<PropertyFilter>filters) {
		boolean flag=false;
		for(Long id:stationIds){
			if(stationId.equals(id)){
				flag=true;
				break;
			}
		}
		if(flag){
			filters.add(new PropertyFilter("GEL_status","1"));
		}else{
			filters.add(new PropertyFilter("LIKES_dutyDepartId_OR_createDepartId",departId.toString()));
			filters.add(new PropertyFilter("GEL_status","1"));
		}
		return oprExceptionDao.findPage(page, filters);
	}

	@ModuleName(value="异常信息保存",logType=LogType.buss)
	public void saveExceptionOfNew(OprException oprException, File exceptionAdd,
																			String exceptionAddFileName,File exptionAdd, String exptionAddFileName) throws Exception {
		User user= WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
		Long departId = Long.parseLong(user.get("departId").toString());
		String  departName = user.get("departName").toString();

		if(exceptionAdd!=null){
			 exceptionAddFileName=SysFileUtils.makeFileNameAddDno(oprException.getDno().toString(),exceptionAddFileName,exceptionAdd);
			String saveFileName =exceptionFileLoad+new SimpleDateFormat("yyyyMMdd").format(new Date()) + "/"+exceptionAddFileName;
			String basePath =path+saveFileName ;
			SmbFileUtils.smbFileWrite(new FileInputStream(exceptionAdd), basePath,domain,username,password);
			oprException.setExceptionAdd(saveFileName);
		}
		if(exptionAdd!=null){
			exptionAddFileName=SysFileUtils.makeFileNameAddDno(oprException.getDno().toString(),exptionAddFileName,exptionAdd);
			String saveFileName =exceptionFileLoadDo+new SimpleDateFormat("yyyyMMdd").format(new Date()) + "/"+exptionAddFileName;
			String basePath =path+saveFileName ;
			SmbFileUtils.smbFileWrite(new FileInputStream(exptionAdd), basePath,domain,username,password);
			oprException.setExptionAdd(saveFileName);
		}
		/*
		if(oprException.getStatus()==0){
			List<OprStatus> list =oprStatusDao.find("from OprStatus o where o.dno=? ", oprException.getDno());
			OprStatus entity = list.get(0);
			//entity.setOutStatus(3l);
			//oprStatusDao.save(entity);  
		}*/
		if(oprException.getStatus()!=null){
			if(oprException.getStatus()==2l){
				oprException.setDealTime(new Date());
			}
			
			if(oprException.getStatus()==3l){
				List<OprException>list =oprExceptionDao.find("from OprException oe where oe.dno=? and oe.status != 3 and oe.id !=? ",oprException.getDno(),oprException.getId());
				if(list.size()==0){
					List<OprStatus>liste =oprStatusService.findBy("dno",oprException.getDno());
					liste.get(0).setIsOprException(0l);
					oprStatusService.save(liste.get(0));    //更改运作异常状态
				}
			}
			
			if(oprException.getStatus()==1l){
				oprException.setExceptionTime(new Date());
				oprException.setCreateDepartId(departId.toString());
				oprException.setCreateDepartName(departName);
				
				List<OprStatus>list =oprStatusService.findBy("dno",oprException.getDno());
				list.get(0).setIsOprException(1l);
				oprStatusService.save(list.get(0));    //更改运作异常状态
			}
		}
		
		oprExceptionDao.save(oprException);
	}


	@ModuleName(value="异常信息（审核）保存",logType=LogType.buss)
	public void saveExceptionDo(OprException oprException) throws Exception {
		oprExceptionDao.save(oprException);
	}
	
	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getExceptionFileLoad() {
		return exceptionFileLoad;
	}

	public void setExceptionFileLoad(String exceptionFileLoad) {
		this.exceptionFileLoad = exceptionFileLoad;
	}

	public String getExceptionFileLoadDo() {
		return exceptionFileLoadDo;
	}

	public void setExceptionFileLoadDo(String exceptionFileLoadDo) {
		this.exceptionFileLoadDo = exceptionFileLoadDo;
	}

	public void deleteByIdsOfStatus(List<Long> list) throws Exception {
		 for(Long id: list){
	           OprException oprException= get(id);
	           oprException.setStatus(0l);
	        	save(oprException);
		 }
		
	}

	

}

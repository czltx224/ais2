package com.xbwl.cus.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.common.utils.SmbFileUtils;
import com.xbwl.common.utils.SysFileUtils;
import com.xbwl.cus.dao.ICusComplaintDao;
import com.xbwl.cus.service.ICusComplaintService;
import com.xbwl.entity.CusComplaint;
import com.xbwl.ws.client.IWSCusComplaintService;

import dto.CusComplaintDto;

/**
 * �ͻ�Ͷ�߷����ʵ����
 *@author LiuHao
 *@time Oct 21, 2011 3:07:49 PM
 */
@Service("cusComplaintServiceImpl")
@Transactional(rollbackFor = { Exception.class })
public class CusComplaintServiceImpl extends BaseServiceImpl<CusComplaint,Long> implements
		ICusComplaintService {
	@Resource(name="cusComplaintHibernateDaoImpl")
	private ICusComplaintDao cusComplaintDao;
	
	@Resource(name="wsCusComplaintRemot")
	private IWSCusComplaintService wsCusComplaintService;
	
	@Override
	public IBaseDAO getBaseDao() {
		return cusComplaintDao;
	}
	@Value("${cusComplaintServiceImpl.complaint_file_load}")
	private String complaintFileLoad;
	
	@Value("${smb.smb_domain}")
	private String  domain;
	
	@Value("${smb.smb_username}")
	private String username;
		
	@Value("${smb.smb_password}")
	private String password;
		
	@Value("${smb.smb_path}")
	private String  path;
	
	public void deleteComplaint(Long id) throws Exception {
		CusComplaint cc=cusComplaintDao.getAndInitEntity(id);
		if(cc.getIsAccept()!=null){
			if(cc.getIsAccept()==1){
				throw new ServiceException("��ѡ��İ����Ѳ��ɵ����󣬲���ɾ��");
			}
		}
		cc.setStatus(0L);
		cusComplaintDao.save(cc);
	}
	public void acceptComplaint(String acceptName, File file,
			String fileName,Long comId) throws Exception {
			CusComplaint cc=cusComplaintDao.get(comId);
			cc.setAcceptTime(new Date());
			cc.setAcceptName(acceptName);
			if(file!=null){
				fileName=SysFileUtils.makeFileName(fileName,file);
				String saveFileName =complaintFileLoad+new SimpleDateFormat("yyyyMMdd").format(new Date()) + "/"+fileName;
				String basePath =path+saveFileName;
				SmbFileUtils.smbFileWrite(new FileInputStream(file), basePath,domain,username,password);
				cc.setFilePath(saveFileName);
			}
			cc.setIsAccept(1L);
			cusComplaintDao.save(cc);
	}
	public void dutyComplaint(CusComplaint cusCom, Long comId) throws Exception {
		//REVIEW-ACCEPT �Դ���Ĳ���cusCom��ʹ��ǰ���зǿ��ж� 
		//FIXED LIUH
		CusComplaint cc=cusComplaintDao.get(comId);
		if(cc == null){
			throw new ServiceException("�����쳣��id:"+comId+"��Ӧ�����ݲ����ڣ�");
		}
		cc.setDutyName(cusCom.getDutyName());
		cc.setDutyTime(cusCom.getDutyTime());
		cc.setDealCost(cusCom.getDealCost());
		cc.setDealResult(cusCom.getDealResult());
		cusComplaintDao.save(cc);
	}
	@Override
	public void save(CusComplaint entity) {
		if(null!=entity.getDealResult() && entity.getDealResult().equals("2") && "��Ӫ".equals(entity.getAppellateName()) && null!=entity.getFkPsComplaint()){
			CusComplaintDto dto = new CusComplaintDto();
			BeanUtils.copyProperties(entity, dto);
			this.wsCusComplaintService.updateCusComplaintServiceRemote(dto);
		}
		super.save(entity);
	}
	
	
}

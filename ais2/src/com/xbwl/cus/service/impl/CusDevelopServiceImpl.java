package com.xbwl.cus.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.common.utils.SmbFileUtils;
import com.xbwl.common.utils.SysFileUtils;
import com.xbwl.cus.dao.ICusDevelopDao;
import com.xbwl.cus.dao.ICusRecordDao;
import com.xbwl.cus.service.ICusDevelopService;
import com.xbwl.entity.CusDemand;
import com.xbwl.entity.CusDevelop;
import com.xbwl.entity.CusRecord;

/**
 * �ͻ��������̷����ʵ����
 *@author LiuHao
 *@time Oct 13, 2011 3:41:36 PM
 */
@Service("cusDevelopServiceImpl")
@Transactional(rollbackFor = { Exception.class })
public class CusDevelopServiceImpl extends BaseServiceImpl<CusDevelop,Long> implements
		ICusDevelopService {
	@Resource(name="cusDevelopHibernateDaoImpl")
	private ICusDevelopDao cusDevelopDao;
	@Resource(name="cusRecordHibernateDaoImpl")
	private ICusRecordDao cusRecordDao;
	@Value("${cusDeveploServiceImpl.develop_file_load}")
	private String demandFileLoad;
	
	@Value("${smb.smb_domain}")
	private String  domain;
	
	@Value("${smb.smb_username}")
	private String username;
		
	@Value("${smb.smb_password}")
	private String password;
		
	@Value("${smb.smb_path}")
	private String  path; 
	@Override
	public IBaseDAO getBaseDao() {
		return cusDevelopDao;
	}
	//REVIEW-ACCEPT ����ɾ���Ƿ���������
	//FIXED �����Ƿ���Service ����Ҫ����
	public void delCusDevelop(List pks) throws Exception {
		//REVIEW ʹ�ô������ǰ���зǿպ�size�ж�
		for (int i = 0; i < pks.size(); i++) {
			Long id=Long.valueOf(pks.get(i).toString());
			CusDevelop cd=cusDevelopDao.get(id);
			//REVIEW-ACCEPT �Բ�ѯ�����Ķ�����ʹ��ǰ��ý��зǿ��ж�
			//FIXED LIUH
			if(cd == null){
				throw new ServiceException("�����쳣��ID��"+id+"��Ӧ�Ŀ������̲�����");
			}else{
				if(cd.getIsAudit()==1){
					throw new ServiceException("��ѡ��İ����������Ļ����������");
				}
				cd.setStatus(0L);
				cusDevelopDao.save(cd);
			}
		}
	}
	public void saveCusDevelop(CusDevelop cusDevelop, File file,
			String fileName) throws Exception {
		//REVIEW-ACCEPT ʹ�ô������ǰ���зǿ��ж�
		//FIXED LIUH
		if(cusDevelop == null){
			throw new ServiceException("�����쳣����Ҫ����Ķ���Ϊ����");
		}
		if(file!=null){
			fileName=SysFileUtils.makeFileName(fileName,file);
			String saveFileName =demandFileLoad+new SimpleDateFormat("yyyyMMdd").format(new Date()) + "/"+fileName;
			String basePath =path+saveFileName;
			SmbFileUtils.smbFileWrite(new FileInputStream(file), basePath,domain,username,password);
			cusDevelop.setFilePath(saveFileName);
		}
		CusRecord cus=cusRecordDao.get(cusDevelop.getCusRecordId());
		//REVIEW-ACCEPT ʹ�ò�ѯ����ǰӦ�����ǿ��ж�
		//FIXED LIUH
		if(cus == null){
			throw new ServiceException("�����쳣���ͻ����:"+cusDevelop.getCusRecordId()+"��Ӧ�Ŀͻ���Ϣ������");
		}else{
			cus.setLastCommunicate(new Date());
			cusDevelop.setDevelopTime(new Date());
			cusDevelop.setStatus(1L);
			//cusDevelop.setIsAudit(0L);
			cusDevelopDao.save(cusDevelop);
			cusRecordDao.save(cus);
		}
	}

}

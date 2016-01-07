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
 * 客户开发过程服务层实现类
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
	//REVIEW-ACCEPT 批量删除是否考虑事务性
	//FIXED 事物是放在Service 不需要考虑
	public void delCusDevelop(List pks) throws Exception {
		//REVIEW 使用传入参数前进行非空和size判断
		for (int i = 0; i < pks.size(); i++) {
			Long id=Long.valueOf(pks.get(i).toString());
			CusDevelop cd=cusDevelopDao.get(id);
			//REVIEW-ACCEPT 对查询回来的对象在使用前最好进行非空判断
			//FIXED LIUH
			if(cd == null){
				throw new ServiceException("数据异常，ID："+id+"对应的开发过程不存在");
			}else{
				if(cd.getIsAudit()==1){
					throw new ServiceException("您选择的包含已评估的活动，不能作废");
				}
				cd.setStatus(0L);
				cusDevelopDao.save(cd);
			}
		}
	}
	public void saveCusDevelop(CusDevelop cusDevelop, File file,
			String fileName) throws Exception {
		//REVIEW-ACCEPT 使用传入参数前进行非空判断
		//FIXED LIUH
		if(cusDevelop == null){
			throw new ServiceException("数据异常，您要保存的对象为空了");
		}
		if(file!=null){
			fileName=SysFileUtils.makeFileName(fileName,file);
			String saveFileName =demandFileLoad+new SimpleDateFormat("yyyyMMdd").format(new Date()) + "/"+fileName;
			String basePath =path+saveFileName;
			SmbFileUtils.smbFileWrite(new FileInputStream(file), basePath,domain,username,password);
			cusDevelop.setFilePath(saveFileName);
		}
		CusRecord cus=cusRecordDao.get(cusDevelop.getCusRecordId());
		//REVIEW-ACCEPT 使用查询对象前应当做非空判断
		//FIXED LIUH
		if(cus == null){
			throw new ServiceException("数据异常，客户编号:"+cusDevelop.getCusRecordId()+"对应的客户信息不存在");
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

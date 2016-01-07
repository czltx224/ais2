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
import com.xbwl.cus.dao.ICusCurrivalDao;
import com.xbwl.cus.dao.ICusRecordDao;
import com.xbwl.cus.service.ICusCurrivalService;
import com.xbwl.entity.CusCurrival;
import com.xbwl.entity.CusRecord;

/**
 * 竞争对手服务层实现类
 *@author LiuHao
 *@time Oct 28, 2011 9:51:51 AM
 */
@Service("cusCurrivalServiceImpl")
@Transactional(rollbackFor = { Exception.class })
public class CusCurrivalServiceImpl extends BaseServiceImpl<CusCurrival,Long> implements
		ICusCurrivalService {
	@Resource(name="cusCurrivalHibernateDaoImpl")
	private ICusCurrivalDao cusCurrivalDao;
	@Value("${cusCurrivalServiceImpl.currival_file_load}")
	private String currivalFileLoad;
	
	@Resource(name="cusRecordHibernateDaoImpl")
	private ICusRecordDao cusRecordDao;
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
		return cusCurrivalDao;
	}
	public void saveCurrival(CusCurrival cusCurrival, File file, String fileName)
			throws Exception {
		//REVIEW-ACCEPT 对传入的对象在使用前做非空判断
		//FIXED LIUH
		if(cusCurrival == null){
			throw new ServiceException("数据异常，需要保存的对象为空！");
		}
		if(file!=null){			
			fileName=SysFileUtils.makeFileName(fileName,file);
			String saveFileName =currivalFileLoad+new SimpleDateFormat("yyyyMMdd").format(new Date()) + "/"+fileName;
			String basePath =path+saveFileName;
			SmbFileUtils.smbFileWrite(new FileInputStream(file), basePath,domain,username,password);
			cusCurrival.setCurProjiect(saveFileName);
		}		
		List<CusRecord> list=cusRecordDao.findBy("cusId", cusCurrival.getCusId());
		CusRecord c=new CusRecord();
		//REVIEW-ACCEPT list有可能为空吗
		//FIXED LIUH
		if(list != null && list.size()>0){
			c = list.get(0);
		}
		cusCurrival.setStatus(1L);
		cusCurrival.setCusRecordId(c.getId());
		cusCurrivalDao.save(cusCurrival);
	}
	public void delCurrival(Long id) throws Exception {
		CusCurrival cc=cusCurrivalDao.get(id);
		cc.setStatus(0L);
		cusCurrivalDao.save(cc);
	}
}

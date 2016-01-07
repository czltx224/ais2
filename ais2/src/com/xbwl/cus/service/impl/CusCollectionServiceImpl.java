package com.xbwl.cus.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.common.utils.SmbFileUtils;
import com.xbwl.common.utils.SysFileUtils;
import com.xbwl.cus.dao.ICusCollectionDao;
import com.xbwl.cus.service.ICusCollectionService;
import com.xbwl.entity.CusCollection;

/**
 * 客户催款服务层实现类
 *@author LiuHao
 *@time Nov 3, 2011 4:08:33 PM
 */
@Service("cusCollectionServiceImpl")
@Transactional(rollbackFor = { Exception.class })
public class CusCollectionServiceImpl extends BaseServiceImpl<CusCollection,Long> implements
		ICusCollectionService {
	@Resource(name="cusCollectionHibernateDaoImpl")
	private ICusCollectionDao cusCollectionDao;
	@Value("${cusCollectionServiceImpl.collection_file_load}")
	private String collectionFileLoad;
	
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
		return cusCollectionDao;
	}
	public void saveCollection(CusCollection cusCollection, File file,
			String fileName) throws Exception {
		if(file!=null){
			fileName=SysFileUtils.makeFileName(fileName,file);
			String saveFileName =collectionFileLoad+new SimpleDateFormat("yyyyMMdd").format(new Date()) + "/"+fileName;
			String basePath =path+saveFileName;
			SmbFileUtils.smbFileWrite(new FileInputStream(file), basePath,domain,username,password);
			cusCollection.setCollectionFile(saveFileName);
		}
		cusCollectionDao.save(cusCollection);
	}
}

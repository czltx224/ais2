package com.xbwl.sys.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.common.utils.SmbFileUtils;
import com.xbwl.common.utils.SysFileUtils;
import com.xbwl.entity.SysProblem;
import com.xbwl.sys.dao.ISysProBlemDao;
import com.xbwl.sys.service.ISysProblemService;

/**
 * author shuw
 * time Feb 11, 2012 2:42:22 PM
 */
@Service("sysProblemServiceImpl")
public class SysProblemServiceImpl extends BaseServiceImpl<SysProblem, Long>
		implements ISysProblemService {

	@Resource(name="sysProblemHibernateDaoImpl")
	private ISysProBlemDao sysProBlemDao;
	
	public IBaseDAO<SysProblem, Long> getBaseDao() {
		return sysProBlemDao;
	}
	
	@Value("${smb.smb_domain}")
	private String  domain;
	
	@Value("${smb.smb_username}")
	private String username;
		
	@Value("${smb.smb_password}")
	private String password;
		
	@Value("${smb.smb_path}")
	private String  path;                // =smb://127.0.0.1/images/
	
	@Value("${problem_photo}")
	private String problemFile;

	//问题新增和图片保存
	public void savePhoto(SysProblem sysProblem, String fileName, File file)
			throws Exception {
		if(file!=null){
			fileName=SysFileUtils.makeFileNameAddDno(fileName,file);
			String saveFileName =problemFile+new SimpleDateFormat("yyyyMMdd").format(new Date()) + "/"+fileName;
			String basePath =path+saveFileName ;
			SmbFileUtils.smbFileWrite(new FileInputStream(file), basePath,domain,username,password);
			sysProblem.setProblemPhotoAddr(saveFileName);
		}
		save(sysProblem);
	}

	public ISysProBlemDao getSysProBlemDao() {
		return sysProBlemDao;
	}

	public void setSysProBlemDao(ISysProBlemDao sysProBlemDao) {
		this.sysProBlemDao = sysProBlemDao;
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

	public String getProblemFile() {
		return problemFile;
	}

	public void setProblemFile(String problemFile) {
		this.problemFile = problemFile;
	}

}

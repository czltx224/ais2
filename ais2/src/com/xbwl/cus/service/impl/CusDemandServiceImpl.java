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
import com.xbwl.cus.dao.ICusDemandDao;
import com.xbwl.cus.dao.ICusLinkManDao;
import com.xbwl.cus.dao.ICusRecordDao;
import com.xbwl.cus.service.ICusDemandService;
import com.xbwl.entity.CusDemand;
import com.xbwl.entity.CusLinkman;
import com.xbwl.entity.CusRecord;

/**
 * 客户需求服务层实现类
 *@author LiuHao
 *@time Oct 9, 2011 9:28:36 AM
 */
@Service("cusDemandServiceImpl")
@Transactional(rollbackFor = { Exception.class })
public class CusDemandServiceImpl extends BaseServiceImpl<CusDemand, Long>
		implements ICusDemandService {
	@Resource(name = "cusDemandHibernateDaoImpl")
	private ICusDemandDao cusDemandDao;
	@Resource(name = "cusRecordHibernateDaoImpl")
	private ICusRecordDao cusRecordDao;

	@Resource(name = "cusLinkManHibernateDaoImpl")
	private ICusLinkManDao cusLinkManDao;
	@Value("${cusDemandServiceImpl.demand_file_load}")
	private String demandFileLoad;

	@Value("${smb.smb_domain}")
	private String domain;

	@Value("${smb.smb_username}")
	private String username;

	@Value("${smb.smb_password}")
	private String password;

	@Value("${smb.smb_path}")
	private String path;

	@Override
	public IBaseDAO getBaseDao() {
		return cusDemandDao;
	}

	public boolean saveCusDemand(CusDemand cusDemand, File file, String fileName)
			throws Exception {
		if (file != null) {
			fileName = SysFileUtils.makeFileName(fileName, file);
			String saveFileName = demandFileLoad
					+ new SimpleDateFormat("yyyyMMdd").format(new Date()) + "/"
					+ fileName;
			String basePath = path + saveFileName;
			SmbFileUtils.smbFileWrite(new FileInputStream(file), basePath,
					domain, username, password);
			cusDemand.setFilePath(saveFileName);
		}
		// 该客户有联系人
		List<CusLinkman> list = cusLinkManDao.findBy("cusRecordId", cusDemand
				.getCusRecordId());
		// REVIEW-ACCEPT list有可能为空吗？
		//FIXED LIUH
		if (list != null && list.size() > 0) {
			CusRecord cus = cusRecordDao.get(cusDemand.getCusRecordId());
			// REVIEW-ACCEPT cus有可能为空，
			//FIXED LIUH
			if(cus == null){
				throw new ServiceException("数据异常，客户ID："+cusDemand.getCusRecordId()+"对应的数据为空");
			}
			if("潜在客户".equals(cus.getDevelopLevel())){
				cus.setDevelopLevel("目标客户");
			}
			cusRecordDao.save(cus);
		}
		cusDemand.setStatus(1L);
		cusDemandDao.save(cusDemand);
		return true;
	}

	// REVIEW 批量删除是否考虑事务性？
	//FIXED 事务在service层，不需要考虑
	public void delCusDemand(List pks) throws Exception {
		// REVIEW 对pks做非空和size判断
		//FIXED LIUH 已经在控制层做判断，此处不需要再次判断
		for (int i = 0; i < pks.size(); i++) {
			// REVIEW pks.get(i)有可能为空的的情况，将导致循环中断并导致删除部分成功
			// FIXED LIUH
			if(pks.get(i) == null){
				throw new ServiceException("数据异常，您选择要删除的数据的ID有为空的，请尝试单条删除");
			}
			Long id = Long.valueOf(pks.get(i).toString());
			CusDemand cd = cusDemandDao.get(id);
			if (cd.getIsAccept() == 1) {
				throw new ServiceException("您选择的包含已采纳的需求，不能作废");
			}
			cd.setStatus(0L);
			cusDemandDao.save(cd);
		}
	}
}

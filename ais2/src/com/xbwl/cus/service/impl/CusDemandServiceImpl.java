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
 * �ͻ���������ʵ����
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
		// �ÿͻ�����ϵ��
		List<CusLinkman> list = cusLinkManDao.findBy("cusRecordId", cusDemand
				.getCusRecordId());
		// REVIEW-ACCEPT list�п���Ϊ����
		//FIXED LIUH
		if (list != null && list.size() > 0) {
			CusRecord cus = cusRecordDao.get(cusDemand.getCusRecordId());
			// REVIEW-ACCEPT cus�п���Ϊ�գ�
			//FIXED LIUH
			if(cus == null){
				throw new ServiceException("�����쳣���ͻ�ID��"+cusDemand.getCusRecordId()+"��Ӧ������Ϊ��");
			}
			if("Ǳ�ڿͻ�".equals(cus.getDevelopLevel())){
				cus.setDevelopLevel("Ŀ��ͻ�");
			}
			cusRecordDao.save(cus);
		}
		cusDemand.setStatus(1L);
		cusDemandDao.save(cusDemand);
		return true;
	}

	// REVIEW ����ɾ���Ƿ��������ԣ�
	//FIXED ������service�㣬����Ҫ����
	public void delCusDemand(List pks) throws Exception {
		// REVIEW ��pks���ǿպ�size�ж�
		//FIXED LIUH �Ѿ��ڿ��Ʋ����жϣ��˴�����Ҫ�ٴ��ж�
		for (int i = 0; i < pks.size(); i++) {
			// REVIEW pks.get(i)�п���Ϊ�յĵ������������ѭ���жϲ�����ɾ�����ֳɹ�
			// FIXED LIUH
			if(pks.get(i) == null){
				throw new ServiceException("�����쳣����ѡ��Ҫɾ�������ݵ�ID��Ϊ�յģ��볢�Ե���ɾ��");
			}
			Long id = Long.valueOf(pks.get(i).toString());
			CusDemand cd = cusDemandDao.get(id);
			if (cd.getIsAccept() == 1) {
				throw new ServiceException("��ѡ��İ����Ѳ��ɵ����󣬲�������");
			}
			cd.setStatus(0L);
			cusDemandDao.save(cd);
		}
	}
}

package com.xbwl.cus.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.cus.dao.ICusLinkManDao;
import com.xbwl.cus.service.ICusLinkManService;
import com.xbwl.entity.CusDemand;
import com.xbwl.entity.CusLinkman;

/**
 * ��ϵ����Ϣ�����ʵ����
 *@author LiuHao
 *@time Oct 8, 2011 5:59:26 PM
 */
@Service("cusLinkManServiceImpl")
@Transactional(rollbackFor = { Exception.class })
public class CusLinkManServiceImpl extends BaseServiceImpl<CusLinkman,Long> implements
		ICusLinkManService {
	@Resource(name="cusLinkManHibernateDaoImpl")
	private ICusLinkManDao cusLinkManDao;
	
	@Override
	public IBaseDAO getBaseDao() {
		return cusLinkManDao;
	}

	public void delCusLinkman(List pks) throws Exception {
		for (int i = 0; i < pks.size(); i++) {
			Long id=Long.valueOf(pks.get(i).toString());
			CusLinkman cl=cusLinkManDao.get(id);
			if(cl==null){
				throw new ServiceException("�����쳣�����Ϊ��"+id+"����ϵ��Ϊ�գ�");
			}
			cl.setStatus(0L);
			cusLinkManDao.save(cl);
		}
	}
}

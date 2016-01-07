package com.xbwl.cus.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.cus.dao.ICusSaleChanceDao;
import com.xbwl.cus.service.ICusSaleChanceService;
import com.xbwl.entity.CusComplaint;
import com.xbwl.entity.CusSaleChance;

/**
 * �ͻ����ۻ�������ʵ����
 *@author LiuHao
 *@time Oct 22, 2011 9:32:28 AM
 */
@Service("cusSaleChanceServiceImpl")
@Transactional(rollbackFor = { Exception.class })
public class CusSaleChanceServiceImpl extends BaseServiceImpl<CusSaleChance,Long> implements
		ICusSaleChanceService {
	@Resource(name="cusSaleChanceHibernateDaoImpl")
	private ICusSaleChanceDao cusSaleChanceDao;
	@Override
	public IBaseDAO getBaseDao() {
		return cusSaleChanceDao;
	}
	public void delSaleChance(Long id) throws Exception {
		CusSaleChance cc=cusSaleChanceDao.getAndInitEntity(id);
		//REVIEW-ACCEPT ʹ��ǰ���зǿ��ж�
		//FIXED LIUH 
		if(cc == null){
			throw new ServiceException("�����쳣��ID��"+id+"��Ӧ�����ۻ���Ϊ��");
		}
		cc.setStatus(0L);
		cusSaleChanceDao.save(cc);
	}
}

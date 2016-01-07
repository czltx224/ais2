package com.xbwl.finance.Service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.ralasafe.WebRalasafe;
import org.ralasafe.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.entity.FiInvoice;
import com.xbwl.finance.Service.IFiInvoiceService;
import com.xbwl.finance.dao.IFiInvoiceDao;

@Service("fiInvoiceServiceImpl")
@Transactional(rollbackFor={Exception.class})
public class FiInvoiceServiceImpl extends BaseServiceImpl<FiInvoice, Long>
		implements IFiInvoiceService {

	@Resource(name = "fiInvoiceHibernateDaoImpl")
	private IFiInvoiceDao fiInvoiceDao;

	@Override
	public IBaseDAO getBaseDao() {
		return fiInvoiceDao;
	}
	
	public String invalid(String ids) throws Exception{
		String[] idssp=ids.split(",");
		for(int i=0;i<idssp.length;i++){
			FiInvoice fiInvoice=this.get(Long.valueOf(idssp[i]));
			if(fiInvoice.getStatus()==0) throw new ServiceException("����ʧ�ܣ����ݺ�["+fiInvoice.getId()+"]�Ѿ����ϣ�");
			if(fiInvoice.getReviewStatus()>1) throw new ServiceException("����ʧ�ܣ����ݺ�["+fiInvoice.getId()+"]����ˣ�");
			if(fiInvoice.getStatus()>1) throw new ServiceException("����ʧ�ܣ����ݺ�["+fiInvoice.getId()+"]�Ѽĳ���");
			fiInvoice.setStatus(0L);
			this.fiInvoiceDao.save(fiInvoice);
		}
		return "���ϳɹ�!";
	}
	
	public String review(String ids) throws Exception{
		String[] idssp=ids.split(",");
		User user=WebRalasafe.getCurrentUser(Struts2Utils.getRequest());
		for(int i=0;i<idssp.length;i++){
			FiInvoice fiInvoice=this.get(Long.valueOf(idssp[i]));
			if(fiInvoice.getStatus()==0) throw new ServiceException("���ʧ�ܣ����ݺ�["+fiInvoice.getId()+"]�Ѿ����ϣ�");
			if(fiInvoice.getReviewStatus()>1) throw new ServiceException("���ʧ�ܣ����ݺ�["+fiInvoice.getId()+"]����ˣ�");
			if(fiInvoice.getStatus()>1) throw new ServiceException("���ʧ�ܣ����ݺ�["+fiInvoice.getId()+"]�Ѽĳ���");
			fiInvoice.setReviewStatus(2L);
			fiInvoice.setReviewTime(new Date());
			fiInvoice.setReviewUser(user.get("name").toString());
			this.fiInvoiceDao.save(fiInvoice);
		}
		return "��˳ɹ�!";
	}
}

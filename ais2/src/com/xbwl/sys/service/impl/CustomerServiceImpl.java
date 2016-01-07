package com.xbwl.sys.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdcn.bpaf.common.exception.ServiceException;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.orm.Page;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.entity.Customer;
import com.xbwl.entity.FiArrearset;
import com.xbwl.entity.OprFaxIn;
import com.xbwl.oper.fax.dao.IOprFaxInDao;
import com.xbwl.sys.dao.ICustomerDao;
import com.xbwl.sys.service.ICustomerService;

@Service("customerServiceImpl")
@Transactional(rollbackFor={Exception.class})
public class CustomerServiceImpl extends BaseServiceImpl<Customer,Long> implements
		ICustomerService {
	
	@Resource(name="customerHibernateDaoImpl")
	private ICustomerDao customerDao;
	
	@Resource(name="oprFaxInHibernateDaoImpl")
	private IOprFaxInDao oprFaxInDao;
	
	@Override
	public IBaseDAO<Customer, Long> getBaseDao() {
		return customerDao;
	}
	public Page<Customer> gettraCustomer(Page page) throws Exception {
		return this.findPage(page, "from Customer c where c.custprop=? or c.custprop=?", "��ת","�ⷢ");
	}
	
	public Page<Customer> gettraCustomer(Page page,String cusName) throws Exception {
		
		StringBuffer sb = new StringBuffer("from Customer c where 1=1 and (c.custprop=? or c.custprop=?)");
		if(null!=cusName && !"".equals(cusName)){
			sb.append(" and cusName like ").append("'%").append(cusName).append("%'");
		}
		
		return this.findPage(page, sb.toString(), "��ת","�ⷢ");
	}
	
	@Override
	public void delete(Customer entity) {
		List<OprFaxIn> list = this.oprFaxInDao.findBy("cusId", entity.getId());
		if(null!=list && list.size()>0){
			throw new ServiceException(entity.getCusName()+"�Ѿ�¼��������ɾ����");
		}
		super.delete(entity);
	}
	
	@Override
	public void deleteByIds(List<Long> ids) {
		
		for(Long id : ids){
			List<OprFaxIn> list = this.oprFaxInDao.findBy("cusId", id);
			if(null!=list && list.size()>0){
				throw new ServiceException(list.get(0).getCpName()+"�Ѿ�¼��������ɾ����");
			}
		}
		super.deleteByIds(ids);
	}
	
	public void save(Customer entity) {
		if(entity!=null&&entity.getId()==null){
			List<FiArrearset> list=find("from Customer c where c.cusName=?  ", entity.getCusName());
			if(list.size()>0){
				throw new ServiceException("�˿��������Ѵ��ڣ���������¼��");
			}
		}
		super.save(entity);
	}
	public void stopCustomer(Customer customer) throws Exception {
    	customer.setStatus(0l);//�޸Ŀ���״̬Ϊ0����0����ʾͣ�ã�1��������
    	this.customerDao.save(customer);
	}
	public void startCustomer(Customer customer) throws Exception {
		customer.setStatus(1l);//�޸Ŀ���״̬Ϊ0����0����ʾͣ�ã�1��������
    	this.customerDao.save(customer);
	}
}

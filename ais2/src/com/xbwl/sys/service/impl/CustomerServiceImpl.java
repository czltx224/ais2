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
		return this.findPage(page, "from Customer c where c.custprop=? or c.custprop=?", "中转","外发");
	}
	
	public Page<Customer> gettraCustomer(Page page,String cusName) throws Exception {
		
		StringBuffer sb = new StringBuffer("from Customer c where 1=1 and (c.custprop=? or c.custprop=?)");
		if(null!=cusName && !"".equals(cusName)){
			sb.append(" and cusName like ").append("'%").append(cusName).append("%'");
		}
		
		return this.findPage(page, sb.toString(), "中转","外发");
	}
	
	@Override
	public void delete(Customer entity) {
		List<OprFaxIn> list = this.oprFaxInDao.findBy("cusId", entity.getId());
		if(null!=list && list.size()>0){
			throw new ServiceException(entity.getCusName()+"已经录单，不能删除！");
		}
		super.delete(entity);
	}
	
	@Override
	public void deleteByIds(List<Long> ids) {
		
		for(Long id : ids){
			List<OprFaxIn> list = this.oprFaxInDao.findBy("cusId", id);
			if(null!=list && list.size()>0){
				throw new ServiceException(list.get(0).getCpName()+"已经录单，不能删除！");
			}
		}
		super.deleteByIds(ids);
	}
	
	public void save(Customer entity) {
		if(entity!=null&&entity.getId()==null){
			List<FiArrearset> list=find("from Customer c where c.cusName=?  ", entity.getCusName());
			if(list.size()>0){
				throw new ServiceException("此客商名称已存在，不允许多次录入");
			}
		}
		super.save(entity);
	}
	public void stopCustomer(Customer customer) throws Exception {
    	customer.setStatus(0l);//修改客商状态为0，（0：表示停用，1：正常）
    	this.customerDao.save(customer);
	}
	public void startCustomer(Customer customer) throws Exception {
		customer.setStatus(1l);//修改客商状态为0，（0：表示停用，1：正常）
    	this.customerDao.save(customer);
	}
}

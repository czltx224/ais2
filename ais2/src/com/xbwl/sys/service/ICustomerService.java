package com.xbwl.sys.service;

import com.xbwl.common.orm.Page;
import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.Customer;


public interface ICustomerService extends IBaseService<Customer,Long> {
	/**
	 * 查询中转外发供应商
	 * @author LiuHao
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public Page<Customer> gettraCustomer(Page page)throws Exception;
	
	
	/**查询中转外发供应商
	 * @param page
	 * @param cusName 按照客商名称查询
	 * @return Page 分页对象
	 * @throws Exception
	 */
	public Page<Customer> gettraCustomer(Page page,String cusName)throws Exception;


	/**
	 * 停用客商
	 * @param customer
	 * @throws Exception
	 */
	public void stopCustomer(Customer customer)throws Exception;


	/**
	 * 启用客商
	 * @param customer
	 */
	public void startCustomer(Customer customer)throws Exception;
}
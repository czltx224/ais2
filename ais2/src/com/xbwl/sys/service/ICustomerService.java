package com.xbwl.sys.service;

import com.xbwl.common.orm.Page;
import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.Customer;


public interface ICustomerService extends IBaseService<Customer,Long> {
	/**
	 * ��ѯ��ת�ⷢ��Ӧ��
	 * @author LiuHao
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public Page<Customer> gettraCustomer(Page page)throws Exception;
	
	
	/**��ѯ��ת�ⷢ��Ӧ��
	 * @param page
	 * @param cusName ���տ������Ʋ�ѯ
	 * @return Page ��ҳ����
	 * @throws Exception
	 */
	public Page<Customer> gettraCustomer(Page page,String cusName)throws Exception;


	/**
	 * ͣ�ÿ���
	 * @param customer
	 * @throws Exception
	 */
	public void stopCustomer(Customer customer)throws Exception;


	/**
	 * ���ÿ���
	 * @param customer
	 */
	public void startCustomer(Customer customer)throws Exception;
}
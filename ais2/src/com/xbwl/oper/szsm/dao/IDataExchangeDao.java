package com.xbwl.oper.szsm.dao;

import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.entity.DigitalChinaExchange;

/**
 * ��������Խ����ݷ��ʲ�ӿ�
 * @author czl
 * @date 2012-06-20
 */
public interface IDataExchangeDao extends IBaseDAO<DigitalChinaExchange, Long>{

	/**
	 * �������ݶԽӲ�ѯSQL��ȡ
	 * @return
	 * @throws Exception
	 */
	public String searchTmTransferD()throws Exception;

	/**
	 * ǩ�����ݶԽӲ�ѯSQL��ȡ
	 * @return
	 * @throws Exception
	 */
	public String searchTmDnSign()throws Exception;

}

package com.xbwl.oper.szsm.dao;

import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.entity.DigitalChinaExchange;

/**
 * 神州数码对接数据访问层接口
 * @author czl
 * @date 2012-06-20
 */
public interface IDataExchangeDao extends IBaseDAO<DigitalChinaExchange, Long>{

	/**
	 * 配送数据对接查询SQL获取
	 * @return
	 * @throws Exception
	 */
	public String searchTmTransferD()throws Exception;

	/**
	 * 签收数据对接查询SQL获取
	 * @return
	 * @throws Exception
	 */
	public String searchTmDnSign()throws Exception;

}

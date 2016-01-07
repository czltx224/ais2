package com.xbwl.oper.fax.service;

import java.util.List;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.BasValueAddFee;
import com.xbwl.entity.OprChangeDetail;
import com.xbwl.entity.OprChangeMain;
import com.xbwl.entity.OprFaxIn;
import com.xbwl.entity.OprValueAddFee;

/**
 *@author LiuHao
 *@time Aug 25, 2011 3:02:25 PM
 */
public interface IOprFaxChangeService extends IBaseService<OprChangeMain,Long> {
	/**
	 * 保存更改项的主表和明细
	 * @param oprChangeMain
	 * @param list
	 * @throws Exception
	 */
	public void saveOprFaxChange(List<OprValueAddFee> addFeeList,OprChangeMain oprChangeMain,List<OprChangeDetail> list,OprFaxIn ofi,String changeType) throws Exception;

	/**
	 * 保存更改项的主表和明细
	 * @param oprChangeMain
	 * @param list
	 * @throws Exception
	 */
	public String getSelectQuerySql(Long dno) throws Exception;
	/**
	 * 根据配送单号获得更改主表的信息
	 * @author LiuHao
	 * @time Mar 8, 2012 2:56:38 PM 
	 * @param dno
	 * @return
	 * @throws Exception
	 */
	public OprChangeMain getChangeByDno(Long dno)throws Exception;
}

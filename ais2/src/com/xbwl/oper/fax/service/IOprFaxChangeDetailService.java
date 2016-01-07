package com.xbwl.oper.fax.service;

import java.util.List;

import com.xbwl.common.orm.Page;
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
public interface IOprFaxChangeDetailService extends IBaseService<OprChangeDetail,Long> {
	/**
	 * ���ݸ�������ID��ѯ������ϸ��Ϣ
	 * @param page
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Page<OprChangeDetail> findDetailByMainId(Page page,Long id) throws Exception;
	/**
	 * ���������������ļ�¼
	 * @param list
	 * @param ofi
	 * @return
	 * @throws Exception
	 */
	public String updateChangeRecord(List<OprValueAddFee> addFeeList,List<OprChangeDetail> list,OprFaxIn ofi,Long oprDetailId,String oprName)throws Exception;
	/**
	 * �ж��Ƿ���תǷ��
	 * @author LiuHao
	 * @time Apr 18, 2012 9:19:06 AM 
	 * @param ofi
	 * @param ocdList
	 * @return
	 * @throws Exception
	 */
	public String decideDebt(OprFaxIn ofi,List<OprChangeDetail> ocdList) throws Exception;
}

package com.xbwl.oper.fax.vo;

import com.xbwl.entity.OprFaxIn;

/**
 * @project ais_edi
 * @author czl
 * @Time Feb 11, 2012 3:18:10 PM
 */
public class EdiFaxInVo extends OprFaxIn{

	private Long consigneeId;// ’ªı»ÀID

	public Long getConsigneeId() {
		return consigneeId;
	}

	public void setConsigneeId(Long consigneeId) {
		this.consigneeId = consigneeId;
	}
	
}

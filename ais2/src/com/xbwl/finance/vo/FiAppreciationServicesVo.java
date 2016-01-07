package com.xbwl.finance.vo;


import com.xbwl.entity.FiAppreciationService;

/**
 * author shuw
 * time Oct 27, 2011 11:13:37 AM
 */

public class FiAppreciationServicesVo  extends FiAppreciationService{
	
	private String inDepart;
	private Long inDepartId;
	private String cosignee;
	
	public String getInDepart() {
		return inDepart;
	}
	public void setInDepart(String inDepart) {
		this.inDepart = inDepart;
	}
	public String getCosignee() {
		return cosignee;
	}
	public void setCosignee(String cosignee) {
		this.cosignee = cosignee;
	}
	public Long getInDepartId() {
		return inDepartId;
	}
	public void setInDepartId(Long inDepartId) {
		this.inDepartId = inDepartId;
	}
	
	

}

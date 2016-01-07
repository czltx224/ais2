package com.xbwl.finance.vo;

import com.xbwl.entity.FiReceivabledetail;

/**
 * @ClassName: FiReceivabledetailVo
 * @Description: 往来账款明细VO。 配送单表：OprFaxIn、客商表：Customer、客商欠款设置:FiArrearset
 * @author oysz
 * @date Jul 14, 2011 5:04:13 PM
 */
public class FiReceivabledetailVo extends FiReceivabledetail {

	private String custprop;// 客商表：客商类型
	private String reconciliationUser;// 客商欠款设置:对账员
	private Long billingCycle; // 客商欠款设置:对账/结算周期
	
	private Long piece;// 件数
	private Double cusWeight;// 计费重量
	private Double bulk;// 体积
	
	public String getCustprop() {
		return custprop;
	}
	public void setCustprop(String custprop) {
		this.custprop = custprop;
	}
	public String getReconciliationUser() {
		return reconciliationUser;
	}
	public void setReconciliationUser(String reconciliationUser) {
		this.reconciliationUser = reconciliationUser;
	}
	public Long getBillingCycle() {
		return billingCycle;
	}
	public void setBillingCycle(Long billingCycle) {
		this.billingCycle = billingCycle;
	}
	
	public Long getPiece() {
		return piece;
	}

	public void setPiece(Long piece) {
		this.piece = piece;
	}

	public Double getCusWeight() {
		return cusWeight;
	}

	public void setCusWeight(Double cusWeight) {
		this.cusWeight = cusWeight;
	}

	public Double getBulk() {
		return bulk;
	}

	public void setBulk(Double bulk) {
		this.bulk = bulk;
	}

}

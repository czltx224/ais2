package com.xbwl.oper.szsm.beans;

/**
 * 签收数据对接xml输出Bean
 * @author czl
 * @date 2012-06-27
 */
public class SignInXMLBean {
	private String dono;
	private String signtime;
	private String signer;
	private String carrierno;
	private String memo;

	public String getXML() {
		StringBuffer xml = new StringBuffer();
	    xml.append("<?xml version=\"1.0\" encoding=\"GB2312\" ?><el-express><tracking name=\"sign\"><carriers>");
		xml.append("<carrierno>").append(this.carrierno).append("</carrierno>")
				.append("</carriers>")
				.append("<goodsinfo>")
				.append("<dono>").append(this.dono).append("</dono>")
				.append("<signer>").append(this.signer).append("</signer>")
				.append("<signtime>").append(this.signtime).append("</signtime>")
				.append("<memo>").append(this.memo).append("</memo>")
				.append("</goodsinfo>")
				.append("</tracking>").append("</el-express>");
		return xml.toString();
	}

	public String getDono() {
		return dono;
	}

	public void setDono(String dono) {
		this.dono = dono;
	}

	public String getSigntime() {
		return signtime;
	}

	public void setSigntime(String signtime) {
		this.signtime = signtime;
	}

	public String getSigner() {
		return signer;
	}

	public void setSigner(String signer) {
		this.signer = signer;
	}

	public String getCarrierno() {
		return carrierno;
	}

	public void setCarrierno(String carrierno) {
		this.carrierno = carrierno;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
}
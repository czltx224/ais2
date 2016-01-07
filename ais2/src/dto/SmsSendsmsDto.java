package dto;

import java.util.Date;

/**
 * @author czl
 * @Time 2012-3-29
 * 
 */
public class SmsSendsmsDto implements java.io.Serializable {

	private Long id;
	private String tel;//手机号码
	private String context;//短信内容
	private Date addtime;//短信写入时间
	private Long state=1L;//状态#0--已发送,1--未发送
	private String sendDepart;//发送部门
	private String sendName;//发送人
	private Long sysNo;//发送平台#20会员制
	private Long urgent;//加急
	private String smstype;//信息类型
	private String receiver;//#接收对象
	private String billno;//运单号
	private Long ncount;//短信条数
	private String msgid;//短信Id
	private Date sendtime;//发送时间
	private Date receivertime;//接收到的时间
	private Long retry;//重试次数
	private Long isok;//是否成功
	private String errid;//返回错误码
	private Long isyx;//是否有效
	private Long port;//信息通道#1梦网,2亿美
	//private Long sendnum;//群发代码#用于后期优化群发短信
	
	private String systemName;//系统名称
	private String uid;//客户端IP+密码+当天日期(yyyyMMdd),拼接后的MD5
	private String clientIp;//客户端IP
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public Date getAddtime() {
		return addtime;
	}
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	public Long getState() {
		return state;
	}
	public void setState(Long state) {
		this.state = state;
	}
	public String getSendDepart() {
		return sendDepart;
	}
	public void setSendDepart(String sendDepart) {
		this.sendDepart = sendDepart;
	}
	public String getSendName() {
		return sendName;
	}
	public void setSendName(String sendName) {
		this.sendName = sendName;
	}
	public Long getSysNo() {
		return sysNo;
	}
	public void setSysNo(Long sysNo) {
		this.sysNo = sysNo;
	}
	public Long getUrgent() {
		return urgent;
	}
	public void setUrgent(Long urgent) {
		this.urgent = urgent;
	}
	public String getSmstype() {
		return smstype;
	}
	public void setSmstype(String smstype) {
		this.smstype = smstype;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getBillno() {
		return billno;
	}
	public void setBillno(String billno) {
		this.billno = billno;
	}
	public Long getNcount() {
		return ncount;
	}
	public void setNcount(Long ncount) {
		this.ncount = ncount;
	}
	public String getMsgid() {
		return msgid;
	}
	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}
	public Date getSendtime() {
		return sendtime;
	}
	public void setSendtime(Date sendtime) {
		this.sendtime = sendtime;
	}
	public Date getReceivertime() {
		return receivertime;
	}
	public void setReceivertime(Date receivertime) {
		this.receivertime = receivertime;
	}
	public Long getRetry() {
		return retry;
	}
	public void setRetry(Long retry) {
		this.retry = retry;
	}
	public Long getIsok() {
		return isok;
	}
	public void setIsok(Long isok) {
		this.isok = isok;
	}
	public String getErrid() {
		return errid;
	}
	public void setErrid(String errid) {
		this.errid = errid;
	}
	public Long getIsyx() {
		return isyx;
	}
	public void setIsyx(Long isyx) {
		this.isyx = isyx;
	}
	public String getSystemName() {
		return systemName;
	}
	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getClientIp() {
		return clientIp;
	}
	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}
	public Long getPort() {
		return port;
	}
	public void setPort(Long port) {
		this.port = port;
	}
}

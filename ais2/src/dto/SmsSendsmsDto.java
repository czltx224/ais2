package dto;

import java.util.Date;

/**
 * @author czl
 * @Time 2012-3-29
 * 
 */
public class SmsSendsmsDto implements java.io.Serializable {

	private Long id;
	private String tel;//�ֻ�����
	private String context;//��������
	private Date addtime;//����д��ʱ��
	private Long state=1L;//״̬#0--�ѷ���,1--δ����
	private String sendDepart;//���Ͳ���
	private String sendName;//������
	private Long sysNo;//����ƽ̨#20��Ա��
	private Long urgent;//�Ӽ�
	private String smstype;//��Ϣ����
	private String receiver;//#���ն���
	private String billno;//�˵���
	private Long ncount;//��������
	private String msgid;//����Id
	private Date sendtime;//����ʱ��
	private Date receivertime;//���յ���ʱ��
	private Long retry;//���Դ���
	private Long isok;//�Ƿ�ɹ�
	private String errid;//���ش�����
	private Long isyx;//�Ƿ���Ч
	private Long port;//��Ϣͨ��#1����,2����
	//private Long sendnum;//Ⱥ������#���ں����Ż�Ⱥ������
	
	private String systemName;//ϵͳ����
	private String uid;//�ͻ���IP+����+��������(yyyyMMdd),ƴ�Ӻ��MD5
	private String clientIp;//�ͻ���IP
	
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

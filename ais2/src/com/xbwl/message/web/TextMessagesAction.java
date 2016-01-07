package com.xbwl.message.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.ralasafe.WebRalasafe;
import org.ralasafe.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;

import com.xbwl.common.service.IBaseService;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.message.service.ISmsSendsmsRecordService;

import dto.SmsSendsmsDto;
/**
 * @author czl
 * @Time 2012-3-27
 * 
 */
@Controller
@Action("textMessagesAction")
@Scope("prototype")
@Namespace("/message")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/message/opr_sendTextMessage.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "msg", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"excludeNullProperties", "true" }
		)}

)
public class TextMessagesAction extends SimpleActionSupport {

	private final static String SEND_MSG_URL="http://localhost:8080/message/sms/smsInterfaceAction!sendMsg.action";
	private String consigneeTels;
	private String textMessageContent;
	private String receiver;
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	
	
	@Resource(name="smsSendsmsRecordServiceImpl")
	private ISmsSendsmsRecordService smsSendsmsRecordService;
	
	/**
	 * 手机短信发送接口
	 * 实例：http://localhost:8081/message/sms/smsInterfaceAction!sendMsg.action?systemName=nis&uid=202cb962ac59075b964b07152d234b70&tel=18219455246&context=测试&sendDepart=ais研发部&sendName=阳书哲&sysNo=40&smstype=手动&receiver=阳书哲
	 * 1、systemName 用户权限表中系统名称
	 * 2、uid:客户端IP+密码+当天日期(yyyyMMdd),拼接后的MD5
	 * 1、tel 手机号码(必填)
	 * 2、context 内容(必填)
	 * 3、sendDepart 发送部门(必填)
	 * 4、sendName 发送人
	 * 5、sysNo 发送平台#(20会员制,40AIS)
	 * 6、urgent 加急
	 * 7、smstype//信息类型
	 * 7、receiver//#接收对象
	 * 7、billno//运单号
	 */
	public String sendMsg(){
		try{
			User user = WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
			String uid = "";
			String userName=user.get("name")+"";
			String bussDepartName=user.get("rightDepart")+"";
			HttpServletRequest  request = ServletActionContext.getRequest();
			String localIp=request.getRemoteAddr();
			
			this.consigneeTels = this.consigneeTels.replace("，", ",");
			String[] stsTel = this.consigneeTels.split(",");
			String telephones="";
			uid = this.smsSendsmsRecordService.getSmsUid(localIp);
			for (int i = 0; i < stsTel.length; i++) {
				if(i>0){
					telephones+=(char)18;
				}
				telephones+=stsTel[i];
			}
				SmsSendsmsDto dto =  new SmsSendsmsDto();
				dto.setTel(telephones);
				dto.setContext(this.textMessageContent);//短信内容
				dto.setSendName(userName);
				dto.setSendDepart(bussDepartName);
				dto.setSmstype("AIS通知短信");
				dto.setReceiver(this.receiver);//接收对象
				dto.setBillno("");
				dto.setClientIp(localIp);
				dto.setSystemName("ais");
				dto.setUid(uid);
				dto.setSysNo(2l);//手动设置
				
				this.smsSendsmsRecordService.saveSendMsg(dto,this.consigneeTels);
			
		} catch (Exception e) {
			addError(e.getLocalizedMessage(), e);
		} 
		
		return "reload";
	}
	
	/**
	 * 手机短信发送接口
	 * 实例：http://localhost:8081/message/sms/smsInterfaceAction!sendMsg.action?systemName=nis&uid=202cb962ac59075b964b07152d234b70&tel=18219455246&context=测试&sendDepart=ais研发部&sendName=阳书哲&sysNo=40&smstype=手动&receiver=阳书哲
	 * 1、systemName 用户权限表中系统名称
	 * 2、uid:客户端IP+密码+当天日期(yyyyMMdd),拼接后的MD5
	 * 1、tel 手机号码(必填)
	 * 2、context 内容(必填)
	 * 3、sendDepart 发送部门(必填)
	 * 4、sendName 发送人
	 * 5、sysNo 发送平台#(20会员制,40AIS)
	 * 6、urgent 加急
	 * 7、smstype//信息类型
	 * 7、receiver//#接收对象
	 * 7、billno//运单号
	 */
	public String sendMsg_old(){
		User user = WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
		String uid = "";
		String userName=user.get("name")+"";
		//String bussDepart=user.get("bussDepart")+"";
		String bussDepartName=user.get("rightDepart")+"";
		//String departId=user.get("departId")+"";
		String password = "xb4790china?/~~";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		
		HttpServletRequest  request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		
		Md5PasswordEncoder md5=new Md5PasswordEncoder();
		String localIp=request.getRemoteAddr();
		uid+=localIp;
		uid+=password;
		uid+=sdf.format(new Date());
		try {
			uid=md5.encodePassword(uid,"");
		} catch (Exception e1) {
			addError("MD5转换失败！", e1);
			return "reload";
		}
		String[] stsTel = this.consigneeTels.split(",");
		StringBuffer path = new StringBuffer();
		path.append(this.SEND_MSG_URL);
		path.append("?systemName=ais");
		path.append("&uid=").append(uid);
		path.append("&context=").append(this.textMessageContent);
		path.append("&sendDepart=").append(bussDepartName);
		path.append("&smstype=").append("手动");
		path.append("&sendName=").append(userName);
		path.append("&receiver=").append(userName);

		path.append("&sysNo=").append("1");

		path.append("&characterEncoding=utf-8");

		for (int i = 0; i < stsTel.length; i++) {
			String tel = stsTel[i];
			try{
				Long.valueOf(tel);
			}catch (Exception e) {
				e.printStackTrace();
				getValidateInfo().setSuccess(false);
		        getValidateInfo().setMsg("电话号码有误！ '"+tel+"'");
				break;
			}
			if(tel.length()<5 || tel.length()>11){
		        getValidateInfo().setSuccess(false);
		        getValidateInfo().setMsg("电话号码有误！ '"+tel+"'");
				break;
			}
			try {
				String url = path+"&tel="+tel;
				response.sendRedirect(url);
			} catch (Exception e) {
				addError("发送失败！", e);
			} 
		}
		return "reload";
	}
	
	/**
	 * 跳转到群发短信界面
	 * @return
	 */
	public String openPage(){
		
		return "input";
	}

	public String getConsigneeTels() {
		return consigneeTels;
	}

	public void setConsigneeTels(String consigneeTels) {
		this.consigneeTels = consigneeTels;
	}

	public String getTextMessageContent() {
		return textMessageContent;
	}

	public void setTextMessageContent(String textMessageContent) {
		this.textMessageContent = textMessageContent;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	@Override
	protected Object createNewInstance() {
		return null;
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return null;
	}

	@Override
	public Object getModel() {
		return null;
	}

	@Override
	public void setModel(Object obj) {
		
	}
}

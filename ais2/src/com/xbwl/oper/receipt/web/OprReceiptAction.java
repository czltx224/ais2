package com.xbwl.oper.receipt.web;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.ralasafe.WebRalasafe;
import org.ralasafe.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.util.Element;
import com.xbwl.common.service.IBaseService;
import com.xbwl.common.web.ServletUtils;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.entity.OprReceipt;
import com.xbwl.oper.receipt.service.IOprReceiptService;
import com.xbwl.oper.receipt.vo.ReceiptConfirmVo;

/**
 * author CaoZhili time Jul 25, 2011 5:50:24 PM
 * 
 * �ص����Ʋ������
 */
@Controller
@Action("oprReceiptAction")
@Scope("prototype")
@Namespace("/receipt")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/receipt/opr_receiptManagement.jsp", type = "dispatcher"),
		@Result(name = "putScan", location = "/WEB-INF/xbwl/receipt/put_ScanImagesUpload.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root","validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "msg", type = "json", params = { "root", "validateInfo",	"excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages","excludeNullProperties", "true" }
		) })
public class OprReceiptAction extends SimpleActionSupport {

	@Resource(name = "oprReceiptServiceImpl")
	private IOprReceiptService oprReceiptService;

	private OprReceipt oprReceipt;
	
	@Element(value = ReceiptConfirmVo.class)
	private List<ReceiptConfirmVo> confirmVo;
	
	private String dnos;
	private String  numString;
	
	public String putScan() {
		return "putScan";
	}
	
	
	public List<ReceiptConfirmVo> getConfirmVo() {
		return confirmVo;
	}

	public void setConfirmVo(List<ReceiptConfirmVo> confirmVo) {
		this.confirmVo = confirmVo;
	}

	public String getDnos() {
		return dnos;
	}

	public void setDnos(String dnos) {
		this.dnos = dnos;
	}

	@Override
	protected Object createNewInstance() {

		return new OprReceipt();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {

		return this.oprReceiptService;
	}

	@Override
	public Object getModel() {

		return this.oprReceipt;
	}

	@Override
	public void setModel(Object obj) {

		this.oprReceipt = (OprReceipt) obj;
	}
	@Value("${oprReceipt.requestStage}")
	private String   requestStage; 

	@Value("${oprScanneFigure.imageUrl}")
	private String imageUrl;
	/**
	 * �ص����㵽
	 * 
	 * @return
	 */
	public String saveReport() {
		User user = WebRalasafe.getCurrentUser(ServletActionContext
				.getRequest());
		this.oprReceipt = new OprReceipt();

		Long receiptId = Long.parseLong(ServletActionContext.getRequest()
				.getParameter("id"));
		this.oprReceipt.setReachNum(Long.parseLong(ServletActionContext
				.getRequest().getParameter("reachNum")));
		this.oprReceipt.setCurStatus("�����");
		
		this.oprReceipt.setReachMan(user.get("name").toString());
		this.oprReceipt.setReachStatus(new Long(1));
		this.oprReceipt.setReachTime(new Date());

		try {
			this.oprReceiptService.saveReportService(this.oprReceipt,receiptId);
		} catch (Exception e) {
			e.printStackTrace();
			super.getValidateInfo().setSuccess(false);
			super.getValidateInfo().setMsg(e.getLocalizedMessage());
			return "msg";
		}

		return "reload";
	}

	/**
	 * �ص��ĳ�
	 * 
	 * @return 
	 */
	public String saveOut() {
		this.oprReceipt = new OprReceipt();
		
		String dnos=ServletActionContext.getRequest().getParameter("dnos");
		
		this.oprReceipt.setOutCompany(ServletActionContext.getRequest()
				.getParameter("outCompany"));
		try{
			this.oprReceipt.setOutCost(Double.parseDouble(ServletActionContext.getRequest()
				.getParameter("outCost")));
		}catch (Exception e) {
			this.oprReceipt.setOutCost(0d);
		}
		this.oprReceipt.setOutMan(ServletActionContext.getRequest()
				.getParameter("outMan"));
		this.oprReceipt.setOutNo(ServletActionContext.getRequest()
				.getParameter("outNo"));
		this.oprReceipt.setOutStatus(new Long(1));
		this.oprReceipt.setOutTime(new Date());
		this.oprReceipt.setOutWay(ServletActionContext.getRequest()
				.getParameter("outWay"));
	
		this.oprReceipt.setCurStatus("�Ѽĳ�");

		try {
			this.oprReceiptService.saveOutService(this.oprReceipt,this.dnos.split(","));
			super.getValidateInfo().setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			super.getValidateInfo().setSuccess(false);
			super.getValidateInfo().setMsg(e.getLocalizedMessage());
			return "msg";
		}

		return "reload";
	}

	/**
	 * �ص���ȡ 
	 * @return
	 */
	public String saveGet() {
		this.oprReceipt = new OprReceipt();

		String ids=ServletActionContext.getRequest().getParameter("ids");
		this.oprReceipt.setGetMan(ServletActionContext.getRequest()
				.getParameter("getMan"));
		try{
			this.oprReceipt.setGetNum(Long.valueOf(ServletActionContext.getRequest()
				.getParameter("getNum")));
		}catch (NumberFormatException e) {
			addError("�ص�������������", e);
		}
		
		this.oprReceipt.setCurStatus("����ȡ");
		this.oprReceipt.setGetStatus(new Long(1));
		this.oprReceipt.setGetTime(new Date());

		try {
			this.oprReceiptService.saveGetService(this.oprReceipt,ids);
		} catch (Exception e) {
			e.printStackTrace();
			super.getValidateInfo().setSuccess(false);
			super.getValidateInfo().setMsg(e.getLocalizedMessage());
			return "msg";
		}

		return "reload";
	}

	/**
	 * �ص�ȷ��
	 * 
	 * @return
	 */
	public String saveConfirm() {
		User user = WebRalasafe.getCurrentUser(ServletActionContext
				.getRequest());
		String userName=user.get("name")+"";
		Date dt = new Date();
		String curStatus = "��ȷ��";
		
		for(int i=0;i<this.confirmVo.size();i++){
			this.confirmVo.get(i).setConfirmMan(userName);
			this.confirmVo.get(i).setConfirmTime(dt);
			this.confirmVo.get(i).setCurStatus(curStatus);
		}
		
		try {
			this.oprReceiptService.saveConfirmService(this.confirmVo);
		} catch (Exception e) {
			e.printStackTrace();
			super.getValidateInfo().setSuccess(false);
			super.getValidateInfo().setMsg(e.getLocalizedMessage());
			return "msg";
		}

		return "reload";
	}
	 public void prepareSqlRalaList() {
	    	try {
	    	     //���÷�ҳ����
		         setPageConfig();
			} catch (Exception e) {
				e.printStackTrace();
			}
	  }
	/**
	 * ��дSQL��ѯ����
	 * @return
	 */
	public String sqlRalaList(){
		StringBuffer sb=new StringBuffer();
		try{
			Map<String, String> map = ServletUtils.getParametersStartingWith(Struts2Utils.getRequest(), "filter_");
			//map.put("REQUEST_STAGE", this.requestStage);
			sb=this.oprReceiptService.getSqlRalaListService(map);
			this.oprReceiptService.getPageBySqlMap(this.getPages(), sb.toString(), map);
		}catch (Exception e) {
			addError("�ص���ѯʧ�ܣ�", e);
		}
		return "list";
	}
	
	/**
	 * ��ȡǩ��ͼ
	 * @return
	 */
	public String getImage(){
		try{
			List<String> listUrl =oprReceiptService.getImageUrlList(getId(),imageUrl);
			Struts2Utils.renderJson(listUrl);
		}catch (Exception e) {
			addError("ȡ�ص�ɨ��ͼƬʧ�ܣ�", e);
			return null;
		}
		return null;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	/**
	 * �����ص�ȷ��
	 * @return
	 */
	public String cancelConfirm(){
		String receiptIds  = ServletActionContext.getRequest().getParameter("receiptIds");
		if(null==receiptIds || "".equals(receiptIds)){
			getValidateInfo().setSuccess(false);
	        getValidateInfo().setMsg("�봫��ȷ�Ļص�������");
	        return this.RELOAD;
		}
		try{
			this.oprReceiptService.cancelConfirmService(receiptIds.split(","));
		}catch (Exception e) {
			getValidateInfo().setSuccess(false);
	        getValidateInfo().setMsg("�ص�ȷ�ճ���ʧ�ܣ�"+e.getLocalizedMessage());
		}
		return this.RELOAD;
	}
	
	public String delImageAddr() {
		try{
			oprReceiptService.delImageUrl(getId(),numString);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * �����ص��ĳ�
	 * @return
	 */
	public String cancelOut(){
		String receiptIds  = ServletActionContext.getRequest().getParameter("receiptIds");
		if(null==receiptIds || "".equals(receiptIds)){
			getValidateInfo().setSuccess(false);
	        getValidateInfo().setMsg("�봫��ȷ�Ļص�������");
	        return this.RELOAD;
		}
		try{
			this.oprReceiptService.cancelOutService(receiptIds.split(","));
		}catch (Exception e) {
			getValidateInfo().setSuccess(false);
	        getValidateInfo().setMsg("�ص��ĳ�����ʧ�ܣ�"+e.getLocalizedMessage());
		}
		return this.RELOAD;
	}


	public String getNumString() {
		return numString;
	}


	public void setNumString(String numString) {
		this.numString = numString;
	}
}

package com.xbwl.flow.web;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.service.IBaseService;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.entity.FlowNodeinfo;
import com.xbwl.entity.FlowPipeinfo;
import com.xbwl.flow.service.IFlowExportService;
import com.xbwl.flow.service.IFlowNodeinfoService;
import com.xbwl.flow.service.IFlowPipeinfoService;

/**
 * ���̹���- ������Ϣ���Ʋ�
 * @author LiuHao
 * @time Aug 16, 2011 2:59:16 PM
 */
@Controller
@Action("flowPipeinfoAction")
@Scope("prototype")
@Namespace("/flow")
@Results( {
		@Result(name = "flowlayout", location = "/WEB-INF/xbwl/flow/workflowlayout.jsp", type = "dispatcher"),
		@Result(name = "update", location = "/WEB-INF/xbwl/flow/flow_pipeupdate.jsp", type = "dispatcher"),
		@Result(name = "input", location = "/WEB-INF/xbwl/flow/flow_pipeinfo.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"excludeNullProperties", "true" }) })
public class FlowPipeinfoAction extends SimpleActionSupport {
	
	@Resource(name="flowPipeinfoServiceImpl")
	private IFlowPipeinfoService flowPipeinfoService;
	@Resource(name="flowNodeinfoServiceImpl")
	private IFlowNodeinfoService flowNodeinfoService;
	@Resource(name="flowExportServiceImpl")
	private IFlowExportService flowExportService;
	private FlowPipeinfo flowPipeinfo;
	private Long flowId;//����ID
	private String flowName;//��������
	
	private String dFlag;//ɾ����� start stop
	private Long formId;//��ID
	private String formName;//������
	private String dicObjType;//��������
	
	private String xmlStr;//����ͼXML�ַ���
	
	@Override
	protected Object createNewInstance() {
		return new FlowPipeinfo();
	}
	@Override
	public Map getContextMap() {
		return null;
	}
	@Override
	protected IBaseService getManager() {
		return flowPipeinfoService;
	}
	@Override
	public Object getModel() {
		return flowPipeinfo;
	}
	@Override
	public void setModel(Object obj) {
		this.flowPipeinfo=(FlowPipeinfo)obj;
	}
	
	
	/**
	 * @return the flowId
	 */
	public Long getFlowId() {
		return flowId;
	}
	/**
	 * @param flowId the flowId to set
	 */
	public void setFlowId(Long flowId) {
		this.flowId = flowId;
	}
	
	/**
	 * @return the dFlag
	 */
	public String getDFlag() {
		return dFlag;
	}
	/**
	 * @param flag the dFlag to set
	 */
	public void setDFlag(String flag) {
		dFlag = flag;
	}
	
	/**
	 * @return the formName
	 */
	public String getFormName() {
		return formName;
	}
	/**
	 * @param formName the formName to set
	 */
	public void setFormName(String formName) {
		this.formName = formName;
	}
	/**
	 * @return the dicObjType
	 */
	public String getDicObjType() {
		return dicObjType;
	}
	/**
	 * @param dicObjType the dicObjType to set
	 */
	public void setDicObjType(String dicObjType) {
		this.dicObjType = dicObjType;
	}
	
	/**
	 * @return the flowName
	 */
	public String getFlowName() {
		return flowName;
	}
	/**
	 * @param flowName the flowName to set
	 */
	public void setFlowName(String flowName) {
		this.flowName = flowName;
	}
	
	/**
	 * @return the formId
	 */
	public Long getFormId() {
		return formId;
	}
	/**
	 * @param formId the formId to set
	 */
	public void setFormId(Long formId) {
		this.formId = formId;
	}
	
	/**
	 * @return the xmlStr
	 */
	public String getXmlStr() {
		return xmlStr;
	}
	/**
	 * @param xmlStr the xmlStr to set
	 */
	public void setXmlStr(String xmlStr) {
		this.xmlStr = xmlStr;
	}
	/**
	 * ͣ�� ��������
	 * @author LiuHao
	 * @time Feb 17, 2012 10:31:21 AM 
	 * @return
	 */
	public String stopOrStartFlow(){
		FlowPipeinfo fp=flowPipeinfoService.getAndInitEntity(flowId);
		if(fp == null){
			throw new ServiceException("�����쳣��ID:"+flowId+"��Ӧ�Ķ���Ϊ����!");
		}
		//�޸�����ͣ��״̬ 1.����  0.ɾ��
		if("start".equals(dFlag)){
			fp.setIsDelete(1L);
		}else{
			fp.setIsDelete(0L);
		}
		flowPipeinfoService.save(fp);
		return RELOAD;
	}
	
	public String delete(){
		FlowPipeinfo fp=flowPipeinfoService.getAndInitEntity(flowId);
		if(fp == null){
			throw new ServiceException("�����쳣��ID:"+flowId+"��Ӧ�Ķ���Ϊ����!");
		}
		fp.setStatus(0L);
		flowPipeinfoService.save(fp);
		return RELOAD;
	}
	public String gotoUpdatePipe(){
		return "update";
	}
	
	public String toFlowlayout(){
		return "flowlayout";
	}
	public String getLayoutXml(){
		
		XMLOutputter outputer=new XMLOutputter();
		try {
			FlowNodeinfo startNode=flowNodeinfoService.getSEnodeinfoByPipeId(flowId, "start");
			if (startNode == null) {
			      Element graph = new Element("graph");
			      Document document = new Document(graph);
			      outputer.output(document, Struts2Utils.getResponse().getOutputStream());
			}
			if ((startNode.getDrawxpos() <= 0) || (startNode.getDrawypos() <= 0)){
			     flowPipeinfoService.setDrawpos(flowId);
			}
			Document document = flowPipeinfoService.getLayoutXml(flowId);
			String str = outputer.outputString(document);
			//System.out.println(str);
			outputer.output(document, Struts2Utils.getResponse().getOutputStream());
		} catch (Exception e) {
			addError("��ѯ����", e);
		}
		return null;
	}
	/**
	 * �����û��϶�������ͼ����
	 * @author LiuHao
	 * @time Feb 23, 2012 4:59:07 PM 
	 * @return
	 */
	public String saveLayoutXML(){
		try {
			flowPipeinfoService.saveLayoutXml(xmlStr);
		} catch (Exception e) {
			addError("�������", e);
		}
		return RELOAD;
	}
}

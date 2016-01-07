package com.xbwl.timing.web;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.xbwl.common.service.IBaseService;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.oper.edi.scanning.AISCtEstimateScanning;
import com.xbwl.oper.edi.scanning.AISCtFaxOutScanning;
import com.xbwl.oper.edi.scanning.AISCtTmdScanning;
import com.xbwl.oper.edi.scanning.AISSysQianshouScanning;
import com.xbwl.oper.szsm.scanning.AISScanningShenZhouShuMa;
@Controller
@Action("aisScanningByHandAction")
@Scope("prototype")
@Namespace("/timing")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/timing/aisScanningByHand.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "msg", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"excludeNullProperties", "true" }
		) }
)
public class AISScanningByHandAction extends SimpleActionSupport {

	@Resource(name="aisCtEstimateScanning")
	private AISCtEstimateScanning ctEstimateScanning;
	
	@Resource(name="aisCtTmdScanning")
	private AISCtTmdScanning ctTmdScanning;
	
	@Resource(name="aisCtFaxChangeScanning")
	private AISCtFaxOutScanning ctFaxOutScanning;
	
	@Resource(name="aisSysQianshouScanning")
	private AISSysQianshouScanning sysQianshouScanning;
	
	@Resource(name="aisScanningShenZhouShuMa")
	private AISScanningShenZhouShuMa scanningShenZhouShuMa;
	
	private String scanningName;
	
	public String getScanningName() {
		return scanningName;
	}

	public void setScanningName(String scanningName) {
		this.scanningName = scanningName;
	}

	/**
	 * 手动执行定时任务ACTION
	 * @return
	 */
	public String doScanningByHand(){
		if(null==scanningName || "".equals(scanningName)){
			String errormsg = "扫描任务类为空！"; 
			ServletActionContext.getRequest().setAttribute("errormsg",errormsg);
			this.getValidateInfo().setMsg(errormsg);
			this.getValidateInfo().setSuccess(false);
		}else{
			if(scanningName.equals("ctEstimateScanning")){
				this.ctEstimateScanning.execute();
			}else if(scanningName.equals("ctTmdScanning")){
				this.ctTmdScanning.execute();
			}else if(scanningName.equals("ctFaxOutScanning")){
				this.ctFaxOutScanning.execute();
			}else if(scanningName.equals("sysQianshouScanning")){
				this.sysQianshouScanning.execute();
			}else if(scanningName.equals("scanningShenZhouShuMa")){
				this.scanningShenZhouShuMa.execute();
			}else{
				String errormsg = "错误的扫描任务类！";
				ServletActionContext.getRequest().setAttribute("errormsg",errormsg);
				this.getValidateInfo().setMsg(errormsg);
				this.getValidateInfo().setSuccess(false);
			}
		}
		return this.INPUT;
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

package com.xbwl.cus.web;

import java.io.File;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.xbwl.common.service.IBaseService;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.cus.service.ICusCollectionService;
import com.xbwl.cus.service.ICusComplaintService;
import com.xbwl.entity.CusCollection;
import com.xbwl.entity.FiPayment;
import com.xbwl.finance.Service.IFiPaymentService;

/**
 * 客户催款控制层
 *@author LiuHao
 *@time Oct 8, 2011 1:53:31 PM
 */
@Controller
@Action("cusPaymentAction")
@Scope("prototype")
@Namespace("/cus")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/cus/cus_payment.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "msg", type = "json", params = { "root", "validateInfo",
				"excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"excludeNullProperties", "true" }
		) }
)
public class CusPaymentAction extends SimpleActionSupport {
	@Resource(name="cusCollectionServiceImpl")
	private ICusCollectionService cusCollectionService;
	private CusCollection cusCollection;
	private File upFile;
	private String upFileFileName;
	@Value("${exception_images_url}")
	private String exceptionImagesUrl;
	@Override
	protected Object createNewInstance() {
		return new CusCollection();
	}
	@Override
	public Map getContextMap() {
		return null;
	}
	@Override
	protected IBaseService getManager() {
		return cusCollectionService;
	}
	@Override
	public Object getModel() {
		return cusCollection;
	}
	@Override
	public void setModel(Object obj) {
		this.cusCollection=(CusCollection)obj;
	}
	/**
	 * @return the upFile
	 */
	public File getUpFile() {
		return upFile;
	}
	/**
	 * @param upFile the upFile to set
	 */
	public void setUpFile(File upFile) {
		this.upFile = upFile;
	}
	/**
	 * @return the upFileFileName
	 */
	public String getUpFileFileName() {
		return upFileFileName;
	}
	/**
	 * @param upFileFileName the upFileFileName to set
	 */
	public void setUpFileFileName(String upFileFileName) {
		this.upFileFileName = upFileFileName;
	}
	public String save(){
		try {
			cusCollectionService.saveCollection(cusCollection, upFile, upFileFileName);
		} catch (Exception e) {
			addError("数据保存失败！", e);
		}
		return RELOAD;
	}
}

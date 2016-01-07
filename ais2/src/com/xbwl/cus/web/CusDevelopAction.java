package com.xbwl.cus.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.service.IBaseService;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.cus.service.ICusDemandService;
import com.xbwl.cus.service.ICusDevelopService;
import com.xbwl.entity.CusDemand;
import com.xbwl.entity.CusDevelop;
import com.xbwl.entity.CusLinkman;

/**
 * �ͻ��������̿��Ʋ�
 *@author LiuHao
 *@time Oct 8, 2011 1:53:31 PM
 */
@Controller
@Action("cusDevelopAction")
@Scope("prototype")
@Namespace("/cus")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/cus/cus_develop.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "msg", type = "json", params = { "root", "validateInfo",
				"excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"excludeNullProperties", "true" }
		) }
)
public class CusDevelopAction extends SimpleActionSupport {
	@Resource(name="cusDevelopServiceImpl")
	private ICusDevelopService cusDevelopService;
	private CusDevelop cusDevelop;
	private File upFile;
	private String upFileFileName;
	@Value("${exception_images_url}")
	private String exceptionImagesUrl;
	@Override
	protected Object createNewInstance() {
		return new CusDevelop();
	}
	@Override
	public Map getContextMap() {
		return null;
	}
	@Override
	protected IBaseService getManager() {
		return cusDevelopService;
	}
	@Override
	public Object getModel() {
		return cusDevelop;
	}
	@Override
	public void setModel(Object obj) {
		this.cusDevelop=(CusDevelop)obj;
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
	
	/**
	 * @return the exceptionImagesUrl
	 */
	public String getExceptionImagesUrl() {
		return exceptionImagesUrl;
	}
	/**
	 * @param exceptionImagesUrl the exceptionImagesUrl to set
	 */
	public void setExceptionImagesUrl(String exceptionImagesUrl) {
		this.exceptionImagesUrl = exceptionImagesUrl;
	}
	public String save(){
		FileInputStream fis=null;
		try {
			if(upFile!=null){
				fis=new FileInputStream(upFile);
				//REVIEW Ӧ�ý�������ֱ�ӷ��뵽�Ƚϵط�������ÿ���жϵļ��㣬����if��size��10584760��
				//FIXED liuh
					//int size=fis.available()/1024/1024;
					//10485760=10*1024*1024 �ļ���СΪ10M
					if(fis.available()>10485760){
						getValidateInfo().setMsg("�ϴ��ļ���С���ܴ���10M��");
			    		addMessage("�ϴ��ļ���С���ܴ���10M��");
			    		Struts2Utils.render("text/html; charset=UTF-8",JSONObject.fromObject(getValidateInfo()).toString());
			    		return null;
					}
			}
			cusDevelopService.saveCusDevelop(cusDevelop, upFile, upFileFileName);
			getValidateInfo().setMsg("���ݱ���ɹ���");
    		addMessage("���ݱ���ɹ���");
		} catch(FileNotFoundException e){
    		addError("�����ϴ����ļ���", e);
    		getValidateInfo().setMsg("�����ϴ����ļ���");
    		addMessage("�����ϴ����ļ���");
		}catch(Exception e){
			getValidateInfo().setSuccess(false);
        	getValidateInfo().setMsg("���ݱ���ʧ�ܣ�");
            addError("���ݱ���ʧ�ܣ�", e);
		}finally{
			try {
				if(fis!=null){
					fis.close();
				}
			} catch (IOException e) {
				 addError("���ݱ���ʧ�ܣ�", e);
			}
		}
		Struts2Utils.render("text/html; charset=UTF-8",JSONObject.fromObject(getValidateInfo()).toString());
		return null;
	}
	public String delete(){
		
		try {
			List pks = getPksByIds();
			//REVIEW-ACCEPT �ж��Ƿ�Ϊ��
			//FIXED LIUH
			if(pks == null || pks.size() < 1){
				throw new ServiceException("�����쳣��Ҫɾ������ϢΪ��");
			}else{
				cusDevelopService.delCusDevelop(pks);
				getValidateInfo().setSuccess(true);
	        	getValidateInfo().setMsg("����ɾ���ɹ���");
			}
		} catch (Exception e) {
			getValidateInfo().setSuccess(false);
        	getValidateInfo().setMsg("����ɾ��ʧ�ܣ�");
        	addError("����ɾ��ʧ�ܣ�", e);
		}
		return RELOAD;
	}
}

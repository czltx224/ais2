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
import com.xbwl.cus.service.ICusGoodsRankService;
import com.xbwl.cus.service.ICusRecordService;
import com.xbwl.entity.CusDemand;
import com.xbwl.entity.CusGoodsRank;
import com.xbwl.entity.CusLinkman;
import com.xbwl.entity.CusRecord;

/**
 * 客户需求控制层
 *@author LiuHao
 *@time Oct 8, 2011 1:53:31 PM
 */
@Controller
@Action("cusGoodsRankAction")
@Scope("prototype")
@Namespace("/cus")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/reports/cus/report_cusgoodsrank.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "msg", type = "json", params = { "root", "validateInfo",
				"excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"excludeNullProperties", "true" }
		) }
)
public class CusGoodsRankAction extends SimpleActionSupport {
	@Resource(name="cusGoodsRankServiceImpl")
	private ICusGoodsRankService cusGoodsRankService;
	private CusGoodsRank cusGoodsRank;
	@Override
	protected Object createNewInstance() {
		return new CusGoodsRank();
	}
	@Override
	public Map getContextMap() {
		return null;
	}
	@Override
	protected IBaseService getManager() {
		return cusGoodsRankService;
	}
	@Override
	public Object getModel() {
		return cusGoodsRank;
	}
	@Override
	public void setModel(Object obj) {
		this.cusGoodsRank=(CusGoodsRank)obj;
	}
	
	public String getGoodsRank(){
		this.setPageConfig();
		try {
			cusGoodsRankService.findAllRank(this.getPages());
		} catch (Exception e) {
			addError("查询出错", e);
		}
		return LIST;
	}
	public String save(){
		try {
			cusGoodsRankService.save((CusGoodsRank)this.getModel());
			this.getValidateInfo().setSuccess(true);
			this.getValidateInfo().setMsg("保存成功");
		} catch (Exception e) {
			addError("保存失败", e);
		}
		return RELOAD;
	}
}

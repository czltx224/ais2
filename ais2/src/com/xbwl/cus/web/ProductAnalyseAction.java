package com.xbwl.cus.web;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.xbwl.common.service.IBaseService;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.cus.service.IProAnalyseService;
import com.xbwl.cus.vo.ReportSerarchVo;
import com.xbwl.entity.ProductAnalyse;

/**
 * 客户产品分析控制层
 * author LiuHao
 *  time Jul 6, 2011 2:40:02 PM
 */
@Controller
@Action("productAnalyseAction")
@Scope("prototype")
@Namespace("/cus")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/reports/cus/report_procuctAnalyse.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root","validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages","excludeNullProperties", "true" }
		) }
)
public class ProductAnalyseAction extends SimpleActionSupport {

	@Resource(name = "proAnalyseServiceImpl")
	private IProAnalyseService proAnalyseService;
	private ReportSerarchVo reportSerarchVo;
	//整体分析统计时间
	private Date wholeDate;
	//产品货量/销售分析 类型(货量、销售)
	private String countType;
	//产品分析统计时间
	private Date typeDate;
	//货量等级统计时间
	private Date goodsRankDate;

	@Override
	protected Object createNewInstance() {
		return new ReportSerarchVo();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return this.proAnalyseService;
	}

	@Override
	public Object getModel() {
		return this.reportSerarchVo;
	}
	@Override
	public void setModel(Object obj) {
		this.reportSerarchVo = (ReportSerarchVo) obj;
	}
	/**
	 * @return the wholeDate
	 */
	public Date getWholeDate() {
		return wholeDate;
	}

	/**
	 * @param wholeDate the wholeDate to set
	 */
	public void setWholeDate(Date wholeDate) {
		this.wholeDate = wholeDate;
	}
	
	/**
	 * @return the countType
	 */
	public String getCountType() {
		return countType;
	}

	/**
	 * @param countType the countType to set
	 */
	public void setCountType(String countType) {
		this.countType = countType;
	}
	
	/**
	 * @return the typeDate
	 */
	public Date getTypeDate() {
		return typeDate;
	}

	/**
	 * @param typeDate the typeDate to set
	 */
	public void setTypeDate(Date typeDate) {
		this.typeDate = typeDate;
	}
	
	/**
	 * @return the goodsRankDate
	 */
	public Date getGoodsRankDate() {
		return goodsRankDate;
	}

	/**
	 * @param goodsRankDate the goodsRankDate to set
	 */
	public void setGoodsRankDate(Date goodsRankDate) {
		this.goodsRankDate = goodsRankDate;
	}
	
	public void prepareFindProWholeMsg(){
		try {
			this.prepareSave();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void prepareFindCountMsg(){
		try {
			this.prepareSave();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void prepareFindGoodsRank(){
		try {
			this.prepareSave();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 整体产品分析
	 * @return
	 */
	public String findProWholeMsg(){
		this.setPageConfig();
		try {
			proAnalyseService.findWholePro(this.getPages(),reportSerarchVo);
		} catch (Exception e) {
			addError("查询出错", e);
		}
		return LIST;
	}
	/**
	 * 产品趋势分析
	 * @return
	 */
	public String findCountMsg(){
		this.setPageConfig();
		try {
			proAnalyseService.findIncomePro(this.getPages(),reportSerarchVo);
		} catch (Exception e) {
			addError("查询出错！", e);
		}
		return LIST;
	}
	/**
	 * 产品等级分析
	 * @return
	 */
	public String findGoodsRank(){
		this.setPageConfig();
		try {
			proAnalyseService.findGoodsRank(this.getPages(),reportSerarchVo);
		} catch (Exception e) {
			addError("查询出错", e);
		}
		return LIST;
	}
}

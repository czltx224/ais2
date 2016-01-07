package com.xbwl.oper.stock.web;

import java.util.Date;
import java.util.List;
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
import com.xbwl.common.utils.DoubleUtil;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.oper.fax.vo.OprFaxInVo;
import com.xbwl.oper.stock.service.IOprOutStockService;

/**
 * author shuw
 * time Aug 22, 2011 11:35:46 AM
 */

@Controller
@Action("oprOutStoreAction")
@Scope("prototype")
@Namespace("/stock")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/fax/out_store.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root","validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages","excludeNullProperties", "true" }
		) })
public class OprOutStockAction extends SimpleActionSupport {

	private OprFaxInVo oprFaxInVo;
	
	@Resource(name = "oprOutStockServiceImpl")
	private IOprOutStockService oprOutStockService;
	
	@Value("${opr_store_feerate}")
	private String  opr_ddDouble;

	@Value("${free_time_hours}")
	private String  free_time_hours;

	@Value("${free_lowest}")
	private String free_lowest;
	
	/**
	 * 根据配送单号查自提出库货物信息
	 * @return  重写ralaList方法
	 */
	public String ralaList()  {
		double free=Double.parseDouble(free_lowest);    //最低价钱
		double  dFree=Double.parseDouble(opr_ddDouble);     //平均值
		long  hours=Long.parseLong(free_time_hours);  // 免费小时数
	  	try {
	  		getRequestFilter();
        	Struts2Utils.getSession().setAttribute("gotoPage",Struts2Utils.getRequest().getRequestURI() );
        	setPages(getManager().getPageByRela(getFilters(),Struts2Utils.getRequest(), getPrivilege(), getContextMap(),getPages()));
        	List  list =getPages().getResult();
        	for(Object object  :list){
        		OprFaxInVo oprFaxInVo=(OprFaxInVo)object;
        	//	Date d =oprFaxInVo.getStoreDate();                  // 入库时间　
        		Date nowTime = new Date();
        		Date informTime=oprFaxInVo.getNoticeDate();    // 通知客户提货时间
        	//	DateFormat   f   =   new   SimpleDateFormat( "yyyy-MM-dd   hh:MM");  //测试用的
        	//	Date   d   =   f.parse( "2010-7-09   23:20");                                       //测试用的
        	//	Date d2=f.parse("2010-7-011   23:20");
        		long hour = 0;
        		if(informTime!=null){
		        		if((nowTime.getTime()-informTime.getTime()%(60*60*1000)==0)){
		        			hour =(nowTime.getTime()-informTime.getTime())/(60*60*1000);  //　库存小时数
		        		}else{
		        			hour =(nowTime.getTime()-informTime.getTime())/(60*60*1000)+1;
		        		}
        		}
        		oprFaxInVo.setStoreTime(hour);
        		if(hour>=hours){
        			Long t =(hour-hours)%24==0?(hour-hours)/24:(hour-hours)/24+1;  // 天数
        			
        			double wd =(oprFaxInVo.getCusWeight());
        			double total=DoubleUtil.mul(DoubleUtil.mul(dFree,wd),t); // 仓储费等于 重量乘以费率
        			if(total<free){
        				  oprFaxInVo.setStoreFee(DoubleUtil.round(free,2));
        			}else {
       				     oprFaxInVo.setStoreFee(DoubleUtil.round(total,2));
					}
        		}else {
        			oprFaxInVo.setStoreFee(0.0);  //改成零
				}
        		oprFaxInVo.setTotalCpValueAddFee(DoubleUtil.add(oprFaxInVo.getCusValueAddFee(),oprFaxInVo.getStoreFee()));
        		oprFaxInVo.setTotalPaymentCollection(DoubleUtil.add(oprFaxInVo.getTotalCpValueAddFee(),oprFaxInVo.getConsigneeFee(),oprFaxInVo.getPaymentCollection()));
        		oprFaxInVo.setSysTotalPaymentCollection(oprFaxInVo.getTotalPaymentCollection());
        	}
			getPages().setResult(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return LIST;
	}
	
	
	
	public String getOpr_ddDouble() {
		return opr_ddDouble;
	}

	public void setOpr_ddDouble(String opr_ddDouble) {
		this.opr_ddDouble = opr_ddDouble;
	}

	public String getFree_time_hours() {
		return free_time_hours;
	}

	public void setFree_time_hours(String free_time_hours) {
		this.free_time_hours = free_time_hours;
	}

	public String getFree_lowest() {
		return free_lowest;
	}

	public void setFree_lowest(String free_lowest) {
		this.free_lowest = free_lowest;
	}
	
	
	
	
	
	
	
	@Override
	protected Object createNewInstance() {
		return new OprFaxInVo();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return oprOutStockService;
	}

	@Override
	public Object getModel() {
		return oprFaxInVo;
	}

	@Override
	public void setModel(Object obj) {
		oprFaxInVo=(OprFaxInVo)obj;
	}

}

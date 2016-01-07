package com.xbwl.finance.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.ralasafe.WebRalasafe;
import org.ralasafe.user.User;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.xbwl.common.orm.Page;
import com.xbwl.common.service.IBaseService;
import com.xbwl.common.utils.DoubleUtil;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.finance.Service.IFiIncomeService;
import com.xbwl.finance.vo.FiIncomeVo;

/**
 * author shuw
 * time Oct 13, 2011 11:10:46 AM
 */
@Controller
@Action("fiIncomeVoAction")
@Scope("prototype")
@Namespace("/fi")
@Results( {
		@Result(name = "reload", type = "json", params = { "root","validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "input", location = "/WEB-INF/xbwl/fi/fi_Income.jsp", type = "dispatcher"),
		@Result(name = "list", type = "json", params = { "root", "pages","includeProperties", "*" }) })	
public class FiIncomeVoAction extends SimpleActionSupport {

	private FiIncomeVo fiIncomeVo;
	
	@Resource(name="fiIncomeServiceImpl")
	private IFiIncomeService fiIncomeService;
	
	private String selectString;
	private Date startTime;
	private Date endTime;
	private Long dno;//配送单号
	private Long departId;
	private String certiNo;
	private String serviceCode;
	private Long accountId;//交账单号
	private String costType;//费用类型

	public String  getTotalFiInIncome(){
		try {
			setPageConfig();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String endTimeString = null;
			String startTimeString =null;
			if(endTime!=null){
				endTimeString = formatter.format(endTime)+" 23:59:59";
			}
			if(startTime!=null){
				startTimeString = formatter.format(startTime)+" 00:00:00";
			}
			Map map=new HashMap();
			map.put("departId", departId);
			map.put("selectString", selectString);
			map.put("startTime", startTime);
			map.put("endTime",endTime );
			map.put("dno", dno);
			map.put("serviceCode", serviceCode);
			map.put("certiNo",certiNo );
			String sql =  fiIncomeService.getTotalFiInIncome(map);
			Map map2=new HashMap();
			map2.put("departId", departId);
			map2.put("selectString", selectString);
			map2.put("startTime", startTimeString);
			map2.put("endTime",endTimeString );
			map2.put("serviceCode", "%"+serviceCode+"%");
			map2.put("dno", dno);
			map2.put("certiNo",certiNo );
			fiIncomeService.getPageBySqlMap(getPages(),sql,map2);
			Page page = getPages();
			FiIncomeVo fiIncomeVo = new  FiIncomeVo();
			List <Map>list =page.getResultMap();
			if(list.size()!=0){
				for(Map totalMap : list ){
					fiIncomeVo.setSourceData("<B>汇总</B>");
					fiIncomeVo.setTherAmount(totalMap.get("THER_AMOUNT")==null?0:Double.parseDouble(totalMap.get("THER_AMOUNT")+""));
					fiIncomeVo.setYfdsAmount(totalMap.get("YFDS_AMOUNT")==null?0:Double.parseDouble(totalMap.get("YFDS_AMOUNT")+""));
					fiIncomeVo.setYfzzAmount(totalMap.get("YFZZ_AMOUNT")==null?0:Double.parseDouble(totalMap.get("YFZZ_AMOUNT")+""));
					fiIncomeVo.setDfdsAmount(totalMap.get("DFDS_AMOUNT")==null?0:Double.parseDouble(totalMap.get("DFDS_AMOUNT")+""));
					fiIncomeVo.setDfzzAmount(totalMap.get("DFZZ_AMOUNT")==null?0:Double.parseDouble(totalMap.get("DFZZ_AMOUNT")+""));
					
					fiIncomeVo.setYfzcAmount(totalMap.get("DFZC_AMOUNT")==null?0:Double.parseDouble(totalMap.get("DFZC_AMOUNT")+""));
					fiIncomeVo.setDfzcAmount(totalMap.get("YFZC_AMOUNT")==null?0:Double.parseDouble(totalMap.get("YFZC_AMOUNT")+""));
					
					fiIncomeVo.setDfzzAmount(totalMap.get("DFZZ_AMOUNT")==null?0:Double.parseDouble(totalMap.get("DFZZ_AMOUNT")+""));
					fiIncomeVo.setCqWeight(totalMap.get("CQWEIGHT")==null?0:Double.parseDouble(totalMap.get("CQWEIGHT")+""));
					fiIncomeVo.setPiece(totalMap.get("PIECE")==null?0:Long.parseLong(totalMap.get("PIECE")+""));
				}
			}else{
				fiIncomeVo.setSourceData("<B>汇总</B>");
				fiIncomeVo.setTherAmount(0.00);
				fiIncomeVo.setYfdsAmount(0.00);
				fiIncomeVo.setYfzzAmount( 0.00);
				fiIncomeVo.setDfdsAmount( 0.00);
				fiIncomeVo.setDfzzAmount(0.00 );
				fiIncomeVo.setCqWeight(0.00);
				fiIncomeVo.setYfzcAmount(0.0);
				fiIncomeVo.setDfzcAmount(0.0);
				fiIncomeVo.setPiece(0l);
				
			}
			Struts2Utils.renderJson(fiIncomeVo);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}
	
	//根据交账单号查看收入明细
	public String findFiIncomeDetail() throws Exception{
		try {
			this.setPageConfig();
			Map map=new HashMap<String,Object>();
			map.put("dno",dno);
			map.put("accountId", accountId);
			map.put("costType", costType);
			this.fiIncomeService.findFiIncomeDetail(this.getPages(),map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "list";
	}
	
	
	@Override
	protected Object createNewInstance() {
		return new FiIncomeVo();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return fiIncomeService;
	}

	@Override
	public Object getModel() {
		return fiIncomeVo;
	}

	@Override
	public void setModel(Object obj) {
		fiIncomeVo = (FiIncomeVo)obj;
	}


	public String getSelectString() {
		return selectString;
	}


	public void setSelectString(String selectString) {
		this.selectString = selectString;
	}


	public Date getStartTime() {
		return startTime;
	}


	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}


	public Date getEndTime() {
		return endTime;
	}


	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}


	public Long getDno() {
		return dno;
	}


	public void setDno(Long dno) {
		this.dno = dno;
	}


	public Long getDepartId() {
		return departId;
	}


	public void setDepartId(Long departId) {
		this.departId = departId;
	}

	
	public String getServiceCode() {
		return serviceCode;
	}


	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}



	public String getCertiNo() {
		return certiNo;
	}


	public void setCertiNo(String certiNo) {
		this.certiNo = certiNo;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public String getCostType() {
		return costType;
	}

	public void setCostType(String costType) {
		this.costType = costType;
	}

	
	
}

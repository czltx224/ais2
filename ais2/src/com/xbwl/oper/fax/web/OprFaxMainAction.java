package com.xbwl.oper.fax.web;

import java.io.Reader;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hibernate.lob.SerializableClob;
import org.ralasafe.WebRalasafe;
import org.ralasafe.user.User;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.xbwl.common.service.IBaseService;
import com.xbwl.common.utils.DoubleUtil;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.entity.OprFaxMain;
import com.xbwl.oper.fax.service.IOprFaxMainService;

/**
 * @author CaoZhili
 * time Aug 29, 2011 10:32:05 AM
 */
@Controller
@Action("oprFaxMainAction")
@Scope("prototype")
@Namespace("/fax")
@Results( {
		@Result(name = "reload", type = "json", params = { "root","validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages","includeProperties", "*" }) })
public class OprFaxMainAction extends SimpleActionSupport {
	
	@Resource(name = "oprFaxMainServiceImpl")
	private IOprFaxMainService oprFaxMainService;
	
	private OprFaxMain oprFaxMain;
	
	private String flightMainNo;
	private Date startDate;
	private Date  endDate;
	private Long piece;
	private Double	weight;
	private Long 	xvalue;
	private String flightNo;
	private Long type;
	
	public String findAll(){
		try {
			if(flightMainNo!=null){
				flightMainNo.trim();
			}
			setPageConfig();
			Map map=new HashMap();
			map.put("flightMainNo",flightMainNo);
			map.put("startDate",startDate);
			map.put("endDate",endDate);
			map.put("piece", piece);
			map.put("weight", weight);
			map.put("xvalue", xvalue);
			map.put("type", type);
			map.put("flightNO", flightNo);
			
			User user= WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
			Long bussDepartId=Long.parseLong(user.get("bussDepart")+"");
			
			Map filterMap=new HashMap();
			filterMap.put("startDate",startDate);
			filterMap.put("endDate",endDate);
			filterMap.put("piece", piece);
			filterMap.put("weight", weight);
			filterMap.put("xvalue", xvalue);  
			filterMap.put("bussDepart", bussDepartId);
			if(type!=null){
				filterMap.put("flightNo","%"+flightNo+"%");
				filterMap.put("flightMainNo","%"+ flightMainNo+"%");
			}else{
				if(piece==null&&weight==null){
					filterMap.put("flightMainNo",flightMainNo);
				}else{
					if (xvalue==null) {
						filterMap.put("flightMainNo",flightMainNo);
					}else{
						filterMap.put("flightMainNo","%"+ flightMainNo+"%");
						filterMap.put("piece1", piece-xvalue);
						filterMap.put("piece2", piece+xvalue);
						filterMap.put("weight1", DoubleUtil.sub(weight, xvalue));
						filterMap.put("weight2",  DoubleUtil.add(weight, xvalue));
					}
				}
			}
			String sqlString =oprFaxMainService.findFiDeliverycost(map);
			oprFaxMainService.getPageBySqlMap(getPages(), sqlString, filterMap);
			List<Map>list  = getPages().getResultMap();
			for(Map mapist :list ){
				SerializableClob clob=(SerializableClob)mapist.get("T1_FLIGHT_NO");
				mapist.put("T1_FLIGHT_NO", clob2String(clob));
				SerializableClob clob2=(SerializableClob)mapist.get("T1_FLIGHT_DATE");
				mapist.put("T1_FLIGHT_DATE", clob2String(clob2));
				SerializableClob clob3=(SerializableClob)mapist.get("T1_CP_NAME");
				mapist.put("T1_CP_NAME", clob2String(clob3));
				SerializableClob clob4=(SerializableClob)mapist.get("T2_CUS_NAME");
				mapist.put("T2_CUS_NAME", clob2String(clob4));
			}
			 getPages().setResultMap(list);
			super.getValidateInfo().setSuccess(true);
			super.getValidateInfo().setMsg("查询操作成功");
		} catch (Exception e) {
			   e.printStackTrace();
			   logger.error("操作出错");
			   super.getValidateInfo().setSuccess(false);
			   super.getValidateInfo().setMsg("操作失败,失败原因:"+e.getLocalizedMessage());
		}
		return  "list";
	}
	
	public final static String clob2String(SerializableClob clob)throws Exception{
	    if (clob ==null ){
	      return "";
	    }
	    StringBuffer sb = new StringBuffer(65535);//64K
	    Reader clobStream = null;
	    try{
		      clobStream = clob.getCharacterStream();
		      char[] b = new char[60000];//每次获取60K
		      int i = 0;
		      while((i = clobStream.read(b)) != -1){
		        sb.append(b,0,i);
		      }
	    }catch(Exception ex){
	       sb = null;
	    }finally{
		      try{
		        if (clobStream != null)
		          clobStream.close();
		      }catch (Exception e){
		      }
	    }
	    if (sb == null){
	      return "";
	    }else{
	      return sb.toString();
	    }
	  }

	
	@Override
	protected Object createNewInstance() {
		return new OprFaxMain();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return this.oprFaxMainService;
	}

	@Override
	public Object getModel() {
		return oprFaxMain;
	}

	@Override
	public void setModel(Object obj) {
		this.oprFaxMain=(OprFaxMain)obj;
	}


	public String getFlightMainNo() {
		return flightMainNo;
	}


	public void setFlightMainNo(String flightMainNo) {
		this.flightMainNo = flightMainNo;
	}


	public Date getStartDate() {
		return startDate;
	}


	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}


	public Date getEndDate() {
		return endDate;
	}


	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	
	public Long getPiece() {
		return piece;
	}

	public void setPiece(Long piece) {
		this.piece = piece;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Long getXvalue() {
		return xvalue;
	}

	public void setXvalue(Long xvalue) {
		this.xvalue = xvalue;
	}

	public String getFlightNo() {
		return flightNo;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}
}

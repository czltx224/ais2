package com.xbwl.report.print.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.ralasafe.WebRalasafe;
import org.ralasafe.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.utils.DoubleUtil;
import com.xbwl.entity.FiTransitcost;
import com.xbwl.entity.OprFaxIn;
import com.xbwl.finance.Service.IFiTransitcostService;
import com.xbwl.oper.fax.service.IOprFaxInService;
import com.xbwl.report.print.bean.BillLadingList;
import com.xbwl.report.print.bean.FiTrainCostBean;
import com.xbwl.report.print.bean.PrintBean;
import com.xbwl.report.print.printServiceInterface.IPrintServiceInterface;

/**
 * 中转成本打印
 * author shuw
 * time May 14, 2012 10:12:45 AM
 */
@Service("fiTrainCostPrintServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class FiTrainCostPrintServiceImpl  implements IPrintServiceInterface{

	@Resource(name = "fiTransitcostServiceImpl")
	private IFiTransitcostService fiTransitService;
	
	@Resource(name = "oprFaxInServiceImpl")
	private IOprFaxInService oprFaxInService;
	
	public void afterPrint(PrintBean bean) {
		
	}

	public List<? extends PrintBean> setPrintBeanList(BillLadingList mainbill,
			Map<String, String> map) {
		List<FiTrainCostBean> list = null;
		try {
			list = doPrintBeanList(mainbill, map);
		} catch (Exception e) {
			mainbill.setMsg("单据打印失败！");
			e.printStackTrace();
		}
		return list;
	}

	public List<FiTrainCostBean> doPrintBeanList(BillLadingList mainbill,Map<String, String> map) throws Exception{
		User user= WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
		String ids = map.get("ids");
		List<FiTrainCostBean> list = new ArrayList<FiTrainCostBean>();
        String[] idsValue = ids.split("\\,");
        double totalWeight=0.0;
        double totalAmount=0.0;
        String dateString=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        long totalPiece=0;
        for (String delId : idsValue) {
        	FiTransitcost fiTransitcost=fiTransitService.get(Long.valueOf(delId));
        	OprFaxIn ofIn=oprFaxInService.get(fiTransitcost.getDno());
        	FiTrainCostBean fiTraBean=new FiTrainCostBean();
        	
        	fiTraBean.setDno(fiTransitcost.getDno());
        	fiTraBean.setPrintName(user.get("name")+"");
        	fiTraBean.setPrintDateString(dateString);
        	fiTraBean.setBatchNo(fiTransitcost.getBatchNo()+"");
        	fiTraBean.setPiece(ofIn.getPiece());
        	fiTraBean.setCustomer(fiTransitcost.getCustomerName());
        	fiTraBean.setWeight(ofIn.getCqWeight());
        	fiTraBean.setAmount(fiTransitcost.getAmount());
        	fiTraBean.setSourceData(fiTransitcost.getSourceData());
        	fiTraBean.setSourceNo(fiTransitcost.getSourceNo()==null?"":fiTransitcost.getSourceNo()+"");
        	fiTraBean.setConsigneeInfo(new StringBuffer(500).append(ofIn.getConsignee()).append("/").append(ofIn.getAddr()).toString());
        
        	if(!isRepeatDno(list, fiTraBean.getDno())){
        		totalPiece+=ofIn.getPiece();
        		totalWeight=DoubleUtil.add(totalWeight, ofIn.getCqWeight());
        	}
        	totalAmount=DoubleUtil.add(totalAmount, fiTransitcost.getAmount());
        	list.add(fiTraBean	);
        }
        
        for (FiTrainCostBean fiTraBean : list) {
        	fiTraBean.setTotalPiece(totalPiece);
        	fiTraBean.setTotalWeight(totalWeight);
        	fiTraBean.setTotalAmount(totalAmount);
        }
        
		return list;
	}

	//判断什么配送单号是否存在
	public boolean isRepeatDno(List<FiTrainCostBean>list ,Long dno){
		for(FiTrainCostBean  fBean:list){
			if(fBean.getDno().equals(dno)){
				return true;
			}
		}
		return false;
	}
}

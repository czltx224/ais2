package com.xbwl.report.print.service.impl;

import java.text.DateFormat;
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

import com.xbwl.common.exception.ServiceException;
import com.xbwl.entity.FiRepayment;
import com.xbwl.finance.Service.IFiRepaymentService;
import com.xbwl.report.print.bean.BillLadingList;
import com.xbwl.report.print.bean.FiReconAccountBean;
import com.xbwl.report.print.bean.FiRepaymentPrint;
import com.xbwl.report.print.bean.PrintBean;
import com.xbwl.report.print.printServiceInterface.IPrintServiceInterface;

/**
 * author shuw
 * time May 18, 2012 5:10:18 PM
 */
@Service("fiRepaymentPrintServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class FiRepaymentPrintServiceImpl  implements IPrintServiceInterface{

	@Resource(name="fiRepaymentServiceImpl")
	private IFiRepaymentService fiRepaymentService;
	
	public void afterPrint(PrintBean bean) {}

	public List<? extends PrintBean> setPrintBeanList(BillLadingList mainbill,
			Map<String, String> map) {
		List<FiRepaymentPrint> list = null;
		try {
			list = doPrintBeanList(mainbill, map);
		} catch (Exception e) {
			mainbill.setMsg(e.getMessage());
			e.printStackTrace();
		}
		return list;
	}

	private List<FiRepaymentPrint> doPrintBeanList(BillLadingList mainbill,Map<String, String> map)  throws Exception{
		String batchNo = map.get("batchNo");
		User user= WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
		String userName=user.get("name").toString();
		
		if(batchNo==null||"".equals(batchNo)){
			throw new ServiceException("对账单号不能为空");
		}
		List<FiRepaymentPrint> list = new ArrayList<FiRepaymentPrint>();
		
		 String  dateString=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		 DateFormat df =new SimpleDateFormat("yyyy-MM-dd");
		 
		List<FiRepayment>listRe =fiRepaymentService.find("from FiRepayment f where f.batchNo=?  and f.status=1  ", Long.valueOf(batchNo));
		
		if(listRe.size()==0){
			throw new ServiceException("无数据需要打印");
		}
		StringBuffer sBuffer=new StringBuffer(500).append(listRe.get(0).getDepartName());
		
		 Date minDate= new Date();
		 Date maxDate = new Date();
		for(FiRepayment firep:listRe){
			FiRepaymentPrint firepay=new FiRepaymentPrint();
			firepay.setPrintName(userName);
			firepay.setPrintTime(dateString);
			firepay.setAccountDataString(firep.getAccountData()==null?"":df.format(firep.getAccountData()) );
			firepay.setPrintHead("");
			if(firep.getAccountData()!=null){
				if(firep.getAccountData().getTime()<minDate.getTime()){
					minDate=firep.getAccountData();
				}
				
				if(firep.getAccountData().getTime()>maxDate.getTime()){
					maxDate=firep.getAccountData();
				}
			}
			firepay.setBatchNo(firep.getBatchNo());
			firepay.setAccountsBalance(firep.getAccountsBalance());
			firepay.setCustomerName(firep.getCustomerName());
			firepay.setDepartName(firep.getDepartName());
			firepay.setEliminationAccounts(firep.getEliminationAccounts()==null?0.0:firep.getEliminationAccounts());
			firepay.setEliminationCope(firep.getEliminationCope());
			firepay.setProblemAmount(firep.getProblemAmount()==null?0.0:firep.getProblemAmount());
			firepay.setSourceData(firep.getSourceData());
			firepay.setId(firep.getId());
			firepay.setSourceNo(firep.getSourceNo()+"");
			
			list.add(firepay);
		}
		sBuffer.append(df.format(minDate)).append("至")
		.append(df.format(maxDate)).append("代理还款交账报表");
		
		String printHead=sBuffer.toString();
		for(FiRepaymentPrint firepay:list){
			firepay.setPrintHead(printHead);
		}

		return list;
	}

}

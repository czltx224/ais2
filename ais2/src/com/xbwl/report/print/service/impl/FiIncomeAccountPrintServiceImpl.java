package com.xbwl.report.print.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.logicalcobwebs.concurrent.FJTask;
import org.ralasafe.WebRalasafe;
import org.ralasafe.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.utils.DoubleChangeUtil;
import com.xbwl.entity.BasDictionaryDetail;
import com.xbwl.entity.FiCapitaaccountset;
import com.xbwl.entity.FiFundstransfer;
import com.xbwl.entity.FiIncomeAccount;
import com.xbwl.finance.Service.IFiIncomeAccountService;
import com.xbwl.report.print.bean.BillLadingList;
import com.xbwl.report.print.bean.FiFundstransferBean;
import com.xbwl.report.print.bean.FiIncomeAccountPrint;
import com.xbwl.report.print.bean.PrintBean;
import com.xbwl.report.print.printServiceInterface.IPrintServiceInterface;

/**
 * 收入收银报表打印
 * author shuw
 * time May 18, 2012 9:30:43 AM
 */
@Service("fiIncomeAccountPrintServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class FiIncomeAccountPrintServiceImpl implements IPrintServiceInterface{

	@Resource(name="fiIncomeAccountServiceImpl")
	private IFiIncomeAccountService fiIncomeAccountService;
	
	public void afterPrint(PrintBean bean) {
		
	}

	public List<? extends PrintBean> setPrintBeanList(BillLadingList mainbill,
			Map<String, String> map) {
		List<PrintBean> list = null;
		try {
			list = doPrintBeanList(mainbill, map);
		} catch (Exception e) {
			mainbill.setMsg("资金交接单打印失败！");
			e.printStackTrace();
		}
		return list;
	}
	
	public List<PrintBean> doPrintBeanList(BillLadingList mainbill,Map<String, String> map) throws Exception{
		String id = map.get("accountId");
		User user = WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
		List<PrintBean> list = new ArrayList<PrintBean>();
		if(id==null||"".equals(id)){
			mainbill.setMsg("打印时交账单号不能为空");
			return list;
		}
		List<FiIncomeAccount> listAccount =fiIncomeAccountService.find("from FiIncomeAccount f where f.batchNo=? and f.status=1 ",Long.valueOf(id));
		if(listAccount.size()==0){
			mainbill.setMsg("无数据需要打印");
			return list;
		}
		StringBuffer printHead=new StringBuffer(500);
		DateFormat dFormat= new SimpleDateFormat("yyyy-MM-dd");
		DateFormat dFs= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		FiIncomeAccount fiInc =listAccount.get(0);
		printHead.append(fiInc.getDepartName()).append(dFormat.format(fiInc.getAccountData())).append("收入收银交账报表");
		Date date = new Date();
		for(FiIncomeAccount fiinco:listAccount){
			 FiIncomeAccountPrint fiPrint=new FiIncomeAccountPrint();
			 fiPrint.setPrintHead(printHead.toString());
			 fiPrint.setTypeName(fiinco.getTypeName());
			 fiPrint.setCashAmount(fiinco.getCashAmount()==null?0.0:fiinco.getCashAmount());
			 fiPrint.setPosAmount(fiinco.getPosAmount()==null?0.0:fiinco.getPosAmount());
			 fiPrint.setCheckAmount(fiinco.getCheckAmount()==null?0.0:fiinco.getCheckAmount());
			 fiPrint.setIntecollectionAmount(fiinco.getIntecollectionAmount()==null?0.0:fiinco.getIntecollectionAmount());
			 fiPrint.setEliminationAmount(fiinco.getEliminationAmount()==null?0.0:fiinco.getEliminationAmount());
			 fiPrint.setConsigneeAmount(fiinco.getConsigneeAmount()==null?0.0:fiinco.getConsigneeAmount());
			 fiPrint.setCollectionAmount(fiinco.getCollectionAmount()==null?0.0:fiinco.getCollectionAmount());
			 fiPrint.setCpFee(fiinco.getCpFee()==null?0.0:fiinco.getCpFee());
			 fiPrint.setIncomeAmount(fiinco.getIncomeAmount()==null?0.0:fiinco.getIncomeAmount());
			 fiPrint.setConsigneeFee(fiinco.getConsigneeFee()==null?0.0:fiinco.getConsigneeFee());
			 fiPrint.setAccountData(fiinco.getAccountData()==null?"":dFormat.format(fiinco.getAccountData()));
			 fiPrint.setBatchNo(fiinco.getBatchNo());
			 fiPrint.setPrintName(user.get("name")+"");
			 fiPrint.setPrintTime(dFs.format(date));
			 fiPrint.setAccountStatus(fiinco.getAccountStatus()==1l?"已交账":"未交账");
			 list.add(fiPrint);
		}
		return list;
	}

}

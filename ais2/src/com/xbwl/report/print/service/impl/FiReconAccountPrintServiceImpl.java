package com.xbwl.report.print.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.ralasafe.WebRalasafe;
import org.ralasafe.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.entity.Customer;
import com.xbwl.entity.FiReceivablestatement;
import com.xbwl.entity.FiReconciliationAccount;
import com.xbwl.finance.Service.IFiReceivabledetailService;
import com.xbwl.finance.Service.IFiReceivablestatementService;
import com.xbwl.finance.Service.IFiReconciliationAccountService;
import com.xbwl.oper.fax.service.IOprFaxInService;
import com.xbwl.report.print.bean.BillLadingList;
import com.xbwl.report.print.bean.FiReconAccountBean;
import com.xbwl.report.print.bean.PrintBean;
import com.xbwl.report.print.printServiceInterface.IPrintServiceInterface;
import com.xbwl.sys.service.ICustomerService;

/**
 * author shuw
 * 对账单打印
 * time Dec 7, 2011 6:00:31 PM
 */
@Service("fiReconAccountPrintServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class FiReconAccountPrintServiceImpl implements IPrintServiceInterface{

	@Resource(name = "fiReceivablestatementServiceImpl")
	private IFiReceivablestatementService fiReceivablestatementService;
	
	@Resource(name="fiReconciliationAccountServiceImpl")
	private IFiReconciliationAccountService fiReconciliationAccountService;
	
	@Resource(name = "fiReceivabledetailServiceImpl")
	private IFiReceivabledetailService fiReceivabledetailService;  //对账往来明细
	
	@Resource(name = "customerServiceImpl")
	private ICustomerService customerService;
	
	@Resource(name = "oprFaxInServiceImpl")
	private IOprFaxInService oprFaxInService;

	public void afterPrint(PrintBean bean) {
		
	}

	public List<FiReconAccountBean> setPrintBeanList(BillLadingList mainbill,
			Map<String, String> map) {
		List<FiReconAccountBean> list = null;
		try {
			list = doPrintBeanList(mainbill, map);
		} catch (Exception e) {
			mainbill.setMsg("对账单打印失败！");
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * @param mainbill
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<FiReconAccountBean> doPrintBeanList(BillLadingList mainbill,Map<String, String> map) throws Exception{
		User user= WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
		Long bussDepartId=Long.parseLong(user.get("bussDepart")+"");
		String id = map.get("rid");
		List<FiReconAccountBean> list = new ArrayList<FiReconAccountBean>();
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
		FiReceivablestatement fiReceivablestatement=fiReceivablestatementService.get(Long.parseLong(id));
		Customer customer=customerService.get(fiReceivablestatement.getCustomerId());
		
		List<FiReconciliationAccount>listFr =fiReconciliationAccountService.find("from FiReconciliationAccount fc where fc.departId=? and fc.isDelete=1 ",bussDepartId);
		FiReconciliationAccount firAccount=null;
		if(listFr.size()==0){
			mainbill.setMsg("请设置对账打印账号");
			return list;
		}else{
				firAccount=listFr.get(listFr.size()-1);
		}

		if(customer==null){
			mainbill.setMsg("对账客商已不存在！");
			return list;
		}
		
		StringBuffer sd= new StringBuffer();
		sd.append(firAccount.getNature()).append("-").append(customer.getCusName()).append("对账单(").
		append(simpleDateFormat.format(fiReceivablestatement.getStateDate())).append("至").
		append(simpleDateFormat.format(fiReceivablestatement.getEndDate())).append(")");

		 /*	StringBuffer sb = new StringBuffer();
		sb.append(" select new com.xbwl.report.print.bean.FiReconAccountBean (o.cpName, o.flightMainNo,")
			.append("o.subNo, o.dno,o.piece,o.cusWeight,")
			.append("o.consignee,o.createTime,f.costType,f.amount ")
			.append(")  from FiReceivabledetail f,OprFaxIn o where f.dno=o.dno and   f.reconciliationNo=?  order by  o.createTime ");*/

		StringBuffer sb =fiReceivabledetailService.getAllReceivabledetailSql();
		Map valueMap=new HashMap<String , Object>();
		valueMap.put("fid", id);
		List listMap =fiReceivabledetailService.createSQLMapQuery(sb.toString(), valueMap).list();
		
		for(int i=0;i<listMap.size();i++){
			Map  iMap=(Map)listMap.get(i);
			FiReconAccountBean fiReconAccountBean =new  FiReconAccountBean();
			fiReconAccountBean.setCustomerPeople(customer.getLinkman1()==null?" ":customer.getLinkman1());
			fiReconAccountBean.setCustomerPhone(customer.getPhone1()==null?" ":customer.getPhone1());
			fiReconAccountBean.setCustomerInfo(customer.getLegalbody()==null?" ":customer.getLegalbody());
			fiReconAccountBean.setCustomerName(customer.getCusName()==null?" ":customer.getCusName());
			fiReconAccountBean.setReconId(Long.parseLong(id));
			
			fiReconAccountBean.setFlightMainNo(iMap.get("FLIGHTMAINNO")==null?" ":iMap.get("FLIGHTMAINNO")+"");
			fiReconAccountBean.setSubNo(iMap.get("SUBNO")==null?" ":iMap.get("SUBNO")+"");
			fiReconAccountBean.setDno(Long.valueOf(iMap.get("DNO")+""));
			fiReconAccountBean.setPiece(Long.valueOf(iMap.get("PIECE")==null?"0":iMap.get("PIECE")+""));
			fiReconAccountBean.setWeight(Double.valueOf(iMap.get("CUSWEIGHT")==null?"0":iMap.get("CUSWEIGHT")+""));
			fiReconAccountBean.setConsignee(iMap.get("CONSIGNEE")+"");
			fiReconAccountBean.setCreateTime(simpleDateFormat.format((Date)iMap.get("CREATETIME")));
			
			fiReconAccountBean.setPaymentAmount(Double.valueOf(iMap.get("PAYMENTAMOUNT")==null?"0":iMap.get("PAYMENTAMOUNT")+""));
			fiReconAccountBean.setCpValueAddFee(Double.valueOf(iMap.get("CPVALUEADDFEE")==null?"0":iMap.get("CPVALUEADDFEE")+""));
			fiReconAccountBean.setCpFee(Double.valueOf(iMap.get("CPFEE")==null?"0":iMap.get("CPFEE")+""));
			fiReconAccountBean.setBulk(Double.valueOf(iMap.get("BULK")==null?"0":iMap.get("BULK")+""));
			
			fiReconAccountBean.setDepartName(sd.toString());
			fiReconAccountBean.setCreateBank(firAccount.getBank()==null?" ":firAccount.getBank());
			fiReconAccountBean.setAccountNum(firAccount.getAccountNum()==null?" ":firAccount.getAccountNum());
			fiReconAccountBean.setAccountName(firAccount.getAccountName()==null?" ":firAccount.getAccountName());
			fiReconAccountBean.setCreateBank2(firAccount.getBank2()==null?" ":firAccount.getBank2());
			fiReconAccountBean.setAccountNum2(firAccount.getAccountNum2()==null?" ":firAccount.getAccountNum2());
			fiReconAccountBean.setAccountName2(firAccount.getAccountName2()==null?" ":firAccount.getAccountName2());
			fiReconAccountBean.setRececonPeople(fiReceivablestatement.getReconciliationUser()==null?" ":fiReceivablestatement.getReconciliationUser());
			fiReconAccountBean.setPhone(firAccount.getPhone()==null?" ":firAccount.getPhone());
			list.add(fiReconAccountBean);
		}

		if (null == id || "".equals(id)) {
			mainbill.setMsg("对账单号为空");
			return list;
		}
		return list;
	}

}

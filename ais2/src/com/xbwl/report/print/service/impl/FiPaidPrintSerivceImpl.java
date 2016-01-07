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

import com.xbwl.finance.dao.IFiPaidDao;
import com.xbwl.report.print.bean.BillLadingList;
import com.xbwl.report.print.bean.FiPaidBean;
import com.xbwl.report.print.bean.PrintBean;
import com.xbwl.report.print.printServiceInterface.IPrintServiceInterface;


@Service("fiPaidPrintSerivceImpl")
@Transactional(rollbackFor = Exception.class)
public class FiPaidPrintSerivceImpl implements IPrintServiceInterface {

	@Resource(name = "fiPaidHibernateDaoImpl")
	private IFiPaidDao fiPaidDao;
	
	public void afterPrint(PrintBean bean) {
		// REVIEW Auto-generated method stub

	}

	public List<PrintBean> setPrintBeanList(BillLadingList mainbill,
			Map<String, String> map) {
		List<PrintBean> list=new ArrayList<PrintBean>();
		StringBuffer sb=new StringBuffer();
		String paidIds = map.get("paidIds");
		
		String last;
		last = paidIds.substring(paidIds.length() - 1, paidIds.length());
		if (last.equals(",")) {
			paidIds = paidIds.substring(0, paidIds.length() - 1);
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		User user = WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
		
		/*sb.append("select fpt.payment_type,fpd.paid_id,fpt.id,fpd.settlement_amount,fpt.cost_type,fpt.documents_smalltype,fpt.documents_no,fpt.cost_type,");
       sb.append("case when nvl(fpt.customer_name,'')='' then nvl(fpt.contacts,'') else nvl(fpt.customer_name,'') end as customer_name,");
       sb.append("case when fpt.documents_smalltype = '配送单' then ofi.piece end as piece,");
       sb.append("case when fpt.documents_smalltype = '配送单' then ofi.cus_weight end as weight,");
       sb.append("case when fpt.documents_smalltype = '配送单' then ofi.bulk end as bulk,");
       sb.append("case when fpt.documents_smalltype = '配送单' then ofi.flight_main_no end as flight_main_no,");
       sb.append("case when fpt.documents_smalltype = '配送单' then ofi.addr end as addr,");
       sb.append("case when fpt.documents_smalltype = '配送单' then ofi.consignee end as consignee,");
       sb.append("case when fpt.documents_smalltype = '配送单' then ofi.consignee_tel end as consignee_tel,");
       sb.append(" fpt.source_data,fpt.source_no");
       sb.append(" from fi_paid fpd left join fi_payment fpt on fpd.fi_payment_id = fpt.id");
       sb.append(" left join opr_fax_in ofi on fpt.documents_no = ofi.d_no");
       sb.append(" where fpd.paid_id in (");
       sb.append(paidIds);
       sb.append(")");*/
	   sb.append("select fpt.payment_type,fpd.paid_id,fpt.documents_no,nvl(ofi.consignee,' ') as consignee,nvl(ofi.consignee_tel,' ') as consignee_tel,nvl(ofi.addr,' ') as addr,nvl(ofi.piece,0) as piece,nvl(ofi.cus_weight,0) as weight,fpt.customer_name,nvl(ofi.flight_main_no,' ') as flight_main_no,");
	   sb.append("to_char(WMSYS.wm_concat(decode(fpt.cost_type,'到付增值费','增值费','到付提送费','到付费','代收货款','代收款',fpt.cost_type))) as cost_type,");
	   sb.append("to_char(WMSYS.wm_concat(fpd.settlement_amount)) as settlement_amount,sum(fpd.settlement_amount) as sum_amount ");
	   sb.append("from fi_paid fpd left join fi_payment fpt on fpd.fi_payment_id = fpt.id ");
	   sb.append("left join opr_fax_in ofi on fpt.documents_no = ofi.d_no where fpd.paid_id in (");
	   sb.append(paidIds);
	   sb.append(") group by fpt.payment_type,fpd.paid_id,fpt.documents_no,ofi.consignee,ofi.consignee_tel,ofi.addr,ofi.piece,ofi.cus_weight,fpt.customer_name,ofi.flight_main_no");
	   
       FiPaidBean bean=null;
       String consignee=null;//收货人姓名
       String consigneeTel=null;//收货人电话
       String add=null; //收货人地址
       String customerName=null;//往来单位
       Long paymentTypeId; //收付类型ID(1:收款单/2:付款单)
       String paymentType;//收付类型
       
	   	Long piece=0L;// 件数
		Double cusWeight=0.0;// 计费重量
		Double bulk=0.0;// 体积
		
       StringBuffer addr=new StringBuffer();
		List<Map> listPaid=this.fiPaidDao.createSQLQuery(sb.toString()).list();
		for(int i=0;i<listPaid.size();i++){
			Map m=listPaid.get(i);
			bean=new FiPaidBean();
			paymentTypeId=Long.valueOf(m.get("PAYMENT_TYPE")+"");
			if(paymentTypeId==1L){
				paymentType="收银报表";
			}else if(paymentTypeId==2L){
				paymentType="付款报表";
			}else{
				paymentType="";
			}
			bean.setPaymentType(paymentType);
			bean.setPaidId(Long.valueOf(m.get("PAID_ID")+""));
			//bean.setDocumentsSmalltype(m.get("DOCUMENTS_SMALLTYPE")+"");
			bean.setDocumentsNo(Long.valueOf(m.get("DOCUMENTS_NO")+""));
			bean.setPiece(Long.valueOf(m.get("PIECE")+""));
			bean.setCusWeight(Double.valueOf(m.get("WEIGHT")+""));
			//bean.setBulk(Double.valueOf(m.get("BULK")+""));
			bean.setFlightMainNo(m.get("FLIGHT_MAIN_NO")+"");
			bean.setSettlementAmount(m.get("SETTLEMENT_AMOUNT")+"");
			bean.setSumAmount(Double.valueOf(m.get("SUM_AMOUNT")+""));
			customerName=m.get("CUSTOMER_NAME")+"";
			bean.setCustomerName("null".equals(customerName)?"":customerName);
			bean.setCostType(m.get("COST_TYPE")+"");
			consignee=m.get("CONSIGNEE")+"";
			if(consignee!=null&&!"".equals(consignee)){
				addr.append(consignee).append("/");
			}
			/*consigneeTel=m.get("CONSIGNEE_TEL")+"";
			if(consigneeTel!=null&&!"".equals(consigneeTel)){
				addr.append(consigneeTel).append("/");
			}*/
			add=m.get("ADDR")+"";
			if(addr!=null&&!"".equals(addr)){
				addr.append(add);
			}
			bean.setAddr(addr.toString());
			addr.delete(0, addr.length());
			//bean.setSourceData(m.get("SOURCE_DATA")+"");
			//bean.setSourceId(Long.valueOf(m.get("SOURCE_NO")+""));
			bean.setPrintName(user.get("name")+"");
			bean.setPrintTime(sdf.format(new Date()));
			bean.setPrintNum(1L);
			list.add(bean);
		}
		return list;
	}

}

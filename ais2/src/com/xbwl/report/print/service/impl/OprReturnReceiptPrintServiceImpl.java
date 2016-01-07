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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.entity.OprFaxIn;
import com.xbwl.entity.OprReceipt;
import com.xbwl.oper.fax.service.IOprFaxInService;
import com.xbwl.oper.receipt.service.IOprReceiptService;
import com.xbwl.report.print.bean.BillLadingList;
import com.xbwl.report.print.bean.OprReturnReceiptBean;
import com.xbwl.report.print.bean.PrintBean;
import com.xbwl.report.print.printServiceInterface.IPrintServiceInterface;

/**
 * @author czl
 * @createTime 2012-03-17 04:35 AM
 *
 * 配送中心返单报表服务处实现类
 */
@Service("oprReturnReceiptPrintServiceImpl")
@Transactional(rollbackFor=Exception.class)
public class OprReturnReceiptPrintServiceImpl implements IPrintServiceInterface {

	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource(name="oprFaxInServiceImpl")
	private IOprFaxInService oprFaxInService;
	
	@Resource(name="oprReceiptServiceImpl")
	private IOprReceiptService oprReceiptService;
	
	public void afterPrint(PrintBean bean) {
		logger.debug("配送中心返单报表打印完毕！");
	}

	public List<? extends PrintBean> setPrintBeanList(BillLadingList mainbill,
			Map<String, String> map) {
		List<PrintBean> list= new ArrayList<PrintBean>();
		
		String dno = map.get("dnos");
		if(null==dno || "".equals(dno)){
			mainbill.setMsg("请传配送单号过来！");
			return list;
		}
		
		User user = WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date dt = new Date();
		String[] dnos= dno.split(",");
		OprReturnReceiptBean bean = null;
		OprReceipt receipt = null;
		OprFaxIn fax = null;
		for(int i=0;i<dnos.length;i++){
			bean = new OprReturnReceiptBean();
			fax = this.oprFaxInService.get(Long.valueOf(dnos[i]));
			receipt = this.oprReceiptService.findUniqueBy("dno",fax.getDno());
			if(null==receipt.getConfirmStatus() || receipt.getConfirmStatus()<1l){//判断是否回单确收  回单确认状态 0：未确认，1：正常，2：异常
				mainbill.setMsg("配送单号为"+receipt.getDno()+"的货物还没有回单确收！");
				return list;
			}
			bean.setConfirmMan(receipt.getConfirmMan()==null?"":receipt.getConfirmMan());
			bean.setConfirmTime(receipt.getConfirmTime()==null?"":sdf.format(receipt.getConfirmTime()));
			bean.setConsigneeInfo(fax.getConsignee()+"/"+fax.getConsigneeTel()+"/"+fax.getAddr());
			bean.setDno(fax.getDno()==null?"":fax.getDno()+"");
			bean.setFlightMainNo(fax.getFlightMainNo()==null?"":fax.getFlightMainNo());
			bean.setPiece(fax.getPiece()==null?"0":fax.getPiece()+"");
			bean.setWeight(fax.getCusWeight()==null?"0":fax.getCusWeight()+"");
			bean.setPrintTitle("配送中心返单报表！");
			bean.setReturnName("");//返单部门/网点/人
			bean.setReturnNum(receipt.getConfirmNum()==null?"0":receipt.getConfirmNum()+"");
			//无原件默认为一份，有原件则为1+原件份数
			if(receipt.getReceiptType().indexOf("原件")>0){
				bean.setSignNum(receipt.getReachNum()==null?"1":receipt.getReachNum()+1+"");
			}else{
				bean.setSignNum("1");
			}
			bean.setSubNo(fax.getSubNo()==null?"":fax.getSubNo());
			
			bean.setPrintId("");
			bean.setPrintName(user.get("name")+"");
			bean.setPrintTime(sdf.format(dt));
			bean.setPrintNum(1l);
			bean.setSourceNo(fax.getDno()+"");
			
			list.add(bean);
		}
		
		return list;
	}

}

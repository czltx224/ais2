package com.xbwl.report.print.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.ralasafe.WebRalasafe;
import org.ralasafe.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.utils.DoubleChangeUtil;
import com.xbwl.entity.FiPaid;
import com.xbwl.entity.FiReceipt;
import com.xbwl.finance.Service.IFiPaidService;
import com.xbwl.finance.Service.IFiReceiptService;
import com.xbwl.report.print.bean.BillLadingList;
import com.xbwl.report.print.bean.FiReceiptBean;
import com.xbwl.report.print.bean.PrintBean;
import com.xbwl.report.print.printServiceInterface.IPrintServiceInterface;

/**
 * author shuw
 * �վݴ�ӡ
 * time Dec 6, 2011 2:24:00 PM
 */
@Service("fiReceiptPrintServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class FiReceiptPrintServiceImpl implements IPrintServiceInterface{

	@Resource(name="fiReceiptServiceImpl")
	private IFiReceiptService fiReceiptService;

	@Resource(name = "fiPaidServiceImpl")
	private IFiPaidService fiPaidService;
	
	@Value("${print_receipt_addr_url}")
	private String printPhoto ;
	
	public void afterPrint(PrintBean bean) {
		FiReceiptBean fben=(FiReceiptBean)bean;
		User user = WebRalasafe.getCurrentUser(ServletActionContext.getRequest());  //��д�վ���
		FiReceipt fiReceipt= fiReceiptService.get(fben.getReceiptId());
		fiReceipt.setPrintDate(new Date());
		fiReceipt.setPrintName(user.get("name")+"");
		long num=fiReceipt.getPrintNum()==null?0l:fiReceipt.getPrintNum();
		num=num+1;
		fiReceipt.setPrintNum(num);  //��ӡһ�Σ�������һ��
		fiReceipt.setReceiptNo(fben.getPrintReceiptAccountNo());
	}

	public List<PrintBean> setPrintBeanList(BillLadingList mainbill,
			Map<String, String> map) {
		List<PrintBean> list = null;
		try {
			list = doPrintBeanList(mainbill, map);
		} catch (Exception e) {
			mainbill.setMsg("�վݴ�ӡʧ�ܣ�");
			e.printStackTrace();
		}
		return list;
	}

	public List<PrintBean> doPrintBeanList(BillLadingList mainbill,Map<String, String> map) throws Exception{
		String id = map.get("fiReceiptId");
		List<PrintBean> list = new ArrayList<PrintBean>();
		if (null == id || "".equals(id)) {
			mainbill.setMsg("�վݵ��Ų���Ϊ��");
			return list;
		}
		FiReceipt fiReceipt= fiReceiptService.get(Long.parseLong(id));
		FiReceiptBean fBean=new FiReceiptBean();
		fBean.setDoName(fiReceipt.getCreateName()==null?"":fiReceipt.getCreateName());
		fBean.setAmount(fiReceipt.getAmount()+"");
		fBean.setPrintReceiptAccountNo(fiReceipt.getId()+"");
		fBean.setPrintPhoto(printPhoto);
		
		Calendar cal=Calendar.getInstance();
		cal.setTime(fiReceipt.getReceiptData()==null?new Date():fiReceipt.getReceiptData());
		StringBuffer sb = new StringBuffer();
		sb.append(cal.get(Calendar.YEAR)).append("��")
		 	 .append(cal.get(Calendar.MONTH)+1).append("��")
		 	 .append(cal.get(Calendar.DAY_OF_MONTH)).append("��");
		fBean.setReceiptData(sb.toString());  //ƴ�������ַ���

		String remark=fiReceipt.getRemark();
		if(remark.length()>32&&remark.length()<64){
			fBean.setCreataRemark(remark.substring(0, 31));
			fBean.setCreataRemark2(remark.substring(31,remark.length()));
			fBean.setCreataRemark3("");
		}else if(remark.length()>=70){
			fBean.setCreataRemark(remark.substring(0, 31));
			fBean.setCreataRemark2(remark.substring(31,70));
			fBean.setCreataRemark3(remark.substring(70,remark.length()));
		}else{
			fBean.setCreataRemark(remark);
			fBean.setCreataRemark2("");
			fBean.setCreataRemark3("");
		}
		
		fBean.setReceiptId(fiReceipt.getId());
		fBean.setMaxAmount(DoubleChangeUtil.digitUppercase(fiReceipt.getAmount()));
		Long pid=fiReceipt.getFiPaidId();
		if(pid==null){
			mainbill.setMsg("�Ҳ�����Ӧ������");
			return list;
		}
		FiPaid fPaid=fiPaidService.get(pid);
		if(fPaid!=null){            //�������� �ж�
			String type = fPaid.getPenyJenis();
			if("�ֽ�".equals(type)){
				fBean.setCashStatus("1");
			}else if("֧Ʊ".equals(type)){
				fBean.setCashStatus("3");
			}else if("����".equals(type)){
				fBean.setCashStatus("2");
			}else{
				fBean.setCashStatus("4");
			}
		}else{
			mainbill.setMsg("�Ҳ�����Ӧ������");
			return list;
		}
		list.add(fBean);
		return list;
	}

	public String getPrintPhoto() {
		return printPhoto;
	}

	public void setPrintPhoto(String printPhoto) {
		this.printPhoto = printPhoto;
	}

}

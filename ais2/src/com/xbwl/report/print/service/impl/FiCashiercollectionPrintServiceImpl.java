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
import com.xbwl.entity.FiCashiercollection;
import com.xbwl.entity.FiReceipt;
import com.xbwl.finance.Service.IFiCashiercollectionService;
import com.xbwl.finance.Service.IFiReceiptService;
import com.xbwl.report.print.bean.BillLadingList;
import com.xbwl.report.print.bean.FiReceiptBean;
import com.xbwl.report.print.bean.PrintBean;
import com.xbwl.report.print.printServiceInterface.IPrintServiceInterface;

@Service("fiCashiercollectionPrintServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class FiCashiercollectionPrintServiceImpl implements
		IPrintServiceInterface {
	@Resource(name="fiCashiercollectionServiceImpl")
	private IFiCashiercollectionService fiCashiercollectionService;
	
	@Resource(name="fiReceiptServiceImpl")
	private IFiReceiptService fiReceiptService;
	
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
		List<PrintBean> list = new ArrayList<PrintBean>();
		String ids=map.get("ids");
		if (null == ids || "".equals(ids)) {
			mainbill.setMsg("�����տ����Ϊ��");
			return null;
		}
		

		FiCashiercollection fiCashiercollection=this.fiCashiercollectionService.get(Long.valueOf(ids));//�����տ
		if(fiCashiercollection.getVerificationStatus()!=1L){
			mainbill.setMsg("�����տ������������ӡ!");
			return null;
		}
			
		FiReceipt fiReceipt=null;
		
		//����վݺŲ������Զ�����һ���վ�
		if(fiCashiercollection.getFiReceiptId()==null){
			fiReceipt=new FiReceipt();
			fiReceipt.setAmount(fiCashiercollection.getCollectionAmount());
			fiReceipt.setReceiptData(new Date());
			fiReceipt.setRemark(fiCashiercollection.getVerificationRemark());
			fiReceipt.setSourceData("�����տ");
			fiReceipt.setSourceNo(fiCashiercollection.getId());
			this.fiReceiptService.save(fiReceipt);
			fiCashiercollection.setFiReceiptId(fiReceipt.getId());
			this.fiCashiercollectionService.save(fiCashiercollection);
		}else{
			fiReceipt=this.fiReceiptService.get(fiCashiercollection.getFiReceiptId());
		}
		
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
		if(remark==null){
			fBean.setCreataRemark(remark);
			fBean.setCreataRemark2("");
			fBean.setCreataRemark3("");
		}else if(remark.length()>32&&remark.length()<64){
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
		if(fiCashiercollection!=null){            //�������� �ж�
			String type = fiCashiercollection.getPenyJenis();
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

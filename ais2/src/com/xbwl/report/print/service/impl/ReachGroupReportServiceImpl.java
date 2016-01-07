package com.xbwl.report.print.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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

import com.xbwl.entity.BasCar;
import com.xbwl.entity.Customer;
import com.xbwl.entity.OprException;
import com.xbwl.entity.OprFaxIn;
import com.xbwl.entity.OprOvermemo;
import com.xbwl.entity.OprOvermemoDetail;
import com.xbwl.entity.OprRemark;
import com.xbwl.entity.OprRequestDo;
import com.xbwl.oper.exception.service.IOprExceptionService;
import com.xbwl.oper.fax.service.IOprFaxInService;
import com.xbwl.oper.stock.service.IOprOvermemoDetailService;
import com.xbwl.oper.stock.service.IOprOvermemoService;
import com.xbwl.oper.stock.service.IOprRemarkService;
import com.xbwl.oper.stock.service.IOprRequestDoService;
import com.xbwl.report.print.bean.BillLadingList;
import com.xbwl.report.print.bean.PrintBean;
import com.xbwl.report.print.bean.ReachGroupReportBean;
import com.xbwl.report.print.printServiceInterface.IPrintServiceInterface;
import com.xbwl.sys.service.IBasCarService;
import com.xbwl.sys.service.ICustomerService;

/**
 * author CaoZhili
 * time Nov 3, 2011 5:17:23 PM
 * ����嵥�����ʵ����
 */
@Service("reachGroupReportServiceImpl")
@Transactional(rollbackFor=Exception.class)
public class ReachGroupReportServiceImpl implements IPrintServiceInterface{

	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource(name = "oprOvermemoServiceImpl")
	private IOprOvermemoService oprOvermemoService;
	
	@Resource(name="oprOvermemoDetailServiceImpl")
	private IOprOvermemoDetailService oprOvermemoDetailService;
	
	@Resource(name = "oprFaxInServiceImpl")
	private IOprFaxInService oprFaxInService;
	
	@Resource(name="basCarServiceImpl")
	private IBasCarService basCarService;
	
	@Resource(name = "oprRemarkServiceImpl")
	private IOprRemarkService oprRemarkService;

	@Resource(name = "oprExceptionServiceImpl")
	private IOprExceptionService oprExceptionService;
	
	@Resource(name="oprRequestDoServiceImpl")
	private IOprRequestDoService oprRequestDoService;
	
	@Resource(name="customerServiceImpl")
	private ICustomerService customerService;
	
	public static final String requestStage="���׶�";
	
	public void afterPrint(PrintBean bean) {
		logger.debug("����嵥��ӡ��ϣ�");
	}

	public List<PrintBean> setPrintBeanList(BillLadingList mainbill,
			Map<String, String> map) {
		List<PrintBean> list = null;
		try{
			list =  doPrintBeanList(mainbill,map);
		}catch (Exception e) {
			mainbill.setMsg("����嵥��ӡʧ�ܣ�");
			e.printStackTrace();
		}
		return list;
	}

	private List<PrintBean> doPrintBeanList(BillLadingList mainbill,
			Map<String, String> map) throws Exception{
		List<PrintBean> list = new ArrayList<PrintBean>();
		Integer subNum=5;
		
		String overmemoId = map.get("overmemoId");
		if (null == overmemoId || "".equals(overmemoId)) {
			mainbill.setMsg("���ӵ���Ϊ�գ�");
			return list;
		}
		OprOvermemo overmemo = this.oprOvermemoService.get(Long
				.valueOf(overmemoId));
		ReachGroupReportBean bean = null;
		OprFaxIn fax = null;
		OprOvermemoDetail detail = null;
		List<OprRemark> remarkList = null;
		List<OprException> exList = null;
		BasCar car = null;
		Customer customer=null;
		List<OprRequestDo> requestList=null;
		Date dt = new Date();
		List<OprOvermemoDetail> odetaiList = null;
		User user = WebRalasafe.getCurrentUser(ServletActionContext
				.getRequest());
		odetaiList =  this.oprOvermemoDetailService.find("from OprOvermemoDetail where oprOvermemo.id=?"+(overmemo.getOrderfields()==null?"":(" order by "+overmemo.getOrderfields())),overmemo.getId());
		Iterator<OprOvermemoDetail> itr = odetaiList.iterator();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		while (itr.hasNext()) {
			detail = itr.next();
			bean=new ReachGroupReportBean();
			fax = this.oprFaxInService.get(Long.valueOf(detail.getDno()));
			car =  this.basCarService.get(overmemo.getCarId());
			if(null!=car){
				bean.setCarCode(car.getCarCode()==null?"":car.getCarCode());
			}
			bean.setOvermemoNo(overmemoId);
			bean.setConsigneeInfo(fax.getConsignee() + "/"
					+ fax.getConsigneeTel() + "/" + fax.getAddr());// �ջ�����Ϣ
			bean.setCqName(fax.getCpName() == null ? "" : fax.getCpName());// ��������
			bean.setDno(fax.getDno() == null ? "" : fax.getDno() + "");// ���͵���
			bean.setTotalPiece(overmemo.getTotalPiece() + "��");
			bean.setTotalWeight(overmemo.getTotalWeight() + "����");
			bean.setTotalTicket(overmemo.getTotalTicket() + "Ʊ");
			bean.setPiece(detail.getPiece() == null ? "0" : detail.getPiece()
					+ "");
			bean.setRealPiece(detail.getRealPiece() == null ? "0" : detail
					.getRealPiece()
					+ "");
			bean.setWeight(detail.getWeight() == null ? "0" : detail.getWeight()
					+ "");
			bean.setSubNo(detail.getSubNo() == null ? "" : detail.getSubNo());
			bean.setFlightMainNo(detail.getFlightMainNo() == null ? "" : detail
					.getFlightMainNo());
			bean.setIsNotReceipt("��");
			if(null!=fax.getReceiptType() && (fax.getReceiptType().equals("�ͻ�ԭ������") || fax.getReceiptType().equals("ָ��ԭ������"))){
				bean.setIsNotReceipt("��");
			}
			bean.setReceiptType(fax.getReceiptType()==null?"":fax.getReceiptType());//���ûص�����
			bean.setFlightNo(fax.getFlightNo()==null?"":fax.getFlightNo());//���ú����
			String remark = "";
			int countNum=0;
			remarkList = this.oprRemarkService.findBy("dno", fax.getDno());
			for (int j = 0; j < remarkList.size(); j++) {
				String single =remarkList.get(j).getRemark();
				if(null!=single && single.length()>0){
					countNum++;
					if(single.length()>subNum){
						single=single.substring(0,subNum);
					}
				}
				remark=(remark.equals("") ? ""
						: (remark + "��"))
						+ ( countNum+ ": "+ single);
			}
			String exception = "";
			countNum=0;
			exList = this.oprExceptionService.findBy("dno", fax.getDno());
			if (null != exList && exList.size() > 0) {
				for (int j = 0; j < exList.size(); j++) {
					String single =exList.get(j).getExceptionDescribe();
					if(null!=single && single.length()>0){
						countNum++;
						if(single.length()>subNum){
							single=single.substring(0,subNum);
						}
					}
					exception=(exception.equals("") ? "" : (exception+ "��"))
					+ ( countNum+ ": "+ single);
				}
			}
			bean.setExceptionRemark(exception+" "+remark);
			
			requestList = this.oprRequestDoService.find("from OprRequestDo where dno=? and requestStage=?", fax.getDno(),ReachGroupReportServiceImpl.requestStage);
			if(null!=requestList && requestList.size()>0){
				bean.setRequest(requestList.get(0).getRequest());
				bean.setIsOpr(requestList.get(0).getIsOpr()==null?"":requestList.get(0).getIsOpr()+"");
			}
			customer = this.customerService.get(fax.getCusId());
			if(null!=customer){
				bean.setCqArea(customer.getPkAreacl()==null?"":customer.getPkAreacl());//���ô������
			}
			bean.setPrintTitle(user.get("rightDepart") + "����嵥");
			
			bean.setSourceNo(fax.getDno()==null?"":fax.getDno()+"");// ���͵���
			bean.setPrintName(user.get("name") + "");// ��ӡ��
			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			bean.setPrintTime(sdf.format(dt));// ��ӡʱ��
			
			list.add(bean);
		}
		return list;
	}

}

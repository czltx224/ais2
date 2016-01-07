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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.entity.OprFaxIn;
import com.xbwl.entity.OprLoadingbrigadeWeight;
import com.xbwl.entity.OprOvermemo;
import com.xbwl.entity.OprOvermemoDetail;
import com.xbwl.entity.OprReceipt;
import com.xbwl.entity.OprRequestDo;
import com.xbwl.entity.SysPrintManager;
import com.xbwl.oper.fax.service.IOprFaxInService;
import com.xbwl.oper.receipt.service.IOprReceiptService;
import com.xbwl.oper.stock.service.IOprLoadingbrigadeWeightService;
import com.xbwl.oper.stock.service.IOprOvermemoDetailService;
import com.xbwl.oper.stock.service.IOprOvermemoService;
import com.xbwl.oper.stock.service.IOprRequestDoService;
import com.xbwl.report.print.bean.BillLadingList;
import com.xbwl.report.print.bean.OvermemoListBean;
import com.xbwl.report.print.bean.PrintBean;
import com.xbwl.report.print.printServiceInterface.IPrintServiceInterface;
import com.xbwl.report.print.service.ISysPrintManagerService;
import com.xbwl.report.print.util.NumberUtil;
import com.xbwl.sys.service.IBasLoadingbrigadeService;

/**
 * author CaoZhili time Oct 28, 2011 9:57:07 AM 
 * ʵ���嵥��ӡ�����ʵ����
 */
@Service("overmemoListServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class OvermemoListServiceImpl implements IPrintServiceInterface {

	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource(name = "oprOvermemoServiceImpl")
	private IOprOvermemoService oprOvermemoService;
	
	@Resource(name="oprOvermemoDetailServiceImpl")
	private IOprOvermemoDetailService oprOvermemoDetailService;

	@Resource(name = "oprFaxInServiceImpl")
	private IOprFaxInService oprFaxInService;

	@Resource(name = "oprReceiptServiceImpl")
	private IOprReceiptService oprReceiptService;

	@Resource(name = "oprLoadingbrigadeWeightServiceImpl")
	private IOprLoadingbrigadeWeightService oprLoadingbrigadeWeightService;

	@Resource(name = "basLoadingbrigadeService")
	private IBasLoadingbrigadeService basLoadingbrigadeService;
	
	@Resource(name="oprRequestDoServiceImpl")
	private IOprRequestDoService oprRequestDoService;

	@Value("${print.overmemoListServiceImpl.overmemoType}")
	private String overmemoType;
	
	@Resource(name = "sysPrintManagerServiceImpl")
	private ISysPrintManagerService sysPrintManagerService;
	
	public void afterPrint(PrintBean bean) {
		logger.debug("ʵ���嵥��ӡ���..............");
	}

	public List<PrintBean> setPrintBeanList(BillLadingList mainbill,
			Map<String, String> map) {
		List<PrintBean> list = new ArrayList<PrintBean>();
		try {
			// list =test();
			list = doPrintBeanList(mainbill, map);
		} catch (Exception e) {
			mainbill.setMsg("ʵ���ӡ�嵥ʧ�ܣ�");
			e.printStackTrace();
		}
		return list;
	}

	private List<PrintBean> doPrintBeanList(BillLadingList mainbill,
			Map<String, String> map) throws Exception {
		List<PrintBean> list = new ArrayList<PrintBean>();
		String overmemoId = map.get("overmemoId");
		if (null == overmemoId || "".equals(overmemoId)) {
			mainbill.setMsg("ʵ�䵥��Ϊ�գ�");
			return list;
			// throw new ServiceException("ʵ�䵥��δ�գ�");
		}
		OprOvermemo overmemo = this.oprOvermemoService.get(Long
				.valueOf(overmemoId));
		OvermemoListBean bean = null;
		OprFaxIn fax = null;
		OprOvermemoDetail detail = null;
		List<OprReceipt> receiptList = null;
		List<OprLoadingbrigadeWeight> loadingList = null;
		List<OprOvermemoDetail> odetaiList = null;
		User user = WebRalasafe.getCurrentUser(ServletActionContext
				.getRequest());
		odetaiList =  this.oprOvermemoDetailService.find("from OprOvermemoDetail where oprOvermemo.id=?"+(overmemo.getOrderfields()==null?"":(" order by "+overmemo.getOrderfields())),overmemo.getId());
		Iterator<OprOvermemoDetail> itr = odetaiList.iterator();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Long printNum = getPrintNum(overmemo);
		List<OprRequestDo> requestList=null;
		while (itr.hasNext()) {
			Double shouru=0d;
			detail = itr.next();
			bean = new OvermemoListBean();
			fax = this.oprFaxInService.get(Long.valueOf(detail.getDno()));
			if (null == fax) {
				mainbill.setMsg("���͵���Ϊ�գ�");
				return list;
			}
			receiptList = this.oprReceiptService.findBy("dno", detail.getDno());
			if (null != receiptList && receiptList.size() > 0) {
				bean.setSignNun(receiptList.get(0).getGetNum() == null ? "0"
						: receiptList.get(0).getGetNum() + "");
			}
			//�������
			shouru = NumberUtil.numberAdd(fax.getConsigneeFee(), fax.getCpFee())//����+Ԥ��
			        +NumberUtil.numberAdd(fax.getCusValueAddFee(), fax.getCpValueAddFee())//������ֵ��+Ԥ����ֵ��
			        +NumberUtil.numberAdd(fax.getSonderzugPrice(), fax.getCpSonderzugPrice());//����ר����+Ԥ��ר����
			bean.setShouru(shouru+"");
			bean.setAutostowMode(overmemo.getOvermemoType() + "ʵ���嵥");
			//bean.setConsigneeFee((fax.getConsigneeFee() + fax.getCpValueAddFee())+ "");
			bean.setConsigneeFee(NumberUtil.numberAdd(fax.getConsigneeFee(), fax.getCusValueAddFee(),fax.getSonderzugPrice())+"");//�������ͷ�+������ֵ��+����ר����
			bean.setCpFee(NumberUtil.numberAdd(fax.getCpFee(), fax.getCpValueAddFee(),fax.getCpSonderzugPrice())+"");//Ԥ�����ͷ�+Ԥ����ֵ��+Ԥ��ר����
			bean.setConsigneeInfo(fax.getConsignee() + "/"
					+ fax.getConsigneeTel() + "/" + fax.getAddr());
			bean.setCqName(fax.getCpName() == null ? "" : fax.getCpName());
			bean.setDno(detail.getDno() == null ? "" : detail.getDno() + "");
		
			bean.setFlightMainNo(detail.getFlightMainNo() == null ? "" : detail
					.getFlightMainNo());
			bean.setOvermemoId(overmemoId == null ? "" : overmemoId);
			bean.setPaymentCollection(fax.getPaymentCollection() == null ? "0.0"
					: fax.getPaymentCollection() + "");
			bean.setPiece(detail.getPiece() == null ? "0" : detail.getPiece()
					+ "");
			bean.setRealPiece(detail.getRealPiece() == null ? "0" : detail
					.getRealPiece()
					+ "");
			bean.setStartDepartName(overmemo.getStartDepartName() == null ? ""
					: overmemo.getStartDepartName());
			bean.setSubNo(detail.getSubNo() == null ? "" : detail.getSubNo());
			bean.setToWhere(overmemo.getEndDepartName() == null ? "" : overmemo
					.getEndDepartName());
			bean.setWeight(detail.getWeight() == null ? "0" : detail.getWeight()
					+ "");
			bean.setTraFee(fax.getTraFee()==null?"0.0":fax.getTraFee()+"");//��ת��
			
			//���ÿͻ����Ի�Ҫ��
			requestList = this.oprRequestDoService.findBy("dno", fax.getDno());
			bean.setRequest("");
			if(fax.getUrgentService()!=null&&fax.getUrgentService()==1l){
				bean.setRequest("�Ӽ� ");
			}
			if(null!=requestList && requestList.size()>0){
				for (int j = 0; j < requestList.size(); j++) {
					if(requestList.get(j).getRequestStage().equals("�ͻ�")){
						bean.setRequest(bean.getRequest()+"�ͻ���"+requestList.get(j).getRequest());//�Ӹ��Ի�Ҫ�����ȡ �ͻ��׶�
					}else if(requestList.get(j).getRequestStage().equals("ǩ��")){
						if(!"".equals(bean.getRequest())){
							bean.setRequest(bean.getRequest()+",");
						}
						bean.setRequest(bean.getRequest()+"ǩ�գ�"+requestList.get(j).getRequest());//�Ӹ��Ի�Ҫ�����ȡ �ͻ��׶�
					}
				}
			}
			
			bean.setSendName("");
			bean.setSendNameTel("");
			bean.setAutostowName("");
			bean.setDriverName("");
			bean.setPrintNum(printNum);// ��ӡ����
			bean.setSourceNo(detail.getId().toString());// ʵ�䵥��
			bean.setPrintName(user.get("name") + "");// ��ӡ��
			bean.setPrintTime(sdf.format(new Date()));// ��ӡʱ��

			bean.setTotalPiece(overmemo.getTotalPiece() + "��");
			bean.setTotalTicket(overmemo.getTotalTicket() + "Ʊ");
			bean.setTotalWeight(overmemo.getTotalWeight() + "����");
			bean.setRouteNumber(overmemo.getRouteNumber() == null ? ""
					: overmemo.getRouteNumber() + "");
			try {
				bean.setCargoName(overmemo.getCreateName() == null ? ""
						: overmemo.getCreateName());
			} catch (Exception e) {
				e.printStackTrace();
			}
			bean.setDispatchGroup("");
			bean.setLoadingGroup("");
			loadingList = this.oprLoadingbrigadeWeightService
					.find(
							"from OprLoadingbrigadeWeight where overmemoNo=? and loadingbrigadeType=? ",
							Long.valueOf(overmemoId), 1l);
			if (null != loadingList && loadingList.size() > 0) {
				bean.setDispatchGroup(this.basLoadingbrigadeService.get(
						loadingList.get(0).getDispatchId()).getLoadingName());
				bean.setLoadingGroup(this.basLoadingbrigadeService.get(
						loadingList.get(0).getLoadingbrigadeId())
						.getLoadingName());
			}
			list.add(bean);
		}
		String[] overmemoTypes = this.overmemoType.split(",");
		boolean flag = false;
		for (int i = 0; i < overmemoTypes.length; i++) {
			if(overmemo.getOvermemoType().trim().equals(overmemoTypes[i].trim())) {
				flag = true;
				break;
			}
		}
		if (flag) {
			// ����������ͻ���ʵ�䵥�����ӡ������
			List<SysPrintManager> printManagerList = this.sysPrintManagerService
			.findBy("code", "22");
			if (null != printManagerList && printManagerList.size() > 0) {
				mainbill.setReportName(printManagerList.get(0).getReportName());
				mainbill.setReportPath(printManagerList.get(0).getReportPath());
				// mainbill.setControlSide(0);
			} else {
				mainbill.setReportName("");
				mainbill.setReportPath("");
				mainbill.setMsg("û���ҵ������ͻ����䵥��ӡģ�壡");
				return list;
			}
		}
		return list;
	}

	public Long getPrintNum(OprOvermemo oprOvermemo) {
		Long printNum = (oprOvermemo.getPrintNum()==null?0l:oprOvermemo.getPrintNum()) + 1l;
		oprOvermemo.setPrintNum(Long.valueOf(printNum));
		this.oprOvermemoService.save(oprOvermemo);
		return printNum;
	}
}

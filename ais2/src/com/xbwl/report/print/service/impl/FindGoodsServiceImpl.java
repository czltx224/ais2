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

import com.xbwl.entity.BasFlight;
import com.xbwl.entity.OprException;
import com.xbwl.entity.OprFaxIn;
import com.xbwl.entity.OprRemark;
import com.xbwl.entity.OprRequestDo;
import com.xbwl.entity.OprStatus;
import com.xbwl.entity.OprStock;
import com.xbwl.oper.exception.service.IOprExceptionService;
import com.xbwl.oper.fax.service.IOprFaxInService;
import com.xbwl.oper.stock.service.IOprRemarkService;
import com.xbwl.oper.stock.service.IOprRequestDoService;
import com.xbwl.oper.stock.service.IOprStatusService;
import com.xbwl.oper.stock.service.IOprStockService;
import com.xbwl.report.print.bean.BillLadingList;
import com.xbwl.report.print.bean.FindGoodsBean;
import com.xbwl.report.print.bean.PrintBean;
import com.xbwl.report.print.printServiceInterface.IPrintServiceInterface;
import com.xbwl.sys.service.IBasFlightService;

/**
 * author CaoZhili time Nov 3, 2011 9:00:11 AM 
 * �һ�����ӡ�����ʵ����
 */
@Service("findGoodsServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class FindGoodsServiceImpl implements IPrintServiceInterface {

	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource(name = "oprFaxInServiceImpl")
	private IOprFaxInService oprFaxInService;

	@Resource(name = "basFilghtServiceImpl")
	private IBasFlightService basFlightService;

	@Resource(name = "oprStatusServiceImpl")
	private IOprStatusService oprStatusService;

	@Resource(name = "oprStockServiceImpl")
	private IOprStockService oprStockService;

	@Resource(name = "oprRequestDoServiceImpl")
	private IOprRequestDoService oprRequestDoService;

	@Resource(name = "oprRemarkServiceImpl")
	private IOprRemarkService oprRemarkService;

	@Resource(name = "oprExceptionServiceImpl")
	private IOprExceptionService oprExceptionService;

	public void afterPrint(PrintBean bean) {
		logger.debug("�һ�����ӡ��ϣ�");
	}

	public List<PrintBean> setPrintBeanList(BillLadingList mainbill,
			Map<String, String> map) {
		List<PrintBean> list = null;
		try {
			list = doPrintBeanList(mainbill, map);
		} catch (Exception e) {
			mainbill.setMsg("�һ�����ӡʧ�ܣ�");
			e.printStackTrace();
		}
		return list;
	}

	private static final String signRequest = "ǩ��";
	private static final String outStockRequest = "����";

	private List<PrintBean> doPrintBeanList(BillLadingList mainbill,
			Map<String, String> map) throws Exception {
		List<PrintBean> list = new ArrayList<PrintBean>();
		String dnoString = map.get("dnos");

		if (null == dnoString || dnoString.equals("")) {
			mainbill.setMsg("û�а����͵��ţ��޷���ӡ��");
			return list;
		}

		String[] dnos = dnoString.split(",");
		FindGoodsBean bean = null;
		OprFaxIn fax = null;
		List<OprStatus> statusList = null;
		List<OprStock> stockList = null;
		List<OprRequestDo> requestList = null;
		List<OprRemark> remarkList = null;
		List<OprException> exList = null;
		List<BasFlight> flightList = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		SimpleDateFormat sdf3 = new SimpleDateFormat("MM-dd HH:mm");
		User user = WebRalasafe.getCurrentUser(ServletActionContext
				.getRequest());
		Date dt = new Date();
		for (int i = 0; i < dnos.length; i++) {
			fax = this.oprFaxInService.get(Long.valueOf(dnos[i]));
			bean = new FindGoodsBean();

			bean.setConsigneeFee(fax.getConsigneeFee() == null ? "" : fax
					.getConsigneeFee()
					+ "");
			bean.setConsigneeInfo(fax.getConsignee() + "/"
					+ fax.getConsigneeTel() + "/" + fax.getAddr());// �ջ�����Ϣ
			bean.setCqName(fax.getCpName() == null ? "" : fax.getCpName());// ��������
			bean.setDno(fax.getDno() == null ? "" : fax.getDno() + "");// ���͵���
			bean.setPaymentCollection(fax.getPaymentCollection() == null ? ""
					: fax.getPaymentCollection() + "");
			bean.setPiece(fax.getPiece() == null ? "" : fax.getPiece() + "");
			bean.setSubNo(fax.getSubNo() == null ? "" : fax.getSubNo());
			bean.setFlightMainNo(fax.getFlightMainNo() == null ? "" : fax
					.getFlightMainNo());
			bean
					.setCountFee(((fax.getConsigneeFee() == null ? 0l : fax
							.getConsigneeFee())
							+ (fax.getCpValueAddFee() == null ? 0l : fax
									.getCpValueAddFee()) + (fax
							.getPaymentCollection() == null ? 0l : fax
							.getPaymentCollection()))
							+ "");// ���úϼ�
			flightList = this.basFlightService.findBy("flightNumber", fax
					.getFlightNo());
			if (null != flightList && flightList.size() > 0) {
				bean.setFlightInfo(fax.getFlightNo() == null ? "" : fax
						.getFlightNo()
						+ "/" + flightList.get(0).getStartCity());// ������Ϣ(�����+ʼ������)
			} else {
				bean.setFlightInfo(fax.getFlightNo() == null ? "" : fax
						.getFlightNo());
			}
			bean.setTraceTo(fax.getGowhere() == null ? "" : fax.getGowhere());// ȥ��
																				// ��Ӧ��
			bean.setWeight(fax.getCqWeight() == null ? "" : fax.getCqWeight()
					+ "");
			bean
					.setTakeMode(fax.getTakeMode() == null ? "" : fax
							.getTakeMode());
			statusList = this.oprStatusService.findBy("dno", fax.getDno());
			bean.setReachDate("");
			if (null != statusList && statusList.size() > 0) {
				if (null != statusList.get(0).getReachTime()
						&& statusList.get(0).getReachTime().toString().length() > 1) {
					bean.setReachDate(sdf2.format(statusList.get(0)
							.getReachTime()));// ��״̬����ȡ�㵽����
				}
			}
			bean.setDistributionMode(fax.getDistributionMode() == null ? ""
					: fax.getDistributionMode());
			bean.setGoodsStatus(fax.getGoodsStatus() == null ? "" : fax
					.getGoodsStatus());
			bean.setReceiptType(fax.getReceiptType() == null ? "" : fax
					.getReceiptType());
			bean.setStockPiece("");
			bean.setStockArea("");
			stockList = this.oprStockService.find(
					"from OprStock where dno=? and departId=?", fax.getDno(),
					Long.valueOf(user.get("bussDepart") + ""));
			if (null != stockList && stockList.size() > 0) {
				bean
						.setStockArea(stockList.get(0).getStorageArea() == null ? ""
								: stockList.get(0).getStorageArea());
				bean.setStockPiece(stockList.get(0).getPiece() == null ? ""
						: stockList.get(0).getPiece() + "");
			}

			bean.setPrintTitle(user.get("rightDepart") + "�һ���");
			bean.setRequestString("");
			requestList = this.oprRequestDoService.find(
					"from OprRequestDo where dno=?", fax.getDno());
			if (null != requestList && requestList.size() > 0) {
				for (int j = 0; j < requestList.size(); j++) {
					if (requestList.get(j).getRequestStage().equals(
							FindGoodsServiceImpl.outStockRequest)) {
						bean.setRequestString("����Ҫ��"
								+ requestList.get(j).getRequest());
					} else if (requestList.get(j).getRequestStage().equals(
							FindGoodsServiceImpl.signRequest)) {
						bean.setRequestString(bean.getRequestString()
								+ "  ǩ��Ҫ��"
								+ requestList.get(j).getRequest());
					}
				}
			}
			bean.setRemarkString("");
			remarkList = this.oprRemarkService.findBy("dno", fax.getDno());
			for (int j = 0; j < remarkList.size(); j++) {
				bean.setRemarkString((bean.getRemarkString().equals("") ? ""
						: (bean.getRemarkString() + "��"))
						+ (remarkList.get(j).getRemark() == null ? "" : (j + 1)
								+ ": "
								+ remarkList.get(j).getRemark()
								+ "-"
								+ remarkList.get(j).getCreateName()
								+ "-"
								+ sdf3
										.format(remarkList.get(j)
												.getCreateTime())));
			}
			bean.setExceptionString("");
			exList = this.oprExceptionService.findBy("dno", fax.getDno());
			if (null != exList && exList.size() > 0) {
				String deString="";
				String exInfoString="";
				for (int j = 0; j < exList.size(); j++) {
					//FIXED ̫���Ķ���
					deString = exList.get(j).getExceptionDescribe()+ "-"+ exList.get(j).getExceptionName()
						+ "-"+ sdf3.format(exList.get(j).getExceptionTime());
					exInfoString = bean.getExceptionString() + "��"
						+ (exList.get(j).getExceptionDescribe() == null ? "": (j + 1)+ ": "+ deString);
					bean.setExceptionString((bean.getExceptionString().equals("") ? "" : (exInfoString)));
				}
			}

			bean.setSourceNo(dnos[i]);// ���͵���
			bean.setPrintName(user.get("name") + "");// ��ӡ��
			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			bean.setPrintTime(sdf.format(dt));// ��ӡʱ��

			list.add(bean);
		}
		return list;
	}
}

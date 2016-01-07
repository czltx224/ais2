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

import com.xbwl.entity.BasFlight;
import com.xbwl.entity.OprFaxIn;
import com.xbwl.entity.OprPrewired;
import com.xbwl.entity.OprPrewiredDetail;
import com.xbwl.entity.OprReceipt;
import com.xbwl.entity.OprStatus;
import com.xbwl.entity.OprStock;
import com.xbwl.oper.fax.service.IOprFaxInService;
import com.xbwl.oper.receipt.service.IOprReceiptService;
import com.xbwl.oper.stock.service.IOprPrewiredDetailService;
import com.xbwl.oper.stock.service.IOprPrewiredService;
import com.xbwl.oper.stock.service.IOprStatusService;
import com.xbwl.oper.stock.service.IOprStockService;
import com.xbwl.report.print.bean.BillLadingList;
import com.xbwl.report.print.bean.PrewiredListBean;
import com.xbwl.report.print.bean.PrintBean;
import com.xbwl.report.print.printServiceInterface.IPrintServiceInterface;
import com.xbwl.sys.service.IBasFlightService;

/**
 * author CaoZhili time Oct 25, 2011 3:15:48 PM 
 * Ԥ���嵥��ӡ�����ʵ����
 */
@Service("oprPrewiredPrintServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class OprPrewiredPrintServiceImpl implements IPrintServiceInterface {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource(name = "oprPrewiredServiceImpl")
	private IOprPrewiredService oprPrewiredService;

	@Resource(name="oprPrewiredDetailServiceImpl")
	private IOprPrewiredDetailService oprPrewiredDetailService;
	
	@Resource(name = "oprFaxInServiceImpl")
	private IOprFaxInService oprFaxInService;

	@Resource(name = "oprStatusServiceImpl")
	private IOprStatusService oprStatusService;

	@Resource(name = "oprStockServiceImpl")
	private IOprStockService oprStockService;

	@Resource(name = "oprReceiptServiceImpl")
	private IOprReceiptService oprReceiptService;

	@Resource(name = "basFilghtServiceImpl")
	private IBasFlightService basFlightService;

	public void afterPrint(PrintBean bean) {
		logger.debug("Ԥ���嵥��ӡ��ϣ�����");
	}

	public List<PrintBean> setPrintBeanList(BillLadingList mainbill,
			Map<String, String> map) {
		List<PrintBean> list = new ArrayList<PrintBean>();
		try {
			// list =test();
			list = doPrintBeanList(mainbill, map);
		} catch (Exception e) {
			mainbill.setMsg("����Ԥ���ӡ�嵥ʧ�ܣ�");
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * ��װԤ���嵥��ӡʵ��
	 * 
	 * @param map
	 *            ��������
	 * @return ��װ��ļ���
	 * @throws Exception
	 *             �����쳣
	 */
	private List<PrintBean> doPrintBeanList(BillLadingList mainbill,
			Map<String, String> map) throws Exception {
		List<PrintBean> list = new ArrayList<PrintBean>();
		String prewiredId = map.get("prewiredId");
		if (null == prewiredId || "".equals(prewiredId)) {
			mainbill.setMsg("ʵ�䵥��δ�գ�");
			return list;
		}
		PrewiredListBean bean = null;
		OprFaxIn fax = null;
		OprPrewiredDetail detail = null;
		OprStatus status = null;
		List<OprStock> stockList = null;
		List<OprReceipt> receiptList = null;
		List<BasFlight> flightList = null;
		List<OprPrewiredDetail> pdetaiList = null;
		User user = WebRalasafe.getCurrentUser(ServletActionContext
				.getRequest());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		OprPrewired prewired = this.oprPrewiredService.get(Long
				.valueOf(prewiredId));
		pdetaiList =  this.oprPrewiredDetailService.find("from OprPrewiredDetail where oprPrewired.id=?"+(prewired.getOrderField()==null?"":(" order by "+prewired.getOrderField())),prewired.getId());
		Iterator<OprPrewiredDetail> itr =pdetaiList.iterator();
		Long printNumLong =getPrintNum(prewired);
		while (itr.hasNext()) {
			detail = itr.next();
			fax = this.oprFaxInService.get(detail.getDNo());
			status = this.oprStatusService.findBy("dno", detail.getDNo())
					.get(0);
			stockList = this.oprStockService.find(
					"from OprStock where dno=? and departId=?",
					detail.getDNo(), prewired.getDepartId());
			receiptList = this.oprReceiptService.findBy("dno", detail.getDNo());
			bean = new PrewiredListBean();
			bean.setAutostowMode(prewired.getAutostowMode() + "Ԥ���嵥");
			bean.setConsigneeInfo(fax.getConsignee() + "/"
					+ fax.getConsigneeTel() + "/" + fax.getAddr());// �ջ�����Ϣ
			bean.setCqName(fax.getCpName() == null ? "" : fax.getCpName());// ��������
			bean.setDno(detail.getDNo() == null ? "" : detail.getDNo() + "");// ���͵���
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
			bean.setFlightMainNo(fax.getFlightMainNo());// ������
			bean.setPiece(detail.getPiece() == null ? "0" : detail.getPiece()
					+ "");// ����
			bean.setWeight(detail.getWeight() == null ? "0" : detail.getWeight()
					+ "");// ����
			bean.setSubNo(fax.getSubNo() == null ? "" : fax.getSubNo());// �ֵ���
			bean.setVotes(prewired.getVotes() == null ? "0" : prewired
					.getVotes()
					+ "");// ��Ʊ��
			bean.setPrewiredId(prewired.getId() == null ? "" : prewired.getId()
					+ "");// �䳵����
			bean.setStartDepartName(prewired.getDepartName() == null ? ""
					: prewired.getDepartName());// ʼ������
			bean.setGoodsStatus(fax.getGoodsStatus() == null ? "" : fax
					.getGoodsStatus());// ����״̬
			bean.setStockArea("");
			bean.setStockCountTime("0");
			if (null != stockList && stockList.size() > 0) {
				bean
						.setStockArea(stockList.get(0).getStorageArea() == null ? ""
								: stockList.get(0).getStorageArea());// �������
				Long stockCountTime = 0l;
				if (null != status.getReachTime()
						&& status.getReachTime().toString().length() > 1) {
					stockCountTime = (new Date().getTime() - status
							.getReachTime().getTime())
							/ (1000 * 60 * 60);
				}
				bean.setStockCountTime(stockCountTime + "");// ���ʱ��
			}
			bean.setReceiptNum("");
			if (null != receiptList && receiptList.size() > 0) {
				if (null != receiptList.get(0).getReachNum()
						&& receiptList.get(0).getReachNum() > 0) {
					bean.setReceiptNum(receiptList.get(0).getReachNum() + "");// ԭ������
				}
			}
			bean.setToWhere(prewired.getToWhere() == null ? "" : prewired
					.getToWhere());
			bean.setTotalPiece(prewired.getPiece() + "��");
			bean.setTotalTicket(prewired.getVotes() + "Ʊ");
			bean.setTotalWeight(prewired.getWeight() + "����");
			bean.setPrintNum(printNumLong);// ��ӡ����
			bean.setSourceNo(detail.getId().toString());// Ԥ�䵥��
			bean.setPrintName(user.get("name") + "");// ��ӡ��
			bean.setPrintTime(sdf.format(new Date()));// ��ӡʱ��

			list.add(bean);
		}
		return list;
	}

	/**
	 * ��װԤ���嵥��ӡʵ����Է���
	 * 
	 * @return ��װ��ļ��϶���
	 */
	public List<PrintBean> test() {
		List<PrintBean> list = new ArrayList<PrintBean>();
		PrewiredListBean bean = null;
		for (int i = 0; i < 5; i++) {

			bean = new PrewiredListBean();
			bean.setAutostowMode("��ת");
			bean.setConsigneeInfo("����/1324564512/���ݰ�������ƽ·��");
			bean.setCqName("˳�繫˾");
			bean.setDno(9876543322l + "");
			bean.setFlightInfo("GT202/����");
			bean.setFlightMainNo("GT2032");
			bean.setSubNo("02" + (i));
			bean.setPiece("12");
			bean.setWeight("256.2");
			bean.setVotes("5");
			bean.setPrewiredId("22255656");
			bean.setStockCountTime("123");
			bean.setReceiptNum("23");
			bean.setSourceNo("002");
			bean.setStartDepartName("������������");
			bean.setToWhere("��ɽӪҵ��");
			bean.setGoodsStatus("���㵽");
			bean.setStockArea("��ƽһ��");

			bean.setPrintName("������");
			bean.setPrintTime(new Date().toLocaleString());
			bean.setPrintNum(1l);

			list.add(bean);
		}

		return list;
	}

	/**
	 * ��ȡ��ӡ���������ұ����ӡ����
	 * 
	 * @param detail
	 *            Ԥ����ϸ����
	 * @return ��ӡ��Ĵ���
	 */
	public Long getPrintNum(OprPrewired oprPrewired) {
		Long printNum = (oprPrewired.getPrintNum()==null?0l:oprPrewired.getPrintNum()) + Long.valueOf(1);
		oprPrewired.setPrintNum(Long.valueOf(printNum));
		this.oprPrewiredService.save(oprPrewired);
		return printNum;
	}
}

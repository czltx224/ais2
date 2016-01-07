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
 * 预配清单打印服务层实现类
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
		logger.debug("预配清单打印完毕！！！");
	}

	public List<PrintBean> setPrintBeanList(BillLadingList mainbill,
			Map<String, String> map) {
		List<PrintBean> list = new ArrayList<PrintBean>();
		try {
			// list =test();
			list = doPrintBeanList(mainbill, map);
		} catch (Exception e) {
			mainbill.setMsg("配置预配打印清单失败！");
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 封装预配清单打印实体
	 * 
	 * @param map
	 *            条件集合
	 * @return 封装后的集合
	 * @throws Exception
	 *             外抛异常
	 */
	private List<PrintBean> doPrintBeanList(BillLadingList mainbill,
			Map<String, String> map) throws Exception {
		List<PrintBean> list = new ArrayList<PrintBean>();
		String prewiredId = map.get("prewiredId");
		if (null == prewiredId || "".equals(prewiredId)) {
			mainbill.setMsg("实配单号未空！");
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
			bean.setAutostowMode(prewired.getAutostowMode() + "预配清单");
			bean.setConsigneeInfo(fax.getConsignee() + "/"
					+ fax.getConsigneeTel() + "/" + fax.getAddr());// 收货人信息
			bean.setCqName(fax.getCpName() == null ? "" : fax.getCpName());// 发货代理
			bean.setDno(detail.getDNo() == null ? "" : detail.getDNo() + "");// 配送单号
			flightList = this.basFlightService.findBy("flightNumber", fax
					.getFlightNo());
			if (null != flightList && flightList.size() > 0) {
				bean.setFlightInfo(fax.getFlightNo() == null ? "" : fax
						.getFlightNo()
						+ "/" + flightList.get(0).getStartCity());// 航班信息(航班号+始发城市)
			} else {
				bean.setFlightInfo(fax.getFlightNo() == null ? "" : fax
						.getFlightNo());
			}
			bean.setFlightMainNo(fax.getFlightMainNo());// 主单号
			bean.setPiece(detail.getPiece() == null ? "0" : detail.getPiece()
					+ "");// 件数
			bean.setWeight(detail.getWeight() == null ? "0" : detail.getWeight()
					+ "");// 重量
			bean.setSubNo(fax.getSubNo() == null ? "" : fax.getSubNo());// 分单号
			bean.setVotes(prewired.getVotes() == null ? "0" : prewired
					.getVotes()
					+ "");// 总票数
			bean.setPrewiredId(prewired.getId() == null ? "" : prewired.getId()
					+ "");// 配车单号
			bean.setStartDepartName(prewired.getDepartName() == null ? ""
					: prewired.getDepartName());// 始发部门
			bean.setGoodsStatus(fax.getGoodsStatus() == null ? "" : fax
					.getGoodsStatus());// 货物状态
			bean.setStockArea("");
			bean.setStockCountTime("0");
			if (null != stockList && stockList.size() > 0) {
				bean
						.setStockArea(stockList.get(0).getStorageArea() == null ? ""
								: stockList.get(0).getStorageArea());// 库存区域
				Long stockCountTime = 0l;
				if (null != status.getReachTime()
						&& status.getReachTime().toString().length() > 1) {
					stockCountTime = (new Date().getTime() - status
							.getReachTime().getTime())
							/ (1000 * 60 * 60);
				}
				bean.setStockCountTime(stockCountTime + "");// 库存时间
			}
			bean.setReceiptNum("");
			if (null != receiptList && receiptList.size() > 0) {
				if (null != receiptList.get(0).getReachNum()
						&& receiptList.get(0).getReachNum() > 0) {
					bean.setReceiptNum(receiptList.get(0).getReachNum() + "");// 原件份数
				}
			}
			bean.setToWhere(prewired.getToWhere() == null ? "" : prewired
					.getToWhere());
			bean.setTotalPiece(prewired.getPiece() + "件");
			bean.setTotalTicket(prewired.getVotes() + "票");
			bean.setTotalWeight(prewired.getWeight() + "公斤");
			bean.setPrintNum(printNumLong);// 打印次数
			bean.setSourceNo(detail.getId().toString());// 预配单号
			bean.setPrintName(user.get("name") + "");// 打印人
			bean.setPrintTime(sdf.format(new Date()));// 打印时间

			list.add(bean);
		}
		return list;
	}

	/**
	 * 封装预配清单打印实体测试方法
	 * 
	 * @return 封装后的集合对象
	 */
	public List<PrintBean> test() {
		List<PrintBean> list = new ArrayList<PrintBean>();
		PrewiredListBean bean = null;
		for (int i = 0; i < 5; i++) {

			bean = new PrewiredListBean();
			bean.setAutostowMode("中转");
			bean.setConsigneeInfo("王五/1324564512/广州白云区东平路口");
			bean.setCqName("顺风公司");
			bean.setDno(9876543322l + "");
			bean.setFlightInfo("GT202/广州");
			bean.setFlightMainNo("GT2032");
			bean.setSubNo("02" + (i));
			bean.setPiece("12");
			bean.setWeight("256.2");
			bean.setVotes("5");
			bean.setPrewiredId("22255656");
			bean.setStockCountTime("123");
			bean.setReceiptNum("23");
			bean.setSourceNo("002");
			bean.setStartDepartName("广州配送中心");
			bean.setToWhere("中山营业部");
			bean.setGoodsStatus("入库点到");
			bean.setStockArea("东平一区");

			bean.setPrintName("曹智礼");
			bean.setPrintTime(new Date().toLocaleString());
			bean.setPrintNum(1l);

			list.add(bean);
		}

		return list;
	}

	/**
	 * 获取打印次数，并且保存打印次数
	 * 
	 * @param detail
	 *            预配明细对象
	 * @return 打印后的次数
	 */
	public Long getPrintNum(OprPrewired oprPrewired) {
		Long printNum = (oprPrewired.getPrintNum()==null?0l:oprPrewired.getPrintNum()) + Long.valueOf(1);
		oprPrewired.setPrintNum(Long.valueOf(printNum));
		this.oprPrewiredService.save(oprPrewired);
		return printNum;
	}
}

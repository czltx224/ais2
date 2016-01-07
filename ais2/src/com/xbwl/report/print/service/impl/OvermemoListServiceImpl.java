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
 * 实配清单打印服务层实现类
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
		logger.debug("实配清单打印完毕..............");
	}

	public List<PrintBean> setPrintBeanList(BillLadingList mainbill,
			Map<String, String> map) {
		List<PrintBean> list = new ArrayList<PrintBean>();
		try {
			// list =test();
			list = doPrintBeanList(mainbill, map);
		} catch (Exception e) {
			mainbill.setMsg("实配打印清单失败！");
			e.printStackTrace();
		}
		return list;
	}

	private List<PrintBean> doPrintBeanList(BillLadingList mainbill,
			Map<String, String> map) throws Exception {
		List<PrintBean> list = new ArrayList<PrintBean>();
		String overmemoId = map.get("overmemoId");
		if (null == overmemoId || "".equals(overmemoId)) {
			mainbill.setMsg("实配单号为空！");
			return list;
			// throw new ServiceException("实配单号未空！");
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
				mainbill.setMsg("配送单号为空！");
				return list;
			}
			receiptList = this.oprReceiptService.findBy("dno", detail.getDno());
			if (null != receiptList && receiptList.size() > 0) {
				bean.setSignNun(receiptList.get(0).getGetNum() == null ? "0"
						: receiptList.get(0).getGetNum() + "");
			}
			//收入计算
			shouru = NumberUtil.numberAdd(fax.getConsigneeFee(), fax.getCpFee())//到付+预付
			        +NumberUtil.numberAdd(fax.getCusValueAddFee(), fax.getCpValueAddFee())//到付增值费+预付增值费
			        +NumberUtil.numberAdd(fax.getSonderzugPrice(), fax.getCpSonderzugPrice());//到付专车费+预付专车费
			bean.setShouru(shouru+"");
			bean.setAutostowMode(overmemo.getOvermemoType() + "实配清单");
			//bean.setConsigneeFee((fax.getConsigneeFee() + fax.getCpValueAddFee())+ "");
			bean.setConsigneeFee(NumberUtil.numberAdd(fax.getConsigneeFee(), fax.getCusValueAddFee(),fax.getSonderzugPrice())+"");//到付提送费+到付增值费+到付专车费
			bean.setCpFee(NumberUtil.numberAdd(fax.getCpFee(), fax.getCpValueAddFee(),fax.getCpSonderzugPrice())+"");//预付提送费+预付增值费+预付专车费
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
			bean.setTraFee(fax.getTraFee()==null?"0.0":fax.getTraFee()+"");//中转费
			
			//设置客户个性化要求
			requestList = this.oprRequestDoService.findBy("dno", fax.getDno());
			bean.setRequest("");
			if(fax.getUrgentService()!=null&&fax.getUrgentService()==1l){
				bean.setRequest("加急 ");
			}
			if(null!=requestList && requestList.size()>0){
				for (int j = 0; j < requestList.size(); j++) {
					if(requestList.get(j).getRequestStage().equals("送货")){
						bean.setRequest(bean.getRequest()+"送货："+requestList.get(j).getRequest());//从个性化要求表中取 送货阶段
					}else if(requestList.get(j).getRequestStage().equals("签收")){
						if(!"".equals(bean.getRequest())){
							bean.setRequest(bean.getRequest()+",");
						}
						bean.setRequest(bean.getRequest()+"签收："+requestList.get(j).getRequest());//从个性化要求表中取 送货阶段
					}
				}
			}
			
			bean.setSendName("");
			bean.setSendNameTel("");
			bean.setAutostowName("");
			bean.setDriverName("");
			bean.setPrintNum(printNum);// 打印次数
			bean.setSourceNo(detail.getId().toString());// 实配单号
			bean.setPrintName(user.get("name") + "");// 打印人
			bean.setPrintTime(sdf.format(new Date()));// 打印时间

			bean.setTotalPiece(overmemo.getTotalPiece() + "件");
			bean.setTotalTicket(overmemo.getTotalTicket() + "票");
			bean.setTotalWeight(overmemo.getTotalWeight() + "公斤");
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
			// 如果是市内送货的实配单，则打印出收入
			List<SysPrintManager> printManagerList = this.sysPrintManagerService
			.findBy("code", "22");
			if (null != printManagerList && printManagerList.size() > 0) {
				mainbill.setReportName(printManagerList.get(0).getReportName());
				mainbill.setReportPath(printManagerList.get(0).getReportPath());
				// mainbill.setControlSide(0);
			} else {
				mainbill.setReportName("");
				mainbill.setReportPath("");
				mainbill.setMsg("没有找到市内送货是配单打印模板！");
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

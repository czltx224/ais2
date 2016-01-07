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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.entity.OprOvermemo;
import com.xbwl.entity.OprSignRoute;
import com.xbwl.entity.SysPrintManager;
import com.xbwl.oper.stock.service.IOprOvermemoService;
import com.xbwl.oper.stock.service.IOprSignRouteService;
import com.xbwl.report.print.bean.BillLadingList;
import com.xbwl.report.print.bean.CarDriverMsgReportBean;
import com.xbwl.report.print.bean.PrintBean;
import com.xbwl.report.print.printServiceInterface.IPrintServiceInterface;
import com.xbwl.report.print.service.ISysPrintManagerService;

/**
 * author CaoZhili time Nov 2, 2011 10:33:54 AM 
 * 新邦内部车辆签单打印服务层实现类
 */
@Service("carDriverMsgReportServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class CarDriverMsgReportServiceImpl implements IPrintServiceInterface {

	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource(name = "oprSignRouteServiceImpl")
	private IOprSignRouteService signRouteService;

	@Resource(name = "oprOvermemoServiceImpl")
	private IOprOvermemoService oprOvermemoService;

	@Resource(name = "sysPrintManagerServiceImpl")
	private ISysPrintManagerService sysPrintManagerService;

	@Value("${print.carDriverMsgReportServiceImpl.rentCarType}")
	private String rentCarType;

	public void afterPrint(PrintBean bean) {
		logger.debug("车辆签单打印完毕！");
	}

	public List<PrintBean> setPrintBeanList(BillLadingList mainbill,
			Map<String, String> map) {
		List<PrintBean> list = null;
		try {
			list = doPrintBeanList(mainbill, map);
		} catch (Exception e) {
			mainbill.setMsg("内部车辆签单打印失败！");
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * @param mainbill
	 * @param map
	 * @return
	 */
	private List<PrintBean> doPrintBeanList(BillLadingList mainbill,
			Map<String, String> map) throws Exception {
		String routeNumber = map.get("routeNumber");
		List<PrintBean> list = new ArrayList<PrintBean>();
		Date dt = new Date();
		CarDriverMsgReportBean bean = new CarDriverMsgReportBean();
		if (null == routeNumber || "".equals(routeNumber)) {
			mainbill.setMsg("车次号为空！");
			return list;
		}
		List<OprSignRoute> signRouteList = this.signRouteService.findBy(
				"routeNumber", Long.valueOf(routeNumber));
		OprSignRoute signRoute = null;
		if (null != signRouteList && signRouteList.size() > 0) {
			signRoute = signRouteList.get(0);
		} else {
			mainbill.setMsg("没有找到对应的签单号信息！");
			// return null;
			return list;
		}

		bean.setSignRouteNo(signRoute.getCarSignNo() == null ? "" : signRoute
				.getCarSignNo());

		List<OprOvermemo> overmemoList = this.oprOvermemoService.findBy(
				"routeNumber", signRoute.getRouteNumber());
		OprOvermemo overmemo = null;
		if (null != overmemoList && overmemoList.size() > 0) {
			overmemo = overmemoList.get(0);
			bean.setOvermemoNo(overmemo.getId() == null ? "" : overmemo.getId()
					+ "");
		} else {
			bean.setOvermemoNo("");
			mainbill.setMsg("没有找到交接单号！");
			return list;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		User user = WebRalasafe.getCurrentUser(ServletActionContext
				.getRequest());

		bean.setCarCode(signRoute.getCarNo() == null ? "" : signRoute
				.getCarNo());
		bean.setUseCarDepart(signRoute.getDepartName() == null ? "" : signRoute
				.getDepartName());
		bean.setUseCarResult(signRoute.getRentCarResult() == null ? ""
				: signRoute.getRentCarResult());
		bean.setUseCarType(signRoute.getUseCarType() == null ? "" : signRoute
				.getUseCarType());
		bean.setUseCarDate(signRoute.getCreateTime() == null ? "" : sdf
				.format(signRoute.getCreateTime())
				+ "");
		bean.setPrintDepart(user.get("rightDepart") + "");

		bean.setSourceNo(routeNumber);// 车次号
		bean.setPrintName(user.get("name") + "");// 打印人
		sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		bean.setPrintTime(sdf.format(dt));// 打印时间

		if (null == signRoute.getPrintNum()) {
			signRoute.setPrintNum(1l);
		} else {
			signRoute.setPrintNum(signRoute.getPrintNum() + 1l);
		}
		bean.setPrintNum(signRoute.getPrintNum());// 打印次数
		signRoute.setPrintDepartName(bean.getPrintDepart());
		signRoute.setPrintDepartId(Long.valueOf(user.get("bussDepart") + ""));
		signRoute.setPrintName(bean.getPrintName());
		// 保存打印次数
		this.signRouteService.save(signRoute);

		if (bean.getUseCarType().equals(this.rentCarType)) {
			// 设置收货人，即报销人
			bean.setSendGoodsMan(overmemo.getEndDepartName() == null ? ""
					: overmemo.getEndDepartName());
			// 设置车主电话
			bean.setCarManTel(signRoute.getPhone() == null ? "" : signRoute
					.getPhone());

			List<SysPrintManager> printManagerList = this.sysPrintManagerService
					.findBy("code", "5");
			if (null != printManagerList && printManagerList.size() > 0) {
				mainbill.setReportName(printManagerList.get(0).getReportName());
				mainbill.setReportPath(printManagerList.get(0).getReportPath());
				// mainbill.setControlSide(0);
			} else {
				mainbill.setReportName("");
				mainbill.setReportPath("");
				mainbill.setMsg("没有找到外租车打印模板！");
				return list;
			}
		}

		list.add(bean);
		return list;
	}

}

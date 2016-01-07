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

import com.xbwl.entity.OprFaxIn;
import com.xbwl.entity.OprStock;
import com.xbwl.entity.OprStocktake;
import com.xbwl.entity.OprStocktakeDetail;
import com.xbwl.oper.fax.service.IOprFaxInService;
import com.xbwl.oper.stock.service.IOprStockService;
import com.xbwl.oper.stock.service.IOprStockTakeService;
import com.xbwl.report.print.bean.BillLadingList;
import com.xbwl.report.print.bean.OprStockInventoryBean;
import com.xbwl.report.print.bean.PrintBean;
import com.xbwl.report.print.printServiceInterface.IPrintServiceInterface;

/**
 * 库存盘点请仓单打印服务层实现类
 * @project ais
 * @author czl
 * @Time Feb 25, 2012 11:49:48 AM
 */
@Service("oprStockInventoryReportServiceImpl")
@Transactional(rollbackFor=Exception.class)
public class OprStockInventoryReportServiceImpl implements IPrintServiceInterface{

	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource(name="oprStockTakeServiceImpl")
	private IOprStockTakeService oprStockTakeService;
	
	@Resource(name="oprFaxInServiceImpl")
	private IOprFaxInService oprFaxInService;
	
	@Resource(name="oprStockServiceImpl")
	private IOprStockService oprStockService;
	
	public void afterPrint(PrintBean bean) {
		logger.debug("库存盘点清仓单打印完毕！");
	}

	public List<? extends PrintBean> setPrintBeanList(BillLadingList mainbill,
			Map<String, String> map) {
		List<PrintBean> list = new ArrayList<PrintBean>();
		
		String inventoryNo = map.get("inventoryNum");
		if(null==inventoryNo || "".equals(inventoryNo)){
			mainbill.setMsg("没有请仓单号！");
			return null;
		}
		Long inventoryNum = null;
		try{
			inventoryNum = Long.valueOf(inventoryNo);
		}catch (Exception e) {
			mainbill.setMsg("清仓单号不正确！");
			return null;
		}
		
		OprStocktake take = this.oprStockTakeService.get(inventoryNum);
		User user = WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date dt = new Date();
		
		Iterator<OprStocktakeDetail> itr = take.getOprStocktakeDetails().iterator();
//		int i=0;
		while (itr.hasNext()) {
//			i++;
//			if(i==50){
//				break;
//			}
			OprStocktakeDetail detail = itr.next();
			OprStockInventoryBean bean = new OprStockInventoryBean();
			try{
				OprFaxIn faxIn = this.oprFaxInService.get(detail.getDNo());
				List<OprStock> stockList = this.oprStockService.find("from OprStock where dno=? and departId=?", faxIn.getDno(),faxIn.getDistributionDepartId());
				bean.setStockPiece("0");
				if(null!=stockList && stockList.size()>0){
					bean.setStockPiece(stockList.get(0).getPiece()+"");
				}
				bean.setPiece(faxIn.getPiece()==null?"0":faxIn.getPiece()+"");
				bean.setDno(faxIn.getDno()+"");
				bean.setConsigneeInfo(faxIn.getConsignee()+"/"+faxIn.getConsigneeTel()+"/"+faxIn.getAddr());
				bean.setDistributionMode(faxIn.getDistributionMode());
				bean.setTakeMode(faxIn.getTakeMode());
				bean.setCpName(faxIn.getCpName());
				bean.setFlightMainNo(faxIn.getFlightMainNo());
				//bean.setInventoryPiece(detail);
				bean.setSubNo(faxIn.getSubNo());
				bean.setFlightNo(faxIn.getFlightNo());
				bean.setWeight(faxIn.getCusWeight()==null?"0":faxIn.getCusWeight()+"");
				bean.setInventoryNum(inventoryNo);
				bean.setStockArea(take.getStorageArea()==null?"":take.getStorageArea());
				
				bean.setPrintName(user.get("name")+"");
				bean.setPrintNum(0l);
				bean.setSourceNo(detail.getId()+"");
				bean.setPrintTime(sdf.format(dt));
				bean.setPrintTitle("清仓盘点单");//设置打印标题
				
				list.add(bean);
			}catch (Exception e) {
				//mainbill.setMsg("请仓单号对应的配送单号不存在！");
			}
		}
		return list;
	}

}

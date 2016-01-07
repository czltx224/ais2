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
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.entity.OprFaxIn;
import com.xbwl.oper.fax.dao.IOprFaxInDao;
import com.xbwl.report.print.bean.BillLadingList;
import com.xbwl.report.print.bean.OprWaybillLabelBean;
import com.xbwl.report.print.bean.PrintBean;
import com.xbwl.report.print.printServiceInterface.IPrintServiceInterface;

/**
 * @project ais
 * @author czl
 * @Time Feb 17, 2012 10:00:48 AM
 */
@Service("oprWaybillLabelPrintServiceImpl")
@Transactional(rollbackFor=Exception.class)
public class OprWaybillLabelPrintServiceImpl implements IPrintServiceInterface{

	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource(name="oprFaxInHibernateDaoImpl")
	private IOprFaxInDao oprFaxInDao;
	
	public void afterPrint(PrintBean bean) {
		logger.debug("运单标签打印完毕！");
	}

	public List<PrintBean> setPrintBeanList(BillLadingList mainbill,
			Map<String, String> map) {
		String[] dnos = map.get("dnos").split(",");
		String printNumString = map.get("printNum");
		String byPiece = map.get("byPiece");
		int printNum = 1;
		if(null==dnos || "".equals(dnos)){
			mainbill.setMsg("配送单号为空！");
			return null;
		}
		if(null!=printNumString && !"".equals(printNumString)){
			try{
				printNum = Integer.parseInt(printNumString);
			}catch (Exception e) {
				printNum = 1;
			}
		}
		User user = WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
		List<PrintBean> list = new ArrayList<PrintBean>();
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm");
		for (int i = 0; i < dnos.length; i++) {
			String dno = dnos[i];
			try{
				OprFaxIn faxIn = this.oprFaxInDao.get(Long.valueOf(dno));
				OprWaybillLabelBean bean = new OprWaybillLabelBean();
				
				bean.setAddr(faxIn.getAddr());
				bean.setConsignee(faxIn.getConsignee());
				bean.setFlightMainNo(faxIn.getFlightMainNo());
				sdf = new SimpleDateFormat("yy-MM-dd HH:mm");
				bean.setFaxInTime(sdf.format(faxIn.getCreateTime()));
				bean.setPiece(faxIn.getPiece()==null?"0":faxIn.getPiece()+"");
				bean.setDno(dno);
				bean.setStartDepart(faxIn.getInDepart());
				bean.setTransMode(faxIn.getDistributionMode()+faxIn.getTakeMode().substring(2));//截取
				bean.setIsRound("true");
				if(faxIn.getDistributionMode().equals("新邦")){
					bean.setIsRound("false");
				}
				
				sdf = new SimpleDateFormat("MM-dd HH:mm:ss");
				bean.setPrintName(user.get("name")+"");
				bean.setPrintTime(sdf.format(dt));
				bean.setPrintNum(1l);
				bean.setSourceNo("");
				if(null!=byPiece && byPiece.equals("true")){
					for (int j = 0; j < faxIn.getPiece(); j++) {
						OprWaybillLabelBean newBean = new OprWaybillLabelBean();
						BeanUtils.copyProperties(bean,newBean);
						list.add(newBean);
					}
				}else{
					for (int j = 0; j < printNum; j++) {
						OprWaybillLabelBean newBean = new OprWaybillLabelBean();
						BeanUtils.copyProperties(bean,newBean);
						list.add(newBean);
					}
				}
				
			}catch (Exception e) {
				mainbill.setMsg("封装Bean错误，该传真不规范！");
			}
		}
		return list;
	}

}

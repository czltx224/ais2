package com.xbwl.report.print.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.utils.DoubleChangeUtil;
import com.xbwl.entity.BasDictionaryDetail;
import com.xbwl.entity.FiCapitaaccountset;
import com.xbwl.entity.FiFundstransfer;
import com.xbwl.finance.Service.IFiCapitaaccountsetService;
import com.xbwl.finance.Service.IFiFundstransferService;
import com.xbwl.report.print.bean.BillLadingList;
import com.xbwl.report.print.bean.FiFundstransferBean;
import com.xbwl.report.print.bean.PrintBean;
import com.xbwl.report.print.printServiceInterface.IPrintServiceInterface;
import com.xbwl.sys.dao.IBasDictionaryDetailDao;
import com.xbwl.sys.service.IBasDictionaryService;

/**
 * author shuw
 * 打印资金交接单Service
 * time Dec 5, 2011 11:06:10 AM
 */
@Service("fiFundstransferPrintServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class FiFundstransferPrintServiceImpl implements IPrintServiceInterface{

	@Resource(name="fiFundstransferServiceImpl")
	private IFiFundstransferService fiFundstransferService;
	
	@Resource(name = "fiCapitaaccountsetServiceImpl")
	private IFiCapitaaccountsetService fiCapitaaccountsetService;
	
	@Resource
	private IBasDictionaryDetailDao idictionaryDao;
	
	public void afterPrint(PrintBean bean) {

	}

	public List<PrintBean> setPrintBeanList(BillLadingList mainbill,Map<String, String> map) {
		List<PrintBean> list = null;
		try {
			list = doPrintBeanList(mainbill, map);
		} catch (Exception e) {
			mainbill.setMsg("资金交接单打印失败！");
			e.printStackTrace();
		}
		return list;
	}
	
	public List<PrintBean> doPrintBeanList(BillLadingList mainbill,Map<String, String> map) throws Exception{
		String id = map.get("id");
		List<PrintBean> list = new ArrayList<PrintBean>();
		Date date  = new Date()	;
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dataString=simpleDateFormat.format(date);
		if (null == id || "".equals(id)) {
			mainbill.setMsg("资金交接单号为空");
			return list;
		}
		FiFundstransferBean fiFundstransferBean= new FiFundstransferBean();
		FiFundstransfer fiFundstransfer =fiFundstransferService.get(Long.parseLong(id));
		if (null != fiFundstransfer) {
			fiFundstransferBean.setPayDepartName(fiFundstransfer.getDepartName()==null?"":fiFundstransfer.getDepartName());
			fiFundstransferBean.setCreataRemark(fiFundstransfer.getRemark()==null?"":fiFundstransfer.getRemark());
			fiFundstransferBean.setAmount(fiFundstransfer.getAmount()==null?"0":fiFundstransfer.getAmount()+"");
			fiFundstransferBean.setPrintAccountNo(id);
			fiFundstransferBean.setMaxAmount(DoubleChangeUtil.digitUppercase(fiFundstransfer.getAmount()==null?0.0:fiFundstransfer.getAmount()));
			fiFundstransferBean.setIncomeDepartName(fiFundstransfer.getReceivablesaccountDept()==null?"":fiFundstransfer.getReceivablesaccountDept());
			fiFundstransferBean.setPayData(dataString);
			FiCapitaaccountset fiCapitaaccountset=fiCapitaaccountsetService.get(fiFundstransfer.getPaymentaccountId());
			FiCapitaaccountset fiCapitaaccountset2=fiCapitaaccountsetService.get(fiFundstransfer.getReceivablesaccountId());
			if(fiCapitaaccountset==null){
				mainbill.setMsg("没有找到对应的资金付款账号信息！");
				return list;
			}
			if(fiCapitaaccountset2==null){
				mainbill.setMsg("没有找到对应的资金收款账号信息！");
				return list;
			}
			
			Long did = fiCapitaaccountset.getAccountType();
			if(did!=null){
				BasDictionaryDetail bDetail =idictionaryDao.get(did);
				if(bDetail!=null){
					if("现金".equals(bDetail.getTypeName())){
						fiFundstransferBean.setCashStatus("1");
					}else{
						fiFundstransferBean.setCashStatus("2");
					}
				}else{
					fiFundstransferBean.setCashStatus("1");
				}
			}else{
				fiFundstransferBean.setCashStatus("1");
			}
			fiFundstransferBean.setPayAccountNo(fiCapitaaccountset.getAccountName()==null?"":fiCapitaaccountset.getAccountName());
			fiFundstransferBean.setIncomeAccountNo(fiCapitaaccountset2.getAccountName()==null?"":fiCapitaaccountset2.getAccountName());
			list.add(fiFundstransferBean);
		} else {
			mainbill.setMsg("没有找到对应的资金交接单信息！");
			return list;
		}
		return list;
	}

}

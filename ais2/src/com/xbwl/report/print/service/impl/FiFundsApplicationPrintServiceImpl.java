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
import com.xbwl.entity.FiApplyfund;
import com.xbwl.entity.FiCapitaaccountset;
import com.xbwl.entity.FiFundstransfer;
import com.xbwl.finance.Service.IFiApplyfundService;
import com.xbwl.finance.Service.IFiCapitaaccountsetService;
import com.xbwl.finance.Service.impl.FiApplyfundServiceImpl;
import com.xbwl.report.print.bean.BillLadingList;
import com.xbwl.report.print.bean.FiFundstransferBean;
import com.xbwl.report.print.bean.PrintBean;
import com.xbwl.report.print.printServiceInterface.IPrintServiceInterface;
import com.xbwl.sys.dao.IBasDictionaryDetailDao;

/**
 * author shuw
 * 资金申请单Service
 * time Dec 5, 2011 11:06:10 AM
 */
@Service("fiFundsApplicationPrintServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class FiFundsApplicationPrintServiceImpl implements IPrintServiceInterface{

	@Resource(name="fiApplyfundServiceImpl")
	private IFiApplyfundService fiApplyfundService;
	
	@Resource(name = "fiCapitaaccountsetServiceImpl")
	private IFiCapitaaccountsetService fiCapitaaccountsetService;
	
	@Resource
	private IBasDictionaryDetailDao idictionaryDao;
	
	public void afterPrint(PrintBean bean) {
	}

	public List<? extends PrintBean> setPrintBeanList(BillLadingList mainbill,
			Map<String, String> map) {
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
		FiApplyfund fiApplyfund =fiApplyfundService.get(Long.parseLong(id));
		if (null != fiApplyfund) {
			fiFundstransferBean.setPayDepartName(fiApplyfund.getDepartName()==null?"":fiApplyfund.getDepartName());
			fiFundstransferBean.setCreataRemark(fiApplyfund.getAppRemark()==null?"":fiApplyfund.getAppRemark());
			fiFundstransferBean.setAmount(fiApplyfund.getAmount()==null?"0":fiApplyfund.getAmount()+"");
			fiFundstransferBean.setPrintAccountNo(id);
			fiFundstransferBean.setMaxAmount(DoubleChangeUtil.digitUppercase(fiApplyfund.getAmount()==null?0.0:fiApplyfund.getAmount()));

			FiCapitaaccountset fiCapitaaccountset=fiCapitaaccountsetService.get(fiApplyfund.getAppAccountId());
			FiCapitaaccountset fiCapitaaccountset2=fiCapitaaccountsetService.get(fiApplyfund.getPaymentAccountId());
			
			fiFundstransferBean.setIncomeDepartName(fiCapitaaccountset2.getDepartName()==null?"":fiCapitaaccountset2.getDepartName());
			
			fiFundstransferBean.setPayData(dataString);
			if(fiCapitaaccountset==null){
				mainbill.setMsg("没有找到对应的资金申请账号信息！");
				return list;
			}
			if(fiCapitaaccountset2==null){
				mainbill.setMsg("没有找到对应的资金拨付账号信息！");
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
			mainbill.setMsg("没有找到对应的申请单信息！");
			return list;
		}
		return list;
	}
}

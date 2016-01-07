package com.xbwl.report.print.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.ralasafe.WebRalasafe;
import org.ralasafe.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.cus.service.ICusRecordService;
import com.xbwl.entity.BasFlight;
import com.xbwl.entity.CusRecord;
import com.xbwl.entity.OprBillladingPrintManager;
import com.xbwl.entity.OprChangeMain;
import com.xbwl.entity.OprFaxIn;
import com.xbwl.entity.OprPrewiredDetail;
import com.xbwl.entity.OprReceipt;
import com.xbwl.entity.OprRequestDo;
import com.xbwl.entity.OprSign;
import com.xbwl.entity.OprStatus;
import com.xbwl.entity.OprValueAddFee;
import com.xbwl.oper.fax.service.IOprFaxChangeService;
import com.xbwl.oper.fax.service.IOprFaxInService;
import com.xbwl.oper.receipt.service.IOprReceiptService;
import com.xbwl.oper.stock.service.IOprHistoryService;
import com.xbwl.oper.stock.service.IOprPrewiredService;
import com.xbwl.oper.stock.service.IOprRequestDoService;
import com.xbwl.oper.stock.service.IOprSignService;
import com.xbwl.oper.stock.service.IOprStatusService;
import com.xbwl.oper.stock.service.IOprValueAddFeeService;
import com.xbwl.rbac.Service.IUserService;
import com.xbwl.report.print.bean.BillLading;
import com.xbwl.report.print.bean.BillLadingList;
import com.xbwl.report.print.bean.PrintBean;
import com.xbwl.report.print.printServiceInterface.IPrintServiceInterface;
import com.xbwl.report.print.service.IOprBillladingPrintManagerService;
import com.xbwl.report.print.util.NumberUtil;
import com.xbwl.sys.service.IBasFlightService;

/**
 * @author czl
 * @createTime 9:37:29 AM
 * @updateName Administrator
 * @updateTime 9:37:29 AM
 * 提货单打印服务层实现类
 */

@Service("billLadingServiceImpl")
@Transactional(rollbackFor=Exception.class)
public class BillLadingServiceImpl implements IPrintServiceInterface {

	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource(name="oprFaxInServiceImpl")
	private IOprFaxInService oprFaxInService;
	
	@Resource(name="oprPrewiredServiceImpl")
	private IOprPrewiredService oprPrewiredService;
	
	@Resource(name="basFilghtServiceImpl")
	private IBasFlightService basFlightService;
	
	@Resource(name="oprSignServiceImpl")
	private IOprSignService oprSignService;
	
	@Resource(name="userServiceImpl")
	private IUserService userService;
	
	@Resource(name="oprStatusServiceImpl")
	private IOprStatusService oprStatusService;
	
	@Resource(name="oprRequestDoServiceImpl")
	private IOprRequestDoService oprRequestDoService;
	
	@Resource(name="oprReceiptServiceImpl")
	private IOprReceiptService oprReceiptService;
	
	@Resource(name="oprBillladingPrintManagerServiceImpl")
	private IOprBillladingPrintManagerService oprBillladingPrintManagerService;
	
	@Resource(name="cusRecordServiceImpl")
	private ICusRecordService cusRecordService;
	
	@Resource(name="oprValueAddFeeServiceImpl")
	private IOprValueAddFeeService oprValueAddFeeService;
	
	@Value("${billLadingServiceImpl.log_print}")
	private Long log_print;
	
	@Resource(name="oprHistoryServiceImpl")
	private IOprHistoryService oprHistoryService;
	
	@Resource(name="oprFaxChangeServiceImpl")
	private IOprFaxChangeService oprFaxChangeService;
	
	//方法太长，注意代码缩进，注意相关代码块放一块
	public List<PrintBean> setPrintBeanList(BillLadingList mainbill,Map<String, String> map) {
		List<PrintBean> list= new ArrayList<PrintBean>();
		try{
			String dno = map.get("dnos");
			String prewiredId = map.get("prewiredIds");
			if(null==dno || "".equals(dno)){
				if(null==prewiredId || "".equals(prewiredId)){
					mainbill.setMsg("请传配送单号或者预配单号过来！");
					return list;
				}
			}
			User user = WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH");
			SimpleDateFormat sdfSign = new SimpleDateFormat("MM月dd日HH时mm分");
			
			String[] dnos=null;
			String[] prewiredIds= null; 
			if (null!= prewiredIds && prewiredIds.length>0){
				dno = new String("");
				prewiredIds=prewiredId.split(",");
				Set<OprPrewiredDetail> pSet=null;
				Iterator< OprPrewiredDetail> itr = null;
				for (int i = 0; i < dnos.length; i++) {
					pSet = this.oprPrewiredService.get(Long.valueOf(prewiredIds[i])).getOprPrewiredDetails();
					 itr = pSet.iterator();
					while (itr.hasNext()) {
						dno+=itr.next().getDNo()+",";
					}
				}
			}
			dnos=dno.split(",");
			BillLading bean = null;
			OprFaxIn fax = null;
			List<OprSign> signList = null;
			List<OprStatus> statusList =  null;
			List<OprRequestDo> requestList=null;
			List<OprReceipt> receiptList = null;
			List<BasFlight> flightList = null;
			OprBillladingPrintManager billladingPrintManager=null;
			String changeDno="";
			String notOutDno="";
			for(int i=0;i<dnos.length;i++){
				bean = new BillLading();
				fax = this.oprFaxInService.get(Long.valueOf(dnos[i]));
				
				//bean.setConsigneeFee(fax.getConsigneeFee()+"");
				bean.setConsigneeFee(NumberUtil.numberAdd(fax.getConsigneeFee(),fax.getCusValueAddFee(),fax.getSonderzugPrice())+"");//到付增值服务费
				bean.setPaymentCollection(fax.getPaymentCollection()==null?"0.0":fax.getPaymentCollection()+"");
				bean.setConsigneeInfo(fax.getConsignee()+"/"+fax.getConsigneeTel()+"/"+fax.getAddr());
				bean.setCpValueAddFee(fax.getCusValueAddFee()==null?"0.0":fax.getCusValueAddFee()+"");//增值服务费
				signList  =  this.oprSignService.findBy("dno", fax.getDno());
				if(null!=signList && signList.size()>0){
					OprSign sign = signList.get(0);
					String signMan=sign.getSignMan()==null?"":sign.getSignMan();
					bean.setCardId(sign.getReIdentityCard()==null?(sign.getIdentityCard()==null?"":sign.getIdentityCard()):sign.getReIdentityCard());//签收证件号码
					bean.setSignMan(signMan);//设置签收人
					if(null!=sign.getCreateTime()){
						bean.setSignTime(sdfSign.format(sign.getCreateTime()));
					}
				}else{
					bean.setSignTime("  月    日    时    分");
				}
				bean.setComplainTel("");
				bean.setContext("");
				bean.setTitle("");
				billladingPrintManager = getBillladingPrintManager(mainbill,fax);
				if(null!=billladingPrintManager){
					bean.setComplainTel(billladingPrintManager.getComplaintTel()==null?"":billladingPrintManager.getComplaintTel());//待定，需要新建表
					bean.setContext(billladingPrintManager.getLeftContext()==null?"":billladingPrintManager.getLeftContext());//待定，需要新建表
					bean.setTitle(billladingPrintManager.getTitle());
				}
				bean.setCountFee(NumberUtil.numberAdd(Double.valueOf(bean.getConsigneeFee()),fax.getPaymentCollection())+"");
				bean.setCqName(fax.getCpName()==null?"":fax.getCpName());
				bean.setCusService(fax.getCustomerService()==null?"":fax.getCustomerService());//
				bean.setCusServiceTel(this.userService.findBy("userName", fax.getCustomerService()).get(0).getTelPhone());//从员工表中获取，通过客服员名称
				bean.setDno(fax.getDno()+"");
				flightList = this.basFlightService.findBy("flightNumber", fax.getFlightNo());
				if(null!=flightList && flightList.size()>0){
					bean.setFlightInfo(fax.getFlightNo()==null?"":fax.getFlightNo()+"/"+flightList.get(0).getStartCity());//航班信息(航班号+始发城市)
				}else{
					bean.setFlightInfo(fax.getFlightNo()==null?"":fax.getFlightNo());
				}
				bean.setFlightMainNo(fax.getFlightMainNo()==null?"":fax.getFlightMainNo());
				String isUrgent = setIsUrgent(fax);
				bean.setIsUrgent(isUrgent.equals("")?"普通":isUrgent);//特殊标识
				bean.setPiece(fax.getPiece()==null?"0":fax.getPiece()+"");
				bean.setWeight(fax.getCusWeight()==null?"0":fax.getCusWeight()+"");//代理重量
				bean.setBulk(fax.getBulk()==null?"0":fax.getBulk()+"");//设置体积
				
				bean.setReachDate("");
				statusList = this.oprStatusService.findBy("dno", fax.getDno());
				if(null!=statusList && statusList.size()>0){
					OprStatus status = statusList.get(0);
					if(null!=status.getReachTime() && status.getReachTime().toString().length()>1){
						bean.setReachDate(sdf2.format(status.getReachTime()));//从状态表中取点到日期
					}
					if("新邦".equals(fax.getDistributionMode()) && "市内自提".equals(fax.getTakeMode())){
						//判断如果是新邦市内自提的货，没有出库则不允许打印
						if(!status.getOutStatus().equals(1l) && !status.getOutStatus().equals(2l)){
							notOutDno+=fax.getDno()+",";
							continue;
						}
					}
				}else{
					continue;
				}
				requestList = this.oprRequestDoService.findBy("dno", fax.getDno());
				
				if(null!=requestList && requestList.size()>0){
					for (int j = 0; j < requestList.size(); j++) {
						if(requestList.get(j).getRequestStage().equals("送货")){
							String str="";
							if(fax.getUrgentService()!=null&&fax.getUrgentService()==1l){
								str+="加急 ";
							}
							bean.setRequest(str+=requestList.get(j).getRequest());//从个性化要求表中取 送货阶段
						}else if(requestList.get(j).getRequestStage().equals("签收")){
							bean.setSignRequire(requestList.get(j).getRequest());////从个性化要求表中取 签收阶段
						}
					}
				}else{
					if(fax.getUrgentService()!=null&&fax.getUrgentService()==1l){
						bean.setRequest("加急");
					}
				}
				
				bean.setSignType(fax.getReceiptType()==null?"":fax.getReceiptType());//签单就是回单
				bean.setStartDepartName(fax.getInDepart()==null?"":fax.getInDepart());
				bean.setSubNo(fax.getSubNo()==null?"":fax.getSubNo());
				bean.setTakeMode(fax.getTakeMode()==null?"":fax.getTakeMode());
				bean.setToWhere(fax.getGowhere()==null?"":fax.getGowhere());//去向 供应商
				bean.setTraceTo(fax.getRealGoWhere()==null?"":fax.getRealGoWhere());//实际去向(去预配去向)
				
				receiptList = this.oprReceiptService.findBy("dno", fax.getDno());
				if(null!=receiptList && receiptList.size()>0){
					OprReceipt receipt = receiptList.get(0);
					if(null ==receipt.getPrintNum()){
						receipt.setPrintNum(1l);
					}else{
						receipt.setPrintNum(receipt.getPrintNum()+1l);
					}
					bean.setPrintNum(receipt.getPrintNum());//打印次数
					this.oprReceiptService.save(receipt);
				}
				bean.setSourceNo(fax.getDno()+"");//实配单号
				bean.setPrintName(user.get("name")+"");//打印人
				bean.setPrintTime(sdf.format(new Date()));//打印时间 
				//设置是否早班 
				//早班标准是否客户已经确认为12点之前
				if(fax.getFlightDate().getHours()<12){
					bean.setMorningflag("1");
				}else{
					bean.setMorningflag("0");
				}
				bean.setDistributionMode(fax.getDistributionMode()==null?"":fax.getDistributionMode());
				bean.setSpecialSign(getSpecialSign(fax));
				if(fax.getWait()==1l){
					bean.setConsigneeInfo("等通知放货");
				}
				//设置仓储费
				List<OprValueAddFee> valueList = this.oprValueAddFeeService.find("from OprValueAddFee where dno=? and feeName=?", fax.getDno(),"仓储费");
				Double stockAddFee = 0d;
				if(null!=valueList && valueList.size()>0){
					for(OprValueAddFee fee : valueList){
						stockAddFee+=fee.getFeeCount();
					}
				}
				//去除仓存费
				bean.setConsigneeFee((Double.valueOf(bean.getConsigneeFee())-stockAddFee)+"");
				bean.setStockAddFee(stockAddFee==0d?"0.0":(stockAddFee+""));
				if(isChange(fax.getDno())){
					changeDno+=fax.getDno()+",";
				}
				list.add(bean);
			}
			//判断是否有更改未审核
			if(null!=changeDno && !"".equals(changeDno)){
				mainbill.setMsg((mainbill.getMsg()==null?"":mainbill.getMsg()+"--")+"配送单号为"+changeDno+"的货物有做更改，还未审核！");
			}
			if(null!=notOutDno && !"".equals(notOutDno)){
				mainbill.setMsg((mainbill.getMsg()==null?"":mainbill.getMsg()+"--")+"配送单号为"+notOutDno+"的货物为新邦市内自提的货物，请先在自提出库里面进行出库！");
			}
		}catch (Exception e) {
			mainbill.setMsg((mainbill.getMsg()==null?"":mainbill.getMsg()+"--")+e.getLocalizedMessage());
		}
		return list;
	}

	/**获取特殊字符
	 * @param fax
	 * @return
	 */
	private String getSpecialSign(OprFaxIn fax) {
		
		List<CusRecord> recordList = this.cusRecordService.find("from CusRecord where cusId=?", fax.getCusId());
		if(null!=recordList && recordList.size()>0){
			CusRecord record = recordList.get(0);
			if(record.getImportanceLevel().equals("VIP客户")){
				return "VIP";
			}else if(record.getImportanceLevel().equals("重点客户")){
				return "重";
			}else if(record.getImportanceLevel().equals("项目客户")){
				return "项";
			}
		}
		return "";
	}

	private OprBillladingPrintManager getBillladingPrintManager(BillLadingList mainbill,OprFaxIn fax) {
		
		String hql ="from OprBillladingPrintManager where sonderzug=? and takeMode=? and distributionMode=? and departId=?";
		List<OprBillladingPrintManager> list = this.oprBillladingPrintManagerService.find(hql, fax.getSonderzug(),fax.getTakeMode(),fax.getDistributionMode(),fax.getInDepartId());
		if(null!=list && list.size()>0){
			return list.get(0);
		}else{
			hql="from OprBillladingPrintManager where sonderzug is null and takeMode is null and distributionMode is null and departId=?";
			list = this.oprBillladingPrintManagerService.find(hql, fax.getInDepartId());
			if(null!=list && list.size()>0){
				return list.get(0);
			}else{
				mainbill.setMsg("没有该部门信息，请维护！");
				return null;
			}
		}
	}

	/**获取特殊标识
	 * @param fax
	 * @return
	 */
	private String setIsUrgent(OprFaxIn fax) {
		String isUrgent="";
//		if(fax.getGreenChannel()==1l){
//			isUrgent+="VIP";
//		}
		if(fax.getSonderzug()==1l){
			isUrgent+=" 专车";
		}
		if(fax.getWait()==1l){
			isUrgent+=" 等通知放货";
		}
		return isUrgent;
	}

	/* (non-Javadoc)打印完毕后处理事项
	 * @see com.xbwl.report.print.printServiceInterface.IPrintServiceInterface#afterPrint(com.xbwl.report.print.bean.PrintBean)
	 */
	public void afterPrint(PrintBean bean) {
		logger.debug("提货单打印完毕！");
		BillLading billLading = (BillLading)bean;
		User user = WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
		try {
			List<OprReceipt> list = this.oprReceiptService.findBy("dno", Long.valueOf(billLading.getDno()));
			if(null!=list && list.size()>0){
				OprReceipt receipt = list.get(0);
				if("新邦货单".equals(receipt.getReceiptType()) && receipt.getReachStatus()!=1l){
					receipt.setReachMan(user.get("name")+"");
					receipt.setReachNum(1l);//新邦货单默认为一份
					receipt.setReachStatus(1l);//修改为已经点到状态
					receipt.setReachTime(new Date());//系统默认时间
					receipt.setCurStatus("已入库");
				    this.oprReceiptService.saveReportService(receipt,receipt.getId());
				}
			}
			
			//写入日志
			this.oprHistoryService.saveLog(Long.valueOf(billLading.getDno()), billLading.getPrintName()+" 打印第"+billLading.getPrintNum()+"份提货单！", this.log_print);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean isChange(Long dno){
		//状态，0，删除，1，未审核，2，已审核，3，审核不通过，4，已知会
		Long notAuditStatus=1l;
		String hql = "from OprChangeMain where dno=? and status=?";
		List<OprChangeMain> list = this.oprFaxChangeService.find(hql, dno,notAuditStatus);
		if(null!=list && list.size()>0){
			return true;
		}
		
		return false;
	}
}

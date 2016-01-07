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
 * �������ӡ�����ʵ����
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
	
	//����̫����ע�����������ע����ش�����һ��
	public List<PrintBean> setPrintBeanList(BillLadingList mainbill,Map<String, String> map) {
		List<PrintBean> list= new ArrayList<PrintBean>();
		try{
			String dno = map.get("dnos");
			String prewiredId = map.get("prewiredIds");
			if(null==dno || "".equals(dno)){
				if(null==prewiredId || "".equals(prewiredId)){
					mainbill.setMsg("�봫���͵��Ż���Ԥ�䵥�Ź�����");
					return list;
				}
			}
			User user = WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH");
			SimpleDateFormat sdfSign = new SimpleDateFormat("MM��dd��HHʱmm��");
			
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
				bean.setConsigneeFee(NumberUtil.numberAdd(fax.getConsigneeFee(),fax.getCusValueAddFee(),fax.getSonderzugPrice())+"");//������ֵ�����
				bean.setPaymentCollection(fax.getPaymentCollection()==null?"0.0":fax.getPaymentCollection()+"");
				bean.setConsigneeInfo(fax.getConsignee()+"/"+fax.getConsigneeTel()+"/"+fax.getAddr());
				bean.setCpValueAddFee(fax.getCusValueAddFee()==null?"0.0":fax.getCusValueAddFee()+"");//��ֵ�����
				signList  =  this.oprSignService.findBy("dno", fax.getDno());
				if(null!=signList && signList.size()>0){
					OprSign sign = signList.get(0);
					String signMan=sign.getSignMan()==null?"":sign.getSignMan();
					bean.setCardId(sign.getReIdentityCard()==null?(sign.getIdentityCard()==null?"":sign.getIdentityCard()):sign.getReIdentityCard());//ǩ��֤������
					bean.setSignMan(signMan);//����ǩ����
					if(null!=sign.getCreateTime()){
						bean.setSignTime(sdfSign.format(sign.getCreateTime()));
					}
				}else{
					bean.setSignTime("  ��    ��    ʱ    ��");
				}
				bean.setComplainTel("");
				bean.setContext("");
				bean.setTitle("");
				billladingPrintManager = getBillladingPrintManager(mainbill,fax);
				if(null!=billladingPrintManager){
					bean.setComplainTel(billladingPrintManager.getComplaintTel()==null?"":billladingPrintManager.getComplaintTel());//��������Ҫ�½���
					bean.setContext(billladingPrintManager.getLeftContext()==null?"":billladingPrintManager.getLeftContext());//��������Ҫ�½���
					bean.setTitle(billladingPrintManager.getTitle());
				}
				bean.setCountFee(NumberUtil.numberAdd(Double.valueOf(bean.getConsigneeFee()),fax.getPaymentCollection())+"");
				bean.setCqName(fax.getCpName()==null?"":fax.getCpName());
				bean.setCusService(fax.getCustomerService()==null?"":fax.getCustomerService());//
				bean.setCusServiceTel(this.userService.findBy("userName", fax.getCustomerService()).get(0).getTelPhone());//��Ա�����л�ȡ��ͨ���ͷ�Ա����
				bean.setDno(fax.getDno()+"");
				flightList = this.basFlightService.findBy("flightNumber", fax.getFlightNo());
				if(null!=flightList && flightList.size()>0){
					bean.setFlightInfo(fax.getFlightNo()==null?"":fax.getFlightNo()+"/"+flightList.get(0).getStartCity());//������Ϣ(�����+ʼ������)
				}else{
					bean.setFlightInfo(fax.getFlightNo()==null?"":fax.getFlightNo());
				}
				bean.setFlightMainNo(fax.getFlightMainNo()==null?"":fax.getFlightMainNo());
				String isUrgent = setIsUrgent(fax);
				bean.setIsUrgent(isUrgent.equals("")?"��ͨ":isUrgent);//�����ʶ
				bean.setPiece(fax.getPiece()==null?"0":fax.getPiece()+"");
				bean.setWeight(fax.getCusWeight()==null?"0":fax.getCusWeight()+"");//��������
				bean.setBulk(fax.getBulk()==null?"0":fax.getBulk()+"");//�������
				
				bean.setReachDate("");
				statusList = this.oprStatusService.findBy("dno", fax.getDno());
				if(null!=statusList && statusList.size()>0){
					OprStatus status = statusList.get(0);
					if(null!=status.getReachTime() && status.getReachTime().toString().length()>1){
						bean.setReachDate(sdf2.format(status.getReachTime()));//��״̬����ȡ�㵽����
					}
					if("�°�".equals(fax.getDistributionMode()) && "��������".equals(fax.getTakeMode())){
						//�ж�������°���������Ļ���û�г����������ӡ
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
						if(requestList.get(j).getRequestStage().equals("�ͻ�")){
							String str="";
							if(fax.getUrgentService()!=null&&fax.getUrgentService()==1l){
								str+="�Ӽ� ";
							}
							bean.setRequest(str+=requestList.get(j).getRequest());//�Ӹ��Ի�Ҫ�����ȡ �ͻ��׶�
						}else if(requestList.get(j).getRequestStage().equals("ǩ��")){
							bean.setSignRequire(requestList.get(j).getRequest());////�Ӹ��Ի�Ҫ�����ȡ ǩ�ս׶�
						}
					}
				}else{
					if(fax.getUrgentService()!=null&&fax.getUrgentService()==1l){
						bean.setRequest("�Ӽ�");
					}
				}
				
				bean.setSignType(fax.getReceiptType()==null?"":fax.getReceiptType());//ǩ�����ǻص�
				bean.setStartDepartName(fax.getInDepart()==null?"":fax.getInDepart());
				bean.setSubNo(fax.getSubNo()==null?"":fax.getSubNo());
				bean.setTakeMode(fax.getTakeMode()==null?"":fax.getTakeMode());
				bean.setToWhere(fax.getGowhere()==null?"":fax.getGowhere());//ȥ�� ��Ӧ��
				bean.setTraceTo(fax.getRealGoWhere()==null?"":fax.getRealGoWhere());//ʵ��ȥ��(ȥԤ��ȥ��)
				
				receiptList = this.oprReceiptService.findBy("dno", fax.getDno());
				if(null!=receiptList && receiptList.size()>0){
					OprReceipt receipt = receiptList.get(0);
					if(null ==receipt.getPrintNum()){
						receipt.setPrintNum(1l);
					}else{
						receipt.setPrintNum(receipt.getPrintNum()+1l);
					}
					bean.setPrintNum(receipt.getPrintNum());//��ӡ����
					this.oprReceiptService.save(receipt);
				}
				bean.setSourceNo(fax.getDno()+"");//ʵ�䵥��
				bean.setPrintName(user.get("name")+"");//��ӡ��
				bean.setPrintTime(sdf.format(new Date()));//��ӡʱ�� 
				//�����Ƿ���� 
				//����׼�Ƿ�ͻ��Ѿ�ȷ��Ϊ12��֮ǰ
				if(fax.getFlightDate().getHours()<12){
					bean.setMorningflag("1");
				}else{
					bean.setMorningflag("0");
				}
				bean.setDistributionMode(fax.getDistributionMode()==null?"":fax.getDistributionMode());
				bean.setSpecialSign(getSpecialSign(fax));
				if(fax.getWait()==1l){
					bean.setConsigneeInfo("��֪ͨ�Ż�");
				}
				//���òִ���
				List<OprValueAddFee> valueList = this.oprValueAddFeeService.find("from OprValueAddFee where dno=? and feeName=?", fax.getDno(),"�ִ���");
				Double stockAddFee = 0d;
				if(null!=valueList && valueList.size()>0){
					for(OprValueAddFee fee : valueList){
						stockAddFee+=fee.getFeeCount();
					}
				}
				//ȥ���ִ��
				bean.setConsigneeFee((Double.valueOf(bean.getConsigneeFee())-stockAddFee)+"");
				bean.setStockAddFee(stockAddFee==0d?"0.0":(stockAddFee+""));
				if(isChange(fax.getDno())){
					changeDno+=fax.getDno()+",";
				}
				list.add(bean);
			}
			//�ж��Ƿ��и���δ���
			if(null!=changeDno && !"".equals(changeDno)){
				mainbill.setMsg((mainbill.getMsg()==null?"":mainbill.getMsg()+"--")+"���͵���Ϊ"+changeDno+"�Ļ����������ģ���δ��ˣ�");
			}
			if(null!=notOutDno && !"".equals(notOutDno)){
				mainbill.setMsg((mainbill.getMsg()==null?"":mainbill.getMsg()+"--")+"���͵���Ϊ"+notOutDno+"�Ļ���Ϊ�°���������Ļ���������������������г��⣡");
			}
		}catch (Exception e) {
			mainbill.setMsg((mainbill.getMsg()==null?"":mainbill.getMsg()+"--")+e.getLocalizedMessage());
		}
		return list;
	}

	/**��ȡ�����ַ�
	 * @param fax
	 * @return
	 */
	private String getSpecialSign(OprFaxIn fax) {
		
		List<CusRecord> recordList = this.cusRecordService.find("from CusRecord where cusId=?", fax.getCusId());
		if(null!=recordList && recordList.size()>0){
			CusRecord record = recordList.get(0);
			if(record.getImportanceLevel().equals("VIP�ͻ�")){
				return "VIP";
			}else if(record.getImportanceLevel().equals("�ص�ͻ�")){
				return "��";
			}else if(record.getImportanceLevel().equals("��Ŀ�ͻ�")){
				return "��";
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
				mainbill.setMsg("û�иò�����Ϣ����ά����");
				return null;
			}
		}
	}

	/**��ȡ�����ʶ
	 * @param fax
	 * @return
	 */
	private String setIsUrgent(OprFaxIn fax) {
		String isUrgent="";
//		if(fax.getGreenChannel()==1l){
//			isUrgent+="VIP";
//		}
		if(fax.getSonderzug()==1l){
			isUrgent+=" ר��";
		}
		if(fax.getWait()==1l){
			isUrgent+=" ��֪ͨ�Ż�";
		}
		return isUrgent;
	}

	/* (non-Javadoc)��ӡ��Ϻ�������
	 * @see com.xbwl.report.print.printServiceInterface.IPrintServiceInterface#afterPrint(com.xbwl.report.print.bean.PrintBean)
	 */
	public void afterPrint(PrintBean bean) {
		logger.debug("�������ӡ��ϣ�");
		BillLading billLading = (BillLading)bean;
		User user = WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
		try {
			List<OprReceipt> list = this.oprReceiptService.findBy("dno", Long.valueOf(billLading.getDno()));
			if(null!=list && list.size()>0){
				OprReceipt receipt = list.get(0);
				if("�°����".equals(receipt.getReceiptType()) && receipt.getReachStatus()!=1l){
					receipt.setReachMan(user.get("name")+"");
					receipt.setReachNum(1l);//�°����Ĭ��Ϊһ��
					receipt.setReachStatus(1l);//�޸�Ϊ�Ѿ��㵽״̬
					receipt.setReachTime(new Date());//ϵͳĬ��ʱ��
					receipt.setCurStatus("�����");
				    this.oprReceiptService.saveReportService(receipt,receipt.getId());
				}
			}
			
			//д����־
			this.oprHistoryService.saveLog(Long.valueOf(billLading.getDno()), billLading.getPrintName()+" ��ӡ��"+billLading.getPrintNum()+"���������", this.log_print);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean isChange(Long dno){
		//״̬��0��ɾ����1��δ��ˣ�2������ˣ�3����˲�ͨ����4����֪��
		Long notAuditStatus=1l;
		String hql = "from OprChangeMain where dno=? and status=?";
		List<OprChangeMain> list = this.oprFaxChangeService.find(hql, dno,notAuditStatus);
		if(null!=list && list.size()>0){
			return true;
		}
		
		return false;
	}
}

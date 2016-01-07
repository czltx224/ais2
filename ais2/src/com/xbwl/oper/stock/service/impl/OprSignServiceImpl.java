package com.xbwl.oper.stock.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.ralasafe.WebRalasafe;
import org.ralasafe.user.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.log.anno.ModuleName;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.orm.PropertyFilter;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.common.utils.DoubleUtil;
import com.xbwl.common.utils.LogType;
import com.xbwl.entity.OprFaxIn;
import com.xbwl.entity.OprLoadingbrigadTime;
import com.xbwl.entity.OprLoadingbrigadeWeight;
import com.xbwl.entity.OprRequestDo;
import com.xbwl.entity.OprSign;
import com.xbwl.entity.OprStatus;
import com.xbwl.entity.OprStock;
import com.xbwl.entity.OprValueAddFee;
import com.xbwl.entity.SysDepart;
import com.xbwl.finance.dto.IFiInterface;
import com.xbwl.finance.dto.impl.FiInterfaceProDto;
import com.xbwl.oper.fax.dao.IOprFaxInDao;
import com.xbwl.oper.stock.dao.IOprLoadingbrigadeTimeDao;
import com.xbwl.oper.stock.dao.IOprRequestDoDao;
import com.xbwl.oper.stock.dao.IOprSignDao;
import com.xbwl.oper.stock.dao.IOprStatusDao;
import com.xbwl.oper.stock.dao.IOprStockDao;
import com.xbwl.oper.stock.dao.IOprValueAddFeeDao;
import com.xbwl.oper.stock.service.IOprHistoryService;
import com.xbwl.oper.stock.service.IOprLoadingbrigadeWeightService;
import com.xbwl.oper.stock.service.IOprSignService;
import com.xbwl.oper.stock.service.IOprStatusService;
import com.xbwl.oper.stock.vo.LoadingbrigadeTypeEnum;
import com.xbwl.rbac.Service.IDepartService;

 

/**
 *����������
 * @author shuw
 *@2011-7-18
 */

@Service("oprSignServiceImpl")
@Transactional(rollbackFor={Exception.class})
public class OprSignServiceImpl  extends BaseServiceImpl<OprSign, Long>
			implements IOprSignService{
	private static final String SIGN_REQUEST_STAGE="ǩ��";
	@Resource(name="oprValueAddFeeHibernateDaoImpl")
	private IOprValueAddFeeDao oprValueAddFeeDao;
	
	@Resource(name="oprSignHibernateDaoImpl")
	private IOprSignDao oprSignDao;
	
	@Resource(name="oprStatusHibernateDaoImpl")
	private IOprStatusDao oprStatusDao;

	@Value("${oprSignServiceImpl.log_selfOutStock}")
	private Long log_selfOutStock ;
	
	@Value("${oprSignAction.log_inputSignReturn}")
	private Long log_inputSignReturn ;
	
	@Resource(name = "oprLoadingbrigadeTimeHibernateDaoImpl")
	private IOprLoadingbrigadeTimeDao oprLoadingbrigadeTimeDao;
	
	@Resource(name = "oprLoadingbrigadeWeightServiceImpl")
	private IOprLoadingbrigadeWeightService oprLoadingbrigadeWeightService;
	
	@Resource(name = "oprFaxInHibernateDaoImpl")
	private IOprFaxInDao oprFaxInDao;
	
	@Resource(name = "oprHistoryServiceImpl")
	private IOprHistoryService oprHistoryService;
	
	@Value("${oprSignAction.log_inputSign}")
	private Long log_inputSign;//ǩ��¼��
	
	@Value("${oprSignAction.log_signException}")
	private Long log_signException;//�쳣ǩ��
	
	@Resource(name="oprStatusServiceImpl")
	private IOprStatusService oprStatusService;
	
	@Resource(name = "oprStockDaoImpl")
	private IOprStockDao oprStockDao;
	
	@Resource(name = "fiInterfaceImpl")
	private IFiInterface iFiInterface;
	@Resource(name = "oprRequestDoHibernateDaoImpl")
	private IOprRequestDoDao oprRequestDoDao;
	
	@Override
	public IBaseDAO<OprSign, Long> getBaseDao() {
		return oprSignDao;
	}
	
	@Resource(name="departServiceImpl")
	private IDepartService departService;
	
	@Value("${oprSignServiceImpl.takeMode}")
	private String takeMode;
	
	@Override
	public void save(OprSign entity) {
		try{
			User user = WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
				OprStatus os=oprStatusService.findStatusByDno(entity.getDno());
				if(os==null){
					throw new ServiceException("�����쳣��״̬�����ڸ����͵��Ļ��");
				}
				if(os.getOutStatus()==0l){
					throw new ServiceException("��Ʊ����û�г��⣬������ǩ�գ�");
				}else if(os.getOutStatus()==3l){
					throw new ServiceException("��Ʊ������Ԥ��׶Σ�������ǩ�գ�");
				}
				List<OprSign> signList= this.oprSignDao.findBy("dno",entity.getDno());
				if(signList.size()>0){
					throw new ServiceException("���͵���Ϊ"+entity.getDno()+"�Ļ����Ѿ�ǩ�գ�");
				}
				String signMan = entity.getSignMan();
				if(null==entity.getSignMan() || "".equals(entity.getSignMan().trim())){
					signMan = entity.getReplaceSign();//��ǩ��
				}
				os.setSignStatus(1L);
				os.setSignTime(new Date());
				os.setSignName(signMan);
				oprStatusService.save(os);
				
				if(entity.getIsSignException()==1l){
					//�쳣ǩ��Ҫ���쳣��¼
					oprHistoryService.saveLog(entity.getDno(), signMan+"�쳣ǩ�������͵���Ϊ"+entity.getDno()+"�Ļ����ע:"+entity.getRemark(), this.log_signException);
				}
				oprHistoryService.saveLog(entity.getDno(), signMan+"ǩ�������͵���Ϊ"+entity.getDno()+"�Ļ���", log_inputSign);
				Date dt = new Date();
				if(null==entity.getId()){
					entity.setCreateName(user.get("name")+"");
					entity.setCreateTime(dt);
				}
				entity.setUpdateName(user.get("name")+"");
				entity.setUpdateTime(dt);
				entity.setTs(dt.getTime()+"");
				entity.setDepartId(Long.valueOf(user.get("bussDepart")+""));
				super.save(entity);
				
			}catch(Exception e){
				throw new ServiceException(e.getLocalizedMessage());
			}
		
	}



	/**
	 * ����������ݱ��棬���浽���ű���
	 */
	@ModuleName(value="�������ǩ��",logType=LogType.buss)
	public void saveSignStatusByFaxIn(OprSign oprSign,Double storeFee,
			Double  consigneeRate,Double  consigneeFee,Double totalCusValueAddFee)  throws Exception{
			User user= WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
			String userName=user.get("name")+"";
			oprSign.setIsSignException(0l);
			oprSign.setCreateName(userName);
			oprSign.setCreateTime(new Date());
			oprSign.setUpdateName(userName);
			oprSign.setUpdateTime(new Date());
			oprSignDao.save(oprSign);
			
			List<OprStatus> listsu =oprStatusDao.findBy("dno",oprSign.getDno());
		  	OprStatus oprStatus=null;
			if(listsu.size()==0){
				throw new ServiceException("�Ҳ�����Ʊ����״̬��¼");
			}else {
				oprStatus=listsu.get(0);
			}
			
			if(oprStatus.getSignStatus()!=null&&oprStatus.getSignStatus()==1l){
				throw new ServiceException("������ǩ�գ������ظ�ǩ��");
			}
			
			oprStatus.setSignStatus(1l);
			oprStatus.setOutTime(new Date());
			oprStatus.setOutStatus(1l);
			oprStatus.setSignTime(new Date());
			oprStatusDao.save(oprStatus);
			
			List<PropertyFilter>filters= new ArrayList<PropertyFilter>();
			DateFormat df=new SimpleDateFormat("HH:mm:ss");
			String dateString =df.format(new Date());
			String nowDate ="1970-01-01 ".concat(dateString);
		//	Date foDate=dfnow.parse(nowDate);
			filters.add(new PropertyFilter("LED_startDate",nowDate));
			filters.add(new PropertyFilter("GED_endDate",nowDate));
			filters.add(new PropertyFilter("EQL_departId",oprSign.getDepartId()+""));
			List<OprLoadingbrigadTime> list =oprLoadingbrigadeTimeDao.find(filters);
			
			StringBuffer sb = new StringBuffer(" ");
			OprFaxIn fax=oprFaxInDao.get(oprSign.getDno());   
			if(DoubleUtil.sub(fax.getConsigneeRate(), consigneeRate)!=0.0){
				sb.append("   �������ͷ���").append(fax.getConsigneeRate()).append("�ĳ�").append(consigneeRate);
			}
			if(DoubleUtil.sub(fax.getConsigneeFee(), consigneeFee)!=0.0){
				sb.append("   �������ͷ�").append(fax.getConsigneeFee()).append("�ĳ�").append(consigneeFee);
			}
			
			List<FiInterfaceProDto> listfiIneDto=new ArrayList<FiInterfaceProDto>();

			List<SysDepart>  list2= departService.find("from SysDepart o where o.departName=? ",fax.getCusDepartName());
			if(list2.size()!=1){
				throw  new ServiceException("�Ҳ����ͷ�����");
			}
			
			if(DoubleUtil.sub(fax.getConsigneeFee(),consigneeFee)!=0.0){  //�޸Ĺ��ĵ������ͷ� ���д�������
				FiInterfaceProDto fidoDto = new FiInterfaceProDto();
				fidoDto.setDno(fax.getDno());// ���͵���(�������)
				fidoDto.setCustomerId(fax.getCusId());
				fidoDto.setCustomerName(fax.getCpName());
				fidoDto.setAmount(DoubleUtil.sub(consigneeFee,fax.getConsigneeFee()));
				fidoDto.setCostType("�������ͷ�");
				fidoDto.setSourceData("���ᵥ");
				fidoDto.setSourceNo(fax.getDno());
				fidoDto.setWhoCash("����");
				fidoDto.setCustomerName(fax.getCustomerService());
				fidoDto.setIncomeDepart(fax.getInDepart());
				fidoDto.setIncomeDepartId(fax.getInDepartId());
				fidoDto.setAdmDepart(fax.getCusDepartName());
				fidoDto.setAdmDepartId(list2.get(0).getDepartId()	);
				listfiIneDto.add(fidoDto);
			}
			
			if(DoubleUtil.sub(fax.getCusValueAddFee(),totalCusValueAddFee)!=0.0){  //�޸Ĺ��ĵ�����ֵ�� ���д�������
				FiInterfaceProDto fidoDto = new FiInterfaceProDto();
				fidoDto.setDno(fax.getDno());// ���͵���(�������)
				fidoDto.setCustomerId(fax.getCusId());
				fidoDto.setCustomerName(fax.getCpName());
				fidoDto.setAmount(DoubleUtil.sub(totalCusValueAddFee,fax.getCusValueAddFee()));
				fidoDto.setCostType("������ֵ��");
				fidoDto.setSourceData("���ᵥ");
				fidoDto.setSourceNo(fax.getDno());
				fidoDto.setWhoCash("����");
				fidoDto.setCustomerName(fax.getCustomerService());
				fidoDto.setIncomeDepart(fax.getInDepart());
				fidoDto.setIncomeDepartId(fax.getInDepartId());
				fidoDto.setAdmDepart(fax.getCusDepartName());
				fidoDto.setAdmDepartId(list2.get(0).getDepartId()	);
				listfiIneDto.add(fidoDto);
			}
			
			if(listfiIneDto.size()!=0){
				iFiInterface.currentToFiIncome(listfiIneDto);
			}

			fax.setConsigneeFee(consigneeFee);             //���´���¼���
			fax.setConsigneeRate(consigneeRate);
			fax.setCusValueAddFee(totalCusValueAddFee);
			
			if(storeFee>0.0){
				OprValueAddFee oprValueAddFee = new OprValueAddFee();
				oprValueAddFee.setDno(oprSign.getDno());
				oprValueAddFee.setFeeCount(storeFee);
				oprValueAddFee.setFeeName("�ִ���");
				oprValueAddFee.setStatus(0l);
				oprValueAddFee.setPayType("����");
				oprValueAddFeeDao.save(oprValueAddFee);
				
			}
			List<OprLoadingbrigadeWeight> weightList = new ArrayList<OprLoadingbrigadeWeight>();
			if(list.size()>0 && fax.getTakeMode().equals(this.takeMode)){//�����������������װж�����������������¼
				 Long wid =list.get(0).getLoadingbrigadId();
				 Long wid2 =list.get(0).getGroupId();
				  OprLoadingbrigadeWeight olbWeight  = new OprLoadingbrigadeWeight();
				  olbWeight.setDno(fax.getDno());
				  olbWeight.setLoadingbrigadeId(wid);
				  olbWeight.setPiece(fax.getPiece());
				  olbWeight.setBulk(fax.getBulk());
				  olbWeight.setWeight(fax.getCqWeight());
				  olbWeight.setGoods(fax.getGoods());
				  olbWeight.setDispatchId(wid2);
				  olbWeight.setDepartId(oprSign.getDepartId());
				  
				  weightList.add(olbWeight);
				  oprLoadingbrigadeWeightService.saveLoadingWeight(weightList,LoadingbrigadeTypeEnum.TIHUO);
			}
			
			if(oprStatus.getIsCreateFi()==null||oprStatus.getIsCreateFi()==0l){
				
					List<FiInterfaceProDto> listfiInterfaceDto =new ArrayList<FiInterfaceProDto>();
					FiInterfaceProDto fiInterfaceProDto = new FiInterfaceProDto();
					fiInterfaceProDto.setOutStockMode(fax.getTakeMode());
					fiInterfaceProDto.setDno(oprSign.getDno());
					fiInterfaceProDto.setSettlementType(1l);
					fiInterfaceProDto.setDocumentsType("����");
					fiInterfaceProDto.setDocumentsSmalltype("���͵�");
					fiInterfaceProDto.setDocumentsNo(oprSign.getDno());
					fiInterfaceProDto.setAmount(fax.getConsigneeFee());
					fiInterfaceProDto.setCostType("�������ͷ�");
					fiInterfaceProDto.setIncomeDepartId(fax.getInDepartId());
					fiInterfaceProDto.setDepartId(oprSign.getDepartId());
					fiInterfaceProDto.setSourceData("���ᵥ");
					fiInterfaceProDto.setSourceNo(oprSign.getDno());
					fiInterfaceProDto.setCollectionUser(userName);
					fiInterfaceProDto.setContacts(userName);
					fiInterfaceProDto.setDepartId(Long.valueOf(user.get("bussDepart")+""));
					fiInterfaceProDto.setDepartName(user.get("rightDepart")+"");
					listfiInterfaceDto.add(fiInterfaceProDto);
					
					if(fax.getCusValueAddFee()!=null&&fax.getCusValueAddFee()!=0){
						FiInterfaceProDto fiInterfaceProDto2 = new FiInterfaceProDto();
						fiInterfaceProDto2.setOutStockMode(fax.getTakeMode());
						fiInterfaceProDto2.setDno(oprSign.getDno());
						fiInterfaceProDto2.setSettlementType(1l);
						fiInterfaceProDto2.setDocumentsType("����");
						fiInterfaceProDto2.setDocumentsSmalltype("���͵�");
						fiInterfaceProDto2.setDocumentsNo(oprSign.getDno());
						fiInterfaceProDto2.setAmount(fax.getCusValueAddFee());
						fiInterfaceProDto2.setCostType("������ֵ��");
						fiInterfaceProDto2.setIncomeDepartId(fax.getInDepartId());
						fiInterfaceProDto2.setDepartId(oprSign.getDepartId());
						fiInterfaceProDto2.setSourceData("���ᵥ");
						fiInterfaceProDto2.setSourceNo(oprSign.getDno());
						fiInterfaceProDto2.setCollectionUser(userName);
						fiInterfaceProDto2.setContacts(userName);
						fiInterfaceProDto2.setDepartId(Long.valueOf(user.get("bussDepart")+""));
						fiInterfaceProDto2.setDepartName(user.get("rightDepart")+"");
						
						listfiInterfaceDto.add(fiInterfaceProDto2);
					}
				
					if(fax.getPaymentCollection()!=null&&fax.getPaymentCollection()!=0){
						FiInterfaceProDto fiInterfaceProDto3 = new FiInterfaceProDto();
						fiInterfaceProDto3.setOutStockMode(fax.getTakeMode());
						fiInterfaceProDto3.setDno(oprSign.getDno());
						fiInterfaceProDto3.setSettlementType(1l);
						fiInterfaceProDto3.setIncomeDepartId(fax.getInDepartId());
						fiInterfaceProDto3.setDocumentsType("���ջ���");
						fiInterfaceProDto3.setDocumentsSmalltype("���͵�");
						fiInterfaceProDto3.setDocumentsNo(oprSign.getDno());
						fiInterfaceProDto3.setAmount(fax.getPaymentCollection());
						fiInterfaceProDto3.setCostType("���ջ���");
						fiInterfaceProDto3.setDepartId(oprSign.getDepartId());
						fiInterfaceProDto3.setSourceData("���ᵥ");
						fiInterfaceProDto3.setSourceNo(oprSign.getDno());
						fiInterfaceProDto3.setCollectionUser(userName);
						fiInterfaceProDto3.setContacts(userName);
						fiInterfaceProDto3.setDepartId(Long.valueOf(user.get("bussDepart")+""));
						fiInterfaceProDto3.setDepartName(user.get("rightDepart")+"");
						listfiInterfaceDto.add(fiInterfaceProDto3);
					}
					iFiInterface.outStockToFi(listfiInterfaceDto);
					oprStatus.setIsCreateFi(1l);
			}
			
			if("".equals(oprSign.getReplaceSign()) || oprSign.getReplaceSign()==null){
				fax.setRealGoWhere("������⣺"+oprSign.getSignMan());
				oprStatus.setSignName(oprSign.getSignMan());
			}else{
				fax.setRealGoWhere("������⣺"+oprSign.getReplaceSign());
				oprStatus.setSignName(oprSign.getReplaceSign());
			}
			
			List<OprStock> listo = oprStockDao.find(" from OprStock o where o.dno=? and o.departId=? ",oprSign.getDno(),oprSign.getDepartId());
			OprStock oStock= null;
			if(listo.size()==0){
				if("��������".equals(fax.getTakeMode())){
					oStock=new OprStock(fax.getDno(),fax.getFlightMainNo(),fax.getSubNo(),fax.getConsignee(),fax.getAddr(),fax.getCusWeight(),0l);
				}else{
					throw new ServiceException("��Ʊ���������Ϊ0�����ܽ������ǩ��");
				}
		  	}else{
		  		oStock=listo.get(0);
		  		if(!"��������".equals(fax.getTakeMode())&&oStock.getPiece()==0l){
					throw new ServiceException("��Ʊ���������Ϊ0�����ܽ������ǩ��");
				}
		  	}
		  //**********************************************
		  	oStock.setPiece(oStock.getPiece()-fax.getPiece());
		  	if(null!=oStock.getId() && oStock.getPiece().equals(0l)){
				//���û�п����ɾ��
				oprStockDao.delete(oStock);
			}else{
				oprStockDao.save(oStock);
			}
			oprFaxInDao.save(fax);
			
			if(oprSign.getReplaceSign()==null||"".equals(oprSign.getReplaceSign())){
				oprHistoryService.saveLog(oprSign.getDno(), "���������ǩ�գ�ǩ���ˣ�"+oprSign.getSignMan()+sb.toString(), log_selfOutStock);
			}else{
				oprHistoryService.saveLog(oprSign.getDno(), "���������ǩ�գ��ջ��ˣ�"+oprSign.getSignMan()+"��ǩ���ˣ�"+oprSign.getReplaceSign()+sb.toString(), log_selfOutStock);
			}
	}

	public void delSign(String[] dnos) throws Exception {
		
		if(null==dnos || "".equals(dnos) || dnos.length<1){
			throw new ServiceException("���������͵���!");
		}
		for (int i = 0; i < dnos.length; i++) {
			
			Long dno = Long.valueOf(dnos[i]);
			OprStatus status=oprStatusDao.findStatusByDno(dno);
			if(status.getCashStatus()==1l){
				throw new ServiceException("���͵���Ϊ"+dno+"�Ļ����Ѿ�����,��������!");
			}
			if(status.getSignStatus()!=1l){
				throw new ServiceException("���͵���Ϊ"+dno+"��û��ǩ��!");
			}
			List<OprRequestDo> list=oprRequestDoDao.find("from OprRequestDo ord where ord.dno=? and ord.requestStage=?", dno,SIGN_REQUEST_STAGE);
			//�����Ʊ�������ǩ�ս׶εĸ��Ի�Ҫ����ִ��״̬�޸�Ϊδִ��
			if(null!= list && list.size()>0){
				OprRequestDo ord=list.get(0);
				ord.setIsOpr(0L);
				ord.setOprMan("");
				oprRequestDoDao.save(ord);
			}
			status.setSignStatus(Long.valueOf(0));
			status.setSignName("");
			status.setSignTime(null);
			oprStatusDao.save(status);
			
			List<OprSign> signList = this.oprSignDao.findBy("dno",dno);
			for(OprSign sign : signList){
				this.delete(sign);
			}
			oprHistoryService.saveLog(dno, "���͵���Ϊ��"+dno+"�Ļ�����ǩ��¼�볷��", log_inputSignReturn);
		
		}
		
	}


	public Long getLog_selfOutStock() {
		return log_selfOutStock;
	}


	public void setLog_selfOutStock(Long log_selfOutStock) {
		this.log_selfOutStock = log_selfOutStock;
	}



	public void saveTask(OprSign sign) throws Exception {
		try{
				List<OprSign> signList= this.oprSignDao.findBy("dno",sign.getDno());
				OprFaxIn fax = this.oprFaxInDao.findUniqueBy("dno",sign.getDno());
				
				if(null==fax){
					return;
				}
				OprStatus status=oprStatusService.findStatusByDno(sign.getDno());
				
				if(null==status){
					throw new ServiceException("�����쳣�������ڴ˻��");
				}
				OprSign entity = null;
				if(signList.size()>0){
					entity = signList.get(0);
				}else{
					entity = sign;
				}
				String signMan = entity.getSignMan();
				if(null!=entity.getReplaceSign() && !"".equals(entity.getReplaceSign().trim())){
					signMan = entity.getReplaceSign();//��ǩ��
				}
				
				
				status.setSignStatus(1L);
				status.setSignTime(new Date());
				status.setSignName(signMan);
				oprStatusService.save(status);
				
				BeanUtils.copyProperties(sign, entity,new String[]{"id"});
				entity.setDepartId(fax.getInDepartId());//��¼��������Ϊǩ�յĲ���
				this.oprSignDao.save(entity);
				
			}catch(Exception e){
				e.printStackTrace();
				throw new ServiceException(e.getLocalizedMessage());
			}
	}
}

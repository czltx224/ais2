package com.xbwl.oper.stock.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.ralasafe.WebRalasafe;
import org.ralasafe.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.log.anno.ModuleName;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.orm.Page;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.common.utils.DoubleUtil;
import com.xbwl.common.utils.LogType;
import com.xbwl.entity.Customer;
import com.xbwl.entity.OprFaxIn;
import com.xbwl.entity.OprLoadingbrigadeWeight;
import com.xbwl.entity.OprOvermemo;
import com.xbwl.entity.OprOvermemoDetail;
import com.xbwl.entity.OprPrewired;
import com.xbwl.entity.OprPrewiredDetail;
import com.xbwl.entity.OprReceipt;
import com.xbwl.entity.OprReturnGoods;
import com.xbwl.entity.OprStatus;
import com.xbwl.entity.OprStock;
import com.xbwl.entity.SysDepart;
import com.xbwl.finance.dto.IFiInterface;
import com.xbwl.finance.dto.impl.FiInterfaceProDto;
import com.xbwl.oper.fax.dao.IOprFaxInDao;
import com.xbwl.oper.receipt.service.IOprReceiptService;
import com.xbwl.oper.stock.dao.IOprOvermemoDao;
import com.xbwl.oper.stock.dao.IOprPrewiredDao;
import com.xbwl.oper.stock.dao.IOprReturnGoodsDao;
import com.xbwl.oper.stock.dao.IOprStatusDao;
import com.xbwl.oper.stock.dao.IOprStockDao;
import com.xbwl.oper.stock.service.IOprHistoryService;
import com.xbwl.oper.stock.service.IOprLoadingbrigadeWeightService;
import com.xbwl.oper.stock.service.IOprPrewiredService;
import com.xbwl.oper.stock.vo.LoadingbrigadeTypeEnum;
import com.xbwl.oper.stock.vo.OprPrewiredVo;
import com.xbwl.rbac.dao.IDepartDao;
import com.xbwl.sys.service.ICustomerService;

/**
 * author shuw
 * time 2011-7-19 ����11:13:18
 */
 
/**
 * @author Administrator
 *
 */
@Service("oprPrewiredServiceImpl")
@Transactional(rollbackFor={Exception.class})
public class OprPrewiredServiceImpl   extends BaseServiceImpl<OprPrewired, Long>
	implements IOprPrewiredService{

	@Resource(name = "oprFaxInHibernateDaoImpl")
	private IOprFaxInDao oprFaxInDao;

	@Resource(name="oprPrewiredHibernateDaoImpl")
	private IOprPrewiredDao oprPrewiredDao;
	
	@Resource(name="oprOvermemoHibernateDaoImpl")
	private IOprOvermemoDao oprOvermemoDao;

	@Resource(name="oprStockDaoImpl")
	private IOprStockDao oprStockDao;
	
	@Resource(name = "oprStatusHibernateDaoImpl")
	private IOprStatusDao oprStatusDao;
	
	@Resource(name = "oprLoadingbrigadeWeightServiceImpl")
	private IOprLoadingbrigadeWeightService oprLoadingbrigadeWeightService;

	@Resource(name = "oprReturnGoodsHibernateDaoImpl")
	private IOprReturnGoodsDao oprReturnGoodsDao;
	
	@Resource(name="oprReceiptServiceImpl")
	private IOprReceiptService oprReceiptService;
	
	@Value("${oprPrewiredServiceImpl.log_sureCargo}")
	private Long log_sureCargo ;
	
	@Value("${oprPrewiredServiceImpl.log_delBeforeCargo}")
	private Long log_delBeforeCargo ;  //ȡ��Ԥ��

	@Value("${oprReceiptServiceImpl.log_receiptOutStore}")
	private Long log_receiptOutStore ;  //�ص�����
	
	@Resource(name = "oprHistoryServiceImpl")
	private IOprHistoryService oprHistoryService;
	
	@Resource(name = "customerServiceImpl")
	private ICustomerService customerService;
	
	@Override
	public IBaseDAO<OprPrewired, Long> getBaseDao() {
		return oprPrewiredDao;
	}
	
	@Resource(name = "fiInterfaceImpl")
	private IFiInterface iFiInterface;

	@Resource(name="departHibernateDaoImpl")
    private IDepartDao departDao;
	
	/**
	 *  ���浽���Ž�����ϸ�����޸Ŀ�棬����״̬
	 * @see com.xbwl.oper.stock.service.IOprPrewiredService#saveOvemByIds(java.util.List, java.util.Map)
	 */
	@ModuleName(value="����ʵ�䱣��",logType=LogType.buss)
	public Long saveOvemByIds(List<OprPrewiredVo> aa, Map fileMap,String dnos) throws Exception  {
		
				Long totalDno = (Long)fileMap.get("totalDno");
				Long totalNum = (Long)fileMap.get("totalNum");
				Double  totalWeight = (Double)fileMap.get("totalWeight");
				Long departId = (Long)fileMap.get("departId");  //����ȥ��
				String departName = (String)fileMap.get("departName");  //����ȥ������
				Long bussDepartId = (Long)fileMap.get("bussDepartId");
				String loadingbrigade = (String)fileMap.get("loadingbrigade");
				String loadingbrigadeId = fileMap.get("loadingbrigadeId").toString();
				String dispatchGroup = (String) fileMap.get("dispatchGroup");
				String mode = (String)fileMap.get("mode"); 
				String username=(String)fileMap.get("username");
				
				Set<Long> dnoSet = new HashSet<Long>();  //ͬһ��Ԥ�����������͵�״̬��Ϊ0
				if(!"".equals(dnos)&&null!=dnos){
				        String[] idsValue = dnos.split("\\,");
				        for (String delId : idsValue) {
				        	boolean flag=true;
				        	for(OprPrewiredVo oprPrewiredVo:aa){
				        		Long sLong =oprPrewiredVo.getDno();
				        		if(sLong.equals(Long.valueOf(delId))){
				        				flag=false;
				        		}
				        	}
				        	if(flag){
				        		dnoSet.add(Long.valueOf(delId));
				        	}
				        }
				        for(Long dnoLong:dnoSet){
				        	List<OprStatus> list2 =oprStatusDao.find(" from OprStatus o where o.dno=?  ",dnoLong);
				        	OprStatus osu= list2.get(0);
				        	//�����Ԥ�䣬��д����Ԥ�����־ 0,δ���⣬1��Ϊ�ѳ��⣬2���쳣���⣬3����Ԥ��
				        	if(osu.getOutStatus().equals(3l)){
				        		oprHistoryService.saveLog(dnoLong, "ȡ��Ԥ�䣬δԤ���ߵĻ���" , log_delBeforeCargo);
				        	}
				        	
				        	if("���Ž���".equals(mode)){
							  	osu.setDepartOvermemoStatus(0l);
							  	// osu.setReachStatus(0l);//�޸ĵ㵽״̬Ϊδ�㵽
							  	oprStatusDao.save(osu);
				        	}else{
				        		osu.setOutStatus(0l);
				        		osu.setOutTime(new Date());
				        		oprStatusDao.save(osu);
				        	}
				        }
				}
		       
				OprOvermemo  oprOvermemo = new OprOvermemo();
				oprOvermemo.setTotalTicket(totalDno);
				oprOvermemo.setTotalWeight(totalWeight);
				oprOvermemo.setTotalPiece(totalNum);
				oprOvermemo.setStartDepartId(bussDepartId);
				SysDepart sysDepart =departDao.get(bussDepartId);
				oprOvermemo.setStartDepartName(sysDepart.getDepartName());
				oprOvermemo.setEndDepartId(departId);
				oprOvermemo.setEndDepartName(departName);
			    oprOvermemo.setOvermemoType(mode);
			    oprOvermemo.setStatus(7l);
			    
			    Set<OprOvermemoDetail>set = oprOvermemo.getOprOvermemoDetails();
			    if (set==null) {
					set=new HashSet<OprOvermemoDetail>();
				}
			    for(OprPrewiredVo opwdVo:aa ){
			    	if(opwdVo.getPiece()==null){
			    		opwdVo.setPiece(0l);
			    	}
			    	if(opwdVo.getWId()!=null){
			    			OprPrewired oprPrewired = oprPrewiredDao.get(opwdVo.getWId());
			    			if(oprPrewired.getStatus()==0l){
			    					throw new ServiceException("Ԥ�䵥��"+opwdVo.getWId()+"�Ѿ����ϣ�������Ԥ�䣡");
			    			}
			    		
			    			if(!"ר��".equals(mode)){
					    			List<OprStock> list = oprStockDao.find(" from OprStock o where o.dno=? and o.departId=? ",opwdVo.getDno(),bussDepartId);
					    			if(list.size()==0){
					    				throw new ServiceException("���͵���Ϊ "+opwdVo.getDno()+" �����޿�棬�������㵽����");
					    			}
					    			if(list.get(0).getPiece()==0l){
					    				throw new ServiceException("���͵���Ϊ "+opwdVo.getDno()+" ���������Ϊ0���޷����⡣");
					    			}
			    			}
			    	}
			    	
			    	 OprOvermemoDetail oprOverDetail = new OprOvermemoDetail();
					 OprFaxIn oIn=oprFaxInDao.get(opwdVo.getDno());
					 
						//�ж�ʵ�䷽ʽ��ʵ��Ļ����Ƿ�һ��
						if("��ת".equals(mode) || ("�ⷢ").equals(mode)){
							if(!oIn.getDistributionMode().equals(mode)){
								 throw new ServiceException("Ԥ�䷽ʽ��һ��('"+oIn.getDistributionMode()+"')���޷�����ʵ��");
							}
						}else if("ר��".equals(mode) || ("�����ͻ�").equals(mode)){
							if((!"�°�".equals(oIn.getDistributionMode()))&&oIn.getSonderzug()!=1l){
								 throw new ServiceException("Ԥ�䷽ʽ��һ��('"+oIn.getDistributionMode()+"')���޷�����ʵ��");
							}
						}   // ���Ž��ӵĻ��������ʵ��
					 
					 oIn.setRealGoWhere(new StringBuffer(mode).append(":").append(departName).append("��").append(opwdVo.getTelPhone()).append("").toString());
					 if("��ת".equals(mode)||"�ⷢ".equals(mode)){   // ���´���¼���
						 oIn.setGowhere(departName);
						 oIn.setGoWhereId(departId);
					 }
					 
					 oprOverDetail.setReNum(opwdVo.getReachNum());
					 oprOverDetail.setDno(oIn.getDno());
					 oprOverDetail.setRealPiece(opwdVo.getRealPiece());
					 oprOverDetail.setPiece(oIn.getPiece());
					 oprOverDetail.setCusId(oIn.getCusId());
					 oprOverDetail.setCpName(oIn.getCpName());
			
					 if(opwdVo.getRealPiece()<opwdVo.getPiece()){
						 double  d =Double.parseDouble(opwdVo.getRealPiece().toString());
						 double  cqw =Double.parseDouble(oIn.getCusWeight().toString());
						 double tot =DoubleUtil.mul(DoubleUtil.div(cqw,d, 1),opwdVo.getRealPiece());
						 oprOverDetail.setWeight(tot);
						 List<OprStock> list = oprStockDao.find(" from OprStock o where o.dno=? and o.departId=? ",opwdVo.getDno(),bussDepartId);
						 list.get(0).setWeight(tot);
						 oprStockDao.save(list.get(0));		
			    		
					 }else{
						 oprOverDetail.setWeight(oIn.getCqWeight());
					 }
					 
					 if(opwdVo.getRealPiece()<oIn.getPiece()){
						 oprOverDetail.setIsException(1l);
					 }else {
						 oprOverDetail.setIsException(0l);
					 }
					 oprOverDetail.setSubNo(oIn.getSubNo());
					 oprOverDetail.setFlightNo(oIn.getFlightNo());
					 oprOverDetail.setConsignee(oIn.getConsignee());
					 oprOverDetail.setAddr(oIn.getAddr());
					 oprOverDetail.setDistributionMode(oIn.getDistributionMode());
					 oprOverDetail.setTakeMode(oIn.getTakeMode());
					 oprOverDetail.setGoods(oIn.getGoods());
					 oprOverDetail.setFlightMainNo(oIn.getFlightMainNo());
					 oprOverDetail.setStatus(0l);
					 oprOverDetail.setOprOvermemo(oprOvermemo);
					 set.add(oprOverDetail);
				 }
			    
			    for(OprPrewiredVo opwdVo:aa ){
			    	if(opwdVo.getWId()!=null){
			    		OprPrewired	oprPrewired= oprPrewiredDao.get(opwdVo.getWId());
			    		oprPrewired.setStatus(0l);
			    		oprPrewiredDao.save(oprPrewired);
			    	}
			    }
			    
			     oprOvermemo.setOprOvermemoDetails(set);
			     oprOvermemo.setTelPhone(aa.get(0).getTelPhone());
				 oprOvermemoDao.save(oprOvermemo);
				 
				 Set<OprOvermemoDetail>setDetail=oprOvermemo.getOprOvermemoDetails(); 
				 for(OprOvermemoDetail oprOverDetail:setDetail){
					 oprHistoryService.saveLog(oprOverDetail.getDno(), mode+"ʵ����⣬ʵ�䵥�ţ�"+oprOvermemo.getId()+",ʵ�������"+oprOverDetail.getRealPiece()+",ʵ��ȥ��"+oprOvermemo.getEndDepartName() , log_sureCargo);
					 if((null!=loadingbrigade && loadingbrigade.trim().length()>0) || (null!=dispatchGroup && dispatchGroup.trim().length()>0)){//װж����߷ֲ�����һ���ͱ���
						 try{
							 saveOprLoadingbrigade(oprOverDetail, oprOvermemo.getId(), loadingbrigadeId, dispatchGroup);  // ���������װж��ͷֲ���
						 }catch (Exception e) {
							 throw new ServiceException("���浽װж�������ʧ�ܣ�");
						 }
					 }
				 }
				 
				 
				 if(!"���Ž���".equals(mode)){
					 saveFiInterface(aa,mode,bussDepartId,oprOvermemo.getId(),departId,departName,username);  
				 }
				 
				 updateOprStockAndStatus(aa,bussDepartId,mode);                  // �޸ĳ���״̬

				 //��д�ص���ĳ���
				 List<OprReceipt> receiptList = null;
				 OprReceipt receipt = null;
				 Date dt = new Date();
				 SimpleDateFormat sdf = new SimpleDateFormat("yyyy��MM��dd��");
				 for(int i=0;i<aa.size();i++){
					 receiptList = this.oprReceiptService.findBy("dno", aa.get(i).getDno());
					 if(null!=receiptList && receiptList.size()>0){
						 receipt = receiptList.get(0);
						 if(aa.get(i).getReachNum()!=null&&aa.get(i).getReachNum()!=0){
							 receipt.setOutStockMan(username);
							 receipt.setOutStockNum(aa.get(i).getReachNum());
							 receipt.setOutStockStatus(1l);
							 receipt.setOutStockTime(new Date());
							 receipt.setCurStatus("�ѳ���");
							 this.oprHistoryService.saveLog(receipt.getDno(), username+"��"+sdf.format(dt)+"����ص�"+aa.get(i).getReachNum()+"��", log_receiptOutStore);
							 this.oprReceiptService.save(receipt);
						 }
					 }
				 }
				 
				 //��д������ �޸ķ���״̬
				 List<OprReturnGoods> returnList= null;
				 OprReturnGoods returnGoods =null;
				 for(int i=0;i<aa.size();i++){
					 returnList = this.oprReturnGoodsDao.find("from OprReturnGoods where dno=? and status=?", aa.get(i).getDno(),2l);//��ѯ����״̬Ϊ�������͵�
					 if(null!=returnList && returnList.size()>0){
						 returnGoods = returnList.get(0);
						 returnGoods.setStatus(3l);//�޸ķ���״̬Ϊ����
						 this.oprReturnGoodsDao.save(returnGoods);
					 }
				 }
			return oprOvermemo.getId();
	}
	
	/**
	 * ʵ������ �ò���ӿ�
	 * @param aa  ʵ��Ļ���
	 * @param mode ʵ�䷽ʽ 
	 * @param bussDepart ҵ����
	 * @param ovemID  ʵ���ID
	 * @param departId    ʵ��ȥ��
	 * @param departName
	 * @param username  ����Ա
	 * @throws Exception
	 */
	public void saveFiInterface(List<OprPrewiredVo> aa,String mode,Long bussDepart,Long ovemID,Long departId,String departName,String username) throws Exception{
		List<FiInterfaceProDto> listfiInterfaceDto =new ArrayList<FiInterfaceProDto>();
		User user= WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
		Long bussDepartId = Long.parseLong(user.get("bussDepart").toString());
		for(OprPrewiredVo oprPrewiredVo:aa){
			OprFaxIn oIn=oprFaxInDao.get(oprPrewiredVo.getDno());
			
	  		List<OprStatus> list2 =oprStatusDao.find(" from OprStatus o where o.dno=?  ",oIn.getDno());
			if(list2.size()==0){
				throw new ServiceException("�����״̬��¼������");
			}
			OprStatus osu= list2.get(0);
			if(osu.getIsCreateFi()!=null&&osu.getIsCreateFi()==1l){  //����ѹ���
				continue;
			}

			Customer customer=null; 
			if("��ת".equals(mode)||"�ⷢ".equals(mode)){
				customer=customerService.get(departId);
			}
			
			if(oIn.getSonderzugPrice()!=null&&oIn.getSonderzugPrice()!=0){
				FiInterfaceProDto fiInterfaceProDto = new FiInterfaceProDto();
				fiInterfaceProDto.setOutStockMode(mode);
				fiInterfaceProDto.setDno(oprPrewiredVo.getDno());
				fiInterfaceProDto.setSettlementType(1l);
				fiInterfaceProDto.setDocumentsType("����");
				fiInterfaceProDto.setDocumentsSmalltype("���͵�");
				fiInterfaceProDto.setDocumentsNo(oprPrewiredVo.getDno());
				fiInterfaceProDto.setAmount(oIn.getSonderzugPrice());
				fiInterfaceProDto.setCostType("����ר����");
				fiInterfaceProDto.setDepartId(bussDepart);
				fiInterfaceProDto.setIncomeDepartId(oIn.getInDepartId());
				fiInterfaceProDto.setSourceData("ʵ�䵥");
				fiInterfaceProDto.setSourceNo(ovemID);
				if("�����ͻ�".equals(mode)||"ר��".equals(mode)){
					fiInterfaceProDto.setCollectionUser(departName);
					fiInterfaceProDto.setContacts(departName);
				}else{
					fiInterfaceProDto.setGocustomerId(departId);
					fiInterfaceProDto.setCollectionUser(username);
					fiInterfaceProDto.setGocustomerName(departName);
					fiInterfaceProDto.setCustomerId(departId);
					fiInterfaceProDto.setCustomerName(departName);
				}
				fiInterfaceProDto.setDepartId(Long.valueOf(user.get("bussDepart")+""));
				fiInterfaceProDto.setDepartName(user.get("rightDepart")+"");
				listfiInterfaceDto.add(fiInterfaceProDto);
			}
			/*
			 * ������½�Ĺ�Ӧ�̲����е������ͷѲ�д�����ӿ�
			 * LiuHao 03-20
			 */
			
			if(customer!=null&&"�½�".equals(customer.getSettlement())){
				if(oIn.getConsigneeFee()!=null&&oIn.getConsigneeFee()!=0.0){
					FiInterfaceProDto fiInterfaceProDto4 = new FiInterfaceProDto();
					fiInterfaceProDto4.setOutStockMode(mode);
					fiInterfaceProDto4.setDno(oprPrewiredVo.getDno());
					fiInterfaceProDto4.setSettlementType(1l);
					fiInterfaceProDto4.setDocumentsType("����");
					fiInterfaceProDto4.setDocumentsSmalltype("���͵�");
					fiInterfaceProDto4.setDocumentsNo(oprPrewiredVo.getDno());
					fiInterfaceProDto4.setAmount(oIn.getConsigneeFee());
					fiInterfaceProDto4.setCostType("�������ͷ�");
					fiInterfaceProDto4.setDepartId(bussDepart);
					fiInterfaceProDto4.setSourceData("ʵ�䵥");
					fiInterfaceProDto4.setSourceNo(ovemID);
					fiInterfaceProDto4.setGocustomerId(departId);
					fiInterfaceProDto4.setIncomeDepartId(oIn.getInDepartId());
					fiInterfaceProDto4.setCollectionUser(username);
					fiInterfaceProDto4.setGocustomerName(departName);
					fiInterfaceProDto4.setDepartId(Long.valueOf(user.get("bussDepart")+""));
					fiInterfaceProDto4.setDepartName(user.get("rightDepart")+"");
					listfiInterfaceDto.add(fiInterfaceProDto4);
				}
			}else if(customer!=null&&"�ֽ�".equals(customer.getSettlement())){
				FiInterfaceProDto fiInterfaceProDto4 = new FiInterfaceProDto();
				fiInterfaceProDto4.setOutStockMode(mode);
				fiInterfaceProDto4.setDno(oprPrewiredVo.getDno());
				fiInterfaceProDto4.setSettlementType(1l);
				fiInterfaceProDto4.setDocumentsType("����");
				fiInterfaceProDto4.setDocumentsSmalltype("���͵�");
				fiInterfaceProDto4.setDocumentsNo(oprPrewiredVo.getDno());
				fiInterfaceProDto4.setAmount(oIn.getConsigneeFee()==null?0:oIn.getConsigneeFee());
				fiInterfaceProDto4.setCostType("�������ͷ�");
				fiInterfaceProDto4.setDepartId(bussDepart);
				fiInterfaceProDto4.setSourceData("ʵ�䵥");
				fiInterfaceProDto4.setSourceNo(ovemID);
				fiInterfaceProDto4.setGocustomerId(departId);
				fiInterfaceProDto4.setIncomeDepartId(oIn.getInDepartId());
				fiInterfaceProDto4.setCollectionUser(username);
				fiInterfaceProDto4.setGocustomerName(departName);
				fiInterfaceProDto4.setDepartId(Long.valueOf(user.get("bussDepart")+""));
				fiInterfaceProDto4.setDepartName(user.get("rightDepart")+"");
				listfiInterfaceDto.add(fiInterfaceProDto4);
			}else{
				FiInterfaceProDto fiInterfaceProDto4 = new FiInterfaceProDto();
				fiInterfaceProDto4.setOutStockMode(mode);
				fiInterfaceProDto4.setDno(oprPrewiredVo.getDno());
				fiInterfaceProDto4.setSettlementType(1l);
				fiInterfaceProDto4.setDocumentsType("����");
				fiInterfaceProDto4.setDocumentsSmalltype("���͵�");
				fiInterfaceProDto4.setDocumentsNo(oprPrewiredVo.getDno());
				fiInterfaceProDto4.setAmount(oIn.getConsigneeFee()==null?0:oIn.getConsigneeFee());
				fiInterfaceProDto4.setCostType("�������ͷ�");
				fiInterfaceProDto4.setDepartId(bussDepart);
				fiInterfaceProDto4.setSourceData("ʵ�䵥");
				fiInterfaceProDto4.setIncomeDepartId(oIn.getInDepartId());
				fiInterfaceProDto4.setSourceNo(ovemID);
				fiInterfaceProDto4.setCollectionUser(departName);
				fiInterfaceProDto4.setContacts(departName);
				fiInterfaceProDto4.setDepartId(Long.valueOf(user.get("bussDepart")+""));
				fiInterfaceProDto4.setDepartName(user.get("rightDepart")+"");
				listfiInterfaceDto.add(fiInterfaceProDto4);
			}
			/*
			if(customer!=null&&"�½�".equals(customer.getSettlement())&&(oIn.getConsigneeFee()!=null||oIn.getConsigneeFee()!=0)){
				FiInterfaceProDto fiInterfaceProDto4 = new FiInterfaceProDto();
				fiInterfaceProDto4.setOutStockMode(mode);
				fiInterfaceProDto4.setDno(oprPrewiredVo.getDno());
				fiInterfaceProDto4.setSettlementType(1l);
				fiInterfaceProDto4.setDocumentsType("����");
				fiInterfaceProDto4.setDocumentsSmalltype("���͵�");
				fiInterfaceProDto4.setDocumentsNo(oprPrewiredVo.getDno());
				fiInterfaceProDto4.setAmount(oIn.getConsigneeFee());
				fiInterfaceProDto4.setCostType("�������ͷ�");
				fiInterfaceProDto4.setDepartId(bussDepart);
				fiInterfaceProDto4.setSourceData("ʵ�䵥");
				fiInterfaceProDto4.setSourceNo(ovemID);
				if("�����ͻ�".equals(mode)||"ר��".equals(mode)){
					fiInterfaceProDto4.setCollectionUser(departName);
					fiInterfaceProDto4.setContacts(departName);
					
				}else{
					fiInterfaceProDto4.setCustomerId(departId);
					fiInterfaceProDto4.setCollectionUser(username);
					fiInterfaceProDto4.setCustomerName(departName);
				}
				listfiInterfaceDto.add(fiInterfaceProDto4);
			}else{
				FiInterfaceProDto fiInterfaceProDto4 = new FiInterfaceProDto();
				fiInterfaceProDto4.setOutStockMode(mode);
				fiInterfaceProDto4.setDno(oprPrewiredVo.getDno());
				fiInterfaceProDto4.setSettlementType(1l);
				fiInterfaceProDto4.setDocumentsType("����");
				fiInterfaceProDto4.setDocumentsSmalltype("���͵�");
				fiInterfaceProDto4.setDocumentsNo(oprPrewiredVo.getDno());
				fiInterfaceProDto4.setAmount(oIn.getConsigneeFee()==null?0:oIn.getConsigneeFee());
				fiInterfaceProDto4.setCostType("�������ͷ�");
				fiInterfaceProDto4.setDepartId(bussDepart);
				fiInterfaceProDto4.setSourceData("ʵ�䵥");
				fiInterfaceProDto4.setSourceNo(ovemID);
				if("�����ͻ�".equals(mode)||"ר��".equals(mode)){
					fiInterfaceProDto4.setCollectionUser(departName);
					fiInterfaceProDto4.setContacts(departName);
					
				}else{
					fiInterfaceProDto4.setCustomerId(departId);
					fiInterfaceProDto4.setCollectionUser(username);
					fiInterfaceProDto4.setCustomerName(departName);
				}
				listfiInterfaceDto.add(fiInterfaceProDto4);
			}*/
				if(oIn.getCusValueAddFee()!=null&&oIn.getCusValueAddFee()!=0){
					FiInterfaceProDto fiInterfaceProDto2 = new FiInterfaceProDto();
					fiInterfaceProDto2.setOutStockMode(mode);
					fiInterfaceProDto2.setDno(oprPrewiredVo.getDno());
					fiInterfaceProDto2.setSettlementType(1l);
					fiInterfaceProDto2.setDocumentsType("����");
					fiInterfaceProDto2.setDocumentsSmalltype("���͵�");
					fiInterfaceProDto2.setDocumentsNo(oprPrewiredVo.getDno());
					fiInterfaceProDto2.setAmount(oIn.getCusValueAddFee());
					fiInterfaceProDto2.setCostType("������ֵ��");
					fiInterfaceProDto2.setDepartId(bussDepart);
					fiInterfaceProDto2.setIncomeDepartId(oIn.getInDepartId());
					fiInterfaceProDto2.setSourceData("ʵ�䵥");
					fiInterfaceProDto2.setSourceNo(ovemID);
					fiInterfaceProDto2.setDepartId(Long.valueOf(user.get("bussDepart")+""));
					fiInterfaceProDto2.setDepartName(user.get("rightDepart")+"");
					if("�����ͻ�".equals(mode)||"ר��".equals(mode)){
						fiInterfaceProDto2.setCollectionUser(departName);
						fiInterfaceProDto2.setContacts(departName);
					}else{
						fiInterfaceProDto2.setGocustomerId(departId);
						fiInterfaceProDto2.setCollectionUser(username);
						fiInterfaceProDto2.setGocustomerName(departName);
					}
					listfiInterfaceDto.add(fiInterfaceProDto2);
				}

				if(oIn.getPaymentCollection()!=null&&oIn.getPaymentCollection()!=0){
					FiInterfaceProDto fiInterfaceProDto3 = new FiInterfaceProDto();
					fiInterfaceProDto3.setOutStockMode(mode);
					fiInterfaceProDto3.setDno(oprPrewiredVo.getDno());
					fiInterfaceProDto3.setSettlementType(1l);
					fiInterfaceProDto3.setDocumentsType("���ջ���");
					fiInterfaceProDto3.setDocumentsSmalltype("���͵�");
					fiInterfaceProDto3.setDocumentsNo(oprPrewiredVo.getDno());
					fiInterfaceProDto3.setAmount(oIn.getPaymentCollection());
					fiInterfaceProDto3.setCostType("���ջ���");
					fiInterfaceProDto3.setDepartId(bussDepart);
					fiInterfaceProDto3.setIncomeDepartId(oIn.getInDepartId());
					fiInterfaceProDto3.setSourceData("ʵ�䵥");
					fiInterfaceProDto3.setSourceNo(ovemID);
					fiInterfaceProDto3.setDepartId(Long.valueOf(user.get("bussDepart")+""));
					fiInterfaceProDto3.setDepartName(user.get("rightDepart")+"");
					if("�����ͻ�".equals(mode)||"ר��".equals(mode)){
						fiInterfaceProDto3.setCollectionUser(departName);
						fiInterfaceProDto3.setContacts(departName);
					}else{
						fiInterfaceProDto3.setGocustomerId(departId);
						fiInterfaceProDto3.setCollectionUser(username);
						fiInterfaceProDto3.setGocustomerName(departName);
					}
					listfiInterfaceDto.add(fiInterfaceProDto3);
				}
				oIn.setDistributionDepartId(bussDepartId);  //�������Ͳ���
				osu.setIsCreateFi(1l);
				oprStatusDao.save(osu);
		 }
		/*
		 * �������ר���ѡ��������ͷѡ�������ֵ�ѡ����ջ��� ��һ����Ϊ��ʱ���ŵ��ò���ӿ�
		 * LiuHao 03-20
		 */
		iFiInterface.outStockToFi(listfiInterfaceDto);
		 
	}
	
	/**
	 * @param aa
	 * @param id  ����װж�飬�ֲ�������
	 * @param loadingbrigadeId
	 * @param dispatchGroup
	 */
	public void saveOprLoadingbrigade(OprOvermemoDetail detail,Long overmemoNo,String loadingbrigadeId,String dispatchGroup)throws Exception{
		List<OprLoadingbrigadeWeight> weightList = new ArrayList<OprLoadingbrigadeWeight>();
		  OprFaxIn oIn=oprFaxInDao.get(detail.getDno());
		  OprLoadingbrigadeWeight olbWeight  = new OprLoadingbrigadeWeight();
		  if(null!=loadingbrigadeId && !"".equals(loadingbrigadeId)){//����װж��
			  olbWeight.setLoadingbrigadeId(Long.parseLong(loadingbrigadeId));
		  }
		  if(null!=dispatchGroup && !"".equals(dispatchGroup)){//���÷ֲ���
			  olbWeight.setDispatchId(Long.parseLong(dispatchGroup));
		  }
		  olbWeight.setDno(detail.getDno());
		  olbWeight.setOvermemoNo(overmemoNo);
		  olbWeight.setGoods(oIn.getGoods());
		  olbWeight.setPiece(detail.getRealPiece());
		  olbWeight.setBulk(oIn.getBulk());
		  olbWeight.setLoadingbrigadeType(1l);
		  olbWeight.setWeight(detail.getWeight());
		  olbWeight.setOvermemoDetailId(detail.getId());
		  
		  weightList.add(olbWeight);
	      oprLoadingbrigadeWeightService.saveLoadingWeight(weightList,LoadingbrigadeTypeEnum.ZHUANGHUO);
	}
	
	/**
	 * @param aa �޸Ŀ�棬�ı����״̬
	 */
	public void updateOprStockAndStatus(List<OprPrewiredVo> aa,Long bussDepartId,String  mode){
		  for(OprPrewiredVo opwdVo:aa ){
			  	OprFaxIn oIn=oprFaxInDao.get(opwdVo.getDno());
			  	OprStock oStock=null;
			  	OprStatus osu=null;
			  	
			  	List<OprStock> list = oprStockDao.find(" from OprStock o where o.dno=? and o.departId=? ",opwdVo.getDno(),bussDepartId);
		  		if(list.size()>0){
		  			oStock=list.get(0);
		  		}else{
		  			if(!"ר��".equals(mode)&&oIn.getSonderzug()!=1l){
		  				throw new ServiceException("��ר���������Ҫ�п��");
		  			}else{
						oStock=new OprStock();
						oStock.setDno(oIn.getDno());
						oStock.setDepartId(bussDepartId);
						oStock.setFlightNo(oIn.getFlightNo());
						oStock.setFlightMainNo(oIn.getFlightMainNo());
						oStock.setSubNo(oIn.getSubNo());
						oStock.setAddr(oIn.getAddr());
						oStock.setConsignee(oIn.getConsignee());
						oStock.setPiece(0l);
						oStock.setWeight(oIn.getCusWeight());
		  			}
		  		}

		  		List<OprStatus> list2 =oprStatusDao.find(" from OprStatus o where o.dno=?  ",opwdVo.getDno());
				if(list2.size()==0){
					throw new ServiceException("�����״̬��¼������");
				}else{
					osu= list2.get(0);
				}
				
				if(osu.getOutStatus()!=null&&osu.getOutStatus()==1l){
					throw new ServiceException("�����Ѿ����⣬����ʵ��");
				}
				if("ר��".equals(mode)){   //ר���Ǹ����
					osu.setOutStatus(1l);
					osu.setOutTime(new Date());
					osu.setReturnStatus(0l);
				}else if("���Ž���".equals(mode)){
					osu.setDepartOvermemoStatus(1l);
					// osu.setReachStatus(0l);//�޸ĵ㵽״̬Ϊδ�㵽
					osu.setReturnStatus(0l);
					if(oStock.getPiece()==opwdVo.getRealPiece()){
						oprStockDao.delete(oStock.getId());
					}else{
						if(oStock.getPiece()==0l){
							throw new ServiceException("���Ϊ�㣬����ʵ��");
						}
					}
				}else{
				  	String sql = "select sum(t.return_num) returnNum from opr_return_goods t where t.d_no=? and t.return_depart = ?  ";
				  	Page page = new Page();  //���㷵������
				  	page.setLimit(500);
				  	page =oprReturnGoodsDao.findPageBySql(page, sql, opwdVo.getDno(),bussDepartId	);
				  	List<Map>  listo = page.getResultMap();
					Long returnNum=0l;   //��������
					for(Map totalMap : listo ){
						returnNum = totalMap.get("RETURNNUM")==null?0l:Long.parseLong(String.valueOf(totalMap.get("RETURNNUM")));
					}
					
					StringBuffer sb = new StringBuffer();
					sb.append("select sum(oe.real_piece) sumPiece ")
						 .append(" from opr_overmemo t")
						 .append(" join opr_overmemo_detail oe on t.id = oe.overmemo")
						 .append(" where t.overmemo_type <> '�����ӻ�' ")
						 .append("  and t.overmemo_type <> '��Χ�ӻ�' ")
						 .append("  and t.overmemo_type <> '���Ž���' ")
						 .append("  and oe.d_no=? ")
						 .append("  and oe.depart_id = ?   ");
					Page page2 = new Page();  //���㷵������
				  	page2.setLimit(500);
				  	page2 =oprReturnGoodsDao.findPageBySql(page2, sb.toString(), opwdVo.getDno(),bussDepartId	);
				  	List<Map>  listo2 = page2.getResultMap();
				  	Long sumPiece=0l;   //�������
					for(Map totalMap : listo2 ){
						sumPiece = totalMap.get("SUMPIECE")==null?0l:Long.parseLong(String.valueOf(totalMap.get("SUMPIECE")));
					}
					
					if(sumPiece-(oIn.getPiece()+returnNum)>=0){
						osu.setOutStatus(1l);
					}else{
						osu.setOutStatus(2l);
					}
					if(oStock.getPiece()==0l){
						throw new ServiceException("���Ϊ�㣬����ʵ��");
					}
					osu.setReturnStatus(0l);
					osu.setOutTime(new Date());
				  	
				}
				oStock.setPiece(oStock.getPiece()-opwdVo.getRealPiece());
				if(null!=oStock.getId() && oStock.getPiece().equals(0l)){
					//���û�п����ɾ��
					oprStockDao.delete(oStock);
				}else{
					oprStockDao.save(oStock);
				}
				oprStatusDao.save(osu);
		  }
	}
	
	@ModuleName(value="ȡ��Ԥ��",logType=LogType.buss)
	public void deleteStatusByIds(List<Long> list) throws Exception{
		 for(Long id: list){
			 OprPrewired oprPrewired2=this.get(id);
			 if(oprPrewired2.getStatus()!=null&&oprPrewired2.getStatus()==0){
				 continue;
			 }
			 Set<OprPrewiredDetail>listDetail  = oprPrewired2.getOprPrewiredDetails();
			 for(OprPrewiredDetail oprPrewiredDetail : listDetail){
				 	List<OprStatus> listu =oprStatusDao.find(" from OprStatus o where o.dno=?  ",oprPrewiredDetail.getDNo());
				 	OprStatus osu= listu.get(0);
				 	if(osu.getOutStatus()==1l){  //�Ѿ������˵Ļ������ȥ��
				 		continue;
				 	}
				 	if("���Ž���".equals(oprPrewired2.getAutostowMode())){
				 		osu.setDepartOvermemoStatus(0l);
				 	}else{
				 		osu.setOutStatus(0l);
				 	}
				  	oprStatusDao.save(osu);
				  	oprHistoryService.saveLog(oprPrewiredDetail.getDNo(), "ȡ��Ԥ�䣬Ԥ�䵥�ţ�"+oprPrewiredDetail.getOprPrewired().getId() , log_delBeforeCargo);
			 }
			 oprPrewired2.setStatus(0l);
			 save(oprPrewired2);
		 }
	}
	

	@ModuleName(value="ʵ����Ϣ����SQLƴ��",logType=LogType.buss)
	public String getTotalDetail(String ids) {
		StringBuffer sb = new StringBuffer();
		if(!"".equals(ids)&&ids!=null){
			if(ids.endsWith(",")){
				ids = ids.substring(0,ids.length()-1);
			}
			sb.append(" select count(dno) dno,sum(weight)  weight, ");
			sb.append(" sum(piece)  piece,sum(realPiece) realPiece, ");
			sb.append( " sum(consigneeFee) consigneeFee ,sum(cpFee) cpFee,sum(traFee) traFee,sum(paymentCollection) paymentCollection ");
			sb.append( " from ( ");
					sb.append( " select w.id as wId,w.depart_id as departId,w.autostow_mode as autostowMode,w.to_where as toWhere,  ");
							sb.append( " wd.d_no as dno,wd.consignee as consignee,wd.request as request,wd.weight as weight,wd.piece as piece,wd.addr as addr,wd.gowhere as goWhere, ");
							sb.append( " os.piece as realPiece, ot.OUT_STATUS outstatus, ");
							sb.append( " oi.receipt_type as receiptType,oi.consignee_rate as consigneeRate,oi.consignee_fee as consigneeFee,oi.town as town, ");
							sb.append( " oi.TRA_FEE as cpFee,oi.tra_fee as traFee,oi.payment_collection as paymentCollection, ");
							sb.append( "  ot.fee_audit_status as feeAuditStatus, ");
							sb.append( "   oe.print_num as printNum,oi.TRAFFIC_MODE trafficMode  ");
					sb.append( " from opr_prewired w,opr_prewired_detail wd,opr_fax_in oi,opr_stock os,opr_status ot,opr_receipt oe ");
					sb.append( "  where w.id = wd.opr_prewired_id ");
							sb.append( " and wd.d_no=oi.d_no ");
							sb.append( " and w.STATUS=1 ");  
							sb.append( " and wd.d_no = os.d_no(+) ");
							sb.append(" and os.DEPART_ID = :sdepartId");
							sb.append( " and wd.d_no = ot.d_no ");
							sb.append( " and wd.d_no = oe.d_no(+)) ");
							//REVIEW-ACCEPT ʹ��or����in
							//FIXED
			sb.append( "where departId= :departId and (outstatus = 0  or  outstatus = 3 )");
			sb.append("AND   dno in (  "+ids+"  ) 	");
		}
		return sb.toString();
	}

	@ModuleName(value="ʵ���ѯSQLƴ��",logType=LogType.buss)
	public String getAllDetail(Map filterMap) throws Exception {
		StringBuffer sb = new StringBuffer();
		String ids = filterMap.get("ids").toString();
					sb.append( " select w.id as wId,w.depart_id as departId,w.autostow_mode as autostowMode,w.to_where as toWhere,  ");
							sb.append( " wd.d_no as dno,wd.consignee as consignee,wd.request as request,wd.weight as weight,wd.piece as piece,wd.addr as addr,wd.gowhere as goWhere, ");
							sb.append( " ot.OUT_STATUS outstatus,oi.remark remark,oi.flight_main_no, ");
							sb.append("  NVL( os.piece, 0 )  as realPiece,  NVL( os.piece, 0 )  as REALPIECE2, ");
							sb.append( " oi.receipt_type as receiptType,oi.consignee_rate as consigneeRate,oi.consignee_fee as consigneeFee,oi.town as town, ");
							sb.append( " oi.CONSIGNEE_FEE as cpFee,oi.tra_fee as traFee,oi.payment_collection as paymentCollection, ");
							sb.append( "  ot.fee_audit_status as feeAuditStatus, oi.cp_name cpName, ");
							sb.append( "   oe.print_num as printNum,oe.REACH_NUM reachNum,oi.TRAFFIC_MODE trafficMode  ");
					sb.append( " from opr_prewired w,opr_prewired_detail wd,opr_fax_in oi,opr_stock os,opr_status ot,opr_receipt oe ");
					sb.append( "  where w.id = wd.opr_prewired_id ");
							sb.append( " and wd.d_no=oi.d_no ");   
							sb.append( " and w.STATUS=1 ");  
							sb.append(" AND   oi.status  =  1 	");
							sb.append( " and wd.d_no = os.d_no(+) ");
							sb.append(" and os.DEPART_ID(+) =:departId2 ");
							sb.append( " and wd.d_no = ot.d_no ");
							sb.append( " and wd.d_no = oe.d_no(+) ");
							sb.append( " and w.depart_id= :departId ");
							sb.append(" and ot.OUT_STATUS in (0,3) ");
		
		if(!"".equals(ids)&&ids!=null){
			if(ids.endsWith(",")){
				ids = ids.substring(0,ids.length()-1);
			}
			sb.append(" AND   w.id  in  ("+ids+")  ");
		}
		return sb.toString();
	}

	@ModuleName(value="ʵ�䣨��Ʊ��ӣ�SQLƴ��",logType=LogType.buss)
	public String getInfoByDno(String requestStage) {
		StringBuffer sb = new StringBuffer();
		sb.append( " select  os.DEPART_ID departId, oi.DISTRIBUTION_MODE autostowMode,oi.d_no dno,oi.remark remark,oi.flight_main_no,");
				sb.append( "  oi.consignee as consignee ,oi.CUS_WEIGHT as weight,oi.piece as piece,oi.addr as addr,oi.gowhere as goWhere, ");
				sb.append(" os.piece as realPiece2, oi.SONDERZUG as sonderzug, t4.request as  request , oi.cp_name cpName,  ");
				sb.append( " os.piece as realPiece,ot.OUT_STATUS outstatus,oi.TAKE_MODE as TAKEMODE,");
				sb.append( " oi.receipt_type as receiptType,oi.consignee_rate as consigneeRate,oi.consignee_fee as consigneeFee,oi.town as town, ");
				sb.append( " oi.TRA_FEE as cpFee,oi.tra_fee as traFee,oi.payment_collection as paymentCollection, ");
				sb.append( "  ot.fee_audit_status as feeAuditStatus,oi.END_DEPART_ID as  ENDDEPARTID, oi.CUR_DEPART_ID as CURDEPARTID,    ");
				sb.append( "   oe.print_num as printNum,oe.REACH_NUM reachNum,oi.TRAFFIC_MODE trafficMode  ");
		sb.append( " from  opr_fax_in oi,opr_stock os ,  ");
		sb.append( "  opr_status ot,opr_receipt oe,  ");
		sb.append( " ( select   ai.d_no dno ,ai.request request,ai.request_stage  	");
		sb.append(" from   aisuser.OPR_REQUEST_DO ai   	");
		sb.append(" where   ai.request_stage = :requestStage )  t4  ");
		sb.append( "  where  ");
				sb.append( "  oi.d_no = os.d_no(+) ");
				sb.append( " and oi.d_no= ot.d_no ");
				sb.append(" AND   oi.status  =  1 	");
				sb.append(" and t4.dno(+)=oi.d_no ");
				sb.append( " and oi.d_no = oe.d_no(+) ");
				//REVIEW-ACCEPT ʹ��or����in
				//FIXED
				
				sb.append( " and  os.DEPART_ID(+)= :departId  and (ot.OUT_STATUS = 0  or  ot.OUT_STATUS = 2 )  ");
				sb.append("  and oi.d_no = :dno ");
		return sb.toString();
	}

	@ModuleName(value="ʵ���ѯ�����Ž��ӣ�SQLƴ��",logType=LogType.buss)
	public String getAllDetailByDepartId(Map filterMap) throws Exception {
		StringBuffer sb = new StringBuffer();
		String ids=filterMap.get("ids").toString();
	/*	String toWhere =(String)filterMap.get("toWhere");
		Long feeTypeLong = (Long)filterMap.get("feeType");
		String ids = (String)filterMap.get("ids");
		Long dno = (Long)filterMap.get("dno");*/
		
		sb.append("  select w.id  as wId, ");
		sb.append(" w.depart_id   as departId, ");
		sb.append(" w.autostow_mode  as autostowMode, ");
		sb.append(" w.to_where  as toWhere, ");
		sb.append(" wd.d_no  as dno, ");
		sb.append(" wd.consignee  as consignee, ");
		sb.append(" wd.request  as request, ");
		sb.append(" wd.weight  as weight, ");
		sb.append(" wd.piece  as piece, ");
		sb.append(" wd.addr  as addr, ");
		sb.append(" wd.gowhere as goWhere, ");
		sb.append("  NVL( os.piece, 0 )   as realPiece, ");
		sb.append("  NVL( os.piece, 0 )  as realPiece2, ");
		sb.append(" ot.OUT_STATUS  outstatus, ");
		sb.append(" oi.receipt_type  as receiptType, ");
		sb.append(" oi.consignee_rate  as consigneeRate, ");
		sb.append(" oi.consignee_fee  as consigneeFee, ");
		sb.append(" oi.town  as town, ");
		sb.append(" oi.cp_name cpName,");
		sb.append(" oi.TRA_FEE  as cpFee, ");
		sb.append(" oi.tra_fee  as traFee, ");
		sb.append(" oi.payment_collection as paymentCollection, ");
		sb.append(" ot.fee_audit_status   as feeAuditStatus, ");
		sb.append(" oe.print_num  as printNum, ");
		sb.append(" oe.REACH_NUM reachNum , ");
		sb.append(" ot.DEPART_OVERMEMO_STATUS departstatus,oi.TRAFFIC_MODE trafficMode ,oi.flight_main_no ");
sb.append("  from opr_prewired  w, ");
			sb.append(" opr_prewired_detail wd, ");
			sb.append(" opr_fax_in   oi, ");
			sb.append(" opr_stock    os, ");
			sb.append(" opr_status   ot, ");
			sb.append(" opr_receipt  oe ");
sb.append(" where w.id = wd.opr_prewired_id ");
		sb.append("  and wd.d_no = oi.d_no ");
		sb.append("  and wd.d_no = os.d_no(+) ");
		sb.append("   and wd.d_no = ot.d_no ");
		sb.append("  and wd.d_no = oe.d_no(+)  ");
		sb.append("  and w.STATUS =1  ");
		sb.append(" AND   oi.status  =  1 	");
		// sb.append( " and ot.DEPART_OVERMEMO_STATUS = 3 ");
		sb.append("  and w.depart_id = :departId ");
		sb.append("  and os.DEPART_ID(+)  =:departId2  ");
		
		if(!"".equals(ids)&&ids!=null){
			if(ids.endsWith(",")){
				ids = ids.substring(0,ids.length()-1);
			}
			sb.append(" AND    w.id  in  ("+ids+")  ");
		}
		return sb.toString();
	}

	@ModuleName(value="ʵ���ѯ��ר����SQLƴ��",logType=LogType.buss)
	public String getAllDetailByCar(Map filterMap) throws Exception {
		StringBuffer sb = new StringBuffer();
		String ids = filterMap.get("ids").toString();

		sb.append("  select w.id  as wId, ");
				sb.append(" w.depart_id   as departId, ");
				sb.append(" w.autostow_mode  as autostowMode, ");
				sb.append(" w.to_where  as toWhere, ");
				sb.append(" wd.d_no  as dno, ");
				sb.append(" wd.consignee  as consignee, ");
				sb.append(" wd.request  as request, ");
				sb.append(" wd.weight  as weight, ");
				sb.append(" wd.piece  as piece, ");
				sb.append(" wd.addr  as addr, ");
				sb.append(" wd.gowhere as goWhere, ");
				sb.append(" NVL( os.piece, wd.piece)   as realPiece, ");
				sb.append(" NVL( os.piece, 0 ) as realPiece2, ");
				sb.append(" ot.OUT_STATUS  outstatus, ");
				sb.append(" oi.receipt_type  as receiptType, ");
				sb.append(" oi.consignee_rate  as consigneeRate, ");
				sb.append(" oi.consignee_fee  as consigneeFee, ");
				sb.append(" oi.town  as town, ");
				sb.append(" oi.cp_name cpName, ");
				sb.append(" oi.TRA_FEE  as cpFee, ");
				sb.append(" oi.tra_fee  as traFee, ");
				sb.append(" oi.payment_collection as paymentCollection, ");
				sb.append(" ot.fee_audit_status   as feeAuditStatus, ");
				sb.append(" oe.print_num  as printNum, ");
				sb.append(" oe.REACH_NUM reachNum,oi.TRAFFIC_MODE trafficMode  ");
		sb.append("  from opr_prewired  w, ");
					sb.append(" opr_prewired_detail wd, ");
					sb.append(" opr_fax_in   oi, ");
					sb.append(" opr_stock    os, ");
					sb.append(" opr_status   ot, ");
					sb.append(" opr_receipt  oe ");
		sb.append(" where w.id = wd.opr_prewired_id ");
				sb.append("  and wd.d_no = oi.d_no ");
				sb.append("  and wd.d_no = os.d_no(+) ");
				sb.append("   and wd.d_no = ot.d_no ");
				sb.append("  and wd.d_no = oe.d_no(+)  ");
				sb.append("  and w.STATUS =1  ");
				sb.append(" AND   oi.status  =  1 	");
				sb.append("  and w.depart_id = :departId ");
				sb.append("  and os.DEPART_ID(+) = :departId2 ");
				sb.append("  and ot.OUT_STATUS in (0, 3) ");
	
		if(!"".equals(ids)&&ids!=null){
			if(ids.endsWith(",")){
				ids = ids.substring(0,ids.length()-1);
			}
			sb.append(" AND   w.id   in  ("+ids+")  ");
		}
		return sb.toString();
	}

}

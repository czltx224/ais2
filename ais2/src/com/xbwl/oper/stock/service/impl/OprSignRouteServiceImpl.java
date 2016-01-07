package com.xbwl.oper.stock.service.impl;

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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.log.anno.ModuleName;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.common.utils.DoubleUtil;
import com.xbwl.common.utils.LogType;
import com.xbwl.entity.FiCost;
import com.xbwl.entity.OprOvermemo;
import com.xbwl.entity.OprOvermemoDetail;
import com.xbwl.entity.OprSignRoute;
import com.xbwl.finance.dao.IFiCostDao;
import com.xbwl.finance.dto.impl.FiInterfaceImpl;
import com.xbwl.finance.dto.impl.FiInterfaceProDto;
import com.xbwl.oper.stock.dao.IOprOvermemoDao;
import com.xbwl.oper.stock.dao.IOprSignRouteDao;
import com.xbwl.oper.stock.service.IOprHistoryService;
import com.xbwl.oper.stock.service.IOprSignRouteService;

/**
 * author shuw
 * time Aug 2, 2011 1:52:56 PM
 */

@Service("oprSignRouteServiceImpl")
@Transactional(rollbackFor={Exception.class})
public class OprSignRouteServiceImpl extends BaseServiceImpl<OprSignRoute	, Long>
					implements IOprSignRouteService{

	@Resource(name="oprSignRouteHibernateDaoImpl")
	private IOprSignRouteDao oprSignRouteDao;
	
	@Override
	public IBaseDAO<OprSignRoute, Long> getBaseDao() {
		return oprSignRouteDao;
	}

	@Resource(name="fiCostHibernateDaoImpl")
	private IFiCostDao fiCostDao;
	
	@Resource(name = "fiInterfaceImpl")
	private FiInterfaceImpl fiInterfaceImpl;
	
	@Resource(name="oprOvermemoHibernateDaoImpl")
	private IOprOvermemoDao oprOvermemoDao;
	
	@Value("${fiAuditCost.log_auditCost}")
	private Long log_auditCost ;
	
	@Value("${fiAuditCost.log_qxAuditCost}")
	private Long log_qxAuditCost ;
	
	@Resource(name = "oprHistoryServiceImpl")
	private IOprHistoryService oprHistoryService;
	
	/**
	 * @author shuw
	 *��������ǩ������
	 */
	public String getCarTimesNum(Long bussdepartId) throws Exception{
		String carString ;
		Map  times = (Map)oprSignRouteDao.createSQLQuery("  select seq_opr_car_times.nextval cartimes from  dual  ").list().get(0);
		Long s =Long.valueOf(times.get("CARTIMES")+"");
		SimpleDateFormat dateFm = new SimpleDateFormat("yyyyMMdd"); //��ʽ����ǰϵͳ����
		String dateTime = dateFm.format(new java.util.Date());
		carString="A"+dateTime+bussdepartId+s;
		return carString;
	}
	
	//�����ɱ����
	@ModuleName(value="�����ɱ����",logType=LogType.fi)
	public void fiAuditByName(List<OprSignRoute> aa) throws Exception {
		User user= WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
		String username = user.get("name").toString();
		Long departId = Long.parseLong(user.get("departId")+"");
		List<FiInterfaceProDto> listfiInterfaceDto = new ArrayList<FiInterfaceProDto>();
		for(OprSignRoute oRoute:aa){
			
				OprSignRoute oprSignRoute = oprSignRouteDao.get(oRoute.getId());
				if(oprSignRoute.getStatus()!=3l){
					throw new ServiceException("״̬�����޷����л�����");
				}
				oprSignRoute.setTs(oRoute.getTs());
				oprSignRoute.setFiVerifyName(username);
				oprSignRoute.setStatus(4l);
				
				List<OprOvermemo>list =oprOvermemoDao.findBy("routeNumber",oprSignRoute.getRouteNumber() );
				OprOvermemo oprOvermemo = list.get(0);
				Set<OprOvermemoDetail> set =oprOvermemo.getOprOvermemoDetails();
				List<FiCost>listFi = new ArrayList<FiCost>();
				Double dou=0.0;
				if("��ʱ���⳵".equals(oprSignRoute.getUseCarType())){       
					dou =oprSignRoute.getFeeTotal();
				}else {
					dou =oprSignRoute.getTollChargeTotal();
				}
				//���浽�ɱ�����
				Iterator t1=set.iterator() ;
				while(t1.hasNext()){
					FiCost fiCost = new FiCost();
					OprOvermemoDetail oprOvermemoDetail=(OprOvermemoDetail)t1.next();
					fiCost.setDno(oprOvermemoDetail.getDno());
					fiCost.setCostType("ǩ���ɱ�");
					fiCost.setCostTypeDetail(oprSignRoute.getUseCarType());
					if(t1.hasNext()==false){
						fiCost.setCostAmount(dou);
					}else{
						double dou2=0l;
						if("��ʱ���⳵".equals(oprSignRoute.getUseCarType())){                                   //��ʱ���⳵���ڲ�������һ����
							dou2 = DoubleUtil.div(oprSignRoute.getFeeTotal(), set.size(),2);
						}else{
							dou2 = DoubleUtil.div(oprSignRoute.getTollChargeTotal(), set.size(),2);
						}
						fiCost.setCostAmount(dou2);
						oprHistoryService.saveLog(fiCost.getDno(), "֧�������ɱ���֧����"+fiCost.getCostAmount() , log_auditCost);     //������־
						dou=DoubleUtil.sub(dou, dou2);
					}
					fiCost.setSourceSignNo(oprSignRoute.getCarSignNo());
					fiCost.setDataSource("�����ɱ�");
					listFi.add(fiCost);
					fiCostDao.save(fiCost);
				}
				save(oprSignRoute);
				
					
					FiInterfaceProDto fiInterfaceProDto = new FiInterfaceProDto();
					fiInterfaceProDto.setCustomerId(null);
					fiInterfaceProDto.setCustomerName(null);
					fiInterfaceProDto.setDistributionMode("����");
					fiInterfaceProDto.setDno(null);
					fiInterfaceProDto.setSettlementType(2l);
					fiInterfaceProDto.setDocumentsType("�ɱ�");
					fiInterfaceProDto.setDocumentsSmalltype("�����ɱ�");
					fiInterfaceProDto.setDocumentsNo(oprSignRoute.getId());
					if("��ʱ���⳵".equals(oprSignRoute.getUseCarType())){                                   //��ʱ���⳵���ڲ�������һ����
						fiInterfaceProDto.setAmount(oprSignRoute.getFeeTotal());
						fiInterfaceProDto.setCreateRemark("֧��"+oprSignRoute.getDriverName()+"����ǩ����"+oprSignRoute.getFeeTotal());
					}else{
						fiInterfaceProDto.setAmount(oprSignRoute.getTollChargeTotal());
						fiInterfaceProDto.setCreateRemark("֧��"+oprSignRoute.getDriverName()+"����ǩ����"+oprSignRoute.getTollChargeTotal());
					}
					fiInterfaceProDto.setContacts(oprSignRoute.getDriverName());
					fiInterfaceProDto.setCostType("�����ɱ�");
					fiInterfaceProDto.setDepartId(departId);
					fiInterfaceProDto.setCollectionUser(username);   //�տ�������
					fiInterfaceProDto.setSourceData("�����ɱ�");
					fiInterfaceProDto.setSourceNo(oprSignRoute.getId());
					listfiInterfaceDto.add(fiInterfaceProDto);
		}
		fiInterfaceImpl.currentToFiPayment(listfiInterfaceDto);
	}

	//ȡ��������
	@ModuleName(value="�����ɱ��������",logType=LogType.fi)
	public void qxFiAudit(Long id, String ts) throws Exception {
		OprSignRoute oprSignRoute = oprSignRouteDao.get(id);
		if(!ts.equals(oprSignRoute.getTs())){
			throw new ServiceException("�����Ѳ����˴����ݣ������²�ѯ���ٲ���");
		}
		
		if(oprSignRoute.getPayStatus()==1l){
			throw new ServiceException("������֧������������������");
		}
		List<FiCost>list =fiCostDao.find("from FiCost f where f.dataSource=?  and  f.sourceSignNo=?  ", "�����ɱ�",oprSignRoute.getCarSignNo());
		
		for(FiCost fiCost:list){
			FiCost fiCostNew = new FiCost();
			fiCostNew.setCostType("ǩ���ɱ�");
			fiCostNew.setCostTypeDetail("�������");
			fiCostNew.setSourceSignNo(oprSignRoute.getCarSignNo());
			fiCostNew.setCostAmount(- fiCost.getCostAmount());
			fiCostNew.setDataSource("�����ɱ�");
			fiCostNew.setDno(fiCost.getDno());
			fiCostNew.setStatus(1l);
			fiCostDao.save(fiCostNew);  //����
			
			oprHistoryService.saveLog(fiCost.getDno(), "���������ɱ�������֧����"+fiCost.getCostAmount() , log_qxAuditCost);     //������־
		}
		
		FiInterfaceProDto fiInterfaceProDto = new FiInterfaceProDto()	;
		fiInterfaceProDto.setSourceData("�����ɱ�");
		fiInterfaceProDto.setSourceNo(oprSignRoute.getId());
		fiInterfaceImpl.invalidToFiPayment(fiInterfaceProDto);
		
		oprSignRoute.setStatus(1l);
		oprSignRoute.setFiVerifyName(null);
		oprSignRoute.setCarVerifyName(null);
		oprSignRouteDao.save(oprSignRoute);
	}

	@ModuleName(value="Ӧ��Ӧ�����������ɱ�",logType=LogType.fi)
	public void payConfirmationById(Long id) throws Exception{
		OprSignRoute oprSignRoute=this.oprSignRouteDao.get(id);
		if(oprSignRoute==null) throw new ServiceException("�����ɱ�ʧ�ܣ�");
		User user= WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
		oprSignRoute.setPayStatus(1L);
		oprSignRoute.setPayTime(new Date());
		oprSignRoute.setPayUser(user.get("name").toString());
		this.oprSignRouteDao.save(oprSignRoute);
		
	}
	
	public void payConfirmationRegisterById(Long id) throws Exception{
		OprSignRoute oprSignRoute=this.oprSignRouteDao.get(id);
		if(oprSignRoute==null) throw new ServiceException("�����ɱ�ʧ�ܣ�");
		oprSignRoute.setPayStatus(0L);
		oprSignRoute.setPayTime(null);
		oprSignRoute.setPayUser(null);
		this.oprSignRouteDao.save(oprSignRoute);
		
	}

	//������˶��������
	@ModuleName(value="�����ɱ�������˶�Ʊ���",logType=LogType.fi)
	public void carAuditByName(List<OprSignRoute> aa) throws Exception {
		User user= WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
		String username = user.get("name").toString();
		for(OprSignRoute oproute:aa){
			OprSignRoute oprSignRoute=this.oprSignRouteDao.get(oproute.getId());
			if(oprSignRoute.getStatus()!=2){
				throw new ServiceException("���ǵ����״̬�����ύ������ˣ�");
			}
			
			oprSignRoute.setTs(oproute.getTs());  //ʱ��Ω��֤
			oprSignRoute.setStatus(3l);
			oprSignRoute.setCarVerifyName(username);
			oprSignRouteDao.save(oprSignRoute);
		}
	}

	//���ݳ��κţ�ɾ�������ɱ�
	public void cancelCarByRouteNumber(Long routeNumber) throws Exception {
			if(routeNumber==null){
				throw new ServiceException("�����복�κ�");
			}
			List<OprSignRoute>list =find("from OprSignRoute o where o.routeNumber=? and o.status <>0 ",routeNumber);
			for(OprSignRoute osignroute:list){
				if(osignroute.getStatus()!=1){
					throw new ServiceException("�����ɱ��Ѵ�������ɾ��");
				}
				osignroute.setStatus(0l);
				save(osignroute);
			}
	}

	public double getVotesPiece(double weight,double startWeight,double levelWeight,double twoLevelWeight) throws Exception {
		if(levelWeight==0.0||twoLevelWeight==0.0){
			throw new ServiceException("���������ĵȼ�����Ϊ0");
		}
		if(weight<=0.0){
			return 0.0;
		}else if(weight<=startWeight){
			return 1.0;
		}else{
			weight=DoubleUtil.sub(weight, startWeight);  // ��ȥ200Kg��Ʊ��+1
			double piece=1;
			while(weight>0.0){
				if(DoubleUtil.sub(weight, levelWeight)>=0.0){
					piece=piece+1;
					weight=DoubleUtil.sub(weight, levelWeight);
				}else if(DoubleUtil.sub(weight, twoLevelWeight)>=0.0){
					piece=piece+1;
					weight=DoubleUtil.sub(weight, weight);
				}else{
					piece=piece+0.5;
					weight=DoubleUtil.sub(weight, weight);
				}
			}
			return piece;
		}
	}

}

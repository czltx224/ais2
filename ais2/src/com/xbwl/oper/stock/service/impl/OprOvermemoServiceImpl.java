package com.xbwl.oper.stock.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.hibernate.Query;
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
import com.xbwl.cus.service.IConsigneeInfoService;
import com.xbwl.entity.BasCar;
import com.xbwl.entity.BasDriver;
import com.xbwl.entity.ConsigneeInfo;
import com.xbwl.entity.CtTmD;
import com.xbwl.entity.OprFaxIn;
import com.xbwl.entity.OprLoadingbrigadeWeight;
import com.xbwl.entity.OprOvermemo;
import com.xbwl.entity.OprOvermemoDetail;
import com.xbwl.entity.OprReceipt;
import com.xbwl.entity.OprRequestDo;
import com.xbwl.entity.OprShuntApplyDetail;
import com.xbwl.entity.OprSignRoute;
import com.xbwl.entity.OprStatus;
import com.xbwl.entity.OprStock;
import com.xbwl.entity.OprStoreArea;
import com.xbwl.entity.SysDepart;
import com.xbwl.finance.dto.IFiInterface;
import com.xbwl.finance.dto.impl.FiInterfaceProDto;
import com.xbwl.oper.edi.service.ICtTmDService;
import com.xbwl.oper.fax.dao.IOprFaxInDao;
import com.xbwl.oper.receipt.service.IOprReceiptService;
import com.xbwl.oper.stock.dao.IOprOvermemoDao;
import com.xbwl.oper.stock.dao.IOprStatusDao;
import com.xbwl.oper.stock.dao.IOprStockDao;
import com.xbwl.oper.stock.dao.IOprStoreAreaDao;
import com.xbwl.oper.stock.service.IOprHistoryService;
import com.xbwl.oper.stock.service.IOprLoadingbrigadeWeightService;
import com.xbwl.oper.stock.service.IOprOvermemoDetailService;
import com.xbwl.oper.stock.service.IOprOvermemoService;
import com.xbwl.oper.stock.service.IOprRequestDoService;
import com.xbwl.oper.stock.service.IOprSignRouteService;
import com.xbwl.oper.stock.service.IOprStoreAreaService;
import com.xbwl.oper.stock.vo.CarGoVo;
import com.xbwl.oper.stock.vo.LoadingbrigadeTypeEnum;
import com.xbwl.oper.takegoods.dao.IOprShuntApplyDetailDao;
import com.xbwl.rbac.Service.IDepartService;
import com.xbwl.sys.dao.IBasCarDao;
import com.xbwl.sys.dao.ICustomerDao;
import com.xbwl.sys.service.IBasDriverService;
import com.xbwl.sys.service.ICustomerService;
import com.xbwl.ws.client.result.base.WSResult;

/**
 * author CaoZhili time Jul 2, 2011 2:43:54 PM
 * 
 * ���ӵ���������ʵ����
 */
@Service("oprOvermemoServiceImpl")
@Transactional(rollbackFor={Exception.class})
public class OprOvermemoServiceImpl extends BaseServiceImpl<OprOvermemo, Long>
		implements IOprOvermemoService {
	
	@Resource(name="oprOvermemoHibernateDaoImpl")
	private IOprOvermemoDao oprOvermemoDao;
	
	@Resource(name="oprFaxInHibernateDaoImpl")
	private IOprFaxInDao oprFacInDao;
	
	@Resource(name="oprStoreAreaHibernateDaoImpl")
	private IOprStoreAreaDao oprStoreAreaDao;
	
	@Resource(name="customerHibernateDaoImpl")
	private ICustomerDao customerDao;
	
	@Resource(name="oprStoreAreaServiceImpl")
	private IOprStoreAreaService oprStoreAreaServiceImpl;
	
	@Resource(name = "oprFaxInHibernateDaoImpl")
	private IOprFaxInDao oprFaxInDao;
	
	@Resource(name="oprSignRouteServiceImpl")
	private IOprSignRouteService oprSignRouteService;
	
	@Resource(name = "basDriverServiceImpl")
	private IBasDriverService basDriverService;
	
	@Resource(name="departServiceImpl")
	private IDepartService departService;
	
	@Resource(name="oprStockDaoImpl")
	private IOprStockDao oprStockDao;
	
	@Resource(name = "oprStatusHibernateDaoImpl")
	private IOprStatusDao oprStatusDao;
	
	@Resource(name="oprReceiptServiceImpl")
	private IOprReceiptService oprReceiptService;
	
	@Resource(name = "oprHistoryServiceImpl")
	private IOprHistoryService oprHistoryService;
	
	@Resource(name="basCarHibernateDaoImpl")
	private IBasCarDao basCarDao;
	
	@Resource(name="oprShuntApplyDetailHibernateDaoImpl")
	private IOprShuntApplyDetailDao oprShuntApplyDetailDao;
	
	@Resource(name="fiInterfaceImpl")
	private IFiInterface fiInterface;
	
	@Value("${oprOvermemoServiceImpl.log_carArrive}")
	private Long log_carArrive;//����ȷ��
	
	@Value("${oprOvermemoServiceImpl.log_carArriveReturn}")
	private Long log_carArriveReturn;//����ȷ�ϳ���
	
	@Value("${oprOvermemoServiceImpl.log_carUploadstart}")
	private Long log_carUploadstart;//ж����ʼ
	
	@Value("${oprOvermemoServiceImpl.log_carUploadend}")
	private Long log_carUploadend;//ж������
	
	@Value("${oprOvermemoServiceImpl.oprovermemoType}")
	private String oprOvermemoType;//���ӵ�����
	
	@Value("${oprOvermemoServiceImpl.log_delSureCargo}")
	private Long log_delSureCargo;//ȡ��ʵ��
	
	@Value("${oprOvermemoServiceImpl.log_carOut}")
	private Long log_carOut;//����ȷ��
	
	@Value("${oprReceiptServiceImpl.log_delReceiptOutStore}")
	private Long log_delReceiptOutStore ;  //�ص�����
	
	@Value("${oprOvermemoServiceImpl.log_delOutCar}")
	private Long log_delOutCar ;  //��������ȷ��
	
	@Value("${oprFaxInServiceImpl.log_ediFailure}")
	private Long log_ediFailure;//��תEDIд��ʧ��
	
	@Resource(name="consigneeInfoServiceImpl")
	private IConsigneeInfoService consigneeInfoService;
	
	@Resource(name="oprRequestDoServiceImpl")
	private IOprRequestDoService oprRequestDoService;
	
	@Resource(name="ctTmDServiceImpl")
	private ICtTmDService ctTmDService;
	
	@Value("${opr.edi_distributionMode}")
	private String edi_distributionMode;
	
	@Resource(name="customerServiceImpl")
	private ICustomerService customerService;
	
	@Resource(name="oprOvermemoDetailServiceImpl")
	private IOprOvermemoDetailService oprOvermemoDetailService;
	@Resource(name="oprLoadingbrigadeWeightServiceImpl")
	private IOprLoadingbrigadeWeightService iOprLoadingbrigadeWeightService;//װж�����
	
	@Resource(name="oprLoadingbrigadeWeightServiceImpl")
	private IOprLoadingbrigadeWeightService oprLoadingbrigadeWeightService;
	
	@Override
	public IBaseDAO<OprOvermemo, Long> getBaseDao() {
		return this.oprOvermemoDao;
	}

	public boolean carUpload(Long routeNum) throws Exception {
		boolean flag=true;
		List<OprOvermemo> overList=this.findOverByrouteNum(routeNum);
		for (OprOvermemo oprOvermemo:overList) {
			//ֻ�ܶ��ѵ���ȷ�ϵ���ж����ʼ����
			if(oprOvermemo.getStatus()!=1){
				flag=false;
				break;
			}else{
				List<BasCar> bcList = basCarDao.findBy("carCode", oprOvermemo.getCarCode());
				if(bcList.size()>0){
					BasCar bc = bcList.get(0);
					bc.setCarStatus("ж����");
					basCarDao.save(bc);
				}
				oprOvermemo.setStatus(Long.valueOf(2));
				oprOvermemo.setUnloadStartTime(new Date());
				this.save(oprOvermemo);
				for(OprOvermemoDetail ood:oprOvermemo.getOprOvermemoDetails()){
					OprFaxIn ofi = oprFaxInDao.getAndInitEntity(ood.getDno());
					if(ofi !=null && ofi.getStatus() == 1){
						oprHistoryService.saveLog(ood.getDno(),"���͵���Ϊ��"+ood.getDno()+"�Ļ����Ѿ���ʼж��", log_carUploadstart);
					}
				}
			}
		}
		return flag;
	}

	public void carUploadReturn(Long routeNum) throws Exception {
			if(this.isCarUpload(routeNum)){
				List<FiInterfaceProDto> listfiInterfaceDto = new ArrayList<FiInterfaceProDto>();
				List<OprOvermemo> overList=this.findOverByrouteNum(routeNum);
				for(OprOvermemo oo:overList){
					oo.setStatus(Long.valueOf(0));
					oo.setEndTime(null);
					oo.setOrderfields(null);
					this.save(oo);
					for(OprOvermemoDetail ood:oo.getOprOvermemoDetails()){
						OprFaxIn ofi = oprFaxInDao.getAndInitEntity(ood.getDno());
						if(ofi !=null && ofi.getStatus() == 1){
							oprHistoryService.saveLog(ood.getDno(),"���͵���Ϊ��"+ood.getDno()+"�Ļ��ﵽ��ȷ�ϳ���", log_carArriveReturn);
						}
						
					}
					if("���Ž���".equals(oo.getOvermemoType())){
						FiInterfaceProDto fpd = new FiInterfaceProDto();
						fpd.setSourceData("����ȷ��");
						fpd.setSourceNo(oo.getId());
						listfiInterfaceDto.add(fpd);
					}
				}
				//������ڲ��Ž��ӵĻ������Ҫ�����ڲ��ɱ�
				if(listfiInterfaceDto.size()>0){
					fiInterface.invalidInternalCostToFi(listfiInterfaceDto);
				}
			}
	}

	public boolean carArriveConfirm(Long routeNumber,String orderbyName,User user)
	throws Exception {
		List<OprOvermemo> overList=this.findOverByrouteNum(routeNumber);
		List<FiInterfaceProDto> listfiInterfaceDto = new ArrayList<FiInterfaceProDto>();
		for(OprOvermemo oprOvermemo:overList){
			//��ÿ������
			Set<OprOvermemoDetail> set=oprOvermemo.getOprOvermemoDetails();
			List<OprStoreArea> list=oprStoreAreaDao.findBy("departId", Long.valueOf(user.get("bussDepart").toString()));
			for (OprOvermemoDetail overmemoDetail : set) {
				OprFaxIn oprFaxIn=oprFacInDao.getAndInitEntity(overmemoDetail.getDno());
				for (OprStoreArea oprStoreArea : list) {
					//���ȫ��ƥ�������ÿ������
					if(oprFaxIn==null){
						overmemoDetail.setStorageArea("�޴���");
					}else{
						overmemoDetail.setStorageArea(oprStoreAreaServiceImpl.getStockArea(oprStoreArea,oprFaxIn));
						List<OprStatus> osList = oprStatusDao.findBy("dno",oprFaxIn.getDno());                            // ��д״̬������ֿ�״̬ʱ��ȷ���ˣ�
						if(osList.size()>0){
							OprStatus os=osList.get(0);
							os.setDoStoreStatus(1l);
							os.setDoStoreName(user.get("userName")+"");
							os.setDoStoreTime(new Date());
							oprStatusDao.save(os);
						}
					}
					overmemoDetail.setOprOvermemo(oprOvermemo);
				}
				List<OprStatus> staList=oprStatusDao.findBy("dno", overmemoDetail.getDno());
				//��д״̬�� �Ƿ񵽲ֿ⣬���ֿ�ʱ��
				if(staList.size()>0){
					OprStatus oprStatus=staList.get(0);
					oprStatus.setDoStoreStatus(1L);
					oprStatus.setDoStoreTime(new Date());
					oprStatus.setDoStoreName(user.get("name").toString());
					oprStatusDao.save(oprStatus);
				}
				OprFaxIn ofi = oprFaxInDao.getAndInitEntity(overmemoDetail.getDno());
				if(ofi != null && ofi.getStatus() == 1){
					oprHistoryService.saveLog(overmemoDetail.getDno(),"���͵���Ϊ��"+overmemoDetail.getDno()+"�Ļ��ﵽ��ȷ��", log_carArrive);
				}
				if("���Ž���".equals(oprOvermemo.getOvermemoType())){
					//����ɱ��ӿڲ�������
					FiInterfaceProDto fiDto = new FiInterfaceProDto();
					fiDto.setStartDepartId(oprOvermemo.getStartDepartId());
					fiDto.setStartDepartName(oprOvermemo.getStartDepartName());
					fiDto.setEndDepart(oprOvermemo.getEndDepartName());
					fiDto.setEndDepartId(oprOvermemo.getEndDepartId());
					fiDto.setSourceData("����ȷ��");
					fiDto.setSourceNo(oprOvermemo.getId());//���ӵ������ţ���ʵ�䵥��
					fiDto.setDno(overmemoDetail.getDno());
					fiDto.setFlightPiece(overmemoDetail.getPiece());
					fiDto.setFlightWeight(overmemoDetail.getWeight());
					fiDto.setBulk(oprFaxIn.getBulk());
					fiDto.setCustomerId(overmemoDetail.getCusId());
					fiDto.setDistributionMode(oprFaxIn.getDistributionMode());
					
					String outTakeMode = oprFaxIn.getDistributionMode(); 
					if(oprFaxIn.getDistributionMode().equals("�ⷢ")){
						outTakeMode += oprFaxIn.getTakeMode().substring(2); 
					}else{
						String areaType = oprFaxIn.getAreaType().equals("����")?oprFaxIn.getAreaType():"����";
						if(oprFaxIn.getTakeMode().indexOf("����")>0){
							areaType="";
						}
						outTakeMode += areaType+ oprFaxIn.getTakeMode().substring(2); 
					}
					fiDto.setOutStockMode(outTakeMode);
					listfiInterfaceDto.add(fiDto);
				}
		 	}
			oprOvermemo.setStatus(Long.valueOf(1));
			oprOvermemo.setOprOvermemoDetails(set);
			oprOvermemo.setEndTime(new Date());
			oprOvermemo.setOrderfields(orderbyName);
			this.save(oprOvermemo);
			
//			if("���Ž���".equals(oprOvermemo.getOvermemoType())){
//				if(oprOvermemo.getEndDepartId().equals(205l) || oprOvermemo.getEndDepartId().equals(240l)){
//					if(oprOvermemo.getStartDepartId().equals(205l) || oprOvermemo.getStartDepartId().equals(240l)){
//						//������ò����ڲ��ɱ��ӿ�  
//						//1�����Ž��� 2��ʼ�����Ż����ն˲����ǹ��ݺ����ڲŵ��ýӿ�
//						//ȥ���������� 12-04-01
//						this.fiInterface.internalCostToFi(listfiInterfaceDto);
//					}
//				}
//			}
		}
		//������ڲ��Ž��ӵĻ��� ����Ҫ�����ڲ��ɱ��ӿ�
		if(listfiInterfaceDto.size()>0){
			this.fiInterface.internalCostToFi(listfiInterfaceDto);
		}
		return true;
	}

	public boolean isCarUpload(Long routeNumber) throws Exception {
		boolean flag=true;
		List<OprOvermemo> overList=this.findOverByrouteNum(routeNumber);
		for(OprOvermemo oo:overList){
			//ֻ�ܳ����ѵ���ȷ�ϵ�����
			if(oo.getStatus()!=1){
				flag=false;
        		break;
			}
			for(OprOvermemoDetail ood:oo.getOprOvermemoDetails()){
				if(ood.getStatus()==null){
					throw new ServiceException(ood.getId()+"���ӵ���ϸ�㵽״̬Ϊ����");
				}else{
					if(ood.getStatus()==1){
						flag=false;
						break;
					}
				}
			}
		}
		return flag;
	}

	public boolean isCarArriveConfirm(Long routeNum) throws Exception {
		boolean flag=true;
		List<OprOvermemo> overList=this.findOverByrouteNum(routeNum);
		for (OprOvermemo oo:overList) {
			//ֻ�ܶ��ѷ����Ľ��е���ȷ��
			if(oo.getStatus()!=0){
				flag=false;
        		break;
			}
		}
		return flag;
	}

	public boolean isPrintMsg(Long routeNum) throws Exception {
		boolean flag=true;
		List<OprOvermemo> overList=this.findOverByrouteNum(routeNum);
		for(OprOvermemo oo:overList){
			if(oo.getStatus()!=2){
				flag=false;
			}
		}
		return flag;
	}

	public void printMsg(Long routeNum) throws Exception {
		User user = WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
		Long bussDepartId = Long.parseLong(user.get("bussDepart").toString());
		String  carTimes =oprSignRouteService.getCarTimesNum(bussDepartId);
		BasDriver basDriver=null;
		
		List<OprSignRoute> listr   = oprSignRouteService.find("from OprSignRoute os where os.routeNumber = ? and os.status>0 ", routeNum);
		if(listr.size()>0){
			throw new ServiceException("�������Ѵ�ӡ����ǩ�������ܶ�δ�ӡ");
		}
		List<OprOvermemo>list  = oprOvermemoDao.findBy("routeNumber", routeNum);
		OprOvermemo oprOvermemo = list.get(0);
		Date date = new Date();
		SysDepart depart =departService.get(bussDepartId);
		OprSignRoute oprSignRoute = new OprSignRoute();
		oprSignRoute.setCarSignNo(carTimes);
		oprSignRoute.setUseCarType(oprOvermemo.getUseCarType());

		if(oprOvermemo.getCarId()!=null){
			BasCar basCar = basCarDao.get(oprOvermemo.getCarId());
			if(basCar!=null){
				oprSignRoute.setCarNo(basCar.getCarCode());
				oprSignRoute.setMaxloadWeight(Double.valueOf(basCar.getMaxloadWeight()));
			}
		}else{
			oprSignRoute.setCarNo(oprOvermemo.getCarCode());
		}
		
		oprSignRoute.setRentCarResult(oprOvermemo.getRentCarResult());
		oprSignRoute.setPrintDepartId(bussDepartId);
		oprSignRoute.setPrintDepartName(depart.getDepartName());
		oprSignRoute.setPrintName(user.get("name")+"");
		oprSignRoute.setPrintNum(1l);
		
		if(basDriver!=null){
			oprSignRoute.setUserCode(basDriver.getUserCode());
			oprSignRoute.setDriverName(basDriver.getDriverName());
		}
		oprSignRoute.setRouteNumber(routeNum);
		oprSignRoute.setUseCarDate(date);
		oprSignRoute.setCreateDeptId(bussDepartId);
		oprSignRoute.setCreateDepartName(depart.getDepartName());
		oprSignRoute.setStartAddr(depart.getDepartName());
		oprSignRoute.setEndAddr(oprOvermemo.getEndDepartName());
		oprSignRoute.setStartTime(date);
		oprSignRoute.setStatus(1l);

		oprSignRoute.setCarId(oprOvermemo.getCarId());

		//oprSignRoute.setRouteNumber(carciLong);
		oprSignRouteService.save(oprSignRoute);
	}
	
	public Integer findMemoBy(Long carId,Long overmemoId){
		
		//StringBuffer hql=new StringBuffer("select id from OprOvermemo where 1=1 and status=1");
		StringBuffer hql=new StringBuffer("select id from OprOvermemo where 1=1");
		
		if(carId>0){
			hql.append(" and carId="+carId);
		}
		if(overmemoId>0){
			hql.append(" and Id="+overmemoId);
		}
		
		Query query =this.oprOvermemoDao.getSession().createQuery(hql.toString());
		Integer size=query.list().size();
		//this.oprOvermemoDao.getSession().flush();
		return size;
	}

	@ModuleName(value="����ȷ�ϱ���",logType=LogType.buss)
	public Long saveOprSignRouteAndOvem(CarGoVo goVo,List<Long> ids) throws Exception{
		String  carTimes =oprSignRouteService.getCarTimesNum(goVo.getBussDepartId());
		User user = WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
		Long carciLong=this.findRouteNumberSeq();
		BasDriver basDriver=null;
		if(goVo.getDriverId()!=null){
			basDriver = basDriverService.get(goVo.getDriverId());
		}
		
		Date date = new Date();
		SysDepart depart =departService.get(goVo.getBussDepartId());
		OprSignRoute oprSignRoute=null;
		if(goVo.getCheckPrint()==1){
				oprSignRoute = new OprSignRoute();
				oprSignRoute.setCarSignNo(carTimes);
				if(goVo.getCarCode()!=null&&!"".equals(goVo.getCarCode())){
					oprSignRoute.setCarNo(goVo.getCarCode());
				}
				if(goVo.getDriverName()!=null&&!"".equals(goVo.getDriverName())){
					oprSignRoute.setDriverName(goVo.getDriverName());
				}
				if(basDriver!=null){
					oprSignRoute.setUserCode(basDriver.getUserCode());
					oprSignRoute.setDriverName(basDriver.getDriverName());
				}
				oprSignRoute.setUseCarDate(date);
				oprSignRoute.setCreateDeptId(goVo.getBussDepartId());
				oprSignRoute.setCreateDepartName(depart.getDepartName());
				oprSignRoute.setStartAddr(depart.getDepartName());
				OprOvermemo overmemo =this.get(ids.get(0));
				oprSignRoute.setEndAddr(overmemo.getEndDepartName());
				oprSignRoute.setStartTime(date);
							
				oprSignRoute.setStatus(1l);
				if(goVo.getCarId()!=null){
						oprSignRoute.setCarId(goVo.getCarId());
				}
				oprSignRoute.setIsSeparateDelivery(goVo.getCheckAlone());
				if(goVo.getDriverPhone()!=null&&!"".equals(goVo.getDriverPhone())){
					oprSignRoute.setPhone(goVo.getDriverPhone());
				}
				oprSignRoute.setPrintDepartId(goVo.getBussDepartId());
				oprSignRoute.setPrintDepartName(depart.getDepartName());
				oprSignRoute.setPrintName(user.get("name")+"");
				oprSignRoute.setPrintNum(1l);
				oprSignRoute.setUseCarType(goVo.getUseCarType());
				oprSignRoute.setRentCarResult(goVo.getRentCarResult());
				oprSignRoute.setRouteNumber(carciLong);
				
				
		}
		double votesPiece=0.0; //�ۺ�Ʊ�� 
		for(Long id :ids ){
			List<FiInterfaceProDto> listfiInterfaceDto = new ArrayList<FiInterfaceProDto>();
			OprOvermemo oprOvermemo = get(id);
			oprOvermemo.setRouteNumber(carciLong);
			oprOvermemo.setUseCarType(goVo.getUseCarType());
			oprOvermemo.setRentCarResult(goVo.getRentCarResult());
			if(goVo.getLockNum()!=null){
				oprOvermemo.setLockNo(goVo.getLockNum());
			}
			if(goVo.getCarId()!=null){
				oprOvermemo.setCarId(goVo.getCarId());
			}
			oprOvermemo.setCarCode(goVo.getCarCode()==null?"":goVo.getCarCode());
			oprOvermemo.setStartTime(date);
			oprOvermemo.setStatus(0l);
			save(oprOvermemo);
			
			for(OprOvermemoDetail overmemoDetail :oprOvermemo.getOprOvermemoDetails()){
				List<OprStatus> list2 =oprStatusDao.find(" from OprStatus o where o.dno=?  ",overmemoDetail.getDno());
				if(list2.size()==0){
					throw new ServiceException("ȡ��������״̬����");
				}
				list2.get(0).setDepartOvermemoStatus(0l);
				list2.get(0).setDepartOvermemoStartTime(date);
				oprStatusDao.save(list2.get(0));
				OprFaxIn oprFaxIn = oprFaxInDao.get(overmemoDetail.getDno());
				
				oprHistoryService.saveLog(overmemoDetail.getDno(),"����ȷ�� ����ȷ�ϼ�����"+overmemoDetail.getRealPiece(), log_carOut);
				if(goVo.getCheckPrint()==1){
					if(overmemoDetail.getRealPiece()!=null){
						if(overmemoDetail.getRealPiece()-oprFaxIn.getPiece()>=0||oprFaxIn.getPiece()==null){
							votesPiece=DoubleUtil.add(oprSignRouteService.getVotesPiece(oprFaxIn.getCusWeight(), 200.00, 100.00,50.00),votesPiece); //�����ۺ�Ʊ��
						}else{
							double weight=DoubleUtil.mul(oprFaxIn.getCusWeight(),(double)overmemoDetail.getRealPiece()/(double)oprFaxIn.getPiece());
							votesPiece=DoubleUtil.add(oprSignRouteService.getVotesPiece(weight, 200.00, 100.00,50.00),votesPiece);
						}
					}
				}
				
				if("���Ž���".equals(oprOvermemo.getOvermemoType())){
					//����ɱ��ӿڲ�������
					FiInterfaceProDto fiDto = new FiInterfaceProDto();
					fiDto.setStartDepartId(oprOvermemo.getStartDepartId());
					fiDto.setStartDepartName(oprOvermemo.getStartDepartName());
					fiDto.setEndDepart(oprOvermemo.getEndDepartName());
					fiDto.setEndDepartId(oprOvermemo.getEndDepartId());
					fiDto.setSourceData("����ȷ��");
					fiDto.setSourceNo(oprOvermemo.getId());//���ӵ������ţ���ʵ�䵥��
					fiDto.setDno(overmemoDetail.getDno());
					fiDto.setFlightPiece(overmemoDetail.getPiece());
					fiDto.setFlightWeight(overmemoDetail.getWeight());
					fiDto.setBulk(oprFaxIn.getBulk());
					fiDto.setCustomerId(overmemoDetail.getCusId());
					fiDto.setDistributionMode(oprFaxIn.getDistributionMode());
					
					String outTakeMode = oprFaxIn.getDistributionMode(); 
					if(oprFaxIn.getDistributionMode().equals("�ⷢ")){
						outTakeMode += oprFaxIn.getTakeMode().substring(2); 
					}else{
						String areaType = oprFaxIn.getAreaType().equals("����")?oprFaxIn.getAreaType():"����";
						if(oprFaxIn.getTakeMode().indexOf("����")>0){
							areaType="";
						}
						outTakeMode += areaType+ oprFaxIn.getTakeMode().substring(2); 
					}
					fiDto.setOutStockMode(outTakeMode);
					listfiInterfaceDto.add(fiDto);
				}
			}
			if(null==oprOvermemo.getOvermemoType()){
				continue;
			}
			if(null==oprOvermemo.getStartDepartId() || null==oprOvermemo.getEndDepartId() ){
				continue;
			}
			if("���Ž���".equals(oprOvermemo.getOvermemoType())){
					//������ò����ڲ��ɱ��ӿ�  
					//1�����Ž��� 2��ʼ�����Ż����ն˲����ǹ��ݺ����ڲŵ��ýӿ�
					this.fiInterface.internalCostToFi(listfiInterfaceDto);
			}
		}
		if(oprSignRoute!=null){
			oprSignRoute.setTotalPoll(votesPiece);
			oprSignRouteService.save(oprSignRoute);
		}
		return carciLong;
	}
	
	public Long getNewDno(){
		List list=this.oprOvermemoDao.getSession().createSQLQuery("select seq_no_dno.nextval as dno from dual").list();
		return Long.valueOf(list.get(0).toString());
	}

	@ModuleName(value="ȡ��ʵ��",logType=LogType.buss)
	public void deleteOprOvermemo(List<Long> list,Long bussDepart,String overmemoType) throws Exception {
		User user= WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
		List<FiInterfaceProDto> listfiInterfaceDto =new ArrayList<FiInterfaceProDto>();
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy��MM��dd��");
		for(Long id:list){
			OprOvermemo ovem = oprOvermemoDao.get(id);
			if(ovem==null){
				throw new ServiceException("�����ѳ���ʵ��");
			}
			
			int size=0;
			if(!"���Ž���".equals(overmemoType)){
				FiInterfaceProDto fiInterfaceProDto = new FiInterfaceProDto();  //�ǲ��Ž��ӵĻ������ò���ӿ�
				fiInterfaceProDto.setSourceData("ʵ�䵥");
				fiInterfaceProDto.setSourceNo(id);
				listfiInterfaceDto.add(fiInterfaceProDto);
				size=fiInterface.invalidToFi(listfiInterfaceDto);
			}
		
			
			for(OprOvermemoDetail overmemoDetail : ovem.getOprOvermemoDetails()){
				List<OprStock> lists = oprStockDao.find(" from OprStock o where o.dno=? and o.departId=? ",overmemoDetail.getDno(),bussDepart);
				OprStock oso = lists.get(0);
				if ("ר��".equals(overmemoType)) {
					oso.setPiece(oso.getPiece()+overmemoDetail.getPiece());
				}else{
					oso.setPiece(oso.getPiece()+overmemoDetail.getRealPiece());
				}
				oprStockDao.save(oso);                                                  //�޸Ŀ��
				
				OprFaxIn oprFaxIn = oprFaxInDao.get(overmemoDetail.getDno());
				oprFaxIn.setRealGoWhere("");
				oprFaxInDao.save(oprFaxIn);                                           //ȥ������
				
				if(overmemoDetail.getReNum()>0){
					List<OprReceipt> reList = this.oprReceiptService.findBy("dno", overmemoDetail.getDno());
					if(null!=reList && reList.size()>0){
						 OprReceipt receipt = reList.get(0);
						 receipt.setOutStockMan(null);
						 receipt.setOutStockNum(0l);
						 receipt.setOutStockStatus(0l);
						 receipt.setOutStockTime(null);
						 receipt.setGetMan("");
						 receipt.setGetNum(null);
						 receipt.setGetTime(null);
						 receipt.setGetStatus(0l);
						 receipt.setCurStatus("�����");
						 this.oprHistoryService.saveLog(receipt.getDno(), user.get("userName")+"��"+sdf.format(dt)+"ȡ������ص�"+receipt.getReachNum()+"��", log_delReceiptOutStore);
						 this.oprReceiptService.save(receipt);                     //�ص�״̬�ع�
					 }
				}
				
				List<OprStatus> list2 =oprStatusDao.find(" from OprStatus o where o.dno=?  ",overmemoDetail.getDno());
			  	OprStatus osu= list2.get(0);
			  	if("���Ž���".equals(overmemoType)){
			  		osu.setDepartOvermemoStatus(0l);
			  	}else{
			  		if(overmemoDetail.getRealPiece()!=overmemoDetail.getPiece()){
			  			osu.setOutStatus(2l);
			  		}else{
			  			osu.setOutStatus(0l);
			  		}
			  		
			  		if(size>0){
						osu.setIsCreateFi(0l);
					}
			  	}	
			  	oprStatusDao.save(osu);
			  	oprHistoryService.saveLog(overmemoDetail.getDno(),"ȡ��ʵ�䣬ʵ�䵥�ţ�"+ovem.getId()+"��ʵ�������"+overmemoDetail.getRealPiece(), log_delSureCargo);
			}
			
			List<OprLoadingbrigadeWeight> loadingbrigadeList = this.oprLoadingbrigadeWeightService.findBy("overmemoNo", id);
			//ɾ��װж��������¼
			for (OprLoadingbrigadeWeight loadingbrigade:loadingbrigadeList){
				this.oprLoadingbrigadeWeightService.delete(loadingbrigade);
			}
			
			oprOvermemoDao.delete(id);
		}
	}
	/**
	 * ���ݳ��κŲ�ѯ���ӵ���Ϣ
	 * LiuH
	 */
	protected List<OprOvermemo> findOverByrouteNum(Long routeNum){
		return this.find("from OprOvermemo oo where oo.routeNumber=?", routeNum);
	}
	/**
	 * ��ó��κ�
	 * @return
	 */
	public Long findRouteNumberSeq()throws Exception{
		List list=this.oprOvermemoDao.getSession().createSQLQuery("select SEQ_CAR_ROUTENUMBER.nextval as routenumber from dual").list();
		return Long.valueOf(list.get(0).toString());
	}

	public Page findStartDepart(Page page) throws Exception {
		Page page1=this.getPageBySql(page, "select distinct start_depart_name startName from opr_overmemo", new String[]{});
		return page1;
	}

	public void handAddOpr(OprOvermemo oprOvermemo,List<OprOvermemoDetail> oprDetails,User user,List<FiInterfaceProDto> list,Long loadId) throws Exception {
		Set<OprOvermemoDetail> set=new HashSet<OprOvermemoDetail>(oprDetails);
		oprOvermemo.setStatus(Long.valueOf(0));
		oprOvermemo.setCreateName(user.get("name").toString());
		oprOvermemo.setCreateTime(new Date());
		oprOvermemo.setEndDepartId(Long.valueOf(user.get("bussDepart")+""));
		oprOvermemo.setEndDepartName(user.get("rightDepart").toString());
		Iterator<OprOvermemoDetail> iter=set.iterator();
		while(iter.hasNext()){
			OprOvermemoDetail opr=iter.next();
			if(opr.getDno()==0){
				opr.setDno(this.getNewDno());
				opr.setStatus(Long.valueOf(0));
			}else{
				OprStatus os = oprStatusDao.findBy("dno", opr.getDno()).get(0);
				os.setAirportOutcarStatus(1L);
				os.setAirportOutcarTime(oprOvermemo.getStartTime());
			}
			opr.setCreateName(user.get("name").toString());
			opr.setCreateTime(new Date());
			opr.setOprOvermemo(oprOvermemo);
		}
		//������κ�Ϊ�գ�������һ��
		if(oprOvermemo.getRouteNumber()==null){
			oprOvermemo.setRouteNumber(this.findRouteNumberSeq());
		}
		else{
			List<OprShuntApplyDetail> listSad=oprShuntApplyDetailDao.findBy("routeNumber", oprOvermemo.getRouteNumber());
			OprShuntApplyDetail osad=listSad.get(0);
			osad.setRouteNumber(null);
			oprShuntApplyDetailDao.save(osad);
		}
		oprOvermemo.setOprOvermemoDetails(set);
		/*
		FiInterfaceProDto fpd=new FiInterfaceProDto();
		fpd.setCustomerId(oprOvermemo.getStartDepartId());
		fpd.setFlightMainNo(flightMainNo);
		fpd.setFlightWeight(mainWeight);
		if(mainPiece==null){
			fpd.setFlightPiece(oprOvermemo.getTotalPiece());
		}else{
			fpd.setFlightPiece(mainPiece);
		}
		List<FiInterfaceProDto> list=new ArrayList<FiInterfaceProDto>();
		list.add(fpd);*/
		this.save(oprOvermemo);
		//��������Žӻ�����д��װж�������
		if(oprOvermemo.getOvermemoType().equals("���Žӻ�")){
			Iterator ite = set.iterator();
			List<OprLoadingbrigadeWeight> loadList = new ArrayList<OprLoadingbrigadeWeight>();
			while(ite.hasNext()){
				OprOvermemoDetail ood  = (OprOvermemoDetail)ite.next();
				OprLoadingbrigadeWeight ow = new OprLoadingbrigadeWeight();
				ow.setDno(ood.getDno());
				ow.setWeight(ood.getWeight());
				ow.setGoods(ood.getGoods());
				ow.setLoadingbrigadeId(loadId);
				ow.setOvermemoNo(oprOvermemo.getId());
				ow.setPiece(ood.getPiece());
				
				loadList.add(ow);
			}
			
			iOprLoadingbrigadeWeightService.saveLoadingWeight(loadList, LoadingbrigadeTypeEnum.JIEHUO);
		}
		
		fiInterface.storageToFiDeliverCost(list);
		
		List<BasCar> carList=basCarDao.findBy("carCode", oprOvermemo.getCarCode());
		if(carList.size()>0){
			BasCar bc=carList.get(0);
			bc.setCarStatus("װ����");
			basCarDao.save(bc);
		}
		
	}

	/**
	 * ж������
	 * @return
	 */
	public boolean carEndUpload(Long id, Long routeNumber) throws Exception {
		List<OprOvermemo> overList=this.findOverByrouteNum(routeNumber);
		
		for(OprOvermemo oo:overList){
			oo.setUnloadEndTime(new Date());
			oo.setStatus(Long.valueOf(3));
			for(OprOvermemoDetail ood:oo.getOprOvermemoDetails()){
				OprFaxIn ofi = oprFaxInDao.getAndInitEntity(ood.getDno());
				if(ofi != null && ofi.getStatus()==1){
					oprHistoryService.saveLog(ood.getDno(),"���͵���Ϊ��"+ood.getDno()+"�Ļ���ж����������ӡǩ��", log_carUploadend);
				}
			}
			List<BasCar> bcList = basCarDao.findBy("carCode", oo.getCarCode());
			if(bcList.size()>0){
				BasCar bc = bcList.get(0);
				bc.setCarStatus("������");
				basCarDao.save(bc);
			}
		}
		return true;
	}

	/**
	 * ȡ������ȷ��
	 * @return
	 */	
	public String cancelOvermemo(Long routeNumber) throws Exception {
		if(routeNumber==null){
			throw new ServiceException("�ύ�ĳ��κŲ���Ϊ��"); 
		}
		
		List<OprOvermemo> oprOvemList=find("from OprOvermemo oo where oo.routeNumber=? ",routeNumber);
		if(oprOvemList.size()==0){
			throw new ServiceException("û���ҵ���Ӧ��ʵ������"); 
		}
		
		//  invalidInternalCostToFi
		List<FiInterfaceProDto> fiList=new ArrayList<FiInterfaceProDto>();
		for(OprOvermemo oprOvermemo:oprOvemList){
			FiInterfaceProDto fiInterfaceProDto = new FiInterfaceProDto();
			fiInterfaceProDto.setSourceNo(oprOvermemo.getId());
			fiInterfaceProDto.setSourceData("����ȷ��");
			fiList.add(fiInterfaceProDto);
			
			if(oprOvermemo.getStatus()!=null&&oprOvermemo.getStatus()==1l){
				throw new ServiceException("������������ȷ�ϣ������ٳ�������ȷ��"); 
			}
			
						
			Set<OprOvermemoDetail> ovemSet =oprOvermemo.getOprOvermemoDetails();
			for(OprOvermemoDetail ovemDetail:ovemSet){
				List<OprStatus> osList = oprStatusDao.findBy("dno", ovemDetail.getDno());
				if(osList.size()==0){
					throw new ServiceException("����״̬���ݲ�����"); 
				}
				OprStatus osu=osList.get(0);
				if(osu.getDepartOvermemoStatus()!=null){
					if(osu.getDepartOvermemoStatus()==1l||osu.getDepartOvermemoStatus()==2l){
						throw new ServiceException("�����ѽ�����ɣ�������������ȷ��"); 
					}
				}
				osu.setDepartOvermemoStatus(0l);
				osu.setDepartOvermemoStartTime(null);
				oprStatusDao.save(osu);
				
				if(osu.getSignStatus()!=0l){
					throw new ServiceException("������ǩ�յĻ����������"); 
				}
				if(osu.getReturnStatus()!=0l){
					throw new ServiceException("�����ѷ��������ܳ�������ȷ��"); 
				}
				
				oprOvermemo.setRouteNumber(null);
				oprOvermemo.setUseCarType(null);
				oprOvermemo.setRentCarResult(null);
				oprOvermemo.setLockNo(null);
				oprOvermemo.setCarId(null);
				oprOvermemo.setCarCode(null);
				oprOvermemo.setStartTime(null);
				oprOvermemo.setStatus(7l); //ʵ���ĳ���״̬������Ƚ����⣬�����������
				save(oprOvermemo);
				
				oprHistoryService.saveLog(ovemDetail.getDno(),"���͵���Ϊ��"+ovemDetail.getDno()+"�Ļ��ﳷ������ȷ��", log_delOutCar);
			}
		}
		
		this.fiInterface.invalidInternalCostToFi(fiList);
		oprSignRouteService.cancelCarByRouteNumber(routeNumber);
		return null;
	}
	
public void insertEdiDataService(CarGoVo goVo,List<Long> ids) throws Exception {
		
	if(null==goVo || null==ids){
		throw new ServiceException("��������ֵ��");
	}
	for(Long oprOvermemoId :ids ){
		OprOvermemo oprOvermemo = get(oprOvermemoId);
		if(!edi_distributionMode.equals(oprOvermemo.getOvermemoType())){//���������ת�Ļ��������
			continue;
		}
		User user = WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
		for(OprOvermemoDetail detail :oprOvermemo.getOprOvermemoDetails()){
			CtTmD entity = new CtTmD();
			WSResult result=null;
			Long consignId =null;
			OprFaxIn faxIn = this.oprFacInDao.get(detail.getDno());
			List<ConsigneeInfo>  conList = this.consigneeInfoService.findBy("consigneeTel", faxIn.getConsigneeTel());
			if(null!=conList && conList.size()>0){
				consignId=conList.get(0).getId();
			}
			List<OprRequestDo> requestList = this.oprRequestDoService.findBy("dno", detail.getDno());
			String request ="";
			String signRequest ="";
			if(null!=requestList && requestList.size()>0){
				for (int j = 0; j < requestList.size(); j++) {
					OprRequestDo req = requestList.get(j);
					if("�ͻ�".equals(req.getRequestStage())){
						request+=req.getRequest()+"/";
					}else if("ǩ��".equals(req.getRequestStage())){
						signRequest+=req.getRequest()+"/";
					}
				}
			}
			Date dt = new Date();
			entity.setConsignId(consignId);
			entity.setCreateName(user.get("name")+"");
			entity.setCreateTime(dt);
			//entity.setCtDNo(ctDNo);
			try{
				entity.setCtId(Long.valueOf(this.customerService.get(faxIn.getGoWhereId()).getEdiUserId()));
			}catch (Exception e) {
				oprHistoryService.saveLog(Long.valueOf(entity.getDNo()), "��������д��EID��ʱ��ʧ�ܣ�û���ҵ�"+faxIn.getGowhere()+"��EDI�Խӱ���.",this.log_ediFailure );
				logger.error("�������⣬"+faxIn.getGowhere()+"��EDI�����ȡʧ�ܣ�"); 
				return;
			}
			entity.setTrId(oprOvermemoId+"");//��ת����
			entity.setCtName(faxIn.getGowhere());//��ת��˾
			entity.setCubage(faxIn.getBulk());
			entity.setCustomerServiceName(faxIn.getCustomerService());
			//entity.setDirverClockOutTime(dt);
			entity.setDriverClockInTime(dt);
//				entity.setDistributeTime(distributeTime);
			entity.setDnAmt(faxIn.getConsigneeFee());
			entity.setDnAmtChange(0d);
			entity.setDNo(detail.getDno()+"");
			entity.setDnside(faxIn.getWhoCash());//Ԥ������
			//entity.setDriverClockInTime(driverClockInTime);
			//entity.setEdiRemark(goVo.get);
			entity.setEndpayAmt(faxIn.getPaymentCollection());
			//entity.setEndpayAmtChange(endpayAmtChange);
			entity.setExceptionFlag("1");//1Ϊ��0Ϊ��
			entity.setFlyTime(faxIn.getFlightTime());
			entity.setGoods(faxIn.getGoods());
			entity.setGoodStatus("1");
			entity.setIsvaluables("0");
			entity.setOkFlag("0");
//				entity.setOkFlagCreatename(okFlagCreatename);
//				entity.setOkFlagCreatetime(okFlagCreatetime);
			entity.setPiece(detail.getPiece());
//				entity.setPrintTimes(printTimes);
			entity.setReceAdd(faxIn.getAddr());
			entity.setReceCorp("");
			entity.setReceMan(faxIn.getConsignee());
			//entity.setReceMob(faxIn.getConsigneeTel());
			entity.setReceTel(faxIn.getConsigneeTel());
			entity.setRequest(request);//�ͻ�Ҫ��
			entity.setRemark(faxIn.getRemark());
			//entity.setReturnVoucherFlag(returnVoucherFlag);
			entity.setShfNo(detail.getFlightNo());
			entity.setSignFlag("0");
			//entity.setSignType(signType);
			//entity.setSp(sp);
			entity.setStopflag("0");
			entity.setSustbillNo(detail.getSubNo());
			entity.setTakeMode(detail.getTakeMode().trim());
			entity.setTraAmt(faxIn.getTraFee());
//				entity.setTraAmtChange(traAmtChange);
			entity.setTraCost(faxIn.getTraFeeRate());
//				entity.setTrId(trId);
			entity.setWeight(detail.getWeight());
			entity.setYdNo(detail.getFlightMainNo());
			entity.setSignRequest(signRequest);//ǩ��Ҫ��
			entity.setSignType(faxIn.getReceiptType());//ǩ������
			entity.setDeptName(faxIn.getInDepartId()==457l?"����":"����");
			entity.setIsSp(faxIn.getSonderzug()==null?"0":faxIn.getSonderzug()+"");//�Ƿ�ר��
			entity.setIsurgent(faxIn.getUrgentService()==null?"0":faxIn.getUrgentService()+"");//�Ƿ�Ӽ�
			entity.setStatus(1l);//STATUS 1��������0������ʱ����Ҫɾ�����ж�����
			
			this.ctTmDService.save(entity);
//					
			}
			
		}
	}
}

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
 * 交接单主表服务层实现类
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
	private Long log_carArrive;//到车确认
	
	@Value("${oprOvermemoServiceImpl.log_carArriveReturn}")
	private Long log_carArriveReturn;//到车确认撤销
	
	@Value("${oprOvermemoServiceImpl.log_carUploadstart}")
	private Long log_carUploadstart;//卸车开始
	
	@Value("${oprOvermemoServiceImpl.log_carUploadend}")
	private Long log_carUploadend;//卸车结束
	
	@Value("${oprOvermemoServiceImpl.oprovermemoType}")
	private String oprOvermemoType;//交接单类型
	
	@Value("${oprOvermemoServiceImpl.log_delSureCargo}")
	private Long log_delSureCargo;//取消实配
	
	@Value("${oprOvermemoServiceImpl.log_carOut}")
	private Long log_carOut;//发车确认
	
	@Value("${oprReceiptServiceImpl.log_delReceiptOutStore}")
	private Long log_delReceiptOutStore ;  //回单出库
	
	@Value("${oprOvermemoServiceImpl.log_delOutCar}")
	private Long log_delOutCar ;  //撤销发车确认
	
	@Value("${oprFaxInServiceImpl.log_ediFailure}")
	private Long log_ediFailure;//中转EDI写入失败
	
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
	private IOprLoadingbrigadeWeightService iOprLoadingbrigadeWeightService;//装卸组货量
	
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
			//只能对已到车确认的做卸车开始操作
			if(oprOvermemo.getStatus()!=1){
				flag=false;
				break;
			}else{
				List<BasCar> bcList = basCarDao.findBy("carCode", oprOvermemo.getCarCode());
				if(bcList.size()>0){
					BasCar bc = bcList.get(0);
					bc.setCarStatus("卸车中");
					basCarDao.save(bc);
				}
				oprOvermemo.setStatus(Long.valueOf(2));
				oprOvermemo.setUnloadStartTime(new Date());
				this.save(oprOvermemo);
				for(OprOvermemoDetail ood:oprOvermemo.getOprOvermemoDetails()){
					OprFaxIn ofi = oprFaxInDao.getAndInitEntity(ood.getDno());
					if(ofi !=null && ofi.getStatus() == 1){
						oprHistoryService.saveLog(ood.getDno(),"配送单号为："+ood.getDno()+"的货物已经开始卸车", log_carUploadstart);
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
							oprHistoryService.saveLog(ood.getDno(),"配送单号为："+ood.getDno()+"的货物到车确认撤销", log_carArriveReturn);
						}
						
					}
					if("部门交接".equals(oo.getOvermemoType())){
						FiInterfaceProDto fpd = new FiInterfaceProDto();
						fpd.setSourceData("到车确认");
						fpd.setSourceNo(oo.getId());
						listfiInterfaceDto.add(fpd);
					}
				}
				//如果存在部门交接的货物，则需要撤销内部成本
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
			//获得库存区域
			Set<OprOvermemoDetail> set=oprOvermemo.getOprOvermemoDetails();
			List<OprStoreArea> list=oprStoreAreaDao.findBy("departId", Long.valueOf(user.get("bussDepart").toString()));
			for (OprOvermemoDetail overmemoDetail : set) {
				OprFaxIn oprFaxIn=oprFacInDao.getAndInitEntity(overmemoDetail.getDno());
				for (OprStoreArea oprStoreArea : list) {
					//如果全部匹配则设置库存区域
					if(oprFaxIn==null){
						overmemoDetail.setStorageArea("无传真");
					}else{
						overmemoDetail.setStorageArea(oprStoreAreaServiceImpl.getStockArea(oprStoreArea,oprFaxIn));
						List<OprStatus> osList = oprStatusDao.findBy("dno",oprFaxIn.getDno());                            // 回写状态表（到达仓库状态时间确认人）
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
				//回写状态表 是否到仓库，到仓库时间
				if(staList.size()>0){
					OprStatus oprStatus=staList.get(0);
					oprStatus.setDoStoreStatus(1L);
					oprStatus.setDoStoreTime(new Date());
					oprStatus.setDoStoreName(user.get("name").toString());
					oprStatusDao.save(oprStatus);
				}
				OprFaxIn ofi = oprFaxInDao.getAndInitEntity(overmemoDetail.getDno());
				if(ofi != null && ofi.getStatus() == 1){
					oprHistoryService.saveLog(overmemoDetail.getDno(),"配送单号为："+overmemoDetail.getDno()+"的货物到车确认", log_carArrive);
				}
				if("部门交接".equals(oprOvermemo.getOvermemoType())){
					//财务成本接口参数设置
					FiInterfaceProDto fiDto = new FiInterfaceProDto();
					fiDto.setStartDepartId(oprOvermemo.getStartDepartId());
					fiDto.setStartDepartName(oprOvermemo.getStartDepartName());
					fiDto.setEndDepart(oprOvermemo.getEndDepartName());
					fiDto.setEndDepartId(oprOvermemo.getEndDepartId());
					fiDto.setSourceData("到车确认");
					fiDto.setSourceNo(oprOvermemo.getId());//交接单主表单号，即实配单号
					fiDto.setDno(overmemoDetail.getDno());
					fiDto.setFlightPiece(overmemoDetail.getPiece());
					fiDto.setFlightWeight(overmemoDetail.getWeight());
					fiDto.setBulk(oprFaxIn.getBulk());
					fiDto.setCustomerId(overmemoDetail.getCusId());
					fiDto.setDistributionMode(oprFaxIn.getDistributionMode());
					
					String outTakeMode = oprFaxIn.getDistributionMode(); 
					if(oprFaxIn.getDistributionMode().equals("外发")){
						outTakeMode += oprFaxIn.getTakeMode().substring(2); 
					}else{
						String areaType = oprFaxIn.getAreaType().equals("市内")?oprFaxIn.getAreaType():"郊区";
						if(oprFaxIn.getTakeMode().indexOf("自提")>0){
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
			
//			if("部门交接".equals(oprOvermemo.getOvermemoType())){
//				if(oprOvermemo.getEndDepartId().equals(205l) || oprOvermemo.getEndDepartId().equals(240l)){
//					if(oprOvermemo.getStartDepartId().equals(205l) || oprOvermemo.getStartDepartId().equals(240l)){
//						//出库调用财务内部成本接口  
//						//1、部门交接 2、始发部门或者终端部门是广州和深圳才调用接口
//						//去掉部门限制 12-04-01
//						this.fiInterface.internalCostToFi(listfiInterfaceDto);
//					}
//				}
//			}
		}
		//如果存在部门交接的货物 则需要调用内部成本接口
		if(listfiInterfaceDto.size()>0){
			this.fiInterface.internalCostToFi(listfiInterfaceDto);
		}
		return true;
	}

	public boolean isCarUpload(Long routeNumber) throws Exception {
		boolean flag=true;
		List<OprOvermemo> overList=this.findOverByrouteNum(routeNumber);
		for(OprOvermemo oo:overList){
			//只能撤销已到车确认的数据
			if(oo.getStatus()!=1){
				flag=false;
        		break;
			}
			for(OprOvermemoDetail ood:oo.getOprOvermemoDetails()){
				if(ood.getStatus()==null){
					throw new ServiceException(ood.getId()+"交接单明细点到状态为空了");
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
			//只能对已发出的进行到车确认
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
			throw new ServiceException("此数据已打印车辆签单，不能多次打印");
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

	@ModuleName(value="发车确认保存",logType=LogType.buss)
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
		double votesPiece=0.0; //折合票数 
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
					throw new ServiceException("取不到货物状态数据");
				}
				list2.get(0).setDepartOvermemoStatus(0l);
				list2.get(0).setDepartOvermemoStartTime(date);
				oprStatusDao.save(list2.get(0));
				OprFaxIn oprFaxIn = oprFaxInDao.get(overmemoDetail.getDno());
				
				oprHistoryService.saveLog(overmemoDetail.getDno(),"发车确认 发车确认件数："+overmemoDetail.getRealPiece(), log_carOut);
				if(goVo.getCheckPrint()==1){
					if(overmemoDetail.getRealPiece()!=null){
						if(overmemoDetail.getRealPiece()-oprFaxIn.getPiece()>=0||oprFaxIn.getPiece()==null){
							votesPiece=DoubleUtil.add(oprSignRouteService.getVotesPiece(oprFaxIn.getCusWeight(), 200.00, 100.00,50.00),votesPiece); //计算折合票数
						}else{
							double weight=DoubleUtil.mul(oprFaxIn.getCusWeight(),(double)overmemoDetail.getRealPiece()/(double)oprFaxIn.getPiece());
							votesPiece=DoubleUtil.add(oprSignRouteService.getVotesPiece(weight, 200.00, 100.00,50.00),votesPiece);
						}
					}
				}
				
				if("部门交接".equals(oprOvermemo.getOvermemoType())){
					//财务成本接口参数设置
					FiInterfaceProDto fiDto = new FiInterfaceProDto();
					fiDto.setStartDepartId(oprOvermemo.getStartDepartId());
					fiDto.setStartDepartName(oprOvermemo.getStartDepartName());
					fiDto.setEndDepart(oprOvermemo.getEndDepartName());
					fiDto.setEndDepartId(oprOvermemo.getEndDepartId());
					fiDto.setSourceData("发车确认");
					fiDto.setSourceNo(oprOvermemo.getId());//交接单主表单号，即实配单号
					fiDto.setDno(overmemoDetail.getDno());
					fiDto.setFlightPiece(overmemoDetail.getPiece());
					fiDto.setFlightWeight(overmemoDetail.getWeight());
					fiDto.setBulk(oprFaxIn.getBulk());
					fiDto.setCustomerId(overmemoDetail.getCusId());
					fiDto.setDistributionMode(oprFaxIn.getDistributionMode());
					
					String outTakeMode = oprFaxIn.getDistributionMode(); 
					if(oprFaxIn.getDistributionMode().equals("外发")){
						outTakeMode += oprFaxIn.getTakeMode().substring(2); 
					}else{
						String areaType = oprFaxIn.getAreaType().equals("市内")?oprFaxIn.getAreaType():"郊区";
						if(oprFaxIn.getTakeMode().indexOf("自提")>0){
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
			if("部门交接".equals(oprOvermemo.getOvermemoType())){
					//出库调用财务内部成本接口  
					//1、部门交接 2、始发部门或者终端部门是广州和深圳才调用接口
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

	@ModuleName(value="取消实配",logType=LogType.buss)
	public void deleteOprOvermemo(List<Long> list,Long bussDepart,String overmemoType) throws Exception {
		User user= WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
		List<FiInterfaceProDto> listfiInterfaceDto =new ArrayList<FiInterfaceProDto>();
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		for(Long id:list){
			OprOvermemo ovem = oprOvermemoDao.get(id);
			if(ovem==null){
				throw new ServiceException("货物已撤销实配");
			}
			
			int size=0;
			if(!"部门交接".equals(overmemoType)){
				FiInterfaceProDto fiInterfaceProDto = new FiInterfaceProDto();  //非部门交接的货，调用财务接口
				fiInterfaceProDto.setSourceData("实配单");
				fiInterfaceProDto.setSourceNo(id);
				listfiInterfaceDto.add(fiInterfaceProDto);
				size=fiInterface.invalidToFi(listfiInterfaceDto);
			}
		
			
			for(OprOvermemoDetail overmemoDetail : ovem.getOprOvermemoDetails()){
				List<OprStock> lists = oprStockDao.find(" from OprStock o where o.dno=? and o.departId=? ",overmemoDetail.getDno(),bussDepart);
				OprStock oso = lists.get(0);
				if ("专车".equals(overmemoType)) {
					oso.setPiece(oso.getPiece()+overmemoDetail.getPiece());
				}else{
					oso.setPiece(oso.getPiece()+overmemoDetail.getRealPiece());
				}
				oprStockDao.save(oso);                                                  //修改库存
				
				OprFaxIn oprFaxIn = oprFaxInDao.get(overmemoDetail.getDno());
				oprFaxIn.setRealGoWhere("");
				oprFaxInDao.save(oprFaxIn);                                           //去向作废
				
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
						 receipt.setCurStatus("已入库");
						 this.oprHistoryService.saveLog(receipt.getDno(), user.get("userName")+"在"+sdf.format(dt)+"取消出库回单"+receipt.getReachNum()+"份", log_delReceiptOutStore);
						 this.oprReceiptService.save(receipt);                     //回单状态回滚
					 }
				}
				
				List<OprStatus> list2 =oprStatusDao.find(" from OprStatus o where o.dno=?  ",overmemoDetail.getDno());
			  	OprStatus osu= list2.get(0);
			  	if("部门交接".equals(overmemoType)){
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
			  	oprHistoryService.saveLog(overmemoDetail.getDno(),"取消实配，实配单号："+ovem.getId()+"，实配件数："+overmemoDetail.getRealPiece(), log_delSureCargo);
			}
			
			List<OprLoadingbrigadeWeight> loadingbrigadeList = this.oprLoadingbrigadeWeightService.findBy("overmemoNo", id);
			//删除装卸组货量表记录
			for (OprLoadingbrigadeWeight loadingbrigade:loadingbrigadeList){
				this.oprLoadingbrigadeWeightService.delete(loadingbrigade);
			}
			
			oprOvermemoDao.delete(id);
		}
	}
	/**
	 * 根据车次号查询交接单信息
	 * LiuH
	 */
	protected List<OprOvermemo> findOverByrouteNum(Long routeNum){
		return this.find("from OprOvermemo oo where oo.routeNumber=?", routeNum);
	}
	/**
	 * 获得车次号
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
		//如果车次号为空，则新增一个
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
		//如果是上门接货，则写入装卸组货量表
		if(oprOvermemo.getOvermemoType().equals("上门接货")){
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
			bc.setCarStatus("装车中");
			basCarDao.save(bc);
		}
		
	}

	/**
	 * 卸车结束
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
					oprHistoryService.saveLog(ood.getDno(),"配送单号为："+ood.getDno()+"的货物卸车结束，打印签单", log_carUploadend);
				}
			}
			List<BasCar> bcList = basCarDao.findBy("carCode", oo.getCarCode());
			if(bcList.size()>0){
				BasCar bc = bcList.get(0);
				bc.setCarStatus("空闲中");
				basCarDao.save(bc);
			}
		}
		return true;
	}

	/**
	 * 取消发车确认
	 * @return
	 */	
	public String cancelOvermemo(Long routeNumber) throws Exception {
		if(routeNumber==null){
			throw new ServiceException("提交的车次号不能为空"); 
		}
		
		List<OprOvermemo> oprOvemList=find("from OprOvermemo oo where oo.routeNumber=? ",routeNumber);
		if(oprOvemList.size()==0){
			throw new ServiceException("没有找到对应的实配数据"); 
		}
		
		//  invalidInternalCostToFi
		List<FiInterfaceProDto> fiList=new ArrayList<FiInterfaceProDto>();
		for(OprOvermemo oprOvermemo:oprOvemList){
			FiInterfaceProDto fiInterfaceProDto = new FiInterfaceProDto();
			fiInterfaceProDto.setSourceNo(oprOvermemo.getId());
			fiInterfaceProDto.setSourceData("发车确认");
			fiList.add(fiInterfaceProDto);
			
			if(oprOvermemo.getStatus()!=null&&oprOvermemo.getStatus()==1l){
				throw new ServiceException("车辆已做到车确认，不能再撤销发车确认"); 
			}
			
						
			Set<OprOvermemoDetail> ovemSet =oprOvermemo.getOprOvermemoDetails();
			for(OprOvermemoDetail ovemDetail:ovemSet){
				List<OprStatus> osList = oprStatusDao.findBy("dno", ovemDetail.getDno());
				if(osList.size()==0){
					throw new ServiceException("货物状态数据不存在"); 
				}
				OprStatus osu=osList.get(0);
				if(osu.getDepartOvermemoStatus()!=null){
					if(osu.getDepartOvermemoStatus()==1l||osu.getDepartOvermemoStatus()==2l){
						throw new ServiceException("货物已交接完成，不允许撤销发车确认"); 
					}
				}
				osu.setDepartOvermemoStatus(0l);
				osu.setDepartOvermemoStartTime(null);
				oprStatusDao.save(osu);
				
				if(osu.getSignStatus()!=0l){
					throw new ServiceException("存在已签收的货物，不允许撤销"); 
				}
				if(osu.getReturnStatus()!=0l){
					throw new ServiceException("货物已返货，不能撤销发车确认"); 
				}
				
				oprOvermemo.setRouteNumber(null);
				oprOvermemo.setUseCarType(null);
				oprOvermemo.setRentCarResult(null);
				oprOvermemo.setLockNo(null);
				oprOvermemo.setCarId(null);
				oprOvermemo.setCarCode(null);
				oprOvermemo.setStartTime(null);
				oprOvermemo.setStatus(7l); //实配后的车辆状态，这里比较特殊，必须是这个。
				save(oprOvermemo);
				
				oprHistoryService.saveLog(ovemDetail.getDno(),"配送单号为："+ovemDetail.getDno()+"的货物撤销发车确认", log_delOutCar);
			}
		}
		
		this.fiInterface.invalidInternalCostToFi(fiList);
		oprSignRouteService.cancelCarByRouteNumber(routeNumber);
		return null;
	}
	
public void insertEdiDataService(CarGoVo goVo,List<Long> ids) throws Exception {
		
	if(null==goVo || null==ids){
		throw new ServiceException("不允许传空值！");
	}
	for(Long oprOvermemoId :ids ){
		OprOvermemo oprOvermemo = get(oprOvermemoId);
		if(!edi_distributionMode.equals(oprOvermemo.getOvermemoType())){//如果不是中转的货物，则跳过
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
					if("送货".equals(req.getRequestStage())){
						request+=req.getRequest()+"/";
					}else if("签收".equals(req.getRequestStage())){
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
				oprHistoryService.saveLog(Long.valueOf(entity.getDNo()), "发车出库写入EID临时表失败，没有找到"+faxIn.getGowhere()+"的EDI对接编码.",this.log_ediFailure );
				logger.error("发车出库，"+faxIn.getGowhere()+"的EDI编码获取失败！"); 
				return;
			}
			entity.setTrId(oprOvermemoId+"");//中转单号
			entity.setCtName(faxIn.getGowhere());//中转公司
			entity.setCubage(faxIn.getBulk());
			entity.setCustomerServiceName(faxIn.getCustomerService());
			//entity.setDirverClockOutTime(dt);
			entity.setDriverClockInTime(dt);
//				entity.setDistributeTime(distributeTime);
			entity.setDnAmt(faxIn.getConsigneeFee());
			entity.setDnAmtChange(0d);
			entity.setDNo(detail.getDno()+"");
			entity.setDnside(faxIn.getWhoCash());//预付到付
			//entity.setDriverClockInTime(driverClockInTime);
			//entity.setEdiRemark(goVo.get);
			entity.setEndpayAmt(faxIn.getPaymentCollection());
			//entity.setEndpayAmtChange(endpayAmtChange);
			entity.setExceptionFlag("1");//1为否，0为是
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
			entity.setRequest(request);//送货要求
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
			entity.setSignRequest(signRequest);//签收要求
			entity.setSignType(faxIn.getReceiptType());//签单类型
			entity.setDeptName(faxIn.getInDepartId()==457l?"广州":"深圳");
			entity.setIsSp(faxIn.getSonderzug()==null?"0":faxIn.getSonderzug()+"");//是否专车
			entity.setIsurgent(faxIn.getUrgentService()==null?"0":faxIn.getUrgentService()+"");//是否加急
			entity.setStatus(1l);//STATUS 1：正常，0：保存时，需要删除的判断依据
			
			this.ctTmDService.save(entity);
//					
			}
			
		}
	}
}

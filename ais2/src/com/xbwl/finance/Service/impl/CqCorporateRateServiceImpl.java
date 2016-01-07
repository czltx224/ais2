package com.xbwl.finance.Service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.hibernate.Query;
import org.ralasafe.WebRalasafe;
import org.ralasafe.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.orm.Page;
import com.xbwl.common.orm.PropertyFilter;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.common.utils.ReadExcel;
import com.xbwl.common.utils.ReadExcel2007;
import com.xbwl.cus.vo.ReportSerarchVo;
import com.xbwl.entity.BasCqCorporateRate;
import com.xbwl.entity.BasCqStCorporateRate;
import com.xbwl.entity.ConsigneeDealPrice;
import com.xbwl.entity.Customer;
import com.xbwl.entity.SysDepart;
import com.xbwl.finance.Service.IBasProjCusTransitRateService;
import com.xbwl.finance.Service.IBasProjectRateService;
import com.xbwl.finance.Service.IBasTraShipmentRateService;
import com.xbwl.finance.Service.ICqCorporateRateService;
import com.xbwl.finance.Service.ICqStCorporateRateService;
import com.xbwl.finance.dao.ICqCorporateRateDao;
import com.xbwl.finance.dao.ICqStCorporateRateDao;
import com.xbwl.finance.vo.FaxrateSerarchVo;
import com.xbwl.rbac.Service.IDepartService;
import com.xbwl.sys.service.IBasAreaService;
import com.xbwl.sys.service.IConDealPriceService;
import com.xbwl.sys.service.ICustomerService;

/**
 * @author LiuHao
 * @time 2011-7-21 上午11:22:48
 */
@Service("cqCorporateRateServiceImpl")
@Transactional(rollbackFor = { Exception.class })
public class CqCorporateRateServiceImpl extends
		BaseServiceImpl<BasCqCorporateRate, Long> implements
		ICqCorporateRateService {
	@Resource(name = "cqCorporateRateHiberndateDaoImpl")
	private ICqCorporateRateDao cqCorporateRateDao;

	@Resource(name = "cqStCorporateRateHibernateDaoImpl")
	private ICqStCorporateRateDao cqStCorporateRateDao;
	@Resource(name="customerServiceImpl")
	private ICustomerService customerService;
	@Resource(name="basProjectRateServiceImpl")
	private IBasProjectRateService basProjectRateService;
	@Resource(name="cqStCorporateRateServiceImpl")
	private ICqStCorporateRateService cqStCorporateRateService;
	@Resource(name="conDealPriceServiceImpl")
	private IConDealPriceService conDealPriceService;
	@Resource(name="basTraShipmentRateServiceImpl")
	private IBasTraShipmentRateService basTraShipmentRateService;
	@Resource(name="basProjCusTransitRateServiceImpl")
	private IBasProjCusTransitRateService basProjCusTransitRateService;
	
	@Resource(name="basAreaServiceImpl")
	private IBasAreaService basAreaService;
	@Resource(name="departServiceImpl")
	private IDepartService departService;
	
	@Override
	public IBaseDAO<BasCqCorporateRate, Long> getBaseDao() {
		return cqCorporateRateDao;
	}

	@Override
	public void save(BasCqCorporateRate entity) {
		try {
        	if(allowSaveService(entity)){
        		//去掉折扣
	        	//Double discount=getDiscountService(entity);
	        	//if(null==discount || 0d==discount){
	        	//	throw new ServiceException("折扣计算失败！");
	        	//}else{
	        	entity.setDiscount(0d);
	        		if(null!=entity.getId() && entity.getId()>0){
	        			entity.setStatus(1l);//修改和把状态改为未审核
	        		}
	        		this.cqCorporateRateDao.save(entity);
	        	//}
        	}else{
        		throw new ServiceException("该客商已经存在这样的协议价！");
        	}
        } catch (Exception e) {
        	throw new ServiceException(e.getLocalizedMessage());
        }
	}



	public void updateStatusService(String[] ids, Long status)
			throws Exception {
		BasCqCorporateRate basCqCorporateRate = null;

		for (int i = 0; i < ids.length; i++) {
			basCqCorporateRate = this.cqCorporateRateDao.getAndInitEntity(Long.valueOf(ids[i]));
			basCqCorporateRate.setStatus(status);
			this.cqCorporateRateDao.save(basCqCorporateRate);
		}
	}

	public Double getDiscountService(BasCqCorporateRate basCqCorporateRate)
			throws ServiceException {
		Double discount = Double.valueOf(0);
		User user = WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
		Long bussDepartId = Long.parseLong(user.get("bussDepart") + "");
		Query query = this.cqStCorporateRateDao.createQuery("from BasCqStCorporateRate where addressType=? and takeMode=? and trafficMode=? and distributionMode=? and departId=? and status!=?",
				basCqCorporateRate.getAddressType(),basCqCorporateRate.getTakeMode(),basCqCorporateRate.getTrafficMode(),basCqCorporateRate.getDistributionMode(),bussDepartId,0l);
		
		List<BasCqStCorporateRate> list=query.list();
		BasCqStCorporateRate entity = null;

		for (int i = 0; i < list.size(); i++) {
			entity = list.get(i);
			
			if(entity.getStatus()!=2){
				throw new ServiceException("该标准协议价未审核！");
			}

			discount = this.getDiscount(basCqCorporateRate, entity);
		}
			
		return discount;
	}

	private Double getDiscount(BasCqCorporateRate basCqCorporateRate,
			BasCqStCorporateRate entity) {
		Double lowPrice = Double.valueOf(0);
		Double stage1Rate = Double.valueOf(0);
		Double stage2Rate = Double.valueOf(0);
		Double stage3Rate = Double.valueOf(0);

		if (null != basCqCorporateRate.getLowPrice()) {
			lowPrice = basCqCorporateRate.getLowPrice() / entity.getLowPrice();
		}
		if (null != basCqCorporateRate.getStage1Rate()) {
			stage1Rate = basCqCorporateRate.getStage1Rate()
					/ entity.getStage1Rate();
		}
		if (null != basCqCorporateRate.getStage2Rate()) {
			stage2Rate = basCqCorporateRate.getStage2Rate()
					/ entity.getStage2Rate();
		}
		if (null != basCqCorporateRate.getStage3Rate()) {
			stage3Rate = basCqCorporateRate.getStage3Rate()
					/ entity.getStage3Rate();
		}

		double[] discounts = new double[] { lowPrice, stage1Rate, stage2Rate,
				stage3Rate };

		Arrays.sort(discounts);
		for (int i = 0; i < discounts.length; i++) {
			//FIXED 将计算和比较进行合并
			if (discounts[i] > Double.valueOf(0.0)) {
				return  Double.valueOf(Math.round(discounts[i] * 100)) / 100;
			}
		}
		return null;
	}

	public Page<BasCqCorporateRate> findCqCorRate(Page page,
			String trafficMode, String takeMode, String distributionMode,
			String addressType, String startCity, String city, String town,
			String street, String valuationType,Long cusId,Long departId) throws Exception {
		Page<BasCqCorporateRate> page1=null;
		page1=this.findPage(page, "from BasCqCorporateRate bccr where bccr.trafficMode=? and bccr.takeMode=? and bccr.distributionMode=? and bccr.addressType=? and bccr.status=2 and bccr.startDate<=sysdate and bccr.endDate>=sysdate and bccr.startAddr=? and bccr.endAddr=?  and bccr.valuationType=? and bccr.cusId=? and bccr.departId=?", trafficMode,takeMode,distributionMode,addressType,startCity,street,valuationType,cusId,departId);
		if(page1.getResult().size()<1){
			page1=this.findPage(page, "from BasCqCorporateRate bccr where bccr.trafficMode=? and bccr.takeMode=? and bccr.distributionMode=? and bccr.addressType=? and bccr.status=2 and bccr.startDate<=sysdate and bccr.endDate>=sysdate and bccr.startAddr=? and bccr.endAddr=?  and bccr.valuationType=? and bccr.cusId=? and bccr.departId=?", trafficMode,takeMode,distributionMode,addressType,startCity,town,valuationType,cusId,departId);
			if(page1.getResult().size()<1){
				page1=this.findPage(page, "from BasCqCorporateRate bccr where bccr.trafficMode=? and bccr.takeMode=? and bccr.distributionMode=? and bccr.addressType=? and bccr.status=2 and bccr.startDate<=sysdate and bccr.endDate>=sysdate and bccr.startAddr=? and bccr.endAddr=?  and bccr.valuationType=? and bccr.cusId=? and bccr.departId=?", trafficMode,takeMode,distributionMode,addressType,startCity,city,valuationType,cusId,departId);
				if(page1.getResult().size()<1){
					page1=this.findPage(page, "from BasCqCorporateRate bccr where bccr.trafficMode=? and bccr.takeMode=? and bccr.distributionMode=? and bccr.addressType=? and bccr.status=2 and bccr.startDate<=sysdate and bccr.endDate>=sysdate and bccr.startAddr=? and bccr.valuationType=? and bccr.cusId=? and bccr.departId=? and bccr.endAddr is null", trafficMode,takeMode,distributionMode,addressType,startCity,valuationType,cusId,departId);
					//该代理没有该目的站的协议价
					if(page1.getResult().size()<1){
						Map map= new HashMap();
						map.put("cusId", cusId);
						map.put("departId", departId);
						map.put("trafficMode", trafficMode);
						map.put("takeMode", takeMode);
						map.put("distributionMode", distributionMode);
						map.put("addressType", addressType);
						map.put("startCity", startCity);
						map.put("valuationType", valuationType);
						
						StringBuffer sql= new StringBuffer();
						sql.append(" from BasCqCorporateRate t");
						sql.append(" where t.cusId = :cusId");
						sql.append(" and t.startDate >= sysdate");
						sql.append(" and t.endDate <= sysdate+1");
						sql.append(" and t.trafficMode=:trafficMode");
						sql.append(" and t.distributionMode=:distributionMode");
						sql.append("  and t.takeMode=:takeMode");
						sql.append(" and t.status=2");
						sql.append(" and t.addressType=:addressType");
						sql.append("  and t.valuationType=:valuationType");
						sql.append(" and t.departId=:departId");
						sql.append("  and t.startAddr=:startCity");
						
						page1 = this.findPage(page, sql.toString(), map);
					}
				}
			}
		}
		return page1;
	}

	public boolean allowSaveService(BasCqCorporateRate entity) {

		User user = WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
		Long bussDepartId = Long.parseLong(user.get("bussDepart") + "");
		entity.setDepartId(bussDepartId);
		
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		PropertyFilter filter = new PropertyFilter("NEL_status","0");
		filters.add(filter);
		if(null!=entity.getId() && entity.getId()>0){
			filter = new PropertyFilter("NEL_id",entity.getId()+"");
			filters.add(filter);
		}
		if(null!=entity.getDistributionMode() && !"".equals(entity.getDistributionMode())){
			filter = new PropertyFilter("EQS_distributionMode",entity.getDistributionMode());
			filters.add(filter);
		}
		if(null!=entity.getCusName() && !"".equals(entity.getCusName())){
			filter = new PropertyFilter("EQS_cusName",entity.getCusName());
			filters.add(filter);
		}
		if(null!=entity.getCusId() && !"".equals(entity.getCusId())){
			filter = new PropertyFilter("EQL_cusId",entity.getCusId()+"");
			filters.add(filter);
		}
		if(null!=entity.getTakeMode() && !"".equals(entity.getTakeMode())){
			filter = new PropertyFilter("EQS_takeMode",entity.getTakeMode());
			filters.add(filter);
		}
		if(null!=entity.getTrafficMode() && !"".equals(entity.getTrafficMode())){
			filter = new PropertyFilter("EQS_trafficMode",entity.getTrafficMode());
			filters.add(filter);
		}
		if(null!=entity.getAddressType() && !"".equals(entity.getAddressType())){
			filter = new PropertyFilter("EQS_addressType",entity.getAddressType());
			filters.add(filter);
		}
		if(null!=entity.getValuationType() && !"".equals(entity.getValuationType())){
			filter = new PropertyFilter("EQS_valuationType",entity.getValuationType());
			filters.add(filter);
		}
		if(null!=entity.getStartAddr() && !"".equals(entity.getStartAddr())){
			filter = new PropertyFilter("EQS_startAddr",entity.getStartAddr());
			filters.add(filter);
		}
		if(null!=entity.getDepartId() && !"".equals(entity.getDepartId())){
			filter = new PropertyFilter("EQL_departId",entity.getDepartId()+"");
			filters.add(filter);
		}
		if(null!=entity.getEndAddr() && !"".equals(entity.getEndAddr())){
			filter = new PropertyFilter("EQS_endAddr",entity.getEndAddr());
			filters.add(filter);
		}else{
			filter = new PropertyFilter("NULLS_endAddr",entity.getEndAddr()); 
			filters.add(filter);
		}
		List<BasCqCorporateRate> list = this.cqCorporateRateDao.find(filters);
		
		if(null!=list && list.size()>0){
			return false;
		}else{
			return true;
		}
	}

	public Page findRate(Page page, FaxrateSerarchVo rateSearchVo)
			throws Exception {
		User user = WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
		String whoCash = rateSearchVo.getWhoCash();
		Long cusId = rateSearchVo.getCusId();
		String cusName = rateSearchVo.getCusName();
		Long piece = rateSearchVo.getPiece();
		Double weight = rateSearchVo.getCusWeight();
		String city = rateSearchVo.getCity();
		String town = rateSearchVo.getTown();
		String street = rateSearchVo.getStreet();
		Double bulk = rateSearchVo.getBulk();
		Long departId = Long.valueOf(user.get("bussDepart")+"");
		String trafficMode = rateSearchVo.getTrafficMode();
		String takeMode = rateSearchVo.getTakeMode();
		String distributionMode = rateSearchVo.getDistributionMode();
		String addressType = rateSearchVo.getAddrType();
		String startCity = rateSearchVo.getStartCity();
		String valuationType = rateSearchVo.getValuationType();
		String[] tels = rateSearchVo.getConsigneeTel();
		Long disDepartId = rateSearchVo.getDisDepartId();
		Long cpId = rateSearchVo.getCpId();
		String cpName = rateSearchVo.getCpName();
		
		Page returnPage = new Page(1);
		returnPage.setPageNo(1);
		returnPage.setLimit(20);
		returnPage.setStart(0);
		
		Map resultMap = new HashMap();
		List resultList = new ArrayList(0);
		Customer cus = customerService.getAndInitEntity(cusId);
		if(whoCash.equals("预付") || whoCash.equals("双方付")){
			//项目客户
			if(cus.getIsProjectcustomer() == 1){
				Page projectPage = basProjectRateService.findProRate(page, piece, weight, bulk, cusId, city,town);
				if(projectPage.getResultMap().size()>0){
					Map proCpMap = (Map)projectPage.getResultMap().get(0);
					String valua = proCpMap.get("COUNT_WAY").toString();
					Double rate = Double.valueOf(proCpMap.get("RATE").toString());
					Double lowFee = Double.valueOf(proCpMap.get("LOW_FEE").toString());
					Object add = proCpMap.get("ADD_FEE");
					Double add_fee = 0.0;
					if(add !=null && !"".equals(add)){
						add_fee = Double.valueOf(add.toString());
					}
					
					Double pprice=0.0;
					if(valua.equals("件数")){
						pprice= piece*rate;
					}else if(valua.equals("重量")){
						pprice=weight*rate;
					}
					else if(valua.equals("体积")){
						pprice=bulk*rate;
					}
					if(pprice<lowFee){
						pprice=lowFee;
					}
					pprice = Math.round(pprice+add_fee)+0.0;
					resultMap.put("cpProRate", rate);
					resultMap.put("cpProFee", pprice);
				}
			}else{
				Page cqRatePage = this.findCqCorRate(page, trafficMode, takeMode, distributionMode, addressType, startCity, city, town, street, valuationType, cusId, departId);
				//该代理无协议价
				if(cqRatePage.getResult().size()<1){
					//取公布价
					Page cqStPage = cqStCorporateRateService.findCqStCorRate(page, trafficMode, takeMode, distributionMode, addressType, startCity, city, town, street, departId);
					if(cqStPage.getResult().size()>0){
						Map map = this.getStRate(weight, cqStPage.getResult().get(0));
						resultMap.put("stRate", map.get("stRate"));
						resultMap.put("stFee", map.get("stFee"));
					}
				}else{
					Map map = this.getValRate(weight, bulk, piece, "cpFee","cpRate",cqRatePage.getResult().get(0));
					resultMap.put("cpRate", map.get("cpRate"));
					resultMap.put("cpFee", map.get("cpFee"));
				}
			}
			
		}else{
			//到付
			Page conRatePage = conDealPriceService.findConDealPrice(page,cpName, tels);
			if(conRatePage.getResult().size()<1){
				if(cus.getIsProjectcustomer() == 1){
					Page projectPage = basProjectRateService.findProRate(page, piece, weight, bulk, cusId, city,town);
					if(projectPage.getResultMap().size()>0){
						Map proCpMap = (Map)projectPage.getResultMap().get(0);
						String valua = proCpMap.get("COUNT_WAY").toString();
						Double rate = Double.valueOf(proCpMap.get("RATE").toString());
						Double lowFee = Double.valueOf(proCpMap.get("LOW_FEE").toString());
						
						Object add = proCpMap.get("ADD_FEE");
						Double add_fee = 0.0;
						if(add !=null && !"".equals(add)){
							add_fee = Double.valueOf(add.toString());
						}
						
						Double pprice=0.0;
						if(valua.equals("件数")){
							pprice= piece*rate;
						}else if(valua.equals("重量")){
							pprice=weight*rate;
						}
						else if(valua.equals("体积")){
							pprice=bulk*rate;
						}
						if(pprice<lowFee){
							pprice=lowFee;
						}
						pprice = Math.round(pprice+add_fee)+0.0;
						resultMap.put("cpProRate", rate);
						resultMap.put("cpProFee", pprice);
					}else{
						//取公布价
						Page cqStPage = cqStCorporateRateService.findCqStCorRate(page, trafficMode, takeMode, distributionMode, addressType, startCity, city, town, street, departId);
						if(cqStPage.getResult().size()>0){
							Map map = this.getStRate(weight, cqStPage.getResult().get(0));
							resultMap.put("stRate", map.get("stRate"));
							resultMap.put("stFee", map.get("stFee"));
						}
					}
				}else{
					//取公布价
					Page cqStPage = cqStCorporateRateService.findCqStCorRate(page, trafficMode, takeMode, distributionMode, addressType, startCity, city, town, street, departId);
					if(cqStPage.getResult().size()>0){
						Map map = this.getStRate(weight, cqStPage.getResult().get(0));
						resultMap.put("stRate", map.get("stRate"));
						resultMap.put("stFee", map.get("stFee"));
					}
				}
				
			}else{
				ConsigneeDealPrice cdp =(ConsigneeDealPrice)conRatePage.getResult().get(0);
				Double citySend = cdp.getCitySendPrice();
				Double citySendMin = cdp.getCitySendMinPrice();
				Double flyOwn = cdp.getFlyOwnPrice();
				Double flyOwnMin = cdp.getFlyOwnMinPrice();
				Double cityOwn = cdp.getCityOwnPrice();
				Double cityOwnMin = cdp.getCityOwnMinPrice();
				
				Double conRate = 0.0;
				Double conFee = 0.0;
				
				if(takeMode.equals("机场自提")){
					conRate = flyOwn;
					conFee = flyOwn*weight;
					if(conFee < flyOwnMin){
						conFee = flyOwnMin;
					}
				}else if(takeMode.equals("市内自提")){
					conRate = cityOwn;
					conFee = cityOwn*weight;
					if(conFee < cityOwnMin){
						conFee = cityOwnMin;
					}
				}else if(takeMode.equals("市内送货")){
					conRate = citySend;
					conFee = citySend*weight;
					if(conFee < citySendMin){
						conFee = citySendMin;
					}
				}
				
				conFee = Math.round(conFee)+0.0;
				
				resultMap.put("conRate", conRate);
				resultMap.put("conFee", conFee);
			}
			//中转
//			if(distributionMode.equals("中转")){
//				Page traPage = basTraShipmentRateService.findTraRate(page, cusName, trafficMode, takeMode, addressType, disDepartId, valuationType);
//				if(traPage.getResultMap().size()>0){
//					Map map = this.getValRate(weight, bulk, piece, "traFee", "traRate", traPage.getResultMap().get(0));
//					resultMap.put("traRate", map.get("traRate"));
//					resultMap.put("traFee", map.get("traFee"));
//				}
//			}
		}
		//中转
		if(distributionMode.equals("中转")){
			if(cus.getIsProjectcustomer()==1){
				Page protraPage = basProjCusTransitRateService.findProTraRate(page, piece, weight, bulk, cusId, cpId, addressType, takeMode, trafficMode, disDepartId,town);
				if(protraPage.getResultMap().size()<1){
					protraPage = basProjCusTransitRateService.findProTraRate(page, piece, weight, bulk, cusId, null, addressType, takeMode, trafficMode, disDepartId,town);
				}
				if(protraPage.getResultMap().size()>0){
					Map protramap = (Map)protraPage.getResultMap().get(0);
					String valua = protramap.get("VALUATION_TYPE").toString();
					Double rate = Double.valueOf(protramap.get("RATE").toString());
					Double lowFee = Double.valueOf(protramap.get("LOW_FEE").toString());
					
					Double pprice=0.0;
					if(valua.equals("件数")){
						pprice= piece*rate;
					}else if(valua.equals("重量")){
						pprice=weight*rate;
					}
					else if(valua.equals("体积")){
						pprice=bulk*rate;
					}
					if(pprice<lowFee){
						pprice=lowFee;
					}
					pprice = Math.round(pprice)+0.0;
					resultMap.put("traProRate", rate);
					resultMap.put("traProFee", pprice);
				}else{
					Page traPage = basTraShipmentRateService.findTraRate(page, cusName, trafficMode, takeMode, addressType, disDepartId, valuationType,town);
					if(traPage.getResultMap().size()>0){
						Map map = this.getValRate(weight, bulk, piece, "traFee", "traRate", traPage.getResultMap().get(0));
						resultMap.put("traRate", map.get("traRate"));
						resultMap.put("traFee", map.get("traFee"));
					}
				}
			}else{
				Page traPage = basTraShipmentRateService.findTraRate(page, cusName, trafficMode, takeMode, addressType, disDepartId, valuationType,town);
				if(traPage.getResultMap().size()>0){
					Map map = this.getValRate(weight, bulk, piece, "traFee", "traRate", traPage.getResultMap().get(0));
					resultMap.put("traRate", map.get("traRate"));
					resultMap.put("traFee", map.get("traFee"));
				}
			}
		}
		
		//获得标准价格
		Object conRate = resultMap.get("conRate");
		Object cpRate = resultMap.get("cpRate");
		Object cpProRate = resultMap.get("cpProRate");
		Object traProRate = resultMap.get("traProRate");
		
		if((conRate!=null && !"".equals(conRate)) || (cpRate!=null && !"".equals(cpRate)) || (cpProRate!=null && !"".equals(cpProRate))){
			Object stRate = resultMap.get("stRate");
			if(stRate == null || stRate.equals("")){
				Page cqStPage = cqStCorporateRateService.findCqStCorRate(page, trafficMode, takeMode, distributionMode, addressType, startCity, city, town, street, departId);
				if(cqStPage.getResult().size()>0){
					Map map = this.getStRate(weight, cqStPage.getResult().get(0));
					resultMap.put("stRate", map.get("stRate"));
					resultMap.put("stFee", map.get("stFee"));
				}
			}
		}
		
		
		
		resultList.add(resultMap);
		returnPage.setResultMap(resultList);
		return returnPage;
	}
	/**
	 * 获得存在计价方式的价格和费率
	 * @author LiuHao
	 * @time Apr 27, 2012 11:57:19 AM 
	 * @param weight
	 * @param bulk
	 * @param piece
	 * @param feeType
	 * @param rateType
	 * @param rateObj
	 * @return
	 */
	private Map getValRate(Double weight,Double bulk,Long piece,String feeType,String rateType,Object rateObj){
		String valu="";
		Double sta1Rate=0.0;
		Double sta2Rate=0.0;
		Double sta3Rate=0.0;
		Double lowFee=0.0;
		
		Map map=new HashMap();
		Double pprice=0.0;
		Double rate = 0.0;
		
		if(feeType.equals("cpFee")){
			BasCqCorporateRate bccr = (BasCqCorporateRate)rateObj;
			valu = bccr.getValuationType();
			sta1Rate = bccr.getStage1Rate();
			sta2Rate = bccr.getStage2Rate();
			sta3Rate = bccr.getStage3Rate();
			lowFee = bccr.getLowPrice();
		}else if(feeType.equals("traFee")){
			Map tramap = (Map)rateObj;
			valu = tramap.get("VALUATION_TYPE").toString();
			sta1Rate = Double.valueOf(tramap.get("STAGE1_RATE").toString());
			sta2Rate = Double.valueOf(tramap.get("STAGE2_RATE").toString());
			sta3Rate = Double.valueOf(tramap.get("STAGE3_RATE").toString());
			lowFee = Double.valueOf(tramap.get("LOW_PRICE").toString());
		}
		if(valu.equals("重量")){
			if(weight>0 && weight<=500){
				pprice = weight*sta1Rate;
				rate = sta1Rate;
			}else if(weight>500 && weight<=1000){
				pprice = weight*sta2Rate;
				rate = sta2Rate;
			}else if(weight> 1000){
				pprice = weight*sta3Rate;
				rate = sta3Rate;
			}
		}else if(valu.equals("件数")){
			pprice = piece*sta1Rate;
			rate = sta1Rate;
		}else if(valu.equals("体积")){
			pprice = bulk*sta1Rate;
			rate = sta1Rate;
		}
		
		if(pprice<lowFee){
			pprice=lowFee;
		}
		pprice = Math.round(pprice)+0.0;
		map.put(rateType, rate);
		map.put(feeType, pprice);
		return map;
	}
	/**
	 * 获得公布价和费率
	 * @author LiuHao
	 * @time Apr 27, 2012 11:57:00 AM 
	 * @return
	 */
	private Map getStRate(Double weight,Object rateObj){
		Map map=new HashMap();
		Double pprice=0.0;
		Double rate = 0.0;
		
		Double sta1Rate=0.0;
		Double sta2Rate=0.0;
		Double sta3Rate=0.0;
		Double lowFee=0.0;
		BasCqStCorporateRate bcsr = (BasCqStCorporateRate)rateObj;
		sta1Rate = bcsr.getStage1Rate();
		sta2Rate = bcsr.getStage2Rate();
		sta3Rate = bcsr.getStage3Rate();
		lowFee = bcsr.getLowPrice();
		if(weight>0 && weight<=500){
			pprice = weight*sta1Rate;
			rate = sta1Rate;
		}else if(weight>500 && weight<=1000){
			pprice = weight*sta2Rate;
			rate = sta2Rate;
		}else if(weight> 1000){
			pprice = weight*sta3Rate;
			rate = sta3Rate;
		}
		if(pprice<lowFee){
			pprice=lowFee;
		}
		pprice = Math.round(pprice)+0.0;
		map.put("stRate", rate);
		map.put("stFee", pprice);
		return map;
	} 
	
	public void saveExcelOfExcel(File excelFile, String fileName) throws Exception {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		List<BasCqCorporateRate> fiList = new ArrayList<BasCqCorporateRate>();
		List list=null;
		if(fileName.toLowerCase().endsWith(".xlsx")){
				ReadExcel2007 readExcel2007 = new ReadExcel2007(16);
				FileInputStream fint = new FileInputStream(excelFile);
				try {
					list = readExcel2007.readExcel2007(fint);
				} catch (Exception e) {
						throw new ServiceException("读取数据错误，存在非法值");
				}finally{
					if (fint!=null) {
						fint.close();
						fint=null;
					}
				}
		}else if(fileName.toLowerCase().endsWith(".xls")){
				ReadExcel readExcel = new ReadExcel(16);
				FileInputStream fint = new FileInputStream(excelFile);
				try {
					list = readExcel.readExcel(fint);
				} catch (Exception e) {
						throw new ServiceException("读取数据错误，存在非法值");
				}finally{
					if (fint!=null) {
						fint.close();
						fint=null;
					}
				}
		}else{
			throw new ServiceException("请导入Excel文件，后缀为.xlsx或者.xls");
		}
		
		for(int i=0;i<list.size();i++){
			List row=(List)list.get(i);
			if(i!=0){
				BasCqCorporateRate basCqRate =  new BasCqCorporateRate();
				Iterator jt=row.iterator();
				jt.hasNext();
				 jt.next();
				String company=(String) jt.next();
				if((!"".equals(company))&&company!=null){
					List<Customer> listCus =customerService.findBy("cusName", company);
					if(listCus.size()==0||listCus.size()>2){
						throw new ServiceException("第<"+(i+1)+">行客商<"+company+">在客商基础资料里面找不到");
					}else{
						basCqRate.setCusId(listCus.get(0).getId());
						basCqRate.setCusName(company);
					}
				}else{
					throw new ServiceException("第<"+(i+1)+">行客商名称不能为空");
				}
				
				String startDateString=(String) jt.next();
				if(startDateString!=null&&df.parse(startDateString)!=null){
					basCqRate.setStartDate(df.parse(startDateString));
				}
				String endDateString=(String) jt.next();
				if(endDateString!=null&&df.parse(endDateString)!=null){
					basCqRate.setEndDate(df.parse(endDateString));
				}
				
				String tranType =(String)jt.next();
				if("空运".equals(tranType)||"汽运".equals(tranType)){
					basCqRate.setTrafficMode(tranType);
				}else{
					throw new ServiceException("第<"+(i+1)+">行不存这样的运输方式");
				}
				
				String dispachName =(String)jt.next();
				if("中转".equals(dispachName)||"新邦".equals(dispachName)||"外发".equals(dispachName)){
					basCqRate.setDistributionMode(dispachName);
				}else{
					throw new ServiceException("第<"+(i+1)+">行不存这样的配送方式");
				}
				
				String diGoodsType =(String)jt.next();
				if("机场自提".equals(diGoodsType)||"市内送货".equals(diGoodsType)||"市内自提".equals(diGoodsType)){
					basCqRate.setTakeMode(diGoodsType);
				}else{
					throw new ServiceException("第<"+(i+1)+">行不存这样的配送方式");
				}
				
				String lowPrice =(String)jt.next();
				basCqRate.setLowPrice(Double.valueOf(lowPrice==null?"0.0":lowPrice));

				String stage1Rate =(String)jt.next();
				basCqRate.setStage1Rate(Double.valueOf(stage1Rate==null?"0.0":stage1Rate));
				String stage2Rate =(String)jt.next();
				basCqRate.setStage2Rate(Double.valueOf(stage2Rate==null?"0.0":stage2Rate));
				String stage3Rate =(String)jt.next();
				basCqRate.setStage3Rate(Double.valueOf(stage3Rate==null?"0.0":stage3Rate));
				
				String discount =(String)jt.next();
				basCqRate.setDiscount(Double.valueOf(discount==null?"0.0":discount));
				
				String addrType =(String)jt.next();
				if("市内".equals(addrType)||"近郊".equals(addrType)||"远郊".equals(addrType)||"港澳".equals(addrType)||"关外".equals(addrType)){
					basCqRate.setAddressType(addrType);
				}else{
					throw new ServiceException("第<"+(i+1)+">行不存这样的地址类型");
				}
				
				String valuationType =(String)jt.next();
				if("件数".equals(valuationType)||"重量".equals(valuationType)||"体积".equals(addrType)){
					basCqRate.setValuationType(valuationType);
				}else{
					throw new ServiceException("第<"+(i+1)+">行不存这样的计价方式");
				}
				
				String startAddr =(String)jt.next();
				if(startAddr!=null&&(!"".equals(startAddr))){
					if(basAreaService.isBasAreaExistOfString(startAddr)){
						basCqRate.setStartAddr(startAddr);
					}else{
						throw new ServiceException("第<"+(i+1)+">行开始地址在地区表找不到");
					}
				}else{
					throw new ServiceException("第<"+(i+1)+">行开始地址不能为空");
				}
				String endAddr =(String)jt.next();
				if(endAddr!=null&&(!"".equals(endAddr))){
					if(basAreaService.isBasAreaExistOfString(endAddr)){
						basCqRate.setEndAddr(endAddr);
					}else{
						throw new ServiceException("第<"+(i+1)+">行结束地址在地区表找不到");
					}
				}
				
				basCqRate.setStatus(1l);
				fiList.add(basCqRate);
			}
		}
		
		for(BasCqCorporateRate basCqRate : fiList){
			save(basCqRate);
		}
	}
}


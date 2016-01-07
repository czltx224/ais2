package com.xbwl.finance.Service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
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
import com.xbwl.entity.BasTraShipmentRate;
import com.xbwl.entity.Customer;
import com.xbwl.finance.Service.IBasTraShipmentRateService;
import com.xbwl.finance.dao.IBasTraShipmentRateDao;
import com.xbwl.sys.service.IBasAreaService;
import com.xbwl.sys.service.ICustomerService;

/**
 * @author CaoZhili time Aug 4, 2011 10:23:04 AM
 * 
 * 中转协议价服务层实现类
 */
@Service("basTraShipmentRateServiceImpl")
@Transactional(rollbackFor = { Exception.class })
public class BasTraShipmentRateServiceImpl extends
		BaseServiceImpl<BasTraShipmentRate, Long> implements
		IBasTraShipmentRateService {

	@Resource(name = "basTraShipmentRateHibernateDaoImpl")
	private IBasTraShipmentRateDao basTraShipmentRateDao;

	@Resource(name="basAreaServiceImpl")
	private IBasAreaService basAreaService;
	
	@Resource(name="customerServiceImpl")
	private ICustomerService customerService;
	
	@Override
	public IBaseDAO<BasTraShipmentRate, Long> getBaseDao() {

		return this.basTraShipmentRateDao;
	}

	@Override
	public void save(BasTraShipmentRate entity) {
		try {
        	if(allowSaveService(entity)){
        		if(null!=entity.getId() && entity.getId()>0){
        			entity.setStatus(1l);//修改和把状态改为未审核
        		}
		        this.basTraShipmentRateDao.save(entity);
        	}else{
        		throw new ServiceException("该中转协议价已经存在！");
        	}
        } catch (Exception e) {
        	throw new ServiceException(e.getLocalizedMessage());
        }
	}



	public void updateStatusService(String[] ids, Long status)
			throws Exception {
		BasTraShipmentRate entity = null;
		for (int i = 0; i < ids.length; i++) {
			entity = this.basTraShipmentRateDao.getAndInitEntity(Long.valueOf(ids[i]));
			entity.setStatus(status);
			this.basTraShipmentRateDao.save(entity);
		}
	}

	public boolean allowSaveService(BasTraShipmentRate entity) {
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
		if(null!=entity.getValuationType() && !"".equals(entity.getValuationType())){
			filter = new PropertyFilter("EQS_valuationType",entity.getValuationType());
			filters.add(filter);
		}
		if(null!=entity.getAreaType() && !"".equals(entity.getAreaType())){
			filter = new PropertyFilter("EQS_areaType",entity.getAreaType());
			filters.add(filter);
		}
		if(null!=entity.getDepartId() && !"".equals(entity.getDepartId())){
			filter = new PropertyFilter("EQL_departId",entity.getDepartId()+"");
			filters.add(filter);
		}
		if(null!=entity.getIsNotProject() && !"".equals(entity.getIsNotProject())){
			filter = new PropertyFilter("EQL_isNotProject",entity.getIsNotProject()+"");
			filters.add(filter);
			
			if(null!=entity.getProjectCusId() && !"".equals(entity.getProjectCusId())){
				filter = new PropertyFilter("EQL_projectCusId",entity.getProjectCusId()+"");
				filters.add(filter);
			}
		}
		if(null != entity.getSpeTown() && !"".equals(entity.getSpeTown())){
			filter = new PropertyFilter("EQS_speTown",entity.getSpeTown());
			filters.add(filter);
		}
		List<BasTraShipmentRate> list = this.basTraShipmentRateDao.find(filters);
		
		if(null!=list && list.size()>0){
			return false;
		}else{
			return true;
		}
	}

	public Page findTraRate(Page page, String cusName, String trafficMode,
			String takeMode, String addrType, Long disdepartId,
			String valuationType,String speTown) throws Exception {
		Map map = new HashMap();
		map.put("cusName", cusName);
		map.put("trafficMode", trafficMode);
		map.put("takeMode", takeMode);
		map.put("disdepartId", disdepartId);
		map.put("valuationType", valuationType);
		map.put("speTown", speTown);
		
		String sql="select * from bas_tra_shipment_rate t where t.cus_name=:cusName and t.traffic_mode=:trafficMode and t.take_mode=:takeMode and t.depart_id=:disdepartId and t.spe_town=:speTown and t.valuation_type=:valuationType and t.status=2 and t.start_date<=sysdate and t.end_date>=sysdate";
		Page page1 = this.getPageBySqlMap(page, sql, map);
		if(page1.getResultMap().size()<1){
			map.put("addrType", addrType);
			sql = "select * from bas_tra_shipment_rate t where t.cus_name=:cusName and t.traffic_mode=:trafficMode and t.take_mode=:takeMode and t.area_type=:addrType and t.depart_id=:disdepartId and t.valuation_type=:valuationType and t.status=2 and t.start_date<=sysdate and t.end_date>=sysdate";
			page1 = this.getPageBySqlMap(page, sql, map);
		}
		return page1;
	}

	public void saveExcelOfExcel(File excelFile, String fileName) throws Exception {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		List<BasTraShipmentRate> fiList = new ArrayList<BasTraShipmentRate>();
		List list=null;
		if(fileName.toLowerCase().endsWith(".xlsx")){
				ReadExcel2007 readExcel2007 = new ReadExcel2007(15);
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
				ReadExcel readExcel = new ReadExcel(15);
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
				BasTraShipmentRate basCqRate =  new BasTraShipmentRate();
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
				
				String diGoodsType =(String)jt.next();
				if("机场自提".equals(diGoodsType)||"市内送货".equals(diGoodsType)||"市内自提".equals(diGoodsType)){
					basCqRate.setTakeMode(diGoodsType);
				}else{
					throw new ServiceException("第<"+(i+1)+">行不存这样提货方式");
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
					basCqRate.setAreaType(addrType);
				}else{
					throw new ServiceException("第<"+(i+1)+">行不存这样的地址类型");
				}
				
				String valuationType =(String)jt.next();
				if("件数".equals(valuationType)||"重量".equals(valuationType)||"体积".equals(addrType)){
					basCqRate.setValuationType(valuationType);
				}else{
					throw new ServiceException("第<"+(i+1)+">行不存这样的计价方式");
				}
				
				String isPro =(String)jt.next();
				if(isPro==null||"".equals(isPro)){
					basCqRate.setIsNotProject(0l);				
				}else{
					basCqRate.setIsNotProject(isPro=="是"?1l:0l);				
				}
				
				if(basCqRate.getIsNotProject()==1l){
					String isCompanyPro =(String)jt.next();
					if((!"".equals(isCompanyPro))&&isCompanyPro!=null){
						List<Customer> listCus =customerService.findBy("cusName", isCompanyPro);
						if(listCus.size()==0||listCus.size()>2){
							throw new ServiceException("第<"+(i+1)+">行项目客户名称<"+isCompanyPro+">在客商基础资料里面找不到");
						}else{
							basCqRate.setProjectCusId(listCus.get(0).getId());
							basCqRate.setProjectCusName(isCompanyPro);
						}
					}
				}
				
				basCqRate.setStatus(1l);
				fiList.add(basCqRate);
			}
		}
		
		for(BasTraShipmentRate basCqRate : fiList){
			save(basCqRate);
		}
	}
}

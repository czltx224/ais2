package com.xbwl.finance.Service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

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
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.cus.dao.ICusRecordDao;
import com.xbwl.entity.BasCqCorporateRate;
import com.xbwl.entity.BasCqStCorporateRate;
import com.xbwl.entity.CusRecord;
import com.xbwl.entity.Customer;
import com.xbwl.finance.Service.ICqCorporateRateService;
import com.xbwl.finance.Service.ICqStCorporateRateService;
import com.xbwl.finance.dao.ICqStCorporateRateDao;
import com.xbwl.sys.service.IBasAreaService;

/**
 *@author LiuHao
 *@time 2011-7-21 ����11:37:46
 */
@Service("cqStCorporateRateServiceImpl")
@Transactional(rollbackFor={Exception.class})
public class CqStCorporateRateServiceImpl extends BaseServiceImpl<BasCqStCorporateRate, Long>
		implements ICqStCorporateRateService {
	
	@Resource(name="cqStCorporateRateHibernateDaoImpl")
	private ICqStCorporateRateDao cqStCorporateRateDao;
	
	@Resource(name = "cqCorporateRateServiceImpl")
	private ICqCorporateRateService cqCorporateRateService;
	
	@Resource(name="cusRecordHibernateDaoImpl")
	private ICusRecordDao cusRecordDao;
	
	@Override
	public IBaseDAO<BasCqStCorporateRate, Long> getBaseDao() {
		return cqStCorporateRateDao;
	}
	
	@Resource(name="basAreaServiceImpl")
	private IBasAreaService basAreaService;

	@Override
	public void save(BasCqStCorporateRate entity) {
		try {
			//ȷ�������۵ļƷѷ�ʽΪ����
			entity.setValuationType("����");//�����۶��ǰ��������Ʒѵ�
        	if(allowSaveService(entity)){
        		if(null!=entity.getId() && entity.getId()>0){
        			entity.setStatus(1l);//�޸ĺͰ�״̬��Ϊδ���
        		}
		        this.cqStCorporateRateDao.save(entity);
        	}else{
        		throw new ServiceException("�ñ�׼Э����Ѿ����ڣ�");
        	}
        } catch (Exception e) {
        	throw new ServiceException(e.getLocalizedMessage());
        }

	}



	public void updateStatusService(String[] ids, Long status)
			throws Exception {
		BasCqStCorporateRate basCqStCorporateRate=null;
		
		for (int i = 0; i < ids.length; i++) {
			basCqStCorporateRate=this.cqStCorporateRateDao.getAndInitEntity(Long.valueOf(ids[i]));
			basCqStCorporateRate.setStatus(status);
			this.cqStCorporateRateDao.save(basCqStCorporateRate);
		}
	}


	public Page<BasCqStCorporateRate> findCqStCorRate(Page page,
			String trafficMode, String takeMode, String distributionMode,
			String addressType, String startCity, String city, String town,
			String street, Long departId) throws Exception {
		Page<BasCqStCorporateRate> page1=null;
		page1=this.findPage(page, "from BasCqStCorporateRate bccr where bccr.trafficMode=? and bccr.takeMode=? and bccr.distributionMode=? and bccr.addressType=? and bccr.status=2 and bccr.startDate<=sysdate and bccr.startAddr=? and bccr.endAddr=? and bccr.departId=?", trafficMode,takeMode,distributionMode,addressType,startCity,street,departId);
		if(page1.getResult().size()<1){
			page1=this.findPage(page, "from BasCqStCorporateRate bccr where bccr.trafficMode=? and bccr.takeMode=? and bccr.distributionMode=? and bccr.addressType=? and bccr.status=2 and bccr.startDate<=sysdate and bccr.startAddr=? and bccr.endAddr=? and bccr.departId=?", trafficMode,takeMode,distributionMode,addressType,startCity,town,departId);
			if(page1.getResult().size()<1){
				page1=this.findPage(page, "from BasCqStCorporateRate bccr where bccr.trafficMode=? and bccr.takeMode=? and bccr.distributionMode=? and bccr.addressType=? and bccr.status=2 and bccr.startDate<=sysdate and bccr.startAddr=? and bccr.endAddr=? and bccr.departId=?", trafficMode,takeMode,distributionMode,addressType,startCity,city,departId);
				//��Ŀ��վû�й�����
				if(page1.getResult().size()<1){
					//ȡĿ��վΪ�յ�
					page1=this.findPage(page, "from BasCqStCorporateRate bccr where bccr.trafficMode=? and bccr.takeMode=? and bccr.distributionMode=? and bccr.addressType=? and bccr.status=2 and bccr.startDate<=sysdate and bccr.startAddr=? and bccr.departId=? and bccr.endAddr is null", trafficMode,takeMode,distributionMode,addressType,startCity,departId);
				}
			}
		}
		return page1;
	}

	public boolean allowSaveService(BasCqStCorporateRate entity) {

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
		List<BasCqStCorporateRate> list = this.cqStCorporateRateDao.find(filters);
		
		if(null!=list && list.size()>0){
			return false;
		}else{
			return true;
		}
	}

	public void discountCqRate(List cqId, Long cusId, String cpName,
			Double rebate,Date startTime,Date endTime) throws Exception {
		User user=WebRalasafe.getCurrentUser(Struts2Utils.getRequest());
		List<CusRecord> list=cusRecordDao.findBy("cusId", cusId);
		if(list.size()>0){
			CusRecord cr=list.get(0);
			cr.setImportanceLevel("Ŀ��ͻ�");
			cusRecordDao.save(cr);
		}else{
			throw new ServiceException("�ÿͻ������û�ж�Ӧ������ϵϵͳ����Ա��");
		}
		for (int i = 0; i < cqId.size(); i++) {
			BasCqStCorporateRate bs=cqStCorporateRateDao.get(Long.valueOf(cqId.get(i).toString()));
			BasCqCorporateRate bq=new BasCqCorporateRate();
			bq.setAddressType(bs.getAddressType());
			bq.setCreateName(user.get("name").toString());
			bq.setCreateTime(new Date());
			bq.setCusId(cusId);
			bq.setCusName(cpName);
			bq.setDepartId(Long.valueOf(user.get("bussDepart")+""));
			bq.setDiscount(rebate);
			bq.setDistributionMode(bs.getDistributionMode());
			bq.setEndAddr(bs.getEndAddr());
			bq.setLowPrice(bs.getLowPrice()*rebate);
			bq.setStage1Rate(bs.getStage1Rate()*rebate);
			bq.setStage2Rate(bs.getStage2Rate()*rebate);
			bq.setStage3Rate(bs.getStage3Rate()*rebate);
			bq.setStartAddr(bs.getStartAddr());
			bq.setStartDate(startTime);
			bq.setEndDate(endTime);
			bq.setStatus(1L);
			bq.setTakeMode(bs.getTakeMode());
			bq.setTrafficMode(bs.getTrafficMode());
			//FIXED ����ľ�������Ӧ��ע��˵��
			//bq.setTs("3126546");
			bq.setValuationType(bs.getValuationType());
			if(cqCorporateRateService.allowSaveService(bq)){
				cqCorporateRateService.save(bq);
			}else{
				continue;
			}
		}
	}

	public void saveExcelOfExcel(File excelFile, String fileName)
			throws Exception {
		User user= WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		List<BasCqStCorporateRate> fiList = new ArrayList<BasCqStCorporateRate>();
		List list=null;
		if(fileName.toLowerCase().endsWith(".xlsx")){
				ReadExcel2007 readExcel2007 = new ReadExcel2007(14);
				FileInputStream fint = new FileInputStream(excelFile);
				try {
					list = readExcel2007.readExcel2007(fint);
				} catch (Exception e) {
						throw new ServiceException("��ȡ���ݴ��󣬴��ڷǷ�ֵ");
				}finally{
					if (fint!=null) {
						fint.close();
						fint=null;
					}
				}
		}else if(fileName.toLowerCase().endsWith(".xls")){
				ReadExcel readExcel = new ReadExcel(14);
				FileInputStream fint = new FileInputStream(excelFile);
				try {
					list = readExcel.readExcel(fint);
				} catch (Exception e) {
						throw new ServiceException("��ȡ���ݴ��󣬴��ڷǷ�ֵ");
				}finally{
					if (fint!=null) {
						fint.close();
						fint=null;
					}
				}
		}else{
			throw new ServiceException("�뵼��Excel�ļ�����׺Ϊ.xlsx����.xls");
		}
		
		for(int i=0;i<list.size();i++){
			List row=(List)list.get(i);
			if(i!=0){
				BasCqStCorporateRate basCqRate =  new BasCqStCorporateRate();
				Iterator jt=row.iterator();
				jt.hasNext();
				 jt.next();
				
				String startDateString=(String) jt.next();
				if(startDateString!=null&&df.parse(startDateString)!=null){
					basCqRate.setStartDate(df.parse(startDateString));
				}
				String endDateString=(String) jt.next();
				if(endDateString!=null&&df.parse(endDateString)!=null){
					basCqRate.setEndDate(df.parse(endDateString));
				}
				
				String tranType =(String)jt.next();
				if("����".equals(tranType)||"����".equals(tranType)){
					basCqRate.setTrafficMode(tranType);
				}else{
					throw new ServiceException("��<"+(i+1)+">�в������������䷽ʽ");
				}
				
				String dispachName =(String)jt.next();
				if("��ת".equals(dispachName)||"�°�".equals(dispachName)||"�ⷢ".equals(dispachName)){
					basCqRate.setDistributionMode(dispachName);
				}else{
					throw new ServiceException("��<"+(i+1)+">�в������������ͷ�ʽ");
				}
				
				String diGoodsType =(String)jt.next();
				if("��������".equals(diGoodsType)||"�����ͻ�".equals(diGoodsType)||"��������".equals(diGoodsType)){
					basCqRate.setTakeMode(diGoodsType);
				}else{
					throw new ServiceException("��<"+(i+1)+">�в������������ͷ�ʽ");
				}
				
				String lowPrice =(String)jt.next();
				basCqRate.setLowPrice(Double.valueOf(lowPrice==null?"0.0":lowPrice));

				String stage1Rate =(String)jt.next();
				basCqRate.setStage1Rate(Double.valueOf(stage1Rate==null?"0.0":stage1Rate));
				String stage2Rate =(String)jt.next();
				basCqRate.setStage2Rate(Double.valueOf(stage2Rate==null?"0.0":stage2Rate));
				String stage3Rate =(String)jt.next();
				basCqRate.setStage3Rate(Double.valueOf(stage3Rate==null?"0.0":stage3Rate));
				
				String addrType =(String)jt.next();
				if("����".equals(addrType)||"����".equals(addrType)||"Զ��".equals(addrType)||"�۰�".equals(addrType)||"����".equals(addrType)){
					basCqRate.setAddressType(addrType);
				}else{
					throw new ServiceException("��<"+(i+1)+">�в��������ĵ�ַ����");
				}
				
				String valuationType =(String)jt.next();
				if("����".equals(valuationType)||"����".equals(valuationType)||"���".equals(addrType)){
					basCqRate.setValuationType(valuationType);
				}else{
					throw new ServiceException("��<"+(i+1)+">�в��������ļƼ۷�ʽ");
				}
				
				String startAddr =(String)jt.next();
				if(startAddr!=null&&(!"".equals(startAddr))){
					if(basAreaService.isBasAreaExistOfString(startAddr)){
						basCqRate.setStartAddr(startAddr);
					}else{
						throw new ServiceException("��<"+(i+1)+">�п�ʼ��ַ�ڵ������Ҳ���");
					}
				}else{
					throw new ServiceException("��<"+(i+1)+">�п�ʼ��ַ����Ϊ��");
				}
				String endAddr =(String)jt.next();
				if(endAddr!=null&&(!"".equals(endAddr))){
					if(basAreaService.isBasAreaExistOfString(endAddr)){
						basCqRate.setEndAddr(endAddr);
					}else{
						throw new ServiceException("��<"+(i+1)+">�н�����ַ�ڵ������Ҳ���");
					}
				}
				
				basCqRate.setStatus(1l);
				fiList.add(basCqRate);
			}
		}
		
		for(BasCqStCorporateRate basCqRate : fiList){
			save(basCqRate);
		}
	}
}

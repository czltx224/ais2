package com.xbwl.cus.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.ralasafe.WebRalasafe;
import org.ralasafe.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.cus.dao.ICusOverWeightDao;
import com.xbwl.cus.service.ICusOverWeightService;
import com.xbwl.entity.BasCusService;
import com.xbwl.entity.OprOverweight;
import com.xbwl.entity.SysDepart;
import com.xbwl.finance.dto.IFiInterface;
import com.xbwl.finance.dto.impl.FiInterfaceProDto;
import com.xbwl.rbac.Service.IDepartService;
import com.xbwl.sys.service.IBasCusService;

/**
 * 主单超重处理服务层实现类
 *@author LiuHao
 *@time Oct 25, 2011 10:57:23 AM
 */
@Service("cusOverWeightServiceImpl")
@Transactional(rollbackFor = { Exception.class })
public class CusOverWeightServiceImpl extends BaseServiceImpl<OprOverweight,Long> implements
		ICusOverWeightService {
	@Resource(name="cusOverWeightHibernateDaoImpl")
	private ICusOverWeightDao cusOverWeightDao;
	@Resource(name="fiInterfaceImpl")
	private IFiInterface fiInterface;
	@Resource(name="basCusServiceImpl")
	private IBasCusService basCusService;
	@Resource(name="departServiceImpl")
	private IDepartService departService;
	@Override
	public IBaseDAO getBaseDao() {
		return cusOverWeightDao;
	}
	//REVIEW-ACCEPT 增加注释
	//FIXED 接口已写注释 此处是否可以不写？
	public void aduitOverWeight(Long ooId,String aduitRemark) throws Exception {
		OprOverweight oprOverweight = cusOverWeightDao.get(ooId);
		User user = WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
		if(oprOverweight.getAuditStatus()!=1L){
			new ServiceException("只能审核未审核的超重信息！");
		}else{
			
			oprOverweight.setAuditRemark(aduitRemark);
			oprOverweight.setAuditName(user.get("name").toString());
			oprOverweight.setAuditStatus(2L);
			oprOverweight.setAuditTime(new Date());
			
			//收入接口所需List
			List<FiInterfaceProDto> incomeList=new ArrayList<FiInterfaceProDto>(0);
			//REVIEW-ACCEPT 这个list只有一个元素，应当在初始化时指定长度避免内存浪费
			//FIXED LIUH
			List<FiInterfaceProDto> list=new ArrayList<FiInterfaceProDto>(0); 
			FiInterfaceProDto fpd=new FiInterfaceProDto();
			fpd.setFlightMainNo(oprOverweight.getFlightMainNo());
			fpd.setCustomerId(oprOverweight.getCustomerId());
			fpd.setCustomerName(oprOverweight.getCustomerName());
			fpd.setCollectionUser(user.get("name").toString());
			fpd.setSettlementType(1L);
			fpd.setCreateRemark("收_"+oprOverweight.getCustomerName()+"_"+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"_主单号("+oprOverweight.getFlightMainNo()+")_超重("+oprOverweight.getWeight()+")__费率("+oprOverweight.getRate()+")_预付提送费");
			fpd.setAmount(oprOverweight.getAmount());
			fpd.setDocumentsType("收入");
			fpd.setDocumentsSmalltype("超重结算单");
			fpd.setDocumentsNo(ooId);
			fpd.setCostType("预付提送费");
			fpd.setSourceData("主单超重");
			fpd.setSourceNo(ooId);
			list.add(fpd);
			
			//收入
			BasCusService cusService=basCusService.getCusServiceByCusId(oprOverweight.getCustomerId(), Long.valueOf(user.get("bussDepart").toString()));
			if(cusService == null){
				throw new ServiceException("数据异常，代理ID为:"+oprOverweight.getCustomerId()+"的客商对应的客服员为空！");
			}
			SysDepart sd=departService.getDepartByDepartNo(cusService.getServiceDepartCode());
			if(sd == null){
				throw new ServiceException("部门编码:"+cusService.getServiceDepartCode()+"对应的部门信息为空了，请联系系统管理员！");
			}
			FiInterfaceProDto incomeFpd=new FiInterfaceProDto();
			incomeFpd.setCustomerId(oprOverweight.getCustomerId());
			incomeFpd.setCustomerName(oprOverweight.getCustomerName());
			incomeFpd.setAmount(oprOverweight.getAmount());
			incomeFpd.setSourceData("主单超重");
			incomeFpd.setCostType("其他收入");
			incomeFpd.setCustomerService(cusService.getServiceName());
			incomeFpd.setAdmDepart(cusService.getServiceDepart());
			incomeFpd.setAdmDepartId(sd.getDepartId());
			incomeFpd.setSourceNo(oprOverweight.getId());
			incomeFpd.setIncomeDepartId(oprOverweight.getDepartId());
			incomeFpd.setIncomeDepart(oprOverweight.getDepartName());
			incomeFpd.setDepartId(Long.valueOf(user.get("bussDepart")+""));
			incomeFpd.setDepartName(user.get("rightDepart")+"");
			incomeList.add(incomeFpd);
			fiInterface.addFinanceInfo(list);
			//收入接口
			fiInterface.currentToFiIncome(incomeList);
			cusOverWeightDao.save(oprOverweight);
		}

	}
}

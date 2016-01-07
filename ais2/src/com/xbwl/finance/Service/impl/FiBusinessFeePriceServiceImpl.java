package com.xbwl.finance.Service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.ralasafe.WebRalasafe;
import org.ralasafe.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.log.anno.ModuleName;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.common.utils.LogType;
import com.xbwl.entity.FiBusinessFeePrice;
import com.xbwl.finance.Service.IFiBusinessFeePriceService;
import com.xbwl.finance.dao.IFiBusinessFeePriceDao;

/**
 * author shuw
 * time Dec 26, 2011 10:13:37 AM 
 * 业务费协议价管理
 */
@Service("fiBusinessFeePriceServiceImpl")
@Transactional(rollbackFor = { Exception.class })
public class FiBusinessFeePriceServiceImpl  extends
					BaseServiceImpl<FiBusinessFeePrice, Long> implements IFiBusinessFeePriceService {

	@Resource(name="fiBusinessFeePriceHibernateDaoImpl")
	private IFiBusinessFeePriceDao fiBusinessFeePriceDao;  
	
	public IBaseDAO<FiBusinessFeePrice, Long> getBaseDao() {
		return fiBusinessFeePriceDao;
	}

	//删除业务费协议价
	@ModuleName(value="佣金协议价管理删除",logType=LogType.fi)
	public void deleteByStatus(List<Long> list) throws Exception {
		for(Long id:list){
			FiBusinessFeePrice fiBusinessFeePrice=get(id);
			fiBusinessFeePrice.setIsDelete(0l);  //状态修改删除
		}
	}

	//审核数据保存
	@ModuleName(value="佣金协议价管理审核",logType=LogType.fi)
	public void audit(List<FiBusinessFeePrice> aa) throws Exception {
		User user = WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
		for(FiBusinessFeePrice fbp:aa){
			FiBusinessFeePrice fiBusPrice=get(fbp.getId());
			
			if(fiBusPrice.getIsDelete()==0l){   //验证是否已删除
				throw new ServiceException("存在删除的数据，不能审核");
			}
			
			fiBusPrice.setStatus(1l);
			fiBusPrice.setReviewDate(new Date());
			fiBusPrice.setReviewUser(user.get("name").toString());
		}
	}

	@ModuleName(value="佣金协议价管理保存",logType=LogType.fi)
	public void save(FiBusinessFeePrice entity) {
		User user = WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
		Long bussDepartId=Long.parseLong(user.get("bussDepart")+"");
		if(entity.getId()==null){
			List<FiBusinessFeePrice>list =find("from FiBusinessFeePrice f where f.customerId=? and  f.incomeCustomerId=? and f.settlement=? and f.departId=? ",
					entity.getCustomerId(),entity.getIncomeCustomerId(),entity.getSettlement(), bussDepartId);
			if(list.size()!=0){
				throw new ServiceException("此费率已存在，不多次新增"); 
			}
			
			if(entity.getRate()<=0.0){
				throw new ServiceException("费率为零或为负数，不能新增"); 
			}
		}
		fiBusinessFeePriceDao.save(entity);
	}

	
}

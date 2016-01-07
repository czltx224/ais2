package com.xbwl.finance.Service.impl;

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
import com.xbwl.entity.FiDeliveryPrice;
import com.xbwl.finance.Service.IFiDeliveryPriceService;
import com.xbwl.finance.dao.IFiDeliveryPriceDao;

/**
 * author shuw
 * time Oct 10, 2011 3:58:37 PM
 */
@Service("fiDeliveryPriceServiceImpl")
@Transactional(rollbackFor = { Exception.class })
public class FiDeliveryPriceServiceImpl extends BaseServiceImpl<FiDeliveryPrice,Long> implements
																	IFiDeliveryPriceService {

	@Resource(name = "fiDeliveryPriceHibernateDaoImpl")
	private IFiDeliveryPriceDao fiDeliveryPriceDao;
	
	@Override
	public IBaseDAO<FiDeliveryPrice, Long> getBaseDao() {
		return fiDeliveryPriceDao;
	}

	public void save(FiDeliveryPrice entity) {
		if(entity!=null){
			if(entity.getId()==null){
				User user= WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
				Long bussDepartId=Long.parseLong(user.get("bussDepart")+"");
				List<FiDeliveryPrice>list =fiDeliveryPriceDao.find("from FiDeliveryPrice fp where fp.departId=? and fp.customerId=? and fp.goodsType=?  and fp.isdelete=1 ",
						bussDepartId,entity.getCustomerId(),entity.getGoodsType());
				if(list.size()>0){
					throw new ServiceException("此货物类型的协议价已存在，不允许多次新建");
				}
			}
		}
		super.save(entity);
	}

	public void deleteOfStatus(List<Long> list) throws Exception {
		 for(Long id:list){
			 	FiDeliveryPrice fiDeliveryPrice =get(id);
			 	fiDeliveryPrice.setIsdelete(0l);
			 	save(fiDeliveryPrice);
		 }
	}
	
	@ModuleName(value="获取提货协议价",logType=LogType.fi)
	public List<FiDeliveryPrice> getPriceByCustomerId(Long customerId,String goodsType ) throws Exception{
		User user = WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
		Long bussDepart=Long.parseLong(user.get("bussDepart")+"");
		List<FiDeliveryPrice> list=fiDeliveryPriceDao.find("from FiDeliveryPrice f where f.customerId=? and f.goodsType=? and f.departId=? and f.isdelete=1", customerId,goodsType,bussDepart);
		return list;
	}

}

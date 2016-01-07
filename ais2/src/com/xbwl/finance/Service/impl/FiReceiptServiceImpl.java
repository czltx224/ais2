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
import com.xbwl.entity.FiPaid;
import com.xbwl.entity.FiReceipt;
import com.xbwl.finance.Service.IFiPaidService;
import com.xbwl.finance.Service.IFiReceiptService;
import com.xbwl.finance.dao.IFiReceiptDao;

/**
 * author shuw
 * time Dec 20, 2011 10:24:28 AM
 */
@Service("fiReceiptServiceImpl")
@Transactional(rollbackFor={Exception.class})
public class FiReceiptServiceImpl extends BaseServiceImpl<FiReceipt, Long> implements
																IFiReceiptService{
	
	@Resource(name="fiReceiptDaoHibernateDaoImpl")
	private IFiReceiptDao fiReceiptDao;
	
	@Resource(name = "fiPaidServiceImpl")
	private IFiPaidService fiPaidService;
	
	public IBaseDAO<FiReceipt, Long> getBaseDao() {
		return fiReceiptDao;
	}

	//作废收据
	@ModuleName(value="作废收据",logType=LogType.fi)
	public String deleteByStatus(List<Long> ids) throws Exception {
		if(ids==null||ids.size()==0){
			throw new ServiceException("请选择你要作废的收据数据");
		}
		User user = WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
		for(Long id:ids){
			FiReceipt fiReceipt = get(id);
			if(fiReceipt.getPrintNum()!=null&&fiReceipt.getPrintNum()>0l){
				throw new ServiceException("此收据已打印不能作废");
			}
			fiReceipt.setDelName(user.get("name")+"");
			fiReceipt.setDelStatus(0l);
			fiReceipt.setDelTime(new Date());
		}
		return null;
	}

	/*
	public void save(FiReceipt entity) {
		/*if(entity.getId()==null){
			List<FiReceipt>list =find("from FiReceipt f where f.fiPaidId=? and f.delStatus=1 ", entity.getFiPaidId());
			if(list.size()>0){
				throw new ServiceException("此记录已录入收据");
			}
		}
		FiPaid fiPaid= fiPaidService.get(entity.getFiPaidId());
		fiReceiptDao.save(entity);
		fiPaid.setFiReceiptId(entity.getId());
	}*/

	
}

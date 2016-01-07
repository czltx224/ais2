package com.xbwl.finance.Service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.criterion.Criterion;
import org.ralasafe.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.orm.Page;
import com.xbwl.common.orm.PropertyFilter;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.entity.FiCollectionstatement;
import com.xbwl.entity.FiReceivablestatement;
import com.xbwl.finance.Service.IFiCollectiondetailService;
import com.xbwl.finance.Service.IFiCollectionsetService;
import com.xbwl.finance.Service.IFiCollectionstatementService;
import com.xbwl.finance.dao.IFiCollectiondetailDao;
import com.xbwl.finance.dao.IFiCollectionstatementDao;

@Service("fiCollectionstatementServiceImpl")
@Transactional(rollbackFor = { Exception.class })
public class FiCollectionstatementServiceImpl extends BaseServiceImpl<FiCollectionstatement,Long> implements
		IFiCollectionstatementService {

	@Resource(name="fiCollectionstatementHibernateDaoImpl")
	private IFiCollectionstatementDao fiCollectionstatementDao;
	
	@Resource(name="fiCollectiondetailServiceImpl")
	private IFiCollectiondetailService fiCollectiondetailService;
	
	@Override
	public IBaseDAO getBaseDao() {
		return fiCollectionstatementDao;
	}


	public boolean isConfirmReview(String reconciliationNos) {
		boolean flag=true;
		String[] creconciliationNos=reconciliationNos.split(",");
		for(int i=0;i<creconciliationNos.length;i++){
			FiCollectionstatement frs=this.get(Long.valueOf(creconciliationNos[i]));
			Long status=frs.getReconciliationStatus();
			if(status!=2){//未审核
				flag=false;
				break;
			}
		}
		       
		return flag;
	}
	
 
	public void confirmReview(String reconciliationNos,User user) {
		
		String[] creconciliationNos=reconciliationNos.split(",");
		for(int i=0;i<creconciliationNos.length;i++){
			//更新对账单状态
			FiCollectionstatement frs=this.get(Long.valueOf(creconciliationNos[i]));
			frs.setReconciliationStatus(Long.valueOf(3));
			frs.setReviewUser(String.valueOf(user.get("name")));
			frs.setReviewDate(new Date());
			frs.setReviewDept(String.valueOf(user.get("departName")));
			this.save(frs);
			
			//回写欠款明细对账状态
			fiCollectiondetailService.updateStatusByreconciliationNo(Long.valueOf(creconciliationNos[i]),frs.getReconciliationStatus());
			
			//将数据写入应收应付表
		}
		
	}
	
	public boolean isRevocationReview(String reconciliationNos){
		boolean flag=true;
		String[] creconciliationNos=reconciliationNos.split(",");
		for(int i=0;i<creconciliationNos.length;i++){
			FiCollectionstatement frs=this.get(Long.valueOf(creconciliationNos[i]));
			Long status=frs.getReconciliationStatus();
			if(status!=3){//未审核
				flag=false;
				break;
			}
		}
		       
		return flag;
	}
	
	public void revocationReview(String reconciliationNos,User user){
		String[] creconciliationNos=reconciliationNos.split(",");
		for(int i=0;i<creconciliationNos.length;i++){
			//更新对账单状态
			FiCollectionstatement frs=this.get(Long.valueOf(creconciliationNos[i]));
			frs.setReconciliationStatus(Long.valueOf(2));
			frs.setReviewUser(String.valueOf(user.get("name")));
			frs.setReviewDate(new Date());
			frs.setReviewDept(String.valueOf(user.get("departName")));
			this.save(frs);
			
			//回写欠款明细对账状态
			fiCollectiondetailService.updateStatusByreconciliationNo(Long.valueOf(creconciliationNos[i]),frs.getReconciliationStatus());
			
			//将数据写入应收应付表
			
		}
			
	}
}

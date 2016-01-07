package com.xbwl.finance.Service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.orm.Page;
import com.xbwl.common.orm.PropertyFilter;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.entity.FiIncomeAccount;
import com.xbwl.entity.FiRepayment;
import com.xbwl.finance.Service.IFiRepaymentService;
import com.xbwl.finance.dao.IFiRepaymentDao;


@Service("fiRepaymentServiceImpl")
@Transactional(rollbackFor = { Exception.class })
public class FiRepaymentServiceImpl extends BaseServiceImpl<FiRepayment,Long> implements
		IFiRepaymentService {

	@Resource(name="fiRepaymentHibernateDaoImpl")
	private IFiRepaymentDao fiRepaymentDao;
	@Override
	public IBaseDAO getBaseDao() {
		return this.fiRepaymentDao;
	}
	
	public void confirmAccountSingle(String ids) throws Exception{
		String hql=null;
		String sqlseq=null;
		
		if(ids==null){
			throw new ServiceException("还款明细为空");
		}
		String[] idn=ids.split(",");
		Long seq=null;
		Long id=null;
		
		//生成交账批次号
		sqlseq="select SEQ_BATCH.nextval SEQ from dual";
		Map seqid=(Map) this.fiRepaymentDao.createSQLQuery(sqlseq).list().get(0) ;
		seq=Long.valueOf(seqid.get("SEQ")+"");
		
		for(int i=0;i<idn.length;i++){
			id=Long.valueOf(idn[i]);
			hql="from FiRepayment f where f.id=?";
			FiRepayment fiRepayment=(FiRepayment) this.fiRepaymentDao.find(hql,id).get(0);
			if(fiRepayment.getStatus()==0L){
				throw new ServiceException("还款明细已作废！");
			}else if(fiRepayment.getAccountStatus()==1L){
				throw new ServiceException("还款明细已经交账！");
			}
			fiRepayment.setAccountStatus(1L);
			fiRepayment.setBatchNo(seq);
			this.fiRepaymentDao.save(fiRepayment);
			
		}
	}

}

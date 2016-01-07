package com.xbwl.finance.Service.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.bean.ValidateInfo;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.orm.Page;
import com.xbwl.common.orm.PropertyFilter;
import com.xbwl.common.orm.PropertyFilter.MatchType;
import com.xbwl.common.service.impl.BaseServiceImpl;

import com.xbwl.entity.FiCollectiondetail;
import com.xbwl.entity.FiCollectionset;
import com.xbwl.entity.FiCollectionstatement;
import com.xbwl.finance.Service.IFiCollectiondetailService;
import com.xbwl.finance.dao.IFiCollectiondetailDao;
import com.xbwl.finance.dao.IFiCollectionstatementDao;

@Service("fiCollectiondetailServiceImpl")
@Transactional(rollbackFor = { Exception.class })
public class FiCollectiondetailServiceImpl extends BaseServiceImpl<FiCollectiondetail,Long> implements
		IFiCollectiondetailService {

	@Resource(name = "fiCollectiondetailHibernateImpl")
	private IFiCollectiondetailDao fiCollectiondetailDao;

	@Resource(name="fiCollectionstatementHibernateDaoImpl")
	private IFiCollectionstatementDao fiCollectionstatementDao;
	
	@Override
	public IBaseDAO getBaseDao() {
		return fiCollectiondetailDao;
	}
	
	public void saveCollectionstatement(Map map,Page page,ValidateInfo validateInfo){
		page.setLimit(200);
		Page pagesearch=this.getCollectiondetailBatch(page, map);
		if(pagesearch.getTotalCount()<=0){
			validateInfo.setSuccess(false);
			validateInfo.setMsg("没有找到相关信息，无法生成对账单!原因可能是：<br>1、您选择的查询条件有误；<br>2、根据您选择的查询条件都已对账。");
		}

		for(int i=1;i<=pagesearch.getTotalPages();i++){
			Iterator it=pagesearch.getResultMap().iterator();
			while(it.hasNext()){
				Map cstmap=(Map) it.next();
				
				Date stateDate=(Date) map.get("stateDate");
				Date endDate=(Date) map.get("endDate");
				Long batch=Long.valueOf(cstmap.get("BATCH")+"");
				Long customerId=Long.valueOf(cstmap.get("CUSTOMER_ID")+ "");
				Long createDeptid=Long.valueOf(cstmap.get("CREATE_DEPTID")+ "");
				Double amount=Double.valueOf(cstmap.get("AMOUNT")+"");
				Long ReconciliationStatus=Long.valueOf(2);
				
				FiCollectionstatement st=new FiCollectionstatement();
				st.setStateDate(stateDate);
				st.setEndDate(endDate);
				st.setReconciliationStatus(ReconciliationStatus);
				st.setCustomerName(cstmap.get("CUSTOMER_NAME")+ "");
				st.setCustomerId(customerId);
				st.setAmount(amount);
				st.setCreateDeptid(createDeptid);
				this.fiCollectionstatementDao.save(st);
				Long stid=st.getId();
				String sqlupdate="update FiCollectiondetail set reconciliationNo=?,reconciliationStatus=2 where batch=? and customerId=? and createDeptid=?";
				this.fiCollectiondetailDao.batchExecute(sqlupdate, stid,batch,customerId,createDeptid);
			}
		}
		
		if(page.getPageNo()*page.getLimit()+1<=page.getTotalCount()){
			page.setStart(page.getPageNo()*page.getLimit());
			saveCollectionstatement(map,page,validateInfo);
		}
		validateInfo.setMsg("对账单生成成功！");
		
	}
	
	public Page getCollectiondetailBatch(Page page,Map map){
		Long seq; 
		StringBuffer sqlbatch=new StringBuffer();
		StringBuffer sqlselect=new StringBuffer();
		
		String sqlseq="select SEQ_BATCH.nextval SEQ from dual";
		Map seqid=(Map) this.fiCollectiondetailDao.createSQLQuery(sqlseq).list().get(0) ;
		seq=Long.valueOf(seqid.get("SEQ")+"");
		map.put("SEQ", seq);
		
		sqlbatch.append("update fi_collectiondetail fd set fd.batch=:SEQ where exists(select 1 from fi_collectionset fs where fd.customer_id=fs.customer_id and fd.create_deptid=fs.dept_id");
		sqlbatch.append(" and fd.CREATE_TIME>=:stateDate and fd.CREATE_TIME<=:endDate and fd.Reconciliation_status=1");
		if (map.get("customerId") != null) {//客商ID
			sqlbatch.append(" and customer_id=:customerId");
		}
		if (map.get("createDeptid") != null){//部门ID
				 sqlbatch.append(" and fd.create_deptid=:createDeptid");
		}
		sqlbatch.append(")");
		
		Integer i=this.fiCollectiondetailDao.batchSQLExecute(sqlbatch.toString(), map);
		
		//根据批次号，将数据汇总，返回Page对象
		sqlselect.append("select fd.customer_id,fd.customer_name,fd.create_deptid,sum(fd.amount) as amount,fd.batch from fi_collectiondetail fd,fi_collectionset fs where fd.create_deptid=fs.dept_id and fd.customer_id=fs.customer_id");
		sqlselect.append(" and fd.batch=:SEQ");
		sqlselect.append(" group by fd.customer_id,fd.customer_name,fd.create_deptid,fs.billing_cycle,fd.batch");
		return this.fiCollectiondetailDao.findPageBySqlMap(page, sqlselect.toString(), map);
	}
	
	public void updateStatusByreconciliationNo(Long reconciliationNo,Long reconciliationStatus){
		String sqlupdate="update fi_collectiondetail set reconciliation_Status=? where reconciliation_No=?";
		this.fiCollectiondetailDao.batchSQLExecute(sqlupdate, reconciliationStatus,reconciliationNo);
	}
}

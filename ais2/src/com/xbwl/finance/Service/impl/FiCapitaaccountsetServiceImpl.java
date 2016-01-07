package com.xbwl.finance.Service.impl;

import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.ralasafe.WebRalasafe;
import org.ralasafe.user.User;
import org.ralasafe.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.orm.Page;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.entity.FiCapitaaccount;
import com.xbwl.entity.FiCapitaaccountset;
import com.xbwl.finance.Service.IFiCapitaaccountsetService;
import com.xbwl.finance.dao.IFiCapitaaccountDao;
import com.xbwl.finance.dao.IFiCapitaaccountsetDao;
import com.xbwl.finance.vo.FiCapitaaccountsetVo;

@Service("fiCapitaaccountsetServiceImpl")
@Transactional(rollbackFor = { Exception.class })
public class FiCapitaaccountsetServiceImpl extends
		BaseServiceImpl<FiCapitaaccountset, Long> implements
		IFiCapitaaccountsetService {

	@Resource(name = "fiCapitaaccountsetHibernateDaoImpl")
	private IFiCapitaaccountsetDao fiCapitaaccountsetDao;

	// 资金账号流水Dao
	@Resource(name = "fiCapitaaccountHibernateDaoImpl")
	private IFiCapitaaccountDao fiCapitaaccountDao;

	@Override
	public IBaseDAO getBaseDao() {
		return fiCapitaaccountsetDao;
	}

	private SimpleJdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new SimpleJdbcTemplate(dataSource);
	}
	
	public void saveFiCapitaaccountset(FiCapitaaccountset fiCapitaaccountset)
			throws Exception {
		if (fiCapitaaccountset == null) {
			throw new ServiceException("保存失败，资金账号设置实体为空！");
		}
		Double openingBalance = fiCapitaaccountset.getOpeningBalance();// 初期余额
		 
		if(openingBalance==null||"".equals(openingBalance)){
			openingBalance=0.0;
		}
		

		if (fiCapitaaccountset.getId() != null) {// 修改收款单，获取原保存金额
			//FiCapitaaccountset fcs=this.fiCapitaaccountsetDao.get(fiCapitaaccountset.getId());//原保存数
			Map fcs=this.jdbcTemplate.queryForMap("select * from Fi_Capitaaccountset where id=?", fiCapitaaccountset.getId());
			Double ob;
			if("null".equals(String.valueOf(fcs.get("OPENING_BALANCE")))||String.valueOf(fcs.get("OPENING_BALANCE"))==null){
				ob=0.0;
			}else{
				ob=Double.valueOf(String.valueOf(fcs.get("OPENING_BALANCE")));
			};
			if(openingBalance>ob||openingBalance<ob){

				FiCapitaaccount fiCapitaaccount = new FiCapitaaccount();
				if(openingBalance>ob){
					fiCapitaaccount.setLoan(openingBalance-ob);
				}else{
					fiCapitaaccount.setBorrow(ob-openingBalance);
					openingBalance=ob-openingBalance;
				}
				fiCapitaaccount.setBalance(openingBalance);// 账号余额
				fiCapitaaccount.setFiCapitaaccountsetId(fiCapitaaccountset.getId());
				fiCapitaaccount.setSourceData("账号设置");
				fiCapitaaccount.setRemark("修改余额");
				fiCapitaaccount.setSourceNo(fiCapitaaccountset.getId());
				this.fiCapitaaccountDao.save(fiCapitaaccount);// 保存流水账
			}
			this.fiCapitaaccountsetDao.save(fiCapitaaccountset);
		} else {
			// 新增账金账号
			this.fiCapitaaccountsetDao.save(fiCapitaaccountset);

			if (openingBalance > 0L) {
				FiCapitaaccount fiCapitaaccount = new FiCapitaaccount();
				fiCapitaaccount.setLoan(openingBalance);
				fiCapitaaccount.setBalance(openingBalance);// 账号余额
				fiCapitaaccount.setFiCapitaaccountsetId(fiCapitaaccountset.getId());
				fiCapitaaccount.setSourceData("账号设置");
				fiCapitaaccount.setSourceNo(fiCapitaaccountset.getId());

				this.fiCapitaaccountDao.save(fiCapitaaccount);// 保存流水账
			}

		}

	}

	public Page<FiCapitaaccountset> findAccountList(Map map,Page page) throws Exception{
		Page<FiCapitaaccountset> pageReturn=null;
		User user=WebRalasafe.getCurrentUser(Struts2Utils.getRequest());
		Long departId=0L;
		Long paymentType=0L;
		Long userId=0L;
		Long accountType=0L;
		if(!"null".equals(map.get("departId")+"")){
			departId=Long.valueOf(map.get("departId")+"");
		}
		
		if(!"null".equals(map.get("paymentType")+"")){
			paymentType=Long.valueOf(map.get("paymentType")+"");
		}
		
		if(!"null".equals(map.get("userId")+"")){
			userId=Long.valueOf(map.get("userId")+"");
		}
		
		if(!"null".equals(map.get("accountType")+"")){
			accountType=Long.valueOf(map.get("accountType")+"");
		}
		
		String accountNum=map.get("accountNum")+"";
		String accountName=map.get("accountName")+"";

		StringBuffer sql=new StringBuffer();
		sql.append("select b1.type_name as payment_type_Name,b2.type_name as account_type_name,f.* from Fi_Capitaaccountset f left join bas_dictionary_detail b1 on f.payment_type=b1.id left join bas_dictionary_detail b2 on f.account_type=b2.id where f.isdelete=1");
		if(departId!=0L){
			sql.append(" and f.depart_id=:departId");
		}
		if(paymentType!=0L){
			sql.append(" and (f.payment_type=:paymentType or f.payment_type=27300)");
		}
		if(userId!=0L){
			sql.append(" and f.responsibleid=:userId");
		}
		if(accountType!=0L){
			sql.append(" and f.account_type=:accountType");
		}
		if(!"null".equals(accountNum)){
			sql.append(" and f.account_num like '%"+accountNum+"%'");
		}
		if(!"null".equals(accountName)){
			sql.append(" and f.account_name like '%"+accountName+"%'");
		}
		pageReturn=this.fiCapitaaccountsetDao.findPageBySqlMap(page, sql.toString(), map);
		return pageReturn;
	}
}

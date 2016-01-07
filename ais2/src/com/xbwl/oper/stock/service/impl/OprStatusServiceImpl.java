package com.xbwl.oper.stock.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.ralasafe.WebRalasafe;
import org.ralasafe.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.common.utils.DoubleUtil;
import com.xbwl.entity.OprStatus;
import com.xbwl.finance.dao.IFiPaymentDao;
import com.xbwl.oper.stock.dao.IOprStatusDao;
import com.xbwl.oper.stock.service.IOprStatusService;

/**
 * author CaoZhili time Jul 6, 2011 2:55:16 PM
 * 
 * 状态辅助表服务层实现类
 */
@Service("oprStatusServiceImpl")
@Transactional(rollbackFor = { Exception.class })
public class OprStatusServiceImpl extends BaseServiceImpl<OprStatus, Long>
		implements IOprStatusService {

	@Resource(name = "oprStatusHibernateDaoImpl")
	private IOprStatusDao oprStatusDao;
	
	@Resource(name = "fiPaymentHibernateDaoImpl")
	private IFiPaymentDao fiPaymentDao;

	@Override
	public IBaseDAO<OprStatus, Long> getBaseDao() {
		return this.oprStatusDao;
	}

	public OprStatus findStatusByDno(Long dno) throws Exception {
		return oprStatusDao.findStatusByDno(dno);
	}
	
	//财务收银时回写传真状态表收银状态
	public void verificationCashStatusByFiPayment(Long dno) throws Exception{
		StringBuffer sqlbuffer=new StringBuffer();
		double amount=0.0;
		double settlementAmount=0.0;
		double eliminationAmount=0.0;
		User user = WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
		Object[] obj = fiPaymentDao.findUnique("select sum(amount) as amount,sum(settlementAmount) as settlementAmount,sum(eliminationAmount) as eliminationAmount from FiPayment f where f.paymentType=1 and f.documentsSmalltype='配送单' and f.status=1 and f.documentsNo=?",dno);
		if(obj[0]==null){
			amount=0.0;
		}else{
			amount=Double.valueOf(obj[0]+"");
		}
		if(obj[1]==null){
			settlementAmount=0.0;
		}else{
			settlementAmount=Double.valueOf(obj[1]+"");
		}
		if(obj[2]==null){
			eliminationAmount=0.0;
		}else{
			eliminationAmount=Double.valueOf(obj[2]+"");
		}
		
		if(amount==settlementAmount){
			OprStatus oprStatus=this.oprStatusDao.findStatusByDno(dno);
			if(oprStatus!=null){
				oprStatus.setCashStatus(1L);
				oprStatus.setCashTime(new Date());
				oprStatus.setCashName(String.valueOf(user.get("name")));
				this.oprStatusDao.save(oprStatus);
			}
		}
	}
	
	//财务收银应收时回写传真状态表收银状态
	public void verificationCashStatusByFiReceivabledetail(Long dno) throws Exception{
		StringBuffer sqlbuffer=new StringBuffer();
		double amount=0.0;
		double problemAmount=0.0;
		double verificationAmount=0.0;
		double eliminationAmount=0.0;
		StringBuffer sqlBuffer=new StringBuffer();
		sqlBuffer.append("select sum(f.amount) as amount,sum(f.problem_amount) as problem_amount,sum(f.verification_amount) as verification_amount,sum(f.elimination_amount) as elimination_amount from Fi_Receivabledetail f inner join customer c on f.customer_id=c.id ");
		sqlBuffer.append("where f.d_no=? and f.payment_type=1 and (c.custprop='中转' or c.custprop='外发')");
		List<Map> list=this.fiPaymentDao.createSQLQuery(sqlBuffer.toString(), dno).list();
		amount=Double.valueOf(list.get(0).get("AMOUNT")+"");
		//problemAmount=Double.valueOf(list.get(0).get("PROBLEM_AMOUNT")+"");
		verificationAmount=Double.valueOf(list.get(0).get("VERIFICATION_AMOUNT")+"");
		eliminationAmount=Double.valueOf(list.get(0).get("ELIMINATION_AMOUNT")+"");
		verificationAmount=DoubleUtil.add(verificationAmount, eliminationAmount);
		if(verificationAmount>=amount){
			OprStatus oprStatus=this.oprStatusDao.findStatusByDno(dno);
			if(oprStatus!=null){
				oprStatus.setCashStatus(1L);
				this.oprStatusDao.save(oprStatus);
			}
		}
	}
	
	//财务撤销收银
	public void revocationCashStatus(Long dno) throws Exception{
		OprStatus oprStatus=this.oprStatusDao.findStatusByDno(dno);
		if(oprStatus!=null){
			oprStatus.setCashStatus(0L);
			this.oprStatusDao.save(oprStatus);
		}
	}
	
}

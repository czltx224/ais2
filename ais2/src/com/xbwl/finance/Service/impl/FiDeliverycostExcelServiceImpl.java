package com.xbwl.finance.Service.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.ralasafe.WebRalasafe;
import org.ralasafe.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.log.anno.ModuleName;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.common.utils.LogType;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.entity.FiDeliverycostExcel;
import com.xbwl.entity.OprHistory;
import com.xbwl.entity.OprNode;
import com.xbwl.finance.Service.IFiDeliverycostExcelService;
import com.xbwl.finance.dao.IFiDeliverycostDao;
import com.xbwl.finance.dao.IFiDeliverycostExcelDao;
import com.xbwl.finance.dto.IFiInterface;
import com.xbwl.finance.dto.impl.FiInterfaceProDto;
import com.xbwl.oper.stock.service.IOprHistoryService;
import com.xbwl.oper.stock.service.IOprNodeService;

/**
 * author shuw
 * time Oct 22, 2011 10:56:37 AM
 */
@Service("fiDeliverycostExcelServiceImpl")
@Transactional(rollbackFor = { Exception.class })
public class FiDeliverycostExcelServiceImpl extends BaseServiceImpl<FiDeliverycostExcel,Long> implements
														IFiDeliverycostExcelService{

	@Resource(name = "fiDeliverycostExcelHibernateDaoImpl")
	private IFiDeliverycostExcelDao fiDeliverycostExcelDao;
	
	@Resource(name="fiDeliverycostHibernateDaoImpl")
	private IFiDeliverycostDao fiDeliverycostDao;
	
	@Resource(name = "fiInterfaceImpl")
	private IFiInterface fiInterfaceImpl;
	
	@Value("${fiAuditCost.log_auditCost}")
	private Long log_auditCost ;
	
	@Resource(name = "oprNodeServiceImpl")
	private IOprNodeService oprNodeService;
	
	@Override
	public IBaseDAO<FiDeliverycostExcel, Long> getBaseDao() {
		return fiDeliverycostExcelDao;
	}
	
	@ModuleName(value="提货成本管理Excel对账审核",logType=LogType.fi)
	public String auditFi(String batchNo, String cusName, Long cusId,Double money, String code) throws Exception {
		List<FiDeliverycostExcel>list  = fiDeliverycostExcelDao.find("from FiDeliverycostExcel f where f.batchNo=? and f.status=? ",Long.parseLong(batchNo),0l);
		if(list.size()!=0){
			throw new ServiceException("存在没有对账成功的数据，不允许审核");
		}
		
		User user=WebRalasafe.getCurrentUser(Struts2Utils.getRequest());
/*		List<FiInterfaceProDto> listfiInterfaceDto =new ArrayList<FiInterfaceProDto>();
		FiInterfaceProDto fiIn = new FiInterfaceProDto();
		fiIn.setCustomerId(cusId);
		fiIn.setCustomerName(cusName);
		fiIn.setDistributionMode("客商");
		fiIn.setSettlementType(2l);
		fiIn.setDocumentsType("成本");
		fiIn.setDocumentsSmalltype("提货对账单");
		fiIn.setDocumentsNo(Long.parseLong(batchNo));
		fiIn.setAmount(money);
		fiIn.setCostType("对账");
		fiIn.setDepartId(Long.parseLong(user.get("departId")+""));
		fiIn.setSourceData("提货成本");
		fiIn.setSourceNo(Long.parseLong(batchNo));
		fiIn.setCreateRemark("支付"+cusName+"提货费"+money+"元");
		listfiInterfaceDto.add(fiIn);
		fiInterfaceImpl.reconciliationToFiPayment(listfiInterfaceDto);*/
		OprNode oprNode = oprNodeService.get(log_auditCost);
		Connection con=SessionFactoryUtils.getDataSource(fiDeliverycostExcelDao.getSessionFactory()).getConnection();
		try {
			CallableStatement cs=con.prepareCall("{call pro_fi_deliverycost_audit(?,?,?,?,?, ?,?,?,?,?)}");
			cs.setLong(1, Long.parseLong(batchNo));
			cs.setString(2,code);
			cs.setString(3,user.get("name").toString());
			cs.setString(4,System.currentTimeMillis()+"");
			cs.setLong(5,Long.parseLong(user.get("bussDepart")+""));
			cs.setString(6,user.get("rightDepart").toString());
			
			cs.setString(7,oprNode.getNodeName());
			cs.setLong(8, oprNode.getId());
			cs.setLong(9, oprNode.getNodeType());
			cs.registerOutParameter(10,Types.VARCHAR);
			cs.executeUpdate();
			String returnMsg=cs.getString(10);
			return returnMsg;
		} catch (Exception e) {
			throw new ServiceException("保存的存储过程出错");
		}finally{
			if(con!=null){
				con.close();
				con=null;
			}
		}
	}

	public Long getBatchNO(Long bussDepartId) throws Exception {
		String carString ;
		Map  times = (Map)fiDeliverycostExcelDao.createSQLQuery("  select SEQ_FI_EXCEL_BATCH_NO.nextval cartimes from  dual  ").list().get(0);
		Long s =Long.valueOf(times.get("CARTIMES")+"");
		//SimpleDateFormat dateFm = new SimpleDateFormat("yyyyMMdd"); //格式化当前系统日期
		//String dateTime = dateFm.format(new java.util.Date());
		//carString="E"+dateTime+bussDepartId+s;
		return s;
	}

	@ModuleName(value="提货成本管理Excel对账",logType=LogType.fi)
	public String compareStatus(String batchNo) throws Exception {
		Connection con=SessionFactoryUtils.getDataSource(fiDeliverycostExcelDao.getSessionFactory()).getConnection();
		try{
			CallableStatement cs=con.prepareCall("{call pro_fi_deliverycost_excel(?,?)}");
			cs.setString(1, batchNo);
			cs.registerOutParameter(2,Types.VARCHAR);
			cs.executeUpdate();
			String returnMsg=cs.getString(2);
			return returnMsg;
		} catch (Exception e) {
			throw new ServiceException("保存的存储过程出错");
		}finally{
			if(con!=null){
				con.close();
				con=null;
			}
		}
	}

	@ModuleName(value="提货成本管理Excel对账由大改小修正",logType=LogType.fi)
	public String updateAllFax(String batchNo) throws Exception {
		User user=WebRalasafe.getCurrentUser(Struts2Utils.getRequest());
		Connection con=SessionFactoryUtils.getDataSource(fiDeliverycostExcelDao.getSessionFactory()).getConnection();
		try{
			CallableStatement cs=con.prepareCall("{call pro_fi_delict_update(?,?,?)}");
			cs.setString(1, batchNo);
			cs.setLong(2, 1l);
			cs.registerOutParameter(3,Types.VARCHAR);
			cs.executeUpdate();
			String returnMsg=cs.getString(3);
			return returnMsg;
		} catch (Exception e) {
			throw new ServiceException("保存的存储过程出错");
		}finally{
			if(con!=null){
				con.close();
				con=null;
			}
		}
	}

	@ModuleName(value="提货成本管理Excel对账完全修正",logType=LogType.fi)
	public String updateFax(String batchNo) throws Exception {
		User user=WebRalasafe.getCurrentUser(Struts2Utils.getRequest());
		Connection con=SessionFactoryUtils.getDataSource(fiDeliverycostExcelDao.getSessionFactory()).getConnection();
		try{
			CallableStatement cs=con.prepareCall("{call pro_fi_delict_update(?,?,?)}");
			cs.setString(1, batchNo);
			cs.setLong(2, 0l);
			cs.registerOutParameter(3,Types.VARCHAR);
			cs.executeUpdate();
			String returnMsg=cs.getString(3);
			return returnMsg;
		} catch (Exception e) {
			throw new ServiceException("保存的存储过程出错");
		}finally{
			if(con!=null){
				con.close();
				con=null;
			}
		}
	}

	public String getTotalAmount(String batchNo) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("select     sum(t.excel_amount)+sum(t.EXCEL_BAN_FEE) as  excelAmount  " );
		sb.append("FROM  aisuser.fi_deliverycost_excel t ");
		sb.append("WHERE   t.batch_no=:batchNo 	");
		return sb.toString();
	}
}

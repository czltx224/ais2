package com.xbwl.oper.fax.service.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import oracle.jdbc.OracleTypes;

import org.apache.struts2.ServletActionContext;
import org.hibernate.transform.Transformers;
import org.ralasafe.WebRalasafe;
import org.ralasafe.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.log.anno.ModuleName;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.orm.Page;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.common.utils.DoubleUtil;
import com.xbwl.common.utils.LogType;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.cus.service.IMarketTargetService;
import com.xbwl.entity.BasCusService;
import com.xbwl.entity.CtEstimate;
import com.xbwl.entity.FiIncome;
import com.xbwl.entity.OprFaxIn;
import com.xbwl.entity.OprRequestDo;
import com.xbwl.entity.OprStatus;
import com.xbwl.entity.OprValueAddFee;
import com.xbwl.entity.SysDepart;
import com.xbwl.finance.dao.IFiIncomeDao;
import com.xbwl.finance.dto.IFiInterface;
import com.xbwl.finance.dto.impl.FiInterfaceProDto;
import com.xbwl.oper.edi.service.ICtEstimateService;
import com.xbwl.oper.fax.dao.IOprFaxInDao;
import com.xbwl.oper.fax.service.IOprFaxInService;
import com.xbwl.oper.fax.vo.EdiFaxInVo;
import com.xbwl.oper.fax.vo.FaxReturnMsg;
import com.xbwl.oper.fax.vo.OprFaxInQueryVo;
import com.xbwl.oper.reports.util.AppendConditions;
import com.xbwl.oper.stock.service.IOprHistoryService;
import com.xbwl.oper.stock.service.IOprStatusService;
import com.xbwl.rbac.Service.IDepartService;
import com.xbwl.rbac.Service.IUserService;
import com.xbwl.sys.service.IBasCusService;
import com.xbwl.sys.service.ICustomerService;

/**
 * author CaoZhili time Jul 6, 2011 2:37:00 PM
 */
@Service("oprFaxInServiceImpl")
@Transactional(rollbackFor = { Exception.class })
public class OprFaxInServiceImpl extends BaseServiceImpl<OprFaxIn, Long>
		implements IOprFaxInService {
	
	@Resource(name = "oprFaxInHibernateDaoImpl")
	private IOprFaxInDao oprFaxInDao;
	
	@Resource(name="oprStatusServiceImpl")
	private IOprStatusService oprStatusService;
	
	@Resource(name="userServiceImpl")
	private IUserService userService;
	
	@Resource(name="basCusServiceImpl")
	private IBasCusService basCusService;
	
	@Value("${enterStock.requestStage}")
	private String requestStage;
	
	@Value("${oprFaxInServiceImpl.consignee}")
	private String consignee;
	
	@Value("${oprOvermemoServiceImpl.log_faxCancel}")
	private Long log_faxCancel;//到车确认
	
	@Value("${oprFaxInServiceImpl.log_ediFailure}")
	private Long log_ediFailure;//中转EDI写入失败
	
	@Resource(name="customerServiceImpl")
	private ICustomerService customerService;
	
	@Value("${oprFaxInServiceImpl.cp}")
	private String cp;
	
	@Resource(name="appendConditions")
	private AppendConditions appendConditions;
	
	
	//传真作废 调用财务接口
	@Resource(name="fiInterfaceImpl")
	private IFiInterface fiInterface;
	
	private JdbcTemplate jdbcTemplate;
	
	@Resource(name = "oprHistoryServiceImpl")
	private IOprHistoryService oprHistoryService;
	
	@Resource(name="ctEstimateServiceImpl")
	private ICtEstimateService ctEstimateService;
	
	@Resource(name="departServiceImpl")
	private IDepartService departService;
	
	@Resource(name="fiIncomeHibernateDaoImpl")
	private IFiIncomeDao fiIncomeDao;
	
	@Resource(name="marketTargetServiceImpl")
	private IMarketTargetService marketTargetService;
	
	@Override
	public IBaseDAO<OprFaxIn, Long> getBaseDao() {
		return this.oprFaxInDao;
	}
	@Autowired
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	@ModuleName(value="根据主单号和航班号查询主单信息",logType=LogType.buss)
	public Page<OprFaxIn> findMainMsgByMainNo(String mainNo, String fightNo,
			Page page) throws Exception {
		User user=WebRalasafe.getCurrentUser(Struts2Utils.getRequest());
		Page<OprFaxIn> page1=null;
		if(fightNo==null||fightNo.equals("")){
			page1=this.findPage(page,"select ofi from OprFaxIn ofi,OprStatus os where ofi.dno=os.dno and os.reachStatus=0 and ofi.flightMainNo=? and ofi.inDepartId=?",mainNo,Long.valueOf(user.get("bussDepart")+""));
		}else{
			page1=this.findPage(page,"select ofi from OprFaxIn ofi,OprStatus os where os.reachStatus=0 and ofi.flightMainNo=? and ofi.flightNo=? and ofi.dno=os.dno and ofi.inDepartId=?",mainNo, fightNo,Long.valueOf(user.get("bussDepart")+""));
		}
		return page1;

	}
	
	@ModuleName(value="根据配送单号查询传真表信息",logType=LogType.buss)
	public List<Map> findFaxVoByDno(Long dno) throws Exception {
		User user=WebRalasafe.getCurrentUser(Struts2Utils.getRequest());
		Long departId  = Long.valueOf(user.get("bussDepart")+"");
		StringBuffer sql = new StringBuffer();
		sql.append("select  f.d_no as dno,f.cp_Name as cpName,f.flight_No as flightNo,f.flight_time as flighttime,f.flight_Main_No as flightMainNo,")
			.append("f.sub_No as subNo,f.receipt_Type as receiptType,f.piece as piece,f.cq_Weight as cqWeight," )
			.append("s.REACH_STATUS as status, f.goods as goods , r.request as request,r.is_Opr as isOpr,")
			.append("f.addr as addr,f.consignee as consignee,f.consignee_tel as consigneeTel,f.bulk as bulk," )
			.append("f.distribution_Mode as distributionMode,f.take_Mode as takeMode,r.remark as remark,f.cus_Id as cusId," )
			.append("REQUESTDOID as REQUESTDOID,NVL(od.REALPIECE,0) as REALPIECE,f.distribution_depart_id,f.distribution_depart,")
			.append("f.end_depart_id,f.end_depart")
			.append(" from Opr_Fax_In f,")
			.append("(select max(tp.id) as REQUESTDOID, tp.d_no, tp.request,  tp.is_opr,tp.remark")
			.append(" from OPR_REQUEST_DO tp where tp.REQUEST_STAGE = '").append(this.requestStage).append("'")
			.append("  group by tp.request, tp.is_opr, tp.remark, tp.d_no) r,")
			.append(" (select p.d_no,sum(nvl(p.real_piece,0)) as REALPIECE ")
            .append(" from opr_overmemo_detail p  where  p.d_no=").append(dno)
            .append(" and P.STATUS in(1,2) AND p.depart_id =").append(departId)
            .append(" group by p.d_no) od,")
			.append(" opr_status s where f.d_no=r.d_no(+) and f.d_no=s.d_no(+) ")
			.append(" and f.d_no=od.d_no(+) and f.status=1");
		sql.append(" and (f.in_depart_id =").append(departId)//添加部门限制，当前部门完全无关的不需要显示
		   .append(" or f.end_depart_id=").append(departId)
		   .append(" or f.distribution_depart_id=").append(departId)
		   .append(" )");
			//.append(" and s.out_status in (0,2)");
		
		sql.append(" and f.d_no=").append(dno);
		
		List<Map> list = this.oprFaxInDao.getSession().createSQLQuery(sql.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return list;
	}
	@ModuleName(value="保存传真信息",logType=LogType.buss)
	public FaxReturnMsg saveFaxDetail(OprFaxIn ofi,List<OprValueAddFee> list,List<OprRequestDo> requestList) throws Exception {
		
		FaxReturnMsg msg = new FaxReturnMsg();
		
		Calendar calendar = Calendar.getInstance();
		if(ofi.getDno() == null){
			calendar.setTime(ofi.getCreateTime());
		}else{
			calendar.setTime(ofi.getUpdateTime());
		}
		
		Timestamp timestamp = new Timestamp(calendar.getTime().getTime());
		Connection con=null;
		try{
		con=SessionFactoryUtils.getDataSource(oprFaxInDao.getSessionFactory()).getConnection();
		//Long startTime = System.currentTimeMillis();
		CallableStatement cs=con.prepareCall("{call pro_fax_in(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
		if(ofi.getFaxMainId()==null){
			cs.setLong(1, 0);
		}else{
			cs.setLong(1, ofi.getFaxMainId());
		}
		if(ofi.getCusId()==null||ofi.getCusId().equals("null")){
			cs.setLong(2, 0);
		}else{
			cs.setLong(2, ofi.getCusId());
		}
		cs.setString(3, ofi.getCpName());
		cs.setString(4, ofi.getFlightNo());
		cs.setDate(5, new java.sql.Date(ofi.getFlightDate().getTime()));
		cs.setString(6, ofi.getFlightTime());
		cs.setString(7, ofi.getTrafficMode());
		cs.setString(8, ofi.getFlightMainNo());
		cs.setString(9, ofi.getSubNo());
		cs.setString(10, ofi.getDistributionMode());
		cs.setString(11, ofi.getTakeMode());
		cs.setString(12, ofi.getReceiptType());
		cs.setString(13, ofi.getConsignee());
		cs.setString(14, ofi.getConsigneeTel());
		cs.setString(15, ofi.getCity());
		cs.setString(16, ofi.getTown());
		cs.setString(17, ofi.getStreet());
		cs.setString(18,ofi.getAddr());
		cs.setString(19,ofi.getAreaType());
		cs.setString(20,ofi.getAreaRank());
		cs.setString(21,ofi.getGoods());
		cs.setString(22,ofi.getValuationType());
		cs.setLong(23,ofi.getPiece());
		cs.setDouble(24,ofi.getCqWeight());
		cs.setDouble(25,ofi.getCusWeight());
		if(ofi.getBulk()==null){
			cs.setDouble(26,0);
		}else{
			cs.setDouble(26,ofi.getBulk());
		}
		cs.setString(27,ofi.getInDepart());
		cs.setString(28,ofi.getCurDepart());
		cs.setString(29,ofi.getEndDepart());
		cs.setString(30,ofi.getDistributionDepart());
		cs.setLong(31,0);//绿色通道
		if(ofi.getUrgentService()==null){
			cs.setLong(32,0);
		}else{
			cs.setLong(32,ofi.getUrgentService());
		}
		if(ofi.getWait()==null){
			cs.setLong(33,0);
		}else{
			cs.setLong(33,ofi.getWait());
		}
		if(ofi.getSonderzug()==null){
			cs.setLong(34,0);
		}else{
			cs.setLong(34,ofi.getSonderzug());
		}
		if(ofi.getSonderzugPrice()==null){
			cs.setDouble(35,0);
		}else{
			cs.setDouble(35,ofi.getSonderzugPrice());
		}
		cs.setString(36,ofi.getCarType());
		cs.setString(37,ofi.getRoadType());
		cs.setString(38,ofi.getRemark());
		cs.setLong(39,1L);
		cs.setString(40,ofi.getBarCode());
		cs.setString(41,ofi.getGowhere());
		if(ofi.getPaymentCollection()==null){
			cs.setDouble(42,0);
		}else{
			cs.setDouble(42,ofi.getPaymentCollection());
		}
		if(ofi.getTraFeeRate()==null){
			cs.setDouble(43,0);
		}else{
			cs.setDouble(43,ofi.getTraFeeRate());
		}
		if(ofi.getTraFee()==null){
			cs.setDouble(44,0);
		}else{
			cs.setDouble(44,ofi.getTraFee());
		}
		if(ofi.getCpRate()==null){
			cs.setDouble(45,0);
		}else{
			cs.setDouble(45,ofi.getCpRate());
		}
		if(ofi.getCpFee()==null){
			cs.setDouble(46,0);
		}else{
			cs.setDouble(46,ofi.getCpFee());
		}
		if(ofi.getCpValueAddFee()==null){
			cs.setDouble(47,0);
		}else{
			cs.setDouble(47,ofi.getCpValueAddFee());
		}
		if(ofi.getCusValueAddFee()==null){
			cs.setDouble(48,0);
		}else{
			cs.setDouble(48,ofi.getCusValueAddFee());
		}
		if(ofi.getConsigneeRate()==null){
			cs.setDouble(49,0);
		}else{
			cs.setDouble(49,ofi.getConsigneeRate());
		}
		if(ofi.getConsigneeFee()==null){
			cs.setDouble(50,0);
		}else{
			cs.setDouble(50,ofi.getConsigneeFee());
		}
		cs.setString(51,ofi.getWhoCash());
		cs.setString(52,"传真录入");
		cs.setString(53,ofi.getCreateName());
		cs.setTimestamp(54,timestamp);
		cs.setString(55,ofi.getUpdateName());
		cs.setTimestamp(56,timestamp);
		//时间戳，数字158745214无其它意义
		cs.setString(57,"158745214");
		//cs.setString(58, "a");
		//cs.setString(59, "b");
		StringBuffer addFeeStr=new StringBuffer();
		StringBuffer requStr=new StringBuffer();
		if(list!=null){
			for(int i=0;i<list.size();i++){
				OprValueAddFee ovaf=list.get(i);
				addFeeStr.append(ovaf.getFeeName()+(char)17+ovaf.getFeeCount()+(char)17+ovaf.getPayType());
				if(i!=list.size()-1){
					addFeeStr.append((char)18);
				}
			}
			cs.setString(58, addFeeStr.toString());
		}else{
			cs.setString(58,"");
		}
		if(requestList!=null){
			for(int i=0;i<requestList.size();i++){
				OprRequestDo od=requestList.get(i);
				requStr.append(od.getRequestStage()+(char)17+od.getRequest()+(char)17+od.getRequestType());
				if(i!=requestList.size()-1){
					requStr.append((char)18);
				}
			}
			cs.setString(59, requStr.toString());
		}else{
			cs.setString(59, "");
		}
		/*
		if(list==null){
			con=getNativeConnection(con);
			ArrayDescriptor arraydesc = new ArrayDescriptor("ADD_FEE_TABLE", con);
			cs.setArray(58,new ARRAY(arraydesc,con,new Object[]{}));
			cs.setString(58, "");
		}else{
			Array array=getArray("ADD_FEE_OBJECT","ADD_FEE_TABLE", list, con);
			cs.setArray(58, array);
		}
		if(requestList==null){
			con=getNativeConnection(con);
			ArrayDescriptor arraydesc = new ArrayDescriptor("REQUEST_TABLE", con);
			cs.setArray(59,new ARRAY(arraydesc,con,new Object[]{}));
		}else{
			Array array=getReqArray("REQUEST_OBJECT","REQUEST_TABLE", requestList, con);
			cs.setArray(59, array);
		}*/
		
		if(ofi.getDistributionDepartId()==null||ofi.getDistributionDepartId()==0){
			cs.setLong(60, ofi.getCurDepartId());
			cs.setString(30, ofi.getCurDepart());
		}else{
			cs.setLong(60, ofi.getDistributionDepartId());
		}
		if(ofi.getEndDepartId()==null||ofi.getEndDepartId()==0){
			cs.setLong(61, ofi.getCurDepartId());
			cs.setString(29,ofi.getCurDepart());
		}else{
			cs.setLong(61, ofi.getEndDepartId());
		}
		cs.setLong(62, ofi.getCurDepartId());
		cs.setLong(63, ofi.getInDepartId());
		if(ofi.getDno()==null){
			cs.setLong(64, 0);
		}else{
			cs.setLong(64, ofi.getDno());
		}
		if(ofi.getNormSonderzugPrice()==null){
			cs.setDouble(65, 0);
		}else{
			cs.setDouble(65, ofi.getNormSonderzugPrice());
		}
		if(ofi.getNormCpRate()==null){
			cs.setDouble(66, 0);
		}else{
			cs.setDouble(66, ofi.getNormCpRate());
		}
		if(ofi.getNormConsigneeRate()==null){
			cs.setDouble(67, 0);
		}else{
			cs.setDouble(67, ofi.getNormConsigneeRate());
		}
		if(ofi.getNormTraRate()==null){
			cs.setDouble(68, 0);
		}else{
			cs.setDouble(68, ofi.getNormTraRate());
		}
		if(ofi.getGoWhereId()==null){
			cs.setLong(69, 0);
		}else{
			cs.setLong(69, ofi.getGoWhereId());
		}
		cs.setString(70, ofi.getCusDepartCode());
		if(ofi.getCpSonderzugPrice()==null){
			cs.setDouble(71, 0);
		}else{
			cs.setDouble(71, ofi.getCpSonderzugPrice());
		}
		cs.registerOutParameter(72,Types.VARCHAR);
		cs.registerOutParameter(73,Types.INTEGER);
		cs.registerOutParameter(74,Types.INTEGER);
		cs.executeUpdate();
		String returnMsg=cs.getString(72);
		Long returnDno=cs.getLong(73);
		Long returnConsigneeId=cs.getLong(74);
		
		msg.setConsigneeId(returnConsigneeId);
		msg.setDno(returnDno);
		msg.setReturnMsg(returnMsg);
		
		//Long endTime = System.currentTimeMillis();
		//logger.debug("传真录入存储过程执行时间：");
		//logger.debug("{}",endTime - startTime);
		
		return msg;
		}catch(Exception e){
			e.printStackTrace();
			throw new ServiceException(e);
		}finally{
			if(con!=null){
				con.close();
			}
		}
	}
	/*
	//将增值服务费的List转换成ORACLE数组
	private  ARRAY getArray(String OracleObj, String OracleTbl,
			List<OprValueAddFee> alist, Connection conn) throws Exception {
		conn=getNativeConnection(conn);
		ARRAY list = null;
		if (alist != null && alist.size() > 0) {
			StructDescriptor structdesc = new StructDescriptor(OracleObj, conn);
			STRUCT[] structs = new STRUCT[alist.size()];
			Object[] result = null;
			
			for (int i = 0; i < alist.size(); i++) {
				result = new Object[3];
				result[0] = alist.get(i).getFeeName();
				result[1] =alist.get(i).getFeeCount();
				result[2] = alist.get(i).getPayType();

				structs[i] = new STRUCT(structdesc, conn, result);
			}
			ArrayDescriptor arraydesc = new ArrayDescriptor(OracleTbl, conn);
			list = new ARRAY(arraydesc, conn, structs);
		}
		return list;
	}
	//将个性化要求的list转换成ORACLE数组
	private  ARRAY getReqArray(String OracleObj, String OracleTbl,
			List<OprRequestDo> alist, Connection conn) throws Exception {
		conn=getNativeConnection(conn);
		ARRAY list = null;
		if (alist != null && alist.size() > 0) {
			StructDescriptor structdesc = new StructDescriptor(OracleObj, conn);
			STRUCT[] structs = new STRUCT[alist.size()];
			Object[] result = null;
			
			for (int i = 0; i < alist.size(); i++) {
				result = new Object[3];
				result[0] = alist.get(i).getRequestStage();
				result[1] =alist.get(i).getRequest();
				result[2] =alist.get(i).getRequestType();
				structs[i] = new STRUCT(structdesc, conn, result);
			}
			ArrayDescriptor arraydesc = new ArrayDescriptor(OracleTbl, conn);
			list = new ARRAY(arraydesc, conn, structs);
		}
		return list;
	}
	 public  Connection getNativeConnection(Connection con) throws SQLException {
		 	
		 	C3P0NativeJdbcExtractor cp30NativeJdbcExtractor = new C3P0NativeJdbcExtractor(); 
		 	OracleConnection connection=(OracleConnection)cp30NativeJdbcExtractor.getNativeConnection(con);

		 	return connection;
		 }

	 */
	public String  getSqlHaveGoodsNoReceipt(Map<String, String> map)
			throws Exception {
	
 		String EQS_flightNo = map.get("EQS_flightNo");
 		String EQS_flightMainNo = map.get("EQS_flightMainNo");
 		
		StringBuffer sb=new StringBuffer();
		
		sb.append("select *from (SELECT  t0.D_NO  AS dno , t0.CP_NAME  AS cpName , t0.FLIGHT_NO  AS  ")
		  .append("flightNo , t0.FLIGHT_MAIN_NO  AS flightMainNo , t0.SUB_NO  AS  " )
		  .append("subNo , t0.DISTRIBUTION_MODE  AS distributionMode , " )
		  .append(" t0.TAKE_MODE || '/' ||t0.DISTRIBUTION_MODE  AS takeMode , t0.CONSIGNEE || '/' ||t0.CONSIGNEE_TEL  AS consignee  , t0.CONSIGNEE_TEL  AS  " )
		  .append("consigneeTel , t0.ADDR  AS addr , t0.PIECE  AS piece ,   " )
		  .append("t0.CQ_WEIGHT  AS cqWeight , t0.IN_DEPART_ID  AS inDepartId , t0.CUS_ID    " )
		  .append(	"AS cusId , t0.TRAFFIC_MODE  AS trafficMode , t1.REACH_STATUS  AS reachStatus  " )
		  .append(	"FROM  aisuser.OPR_FAX_IN t0 , aisuser.OPR_STATUS t1 ")
		  .append("WHERE  (   t0.D_NO  =  t1.D_NO(+) and t0.status=:EQL_faxStatus ) ) where t0.status=1");
		
		sb.append(" and inDepartId = :EQL_inDepartId");
		sb.append(" and reachStatus = :EQL_reachStatus");
		
		if(null!=EQS_flightMainNo && !"".equals(EQS_flightMainNo)){
			sb.append(" and flightMainNo = :EQS_flightMainNo");
		}
		
		if(null!=EQS_flightNo && !"".equals(EQS_flightNo)){
			sb.append(" and flightNo = :EQS_flightNo");
			sb.append(" and piece =:EQL_piece");
		}
		
		return sb.toString();
	}

	public Page findTakeGoods(Page page,Long cusId,String flightNo,String startTime,String endTime,Long departId,String flightNos,Long isSonderzug) throws Exception {
		StringBuffer sb=new StringBuffer("select bf.standard_starttime,bf.standard_endtime,ofi.sonderzug,to_char(ofi.flight_date,'yyyy-mm-dd') flight_date,c.cus_Name,ofi.flight_No,count(*) as ticketNum,sum(ofi.piece) as piece,sum(ofi.cq_Weight) as cqWeight from Opr_Fax_In ofi,Bas_Flight bf,Customer c,opr_status os where ofi.flight_No=bf.flight_Number(+) and bf.cus_Id=c.id(+) and ofi.d_no=os.d_no and os.reach_status=0 and ofi.flight_date>=sysdate-7 and ofi.flight_date<=sysdate+7 ");
		Map map=new HashMap();
		if(isSonderzug != null && !"".equals(isSonderzug)){
			sb.append(" and ofi.sonderzug=:isSonderzug");
			map.put("isSonderzug", isSonderzug);
		}
		if(cusId!=null){
			sb.append(" and c.id=:cusId ");
			map.put("cusId", cusId);
		}
		if(flightNo!=null&&!flightNo.equals("")){
			map.put("flightNo", flightNo);
			sb.append("and ofi.flight_No=:flightNo ");
		}
		if(departId!=null){
			sb.append(" and ofi.in_Depart_Id=:departId ");
			map.put("departId", departId);
		}
		if(flightNos!=null){
			String[] str=flightNos.split(",");
			StringBuffer fns=new StringBuffer("");
			for (int i = 0; i < str.length; i++) {
				fns.append("'"+str[i]+"'");
				if(i!=str.length-1){
					fns.append(",");
				}
			}
			sb.append(" and ofi.flight_No in ("+fns+")");
		}
		if(startTime!=null&&!"".equals(startTime)){
			sb.append(" and bf.standard_endtime>=:startTime");
			map.put("startTime", startTime);
		}
		if(endTime!=null&&!"".equals(endTime)){
			sb.append(" and bf.standard_endtime<=:endTime");
			map.put("endTime", endTime);
		}
		sb.append(" group by ofi.flight_No,ofi.sonderzug,c.cus_name,ofi.flight_date,bf.standard_starttime,bf.standard_endtime order by ofi.flight_date desc");
		return this.getPageBySqlMap(page, sb.toString(), map);
	}

	public Page fnSumInfo(Page page, Long cusId, String flightNo,
			String startTime, String endTime,Long departId,Long isSonderzug) throws Exception {
		StringBuffer sb=new StringBuffer("select sum(ofi.sonderzug) sonderzug,count(*) as ticketNum,sum(ofi.piece) as piece,sum(ofi.cq_Weight) as cqWeight from Opr_Fax_In ofi,Bas_Flight bf,Customer c,opr_status os where ofi.flight_No=bf.flight_Number(+) and bf.cus_Id=c.id(+) and os.d_no=ofi.d_no and os.reach_status=0 and ofi.flight_date>=sysdate-7 and ofi.flight_date<=sysdate+7 ");
		Map map=new HashMap();
		if(isSonderzug != null && !"".equals(isSonderzug)){
			sb.append(" and ofi.sonderzug=:isSonderzug");
			map.put("isSonderzug", isSonderzug);
		}
		if(cusId!=null){
			sb.append(" and c.id=:cusId ");
			map.put("cusId", cusId);
		}
		if(flightNo!=null&&!flightNo.equals("")){
			map.put("flightNo", flightNo);
			sb.append("and ofi.flight_No=: flightNo ");
		}
		if(startTime!=null&&!"".equals(startTime)){
			sb.append(" and bf.standard_endtime>=:startTime");
			map.put("startTime", startTime);
		}
		if(endTime!=null&&!"".equals(endTime)){
			sb.append(" and bf.standard_endtime<=:endTime");
			map.put("endTime", endTime);
		}
		sb.append(" and ofi.in_Depart_Id=:departId ");
		map.put("departId", departId);

		return this.getPageBySqlMap(page, sb.toString(), map);
	}
	
	@ModuleName(value="综合查询，查询数据",logType=LogType.buss)
	public Page queryCondition(Long type,Page page, OprFaxInQueryVo oprFaxInQueryVo,String sort,String dir)throws Exception {
		User user = WebRalasafe.getCurrentUser(Struts2Utils.getRequest());
		Long bussDepartId = Long.parseLong(user.get("bussDepart") + "");
		SimpleDateFormat dateFormat  = new SimpleDateFormat("yyyy-MM-dd");
		Connection con=null;
		ResultSet rs = null;
		CallableStatement cs=null;
		try {
			
			    con = SessionFactoryUtils.getDataSource(oprFaxInDao.getSessionFactory()).getConnection();
				cs = con.prepareCall("{call PRO_QUERY_CONDITION_OPR_FAX_IN(?,?,?,?,?  ,?,?,?,?,?  ,?,?,?,?,?  ,?,?,?,?,?,   ?,?,?,?,?,  ?,?,?,?,?  ,?,?,?,?,?,  ?,?  ,?,?,? ,?,?,?  ,?,? ,?,?,? ,?,?,?,?,?,?,?,?,?,? ,?,?,?,   ?,?,?,?,?)}");
				
				cs.setString(1, oprFaxInQueryVo.getDno()==null?null:oprFaxInQueryVo.getDno() + "");
				if (oprFaxInQueryVo.getConsignee() == null|| "".equals(oprFaxInQueryVo.getConsignee())) {
					cs.setString(2, null);
				} else {
					cs.setString(2, "%"+ oprFaxInQueryVo.getConsignee() + "%");
				}
		//		cs.setString(2, oprFaxInQueryVo.getConsignee()==null?null:("".equals(oprFaxInQueryVo.getConsignee())?null:oprFaxInQueryVo.getConsignee()+"%"));
				
				cs.setString(3, oprFaxInQueryVo.getStartDate()==null?null:dateFormat.format(oprFaxInQueryVo.getStartDate()));
				cs.setString(4, oprFaxInQueryVo.getEndDate()==null?null:dateFormat.format(oprFaxInQueryVo.getEndDate()));
				if ("".equals(oprFaxInQueryVo.getFlightMainNo())||oprFaxInQueryVo.getFlightMainNo()==null) {
					cs.setString(5, null);
				} else {
					cs.setString(5,  "%"+oprFaxInQueryVo.getFlightMainNo()+"%");
				}
		
				if ("".equals(oprFaxInQueryVo.getConsigneePhone())||oprFaxInQueryVo.getConsigneePhone()==null) {
					cs.setString(6, null);
				} else {
					cs.setString(6,  "%"+oprFaxInQueryVo.getConsigneePhone()+"%");
				}
				if ("".equals(oprFaxInQueryVo.getSubNo())||oprFaxInQueryVo.getSubNo()==null) {
					cs.setString(7, null);
				} else {
					cs.setString(7,   "%"+oprFaxInQueryVo.getSubNo()+ "%");
				}
				if (oprFaxInQueryVo.getAddr() == null||"".equals(oprFaxInQueryVo.getAddr())) {
					cs.setString(8, oprFaxInQueryVo.getAddr());
				} else {
					cs.setString(8, "%"+ oprFaxInQueryVo.getAddr() + "%");
				}
				if ("".equals(oprFaxInQueryVo.getFlightNo())||oprFaxInQueryVo.getFlightNo()==null) {
					cs.setString(9, null);
				} else {
					cs.setString(9,  "%"+oprFaxInQueryVo.getFlightNo()+ "%");
				}
				if (oprFaxInQueryVo.getCpName() == null|| "".equals(oprFaxInQueryVo.getCpName())) {
					cs.setString(10, null);
				} else {
					cs.setString(10, "%"+ oprFaxInQueryVo.getCpName() + "%");
				}
				
				if (oprFaxInQueryVo.getCustomerService() == null|| "".equals(oprFaxInQueryVo.getCustomerService())) {
					cs.setString(11, null);
				} else {
					cs.setString(11, "%"+ oprFaxInQueryVo.getCustomerService() + "%");
				}
				cs.setString(12, oprFaxInQueryVo.getDistribution()==null?null:oprFaxInQueryVo.getDistribution() + "");
				if (oprFaxInQueryVo.getGoWhere() == null|| "".equals(oprFaxInQueryVo.getGoWhere())) {
					cs.setString(13, null);
				} else {
					cs.setString(13,  "%"+oprFaxInQueryVo.getGoWhere() + "%");
				}
				cs.setString(14, oprFaxInQueryVo.getSignStatus()==null?null:oprFaxInQueryVo.getSignStatus() + "");
				cs.setString(15, oprFaxInQueryVo.getDelStatus()==null?null:oprFaxInQueryVo.getDelStatus() + "");
			
				cs.setString(16, oprFaxInQueryVo.getDoGoods()==null?null:oprFaxInQueryVo.getDoGoods() + "");
				cs.setString(17, oprFaxInQueryVo.getGoodsStatus()==null?null:oprFaxInQueryVo.getGoodsStatus() + "");
				cs.setString(18, oprFaxInQueryVo.getGoodsStatusTwo()==null?null:oprFaxInQueryVo.getGoodsStatusTwo() + "");
				cs.setString(19, oprFaxInQueryVo.getIsNoticeStatus()==null?null:oprFaxInQueryVo.getIsNoticeStatus() + "");
				cs.setString(20, oprFaxInQueryVo.getIsUrgentStatus()==null?null:oprFaxInQueryVo.getIsUrgentStatus() + "");
				
				cs.setString(21, oprFaxInQueryVo.getDoMoneyStatus()==null?null:oprFaxInQueryVo.getDoMoneyStatus() + "");
				cs.setString(22, oprFaxInQueryVo.getStartDepartId()==null?null:oprFaxInQueryVo.getStartDepartId() + "");
				cs.setString(23, oprFaxInQueryVo.getFaxInStartDate()==null?null:dateFormat.format(oprFaxInQueryVo.getFaxInStartDate()) );
				cs.setString(24, oprFaxInQueryVo.getFaxInEndDate()==null?null:dateFormat.format(oprFaxInQueryVo.getFaxInEndDate()) );
				cs.setString(25, oprFaxInQueryVo.getDoGoodStartDate()==null?null:dateFormat.format(oprFaxInQueryVo.getDoGoodStartDate()) );
		
				cs.setString(26, oprFaxInQueryVo.getDoGoodEndDate()==null?null:dateFormat.format(oprFaxInQueryVo.getDoGoodEndDate()));
				cs.setString(27, oprFaxInQueryVo.getSonderzug()==null?null:oprFaxInQueryVo.getSonderzug() + "");
				cs.setString(28, oprFaxInQueryVo.getSignDanStatus()==null?null:oprFaxInQueryVo.getSignDanStatus() + "");
				cs.setString(29, oprFaxInQueryVo.getDistributionDepartId()==null?null:oprFaxInQueryVo.getDistributionDepartId() + "");
				cs.setString(30, oprFaxInQueryVo.getIsDoToStatus()==null?null:oprFaxInQueryVo.getIsDoToStatus() + "");

				cs.setString(31, oprFaxInQueryVo.getNoticeGoodStatus()==null?null:oprFaxInQueryVo.getNoticeGoodStatus() + "");
				cs.setString(32, oprFaxInQueryVo.getOvermemo()==null?null:oprFaxInQueryVo.getOvermemo() + "");
				cs.setString(33, oprFaxInQueryVo.getServiceDepartName()==null?null:oprFaxInQueryVo.getServiceDepartName() + "");
				cs.setString(34, oprFaxInQueryVo.getEndDepartId()==null?null:oprFaxInQueryVo.getEndDepartId() + "");
				if ("".equals(oprFaxInQueryVo.getQueryCondition())||
							oprFaxInQueryVo.getQueryCondition()==null||
							"".equals(oprFaxInQueryVo.getQueryConditionSelect())||oprFaxInQueryVo.getQueryConditionSelect()==null) {
					cs.setString(35, null);
					cs.setString(36, null);
				} else {
					cs.setString(35, oprFaxInQueryVo.getQueryCondition());
					cs.setString(36, oprFaxInQueryVo.getQueryConditionSelect());
				}
		
				
				cs.registerOutParameter(36, Types.VARCHAR);
				cs.registerOutParameter(37, OracleTypes.CURSOR);
		
				cs.setLong(38, oprFaxInQueryVo.getRightDepartId());
				cs.setLong(39, oprFaxInQueryVo.getRightDepartId());
				cs.setLong(40, page.getLimit());
				cs.setLong(41, page.getPageNo());
				
				cs.setLong(56, oprFaxInQueryVo.getRightDepartId());
				cs.setLong(57,bussDepartId);
				cs.setLong(58,type);
				cs.setString(59, oprFaxInQueryVo.getFiTraAudit()==null?null:oprFaxInQueryVo.getFiTraAudit()+"");

				
				cs.registerOutParameter(42, Types.INTEGER);
				cs.registerOutParameter(43, Types.INTEGER);
		
				cs.registerOutParameter(44, Types.INTEGER);
				cs.registerOutParameter(45, Types.INTEGER);
				cs.registerOutParameter(46, Types.INTEGER);
				cs.registerOutParameter(47, Types.DOUBLE);
				cs.registerOutParameter(48, Types.DOUBLE);
				cs.registerOutParameter(49, Types.DOUBLE);
				cs.registerOutParameter(50, Types.DOUBLE);
				cs.registerOutParameter(51, Types.DOUBLE);
				cs.registerOutParameter(52, Types.DOUBLE);
				cs.registerOutParameter(53, Types.DOUBLE);
				cs.registerOutParameter(54, Types.DOUBLE);
				cs.registerOutParameter(55, Types.DOUBLE);
				cs.registerOutParameter(62, Types.INTEGER);
				
				cs.registerOutParameter(66, Types.VARCHAR);
				cs.setString(63,sort==null?"dno":sort);  //排序字段
				cs.setString(64,dir==null?"DESC":dir);  //排序方向
				
				if (oprFaxInQueryVo.getRealgowhere() == null|| "".equals(oprFaxInQueryVo.getRealgowhere())) {
					cs.setString(65, null);
				} else {
					cs.setString(65, "%"+ oprFaxInQueryVo.getRealgowhere() + "%");
				}
				
				cs.setString(60, oprFaxInQueryVo.getReturnStatus()==null?null:oprFaxInQueryVo.getReturnStatus()+"");  //返货状态
				if (oprFaxInQueryVo.getTrafficmode() != null&&!"".equals(oprFaxInQueryVo.getTrafficmode())) {   //运输方式
					cs.setString(61, (oprFaxInQueryVo.getTrafficmode() + ""));
				} else {
					cs.setString(61, null);
				}
				Long startTime = System.currentTimeMillis();
				cs.execute();
		
				Long endTime = System.currentTimeMillis();
				Long sLong=endTime-startTime;
		//		System.out.println("存储过程执行时间："+sLong+"^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
				logger.debug("存储过程执行时间：");
				logger.debug("{}",sLong);
				List<OprFaxInQueryVo> list = new ArrayList<OprFaxInQueryVo>();
				if(type==2l){
					OprFaxInQueryVo oqo = new OprFaxInQueryVo();
					oqo.setDno(cs.getLong(44));
					oqo.setPiece(cs.getLong(45));
					oqo.setStockpiece(cs.getLong(46));
					oqo.setBulk(cs.getDouble(47));
					oqo.setCusweight(cs.getDouble(48));
					oqo.setPaymentcollection(cs.getDouble(49));
					oqo.setTotalYuFee(cs.getDouble(50));
					oqo.setTotalDaoFee(cs.getDouble(51));
					oqo.setSonderzugprice(cs.getDouble(52));
					oqo.setCpSonderzugprice(cs.getDouble(55));
					oqo.setTrafee(cs.getDouble(53));
					oqo.setTotalFee(cs.getDouble(54));
					oqo.setTotalCp(cs.getLong(62));
					list.add(oqo);
				}
				
				if(type==1l){
							rs = (ResultSet) cs.getObject(37);
							page.setTotalCount(cs.getLong(42));
							String collectMsg=cs.getString(66);
							while (rs.next()) {
								OprFaxInQueryVo o = new OprFaxInQueryVo();
								o.setDno(rs.getLong("DNO"));
								o.setCpname(rs.getString("CPNAME"));
								o.setGoodsstatus(rs.getString("GOODSSTATUS"));
								o.setFlightTime(rs.getString("FLIGHTTIME"));
								String flightString=o.getFlightTime()==null?"":o.getFlightTime();
								o.setFlightno(rs.getString("FLIGHTNO")==null?"":rs.getString("FLIGHTNO")+"<span style='color:red'>/</span>"+rs.getDate("FLIGHTDATE")+"<span style='color:red'>/</span>"+flightString);
								o.setFlightNo(rs.getString("FLIGHTNO"));
								o.setFlightdate(rs.getTimestamp("FLIGHTDATE"));
								o.setFlightdateString(o.getFlightdate().toString().substring(0,9));
								o.setCurdepart(rs.getString("CURDEPART"));
								o.setPiece(rs.getLong("PIECE"));
								o.setSubno(rs.getString("SUBNO"));
								o.setConsignee(rs.getString("CONSIGNEE"));
								o.setConsigneetel(rs.getString("CONSIGNEETEL"));
								o.setGoods(rs.getString("GOODS"));
								o.setCusweight(rs.getDouble("CUSWEIGHT"));
								o.setConsigneefee(rs.getDouble("CONSIGNEEFEE"));
								o.setCusvalueaddfee(rs.getDouble("CUSVALUEADDFEE"));
								o.setCpvalueaddfee(rs.getDouble("CPVALUEADDFEE"));
								o.setAddr(rs.getString("ADDR"));
								o.setGowhere(rs.getString("GOWHERE"));
								o.setDismode(rs.getString("DISMODE"));
								o.setCreatetime(rs.getTimestamp("CREATETIME"));
								o.setTakemode(rs.getString("TAKEMODE"));
								o.setCustomerservice(rs.getString("CUSTOMERSERVICE"));
								o.setReceipttype(rs.getString("RECEIPTTYPE"));
								o.setUrgentservice(rs.getLong("URGENTSERVICE"));
								o.setCurstatus(rs.getString("CURSTATUS"));
								o.setStockpiece(rs.getLong("STOCKPIECE"));
								o.setStockcreatetime(rs.getTimestamp("STOCKCREATETIME"));
								o.setNodeorder(rs.getLong("NODEORDER"));
								o.setCusadd(rs.getString("CUSADD"));
						//		o.setCustomerName(rs.getString("CUSTOMERNAME"));
								o.setConfirmtime(rs.getTimestamp("CONFIRMTIME"));
								o.setFlightmainno(rs.getString("FLIGHTMAINNO"));
								o.setSumitDate(rs.getTimestamp("SUMITDATE"));
								o.setSignsource(rs.getString("SIGNSOURCE"));
								o.setStatus(rs.getLong("STATUS"));
								o.setNotifystatus(rs.getLong("NOTIFYSTATUS"));
								o.setConfirmstatus(rs.getLong("CONFIRMSTATUS"));
								o.setConfirmname((rs.getLong("CONFIRMNUM")==0?"":(rs.getLong("CONFIRMNUM")+"份/"))+(rs.getString("CONFIRMNAME")==null?"":rs.getString("CONFIRMNAME")));
								
								o.setCashstatus(rs.getLong("CASHSTATUS"));
								o.setCashName(rs.getString("CASHNAME"));
								o.setCashTime(rs.getTimestamp("CASHTIME"));
								
								o.setIsException(rs.getLong("ISEXCEPTION"));
								o.setFisurestatus(rs.getLong("FISURESTATUS"));
								o.setIndepartid(rs.getLong("INDEPARTID"));
								o.setDogooddate(rs.getTimestamp("DOGOODDATE"));
								o.setSonderzug(rs.getLong("SONDERZUG"));
								o.setReachstatus(rs.getLong("REACHSTATUS"));
								o.setGetstatus(rs.getLong("GETSTATUS"));
								o.setOutstatus(rs.getLong("OUTSTATUS"));
								o.setScanstatus(rs.getLong("SCANSTATUS"));
								o.setPrintnum(rs.getLong("PRINTNUM"));
								o.setReachName(rs.getString("REACHNAME"));
								o.setOreachstatus(rs.getLong("OREACHSTATUS"));
								o.setWaitnotice(rs.getLong("WAITNOTICE"));
								o.setOsuoutstatus(rs.getLong("OSUOUTSTATUS"));
								o.setOsuoutstatustime(rs.getDate("OSUOUTSTATUSTIME"));
								o.setCusdepartname(rs.getString("CUSDEPARTNAME"));
								o.setOicreatename(rs.getString("OICREATENAME"));
								o.setCusweight(rs.getDouble("CUSWEIGHT"));
								o.setPaymentcollection(rs.getDouble("PAYMENTCOLLECTION"));
								o.setRemark(rs.getString("REMARK"));
					
								o.setRealgowhere(rs.getString("REALGOWHERE"));
								o.setRealSignMan(rs.getString("SIGNMAN")); 
							//	o.setReplacesign(rs.getString("REPLACESIGN"));
							//	if (o.getReplacesign() != null) {
								//	o.setRealSignMan(o.getReplacesign());
							//	} else {
								//	o.setRealSignMan(o.getSignman() == null ? "" : o.getSignman());
								//}
							//	o.setDiscreatetime(rs.getTimestamp("DISCREATETIME"));
						//		o.setAirportendtime(rs.getTimestamp("AIRPORTENDTIME"));
								o.setTrafficmode(rs.getString("TRAFFICMODE"));
								o.setAirportstarttime(rs.getTimestamp("AIRPORTSTARTTIME"));
							//	o.setCarNoNum(rs.getString("CARNONUM"));
					
								o.setOicartype(rs.getString("OICARTYPE"));
								o.setRoadtype(rs.getString("ROADTYPE"));
								o.setSonderzugprice(rs.getDouble("SONDERZUGPRICE"));
								o.setCity(rs.getString("CITY"));
								o.setTown(rs.getString("TOWN"));
								o.setStreet(rs.getString("STREET"));
								o.setValuationtype(rs.getString("VALUATIONTYPE"));
								o.setBulk(rs.getDouble("BULK"));
					
								o.setCqweight(rs.getDouble("CQWEIGHT"));
								o.setWhocash(rs.getString("WHOCASH"));
								o.setTrafeerate(rs.getDouble("TRAFEERATE"));
								o.setTrafee(rs.getDouble("TRAFEE"));
								o.setCprate(rs.getDouble("CPRATE"));
								o.setCpfee(rs.getDouble("CPFEE"));
								o.setSignStatus(rs.getLong("SIGNSTATUS"));
								
								o.setStockpiece(rs.getLong("STOCKPIECE"));
								o.setCreatetime(rs.getTimestamp("CREATETIME"));
								o.setConsigneerate(rs.getDouble("CONSIGNEERATE"));
								o.setCurdepart(rs.getString("CURDEPART"));
								o.setIndepart(rs.getString("INDEPART"));
								o.setEnddepart(rs.getString("ENDDEPART"));
								
								o.setServiceDepartName(rs.getString("SERVICEDEPARTNAME"));
								o.setAirportstartstatus(rs.getLong("AIRPORTSTARTSTATUS"));
								o.setDoStoreName(rs.getString("DOSTORENAME"));
								o.setDoStoreStatus(rs.getLong("DOSTORESTATUS"));
								o.setDoStoreTime(rs.getTimestamp("DOSTORETIME"));
								o.setDistributiondepartname(rs.getString("DISTRIBUTIONDEPARTNAME"));
								o.setTraPayStatus(rs.getLong("PAYTRA"));
								o.setFiTraAudit(rs.getLong("FEEAUDITSTATUS"));
		
								o.setNoticeGoodStatus(rs.getLong("NOTICEGOODSTATUS"));
								o.setReturnStatus(rs.getLong("RETURNSTATUS"));
								o.setCpSonderzugprice(rs.getDouble("CPSONDERZUGPRICE"));
								o.setIsOprException(rs.getLong("ISOPREXCEPTION"));
								o.setTraPayTime(rs.getDate("PAYTRATIME")==null?null:rs.getDate("PAYTRATIME")+"");
								o.setPhoneNoticeSign(o.getSignStatus()==2l?1l:0l);
								
								o.setConsignee(o.getConsignee()==null?"":o.getConsignee());
								o.setAddr(o.getAddr()==null?"":o.getAddr());
								o.setConsigneetel(o.getConsigneetel()==null?"":o.getConsigneetel());
								o.setGoodStatusColor(rs.getString("GOODSTATUSCOLOR"));
								o.setConsigneeInfoString(o.getConsignee()+ "<span style='color:red'>/</span>" + o.getAddr()+ "<span style='color:red'>/</span>" + o.getConsigneetel());
								
								o.setCusId(rs.getLong("CUSID"));
								o.setGowhereId(rs.getLong("GOWHEREID"));
								o.setDnoIncomeFee(o.getCusvalueaddfee()+o.getCpvalueaddfee()+o.getCpSonderzugprice()+o.getSonderzugprice()+o.getCpfee()+o.getConsigneefee());
								o.setCashMoney(o.getCusvalueaddfee()+o.getSonderzugprice()+o.getConsigneefee()+o.getPaymentcollection());
								o.setCollectMsg(collectMsg);
								o.setRequest(rs.getString("REQUEST"));
								o.setIsUrgentStatus(rs.getLong("ISURGENT"));
								o.setIsFlyLate(rs.getLong("ISFLYLATE"));
								list.add(o);
							}
				}
				page.setResult(list);
				logger.debug("*****" + list.size());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("综合查询存储过程出错了");
		}finally{
			if(rs!=null){
				rs.close();
				rs=null;
			}
			if(cs!=null){
				cs.close();
				cs=null;
			}
			if(con!=null){
				con.close();
				con=null;
			}
		}
		return null;
	}

	public Page findCusInfo(Page page, String conName, String conTel, Long cusId,String type,String consignee1,Date startTime,Date endTime)
			throws Exception {
		Map map=new HashMap();
		StringBuffer sql=new StringBuffer();
		if(type.equals(cp)){
			sql.append("select consignee,consignee_tel,weight,consigneefee,cpfee,costamount,weightrate,sonder,citysend,cityown,paycoll,cpvaladdfee,convaladdfee,");
			sql.append("income-costamount profit,concat(to_char((income-costamount)/decode(income,0,1,income)*100,'990.99'),'%') profitrate from(");
			sql.append("select t.consignee,t.consignee_tel,sum(t.cq_weight) weight,sum(t.consignee_fee) consigneefee,sum(t.cp_fee) cpfee,");
			sql.append("nvl(sum(f.cost_amount),0) costamount,");
			sql.append("concat(to_char(sum(t.cq_weight)/nvl(sumo.sumweight,1)*100,'990.99'),'%') weightrate,");
			sql.append("nvl(sum(case when t.sonderzug=1 then t.cq_weight end),0) sonder,");
			sql.append("nvl(sum(case when t.take_mode='市内送货' and t.distribution_mode='新邦' then t.cq_weight end),0) citysend,");
			sql.append("nvl(sum(case when t.take_mode='市内自提' and t.distribution_mode='新邦' then t.cq_weight end),0) cityown,");
			sql.append("sum(t.payment_collection) paycoll,nvl(sum(t.cp_value_add_fee),0) cpvaladdfee,");
			sql.append("nvl(sum(t.cus_value_add_fee),0) convaladdfee,");
			sql.append("nvl(sum(t.consignee_fee),0)+nvl(sum(t.cp_fee),0)+nvl(sum(case when t.sonderzug=1 then t.cq_weight end),0)+nvl(sum(t.cp_value_add_fee),0)+nvl(sum(t.cus_value_add_fee),0) income ");
			sql.append("from opr_fax_in t,fi_cost f,(select sum(o.cq_weight) sumweight from opr_fax_in o where o.status=1 ");
			if(cusId!=null){
				sql.append("and o.cus_id=:cusId ");
				map.put("cusId", cusId);
			}
			if(startTime!=null){
				String sTime=new SimpleDateFormat("yyyy-MM-dd").format(startTime);
				sql.append("and to_date(to_char(o.create_time,'yyyy-mm-dd'),'yyyy-mm-dd')>=to_date(:startTime,'yyyy-MM-dd')");
				map.put("startTime", sTime);
			}
			if(endTime!=null){
				String eTime=new SimpleDateFormat("yyyy-MM-dd").format(endTime);
				sql.append(" and to_date(to_char(o.create_time,'yyyy-mm-dd'),'yyyy-mm-dd')<=to_date(:endTime,'yyyy-MM-dd')");
				map.put("endTime", eTime);
			}
			sql.append(") sumo where t.d_no=f.d_no(+) and t.status=1 ");
			if(cusId!=null){
				sql.append("and t.cus_id=:cusId ");
				map.put("cusId", cusId);
			}
			if(startTime!=null){
				String sTime=new SimpleDateFormat("yyyy-MM-dd").format(startTime);
				sql.append("and to_date(to_char(t.create_time,'yyyy-mm-dd'),'yyyy-mm-dd')>=to_date(:startTime,'yyyy-MM-dd')");
				map.put("startTime", sTime);
			}
			if(endTime!=null){
				String eTime=new SimpleDateFormat("yyyy-MM-dd").format(endTime);
				sql.append(" and to_date(to_char(t.create_time,'yyyy-mm-dd'),'yyyy-mm-dd')<=to_date(:endTime,'yyyy-MM-dd')");
				map.put("endTime", eTime);
			}
			if(conName!=null&&!conName.equals("")){
				sql.append(" and t.consignee like '%'||:conName||'%' ");
				map.put("conName",conName);
			}
			if(conTel!=null&&!conTel.equals("")){
				sql.append("and t.consignee_tel like :conTel||'%' ");
				map.put("conTel",conTel);
			}
			sql.append("group by t.consignee_tel,t.consignee,sumo.sumweight) order by weightrate desc");
		}else if(type.equals(consignee)){
			sql.append("select cp_name consignee,weight,consigneefee,cpfee,costamount,weightrate,sonder,citysend,cityown,paycoll,cpvaladdfee,convaladdfee,");
			sql.append("income-costamount profit,concat(to_char((income-costamount)/decode(income,0,1,income)*100,'990.99'),'%') profitrate from(");
			sql.append("select t.cus_id,t.cp_name,sum(t.cq_weight) weight,sum(t.consignee_fee) consigneefee,sum(t.cp_fee) cpfee,");
			sql.append("nvl(sum(f.cost_amount),0) costamount,");
			sql.append("concat(to_char(sum(t.cq_weight)/nvl(sumo.sumweight,1)*100,'990.99'),'%') weightrate,");
			sql.append("nvl(sum(case when t.sonderzug=1 then t.cq_weight end),0) sonder,");
			sql.append("nvl(sum(case when t.take_mode='市内送货' then t.cq_weight end),0) citysend,");
			sql.append("nvl(sum(case when t.take_mode='市内自提' then t.cq_weight end),0) cityown,");
			sql.append("sum(t.payment_collection) paycoll,nvl(sum(t.cp_value_add_fee),0) cpvaladdfee,");
			sql.append("nvl(sum(t.cus_value_add_fee),0) convaladdfee,");
			sql.append("nvl(sum(t.consignee_fee),0)+nvl(sum(t.cp_fee),0)+nvl(sum(case when t.sonderzug=1 then t.cq_weight end),0)+nvl(sum(t.cp_value_add_fee),0)+nvl(sum(t.cus_value_add_fee),0) income ");
			sql.append("from opr_fax_in t,fi_cost f,(select sum(o.cq_weight) sumweight from opr_fax_in o where o.consignee=:consignee) sumo where t.d_no=f.d_no(+) and t.consignee=:consignee and t.status=1");
			sql.append("group by t.cus_id,t.cp_name,sumo.sumweight) order by weightrate desc");
			map.put("consignee", consignee1);
		}
		
		return this.getPageBySqlMap(page, sql.toString(), map);
	}
	
	//货量统计（业务费管理）
	public Map<String, Double> countCusGoods(Long customerId, String businessMonth)
			throws Exception {
		Map<String, Double> map= new HashMap<String, Double>();
		StringBuffer sb = new StringBuffer();
		sb.append("select nvl(sum(t.cq_weight),0) as cqWeight,nvl(sum(t.CUS_VALUE_ADD_FEE),0) as cusValueAddFee,")
			 .append(" nvl(sum(t.CONSIGNEE_FEE),0) as consigneeFee,nvl(sum(t.CP_FEE),0) as cpFee, ")
			 .append(" nvl(sum(t.CP_VALUE_ADD_FEE),0) as cpValueAddFee   from opr_fax_in t where t.cus_id=")
			 .append(customerId).append(" and status=1  and to_char(t.create_time,'yyyy-MM')='").append(businessMonth)
			 .append("'");
		
		List<Map> list = this.oprFaxInDao.getSession().createSQLQuery(sb.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		if(list.size()>0){
			 map.put("totalGoods", Double.valueOf(list.get(0).get("CQWEIGHT").toString()));
			 Double totalAmount=0.0;
			 totalAmount=DoubleUtil.add(Double.valueOf(list.get(0).get("CUSVALUEADDFEE").toString()), totalAmount);
			 totalAmount=DoubleUtil.add(Double.valueOf(list.get(0).get("CONSIGNEEFEE").toString()), totalAmount);
			 totalAmount=DoubleUtil.add(Double.valueOf(list.get(0).get("CPFEE").toString()), totalAmount);
			 totalAmount=DoubleUtil.add(Double.valueOf(list.get(0).get("CPVALUEADDFEE").toString()), totalAmount);
			 map.put("totalAmount", totalAmount);
			 return map;
		}
		return null;
	}

	public Page findGoodsAnaly(Page page,String startCount,String endCount,String countRange,Long cusId) throws Exception {
		Map map=new HashMap();
		User user = WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
		map.put("departId", user.get("bussDepart"));
		
		StringBuffer sql=new StringBuffer();
		StringBuffer weiSql = new StringBuffer();
		StringBuffer ticSql = new StringBuffer();
		StringBuffer ticSqlT = new StringBuffer();
		int  count=1;
		int countNum=0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date1=new Date();
		Date date2=new Date();
		Calendar cal=Calendar.getInstance();
		String dateFormat="yyyy-MM-dd";
		//获得 年、月、日、周 统计时的间隔天数
		if(countRange.equals("日")){
			dateFormat="yyyy-MM-dd";
			if(null==startCount || "".equals(startCount)){
				date1=sdf.parse(cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH))+"-"+cal.get(Calendar.DATE));
			}else{
				date1 = sdf.parse(startCount);	
			}
			if(null!=endCount || !"".equals(endCount)){
				date2 = sdf.parse(endCount);
			}
			long l=date2.getTime()-date1.getTime(); 
			
			count=this.appendConditions.panduan(date1, date2, countRange, 60);
		}else if(countRange.equals("月")){
			dateFormat="yyyy-MM";
			sdf = new SimpleDateFormat("yyyy-MM");
			if(null==startCount || "".equals(startCount)){
				date1=sdf.parse(cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)));
			}else{
				date1 = sdf.parse(startCount);	
			}
			if(null!=endCount || !"".equals(endCount)){
				date2 = sdf.parse(endCount);
			}
			count=this.appendConditions.panduan(date1, date2, countRange, 12);
		}else if(countRange.equals("周")){
			dateFormat="WW";
			try{
				count=Integer.parseInt(endCount)+1-Integer.parseInt(startCount);
				if(count<0){
					throw new ServiceException("周数输入异常！");
				}
			}catch (Exception e) {
				throw new ServiceException("周数输入异常！");
			}
			if(count>20){
				throw new ServiceException("按周统计不能超过20周！");
			}
			
			countNum=Integer.parseInt(startCount);
		}else if(countRange.equals("年")){
			dateFormat="yyyy";
			count=Integer.parseInt(endCount)+1-Integer.parseInt(startCount);
			if(count>10){
				throw new ServiceException("按年统计不能超过10个年！");
			}else if(count<1){
				throw new ServiceException("年份不存在负数！");
			}
			countNum=Integer.parseInt(startCount);
		}
		//拼接 年、月、日、周 统计的SQL语句
		if(countRange.equals("日")){
			cal.setTime(date1);
			for(int i=1;i<=count;i++){
				String yy = cal.get(Calendar.YEAR)+"";
				String dd = cal.get(Calendar.DATE)<10?"0"+cal.get(Calendar.DATE):cal.get(Calendar.DATE)+"";
				String mm = cal.get(Calendar.MONTH)+1<10?"0"+(cal.get(Calendar.MONTH)+1):(cal.get(Calendar.MONTH)+1)+"";
				if(i!=1){
					weiSql.append(",");
					ticSql.append(",");
					ticSqlT.append(",");
				}
				weiSql.append("sum(decode(to_date(to_char(t.create_time,'"+dateFormat+"'),'"+dateFormat+"'),")
				      .append("to_date('"+sdf.format(cal.getTime())+"','"+dateFormat+"'),round(nvl(")
				      .append("t.cq_weight")
				      .append(",0),2),0)) ")
				      .append("\"").append(yy).append("-").append(mm).append("-").append(dd).append("\"");
				ticSql.append("count(distinct decode(")
					  .append("\"").append(yy).append("-").append(mm).append("-").append(dd).append("\"")
					  .append(",0,null,")
				      .append("\"").append(yy).append("-").append(mm).append("-").append(dd).append("\"")
				      .append("))")
				      .append(" \"").append(yy).append("-").append(mm).append("-").append(dd).append("\"");
				ticSqlT.append("decode(to_date(to_char(t.create_time,'"+dateFormat+"'),'"+dateFormat+"'),")
				  .append("to_date('"+sdf.format(cal.getTime())+"','"+dateFormat+"'),round(nvl(")
				  .append("t.d_no")
				  .append(",0),2),0) ")
				  .append("\"").append(yy).append("-").append(mm).append("-").append(dd).append("\"");
				cal.set(Calendar.DATE, cal.get(Calendar.DATE)+1);
			}
		}else if(countRange.equals("月")){
			cal.setTime(date1);
			for(int i=1;i<=count;i++){
				String yy = cal.get(Calendar.YEAR)+"";
				String mm = cal.get(Calendar.MONTH)+1<10?"0"+(cal.get(Calendar.MONTH)+1):(cal.get(Calendar.MONTH)+1)+"";
				if(i!=1){
					weiSql.append(",");
					ticSql.append(",");
					ticSqlT.append(",");
				}
				weiSql.append("sum(decode(to_date(to_char(t.create_time,'"+dateFormat+"'),'"+dateFormat+"'),")
				  .append("to_date('"+sdf.format(cal.getTime())+"','"+dateFormat+"'),round(nvl(")
				  .append("t.cq_weight")
				  .append(",0),2),0)) ")
				  .append("\"").append(yy).append("-").append(mm).append("\"");
				ticSql.append("count(distinct decode(")
					  .append("\"").append(yy).append("-").append(mm).append("\"")
					  .append(",0,null,")
					  .append("\"").append(yy).append("-").append(mm).append("\"")
					  .append("))")
					  .append(" \"").append(yy).append("-").append(mm).append("\"");
				ticSqlT.append("decode(to_date(to_char(t.create_time,'"+dateFormat+"'),'"+dateFormat+"'),")
				  .append("to_date('"+sdf.format(cal.getTime())+"','"+dateFormat+"'),round(nvl(")
				  .append("t.d_no")
				  .append(",0),2),0) ")
				  .append("\"").append(yy).append("-").append(mm).append("\"");
				cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)+1);
			}
		}else if(countRange.equals("周") || countRange.equals("年")){
			countNum=Integer.parseInt(startCount);
			for(int i=1;i<=count;i++){
				if(i!=1){
					weiSql.append(",");
					ticSql.append(",");
					ticSqlT.append(",");
				}
			weiSql.append("sum(decode(trunc(to_char(t.create_time,'"+dateFormat+"')),")
				  .append(countNum+",round(nvl(")
				  .append("t.cq_weight")
				  .append(",0),2),0)) ")
				  .append("\"").append(countNum).append(countRange).append("\"");
			ticSql.append("count(distinct decode(")
				  .append("\"").append(countNum).append(countRange).append("\"")
				  .append(",0,null,")
				  .append("\"").append(countNum).append(countRange).append("\"")
				  .append("))")
				  .append(" \"").append(countNum).append(countRange).append("\"");
		   ticSqlT.append("decode(trunc(to_char(t.create_time,'"+dateFormat+"')),")
				  .append(countNum+",round(nvl(")
				  .append("t.d_no")
				  .append(",0),2),0) ")
				  .append("\"").append(countNum).append(countRange).append("\"");
				countNum++;
			}
		}
		ticSqlT.append(" from opr_fax_in t where t.status=1 and t.in_depart_id=:departId ");
		if(cusId!=null){
			ticSqlT.append(" and t.cus_id=:cusId ");
		}
		ticSqlT.append(")");
		
		sql.append("select '重量' counttype,");
		sql.append(weiSql);
		sql.append(" from opr_fax_in t where t.status=1 and t.in_depart_id=:departId ");
		if(cusId!=null){
			sql.append(" and t.cus_id=:cusId");
			map.put("cusId", cusId);
		}
		sql.append(" union ");
		sql.append("select '票数' counttype,");
		sql.append(ticSql);
		sql.append(" from (select ");
		sql.append(ticSqlT);
		return this.getPageBySqlMap(page, sql.toString(), map);
	}

	public void faxCancel(List dnoList) throws Exception {
		OprStatus os=null;
		OprFaxIn oprFaxIn=null;
		User user=WebRalasafe.getCurrentUser(Struts2Utils.getRequest());
		//收入接口所需List
		List<FiInterfaceProDto> incomeList=new ArrayList<FiInterfaceProDto>();
		
		//代收货款、预付款更改所需List
		List<FiInterfaceProDto> list=new ArrayList<FiInterfaceProDto>();
		for (int j = 0; j < dnoList.size(); j++) {
			Long dno=Long.valueOf(dnoList.get(j).toString());
			//List<OprOvermemoDetail> overList = oprOvermemoDetailService.findBy("dno", dno);
			
			os=oprStatusService.findStatusByDno(dno);
			oprFaxIn=oprFaxInDao.get(dno);
			SysDepart sd=departService.getDepartByDepartNo(oprFaxIn.getCusDepartCode());
			if(os.getReachStatus()==0){
				if(oprFaxIn.getCpFee()!=0){
					//收入
//					FiInterfaceProDto incomeFpd=new FiInterfaceProDto();
//					incomeFpd.setDno(oprFaxIn.getDno());
//					incomeFpd.setAmount(0-oprFaxIn.getCpFee());
//					incomeFpd.setSourceData("传真作废");
//					incomeFpd.setCostType("预付提送费");
//					incomeFpd.setWhoCash("预付");
//					incomeFpd.setIncomeDepartId(oprFaxIn.getCurDepartId());
//					incomeFpd.setIncomeDepart(oprFaxIn.getCurDepart());
//					incomeFpd.setAdmDepart(oprFaxIn.getCusDepartName());
//					incomeFpd.setAdmDepartId(sd.getDepartId());
//					//传真作废ID
//					incomeFpd.setSourceNo(dno);
//					incomeList.add(incomeFpd);
					
					//欠款明细
					FiInterfaceProDto fip=new FiInterfaceProDto();
					fip.setDno(oprFaxIn.getDno());
					fip.setCollectionUser(user.get("name").toString());
					fip.setSourceData("传真作废");
					fip.setCustomerId(oprFaxIn.getCusId());
					fip.setPreCustomerId(oprFaxIn.getCusId());
					fip.setCustomerName(oprFaxIn.getCpName());
					fip.setBeforeAmount(oprFaxIn.getCpFee());
					fip.setAmount(0.0);
					fip.setCostType("预付提送费");
					fip.setContacts(oprFaxIn.getConsignee());
					fip.setGocustomerId(oprFaxIn.getGoWhereId());
					fip.setGocustomerName(oprFaxIn.getGowhere());
					fip.setWhoCash(oprFaxIn.getWhoCash());
					fip.setIncomeDepartId(oprFaxIn.getCurDepartId());
					fip.setIncomeDepart(oprFaxIn.getCurDepart());
					fip.setAdmDepart(oprFaxIn.getCusDepartName());
					fip.setAdmDepartId(sd.getDepartId());
					
					fip.setStockStatus(0L);
					fip.setSourceNo(dno);
					list.add(fip);
				}
				if(oprFaxIn.getCpSonderzugPrice()!=0){
					//收入
//					FiInterfaceProDto incomeFpd=new FiInterfaceProDto();
//					incomeFpd.setDno(oprFaxIn.getDno());
//					incomeFpd.setAmount(0-oprFaxIn.getCpSonderzugPrice());
//					incomeFpd.setSourceData("传真作废");
//					incomeFpd.setCostType("预付专车费");
//					incomeFpd.setWhoCash("预付");
//					incomeFpd.setIncomeDepartId(oprFaxIn.getCurDepartId());
//					incomeFpd.setIncomeDepart(oprFaxIn.getCurDepart());
//					incomeFpd.setAdmDepart(oprFaxIn.getCusDepartName());
//					incomeFpd.setAdmDepartId(sd.getDepartId());
//					//传真作废ID
//					incomeFpd.setSourceNo(dno);
//					incomeList.add(incomeFpd);
					
					//欠款明细
					FiInterfaceProDto fip=new FiInterfaceProDto();
					fip.setDno(oprFaxIn.getDno());
					fip.setCollectionUser(user.get("name").toString());
					fip.setSourceData("传真作废");
					fip.setCustomerId(oprFaxIn.getCusId());
					fip.setCustomerName(oprFaxIn.getCpName());
					fip.setBeforeAmount(oprFaxIn.getCpSonderzugPrice());
					fip.setAmount(0.0);
					fip.setPreCustomerId(oprFaxIn.getCusId());
					fip.setWhoCash(oprFaxIn.getWhoCash());
					fip.setCostType("预付专车费");
					fip.setContacts(oprFaxIn.getConsignee());
					fip.setGocustomerId(oprFaxIn.getGoWhereId());
					fip.setGocustomerName(oprFaxIn.getGowhere());
					
					fip.setIncomeDepartId(oprFaxIn.getCurDepartId());
					fip.setIncomeDepart(oprFaxIn.getCurDepart());
					fip.setAdmDepart(oprFaxIn.getCusDepartName());
					fip.setAdmDepartId(sd.getDepartId());
					
					fip.setStockStatus(0L);
					fip.setSourceNo(dno);
					list.add(fip);
				}
				if(oprFaxIn.getPaymentCollection()!=0){
					FiInterfaceProDto fip=new FiInterfaceProDto();
					fip.setDno(oprFaxIn.getDno());
					fip.setCollectionUser(user.get("name").toString());
					fip.setSourceData("传真作废");
					fip.setCustomerId(oprFaxIn.getCusId());
					fip.setCustomerName(oprFaxIn.getCpName());
					fip.setBeforeAmount(oprFaxIn.getPaymentCollection());
					fip.setAmount(0.0);
					fip.setPreCustomerId(oprFaxIn.getCusId());
					fip.setWhoCash(oprFaxIn.getWhoCash());
					fip.setCostType("代收货款");
					fip.setContacts(oprFaxIn.getConsignee());
					fip.setGocustomerId(oprFaxIn.getGoWhereId());
					fip.setGocustomerName(oprFaxIn.getGowhere());
					
					fip.setIncomeDepartId(oprFaxIn.getCurDepartId());
					fip.setIncomeDepart(oprFaxIn.getCurDepart());
					fip.setAdmDepart(oprFaxIn.getCusDepartName());
					fip.setAdmDepartId(sd.getDepartId());
					
					fip.setStockStatus(0L);
					fip.setSourceNo(dno);
					list.add(fip);
				}
//				if(oprFaxIn.getConsigneeFee()!=0){
//					//收入
//					FiInterfaceProDto incomeFpd=new FiInterfaceProDto();
//					incomeFpd.setDno(oprFaxIn.getDno());
//					incomeFpd.setAmount(0-oprFaxIn.getConsigneeFee());
//					incomeFpd.setSourceData("传真作废");
//					incomeFpd.setCostType("到付提送费");
//					incomeFpd.setWhoCash("到付");
//					incomeFpd.setIncomeDepartId(oprFaxIn.getCurDepartId());
//					incomeFpd.setIncomeDepart(oprFaxIn.getCurDepart());
//					incomeFpd.setAdmDepart(oprFaxIn.getCusDepartName());
//					incomeFpd.setAdmDepartId(sd.getDepartId());
//					//传真作废ID
//					incomeFpd.setSourceNo(dno);
//					incomeList.add(incomeFpd);
//				}
//				if(oprFaxIn.getSonderzugPrice()!=0){
//					//收入
//					FiInterfaceProDto incomeFpd=new FiInterfaceProDto();
//					incomeFpd.setDno(oprFaxIn.getDno());
//					incomeFpd.setAmount(0-oprFaxIn.getSonderzugPrice());
//					incomeFpd.setSourceData("传真作废");
//					incomeFpd.setCostType("到付专车费");
//					incomeFpd.setWhoCash("到付");
//					incomeFpd.setIncomeDepartId(oprFaxIn.getCurDepartId());
//					incomeFpd.setIncomeDepart(oprFaxIn.getCurDepart());
//					incomeFpd.setAdmDepart(oprFaxIn.getCusDepartName());
//					incomeFpd.setAdmDepartId(sd.getDepartId());
//					//传真作废ID
//					incomeFpd.setSourceNo(dno);
//					incomeList.add(incomeFpd);
//				}
				if(oprFaxIn.getCpValueAddFee()!=0){
//					//收入
//					FiInterfaceProDto incomeFpd=new FiInterfaceProDto();
//					incomeFpd.setDno(oprFaxIn.getDno());
//					incomeFpd.setAmount(0-oprFaxIn.getCpValueAddFee());
//					incomeFpd.setSourceData("传真作废");
//					incomeFpd.setCostType("预付增值费");
//					incomeFpd.setWhoCash("预付");
//					incomeFpd.setIncomeDepartId(oprFaxIn.getCurDepartId());
//					incomeFpd.setIncomeDepart(oprFaxIn.getCurDepart());
//					incomeFpd.setAdmDepart(oprFaxIn.getCusDepartName());
//					incomeFpd.setAdmDepartId(sd.getDepartId());
//					//传真作废ID
//					incomeFpd.setSourceNo(dno);
//					incomeList.add(incomeFpd);
					
					//欠款明细
					FiInterfaceProDto fip=new FiInterfaceProDto();
					fip.setDno(oprFaxIn.getDno());
					fip.setCollectionUser(user.get("name").toString());
					fip.setSourceData("传真作废");
					fip.setCustomerId(oprFaxIn.getCusId());
					fip.setPreCustomerId(oprFaxIn.getCusId());
					fip.setCustomerName(oprFaxIn.getCpName());
					fip.setBeforeAmount(oprFaxIn.getCpValueAddFee());
					fip.setAmount(0.0);
					fip.setCostType("预付增值费");
					fip.setWhoCash(oprFaxIn.getWhoCash());
					fip.setContacts(oprFaxIn.getConsignee());
					fip.setGocustomerId(oprFaxIn.getGoWhereId());
					fip.setGocustomerName(oprFaxIn.getGowhere());
					fip.setIncomeDepartId(oprFaxIn.getCurDepartId());
					fip.setIncomeDepart(oprFaxIn.getCurDepart());
					fip.setAdmDepart(oprFaxIn.getCusDepartName());
					fip.setAdmDepartId(sd.getDepartId());
					fip.setStockStatus(0L);
					fip.setSourceNo(dno);
					list.add(fip);
				}
//				if(oprFaxIn.getCusValueAddFee()!=0){
//					//收入
//					FiInterfaceProDto incomeFpd=new FiInterfaceProDto();
//					incomeFpd.setDno(oprFaxIn.getDno());
//					incomeFpd.setAmount(0-oprFaxIn.getCusValueAddFee());
//					incomeFpd.setSourceData("传真作废");
//					incomeFpd.setCostType("到付增值费");
//					incomeFpd.setWhoCash("到付");
//					incomeFpd.setIncomeDepartId(oprFaxIn.getCurDepartId());
//					incomeFpd.setIncomeDepart(oprFaxIn.getCurDepart());
//					incomeFpd.setAdmDepart(oprFaxIn.getCusDepartName());
//					incomeFpd.setAdmDepartId(sd.getDepartId());
//					//传真作废ID
//					incomeFpd.setSourceNo(dno);
//					incomeList.add(incomeFpd);
//				}
			}else{
				throw new ServiceException("您选择作废的传真包含已入库的货物，不能作废！");
			}
			oprFaxIn.setStatus(0L);
			oprFaxInDao.save(oprFaxIn);
			oprHistoryService.saveLog(dno, "传真作废", log_faxCancel);
		}
		//更改收入接口
		//fiInterface.currentToFiIncome(incomeList);
		//更改欠款明细
		fiInterface.changeToFi(list);
	}
	public Page sonderzugMsgCount(Page page,Date startTime, Date endTime,Long isException) throws Exception {
		StringBuffer sql=new StringBuffer("select t.d_no,t.cp_name,t.piece,t.cus_weight,nvl((t.sonderzug_price+t.cp_sonderzug_price),0) total_Son,to_char(t.create_time,'yyyy-MM-dd') create_time,t.customer_service,t.cus_depart_name from opr_fax_in t,");
		sql.append("(select fc.d_no,sum(fc.cost_amount) total_amount from fi_cost fc where fc.cost_type='提货成本' or fc.cost_type='签单成本' group by fc.d_no) ts");
		sql.append(" where t.d_no=ts.d_no(+) and t.status=1 and t.sonderzug=1 and t.create_time>=to_date(?,'yyyy-mm-dd') and t.create_time<=to_date(?,'yyyy-mm-dd')");
		if(isException==1){
			sql.append(" and nvl(ts.total_amount,0)>=nvl((t.sonderzug_price+t.cp_sonderzug_price),0) ");
		}else if(isException==0){
			sql.append(" and nvl(ts.total_amount,0)<nvl((t.sonderzug_price+t.cp_sonderzug_price),0) ");
		}
		return this.getPageBySql(page, sql.toString(), new SimpleDateFormat("yyyy-MM-dd").format(startTime),new SimpleDateFormat("yyyy-MM-dd").format(endTime));
	}
	public List daySaleMsg(Date date,String countType,Long departId,String numberType) throws Exception {
		String funName="";
		String countName = "";
		
		String groupBy = "";
		String sqlTerm = "";
		String showColumn = "";
		if("客服部门".equals(numberType)){
			groupBy = "t.cus_depart_code, t.cus_depart_name";
			sqlTerm = " and t.cus_depart_code = mt.cus_depart_code(+) and mt.customer_service is null ";
			showColumn = "cus_depart_name,duty_name,";
		}else{
			groupBy = "t.customer_service,t.cus_depart_name ";
			sqlTerm = " and t.customer_service = mt.customer_service(+)";
			showColumn = "customer_service,cus_depart_name,";
		}
		Calendar cal = Calendar.getInstance();      
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH,   1);
		 //统计月份的第一天
		Date firstDate = cal.getTime();
		cal.add(Calendar.MONTH,   1); 
		cal.add(Calendar.DAY_OF_MONTH,   -1); 
		//统计月份的最后一天
		Date lastDate = cal.getTime();
		//86400000=24*60*60*1000
		//统计月份的天数
		long mon_days = (lastDate.getTime() - firstDate.getTime())/86400000+1;
		//本月剩余天数
		long today_remain = (lastDate.getTime() - new Date().getTime())/86400000+1;
		//如本月剩余天数小于0 则统计的是当前月前的
		if(today_remain<0){
			today_remain = 1;
		}
		//本月已过天数(不算统计当天)
		long today = (date.getTime() - firstDate.getTime())/86400000+1;
		if(today>(mon_days-1)){
			today = mon_days - 1;
		}
		
		String dayDate=new SimpleDateFormat("yyyy-MM-dd").format(date);
		String monDate=new SimpleDateFormat("yyyy-MM").format(date);
		Map map=new HashMap();
		map.put("dayDate", dayDate);
		map.put("monDate", monDate);
		map.put("monDays",mon_days);
		map.put("todayRemain", today_remain);
		map.put("today", today);
		map.put("firstDate", new SimpleDateFormat("yyyy-MM-dd").format(firstDate));
		map.put("lastDate", new SimpleDateFormat("yyyy-MM-dd").format(lastDate));
		map.put("countType", countType);
		if(countType.equals("票数")){
			funName="count";
			countName="t.d_no";
		}else if(countType.equals("货量")){
			countName="t.cq_weight";
			funName="sum";
		}else if(countType.equals("收入")){
			funName="sum";
			countName="(t.cp_fee+t.cp_value_add_fee+t.cp_sonderzug_price+t.cus_value_add_fee+t.consignee_fee+t.sonderzug_price)";
			//map.put("countNum", "(t.cp_fee+t.cp_value_add_fee+t.cp_sonderzug_price+t.cus_value_add_fee+t.consignee_fee+t.sonderzug_price)");
		}
		
		StringBuffer sql=new StringBuffer("select mon_tar_num,round((mon_tar_num) / :monDays, 2) day_avg,day_num,");
		sql.append(showColumn);
		sql.append("rtrim(to_char((day_num /decode(((mon_tar_num) / :monDays), 0, 1, ((mon_tar_num) / :monDays)))*100,'fm9999999990.99'),'.')||'%' day_rate, add_num,");
		//sql.append("rtrim(to_char((add_num/decode((mon_tar_num) / (:monDays * :today),0,1,(mon_tar_num) / (:monDays * :today)))*100,'fm9999999990.99'),'.')||'%' add_rate,");
		sql.append("rtrim(to_char((add_num/decode(mon_tar_num,0,1,mon_tar_num))*100,'fm9999999990.99'),'.')||'%' add_rate,");
		sql.append("round((add_num/decode(mon_tar_num,0,1,mon_tar_num))-(:today/:monDays),4)*100||'%' should_differ,");
		//sql.append("round((mon_tar_num) / decode((:monDays),0,1,(:monDays)),2)*:today should_num,add_num - round((mon_tar_num) / decode((:monDays),0,1,(:monDays)),2)*:today should_differ,");
		sql.append("round((:today/:monDays),4)*100||'%' should_num,");
		sql.append("mon_tar_num-add_num tar_differ,round((mon_tar_num-add_num)/:todayRemain,2) remain_day_num");
		sql.append(" from (select ");
		if("客服部门".equals(numberType)){
			sql.append("t.cus_depart_name,(select wmsys.wm_concat(su.user_name) duty_name ");
			sql.append(" from sys_depart sd, sys_station ss, sys_user su");
			sql.append(" where sd.lead_station = ss.station_id and ss.station_id = su.station_id and sd.depart_no = t.cus_depart_code and su.status = 1 and su.workstatus=1 ");
			sql.append(") duty_name,");
		}else{
			sql.append("t.customer_service,t.cus_depart_name,");
		}
		sql.append("max(case when mt.target_type =:countType and to_date(to_char(mt.target_time, 'yyyy-mm'), 'yyyy-mm') = to_date(:monDate, 'yyyy-mm') then mt.target_num else 0 end) mon_tar_num,");
		sql.append(funName);
		sql.append("(");
		if("count".equals(funName)){
			sql.append("distinct ");
		}
		sql.append(" decode(to_date(to_char(t.create_time, 'yyyy-mm-dd'),'yyyy-mm-dd'), to_date(:dayDate, 'yyyy-mm-dd'),");
		sql.append(countName);
		sql.append(",");
		if("count".equals(funName)){
			sql.append("''");
		}else{
			sql.append("0");
		}
		sql.append(")) day_num,");
		
		sql.append(funName);
		sql.append("(");
		if("count".equals(funName)){
			sql.append("distinct ");
		}
		sql.append(" case when to_date(to_char(t.create_time,'yyyy-mm-dd'),'yyyy-mm-dd') >= to_date(:firstDate, 'yyyy-mm-dd') and to_date(to_char(t.create_time,'yyyy-mm-dd'),'yyyy-mm-dd') <=to_date(:dayDate, 'yyyy-mm-dd') then ");
		sql.append(countName);
		sql.append(" else 0 end) add_num");
		sql.append(" from opr_fax_in t, marketing_target mt");
		sql.append(" where t.status=1 ");
		sql.append(sqlTerm);
		if(departId != null){
			SysDepart sd = departService.getAndInitEntity(departId);
			if(sd.getIsBussinessDepa() == 1){
				sql.append(" and t.in_depart_id=:departId");
				map.put("departId", departId);
			}else{
				sql.append(" and t.cus_depart_code=:cusDepartCode");
				map.put("cusDepartCode", sd.getDepartNo());
			}
		}
		sql.append(" and to_date(to_char(t.create_time,'yyyy-mm-dd'),'yyyy-mm-dd')>=to_date(:firstDate,'yyyy-mm-dd')  and to_date(to_char(t.create_time,'yyyy-mm-dd'),'yyyy-mm-dd')<=to_date(:lastDate,'yyyy-mm-dd') ");
		sql.append(" group by ");
		sql.append(groupBy);
		sql.append(")");
		sql.append(" order by add_rate desc");
		return this.createSQLMapQuery(sql.toString(), map).list();
	}
	public Page findWholeSellMsg(Page page,String countRange,String startCount,String endCount,Long departId,String countType,String groups) throws Exception {
		Map map=new HashMap();
		StringBuffer sql=new StringBuffer("select t.cus_depart_name,");
		map.put("countType", countType);
		int  count=1;
		int countNum=0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date1=new Date();
		Date date2=new Date();
		Calendar cal=Calendar.getInstance();
		String dateFormat="yyyy-MM-dd";
		
		String[] groupa = null;
		if(!"".equals(groups)){
			groupa = groups.split(",");
		}
		
		if(countRange.equals("日")){
			dateFormat="yyyy-MM-dd";
			if(null==startCount || "".equals(startCount)){
				date1=sdf.parse(cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH))+"-"+cal.get(Calendar.DATE));
			}else{
				date1 = sdf.parse(startCount);	
			}
			if(null!=endCount || !"".equals(endCount)){
				date2 = sdf.parse(endCount);
			}
			
			Calendar cal1 = Calendar.getInstance();      
			cal.setTime(date1);
			Calendar cal2 = Calendar.getInstance();      
			cal.setTime(date2);
			if(cal1.get(Calendar.MONTH)!=cal2.get(Calendar.MONTH)){
				throw new ServiceException("请勿跨月统计!");
			}
			long l=date2.getTime()-date1.getTime(); 
			
			count=this.appendConditions.panduan(date1, date2, countRange, 60);
		}else if(countRange.equals("月")){
			dateFormat="yyyy-MM";
			sdf = new SimpleDateFormat("yyyy-MM");
			if(null==startCount || "".equals(startCount)){
				date1=sdf.parse(cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)));
			}else{
				date1 = sdf.parse(startCount);	
			}
			if(null!=endCount || !"".equals(endCount)){
				date2 = sdf.parse(endCount);
			}
			count=this.appendConditions.panduan(date1, date2, countRange, 12);
		}else if(countRange.equals("周")){
			dateFormat="WW";
			try{
				count=Integer.parseInt(endCount)+1-Integer.parseInt(startCount);
				if(count<0){
					throw new ServiceException("周数输入异常！");
				}
			}catch (Exception e) {
				throw new ServiceException("周数输入异常！");
			}
			if(count>20){
				throw new ServiceException("按周统计不能超过20周！");
			}
			
			countNum=Integer.parseInt(startCount);
		}else if(countRange.equals("年")){
			dateFormat="yyyy";
			count=Integer.parseInt(endCount)+1-Integer.parseInt(startCount);
			if(count>10){
				throw new ServiceException("按年统计不能超过10个年！");
			}else if(count<1){
				throw new ServiceException("年份不存在负数！");
			}
			countNum=Integer.parseInt(startCount);
		}
		String countName = "";
		String funName = "";
		//统计类型 票数、收入、重量
		if("收入".equals(countType)){
			countName="(t.cp_fee+t.cp_value_add_fee+t.cp_sonderzug_price+t.cus_value_add_fee+t.consignee_fee+t.sonderzug_price)";
			funName="sum";
		}else if("货量".equals(countType)){
			funName="sum";
			countName="t.cq_weight";
		}else if("票数".equals(countType)){
			countName="t.d_no";
			funName="count";
		}
		if(countRange.equals("日")){
			cal.setTime(date1);
			map.put("monDate", new SimpleDateFormat("yyyy-MM").format(date1));
			Calendar cal1 = Calendar.getInstance();      
			cal1.setTime(date1);
			cal1.set(Calendar.DAY_OF_MONTH,   1);
			 //统计月份的第一天
			Date firstDate = cal1.getTime();
			cal1.add(Calendar.MONTH,   1); 
			cal1.add(Calendar.DAY_OF_MONTH,   -1); 
			//统计月份的最后一天
			Date lastDate = cal1.getTime();
			//86400000=24*60*60*1000
			//统计月份的天数
			long mon_days = (lastDate.getTime() - firstDate.getTime())/86400000+1;
			for(int i=1;i<=count;i++){
				String yy = cal.get(Calendar.YEAR)+"";
				String dd = cal.get(Calendar.DATE)<10?"0"+cal.get(Calendar.DATE):cal.get(Calendar.DATE)+"";
				String mm = cal.get(Calendar.MONTH)+1<10?"0"+(cal.get(Calendar.MONTH)+1):(cal.get(Calendar.MONTH)+1)+"";
				if(i!=1){
					sql.append(",");
				}
				sql.append(funName);
				sql.append("(");
				if("count".equals(funName)){
					sql.append("distinct ");
				}
				sql.append("decode(to_date(to_char(t.create_time,'"+dateFormat+"'),'"+dateFormat+"'),");
				sql.append("to_date('"+sdf.format(cal.getTime())+"','"+dateFormat+"'),round(nvl(");
				sql.append(countName);
				sql.append(",0),2),");
				if("count".equals(funName)){
					sql.append("''");
				}else{
					sql.append("0");
				}
				sql.append(")) ");
				sql.append("\"").append(yy).append("-").append(mm).append("-").append(dd).append("\"");
				if(groupa != null){
					if(groupa.length>1){
						sql.append(",");
						sql.append("round(max(case when mt.target_type =:countType and to_date(to_char(mt.target_time, 'yyyy-mm'), 'yyyy-mm') = to_date(:monDate, 'yyyy-mm') then mt.target_num else 0 end)/");
						sql.append(mon_days);
						sql.append(",2)");
						sql.append("\"").append(yy).append("-").append(mm).append("-").append(dd).append("指标\"");
						
						sql.append(",");
						sql.append("rtrim(to_char((");
						sql.append(funName);
						sql.append("(");
						if("count".equals(funName)){
							sql.append("distinct ");
						}
						sql.append("decode(to_date(to_char(t.create_time,'"+dateFormat+"'),'"+dateFormat+"'),");
						sql.append("to_date('"+sdf.format(cal.getTime())+"','"+dateFormat+"'),round(nvl(");
						sql.append(countName);
						sql.append(",0),2),");
						if("count".equals(funName)){
							sql.append("''");
						}else{
							sql.append("0");
						}
						sql.append(")) ");
						sql.append("/");
						sql.append("decode(round(max(case when mt.target_type =:countType and to_date(to_char(mt.target_time, 'yyyy-mm'), 'yyyy-mm') = to_date(:monDate, 'yyyy-mm') then mt.target_num else 0 end)/");
						sql.append(mon_days);
						sql.append(",2),0,1,");
						sql.append("round(max(case when mt.target_type =:countType and to_date(to_char(mt.target_time, 'yyyy-mm'), 'yyyy-mm') = to_date(:monDate, 'yyyy-mm') then mt.target_num else 0 end)))/");
						sql.append(mon_days);
						sql.append(")*100");
						sql.append(",'fm9999999990.99'),'.')||'%' ");
						sql.append("\"").append(yy).append("-").append(mm).append("-").append(dd).append("完成率\"");
					}else if(groupa.length == 1){
						if(groupa[0].equals("target")){
							sql.append(",");
							sql.append("round(max(case when mt.target_type =:countType and to_date(to_char(mt.target_time, 'yyyy-mm'), 'yyyy-mm') = to_date(:monDate, 'yyyy-mm') then mt.target_num else 0 end)/");
							sql.append(mon_days);
							sql.append(",2)");
							sql.append("\"").append(yy).append("-").append(mm).append("-").append(dd).append("指标\"");
						}else if(groupa[0].equals("targetRate")){
							sql.append(",");
							sql.append("rtrim(to_char((");
							sql.append(funName);
							sql.append("(");
							if("count".equals(funName)){
								sql.append("distinct ");
							}
							sql.append("decode(to_date(to_char(t.create_time,'"+dateFormat+"'),'"+dateFormat+"'),");
							sql.append("to_date('"+sdf.format(cal.getTime())+"','"+dateFormat+"'),round(nvl(");
							sql.append(countName);
							sql.append(",0),2),");
							if("count".equals(funName)){
								sql.append("''");
							}else{
								sql.append("0");
							}
							sql.append(")) ");
							sql.append("/");
							sql.append("decode(round(max(case when mt.target_type =:countType and to_date(to_char(mt.target_time, 'yyyy-mm'), 'yyyy-mm') = to_date(:monDate, 'yyyy-mm') then mt.target_num else 0 end)/");
							sql.append(mon_days);
							sql.append(",2),0,1,");
							sql.append("round(max(case when mt.target_type =:countType and to_date(to_char(mt.target_time, 'yyyy-mm'), 'yyyy-mm') = to_date(:monDate, 'yyyy-mm') then mt.target_num else 0 end)/");
							sql.append(mon_days);
							sql.append("))*100)");
							sql.append(",'fm9999999990.99'),'.')||'%' ");
							sql.append("\"").append(yy).append("-").append(mm).append("-").append(dd).append("完成率\"");
						}
					}
				}
				cal.set(Calendar.DATE, cal.get(Calendar.DATE)+1);
			}
		}else if(countRange.equals("月")){
			cal.setTime(date1);
			map.put("monDate", new SimpleDateFormat("yyyy-MM").format(date1));
			for(int i=1;i<=count;i++){
				String yy = cal.get(Calendar.YEAR)+"";
				String mm = cal.get(Calendar.MONTH)+1<10?"0"+(cal.get(Calendar.MONTH)+1):(cal.get(Calendar.MONTH)+1)+"";
				if(i!=1){
					sql.append(",");
				}
				sql.append(funName);
				sql.append("(");
				if("count".equals(funName)){
					sql.append("distinct ");
				}
				sql.append("decode(to_date(to_char(t.create_time,'"+dateFormat+"'),'"+dateFormat+"'),");
				sql.append("to_date('"+sdf.format(cal.getTime())+"','"+dateFormat+"'),round(nvl(");
				sql.append(countName);
				sql.append(",0),2),0)) ");
				sql.append("\"").append(yy).append("-").append(mm).append("\"");
				if(groupa != null){
					if(groupa.length>1){
						sql.append(",");
						sql.append("max(case when mt.target_type =:countType and to_date(to_char(mt.target_time, 'yyyy-mm'), 'yyyy-mm') = to_date(");
						sql.append("'").append(yy).append("-").append(mm).append("'");
						sql.append(", 'yyyy-mm') then mt.target_num else 0 end)");
						sql.append("\"").append(yy).append("-").append(mm).append("指标\"");
						
						sql.append(",");
						sql.append("rtrim(to_char((");
						sql.append(funName);
						sql.append("(");
						if("count".equals(funName)){
							sql.append("distinct ");
						}
						sql.append("decode(to_date(to_char(t.create_time,'"+dateFormat+"'),'"+dateFormat+"'),");
						sql.append("to_date('"+sdf.format(cal.getTime())+"','"+dateFormat+"'),round(nvl(");
						sql.append(countName);
						sql.append(",0),2),");
						if("count".equals(funName)){
							sql.append("''");
						}else{
							sql.append("0");
						}
						sql.append(")) ");
						sql.append("/");
						sql.append("decode(");
						sql.append("max(case when mt.target_type =:countType and to_date(to_char(mt.target_time, 'yyyy-mm'), 'yyyy-mm') = to_date(");
						sql.append("'").append(yy).append("-").append(mm).append("'");
						sql.append(", 'yyyy-mm') then mt.target_num else 0 end)");
						sql.append(",0,1,max(case when mt.target_type =:countType and to_date(to_char(mt.target_time, 'yyyy-mm'), 'yyyy-mm') = to_date(");
						sql.append("'").append(yy).append("-").append(mm).append("'");
						sql.append(", 'yyyy-mm') then mt.target_num else 0 end))");
						sql.append(")*100");
						sql.append(",'fm99999999990.99'),'.')||'%' ");
						sql.append("\"").append(yy).append("-").append(mm).append("完成率\"");
					}else if(groupa.length == 1){
						if(groupa[0].equals("target")){
							sql.append(",");
							sql.append("max(case when mt.target_type =:countType and to_date(to_char(mt.target_time, 'yyyy-mm'), 'yyyy-mm') = to_date(");
							sql.append("'").append(yy).append("-").append(mm).append("'");
							sql.append(", 'yyyy-mm') then mt.target_num else 0 end)");
							sql.append("\"").append(yy).append("-").append(mm).append("指标\"");
						}else if(groupa[0].equals("targetRate")){
							sql.append(",");
							sql.append("rtrim(to_char((");
							sql.append(funName);
							sql.append("(");
							if("count".equals(funName)){
								sql.append("distinct ");
							}
							sql.append("decode(to_date(to_char(t.create_time,'"+dateFormat+"'),'"+dateFormat+"'),");
							sql.append("to_date('"+sdf.format(cal.getTime())+"','"+dateFormat+"'),round(nvl(");
							sql.append(countName);
							sql.append(",0),2),");
							if("count".equals(funName)){
								sql.append("''");
							}else{
								sql.append("0");
							}
							sql.append(")) ");
							sql.append("/");
							sql.append("decode(");
							sql.append("max(case when mt.target_type =:countType and to_date(to_char(mt.target_time, 'yyyy-mm'), 'yyyy-mm') = to_date(");
							sql.append("'").append(yy).append("-").append(mm).append("'");
							sql.append(", 'yyyy-mm') then mt.target_num else 0 end)");
							sql.append(",0,1,max(case when mt.target_type =:countType and to_date(to_char(mt.target_time, 'yyyy-mm'), 'yyyy-mm') = to_date(");
							sql.append("'").append(yy).append("-").append(mm).append("'");
							sql.append(", 'yyyy-mm') then mt.target_num else 0 end))");
							sql.append(")*100");
							sql.append(",'fm99999999990.99'),'.')||'%' ");
							sql.append("\"").append(yy).append("-").append(mm).append("完成率\"");
						}
					}
				}
				
				cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)+1);
			}
		}else if(countRange.equals("周") || countRange.equals("年")){
			countNum=Integer.parseInt(startCount);
			for(int i=1;i<=count;i++){
				if(i!=1){
					sql.append(",");
				}
				sql.append(funName);
				sql.append("(");
				if("count".equals(funName)){
					sql.append("distinct ");
				}
				sql.append("decode(trunc(to_char(t.create_time,'"+dateFormat+"')),");
				sql.append(countNum+",round(nvl(");
				sql.append(countName);
				sql.append(",0),2),");
				if("count".equals(funName)){
					sql.append("''");
				}else{
					sql.append("0");
				}
				sql.append(")) ");
				sql.append("\"").append(countNum).append(countRange).append("\"");
				countNum++;
			}
		}
		sql.append(" from opr_fax_in t, marketing_target mt");
		sql.append(" where t.cus_depart_code = mt.cus_depart_code(+) and t.status=1 and mt.customer_service is null ");
		if(departId!=null){
			SysDepart sd = departService.getAndInitEntity(departId);
			if(sd.getIsBussinessDepa() == 1){
				sql.append(" and t.in_depart_id=:departId");
				map.put("departId", departId);
			}else{
				sql.append(" and t.cus_depart_code=:cusDepartCode");
				map.put("cusDepartCode", sd.getDepartNo());
			}
		}
		sql.append(" group by t.cus_depart_name ");
		return this.getPageBySqlMap(page, sql.toString(), map);
	}

	//多票货物更改成同一个航班号
	public void updateFlight(List<Long> dnos, OprFaxIn oprFaxIn,Long isFlyLate) throws Exception {
		if(dnos==null||dnos.size()==0||oprFaxIn==null||oprFaxIn.getFlightNo()==null||                //非空判断
					oprFaxIn.getFlightDate()==null||oprFaxIn.getFlightTime()==null){
			throw new ServiceException("提交的数据不能为空");
		}
		for(Long dno:dnos){
			OprFaxIn oFaxIn = get(dno);
			OprStatus status = this.oprStatusService.findStatusByDno(dno);
			
			if(oFaxIn==null){
				throw new ServiceException("配送单号："+dno+"货物找不到，无法更改航班号！");
			}
			oFaxIn.setFlightNo(oprFaxIn.getFlightNo());
			oFaxIn.setFlightDate(oprFaxIn.getFlightDate());
			oFaxIn.setFlightTime(oprFaxIn.getFlightTime());
			
			status.setIsFlyLate(isFlyLate);//修改航班的延误状态 航班是否延误，0为未延误（即正常），1为延误
			this.oprStatusService.save(status);
		}
		
	}
	
	public void insertEdiDataService(EdiFaxInVo ofi,List<OprRequestDo> requestList)throws Exception{
		String request ="";
		if(null!=requestList && requestList.size()>0){
			for (int j = 0; j < requestList.size(); j++) {
				request+=requestList.get(j).getRequest();
			}
		}
		
		CtEstimate  entity = new CtEstimate();
		entity.setDNo(ofi.getDno()+"");
		entity.setConsignId(ofi.getConsigneeId());
		
		entity.setCreateName(ofi.getCreateName());
		entity.setCreateTime(ofi.getCreateTime());
		entity.setFaxInTime(ofi.getCreateTime());
		try{
			entity.setCtId(Long.valueOf(this.customerService.get(ofi.getGoWhereId()).getEdiUserId()));
		}catch (Exception e) {
			oprHistoryService.saveLog(Long.valueOf(entity.getDNo()), "传真写入EID临时表失败，没有找到"+ofi.getGowhere()+"的EDI对接编码.",this.log_ediFailure );
			logger.error("传真，"+ofi.getGowhere()+"的EDI编码获取失败！"); 
			return;
			//throw  new ServiceException("中转客商编码获取失败！");
		}
		entity.setCtName(ofi.getGowhere());
		entity.setCubage(ofi.getBulk());
		entity.setCustomerServiceName(ofi.getCustomerService());
		entity.setDnAmt(ofi.getConsigneeFee()+ofi.getCusValueAddFee());
		entity.setEndpayAmt(ofi.getPaymentCollection());
		entity.setFlyTime(ofi.getFlightTime());
		entity.setGoods(ofi.getGoods());
		entity.setIsvaluables("0");
		entity.setPiece(ofi.getPiece());
		entity.setReceAdd(ofi.getAddr());
		entity.setReceCorp("");
		entity.setReceMan(ofi.getConsignee());
		//dto.setReceMob(ofi.getConsigneeTel());
		entity.setReceTel(ofi.getConsigneeTel());
		entity.setRemark(ofi.getRemark());
		entity.setRequest(request);
		entity.setShfNo(ofi.getFlightNo());
		entity.setStopflag("0");
		entity.setSustbillNo(ofi.getSubNo());
		entity.setTakeMode(ofi.getTakeMode());
		entity.setTraAmt(ofi.getTraFee());
		entity.setTraCost(ofi.getTraFeeRate());
		entity.setWeight(ofi.getCqWeight());
		entity.setYdNo(ofi.getFlightMainNo());
		
		this.ctEstimateService.save(entity);
		
//		EdiWSResult reDto=null;
//		//保存到EDI
//		try{
//			reDto = this.wsCtEstimateService.insertCtEstimateService(dto);
//			
//		}catch (Exception e) {
//			if(null==reDto){
//				//写入临时存储表
//				CtEstimate entity = new CtEstimate();
//				BeanUtils.copyProperties(dto, entity);
//				try{
//					this.ctEstimateService.save(entity);
//				}catch (Exception ee) {
//					throw ee;
//				}
//			}else{
//				throw e;
//			}
//		}
//		
//		if(null!=reDto && "1".equals(reDto.getCode())){
//			throw new ServiceException(reDto.getMessage());
//		}
	}
	public Page getSumInfoByMainno(Page page,String fligthMainNo,Date createTime) throws Exception {
		Map map=new HashMap();
		map.put("flightMainNo", fligthMainNo);
		map.put("createTime", createTime);
		return this.getPageBySqlMap(page, "select count(*) as sum_ticket,sum(t.PAYMENT_COLLECTION) sum_pay,sum(t.piece) sum_piece,sum(t.cus_weight) sum_weight,sum(t.cp_fee) sum_cpfee,sum(t.consignee_fee) sum_consigneefee from opr_fax_in t where t.flight_main_no=:flightMainNo and t.create_time>=:createTime", map);
	}
	public void customerServiceAdjust(String dnos) throws Exception {
		
		String[] dno = dnos.split(",");
		for (int i = 0; i < dno.length; i++) {
			OprFaxIn ofIn = oprFaxInDao.getAndInitEntity(Long.valueOf(dno[i]));
			
			List<BasCusService> bList = basCusService.find("from BasCusService bcs where bcs.cusId=? and bcs.departId=?", ofIn.getCusId(),ofIn.getInDepartId());
			if (bList.size()!=1) {
				throw new ServiceException("数据异常，该代理对应的客服员不存在或者存在多个客服员！");
			}
			BasCusService bCusService = bList.get(0);
			//修改传真表
			
			ofIn.setCustomerService(bCusService.getServiceName());
			ofIn.setCusDepartName(bCusService.getServiceDepart());
			ofIn.setCusDepartCode(bCusService.getServiceDepartCode());
			oprFaxInDao.save(ofIn);
			
			SysDepart sysDepart = departService.getDepartByDepartNo(bCusService.getServiceDepartCode());
			if(sysDepart == null){
				throw new ServiceException("数据异常，部门编码:"+bCusService.getServiceDepartCode()+"对应的部门为空！");
			}
			//修改收入表
			List<FiIncome> fiList = fiIncomeDao.findBy("dno", ofIn.getDno());
			for (int j = 0; j < fiList.size(); j++) {
				FiIncome fIncome = fiList.get(i);
				fIncome.setCustomerService(bCusService.getServiceName());
				fIncome.setAdmDepartId(sysDepart.getDepartId());
				fIncome.setAdmDepart(bCusService.getServiceDepart());
				fiIncomeDao.save(fIncome);
			}
		}
	}
	public Page getDepartResults(Page page, Date startDate, Date endDate,Long departId,String dateType)
			throws Exception {
		String datePar="";
		if("日".equals(dateType)){
			datePar = "yyyy-MM-dd";
			
		}else{
			datePar = "yyyy-MM";
		}
		String startD = new SimpleDateFormat(datePar).format(startDate);
		String endD = new SimpleDateFormat(datePar).format(endDate);
		
		Map map = new HashMap();
		map.put("startDate", startD);
		map.put("endDate", endD);
		List<Map> marketList = marketTargetService.findTargetDepartCode(departId);
		if(marketList.size()<1){
			throw new ServiceException("请先到营销指标设置客服部门指标再尝试统计");
		}
		//拼接SQL语句
		StringBuffer sumTicket = new StringBuffer("(");
		StringBuffer sumWeight = new StringBuffer("(");
		StringBuffer sumIncome = new StringBuffer("(");
		
		StringBuffer sql = new StringBuffer("select day_time,");
		for(int i = 0; i < marketList.size(); i++){
			sql.append("ticket")
			   .append(i)
			   .append(",");
			sumTicket.append("ticket")
			   .append(i);
			sql.append("weight")
			   .append(i)
			   .append(",");
			sumWeight.append("weight")
			   .append(i);
			sql.append("income")
			   .append(i)
			   .append(",");
			sumIncome.append("income")
			   .append(i);
			
			if(i != marketList.size()-1){
				sumTicket.append("+");
				sumWeight.append("+");
				sumIncome.append("+");
			}else{
				sumTicket.append(") sum_ticket,");
				sumWeight.append(") sum_weight,");
				sumIncome.append(") sum_income");
			}
		}
		sql.append(sumTicket)
		   .append(sumWeight)
		   .append(sumIncome)
		   .append(" from (select to_char(t.create_time,'")
		   .append(datePar)
		   .append("') day_time,");
		for (int i = 0; i < marketList.size(); i++) {
			String cusDepartCode = marketList.get(i).get("CUS_DEPART_CODE").toString();
			sql.append("count(distinct decode(t.cus_depart_code,")
			   .append(cusDepartCode)
			   .append(", t.d_no, '')) ticket")
			   .append(i)
			   .append(",");
			sql.append("sum(decode(t.cus_depart_code,")
			   .append(cusDepartCode)
			   .append(", t.cus_weight, 0)) weight")
			   .append(i)
			   .append(",");
			sql.append("sum(decode(t.cus_depart_code,")
			   .append(cusDepartCode)
			   .append(", t.cp_fee+t.cp_value_add_fee+t.consignee_fee+t.cus_value_add_fee+t.cp_sonderzug_price+t.sonderzug_price, 0)) income")
			   .append(i);
			if(i != marketList.size()-1){
				sql.append(",");
			}
		}
		sql.append(" from opr_fax_in t where t.status=1 and t.create_time between to_date(:startDate,'")
		   .append(datePar)
		   .append("') and to_date(:endDate,'")
		   .append(datePar)
		   .append("')+1")
		   .append(" group by to_char(t.create_time,'")
		   .append(datePar)
		   .append("'))");
		return this.getPageBySqlMap(page, sql.toString(), map);
	}
}

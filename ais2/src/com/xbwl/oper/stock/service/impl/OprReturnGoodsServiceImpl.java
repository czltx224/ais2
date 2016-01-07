package com.xbwl.oper.stock.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.ralasafe.WebRalasafe;
import org.ralasafe.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.log.anno.ModuleName;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.common.utils.LogType;
import com.xbwl.entity.OprFaxIn;
import com.xbwl.entity.OprOvermemoDetail;
import com.xbwl.entity.OprReceipt;
import com.xbwl.entity.OprReturnGoods;
import com.xbwl.entity.OprStatus;
import com.xbwl.entity.SysDepart;
import com.xbwl.finance.dto.IFiInterface;
import com.xbwl.finance.dto.impl.FiInterfaceProDto;
import com.xbwl.oper.fax.dao.IOprFaxInDao;
import com.xbwl.oper.receipt.service.IOprReceiptService;
import com.xbwl.oper.reports.util.AppendConditions;
import com.xbwl.oper.stock.dao.IOprReturnGoodsDao;
import com.xbwl.oper.stock.dao.IOprStatusDao;
import com.xbwl.oper.stock.service.IOprExceptionStockService;
import com.xbwl.oper.stock.service.IOprHistoryService;
import com.xbwl.oper.stock.service.IOprOvermemoDetailService;
import com.xbwl.oper.stock.service.IOprReturnGoodsService;
import com.xbwl.oper.stock.service.IOprStockService;
import com.xbwl.rbac.Service.IDepartService;

/**
 * @author CaoZhili time Jul 30, 2011 10:43:52 AM
 * 
 * 返货管理服务层实现类
 */
@Service("oprReturnGoodsServiceImpl")
@Transactional(rollbackFor = { Exception.class })
public class OprReturnGoodsServiceImpl extends
		BaseServiceImpl<OprReturnGoods, Long> implements IOprReturnGoodsService {

	@Resource(name = "oprReturnGoodsHibernateDaoImpl")
	private IOprReturnGoodsDao oprReturnGoodsDao;

	@Resource(name = "oprFaxInHibernateDaoImpl")
	private IOprFaxInDao oprFaxInDao;

	@Resource(name = "oprStatusHibernateDaoImpl")
	private IOprStatusDao oprStatusDao;

	@Resource(name="oprExceptionStockServiceImpl")
	private IOprExceptionStockService oprExceptionStockService;
	
	@Resource(name = "oprHistoryServiceImpl")
	private IOprHistoryService oprHistoryService;
	
	@Resource(name="oprStockServiceImpl")
	private IOprStockService oprStockService;
	
	@Resource(name="oprReceiptServiceImpl")
	private IOprReceiptService oprReceiptService;
	
	@Resource(name="fiInterfaceImpl")
	private IFiInterface fiInterface;
	
	@Resource(name="oprOvermemoDetailServiceImpl")
	private IOprOvermemoDetailService oprOvermemoDetailService;
	
	@Resource(name="departServiceImpl")
	private IDepartService departService;
	
	@Resource(name="appendConditions")
	private AppendConditions appendConditions;
	
	@Value("${oprReturnGoodsServiceImpl.log_returnEnterStock}")
	private Long   log_returnEnterStock;//返货入库
	
	@Value("${oprReturnGoodsServiceImpl.log_returnRegistration}")
	private Long   log_returnRegistration;//返货登记
	
	@Value("${oprOutStockServiceImpl.log_returAudit}")
	private Long   log_returAudit;//返货审核
	
	public static final  String bufen="部分返货";
	public static final String zhengpiao ="整票返货";
	public static final String chailing ="拆零返货";
	
	@Override
	public IBaseDAO<OprReturnGoods, Long> getBaseDao() {
		return this.oprReturnGoodsDao;
	}

	@ModuleName(value="返货登记",logType=LogType.buss)
	public void saveRegistrationService(OprReturnGoods oprReturnGoods)
			throws Exception {
		List<OprStatus> statuslist = this.oprStatusDao.findBy("dno", oprReturnGoods.getDno());
		if(statuslist.size()>0 && statuslist.get(0).getOutStatus()==0){
			throw new ServiceException("您输入的配送单还没有出库！");
		}
		OprStatus entity = statuslist.get(0);
		
		if(oprReturnGoods.getReturnType().equals(zhengpiao )){
			entity.setReturnStatus(1l);//整票返货
		}else if(oprReturnGoods.getReturnType().equals(bufen )){
			entity.setReturnStatus(2l);//部分返货
		}else if(oprReturnGoods.getReturnType().equals(chailing )){
			entity.setIsException(1l);
			entity.setReturnStatus(3l);//拆零返货
		}
		entity.setReturnTime(new Date());
		SimpleDateFormat sdf =  new SimpleDateFormat("yyyy年MM月dd日");
		this.oprReturnGoodsDao.save(oprReturnGoods);
		this.oprHistoryService.saveLog(oprReturnGoods.getDno(), oprReturnGoods.getCreateName()+"在"+sdf.format(oprReturnGoods.getCreateTime())+"返货登记"+oprReturnGoods.getReturnNum()+"件", log_returnRegistration);
		
		this.oprStatusDao.save(entity);
		
	}

	@ModuleName(value="返货入库",logType=LogType.buss)
	public void saveEnterStockService(String ids, Long returnStatus)
			throws Exception {

		String[] strids = ids.split(",");
		OprReturnGoods oprReturnGoods = null;
		
		for (int i = 0; i < strids.length; i++) {

			oprReturnGoods = this.oprReturnGoodsDao.get(new Long(strids[i]));
			
			if(oprReturnGoods.getStatus()==2l){
				//已经入库的货物，不允许重复入库
				throw new ServiceException("配送单号为"+oprReturnGoods.getDno()+"的货物已经入库！");
			}
			
			// 修改状态辅助表中的状态
			List<OprStatus> list = this.oprStatusDao.findBy("dno",
					oprReturnGoods.getDno());
			if (null!=list && list.size() > 0) {
				OprStatus entity = list.get(0);
				Date dt = new Date();
				if(oprReturnGoods.getReturnType().equals(zhengpiao )){
					entity.setReturnStatus(1l);//整票返货
				}else if(oprReturnGoods.getReturnType().equals(bufen )){
					entity.setReturnStatus(2l);//部分返货
				}else if(oprReturnGoods.getReturnType().equals(chailing )){
					entity.setIsException(1l);
					entity.setReturnStatus(3l);//拆零返货
				}
				entity.setOutStatus(returnStatus);
				if(oprReturnGoods.getReturnType().equals(zhengpiao )){
					entity.setOutStatus(0l);//整票返货则该为未出库状态
				}
				entity.setReturnEnterTime(dt);
				this.oprStatusDao.save(entity);
			}else{
				throw new ServiceException("配送单号有误！");
			}
			// 修改返货表中的状态
			OprReturnGoods goodsEntity = this.oprReturnGoodsDao
					.get(oprReturnGoods.getId());
			goodsEntity.setStatus(returnStatus);
			SimpleDateFormat sdf =  new SimpleDateFormat("yyyy年MM月dd日");
			this.oprHistoryService.saveLog(oprReturnGoods.getDno(), oprReturnGoods.getCreateName()+"在"+sdf.format(oprReturnGoods.getCreateTime())+"返货入库"+oprReturnGoods.getReturnNum()+"件", log_returnEnterStock);
			
			if(oprReturnGoods.getReturnType().equals(zhengpiao) || oprReturnGoods.getReturnType().equals(bufen)){
				//throw new ServiceException("只有整票返货和部分返货才允许返货！");
				try{
					this.oprStockService.saveReturnGoodsStock(oprReturnGoods);//整票和部分入正常库存
				}catch (Exception e) {
					throw new ServiceException("库存设置失败！");
				}
			}else if(oprReturnGoods.getReturnType().equals(chailing)){
			// 修改或保存到异常库存
				try{
					this.oprExceptionStockService.saveReturnGoodsStock(oprReturnGoods);//拆零入异常库存
				}catch(Exception e){
					throw new ServiceException("异常库存设置失败！");
				}
			}else{
				throw new ServiceException("该货物返货类型不正确！");
			}
			this.oprReturnGoodsDao.save(goodsEntity);

		}
	}
	@ModuleName(value="判断是否允许返货登记",logType=LogType.buss)
	public OprFaxIn allowRegistration(Long dno) throws Exception{
		User user = WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
		Long bussDepartId = Long.valueOf(user.get("bussDepart")+"");
		List<OprStatus> list = this.oprStatusDao.findBy("dno", dno);
		List<OprReceipt> reList = this.oprReceiptService.findBy("dno",dno);
		if(null!=reList && reList.size()>0){
			if(reList.get(0).getConfirmStatus()==1l){
				//已经回单确收的货物，不允许返货
				throw new ServiceException("这票货已经回单确收，不允许返货！");
			}
		}
		if(null!=list && list.size()>0){
			
			if(list.get(0).getOutStatus()==0L){
				throw new ServiceException("该货物还没有出库呢！");
			}else if(list.get(0).getOutStatus()==3L){
				throw new ServiceException("该货物还是处于预配状态！");
			}else if(list.get(0).getSignStatus()==1l){
				//已经签收的货物不允许返货
				throw new ServiceException("这票货已经签收，不允许返货！");
			}else if(list.get(0).getFeeAuditStatus()==1l){//status.setFeeAuditStatus(0l)
				throw new ServiceException("这票货中转费已经审核，不允许返货！");
			}else{
 				List<OprReturnGoods> returnlist = this.oprReturnGoodsDao.findBy("dno", dno);
 				for(int i=0;i<returnlist.size();i++){
 					if(returnlist.get(i).getStatus()!=3l){
 						throw new ServiceException("该货物已经返货,还没有出库呢！");
 					}
 				}
 
				OprFaxIn faxIn = this.oprFaxInDao.get(dno);
				if(!faxIn.getEndDepartId().equals(bussDepartId)){
					SysDepart depart = this.departService.get(faxIn.getEndDepartId());
					throw new ServiceException("请到"+depart.getDepartName()+"返货!");
				}
				
				return faxIn;
			}
		}else{
			throw new ServiceException("该货物不存在！");
		}
	}

	public Long findMaxOvermemoNoByDno(Long dno) throws Exception {
		
		String sql="select max(overmemo) as overmemoNo from opr_overmemo_detail t where t.d_no=?";
		List<Map> list  = this.oprReturnGoodsDao.createSQLQuery(sql, dno).list();
		
		if(null!=list && list.size()>0){
			if(null!=list.get(0).get("OVERMEMONO") && !"".equals(list.get(0).get("OVERMEMONO"))){
				return Long.valueOf(list.get(0).get("OVERMEMONO")+"");
			}
		}
		return null;
	}

	@ModuleName(value="返货审核",logType=LogType.buss)
	public void auditReturnGoods(Long returnGoodsId) throws Exception {
		if(null==returnGoodsId || "".equals(returnGoodsId)){
			throw new ServiceException("没有返货单号！");
		}
		OprReturnGoods returnGoods = this.oprReturnGoodsDao.get(returnGoodsId);
		if(null!=returnGoods.getAuditStatus() && returnGoods.getAuditStatus().equals(1l)){
			throw new ServiceException("该返货已经审核！");
		}
		
		if(null==returnGoods.getOutNo()){
			throw new ServiceException("没有交接单号！");
		}
		User user = WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
		List<FiInterfaceProDto> listfiInterfaceDto = new ArrayList<FiInterfaceProDto>();
		List<OprOvermemoDetail> list= this.oprOvermemoDetailService.find("from OprOvermemoDetail where oprOvermemo.id=? and dno=?", returnGoods.getOutNo(),returnGoods.getDno());
		FiInterfaceProDto dto = null;
		OprFaxIn fax = null;
		String distributionMode ="";
		for (OprOvermemoDetail detail : list) {
			dto = new FiInterfaceProDto();
			fax = this.oprFaxInDao.get(detail.getDno());
			if(detail.getDistributionMode().equals("中转") || detail.getDistributionMode().equals("外发")){
				distributionMode = detail.getDistributionMode();
				dto.setCustomerId(fax.getGoWhereId()==null?0l:fax.getGoWhereId());
				dto.setCustomerName(fax.getGowhere()==null?"":fax.getGowhere());
			}
			dto.setDno(detail.getDno()==null?0l:detail.getDno());
			dto.setDistributionMode(fax.getDistributionMode()==null?"":fax.getDistributionMode());
			dto.setAmount(returnGoods.getReturnCost()==null?0l:returnGoods.getReturnCost());
			
			dto.setSourceData("配送单");//业务调用财务作废总接口
			dto.setSourceNo(returnGoods.getDno());//业务调用财务作废总接口
			listfiInterfaceDto.add(dto);
		}
		
		// 如果返货成本为空，未付成本则清空传真表上的中转费。
//		if (returnGoods.getReturnCost() == 0) {//清空中转费
			// 清空传真表上的中转费。
		OprFaxIn faxIn = this.oprFaxInDao.get(returnGoods.getDno());

		faxIn.setTraFee(new Double(0));
		faxIn.setTraFeeRate(new Double(0));
		this.oprFaxInDao.save(faxIn);
		OprStatus status = this.oprStatusDao.findBy("dno", returnGoods.getDno()).get(0);
		status.setPayTra(0l);
		//status.setFeeAuditStatus(0l);//中转费用审核:0未审核，1已审核
//		}
		
		//1、调用财务接口
		if(returnGoods.getReturnType().equals(zhengpiao)){
			this.fiInterface.oprReturnToFi(listfiInterfaceDto);//业务调用财务作废总接口
			status.setIsCreateFi(0l);//是否已挂账 0：未挂账，1：已挂账。主要针对异常出库。
		}
		this.oprStatusDao.save(status);
		
		//2、调用成本接口
		if(distributionMode.equals("中转") || distributionMode.equals("外发")){
			//if(null!=returnGoods.getReturnCost() && returnGoods.getReturnCost()>=0){
				//throw new ServiceException("该返货没有返货成本！");
				List<FiInterfaceProDto> dtoList = new ArrayList<FiInterfaceProDto>();
				for (FiInterfaceProDto fdto : listfiInterfaceDto) {
					fdto.setSourceData("返货登记");//返货成本写入中转成本
					fdto.setSourceNo(returnGoods.getId());//返货成本写入中转成本
					
					dtoList.add(fdto);
				}
				this.fiInterface.oprReturnToFiTransitcost(dtoList);
			//}
		}
		
		//返货审核写入日志
		this.oprHistoryService.saveLog(returnGoods.getDno(), user.get("name")+"，返货审核。",this.log_returAudit);
		
		//3、修改返货表的审核状态
		returnGoods.setAuditStatus(1l);
		this.oprReturnGoodsDao.save(returnGoods);
	}

	public String findTotalCountService(Map<String, String> map) throws Exception {
		//返货统计
		
		StringBuffer sb = new StringBuffer();
		sb.append("select count(*) totalNum,sum(r.return_num) totalPiece,")
             .append(" sum(r.return_cost) totalCost,sum(r.consignee_fee) totalConsigneeFee,")
             .append("sum(r.payment_collection) totalPayment,")
             .append("sum(nvl(piece,0)) faxPiece,sum(nvl(cq_weight,0)) cqWeight,sum(nvl(CUS_VALUE_ADD_FEE,0)) CUSVALUEADDFEE")
             .append(" from opr_return_goods r ,")
             .append("(select t.flight_no,t.cp_name,t.d_no,t.distribution_mode,t.take_mode,t.piece,t.cq_weight,")
             .append("t.consignee,t.consignee_tel,t.addr,t.real_go_where,t.cus_value_add_fee from opr_fax_in t where t.status=1 ) f")
             .append(" where r.d_no=f.d_no(+)");
		
		//sb.append(" and f.status(+)=1 ");//未传真作废
		String dno = map.get("EQL_dno");
		String sts[] = {"dno","GED_createTime","LED_createTime","GED_updateTime","LED_updateTime"};
		if(null!=dno && !"".equals(dno)){
			sb.append( " and r.d_no=:EQL_dno");
		}else{
			sb.append(this.appendConditions.appendCountDate(map));
			sb.append(this.appendConditions.appendConditions(map, sts));
		}
		return sb.toString();
		
	}
}

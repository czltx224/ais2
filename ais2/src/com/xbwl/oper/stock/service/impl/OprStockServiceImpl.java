package com.xbwl.oper.stock.service.impl;

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
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.entity.OprExceptionStock;
import com.xbwl.entity.OprFaxIn;
import com.xbwl.entity.OprRemark;
import com.xbwl.entity.OprReturnGoods;
import com.xbwl.entity.OprStatus;
import com.xbwl.entity.OprStock;
import com.xbwl.entity.OprStoreArea;
import com.xbwl.oper.fax.service.IOprFaxInService;
import com.xbwl.oper.reports.util.AppendConditions;
import com.xbwl.oper.stock.dao.IOprStockDao;
import com.xbwl.oper.stock.service.IOprExceptionStockService;
import com.xbwl.oper.stock.service.IOprHistoryService;
import com.xbwl.oper.stock.service.IOprRemarkService;
import com.xbwl.oper.stock.service.IOprStatusService;
import com.xbwl.oper.stock.service.IOprStockService;
import com.xbwl.oper.stock.service.IOprStoreAreaService;

@Service("oprStockServiceImpl")
@Transactional(rollbackFor = { Exception.class })
public class OprStockServiceImpl extends BaseServiceImpl<OprStock, Long>
		implements IOprStockService {

	@Resource(name = "oprStockDaoImpl")
	private IOprStockDao oprStockDao;
	
	@Resource(name = "oprStoreAreaServiceImpl")
	private IOprStoreAreaService oprStoreAreaService;
	
	@Resource(name="oprExceptionStockServiceImpl")
	private IOprExceptionStockService oprExceptionStockService;
	
	@Resource(name="oprFaxInServiceImpl")
	private IOprFaxInService oprFaxInService;
	
	@Resource(name="oprStatusServiceImpl")
	private IOprStatusService oprStatusService;
	
	@Resource(name="oprRemarkServiceImpl")
	private IOprRemarkService oprRemarkService;
	
	@Resource(name="oprHistoryServiceImpl")
	private IOprHistoryService oprHistoryService;
	
	@Value("${oprExceptionStockServiceImpl.log_normalToException}")
	private Long log_normalToException;
	
	@Resource(name="appendConditions")
	private AppendConditions appendConditions;
	
	@Override
	public IBaseDAO<OprStock, Long> getBaseDao() {
		return oprStockDao;
	}

	public IOprStockDao getOprStockDao() {
		return oprStockDao;
	}

	public void setOprStockDao(IOprStockDao oprStockDao) {
		this.oprStockDao = oprStockDao;
	}

	public void saveReturnGoodsStock(OprReturnGoods oprReturnGoods)
			throws Exception {
		List<OprStock> stockList = this.oprStockDao.find("from OprStock where dno=? and departId=?",oprReturnGoods.getDno(),oprReturnGoods.getReturnDepart());
		OprStock stockEntity = null;
		OprFaxIn fax = this.oprFaxInService.get(oprReturnGoods.getDno());
		if (null!=stockList &&stockList.size() > 0) {
			stockEntity = stockList.get(0);
		} else {
			List<OprStoreArea> oprStoreAreaList=oprStoreAreaService.findBy("departId", oprReturnGoods.getReturnDepart());
			OprStoreArea oprStoreArea =null;
			if(null!=oprStoreAreaList && oprStoreAreaList.size()>0){
				oprStoreArea = oprStoreAreaList.get(0);
			}
			String stockarea=null;
			try {
				if(null!=oprStoreArea){
					stockarea = this.oprStoreAreaService.getStockArea(oprStoreArea, fax);
				}else{
					stockarea="";
				}
			} catch (Exception e) {
				throw new ServiceException("库存区域获取失败！");
			}
			stockEntity = new OprStock();
			stockEntity.setAddr(fax.getAddr());
			stockEntity.setConsignee(fax.getConsignee());
			stockEntity.setDno(oprReturnGoods.getDno());
			stockEntity.setFlightMainNo(fax.getFlightMainNo());
			stockEntity.setFlightNo(fax.getFlightNo());
			stockEntity.setStorageArea(stockarea);
			stockEntity.setSubNo(fax.getSubNo());
		}
		stockEntity.setDepartId(oprReturnGoods.getReturnDepart());
		stockEntity.setPiece(stockEntity.getPiece()
				+ oprReturnGoods.getReturnNum());
		if(oprReturnGoods.getReturnType().equals(OprReturnGoodsServiceImpl.bufen)){
			stockEntity.setWeight((stockEntity.getPiece()/fax.getPiece())*fax.getCusWeight());
		}else if(oprReturnGoods.getReturnType().equals(OprReturnGoodsServiceImpl.zhengpiao)){
			stockEntity.setWeight(fax.getCusWeight());
		}
		this.oprStockDao.save(stockEntity);
	}

	public String getStockSql(Map<String, String> map)
			throws Exception {
		String[] sts = null;
		
		StringBuffer sb=new StringBuffer();
		
		sb.append("select enterStockTime,s.id as id,s.d_no as dno,f.distribution_mode as distributionMode,f.take_mode as takeMode,")
		  .append(" f.cp_name as cpName,f.consignee as consignee,f.addr as addr,f.REAL_GO_WHERE as REALGOWHERE,")
		  .append(" f.flight_main_no as flightMainNo,f.sub_no as subNo,f.flight_no as flightNo,to_char(f.create_time,'yyyy-MM-dd hh24:mi') as createTime,")
		  .append(" ROUND(TO_NUMBER(sysdate - s.create_time) * 24) as stockTime,")
		  .append(" f.goods as goods,f.GOODS_STATUS as GOODSSTATUS, s.piece as piece,s.weight as weight,")
		  .append(" f.bulk as bulk,s.depart_id as departId,O.end_depart_id as endDepartId,IS_EXCEPTION isException")
		  .append(" from opr_stock s,opr_fax_in f,opr_status a," )
		  .append("(select tp.d_no,min(tp.OVERMEMO) as OVERMEMO, to_char(min(tp.create_time),'yyyy-MM-dd hh24:mi') enterStockTime from")
		  .append(" OPR_OVERMEMO_DETAIL  TP group by tp.d_no ) t3,OPR_OVERMEMO O ")
		//.append(" where s.d_no=f.d_no  and s.d_no=t3.d_no and t3.OVERMEMO=O.id");
		.append(" where s.d_no=f.d_no(+)  and f.d_no=t3.d_no(+) and f.d_no=a.d_no and t3.OVERMEMO=O.id(+)");
		sb.append(" and f.status(+)=1 ");//未传真作废
		sb.append(appendConditions.appendConditions(map, sts));
		return sb.toString();
	}

	public void toExceptionStockService(String[] ids,String remark) throws Exception {
		OprExceptionStock entity =null;
		OprStock stock = null;
		List<OprExceptionStock>  list =null;
		OprFaxIn fax = null;
		OprStatus status = null;
		OprRemark oprRemark = null;
		Date dt = new Date();
		User user = WebRalasafe.getCurrentUser(ServletActionContext
				.getRequest());
		for (int i = 0; i < ids.length; i++) {
			stock = this.oprStockDao.get(Long.valueOf(ids[i]));
			if(null!=stock && stock.getPiece()>0){
				list = this.oprExceptionStockService.find("from OprExceptionStock where dno=? and departId=?", stock.getDno(),stock.getDepartId());
				if(null!=list && list.size()>0){
					entity = list.get(0);
					entity.setPiece(entity.getPiece()+stock.getPiece());
					entity.setWeight(entity.getWeight()+stock.getWeight());
				}else{
					entity = new OprExceptionStock();
					fax = this.oprFaxInService.get(stock.getDno());
					entity.setConsigneeAddr(stock.getAddr());
					entity.setConsignee(stock.getConsignee());
					entity.setDepartId(stock.getDepartId());
					entity.setDepartName(fax.getInDepart());
					entity.setDno(stock.getDno());
					entity.setPiece(stock.getPiece());
					entity.setWeight(stock.getWeight());
					
					entity.setConfigneeFee(fax.getConsigneeFee());
					entity.setCpFee(fax.getCpFee());
					entity.setCpName(fax.getCpName());
					entity.setDistributionMode(fax.getDistributionMode());
					entity.setGowhere(fax.getRealGoWhere());
					entity.setPaymentCollection(fax.getPaymentCollection());
					entity.setSourceType("正常转异常");
					entity.setSourceNo(stock.getId()+"");
				}
				entity.setExceptionEnterName(user.get("name").toString());
				entity.setExceptionEnterTime(dt);
				entity.setExceptionStatus(2l);
				this.oprExceptionStockService.save(entity);
				stock.setPiece(0l);
				stock.setWeight(0d);
				this.oprStockDao.save(stock);
				 status = this.oprStatusService.findBy("dno",entity.getDno()).get(0);
				status.setIsException(1l);//设置为异常状态
				this.oprStatusService.save(status);
				
				oprRemark = new OprRemark();//添加备注
				oprRemark.setRemark(remark);
				oprRemark.setDno(stock.getDno());
				this.oprRemarkService.save(oprRemark);
				
				this.oprHistoryService.saveLog(entity.getDno(),"转异常库存"+entity.getPiece()+"件。", this.log_normalToException);
			}else{
				throw new ServiceException("这票货没有异常库存！");
			}
		}	
	}
}

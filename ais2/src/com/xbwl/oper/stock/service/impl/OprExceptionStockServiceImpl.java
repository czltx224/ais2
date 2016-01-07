package com.xbwl.oper.stock.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.ralasafe.WebRalasafe;
import org.ralasafe.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdcn.bpaf.common.exception.ServiceException;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.entity.OprExceptionStock;
import com.xbwl.entity.OprFaxIn;
import com.xbwl.entity.OprRemark;
import com.xbwl.entity.OprReturnGoods;
import com.xbwl.entity.OprStatus;
import com.xbwl.entity.OprStock;
import com.xbwl.oper.fax.service.IOprFaxInService;
import com.xbwl.oper.stock.dao.IOprExceptionStockDao;
import com.xbwl.oper.stock.service.IOprExceptionStockService;
import com.xbwl.oper.stock.service.IOprHistoryService;
import com.xbwl.oper.stock.service.IOprRemarkService;
import com.xbwl.oper.stock.service.IOprReturnGoodsService;
import com.xbwl.oper.stock.service.IOprStatusService;
import com.xbwl.oper.stock.service.IOprStockService;
import com.xbwl.oper.stock.vo.OprExceptionStockVo;

/**异常库存管理服务层实现类
 * author CaoZhili time Nov 18, 2011 9:05:08 AM
 */
@Service("oprExceptionStockServiceImpl")
@Transactional(rollbackFor=Exception.class)
public class OprExceptionStockServiceImpl extends
		BaseServiceImpl<OprExceptionStock, Long> implements
		IOprExceptionStockService {

	@Resource(name="oprExceptionStockHibernateDaoImpl")
	private IOprExceptionStockDao oprExceptionStockDao;
	
	@Resource(name="oprStockServiceImpl")
	private IOprStockService oprStockService;
	
	@Resource(name="oprFaxInServiceImpl")
	private IOprFaxInService oprFaxInService;
	
	@Value("${oprExceptionStockServiceImpl.exceptionStockArea}")
	private String exceptionStockArea;
	
	@Value("${oprExceptionStockServiceImpl.log_exceptionToNormal}")
	private Long log_exceptionToNormal;
	
	@Value("${oprExceptionStockServiceImpl.log_exceptionOutStock}")
	private Long log_exceptionOutStock;
	
	@Resource(name="oprStatusServiceImpl")
	private IOprStatusService oprStatusService;
	
	@Resource(name="oprRemarkServiceImpl")
	private IOprRemarkService oprRemarkService;
	
	@Resource(name="oprHistoryServiceImpl")
	private IOprHistoryService oprHistoryService;
	
	@Resource(name="oprReturnGoodsServiceImpl")
	private IOprReturnGoodsService oprReturnGoodsService;
	
	private String sourceType="返货入库";
	
	@Override
	public IBaseDAO<OprExceptionStock, Long> getBaseDao() {
		return this.oprExceptionStockDao;
	}

	public void outStockService(OprExceptionStockVo vo)
			throws Exception {
		OprExceptionStock entity = this.oprExceptionStockDao.get(vo.getId());
		OprStatus status = null;
		if(null!=entity){
			if(null!=entity.getPiece() && entity.getPiece()>0){
				entity.setOutCost(vo.getOutCost());
				entity.setOutStockNo(vo.getOutStockNo());
				entity.setExceptionOutName(vo.getExceptionOutName());
				entity.setExceptionOutTime(vo.getExceptionOutTime());
				entity.setPiece(0l);
				entity.setWeight(0d);
				entity.setOutLoad(vo.getOutLoad());
				entity.setOutRemark(vo.getOutRemark());
				entity.setOutSender(vo.getOutSender());
				entity.setOutStockObj(vo.getOutStockObj());
				entity.setOutStockObjName(vo.getOutStockObjName());
				entity.setAddConfigneeFee(vo.getAddConfigneeFee());
				entity.setAddCpFee(vo.getAddCpFee());
				
				entity.setExceptionStatus(3l);
				
				this.oprExceptionStockDao.save(entity);
				
				status  = this.oprStatusService.findBy("dno",entity.getDno()).get(0);
				status.setOutStatus(2l);
				status.setReturnStatus(0l);
				status.setOutTime(vo.getExceptionOutTime());
				
				OprReturnGoods returnGoods = this.oprReturnGoodsService.get(Long.valueOf(entity.getSourceNo()));
				if(null!=returnGoods){
					returnGoods.setStatus(3l);//返货出库
					this.oprReturnGoodsService.save(returnGoods);
				}
				
				if(null!=entity.getOutRemark() && !"".equals(entity.getOutRemark().trim())){
					
					OprRemark oprRemark = new OprRemark();//添加备注
					oprRemark.setRemark(entity.getOutRemark());
					oprRemark.setDno(entity.getDno());
					this.oprRemarkService.save(oprRemark);
				}
				
				this.oprHistoryService.saveLog(entity.getDno(), entity.getOutStockObjName()+"异常出库,送货员为"+entity.getOutSender(), this.log_exceptionOutStock);
				this.oprStatusService.save(status);
				
			}else{
				throw new ServiceException("该单号没有库存！");
			}
			
		}else{
			throw new ServiceException("没找到要出库的异常库存单号！");
		}
	}

	public void saveReturnGoodsStock(OprReturnGoods oprReturnGoods)
			throws Exception {
		User user = WebRalasafe.getCurrentUser(ServletActionContext
				.getRequest());
		Date dt = new Date();
		Long departId = Long.parseLong(user.get("bussDepart").toString());
		List<OprExceptionStock> list = this.oprExceptionStockDao.find("from OprExceptionStock where dno=? and departId=?", oprReturnGoods.getDno(),departId);
		OprExceptionStock entity = null;
		OprFaxIn fax = this.oprFaxInService.get(oprReturnGoods.getDno());
		if(null!=list && list.size()>0){
			 entity  = list.get(0);
		}
		if(null==entity){
			entity = new OprExceptionStock();
			entity.setConfigneeFee(fax.getConsigneeFee());
			entity.setConsignee(fax.getConsignee());
			entity.setConsigneeAddr(fax.getAddr());
			entity.setCpFee(fax.getCpFee());
			entity.setCpName(fax.getCpName());
			entity.setDistributionMode(fax.getDistributionMode());
			entity.setDno(fax.getDno());
			entity.setGowhere(fax.getRealGoWhere());
			entity.setPaymentCollection(fax.getPaymentCollection());
			//entity.setReturnObj(oprReturnGoods.getDutyParty());//返货对象
		}
		if(oprReturnGoods.getReturnType().equals(OprReturnGoodsServiceImpl.bufen)){
			entity.setWeight((entity.getPiece()/fax.getPiece())*fax.getCusWeight());
		}else if(oprReturnGoods.getReturnType().equals(OprReturnGoodsServiceImpl.zhengpiao)){
			entity.setWeight(fax.getCusWeight());
		}else if(oprReturnGoods.getReturnType().equals(OprReturnGoodsServiceImpl.chailing)){
			entity.setWeight(0d);
		}
		entity.setReturnType(oprReturnGoods.getReturnType());
		entity.setSourceType(this.sourceType);
		entity.setSourceNo(oprReturnGoods.getId()+"");
		entity.setPiece(oprReturnGoods.getReturnNum());
		entity.setExceptionEnterName(user.get("name").toString());
		entity.setExceptionEnterTime(dt);
		entity.setExceptionStatus(2l);
		entity.setDepartId(departId);
		entity.setDepartName(user.get("rightDepart")+"");
		
		this.oprExceptionStockDao.save(entity);
	}

	public void toNormalStockService(String[] ids,String remark) throws Exception {
		OprExceptionStock entity = null;
		OprStock stock = null;
		List<OprStock> list = null;
		OprFaxIn fax = null;
		OprRemark oprRemark = null;
		OprStatus status = null;
		for (int i = 0; i < ids.length; i++) {
			entity= this.oprExceptionStockDao.get(Long.valueOf(ids[i]));
			if(null!=entity && entity.getPiece()>0){
				Long exceptionPiece = entity.getPiece();
				 list = this.oprStockService.find("from OprStock where dno=? and departId=?", entity.getDno(),entity.getDepartId());
				if(null!=list && list.size()>0){
					stock = list.get(0);
					stock.setPiece(stock.getPiece()+entity.getPiece());
					stock.setWeight(stock.getWeight()+entity.getWeight());
				}else{
					stock = new OprStock();
					fax = this.oprFaxInService.get(entity.getDno());
					stock.setAddr(entity.getConsigneeAddr());
					stock.setConsignee(entity.getConsignee());
					stock.setDepartId(entity.getDepartId());
					stock.setDno(entity.getDno());
					stock.setFlightMainNo(fax.getFlightMainNo());
					stock.setFlightNo(fax.getFlightNo());
					stock.setPiece(entity.getPiece());
					stock.setWeight(entity.getWeight());
					stock.setSubNo(fax.getSubNo());
	
				}
				
				
				stock.setStorageArea(this.exceptionStockArea);//设置为异常库存区域
				entity.setPiece(0l);
				entity.setWeight(0d);
				entity.setExceptionStatus(0l);
				this.oprStockService.save(stock);
				this.oprExceptionStockDao.save(entity);
				
				status  = this.oprStatusService.findBy("dno",entity.getDno()).get(0);
				status.setIsException(0l);//设置为正常状态
				this.oprStatusService.save(status);
				
				oprRemark = new OprRemark();//添加备注
				oprRemark.setRemark(remark);
				oprRemark.setDno(entity.getDno());
				this.oprRemarkService.save(oprRemark);
				
				this.oprHistoryService.saveLog(entity.getDno(),"转正常库存"+exceptionPiece+"件。", this.log_exceptionToNormal);
			
			}else{
				throw new ServiceException("这票货没有异常库存！");
			}
		}
	}

}

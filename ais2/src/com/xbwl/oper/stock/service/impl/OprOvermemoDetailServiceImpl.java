package com.xbwl.oper.stock.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.ralasafe.WebRalasafe;
import org.ralasafe.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdcn.bpaf.common.exception.ServiceException;
import com.xbwl.common.log.anno.ModuleName;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.common.utils.LogType;
import com.xbwl.entity.OprFaxIn;
import com.xbwl.entity.OprFaxMain;
import com.xbwl.entity.OprLoadingbrigadeWeight;
import com.xbwl.entity.OprOvermemo;
import com.xbwl.entity.OprOvermemoDetail;
import com.xbwl.entity.OprReceipt;
import com.xbwl.entity.OprRequestDo;
import com.xbwl.entity.OprStatus;
import com.xbwl.entity.OprStock;
import com.xbwl.entity.SysDepart;
import com.xbwl.oper.fax.service.IOprFaxInService;
import com.xbwl.oper.fax.service.IOprFaxMainService;
import com.xbwl.oper.receipt.dao.IOprReceiptDao;
import com.xbwl.oper.reports.util.AppendConditions;
import com.xbwl.oper.stock.dao.IOprOvermemoDao;
import com.xbwl.oper.stock.dao.IOprOvermemoDetailDao;
import com.xbwl.oper.stock.service.IOprHistoryService;
import com.xbwl.oper.stock.service.IOprLoadingbrigadeWeightService;
import com.xbwl.oper.stock.service.IOprOvermemoDetailService;
import com.xbwl.oper.stock.service.IOprRemarkService;
import com.xbwl.oper.stock.service.IOprRequestDoService;
import com.xbwl.oper.stock.service.IOprStatusService;
import com.xbwl.oper.stock.service.IOprStockService;
import com.xbwl.oper.stock.vo.LoadingbrigadeTypeEnum;
import com.xbwl.oper.stock.vo.OprFaxInSureVo;
import com.xbwl.oper.stock.vo.OprMathingGoods;
import com.xbwl.rbac.Service.IDepartService;

/**
 * author CaoZhili time Jul 2, 2011 2:43:38 PM
 * 
 * 交接单明细表服务层实现类
 */
@Service("oprOvermemoDetailServiceImpl")
@Transactional(rollbackFor={Exception.class})
public class OprOvermemoDetailServiceImpl extends
		BaseServiceImpl<OprOvermemoDetail, Long> implements
		IOprOvermemoDetailService {

	@Resource(name="oprOvermemoDetailHibernateDaoImpl")
	private IOprOvermemoDetailDao oprOvermemoDetailDao;
	
	@Resource(name = "oprStockServiceImpl")
	private IOprStockService oprStockService;
	
	@Resource(name = "oprHistoryServiceImpl")
	private IOprHistoryService oprHistoryService;
	
	@Resource(name="oprLoadingbrigadeWeightServiceImpl")
	private IOprLoadingbrigadeWeightService oprLoadingbrigadeWeightService;
	
	@Resource(name="oprRequestDoServiceImpl")
	private IOprRequestDoService oprRequestDoService;
	
	@Resource(name="oprFaxInServiceImpl")
	private IOprFaxInService oprFaxInService;
	
	@Resource(name = "oprStatusServiceImpl")
	private IOprStatusService oprStatusService;
	
	@Resource(name = "oprReceiptHibernateDaoImpl")
	private IOprReceiptDao oprReceiptDao;
	
	@Resource(name="oprOvermemoHibernateDaoImpl")
	private IOprOvermemoDao oprOvermemoDao;
	
	@Resource(name="departServiceImpl")
	private IDepartService departService;
	
	@Resource(name="appendConditions")
	private AppendConditions appendConditions;
	
	@Resource(name="oprFaxMainServiceImpl")
	private IOprFaxMainService oprFaxMainService;
	
	@Resource(name="oprRemarkServiceImpl")
	private IOprRemarkService oprRemarkService;
	
	@Value("${oprOvermemoDetailServiceImpl.log_enterStock}")
	private Long   log_enterStock;
	
	@Value("${oprOvermemoDetailServiceImpl.log_enterPartStock}")
	private Long   log_enterPartStock;
	
	@Value("${oprOvermemoDetailServiceImpl.log_cancelEnterStock}")
	private Long   log_cancelEnterStock;
	
	@Value("${oprOvermemoDetailServiceImpl.overmemoType}")
	private String  overmemoType;
	
	@Value("${oprReceiptServiceImpl.log_saveReport}")
	private Long  log_saveReport;
	
	@Value("${oprReceiptServiceImpl.log_cancelReport}")
	private Long  log_cancelReport;
	
	@Value("${enterStock.requestStage}")
	private String   requestStage;
	
	@Override
	public IBaseDAO<OprOvermemoDetail, Long> getBaseDao() {
		
		return this.oprOvermemoDetailDao;
	}

	public OprOvermemoDetail saveEnterStockByOprFaxInSureVo(OprFaxInSureVo faxVo,User user,OprOvermemoDetail overmemoDetail) throws Exception{
		//OprOvermemoDetail.status 0，为未点到，1为已经点到，2 部分点到
		Long allReachStatus = new Long(1l);//全部点到状态
		Long departReachStatus = new Long(2);//部分点到状态
		Date dt = new Date();
		Long enterStockNum=this.log_enterStock;//默认为入库点到
		//1.修改状态辅助表的点到状态 
		Long bussDepartId=Long.valueOf(user.get("bussDepart")+"");
		String bussDepartName = user.get("rightDepart")+"";
		OprStock oprStock = null;
		OprOvermemoDetail detail=null;
		OprFaxIn fax =  this.oprFaxInService.get(faxVo.getDno());;
		if(faxVo.getDno()<=0){
			throw new ServiceException("无传真的货物不允许点到！");
		}
		if(null==fax){
			throw new ServiceException("系统中不存在配送单号为'"+faxVo.getDno()+"'的货物！");
		}
		//修改传真表的当前部门为当前点到部门
		fax.setCurDepart(bussDepartName); //设置当前部门
		fax.setCurDepartId(bussDepartId);
		
		//计算已到件数
		List<OprOvermemoDetail> detailList = this.oprOvermemoDetailDao.find(" from OprOvermemoDetail where dno=? and (status=1 or status=2)",faxVo.getDno());
		Long sumRealPiece =0l;
		for (int j = 0; j < detailList.size(); j++) {
			if(detailList.get(j).getStatus()==1l || detailList.get(j).getStatus()==2l){//如果是已点到或者部分点到，就累加
				sumRealPiece+=(detailList.get(j).getRealPiece()==null?0:detailList.get(j).getRealPiece());
			}
		}
		if(null==overmemoDetail){
			if(faxVo.getOvermemoDetailId()!=null){
				detail=this.oprOvermemoDetailDao.get(faxVo.getOvermemoDetailId());
			}
			if(detail==null){
				detail=new OprOvermemoDetail();
				detail.setAddr(fax.getAddr());
				detail.setConsignee(fax.getConsignee());
				detail.setCpName(fax.getCpName());
				detail.setCusId(fax.getCusId());
				detail.setDistributionMode(fax.getDistributionMode());
				detail.setDno(fax.getDno());
				detail.setFlightMainNo(fax.getFlightMainNo());
				detail.setFlightNo(fax.getFlightNo());
				detail.setGoods(fax.getGoods());
				detail.setIsException(new Long(3));
				detail.setPiece(fax.getPiece());
				detail.setSubNo(fax.getSubNo());
				detail.setTakeMode(fax.getTakeMode());
				detail.setWeight(faxVo.getCqWeight());
			}
		}else{
			detail = overmemoDetail;
		}
		detail.setRealPiece(faxVo.getSurePiece());
		detail.setDepartId(bussDepartId);
		
		List<OprStatus> statuslist= this.oprStatusService.findBy("dno",faxVo.getDno());
		if(null==statuslist || statuslist.size()==0){
			throw new ServiceException("系统中不存在配送单号为'"+faxVo.getDno()+"'的货物！");
		}
		OprStatus st=statuslist.get(0);
		if(sumRealPiece+faxVo.getSurePiece()>=fax.getPiece()){
			enterStockNum = this.log_enterStock;
			st.setReachStatus(allReachStatus);//正常点到
			st.setReachTime(dt);
			st.setReachName(user.get("name")+"");				
			st.setDepartOvermemoStatus(0l);//修改部门交接状态
			this.oprStatusService.save(st);
			
			detail.setStatus(allReachStatus);//修改交接单明细状态
		}else if(sumRealPiece+faxVo.getSurePiece()<fax.getPiece()){
			enterStockNum=this.log_enterPartStock;//改为部分点到
			st.setReachStatus(departReachStatus);//部分点到
			st.setReachTime(dt);
			st.setReachName(user.get("name")+"");				
			st.setDepartOvermemoStatus(0l);//修改部门交接状态
			this.oprStatusService.save(st);
			
			detail.setStatus(departReachStatus);//修改交接单明细状态
		}
		this.oprOvermemoDetailDao.save(detail);
		
		String hql="from OprStock o where o.dno=? and o.departId=?";
		List<OprStock> stocklist =this.oprStockService.find(hql,faxVo.getDno(),bussDepartId);
		
		if (null != stocklist && stocklist.size() > 0) {// judge whether
			oprStock = stocklist.get(0);
		}else{
			//5.loop save to opr_stocks 循环修改库存
			oprStock = new OprStock();//如果没有则新建库存
			oprStock.setDno(fax.getDno());
			oprStock.setDepartId(bussDepartId);
			oprStock.setFlightNo(fax.getFlightNo());
			oprStock.setFlightMainNo(fax.getFlightMainNo());
			oprStock.setSubNo(fax.getSubNo());
			oprStock.setAddr(fax.getAddr());
			oprStock.setConsignee(fax.getConsignee());
		}
		oprStock.setPiece(faxVo.getSurePiece()+(oprStock.getPiece()==null?0:oprStock.getPiece()));//累计件数
		oprStock.setWeight(faxVo.getCqWeight()+(oprStock.getWeight()==null?0:oprStock.getWeight()));//累计重量
		this.oprStockService.save(oprStock);// Save to opr_stock table
		
		//修改备注
		if(null!=faxVo.getReqRemark() && !"".equals(faxVo.getReqRemark().trim())){
			this.oprRemarkService.saveRemark(faxVo.getDno(),faxVo.getReqRemark());//修改备注
		}
		//修改个性化要求执行表
		if(null!=faxVo.getRequestDoId() && faxVo.getRequestDoId()>0){
			OprRequestDo requestDo=this.oprRequestDoService.get(faxVo.getRequestDoId());
			requestDo.setIsOpr(faxVo.getIsOpr());
			requestDo.setRemark(faxVo.getReqRemark());//更新个性化要求表的备注
			requestDo.setOprMan(user.get("name")+"");
			this.oprRequestDoService.save(requestDo);
			
			this.oprHistoryService.saveLog(fax, "点到件数"+faxVo.getSurePiece()+"件。个性化要求："+(faxVo.getIsOpr()==1l?"执行":"未执行"), enterStockNum);
		}else{
			this.oprHistoryService.saveLog(fax, "点到件数"+faxVo.getSurePiece()+"件。", enterStockNum);
		}
	   //.修改回单表.....
		List<OprReceipt> list = this.oprReceiptDao.findBy("dno", faxVo.getDno());
		if(null!=list && list.size()>0){
			OprReceipt receipt=list.get(0);
			//修改为入库状态
			if(receipt.getReceiptType().indexOf("原件")>-1){
				receipt.setReachNum(faxVo.getSplitNum());
				receipt.setReachStatus(1l);
				receipt.setReachMan(user.get("name")+"");
				receipt.setReachTime(dt);
				receipt.setCurStatus("已入库");
				this.oprReceiptDao.save(receipt);
				this.oprHistoryService.saveLog(receipt.getDno(), "回单入库份数为"+receipt.getReachNum()+"份", log_saveReport);
			}
		}
		return detail;
	}

	@ModuleName(value="入库点到确认",logType=LogType.buss)
	public void doEnterReport(List<OprFaxInSureVo> overIds, User user) throws Exception{
		OprLoadingbrigadeWeight oprLoadingbrigadeWeight = null;
		List<OprLoadingbrigadeWeight> weightList = new ArrayList<OprLoadingbrigadeWeight>();
		for (int i = 0; i < overIds.size(); i++){
			OprFaxInSureVo faxVo = overIds.get(i);
			OprFaxIn fax =  this.oprFaxInService.get(faxVo.getDno());
			//入库点到
			OprOvermemoDetail detail = saveEnterStockByOprFaxInSureVo(faxVo,user,null);//修改为点到状态
			
			//4.loop save to opr_loadingbrigade_weight
			oprLoadingbrigadeWeight=new OprLoadingbrigadeWeight();//记录装卸组和分拨组
			oprLoadingbrigadeWeight.setOvermemoNo(faxVo.getOvermemoNo());
			oprLoadingbrigadeWeight.setWeight(faxVo.getCqWeight());
			oprLoadingbrigadeWeight.setPiece(faxVo.getSurePiece());
			oprLoadingbrigadeWeight.setLoadingbrigadeId(faxVo.getLoadingbrigadeId());
			oprLoadingbrigadeWeight.setDispatchId(faxVo.getDispatchId());
			oprLoadingbrigadeWeight.setBulk(fax.getBulk());
			oprLoadingbrigadeWeight.setDno(fax.getDno());
			oprLoadingbrigadeWeight.setGoods(fax.getGoods());
			oprLoadingbrigadeWeight.setOvermemoDetailId(detail.getId());
			weightList.add(oprLoadingbrigadeWeight);
		}
		//保存到装卸组货量表
		this.oprLoadingbrigadeWeightService.saveLoadingWeight(weightList, LoadingbrigadeTypeEnum.XIEHUO);
	}

	@ModuleName(value="发车确认信息汇总",logType=LogType.buss)
	public String getSumInfoByIds(String ids,Long bussdepartId) {
		StringBuffer sb = new StringBuffer();
		if(!"".equals(ids)&&ids!=null){
			if(ids.endsWith(",")){
				ids = ids.substring(0,ids.length()-1);
			}
			sb.append(" select sum(o.total_ticket) totalTicket,sum(o.total_piece)  " );
			sb.append(" totalPiece,sum(o.total_weight) totalWeight  	");
			sb.append(" from opr_overmemo o where o.start_depart_id =  "+bussdepartId+" and id in( "+ids+"  )  ");
		}
		return sb.toString();
	}

	@ModuleName(value="有货无单单据匹配",logType=LogType.buss)
	public void saveMatchingService(List<OprMathingGoods> mathlist)throws Exception {
		
		for (int i = 0; i <mathlist.size() ; i++) {
			OprMathingGoods mathingGoods = mathlist.get(i);
			if(null==mathingGoods.getId()){
				throw new ServiceException("交接单明细编号编号为空！");
			}
			OprOvermemo overmemo = this.oprOvermemoDao.get(mathingGoods.getOvermemoNo());
			if(null==overmemo){
				throw new ServiceException("系统中不存在交接单号为"+mathingGoods.getOvermemoNo()+"的货物！");
			}
			OprFaxIn fax=null;
			OprOvermemoDetail entity =null;
			OprFaxMain faxMain =this.oprFaxMainService.get(mathingGoods.getMainId());
			if(null==faxMain){
				throw new ServiceException("系统中不存在主单号为"+mathingGoods.getMainId()+"的货物！");
			}
			List<OprFaxIn> faxList = this.oprFaxInService.findBy("flightMainNo",faxMain.getFlightMainNo());
			User user = WebRalasafe.getCurrentUser(ServletActionContext
					.getRequest());
			OprLoadingbrigadeWeight oprLoadingbrigadeWeight = null;
			List<OprLoadingbrigadeWeight> weightList = new ArrayList<OprLoadingbrigadeWeight>();
			
			Iterator<OprFaxIn> itr =faxList.iterator();
			while(itr.hasNext()){
				fax=itr.next();
				
				OprStatus status = this.oprStatusService.findStatusByDno(fax.getDno());
				if(status.getReachStatus()>0){
					throw new ServiceException("配送单号为"+fax.getDno()+"的货物已经点到！");
				}
				
				entity = new OprOvermemoDetail();
				entity.setAddr(fax.getAddr());
				entity.setConsignee(fax.getConsignee());
				entity.setCpName(fax.getCpName()); 
				entity.setCusId(fax.getCusId());
				entity.setDistributionMode(fax.getDistributionMode());
				entity.setDno(fax.getDno());
				entity.setFlightMainNo(fax.getFlightMainNo());
				entity.setFlightNo(fax.getFlightNo());
				entity.setGoods(fax.getGoods());
				entity.setWeight(fax.getCusWeight());
				entity.setTakeMode(fax.getTakeMode());
				entity.setSubNo(fax.getSubNo());
				entity.setPiece(fax.getPiece());
				entity.setOprOvermemo(overmemo);
				entity.setIsException(1l);//设置为异常
				
				//查询入库阶段的个性化要求
				List<OprRequestDo> requestList = this.oprRequestDoService.find("from OprRequestDo where dno=? and requestStage=?", fax.getDno(),this.requestStage);
				Long requestDoId = null;
				if(null!=requestList && requestList.size()>0){
					requestDoId = requestList.get(0).getId();
				}
				
				//判断是否需要点到
				if(null!=mathingGoods.getReachFlag() && mathingGoods.getReachFlag()){
					OprFaxInSureVo faxVo = new OprFaxInSureVo();
					faxVo.setDno(fax.getDno());
					faxVo.setCqWeight(fax.getCusWeight());
					faxVo.setSurePiece(fax.getPiece());
					faxVo.setOvermemoDetailId(mathingGoods.getId());
					faxVo.setRequestDoId(requestDoId);
					faxVo.setSplitNum(1l);
					
					OprOvermemoDetail detail = this.saveEnterStockByOprFaxInSureVo(faxVo, user, entity);
					
					oprLoadingbrigadeWeight=new OprLoadingbrigadeWeight();//记录装卸组和分拨组
					oprLoadingbrigadeWeight.setOvermemoNo(faxVo.getOvermemoNo());
					oprLoadingbrigadeWeight.setWeight(faxVo.getCqWeight());
					oprLoadingbrigadeWeight.setPiece(faxVo.getSurePiece());
					oprLoadingbrigadeWeight.setLoadingbrigadeId(faxVo.getLoadingbrigadeId());
					oprLoadingbrigadeWeight.setDispatchId(faxVo.getDispatchId());
					oprLoadingbrigadeWeight.setBulk(fax.getBulk());
					oprLoadingbrigadeWeight.setDno(fax.getDno());
					oprLoadingbrigadeWeight.setGoods(fax.getGoods());
					oprLoadingbrigadeWeight.setOvermemoDetailId(detail.getId());
					weightList.add(oprLoadingbrigadeWeight);
				}else{
					this.oprOvermemoDetailDao.save(entity);
				}
			}
			//先删除掉交接单细表中的数据
			this.oprOvermemoDetailDao.delete(mathingGoods.getId());
			//保存到装卸组货量表
			this.oprLoadingbrigadeWeightService.saveLoadingWeight(weightList, LoadingbrigadeTypeEnum.XIEHUO);
		}
	}

	@ModuleName(value="撤销点到",logType=LogType.buss)
	public void revokedOvermemoService(List<OprFaxInSureVo> overIds,User user)
			throws Exception {
		//1.判断是否可以撤销
		Long bussDepartId=Long.valueOf(user.get("bussDepart")+"");
		OprStatus oprst=null;
		for (int i = 0; i < overIds.size(); i++) {
			List<OprStatus> statuslist = this.oprStatusService.findBy("dno",overIds.get(i).getDno());
			if(null!=statuslist && statuslist.size()>0){
				oprst = statuslist.get(0);
				
				if(oprst.getOutStatus()==1l){
					throw new ServiceException(overIds.get(i).getDno()+"的配送单货物已经正常出库，不允许撤销点到！");
				}
//				else if(oprst.getOutStatus()==2l){
//					throw new ServiceException(overIds.get(i).getDno()+"的配送单货物已经异常出库，不允许撤销点到！");
//				}
			}
		}
		//3. loop modify to opr_stocks
		OprStock oprStock = null;
		OprOvermemoDetail detail=null;
		for (int i = 0; i < overIds.size(); i++) {
			detail = this.oprOvermemoDetailDao.getAndInitEntity(overIds.get(i).getOvermemoDetailId());
			
			String hql="from OprStock o where o.dno=? and o.departId=?";
			
			List<OprStock> stocklist =this.oprStockService.find(hql,overIds.get(i).getDno(),bussDepartId);
				//this.oprStockDao.find(filters);
			if (null == stocklist || stocklist.size() == 0) {// judge whether
				throw new ServiceException("库存表中没有该配送单记录");
			}
			oprStock = stocklist.get(0);
			
			List<OprStatus> statuslist= this.oprStatusService.findBy("dno",overIds.get(i).getDno());
			
			if(null==statuslist || statuslist.size()==0){
				//throw new ServiceException("状态辅助表没有改配送单号！");
			}else{
				OprStatus st=statuslist.get(0);
				
				OprOvermemo oo =this.oprOvermemoDao.get(overIds.get(i).getOvermemoNo());
				if(null!=oo){
					if(oo.getOvermemoType().equals(overmemoType)){
						
						st.setDepartOvermemoStatus(2l);
						OprFaxIn fax =  this.oprFaxInService.get(overIds.get(i).getDno());
						if(null!=fax){
							SysDepart depart =this.departService.get(oo.getStartDepartId());
							fax.setCurDepart(null==depart?"":depart.getDepartName()); //部门名称怎么拿？
							fax.setCurDepartId(oo.getStartDepartId());
							this.oprHistoryService.saveLog(fax, "撤销点到，库存减少"+detail.getRealPiece()+"件", log_cancelEnterStock);
						}
					}else{
						this.oprHistoryService.saveLog(overIds.get(i).getDno(), "撤销点到，库存减少"+detail.getRealPiece()+"件", log_cancelEnterStock);
					}
				}else{
					this.oprHistoryService.saveLog(overIds.get(i).getDno(), "撤销点到，库存减少"+detail.getRealPiece()+"件", log_cancelEnterStock);
				}
				st.setReachStatus(0l);
//				st.setReachTime(null);
//				st.setReachName("");
				this.oprStatusService.save(st);
			}
			
			oprStock.setPiece(oprStock.getPiece()-detail.getRealPiece());
			oprStock.setWeight(oprStock.getWeight()-detail.getWeight());
			this.oprStockService.save(oprStock);// Save to opr_stock table
			
			if(null!=overIds.get(i).getOvermemoNo() && overIds.get(i).getOvermemoNo()>0){
				OprOvermemo main=this.oprOvermemoDao.getAndInitEntity(overIds.get(i).getOvermemoNo());
				main.setTotalPiece(main.getTotalPiece()+overIds.get(i).getSurePiece());
				main.setTotalWeight(main.getTotalWeight()+overIds.get(i).getCqWeight());
				main.setTotalTicket(main.getTotalTicket()+1l);
				this.oprOvermemoDao.save(main);
			}
			
			detail.setRealPiece(0l);
			detail.setStatus(0l);
			
			this.oprOvermemoDetailDao.save(detail);
		}
		
		//4.loop delete to opr_loadingbrigade_weight
		for (int i = 0; i < overIds.size(); i++){
			if(null!=overIds.get(i).getLoadingbrigadeWeightId() && overIds.get(i).getLoadingbrigadeWeightId()>0){
				this.oprLoadingbrigadeWeightService.delete(overIds.get(i).getLoadingbrigadeWeightId());
			}
			
		}
		//6修改个性化要求执行表
		for (int i = 0; i < overIds.size(); i++){
			if(null==overIds.get(i).getRequestDoId() || overIds.get(i).getRequestDoId()==0l){
				continue;
			}
			OprRequestDo requestDo=this.oprRequestDoService.get(overIds.get(i).getRequestDoId());
			
			requestDo.setIsOpr(0l);
			this.oprRequestDoService.save(requestDo);
		}
		
		//7.修改回单表.....
		for (int i = 0; i < overIds.size(); i++) {
			List<OprReceipt> list = this.oprReceiptDao.findBy("dno", overIds.get(i).getDno());
			
			if(null!=list && list.size()>0){
				OprReceipt receipt=list.get(0);
				//修改为未入库状态
				receipt.setReachNum(0l);
				receipt.setReachStatus(0l);
				receipt.setReachMan("");
				receipt.setReachTime(null);
				receipt.setCurStatus("");
				this.oprReceiptDao.save(receipt);
				this.oprHistoryService.saveLog(receipt.getDno(), "撤销回单份数为"+receipt.getReachNum()+"份", log_cancelReport);
			}
		}
		
	}

	@ModuleName(value="入库点到获取查询语句",logType=LogType.buss)
	public String getSqlRalaListService(Map<String, String> map)
			throws Exception {
		
		String LIKES_carCode =map.get("LIKES_carCode");
		String EQS_overmemoId =map.get("EQS_overmemoId");
		String EQS_status =map.get("EQS_status");
		String EQS_endDepartId =map.get("EQS_endDepartId");
		String ORDERFIELDS =map.get("ORDERFIELDS");
		String EQL_flightMainNo = map.get("EQL_flightMainNo");
		String cpName = map.get("LIKES_cpName");
		OprOvermemo overmemo = null;
		
 		StringBuffer sb = new StringBuffer();
 		sb.append("SELECT  ID  AS id , OVERMEMO  AS OVERMEMOID , nvl(REALPIECE,0)  AS REALPIECE ,")
 		  .append(" REQUEST  AS REQUEST , IS_OPR  AS ISOPR ,CAR_CODE  AS CARCODE , CAR_ID  AS CARID ,")
 		  .append("FLIGHT_MAIN_NO AS FLIGHTMAINNO , BULK  AS BULK ,flight_time as flighttime,flightInfo,")
 		  .append("ADDR  AS ADDR , GOODS  AS GOODS , TAKE_MODE  AS TAKEMODE ,STORAGE_AREA as STOCKAREANAME,")
 		  .append(" DISTRIBUTION_MODE  AS DISTRIBUTIONMODE , FLIGHT_NO  AS  FLIGHTNO,")
 		  .append(" nvl(PIECE,0)  AS PIECE , nvl(CQ_WEIGHT,0)  AS CQWEIGHT , ORDERFIELDS  AS ORDERFIELDS ,")
 		  .append(" REMARK  AS REMARK , SUB_NO  AS SUBNO , CP_NAME  AS CPNAME ,")
 		  .append(" CUS_ID  AS CUSID , D_NO AS DNO , STATUS  AS STATUS ,RECEIPT_TYPE as RECEIPTTYPE, ")
 		  .append(" nvl(piece,0) as totalPiece,nvl((nvl(piece,0)-nvl(realpiece,0)),0) as shouldPiece,")
 		  //添加收货人信息
 		  .append(" nvl(consignee,'') as consignee,nvl(CONSIGNEE_TEL,'') as CONSIGNEETEL,")
 		  .append(" END_DEPART_ID  AS ENDDEPARTID ,REQUESTDOID as REQUESTDOID,0 as splitNum");
 		  sb.append(" from (SELECT t0.ID, t0.OVERMEMO, t6.REALPIECE,t1.REQUEST ,t1.IS_OPR ,t3.CAR_CODE ,")
               .append("t3.CAR_ID ,t5.FLIGHT_MAIN_NO ,t5.flight_time as flight_time,t5.flight_no||'/'||t5.flight_time as flightInfo,")
               .append("t5.BULK ,t5.ADDR  ,t5.GOODS  ,t5.TAKE_MODE ,t0.STORAGE_AREA,t5.DISTRIBUTION_MODE ,")
               .append("t5.FLIGHT_NO ,t5.CQ_WEIGHT  ,t3.ORDERFIELDS ,t1.REMARK ,t5.SUB_NO ,")
               .append("t5.CP_NAME ,t5.CUS_ID ,t0.D_NO ,t0.status status ,t5.RECEIPT_TYPE,t5.piece,nvl((nvl(t5.piece,0)-nvl(t6.realpiece,0)),0) as shouldPiece,")
               .append("t5.consignee,t5.CONSIGNEE_TEL,t3.END_DEPART_ID ,t1.REQUESTDOID ");
 		  sb.append(" FROM  OPR_OVERMEMO_DETAIL t0 , ")
	 		  .append("(select  max(tp.id) as REQUESTDOID, tp.d_no,tp.request, tp.is_opr, tp.remark from OPR_REQUEST_DO tp where tp.REQUEST_STAGE=:requestStage group by tp.request, tp.is_opr,tp.remark,tp.d_no) t1 ,")
	 		  .append(" OPR_OVERMEMO t3 , OPR_FAX_IN t5 ,opr_status s,")
	 		  .append("  ( select p.d_no,sum(p.real_piece) as REALPIECE from ")
	 		  .append("  opr_overmemo t, opr_overmemo_detail p")
	 		  .append(" where p.overmemo =t.id(+) and t.end_depart_id(+)=:EQS_endDepartId and p.status in (1,2)")
	 		  ///.append(" and t.overmemo_type in (select t.type_name from bas_dictionary_detail t where t.dictionary_id=11 )")
	 		  .append(" group by p.d_no) t6 ")
	 		  .append(" WHERE  (   t0.D_NO  =  t1.D_NO(+)   AND   t0.D_NO =  t5.D_NO(+) and t5.d_no=s.d_no ");
 		 sb.append(" and t5.status(+)=1 ");//未传真作废
 		 //只查询交接单
 		 sb.append(" and t3.overmemo_type in (select t.type_name from bas_dictionary_detail t where t.dictionary_id=11 )");
 		 sb.append(" AND   t0.OVERMEMO  =  t3.ID and t0.d_no=t6.d_no(+) and t3.STATUS>=1 and t3.status<=3 )  )where 1=1 ");
 		 
 		if(null!=EQS_status && !"".equals(EQS_status)){
 			sb.append(" AND   status  =  :EQS_status ");
 		}
  		
  		if(null!=EQS_endDepartId && !"".equals(EQS_endDepartId)){
 			sb.append(" AND   end_Depart_Id  =  :EQS_endDepartId ");
 		}
  		
  		if(null!=LIKES_carCode && !"".equals(LIKES_carCode)){
 			sb.append(" AND   CAR_CODE  LIKE  '%'||:LIKES_carCode||'%'");
 		}
 		if(null!=EQS_overmemoId && !"".equals(EQS_overmemoId)){
 			overmemo = this.oprOvermemoDao.get(Long.valueOf(EQS_overmemoId));
 			sb.append(" AND   OVERMEMO  =  :EQS_overmemoId ");
 		}
 		if(null!=EQL_flightMainNo && !"".equals(EQL_flightMainNo)){
 			sb.append(" AND   FLIGHT_MAIN_NO  =  :EQL_flightMainNo ");
 		}
 		if(null!= cpName && !"".equals(cpName)){
 			sb.append(" and CP_NAME like '%'||:LIKES_cpName||'%'");
 		}
		sb.append(" order by OVERMEMOID");//OVERMEMO
		
		if(null!=overmemo){
			sb.append(",").append(overmemo.getOrderfields()==null?"":overmemo.getOrderfields());
		}
		
		if(null!=ORDERFIELDS && !"".equals(ORDERFIELDS)){
			sb.append(",").append(ORDERFIELDS);
		}
		return sb.toString();
	}

	public List<OprOvermemoDetail> findDetailByDno(Long dno) throws Exception {
		return this.createSQLQuery("select t.* from OPR_OVERMEMO_DETAIL t where t.d_no in("+dno+")", null).list();
	}
	
	@ModuleName(value="交接单查询SQL获取",logType=LogType.buss)
	public String overmemoSearchService(Map<String, String> map)
			throws Exception {
		StringBuffer sb = new StringBuffer();
		
		sb.append("select t.id,t.start_depart_id startDepartId,t.end_depart_id endDepartId,to_char(t.start_time,'yyyy-MM-dd hh24:mi') startTime,")
		     .append("to_char(t.end_time,'yyyy-MM-dd hh24:mi') endTime,to_char(t.unload_start_time,'yyyy-MM-dd hh24:mi') as unloadStartTime,to_char(t.unload_end_time,'yyyy-MM-dd hh24:mi') unloadEndTime,")
		     .append("t.overmemo_type overmemoType,t.car_id carId,t.car_code carCode,t.status,t.remark,")
		     .append("t.create_time createTime,t.create_name createName,t.update_time updateTime,t.update_name updateName,")
		     .append("t.ts,t.orderfields,t.lock_no lockNo,t.total_ticket totalTicket,t.total_piece totalPiece,t.total_weight totalWeight,")
		     .append("t.end_depart_name endDepartName,t.start_depart_name startDepartName,t.route_number routeNumber,")
		     .append("r.print_num printNum,t.use_car_type useCarType,t.rent_car_result rentCarResult,r.is_separate_delivery isseparatedelivery")
		     .append(" from opr_overmemo t,opr_sign_route r where t.route_number=r.route_number(+)");
		
		//添加日期条件
		sb.append(appendConditions.appendCountDate(map));
		
		//添加条件
		sb.append(appendConditions.appendConditions(map,null));
		
		return sb.toString();
	}

	@ModuleName(value="交接单明细查询SQL获取",logType=LogType.buss)
	public String overmemoDetailSearchService(Map<String, String> map)
			throws Exception {
		StringBuffer sb = new StringBuffer();
		String[] sts = new String[]{"countDate"};
		
		sb.append("select t.overmemo,t.id,t.d_no as dno,t.real_piece realPiece,t.piece,t.cus_id cusId,t.cp_name cpName,")
		     .append("t.weight,t.is_exception isException,t.create_name createName,to_char(t.create_time,'yyyy-MM-dd hh24:mi') createTime,t.update_name updateName,")
		     .append("t.update_time updateTime,t.sub_no subNo,t.flight_no flightNo,t.consignee,t.addr,t.status,t.distribution_mode distributionMode,")
		     .append("t.take_mode takeMode,t.goods,t.flight_main_no flightMainNo,t.storage_area storageArea,t.RENUM,")
		     .append("o.car_code carCode,o.end_depart_name endDepartName,o.start_depart_name startDepartName,")
		     .append("f.CONSIGNEE_FEE CONSIGNEEFEE,f.CP_FEE CPFEE,o.overmemo_type overmemoType,to_char(o.START_TIME,'yyyy-MM-dd hh24:mi') STARTTIME,")
		     .append("to_char(o.END_TIME,'yyyy-MM-dd hh24:mi') ENDTIME")
		     .append(" from opr_overmemo_detail t,opr_overmemo o,opr_fax_in f")
		     .append(" where t.overmemo = o.id(+) and t.d_no=f.d_no(+)");
		    // .append(" and (o.end_depart_id=:bussDepart or o.start_depart_id=:bussDepart)");
		
		//添加日期条件
		sb.append(appendConditions.appendCountDate(map));
		
		//添加条件
		sb.append(appendConditions.appendConditions(map,sts));
		
		return sb.toString();
	}

	@ModuleName(value="未到主单跟踪查询SQL获取",logType=LogType.buss)
	public String findNotReportTracking(Map<String, String> map)
			throws ServiceException {
		
		StringBuffer sb = new StringBuffer();
		String[] sts = new String[]{"reachStatus","bussDepart"};
		String reachStatus = map.get("reachStatus");
		sb.append("select rownum,e.* from (");
		sb.append("select f.flight_no,f.flight_main_no,f.cp_name,sum(f.piece) piece ,sum(f.cq_weight) weight,")
		  .append("to_char(f.create_time,'yyyy-MM-dd') create_time,f.flight_time,l.start_city,")
		  .append("sum(f.piece)-sum(nvl(t.real_piece,0)) notPiece,WMSYS.wm_concat(t.clob_status_realpiece) clob_status_realpiece")
		  .append(" from opr_fax_in f,bas_flight l,opr_status s,")
		  //.append("(select d_no,sum(real_piece) real_piece,opr_overmemo.status from opr_overmemo_detail,opr_overmemo")
		  //.append(" where opr_overmemo_detail.overmemo=opr_overmemo.id(+)")
		  .append("(select d_no,sum(real_piece) real_piece,WMSYS.wm_concat((opr_overmemo.status||':'||real_piece||'_'||nvl(total_piece,0))) clob_status_realpiece")
		  .append(" from opr_overmemo_detail,opr_overmemo")
		  .append(" where opr_overmemo_detail.overmemo = opr_overmemo.id(+)")
		  .append(" and opr_overmemo_detail.status in (1,2) ")
		  //.append(" opr_overmemo_detail.status in (1,2) and opr_overmemo.end_depart_id(+)=:bussDepart")
		  .append("  and opr_overmemo_detail.depart_id=:bussDepart and opr_overmemo.end_depart_id(+)=:bussDepart")
		  //.append(" group by d_no,opr_overmemo.status ) t")
		  .append(" group by d_no) t")
		  .append(" where f.d_no=t.d_no(+) and f.flight_no=l.flight_number(+) and f.d_no=s.d_no");
		
		//Action中加了限制
		//sb.append(" and f.status=1 ");//未传真作废  
		//if(null!=reachStatus &&!"".equals(reachStatus)){
		//	sb.append(" and s.reach_status!=:reachStatus");
		//}
		try{
			//添加日期条件
			sb.append(appendConditions.appendCountDate(map));
			
			//添加条件
			sb.append(appendConditions.appendConditions(map,sts));
			
			sb.append(" group by f.flight_no,f.flight_main_no,f.cp_name,")
			  .append(" to_char(f.create_time,'yyyy-MM-dd'),f.flight_time,l.start_city");
			sb.append(" having sum(f.piece)-sum(nvl(t.real_piece,0))>0");
		}catch (Exception e) {
			throw new ServiceException("添加条件失败！");
		}
		sb.append(") e");
		sb.append(" order by e.flight_time,e.flight_main_no desc");
		return sb.toString();
	}
	@ModuleName(value="汇总货物未到主单信息查询SQL",logType=LogType.buss)
	public String totalNotReportTracking(Map<String, String> map)
			throws ServiceException {
		String str = findNotReportTracking(map);
		StringBuffer sb = new StringBuffer();
		//System.out.println(str.substring(0,str.indexOf(("group by"))));
		sb.append("select sum(tt.piece) totalPiece,sum(tt.weight) totalWeight,count(*) totalNum,sum(tt.notPiece) totalNotPiece");
		sb.append(" from (").append(str.substring(0,str.indexOf(("order by")))).append(" ) tt");
		return sb.toString();
	}

	@ModuleName(value="有货无单查询SQL获取",logType=LogType.buss)
	public String findNoFaxListService(Map<String, String> map)
			throws Exception {
		StringBuffer sb = new StringBuffer();
		String flightMainNo = map.get("flightMainNo");
		sb.append("select t.overmemo,t.id ,t.d_no,t.piece,t.weight,t.flight_main_no,t.status,")
		  .append("o.start_depart_id,o.start_depart_name,to_char(o.end_time,'yyyy-MM-dd hh24:mi') end_time,o.remark")
		  .append(" from opr_overmemo_detail t ,opr_overmemo o")
		  .append(" where t.overmemo=o.id and t.d_no<0");
		
		sb.append(this.appendConditions.appendCountDate(map));
		
		if(null!=flightMainNo &&!"".equals(flightMainNo.trim())){
			sb.append(" and flight_main_no like '%'||:flightMainNo||'%'");
		}
		
		return sb.toString();
	}

	public String findNotReportTrackDetail(Map<String, String> map)
			throws Exception {
		String flightMainNo = map.get("flightMainNo");
		StringBuffer sb = new StringBuffer();
		sb.append("select f.*,s.reach_status from opr_fax_in f,opr_status s where f.d_no=s.d_no");
		if(null==flightMainNo || "".equals(flightMainNo.trim())){
			throw new ServiceException("主单号为空！");
		}
		sb.append(" and f.flight_main_no=:flightMainNo");
		return sb.toString();
	}

	public String findOvermemoDetail(Map<String, String> map) throws Exception {
		StringBuffer sb = new StringBuffer();
		String[] sts = new String[]{"bussDepart"};
		sb.append("select t.id idd,t.cus_id cusId,t.cp_name cpName,t.flight_no flightNo,t.consignee,t.addr,t.goods,")
		  .append("t.SUB_NO subNo,t.weight,t.d_no dno,t.STORAGE_AREA stockAreaName,t.overmemo overmemoId,")
		  .append("to_char(t.CREATE_TIME,'yyyy-MM-dd hh24:mi') createTime,")
		  .append("t.piece,t.status,l.LOADING_NAME loadingbrigadeName,g.LOADING_NAME dispatchGroup,t.FLIGHT_MAIN_NO flightMainNo,")
		  .append("f.GOODS_STATUS goodsStatus,f.REAL_GO_WHERE realGoWhere,")
		  .append("o.END_DEPART_ID,o.car_code carCode,t.REAL_PIECE realPiece,")
		  .append("w.id loadingbrigadeWeightId")
		  .append(" from OPR_OVERMEMO_DETAIL t,OPR_OVERMEMO o,OPR_FAX_IN f,OPR_LOADINGBRIGADE_WEIGHT w,")
		  .append("BAS_LOADINGBRIGADE l,BAS_LOADINGBRIGADE g");
		sb.append(" where t.overmemo=o.id(+) and t.d_no=f.d_no(+) and t.d_no=w.d_no(+)")
		  .append(" and w.LOADINGBRIGADE_ID=l.id(+) and w.DISPATCH_ID=g.id(+)");
		sb.append(" and t.id=w.OVERMEMO_DETAIL_ID(+)");
		sb.append(" and w.depart_id(+)=:bussDepart and o.end_depart_id(+)=:bussDepart and t.depart_id=:bussDepart");//当前业务部门
		sb.append(" and (o.overmemo_type in (SELECT t11.TYPE_NAME  AS t11_TYPE_NAME")
		  .append(" FROM  aisuser.BAS_DICTIONARY_DETAIL t11")    
		  .append(" WHERE t11.dictionary_id  = 11) or o.overmemo_type is null)");
		sb.append(" and t.status in (1,2)");
		//添加日期条件
		sb.append(this.appendConditions.appendCountDate(map));
		//添加查询条件
		sb.append(this.appendConditions.appendConditions(map, sts));
		sb.append(" order by t.d_no desc");
		
		return sb.toString();
	}
}

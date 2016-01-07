package com.xbwl.finance.Service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.hibernate.Query;
import org.ralasafe.WebRalasafe;
import org.ralasafe.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.log.anno.ModuleName;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.common.utils.DoubleUtil;
import com.xbwl.common.utils.LogType;
import com.xbwl.entity.FiCost;
import com.xbwl.entity.FiOutcost;
import com.xbwl.entity.OprFaxIn;
import com.xbwl.entity.OprStatus;
import com.xbwl.finance.Service.IFiCostService;
import com.xbwl.finance.Service.IFiOutCostService;
import com.xbwl.finance.dao.IFiOutCostDao;
import com.xbwl.finance.dto.IFiInterface;
import com.xbwl.finance.dto.impl.FiInterfaceProDto;
import com.xbwl.oper.fax.dao.IOprFaxInDao;
import com.xbwl.oper.stock.dao.IOprStatusDao;
import com.xbwl.oper.stock.service.IOprHistoryService;
import com.xbwl.oper.stock.service.IOprStatusService;

/**
 *@author LiuHao
 *@time Aug 29, 2011 5:50:26 PM
 */
@Service("fiOutCostServiceImpl")
@Transactional(rollbackFor={Exception.class})
public class FiOutCostServiceImpl extends BaseServiceImpl<FiOutcost,Long> implements
		IFiOutCostService {
	
	@Resource(name="fiOutCostHibernateDaoImpl")
	private IFiOutCostDao fiOutCostDao;
	
	@Resource(name="oprFaxInHibernateDaoImpl")
	private IOprFaxInDao oprFaxInDao;
	
	@Resource(name="oprStatusHibernateDaoImpl")
	private IOprStatusDao oprStatusDao;
	
	@Resource(name="fiInterfaceImpl")
	private IFiInterface fiInterface;
	
	@Value("${fiAuditCost.log_auditCost}")
	private Long log_auditCost ;
	
	@Value("${fiAuditCost.log_qxAuditCost}")
	private Long log_qxAuditCost ;
	
	@Resource(name = "oprHistoryServiceImpl")
	private IOprHistoryService oprHistoryService;
	
	@Resource(name = "fiCostServiceImpl")
	private IFiCostService fiCostService;
	
	@Resource(name = "fiInterfaceImpl")
	private IFiInterface fiInterfaceImpl;
	
	//传真状态表
	@Resource(name = "oprStatusServiceImpl")
	private IOprStatusService oprStatusService;
	
	@Override
	public IBaseDAO getBaseDao() {
		return fiOutCostDao;
	}
	
	//外发成本审核
	@ModuleName(value="外发成本审核",logType=LogType.fi)
	public String outCostAduit(List<FiOutcost>aa ,Long batchNo) throws Exception {
		User user= WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
		Long bussDepartId=Long.parseLong(user.get("bussDepart")+"");
		
		String hql="from FiOutcost fo where fo.batchNo=? and fo.isdelete=1 and fo.departId=? and fo.customerId=?  ";
		List<FiOutcost>listFoct =find(hql,batchNo,bussDepartId,aa.get(0).getCustomerId());
		Map<Long,Double>map = new HashMap<Long, Double>();
		
		for(FiOutcost fiOct:listFoct){
			if(fiOct.getStatus()==1l){
				throw new ServiceException("存在已审核的数据，不能提交审核");         //状态判断
			}
			if(fiOct.getIsdelete()==0l){
				throw new  ServiceException("存在作废的数据不能审核");  // 数据删除判断
			}
			
		/*	if(!bussDepartId.equals(fiOct.getDepartId())){   //不允许操作非本部门的数据
				throw new ServiceException("请勿操作非本业务部门的数据");
			}*/
			
			if(map.get(fiOct.getDno())==null){  //金额写入Map，再回写传真表和状态表
				map.put(fiOct.getDno(), fiOct.getAmount());
			}else{
				Double sDouble =map.get(fiOct.getDno());
				map.put(fiOct.getDno(), DoubleUtil.add(sDouble,fiOct.getAmount()));
			}
			
			//审核状态回写，时间戳判断
			fiOct.setStatus(1l);
			fiOct.setReviewUser(user.get("name").toString());
			fiOct.setReviewDate(new Date());
			fiOutCostDao.save(fiOct);
			
			oprHistoryService.saveLog(fiOct.getDno(), "支付外发成本，支付金额："+fiOct.getAmount() , log_auditCost);     //操作日志
			FiCost ficost=new FiCost();
			ficost.setDno(fiOct.getDno());
			ficost.setCostType("外发成本");
			ficost.setSourceSignNo(fiOct.getId()+"");
			ficost.setDataSource("外发成本");
			ficost.setCostAmount(fiOct.getAmount());
			ficost.setCostTypeDetail(fiOct.getSourceData());
			fiCostService.save(ficost);
			

		}
		
	

		String s =changeOprStatus(map,listFoct.get(0),bussDepartId);   //更改状态
		return s;
	}

	/* 
	 * 撤销成本审核
	 */
	@ModuleName(value="外发撤销成本审核",logType=LogType.fi)
	public String qxFiAduit(Long id, String ts) throws Exception {
		User user= WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
		Long bussDepartId=Long.parseLong(user.get("bussDepart")+"");

		FiOutcost fiOutCost =fiOutCostDao.getAndInitEntity(id);
	/*	if(!bussDepartId.equals(fiOutCost.getDepartId())){
			throw new ServiceException("请勿操作非本业务部门的数据");
		}*/
		
		//时间戳判断
		fiOutCost.setTs(ts);
		
		List<OprStatus> listo =oprStatusDao.findBy("dno", fiOutCost.getDno()); //取货物状态  当货物返货一次，不用回写成本审核状态
		OprStatus oprStatus=null;    
		if(listo.size()==1){
			oprStatus=listo.get(0);
		}else{
			throw new ServiceException("取货物状态时出错");
		}
		
		String hql="from FiOutcost fo where fo.dno=? and fo.isdelete=1 and fo.status=1 and fo.departId=? and fo.customerId=? ";
		List<FiOutcost>list =find(hql,fiOutCost.getDno(),fiOutCost.getDepartId(),fiOutCost.getCustomerId());  
		double totaAmount=0.0;    //总计需要撤销的外发成本
		for (FiOutcost fiOutcost2 : list) {
			if(fiOutcost2.getPayStatus()==1l){
				throw new ServiceException("部分已支付,不能撤销会计审核");
			}
			
			fiOutcost2.setStatus(0l);
			if(fiOutCost.getId()-fiOutcost2.getId()==0){
				fiOutcost2.setTs(ts);
			}
			fiOutcost2.setReviewDate(null);
			fiOutcost2.setReviewUser(null);
			fiOutCostDao.save(fiOutcost2);
			totaAmount=DoubleUtil.add(fiOutcost2.getAmount(), totaAmount);
			
			if("传真录入".equals(fiOutcost2.getSourceData())){
				oprStatus.setFeeAuditStatus(0l);
				oprStatus.setFeeAuditTime(new Date());
				oprStatusDao.save(oprStatus);
			}
		}
		
		FiCost fiCostNew = new FiCost();  // 对冲成本金额
		fiCostNew.setCostType("外发成本");
		fiCostNew.setCostTypeDetail("撤销审核");
		fiCostNew.setCostAmount(- totaAmount);
		fiCostNew.setDataSource("外发成本");
		fiCostNew.setDno(list.get(0).getDno());
		fiCostNew.setStatus(1l);
		fiCostService.save(fiCostNew);  //重新
		
		List<FiInterfaceProDto> listPro = new ArrayList<FiInterfaceProDto>();   // 调用财务接口 取消应收应付
		FiInterfaceProDto fiInterfaceProDto = new FiInterfaceProDto()	;
		fiInterfaceProDto.setCostType("外发费");
		fiInterfaceProDto.setSourceData("外发成本");
		fiInterfaceProDto.setDno(fiOutCost.getDno());
		fiInterfaceProDto.setDocumentsNo(fiOutCost.getDno());
		fiInterfaceProDto.setSourceNo(fiOutCost.getId());
		fiInterfaceProDto.setCustomerId(fiOutCost.getCustomerId()	);
		listPro.add(fiInterfaceProDto);
		fiInterfaceImpl.invalidToFi(listPro);
		
		oprHistoryService.saveLog(fiOutCost.getDno(), "外发成本审核撤销，撤销金额："+totaAmount , log_qxAuditCost);     //操作日志
		return null;
	}
	
	/*
	 * 核销外发成本
	 */
	@ModuleName(value="核销外发成本",logType=LogType.fi)
	public void payConfirmationBybatchNo(Long batchNo) throws Exception{
		//FiOutcost fiOutcost=this.fiOutCostDao.get(id);
		List<FiOutcost> list=this.fiOutCostDao.findBy("batchNo", batchNo);
		if(list==null) throw new ServiceException("外发成本中根据批次号:"+batchNo+"未找到数据！");
		User user= WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
		for(FiOutcost fiOutcost:list){
			fiOutcost.setPayStatus(1L);
			fiOutcost.setPayTime(new Date());
			fiOutcost.setPayUser(user.get("name").toString());
			this.fiOutCostDao.save(fiOutcost);
			
			//成本支付状态
			OprStatus oprStatus=this.oprStatusService.findStatusByDno(fiOutcost.getDno());
			if(oprStatus!=null){
				oprStatus.setPayTra(1L);
				oprStatus.setPayTraTime(new Date());
				this.oprStatusService.save(oprStatus);
			}
		}
		
	}
	
	public void payConfirmationRegisterBybatchNo(Long batchNo) throws Exception{
		List<FiOutcost> list=this.fiOutCostDao.findBy("batchNo", batchNo);
		if(list==null) throw new ServiceException("外发成本中根据批次号:"+batchNo+"未找到数据！");
		User user= WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
		for(FiOutcost fiOutcost:list){
			fiOutcost.setPayStatus(0L);
			fiOutcost.setPayTime(null);
			fiOutcost.setPayUser(null);
			this.fiOutCostDao.save(fiOutcost);
			
			//撤销成本支付状态
			OprStatus oprStatus=this.oprStatusService.findStatusByDno(fiOutcost.getDno());
			if(oprStatus!=null){
				oprStatus.setPayTra(0L);
				oprStatus.setPayTraTime(null);
				this.oprStatusService.save(oprStatus);
			}
		}
	}

	//返货审核时，外发审核状态提醒
	@ModuleName(value="返货时，返发成本审核状态提醒",logType=LogType.fi)
	public String returnGoodsPrompt(Long dno, Long departId) throws Exception {
		StringBuffer sb= new StringBuffer();
		sb.append("配送单号为").append(dno).append("的货物，外发成本");
		OprFaxIn oprFaxIn  = oprFaxInDao.get(dno);
		List<FiOutcost>list =find("from FiOutcost fo where fo.dno=? and fo.isdelete=1 and fo.sourceData=? and fo.departId=? and fo.customerId=? and fo.amount>0 ",dno,"传真录入",departId,oprFaxIn.getGoWhereId());
		if(list.size()>0){
			sb.append("已录入");
			if(1l==list.get(0).getStatus()){
				sb.append("已审核");
				if(list.get(0).getPayStatus()==1l){
					sb.append("已支付");
				}else{
					sb.append("未支付");
				}
			}else{
				sb.append("未审核");
			}
			sb.append("外发成本为").append(list.get(0).getAmount()).append("元");
		}else{
			sb.append("未录入");
		}
		sb.append("，请确认。");
		return sb.toString();
	}

	//外发成本作废
	@ModuleName(value="外发成本作废",logType=LogType.fi)
	public String delOutcostData(Long id, String ts) throws Exception {
		FiOutcost fiOutcost=get(id);
		if(!"传真录入".equals(fiOutcost.getSourceData())){
			throw new ServiceException("数据来源非传真录入的外发成本，不允许删除");
		}
		fiOutcost.setTs(ts);
		fiOutcost.setIsdelete(0l);
		save(fiOutcost);
		return null;
	}

	//重写save方法，根据条件保存
	public void save(FiOutcost entity) {
		if(entity.getId()==null){
			User user= WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
			Long bussDepartId=Long.parseLong(user.get("bussDepart")+"");
			
			OprFaxIn oprFaxIn=oprFaxInDao.get(entity.getDno());  //取货物的传真记录
			if(oprFaxIn==null){
				throw new ServiceException("此配送单号的货物不存在！");
			}
			
			List<OprStatus> listo =oprStatusDao.findBy("dno", entity.getDno()); //取货物状态
			OprStatus oprStatus=null;    
			if(listo.size()==1){
				oprStatus=listo.get(0);
			}else{
				throw new ServiceException("取货物状态时出错");
			}
			
			if(entity.getAmount()>0){
				if(oprStatus.getOutStatus()-1l!=0&&oprStatus.getOutStatus()-2l!=0){
					throw new ServiceException("货物未出库，不允许录入外发成本");
				}
	
				String hql="from FiOutcost fo where fo.dno=? and fo.isdelete=1 and fo.sourceData=? and fo.departId=? and fo.customerId=? and fo.amount>0 ";
				if(oprStatus.getReturnStatus()==0l){   // 当货物已返货时，不做约束;未返货只能录一次
					List<FiOutcost>listFo =find(hql,entity.getDno(),"传真录入",bussDepartId,entity.getCustomerId());  //判断是否录入外发成本  如果没有录入 则不允许录入负数
					if(listFo.size()>0){
						throw new ServiceException("不能多次录入外发成本");
					}
				}
			}else if(entity.getAmount()<0){
				String hql="from FiOutcost fo where fo.dno=? and fo.isdelete=1 and fo.sourceData=? and fo.departId=? and fo.amount>0 ";
				List<FiOutcost>list =find(hql,entity.getDno(),"传真录入",bussDepartId);  //判断是否录入外发成本  如果没有录入 则不允许录入负数
				if(list.size()>0){
					if(oprFaxIn.getGoWhereId()!=null){
						if(oprFaxIn.getGoWhereId()-entity.getCustomerId()!=0&&entity.getCustomerId()-list.get(0).getCustomerId()!=0){  
							throw new ServiceException("不能录入其他外发客商的成本");
						}
					}
	//				if(oprFaxIn.getGoWhereId()-entity.getCustomerId()==0){    //判断是否第二次出库
	///*					boolean flag=true;
	//					for(FiOutcost fiOutcost:list) {
	//						if(fiOutcost.getCustomerId()-entity.getCustomerId()==0){
	//							flag=true;
	//							break;
	//						}
	//					}
	//					if(flag){
	//						throw new ServiceException("您还没有录入外发成本，不允许录入负成本");
	//					}*/
	//				}
				}else{
					throw new ServiceException("您还没有录入外发成本，不允许录入负成本");
				}
			}else{
				throw new ServiceException("录入的外发成本为零，没有意义");
			}
		}
		fiOutCostDao.save(entity);
	}

	//撤销审核信息提示
	@ModuleName(value="外发成本撤销审核信息提示",logType=LogType.fi)
	public String qxAmountCheck(Long id) throws Exception {
		FiOutcost fiOutcost =fiOutCostDao.getAndInitEntity(id);
		String hql="from FiOutcost fo where fo.dno=? and fo.isdelete=1 and fo.status=1 and fo.departId=? and fo.customerId=? ";
		List<FiOutcost>list =find(hql,fiOutcost.getDno(),fiOutcost.getDepartId(),fiOutcost.getCustomerId());  
		double totaAmount=0.0;
		for (FiOutcost fiOutcost2 : list) {
			totaAmount=DoubleUtil.add(fiOutcost2.getAmount(), totaAmount);
		}
		StringBuffer sb=new StringBuffer();
		sb.append("撤销外发成本总计<span  style='color:red'> ").append(list.size()).append(" </span>行,")
			.append("金额<span  style='color:red'> ").append(totaAmount).append(" </span>元");
		return sb.toString();
	}

	/* 
	 * 审核时信息提示
	 * @see com.xbwl.finance.Service.IFiOutCostService#aduitAmountCheck(java.util.List)
	 */
	 @ModuleName(value="外发成本审核信息提示",logType=LogType.fi)
	public Map<String,String> aduitAmountCheck(List<FiOutcost> aa) throws Exception {
		User user= WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
		Long bussDepartId=Long.parseLong(user.get("bussDepart")+"");
		StringBuffer sb=new StringBuffer(); 
		Long batchNo=getOutcostBatchNo();
		Map<String, String>map=new HashMap<String, String>();
		sb.append("审核外发成本总计:");
		for(FiOutcost fiOct:aa){
			int num=0;
			double totaAmount=0.0;
			String hql="from FiOutcost fo where fo.dno=? and fo.isdelete=1 and fo.status=0 and fo.departId=? ";
			List<FiOutcost>list =find(hql,fiOct.getDno(),bussDepartId);  
			for(FiOutcost fiOutcost:list){
				if(fiOutcost.getBatchNo()!=null&&fiOutcost.getBatchNo()-batchNo==0l){  //如果批次号相等，说明前一个迭代已计算了审核的金额，跳出继续循环
					continue;
				}else{
					fiOutcost.setBatchNo(batchNo); //写入批次号
					save(fiOutcost);
					num=num+1;  //总条数
					totaAmount=DoubleUtil.add(totaAmount, fiOutcost.getAmount());  // 总金额数
				}
			}
			if(num!=0){
				sb.append("配送单号 ").append(fiOct.getDno()).append(" 合计<span  style='color:red'> ").append(list.size()).append(" </span>行,")
					 .append("金额<span  style='color:red'> ").append(totaAmount).append(" </span>元。</b>");
			}
		}
		map.put("auditInfo", sb.toString());
		map.put("batchNo", batchNo+"");
		return map;
	}

	// 审核时回写状态表  调用财务接口
	public String changeOprStatus(Map<Long, Double>map,FiOutcost fiOutcost,Long bussDepartId) throws Exception{
		List<FiInterfaceProDto> list=new ArrayList<FiInterfaceProDto>();
		String msg=null;
		Iterator iter = map.entrySet().iterator();
		while (iter.hasNext()){
			Map.Entry entry = (Map.Entry)iter.next();
			Long dnoLong = (Long) entry.getKey();
			Double value = (Double)entry.getValue();
			//应收应付财务接口
			FiInterfaceProDto fipd=new FiInterfaceProDto();
			fipd.setAmount(value==null?0.0:value);
			fipd.setCustomerId(fiOutcost.getCustomerId());
			fipd.setCustomerName(fiOutcost.getCustomerName());
			fipd.setDno(dnoLong);
			fipd.setDocumentsType("成本");
			fipd.setDocumentsSmalltype("配送单");
			fipd.setDistributionMode("客商");
			fipd.setSettlementType(2l);
			fipd.setDocumentsNo(dnoLong);
			fipd.setCostType("外发费");
			fipd.setDepartId(bussDepartId);
			fipd.setSourceData("外发成本");
			fipd.setSourceNo(fiOutcost.getBatchNo());
			fipd.setCreateRemark("支付外发公司"+fiOutcost.getCustomerName()+"外发费"+value+"元");
			list.add(fipd);
			
			//状态回写
			List<OprStatus> listo =oprStatusDao.findBy("dno", dnoLong);
			if(listo.size()==0){
				throw new ServiceException("当前数据的配送单状态不存在，不能审核");
			} 

			StringBuffer sb = new StringBuffer();  //  获取总的审核费用，回写传真表
			sb.append("select sum(t.amount) amount from fi_outcost t where t.status=1 and t.depart_id=? and t.isdelete=1 and t.d_no=? " );
			Query query = createSQLQuery(sb.toString(), bussDepartId,dnoLong);
			if(query!=null){
				OprFaxIn ofi=oprFaxInDao.get(dnoLong);
				Map mapCost=(Map) query.list().get(0);
				double amount=Double.valueOf(mapCost.get("AMOUNT")+"");
				ofi.setTraFee(amount);   //回写中转费/外发费
			//	oprFaxInDao.save(ofi);
			}
			
			 listo.get(0).setFeeAuditStatus(1l);   // 回写状态的成本审核状态
			 listo.get(0).setFeeAuditTime(new Date());
			 oprStatusDao.save( listo.get(0));
		}
		msg =fiInterface.addFinanceInfo(list);    //调用财务接口
		return msg;
	}
	
	
	
	/* 
	 * 获取批次号
	 * @see com.xbwl.finance.Service.IFiOutCostService#getOutcostBatchNo()
	 */
	public Long getOutcostBatchNo() throws Exception {
		Map  batch = (Map)createSQLQuery("  select SEQ_FI_OUTCOST_BATCHNO.nextval batchNo from  dual  ").list().get(0);
		Long batchNo =Long.valueOf(batch.get("BATCHNO")+"");
		return batchNo;
	}
	
	
}

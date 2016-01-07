package com.xbwl.finance.Service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.transform.Transformers;
import org.ralasafe.WebRalasafe;
import org.ralasafe.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.log.anno.ModuleName;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.common.utils.DoubleUtil;
import com.xbwl.common.utils.LogType;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.entity.FiBusinessFee;
import com.xbwl.entity.FiCost;
import com.xbwl.finance.Service.IFiBusinessFeeService;
import com.xbwl.finance.dao.IFiBusinessFeeDao;
import com.xbwl.finance.dao.IFiCostDao;
import com.xbwl.finance.dto.IFiInterface;
import com.xbwl.finance.dto.impl.FiInterfaceProDto;
import com.xbwl.oper.fax.dao.IOprFaxInDao;
import com.xbwl.oper.fax.service.IOprFaxInService;
import com.xbwl.sys.service.ICustomerService;

/**
 * author CaoZhili time Oct 17, 2011 3:39:16 PM 业务费管理服务层实现类
 */
@Service("fiBusinessFeeServiceImpl")
@Transactional(rollbackFor = { Exception.class })
public class FiBusinessFeeServiceImpl extends
		BaseServiceImpl<FiBusinessFee, Long> implements IFiBusinessFeeService {

	@Resource(name = "fiBusinessFeeHibernateDaoImpl")
	private IFiBusinessFeeDao fiBusinessFeeDao;

	@Resource(name="customerServiceImpl")
	private ICustomerService customerService;
	
	@Resource(name = "oprFaxInHibernateDaoImpl")
	private IOprFaxInDao oprFaxInDao;
	
	@Resource(name="fiCostHibernateDaoImpl")
	private IFiCostDao fiCostDao;
	
	@Resource(name="fiInterfaceImpl")
	private IFiInterface fiInterface;
	
	@Override
	public IBaseDAO<FiBusinessFee, Long> getBaseDao() {
		return this.fiBusinessFeeDao;
	}

	@ModuleName(value="佣金管理审核保存",logType=LogType.fi)
	public void auditStatusService(String[] idStrings, Long status,
			String workflowNo,Double amount) throws Exception {
		User user=WebRalasafe.getCurrentUser(Struts2Utils.getRequest());
		Long bussDepartId  = Long.valueOf(user.get("bussDepart")+"");
		FiBusinessFee entity = null;
		List<FiInterfaceProDto> listfiInterfaceDto = new ArrayList<FiInterfaceProDto>();
		FiInterfaceProDto dto=null;
		for (int i = 0; i < idStrings.length; i++) {
			entity = this.fiBusinessFeeDao.get(Long.valueOf(idStrings[i]));
			if(entity.getStatus()==2l){
				throw new ServiceException("该业务费已经审核！");
			}else if(entity.getStatus()==0l){
				throw new ServiceException("该业务费已经作废！");
			}
			entity.setStatus(status);
			entity.setAmount(amount);
			if (null != workflowNo && !"".equals(workflowNo)) {
				entity.setWorkflowNo(workflowNo);
			}
			if(status==2l){
				String cusNameString = this.customerService.get(entity.getCollectionCustomerId()).getCusName();
				dto = new FiInterfaceProDto();
				dto.setCustomerId(entity.getCollectionCustomerId());
				dto.setCustomerName(cusNameString);
				dto.setDistributionMode("客商");
				dto.setSettlementType(2l);//2:取款/付款
				dto.setDocumentsType("业务费");
				dto.setDocumentsSmalltype("业务费单");
				dto.setDocumentsNo(entity.getId());
				//dto.setCollectionUser(collectionUser);
				dto.setAmount(entity.getAmount());
				dto.setCostType("业务费");
				//dto.setDepartId(entity.getBelongDepartId());
				dto.setSourceData("业务费");
				dto.setSourceNo(entity.getId());
				//dto.setOutStockMode(outStockMode);
				//备注格式：支付_收款客商_业务月份_业务费_流程号（流程号）
				dto.setCreateRemark("支付_"+cusNameString+"_"+entity.getBusinessMonth()+"_"+entity.getAmount()+"_流程号("+workflowNo+")");
				dto.setDepartId(Long.valueOf(user.get("bussDepart")+""));
				dto.setDepartName(user.get("rightDepart")+"");
				listfiInterfaceDto.add(dto);
				
				StringBuffer sumSb = new StringBuffer();  //总询总条数，和最后一个配送单号
				sumSb.append("select max(d_no) dno,count(*)  DNOSIZE from opr_fax_in t ");
				sumSb.append(" where t.cus_id=  ")
							  .append(entity.getCustomerId())
							  .append(" and status=1  and to_char(t.create_time,'yyyy-MM')='").append(entity.getBusinessMonth())
							  .append("'");
				List<Map> list = oprFaxInDao.getSession().createSQLQuery(sumSb.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
				Long size=Long.parseLong(list.get(0).get("DNOSIZE")+"");
				Long maxDno=Long.parseLong(list.get(0).get("DNO")+"");
				double amo =DoubleUtil.div(entity.getAmount(), size, 2);
				
				//写入成本表
				StringBuffer sb = new StringBuffer();
				sb.append("insert into fi_cost (id,cost_type,cost_type_detail,data_source,source_sign_no,create_name,create_time,")
					 .append(" update_name,update_time,ts,depart_id,depart_name,d_no,status,cost_amount ) ");
				sb.append("select Seq_fi_cost.Nextval,'其他成本','业务费','业务费管理','")
					 .append(entity.getId()).append("','").append(user.get("name"))
					 .append("',sysdate,'").append(user.get("name").toString())
					 .append("',sysdate,").append("'13800138000',").append(bussDepartId).append(",'")
					 .append(user.get("rightDepart"))
					 .append("',t.d_no,1, ").append(amo).append(" from opr_fax_in t where t.cus_id=")
					 .append(entity.getCustomerId()).append(" and status=1  and to_char(t.create_time,'yyyy-MM')='").append(entity.getBusinessMonth())
					 .append("'");
				
				fiCostDao.batchSQLExecute(sb.toString());  //批量修改 插入成本
				
				if(entity.getAmount()-DoubleUtil.mul(amo, size)>0.0){  //分摊成本，如果还有余数，插入最后一票的成本上面
					FiCost fiCost = new FiCost();
					fiCost.setCostType("其他成本");
					fiCost.setDataSource("业务费管理");
					fiCost.setCostAmount(entity.getAmount()-DoubleUtil.mul(amo, size));
					fiCost.setCostTypeDetail("业务费");
					fiCost.setSourceSignNo(entity.getId()+"");
					fiCost.setStatus(1l);
					fiCost.setDno(maxDno);
					fiCostDao.save(fiCost);
				}
				/*
				List<Map> list = oprFaxInDao.getSession().createSQLQuery(sb.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
				if(list.size()>0){
					double totalAmou = entity.getAmount();
					double amou=DoubleUtil.div(entity.getAmount(), list.size(), 2);
					for(int j=0;j<list.size();j++){
						
						FiCost fiCost = new FiCost();
						fiCost.setCostType("中转成本");
						fiCost.setDataSource("中转成本");
						if(j==list.size()-1){
							fiCost.setCostAmount(amou);
							totalAmou=DoubleUtil.sub(totalAmou, amou);
						}else{
							fiCost.setCostAmount(totalAmou);
						}
						fiCost.setCostTypeDetail("返货登记");
						fiCost.setDataSource("中转成本");
						fiCost.setCostTypeDetail("传真录入");
						fiCost.setStatus(1l);
						fiCost.setDno(Long.valueOf(list.get(i).get("CQWEIGHT").toString()));
						fiCostDao.save(fiCost);
					}
				}*/
			}
			
			this.fiBusinessFeeDao.save(entity);
		}
		this.fiInterface.addFinanceInfo(listfiInterfaceDto);
	}

	public void save(FiBusinessFee entity) {
	  if(entity.getId()==null){
			List<FiBusinessFee> list = this.fiBusinessFeeDao.createQuery("from FiBusinessFee where customerId=? and status!=0 and departId=? and collectionCustomerId=?  and businessMonth=? ",
							entity.getCustomerId(),entity.getBelongDepartId(),entity.getCollectionCustomerId(), entity.getBusinessMonth()).list();
			if (null != list && list.size() > 0) {
				throw new ServiceException("该客商这个月的业务费已经存在！");
			}
		}
		super.save(entity);
	}
}

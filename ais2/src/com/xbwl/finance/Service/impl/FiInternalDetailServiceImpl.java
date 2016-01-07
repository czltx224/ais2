package com.xbwl.finance.Service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.common.utils.XbwlInt;
import com.xbwl.entity.FiInternalDetail;
import com.xbwl.entity.FiInternalDivide;
import com.xbwl.finance.Service.IFiInternalDetailService;
import com.xbwl.finance.dao.IFiInternalDetailDao;

/**
 * author CaoZhili time Oct 20, 2011 1:52:34 PM
 */
@Service("fiInternalDetailServiceImpl")
@Transactional(rollbackFor = { Exception.class })
public class FiInternalDetailServiceImpl extends
		BaseServiceImpl<FiInternalDetail, Long> implements
		IFiInternalDetailService {

	@Resource(name="fiInternalDetailHibernateDaoImpl")
	private IFiInternalDetailDao fiInternalDetailDao;
	
	private String dataSource="内部划分";
	
	@Override
	public IBaseDAO<FiInternalDetail, Long> getBaseDao() {
		return this.fiInternalDetailDao;
	}

	public String reportListService(Map<String, String> map) throws Exception {
		StringBuffer sb = new StringBuffer();
		String belongsDepartId = map.get("belongsDepartId");
		String status = map.get("status");
		String dnoString =map.get("dno");
		//REVIEW 直接对象append，不利于阅读
		sb.append("select to_char(t.create_time,'yyyy-MM-dd hh24:mi:ss') create_time,t.d_no,")
		     .append(" case SETTLEMENT_TYPE")
		     .append(" when 1 then sum(amount)")
		     .append(" end as shouru,")
		     .append(" case SETTLEMENT_TYPE")
		     .append(" when 2 then sum(amount)")
		     .append(" end as zhichu,")
		     .append(" t.distribution_mode,")
		     .append("  t.belongs_depart_id,t.belongs_depart_name,")
		     .append(" sum(f.cq_weight) weight,sum(f.piece) piece,sum(f.bulk) bulk,")
		     .append(" f.take_mode,f.area_type,f.consignee,f.cp_name")
		     //REVIEW 去除1=1条件
		     .append(" from (select * from fi_internal_detail where 1=1 ");
			    if(null!=dnoString && !"".equals(dnoString)){
			    	sb.append(" and d_no =:dno");
			    }
			     if(null!= belongsDepartId && !"".equals(belongsDepartId)){
						sb.append(" and belongs_depart_id =:belongsDepartId");
				}
			     if(null!= status && !"".equals(status)){
						sb.append(" and status =:status");
				}
			    
		     sb.append(") t,")
		     .append("opr_fax_in f")
		     .append(" where t.d_no=f.d_no(+)")
		     .append(" group by t.create_time,t.d_no,t.distribution_mode,")
		     .append(" t.belongs_depart_id,t.belongs_depart_name,")
		     .append(" f.take_mode,f.area_type,f.consignee,f.cp_name,SETTLEMENT_TYPE");
		    
		
		return sb.toString();
	}

	
	@XbwlInt(isCheck=false)
	public void saveTwoDepart(FiInternalDivide entity) throws Exception {
		
		FiInternalDetail detail = new FiInternalDetail();
		detail.setAmount(entity.getAmount());
		detail.setBelongsDepartId(entity.getBearDepartId());//承担部门
		detail.setBelongsDepartName(entity.getBearDepartName());
		detail.setStartDepartId(entity.getBearDepartId());
		detail.setStartDepartName(entity.getBearDepartName());
		detail.setDno(entity.getDno());
		detail.setEndDepartId(entity.getBenefitDepartId());
		detail.setEndDepartName(entity.getBenefitDepartName());
		detail.setRemark("备注:"+entity.getRemark()+",审核备注:"+entity.getAuditRemark());
		detail.setSourceData(dataSource);
		detail.setSourceNo(entity.getId());
		detail.setSettlementType(2l);//结算类型(1:收入、2:支出)
		detail.setStatus(1l);
		
		FiInternalDetail detail2 =(FiInternalDetail)detail.clone();
		detail2.setBelongsDepartId(entity.getBenefitDepartId());//受益部门
		detail2.setBelongsDepartName(entity.getBenefitDepartName());
		detail2.setSettlementType(1l);//结算类型(1:收入、2:支出)
		
		this.fiInternalDetailDao.save(detail);
		this.fiInternalDetailDao.save(detail2);
	}

	public void cancelAuditDivideService(Long divideId) throws Exception {
		//要找到原先审核的两条记录
		//通过数据来源和数据来源编号 确定唯一性
		List<FiInternalDetail> list = this.fiInternalDetailDao.createQuery("from FiInternalDetail where sourceNo=? and sourceData=?", divideId,dataSource).list();
		FiInternalDetail entity=null;
		for (int i = 0; i < list.size(); i++) {
			 entity = list.get(i);
			 entity.setStatus(0l);
			 this.fiInternalDetailDao.save(entity);
		}
	}

}

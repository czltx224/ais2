package com.xbwl.oper.stock.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdcn.bpaf.common.exception.ServiceException;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.entity.OprInformAppointmentDetail;
import com.xbwl.oper.stock.dao.IOprInformAppointmentDetailDao;
import com.xbwl.oper.stock.service.IOprInformAppointmentDetailService;

/**
 * author CaoZhili time Jul 18, 2011 2:25:05 PM
 * 
 * 通知预约明细表服务层实现类
 */
@Service("oprInformAppointmentDetailServiceImpl")
@Transactional(rollbackFor = { Exception.class })
public class OprInformAppointmentDetailServiceImpl extends
		BaseServiceImpl<OprInformAppointmentDetail, Long> implements
		IOprInformAppointmentDetailService {

	@Resource(name = "oprInformAppointmentDetailHibernateDaoImpl")
	private IOprInformAppointmentDetailDao oprInformAppointmentDetailDao;

	@Override
	public IBaseDAO<OprInformAppointmentDetail, Long> getBaseDao() {
		
		return this.oprInformAppointmentDetailDao;
	}

	public String findDetailByDno(Map<String, String> map)
			throws ServiceException {
		String dno= map.get("EQL_dno");
		
		StringBuffer sb = new StringBuffer();
		sb.append("select t.id,t.inform_id informid,t.service_name servicename,to_char(t.inform_time,'yyyy-MM-dd HH24:mi') informtime,t.cus_request cusrequest,t.cus_options cusoptions,")
		  .append("t.inform_type informtype,t.inform_result informresult,t.cus_name cusname,t.cus_addr cusaddr,t.cus_tel custel,t.cus_mobile cusmobile,")
		  .append("t.inpaymentcollection,t.cp_fee cpfee,t.delivery_fee deliveryfee,t.remark,t.create_name createname,t.create_time createtime,")
		  .append("t.update_name updatename,t.update_time updatetime,t.ts,d.d_no")
		  .append(" from opr_inform_appointment_detail t,opr_inform_appointment  d where t.inform_id=d.id");
		
		if(null!=dno && !"".equals(dno)){
			sb.append(" and d.d_no=:EQL_dno");
		}
		return sb.toString();
	}
}

package com.xbwl.oper.takegoods.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.ralasafe.WebRalasafe;
import org.ralasafe.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.orm.Page;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.entity.OprShuntApply;
import com.xbwl.oper.takegoods.dao.IOprShuntApplyDao;
import com.xbwl.oper.takegoods.service.IOprShuntApplyService;

/**
 *@author LiuHao
 *@time Dec 5, 2011 11:32:32 AM
 */
@Service("oprShuntApplyServiceImpl")
@Transactional(rollbackFor=Exception.class)
public class OprShuntApplyServiceImpl extends BaseServiceImpl<OprShuntApply,Long> implements
		IOprShuntApplyService {
	@Resource(name="oprShuntApplyHibernateDaoImpl")
	private IOprShuntApplyDao oprShuntApplyDao;
	@Override
	public IBaseDAO getBaseDao() {
		return oprShuntApplyDao;
	}
	public void shuntApply(List<OprShuntApply> list) throws Exception {
		User user=WebRalasafe.getCurrentUser(Struts2Utils.getRequest());
		//如果对多个航班进行调车申请，则需要连接航班号、提货点、航班落地时间以及计算总重量、总件数
		if(list.size()>1){
			OprShuntApply osa=new OprShuntApply();
			String flightNo="";
			String takeAddr="";
			String flightEndTime="";
			Double weight=0.0;
			Long piece=0L;
			for (int i = 0; i < list.size(); i++) {
				OprShuntApply os=list.get(i);
				flightNo+=os.getFlightNo();//连接航班号
				takeAddr+=os.getTakeAddr();//连接提货点
				flightEndTime+=os.getFlightEndTime();//连接航班落地时间
				weight+=os.getShuntWeight();//计算总重量
				piece+=os.getShuntPiece();//计算总件数
				if(i!=list.size()-1){
					flightNo+=",";
					takeAddr+=",";
					flightEndTime+=",";
				}
			}
			osa.setCreateName(user.get("name").toString());
			osa.setCreateTime(new Date());
			//osa.setTs("23434");
			osa.setFlightNo(flightNo);
			osa.setDepartId(Long.valueOf(user.get("bussDepart")+""));
			//osa.setFlightEndTime(flightEndTime);
			osa.setShuntPiece(piece);
			osa.setShuntWeight(weight);
			osa.setTakeAddr(takeAddr);
			oprShuntApplyDao.save(osa);
		}else{
			OprShuntApply os=list.get(0);
			//REVIEW 具体意义说明
			//os.setTs("23423423423");
			os.setDepartId(Long.valueOf(user.get("bussDepart")+""));
			oprShuntApplyDao.save(os);
		}
	}
	public Page findShuntApply(Page page, String flightNo, String takeAddr)
			throws Exception {
		Map map=new HashMap();
		StringBuffer sql=new StringBuffer("select * from opr_shunt_apply osa,(");
		sql.append("select osad.shunt_apply_id,wmsys.wm_concat(osad.dis_car_no) dis_car_no,sum(osad.dis_car_ton) dis_car_ton,sum(osad.dis_shunt_piece) dis_shunt_piece,");
		sql.append("wmsys.wm_concat(osad.plan_car_time) plan_car_time,sum(osad.dis_shunt_weight) dis_shunt_weight");
		sql.append(" from opr_shunt_apply_detail osad where osad.status=1 group by osad.shunt_apply_id) osad");
		sql.append(" where osa.id=osad.shunt_apply_id(+)");
		if(flightNo!=null){
			sql.append(" and osa.flight_no like :flightNo");
			map.put("flightNo", "'%'"+flightNo+"'%'");
		}
		if(takeAddr!=null){
			sql.append(" and osa.take_addr like :takeAddr");
			map.put("takeAddr", "'%'"+takeAddr+"'%'");
		}
		return oprShuntApplyDao.findPageBySqlMap(page, sql.toString(), map);
	}
}

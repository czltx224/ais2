package com.xbwl.oper.takegoods.service.impl;

import java.text.SimpleDateFormat;
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
import com.xbwl.entity.BasCar;
import com.xbwl.entity.OprShuntApplyDetail;
import com.xbwl.oper.stock.service.IOprOvermemoService;
import com.xbwl.oper.takegoods.dao.IOprShuntApplyDetailDao;
import com.xbwl.oper.takegoods.service.IOprShuntApplyDetailService;
import com.xbwl.sys.service.IBasCarService;

/**
 *@author LiuHao
 *@time Dec 5, 2011 11:32:32 AM
 */
@Service("oprShuntApplyDetailServiceImpl")
@Transactional(rollbackFor=Exception.class)
public class OprShuntApplyDetailServiceImpl extends BaseServiceImpl<OprShuntApplyDetail,Long> implements
		IOprShuntApplyDetailService {
	@Resource(name="oprShuntApplyDetailHibernateDaoImpl")
	private IOprShuntApplyDetailDao oprShuntApplyDetailDao;
	@Resource(name="oprOvermemoServiceImpl")
	private IOprOvermemoService oprOvermemoService;
	@Resource(name="basCarServiceImpl")
	private IBasCarService basCarService;
	@Override
	public IBaseDAO getBaseDao() {
		return oprShuntApplyDetailDao;
	}
	public void shuntApplyAduit(OprShuntApplyDetail osad) throws Exception {
		List<BasCar> list=basCarService.findBy("carCode", osad.getDisCarNo());
		BasCar bc=list.get(0);
		bc.setCarStatus("µ÷³µÖÐ");
		basCarService.save(bc);
		
		User user=WebRalasafe.getCurrentUser(Struts2Utils.getRequest());
		osad.setRouteNumber(oprOvermemoService.findRouteNumberSeq());
		osad.setStatus(1L);
		osad.setCreateName(user.get("name").toString());
		osad.setCreateTime(new Date());
		oprShuntApplyDetailDao.save(osad);
	}
	public void repeatAduit(Long osadId) throws Exception {
		User user=WebRalasafe.getCurrentUser(Struts2Utils.getRequest());
		OprShuntApplyDetail osad=oprShuntApplyDetailDao.get(osadId);
		osad.setStatus(0L);
		osad.setUpdateName(user.get("name").toString());
		osad.setUpdateTime(new Date());
		oprShuntApplyDetailDao.save(osad);
	}
	public Long findMaxRouteNumber(String carNo) throws Exception {
		List<Object> list=this.find("select max(osad.routeNumber) from OprShuntApplyDetail osad where osad.disCarNo=? and osad.status=1", carNo);
		if(list.size()>0){
			return (Long) list.get(0);
		}else{
			return null;
		}
	}
	public Page findCarGuard(Page page,Date date) throws Exception {
		StringBuffer sql=new StringBuffer("select osad.dis_car_no,osad.dis_car_ton,osad.dis_shunt_piece,osad.dis_shunt_weight,to_char(osad.create_time,'yyyy-mm-dd') create_time,osad.plan_car_time,bc.car_status from opr_shunt_apply_detail osad,bas_car bc where osad.dis_car_no=bc.car_code and osad.status=1 ");
		Map map=new HashMap();
		if(date!=null){
			sql.append(" and to_date(to_char(osad.create_time,'yyyy-mm-dd'),'yyyy-mm-dd')=to_date(:createTime,'yyyy-mm-dd')");
			map.put("createTime", new SimpleDateFormat("yyyy-MM-dd").format(date));
		}
		return oprShuntApplyDetailDao.findPageBySqlMap(page, sql.toString(), map);
	}
}

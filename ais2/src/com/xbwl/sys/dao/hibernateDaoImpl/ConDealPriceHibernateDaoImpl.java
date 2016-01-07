package com.xbwl.sys.dao.hibernateDaoImpl;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.ralasafe.WebRalasafe;
import org.ralasafe.user.User;
import org.springframework.stereotype.Repository;

import com.xbwl.common.orm.Page;
import com.xbwl.common.orm.hibernate.BaseDAOHibernateImpl;
import com.xbwl.entity.ConsigneeDealPrice;
import com.xbwl.sys.dao.IConDealPriceDao;

/**
 *@author LiuHao
 *@time Jun 27, 2011 6:45:23 PM
 */
@Repository("conDealPriceHibernateDaoImpl")
public class ConDealPriceHibernateDaoImpl extends BaseDAOHibernateImpl<ConsigneeDealPrice,Long>
		implements IConDealPriceDao {

	public Page<ConsigneeDealPrice> findConDealPrice(Page page, String cusName,String[] tels)
			throws Exception {
		User user = WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
		Map map = new HashMap();
		map.put("departId", Long.valueOf(user.get("bussDepart").toString()));
		StringBuffer tel = new StringBuffer("");
		for(int i = 0; i < tels.length; i++){
			tel.append(tels[i]);
			if(i != tels.length-1){
				tel.append(",");
			}
		}
		map.put("dispatchAgency",cusName);
		map.put("tel",tel.toString());
		
		StringBuffer hql=new StringBuffer("from ConsigneeDealPrice cdp where cdp.startTime<sysdate and cdp.stopTime>sysdate and cdp.isstop=0");
		hql.append(" and cdp.dispatchAgency=:dispatchAgency and cdp.contactWay=:tel and cdp.departId=:departId ");
		Page<ConsigneeDealPrice> page1 = this.findPage(page, hql.toString(), map);
		if(page1.getResult().size()<1){
			hql=new StringBuffer("from ConsigneeDealPrice cdp where cdp.startTime<sysdate and cdp.stopTime>sysdate and cdp.isstop=0");
			hql.append(" and cdp.dispatchAgency=:dispatchAgency and cdp.contactWay is null and cdp.departId=:departId ");
			page1 = this.findPage(page, hql.toString(), map);
			if(page1.getResult().size()<1){
				hql=new StringBuffer("from ConsigneeDealPrice cdp where cdp.startTime<sysdate and cdp.stopTime>sysdate and cdp.isstop=0");
				hql.append(" and cdp.dispatchAgency is null and cdp.contactWay=:tel and cdp.departId=:departId ");
				page1 = this.findPage(page, hql.toString(), map);
			}
		}
//		StringBuffer hql = new StringBuffer("from ConsigneeDealPrice cdp where cdp.isstop=0 and cdp.startTime<sysdate and cdp.stopTime>sysdate and cdp.contactWay=:tel");
		
		return page1;
	}
}

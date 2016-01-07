package com.xbwl.sys.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.jasperreports.engine.type.StretchTypeEnum;
import net.sf.json.JSONSerializer;

import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.junit.Test;
import org.ralasafe.WebRalasafe;
import org.ralasafe.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sybase.jdbc3.a.b.p;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.orm.Page;
import com.xbwl.common.orm.PropertyFilter;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.entity.BasArea;
import com.xbwl.rbac.vo.SysDepartVo;
import com.xbwl.sys.dao.IBasAreaDao;
import com.xbwl.sys.service.IBasAreaService;

/**
 *author LiuHao
 *time Jun 22, 2011 12:00:23 PM
 */
@Service("basAreaServiceImpl")
@Transactional(rollbackFor={Exception.class})
public class BasAreaServiceImpl extends BaseServiceImpl<BasArea,Long> implements
		IBasAreaService {
	@Resource(name="basAreaDaoImpl")
	private IBasAreaDao basAreaDao;
	@Override
	public IBaseDAO getBaseDao() {
		return basAreaDao;
	}
	@Transactional(rollbackFor={Exception.class},readOnly=true)
	public List<BasArea> getBasAreaTreeByPrentId(Long parentId) throws Exception {
		List list=this.find("from BasArea ba where ba.parentId=?",parentId);
		return list;
	}
	/**
	 * �����ϼ�������ѯ������Ϣ
	 */
	@Transactional(rollbackFor={Exception.class},readOnly=true)
	public Page findArea(Page page,Long parentId) throws Exception {
		List list = new ArrayList();
		Page tpage=basAreaDao.findPage(page, "from BasArea ba where ba.parentId=?",parentId);
		List resultList=tpage.getResult();
		for (Object object : resultList) {
			list.add(object);
		}
		page.setResult(list);
		return page;
	}
	/**
	 * ���ݵ���ID��ѯ������Ϣ
	 */
	@Transactional(rollbackFor={Exception.class},readOnly=true)
	public Page findAreaById(Page page, Long id) throws Exception {
		return basAreaDao.findPage(page, "from BasArea ba where ba.id=?",id);
	}
	public Page getAreaMsg(Page page,String city, String town, String street)
			throws Exception {
		Page returnPage=null;
		User user=WebRalasafe.getCurrentUser(Struts2Utils.getRequest());
		//��ѯ�������
		returnPage = this.findPage(page,this.getAreaMsgHql("BasSpecialArea","departId="+user.get("bussDepart").toString()),city,'%'+"��"+'%');
		if(returnPage.getResult().size()<1){
			returnPage = this.findPage(page,this.getAreaMsgHql("BasSpecialArea","departId="+user.get("bussDepart").toString()),town,'%'+"��"+'%');
			if(!"".equals(street) && returnPage.getResult().size()<1){
				returnPage = this.findPage(page,this.getAreaMsgHql("BasSpecialArea","departId="+user.get("bussDepart").toString()),street,'%'+"�ֵ�"+'%');
			}
		}
		//����������û�� ���ѯ��������
		if(returnPage.getResult().size()<1){
			if(!"".equals(street)){
				returnPage = this.findPage(page,this.getAreaMsgHql("BasArea",null),street,'%'+"�ֵ�"+'%');
			}
			if(returnPage.getResult().size()<1){
				returnPage = this.findPage(page,this.getAreaMsgHql("BasArea",null),town,'%'+"��"+'%');
				if(returnPage.getResult().size()<1){
					returnPage = this.findPage(page,this.getAreaMsgHql("BasArea",null),city,'%'+"��"+'%');
				}
			}
		}
		return returnPage;
	}
	
	private String getAreaMsgHql(String entityName,String term){
		StringBuffer hql=new StringBuffer("from ");
		hql.append(entityName);
		hql.append(" where ");
		if(null != term){
			hql.append(term);
			hql.append(" and ");
		}
		hql.append("areaName=? and areaRank like ?");
		return hql.toString();
	}
	
	public boolean isBasAreaExistOfString(String string) throws Exception {
		List list =createSQLQuery("  select ar.area_name areaname  from  bas_area ar where ar.area_name=?  ",string).list();
		if(list.size()==1){
			return true;
		}else{
			return false;
		}
	}
}

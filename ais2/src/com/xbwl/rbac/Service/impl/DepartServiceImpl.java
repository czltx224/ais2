package com.xbwl.rbac.Service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONSerializer;

import org.dozer.DozerBeanMapper;
import org.hibernate.proxy.map.MapLazyInitializer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdcn.bpaf.common.exception.ServiceException;
import com.xbwl.common.log.anno.ModuleName;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.orm.Page;
import com.xbwl.common.orm.PropertyFilter;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.common.utils.LogType;
import com.xbwl.common.utils.OprHistory;
import com.xbwl.entity.SysDepart;
import com.xbwl.rbac.Service.IDepartService;
import com.xbwl.rbac.dao.IDepartDao;
import com.xbwl.rbac.vo.DepartTree;
import com.xbwl.rbac.vo.SysDepartVo;

/**
 *author LiuHao
 *time Jun 9, 2011 2:13:43 PM
 */
@Service("departServiceImpl")
@Transactional(rollbackFor={Exception.class})
public class DepartServiceImpl extends BaseServiceImpl<SysDepart,Long> implements
		IDepartService {
	@Resource(name="departHibernateDaoImpl")
    private IDepartDao departDao;
	
	@Resource
	private  DozerBeanMapper dozer;
    
	public IBaseDAO<SysDepart, Long> getBaseDao() {
		return departDao;
	}
	@ModuleName(value="获取部门树",logType=LogType.bas)
	@Transactional(rollbackFor={Exception.class},readOnly=true)
	public String getDepartTreeById(Long id) {
		
		 SysDepart depart=departDao.getAndInitEntity(id);
		
		 DepartTree menuTree= dozer.map(depart,DepartTree.class);

		 return JSONSerializer.toJSON(menuTree.getChildren()).toString();
		
	}
	
	/**
	 * 根据上级部门ID查询部门信息
	 */
	@ModuleName(value="获取部门分页",logType=LogType.bas)
	public Page<SysDepart> getDepartByParentId(Page page,Long parentId) throws Exception {
		List list = new ArrayList();
		departDao.findPage(page,"from SysDepart sd where sd.parent.departId=? ", parentId);
		List resultList=page.getResult();
		for (Object object : resultList) {
				list.add(dozer.map(object, SysDepartVo.class));
			}
		page.setResult(list);
		return page;
		
	}
	/**
	 * 查询所有部门信息并封装到VO
	 */
	public Page<SysDepart> findAllDepart(Page page,List<PropertyFilter> filter) throws Exception {
		List list = new ArrayList();
		departDao.findPage(page, filter);
		for(Object obj:page.getResult()){
			list.add(dozer.map(obj, SysDepartVo.class));
		}
		page.setResult(list);
		return page;
	}
	public Page<SysDepart> findDepartBySationId(Page page, Long stationId)
			throws Exception {
		return this.getPageBySql(page, "select * from sys_depart sd where sd.lead_station=?", stationId+"");
	}
	public SysDepart getDepartByDepartNo(String departNo) throws Exception {
		return departDao.findUniqueBy("departNo", departNo);
	}
	
	public String findDepartService(Map<String, String> map)
			throws ServiceException {
		String departName = map.get("LIKES_departName");
		String EQL_isBussinessDepa = map.get("EQL_isBussinessDepa");
		String service = map.get("EQL_isCusDepart");
		StringBuffer sb = new StringBuffer();
		sb.append("select  t.depart_id departId,t.depart_name departName,t.depart_no departno from sys_depart t where 1=1");
		
		if(null!=departName && !"".equals(departName)){
			sb.append(" and t.depart_name like '%'||:LIKES_departName||'%'");
		}
		if(null!=EQL_isBussinessDepa && !"".equals(EQL_isBussinessDepa)){
			sb.append(" and t.IS_BUSSINESS_DEPA =:EQL_isBussinessDepa");
		}
		if(null!=service && !"".equals(service)){
			sb.append(" and t.IS_CUS_DEPART =:EQL_isCusDepart ");
		}
		return sb.toString();
	}
	public List getMaxDepartno(Long parentId) throws Exception {
		String sql="select max(to_number(t.depart_no)) max_departno from sys_depart t where t.parent_id=?";
		return this.createSQLQuery(sql, parentId).list();
		//this.cr
	}
	
}

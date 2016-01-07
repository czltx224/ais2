package com.xbwl.sys.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONSerializer;

import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sybase.jdbc3.a.b.p;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.orm.Page;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.entity.OprExceptionType;
import com.xbwl.rbac.vo.DepartTree;
import com.xbwl.rbac.vo.SysDepartVo;
import com.xbwl.sys.dao.IOprExceptionTypeDao;
import com.xbwl.sys.service.IOprExceptionTypeService;

/**
 * author shuw
 * time Aug 17, 2011 9:25:30 AM
 */
@Service("oprExceptionTypeServiceImpl")
@Transactional(rollbackFor={Exception.class})
public class OprExceptionTypeImpl extends BaseServiceImpl<OprExceptionType, Long> implements IOprExceptionTypeService {

	@Resource(name="oprExceptionTypeHibernateDaoImpl")
	private IOprExceptionTypeDao oprExceptionDao;
	
	@Resource
	private  DozerBeanMapper dozer;
	
	@Override
	public IBaseDAO<OprExceptionType, Long> getBaseDao() {
		return oprExceptionDao;
	}

	public String getExceTypeNodeSql(Map<String, String> map) throws Exception {
		String nodeName = map.get("LIKES_nodeName");
		StringBuffer sb=new StringBuffer();
		sb.append("select  t.node_id id, min(t.NODE_NAME) nodeName from OPR_EXCEPTION_TYPE t ");
		
		if(null!=nodeName && !"".equals(nodeName)){
			sb.append("  where t.node_name like '%'||:LIKES_nodeName||'%'");
		}
		sb.append("  group by t.node_id ");
		return sb.toString();
	}

//	@Transactional(rollbackFor={Exception.class},readOnly=true)
//	public String getExceptionTreeById(Long node) {
//		OprExceptionType oprExceptionType = oprExceptionDao.getAndInitEntity(node);
////	    OprExceptionTypeTree menuTree= dozer.map(oprExceptionType,OprExceptionTypeTree.class);
//
////		 return JSONSerializer.toJSON(menuTree.getChildren()).toString();
		//return null;
//	}
/*
	public Page getExceptionByParentId(Page page, Long parentId) {
		List list = new ArrayList();
		oprExceptionDao.findPage(page,"from OprExceptionType sd where sd.parent.id=? ", parentId);
		List resultList=page.getResult();
		for (Object object : resultList) {
				list.add(dozer.map(object, OprExceptionType.class));
		}
		page.setResult(list);
		return page;
	}

	public Page findAllException(Page page, List filter) {
		List list = new ArrayList();
		oprExceptionDao.findPage(page, filter);
		for(Object obj:page.getResult()){
			list.add(dozer.map(obj, OprExceptionType.class));
		}
		page.setResult(list);
		return page;
	}

	public String getExceptionTreeById(Long node) {
		// TODO Auto-generated method stub
		return null;
	}
 */
	

}

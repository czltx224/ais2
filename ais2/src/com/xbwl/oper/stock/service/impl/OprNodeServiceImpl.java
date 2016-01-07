package com.xbwl.oper.stock.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.orm.Page;
import com.xbwl.common.orm.PropertyFilter;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.entity.OprNode;
import com.xbwl.oper.stock.dao.IOprNodeDao;
import com.xbwl.oper.stock.service.IOprNodeService;

/**
 * author shuw
 * time Aug 9, 2011 8:42:35 AM
 */
@Service("oprNodeServiceImpl")
@Transactional(rollbackFor={Exception.class})
public class OprNodeServiceImpl extends BaseServiceImpl<OprNode, Long> implements IOprNodeService{

	@Resource(name = "oprNodeHibernateDaoImpl")
	private IOprNodeDao oprNodeDao;
	
	@Override
	public IBaseDAO<OprNode, Long> getBaseDao() {
		return oprNodeDao;
	}
	
	//综合查询，获取部分节点Sql
	public String getListOfQuery(Map<String,String> map) throws Exception {
		String nodeName = map.get("LIKES_nodeName");
		StringBuffer sb = new StringBuffer();
		sb.append( " select t.id||'.'||t.node_order id,t.node_name nodename  from opr_node t where t.node_type in (1,2,3,4)  ");
		if(null!=nodeName && !"".equals(nodeName)){
			sb.append(" and t.node_name like '%'||:LIKES_nodeName||'%'");
		}
		return sb.toString();
	}

}

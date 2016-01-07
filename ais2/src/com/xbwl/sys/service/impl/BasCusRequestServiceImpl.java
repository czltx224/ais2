package com.xbwl.sys.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.criterion.Criterion;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.orm.Page;
import com.xbwl.common.orm.PropertyFilter;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.entity.BasCusRequest;
import com.xbwl.sys.dao.IBasCusRequestDao;
import com.xbwl.sys.service.IBasCusRequestService;

/**
 *@author LiuHao
 *@time Jun 27, 2011 6:49:48 PM
 */
@Service("basCusRequestServiceImpl")
@Transactional(rollbackFor={Exception.class})
public class BasCusRequestServiceImpl extends BaseServiceImpl<BasCusRequest,Long> implements
		IBasCusRequestService {
	@Resource(name="basCusRequestHibernateDaoImpl")
	private IBasCusRequestDao basCusRequestDao;
	@Override
	public IBaseDAO getBaseDao() {
		return basCusRequestDao;
	}
	public Page<BasCusRequest> findRequest(Page page, String cpName,
			String cusTel) throws Exception {
		return this.findPage(page, "from BasCusRequest bcr where (bcr.cpName=? and bcr.cusTel=?) or (bcr.cpName=? and bcr.cusTel='*') or (bcr.cpName='*' and bcr.cusTel=?)", cpName,cusTel,cpName,cusTel);
	}

}

package com.xbwl.sys.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.entity.BasLoadingbrigade;
import com.xbwl.sys.dao.IBasLoadingbrigadeDao;
import com.xbwl.sys.service.IBasLoadingbrigadeService;


@Service("basLoadingbrigadeService")
@Transactional(rollbackFor={Exception.class})
public class BasLoadingbrigadeServiceImpl extends BaseServiceImpl<BasLoadingbrigade,Long> 
		implements IBasLoadingbrigadeService {
	
	@Resource
	private IBasLoadingbrigadeDao basLoadingbrigadeDao;

	@Override
	public IBaseDAO<BasLoadingbrigade, Long> getBaseDao() {
		return basLoadingbrigadeDao;
	}

}

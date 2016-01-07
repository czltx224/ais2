package com.xbwl.oper.receipt.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.entity.TbImages;
import com.xbwl.oper.receipt.dao.ITbImagesDao;
import com.xbwl.oper.receipt.service.ITbImagesService;

/**
 * author shuw
 * time May 7, 2012 11:54:56 AM
 */
@Service("tbImagesServiceImpl")
@Transactional(rollbackFor = { Exception.class })
public class TbImagesServiceImpl extends BaseServiceImpl<TbImages, Long> implements ITbImagesService{

	@Resource(name="tbImagesHibernateDaoImpl")
	private ITbImagesDao tbImagesDao;
	
	@Override
	public IBaseDAO<TbImages, Long> getBaseDao() {
		return tbImagesDao;
	}

}

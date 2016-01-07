package com.xbwl.sys.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.entity.BasDictionaryDetail;
import com.xbwl.sys.dao.IBasDictionaryDetailDao;
import com.xbwl.sys.service.IBasDictionaryDetailService;

@Service("basDictionaryDetailServiceImpl")
@Transactional(rollbackFor={Exception.class})
public class BasDictionaryDetailServiceImpl extends BaseServiceImpl<BasDictionaryDetail,Long> implements IBasDictionaryDetailService {

	@Resource
	private IBasDictionaryDetailDao idictionaryDao;

	public IBaseDAO<BasDictionaryDetail, Long> getBaseDao() {
		return idictionaryDao;
	}

	public List<BasDictionaryDetail> getDicDetail(Long dicId) throws Exception {
		return idictionaryDao.findBy("basDictionaryId", dicId);
	}

	

}

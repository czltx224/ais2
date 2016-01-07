package com.xbwl.cus.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.orm.Page;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.cus.dao.ICusGoodsRankDao;
import com.xbwl.cus.service.ICusGoodsRankService;
import com.xbwl.entity.CusGoodsRank;

/**
 *@author LiuHao
 *@time May 18, 2012 10:06:24 AM
 */
@Service("cusGoodsRankServiceImpl")
@Transactional(rollbackFor = { Exception.class })
public class CusGoodsRankServiceImpl extends BaseServiceImpl<CusGoodsRank,Long> implements
		ICusGoodsRankService {
	@Resource(name="cusGoodsRankHibernateDaoImpl")
	private ICusGoodsRankDao cusGoodsRankDao;
	@Override
	public IBaseDAO getBaseDao() {
		return cusGoodsRankDao;
	}
	public Page findAllRank(Page page) throws Exception {
		return this.findPage(page, "from CusGoodsRank",new Object[]{});
	}
}

package com.xbwl.oper.stock.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.entity.OprOvermemo;
import com.xbwl.entity.OprStocktakeDetail;
import com.xbwl.oper.stock.dao.IOprOvermemoDao;
import com.xbwl.oper.stock.dao.IOprStockTakeDetailDao;
import com.xbwl.oper.stock.service.IOprStockTakeDetailService;


/**
 * @author Administrator
 *ø‚¥Ê≈Ãµ„√˜œ∏±Ì
 */
@Service("oprStockTakeDetailServiceImpl")
@Transactional(rollbackFor={Exception.class})
public class OprStockTakeDetailServiceImpl  extends BaseServiceImpl<OprStocktakeDetail, Long>
				implements IOprStockTakeDetailService{

	@Resource(name="oprStockTakeDetailDaoImpl")
	private IOprStockTakeDetailDao oprStockTakeDetailDao;
	
	@Resource
	private  DozerBeanMapper dozer;

	
	@Override
	public IBaseDAO<OprStocktakeDetail, Long> getBaseDao() {
		return oprStockTakeDetailDao;
	}


	public void saveRealPieceById(List<OprStocktakeDetail> oprList) {
		for(OprStocktakeDetail opr:oprList){
			OprStocktakeDetail entityDetail=this.getBaseDao().get(opr.getId());
			entityDetail.setStatus(1l);
			entityDetail.setRealPiece(opr.getRealPiece());
			oprStockTakeDetailDao.save(entityDetail);
		}
	}
	
 

}

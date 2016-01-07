package com.xbwl.oper.stock.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.ralasafe.WebRalasafe;
import org.ralasafe.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdcn.bpaf.common.exception.ServiceException;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.orm.Page;
import com.xbwl.common.orm.PropertyFilter;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.entity.OprStock;
import com.xbwl.entity.OprStocktake;
import com.xbwl.entity.OprStocktakeDetail;
import com.xbwl.oper.stock.dao.IOprStockDao;
import com.xbwl.oper.stock.dao.IOprStockTakeDao;
import com.xbwl.oper.stock.service.IOprStockTakeDetailService;
import com.xbwl.oper.stock.service.IOprStockTakeService;


@Service("oprStockTakeServiceImpl")
@Transactional(rollbackFor={Exception.class})
public class OprStockTakeServiceImpl extends BaseServiceImpl<OprStocktake, Long>
					implements IOprStockTakeService {
	
	@Resource(name="oprStockTakeDaoImpl")
	private IOprStockTakeDao oprStockTakeDao;
	
	@Resource(name="oprStockDaoImpl")
	private IOprStockDao oprStockDao;



	@Override
	public IBaseDAO<OprStocktake, Long> getBaseDao() {
		return this.oprStockTakeDao;
	}

	/**
	 * 保存主盘点表，然后查出相关库存信息保存到盘点明细表
	 *
	 */
	public String saveOprStockTakeQueryOprStock(OprStocktake opr,Long  departId) {
		Page<OprStock> page = new Page<OprStock>();
	//	page.setPageSize(500);
		page.setStart(0);
		page.setLimit(500);
		List<OprStock> list = new ArrayList<OprStock>();

		if(opr.getStorageArea()==null||"".equals(opr.getStorageArea())) {
			oprStockDao.findPage(page, "from OprStock o where o.departId=?  ",departId);
		}else{
			oprStockDao.findPage(page, "from OprStock o where o.departId=?  and  o.storageArea=?  ",departId,opr.getStorageArea());
		} 
		if(page.getTotalCount()>500){
			throw new ServiceException("当前库存超过500条库存记录，不允许盘点！");
		}
		list  = page.getResult();
		if(list.size()==0){
			return "stop";
		}else {
			Set<OprStocktakeDetail> oprStocktakeDetails = new HashSet<OprStocktakeDetail>();
			for(OprStock oprStock : list ){
				OprStocktakeDetail  oprd = new  OprStocktakeDetail();
				oprd.setAddr(oprStock.getAddr());
				oprd.setDNo(oprStock.getDno());
				oprd.setPiece(oprStock.getPiece());
				oprd.setWeight(oprStock.getWeight());
				oprd.setStorageArea(oprStock.getStorageArea());
				oprd.setFlightMainNo(oprStock.getFlightMainNo());
				oprd.setFlightNo(oprStock.getFlightNo());
				oprd.setSubNo(oprStock.getSubNo());
				oprd.setConsignee(oprStock.getConsignee());
				oprd.setStatus(0l);
				oprd.setOprStocktake(opr);
				oprd.setDepartId(departId);
				oprStocktakeDetails.add(oprd);
			}
			opr.setOprStocktakeDetails(oprStocktakeDetails);
			oprStockTakeDao.save(opr);
		    return opr.getId()+"";
		}
	}

	
	public IOprStockTakeDao getOprStockTakeDao() {
		return oprStockTakeDao;
	}

	public void setOprStockTakeDao(IOprStockTakeDao oprStockTakeDao) {
		this.oprStockTakeDao = oprStockTakeDao;
	}

	public IOprStockDao getOprStockDao() {
		return oprStockDao;
	}

	public void setOprStockDao(IOprStockDao oprStockDao) {
		this.oprStockDao = oprStockDao;
	}
}

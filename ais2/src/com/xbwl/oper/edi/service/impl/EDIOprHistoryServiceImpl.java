package com.xbwl.oper.edi.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.entity.EDIOprHistory;
import com.xbwl.entity.OprHistory;
import com.xbwl.oper.edi.dao.IEDIOprHistoryDao;
import com.xbwl.oper.edi.service.IEDIOprHistoryService;
import com.xbwl.oper.stock.service.IOprHistoryService;

@Service("ediOprHistoryServiceImpl")
@Transactional(rollbackFor=Exception.class)
public class EDIOprHistoryServiceImpl extends
		BaseServiceImpl<EDIOprHistory, Long> implements IEDIOprHistoryService {

	@Resource(name="ediOprHistoryHibernateDaoImpl")
	private IEDIOprHistoryDao ediOprHistoryDao;
	
	@Resource(name="oprHistoryServiceImpl")
	private IOprHistoryService oprHistoryService;
	
	@Override
	public IBaseDAO<EDIOprHistory, Long> getBaseDao() {
		return this.ediOprHistoryDao;
	}

	public void scanTimingCtOprHistoryService() throws Exception {
		List<EDIOprHistory> list = this.ediOprHistoryDao.find("from EDIOprHistory order by id");
		if(null!=list && list.size()>0){
			for (int i = 0; i <list.size(); i++) {
				EDIOprHistory history = list.get(i);
				OprHistory entity = new OprHistory();
				BeanUtils.copyProperties(history, entity,new String[]{"id"});
				this.oprHistoryService.saveTransitLog(entity);//ÏÈ±£´æ
				this.ediOprHistoryDao.delete(history);//ÔÙÉ¾³ý
			}
		}
	}
}

package com.xbwl.oper.stock.dao;

import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.entity.OprRequestDo;

/**
 * author CaoZhili
 * time Jul 12, 2011 10:20:36 AM
 */
public interface IOprRequestDoDao extends IBaseDAO<OprRequestDo, Long>{
	
	public OprRequestDo getOprRequestDoByDnoAndStage(String s,Long dno);

}

package com.xbwl.finance.Service;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.FiCheck;

public interface IFiCheckService extends IBaseService<FiCheck,Long> {

	public void deleteByStatus(Long id,String ts)throws Exception;
	
	/**
	 * µΩ’À»∑»œ
	 * @param id 
	 * @param ts
	 * @throws Exception
	 */
	public void checkAudit(Long id,String ts)throws Exception;
}

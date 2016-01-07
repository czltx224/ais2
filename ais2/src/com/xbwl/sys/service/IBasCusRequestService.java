package com.xbwl.sys.service;

import com.xbwl.common.orm.Page;
import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.BasCusRequest;

/**
 *@author LiuHao
 *@time Jun 27, 2011 6:47:48 PM
 */
public interface IBasCusRequestService extends IBaseService<BasCusRequest,Long> {
	public Page<BasCusRequest> findRequest(Page page,String cpName,String cusTel)throws Exception;
}

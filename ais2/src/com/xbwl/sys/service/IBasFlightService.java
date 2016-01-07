package com.xbwl.sys.service;

import java.util.List;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.BasFlight;

public interface IBasFlightService extends IBaseService<BasFlight,Long> {

	/**
	 * ���Һ����
	 * @param string
	 * @return
	 * @throws Exception
	 */
	public List<BasFlight> findFlightNo(String flightNo)throws Exception;
}

package com.xbwl.oper.szsm.scanning;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.xbwl.common.scheduling.XbwlScheduling;
import com.xbwl.oper.szsm.service.IDataExchangeService;

/**
 * 神州数码定时扫描任务
 * @author czl
 * @date 2012-06-20
 */
@Service("aisScanningShenZhouShuMa")
public class AISScanningShenZhouShuMa implements XbwlScheduling {

	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource(name="dataExchangeServiceImpl")
	private IDataExchangeService dataExchangeService;
	
	public void execute() {
		try{
			this.dataExchangeService.doDispatchingExchange();
			this.dataExchangeService.doSingInExchange();
		}catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			e.printStackTrace();
		}
	}
}

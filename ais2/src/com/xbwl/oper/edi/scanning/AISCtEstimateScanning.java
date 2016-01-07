package com.xbwl.oper.edi.scanning;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.xbwl.common.scheduling.XbwlScheduling;
import com.xbwl.oper.edi.service.ICtEstimateService;
/**
 * AISUSER.ct_estimate表定时扫描到EDI
 * @author CZL
 * @TIME 2012-04-13
 */
@Service("aisCtEstimateScanning")
public class AISCtEstimateScanning implements XbwlScheduling{

	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource(name="ctEstimateServiceImpl")
	private ICtEstimateService ctEstimateService;
	
	//定时任务执行方法
	public void execute() {
		try {
			this.ctEstimateService.scanningCtEstimateService();
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			e.printStackTrace();
		}
	}
}

package com.xbwl.oper.edi.scanning;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.xbwl.common.scheduling.XbwlScheduling;
import com.xbwl.entity.EdiScanTiming;
import com.xbwl.oper.edi.service.IEdiScanTimingService;
import com.xbwl.oper.edi.service.IOprFaxOutService;

@Service("aisCtFaxChangeScanning")
public class AISCtFaxOutScanning implements XbwlScheduling{

	public static final Long SCAN_ID=11451L;//定时任务最后扫描时间记录表ID
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource(name = "oprFaxOutServiceImpl")
	private IOprFaxOutService oprFaxOutService;
	
	@Resource(name="ediScanTimingServiceImpl")
	private IEdiScanTimingService ediScanTimingService;
	
	//定时任务执行方法
	public void execute() {
		try {
			this.setLastScanTime();
			this.oprFaxOutService.faxChangeScanning();
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * 不管成功与否，都重置最后扫描时间
	 * @return
	 */
	public void setLastScanTime(){
		Date dt = new Date();
		List<EdiScanTiming> scanList = this.ediScanTimingService.findBy("scanId",this.SCAN_ID);
		if(null!=scanList && scanList.size()>0){
			EdiScanTiming scan = scanList.get(0);
			scan.setScanLastTime(dt);//修改扫描时间
			this.ediScanTimingService.save(scan);
		}
	}

}

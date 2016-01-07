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

	public static final Long SCAN_ID=11451L;//��ʱ�������ɨ��ʱ���¼��ID
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource(name = "oprFaxOutServiceImpl")
	private IOprFaxOutService oprFaxOutService;
	
	@Resource(name="ediScanTimingServiceImpl")
	private IEdiScanTimingService ediScanTimingService;
	
	//��ʱ����ִ�з���
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
	 * ���ܳɹ���񣬶��������ɨ��ʱ��
	 * @return
	 */
	public void setLastScanTime(){
		Date dt = new Date();
		List<EdiScanTiming> scanList = this.ediScanTimingService.findBy("scanId",this.SCAN_ID);
		if(null!=scanList && scanList.size()>0){
			EdiScanTiming scan = scanList.get(0);
			scan.setScanLastTime(dt);//�޸�ɨ��ʱ��
			this.ediScanTimingService.save(scan);
		}
	}

}

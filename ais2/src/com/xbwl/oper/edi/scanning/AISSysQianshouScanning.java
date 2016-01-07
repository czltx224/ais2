package com.xbwl.oper.edi.scanning;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.xbwl.common.scheduling.XbwlScheduling;
import com.xbwl.oper.edi.service.ISysQianshouService;

@Service("aisSysQianshouScanning")
public class AISSysQianshouScanning implements XbwlScheduling {

	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource(name="sysQianshouServiceImpl")
	private ISysQianshouService sysQianshouService;
	
	//��ʱ����ִ�з���
	public void execute() {
		try {
			//�Զ���ǩ�յĻ������ҵ����
			this.sysQianshouService.scanningSysQianshouService();
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			e.printStackTrace();
		}
	}
}

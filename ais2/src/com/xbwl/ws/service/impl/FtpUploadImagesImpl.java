package com.xbwl.ws.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.xbwl.oper.receipt.service.IScanImageInsertTableService;
import com.xbwl.ws.service.IFtpUploadImages;

/**
 * Í¼Æ¬ÉÏ´« 
 * author shuw
 * time May 7, 2012 5:00:29 PM
 */
@Component("ftpUploadImagesImpl")
public class FtpUploadImagesImpl implements IFtpUploadImages{

	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource(name="scanImageInsertTableServiceImpl")
	private IScanImageInsertTableService scanImagesInsertTableService;

	public void updateOprReceipt(String type,Long dno,String imageName,String loginName) {
		try {
			scanImagesInsertTableService.saveAllImagesInfo(type,dno,imageName,loginName);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getLocalizedMessage());
		}
	}
	
}

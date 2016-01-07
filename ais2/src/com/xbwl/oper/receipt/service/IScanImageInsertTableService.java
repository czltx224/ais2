package com.xbwl.oper.receipt.service;
/**
 * author shuw
 * time May 7, 2012 3:53:32 PM
 */
public interface IScanImageInsertTableService {

	/**
	 * 定时任务：把上传的图片地址回写回单表
	 * @throws Exception
	 */
	public void saveAllImagesInfo(String type,Long dno,String imageName,String loginName)throws Exception; 
	
}

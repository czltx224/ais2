package com.xbwl.oper.receipt.service;
/**
 * author shuw
 * time May 7, 2012 3:53:32 PM
 */
public interface IScanImageInsertTableService {

	/**
	 * ��ʱ���񣺰��ϴ���ͼƬ��ַ��д�ص���
	 * @throws Exception
	 */
	public void saveAllImagesInfo(String type,Long dno,String imageName,String loginName)throws Exception; 
	
}

package com.xbwl.cus.service;

import java.io.File;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.CusComplaint;

/**
 *@author LiuHao
 *@time Oct 21, 2011 3:07:11 PM
 */
public interface ICusComplaintService extends IBaseService<CusComplaint,Long> {
	/**
	 * ɾ���ͻ�Ͷ��
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public void deleteComplaint(Long id)throws Exception;
	/**
	 * ���ɿͻ�Ͷ��
	 * @param cusComplaint
	 * @param file
	 * @param fileName
	 * @throws Exception
	 */
	public void acceptComplaint(String acceptName,File file,String fileName,Long comId)throws Exception;
	/**
	 * �ͻ�Ͷ�����
	 * @param cusCom
	 * @param comId
	 * @throws Exception
	 */
	public void dutyComplaint(CusComplaint cusCom,Long comId)throws Exception;
}

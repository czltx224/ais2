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
	 * 删除客户投诉
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public void deleteComplaint(Long id)throws Exception;
	/**
	 * 采纳客户投诉
	 * @param cusComplaint
	 * @param file
	 * @param fileName
	 * @throws Exception
	 */
	public void acceptComplaint(String acceptName,File file,String fileName,Long comId)throws Exception;
	/**
	 * 客户投诉审核
	 * @param cusCom
	 * @param comId
	 * @throws Exception
	 */
	public void dutyComplaint(CusComplaint cusCom,Long comId)throws Exception;
}

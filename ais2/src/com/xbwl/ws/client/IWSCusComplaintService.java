package com.xbwl.ws.client;

import com.xbwl.common.exception.ServiceException;

import dto.CusComplaintDto;

/**
 * @project ais_edi
 * @author czl
 * @Time Mar 7, 2012 5:56:02 PM
 */
public interface IWSCusComplaintService {

	
	/**处理投诉建议后回写网营表
	 * @param map
	 * @throws ServiceException
	 */
	public void updateCusComplaintServiceRemote(CusComplaintDto complaintDto)throws ServiceException;
}

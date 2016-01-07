package com.xbwl.oper.stock.service;

import java.util.List;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.OprMainOrderAdjust;
import com.xbwl.oper.stock.vo.OprMainOrderAdjustVo;

/**
 * @author CaoZhili time Aug 8, 2011 9:56:44 AM
 * 
 * ����������¼������ӿ�
 */
public interface IOprMainOrderAdjustService extends
		IBaseService<OprMainOrderAdjust, Long> {


	/**
	 * �޸������źͻ�������
	 * @param mainIds �޸Ķ���ļ���
	 * @throws ServiceException ������쳣
	 */
	public void updateMainNoAndWeightService(List<OprMainOrderAdjustVo> mainIds) throws Exception;

}

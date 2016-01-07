package com.xbwl.oper.exception.service;

import java.io.File;
import java.util.List;

import com.xbwl.common.orm.Page;
import com.xbwl.common.orm.PropertyFilter;
import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.OprException;

/**
 * author shuw
 * time Aug 22, 2011 4:52:49 PM
 */

public interface IOprExceptionService extends IBaseService<OprException, Long> {

	/**
	 * 异常信息查询（异常反馈）
	 * @param page
	 * @param stationId 岗位ID
	 * @param stationIds  权限岗位集合字符串
	 * @param bussDepartId
	 * @param departId
	 * @param filters  查询条件filters
	 * @return
	 */
	public Page getAllByStationId(Page page,Long stationId,List<Long> stationIds,Long bussDepartId,Long departId,List<PropertyFilter> filters);
	
	/**
	 * 异常信息保存
	 * @param oprException
	 * @param exceptionAdd 导入的图片
	 * @param exceptionAddFileName 图名
	 * @param exptionAdd  修复后的图片
	 * @param exptionAddFileName 图片名
	 * @throws Exception
	 */
	public void saveExceptionOfNew(OprException oprException,
			File exceptionAdd,String exceptionAddFileName, File exptionAdd, String exptionAddFileName) throws Exception;
	
	/**
	 * 异常信息审核保存
	 * @param oprException
	 * @throws Exception
	 */
	public void saveExceptionDo(OprException oprException) throws Exception;
	
	/**
	 *异常删除
	 * @param list  id
	 * @throws Exception
	 */
	public  void  deleteByIdsOfStatus(List<Long >list ) throws Exception;
}

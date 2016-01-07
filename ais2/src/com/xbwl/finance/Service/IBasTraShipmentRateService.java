package com.xbwl.finance.Service;

import java.io.File;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.BasTraShipmentRate;
import com.xbwl.common.orm.Page;

/**
 * @author CaoZhili time Aug 4, 2011 10:22:04 AM
 * 
 * 中转协议价服务层接口
 */
public interface IBasTraShipmentRateService extends
		IBaseService<BasTraShipmentRate, Long> {

	/**
	 * @param ids 需要修改的中转协议价ID数组
	 * @param status  需要修改的状态
	 * @throws Exception 服务层异常
	 */
	public void updateStatusService(String[] ids, Long status) throws Exception;

	/**
	 * 判断是否允许保存专车协议价
	 * @param basTraShipmentRate
	 * @return
	 */
	public boolean allowSaveService(BasTraShipmentRate basTraShipmentRate);
	/**
	 * 查询中转协议价
	 * @author LiuHao
	 * @time Apr 26, 2012 5:06:10 PM 
	 * @param page
	 * @param cusName
	 * @param trafficMode
	 * @param takeMode
	 * @param addrType
	 * @param disdepartId
	 * @param valuationType
	 * @return
	 * @throws Exception
	 */
	public Page findTraRate(Page page,String cusName,String trafficMode,String takeMode,String addrType,Long disdepartId,String valuationType,String speTown)throws Exception;

	/**
	 * 上传Excle ，保存协议价
	 * @param file
	 * @param fileName
	 * @throws Exception
	 */
	public void saveExcelOfExcel(File file,String fileName)	throws Exception;
}

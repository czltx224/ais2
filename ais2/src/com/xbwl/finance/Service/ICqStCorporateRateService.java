package com.xbwl.finance.Service;

import java.io.File;
import java.util.Date;
import java.util.List;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.orm.Page;
import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.BasCqStCorporateRate;

/**
 *@author LiuHao
 *@time 2011-7-21 上午11:36:38
 */
public interface ICqStCorporateRateService extends IBaseService<BasCqStCorporateRate, Long> {

	/**
	 * @param ids 代理标准协议价字符串ID数组
	 * @param status 要修改的 代理标准协议价 状态
	 * @throws ServiceException 服务层异常 
	 */
	public void updateStatusService(String[] ids,Long status) throws Exception;
	/**
	 * 查询公布价
	 * @param page
	 * @param trafficMode
	 * @param takeMode
	 * @param distributionMode
	 * @param addressType
	 * @param startCity
	 * @param city
	 * @param town
	 * @param street
	 * @param departId
	 * @return
	 * @throws Exception
	 */
	public Page<BasCqStCorporateRate> findCqStCorRate(Page page,String trafficMode,String takeMode,String distributionMode,String addressType,String startCity,String city,String town,String street,Long departId)throws Exception;
	
	/**
	 * 判断是否允许保存该协议价格
	 * @param cqStCorporateRate
	 * @return
	 */
	public boolean allowSaveService(BasCqStCorporateRate cqStCorporateRate);
	/**
	 * 打折生成代理协议价
	 * @param cqId
	 * @param cusId
	 * @param cpName
	 * @param rebate
	 * @param startTime
	 * @param endTime
	 * @throws Exception
	 */
	public void discountCqRate(List cqId,Long cusId,String cpName,Double rebate,Date startTime,Date endTime)throws Exception;
	
	
	/**
	 * 导入Excel保存协议价
	 * @param excelFile
	 * @param fileNameString
	 * @throws Exception
	 */
	public void saveExcelOfExcel(File excelFile,String fileNameString) throws Exception;
}

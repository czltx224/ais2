package com.xbwl.finance.Service;

import java.io.File;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.orm.Page;
import com.xbwl.common.service.IBaseService;
import com.xbwl.cus.vo.ReportSerarchVo;
import com.xbwl.entity.BasCqCorporateRate;
import com.xbwl.finance.vo.FaxrateSerarchVo;

/**
 *@author LiuHao
 *@time 2011-7-21 上午11:21:53
 */
public interface ICqCorporateRateService extends IBaseService<BasCqCorporateRate, Long> {
	
	
	/**
	 * 代理协议价保存Excel
	 * @param excle        excel文件
	 * @param excelName         文件名
	 * @throws Exception
	 */
	public void saveExcelOfExcel(File excle ,String excelName) throws Exception;

	/**
	 * @param ids 代理协议价字符串ID数组
	 * @param status 要修改的 代理协议价 状态
	 * @throws ServiceException 服务层异常 
	 */
	public void updateStatusService(String[] ids,Long status) throws Exception;

	/**
	 * 通过
	 * @param basCqCorporateRate 代理协议价实体对象
	 * @return 折扣价
	 * @throws ServiceException 服务层异常
	 */
	public Double getDiscountService(BasCqCorporateRate basCqCorporateRate)throws Exception;
	/**
	 * 查询代理协议价格
	 * @param page
	 * @param trafficMode 运输方式
	 * @param takeMode 提货方式
	 * @param distributionMode 配送方式
	 * @param addressType 地址类型
	 * @param startCity 始发站
	 * @param city 市
	 * @param town 区
	 * @param street 镇/街道
	 * @param departId
	 * @return
	 * @throws Exception
	 */
	public Page<BasCqCorporateRate> findCqCorRate(Page page,String trafficMode,String takeMode,String distributionMode,String addressType,String startCity,String city,String town,String street,String valuationType,Long cusId,Long departId)throws Exception;

	/**
	 * 判断是否可以添加重复的协议价
	 * @param cqCorporateRate
	 * @return
	 */
	public boolean allowSaveService(BasCqCorporateRate cqCorporateRate);
	/**
	 * 价格查询
	 * @author LiuHao
	 * @time Apr 26, 2012 10:47:38 AM 
	 * @param page
	 * @param rateSearchVo
	 * @return
	 * @throws Exception
	 */
	public Page findRate(Page page,FaxrateSerarchVo rateSearchVo)throws Exception;
}

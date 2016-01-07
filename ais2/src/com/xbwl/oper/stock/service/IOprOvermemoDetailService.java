package com.xbwl.oper.stock.service;

import java.util.List;
import java.util.Map;

import org.ralasafe.user.User;

import com.gdcn.bpaf.common.exception.ServiceException;
import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.OprOvermemoDetail;
import com.xbwl.oper.stock.vo.OprFaxInSureVo;
import com.xbwl.oper.stock.vo.OprMathingGoods;

/**
 * @author CaoZhili time Jul 2, 2011 2:39:58 PM
 * 
 * 交接单明细表服务层接口
 */
public interface IOprOvermemoDetailService extends
		IBaseService<OprOvermemoDetail, Long> {

	/**
	 * 通过OprFaxInSureVo对象入库点到
	 * @param faxVo OprFaxInSureVo对象 
	 * @param user 目前登录用户
	 * @param overmemoDetail 交接单明细对象，可以为空
	 * @throws Exception
	 */
	public OprOvermemoDetail saveEnterStockByOprFaxInSureVo(OprFaxInSureVo faxVo,User user,OprOvermemoDetail overmemoDetail)throws Exception;
	
	/**
	 * 点到确认
	 * @param overIds OprFaxInSureVo对象 集合
	 * @param user 目前登录人对象
	 * @return
	 */
	public void doEnterReport(List<OprFaxInSureVo> overIds,User user)throws Exception;

	/**
	 * 汇总信息
	 * @param ids
	 */
	public String  getSumInfoByIds(String ids ,Long bussDepartId);

	/**
	 * 有货无单单据匹配方法
	 * @param list 要匹配的数据对象集合
	 * @throws Exception
	 */
	public void saveMatchingService(List<OprMathingGoods> list) throws Exception;

	
	/**
	 * 点到撤销
	 * @param overIds
	 * @param user
	 * @throws Exception
	 */
	public void revokedOvermemoService(List<OprFaxInSureVo> overIds, User user) throws Exception;

	/**
	 * 入库点到查询手写SQL方法
	 * @param filterParamMap
	 * @return
	 * @throws Exception
	 */
	public String getSqlRalaListService(Map<String, String> filterParamMap)throws Exception;
	
	/**
	 * 根据配送单号查询交接单明细
	 * @param dno
	 * @return
	 * @throws Exception
	 */
	public List<OprOvermemoDetail> findDetailByDno(Long dno)throws Exception;
	
	/**交接单查询语句获取方法
	 * @param map 查询条件集合
	 * @return 查询SQL
	 * @throws Exception 服务层异常
	 */
	public String overmemoSearchService(Map<String, String> map) throws Exception;

	/**交接单明细查询语句获取方法
	 * @param map 查询条件集合
	 * @return 查询SQL
	 * @throws Exception 服务层异常
	 */
	public String overmemoDetailSearchService(Map<String, String> map) throws Exception;

	/**
	 * 货物未到主单跟踪信息查询SQL
	 * @param map
	 * @return
	 * @throws ServiceException
	 */
	public String findNotReportTracking(Map<String, String> map)throws ServiceException;
	
	/**
	 * 汇总货物未到主单信息查询SQL
	 * @param map
	 * @return
	 * @throws ServiceException
	 */
	public String totalNotReportTracking(Map<String, String> map)throws ServiceException;

	/**
	 * 有货无单查询SQL获取方法
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public String findNoFaxListService(Map<String, String> map)throws Exception;
	
	/**
	 * 未到主单明细查询
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public String findNotReportTrackDetail(Map<String,String> map)throws Exception;

	/**
	 * 入库综合处理交接单查询
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public String findOvermemoDetail(Map<String, String> map)throws Exception;
}

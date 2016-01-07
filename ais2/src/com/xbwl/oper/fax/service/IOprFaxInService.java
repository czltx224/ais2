package com.xbwl.oper.fax.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.xbwl.common.orm.Page;
import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.OprFaxIn;
import com.xbwl.entity.OprRequestDo;
import com.xbwl.entity.OprValueAddFee;
import com.xbwl.oper.fax.vo.EdiFaxInVo;
import com.xbwl.oper.fax.vo.FaxReturnMsg;
import com.xbwl.oper.fax.vo.OprFaxInQueryVo;

/**
 * author CaoZhili time Jul 6, 2011 2:35:44 PM
 */
public interface IOprFaxInService extends IBaseService<OprFaxIn, Long> {
	/**
	 * 根据主单号和航班号查询主单信息
	 * @param mainNo
	 * @param fightNo
	 * @return
	 * @throws Exception
	 */
	public Page<OprFaxIn> findMainMsgByMainNo(String mainNo,String fightNo,Page page)throws Exception;
	
	/**
	 * 根据配送单号查询入库点到信息
	 * @param dno
	 * @return
	 * @throws Exception
	 */
	public List<Map> findFaxVoByDno(Long dno)throws Exception;

	
	/**
	 * 综合查询
	 * @param page
	 * @param oprFaxInQueryVo 
	 */
	public Page  queryCondition(Long type,Page page,OprFaxInQueryVo oprFaxInQueryVo,String sort,String dir) throws Exception; 
	
	/**综合查询信息汇总拼接SQL语句
	 * @return
	public String getQuerySumSql(String ids);
	 */
	
	/**
	 * 新增明细
	 * @param ofi
	 * @throws Exception
	 */
	public FaxReturnMsg saveFaxDetail(OprFaxIn ofi,List<OprValueAddFee> list,List<OprRequestDo> requestList)throws Exception;

	/**
	 * 通过主单号或者航班号查询传真表，包括点到状态
	 * @param map 查询条件Map
	 * @return 查询语句
	 * @throws Exception
	 */
	public String getSqlHaveGoodsNoReceipt(Map<String, String> map)throws Exception;
	/**
	 * 提货管理查询
	 * @param page
	 * @param hql
	 * @return
	 * @throws Exception
	 */
	public Page findTakeGoods(Page page,Long cusId,String flightNo,String startTime,String endTime,Long departId,String flightNos,Long isSonderzug)throws Exception;
	/**
	 * 机场调度信息汇总
	 * @param page
	 * @param cusId
	 * @param flightNo
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws Exception
	 */
	public Page fnSumInfo(Page page,Long cusId,String flightNo,String startTime,String endTime,Long departId,Long isSonderzug) throws Exception;
	/**
	 * 收货人信息统计
	 * @param page
	 * @param conName
	 * @param conTel
	 * @param cusId
	 * @return
	 * @throws Exception
	 */
	public Page findCusInfo(Page page,String conName,String conTel,Long cusId,String type,String consignee1,Date startTime,Date endTime)throws Exception;
	/**货量统计
	 * @param customerId 客商ID
	 * @param businessMonth 月份
	 * @return 统计的货量
	 * @throws Exception
	 */
	public Map<String, Double > countCusGoods(Long customerId, String businessMonth)throws Exception;
	/**
	 * 客户关系管理-货量分析查询
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public Page findGoodsAnaly(Page page,String startCount,String endCount,String countRange,Long cusId)throws Exception;
	/**
	 * 传真作废
	 * @param dno
	 * @throws Exception
	 */
	public void faxCancel(List dno)throws Exception;
	/**
	 * 异常专车信息统计
	 * @param startTime
	 * @param endTime
	 * @param isException
	 * @throws Exception
	 */
	public Page sonderzugMsgCount(Page page,Date startTime,Date endTime,Long isException) throws Exception;
	/**
	 * 日销售情况统计
	 * @return
	 * @throws Exception
	 */
	public List daySaleMsg(Date date,String countType,Long departId,String numberType)throws Exception;
	/**
	 * 整体销售分析
	 * @param date
	 * @param countType
	 * @return
	 * @throws Exception
	 */
	public Page findWholeSellMsg(Page page,String countRange,String startCount,String endCount,Long departId,String countType,String groups)throws Exception;
	
	/**综合查询多票货物修改航班号、航班日期、航班时间。
	 * @param dnos 配送单号集合
	 * @param oprFaxIn  
	 * @param isFlyLate 航班是否延误，0为未延误（即正常），1为延误
	 * @throws Exception
	 */
	public void updateFlight(List<Long> dnos,OprFaxIn oprFaxIn,Long isFlyLate) throws Exception;
	
	/**写入EDI数据
	 * @param ofi 传真对象
	 * @param requestList 个性化要求集合
	 * @throws Exception
	 */
	public void insertEdiDataService(EdiFaxInVo ofi,List<OprRequestDo> requestList)throws Exception;
	/**
	 * 根据主单号汇总传真信息
	 * @param fligthMainNo
	 * @return
	 * @throws Exception
	 */
	public Page getSumInfoByMainno(Page page,String fligthMainNo,Date createTime)throws Exception;
	/**
	 * 客服员调整
	 * @param dnos
	 * @throws Exception
	 */
	public void customerServiceAdjust(String dnos)throws Exception;
	/**
	 * 部门业绩统计
	 * @author LiuHao
	 * @time Jun 21, 2012 2:12:29 PM 
	 * @param page
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	public Page getDepartResults(Page page,Date startDate,Date endDate,Long departId,String dateType)throws Exception;
}

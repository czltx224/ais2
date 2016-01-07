package com.xbwl.oper.stock.service;

import java.util.List;

import org.ralasafe.user.User;

import com.xbwl.common.orm.Page;
import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.OprOvermemo;
import com.xbwl.entity.OprOvermemoDetail;
import com.xbwl.finance.dto.impl.FiInterfaceProDto;
import com.xbwl.oper.stock.vo.CarGoVo;

/**
 * @author CaoZhili time Jul 2, 2011 2:39:43 PM
 * 
 * 交接单主表服务层接口
 */

/**
 * @author LiuHao
 *
 */
/**
 * @author LiuHao
 *
 */
public interface IOprOvermemoService extends IBaseService<OprOvermemo, Long> {
	/**
	 * 卸车开始
	 * @param ids
	 * @throws Exception
	 */
	public boolean carUpload(Long routeNumber)throws Exception;
	
	/**
	 * 卸车结束
	 * @param id
	 * @param routeNumber
	 * @throws Exception
	 */
	public boolean carEndUpload(Long id,Long routeNumber)throws Exception;
	
	
	/**
	 * 到车确认撤销
	 * @param ids
	 * @throws Exception
	 */
	public void carUploadReturn(Long id)throws Exception;
	
	/**
	 * 判断是否能做到车确认撤销
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	public boolean isCarUpload(Long routeNumber)throws Exception;
	/**
	 * 判断是否能做到车确认操作
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	public boolean isCarArriveConfirm(Long routeNumber)throws Exception;
	/**
	 * 到车确认
	 * @param ids
	 * @param orderbyName
	 * @throws Exception
	 */
	public boolean carArriveConfirm(Long routeNumber,String orderbyName,User user)throws Exception;
	
	/**
	 * 判断是否可以打印签单
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	public boolean isPrintMsg(Long id)throws Exception;
	/**
	 * 打印车辆清单
	 * @param id
	 * @throws Exception
	 */
	public void printMsg(Long id)throws Exception;
	
	/**
	 * 按属性查询返回集合的大小
	 * @param propertyName
	 * @param endDepartId
	 * @param value
	 * @return
	 */
	public Integer findMemoBy(Long carId,Long overmemoId);
	
	/**
	 * 发车确认
	 * @param goVo 发车确认实体
	 * @param ids
	 * @throws Exception
	 * @return 车次号
	 */
	public Long saveOprSignRouteAndOvem(CarGoVo goVo,List<Long> ids) throws Exception;
	
	/**
	 * @return
	 * 无传真时获得负的配送单号
	 * @throws Exception
	 */
	public Long getNewDno() throws Exception;
	
	/**取消实配
	 * @param list
	 * @param bussDepartId
	 * @param overmemoType
	 * @throws Exception
	 */
	public void deleteOprOvermemo(List<Long>list,Long bussDepartId,String overmemoType) throws Exception;
	/**
	 * 获得车次号
	 * @return
	 * @throws Exception
	 */
	public Long findRouteNumberSeq()throws Exception;
	
	public Page findStartDepart(Page page)throws Exception;
	/**
	 * 手工添加交接单
	 * @param oprOvermemo
	 * @param oprDetails
	 * @param user
	 * @throws Exception
	 */
	public void handAddOpr(OprOvermemo oprOvermemo,List<OprOvermemoDetail> oprDetails,User user,List<FiInterfaceProDto> list,Long loadId)throws Exception;
	
	/**
	 * 取消发车确认
	 * @param Long routeNumer
	 * @return
	 * @throws Exception
	 */
	public String cancelOvermemo(Long routeNumer) throws Exception;

	/**出库写入EDI数据
	 * @param goVo
	 * @param ids
	 * @throws Exception
	 */
	public void insertEdiDataService(CarGoVo goVo,List<Long> ids)throws Exception; 

}


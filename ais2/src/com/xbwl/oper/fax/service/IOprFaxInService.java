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
	 * ���������źͺ���Ų�ѯ������Ϣ
	 * @param mainNo
	 * @param fightNo
	 * @return
	 * @throws Exception
	 */
	public Page<OprFaxIn> findMainMsgByMainNo(String mainNo,String fightNo,Page page)throws Exception;
	
	/**
	 * �������͵��Ų�ѯ���㵽��Ϣ
	 * @param dno
	 * @return
	 * @throws Exception
	 */
	public List<Map> findFaxVoByDno(Long dno)throws Exception;

	
	/**
	 * �ۺϲ�ѯ
	 * @param page
	 * @param oprFaxInQueryVo 
	 */
	public Page  queryCondition(Long type,Page page,OprFaxInQueryVo oprFaxInQueryVo,String sort,String dir) throws Exception; 
	
	/**�ۺϲ�ѯ��Ϣ����ƴ��SQL���
	 * @return
	public String getQuerySumSql(String ids);
	 */
	
	/**
	 * ������ϸ
	 * @param ofi
	 * @throws Exception
	 */
	public FaxReturnMsg saveFaxDetail(OprFaxIn ofi,List<OprValueAddFee> list,List<OprRequestDo> requestList)throws Exception;

	/**
	 * ͨ�������Ż��ߺ���Ų�ѯ����������㵽״̬
	 * @param map ��ѯ����Map
	 * @return ��ѯ���
	 * @throws Exception
	 */
	public String getSqlHaveGoodsNoReceipt(Map<String, String> map)throws Exception;
	/**
	 * ��������ѯ
	 * @param page
	 * @param hql
	 * @return
	 * @throws Exception
	 */
	public Page findTakeGoods(Page page,Long cusId,String flightNo,String startTime,String endTime,Long departId,String flightNos,Long isSonderzug)throws Exception;
	/**
	 * ����������Ϣ����
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
	 * �ջ�����Ϣͳ��
	 * @param page
	 * @param conName
	 * @param conTel
	 * @param cusId
	 * @return
	 * @throws Exception
	 */
	public Page findCusInfo(Page page,String conName,String conTel,Long cusId,String type,String consignee1,Date startTime,Date endTime)throws Exception;
	/**����ͳ��
	 * @param customerId ����ID
	 * @param businessMonth �·�
	 * @return ͳ�ƵĻ���
	 * @throws Exception
	 */
	public Map<String, Double > countCusGoods(Long customerId, String businessMonth)throws Exception;
	/**
	 * �ͻ���ϵ����-����������ѯ
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public Page findGoodsAnaly(Page page,String startCount,String endCount,String countRange,Long cusId)throws Exception;
	/**
	 * ��������
	 * @param dno
	 * @throws Exception
	 */
	public void faxCancel(List dno)throws Exception;
	/**
	 * �쳣ר����Ϣͳ��
	 * @param startTime
	 * @param endTime
	 * @param isException
	 * @throws Exception
	 */
	public Page sonderzugMsgCount(Page page,Date startTime,Date endTime,Long isException) throws Exception;
	/**
	 * ���������ͳ��
	 * @return
	 * @throws Exception
	 */
	public List daySaleMsg(Date date,String countType,Long departId,String numberType)throws Exception;
	/**
	 * �������۷���
	 * @param date
	 * @param countType
	 * @return
	 * @throws Exception
	 */
	public Page findWholeSellMsg(Page page,String countRange,String startCount,String endCount,Long departId,String countType,String groups)throws Exception;
	
	/**�ۺϲ�ѯ��Ʊ�����޸ĺ���š��������ڡ�����ʱ�䡣
	 * @param dnos ���͵��ż���
	 * @param oprFaxIn  
	 * @param isFlyLate �����Ƿ�����0Ϊδ���󣨼���������1Ϊ����
	 * @throws Exception
	 */
	public void updateFlight(List<Long> dnos,OprFaxIn oprFaxIn,Long isFlyLate) throws Exception;
	
	/**д��EDI����
	 * @param ofi �������
	 * @param requestList ���Ի�Ҫ�󼯺�
	 * @throws Exception
	 */
	public void insertEdiDataService(EdiFaxInVo ofi,List<OprRequestDo> requestList)throws Exception;
	/**
	 * ���������Ż��ܴ�����Ϣ
	 * @param fligthMainNo
	 * @return
	 * @throws Exception
	 */
	public Page getSumInfoByMainno(Page page,String fligthMainNo,Date createTime)throws Exception;
	/**
	 * �ͷ�Ա����
	 * @param dnos
	 * @throws Exception
	 */
	public void customerServiceAdjust(String dnos)throws Exception;
	/**
	 * ����ҵ��ͳ��
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

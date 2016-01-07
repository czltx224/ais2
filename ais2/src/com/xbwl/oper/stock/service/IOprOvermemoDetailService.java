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
 * ���ӵ���ϸ������ӿ�
 */
public interface IOprOvermemoDetailService extends
		IBaseService<OprOvermemoDetail, Long> {

	/**
	 * ͨ��OprFaxInSureVo�������㵽
	 * @param faxVo OprFaxInSureVo���� 
	 * @param user Ŀǰ��¼�û�
	 * @param overmemoDetail ���ӵ���ϸ���󣬿���Ϊ��
	 * @throws Exception
	 */
	public OprOvermemoDetail saveEnterStockByOprFaxInSureVo(OprFaxInSureVo faxVo,User user,OprOvermemoDetail overmemoDetail)throws Exception;
	
	/**
	 * �㵽ȷ��
	 * @param overIds OprFaxInSureVo���� ����
	 * @param user Ŀǰ��¼�˶���
	 * @return
	 */
	public void doEnterReport(List<OprFaxInSureVo> overIds,User user)throws Exception;

	/**
	 * ������Ϣ
	 * @param ids
	 */
	public String  getSumInfoByIds(String ids ,Long bussDepartId);

	/**
	 * �л��޵�����ƥ�䷽��
	 * @param list Ҫƥ������ݶ��󼯺�
	 * @throws Exception
	 */
	public void saveMatchingService(List<OprMathingGoods> list) throws Exception;

	
	/**
	 * �㵽����
	 * @param overIds
	 * @param user
	 * @throws Exception
	 */
	public void revokedOvermemoService(List<OprFaxInSureVo> overIds, User user) throws Exception;

	/**
	 * ���㵽��ѯ��дSQL����
	 * @param filterParamMap
	 * @return
	 * @throws Exception
	 */
	public String getSqlRalaListService(Map<String, String> filterParamMap)throws Exception;
	
	/**
	 * �������͵��Ų�ѯ���ӵ���ϸ
	 * @param dno
	 * @return
	 * @throws Exception
	 */
	public List<OprOvermemoDetail> findDetailByDno(Long dno)throws Exception;
	
	/**���ӵ���ѯ����ȡ����
	 * @param map ��ѯ��������
	 * @return ��ѯSQL
	 * @throws Exception ������쳣
	 */
	public String overmemoSearchService(Map<String, String> map) throws Exception;

	/**���ӵ���ϸ��ѯ����ȡ����
	 * @param map ��ѯ��������
	 * @return ��ѯSQL
	 * @throws Exception ������쳣
	 */
	public String overmemoDetailSearchService(Map<String, String> map) throws Exception;

	/**
	 * ����δ������������Ϣ��ѯSQL
	 * @param map
	 * @return
	 * @throws ServiceException
	 */
	public String findNotReportTracking(Map<String, String> map)throws ServiceException;
	
	/**
	 * ���ܻ���δ��������Ϣ��ѯSQL
	 * @param map
	 * @return
	 * @throws ServiceException
	 */
	public String totalNotReportTracking(Map<String, String> map)throws ServiceException;

	/**
	 * �л��޵���ѯSQL��ȡ����
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public String findNoFaxListService(Map<String, String> map)throws Exception;
	
	/**
	 * δ��������ϸ��ѯ
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public String findNotReportTrackDetail(Map<String,String> map)throws Exception;

	/**
	 * ����ۺϴ����ӵ���ѯ
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public String findOvermemoDetail(Map<String, String> map)throws Exception;
}

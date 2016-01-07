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
 * ���ӵ���������ӿ�
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
	 * ж����ʼ
	 * @param ids
	 * @throws Exception
	 */
	public boolean carUpload(Long routeNumber)throws Exception;
	
	/**
	 * ж������
	 * @param id
	 * @param routeNumber
	 * @throws Exception
	 */
	public boolean carEndUpload(Long id,Long routeNumber)throws Exception;
	
	
	/**
	 * ����ȷ�ϳ���
	 * @param ids
	 * @throws Exception
	 */
	public void carUploadReturn(Long id)throws Exception;
	
	/**
	 * �ж��Ƿ���������ȷ�ϳ���
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	public boolean isCarUpload(Long routeNumber)throws Exception;
	/**
	 * �ж��Ƿ���������ȷ�ϲ���
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	public boolean isCarArriveConfirm(Long routeNumber)throws Exception;
	/**
	 * ����ȷ��
	 * @param ids
	 * @param orderbyName
	 * @throws Exception
	 */
	public boolean carArriveConfirm(Long routeNumber,String orderbyName,User user)throws Exception;
	
	/**
	 * �ж��Ƿ���Դ�ӡǩ��
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	public boolean isPrintMsg(Long id)throws Exception;
	/**
	 * ��ӡ�����嵥
	 * @param id
	 * @throws Exception
	 */
	public void printMsg(Long id)throws Exception;
	
	/**
	 * �����Բ�ѯ���ؼ��ϵĴ�С
	 * @param propertyName
	 * @param endDepartId
	 * @param value
	 * @return
	 */
	public Integer findMemoBy(Long carId,Long overmemoId);
	
	/**
	 * ����ȷ��
	 * @param goVo ����ȷ��ʵ��
	 * @param ids
	 * @throws Exception
	 * @return ���κ�
	 */
	public Long saveOprSignRouteAndOvem(CarGoVo goVo,List<Long> ids) throws Exception;
	
	/**
	 * @return
	 * �޴���ʱ��ø������͵���
	 * @throws Exception
	 */
	public Long getNewDno() throws Exception;
	
	/**ȡ��ʵ��
	 * @param list
	 * @param bussDepartId
	 * @param overmemoType
	 * @throws Exception
	 */
	public void deleteOprOvermemo(List<Long>list,Long bussDepartId,String overmemoType) throws Exception;
	/**
	 * ��ó��κ�
	 * @return
	 * @throws Exception
	 */
	public Long findRouteNumberSeq()throws Exception;
	
	public Page findStartDepart(Page page)throws Exception;
	/**
	 * �ֹ���ӽ��ӵ�
	 * @param oprOvermemo
	 * @param oprDetails
	 * @param user
	 * @throws Exception
	 */
	public void handAddOpr(OprOvermemo oprOvermemo,List<OprOvermemoDetail> oprDetails,User user,List<FiInterfaceProDto> list,Long loadId)throws Exception;
	
	/**
	 * ȡ������ȷ��
	 * @param Long routeNumer
	 * @return
	 * @throws Exception
	 */
	public String cancelOvermemo(Long routeNumer) throws Exception;

	/**����д��EDI����
	 * @param goVo
	 * @param ids
	 * @throws Exception
	 */
	public void insertEdiDataService(CarGoVo goVo,List<Long> ids)throws Exception; 

}


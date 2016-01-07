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
	 * �쳣��Ϣ��ѯ���쳣������
	 * @param page
	 * @param stationId ��λID
	 * @param stationIds  Ȩ�޸�λ�����ַ���
	 * @param bussDepartId
	 * @param departId
	 * @param filters  ��ѯ����filters
	 * @return
	 */
	public Page getAllByStationId(Page page,Long stationId,List<Long> stationIds,Long bussDepartId,Long departId,List<PropertyFilter> filters);
	
	/**
	 * �쳣��Ϣ����
	 * @param oprException
	 * @param exceptionAdd �����ͼƬ
	 * @param exceptionAddFileName ͼ��
	 * @param exptionAdd  �޸����ͼƬ
	 * @param exptionAddFileName ͼƬ��
	 * @throws Exception
	 */
	public void saveExceptionOfNew(OprException oprException,
			File exceptionAdd,String exceptionAddFileName, File exptionAdd, String exptionAddFileName) throws Exception;
	
	/**
	 * �쳣��Ϣ��˱���
	 * @param oprException
	 * @throws Exception
	 */
	public void saveExceptionDo(OprException oprException) throws Exception;
	
	/**
	 *�쳣ɾ��
	 * @param list  id
	 * @throws Exception
	 */
	public  void  deleteByIdsOfStatus(List<Long >list ) throws Exception;
}

package com.xbwl.finance.Service;

import java.util.Map;

import com.xbwl.common.bean.ValidateInfo;
import com.xbwl.common.orm.Page;
import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.FiCollectiondetail;

public interface IFiCollectiondetailService extends IBaseService<FiCollectiondetail,Long> {
	
	/**
	 * 
	* @Title: saveCollectionstatement 
	* @Description: TODO(���ݲ�ѯ�������ɶ��˵�) 
	* @param @param map ��ѯ����
	* @param @param page ��ҳ
	* @param @param validateInfo    ������Ϣ���� 
	* @throws
	 */
	public void saveCollectionstatement(Map map,Page page,ValidateInfo validateInfo);
	
	/**
	 * 
	* @Title: getCollectiondetailBatch 
	* @Description: TODO(����Map��Ϊ��ѯ��������ȡ�������κţ��ٷ���page����) 
	* @param @param page
	* @param @param map ��ѯ����
	* @param @return    page
	* @return Page    �������� 
	* @throws
	 */
	public Page getCollectiondetailBatch(Page page,Map map);
	
	/**
	 * 
	* @Title: updateFiReceivabledetailStatus 
	* @Description: TODO(���ݶ��˵��Ÿ���Ƿ����ϸ״̬) 
	* @param @param reconciliationNo ���˵���
	* @param @param reconciliationStatus  ���˵�״̬ 
	* @return void    �������� 
	* @throws
	 */
	public void updateStatusByreconciliationNo(Long reconciliationNo,Long reconciliationStatus);

}

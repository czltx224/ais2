package com.xbwl.oper.edi.service;

import java.util.Map;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.SysQianshou;

/**
 * ����ǩ�ռ�¼������ӿ�
 * @author czl
 * @date 2012-05-28
 *
 */
public interface ISysQianshouService extends IBaseService<SysQianshou, String> {

	/**
	 * ɨ�����ǩ�ռ�¼����������ش���
	 * @throws Exception
	 */
	public void scanningSysQianshouService()throws Exception;

	/**
	 * ����ǩ����ʷ��¼��ѯSQL��ȡ
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public String findRecordList(Map<String, String> map)throws Exception;

}

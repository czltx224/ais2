package com.xbwl.sys.service;

import java.util.List;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.BasDictionaryDetail;

/**
 * @author Administrator
 *�����ֵ���ϸ�����ӿ�
 */
public interface IBasDictionaryDetailService extends IBaseService<BasDictionaryDetail,Long>{
	/**
	 * ���������ֵ�����ID�����ϸ
	 * @author LiuHao
	 * @time Mar 2, 2012 10:01:41 AM 
	 * @param dicId
	 * @return
	 * @throws Exception
	 */
	public List<BasDictionaryDetail> getDicDetail(Long dicId)throws Exception;
}

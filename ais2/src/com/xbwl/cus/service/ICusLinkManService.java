package com.xbwl.cus.service;

import java.util.List;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.CusLinkman;

/**
 *@author LiuHao
 *@time Oct 8, 2011 5:58:08 PM
 */
public interface ICusLinkManService extends IBaseService<CusLinkman,Long> {
	/**.
	 * ��ϵ������
	 * @param lId
	 * @throws Exception
	 */
	public void delCusLinkman(List pks)throws Exception;
}

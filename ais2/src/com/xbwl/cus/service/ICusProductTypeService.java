package com.xbwl.cus.service;

import com.xbwl.common.orm.Page;
import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.CusProducttype;

/**
 *@author LiuHao
 *@time Oct 19, 2011 5:15:00 PM
 */
public interface ICusProductTypeService extends IBaseService<CusProducttype,Long> {
	/**
	 * ͳ�Ʋ�Ʒ�ṹ
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public Page findCusProductMsg(Page page,String startCount,String endCount,String countRange,Long cusId)throws Exception;
}

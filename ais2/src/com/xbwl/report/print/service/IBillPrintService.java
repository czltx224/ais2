package com.xbwl.report.print.service;

import java.util.Map;

import org.ralasafe.user.User;

import com.xbwl.report.print.bean.BillLadingList;

/**
 * @author Administrator
 * @createTime 11:29:12 AM
 * @updateName Administrator
 * @updateTime 11:29:12 AM
 * 
 */

public interface IBillPrintService {
	/**获取打印信息
	 * @param modeName
	 * @param ids
	 * @return
	 */
	public BillLadingList getBillLadingList(String modeName,Map<String, String> map,User user);
}

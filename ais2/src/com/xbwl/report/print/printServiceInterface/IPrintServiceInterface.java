package com.xbwl.report.print.printServiceInterface;

import java.util.List;
import java.util.Map;

import com.xbwl.report.print.bean.BillLadingList;
import com.xbwl.report.print.bean.PrintBean;

/**
 * @author Administrator
 * @createTime 11:56:14 AM
 * @updateName Administrator
 * @updateTime 11:56:14 AM
 * 
 */

public interface IPrintServiceInterface {
	/**��װ��ӡʵ��
	 * @return
	 */
	public List<? extends PrintBean> setPrintBeanList(BillLadingList mainbill,Map<String, String> map);
	
	
	/**��ӡ����
	 * @param bean
	 */
	public void afterPrint(PrintBean bean);

}

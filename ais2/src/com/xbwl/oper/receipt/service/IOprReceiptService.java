package com.xbwl.oper.receipt.service;

import java.util.List;
import java.util.Map;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.OprReceipt;
import com.xbwl.oper.receipt.vo.ReceiptConfirmVo;

/**
 * author CaoZhili time Jul 25, 2011 6:03:31 PM
 * 
 * 回单服务层接口
 */
public interface IOprReceiptService extends IBaseService<OprReceipt, Long> {
	
	/**
	 * 获取查询SQL
	 * @param map 集合查询参数
	 * @return
	 * @throws Exception
	 */
	public StringBuffer getSqlRalaListService(Map<String,String> map)throws Exception;
	
	/**
	 * 回单入库点到
	 * @param oprReceipt OprReceipt 对象
	 * @param id 回单ID
	 * @throws Exception 服务异常
	 */
	public void saveReportService(OprReceipt oprReceipt,Long id) throws Exception;
	
	/**
	 * 回单出库
	 * @param oprReceipt OprReceipt 对象
	 * @param ids id合成的字符串,用逗号隔开
	 * @throws Exception 服务异常 
	 */
	public void saveGetService(OprReceipt oprReceipt, String ids) throws Exception;
	
	/**
	 * 回单寄出
	 * @param oprReceipt OprReceipt 对象
	 * @param dnos 寄出单号数组
	 * @throws Exception 服务异常 
	 */
	public void saveOutService(OprReceipt oprReceipt,String[] dnos) throws Exception;

	/**获取图片地址和图片所在的文件夹
	 * @param dno 配送单号
	 * @param url 图片路径配置
	 * @return
	 * @throws Exception
	 */
	
	public List<String> getImageUrlList(Long dno,String url) throws Exception;

	/**
	 * 撤销回单确收方法
	 * @param split
	 * @throws Exception
	 */
	public void cancelConfirmService(String[] split)throws Exception;

	/**
	 * 撤销回单寄出
	 * @param split
	 * @throws Exception
	 */
	public void cancelOutService(String[] split)throws Exception;

	/**
	 * 回单确收
	 * @param confirmVo
	 * @throws Exception
	 */
	public void saveConfirmService(List<ReceiptConfirmVo> confirmVoList)throws Exception;
	
	/**
	 * 删除图片
	 * @param split
	 * @throws Exception
	 */
	public void delImageUrl(Long dno,String numI)throws Exception;

}

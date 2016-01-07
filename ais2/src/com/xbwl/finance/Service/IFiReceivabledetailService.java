package com.xbwl.finance.Service;

import java.util.List;
import java.util.Map;

import org.ralasafe.user.User;

import com.xbwl.common.bean.ValidateInfo;
import com.xbwl.common.orm.Page;
import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.FiPayment;
import com.xbwl.entity.FiReceivabledetail;

public interface IFiReceivabledetailService extends
		IBaseService<FiReceivabledetail, Long> {

	/**
	 * 根据参数生成对账单
	 * @param map
	 * @param page
	 * @param validateInfo
	 * @return Boolean 返回类型 ,true为生成成功，false为生成失败。
	 * @throws Exception
	 */
	public Boolean saveFiReceivablestatement(Map map,Page page,ValidateInfo validateInfo) throws Exception;
	
	/**
	 * 往来明细剔除对账
	 * @param map
	 * @throws Exception
	 */
	public void eliminate(Map map)  throws Exception;
	
	/**
	 * 对账单中添加往来明细
	 * @param map
	 * @throws Exception
	 */
	public void receivabledetailAdd(Map map) throws Exception;
	
	/**
	 * 
	* @Title: saveProble 
	* @Description: TODO(保存问题账款：1、更新供应商往来明细问题账款信息，2、新增至问题账款记录表。) 
	* @param @param fiReceivabledetail   供应商往来明细实体 
	* @return void    返回类型 
	* @throws
	 */
	public void saveProble(FiReceivabledetail fiReceivabledetail) throws Exception;

	/**
	 * 
	* @Title: updateFiReceivabledetailStatus 
	* @Description: TODO(根据对账单号更新欠款明细状态) 
	* @param @param reconciliationNo 对账单号
	* @param @param reconciliationStatus  对账单状态 
	* @return void    返回类型 
	* @throws
	 */
	public void updateStatusByreconciliationNo(Long reconciliationNo,Long reconciliationStatus);
	
	/**
	 * 
	* @Title: isProbleBytailNo 
	* @Description: TODO(根据供应商往来明细表对账状态，判断是否已登记过问题账款) 
	* @param @param id 供应商往来明细表ID
	* @param @return    设定文件 
	* @return boolean    true:未登记,false已登记
	* @throws
	 */
	public boolean isProbleBytailNo(Long id);
	
	/**
	 * 
	* @Title: isStatusAudited 
	* @Description: TODO(根据欠款明细单号判断问题账款是否已收银) 
	* @param @param receivabledetailNo 欠款明细单号
	* @param @return    如果为已收银则返回True,其它：返回则返回False。
	* @return boolean  
	* @throws
	 */
	public boolean isStatusAudited(Long receivabledetailNo);
	
	/**
	 * 根据对账单号更新欠款明细审核状态为已核销
	 * @param reconciliationNo    对账单号
	 */
	public void updateVerificationStatus(Long reconciliationNo);
	
	/**
	 * 根据对账单查询所有代收货款列表
	 * @param reconciliationNo 对账单号
	 * @return
	 * @throws Exception
	 */
	public List<FiReceivabledetail> findCollectionByreconciliationNo(Long reconciliationNo) throws Exception;
	
	/**
	 * 根据配送单号列表返回应付代收货款欠款明细
	 * @param dnos 配送单列表(格式：1,2,3,)
	 * @return
	 * @throws Exception
	 */
	public List<FiReceivabledetail> findCollectionByDnos(String dnos) throws Exception;
	
	/**
	 * 作废对账单
	 * @param reconciliationNo
	 * @throws Exception
	 */
	public void invalid(Long reconciliationNo) throws Exception;
	
	/**
	 * 核销应付代收货款收银状态
	 * @param fiReceivabledetail 应付代收货款实体
	 * @throws Exception
	 */
	public void verificationCollectionStatus(FiReceivabledetail fiReceivabledetail) throws Exception;
	
	/**
	 * 核销应付代收货款收银状态
	 * @param fiPayment 应收应付实体
	 * @throws Exception
	 */
	public void verificationReceistatment(FiPayment fiPayment) throws Exception;
	
	/**
	 * 撤销实收单时，如果为应收代收货款，则撤销应付代收货款收银状态
	 * @param fiPayment
	 * @throws Exception
	 */
	public void revocationFiPaid(FiPayment fiPayment) throws Exception;
	
	/**
	 * 撤销对账单时，如果为应收代收货款，则撤销应付代收货款收银状态
	 * @param fiReceivabledetail
	 * @throws Exception
	 */
	public void revocationCollectionStatus(FiReceivabledetail fiReceivabledetail) throws Exception;

	/**
	 * 返回所有对账明细数据 主要用在导出和打印对账单的时候
	 * @return
	 * @throws Exception
	 */
	public StringBuffer getAllReceivabledetailSql() throws Exception;
	
	
	/**
	 * 手工新增往来明细数据
	 * @param fiReceivabledetail
	 * @throws Exception
	 */
	public void saveReceivabledetail(FiReceivabledetail fiReceivabledetail) throws Exception;
	
	/**
	 * 审核往来明细
	 * @param map 
	 * @param user
	 * @throws Exception
	 */
	public void audit(Map map,User user) throws Exception;
	
	/**
	 * 撤销审核往来明细
	 * @param map:往来明细ID
	 * @param user 
	 * @throws Exception
	 */
	public void revocationAudit(Map map,User user) throws Exception;
	
	/**
	 * 作废手工新增往来明细
	 * @param map:往来明细ID
	 * @param user
	 * @throws Exception
	 */
	public void invalid(Map map,User user)throws Exception;
}


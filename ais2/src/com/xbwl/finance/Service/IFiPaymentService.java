package com.xbwl.finance.Service;

import java.util.Map;

import org.ralasafe.user.User;

import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.orm.Page;
import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.FiCheck;
import com.xbwl.entity.FiPayment;


/**
 * @author 舒稳
*TODO
*功能描述：
*@2011-7-16
 *
 */
public interface IFiPaymentService extends IBaseService<FiPayment, Long> {

	/**
	 * 
	* @Title: searchReceiving 
	* @Description: 根据选择的ids,在应收应付款表中找到id对应的所有配送单号
	* @param @param page
	* @param @param ids,格式:1,2,3,
	* @param @return    设定文件 
	* @return Page<FiPayment>    返回类型 
	* @throws
	 */
	public Map searchReceiving(Map map) throws Exception;
	
	/**
	 * 
	* @Title: searchReceiving 
	* @Description: 根据选择的ids,在应收应付款表中找到id对应的付款单
	* @param @param page
	* @param @param ids,格式:1,2,3,
	* @param @return    设定文件 
	* @return Page<FiPayment>    返回类型 
	* @throws
	 */
	public Page<FiPayment> searchPayment(Page page, Map map) throws Exception;
	
	/**
	 * 
	* @Title: saveReceiving 
	* @Description: TODO(收款操作：应收应付-收款确认) 
	 */
	public void saveReceiving(Map<String,Object> map,User user) throws Exception;
	
	/**
	 * 
	* @Title: saveReceiving 
	* @Description: TODO(付款操作：应收应付-付款确认) 
	* @throws
	 */
	public void savePayment(Map<String, Object> map) throws Exception;
	
	/**
	* @Title: saveEntrust 
	* @Description: TODO(委托收付款-保存) 
	 */
	public void saveEntrust(Map<String,Object> map,FiPayment fiPayment) throws Exception;
	
	/**
	* @Title: saveEntrust 
	* @Description: TODO(挂账-保存) 
	 */
	public void saveLosses(Map<String,Object> map) throws Exception;
	
	/**
	 * 支票到账确认(生成实收实付，收款状态)
	 * @param fiCheck
	 * @throws Exception
	 */
	public void checkAudit(FiCheck fiCheck) throws Exception;
	
	/**
	 * 审核单据
	 * @param id 单据ID
	 * @param user 审核人
	 * @throws Exception
	 */
	public void audit(Long id,User user) throws Exception;
	
	/**
	 * 撤销审核
	 * @param id 单据id
	 * @param user
	 * @throws Exception
	 */
	public void revocationAudit(Long id,User user) throws Exception;
	
	
	
}

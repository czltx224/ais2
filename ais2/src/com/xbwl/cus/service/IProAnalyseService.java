package com.xbwl.cus.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.xbwl.common.orm.Page;
import com.xbwl.common.service.IBaseService;
import com.xbwl.cus.vo.ReportSerarchVo;
import com.xbwl.entity.ProductAnalyse;

/**
 *@author LiuHao
 *@time Dec 3, 2011 3:12:04 PM
 */
public interface IProAnalyseService extends IBaseService<ProductAnalyse,Long>{
	/**
	 * ��Ʒ����ͳ��
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public Page findWholePro(Page page,ReportSerarchVo searchVo)throws Exception;
	/**
	 * ��ѯ��Ʒ��������
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	public Page findIncomePro(Page page,ReportSerarchVo searchVo) throws Exception;
	/**
	 * �����ȼ�����
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public Page findGoodsRank(Page page,ReportSerarchVo searchVo)throws Exception;
	/**
	 * ���������ѯ�Ĳ������� ��������Ϣ
	 * @author LiuHao
	 * @time May 21, 2012 3:54:05 PM 
	 * @param searchVo
	 * @return
	 * @throws Exception
	 */
	public Map getSqlStr(ReportSerarchVo searchVo)throws Exception;
}

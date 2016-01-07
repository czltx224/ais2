package com.xbwl.flow.service;

import org.jdom.Document;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.FlowPipeinfo;

/**
 * ���̹��� �ܵ���Ϣ�����ӿ�
 *@author LiuHao
 *@time Feb 17, 2012 8:53:05 AM
 */
public interface IFlowPipeinfoService extends IBaseService<FlowPipeinfo,Long> {
	/**
	 * ��ѯ����ͼ
	 * @author LiuHao
	 * @time Feb 22, 2012 4:33:34 PM 
	 * @param pipeId
	 * @return
	 * @throws Exception
	 */
	public Document getLayoutXml(Long pipeId) throws Exception;
	/**
	 * ���ò���������Ϊ0�Ľڵ���߳�����Ϣ
	 * @author LiuHao
	 * @time Feb 22, 2012 6:20:02 PM 
	 * @param flowId
	 * @throws Exception
	 */
	public void setDrawpos(Long flowId)throws Exception;
	/**
	 * �����û��϶�������ͼ����
	 * @author LiuHao
	 * @time Feb 23, 2012 5:14:58 PM 
	 * @throws Exception
	 */
	public void saveLayoutXml(String strXml)throws Exception;
	/**
	 * �������ͼxml���ַ�����ʽ
	 * @author LiuHao
	 * @time Feb 25, 2012 3:08:34 PM 
	 * @param pipeId
	 * @param workflowId
	 * @return
	 * @throws Exception
	 */
	public String getShowChartStr(Long pipeId,Long workflowId)throws Exception;
	/**
	 * �������ͼ��XML��ʽ
	 * @author LiuHao
	 * @time Feb 25, 2012 3:09:47 PM 
	 * @return
	 * @throws Exception
	 */
	public Document getShowChartXml(Long pipeId,Long workflowId)throws Exception;

}

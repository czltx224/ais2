package com.xbwl.flow.service;

import java.util.List;

import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.FlowExport;

/**
 * ���̹��� ���ڹ��� ���ݷ��ʲ�ӿ�
 *@author LiuHao
 *@time Feb 20, 2012 11:03:31 AM
 */
public interface IFlowExportService extends IBaseService<FlowExport,Long> {
	/**
	 * ��������/�޸ĳ�����Ϣ
	 * @author LiuHao
	 * @time Feb 20, 2012 3:01:44 PM 
	 * @param exports
	 * @throws Exception
	 */
	public void saveExports(List<FlowExport> exports)throws Exception;
	/**
	 * ��������ID��ѯ���г�����Ϣ
	 * @author LiuHao
	 * @time Feb 22, 2012 4:04:49 PM 
	 * @throws Exception
	 */
	public List<FlowExport> getExportByPipeid(Long pipeId) throws Exception;
}

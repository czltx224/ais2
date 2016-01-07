package com.xbwl.rbac.Service;

import java.util.List;
import java.util.Map;

import com.gdcn.bpaf.common.exception.ServiceException;
import com.xbwl.common.orm.Page;
import com.xbwl.common.orm.PropertyFilter;
import com.xbwl.common.service.IBaseService;
import com.xbwl.entity.SysDepart;

/**
 *author LiuHao
 *time Jun 9, 2011 2:12:39 PM
 */
public interface IDepartService extends IBaseService<SysDepart,Long> {
	
	/**���ݲ���ID���������沿����
	 * @param id
	 * @return
	 */
	public String getDepartTreeById(Long id);
	//�����ϼ����ű�Ų�ѯ������Ϣ
	public Page<SysDepart> getDepartByParentId(Page page,Long parentId) throws Exception;
	//��ѯ���в�����Ϣ
	public Page<SysDepart> findAllDepart(Page page,List<PropertyFilter> filter)throws Exception;
	/**
	 * ���ݸ�λID��ѯ������Ϣ
	 * @author LiuHao
	 * @time Feb 29, 2012 4:13:32 PM 
	 * @param page
	 * @param stationId
	 * @return
	 * @throws Exception
	 */
	public Page<SysDepart> findDepartBySationId(Page page,Long stationId)throws Exception;
	/**
	 * ���ݲ��ű����ѯ������Ϣ
	 * @author LiuHao
	 * @time Feb 29, 2012 4:14:28 PM 
	 * @param departNo
	 * @return
	 * @throws Exception
	 */
	public SysDepart getDepartByDepartNo(String departNo)throws Exception;
	
	/**
	 * ��ѯ�������ƺ�ID
	 * @param map
	 * @return
	 * @throws ServiceException
	 */
	public String findDepartService(Map<String, String> map)throws ServiceException;
	/**
	 * ��ѯ���ű���Ϊ�յĲ�����Ϣ
	 * @author LiuHao
	 * @time Mar 23, 2012 11:59:47 AM 
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	public List getMaxDepartno(Long parentId)throws Exception;
	
}

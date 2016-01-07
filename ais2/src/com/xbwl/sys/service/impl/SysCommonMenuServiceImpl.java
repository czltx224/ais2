package com.xbwl.sys.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.ralasafe.WebRalasafe;
import org.ralasafe.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.orm.Page;
import com.xbwl.common.orm.PropertyFilter;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.entity.SysCommonMenu;
import com.xbwl.sys.dao.ISysCommonMenuDao;
import com.xbwl.sys.service.ISysCommonMenuService;

/**
 * author shuw
 * time Mar 1, 2012 3:25:42 PM
 */
@Service("sysCommonMenuServiceImpl")
@Transactional(rollbackFor={Exception.class})
public class SysCommonMenuServiceImpl extends BaseServiceImpl<SysCommonMenu, Long> implements ISysCommonMenuService {

	@Resource(name="sysCommonMenuHibernateDaoImpl")
	private ISysCommonMenuDao sysCommonMenuDao;

	public IBaseDAO<SysCommonMenu, Long> getBaseDao() {
		return sysCommonMenuDao;
	}

	/* 
	 * ���ó��ò˵� ��ѡ����˵����淽��
	 * @see com.xbwl.sys.service.ISysCommonMenuService#saveList(java.util.List)
	 */
	public void saveList(List<SysCommonMenu> list) throws Exception {
		if(list==null){
			throw new ServiceException("���ݲ���Ϊ��");
		}
		
		for(SysCommonMenu sysMenu : list){
			if(!WebRalasafe.hasPrivilege(Struts2Utils.getRequest(), sysMenu.getNodeId())){ //�ж��û���Ȩ��
				throw new ServiceException(sysMenu.getName()+"�˵����Ѿ�û��Ȩ���ˣ������¼��غ�������");
			}  
			
			List<SysCommonMenu> list2 =sysCommonMenuDao.find("from SysCommonMenu s where s.name=? and s.userId=? ",
							sysMenu.getName(),sysMenu.getUserId());
			if(list2.size()==0){  //��������ˣ�����Ҫ�ٱ���
				save(sysMenu);
			}
		}
		
		User user = WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
		List<SysCommonMenu> listPri =sysCommonMenuDao.find("from SysCommonMenu s where  s.userId=? ",
				Long.valueOf(user.get("id")+""));
		for(SysCommonMenu sm:listPri){
			if(!WebRalasafe.hasPrivilege(Struts2Utils.getRequest(), sm.getNodeId())){ //�ж��û���Ȩ��,û��Ȩ�޾�ɾ��
				sysCommonMenuDao.delete(sm.getId());
			}
		}
	}

	//ҳ���ѯʱ���÷�������Ҫ��ɾ���û��Ѿ�û��Ȩ�޵Ĳ˵�
	public Page queryList(Page page, List<PropertyFilter> filters)
			throws Exception {
		List<SysCommonMenu> listComList=find(filters);
		for(SysCommonMenu sysmenu:listComList){
			if(!WebRalasafe.hasPrivilege(Struts2Utils.getRequest(), sysmenu.getNodeId())){ //�ж��û���Ȩ��,û��Ȩ�޾�ɾ��
				sysCommonMenuDao.delete(sysmenu.getId());
			}
		}
		return findPage(page, filters);
	}
	
	
	

}

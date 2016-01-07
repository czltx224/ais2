package com.xbwl.ct.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.ct.dao.ICtUserDao;
import com.xbwl.ct.service.ICtUserService;
import com.xbwl.entity.Customer;
import com.xbwl.sys.service.ICustomerService;
import com.xbwl.ws.client.IWSEdiToAisRemote;
import com.xbwl.ws.client.result.base.WSResult;

import dto.CtUserDto;

/**
 *EDI用户
 * author shuw
 * time May 2, 2012 9:56:49 AM
 */
@Service("ctUserServiceImpl")
@Transactional(rollbackFor = { Exception.class })
public class CtUserServiceImpl extends BaseServiceImpl<CtUserDto, Long> implements ICtUserService{


	@Resource(name = "ctUserHibernateDaoImpl")
	private ICtUserDao ctUserDao;
	
	@Resource(name="wsEdiToAisRemote")
	private IWSEdiToAisRemote wsEdiToAisRemote;
	
	@Resource(name="customerServiceImpl")
	private ICustomerService customerService;
	
	public IBaseDAO<CtUserDto, Long> getBaseDao() {
		return ctUserDao;
	}

	public void save(CtUserDto entity) {
		
		WSResult rs = this.wsEdiToAisRemote.saveToCtUser(entity);
		if(!WSResult.SUCCESS.equals(rs.getCode())){
			throw new ServiceException(rs.getMessage());
		}else{
			List<Customer> cusList = customerService.findBy("cusName", entity.getUserName());
			if(null!=cusList && cusList.size()>0){
				Customer cus = cusList.get(0);
				cus.setEdiUserId(entity.getUserId());//保存EDI用户，更新EDI对应的编码
				this.customerService.save(cus);
			}
		}
	}

	public List findCtUser(Map<String, String> map) throws Exception {
		
		List list = this.wsEdiToAisRemote.findCtUser(map);
		if(null==list){
			throw new ServiceException("查询EDI用户失败！");
		}else if(list.size()>0){
			Map<String,String> rsmap= (Map)list.get(list.size()-1);
			// if(rsmap.get(///)
		}
		return list;
	}

	public void deleteCtUser(String[] ids) throws Exception {
		WSResult rs = this.wsEdiToAisRemote.deleteCtuser(ids);
		if(!WSResult.SUCCESS.equals(rs.getCode())){
			throw new ServiceException(rs.getMessage());
		}
	}

}

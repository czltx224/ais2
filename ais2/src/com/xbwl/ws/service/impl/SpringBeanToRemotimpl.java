package com.xbwl.ws.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.xbwl.common.scheduling.XbwlScheduling;
import com.xbwl.common.utils.SpringContextHolder;
import com.xbwl.ws.service.ISpringBeanToRemot;

/**
 * @author Administrator
 * @createTime 3:36:38 PM
 * @updateName Administrator
 * @updateTime 3:36:38 PM
 * 
 */
@Component("springBeanToRemotimpl")
public class SpringBeanToRemotimpl implements ISpringBeanToRemot{
	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Resource
	private  SpringContextHolder springContextHolder; 
	
	public void execBean(String beanId) {
		try {
			XbwlScheduling xbwlScheduling=(XbwlScheduling)springContextHolder.getBean(beanId);
			xbwlScheduling.execute();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getLocalizedMessage());
		}
		
	}

}

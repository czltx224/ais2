package com.xbwl.test.common;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.xbwl.common.scheduling.XbwlScheduling;
import com.xbwl.ws.client.SchedulerWebService;

/**
 * @author Administrator
 * @createTime 4:15:50 PM
 * @updateName Administrator
 * @updateTime 4:15:50 PM
 * 
 */
@Component("hello")
public  class Test implements XbwlScheduling {

	@Resource(name="schedulerWebService")
	SchedulerWebService schedulerWebService;
	
	public void execute() {
		
		System.out.println("hello hessian sucess!");
	}

	
	
}

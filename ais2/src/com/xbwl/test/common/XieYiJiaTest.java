package com.xbwl.test.common;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ralasafe.WebRalasafe;
import org.ralasafe.user.User;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xbwl.common.orm.Page;
import com.xbwl.common.orm.PropertyFilter;
import com.xbwl.common.utils.WebTestUtils;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.entity.BasTraShipmentRate;
import com.xbwl.finance.Service.IBasTraShipmentRateService;

/**
 * @author CaoZhili
 * time Aug 11, 2011 5:41:19 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)   
@ContextConfiguration(locations = {   
        "classpath*:applicationContext.xml"
        })   

public class XieYiJiaTest {

	@Resource
	private IBasTraShipmentRateService basTraShipmentRateService;
	
	@Before
	public void before() {
		//设置系统变量值, 为DAILY,NIGHTLY
		
		MockHttpServletRequest request = new MockHttpServletRequest();
		WebTestUtils.setRequestToStruts2(request);
		assertEquals(request, ServletActionContext.getRequest());

		MockHttpServletResponse response = new MockHttpServletResponse();
		WebTestUtils.setResponseToStruts2(response);
		assertEquals(request, ServletActionContext.getRequest());
		
		User user=new User();
		user.set("name", "张三");
		user.set("bussDepart", 1);
		user.set("rightDepart", "广州配送中心");
		WebRalasafe.setCurrentUser(Struts2Utils.getRequest(), user);
	}
	
	@Test
	public void testSave(){
		for(int i=1;i<100000;i++){
			BasTraShipmentRate entity=new BasTraShipmentRate();
			entity.setCusId(1l);
			entity.setCusName("德邦");
			entity.setDiscount(0.58d);
			entity.setAreaType("近郊");
			entity.setEndDate(new Date());
			entity.setLowPrice(235d);
			entity.setStage1Rate(2651d);
			entity.setStage2Rate(2652d);
			entity.setStage3Rate(2653d);
			entity.setStartDate(new Date());
			entity.setStatus(0l);
			entity.setTakeMode("市内自提");
			entity.setTrafficMode("空运");
			
			basTraShipmentRateService.save(entity);
		}
	}
	
	@Test
	public void testSearch(){
		System.out.println(Runtime.getRuntime().maxMemory()/(1000*1000)+"===="+Runtime.getRuntime().freeMemory()/(1000*1000)+"==="+Runtime.getRuntime().totalMemory()/(1000*1000));
				List<PropertyFilter> list=new ArrayList<PropertyFilter>();
				Page page=new Page();
				page.setLimit(1000);
				
				for(int i=1000;i<100000;i++){
					//ibasFlightService.getPageByRela(list, Struts2Utils.getRequest(), 62, null, page);
					basTraShipmentRateService.findPage(page,list);
					System.out.println(Runtime.getRuntime().maxMemory()/(1000*1000)+"===="+Runtime.getRuntime().freeMemory()/(1000*1000)+"==="+Runtime.getRuntime().totalMemory()/(1000*1000));

				}
			
	}
}

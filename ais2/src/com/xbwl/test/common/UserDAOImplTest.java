package com.xbwl.test.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.xbwl.common.orm.Page;
import com.xbwl.common.utils.SpringContextHolder;
import com.xbwl.sys.dao.IBasAreaDao;
import com.xbwl.sys.service.IBasAreaService;

/**
 * @author Administrator
 * @createTime ÉÏÎç11:47:58
 * @updateName Administrator
 * @updateTime ÉÏÎç11:47:58
 * 
 */

public class UserDAOImplTest {
	
	
	@Test
	public void testGetAccountLevelByAccount() {
		
		SpringContextHolder.clear();
		try {
			SpringContextHolder.getBean("foo");
			fail("No exception throw for applicationContxt hadn't been init.");
		} catch (IllegalStateException e) {

		}

		ConfigurableApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath:/applicationContext.xml");
		assertNotNull(SpringContextHolder.getApplicationContext());

		SpringContextHolder holderByName = SpringContextHolder.getBean("springContextHolder");
		assertEquals(SpringContextHolder.class, holderByName.getClass());

		SpringContextHolder holderByClass = SpringContextHolder.getBean(SpringContextHolder.class);
		assertEquals(SpringContextHolder.class, holderByClass.getClass());

		//context.close();
	//	assertNull(ReflectionUtils.getFieldValue(holderByName, "applicationContext"));
		
		IBasAreaService basAreaDao=(IBasAreaService)SpringContextHolder.getBean("basAreaServiceImpl");
		Page page=new Page();
		
		page.setLimit(50);
		
		
		Map map=new HashMap();
		map.put("id", 1l);
		
		
		
		try {
			basAreaDao.getPageBySqlMap(page,"select * from bas_area where id=:id ", map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}

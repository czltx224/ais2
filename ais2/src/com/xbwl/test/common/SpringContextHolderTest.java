package com.xbwl.test.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.ReflectionUtils;

import com.xbwl.common.utils.SpringContextHolder;

public class SpringContextHolderTest {

	@Test
	public void testGetBean() {

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

		context.close();
	//	assertNull(ReflectionUtils.getFieldValue(holderByName, "applicationContext"));

	}
}

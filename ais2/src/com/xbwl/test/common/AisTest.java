package com.xbwl.test.common;


import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ralasafe.WebRalasafe;
import org.ralasafe.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.caucho.hessian.client.HessianProxyFactory;
import com.xbwl.common.utils.ReadExcel2007;
import com.xbwl.common.utils.WebTestUtils;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.cus.dao.ICusOverManagerDao;
import com.xbwl.oper.fax.service.IOprFaxInService;
import com.xbwl.rbac.Service.IUserService;
import com.xbwl.rbac.entity.SysUser;
import com.xbwl.report.print.service.impl.BillPrintServiceImpl;
import com.xbwl.ws.client.SchedulerWebService;

/**
 * @author Administrator
 * @createTime 11:06:22 AM
 * @updateName Administrator
 * @updateTime 11:06:22 AM
 * 
 */

@RunWith(SpringJUnit4ClassRunner.class)   
@ContextConfiguration(locations = {   
        "classpath*:applicationContext.xml"
        })   

public class AisTest {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource
	private IOprFaxInService  oprFaxInService;
	
	@Resource
	private IUserService userService;
	
	@Resource(name="cusOverManagerHibernateDaoImpl")
	private ICusOverManagerDao cusOverManagerDao;
	
	@Value("${exception_file_load}")
	private String exceptionFileLoad;
	
	@Value("${smb.smb_path}")
	private String  path  ;
	
	@Value("${smb.smb_domain}")
	private String  domain;
	
	@Value("${smb.smb_username}")
	private String username;
		
	@Value("${smb.smb_password}")
	private String password;
	
	User user=new User();
	
	@Resource(name="schedulerWebService")
	SchedulerWebService schedulerWebService;

	
	@Resource(name="billPrintServiceImpl")
	private BillPrintServiceImpl billPrintServiceImpl;
	
	@Before
	public void before() {
		//设置系统变量值, 为DAILY,NIGHTLY
		
		MockHttpServletRequest request = new MockHttpServletRequest();
		WebTestUtils.setRequestToStruts2(request);
		assertEquals(request, ServletActionContext.getRequest());

		MockHttpServletResponse response = new MockHttpServletResponse();
		WebTestUtils.setResponseToStruts2(response);
		assertEquals(request, ServletActionContext.getRequest());
		
		user.set("name", "张三");
		user.set("id", 1);
		WebRalasafe.setCurrentUser(Struts2Utils.getRequest(), user);
	}
	
//	@Test
//	public void testSave(){
//		for(int i=1000;i<100000;i++){
//			BasFlight basFlight=new BasFlight();
//			basFlight.setFlightNumber(":"+i);
//			basFlight.setEndCity("广州");
//			basFlight.setSvo(""+i);
//			
//			ibasFlightService.save(basFlight);
//		}
//	}
	/*
	@Test
	public void testSearch() throws Exception{
				List<PropertyFilter> list=new ArrayList<PropertyFilter>();
				Page page=new Page();
				page.setLimit(1000);
				BillLadingList billLadingList=new BillLadingList();
				billLadingList.setBillMd5("dasdasdasdadada");
				billLadingList.setReportPath("http://");
				
				File file=new File("d:/dddd.xml");
				
				oprFaxInService.findPage(page);
				
				for(Object oprFax:page.getResult()){
					OprFaxIn faxIn=(OprFaxIn)oprFax;
					BillLading bean=new BillLading();
					bean.setComplainTel("dasdasda");
					bean.setCusService(faxIn.getCustomerService());
					bean.setCusServiceTel("1892278012");
					
					billLadingList.getPrintBeans().add(bean);
				};
				XBlink.toXml(billLadingList,file);
		//	}
		}
	
	@Test
	public void testPrint(){
		//XBlink.toXml(billPrintServiceImpl.getBillLadingList("1","1",user),System.out);
	}
*/
	
	@Test
	public  void test() throws Exception {
		String fileName="D:\\桌面\\临时工作文件夹\\系统登录人员表（一代）.xlsx";
		ReadExcel2007 readExcel2007=new ReadExcel2007(3);
		List list  = readExcel2007.readExcelByFileName(fileName);
		List<SysUser> listUser = new ArrayList<SysUser>();
		for(int i=0;i<list.size();i++){
			SysUser  user = new SysUser();
			List row=(List)list.get(i);
			if(i!=0){
				Iterator jt=row.iterator();
				String userCode=(String) jt.next();
				user.setUserCode(userCode.substring(0,userCode.lastIndexOf('.')));
				user.setLoginName(userCode.substring(0,userCode.lastIndexOf('.')));
				jt.hasNext();
				
				String deString=(String) jt.next();
				double departId = Double.parseDouble(deString);
				 if(departId==66.0){
					 user.setBussDepart(153l);
				 }else{
					 user.setBussDepart(1l);
				 }
				jt.hasNext();
				
				if(i%2==0){
					user.setSex(0l);
				}else{
					user.setSex(1l);
				}

				String userName=(String)jt.next();
				user.setUserName(userName);
		System.out.println(i+"*");
				jt.hasNext();
			
			}
			user.setBirthday(new Date());
			user.setBirthdayType(1l);
			user.setDepartId(155l);
			user.setStationId(10051l);
			user.setOffTel("13800138000");
			user.setWorkstatus(1l);
			user.setTelPhone("13800138000");
			user.setStationNames("测试");
			user.setHrstatus(1l);
			user.setStatus("1");
			user.setPassword("123456");
			listUser.add(user);
		}
		System.out.println(listUser.size()+"**********");
		for(int j=0;j<listUser.size();j++){
			System.err.println(j+"^^^^^^"+listUser.get(j).getUserName());
			userService.save(listUser.get(j));
		}
	}
	
	public static void main(String[] args) {
		try {
			String url ="http://localhost:81/aisCenter/hessian/schedulerWebServiceImpl";
			HessianProxyFactory factory= new HessianProxyFactory();
			
			factory.setDebug(true);

			SchedulerWebService schedulerWebService = (SchedulerWebService) factory.create(SchedulerWebService.class, url); 

//			schedulerWebService.startTrigger("22222222", null);
			
//			schedulerWebService.stopAll();
			schedulerWebService.stopJob("第一次测试2", null);
			
			
//			JobConfigDTO job=new JobConfigDTO();
//			job.setTriggerName("第一次测试2");
//			job.setJobName("第一次测试2");
//			job.setJobBean("hello");
//		//	job.setJobSql("select * from opr_fax_in;");
//			job.setTriggerScript("0/15 * *  *  * ?");
//			job.setDescription("dasdasdada");
//			job.setHessianUrl("http://192.168.8.51:81/ais2/hessian/springBeanToRemot");
//			
//			HessianResult res=schedulerWebService.modifyScheduler(job);
		//	schedulerWebService.startAll();
		//	System.out.println(res.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
	@Test
	public void test2(){
		try {
			schedulerWebService.startAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	};
	

	
	
}


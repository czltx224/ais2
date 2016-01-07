package com.xbwl.report.print.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.ralasafe.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.orm.Page;
import com.xbwl.common.utils.SpringContextHolder;
import com.xbwl.entity.OprPrint;
import com.xbwl.entity.SysPrintManager;
import com.xbwl.rbac.Service.IUserService;
import com.xbwl.report.print.bean.BillLadingList;
import com.xbwl.report.print.bean.PrintBean;
import com.xbwl.report.print.printServiceInterface.IPrintServiceInterface;
import com.xbwl.report.print.service.IBillPrintService;
import com.xbwl.report.print.service.IOprPrintService;
import com.xbwl.report.print.service.ISysPrintManagerService;
import com.xbwl.report.print.service.ISysPrintRightService;

/**
 * @author Administrator
 * @createTime 11:30:16 AM
 * @updateName Administrator
 * @updateTime 11:30:16 AM
 * 
 */

/**单据打印实现
 * @author Administrator
 *
 */

@Service("billPrintServiceImpl")
@Transactional()
public  class BillPrintServiceImpl implements IBillPrintService {
	
	@Resource(name="sysPrintManagerServiceImpl")
	private ISysPrintManagerService sysPrintManagerService;
	
	@Resource(name="userServiceImpl")
	private IUserService userServiceImpl;
	
	@Resource(name="sysPrintRightServiceImpl")
	private ISysPrintRightService sysPrintRightService;
	
	
	@Resource(name="oprPrintServiceImpl")
	private IOprPrintService oprPrintService;
	
	@Value("${sys.printnum}")
	private Long printNum;
	
	@Value("${print.image_base_url}")
	private String imaBaseUrl;
	
	
	public BillLadingList getBillLadingList(String modeName,Map<String, String> map,User user){
		
		BillLadingList billList=checkIsNew(modeName,user);
		billList.setImgBaseUrl(imaBaseUrl);
		
		if(billList.isNew()){
			
			long num=getCanprintNum(modeName,user);
			List<PrintBean> list=(List<PrintBean>) ((IPrintServiceInterface)SpringContextHolder.getBean(billList.getSpringBean())).setPrintBeanList(billList,map);
			List<String> noPermission=Check(billList,list,num,user);
			
			billList.setNoPermission(noPermission);
		}
		return billList;
	} 
	
	/**检查打印权限
	 * @param billList
	 * @param printList
	 * @param num
	 * @return
	 */
	private List<String> Check(BillLadingList billList,List<PrintBean> printList,long num,User user){
		
		List<String> noPermission=new ArrayList<String>(); 
		
		for(PrintBean print:printList){
			if(print.getPrintNum()>num){
				if(print.getPrintId()!=null){
					noPermission.add(print.getSourceNo());
				}
			
			}else{
				OprPrint oprPrint=new OprPrint();
				oprPrint.setPrintMan(user.get("name").toString());
				oprPrint.setPrintTime(new Date());
				oprPrint.setSourceNo(print.getSourceNo());
				oprPrint.setSourceclass(print.getClass().getSimpleName());
				
				oprPrintService.save(oprPrint);
				
				print.setPrintId(oprPrint.getId().toString());
				
				((IPrintServiceInterface)SpringContextHolder.getBean(billList.getSpringBean())).afterPrint(print);
				
				billList.getPrintBeans().add(print);
			}
		}
		return noPermission;
	}
	
	
	
	/**
	 * 检查是否是最新的模板
	 * @param modeName
	 * @return
	 */
	private BillLadingList checkIsNew(String modeName,User user){
		
		BillLadingList billList=new BillLadingList();
		
		SysPrintManager sysPrint= sysPrintManagerService.getModeByCode(modeName,user);
		
		if(sysPrint!=null){
				billList.setNew(true);
				billList.setSpringBean(sysPrint.getSpringBean());
				billList.setReportName(sysPrint.getReportName());
				billList.setReportPath(sysPrint.getReportPath());
			}else{
				billList.setNew(false);
			}
		
		return billList;
	} 
	
	
	/**获取当前角色最大打印次数
	 * @return
	 */
	private long getCanprintNum(String modeCode,User user){
		
		Long num=printNum;
		
		
		Page page=userServiceImpl.getUserRoleByUserId(user.get("id").toString());
		
		for(Object map:page.getResultMap()){
			Long foo= sysPrintRightService.findUnique("select nvl(max(num),1) from SysPrintRight where roleId=? and modeCode=?", Long.valueOf(((Map)map).get("ROLEID").toString()),modeCode);
			if(foo>num){
				num=foo;
			}
		}
		
		return num;
	}
	
	
	
	
	

}

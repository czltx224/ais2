package com.xbwl.common.log.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;

import com.xbwl.common.bean.ValidateInfo;
import com.xbwl.common.log.service.ISysLogService;
import com.xbwl.common.orm.Page;
import com.xbwl.common.orm.PropertyFilter;
import com.xbwl.common.orm.hibernate.HibernateUtils;
import com.xbwl.common.service.IBaseService;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.entity.SysLog;



@Controller
@Action("sysLog")
@Scope("prototype")
@Namespace("/log")
@Results( {
		@Result(type = "json", name = "success", params = { "root", "page",
				"excludeProperties",
				"" }),
		@Result(name = "input", location = "/WEB-INF/xbwl/rbac/sys_log.jsp", type = "dispatcher"),
		
		@Result(name = "status", type = "json", params = { "root",
				"validateInfo" }) })
public class SysLogAction extends SimpleActionSupport {

	private static final long serialVersionUID = 1L;

	@Resource
	private ISysLogService sysLogService;

    private Long id;

	private String ids;

	private SysLog sysLog;


	public SysLog getSysLog() {
		return sysLog;
	}

	public void setSysLog(SysLog sysLog) {
		this.sysLog = sysLog;
	}

	private String checkItems;

	private String itemsValue;

	private ValidateInfo validateInfo = new ValidateInfo();

	private Page<SysLog> page = new Page<SysLog>();

	public Page<SysLog> getPage() {
		return page;
	}

	public void setPage(Page<SysLog> page) {
		this.page = page;
	}
	// REVIEW-ACCEPT ������˵ϵͳ��־���ṩɾ�����ܣ�ֻ��ͨ��ϵͳ��ά������ɾ������ʵ�ֹ����ҵ��ֿ�
	//FIXED �˹����Ժ����Σ�ǰ��Ϊ�˲������������
	public String delete() throws Exception {

		try {
            List<Long> pks = getPksByIds();
            // REVIEW sysLogService��pksӦ�������ж�
            //δ����Ч�޸���2012-01-03 �����sysLogServiceΪ��ʱδ�ܺ�����
            //FIXED 
			sysLogService.deleteByIds(pks);
			
			validateInfo.setSuccess(true);
			validateInfo.setMsg("ɾ��ϵͳ��־�����ɹ���");
		} catch (Exception e) {
			logger.error(e.getMessage());
			validateInfo.setSuccess(false);
			validateInfo.setMsg(e.getMessage() + "������ϵ����Ա��");
		}
		return "status";
	}

	// REVIEW-ACCEPT ���ӷ���ע��
	
	/* ��ѯ�����
	 * @see com.xbwl.common.web.action.SimpleActionSupport#list()
	 */
	public String list() throws Exception {

		try {
			// REVIEW-ACCEPT ���µı��ر���Ӧ����ʹ��ǰ�ж��Ƿ�Ϊ��
			//FIXED
			Assert.notNull(Struts2Utils.getRequest());
			
			List<PropertyFilter> filters = HibernateUtils.buildPropertyFilters(Struts2Utils.getRequest());
			if (itemsValue != null && !"".equals(itemsValue.trim())
					&& checkItems != null && !"".equals(checkItems.trim())) {
				filters.add(new PropertyFilter(checkItems, itemsValue));
			}
			
			if (!page.isOrderBySetted()) {
				page.setOrderBy("id");
				page.setOrder(Page.DESC);
			}

			page.setLimit(limit);
			page.setStart(start);
			page = sysLogService.findPage(page, filters);
			// REVIEW ע�⣬������Զ���سɹ�!
			return SUCCESS;
		
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			
			return ERROR;
		}
		
	}

	protected void prepareModel() throws Exception {
		if (id != null) {
			sysLog = sysLogService.getAndInitEntity(id);
		} else {
			sysLog = new SysLog();
		}
	}

	public String save() throws Exception {
		return null;
	}

	public SysLog getModel() {
		return sysLog;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getCheckItems() {
		return checkItems;
	}

	public void setCheckItems(String checkItems) {
		this.checkItems = checkItems;
	}

	public String getItemsValue() {
		return itemsValue;
	}

	public void setItemsValue(String itemsValue) {
		this.itemsValue = itemsValue;
	}

	public ValidateInfo getValidateInfo() {
		return validateInfo;
	}

	public void setValidateInfo(ValidateInfo validateInfo) {
		this.validateInfo = validateInfo;
	}

	public List<Long> getPksByIds() {
		List<Long> pks=new ArrayList<Long>();
		// REVIEW-ACCEPT idsӦ��Ҫ�����ж�
		//FIXED
		Assert.notNull(ids, "IDS");
		
		String[] idsValue=ids.split("\\,");
		for (String id : idsValue) {
			pks.add(Long.parseLong(id));
		}
		return pks;
	}

	@Override
	protected Object createNewInstance() {
		return new SysLog();
	}

	@Override
	public Map getContextMap() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return sysLogService;
	}

	@Override
	public void setModel(Object obj) {
		this.sysLog=(SysLog)obj;
	}
}

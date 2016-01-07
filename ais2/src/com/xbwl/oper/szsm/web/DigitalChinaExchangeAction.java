package com.xbwl.oper.szsm.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.xbwl.common.service.IBaseService;
import com.xbwl.common.web.ServletUtils;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.entity.DigitalChinaExchange;
import com.xbwl.oper.szsm.service.IDataExchangeService;

/**
 * ��������Խӿ��Ʋ������
 * @author czl
 * @date 2012-06-27 
 */
@Controller
@Action("digitalChinaExchangeAction")
@Scope("prototype")
@Namespace("/stock")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/stock/szsm/opr_digitalChinaExchange.jsp", type = "dispatcher"),
		@Result(name = "count", location = "/WEB-INF/xbwl/stock/szsm/opr_digitalChinaExchangeCount.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "msg", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"excludeNullProperties", "true" }
		) }
)
public class DigitalChinaExchangeAction extends SimpleActionSupport {

	@Resource(name="dataExchangeServiceImpl")
	private IDataExchangeService dataExchangeService;
	
	private DigitalChinaExchange digitalChinaExchange;
	
	@Override
	protected Object createNewInstance() {
		return new DigitalChinaExchange();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return this.dataExchangeService;
	}

	@Override
	public Object getModel() {
		return this.digitalChinaExchange;
	}

	@Override
	public void setModel(Object obj) {
		this.digitalChinaExchange=(DigitalChinaExchange)obj;
	}

	/**
	 * ��������Խӳɹ������ѯ
	 * @return
	 */
	public String findList(){
		String sql=null;
		//���÷�ҳ����
		try{
			setPageConfig();
			Map<String, String> map = ServletUtils.getParametersStartingWith(Struts2Utils.getRequest(), "filter_");
			sql=this.dataExchangeService.findList(map);
			this.dataExchangeService.getPageBySqlMap(this.getPages(), sql, map);
			
		}catch(Exception e){
			addError("��������Խӳɹ������ѯ��ѯʧ�ܣ�", e);
		}
		return this.LIST;
	}
	
	/**
	 * ��ת����������Խ�ͳ��ҳ��
	 * @return
	 */
	public String count(){
		return "count";
	}
	
	/**
	 * ��������Խ�ͳ�Ʋ�ѯ
	 * @return
	 */
	public String findCount(){
		String sql=null;
		//���÷�ҳ����
		try{
			setPageConfig();
			Map<String, String> map = ServletUtils.getParametersStartingWith(Struts2Utils.getRequest(), "filter_");
			sql=this.dataExchangeService.findCount(map);
			this.dataExchangeService.getPageBySqlMap(this.getPages(), sql, map);
			
		}catch(Exception e){
			addError("��������Խ�ͳ�Ʋ�ѯ��", e);
		}
		return this.LIST;
	}
	
	/**
	 * ��������Խ�ͳ�ƻ���
	 * @return
	 */
	public String findCountSum(){
		String sql=null;
		//���÷�ҳ����
		try{
			setPageConfig();
			Map<String, String> map = ServletUtils.getParametersStartingWith(Struts2Utils.getRequest(), "filter_");
			sql=this.dataExchangeService.findCountSum(map);
			this.dataExchangeService.getPageBySqlMap(this.getPages(), sql, map);
			
			List<Map> list =getPages().getResultMap();
			JSONObject jsonObject = new JSONObject();
			for(Map  totalMap : list ){
				jsonObject.element("CHUANZHEN","����(Ʊ)"+(totalMap.get("CHUANZHEN")==null?"0":totalMap.get("CHUANZHEN")));
				jsonObject.element("CHUKU","����(Ʊ)"+(totalMap.get("CHUKU")==null?"0":totalMap.get("CHUKU")));
				jsonObject.element("PEICHEDUIJIE",("�䳵�Խ�(Ʊ)"+totalMap.get("PEICHEDUIJIE")==null?"0":totalMap.get("PEICHEDUIJIE")));
				jsonObject.element("QIANSHOUDUIJIE","ǩ�նԽ�(Ʊ)"+(totalMap.get("QIANSHOUDUIJIE")==null?"0":totalMap.get("QIANSHOUDUIJIE")));
				jsonObject.element("DUIJIE","�Խ�(Ʊ)"+(totalMap.get("DUIJIE")==null?"0":totalMap.get("DUIJIE")));
			}
			Struts2Utils.renderJson(jsonObject);
		}catch(Exception e){
			addError("��������Խ�ͳ�Ʋ�ѯ��", e);
		}
		return this.LIST;
	}
}

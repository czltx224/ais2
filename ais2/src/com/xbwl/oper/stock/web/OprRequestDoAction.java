package com.xbwl.oper.stock.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

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
import com.xbwl.entity.OprRequestDo;
import com.xbwl.oper.stock.service.IOprRequestDoService;

/**
 * author CaoZhili time Jul 19, 2011 6:16:28 PM
 * 
 * ���Ի�Ҫ����Ʋ������
 */

@Controller
@Action("oprRequestDoAction")
@Scope("prototype")
@Namespace("/stock")
@Results( {
		@Result(name = "input", location = "/WEB-INF/xbwl/stock/opr_requestDo.jsp", type = "dispatcher"),
		@Result(name = "reload", type = "json", params = { "root",
				"validateInfo", "excludeNullProperties", "true" }),
		@Result(name = "list", type = "json", params = { "root", "pages",
				"excludeNullProperties", "true" }

		) }

)
public class OprRequestDoAction extends SimpleActionSupport {

	@Resource(name = "oprRequestDoServiceImpl")
	private IOprRequestDoService oprRequestDoService;

	private OprRequestDo oprRequestDo;

	@Override
	protected Object createNewInstance() {

		return new OprRequestDo();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {

		return this.oprRequestDoService;
	}

	@Override
	public Object getModel() {

		return this.oprRequestDo;
	}

	@Override
	public void setModel(Object obj) {

		this.oprRequestDo = (OprRequestDo) obj;
	}

	@Override
	public String save() throws Exception {
		Boolean flag=true;
		List<OprRequestDo> list = this.oprRequestDoService.findBy("dno", this.oprRequestDo.getDno());
	 
//		for (int i = 0; i <list.size(); i++) {
//			if(null==this.oprRequestDo.getId() || this.oprRequestDo.getId()==0){
//				if(list.get(i).getRequestStage().equals(this.oprRequestDo.getRequestStage())){
//						flag=false;
//				}
//			}else{
//				if(!list.get(i).getId().equals(this.getId()) && list.get(i).getRequestStage().equals(this.oprRequestDo.getRequestStage())){
//					flag=false;
//				}
//			}
//		}
//		if(flag){
//			return super.save();
//		}else{
//			super.getValidateInfo().setSuccess(false);
//			super.getValidateInfo().setMsg("�ý׶θ��Ի�Ҫ���Ѿ����ڣ������ظ���ӣ�");
//			return this.RELOAD;
//		}
		 //ֻ������ӳ��⣬�ͻ��׶εĸ��Ի�Ҫ��
		if(null==this.oprRequestDo.getId()){
			if(this.oprRequestDo.getRequestStage().equals("����") || this.oprRequestDo.getRequestStage().equals("�ͻ�")){
				try{
					this.oprRequestDoService.save(this.oprRequestDo);
					getValidateInfo().setMsg("���ݱ���ɹ���");
					addMessage("���ݱ���ɹ���");
				}catch (Exception e) {
					addError("���ݱ���ʧ�ܣ�", e);
				}
			}else{
				super.getValidateInfo().setSuccess(false);
				super.getValidateInfo().setMsg("���ڲ�������ӳ�����ͻ���ĸ��Ի�Ҫ��");
			}
		}else{
			if(this.oprRequestDo.getRequestStage().equals("����") || this.oprRequestDo.getRequestStage().equals("�ͻ�")){
				try{
					this.oprRequestDoService.save(this.oprRequestDo);
					getValidateInfo().setMsg("���ݱ���ɹ���");
					addMessage("���ݱ���ɹ���");
				}catch (Exception e) {
					addError("���ݱ���ʧ�ܣ�", e);
				}
				}else{
					super.getValidateInfo().setSuccess(false);
					super.getValidateInfo().setMsg("���ڲ������޸ĳ�����ͻ���ĸ��Ի�Ҫ��");
				}
		}
		return this.RELOAD;
	}
	
	public String findRequestDoList(){
		String sql=null;
		//���÷�ҳ����
        setPageConfig();
		Map<String, String> filterParamMap = ServletUtils.getParametersStartingWith(Struts2Utils.getRequest(), "filter_");
		if(null!=getCheckItems() && null!= getItemsValue()){
			filterParamMap.put("checkItems", getCheckItems().substring(getCheckItems().indexOf("_")+1));
			filterParamMap.put("itemsValue", "%"+getItemsValue()+"%");
		}
		try{
			sql=this.oprRequestDoService.getSqlRalaListService(filterParamMap);
			this.oprRequestDoService.getPageBySqlMap(this.getPages(), sql, filterParamMap);
			
		}catch(Exception e){
			addError("��ѯʧ�ܣ�", e);
			
		}
		
		return this.LIST;
	}
	
}

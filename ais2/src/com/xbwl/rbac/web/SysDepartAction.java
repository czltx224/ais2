package com.xbwl.rbac.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.orm.Page;
import com.xbwl.common.service.IBaseService;
import com.xbwl.common.web.ServletUtils;
import com.xbwl.common.web.action.SimpleActionSupport;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.entity.SysDepart;
import com.xbwl.entity.SysStation;
import com.xbwl.rbac.Service.IDepartService;
import com.xbwl.sys.service.IStationService;

/**
 *author LiuHao
 *time Jun 9, 2011 2:16:51 PM
 */
@Controller
@Action("departAction")
@Scope("prototype")
@Namespace("/sys")
@Results({
			@Result(name = "departList", location = "/WEB-INF/xbwl/rbac/depart_list.jsp", type = "dispatcher"),
            @Result(name = "input", location = "/WEB-INF/xbwl/rbac/sys_depart.jsp", type = "dispatcher"),
            @Result(name = "reload", type = "json", params = {"root","validateInfo","excludeNullProperties","true"}),
            @Result(name = "list", type = "json", params = {"root","pages","includeProperties","*"})
         })
public class SysDepartAction extends SimpleActionSupport {
	
	@Resource(name="departServiceImpl")
	private IDepartService departService;
	
	@Resource(name="stationServiceImpl")
	private IStationService stationService;
	
	@Resource
	private  DozerBeanMapper dozer;
	
	private SysDepart sysDepart;
	private Long parentId;
	private String parentName;
	private Long stationId;
	private Long departId;
	private String type;//���ͣ��������޸ģ�
	private String parentDepartNo;//�ϼ����ű���
	
	private Long node;

	@Override
	protected Object createNewInstance() {
		return new SysDepart();
	}

	@Override
	public Map getContextMap() {
		return null;
	}

	@Override
	protected IBaseService getManager() {
		return departService;
	}
	@Override
	public Object getModel() {
		return sysDepart;
	}
	
	@Override
	public void setModel(Object obj) {
		sysDepart=(SysDepart)obj;

	}
		
	/** set and get **/
	
	public Long getNode() {
		return node;
	}

	public void setNode(Long node) {
		this.node = node;
	}
	
	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public Long getDepartId() {
		return departId;
	}

	public void setDepartId(Long departId) {
		this.departId = departId;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public Long getStationId() {
		return stationId;
	}

	public void setStationId(Long stationId) {
		this.stationId = stationId;
	}
	
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * @return the parentDepartNo
	 */
	public String getParentDepartNo() {
		return parentDepartNo;
	}

	/**
	 * @param parentDepartNo the parentDepartNo to set
	 */
	public void setParentDepartNo(String parentDepartNo) {
		this.parentDepartNo = parentDepartNo;
	}

	/**
	 * ��ò�����
	 * @return
	 */
	public String getDepartTree(){
		try{
			Struts2Utils.renderJson(departService.getDepartTreeById(node));
		}catch(Exception e){
			logger.error("��ѯ����");
			super.getValidateInfo().setSuccess(false);
			super.getValidateInfo().setMsg("��ѯʧ��,ʧ��ԭ��:"+e.getLocalizedMessage());
			
			return "reload";
		}
		return null;
	}
	/**
	 * �����ϼ�����ID��ѯ������Ϣ
	 * @param parentId
	 * @return
	 */
	public String getDepartByParentId() {
		try {
			setPageConfig();
			departService.getDepartByParentId(this.getPages(), parentId);
		} catch (Exception e) {
			logger.error("��ѯ����");
			super.getValidateInfo().setSuccess(false);
			super.getValidateInfo().setMsg(
					"��ѯʧ��,ʧ��ԭ��:" + e.getLocalizedMessage());
			return "reload";
		}
		return "list";
	}
    /**
     * ׼��ʵ��
     * @throws Exception
     */
    @Override
	public void prepareSave() throws Exception {
        try {
            if (departId != null) {
            	sysDepart=(SysDepart)getManager().getAndInitEntity(departId);
            } else {
            	sysDepart=(SysDepart)createNewInstance();
            }
            if(parentId!=null){
            	sysDepart.setParent((SysDepart)getManager().getAndInitEntity(parentId));
            }
            if(stationId!=null){
            	sysDepart.setSysStation(((SysStation)stationService.getAndInitEntity(stationId)));
            }
            setModel(sysDepart);
        } catch (Exception e) {
            addError("��¼��ѯʧ�ܣ�", e);
        }
    }
	/**
	 * ��ת
	 * @return
	 */
	public String toDepartList(){
		return "departList";
	}
	
	/**
	 * ������в�����Ϣ��ȥ������
	 * @return
	 */
	public String findAll(){
		try {
			super.prepareList();
			departService.findAllDepart(this.getPages(), this.getFilters());
		} catch (Exception e) {
			logger.error("��ѯ����");
			super.getValidateInfo().setSuccess(false);
			super.getValidateInfo().setMsg(
					"��ѯʧ��,ʧ��ԭ��:" + e.getLocalizedMessage());
			return "reload";
		}
		return "list";
	}
	/**
	 * ��д���淽��
	 */
	public String save(){
		try {
			this.setPageConfig();
			Page<SysDepart> page=departService.getDepartByParentId(this.getPages(), parentId);
			int count=page.getResult().size();
			if(type.equals("add")){
				if(sysDepart.getDepartNo()==null||sysDepart.equals("")){
					sysDepart.setDepartNo("10"+(count+1));
				}else{
					sysDepart.setDepartNo(sysDepart.getDepartNo()+"0"+(count+1));
				}
			}else if(type.equals("update")){
				/**
				 * ������ݵ�����ȷ��������ȫ��Ϊ�ֶ����ӣ������������
				 */
				if(sysDepart.getDepartNo() == null || "".equals(sysDepart.getDepartNo())){
					List<Map> departList =departService.getMaxDepartno(parentId);
					if(parentDepartNo == null || "".equals(parentDepartNo)){
						throw new ServiceException("�ϼ����ű���Ϊ�գ����ܱ��棡");
					}else{
						if(departList.size()>0){
							Object objno = departList.get(0).get("MAX_DEPARTNO");
							if(objno == null || "".equals(objno)){
								sysDepart.setDepartNo(parentDepartNo+"0"+1);
							}else{
								String depart_no=objno.toString();
								sysDepart.setDepartNo(depart_no.substring(0, depart_no.length()-1)+(Long.parseLong(depart_no.substring(depart_no.length()-1))+1));
							}
						}
					}
				}
			}
			departService.save(sysDepart);
			super.getValidateInfo().setSuccess(true);
			super.getValidateInfo().setMsg("����ɹ�");
		} catch (Exception e) {
			logger.error("�������");
			super.getValidateInfo().setSuccess(false);
			super.getValidateInfo().setMsg(
					"����ʧ��,ʧ��ԭ��:" + e.getLocalizedMessage());
		}
		return RELOAD;
	}
	
	public  String findDepart(){
		String sql=null;
		//���÷�ҳ����
        setPageConfig();
		Map<String, String> map = ServletUtils.getParametersStartingWith(Struts2Utils.getRequest(), "filter_");
		try{
			sql=this.departService.findDepartService(map);
			this.departService.getPageBySqlMap(this.getPages(), sql, map);
			
		}catch(Exception e){
			addError("��ѯʧ�ܣ�", e);
			
		}
		return this.LIST;
	}
	public String delete(){
		this.setPageConfig();
		Long idLong = this.getId();
		try {
			Page<SysDepart> dPage = departService.getDepartByParentId(this.getPages(), idLong);
			if(dPage.getResult().size()>0){
				this.getValidateInfo().setSuccess(false);
				this.getValidateInfo().setMsg("����ɾ�������¼����ŵĲ�����!");
			}else{
				departService.delete(idLong);
				this.getValidateInfo().setSuccess(true);
				this.getValidateInfo().setMsg("ɾ���ɹ�!");
			}
		} catch (Exception e) {
			addError("ɾ������", e);
		}
		return RELOAD;
	}
}

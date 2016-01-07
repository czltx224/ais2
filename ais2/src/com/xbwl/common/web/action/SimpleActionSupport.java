/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xbwl.common.web.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.record.formula.functions.T;
import org.ralasafe.WebRalasafe;

import com.xbwl.common.bean.ValidateInfo;
import com.xbwl.common.orm.Page;
import com.xbwl.common.orm.PropertyFilter;
import com.xbwl.common.orm.hibernate.HibernateUtils;
import com.xbwl.common.orm.hibernate.pojo.AuditableEntity;
import com.xbwl.common.service.IBaseService;
import com.xbwl.common.web.struts2.Struts2Utils;

/**
 *
 * @author Administrator
 */
public abstract class SimpleActionSupport extends CrudActionSupport {

    private Page<T> pages = new Page<T>();
    private Long id;
    private String ids;
    private String checkItems;
    private String itemsValue;
    private ValidateInfo validateInfo = new ValidateInfo();
    private List<PropertyFilter> filters = null;
    public static final String LIST = "list";
   
    public abstract Map getContextMap();

    // <editor-fold defaultstate="collapsed" desc="���󷽷�">
    protected abstract IBaseService getManager();

    protected abstract Object createNewInstance();
    
    private boolean xml;
    
    private Map contextMap;

	public abstract Object getModel();
	
	private int privilege;

    public int getPrivilege() {
		return privilege;
	}

	public void setPrivilege(int privilege) {
		this.privilege = privilege;
	}

	public abstract void setModel(Object obj);
    // </editor-fold>

    public SimpleActionSupport() {
        limit = 10;
        pages.pageSize(limit);
    }
    
    

    /**
     * ��ѯ�б�
     * @return
     * @throws Exception
     */
    @Override
    public String list() throws Exception {
        try {
            //��listǰ��Ĭ�ϵ���prepareList
            //��ҳ��ѯ
        	Struts2Utils.getSession().setAttribute("gotoPage",Struts2Utils.getRequest().getRequestURI() );
        	if(getPages().getOrderBy()==null){
        		getPages().setOrderBy("id");
        		getPages().setOrder("desc");
        	}
            setPages(getManager().findPage(getPages(), getFilters()));
            
            if(xml){
            	Struts2Utils.renderXml(pages);
            	return null;
            }
        } catch (Exception e) {
            addError("���ݲ�ѯʧ�ܣ�", e);
        }
        return LIST;
    }
    
    
    @SuppressWarnings("unchecked")
	public String ralaList() throws Exception{
    	try {
        	Struts2Utils.getSession().setAttribute("gotoPage",Struts2Utils.getRequest().getRequestURI() );
        	
        	setPages(getManager().getPageByRela(getFilters(),Struts2Utils.getRequest(), privilege, getContextMap(), pages));
			
        	 if(xml){
             	Struts2Utils.renderXml(pages);
             	return null;
             }
        	
		} catch (Exception e) {
			addError("���ݲ�ѯʧ�ܣ�", e);
		}
		return LIST;
    	
    }

    /**
     * ����������ʵ��
     * @return
     * @throws Exception
     */
    @Override
    public String save() throws Exception {
        try {
        	if(WebRalasafe.permit(Struts2Utils.getRequest(), privilege, getModel(), getContextMap())){
        		getManager().save(getModel());
        		validateInfo.setMsg("���ݱ���ɹ���");
        		addMessage("���ݱ���ɹ���");
        	}else{
        		validateInfo.setSuccess(false);
        		validateInfo.setMsg(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
        		addMessage(WebRalasafe.getDenyReason(Struts2Utils.getRequest()));
        	}
            
        } catch (Exception e) {
        	validateInfo.setSuccess(false);
    		validateInfo.setMsg("���ݱ���ʧ�ܣ�");
            addError("���ݱ���ʧ�ܣ�", e);
        }

        return RELOAD;
    }

    /**
     * ɾ��ʵ��
     * @return
     * @throws Exception
     */
    @Override
    public String delete() throws Exception {
        try {
            List pks = getPksByIds();
            getManager().deleteByIds(pks);
            addMessage("����ɾ���ɹ���");
        } catch (Exception e) {
            addError("����ɾ��ʧ�ܣ�", e);
        }
        return RELOAD;
    }



    /**
     * �༭ʵ��
     * @return
     * @throws Exception
     */
    @Override
    public String input() throws Exception {
        return INPUT;
    }

    /**
     * ��ѯ�б�ǰ��Ԥ����
     */
    public void prepareRalaList() {
    	try {
    		
    		//���ù�������
    		getRequestFilter();
    		 getContextMap();
    		 //���÷�ҳ����
	         setPageConfig();
		} catch (Exception e) {
			e.printStackTrace();
		}
       
   
    }
    
    /**
     * ��ѯ�б�ǰ��Ԥ����
     */
    public void prepareList() {
    	try {
    		 //���ù�������
    		getRequestFilter();
    	        //���÷�ҳ����
	         setPageConfig();
		} catch (Exception e) {
			e.printStackTrace();
		}
       
   
    }

    /**
     * ׼��ʵ��
     * @throws Exception
     */
    @Override
    protected void prepareModel() throws Exception {
        try {
            if (id != null) {
            	Object object=getManager().getAndInitEntity(id);
            	if(object instanceof AuditableEntity){
            		AuditableEntity entity=(AuditableEntity)object;
            		entity.setTs(null);
            	}
            	setModel(object);
            } else {
                setModel(createNewInstance());
            }
        } catch (Exception e) {
            addError("��¼��ѯʧ�ܣ�", e);
        }
    }

    /**
     * ��ȡҳ���ϵĲ�ѯ��������������ת��ΪPropertyFilter�б�
     * @return ���������б�
     */
    protected List<PropertyFilter> getRequestFilter() {
        filters = HibernateUtils.buildPropertyFilters(Struts2Utils.getRequest());
        if (getItemsValue() != null && !"".equals(getItemsValue().trim()) && getCheckItems() != null && !"".equals(getCheckItems().trim())) {
            getFilters().add(new PropertyFilter(getCheckItems(), getItemsValue()));
        }
        return getFilters();
    }

    /**
     * ��ȡѡ��״̬��ʵ��id
     * @return
     */
    protected List getPksByIds() {
        List pks = new ArrayList();
        String[] idsValue = ids.split("\\,");
        for (String delId : idsValue) {
            pks.add(Long.valueOf(delId));
        }
        return pks;
    }

    /**
     * ��¼�쳣��Ϣ
     * @param e
     */
    protected void logException(Exception e) {
        e.printStackTrace();
        logger.error(e.getMessage());
    }

    /**
     * �����ɹ�����ҳ������ʾ��Ϣ
     * @param aMessage
     */
    protected void addMessage(String aMessage) {
        addActionMessage("[message]" + aMessage);
    }

    /**
     * ����ʧ�ܣ���ҳ����ʾ������Ϣ����¼�쳣��Ϣ
     * @param anErrorMessage
     * @param e
     */
    protected void addError(String anErrorMessage, Exception e) {
        addActionMessage("[error]" + anErrorMessage);
        
        getValidateInfo().setSuccess(false);
        getValidateInfo().setMsg("����ʧ�ܣ�"+e.getLocalizedMessage());
        
        logException(e);
    }

    protected void refreshFrame(String frameName, String url) {
        addActionMessage("[refresh]" + frameName + "$" + url);
    }

    /**
     * ��ѯǰ���÷�ҳ����
     */
    protected void setPageConfig() {
    	getPages().setLimit(limit);
        getPages().setStart(start);
    }


    public Page<T> getPages() {
		return pages;
	}

	public void setPages(Page<T> pages) {
		this.pages = pages;
	}


    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
     * @return the ids
     */
    public String getIds() {
        return ids;
    }

    /**
     * @param ids the ids to set
     */
    public void setIds(String ids) {
        this.ids = ids;
    }

    /**
     * @return the checkItems
     */
    public String getCheckItems() {
        return checkItems;
    }

    /**
     * @param checkItems the checkItems to set
     */
    public void setCheckItems(String checkItems) {
        this.checkItems = checkItems;
    }

    /**
     * @return the itemsValue
     */
    public String getItemsValue() {
        return itemsValue;
    }

    /**
     * @param itemsValue the itemsValue to set
     */
    public void setItemsValue(String itemsValue) {
        this.itemsValue = itemsValue;
    }

    /**
     * @return the validateInfo
     */
    public ValidateInfo getValidateInfo() {
        return validateInfo;
    }

    /**
     * @param validateInfo the validateInfo to set
     */
    public void setValidateInfo(ValidateInfo validateInfo) {
        this.validateInfo = validateInfo;
    }

    /**
     * @return the filters
     */
    protected List<PropertyFilter> getFilters() {
        return filters;
    }
    
	public void setContextMap(Map contextMap) {
		this.contextMap = contextMap;
	}

    // <editor-fold defaultstate="collapsed" desc="���ߺ���">
    protected void alert(String message) {
        try {
            HttpServletResponse response = Struts2Utils.getResponse();
            response.getWriter().print("<script>alert('" + message + "');</script>");
            response.flushBuffer();
        } catch (IOException ex) {
            Logger.getLogger(SimpleActionSupport.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

	public boolean isXml() {
		return xml;
	}

	public void setXml(boolean xml) {
		this.xml = xml;
	}


    // </editor-fold>
}

package com.xbwl.oper.stock.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.log.anno.ModuleName;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.common.utils.LogType;
import com.xbwl.entity.Customer;
import com.xbwl.entity.OprFaxIn;
import com.xbwl.entity.OprStoreArea;
import com.xbwl.oper.stock.dao.IOprStoreAreaDao;
import com.xbwl.oper.stock.service.IOprStoreAreaService;
import com.xbwl.sys.dao.ICustomerDao;

/**
 * @author CaoZhili
 *
 * �����������ʵ����
 */
@Service("oprStoreAreaServiceImpl")
@Transactional(rollbackFor = { Exception.class })
public class OprStoreAreaServiceImpl extends
		BaseServiceImpl<OprStoreArea, Long> implements IOprStoreAreaService {

	@Resource(name = "oprStoreAreaHibernateDaoImpl")
	private IOprStoreAreaDao oprStoreAreaDao;

	@Resource(name="customerHibernateDaoImpl")
	private ICustomerDao customerDao;
	
	@Override
	public IBaseDAO<OprStoreArea, Long> getBaseDao() {
		return oprStoreAreaDao;
	}

	@ModuleName(value="��д���̲�ѯ",logType=LogType.buss)
	public List<Map> findCusNameService(String[] custprops,String cusName) throws Exception{
		StringBuffer hql=new StringBuffer("select distinct  id as id ,cusName as cusName from Customer where 1=1");
		for(int i=0;i<custprops.length;i++){
			if(i==0){
				hql.append(" and custprop='"+custprops[i]+"'");
			}else{
				hql.append(" or custprop='"+custprops[i]+"'");
			}
		}
		
		if(null!=cusName && !"".equals(cusName)){
			hql.append(" and cusName like").append("'%").append(cusName).append("%'");
		}
		Query query=this.customerDao.getSession().createQuery(hql.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		
		return query.list();
	}

	@ModuleName(value="���������ѯ",logType=LogType.buss)
	public List<Map> findPkAreaclService(String pkAreacl) throws Exception {
		StringBuffer hql=new StringBuffer("select distinct pkAreacl as pkAreacl from Customer where pkAreacl is not null");
		
		if(null!=pkAreacl && !"".equals(pkAreacl)){
			hql.append(" and pkAreacl like").append("'%").append(pkAreacl).append("%'");
		}
		
		Query query=this.customerDao.getSession().createQuery(hql.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		
		return query.list();
	}
	/**
	 * ��ÿ������
	 * @param oprStoreArea ��ǰҵ���ŵĿ������
	 * @param ofi ���ӵ���ϸ��Ӧ�Ĵ�����ϸ
	 * @return
	 */
	@ModuleName(value="��ȡ�������",logType=LogType.buss)
	public String getStockArea(OprStoreArea oprStoreArea,OprFaxIn ofi){
		boolean flag=true;
		//���䷽ʽ
		if(oprStoreArea.getTranMode()!=null){
			if(!oprStoreArea.getTranMode().equals(ofi.getTrafficMode())){
				flag=false;
			}
		}
		//��������
		if(oprStoreArea.getCpArea()!=null){
			Customer cus=customerDao.getAndInitEntity(ofi.getCusId());
			if(cus==null){
				flag=false;
			}else{
				if(!oprStoreArea.getCpArea().equals(cus.getPkAreacl())){
					flag=false;
				}
			}
		}
		//ȥ��
		if(oprStoreArea.getTowhere()!=null){
			if(!oprStoreArea.getTowhere().equals(ofi.getGowhere())){
				flag=false;
			}
		}
		//�����ʽ
		if(oprStoreArea.getTakeMoke()!=null){
			if(!oprStoreArea.getTakeMoke().equals(ofi.getTakeMode())){
				flag=false;
			}
		}
		//���ͷ�ʽ
		if(oprStoreArea.getTowhere()!=null){
			if(!oprStoreArea.getTowhere().equals(ofi.getGowhere())){
				flag=false;
			}
		}
		//�ڲ������ն˲���
		if(oprStoreArea.getOvermemoDepart()!=null){
			if(!oprStoreArea.getOvermemoDepart().equals(ofi.getEndDepart())){
				flag=false;
			}
		}
		if(flag){
			return oprStoreArea.getAreaName(); 
		}else{
			return "";
		}
	}
}

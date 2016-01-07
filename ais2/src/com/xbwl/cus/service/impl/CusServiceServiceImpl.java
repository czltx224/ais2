package com.xbwl.cus.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.cus.dao.ICusServiceDao;
import com.xbwl.cus.service.ICusRecordService;
import com.xbwl.cus.service.ICusServiceService;
import com.xbwl.entity.BasCusService;
import com.xbwl.entity.CusRecord;
import com.xbwl.entity.CusService;
import com.xbwl.entity.CusServiceId;
import com.xbwl.entity.SysDepart;
import com.xbwl.oper.reports.util.AppendConditions;
import com.xbwl.rbac.Service.IDepartService;
import com.xbwl.rbac.Service.IUserService;
import com.xbwl.rbac.entity.SysUser;
import com.xbwl.sys.service.IBasCusService;

/**
 * author CaoZhili time Oct 10, 2011 4:35:39 PM
 */
@Service("cusServiceServiceImpl") 
@Transactional(rollbackFor = { Exception.class })
public class CusServiceServiceImpl extends BaseServiceImpl<CusService, Long>
		implements ICusServiceService {

	@Resource(name = "cusServiceHibernateDaoImpl")
	private ICusServiceDao cusServiceDao;

	@Resource(name="cusRecordServiceImpl")
	private ICusRecordService cusRecordService;
	
	@Resource(name="departServiceImpl")
	private IDepartService departService;
	
	@Resource(name="basCusServiceImpl")
	private IBasCusService basCusServiceService;
	
	@Resource(name="userServiceImpl")
	private IUserService userService;
	
	@Resource(name="appendConditions")
	private AppendConditions appendConditions;
	
	@Override
	public IBaseDAO<CusService, Long> getBaseDao() {

		return this.cusServiceDao;
	}
	//FIXED-ANN ����ע��
	/* (non-Javadoc)����ͻ����淽��
	 * @see com.xbwl.cus.service.ICusServiceService#saveCusService(java.lang.String[], java.lang.Long, java.lang.Boolean)
	 */
	public void saveCusService(String[] splitStrings, Long userId,Boolean flag)
			throws Exception {
		//FIXED ʹ��ǰ���зǿ��ж�
		if(null==userId){
			throw new ServiceException("�û�ID������Ϊ�գ�");
		}
		SysUser user  = this.userService.get(userId);
		CusRecord record = null;
		for (int i = 0; i < splitStrings.length; i++) {
			record = this.cusRecordService.get(Long.valueOf(splitStrings[i]));
			if(null!=record.getCusId() && record.getCusId()>0){
				List<BasCusService> basServiceList = this.basCusServiceService.find("from BasCusService where cusId=? and departId=?", record.getCusId(),user.getBussDepart());
				BasCusService basService = null;
				SysDepart depart = this.departService.get(user.getDepartId());
				//FIXED ʹ��ǰ���зǿ��ж� -- �����ж�
				if(null!=basServiceList && basServiceList.size()>0){
					basService = basServiceList.get(0);
				}else{
					if(null==depart || null==depart.getDepartId()){
						throw new ServiceException(user.getUserName()+" ���Ų���ȷ��");
					}
					basService= new BasCusService();
				}
				basService.setCusId(record.getCusId());
				basService.setServiceDepart(depart.getDepartName()+"");
				basService.setServiceDepartCode(depart.getDepartNo());
				basService.setServiceName(user.getUserName());
				basService.setServiceId(user.getId());	
				
				//���¸ÿͻ��Ŀͷ����ű���
				record.setDepartCode(depart.getDepartNo());
				record.setPrincipal(user.getUserName());
				record.setPrincipalId(user.getId());
				this.cusRecordService.save(record);
				this.basCusServiceService.save(basService);
			}else{
				throw new ServiceException("����ID�����ڣ�");
			}
		}
	}
	//FIXED-ANN ����ע��
	/* (non-Javadoc)����ͻ��Ƴ�����
	 * @see com.xbwl.cus.service.ICusServiceService#moveCusService(java.lang.String[], java.lang.Long, java.lang.Boolean, java.lang.String)
	 */
	public void moveCusService(String[] splitStrings,Long bussDepart) throws Exception {
		//FIXED ʹ��ǰ���зǿ��ж�
		if(null==splitStrings){
			throw new ServiceException("�ͻ�ID����Ϊ�գ�");
		}
		CusRecord record = null;
		for (int i = 0; i < splitStrings.length; i++) {
			record = this.cusRecordService.get(Long.valueOf(splitStrings[i]));
			//FIXED ���ж�Ϊ��ʱ��Ӧ��Ҫȥ�ո���жϳ���
			List<BasCusService> basServiceList = this.basCusServiceService.find("from BasCusService where cusId=? and departId=?", record.getCusId(),bussDepart);
			
			if(null!=basServiceList && basServiceList.size()>0){
				for (int j = 0; j < basServiceList.size(); j++) {
					BasCusService basService = basServiceList.get(j);
					this.basCusServiceService.delete(basService);
				}
				
				//���¸ÿͻ��Ŀͷ����ű���
				record.setPrincipal(null);
				record.setPrincipalId(null);
				
				this.cusRecordService.save(record);
			}
		}
	}
	
	public String findServiceDesignate(Map<String, String> map)
			throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("select t.cus_record_id,r.cus_name,t.user_code,u.user_name,b.service_name,")
		  .append("to_char(t.create_time,'yyyy-MM-dd hh24:mi') create_time,t.create_name,r.cus_id,t.ts")
		  .append(" from cus_service t,cus_record r,sys_user u,bas_cus_service b")
		  .append(" where t.cus_record_id=r.id(+)")
		  .append(" and t.user_code=u.user_code(+)")
		  .append(" and r.cus_id=b.cus_id(+)");
		//�����������
		sb.append(appendConditions.appendCountDate(map));
		//�������
		sb.append(appendConditions.appendConditions(map,null));
		
		return sb.toString();
	}
	
	public void deleteServiceDesignate(Long cusRecordId, String userCode)
			throws Exception {
		List<CusService> cuslist = this.cusServiceDao.find("from CusService where id.cusRecordId=? and id.userCode=?",cusRecordId,userCode);
		if(null!=cuslist && cuslist.size()>0){
			for (int i = 0; i <cuslist.size(); i++) {
				CusService cusService =  cuslist.get(i);
				this.cusServiceDao.delete(cusService);
			}
		}
	}
	
	public void saveServiceDesignate(Long cusRecordId, String userCode)
			throws Exception {
		if(null==cusRecordId){
			throw new ServiceException("�ͻ�ID����Ϊ�գ�");
		}
		if(null==userCode || "".equals(userCode.trim())){
			throw new ServiceException("�û����벻��Ϊ�գ�");
		}
		CusService cusService = null;
		List<CusService> cuslist = this.cusServiceDao.find("from CusService where id.cusRecordId=? and id.userCode=?",cusRecordId,userCode);
		if(null!=cuslist && cuslist.size()>0){
			cusService =  cuslist.get(0);
			cusService.getId().setCusRecordId(cusRecordId);
			cusService.getId().setUserCode(userCode);
		}else{
			CusServiceId cusServiceId = new CusServiceId(cusRecordId,userCode);
			cusService =  new CusService(cusServiceId);
		}
		this.cusServiceDao.save(cusService);
		
	}

}

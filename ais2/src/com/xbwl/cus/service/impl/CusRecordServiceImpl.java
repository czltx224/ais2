package com.xbwl.cus.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.ralasafe.WebRalasafe;
import org.ralasafe.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.orm.Page;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.cus.dao.ICusRecordDao;
import com.xbwl.cus.dao.ICusServiceDao;
import com.xbwl.cus.service.ICusRecordService;
import com.xbwl.cus.service.ICusSearchService;
import com.xbwl.entity.BasCusService;
import com.xbwl.entity.ConsigneeInfo;
import com.xbwl.entity.CusRecord;
import com.xbwl.entity.CusSearch;
import com.xbwl.entity.CusService;
import com.xbwl.entity.CusServiceId;
import com.xbwl.entity.Customer;
import com.xbwl.entity.SysDepart;
import com.xbwl.rbac.Service.IDepartService;
import com.xbwl.rbac.Service.IUserService;
import com.xbwl.rbac.entity.SysUser;
import com.xbwl.sys.dao.IBasCusServiceDao;
import com.xbwl.sys.dao.IConInfoDao;
import com.xbwl.sys.service.ICustomerService;
//REVIEW-ACCEPT 增加注释
//FIXED
/**
 * 客户档案服务层实现类
 * @author LiuHao
 * @time Oct 8, 2011 1:50:33 PM
 */
@Service("cusRecordServiceImpl")
@Transactional(rollbackFor = { Exception.class })
public class CusRecordServiceImpl extends BaseServiceImpl<CusRecord, Long>
		implements ICusRecordService {
	
	@Resource(name = "cusRecordHibernateDaoImpl")
	private ICusRecordDao cusRecordDao;

	@Resource(name="departServiceImpl")
	private IDepartService departService;
	
	@Resource(name="conInfoHibernateDaoImpl")
	private IConInfoDao conInfoDao;
	
	@Resource(name="cusSearchServiceImpl")
	private ICusSearchService cusSearchService;
	
	@Resource(name = "cusServiceHibernateDaoImpl")
	private ICusServiceDao cusServiceDao;
	
	@Resource(name="userServiceImpl")
	private IUserService userService;
	
	@Resource(name="basCusServiceHibernateDaoImpl")
	private IBasCusServiceDao basCusServiceDao;
	
	@Value("${userRight.manager.level}")
	private Long managerLevel;
	
	@Value("${userRight.highmanager.level}")
	private Long highManagerLevel;
	
	@Resource(name = "customerServiceImpl")
	private ICustomerService customerService;
	
	@Override
	public IBaseDAO getBaseDao() {
		return cusRecordDao;
	}
	//FIXED-ANN 增加注释
	/* (non-Javadoc)货物客户列表查询SQL
	 * @see com.xbwl.cus.service.ICusRecordService#findCustomerListService(java.util.Map)
	 */
	public String findCustomerListService(Map<String, String> map)
			throws Exception {
		//REVIEW 传入参数在使用前应当判断是否合法 -- 不明白--map可能会为空
		String searchItem = map.get("searchItem");
		String searchValue = map.get("searchValue");
		
		String checkItems = map.get("checkItems");
		String itemsValue = map.get("itemsValue");
		String searchStatement = map.get("searchStatement");
		String userCode = map.get("userCode");
		//userRight.manager.level
		String managerLevel = map.get("managerLevel");
		String userLevel = map.get("userLevel");
		String bussDepart = map.get("bussDepart");
		String cusName = map.get("LIKES_cusName");
		if(null==bussDepart || "".equals(bussDepart)){
			throw new ServiceException("业务部门不允许为空！");
		}
		
		StringBuffer sb =  new StringBuffer();
		//FIXED 大量的对象直接append不利于代码阅读
		sb.append("select c.id ,c.profit_type,c.cus_name,c.cus_id,bc.service_id,bc.service_name,c.short_name,")
		    .append("c.importance_level,c.is_cq,c.phone,c.main_bussiness,c.expected_cargo,c.expected_turnover,c.addr,")
		    .append("c.website,c.fax,c.company_email,c.attention_remark,c.company_remark,")
		    .append(" c.attention_classify,c.type1,c.area,c.buss_tel,c.man_count,c.buss_airport,")
		    .append(" to_char(c.last_communicate,'yyyy-MM-dd hh24:mi:ss')last_communicate,")
		    .append(" to_char(c.last_buss,'yyyy-MM-dd hh24:mi:ss')last_buss,")
		    .append(" to_char(c.start_buss,'yyyy-MM-dd hh24:mi:ss')start_buss,")
		    .append(" DEVELOP_LEVEL,status,depart_code departcode,d.depart_name as departCodeName");
		boolean flag =false;
		if(null!=managerLevel && !"".equals(managerLevel)){
			if(null!=userLevel && !"".equals(userLevel)){
				if(Long.valueOf(userLevel)>=Long.valueOf(managerLevel)){
					flag = true;
				}
			}
		}
		//FIXED 去掉1=1过滤条件
		sb.append(" from cus_record c,bas_cus_service bc,sys_depart d")
		  .append(" where c.depart_code=d.depart_no(+) and c.cus_id=bc.cus_id(+)");
		sb.append(" and c.depart_id=:bussDepart");
		sb.append(" and bc.depart_id(+)=:bussDepart");
		if(flag){
			sb.append(" and (c.depart_code like :departCode||'%'");
			sb.append("  or c.id in (select t2.cus_record_id ")
			  .append(" from sys_user t1, cus_service t2")
			  .append("  where t1.user_code = t2.user_code and t1.id=:userId))");
		}else{
			sb.append(" and (bc.service_id =:userId");
			sb.append("  or c.id in (select t2.cus_record_id ")
			  .append(" from sys_user t1, cus_service t2")
			  .append("  where t1.user_code = t2.user_code and t1.id=:userId))");
		}
		sb.append(" and status =:status");
		
		if(null!=cusName && !"".equals(cusName)){
			sb.append(" and c.cus_name like '%'||:LIKES_cusName||'%'");
		}
		
		if(null!=searchStatement && !"".equals(searchStatement)){
			sb.append(searchStatement);
		}else{
			if(null!=searchItem && !"".equals(searchItem)){
				if(null!=searchValue && !"".equals(searchValue)){
					
					if(searchItem.equals("LAST_COMMUNICATE")){
						sb.append(" and (last_communicate+interval '").append(searchValue).append("' month)<sysdate");
					}else if(searchItem.equals("LAST_BUSS")){
						sb.append(" and (LAST_BUSS+").append(":searchValue").append(" )<sysdate");
					}else{
						sb.append(" and ").append(searchItem).append("=:searchValue");
					}
				}
			}
			if(null!=checkItems && !"".equals(checkItems)){
				if(null!=itemsValue && !"".equals(itemsValue)){
					String ch = checkItems.substring(checkItems.indexOf("_")+1);
					if(checkItems.indexOf("LIKES")==0){
						sb.append(" and ").append(ch).append(" like '%'||:itemsValue||'%'");
					}else{
						sb.append(" and ").append(ch).append(" = :itemsValue");
					}
				}
			}
		}
		return sb.toString();
	}
	//FIXED-ANN 增加注释
	/* (non-Javadoc)修改客户状态方法
	 * @see com.xbwl.cus.service.ICusRecordService#stopCustomerService(java.lang.String[], java.lang.Long)
	 */
	public void stopCustomerService(String[] strIds) throws Exception {
		//FIXED 使用前进行非空判断
		if(null==strIds){
			throw new ServiceException("要停用的客户ID不允许为空！");
		}
		for (int i = 0; i < strIds.length; i++) {
			CusRecord entity = this.cusRecordDao.get(Long.valueOf(strIds[i]));
			Customer customer = this.customerService.get(entity.getCusId());
			if(null!=entity){
				// 状态 0,删除，1，正常
				entity.setStatus(0l);//停用客户表cus_record
				this.cusRecordDao.save(entity);
			}else{
				throw new ServiceException("ID为"+strIds[i]+"的客户不存在！");
			}
			if(null!=customer){
				//停用客商表
				this.customerService.stopCustomer(customer);
			}
		}
		
	}
	//FIXED-ANN 增加注释
	/* (non-Javadoc)获取分配客户查询语句
	 * @see com.xbwl.cus.service.ICusRecordService#findDistributionCustomerService(java.util.Map)
	 */
	public String findDistributionCustomerService(Map<String, String> map)
			throws Exception {
		//FIXED 使用前进行非空判断
		String distributionItem = map.get("distributionItem");
		String bussDepart = map.get("bussDepart");
		String userLevel = map.get("userLevel");
		if(null==distributionItem || "".equals(distributionItem.trim())){
			//throw new ServiceException("判断是否当前客服员不允许为空！");
			distributionItem="YES";
		}
		String userId = map.get("userId");
		if(null==userId || "".equals(userId.trim())){
			throw new ServiceException("用户编号不能为空！");
		}
		boolean flag =false;
		if(null!=managerLevel && !"".equals(managerLevel)){
			if(null!=userLevel && !"".equals(userLevel)){
				if(Long.valueOf(userLevel)>=Long.valueOf(managerLevel)){
					flag = true;
				}
			}
		}
		String searchItem = map.get("searchItem");
		String searchValue = map.get("searchValue");
		//FIXED 不利于阅读
		StringBuffer sb =  new StringBuffer();
		sb.append("select r.id id, r.cus_id,r.cus_name,r.importance_level,r.develop_level,")
		  .append("r.depart_code,bc.service_id, ")
		  .append(" bc.service_name")
		  .append(" from cus_record r,")
		  .append(" bas_cus_service bc");
	    sb.append(" where r.cus_id = bc.cus_id(+)")
	      .append(" and r.depart_id=:bussDepart");//添加部门限制
	    
	    if(flag){
	    	sb.append(" and r.depart_code like :departCode||'%'");
	    }else{
	    	sb.append(" and bc.service_id =:userId");
	    }
		    
		sb.append(" and status =:status");
		
		if(null!=bussDepart && !"".equals(bussDepart)){
			//sb.append(" and r.depart_id=:departId");
			sb.append(" and bc.depart_id(+)=:bussDepart");
		}
		
		//FIXED 是否考虑大小写的问题？ 不需要
		if(distributionItem.equals("YES")){//属于当前客服员的维护客户
			sb.append(" and bc.service_id=:userId");
		}else if(distributionItem.equals("NO")){//不属于当前客服员维护的客户
			sb.append(" and ( bc.service_id !=:userId")
			  .append(" or bc.service_id is null )");
		}
		
		if(null!=searchItem && !"".equals(searchItem)){
			if(searchItem.equals("service_name_isNULL")){
				sb.append(" and bc.service_name is null");
			}else if(null!=searchValue && !"".equals(searchValue)){
				sb.append(" and ").append(searchItem).append(" like '%'||:searchValue||'%'");
			}
		}
		return sb.toString();
	}

	public void referToDepartService(String[] idStrings, Long departId)
			throws Exception {
		SysDepart depart = this.departService.get(departId);
		CusRecord record=null;
		for (int i = 0; i < idStrings.length; i++) {
			record=this.cusRecordDao.get(Long.valueOf(idStrings[i]));
			record.setDepartCode(depart.getDepartNo());
			this.cusRecordDao.save(record);
		}
	}

	/* (non-Javadoc)查询表的备注
	 * @see com.xbwl.cus.service.ICusRecordService#findTableRemark(java.util.Map)
	 */
	public String findTableRemark(Map<String, String> map) throws Exception {
		//FIXED 使用前进行非空判断 --后面判断
		String remarkName = map.get("remarkName");
		StringBuffer sb =  new StringBuffer();
		//FIXED 直接append对象不利于阅读
		sb.append("select c.comments as chinaName,a.table_name as tableName,")
             .append("  b.COLUMN_NAME as COLUMNNAME,")
             .append(" decode(b.DATA_TYPE,") 
             .append(" 'CHAR','CHAR(' || b.DATA_LENGTH || ')',")
             .append(" 'NUMBER',")
		     .append("  case ")
		     .append(" when (b.DATA_LENGTH = 22 and b.DATA_SCALE = 0) then 'INTEGER' ")
		     .append(" else ")
		     .append("  'NUMBER(' || b.DATA_LENGTH || ',' || b.DATA_SCALE || ')' ")
		     .append(" end,")
		     .append(" 'RAW', 'RAW(' || b.DATA_LENGTH || ')',")
		     .append(" 'TIMESTAMP(6)', 'TIMESTAMP',")
             .append(" 'VARCHAR','VARCHAR(' || b.DATA_LENGTH || ')',")
             .append(" 'VARCHAR2','VARCHAR2(' || b.DATA_LENGTH || ')', b.DATA_TYPE) as dataType,")
		     .append("  d.comments as remark")
		     .append("  from user_tables a")
		     .append(" join user_tab_columns b on a.table_name = b.TABLE_NAME")
		     .append(" left outer join user_tab_comments c on a.table_name = c.table_name")
		     .append("  left outer join user_col_comments d on (b.TABLE_NAME = d.table_name and")
		     .append("  b.COLUMN_NAME = d.column_name)")
		     .append("  where a.table_name = :tableName");
		     if(null!=remarkName && !"".equals(remarkName)){
		    	 sb.append(" and d.comments like '%' || :remarkName || '%'");
		     }
		    sb .append(" order by a.table_name, b.COLUMN_ID");
		
		return sb.toString();
	}
	//FIXED-ANN 增加注释
	/* (non-Javadoc)客户列表自定义查询
	 * @see com.xbwl.cus.service.ICusRecordService#customSearchService(java.util.Map)
	 */
	public String customSearchService(Map<String, String> map) throws Exception {
		//FIXED 使用前进行非空判断 --后面判断
		String managerLevel = map.get("managerLevel");
		String userCode = map.get("userCode");
		String searchId = map.get("searchId");
		
		StringBuffer sb =  new StringBuffer();
		sb.append("select c.id ,c.cus_name,c.importance_level,")
		    .append(" c.attention_classify,c.type1,c.area,c.buss_airport,")
		    .append(" to_char(c.last_communicate,'yyyy-MM-dd hh24:mi:ss')last_communicate,")
		    .append(" to_char(c.last_buss,'yyyy-MM-dd hh24:mi:ss')last_buss,")
		    .append(" to_char(c.start_buss,'yyyy-MM-dd hh24:mi:ss')start_buss,")
		    .append(" DEVELOP_LEVEL,status");
		
		if(null!=searchId && !"".equals(searchId)){
			CusSearch cSearch = this.cusSearchService.get(Long.valueOf(searchId));
			sb.append(" from (select * from cus_record t where 1=1").append(cSearch.getSearchStatement()).append(") c");
		}else{
			throw new ServiceException("没有自定义查询ID!");
		}
		
		if(null!=managerLevel && !"".equals(managerLevel)){
			if(null!=userCode && !"".equals(userCode)){
				if(Long.valueOf(userCode)>=Long.valueOf(managerLevel)){
					//FIXED 去掉1=1条件
					sb.append(" where c.depart_code like :departCode||'%'");
				}else{
					//FIXED 去掉1=1条件
					sb.append(" ,CUS_SERVICE s")
						 .append(" where c.id=s.cus_record_id");
					sb.append(" and s.user_code =:userCode");
				}
			}
		}
		sb.append(" and status =:status");

		return sb.toString();
	}
	/*
	public boolean saveCusAndCon(CusRecord cusRecord,User user) throws Exception {
		SysUser su=userService.get(Long.valueOf(user.get("id").toString()));
		cusRecordDao.save(cusRecord);
		List<ConsigneeInfo> list=conInfoDao.findBy("consigneeTel", cusRecord.getPhone());
		if(list.size()>0){
			ConsigneeInfo ci=list.get(0);
			ci.setCusRecordId(cusRecord.getId());
			conInfoDao.save(ci);
		}
		//创建用户可维护
		CusService cs=new CusService();
		cs.setId(new CusServiceId(cusRecord.getId(),su.getUserCode()));
		cs.setCreateName(su.getUserName());
		cs.setCreateTime(new Date());
		cusServiceDao.save(cs);
		return true;
	}
	*/
	public List findFaxcus(Long departId,String cusName) throws Exception {
		StringBuffer sql=new StringBuffer("select cus_id,cus_name from(select c.id cus_id,c.cus_name from customer c where c.custprop='发货代理' union all select r.cus_id,r.cus_name from cus_record r where r.depart_id=:departId and r.is_cq='发货代理' and r.cus_name not in(select cus.cus_name from customer cus))");
		Map map=new HashMap();
		map.put("departId", departId);
		if(cusName!=null&&!cusName.equals("")){
			sql.append(" where cus_name like :cusName");
			//REVIEW-ACCEPT 模糊查找应当是前后都需要匹配的 吧
			//FIXED LIUH
			map.put("cusName", "%"+cusName+"%");
		}
		return this.createSQLMapQuery(sql.toString(), map).setMaxResults(10).list();
	}
	//FIXED-ANN 增加注释
	/* (non-Javadoc)
	 * 新增客户
	 * @see com.xbwl.cus.service.ICusRecordService#saveCusRecord(com.xbwl.entity.CusRecord, org.ralasafe.user.User, java.lang.String)
	 */
	public void saveCusRecord(CusRecord cusRecord, User user,String conType) throws Exception {
		//REVIEW-ACCEPT 使用前进行非空判断
		//FIXED LIUH
		if(user==null || user.get("id")==null){
			throw new ServiceException("数据异常，ralasafe User 对象为空。");
		}
		SysUser su=userService.get(Long.valueOf(user.get("id").toString()));
		if(su == null){
			throw new ServiceException("数据异常，当前用户在员工信息中不存在!");
		}
		if(cusRecord.getIsCq().equals("发货代理")){
			List<Customer> list=customerService.findBy("cusName", cusRecord.getCusName());
			SysDepart sd=departService.get(su.getDepartId());
			if(sd == null){
				throw new ServiceException("数据异常，当前用户的部门不存在！");
			}
			if(cusRecord.getId()!=null){
				for (int i = 0; i < list.size(); i++) {
					list.remove(i);
				}
				if(cusRecord.getCusId() == null){
					throw new ServiceException("数据异常，该客户没有对应的代理，请联系系统管理员！");
				}
				Customer c = customerService.get(cusRecord.getCusId());
				list.add(c);
			}
			if(list.size()<1){
				//写入客商表
				Customer cus=new Customer();
				cus.setCusName(cusRecord.getCusName());
				cus.setCusAdd(cusRecord.getAddr());
				cus.setCustprop("发货代理");
				cus.setCustshortname(cusRecord.getShortName());
				cus.setEmail(cusRecord.getCompanyEmail());
				cus.setIsProjectcustomer(cusRecord.getIsProjectcustomer());
				cus.setSettlement(cusRecord.getSettlement());
				cus.setUrl(cusRecord.getWebSite());
				cus.setFax1(cusRecord.getFax());
				cus.setPhone1(cusRecord.getBussTel());
				cus.setPhone2(cusRecord.getPhone());
				cus.setLegalbody(cusRecord.getBusinessEntity());
				cus.setMemo(cusRecord.getRemark());
				cus.setFinancialPositon(cusRecord.getFinancialPositon());
				cus.setArea(cusRecord.getArea());
				cus.setScopeBusiness(cusRecord.getScopeBusiness());
				cus.setDevelopLevel(cusRecord.getDevelopLevel());
				//REVIEW-ACCEPT 234214234 是什么意思？
				//FIXED liuh
				//cus.setTs("234214234");
				customerService.save(cus);
				
				cusRecord.setCusId(cus.getId());
				
				//新增客户员代理对应信息
				saveCusService(user,cus,sd);
			}else{
				
				//广州或者深圳有客商了 只新增客服员与客商对应
				Customer c=list.get(0);
				//修改客商基础资料
				if(cusRecord.getId()!=null){
					c.setCustprop(cusRecord.getIsCq());
					c.setCusName(cusRecord.getCusName());
					c.setCusAdd(cusRecord.getAddr());
					c.setStatus(1L);
					c.setCustshortname(cusRecord.getShortName());
					c.setEmail(cusRecord.getCompanyEmail());
					c.setIsProjectcustomer(cusRecord.getIsProjectcustomer());
					c.setSettlement(cusRecord.getSettlement());
					c.setUrl(cusRecord.getWebSite());
					c.setFax1(cusRecord.getFax());
					c.setPhone1(cusRecord.getBussTel());
					c.setPhone2(cusRecord.getPhone());
					c.setLegalbody(cusRecord.getBusinessEntity());
					c.setMemo(cusRecord.getRemark());
					c.setFinancialPositon(cusRecord.getFinancialPositon());
					c.setArea(cusRecord.getArea());
					c.setScopeBusiness(cusRecord.getScopeBusiness());
					c.setDevelopLevel(cusRecord.getDevelopLevel());
					
					customerService.save(c);
				}
				saveCusService(user,c,sd);
				cusRecord.setCusId(c.getId());
				
			}
		}
		if(cusRecord.getId() == null){
			
			cusRecord.setDepartId(Long.valueOf(user.get("bussDepart").toString()));
			cusRecord.setDepartCode(user.get("departId").toString());
			cusRecord.setPrincipalId(Long.valueOf(user.get("id").toString()));
			cusRecord.setPrincipal(user.get("name").toString());
			cusRecord.setStatus(1L);
		}
		cusRecordDao.save(cusRecord);
			
		//如果是收货人转客户，回写收货人信息表
		if(conType!=null||!conType.equals("")){
			List<ConsigneeInfo> list=conInfoDao.findBy("consigneeTel", cusRecord.getPhone());
			//REVIEW-ACCEPT 使用前进行非空判断
			//FIXED LIUH
			if(list != null && list.size() >0 ){
				ConsigneeInfo ci=list.get(0);
				ci.setCusRecordId(cusRecord.getId());
				conInfoDao.save(ci);
			}
		}
		//创建用户可维护
		CusService cs=new CusService();
		cs.setId(new CusServiceId(cusRecord.getId(),su.getUserCode()));
		cs.setCreateName(su.getUserName());
		cs.setCreateTime(new Date());
		cusServiceDao.save(cs);
	}
	/**
	 * 新增客服员
	 * @author LiuHao
	 * @time Feb 22, 2012 10:19:54 AM 
	 * @param user
	 * @param c
	 * @param sd
	 * @throws Exception
	 */
	private void saveCusService(User user,Customer c,SysDepart sd)throws Exception{
		List<BasCusService> listCus=basCusServiceDao.findCusServices(c.getId(), Long.valueOf(user.get("bussDepart").toString()));
		//如果没有客服员 ，才进行新增
		if(listCus.size()<=0){
			BasCusService bcs=new BasCusService();
			bcs.setCreateName(user.get("name").toString());
			bcs.setCreateTime(new Date());
			bcs.setCusId(c.getId());
			bcs.setServiceDepart(sd.getDepartName());
			bcs.setServiceDepartCode(sd.getDepartNo());
			bcs.setServiceName(user.get("name").toString());
			bcs.setServiceId(Long.valueOf(user.get("id")+""));
			bcs.setDepartId(Long.valueOf(user.get("bussDepart").toString()));
			bcs.setDepartName(user.get("rightDepart")+"");
			
			basCusServiceDao.save(bcs);
		}
	}
	public Page findWarnCus(Page page,Long cusRecordId,String cusService) throws Exception {
		User user = WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
		Map map=new HashMap();
		Long bussDepart = Long.valueOf(user.get("bussDepart").toString());
		Long userId = Long.valueOf(user.get("id").toString());
		SysUser su = userService.getAndInitEntity(userId);
		Long userLevel = su.getUserLevel()==null?0:su.getUserLevel();
		map.put("bussDepart", bussDepart);
		StringBuffer sql = new StringBuffer("select round((sysdate-t.last_buss),1) no_diliver_day,round((sysdate-t.last_communicate),1) no_develop_day,t.id,t.warn_delivery_cycle,t.cus_name,to_char(t.last_buss,'yyyy-mm-dd hh24:mi:ss') last_buss,to_char(t.last_communicate,'yyyy-mm-dd hh24:mi:ss') last_communicate,bcs.service_name from cus_record t,bas_cus_service bcs where t.cus_id=bcs.cus_id and (sysdate-t.last_communicate)>=t.warn_delivery_cycle and (sysdate-t.last_buss)>t.warn_delivery_cycle and bcs.depart_id=:bussDepart and t.depart_id=:bussDepart");
		if(userLevel < managerLevel){
			
			sql.append(" and bcs.service_id=:serviceId");
			map.put("serviceId", userId);
		}else{
			if(cusService != null && !"".equals(cusService)){
				sql.append(" and bcs.service_name=:serviceName");
				map.put("serviceName", cusService);
			}
		}
		
		if(userLevel >= highManagerLevel){
			if(cusService != null && !"".equals(cusService)){
				sql.append(" and bcs.service_name=:serviceName");
				map.put("serviceName", cusService);
			}
		}else if(userLevel >= managerLevel && userLevel < highManagerLevel){
			String departCode = user.get("departId").toString();
			sql.append(" and (bcs.service_id=:serviceId or bcs.service_depart_code=:departCode)");
			map.put("serviceId", userId);
			map.put("departCode", departCode);
			
			if(cusService != null && !"".equals(cusService)){
				sql.append(" and bcs.service_name=:serviceName");
				map.put("serviceName", cusService);
			}
		}else{
			sql.append(" and bcs.service_id=:serviceId");
			map.put("serviceId", userId);
		}
		if(cusRecordId!=null){
			sql.append(" and bcs.id=:cusRecordId");
			map.put("cusRecordId", cusRecordId);
		}
		
		return this.getPageBySqlMap(page, sql.toString(), map);
	}
	
	public void startCustomerService(String[] strIds) throws Exception {
		if(null==strIds){
			throw new ServiceException("要启用的客户ID不允许为空！");
		}
		for (int i = 0; i < strIds.length; i++) {
			CusRecord entity = this.cusRecordDao.get(Long.valueOf(strIds[i]));
			Customer customer = this.customerService.get(entity.getCusId());
			if(null!=entity){
				//状态 0,删除，1，正常
				entity.setStatus(1l);//停用客户表cus_record
				this.cusRecordDao.save(entity);
			}else{
				throw new ServiceException("ID为"+strIds[i]+"的客户不存在！");
			}
			if(null!=customer){
				//启用客商表
				this.customerService.startCustomer(customer);
			}
		}
	}
}

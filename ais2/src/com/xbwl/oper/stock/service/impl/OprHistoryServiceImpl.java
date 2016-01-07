package com.xbwl.oper.stock.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.ralasafe.WebRalasafe;
import org.ralasafe.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.log.anno.ModuleName;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.common.utils.LogType;
import com.xbwl.entity.OprFaxIn;
import com.xbwl.entity.OprHistory;
import com.xbwl.entity.OprNode;
import com.xbwl.entity.OprStatus;
import com.xbwl.oper.fax.dao.IOprFaxInDao;
import com.xbwl.oper.stock.dao.IOprHistoryDao;
import com.xbwl.oper.stock.dao.IOprNodeDao;
import com.xbwl.oper.stock.dao.IOprStatusDao;
import com.xbwl.oper.stock.service.IOprHistoryService;
import com.xbwl.rbac.entity.SysUser;
/**
 * @author CaoZhili time Jul 21, 2011 9:59:29 AM
 * 
 * ������ʷ��¼�����ʵ����
 */
@Service("oprHistoryServiceImpl")
@Transactional(rollbackFor = { Exception.class })
public class OprHistoryServiceImpl extends BaseServiceImpl<OprHistory, Long>
		implements IOprHistoryService {

	@Resource(name="oprHistoryHibernateDaoImpl")
	private IOprHistoryDao oprHistoryDao;
	
	@Resource(name="oprNodeHibernateDaoImpl")
	private IOprNodeDao oprNodeDao;
	
	@Resource(name="oprFaxInHibernateDaoImpl")
	private IOprFaxInDao oprFaxInDao;
	
	@Resource(name="oprStatusHibernateDaoImpl")
	private IOprStatusDao oprStatusDao;
	
	/**
	 * ��ת�����쳣AIS2.0�ڵ���
	 */
	public final static Long NODE_NEW_EXCEPTION=106l;//��ת�����쳣AIS2.0�ڵ���
	
	/**
	 * ��ת�㵽AIS2.0�ڵ���
	 */
	public final static Long NODE_REACH_STOCK=101l;//��ת�㵽AIS2.0�ڵ���
	
	private OprHistory oprHistory;
	@Override
	public IBaseDAO<OprHistory, Long> getBaseDao() {

		return this.oprHistoryDao;
	}

	public OprHistory saveLog(Long dno, String ms, OprNode node)throws Exception {

		return saveDnoLog(dno,ms,node);
	}

	public OprHistory[] saveLog(Long[] dno, String ms, OprNode node)throws Exception {
		
		OprHistory[] history = new OprHistory[dno.length];
		
		for (int i = 0; i < history.length; i++) {
			history[i] = saveDnoLog(dno[i],ms,node);
		}
		
		return history;
	}

	public OprHistory[] saveLog(Long[] dno, String[] ms, OprNode node)throws Exception {
		
		OprHistory[] history = new OprHistory[dno.length];
		
		for (int i = 0; i < history.length; i++) {
			history[i] = saveDnoLog(dno[i],ms[i],node);
		}
		
		return history;
	}

	public OprHistory saveLog(Long dno, String ms, Long nodeId) throws Exception{
		
		OprNode node =this.oprNodeDao.getAndInitEntity(nodeId);
		
		return saveDnoLog(dno,ms,node);
	}
	
	public OprHistory saveFiLog(Long dno, String ms, Long nodeId) throws Exception{
		OprNode node =this.oprNodeDao.getAndInitEntity(nodeId);
		return saveDnoFiLog(dno,ms,node);
	}

	public OprHistory[] saveLog(Long[] dno, String ms, Long nodeId) throws Exception{

		OprHistory[] history = new OprHistory[dno.length];
		OprNode node =this.oprNodeDao.getAndInitEntity(nodeId);
		
		for (int i = 0; i < history.length; i++) {
			history[i] = saveDnoLog(dno[i],ms,node);
		}
		
		return history;
	}

	public OprHistory[] saveLog(Long[] dno, String[] ms, Long nodeId)throws Exception {
		
		OprHistory[] history = new OprHistory[dno.length];
		OprNode node =this.oprNodeDao.getAndInitEntity(nodeId);
		
		for (int i = 0; i < history.length; i++) {
			history[i] = saveDnoLog(dno[i],ms[i],node);
		}
		
		return history;
	}
	
	/**
	 * ������־����
	 * @param dno
	 * @param ms
	 * @param node
	 * @return
	 * @throws Exception
	 */
	@ModuleName(value="���͵��Ų�����־����",logType=LogType.buss)
	private OprHistory saveDnoFiLog(Long dno, String ms, OprNode node)throws Exception{
		
		User user = WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
		this.oprHistory = new OprHistory();
		
		this.oprHistory.setDno(dno);
		this.oprHistory.setOprComment(ms);
		this.oprHistory.setOprDepart(user.get("rightDepart").toString());
		this.oprHistory.setOprMan(user.get("name").toString());
		this.oprHistory.setOprName(node.getNodeName());
		this.oprHistory.setOprNode(node.getId());
		this.oprHistory.setOprTime(new Date());
		this.oprHistory.setOprType(node.getNodeType());
		
		this.oprHistoryDao.save(this.oprHistory);
		return this.oprHistory;
	}
	
	/**
	 * ��־����ʹ���״̬�޸�
	 * @param dno
	 * @param ms
	 * @param node
	 * @return
	 * @throws Exception
	 */
	@ModuleName(value="���͵�����־����",logType=LogType.buss)
	private OprHistory saveDnoLog(Long dno, String ms, OprNode node)throws Exception{
		
		User user = WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
		this.oprHistory = new OprHistory();
		
		this.oprHistory.setDno(dno);
		this.oprHistory.setOprComment(ms);
		this.oprHistory.setOprDepart(user.get("rightDepart").toString());
		this.oprHistory.setOprMan(user.get("name").toString());
		this.oprHistory.setOprName(node.getNodeName());
		this.oprHistory.setOprNode(node.getId());
		this.oprHistory.setOprTime(new Date());
		this.oprHistory.setOprType(node.getNodeType());
		
		this.oprHistoryDao.save(this.oprHistory);
		
		OprFaxIn fax = this.oprFaxInDao.get(dno);
		if(null!=fax){
			List<OprNode> nodelist = this.oprNodeDao.findBy("nodeName", fax.getGoodsStatus());
		
			if(null!=nodelist && nodelist.size()>0){
				if(null== nodelist.get(0).getNodeOrder() || null==node.getNodeOrder()){
					throw new ServiceException("�ڵ��ڵ����Ϊ�գ���ά����");
				}
//				if(node.getNodeType()==1l){
					setGoodsStatus(fax,node);
//				}else if(nodelist.get(0).getNodeOrder()>node.getNodeOrder()){
//					return this.oprHistory;
//				}
			}
		}
		return this.oprHistory;
	}
	
	/**��־����ʹ���״̬�޸�
	 * @param fax
	 * @param ms
	 * @param node
	 * @return
	 * @throws Exception
	 */
	@ModuleName(value="������־����",logType=LogType.buss)
	private OprHistory saveDnoLog(OprFaxIn fax, String ms, OprNode node)throws Exception{
		
		User user = WebRalasafe.getCurrentUser(ServletActionContext.getRequest());
		this.oprHistory = new OprHistory();
		
		this.oprHistory.setDno(fax.getDno());
		this.oprHistory.setOprComment(ms);
		this.oprHistory.setOprDepart(user.get("rightDepart").toString());
		this.oprHistory.setOprMan(user.get("name").toString());
		this.oprHistory.setOprName(node.getNodeName());
		this.oprHistory.setOprNode(node.getId());
		this.oprHistory.setOprTime(new Date());
		this.oprHistory.setOprType(node.getNodeType());
		
		this.oprHistoryDao.save(this.oprHistory);
		
		if(null!=fax){
			List<OprNode> nodelist = this.oprNodeDao.findBy("nodeName", fax.getGoodsStatus());
		
			if(null!=nodelist && nodelist.size()>0){
					//����״̬                     //��ǰ����״̬
				if(null== nodelist.get(0).getNodeOrder() || null==node.getNodeOrder()){
					throw new ServiceException("�ڵ��ڵ����Ϊ�գ���ά����");
				}
//				if(node.getNodeType()==1l){
					setGoodsStatus(fax,node);
//				}else if(nodelist.get(0).getNodeOrder()>node.getNodeOrder()){
//					return this.oprHistory;
//				}
			}
		}
		return this.oprHistory;
	}

	public OprHistory saveLog(OprFaxIn fax, String ms, Long nodeId)
			throws Exception {
		OprHistory history = new OprHistory();
		OprNode node =this.oprNodeDao.getAndInitEntity(nodeId);
		
		history=this.saveDnoLog(fax, ms, node);
		
		return history;
	}
	
	
	/**���Ļص������ĵ�ǰ״̬
	 * @param fax
	 * @param node
	 */
	public void setGoodsStatus(OprFaxIn  fax,OprNode node){
		
		String sql="from OprHistory t where t.dno=? and oprType =? order by id desc";
		
		List<OprHistory> hislist = this.oprHistoryDao.createQuery(sql, oprHistory.getDno(),1l).list();
		if(null!=hislist && hislist.size()>0){
			if(node.getNodeType()==3l && hislist.size()>1){
				fax.setGoodsStatus(hislist.get(1).getOprName());
				this.oprFaxInDao.save(fax);
			}else if(node.getNodeType()==1l){
				fax.setGoodsStatus(node.getNodeName());
				this.oprFaxInDao.save(fax);
			}
		}else{
			fax.setGoodsStatus(node.getNodeName());
			this.oprFaxInDao.save(fax);
		}
	}

	public String findHistoryByDno(Long dno) throws Exception {
		
		StringBuffer sb = new StringBuffer();
		sb.append("select id,opr_name as oprName,opr_node as oprNode,to_char(opr_time,'yyyy-MM-dd hh24:mi') as oprTime,")
		  .append("opr_comment as oprComment,opr_man as oprMan,opr_depart as oprDepart,")
		  .append("d_no as dno,opr_type as oprType from opr_history where d_no=:EQL_dno");
		sb.append(" order by opr_time asc,opr_node asc ");
		if(null==dno || 0l==dno){
			throw new ServiceException("���͵��Ŵ���");
		}
	    return sb.toString();
	}
	
	@ModuleName(value="EDI��дAIS��־",logType=LogType.buss)
	public void saveTransitLog(OprHistory oprHistory) throws Exception {
		
		OprFaxIn faxIn = this.oprFaxInDao.get(oprHistory.getDno());
		if(null==faxIn){
			//throw new ServiceException("û���ҵ������͵��ţ�");
			return;
		};
		OprNode node = this.oprNodeDao.get(oprHistory.getOprNode());
		oprHistory.setOprName(node.getNodeName());
		oprHistory.setOprType(node.getNodeType());
		//oprHistory.setId(null);
		this.oprHistoryDao.save(oprHistory);

		if(this.NODE_NEW_EXCEPTION.equals(oprHistory.getOprNode())){
			//����������쳣��������쳣����
			OprStatus status =null;
			List<OprStatus> statusList = this.oprStatusDao.findBy("dno", oprHistory.getDno());
			
			if(null!=statusList && statusList.size()>0){
				status = statusList.get(0);
				status.setIsOprException(1l);//��״̬���б��Ϊ�쳣
				this.oprStatusDao.save(status);
			}
		}else if(this.NODE_REACH_STOCK.equals(oprHistory.getOprNode())){
			OprStatus status =null;
			List<OprStatus> statusList = this.oprStatusDao.findBy("dno", oprHistory.getDno());
			
			if(null!=statusList && statusList.size()>0){
				status = statusList.get(0);
				status.setEdiReachStatus(1l);//EDI�㵽״̬ 0��δ�㵽��1���㵽
				status.setEdiReachTime(oprHistory.getOprTime());//EDI�㵽ʱ��
				this.oprStatusDao.save(status);
			}
		}else{
			faxIn.setGoodsStatus(node.getNodeName());//�޸Ĵ����״̬
			this.oprFaxInDao.save(faxIn);
		}
	}

	public void saveLogByUser(Long dno,String msg,Long nodeId,SysUser user) throws Exception {
		
		OprNode node =this.oprNodeDao.get(nodeId);
		this.oprHistory = new OprHistory();
		
		this.oprHistory.setDno(dno);
		this.oprHistory.setOprComment(msg);
		if(null!=user){
			this.oprHistory.setOprDepart(user.getRightDepart());
			this.oprHistory.setOprMan(user.getUserName());
		}
		this.oprHistory.setOprName(node.getNodeName());
		this.oprHistory.setOprNode(node.getId());
		this.oprHistory.setOprTime(new Date());
		this.oprHistory.setOprType(node.getNodeType());
		
		this.oprHistoryDao.save(this.oprHistory);
		
		OprFaxIn fax = this.oprFaxInDao.get(dno);
		if(null!=fax && node.getNodeType()==1l){
			fax.setGoodsStatus(node.getNodeName());
			this.oprFaxInDao.save(fax);
		}
	}

}

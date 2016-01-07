package com.xbwl.oper.fax.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.ralasafe.WebRalasafe;
import org.ralasafe.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.entity.FlowNodeinfo;
import com.xbwl.entity.OprChangeDetail;
import com.xbwl.entity.OprChangeMain;
import com.xbwl.entity.OprFaxIn;
import com.xbwl.entity.OprValueAddFee;
import com.xbwl.flow.service.IFlowNodeinfoService;
import com.xbwl.flow.service.IWorkFlowbaseService;
import com.xbwl.flow.vo.FlowSaveVo;
import com.xbwl.oper.fax.dao.IOprFaxChangeDao;
import com.xbwl.oper.fax.service.IOprFaxChangeDetailService;
import com.xbwl.oper.fax.service.IOprFaxChangeService;
import com.xbwl.rbac.Service.IDepartService;

/**
 *@author LiuHao
 *@time Aug 25, 2011 3:03:18 PM
 */
@Service("oprFaxChangeServiceImpl")
@Transactional(rollbackFor = { Exception.class })
public class OprFaxChangeServiceImpl extends BaseServiceImpl<OprChangeMain,Long> implements
		IOprFaxChangeService {
	@Resource(name="oprFaxChangeHibernateDaoImpl")
	private IOprFaxChangeDao oprFaxChangeDao;
	@Resource(name="departServiceImpl")
	private IDepartService departService;
	@Resource(name="oprFaxChangeDetailServiceImpl")
	private IOprFaxChangeDetailService oprFaxChangeDetailService;
	@Resource(name = "workFlowbaseServiceImpl")
	private IWorkFlowbaseService workFlowbaseService;
	@Resource(name = "flowNodeinfoServiceImpl")
	private IFlowNodeinfoService flowNodeinfoService;
	private OprChangeDetail oprchangeDetail=new OprChangeDetail();
	@Value("${oprFaxChangeServiceImpl.changeFlow.pipeId}")
	private Long pipeId;
	@Override
	public IBaseDAO getBaseDao() {
		return oprFaxChangeDao;
	}
	public void saveOprFaxChange(List<OprValueAddFee> addFeeList,OprChangeMain oprChangeMain,
			List<OprChangeDetail> list,OprFaxIn ofi,String changeType) throws Exception {
		User user=WebRalasafe.getCurrentUser(Struts2Utils.getRequest());
		HashSet<OprChangeDetail> set=new HashSet<OprChangeDetail>(list);
		Iterator<OprChangeDetail> iter=set.iterator();
		while(iter.hasNext()){
			OprChangeDetail ocd=iter.next();
			ocd.setCreateName(user.get("name").toString());
			ocd.setCreateTime(new Date());
			ocd.setOprChangeMain(oprChangeMain);
		}
		oprChangeMain.setCreateName(user.get("name").toString());
		oprChangeMain.setCreateTime(new Date());
		oprChangeMain.setUpdateName(user.get("name").toString());
		oprChangeMain.setUpdateTime(new Date());
		oprChangeMain.setOprChangeDetail(set);
		
		StringBuffer addStr = new StringBuffer("");
		if(addFeeList != null){
			for (int i = 0; i < addFeeList.size(); i++) {
				OprValueAddFee ovaf =addFeeList.get(i);
				addStr.append(ovaf.getId());
				addStr.append(",");
				
				addStr.append(ovaf.getFeeName());
				addStr.append(",");
				
				addStr.append(ovaf.getFeeCount());
				addStr.append(",");
				
				addStr.append(ovaf.getPayType());
				
				if(i != addFeeList.size()-1){
					addStr.append(";");
				}
			}
		}
//		if(oprChangeMain.getIsSystem()==1){
//			oprChangeMain.setStatus(2L);
//			this.save(oprChangeMain);
//			oprFaxChangeDetailService.updateChangeRecord(addFeeList,list, ofi,oprChangeMain.getId());
//		}else{
			oprChangeMain.setStatus(1L);
			this.save(oprChangeMain);
			
			StringBuffer formDetailStr=new StringBuffer("");
			if(list!=null){
				for(int i=0;i<list.size();i++){
					OprChangeDetail od=list.get(i);
					formDetailStr.append(od.getChangeFieldZh()+(char)17+od.getChangePre()+(char)17+od.getChangePost()+(char)17+od.getChangeField()+(char)17+changeType+(char)17+ofi.getInDepart()+(char)17+addStr.toString()+(char)17+oprChangeMain.getIsSystem());
					//formDetailStr.append(od.getChangeFieldZh()+(char)17+od.getChangePre()+(char)17+od.getChangePost()+(char)17+od.getChangeField()+(char)17+changeType+(char)17+ofi.getInDepart());
					if(i!=list.size()-1){
						formDetailStr.append((char)18);
					}
				}
			}
			//流程驱动
			//写入流程相关表
			FlowNodeinfo nodeInfo = flowNodeinfoService.getSEnodeinfoByPipeId(pipeId, "start");
			
			FlowSaveVo flowVo=new FlowSaveVo();
			flowVo.setApplyRemark(oprChangeMain.getRemark());
			flowVo.setAuditRemark(oprChangeMain.getRemark());
			flowVo.setPipeId(pipeId);
			flowVo.setNodeId(nodeInfo.getId());
			flowVo.setOprType("submit");
			flowVo.setLogType(1l);
			flowVo.setDno(ofi.getDno());
			flowVo.setFormDetailStr(formDetailStr.toString());
			flowVo.setChangeType(changeType);
			
			workFlowbaseService.flowSubmit(flowVo);
		//}
	}

	public String getSelectQuerySql(Long dno) throws Exception {
		StringBuffer sb = new StringBuffer();		
			sb.append("SELECT  ");
					sb.append("t1.ID  AS t1_ID , t1.IS_SYSTEM  AS t1_IS_SYSTEM , t1.CREATE_NAME  AS  ");
					sb.append("t1_CREATE_NAME , t1.CREATE_TIME  AS t1_CREATE_TIME , t1.UPDATE_NAME  AS   ");
					sb.append("t1_UPDATE_NAME , t1.UPDATE_TIME  AS t1_UPDATE_TIME , ");
					sb.append("t1.REMARK  AS t1_REMARK , t1.STATUS  AS t1_STATUS , t1.D_NO  AS t1_D_NO ,   ");
					sb.append("t1.DEPART_ID  AS t1_DEPART_ID , t1.DEPART_NAME  AS t1_DEPART_NAME , t0.ID    ");
					sb.append("AS t0_ID , t0.OPR_CHANGE_MAIN_ID  AS t0_OPR_CHANGE_MAIN_ID , t0.CHANGE_FIELD   ");
					sb.append("AS t0_CHANGE_FIELD , t0.CHANGE_FIELD_ZH  AS t0_CHANGE_FIELD_ZH ,   ");
					sb.append("t0.CHANGE_PRE  AS t0_CHANGE_PRE , t0.CHANGE_POST  AS t0_CHANGE_POST ");
			sb.append("FROM  OPR_CHANGE_DETAIL t0,OPR_CHANGE_MAIN t1    ");
	        sb.append("WHERE  (   t0.opr_change_main_id  =  t1.ID )  ");
	        		sb.append(" AND t1.STATUS>0  ");
					sb.append(" AND t1.d_no=:dno  ");
		return sb.toString();
	}
	public OprChangeMain getChangeByDno(Long dno) throws Exception {
		List<OprChangeMain> list = this.find("from OprChangeMain ocm where ocm.dno=? and ocm.status=1", dno);
		if(list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
}

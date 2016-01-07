package com.xbwl.oper.psonline.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.scheduling.XbwlScheduling;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.entity.BizOperateLog;
import com.xbwl.oper.psonline.dao.IPSBizOperateLogDao;
import com.xbwl.oper.psonline.service.IPSBizOperateLogService;
import com.xbwl.oper.stock.dao.IOprHistoryDao;

/**
 * 网营日志服务层实现类
 * @author czl
 * @time 2012-04-10
 */
@Service("pSBizOperateLogServiceImpl")
@Transactional(rollbackFor=Exception.class)
public class PSBizOperateLogServiceImpl extends
		BaseServiceImpl<BizOperateLog, String> implements
		IPSBizOperateLogService,XbwlScheduling {

	@Resource(name="pSBizOperateLogHibernateDaoImpl")
	private IPSBizOperateLogDao psBizOperateLogDao;
	
	@Resource(name="oprHistoryHibernateDaoImpl")
	private IOprHistoryDao oprHistoryDao;
	
	@Override
	public IBaseDAO<BizOperateLog, String> getBaseDao() {
		return this.psBizOperateLogDao;
	}

	public void execute() {
		try{
		Query query = this.psBizOperateLogDao.createSQLQuery("select  max(t.opr_time) maxTime from psonline.biz_operate_log t ");
			List maxList = query.list();
			if(null!=maxList && maxList.size()==1){
				HashMap map = (HashMap)maxList.get(0);
				System.out.println(maxList.get(0).getClass());
				Date maxTime = (Date)map.get("MAXTIME");
				
				if(null!=maxTime){
					query = this.oprHistoryDao.createSQLQuery("select t.*,n.out_name from Opr_History t,Opr_Node n where t.opr_name=n.node_name and n.is_Out=? and opr_Time>?", 1l,maxTime);
				}else{
					query = this.oprHistoryDao.createSQLQuery("select t.*,n.out_name from Opr_History t,Opr_Node n where t.opr_name=n.node_Name and n.is_Out=?",1l);
				}
				List<HashMap> listMap = query.list();
				BizOperateLog bizLog = null;
				for(HashMap hisMap :listMap){
					bizLog = new BizOperateLog();
					//System.out.println(hisMap.get("OPR_TIME"));
					try{
						bizLog.setOprTime((Date)hisMap.get("OPR_TIME"));
					}catch (Exception e) {
						bizLog.setOprTime(null);
					}
					bizLog.setCreateTime(new Date());
					bizLog.setRemark(hisMap.get("OPR_COMMENT")+"");
					bizLog.setOnlineRemark(hisMap.get("OUT_NAME")+"");
					bizLog.setDNo(hisMap.get("D_NO")+"");
					this.psBizOperateLogDao.save(bizLog);
				}
				
			}else{
				logger.error("网营日志定时任务查询错误！");
			}
		}catch (Exception e) {
			logger.error("网营日志定时任务查询错误！");
			e.printStackTrace();
		}
	}

}

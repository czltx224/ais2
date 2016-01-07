package com.xbwl.oper.szsm.service.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.log.anno.ModuleName;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.orm.Page;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.common.utils.LogType;
import com.xbwl.entity.CtEstimate;
import com.xbwl.entity.DigitalChinaExchange;
import com.xbwl.oper.reports.util.AppendConditions;
import com.xbwl.oper.szsm.beans.DispatchingXMLBean;
import com.xbwl.oper.szsm.beans.SignInXMLBean;
import com.xbwl.oper.szsm.dao.IDataExchangeDao;
import com.xbwl.oper.szsm.service.IDataExchangeService;
import com.xbwl.oper.szsm.util.DotrackUtil;

/**
 * 神州数码对接服务实现类
 * @author czl
 * @date 2012-06-27
 */
@Service("dataExchangeServiceImpl")
@Transactional(rollbackFor=Exception.class)
public class DataExchangeServiceImpl extends
		BaseServiceImpl<DigitalChinaExchange, Long> implements
		IDataExchangeService {
	
	@Resource(name = "dataExchangeDaoImpl")
	private IDataExchangeDao dataExchangeDao;

	@Value("${szsm_drivername}")
	private String szsm_drivername;
	
	@Value("${szsm_driverphone}")
	private String szsm_driverphone;
	
	@Value("${szsm_carrierno}")
	private String szsm_carrierno;
	
	@Resource(name="dotrackUtil")
	private DotrackUtil dotrackUtil;
	
	@Value("${szsm_cus_id}")
	private Long szsm_cus_id;
	
	@Resource(name="appendConditions")
	private AppendConditions appendConditions;
	
	@Override
	public IBaseDAO<DigitalChinaExchange, Long> getBaseDao() {
		return this.dataExchangeDao;
	}

	private static Logger logger = Logger
			.getLogger(DataExchangeServiceImpl.class);

	@ModuleName(value="神州数码配送对接",logType=LogType.buss)
	public void doDispatchingExchange() throws Exception {
		Page pg = new Page();
		pg.setStart(0);// 起始页码
		pg.setLimit(100);// 分页大小
		String sql = this.dataExchangeDao.searchTmTransferD();
		Page page = this.getPageBySql(pg, sql);
		int successCount = 0;
		int failCount = 0;
		
		logger.warn("<--------------------开始对接配送收数据-------------------->");
		for (int i = 1; i <= page.getTotalPages(); i++) {
			List list1 = page.getResultMap();
	
			DispatchingXMLBean dXMLBean = new DispatchingXMLBean();
			for (Iterator localIterator = list1.iterator(); localIterator.hasNext();) {
				Map map = (Map) localIterator.next();
				dXMLBean.setDispense_time((String) map.get("TIME"));
				dXMLBean.setVehicleno((map.get("CAR_CODE") == null) ? ""
						: (String) map.get("CAR_CODE"));
				dXMLBean.setDono((String) map.get("SUB_NO"));
				dXMLBean.setCarrierno(this.szsm_carrierno);
				dXMLBean.setDrivername(this.szsm_drivername);
				dXMLBean.setDriverphone(this.szsm_driverphone);
				
				boolean flag = false;
				try {
					flag = dotrackUtil.toWrite(dXMLBean.getXML());
				}catch(java.net.ConnectException ne){
					throw ne;
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
				if (flag) {
					DigitalChinaExchange dce = new DigitalChinaExchange();
					dce.setDno(Long.valueOf(map.get("D_NO")+""));
					dce.setOtherId(map.get("ID")+"");
					dce.setFree1(map.get("SUB_NO")+"");
					dce.setType("1");
					dce.setCreateTime(new Date());
					this.dataExchangeDao.save(dce);
					++successCount;
				} else {
					++failCount;
				}
			}
			if (page.getPageNo() > i) {
				//pg.setStart(i - 1);
				page = this.getPageBySql(pg, sql);
			}else{
				break;
			}
		}

		StringBuffer mesg = new StringBuffer("共需对接数据：");
		mesg.append(page.getTotalCount()).append("条。成功").append(successCount).append(
				"条,失败").append(failCount).append("条");
		logger.warn(mesg);
		logger.warn("<--------------------配送收数据对接完毕-------------------->");
	}

	@ModuleName(value="神州数码签收对接",logType=LogType.buss)
	public void doSingInExchange() throws Exception {
		Page pg = new Page();
		pg.setStart(0);// 起始页码
		pg.setLimit(100);// 分页大小
		String sql = this.dataExchangeDao.searchTmDnSign();
		Page<CtEstimate> page = this.getPageBySql(pg, sql);
		int successCount = 0;
		int failCount = 0;
		
		logger.warn("<--------------------开始对接签收数据-------------------->");
		for (int i = 1; i <= page.getTotalPages(); i++) {
			List list = page.getResultMap();
			SignInXMLBean siXMLBean = new SignInXMLBean();
			for (Iterator localIterator = list.iterator(); localIterator.hasNext();) {
				Map map = (Map) localIterator.next();
				siXMLBean.setDono((String) map.get("SUB_NO"));
				siXMLBean.setSigntime((String) map.get("TIME"));
				siXMLBean.setSigner((String) map.get("SIGN_MAN"));
				siXMLBean.setCarrierno(this.szsm_carrierno);
				
				boolean flag = false;
				try {
					flag = dotrackUtil.toWrite(siXMLBean.getXML());
				} catch(java.net.ConnectException ne){
					throw ne;
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
				if (flag) {
					DigitalChinaExchange dce = new DigitalChinaExchange();
					dce.setDno(Long.valueOf(map.get("D_NO")+""));
					dce.setOtherId(map.get("ID").toString());
					dce.setFree1(map.get("SUB_NO").toString());
					dce.setType("2");
					dce.setCreateTime(new Date());
					this.dataExchangeDao.save(dce);
					++successCount;
				} else {
					++failCount;
				}
			}
			if (page.getPageNo() > i) {
				// pg.setStart(i-1);
				page = this.getPageBySql(pg, sql);
			}else{
				break;
			}
		}

		StringBuffer mesg = new StringBuffer("共需对接数据：");
		mesg.append(page.getTotalCount()).append("条。成功").append(successCount).append(
				"条,失败").append(failCount).append("条");
		logger.warn(mesg);
		logger.warn("<--------------------签收数据对接完毕-------------------->");
	}

	public String findList(Map<String, String> map) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("select t.id,t.other_id,t.d_no,t.type,to_char(t.create_time,'yyyy-MM-dd hh24:mi') create_time,t.free1,t.free2")
		  .append(" from digital_china_exchange t where 1=1");
		
		sb.append(this.appendConditions.appendCountDate(map));
		sb.append(this.appendConditions.appendConditions(map, null));
		sb.append(" order by create_time desc,d_no desc");
		return sb.toString();
	}

	public String findCount(Map<String, String> map) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("select count(*) chuanzhen,sum(decode(s.out_status,1,1,2,1,0)) chuku,")
          .append("sum(decode(e.type,1,1,0)) peicheduijie,")
          .append("sum(decode(e.type,2,1,0)) qianshouduijie,")
          .append("sum(decode(e.type,1,1,2,1,0)) duijie,")
          .append("to_char(f.create_time,'yyyy-MM-dd') create_time") 
          .append(" from opr_fax_in f,opr_status s,digital_china_exchange e")
          .append(" where f.d_no=s.d_no(+) and f.d_no=e.d_no(+)"); 
        sb.append(" and f.status=1") 
          .append(" and f.cus_id=").append(szsm_cus_id);
        
        sb.append(this.appendConditions.appendCountDate(map));
        sb.append(" group by to_char(f.create_time,'yyyy-MM-dd')");
        sb.append(" order by to_char(f.create_time,'yyyy-MM-dd') desc");
        return sb.toString();
	}

	public String findCountSum(Map<String, String> map) throws Exception {
		String str = findCount(map);
		StringBuffer sb = new StringBuffer();
		sb.append("select sum(tt.chuanzhen) chuanzhen,sum(tt.chuku) chuku,sum(tt.peicheduijie) peicheduijie,");
		sb.append("sum(tt.qianshouduijie) qianshouduijie,sum(tt.duijie) duijie");
		sb.append(" from (").append(str.substring(0,str.indexOf(("order by")))).append(" ) tt");
		return sb.toString();
	}
}
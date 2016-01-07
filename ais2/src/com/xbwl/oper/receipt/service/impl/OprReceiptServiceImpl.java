package com.xbwl.oper.receipt.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.log.anno.ModuleName;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.common.utils.LogType;
import com.xbwl.entity.OprReceipt;
import com.xbwl.entity.OprRemark;
import com.xbwl.entity.OprStatus;
import com.xbwl.oper.receipt.dao.IOprReceiptDao;
import com.xbwl.oper.receipt.service.IOprReceiptService;
import com.xbwl.oper.receipt.vo.ReceiptConfirmVo;
import com.xbwl.oper.reports.util.AppendConditions;
import com.xbwl.oper.stock.dao.IOprRemarkDao;
import com.xbwl.oper.stock.service.IOprHistoryService;
import com.xbwl.oper.stock.service.IOprStatusService;

/**
 * author CaoZhili time Jul 25, 2011 6:04:11 PM
 * 
 * 回单服务层实现类
 */
@Service("oprReceiptServiceImpl")
@Transactional(rollbackFor = { Exception.class })
public class OprReceiptServiceImpl extends BaseServiceImpl<OprReceipt, Long>
		implements IOprReceiptService {

	@Resource(name = "oprReceiptHibernateDaoImpl")
	private IOprReceiptDao oprReceiptDao;

	@Resource(name = "oprHistoryServiceImpl")
	private IOprHistoryService oprHistoryService;
	
	@Resource(name="oprStatusServiceImpl")
	private IOprStatusService oprStatusService;
	
	@Resource(name="oprRemarkHibernateDaoImpl")
	private IOprRemarkDao oprRemarkDao;
	
	@Value("${oprReceiptServiceImpl.log_saveReport}")
	private Long   log_saveReport;
	
	@Value("${oprReceiptServiceImpl.log_saveGet}")
	private Long   log_saveGet;
	
	@Value("${oprReceiptServiceImpl.log_saveConfirm}")
	private Long   log_saveConfirm;
	
	@Value("${oprReceiptServiceImpl.log_saveOut}")
	private Long   log_saveOut;
	
	@Value("${oprReceiptServiceImpl.log_buqian}")
	private Long   log_buqian;
	
	@Value("${oprReceiptServiceImpl.log_cancelOut}")
	private Long   log_cancelOut;
	
	@Value("${oprReceiptServiceImpl.log_cancelConfirm}")
	private Long   log_cancelConfirm;
	
	@Resource(name="appendConditions")
	private AppendConditions appendConditions;
	
	@Override
	public IBaseDAO<OprReceipt, Long> getBaseDao() {
		return this.oprReceiptDao;
	}
	
	@ModuleName(value="回单入库点到",logType=LogType.buss)
	public void saveReportService(OprReceipt oprReceipt,Long id)
			throws Exception {
		
		OprReceipt entity = this.oprReceiptDao.get(id);

		entity.setReachNum(oprReceipt.getReachNum());
		entity.setReachMan(oprReceipt.getReachMan());
		entity.setReachTime(oprReceipt.getReachTime());
		entity.setReachStatus(oprReceipt.getReachStatus());

		entity.setCurStatus(oprReceipt.getCurStatus());
		
		this.oprHistoryService.saveLog(entity.getDno(), "回单入库，"+entity.getReachMan()+"入库"+entity.getReachNum()+"份。", log_saveReport);
		this.oprReceiptDao.save(entity);
	}
	
	@ModuleName(value="领单",logType=LogType.buss)
	public void saveGetService(OprReceipt oprReceipt, String ids)
			throws Exception {
		String[] strids = ids.split(",");
		OprReceipt entity = null;
		for (int i = 0; i < strids.length; i++) {
			entity = this.oprReceiptDao.get(new Long(strids[i]));

			entity.setGetMan(oprReceipt.getGetMan());
			entity.setGetStatus(oprReceipt.getGetStatus());
			entity.setGetTime(oprReceipt.getGetTime());
			entity.setGetNum(oprReceipt.getGetNum());

			entity.setCurStatus(oprReceipt.getCurStatus());
			
			this.oprHistoryService.saveLog(entity.getDno(), "领单，"+entity.getGetMan()+"领取"+entity.getGetNum()+"份。", log_saveGet);
			this.oprReceiptDao.save(entity);
		}

	}

	@ModuleName(value="回单确收",logType=LogType.buss)
	public void saveConfirmService(List<ReceiptConfirmVo> confirmVoList)
			throws Exception {
		for(ReceiptConfirmVo confirmVo : confirmVoList){
			OprReceipt entity = this.oprReceiptDao.findUniqueBy("dno",confirmVo.getDno());
			
			if(null!=entity.getConfirmStatus() && (entity.getConfirmStatus()==1l || entity.getConfirmStatus()==2l)){
				throw new ServiceException("配送单号为"+entity.getDno()+"的货物已经确收，如果要再次确收请先撤销！");
			}
			
			List<OprStatus> statusList = this.oprStatusService.findBy("dno", entity.getDno());
			
			if(null!=statusList && statusList.size()>0){
				OprStatus status = statusList.get(0);
//				if(null==status.getOutStatus() || status.getOutStatus()==0l || status.getOutStatus()==3l){
//					throw new ServiceException("配送单号为"+entity.getDno()+"的货物还没有出库，请先出库！");
//				}
				if(null==status.getSignStatus() || status.getSignStatus()<1){
					throw new ServiceException("配送单号为"+entity.getDno()+"的货物还没有签收，请先签收！");
				}
				
			}else{
				throw new ServiceException("没有这票货的记录！");
			}
	
			entity.setConfirmMan(confirmVo.getConfirmMan());
			entity.setConfirmRemark(confirmVo.getRemark());
	
			entity.setConfirmNum(confirmVo.getConfirmNum());
			entity.setConfirmTime(confirmVo.getConfirmTime());
			entity.setConfirmStatus(confirmVo.getConfirmStatus());
			entity.setCurStatus(confirmVo.getCurStatus());
			
			if(null!=entity.getConfirmRemark()&&!"".equals(entity.getConfirmRemark().trim())){
				OprRemark remark = new OprRemark();
				remark.setDno(entity.getDno());
				remark.setRemark(entity.getConfirmRemark().trim());
				this.oprRemarkDao.save(remark);
			}
			
			this.oprHistoryService.saveLog(entity.getDno(), "回单确收，确收"+entity.getConfirmNum()+"份。", log_saveConfirm);
			this.oprReceiptDao.save(entity);
		}
	}

	@ModuleName(value="回单寄出",logType=LogType.buss)
	public void saveOutService(OprReceipt oprReceipt,String[] dnos)
			throws Exception {
		OprReceipt entity = null;
		Double outCost = (oprReceipt.getOutCost()/dnos.length);
		for (int i=0;i<dnos.length;i++) {
			entity = this.oprReceiptDao.findUniqueBy("dno",Long.valueOf(dnos[i]));
			
			if(null==entity.getConfirmStatus() || entity.getConfirmStatus()==0l){
				throw new ServiceException("配送单号为"+entity.getDno()+"的货物还没有确收，请先确收！");
			}
			if(entity.getOutStatus()==1l){
				throw new ServiceException("配送单号为"+entity.getDno()+"的货物已经寄出！");
			}
			entity.setOutCompany(oprReceipt.getOutCompany());
			entity.setOutCost(outCost);
			entity.setOutMan(oprReceipt.getOutMan());
			entity.setOutNo(oprReceipt.getOutNo());
			entity.setOutStatus(oprReceipt.getOutStatus());
			entity.setOutTime(oprReceipt.getOutTime());
			entity.setOutWay(oprReceipt.getOutWay());

			entity.setCurStatus(oprReceipt.getCurStatus());
			this.oprHistoryService.saveLog(entity.getDno(), "回单寄出，寄出人"+entity.getOutMan()+",寄出单号"+entity.getOutNo(), log_saveOut);
			this.oprReceiptDao.save(entity);
		}
	}

	@ModuleName(value="回单查询语句获取",logType=LogType.buss)
	public StringBuffer getSqlRalaListService(Map<String,String> map)throws Exception {
		String dno =map.get("dno");
		String ids=map.get("ids");
 		String bussDepart = map.get("bussDepart");
 		String printNum = map.get("printNum");
 		String searchgoodsStatusValue = map.get("searchgoodsStatusValue");
 		
 		String[] sts = new String[]{"printNum","bussDepart","dno","ids","searchgoodsStatusValue"};
		StringBuffer sb = new StringBuffer();
		//FIXED 不利于阅读
		sb.append("SELECT  r.ID  AS id ,f.URGENT_SERVICE as urgentStatus, r.D_NO  AS dno , r.PRINT_NO  AS printNo ,")
				.append("r.PRINT_NUM  AS printNum , r.RECEIPT_TYPE  AS receiptType ," )
				.append("r.REACH_STATUS  AS reachStatus , r.REACH_NUM  AS reachNum ," )
				.append("r.REACH_MAN  AS reachMan ,to_char( r.REACH_TIME,'yyyy-mm-dd hh24:mi:ss')  AS reachTime ," )
				.append("r.GET_STATUS  AS getStatus , get_num as getNum,r.GET_MAN  AS getMan ,")
				.append("to_char( r.GET_TIME,'yyyy-mm-dd hh24:mi:ss') " )
				.append("AS getTime , r.CONFIRM_STATUS  AS confirmStatus , r.CONFIRM_NUM " )
				.append("AS confirmNum , r.CONFIRM_MAN  AS confirmMan , " )
				.append(" to_char( r.CONFIRM_TIME,'yyyy-mm-dd hh24:mi:ss')  AS " )
				.append("confirmTime , r.CONFIRM_REMARK  AS confirmRemark , r.OUT_STATUS " )
				.append("AS outStatus , r.OUT_WAY  AS outWay , r.OUT_NO  AS outNo ," )
				.append("OUT_STOCK_NUM as outStockNum,OUT_STOCK_MAN AS OUTSTOCKMAN , ")
				.append("to_char( r.OUT_STOCK_TIME,'yyyy-mm-dd hh24:mi:ss')AS OUTSTOCKTIME,OUT_STOCK_STATUS AS OUTSTOCKSTATUS,")
				.append("r.OUT_COMPANY  AS outCompany , r.OUT_COST  AS outCost ," )
				.append("r.scan_stauts  AS scanStatus , r.SCAN_MAN  AS scanMan ," )
				.append(" to_char(r.SCAN_TIME,'yyyy-mm-dd hh24:mi:ss')  AS scanTime , r.SCAN_ADDR  AS scanAddr ," )
				.append("r.CREATE_NAME  AS createName , to_char(r.CREATE_TIME,'yyyy-mm-dd hh24:mi')  AS createTime , ")
				.append("r.UPDATE_NAME  AS updateName , to_char(r.UPDATE_TIME,'yyyy-mm-dd hh24:mi')  AS updateTime ," )
				.append("r.TS  AS ts , r.CUR_STATUS  AS curStatus , r.OUT_MAN  AS outMan ," )
				.append(" to_char(r.OUT_TIME ,'yyyy-mm-dd hh24:mi') AS outTime , f.CP_NAME  AS cpName ," )
				.append("f.DISTRIBUTION_MODE  AS distributionMode , f.REAL_GO_WHERE  AS realGoWhere ,f.gowhere," )
				.append("p.OPR_PREWIRED_ID  AS prewiredId , o.overmemo  AS overmemoId ," )
				.append("f.GOODS_STATUS AS goodsStatus,s.sign_man as signMan,r.SCAN_NUM,")
				.append("f.consignee,f.addr,f.consignee_tel consigneeTel,f.piece,f.traffic_mode,")
				.append("f.Flight_Main_No,f.take_mode,f.sub_no subNO,f.SONDERZUG,f.flight_no")
				.append(" FROM  aisuser.OPR_RECEIPT r , aisuser.OPR_FAX_IN f ," )
				.append("(select tp.d_no,max(tp.OPR_PREWIRED_ID) OPR_PREWIRED_ID from aisuser.OPR_PREWIRED_DETAIL tp group by tp.d_no) p ,")
				.append("(select tt.d_no,max(tt.overmemo) overmemo from aisuser.OPR_OVERMEMO_DETAIL tt group by tt.d_no ) o,")
				.append(" opr_overmemo om,opr_sign s,opr_status t " )
				.append(" WHERE r.D_NO  =  f.D_NO   AND   r.D_NO  =  p.D_NO(+)   AND   r.D_NO =  o.D_NO(+) and r.d_no=s.d_no(+)" );
		sb.append(" and r.d_no=t.d_no and o.overmemo=om.id(+)");
		sb.append(" and f.status=1 ");//未传真作废
		if(null!=bussDepart && !"".equals(bussDepart)){
			sb.append(" and (f.in_depart_id =:bussDepart or f.end_depart_id=:bussDepart or f.distribution_depart_id=:bussDepart)");
		}
		if(!"".equals(ids) && ids!=null){
			if(ids.endsWith(",")){
				ids = ids.substring(0,ids.length()-1);
			}
			sb.append(" AND   r.ID  not in  ("+ids+") ");
		}
		if(null!=dno && !"".equals(dno)){
			sb.append(" and f.d_no=:dno");
		}else{
			if(null!=printNum && !"".equals(printNum)){
				if(Long.valueOf(printNum)==0l){
					sb.append(" AND  (r.PRINT_NUM =0 or r.PRINT_NUM is null)");
				}else {
					sb.append(" AND  r.PRINT_NUM  >=:printNum");
				}
			}
			if(null!=searchgoodsStatusValue && !"".equals(searchgoodsStatusValue.trim())){
				sb.append(" and ").append(searchgoodsStatusValue);
			}
			sb.append(this.appendConditions.appendConditions(map, sts));//添加查询条件
			sb.append(this.appendConditions.appendCountDate(map));//添加日期条件
		}
		sb.append(" order by r.print_num asc,f.sonderzug desc,f.urgent_service desc,f.d_no desc");
		return sb;
	}

	//获取图片路径
	public List<String> getImageUrlList(Long dno,String url) throws Exception {
		if(dno==null||url==null){
			throw new ServiceException("方法参数不能为空！");
		}
		List<String> list = new ArrayList<String>();
		List<OprReceipt> oprtList = findBy("dno", dno);
		String[] strs=null;
		OprReceipt oprReceipt=null;
		if(oprtList.size()==0){
			return list;
		}else{
			oprReceipt=oprtList.get(0);
		}
		if(oprReceipt!=null&&oprReceipt.getScanAddr()!=null){
			strs=oprReceipt.getScanAddr().split("&%&");         //去掉连接符，分开为多个图片地址
		}else{
			return list;
		}
		
		for(String string:strs){
			StringBuffer sb=new StringBuffer(url);
			sb.append(string.substring(string.lastIndexOf(".")-8,string.lastIndexOf("."))).append("/").append(string).append("?id=XBWL,GETPICTURE,@)!)");  //访问图片内径，后面拼接密码
			list.add(sb.toString());
		}
		return list;
	}

	public void cancelConfirmService(String[] split) throws Exception {
		for (int i = 0; i < split.length; i++) {
			OprReceipt entity = this.oprReceiptDao.get(Long.valueOf(split[i]));
			if(entity.getConfirmStatus()!=1l){
				throw new ServiceException(entity.getDno()+"的回单还没有确收！");
			}
			if(entity.getOutStatus()==1l){
				throw new ServiceException(entity.getDno()+"的回单已经寄出，请先撤销寄出！");
			}
			entity.setConfirmMan("");
			entity.setConfirmRemark("");
			entity.setConfirmNum(0l);
			entity.setConfirmTime(null);
			entity.setConfirmStatus(0l);
			entity.setCurStatus("已出库");
			this.oprHistoryService.saveLog(entity.getDno(), "回单撤销确收。", log_cancelConfirm);
			
			this.oprReceiptDao.save(entity);
		}
	}

	public void cancelOutService(String[] split) throws Exception {
		for (int i = 0; i < split.length; i++) {
			OprReceipt entity = this.oprReceiptDao.get(Long.valueOf(split[i]));

			entity.setOutCompany("");
			entity.setOutCost(0d);
			entity.setOutMan("");
			entity.setOutNo("");
			entity.setOutStatus(0l);
			entity.setOutTime(null);
			entity.setOutWay("");

			entity.setCurStatus("已确收");
			this.oprHistoryService.saveLog(entity.getDno(), "回单撤销寄出", log_cancelOut);
			this.oprReceiptDao.save(entity);
		}
	}

	public void delImageUrl(Long dno, String numI) throws Exception {
		Assert.notNull(dno, "配送单号不能为空");
		Assert.notNull(numI, "图片ID不能为空");
		
		 int i=Integer.parseInt(numI.substring(numI.lastIndexOf("_")+1));
		OprReceipt oprReceipt = oprReceiptDao.findUniqueBy("dno",dno);
		if(oprReceipt.getScanNum()==0){
			throw new ServiceException("无图片可以删除");
		}
		String[] dataStr = oprReceipt.getScanAddr().split("&%&");
		ArrayList<String>   al   =   new   ArrayList<String>(Arrays.asList(dataStr)); 
		al.remove(i);
		String newAddr="";
		for(String s:al){
			newAddr+=s+"&%&";
		}
		oprReceipt.setScanNum(oprReceipt.getScanNum()-1);
		oprReceipt.setScanAddr(newAddr);
		oprReceiptDao.save(oprReceipt);
	}
}

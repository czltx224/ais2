package com.xbwl.oper.reports.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.log.anno.ModuleName;
import com.xbwl.common.orm.IBaseDAO;
import com.xbwl.common.service.impl.BaseServiceImpl;
import com.xbwl.common.utils.LogType;
import com.xbwl.entity.OprLoadingbrigadeWeight;
import com.xbwl.oper.reports.service.IInuploadGoodsService;
import com.xbwl.oper.reports.util.AppendConditions;
import com.xbwl.oper.stock.dao.IOprLoadingbrigadeWeightDao;

/**װж�����ͷֲ����������ʵ����
 *@author LiuHao
 *@time Sep 19, 2011 3:31:59 PM
 */
@Service("inuploadGoodsServiceImpl")
@Transactional(rollbackFor = { Exception.class })
public class InuploadGoodsServiceImpl extends BaseServiceImpl<OprLoadingbrigadeWeight,Long> implements
		IInuploadGoodsService {
	@Resource(name="oprLoadingbrigadeWeightHibernateDaoImpl")
	private IOprLoadingbrigadeWeightDao oprLoadingbrigadeWeightDao;
	
	@Resource(name="appendConditions")
	private AppendConditions appendConditions;
	
	@Override
	public IBaseDAO getBaseDao() {
		return oprLoadingbrigadeWeightDao;
	}
	public String findInuploadGoods(Map<String, String> map) throws Exception {
		StringBuffer sb = new StringBuffer();
		String[] sts = new String[]{"cusId","countDate","departId","sendMans"};
		
		sb.append("select * from(");
		
		sb.append(findInupload(map));
		//REVIEW ȥ��1=1���� -- ���û�����������Ҳ��ܼ�where
		sb.append(" ) where 1=1"); 
		sb.append(this.appendConditions.appendConditions(map, sts));
		sb.append(" order by loading_name desc");
		
		return sb.toString();
	}
	/**װж����ͳ��
	 * @param map
	 * @return
	 * @throws Exception
	 */
	private String findInupload(Map<String, String> map)throws Exception{
		StringBuffer sb = new StringBuffer();
		String startCount =map.get("startCount");
		String endCount =map.get("endCount");
		String countRange =map.get("countRange");
		String cusId = map.get("cusId");
		String departId = map.get("departId");
		String sendMans = map.get("sendMans");
		
		int  count=1;
		int countNum=0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date1=new Date();
		Date date2=new Date();
		Calendar cal=Calendar.getInstance();
		String dateFormat="yyyy-MM-dd";
		
		if(countRange.equals("��")){
			dateFormat="yyyy-MM-dd";
			if(null==startCount || "".equals(startCount)){
				date1=sdf.parse(cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH))+"-"+cal.get(Calendar.DATE));
			}else{
				date1 = sdf.parse(startCount);	
			}
			if(null!=endCount || !"".equals(endCount)){
				date2 = sdf.parse(endCount);
			}
			long l=date2.getTime()-date1.getTime(); 
			
			count=this.appendConditions.panduan(date1, date2, countRange, 60);
		}else if(countRange.equals("��")){
			dateFormat="yyyy-MM";
			sdf = new SimpleDateFormat("yyyy-MM");
			if(null==startCount || "".equals(startCount)){
				date1=sdf.parse(cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)));
			}else{
				date1 = sdf.parse(startCount);	
			}
			if(null!=endCount || !"".equals(endCount)){
				date2 = sdf.parse(endCount);
			}
			count=this.appendConditions.panduan(date1, date2, countRange, 12);
		}else if(countRange.equals("��")){
			dateFormat="WW";
			try{
				count=Integer.parseInt(endCount)+1-Integer.parseInt(startCount);
				if(count<0){
					throw new ServiceException("���������쳣��");
				}
			}catch (Exception e) {
				throw new ServiceException("���������쳣��");
			}
			if(count>20){
				throw new ServiceException("����ͳ�Ʋ��ܳ���20�ܣ�");
			}
			
			countNum=Integer.parseInt(startCount);
		}else if(countRange.equals("��")){
			dateFormat="yyyy";
			count=Integer.parseInt(endCount)+1-Integer.parseInt(startCount);
			if(count>10){
				throw new ServiceException("����ͳ�Ʋ��ܳ���10���꣡");
			}else if(count<1){
				throw new ServiceException("��ݲ����ڸ�����");
			}
			countNum=Integer.parseInt(startCount);
		}
		
		sb.append("select bl.loading_name,")//0.ж����1.װ����2.�����3.�ͻ���4.�ӻ�
		  .append(" decode(t.loadingbrigade_type,0,'ж��',1,'װ��',2,'���',4,'�ӻ�','δ֪') loadingbrigade_type,");
		sb.append("sum(round(nvl(t.weight,0),2))  SUMCOL");
		
		if(countRange.equals("��")){
			cal.setTime(date1);
			for(int i=1;i<=count;i++){
				String yy = cal.get(Calendar.YEAR)+"";
				String dd = cal.get(Calendar.DATE)<10?"0"+cal.get(Calendar.DATE):cal.get(Calendar.DATE)+"";
				String mm = cal.get(Calendar.MONTH)+1<10?"0"+(cal.get(Calendar.MONTH)+1):(cal.get(Calendar.MONTH)+1)+"";
				sb.append(",sum(decode(to_date(to_char(t.create_time,'"+dateFormat+"'),'"+dateFormat+"'),")
				  .append("to_date('"+sdf.format(cal.getTime())+"','"+dateFormat+"'),round(nvl(t.weight,0),2),0)) ")
				  .append("\"").append(yy).append("-").append(mm).append("-").append(dd).append("\"");
				cal.set(Calendar.DATE, cal.get(Calendar.DATE)+1);
			}
		}else if(countRange.equals("��")){
			cal.setTime(date1);
			for(int i=1;i<=count;i++){
				String yy = cal.get(Calendar.YEAR)+"";
				String mm = cal.get(Calendar.MONTH)+1<10?"0"+(cal.get(Calendar.MONTH)+1):(cal.get(Calendar.MONTH)+1)+"";
				sb.append(",sum(decode(to_date(to_char(t.create_time,'"+dateFormat+"'),'"+dateFormat+"'),")
				  .append("to_date('"+sdf.format(cal.getTime())+"','"+dateFormat+"'),round(nvl(t.weight,0),2),0)) ")
				  .append("\"").append(yy).append("-").append(mm).append("\"");
				cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)+1);
			}
		}else if(countRange.equals("��") || countRange.equals("��")){
			countNum=Integer.parseInt(startCount);
			for(int i=1;i<=count;i++){
				sb.append(",sum(decode(trunc(to_char(t.create_time,'"+dateFormat+"')),")
				  .append(countNum+",round(nvl(t.weight,0),2),0)) ")
				  .append("\"").append(countNum).append(countRange).append("\"");
				countNum++;
			}
		}
		//�жϣ���������ͻ�Ա����������ӵ���ͽ��ӵ���ϸ��
		if(null!=sendMans && !"".equals(sendMans)){
			sb.append(" from opr_loadingbrigade_weight t,bas_loadingbrigade bl,opr_fax_in f,opr_overmemo o,opr_overmemo_detail d")
			  .append(" where t.loadingbrigade_id(+)=bl.id and t.d_no=f.d_no(+) and d.overmemo=o.id(+) and f.d_no=d.d_no(+)");
		}else{
			sb.append(" from opr_loadingbrigade_weight t,bas_loadingbrigade bl,opr_fax_in f")
			  .append(" where t.loadingbrigade_id(+)=bl.id and t.d_no=f.d_no(+)");
		}
		if(null!=cusId && !"".equals(cusId)){
			sb.append(" and f.cus_id=:cusId");
		}
		if(null!=departId && !"".equals(departId)){
			sb.append(" and t.DEPART_ID=:departId");
		}
		//�жϣ���������ͻ�Ա����ѽ��ӵ�������ն˲�����Ϊ��ѯ����
		if(null!=sendMans && !"".equals(sendMans)){
			String[] stssendMans = sendMans.split(",");
			sb.append(" and (");
			for (int i = 0; i < stssendMans.length; i++) {
				sb.append("o.end_depart_name ='").append(stssendMans[i]).append("'");
				if(i!=stssendMans.length-1){
					sb.append(" or ");
				}
			}
			sb.append(")");
		}
		sb.append(this.appendConditions.appendCountDate(map));
		sb.append(" group by t.loadingbrigade_type,bl.loading_name ");
        //   .append(" to_date(to_char(t.create_time,'yyyy-MM'),'yyyy-MM'),to_date(to_char(t.create_time,'yyyy-MM-dd'),'yyyy-MM-dd') ");
		//sb.append(" order by bl.loading_name desc");
		
		return sb.toString();
	}
	/**װж������Сʱͳ��
	 * @param map
	 * @return
	 * @throws Exception
	 */
	private String findInuploadByHour(Map<String, String> map)throws Exception{
		StringBuffer sb = new StringBuffer();
		String startCount =map.get("startCount");
		String cusId = map.get("cusId");

		sb.append("select bl.loading_name,t.loadingbrigade_type,sum(decode(to_date(to_char(t.create_time,'yyyy-MM-dd'),'yyyy-MM-dd'),to_date('"+startCount+"','yyyy-MM-dd'),round(nvl(t.weight,0),2),0)) sumgoods, ");
		for(int i=0;i<=23;i++){
			sb.append("sum(decode(to_date(to_char(t.create_time,'yyyy-MM-dd hh24'),'yyyy-MM-dd hh24'),to_date('"+startCount+" "+i+"','yyyy-MM-dd hh24'),round(nvl(t.weight,0),2),0)) col"+i);
			if(i!=23){
				sb.append(",");
			}
		}
		sb.append(" from opr_loadingbrigade_weight t,bas_loadingbrigade bl where t.loadingbrigade_id=bl.id ");
		if(null!=cusId && !"".equals(cusId)){
			sb.append(" and f.cus_id=:cusId");
		}
		
		sb.append("group by t.loadingbrigade_type,bl.loading_name order by bl.loading_name desc");
		
		return sb.toString();
	}
	
	@ModuleName(value="�ֲ�������ѯSQL��ȡ",logType=LogType.buss)
	public String getSeparateDialReportSQLService(Map<String, String> map)
			throws Exception {
		StringBuffer sb = new StringBuffer();
		String[] sts = new String[]{"cpName","countDate","departId"};
		
		sb.append("select *from (");
		
		sb.append(this.findSeparateDial(map));//SQLƴ��
		sb.append("  )group by  dispatch_id,fname");
	    sb.append(" ) ts,bas_loadingbrigade tb where ts.dispatch_id=tb.id) tt where 1=1");
	    
	    sb.append(this.appendConditions.appendConditions(map, sts));
	    sb.append(" order by dispatch_id");
	    System.out.println(sb);
		return sb.toString();
	}
	
	/**�ֲ�����ͳ�Ʋ�ѯƴ�Ӳ�ѯSQL
	 * @param sb ���������ַ��� czl
	 * @param strdate
	 * @param map
	 * @return
	 * @throws Exception
	 */
	private StringBuffer findSeparateDial(Map<String, String> map)throws Exception{
		StringBuffer sb = new StringBuffer();
		
		String cpName=map.get("cpName");
		String countRange =map.get("countRange");
		String startCount =map.get("startCount");
		String endCount =map.get("endCount");
		String departId = map.get("departId");
		
		int  count=1;
		int countNum=0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date1=new Date();
		Date date2=new Date();
		Calendar cal=Calendar.getInstance();
		String dateFormat="yyyy-MM-dd";
		
		if(countRange.equals("��")){
			dateFormat="yyyy-MM-dd";
			if(null==startCount || "".equals(startCount)){
				date1=sdf.parse(cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH))+"-"+cal.get(Calendar.DATE));
			}else{
				date1 = sdf.parse(startCount);	
			}
			if(null!=endCount || !"".equals(endCount)){
				date2 = sdf.parse(endCount);
			}
			long l=date2.getTime()-date1.getTime(); 
			
			count=this.appendConditions.panduan(date1, date2, countRange, 60);
		}else if(countRange.equals("��")){
			dateFormat="yyyy-MM";
			sdf = new SimpleDateFormat("yyyy-MM");
			if(null==startCount || "".equals(startCount)){
				date1=sdf.parse(cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)));
			}else{
				date1 = sdf.parse(startCount);	
			}
			if(null!=endCount || !"".equals(endCount)){
				date2 = sdf.parse(endCount);
			}
			count=this.appendConditions.panduan(date1, date2, countRange, 12);
		}else if(countRange.equals("��")){
			dateFormat="WW";
			try{
				count=Integer.parseInt(endCount)+1-Integer.parseInt(startCount);
				if(count<0){
					throw new ServiceException("���������쳣��");
				}
			}catch (Exception e) {
				throw new ServiceException("���������쳣��");
			}
			if(count>20){
				throw new ServiceException("����ͳ�Ʋ��ܳ���20�ܣ�");
			}
			
			countNum=Integer.parseInt(startCount);
		}else if(countRange.equals("��")){
			dateFormat="yyyy";
			count=Integer.parseInt(endCount)+1-Integer.parseInt(startCount);
			if(count>10){
				throw new ServiceException("����ͳ�Ʋ��ܳ���10���꣡");
			}else if(count<1){
				throw new ServiceException("��ݲ����ڸ�����");
			}
			countNum=Integer.parseInt(startCount);
		}

		sb.append("select tb.loading_name,ts.* from(");
		sb.append("select dispatch_id,fname ");
		sb.append(",sum(nvl(sumcol,0)) sumcol");

		if(countRange.equals("��")){
			cal.setTime(date1);
			for(int i=1;i<=count;i++){
				String yy = cal.get(Calendar.YEAR)+"";
				String dd = cal.get(Calendar.DATE)<10?"0"+cal.get(Calendar.DATE):cal.get(Calendar.DATE)+"";
				String mm = cal.get(Calendar.MONTH)+1<10?"0"+(cal.get(Calendar.MONTH)+1):(cal.get(Calendar.MONTH)+1)+"";
				sb.append(",sum(nvl(col"+i+",0)) \"").append(yy).append("-").append(mm).append("-").append(dd).append("\"");
				
				cal.set(Calendar.DATE, cal.get(Calendar.DATE)+1);
			}
		}else if(countRange.equals("��")){
			cal.setTime(date1);
			for(int i=1;i<=count;i++){
				String yy = cal.get(Calendar.YEAR)+"";
				String mm = cal.get(Calendar.MONTH)+1<10?"0"+(cal.get(Calendar.MONTH)+1):(cal.get(Calendar.MONTH)+1)+"";
				sb.append(",sum(nvl(col"+i+",0)) \"").append(yy).append("-").append(mm).append("\"");
				
				cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)+1);
			}
		}else if(countRange.equals("��") || countRange.equals("��")){
			countNum=Integer.parseInt(startCount);
			for(int i=1;i<=count;i++){
				sb.append(",sum(nvl(col"+i+",0)) \"").append(countNum+countRange).append("\"");
				countNum++;
			}
		}
		
		sb.append(" from (");
		sb.append("select t.dispatch_id,f.cp_name cpName,");
		sb.append("decode(t.loadingbrigade_type,0 ,'�������',4,'�������','��������') fname");
		
		sb.append(",round(sum(nvl(t.weight,0)),2) sumcol");
		
		if(countRange.equals("��")){
			cal.setTime(date1);
			for(int i=1;i<=count;i++){
				sb.append(",sum(decode(to_date(to_char(t.create_time,'"+dateFormat+"'),'"+dateFormat+"'),to_date('"+sdf.format(cal.getTime())+"','"+dateFormat+"'), round(nvl(t.weight,0),2))) col").append(i);
				cal.set(Calendar.DATE, cal.get(Calendar.DATE)+1);
			}
		}else if(countRange.equals("��")){
			cal.setTime(date1);
			for(int i=1;i<=count;i++){
				sb.append(",sum(decode(to_date(to_char(t.create_time,'"+dateFormat+"'),'"+dateFormat+"'),to_date('"+sdf.format(cal.getTime())+"','"+dateFormat+"'), round(nvl(t.weight,0),2))) col").append(i);
				cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)+1);
			}
		}else if(countRange.equals("��") || countRange.equals("��")){
			countNum=Integer.parseInt(startCount);
			for(int i=1;i<=count;i++){
				sb.append(",sum(decode(trunc(to_char(t.create_time,'"+dateFormat+"')),"+countNum+", round(nvl(t.weight,0),2),0)) col").append(i);
				countNum++;
			}
		}
		
		sb.append("  from opr_loadingbrigade_weight t ,opr_fax_in f where t.d_no = f.d_no ");
		if(null!=cpName && !"".equals(cpName)){
			sb.append(" and f.cp_name =:cpName");
		}
		if(null!=departId && !"".equals(departId)){
			sb.append(" and t.depart_id=:departId");
		}
		sb.append(this.appendConditions.appendCountDate(map));
	    sb .append(" group by t.dispatch_id,f.cp_name,decode(t.loadingbrigade_type,0 ,'�������',4,'�������','��������') ")
		   .append(" union all");
		
		
	    sb.append(" select t.dispatch_id,f.cp_name,")
	      .append(" decode(t.loadingbrigade_type,0 ,'���Ʊ��',4,'���Ʊ��','����Ʊ��')  fname");
	    sb.append(",count(*)  sumcol");
	    
	    if(countRange.equals("��")){
			cal.setTime(date1);
			for(int i=1;i<=count;i++){
				sb.append(",sum(decode(to_date(to_char(t.create_time,'"+dateFormat+"'),'"+dateFormat+"'),to_date('"+sdf.format(cal.getTime())+"','"+dateFormat+"'), 1,0)) col").append(i);
				cal.set(Calendar.DATE, cal.get(Calendar.DATE)+1);
			}
		}else if(countRange.equals("��")){
			cal.setTime(date1);
			for(int i=1;i<=count;i++){
				sb.append(",sum(decode(to_date(to_char(t.create_time,'"+dateFormat+"'),'"+dateFormat+"'),to_date('"+sdf.format(cal.getTime())+"','"+dateFormat+"'),1,0)) col").append(i);
				cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)+1);
			}
		}else if(countRange.equals("��") || countRange.equals("��")){
			countNum=Integer.parseInt(startCount);
			for(int i=1;i<=count;i++){
				sb.append(",sum(decode(trunc(to_char(t.create_time,'"+dateFormat+"')),"+countNum+",1,0)) col").append(i);
				countNum++;
			}
		}
	    sb.append(" from opr_loadingbrigade_weight t,opr_fax_in f where t.d_no = f.d_no ");
	    if(null!=cpName && !"".equals(cpName)){
			sb.append(" and f.cp_name =:cpName");
		}
	    if(null!=departId && !"".equals(departId)){
			sb.append(" and t.depart_id=:departId");
		}
	    sb.append(this.appendConditions.appendCountDate(map));
	    sb.append(" group by t.dispatch_id,f.cp_name,decode(t.loadingbrigade_type,0 ,'���Ʊ��',4,'���Ʊ��','����Ʊ��')");
	    
	    return sb;
	}
	
}

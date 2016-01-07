package com.xbwl.oper.reports.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xbwl.common.exception.ServiceException;

@Repository("appendConditions")
public class AppendConditions {

	/**日期拼接查询
	 * @param map
	 * @return 拼接后的字符串
	 */
	public String appendCountDate(Map<String, String> map)throws Exception{
		StringBuffer sb = new StringBuffer();
		String startCount =map.get("startCount");
		String endCount =map.get("endCount");
		String countRange =map.get("countRange");
		String countCheckItems = map.get("countCheckItems");
		
		int year = Calendar.getInstance().get(Calendar.YEAR);
		if(null!=countCheckItems && !"".equals(countCheckItems)){
			countCheckItems=StringToOracleParam(countCheckItems);
			if(null==countRange || "".equals(countRange) || countRange.equals("日")){
				if(null!=startCount && !"".equals(startCount) && isDateType(startCount)){
					sb.append(" and ").append(countCheckItems) .append(" >=to_date(:startCount,'yyyy-MM-dd')");
				}
				if(null!=endCount && !"".equals(endCount) && isDateType(endCount)){
				   sb.append(" and ").append(countCheckItems) .append(" <=to_date(:endCount,'yyyy-MM-dd')+1-0.0001");
				}
			}else if(countRange.equals("周")){
				if(null!=startCount && !"".equals(startCount)){
					sb.append(" and trunc(to_char(").append(countCheckItems) .append(" ,'WW')) >=:startCount");
				}
				if(null!=endCount && !"".equals(endCount)){
				   sb.append(" and trunc(to_char(").append(countCheckItems) .append(" ,'WW')) <=:endCount");
				}
				sb.append(" and to_char(").append(countCheckItems) .append(" ,'yyyy')=").append(year);
			}else if(countRange.equals("月")){
				if(null!=startCount && !"".equals(startCount)){
					sb.append(" and to_char(").append(countCheckItems) .append(" ,'yyyy-MM') >=:startCount");
				}
				if(null!=endCount && !"".equals(endCount)){
			       sb.append(" and to_char(").append(countCheckItems) .append(" ,'yyyy-MM') <=:endCount");
				}
			}else if(countRange.equals("年")){
				if(null!=startCount && !"".equals(startCount)){
					sb.append(" and to_char(").append(countCheckItems) .append(" ,'yyyy') >=:startCount");
				}
				if(null!=endCount && !"".equals(endCount)){
			      sb.append(" and to_char(").append(countCheckItems) .append(",'yyyy') <=:endCount");
				}
			}else{
				throw new ServiceException("统计方式错误！");
			}
		}
		return sb.toString();
	}
	
	/**拼接条件 map中的所有属性是查询的字段，如果有重复的字段则在前面加一个别名，别名只能是一个字符
	 * 可支持以模糊查询
	 * @param map 拼接条件集合
	 * @param sts 需要排除的删选字段，有则写，可以为空
	 * @return 拼接后的字符串
	 */
	public String appendConditions(Map<String, String> map,String[] sts) throws Exception{
		StringBuffer sb = new StringBuffer();
		Iterator< String> keyItr = map.keySet().iterator();
		String value = "";
		String key = "";
		String itemString ="";
	    String itemBefore ="";
		String startHour = null;
		String endHour = null;
		
		//将字符串数组转为HashSet集合
		HashSet<String> set = new HashSet<String>();
		//默认过滤项
		set.add("startCount");
		set.add("endCount");
		set.add("countRange");
		set.add("countCheckItems");
		set.add("startHour");
		set.add("endHour");
		set.add("myCheckItemsValue");
		set.add("itemsValue");
		
		if(sts!=null){
			for (int i = 0; i < sts.length; i++) {
				set.add(sts[i]);
			}
		}
		
		while(keyItr.hasNext()){
			key = keyItr.next();
			//去除不需要过滤的字段
			if(set.contains(key) || key.indexOf("temsValue")!=-1){
				continue;
			}
			
			value=map.get(key)==null?"":map.get(key).trim(); 
			itemString = key.substring(key.indexOf("_")+1);
			if(key.indexOf("_")>0){
				itemBefore = key.substring(0,key.indexOf("_"));
			}else{
				itemBefore="";
			}
			 if(null!=value && value.length()>0){
				if(key.equals("checkHour")){
					startHour = map.get("startHour");
					endHour = map.get("endHour");
					if(null!=startHour && !"".equals(startHour)){
						sb.append(" and ").append(StringToOracleParam(value)).append(">= :startHour");
					}
					if(null!=endHour && !"".equals(endHour)){
						sb.append(" and ").append(StringToOracleParam(value)).append("<= :endHour");
					}
				}else if(key.indexOf("heckItems")!=-1){
					String keyTwo = key.replace("checkItems", "itemsValue");
				    keyTwo = keyTwo.replace("CheckItems", "ItemsValue");
					String v = map.get(keyTwo);
					if(null!=v && !"".equals(v)){
						sb.append(getAppendSql(value,keyTwo));
					}
				}else{
					sb.append(getAppendSql(key,key));
				}
			}
		}
		return sb.toString();
	}
	
	/**拼接单个条件
	 * @param item
	 * @param value
	 * @return
	 */
	private String getAppendSql(String item,String value){
		 StringBuffer sb = new StringBuffer();
		 String itemString = "";
		 String itemBefore = "";
		 if(item.indexOf("_")>0){
			 itemString =item.substring(item.indexOf("_")+1);
			 itemBefore =item.substring(0,item.indexOf("_"));
		 }else{
			 itemString=item;
		 }
		
	     if(null!=itemBefore && itemBefore.equals("LIKES")){
	    	 if(itemString.indexOf("_")>0){
				 itemBefore = itemString.substring(0,itemString.indexOf("_"));
				 itemString = itemString.substring(itemString.indexOf("_")+1);
			 }
	    	 if(null!=itemBefore && itemBefore.length()==1){
		    	 sb.append(" and ").append(itemBefore).append(".").append(StringToOracleParam(itemString)).append(" like '%'||trim(:").append(value).append(") || '%'");
		     }else{
		    	 sb.append(" and ").append(StringToOracleParam(itemString)).append(" like '%'||trim(:").append(value).append(") || '%'");
		     }
	    	 //sb.append(" and ").append(StringToOracleParam(itemString)).append(" like || '%':").append(value).append(" || '%'");
	     }else if(null!=itemBefore && itemBefore.equals("OR")){
	    	 if(itemString.indexOf("_")>0){
				 itemBefore = itemString.substring(0,itemString.indexOf("_"));
				 itemString = itemString.substring(itemString.indexOf("_")+1);
			 }
	    	 if(null!=itemBefore && itemBefore.length()==1){
		    	 sb.append(" and (").append(itemBefore).append(".").append(StringToOracleParam(itemString.substring(0,itemString.indexOf("$")))).append(" = :").append(value);
		    	 sb.append(" or ").append(itemBefore).append(".").append(StringToOracleParam(itemString.substring(itemString.indexOf("$")+1))).append(" = :").append(value).append(")");
	    	 }else{
	    		 sb.append(" and (").append(StringToOracleParam(itemString.substring(0,itemString.indexOf("$")))).append(" = :").append(value);
		    	 sb.append(" or ").append(StringToOracleParam(itemString.substring(itemString.indexOf("$")+1))).append(" = :").append(value).append(")"); 
	    	 }
	     }else if(null!=itemBefore && itemBefore.length()==1){
	    	 sb.append(" and ").append(itemBefore).append(".").append(StringToOracleParam(itemString)).append(" = :").append(value);
	     }else{
	    	 sb.append(" and ").append(StringToOracleParam(itemString)).append(" = :").append(value);
	     }
		 
		 return sb.toString();
	}
	
	/**字符串转化为数据库中的字段
	 * 也就是把大写转化为 '_'+小写
	 * @param str
	 * @return
	 */
	public static String StringToOracleParam(String str){
		String newStr = "";
		for (int i = 0; i < str.length(); i++) {
			Character ch = str.charAt(i);
			if(ch.isUpperCase(ch)){
				newStr+="_"+ch;
			}else{
				newStr+=ch;
			}
		}
		return newStr;
	}
	
	/**获取两个时间之间的月份
	 * @param start 开始时间
	 * @param end 结束时间
	 * @return
	 */
	public static int getMonth(Date start, Date end)throws Exception {
		if (start.after(end)) {
			Date t = start;
			start = end;
			end = t;
		}
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.setTime(start);
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTime(end);
		Calendar temp = Calendar.getInstance();
		temp.setTime(end);
		temp.add(Calendar.DATE, 1);

		int year = endCalendar.get(Calendar.YEAR)
				- startCalendar.get(Calendar.YEAR);
		int month = endCalendar.get(Calendar.MONTH)
				- startCalendar.get(Calendar.MONTH);

		if ((startCalendar.get(Calendar.DATE) == 1)
				&& (temp.get(Calendar.DATE) == 1)) {
			return year * 12 + month + 1;
		} else if ((startCalendar.get(Calendar.DATE) != 1)
				&& (temp.get(Calendar.DATE) == 1)) {
			return year * 12 + month;
		} else if ((startCalendar.get(Calendar.DATE) == 1)
				&& (temp.get(Calendar.DATE) != 1)) {
			return year * 12 + month;
		} else {
			return (year * 12 + month - 1) < 0 ? 0 : (year * 12 + month);
		}
	}
	
	/**判断时间范围
	 * @param startDate
	 * @param endDate
	 * @param countRange
	 * @param num
	 * @return
	 * @throws Exception
	 */
	public int panduan(Date startDate,Date endDate,String countRange,int num)throws Exception{
		int count =0;
		if(countRange.equals("日")){
			long l=endDate.getTime()-startDate.getTime(); 
			count=((int)(l/(24*60*60*1000))+1);
			if(count>num){
				throw new ServiceException("按天统计不能超过"+num+"天！");
			}
		}else if (countRange.equals("周")){
			
		}else if (countRange.equals("月")){
			count=getMonth(startDate, endDate)+1;
			if(count>12){
				throw new ServiceException("按月统计不能超过"+num+"个月！");
			}
			
		}else if (countRange.equals("年")){
		}
		
		return count;
	}
	
	/**保留小数点位数转换
	 * @param number
	 * @param num
	 * @return
	 */
	public static Double formatDouble(Double number,int num){
		NumberFormat  formater  =  DecimalFormat.getInstance();  
	    formater.setMaximumFractionDigits(num);
		return Double.parseDouble(formater.format(number));
	}
	
	public static boolean isDateType(String dateString){
		boolean flag = false;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try{
			sdf.parse(dateString);
			flag =true;
		}catch (Exception e) {
			System.out.println("日期格式错误！");
			flag = false;
		}
		return flag;
	}
	
}

package mytest;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.BeanUtils;

import com.xbwl.common.utils.MD5Utils;
import com.xbwl.entity.BasCqStCorporateRate;
import com.xbwl.entity.CtTmD;

import dto.CtTmdDto;

public class Test {

	public static void main(String[] args)throws Exception {
		// fieldTest();
		 zhengze();
	}
	public static void getIpAddr()throws Exception{
		InetAddress addr = InetAddress.getLocalHost();
		String ip=addr.getHostAddress();//获得本机IP
		System.out.println(ip);
	}
	public static void zhengze()throws Exception{
		
		String context ="M18088曹哥";
		Long dno = Long.valueOf(context.replaceAll("[^\\d]*(\\d)[^\\d]*", "$1"));
		//去吃数字，获取发送人
		String sendMan = context.replaceAll("[^\\D]*(\\D)[^\\D]*", "$1");
		
		System.out.println(dno);
		System.out.println(sendMan);
	}
	
	public static void md5Test()throws Exception{
		
		String md5 = MD5Utils.strToMd5("123456");
		System.out.println(md5);
	}
	
	/**2012-04-19
	 * @throws Exception
	 */
	public static void fieldTest()throws Exception{
		BasCqStCorporateRate test = new BasCqStCorporateRate();
		test.setDistributionMode("xinbang");
		
		Class cls = test.getClass();
		Field[] fields = cls.getDeclaredFields();
		Method[] methods = cls.getDeclaredMethods();
		for (int i = 0; i < fields.length; i++) {
			System.out.println(fields[i].getName()+"   =="+fields[i].getType());
		}
		for (int i = 0; i < methods.length; i++) {
			//System.out.println(methods[i].getName()+"   =="+methods[i].getReturnType());
			if(methods[i].getName().indexOf("get")!=-1)
			System.out.println(methods[i].invoke(cls.newInstance()));
		}
	}
	
	public static void test0608(){
		//System.out.println("1曹智礼asf1dskf 2 曹智礼df(3)df456".replaceAll("[^\\d]*(\\d)[^\\d]*", "$1"));
		
		//String regex="\\u007B.*\\u007D";
		//String str="132更新至456";
		//System.out.println(str.replaceAll(regex,"$1"));
		
		String find = "(?<=\\u007B).*?(?=\\u007D)";//"\\u007B.*?\\u007D";//"\\u007B((?>[^\\u007D]+))\\u007D";
		Pattern p = Pattern.compile(find);
		Matcher matcher = p.matcher("132更新至456");
		while(matcher.find()) {
			System.out.println("==:"+matcher.group());
		}
		System.out.println("1曹智礼asf1dskf 2 曹智礼df(3)df456".replaceAll("[^\\D]*(\\D)[^\\D]*", "$1"));
	}
}

package mytest;

import java.util.HashMap;
import java.util.Map;


/**
 * 获取系统信息的工具类
 * @author HuangJian
 * @since 2010-05-04
 */
public class SystemRuntimeStatus {
	private static Map<String,String> resultMap = new HashMap<String,String>();
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// 可使用内存
		long totalMemory = Runtime.getRuntime().totalMemory();
		// 剩余内存
		long freeMemory = Runtime.getRuntime().freeMemory();
		// 最大可使用内存
		long maxMemory = Runtime.getRuntime().maxMemory();
		System.out.println("totalMemory is:"+totalMemory/1024/1024);
		System.out.println("freeMemory is:"+freeMemory/1024/1024);
		System.out.println("maxMemory is:"+maxMemory/1024/1024);
	}
	/**
	 * 可使用内存。单位为mb
	 * @return
	 */
	public static String getTotalMemory(){
		return Runtime.getRuntime().totalMemory()/1024/1024+"";
	}
	/**
	 * 剩余内存。单位为mb
	 * @return
	 */
	public static String getFreeMemory(){
		return Runtime.getRuntime().freeMemory()/1024/1024+"";
	}
	/**
	 * 最大可使用内存。单位为mb
	 * @return
	 */
	public static String getMaxMemory(){
		return Runtime.getRuntime().maxMemory()/1024/1024+"";
	}
	public static Map<String,String> getSystemMesg(){
		resultMap.put("totalMemory", getTotalMemory());
		resultMap.put("freeMemory", getFreeMemory());
		resultMap.put("maxMemory", getMaxMemory());
		return resultMap;
	}
}

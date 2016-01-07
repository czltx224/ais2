package mytest;

import java.util.HashMap;
import java.util.Map;


/**
 * ��ȡϵͳ��Ϣ�Ĺ�����
 * @author HuangJian
 * @since 2010-05-04
 */
public class SystemRuntimeStatus {
	private static Map<String,String> resultMap = new HashMap<String,String>();
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// ��ʹ���ڴ�
		long totalMemory = Runtime.getRuntime().totalMemory();
		// ʣ���ڴ�
		long freeMemory = Runtime.getRuntime().freeMemory();
		// ����ʹ���ڴ�
		long maxMemory = Runtime.getRuntime().maxMemory();
		System.out.println("totalMemory is:"+totalMemory/1024/1024);
		System.out.println("freeMemory is:"+freeMemory/1024/1024);
		System.out.println("maxMemory is:"+maxMemory/1024/1024);
	}
	/**
	 * ��ʹ���ڴ档��λΪmb
	 * @return
	 */
	public static String getTotalMemory(){
		return Runtime.getRuntime().totalMemory()/1024/1024+"";
	}
	/**
	 * ʣ���ڴ档��λΪmb
	 * @return
	 */
	public static String getFreeMemory(){
		return Runtime.getRuntime().freeMemory()/1024/1024+"";
	}
	/**
	 * ����ʹ���ڴ档��λΪmb
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

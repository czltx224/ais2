package com.xbwl.message.test;

import java.net.MalformedURLException;

import com.caucho.hessian.client.HessianProxyFactory;
import com.xbwl.ws.client.IWSSmsSendsmsService;

import dto.SmsSendsmsDto;

/**
 *@author LiuHao
 *@time Dec 15, 2012 6:21:01 PM
 */
public class TestMsg {
	public static void main(String[] args) {
		String url="http://121.9.243.134:9001/message/hessian/springSmsSendsmsRemot";
		HessianProxyFactory factory = new HessianProxyFactory();
		//factory.setDebug(true);
		try {
			
			IWSSmsSendsmsService ws= (IWSSmsSendsmsService)factory.create(IWSSmsSendsmsService.class, url);
			SmsSendsmsDto smsDto = new SmsSendsmsDto();
			smsDto.setUid("95d753514094061f2468316903c1ec19");
			smsDto.setClientIp("192.168.8.51");
			smsDto.setTel("18620726956");
			smsDto.setContext("测试");
			smsDto.setSendDepart("新邦物流");
			smsDto.setSmstype("ewt手机验证");
			smsDto.setSendName("刘浩");
			smsDto.setReceiver("用户");//接收对象
			smsDto.setBillno("");
			smsDto.setSystemName("ais");
			smsDto.setSysNo(2l);//手动设置
			ws.saveMsg(smsDto);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

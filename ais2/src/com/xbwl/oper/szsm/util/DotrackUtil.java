package com.xbwl.oper.szsm.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

// Referenced classes of package cn.xbps.utils:
//			InitUtil

@Component("dotrackUtil")
public class DotrackUtil {

	private  Logger logger = Logger.getLogger(DotrackUtil.class);
	
	@Value("${szsm_url}")
	private  String url;
	
	@Value("${szsm_ty_conn_timeout}")
	private String TY_CONN_TIMEOUT;
	
	@Value("${szsm_ty_read_timeout}")
	private String TY_READ_TIMEOUT;


	public DotrackUtil() {
	}

	public  boolean toWrite(String xml) throws Exception {
		HttpURLConnection huc = null;
		InputStream is = null;
		URL serverURL;
		try {
			serverURL = new URL(url);
			huc = (HttpURLConnection) serverURL.openConnection();
			huc.setRequestMethod("POST");
			huc.setRequestProperty("Content-type", "text/xml");
			System.setProperty("sun.net.client.defaultConnectTimeout",
					TY_CONN_TIMEOUT);
			System.setProperty("sun.net.client.defaultReadTimeout",
					TY_READ_TIMEOUT);
			huc.setDoOutput(true);
			SAXReader reader = new SAXReader();
			huc.getOutputStream().write(xml.getBytes());
			is = huc.getInputStream();
			Document doc = reader.read(is);
			Element element = doc.getRootElement();
			if ("1".equals(element.element("returninfo").elementTextTrim("flag"))) {
				StringBuffer mesg = new StringBuffer();
				mesg.append(
						element.element("returninfo").elementTextTrim("dono"))
						.append("写入失败，原因为：").append(
								element.element("returninfo").elementTextTrim(
										"desc"));
				logger.warn(mesg);
				return false;
			}
			if (("0".equals(element.element("returninfo").elementTextTrim("flag")))) {
				return true;
			}
		}catch(java.net.ConnectException ne){
			throw ne;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (huc != null)
				huc.disconnect();
			if (is != null)
				try {
					is.close();
				} catch (IOException e) {
					logger.error("异常", e);
					e.printStackTrace();
					throw e;
				}
		}
		if (huc != null) {
			huc.disconnect();
		}
		if (is != null)
			try {
				is.close();
			} catch (IOException e) {
				logger.error("异常", e);
				e.printStackTrace();
				throw e;
			}

		return false;
	}
}
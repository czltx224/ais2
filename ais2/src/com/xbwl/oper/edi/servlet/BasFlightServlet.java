package com.xbwl.oper.edi.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.xbwl.common.utils.SpringContextHolder;
import com.xbwl.entity.BasFlight;
import com.xbwl.sys.service.IBasFlightService;

public class BasFlightServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// System.out.println("doGet........");
		String flightNo=null;
		if(req.getParameter("q")!=null){
			try {
				flightNo=java.net.URLDecoder.decode(req.getParameter("q"),"UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		IBasFlightService basFlightService = (IBasFlightService)SpringContextHolder.getBean("basFilghtServiceImpl");
		List<BasFlight> list = null;
		try {
			list = basFlightService.findFlightNo(flightNo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.setContentType("text/html;charset=utf-8");
	    resp.setCharacterEncoding("utf-8");
		PrintWriter printWriter = resp.getWriter();
	    // printWriter.println("<meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\">");
	    List jsonList = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			JSONObject obj = new JSONObject();
			obj.element("SHF_NO", list.get(i).getFlightNumber());
			obj.element("FROM_CITY", list.get(i).getStartCity());
			//printWriter.println(obj);
			jsonList.add(obj);
		}
		printWriter.println(jsonList);
		//printWriter.
		printWriter.flush();
	    printWriter.close();
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doGet(req, resp);
		//super.doPost(req, resp);
		// System.out.println("doPost........");
	}

	@Override
	public void destroy() {
		//System.out.println("BasFlightServlet destroy...............");
		super.destroy();
	}

	@Override
	public void init() throws ServletException {
		//System.out.println("BasFlightServlet init...............");
		super.init();
	}
}

package com.xbwl.common.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Administrator
 * @createTime 5:22:59 PM
 * @updateName Administrator
 * @updateTime 5:22:59 PM
 * 
 */
//REVIEW 这个类是否还有效，是否需要删除
public class JnlpServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public JnlpServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// TODO Auto-generated method stub
        response.setContentType("application/x-java-jnlp-file"); 
        request.setCharacterEncoding("utf-8"); 
        response.setCharacterEncoding("utf-8");
        PrintWriter printWriter = response.getWriter();
       

        printWriter.write("<?xml version='1.0' encoding='utf-8'?>");
        printWriter.write("<jnlp spec='1.0+' codebase='"+request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+""+request.getContextPath()+"'  >");
        printWriter.write("<information>");
        //printWriter.write("<update check='background'/>");
        printWriter.write("<title>空港配送管理系统</title>");
        printWriter.write("<vendor>AIS研发部</vendor>");
        printWriter.write("<description>新邦物流配送管理系统 测试版本</description>");
        printWriter.write("<icon href='webstart/d_icon.gif'/>");
        printWriter.write("<icon kind='splash' href='webstart/login.gif'/> ");
        printWriter.write("<offline-allowed/>");
        printWriter.write("<shortcut online='true'> ");
        printWriter.write("<desktop/> ");
        printWriter.write("</shortcut>");
        printWriter.write("</information>");
        printWriter.write("<security>");
        printWriter.write(" <all-permissions/>");
        printWriter.write("</security>");
        printWriter.write("<resources>");
        printWriter.write("<j2se version=\"1.6+\"  href ='http://java.sun.com/products/autodl/j2se' /> ");
        printWriter.write("<jar href='webstart/jar/aisLogin_fat.jar' main= 'true' download= 'eager' />");
        printWriter.write("</resources>");
        printWriter.write("<application-desc main-class='ais.com.xbwl.java.AdaLoginFrame2'> ");
        printWriter.write("<param name='javaWebStart' value='true'/>");
        printWriter.println("    <argument>"+ request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+""+request.getContextPath()+"/login.jsp" +"</argument>"); 
        printWriter.write("</application-desc>");
        printWriter.write("</jnlp>");
	
        printWriter.flush();
        printWriter.close();
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request,response);
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}

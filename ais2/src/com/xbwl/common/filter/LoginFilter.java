package com.xbwl.common.filter;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.ralasafe.Factory;
import org.ralasafe.WebRalasafe;
import org.ralasafe.encrypt.Base64Encrypt;
import org.ralasafe.encrypt.Encrypt;
import org.ralasafe.encrypt.Md5Encrypt;
import org.ralasafe.encrypt.PlainEncrypt;
import org.ralasafe.encrypt.ShaEncrypt;
import org.ralasafe.metadata.user.FieldMetadata;
import org.ralasafe.servlet.WebUtil;
import org.ralasafe.user.User;
import org.ralasafe.user.UserManager;
import org.ralasafe.userType.UserType;
import org.ralasafe.util.StringUtil;
import org.springframework.util.Assert;

import com.xbwl.common.log.service.ISysLogService;
import com.xbwl.common.utils.MD5Utils;
import com.xbwl.common.utils.SpringContextHolder;
import com.xbwl.entity.SysLog;

public class LoginFilter implements Filter {
	private static final String RALASAFE_REQUEST_LOGIN = "ralasafeRequestLogin";
	private static final String DENY_MESSAGE = "denyMessage";
	private static final String GOTO_PAGE = "gotoPage";
	private String loginPage;
	private String checkLoginUrlPattern;
	private String[] uniqueFieldsParams;
	private String passwordParam;
	private String userPasswordField;
	private String denyMessage;
	private String successPage;
	private int maxAttempts;
	private String attemptMessage;
	private String userAttemptField;
	private String resetInterval;
	private Encrypt encrypt;
	
	private SpringContextHolder springContextHolder;
	
	
	private boolean isTest;

	// private String redirectLoginPage;

	public void destroy() {
	}

	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpReq = (HttpServletRequest) (req);
		HttpServletResponse httpResp = (HttpServletResponse) (resp);

		// If the request is login screen, there's no need to filter now
		if (requestForLoginPage(httpReq)) {
			chain.doFilter(req, resp);
			return;
		}

		HttpSession session = httpReq.getSession();
		if (isTest) {
			User testUser = new User();

			testUser.set("name", "张三");
			testUser.set("id", 1);
			testUser.set("departId", 1);
			testUser.set("rightDepart", "广州配送中心");
			testUser.set("bussDepart", 1);

			WebRalasafe.setCurrentUser(httpReq, testUser);
		}

		if (requestFromLoginPage(httpReq)) {
			
			WebRalasafe.setCurrentUser(httpReq, null);

			String gotoUrl = (String) session.getAttribute(GOTO_PAGE);

			User user = validUser(httpReq, httpResp);
			
			if (user == null) {
				gotoLoginPage(httpReq, httpResp, denyMessage, gotoUrl);
				return;
			} else {
				ISysLogService sysLog=springContextHolder.getBean("sysLogService");
				SysLog log=new SysLog();
				log.setClientIp(req.getRemoteAddr());
				log.setCreatedtime(new Date());
				log.setLogType("登录系统");
				log.setOperAccount(user.get("name").toString());
				
				sysLog.save(log);
				
				req.setAttribute(GOTO_PAGE, gotoUrl);

				session.removeAttribute(DENY_MESSAGE);
				session.removeAttribute(GOTO_PAGE);
				session.removeAttribute(RALASAFE_REQUEST_LOGIN);
				WebRalasafe.setCurrentUser(httpReq, user);

				chain.doFilter(req, resp);
				return;
			}
		} else {
			Object user = WebRalasafe.getCurrentUser(httpReq);
			if (user == null) {
				// not login yet, goto login screen
				session.removeAttribute(DENY_MESSAGE);
				String gotoPage = httpReq.getRequestURI();
				gotoLoginPage(httpReq, httpResp, null, gotoPage);
				return;
			} else {
				chain.doFilter(req, resp);
				return;
			}
		}
	}

	/**
	 * Does client side request for login page?
	 * 
	 * @param httpReq
	 * @return
	 */
	private boolean requestForLoginPage(HttpServletRequest httpReq) {
		String url = httpReq.getServletPath();
		if (url.toLowerCase().startsWith(loginPage.toLowerCase())) {
			// is ralasafe system direct to this page?
			String flag = (String) httpReq.getSession().getAttribute(
					RALASAFE_REQUEST_LOGIN);
			// String flag=httpReq.getParameter( RALASAFE_REQUEST_LOGIN );
			if (!"y".equalsIgnoreCase(flag)) {
				HttpSession session = httpReq.getSession();
				session.removeAttribute(DENY_MESSAGE);
				session.removeAttribute(GOTO_PAGE);
			}

			return true;
		}

		return false;
	}

	/**
	 * Client side filled username and password, request for login.
	 * 
	 * @param httpReq
	 * @return
	 */
	private boolean requestFromLoginPage(HttpServletRequest httpReq) {
		String url = httpReq.getHeader("REFERER");
		if (StringUtil.isEmpty(url)) {
			url = httpReq.getHeader("referer");
		}

		String fromPath = "";
		if (StringUtil.isEmpty(url)) {
			fromPath = httpReq.getServletPath();
		} else {
			String subUrl = url.substring(url.indexOf("://") + "://".length());

			String contextPath = httpReq.getContextPath();
			// whether is a ROOT context. ROOT context path is "".
			if ("".equals(contextPath)) {
				fromPath = subUrl.substring(subUrl.indexOf("/"));
			} else {
				fromPath = subUrl.substring(subUrl.indexOf(contextPath)
						+ contextPath.length());
			}
		}

		return fromPath.toLowerCase().startsWith(loginPage.toLowerCase());
	}

	// REVIEW-ACCEPT 增加方法注释
	//FIXED
	/**跳转进登陆界面
	 * @param httpReq
	 * @param httpResp
	 * @param denyMessage
	 * @param gotoPage
	 * @throws ServletException
	 * @throws IOException
	 */
	private void gotoLoginPage(HttpServletRequest httpReq,
			HttpServletResponse httpResp, String denyMessage, String gotoPage)
			throws ServletException, IOException {
		httpReq.getSession().setAttribute(RALASAFE_REQUEST_LOGIN, "y");
		httpReq.getSession().setAttribute(DENY_MESSAGE, denyMessage);
		httpReq.getSession().setAttribute(GOTO_PAGE, gotoPage);

		httpResp.sendRedirect(httpReq.getContextPath() + loginPage);
	}

	// REVIEW-ACCEPT 增加方法注释
	//FIXED
	/**验证用户
	 * @param httpReq
	 * @param httpResp
	 * @return
	 */
	private User validUser(HttpServletRequest httpReq,
			HttpServletResponse httpResp) {
		// convert uniqueFields to object
		UserType userType = WebUtil.getCurrentUserType(httpReq);
		FieldMetadata[] uniqueFields = userType.getUserMetadata()
				.getMainTableMetadata().getUniqueFields();

		User expectUser = new User();
		for (int i = 0; i < uniqueFields.length; i++) {
			FieldMetadata fieldMetadata = uniqueFields[i];
			String javaType = fieldMetadata.getJavaType();

			String paramName = fieldMetadata.getName();
			if (uniqueFieldsParams != null) {
				paramName = uniqueFieldsParams[i];
			}
			String rawValue = httpReq.getParameter(paramName);

			Object value = parse(javaType, rawValue);
			expectUser.set(fieldMetadata.getName(), value);
		}
		// REVIEW-ACCEPT 密码的明文是从req里面直接获取后才进行的加密吗？应当是在前台加密后才向后传输的
		//FIXED 现在密码都是明文保存进数据库的，如果用前台加密，可以考虑前台加密，但为了项目的易测性，建议放在后期加入此功能。
		String rawPassword = httpReq.getParameter(passwordParam);
		String password = encrypt.encrypt(rawPassword);

		UserManager manager = Factory.getUserManager(userType.getName());
		User findUser = manager.selectByUniqueFields(expectUser);
		
		try {
//			if (findUser != null 
//					&&findUser.get(userPasswordField)!=null && (findUser.get(userPasswordField).equals(password) || findUser.get(userPasswordField).equals(MD5Utils.strToMd5(password)) )) {
//				return findUser;
//			} else {
//				return null;
//			}
			//System.out.println(ps.equals(md5));
			if (findUser == null || null==findUser.get(userPasswordField)){
				return null;
			}
				
			String md5 = MD5Utils.strToMd5(password).trim();
			String ps = findUser.get(userPasswordField).toString().trim();
			
			if(ps!=null && (ps.equals(md5)||ps.equals(password))) {
				return findUser;
			} else {
				return null;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	// REVIEW-ACCEPT 增加方法注释
	/**类型转换工具方法
	 * @param javaType
	 * @param rawValue
	 * @return
	 */
	//FIXED
	private Object parse(String javaType, String rawValue) {
		// REVIEW-ACCEPT rawValue需要做空判断后才进行下面的处理
		//FIXED
		Assert.notNull(rawValue);
		
		if (javaType.equalsIgnoreCase("java.lang.String")) {
			return rawValue;
		} else if (javaType.equalsIgnoreCase("java.lang.Integer")) {
			return new Integer(rawValue);
		} else if (javaType.equalsIgnoreCase("java.lang.Double")) {
			return new Double(rawValue);
		} else if (javaType.equalsIgnoreCase("java.lang.Float")) {
			return new Float(rawValue);
		}
		return null;
	}

	public void init(FilterConfig config) throws ServletException {
		String rawUniqueFieldsParams = config
				.getInitParameter("uniqueFieldsParams");
		if (!StringUtil.isEmpty(rawUniqueFieldsParams)) {
			uniqueFieldsParams = StringUtil.splitAndTrim(rawUniqueFieldsParams,
					",");
		}

		passwordParam = config.getInitParameter("passwordParam");
		if (StringUtil.isEmpty(passwordParam)) {
			passwordParam = "password";
		}

		isTest = new Boolean(config.getInitParameter("isTest"));

		userPasswordField = config.getInitParameter("userPasswordField");
		if (StringUtil.isEmpty(userPasswordField)) {
			userPasswordField = "password";
		}

		denyMessage = config.getInitParameter("denyMessage");
		if (StringUtil.isEmpty(denyMessage)) {
			denyMessage = "用户名或密码错误！";
		}

		String encryptMethod = config.getInitParameter("encryptMethod");
		if (StringUtil.isEmpty(encryptMethod)) {
			encrypt = new PlainEncrypt();
		} else if ("base64".equalsIgnoreCase(encryptMethod)) {
			encrypt = new Base64Encrypt();
		} else if ("md5hex".equalsIgnoreCase(encryptMethod)) {
			encrypt = new Md5Encrypt();
		} else if ("shahex".equalsIgnoreCase(encryptMethod)) {
			encrypt = new ShaEncrypt();
		} else {
			// developer customize encrypt method
			try {
				Object instance = Class.forName(encryptMethod).newInstance();
				encrypt = (Encrypt) instance;
			} catch (Exception e) {
				e.printStackTrace();
				throw new ServletException("Encry method not found: "
						+ encryptMethod);
			}
		}

		loginPage = config.getInitParameter("loginPage");

		// redirectLoginPage="/"+config.getServletContext().getServletContextName()+loginPage;
		// if( redirectLoginPage.indexOf( "?" )==-1 ) {
		// redirectLoginPage=redirectLoginPage+"?1=1";
		// }
	}
}

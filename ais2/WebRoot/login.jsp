<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="org.ralasafe.util.StringUtil"%>
<%@page import="org.ralasafe.WebRalasafe"%>
<%@page import="java.net.URLDecoder"%>
<%@page import="java.util.Locale"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<script type="text/javascript">
	// 让光标默认停留在用户名的输入框
	function setFocus() {
 		document.getElementById("loginName").focus();
	}
</script>
<html>
	<head>
		<title>新邦物流配送管理系统-系统登陆</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />	
		<meta http-equiv="pragma" content="no-cache"/>
		<meta http-equiv="cache-control" content="no-cache"/>
		<meta http-equiv="expires" content="0"/>   	
        <link href="${pageContext.request.contextPath}/style/login.css" rel="stylesheet" type="text/css" />
    </head>
	<body onload="setFocus();">
		<form id="login"></form>
	    <script type="text/javascript">
	    	var oLogin = document.getElementById("login");
	
	    	if(parent.window != window){
				with(oLogin) {
					action = '${pageContext.request.contextPath}/login/loginAction!input.action';
					target = '_top';
					submit();
				}
	        }
	        
	        
	    </script>
  <center>
      <form id="loginForm" action="${pageContext.request.contextPath }/login/loginAction!login.action" method="post">
      <div class="loginDiv" style="">
        <c:if test="${denyMessage != null && not empty denyMessage}">
        	<script type="text/javascript">
        		sendNSCommand('logout','${denyMessage}');
        	</script>
			<p class="formt"><font color="red">${denyMessage }</font></p>
		</c:if>
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <!-- 
          <tr>
            <td width="17%" height="106">&nbsp;</td>
            <td width="16%">&nbsp;</td>
            <td width="90%"><span style="font-size:25px;"><b>请用登陆器登陆系统！</b></span></td>
            <td width="1%">&nbsp;</td>
            <td width="19%">&nbsp;</td>
            <td height="32">&nbsp;</td>
            <td><span></span></td>
            <td></td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
			-->
          
          <tr>
            <td height="32">&nbsp;</td>
            <td><span class="myFont">用户名：</span></td>
            <td><input name="loginName" type="text" class="loginTextField" id="loginName"></td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td><span class="myFont">密　码：</span></td>
            <td><input name="password" type="password" class="loginTextField" id="password"/></td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
          <tr>
          	<td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>
              <input name="button" type="submit" style="widows: 100px;" class="loginBtn" id="button" value="提交" />
              <input name="button3" type="reset"  class="loginBtn" id="button3" value="重置">
            </td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
          </tr>    
          <tr>
          	<td>&nbsp;</td>
          	<td colspan="4">
          	</td>
          </tr>
        </table>
      </div>
      </form>
  </center>  
</body>
</html>
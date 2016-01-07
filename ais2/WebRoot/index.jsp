<%@ page language="java" pageEncoding="UTF-8"%>
<SCRIPT LANGUAGE="JavaScript">
var javawsInstalled = 0;
var javaws12Installed = 0;

isIE = "false";

if (navigator.mimeTypes && navigator.mimeTypes.length) {
x = navigator.mimeTypes['application/x-java-jnlp-file'];
if (x) {
javawsInstalled = 1;
javaws12Installed=1;
}
} else { 
isIE = "true";
}
</SCRIPT>
<SCRIPT LANGUAGE="VBScript">
on error resume next
If isIE = "true" Then
If Not(IsObject(CreateObject("JavaWebStart.isInstalled"))) Then
javawsInstalled = 0
Else
javawsInstalled = 1
End If
If Not(IsObject(CreateObject("JavaWebStart.isInstalled.2"))) Then
javaws12Installed = 0
Else
javaws12Installed = 1
End If
End If
</SCRIPT>

<SCRIPT LANGUAGE="JavaScript"
SRC="commonjs/xbDetectBrowser.js">
</SCRIPT>

<SCRIPT LANGUAGE="JavaScript">
if (javawsInstalled || navigator.family == 'gecko') {
document.write("<a href=${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}/${pageContext.request.contextPath}/servlet/jnlp>进入系统</a>");
} else {
document.write("Click ");
document.write("<a href=http://dlres.java.sun.com/PluginBrowserCheck?pass=http://localhost:9999/ais2/downl.html&fail=http://java.sun.com/cgi-bin/javawebstart-platform.sh>here</a> ");
document.write("to download and install the Java Web Start product and the application.");
}
</SCRIPT>
<HTML>
<BODY>

<OBJECT CODEBASE="http://java.sun.
com/products/plugin/autodl/jinstall-1_4_1-windows-i586.cab" 
CLASSID="clsid:5852F5ED-8BF4-11D4-A245-0080C6F74284" HEIGHT=0 WIDTH=0>

<PARAM NAME="app" VALUE="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}/${pageContext.request.contextPath}/servlet/jnlp">
<PARAM NAME="back" VALUE="true">

<!-- Alternate HTML for browsers which cannot instantiate the object 
-->
<A HREF="http://java.sun.com/cgi-bin/javawebstart-platform.sh?"></A>
</OBJECT>
</BODY>
<SCRIPT LANGUAGE="JavaScript">
  if (window.opener) {
        window.opener=null;
        window.open('','_self');
        window.close();
    }
</SCRIPT>
</HTML>








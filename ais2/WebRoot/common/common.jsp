<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@page import="org.ralasafe.user.User"%>
<%@page import="org.ralasafe.WebRalasafe"%>
   	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extjs3.1/resources/css/ext-all.css" />
   	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/resource.css" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/extjs3.1/adapter/ext/ext-base.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/extjs3.1/ext-all.js"></script> 
    <script type="text/javascript" src="${pageContext.request.contextPath}/commonjs/ext-lang-zh_CN.js"></script> 


    <script type="text/javascript" src="${pageContext.request.contextPath}/commonjs/ais2Utils.js"></script>
	<%User user= WebRalasafe.getCurrentUser(request);%>
     <script type="text/javascript">
		var sysPath='${pageContext.request.contextPath}';
		Ext.BLANK_IMAGE_URL = sysPath + "/imags/s.gif";
		var alertTitle="系统消息";
		Ext.QuickTips.init();
		var pageSize=50;
		var comboSize=20;
		var userName='<%=user.get("name")%>';
		var bussDepart='<%=user.get("bussDepart")%>';
		var bussDepartName='<%=user.get("rightDepart")%>';
		var departId='<%=user.get("departId")%>';
		var userId='<%=user.get("id")%>';
		var colcss ='background: #CAE3FF;';

		var stationId='<%=user.get("stationId") %>';
		Ext.getDoc().on('keydown',function(e){
			if(e.getKey() == 8 && e.getTarget().type =='text' && !e.getTarget().readOnly){
			
			}else if(e.getKey() == 8 && e.getTarget().type =='textarea' && !e.getTarget().readOnly){ 
		
			}else if(e.getKey() == 8 && e.getTarget().type =='password' && !e.getTarget().readOnly){

			}else if(e.getKey() == 8){
				e.preventDefault();
			}
			if(e.getKey()==37&&e.ctrlKey==true){
			 var iindex=event.srcElement.sourceIndex;
				for(var i=1;i<iindex;i++) {   
					  var tp= document.all(iindex-i);
				  	  var itype=tp.type;
					 if(!tp.disabled && (itype=="text"||itype=="select-one")) { 
								  tp.focus(); 
						break;   
					  }   
			     } 
			}
		});
		
if  (!Ext.grid.GridView.prototype.templates) {    
    Ext.grid.GridView.prototype.templates = {};    
}    
Ext.grid.GridView.prototype.templates.cell =  new  Ext.Template(    
     '<td class="x-grid3-col x-grid3-cell x-grid3-td-{id} x-selectable {css}" style="{style}" tabIndex="0" {cellAttr}>' ,    
     '<div class="x-grid3-cell-inner x-grid3-col-{id}" {attr}>{value}</div>' ,    
     '</td>'    
);
		
		
 	  </script>
<style>
.x-grid3-cell-inner{
white-space:normal;
overflow:visible;
}
</style>
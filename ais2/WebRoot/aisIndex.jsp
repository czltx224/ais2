
<%@page import="org.ralasafe.user.User"%>
<%@page import="org.ralasafe.WebRalasafe"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" pageEncoding="UTF-8"%>
<html>
  <head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
  	<%User user= WebRalasafe.getCurrentUser(request);%>
  
    <title>新邦物流有限公司</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/ais.css" />
	<style> 
		A{TEXT-DECORATION:   NONE} 
		a:link{color:black;text-decoration:none;}
		a:hover{color:blur;font-size：40pt;text-decoration:underline}
	
	</style>
	<jsp:include page="/common/common.jsp" />
	
	 <script type="text/javascript" src="${pageContext.request.contextPath}/extjs3.1/ux/ux-all.js"></script>
	 
	 <script type="text/javascript" src="${pageContext.request.contextPath}/commonjs/extTabCloseMenu.js" ></script>
	 <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extjs3.1/ux/css/DateTimePicker.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/extjs3.1/ux/DateTimeField.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extjs3.1/ux/css/ux-all.css"/>
    <!-- 
    <script type="text/javascript" src="${pageContext.request.contextPath}/extjs3.1/ux/examples.js"></script>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extjs3.1/ux/css/examples.css"/>
	 -->
	<style type="text/css">
		<!--
		body{
			margin:0px;
			font-size:12px;
		
		}
		
		.top_font_white{ 
			color:#FFFFFF;
			font-size:12px;
			}
		.south_font_blur{ 
			color:#15428B;
			font-size:13px;
			}	
		.top_font_white_user{ 
			color:#FFFFFF;
			vertical-align:super;
			font-size:12px;
			}
		-->
	</style>
	
	
  	<script type="text/javascript">
  		
  			
  		
  		Ext.onReady(function(){
  		var rightClick = new Ext.menu.Menu({  
	        id :'rightClickCont',  
	        items : [{  
	                id:'rmove',  
	                text : '问题提交',  
	                handler:function (){  
	                	var node=new Ext.tree.TreeNode({id:'keel_'+240,leaf :false,text:"问题提交和处理"});
		      			node.attributes={href1:'sys/sysProblemAction!input.action'};
		       			toAddTabPage(node,true);
	                }  
	            }, {  
	                id:'rname',  
	                text : '综合查询',
	                handler:function (){
	                	var node=new Ext.tree.TreeNode({id:'keel_'+999999999,leaf :false,text:"综合查询"});
		      			node.attributes={href1:'fax/oprFaxInAction!inputQuery.action'};
		       			toAddTabPage(node,true);
	                }  
	            }, {  
	                id:'rdetail',    
	                text : '异常管理', //
	                handler:function (){
	                	var node=new Ext.tree.TreeNode({id:'keel_'+120,leaf :false,text:"异常反馈"});
		      			node.attributes={href1:'exception/oprExceptionAction!input.action'};
		       			toAddTabPage(node,true);
	                }  
	            }, {  
	                id:'rattribute',  
	                text : '流程管理',
	                handler:function (){ 
	                	var node=new Ext.tree.TreeNode({id:'keel_'+858584,leaf :false,text:"流程处理"});
		      			node.attributes={href1:'flow/workFlowstepAction!input.action'};
		       			toAddTabPage(node,true);
	                }  
	            }]  
    	});
  		
  		Ext.getDoc().on("contextmenu", function(e){
		    e.stopEvent();
		    rightClick.showAt(e.getXY());//取得鼠标点击坐标，展示菜单 
		});
 
    
       Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
	 
	  var treePanel = new Ext.tree.TreePanel({
		    	    split: true,
			        height: 300,
			        minSize: 150,
			        autoScroll: true,
			        enableTabScroll:true,
			        title:'系统菜单',
			        // tree-specific configs:
			        rootVisible: false,
			        lines: false,
			     //   singleExpand: true,
			        useArrows: true,
			        loader: new Ext.tree.TreeLoader({
			            dataUrl:'loginAction!getMenu.action'
			        }),
			      	root: new Ext.tree.AsyncTreeNode({id:"0",text:''}),
			        listeners : {
			    		'click':function(node){
				    		var id = node.id;
				    		var leaf = node.leaf;
				    		if(id!=0&&leaf){
						    	toAddTabPage(node);
				    		}	    		
			    	},scope:this
			    }

		    });
		    
    
     tabPanl= new Ext.TabPanel( {
				        region: 'center',
				        id:'center',
				        enableTabScroll : true,
						resizeTabs      : true,
						minTabWidth     : 100,
				        xtype: 'tabpanel', // TabPanel itself has no title
				        activeTab: 0,      // First tab active by default
				        plugins: new Ext.ux.TabCloseMenu(),
				        items: {
				            title: '首页',
				            html: '<iframe scrolling="auto" frameborder="0" width="100%" height="100%" src="../textlist.html"></iframe>'
				        }
				    });
				    
				    
				    
      Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
	

	
	var commonMenuFields=[{name:"id",mapping:'id'},
	'name','sortNum',
	'href','nodeId'];
	
	var commonMenuJsonread= new Ext.data.JsonReader({
               root:'result',
               totalProperty:'totalCount'},
               commonMenuFields);
	
 	var commonMenuDataStore = new Ext.data.Store({
        storeId:"commonMenuDataStore",
        proxy: new Ext.data.HttpProxy({url:sysPath+"/sys/sysCommonMenuAction!list.action"}),
        baseParams:{
			filter_EQL_userId:userId,	
			limit : 200	
        },
        reader:commonMenuJsonread,
        sortInfo:{field:'sortNum',direction:'ASC'}
    });
    
	
	  var commonMenuRecordGrid=new Ext.grid.GridPanel({
		id:'commonMenuCenter',
		width :150,
		autoScroll : true,  //面板上的body元素
		hideHeaders:true,
	//	trackMouseOver : true,
		frame : false,
		border : false,
//		enableColumnResize:false, //关闭列的自适大小功能
		autoWidth:false,
		stripeRows : true,
		columns:[
				{header: 'ID', dataIndex: 'id',hidden : true,hideable:false},
			//	{header: '排序', dataIndex: 'sortNum',width:80,hidden : true,hideable:false},
		        {header: '权限值', dataIndex: 'nodeId',width:60,hidden : true,hideable:false},
		        {header: '', dataIndex: 'name',hideable:true,width:147,
		        	renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){
		    			return "&nbsp;&nbsp;&nbsp;<img src='../images/table_16x16.gif' ></img>&nbsp;<a href='#' onclick='forWardStore("+rowIndex+");' >"+value+"</a>";
		    		}
		    	},
		        {header: '路径',hideable:false, dataIndex: 'href',hidden : true},
		         {header: '排序Num',hideable:false, dataIndex: 'sortNum',hidden : true}
		    ],
		store : commonMenuDataStore,
		listeners:{
			afterlayout:function(grid,e){
				dateStoreLoad();
			}
		}
	});
	function dateStoreLoad(){
		 commonMenuDataStore.reload({
					params : {
						start : 0,
						limit : 200
					},callback :function(v){
						if(v.length==0){
							treePanel.expand();
						}else{
							var vwidth=Ext.lib.Dom.getViewHeight()*0.55;
							commonMenuRecordGrid.setHeight(vwidth);
						}
					}
			});
	}
    commonMenuPanl = new Ext.Panel({ 
		   layout :'fit', 
		   labelAlign : 'center',
		   frame:false,        //渲染面板 
		   border : false,
		   title:'常用菜单&nbsp;&nbsp;&nbsp;<a href="#" onclick="openHtml();" >设置菜单</a>',
		   height :  Ext.lib.Dom.getViewHeight()-330,
		 //  titleCollapse:true,
		   enableDragDrop:true,
		   stripeRows:false,
		   trackMouseOver:true, 
		   collapsible : true,
		   width: 166, 
		  // applyTo :'panel', 
		   defaults : {                  //设置默认属性 
		    bodyStyle:'background-color:#FFFFFF'       //设置面板体的背景色 
		   }, 
		//面板items配置项默认的xtype类型为panel，该默认值可以通过defaultType配置项进行更改 
		   items: [ 
		   		commonMenuRecordGrid
		   ] 
		  }); 
        
        var viewport = new Ext.Viewport({
            layout: 'border',
            items: [ 
           		 	new Ext.Panel({
	                     region:'north',
	                     contentEl:'north', 
	                     //iconCls:'application_homeIcon', 
	                     height:87,
	                    // collapsible:true,
						 border:false,
	                     layout: 'fit'
	                    }),
						{
				        region: 'west',
				        collapsible: true,
				        autoScroll:true,
				        collapseFirst:true,
				        title: '项目菜单',
				        id:'west',
				        layout:'accordion',
				 	    layoutConfig: {
					        titleCollapse: false,
					        animate: true,
					        activeOnTop: true
					    },
				        width: 170,
				        items:[{
						        title: '常用功能菜单',
								buttonAlign:'center',
						        items:[commonMenuPanl,
						        		new Ext.form.FormPanel({
											labelAlign : 'center',
											frame : true,
											width: 166,
											buttonAlign:'center',
										//	url:sysPath+'/user/userAction!ralaList.action?privilege=17',
										 	height: 330,
										 	border :false,
											bodyStyle : 'padding:5px 5px 5px',
										    labelWidth : 100,
								            labelAlign : "center",
									        items : [{
									        	text:'计算器',
												id : 'calc',
												iconCls : 'table',
										//		iconCls:'userEdit',
												xtype :'button',
												width: 120,
												handler : function(v) {
														sendNSCommand("cmd","calc");
													}
												
									        	},
									        	{
									        	text:'任务管理器',
												id : 'taskmgr',
												iconCls : 'computerUser',
										//		iconCls:'userEdit',
												xtype :'button',
												width: 120,
												handler : function(v) {
														sendNSCommand("cmd","taskmgr");
													}
												
									        	},
									        	{
									        	text:'垃圾清理',
												id : 'cleanmgr',
												iconCls : 'refresh',
										//		iconCls:'userEdit',
												xtype :'button',
												width: 120,
												handler : function(v) {
														sendNSCommand("cmd","cleanmgr");
													}
												
									        	},
									        	{
									        	text:'计算机管理',
												id : 'compmgmt.msc',
											//	iconCls:'userEdit',
											iconCls : 'computerUser',
												xtype :'button',
												width: 120,
												handler : function(v) {
														sendNSCommand("cmd","compmgmt.msc");
													}
												}	,
									        	{
									        	text:'文本备注',
												id : 'chkdsk.exe',
												iconCls : 'refresh',
											//	iconCls:'userEdit',
												xtype :'button',
												width: 120,
												handler : function(v) {
														sendNSCommand("cmd","notepad");
													}
												}	,
												{
									        	text:'15秒自动关机',
												id : 'rononce -p',
											//	iconCls:'userEdit',
												iconCls : 'groupClose',
												xtype :'button',
												width: 120,
												handler : function(v) {
														Ext.Msg.confirm(alertTitle,'请保存好您的数据，确定需要<span style="color:red">&nbsp;关机&nbsp;</span>吗?',function(btnYes) {
															if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
																sendNSCommand("cmd","shutdown /s /t 15");
															}
														});
													}
												
									        	}]
											})

						        
						        ]
				   			 },treePanel
				   		 		
				   		 
				   		 ]
				    },  new Ext.BoxComponent({
		                     region:'south',
		                     contentEl: 'south',
		                     height:17,
		                     layout: 'fit',
		                     collapsible: true})
                     ,{
				   	 region:'center', 
                     id: 'centerPanel', 
                     iconCls:'',
                     autoScroll:false,
                     layout: 'fit',
                     items:[tabPanl]}
	            
	           ]
        });
        
            var closeButton = new Ext.Button({
				iconCls : 'problem',
				iconAlign : 'left',
				text:'<font class="top_font_white">问题提交和处理</font>',
				scale : 'medium',
				width : 20,
				height:15,
				tooltip : '<span style="font-size:12px">问题提交和处理</span>',
				pressed : true,
				arrowAlign : 'right',
				renderTo : 'problem',
				handler : function(v) {
				
					var node=new Ext.tree.TreeNode({id:'keel_'+240,leaf :false,text:"问题提交和处理"});
		      		node.attributes={href1:'sys/sysProblemAction!input.action'};
		       		toAddTabPage(node,true);
			    	

				}
			});
			
         var closeButton = new Ext.Button({
				iconCls : 'top_calendar',
				iconAlign : 'left',
				text:'<font class="top_font_white">在线帮助</font>',
				scale : 'medium',
				width : 20,
				height:15,
				tooltip : '<span style="font-size:12px">在线帮助</span>',
				pressed : true,
				arrowAlign : 'right',
				renderTo : 'help',
				handler : function() {
					
					try{
						sendNSCommand("logout");
					}catch(e){
					}
				
					window.location.href = '${pageContext.request.contextPath}/logout';
				}
			});
			
			  var closeButton = new Ext.Button({
				iconCls : 'top_close',
				iconAlign : 'left',
				text:'<font class="top_font_white">退出</font>',
				scale : 'medium',
				width : 15,
				height:15,
				tooltip : '<span style="font-size:12px">切换用户,安全退出系统</span>',
				pressed : true,
				arrowAlign : 'right',
				renderTo : 'closeDiv',
				handler : function() {
				     Ext.Msg.confirm(alertTitle,'<span style="color:red">您确定需要退出系统吗？</span>',function(btnYes) {
						if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true){
							try{
								sendNSCommand("logout");
							}catch(e){
							}
							window.location.href = '${pageContext.request.contextPath}/logout';
						}
					});
					
				}
			});
			
			
	var fields=[{name:"id",mapping:'id'},{name:"departId",mapping:'departId'},'userCode','loginName','userName','birthdayType',
	                                     {name:"departName",mapping:'departName'},'birthday','workstatus','hrstatus','manCode',
                                         {name:"stationId",mapping:'stationId'}, 'offTel','telPhone','sex','updateName',
                                         {name:"stationName",mapping:'stationName'},'stationNames','duty','status',
                                         {name:"bussDepart",mapping:'bussDepart'}, 'userLevel','createName','createTime','updateName',
                                         {name:"bussDepartName",mapping:'bussDepartName'},'password','updateTime','ts'];
	
	jsonread= new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'},
                    fields);

			var mainMenu = new Ext.menu.Menu({
				id : 'mainMenu',
				items : []
			});
			
		Ext.Ajax.request({
				url : sysPath+ "/user/userAction!getUserlist.action",
				params:{
					id:userId
				},
				success : function(resp) {
					var numb=Ext.decode(resp.responseText).length;
					for(var i=0;i<numb;i++){
						var item={
							text : Ext.decode(resp.responseText)[i].userName,
							id:Ext.decode(resp.responseText)[i].id+'',
							iconCls : 'computerUser',
							handler : function(b) {
								 Ext.Msg.confirm(alertTitle,'您确定需要切换用户吗？',function(btnYes) {
									if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true){
										 changeUserInit(b.getId());
									}
								});
								
							}
						};
						mainMenu.add(item);
					}
					var itemU={
						text : '修改个人信息',
						iconCls : 'userEdit',
						handler : function() {
							updateUserInit();
						}
					};
					mainMenu.add(itemU);
					var itemP={
						text : '修改用户密码',
						iconCls : 'groupEdit',
						handler : function() {
							updateUserPassword();
						}
					};
					mainMenu.add(itemP);
					

				},
				failure : function(response) {
					Ext.Msg.alert(alertTitle,"加载用户的数据失败");
				}
			})

			var configButton = new Ext.Button({
				text : '<font class="top_font_white">当前用户：<%=user.get("name") %></font>',
				iconCls : 'top_user',
				iconAlign : 'left',
				scale : 'medium',
				width : 15,
				height:15,
				tooltip : '<span style="font-size:12px">切换用户</span>',
				pressed : true,
				renderTo : 'configDiv',
				menu : mainMenu
			});	
    });
	
	/**
    * 切换用户 
    */
	function changeUserInit(userId){
		Ext.Ajax.request({
			url : sysPath+ "/user/userAction!changeUser.action",
			params:{
				id:userId
			},
			success : function(resp) {
				parent.location.href=sysPath+'/login/loginAction!login.action';
				parent.reload();
			},
			failure : function(response) {
				location.reload();
				Ext.Msg.alert(alertTitle,"加载用户的数据失败");
			}
		})
	}

    /**
    * 修改用户密码 
    */
	function updateUserPassword(){
		var userFormPanel = new Ext.form.FormPanel({
				defaultType : 'textfield',
				labelAlign : 'right',
				labelWidth : 80,
				frame : true,
				reader :jsonread,
				bodyStyle : 'padding:5 5 0',
				items : [{
							fieldLabel : '登录帐户',
							name : 'loginName',
							id : 'loginName',
							allowBlank : false,
							readOnly : true,
							fieldClass : 'x-custom-field-disabled',
							anchor : '99%'
						}, {
							fieldLabel : '姓名',
							name : 'userName',
							id : 'userName',
							allowBlank : false,
							readOnly : true,
							fieldClass : 'x-custom-field-disabled',
							anchor : '99%'
						}, {
							fieldLabel : '当前密码<span style="color:red">*</span>',
							name : 'password1',
							id : 'password1',
							inputType : 'password',
							maxLength : 50,
							allowBlank : false,
							enableKeyEvents:true,
							listeners : {
						 			keyup:function(numberfield, e){
						                if(e.getKey() == 13 ){
						                	Ext.getCmp('password2').focus();
						                 }
						 			}
						 	},
							anchor : '99%'
						}, {
							fieldLabel : '新密码<span style="color:red">*</span>',
							name : 'password2',
							id : 'password2',
							inputType : 'password',
							maxLength : 50,
						//	regex:/^[A-Za-z]+[0-9]+$/,
						//	regexText:'格式不符合要求，必须包含数据和字母',
							minLength:6,
							enableKeyEvents:true,
							listeners : {
						 			keyup:function(numberfield, e){
						                if(e.getKey() == 13 ){
						                	Ext.getCmp('password3').focus();
						                 }
						 			}
						 	},
							allowBlank : false,
							anchor : '99%'
						}, {
							fieldLabel : '确认新密码<span style="color:red">*</span>',
							name : 'password3',
							id : 'password3',
					//		regex:/^(?=.{6, 12}$)(?=[0-9A-Za-z-]*$)(?=(?!-).*(?<!-)$)(?=.*[^0-9])(?!.*--)$/,
							inputType : 'password',
							maxLength : 50,
							editable:true,
							vtypeText:"两次输入的密码不一致！",  
					//		regexText:'格式不符合要求，必须包含数据和字母',
							minLength:6,
							enableKeyEvents:true,
							listeners : {
						 			keyup:function(numberfield, e){
						                if(e.getKey() == 13 ){
						                	Ext.getCmp('sv').focus();
						                 }
						 			}
						 	},
							confirmTo:"password2",
							allowBlank : false,
							anchor : '99%'
						}, {
							id : 'password',
							name : 'password',
							hidden : true
						},{	id:"id",
							name : "id",
							xtype : "hidden"
						},{
							name : "tts",
							xtype : "hidden"
						}]
			});
	
			var userWindow = new Ext.Window({
				layout : 'fit',
				width : 320,
				height : 225,
				resizable : false,
				draggable : true,
				closeAction : 'hide',
				modal : true,
				title : '<span class="commoncss">密码修改</span>',
				iconCls : 'groupNotPass',
				collapsible : true,
				titleCollapse : true,
				maximizable : false,
				buttonAlign : 'right',
				border : false,
				animCollapse : true,
				animateTarget : Ext.getBody(),
				constrain : true,
				listeners : {
					'show' : function(obj) {
						Ext.getCmp('password1').focus(true,200);
					}
				},
				items : [userFormPanel],
				buttons : [{
					text : '保存',
					iconCls : 'groupSave',
					id:'sv',
					handler : function() {
						var password2=Ext.getCmp('password2').getValue();
						var password3=Ext.getCmp('password3').getValue();
						if(password2!=password3){
							Ext.Msg.alert(alertTitle,'两次输入的新密码不一致！');
							this.disabled = false;//只能点击一次
							return;
						}
						
						if (userFormPanel.getForm().isValid()) {
							this.disabled = true;//只能点击一次
							/*
							var regex=/^[/a-zA-z]+[/0-9]+$/;
					
					     	if(!regex.test(password2)){
					     	 	Ext.Msg.alert(alertTitle,'密码格式不符合要求，必须包含字母和数字！');
					     	 	this.disabled = false;//只能点击一次
					     	    return;
					     	}*/
							
							userFormPanel.getForm().submit({
								url : sysPath+ "/user/userAction!savePassword.action",
								params:{
									privilege:23},
								waitMsg : '正在保存数据...',
								success : function(form, action) {
											userWindow.close();
											Ext.Msg.alert(alertTitle,action.result.msg);
								},
								failure : function(form, action) {
									if (action.faliureType == Ext.form.Action.SERVER_INVALID) {
										Ext.Msg.alert(alertTitle, "服务器连接异常，请联系管理员！");
									} else {
										if (!action.result.success) {
											Ext.Msg.alert(alertTitle,action.result.msg, function() {
													});
		
										}
									}
								}
						});
						
						}
						this.disabled = false;//只能点击一次
					}
				}, {
					text : '关闭',
					handler : function() {
						userWindow.close();
					}
				}]
			});
	
		userWindow.on('hide', function() {
					userFormPanel.destroy();
				});
		
		
		userWindow.on('show', function() {
					setTimeout(function() {
								userFormPanel.form.load({
											url : sysPath+ "/user/userAction!list.action",
											params:{filter_EQL_id:userId,limit : pageSize},
											success : function(form, action) {
											},
											failure : function(form, action) {
												Ext.Msg.alert(alertTitle,'数据读取失败:'+ action.failureType);
											}
										});
							}, 5);
				});
	
	
		userWindow.show();
	}

    /**
    * 修改个人信息 
    */
    function updateUserInit(){
    					 var formUser = new Ext.form.FormPanel({
								labelAlign : 'left',
								frame : true,
								bodyStyle : 'padding:5px 5px 5px',
							    width : 600,
							    labelWidth : 70,
								reader :jsonread,
					            labelAlign : "right",
						        items : [{
											layout : 'column',
											items : [{
														columnWidth : .5,
														layout : 'form',
														items : [{	id:"id",
																	name : "id",
																	xtype : "hidden"
																},{	id:"password",
																	name : "password",
																	xtype : "hidden"
																},{id:"ts",
																	name : "ts",
																	xtype : "hidden"
																}, {
																	xtype : 'textfield',
																	labelAlign : 'left',
																	fieldLabel : '用户编号',
																	name : 'userCode',
																	id:'userCode',
																	readOnly:true,
																	maxLength:10,
																	allowBlank : false,
																	blankText : "用户编号不能为空！",
																	anchor : '95%',
																	listeners : {
																		'blur':function(com){
										
																		 }
																	}
																},  {
																	xtype : 'textfield',
																	labelAlign : 'left',
																	fieldLabel : '用户名<span style="color:red">*</span>',
																	name : 'userName',
																	id:'userName',
																	maxLength:20,
																//	value:_record[0].data.userName,
																	allowBlank : false,
																	blankText : "用户名不能为空！",
																	anchor : '95%'
																},  {xtype: 'radiogroup',
													                fieldLabel: '生日类型',
													                id:"birthdaynum",
													                columns: 3,
													                items: [{
													                    inputValue: '0',
													                    boxLabel: '农历',
													                    name: 'birthdayType',
													                    checked:true
													                }, {
													                    inputValue: '1',
													                    name: 'birthdayType' ,
													                    boxLabel: '阳历'
													                }]
																},{xtype : 'textfield',
																	labelAlign : 'left',
																	fieldLabel : '身份证号<span style="color:red">*</span>',
																	name : 'manCode',
																	id:'manCode',
																	maxLength:20,
																	allowBlank : false,
																	blankText : "身份证号码不能为空！",
																	anchor : '95%'
																}, {
																	xtype : 'numberfield',
																	labelAlign : 'left',
																	id:'telPhone',
																	fieldLabel : '移动电话<span style="color:red">*</span>',
																	maxLength:20,
																	allowBlank : false,
																	name : 'telPhone',
																	anchor : '95%',
																	blankText : "移动电话不能为空！"
																}]

													}, {
														columnWidth : .5,
														layout : 'form',
														items : [{
																	xtype : 'textfield',
																	labelAlign : 'left',
																	fieldLabel : '登录账号<span style="color:red">*</span>',
																	name : 'loginName',
																	id:'loginName',
																	maxLength:20,
																	blankText : "登录账号不能为空！",
																	anchor : '95%'
																},{xtype: 'radiogroup',
													                fieldLabel: '性别<span style="color:red">*</span>',
													                id:"sexnum",
													                columns: 3,
													                items: [{
													                    inputValue: '1',
													                    boxLabel: '男',
													                     name: 'sex',
													                    checked:true
													                }, {
													                    boxLabel: '女',
													                    inputValue: '0',
													                     name: 'sex'
													                }]
																}, {fieldLabel: '生日日期',
																    name: 'birthday',
																    id:'birthday',
																    labelAlign : 'left',
																    xtype:'datefield',
																    format : 'Y-m-d',
																    width : 150,
																    editable : false,
																//	allowBlank : false,
																	anchor : '95%'
																},{
																	xtype : 'numberfield',
																	labelAlign : 'left',
																	fieldLabel : '办公电话<span style="color:red">*</span>',
																	maxLength:20,
																	allowBlank : false,
																	name : 'offTel',
																	id : 'offTel',
																	anchor : '95%',
																	blankText : "办公电话不能为空！"
																}]
													}]
													
										},{
											labelAlign : 'top',
											xtype : 'textarea',
											name : 'duty',
											id:'duty',
											maxLength:500,
											fieldLabel : '岗位职责',
											height : 50,
											width:'95%'
										}]
										});
		
							

		//	form.getForm().loadRecord(_record);
		//	menuStore.load();
		//	stationStore.load();
		//	bussStore.load();
			Ext.Ajax.request({
				url : sysPath+ "/user/userAction!list.action",
				params:{filter_EQL_id:userId,limit : pageSize},
				success : function(resp) {
					Ext.getCmp("userName").setValue(Ext.decode(resp.responseText).result[0].userName);
					Ext.getCmp("userCode").setValue(Ext.decode(resp.responseText).result[0].userCode);
					Ext.getCmp("manCode").setValue(Ext.decode(resp.responseText).result[0].manCode);
					Ext.getCmp("duty").setValue(Ext.decode(resp.responseText).result[0].duty);
					Ext.getCmp("birthday").setValue(Ext.decode(resp.responseText).result[0].birthday);
					Ext.getCmp("offTel").setValue(Ext.decode(resp.responseText).result[0].offTel);
					Ext.getCmp("loginName").setValue(Ext.decode(resp.responseText).result[0].loginName);
					Ext.getCmp("id").setValue(Ext.decode(resp.responseText).result[0].id);
					Ext.getCmp("telPhone").setValue(Ext.decode(resp.responseText).result[0].telPhone);
					Ext.getCmp("ts").setValue(Ext.decode(resp.responseText).result[0].ts);
				  	Ext.getCmp("sexnum").setValue(Ext.decode(resp.responseText).result[0].sex);
					Ext.getCmp("birthdaynum").setValue(Ext.decode(resp.responseText).result[0].birthdayType);
				},
				failure : function(response) {
					Ext.Msg.alert(alertTitle,"加载用户的数据失败");
				}
			})
						  
		var win = new Ext.Window({
			title : '修改用户信息',
			width : 600,
			closeAction : 'hide',
			plain : true,
			draggable : true,
		    resizable : false,	
			modal : true,
			constrain : true,
			collapsible : true,
			titleCollapse : true,
			maximizable : false,

			iconCls : 'userAdd',
				border : false,
				animCollapse : true,
				animateTarget : Ext.getBody(),
	
			
			items : formUser,
			buttonAlign : "center",	buttons : [{
				text : "保存",
				iconCls : 'groupSave',
				handler : function() {
					if (formUser.getForm().isValid()) {
						this.disabled = true;//只能点击一次
						formUser.getForm().submit({
							url : sysPath+ '/user/userAction!save.action',
							params:{
								privilege:23
							},
							waitMsg : '正在保存数据...',
							success : function(form, action) {
										win.hide(), 
										Ext.Msg.alert(alertTitle,action.result.msg);
							},
							failure : function(form, action) {
								this.disabled = false;//只能点击一次
								if (action.faliureType == Ext.form.Action.SERVER_INVALID) {
									Ext.Msg.alert(alertTitle, "服务器连接异常，请联系管理员！");
								} else {
										Ext.Msg.alert(alertTitle,action.result.msg);
								}
							}
						});
					}
				}
			}, {
				text : "重置",
				iconCls : 'refresh',
				handler : function() {		
							Ext.Ajax.request({
								url : sysPath+ "/user/userAction!list.action",
								params:{filter_EQL_id:userId,limit : pageSize},
								success : function(resp) {
									Ext.getCmp("telPhone").setValue(Ext.decode(resp.responseText).result[0].telPhone);
									Ext.getCmp("userName").setValue(Ext.decode(resp.responseText).result[0].userName);
									Ext.getCmp("userCode").setValue(Ext.decode(resp.responseText).result[0].userCode);
									Ext.getCmp("manCode").setValue(Ext.decode(resp.responseText).result[0].manCode);
									Ext.getCmp("duty").setValue(Ext.decode(resp.responseText).result[0].duty);
									Ext.getCmp("birthday").setValue(Ext.decode(resp.responseText).result[0].birthday);
									Ext.getCmp("offTel").setValue(Ext.decode(resp.responseText).result[0].offTel);
									Ext.getCmp("loginName").setValue(Ext.decode(resp.responseText).result[0].loginName);
									Ext.getCmp("id").setValue(Ext.decode(resp.responseText).result[0].id);
									Ext.getCmp("ts").setValue(Ext.decode(resp.responseText).result[0].ts);
								  	Ext.getCmp("sexnum").setValue(Ext.decode(resp.responseText).result[0].sex);
									Ext.getCmp("birthdaynum").setValue(Ext.decode(resp.responseText).result[0].birthdayType);
								},
								failure : function(response) {
									Ext.Msg.alert(alertTitle,"加载用户的数据失败");
								}
							})
					}
				 }, {
						text : "取消",
						handler : function() {
						   win.close();
						}
					}]
								
		
		});
	
		win.on('hide', function() {
					formUser.destroy();
				});
		
		win.show();
    	
    }
    
    function toAddTabPage(node,flag) {//添加tabpanel标签栏
	        var getTabPage = null;
	        if(node.id==267){  //测试库上是 267
	        	putScan();
	        	return;
	        };
	        if (flag || node.getDepth() > 0) {  //如果不是根节点root
	            var tabItems = tabPanl.items;//获取已经生成的tabpanl
	            var val = null;
	            tabItems.each(function(item) {//对已经生成的tabpanel进行迭代
	                if (item.id == node.id && node.id-96!=0) {//如果为已经生成的tabpanel
	                    val = node.id;
	                    getTabPage = item;
	                    return false;
	                }
	            });
	            //alert(node.id);
	            if (val != null) {//激活已经以生成的tabpanel
	                tabPanl.setActiveTab(getTabPage);
	            } else {
	                var index = tabItems.length;
	                if (index > 18) {
	                    Ext.Msg.alert('提示', '标签数量过多,请关闭不必要的标签项');
	                } else {
	                    var tabPage = tabPanl.add({
	                        title:node.text,
	                        layout:'fit', 
	                        titleCollapse: true ,
	                        id:'keel_'+(node.id-96==0 || node.id-68 == 0?Ext.id():node.id),
	                        autoDestroy:true,
	                        html : '<iframe class="iframe" id="tabIframe_'+node.id+'" frameborder="0"  style="width:100%; height:100%;" src="' +sysPath+"/"+node.attributes.href1+ '"/>',
			                closable : true,
			                listeners : {
				                'beforedestroy' : function(o) {
				                    var _id = o.id.substring(o.id.indexOf('_') + 1);
				                    var el = document.getElementById("tabIframe_" +_id);
				                    if(el){
				                        el.src = 'javascript:false';
				                        try{
				                            var iframe = el.contentWindow;
				                            iframe.document.readyState="complete";
				                            iframe.document.write('');
				                            iframe.close();
				                            if(Ext.isIE){
			                                    CollectGarbage();
			                                }
				                        }catch(e){};
				                       
				                        el.parentNode.removeChild(el);
				                    }
				                    o.html=null;
	                  			  tabPanl.remove(o);
              				  }
			              },

	                        closable:true
	                    });
	                    tabPanl.setActiveTab(tabPage);
	                }
	            }
	        }
	        
	        
	    }
        
            
            
	  	function exportExl(grid){
	        var store=grid.getStore();
	          	
			Ext.Msg.prompt('系统消息',"请输入导出开始行数,默认为0,取消将导出本页,关闭停止导出",function(btn, text){
				if(btn=="ok"){
					var mask=grid.loadMask;
					mask.msg='正在导出XLS，导出最大限制为1万条记录，导出过程中，请不要关闭此页面！';
		            mask.show();
			           
		            Ext.Ajax.request({
						url : store.proxy.url,
						params : Ext.apply(store.baseParams ,{start:text==""?0:text,limit:10000}),
						success : function(resp) {
							try{
								var model=grid.getColumnModel();
								var colNum=model.getColumnCount();
								var showCol=''; 
								
								for(var i=0;i<colNum;i++ ){
									if(!model.isHidden(i) && model.getDataIndex(i)){
										var field=store.fields.map[model.getDataIndex(i)];
										var mapping=field.mapping==null?field.name:field.mapping;
										// alert(model.getColumnHeader(i)+','+mapping);
										showCol+=model.getColumnHeader(i)+"&:"+mapping  +"#$";
									}
								};
								sendNSCommand('exportExl',resp.responseText,showCol);
								resp=null;
								
								
							}catch(e){
								// alert("exception:"+e.message);
							}finally{
								mask.msg='loading........';
								mask.hide();
							//	mask.disable();
							}
						
						}
					});
			            
				}else if(btn="cancel"){
					exportExcel(grid);
				}
			});	
				
	        
	         	 
	         	 
	        
	        } ;
	        
	        function print(code,params){
	        
					   	
					Ext.Msg.confirm('系统消息',"是否需要打印",function(btn, text){
						if(btn=='yes'){
							    mask2=new Ext.LoadMask(Ext.getBody(),{msg:'系统正在读取打印数据，请稍候！'});
				            	mask2.show();
				            	
				            	 Ext.Ajax.request({
				            	 			url : sysPath+'/print/billLadingPrintAction!print.action?modeName='+code,
				            	 		    params : params,  
				            	 		    success : function(resp) {  
				            	 		   		 	try{
				            	 		    			sendNSCommand('getXml',resp.responseText);  
				            	 		    		}catch(e){
				            	 		    			alert("只能在客户端下打印！"+" : "+e.message);
				            	 		    			mask2.hide();
														}
				            	 		    		}  
				            	 		    		
			            	 		    		}); 

			            	}
					});	
					
			}
			
			function putScan(code,params){
	   		 	try{
		    		 sendNSCommand('putScan');  
	    		}catch(e){
	    			alert("需要客户端才能启动！"+" : "+e.message);
	    		}  
			}
			
			function ovemPrint(code,params){
					Ext.Msg.confirm('系统消息',"实配保存成功。是否需要打印？",function(btn, text){
						
						if(btn=='yes'){
							    mask2=new Ext.LoadMask(Ext.getBody(),{msg:'系统正在读取打印数据，请稍候！'});
				            	mask2.show();
				            	
				            	 Ext.Ajax.request({
		            	 			url : sysPath+'/print/billLadingPrintAction!print.action?modeName='+code,
		            	 		    params : params,  
		            	 		    success : function(resp) {  
		            	 		   		 	try{
		            	 		    			sendNSCommand('getXml',resp.responseText);  
		            	 		    		}catch(e){
		            	 		    			alert("只能在客户端下打印！"+" : "+e.message);
		            	 		    			mask2.hide();
										    }
		            	 		    		Ext.Msg.confirm(alertTitle,'是否需要直接打开发车确认页面?',function(btnYes) {
												if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
													var node=new Ext.tree.TreeNode({id:Ext.id(),leaf :false,text:"发车确认"});
				     								node.attributes={href1:'stock/oprOvermemoAction!outCar.action?id='+params.print_overmemoId};
				       								toAddTabPage(node,true);
												}
											});
		            	 		    }
	            	 		    }); 

			            	}else{
			            		Ext.Msg.confirm(alertTitle,'您已经取消打印了，是否需要直接打开发车确认页面?',function(btnYes) {
									if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
										var node=new Ext.tree.TreeNode({id:Ext.id(),leaf :false,text:"发车确认"});
      									node.attributes={href1:'stock/oprOvermemoAction!outCar.action?id='+params.print_overmemoId};
        								toAddTabPage(node,true);
									}
								});
			            	
			            	}
					});	
					
			}
			
			function exportExcel(grid) {
				var vExportContent = grid.getExcelXml(1,false);
			        if (Ext.isIE6 || Ext.isIE7 || Ext.isIE8 || Ext.isSafari || Ext.isSafari2 || Ext.isSafari3) {
			            var fd=Ext.get('frmDummy');
			            if (!fd) {
			                fd=Ext.DomHelper.append(Ext.getBody(),{tag:'form',method:'post',id:'frmDummy',action:'${pageContext.request.contextPath}/exportexcel.jsp', target:'',name:'frmDummy',cls:'x-hidden',cn:[
			                    {tag:'input',name:'exportContent',id:'exportContent',type:'hidden'}
			                ]},true);
			            }
			            fd.child('#exportContent').set({value:vExportContent});
			            fd.dom.submit();
			        } else {
			            document.location = 'data:application/vnd.ms-excel;base64,'+Base64.encode(vExportContent);
			        }
			}
			/*
			function showMessage(title,msg){
				if(title==null||title==''){
					Ext.example.msg('<b>'+alertTitle+'</b>',msg);
				}else{
					Ext.example.msg('<b>'+title+'</b>',msg);
				}
			}
  			*/
  		//常用菜单打开
  		function forWardStore(rowIndex){
  			var dataStore=Ext.StoreMgr.get("commonMenuDataStore");
			
			var node=new Ext.tree.TreeNode({id:dataStore.getAt(rowIndex).get('nodeId'),leaf :false,text:dataStore.getAt(rowIndex).get('name')});
	      		node.attributes={href1:dataStore.getAt(rowIndex).get('href')};
	       		toAddTabPage(node,true);
  		}
  					
  		function commonMenuLoad(){
  			var dataStore=Ext.StoreMgr.get("commonMenuDataStore");
			dataStore.load();
		}
  							    
				      	
  		function openHtml(){
				var node=new Ext.tree.TreeNode({id:'keel',leaf :false,text:"设置常用菜单"});
	      		node.attributes={href1:'sys/sysCommonMenuAction!input.action'};
	       		toAddTabPage(node,true);
		}
  		
  	/*	Ext.EventManager.on(window, 'load', function(){
			 setTimeout(
				 function() {
				//	Ext.get('loading').remove();
				//	Ext.get('loading-mask').fadeOut({remove:true});
					}, 200); 
		});  */
  		
  	</script>
  <script type="text/javascript">
  	function colseflow(){
  		document.getElementById("flowdiv").style.visibility="hidden";//隐藏
  	}
  </script>
  </head>
  <body>
  <div id="north">
  <table width="100%" height="85" border="0" cellpadding="0" cellspacing="0" background="${pageContext.request.contextPath}/imags/top_bg.jpg">
  <tr>
    <td width="725" rowspan="4"><img src="${pageContext.request.contextPath}/imags/top_left.jpg" height="85" /></td>
    <td height="20" align="right" class="top_font_white_user"><div align="right" id="flowdiv" style="visibility:hidden"><span style="font-size:12px" onmouseover="cursor:pointer"><marquee behavior="" onMouseOut="this.start()" onmouseover="this.stop();"><%=user.get("name") %>，您有<span style="color:red" id="spanflownum"></span>个流程需要审批 &nbsp;&nbsp;&nbsp;<a style='font-size:10px' href="#" onclick="colseflow();">关闭</a></marquee></span></div></td>
    <td>&nbsp;</td>
  </tr>

 
  <tr>
  <td  align="right">
	  <table border="0" cellspacing="3" cellpadding="5">
	    <tr>
		    <td  align="right">
		    <span id="configDiv" class="top_font_white_user"></span> 
		    </td>&nbsp;
		     <td>
		    <span id="problem" class="top_font_white_user"></span> 
		    </td>
		    <td>
		    <span id="help" class="top_font_white_user"></span> 
		    </td>
		    <td>
		    <span id="closeDiv" ></span><span class="top_font_white_user"></span></td>
		    <td valign="middle">&nbsp;</td>
		   </tr>
	    </table>
  </td>
  <td>&nbsp;</td>
  </tr>
</table>
  
 </div> 
 
<div id="south" align="left">
	<table class="banner" width="100%">
		<tr>
			<td width="35%"><span class="south_font_blur"><nobr>&nbsp;&nbsp;欢迎您,<%=user.get("name") %>!&nbsp;岗位:<%=user.get("stationName") %>&nbsp;所属部门:<%=user.get("departName") %></nobr></span></td>
			<td width="30%" ><center hidden='true'></center></td>
			<td width="35%"><div align="right"><span class="south_font_blur"><nobr>Copyright?2003-2010 xbwl.cn All rights reserved 新邦物流</nobr></span></div></td>
		</tr>
	</table>
</div>
  
  
 <script type="text/javascript">
 	var failureNum=0;
 	function setFlowNum(){
 		Ext.Ajax.request({
			url:sysPath+"/flow/workFlowstepAction!getstayauditFlow.action",
			params:{
				filter_EQL_receiverId:userId,
				filter_EQL_submiterId:null
			},
			success:function(resp){
				var respText=Ext.decode(resp.responseText);
				var num=respText.resultMap.length;
				if(num>0){
					document.getElementById("flowdiv").style.visibility="visible";//显示
					sendNSCommand("flicker","您有"+num+"条流程需要处理");
				}else{
					document.getElementById("flowdiv").style.visibility="hidden";//隐藏
					sendNSCommand("stopFlicker","");
				}
				document.getElementById('spanflownum').innerText=num+"";
				failureNum=0;
			},failure : function(response, options) {
			    failureNum++;
				if(response.status == 0 && failureNum>=3){
					Ext.Msg.alert(alertTitle,"与服务器连接异常，可能原因是：<br>1.网络不稳定<br>2.服务器已停止运行<br>请尝试退出后重新登录。如果问题依旧存在，请联系系统管理员！");
					Ext.TaskMgr.stop(task);
				}
			}
		});
		
 	}
 	var task={
 		interval: 120000, //runs every 1 sec
        run: function() {
            setFlowNum();
        },
        scope: this
 	}
 	Ext.TaskMgr.start(task);
 	
  /**
 * 显示系统时钟
 
function showTime() {
	var date = new Date();
	var h = date.getHours();
	h = h < 10 ? '0' + h : h;
	var m = date.getMinutes();
	m = m < 10 ? '0' + m : m;
	var s = date.getSeconds();
	s = s < 10 ? '0' + s : s;
	document.getElementById('rTime').innerHTML = h + ":" + m + ":" + s;
}

window.onload = function() {
	setInterval("showTime()", 1000);
}
*/
</script> 
  </body>
</html>

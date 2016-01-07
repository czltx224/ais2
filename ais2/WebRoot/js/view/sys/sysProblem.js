   
   	Ext.onReady(function(){
  		Ext.QuickTips.init();
    	var fields1=['id','problemContent','doProblemName',     
								'doProblemTime',       //	处理时间
								'doProblemResult',   //		处理结果
								'problemStatus',           //		问题状态
								'problemPhotoAddr',    //  问题图片地址
								'createTime',                                  
								'createName',
								'updateTime','problemUrgentRating',
								'updateName','ts',
								'problemScore','problemModeName',            //评分	
								'problemAssignedTime',   //指派时间
								'doProblemMaxTime',        //  处理时限
								'doProblemStatus'];
			    	
			    	var jsonread1= new Ext.data.JsonReader({
			              root:'result',
			              totalProperty:'totalCount'},
			              fields1);
			    	/*
			    	var expander = new Ext.ux.grid.RowExpander({
				   		height:280,
				       tpl : '<div id="f{id}" style="height:280px;width:1126px" ></div>',
				       listeners : {
						'expand' : function(record, body, rowIndex){
							 var id=body.get('id');
					 		 Ext.getCmp(id).setValue(body.get('problemContent'));
						 }
					}
				   });*/
			    	
			    	var expander = new Ext.grid.RowExpander({
						tpl : new Ext.Template(//'<p style=margin-left:70px;><span style=color:Teal;>项目ID</span><br><span>{xmid}</span></p>',
								'<p style=margin-left:70px;><span style=color:Teal;>问题内容</span><br><span>{problemContent}</span></p>',
								'<p style=margin-left:70px;><span style=color:Teal;>处理结果</span><br><span>{doProblemResult}</span></p>'),
						// 设置行双击是否展开
						expandOnDblClick : true
					});
			    	
			    	var problemStore = new Ext.data.Store({
			    			id:'problemStore',
			                proxy: new Ext.data.HttpProxy({url:sysPath+"/sys/sysProblemAction!list.action"}),
			                baseParams:{
			                	limit: pageSize
			                },
			                reader:jsonread1
			        });
			        
			        	//是否录签收
					var statusStore=new Ext.data.SimpleStore({
							 auteLoad:true, //此处设置为自动加载
				  			 data:[['1','新增'],['2','已指派'],['3','已处理'],['4','已评分'],['5','已否决']],  //问题状态 0：删除，1：新增，2：已指派，3：已处理，4：已评分
				   			 fields:["id","name"]
					});
			        
			        //是否录签收
					var noStore=new Ext.data.SimpleStore({
							 auteLoad:true, //此处设置为自动加载
				  			 data:[['2','正常'],['5','否决']], 
				   			 fields:["id","name"]
					});
			        
			    	var oneBar = new Ext.Toolbar({
								id:'onebar',
								    items :  ['&nbsp;',{
								    				text:'<B>新增</B>',
													id : 'total',
													iconCls:'userEdit',
													handler : function(){
															var userForm = new Ext.FormPanel({
																labelAlign : 'right',
																bodyStyle : 'padding:5px 5px 5px',
																id : 'newadd',
																fileUpload : true,
																defaults : {
																	width : 230
																},
																defaultType : 'textfield',
																frame : true,
																labelSeparator : '：',
																items : [{
																	xtype : 'textfield',
																	inputType : 'file',
																	fieldLabel : '问题图片',
																	name : 'oprProblemPhotoAddr',
																	vtype : "imgVtype"
																},{ //problemModeName
																	xtype : 'textfield',
																	fieldLabel : '模块名称',
																	maxLength : 50,
																	allowBlank : false,
																	name : 'sysProblem.problemModeName'
																},{ //problemModeName
																	xtype : 'textarea',
																	fieldLabel : '问题描述',
																	maxLength : 500,
																	allowBlank : false,
																	height : 200,
																	name : 'sysProblem.problemContent'
																}],
														
																buttons : [{
																	text : '保存',
																	iconCls : 'save',
																	handler : function() {
																		if (userForm.form.isValid()) {
																			this.disabled = true; // 只能点击一次
																			userForm.form.doAction('submit', {
																						url : sysPath+'/sys/sysProblemAction!savePhoto.action',
																						method : 'post',
																						waitMsg : '正在保存数据...',
																						success : function(form, action) {
																							popupWin.hide();
																							Ext.Msg.alert("系统消息", "保存成功",function(){
																								problemStore.reload();
																							});
																						},
																						failure : function(form, action) {
																							popupWin.hide();
																							Ext.Msg.alert("系统消息", "保存失败");
																						}
																					});
																		} else {
																			Ext.Msg.alert('输入不规范', "请重新输入");
																		}
																	}
																}, {
																	text : '关闭',
																	handler : function() {
																		popupWin.hide();
																	}
																}]
															});
														
															popupWin = new Ext.Window({
																		title : '新增',
																		layout : 'fit',
																		width : 400,
																		height : 340,
																		closeAction : 'hide',
																		plain : true,
																		modal : true,
																		items : userForm
																	});
															popupWin.on('hide', function() {
																		userForm.destroy();
																	});
															popupWin.show();
													}
												},'-',{
								    				text:'<B>指派</B>',
													id : 'submitbtn5',
													//disabled : true,
													iconCls:'sort_down',
													handler : function(){
														var _records = Ext.getCmp('myrecordGrid1').getSelectionModel().getSelections();
														if(_records.length!=1||_records[0].data.problemStatus!=1){
															Ext.Msg.alert(alertTitle,"请选择一条状态为新增数据，再指派", function() {			
															});
															return;
														}
														var changeStore = new Ext.data.Store({
												            proxy: new Ext.data.HttpProxy({
												            	url:sysPath+"/user/userAction!list.action",
												            	method:'post'
												            }),
												            reader: new Ext.data.JsonReader(
												            	{root: 'result', totalProperty: 'totalCount'
												          		}, [{name:'name', mapping:'userName',type:'string'},        
												               		 {name:'id', mapping:'id'}
												              		])    
															});
														
															//问题紧急程度
														var oprUrgentStore = new Ext.data.Store({ 
													            storeId:"oprUrgentStore",                        
													            proxy: new Ext.data.HttpProxy({url:sysPath+"/sys/basDictionaryAction!ralaList.action",method:'post'}),
													            baseParams:{
													             	privilege:16,
																	itemsValue:285,
													             	checkItems:'EQS_basDictionaryId'
													            },
													            reader: new Ext.data.JsonReader({
													            root: 'result', totalProperty: 'totalCount'
													           }, [  {name:'typeName', mapping:'typeName',type:'string'}           
													              ])    
													    });
														
														var mymodify = new Ext.FormPanel({
															region : 'center',
															width : 400,
															labelAlign : 'right',
															bodyStyle : 'padding:5px 5px 5px',
															id : 'newadd',
															layout : 'form',
															defaults : {
																width : 230
															},
															frame : true,
															labelSeparator : '：',
															url : sysPath+'/sys/sysProblemAction!list.action?filter_EQL_id='
																	+ _records[0].data.id,
															reader :jsonread1,
															items : [{
																	xtype : 'combo',
																	id:'noStatus',
																	fieldLabel : '指派状态<span style="color:red">*</span>',
																	triggerAction : 'all',
																	store : noStore,
																	emptyText : "请选择状态",
																	editable:false,
																	mode : "local",//获取本地的值
																	displayField : 'name',//显示值，与fields对应
																	valueField : 'id',//value值，与fields对应
																	name : 'noStatus',
																	enableKeyEvents:true,
																	listeners : {
																 		 render:function(numberfield, e){
																 		 	numberfield.setValue(2);
																 		 },
																 		 select:function(numberfield, e){
																 		 	if(numberfield.getValue()==2){
																 		 		Ext.getCmp('dicTo1').enable();
																 		 		Ext.getCmp('dic').enable();
																 		 		Ext.getCmp('doProblemMaxTime').enable();
																 		 	}else{
																 		 		Ext.getCmp('dicTo1').disable();
																 		 		Ext.getCmp('dic').disable();
																 		 		Ext.getCmp('doProblemMaxTime').disable();
																 		 	}
																 		 }
																 	}
														       },{xtype : 'combo',
																	id:'dicTo1',
																	triggerAction : 'all',
																	store : changeStore,
																	fieldLabel : '指派处理人<span style="color:red">*</span>',
																	minChars : 1,
																	queryParam:'filter_LIKES_userName',
																	listWidth:245,
																	allowBlank : false,
																	emptyText : "请选择处理人名字",
																	forceSelection : true,
																	editable : true,
																	displayField : 'name',//显示值，与fields对应
																	valueField : 'name',//value值，与fields对应
																	name : 'doProblemName',
																	pageSize:50,
																	enableKeyEvents:true,
														            listeners : {
														 			 
														 			}},{xtype : 'combo',
																	id:'dic',
																	triggerAction : 'all',
																	//mode:'local',
																	fieldLabel : '紧急程度<span style="color:red">*</span>',
																	store : oprUrgentStore,
																	allowBlank : false,
																	emptyText : "请选相应的紧急程度",
																	forceSelection : true,
																	editable : true,
																	displayField : 'typeName',//显示值，与fields对应
																	valueField : 'typeName',//value值，与fields对应
																	name : 'problemUrgentRating'
															
															    },{
																	xtype : 'datefield',
														    		format:'Y-m-d H:i:s',
														    		id:'doProblemMaxTime',
				    												invalidText:'时间格式必须为：××××-××-×× ××:××',
														    		fieldLabel : '处理时限<span style="color:red">*</span>',
														    		name:'doProblemMaxTime',
														    		allowBlank : false,
														    		value:new Date().add(Date.DAY,7),
														    		emptyText : "请选择时间",
														    		listeners : {
													    		    }
																},{ //problemModeName
																	xtype : 'textfield',
																	fieldLabel : '模块名称',
																	maxLength : 50,
																	readOnly:true,
																	allowBlank : false,
																	name : 'problemModeName'
																},{
																	xtype : 'textarea',
																	fieldLabel : '问题描述',
																	maxLength : 500,
																	allowBlank : false,
																	height : 200,
																	name : 'problemContent'
																},{	id:"id",
																	name : "id",
																	xtype : "hidden"
																},{
																	name : "ts",
																	xtype : "hidden"
																},{
																	xtype : 'datefield',
														    		format:'Y-m-d H:i:s',
														    		hidden:true,
														    		name:'kkkk',
														    		id:'problemAssignedTime2',
														    		value:new Date()
																}]
													
														});
															var img1 = new Ext.Panel({
																title : '问题描述图片',
																collapsed:false,
																html : '<center><img id="img1"  width="200" height="200"  style="cursor:hand" ></img><center>',
																cls : 'empty',
																enableKeyEvents:true,
													            listeners : {
													 			 	expand:function(){
													 			 		Ext.Ajax.request({
													 			 			url:sysPath+"/sys/sysProblemAction!showPhoto.action",
													 			 			params:{
													 			 				id:_records[0].data.id
													 			 			},
													 			 			success:function(resp){
																 			 	Ext.get("img1").dom.src = resp.responseText+ _records[0].data.problemPhotoAddr;
																 			 	
																 			 	
													 			 				new Ext.ToolTip({
																					target : 'img1',
																					html : '<img src="' + resp.responseText+ _records[0].data.problemPhotoAddr
																							+ '"/>',
																					title : '问题描述图片原始图',
																					autoScroll : true,
																					autoHide : false,
																					closable : true,
																					draggable : true,
																					// autoWidth:true,
																					// autoHeight:true,
																					allowDomMove : true,
																					defaultAlign : "center"
																				});
													 			 			
													 			 			},
													 			 			failure:function(response){
													 			 				Ext.MessageBox.alert(alertTitle, '载入失败');
													 			 			}
													 			 		});
													 			 	}
													 			}
															});
															
															var pan = new Ext.Panel({
																split : false,
																collapsible : true,
																collapsed : false,
																// collapseMode: 'mini',
																title : '图片显示',
																region : 'east',
																layout : 'accordion',
																width : 200,
																items : [img1]
															});
															
																var bigpanel = new Ext.Panel({
																	frame : true,
																	border : false,
																	layout : 'border',
																	height : 300,
																	bodyStyle : 'padding:5px 5px 5px',
																	labelAlign : "right",
																	buttonAlign : "center",
																	buttons : [{
																		text : '保存',
																		iconCls : 'save',
																		handler : function() {
																			if (mymodify.form.isValid()) {
																				this.disabled = true; // 只能点击一次
																				mymodify.form.doAction('submit', {
																					url : sysPath+'/sys/sysProblemAction!save.action',
																					params : {
																						problemStatus:Ext.getCmp('noStatus').getValue(),
																						privilege:240,
																						problemAssignedTime:Ext.getCmp('problemAssignedTime2').getValue().format('Y-m-d H:i:s')
																					},
																					waitMsg : '正在保存数据...',
																					success : function(form, action) {
																						popupWin.hide();
																						Ext.Msg.alert(alertTitle, "保存成功",
																								function() {
																									problemStore.reload();
																								});
																					},
																					failure : function(form, action) {
																						popupWin.hide();
																						Ext.Msg.alert("系统消息", "保存失败");
																					}
																				});
																				this.disabled = true; // 只能点击一次
																			}
																		}
																	}, {
																		text : '重置',
																		handler : function() {
																				mymodify.load({
																					waitMsg : '正在载入数据...',
																					success : function(_form, action){
																						
																						if( _records[0].data.problemPhotoAddr!=''){
																				 			Ext.Ajax.request({
																			 			 			url:sysPath+"/sys/sysProblemAction!showPhoto.action",
																			 			 			params:{
																			 			 				id:_records[0].data.id
																			 			 			},
																			 			 			success:function(resp){
																						 			 	Ext.get("img1").dom.src = resp.responseText+ _records[0].data.problemPhotoAddr;
																			 			 			},
																			 			 			failure:function(response){
																			 			 				Ext.MessageBox.alert(alertTitle, '载入失败');
																			 			 			}
																			 			 		});
																						}
																	
																					},
																					failure : function(_form, action) {
																						Ext.MessageBox.alert('编辑', '载入失败');
																					}
																				});
																		}
																	}, {
																		text : '关闭',
																		handler : function() {
																			popupWin.hide();
																		}
																	}],
																	items : [mymodify, pan]
													
																});
																
															mymodify.load({
																waitMsg : '正在载入数据...',
																success : function(_form, action){
																	
																	if( _records[0].data.problemPhotoAddr!=''){
															 			Ext.Ajax.request({
														 			 			url:sysPath+"/sys/sysProblemAction!showPhoto.action",
														 			 			params:{
														 			 				id:_records[0].data.id
														 			 			},
														 			 			success:function(resp){
																	 			 	Ext.get("img1").dom.src = resp.responseText+ _records[0].data.problemPhotoAddr;
																	 			 	
														 			 				new Ext.ToolTip({
																						target : 'img1',
																						html : '<img src="' + resp.responseText+ _records[0].data.problemPhotoAddr
																								+ '"/>',
																						title : '问题描述图片原始图',
																						autoScroll : true,
																						autoHide : true,
																						closable : true,
																						draggable : true,
																						autoWidth:true,
																						autoHeight:true,
																						allowDomMove : true//,
																						//defaultAlign : "center"
																					});
														 			 			},
														 			 			failure:function(response){
														 			 				Ext.MessageBox.alert(alertTitle, '载入失败');
														 			 			}
														 			 		});
																	}
												
																},
																failure : function(_form, action) {
																	Ext.MessageBox.alert('编辑', '载入失败');
																}
															});
													popupWin = new Ext.Window({
																title : '指派处理人',
																layout : 'fit',
																width : 600,
																height : 350,
																closeAction : 'hide',
																plain : true,
																modal : true,
																items : bigpanel
															});
													popupWin.on('hide', function() {
																bigpanel.destroy();
															});
													popupWin.show();
													}
												},'-',{
													text:'<B>处理</B>',
													id : 'submitbtn4',
													//disabled : true,
													iconCls:'refresh',
													handler : function (){
														var _records = Ext.getCmp('myrecordGrid1').getSelectionModel().getSelections();
														if(_records.length!=1||_records[0].data.problemStatus!=2){
															Ext.Msg.alert(alertTitle,"请选择一条状态为已指派数据，再进行处理", function() {			
															});
																return;
														}
														
														if(_records[0].data.doProblemName!=userName){
														Ext.Msg.alert(alertTitle,"抱歉！你无权限操作此按钮。", function() {			
														});
															return;
													}
														
															var changeStore = new Ext.data.Store({
													            proxy: new Ext.data.HttpProxy({
													            	url:sysPath+"/user/userAction!list.action",
													            	method:'post'
													            }),
													            reader: new Ext.data.JsonReader(
													            	{root: 'result', totalProperty: 'totalCount'
													          		}, [{name:'name', mapping:'userName',type:'string'},        
													               		 {name:'id', mapping:'id'}
													              		])    
																});
														
														var mymodify = new Ext.FormPanel({
															region : 'center',
															width : 400,
															labelAlign : 'right',
															bodyStyle : 'padding:5px 5px 5px',
															id : 'newadd',
															layout : 'form',
															defaults : {
																width : 230
															},
															frame : true,
															labelSeparator : '：',
															url : sysPath+'/sys/sysProblemAction!list.action?filter_EQL_id='
																	+ _records[0].data.id,
															reader :jsonread1,
															items : [{
																	xtype : 'textarea',
																	fieldLabel : '问题描述',
																	maxLength : 500,
																	disabled:true,
																	allowBlank : false,
																	height : 100,
																	name : 'problemContent'
																},{xtype : 'combo',
																	id:'dicTo1',
																	triggerAction : 'all',
																	store : changeStore,
																	fieldLabel : '指派处理人',
																	minChars : 1,
																	disabled:true,
																	queryParam:'filter_LIKES_userName',
																	listWidth:245,
																	allowBlank : false,
																	emptyText : "请选择处理人名字",
																	forceSelection : true,
																	editable : true,
																	displayField : 'name',//显示值，与fields对应
																	valueField : 'name',//value值，与fields对应
																	name : 'doProblemName',
																	pageSize:50,
																	enableKeyEvents:true,
														            listeners : {
														 			 
														 			}},{
																	xtype : 'datefield',
														    		format:'Y-m-d H:i:s',
														    		disabled:true,
														    		fieldLabel : '指派时间',
														    		name:'problemAssignedTime'
																},{
																	xtype : 'textfield',
																	fieldLabel : '紧急程度',
																	maxLength : 50,
																	disabled:true,
																	allowBlank : false,
																	name : 'problemUrgentRating'
																},{
																	xtype : 'datefield',
														    		format:'Y-m-d H:i:s',
														    		disabled:true,
														    		fieldLabel : '处理时限',
														    		name:'doProblemMaxTime',
														    		listeners : {
													    		    }
																},{
																	xtype : 'textarea',
																	fieldLabel : '处理结果<span style="color:red">*</span>',
																	maxLength : 500,
																	allowBlank : false,
																	height : 100,
																	name : 'doProblemResultString'
																},{	id:"id",
																	name : "id",
																	xtype : "hidden"
																},{
																	name : "ts",
																	xtype : "hidden"
																}]
													
														});
															var img1 = new Ext.Panel({
																title : '问题描述图片',
																collapsed:false,
																html : '<center><img id="img1"  width="200" height="200"  style="cursor:hand" ></img><center>',
																cls : 'empty',
																enableKeyEvents:true,
													            listeners : {
													 			 	expand:function(){
													 			 		Ext.Ajax.request({
													 			 			url:sysPath+"/sys/sysProblemAction!showPhoto.action",
													 			 			params:{
													 			 				id:_records[0].data.id
													 			 			},
													 			 			success:function(resp){
																 			 	Ext.get("img1").dom.src = resp.responseText+ _records[0].data.problemPhotoAddr;
																 			 	
																 			 	
													 			 				new Ext.ToolTip({
																					target : 'img1',
																					html : '<img src="' + resp.responseText+ _records[0].data.problemPhotoAddr
																							+ '"/>',
																					title : '问题描述图片原始图',
																					autoScroll : true,
																					autoHide : false,
																					closable : true,
																					draggable : true,
																					// autoWidth:true,
																					// autoHeight:true,
																					allowDomMove : true,
																					defaultAlign : "center"
																				});
													 			 			
													 			 			},
													 			 			failure:function(response){
													 			 				Ext.MessageBox.alert(alertTitle, '载入失败');
													 			 			}
													 			 		});
													 			 	}
													 			}
															});
															
															var pan = new Ext.Panel({
																split : false,
																collapsible : true,
																collapsed : false,
																// collapseMode: 'mini',
																title : '图片显示',
																region : 'east',
																layout : 'accordion',
																width : 200,
																items : [img1]
															});
															
																var bigpanel = new Ext.Panel({
																	frame : true,
																	border : false,
																	layout : 'border',
																	height : 300,
																	bodyStyle : 'padding:5px 5px 5px',
																	labelAlign : "right",
																	buttonAlign : "center",
																	buttons : [{
																		text : '保存',
																		iconCls : 'save',
																		handler : function() {
																			if (mymodify.form.isValid()) {
																				this.disabled = true; // 只能点击一次
																				mymodify.form.doAction('submit', {
																							url:sysPath+"/sys/sysProblemAction!saveHandle.action",
																							method : 'post',
																							params : {
																								problemStatus:3,
																								privilege:240
																							},
																							waitMsg : '正在保存数据...',
																							success : function(form, action) {
																								popupWin.hide();
																								Ext.Msg.alert("系统消息", "保存成功",
																										function() {
																											problemStore.reload();
																										});
																							},
																							failure : function(form, action) {
																								popupWin.hide();
																								Ext.Msg.alert("系统消息", "保存失败");
																							}
																						});
																			}
																		}
																	}, {
																		text : '重置',
																		handler : function() {
																				mymodify.load({
																					waitMsg : '正在载入数据...',
																					success : function(_form, action){
																						
																						if( _records[0].data.problemPhotoAddr!=''){
																				 			Ext.Ajax.request({
																			 			 			url:sysPath+"/sys/sysProblemAction!showPhoto.action",
																			 			 			params:{
																			 			 				id:_records[0].data.id
																			 			 			},
																			 			 			success:function(resp){
																						 			 	Ext.get("img1").dom.src = resp.responseText+ _records[0].data.problemPhotoAddr;
																			 			 			},
																			 			 			failure:function(response){
																			 			 				Ext.MessageBox.alert(alertTitle, '载入失败');
																			 			 			}
																			 			 		});
																						}
																	
																					},
																					failure : function(_form, action) {
																						Ext.MessageBox.alert('编辑', '载入失败');
																					}
																				});
																		}
																	}, {
																		text : '关闭',
																		handler : function() {
																			popupWin.hide();
																		}
																	}],
																	items : [mymodify, pan]
													
																});
																
															mymodify.load({
																waitMsg : '正在载入数据...',
																success : function(_form, action){
																	
																	if( _records[0].data.problemPhotoAddr!=''){
															 			Ext.Ajax.request({
														 			 			url:sysPath+"/sys/sysProblemAction!showPhoto.action",
														 			 			params:{
														 			 				id:_records[0].data.id
														 			 			},
														 			 			success:function(resp){
																	 			 	Ext.get("img1").dom.src = resp.responseText+ _records[0].data.problemPhotoAddr;
																	 			 	
														 			 				new Ext.ToolTip({
																						target : 'img1',
																						html : '<img src="' + resp.responseText+ _records[0].data.problemPhotoAddr
																								+ '"/>',
																						title : '问题描述图片原始图',
																						autoScroll : true,
																						autoHide : true,
																						closable : true,
																						draggable : true,
																						autoWidth:true,
																						autoHeight:true,
																						allowDomMove : true//,
																						//defaultAlign : "center"
																					});
														 			 			},
														 			 			failure:function(response){
														 			 				Ext.MessageBox.alert(alertTitle, '载入失败');
														 			 			}
														 			 		});
																	}
												
																},
																failure : function(_form, action) {
																	Ext.MessageBox.alert('编辑', '载入失败');
																}
															});
													popupWin = new Ext.Window({
																title : '处理',
																layout : 'fit',
																width : 600,
																height : 415,
																closeAction : 'hide',
																plain : true,
																modal : true,
																items : bigpanel
															});
													popupWin.on('hide', function() {
																bigpanel.destroy();
															});
													popupWin.show();
													}
												},'-',{
													text:'<B>查看</B>',
													id : 'dnoInfo',
													disabled : false,
													iconCls:'userAdd',
													handler : function (){
														var _records = Ext.getCmp('myrecordGrid1').getSelectionModel().getSelections();
														if(_records.length!=1){
															Ext.Msg.alert(alertTitle,"请选择一条数据，再进行查看", function() {			
															});
																return;
														}
														
														var changeStore = new Ext.data.Store({
												            proxy: new Ext.data.HttpProxy({
												            	url:sysPath+"/user/userAction!list.action",
												            	method:'post'
												            }),
												            reader: new Ext.data.JsonReader(
												            	{root: 'result', totalProperty: 'totalCount'
												          		}, [{name:'name', mapping:'userName',type:'string'},        
												               		 {name:'id', mapping:'id'}
												              		])    
															});
														
														var mymodify = new Ext.FormPanel({
															region : 'center',
															width : 400,
															labelAlign : 'right',
															bodyStyle : 'padding:1px 1px 1px',
															id : 'newadd',
															layout : 'form',
															defaults : {
																width : 230
															},
															frame : true,
															labelSeparator : '：',
															url : sysPath+'/sys/sysProblemAction!list.action?filter_EQL_id='
																	+ _records[0].data.id,
															reader :jsonread1,
															items : [{
																	xtype : 'textfield',
																	fieldLabel : '提交人',
																	readOnly:true,
																	name : 'createName'
																}, {fieldLabel: '提交时间',
																    name: 'createTime',
																    readOnly:true,
																    format:'Y-m-d H:i:s',
																    xtype:'datefield'
																},{
																	xtype : 'textfield',
																	fieldLabel : '问题状态',
																	maxLength : 50,
																	id:'pStatus',
																	readOnly:true,
																	name : 'problemStatus'
																},{ //problemModeName
																	xtype : 'textfield',
																	fieldLabel : '模块名称',
																	maxLength : 50,
																	readOnly:true,
																	name : 'problemModeName'
																},{
																	xtype : 'textarea',
																	fieldLabel : '问题描述',
																	maxLength : 500,
																	readOnly:true,
																	height : 100,
																	name : 'problemContent'
																},{xtype : 'combo',
																	id:'dicTo1',
																	triggerAction : 'all',
																	store : changeStore,
																	fieldLabel : '指派处理人',
																	minChars : 1,
																	readOnly:true,
																	queryParam:'filter_LIKES_userName',
																	listWidth:245,
																	emptyText : "请选择处理人名字",
																	forceSelection : true,
																	editable : true,
																	displayField : 'name',//显示值，与fields对应
																	valueField : 'name',//value值，与fields对应
																	name : 'doProblemName',
																	pageSize:50,
																	enableKeyEvents:true,
														            listeners : {
														 			 
														 			}},{
																	xtype : 'datefield',
														    		format:'Y-m-d H:i:s',
														    		readOnly:true,
														    		fieldLabel : '指派时间',
														    		name:'problemAssignedTime'
																},{
																	xtype : 'textfield',
																	fieldLabel : '紧急程度',
																	maxLength : 50,
																	readOnly:true,
																	name : 'problemUrgentRating'
																},{
																	xtype : 'datefield',
														    		format:'Y-m-d H:i:s',
														    		readOnly:true,
														    		fieldLabel : '处理时限',
														    		name:'doProblemMaxTime',
														    		listeners : {
													    		    }
																},{
																	xtype : 'textarea',
																	fieldLabel : '处理结果',
																	maxLength : 500,
																	height : 80,
																	readOnly:true,
																	name : 'doProblemResult'
																},{
																	xtype : 'numberfield',
																	fieldLabel : '评分',
																	maxValue:100,
																	decimalPrecision:0,
																	allowNegative:false,
																	id:'problemScore',
																	readOnly:true,
																	name : 'problemScore'
																},{
												    			xtype:'label',
												    			id:'showMsg',
												    			width:380
												    		},{	id:"id",
																	name : "id",
																	xtype : "hidden"
																},{
																	name : "ts",
																	xtype : "hidden"
																}]
													
														});
															var img1 = new Ext.Panel({
																title : '问题描述图片',
																collapsed:false,
																html : '<center><img id="img1"  width="200" height="200"  style="cursor:hand" ></img><center>',
																cls : 'empty'
															});
															
															var pan = new Ext.Panel({
																split : false,
																collapsible : true,
																collapsed : false,
																// collapseMode: 'mini',
																title : '图片显示',
																region : 'east',
																layout : 'accordion',
																width : 200,
																items : [img1]
															});
															
																var bigpanel = new Ext.Panel({
																	frame : true,
																	border : false,
																	layout : 'border',
																	height : 300,
																	bodyStyle : 'padding:5px 5px 5px',
																	labelAlign : "right",
																	buttonAlign : "center",
																	buttons : [ {
																		text : '重置',
																		handler : function() {
																				mymodify.load({
																					waitMsg : '正在载入数据...',
																					success : function(_form, action){
																						
																						if( _records[0].data.problemPhotoAddr!=''){
																				 			Ext.Ajax.request({
																			 			 			url:sysPath+"/sys/sysProblemAction!showPhoto.action",
																			 			 			params:{
																			 			 				id:_records[0].data.id
																			 			 			},
																			 			 			success:function(resp){
																						 			 	Ext.get("img1").dom.src = resp.responseText+ _records[0].data.problemPhotoAddr;
																						 			 	
																						 			 	var v = Ext.getCmp('pStatus').getValue();
																						 			 	if(v=='0'){
																											Ext.getCmp('pStatus').setValue('已作废');
																										}else if(v=='1'){
																											Ext.getCmp('pStatus').setValue('新增');
																										}else if(v=='2'){
																											Ext.getCmp('pStatus').setValue('已指派');
																										}else if(v=='3'){
																											Ext.getCmp('pStatus').setValue('已处理');
																										}else if(v=='4'){
																											Ext.getCmp('pStatus').setValue('已评分');
																										}else if(v=='5'){
																											Ext.getCmp('pStatus').setValue('已否决');
																										}
																						 			 	

																			 			 			},
																			 			 			failure:function(response){
																			 			 				Ext.MessageBox.alert(alertTitle, '载入失败');
																			 			 			}
																			 			 		});
																						}
																	
																					},
																					failure : function(_form, action) {
																						Ext.MessageBox.alert('编辑', '载入失败');
																					}
																				});
																		}
																	}, {
																		text : '关闭',
																		handler : function() {
																			popupWin.hide();
																		}
																	}],
																	items : [mymodify, pan]
													
																});
																
															mymodify.load({
																waitMsg : '正在载入数据...',
																success : function(_form, action){
																	
																	if( _records[0].data.problemPhotoAddr!=''){
															 			Ext.Ajax.request({
														 			 			url:sysPath+"/sys/sysProblemAction!showPhoto.action",
														 			 			params:{
														 			 				id:_records[0].data.id
														 			 			},
														 			 			success:function(resp){
																	 			 	Ext.get("img1").dom.src = resp.responseText+ _records[0].data.problemPhotoAddr;
																	 			 	
																	 			 	var v = Ext.getCmp('pStatus').getValue();
																	 			 	if(v=='0'){
																						Ext.getCmp('pStatus').setValue('已作废');
																					}else if(v=='1'){
																						Ext.getCmp('pStatus').setValue('新增');
																					}else if(v=='2'){
																						Ext.getCmp('pStatus').setValue('已指派');
																					}else if(v=='3'){
																						Ext.getCmp('pStatus').setValue('已处理');
																					}else if(v=='4'){
																						Ext.getCmp('pStatus').setValue('已评分');
																					}else if(v=='5'){
																						Ext.getCmp('pStatus').setValue('已否决');
																					}
																	 			 	

														 			 				new Ext.ToolTip({
																						target : 'img1',
																						html : '<img src="' + resp.responseText+ _records[0].data.problemPhotoAddr
																								+ '"/>',
																						title : '问题描述图片原始图',
																						autoScroll : true,
																						autoHide : true,
																						closable : true,
																						draggable : true,
																						autoWidth:true,
																						autoHeight:true,
																						allowDomMove : true//,
																						//defaultAlign : "center"
																					});
														 			 			},
														 			 			failure:function(response){
														 			 				Ext.MessageBox.alert(alertTitle, '载入失败');
														 			 			}
														 			 		});
																	}
												
																},
																failure : function(_form, action) {
																	Ext.MessageBox.alert('编辑', '载入失败');
																}
															});
												
													popupWin = new Ext.Window({
																title : '查看',
																layout : 'fit',
																width : 600,
																height : 530,
																closeAction : 'hide',
																plain : true,
																modal : true,
																items : bigpanel
															});
													popupWin.on('hide', function() {
																bigpanel.destroy();
															});
													popupWin.show();
													}
												},'-',{
													text:'<B>评分</B>',
													id : 'pingFen',
													iconCls:'groupEdit',
													handler : function (){
														var _records = Ext.getCmp('myrecordGrid1').getSelectionModel().getSelections();
														if(_records.length!=1){
															Ext.Msg.alert(alertTitle,"请选择一条数据，再进行评分");
															return;
														}
														
														if(_records[0].data.problemStatus!=3){
															Ext.Msg.alert(alertTitle,"只能状态为已处理的数据才能进行评分");
															return;
														}
														
														if(userName!=_records[0].data.createName){
															Ext.Msg.alert(alertTitle,"只有该问题提交人才能评分，并且每个问题只能评一次分");
															return;
														}
														var changeStore = new Ext.data.Store({
												            proxy: new Ext.data.HttpProxy({
												            	url:sysPath+"/user/userAction!list.action",
												            	method:'post'
												            }),
												            reader: new Ext.data.JsonReader(
												            	{root: 'result', totalProperty: 'totalCount'
												          		}, [{name:'name', mapping:'userName',type:'string'},        
												               		 {name:'id', mapping:'id'}
												              		])    
															});
														
														var mymodify = new Ext.FormPanel({
															region : 'center',
															width : 420,
															labelAlign : 'right',
															bodyStyle : 'padding:5px 5px 5px',
															id : 'newadd',
															layout : 'form',
															defaults : {
																width : 230
															},
															
															
															frame : false,
															labelSeparator : '：',
															url : sysPath+'/sys/sysProblemAction!list.action?filter_EQL_id='+ _records[0].data.id,
															reader :jsonread1,
															html : "<center>"+
				"<div style='width:200;text-align:left;float: left;'><h1 style='font-size:150%'>&nbsp;&nbsp;请对处理结果评分<span style='color:red'>*</span>：</h1></div></br><div class='user_rate'>"+
				"<div class='big_rate_bak'>"
		       +"<b rate='2' onclick='javascript:up_rate(20);' title='很差'> </b>"
		       +"<b rate='4' onclick='javascript:up_rate(40);' title='不满意'> </b>"
		       +"<b rate='6' onclick='javascript:up_rate(60);' title='一般'> </b>"
		       +"<b rate='8' onclick='javascript:up_rate(80);' title='满意'> </b>"
		       +"<b rate='10' onclick='javascript:up_rate(100);' title='非常好'> </b>"
		       +"<div style='width:45px;' id='big_rate_up' class='big_rate_up'></div>"
			   +"</div><p><span id='s' class='s'></span><span id='g' class='g'></span></p>"
			   +"<div style='width:150px; float: left;margin-left: 220px; '><span id='rlt' ></span></div>"
			   +"<div style='width:120px; float: left;margin-left: 10px;'><a href='javascript:showArea();' onclick='showArea();'><center>",
															cls : 'empty',
															items : [{
																	xtype : 'textfield',
																	fieldLabel : '提交人',
																	readOnly:true,
																	name : 'createName'
																},{ //problemModeName
																	xtype : 'textfield',
																	fieldLabel : '模块名称',
																	maxLength : 50,
																	readOnly:true,
																	name : 'problemModeName'
																},{
																	xtype : 'textarea',
																	fieldLabel : '问题描述',
																	maxLength : 500,
																	readOnly:true,
																	height : 100,
																	name : 'problemContent'
																},{xtype : 'combo',
																	id:'dicTo1',
																	triggerAction : 'all',
																	store : changeStore,
																	fieldLabel : '处理人',
																	minChars : 1,
																	readOnly:true,
																	queryParam:'filter_LIKES_userName',
																	listWidth:245,
																	emptyText : "请选择处理人名字",
																	forceSelection : true,
																	editable : true,
																	displayField : 'name',//显示值，与fields对应
																	valueField : 'name',//value值，与fields对应
																	name : 'doProblemName',
																	pageSize:50,
																	enableKeyEvents:true,
														            listeners : {
														 			 
														 		}},{
																	xtype : 'textarea',
																	fieldLabel : '处理结果',
																	maxLength : 500,
																	height : 80,
																	readOnly:true,
																	name : 'doProblemResult'
																},{
												    			xtype:'label',
												    			id:'showMsg',
												    			width:380
												    		},{	id:"id",
																	name : "id",
																	xtype : "hidden"
																},{	id:"fen",
																	name : "problemScore",
																	xtype : "hidden"
																},{
																	name : "ts",
																	xtype : "hidden"
																}]
													
														});
															
																var bigpanel = new Ext.Panel({
																	frame : false,
																	border : false,
																	layout : 'border',
																	height : 420,
																	bodyStyle : 'padding:5px 5px 5px',
																	labelAlign : "right",
																	buttonAlign : "center",
																	buttons : [{
																		text : '评分',
																		iconCls : 'save',id:'save',
																		handler : function() {
																			if (mymodify.form.isValid()) {
																				this.disabled = true; // 只能点击一次
																				mymodify.form.doAction('submit', {
																							url:sysPath+"/sys/sysProblemAction!save.action",
																							method : 'post',
																							params : {
																								problemStatus:4,
																								privilege:240
																							},
																							waitMsg : '正在保存数据...',
																							success : function(form, action) {
																								popupWin.hide();
																								Ext.Msg.alert(alertTitle, "评分保存成功，感谢您对AIS2系统的支持！",
																										function() {
																											problemStore.reload();
																										});
																							},
																							failure : function(form, action) {
																								popupWin.hide();
																								Ext.Msg.alert(alertTitle, "保存失败");
																							}
																						});
																			}
																		}
																	}, {
																		text : '重置',
																		handler : function() {
																				mymodify.load({
																					waitMsg : '正在载入数据...',
																					success : function(_form, action){
																					},
																					failure : function(_form, action) {
																						Ext.MessageBox.alert('编辑', '载入失败');
																					}
																				});
																		}
																	}, {
																		text : '关闭',
																		handler : function() {
																			popupWin.hide();
																		}
																	}],
																	items : [mymodify]
													
																});
																
															mymodify.load({
																waitMsg : '正在载入数据...',
																success : function(_form, action){
																	
																	if( _records[0].data.problemPhotoAddr!=''){
															 			Ext.Ajax.request({
														 			 			url:sysPath+"/sys/sysProblemAction!showPhoto.action",
														 			 			params:{
														 			 				id:_records[0].data.id
														 			 			},
														 			 			success:function(resp){

														 			 			},
														 			 			failure:function(response){
														 			 				Ext.MessageBox.alert(alertTitle, '载入失败');
														 			 			}
														 			 		});
																	}
												
																},
																failure : function(_form, action) {
																	Ext.MessageBox.alert('编辑', '载入失败');
																}
															});
												
													popupWin = new Ext.Window({
																title : '评分',
																layout : 'fit',
																width : 420,
																labelAlign : 'center',
																height : 430,
																closeAction : 'hide',
																plain : true,
																modal : true,
																items : bigpanel
															});
													popupWin.on('hide', function() {
																bigpanel.destroy();
															});
													popupWin.show();
												}
						},'-',{
							text:'<B>查询</B>',
							id : 'select',
							iconCls:'btnSearch',
							handler : function (){
									query();	
	
							}
						},'&nbsp;&nbsp;',{
		    			xtype:'label',
		    			id:'showMsg',
		    			width:200
		    		}
					]
							
							
				});
					
			  function query(){
			  	   var startDate=Ext.get('startDate').dom.value;
				    var endDate=Ext.get('endDate').dom.value;
				
				    Ext.apply(problemStore.baseParams={
							filter_GED_createTime : startDate,
							filter_LED_createTime : endDate,
							filter_EQL_problemStatus:Ext.getCmp('signStatus').getValue(),
							 filter_LIKES_createName:Ext.getCmp('pName').getValue(),
				             filter_LIKES_doProblemName:Ext.getCmp('sName').getValue(),
							limit : pageSize
							
				    });
				    
					problemStore.reload({
						params : {
							start : 0,
							limit : pageSize
						}
					});
			  }
					
						// 查询工具条
			 	var twobar = new Ext.Toolbar({
						id:'twobar',
			    	items : ['&nbsp;','创建时间', {
					xtype : 'datefield',
					format : 'Y-m-d',
					selectOnFocus:true,
					width : 80,
					blankText : "录入日期不能为空！",
					name:'startDate',
					enableKeyEvents:true,
					listeners : {
				 		 keyup:function(numberfield, e){
				             if(e.getKey() == 13 ){
								 query();	
				              }
				 		 }
				 	},
					id : 'startDate'
				},'&nbsp;', '至','&nbsp;', {
					xtype : 'datefield',
					format : 'Y-m-d',
					selectOnFocus:true,
					width : 80,
					value : new Date(),
					blankText : "录入日期不能为空！",
					name:'endDate',
					id : 'endDate',
					enableKeyEvents:true,
					listeners : {
				 		 keyup:function(numberfield, e){
				             if(e.getKey() == 13 ){
								 query();	
				              }
				 		 }
				 	}
				},'-','状态:',
				   {
					xtype : 'combo',
					id:'signStatus',
					triggerAction : 'all',
					store : statusStore,
					emptyText : "请选择状态",
					editable:true,
					width : 80,
					mode : "local",//获取本地的值
					displayField : 'name',//显示值，与fields对应
					valueField : 'id',//value值，与fields对应
					name : 'name',
					enableKeyEvents:true,
					listeners : {
				 		 keyup:function(numberfield, e){
				             if(e.getKey() == 13 ){
								 query();	
				              }
				 		 }
				 	}
		       }
				
				,'-','提交人:',
		          {xtype : 'textfield',
		  	       id:'pName',
		    	   maxLength : 10,
		    	   enableKeyEvents:true,
					listeners : {
				 		 keyup:function(numberfield, e){
				             if(e.getKey() == 13 ){
								 query();	
				              }
				 		 }
				 	},
		           width:60}	
			     ,'-','处理人:',
			     {xtype : 'textfield',
 	              id:'sName',
   			      maxLength : 10,
   			      enableKeyEvents:true,
				  listeners : {
				 		 keyup:function(numberfield, e){
				             if(e.getKey() == 13 ){
								 query();	
				              }
				 		 }
				 	},
			      width:60}]
				});
					
			    	var addArriveRecord = new Ext.grid.GridPanel({
			    				region : "center",
			    				renderTo:Ext.getBody(),
			    				id : 'myrecordGrid1',
			    				height : Ext.lib.Dom.getViewHeight()-1,
								width : Ext.lib.Dom.getViewWidth()-1,
			    				border:true,
			    				autoScroll : true,
			    				//autoSizeColumns: true,
			    				frame : false,
			    				loadMask : true,
			    				stripeRows : true,
			    			//	iconCls:'gjQuery',
								layout:'fit',
								autoWidth:true,
								plugins : [expander],
			    				viewConfig:{
			    				  // forceFit: true,
			    				   scrollOffset: 0
			    				},
			    				tbar:oneBar,
			    				listeners: {
				                    render: function(){
				                         twobar.render(this.tbar);
				                    }
					            },
			    				cm : new Ext.grid.ColumnModel([expander,new Ext.grid.CheckboxSelectionModel({
											width:25,
											singleSelect: true
										}),
			    						{header : 'ID',dataIndex:'id',width:35,hidden:true}, 
			    						new Ext.grid.RowNumberer({
											header : '序号',
											width : 35,
											sortable : true
										}),
			    						{header: '提交人', dataIndex: 'createName',width:60,renderer:function(v){
													if(v==userName){
													 	return '<span style="color:red" >'+v+'</span>';
													}else{
														return v;
													}
											}},
										{header: '提交时间', dataIndex: 'createTime',width:110,sortable : true},
										{header: '模块名称', dataIndex: 'problemModeName',width:85,sortable : true},
			    						{header : '问题内容',dataIndex : 'problemContent',width:150}, 
			    						{header : '状态',dataIndex : 'problemStatus',width:60,renderer:function(v){
													if(v=='0'){
														return '已作废';
													}else if(v=='1'){
														return '<span style="color:green" >新增</span>';
													}else if(v=='2'){
														return '<span style="color:green" >已指派</span>';
													}else if(v=='3'){
														return '<span style="color:green" >已处理</span>';
													}else if(v=='4'){
														return '<span style="color:green" >已评分</span>';
													}else if(v=='5'){
														return '<span style="color:red" >已否决</span>';
													}
											}}, 
			    						{header : '指派时间',dataIndex : 'problemAssignedTime',width:110},
			    						{header : '紧急程度',dataIndex : 'problemUrgentRating',width:65,renderer:function(v){
													if(v=='紧急'){
													 	return '<span style="color:red" ><B>'+v+'</B></span>';
													}else{
														return v;
													}
													
											}}, 
			    						{header : '处理人',dataIndex : 'doProblemName',width:80,renderer:function(v){
													if(v==userName){
													 	return '<span style="color:red" >'+v+'</span>';
													}else{
														return v;
													}
													
											}}, 
			    						{header : '处理结果',dataIndex : 'doProblemResult',width:120}, 
			    						{header : '评分',dataIndex : 'problemScore',width:60}, 
			    						{header : '处理要求时限',dataIndex : 'doProblemMaxTime',width:110,renderer:function(v){
													if(v<=new Date().format('Y-m-d H:i:s')){
														return '<span style="color:red" >'+v+'</span>';
													}else{
														return v;
													}
													
											}}, 
			    						{header : '处理及时状态',dataIndex : 'doProblemStatus',width:60,renderer:function(v){
													if(v=='0'){
														return '<span style="color:red" >不及时</span>';
													}else if(v=='1'){
														return '<span style="color:green" >及时</span>';
													}else{
														return '未处理';
													}
											}}, 
			    						{header : '处理时间',dataIndex : 'doProblemTime',width:110},
			    						{header : '图片地址',dataIndex : 'problemPhotoAddr',hidden : true,width:80}
			    						
			    				]),
			    			
			    				ds : problemStore,
			    				bbar : new Ext.PagingToolbar({
									pageSize : pageSize, 
									store : problemStore,
									displayInfo : true,
									displayMsg : '显示第 {0} 条到 {1} 条记录，共 {2} 条',
									emptyMsg : "没有记录信息显示"
								})
			    			});
			    	   
			});
			
			function up_rate(rate){
				//	$(".big_rate_up").width("0");
				//	Ext.fly('elId').width('0');    
					Ext.get("big_rate_up").applyStyles({height: "0"});  
					//Ext.query("*[class=big_rate_up]").applyStyles({height: "0"});  
					get_rate(rate);
				}
			
																	
				function get_rate(rate){
					if(rate=="20"){
						Ext.get("rlt").update("<span style='color:red'>您对处理结果很不满意</span>")
					}else if(rate=="40"){
						Ext.get("rlt").update("<span style='color:red'>您对处理结果不满意</span>")
					}else if(rate=="60"){
						Ext.get("rlt").update("<span style='color:red'>您对处理结果一般</span>")
					}else if(rate=="80"){
						Ext.get("rlt").update("<span style='color:red'>您对处理结果满意</span>")
					}else{
						Ext.get("rlt").update("<span style='color:red'>您对处理结果非常满意</span>")
					}
					
					Ext.getCmp('fen').setValue(rate);
					
					rate = rate.toString();
					var s;
					var g;
				//	Ext.get("rlt").show();
					if (rate.length >= 3){
						s = 10;	
						g = 0;
						Ext.get("g").hide();
					}else if(rate == "0"){
						s = 0;
						g = 0;
					}else{
						s = rate.substr(0,1);
						g = rate.substr(1,1);
					}
				//	Ext.get("s").update("跟踪信息全部准确")
				//	Ext.get("g").update("跟踪信息全部准确")
				//	$("#s").text(s);
				//	$("#g").text("."+ g);
					Ext.get("big_rate_up").applyStyles({width:(parseInt(s) + parseInt(g) / 10) * 14,height:26}); 
					//$(".big_rate_up").animate({width:(parseInt(s) + parseInt(g) / 10) * 14,height:26},1000);
				  /*	$(".big_rate_bak b").each(function(){
						$(this).mouseover(function(){
							$(".big_rate_up").width($(this).attr("rate") * 14 );
							$("#s").text($(this).attr("rate"));
							$("#g").text("");
							
							var rlt = $(this).attr("rate");
							if(rlt == '2') {$("#rlt").text("跟踪信息极不准确")}
							else if(rlt == '4') {$("#rlt").text("跟踪信息不准确")}
						    else if(rlt == '6') {$("#rlt").text("跟踪信息部分准确")}
							else if(rlt == '8') {$("#rlt").text("跟踪信息大部分准确")}
							else {$("#rlt").text("跟踪信息全部准确")}
						}).click(function(){
							$("#f").text($(this).attr("rate"));
							$("#my_rate").show();
						})
					})
					$(".big_rate_bak").mouseout(function(){
						$("#s").text(s);
						$("#g").text("." + g);
						$(".big_rate_up").width((parseInt(s) + parseInt(g) / 10) * 14);
					})*/
				}
			
			
			
			
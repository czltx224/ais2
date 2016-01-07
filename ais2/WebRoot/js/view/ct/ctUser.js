	Ext.QuickTips.init();
	var privilege=269; //265
	var comboxPage=comboSize;
	var saveUrl="ct/ctUserAction!saveCtUser.action";
	var ralaListUrl="ct/ctUserAction!findCtUser.action";
	
	localAuthority=new Ext.data.SimpleStore({
			 autoLoad:true, //此处设置为自动加载
  			  data:[['1','有'],['0','无']],
   			 fields:["propertyId","propertyName"]
	});
Ext.onReady(function(){
	Ext.QuickTips.init();
	var rownum=new Ext.grid.RowNumberer({
		header:'序号',
        width : 35,
		sortable : true	
	});

 
	var fields=[{name:"id",mapping:'ID'},
				{name:"userId",mapping:'USER_ID'},
				{name:"userName",mapping:'USER_NAME'},
				{name:"userPassword",mapping:'USER_PASSWORD'},
				{name:"status",mapping:'STATUS'},
				{name:"phone",mapping:'PHONE'},
				{name:"address",mapping:'ADDRESS'},
				{name:"contact",mapping:'CONTACT'},
				{name:"remark",mapping:'REMARK'},
				{name:"free1",mapping:'FREE1'}];
	
	var jsonread= new Ext.data.JsonReader({
                    root:'resultMap',
                    totalProperty:'totalCount'},
                    fields);
	var sm = new Ext.grid.CheckboxSelectionModel();
	
	
	var form1 = new Ext.form.FormPanel({
				id : 'form1',
				frame : true,
				width : 100,
				cls : 'displaynone',
				hidden : true,
				items : [],
				buttons : []
			});
	form1.render(document.body);
	
	
	var dataStore = new Ext.data.Store({
        storeId:"dataStore",
        proxy: new Ext.data.HttpProxy({url:sysPath+"/"+ralaListUrl}),
        baseParams:{privilege:privilege},
        reader:jsonread
    });
	
	var customerStore = new Ext.data.Store({
		storeId : "customerStore",
		proxy : new Ext.data.HttpProxy({url : sysPath + "/sys/customerAction!list.action",method:'post'}),
		baseParams:{
			filter_EQS_custprop:'中转',
			filter_EQL_status:1},
		reader:new Ext.data.JsonReader({
					root : 'result',
					totalProperty : 'totalCount'
				}, [{name : 'name',mapping : 'cusName'
					},{name : 'id',mapping : 'id'}])
	});
     
	var recordGrid=new Ext.grid.GridPanel({
	//	renderTo:'recordGrid',
		renderTo:Ext.getBody(),
		id:'userCenter',
		height : Ext.lib.Dom.getViewHeight()-1,
		width : Ext.lib.Dom.getViewWidth()-1,
		//autoScroll : true,
		viewConfig : {
			scrollOffset: 0,
			columnsText : "显示的列",
			sortAscText : "升序",
			sortDescText : "降序",
			//forceFit : true,
			autoScroll:true
		},
	//	autoExpandColumn : 1,
		frame : true,
		loadMask : true,
		sm:sm,
		stripeRows : true,
		tbar:[{text:'<b>导出</b>',iconCls:'sort_down',handler:function() {
                	parent.exportExl(recordGrid);
        } },'-','&nbsp;&nbsp;',{
				text:'<B>添加</B>',
				iconCls : 'userAdd',
				id:'addbtn',
				handler : function() {
						updateUser(null);
				}
			}, '-',{
				text : '<B>修改</B>',
				id : 'updatebtn',
				iconCls:'userEdit',
				handler : function() {
						var _records = recordGrid.getSelectionModel().getSelections();
						if (_records.length == 0) {
							Ext.Msg.alert('系统消息', '请选择一条您需要修改的数据');
						} else if (_records.length > 1) {
							Ext.Msg.alert('系统消息','修改数据只能选择一数据');
						} else {
							updateUser(_records[0]);
						}
				}
			},'-',{
				text:'<b>删除</b>',
				id : 'deletebtn',
			 	iconCls:'userDelete',
			 	handler : function(){
			 		var _records = recordGrid.getSelectionModel().getSelections();
					if (_records.length == 0) {
						Ext.Msg.alert('系统消息', '请选择一条您需要修改的数据');
					} else if (_records.length > 1) {
						Ext.Msg.alert('系统消息','修改数据只能选择一数据');
					} else {
						deleteUser(_records[0].data.id);
					}
				} 
			},{
				xtype:'textfield',
	 	        id : 'itemsValue',
		        name : 'itemsValue',
	            blankText:'查询用户数据',
	         //   disabled:true,
	            enableKeyEvents:true,
	            listeners : {
	 				keyup:function(textField, e){
	                     if(e.getKey() == 13){
	                     	searchLog();
	                     }
	 				}
	 			}
			},'-',{
                xtype : "combo",
    			id:"comboselect",
   				width : 100,
 				triggerAction : 'all',
    			model : 'local',
    			hiddenId : 'checkItems',
    			hiddenName : 'checkItems',
    			name : 'checkItemstext',
    			store : [['', '查询全部'],
    					 ['LIKES_userName','用户名'],	
    					 ['LIKES_phone','联系方式'],
    					 ['LIKES_contact','联系人'],
    					 ['LIKES_address','地址'],
    					 ['LIKES_remark','备注']		
    					],
    					emptyText : '选择查询类型',
    					forceSelection : true,
    					listeners : {
    						'select' : function(combo, record, index) { 
    							
    					
    						}
    					}
    				},'-',{
    				     text : '<b>查询</b>',
    				     id : 'btn',
    				     iconCls : 'btnSearch',
    					 handler : searchLog
    				}
			],
	
			columns:[rownum,sm,
					{header: 'id', dataIndex: 'id',	hidden : true,sortable : true},
        		    {header: '用户账号' , dataIndex: 'userId',width:60,sortable : true},
        			{header: '用户名' , dataIndex: 'userName',width:150,sortable : true},
			        {header: '用户密码'   , dataIndex: 'userPassword',width:150,sortable : true},
			        {header: '联系方式' , dataIndex: 'phone',width:120,sortable : true},
    				{header: '地址' , dataIndex: 'address',width:200,sortable : true},
    			    {header: '联系人' , dataIndex: 'contact',width:100,sortable : true},
    			    {header: '财务-打印权限' , dataIndex: 'free1',width:100,sortable : true,align:'center' 
    			    	,renderer:function(v){
    			    		return v==1?'有':'无';
    			    	}
    			    },
    			    {header: '备注', dataIndex: 'remark',width:200,sortable : true,sortable : true}],
    			    
			store : dataStore,
			bbar : new Ext.PagingToolbar({
					pageSize : pageSize, 
					store : dataStore,
					displayInfo : true,
					displayMsg : '显示第 {0} 条到 {1} 条记录，共 {2} 条',
					emptyMsg : "没有记录信息显示"
			})
	});

		function searchLog() {
			Ext.apply(dataStore.baseParams, {
 				checkItems : Ext.getCmp('comboselect').getValue(),
				itemsValue : Ext.get("itemsValue").dom.value
 			});

			dataStore.reload({
				params : {start : 0,limit : pageSize}
			});
		}
		
		recordGrid.on('click', function() {

		});

		recordGrid.on('rowdblclick',function(grid,index,e){
		        var _records = recordGrid.getSelectionModel().getSelections();
				if (_records.length ==1) {
					updateUser(_records[0]);
				}
			 	
		});
		
	function deleteUser(id){
	     Ext.Msg.confirm(alertTitle,'数据删除后将不可恢复，您确定要删除数据吗?',function(btnYes) {
			if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) { 	
			form1.getForm().doAction('submit', {
				url : sysPath+ "/ct/ctUserAction!deleteCtUser.action",
				method : 'post',
				params : {
					ids : id,
					privilege:privilege
				},
				waitMsg : '正在删除数据...',
				success : function(form1, action) {
					Ext.Msg.alert(alertTitle,"数据删除成功",
							function() {dataStore.reload();});
				},
				failure : function(form1, action) {
					Ext.Msg.alert(alertTitle,"数据删除失败",
							function() {dataStore.reload();});
				}
			});
			}
		});
	}
	
	
	
	function updateUser(_record) {
					 var form = new Ext.form.FormPanel({
								labelAlign : 'left',
								frame : true,
							//	url:sysPath+'/user/userAction!ralaList.action?privilege=17',
							
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
																},{	id:"status",
																	name : "status",
																	xtype : "hidden"
																},{
																   xtype : 'combo',
										        				   fieldLabel: '用户名<span style="color:red">*</span>',
														           allowBlank : false,
														           typeAhead:false,
															       minChars : 1,
															       forceSelection : true,
															       //listWidth:245,
															       id:'ctUserName',
															       name:'usppp',
															       triggerAction : 'all',
																   store: customerStore,
																   pageSize : comboxPage,
																   queryParam : 'filter_LIKES_cusName',
																   displayField : 'name',
																   valueField : 'id',
																   anchor : '95%',
																   listeners : {
																		 select:function(combo){
																		 	Ext.getCmp('userId').setValue(combo.getValue());
																		 },
																		 render:function(combo){
																		 	if(_record!=null){
																		 		combo.disable();
																		 	}
																		 }
																 	}
										        				},{
																	xtype : 'textfield',
																	labelAlign : 'left',
																	fieldLabel : '密码<span style="color:red">*</span>',
																	name : 'userPassword',
																	maxLength:100,
																	allowBlank : false,
																	blankText : "密码不能为空！",
																	anchor : '95%'
																},{
																	xtype : 'textfield',
																	labelAlign : 'left',
																	fieldLabel : '联系电话<span style="color:red">*</span>',
																	name : 'phone',
																	maxLength:100,
																	allowBlank : false,
																	blankText : "联系电话不能为空！",
																	anchor : '95%'
																},{
																   xtype : 'combo',
										        				   fieldLabel: '财务权限<span style="color:red">*</span>',
														           allowBlank : false,
															       forceSelection : true,
															       mode:'local',
															       hiddenName:'free1',
															       triggerAction : 'all',
																   store: localAuthority,
																   displayField : 'propertyName',
																   valueField : 'propertyId',
																   anchor : '95%'
										        				}]

													}, {
														columnWidth : .5,
														layout : 'form',
														items : [ {	
																	xtype : 'numberfield',
																	id:"userId",
																	name : "userId",
																	fieldLabel : '用户账号<span style="color:red">*</span>',
																	allowBlank : false,
																	blankText : "用户账号不能为空！",
																	anchor : '95%'
																},{
																	xtype : 'textfield',
																	labelAlign : 'left',
																	fieldLabel : '联系人<span style="color:red">*</span>',
																	name : 'contact',
																	maxLength:30,
																	allowBlank : false,
																	blankText : "联系人不能为空！",
																	anchor : '95%'
																},{
																	xtype : 'textfield',
																	labelAlign : 'left',
																	fieldLabel : '地址<span style="color:red">*</span>',
																	name : 'address',
																	maxLength:100,
																	allowBlank : false,
																	blankText : "地址不能为空！",
																	anchor : '95%'
																}]
													}]
													
										},{
											labelAlign : 'top',
											xtype : 'textarea',
											name : 'remark',
											maxLength:500,
											fieldLabel : '备注',
											height : 50,
											width:'95%'
										}]
										});
		
							
		carTitle='增加用户信息';
		if(_record!=null){
			carTitle='修改用户信息';
			form.load({
				url : sysPath+ "/"+ralaListUrl,
				params:{filter_EQL_id: _record.data.id,limit : pageSize},
				success : function(response){
					Ext.getCmp('ctUserName').setRawValue(_record.data.userName);
				}
			})
		}
			
		var win = new Ext.Window({
			title : carTitle,
			width : 600,
			closeAction : 'hide',
			plain : true,
		    resizable : false,	
			modal : true,
			items : form,
			buttonAlign : "center",	buttons : [{
				text : "保存",
				iconCls : 'groupSave',
				handler : function() {
					if (form.getForm().isValid()) {
						this.disabled = true;//只能点击一次
						form.getForm().submit({
							url : sysPath+ '/'+saveUrl,
							params:{
								userName:Ext.getCmp('ctUserName').getRawValue(),
								privilege:privilege},
							waitMsg : '正在保存数据...',
							success : function(form, action) {
										win.hide(), 
										Ext.Msg.alert(alertTitle,action.result.msg, function() {
												reload();
										});
							},
							failure : function(form, action) {
								this.disabled = false;//只能点击一次
								if (action.faliureType == Ext.form.Action.SERVER_INVALID) {
									Ext.Msg.alert(alertTitle, "服务器连接异常，请联系管理员！");
								} else {
									if (!action.result.success) {
										Ext.Msg.alert(alertTitle,action.result.msg);
	
									}
								}
							}
						});
						this.disabled = false;//只能点击一次
					}
				}
			}, {
				text : "重置",
				iconCls : 'refresh',
				handler : function() {		
						if(_record==null){
							form.getForm().reset();
						}else{
							carTitle='修改用户信息';
							form.load({
							    url : sysPath+"/"+ralaListUrl,
							    params:{filter_EQL_id:_record.data.id,privilege:privilege,limit : pageSize},
							    success : function(response){
									Ext.getCmp('ctUserName').setRawValue(_record.data.userName);
								}
						   });
		             	}
					}
				 }, {
						text : "取消",
						handler : function() {
						   win.close();
						}
					}]
								
		
		});
	
		win.on('hide', function() {
					form.destroy();
				});
		
		win.show();
		
 }

	function reload(){
		 dataStore.reload()
	}

});
	Ext.QuickTips.init();
	var privilege=226;
	var comboxPage=15;
	var saveUrl="fi/fiReconciliationAccountAction!save.action";
	
	var ralaListUrl="fi/fiReconciliationAccountAction!list.action";
	
Ext.onReady(function(){
	Ext.QuickTips.init();
	var rownum=new Ext.grid.RowNumberer({
		header:'序号',
        width : 35,
		sortable : true	
	});
	
    var fields=[{name:"id",mapping:'id'},'nature',  //账号性质
	'bank',  //开户银行
	'accountNum',  //账号
	'accountName',  //账号名称 
	'phone',  // 联系方式
	'createTime',   
	'createName','createBank',
	'updateTime',
	'bank2',	//	对私银行名称
	'accountNum2',	//	对私账号
	'accountName2',	//	对私开户名
	'createBank2', //		对私开户银行
	'updateName',
	'isDelete',   //状态，0删除 1正常
	'departName',  
	'departId',  // 业务部门
	'remark',
	'ts'];
	
	var jsonread= new Ext.data.JsonReader(
    	         {  root:'result',
                    totalProperty:'totalCount'
                  },fields);
                  
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
                proxy: new Ext.data.HttpProxy({url:sysPath+"/"+ralaListUrl}),
                baseParams:{filter_EQL_departId:bussDepart,
							filter_EQL_isDelete:1,
							limit : pageSize},
                reader:jsonread
    });
	
	var menuStore = new Ext.data.Store({ 
            storeId:"menuStore",                        
            proxy: new Ext.data.HttpProxy({url:sysPath+"/sys/dictionaryAction!list.action",method:'post'}),
            reader: new Ext.data.JsonReader({
              root: 'result',
              totalProperty:'totalCount'
              }, [    
                   {name : 'basDictionaryName',mapping :'name'}, 
                   {name:'basDictionaryId',mapping :'id'}
                 ])
     });

	var menuStore=new Ext.data.SimpleStore({
			 auteLoad:true, //此处设置为自动加载
  			  data:[['1','对公'],['0','对私']],
   			 fields:["id","nature"]
	});

	var recordGrid=new Ext.grid.GridPanel({
	//	renderTo:'recordGrid',
		renderTo:Ext.getBody(),
		id:'userCenter',
		height : Ext.lib.Dom.getViewHeight()-1,
		width : Ext.lib.Dom.getViewWidth()-1,
		autoScroll : true,
		viewConfig : {
			columnsText : "显示的列",
			sortAscText : "升序",
			sortDescText : "降序",
			forceFit : true
		},
		autoExpandColumn : 1,
		frame : false,
		loadMask : true,
		sm:sm,
		stripeRows : true,
		tbar:['&nbsp;&nbsp;',{
				text:'<B>添加</B>',
				iconCls : 'userAdd',
				id:'addbtn',
				handler : function() {
						updateUser();
				}
			}, '-',{
				text : '<B>修改</B>',
				id : 'updatebtn',
				disabled : true,
				iconCls:'userEdit',
				handler : function() {
						var _records = recordGrid.getSelectionModel().getSelections();
						if (_records.length == 0) {
							Ext.Msg.alert(alertTitle, '请选择一条您需要修改的数据');
						} else if (_records.length > 1) {
							Ext.Msg.confirm(alertTitle,'修改数据只能选择一数据');
						} else {
							updateUser(_records[0]);
						}
				}
			},'-',{
				text:'<b>删除</b>',
			 	disabled : true,
				id : 'deletebtn',
			 	iconCls:'userDelete',
			 	handler : function() {
					deleteUser();
				} 
			},'-',{
    				     text : '<b>搜索</b>',
    				     id : 'btn',
    				     iconCls : 'btnSearch',
    					 handler : searchLog
    				}
			],
			columns:[rownum,
        			sm,
        		    {header: 'ID', dataIndex: 'id',sortable : true,hidden : true},
        		    {header: '打印抬头', dataIndex: 'nature',sortable : true},
        			{header: '账号(对公)', dataIndex: 'accountNum',sortable : true},
    				{header: '账户名称(对公)', dataIndex: 'accountName'},
			        {header: '所属银行(对公)', dataIndex: 'bank',sortable : true},
			        {header: '开户银行(对公)', dataIndex: 'createBank',sortable : true},
			        {header: '账号(对私)', dataIndex: 'accountNum2',sortable : true},
    				{header: '账户名称(对私)', dataIndex: 'accountName2'},
			        {header: '所属银行(对私)', dataIndex: 'bank2',sortable : true},
			        {header: '开户银行(对私)', dataIndex: 'createBank2',sortable : true},
			        {header: '联系方式', dataIndex: 'phone',sortable : true},
					{header: '部门ID', dataIndex: 'departId',width:80,hidden : true},
					{header: '部门名称', dataIndex: 'departName',width:150},
					{header: '创建人', dataIndex: 'createName',width:40,hidden : true},
					{header: '创建时间', dataIndex: 'createTime',width:60,hidden : true,sortable : true},
					{header: '修改人', dataIndex: 'updateName',hidden : true,width:40},
					{header: '修改时间', dataIndex: 'updateTime',hidden : true,sortable : true,width:60},
					{header: '时间戳', dataIndex: 'ts',hidden : true,width:110},
    			    {header: '备注', dataIndex: 'remark',width:150}],
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
			dataStore.reload({
						params : {
							start : 0,
							limit : pageSize
						}
					});
	} 
		
		recordGrid.on('click', function() {
			select();
		});

		recordGrid.on('rowdblclick',function(grid,index,e){
		       var _records = recordGrid.getSelectionModel().getSelections();
			    if (_records.length ==1) {
					updateUser(_records[0]);
				}
		});

		function select(){
			var vnetmusicRecord = recordGrid.getSelectionModel().getSelections();
				var updatebtn = Ext.getCmp('updatebtn');
				var deletebtn = Ext.getCmp('deletebtn');
				if (vnetmusicRecord.length == 1) {
					updatebtn.setDisabled(false);
					deletebtn.setDisabled(false);
				} else if (vnetmusicRecord.length > 1) {
					deletebtn.setDisabled(false);
					updatebtn.setDisabled(true);
				} else {
					deletebtn.setDisabled(true);
					updatebtn.setDisabled(true);
				}
		}
		
			function deleteUser(){
			  var _records = Ext.getCmp('userCenter').getSelectionModel().getSelections();
			  if (_records.length == 0) {
				 parent.Ext.Msg.alert(alertTitle, '请选择一条您需要删除的数据');
				 return false;
			  }else {
			     parent.Ext.Msg.confirm(alertTitle,'数据删除后将不可恢复，您确定要删除这<span style="color:red">&nbsp;'+ _records.length + '&nbsp;</span>行数据吗?',function(btnYes) {
									if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
								        var ids = "";
   										for(var i = 0; i < _records.length; i++) {
											ids += _records[i].data.id + ",";
										}  	
										form1.getForm().doAction('submit', {
											url : sysPath+ "/fi/fiReconciliationAccountAction!delete.action",
											method : 'post',
											params : {
   													ids : ids,
   													privilege:privilege
   											},
											waitMsg : '正在删除数据...',
											success : function(form1, action) {
												parent.Ext.Msg.alert(
														alertTitle,
														"数据删除成功",
														function() {
															dataStore.reload()
														});
											},
											failure : function(form1, action) {
												parent.Ext.Msg.alert(
														alertTitle,
														"数据删除失败",
														function() {
															dataStore.reload();
															 select();
														});
											}
										});
										}
									});
	   
	  }
	  select();
	}
	
	
	function updateUser(_record) {
					 var form = new Ext.form.FormPanel({
								labelAlign : 'right',
								frame : true,
								bodyStyle : 'padding:3px 3px 0',
							    width : 390,
							    labelWidth : 120, // 标签宽度
								reader : new Ext.data.JsonReader({
			                                   root: 'result', totalProperty: 'totalCount'
			                              }, fields),
					            labelAlign : "right",
						        items : [{id:"id",
										  name : "id",
										  xtype : "hidden"
										 },{id:"ts",
										  name : "ts",
										  xtype : "hidden"
										 },{  
											  xtype : 'textfield',
											  fieldLabel: '打印抬头<span style="color:red">*</span>',
											   name: 'nature',
							        		   maxLength : 50,
							        		   allowBlank : false,
							        		   anchor : '95%',
									           blankText : '必须填写打印抬头'
						        		   },{ xtype : 'textfield',
						        		       fieldLabel: '账号(对公)<span style="color:red">*</span>', 
							        		   name: 'accountNum',
							        		   maxLength : 25,
							        		   allowBlank : false,
							        		   anchor : '95%',
									           blankText : '必须填写账号'
								           },{ xtype : 'textfield',
						        		       fieldLabel: '所属银行(对公)<span style="color:red">*</span>', 
							        		   name: 'bank',
							        		   maxLength : 25,
							        		   allowBlank : false,
							        		   anchor : '95%',
									           blankText : '必须填写条目名称'
								           },{ xtype : 'textfield',
						        		       fieldLabel: '开户银行名称(对公)<span style="color:red">*</span>', 
							        		   name: 'createBank',
							        		   maxLength : 50,
							        		   allowBlank : false,
							        		   anchor : '95%',
									           blankText : '必须填写开户银行名称'
								           },{ xtype : 'textfield',
						        		       fieldLabel: '账户名称(对公)<span style="color:red">*</span>', 
							        		   name: 'accountName',
							        		   maxLength : 30,
							        		   allowBlank : false,
							        		   anchor : '95%',
									           blankText : '必须填写账户名称' 
								           },{ xtype : 'textfield',
						        		       fieldLabel: '账号(对私)<span style="color:red">*</span>', 
							        		   name: 'accountNum2',
							        		   maxLength : 25,
							        		   allowBlank : false,
							        		   anchor : '95%',
									           blankText : '必须填写账号'
								           },{ xtype : 'textfield',
						        		       fieldLabel: '所属银行(对私)<span style="color:red">*</span>', 
							        		   name: 'bank2',
							        		   maxLength : 25,
							        		   allowBlank : false,
							        		   anchor : '95%',
									           blankText : '必须填写条目名称'
								           },{ xtype : 'textfield',
						        		       fieldLabel: '开户银行名称(对私)<span style="color:red">*</span>', 
							        		   name: 'createBank2',
							        		   maxLength : 50,
							        		   allowBlank : false,
							        		   anchor : '95%',
									           blankText : '必须填写开户银行名称'
								           },{ xtype : 'textfield',
						        		       fieldLabel: '账户名称(对私)<span style="color:red">*</span>', 
							        		   name: 'accountName2',
							        		   maxLength : 30,
							        		   allowBlank : false,
							        		   anchor : '95%',
									           blankText : '必须填写账户名称' 
								           },{ xtype : 'textfield',
						        		       fieldLabel: '联系电话<span style="color:red">*</span>', 
							        		   name: 'phone',
							        		   maxLength : 30,
							        		   allowBlank : false,
							        		   anchor : '95%',
									           blankText : '必须填写账户名称' 
								           },{
												xtype : 'textarea',
												name : 'remark',
												maxLength:500,
												fieldLabel : '备注',
												height : 50,
												width:'95%'
											}
								        ]
						});
		
							
		carTitle='新增账户信息';
		if(_record!=null){
			carTitle='修改账户信息';
			form.load({
				url : sysPath+ "/"+ralaListUrl,
				params:{filter_EQL_id: _record.data.id,limit : pageSize}
			})
		}
			
		var win = new Ext.Window({
			title : carTitle,
			width : 390,
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
							params : {
								privilege:privilege,
								isDelete:1
							},
							waitMsg : '正在保存数据...',
							success : function(form, action) {
									win.hide(), 
									Ext.Msg.alert(alertTitle,action.result.msg, function() {
											 dataStore.reload();
									});
							},
							failure : function(form, action) {
								if (action.faliureType == Ext.form.Action.SERVER_INVALID) {
									Ext.Msg.alert(alertTitle, "服务器连接异常，请联系管理员！");
								} else {
									if (action.result.msg) {
										win.hide();
										Ext.Msg.alert(alertTitle,action.result.msg);
	
									}
								}
							}
						});
					}
				}
			}, {
				text : "重置",
				iconCls : 'refresh',
				handler : function() {		
						if(_record==null){
							form.getForm().reset();
						
						}
						if(_record!=null){
							carTitle='修改账户信息';
							form.load({
							    url : sysPath+"/"+ralaListUrl,
							    params:{filter_EQL_id:_record.data.id,limit : pageSize}
						   });
									
		             	}
					}
				 }, {
						text : "取消",
						handler : function() {
						   win.close();
						   select();
						}
					}]
								
		
		});
	
		win.on('hide', function() {
					form.destroy();
					select();
				});
		
		win.show();
		
 }
});
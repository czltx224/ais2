	Ext.QuickTips.init();
	var privilege=16;
	var comboxPage=15;
	var saveUrl="sys/basDictionaryAction!save.action";
	
	var ralaListUrl="sys/basDictionaryAction!ralaList.action";
	
Ext.onReady(function(){
	Ext.QuickTips.init();
	var rownum=new Ext.grid.RowNumberer({
		header:'序号',
        width : 35,
		sortable : true	
	});
 
    var fields=[{name:"id",mapping:'id'},'typeName','typeCode','basDictionaryId','basDictionaryName'];
	
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
                baseParams:{limit: pageSize},
                reader:jsonread
    });
	
	var menuStore = new Ext.data.Store({ 
            storeId:"menuStore",                        
            proxy: new Ext.data.HttpProxy({url:sysPath+"/sys/dictionaryAction!list.action",method:'post'}),
            baseParams:{ralasafe:true},
            reader: new Ext.data.JsonReader({
              root: 'result',
              totalProperty:'totalCount'
              }, [    
                   {name : 'basDictionaryName',mapping :'name'}, 
                   {name:'basDictionaryId',mapping :'id'}
                 ])
     });

     menuStore.load();
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
				tooltip : '增加数据字典信息',
				iconCls : 'userAdd',
				id:'addbtn',
				handler : function() {
						updateUser();
				}
			}, '-',{
				text : '<B>修改</B>',
				id : 'updatebtn',
				disabled : true,
				tooltip : '修改数据字典',
				iconCls:'userEdit',
				handler : function() {
						var _records = recordGrid.getSelectionModel().getSelections();
						if (_records.length == 0) {
							parent.Ext.Msg.alert(alertTitle, '请选择一条您需要修改的数据');
						} else if (_records.length > 1) {
							parent.Ext.Msg.confirm(alertTitle,
									'修改数据只能选择一数据，请确认您是否真的修改', function(btnYes) {
										if (btnYes == 'yes' || btnYes == 'ok'
												|| btnYes == true) {
											recordGrid.getSelectionModel()
													.clearSelections();
										}
									});
						} else {
							updateUser(_records[0]);
						}
				}
			},'-',{
				text:'<b>删除</b>',
			 	disabled : true,
				id : 'deletebtn',
			 	iconCls:'userDelete',
			 	tooltip : '删除数据字典',
			 	handler : function() {
					deleteUser();
				} 
			},'-',{
				xtype:'textfield',
	 	        id : 'itemsValue',
		        name : 'itemsValue',
	            blankText:'查询数据字典',
	            enableKeyEvents:true,
	            listeners : {
	 				keyup:function(textField, e){
	                     if(e.getKey() == 13){
	                     	searchLog();
	                     }
	 				}
	 			}
	        },{
	        
				xtype : 'combo',
				id:'comboType',
				hidden : true,
				typeAhead:true, // 自动填充
				pageSize : comboxPage,
				hiddenId : 'dictionaryName',
    			hiddenName : 'dictionaryName',
				triggerAction : 'all',
				store : menuStore,
				allowBlank : true,　//指定参数是否允许为空
				width:210,
				minChars : 1,
				queryParam : 'filter_LIKES_name',
				emptyText : "请选择类别名称",
				forceSelection : true,  //模糊查询
				fieldLabel:'类别名称',
				editable : true,  // 可编辑
			//	mode : "local",//获取本地的值
				displayField : 'basDictionaryName',//显示值，与fields对应
				valueField : 'basDictionaryId',//value值，与fields对应
				name : 'basDictionaryId',
				anchor : '95%'
	    	},'&nbsp;&nbsp;',{
                xtype : "combo",
    			id:"comboselect",
   				width : 100,
 				triggerAction : 'all',
    			model : 'local',
    			hiddenId : 'checkItems',
    			hiddenName : 'checkItems',
    			name : 'checkItemstext',
    			store : [['','查询全部'],
    							 ['LIKES_typeName','条目名称'],
    							 ['EQS_basDictionaryId','类别名称'],
    							 ['EQS_typeCode','类型编码']
    							 ],
    					emptyText : '选择查询类型',
    					forceSelection : true,
    						listeners : {
    						'select' : function(combo, record, index) { // override default onSelect to
    												// do redirect
    							if (combo.getValue() == '') {
    								Ext.getCmp("comboType").disable();
    								Ext.getCmp("comboType").hide();
    								Ext.getCmp("comboType").setValue("");

    								Ext.getCmp("itemsValue").enable();
    								Ext.getCmp("itemsValue").show();
    								Ext.getCmp("itemsValue").setValue("");
    							}else if(combo.getValue() == 'EQS_basDictionaryId') {
    								Ext.getCmp("comboType").setValue("");
    								Ext.getCmp("comboType").show();
    								Ext.getCmp("comboType").enable();
    								
    								Ext.getCmp("itemsValue").disable();
    								Ext.getCmp("itemsValue").hide();
    								Ext.getCmp("itemsValue").setValue("");
    							}else{
    								Ext.getCmp("comboType").disable();
    								Ext.getCmp("comboType").hide();
    								Ext.getCmp("comboType").setValue("");
    								
    								Ext.getCmp("itemsValue").enable();
    								Ext.getCmp("itemsValue").show();
    							}
    						}
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
        		    {header: 'ID', dataIndex: 'id',sortable : true},
        			{header: '条目名称', dataIndex: 'typeName',sortable : true},
    				{header: '类别名称', dataIndex: 'basDictionaryName'},
			        {header: '类别编码', dataIndex: 'typeCode',sortable : true}
			    ],
    			    
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
		  dataStore.proxy = new Ext.data.HttpProxy({
					method : 'POST',
					url : sysPath+'/'+ralaListUrl+'?privilege='+privilege
		  });
 		 if (Ext.getCmp('comboselect').getValue() == 'EQS_basDictionaryId') {
 		 	var typeValue=Ext.getCmp("comboType").getValue();
 		 	Ext.apply(dataStore.baseParams={
    						checkItems : Ext.getCmp('comboselect').getValue(),
    						itemsValue : Ext.get("dictionaryName").dom.value,
    						limit : pageSize
    		});
 		 
 		 }else{
			 dataStore.baseParams = {
				checkItems : Ext.get("checkItems").dom.value,
			 	itemsValue : Ext.get("itemsValue").dom.value,
			 	limit : pageSize
			 }
		 }
		 Ext.getCmp('addbtn').setDisabled(false);
		 Ext.getCmp('updatebtn').setDisabled(false);
		 dataStore.reload({
					baseparams : {
						start : 0,
						privilege:privilege,
						limit : pageSize
					}
		 });
		select();
		 
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
											url : sysPath+ "/sys/basDictionaryAction!delete.action",
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
							//	url:sysPath+'/user/userAction!ralaList.action?privilege=17',
							
								bodyStyle : 'padding:3px 3px 0',
							    width : 390,
								reader : new Ext.data.JsonReader({
			                                   root: 'result', totalProperty: 'totalCount'
			                              }, fields),
					            labelAlign : "right",
						        items : [{id:"id",
										  name : "id",
										  xtype : "hidden"
										 },{  
											  xtype : 'combo',
											  fieldLabel: '类别名称<span style="color:red">*</span>',
											  allowBlank : false,
											  typeAhead:false,
											  id:'bnaryN',
											  forceSelection : true,
											  minChars : 1,
											//  minListWidth:'auto',
										      triggerAction : 'all',
											  store: menuStore,
											  pageSize : comboxPage,
											  queryParam : 'filter_LIKES_name',
											  displayField : 'basDictionaryName',
											  valueField : 'basDictionaryId',
											  hiddenName : 'basDictionaryId',
											  blankText : "类型不能为空！",
											  anchor : '95%',
											  emptyText : "请选择类型"
						        		   },{ xtype : 'textfield',
						        		       fieldLabel: '条目名称<span style="color:red">*</span>', 
							        		   name: 'typeName',
							        		   maxLength : 20,
							        		   allowBlank : false,
							        		   anchor : '95%',
									           blankText : '必须填写条目名称'
								           },{ xtype : 'numberfield',
								               fieldLabel: '类别编码', 
								               name: 'typeCode',
								               maxLength : 8,
								               anchor : '95%'
								           },{ 
								             name: 'privilege',hidden:true,value:16
								           }
								        ]
						});
		
							
		carTitle='增加数据字典信息';
		if(_record!=null){
			carTitle='修改数据字典信息';
            menuStore.load();
			form.load({
				url : sysPath+ "/"+ralaListUrl,
				params:{filter_EQL_id: _record.data.id, privilege:privilege,limit : pageSize},
				success : function(resp) {
					Ext.getCmp('bnaryN').setRawValue(_record.get('basDictionaryName'));
				}
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
							params:{privilege:privilege,limit : pageSize},
							waitMsg : '正在保存数据...',
							success : function(form, action) {
										win.hide(), 
										Ext.Msg.alert(alertTitle,action.result.msg, function() {
												reload();
										});
							}
							
							,
							failure : function(form, action) {
								if (action.faliureType == Ext.form.Action.SERVER_INVALID) {
									Ext.Msg.alert(alertTitle, "服务器连接异常，请联系管理员！");
								} else {
									if (action.result.msg) {
										win.hide();
										Ext.Msg.alert(alertTitle,
												action.result.msg, function() {
													reload();
												});
	
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
							carTitle='修改用户信息';
							menuStore.load();
							form.load({
							    url : sysPath+"/"+ralaListUrl,
							    params:{filter_EQL_id:_record.data.id,privilege:privilege,limit : pageSize},
							    success : function(resp) {
								}
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

	function reload(){
		 dataStore.reload()
	}

});


 








	

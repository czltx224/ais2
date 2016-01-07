	Ext.QuickTips.init();
	var privilege=26;
	var comboxPage=comboSize;
	var saveUrl="sys/loadingbrigadeAction!save.action";
	
	var ralaListUrl="sys/loadingbrigadeAction!ralaList.action";
	
Ext.onReady(function(){
	Ext.QuickTips.init();
	var rownum=new Ext.grid.RowNumberer({
		header:'序号',
        width : 35,
		sortable : true	
	});
 
    var fields=[{name:"id",mapping:'id'},'ts','type','departId','loadingName','departName','manCount','createName','createTime','updateName','updateTime'];

    
    var jsonread= new Ext.data.JsonReader({
                    root:'result',
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
                proxy: new Ext.data.HttpProxy({url:sysPath+"/"+ralaListUrl}),
                baseParams:{
                	limit: pageSize,
                	filter_EQL_departId:bussDepart
                },
                reader:jsonread
    });
	
 
	/*var menuStore = new Ext.data.Store({ 
            storeId:"menuStore",                        
            proxy: new Ext.data.HttpProxy({url:sysPath+"/sys/departAction!ralaList.action?privilege=53",method:'post'}),
            reader: new Ext.data.JsonReader({
            root: 'result', totalProperty: 'totalCount'
           }, [  {name:'departName', mapping:'departName',type:'string'},           
                 {name:'departId', mapping: 'departId'}
              ]),    
            sortInfo:{field:'departId',direction:'ASC'}
    });
	
	menuStore.load();*/


     
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
				tooltip : '增加装卸组信息',
				iconCls : 'userAdd',
				id:'addbtn',
				handler : function() {
						updateUser();
				}
			}, '-',{
				text : '<B>修改</B>',
				id : 'updatebtn',
				disabled : true,
				tooltip : '修改装卸组信息',
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
			 	tooltip : '删除装卸组',
			 	handler : function() {
					deleteUser();
				} 
			},'-',{
	    		xtype : 'datefield',
	    		id : 'startDate',
	    		format : 'Y-m-d',
	    		emptyText : "选择开始时间",
	    		anchor : '95%',
	    		hidden : true,
	    		width : 100,
	    		disabled : true,
	    		listeners : {
	    			'select' : function() {
	    			   var start = Ext.getCmp('startDate').getValue()
	    			      .format("Y-m-d");
	    			   Ext.getCmp('endDate').setMinValue(start);
	    		     }
	    		}
    		}, '&nbsp;&nbsp;', {
	    		xtype : 'datefield',
	    		id : 'endDate',
	    		format : 'Y-m-d',
	    		emptyText : "选择结束时间",
	    		hidden : true,
	    		width : 100,
	    		disabled : true,
	    		anchor : '95%'
    	    }/*,{
				xtype : 'combo',
				id:'comboType',
				hidden : true,
				typeAhead:true,
				hiddenId : 'dictionaryName',
    			hiddenName : 'dictionaryName',
				triggerAction : 'all',
				store : menuStore,
				width:200,
				listWidth:245,
				minChars : 1,
				allowBlank : true,
				queryParam : 'filter_LIKES_departName',
				emptyText : "请选择部门名称",
				forceSelection : true,
				fieldLabel:'部门名称',
				editable : true,
			//	minListWidth:'auto',
				pageSize:comboxPage,
				displayField : 'departName',//显示值，与fields对应
				valueField : 'departId',//value值，与fields对应
				name : 'departId',
				anchor : '100%'
		    }*/,{
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
    							 ['LIKES_loadingName', '组名称'],
    						//	 ['EQS_departId', "部门名称"],
    							 ['LIKES_createName', '创建人'],
    							 ['LIKES_updateName', '修改人'],
    							 ['EQD_createTime', '创建时间'],
    							 ['EQD_updateTime', '修改时间']
    						    ],
    					emptyText : '选择查询类型',
    					forceSelection : true,
    								listeners : {
    						'select' : function(combo, record, index) { 
    							if (combo.getValue() == 'EQD_createTime') {
    								Ext.getCmp("startDate").enable();
    								Ext.getCmp("startDate").show();

    								Ext.getCmp("endDate").enable();
    								Ext.getCmp("endDate").show();
    								
    								Ext.getCmp("itemsValue").disable();
    								Ext.getCmp("itemsValue").hide();
    								Ext.getCmp("itemsValue").setValue("");
    								
    								Ext.getCmp("comboType").disable();
    								Ext.getCmp("comboType").hide();
    								Ext.getCmp("comboType").setValue("");
    								
    							}else if(combo.getValue() == 'EQD_updateTime') {
    								Ext.getCmp("startDate").enable();
    								Ext.getCmp("startDate").show();

    								Ext.getCmp("endDate").enable();
    								Ext.getCmp("endDate").show();
    								
    							 	Ext.getCmp("itemsValue").disable();
    								Ext.getCmp("itemsValue").hide();
    								Ext.getCmp("itemsValue").setValue("");
    								
    								Ext.getCmp("comboType").disable();
    								Ext.getCmp("comboType").hide();
    								Ext.getCmp("comboType").setValue("");
    								
    						 	}/*else if(combo.getValue() == 'EQS_departId') {
    						       	Ext.getCmp("startDate").disable();
    						  		Ext.getCmp("startDate").hide();
    								Ext.getCmp("startDate").setValue("");

    								Ext.getCmp("endDate").disable();
    								Ext.getCmp("endDate").hide();
    								Ext.getCmp("endDate").setValue("");
    								
    							 	Ext.getCmp("itemsValue").disable();
    								Ext.getCmp("itemsValue").hide();
    								Ext.getCmp("itemsValue").setValue("");
    								
    								Ext.getCmp("comboType").enable();
    								Ext.getCmp("comboType").show();
    								
    						 	}*/else{
    					         	Ext.getCmp("startDate").disable();
    						  		Ext.getCmp("startDate").hide();
    								Ext.getCmp("startDate").setValue("");

    								Ext.getCmp("endDate").disable();
    								Ext.getCmp("endDate").hide();
    								Ext.getCmp("endDate").setValue("");
    								
    						      	Ext.getCmp("itemsValue").enable();
    								Ext.getCmp("itemsValue").show();
    								
    								Ext.getCmp("comboType").disable();
    								Ext.getCmp("comboType").hide();
    								Ext.getCmp("comboType").setValue("");
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
			columns:[ rownum,sm,
        		    {header: 'id', dataIndex: 'id',	hidden : true,sortable : true},
        			{header: '组名称', dataIndex: 'loadingName'},
        			{header: '组类型', dataIndex: 'type',renderer:function(v){
			        					if( v=='0'){
			        						return '装卸组';
			        					}else if(v=='1'){
			        						return '分拨组';
			        					}else{
			        						return '无类型';
			        					}
			        				}},
			        {header: '部门名称', dataIndex: 'departName',width:150},
			        {header: '装卸组人数', dataIndex: 'manCount',sortable : true},
			        {header: '创建人', dataIndex: 'createName'},
			        {header: '创建时间', dataIndex: 'createTime',sortable : true},
    				{header: '修改人', dataIndex: 'updateName'},
    			    {header: '修改时间', dataIndex: 'updateTime',sortable : true}
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
		 /*   if (Ext.getCmp('comboselect').getValue() == 'EQS_departId') {
 		 		var typeValue=Ext.getCmp("comboType").getValue();
 		 		Ext.apply(dataStore.baseParams={
    						checkItems : Ext.getCmp('comboselect').getValue(),
    						itemsValue : Ext.get("dictionaryName").dom.value,
    						limit : pageSize
    			});
 		 
 		 	}else*/	if (Ext.getCmp('comboselect').getValue() == 'EQD_createTime') {
    				var start='';
					var end ='';
					if(Ext.getCmp('startDate').getValue()!=""){
					   var start = Ext.getCmp('startDate').getValue().format("Y-m-d");
					}
					if(Ext.getCmp('endDate').getValue()!=""){
					    var end = Ext.getCmp('endDate').getValue().format("Y-m-d");
					}
    			Ext.apply(dataStore.baseParams, {
    						filter_GED_createTime : start,
    						filter_LED_createTime : end,
    						filter_EQL_departId:bussDepart,
    						checkItems : '',
    						itemsValue : ''
    					});
    		} else if (Ext.getCmp('comboselect').getValue() == 'EQD_updateTime'){
    				var start='';
					var end ='';
					if(Ext.getCmp('startDate').getValue()!=""){
					    var start = Ext.getCmp('startDate').getValue().format("Y-m-d");
					}
					if(Ext.getCmp('endDate').getValue()!=""){
					   var end = Ext.getCmp('endDate').getValue().format("Y-m-d");
					}
    			Ext.apply(dataStore.baseParams, {
    						filter_GED_updateTime : start,
    						filter_LED_updateTime : end,
    						filter_EQL_departId:bussDepart,
    						checkItems : '',
    						itemsValue : ''
    					});
    		}else{	
				dataStore.baseParams = {
					checkItems : Ext.get("checkItems").dom.value,
					itemsValue : Ext.get("itemsValue").dom.value,
					filter_EQL_departId:bussDepart
			    }
			}
			var userrolebtn = Ext.getCmp('addbtn');
			var updatebtn = Ext.getCmp('updatebtn');

			userrolebtn.setDisabled(false);
			updatebtn.setDisabled(false);

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
											url : sysPath+ "/sys/loadingbrigadeAction!delete.action",
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
								frame : false,
							//	url:sysPath+'/user/userAction!ralaList.action?privilege=17',
							
								bodyStyle : 'padding:3px 3px 0',
							    width : 380,
								reader : jsonread,
					            labelAlign : "right",
						        items : [
						        	  { id:'ts',
			        	                name: 'ts', 
			        	                xtype : "hidden",
			        	                hidden:true
			        	              },
			        	              { id:'id',
			        	                name: 'id', 
			        	                xtype : "hidden",
			        	                hidden:true
			        	              },{xtype : 'textfield',
			        	                fieldLabel: '组名称<span style="color:red">*</span>', 
				        			    name: 'loadingName',
				        			    maxLength : 20,
				        			    allowBlank : false,
						                blankText : '必须填写组名称',
						                anchor : '95%'
						              },{xtype: 'radiogroup',
						                fieldLabel: '组类型<span style="color:red">*</span>',
						                id:"type",
						                columns: 3,
						                defaults: {
						                    name: 'type' 
						                },
						                items: [{
						                    inputValue: '0',
						                    boxLabel: '装卸组',
						                    checked:_record==null?true:(_record.data.sex=="1"?true:false)
						                }, {
						                    boxLabel: '分拨组',
						                    inputValue: '1',
						                    checked:_record==null?false:(_record.data.sex=="0"?true:false)
						                }]
									},/*
									,{
								
										xtype : 'combo',
										fieldLabel: '部门名称<span style="color:red">*</span>',
										allowBlank : false,
										typeAhead:false,
										forceSelection : true,
										minChars : 1,
									//	minListWidth:'auto',
										triggerAction : 'all',
										store: menuStore,
										pageSize : comboxPage,
										queryParam : 'filter_LIKES_departName',
										displayField : 'departName',
										valueField : 'departId',
										hiddenName : 'departId',
										blankText : "部门不能为空！",
										anchor : '95%',
										emptyText : "请选择部门名称"
									},
									{  
											  xtype : 'combo',
											  fieldLabel: '类别名称<span style="color:red">*</span>',
											  allowBlank : false,
											  typeAhead:false,
											  forceSelection : true,
											  minChars : 1,
											//  minListWidth:'auto',
										      triggerAction : 'all',
											  store: menuStore,
											  pageSize : comboxPage,
											  queryParam : 'filter_LIKES_basDictionaryName',
											  displayField : 'basDictionaryName',
											  valueField : 'basDictionaryId',
											  hiddenName : 'basDictionaryId',
											  blankText : "类型不能为空！",
											  anchor : '95%',
											  emptyText : "请选择类型"
						        		   }
									
									*/
									{
										xtype : 'numberfield',
										labelAlign : 'left',
										fieldLabel : '组人数',
										maxLength:6,
										name : 'manCount',
										anchor : '95%'
									},{ name: 'privilege',hidden:true,value:26}
					                ]
						});
		
							
		carTitle='增加组类型信息';
		if(_record!=null){
			carTitle='修改组类型信息';
		//	menuStore.load();
			form.load({
				url : sysPath+ "/"+ralaListUrl,
				params:{filter_EQL_id: _record.data.id, privilege:privilege,limit : pageSize}
			})
		}
			
		var win = new Ext.Window({
			title : carTitle,
			width : 380,
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
						//	menuStore.load();
							form.load({
							    url : sysPath+"/"+ralaListUrl,
							    params:{filter_EQL_id:_record.data.id,privilege:privilege,limit : pageSize}
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



















	

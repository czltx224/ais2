
	Ext.QuickTips.init();
	var privilege=235;  
	var comboxPage=15;
	var saveUrl="fi/fiReceiptAction!save.action";
	
	var ralaListUrl="fi/fiReceiptAction!list.action";
	
Ext.onReady(function(){
	Ext.QuickTips.init();
	var rownum=new Ext.grid.RowNumberer({
		header:'序号',
        width : 35,
		sortable : true	
	});
 
   var fields=[
 	'id',
	'receiptNo',   //收据单号
	'receiptData',   //收据时间
	'remark',   // 摘要
	'amount',   //金额
	'departId',         //业务部门
	'departName',
	'fiPaidId',     //实配单号
	'createName',                   
	'createTime',
	'updateName',
	'updateTime',
	'delStatus',       //作废状态 0：作废，1：正常
	'delName',      //作废人
	'delTime',           //作废时间
	'printNum',           //打印次数
	'printName',         //打印人
	'printDate',              //打印时间
	'sourceData',         //数据来源
	'sourceNo',         //来源单号
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
	
			//权限部门
	var rightDepartStore2 = new Ext.data.Store({ 
            storeId:"rightDepartStore2",                        
            proxy: new Ext.data.HttpProxy({url:sysPath+"/sys/basRightDepartAction!ralaList.action",method:'post'}),
            baseParams:{
            	privilege:63,
            	filter_EQL_userId:userId
            },
            reader: new Ext.data.JsonReader({
            root: 'result', totalProperty: 'totalCount'
           }, [{name:'departName', mapping:'departName',type:'string'},
               {name:'departId', mapping:'rightDepartid',type:'string'}             
              ])    
    });
	
	var dataStore = new Ext.data.Store({
                proxy: new Ext.data.HttpProxy({url:sysPath+"/"+ralaListUrl}),
                baseParams:{limit: pageSize},
                reader:jsonread
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
		tbar:['&nbsp;',{
				text : '<B>修改</B>',
				id : 'updatebtn',
				tooltip : '修改数据字典',
				iconCls:'userEdit',
				handler : function() {
						var _records = recordGrid.getSelectionModel().getSelections();
						if (_records.length == 0) {
							parent.Ext.Msg.alert(alertTitle, '请选择一条您需要修改的数据');
						} else if (_records.length > 1) {
							Ext.Msg.confirm(alertTitle,'请选择一条您需要修改的数据');
						} else {
							updateReceipt(_records[0]);
						}
				}
			},'&nbsp;','-','&nbsp;',{
				text:'<b>作废</b>',
				id : 'deletebtn',
			 	iconCls:'userDelete',
			 	handler : function() {
					deleteUser();
				} 
			},'&nbsp;','-','&nbsp;',{
				text:'<b>收据打印</b>',
				iconCls:'printBtn',
				handler:function() {
					var records = recordGrid.getSelectionModel().getSelections();
					
					if(records.length<1){
						Ext.Msg.alert(alertTitle,"请选择一条需要打印的数据！");
						return;
					}else if (records.length>1){
						Ext.Msg.alert(alertTitle,"一次只允许打印一条单据！");
						return;
					}
					if(records[0].get('printNum')==0){
		            	parent.print('10',{print_fiReceiptId:records[0].data.id});
					}else{
						Ext.Msg.alert(alertTitle,"单据已打印，不能多次打印");
						return;
					}
	           	} 
			  },'&nbsp;','-','&nbsp;','创建部门:',{
					xtype : 'combo',
					id:'comboRightDepart2',
					hiddenId : 'dictionaryName',
	    			hiddenName : 'olist[0].rightDepartId',
					triggerAction : 'all',
					store : rightDepartStore2,
					mode:'local',
					width : 100,
		//			queryParam : 'filter_LIKES_departName',
					minChars : 1,
					listWidth:245,
					allowBlank : false,
					emptyText : "请选择创建部门名称",
					forceSelection : false,
					fieldLabel:'部门',
					editable : false,
					pageSize:500,
					displayField : 'departName',//显示值，与fields对应
					valueField : 'departId',//value值，与fields对应
					anchor : '95%',
					enableKeyEvents:true,
					listeners : {
						 render:function(combo){
						 		rightDepartStore2.load({
										params : {
											start : 0,
											limit : 500
										},callback :function(v){
											var flag=true;
											for(var i=0;i<rightDepartStore2.getCount();i++){
												if(rightDepartStore2.getAt(i).get("departId")==bussDepart){
													flag=false;
												}
											}
											if(flag){
												var store=new Ext.data.Record.create([{name:'departName'},{name:'departId'}]);
												var record=new store();
												record.set("departId",bussDepart);
												record.set("departName",bussDepartName);
												rightDepartStore2.add(record);		
											}
												combo.setValue(bussDepart);
										}
								});
						 },
				 		 keyup:function(numberfield, e){
				             if(e.getKey() == 13 ){
									searchLog();
				              }
				 		 }
				 	}
	    },'-','&nbsp;',{
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
    			store : [['','查询全部'],
						 ['LIKES_receiptNo','收据单据号'],
						 ['EQN_amount','金额']],
    					emptyText : '选择查询类型',
    					forceSelection : true,
    						listeners : {
    						'select' : function(combo, record, index) { // override default onSelect to
    												// do redirect
    							if (combo.getValue() == '') {
    								Ext.getCmp("itemsValue").setValue("");
    								Ext.getCmp("itemsValue").disable();
    							}else{
    								
    								Ext.getCmp("itemsValue").enable();
    								Ext.getCmp("itemsValue").show();
    							}
    						}
    					}
    				},'-',{
    				     text : '<b>查询</b>',
    				     id : 'btn',
    				     iconCls : 'btnSearch',
    					 handler : searchLog
    				}
			],
			columns:[rownum,
        			sm,
        		    {header: 'ID', dataIndex: 'id',sortable : true,width:60},
        		    {header: '收据单号', dataIndex: 'receiptNo',width:80,sortable : true},
        			{header: '金额', dataIndex: 'amount',width:60,sortable : true},
    				{header: '数据来源', dataIndex: 'sourceData',sortable : true,width:80},
    				{header: '来源单号', dataIndex: 'sourceNo',sortable : true,width:80},
			        {
						header : '摘要',
						dataIndex : 'remark',
						width : 120
					},
					 {header: '打印次数', dataIndex: 'printNum',width:80,sortable : true},
					 {header: '打印人', dataIndex: 'printName',width:80,sortable : true},
					 {header: '打印时间', dataIndex: 'printDate',width:80,sortable : true},
			         {header: '创建部门', dataIndex: 'departName',width:80,sortable : true},
					 {header: '创建人', dataIndex: 'createName',width:80,hidden:true,sortable : true},
			         {header: '创建时间', dataIndex: 'createTime',width:80,hidden:true,sortable : true},
			         {header: '修改人', dataIndex: 'updateName',width:80,hidden:true,sortable : true},
			         {header: '修改时间', dataIndex: 'updateTime',width:80,hidden:true,sortable : true},
			         {header: '创建部门ID', dataIndex: 'departId',width:80,hidden:true,sortable : true},{
						header : '时间戳',
						dataIndex : 'ts',
						width : 60,
						sortable : true,
						hidden : true//,
					//	hideable : false
					}
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
			var comboRightDepart2 =Ext.getCmp('comboRightDepart2').validate();
			if(!comboRightDepart2){
				return;
			}
		
		   	dataStore.proxy = new Ext.data.HttpProxy({
				method : 'POST',
				url : sysPath+'/'+ralaListUrl
		  	});
		
			Ext.apply(dataStore.baseParams={
				checkItems : Ext.getCmp('comboselect').getValue(),
				itemsValue : Ext.get("itemsValue").dom.value,
				filter_EQL_departId:Ext.getCmp('comboRightDepart2').getValue(),
				privilege:privilege,
				filter_EQL_delStatus:1,
				limit : pageSize
    		});
		
		 	dataStore.reload({
				baseparams : {
					start : 0,
					limit : pageSize
				}
		 	});
	 	} 
		


		
			function deleteUser(){
			  var _records = Ext.getCmp('userCenter').getSelectionModel().getSelections();
			  if (_records.length == 0) {
				 parent.Ext.Msg.alert(alertTitle, '请选择一条您需要作废的数据');
				 return false;
			  }else {
			  	for(var i=0;i<_records.length;i++){
			  		if(_records[i].get('printNum')!=0){
						Ext.Msg.alert(alertTitle,"已打印的数据不能作废");
						return ;
					}
			  	}
			  
			     Ext.Msg.confirm(alertTitle,'数据作废后将不可恢复，您确定要作废这<span style="color:red">&nbsp;'+ _records.length + '&nbsp;</span>行数据吗?',function(btnYes) {
												if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
													
									        var ids = "";
    										for(var i = 0; i < _records.length; i++) {
												ids += _records[i].data.id + ",";
											}  	
										form1.getForm().doAction('submit', {
											url : sysPath+ "/fi/fiReceiptAction!deleteByStatus.action",
											method : 'post',
											params : {
   													ids : ids,
   													privilege:privilege
   											},
											waitMsg : '正在作废数据...',
											success : function(form1, action) {
												parent.Ext.Msg.alert(
														alertTitle,
														"数据作废成功",
														function() {
															dataStore.reload()
														});
											},
											failure : function(form1, action) {
												parent.Ext.Msg.alert(
														alertTitle,
														"数据作废失败",
														function() {
															dataStore.reload();
														});
											}
										});
										}
									});
	   
	  }
	}
	
	
	function updateReceipt(record) {
			
			if(record.get('printNum')!=0){
				Ext.Msg.alert(alertTitle,"已打印的数据不能再修改");
				return ;
			}
			
			var userFormPanel = new Ext.form.FormPanel({
				defaultType : 'textfield',
				labelAlign : 'right',
				labelWidth : 80,
				reader:jsonread,
				frame : true,
				bodyStyle : 'padding:5 5 0',
				items : [{	id:"idid",
							name : "id",
							xtype : "hidden"
						},{	id:"ts",
							name : "ts",
							xtype : "hidden"
						},{	id:"fiPaidId",
							name : "fiPaidId",
							xtype : "hidden"
						}, {fieldLabel: '收据日期<span style="color:red">*</span>',
						    name: 'receiptData',
						    id:'receiptData',
						    labelAlign : 'left',
						    xtype:'datefield',
						    format : 'Y-m-d',
						    width : 150,
							allowBlank : false,
							anchor : '95%'
						}, {
							fieldLabel : '金额',
							name : 'amount',
							id : 'amount',
							readOnly:true,
							disabled:true,
							allowNegative:false,
                           	decimalPrecision:2,
							maxLength : 50,
							allowBlank : false,
							anchor : '95%'
						},{
						///	labelAlign : 'top',
							xtype : 'textarea',
							name : 'remark',
							id:'remark',
							maxLength:500,
							allowBlank : false,
							fieldLabel : '摘要<span style="color:red">*</span>',
							height : 45,
							width:'95%'
						}]
			});
			
			userFormPanel.load({
				url : sysPath+ "/"+ralaListUrl,
				params:{filter_EQL_id: record.data.id,limit : pageSize}
			});
			
			var userWindow = new Ext.Window({
				layout : 'fit',
				width : 320,
				height : 200,
				resizable : false,
				draggable : true,
				closeAction : 'hide',
				modal : true,
				title : '<span class="commoncss">修改收据</span>',
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
						Ext.getCmp('remark').focus(true,200);
					}
				},
				items : [userFormPanel],
				buttons : [{
					text : '保存',
					iconCls : 'groupSave',
					handler : function() {
						
						if (userFormPanel.getForm().isValid()) {
							userFormPanel.getForm().submit({
								url : sysPath+ "/fi/fiReceiptAction!save.action",
								params:{
									privilege:privilege
								},
								waitMsg : '正在保存数据...',
								success : function(form, action) {
											userWindow.close();
											Ext.Msg.alert(alertTitle,action.result.msg,function(){
												dataStore.reload();
											});
								},
								failure : function(form, action) {
									if (action.faliureType == Ext.form.Action.SERVER_INVALID) {
										Ext.Msg.alert(alertTitle, "服务器连接异常，请联系管理员！");
									} else {
										if (!action.result.success) {
											Ext.Msg.alert(alertTitle,action.result.msg, function() {
												dataStore.reload();
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

		userWindow.show();
 	}


});


 
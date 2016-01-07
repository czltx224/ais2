	Ext.QuickTips.init();
	var privilege=154;
	var comboxPage=50;
	var saveUrl="fi/fiDeliveryPriceAction!save.action";
	var customerGridSearchUrl = "sys/customerAction!list.action";
	var ralaListUrl="fi/fiDeliveryPriceAction!list.action";
	
Ext.onReady(function(){
	Ext.QuickTips.init();
	var rownum=new Ext.grid.RowNumberer({
		header:'序号',
        width : 35,
		sortable : true	
	});
 
    var fields=[{name:"id",mapping:'id'},'ts','customerId','customerName',
    	'rates','lowest','board1','board2','board3','board4','isBoardStatus',
    	'board5','board6','remark','departId','departName','isdelete','goodsType',
    	'createName','createTime','updateName','updateTime'];

    
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
	
	var statusStore=new Ext.data.SimpleStore({
			 auteLoad:true, //此处设置为自动加载
  			  data:[['1','是'],['0','否']],
   			 fields:["id","name"]
	});
			// 客商列表
	customerStore = new Ext.data.Store({
		storeId : "customerStore",
		proxy : new Ext.data.HttpProxy({
					url : sysPath + "/" + customerGridSearchUrl,
					method:'post'
				}),
		baseParams:{
			filter_EQS_custprop:'提货公司'
		},
		reader : new Ext.data.JsonReader({
					root : 'result',
					totalProperty : 'totalCount'
				}, [{
							name : 'cusName',
							mapping : 'cusName'
						}, {
							name : 'id',
							mapping : 'id'
						}])
	});
	customerStore.load();
	
	var dataStore = new Ext.data.Store({
                proxy: new Ext.data.HttpProxy({url:sysPath+"/"+ralaListUrl}),
                baseParams:{
                	limit: pageSize,
                	filter_EQL_departId:bussDepart
                },
                reader:jsonread
    });
	
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
 
	var menuStore = new Ext.data.Store({ 
            storeId:"menuStore",                        
            proxy: new Ext.data.HttpProxy({url:sysPath+"/sys/departAction!ralaList.action?privilege=53",method:'post'}),
            reader: new Ext.data.JsonReader({
            root: 'result', totalProperty: 'totalCount'
           }, [  {name:'departName', mapping:'departName',type:'string'},           
                 {name:'departId', mapping: 'departId'}
              ]),    
            sortInfo:{field:'departId',direction:'ASC'}
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
		tbar:[{text:'<b>导出</b>',iconCls:'sort_down',handler:function() {
                	parent.exportExl(recordGrid);
       			} },'-','&nbsp;&nbsp;',{
				text:'<B>添加</B>',
				tooltip : '增加提货公司协议价',
				iconCls : 'userAdd',
				id:'addbtn',
				handler : function() {
						updateUser();
				}
			}, '-',{
				text : '<B>修改</B>',
				id : 'updatebtn',
				disabled : true,
				tooltip : '修改提货公司协议价',
				iconCls:'userEdit',
				handler : function() {
						var _records = recordGrid.getSelectionModel().getSelections();
						if (_records.length == 0) {
							Ext.Msg.alert(alertTitle, '请选择一条您需要修改的数据');
						} else if (_records.length > 1) {
							Ext.Msg.confirm(alertTitle,
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
			 	tooltip : '删除提货公司协议价',
			 	handler : function() {
					deleteUser();
				} 
			},'-','&nbsp;','创建部门:',{
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
					 afterRender:function(combo){
					 		
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
    	    },{
			   xtype : 'combo',
	           allowBlank : true,
	           queryParam : 'filter_LIKES_cusName',
		       minChars : 1,
		       triggerAction : 'all',
			   typeAhead:false,
			   hidden : true,
		       forceSelection : true,
			   store: customerStore,
			   pageSize : 50,
			   id:'customerId2',
			   name:'customerName2',
			   displayField : 'cusName',
			   valueField : 'id',
			   anchor : '95%',
			   emptyText : "请选择提货公司"
     		},{
			   xtype : 'combo',
	           allowBlank : true,
			   hidden : true,
		       forceSelection : true,
			   store: statusStore,
			   triggerAction : 'all',
			   id:'statusS',
			   mode : "local",//获取本地的值
			   name:'name',
			   displayField : 'name',
			   valueField : 'id',
			   anchor : '95%',
			   emptyText : "请选择是否需要板费"
     		},{
				xtype:'textfield',
	 	        id : 'itemsValue',
	 	        disabled:true,
		        name : 'itemsValue',
	            blankText:'查询条件',
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
						 ['EQL_customerId', '提货公司'],
						 ['EQL_isBoardStatus', "是否需要板费"],
						 ['EQD_createTime', '创建时间']
   				    ],
    					emptyText : '选择查询类型',
    					forceSelection : true,
    								listeners : {
    						'select' : function(combo, record, index) { 
    							if (combo.getValue() == 'EQL_customerId') {
    								Ext.getCmp("startDate").disable();
    								Ext.getCmp("startDate").hide();
    								Ext.getCmp("startDate").setValue("");
    								Ext.getCmp("endDate").disable();
    								Ext.getCmp("endDate").hide();
    								Ext.getCmp("endDate").setValue("");
    								
    								Ext.getCmp("customerId2").enable();
    								Ext.getCmp("customerId2").show();
    								
    								Ext.getCmp("statusS").disable();
    								Ext.getCmp("statusS").hide();
    								
    								Ext.getCmp("itemsValue").disable();
    								Ext.getCmp("itemsValue").hide();
    								Ext.getCmp("itemsValue").setValue("");
    							}else if(combo.getValue() == 'EQD_createTime') {
    								Ext.getCmp("startDate").enable();
    								Ext.getCmp("startDate").show();
    								Ext.getCmp("endDate").enable();
    								Ext.getCmp("endDate").show();
    								
    								Ext.getCmp("customerId2").disable();
    								Ext.getCmp("customerId2").hide();
    								Ext.getCmp("customerId2").setValue("");
    								
    								
    							 	Ext.getCmp("itemsValue").disable();
    								Ext.getCmp("itemsValue").hide();
    								
    								Ext.getCmp("statusS").disable();
    								Ext.getCmp("statusS").hide();
    								Ext.getCmp("statusS").setValue("");
    						 	}else if(combo.getValue() == 'EQL_isBoardStatus') {
    						       	Ext.getCmp("startDate").disable();
    						  		Ext.getCmp("startDate").hide();
    								Ext.getCmp("startDate").setValue("");

									Ext.getCmp("customerId2").disable();
    								Ext.getCmp("customerId2").hide();
    								Ext.getCmp("customerId2").setValue("");
    								

    								Ext.getCmp("endDate").disable();
    								Ext.getCmp("endDate").hide();
    								Ext.getCmp("endDate").setValue("");
    								
    							 	Ext.getCmp("itemsValue").disable();
    								Ext.getCmp("itemsValue").hide();
    								
    								Ext.getCmp("statusS").enable();
    								Ext.getCmp("statusS").show();
    								
    						 	}else{
    					         	Ext.getCmp("startDate").disable();
    								Ext.getCmp("startDate").hide();
    								Ext.getCmp("startDate").setValue("");
    								Ext.getCmp("endDate").disable();
    								Ext.getCmp("endDate").hide();
    								Ext.getCmp("endDate").setValue("");
    								
    								Ext.getCmp("customerId2").disable();
    								Ext.getCmp("customerId2").hide();
    								Ext.getCmp("customerId2").setValue("");
    								
    								Ext.getCmp("statusS").disable();
    								Ext.getCmp("statusS").hide();
    								Ext.getCmp("statusS").setValue("");
    								
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
			columns:[ rownum,sm,
        		    {header: 'id', dataIndex: 'id',	hidden : true,sortable : true},
        			{header: '提货公司ID', dataIndex: 'customerId',hidden : true},
        			{header: '提货公司名称', dataIndex: 'customerName'},
        			{header: '货物类型', dataIndex: 'goodsType'},
			        {header: '重量费率', dataIndex: 'rates'},
			        {header: '最低一票', dataIndex: 'lowest',sortable : true},
			        {header: '是否需要板费', dataIndex: 'isBoardStatus',sortable : true,
			        				renderer:function(v){
			        					if( v=='0'){
			        						return '否';
			        					}else if(v=='1'){
			        						return '是';
			        					}else{
			        						return '';
			        					}
			        				}},
			        {header: '板费(101-200)kg', dataIndex: 'board1'},
			        {header: '板费(201-300)kg', dataIndex: 'board2'},
			        {header: '板费(301-400)kg', dataIndex: 'board3'},
			        {header: '板费(401-500)kg', dataIndex: 'board4'},
			        {header: '板费(501-1000)kg', dataIndex: 'board5'},
			        {header: '板费(1001<)kg', dataIndex: 'board6'},
			        {header: '创建人', dataIndex: 'createName',hidden : true},
			        {header: '创建时间', dataIndex: 'createTime',sortable : true,hidden : true},
			        {header: '创建部门ID', dataIndex: 'departId',hidden : true},
			        {header: '创建部门', dataIndex: 'departName',sortable : true,hidden : true},
    				{header: '修改人', dataIndex: 'updateName',hidden : true},
    			    {header: '修改时间', dataIndex: 'updateTime',sortable : true,hidden : true},
    				{header: '备注', dataIndex: 'remark'},
    				{header: '时间戳', dataIndex: 'ts',hidden : true}
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
			var deId = Ext.getCmp('comboRightDepart2').getValue();
 
		    if (Ext.getCmp('comboselect').getValue() == 'EQL_isBoardStatus') {
 		 		var typeValue=Ext.getCmp("statusS").getValue();
 		 		 Ext.apply(dataStore.baseParams, {
    						checkItems : Ext.getCmp('comboselect').getValue(),
    						itemsValue : Ext.getCmp("statusS").getValue(),
    						filter_EQL_isdelete:1,
							filter_EQL_departId:deId,
    						limit : pageSize
    			});
 		 
 		 	}else if(Ext.getCmp('comboselect').getValue() == 'EQD_createTime') {
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
						filter_EQL_isdelete:1,
						filter_EQL_departId:deId,
    					limit : pageSize,
						checkItems : '',
						itemsValue : ''
    			});
    		}else if (Ext.getCmp('comboselect').getValue() == 'EQL_customerId'){
   				 Ext.apply(dataStore.baseParams, {
   						checkItems : Ext.getCmp('comboselect').getValue(),
   						filter_EQL_isdelete:1,
						filter_EQL_departId:deId,
    					limit : pageSize,
    					itemsValue : Ext.getCmp("customerId2").getValue()
   				});
    		}else{	
				Ext.apply(dataStore.baseParams, {
   						filter_EQL_isdelete:1,
						filter_EQL_departId:deId,
    					limit : pageSize
   				});
			}
			var userrolebtn = Ext.getCmp('addbtn');
			var updatebtn = Ext.getCmp('updatebtn');

			userrolebtn.setDisabled(false);
			updatebtn.setDisabled(false);

			dataStore.reload({
					baseParams : {
						start : 0,
						filter_EQL_departId:deId,
						filter_EQL_isdelete:1,
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
				 Ext.Msg.alert(alertTitle, '请选择一条您需要删除的数据');
				 return false;
			  }else {
			     Ext.Msg.confirm(alertTitle,'数据删除后将不可恢复，您确定要删除这<span style="color:red">&nbsp;'+ _records.length + '&nbsp;</span>行数据吗?',function(btnYes) {
									if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
													
								        var ids = "";
   										for(var i = 0; i < _records.length; i++) {
											ids += _records[i].data.id + ",";
										}  	
										form1.getForm().doAction('submit', {
											url : sysPath+ "/fi/fiDeliveryPriceAction!deleteOfStatus.action",
											method : 'post',
											params : {
   													ids : ids,
   													privilege:privilege
   											},
											waitMsg : '正在删除数据...',
											success : function(form1, action) {
												Ext.Msg.alert(
														alertTitle,
														"数据删除成功",
														function() {
															dataStore.reload()
														});
											},
											failure : function(form1, action) {
												Ext.Msg.alert(
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
								labelAlign : 'left',
								frame : true,
							//	url:sysPath+'/user/userAction!ralaList.action?privilege=17',
							
								bodyStyle : 'padding:5px 5px 5px',
							    width : 600,
							    labelWidth : 110,
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
																},{
																	name : "ts",
																	xtype : "hidden"
																},{
																   xtype : 'combo',
										        				   fieldLabel: '提货公司<span style="color:red">*</span>',
														           allowBlank : false,
														           queryParam : 'filter_LIKES_cusName',
															       minChars : 1,
															       triggerAction : 'all',
																   typeAhead:false,
															       forceSelection : true,
																   store: customerStore,
																   pageSize : 50,
																   id:'customerId',
																   name:'customerName',
																   displayField : 'cusName',
																   valueField : 'id',
																   blankText : "提货公司不能为空！",
																   anchor : '95%',
																   emptyText : "请选择提货公司",
													               listeners : {
													                	  render:function(c){
													                	  	if(_record!=null){
													                	  		c.setValue(_record.data.customerId);
													                	  		c.setRawValue(_record.data.customerName);
													                	  	}
													                	  }
													                }
										        				},{
																	xtype : 'textfield',
																	fieldLabel : '货物类型<span style="color:red">*</span>',
																	name : 'goodsType',
																	id:'goodsType',
																	maxLength:12,
																	allowBlank : false,
																	blankText : "货物类型不能为空！",
																	anchor : '95%'
																},{
																	xtype : 'numberfield',
																	labelAlign : 'left',
																	fieldLabel : '最低一票',
																	name : 'lowest',
																	id:'lowest',
																	maxLength:12,
																	allowBlank : true,
																	value:0,
																	anchor : '95%'
																},{
																	xtype : 'numberfield',
																	labelAlign : 'left',
																	fieldLabel : '板费(101-200)kg',
																	name : 'board1',
																	id:'board1',
																	//disabled:true,
																	maxLength:12,
																	allowBlank : true,
																	value:0,
																	anchor : '95%'
																},{
																	xtype : 'numberfield',
																	labelAlign : 'left',
																	fieldLabel : '板费(301-400)kg',
																	name : 'board3',
																	id:'board3',
																	//disabled:true,
																	maxLength:12,
																	allowBlank : true,
																	value:0,
																	anchor : '95%'
																},{
																	xtype : 'numberfield',
																	labelAlign : 'left',
																	fieldLabel : '板费(501-1000)kg',
																	name : 'board5',
																	id:'board5',
																	//disabled:true,
																	maxLength:12,
																	allowBlank : true,
																	value:0,
																	anchor : '95%'
																}]

													}, {
														columnWidth : .5,
														layout : 'form',
														items : [{
																	xtype : 'numberfield',
																	labelAlign : 'left',
																	fieldLabel : '费率<span style="color:red">*</span>',
																	name : 'rates',
																	id:'rates',
																	maxLength:12,
																	allowBlank : false,
																	blankText : "费率不能为空！",
																	anchor : '95%'
																},{xtype: 'radiogroup',
													                fieldLabel: '是否需要板费',
													                columns: 3,
													                id:"isBoardStatus",
													                defaults: {
													                    name: 'isBoardStatus' 
													                },
													                listeners : {
													                	  change:function(c){
														                	  	if(Ext.getCmp('board1').getEl().dom.disabled==true){
														                	  		Ext.getCmp('board1').enable();
														                	  	}else{
														                	  		Ext.getCmp('board1').disable();
														                	  	}
														                	    if(Ext.getCmp('board2').getEl().dom.disabled==true){
														                	  		Ext.getCmp('board2').enable();
														                	  	}else{
														                	  		Ext.getCmp('board2').disable();
														                	  	}
														                	  	if(Ext.getCmp('board3').getEl().dom.disabled==true){
														                	  		Ext.getCmp('board3').enable();
														                	  	}else{
														                	  		Ext.getCmp('board3').disable();
														                	  	}
														                	  	if(Ext.getCmp('board4').getEl().dom.disabled==true){
														                	  		Ext.getCmp('board4').enable();
														                	  	}else{
														                	  		Ext.getCmp('board4').disable();
														                	  	}
														                	  	if(Ext.getCmp('board5').getEl().dom.disabled==true){
														                	  		Ext.getCmp('board5').enable();
														                	  	}else{
														                	  		Ext.getCmp('board5').disable();
														                	  	}
														                	  	if(Ext.getCmp('board6').getEl().dom.disabled==true){
														                	  		Ext.getCmp('board6').enable();
														                	  	}else{
														                	  		Ext.getCmp('board6').disable();
														                	  	}
													                	  },
													                	  render:function(v){
													                	  		if(_record!=null){
													                	  			if(_record.data.isBoardStatus==1){
													                	  				Ext.getCmp('board1').enable();
													                	  				Ext.getCmp('board2').enable();
													                	  				Ext.getCmp('board3').enable();
													                	  				Ext.getCmp('board4').enable();
													                	  				Ext.getCmp('board5').enable();
													                	  				Ext.getCmp('board6').enable();
													                	  			}else{
													                	  				Ext.getCmp('board1').disable();
													                	  				Ext.getCmp('board2').disable();
													                	  				Ext.getCmp('board3').disable();
													                	  				Ext.getCmp('board4').disable();
													                	  				Ext.getCmp('board5').disable();
													                	  				Ext.getCmp('board6').disable();
													                	  			}
													                	  		}
													                	  	
													                	  }
													                },
													                items: [{
													                    inputValue: '0',
													                    boxLabel: '否',
													                    checked:_record==null?false:(_record.data.isBoardStatus=="0"?true:false)
													                },{
													                    inputValue: '1',
													                    boxLabel: '是',
													                    checked:_record==null?true:(_record.data.isBoardStatus=="1"?true:false)
													                }]
																},{
																	xtype : 'numberfield',
																	labelAlign : 'left',
																	fieldLabel : '板费(201-300)kg',
																	name : 'board2',
																	id:'board2',
																	//disabled:true,
																	maxLength:12,
																	allowBlank : true,
																	value:0,
																	anchor : '95%'
																},{
																	xtype : 'numberfield',
																	labelAlign : 'left',
																	fieldLabel : '板费(401-500)kg',
																	name : 'board4',
																	id:'board4',
																	maxLength:12,
																	//disabled:true,
																	allowBlank : true,
																	value:0,
																	anchor : '95%'
																},{
																	xtype : 'numberfield',
																	labelAlign : 'left',
																	fieldLabel : '板费(1001<=)kg',
																	name : 'board6',
																	id:'board6',
																	//disabled:true,
																	maxLength:12,
																	allowBlank : true,
																	value:0,
																	anchor : '95%'
																}]
													}]
													
										},{
											labelAlign : 'top',
											xtype : 'textarea',
											name : 'remark',
											fieldLabel : '备注',
											height : 50,
											width:'95%'
										}]
										});
				  
		
							
		carTitle='新增提货公司协议价';
		if(_record!=null){
			carTitle='修改提货公司协议价';
			form.load({
				url : sysPath+ "/"+ralaListUrl,
				params:{filter_EQL_id: _record.data.id, privilege:privilege,limit : pageSize},
				success : function(response){
					Ext.getCmp('customerId').disable();
					if(_record!=null){
               	  		Ext.getCmp('customerId').setValue(_record.data.customerId);
               	  		Ext.getCmp('customerId').setRawValue(_record.data.customerName);
               	  		Ext.getCmp('goodsType').disable();
               	  	}
				}// 回调函数有1个参数
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
				id:'savaF',
				iconCls : 'groupSave',
				handler : function() {
					if (form.getForm().isValid()) {
						this.disabled = true;//只能点击一次
						form.getForm().submit({
							url : sysPath+ '/'+saveUrl,
							params:{
								privilege:privilege,
								customerId : Ext.getCmp('customerId').getValue(),
								isdelete:1
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
									Ext.Msg.alert(alertTitle,"服务器连接异常，请联系管理员！");
								}else{
									Ext.Msg.alert(alertTitle,action.result.msg,function(){});
								}
							}
						});
						this.disabled = false;
					}
				}
			}, {
				text : "重置",
				iconCls : 'refresh',
				handler : function() {		
						if(_record==null){
							form.getForm().reset();
						
						}else{
							if(_record!=null){
								carTitle='修改提货公司协议价';
								form.load({
									url : sysPath+ "/"+ralaListUrl,
									params:{filter_EQL_id: _record.data.id, privilege:privilege,limit : pageSize},
									success : function(response){
										Ext.getCmp('customerId').disable();
										if(_record!=null){
					            	  		Ext.getCmp('customerId').setValue(_record.data.customerId);
					            	  		Ext.getCmp('customerId').setRawValue(_record.data.customerName);
					            	  		Ext.getCmp('goodsType').disable();
					            	  	}
									}// 回调函数有1个参数
								})
							}
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

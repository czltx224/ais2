	Ext.QuickTips.init();
	var privilege=181;
	var comboxPage=comboSize;
	var departGridSearchUrl = "sys/departAction!findAll.action";
	var saveUrl="fi/fiAppreciationServicesAction!save.action";
	var customerGridSearchUrl="sys/customerAction!list.action";
	var areaListUrl="stock/oprStoreAreaAction!list.action";
	var ralaListUrl="fi/fiAppreciationServicesAction!ralaList.action";
	var customerGridSearchUrl = "sys/customerAction!list.action";
	var dictionaryUrl='sys/dictionaryAction!ralaList.action';
	
Ext.onReady(function(){
	Ext.QuickTips.init();
	var rownum=new Ext.grid.RowNumberer({
		header:'No.',
        width : 25
	});
		
	var fields=[
	'id',
	'cosignee',
	'inDepart',
	'dno',  //配送单号
  	'customerId',  //客商
	'customerName',  
	'appreciationType',   // 增服务费类型
	'incomeAmount',  // 收入金额
	'costAmount',  //  成本金额
	'recipientsUser',    //    领款人
	'confirmUser',//   客户确认人
	'phome',   //   联系方式
	'handleUser',  // 经手人
 	'remark',   //备注
	'paymentStatus',   //付款状态(1：未付款,2：已付款)
	'serviceAuditStatus',   //客服审核状态(1:未审核,2:已审核)
	'serviceAuditUser',   //客服审核人
  	'serviceAuditDate',   //客服审核时间
	'accountAuditUser',   //会计审核人
	'accountAuditDate',   //会计审核时间
	'accountAuditStatus',   //会计审核状态
	'departId',  //
	'paymentMode',	
	'departName',  //
	'createTime',  //
	'createName',   //
	'updateTime',  //
	'updateName',   //
	'ts',  //
	'paymentType'
	];
    			
    var jsonread= new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'},
                    fields);
                              
	var sm = new Ext.grid.CheckboxSelectionModel({
		width:25,
		moveEditorOnEnter:true, 
		id:'checkId',
		name:'checkId'
	});
	
	//增值服务类型
	 var carTypeStore= new Ext.data.Store({ 
		autoLoad:true, 
		storeId:"",
		baseParams:{filter_EQL_basDictionaryId:145,privilege:16},
		proxy:new Ext.data.HttpProxy({url:sysPath+"/"+dictionaryUrl}),
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
    	{name:'typeCode',mapping:'typeCode'},
    	{name:'typeName',mapping:'typeName'}
    	])
	});
	carTypeStore.load();
	
	//付款方
	payManStore	= new Ext.data.Store({ 
		storeId:"payManStore",
		baseParams:{filter_EQL_basDictionaryId:8,privilege:16},
		proxy:new Ext.data.HttpProxy({url:sysPath+"/"+dictionaryUrl}),
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
       	{name:'payManId',mapping:'typeCode'},
       	{name:'payManName',mapping:'typeName'}
       	])
	});
	
	//到款方式
	var payModeStore=new Ext.data.SimpleStore({
		auteLoad:true, //此处设置为自动加载
		storeId:"pa",
  	  	data:[['1','月结'],['0','到付']],
   		fields:["id","name"]
	});
	
	//权限部门
	var rightDepartStore2 = new Ext.data.Store({ 
            storeId:"rightDepartStore2",                        
            proxy: new Ext.data.HttpProxy({url:sysPath+"/sys/basRightDepartAction!findDepartName.action",method:'post'}),
            baseParams:{
            	privilege:63,
            	filter_EQL_userId:userId
            },
            reader: new Ext.data.JsonReader({
            root: 'resultMap', totalProperty: 'totalCount'
           }, [{name:'departName', mapping:'DEPARTNAME'},
               {name:'departId', mapping:'RIGHTDEPARTID'}             
              ])    
    });
    
	var inDepartStore = new Ext.data.Store({ 
            storeId:"inDepartStore",                        
            proxy: new Ext.data.HttpProxy({url:sysPath+"/sys/departAction!ralaList.action",method:'post'}),
            baseParams:{privilege:53},
            reader: new Ext.data.JsonReader({
            root: 'result', totalProperty:'totalCount'
           }, [  
                 {name:'departName', mapping:'departName'},    
                 {name:'departId', mapping: 'departId'}
              ]),                                      
            sortInfo:{field:'departId',direction:'ASC'}  
     });
	inDepartStore.load();
	
	
	//车辆用途加载
	 var carDoStore= new Ext.data.Store({ 
		autoLoad:true, 
		storeId:"carDoStore",
		baseParams:{filter_EQL_basDictionaryId:31,privilege:16},
		proxy:new Ext.data.HttpProxy({url:sysPath+"/"+dictionaryUrl}),
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
    	{name:'id',mapping:'typeCode'},
    	{name:'carDoName',mapping:'typeName'}
    	])
	});
	
	 carDoStore.load();
	 
	// 客商列表
	customerStore = new Ext.data.Store({
		storeId : "customerStore",
		proxy : new Ext.data.HttpProxy({
					url : sysPath + "/" + customerGridSearchUrl
				}),
		reader : new Ext.data.JsonReader({
					root : 'result',
					totalProperty : 'totalCount'
				}, [{
							name : 'customerName',
							mapping : 'cusName'
						}, {
							name : 'customerId',
							mapping : 'id'
						}])
	});
	
	customerStore2 = new Ext.data.Store({
		storeId : "customerStore2",
		proxy : new Ext.data.HttpProxy({
					url : sysPath + "/" + customerGridSearchUrl
				}),
		reader : new Ext.data.JsonReader({
					root : 'result',
					totalProperty : 'totalCount'
				}, [{
							name : 'customerName',
							mapping : 'cusName'
						}, {
							name : 'customerId',
							mapping : 'id'
						}])
	});
	
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
	
	var statusDataStore=new Ext.data.SimpleStore({
		auteLoad:true, //此处设置为自动加载
  	  	data:[['0','已作废'],
  	  		  ['1','新增'],
  	  		  ['2','待审核'],
  	  		  ['3','车队已审核'],
  	  		  ['4','财务已审核'],
  	  		  ['5','财务已收款']],
   		fields:["id","name"]
	});
	
	var serviceStore=new Ext.data.SimpleStore({
		auteLoad:true, //此处设置为自动加载
  	  	data:[['1','未审核'],['2','已审核']],
   		fields:["id","name"]
	});
	
	var fiStore=new Ext.data.SimpleStore({
		auteLoad:true, //此处设置为自动加载
  	  	data:[['1','未审核'],['2','已审核']],
   		fields:["id","name"]
	});
	
	var menuStore = new Ext.data.Store({ 
         storeId:"menuStore",                        
         proxy: new Ext.data.HttpProxy({url:sysPath+"/sys/departAction!findDepart.action",method:'post'}),
         baseParams:{
         	privilege:53,
         	filter_EQL_isBussinessDepa:1
         
         },
         reader: new Ext.data.JsonReader({
         root: 'resultMap', totalProperty:'totalCount'
        }, [  
              {name:'departName', mapping:'DEPARTNAME'},    
              {name:'departId', mapping: 'DEPARTID'}
           ]),                                      
         sortInfo:{field:'departId',direction:'ASC'}
     });

	
		// 部门列表
	var departStore = new Ext.data.Store({
		storeId : "departStore",
		proxy : new Ext.data.HttpProxy({
					url : sysPath + "/" + departGridSearchUrl
				}),
		baseParams:{
              privilege:53,
              filter_EQL_isBussinessDepa:1
           },
		reader : new Ext.data.JsonReader({
					root : 'result',
					totalProperty : 'totalCount'
				}, [{
							name : 'departName',
							mapping : 'departName'
						}, {
							name : 'departId',
							mapping : 'departId'
						}])
	});
	departStore.load();
	
	var dataStore = new Ext.data.Store({
                proxy: new Ext.data.HttpProxy({url:sysPath+"/"+ralaListUrl}),
                baseParams:{
                	limit: pageSize,
                	privilege:privilege
                },
                reader:jsonread
    });
 	
 	var twobar = new Ext.Toolbar({
		    	items : ['&nbsp;','创建部门:',{
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
					    },'-','&nbsp;','录单部门:',  //
							{
							xtype : 'combo',
							id:'comboTypeDepart',
							triggerAction : 'all',
							store : menuStore,
							width : 100,
							listWidth:245,
							minChars : 1,
							allowBlank : true,
							emptyText : "请选择录单部门名称",
							forceSelection : true,
							editable : true,
							pageSize:comboxPage,
							displayField : 'departName',//显示值，与fields对应
							valueField : 'departId',//value值，与fields对应
							name : 'departId',
							anchor : '100%',
							enableKeyEvents:true,
							listeners : {
								 keyup:function(numberfield, e){
						             if(e.getKey() == 13 ){
											searchLog();
						              }
								 }
						    }
			},'-','&nbsp;','客服审核状态:',{
				xtype : 'combo',
				typeAhead : false,
				forceSelection : true,
				width : 60,
				id:'serviceStatus',
				mode:'local',
				triggerAction : 'all',
				store : serviceStore,
				displayField : 'name',
				valueField : 'id',
				emptyText : "请选择状态"
					
			},'-','&nbsp;','会计审核状态:',{
				xtype : 'combo',
				typeAhead : false,
				forceSelection : true,
				width : 60,
				id:'fiStatus',
				mode:'local',
				triggerAction : 'all',
				store : serviceStore,
				displayField : 'name',
				valueField : 'id',
				emptyText : "请选择状态"
					
			},'-','&nbsp;','配送单号<span style="color:red">*</span>:', {
				xtype : 'numberfield',
				labelAlign : 'right',
				name : 'dno',
				width:60,
				selectOnFocus:true,
				decimalPrecision:0,
				allowNegative:false,
				id:'dno',
				maxLength:12,
				allowBlank : true,
				enableKeyEvents:true,
	    		listeners : {
	    		     keyup:function(numberfield, e){
	                     if(e.getKey() == 13 ){
							searchLog();
	                     }
	 				 }
	    		}
			}]
		});
     
	var recordGrid=new Ext.grid.EditorGridPanel({
		renderTo:'recordGrid',
		renderTo:Ext.getBody(),
	//	el : 'recordGrid',
		id:'userCenter',
		height : Ext.lib.Dom.getViewHeight()-1,
		width : Ext.lib.Dom.getViewWidth()-1,
	//	autoScroll : true,
		viewConfig : {
			scrollOffset: 0,
			//forceFit : true,
			autoScroll:true,
			columnsText : "显示的列",
			sortAscText : "升序",
			sortDescText : "降序"
		},
		//autoExpandColumn : 1,
		frame : false,
	//	clicksToEdit:1,
		loadMask : true,
		sm:sm,
		stripeRows : true,
		tbar:['&nbsp;',{
				text : '<b>新 增</b>',
				iconCls : 'groupAdd',
				disabled:false,
				id : 'costin',
				handler : function(){
					addNewForm();
				}
			},'-','&nbsp;',
			{
				text : '<b>修 改</b>',
				iconCls : 'userEdit',
				id : 'carAduit',
				disabled:false,
				handler : function(){
					var vnetmusicRecord = recordGrid.getSelectionModel().getSelections();
					if (vnetmusicRecord.length == 1) {
						if(vnetmusicRecord[0].data.serviceAuditStatus=="2"){
							Ext.Msg.alert(alertTitle,"客服已经审核，不能修改了",function(){
							});
						}else{
							if(vnetmusicRecord[0].data.serviceAuditStatus=="2"){
								Ext.Msg.alert(alertTitle,"会计已经审核，不能修改了",function(){
								});
							}else{
								addNewForm(vnetmusicRecord[0]);
							}
						}
					}else{
						Ext.Msg.alert(alertTitle,"只能选择一条记录进行会计",function(){
						});
					}
				}
			},'-','&nbsp;',
			{
				text : '<b>客服审核</b>',
				iconCls : 'groupPass',
				id : 'serviceAduit',
				handler : function(){
					var vnetmusicRecord = recordGrid.getSelectionModel().getSelections();
					if (vnetmusicRecord.length >0){
						var f=true;
						for(var i=0;i<vnetmusicRecord.length;i++){
							if(vnetmusicRecord[0].data.serviceAuditStatus=="2"){
								f=false;
								Ext.Msg.alert(alertTitle,"客服已经审核，不能多次审核",function(){
								});
							}else{
								if(vnetmusicRecord[0].data.accountAuditStatus=="2"){
									f=false;
									Ext.Msg.alert(alertTitle,"会计已经审核，不能再次审核",function(){
									});
								}else{
								}
							}
						}
						if(f){	
							accountAduit(vnetmusicRecord);
						}
						
						
					}else{
						Ext.Msg.alert(alertTitle,"请选择一条记录时行客服审核",function(){});
					}
				}
			},'-','&nbsp;',
			{
				text : '<b>会计审核</b>',
				iconCls : 'groupPass',
				id : 'fiAduit',
				tooltip : '会计审核',
				handler : function(){
					var vnetmusicRecord = recordGrid.getSelectionModel().getSelections();
					if (vnetmusicRecord.length >0) {
						var f=true;
						for(var i=0;i<vnetmusicRecord.length;i++){
							if(vnetmusicRecord[i].data.serviceAuditStatus=="1"){
								Ext.Msg.alert(alertTitle,"客服未审核，不能进行会计审核，请先进行客服审核",function(){
								});
								f=false;
							}else{
								if(vnetmusicRecord[i].data.accountAuditStatus=="2"){
									f=false;
									Ext.Msg.alert(alertTitle,"会计已经审核，不能再次审核",function(){
									});
								}
							}
						}
						if(f){	
							fiAduit(vnetmusicRecord);
						}
					}else{
						Ext.Msg.alert(alertTitle,"请选择一条记录时行客服审核",function(){});
					}
				}
			},'-','&nbsp;&nbsp;',{
				text : '<b>导出</b>',
				iconCls : 'sort_down',
				id : 'exportbtn',
				tooltip : '导出',
				handler : exportinfo
			}/*,'-','&nbsp;&nbsp;',{
				text : '<b>打印</b>',
				iconCls : 'table',
				id : 'printbtn',
				tooltip : '打印',
				handler : printinfo
			}*/,'-','&nbsp;',{
    				     text : '<b>查 询</b>',
    				     id : 'btn',
    				     iconCls : 'btnSearch',
    					 handler : searchLog
    				},'-','&nbsp;',{
   			xtype:'label',
   			id:'showMsg2',
   			width:380
   		}],
			columns:[ rownum,sm,
        			{header: '编号', dataIndex: 'id',width:55,sortable : true},
			        {header: '配送单号 ', dataIndex: 'dno',width:80,sortable : true},
			        {header: '客商', dataIndex: 'customerName',width:90,sortable : true},
			        {header: '收货人', dataIndex: 'cosignee',width:60,sortable : true},
			        {header: '开单部门', dataIndex: 'inDepart',width:60,sortable : true},
			        {header: '付款方', dataIndex: 'paymentType',width:60,sortable : true},
			        {header: '增值服务费类型', dataIndex: 'appreciationType',width:60,sortable : true},
			        {header: '收入金额', dataIndex: 'incomeAmount',width:60,sortable : true},　
			        {header: '费用金额', dataIndex: 'costAmount',width:60,sortable : true},
					{header: '领款人', dataIndex: 'recipientsUser',width:60,sortable : true},
					{header: '联系方式', dataIndex: 'phome',width:70,sortable : true},
					{header: '经手人', dataIndex: 'handleUser',width:80,sortable : true},
			        {header: '客户确认人', dataIndex: 'confirmUser',width:80,sortable : true},
					{header: '付款状态', dataIndex: 'paymentStatus',width:90,sortable : true,renderer:function(v){
			        					if(v=='2'){
			        						return '已付款';
			        					}else{
			        						 return '未付款';
			        					}}},
			        {header: '客服审核状态', dataIndex: 'serviceAuditStatus',width:80,hidden:true,sortable : true,renderer:function(v){
			        					if(v=='1') return '未审核';
			        					if(v=='2') return '已审核';
			        					}},
			        {header: '客服审核人', dataIndex: 'serviceAuditUser',width:80,sortable : true},
			        {header: '客服审核时间', dataIndex: 'serviceAuditDate',width:80,sortable : true},
			        {header: '会计审核状态', dataIndex: 'accountAuditStatus',width:80,sortable : true,renderer:function(v){
			        					if(v=='1') return '未审核';
			        					if(v=='2') return '已审核';
			        					}},
			        {header: '会计审核人', dataIndex: 'accountAuditUser',width:80,sortable : true},
			        {header: '会计审核时间', dataIndex: 'accountAuditDate',width:80,sortable : true},
			        {header: '备注', dataIndex: 'remark',width:120,sortable : true},
			        {header: '创建人', dataIndex: 'createName',width:80,hidden:true,sortable : true},
			        {header: '创建时间', dataIndex: 'createTime',width:80,hidden:true,sortable : true},
			        {header: '修改人', dataIndex: 'updateName',width:80,hidden:true,sortable : true},
			        {header: '修改时间', dataIndex: 'updateTime',width:80,hidden:true,sortable : true},
			        {header: '创建部门ID', dataIndex: 'departId',width:80,hidden:true,sortable : true},
			        {header: '创建部门', dataIndex: 'departName',width:80,hidden:true,sortable : true},
			        {header: '时间戳', dataIndex: 'ts',width:55,hidden:true,sortable : true}
			    ],
			store : dataStore,
			listeners: {
                    render: function(){
                        twobar.render(this.tbar);
                    }
            },
			bbar : new Ext.PagingToolbar({
					pageSize : pageSize, 
					store : dataStore,
					displayInfo : true,
					displayMsg : '显示第 {0} 条到 {1} 条记录，共 {2} 条',
					emptyMsg : "没有记录信息显示"
			})
	});

	
	recordGrid.render();
	
	function searchLog() {
			var dNo=Ext.get("dno").dom.value;
			var dId= Ext.getCmp('comboRightDepart2').getValue();
			Ext.getCmp('showMsg2').getEl().update('<span style="color:red"></span>');
			if(dNo==''){
				var iId=Ext.getCmp('comboTypeDepart').getValue();
				var sstatus=Ext.getCmp('serviceStatus').getValue();
				var fstatus=Ext.getCmp('fiStatus').getValue();
		    	
	    		Ext.apply(dataStore.baseParams={
	            	filter_EQL_departId : dId,
	            	privilege:privilege,
	            	filter_EQL_inDepartId : iId,
					filter_EQL_serviceAuditStatus : sstatus,
					filter_EQL_accountAuditStatus : fstatus
	   			});
			}else{
				var regex=/^[1-9]\d*$/;
			    if(!regex.test(dNo)){
			       	Ext.getCmp('showMsg2').getEl().update('<span style="color:red">配送单号输入格式不正确，无法查询。</span>');
			       	Ext.getCmp('dno').focus();	
			       	Ext.getCmp('dno').markInvalid("配送单号输入格式不正确");
					Ext.getCmp('dno').selectText();	
			     
					return;
			    }
				Ext.getCmp('showMsg2').getEl().update('<span style="color:red">输入配送单号查询时,除创建部门外其他查询条件将无效。</span>');
				Ext.apply(dataStore.baseParams={
					filter_EQL_dno:dNo,
					privilege:privilege,
					filter_EQL_departId : dId,
					limit : pageSize
				});
			
			
			}
			dataStore.reload({
					params : {
						start : 0,
						privilege:privilege,
						limit : pageSize
					}
			});
	}
		
		recordGrid.on('click', function() {
			select();
		});
			
		function select(){
		
		}

  function addNewForm(_record){
  	 var formn = new Ext.form.FormPanel({
								labelAlign : 'left',
								frame : true,
							//	url:sysPath+'/user/userAction!ralaList.action?privilege=17',
							
								bodyStyle : 'padding:5px 5px 5px',
							    width : 600,
							    labelWidth : 80,
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
																	id:'customerId',
																	name : "customerId",
																	xtype : "hidden"
																},{
																	xtype : 'numberfield',
																	labelAlign : 'right',
																	fieldLabel : '配送单号<span style="color:red">*</span>',
																	name : 'dno',
																	emptyText : "必填项",
																	anchor : '95%',
																	selectOnFocus:true,
																	decimalPrecision:0,
																	allowNegative:false,
																	id:'dno',
																	maxLength:12,
																	allowBlank : true,
																	enableKeyEvents:true,
														    		listeners : {
														    			 blur:function(v){
														    			 	if(v.getValue()!=""){
															    			 	Ext.getCmp('showMsg').getEl().update('');
															    			 	Ext.Ajax.request({
														 							url:sysPath+"/fax/oprFaxInAction!ralaList.action",
																					params:{
																						privilege:68,
																						filter_EQL_dno:v.getValue()
																					},
																					success : function(response) { // 回调函数有1个参数
																						if(Ext.decode(response.responseText).result.length==0){
																							Ext.getCmp('showMsg').getEl().update('<span style="color:red">配送单号为'+v.getValue()+'的记录不存在，请重新输入</span>');
																							v.focus();
																							v.markInvalid("配送单号不存在!");
																						}else{
																							Ext.getCmp('inDepart').setRawValue(Ext.decode(response.responseText).result[0].inDepart); //
																							Ext.getCmp('consignee').setValue(Ext.decode(response.responseText).result[0].consignee);
																							Ext.getCmp('customerId').setValue(Ext.decode(response.responseText).result[0].cusId);
																							Ext.getCmp('customerName').setValue(Ext.decode(response.responseText).result[0].cpName);
																						
																						}
																					},
																					failure : function(response) {
																						
																					}
																				});	
														    			 	}
														    			 },
														    			 focus:function(v){
														    			 },
														    		     keyup:function(numberfield, e){
														                     if(e.getKey() == 13 ){
																				Ext.getCmp('appreciationType').focus();
														                     }
														 				 }
														    		}
																},{xtype : 'textfield',
																	labelAlign : 'left',
																	fieldLabel : '发货代理',
																	readOnly:true,
																	emptyText : '自动显示',
																	name : 'customerName',
																	id:'customerName',
																	maxLength:20,
																	selectOnFocus:true,
																	allowBlank : false,
																	anchor : '95%'
																},{
																	xtype : 'combo',
																	id:'appreciationType',
																	triggerAction : 'all',
																	name:'appreciationType',
																	store : carTypeStore,
																	mode:'local',
																	selectOnFocus:true,
																	allowBlank : false,
																	emptyText : "请选择增值服务费类型",
																	forceSelection : false,
																	fieldLabel:'费用类型<span style="color:red">*</span>',
																	editable : false,
																	pageSize:500,
																	displayField : 'typeName',//显示值，与fields对应
																	valueField : 'typeName',//value值，与fields对应
																	anchor : '95%',
																	enableKeyEvents:true,
																	listeners : {
																		keyup:function(numberfield, e){
														                     if(e.getKey() == 13 ){
																				Ext.getCmp('paymentType').focus();
														                     }
														 				 }
																	}
																 		
																 },{
																	xtype : 'combo',
																	id:'paymentMode',
																	store : payModeStore,
																	allowBlank : true,
																	mode : "remote",//获取本地的值
																	emptyText : '自动显示',
																	fieldLabel:'付款方式',
																	editable : false,
																	displayField : 'name',//显示值，与fields对应
																	valueField : 'name',//value值，与fields对应
																	name : 'paymentMode',
																	anchor : '95%',
																	enableKeyEvents:true,
														    		listeners : {
														    		     keyup:function(numberfield, e){
														                     if(e.getKey() == 13 ){
																				 Ext.getCmp('incomeAmount').focus()
														                     }
														 				 }
														    		}
																},{xtype : 'textfield',
																	labelAlign : 'left',
																	fieldLabel : '领款人',
																	name : 'recipientsUser',
																	id:'recipientsUser',
																	emptyText:'非必填项',
																	maxLength:20,
																	selectOnFocus:true,
																	allowBlank : true,
																	anchor : '95%',
																	enableKeyEvents:true,
														    		listeners : {
														    		     keyup:function(numberfield, e){
														                     if(e.getKey() == 13 ){
																				Ext.getCmp('costAmount').focus();
														                     }
														 				 }
														    		}
																},{xtype : 'textfield',
																	labelAlign : 'left',
																	fieldLabel : '经手人<span style="color:red">*</span>',
																	name : 'handleUser',
																	selectOnFocus:true,
																	id:'handleUser',
																	emptyText:'必填选',
																	maxLength:20,
																	allowBlank : false,
																	anchor : '95%',
																	enableKeyEvents:true,
														    		listeners : {
														    		     keyup:function(numberfield, e){
														                     if(e.getKey() == 13 ){
																				Ext.getCmp('phome').focus();
														                     }
														 				 }
														    		}
																},{xtype : 'textfield',
																	labelAlign : 'left',
																	fieldLabel : '客户确认人<span style="color:red">*</span>',
																	name : 'confirmUser',
																	id:'confirmUser',
																	emptyText : "必填项",
																	selectOnFocus:true,
																	maxLength:20,
																	allowBlank : false,
																	anchor : '95%',
																	enableKeyEvents:true,
														    		listeners : {
														    		     keyup:function(numberfield, e){
														                     if(e.getKey() == 13 ){
																				Ext.getCmp('remark').focus();
														                     }
														 				 }
														    		}
																}]

													}, {
														columnWidth : .5,
														layout : 'form',
														items : [{xtype : 'textfield',
																	labelAlign : 'left',
																	fieldLabel : '开单部门',
																	readOnly:true,
																	emptyText : '自动显示',
																	name : 'inDepart',
																	id:'inDepart',
																	maxLength:20,
																	selectOnFocus:true,
																	allowBlank : 	true,
																	anchor : '95%',
																	listeners : {
														 				keyup:function(numberfield, e){
														                     if(e.getKey() == 13 ){
																				Ext.getCmp('queryCondition').focus();
														                     }
														 				}
														 			}
										        				},{xtype : 'textfield',
																	labelAlign : 'left',
																	fieldLabel : '收货人',
																	name : 'consignee',
																	readOnly:true,
																	emptyText : '自动显示',
																	id:'consignee',
																	maxLength:20,
																	selectOnFocus:true,
																	allowBlank : false,
																	anchor : '95%'
																},{
																	xtype : 'combo',
																	triggerAction : 'all',
																	id:'paymentType',
																	store : payManStore,
																	selectOnFocus:true,
																	mode : "remote",//获取本地的值
																	allowBlank : false,
																	emptyText : '请选择付款方',
																	forceSelection : true,
																	fieldLabel:'付款方<font style="color:red;">*</font>',
																	editable : false,
																	displayField : 'payManName',//显示值，与fields对应
																	valueField : 'payManName',//value值，与fields对应
																	name : 'paymentType',
																	anchor : '95%',
																	enableKeyEvents:true,
														    		listeners : {
														    		     keyup:function(numberfield, e){
														                     if(e.getKey() == 13 ){
																				 Ext.getCmp('incomeAmount').focus()
														                     }
														 				 },
														 				 select:function(v){
														 				 	Ext.getCmp('showMsg').getEl().update('<span style="color:red"></span>');
														 				 	if(v.getValue()=='收货人'){
														 				 		Ext.getCmp('paymentMode').setValue("到付");
														 				 	}else{  
														 				 		if(Ext.getCmp('customerId').getValue()!=""){
																    			 	Ext.Ajax.request({
															 							url:sysPath+"/sys/customerAction!list.action",
																						params:{
																							filter_EQL_id:Ext.getCmp('customerId').getValue()
																						},
																						success : function(response) { // 回调函数有1个参数
																							if(Ext.decode(response.responseText).result.length==0){
																								Ext.getCmp('showMsg').getEl().update('<span style="color:red">客商为'+Ext.getCmp('customerName').getValue()+'的记录不存在，请重新输入</span>');
																								Ext.getCmp('paymentMode').setValue("到付");
																							}else{
																								if(Ext.decode(response.responseText).result[0].settlement=="月结"){
																									Ext.getCmp('paymentMode').setValue("月结");
																								}else{
																									Ext.getCmp('paymentMode').setValue("到付");
																								}
																							}
																						},
																						failure : function(response) {
																							Ext.getCmp('showMsg').getEl().update('<span style="color:red">获取付款人的付款方式失败，请重试</span>');
																						}
																					});	
														    			 	}else{
														    			 		Ext.getCmp('dno').focus();
														    			 		Ext.getCmp('showMsg').getEl().update('<span style="color:red">请先输入配送单号，再选择付款方</span>');
														    			 	}
														 				 	
														 				 	
														 				 	
														 				 	}
														 				 }
														    		}
																},{
																	xtype : 'numberfield',
																	labelAlign : 'right',
																	fieldLabel : '收入金额<span style="color:red">*</span>',
																	name : 'incomeAmount',
																	emptyText : "必填项",
																	selectOnFocus:true,
																	value:0,
																	decimalPrecision:2,
																	anchor : '95%',
																	selectOnFocus:true,
																	allowNegative:false,
																	id:'incomeAmount',
																	maxLength:12,
																	allowBlank : true,
																	enableKeyEvents:true,
														    		listeners : {
														    			blur:function(c){
														    				if(c.getValue()==""){
														    					c.setValue(0);
														    				}
														    			},
														    		     keyup:function(numberfield, e){
														                     if(e.getKey() == 13 ){
																				Ext.getCmp('recipientsUser').focus();
														                     }
														 				 }
														    		}
																},{
																	xtype : 'numberfield',
																	labelAlign : 'right',
																	fieldLabel : '费用金额<span style="color:red">*</span>',
																	name : 'costAmount',
																	emptyText : "必填项",
																	selectOnFocus:true,
																	anchor : '95%',
																	value:0,
																	decimalPrecision:2,
																	allowNegative:false,
																	id:'costAmount',
																	maxLength:12,
																	allowBlank : true,
																	enableKeyEvents:true,
														    		listeners : {
														    			blur:function(c){
														    				if(c.getValue()==""){
														    					c.setValue(0);
														    				}
														    			},
														    		     keyup:function(numberfield, e){
														                     if(e.getKey() == 13 ){
																				Ext.getCmp('handleUser').focus();
														                     }
														 				 }
														    		}
																},{
																	xtype : 'numberfield',
																	labelAlign : 'left',
																	fieldLabel : '联系方式<span style="color:red">*</span>',
																	maxLength:20,
																	emptyText : "必填项",
																	selectOnFocus:true,
																	allowBlank : false,
																	name : 'phome',
																	id:'phome',
																	anchor : '95%',
																	blankText : "联系方式不能为空！",
																	enableKeyEvents:true,
														    		listeners : {
														    		     keyup:function(numberfield, e){
														                     if(e.getKey() == 13 ){
																				Ext.getCmp('confirmUser').focus();
														                     }
														 				 }
														    		}
																},{
													    			xtype:'label',
													    			id:'showMsg'
													    		}]
													}]
													
										},{
											labelAlign : 'top',
											xtype : 'textarea',
											name : 'remark',
											id:'remark',
											selectOnFocus:true,
											maxLength:500,
											fieldLabel : '备   注',
											height : 50,
											width:'95%'
										}]
										});
		
							
		carTitle='新增增值服务费';
		if(_record!=null){
			carTitle='修改增值服务费';
		//	formn.getForm().loadRecord(_record);
			formn.load({
				url : sysPath+ "/"+ralaListUrl,
				params:{
					filter_EQL_id: _record.data.id,
					privilege:privilege,
					limit : pageSize
				},
				success : function(b){
					Ext.getCmp('dno').disable();
					Ext.Ajax.request({
						url:sysPath+"/fax/oprFaxInAction!ralaList.action",
						params:{
							privilege:68,
							filter_EQL_dno:Ext.getCmp('dno').getValue()
						},
						success : function(response) { // 回调函数有1个参数
							if(Ext.decode(response.responseText).result.length==0){
								Ext.getCmp('showMsg').getEl().update('<span style="color:red">配送单号为'+v.getValue()+'的记录不存在，请重新输入</span>');
								v.focus();
								v.markInvalid("配送单号不存在!");
							}else{
								Ext.getCmp('inDepart').setValue(_record.data.inDepart); //
								Ext.getCmp('consignee').setValue(Ext.decode(response.responseText).result[0].consignee);
								
							}
						},
						failure : function(response) {
							
						}
					});	
				},
				failure : function(form, action){
						Ext.Msg.alert(alertTitle,"加载失败，请重试");
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
			items : formn,
			buttonAlign : "center",	buttons : [{
				text : "保存",
				iconCls : 'groupSave',
				handler : function() {
					var flag=true;
					var cost =Ext.getCmp('costAmount').getValue();
					var income=Ext.getCmp('incomeAmount').getValue();
					if(Ext.getCmp('costAmount').getValue()==0&&Ext.getCmp('incomeAmount').getValue()==0){
						flag=false;
						Ext.Msg.alert(alertTitle,"收入金额和费用金额都为零，不允许新增", function() {
								Ext.getCmp('costAmount').focus();
								Ext.getCmp('costAmount').markInvalid("两个金额不能都为零!");			
						});
					}
					
					if (formn.getForm().isValid()&&flag){
						var str="";
						if(cost>income){
							str="费用金额比较收入金额要高请确认,";
						}else{
							if(cost==0){
								str+="没有费用金额,";
							}
							if(income==0){
								str+='没有收入金额,';
							}
						}
						
						Ext.Msg.confirm(alertTitle,str+'您确定要新增这条数据吗?',function(btnYes){
							if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true){
								this.disabled = true;//只能点击一次
								formn.getForm().submit({
									url : sysPath+ '/'+saveUrl,
									params:{
										privilege:privilege,
										limit : pageSize
									},
									waitMsg : '正在保存数据...',
									success : function(form, action) {
												win.hide(), 
												Ext.Msg.alert(alertTitle,action.result.msg, function() {
													dataStore.reload();
												});
									},
									failure : function(form, action) {
										this.disabled = false;//只能点击一次
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
						});
					}
				}
			}, {
				text : "重置",
				iconCls : 'refresh',
				handler : function() {		
						if(_record!=null){
							carTitle='修改增值服务费';
							formn.load({
								url : sysPath+ "/"+ralaListUrl,
								params:{
									filter_EQL_id: _record.data.id,
									privilege:privilege,
									limit : pageSize
								},
								success : function(b){
									Ext.getCmp('dno').disable();
									Ext.Ajax.request({
										url:sysPath+"/fax/oprFaxInAction!ralaList.action",
										params:{
											privilege:68,
											filter_EQL_dno:Ext.getCmp('dno').getValue()
										},
										success : function(response) { // 回调函数有1个参数
											if(Ext.decode(response.responseText).result.length==0){
												Ext.getCmp('showMsg').getEl().update('<span style="color:red">配送单号为'+v.getValue()+'的记录不存在，请重新输入</span>');
												v.focus();
												v.markInvalid("配送单号不存在!");
											}else{
												Ext.getCmp('inDepart').setValue(_record.data.inDepart); //
												Ext.getCmp('consignee').setValue(Ext.decode(response.responseText).result[0].consignee);
												
											}
										},
										failure : function(response) {
											
										}
									});	
								},
								failure : function(form, action){
										Ext.Msg.alert(alertTitle,"加载失败，请重试");
								}
							})
						}else{
							formn.getForm().reset();
						}
				 }}, {
						text : "取消",
						handler : function() {
						   win.close();
						   select();
						}
					}]
								
		
		});
	
		win.on('hide', function() {
					formn.destroy();
					select();
				});
		
		win.show();

  }		

	function deleteStore(_record){
		if(_record.data.status!='1'){
			Ext.Msg.alert(alertTitle,"数据已作废，不能重复操作",
									function() {
										
									});
			return;
		}
		Ext.Msg.confirm(alertTitle,'您确定要作废这行数据吗?',function(btnYes) {
				if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
					form1.getForm().doAction('submit', {
						url : sysPath+ "/fi/fiAdvanceAction!deleteStatus.action",
						method : 'post',
						params : {
							privilege:privilege,
							id:_record.data.id,
							fiAdvanceId:_record.data.fiAdvanceId
						},
						waitMsg : '正在作废数据...',
						success : function(form1, action) {
							Ext.Msg.alert(
									alertTitle,
									"数据作废成功",
									function() {
										dataStore.reload();
									});
						},
						failure : function(form1, action) {
							Ext.Msg.alert(
									alertTitle,
									"数据作废失败",
									function() {
										dataStore.reload();
										select();
									});
						}
					});
			 }
		});
	}
	
	function fiAduit(record){
		Ext.Msg.confirm(alertTitle,'您确定要审核 <span style="color:red">'+record.length+'</span> 这条件数据吗?',function(btnYes) {
			
			if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
				var ids = "";
				for(var i = 0; i < record.length; i++) {
					ids += record[i].data.id + ",";
				}  	
				form1.getForm().doAction('submit', {
							url:sysPath+"/fi/fiAppreciationServicesAction!saveFiAudit.action",
							method : 'post',
							params : {
								ids : ids,
   								privilege:privilege
		   					},
							waitMsg : '正在保存数据...',
							success : function(form1, action) {
									Ext.Msg.alert(alertTitle,action.result.msg,function(){
										dataStore.reload({
											params : {
												start : 0,
												limit : pageSize,
												privilege:privilege
											}
										});
									});
										
									
							},
							failure : function(form1, action) {
									Ext.Msg.alert(alertTitle,action.result.msg);
							}
				});
			}
		});	
	}
	
	function accountAduit(record){
		Ext.Msg.confirm(alertTitle,'您确定要审核 <span style="color:red">'+record.length+'</span> 这条件数据吗?',function(btnYes) {
			
			if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
				var ids = "";
				for(var i = 0; i < record.length; i++) {
					ids += record[i].data.id + ",";
				}  	
				form1.getForm().doAction('submit', {
							url:sysPath+"/fi/fiAppreciationServicesAction!saveService.action",
							method : 'post',
							params : {
								ids : ids,
   								privilege:privilege
		   					},
							waitMsg : '正在保存数据...',
							success : function(form1, action) {
									Ext.Msg.alert(alertTitle,action.result.msg,function(){
										dataStore.reload({
											params : {
												start : 0,
												limit : pageSize,
												privilege:privilege
											}
										});
									});
										
									
							},
							failure : function(form1, action) {
									Ext.Msg.alert(alertTitle,action.result.msg);
							}
				});
			}
		});
	}
		/**
	 * 导出按钮事件
	 */
	function exportinfo() {
		parent.exportExl(recordGrid);		
	}
	
	/**
	 * 打印按钮事件
	 */
	function printinfo() {
		Ext.Msg.alert("提示", "正在开发中...");
	}
});
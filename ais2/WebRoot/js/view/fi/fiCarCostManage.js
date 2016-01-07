	Ext.QuickTips.init();
	
	
	var privilege=133;
	var comboxPage=comboSize;
	var departGridSearchUrl = "sys/departAction!findAll.action";
	var saveUrl="fi/fiAdvanceAction!save.action";
	var customerGridSearchUrl="sys/customerAction!list.action";
	var areaListUrl="stock/oprStoreAreaAction!list.action";
	var ralaListUrl="fi/fiCarCostAction!list.action";
	var customerGridSearchUrl = "sys/customerAction!list.action";
	var dictionaryUrl='sys/dictionaryAction!ralaList.action';
	
Ext.onReady(function(){
	Ext.QuickTips.init();
	var rownum=new Ext.grid.RowNumberer({
		header:'No.',
        width : 25,
		sortable : true
		
	});
		
	var fields=[
	'id',
	'carSignNo',
	'routeNumber',
	'carNo',
	'useCarType',
	'maxloadWeight',
	'userCode',
	'driverName',
	'phone',
	'useCarDate',
	'startAddr',
	'endAddr',
	'startTime',
	'endTime',
	'signTimeNum',
	'startKil',
	'endKil',
	'kils',
	'createDepartName',
	'createDeptId',
	'printDepartId',
	'printDepartName',
	'printName',
	'stopFee',
	'highSpeedFee',
	'lowSpeedFee',
	'tollChargeTotal',
	'signFee',
	'feeTotal',
	'totalPoll',
	'carVerifyName',
	'fiVerifyName',
	'rentCarResult',
	'remark',
	'createName',
	'createTime',
	'updateName',
	'updateTime',
	'ts','payStatus',
	'departId',
	'departName',
	'status',
	'enteringName',
	'enteringTime',
	'printNum',
	'isSeparateDelivery',
	'carId',
	'rentReasonCarResult'
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
	
		//用车类型
	 var carTypeStore= new Ext.data.Store({ 
		autoLoad:true, 
		storeId:"",
		baseParams:{filter_EQL_basDictionaryId:30,privilege:16},
		proxy:new Ext.data.HttpProxy({url:sysPath+"/"+dictionaryUrl}),
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
    	{name:'id',mapping:'typeCode'},
    	{name:'carTypeName',mapping:'typeName'}
    	])
	});
	
	carTypeStore.load();
	
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
	
	var sourceDataStore=new Ext.data.SimpleStore({
		auteLoad:true, //此处设置为自动加载
  	  	data:[['1','手工录入'],['2','收付款单']],
   		fields:["id","name"]
	});
	
	var menuStore = new Ext.data.Store({ 
         storeId:"menuStore",                        
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
                	limit: pageSize
                },
                sortInfo : {field: "carSignNo", direction: "DESC"},
                reader:jsonread
    });
 	
 	var twobar = new Ext.Toolbar({
		    	items : ['&nbsp;','创建日期:',
						 {
							xtype : 'datefield',
			    			id : 'startDate',
				    		format : 'Y-m-d',
				    		emptyText : "选择开始时间",
				    		anchor : '95%',
				    		width : 100,
				    		listeners : {
				    			'select' : function() {
				    			   var start = Ext.getCmp('startDate').getValue()
				    			      .format("Y-m-d");
				    			   Ext.getCmp('endDate').setMinValue(start);
				    		     }
			    		    }
						},'&nbsp;','至','&nbsp;',{
							xtype : 'datefield',
				    		id : 'endDate',
				    		format : 'Y-m-d',
				    		emptyText : "选择结束时间",
				    		width : 100,
				    		anchor : '95%'
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
			    },'-','&nbsp;&nbsp;',
			{	xtype:'textfield',
	 	        id : 'itemsValue',
	 	        width : 100,
		        name : 'itemsValue',
		        disabled:true,
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
				typeAhead : false,
				forceSelection : true,
				width : 100,
				id:'status',
				hidden:true,
				diabled:true,
				mode:'local',
				triggerAction : 'all',
				store : statusDataStore,
				displayField : 'name',
				valueField : 'id',
				emptyText : "请选择状态"
					
			},{
				xtype : 'combo',
				typeAhead : false,
				forceSelection : true,
				width : 100,
				id:'carType',
				hidden:true,
				diabled:true,
				mode:'local',
				triggerAction : 'all',
				store : carTypeStore,
				displayField : 'carTypeName',
				valueField : 'carTypeName',
				emptyText : "请选择车辆用途"
					
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
    					['LIKES_carNo', '车牌号'],
    					['EQL_id', '编号'],
    					['EQS_useCarType', '用车类型'],
    					['LIKES_driverName', '司机'],
    					['LIKES_carSignNo', '签单编号'],
    					['EQL_status', '状态']],
    			emptyText : '选择查询类型',
    			forceSelection : true,
    			listeners : {
    				'select' : function(combo, record, index) { 
    					if (combo.getValue() == '') {
    						Ext.getCmp("itemsValue").disable();
    						Ext.getCmp("itemsValue").setValue("");
    						Ext.getCmp("itemsValue").show();
    						
    						
    						Ext.getCmp("status").disable();
    						Ext.getCmp("status").hide();
    						Ext.getCmp("status").setValue("");
    						
    						Ext.getCmp("carType").disable();
    						Ext.getCmp("carType").hide();
    						Ext.getCmp("carType").setValue("");
    					}else if(combo.getValue() == 'EQS_useCarType'){
    						Ext.getCmp("itemsValue").disable();
    						Ext.getCmp("itemsValue").setValue("");
    						Ext.getCmp("itemsValue").hide();
    						
    						
    						Ext.getCmp("status").disable();
    						Ext.getCmp("status").hide();
    						Ext.getCmp("status").setValue("");
    						
    						Ext.getCmp("carType").enable();
    						Ext.getCmp("carType").show();
    						Ext.getCmp("carType").setValue();
    						
    					}else if (combo.getValue() == 'EQL_status') {
    						Ext.getCmp("itemsValue").disable();
    						Ext.getCmp("itemsValue").setValue("");
    						Ext.getCmp("itemsValue").hide();
    						
    						
    						Ext.getCmp("carType").disable();
    						Ext.getCmp("carType").hide();
    						Ext.getCmp("carType").setValue("");
    						
    						Ext.getCmp("status").enable();
    						Ext.getCmp("status").show();
    						Ext.getCmp("status").setValue();
    					}else{  //
    						Ext.getCmp("status").disable();
    						Ext.getCmp("status").hide();
    						Ext.getCmp("status").setValue("");
    						
    				    	Ext.getCmp("itemsValue").enable();
    						Ext.getCmp("itemsValue").show();
    						
    						Ext.getCmp("carType").disable();
    						Ext.getCmp("carType").hide();
    						Ext.getCmp("carType").setValue("");
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
				text : '<b>成本录入</b>',
				iconCls : 'groupAdd',
				id : 'costin',
				handler : function(){
					var vnetmusicRecord = recordGrid.getSelectionModel().getSelections();

					if (vnetmusicRecord.length == 1) {
						if(vnetmusicRecord[0].data.status=='1'||vnetmusicRecord[0].data.status=='2'){
							 if(vnetmusicRecord[0].data.departId!=bussDepart){
								Ext.Msg.alert(alertTitle,"不是本业务部门（"+bussDepartName+")的数据,请勿操作,谢谢");
								return;
							}
						
							outCostIn(vnetmusicRecord[0]);
						}else{
							Ext.Msg.alert(alertTitle,"只有状态为新增的数据才能录入车辆成本");
						}
					}else{
						Ext.Msg.alert(alertTitle,"只能选择一条记录进行成本录入",function(){});
					}
				}
			},'-','&nbsp;',
			{
				text : '<b>车队审核</b>',
				iconCls : 'groupEdit',
				id : 'carAduit',
				handler : function(){
					var vnetmusicRecord = recordGrid.getSelectionModel().getSelections();
					if(vnetmusicRecord.length>0){
						for(var i=0;i<vnetmusicRecord.length;i++){
							if(vnetmusicRecord[i].data.status!=2){
								Ext.Msg.alert(alertTitle,"存在其他状态的记录，无法进行车队审核");
								return;
							}
							
							if(vnetmusicRecord[i].data.departId!=bussDepart){
								Ext.Msg.alert(alertTitle,"不是本业务部门（"+bussDepartName+")的数据,请勿操作,谢谢");
								return;
							}
						}
						carAudit(vnetmusicRecord);
					}else{
						Ext.Msg.alert(alertTitle,"请选择一条或多条记录进行车队审核");
					}
				}
			},'-','&nbsp;',
			{
				text : '<b>会计审核</b>',
				iconCls : 'groupPass',
				id : 'fiAduit',
				handler : function(){
					var vnetmusicRecord = recordGrid.getSelectionModel().getSelections();
					if (vnetmusicRecord.length>0){
						for(var i=0;i<vnetmusicRecord.length;i++){
							if(vnetmusicRecord[i].data.status!=3){
								Ext.Msg.alert(alertTitle,"存在其他状态的记录，无法进行会计审核");
								return;
							}
							
							if(vnetmusicRecord[i].data.departId!=bussDepart){
								Ext.Msg.alert(alertTitle,"不是本业务部门（"+bussDepartName+")的数据,请勿操作,谢谢");
								return;
							}
						}
						fiAduit(vnetmusicRecord);
					}else{
						Ext.Msg.alert(alertTitle,"请选择一条或多条记录进行会计审核",function(){
						});
					}
				}
			},'-','&nbsp;',
			{
				text : '<b>撤销审核</b>',
				iconCls : 'groupNotPass',
				id : 'qxFiAduit',
				handler : function(){
					var vnetmusicRecord = recordGrid.getSelectionModel().getSelections();

					if (vnetmusicRecord.length == 1) {
						qxFiAduit(vnetmusicRecord[0]);
					}else{
						Ext.Msg.alert(alertTitle,"只能选择一条记录进行撤销审核",function(){
						});
					}
				}
			},'-','&nbsp;',{
				text : '<b>导出</b>',
				iconCls : 'sort_down',
				id : 'exportbtn',
				tooltip : '导出',
				handler : exportinfo
			},/*'-','&nbsp;',{
				text : '<b>打印</b>',
				iconCls : 'table',
				id : 'printbtn',
				tooltip : '打印',
				handler : printinfo
			},*/'-','&nbsp;',{
    				     text : '<b>查询</b>',
    				     id : 'btn',
    				     iconCls : 'btnSearch',
    					 handler : searchLog
    				}],
			columns:[ rownum,sm,
        			{header: '编号', dataIndex: 'id',width:55,sortable : true},
			        {header: '车辆签单号', dataIndex: 'carSignNo',width:90,sortable : true},
			  		{header: '车次号', dataIndex: 'routeNumber',width:80,sortable : true},
			        {header: '车队审核', dataIndex: 'carVerifyName',width:80,sortable : true},
			        {header: '财务审核', dataIndex: 'fiVerifyName',width:80,sortable : true},
			        {header: '状态', dataIndex: 'status',width:80,sortable : true,
			        	renderer:function(v){
			        		if(v=='1'){ 
			        			return '新增';
			        		}else if(v=='2'){
			        			return '待审核';
			        		}else if(v=='3'){
			        			return '车队已审核';
			        		}else if(v=='4'){
			        			return '财务已审核';
			        		}else if(v=='5'){
			        			return '已收款';
			        		}else{
			        			return '已作废';
			        		}
			        	}
			        },
			        {header: '车牌号', dataIndex: 'carNo',width:60,sortable : true},
			        {header: '用车类型', dataIndex: 'useCarType',width:60,sortable : true},
			        {header: '车辆吨位', dataIndex: 'maxloadWeight',width:60,sortable : true},
			        {header: '司机工号', dataIndex: 'userCode',width:60,sortable : true},　
			        {header: '司机名字', dataIndex: 'driverName',width:60,sortable : true},
					{header: '联系电话', dataIndex: 'phone',width:60,sortable : true},
					{header: '用车日期', dataIndex: 'useCarDate',width:70,sortable : true},
					{header: '起始地点', dataIndex: 'startAddr',width:80,sortable : true},
					{header: '结束地点', dataIndex: 'endAddr',width:90,sortable : true},
					{header: '开始时间', dataIndex: 'startTime',width:60,hidden:true,sortable : true},
			        {header: '结束时间', dataIndex: 'endTime',width:80,hidden:true,sortable : true},  
			        {header: '签单时数', dataIndex: 'signTimeNum',width:80,hidden:true,sortable : true},
			        {header: '开始公里数', dataIndex: 'startKil',width:80,hidden:true,sortable : true},
			        {header: '结束公里数', dataIndex: 'endKil',width:80,hidden:true,sortable : true},
			        {header: '公里数', dataIndex: 'kils',width:80,hidden:true,sortable : true},
			        {header: '打印部门ID', dataIndex: 'printDepartId',width:80,hidden:true,sortable : true},
			       	{header: '打印部门', dataIndex: 'printDepartName',width:80,hidden:true,sortable : true},
			       	{header: '打印人', dataIndex: 'printName',width:80,sortable : true},
			       	{header: '打印次数', dataIndex: 'printNum',width:80,sortable : true},
			        {header: '车辆用途', dataIndex: 'rentCarResult',width:200,sortable : true},
			        {header: '租车原因', dataIndex: 'rentReasonCarResult',width:200,sortable : true},
	       			
	       			{header: '停车费', dataIndex: 'stopFee',width:80,sortable : true},
			       	{header: '高速费', dataIndex: 'highSpeedFee',width:80,sortable : true},
			       	{header: '低速费', dataIndex: 'lowSpeedFee',width:80,sortable : true},
			       	{header: '路桥费合计', dataIndex: 'tollChargeTotal',width:80,sortable : true},
			        {header: '签单费', dataIndex: 'signFee',width:80,sortable : true},
			        
			        {header: '费用总计', dataIndex: 'feeTotal',width:80,sortable : true},
			        {header: '折合票数 ', dataIndex: 'totalPoll',width:80,sortable : true},
			        {header: '支付状态', dataIndex: 'payStatus',width:120,sortable : true,
			        	renderer:function(v){
			        		if(v=='1'){ 
			        			return '已支付';
			        		}else{
			        			return '未支付';
			        		}
			        	}},
			        {header: '备注', dataIndex: 'remark',width:120,sortable : true},
			        {header: '修改人', dataIndex: 'updateName',width:80,hidden:true,sortable : true},
			        {header: '修改时间', dataIndex: 'updateTime',width:80,hidden:true,sortable : true},
			        {header: '创建人', dataIndex: 'createName',width:80,hidden:true,sortable : true},
			        {header: '创建时间', dataIndex: 'createTime',width:80,hidden:true,sortable : true},
			        {header: '创建部门ID', dataIndex: 'departId',width:80,hidden:true,sortable : true},
			        {header: '创建部门', dataIndex: 'departName',width:80,sortable : true},
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
	    	var start='';
	    	var end ='';
	    	var depart= Ext.getCmp('comboRightDepart2').getValue();
	    	if(Ext.getCmp('startDate').getValue()!=""){
	    		var start = Ext.getCmp('startDate').getValue().format("Y-m-d");
	    	}
	    	if(Ext.getCmp('endDate').getValue()!=""){
	    		var end = Ext.getCmp('endDate').getValue().format("Y-m-d");
	    	}
	    	if(Ext.getCmp('comboselect').getValue()==''){
			 	Ext.apply(dataStore.baseParams={
					filter_GED_createTime : start,
	            	filter_LTD_createTime : end,
	            	filter_EQL_departId:depart
	   			});
	    	}else if(Ext.getCmp('comboselect').getValue()=='EQS_useCarType'){
	    		Ext.apply(dataStore.baseParams={
					filter_GED_createTime : start,
	            	filter_LTD_createTime : end,
	            	filter_EQL_departId:depart,
	            	filter_EQS_useCarType:Ext.getCmp('carType').getValue()
	   			});
	    	}else if(Ext.getCmp('comboselect').getValue()=='EQL_status'){
	    		Ext.apply(dataStore.baseParams={
					filter_GED_createTime : start,
	            	filter_LTD_createTime : end,
	            	filter_EQL_departId:depart,
	            	filter_EQL_status:Ext.getCmp('status').getValue()
	   			});
	    	}else{
	    		Ext.apply(dataStore.baseParams={
					filter_GED_createTime : start,
	            	filter_LTD_createTime : end,
	            	filter_EQL_departId:depart,
					checkItems :Ext.getCmp('comboselect').getValue(),
					itemsValue :Ext.getCmp('itemsValue').getValue()
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

  function outCostIn(record) {
    	var fields1=[
			{name:'dno'},
			{name:'flightMainNo'},//主单号
			{name:'realPiece'},         //件数
			{name:'weight'},        //重量
			{name:'routeNumber'},   // 车次号自动增长
			{name:'carSignNo'},       //签单号
			{name:'overmemoId'},
			{name:'carNo'},
			{name:'useCarDate'},
			{name:'useCarType'},
			{name:'rentCarResult'}];
    	
    	var jsonread1= new Ext.data.JsonReader({
              root:'result',
              totalProperty:'totalCount'},
              fields1);
    	
    	var totalStore = new Ext.data.Store({
    			id:'totalStore',
                proxy: new Ext.data.HttpProxy({url:sysPath+"/fi/fiCarCostAction!ralaList.action"}),
                baseParams:{
                	limit: pageSize,
                	privilege:privilege
                },
                reader:jsonread1
        });
        
        var totalCopyStore = new Ext.data.Store({
    			 fields: fields1
        });
        
    	
    	
    	var em =new Ext.grid.CheckboxSelectionModel();
    	
    	var addArriveRecord = new Ext.grid.GridPanel({
    				region : "center",
    				id : 'myrecordGrid1',
    				height : 200,
    				width : 685,
    				autoScroll : true,
    				//autoSizeColumns: true,
    				frame : true,
    				loadMask : true,
    				stripeRows : true,
    				viewConfig:{
    				  // forceFit: true,
    				   scrollOffset: 0
    				},
    				cm : new Ext.grid.ColumnModel([
    						em, 
    						{header : '配送单号',dataIndex:'dno',width:80}, 
    						{header : '主单号',dataIndex : 'flightMainNo',width:80}, 
    						{header : '件数',dataIndex : 'realPiece',width:80},
    						{header : '重量',dataIndex : 'weight',width:80},
    						{header : '车次号',dataIndex : 'routeNumber',width:80},
    						{header : '交接单号',dataIndex : 'overmemoId',width:80},
    						{header : '车牌号码',dataIndex : 'carNo',width:80}, 
    						{header : '航班号',dataIndex : 'flightNo',width:80},
    						{header : '用车类型',dataIndex : 'useCarType',width:60}, 
    						{header : '车辆用途',dataIndex : 'rentCarResult',width:80},
    						{header : '用车日期',dataIndex:'useCarDate',width:80}
    						
    				]),
    				sm : em,
    				ds : totalStore
    			});
    	   
    		var form = new Ext.form.FormPanel({
    				id:'addForm',
					frame : true,
					layout : 'form',
					border : false,
					bodyStyle : 'padding:5px 5px 5px',
					labelAlign : "right",
					labelWidth : 80,
					reader :jsonread,
					items:[{
						 layout : 'column',
						 items:[{
							 layout:'form',
							 columnWidth:.25,
							 labelWidth:70,
							 items:[
								{xtype:'textfield',
								 fieldLabel:'签单号',
				 				 maxLength:20,
				 				 name:'carSignNo',
				 				 id:'carSignNo',
				 				 readOnly:true,
				 				 anchor : '95%',
				 				 allowBlank:false,
				 				 blankText:'签单号不能为空!',
				 				 enableKeyEvents:true,
				 				 listeners : {
							 		keyup:function(textField, e){
						                  if(e.getKey() == 13){
						                   	Ext.getCmp('carNo').focus(true,true);
						                  }
							 		}
					 			 }
							 	 },{
							 	 xtype:'textfield',
								 fieldLabel:'司机工号',
				 				 maxLength:20,
				 				 name:'userCode',
				 				 id:'userCode',
				 				 anchor : '95%',
				 				 allowBlank:true,
				 				 blankText:'司机工号不能为空!',
				 				 enableKeyEvents:true,
				 				 listeners : {
							 		keyup:function(textField, e){
						                  if(e.getKey() == 13){
						                   	Ext.getCmp('driverName').focus(true,true);
						                  }
							 		}
					 			 }
							},{
							 	 xtype:'textfield',
								 fieldLabel:'起始地址',
				 				 maxLength:30,
				 				 name:'startAddr',
				 				 anchor : '95%',
				 				 id:'startAddr',
				 				 allowBlank:false,
				 				 blankText:'起始地址不能为空!',
				 				 enableKeyEvents:true,
				 				 listeners : {
							 		keyup:function(textField, e){
						                  if(e.getKey() == 13){
						                   	Ext.getCmp('endAddr').focus(true,true);
						                  }
							 		}
					 			 }
							 },{
								xtype : 'numberfield',
								fieldLabel : '起始公里<span style="color:red">*</span>',
								name : 'startKil',
								id : 'startKil',
								maxLength : 11,
								allowBlank : false,
								anchor:'95%',
								enableKeyEvents:true,
				 				listeners : {
							 		keyup:function(textField, e){
						                  if(e.getKey() == 13){
						                   	Ext.getCmp('endKil').focus(true,true);
						                  }
							 		},
							 		blur:function(v){
							 			if(v.getValue()!=''||Ext.getCmp('endKil').getValue()!=''){
							 				Ext.getCmp('kils').setValue((parseFloat(v.getValue())-parseFloat(Ext.getCmp('startKil').getValue())).toFixed(2));
							 			}
							 		}
					 			 }
							 },{
								xtype : 'numberfield',
								fieldLabel : '低速费<span style="color:red">*</span>',
								name : 'lowSpeedFee',
								id : 'lowSpeedFee',
								maxLength : 11,
								allowBlank : false,
								anchor:'95%',
								enableKeyEvents:true,
								listeners : {
					    			'blur' : function(){
					    				var lowSpeedFee=Ext.getCmp('lowSpeedFee').getValue();
					    				var highSpeedFee= Ext.getCmp('highSpeedFee').getValue();
					    				var stopFee = Ext.getCmp('stopFee').getValue()
					    				if(stopFee==''){
					    					stopFee=0;
					    				}
					    				if(highSpeedFee==''){
					    					highSpeedFee=0;
					    				}
					    				if(lowSpeedFee==''){
					    					lowSpeedFee=0;
					    				}
					    			    
					    			    var total = (parseFloat(lowSpeedFee)+parseFloat(highSpeedFee)+parseFloat(stopFee)).toFixed(2);
					    			    Ext.getCmp('tollChargeTotal').setValue(total);
					    			    var signFee = Ext.getCmp('signFee').getValue();
					    			    if(signFee==''){
					    			    	signFee=0;
					    			    }
					    			    Ext.getCmp('totalPollfeeTotal').setValue((parseFloat(signFee)+parseFloat(total)).toFixed(2));
					    			 },
								 		keyup:function(textField, e){
							                  if(e.getKey() == 13){
							                   	Ext.getCmp('signFee').focus(true,true);
							                  }
								 		}
				    		    }
							 },{
								xtype : 'numberfield',
								fieldLabel : '送货票数',
								name : 'totalPoll2',
								id : 'totalPoll2',
								maxLength : 11,
								allowBlank : false,
								anchor:'95%'
							 },{
							 	 xtype:'numberfield',
								 fieldLabel:'签单时数',
				 				 maxLength:30,
				 				 name:'signTimeNum',
				 				 anchor : '95%',
				 				 id:'signTimeNum',
				 				 allowBlank:true,
				 				 enableKeyEvents:true,
				 				 listeners : {
							 		keyup:function(textField, e){
						                  if(e.getKey() == 13){
						                   	 Ext.getCmp('totalPoll').focus(true,true);
						                  }
							 		}
					 			 }
							 }
								]
							 },{
    						 layout:'form',
    						 columnWidth:.25,
    						 labelWidth:70,
    						 items:[
    							{xtype:'textfield',
								 fieldLabel:'车牌号码',
				 				 maxLength:20,
				 				 anchor : '95%',
				 				 name:'carNo',
				 				 id:'carNo',
				 				 allowBlank:false,
				 				 blankText:'车牌号码不能为空!',
				 				 enableKeyEvents:true,
				 				 listeners : {
							 		keyup:function(textField, e){
						                  if(e.getKey() == 13){
						                   	Ext.getCmp('maxloadWeight').focus(true,true);
						                  }
							 		}
					 			 }
							 },{
							 	 xtype:'textfield',
								 fieldLabel:'姓名',
				 				 maxLength:30,
				 				 name:'driverName',
				 				 anchor : '95%',
				 				 id:'driverName',
				 				 allowBlank:false,
				 				 blankText:'姓名不能为空!',
				 				 enableKeyEvents:true,
				 				 listeners : {
							 		keyup:function(textField, e){
						                  if(e.getKey() == 13){
						                   	Ext.getCmp('phone').focus(true,true);
						                  }
							 		}
					 			 }
							 },{
							 	 xtype:'textfield',
								 fieldLabel:'结束地址',
				 				 maxLength:30,
				 				 name:'endAddr',
				 				 anchor : '95%',
				 				 id:'endAddr',
				 				 allowBlank:false,
				 				 blankText:'结束地址不能为空!',
				 				 enableKeyEvents:true,
				 				 listeners : {
							 		keyup:function(textField, e){
						                  if(e.getKey() == 13){
						                   	Ext.getCmp('startTime').focus();
						                  }
							 		}
					 			 }
							},{
								xtype : 'numberfield',
								fieldLabel : '结束公里<span style="color:red">*</span>',
								name : 'endKil',
								id : 'endKil',
								maxLength : 11,
								allowBlank : false,
								anchor:'95%',
								enableKeyEvents:true,
				 				listeners : {
							 		keyup:function(textField, e){
						                  if(e.getKey() == 13){
						                   	Ext.getCmp('stopFee').focus(true,true);
						                  }
							 		},
							 		blur:function(v){
							 			if(v.getValue()!=''||Ext.getCmp('startKil').getValue()!=''){
							 				Ext.getCmp('kils').setValue((parseFloat(v.getValue())-parseFloat(Ext.getCmp('startKil').getValue())).toFixed(2));
							 			}
							 		}
					 			 }
							 },{
								xtype : 'numberfield',
								fieldLabel : '路桥费合计',
								name : 'tollChargeTotal',
								id : 'tollChargeTotal',
								maxLength : 11,
								readOnly:true,
								allowBlank : false,
								anchor:'95%'
							 },{
								xtype : 'numberfield',
								fieldLabel : '折合票数',
								name : 'totalPoll',
								id : 'totalPoll',
								maxLength : 11,
								allowBlank : false,
								anchor:'95%',
								enableKeyEvents:true,
				 				listeners : {
							 		keyup:function(textField, e){
						                  if(e.getKey() == 13){
						                   	Ext.getCmp('rentReasonCarResult').focus(true,true);
						                  }
							 		}
							 	}
							 },{
								xtype : 'numberfield',
								fieldLabel : '费用合计',
								name : 'feeTotal',
								id : 'totalPollfeeTotal',
								maxLength : 11,
								readOnly:true,
								allowBlank : false,
								anchor:'95%'								
							 }
    							 ]
    						},{
    						 layout:'form',
    						 columnWidth:.25,
    						 labelWidth:70,
    						 items:[
    						   {xtype : 'combo',
								id:'useCarType',
								hiddenId : 'toName3',
								fieldLabel:'用车类型',
				    			hiddenName : 'carTypeName',
								triggerAction : 'all',
								store : carTypeStore,
								mode:'local',
								readOnly:true,
								allowBlank : true,
								emptyText : "请选择用车类型",
								forceSelection : true,
								editable : false,
								displayField : 'carTypeName',//显示值，与fields对应
								valueField : 'carTypeName',//value值，与fields对应
								name : 'useCarType',
								anchor : '95%'
						    },{
						    	xtype:'textfield',
								fieldLabel:'联系电话',
				 				maxLength:20,
				 				name:'phone',
				 				id:'phone',
				 				allowBlank:false,
				 				blankText:'电话不能为空!',
				 				enableKeyEvents:true,
				 				anchor : '95%',
				 				listeners : {
							 		keyup:function(textField, e){
						                  if(e.getKey() == 13){
						                   	Ext.getCmp('useCarDate').focus(true,true);
						                  }
							 		}
					 			}
							 },{
								xtype : 'datetimefield',
				    			id : 'startTime',
				    			name:'startTime',
				    			allowBlank:false,
				    		//	width:80,
				    			format:'Y-m-d H:i:s',
				    			invalidText:'时间格式必须为：××××-××-×× ××:××',
					    		fieldLabel:'起始时间<span style="color:red">*</span>',
					    		emptyText : "请选择起始时间",
					    		anchor : '95%',
				    		    enableKeyEvents:true,
					    		listeners : {
					    			'select' : function() {
					    			   var start = Ext.getCmp('startTime').getValue().format("Y-m-d H:m");
					    			   Ext.getCmp('endTime').setMinValue(start);
					    			   if(Ext.getCmp('startTime').getValue()!=''&&Ext.getCmp('endTime').getValue()!=''){
					    			   		var end = Ext.getCmp('endTime').getValue();
					    			   		var len = new Date(Ext.getCmp('endTime').getValue()).getTime()-new Date(Ext.getCmp('startTime').getValue()).getTime();
											Ext.getCmp('signTimeNum').setValue(Math.ceil(len/(1000*60*60)));
					    			   }
				    		     	},
							 		keyup:function(textField, e){
						                  if(e.getKey() == 13){
						                   	Ext.getCmp('endTime').focus();
						                  }
							 		}
				    		    }
							},{
								xtype : 'numberfield',
								fieldLabel : '停车费<span style="color:red">*</span>',
								name : 'stopFee',
								id : 'stopFee',
								maxLength : 11,
								allowBlank : false,
								anchor:'95%',
								enableKeyEvents:true,
								listeners : {
					    			'blur' : function() {
					    				var lowSpeedFee=Ext.getCmp('lowSpeedFee').getValue();
					    				var highSpeedFee= Ext.getCmp('highSpeedFee').getValue();
					    				var stopFee = Ext.getCmp('stopFee').getValue()
					    				if(stopFee==''){
					    					stopFee=0;
					    				}
					    				if(highSpeedFee==''){
					    					highSpeedFee=0;
					    				}
					    				if(lowSpeedFee==''){
					    					lowSpeedFee=0;
					    				}
					    			   	var total = (parseFloat(lowSpeedFee)+parseFloat(highSpeedFee)+parseFloat(stopFee)).toFixed(2);
					    			    Ext.getCmp('tollChargeTotal').setValue(total);
					    			    var signFee = Ext.getCmp('signFee').getValue();
					    			    if(signFee==''){
					    			    	signFee=0;
					    			    }
					    			    Ext.getCmp('totalPollfeeTotal').setValue((parseFloat(signFee)+parseFloat(total)).toFixed(2));
				    		     	},keyup:function(textField, e){
						                  if(e.getKey() == 13){
						                   	Ext.getCmp('highSpeedFee').focus();
						                  }
							 		}
				    		    }
							 },{
								xtype : 'numberfield',
								fieldLabel : '签单费<span style="color:red">*</span>',
								name : 'signFee',
								id : 'signFee',
								maxLength : 11,
								allowBlank : false,
								anchor:'95%',
								listeners : {
					    			'blur' : function() {
					    				var lowSpeedFee=Ext.getCmp('lowSpeedFee').getValue();
					    				var highSpeedFee= Ext.getCmp('highSpeedFee').getValue();
					    				var stopFee = Ext.getCmp('stopFee').getValue()
					    				if(stopFee==''){
					    					stopFee=0;
					    				}
					    				if(highSpeedFee==''){
					    					highSpeedFee=0;
					    				}
					    				if(lowSpeedFee==''){
					    					lowSpeedFee=0;
					    				}
					    			   	var total = parseFloat(lowSpeedFee)+parseFloat(highSpeedFee)+parseFloat(stopFee);
					    			    var signFee = Ext.getCmp('signFee').getValue();
					    			    if(signFee==''){
					    			    	signFee=0;
					    			    }
					    			    Ext.getCmp('totalPollfeeTotal').setValue((parseFloat(signFee)+parseFloat(total)).toFixed(2));
				    		     	}
				    		    }
							 }, {
								xtype : 'combo',
								fieldLabel : '所属部门',
								typeAhead : false,
								forceSelection : true,
								anchor:'95%',
								id:'bussDepart',
								listWidth:245,
								triggerAction : 'all',
								store : departStore,
								pageSize : comboxPage,
								displayField : 'departName',
								valueField : 'departId',
								name : 'departName',
								hiddenName:'departId',
								listeners : {
									 render:function(co){
									 	co.setValue(bussDepart);
									 }
								}
							},{
							 	 xtype:'textfield',
								 fieldLabel:'租车原因',
				 				 maxLength:500,
				 				 name:'rentReasonCarResult',
				 				 anchor : '95%',
				 				 id:'rentReasonCarResult',
				 				 allowBlank:true,
				 				 enableKeyEvents:true,
				 				 listeners : {
							 		keyup:function(textField, e){
						                  if(e.getKey() == 13){
						                   	  Ext.getCmp('remark').focus(true,true);
						                  }
							 		}
					 			 }
							 }]
    						},{
    						 layout:'form',
    						 columnWidth:.25,
    						 labelWidth:70,
    						 items:[
  							{
								xtype : 'numberfield',
								fieldLabel : '车辆吨位',
								name : 'maxloadWeight',
								id : 'maxloadWeight',
								maxLength : 50,
								allowBlank : false,
								anchor:'95%',
								enableKeyEvents:true,
								listeners : {
					 				render:function(v){
					 					if(record.data.maxloadWeight==''||record.data.maxloadWeight==null){
						 					Ext.Ajax.request({
												url:sysPath+"/bascar/basCarAction!ralaList.action",
												params:{
													privilege:20,
													filter_EQL_id:record.data.carId
												},
												success : function(response) { // 回调函数有1个参数
													v.setValue(Ext.decode(response.responseText).result[0].maxloadWeight);
												},
												failure : function(response) {
													
												}
											})
										}else{
											v.disable();
										}
					 				
					 				},
					 				keyup:function(textField, e){
						                  if(e.getKey() == 13){
						                   	Ext.getCmp('userCode').focus(true,true);
						                  }
							 		}
				    		    }
							},{
								xtype : 'datefield',
				    			id : 'useCarDate',
				    			fieldLabel:'用车日期<span style="color:red">*</span>',
				    			name:'useCarDate',
					    		format : 'Y-m-d',
					    		allowBlank:false,
					    		emptyText : "请选择用车日期",
					    		anchor : '95%',
					    		enableKeyEvents:true,
					    		listeners : {
					    			render:function(v){
					    				v.setMaxValue(new Date());
					    			},
					 				keyup:function(textField, e){
						                  if(e.getKey() == 13){
						                   	Ext.getCmp('startAddr').focus();
						                  }
							 		}
				    		    }
							},{
								xtype : 'datetimefield',
					    		id : 'endTime',
					    		name : 'endTime',
					    		format:'Y-m-d H:i:s',
					    		fieldLabel:'结束时间<span style="color:red">*</span>',
					    		allowBlank:false,
					    		emptyText : "请选择结束时间",
					    		anchor : '95%',
					    		enableKeyEvents:true,
					    		listeners : {
					    			'select' : function() {
					    			   if(Ext.getCmp('startTime').getValue()!=''&&Ext.getCmp('endTime').getValue()!=''){
					    			   		var end = Ext.getCmp('endTime').getValue();
					    			   		var len = new Date(Ext.getCmp('endTime').getValue()).getTime()-new Date(Ext.getCmp('startTime').getValue()).getTime();
											Ext.getCmp('signTimeNum').setValue(Math.ceil(len/(1000*60*60)));
					    			   }
				    		     	},
					 				keyup:function(textField, e){
						                  if(e.getKey() == 13){
						                   	Ext.getCmp('startKil').focus();
						                  }
							 		}
				    		    }
							},{
								xtype : 'numberfield',
								fieldLabel : '高速费<span style="color:red">*</span>',
								name : 'highSpeedFee',
								id : 'highSpeedFee',
								maxLength : 11,
								allowBlank : false,
								anchor:'95%',
								enableKeyEvents:true,
								listeners : { 
					    			'blur' : function() {
					    				var lowSpeedFee=Ext.getCmp('lowSpeedFee').getValue();
					    				var highSpeedFee= Ext.getCmp('highSpeedFee').getValue();
					    				var stopFee = Ext.getCmp('stopFee').getValue()
					    				if(stopFee==''){
					    					stopFee=0;
					    				}
					    				if(highSpeedFee==''){
					    					highSpeedFee=0;
					    				}
					    				if(lowSpeedFee==''){
					    					lowSpeedFee=0;
					    				}
					    				var total = (parseFloat(lowSpeedFee)+parseFloat(highSpeedFee)+parseFloat(stopFee)).toFixed(2);
					    			    Ext.getCmp('tollChargeTotal').setValue(total);
					    			    var signFee = Ext.getCmp('signFee').getValue();
					    			    if(signFee==''){
					    			    	signFee=0;
					    			    }
					    			    Ext.getCmp('totalPollfeeTotal').setValue((parseFloat(signFee)+parseFloat(total)).toFixed(2));
				    		     	},
					 				keyup:function(textField, e){
						                  if(e.getKey() == 13){
						                   	Ext.getCmp('lowSpeedFee').focus();
						                  }
							 		}
				    		    }
							 } ,{
							 	xtype : 'combo',
								id:'rentCarResult',
								fieldLabel : '车辆用途',
								triggerAction : 'all',
								store : carDoStore,
								mode:'local',
								readOnly:true,
								allowBlank : true,
								emptyText : "请选择车辆用途",
								forceSelection : true,
								editable : false,
								displayField : 'carDoName',//显示值，与fields对应
								valueField : 'carDoName',//value值，与fields对应
								name : 'rentCarResult',
								anchor:'95%'
						    },{
							 	 xtype:'numberfield',
								 fieldLabel:'公里数',
				 				 maxLength:30,
				 				 name:'kils',
				 				 anchor : '95%',
				 				 id:'kils',
				 				 allowBlank:true,
				 				 enableKeyEvents:true,
				 				 listeners : {
							 		keyup:function(textField, e){
						                  if(e.getKey() == 13){
						                   	//Ext.getCmp('pieceid').focus(true,true);
						                  }
							 		}
					 			 }
							},{
							 	 xtype:'textfield',
								 fieldLabel:'备注',
				 				 maxLength:500,
				 				 name:'remark',
				 				 anchor : '95%',
				 				 id:'remark',
				 				 allowBlank:true,
				 				 enableKeyEvents:true,
				 				 listeners : {
							 		keyup:function(textField, e){
						                  if(e.getKey() == 13){
						                   	  Ext.getCmp('save').focus(true,true);
						                  }
							 		}
					 			 }
							 },{
								name : "ts",
								xtype : "hidden"
							},{
								name : "id",
								xtype : "hidden"
							}]
    						}]
						},{
									layout:'form',
									items:[
										addArriveRecord
									]
							}
					]
    		});
    		
    		if(record!=null){
				form.load({
					url : sysPath+ "/fi/fiCarCostAction!list.action",
					params:{
						filter_EQS_carSignNo:record.data.carSignNo,
						limit : pageSize
					}
				})
			}

			Ext.apply(totalStore.baseParams={
	            	filter_EQS_carSignNo:record.data.carSignNo,
					limit : pageSize
	   			});
	   			
			totalStore.reload({
					params : {
						start : 0,
						privilege:privilege,
						limit : pageSize
					},callback :function(reponse){
					  Ext.getCmp('totalPoll2').setValue(reponse.length);
				}
			});
    		
    		var title;
    		if(record.data.status=='1'){
    			title='成本录入';
    		}else if(record.data.status=='2'){
    			title='车队审核';
    		}else if(record.data.status=='3'){
    			title='会计审核';
    		}
    		
    		var win = new Ext.Window({
			title : title,
			width : 720,
			height:480,
			closeAction : 'hide',
			plain : true,
			maximizable : true, // 设置是否可以最大化
			modal : true,
			layout : 'fit', // 设置窗口布局模式
			items : form,
			buttonAlign : "center",
			buttons : [{
				text : "保存",
				id:'save',
				iconCls : 'save',
				handler : function() {
					var status;
					if(record.data.status=='1'){
						status='2';
					}
					if(record.data.status=='3'){
						status='4';
					}
					if (form.getForm().isValid()) {
						form.getForm().submit({
							url : sysPath + "/fi/fiCarCostAction!save.action",
							params:{
								privilege:privilege,
								status:status,
								limit : pageSize
							},
							waitMsg : '正在保存数据...',
							success : function(form, action) {
								win.hide();
								Ext.Msg.alert(alertTitle,"保存成功!");
								dataStore.reload({
									params : {
										start : 0,
										limit : pageSize,
										privilege:privilege
									}
								});
							},
							failure : function(form, action) {
								if (action.faliureType == Ext.form.Action.SERVER_INVALID) {
									Ext.Msg.alert(alertTitle, "服务器连接异常，请联系管理员！");
								} else {
									if (action.result.msg) {
										win.hide();
										Ext.Msg.alert(alertTitle,
												action.result.msg);
									}
								}
							}
						});
					}

				}
			}, {
				text : "车队审核",
				id:'carAudit',
				handler : function() {
				Ext.Msg.confirm(alertTitle,'您确定要审核 <span style="color:red">'+record.data.carSignNo+'</span> 这条件数据吗?',function(btnYes) {
					if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
						if (form.getForm().isValid()) {
							form.getForm().submit({
								url : sysPath + "/fi/fiCarCostAction!save.action",
								params:{
									privilege:privilege,
									status:3,
									carVerifyName:userName,
									limit : pageSize
								},
								waitMsg : '正在保存数据...',
								success : function(form, action) {
									win.hide();
									Ext.Msg.alert(alertTitle,"保存成功!");
									dataStore.reload({
										params : {
											start : 0,
											limit : pageSize,
											privilege:privilege
										}
									});
								},
								failure : function(form, action) {
									if (action.faliureType == Ext.form.Action.SERVER_INVALID) {
										Ext.Msg.alert(alertTitle, "服务器连接异常，请联系管理员！");
									} else {
										if (action.result.msg) {
											win.hide();
											Ext.Msg.alert(alertTitle,
													action.result.msg);
										}
									}
								}
							});
						}
					
					
					}
				});
					
				}
			}, {
				text : "取消",
				handler : function() {
					win.close();
				}
			}]
		});
		if(record.data.status=='2'){
			Ext.getCmp('carAudit').show();
			Ext.getCmp('save').hide();
		}else{
			Ext.getCmp('save').show();
			Ext.getCmp('carAudit').hide();
		}
		
		win.on('hide', function() {
					form.destroy();
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
		if(record.data.departId!=bussDepart){
			Ext.Msg.alert(alertTitle,"不是本业务部门（"+bussDepartName+")的数据,请勿操作,谢谢",
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
									});
						}
					});
			 }
		});
	}
	
		function fiAduit(records){
			Ext.Msg.confirm(alertTitle,'您确定要审核这<span style="color:red"> '+records.length+' </span>条数据吗?',function(btnYes) {
				if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true){
					var ids="";
					for(var i=0;i<records.length;i++){
	 					if(i==0){
							ids += "aa["+i+"].id="+records[i].get("id")+"&aa["+i+"].ts="+records[i].get("ts");
						}else{
							ids += "&aa["+i+"].id="+records[i].get("id")+"&aa["+i+"].ts="+records[i].get("ts");
						}
					}	
					ids+="&privilege="+privilege;
					form1.getForm().doAction('submit', {
								url:sysPath+"/fi/fiCarCostAction!fiAudit.action",
								method : 'post',
								params : ids,
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
	
	function qxFiAduit(record){
		if(record.data.status!='4'){
			Ext.Msg.alert(alertTitle,"只有会计已审核的状态才能进行审核撤销",
									function() {
									});
			return;
		}
		
		Ext.Msg.confirm(alertTitle,'您确定要撤销 <span style="color:red">'+record.data.carSignNo+'</span> 这条数据的会计审核吗?',function(btnYes) {
			if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
				form1.getForm().doAction('submit', {
							url:sysPath+"/fi/fiCarCostAction!qxFiAudit.action",
							method : 'post',
							params : {
								id:record.data.id,
								privilege:privilege,
								ts:record.data.ts
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
	
	function carAudit(records){
		if(records.length==1){
			outCostIn(records[0]);
		}else{
			Ext.Msg.confirm(alertTitle,'您确定要审核这'+records.length+'条件记录吗?',function(btnYes) {
				var ids="";
				for(var i=0;i<records.length;i++){
 					if(i==0){
						ids += "aa["+i+"].id="+records[i].get("id")+"&aa["+i+"].ts="+records[i].get("ts");
					}else{
						ids += "&aa["+i+"].id="+records[i].get("id")+"&aa["+i+"].ts="+records[i].get("ts");
					}
				}	
				ids+="&privilege="+privilege;
				if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
					form1.getForm().doAction('submit', {
								url:sysPath+"/fi/fiCarCostAction!carAudit.action",
								method : 'post',
								params : ids,
								waitMsg : '正在保存数据...',
								success : function(form1, action) {
										Ext.Msg.alert(alertTitle,action.result.msg,function(){
											dataStore.reload();
										});
											
										
								},
								failure : function(form1, action) {
										Ext.Msg.alert(alertTitle,action.result.msg);
								}
					});
				}
			});
		}

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



    	 



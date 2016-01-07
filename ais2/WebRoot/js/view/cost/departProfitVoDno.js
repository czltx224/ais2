	Ext.QuickTips.init();
	var privilege=163;
	var comboxPage=comboSize;
	var ralaListUrl="fi/departProfitVoAction!ralaList.action";  
	
	/*
	 * 部门利润统计分析
	 * */
Ext.onReady(function(){
	Ext.QuickTips.init();
	var rownum=new Ext.grid.RowNumberer({
        width : 25,
		sortable : true	
	});
 
    var fields=[{name:"dno",mapping:'dno'},
    'dno',
	'cusName',
	'cusId',
	'trafficMode',
	'distributionMode',
	'takeMode',
	'consignee',
	'whoCash', // 付款方式
	'cusWeight',//计费重量
	'normSonderzugPrice',  //专车公布价
	'normCpRate',	  //预付提送公布费率
	'normConsigneeRate',	//到付提送公布费率
	'cpRate',   //预付费畜类
	'sonderzugRate',	// 到付费率
    'consigneeRate',  //专车费率
	'sonderzugPrice',//专车费
	'cpFee',//预付提送费
	'consigneeFee',//到付提送费
	'cusValueAddFee',//预付增值服务费
	'cpValueAddFee',//到付增值费总额
	'therAddFee',  //其他收入
	'totalIncomeFee',//营业收入合计
	'doGoodsCostFee',   //提货成本
	'carCostFee', //车辆成本
	'transitCostFee',  //中转成本
	'outCostFee',  //外发成本
	'therCostFee',  //其他成本
	'totalCostFee',  //成本合计
	'grossProfitFee',  //毛利润
	'sonderzug',//是否专车
	'accounting',
	'createTime',
	'departId',
	'yfSonderzugPrice',
	'departName',
	'serviceDepartName',
	'serviceDepartCode',
	'serviceName'];  
    
    
    var jsonread= new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'},
                    fields);
                              
	var sm = new Ext.grid.CheckboxSelectionModel();

	
	
	var dataStore = new Ext.data.Store({
                proxy: new Ext.data.HttpProxy({url:sysPath+"/"+ralaListUrl}),
                baseParams:{
                	limit:pageSize,
                	privilege:privilege
                },
                reader:jsonread
    });

	var dataStatus=new Ext.data.SimpleStore({
			 auteLoad:true, //此处设置为自动加载
  			 data:[['1','创建日期'],['2','会计日期']],
   			 fields:["id","name"]
	});
	
 	//配送方式
	var distributionStore = new Ext.data.Store({ 
            storeId:"distributionStore",                        
            proxy: new Ext.data.HttpProxy({url:sysPath+"/sys/dictionaryAction!ralaList.action",method:'post'}),
            baseParams:{filter_EQL_basDictionaryId:4},
            reader: new Ext.data.JsonReader({
            root: 'result', totalProperty: 'totalCount'
           }, [  {name:'typeName', mapping:'typeName',type:'string'}           
              ])    
    });
    distributionStore.load();
    
    	//客商Store
	var customerStore = new Ext.data.Store({
		storeId : "customerStore",
		baseParams : {
			limit : pageSize,
			privilege : 61//,
			//filter_EQS_custprop:custprop
		},
		proxy : new Ext.data.HttpProxy({
			url : sysPath + "/sys/customerAction!list.action" 
		}),
		reader : new Ext.data.JsonReader({
					root : 'result',
					totalProperty : 'totalCount'
		}, [
		{name:'cusName'},
		{name:'id'}
		])
	});
    
    //提货方式
	var doGoodsStore = new Ext.data.Store({ 
            storeId:"doGoodsStore",                        
            proxy: new Ext.data.HttpProxy({url:sysPath+"/sys/dictionaryAction!list.action",method:'post'}),
            baseParams:{filter_EQL_basDictionaryId:14},
            reader: new Ext.data.JsonReader({
            root: 'result', totalProperty: 'totalCount'
           }, [  {name:'typeName', mapping:'typeName',type:'string'}           
              ])    
    });

    //运输方式
	var carDoStore = new Ext.data.Store({ 
            storeId:"carDoStore",                        
            proxy: new Ext.data.HttpProxy({url:sysPath+"/sys/dictionaryAction!ralaList.action",method:'post'}),
            baseParams:{filter_EQL_basDictionaryId:18},
            reader: new Ext.data.JsonReader({
            root: 'result', totalProperty: 'totalCount'
           }, [  {name:'typeName', mapping:'typeName',type:'string'}           
              ])    
    });
 	
	 //付款方式
	var payMoneyStore = new Ext.data.Store({ 
            storeId:"payMoneyStore",                        
            proxy: new Ext.data.HttpProxy({url:sysPath+"/sys/dictionaryAction!ralaList.action",method:'post'}),
            baseParams:{filter_EQL_basDictionaryId:17},
            reader: new Ext.data.JsonReader({
            root: 'result', totalProperty: 'totalCount'
           }, [  {name:'typeName', mapping:'typeName',type:'string'}           
              ])    
    });
    
    //毛利润是否亏损
    var isStore=new Ext.data.SimpleStore({
			 auteLoad:true, //此处设置为自动加载
  			 data:[['1','是'],['0','否']],
   			 fields:["id","name"]
	});
	
	//客服部门
	var serviceDepartStore = new Ext.data.Store({ 
            storeId:"serviceDepartStore",                        
            proxy: new Ext.data.HttpProxy({url:sysPath+"/sys/departAction!findDepart.action",method:'post'}),
            baseParams:{
                filter_EQL_isCusDepart:1
            },
            reader: new Ext.data.JsonReader({
            root: 'resultMap', totalProperty:'totalCount'
           }, [  
                 {name:'departName', mapping:'DEPARTNAME'},    
                 {name:'departId', mapping: 'DEPARTNO'}
              ]),
            sortInfo:{field:'departId',direction:'ASC'}
     });

    //var summary = new Ext.ux.grid.GridSummary();
   	
   	var tobar  = new Ext.Toolbar({
		items : ['&nbsp;',
			'<B>日期:</B>',{
	    		xtype : 'datefield',
	    		id : 'startDate',
	    		format : 'Y-m-d',
	    		emptyText : "选择开始时间",
	    		width : 80,
	    		selectOnFocus:true,
	    		value:new Date().add(Date.DAY, -1),
	    		enableKeyEvents:true,
	    		listeners : {
	    			'select' : function() {
	    			   var start = Ext.getCmp('startDate').getValue()
	    			      .format("Y-m-d");
	    			   Ext.getCmp('endDate').setMinValue(start);
	    		     },
	    		     keyup:function(numberfield, e){
	                     if(e.getKey() == 13 ){
							searchLog();
	                     }
	 				 }
	    		}
    		},'&nbsp;','<B>至</B>','&nbsp;',{
	    		xtype : 'datefield',
	    		id : 'endDate',
	    		selectOnFocus:true,
	    		format : 'Y-m-d',
	    		value:new Date(),
	    		emptyText : "选择结束时间",
	    		width : 80,
	    		enableKeyEvents:true,
	    		listeners : {
	    		     keyup:function(numberfield, e){
	                     if(e.getKey() == 13 ){
							searchLog();
	                     }
	 				 }
	    		}
    	    },/*'-','&nbsp;','<B>录单部门:</B>', {
				xtype : 'combo',
				id:'comboType',
				typeAhead:true,
				selectOnFocus:true,
				hiddenId : 'dictionaryName',
    			hiddenName : 'dictionaryName',
				triggerAction : 'all',
				store : bussStore,
				width:80,
				listWidth:245,
				minChars : 1,
				allowBlank : true,
				queryParam : 'filter_LIKES_departName',
				emptyText : "请选择部门名称",
				forceSelection : true,
				fieldLabel:'部门名称',
				editable : true,
				pageSize:comboxPage,
				displayField : 'departName',//显示值，与fields对应
				valueField : 'departId',//value值，与fields对应
				name : 'departId',
				enableKeyEvents:true,
	    		listeners : {
	    		     keyup:function(numberfield, e){
	                     if(e.getKey() == 13 ){
							searchLog();
	                     }
	 				 }
	    		}
		    },*/'-','&nbsp;','<B>代理:</B>',{
				xtype : 'combo',
				typeAhead : false,
				forceSelection : true,
				minChars : 1,
				selectOnFocus:true,
				listWidth:245,
				triggerAction : 'all',
				store : customerStore,
				pageSize : pageSize,
				queryParam : 'filter_LIKES_cusName',
				id : 'combocus',
				valueField : 'id',
				displayField : 'cusName',
				hiddenName : 'cusName',
				emptyText:'请选择代理名称',
				width : 70,
				enableKeyEvents : true,
				listeners : {
					select:function(v){
						//alert(v.getValue());
					},
	    		     keyup:function(numberfield, e){
	                     if(e.getKey() == 13 ){
							searchLog();
	                     }
	 				 }
	    		}
			},'-','&nbsp;','<B>配送方式:</B>',{
				xtype : 'combo',
				id:'distribution',
				triggerAction : 'all',
				store : distributionStore,
				forceSelection : true,
				width:50,
				selectOnFocus:true,
				fieldLabel:'配送方式',
				emptyText : "请选择配送方式",
				//	editable:false,
			//	mode : "local",//获取本地的值
				displayField : 'typeName',//显示值，与fields对应
				valueField : 'typeName',//value值，与fields对应
				name : 'distribution',
				enableKeyEvents:true,
				listeners : {
	 				keyup:function(numberfield, e){
	                     if(e.getKey() == 13 ){
							searchLog();
	                     }
	 				}
	 			}
	    	},'-','&nbsp;','<B>提货方式:</B>',{
				xtype : 'combo',
				id:'doGoods',
				selectOnFocus:true,
				triggerAction : 'all',
				store : doGoodsStore,
				forceSelection : true,
				emptyText : "请选择提货方式",
				//	editable:false,
				fieldLabel:'提货方式',
				//mode : "local",//获取本地的值
				displayField : 'typeName',//显示值，与fields对应
				valueField : 'typeName',//value值，与fields对应
				name : 'doGoods',
				width:70,
				enableKeyEvents:true,
				listeners : {
	 				keyup:function(numberfield, e){
	                     if(e.getKey() == 13 ){
							searchLog();
	                     }
	 				}
	 			}
	    	},'-','&nbsp;','<B>运输方式:</B>',{
				xtype : 'combo',
				id:'carDo',
				selectOnFocus:true,
				triggerAction : 'all',
				store : carDoStore,
				forceSelection : true,
				emptyText : "请选择运输方式",
				//	editable:false,
			//	mode : "local",//获取本地的值
				displayField : 'typeName',//显示值，与fields对应
				valueField : 'typeName',//value值，与fields对应
				name : 'carDo',
				width:50,
				enableKeyEvents:true,
				listeners : {
	 				keyup:function(numberfield, e){
	                     if(e.getKey() == 13 ){
							searchLog();
	                     }
	 				}
	 			}
	    	},'-','&nbsp;','<B>所属部门<span style="color:red">*</span>:</B>',{
				xtype : 'combo',
				id:'comboType',
				typeAhead:true,
				selectOnFocus:true,
				triggerAction : 'all',
				store : serviceDepartStore,
				width:80,
				listWidth:245,
				minChars : 1,
				allowBlank : true,
				queryParam : 'filter_LIKES_departName',
				emptyText : "请选择部门名称",
				forceSelection : true,
				fieldLabel:'部门名称',
				editable : true,
				pageSize:comboxPage,
				displayField : 'departName',//显示值，与fields对应
				valueField : 'departId',//value值，与fields对应
				name : 'departId',
				enableKeyEvents:true,
	    		listeners : {
	    		     keyup:function(numberfield, e){
	                     if(e.getKey() == 13 ){
							searchLog();
	                     }
	 				 },
	 				 select:function(v){
	                    //  alert(v.getValue());
	 				 }
	    		}
		    } ]});
		
	   	var threebar  = new Ext.Toolbar({
			items : ['&nbsp;','<B>收货人:</B>',{
				xtype : 'textfield',
				id:'conginee',
				selectOnFocus:true,
				name : 'conginee',
				width:50,
				maxLength:15,
				enableKeyEvents:true,
				listeners : {
	 				keyup:function(numberfield, e){
	                     if(e.getKey() == 13 ){
							searchLog();
	                     }
	 				}
	 			}
	    	},'-','&nbsp;','<B>付款方式:</B>',{
				xtype : 'combo',
				id:'payMoney',
				selectOnFocus:true,
				triggerAction : 'all',
				store : payMoneyStore,
				forceSelection : true,
				emptyText : "请选择付款方式",
				//	editable:false,
				//mode : "local",//获取本地的值
				displayField : 'typeName',//显示值，与fields对应
				valueField : 'typeName',//value值，与fields对应
				name : 'payMoney',
				width:60,
				enableKeyEvents:true,
				listeners : {
	 				keyup:function(numberfield, e){
	                     if(e.getKey() == 13 ){
							searchLog();
	                     }
	 				}
	 			}  //isStore
	    	},'-','&nbsp;','<B>是否亏损:</B>',{
				xtype : 'combo',
				id:'isFi',
				triggerAction : 'all',
				store : isStore,
				allowBlank : true,
				emptyText : "请选择工作状态",
				forceSelection : true,
				editable : true,
				mode : "local",//获取本地的值
				displayField : 'name',//显示值，与fields对应
				valueField : 'id',//value值，与fields对应
				name : 'isFi',
				width:50
	    	},'-','&nbsp;','<B>毛利润范围:</B>',{
				xtype : 'numberfield',
				id:'rate',
				selectOnFocus:true,
				name : 'payMoney',
				width:50,
				decimalPrecision:2,
				allowNegative:true,
				maxLength:15,
				enableKeyEvents:true,
				listeners : {
	 				keyup:function(numberfield, e){
	                     if(e.getKey() == 13 ){
							searchLog();
	                     }
	 				}
	 			}
	    	},'&nbsp;','<B>至</B>','&nbsp;',{
				xtype : 'numberfield',
				id:'rate2',
				selectOnFocus:true,
				name : 'payMoney',
				width:50,
				decimalPrecision:2,
				allowNegative:true,
				maxLength:15,
				enableKeyEvents:true,
				listeners : {
	 				keyup:function(numberfield, e){
	                     if(e.getKey() == 13 ){
							searchLog();
	                     }
	 				}
	 			}
	    	},'-','&nbsp;','<B>重量范围:</B>',{
				xtype : 'numberfield',
				id:'weight',
				selectOnFocus:true,
				name : 'payMoney',
				width:50,
				decimalPrecision:2,
				allowNegative:true,
				maxLength:15,
				enableKeyEvents:true,
				listeners : {
	 				keyup:function(numberfield, e){
	                     if(e.getKey() == 13 ){
							searchLog();
	                     }
	 				}
	 			}
	    	},'&nbsp;','<B>至</B>','&nbsp;',{
				xtype : 'numberfield',
				id:'weight2',
				selectOnFocus:true,
				name : 'payMoney',
				width:50,
				decimalPrecision:2,
				allowNegative:true,
				maxLength:15,
				enableKeyEvents:true,
				listeners : {
	 				keyup:function(numberfield, e){
	                     if(e.getKey() == 13 ){
							searchLog();
	                     }
	 				}
	 			}
	    	}]});
	
	
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
			forceFit : false
		},
		//autoExpandColumn : 1,
		frame : false,
		loadMask : true,
		sm:sm,
	//	plugins : [summary],
		stripeRows : true,
		tbar:['&nbsp;',{
			text : '<b>导出</b>',
			iconCls : 'table',
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
		},'-','&nbsp;',{
   			xtype:'label',
   			id:'showMsg',
   			width:380
   		}],
		columns:[ rownum,sm,
        			{header: '配送单号', dataIndex: 'dno',width:60,sortable : true},
        			{header: '收货人', dataIndex: 'consignee',width:60,sortable : true},
        			{header: '代理ID', dataIndex: 'cusId',hidden:true,width:60,sortable : true},
        			{header: '发货代理', dataIndex: 'cusName',width:90,sortable : true},
			        {header: '是否专车', dataIndex: 'sonderzug',width:60,sortable : true,renderer:function(v){
										if(v=='0'){
											return '否';
										}else if(v=='1'){
											return '是';
										}else{
											return '出错，无状态';
										}
			        					}},
        			{header: '运输方式', dataIndex: 'trafficMode',width:60,sortable : true},
        			{header: '配送方式', dataIndex: 'distributionMode',width:70,sortable : true},
      				{header: '提货方式', dataIndex: 'takeMode',width:70,sortable : true},
        			{header: '付款方式', dataIndex: 'whoCash',width:70,sortable : true},
        			
			        {header: '计费重量', dataIndex: 'cusWeight',width:70,sortable : true},
					{header: '专车标准价', dataIndex: 'normSonderzugPrice',width:70,sortable : true},
					{header: '预付提送标准价', dataIndex: 'normCpRate',width:95,sortable : true},
					{header: '到付提送标准价', dataIndex: 'normConsigneeRate',width:95,sortable : true},
					
					{header: '预付提送价', dataIndex: 'cpRate',width:70,sortable : true},
					{header: '到付提送价', dataIndex: 'consigneeRate',width:70,sortable : true},
        			{header: '预付提送费', dataIndex: 'cpFee',width:70,sortable : true},
        			{header: '到付提送费', dataIndex: 'consigneeFee',width:70,sortable : true},
					
        			
        			{header: '预付增值费', dataIndex: 'cusValueAddFee',width:70,sortable : true},
        			{header: '到付增值费', dataIndex: 'cpValueAddFee',width:70,sortable : true},
        			{header: '预付专车费', dataIndex: 'yfSonderzugPrice',width:70,sortable : true},
        			{header: '到付专车费', dataIndex: 'sonderzugPrice',width:70,sortable : true},
        			{header: '其他收入', dataIndex: 'therAddFee',width:70,sortable : true},
        			
        			{header: '收入合计', dataIndex: 'totalIncomeFee',width:80,sortable : true},
        			{header: '提货成本', dataIndex: 'doGoodsCostFee',width:60,sortable : true},
        			{header: '车辆成本', dataIndex: 'carCostFee',width:60,sortable : true},
        			{header: '中转成本', dataIndex: 'transitCostFee',width:60,sortable : true},
        			{header: '外发成本', dataIndex: 'outCostFee',width:60,sortable : true},
        			{header: '其他成本', dataIndex: 'therCostFee',width:60,sortable : true},
        			{header: '成本合计', dataIndex: 'totalCostFee',width:60,sortable : true}, 
        			{header: '毛利润', dataIndex: 'grossProfitFee',width:60,sortable : true}, 
        			
        			{header: '录入日期', dataIndex: 'createTime',width:110,sortable : true},
        			{header: '会计日期', dataIndex: 'accounting',width:110,sortable : true},
        			{header: '客服员', dataIndex: 'serviceName',width:60,sortable : true}, 
        			{header: '客服部门', dataIndex: 'serviceDepartName',width:60,sortable : true},
        			{header: '客服部门CODE', dataIndex: 'serviceDepartCode',hidden:true,width:60,sortable : true}],
			store : dataStore,
			listeners: {
              render: function(){
                  tobar.render(this.tbar);
                  threebar.render(this.tbar);
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

		function searchLog() {
			//var dNo=Ext.get("dno").dom.value;
			Ext.getCmp('showMsg').getEl().update('<span style="color:red"></span>');

			var comboType=Ext.getCmp("comboType").getValue();  //客服部门
			var combocus=Ext.getCmp("combocus").getValue();  //代理
			var doGoods=Ext.getCmp("doGoods").getValue();    //提货方式
			var conginee=Ext.getCmp('conginee').getValue(); //收货人
			var carDo=Ext.getCmp('carDo').getValue();  // 运输方式
			
			var rate=Ext.getCmp('rate').getValue();  // 利润
			var rate2=Ext.getCmp('rate2').getValue();  // 利润2
			var payMoney=Ext.getCmp('payMoney').getValue();  //付款方式
			
			var weight=Ext.getCmp('weight').getValue();  // 重量
			var weight2=Ext.getCmp('weight2').getValue();  // 重量2
			
			
			var distribution=Ext.getCmp("distribution").getValue();  //配送方式  
			var isFi=Ext.getCmp("isFi").getValue();  //是否亏损
			var start='';
			var end ='';
			if(Ext.getCmp('startDate').getValue()!=""){
			   	start = Ext.getCmp('startDate').getValue().format("Y-m-d");
			}
			if(Ext.getCmp('endDate').getValue()!=""){
			   	end = Ext.getCmp('endDate').getValue().format("Y-m-d");
			}

   			if(isFi=='1'){
	   			Ext.apply(dataStore.baseParams={
	   				privilege:privilege,
					filter_EQL_cusId:combocus,
					filter_EQS_trafficMode:carDo,
					filter_LTN_grossProfitFee:0,
					filter_LIKES_serviceDepartCode:comboType,
					filter_EQS_distributionMode:distribution,
					filter_EQS_takeMode:doGoods,
					filter_LIKES_consignee:conginee,
					filter_EQS_whoCash:payMoney,
					filter_GEN_grossProfitFee:rate,
					filter_LEN_grossProfitFee:rate2,
					filter_GEN_cusWeight:weight,
					filter_LEN_cusWeight:weight2,
					filter_GED_createTime : start,
	    		    filter_LED_createTime : end,
				    limit : pageSize
				});
   			}else if(isFi=='0'){
   				if(rate==''){
	   				Ext.apply(dataStore.baseParams={
						filter_EQL_cusId:combocus,
						filter_EQS_trafficMode:carDo,
						filter_GEN_grossProfitFee:0,
						filter_LIKES_serviceDepartCode:comboType,
						filter_EQS_distributionMode:distribution,
						filter_EQS_takeMode:doGoods,
						filter_LIKES_consignee:conginee,
						filter_EQS_whoCash:payMoney,
						filter_LEN_grossProfitFee:rate2,
						filter_GEN_cusWeight:weight,
						filter_LEN_cusWeight:weight2,
						filter_GED_createTime : start,
		    		    filter_LED_createTime : end,
					    limit : pageSize
					});
   				}else{
   					Ext.apply(dataStore.baseParams={
   						privilege:privilege,
						filter_EQL_cusId:combocus,
						filter_EQS_trafficMode:carDo,
						filter_GEN_grossProfitFee:0,
						filter_LIKES_serviceDepartCode:comboType,
						filter_EQS_distributionMode:distribution,
						filter_EQS_takeMode:doGoods,
						filter_LIKES_consignee:conginee,
						filter_EQS_whoCash:payMoney,
						filter_GEN_grossProfitFee:rate,
						filter_LEN_grossProfitFee:rate2,
						filter_GEN_cusWeight:weight,
						filter_LEN_cusWeight:weight2,
						filter_GED_createTime : start,
		    		    filter_LED_createTime : end,
					    limit : pageSize
					});
   				}
   			}else{
   				Ext.apply(dataStore.baseParams={
					filter_EQL_cusId:combocus,
					filter_EQS_trafficMode:carDo,
					filter_LIKES_serviceDepartCode:comboType,
					filter_EQS_distributionMode:distribution,
					filter_EQS_takeMode:doGoods,
					filter_LIKES_consignee:conginee,
					filter_EQS_whoCash:payMoney,
					privilege:privilege,
					filter_GEN_grossProfitFee:rate,
					filter_LEN_grossProfitFee:rate2,
					filter_GEN_cusWeight:weight,
					filter_LEN_cusWeight:weight2,
					filter_GED_createTime : start,
	    		    filter_LED_createTime : end,
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
	
	/**
	 * 导出按钮事件
	 */
	function exportinfo() {
		  parent.parent.exportExl(recordGrid);
	}
	

});


	
	/**
	 * 打印按钮事件
	 */
	function printinfo() {
		Ext.Msg.alert("提示", "正在开发中...");
	}

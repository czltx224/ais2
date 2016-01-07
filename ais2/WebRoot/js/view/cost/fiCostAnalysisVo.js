	Ext.QuickTips.init();
	var privilege=159;
	var comboxPage=comboSize;
	var saveUrl="sys/loadingbrigadeAction!save.action";
	
	var ralaListUrl="fi/fiCostAnalysisVoAction!ralaList.action";
	
Ext.onReady(function(){
	Ext.QuickTips.init();
	var rownum=new Ext.grid.RowNumberer({
        width : 25,
		sortable : true	
	});
 
    var fields=[{name:"dno",mapping:'dno'},
	'cpName',
	'consignee',
	'consigneeTel',
	'distributionMode',//配送方式
	'takeMode',//提货方式
	'createTime', //成本日期
	'trafficMode',//运输方式
	
	'costType', //成本大类
	'doGoodCost', //提货成本
	'signDanCost', //签单大类
	'transitCost',  //中转成本
	'outSideCost',  // 外发成本
	'therCost', // 其他成本
 	'cusId',
	'createDepartId',
	'tatalCost',  // 其他成本
	'createDanTime',// 录单日期
	'goWhereS'];  // 供应商
    
    
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
                reader:jsonread,
                sortInfo:{field:'dno',direction:'DESC'}
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
            proxy: new Ext.data.HttpProxy({url:sysPath+"/sys/dictionaryAction!ralaList.action",method:'post'}),
            baseParams:{filter_EQL_basDictionaryId:14},
            reader: new Ext.data.JsonReader({
            root: 'result', totalProperty: 'totalCount'
           }, [  {name:'typeName', mapping:'typeName',type:'string'}           
              ])    
    });
    doGoodsStore.load();
 	
	var bussStore = new Ext.data.Store({ 
            storeId:"bussStore",                        
            proxy: new Ext.data.HttpProxy({url:sysPath+"/sys/departAction!findDepart.action",method:'post'}),
            baseParams:{
               privilege:53,
               filter_EQL_isBussinessDepa:1
               },
            reader: new Ext.data.JsonReader({
                        root: 'resultMap', totalProperty:'totalCount'
                    },[ {name:'departName', mapping:'DEPARTNAME'},    
                        {name:'departId', mapping: 'DEPARTID'}
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
    	    },'-','&nbsp;','<B>录单部门:</B>', {
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
		    },'-','&nbsp;','<B>代理:</B>',{
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
				mode : "local",//获取本地的值
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
				mode : "local",//获取本地的值
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
	    	},'-','&nbsp;','<B>配送单号<span style="color:red">*</span>:</B>', {
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
		},'-','&nbsp;',/*{
			text : '<b>打印</b>',
			iconCls : 'table',
			id : 'printbtn',
			tooltip : '打印',
			handler : printinfo
		},'-','&nbsp;',*/{
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
        			{header: '配送单号', dataIndex: 'dno',width:80,sortable : true},
        			{header: '代理ID', dataIndex: 'cusId',hidden:true,width:60,sortable : true},
        			{header: '发货代理', dataIndex: 'cpName',width:90,sortable : true},
        			{header: '运输方式', dataIndex: 'trafficMode',width:60,sortable : true},
			        {header: '配送方式', dataIndex: 'distributionMode',width:60,sortable : true},
        			{header: '提货方式', dataIndex: 'takeMode',width:60,sortable : true},
   					{header: '提货成本', dataIndex: 'doGoodCost',width:70,sortable : true},
      				{header: '签单成本', dataIndex: 'signDanCost',width:70,sortable : true},
        			{header: '中转成本', dataIndex: 'transitCost',width:70,sortable : true},
			        {header: '外发成本', dataIndex: 'outSideCost',width:70,sortable : true},
					{header: '其他成本', dataIndex: 'therCost',width:60,sortable : true},
        			{header: '合计', dataIndex: 'tatalCost',width:60,sortable : true},
        			{header: '供应商', dataIndex: 'goWhereS',width:90,sortable : true},
        			{header: '收货人', dataIndex: 'consignee',width:60,sortable : true},
        			{header: '收货人电话', dataIndex: 'consigneeTel',width:90,sortable : true},		
        			{header: '传真录入日期', dataIndex: 'createTime',width:110,sortable : true}],
			store : dataStore,
			listeners: {
              render: function(){
                  tobar.render(this.tbar);
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
			var dNo=Ext.get("dno").dom.value;
			Ext.getCmp('showMsg').getEl().update('<span style="color:red"></span>');
			if(dNo==''){
					var comboType=Ext.getCmp("comboType").getValue();
					var combocus=Ext.getCmp("combocus").getValue();
					var doGoods=Ext.getCmp("doGoods").getValue();
					var distribution=Ext.getCmp("distribution").getValue();
					var start='';
					var end ='';
					if(Ext.getCmp('startDate').getValue()!=""){
					   	var start = Ext.getCmp('startDate').getValue().format("Y-m-d");
					}
					if(Ext.getCmp('endDate').getValue()!=""){
					   	var end = Ext.getCmp('endDate').getValue().format("Y-m-d");
					}

	    			Ext.apply(dataStore.baseParams={
						filter_EQL_cusId:combocus,
						filter_EQL_createDepartId:comboType,
						filter_EQS_distributionMode:distribution,
						filter_EQS_takeMode:doGoods,
						privilege:privilege,
						filter_GED_createTime : start,
		    		    filter_LED_createTime : end,
					    limit : pageSize
					});
			}else{
				var regex=/^[1-9]\d*$/;
			    if(!regex.test(dNo)){
			       	Ext.getCmp('showMsg').getEl().update('<span style="color:red">配送单号输入格式不正确，无法查询。</span>');
			       	Ext.getCmp('dNo').focus();	
			       	Ext.getCmp('dNo').markInvalid("配送单号输入格式不正确");
					Ext.getCmp('dNo').selectText();	
			     
					return;
			    }
				Ext.getCmp('showMsg').getEl().update('<span style="color:red">输入配送单号查询时，其他查询条件将无效。</span>');
				Ext.apply(dataStore.baseParams={
					filter_EQL_dno:dNo,
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
		parent.exportExl(recordGrid);
	}
	
	/**
	 * 打印按钮事件
	 */
	function printinfo() {
		Ext.Msg.alert("提示", "正在开发中...");
	}

});



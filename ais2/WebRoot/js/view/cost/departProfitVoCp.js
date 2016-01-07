	Ext.QuickTips.init();
	var comboxPage=comboSize;
	/*
	 * 代理统计分析
	 * */
	
Ext.onReady(function(){
	Ext.QuickTips.init();
	var rownum=new Ext.grid.RowNumberer({
        width : 25,
		sortable : true	
	});
 
    var fields=[ 
	{name:'cusName',mapping:'CUSNAME'},
	{name:'countDno',mapping:'COUNTDNO'},
	{name:'piece',mapping:'PIECE'},
	{name:'cusId',mapping:'CUSID'},
	{name:'cusWeight',mapping:'CUSWEIGHT'},//计费重量
	{name:'sonderzugPrice',mapping:'SONDERZUGPRICE'},//专车费
	{name:'cpFee',mapping:'CPFEE'},//预付提送费
	{name:'consigneeFee',mapping:'CONSIGNEEFEE'},//到付提送费
	{name:'cusValueAddFee',mapping:'CUSVALUEADDFEE'},//预付增值服务费
	{name:'cpValueAddFee',mapping:'CPVALUEADDFEE'},//到付增值费总额
	{name:'therAddFee',mapping:'THERADDFEE'},  //其他收入
	{name:'totalIncomeFee',mapping:'TOTALINCOMEFEE'},//营业收入合计
	{name:'doGoodCostFee',mapping:'DOGOODCOSTFEE'},   //提货成本
	{name:'signDanCostFee',mapping:'SIGNDANCOSTFEE'},
	{name:'transitCostFee',mapping:'TRANSITCOSTFEE'},  //中转成本
	{name:'outCostFee',mapping:'OUTSIDECOSTFEE'},  //外发成本
	{name:'therCostFee',mapping:'THERCOSTFEE'},  //其他成本
	{name:'totalCostFee',mapping:'TOTALCOSTFEE'},  //成本合计
	{name:'maoRate',mapping:'MAORATE'},
	{name:'grossProfitFee',mapping:'GROSSPROFITFEE'},
 	{name:'paymentcollection',mapping:'PAYMENTCOLLECTION'},
	{name:'yfSonderzugPrice',mapping:'YFSONDERZUGPRICE'}];  
    
    
    var jsonread= new Ext.data.JsonReader({
                    root:'resultMap',
                    totalProperty:'totalCount'},
                    fields);
                              
	var dataStore = new Ext.data.Store({
                proxy: new Ext.data.HttpProxy({url:sysPath+"/fi/departProfitVoAction!queryCqList.action"}),
                baseParams:{
                	limit:pageSize
                },
                reader:jsonread
    });

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
	
	//部门
	var serviceDepartStore = new Ext.data.Store({ 
            storeId:"serviceDepartStore",                        
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
              ])
     });

    //var summary = new Ext.ux.grid.GridSummary();
    
   	var cm = new Ext.grid.ColumnModel([rownum,
        			{header: '代理ID', dataIndex: 'cusId',hidden:true,width:80,sortable : true},
        			{header: '代理名称', dataIndex: 'cusName',width:100,sortable : true},
			        {header: '票数', dataIndex: 'countDno',width:60,sortable : true},
			        {header: '件数', dataIndex: 'piece',width:60,sortable : true},
			        {header: '重量', dataIndex: 'cusWeight',width:60,sortable : true},
			        
			        {header: '其他收入', dataIndex: 'therAddFee',width:70,sortable : true},
        			{header: '收入合计', dataIndex: 'totalIncomeFee',width:70,sortable : true},
        			{header: '成本合计', dataIndex: 'totalCostFee',width:70,sortable : true}, 
			        
        			{header: '毛利', dataIndex: 'grossProfitFee',width:70,sortable : true}, 
        			{header: '毛利率', dataIndex: 'maoRate',width:110,sortable : true},
        			{header: '预付提送费', dataIndex: 'cpFee',width:70,sortable : true},
        			{header: '预付专车费', dataIndex: 'yfSonderzugPrice',width:80,sortable : true},
        			{header: '预付增值费', dataIndex: 'cpValueAddFee',width:70,sortable : true},
        			{header: '到付提送费', dataIndex: 'consigneeFee',width:70,sortable : true},
        			{header: '到付增值费', dataIndex: 'cusValueAddFee',width:70,sortable : true},
        			{header: '到付专车费', dataIndex: 'sonderzugPrice',width:70,sortable : true},

        			
        			{header: '提货成本', dataIndex: 'doGoodCostFee',width:70,sortable : true},
        			{header: '签单成本', dataIndex: 'signDanCostFee',width:70,sortable : true},
        			{header: '中转成本', dataIndex: 'transitCostFee',width:70,sortable : true},
        			{header: '外发成本', dataIndex: 'outCostFee',width:70,sortable : true},
        			{header: '其他成本', dataIndex: 'therCostFee',width:70,sortable : true},
        	
        			{header: '代收货款', dataIndex: 'paymentcollection',width:70,sortable : true}
        			]);
   	
   	var onebar  = new Ext.Toolbar({
		items : ['&nbsp;',
			'录单日期:',{
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
    		},'&nbsp;','至','&nbsp;',{
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
    	    },'-','&nbsp;','发货代理:',{
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
			},'-','&nbsp;','录单部门:',{
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
		    },'&nbsp;','-','&nbsp;',{
			text : '<b>导出</b>',
			iconCls : 'table',
			id : 'exportbtn',
			tooltip : '导出',
			handler : function(){
				parent.parent.exportExl(Ext.getCmp('grid'));
			}
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
   		} ]});
	
	var grid = new Ext.grid.GridPanel({
						region : 'center', // 和VIEWPORT布局模型对应，充当center区域布局
						// collapsible : true,
						//border : true,
						// 表格面板标题,默认为粗体，我不喜欢粗体，这里设置样式将其格式为正常字体
						//title : '<span style="font-weight:normal">查询结果</span>',
						autoScroll : true,
					//	hideHeaders:true,
						frame : false,
					//	autoExpandColumn: 'consigneeInfoString',
						columnLines:true,  //True表示为在列分隔处显示分隔符。默认为false
						enableHdMenu:true,  //True表示为在头部出现下拉按钮，以激活头部菜单
					//	enableColumnHide:
					//	floating:true,//浮动面板
					//	draggable:true,//面板拖动
						trackMouseOver:true,
					//	enableColumnMove:false,
					//	enableDragDrop:true,
						//enableColumnResize:false,  //可以拉伸。
						id:'grid',
						//plugins: expander,
						store : dataStore, // 数据存储
						stripeRows : true, // 斑马线
						cm : cm, // 列模型
						bbar  : new Ext.PagingToolbar({
								pageSize : pageSize, 
								store : dataStore,
								displayInfo : true,
								displayMsg : '显示第 {0} 条到 {1} 条记录，共 {2} 条',
								emptyMsg : "没有记录信息显示"
						}),// 分页工具栏
						viewConfig : {
							// 不产横向生滚动条, 各列自动扩展自动压缩, 适用于列数比较少的情况
							forceFit : false,
							columnsText : "显示的列",
							sortAscText : "升序",
							sortDescText : "降序"
						
						},
						loadMask : {
							msg : '正在加载表格数据,请稍等...'
						}
					});
		
		var qForm = new Ext.form.FormPanel({
						region : 'north',
						id:"qForm",
						iconCls:'fieldEdit',
						title : '<span style="font-weight:normal">统计信息<span>',
						collapsible : true,
						border : true,
						draggable:true,
						hideBorders:true,
						//tbar:onebar,
						labelWidth : 60, // 标签宽度
						frame : true, //是否渲染表单面板背景色
						labelAlign : 'right', // 标签对齐方式
						bodyStyle : 'padding:3 5 0', // 表单元素和表单面板的边距
						buttonAlign : 'center',
						height : 145,
						items : [{
									layout : 'column',
									border : false,
									items : [{
												columnWidth : .2,
												layout : 'form',
												labelWidth : 80, // 标签宽度
												border : false,
												items : [{
															fieldLabel : '代理个数',
															id:'total_count',
															readOnly:true,
															xtype : 'numberfield', // 设置为数字输入框类型
															anchor : '95%'
														}, {
															fieldLabel : '预付合计',
														//	id:'dno',
															id:'total_yfFee',
															readOnly:true,
															xtype : 'numberfield', // 设置为数字输入框类型
															anchor : '95%'
														}, {
															fieldLabel : '提货成本合计',
														//	id:'dno',
															id:'total_thcost',
															readOnly:true,
															xtype : 'numberfield', // 设置为数字输入框类型
															anchor : '95%'
														},{
															fieldLabel : '<span style="color:red">收入合计</span>',
															id:'total_income',
															name:'dno',
															readOnly:true,
															xtype : 'numberfield', // 设置为数字输入框类型
															anchor : '95%'
														}]
											}, {
												columnWidth : .2,
												layout : 'form',
												labelWidth : 80, // 标签宽度
												defaultType : 'textfield',
												border : false,
												items : [{
															fieldLabel : '总票数',
														//	id:'dno',
															id:'total_dno',
															readOnly:true,
															xtype : 'numberfield', // 设置为数字输入框类型
															anchor : '95%'
														},{
															fieldLabel : '到付合计',
															id:'total_dfFee',
															name:'dno',
															readOnly:true,
															xtype : 'numberfield', // 设置为数字输入框类型
															anchor : '95%'
														}, {
															fieldLabel : '中转成本合计',
														//	id:'dno',
															id:'total_zzcost',
															readOnly:true,
															xtype : 'numberfield', // 设置为数字输入框类型
															anchor : '95%'
														}, {
															fieldLabel : '<span style="color:red">成本合计</span>',
															id:'total_cost',
															name:'dno',
															readOnly:true,
															xtype : 'numberfield', // 设置为数字输入框类型
															anchor : '95%'
														}]
											},  {
												columnWidth : .2,
												layout : 'form',
												labelWidth : 80, // 标签宽度
												defaultType : 'textfield',
												border : false,
												items : [{
															fieldLabel : '总件数',
															id:'total_piece',
															name:'dno',
															readOnly:true,
															xtype : 'numberfield', // 设置为数字输入框类型
															anchor : '95%'
														},{
															fieldLabel : '其他收入合计',
															id:'total_therFee',
															name:'dno',
															readOnly:true,
															xtype : 'numberfield', // 设置为数字输入框类型
															anchor : '95%'
														}, {
															fieldLabel : '外发成本合计',
														//	id:'dno',
															id:'total_outcost',
															readOnly:true,
															xtype : 'numberfield', // 设置为数字输入框类型
															anchor : '95%'
														}, {
															fieldLabel : '<span style="color:red">毛利合计</span>',
															id:'total_maoli',
															name:'dno',
															readOnly:true,
															xtype : 'numberfield', // 设置为数字输入框类型
															anchor : '95%'
														}]
											},{
												columnWidth : .2,
												layout : 'form',
												labelWidth : 80, // 标签宽度
												defaultType : 'textfield',
												border : false,
												items : [{
															fieldLabel : '总重量',
															id:'total_weight',
															name:'dno',
															readOnly:true,
															xtype : 'numberfield', // 设置为数字输入框类型
															anchor : '95%'
														},{
															fieldLabel : '代收货款合计',
															id:'total_pay',
															name:'dno',
															readOnly:true,
															xtype : 'numberfield', // 设置为数字输入框类型
															anchor : '95%'
														}, {
															fieldLabel : '签单成本合计',
														//	id:'dno',
															readOnly:true,
															id:'total_qdcost',
															xtype : 'numberfield', // 设置为数字输入框类型
															anchor : '95%'
														},{
															fieldLabel : '<span style="color:red">平均毛利润</span>',
															id:'avg_maoli',
															name:'dno',
															readOnly:true,
															xtype : 'numberfield', // 设置为数字输入框类型
															anchor : '95%'
														}]
											},{
												columnWidth : .2,
												layout : 'form',
												labelWidth : 80, // 标签宽度
												defaultType : 'textfield',
												border : false,
												items : [{
															fieldLabel : '',
														//	id:'dno',
															name:'dno',
															hidden:true,
															xtype : 'numberfield', // 设置为数字输入框类型
															anchor : '95%'
														},{
															fieldLabel : '',
														//	id:'dno',
															name:'dno',
															hidden:true,
															xtype : 'numberfield', // 设置为数字输入框类型
															anchor : '95%'
														}, {
															fieldLabel : '其他成本合计',
														//	id:'dno',
															readOnly:true,
															id:'total_thercost',
															xtype : 'numberfield', // 设置为数字输入框类型
															anchor : '95%'
														},{
											    			xtype:'label',
											    			id:'showMsg',
											    			width:380
											    		}]
											}]
								}]
		});
		
		var panel = new Ext.Panel({
			layout : 'border',
			id:'mpanel',
	//		frame:false,
	//		border:true,
	    //	autoWidth: true,
		    height: Ext.lib.Dom.getViewHeight()-1,
		    width : Ext.lib.Dom.getViewWidth()-1,
		    renderTo: Ext.getBody(),
		    tbar:onebar,
			items : [qForm,grid]
			
		});
	
		function searchLog() {
			//var dNo=Ext.get("dno").dom.value;
			var start='';
			var end ='';
			if(Ext.getCmp('startDate').getValue()!=""){
			   	start = Ext.getCmp('startDate').getValue().format("Y-m-d");
			}
			if(Ext.getCmp('endDate').getValue()!=""){
			   	end = Ext.getCmp('endDate').getValue().format("Y-m-d");
			}

   			Ext.apply(dataStore.baseParams={
				filter_GED_createTime : end,
    		    filter_LED_createTime : start,
    		    filter_EQL_cusId:Ext.getCmp('combocus').getValue(),
    		    filter_EQL_departId:Ext.getCmp('comboType').getValue(),
			    limit : pageSize
			});

			dataStore.reload({
					params : {
						start : 0,
						limit : pageSize
					},callback :function(v){
						
						Ext.getCmp('total_count').setValue(v.length);
					
						Ext.Ajax.request({
							url : sysPath+'/fi/departProfitVoAction!queryCqTotal.action',
							params:{
								filter_GED_createTime : end,
				    		    filter_LED_createTime : start,
				    		    filter_EQL_cusId:Ext.getCmp('combocus').getValue(),
				    		    filter_EQL_departId:Ext.getCmp('comboType').getValue(),
							    limit : pageSize
							},
							success : function(response) { // 回调函数有1个参数
								var decode = Ext.decode(response.responseText);
								Ext.getCmp('total_dno').setValue(decode.resultMap[0].COUNTDNO);
								
								Ext.getCmp('avg_maoli').setValue(decode.resultMap[0].MAORATE);
								Ext.getCmp('total_dfFee').setValue(decode.resultMap[0].DYFEE);
								Ext.getCmp('total_income').setValue(decode.resultMap[0].TOTALINCOMEFEE);
								Ext.getCmp('total_piece').setValue(decode.resultMap[0].PIECE);
								Ext.getCmp('total_qdcost').setValue(decode.resultMap[0].SIGNDANCOSTFEE);
								Ext.getCmp('total_zzcost').setValue(decode.resultMap[0].TRANSITCOSTFEE);
								Ext.getCmp('total_outcost').setValue(decode.resultMap[0].OUTSIDECOSTFEE);
								Ext.getCmp('total_yfFee').setValue(decode.resultMap[0].YFFEE);
								Ext.getCmp('total_thcost').setValue(decode.resultMap[0].DOGOODCOSTFEE);
								Ext.getCmp('total_pay').setValue(decode.resultMap[0].PAYMENTCOLLECTION);
								Ext.getCmp('total_therFee').setValue(decode.resultMap[0].THERADDFEE);
								Ext.getCmp('total_thercost').setValue(decode.resultMap[0].THERCOSTFEE);
								Ext.getCmp('total_maoli').setValue(decode.resultMap[0].GROSSPROFITFEE);
								Ext.getCmp('total_cost').setValue(decode.resultMap[0].TOTALCOSTFEE);
								Ext.getCmp('total_weight').setValue(decode.resultMap[0].CUSWEIGHT);
							},
							failure : function(response) {
								Ext.MessageBox.alert(alertTitle, '汇总数据失败');
							}
						});
					}
			});
		
	}
});
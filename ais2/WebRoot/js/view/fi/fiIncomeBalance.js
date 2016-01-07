	Ext.QuickTips.init();
	var privilege=248;
	var comboxPage=comboSize;
	
	var ralaListUrl="fi/fiIncomeBalanceVoAction!list.action";
	
Ext.onReady(function(){
	Ext.QuickTips.init();
	var rownum=new Ext.grid.RowNumberer({
        width : 25,
		sortable : true	
	});
 
    var fields=[{name:"createTime",mapping:'CREATETIME'},
    			{name:"incomeAmount",mapping:'INCOMEAMOUNT'},
    			{name:"doGoodCost",mapping:'DOGOODCOST'},
    			{name:"signDanCost",mapping:'SIGNDANCOST'},
    			{name:"transitCost",mapping:'TRANSITCOST'},
    			{name:"outSideCost",mapping:'OUTSIDECOST'},
    			{name:"therCost",mapping:'THERCOST'},
    			{name:"totalCost",mapping:'TOTALCOST'},
    			{name:"variableCostRate",mapping:'VARIABLECOSTRATE'},
    			{name:"breakEvenPoint",mapping:'BREAKEVENPOINT'}];
    
    var jsonread= new Ext.data.JsonReader({
         root:'resultMap',
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

   	var tobar  = new Ext.Toolbar({
		items : []});
		
	var recordGrid=new Ext.grid.GridPanel({
	//	renderTo:'recordGrid',
		renderTo:Ext.getBody(),
		id:'userCenter',
		height : Ext.lib.Dom.getViewHeight()-5,
		width : Ext.lib.Dom.getViewWidth()-5,
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
		stripeRows : true,
		tbar:['&nbsp;&nbsp;',
			'<B>日期:</B>',{
	    		xtype : 'datefield',
	    		id : 'startDate',
	    		selectOnFocus:true,
	    		format : 'Y-m-d',
	    		emptyText : "选择开始时间",
	    		allowBlank : false,
	    		width : 85,
	    		value:new Date().add(Date.MONTH, -1),
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
	    		value:new Date(),
	    		selectOnFocus:true,
	    		allowBlank : false,
	    		format : 'Y-m-d',
	    		emptyText : "选择结束时间",
	    		width : 85,
	    		enableKeyEvents:true,
	    		listeners : {
	    		     keyup:function(numberfield, e){
	                     if(e.getKey() == 13 ){
							searchLog();
	                     }
	 				 }
	    		}
    	    },'&nbsp;','-','&nbsp;',{
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
		}],
		columns:[ rownum,sm,
        			//{header: '来源类型', dataIndex: 'sourceData',width:60,sortable : true},
        			{header: '日期', dataIndex: 'createTime',width:80,sortable : true},
        			{header: '提货成本', dataIndex: 'doGoodCost',width:70,sortable : true},
        			{header: '', dataIndex: 'txt',width:5,sortable : true,css : 'background: #CAE3FF;'},
        			{header: '签单成本', dataIndex: 'signDanCost',width:70,sortable : true},
        			{header: '中转成本', dataIndex: 'transitCost',width:70,sortable : true},
        			{header: '外发成本', dataIndex: 'outSideCost',width:70,sortable : true},
        			{header: '其他成本', dataIndex: 'therCost',width:70,sortable : true},
        			{header: '小计', dataIndex: 'totalCost',width:70,sortable : true},
        			{header: '', dataIndex: 'txt',width:5,sortable : true,css : 'background: #CAE3FF;'},
        			{header: '进港配送收入', dataIndex: 'incomeAmount',width:100,sortable : true},
        			{header: '', dataIndex: 'txt',width:5,sortable : true,css : 'background: #CAE3FF;'},
        			{header: '变动成本率', dataIndex: 'variableCostRate',width:100,sortable : true},
        			{header: '盈亏平衡点', dataIndex: 'breakEvenPoint',width:100,sortable : true}],
			store : dataStore,
			listeners: {
              render: function(){
                 // tobar.render(this.tbar);
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

	function searchLog(){
		if(!Ext.getCmp('startDate').isValid()||!Ext.getCmp('endDate').isValid()){
			Ext.Msg.alert(alertTitle,"日期格式不符合要求，无法查询", function() {
				if(!Ext.getCmp('startDate').isValid()){
					Ext.getCmp('startDate').focus();
					return;
				}
				if(!Ext.getCmp('endDate').isValid()){
					Ext.getCmp('endDate').focus();
					return;
				}
			});
		}

		var start='';
    	var end ='';
    	if(Ext.getCmp('startDate').getValue()!=""){
    		start = Ext.getCmp('startDate').getValue().format("Y-m-d");
    	}
    	if(Ext.getCmp('endDate').getValue()!=""){
    		end = Ext.getCmp('endDate').getValue().format("Y-m-d");
    	}
		if(start==''&&end==''){
			end=new date().format("Y-m-d");
		}

		Ext.apply(dataStore.baseParams, {
			"fiIncomeBalanceVo.startTime": start,
			limit : pageSize,
			"fiIncomeBalanceVo.endTime" : end
   		});

		dataStore.reload({
				params : {
					start : 0,
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
		

});



/**
 * 打印按钮事件
 */
function printinfo() {
	Ext.Msg.alert("提示", "正在开发中...");
}
















	

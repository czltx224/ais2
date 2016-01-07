	Ext.QuickTips.init();
	var privilege=156;
	var comboxPage=comboSize;
	var saveUrl="sys/loadingbrigadeAction!save.action";
	
	var ralaListUrl="fi/fiIncomeVoAction!ralaList.action";
	
	/*
	 * 收入统计分析
	 * */
	
Ext.onReady(function(){
	Ext.QuickTips.init();
	var rownum=new Ext.grid.RowNumberer({
        width : 25,
		sortable : true	
	});
 
    var fields=[{name:"id",mapping:'id'},'dno','sourceData','therNo','customerName','departName','cashStatus',
 	 'inDepartId','serviceDepartName','serviceDepartId','departId','totalAmount','inDepart','departId',
    'customerId','piece','cqWeight','createTime','accouting','certiStatus','certiNo','dfzcAmount','yfzcAmount',
    'flightMainNo','therAmount','yfzzAmount','dfzzAmount','yfdsAmount','dfdsAmount'];
    
    var jsonread= new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'},
                    fields);
                              
	var sm = new Ext.grid.CheckboxSelectionModel();

	//客服部门	
	var serviceDepartStore = new Ext.data.Store({ 
            storeId:"serviceDepartStore",                        
            proxy: new Ext.data.HttpProxy({url:sysPath+"/sys/departAction!findDepart.action",method:'post'}),
            baseParams:{
                	privilege:53
            },
            reader: new Ext.data.JsonReader({
            root: 'resultMap', totalProperty:'totalCount'
           }, [  
                 {name:'departName', mapping:'DEPARTNAME'},    
                 {name:'departId', mapping: 'DEPARTNO'}
              ]),
            sortInfo:{field:'departId',direction:'ASC'}
     });
	
	var dataStore = new Ext.data.Store({
                proxy: new Ext.data.HttpProxy({url:sysPath+"/"+ralaListUrl}),
                baseParams:{
                	limit:pageSize,
                	privilege:privilege
                },
                reader:jsonread
    });
		//提货类型
	var dataStatus=new Ext.data.SimpleStore({
			 auteLoad:true, //此处设置为自动加载
  			 data:[['1','创建日期'],['2','会计日期']],
   			 fields:["id","name"]
	});
 
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

    var summary = new Ext.ux.grid.GridSummary();
   	
   	var tobar  = new Ext.Toolbar({
		items : ['&nbsp;&nbsp;',
			{
				xtype : 'combo',
				id:'doGoods',
				triggerAction : 'all',
				store : dataStatus,
				editable:false,
				forceSelection : true,
				mode : "local",//获取本地的值
				displayField : 'name',//显示值，与fields对应
				valueField : 'name',//value值，与fields对应
				name : 'dataStatus',
				width : 80,
				enableKeyEvents:true,
				listeners : {
	 				render:function(com){
	          		 	com.setValue('创建日期');
	          		 }
	 			}
	    	},'<B>:</B>',{
	    		xtype : 'datefield',
	    		id : 'startDate',
	    		selectOnFocus:true,
	    		format : 'Y-m-d',
	    		emptyText : "选择开始时间",
	    		width : 80,
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
	    		value:new Date(),
	    		selectOnFocus:true,
	    		format : 'Y-m-d',
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
    	    },'-','&nbsp;&nbsp;','收入部门:', {
				xtype : 'combo',
				id:'comboType',
				typeAhead:true,
				hiddenId : 'dictionaryName',
    			hiddenName : 'dictionaryName',
				triggerAction : 'all',
				store : bussStore,
				width:90,
				selectOnFocus:true,
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
		    },'-','&nbsp;','客服部门:', {
				xtype : 'combo',
				id:'comboTypeService',
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
				pageSize:20,
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
		    },'-','&nbsp;','配送单号:', {
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
			},'-','&nbsp;','凭证单号:', {
				xtype : 'textfield',
				labelAlign : 'left',
				name : 'certiNo',
				id:'certiNo',
				selectOnFocus:true,
				width:60,
				maxLength:50,
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
		plugins : [summary],
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
		}],
		columns:[ rownum,sm,
        			//{header: '来源类型', dataIndex: 'sourceData',width:60,sortable : true},
        		    {header: 'id', dataIndex: 'id',	hidden : true,sortable : true},
        			{header: '主单号', dataIndex: 'flightMainNo',width:60,sortable : true},
        			{header: '配送单号', dataIndex: 'dno',width:90,sortable : true},
        			{header: '其他单号', dataIndex: 'therNo',width:60,sortable : true,renderer:function(v){
			        					if( v=='0'){
			        						return '';
			        					}else{
			        						return v;
			        					}}},
			        					
			        {header: '数据来源', dataIndex: 'sourceData',width:60,sortable : true},
			        {header: '客商名称', dataIndex: 'customerName',width:100,sortable : true},
        			
        			{header: '件数', dataIndex: 'piece',width:40,sortable : true},
        			{header: '重量', dataIndex: 'cqWeight',width:40,sortable : true},
        			
        			{header: '预付增值费', dataIndex: 'yfzzAmount',width:70,sortable : true},
        			{header: '到付增值费', dataIndex: 'dfzzAmount',width:70,sortable : true},
        			{header: '预付专车费', dataIndex: 'yfzcAmount',width:70,sortable : true},
        			{header: '到付专车费', dataIndex: 'dfzcAmount',width:70,sortable : true},
        			
      				{header: '预付提送费', dataIndex: 'yfdsAmount',width:70,sortable : true},
        			{header: '到付提送费', dataIndex: 'dfdsAmount',width:70,sortable : true},
			        {header: '其他收入', dataIndex: 'therAmount',width:70,sortable : true},
			        {header: '收入合计', dataIndex: 'totalAmount',width:70,sortable : true},
			        {header: '收银状态', dataIndex: 'cashStatus',width:70,sortable : true ,renderer:function(v){
			        					if( v=='1'){
			        						return '已收银';
			        					}else if(v=='0'){
			        						return '未收银';
			        					}else{
			        						return '无状态';
			        					}
			        				}},
			        {header: '收入部门', dataIndex: 'departName',width:85,sortable : true},
			        {header: '录单部门', dataIndex: 'inDepart',width:85,sortable : true},
			        {header: '客服部门ID',hidden:true,dataIndex: 'serviceDepartId',width:85,sortable : true},
			        {header: '客服部门', dataIndex: 'serviceDepartName',width:85,sortable : true},
			//		{header: '凭证号', dataIndex: 'certiNo',width:60,sortable : true},
        	/*		{header: '凭证状态', dataIndex: 'certiStatus',width:60,renderer:function(v){
			        					if( v=='0'){
			        						return '撤销';
			        					}else if(v=='1'){
			        						return '未成生';
			        					}else if(v=='2'){
			        						return '传输中';
			        					}else if(v=='3'){
			        						return '已生成';
			        					}else{
			        						return '无凭证状态';
			        					}
			        				},sortable : true},
        			{header: '会计时间', dataIndex: 'accouting',width:120,sortable : true},*/
        			{header: '客商ID', dataIndex: 'customerId',hidden:true,sortable : true},
        			{header: '录单时间', dataIndex: 'createTime',width:120,sortable : true}],
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
			var start='';
	    	var end ='';
	    	if(Ext.getCmp('startDate').getValue()!=""){
	    		start = Ext.getCmp('startDate').getValue().format("Y-m-d");
	    	}
	    	var dId=Ext.getCmp('comboTypeService').getValue();
	    	if(Ext.getCmp('endDate').getValue()!=""){
	    		end = Ext.getCmp('endDate').getValue().format("Y-m-d");
	    	}
			var departId = Ext.getCmp('comboType').getValue();
			var dno = Ext.getCmp('dno').getValue();
			var certiNo = Ext.getCmp('certiNo').getValue();

			if(Ext.getCmp('doGoods').getValue()=='会计日期'){
				Ext.apply(dataStore.baseParams, {
					filter_GED_accounting : start,
					filter_LED_accounting : end,
					filter_LIKES_serviceDepartId:dId,
					filter_EQL_departId:departId,
					filter_EQS_certiNo:certiNo,
					filter_EQL_dno:dno
		   		});
			}else{
				Ext.apply(dataStore.baseParams, {
					filter_GED_createTime : start,
					filter_LED_createTime : end,
					filter_LIKES_serviceDepartId:dId,
					filter_EQL_departId:departId,
					filter_EQS_certiNo:certiNo,
					filter_EQL_dno:dno
		   		});
			}

			dataStore.reload({
					params : {
						start : 0,
						privilege:privilege,
						limit : pageSize
					},callback :function(){
						Ext.Ajax.request({
							url : sysPath+'/fi/fiIncomeVoAction!getTotalFiInIncome.action',
							params:{
								start : 0,
								selectString:Ext.getCmp('doGoods').getValue(),
								startTime:start,
								endTime:end,
								serviceCode:dId,
								dno:dno,
								departId:departId,
								certiNo:certiNo,
								limit : pageSize,
								privilege:privilege
							},
							success : function(response) { // 回调函数有1个参数
								summary.toggleSummary(true);
								summary.setSumValue(Ext.decode(response.responseText));
							},
							failure : function(response) {
								Ext.MessageBox.alert(alertTitle, '汇总数据失败');
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
		

});



/**
 * 打印按钮事件
 */
function printinfo() {
	Ext.Msg.alert("提示", "正在开发中...");
}
















	

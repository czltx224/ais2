//增值服务费明细查询JS
	Ext.QuickTips.init();
	var privilege=204;
	var comboxPage=20;
	var saveUrl="sys/basDictionaryAction!save.action";
	
	var ralaListUrl="fi/fiAppreciationServicesAction!ralaList.action";
	
Ext.onReady(function(){
	Ext.QuickTips.init();
	var rownum=new Ext.grid.RowNumberer({
		header:'序号',
        width : 35,
		sortable : true	
	});
 
    var fields=[{name:"id",mapping:'id'},
	'feeName','status',
	'feeCount',
	'createName',
	'createTime',
	'updateName',
	'updateTime',
	'ts','payType',
	'dno',
  	'consignee',
	'flightMainNo',
	'subNo',
	'customerId',
	'customerName'];
	
	var jsonread= new Ext.data.JsonReader(
    	         {  root:'result',
                    totalProperty:'totalCount'
                  },fields);
                  
	var sm = new Ext.grid.CheckboxSelectionModel();

	var dataStore = new Ext.data.Store({
                proxy: new Ext.data.HttpProxy({url:sysPath+"/"+ralaListUrl}),
                baseParams:{
                	limit: pageSize,
                	privilege:privilege
                },
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
		//sm:sm,
		stripeRows : true,
		tbar:['&nbsp;&nbsp;','<B>配送单号</B>:',{
				xtype : 'numberfield',
				labelAlign : 'right',
				name : 'dno',
				width:50,
				selectOnFocus:true,
				decimalPrecision:0,
				allowNegative:false,
				id:'dno',
				maxLength:12,
				allowBlank : true,
				enableKeyEvents:true,
		            listeners : {
		 				keyup:function(textField, e){
		                     if(e.getKey() == 13){
		                     	searchLog();
		                     }
		 				}
		 			}
			},'&nbsp;','<B>主单号</B>:',{
				xtype:'textfield',
	 	        id : 'flightMainNo',
		        name : 'flightMainNo',
		        maxLength:10,
		       	selectOnFocus:true,
		        width:50,
	            enableKeyEvents:true,
	            listeners : {
	 				keyup:function(textField, e){
	                     if(e.getKey() == 13){
	                     	searchLog();
	                     }
	 				}
	 			}
	        },'&nbsp;','<B>收货人</B>:',{
				xtype:'textfield',
	 	        id : 'consignee',
		        name : 'consignee',
		        maxLength:10,
		        width:50,
		        selectOnFocus:true,
	            enableKeyEvents:true,
	            listeners : {
	 				keyup:function(textField, e){
	                     if(e.getKey() == 13){
	                     	searchLog();
	                     }
	 				}
	 			}
	        },'&nbsp;','<B>发货代理</B>:',{
				xtype:'textfield',
	 	        id : 'customerName',
		        name : 'customerName',
		        maxLength:10,
		        width:50,
		        selectOnFocus:true,
	            enableKeyEvents:true,
	            listeners : {
	 				keyup:function(textField, e){
	                     if(e.getKey() == 13){
	                     	searchLog();
	                     }
	 				}
	 			}
	        },'&nbsp;','<B>创建人</B>:',{
				xtype:'textfield',
	 	        id : 'createName',
		        name : 'createName',
		        maxLength:10,
		        width:50,
	            enableKeyEvents:true,
	            listeners : {
	 				keyup:function(textField, e){
	                     if(e.getKey() == 13){
	                     	searchLog();
	                     }
	 				}
	 			}
	        },'-',{
			     text : '<b>查  询</b>',
			     id : 'btn',
			     iconCls : 'btnSearch',
				 handler : searchLog
			}],
			columns:[rownum,sm,
			        {header: '配送单号', dataIndex: 'dno',sortable : true,width:60},
        		    {header: 'ID', dataIndex: 'id',width:60,hidden:true,sortable : true},
        			{header: '费用名称', dataIndex: 'feeName',width:60,sortable : true},
    				{header: '金额', dataIndex: 'feeCount',sortable : true,width:40},  
    				{header: '付款方式', dataIndex: 'payType',sortable : true,width:60},  
			        {header: '更改状态', dataIndex: 'status',width:60,sortable : true,renderer:function(v){
			        					if(v=='1') return '已更改';
			        					if(v=='0') return '未更改';
			        					}},
			        {header: '收货人', dataIndex: 'consignee',width:60,sortable : true},
			        {header: '主单号', dataIndex: 'flightMainNo',width:60,sortable : true},
			        {header: '分单号', dataIndex: 'subNo',width:60,sortable : true},
			        {header: '客商ID', dataIndex: 'customerId',hidden:true,sortable : true},
			        {header: '发货代理', dataIndex: 'customerName',width:80,sortable : true},
			        
			        {header: '创建人', dataIndex: 'createName',width:60,sortable : true},
			        {header: '创建时间', dataIndex: 'createTime',width:80,sortable : true},
			        {header: '修改人', dataIndex: 'updateName',width:60,hidden:true,sortable : true},
			        {header: '修改时间', dataIndex: 'updateTime',width:80,hidden:true,sortable : true}
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
		var dnoSearch= Ext.getCmp("dno").getValue();
		if(dnoSearch==""){
			dataStore.baseParams = {
				filter_EQL_dno :dnoSearch,
			 	filter_LIKES_createName : Ext.getCmp("createName").getValue(),
			 	filter_LIKES_flightMainNo : Ext.getCmp("flightMainNo").getValue(),
			 	filter_LIKES_consignee : Ext.getCmp("consignee").getValue(),
			 	filter_LIKES_customerName : Ext.getCmp("customerName").getValue(),
			 	privilege:privilege,
			 	limit : pageSize
			}
		}else{
			 dataStore.baseParams = {
				filter_EQL_dno :dnoSearch,
			 	privilege:privilege,
			 	limit : pageSize
			 }
		}
		

		 dataStore.reload({
					params : {
						start : 0,
						limit : pageSize
					}
		 });
		 
	} 

});


 








	

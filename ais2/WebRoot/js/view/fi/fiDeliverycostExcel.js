	Ext.QuickTips.init();
	var privilege=137;
	var comboxPage=15;
	var customerGridSearchUrl="sys/customerAction!list.action";
	var ralaListUrl="fi/fiDeliverycostExcelAction!list.action";
	batchNo="";
	pageSize=100;
Ext.onReady(function(){
	Ext.QuickTips.init();
	var rownum=new Ext.grid.RowNumberer({
		sortable : true	
	});
 
 	var rownum2=new Ext.grid.RowNumberer({
		sortable : true	
	});
	
	var fields2=[{name:"id",mapping:'id'},'excelBanFee','faxMainNo',
	'faxWeight',
	'faxAmount',
	'batchNo',
	'status',
	'wrongInfo','excelCompany','excelNo','excelWeight','excelAmount'];
	
	var fields=[
		'id',
		'faxMainNo',
		'matStatus',
		'flightMainNo',
		'customerId',
		'customerName',
		'faxPiece',
		'flightPiece',
		'faxWeight',
		'flightWeight',
		'flightAmount',
		'boardAmount',
		'amount',
		'price',
		'isLowestStatus',
		'remark',
		'diffWeight',
		'diffAmount',
		'startcity',
		'status',
		'createName',
		'createTime',
		'createDeptid',
		'createDept',
		'reviewDept',
		'reviewUser',
		'reviewDate',
		'reviewRemark',
		'ts',
		'feeType'];
	
	var jsonread= new Ext.data.JsonReader(
    	         {  root:'result',
                    totalProperty:'totalCount'
                  },fields);
                  
    var jsonread2= new Ext.data.JsonReader(
    	         {  root:'result',
                    totalProperty:'totalCount'
                  },fields2);
                  
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
	
	var dataStore = new Ext.data.Store({
          proxy: new Ext.data.HttpProxy({url:sysPath+"/fi/fiDeliverycostAction!ralaList.action"}),
          baseParams:{
          	limit: pageSize,
          	filter_EQL_departId:bussDepart,
          	privilege:137
          },
          sortInfo : {field: "id", direction: "DESC"},
          reader:jsonread
    });
    
    var data2Store = new Ext.data.Store({
                proxy: new Ext.data.HttpProxy({url:sysPath+"/"+ralaListUrl}),
                baseParams:{
                	limit: pageSize,
                	filter_EQL_batchNo:batchNo
                },
                reader:jsonread2
    });
    
    	// 客商列表
	searchCusNameStore = new Ext.data.Store({
		storeId : "searchCusNameStore",
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
	
	var onebar = new Ext.Toolbar({
		id:'onebar',
		items:[
		'&nbsp;',{
				text:'<B>下载对账单模版(Excel)</B>',
				iconCls : 'outExcel',
				id:'addbtn2',
				handler : function() {
						outExcel();
				}
			},'-','&nbsp;',{
				text:'<B>导入对账单(Excel)</B>',
				iconCls : 'table',
				id:'addbtn3',
				handler : function() {
						doExcel();
				}
			},'-','&nbsp;',{
				text:'<B>账单自动对账</B>',
				iconCls : 'fieldEdit',
				id:'addbtn5',
				handler : function() {
						compareExcel();
				}
			},'-','&nbsp;',{
				text:'<B>修正黄单信息</B>',
				iconCls : 'groupEdit',
				id:'addbtn',
				handler : function() {
						updateFiExcel();
				}
			},'-','&nbsp;',{
				text:'<B>完全修正黄单信息</B>',
				iconCls : 'userEdit',
				id:'addbtn4',
				handler : function() {
						updateAllFiExcel();
				}
			},'-','&nbsp;',{
				text:'<B>审核账单</B>',
				iconCls : 'groupPass',
				id:'addbtn6',
				handler : function() {
					auditCost();
				}
			}
		]
	});
	
	var twobar = new Ext.Toolbar({
		    	items : ['&nbsp;','创建日期:',
						 {
							xtype : 'datefield',
			    			id : 'startDate',
				    		format : 'Y-m-d',
				    		emptyText : "选择开始时间",
				    		anchor : '95%',
				    		width : 80,
				    		enableKeyEvents:true,
				    		listeners : {
				    			'select' : function() {
				    			   var start = Ext.getCmp('startDate').getValue()
				    			      .format("Y-m-d");
				    			   Ext.getCmp('endDate').setMinValue(start);
				    		     },
				    		     keyup:function(textField, e){
				                     if(e.getKey() == 13){
				                     	searchLog();
				                     }
				 				 }
			    		    }
						},'&nbsp;','至','&nbsp;',{
							xtype : 'datefield',
				    		id : 'endDate',
				    		format : 'Y-m-d',
				    		emptyText : "选择结束时间",
				    		width : 80,
				    		anchor : '95%',
				    		enableKeyEvents:true,
				    		listeners : {
				    		     keyup:function(textField, e){
				                     if(e.getKey() == 13){
				                     	searchLog();
				                     }
				 				 }
			    		    }
						}]
		});
	
	var recordGrid=new Ext.grid.GridPanel({
	//	renderTo:'recordGrid',
	//	renderTo:Ext.getBody(),
		id:'userCenter',
		height : Ext.lib.Dom.getViewHeight()-1,
		width : Ext.lib.Dom.getViewWidth()*0.4-5,
		autoScroll : true,
		autoExpandColumn : 1,
		loadMask : true,
		frame : true,
		stripeRows : true,
	 	viewConfig : {
				scrollOffset: 0,
				autoScroll:true
		},
		tbar:[twobar],
		columns:[rownum2,
	//	columns:[ rownum,sm,
			{header: '编号', dataIndex: 'id',width:55,sortable : true},
	        {header: '匹配状态', dataIndex: 'matStatus',width:60,sortable : true,
		       		 renderer:function(v){
		        		if(v=='1'){ 
		        			return '自动匹配';
		        		}else if(v=='2'){
		        			return '手工匹配';
		        		}else if(v=='0'){
		        			return '未匹配';
		        		}else{
		        			return '';
		        		}
		        	}
		     },
		    {header: '审核状态', dataIndex: 'status',width:70,sortable : true,
	        		 renderer:function(v){
		        		if(v=='1'){ 
		        			return '已审核';
		        		}else if(v=='0'){
		        			return '未审核';
		        		}
		        	}},
	        {header: '黄单号', dataIndex: 'flightMainNo',width:70,sortable : true},
	        {header: '客商ID', dataIndex: 'customerId',width:70,hidden:true,sortable : true},
	        {header: '提货点', dataIndex: 'customerName',width:80,sortable : true},　
			{header: '黄单件数', dataIndex: 'flightPiece',width:70,sortable : true},
			{header: '黄单重量', dataIndex: 'flightWeight',width:70,sortable : true},
			{header: '黄单金额', dataIndex: 'flightAmount',width:70,sortable : true},
			{header: '板费', dataIndex: 'boardAmount',width:70,sortable : true},
	        {header: '总金额', dataIndex: 'amount',width:70,sortable : true},  
	        
	        {header: '单价', dataIndex: 'price',width:70,sortable : true},
	        {header: '结算方式', dataIndex: 'feeType',width:70,sortable : true},
	        {header: '是否最低一票', dataIndex: 'isLowestStatus',width:70,sortable : true,
	        		 renderer:function(v){
		        		if(v=='1'){ 
		        			return '是';
		        		}else if(v=='0'){
		        			return '否';
		        		}else{
		        			return '';
		        		}
		        	}},
	       	{header: '始发站', dataIndex: 'startcity',width:80,sortable : true},
	       	{header: '创建人', dataIndex: 'createName',width:80,hidden:true,sortable : true},
	        {header: '创建时间', dataIndex: 'createTime',width:80,hidden:true,sortable : true},
	        {header: '创建部门ID', dataIndex: 'createDeptid',width:80,hidden:true,sortable : true},
	        {header: '创建部门', dataIndex: 'createDept',width:80,hidden:true,sortable : true},
	        {header: '备注', dataIndex: 'remark',width:80,sortable : true}
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
	
	function searchLog(){
		var start='';
    	var end ='';
    	if(Ext.getCmp('startDate').getValue()!=""){
    		var start = Ext.getCmp('startDate').getValue().format("Y-m-d");
    	}
    	if(Ext.getCmp('endDate').getValue()!=""){
    		var end = Ext.getCmp('endDate').getValue().format("Y-m-d");
    	}
		
		Ext.apply(dataStore.baseParams={
			filter_GED_createTime : start,
           	filter_LTD_createTime : end,
           	filter_GEL_matStatus:1,
           	filter_LEL_matStatus:2,
			filter_EQL_status:0,
       		filter_EQL_departId:bussDepart,
       		privilege:137
   		});


		dataStore.reload({
				params : {
					start : 0,
					limit : pageSize
				}
		});
	
	
	
	} 
	
	var recordGrid2=new Ext.grid.GridPanel({
	//	renderTo:'recordGrid',
	//	renderTo:Ext.getBody(),
		id:'userCenter2',
		height : Ext.lib.Dom.getViewHeight()-1,
		width : Ext.lib.Dom.getViewWidth()*0.6-3,
		
		autoScroll : true,
		autoExpandColumn : 1,
		loadMask : true,
		frame : true,
		stripeRows : true,
	 	viewConfig : {
				scrollOffset: 0,
				autoScroll:true
		},
	
		stripeRows : true,
		tbar:[],
		columns:[rownum,
       		    {header: 'ID', dataIndex: 'id',hidden:true,width:50,sortable : true},
       			{header: '提货点', dataIndex: 'excelCompany',width:50,sortable : true},
       		    {header: '货单号', dataIndex: 'excelNo',width:70,sortable : true},
       			{header: '对账单重量', dataIndex: 'excelWeight',width:60,sortable : true},
   				{header: '对账单金额',width:60, dataIndex: 'excelAmount',sortable : true},
   				{header: '黄单号', dataIndex: 'faxMainNo',width:70,sortable : true},
       			{header: '黄单重量', dataIndex: 'faxWeight',width:60,sortable : true},
   				{header: '黄单金额',width:60, dataIndex: 'faxAmount',sortable : true},
   				{header: '板费',width:40, dataIndex: 'excelBanFee',sortable : true},
   				{header: '批次号',width:70, dataIndex: 'batchNo',sortable : true},
   				{header: '对账状态',width:70, dataIndex: 'status',sortable : true,renderer:function(v){
										if(v=='0'){
											return '对账失败';
										}else if(v=='1'){
											return '对账成功';
										}else{
											return v;
										}}},
   				{header: '提示信息',width:200, dataIndex: 'wrongInfo',sortable : true}
		    ],
   			    
		store : data2Store,
		bbar : new Ext.PagingToolbar({
				pageSize : pageSize, 
				store : data2Store,
				displayInfo : true,
				displayMsg : '显示第 {0} 条到 {1} 条记录，共 {2} 条',
				emptyMsg : "没有记录信息显示"
		})
	});
	
	var form = new Ext.form.FormPanel({
	 	id:'myForm',
		labelAlign : 'left',
		frame : true,
		renderTo:Ext.getBody(),
		bodyStyle : 'padding:0px 0px 0px',
	    height : Ext.lib.Dom.getViewHeight()-2,
		width : Ext.lib.Dom.getViewWidth()-1,
		labelAlign : "right",
		tbar:onebar,
		listeners: {
               render: function(){
                 
               }
           },
        items : [{
					layout : 'column',
					items : [{
								columnWidth : .4,
								layout : 'form',
								items : [recordGrid]

							},{
								columnWidth : .6,
								layout : 'form',
								buttonAlign:'center',
								items : [recordGrid2]
							}]
				}]
	});
     
     
	form.render();
	recordGrid.setHeight((Ext.lib.Dom.getViewHeight()-Ext.getCmp('onebar').getHeight())-15);
	recordGrid2.setHeight((Ext.lib.Dom.getViewHeight()-Ext.getCmp('onebar').getHeight())-15);
	
	function outExcel(){
		window.location.href=deliverycostExcelUrl;
	}

	function compareExcel(){
		if(dataStore.getCount()==0){
			Ext.Msg.alert(alertTitle,"没有航空公司账单信息，请先导入账单信息", function() {
				doExcel();
			});
		}else{
			Ext.Msg.confirm(alertTitle,'账单自动对账时间较长，请耐心等候。您确定需要自动对账吗?',function(btnYes) {
				if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
					form1.getForm().doAction('submit', {
						url : sysPath+ "/fi/fiDeliverycostExcelAction!compareStatus.action",
						method : 'post',
						params : {
 							batchNo:batchNo	
 						},
						waitMsg : '正在处理数据...',
						success : function(form1, action) {Ext.Msg.alert(alertTitle,action.result.msg,
								function() {
									data2Store.proxy = new Ext.data.HttpProxy({
											method : 'POST',
											url:sysPath+"/"+ralaListUrl
									});
									
									Ext.apply(data2Store.baseParams={
										limit : pageSize,
						 	 			filter_EQL_batchNo:batchNo
									});
									
									data2Store.reload({
										baseParams : {
											start : 0,
											limit : pageSize
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
	}
	
	function updateFiExcel(){
		if(data2Store.getCount()==0){
			Ext.Msg.alert(alertTitle,"请先点击账单自动对账，然后再进行修正", function() {
				
			});
		}else{
			Ext.Msg.confirm(alertTitle,'修正黄单信息时间较长，请耐心等候。您确定需要修正黄单信息吗?',function(btnYes) {
				if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
					form1.getForm().doAction('submit', {
						url : sysPath+ "/fi/fiDeliverycostExcelAction!updateFax.action",
						method : 'post',
						params : {
 							batchNo:batchNo	
 						},
						waitMsg : '正在处理数据...',
						success : function(form1, action) {Ext.Msg.alert(alertTitle,action.result.msg,
								function() {
									data2Store.proxy = new Ext.data.HttpProxy({
											method : 'POST',
											url:sysPath+"/"+ralaListUrl
									});
									
									Ext.apply(data2Store.baseParams={
										limit : pageSize,
						 	 			filter_EQL_batchNo:batchNo
									});
									
									data2Store.reload({
										baseParams : {
											start : 0,
											limit : pageSize
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
	}

	function updateAllFiExcel(){
		if(data2Store.getCount()==0){
			Ext.Msg.alert(alertTitle,"请先点击账单自动对账，然后再进行黄单信息修正", function() {
				
			});
		}else{
			Ext.Msg.confirm(alertTitle,'修正黄单信息(全部)时间较长，请耐心等候。您确定需要修正吗?',function(btnYes) {
				if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
					form1.getForm().doAction('submit', {
						url : sysPath+ "/fi/fiDeliverycostExcelAction!updateAllFax.action",
						method : 'post',
						params : {
 							batchNo:batchNo	
 						},
						waitMsg : '正在处理数据...',
						success : function(form1, action) {Ext.Msg.alert(alertTitle,action.result.msg,
								function() {
									data2Store.proxy = new Ext.data.HttpProxy({
											method : 'POST',
											url:sysPath+"/"+ralaListUrl
									});
									
									Ext.apply(data2Store.baseParams={
										limit : pageSize,
						 	 			filter_EQL_batchNo:batchNo
									});
									
									data2Store.reload({
										baseParams : {
											start : 0,
											limit : pageSize
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
	}

	function auditCost(){
		if(data2Store.getCount()==0){
			Ext.Msg.alert(alertTitle,"请先完成对账，再进行对账审核", function(){});
			return;
		}else{
			for(var i=0;i<data2Store.getCount();i++){
				if(data2Store.getAt(i).get('status')==0){
					Ext.Msg.alert(alertTitle,"存在未匹配的数据，不能进行对账审核", function(){});
					return;
				}
			}		
		}
	
					var form3 = new Ext.form.FormPanel({
								labelAlign : 'right',
								frame : true,
								bodyStyle : 'padding:3px 3px 0',
							    width : 390,
								reader : new Ext.data.JsonReader({
			                                   root: 'result', totalProperty: 'totalCount'
			                              }, fields),
					            labelAlign : "right",
						        items : [{id:"id",
										  name : "id",
										  xtype : "hidden"
										 },{
											xtype : 'combo',
											id:'comboTypeDepart',
											triggerAction : 'all',
											fieldLabel: '提货点名称', 
											store : searchCusNameStore,
											width : 100,
											queryParam : 'filter_LIKES_cusName',
											listWidth:245,
											minChars : 1,
											allowBlank : true,
											emptyText : "请选择提货处名称",
											forceSelection : true,
											editable : true,
											pageSize:comboxPage,
											displayField : 'cusName',//显示值，与fields对应
											valueField : 'id',//value值，与fields对应
											name : 'cusName',
											anchor : '95%'
									    },{ xtype : 'textfield',
					        		       fieldLabel: '主单ID码', 
						        		   name: 'code',
						        		   maxLength : 20,
						        		   allowBlank : true,
						        		   anchor : '95%',
								           blankText : '必须填写条目名称'
								       },{ xtype : 'textfield',
					        		       fieldLabel: '批次号', 
						        		   name: 'batchNo',
						        		   readOnly:true,
						        		   value:batchNo,
						        		   allowBlank : false,
						        		   anchor : '95%'
							           },{ xtype : 'numberfield',
							               fieldLabel: '总金额', 
							               name: 'totalMoney',
							               maxLength : 10,
							               anchor : '95%',
							               listeners:{
							               		render:function(v){
					               					Ext.Ajax.request({
														url : sysPath+ "/fi/fiDeliverycostExcelAction!totalAmount.action",
														params:{
															batchNo:batchNo,
															limit : pageSize
														},
														success : function(response) { // 回调函数有1个参数  departId
															v.setValue(Ext.decode(response.responseText).excelAmount);
													         		  
														},
														failure : function(response) {
															Ext.Msg.alert(alertTitle, '金额统计失败！请重新打开窗口');
															form3.destroy();
														}
													});	
							               		}
							               
							               }
							           }]
						});
		
		var win = new Ext.Window({
			title : "审核对账",
			width : 390,
			closeAction : 'hide',
			plain : true,
		    resizable : false,	
			modal : true,
			items : form3,
			buttonAlign : "center",	buttons : [{
				text : "保存",
				iconCls : 'groupSave',
				handler : function() {
					if (form3.getForm().isValid()) {
						this.disabled = true;//只能点击一次
						form3.getForm().submit({
							url : sysPath+ '/fi/fiDeliverycostExcelAction!auditFi.action',
							params:{
								privilege:privilege,
								limit : pageSize,
								cusId:Ext.getCmp('comboTypeDepart').getValue()
							},
							waitMsg : '正在保存数据...',
							success : function(form, action) {
										win.hide(), 
										Ext.Msg.alert(alertTitle,action.result.msg, function() {
												searchLog();
												data2Store.removeAll();
										});
							},
							failure : function(form, action) {
								if (action.faliureType == Ext.form.Action.SERVER_INVALID) {
									Ext.Msg.alert(alertTitle, "服务器连接异常，请联系管理员！");
								} else {
									if (action.result.msg) {
										win.hide();
										Ext.Msg.alert(alertTitle,
												action.result.msg, function() {
											
												});
	
									}
								}
							}
						});
					}
				}
			},{
						text : "取消",
						handler : function() {
						   win.close();
						  
						}
					}]
								
		
		});
	
		win.on('hide', function() {
					form3.destroy();
					
				});
		
		win.show();
		
	}

	function doExcel(){
			
			var form2 = new Ext.form.FormPanel({
								labelAlign : 'left',
								frame : true,
								fileUpload : true,
							//	url:sysPath+'/user/userAction!ralaList.action?privilege=17',
							
								bodyStyle : 'padding:5px 5px 5px',
							    width : 480,
							    labelWidth :100,
								reader :jsonread,
					            labelAlign : "right",
						        items : [
									{xtype : 'textfield',
									 inputType : 'file',
									 fieldLabel : '导入提货对账单(Excel)',
									 name : 'upLoadExcel',
									 id:'upLoadExcel',
									 anchor : '100%'
									}]
										});
			
		var win = new Ext.Window({
			title : 'Excel导入',
			width : 500,
			closeAction : 'hide',
			plain : true,
		    resizable : false,	
			modal : true,
			items : form2,
			buttonAlign : "center",	buttons : [{
				text : "保存",
				id:'savebtn',
				iconCls : 'groupSave',
				handler : function() {
					if (form2.getForm().isValid()) {
						this.disabled = true;//只能点击一次
						form2.getForm().submit({
							url : sysPath+ '/fi/fiDeliverycostAction!saveExcel.action',
							method : 'post',
							params:{
								privilege:privilege
							},
							waitMsg : '正在处理数据...',
							success : function(form, action) {
								win.hide(), 
								Ext.Msg.alert(alertTitle,action.result.msg, function(){
									batchNo=action.result.batchNo;
									
									searchLog();
									
									data2Store.proxy = new Ext.data.HttpProxy({
											method : 'POST',
											url:sysPath+"/"+ralaListUrl
									});
									
									Ext.apply(data2Store.baseParams={
										limit : pageSize,
						 	 			filter_EQL_batchNo:batchNo
									});
									
									data2Store.reload({
										baseParams : {
											start : 0,
											limit : pageSize
										},callback:function(){
											batchNo=action.result.batchNo;
										}
									});
								});
							},
							failure : function(form, action) {
								if (action.faliureType == Ext.form.Action.SERVER_INVALID) {
									Ext.Msg.alert(alertTitle, "服务器连接异常，请联系管理员！");
								} else {
									if (action.result.msg) {
										win.hide();
										Ext.Msg.alert(alertTitle,action.result.msg, function() {
												//	dataStore.reload();
												});
	
									}
								}
							}
						});
					}
				}
			}, {
				text : "取消",
				handler : function() {
				   win.close();

				}
			}]
								
		
		});
	
		win.on('hide', function() {
					form2.destroy();
				});
		
		win.show();
	}


});


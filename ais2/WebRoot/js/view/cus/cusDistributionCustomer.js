//分配客户js
var privilege=157;//权限参数
var gridSearchUrl="cus/customerListAction!findDistributionCustomer.action";//客户信息查询地址
var saveCusServiceUrl="cus/cusServiceAction!saveCusService.action";//分配客户保存地址
var moveCusServiceUrl="cus/cusServiceAction!moveCusService.action";//分配客户移除地址
var cusServiceUrl='user/userAction!list.action';//客服员查询地址
var searchWidth=130;
var rowWidth=70;
var  fields=[{name:"id",mapping:'ID'},
			{name:"cusId",mapping:'CUS_ID'},
     		{name:'cusName',mapping:'CUS_NAME'},
     		{name:'importanceLevel',mapping:'IMPORTANCE_LEVEL'},
     		{name:'developLevel',mapping:'DEVELOP_LEVEL'},
     		{name:'departCode',mapping:'DEPART_CODE'},
     		{name:'serviceId',mapping:'SERVICE_ID'},
     		{name:'bcServiceName',mapping:'SERVICE_NAME'}];

     	//客服员 cusServiceUrl
		cusServiceStore=new Ext.data.Store({
	        storeId:"cusServiceStore",
			proxy:new Ext.data.HttpProxy({url:sysPath+"/"+cusServiceUrl}),
			baseParams:{filter_EQL_bussDepart:bussDepart},
			reader:new Ext.data.JsonReader({
	                    root:'result',
	                    totalProperty:'totalCount'
	        },[
        	{name:'cusServiceId',mapping:'id'},
        	{name:'cusServiceName',mapping:'userName'}
        	])
        	
		});
Ext.onReady(function(){
	var rownum=new Ext.grid.RowNumberer({
		header:'序号',
        width : 35,
		sortable : true	
	});
	
	var rownum2=new Ext.grid.RowNumberer({
		header:'序号',
        width : 35,
		sortable : true	
	});
	var sm = new Ext.grid.CheckboxSelectionModel();

	var sm2 = new Ext.grid.CheckboxSelectionModel({singleSelect:false});
	
	var dateStore = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({url:sysPath+"/"+this.gridSearchUrl}),
        baseParams:{pageSize: pageSize,filter_distributionItem:"NO"},
        reader:new Ext.data.JsonReader(
    	         {  root:'resultMap',
                    totalProperty:'totalCount'
                  },fields)
    });
    
	var myDateStore = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({url:sysPath+"/"+this.gridSearchUrl}),
        baseParams:{pageSize: pageSize,filter_distributionItem:"YES"},
        reader:new Ext.data.JsonReader(
    	         {  root:'resultMap',
                    totalProperty:'totalCount'
                  },fields)
    }); 
 	var twobar = new Ext.Toolbar({
 				id:'twobar',
		    	items : ['&nbsp;','<B>未分配客户信息</B>'
		    			 ,'-','&nbsp;',
		    	{xtype:'textfield',blankText:'查询数据',
		    		id:'searchContent', 
		    		width : searchWidth,
		    		enableKeyEvents:true,
		    		listeners : {
			 		keyup:function(textField, e){
		                     if(e.getKey() == 13){
		                     	searchLog();
		                     }
			 		}
			 	}},
			 	'-',{ 
    					xtype : "combo",
    					width : searchWidth,
    					triggerAction : 'all',
    					id:'searchSelectBox',
    					model : 'local',
    					hiddenId : 'checkItems',
    					hiddenName : 'checkItems',
    					name : 'checkItemstext',
    					store : [['', '查询全部'],
    							['cus_Name','客户名称'],
    							['service_name','客服员'],
    							['service_name_isNULL','未分配客户'],
    							['importance_Level','重要程度'],
    							['develop_Level','开发阶段']],
    							
    					emptyText : '选择类型',
    					editable : false,
    					forceSelection : true,
    					listeners : {
    						'select' : function(combo, record, index) {
								if(Ext.getCmp("searchContent").getValue().length>0){
									searchLog();
								}
    							//查询全部
    							if(combo.getValue()=='' || combo.getValue()=='service_name_isNULL'){
    								searchLog();
    							}
    						},render:function(combo) {
    							combo.setValue('cus_Name');
    						}
    					}
    					
    				},{
    				     text : '<b>查询</b>',
    				     iconCls : 'btnSearch',
    					 handler : searchLog
    				  }]
		});
		var rightbar = new Ext.Toolbar({
 				id:'rightbar',
		    	items : ['&nbsp;','<B>已分配客户信息</B>'
		    			 ,'-','&nbsp;',
		    	{xtype:'textfield',blankText:'查询数据',
		    		id:'searchRightContent', 
		    		width : searchWidth,
		    		enableKeyEvents:true,
		    		listeners : {
			 		keyup:function(textField, e){
		                     if(e.getKey() == 13){
		                     	searchLog2();
		                     }
			 		}
			 	}},
			 	'-',{ 
    					xtype : "combo",
    					width : searchWidth,
    					triggerAction : 'all',
    					model : 'local',
    					hiddenId : 'checkRightItems',
    					hiddenName : 'checkRightItems',
    					store : [['', '查询全部'],
    							['cus_Name','客户名称'],
    							['service_name','客服员'],
    							['importance_Level','重要程度'],
    							['develop_Level','开发阶段']],
    							
    					emptyText : '选择类型',
    					editable : false,
    					forceSelection : true,
    					listeners : {
    						'select' : function(combo, record, index) {
								if(Ext.getCmp("searchRightContent").getValue().length>0){
									searchLog2();
								}
    							//查询全部
    							if(combo.getValue()==''){
    								searchLog2();
    							}
    						},render:function(combo) {
    							combo.setValue('cus_Name');
    						}
    					}
    					
    				},{
    				     text : '<b>查询</b>',
    				     iconCls : 'btnSearch',
    					 handler : searchLog2
    				  }]
		});
     
     var recordGrid=new Ext.grid.EditorGridPanel({
		id:'userCenter',
		height : 200,
		width : Ext.lib.Dom.getViewWidth()*0.45-5,
		autoScroll : true,  //面板上的body元素
	 	viewConfig : {
				scrollOffset: 0,
				autoScroll:true
		},
		frame : true,
	//	enableColumnResize:false, //关闭列的自适大小功能
		autoWidth:false,
	//	clicksToEdit:1,
		loadMask : true,
		sm:sm,
		tbar:twobar,
		stripeRows : true,
		columns:[ rownum,sm,
					{header: '客户ID', dataIndex: 'id',width:rowWidth,hidden:true},
					{header: '用户ID', dataIndex: 'serviceId',width:rowWidth,hidden:true},
					{header: '客商ID', dataIndex: 'cusId',width:rowWidth},
			        {header: '客户名称', dataIndex: 'cusName',width:rowWidth*2},
    				{header: '日常维护客服', dataIndex: 'bcServiceName',width:rowWidth+20},
    				{header: '重要程度', dataIndex: 'importanceLevel',width:rowWidth},
    				{header: '开发阶段', dataIndex: 'developLevel',width:rowWidth}
			    ],
		store :dateStore,
		bbar : new Ext.PagingToolbar({
					pageSize : pageSize, 
					store : dateStore,
					displayInfo : true,
					displayMsg : '显示第 {0} 条到 {1} 条记录，共 {2} 条',
					emptyMsg : "没有记录信息显示"
		})
	});
     
        var recordGrid2=new Ext.grid.GridPanel({
			id:'userCenter2',
			height : 10,
			width : Ext.lib.Dom.getViewWidth()*0.45-2,
			autoScroll : true,  //面板上的body元素
			viewConfig : {
				scrollOffset: 0,
				autoScroll:true
			},
			frame : true,
		//	enableColumnResize:false, //关闭列的自适大小功能
			autoWidth:false,
			loadMask : true,
			sm:sm2,
			tbar:rightbar,
			stripeRows : true,
			columns:[rownum2,sm2,
					{header: '客户ID', dataIndex: 'id',width:rowWidth,hidden:true},
					{header: '用户ID', dataIndex: 'serviceId',width:rowWidth,hidden:true},
					{header: '客商ID', dataIndex: 'cusId',width:rowWidth},
			        {header: '客户名称', dataIndex: 'cusName',width:rowWidth*2},
    				{header: '日常维护客服', dataIndex: 'bcServiceName',width:rowWidth+20},
    				{header: '重要程度', dataIndex: 'importanceLevel',width:rowWidth},
    				{header: '开发阶段', dataIndex: 'developLevel',width:rowWidth}
			    ],
			store : myDateStore,
			bbar : new Ext.PagingToolbar({
					id:'toolBar2',
					pageSize : pageSize, 
					store : myDateStore,
					displayInfo : true,
					displayMsg : '显示第 {0} 条到 {1} 条记录，共 {2} 条',
					emptyMsg : "没有记录信息显示"
					
			})
	});

      var form = new Ext.form.FormPanel({
								labelAlign : 'left',
								frame : true,
								renderTo:Ext.getBody(),
								bodyStyle : 'padding:0px 0px 0px',
							    height : Ext.lib.Dom.getViewHeight(),
								width : Ext.lib.Dom.getViewWidth(),
								labelAlign : "right",
									tbar:['&nbsp;',
//									  {
//				    				     text : '<b>保存</b>',
//				    				     iconCls : 'save'
//				    				  },'-','&nbsp;',
				    				  '客服员:',{xtype : "combo",
											width : searchWidth,
											pageSize : comboSize,
											forceSelection : true,
											//selectOnFocus : true,
											resizable : true,
											minChars : 0,
											queryParam : 'filter_LIKES_userName',
											triggerAction : 'all',
											store : cusServiceStore,
											mode : "remote",// 从服务器端加载值
											valueField : 'cusServiceId',// value值，与fields对应
											displayField : 'cusServiceName',// 显示值，与fields对应
										    name : 'cusServiceName',
										    id:'searchcusService', 
										    enableKeyEvents:true,
										    listeners : {
												 'select':function(combo){
												 	searchCustomerByUser();
												 }
										    }
								 	},'-',{
				    				     text : '<b>查询</b>',
				    				     id : 'btn2',
				    				     iconCls : 'btnSearch',
				    					 handler : function(){
				    					 	searchCustomerByUser();
				    					 }
				    				  }],
						        items : [{
											layout : 'column',
											items : [{
														columnWidth : .45,
														layout : 'form',
														items : [recordGrid]

													},{
														columnWidth : .1,
														layout : 'form',
														bodyStyle:'text-align:center',  
														items : [
														 {html : '<pre><p></p><p> &nbsp;</p><p> &nbsp;</p><p> </p><p> </p><p> </p><p> </p><p> </p><p></p><p> &nbsp;</p></pre>'}, 
															{
																xtype:'button',
															  	id:'button1',
															  	width:Ext.lib.Dom.getViewWidth()*.05,
							    							 	text:'<B>分配客户>></B>',
							    							 	 handler : function(){
							    							 	 	
							    							 	 	var records = recordGrid.getSelectionModel().getSelections();
							    							 	 	if(records.length<1){
							    							 	 		Ext.Msg.alert(alertTitle,'请选择你要添加分配的客户！');
							    							 	 		return;
							    							 	 	}else{
							    							 	 		var serverName = Ext.getCmp('searchcusService').getRawValue();
							    							 	 		alertmsg="确定把这"+records.length+"个客户的客服员更改为"+serverName+"吗?";	
																		//var alertmsg="此客户的客服原来"+(records[i].data.bcServiceName==null?'没有客服员！确定要新增此客服员吗？':'的客服员为'+records[i].data.bcServiceName+'确定要更改为');
																		 Ext.Msg.confirm(alertTitle, alertmsg, function(btnYes) {
																				if (btnYes == 'yes' || btnYes == 'ok' || btnYes == true) {
																	           		saveRecords(records,true);
																	           }
																	       });
																		
																		/*Ext.Msg.confirm(alertTitle, alertmsg, function(
																			btnYes) {
																			if (btnYes == 'yes' || btnYes == 'ok' || btnYes == true) {
																					saveRecords(records,true);
																			}else{
																					saveRecords(records,false);
																			}
																		});*/
																		
																		
							    							 	 	}
							    							 	 }
															},
															 {html : '<pre><p></p><p> &nbsp;</p><p> &nbsp;</p><p> </p><p></pre>'}, 
															{
																xtype:'button',
															  	id:'button2',
															  	width:Ext.lib.Dom.getViewWidth()*.05,
							    							 	text:'<B><<移除客户</B>',
							    							 	 handler : function(){
							    							 	 	var records = recordGrid2.getSelectionModel().getSelections();
							    							 	 	if(records.length<1){
							    							 	 		Ext.Msg.alert(alertTitle,'没有要移除的客户！');
							    							 	 		return;
							    							 	 	}else{
							    							 	 		alertmsg="确定把客服员移除这"+records.length+"个客户的客服员吗?";	
																		 Ext.Msg.confirm(alertTitle, alertmsg, function(btnYes) {
																			   if (btnYes == 'yes' || btnYes == 'ok' || btnYes == true) {
							    							 	 					moveRecords(records);
																	           }
																	     });
							    							 	 	}
							    							 	 }
															}
														]
													},{
														columnWidth : .45,
														layout : 'form',
														items : [recordGrid2]
													}]
													
										}]
					});
     
     
	

	
		form.render();
		recordGrid.setHeight(Ext.lib.Dom.getViewHeight()-35);
		recordGrid2.setHeight(Ext.lib.Dom.getViewHeight()-35);
		
	//查询未被分配的客户
	function searchLog() {
		var searchcusService = Ext.getCmp('searchcusService').getValue();
	 	if(null==searchcusService || searchcusService.length<1){
	 		Ext.Msg.alert(alertTitle,'没选择客服员！');
	 		return;
	 	}
		  dateStore.proxy = new Ext.data.HttpProxy({
					method : 'POST',
					url : sysPath+'/'+gridSearchUrl
		  });
		  var searchcusService = Ext.getCmp('searchcusService').getValue();
		  dateStore.on('beforeload', function(store,options){
		 	Ext.apply(options.params, {
		 		filter_userId:searchcusService,
				filter_searchItem : Ext.get("checkItems").dom.value,
			 	filter_searchValue : Ext.get("searchContent").dom.value
		 	})
		 });
		 dateStore.reload({
			params : {
				start : 0,
				privilege:privilege,
				limit : pageSize
			}
		});
	} 

	//查询已经分配的客户
	function searchLog2() {
		var searchcusService = Ext.getCmp('searchcusService').getValue();
	 	if(null==searchcusService || searchcusService.length<1){
	 		Ext.Msg.alert(alertTitle,'没选择客服员！');
	 		return;
	 	}
		  myDateStore.proxy = new Ext.data.HttpProxy({
					method : 'POST',
					url : sysPath+'/'+gridSearchUrl
		  });
		  
		 var searchcusService = Ext.getCmp('searchcusService').getValue();
		 myDateStore.on('beforeload', function(store,options){
		 	Ext.apply(options.params, {
		 		filter_userId:searchcusService,
		 		filter_searchItem : Ext.get("checkRightItems").dom.value,
			 	filter_searchValue : Ext.get("searchRightContent").dom.value
		 	})
		 });
		 
		 myDateStore.reload({
			params : { 
				start : 0,
				privilege:privilege,
				limit : pageSize
			}
		});
	} 
	//根据客服员查询客户和已经分配的客户
	function searchCustomerByUser(){
		var searchcusService = Ext.getCmp('searchcusService').getValue();
	 	if(null==searchcusService || searchcusService.length<1){
	 		Ext.Msg.alert(alertTitle,'没选择客服员！');
	 		return;
	 	}else{
	 		searchLog();
	 		searchLog2();
	 	}
	}
	
	//保存选择好的分配客户
	function saveRecords(records,flag){
		var cusRecordIds="";
		for(var i=0;i<records.length;i++){
			cusRecordIds += records[i].data.id;
			if(i!=(records.length-1)){
				cusRecordIds +=',';
			}
		}
		var searchcusService = Ext.getCmp('searchcusService').getValue();
		//saveCusServiceUrl
		Ext.Ajax.request({
			url : sysPath+'/'
					+ saveCusServiceUrl+"?privilege="+privilege,
			params :{cusRecordIds:cusRecordIds,userId:searchcusService,flag:flag},
			success : function(resp) {
				var respText = Ext.util.JSON.decode(resp.responseText);
				if(respText.success){
					//searchLog2();
					searchCustomerByUser();
				}else{
					Ext.Msg.alert(alertTitle,respText.msg);
				}
			}
		});
	}
	
	//移除选择好的分配客户
	function moveRecords(records){
		var cusRecordIds="";
		for(var i=0;i<records.length;i++){
			cusRecordIds += records[i].data.id;
			if(i!=(records.length-1)){
				cusRecordIds +=',';
			}
		}
		var searchcusService = Ext.getCmp('searchcusService').getValue();
		//saveCusServiceUrl
		Ext.Ajax.request({
			url : sysPath+'/'
					+ moveCusServiceUrl+"?privilege="+privilege,
			params :{cusRecordIds:cusRecordIds,userId:searchcusService},
			success : function(resp) {
				var respText = Ext.util.JSON.decode(resp.responseText);
				if(respText.success){
					//searchLog();
					searchCustomerByUser();
				}else{
					Ext.Msg.alert(alertTitle,respText.msg);
				}
			}
		});
	}
	});
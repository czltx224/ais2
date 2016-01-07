	Ext.QuickTips.init();
	var privilege=194;
	var comboxPage=50;
	var departGridSearchUrl = "sys/departAction!findAll.action";
	var saveUrl="fi/fiAdvanceAction!save.action";
	var customerGridSearchUrl="sys/customerAction!list.action";
	var areaListUrl="stock/oprStoreAreaAction!list.action";
	var ralaListUrl="fi/fiCheckAction!list.action";
	var listUrl="fi/fiCheckAction!list.action";
	var customerGridSearchUrl = "sys/customerAction!list.action";
	var dictionaryUrl='sys/dictionaryAction!ralaList.action'; 
	var paymentSearchUrl="fi/fiCapitaaccountsetAction!ralaList.action";
	
	
Ext.onReady(function(){
	Ext.QuickTips.init();
	var rownum=new Ext.grid.RowNumberer({
		header:'No.',
        width : 25,
		sortable : true
		
	});
		
		possm = new Ext.grid.CheckboxSelectionModel({singleSelect:true});
		poscm  = new Ext.grid.RowNumberer({
						header : '序号',
						width : 25,
						sortable : true
					});
					
		var paymentcm=new Ext.grid.ColumnModel([poscm, possm, {
						header : 'id',
						dataIndex : 'id',
						sortable : true,
						hidden : true
					},{
						header : '账号类型id',
						dataIndex : 'accountType',
						hidden : true
					},{
						header : '账号类型',
						dataIndex : 'accountTypeName',
						width : 80
					}, {
						header : '账号',
						dataIndex : 'accountNum',
						width : 80
					}, {
						header : '账号名称',
						dataIndex : 'accountName',
						width : 80
					}, {
						header : '开户行',
						dataIndex : 'bank',
						width : 80
					}, {
						header : '余额',
						dataIndex : 'openingBalance',
						width : 60
					}, {
						header : '负责人',
						dataIndex : 'responsible',
						width : 60
					}])
		
	var fields=[
	'id','customerName',
	'departId',
	'customerId',
	'amount',
	'checkUser',
	'checkNo',
	'checkDate',
	'paymentRemark',
	'remark','departName','ts',
	'updateName',
	'updateTime',
	'createName',
	'createTime',
	'createDept',
	'createDeptid',
	'fipaidId',
	'invalidUser',
	'invalidDate',
	'invalidStatus',
	'reviewUser',
	'reviewDate',
	'reviewStatus',
	'submitUser',
	'submitDate',
	'submitStatus',
	'reachUser',
	'reachDate',
	'reachStatus',
	'todepositUser',
	'todepositDate',
	'todepositStatus',
	'todepositFiCapitaaccountset',
	'confirmUser',
	'confirmDate',
	'confirmStatus',
	'returnUser',
	'returnDate',
	'returnStatus'];
    			
    var jsonread= new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'},
                    fields);
                              
	var sm = new Ext.grid.CheckboxSelectionModel({
		width:25,
		//singleSelect :true,
		moveEditorOnEnter:true, 
		id:'checkId',
		name:'checkId'
	});
	
	var paymentfields = [{
			name : "id",
			mapping : 'id'
		}, {
			name : 'accountType',// 账号类型
			mapping : 'accountType'
		}, {
			name : 'paymentTypeName',// 收支类型
			mapping : 'paymentTypeName'
		}, {
			name : 'accountTypeName',// 账号类型
			mapping : 'accountTypeName'
		},{
			name : 'accountNum',// /账号
			mapping : 'accountNum'
		}, {
			name : 'accountName',// 账号名称
			mapping : 'accountName'
		}, {
			name : 'bank',// 开户行
			mapping : 'bank'
		}, {
			name : 'openingBalance',//余额
			mapping : 'openingBalance'
		}, {
			name : 'responsible',// 负责人
			mapping : 'responsible'
		}];
	
	// 账号设置
	var paymentdateStore = new Ext.data.Store({
		storeId : "paymentdateStore",
		proxy : new Ext.data.HttpProxy({
					url : sysPath + "/" + paymentSearchUrl,
					method:'POST'
				}),
		baseParams:{
			filter_EQL_responsibleId:userId,
			filter_EQL_paymentType:14250,//收款账号:收款
			filter_EQL_isDelete:1,
			privilege:110
		},
		reader : new Ext.data.JsonReader({
					root : 'result',
					totalProperty : 'totalCount'
				}, paymentfields)
		});
 
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
									name : 'cusName',
									mapping : 'cusName'
								}, {
									name : 'id',
									mapping : 'id'
								}])
			});
	customerStore.load();
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
		
	var sourceDataStore=new Ext.data.SimpleStore({
		auteLoad:true, //此处设置为自动加载
  	  	data:[
  	  		['1','已审核'],
  	  		['0','未审核']],
   		fields:["id","name"]
	});
	
	var autoStatus=new Ext.data.SimpleStore({
		auteLoad:true, //此处设置为自动加载
  	  	data:[
  	  		['1','已点到'],
  	  		['0','未点到']],
   		fields:["id","name"]
	});
	
	var submitStatusStore=new Ext.data.SimpleStore({
		auteLoad:true, //此处设置为自动加载
  	  	data:[
  	  		['1','已到账确认'],
  	  		['0','未到账确认']],
   		fields:["id","name"]
	});
	
	var delStatusStore=new Ext.data.SimpleStore({
		auteLoad:true, //此处设置为自动加载
  	  	data:[
  	  		['1','已作废'],
  	  		['0','未作废']],
   		fields:["id","name"]
	});
	
	
	
	var dataStore = new Ext.data.Store({
                proxy: new Ext.data.HttpProxy({url:sysPath+"/"+ralaListUrl}),
                baseParams:{
                	limit: pageSize
                },
                sortInfo : {field: "checkNo", direction: "ASC"},
                reader:jsonread
    });
 	
 	var twobar = new Ext.Toolbar({
		    	items : ['&nbsp;','创建日期:',{
							xtype : 'datefield',
			    			id : 'startDate',
				    		format : 'Y-m-d',
				    		emptyText : "选择开始时间",
				    		anchor : '95%',
				    		selectOnFocus:true,
				    		width : 80,
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
				    		selectOnFocus:true,
				    		width : 80,
				    		anchor : '95%'
						},'-','&nbsp;','创建部门:',{
							xtype : 'combo',
							id:'comboRightDepart2',
							hiddenId : 'dictionaryName',
				   			hiddenName : 'olist[0].rightDepartId',
							triggerAction : 'all',
							store : rightDepartStore2,
							mode:'local',
							width : 80,
							selectOnFocus:true,
				//			queryParam : 'filter_LIKES_departName',
							minChars : 1,
							listWidth:245,
							allowBlank : true,
							emptyText : "请选择创建部门名称",
							forceSelection : false,
							fieldLabel:'部门',
							editable : true,
							pageSize:500,
							displayField : 'departName',//显示值，与fields对应
							valueField : 'departId',//value值，与fields对应
							anchor : '95%',
							enableKeyEvents:true,
							listeners : {
								 afterRender:function(combo){
								 		
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
					    },'-','&nbsp;','到达部门:',{
							xtype : 'combo',
							id:'doDepart',
							hiddenId : 'dictioName',
				   			hiddenName : 'rightDepartId',
							triggerAction : 'all',
							store : rightDepartStore2,
							mode:'local',
							selectOnFocus:true,
							width : 80,
				//			queryParam : 'filter_LIKES_departName',
							minChars : 1,
							listWidth:245,
							allowBlank : true,
							emptyText : "请选择到达部门名称",
							forceSelection : false,
							editable : true,
							pageSize:500,
							displayField : 'departName',//显示值，与fields对应
							valueField : 'departId',//value值，与fields对应
							anchor : '95%',
							enableKeyEvents:true,
							listeners : {
								 afterRender:function(combo){
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
												}
										});
								 },
						 		 keyup:function(numberfield, e){
						             if(e.getKey() == 13 ){
											searchLog();
						              }
						 		 }
						 	}
					    }]
		});

    var threebar = new Ext.Toolbar({
	    	items:['&nbsp;','审核状态:',{
				xtype : 'combo',
				typeAhead : false,
				forceSelection : true,
				width : 60,
				id:'status',
				mode:'local',
				triggerAction : 'all',
				store : sourceDataStore,
				displayField : 'name',
				valueField : 'id',
				emptyText : "请选择审核状态"
					
			},'&nbsp;','点到状态:',{
				xtype : 'combo',
				typeAhead : false,
				forceSelection : true,
				width : 60,
				id:'statuspi',
				mode:'local',
				triggerAction : 'all',
				store : autoStatus,
				displayField : 'name',
				valueField : 'id',
				emptyText : "请选点到状态"
			},'&nbsp;','到账状态:',{
				xtype : 'combo',
				typeAhead : false,
				forceSelection : true,
				width : 60,
				id:'payStatus',
				mode:'local',
				triggerAction : 'all',
				store : submitStatusStore,
				displayField : 'name',
				valueField : 'id',
				emptyText : "请选择到账状态"
			},'&nbsp;','作废状态:',{
				xtype : 'combo',
				typeAhead : false,
				forceSelection : true,
				width : 60,
				id:'payManey',
				mode:'local',
				triggerAction : 'all',
				store : delStatusStore,
				displayField : 'name',
				valueField : 'id',
				emptyText : "请选择作废状态"
			}]});
     
     function searchLog() {
     		Ext.getCmp('showMsg').getEl().update('<span style="color:red"></span>');
	     	var start='';
	    	var end ='';
	    	var deId = Ext.getCmp('comboRightDepart2').getValue();
	    	var deId2= Ext.getCmp('doDepart').getValue();
	    	if(Ext.getCmp('startDate').getValue()!=""){
	    		 start = Ext.getCmp('startDate').getValue().format("Y-m-d");
	    	}
	    	if(Ext.getCmp('endDate').getValue()!=""){
	    		 end = Ext.getCmp('endDate').getValue().format("Y-m-d");
	    	}
	    	
		 	Ext.apply(dataStore.baseParams={
				filter_GED_createTime : start,
            	filter_LTD_createTime : end,
            	filter_EQL_departId:deId,
            	filter_EQL_createDeptid:deId2,
            	filter_EQL_reviewStatus : Ext.getCmp('status').getValue(),
            	filter_EQL_reachStatus : Ext.getCmp('statuspi').getValue(),
            	filter_EQL_confirmStatus : Ext.getCmp('payStatus').getValue(),
            	filter_EQL_invalidStatus : Ext.getCmp('payManey').getValue()
   			});
	    	
	    	/*
	    	else if(Ext.getCmp('comboselect').getValue()=='EQL_matStatus'){
	    		Ext.apply(dataStore.baseParams={
					filter_GED_createTime : start,
	            	filter_LTD_createTime : end,
	            	filter_EQL_departId:deId,
//	            	privilege:privilege,
	            	filter_EQL_matStatus:Ext.getCmp('statuspi').getValue(),
	            	filter_EQL_customerId : Ext.getCmp('comboTypeDepart').getValue()
	   			});
	    	}else if(Ext.getCmp('comboselect').getValue()=='EQL_status'){
	    		Ext.apply(dataStore.baseParams={
					filter_GED_createTime : start,
	            	filter_LTD_createTime : end,
	            	filter_EQL_departId:deId,
//	            	privilege:privilege,
	            	filter_EQL_customerId : Ext.getCmp('comboTypeDepart').getValue(),
	            	filter_EQL_status:Ext.getCmp('status').getValue()
	   			});
	    	}else if(Ext.getCmp('comboselect').getValue()=='EQL_payStatus'){
	    		Ext.apply(dataStore.baseParams={
					filter_GED_createTime : start,
	            	filter_LTD_createTime : end,
	            	filter_EQL_departId:deId,
	            	filter_EQL_customerId : Ext.getCmp('comboTypeDepart').getValue(),
	            	filter_EQL_payStatus:Ext.getCmp('payStatus').getValue()
	   			});
	    	}else{
	    		Ext.apply(dataStore.baseParams={
					filter_GED_createTime : start,
	            	filter_LTD_createTime : end,
	            	filter_EQL_departId:deId,
//	            	privilege:privilege,
	            	filter_EQL_customerId : Ext.getCmp('comboTypeDepart').getValue(),
					checkItems :Ext.getCmp('comboselect').getValue(),
					itemsValue :Ext.getCmp('itemsValue').getValue()
	   			});
	    	}
*/
			dataStore.reload({
					params : {
						start : 0,
//						privilege:privilege,
						limit : pageSize
					}
			});
	}
     
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
		tbar:['&nbsp;',
			   {text : '<b>修改</b>',
				iconCls : 'table',
				id : 'updateAduit',
				handler : function(){
					Ext.getCmp('showMsg').getEl().update('<span style="color:red"></span>');
					var vnetmusicRecord = recordGrid.getSelectionModel().getSelections();
			
					if (vnetmusicRecord.length != 1) {
//						Ext.getCmp('showMsg').getEl().update('<span style="color:red">操作被拒绝，请选择一条数据再进行操作。</span>');
						Ext.Msg.alert(alertTitle,"请选择一条数据再进行操作。",function(){});
					}else{
						if(vnetmusicRecord[0].get('invalidStatus')!='1'&&vnetmusicRecord[0].get('reviewStatus')!='1'){
							updateData(vnetmusicRecord[0]);
						}else{
							//Ext.getCmp('showMsg').getEl().update('<span style="color:red">审核过的数据不能进行修改操作。</span>');
							Ext.Msg.alert(alertTitle,"审核过的数据不能进行修改操作。",function(){});
							return;
						}
					}
				}
			},'-','&nbsp;',
			{
				text : '<b>作废</b>',
				iconCls : 'table',
				id : 'deleteAduit',
				handler : function(){
					Ext.getCmp('showMsg').getEl().update('<span style="color:red"></span>');
					var vnetmusicRecord = recordGrid.getSelectionModel().getSelections();
			
					if (vnetmusicRecord.length != 1) {
//						Ext.getCmp('showMsg').getEl().update('<span style="color:red">操作被拒绝，请选择一条数据再进行操作。</span>');
						Ext.Msg.alert(alertTitle,"请选择一条数据再进行操作。",function(){});
					}else{
						if(vnetmusicRecord[0].get('reviewStatus')!='1'&&vnetmusicRecord[0].get('invalidStatus')!='1'){
							deleteData(vnetmusicRecord[0]);
						}else{
							//Ext.getCmp('showMsg').getEl().update('<span style="color:red">审核过和已作废的数据不能进行作废操作。</span>');
							Ext.Msg.alert(alertTitle,"审核过和已作废的数据不能进行作废操作。",function(){});
							return;
						}
					}
				}
			},'-','&nbsp;',{
				text : '<b>审核</b>',
				iconCls : 'table',
				id : 'carAduit',
				handler : function(){
					Ext.getCmp('showMsg').getEl().update('<span style="color:red"></span>');
					var vnetmusicRecord = recordGrid.getSelectionModel().getSelections();
			
					if (vnetmusicRecord.length != 1) {
//						Ext.getCmp('showMsg').getEl().update('<span style="color:red">操作被拒绝，请选择一条数据再进行操作。</span>');
						Ext.Msg.alert(alertTitle,"请选择一条数据再进行操作。",function(){});
					}else{
						if(vnetmusicRecord[0].get('reviewStatus')!='1'&&vnetmusicRecord[0].get('invalidStatus')!='1'){
							fiAudit(vnetmusicRecord[0]);
						}else{
							//Ext.getCmp('showMsg').getEl().update('<span style="color:red">已审核和已作废的数据不能进行审核</span>');
							Ext.Msg.alert(alertTitle,"已审核和已作废的数据不能进行审核。",function(){});
							return;
						}
					}
				}
			},'-','&nbsp;',
			{
				text : '<b>支票上缴</b>',
				iconCls : 'table',
				id : 'changeAduit',
				handler : function(){
					Ext.getCmp('showMsg').getEl().update('<span style="color:red"></span>');
					var vnetmusicRecord = recordGrid.getSelectionModel().getSelections();
			
					if (vnetmusicRecord.length != 1) {
//						Ext.getCmp('showMsg').getEl().update('<span style="color:red">操作被拒绝，请选择一条数据再进行操作。</span>');
						Ext.Msg.alert(alertTitle,"请选择一条数据再进行操作。",function(){});						
					}else{
						if(vnetmusicRecord[0].get('reviewStatus')=='1'&&vnetmusicRecord[0].get('submitStatus')!='1'){
							submitData(vnetmusicRecord[0]);
						}else{
//							Ext.getCmp('showMsg').getEl().update('<span style="color:red">已上缴和未审核过的数据不能进行支票上缴</span>');
							Ext.Msg.alert(alertTitle,"已上缴和未审核过的数据不能进行支票上缴。",function(){});
							return;
						}
					}
				}
			},'-','&nbsp;',
			{
				text : '<b>点到支票</b>',
				iconCls : 'table',
				id : 'fiAduit',
				handler : function(){
					Ext.getCmp('showMsg').getEl().update('<span style="color:red"></span>');
					var vnetmusicRecord = recordGrid.getSelectionModel().getSelections();
			
					if (vnetmusicRecord.length != 1) {
//						Ext.getCmp('showMsg').getEl().update('<span style="color:red">操作被拒绝，请选择一条数据再进行操作。</span>');
						Ext.Msg.alert(alertTitle,"请选择一条数据再进行操作。",function(){});
					}else{
						if(vnetmusicRecord[0].get('submitStatus')=='1'&&vnetmusicRecord[0].get('reachStatus')!='1'){
							reachData(vnetmusicRecord[0]);
						}else{
//							Ext.getCmp('showMsg').getEl().update('<span style="color:red">已点到和未上缴过的数据不能进行支票点到</span>');
							Ext.Msg.alert(alertTitle,"已点到和未上缴过的数据不能进行支票点到。",function(){});
							return;
						}
					}
				}
			},'-','&nbsp;',{
				text : '<b>撤销点到</b>',
				iconCls : 'table',
				id : 'deleteFi',
				handler : function(){
					Ext.getCmp('showMsg').getEl().update('<span style="color:red"></span>');
					var vnetmusicRecord = recordGrid.getSelectionModel().getSelections();
			
					if (vnetmusicRecord.length != 1) {
//						Ext.getCmp('showMsg').getEl().update('<span style="color:red">操作被拒绝，请选择一条数据再进行操作。</span>');
						Ext.Msg.alert(alertTitle,"请选择一条数据再进行操作。",function(){});
					}else{
						if(vnetmusicRecord[0].get('reachStatus')=='1'&&vnetmusicRecord[0].get('todepositStatus')!='1'){
							qxReachData(vnetmusicRecord[0]);
						}else{
//							Ext.getCmp('showMsg').getEl().update('<span style="color:red">未点到和已送存银行的数据不能进行撤销点到</span>');
							Ext.Msg.alert(alertTitle,"未点到和已送存银行的数据不能进行撤销点到。",function(){});
							return;
						}
					}
				}
			},'-','&nbsp;',{
				text : '<b>送存银行</b>',
				iconCls : 'table',
				id : 'doOutDan',
				handler : function(){
					Ext.getCmp('showMsg').getEl().update('<span style="color:red"></span>');
					var vnetmusicRecord = recordGrid.getSelectionModel().getSelections();
			
					if (vnetmusicRecord.length != 1) {
//						Ext.getCmp('showMsg').getEl().update('<span style="color:red">操作被拒绝，请选择一条数据再进行操作。</span>');
						Ext.Msg.alert(alertTitle,"请选择一条数据再进行操作。",function(){});
					}else{
						if(vnetmusicRecord[0].get('reachStatus')=='1'&&vnetmusicRecord[0].get('todepositStatus')!='1'){
							todepositData(vnetmusicRecord[0]);
						}else{
//							Ext.getCmp('showMsg').getEl().update('<span style="color:red">未点到和已送存银行的支票数据不能进行支票送存</span>');
							Ext.Msg.alert(alertTitle,"未点到和已送存银行的支票数据不能进行支票送存。",function(){});
							return;
						}
					}					
				}
			},'-','&nbsp;',{
				text : '<b>到账确认</b>',
				iconCls : 'table',
				id : 'doMoney',
				handler : function(){
					Ext.getCmp('showMsg').getEl().update('<span style="color:red"></span>');
					var vnetmusicRecord = recordGrid.getSelectionModel().getSelections();
			
					if (vnetmusicRecord.length != 1) {
						Ext.Msg.alert(alertTitle,"请选择一条数据再进行操作。",function(){});
					}else{
						if(vnetmusicRecord[0].get('todepositStatus')==1){
							checkAudit(vnetmusicRecord[0]);
						}else{
							Ext.Msg.alert(alertTitle,"未送存银行的支票数据不能进行到账确认。",function(){});
							return;
						}
					}	
				}
			}/*,'-','&nbsp;',{
				text : '<b>取消到账</b>',
				iconCls : 'table',
				id : 'qxDoMoney',
				handler : function(){
					 
				}
			}*/,'-','&nbsp;',{
				text : '<b>退票</b>',
				iconCls : 'table',
				id : 'out',
				handler :function(){
					Ext.getCmp('showMsg').getEl().update('<span style="color:red"></span>');
					var vnetmusicRecord = recordGrid.getSelectionModel().getSelections();
			
					if (vnetmusicRecord.length != 1) {
//						Ext.getCmp('showMsg').getEl().update('<span style="color:red">操作被拒绝，请选择一条数据再进行操作。</span>');
						Ext.Msg.alert(alertTitle,"请选择一条数据再进行操作。",function(){});
					}else{
						if(vnetmusicRecord[0].get('todepositStatus')=='1'&&vnetmusicRecord[0].get('confirmStatus')!='1'){
							outCheck(vnetmusicRecord[0]);
						}else{
//							Ext.getCmp('showMsg').getEl().update('<span style="color:red">未送存银行和已到账确认的数据不能进行退票处理</span>');
							Ext.Msg.alert(alertTitle,"未送存银行和已到账确认的数据不能进行退票处理。",function(){});
							return;
						}
					}					
				}
			},'-','&nbsp;',{
				text : '<b>导出</b>',
				iconCls : 'table',
				id : 'exportbtn',
				handler : exportinfo
			},'-','&nbsp;',{
		     text : '<b>查询</b>',
		     id : 'btn',
		     iconCls : 'btnSearch',
			 handler : searchLog
    		},'-','&nbsp;&nbsp;',{
   			xtype:'label',
   			id:'showMsg',
   			width:380
			}],
			columns:[ rownum,sm,
        			{header: '编号', dataIndex: 'id',width:55,hidden:true,sortable : true},
			        {header: '客商ID', dataIndex: 'customerId',width:70,hidden:true,sortable : true},
			        {header: '客商名称', dataIndex: 'customerName',width:80,sortable : true},　
			        {header: '支票号', dataIndex: 'checkNo',width:60,sortable : true},
			        {header: '支票金额', dataIndex: 'amount',width:70,sortable : true},
			        {header: '开票人', dataIndex: 'checkUser',width:70,sortable : true},
					{header: '开票时间', dataIndex: 'checkDate',width:70,hidden:true,sortable : true},
					{header: '送达部门', dataIndex: 'createDept',width:95,sortable : true},					
					{header: '送达部门ID', dataIndex: 'createDeptid',width:70,hidden:true,sortable : true},			        
			        {header: '付款摘要', dataIndex: 'paymentRemark',width:70,sortable : true},
					{header: '实收单号', dataIndex: 'fipaidId',width:70,sortable : true},
			        {header: '审核状态', dataIndex: 'reviewStatus',width:60,sortable : true,renderer:function(v){
				        		if(v=='1'){ 
				        			return '<span style="color:blue">已审核</span>';
				        		}else if(v=='0'){
				        			return '<span style="color:red">未审核</span>';
				        		}else{
				        			return '<span style="color:red">未审核</span>';;
				        		}
				        	}},
			        {header: '审核人', dataIndex: 'reviewUser',width:60,sortable : true},
			        {header: '审核时间', dataIndex: 'reviewDate',width:100,sortable : true},
			       
					{header: '上缴状态', dataIndex: 'submitStatus',width:60,sortable : true,renderer:function(v){
				        		if(v=='1'){ 
				        			return '<span style="color:blue">已上缴</span>';
				        		}else if(v=='0'){
				        			return '<span style="color:red">未上缴</span>';
				        		}else{
				        			return '<span style="color:red">未上缴</span>';
				        		}
				        	}},
			        {header: '上缴人', dataIndex: 'submitUser',width:60,sortable : true},
			        {header: '上缴时间', dataIndex: 'submitDate',width:100,sortable : true},
					
					{header: '点到状态', dataIndex: 'reachStatus',width:60,sortable : true,renderer:function(v){
				        		if(v=='1'){ 
				        			return '<span style="color:blue">已点到</span>';
				        		}else if(v=='0'){
				        			return '<span style="color:red">未点到</span>';
				        		}else{
				        			return '<span style="color:red">未点到</span>';
				        		}
				        	}},
			        {header: '点到人', dataIndex: 'reachUser',width:60,sortable : true},
			        {header: '点到时间', dataIndex: 'reachDate',width:100,sortable : true},

					{header: '送存状态', dataIndex: 'todepositStatus',width:60,sortable : true,renderer:function(v){
				        		if(v=='1'){ 
				        			return '<span style="color:blue">已送存</span>';
				        		}else if(v=='0'){
				        			return '<span style="color:red">未送存</span>';
				        		}else{
				        			return '<span style="color:red">未送存</span>';
				        		}
				        	}},
			        {header: '送存人', dataIndex: 'todepositUser',width:60,sortable : true},
			        {header: '送存时间', dataIndex: 'todepositDate',width:100,sortable : true},
					
					{header: '送存账号', dataIndex: 'todepositFiCapitaaccountset',width:80,sortable : true},
					
					{header: '到账状态', dataIndex: 'confirmStatus',width:60,sortable : true,renderer:function(v){
				        		if(v=='1'){ 
				        			return '<span style="color:blue">已到账</span>';
				        		}else if(v=='0'){
				        			return '<span style="color:red">未到账</span>';
				        		}else{
				        			return '<span style="color:red">未到账</span>';
				        		}
				        	}},
			        {header: '到账确认人', dataIndex: 'confirmUser',width:60,sortable : true},
			        {header: '到账时间', dataIndex: 'confirmDate',width:100,sortable : true},

					{header: '退票状态', dataIndex: 'returnStatus',width:60,sortable : true,renderer:function(v){
				        		if(v=='1'){ 
				        			return '<span style="color:blue">已退票</span>';
				        		}else if(v=='0'){
				        			return '<span style="color:red">未退票</span>';
				        		}else{
				        			return '<span style="color:red">未退票</span>';
				        		}
				        	}},
			        {header: '退票人', dataIndex: 'returnUser',width:60,sortable : true},
			        {header: '退票时间', dataIndex: 'returnDate',width:100,sortable : true},
				      
			        {header: '作废状态', dataIndex: 'invalidStatus',width:60,sortable : true,renderer:function(v){
				        		if(v=='1'){ 
				        			return '<span style="color:blue">已作废</span>';
				        		}else if(v=='0'){
				        			return '<span style="color:red">未作废</span>';
				        		}else{
				        			return '<span style="color:red">未作废</span>';
				        		}
				        	}},
			        {header: '作废人', dataIndex: 'invalidUser',width:60,sortable : true},
			        {header: '作废时间', dataIndex: 'invalidDate',width:100,sortable : true},
				        	
			       	{header: '创建人', dataIndex: 'createName',width:80,hidden:true,sortable : true},
			        {header: '创建时间', dataIndex: 'createTime',width:80,hidden:true,sortable : true},
			        {header: '修改人', dataIndex: 'updateName',width:80,hidden:true,sortable : true},
			        {header: '修改时间', dataIndex: 'updateTime',width:80,hidden:true,sortable : true},
			        {header: '创建部门ID', dataIndex: 'departId',width:80,hidden:true,sortable : true},
			        {header: '创建部门', dataIndex: 'departName',width:80,hidden:true,sortable : true},
			        {header: '时间戳', dataIndex: 'ts',width:55,hidden:true,sortable : true},  
			        {header: '备注', dataIndex: 'remark',width:80,sortable : true}
			    ],
			store : dataStore,
			listeners: {
                    render: function(){
                        twobar.render(this.tbar);
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

	
	recordGrid.render();
		
		
		recordGrid.on('click', function() {
			select();
		});
			
		function select(){ /*
			var vnetmusicRecord = recordGrid.getSelectionModel().getSelections();
			var costin = Ext.getCmp('costin');
			var fiAduit = Ext.getCmp('fiAduit');
			var carAduit = Ext.getCmp('carAduit');
			var exportbtn = Ext.getCmp('exportbtn');
			var printbtn = Ext.getCmp('printbtn');

			if (vnetmusicRecord.length == 1) {
				if(vnetmusicRecord[0].get('status')=='1'){
					costin.setDisabled(false);
				}else{
					costin.setDisabled(true);
				}
				
				if(vnetmusicRecord[0].get('status')=='2'){
					carAduit.setDisabled(false);
				}else{
					carAduit.setDisabled(true);
				}
				
				if(vnetmusicRecord[0].get('status')=='3'){
					fiAduit.setDisabled(false);
				}else{
					fiAduit.setDisabled(true);
				}
				
				exportbtn.setDisabled(false);
				printbtn.setDisabled(false);
			} else if (vnetmusicRecord.length > 1) {
				costin.setDisabled(true);
				fiAduit.setDisabled(true);
				carAduit.setDisabled(true);
				exportbtn.setDisabled(true);
				printbtn.setDisabled(true);
			} else {
				costin.setDisabled(true);
				fiAduit.setDisabled(true);
				carAduit.setDisabled(true);
				exportbtn.setDisabled(true);
				printbtn.setDisabled(true);
			}*/
		}
		
		function updateData(record) {
					 var form = new Ext.form.FormPanel({
								labelAlign : 'left',
								frame : true,
								bodyStyle : 'padding:5px 5px 5px',
							    width : 600,
							    labelWidth : 70,
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
																   xtype : 'combo',
										        				   fieldLabel: '客商名称<span style="color:red">*</span>',
														           allowBlank : false,
														           queryParam : 'filter_LIKES_cusName',
															       minChars : 1,
															       triggerAction : 'all',
																   typeAhead:false,
															       forceSelection : true,
																   store: customerStore,
																   pageSize : 50,
																   id:'customer',
																   hiddenName:'customerId',
																   hiddenId:'cuds',
																   displayField : 'cusName',
																   valueField : 'id',
																   blankText : "客商不能为空！",
																   anchor : '95%',
																   emptyText : "请选择客商",
																   enableKeyEvents:true,
																   listeners : {
																		keyup:function(numberfield, e){
														                	if(e.getKey() == 13 ){
//																			C	Ext.getCmp('flightMainNo').focus();
//																				Ext.getCmp('flightMainNo').selectText();
														                 	}
														 				},
														 				select:function(v){
														 				}
																	}
										        				},{
																	xtype : 'numberfield',
																	labelAlign : 'left',
																	fieldLabel : '开票金额<span style="color:red">*</span>',
																	name : 'amount',
																	id:'amount',
																	allowNegative:false,
																	maxLength:20,
																	allowBlank : false,
																	blankText : "开票金额不能为空！",
																	anchor : '95%',
																	enableKeyEvents:true,
																   	listeners : {
																		keyup:function(numberfield, e){
														                	if(e.getKey() == 13 ){
//																				Ext.getCmp('flightPiece').focus();
//																				Ext.getCmp('flightPiece').selectText();
														                 	}
														 				}
														 			}
																},{
																	xtype : 'textfield',
																	labelAlign : 'left',
																	fieldLabel : '开票人<span style="color:red">*</span>',
																	name : 'checkUser',
																	id:'checkUser',
																	maxLength:12,
																	allowBlank : false,
																	blankText : "计费重量不能为空！",
																	anchor : '95%'
																}]

													}, {
														columnWidth : .5,
														layout : 'form',
														items : [{
																	xtype : 'textfield',
																	fieldLabel : '支票号',
																	name : 'checkNo',
																	id:'checkNo',
																	readOnly:true,
																	maxLength:12,
																	allowBlank : false,
																	blankText : "单价不能为空！",
																	anchor : '95%'
																},{
																	xtype : 'datefield',
													    			id : 'checkDate',
													    			name : 'checkDate',
													    			fieldLabel : '开票日期<span style="color:red">*</span>',
														    		format : 'Y-m-d',
														    		emptyText : "请选择开票日期",
														    		anchor : '95%',
														    		listeners : {
														    			'render' : function() {
														    			   Ext.getCmp('checkDate').setMaxValue(new Date());
														    		     }
													    		    }
																},{
																	xtype : 'textfield',
																	labelAlign : 'left',
																	fieldLabel : '备注',
																	name : 'remark',
																	id:'remark',
																	maxLength:500,
																	allowBlank : true,
																	anchor : '95%'
																}]
													}]
													
										}]
										});
			
		if(record!=null){
			customerStore.load();
			form.load({
				url : sysPath+ "/"+ralaListUrl,
				params:{
					filter_EQL_id:record.data.id,
					limit : pageSize
				},
				success : function(form, action) {
				},
				failure : function(form, action) {
					Ext.Msg.alert(alertTitle,"加载失败，请重新加开窗口");		
				}
			})
		}
		
		var win = new Ext.Window({
			title : "支票信息修改",
			width : 600,
			closeAction : 'hide',
			plain : true,
		    resizable : false,	
			modal : true,
			items : form,
			buttonAlign : "center",	buttons : [{
				text : "保存",
				id:'savebtn',
				iconCls : 'groupSave',
				handler : function() {
					if (form.getForm().isValid()) {
						this.disabled = true;//只能点击一次
						form.getForm().submit({
							url : sysPath+ '/fi/fiCheckAction!save.action',
							params:{
								privilege:privilege,
								customerName:Ext.getCmp('customer').getRawValue()
							},
							waitMsg : '正在保存数据...',
							success : function(form, action) {
										win.hide(), 
										Ext.Msg.alert(alertTitle,action.result.msg, function() {
												dataStore.reload();
										});
							},
							failure : function(form, action) {
								if (action.faliureType == Ext.form.Action.SERVER_INVALID) {
									Ext.Msg.alert(alertTitle, "服务器连接异常，请联系管理员！");
								} else {
									if (action.result.msg) {
										win.hide();
										Ext.Msg.alert(alertTitle,action.result.msg, function() {
													dataStore.reload();
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
					form.destroy();
				});
		
		win.show();
 }
 	
 		// 支票点到
 	function reachData(record){
 		Ext.Msg.confirm(alertTitle,'您确定要这条件数据做支票点到吗?',function(btnYes) {
			if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
				form1.getForm().doAction('submit', {
					url:sysPath+"/fi/fiCheckAction!save.action",
					method : 'post',
					params : {
						id : record.get('id'),
						reachUser:userName,
						reachDate:new Date(),
						reachStatus:1,
						ts:record.get('ts'),
						privilege:privilege
   					},
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
 
 //支票上缴
	function submitData(record){
		Ext.Msg.confirm(alertTitle,'您确定要这条件数据做支票上缴吗?',function(btnYes) {
			if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
				form1.getForm().doAction('submit', {
					url:sysPath+"/fi/fiCheckAction!save.action",
					method : 'post',
					params : {
						id : record.get('id'),
						submitUser:userName,
						submitDate:new Date(),
						submitStatus:1,
						ts:record.get('ts'),
						privilege:privilege
   					},
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

 	/**
	 * 审核支票
	 */
	function fiAudit(record){
		Ext.Msg.confirm(alertTitle,'您确定要审核这条件数据吗?',function(btnYes) {
			if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
				form1.getForm().doAction('submit', {
					url:sysPath+"/fi/fiCheckAction!save.action",
					method : 'post',
					params : {
						id : record.get('id'),
						reviewUser:userName,
						reviewDate:new Date(),
						reviewStatus:1,
						ts:record.get('ts'),
						privilege:privilege
   					},
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
	
	// 撤销点到
	function qxReachData(record){
		Ext.Msg.confirm(alertTitle,'您确定要这条件数据撤销点到吗?',function(btnYes) {
			if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
				form1.getForm().doAction('submit', {
					url:sysPath+"/fi/fiCheckAction!save.action",
					method : 'post',
					params : {
						id : record.get('id'),
						reachUser:null,
						reachDate:null,
						reachStatus:0,
						ts:record.get('ts'),
						privilege:privilege
   					},
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
	
	//送存银行
	function todepositData(record){			
				var paymentGrid = new Ext.grid.GridPanel({
					region : "center",
					id : 'paymentGrid',
					//title: '选择现金账号',
					height : 249,
					width : 500,
					viewConfig : {
						columnsText : "显示的列",
						sortAscText : "升序",
						sortDescText : "降序",
						scrollOffset: 0,
						//forceFit : true,
						autoScroll:true
					},
					autoScroll : true,
					frame : true,
					loadMask : true,
					sm :possm ,
					cm : paymentcm,
			//		tbar : paymentTbar,
					ds : paymentdateStore,
					listeners:{
	    				rowdblclick:function(grid, rowindex, e){
	    				var record=grid.getSelectionModel().getSelected();
	    				/*
	    				//Ext.getCmp('accountTypeName').setValue(record.data.accountTypeName);
	    				Ext.getCmp('paymentPenyJenis').setValue(record.data.accountTypeName);
	    				Ext.getCmp('paymentAccountName').setValue(record.data.accountName);
	    				Ext.getCmp('paymentbank').setValue(record.data.bank);
	    				Ext.getCmp('paymentaccountId').setValue(record.data.id);
	    				Ext.getCmp('paymentresponsible').setValue(record.data.responsible);
	    				*/
	    				
	    				Ext.getCmp('todepositFiCapitaaccountset').setValue(record.data.id);
	    				Ext.getCmp('paymentAccountNo').setValue(record.data.accountNum);
	    				Ext.getCmp('checkUserName').setValue(record.data.accountName);
	    				Ext.getCmp('paymentAccountNo').collapse();
	    			
	    				//Ext.getCmp('goods').focus(true,true);
	    				}
	    			},keydown:function(e){
		    			if(e.getKey()==13){//回车
		    			/*	var record=grid.getSelectionModel().getSelected();
		    				//Ext.getCmp('accountTypeName').setValue(record.data.accountTypeName);
		    				Ext.getCmp('paymentPenyJenis').setValue(record.data.accountTypeName);
		    				Ext.getCmp('paymentAccountName').setValue(record.data.accountName);
		    				Ext.getCmp('paymentbank').setValue(record.data.bank);
		    				Ext.getCmp('paymentaccountId').setValue(record.data.id);
		    				Ext.getCmp('paymentresponsible').setValue(record.data.responsible);
		    				*/
		    				Ext.getCmp('todepositFiCapitaaccountset').setValue(record.data.id);
		    				Ext.getCmp('paymentAccountNo').setValue(record.data.accountNum);
		    				Ext.getCmp('checkUserName').setValue(record.data.accountName);
		    				Ext.getCmp('paymentAccountNo').collapse();
		    				//alert(record);
		    			}
    				}
					/*bbar : new Ext.PagingToolbar({
								pageSize : pageSize,
								store : paymentdateStore,
								displayInfo : true,
								displayMsg : '显示第 {0} 条到 {1} 条记录，共 {2} 条',
								emptyMsg : "没有记录信息显示"
							})*/
				});
		//store的load事件(如果store返回只有一行，默认为选中)
		paymentdateStore.on('load',function(){
	    		if(paymentGrid.getStore().getTotalCount()==1){
	    			paymentGrid.getSelectionModel().selectFirstRow(0);
	    		}
			});
		
		var form = new Ext.form.FormPanel({
								labelAlign : 'left',
								frame : true,
								bodyStyle : 'padding:5px 5px 5px',
							    width : 600,
							    labelWidth : 85,
								reader :jsonread,
					            labelAlign : "right",
						        items : [{
											layout : 'column',
											items : [{
														columnWidth : .5,
														layout : 'form',
														items : [{	id:"id",
																	name : "id",
																	value:record.get('id'),
																	xtype : "hidden"
																},{	id:"todepositFiCapitaaccountset",
																	name : "todepositFiCapitaaccountset",
																	xtype : "hidden"
																},{
																	name : "ts",
																	value:record.get('ts'),
																	xtype : "hidden"
																},{
																	xtype:'combo',
																	mode: 'local',
																	fieldLabel : '送存账号<span style="color:red">*</span>',
																	id:'paymentAccountNo',
																	name : 'todepositFiCapitaaccountsetName',
																	maxLength : 20,
																	anchor : '95%',
																	width:500,
																	queryParams:'paymentAccountNo',
																	minChars:30,
																	triggerAction:'all',
																	maxHeight: 500, 
																	listWidth:500,
																	forceSelection:false,
																	emptyText:'请输入收款账号',
																	allowBlank:false,
																	blankText:'收款账号不能为空!!',
																	tpl: '<div id="paymentpanel" style="width:500px;height:249px"></div>',
																	store:new Ext.data.SimpleStore({fields:["paymentAccountNo","paymentAccountNo"],data:[[]]}),
																	enableKeyEvents:true,
																	listeners : {
																 		
														 			}
																},{
																	xtype : 'textfield',
																	labelAlign : 'left',
																	fieldLabel : '送存人',
																	name : 'todepositUser',
																	readOnly:true,
																	value:userName,
																	id:'todepositUser',
																	maxLength:12,
																	allowBlank : false,
																	anchor : '95%'
																}]

													}, {
														columnWidth : .5,
														layout : 'form',
														items : [{
																	xtype : 'textfield',
																	labelAlign : 'left',
																	fieldLabel : '送存账号名称<span style="color:red">*</span>',
																	name : 'namfdskf',
																	readOnly:true,
																	id:'checkUserName',
																	maxLength:12,
																	allowBlank : false,
																	anchor : '95%'
																},{
																	xtype : 'datefield',
													    			id : 'todepositDate',
													    			name : 'todepositDate',
													    			fieldLabel : '送存日期',
													    			value:new Date(),
														    		format : 'Y-m-d H:m:s',
														    		readOnly:true,
														    		anchor : '95%',
														    		listeners : {
														    			'render' : function() {
														    		     }
													    		    }
																}]
													}]
													
										}]
										});
			
		
		var win = new Ext.Window({
			title : "支票送存银行",
			width : 600,
			closeAction : 'hide',
			plain : true,
		    resizable : false,	
			modal : true,
			items : form,
			buttonAlign : "center",	buttons : [{
				text : "保存",
				id:'savebtn',
				iconCls : 'groupSave',
				handler : function() {
					if (form.getForm().isValid()) {
						this.disabled = true;//只能点击一次
						form.getForm().submit({
							url : sysPath+ '/fi/fiCheckAction!save.action',
							params:{
								privilege:privilege,
								todepositStatus:1
							},
							waitMsg : '正在保存数据...',
							success : function(form, action) {
										win.hide(), 
										Ext.Msg.alert(alertTitle,action.result.msg, function() {
												dataStore.reload();
										});
							},
							failure : function(form, action) {
								if (action.faliureType == Ext.form.Action.SERVER_INVALID) {
									Ext.Msg.alert(alertTitle, "服务器连接异常，请联系管理员！");
								}else{
									if (action.result.msg) {
										win.hide();
										Ext.Msg.alert(alertTitle,action.result.msg, function() {
												dataStore.reload();
										});
									}
								}
							}
						});
					}
				}
			},{text : "取消",
			   handler : function(){win.close();}
			}]
		});
		win.on('hide',function(){form.destroy();});
		win.show();
		
	Ext.getCmp("paymentAccountNo").on('expand',function(){  
		if(paymentGrid.rendered){
			paymentGrid.doLayout();
		}else{
   			paymentGrid.render('paymentpanel');
   			paymentdateStore.load();
   		}
    });
    
	Ext.getCmp("paymentAccountNo").on('collapse',function(){
    	return false;
    }); 
}

	// 到账确认
	function checkAudit(record){
		Ext.Msg.confirm(alertTitle,'您确定要到账确认吗?',function(btnYes) {
			if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
				form1.getForm().doAction('submit', {
					url:sysPath+"/fi/fiCheckAction!checkAudit.action",
					method : 'post',
					params : {
						id : record.get('id'),
						reachUser:null,
						reachDate:null,
						reachStatus:0,
						ts:record.get('ts'),
						privilege:privilege
   					},
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
	
	function outCheck(record){
		Ext.Msg.confirm(alertTitle,'您确定要这条件数据退票吗?',function(btnYes) {
			if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
				form1.getForm().doAction('submit', {
					url:sysPath+"/fi/fiCheckAction!save.action",
					method : 'post',
					params : {
						id : record.get('id'),
						reachUser:null,
						reachDate:null,
						reachStatus:0,
						invalidUser:userName,             //作废
						invalidDate:new Date(),
						invalidStatus:1,
						reviewUser:null,           // 审核
						reviewDate:null,
						reviewStatus:0,
						submitUser:null,         // 上缴
						submitDate:null,
						submitStatus:0,
						reachUser:null,            // 点到
						reachDate:null,
						reachStatus:0,
						todepositUser:null,                 //送存
						todepositDate:null,
						todepositStatus:0,
						todepositFiCapitaaccountset:null,
						confirmUser:null,                 ///到账确认人
						confirmDate:null,
						confirmStatus:0,
						returnUser:userName,                      //退票
						returnDate:new Date(),
						returnStatus:1,
						ts:record.get('ts'),
						privilege:privilege
   					},
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
	
	function deleteData(record){
		Ext.Msg.confirm(alertTitle,'你确定要作废这条数据吗？',function(btnYes) {
			if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
				form1.getForm().doAction('submit', {
					url:sysPath+"/fi/fiCheckAction!deleteByStatus.action",
					method : 'post',
					params : {
						id : record.get('id'),
						ts:record.get('ts'),
						privilege:privilege
   					},
					waitMsg : '正在处理数据...',
					success : function(form1, action) {
							Ext.Msg.alert(alertTitle,"数据作废成功",function(){
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
	
	function showExcel(){
  		var node=new Ext.tree.TreeNode({id:'keel_'+(Math.random()*10+1),leaf :false,text:"账单对账"});
      	node.attributes={href1:'fi/fiDeliverycostAction!sexcel.action'};
        parent.toAddTabPage(node,true);
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



    	
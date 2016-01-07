	Ext.QuickTips.init();
	var privilege=136;
	var comboxPage=50;
	var departGridSearchUrl = "sys/departAction!findAll.action";
	var saveUrl="fi/fiAdvanceAction!save.action";
	var customerGridSearchUrl="sys/customerAction!list.action";
	var areaListUrl="stock/oprStoreAreaAction!list.action";
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
	{name:'dno',mapping:'T0_D_NO'},
	{name:'cpName',mapping:'T0_CP_NAME'},
	{name:'status',mapping:'T3_STATUS'},
	{name:'payStatus',mapping:'T3_PAY_STATUS'},
	{name:'departName',mapping:'T3_DEPART_NAME'},
	{name:'departId',mapping:'T3_DEPART_ID'},
	{name:'reviewUser',mapping:'T3_REVIEW_USER'},
	{name:'reviewDate',mapping:'T3_REVIEW_DATE'},
	{name:'reviewRemark',mapping:'T3_REVIEW_REMARK'},
	{name:'ts',mapping:'T3_TS'},
	{name:'createDepartId',mapping:'T0_IN_DEPART_ID'},
	{name:'createDepartName',mapping:'T0_IN_DEPART'},
	                                             {name:'gowhere',mapping:'T0_GOWHERE'},
	{name:'distributionMode',mapping:'T0_DISTRIBUTION_MODE'},
	{name:'takeMode',mapping:'T0_TAKE_MODE'},
	{name:'cusDepartName',mapping:'T0_CUS_DEPART_NAME'},
	{name:'cusDepartCode',mapping:'T0_CUS_DEPART_CODE'},
	
	{name:'addr',mapping:'T0_ADDR'},
	{name:'batchNo',mapping:'BATCHNO'},
	{name:'cqWeight',mapping:'T0_CQ_WEIGHT'},
	
	{name:'areaRank',mapping:'T0_AREA_RANK'},
	{name:'piece',mapping:'T0_PIECE'},
	{name:'cpFee',mapping:'T0_CP_FEE'},
	
	{name:'tid',mapping:'T3_ID'},
	{name:'deficitFee',mapping:'DEFICITFEE'},
	{name:'consigneeFee',mapping:'T0_CONSIGNEE_FEE'},
	{name:'totalFee',mapping:'TOTALFEE'},
	
	{name:'filghtMainNo',mapping:'T0_FLIGHT_MAIN_NO'},
	{name:'conginee',mapping:'T0_CONSIGNEE'},
	
	{name:'subNo',mapping:'T0_SUB_NO'},
	{name:'traFee',mapping:'T0_TRA_FEE'},
	{name:'feeAuditStatus',mapping:'T2_FEE_AUDIT_STATUS'},
	{name:'confirmStatus',mapping:'T1_CONFIRM_STATUS'},
	{name:'cashStatus',mapping:'T2_CASH_STATUS'},
	                                             {name:'gowhereId',mapping:'T0_GOWHERE_ID'},
	{name:'createTime',mapping:'T0_CREATE_TIME'},
	
	{name:'amount',mapping:'T3_AMOUNT'},
	{name:'sourceData',mapping:'T3_SOURCE_DATA'},
	//{name:'customerId',mapping:'T3_CUSTOMER_ID'},
	//{name:'customerName',mapping:'T3_CUSTOMER_NAME'}
	];
    			
    var jsonread= new Ext.data.JsonReader({
                    root:'resultMap',
                    totalProperty:'totalCount'},
                    fields);
                              
	var sm = new Ext.grid.CheckboxSelectionModel({
		width:25,
	//	singleSelect :true,
		moveEditorOnEnter:true, 
		id:'checkId',
		name:'checkId',
		listeners : {
			selectionchange:function(){
				setTotalCount();
			}
		}
	});
	
	function setTotalCount(){
		var vnetmusicRecord = recordGrid.getSelectionModel().getSelections();
				var cost=0;
				var returnCost=0;
					
				for(var j=0;j<vnetmusicRecord.length;j++){;
					if(vnetmusicRecord[j].data.sourceData=='返货登记'){
						returnCost+=parseFloat(vnetmusicRecord[j].data.traFee);
			 		}
			 		
					cost+=parseFloat(vnetmusicRecord[j].data.traFee);
			 	}
			 	Ext.getCmp('count').setValue(vnetmusicRecord.length);
			 	
			 	Ext.getCmp('totalCost').setValue(cost);
			 	Ext.getCmp('totalReturnCost').setValue(returnCost);
			 	 
	}
	
	var myDate=new Date()
	myDate.setDate(1)
	
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
	
		//权限部门
	var rightDepartStore2 = new Ext.data.Store({ 
            storeId:"rightDepartStore2",                        
            proxy: new Ext.data.HttpProxy({url:sysPath+"/sys/basRightDepartAction!findDepartName.action",method:'post'}),
            baseParams:{
            	filter_EQL_userId:userId
            },
            reader: new Ext.data.JsonReader({
            root: 'resultMap', totalProperty: 'totalCount'
           }, [{name:'departName', mapping:'DEPARTNAME',type:'string'},
               {name:'departId', mapping:'RIGHTDEPARTID',type:'string'}             
              ])    
    });
	
	// 客商列表
	customerStore = new Ext.data.Store({
		storeId : "customerStore",
		proxy : new Ext.data.HttpProxy({
					url : sysPath + "/" + customerGridSearchUrl,
					method:'post'
				}),
		baseParams:{
			filter_EQS_custprop:'中转',
			filter_EQL_status:1},
		reader:new Ext.data.JsonReader({
					root : 'result',
					totalProperty : 'totalCount'
				}, [{name : 'cusName',mapping : 'cusName'
					},{name : 'id',mapping : 'id'}])
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
  	  	data:[['0','未确收'],
  	  		  ['1','已确收'],
  	  		  ['2','确收异常']],
   		fields:["id","name"]
	});
	
	var auditStatus=new Ext.data.SimpleStore({
		auteLoad:true, //此处设置为自动加载
  	  	data:[
  	  		  ['0','全部'],
  	  		  ['-1','未付款'],
  	  		  ['1','已付款']
  	  		  ],
   		fields:["id","name"]
	});
	
	var sourceDataStore=new Ext.data.SimpleStore({
		auteLoad:true, //此处设置为自动加载
  	  	data:[['1','手工录入'],['2','收付款单']],
   		fields:["id","name"]
	});
	
	var fiStatusDataStore=new Ext.data.SimpleStore({
		auteLoad:true, //此处设置为自动加载
  	  	data:[['1','已收银'],['0','未收银']],
   		fields:["id","name"]
	});
	
	var fiPayStatusStore=new Ext.data.SimpleStore({
		auteLoad:true, //此处设置为自动加载
  	  	data:[['1','已支付'],['0','未支付']],
   		fields:["id","name"]
	});
	
			//
	var dataStatus=new Ext.data.SimpleStore({
			 auteLoad:true, //此处设置为自动加载
  			 data:[['1','传真日期'],['2','付款日期']],
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
         proxy: new Ext.data.HttpProxy({url:sysPath+"/fi/fiTransitcostAcion!noAuditList.action"}),
         baseParams:{
         	limit: pageSize
         },
         reader:jsonread,
         sortInfo:{field:'dno',direction:'DESC'}
    });
 	
	var copyDataStore =  new Ext.data.Store({
         proxy: new Ext.data.HttpProxy({url:sysPath+"/fi/fiTransitcostAcion!noAuditList.action"}),
         reader:jsonread
    });
 	
 	var fourbar = new Ext.Toolbar({	frame : true,
		items : ['&nbsp;','<B>统计信息</B>','&nbsp;','-','&nbsp;'
				,'总票数:',
		    	{xtype : 'numberfield',
                id:'count',
   			    name: 'count',
   			    readOnly:true,
   			    maxLength : 10,
                width:49,
                value:0,
                enableKeyEvents:true
              },'&nbsp;'
				,'总金额:',
		    	{xtype : 'numberfield',
                fieldLabel: '总金额',
                id:'totalCost',
   			    name: 'totalCost',
   			    readOnly:true,
   			    maxLength : 10,
                width:49,
                 value:0,
                enableKeyEvents:true
              },'&nbsp;','返货金额:',
		    	{xtype : 'numberfield',
   	            id:'totalReturnCost',
   			    name: 'totalReturnCost',
   			    readOnly:true,
   			    maxLength : 10,
   			    value:0,
                width:49,
                enableKeyEvents:true
              },'-','&nbsp;','<B>单票添加</B>','&nbsp;','配送单号:',
							
					{xtype : 'numberfield',
   	             //   labelAlign : 'right',
   	                id:'oprdno',
	                width:65,
	                allowNegative:false,
	                enableKeyEvents:true,
					listeners : {
				 		keyup:function(textField, e){
			               if(e.getKey() == 13){
			                    addOneStore();
			               }
				 		}
		 			}
	              },'&nbsp;',
				{
					text : '<B>单票添加</B>',
					tooltip : '单票添加',
					iconCls:'groupAdd',
					handler : addOneStore
				},'&nbsp;',{
					text : '<B>页面删除</B>',
					iconCls:'groupClose',
					handler : deleteRecordStore
				},{
    			xtype:'label',
    			id:'showMsg2',
    			width:200
    		}]
		});
 	
 	var twobar = new Ext.Toolbar({
		    	items : ['&nbsp;','创建部门:',{
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
					    },'&nbsp;',	{
				xtype : 'combo',
				id:'dateType',
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
	          		 	com.setValue('传真日期');
	          		 }
	 			}
	    	},':',
						 {
							xtype : 'datefield',
			    			id : 'startDate',
				    		format : 'Y-m-d',
				    		emptyText : "选择开始时间",
				    		anchor : '95%',
				    		value:myDate,
				    		width : 80,
				    		selectOnFocus:true,
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
				    		value:new Date(),
				    		selectOnFocus:true,
				    		format : 'Y-m-d',
				    		emptyText : "选择结束时间",
				    		width : 80,
				    		anchor : '95%'
						},/*'<B>部门:</B>',  //
							{
							xtype : 'combo',
							id:'comboTypeDepart',
							triggerAction : 'all',
							store : menuStore,
							width : 100,
							listWidth:245,
							minChars : 1,
							allowBlank : true,
							emptyText : "请选择创建部门名称",
							forceSelection : true,
							editable : true,
							pageSize:comboxPage,
							displayField : 'departName',//显示值，与fields对应
							valueField : 'departId',//value值，与fields对应
							name : 'departId',
							anchor : '100%'
					    },*/'-','&nbsp;','回单状态:',{
				xtype : 'combo',
				typeAhead : false,
				forceSelection : true,
				width : 80,
				id:'danStatus',
				mode:'local',
				selectOnFocus:true,
				triggerAction : 'all',
				store : statusDataStore,
				displayField : 'name',
				valueField : 'id',
				emptyText : "请选择回单状态",
				enableKeyEvents:true,
	            listeners : {
	 				keyup:function(textField, e){
	                     if(e.getKey() == 13){
	                     	searchLog();
	                     }
	 				}
	 			}
					
			},'-','&nbsp;&nbsp;',{
                xtype : "combo",
    			id:"comboselect",
   				width : 70,
 				triggerAction : 'all',
    			model : 'local',
    			hiddenId : 'checkItems',
    			hiddenName : 'checkItems',
    			name : 'checkItemstext',
    			store : [['', '查询全部'],
    					['cashStatus', '收银状态'],
    					['payStatus', '支付状态'],  //fiPayStatusStore
    					['dno', '配送单号'],
    					['batchNo','批次号'],
    					['goWhere', '供应商']],
    			emptyText : '选择查询类型',
    			forceSelection : true,
    			listeners : {
    				'select' : function(combo, record, index) { 
    					if (combo.getValue() == '') {
    						Ext.getCmp("itemsValue").disable();
    						Ext.getCmp("itemsValue").setValue("");
    						Ext.getCmp("itemsValue").show();
    						
    						
    						Ext.getCmp("fiStatus").disable();
    						Ext.getCmp("fiStatus").hide();
    						Ext.getCmp("fiStatus").setValue("");
    						
    						Ext.getCmp("comboCus").disable();
    						Ext.getCmp("comboCus").hide();
    						Ext.getCmp("comboCus").setValue("");
    						
    						Ext.getCmp("fiPayStatusSelect").disable();
    						Ext.getCmp("fiPayStatusSelect").hide();
    						Ext.getCmp("fiPayStatusSelect").setValue("");
    						
    					}else if(combo.getValue() == 'cashStatus'){
    						Ext.getCmp("itemsValue").disable();
    						Ext.getCmp("itemsValue").setValue("");
    						Ext.getCmp("itemsValue").hide();
    						
    						
    						Ext.getCmp("comboCus").disable();
    						Ext.getCmp("comboCus").hide();
    						Ext.getCmp("comboCus").setValue("");
    						
    						Ext.getCmp("fiStatus").enable();
    						Ext.getCmp("fiStatus").show();
    						Ext.getCmp("fiStatus").setValue();
    						
							Ext.getCmp("fiPayStatusSelect").disable();
    						Ext.getCmp("fiPayStatusSelect").hide();
    						Ext.getCmp("fiPayStatusSelect").setValue("");
    					}else if (combo.getValue() == 'goWhere') {
    						Ext.getCmp("itemsValue").disable();
    						Ext.getCmp("itemsValue").setValue("");
    						Ext.getCmp("itemsValue").hide();
    						
    						
    						Ext.getCmp("fiStatus").disable();
    						Ext.getCmp("fiStatus").hide();
    						Ext.getCmp("fiStatus").setValue("");
    						
    						Ext.getCmp("comboCus").enable();
    						Ext.getCmp("comboCus").show();
    						Ext.getCmp("comboCus").setValue();
    						
    						Ext.getCmp("fiPayStatusSelect").disable();
    						Ext.getCmp("fiPayStatusSelect").hide();
    						Ext.getCmp("fiPayStatusSelect").setValue("");
    					}else if (combo.getValue() == 'payStatus'){  //
    						Ext.getCmp("fiStatus").disable();
    						Ext.getCmp("fiStatus").hide();
    						Ext.getCmp("fiStatus").setValue("");
    						
    				    	Ext.getCmp("itemsValue").hide();
    						Ext.getCmp("itemsValue").disable();
    						Ext.getCmp("fiStatus").setValue("");
    						
    						Ext.getCmp("fiPayStatusSelect").enable();
    						Ext.getCmp("fiPayStatusSelect").show();
    						
    						Ext.getCmp("comboCus").disable();
    						Ext.getCmp("comboCus").hide();
    						Ext.getCmp("comboCus").setValue("");
    					}else {  //
    						Ext.getCmp("fiStatus").disable();
    						Ext.getCmp("fiStatus").hide();
    						Ext.getCmp("fiStatus").setValue("");
    						
    				    	Ext.getCmp("itemsValue").enable();
    						Ext.getCmp("itemsValue").show();
    						
    						Ext.getCmp("fiPayStatusSelect").disable();
    						Ext.getCmp("fiPayStatusSelect").hide();
    						Ext.getCmp("fiPayStatusSelect").setValue("");
    						
    						Ext.getCmp("comboCus").disable();
    						Ext.getCmp("comboCus").hide();
    						Ext.getCmp("comboCus").setValue("");
    					}
    				}
    			 }
    		},'-',
			{	xtype:'textfield',
	 	        id : 'itemsValue',
	 	        width : 70,
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
				width : 70,
				id:'fiStatus',
				hidden:true,
				diabled:true,
				mode:'local',
				triggerAction : 'all',
				store : fiStatusDataStore,
				displayField : 'name',
				valueField : 'id',
				emptyText : "请选择收银状态"
					
			},{
				xtype : 'combo',
				typeAhead : false,
				forceSelection : true,
				width : 70,
				id:'fiPayStatusSelect',
				hidden:true,
				diabled:true,
				mode:'local',
				triggerAction : 'all',
				store : fiPayStatusStore,
				displayField : 'name',
				valueField : 'id',
				emptyText : "请选择付款状态"
					
			},{
				xtype : 'combo',
				id:'comboCus',
				triggerAction : 'all',
				store : customerStore,
				width : 70,
				queryParam:'filter_LIKES_cusName',
				listWidth:245,
				hidden:true,
				disabled:true,
				minChars : 1,
				allowBlank : true,
				emptyText : "请选择供应商名称",
				forceSelection : true,
				editable : true,
				pageSize:comboxPage,
				displayField : 'cusName',//显示值，与fields对应
				valueField : 'id',//value值，与fields对应
				name : 'cusName'
			}]
		});
     
	var recordGrid=new Ext.grid.EditorGridPanel({
		renderTo:Ext.getBody(),
	//	el : 'recordGrid',
		id:'userCenter',
		height : Ext.lib.Dom.getViewHeight()-5,
		width : Ext.lib.Dom.getViewWidth()-5,
		autoScroll : true,
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
			{
				text : '<b>备用金付款</b>',
				iconCls : 'groupPass',
				id : 'fiAduit',
				disabled:false,
				handler : function(){
					var vnetmusicRecord = recordGrid.getSelectionModel().getSelections();
					if (vnetmusicRecord.length > 1){
						var cusId =vnetmusicRecord[0].get('gowhereId');
						for(var i=0;i<vnetmusicRecord.length;i++){
							if(cusId!=vnetmusicRecord[i].get('gowhereId')){
								Ext.Msg.alert(alertTitle,"只能选择同一中转客商进行付款",function(){});
								return ;						
							}
							
							if('返货登记'==vnetmusicRecord[i].get('sourceData')||'更改申请'==vnetmusicRecord[i].get('sourceData')){
								Ext.Msg.alert(alertTitle,"只有传真录入的数据才能进行多条数据一起付款",function(){});
								return ;	
							}
							
							if(vnetmusicRecord[i].data.confirmStatus!='1'){
				    			Ext.Msg.alert(alertTitle,"回单状态不是已确收状态，不能进行付款",function(){});
				    			return;
				    		}
				    		
							if(vnetmusicRecord[i].data.status=='1'){
								Ext.Msg.alert(alertTitle,"存在已付款的数据，不能重复付款",function(){});
								return;
							}
						}
						outCostIn(vnetmusicRecord);
					
					}else if(vnetmusicRecord.length == 1){
						if(vnetmusicRecord[0].get('sourceData')=='返货登记'){
							if(vnetmusicRecord[0].data.status=='1'){
				    			Ext.Msg.alert(alertTitle,"已付款，不能重复付款",function(){});
				    			return;
				    		}
							returnGoods(vnetmusicRecord[0]);
						}else if(vnetmusicRecord[0].get('sourceData')=='更改申请'){
							Ext.Msg.alert(alertTitle,"更改申请不用付款",function(){});
							return;
						}else{
							if(vnetmusicRecord[0].data.status=='1'){
				    			Ext.Msg.alert(alertTitle,"已审核，不能重复付款",function(){});
				    			return;
				    		}
				    		
				    		if(vnetmusicRecord[0].data.confirmStatus!='1'){
				    			Ext.Msg.alert(alertTitle,"回单状态不是回单确收状态，不能进行付款",function(){});
				    			return;
				    		}
				    		
							outCostIn(vnetmusicRecord);
						}

						
					}else{
						Ext.Msg.alert(alertTitle,"请选择您要付款的数据",function(){});
					}
				}
			},'-','&nbsp;',{
				text : '<b>撤销付款</b>',
				iconCls : 'groupNotPass',
				id : 'qxfiAduit',
				handler : function(){
					var vnetmusicRecord = recordGrid.getSelectionModel().getSelections();
					if (vnetmusicRecord.length != 1) {
						Ext.Msg.alert(alertTitle,"请选择一条记录然后再操作撤销",function(){
						});
					}else{
						if(vnetmusicRecord[0].get('status')==1){
							if(vnetmusicRecord[0].get('payStatus')==1){
								Ext.Msg.alert(alertTitle,"费用已支付，不能撤销",function(){
								});
							}else{
								qxFiAudit(vnetmusicRecord[0]);
							}
						}else{
							Ext.Msg.alert(alertTitle,"只有付款过的数据，才能进行撤销",function(){
							});
						}
						
					}
				}
			},'-','&nbsp;',{
				text : '<b>导出</b>',
				iconCls : 'sort_down',
				id : 'exportbtn',
				handler : exportinfo
			},'-','&nbsp;',
		        	{text:'<b>打印</b>',iconCls:'printBtn',
						handler:function() {
							var records = recordGrid.getSelectionModel().getSelections();
							if(records.length==0){
								Ext.Msg.alert(alertTitle,"请选择相应的数据才能打印");
								return;
							}
							var flag=true;
							var ids = "";
							for(var i = 0; i < records.length; i++){
							
								ids += records[i].data.tid + ",";
								if(records[i].data.status!='1'){
									flag=false;
								}
							}
							if(!flag){
								Ext.Msg.alert(alertTitle,"包含未付款的数据，不能打印");
								return;
							}
							printinfo(ids);
			        } },'&nbsp;',
			        
			        {
			     text : '<b>查询</b>',
			     iconCls : 'btnSearch',
			     id : 'btn',
				 handler : searchLog
			},'&nbsp;','&nbsp;','客服部门:',{
			   xtype : 'combo',
			   id:'serviceDepartId',
     		   fieldLabel: '客服部门',
	           queryParam : 'filter_LIKES_departName',
		       minChars : 1,
		       width : 85,
		       triggerAction : 'all',
		       forceSelection : true,
			   store: serviceDepartStore,
			   pageSize : 50,
			   listWidth:245,
			   displayField : 'departName',
			   valueField : 'departId',
			   name : 'olist[0].serviceDepartId',
			   anchor : '95%',
			   emptyText : "请选择客服部门名称"
     		},'-','&nbsp;&nbsp;','付款状态:',{
				xtype : 'combo',
				typeAhead : false,
				forceSelection : true,
				width : 70,
				allowBlank : false,
				editable : false,
				id:'auditStatus',
				mode:'local',
				triggerAction : 'all',
				store : auditStatus,
				displayField : 'name',
				valueField : 'id',
				enableKeyEvents:true,
	            listeners : {
	            	render:function(v){
	            		v.setValue(0);
	            	},
	 				keyup:function(textField, e){
	                     if(e.getKey() == 13){
	                     	searchLog();
	                     }
	 				}
	 			}
					
			},'&nbsp;',{
    			xtype:'label',
    			id:'showMsg',
    			width:200
    		}
			],
			columns:[ rownum,sm,
			        {header: '配送单号', dataIndex: 'dno',width:80,sortable : true},
			        {header: 'tid', dataIndex: 'tid',width:60,hidden:true,sortable : true},
			        {header: '中转供应商ID', dataIndex: 'gowhereId',hidden:true,width:50,sortable : true},
					{header: '中转供应商', dataIndex: 'gowhere',width:70,sortable : true},
					{header: '中转费', dataIndex: 'traFee',width:70,sortable : true,
					renderer:function(v){
						if(v==0){
							return 0;
						}else{
							return '<span style="color:red">'+v+'</span>';
						}
					}},
					{header: '数据来源', dataIndex: 'sourceData',width:90,sortable : true,renderer:function(v){
		        		if(v==''||v==null){ 
		        			return '传真录入';
		        		}else {
		        			return v;
		        		}
		        	}},
					{header: '备注', dataIndex: 'reviewRemark',width:120,sortable : true},
			        {header: '收银状态', dataIndex: 'cashStatus',width:80,sortable : true,renderer:function(v){
			        		if(v=='1'){ 
			        			return '已收银';
			        		}else if(v=='0'){
			        			return '未收银';
			        		}else {
			        			return '';
			        		}
			        	}
			        },
			        
			        {header: '回单确收状态', dataIndex: 'confirmStatus',width:80,sortable : true,renderer:function(v){
			        		if(v=='1'){ 
			        			return '已确收';
			        		}else if(v=='0'){
			        			return '未确收';
			        		}else if(v=='2'){
			        			return '确收异常';
			        		}else{
			        			return '';
			        		}
			        	}},
			        {header: '付款状态', dataIndex: 'status',width:60,sortable : true,renderer:function(v){
			        		if(v=='1'){ 
			        			return '已付款';
			        		}else {
			        			return '未付款';
			        		}
			        	}},
			        {header: '支付状态', dataIndex: 'payStatus',width:60,sortable : true,renderer:function(v){
			        		if(v=='1'){ 
			        			return '已支付';
			        		}else if(v=='0'){
			        			return '未支付';
			        		}else {
			        			return '';
			        		}
			        	}},　
					{header: '预付提送费', dataIndex: 'cpFee',width:80,sortable : true},
					{header: '到付提送费', dataIndex: 'consigneeFee',width:80,sortable : true},
					{header: '合计', dataIndex: 'totalFee',width:55,sortable : true},
					{header: '毛利', dataIndex: 'deficitFee',width:70,sortable : true},
			        {header: '主单号', dataIndex: 'filghtMainNo',width:80,sortable : true},
			        {header: '分单号', dataIndex: 'subNo',width:80,sortable : true},
			        {header: '收货人', dataIndex: 'conginee',width:80,sortable : true},
			        {header: '发货代理', dataIndex: 'cpName',width:70,sortable : true},
			        {header: '重量', dataIndex: 'cqWeight',width:80,sortable : true},
			        {header: '地区等级', dataIndex: 'areaRank',width:80,sortable : true},
			        {header: '收货人地址', dataIndex: 'addr',width:160,sortable : true},
					{header: '配送方式', dataIndex: 'distributionMode',width:60,sortable : true},
					{header: '提货方式', dataIndex: 'takeMode',width:90,sortable : true},
					{header: '客服部门编码', dataIndex: 'cusDepartCode',width:90,sortable : true},
					{header: '客服部门', dataIndex: 'cusDepartName',width:110,sortable : true},
					
//					{header: '返货成本金额', dataIndex: 'amount',width:90,sortable : true},
//					{header: '返货供应商', dataIndex: 'customerName',width:90,sortable : true},

			        {header: '批次号', dataIndex: 'batchNo',width:60,sortable : true},
			        {header: '审核人', dataIndex: 'reviewUser',width:60,sortable : true},
			        {header: '审核时间', dataIndex: 'reviewDate',width:60,sortable : true},　

			        {header: '时间戳', dataIndex: 'ts',width:55,hidden:true,sortable : true}
			    ],
			store : dataStore,
			listeners: {
                    render: function(){
                        twobar.render(this.tbar);
                        fourbar.render(this.tbar);
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
		Ext.getCmp('showMsg2').getEl().update('');
		var comboRightDepart2 =Ext.getCmp('comboRightDepart2').validate();
		if(!comboRightDepart2){
			return;
		}
	
    	var start='';
    	var end ='';
    	var dateType=Ext.getCmp('dateType').getValue();
		var dId= Ext.getCmp('comboRightDepart2').getValue();
	    	if(Ext.getCmp('startDate').getValue()!=""){
	    		var start = Ext.getCmp('startDate').getValue().format("Y-m-d");
	    	}
	    	if(Ext.getCmp('endDate').getValue()!=""){
	    		var end = Ext.getCmp('endDate').getValue().format("Y-m-d");
	    	}
	    
	    	if(Ext.getCmp('comboselect').getValue()==''){
			 	Ext.apply(dataStore.baseParams={
					startDate : start,
	            	endDate : end,
	            	dateType:dateType,
	            	departId: dId,
	            	fiAuditStatus:Ext.getCmp('auditStatus').getValue(),
	            	serviceDepartCode:Ext.getCmp('serviceDepartId').getValue(),
	           		confirmStatus : Ext.getCmp('danStatus').getValue()
	   			});
	    	}else if(Ext.getCmp('comboselect').getValue()=='cashStatus'){
	    		Ext.apply(dataStore.baseParams={
					startDate : start,
	            	endDate : end,
	            	departId: dId,
	            	dateType:dateType,
	            	fiAuditStatus:Ext.getCmp('auditStatus').getValue(),
	            	checkItems:'cashStatus',
	            	serviceDepartCode:Ext.getCmp('serviceDepartId').getValue(),
	            	itemsValue:Ext.getCmp('fiStatus').getValue(),
	            	confirmStatus : Ext.getCmp('danStatus').getValue()
	   			});
	    	}else if(Ext.getCmp('comboselect').getValue()=='payStatus'){
	    		Ext.apply(dataStore.baseParams={
					startDate : start,
	            	endDate : end,
	            	departId: dId,
	            	dateType:dateType,
	            	fiAuditStatus:Ext.getCmp('auditStatus').getValue(),
	            	checkItems:'payStatus',
	            	serviceDepartCode:Ext.getCmp('serviceDepartId').getValue(),
	            	itemsValue:Ext.getCmp('fiPayStatusSelect').getValue(),
	            	confirmStatus : Ext.getCmp('danStatus').getValue()
	   			});
	    	}else if(Ext.getCmp('comboselect').getValue()=='goWhere'){
	    		Ext.apply(dataStore.baseParams={
	    			startDate : start,
	            	endDate : end,
	            	departId: dId,
	            	dateType:dateType,
	            	fiAuditStatus:Ext.getCmp('auditStatus').getValue(),
	            	checkItems:'goWhere',
	            	serviceDepartCode:Ext.getCmp('serviceDepartId').getValue(),
	            	itemsValue:Ext.getCmp('comboCus').getValue(),
	            	confirmStatus : Ext.getCmp('danStatus').getValue()
	   			});
	    	}else if(Ext.getCmp('comboselect').getValue()=='batchNo'){
	    		Ext.apply(dataStore.baseParams={
	    			startDate : start,
	            	endDate : end,
	            	departId: dId,
	            	dateType:dateType,
	            	fiAuditStatus:Ext.getCmp('auditStatus').getValue(),
	            	checkItems:'batchNo',
	            	serviceDepartCode:Ext.getCmp('serviceDepartId').getValue(),
	            	itemsValue:Ext.getCmp('itemsValue').getValue(),
	            	confirmStatus : Ext.getCmp('danStatus').getValue()
	   			});
	    	}else{  
	    		var regex=/^[1-9]\d*$/;
	    		var dNo=Ext.getCmp('itemsValue').getValue();
	    		if(dNo!=''){
				    if(!regex.test(dNo)){
				       	Ext.Msg.alert(alertTitle,"配送单号输入格式不正确，无法查询",function(){
					       	Ext.getCmp('itemsValue').focus();	
					       	Ext.getCmp('itemsValue').markInvalid("配送单号输入格式不正确");
							Ext.getCmp('itemsValue').selectText();	
				       	});
				       	
						return;
				    }
	    		}
			    
	    		Ext.apply(dataStore.baseParams={
	            	departId: dId,
	            	fiAuditStatus:Ext.getCmp('auditStatus').getValue(),
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
		
	function qxFiAudit(record){
		if(record.get('sourceData')=='传真录入'||record.get('sourceData')=='更改申请'){
			Ext.Ajax.request({
				url:sysPath+"/fi/fiTransitcostAcion!qxAmountCheck.action",
				params : {
					id:record.get('tid')
				},
				success : function(response){
				    Ext.Msg.confirm(alertTitle,response.responseText+'请确定！<span  style="color:red">您确认要撤销付款吗?</span>',function(btnYes) {
				    	if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
							fnQxAudit(record);
						}
					});
				},
				failure : function(response) {
					Ext.Msg.alert(alertTitle,"信息提示出错，无法提交");
				}
			});
		}else if(record.get('sourceData')=='返货登记'){
			var res="撤销中转成本总计<span  style='color:red'> 1 </span>行,"+
			"金额<span  style='color:red'> "+record.data.traFee+" </span>元";
			Ext.Msg.confirm(alertTitle,res+'请确定！<span  style="color:red">您确实这些数据要付款吗?</span>',function(btnYes) {
		    	if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
					fnQxAudit(record);
				}
			});
		}
		
	}
		
	function fnQxAudit(record){
		form1.getForm().doAction('submit', {
			url : sysPath+ "/fi/fiTransitcostAcion!qxFiAudit.action",
			method : 'post',
			params : {
					ts : record.get('ts'),
					sourceData:record.get('sourceData'),
					id:record.get('tid'),
					privilege:privilege
			},
			waitMsg : '正在处理数据...',
			success : function(form1, action) {
				Ext.Msg.alert(alertTitle,action.result.msg,
						function() {
							dataStore.reload()
						});
			},
			failure : function(form1, action) {
				Ext.Msg.alert(alertTitle,action.result.msg,
						function() {
							dataStore.reload();
						});
			}
		});			
	}
	
	function addOneStore(){
			var oprdno = Ext.getCmp('oprdno').getValue()
			Ext.getCmp('showMsg2').getEl().update('');
			
			if(oprdno==""){
			    Ext.getCmp('oprdno').focus();	
				Ext.getCmp('oprdno').markInvalid("内容不能为空");
		    	
		    	return;
		    }
			//迭代保存已有记录
			if(dataStore.getCount()!=0){
				for(i=0;i<dataStore.getCount();i++){
					if(dataStore.getAt(i).get('dno')==oprdno){ 
						recordGrid.getSelectionModel().selectRow(i);
						Ext.getCmp('oprdno').selectText();
						return;
					}
				}
			}
			
			copyDataStore.reload({
				params : {
					start : 0,
					privilege:privilege,
					departId:Ext.getCmp('comboRightDepart2').getValue(),
					checkItems:'dno',
					fiAuditStatus:0,
					itemsValue :oprdno,
					limit : pageSize
				},callback:function(re){
					if(re.length!=0){
						for(var i=0;i<re.length;i++){
							dataStore.add(copyDataStore.getAt(i));
						}
					}else{
						Ext.getCmp('showMsg2').getEl().update('<span style="color:red" >没有找到这票货的中转成本数据。</span>');
						Ext.getCmp('oprdno').selectText();
					}
				}
			});
			
			Ext.getCmp('oprdno').selectText();
	}
	
  	function outCostIn(records) {
		Ext.Msg.confirm(alertTitle,'您确定要付款这行记录吗?',function(btnYes){
			if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
				var ids="";
			
				for(var i=0;i<records.length;i++){
 					if(i==0){
						ids += "aa["+i+"].dno="+records[i].get("dno");
					}else{
						ids += "&aa["+i+"].dno="+records[i].get("dno");
					}
				}	
				ids+="&privilege="+privilege;
				form1.getForm().doAction('submit', {
					url : sysPath+ "/fi/fiTransitcostAcion!saveOfFax.action",
					method : 'post',
					params :  ids,
					waitMsg : '正在付款数据...',
					success : function(form1, action) {
						Ext.Msg.alert(alertTitle,action.result.msg,
								function() {
									dataStore.reload()
									printinfo(action.result.value);
								});
					},
					failure : function(form1, action) {
						Ext.Msg.alert(alertTitle,action.result.msg);
					}
				});
			}
	});
   }
	
	//返货成本的审核 只能一次审核一条
	function returnGoods(records){
		Ext.Msg.confirm(alertTitle,'您确定要付款这行记录吗?',function(btnYes) {
			if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
		        var ids = "";
				ids += records.data.tid + ",";
				form1.getForm().doAction('submit', {
					url : sysPath+ "/fi/fiTransitcostAcion!saveReturn.action",
					method : 'post',
					params : {
							ts : records.get('ts'),
							sourceData:records.get('sourceData'),
							id:records.get('tid'),
							privilege:privilege
					},
					waitMsg : '正在处理数据...',
					success : function(form1, action) {
						Ext.Msg.alert(alertTitle,action.result.msg,
								function() {
									dataStore.reload();
									printinfo(action.result.value);
								});
					},
					failure : function(form1, action) {
						Ext.Msg.alert(alertTitle,action.result.msg);
					}
				});
			}
		});
	}
	
	function deleteRecordStore(){
		var vnetmusicRecord = recordGrid.getSelectionModel().getSelections();
		for(var i=0;i<vnetmusicRecord.length;i++){
			dataStore.remove(vnetmusicRecord[i]);
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
	function printinfo(ids) {
		parent.print('17',{print_ids:ids});
	}

	setTimeout(function(){
		Ext.getCmp('oprdno').focus();
	},500);

});



    	 



//业务费管理js
var privilege=161;//权限参数
var gridSearchUrl="fi/fiBusinessFeeAction!ralaList.action";//业务费管理权限查询地址
var saveUrl="fi/fiBusinessFeeAction!save.action";//业务费管理保存地址
var searchCustomerUrl='sys/customerAction!list.action';//查询客商地址
var searchCustomerPriceUrl='fi/fiBusinessFeePriceAction!list.action';//查询客商地址
var dictionaryUrl='sys/dictionaryAction!ralaList.action';//数据字典地址
var departUrl='sys/departAction!ralaList.action';//部门查询地址
var countFaxCusGoodsUrl ='fi/fiBusinessFeeAction!countFaxCusGoods.action';//货量统计地址
var auditUrl='fi/fiBusinessFeeAction!audit.action';//业务费管理审核地址
var colWidth=60;
var defaultWidth=60;
var  fields=['id','workflowNo','belongDepartId','belongDepartName','customerId','customerName',
			'remark','collectionAccount','collectionBank','businessType','status','turnover','fiBusinessFeePriceId',
			'rate','departId','departName','createName','createTime','amount','volume','settlement',
			'updateTime','updateName','ts','collectionCustomerId','collectionCustomerName','businessMonth'];
		
			//计算方式
		var settlementStore=new Ext.data.SimpleStore({
			 auteLoad:true, //此处设置为自动加载
	 		 data:[['1','重量'],['0','营业额'],['2','固定金额']],
	  		 fields:["id","name"]
		});
		
				//权限部门
		var rightDepartStore2 = new Ext.data.Store({ 
	            storeId:"rightDepartStore2",                        
	            proxy: new Ext.data.HttpProxy({url:sysPath+"/sys/basRightDepartAction!findDepartName.action",method:'post'}),
	            baseParams:{
	            	privilege:63,
	            	filter_EQL_userId:userId
	            },
	            reader: new Ext.data.JsonReader({
	            root: 'resultMap', totalProperty: 'totalCount'
	           }, [{name:'departName', mapping:'DEPARTNAME',type:'string'},
	               {name:'departId', mapping:'RIGHTDEPARTID',type:'string'}             
	              ])    
	    });
	    
	    //业务部门
	    var bussStore = new Ext.data.Store({ 
            storeId:"bussStore",                        
            proxy: new Ext.data.HttpProxy({url:sysPath+"/sys/departAction!findDepart.action",method:'post'}),
            baseParams:{
               privilege:53,
               filter_EQL_isBussinessDepa:1
            },
            reader: new Ext.data.JsonReader({
                        root: 'resultMap', totalProperty:'totalCount'
                    },[ {name:'bussDepartName', mapping:'DEPARTNAME'},    
                        {name:'bussDepart', mapping: 'DEPARTID'}
            ]),                                      
            sortInfo:{field:'bussDepart',direction:'ASC'}
	     });
	     bussStore.load();
	    
		
		//客商名称
		customerStore= new Ext.data.Store({
			autoLoad:true,
			storeId:"customerStore",
			baseParams:{privilege:61},
			proxy:new Ext.data.HttpProxy({url:sysPath+"/"+searchCustomerUrl}),
			reader:new Ext.data.JsonReader({
	        	root:'result', totalProperty:'totalCount'
	        },[
        	{name:'cusName',mapping:'cusName'},
        	{name:'cusId',mapping:'id'}
        	])
		});
		//业务费类型143
		businessTypeStore	= new Ext.data.Store({
			autoLoad:true,
			storeId:"businessTypeStore",
			baseParams:{filter_EQL_basDictionaryId:143,privilege:16},
			proxy:new Ext.data.HttpProxy({url:sysPath+"/"+dictionaryUrl}),
			reader:new Ext.data.JsonReader({
	                    root:'result',
	                    totalProperty:'totalCount'
	        },[
        	{name:'businessTypeId',mapping:'typeCode'},
        	{name:'businessTypeName',mapping:'typeName'}
        	])
		});
		statusStore=new Ext.data.SimpleStore({
			 autoLoad:true, //此处设置为自动加载
  			  data:[['','全部'],['1','未审核'],['2','已审核'],['0','已作废']],
   			 fields:["statusId","statusName"]
		});
	var tbar=['&nbsp;',{text:'<b>录入</b>',iconCls:'userAdd',id:'basfiBusinessFeeAdd',tooltip : '录入业务管理费',handler:function() {
                	savefiBusinessFee(null);
            } },'-','&nbsp;',
	 	{text:'<b>修改</b>',iconCls:'userEdit',id:'basfiBusinessFeeEdit',disabled:true,tooltip : '修改车辆',handler:function(){
		 	var fiBusinessFee =Ext.getCmp('fiBusinessFeeCenter');
			var _records = fiBusinessFee.getSelectionModel().getSelections();
		
				if (_records.length < 1) {
					Ext.Msg.alert(alertTitle, "请选择您要修改的行！");
					return false;
				} else if (_records.length > 1) {
					Ext.Msg.alert(alertTitle, "一次只能修改一行！");
					return false;
				}
				if (_records[0].data.status == 0) {
					Ext.Msg.alert(alertTitle, "您选择的这条业务费已经作废,不能修改！");
					return false;
				}
				if (_records[0].data.status == 2) {
					Ext.Msg.alert(alertTitle, "您选择的这条业务费已经审核,不能修改！");
					return false;
				}
	 		savefiBusinessFee(_records);}},'-','&nbsp;',
	 	{text:'<b>审核</b>',iconCls:'groupPass',id:'basfiBusinessFeeDelete',disabled:true,handler:function(){
	 		var fiBusinessFee =Ext.getCmp('fiBusinessFeeCenter');
			var _records = fiBusinessFee.getSelectionModel().getSelections();
			if (_records.length < 1) {
				Ext.Msg.alert(alertTitle, "请选择您要审核的行！");
				return false;
			}
			if (_records.length > 1) {
				Ext.Msg.alert(alertTitle, "一次只允许审核一条业务费！");
				return false;
			}
			
			for(var i=0;i<_records.length;i++){
				if (_records[i].data.status == 0) {
					Ext.Msg.alert(alertTitle, "您选择的这条业务费已经作废,不能审核！");
					return false;
				}
				if(_records[i].data.status==2){
					//Ext.Msg.alert(alertTitle, "您选择的第"+(i+1)+"条业务费已经审核或是已经作废了！");
					Ext.Msg.alert(alertTitle, "您选择的这条业务费已经审核,不能再次审核！");
					return false;
				}
			}
//			Ext.Msg.prompt(alertTitle,'请输入流程号：',function(btn,text){
//				if(btn=='ok'){
//					audit(_records,text);
//				}
//			});
			//audit(_records);
			auditfiBusinessFee(_records);
					
	 	}},'-',{text:'<b>导出</b>',iconCls:'sort_down',tooltip : '导出业务管理费',handler:function() {
                	parent.exportExl(fiBusinessFeeGrid);
            } },/*'-',{text:'<b>打印</b>',iconCls:'printBtn',tooltip : '打印业务管理费',handler:function() {
                	alert('功能待实现..');
            } },*/'-',{text : '<b>搜索</b>',iconCls : 'btnSearch',
    					handler : searchfiBusinessFee
    				}		
	 	];	
function audit(_records,workflowNo,win){
	
	Ext.Msg.confirm(alertTitle, "确定要审核"+_records.length+"条记录吗？", function(
					btnYes) {
				if (btnYes == 'yes' || btnYes == 'ok'
						|| btnYes == true) {
					var ids="";
					for(var i=0;i<_records.length;i++){
						 ids=ids+_records[i].data.id;
						 if(i!=_records.length-1){
						 	ids+=',';
						 }
					}
					amount = Ext.getCmp('amountId').getValue();
					
					Ext.Ajax.request({
						url : sysPath+'/'+ auditUrl,
						params : {
							ids : ids,
							workflowNo:workflowNo,
							amount:amount,
						    privilege:privilege,
						    limit : pageSize
						},
						success : function(resp) {
							var respText = Ext.util.JSON.decode(resp.responseText);
							if(respText.success){
								Ext.Msg.alert(alertTitle,"审核成功!");
								win.close();
								dataReload();
							}else{
								Ext.Msg.alert(alertTitle,respText.msg);
							}
						}
					});
				}
			});
}
var queryTbar=new Ext.Toolbar([
		'单据日期：',{
    		xtype : 'datefield',
    		id : 'startDate',
    		format : 'Y-m-d',
    		emptyText : "开始时间",
    		width:defaultWidth+20,
    		editable : true,
    		listeners : {
    			'select' : function() {
    			   var start = Ext.getCmp('startDate').getValue().format("Y-m-d");
    			   Ext.getCmp('endDate').setMinValue(start);
    		     }
    		}
    	}, '&nbsp;至&nbsp;', {
    		xtype : 'datefield',
    		id : 'endDate',
    		format : 'Y-m-d',
    		emptyText : "结束时间",
    		width:defaultWidth+20,
    		editable : true
    	},'-',
	 	'发货客商:',{ 
		 	xtype : 'combo',
			typeAhead : true,
			queryParam : 'filter_LIKES_cusName',
			triggerAction : 'all',
			id:'searchCustomerId',
			width:defaultWidth,
			store : customerStore,
			pageSize : comboSize,
			forceSelection : true,
			selectOnFocus:true,
			listWidth:245,
			resizable:true,
			mode : "remote",//从服务器端加载值
			valueField : 'cusId',//value值，与fields对应
			displayField : 'cusName',//显示值，与fields对应
			enableKeyEvents:true,
			listeners : {
	 		keyup:function(textField, e){
	             if(e.getKey() == 13){
	             	searchfiBusinessFee();
	             }
	 		}
	 	}},'-',
	 	'收款客商:',{ 
		 	xtype : 'combo',
			typeAhead : true,
			queryParam : 'filter_LIKES_cusName',
			triggerAction : 'all',
			id:'searchCollectionCustomerId',
			width:defaultWidth,
			listWidth:245,
			store : customerStore,
			pageSize : comboSize,
			forceSelection : true,
			selectOnFocus:true,
			resizable:true,
			mode : "remote",//从服务器端加载值
			valueField : 'cusId',//value值，与fields对应
			displayField : 'cusName',//显示值，与fields对应
			enableKeyEvents:true,
			listeners : {
	 		keyup:function(textField, e){
	             if(e.getKey() == 13){
	             	searchfiBusinessFee();
	             }
	 		}
	 	}},'-','所属部门:',{
	 		xtype : 'combo',
			typeAhead : true,
			queryParam : 'filter_LIKES_departName',
			triggerAction : 'all',
			store : rightDepartStore2,
			pageSize : comboSize,
			id:'searchDepart',
			listWidth:245,
			allowBlank : false,
			width:defaultWidth,
			forceSelection : true,
			selectOnFocus:true,
			resizable:true,
			mode : "local",//从服务器端加载值
			valueField : 'departId',//value值，与fields对应
			displayField : 'departName',//显示值，与fields对应
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
				 keyup:function(textField, e){
	             	if(e.getKey() == 13){
	             		searchfiBusinessFee();
	             	}
	 			}
	 	}},'-',
	 	'状态:',{
	 		xtype : 'combo',
			typeAhead : true,
			triggerAction : 'all',
			store : statusStore,
			editable:false,
			id:'searchStatus',
			width:defaultWidth,
			forceSelection : true,
			mode : "local",//从服务器端加载值
			valueField : 'statusId',//value值，与fields对应
			displayField : 'statusName',//显示值，与fields对应
			enableKeyEvents:true,
			listeners : {
	 		keyup:function(textField, e){
	             if(e.getKey() == 13){
	             	searchfiBusinessFee();
	             }
	 			}
			}
	 	}	
	 	]);	
	 	
Ext.onReady(function() {
   	dateStore = new Ext.data.Store({
        storeId:"dateStore",
        proxy: new Ext.data.HttpProxy({url : sysPath+ "/"+gridSearchUrl,
		params:{ 
			privilege:privilege,
			filter_EQL_departId:bussDepart,
			limit : pageSize
		}
	}),reader: new Ext.data.JsonReader({
         root: 'result', totalProperty: 'totalCount'
      }, fields)
    });
   
   var sm = new Ext.grid.CheckboxSelectionModel({});
	var rowNum = new Ext.grid.RowNumberer({
        header:'序号', width:20
    });
    fiBusinessFeeGrid = new Ext.grid.GridPanel({
        renderTo: Ext.getBody(),
        id:'fiBusinessFeeCenter',
        height:Ext.lib.Dom.getViewHeight(),		
        width:Ext.lib.Dom.getViewWidth(),
        viewConfig : {
			columnsText : "显示的列",
			sortAscText : "升序",
			sortDescText : "降序"
			//forceFit : true
		},
		autoScroll:false, 
		//autoExpandColumn:1,
        frame:true, 
        loadMask:true,
        sm:sm,
        cm:new Ext.grid.ColumnModel([rowNum,sm,
       		 	{header: 'ID', dataIndex: 'id',width:colWidth,sortable : true,hidden:true},
       		 	{header: '流程号', dataIndex: 'workflowNo',width:colWidth,sortable : true},
       			{header: '所属部门', dataIndex: 'belongDepartName',width:100,sortable : true},
        		{header: '发货客商', dataIndex: 'customerName',width:100,sortable : true},
 				{header: '应付金额', dataIndex: 'amount',width:colWidth,sortable : true},
 				{header: '货量', dataIndex: 'volume',width:colWidth,sortable : true},
 				{header: '计算方式', dataIndex: 'settlement',width:colWidth,sortable : true},
 				{header: '营业额', dataIndex: 'turnover',width:colWidth,sortable : true},
 				{header: '业务月份', dataIndex: 'businessMonth',width:colWidth,sortable : true},
 				{header: '费率', dataIndex: 'rate',width:45,sortable : true},
 				{header: '收款方', dataIndex: 'collectionCustomerName',width:100,sortable : true},
 				{header: '收款账号', dataIndex: 'collectionAccount',width:100,sortable : true},
 				{header: '收款人开户行', dataIndex: 'collectionBank',width:80,sortable : true},
 				{header: '业务费类型', dataIndex: 'businessType',width:colWidth,sortable : true
 					,renderer:function(v){
 						return v==1?'对私':(v==2?"对公":"异常");
			        }
 				},
 				{header: '审核状态', dataIndex: 'status',width:100,sortable : true
 					,renderer:function(v){
 						return v==0?'已作废':(v==1?"未审核":"已审核");
			        }
 				},
 				{header: '内容摘要', dataIndex: 'remark',width:100,sortable : true},
 				{header: '创建部门', dataIndex: 'departName',width:colWidth,sortable : true},
 				{header: '创建人', dataIndex: 'createName',width:colWidth,sortable : true,hidden:true},
 				{header: '创建时间', dataIndex: 'createTime',width:colWidth,sortable : true,hidden:true},
 				{header: '修改人', dataIndex: 'updateName',width:colWidth,sortable : true,hidden:true},
 				{header: '创建时间', dataIndex: 'updateTime',width:colWidth,sortable : true,hidden:true},
 				{header: '时间戳', dataIndex: 'ts',width:colWidth,sortable : true,hidden:true}
        ]),
        store:dateStore,
        tbar: tbar,
        listeners: {
	        render: function(){
	            queryTbar.render(this.tbar);
	        }
        },
        bbar: new Ext.PagingToolbar({
            pageSize: pageSize, 
            privilege:privilege,
            store: dateStore, 
            displayInfo: true,
            displayMsg: '显示第 {0} 条到 {1} 条记录，共 {2} 条', 
            emptyMsg: "没有记录信息显示"
        })
    });
	    fiBusinessFeeGrid.on('click', function() {
	       selabled();
	     	
	    });
		fiBusinessFeeGrid.on('rowdblclick',function(grid,index,e){
		var _records = fiBusinessFeeGrid.getSelectionModel().getSelections();
				
				if (_records.length ==1) {
					savefiBusinessFee(_records);
				}
			 	
		});
	
});
    
    
	
   function searchfiBusinessFee() {
		dateStore.proxy = new Ext.data.HttpProxy({
					method : 'POST',
					url : sysPath+ "/"+gridSearchUrl
				});
		var searchCustomerId =  Ext.getCmp('searchCustomerId').getValue();
		var searchCollectionCustomerId =  Ext.getCmp('searchCollectionCustomerId').getValue();
		var searchDepart =  Ext.getCmp('searchDepart').getValue();
		if(searchDepart==''){
			Ext.Msg.alert(alertTitle, "请选择所属部门再查询");
			return;
		}
		var searchStatus = Ext.getCmp('searchStatus').getValue();
		Ext.apply(dateStore.baseParams,{
			filter_EQL_customerId:searchCustomerId,
			filter_EQL_collectionCustomerId:searchCollectionCustomerId,
			filter_EQL_belongDepartId:searchDepart,
			filter_GED_createTime:(!Ext.getCmp("startDate").hidden?(Ext.get("startDate").dom.value=="开始时间"?"":Ext.get("startDate").dom.value):""),
			filter_LED_createTime:(!Ext.getCmp("endDate").hidden?(Ext.get("endDate").dom.value=="结束时间"?"":Ext.get("endDate").dom.value):""),
			filter_EQL_status:searchStatus,
			privilege:privilege,
			limit : pageSize
		});
//		dateStore.on('beforeload', function(store,options){
//			Ext.apply(options.params, {
//				filter_EQL_customerId:searchCustomerId,
//				filter_EQL_collectionCustomerId:searchCollectionCustomerId,
//				filter_EQL_belongDepartId:searchDepart,
//				filter_GED_createTime:(!Ext.getCmp("startDate").hidden?(Ext.get("startDate").dom.value=="开始时间"?"":Ext.get("startDate").dom.value):""),
//    			filter_LED_createTime:(!Ext.getCmp("endDate").hidden?(Ext.get("endDate").dom.value=="结束时间"?"":Ext.get("endDate").dom.value):""),
//    			filter_EQL_status:searchStatus,
//				privilege:privilege,
//				limit : pageSize
//			})
//		});
		
		var editbtn = Ext.getCmp('basfiBusinessFeeEdit');
		var deletebtn = Ext.getCmp('basfiBusinessFeeDelete');

		editbtn.setDisabled(true);
		deletebtn.setDisabled(true);

		dataReload();
		
		
	}
function savefiBusinessFee(_records) {
	var amount=0;
	if(_records!=null)
	var cid=_records[0].data.id;
	var form = new Ext.form.FormPanel({
		labelAlign : 'left',
		frame : true,
		url:sysPath+'/'+gridSearchUrl,
		baseParams:{
			filter_EQL_id:cid,
			privilege:privilege,
			limit : pageSize},
		bodyStyle : 'padding:5px 5px 0',
		//width : 500,
		reader : new Ext.data.JsonReader({
	        root: 'result', totalProperty: 'totalCount'
	    }, fields),
	labelAlign : "right",
	
		items : [{
							layout : 'column',
							items : [{
										columnWidth : .5,
										layout : 'form',
										items : [{
													name : "id",
													xtype : "hidden"
												},{
													name : "ts",
													xtype : "hidden"
												},{
													xtype : 'combo',
													typeAhead : true,
													triggerAction : 'all',
													store : businessTypeStore,
													allowBlank : false,
													emptyText : "请选择业务费类型",
													id:'businessTypeId',
													forceSelection : true,
													selectOnFocus:true,
													resizable:true,
													fieldLabel : '业务费类型<font style="color:red;">*</font>',
													mode : "remote",//从服务器端加载值
													valueField : 'businessTypeId',//value值，与fields对应
													displayField : 'businessTypeName',//显示值，与fields对应
													hiddenName:'businessType',
													anchor : '95%'
												},{
													xtype : 'combo',
													typeAhead : true,
													queryParam : 'filter_LIKES_cusName',
													triggerAction : 'all',
													store : customerStore,
													pageSize : comboSize,
													allowBlank : false,
													forceSelection : true,
													id:'incomeCustomerId',
													//selectOnFocus:true,
													resizable:true,
													minChars :0,
													fieldLabel : '收款客商<font style="color:red;">*</font>',
													mode : "remote",//从服务器端加载值
													valueField : 'cusId',//value值，与fields对应
													displayField : 'cusName',//显示值，与fields对应
													hiddenName:'collectionCustomerId',
													anchor : '95%',
													enableKeyEvents:true,
													listeners : {
														select:function(combo,e){
															if(combo.getValue()!=''){
																if(Ext.getCmp('outCusId').getValue()!=''&&Ext.getCmp('settlement').getValue()!=''){
																	autoLoadData();
																}
															}
														}
													 }
													},{
													xtype : 'textfield',
													labelAlign : 'left',
													fieldLabel : '所属部门<font style="color:red;">*</font>',
													name : 'belongDepartName',
													id:'belongDepartName',
													readOnly:true,
													value:bussDepartName,
													minValue:1,
													readOnly:true,
													maxLength:50,
													allowBlank : false,
													anchor : '95%'
												 },{
													xtype : 'textfield',
													labelAlign : 'left',
													allowBlank : false,
													readOnly:true,
													fieldLabel : '收款开户行<font style="color:red;">*</font>',
													maxLength:50,
													id:'collectionBank',
													name : 'collectionBank',
													anchor : '95%'
												},{
													name : "belongDepartId",
													value:bussDepart,
													id:'belongDepartId',
													xtype : "hidden"
												},{
													name : "fiBusinessFeePriceId",
													id:'fiBusinessFeePriceId',
													xtype : "hidden"
												},{
													xtype : 'datefield',
													labelAlign : 'left',
													fieldLabel : '业务月份<font style="color:red;">*</font>',
													allowBlank : false,
													id:'businessMonth',
													format:'Y-m',
													maxLength:7,
													name : 'businessMonth',
													anchor : '95%',
													enableKeyEvents:true,
													listeners : {
														 blur:function(td, e){
														 	if(td.getValue()!=''&&Ext.getCmp('outCusId').getValue()!=''){
														 		autoLoadAmount();
														 	}
														 }
													}
													
												},{
													xtype : 'numberfield',
													labelAlign : 'left',
													fieldLabel : '货量<font style="color:red;">*</font>',
													name : 'volume',
													id:'volumeId',
													readOnly:true,
													minValue:1,
													readOnly:true,
													maxLength:20,
													allowBlank : false,
													anchor : '95%'
												},{
													xtype : 'numberfield',
													labelAlign : 'left',
													fieldLabel : '营业额<font style="color:red;">*</font>',
													name : 'turnover',
													id:'turnover',
													readOnly:true,
													allowNegative:false,
													decimalPrecision:2,
													maxLength:20,
													allowBlank : false,
													anchor : '95%'
												}, {
													xtype : 'numberfield',
													labelAlign : 'left',
													allowBlank : false,
													readOnly:false,
													fieldLabel : '应付金额<font style="color:red;">*</font>',
													maxLength:10,
													name : 'amount',
													decimalPrecision:2,
													id:'amountId',
													anchor : '95%',
													enableKeyEvents:true,
													listeners : {
														 blur:function(td, e){
														 	if(td.getValue()=''){
														 		td.setValue(0);
														 	}
														 }
													}
												}]
	
									}, {
										columnWidth : .5,
										layout : 'form',
										items : [{xtype : 'combo',
													typeAhead : true,
													queryParam : 'filter_LIKES_cusName',
													triggerAction : 'all',
													id:'outCusId',
													store : customerStore,
													pageSize : comboSize,
													allowBlank : false,
													forceSelection : true,
													selectOnFocus:true,
													resizable:true,
													minChars :0,
													fieldLabel : '发货客商<font style="color:red;">*</font>',
													mode : "remote",//从服务器端加载值
													valueField : 'cusId',//value值，与fields对应
													displayField : 'cusName',//显示值，与fields对应
													hiddenName:'customerId',
													anchor : '95%',
													enableKeyEvents:true,
													listeners : {
														select:function(combo,e){
															if(combo.getValue()!=''){
																if(Ext.getCmp('outCusId').getValue()!=''&&Ext.getCmp('settlement').getValue()!=''){
																	autoLoadData();
																}
																
																if(Ext.getCmp('businessMonth').getValue()!=""){
																	autoLoadAmount();
																}
															}
														}
													 }
												},{
												   xtype : 'combo',
						        				   fieldLabel: '计算方式<span style="color:red">*</span>',
										           allowBlank : false,
											       triggerAction : 'all',
												   typeAhead:false,
											       forceSelection : true,
												   store: settlementStore,
												   id:'settlement',
												   mode : "local",//获取本地的值
												   displayField : 'name',
												   valueField : 'name',
												   name:'settlement',
												   blankText : "计算方式不能为空！",
												   anchor : '95%',
												   emptyText : "请选择费率计算方式",
												   enableKeyEvents:true,
													listeners : {
														select:function(combo,e){
															if(combo.getValue()!=''){
																if(Ext.getCmp('incomeCustomerId').getValue()!=''&&Ext.getCmp('settlement').getValue()!=''){
																	autoLoadData();
																}
															}
														}
													 }
						        			 },{
													xtype : 'numberfield',
													labelAlign : 'left',
													fieldLabel : '收款账号<font style="color:red;">*</font>',
													allowBlank : false,
													readOnly:true,
													id:'collectionAccount',
													readOnly:true,
													maxLength:20,
													name : 'collectionAccount',
													anchor : '95%'
												},{
													xtype : 'numberfield',
													labelAlign : 'left',
													fieldLabel : '费率<font style="color:red;">*</font>',
													name : 'rate',
													readOnly:true,
													id:'rateId',
													maxLength:20,
													allowBlank : false,
													anchor : '95%',
													enableKeyEvents:true,listeners : {
											 		    blur:function(td, e){
											 		    
											 		    }
											 		}
												},{
													xtype : 'textarea',
													labelAlign : 'left',
													height:100,
													id:'remarkId',
													fieldLabel : '备注',
													name : 'remark',
													anchor : '95%'
												},{
													xtype:'label',
													id:'showMsg',
													width:380,
													enableKeyEvents:true,
													listeners : {
														render:function(v){
															//Ext.getCmp('showMsg').getEl().update('<span style="color:red">金额：单价x计费重量，黄单总金额：金额+板费</span>');
														}
													}
										        }]
									}]
									
						}]
						});
									
		fiBusinessFeeTitle='录入业务费信息';
		if(cid!=null){
			fiBusinessFeeTitle='修改业务费信息';
			//departStore.load();
			//Ext.getCmp('mycombo').store=departStore;
			form.load({
				waitMsg : '正在载入数据...',
    			success : function(_form, action) {

    			},
    			failure : function(_form, action) {
    				Ext.MessageBox.alert(alertTitle, '载入失败');
    			}
			});
		}
		var win = new Ext.Window({
		title : fiBusinessFeeTitle,
		width : 600,
		closeAction : 'hide',
		plain : true,
		resizable : false,
		listeners:{
			'beforeshow':function(){
				run();
			}
		},
		modal : true,
		items : form,
		buttonAlign : "center",	buttons : [{
			text : "保存",
			iconCls : 'groupSave',
			handler : function() {
				if (form.getForm().isValid()) {
					this.disabled = true;//只能点击一次
					form.getForm().submit({
						url : sysPath+ '/'+saveUrl,
						params:{privilege:privilege},
						waitMsg : '正在保存数据...',
						success : function(form, action){
							win.hide();
							Ext.Msg.alert(alertTitle,action.result.msg, function() {
										dataReload();
							});
						},
						failure : function(form, action) {
							if (action.faliureType == Ext.form.Action.SERVER_INVALID) {
								Ext.Msg.alert(alertTitle, "服务器连接异常，请联系管理员！");
							} else {
								if (action.result.msg) {
									Ext.Msg.alert(alertTitle,
											action.result.msg, function() {
												dataReload();
											});

								}
							}
						}
					});
					this.disabled = false;//只能点击一次
			}
			}
		}, {text : "重置",
			iconCls : 'refresh',
			handler : function(){
				if(cid==null){
					form.getForm().reset();
				}else{
					form.load({
						waitMsg : '正在载入数据...',
		    			success : function(_form, action) {
											   			
		    			},
		    			failure : function(_form, action) {
		    				Ext.MessageBox.alert(alertTitle, '载入失败');
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
		
		function autoLoadAmount(){
			var v=Ext.getCmp('businessMonth').getValue();
			var ret=v.getFullYear()+"-"+("00"+(v.getMonth()+1)).slice(-2);
			
			Ext.Ajax.request({
				url : sysPath+'/fi/fiBusinessFeeAction!countFaxCusGoods.action',
				params : {
					customerId:Ext.getCmp('outCusId').getValue(),
					businessMonth:ret
				},
				success : function(resp) {
					Ext.getCmp('showMsg').getEl().update('<span style="color:red"></span>');
					var respText = Ext.util.JSON.decode(resp.responseText);
					Ext.getCmp('turnover').setValue(respText.totalAmount);
					Ext.getCmp('volumeId').setValue(respText.totalGoods);
					
					if(Ext.getCmp('settlement').getValue()!=''&&Ext.getCmp('rateId').getValue()!=''){
						if(Ext.getCmp('settlement').getValue()=="重量"){
							Ext.getCmp('amountId').setValue(Math.ceil(parseFloat(Ext.getCmp('turnover').getValue())*parseFloat(Ext.getCmp('rateId').getValue())));
						}else if(Ext.getCmp('settlement').getValue()=="营业额"){
							Ext.getCmp('amountId').setValue(Math.ceil(parseFloat(Ext.getCmp('volumeId').getValue())*parseFloat(Ext.getCmp('rateId').getValue())));
						}else{
							Ext.getCmp('amountId').setValue(Math.ceil(parseFloat(Ext.getCmp('rateId').getValue())));
						}
					}
				},
				failure : function(response) {
					Ext.getCmp('showMsg').getEl().update('<span style="color:red">查询货量和营业额失败，请重试</span>');
				}
			});
		}
		
		function autoLoadData(){
			Ext.Ajax.request({
				url : sysPath+'/'+ searchCustomerPriceUrl,
				params : {
					filter_EQL_isDelete:1,
					filter_EQL_status:1,
					filter_EQL_departId:bussDepart,
					filter_EQS_settlement:Ext.getCmp('settlement').getValue(),
					filter_EQL_incomeCustomerId:Ext.getCmp('incomeCustomerId').getValue(),
					filter_EQL_customerId:Ext.getCmp('outCusId').getValue()
				},
				success : function(resp){
					Ext.getCmp('showMsg').getEl().update('<span style="color:red"></span>');
					var respText = Ext.util.JSON.decode(resp.responseText);
					if(respText.result.length==0){
						Ext.getCmp('showMsg').getEl().update('<span style="color:red">没业务费协议价无法计算，请在业务费协议价管理模块维护</span>');
					}else if(respText.result.length>1){
						Ext.getCmp('showMsg').getEl().update('<span style="color:red">取出多条协议价数据，请在业务费协议价管理模块维护</span>');
					}else{
						Ext.getCmp('collectionAccount').setValue(respText.result[0].accountNum);
						Ext.getCmp('collectionBank').setValue(respText.result[0].bank);
						Ext.getCmp('rateId').setValue(respText.result[0].rate);
						Ext.getCmp('fiBusinessFeePriceId').setValue(respText.result[0].id);
						
						if(Ext.getCmp('turnover').getValue()!=''&&Ext.getCmp('volumeId').getValue()!=''){
							if(Ext.getCmp('settlement').getValue()=="重量"){
								Ext.getCmp('amountId').setValue(Math.ceil(parseFloat(Ext.getCmp('turnover').getValue())*parseFloat(Ext.getCmp('rateId').getValue())));
							}else if(Ext.getCmp('settlement').getValue()=="营业额"){
								Ext.getCmp('amountId').setValue(Math.ceil(parseFloat(Ext.getCmp('volumeId').getValue())*parseFloat(Ext.getCmp('rateId').getValue())));
							}else{
								Ext.getCmp('amountId').setValue(Math.ceil(parseFloat(Ext.getCmp('rateId').getValue())));
							}
						}
					}
				},
				failure : function(response) {
					Ext.getCmp('showMsg').getEl().update('<span style="color:red">取费率失败，请重试</span>');
				}
			});
		}
		
 }
 
 function auditfiBusinessFee(_records) {
	var amount=0;
	if(_records!=null)
	var cid=_records[0].data.id;
	var form = new Ext.form.FormPanel({
		labelAlign : 'left',
		frame : true,
		url:sysPath+'/'+gridSearchUrl,
		baseParams:{
			filter_EQL_id:cid,
			privilege:privilege,
			limit : pageSize},
		bodyStyle : 'padding:5px 5px 0',
		//width : 500,
		reader : new Ext.data.JsonReader({
	        root: 'result', totalProperty: 'totalCount'
	    }, fields),
	labelAlign : "right",
	
		items : [{
							layout : 'column',
							items : [{
										columnWidth : .5,
										layout : 'form',
										items : [{
													name : "id",
													xtype : "hidden"
												},{
													name : "ts",
													xtype : "hidden"
												}, {
													xtype : 'textfield',
													labelAlign : 'left',
													fieldLabel : '流程号<font style="color:red;">*</font>',
													name : 'workflowNo',
													id:'workflowNo',
													maxLength:20,
													allowBlank : false,
													anchor : '95%'
												},{
													xtype : 'combo',
													typeAhead : true,
													triggerAction : 'all',
													store : businessTypeStore,
													allowBlank : false,
													emptyText : "请选择业务费类型",
													forceSelection : true,
													selectOnFocus:true,
													resizable:true,
													disabled :true,
													fieldLabel : '业务费类型',
													mode : "remote",//从服务器端加载值
													valueField : 'businessTypeId',//value值，与fields对应
													displayField : 'businessTypeName',//显示值，与fields对应
													hiddenName:'businessType',
													anchor : '95%'
												}, {
													xtype : 'combo',
													typeAhead : true,
													queryParam : 'filter_LIKES_departName',
													triggerAction : 'all',
													store : bussStore,
													pageSize : comboSize,
													allowBlank : false,
													forceSelection : true,
													selectOnFocus:true,
													disabled :true,
													minChars :0,
													resizable:true,
													fieldLabel : '所属部门',
													mode : "remote",//从服务器端加载值
													valueField : 'departId',//value值，与fields对应
													displayField : 'departName',//显示值，与fields对应
													hiddenName:'belongDepartId',
													anchor : '95%'
												},{xtype : 'combo',
													typeAhead : true,
													queryParam : 'filter_LIKES_cusName',
													triggerAction : 'all',
													id:'outCusId',
													store : customerStore,
													pageSize : comboSize,
													allowBlank : false,
													disabled :true,
													forceSelection : true,
													selectOnFocus:true,
													resizable:true,
													minChars :0,
													fieldLabel : '发货客商',
													mode : "remote",//从服务器端加载值
													valueField : 'cusId',//value值，与fields对应
													displayField : 'cusName',//显示值，与fields对应
													hiddenName:'customerId',
													anchor : '95%'
												},{
												   xtype : 'textfield',
						        				   fieldLabel: '计算方式',
												   name:'settlement',
												   labelAlign : 'left',
													allowBlank : false,
													disabled :true,
													maxLength:50,
													anchor : '95%'
						        			    },{
													xtype : 'numberfield',
													labelAlign : 'left',
													fieldLabel : '费率',
													name : 'rate',
													id:'rateId',
													maxLength:20,
													disabled :true,
													allowBlank : false,
													anchor : '95%'
												},{
													xtype : 'numberfield',
													labelAlign : 'left',
													fieldLabel : '货量',
													name : 'volume',
													id:'volumeId',
													minValue:1,
													disabled :true,
													maxLength:20,
													allowBlank : false,
													anchor : '95%'
												},{
													xtype : 'textfield',
													labelAlign : 'left',
													fieldLabel : '营业额',
													allowBlank : false,
													disabled :true,
													maxLength:7,
													name : 'turnover',
													anchor : '95%'
												}, {
													xtype : 'numberfield',
													labelAlign : 'left',
													allowBlank : false,
													disabled :false,
													fieldLabel : '应付金额<font style="color:red;">*</font>',
													maxLength:10,
													minValue :1,
													name : 'amount',
													id:'amountId',
													anchor : '95%',
													enableKeyEvents:true,
													listeners : {
														 blur:function(td, e){
														 }
													}
												}]
	
									}, {
										columnWidth : .5,
										layout : 'form',
										items : [{xtype : 'combo',
													typeAhead : true,
													queryParam : 'filter_LIKES_cusName',
													triggerAction : 'all',
													store : customerStore,
													pageSize : comboSize,
													allowBlank : false,
													forceSelection : true,
													selectOnFocus:true,
													disabled :true,
													resizable:true,
													minChars :0,
													fieldLabel : '收款客商',
													mode : "remote",//从服务器端加载值
													valueField : 'cusId',//value值，与fields对应
													displayField : 'cusName',//显示值，与fields对应
													hiddenName:'collectionCustomerId',
													anchor : '95%'
													},{
													xtype : 'numberfield',
													labelAlign : 'left',
													fieldLabel : '收款账号',
													allowBlank : false,
													id:'collectionAccount',
													disabled :true,
													maxLength:20,
													name : 'collectionAccount',
													anchor : '95%'
												}, {
													xtype : 'textfield',
													labelAlign : 'left',
													allowBlank : false,
													disabled :true,
													fieldLabel : '收款开户行',
													maxLength:50,
													name : 'collectionBank',
													anchor : '95%'
												}, {
													xtype : 'datefield',
													labelAlign : 'left',
													fieldLabel : '业务月份',
													allowBlank : false,
													disabled :true,
													id:'businessMonth',
													format:'Y-m',
													maxLength:7,
													name : 'businessMonth',
													anchor : '95%'
												},{
													xtype : 'textarea',
													labelAlign : 'left',
													height:100,
													//readOnly:true,
													id:'remarkId',
													fieldLabel : '备注',
													name : 'remark',
													anchor : '95%'
												}]
									}]
									
						}]
						});
									
		if(cid!=null){
		fiBusinessFeeTitle='审核业务费信息';
		//departStore.load();
		//Ext.getCmp('mycombo').store=departStore;
		form.load({
				waitMsg : '正在载入数据...',
    			success : function(_form, action) {
		   			amount = Ext.getCmp('amountId').getValue();
		   			Ext.getCmp('workflowNo').focus(true,true);
    			},
    			failure : function(_form, action) {
    				Ext.MessageBox.alert(alertTitle, '载入失败');
    			}
			});
			}
		var win = new Ext.Window({
		title : fiBusinessFeeTitle,
		width : 600,
		closeAction : 'hide',
		plain : true,
		resizable : false,
		listeners:{
			'beforeshow':function(){
				run();
			}
		},
		modal : true,
		items : form,
		buttonAlign : "center",	buttons : [{
			text : "保存",
			iconCls : 'groupSave',
			handler : function() {
				if (form.getForm().isValid()) {
					this.disabled = true;//只能点击一次
					var workflowNo = Ext.getCmp('workflowNo').getValue();
					var amountValue = Ext.getCmp('amountId').getValue();
					if(null==amountValue || amountValue<1){
						Ext.Msg.alert(alertTitle,'应付金额必须大于零!');
						return;
					}
					
					if(amountValue>amount){
						Ext.Msg.alert(alertTitle,'只能改少，不能改多!',function(){
							Ext.getCmp('amountId').setValue(amount);
						});
						return;
					}
					
					
					audit(_records,workflowNo,win);
				}
				this.disabled = false;//
			}
		}, {
						
				text : "重置",
				iconCls : 'refresh',
				handler : function() {
					
					if(cid==null){
						form.getForm().reset();
					}
					if(cid!=null){
						form.load({
						waitMsg : '正在载入数据...',
		    			success : function(_form, action) {
											   			
		    			},
		    			failure : function(_form, action) {
		    				Ext.MessageBox.alert(alertTitle, '载入失败');
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
 function dataReload(){
		dateStore.reload({
			params : {
				start : 0,
				privilege:privilege,
				limit : pageSize
			}
		});
		
		selabled();
		
 }
 
 function selabled(){
 	 var _record = fiBusinessFeeGrid.getSelectionModel().getSelections();
 	
        var updatebtn = Ext.getCmp('basfiBusinessFeeEdit');
        var deletebtn = Ext.getCmp('basfiBusinessFeeDelete');
        if (_record.length==1) {       	
            if(updatebtn){
            	updatebtn.setDisabled(false);
            }
            if(deletebtn){
            	deletebtn.setDisabled(false);
            }
        }else if(_record.length>1){
        	 if(updatebtn){
            	updatebtn.setDisabled(true);
            }
            if(deletebtn){
            	deletebtn.setDisabled(false);
            }
        } else {
            if(updatebtn){
            	updatebtn.setDisabled(true);
            }
			if(deletebtn){
            	deletebtn.setDisabled(true);
			}
        }
 }
 
function run(){
    var all = Ext.query('input[type!=hidden]'); // 查找所有非隱藏元素   
    Ext.each(all, function(o, i, all) { // 遍曆並添加enter的監聽  
    		  // Ext.get(o).dom.
               Ext.get(o).addKeyMap({   
                    key : 13,   
                    fn : function() {   
                        try {   
                            all[i + 1].focus()   
                        } catch (e) {   
                            event.keyCode = 9  
                        }   
                        if (all[i + 1]   
                                && /button|reset|submit/.test(all[i + 1].type))   
                            all[i + 1].click(); // 如果點擊則觸發click事件   
  
                        return true;   
                    }   
                })   
            });   
    Ext.getBody().focus(); // 使頁面獲取焦點，否則下面設定默認焦點的功能不靈驗   
  
    try {   
        var el;   
        if (typeof eval(xFocus) == 'object') { // 如果傳入的是id或dom節點   
            el = Ext.getDom(xFocus).tagName == 'input'  
                    ? Ext.getDom(xFocus)   
                    : Ext.get(xFocus).first('input', true); // 找到input框   
        } else {   
            el = all[xFocus || 0]; // 通過索引號找   
        }   
        el.focus();   
    } catch (e) {   
    }   
}   
Ext.isReady ? run() : Ext.onReady(run); // 頁面加載完成後添加表單導航 
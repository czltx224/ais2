	Ext.QuickTips.init();
	var privilege=49;
	var saveUrl="stock/oprStocktakeDetailAction!save.action";
	var areaListUrl="stock/oprStoreAreaAction!list.action";
	var ralaListUrl="stock/oprStocktakeDetailAction!ralaList.action";
	var booleanId =false; 
	
Ext.onReady(function(){
	Ext.QuickTips.init();
	var rownum=new Ext.grid.RowNumberer({
		header:'序号',
        width : 35,
		sortable : true
		
	});

    var fields=[{name:"id",mapping:'id'},'ts','weight','departId','dno','departName','piece','realPiece',
    			'storageArea','updateName','updateTime','createName','createTime',
    			'flightMainNo','consignee','addr','status','stocktadeId','subNo','flightNo',
    			'goWhere','distributionMode','takeMode','cusName','stocktakeId'];
    			
    var jsonread= new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'},
                    fields);
                              
	var sm = new Ext.grid.CheckboxSelectionModel({
		width:25,
		moveEditorOnEnter:true, 
		id:'checkId',
		name:'checkId'
	});
	
	var areaStore = new Ext.data.Store({ 
            storeId:"areaStore",                        
            proxy: new Ext.data.HttpProxy({url:sysPath+"/"+areaListUrl,method:'post'}),
            baseParams:{
            	filter_EQL_departId:bussDepart
            },
            reader: new Ext.data.JsonReader({
            root: 'result', totalProperty: 'totalCount'
           }, [  {name:'storageArea', mapping:'areaName',type:'string'}           
              ])    
    });
	areaStore.load();
	
	var rightDepartStore = new Ext.data.Store({ 
            storeId:"rightDepartStore",                        
            proxy: new Ext.data.HttpProxy({url:sysPath+"/sys/basRightDepartAction!findDepartName.action",method:'post'}),
            baseParams:{
            	privilege:63,
            	filter_EQL_userId:userId
            },
            reader: new Ext.data.JsonReader({
            root: 'resultMap', totalProperty: 'totalCount'
           }, [  {name:'departName', mapping:'DEPARTNAME',type:'string'},
            {name:'departId', mapping:'RIGHTDEPARTID',type:'string'}             
              ])    
    });
	
	
	Ext.override(Ext.grid.RowSelectionModel, {
      selectRow: function(index, keepExisting, preventViewNotify) {
	        if (this.isLocked() || (index < 0 || index >= this.grid.store.getCount()) 
	        				|| (keepExisting && this.isSelected(index)) 
	        				   || (Number(this.grid.store.getAt(index).get("status")) == 1)) {
	        //根据每行的一个标识字段来判断是否选中
	        
	            return;
	        }
	        var r = this.grid.store.getAt(index);
	        if (r && this.fireEvent('beforerowselect', this, index, keepExisting, r) !== false) {
	            if (!keepExisting || this.singleSelect) {
	                this.clearSelections();
	            }
	            this.selections.add(r);
	            this.last = this.lastActive = index;
	            if (!preventViewNotify) {
	                this.grid.getView().onRowSelect(index);
	            }
	            this.fireEvent('rowselect', this, index, r);
	            this.fireEvent('selectionchange', this);
	    	    }
	
    	}
	});
	var flightStore=new Ext.data.Store({   //
		    storeId:"flightStore",                        
            proxy: new Ext.data.HttpProxy({url:sysPath+"/sys/basFlightAction!list.action",method:'post'}),
            baseParams:{privilege:62},
            reader: new Ext.data.JsonReader({
            root: 'result', totalProperty: 'totalCount'
           }, [  {name:'flightNo', mapping:'flightNumber',type:'string'}
              ])
   	});
	
	var statusStore=new Ext.data.SimpleStore({
			 auteLoad:true, //此处设置为自动加载
  			  data:[['0','未盘点'],['1','已盘点']],
   			 fields:["status","statusName"]
	});
	
	var dataStore = new Ext.data.Store({
                proxy: new Ext.data.HttpProxy({url:sysPath+"/"+ralaListUrl}),
                baseParams:{limit: pageSize},
                sortInfo : {field: "status", direction: "ASC"},
                reader:jsonread
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
 	
 	var twobar = new Ext.Toolbar({
		    	items : ['&nbsp;&nbsp;','<B>打印时间:</B>',
						{xtype : 'datefield',
							    		id : 'startDate',
							    		name:'startDate',
							    		format : 'Y-m-d',
							    		emptyText : "选择开始时间",
							    		anchor : '95%',
							    		width : 80,
							    		listeners : {
							    			'select' : function() {
							    			   var start = Ext.getCmp('startDate').getValue().format("Y-m-d");
							    			   Ext.getCmp('endDate').setMinValue(start);
							    		     }
							    		}},
							    		 '&nbsp;&nbsp;<B>到</B>&nbsp;&nbsp;',
						{xtype : 'datefield',
							    		id : 'endDate',
							    		name:'endDate',
							    		format : 'Y-m-d',
							    		emptyText : "选择结束时间",
							    		width : 80,
							    		anchor : '95%'
    	   				 }, '-','&nbsp;&nbsp;','<B>库存区域:</B>',
						 {xtype : 'textfield',
			        	                fieldLabel: '库存区域', 
			        	                id:'storageArea',
				        			    name: 'storageArea',
				        			    maxLength : 10,
				        			    width:70,
						                anchor : '95%'
						              },'-','&nbsp;&nbsp;',
    	   				 '<B>代理公司:</B>',
		    			{xtype : 'textfield',
			        	                fieldLabel: '代理公司', 
			        	                id:'company',
				        			    name: 'company',
				        			    width:70,
				        			    maxLength : 10,
						                anchor : '95%'
						              },'-','&nbsp;&nbsp;','<B>清仓单号:</B>',
						
    	   				 {xtype : 'textfield',
			        	                fieldLabel: '清仓单号',
			        	                id:'stocktadeId',
				        			    name: 'stocktadeId',
				        			    maxLength : 10,
						                anchor : '95%',
						                width:70,
						                enableKeyEvents:true,
						                listeners : {
							 				keyup:function(textField, e){
							                     if(e.getKey() == 13){
							                     	searchLog();
							                     }
							 				}
							 			}
						              },'-',{
    				     text : '<b>查询</b>',
    				     id : 'btn',
    				     iconCls : 'btnSearch',
    					 handler : searchLog
    				}]
		});
     
	var recordGrid=new Ext.grid.EditorGridPanel({
		renderTo:'recordGrid',
		renderTo:Ext.getBody(),
		enableColumnResize:true,
	//	el : 'recordGrid',
		id:'userCenter',
		autoWidth:true,
		minColumnWidth:50,
		autoScroll:true,
		height : Ext.lib.Dom.getViewHeight()-1,
		width : Ext.lib.Dom.getViewWidth()-1,
		viewConfig : {
			scrollOffset: 0,
			autoScroll : true
			//forceFit : true
		},
		//autoExpandColumn : 1,
		frame : true,
		clicksToEdit:1,
		loadMask : true,
		sm:sm,
		stripeRows : true,
		tbar:[{text:'<b>导出</b>',iconCls:'sort_down',handler:function() {
                exportExl(recordGrid);
        } },'-','&nbsp;&nbsp;',{
				text:'<B>打印清仓单</B>',
				tooltip : '打印清仓单',
				iconCls : 'userAdd',
				id:'addbtn',
				handler : function() {
						storeOrder();
				}
			},'-',{
				text : '<B>清仓确认</B>',
				id : 'submitbtn',
				disabled : true,
				tooltip : '清仓确认',
				iconCls:'userEdit',
				handler : function() {
						var _records = recordGrid.getSelectionModel().getSelections();
						if (_records.length == 0) {
							Ext.Msg.alert(alertTitle, '请选择一条您需要提交的数据');
							return false;
						}else {
							sumitStore(_records);
						}
				}
			},'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;',
			{
				xtype : 'combo',
				id:'comboTypeDepart',
				hidden : true,
				hiddenId : 'dictionaryName',
    			hiddenName : 'dictionaryName',
				triggerAction : 'all',
				store : rightDepartStore,
				width:140,
			//	mode:'local',
				queryParam : 'filter_LIKES_departName',
				minChars : 1,
				allowBlank : true,
				emptyText : "请选择权限部门名称",
				forceSelection : true,
				fieldLabel:'权限部门名称',
				editable : true,
			//	pageSize:comboSize,
				displayField : 'departName',//显示值，与fields对应
				valueField : 'departId',//value值，与fields对应
				name : 'departId',
				anchor : '100%'
		    },{
				xtype : 'combo',
				id:'comboTypeFlight',
				hidden : true,
				hiddenId : 'flightNo',
    			hiddenName : 'flightNo',
				triggerAction : 'all',
				store : flightStore,
				minChars : 1,
				width:200,
				allowBlank : true,
				emptyText : "请选择航班号",
				forceSelection : true,
				editable : true,
			    queryParam : 'filter_LIKES_flightNumber',
				pageSize:comboSize,
				displayField : 'flightNo',//显示值，与fields对应
				valueField : 'flightNo',//value值，与fields对应
				name : 'flightNo',
				anchor : '100%'
		    },{	xtype : 'combo',
				id:'comboType',
				hidden : true,
				hiddenId : 'statusName',
    			hiddenName : 'statusName',
				triggerAction : 'all',
				store : statusStore,
				allowBlank : true,
				emptyText : "请选择清仓状态",
			//	forceSelection : true,
				editable : true,
				mode : "local",
				displayField : 'statusName',
				valueField : 'status',
				name : 'status',
				anchor : '95%'
	    	},{	xtype:'textfield',
	 	        id : 'itemsValue',
		        name : 'itemsValue',
	            enableKeyEvents:true,
	            listeners : {
	 				keyup:function(textField, e){
	                     if(e.getKey() == 13){
	                     	searchLog();
	                     }
	 				}
	 			}
	        },'-',{
                xtype : "combo",
    			id:"comboselect",
   				width : 100,
 				triggerAction : 'all',
    			model : 'local',
    			hiddenId : 'checkItems',
    			hiddenName : 'checkItems',
    			name : 'checkItemstext',
    			store : [['EQS_flightMainNo', '主单号'],
    					['EQS_subNo', '分单号'],
    					['EQL_dno', '配送单号'],
    					['EQL_departId', "权限部门名称"],
    					['EQL_status', '状态'],
    					['EQS_flightNo', '航班号'],
    					['LIKES_distributionMode', '配送方式'],
    					['LIKES_takeMode', '提货方式']
    					],
    			emptyText : '选择查询类型',
    			forceSelection : true,
    			listeners : {
    				'select' : function(combo, record, index) { 
    					if (combo.getValue() == 'EQL_status') {
    						Ext.getCmp("itemsValue").disable();
    						Ext.getCmp("itemsValue").hide();
    						Ext.getCmp("itemsValue").setValue("");
    						
    						Ext.getCmp("comboType").setValue("");
    						Ext.getCmp("comboType").show();
    						Ext.getCmp("comboType").enable();
    						
    						Ext.getCmp("comboTypeDepart").disable();
    						Ext.getCmp("comboTypeDepart").hide();
    						Ext.getCmp("comboTypeDepart").setValue("");
    						
    						Ext.getCmp("comboTypeFlight").disable();
    						Ext.getCmp("comboTypeFlight").hide();
    						Ext.getCmp("comboTypeFlight").setValue("");
    					}else if(combo.getValue() == 'EQL_departId'){
    					    Ext.getCmp("itemsValue").disable();
    						Ext.getCmp("itemsValue").hide();
    						Ext.getCmp("itemsValue").setValue("");
    						
    						Ext.getCmp("comboType").disable();
    						Ext.getCmp("comboType").hide();
    						Ext.getCmp("comboType").setValue("");
    						
    						Ext.getCmp("comboTypeDepart").setValue("");
    						Ext.getCmp("comboTypeDepart").show();
    						Ext.getCmp("comboTypeDepart").enable();
    					
    						Ext.getCmp("comboTypeFlight").disable();
    						Ext.getCmp("comboTypeFlight").hide();
    						Ext.getCmp("comboTypeFlight").setValue("");
    					}else if(combo.getValue() == 'EQS_flightNo') {
    					    Ext.getCmp("comboTypeDepart").disable();
    						Ext.getCmp("comboTypeDepart").hide();
    						Ext.getCmp("comboTypeDepart").setValue("");
    						
    						Ext.getCmp("comboType").disable();
    						Ext.getCmp("comboType").hide();
    						Ext.getCmp("comboType").setValue("");
    						
    						Ext.getCmp("comboTypeFlight").setValue("");
    						Ext.getCmp("comboTypeFlight").show();
    						Ext.getCmp("comboTypeFlight").enable();
    					
    						Ext.getCmp("itemsValue").disable();
    						Ext.getCmp("itemsValue").hide();
    						Ext.getCmp("itemsValue").setValue("");
    					
    					}else{  //
    						Ext.getCmp("comboTypeFlight").disable();
    						Ext.getCmp("comboTypeFlight").hide();
    						Ext.getCmp("comboTypeFlight").setValue("");
    						
    						Ext.getCmp("comboTypeDepart").disable();
    						Ext.getCmp("comboTypeDepart").hide();
    						Ext.getCmp("comboTypeDepart").setValue("");
    						
    						Ext.getCmp("comboType").disable();
    						Ext.getCmp("comboType").hide();
    						Ext.getCmp("comboType").setValue("");
    						
    						Ext.getCmp("itemsValue").setValue("");
    						Ext.getCmp("itemsValue").show();
    						Ext.getCmp("itemsValue").enable();
    					}
    				}
    			 }
    		},'&nbsp;&nbsp;<span style="color:red;">清仓确认时，请填写清仓单号后查询。</span>'],
			columns:[ rownum,sm,
        		    {header: 'id', dataIndex: 'id',	hidden : true},
        			{header: '清仓单号', dataIndex: 'stocktakeId',width:60},
			        {header: '配送单号', dataIndex: 'dno',width:100},
			        {header: '库存区域', dataIndex: 'storageArea',width:60},
			        {header: '打印清仓单时间', dataIndex: 'createTime',width:110},
			        {header: '件数', dataIndex: 'piece',width:60},
			        {header: '实际件数<span style="color:red">(请填写)</span>', dataIndex: 'realPiece',
             					editor: new Ext.form.NumberField({
               				       allowBlank: true,
               				       id:'editor',
               				       style: 'text-align:left',
                			       allowNegative: false,
                			       listeners : {
						 				blur:function(e){
						                     if(e.getValue()==''){
						                     	e.setValue(0);
						                     }
						 				},
						 				focus:function(e){
						 					if(e.getValue()=='0'){
						 						e.setValue('');
						 					}
						 				}
						 			}
               	            	}),width:100,css : 'background: #CAE3FF;'
      				 }
               		 ,
			        {header: '状态', dataIndex: 'status',width:60,renderer:function(v,metaData){
			        					if (v == 0){
			        						 metaData.attr = 'style="color: red;"';
            								 metaData.css = "background: #CAE3FF;";
			        					}
			        					return v=='0'?'未盘点':'已盘点';
			        					
			        					}
			         },
    				{header: '重量', dataIndex: 'weight',width:60},
    				{header: '主单号', dataIndex: 'flightMainNo',width:65},
			        {header: '分单号', dataIndex: 'subNo',width:60},
			        {header: '航班号', dataIndex: 'flightNo',width:60},
			        {header: '代理公司', dataIndex: 'cusName',width:80},
			        {header: '收货人', dataIndex: 'consignee',width:60},
    				{header: '收货人地址', dataIndex: 'addr',width:120},
    			    {header: '配送方式', dataIndex: 'distributionMode',width:60},
    			    {header: '提货方式', dataIndex: 'takeMode',width:60},
    			    {header: '去向', dataIndex: 'goWhere',width:80},
    			    {header: '部门名称', dataIndex: 'departName',width:120}
			    ],
			store : dataStore,
			listeners: {
                    render: function(){
                        twobar.render(this.tbar);
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
	
	recordGrid.getStore().on('load',function(s,records){ 
        var girdcount=0; 
        s.each(function(r){ 
            if(r.get('status')==1){ 
                recordGrid.getView().getRow(girdcount).style.backgroundColor='#BFBFC1';
            }
            girdcount=girdcount+1; 
        }); 
	}); 
	
	
		function searchLog() {
			var stocktadeId=Ext.get("stocktadeId").dom.value;
			if(stocktadeId!=""){
				booleanId=true;
				select();
				Ext.apply(dataStore.baseParams={
			    	filter_EQL_stocktakeId:stocktadeId,
			    	limit : pageSize
			    });
			}else{
				booleanId=false;
				Ext.getCmp('submitbtn').setDisabled(true);
			
					   　dataStore.proxy = new Ext.data.HttpProxy({
								method : 'POST',
								url : sysPath+'/'+ralaListUrl+'?privilege='+privilege
							});
						var company=Ext.get("company").dom.value;
						var storageArea=Ext.get("storageArea").dom.value;
						var start='';
						var end='';
						if(Ext.getCmp('startDate').getValue()!=''){
							var start = Ext.getCmp('startDate').getValue().format("Y-m-d");
						}
						if(Ext.getCmp('endDate').getValue()!=''){
							var end = Ext.getCmp('endDate').getValue().format("Y-m-d");
						}
						
					    if (Ext.getCmp('comboselect').getValue() == 'EQL_status') {
			 		 		Ext.apply(dataStore.baseParams={
			 		 					filter_GED_createTime : start,
			    						filter_LED_createTime : end,
			    						filter_EQL_stocktakeId:'',
			    						checkItems : Ext.getCmp('comboselect').getValue(),
			    						itemsValue : Ext.getCmp('comboType').getValue(),
			    						filter_LIKES_cusName: company,
			    						filter_EQL_departId:bussDepart,
			    						filter_LIKES_storageArea:storageArea,
			    						limit : pageSize
			    			});
			 		 
			 		 	}else	if (Ext.getCmp('comboselect').getValue() == 'EQL_departId') {
			    			Ext.apply(dataStore.baseParams, {
			    						filter_GED_createTime : start,
			    						filter_LED_createTime : end,
			    						filter_EQL_stocktakeId:'',
			    						checkItems : Ext.getCmp('comboselect').getValue(),
			    						itemsValue : Ext.getCmp('comboTypeDepart').getValue(),
			    						filter_LIKES_cusName: company,
			    						filter_LIKES_storageArea:storageArea,
			    						limit : pageSize
			    			});
			    		} else if (Ext.getCmp('comboselect').getValue() == 'EQS_flightNo'){
			    			Ext.apply(dataStore.baseParams, {
			    						filter_GED_createTime : start,
			    						filter_LED_createTime : end,
			    						filter_EQL_stocktakeId:'',
			    						filter_EQL_departId:bussDepart,
			    						checkItems : Ext.getCmp('comboselect').getValue(),
			    						itemsValue : Ext.getCmp('comboTypeFlight').getValue(),
			    						filter_LIKES_cusName: company,
			    						filter_LIKES_storageArea:storageArea,
			    						limit : pageSize
			    			});
			    		}else{	
							Ext.apply(dataStore.baseParams, {
			    						filter_GED_createTime : start,
			    						filter_LED_createTime : end,
			    						filter_EQL_stocktakeId:'',
			    						filter_EQL_departId:bussDepart,
			    						checkItems : Ext.getCmp('comboselect').getValue(),
			    						itemsValue : Ext.getCmp('itemsValue').getValue(),
			    						filter_LIKES_cusName: company,
			    						filter_LIKES_storageArea:storageArea,
			    						limit : pageSize
			    			});
						}
			}
			dataStore.reload({
					params : {
						start : 0,
						limit : pageSize
					}
			});
		
	}
		
		recordGrid.on('click', function() {
			select();
		});
		
	/*	recordGrid.on('rowdblclick',function(grid,index,e){
		var _records = recordGrid.getSelectionModel().getSelections();
				
				if (_records.length ==1) {
					updateUser(_records[0]);
				}
			 	
		});*/
		
		
		function select(){
			var vnetmusicRecord = recordGrid.getSelectionModel().getSelections();
				var updatebtn = Ext.getCmp('submitbtn');
				if (vnetmusicRecord.length == 1) {
					if(booleanId){
						updatebtn.setDisabled(false);
					}
				} else if (vnetmusicRecord.length > 1) {
					if(booleanId){
						updatebtn.setDisabled(false);
					}
				} else {
					updatebtn.setDisabled(true);
				}
		}
		
		
		/*    var fields=[{name:"id",mapping:'id'},'ts','weight','departId','DNo','departName','piece','realPiece',
    			'storageArea','updateName','updateTime','createName','createTime',
    			'flightMainNo','consignee','addr','status','stocktadeId','subNo','flightNo',
    			'goWhere','distributionMode','takeMode','cusName','stocktakeId'];*/
    			
    	function storeOrder(){
    		var orderdata=[
	    		['t1_SUB_NO','分单号'],
	    		['t1_CONSIGNEE','收货人'],
	    		['t1_ADDR','收货人地址'],
	    		['t1_PIECE','件数'],
	    		['t3_GOWHERE','去向'],
	    		['t1_D_NO','配送单号'],
	    		['t1_WEIGHT','重量'],
	    		['t1_CREATE_TIME','入库时间'],
	    		['t1_FLIGHT_NO','航班号']  
	    		];
	    	var myReader = new Ext.data.ArrayReader({}, [
	    	{name:'orderId'},
			{name: 'orderName'}
			]);
			 var orderStore=new Ext.data.Store({
				data: orderdata,
				reader: myReader
			});
			 
			 var orderdata1=[
	    		['t3_CP_NAME','代理公司'],
	    		['t1_FLIGHT_MAIN_NO','主单号'],
	    		['t1_STORAGE_AREA','库存区域']
	    		];
	    	var myReader1 = new Ext.data.ArrayReader({}, [
	    	{name:'orderId'},
			{name: 'orderName'}
			]);
			 var orderStore1=new Ext.data.Store({
				data: orderdata1,
				reader: myReader1
			});
			 var orderGridpanl = new Ext.grid.GridPanel( {
				id : 'myrecordGrid2',
				height : 300,
				width : Ext.lib.Dom.getViewWidth(),
				autoScroll : true,
				autoExpandColumn : 1,
				frame : false,
				loadMask : true,
				stripeRows : true,
				viewConfig : {
					forceFit : true,
					scrollOffset : 0
				},
				cm : new Ext.grid.ColumnModel( [
						new Ext.grid.CheckboxSelectionModel(), {
							header : '备选排序字段',
							dataIndex : 'orderId',
							disabled : true,
							hidden : true
						}, {
							header : '备选排序字段',
							dataIndex : 'orderName'
						} ]),
				sm : new Ext.grid.CheckboxSelectionModel(),
				ds : orderStore
			});
			  var orderGridpanl2 = new Ext.grid.GridPanel( {
				id : 'myrecordGri',
				height : 300,
				width : Ext.lib.Dom.getViewWidth(),
				autoScroll : true,
				autoExpandColumn : 1,
				frame : false,
				loadMask : true,
				stripeRows : true,
				viewConfig : {
					forceFit : true,
					scrollOffset : 0
				},
				cm : new Ext.grid.ColumnModel( [
						new Ext.grid.CheckboxSelectionModel(), {
							header : '排序字段',
							dataIndex : 'orderId',
							disabled : true,
							hidden : true
						}, {
							header : '排序字段',
							dataIndex : 'orderName'
						} ]),
				sm : new Ext.grid.CheckboxSelectionModel(),
				ds : orderStore1
			});
    		var form = new Ext.form.FormPanel({
					frame : true,
					border : false,
					bodyStyle : 'padding:5px 5px 5px',
					labelWidth : 70,
					labelAlign :'right',
					width : 320,
					items:[{labelAlign : 'top',
					                    xtype : 'textfield',
			        	                fieldLabel: '仓管员<span style="color:red">*</span>', 
				        			    name: 'oprMan',
				        			    maxLength : 20,
				        			    value:userName,
				        			    allowBlank : false,
						                blankText : '必须填写仓管员',
						                anchor : '95%'
					                  },{labelAlign : 'top',
										xtype : 'combo',
										fieldLabel: '库存区域',
										typeAhead:false,
										forceSelection : true,
										minChars : 1,
										triggerAction : 'all',
										store: areaStore,
										pageSize : comboSize,
										queryParam : 'filter_LIKES_areaName',
										displayField : 'storageArea',
										valueField : 'storageArea',
										hiddenName : 'storageArea',
										anchor : '95%',
										emptyText : "请选择库存区域"
						},{
						 layout : 'column',
						 items:[
						 {
							 layout:'column',
							 columnWidth:.4,
							 items:[
								 orderGridpanl
								]
							 },{
    						 layout:'column',
    						 buttonAlign:'center',
    						 columnWidth:.2,
    						 items:[
    							 {html : '<pre><p></p><p> &nbsp;</p><p> &nbsp;</p><p> </p><p> </p><p> </p><p> </p><p> </p></pre>'},
    							 {xtype:'button',
    							  text:'==>>',
    							  width:50,
    							  handler:function(){
    								 var order1=orderGridpanl.getSelectionModel().getSelections();
    								 for(var i=0;i<order1.length;i++){
    									 orderStore.remove(order1[i]);
    								 }
    								 orderStore1.add(order1);
    								 form.render();
    							  }
    							  },
    								{html:'<pre><p> </p></pre>'},
    							    {xtype:'button',
    							     text:'<<==',
    							     width:50,
    							     handler:function(){
    								     var order2=orderGridpanl2.getSelectionModel().getSelections();
    								     for(var i=0;i<order2.length;i++){
    									     orderStore1.remove(order2[i]);
    								     }
    								     orderStore.add(order2);
    								     form.render();
    							     } 
    							    },
    							    {xtype:'hidden',name:'orderFields',id:'orderFields'}
    							 ]
    						},{
    						 layout:'column',
    						 columnWidth:.4,
    						 items:[
    							orderGridpanl2
    							 ]
    						}]
						}]
    		});
    		var win = new Ext.Window({
			title : '添加排序信息',
			width : 350,
			closeAction : 'hide',
			plain : true,
			modal : true,
			items : form,
			buttonAlign : "center",
			buttons : [{
				text : "打印",
				iconCls : 'save',
				handler : function() {
				    var _record=recordGrid.getSelectionModel().getSelections();
				    orderGridpanl2.getSelectionModel().selectAll();
				    var record=orderGridpanl2.getSelectionModel().getSelections();
				    var orderName="";
				    var carArriveIds="";
				    for(var i=0;i<record.length;i++){
				 	    if(i==0) { orderName=record[i].data.orderId;}
				    	if(i!=0) orderName=orderName+","+record[i].data.orderId;
				    }
			
				   Ext.getCmp("orderFields").setValue(orderName);
					if (form.getForm().isValid()) {
						Ext.Msg.confirm(alertTitle,'您确定要打印这些数据吗?',function(btnYes) {
								if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
										form.getForm().submit({
											url : sysPath + "/stock/oprStocktakeAction!save.action?privilegee="+privilege,
											params : {
												privilege:privilege
											},
											waitMsg : '正在打印数据...',
											success : function(form, action) {
												win.hide();
												Ext.Msg.alert(alertTitle,"数据保存成功！",function(){
													  parent.print('12',{print_inventoryNum:action.result.msg});
												});
											},
											failure : function(form, action) {
												if (action.faliureType == Ext.form.Action.SERVER_INVALID) {
													Ext.Msg.alert(alertTitle, "服务器连接异常，请联系管理员！");
												} else {
													if (action.result.msg) {
														win.hide();
														Ext.Msg.alert(alertTitle,
																action.result.msg);
													}
												}
											}
										});
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
			
							
			
 
	 function sumitStore(_records){
	 		     Ext.Msg.confirm(alertTitle,'您确定要提交这<span style="color:red">&nbsp;'+ _records.length + '&nbsp;</span>行数据吗?',function(btnYes) {
										if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
									    	var ids = "";
    										for(var i = 0; i < _records.length; i++) {
    											if(i==0)                                                   
													ids += "aa["+i+"].id="+_records[i].get("id")+"&aa["+i+"].realPiece="+_records[i].get("realPiece");
												else
													ids += "&aa["+i+"].id="+_records[i].get("id")+"&aa["+i+"].realPiece="+_records[i].get("realPiece");
											} 
											form1.getForm().doAction('submit', {
											url : sysPath+ "/stock/oprStocktakeDetailAction!saveList.action?privilege="+privilege,
											method : 'post',
											params : ids,
											waitMsg : '正在提交数据...',
											success : function(form1, action) {
												Ext.Msg.alert(
														alertTitle,
														"数据提交成功",
														function() {
															dataStore.reload()
														});
											},
											failure : function(form1, action) {
												Ext.Msg.alert(
														alertTitle,
														"数据提交失败",
														function() {
															dataStore.reload();
															 select();
														});
											}
										});
										}
									});
	 
	 
	 
	 }
	 

	function reload(){
		 dataStore.reload()
	}

});



















	

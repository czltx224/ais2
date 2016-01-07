//自提出库JS
	Ext.QuickTips.init();
	var privilege=65;
	var comboxPage=comboSize;
	var saveUrl="stock/oprSignAction!saveSignStatus.action";
	var customerGridSearchUrl="sys/customerAction!list.action";
	var areaListUrl="stock/oprStoreAreaAction!list.action";
	var ralaListUrl="stock/oprOutStoreAction!ralaList.action";
	var dictionaryUrl='sys/dictionaryAction!ralaList.action';
	var booleanId =false; 
	
Ext.onReady(function(){
	Ext.QuickTips.init();
	var rownum=new Ext.grid.RowNumberer({
		header:'No.',
        width : 25,
		sortable : true
	});
    var fields=[{name:"dno",mapping:'dno'},{name:'hiddenValueAddFee',mapping:'cusValueAddFee'},'ts','goods','declaredValue','goodsStatus','cpValueAddFee','cusValueAddFee','customerService',
    	'cusId','cpName','flightNo','flightDate','flightTime','trafficMode','flightMainNo','subNo','distributionMode','takeMode','receiptType',
    	'consignee','consigneeTel','consigneePho','city','town','addr','piece','cqWeight','cusWeight','bulk','inDepart','curDepart','endDepart',
    	'gowhere','distributionDepart','greenChannel','urgentService','wait','sonderzug','carType','remark','status','barCode','paymentCollection',
    	'traFee','traFeeRate','cpRate','cpFee','consigneeRate','whoCash','consigneeFee','createName','createTime',
    	'updateName','updateTime','ts','faxMainId','storePiece','storeDate','valuationType','noticeDate','storeTime',
    	'storeFee','totalPaymentCollection','totalCpValueAddFee','sysTotalPaymentCollection','IDNumber','IDNumberTwo','consigneeTwo'];
    			
    var jsonread= new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'},
                    fields);
                              
	var sm = new Ext.grid.CheckboxSelectionModel({
		width:25,
		singleSelect :true,
		moveEditorOnEnter:true, 
		id:'checkId',
		name:'checkId'
	});
	
	var dictionaryStore = new Ext.data.Store({ 
            storeId:"dictionaryStore",                        
            proxy: new Ext.data.HttpProxy({url:sysPath+"/"+dictionaryUrl,method:'post'}),
            baseParams:{
             	privilege:16,
				filter_EQL_basDictionaryId:16
            },
            reader: new Ext.data.JsonReader({
            root: 'result', totalProperty: 'totalCount'
           }, [  {name:'typeName', mapping:'typeName',type:'string'}           
              ])    
    });
	dictionaryStore.load();
	
	//提货类型
	var doGoodsStore=new Ext.data.SimpleStore({
			 auteLoad:true, //此处设置为自动加载
  			 data:[['1','机场自提'],['2','市内自提']],
   			 fields:["id","name"]
	});
	
	//配送方式
	var distributionStore = new Ext.data.Store({ 
            storeId:"distributionStore",                        
            proxy: new Ext.data.HttpProxy({url:sysPath+"/"+dictionaryUrl,method:'post'}),
            baseParams:{
             	privilege:16,
				filter_EQL_basDictionaryId:4
            },
            reader: new Ext.data.JsonReader({
            root: 'result', totalProperty: 'totalCount'
           }, [  {name:'typeName', mapping:'typeName',type:'string'}           
              ])    
    });
    distributionStore.load();
	
	var	menuStore = new Ext.data.Store({
			storeId : "menuStore",
			proxy : new Ext.data.HttpProxy({
				url : sysPath + "/" + customerGridSearchUrl,method:'post'
			}),
			reader : new Ext.data.JsonReader({
				root : 'result',
				totalProperty : 'totalCount'
			}, [    
				   {name : 'cpName',mapping :'cusName'}
			])
	});
	menuStore.load();
	
	var flightStore=new Ext.data.Store({   //
		    storeId:"flightStore",                        
            proxy: new Ext.data.HttpProxy({url:sysPath+"/sys/basFlightAction!list.action",method:'post'}),
            baseParams:{privilege:62},
            reader: new Ext.data.JsonReader({
            root: 'result', totalProperty: 'totalCount'
           }, [  {name:'flightNo', mapping:'flightNumber',type:'string'}
              ])
   	});
	
	var dataStore = new Ext.data.Store({
                proxy: new Ext.data.HttpProxy({url:sysPath+"/"+ralaListUrl}),
                baseParams:{limit: pageSize},
                sortInfo : {field: "status", direction: "ASC"},
                reader:jsonread,
                listeners:{
	                'load':function(s,records){ 
					        var girdcount=0; 
					        s.each(function(r){ 
					            if(r.get('wait')==1){ 
					                recordGrid.getView().getRow(girdcount).style.backgroundColor='gray';
					            }
				            	girdcount=girdcount+1; 
					        })
	        			}	
                }
    });
 	
 	var twobar = new Ext.Toolbar({
		    	items : ['主单号:',
						 {xtype : 'textfield',
			        	                id:'searchFlightMainNo',
						                width:80,
						                enableKeyEvents:true,
							            listeners : {
							 				keyup:function(textField, e){
							                     if(e.getKey() == 13){
							                     	searchLog();
							                     }
							 				}
							 			}
						              },'-','配送单号:',
						 {xtype : 'numberfield',
			        	                fieldLabel: '配送单号', 
			        	                id:'dNo',
			        	                minValue:0,
			        	                minText:'不符合最小值要求',
				        			    name: 'dNo',
				        			    maxLength : 10,
						                anchor : '95%',
						                width:80,
						                enableKeyEvents:true,
							            listeners : {
							 				keyup:function(textField, e){
							                     if(e.getKey() == 13){
							                     	searchLog();
							                     }
							 				}
							 			}
						              },'-',
    	   				 '收货人姓名:',
		    			{xtype : 'textfield',
			        	                fieldLabel: '收货人姓名', 
			        	                id:'goodsName',
				        			    name: 'goodsName',
						                anchor : '95%',
						                width:80,
						                enableKeyEvents:true,
							            listeners : {
							 				keyup:function(textField, e){
							                     if(e.getKey() == 13){
							                     	searchLog();
							                     }
							 				}
							 			}
						              },'-','收货人电话:',
    	   				 		{xtype : 'numberfield',
			        	                fieldLabel: '收货人电话',
			        	                id:'phone',
				        			    name: 'phone',
						                anchor : '95%',
						                width:80,
						                enableKeyEvents:true,
						                listeners : {
							 				keyup:function(textField, e){
							                     if(e.getKey() == 13){
							                     	searchLog();
							                     }
							 				}
							 			}
						              },'-','发货代理:',{
										xtype : 'combo',
										id:'company',
										hiddenId : 'dictionaryName',
						    			hiddenName : 'dictionaryName',
										triggerAction : 'all',
								//		mode:'local',
										store : menuStore,
										width:120,
										queryParam : 'filter_LIKES_cusName',
										minChars : 1,
										allowBlank : true,
										emptyText : "请选择代理公司名称",
										forceSelection : true,
										fieldLabel:'代理公司名称',
										editable : true,
										listWidth:245,
										pageSize:50,
										displayField : 'cpName',//显示值，与fields对应
										valueField : 'cpName',//value值，与fields对应
										name : 'cpName',
										anchor : '100%',
										enableKeyEvents:true,
							            listeners : {
							 				keyup:function(textField, e){
							                     if(e.getKey() == 13){
							                     	searchLog();
							                     }
							 				}
							 			}
								    },'-',{
								     text : '<b>查&nbsp;&nbsp;询</b>',
								     id : 'btn',
								     iconCls : 'btnSearch',
									 handler : searchLog
								}]
		});
     Ext.override(Ext.grid.RowSelectionModel, {
      selectRow: function(index, keepExisting, preventViewNotify) {
	        if (this.isLocked() || (index < 0 || index >= this.grid.store.getCount()) 
	        				|| (keepExisting && this.isSelected(index)) 
	        				   || (Number(this.grid.store.getAt(index).get("wait")) == 1)) {
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
		tbar:[{text:'<b>导出</b>',iconCls:'sort_down',tooltip : '导出数据',
		 		handler:function(){
		 				parent.exportExl(recordGrid);
		 		}
		 	},'&nbsp;&nbsp;',{
				text : '<B>提货签收</B>',
				id : 'submitbtn',
				disabled : true,
				tooltip : '提货签收',
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
			},'-'
			/*,{
				text : '<B>打印测试</B>',
				iconCls:'printBtn',
				handler : function() {
						var _records = recordGrid.getSelectionModel().getSelections();
						if (_records.length == 0) {
							Ext.Msg.alert(alertTitle, '请选择一条您需要提交的数据');
							return false;
						}else {
							parent.print('1',{print_dnos:_records[0].data.dno});
						}
				}
			}*/,'&nbsp;&nbsp;',
			{	xtype:'textfield',
	 	        id : 'itemsValue',
		        name : 'itemsValue',
		        hidden : true,
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
				id:'comboTypeFlight',
				hidden : true,
				triggerAction : 'all',
				store : flightStore,
				minChars : 1,
				editable:true,
				width:110,
				listWidth:220,
				allowBlank : true,
				listWidth:245,
				allowBlank : true,
				emptyText : "请选择航班号",
				editable : true,
			    queryParam : 'filter_LIKES_flightNumber',
				pageSize:comboxPage,
				displayField : 'flightNo',//显示值，与fields对应
				valueField : 'flightNo',//value值，与fields对应
				name : 'flightNo',
				anchor : '100%',
				enableKeyEvents:true,
				    listeners : {
						keyup:function(textField, e){
				         if(e.getKey() == 13){
				                   	searchLog();
				         }
					}
				}
		    },{
	    		xtype : 'datefield',
	    		id : 'startDate',
	    		format : 'Y-m-d',
	    		emptyText : "选择开始时间",
	    		anchor : '95%',
	    		value: new Date().add(Date.DAY, -7) ,// filter_GED_createTime : new Date().add(Date.DAY, -7).format("Y-m-d"),
	    		width : 100,
	    		listeners : {
	    			'select' : function() {
	    			   var start = Ext.getCmp('startDate').getValue().format("Y-m-d");
	    			   Ext.getCmp('endDate').setMinValue(start);
	    		     }
	    		}
    		},'&nbsp;',{
	    		xtype : 'datefield',
	    		id : 'endDate',
	    		format : 'Y-m-d',
	    		value:new Date(),
	    		emptyText : "选择结束时间",
	    		width : 100,
	    		anchor : '95%',
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
    					['LIKES_flightNo', '航班号'],
    					['EQD_storeDate', '入库时间'],
    					['EQD_createTime', '录单时间'],
    					['EQL_piece', '件数'],
    					['EQL_storePiece','库存件数'],
    					['LIKES_goods', '品名'],
    					['LIKES_goodsStatus', '货物状态']],
    			emptyText : '选择查询类型',
    			forceSelection : true,
    			listeners : {
    				'render':function(combo, record, index){
    					combo.setValue('EQD_createTime');
    				},
    				'select' : function(combo, record, index) { 
    					if (combo.getValue() == 'EQD_storeDate') {
    						Ext.getCmp("startDate").enable();
    						Ext.getCmp("startDate").show();

    						Ext.getCmp("endDate").enable();
    						Ext.getCmp("endDate").show();
    								
    						Ext.getCmp("itemsValue").disable();
    						Ext.getCmp("itemsValue").hide();
    						Ext.getCmp("itemsValue").setValue("");
    						
    						Ext.getCmp("comboTypeFlight").disable();
    						Ext.getCmp("comboTypeFlight").hide();
    						Ext.getCmp("comboTypeFlight").setValue("");
    					}else  if(combo.getValue() == 'LIKES_flightNo') {
    						Ext.getCmp("startDate").disable();
    						Ext.getCmp("startDate").hide();
    					    Ext.getCmp("startDate").setValue("");

    						Ext.getCmp("endDate").disable();
    						Ext.getCmp("endDate").hide();
    						Ext.getCmp("endDate").setValue("");
    						
    						Ext.getCmp("comboTypeFlight").setValue("");
    						Ext.getCmp("comboTypeFlight").show();
    						Ext.getCmp("comboTypeFlight").enable();
    								
    						Ext.getCmp("itemsValue").disable();
    						Ext.getCmp("itemsValue").hide();
    						Ext.getCmp("itemsValue").setValue("");
    					}else if(combo.getValue() == 'EQD_createTime'){
    						Ext.getCmp("startDate").enable();
    						Ext.getCmp("startDate").show();

    						Ext.getCmp("endDate").enable();
    						Ext.getCmp("endDate").show();
    								
    						Ext.getCmp("itemsValue").disable();
    						Ext.getCmp("itemsValue").hide();
    						Ext.getCmp("itemsValue").setValue("");
    						
    						Ext.getCmp("comboTypeFlight").disable();
    						Ext.getCmp("comboTypeFlight").hide();
    						Ext.getCmp("comboTypeFlight").setValue("");
    					}else{  //
    						Ext.getCmp("startDate").disable();
    						Ext.getCmp("startDate").hide();
    					    Ext.getCmp("startDate").setValue("");

    						Ext.getCmp("endDate").disable();
    						Ext.getCmp("endDate").hide();
    						Ext.getCmp("endDate").setValue("");
    						
    				    	Ext.getCmp("itemsValue").enable();
    						Ext.getCmp("itemsValue").show();
    						
    						Ext.getCmp("comboTypeFlight").disable();
    						Ext.getCmp("comboTypeFlight").hide();
    						Ext.getCmp("comboTypeFlight").setValue("");
    					}
    				}
    			 }
    		},'-','&nbsp;&nbsp;','提货类型:',
    		 {
				xtype : 'combo',
				id:'doGoods',
				triggerAction : 'all',
				store : doGoodsStore,
				editable:true,
				forceSelection : true,
				mode : "local",//获取本地的值
				displayField : 'name',//显示值，与fields对应
				valueField : 'name',//value值，与fields对应
				name : 'doGoods',
				width : 100,
				enableKeyEvents:true,
				listeners : {
	          		 render:function(com){
	          		 	if(bussDepart==492){
		          		 	com.setValue('机场自提');
	          		 	}else{
	          		 		com.setValue('市内自提');
	          		 	}
	          		 }
	 				
	 			}
	    	},'-','&nbsp;&nbsp;','配送方式:',{
				xtype : 'combo',
				id:'distribution',
				triggerAction : 'all',
				store : distributionStore,
				forceSelection : true,
				fieldLabel:'配送方式',
				mode : "local",//获取本地的值
				emptyText : "请选择配送方式",
				//	editable:false,
				displayField : 'typeName',//显示值，与fields对应
				valueField : 'typeName',//value值，与fields对应
				name : 'distribution',
				width : 100,
				enableKeyEvents:true,
				listeners : {
					render:function(com){
	 				//	com.setRawValue('新邦');
	 					com.setValue('新邦');
	          		},
	 				keyup:function(numberfield, e){
	                     if(e.getKey() == 13 ){
							searchLog();
	                     }
	 				}
	 			}
	    	}],
			columns:[ rownum,sm,
			        {header: '货物状态', dataIndex: 'goodsStatus',width:80,sortable : true,  
        				renderer: function(v, metadata, record, rowIndex, columnIndex, store) {
        					if(record.get('wait')==1){
        						return v+'<font color=red><b>等</b></font>'
        					}else{
						 		return v;
        					}
        				}
        			},
        			{header: '代理公司', dataIndex: 'cpName',width:95,sortable : true},
			        {header: '配送单号', dataIndex: 'dno',width:80,sortable : true},
			        {header: '主单号', dataIndex: 'flightMainNo',width:60,sortable : true},
			        {header: '分单号', dataIndex: 'subNo',width:60,sortable : true},
			        {header: '入库时间', dataIndex: 'storeDate',width:80,sortable : true},
			        {header: '录单时间', dataIndex: 'createTime',width:80,sortable : true},
			        {header: '客服员', dataIndex: 'customerService',width:60,sortable : true},　
			        {header: '件数', dataIndex: 'piece',width:60,sortable : true},
			        {header: '库存件数', dataIndex: 'storePiece',width:60,sortable : true,
			        	renderer:function(v){
			    			if(v==''){
			    				return 0;
			    			}else{
			    			  return v ;
			    			}
			    		}
			    	},
			        {header: '应收金额', dataIndex: 'totalPaymentCollection',width:60,sortable : true},
			        {header: '收货人姓名', dataIndex: 'consignee',width:80,sortable : true},
			        {header: '收货人电话', dataIndex: 'consigneeTel',width:100,sortable : true},
			        {header: '收货人地址', dataIndex: 'addr',width:150,sortable : true},
			        {header: '备注', dataIndex: 'remark',width:100,sortable : true},
			        {header: '品名', dataIndex: 'goods',width:80,sortable : true},
			        {header: '重量', dataIndex: 'cqWeight',width:60,sortable : true},
			        {header: '航班号', dataIndex: 'flightNo',width:60,sortable : true},
			        {header: '配送方式', dataIndex: 'distributionMode',width:60,sortable : true},
			        {header: '提货方式', dataIndex: 'takeMode',width:60,sortable : true}
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
	
	
		function searchLog() {
			Ext.getCmp('submitbtn').setDisabled(true);					
			var goodsName=Ext.get("goodsName").dom.value;  // 收货人姓名
			var dNo=Ext.get("dNo").dom.value;　　// 配送单号　　stocktadeId
			var phone=Ext.get("phone").dom.value;　　// 收货人电话
			var gongshi =Ext.getCmp('company').getValue();
			var dis=Ext.getCmp('distribution').getValue();
			var searchFlightMainNo = Ext.get('searchFlightMainNo').dom.value;
			
			if(dNo==""){
				 dataStore.baseParams={
				// 	filter_GED_createTime : new Date().add(Date.DAY, -7).format("Y-m-d"),
	       			filter_EQS_takeMode:Ext.getCmp('doGoods').getValue(),
				 	filter_LIKES_consigneeTel: phone,
					filter_LIKES_consignee: goodsName,
					filter_LIKES_cpName:gongshi,
					filter_EQS_distributionMode:dis,
					filter_EQL_dno:dNo,
					filter_LIKES_flightMainNo:searchFlightMainNo,
	   				privilege:privilege,
	   				filter_EQL_signStatus:0,
					limit : pageSize
				 };
				var vv= Ext.getCmp('comboselect').getValue();
		    	var start='';
		    	var end ='';
		    	if(Ext.getCmp('startDate').getValue()!=""){
		    		var start = Ext.getCmp('startDate').getValue().format("Y-m-d");
		    	}
		    	if(Ext.getCmp('endDate').getValue()!=""){
		    		var end = Ext.getCmp('endDate').getValue().format("Y-m-d");
		    	}
		    	
			    if(vv== 'EQD_storeDate') {
	 		 		 Ext.apply(dataStore.baseParams, {
						filter_GED_storeDate : start,
	        			filter_LED_storeDate : end
	    			});
	    		}else if(vv=='EQD_createTime'){
	    			 Ext.apply(dataStore.baseParams, {
						filter_GED_createTime : start,
	        			filter_LED_createTime : end
	    			});
	    		}else if(vv=='LIKES_flightNo'){
	    			 Ext.apply(dataStore.baseParams, {
						filter_LIKES_flightNo : Ext.getCmp('comboTypeFlight').getRawValue()
	    			});
	    		}else{	
					 Ext.apply(dataStore.baseParams, {
						checkItems : Ext.getCmp('comboselect').getValue(),
						itemsValue : Ext.getCmp('itemsValue').getRawValue()
	    			});
				}
			}else{
				 dataStore.baseParams= {
					filter_EQL_dno:dNo,
					privilege:privilege,
					limit : pageSize
	    		 };
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
		
		recordGrid.on('rowdblclick',function(grid,index,e){
				var _records = recordGrid.getSelectionModel().getSelections();
				if (_records.length ==1) {
					
						sumitStore(_records);

					
				}
			 	
		});
		
		
		function select(){
			var vnetmusicRecord = recordGrid.getSelectionModel().getSelections();
				var updatebtn = Ext.getCmp('submitbtn');
				if (vnetmusicRecord.length == 1) {
					updatebtn.setDisabled(false);
				} else if (vnetmusicRecord.length > 1) {
					updatebtn.setDisabled(true);
				} else {
					updatebtn.setDisabled(true);
				}
		}
		
							 var form = new Ext.form.FormPanel({
								labelAlign : 'left',
								frame : true,
							//	url:sysPath+'/user/userAction!ralaList.action?privilege=17',
							
								bodyStyle : 'padding:5px 5px 5px',
							    width : 750,
								reader :jsonread,
							
							/*	listeners : {
								'render' : function() {
										this.findByType('textfield')[0].focus(true, true); //第一个textfield获得焦点
									}
								},*/
								labelAlign : "right",
						        items : [{
											layout : 'column',
											items : [{
														columnWidth : .33,
														layout : 'form',
														items : [{
																	xtype : 'textfield',
																	labelAlign : 'left',
																	fieldLabel : '代理公司',
																	readOnly : true,
																	name : 'cpName',
																	anchor : '95%'
																},{
																	xtype : 'textfield',
																	labelAlign : 'left',
																	fieldLabel : '配送单号',
																	readOnly : true,
																	name : 'dno',
																	id:'dno',
																	anchor : '95%'
																},{
																	xtype : 'textfield',
																	labelAlign : 'left',
																	fieldLabel : '航班号',
																	readOnly : true,
																	name : 'flightNo',
																	anchor : '95%'
																},{
																	xtype : 'textfield',
																	labelAlign : 'left',
																	fieldLabel : '货物名称',
																	readOnly : true,
																	name : 'goods',
																	anchor : '95%'
																},{
																	xtype : 'textfield',
																	labelAlign : 'left',
																	fieldLabel : '体积',
																	readOnly : true,
																	id:'bulk',
																	name : 'bulk',
																	anchor : '95%'
																},{
																	xtype : 'textfield',
																	labelAlign : 'left',
																	fieldLabel : '<b>预付总额</b>',
																	readOnly : true,
																	name : 'cpFee',
																	anchor : '95%'
																},{
																	xtype : 'numberfield',
																	labelAlign : 'left',
																	fieldLabel : '<b>到付提送费率</b><span style="color:red">*</span>',
																	id:'consigneeRate',
																	allowBlank : false,
																	blankText : "到付提送费率不能为空！",
																	name : 'consigneeRate',
																	regex:/^\d+(\.\d+)?$/,
																	regexText : "输入的数字格式不符合要求！", 
																	anchor : '95%',
																	enableKeyEvents: true,   
                   												    listeners:{
                   												    'focus':function(){  
																				Ext.getCmp("consigneeRate").selectText();
                        											  	},                              
                        											   'blur':function(){
                        											     var v=Ext.getCmp('valuationType').getValue(); 
                        											     var consigneeRate=Ext.getCmp('consigneeRate').getValue();
                        											     var fee=parseFloat(consigneeRate).toFixed(2);
                        											     if(v=="体积"){
                        											    	　var weight=Ext.getCmp('bulk').getValue();
                        											    	　var con=parseFloat(weight)*fee;
                        											   		　Ext.getCmp('consigneeFee').setValue(Math.round(con));
                        											   	 }else if(v=="件数"){
                        											   		 var weight=Ext.getCmp('piece').getValue();
                        											    	　var con=parseFloat(weight)*fee;
                        											   		　Ext.getCmp('consigneeFee').setValue(Math.round(con));
                        											   	 }else{
                        											   	 　　　var weight=Ext.getCmp('cusWeight').getValue();
                        											    	　var con=parseFloat(weight)*fee;
                        											   		　Ext.getCmp('consigneeFee').setValue(Math.round(con));
                        											   	 }
                        											   	 
                        											   	 var paymentCollection=Ext.getCmp('paymentCollection').getValue();　　// 代收货歀
                        											   	 if(paymentCollection==''){
                        											   	 	paymentCollection=0;
                        											   	 }　
                        											     var consigneeFee=Ext.getCmp('consigneeFee').getValue();　　// 提送费
                        											     var cusValueAddFee= Ext.getCmp('cusValueAddFee').getValue();
                        											     var v3=parseFloat(cusValueAddFee)+parseFloat(paymentCollection)+parseFloat(consigneeFee);
                        											     Ext.getCmp('totalPaymentCollection').setValue(Math.round(v3));   // 总费用
                        											   	 
                        											   	 
                        											   },
															 			keyup:function(numberfield, e){
															                 if(e.getKey() == 13||e.getKey() == 40){
																				Ext.getCmp("consigneeFee").focus();	
															                 }
															                 if(e.getKey() == 38){
																				Ext.getCmp("sumitButton").focus();	
															                 }
															 			}
                        											}  
																},{
																	xtype : 'textfield',
																	labelAlign : 'left',
																	fieldLabel : '在库时长',
																	readOnly:true,
																	name : 'storeTime',
																	anchor : '95%'
																},{
																	xtype : 'textfield',
																	labelAlign : 'left',
																	readOnly : true,
																	id:'consigneeCard',
																	length:10,
																	fieldLabel : '收货人姓名',
																	name : 'consignee',
																	anchor : '95%'
																},{
																	xtype : 'textfield',
																	labelAlign : 'left',
																	fieldLabel : '代提人姓名',
																	name : 'consigneeTwo',
																	id:'consigneeTwo',
																	anchor : '95%',
																	enableKeyEvents: true,   
                   											        listeners:{
                   												/*     'blur':function(textfield){
                   												     	if(textfield.getValue()!==""){
                   												     	//	Ext.getCmp("IDNumber").getEl().dom.validator=null;
                   												     	//	Ext.getCmp("IDNumber").validate();
                   												       		Ext.getCmp('IDNumber').disable(); 
                   												     	}else{
                   												     		Ext.getCmp('IDNumber').enable();
                   												     	}
                   												     },*/
                   												    'focus':function(){  
																				Ext.getCmp("consigneeTwo").selectText();
                        											  	}, 
																		keyup:function(numberfield, e){
														                     if(e.getKey() == 13||e.getKey() == 40){
																				Ext.getCmp("mycomboID").focus(true,true);
														                     }
														                      if(e.getKey() == 38){
																				Ext.getCmp("IDNumber").focus();
														                     }
														 			}
																}
																}]

													}, {
														columnWidth : .33,
														layout : 'form',
														items : [{
																	xtype : 'textfield',
																	labelAlign : 'left',
																	fieldLabel : '收货人电话',
																	readOnly : true,
																	name : 'consigneeTel',
																	anchor : '95%'
																},{
																	xtype : 'textfield',
																	labelAlign : 'left',
																	fieldLabel : '主单号',
																	readOnly : true,
																	name : 'flightMainNo',
																	anchor : '95%'
																},{fieldLabel: '入库时间',
																    name: 'storeDate',
																    labelAlign : 'left',
																    xtype:'datefield',
																    format : 'Y-m-d',
																    readOnly : true,
																	anchor : '100%'
																},{
																	xtype : 'textfield',
																	id:'piece',
																	labelAlign : 'left',
																	fieldLabel : '件数',
																	readOnly : true,
																	name : 'piece',
																	anchor : '95%'
																},{																
																	xtype : 'textfield',
																	labelAlign : 'left',
																	id:'valuationType',
																	fieldLabel : '<b>计价方式</b>',
																	readOnly : true,
																	name : 'valuationType',
																	anchor : '95%'
																},{
																	xtype : 'textfield',
																	labelAlign : 'left',
																	fieldLabel : '<b>代收货款</b>',
																	readOnly : true,
																	id:'paymentCollection',
																	name : 'paymentCollection',
																	anchor : '95%'
																},{
																	xtype : 'numberfield',
																	labelAlign : 'left',
																	id:'consigneeFee',
																	fieldLabel : '<b>到付提送费</b><span style="color:red">*</span>',
																    allowBlank : false,
																    regex:/^\d+(\.\d+)?$/,
																	regexText : "输入的数字格式不符合要求！", 
																	blankText : "到付提送费不能为空！",
																	name : 'consigneeFee',
																	anchor : '95%',
																	enableKeyEvents: true,                
                   												    listeners:{
                   												   		'focus':function(){  
																				Ext.getCmp("consigneeFee").selectText();
                        											  	},                            
                        											   'blur':function(){  
                        											     var paymentCollection=Ext.getCmp('paymentCollection').getValue();　//代收货款
                        											      if(paymentCollection==''){
                        											   	 	paymentCollection=0;
                        											   	 }　
                        											  	 var consigneeFee=Ext.getCmp('consigneeFee').getValue();　　// 提送货费
                        											  	  if(consigneeFee==''){
                        											   	 	consigneeFee=0;
                        											   	  }
                        											  	 var cusValueAddFee= Ext.getCmp('cusValueAddFee').getValue();  　//增值服务费
                        											     var v3=parseFloat(paymentCollection)+parseFloat(consigneeFee)+parseFloat(cusValueAddFee);
                        											     var v=Ext.getCmp('totalPaymentCollection').setValue(Math.round(v3));
                        											     
                        											   },keyup:function(numberfield, e){
														                     if(e.getKey() == 13||e.getKey() == 40){
																				Ext.getCmp("cusValueAddFee").focus();
														                     }
														                      if(e.getKey() == 38){
																				Ext.getCmp("consigneeRate").focus();
														                     }
														 				}
                        											}  
																},{
																	xtype : 'numberfield',
																	labelAlign : 'left',
																	fieldLabel : '<b>仓储费</b><span style="color:red">*</span>',
																	name : 'storeFee',
																	id:'storeFee',
																	minvalue:0,
																	allowBlank : false,
																	blankText : "仓储费不能为空！",
																	regex:/^\d+(\.\d+)?$/,
																	regexText : "输入的数字格式不符合要求！", 
																	anchor : '95%',
																	enableKeyEvents: true,                
                   												    listeners:{
                   												    'focus':function(){  
																				Ext.getCmp("storeFee").selectText();
                        											  	},                        
                        											   'blur':function(){
                        											     var cpValueAddFee=Ext.getCmp('hiddenValueAddFee').getValue();  //增值服务费，不包仓储费
                        											   	 var storeFee=Ext.getCmp('storeFee').getValue();　　// 仓储费
                        											   	 if(storeFee==''){
                        											   	 	storeFee=0;
                        											   	 }
                        											   	 var total = parseFloat(cpValueAddFee)+parseFloat(storeFee);
                        										
                        											   	 Ext.getCmp('cusValueAddFee').setValue(Math.round(total));　 // 总的增值服务费
                        											   	 
                        											     var paymentCollection=Ext.getCmp('paymentCollection').getValue();　　// 代收货歀　　
                        											      if(paymentCollection==''){
                        											   	 	paymentCollection=0;
                        											   	 }　
                        											     var consigneeFee=Ext.getCmp('consigneeFee').getValue();　　// 提送费
                        											     var v3=total+parseFloat(paymentCollection)+parseFloat(consigneeFee);
                        											     Ext.getCmp('totalPaymentCollection').setValue(Math.round(v3));   // 总费用
                        											     
                        											   },keyup:function(numberfield, e){
														                     if(e.getKey() == 13||e.getKey() == 40){
																				Ext.getCmp("totalPaymentCollection").focus();
														                     }
														                     if(e.getKey() == 38){
																				Ext.getCmp("cusValueAddFee").focus();
														                     }
														 				}
                        											}  
																},{	xtype : 'combo',
																	id:'mycombo',
																	name:'mycombo',
																	triggerAction : 'all',
																	store : dictionaryStore,
																	allowBlank : false,
																	emptyText : "请选择证件类型",
																	mode : "local",
																	fieldLabel : '证件类型',
																	valueField : 'typeName',//value值，与fields对应
																	displayField : 'typeName',//显示值，与fields对应
																	hiddenName:'typeName',
																	enableKeyEvents: true,   
																	listeners: {  
																         afterRender: function(combo) {
																       　　	  var firstValue = combo.store.getAt(2).get('typeName'); 
																		      if(combo.getValue()==null || combo.getValue()=='') {
																		       　combo.setValue(firstValue);//同时下拉框会将与name为firstValue值对应的 text显示   
																		      }
																	       },
																	 	 keyup:function(numberfield, e){
														                 /*    if(e.getKey() == 13){
														                     Ext.getCmp("IDNumber").getEl().dom.regex=/^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}|[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{4}$/;
																				Ext.getCmp("IDNumber").focus();
														                     }*/
														                      if(e.getKey() == 38){
																				Ext.getCmp("totalPaymentCollection").focus();
														                     }
														 				}
																    } ,
																	anchor : '95%'
														    	},{	xtype : 'combo',
																	id:'mycomboID',
																	triggerAction : 'all',
																	store : dictionaryStore,
																	mode : "local",
																	allowBlank : false,
																	emptyText : "请选择证件类型",
																	fieldLabel : '证件类型',
																	valueField : 'typeName',//value值，与fields对应
																	displayField : 'typeName',//显示值，与fields对应
																	hiddenName:'typeNameTwo',
																	listeners: {   
																        afterRender: function(combo) {   
																       　　var firstValue = combo.store.getAt(2).get('typeName'); 
																       if(combo.getValue()==null || combo.getValue()=='') 
																       　　combo.setValue(firstValue);//同时下拉框会将与name为firstValue值对应的 text显示   
																         },keyup:function(numberfield, e){
														                     if(e.getKey() == 13){
																				Ext.getCmp("IDNumberTwo").focus();
														                     }
														                      if(e.getKey() == 38){
																				Ext.getCmp("consigneeTwo").focus();
														                     }
														 				}
																    } ,
																	anchor : '95%'
														    	}]
													},{
														columnWidth : .33,
														layout : 'form',
														items : [{
																	xtype : 'textfield',
																	labelAlign : 'left',
																	fieldLabel : '收货人地址',
																	readOnly : true,
																	name : 'addr',
																	anchor : '95%'
																},{
																	xtype : 'textfield',
																	labelAlign : 'left',
																	fieldLabel : '分单号',
																	readOnly : true,
																	name : 'subNo',
																	anchor : '95%'　　
																},{
																	xtype : 'textfield',
																	labelAlign : 'left',
																	fieldLabel : '航班落地时间',
																	readOnly : true,
																	name : 'flightTime',
																	anchor : '95%'
																},{
																	xtype : 'textfield',
																	labelAlign : 'left',
																	fieldLabel : '重量',
																    readOnly : true,
																    id:'cusWeight',
																	name : 'cusWeight',
																	anchor : '95%'
																},{
																	xtype : 'textfield',
																	labelAlign : 'left',
																	fieldLabel : '提货方式',　　
																	readOnly : true,
																	name : 'takeMode',
																	anchor : '95%'
																},{
																	xtype : 'textfield',
																	labelAlign : 'left',
																	fieldLabel : '<b>签单类型</b>',
																	readOnly : true,
																	id:'receiptType',
																	name : 'receiptType',
																	anchor : '95%'
																},{	id:"hiddenValueAddFee",
																	name : "hiddenValueAddFee",
																	xtype : "hidden"
																},{	id:"totalCpValueAddFee",
																	name : "totalCpValueAddFee",
																	xtype : "hidden"
																},{
																	xtype : 'numberfield',
																	labelAlign : 'left',
																	fieldLabel : '<b>增值服务费</b>',
																	readOnly:true,
																	allowBlank : false,
																	blankText : "增值服务费不能为空！",
																	id:'cusValueAddFee',
																	regex:/^\d+(\.\d+)?$/,
																	regexText : "输入的数字格式不符合要求！", 
																	name : 'cusValueAddFee',
																	anchor : '95%',
																	enableKeyEvents: true,                
                   												    listeners:{
                   												      'focus':function(){  
																				Ext.getCmp("cusValueAddFee").selectText();
                        											  	},                                
                        											   'blur':function(){  
                        											     var cusValueAddFee= Ext.getCmp('cusValueAddFee').getValue();　
                        											      if(cusValueAddFee==''){
                        											   	 	cusValueAddFee=0;
                        											   	  } // 总的增值服务费
                        											     var paymentCollection=Ext.getCmp('paymentCollection').getValue();　　// 代收货歀　　
                        											      if(paymentCollection==''){
                        											   	 	paymentCollection=0;
                        											   	 }　
                        											     var consigneeFee=Ext.getCmp('consigneeFee').getValue();　　// 提送费
                        											     var v3=parseFloat(cusValueAddFee)+parseFloat(paymentCollection)+parseFloat(consigneeFee);
                        											     Ext.getCmp('totalPaymentCollection').setValue(Math.round(v3));   // 总费用
                        											  	 
                        											   },keyup:function(numberfield, e){
														                     if(e.getKey() == 13||e.getKey() == 40){
																				Ext.getCmp("storeFee").focus();
														                     }
														                     if(e.getKey() == 38){
																				Ext.getCmp("consigneeFee").focus();
														                     }
														 				}
                        											}  
																},{
																	xtype : 'numberfield',
																	labelAlign : 'left',
																	fieldLabel : '<b><font size=2>应收总额</font></b>',
																	allowBlank : false,
																	readOnly:true,
																	blankText : "应收总额不能为空！",
																	id:'totalPaymentCollection',
																	regex:/^\d+(\.\d+)?$/,
																	regexText : "输入的数字格式不符合要求！", 
																	name : 'totalPaymentCollection',
																	anchor : '95%',
																	enableKeyEvents:true,
														            listeners : {
														             'focus':function(){  
																				Ext.getCmp("totalPaymentCollection").selectText();
                        											  	},
														 				keyup:function(numberfield, e){
														                     if(e.getKey() == 13||e.getKey() == 40){
																				Ext.getCmp('mycombo').expand();
																				Ext.getCmp('mycombo').focus(true,true);
														                     }
														                     if(e.getKey() == 38){
																				Ext.getCmp('storeFee').focus();
														                     }
														 				}
														 			}
																},{
																	xtype : 'textfield',
																	labelAlign : 'left',
																	fieldLabel : '证件号码',
																	name : 'IDNumber',
																	id:'IDNumber',
																	maxLength:20,
																	validateOnBlur:true,
																	msg:'身份证号码格式不正确',/*
																	validator:function(msg){
																		var c=Ext.getCmp('IDNumber');
																		if(Ext.getCmp('IDNumber').getValue()==""){
																			msg="必须填写证件号码！";
																			return msg;
																			return false;
																		}else{
																			if(Ext.getCmp('mycombo').getValue()=="身份证"){
		                        											     	var regex=/^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}|[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{4}$/;
		                        											        msg="身证号码格式不正确";
		                        											     	if(!regex.test(c.getValue())){
		                        											     	 	return msg;
		                        											     	    return false;
		                        											     	}else{
		                        											     		return true;
		                        											     	}
	                        											     }else{
	                        											     		return true;
	                        											     }
																		}
																	},*/
																	anchor : '95%',
																//	allowBlank : false,
																	enableKeyEvents:true,
																	listeners : {
																		'focus':function(){  
																				Ext.getCmp("IDNumber").selectText();
                        											  	},  
														 				keyup:function(numberfield, e){
														                     if(e.getKey() == 13||e.getKey() == 40){
																				Ext.getCmp('consigneeTwo').focus(true,true);
														                     }
														                     if(e.getKey() == 38){
																				Ext.getCmp('mycombo').focus(true,true);
														                     }
														 				}
														 			}
																	
																},{
																	xtype : 'textfield',
																	labelAlign : 'left',
																	fieldLabel : '证件号码',
																	name : 'IDNumberTwo',
																	maxLength:20,
																	id:'IDNumberTwo',
																	anchor : '95%', /*
																	validator:function(msg){
																		var c=Ext.getCmp('IDNumberTwo');
																		if(Ext.getCmp('consigneeTwo').getValue()!=""){
																			if(Ext.getCmp('mycomboID').getValue()=="身份证"){
	                        											     	var regex=/^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}|[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{4}$/;
	                        											        msg="身证号码格式不正确";
	                        											     	if(!regex.test(c.getValue())){
	                        											     	 	return msg;
	                        											     	    return false;
	                        											     	}else{
	                        											     		return true;
	                        											     	}
                        											     	}else{
	                        											     		if(c.getValue()==""){
	                        											     			 msg="证件号码不能为空！";
	                        											     			 return msg;
	                        											     			 return false;
	                        											     		}else{
	                        											     			 return true;
	                        											     		} 
	                        											    }
																		}else{
																			return true;
																		}
																
																	},*/
																	enableKeyEvents:true,
																	listeners : {
																		'focus':function(){  
																				Ext.getCmp("IDNumberTwo").selectText();
                        											  	}, 
														 				keyup:function(numberfield, e){
														                     if(e.getKey() == 13||e.getKey() == 40){
																				Ext.getCmp('sumitButton').focus();
														                     }
														                      if(e.getKey() == 38){
																				Ext.getCmp('mycomboID').focus(true,true);
														                     }
														 				}
														 			}
																},{id:"sysTotalPaymentCollection",
																	value:"0",
																	name : "sysTotalPaymentCollection",
																	xtype : "hidden"
																},{
																	id:"scanAdd",
																	name : "scanAdd",
																	xtype : "hidden"
																}]
													}]
													
										},{
											labelAlign : 'top',
											xtype : 'textarea',
											name : 'remark',
											maxLength:500,
											fieldLabel : '备注',
											height : 35,
											width:'95%'
										},
										{
											layout : 'column',
											labelWidth : 30,
											items : [{
											columnWidth : .15,
											layout : 'form',
											items : [
												new Ext.form.Radio({
											        boxLabel:'代提',
											        inputValue:'1',
											        id:'adminRadio',
											        checked:true,
											        listeners:{
											            'check':function(v){
											                //alert(adminRadio.getValue());
											                if(v.getValue()){
											                    Ext.getCmp('adminRadio').setValue(true);
												                Ext.getCmp('userRadio').setValue(false);
											                }
											            }
										           }
											    }),new Ext.form.Radio({
											        boxLabel:'芯片识别',
											        inputValue:'1',
											        id:'icRadio',
											        checked:true,
											        listeners:{
											            'check':function(v){
											                //alert(adminRadio.getValue());
											                if(v.getValue()){
											                    Ext.getCmp('icRadio').setValue(true);
												                Ext.getCmp('photoRadio').setValue(false);
											                }
											            }
										           }
											    })]},{
											columnWidth : .15,
											layout : 'form',
											items : [new Ext.form.Radio({
												        boxLabel:'自提',
												        inputValue:'0',
												        id:'userRadio',
												        listeners:{
												            'check':function(v){
												                if(v.getValue()){
												                    Ext.getCmp('adminRadio').setValue(false);
												                    Ext.getCmp('userRadio').setValue(true);
												                }
												            }
												        }
												    }),new Ext.form.Radio({
											        boxLabel:'图像扫描',
											        inputValue:'1',
											        id:'photoRadio',
											        checked:false,
											        listeners:{
											            'check':function(v){
											                //alert(adminRadio.getValue());
											                if(v.getValue()){
											                    Ext.getCmp('photoRadio').setValue(true);
												                Ext.getCmp('icRadio').setValue(false);
											                }
											            }
										           }
											    })
											 ]
											},{
												columnWidth : .05,
												layout : 'form',
												items : [
													 
												 ]
												},{
												columnWidth : .18,
												layout : 'form',
												items : [ 
													new Ext.Button({
												        text:'&nbsp;安装驱动&nbsp;',
												        handler : function() {
												        	this.disabled = true;
												        	this.text="安装中请稍候！！";
												        	parent.sendNSCommand('down',"");
												        }
											    	}),
													 new Ext.Button({
														iconAlign : 'left',
														text:'&nbsp;第一代身份证识别&nbsp;',
														width : 15,
														height:15,
														handler : function() {
															var ic = Ext.getCmp('icRadio').getValue();
															var daiti = Ext.getCmp('adminRadio').getValue(); //是否代提
															if(ic){
																Ext.Msg.alert(alertTitle,'第一代身份证不支持芯片识别',function(){
																	Ext.getCmp('IDNumber').focus(true,200);
																});
																return;
															}else{
																if(daiti){
																 	checkCardType(-2);
																}else{
																	checkCardType(-1);
																}
															}
														}
													})
												 ]
												},{
												columnWidth : .18,
												layout : 'form',
												items : [ {html : '<pre><p></p><p> </p><p> &nbsp;</p></pre>'},
													 new Ext.Button({
														iconAlign : 'left',
														text:'&nbsp;第二代身份证识别&nbsp;',
														width : 15,
														height:15,
														handler : function() {
															var ic = Ext.getCmp('icRadio').getValue();
															var daiti = Ext.getCmp('adminRadio').getValue(); //是否代提
															if(ic){
																if(daiti){
																 	checkCardType(3);
																}else{
																	checkCardType(1);
																}
															}else{
																if(daiti){
																 	checkCardType(4);
																}else{
																	checkCardType(2);
																}
															}
														}
													})
												 ]
												},{
												columnWidth : .12,
												layout : 'form',
												items : [{html : '<pre><p></p><p> &nbsp;</p><p> &nbsp;</p></pre>'},
													 new Ext.Button({
														iconAlign : 'left',
														text:'&nbsp;仪器校准&nbsp;',
														width : 15,
														height:15,
														handler : function() {
															autoAdjust();
														}
													})
												 ]
												},{
												columnWidth : .15,
												layout : 'form',
												items : [{html : '<pre><p></p><p> &nbsp;</p><p> &nbsp;</p></pre>'},
													 new Ext.Button({
														iconAlign : 'left',
														text:'&nbsp;快捷键信息&nbsp;',
														width : 15,
														height:15,
														handler : function() {
															Ext.Msg.alert(alertTitle,"二代本人芯片：‘<b><span style='color:red'>Ctrl</span></b>’加‘<b><font size=3 color:red ><span style='color:red'>←</span></font></b>’<br>"+
																"二代代提芯片：‘<b><span style='color:red'>Ctrl</span></b>’ 加 '<b><font size=3><span style='color:red'>↑</span></font></b>'"+
																"<br>二代本人图像：‘<b><span style='color:red'>Ctrl</span></b>’加‘<b><font size=3><span style='color:red'>↓</span></font></b>’<br>"+
																"二代代提图像：‘<b><span style='color:red'>Ctrl</span></b>’加‘<b><span style='color:red'>Delete</span></b>’"+
																"<br>一代本人图像：‘<b><span style='color:red'>Ctrl</span></b>’加‘<b><span style='color:red'>\\</span></b>’<br>"+
																"一代代提图像：‘<b><span style='color:red'>Ctrl</span></b>’加‘<b><span style='color:red'>]</span></b>’<br>",function(){
																	Ext.getCmp('IDNumber').focus(true,200);
																});
														}
													})
												 ]
												}]}/*,
											 {
								    			xtype:'label',
								    			id:'showMsg',
								    			width:600,
								    			listeners : {
												'render' : function() {
														Ext.getCmp('showMsg').getEl().update("&nbsp;&nbsp;&nbsp;二代本人芯片：‘<b>Ctrl</b>’加‘<b><font size=3>←</font></b>’"+"&nbsp;&nbsp;&nbsp;&nbsp;二代代提芯片：‘<b>Ctrl</b>’ 加 '<b><font size=3>↑</font></b>'"+
														"&nbsp;&nbsp;&nbsp;&nbsp;二代本人图像：‘<b>Ctrl</b>’加‘<b><font size=3>↓</font></b>’<br> "+"&nbsp;&nbsp;&nbsp;二代代提图像：‘<b>Ctrl</b>’加‘<b>Delete</b>’"+
														"&nbsp;&nbsp;&nbsp;&nbsp;一代本人图像：‘<b>Ctrl</b>’加‘<b>\\</b>’"+"&nbsp;一代代提图像：‘<b>Ctrl</b>’加‘<b>]</b>’<br>");
													}
												}
								    		}*/]
										});
		
		var win = new Ext.Window({
			title : '提货签收录入',
			width : 750,
			id:'showWin',
			closeAction : 'hide',
			plain : true,
		    resizable : false,	
			modal : true,
			listeners : {
					'show' : function(obj) {
						Ext.getCmp('IDNumber').focus(true,200);
					}
				},
			items : form,
			buttonAlign : "center",	buttons : [{
				text : "签收",
				id:'sumitButton',
				iconCls : 'groupSave',
				handler : function() {
					var c1=Ext.getCmp('IDNumber');
					var c2=Ext.getCmp('IDNumberTwo');
					
					if(c1.getValue()==''&&c2.getValue()==''){
						Ext.Msg.alert(alertTitle,'必须填写相应的证件号码！',function(){
							c1.markInvalid("必须填写相应的证件号码!");
							c1.focus(true,true);
						});
						return;
					}
					if(c1.getValue()!=""){
						/*if(Ext.getCmp('mycombo').getValue()=="身份证"){
					     	var regex=/^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}|[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{4}$/;
					     	if(!regex.test(c1.getValue())){
					     		Ext.Msg.alert(alertTitle,'身份证号码格式不正确，请重新输入!',function(){
									c1.markInvalid("身份证号码格式不正确");
									c1.focus(true,true);
								});
								return;
					     	}
					     }*/
					}else{
					  /*	if(Ext.getCmp('IDNumberTwo').getValue()!=""&&Ext.getCmp('consigneeTwo').getValue()!=''){
							if(Ext.getCmp('mycomboID').getValue()=="身份证"){
						     	var regex=/^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}|[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{4}$/;
						     	if(!regex.test(c2.getValue())){
						     		Ext.Msg.alert(alertTitle,'身份证号码格式不正确，请重新输入!',function(){
										c2.markInvalid("身份证号码格式不正确");
										c2.focus(true,true);
									});
									return;
						     	}
						     }
						}else{*/
							if(Ext.getCmp('consigneeTwo').getValue()==''){
								Ext.Msg.alert(alertTitle,'请输入代提人姓名!',function(){
									Ext.getCmp('consigneeTwo').markInvalid("代提人姓名不能为空");
									Ext.getCmp('consigneeTwo').focus(true,true);
								});
								return;
							}
							if(c2.getValue()==''){
								Ext.Msg.alert(alertTitle,'请输入代提人证件号码!',function(){
									c2.markInvalid("代提人证件号码不能为空");
									c2.focus(true,true);
								});
								return;
							}
							
						//}
					}
					
					if (form.getForm().isValid()) {
						var totalPaymentCollection=Ext.getCmp('totalPaymentCollection').getValue();  
						var sysTotalPaymentCollection=Ext.getCmp('sysTotalPaymentCollection').getValue();
						if(parseFloat(totalPaymentCollection)<parseFloat(sysTotalPaymentCollection)){
							Ext.Msg.alert(alertTitle,"改动后的应收总额比系统保存的应收总额要少,不允许提交。如果需要改动，请走系统流程。", function() {			
													});
							return;
						}
						var str="";
						if(Ext.getCmp('receiptType').getValue()=="指定原件返回"||Ext.getCmp('receiptType').getValue()=="原件签单传真返回"){
							str+='<span style="color:red">请把原件签单交给客户签字。</span>'
						}
						Ext.Msg.confirm(alertTitle,str+'您确定签收这票货物吗？',function(btnYes) {
							if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
								this.disabled = true;   //只能点击一次
								form.getForm().submit({
								　　url : sysPath+ '/'+saveUrl,
									params:{
											privilege:privilege,
											limit : pageSize
									},
									waitMsg : '正在保存数据...',
									success : function(form, action) {
												 win.hide();
												Ext.Msg.alert(alertTitle,action.result.msg, function() {			
													var _record = recordGrid.getSelectionModel().getSelections();		
													parent.print('1',{print_dnos:_record[0].data.dno});
													dataStore.reload();
												});
									},
									failure : function(form, action) {
										if (action.faliureType == Ext.form.Action.SERVER_INVALID) {
											Ext.Msg.alert(alertTitle, "服务器连接异常，请联系管理员！");
										} else {
											if (action.result.msg) {
												Ext.Msg.alert(alertTitle,
														action.result.msg, function() {
															dataStore.reload()
														});
											}
										}
									}
								});
								this.disabled = false;
							}
						});
					}else{
						Ext.Msg.alert(alertTitle,"格式输入有误！", function(){});
					}
				}
			}, {
				text : "关闭",
				handler : function() {
				   win.hide();
				   select();
				}
			}]
								
		
		});
		
    	function sumitStore(_record) {
    		if(_record[0].data.distributionMode!='新邦'){
   				Ext.Msg.alert(alertTitle,'配送方式非新邦的货物，不允许做提货签收');
   				return false;
   			}
    		
    		if(_record[0].data.takeMode=='市内自提'){
    			if(_record[0].data.storePiece=='0'||_record[0].data.storePiece==''){
    				Ext.Msg.alert(alertTitle,'市内自提的货物必须要做入库点到后才能出库！');
    				return false;
    			}
    		}

			form.load({
				url : sysPath+ "/"+ralaListUrl,
				params:{filter_EQL_dno: _record[0].data.dno, privilege:privilege,limit : pageSize},
				success:function(){
				//	var cusValueAddFee=Ext.getCmp('cusValueAddFee').getValue();//到付增值费
				//	var consigneeFee=Ext.getCmp('consigneeFee').getValue();//提送费总额
					//alert(cusValueAddFee+consigneeFee);
					
					Ext.getCmp('cusValueAddFee').setValue(Ext.getCmp('totalCpValueAddFee').getValue());
					Ext.getCmp('IDNumber').enable();
					setTimeout(function(){
						Ext.getCmp('IDNumber').focus();
					},10000);
				//	Ext.getCmp('totalPaymentCollection').setValue(cusValueAddFee+consigneeFee);
					//alert();
				}
			})
		
			win.on('hide', function() {
						select();
					});
			win.show();
			
		}
			
			var map = new Ext.KeyMap("showWin", [{
	   			key : [37],
	   			ctrl : true,
	   			fn : function() {
	   				checkCardType(1);
	    			alert("Ctrl+向左的箭头");  //二代本人芯片
	      		}
	     	},{
	   			key : [38],  //向上箭头
	   			ctrl : true,
	   			fn : function() {
	
	   				checkCardType(3);  //二代代提芯片
	      		}
	     	},{
	      		key : [40],
	      		fn : function() {
	       			checkCardType(2);
	       			//alert("Ctrl+向下的箭头");  //二代本人图像
	      		}
	     	},{
	      		key : [46],
	      		fn : function() {
	      			checkCardType(4);
	       			//alert("Ctrl+删除键");  //二代代提图像
	      		}
	     	},{
	      		key : [220],
	      		fn : function() {
	      			checkCardType(-1);
	       			//alert("Ctrl+反斜线");  //一代本人图像
	      		}
	     	},{
	      		key : [221],
	      		fn : function(){
	      			checkCardType(-2);
	      			Ext.getCmp('IDNumberTwo').setValue(1321321321321321321);
	       			// alert("Ctrl+右中括号");  //一代代提图像
	      		}
	     	},{
	      		key : [191],
	      		fn : function() {
	       			alert("Ctrl+正斜线");  //签收
	      		}
	     	}]);
			
			function checkCardType(v){
				if(Ext.getCmp('mycombo').getRawValue()!='身份证'){
					if(v==1||v==2||v==-1){
						Ext.Msg.alert(alertTitle,Ext.getCmp('mycombo').getRawValue()+'无法扫描，只有身份证才能进行扫描！',function(){
							Ext.getCmp('mycombo').focus(true,200);
						});
						return;
					}
				}
				if(Ext.getCmp('mycomboID').getRawValue()!='身份证'){
	   				if(v==3||v==4||v==-2){
						Ext.Msg.alert(alertTitle,Ext.getCmp('mycomboID').getRawValue()+'无法扫描，只有身份证才能进行扫描！',function(){
							Ext.getCmp('mycomboID').focus(true,200);
						});
						return;
					}
	 			}
					
				if(v==-1){
					getCardInfoByOCR(1,'自提');
				}else if(v==-2){
					getCardInfoByOCR(1,'代提');
				}else if(v==1){
					getTwoCardInfoByIC('自提');  //二代本人芯片
				}else if(v==2){
					getCardInfoByOCR(2,'自提');  //二代本人图像
				}else if(v==3){
					getTwoCardInfoByIC('代提');  //二代代提芯片
				}else if(v==4){
					getCardInfoByOCR(2,'代提');  //二代代提图像
				}
			}

 	
 	function autoAdjust(){
		//参数设置窗口
		//IDRCoreX1.AutoAdjust();
		//参数设置窗口
		IDRCoreX1.Format_ErrMsg(IDRCoreX1.Show_ConfigWindow(0, 0));
	}
	
	//芯片识别
	function getTwoCardInfoByIC(string){
		//定义证件图片存放路径	"C:\\WINDOWS\\system32\\"
		var sPath = "D:\\imgs\\";
		
		//判断是IC识别还是图像识别IDRType
		var IDRTypeFlag = false;

		//判断是本人提还是代提IDRType
		var signType = string;
			
		//临时芯片数据存放位置
		var str=Ext.getCmp('dno').getValue()+"_"+signType+".wlt";
		var sWltFile = sPath + str;
		//彩色头像存放位置
		//var sHeadPhoto = sPath + "IDR_WltData.bmp";
		var i = 0;
		for ( i;i < 5;i++){ //重复读取
			var iResult = IDRCoreX1.Get_TermbData(sWltFile);
			//二代证机读识别扫描第n次完成
	
			//仅最后一次提示错误
			if ((i = 5) && (iResult < 0)){
				//显示错误消息
				IDRCoreX1.Format_ErrMsg(iResult, true); 
			};
	
			if (iResult < 0){
				continue;
			};
			
			if(signType == "代提"){
	      		Ext.getCmp('IDNumberTwo').setValue(IDRCoreX1.IDCode);
				Ext.getCmp('consigneeTwo').setValue(IDRCoreX1.Names);
			}else{
				Ext.getCmp('IDNumber').setValue(IDRCoreX1.IDCode);
			}
			Ext.getCmp('scanAdd').setValue(str.substring(0,sWltFile.length-3)+"bmp>");
			//显示获取到的身份信息
			//alert("姓名：" + IDRCoreX1.Names + "\n" +
			//			"性别：" + IDRCoreX1.Sex + "\n" +
		//				"民族：" + IDRCoreX1.Nation + "\n" +
	//					"生日：" + IDRCoreX1.BirthDate + "\n" +
	//					"身份证类型：" + IDRCoreX1.CardType + "\n" +
	//					"号码：" + IDRCoreX1.IDCode + "\n" +
	//					"住址：" + IDRCoreX1.Address + "\n" +
	//					"最新住址：" + IDRCoreX1.NewAddress + "\n" +
	//					"签发机关：" + IDRCoreX1.FromDepart + "\n" +
	//					"签发日期：" + IDRCoreX1.StartDate + "\n" +
	//					"有效期限：" + IDRCoreX1.EndDate);     
		//$("#Names").val(IDRCoreX1.Names);
		//$("#IDCode").val(IDRCoreX1.IDCode);
		//$("#Address").val(IDRCoreX1.Address);
			//退出循环
			break;
		}	
		//markScanFlag();
		//$("#showPicArea").append(signType+":<img src="+sWltFile.substring(0,sWltFile.length-3)+"bmp>");
		return;
	}
	
	//图像识别
	function getCardInfoByOCR(xCardType,string){

		//定义证件图片存放路径	"C:\\WINDOWS\\system32\\"  sPath + Ext.getCmp('dno').getValue()+"_3_"+signType+"IDR_Filter_Full" + xCardType + ".jpg";
		var sPath = "D:\\ais2\\scanImages\\";
		var signType =string; //代提  100002540_1_1336447337200_20120508.jpg   alert(new Date().format('Ymd'));
		
		//过滤色证件图片
		var sFilterPhoto = sPath + Ext.getCmp('dno').getValue()+"_3_"+(new Date().getTime())+"_"+(new Date().format('Ymd'))+".jpg";
		//过滤色证件全图
		var sFullPhoto = sPath + Ext.getCmp('dno').getValue()+"_3_"+(new Date().getTime())+"_"+(new Date().format('Ymd'))+".jpg";
		//过滤色证件头像
		var sHeadPhoto = sPath + Ext.getCmp('dno').getValue()+"_3_"+(new Date().getTime())+"_"+(new Date().format('Ymd'))+".jpg";
		//获取图片
		var iResult = IDRCoreX1.Get_IdcPic(xCardType, sFilterPhoto);
		//采集图像完成
		
		IDRCoreX1.Format_ErrMsg(iResult,false); 
		if (iResult < 0){
		//设备未正确安装或工作异常
		//显示错误消息
		  Ext.Msg.alert(alertTitle,"获取过滤色图片失败",function(){
		  		Ext.getCmp('mycombo').focus(true,200);
		  });
		  return; //错误退出
		};
		
		//解析数据
		iResult = IDRCoreX1.Get_IdcData(xCardType, sFilterPhoto, sFullPhoto, sHeadPhoto); 
	  
		//证件信息采集完成
		IDRCoreX1.Format_ErrMsg(iResult,false); 
		if (iResult < 0){
		  //证件信息采集错误
		  //显示错误消息
		  Ext.Msg.alert(alertTitle,"解析证件信息错误",function(){
		  		Ext.getCmp('mycombo').focus(true,200);
		  });
		  return; //错误退出
		};
		
		var str=Ext.getCmp('dno').getValue()+"_3_"+(new Date().getTime())+"_"+(new Date().format('Ymd'))+".jpg";
		//彩色证件全图
		var sColorFullPhoto = sPath + str;
		//彩色证件头像
		var sColorHeadPhoto = sPath + Ext.getCmp('dno').getValue()+"_3_"+(new Date().getTime())+"_"+(new Date().format('Ymd'))+".jpg";		
		//获取彩色图片
		var iResult = IDRCoreX1.Get_ColorPic(xCardType, sColorFullPhoto, sColorHeadPhoto); 
	
		//采集图像完成
		IDRCoreX1.Format_ErrMsg(iResult,false); 
		if (iResult < 0){
		  //证件信息采集错误
		  //显示错误消息
		  Ext.Msg.alert(alertTitle,"获取彩色图像错误",function(){
		  	Ext.getCmp('mycombo').focus(true,200);
		  });
		  return; //错误退出
		};
		
		if(signType == "代提"){
      		Ext.getCmp('IDNumberTwo').setValue(IDRCoreX1.IDCode);
			Ext.getCmp('consigneeTwo').setValue(IDRCoreX1.Names);
		}else{
			Ext.getCmp('IDNumber').setValue(IDRCoreX1.IDCode);
		}
		Ext.getCmp('scanAdd').setValue(str);
		//$("#Names").val(IDRCoreX1.Names);
		//$("#IDCode").val(IDRCoreX1.IDCode);
		//$("#Address").val(IDRCoreX1.Address);
		//$("#showPicArea").append(signType+":<img src="+sColorFullPhoto.substring(0,sColorFullPhoto.length-3)+"jpg>");
		//markScanFlag();
	  return ;
	}
 	
});


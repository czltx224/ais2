//发车确认JS
	var privilege=87;
	var comboxPage=comboSize;
	var ralaListUrl="stock/oprPrewiredDetailAction!findOutCarList.action";
	var dictionaryUrl='sys/dictionaryAction!ralaList.action';
	var pageSize=50;
	
     var summary = new Ext.ux.grid.GridSummary();
	
Ext.onReady(function(){

	Ext.QuickTips.init();
	
	var rownum=new Ext.grid.RowNumberer({
		header:'序号',
        width : 35,
		sortable : true	
	});

	var fields=[{name:"id",mapping:'ID'},{name:"startDepartId",mapping:'START_DEPART_ID'},
				{name:'endDepartId',mapping:'END_DEPART_ID'},
				{name:'startTime',mapping:'START_TIME'},
				{name:'endTime',mapping:'END_TIME'},
				{name:'unloadStartTime',mapping:'UNLOAD_START_TIME'},
	            {name:"unloadEndTime",mapping:'UNLOAD_END_TIME'},
	            {name:'overmemoType',mapping:'OVERMEMO_TYPE'},
	            {name:'carId',mapping:'CAR_ID'},
	            {name:'status',mapping:'STATUS'},
	            {name:'remark',mapping:'REMARK'},
                {name:"createTime",mapping:'CREATE_TIME'},
                {name:'createName',mapping:'CREATE_NAME'},
                {name:'updateTime',mapping:'UPDATE_TIME'},
                {name:'updateName',mapping:'UPDATE_NAME'},
                {name:'ts',mapping:'TS'},
                {name:"totalTicket",mapping:'TOTAL_TICKET'},
                {name:'totalPiece',mapping:'TOTAL_PIECE'},
                {name:'totalWeight',mapping:'TOTAL_WEIGHT'},
                {name:'lockNo',mapping:'LOCK_NO'},
                {name:"endDepartName",mapping:'END_DEPART_NAME'}];

	var changeStore = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({url:sysPath+"/user/userAction!list.action",method:'post'}),
            baseParams:{
             	privilege:20
            },
            reader: new Ext.data.JsonReader(
            	{
            		root: 'result', totalProperty: 'totalCount'
          		}, [{name:'name', mapping:'userName',type:'string'},        
               		 {name:'id', mapping:'id'}
              		])    
	});
	
	//配载方式
	var oprPrewiredStore2 = new Ext.data.Store({ 
            storeId:"dictionaryStore2",                        
            proxy: new Ext.data.HttpProxy({url:sysPath+"/"+dictionaryUrl,method:'post'}),
            baseParams:{
             	privilege:16,
				filter_EQL_basDictionaryId:28
            },
            reader: new Ext.data.JsonReader({
            root: 'result', totalProperty: 'totalCount'
           }, [  {name:'typeName', mapping:'typeName',type:'string'}           
              ])    
    });

	//用车类型
	 var carTypeStore= new Ext.data.Store({ 
		autoLoad:true, 
		storeId:"carTypeStore",
		baseParams:{filter_EQL_basDictionaryId:30,privilege:16},
		proxy:new Ext.data.HttpProxy({url:sysPath+"/"+dictionaryUrl}),
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
    	{name:'id',mapping:'typeCode'},
    	{name:'carTypeName',mapping:'typeName'}
    	])
	});
	
		//车辆用途加载
	 var carDoStore= new Ext.data.Store({ 
		autoLoad:true, 
		storeId:"carDoStore",
		baseParams:{filter_EQL_basDictionaryId:31,privilege:16},
		proxy:new Ext.data.HttpProxy({url:sysPath+"/"+dictionaryUrl}),
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
    	{name:'id',mapping:'typeCode'},
    	{name:'carDoName',mapping:'typeName'}
    	])
	});
	
	//司机资料数据 
	var driverStore = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({url:sysPath+"/driver/driverAction!list.action",method:'post'}),
            baseParams:{
             	privilege:22
            },
            reader: new Ext.data.JsonReader(
            	{
            		root: 'result', totalProperty: 'totalCount'
          		}, [{name:'driverName', mapping:'driverName',type:'string'},        
               		 {name:'id', mapping:'id'}
              		])    
	});



	//车辆资料数据 
	var carStore = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({url:sysPath+"/bascar/basCarAction!list.action",method:'post'}),
            baseParams:{
             	privilege:20
            },
            reader: new Ext.data.JsonReader(
            	{
            		root: 'result', totalProperty: 'totalCount'
          		}, [{name:'carCode', mapping:'carCode',type:'string'},        
               		 {name:'id', mapping:'id'}
              		])    
	});

	
	var jsonread= new Ext.data.JsonReader({
                    root:'resultMap',
                    totalProperty:'totalCount'},
                    fields);
	
	var dataStore = new Ext.data.Store({
		id:"dataStore",
        proxy: new Ext.data.HttpProxy({url:sysPath+"/"+ralaListUrl}),
       	baseParams:{limit: pageSize,privilege:privilege},
        sortInfo : {field: "id", direction: "ASC"},
        reader:jsonread
    });


                              
	var sm = new Ext.grid.CheckboxSelectionModel({
		width:25,
	//	moveEditorOnEnter:true, 
		id:'checkId',
		name:'checkId'
	});



	var dataCopyStore = new Ext.data.Store({
              fields: fields
    }); 

	var onebar = new Ext.Toolbar({
		id:'onebar',
	    items :  ['&nbsp;&nbsp;',{text:'<b>导出</b>',iconCls:'sort_down',handler:function() {
		                	parent.exportExl(recordGrid);
		        } },'-','&nbsp;&nbsp;',
		        	{text:'<b>实配单打印</b>',iconCls:'printBtn',
						handler:function() {
							var records = recordGrid.getSelectionModel().getSelections();
							
							if(records.length<1){
								Ext.Msg.alert(alertTitle,"请选择要打印的实配单据！");
								return;
							}else if (records.length>1){
								Ext.Msg.alert(alertTitle,"一次只允许打印一个实配单据！");
								return;
							}
			            	parent.print('3',{print_overmemoId:records[0].data.id});
			        } },'-','&nbsp;&nbsp;',{
	    				text:'<B>撤销实配</B>',
						id : 'submitbtn1',
						disabled : true,
						iconCls:'userDelete',
						handler : delStore
					},'-','&nbsp;&nbsp;',{
						text:'<B>发车确认</B>',
						id : 'submitbtn2',
						disabled : true,
						iconCls:'userEdit',
						handler : sumitStore
					},'-','&nbsp;&nbsp;',
				       {xtype : 'checkbox',
       	                id:'checkboxCarSign',
       	                name:'checkboxDriver',
       	                boxLabel:'<B>打印车辆签单</B>',
		                anchor : '95%'
		             },'&nbsp;&nbsp;',
				       {xtype : 'checkbox',
       	                id:'checkboxDriver',
       	                name:'checkboxDriver',
       	                boxLabel:'单独送货',
		                anchor : '95%'
		             },'&nbsp;&nbsp;','锁号：',						              　
					  　{xtype : 'textfield',
       	                fieldLabel: '锁号',
       	                id:'lockNum',
        			    name: 'lockNum',
        			    maxLength : 14,
		                width:53,
		            	enableKeyEvents:true,
			            listeners : {
			 				'focus':function(textField, e){
			                     textField.selectText();
			 				}
			 			}
		            }
	]
});

 	var twobar = new Ext.Toolbar({
		    	items : ['&nbsp;&nbsp;','车牌号<span style="color:red">*</span><B>:</B>',
		    					       {xtype:'hidden',name:'goVo.carId',id:'carId'},{xtype : 'combo',
										id:'dicTo',
										triggerAction : 'all',
										hideTrigger:true,
										store : carStore,
										width:70,
										listWidth:245,
										allowBlank : true,
										minChars : 2,
										queryParam : 'filter_LIKES_carCode',
//										forceSelection : false,
										editable : true,
										displayField : 'carCode',//显示值，与fields对应
										valueField : 'id',//value值，与fields对应
										name : 'goVo.carCode',
										anchor : '100%',
										pageSize:comboxPage,
										enableKeyEvents:true,
										listeners : {
											focus:function(combo,e){
												
											},
							 				keyup:function(combo, e){
							 					Ext.getCmp('carId').setValue(combo.getValue());
							                     if(e.getKey() == 13 ){
													Ext.getCmp('dicDriver').focus();
							                     }
								 			}
							 			}
								    },{
								    	xtype:'hidden',
								    	name:'goVo.driverId',
								    	id:'driverId'
								    },'&nbsp;','司机<span style="color:red">*</span>:',
						 			   {xtype : 'combo',
										id:'dicDriver',
										triggerAction : 'all',
										store : driverStore,
										width:85,
										listWidth:245,
										queryParam : 'filter_LIKES_driverName',
										allowBlank : true,
										emptyText : "请选择司机名字",
//										forceSelection : true,
										minChars : 0,
										editable : true,
										displayField : 'driverName',//显示值，与fields对应
										valueField : 'id',//value值，与fields对应
										name : 'goVo.driverName',
										pageSize:comboxPage,
										enableKeyEvents:true,
							            listeners : {
							 				select:function(combo,recode,index){
						 						Ext.Ajax.request({
						 							url:sysPath+"/driver/driverAction!ralaList.action",
													params:{
														privilege:22,
														filter_EQL_id:combo.getValue()
													},
													success : function(response) { // 回调函数有1个参数
														Ext.getCmp('driverPhone').setValue(Ext.decode(response.responseText).result[0].phone);
													}
												});	
							 				},
							 				keyup:function(combo, e){
							 					Ext.getCmp('driverId').setValue(combo.getValue());
							                     if(e.getKey() == 13 ){
													Ext.getCmp('driverPhone').focus();
							                     }
								 			}
							 			}
								    },'&nbsp;&nbsp;','电话<span style="color:red">*</span><B>:</B>',
						              　　{xtype : 'textfield',
			        	                fieldLabel: '电话',
			        	                id:'driverPhone',
				        			    name: 'goVo.driverPhone',
				        			    regex:/^[1-9]\d*$/,
						                regexText:'输入内容格式不符合要求',
				        			    maxLength : 14,
						                anchor : '95%',
						                width:62,
						            	enableKeyEvents:true,
							            listeners : {
							 				'focus':function(textField, e){
							                     textField.selectText();
							 				},
							 				keyup:function(numberfield, e){
							                     if(e.getKey() == 13 ){
													Ext.getCmp('dicCarType').focus(true,true);
													Ext.getCmp('dicCarType').expand();
							                     }
								 			}
							 			}
						            },'&nbsp;','用车类型<span style="color:red">*</span>:',
						 			   {xtype : 'combo',
										id:'dicCarType',
										triggerAction : 'all',
										selectOnFocus : true, 
										store : carTypeStore,
										mode:'local',
										width:100,
									//	listWidth:245,
										allowBlank : true,
										emptyText : "请选择用车类型",
										forceSelection : true,
										editable : false,
										displayField : 'carTypeName',//显示值，与fields对应
										valueField : 'carTypeName',//value值，与fields对应
										name : 'goVo.useCarType',
										enableKeyEvents:true,
							            listeners : {
							 				'focus':function(textField, e){
							                     textField.selectText();
							 				},
							 				keyup:function(numberfield, e){
							                     if(e.getKey() == 13 ){
													Ext.getCmp('rentCarResultId').focus();
							                     }
								 			}
							 			}
								    },'&nbsp;','车辆用途<span style="color:red">*</span>:',
						 			   {xtype : 'combo',
										id:'rentCarResultId',
										triggerAction : 'all',
										store : carDoStore,
										mode:'local',
										selectOnFocus : true, 
										width:100,
										allowBlank : false,
										emptyText : "请选择车辆用途",
										forceSelection : true,
										editable : false,
										displayField : 'carDoName',//显示值，与fields对应
										valueField : 'carDoName',//value值，与fields对应
										name : 'goVo.rentCarResult',
										enableKeyEvents:true,
							            listeners : {
							 				'focus':function(textField, e){
							                     textField.selectText();
							 				},
							 				keyup:function(numberfield, e){
							                     if(e.getKey() == 13 ){
													Ext.getCmp('lockNum').focus();
							                     }
								 			}
							 			}
								    },{	id:"totalDno",
										name : "goVo.totalDno",
										xtype : "hidden"
			    						}]
						              }
				
		);

      	var threebar = new Ext.Toolbar({
		    	items : ['&nbsp;&nbsp;','实配单号:',
							
									{xtype : 'textfield',
			        	                fieldLabel: '实配单号',
			        	                labelAlign : 'right',
			        	                id:'oprdno',
				        			    name: 'oprdnoName',
				        			    maxLength : 10,
						                width:55,
						                regex:/^[1-9]\d*$/,
						                regexText:'输入内容格式不符合要求',
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
										id : 'submitbtn8',
										//disabled : true,
										tooltip : '单票添加',
										iconCls:'groupAdd',
										handler : addOneStore
									},'-','&nbsp;&nbsp;',
								       {xtype : 'checkbox',
			        	                id:'checkbox',
			        	                name:'checkbox',
			        	                boxLabel:'累计添加',
						                anchor : '95%'
						               },'&nbsp;','-','&nbsp;',
									{
										text : '<B>数据清空</B>',
										id : 'submitbtn9',
										//disabled : true,
										tooltip : '所有数据将清空',
										iconCls:'groupClose',
										handler : delAllStore
									},'&nbsp;','-','&nbsp;',{xtype : 'combo',
										id:'oprPre',
										hiddenId : 'dictionaryName',
						    			hiddenName : 'dictionaryName',
										triggerAction : 'all',
								//		mode:'local',
										store : oprPrewiredStore2,
										width:80,
										queryParam : 'filter_LIKES_typeName',
										minChars : 1,
										allowBlank : true,
										emptyText : "请选择实配方式",
										forceSelection : true,
										editable : true,
										displayField : 'typeName',//显示值，与fields对应
										valueField : 'typeName',//value值，与fields对应
										name : 'typeName',
										anchor : '100%',
										listeners:{
											select:function(combo,recode,index){
												 if(combo.getValue()=='部门交接'){
												 	changeStore.proxy=new Ext.data.HttpProxy({
												 		url:sysPath+"/sys/departAction!ralaList.action",
												 		method:'post'
												 	});
												 	changeStore.baseParams={
										               privilege:53,
										               filter_EQL_isBussinessDepa:1
										            }
										            changeStore.reader=new Ext.data.JsonReader({
											                     root: 'result', totalProperty:'totalCount'
											                },[ {name:'name', mapping:'departName'},    
											                     {name:'id', mapping: 'departId'}
											            ])
											        changeStore.load();
												 }else if(combo.getValue()=='中转'){
												 	changeStore.proxy=new Ext.data.HttpProxy({
												 		url:sysPath+"/sys/customerAction!ralaList.action",
												 		method:'post'
												 	});
												 	changeStore.baseParams={
										               privilege:61,
										               filter_EQS_custprop:combo.getValue()
										            }
										            changeStore.reader=new Ext.data.JsonReader({
											                     root: 'result', totalProperty:'totalCount'
											                },[ {name:'name', mapping:'cusName'},    
											                     {name:'id', mapping: 'id'}
											            ])
											            changeStore.load();
												 }else if(combo.getValue()=='外发'){
												 	changeStore.proxy=new Ext.data.HttpProxy({
												 		url:sysPath+"/sys/customerAction!ralaList.action",
												 		method:'post'
												 	});
												 	changeStore.baseParams={
										               privilege:61,
										               filter_EQS_custprop:combo.getValue()
										            }
										            changeStore.reader=new Ext.data.JsonReader({
											                     root: 'result', totalProperty:'totalCount'
											                },[ {name:'name', mapping:'cusName'},    
											                     {name:'id', mapping: 'id'}
											            ])
											            changeStore.load();
												 }else{
												 	changeStore.proxy=new Ext.data.HttpProxy({
												 		url:sysPath+"/user/userAction!ralaList.action",
												 		method:'post'
												 	})
												 	changeStore.baseParams={
										             	privilege:23
										            }
										            changeStore.reader=new Ext.data.JsonReader({
										            		root: 'result', totalProperty: 'totalCount'
										          		}, [{name:'name', mapping:'userName',type:'string'},        
										               		 {name:'id', mapping:'id'}
										             ])
										             changeStore.load();
												 }
											
											}
										
										}
								    },{xtype : 'combo',
										id:'oprPre',
										hiddenId : 'dictionaryName',
						    			hiddenName : 'dictionaryName',
										triggerAction : 'all',
										hidden : true,
										disabled:true,
										store : oprPrewiredStore2,
										width:80,
										queryParam : 'filter_LIKES_typeName',
										minChars : 1,
										allowBlank : true,
										forceSelection : true,
										editable : true,
										displayField : 'typeName',//显示值，与fields对应
										valueField : 'typeName',//value值，与fields对应
										name : 'typeName'
								    },
									   {xtype : 'datefield',
						    			id : 'startDate',
							    		format : 'Y-m-d',
							    		emptyText : "选择开始时间",
							    		hidden : true,
							    		width : 80,
							    		disabled : true,
							    		listeners : {
							    			'select' : function() {
							    			   var start = Ext.getCmp('startDate').getValue()
							    			      .format("Y-m-d");
							    			   Ext.getCmp('endDate').setMinValue(start);
							    		     }
						    		    }
									},{xtype:'textfield',
							 	        id : 'itemsValue',
								        name : 'itemsValue',
								        width : 80,
								        disabled:true,
							            enableKeyEvents:true
							        },'&nbsp;',{
										xtype : 'datefield',
							    		id : 'endDate',
							    		format : 'Y-m-d',
							    		emptyText : "选择结束时间",
							    		hidden : true,
							    		width : 80,
							    		disabled : true,
							    		anchor : '95%'
									},'-',{
						                xtype : "combo",
						    			id:"comboselect",
						   				width:80,
						 				triggerAction : 'all',
						    			model : 'local',
						    			hiddenId : 'checkItems',
						    			hiddenName : 'checkItems',
						    			name : 'checkItemstext',
						    			store : [['', '查询全部'],
						    					['end_Depart_Name', '实配去向'],
						    					['LIKES_overmemoType', '配载方式'],
						    					['EQD_createTime', '实配时间']
						    					],
						    			emptyText : '选择查询类型',
						    			forceSelection : true,
						    			listeners : {
							    				'select' : function(combo, record, index) { 
							    					if (combo.getValue() == 'EQD_createTime') {
							    						
							    						Ext.getCmp("startDate").enable();
							    						Ext.getCmp("startDate").show();
							    						Ext.getCmp("endDate").enable();
							    						Ext.getCmp("endDate").show();
							    								
							    						Ext.getCmp("itemsValue").disable();
							    						Ext.getCmp("itemsValue").hide();
							    						Ext.getCmp("itemsValue").setValue("");
							    						
							    						Ext.getCmp("oprPre").disable();
							    						Ext.getCmp("oprPre").hide();
							    						Ext.getCmp("oprPre").setValue("");
							    					}else if(combo.getValue() == ''){
							    						Ext.getCmp("startDate").disable();
							    						Ext.getCmp("startDate").hide();
							    					    Ext.getCmp("startDate").setValue("");
							
							    						Ext.getCmp("endDate").disable();
							    						Ext.getCmp("endDate").hide();
							    						Ext.getCmp("endDate").setValue("");
							    						
							    						Ext.getCmp("oprPre").disable();
							    						Ext.getCmp("oprPre").hide();
							    						Ext.getCmp("oprPre").setValue("");
							    						
							    						Ext.getCmp("itemsValue").setValue("");
							    						Ext.getCmp("itemsValue").show();
							    						Ext.getCmp("itemsValue").disable();
							    					}else if(combo.getValue() == 'LIKES_overmemoType'){
							    						Ext.getCmp("startDate").disable();
							    						Ext.getCmp("startDate").hide();
							    					    Ext.getCmp("startDate").setValue("");
							
							    						Ext.getCmp("endDate").disable();
							    						Ext.getCmp("endDate").hide();
							    						Ext.getCmp("endDate").setValue("");
							    						
							    						Ext.getCmp("itemsValue").disable();
							    						Ext.getCmp("itemsValue").hide();
							    						Ext.getCmp("itemsValue").setValue("");
							    						
							    						Ext.getCmp("oprPre").setValue("");
							    						Ext.getCmp("oprPre").show();
							    						Ext.getCmp("oprPre").enable();
							    					
							    					}else{
							    						Ext.getCmp("oprPre").disable();
							    						Ext.getCmp("oprPre").hide();
							    						Ext.getCmp("oprPre").setValue("");
							    					
							    						Ext.getCmp("startDate").disable();
							    						Ext.getCmp("startDate").hide();
							    					    Ext.getCmp("startDate").setValue("");
							
							    						Ext.getCmp("endDate").disable();
							    						Ext.getCmp("endDate").hide();
							    						Ext.getCmp("endDate").setValue("");
							    					
							    						Ext.getCmp("itemsValue").setValue("");
							    						Ext.getCmp("itemsValue").show();
							    						Ext.getCmp("itemsValue").enable();
							    					
							    					
							    					}
							    				}
						    
						    		}},'&nbsp;&nbsp;',{
				    				     text : '<b>查询</b>',
				    				     id : 'btn',
				    				     iconCls : 'btnSearch',
				    					 handler : searchLog
				    				}]
		});

     var recordGrid=new Ext.grid.EditorGridPanel({
			id:'userCenter2',
			height : Ext.lib.Dom.getViewHeight(),
			width :Ext.lib.Dom.getViewWidth(),
			autoScroll : true,  //面板上的body元素
			viewConfig : {
				scrollOffset: 0,
				autoScroll:true
			},
			frame : true,
			clicksToEdit:1,
			enableColumnResize:false, //关闭列的自适大小功能
			autoWidth:false,
			tbar:threebar,
			plugins : [summary],
			loadMask : true,
			sm:sm,
			stripeRows : true,
			columns:[sm,
				{header:'实配单号',dataIndex:'id',width:80,sortable : true},
				{header: '实配去向',dataIndex:'endDepartName',width:80,sortable : true},   //到达部门
				{header: '配载类型',dataIndex:'overmemoType',width:80,sortable : true},        //交接单类型
				{header: '票数',dataIndex:'totalTicket',width:80,sortable : true},//总票数
				{header: '件数',dataIndex:'totalPiece',width:80,sortable : true},//件数
				{header: '实配载重',dataIndex:'totalWeight',width:80,sortable : true},
				{header: '备注',dataIndex:'remark',width:80},     //备注
				{header: '创建时间',dataIndex:'createTime',hidden:true,width:80,sortable : true},   
				{header: '创建人',dataIndex:'createName',hidden:true,width:80,sortable : true},
				{header: '修改时间',dataIndex:'updateTime',hidden:true,width:80,sortable : true},
				{header: '修改人',dataIndex:'updateName',hidden:true,width:80,sortable : true},
				{header : '操作',width:60,renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){
	    							//删除一条记录
	    							return "<a href='#' onclick='delTotalStore("+rowIndex+");'>删除</a>";
	    						}
    		}
		],
		store :dataStore,
        bbar: new Ext.PagingToolbar({
            pageSize: pageSize, 
            privilege:privilege,
            store: dataStore, 
            displayInfo: true,
            displayMsg: '显示第 {0} 条到 {1} 条记录，共 {2} 条', 
            emptyMsg: "没有记录信息显示"
        })
	});
     recordGrid.on('click', function() {
		select();
	 });
		
		
	function select(){
		var vnetmusicRecord = recordGrid.getSelectionModel().getSelections();
		var dele = Ext.getCmp('submitbtn1');
		var print = Ext.getCmp('submitbtn2');
		if (vnetmusicRecord.length == 1) {
			dele.setDisabled(false);
			print.setDisabled(false);
		} else if (vnetmusicRecord.length > 1) {
			dele.setDisabled(false);
			print.setDisabled(false);
		} else {
			dele.setDisabled(true);
			print.setDisabled(true);
		}
	}

      var form = new Ext.form.FormPanel({
      							id:'myForm',
								labelAlign : 'left',
								frame : false,
								renderTo:Ext.getBody(),
								bodyStyle : 'padding:0px 0px 0px',
							    height : Ext.lib.Dom.getViewHeight()-1,
								width : Ext.lib.Dom.getViewWidth()-1,
								labelAlign : "right",
								tbar:onebar,
						        items : [onebar,twobar,recordGrid]
					});
     
     
			recordGrid.setHeight((Ext.lib.Dom.getViewHeight()-2*twobar.getHeight()));

	
			form.render();
		
			function searchLog(){
				
				dataStore.proxy = new Ext.data.HttpProxy({
					method : 'POST',
					url : sysPath+'/'+ralaListUrl
				});
				
				
				var checkbox = Ext.get("checkbox").dom.checked;
				
				if(checkbox){
					if(dataStore.getCount()!=0){
						for(i=0;i<dataStore.getCount();i++){
								dataCopyStore.add(dataStore.getAt(i));
						}
					}
				}
				dataStore.baseParams={limit: pageSize,privilege:privilege};
				if(Ext.getCmp('comboselect').getValue() == 'EQD_createTime') {
    				var start='';
					var end ='';
					if(Ext.getCmp('startDate').getValue()!=""){
					    var start = Ext.getCmp('startDate').getValue().format("Y-m-d");
					}
					if(Ext.getCmp('endDate').getValue()!=""){
					    var end = Ext.getCmp('endDate').getValue().format("Y-m-d");
					}
    				Ext.apply(dataStore.baseParams,{
    					filter_GED_createTime : start,
    					filter_LED_createTime : end,
    					privilege:privilege,
			    		limit : pageSize
    				});
    			}else if(Ext.getCmp('comboselect').getValue()=='LIKES_overmemoType'){
    				Ext.apply(dataStore.baseParams,{
    					checkItems : 'overmemo_Type',
						itemsValue : Ext.get("oprPre").dom.value,
    					privilege:privilege,
			    		limit : pageSize
    				});
    			}else if (Ext.getCmp('comboselect').getValue()==''){
    				Ext.apply(dataStore.baseParams, {
    					privilege:privilege,
			    		limit : pageSize
    				});
    			}else{
     				Ext.apply(dataStore.baseParams, {
    					checkItems : Ext.get("checkItems").dom.value,
						itemsValue : Ext.get("itemsValue").dom.value,
    					privilege:privilege,
			    		limit : pageSize
    				});
    			}
				
				dataStore.reload({
						params : {
							start : 0
						},callback :function(){
							var ids="";
							if(Ext.get("checkbox").dom.checked){
								if(dataCopyStore.getCount()!=0){
									for(i=0;i<dataCopyStore.getCount();i++){
										dataStore.add(dataCopyStore.getAt(i));
									}
								}
							}
							if(dataStore.getCount()>0){
								for(i=0;i<dataStore.getCount();i++){
											ids += dataStore.getAt(i).get('id') + ",";
								}
								fnSumInfo(ids);
							}
						}
				});
			
			}

			// 单票添加
			function addOneStore(){
				
				dataStore.proxy = new Ext.data.HttpProxy({
					method : 'POST',
					url : sysPath+'/'+ralaListUrl
				});
				
				var oprdno = Ext.getCmp('oprdno').getValue()
				var regex=/^[1-9]\d*$/;
				if(oprdno!=""){
				    if(!regex.test(oprdno)){
				       	Ext.Msg.alert(alertTitle,"输入内容格式不正确", function() {			
								Ext.getCmp('oprdno').markInvalid("输入内容格式不正确");
								Ext.getCmp('oprdno').focus();	
								Ext.getCmp('oprdno').selectText();	
						});
						return;
				    }
			    }else{
			    	Ext.Msg.alert(alertTitle,"实配单号内容不能为空", function() {			
								Ext.getCmp('oprdno').markInvalid("实配单号内容不能为空");
								Ext.getCmp('oprdno').focus();	
					});
			    	return;
			    }
				
				if(dataStore.getCount()!=0){
					for(i=0;i<dataStore.getCount();i++){
						if(oprdno==dataStore.getAt(i).get('id')){
							Ext.Msg.alert(alertTitle,"此记录已存在", function() {			
								recordGrid.getSelectionModel().selectRow(i);
							});
							return;
						}
					}
				}
				
				var checkbox = Ext.get("checkbox").dom.checked;
				
				if(checkbox){
					if(dataStore.getCount()!=0){
						for(i=0;i<dataStore.getCount();i++){
								dataCopyStore.add(dataStore.getAt(i));
						}
					}
				}
				
				Ext.apply(dataStore.baseParams={
			 	 		privilege:privilege,
			    		limit : pageSize
				});
				
				
				dataStore.reload({
						params : {
							start : 0,
							filter_EQL_id:oprdno
						},callback :function(){
							var ids="";
							if(Ext.get("checkbox").dom.checked){
								if(dataCopyStore.getCount()!=0){
									for(i=0;i<dataCopyStore.getCount();i++){
										dataStore.add(dataCopyStore.getAt(i));
									}
								}
							}
							if(dataStore.getCount()>0){
								for(i=0;i<dataStore.getCount();i++){
											ids += dataStore.getAt(i).get('id') + ",";
								}
								fnSumInfo(ids);
							}
						}
				});
			
			}
			
			function delStore(){
				var _records = recordGrid.getSelectionModel().getSelections();
				 if (_records.length == 0) {
					 Ext.Msg.alert(alertTitle, '请选择一条您要撤销的实配记录');
					 return false;
				 }else {
				 	 for(var i = 0; i < _records.length; i++){
						for(var j = 0; j < _records.length; j++){
							if(_records[i].get('overmemoType')!=_records[j].get('overmemoType')){
								 Ext.Msg.alert(alertTitle, '只能撤销同一配载类型的实配记录');
									return;
							}
					 	}
					 }
				 
				 
				 	 Ext.Msg.confirm(alertTitle,'您确定要提交这<span style="color:red">&nbsp;'+ _records.length + '&nbsp;</span>行实配数据吗?',function(btnYes) {
						if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
							var ids = "";
    						for(var i = 0; i < _records.length; i++){
								ids += _records[i].data.id + ",";
							}
							var mode=_records[0].get('overmemoType');
							//alert(ids);
							Ext.Ajax.request({
								url : sysPath+ "/stock/oprOvermemoAction!delOprOvermemo.action",
								method : 'post',
									params : {
   									ids : ids,
   									overmemoType:mode
   								},
								waitMsg : '正在保存数据...',
								success : function(resp) {
									var result = Ext.util.JSON.decode(resp.responseText);
									Ext.Msg.alert(
										alertTitle,
										result.msg,
										function() {
											searchLog();
											select();
										});
								},
								failure : function(resp) {
									var result = Ext.util.JSON.decode(resp.responseText);
									Ext.Msg.alert(alertTitle,
										result.msg,
										function() {
											searchLog();
											 select();
									});
								}
							});
						}
					 });
				 }
			}
		
		
		
			function sumitStore(){
				var _records = recordGrid.getSelectionModel().getSelections();
				var s =_records[0].data.overmemoType;
				
				if(s=='专车'||s=='市内送货'||s=='部门交接'){
					if(Ext.getCmp("dicTo").getRawValue()==""){
	 			  		Ext.Msg.alert(alertTitle,"车牌号不许为空,请选择填写车牌号", function() {			
								Ext.getCmp('dicTo').markInvalid("车牌号不许为空");
								Ext.getCmp('dicTo').focus(true,true);
						});
							return;
	 			  	}
			  
				if(Ext.getCmp("dicDriver").getRawValue()==""){
 			  		Ext.Msg.alert(alertTitle,"司机不许为空,请选择司机的名字", function() {			
							Ext.getCmp('dicDriver').markInvalid("司机不许为空");
							Ext.getCmp('dicDriver').focus(false,true);
							Ext.getCmp('dicDriver').expand();
					});
						return;
 			  	}
 			    if(Ext.getCmp("driverPhone").getValue()==""){
 			  		Ext.Msg.alert(alertTitle,"电话不允许空值", function() {			
							Ext.getCmp('driverPhone').markInvalid("电话不允许空值");
							Ext.getCmp('driverPhone').focus();	
					});
						return;
 			 	 }
 			  	}
 			  	
 			     if(Ext.getCmp("dicCarType").getValue()==""){
 			  		Ext.Msg.alert(alertTitle,"用车类型不允许空值", function() {			
							Ext.getCmp('dicCarType').markInvalid("用车类型不允许空值");
							Ext.getCmp('dicCarType').focus(true,false);
					});
						return;
 			  	 }
 			     if(Ext.getCmp("rentCarResultId").getValue()==""){
 			  		Ext.Msg.alert(alertTitle,"车辆用途不允许空值", function() {			
							Ext.getCmp('rentCarResultId').markInvalid("车辆用途不允许空值");
							Ext.getCmp('rentCarResultId').focus(true,false);
					});
						return;
 			  	 }
 			  	 if(Ext.getCmp("lockNum").getValue()==""){
 			  	 	for(i=0;i<_records.length;i++){
						if(_records[i].get('overmemoType')=='部门交接'){
							Ext.Msg.alert(alertTitle,"部门交接的货物必须填写锁号。", function() {	
								Ext.getCmp('lockNum').markInvalid("车辆锁号不允许空值");
								Ext.getCmp('lockNum').focus(true,false);		
							});
							return;
						}
					}

 			  	 }
 			  	 
 			  	var combo = Ext.getCmp('dicTo');
				if(combo.getValue()==combo.getRawValue()){
					var boo = true;
					for(i=0;i<_records.length;i++){
						if(_records[i].get('overmemoType')=='中转'){
							Ext.Msg.alert(alertTitle,"车辆资料中没有此车信息", function() {			
							});
							boo=false;
						}
					}
					if(boo){
						Ext.Msg.alert(alertTitle,"车库中没有此车资料,请在车辆管理中维护车辆信息", function() {			
							Ext.getCmp('dicTo').markInvalid("输入内容格式不正确");
					    	Ext.getCmp('dicTo').focus();	
							Ext.getCmp('dicTo').selectText();
							return;
						});
					}
				}
 			  	 
				  if (_records.length == 0) {
					 Ext.Msg.alert(alertTitle, '请选择一条您要提交的实配记录');
					 return false;
				  }else {
				  	var vCar=0;
				  	var str="";
					if(Ext.get("checkboxCarSign").dom.checked){
						vCar=1;
					}else{
						vCar=0;
						str='<span style="color:red">您未选择打印车辆签单，请确认  </span>。';
					}
				     Ext.Msg.confirm(alertTitle,str+'您确定要提交这<span style="color:red">&nbsp;'+ _records.length + '&nbsp;</span>行实配数据吗?',function(btnYes){
				     	var printCar=0;
				     
						if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
							var ids = "";
    						for(var i = 0; i < _records.length; i++) {
								ids += _records[i].data.id + ",";
							}
							
							var boxNum;
							if(Ext.get("checkboxDriver").dom.checked){
								boxNum=1;
							}else{
								boxNum=0;
							}
							
							form.getForm().doAction('submit', {
								url : sysPath+ "/stock/oprOvermemoAction!saveOutCar.action",
								method : 'post',
									params : {
   									ids : ids,
   									checkAlone:boxNum,
   									checkPrint:vCar,
   									lockNum:Ext.getCmp("lockNum").getValue(),
   									privilege:privilege
   								},
								waitMsg : '正在保存数据...',
								success : function(form, action) {
								Ext.Msg.alert(
										alertTitle,
										action.result.msg,
										function() {
											searchLog();
											select();
											if(vCar==1){
											   parent.print('4',{print_routeNumber:action.result.value});
											}
										});
								},
								failure : function(form, action) {
											Ext.Msg.alert(
														alertTitle,
														action.result.msg,
														function() {
															searchLog();
															 select();
											});
								}
							});
						}
					 });
					}
			}
			
       		// 删除所有信息
       		function delAllStore(){
       			var ids="";
       			if(dataStore.getCount()>0){
	       			 parent.Ext.Msg.confirm(alertTitle,'您确定要清除页面上这<span style="color:red">&nbsp;'+ dataStore.getCount()+ '&nbsp;</span>行数据吗?',function(btnYes) {
							if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
								dataStore.removeAll();
								fnSumInfo(ids);
								select();						
							}
					});
       			}
       		}
	if(mid!=null&&mid!=''){
		Ext.getCmp("oprdno").setValue(mid);
		addOneStore();
	}
	
});

		/**
			 * 信息汇总���ܱ��
			 */
	function fnSumInfo(ids) {
		Ext.Ajax.request({
			url : sysPath+'/stock/oprOvermemoDetailAction!getSumInfo.action',
			params:{
				ids:ids
			},
			success : function(response) { // 回调函数有1个参数
				summary.toggleSummary(true);
				summary.setSumValue(Ext.decode(response.responseText));
				Ext.getCmp("totalDno").setValue(Ext.decode(response.responseText).totalTicket);
			},
			failure : function(response) {
				Ext.MessageBox.alert(alertTitle, '汇总数据失败');
			}
		});
	}
			


	function delTotalStore(rowIndex){
		var ids="";
		Ext.Msg.confirm(alertTitle, "确定要从页面上删除这条数据吗？", function(btnYes) {
		   	if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
		    	var form=Ext.getCmp("myForm");
		    	var dataStore=Ext.StoreMgr.get("dataStore");
		       	dataStore.removeAt(rowIndex); 

				for(i=0;i<dataStore.getCount();i++){
					ids += dataStore.getAt(i).get('id') + ",";
				}
								
				fnSumInfo(ids);
				form.render();
		   }
		})
   	}


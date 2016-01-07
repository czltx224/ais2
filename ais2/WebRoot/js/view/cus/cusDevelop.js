var operTitle;
var privilege=158;
var developLevel='潜在客户';
var fields=[
			{name:'developName'},
			{name:'cusName'},
            {name: 'id'},
            {name: 'developStage'},
            {name: 'developType'},
            {name: 'developContext'},
            {name: 'developCost'},
            {name:'developedMan'},
            {name:'developMan'},
            {name: 'developTime'},
            {name: 'isAudit'},
            {name: 'filePath'},
            {name:'auditTime'},
            {name:'auditName'},
            {name:'remark'},
            {name:'cusRecordId'},
            {name:'createName'},
            {name:'createTime'},
            {name:'updateName'},
            {name:'updateTime'},
            {name:'ts'},
            {name:'status'},
            {name:'developLinkmanTel'},
            {name:'assessResult'},
            {name:'resultRemark'}
];
Ext.onReady(function() {
    var menuStore = new Ext.data.Store({
        storeId:"menuStore",
        baseParams:{limit:pageSize,privilege:privilege,filter_EQL_cusRecordId:parent.cusRecordId},
        proxy: new Ext.data.HttpProxy({url:sysPath+"/cus/cusDevelopAction!ralaList.action"}),
        reader: new Ext.data.JsonReader({
            root: 'result', totalProperty: 'totalCount'
        }, fields)
    });
    if(parent.cusRecordId!=null){
    	menuStore.load();
    }
    var cusRecordStore=new Ext.data.Store({
		storeId:'cusRecordStore',
		baseParams:{limit:pageSize,filter_EQL_departId:bussDepart},
		proxy:new Ext.data.HttpProxy({url:sysPath+"/cus/cusRecordAction!list.action"}),
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
        	{name:'id'},
        	{name:'cusName'}
        	])
	});
    var linkManStore = new Ext.data.Store({
        storeId:"menuStore",
        baseParams:{privilege:152},
        proxy: new Ext.data.HttpProxy({url:sysPath+"/cus/cusLinkManAction!ralaList.action"}),
        reader: new Ext.data.JsonReader({
            root: 'result', totalProperty: 'totalCount'
        }, [
        {name:'id'},
        {name:'linkMan',mapping:'name'}
        ])
    });
    var developTypeStore=new Ext.data.Store({
		storeId:'developTypeStore',
		baseParams:{filter_EQL_basDictionaryId:45,privilege:16},
		proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/dictionaryAction!ralaList.action"}),
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
        	{name:'id'},
        	{name:'developType',mapping:'typeName'}
        	])
});
    //开发阶段Store
var developLevelStore=new Ext.data.Store({
		storeId:'developLevelStore',
		baseParams:{filter_EQL_basDictionaryId:39,privilege:16},
		proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/dictionaryAction!ralaList.action"}),
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
        	{name:'id'},
        	{name:'developLevel',mapping:'typeName'}
        	])
});
    //start
    var sm = new Ext.grid.CheckboxSelectionModel({});
    var rowNum = new Ext.grid.RowNumberer({
        header:'序号', width:35, sortable:true
    });
    
    var menuGrid = new Ext.grid.GridPanel({
        renderTo:'showView',
    	id : 'myrecordGrid',
    	height : Ext.lib.Dom.getViewHeight(),
    	width : Ext.lib.Dom.getViewWidth()-1,
    	frame : false,
    	loadMask : true,
    	stripeRows : true,
    	viewConfig:{
    	   scrollOffset: 0,
    	   autoScroll : true
    	},
        sm:sm,
        cm:new Ext.grid.ColumnModel([rowNum,sm,
            {header:'编号',dataIndex:"id",hidden: true, hideable: false},
            {header:'时间戳',dataIndex:"ts",hidden: true, hideable: false},
            {header:'客户ID',dataIndex:"cusRecordId",hidden: true, hideable: false},
            {header:'客户名称',dataIndex:"cusName",width:80},
            {header:'活动主题',dataIndex:"developName",width:80},
            {header:"活动类型",dataIndex:"developType",width:80},
            {header:"客户阶段",dataIndex:"developStage",width:80},
            {header:"活动经过",dataIndex:"developContext",width:80},
            {header:"活动成本",dataIndex:"developCost",width:80},
            {header:"评估状态",dataIndex:"isAudit",width:80,renderer:function(v){
            	if(v==1){
            		return '已评估';
            	}else if(v==0){
            		return '未评估';
            	}
            }},
            {header:"评估人",dataIndex:"auditName",width:80},
            {header:"评估时间",dataIndex:"auditTime",width:80},
            {header:"评估结果",dataIndex:"assessResult",width:80,renderer:function(v){
            	if(v!=''){
            		if(v==1){
            			return '非常不满意';
	            	}else if(v==2){
	            		return '不满意';
	            	}else if(v==3){
						return '一般';            	
	            	}else if(v==4){
	            		return '满意';
	            	}else if(v==5){
						return '非常满意';            	
	            	}else{
	            		return '';
	            	}
            	}
            }},
            {header:"活动联系人",dataIndex:"developedMan",width:80},
            {header:"活动联系电话",dataIndex:"developLinkmanTel",width:80},
            {header:"附件",dataIndex:"filePath",width:80,renderer:function(v){
            	if(v!=''){
            		return '<a href="'+exceptionImagesUrl+v+'" target="_blank">查看附件</a>';
            	}
            }},
            {header:"活动创建人人",dataIndex:"developMan",width:80},
            {header:"活动创建时间",dataIndex:"developTime",width:80},
            {header:"备注",dataIndex:"remark",width:50,hidden: true},
            {header:"修改人",dataIndex:"updateName",width:50,hidden: true},
            {header:"修改时间",dataIndex:"updateTime",width:50,hidden: true}
           
        ]),
        store:menuStore,
        tbar: [
            {
                text:'<B>新建活动过程</B>', id:'addbtn',tooltip:'新建活动过程', iconCls: 'userAdd',handler:function() {
                	if(parent.cusRecordId==null){
                		cusOper(null,'');
                	}else{
                		cusOpernoNull(null,'');
                	}
                	
            } },
            '-',
            {
                text:'<B>修改活动过程</B>',id:'updatebtn',disabled:true, tooltip:'修改活动过程', iconCls: 'userEdit', handler: function() {
                var _record = menuGrid.getSelectionModel().getSelections();
                if (_record.length < 1) {
						parent.Ext.Msg.alert("系统消息", "请选择您要操作的行！");
						return false;
					} else if (_record.length > 1) {
						parent.Ext.Msg.alert("系统消息", "一次只能修改一行！");
						return false;
					}else{
						if(parent.cusRecordId==null){
	                		cusOper(_record,'');
	                	}else{
	                		cusOpernoNull(_record,'');
	                	}
					}
            } } ,
            '-',{
            	text:'<b>评估</b>',id:'assessbtn',disabled:true,iconCls:'userEdit',handler:function(){
            		 	var _record = menuGrid.getSelectionModel().getSelections();
            		 	if(_record[0].data.isAudit!=0){
            		 		Ext.Msg.alert(alertTitle,'只能对未评估的活动过程进行评估！');
            		 		return;
            		 	}else{
            		 		if(parent.cusRecordId==null){
		                		cusOper(_record,'assess');
		                	}else{
		                		cusOpernoNull(_record,'assess');
		                	}
            		 	}
            	}
            },'-',
            {
                text:'<B>作废</B>',id:'deletebtn',disabled:true, tooltip:'作废活动过程', iconCls: 'userDelete',
                handler: function(){
                	var _records = menuGrid.getSelectionModel().getSelections();
                	if (_records.length < 1) {
						parent.Ext.Msg.alert(alertTitle, "请选择您要操作的行！");
						return false;
					} 
					Ext.Msg.confirm(alertTitle, "确定要作废所选的"+_records.length+"行记录吗？", function(btnYes) {
						if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
								var ids="";
								for(var i=0;i<_records.length;i++){
									ids=ids+_records[i].data.id+",";
								}
								Ext.Ajax.request({
								url : sysPath+ "/cus/cusDevelopAction!delete.action",
								params : {
									ids : ids,
									privilege:privilege
								},
								success : function(resp) {
								var respText = Ext.util.JSON.decode(resp.responseText);
								Ext.Msg.alert(alertTitle,respText.msg);
								menuStoreReload();
										}
									});
								}
							});
					
                }
            },'-','&nbsp;',
            {
            	xtype : 'textfield',
    			id:'searchContent',
    			name : 'stationName',
    			width : 100,
    			enableKeyEvents:true,
    			listeners : {
			 		keyup:function(textField, e){
		                     if(e.getKey() == 13){
		                     	searchCusRequest();
		                  }
			 		}
	 			}
            },{
            	xtype : 'combo',
            	triggerAction : 'all',
            	forceSelection : true,
            	id:'searLevelcombo',
    			model : 'local',
    			hidden:true,
    			hiddenName : 'developLevel',
            	store: developLevelStore,
            	valueField:'developLevel',
            	displayField:'developLevel',
    			width : 100
            	
            },{
            	xtype : 'combo',
            	triggerAction : 'all',
            	forceSelection : true,
            	id:'searTypecombo',
            	hidden:true,
    			model : 'local',
    			hiddenName : 'developType',
            	store: developTypeStore,
            	valueField:'developType',
            	displayField:'developType',
    			width : 100
            },{
            	xtype:'combo',
				forceSelection : true,
				triggerAction : 'all',
				id:'searassessResult',
				name:'searassessResult',
				model : 'local',
				hidden:true,
				width:100,
				store:[
				    ['1','非常不满意'],
				    ['2','不满意'],
				    ['3','一般'],
				    ['4','满意'],
				    ['5','非常满意']
				]
            },'-',
            {
            	xtype : 'combo',
            	triggerAction : 'all',
    			model : 'local',
    			id : 'checkItems',
    			name : 'checkItemstext',
            	store: [
            			['', '查询全部'], 
    					['LIKES_developType', '活动类型'],
    					['LIKES_developStage', '活动阶段'],
    					['LIKES_developedMan', '活动联系人'],
    					['LIKES_developLinkmanTel', '活动联系电话'],
    					['LIKES_assessResult', '评估结果']
    				   ],
    			width : 100,
    			listeners : {
    						'select' : function(combo, record, index) {
    							if (combo.getValue() == '') {
    								Ext.getCmp("searchContent").disable();
    								Ext.getCmp("searchContent").show();
    								
    								Ext.getCmp("searTypecombo").setValue("");
    								Ext.getCmp("searTypecombo").disable();
    								Ext.getCmp("searTypecombo").hide();
    								
    								Ext.getCmp("searLevelcombo").disable();
    								Ext.getCmp("searLevelcombo").setValue("");
    								Ext.getCmp("searLevelcombo").hide();
    								
    								Ext.getCmp('searassessResult').setValue("");
    								Ext.getCmp('searassessResult').disable();
    								Ext.getCmp('searassessResult').hide();
    							} else if(combo.getValue()=='LIKES_developType'){
    								Ext.getCmp("searchContent").disable();
    								Ext.getCmp("searchContent").setValue("");
    								Ext.getCmp("searchContent").hide();
    								
    								Ext.getCmp("searLevelcombo").disable();
    								Ext.getCmp("searLevelcombo").setValue("");
    								Ext.getCmp("searLevelcombo").hide();
    								
    								Ext.getCmp('searassessResult').setValue("");
    								Ext.getCmp('searassessResult').disable();
    								Ext.getCmp('searassessResult').hide();
    								
    								Ext.getCmp("searTypecombo").show();
    								Ext.getCmp("searTypecombo").enable();
    							}else if(combo.getValue()=='LIKES_developStage'){
    								Ext.getCmp("searchContent").disable();
    								Ext.getCmp("searchContent").setValue("");
    								Ext.getCmp("searchContent").hide();
    								
    								Ext.getCmp("searTypecombo").disable();
    								Ext.getCmp("searTypecombo").setValue("");
    								Ext.getCmp("searTypecombo").hide();
    								
    								Ext.getCmp('searassessResult').setValue("");
    								Ext.getCmp('searassessResult').disable();
    								Ext.getCmp('searassessResult').hide();
    								
    								Ext.getCmp("searLevelcombo").enable();
    								Ext.getCmp("searLevelcombo").show();
    							}else if(combo.getValue()=='LIKES_assessResult'){
    								Ext.getCmp("searchContent").disable();
    								Ext.getCmp("searchContent").setValue('');
    								Ext.getCmp("searchContent").hide();
    								
    								Ext.getCmp("searTypecombo").setValue("");
    								Ext.getCmp("searTypecombo").disable();
    								Ext.getCmp("searTypecombo").hide();
    								
    								Ext.getCmp("searLevelcombo").disable();
    								Ext.getCmp("searLevelcombo").hide();
    								Ext.getCmp("searLevelcombo").setValue("");
    								
    								Ext.getCmp('searassessResult').show();
    								Ext.getCmp('searassessResult').enable();
    							}else {
    								Ext.getCmp("searchContent").enable();
    								Ext.getCmp("searchContent").show();
    								
    								Ext.getCmp("searTypecombo").setValue("");
    								Ext.getCmp("searTypecombo").disable();
    								Ext.getCmp("searTypecombo").hide();
    								
    								Ext.getCmp('searassessResult').setValue("");
    								Ext.getCmp('searassessResult').disable();
    								Ext.getCmp('searassessResult').hide();
    								
    								Ext.getCmp("searLevelcombo").disable();
    								Ext.getCmp("searLevelcombo").setValue("");
    								Ext.getCmp("searLevelcombo").hide();
    							}
    							Ext.getCmp("searchContent").focus(true, true);
    						}
    					}
            },
            '-','&nbsp;','客户:',
            {
            	xtype : 'combo',
            	triggerAction : 'all',
            	forceSelection : true,
            	pageSize:pageSize,
            	id:'searcuscombo',
    			model : 'local',
    			hiddenName : 'cusName',
            	store: cusRecordStore,
            	valueField:'id',
            	displayField:'cusName',
    			width : 100
            },
            '-',
            {
            	text:'<B>搜索</B>',
            	id:'btn', 
            	iconCls: 'btnSearch',
            	hidden:false,
            	handler : searchCusRequest
            }
        ],
        bbar: new Ext.PagingToolbar({
            pageSize: pageSize,
            privilege:privilege, 
            store: menuStore, 
            displayInfo: true,
            displayMsg: '显示第 {0} 条到 {1} 条记录，共 {2} 条', emptyMsg: "没有记录信息显示"
        }),listeners:{
        	'render':function(){
        		if(parent.viewtype=='mainview'){
		    		Ext.getCmp('searcuscombo').setDisabled(true);
		    	}
        	}
        }
    });
    function searchCusRequest() {
    	menuStore.baseParams ={privilege:privilege,limit : pageSize,start:0};
    	var cusId=Ext.getCmp('searcuscombo').getValue();
    	if(cusId!=''){
    		menuStore.baseParams = {
				filter_EQL_cusRecordId:cusId
			}
    	}
		var searType=Ext.getCmp('checkItems').getValue();
		if(searType=='LIKES_developType'){
			Ext.apply(menuStore.baseParams,{
				filter_EQS_developType:Ext.getCmp('searTypecombo').getValue()
			});
		}
		else if(searType=='LIKES_developStage'){
			Ext.apply(menuStore.baseParams,{
				filter_EQS_developStage:Ext.getCmp('searLevelcombo').getValue()
			});
		}else if(searType=='LIKES_assessResult'){
			Ext.apply(menuStore.baseParams,{
				filter_EQL_assessResult:Ext.getCmp('searassessResult').getValue()
			});
		}else{
			Ext.apply(menuStore.baseParams,{
				checkItems : Ext.getCmp("checkItems").getValue(),
   				itemsValue : Ext.get("searchContent").dom.value
			});
		}
		menuStoreReload();
		
	}
   menuGrid.render();
    menuGrid.on('click', function() {
        var _record = menuGrid.getSelectionModel().getSelections();
        var updatebtn = Ext.getCmp('updatebtn');
        var deletebtn = Ext.getCmp('deletebtn');
        var assbtn=Ext.getCmp('assessbtn');
        if (_record.length==1) {       	
            if(updatebtn){
            	updatebtn.setDisabled(false);
            }
            if(deletebtn){
            	deletebtn.setDisabled(false);
            }
            if(assbtn){
            	assbtn.setDisabled(false);
            }
        }else if(_record.length>1){
        	 if(updatebtn){
            	updatebtn.setDisabled(true);
            }
            if(deletebtn){
            	deletebtn.setDisabled(false);
            }
            if(assbtn){
            	assbtn.setDisabled(false);
            }
        } else {
            if(updatebtn){
            	updatebtn.setDisabled(true);
            }
			if(deletebtn){
            	deletebtn.setDisabled(true);
			}
			if(assbtn){
            	assbtn.setDisabled(true);
			}
        }
    });
    function cusOper(_record,assess) {
	var form = new Ext.form.FormPanel({
					frame : true,
					border : false,
					bodyStyle : 'padding:5px 5px 5px',
					labelWidth : 90,
					fileUpload : true,
					width : 650,
					labelAlign : "right",
					reader : new Ext.data.JsonReader({
			            root: 'result', totalProperty: 'totalCount'
			        }, fields),
			        items:[{xtype:'hidden',name:'ts',id:'ts'},
						   	{xtype:'hidden',name:'id',id:'id'},
							{xtype:'hidden',name:'isAudit',id:'isAudit',value:'0'},
						{
							xtype:"fieldset",
							id:'addsetmsg',
							style:'margin:5px;',
							bodyStyle:'padding:5px 0px 0px 0px',
							width : 620,
							layout:'column',
							items:[{
					        		layout:'form',
					        		columnWidth :.5,
					        		items:[
						   					{xtype:'hidden',name:'departId',value:bussDepart},
						   					{xtype:'hidden',name:'developMan',id:'developMan',value:userName},
						   					{xtype:'hidden',name:'cusRecordId',id:'cusRecordId',value:parent.cusRecordId},
								          	{
								   				xtype:'combo',
				                            	store:cusRecordStore,
				                            	displayField:'cusName',
				                            	valueField:'id',
				                            	pageSize:pageSize,
				                            	queryParam:'filter_LIKES_cusName',
				                            	triggerAction : 'all',
				                            	model:'local',
				                            	minChars:0,
				                            	id:'cuscombo',
				                            	emptyText:'请选择',
				                            	fieldLabel:'客户名称<span style="color:red">*</span>',
				                            	hiddenName:'cusRecordId',
				                            	width:160,
				                            	allowBlank:false,
				                            	blankText:'客户名称不能为空！',
				                            	listeners:{
													'select':function(combo){
														Ext.getCmp('linkmancombo').clearValue();
														Ext.apply(linkManStore.baseParams,{
															filter_EQL_cusRecordId:combo.getValue()
														});
														Ext.getCmp('linkmancombo').store.load();
														Ext.Ajax.request({
															url:sysPath+'/cus/cusRecordAction!list.action',
															params:{
																filter_EQL_id:combo.getValue()
															},success:function(resp){
																var respText = Ext.util.JSON.decode(resp.responseText);
																Ext.getCmp('developStage').setValue(respText.result[0].developLevel);
															}
														});
													}						                            	
				                            	}
										   	},
										   	{
				                            	xtype : 'combo',
				    							typeAhead : true,
				    							forceSelection : true,
				    							width:160,
				    							queryParam : 'filter_LIKES_name',
				    							minChars : 0,
				    							fieldLabel : '活动联系人<span style="color:red">*</span>',
				    							store : linkManStore,
				    							pageSize : pageSize,
				    							triggerAction : 'all',
				    							model:'remote',
				    							id:'linkmancombo',
				    							valueField : 'linkMan',
				    							displayField : 'linkMan',
				    							name : 'developedMan',
				    							emptyText : "请选择",
				    							allowBlank : false,
				    							blankText : "活动联系人不能为空！",
				    							listeners:{
				    								'select':function(combo){
				    									var cId;
				    									if(parent.cusRecordId==null){
				    										cId=Ext.getCmp('cuscombo').getValue();
				    									}else{
															cId=parent.cusRecordId;				    									
				    									}
				    									Ext.Ajax.request({
															url:sysPath+'/cus/cusLinkManAction!ralaList.action',
															params:{
																privilege:152,
																filter_EQS_name:combo.getValue(),
																filter_EQL_cusRecordId:cId
															},success:function(resp){
																var respText = Ext.util.JSON.decode(resp.responseText);
																Ext.getCmp('developLinkmanTel').setValue(respText.result[0].tel);
															}
														});
				    								}
				    								
				    							}
				                            }]
				                    },{
				                    layout:'form',
				                    columnWidth :.5,
				                    items:[
				                           {
								          		xtype : "combo",
												triggerAction : 'all',
												name:'developStage',
												id:'developStage',
												store:developLevelStore,
												valueField:'developLevel',
												displayField:'developLevel',
												model:'local',
												allowBlank:false,
												value:developLevel,
												blankText:'客户阶段不能为空！',
												fieldLabel : '客户阶段<span style="color:red">*</span>'
								          	},
								          	{
												xtype:'textfield',
												name:'developLinkmanTel',
												id:'developLinkmanTel',
												width:160,
												fieldLabel:'联系电话',
												maxLength:100,
												maxLengthText:'长度不能超过100个字符'
				                            }
				                    ]
				            }]
						},{
							xtype:"fieldset",
							id:'dutysetmsg',
							style:'margin:5px;',
							width : 620,
							layout:'column',
							bodyStyle:'padding:5px 0px 0px 0px',
							items:[{
					        		layout:'form',
					        		columnWidth :.5,
					        		items:[{xtype:'textfield',fieldLabel:'活动主题<span style="color:red">*</span>',name:'developName',allowBlank:false,blankText:'过程名称不能为空！',width:160},
								          	{xtype:'numberfield',fieldLabel:'活动成本',name:'developCost',id:'developCost',width:160},
								          	{
								          		xtype:'textarea',
								          		height:60,
								          		fieldLabel:'经过介绍<span style="color:red">*</span>',
								          		width:200,
								          		name:'developContext',
								          		allowBlank : false,
				    							blankText : "经过介绍不能为空！",
				    							maxLength:1000,
												maxLengthText:'长度不能超过1000个汉字'
								          	}
		                           	]
				                    },{
				                    layout:'form',
				                    columnWidth :.5,
				                    items:[{
						                           		xtype : "combo",
														triggerAction : 'all',
														name:'developType',
														//id:'developType',
														store:developTypeStore,
														valueField:'developType',
														displayField:'developType',
														model:'local',
														allowBlank:false,
														emptyText:'请选择',
														blankText:'活动类型不能为空！',
														fieldLabel : '活动类型<span style="color:red">*</span>'
							                           
						                           	},
						                           {
						                           		xtype:'textfield',
						                           		fieldLabel:'附件',
						                           		width:200,
						                           		text:'浏览',
						                           		name:'upFile',
						                           		inputType:'file'                      	
						                           	},
							                          {
										          		xtype:'textarea',
										          		height:60,
										          		fieldLabel:'备注',
										          		width:200,
										          		name:'remark',
										          		maxLength:250,
														maxLengthText:'长度不能超过250个汉字'
									          		}
				                    ]
				            }]
						},{
							xtype:"fieldset",
							id:'setmsg',
							style:'margin:5px;',
							width : 620,
							layout:'column',
							bodyStyle:'padding:5px 0px 0px 0px',
							items:[{
					        		layout:'form',
					        		columnWidth :.5,
					        		items:[{
					        			xtype:'combo',
				                        hiddenName:'cusPleased',
				                        forceSelection : true,
				                        triggerAction : 'all',
				                        hiddenName:'assessResult',
				                        hiddenId:'assessResult',
										model : 'local',
				                        width:160,
				                        fieldLabel:'评估结果',
				                        store:[
				                            ['1','非常不满意'],
				                            ['2','不满意'],
				                            ['3','一般'],
				                            ['4','满意'],
				                            ['5','非常满意']
				                       ]
					        			
					        		},{
					        			xtype:'textfield',
					        			fieldLabel:'评估人',
					        			name:'auditName',
					        			readOnly:true,
					        			id:'auditName',
					        			width:160,
					        			maxLength:10,
										maxLengthText:'长度不能超过10个汉字'
					        		},{
					        			xtype:'datefield',
					        			fieldLabel:'评估时间',
					        			name:'auditTime',
					        			format:'Y-m-d',
					        			id:'auditTime',
					        			width:160
					        		}]
				                    },{
				                    layout:'form',
				                    columnWidth :.5,
				                    items:[{
						                 xtype:'textarea',
						                 fieldLabel:'结果说明',
						                 width:200,
						                 name:'resultRemark',
						                 height:60,
						                 maxLength:250,
						                 maxLengthText:'长度不能大于250个汉字！'
						            }]
				            }]
						}]
				});
		operTitle="新增活动过程";
		if(_record!=null){
			operTitle="修改活动过程";
			Ext.getCmp("id").setValue(_record[0].data.id);
			form.load({
				url : sysPath+ "/cus/cusDevelopAction!ralaList.action",
				params:{filter_EQL_id:_record[0].data.id,privilege:privilege,limit : pageSize},
				success:function(_form, action){
					if(assess=='assess'){
						Ext.getCmp('addsetmsg').setDisabled(true);
						Ext.getCmp('dutysetmsg').setDisabled(true);
						Ext.getCmp('isAudit').setValue("1");
						Ext.getCmp('auditName').setValue(userName);
						Ext.getCmp('auditTime').setValue(new Date());
					}else{
						Ext.getCmp('setmsg').setDisabled(true);
					}
					Ext.getCmp('cuscombo').setValue(_record[0].data.cusRecordId);
					Ext.getCmp('cuscombo').setRawValue(_record[0].data.cusName);
				}
			})
		}
		var win = new Ext.Window({
			title : operTitle,
			width : 650,
			closeAction : 'hide',
			plain : true,
			modal : true,
			//animateTarget:'addbtn',
			items : form,
			buttonAlign : "center",
			buttons : [{
				text : "保存",
				iconCls : 'save',
				handler : function() {
					if (form.getForm().isValid()) {
						form.getForm().submit({
							url : sysPath + "/cus/cusDevelopAction!save.action",
							waitMsg : '正在保存数据...',
							success : function(form, action) {
								win.hide(), 
								Ext.Msg.alert(alertTitle,
										action.result.msg);
										menuStoreReload();
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
				}
			}, {
				text : "重置",
				handler : function() {
					if(_record!=null){
						carTitle='修改活动过程信息';
						form.load({
						url : sysPath
								+ "/sys/cusDevelopAction!list.action",
						params:{filter_EQL_id:_record[0].data.id,privilege:privilege,limit : pageSize}
						});
					}else{
						form.getForm().reset();
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
		Ext.getCmp('cusRecordId').setDisabled(true);
	}
	 function cusOpernoNull(_record,assess) {
		var form = new top.Ext.form.FormPanel({
					frame : true,
					border : false,
					bodyStyle : 'padding:5px 5px 5px',
					labelWidth : 90,
					fileUpload : true,
					width : 650,
					labelAlign : "right",
					reader : new Ext.data.JsonReader({
			            root: 'result', totalProperty: 'totalCount'
			        }, fields),
			        items:[	{xtype:'hidden',name:'ts',id:'ts'},
						   	{xtype:'hidden',name:'id',id:'id'},
							{xtype:'hidden',name:'isAudit',id:'isAudit',value:'0'},
							{
							xtype:"fieldset",
							id:'addsetmsg',
							style:'margin:5px;',
							bodyStyle:'padding:5px 0px 0px 0px',
							width : 620,
							layout:'column',
							items:[{
					        		layout:'form',
					        		columnWidth :.5,
					        		items:[
						   					{xtype:'hidden',name:'departId',value:bussDepart},
						   					{xtype:'hidden',name:'developMan',id:'developMan',value:userName},
						   					{xtype:'hidden',name:'cusRecordId',id:'cusRecordId',value:parent.cusRecordId},
								          	{
										   				xtype:'combo',
						                            	store:cusRecordStore,
						                            	displayField:'cusName',
						                            	valueField:'id',
						                            	pageSize:pageSize,
						                            	queryParam:'filter_LIKES_cusName',
						                            	triggerAction : 'all',
						                            	model:'local',
						                            	minChars:0,
						                            	id:'cuscombo',
						                            	emptyText:'请选择',
						                            	fieldLabel:'客户名称<span style="color:red">*</span>',
						                            	hiddenName:'cusRecordId',
						                            	width:160,
						                            	allowBlank:false,
						                            	blankText:'客户名称不能为空！',
						                            	listeners:{
															'select':function(combo){
																Ext.getCmp('linkmancombo').clearValue();
																Ext.apply(linkManStore.baseParams,{
																	filter_EQL_cusRecordId:combo.getValue()
																});
																Ext.getCmp('linkmancombo').store.load();
																Ext.Ajax.request({
																	url:sysPath+'/cus/cusRecordAction!list.action',
																	params:{
																		filter_EQL_id:combo.getValue()
																	},success:function(resp){
																		var respText = Ext.util.JSON.decode(resp.responseText);
																		Ext.getCmp('developStage').setValue(respText.result[0].developLevel);
																	}
																});
															}						                            	
						                            	}
										   	},
										   	{
				                            	xtype : 'combo',
				    							typeAhead : true,
				    							forceSelection : true,
				    							width:160,
				    							queryParam : 'filter_LIKES_name',
				    							minChars : 0,
				    							fieldLabel : '活动联系人<span style="color:red">*</span>',
				    							store : linkManStore,
				    							pageSize : pageSize,
				    							triggerAction : 'all',
				    							model:'remote',
				    							id:'linkmancombo',
				    							valueField : 'linkMan',
				    							displayField : 'linkMan',
				    							name : 'developedMan',
				    							emptyText : "请选择",
				    							allowBlank : false,
				    							blankText : "活动联系人不能为空！",
				    							listeners:{
				    								'select':function(combo){
				    									var cId;
				    									if(parent.cusRecordId==null){
				    										cId=top.Ext.getCmp('cuscombo').getValue();
				    									}else{
															cId=parent.cusRecordId;				    									
				    									}
				    									Ext.Ajax.request({
															url:sysPath+'/cus/cusLinkManAction!ralaList.action',
															params:{
																privilege:152,
																filter_EQS_name:combo.getValue(),
																filter_EQL_cusRecordId:cId
															},success:function(resp){
																var respText = Ext.util.JSON.decode(resp.responseText);
																top.Ext.getCmp('developLinkmanTel').setValue(respText.result[0].tel);
															}
														});
				    								}
				    								
				    							}
				                            }]
				                    },{
				                    layout:'form',
				                    columnWidth :.5,
				                    items:[
				                           {
								          		xtype : "combo",
												triggerAction : 'all',
												name:'developStage',
												id:'developStage',
												store:developLevelStore,
												valueField:'developLevel',
												displayField:'developLevel',
												model:'local',
												allowBlank:false,
												value:developLevel,
												blankText:'客户阶段不能为空！',
												fieldLabel : '客户阶段<span style="color:red">*</span>'
								          	},
								          	{
												xtype:'textfield',
												name:'developLinkmanTel',
												id:'developLinkmanTel',
												width:160,
												fieldLabel:'联系电话',
												maxLength:100,
												maxLengthText:'长度不能超过100个字符'
				                            }
				                    ]
				            }]
						},{
							xtype:"fieldset",
							id:'dutysetmsg',
							style:'margin:5px;',
							width : 620,
							layout:'column',
							bodyStyle:'padding:5px 0px 0px 0px',
							items:[{
					        		layout:'form',
					        		columnWidth :.5,
					        		items:[{xtype:'textfield',fieldLabel:'活动主题<span style="color:red">*</span>',name:'developName',allowBlank:false,blankText:'过程名称不能为空！',width:160},
								          	{xtype:'numberfield',fieldLabel:'活动成本',name:'developCost',id:'developCost',width:160},
								          	{
								          		xtype:'textarea',
								          		height:60,
								          		fieldLabel:'经过介绍<span style="color:red">*</span>',
								          		width:200,
								          		name:'developContext',
								          		allowBlank : false,
				    							blankText : "经过介绍不能为空！",
				    							maxLength:1000,
												maxLengthText:'长度不能超过1000个汉字'
								          	}
		                           	]
				                    },{
				                    layout:'form',
				                    columnWidth :.5,
				                    items:[{
			                           		xtype : "combo",
											triggerAction : 'all',
											name:'developType',
											//id:'developType',
											store:developTypeStore,
											valueField:'developType',
											displayField:'developType',
											model:'local',
											allowBlank:false,
											emptyText:'请选择',
											blankText:'活动类型不能为空！',
											fieldLabel : '活动类型<span style="color:red">*</span>'
				                           
			                           	},
			                           {
			                           		xtype:'textfield',
			                           		fieldLabel:'附件',
			                           		width:200,
			                           		text:'浏览',
			                           		name:'upFile',
			                           		inputType:'file'                      	
			                           	},
				                          {
							          		xtype:'textarea',
							          		height:60,
							          		fieldLabel:'备注',
							          		width:200,
							          		name:'remark',
							          		maxLength:250,
											maxLengthText:'长度不能超过250个汉字'
						          		}
				                    ]
				            }]
						},{
							xtype:"fieldset",
							id:'setmsg',
							style:'margin:5px;',
							width : 620,
							layout:'column',
							bodyStyle:'padding:5px 0px 0px 0px',
							items:[{
					        		layout:'form',
					        		columnWidth :.5,
					        		items:[{
					        			xtype:'combo',
				                        hiddenName:'cusPleased',
				                        forceSelection : true,
				                        triggerAction : 'all',
				                        hiddenName:'assessResult',
				                        hiddenId:'assessResult',
										model : 'local',
				                        width:160,
				                        fieldLabel:'评估结果',
				                        store:[
				                            ['1','非常不满意'],
				                            ['2','不满意'],
				                            ['3','一般'],
				                            ['4','满意'],
				                            ['5','非常满意']
				                       ]
					        			
					        		},{
					        			xtype:'textfield',
					        			fieldLabel:'评估人',
					        			name:'auditName',
					        			readOnly:true,
					        			id:'auditName',
					        			width:160,
					        			maxLength:10,
										maxLengthText:'长度不能超过10个汉字'
					        		},{
					        			xtype:'datefield',
					        			fieldLabel:'评估时间',
					        			name:'auditTime',
					        			format:'Y-m-d',
					        			id:'auditTime',
					        			width:160
					        		}]
				                    },{
				                    layout:'form',
				                    columnWidth :.5,
				                    items:[{
						                 xtype:'textarea',
						                 fieldLabel:'结果说明',
						                 width:200,
						                 name:'resultRemark',
						                 height:60,
						                 maxLength:250,
						                 maxLengthText:'长度不能大于250个汉字！'
						            }]
				            }]
						}]
				});
		operTitle="新增活动过程";
		if(_record!=null){
			operTitle="修改活动过程";
			top.Ext.getCmp("id").setValue(_record[0].data.id);
			form.load({
				url : sysPath+ "/cus/cusDevelopAction!ralaList.action",
				params:{filter_EQL_id:_record[0].data.id,privilege:privilege,limit : pageSize},
				success:function(_form, action){
					if(assess=='assess'){
						top.Ext.getCmp('addsetmsg').setDisabled(true);
						top.Ext.getCmp('dutysetmsg').setDisabled(true);
						top.Ext.getCmp('isAudit').setValue("1");
						top.Ext.getCmp('auditName').setValue(userName);
						top.Ext.getCmp('auditTime').setValue(new Date());
					}else{
						top.Ext.getCmp('setmsg').setDisabled(true);
					}
					top.Ext.getCmp('cuscombo').setValue(_record[0].data.cusRecordId);
					top.Ext.getCmp('cuscombo').setRawValue(_record[0].data.cusName);
				}
			})
		}
		var win = new top.Ext.Window({
			title : operTitle,
			width : 650,
			closeAction : 'hide',
			plain : true,
			modal : true,
			//animateTarget:'addbtn',
			items : form,
			buttonAlign : "center",
			buttons : [{
				text : "保存",
				iconCls : 'save',
				handler : function() {
					if (form.getForm().isValid()) {
						form.getForm().submit({
							url : sysPath + "/cus/cusDevelopAction!save.action",
							waitMsg : '正在保存数据...',
							success : function(form, action) {
								win.hide(), 
								top.Ext.Msg.alert(alertTitle,
										action.result.msg);
										menuStoreReload();
							},
							failure : function(form, action) {
								if (action.faliureType == Ext.form.Action.SERVER_INVALID) {
									top.Ext.Msg.alert(alertTitle, "服务器连接异常，请联系管理员！");
								} else {
									if (action.result.msg) {
										win.hide();
										top.Ext.Msg.alert(alertTitle,
												action.result.msg);
									}
								}
							}
						});
					}
				}
			}, {
				text : "重置",
				handler : function() {
					if(_record!=null){
						carTitle='修改活动过程信息';
						form.load({
						url : sysPath
								+ "/sys/cusDevelopAction!list.action",
						params:{filter_EQL_id:_record[0].data.id,privilege:privilege,limit : pageSize}
						});
					}else{
						form.getForm().reset();
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
		if(parent.cusRecordId==null){
			top.Ext.getCmp('cusRecordId').setDisabled(true);
		}else{
			top.Ext.getCmp('cuscombo').setRawValue(parent.cusName);
			top.Ext.getCmp('cuscombo').setDisabled(true);
			top.Ext.getCmp('developStage').setValue(parent.cusDevelop);
			Ext.apply(linkManStore.baseParams,{
				filter_EQL_cusRecordId:parent.cusRecordId					
			});
		}
	}
	function menuStoreReload(){
		menuStore.reload({
			params:{
				privilege:privilege,
				start : 0,
				limit : pageSize
			}
		});
	}
    //end
});
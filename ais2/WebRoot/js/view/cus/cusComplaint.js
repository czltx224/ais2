var privilege=170;
var operTitle;
var fields=[
			{name:'id'},
            {name: 'complaintCus'},
            {name: 'complaintDno'},
            {name: 'complaintDate'},
            {name: 'complaintTime'},
            {name: 'complaintType'},
            {name:'compliantManType'},
            {name: 'complaintContext'},
            {name: 'complaintLevel'},
            {name: 'appellateName'},
            {name: 'isAccept'},
            {name: 'acceptTime'},
            {name: 'acceptName'},
            {name: 'filePath'},
            {name: 'dutyName'},
            {name: 'dutyTime'},
            {name:'dealResult'},
            {name:'dealCost'},
            {name: 'createName'},
            {name: 'createTime'},
            {name: 'updateName'},
            {name: 'updateTime'},
            {name:'complaintTel'},
            {name:'complaintName'},
            {name:'complaintRequire'},
            {name:'complaintPromise'},
            {name:'replyDate'},
            {name:'replyTime'},
            {name:'actualDutyTime'},
            {name:'againTime'},
            {name:'complaintFeedback'},
            {name:'spendTime'},
            {name:'cusPleased'},
            {name:'cusRecordId'},
            {name:'status'},
            {name:'remark'},
            {name:'ts'}
];
Ext.onReady(function() {
    var menuStore = new Ext.data.Store({
        storeId:"menuStore",
        baseParams:{privilege:privilege,filter_EQL_cusRecordId:parent.cusRecordId,filter_EQL_status:1,filter_EQL_departId:bussDepart},
        proxy: new Ext.data.HttpProxy({url:sysPath+"/cus/cusComplaintAction!list.action"}),
        reader: new Ext.data.JsonReader({
            root: 'result', totalProperty: 'totalCount'
        }, fields)
    });
    if(parent.cusRecordId!=null){
    	 menuStore.load();
    }
    //客户类型Store
	var isCqStore=new Ext.data.Store({
			storeId:'isCqStore',
			baseParams:{filter_EQL_basDictionaryId:38,privilege:16},
			proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/dictionaryAction!ralaList.action"}),
			reader:new Ext.data.JsonReader({
	                    root:'result',
	                    totalProperty:'totalCount'
	        },[
	        	{name:'id'},
	        	{name:'isCq',mapping:'typeName'}
	        	])
	});
	var cusServiceStore=new Ext.data.Store({
			autoLoad:true,
	        storeId:"cusServiceStore",
			proxy:new Ext.data.HttpProxy({url:sysPath+"/user/userAction!list.action"}),
			reader:new Ext.data.JsonReader({
	                    root:'result',
	                    totalProperty:'totalCount'
	        },[
        	{name:'cusServiceId',mapping:'id'},
        	{name:'cusServiceName',mapping:'userName'}
        	])
        	
		});
    var cusRecordStore=new Ext.data.Store({
		storeId:'cusRecordStore', 
		paseParams:{limit:pageSize,filter_EQL_departId:bussDepart},
		proxy: new Ext.data.HttpProxy({url:sysPath+"/cus/cusRecordAction!list.action"}),
        reader: new Ext.data.JsonReader({
            root: 'result', totalProperty: 'totalCount'
        }, [{name:'cusRecordId',mapping:'id'},{name:'cusName'}])
    });
    var complaintTypeStore=new Ext.data.Store({
		storeId:"complaintTypeStore",
		baseParams:{filter_EQL_basDictionaryId:46,privilege:16},
		proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/dictionaryAction!ralaList.action"}),
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
        	{name:'id'},
        	{name:'complaintType',mapping:'typeName'}
        	])
	});
    //start
    var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:true});
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
            {header:'联系人ID',dataIndex:"cusRecordId",hidden: true, hideable: false},
            {header:'是否关闭',dataIndex:"isAccept",width:60,renderer:function(v){
            	if(v==0){
					return '否';            	
            	}else if(v==1){
            		return '是';
            	}
            }},
            {header:'投诉客户',dataIndex:"complaintCus",width:80},
            {header:'投诉人',dataIndex:"complaintName",width:80},
            {header:'投诉电话',dataIndex:"complaintTel",width:80},
            {header:"投诉单号",dataIndex:"complaintDno",width:80},
            {header:"投诉日期",dataIndex:"complaintDate",width:100},
           // {header:"投诉时间",dataIndex:"complaintTime",width:80},
            {header:"投诉类型",dataIndex:"complaintType",width:80},
            {header:"投诉内容",dataIndex:"complaintContext",width:120},
            {header:"投诉等级",dataIndex:"complaintLevel",width:80,renderer:function(v){
            	if(v==0){
            		return '一般';
            	}else if(v==1){
            		return '严重';
            	}else if(v==2){
            		return '非常严重';
            	
            	}
            }},
            {header:"首问接待人",dataIndex:"appellateName",width:80},
            {header:"客户要求项",dataIndex:"complaintRequire",width:100},
            {header:"客服员承诺",dataIndex:"complaintPromise",width:100},
            {header:"要求回复时间",dataIndex:"replyDate",width:80},
            {header:"处理责任人",dataIndex:"dutyName",width:80},
            {header:"要求处理时间",dataIndex:"dutyTime",width:100},
            {header:"处理结果",dataIndex:"dealResult",width:100,renderer:function(v){
            	if(v==0){
					return '未处理';            	
            	}else if(v==1){
            		return '正在处理';
            	}else if(v==2){
            		return '处理完成';
            	}else{
            		return '';
            	}
            }},
            {header:"处理成本",dataIndex:"dealCost",width:80},
            {header:"实际处理完成时间",dataIndex:"actualDutyTime",width:80},
            {header:"花费时间",dataIndex:"spendTime",width:80,renderer:function(v){
            	if(v!=''){
            		if(v==0){
	            		return '1小时';
	            	}else if(v==1){
						return '2小时';            	
	            	}else if(v==2){
	            		return '半个工作日';
	            	}else if(v==3){
						return '1个工作日';            	
	            	}else if(v==4){
	            		return '2个工作日';
	            	}else if(v==5){
	            		return '2个以上工作日';
	            	}
            	}
            	
            }},
            {header:"回访时间",dataIndex:"actualDutyTime",width:80},
            {header:"客户反馈",dataIndex:"complaintFeedback",width:80},
            {header:"满意程度",dataIndex:"cusPleased",width:80,renderer:function(v){
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
            {header:"备注",dataIndex:"remark",width:100}
           
        ]),
        store:menuStore,
        tbar: [
            {
                text:'<B>新建投诉单</B>', id:'addbtn',tooltip:'增加客户投诉', iconCls: 'userAdd',handler:function() {
                	if(parent.cusRecordId==null){
                		cusOper(null,null);
                	}else{
                		cusOpernoNull(null,null);
                	}
            } },
            '-',
            {
                text:'<B>投诉单处理</B>',id:'updatebtn',disabled:true, tooltip:'修改客户投诉', iconCls: 'userEdit', handler: function() {
                var _record = menuGrid.getSelectionModel().getSelections();
                if (_record.length < 1) {
						parent.Ext.Msg.alert("系统消息", "请选择您要操作的行！");
						return false;
					} else if (_record.length > 1) {
						parent.Ext.Msg.alert("系统消息", "一次只能修改一行！");
						return false;
					}else{
						if(parent.cusRecordId==null){
	                		cusOper(_record,'duty');
	                	}else{
	                		cusOpernoNull(_record,'duty');
	                	}
					}
            } } ,
            '-',
            {
                text:'<B>作废</B>',id:'deletebtn',disabled:true, tooltip:'作废客户投诉信息', iconCls: 'userDelete',
                handler: function(){
                	var _records = menuGrid.getSelectionModel().getSelections();
                	if (_records.length < 1) {
						parent.Ext.Msg.alert(alertTitle, "请选择您要操作的行！");
						return false;
					} 
					Ext.Msg.confirm(alertTitle, "确定要删除所选的"+_records.length+"行记录吗？", function(btnYes) {
						if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
								var ids=_records[0].data.id;
								Ext.Ajax.request({
								url : sysPath+ "/cus/cusComplaintAction!delete.action",
								params : {
									comId : ids,
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
            },
            '-','客户:',
            {
            	xtype : 'combo',
            	triggerAction : 'all',
            	forceSelection : true,
            	pageSize:pageSize,
            	id:'cuscombo',
    			model : 'local',
    			hiddenName : 'cusName',
            	store: cusRecordStore,
            	valueField:'cusRecordId',
            	displayField:'cusName',
    			width : 80
            },'-',
            {
            	xtype : 'textfield',
    			id:'searchContent',
    			name : 'stationName',
    			width : 80,
    			enableKeyEvents:true,
    			listeners : {
			 		keyup:function(textField, e){
		                     if(e.getKey() == 13){
		                     	searchCusRequest();
		                  }
			 		}
	 			}
            },{
            	xtype : 'datefield',
    			format:'Y-m-d',
    			hidden:true,
    			id : 'datefieldstart',
    			name : 'startTime',
    			width : 80
            },{
            	xtype : 'datefield',
    			format:'Y-m-d',
    			hidden:true,
    			id : 'datefieldend',
    			name : 'startTime',
    			width : 80
            },
            '-',
		       
            {
            	xtype : 'combo',
            	triggerAction : 'all',
    			model : 'local',
    			id : 'checkItems',
    			name : 'checkItems',
    			name : 'checkItemstext',
            	store: [
            			['', '查询全部'], 
    					['LIKES_complaintName', '投诉人'],
    					['LIKES_complaintTel', '投诉电话'],
    					['EQS_complaintDno','投诉单号'],
    					['LIKES_complaintTime','投诉时间'],
    					['LIKES_appellateName','首问接待人']
    				   ],
    			width : 80,
    			listeners : {
    						'select' : function(combo, record, index) {
    							if (combo.getValue() == '') {
    								Ext.getCmp("searchContent").disable();
    								Ext.getCmp("searchContent").show();
    								Ext.getCmp("searchContent").setValue("");
    								
    								Ext.getCmp("datefieldstart").disable();
    								Ext.getCmp("datefieldstart").hide();
    								Ext.getCmp("datefieldstart").setValue("");
    								
    								Ext.getCmp("datefieldend").disable();
    								Ext.getCmp("datefieldend").hide();
    								Ext.getCmp("datefieldend").setValue("");
    							} else if(combo.getValue()=='LIKES_complaintTime'){
    								Ext.getCmp("searchContent").setValue("");
    								Ext.getCmp("searchContent").hide();
    								Ext.getCmp("searchContent").disable();
    								
    								Ext.getCmp("datefieldstart").show();
    								Ext.getCmp("datefieldend").show();
    								Ext.getCmp("datefieldstart").enable();
    								Ext.getCmp("datefieldend").enable();
    							}else {
    								Ext.getCmp("searchContent").enable();
    								Ext.getCmp("searchContent").show();
    								Ext.getCmp("datefieldstart").disable();
    								Ext.getCmp("datefieldstart").hide();
    								Ext.getCmp("datefieldstart").setValue("");
    								
    								Ext.getCmp("datefieldend").disable();
    								Ext.getCmp("datefieldend").hide();
    								Ext.getCmp("datefieldend").setValue("");
    							}
    							Ext.getCmp("searchContent").focus(true, true);
    						}
    					}
            },'-',{xtype : 'checkbox',
                id:'checkboxDriver',
                name:'checkboxDriver',
                boxLabel:'已关闭'
             },
            '-',
            {
            	text:'<B>搜索</B>',
            	id:'btnsearch', 
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
        }),
        listeners:{
        	'render':function(){
        		if(parent.viewtype=='mainview'){
		    		Ext.getCmp('cuscombo').setDisabled(true);
		    	}
        	}
        }
    });
    function searchCusRequest() {
    	var isAccept=0;
    	var cusId=Ext.getCmp('cuscombo').getValue();
    	if(cusId!=''){
    		menuStore.baseParams = {
				filter_EQL_cusRecordId:cusId
			}
    	}
    	if(Ext.get("checkboxDriver").dom.checked){
    		isAccept=1;
    	}
		var searType=Ext.getCmp('checkItems').getValue();
		if(searType=='LIKES_complaintTime'){
			var start=Ext.get("datefieldstart").dom.value;
    		var end=Ext.get("datefieldend").dom.value;
    		Ext.apply(menuStore.baseParams, {
   				filter_GED_complaintTime : start,
   				filter_LED_complaintTime : end,
   				filter_EQL_isAccept:isAccept,
   				checkItems :'',
   				itemsValue :''
   			});
		}else{
			Ext.apply(menuStore.baseParams,{
				checkItems : Ext.getCmp("checkItems").getValue(),
   				itemsValue : Ext.get("searchContent").dom.value,
   				filter_EQL_isAccept:isAccept,
   				filter_GED_complaintTime : '',
   				filter_LED_complaintTime:''
			});
		}
		var editbtn = Ext.getCmp('updatebtn');
		var deletebtn = Ext.getCmp('deletebtn');

		editbtn.setDisabled(true);
		deletebtn.setDisabled(true);
		menuStoreReload();
		
	}
   menuGrid.render();
    menuGrid.on('click', function() {
        var _record = menuGrid.getSelectionModel().getSelections();
        var updatebtn = Ext.getCmp('updatebtn');
        var deletebtn = Ext.getCmp('deletebtn');
        if (_record.length==1) {       	
            if(updatebtn){
            	updatebtn.setDisabled(false);
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
    });
    function cusOper(_record,type) {
	var form = new Ext.form.FormPanel({
					frame : true,
					border : false,
					bodyStyle : 'padding:5px 5px 5px',
					labelWidth : 90,
					width : 650,
					labelAlign : "right",
					reader : new Ext.data.JsonReader({
			            root: 'result', totalProperty: 'totalCount'
			        }, fields),
					items:[
						{
							xtype:"fieldset",
							id:'addsetmsg',
							style:'margin:5px;',
							width : 640,
							layout:'column',
							items:[{
					        		layout:'form',
					        		columnWidth :.5,
					        		items:[
					        				{xtype:'hidden',name:'ts',id:'ts'},
						   					{xtype:'hidden',name:'id',id:'id'},
						   					//{xtype:'hidden',name:'isAccept',id:'isAccept',value:0},
						   					{xtype:'hidden',name:'departId',value:bussDepart},
						   					{xtype:'hidden',name:'status',id:'status',value:1},
						   					{xtype:'hidden',name:'complaintCus',id:'complaintCus',value:parent.cusName},
								          	{
								          		xtype : 'combo',
								            	triggerAction : 'all',
								            	queryParam :'filter_LIKES_cusName',
								            	forceSelection : true,
								            	pageSize:pageSize,
								            	id:'complaintcuscombo',
								    			model : 'local',
								    			minChars:0,
								    			fieldLabel:'投诉客户<span style="color:red">*</span>',
								    			hiddenName : 'cusRecordId',
								            	store: cusRecordStore,
								            	valueField:'cusRecordId',
								            	value:parent.cusRecordId,
								            	displayField:'cusName',
								            	maxLength:25,
								            	maxLengthText:'长度不能超过25个汉字',
								    			width : 150,
								    			listeners:{
								    				'select':function(combo){
														Ext.getCmp('complaintCus').setValue(Ext.get('complaintcuscombo').dom.value);							    					
								    				}
								    			}
								          		},
								          	{
								          		xtype : "combo",
												triggerAction : 'all',
												name:'complaintType',
												id:'combodevelopStage',
												store:complaintTypeStore,
												valueField:'complaintType',
												displayField:'complaintType',
												width:150,
												model:'local',
												allowBlank:false,
												emptyText:'请选择',
												blankText:'投诉类型不能为空！',
												fieldLabel : '投诉类型<span style="color:red">*</span>'
								          	},{
								          		xtype : 'combo',
												triggerAction : 'all',
												name:'compliantManType',
												id:'isCq',
												width:150,
												store:isCqStore,
												valueField:'isCq',
												displayField:'isCq',
												model:'local',
												allowBlank:false,
												blankText:'投诉人类型不能为空！',
												fieldLabel : '投诉人类型<span style="color:red">*</span>'
								          	},
				                            {
				                            	xtype : 'textfield',
				                            	name:'appellateName',
				                            	id:'appellateName',
				                            	fieldLabel:'首问接待人<span style="color:red">*</span>',
				                            	width:150,
				                            	readOnly:true,
				                            	maxLength:25,
								            	maxLengthText:'长度不能超过25个汉字',
				                            	allowBlank:false,
				                            	blankText:'首问接待人不能为空',
				                            	value:userName
				    							
				                            },{
						                           xtype:'datetimefield',
						                           value:new Date(),
						                           style : 'padding-left:0px;',
				                            	   format:'Y-m-d H:i:s',
						                           width:150,
						                           fieldLabel:'投诉日期<span style="color:red">*</span>',
						                           allowBlank:false,
						                           blankText:'投诉日期不能为空！',
						                           name:'complaintDate',
						                           id:'complaintDate'
						                     },
				                            {
				                            	xtype:'combo',
				                            	fieldLabel:'投诉等级',
				                            	hiddenName:'complaintLevel',
				                            	value:'0',
				                            	width:150,
				                            	forceSelection : true,
				                            	triggerAction : 'all',
												model : 'local',
				                            	store:[
				                            	['0','一般'],
				                            	['1','重要'],
				                            	['2','非常重要']
				                            	]
				                            	
				                            },{
				                            	xtype:'textarea',
				                            	name:'complaintRequire',
				                            	id:'complaintRequire',
				                            	fieldLabel:'客户要求项',
				                            	height:50,
				                            	width:150,
				                            	maxLength:50,
								            	maxLengthText:'长度不能超过50个汉字'
				                            },{
				                            	xtype:'datetimefield',
				                            	fieldLabel:'要求处理时间',
				                            	name:'dutyTime',
				                            	id:'dutyTime',
				                            	style : 'padding-left:0px;',
				                            	format:'Y-m-d H:i:s',
				                            	width:150
				                            },
				                            {
				                            	xtype:'combo',
				                            	typeAhead : true,
												pageSize : pageSize,
												fieldLabel:'处理人',
												forceSelection : true,
												selectOnFocus : true,
												resizable : true,
												minChars : 0,
												width:150,
												queryParam : 'filter_LIKES_userName',
												triggerAction : 'all',
												store : cusServiceStore,
												mode : "remote",// 从服务器端加载值
												valueField : 'cusServiceId',// value值，与fields对应
												displayField : 'cusServiceName',// 显示值，与fields对应
											    name : 'dutyName',
											    id:'dutyName'
				                            }
		                           	]
				                    },{
				                    layout:'form',
				                    columnWidth :.5,
				                    items:[
			                           {
			                           		xtype : "textfield",
											name:'complaintDno',
											id:'complaintDno',
											width:150,
											allowBlank:false,
											blankText:'投诉单号不能为空！',
											fieldLabel : '投诉单号<span style="color:red">*</span>',
											maxLength:15,
					            			maxLengthText:'长度不能超过15个字符',
					            			listeners:{
					            				'blur':function(textField){
					            					Ext.Ajax.request({
					            						url:sysPath+"/fax/oprFaxInAction!ralaList.action",
					            						params:{
					            							privilege:68,
					            							filter_EQL_dno:textField.getValue()
					            						},success:function(resp){
					            							var respText = Ext.util.JSON.decode(resp.responseText);
					            							Ext.getCmp('cpName').setValue(respText.result[0].cpName);
					            						}
					            					});
					            				}
					            			}
				                           
			                           	},{
			                           		xtype:'textfield',
			                           		name:'complaintName',
			                           		id:'complaintName',
			                           		fieldLabel:'投诉人',
			                           		width:150,
			                           		maxLength:25,
					            			maxLengthText:'长度不能超过25个汉字'
			                           	},{
			                           		xtype:'textfield',
			                           		name:'cpName',
			                           		readOnly:true,
			                           		fieldLabel:'代理公司',
			                           		id:'cpName',
			                           		width:150
			                           	},
				                         
						          			{
						          			xtype:'textfield',
						          			fieldLabel:'投诉电话',
						          			name:'complaintTel',
						          			id:'complaintTel',
						          			width:150,
						          		    maxLength:50,
					            			maxLengthText:'长度不能超过50个字符'
						          		},
							          	{
							          		xtype:'textarea',
							          		height:50,
							          		fieldLabel:'投诉内容<span style="color:red">*</span>',
							          		name:'complaintContext',
							          		width:150,
							          		allowBlank : false,
			    							blankText : "投诉内容不能为空！",
			    							maxLength:250,
					            			maxLengthText:'长度不能超过250个汉字'
							          	},{
			                            	xtype:'textarea',
			                            	name:'complaintPromise',
			                            	id:'complaintPromise',
			                            	fieldLabel:'客服承诺项',
			                            	height:50,
			                            	width:150,
			                            	maxLength:50,
					            			maxLengthText:'长度不能超过50个汉字'
			                            },{
				                            	xtype:'datetimefield',
				                            	fieldLabel:'要求回复日期',
				                            	name:'replyDate',
				                            	id:'replyDate',
				                            	format:'Y-m-d H:i:s',
				                            	width:130
				                        }
				                    ]
				            }]
						},{
							xtype:"fieldset",
							id:'dutysetmsg',
							style:'margin:5px;',
							width : 640,
							layout:'column',
							items:[{
					        		layout:'form',
					        		columnWidth :.5,
					        		items:[{
				                            	xtype:'combo',
				                            	fieldLabel:'处理结果',
				                            	width:150,
				                            	hiddenName:'dealResult',
				                            	//id:'dealResult',
				                            	forceSelection : true,
				                            	triggerAction : 'all',
												model : 'local',
												value:0,
				                            	store:[
				                            		['0','未处理'],
				                            		['1','处理中'],
				                            		['2','处理完成']
				                            	]
				                            },{
				                          	xtype:'numberfield',
				                          	name:'dealCost',
				                          	fieldLabel:'处理成本',
				                          	id:'处理成本',
				                          	width:150,
				                          	maxValue:99999999.99,
											maxLength:11
				                          }
		                           	]
				                    },{
				                    layout:'form',
				                    columnWidth :.5,
				                    items:[{
												xtype:'datetimefield',
												format:'Y-m-d H:i:s',
												fieldLabel:'实际处理时间',
												width:150,
												name:'actualDutyTime',
												id:'actualDutyTime'
				                          },{
				                          	xtype:'combo',
				                          	forceSelection : true,
			                            	triggerAction : 'all',
											model : 'local',
											hiddenName:'spendTime',
											//id:'spendTime',
											width:150,
											store:[
												['0','1小时'],
												['1','2小时'],
												['2','半个工作日'],
												['3','1个工作日'],
												['4','2个工作日'],
												['5','2个以上工作日']
											],
			                            	width:150,
				                          	fieldLabel:'花费时间'
				                          }
				                    ]
				            }]
						},{
							xtype:"fieldset",
							id:'againsetmsg',
							style:'margin:5px;',
							width : 640,
							layout:'column',
							items:[{
					        		layout:'form',
					        		columnWidth :.5,
					        		items:[{
				                            	xtype:'combo',
				                            	hiddenName:'cusPleased',
				                            	//id:'cusPleased',
				                            	forceSelection : true,
				                            	triggerAction : 'all',
												model : 'local',
				                            	width:150,
				                            	fieldLabel:'满意程度',
				                            	store:[
				                            		['1','非常不满意'],
				                            		['2','不满意'],
				                            		['3','一般'],
				                            		['4','满意'],
				                            		['5','非常满意']
				                            	]
				                            },{
												xtype:'radiogroup',
					                           	id:'radioisstop',
					                           	fieldLabel:'是否关闭<span style="color:red">*</span>',
					                           	items: [{
								                    inputValue:'1',
								                    boxLabel: '是',
								                    name:'isAccept',
								                    checked:_record==null?false:(_record[0].data.isAccept=="1"?true:false)
								                }, {
								                    inputValue:'0',
								                    name:'isAccept',
								                    boxLabel: '否',
								                    checked:_record==null?true:(_record[0].data.isAccept=="0"?true:false)
								                }]				                            
				                            },{
						                           xtype:'textarea',
						                           name:'complaintFeedback',
						                           id:'complaintFeedback',
						                           fieldLabel:'客户反馈',
						                           width:150,
						                           height:50,
						                           maxLength:250,
								            	   maxLengthText:'长度不能超过250个汉字'
						                       }
		                           	]
				                    },{
				                    layout:'form',
				                    columnWidth :.5,
				                    items:[{
			                            	xtype:'datetimefield',
			                            	name:'againTime',
			                            	id:'againTime',
			                            	fieldLabel:'回访时间',
			                            	width:150,
			                            	format:'Y-m-d H:i:s'
			                            },{
			                            	xtype:'textarea',
			                            	name:'remark',
				                          	fieldLabel:'备注',
				                          	id:'remark',
				                          	width:150,
				                          	height:50,
				                          	maxLength:250,
					            			maxLengthText:'长度不能超过250个汉字'
			                            }
				                    ]
				            }]
						}]
                    
				});
		operTitle="新建投诉单";
		if(_record!=null){
			operTitle="投诉单处理";
			Ext.getCmp("id").setValue(_record[0].data.id);
			form.load({
				url : sysPath+ "/cus/cusComplaintAction!list.action",
				params:{filter_EQL_id:_record[0].data.id,privilege:privilege,limit : pageSize},
				success:function(){
					if(type=='duty'){
						//Ext.getCmp('dutyName').setValue(userName);
						Ext.getCmp('actualDutyTime').setValue(actualDutyTime);
					}
				}
			})
		}
		var win = new Ext.Window({
			title : operTitle,
			width : 670,
			closeAction : 'hide',
			plain : true,
			modal : true,
			items : form,
			buttonAlign : "center",
			buttons : [{
				text : "保存",
				iconCls : 'save',
				handler : function() {
					if (form.getForm().isValid()) {
						form.getForm().submit({
							url : sysPath + "/cus/cusComplaintAction!save.action?privilege=170",
							waitMsg : '正在保存数据...',
							success : function(form, action) {
								win.hide(), 
								Ext.Msg.alert(alertTitle,action.result.msg);
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
						carTitle='投诉单处理';
						form.load({
						url : sysPath
								+ "/sys/cusComplaintAction!list.action",
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
	}
	function cusOpernoNull(_record,type){
		var form = new top.Ext.form.FormPanel({
					frame : true,
					border : false,
					bodyStyle : 'padding:5px 5px 5px',
					labelWidth : 90,
					width : 650,
					labelAlign : "right",
					reader : new Ext.data.JsonReader({
			            root: 'result', totalProperty: 'totalCount'
			        }, fields),
					items:[
						{
							xtype:"fieldset",
							id:'addsetmsg',
							style:'margin:5px;',
							width : 640,
							layout:'column',
							items:[{
					        		layout:'form',
					        		columnWidth :.5,
					        		items:[
					        				{xtype:'hidden',name:'ts',id:'ts'},
						   					{xtype:'hidden',name:'id',id:'id'},
						   					//{xtype:'hidden',name:'isAccept',id:'isAccept',value:0},
						   					{xtype:'hidden',name:'departId',value:bussDepart},
						   					{xtype:'hidden',name:'status',id:'status',value:1},
						   					{xtype:'hidden',name:'complaintCus',id:'complaintCus',value:parent.cusName},
								          	{
								          		xtype : 'combo',
								            	triggerAction : 'all',
								            	queryParam :'filter_LIKES_cusName',
								            	forceSelection : true,
								            	pageSize:pageSize,
								            	id:'complaintcuscombo',
								    			model : 'local',
								    			minChars:0,
								    			fieldLabel:'投诉客户<span style="color:red">*</span>',
								    			hiddenName : 'cusRecordId',
								            	store: cusRecordStore,
								            	valueField:'cusRecordId',
								            	value:parent.cusRecordId,
								            	displayField:'cusName',
								            	maxLength:25,
								            	maxLengthText:'长度不能超过25个汉字',
								    			width : 150,
								    			listeners:{
								    				'select':function(combo){
														top.Ext.getCmp('complaintCus').setValue(top.Ext.get('complaintcuscombo').dom.value);							    					
								    				}
								    			}
								          		},
								          	{
								          		xtype : "combo",
												triggerAction : 'all',
												name:'complaintType',
												id:'combodevelopStage',
												store:complaintTypeStore,
												valueField:'complaintType',
												displayField:'complaintType',
												width:150,
												model:'local',
												allowBlank:false,
												emptyText:'请选择',
												blankText:'投诉类型不能为空！',
												fieldLabel : '投诉类型<span style="color:red">*</span>'
								          	},{
								          		xtype : 'combo',
												triggerAction : 'all',
												name:'compliantManType',
												id:'isCq',
												width:150,
												store:isCqStore,
												valueField:'isCq',
												displayField:'isCq',
												model:'local',
												allowBlank:false,
												blankText:'投诉人类型不能为空！',
												fieldLabel : '投诉人类型<span style="color:red">*</span>'
								          	},
				                            {
				                            	xtype : 'textfield',
				                            	name:'appellateName',
				                            	id:'appellateName',
				                            	fieldLabel:'首问接待人<span style="color:red">*</span>',
				                            	width:150,
				                            	readOnly:true,
				                            	maxLength:25,
								            	maxLengthText:'长度不能超过25个汉字',
				                            	allowBlank:false,
				                            	blankText:'首问接待人不能为空',
				                            	value:userName
				    							
				                            },{
						                           xtype:'datetimefield',
						                           value:new Date(),
						                           style : 'padding-left:0px;',
				                            	   format:'Y-m-d H:i:s',
						                           width:150,
						                           fieldLabel:'投诉日期<span style="color:red">*</span>',
						                           allowBlank:false,
						                           blankText:'投诉日期不能为空！',
						                           name:'complaintDate',
						                           id:'complaintDate'
						                     },
				                            {
				                            	xtype:'combo',
				                            	fieldLabel:'投诉等级',
				                            	hiddenName:'complaintLevel',
				                            	value:'0',
				                            	width:150,
				                            	forceSelection : true,
				                            	triggerAction : 'all',
												model : 'local',
				                            	store:[
				                            	['0','一般'],
				                            	['1','重要'],
				                            	['2','非常重要']
				                            	]
				                            	
				                            },{
				                            	xtype:'textarea',
				                            	name:'complaintRequire',
				                            	id:'complaintRequire',
				                            	fieldLabel:'客户要求项',
				                            	height:50,
				                            	width:150,
				                            	maxLength:50,
								            	maxLengthText:'长度不能超过50个汉字'
				                            },{
				                            	xtype:'datetimefield',
				                            	fieldLabel:'要求处理时间',
				                            	name:'dutyTime',
				                            	id:'dutyTime',
				                            	style : 'padding-left:0px;',
				                            	format:'Y-m-d H:i:s',
				                            	width:150
				                            },
				                            {
				                            	xtype:'combo',
				                            	typeAhead : true,
												pageSize : pageSize,
												fieldLabel:'处理人',
												forceSelection : true,
												selectOnFocus : true,
												resizable : true,
												minChars : 0,
												width:150,
												queryParam : 'filter_LIKES_userName',
												triggerAction : 'all',
												store : cusServiceStore,
												mode : "remote",// 从服务器端加载值
												valueField : 'cusServiceId',// value值，与fields对应
												displayField : 'cusServiceName',// 显示值，与fields对应
											    name : 'dutyName',
											    id:'dutyName'
				                            }
		                           	]
				                    },{
				                    layout:'form',
				                    columnWidth :.5,
				                    items:[
			                           {
			                           		xtype : "textfield",
											name:'complaintDno',
											id:'complaintDno',
											width:150,
											allowBlank:false,
											blankText:'投诉单号不能为空！',
											fieldLabel : '投诉单号<span style="color:red">*</span>',
											maxLength:15,
					            			maxLengthText:'长度不能超过15个字符',
					            			listeners:{
					            				'blur':function(textField){
					            					Ext.Ajax.request({
					            						url:sysPath+"/fax/oprFaxInAction!ralaList.action",
					            						params:{
					            							privilege:68,
					            							filter_EQL_dno:textField.getValue()
					            						},success:function(resp){
					            							var respText = Ext.util.JSON.decode(resp.responseText);
					            							top.Ext.getCmp('cpName').setValue(respText.result[0].cpName);
					            						}
					            					});
					            				}
					            			}
				                           
			                           	},{
			                           		xtype:'textfield',
			                           		name:'complaintName',
			                           		id:'complaintName',
			                           		fieldLabel:'投诉人',
			                           		width:150,
			                           		maxLength:25,
					            			maxLengthText:'长度不能超过25个汉字'
			                           	},{
			                           		xtype:'textfield',
			                           		name:'cpName',
			                           		readOnly:true,
			                           		fieldLabel:'代理公司',
			                           		id:'cpName',
			                           		width:150
			                           	},
				                         
						          			{
						          			xtype:'textfield',
						          			fieldLabel:'投诉电话',
						          			name:'complaintTel',
						          			id:'complaintTel',
						          			width:150,
						          		    maxLength:50,
					            			maxLengthText:'长度不能超过50个字符'
						          		},
							          	{
							          		xtype:'textarea',
							          		height:50,
							          		fieldLabel:'投诉内容<span style="color:red">*</span>',
							          		name:'complaintContext',
							          		width:150,
							          		allowBlank : false,
			    							blankText : "投诉内容不能为空！",
			    							maxLength:250,
					            			maxLengthText:'长度不能超过250个汉字'
							          	},{
			                            	xtype:'textarea',
			                            	name:'complaintPromise',
			                            	id:'complaintPromise',
			                            	fieldLabel:'客服承诺项',
			                            	height:50,
			                            	width:150,
			                            	maxLength:50,
					            			maxLengthText:'长度不能超过50个汉字'
			                            },{
				                            	xtype:'datetimefield',
				                            	fieldLabel:'要求回复日期',
				                            	name:'replyDate',
				                            	id:'replyDate',
				                            	format:'Y-m-d H:i:s',
				                            	width:130
				                        }
				                    ]
				            }]
						},{
							xtype:"fieldset",
							id:'dutysetmsg',
							style:'margin:5px;',
							width : 640,
							layout:'column',
							items:[{
					        		layout:'form',
					        		columnWidth :.5,
					        		items:[{
				                            	xtype:'combo',
				                            	fieldLabel:'处理结果',
				                            	width:150,
				                            	hiddenName:'dealResult',
				                            	//id:'dealResult',
				                            	forceSelection : true,
				                            	triggerAction : 'all',
												model : 'local',
												value:0,
				                            	store:[
				                            		['0','未处理'],
				                            		['1','处理中'],
				                            		['2','处理完成']
				                            	]
				                            },{
				                          	xtype:'numberfield',
				                          	name:'dealCost',
				                          	fieldLabel:'处理成本',
				                          	id:'处理成本',
				                          	width:150,
				                          	maxValue:99999999.99,
											maxLength:11
				                          }
		                           	]
				                    },{
				                    layout:'form',
				                    columnWidth :.5,
				                    items:[{
												xtype:'datetimefield',
												format:'Y-m-d H:i:s',
												fieldLabel:'实际处理时间',
												width:150,
												name:'actualDutyTime',
												id:'actualDutyTime'
				                          },{
				                          	xtype:'combo',
				                          	forceSelection : true,
			                            	triggerAction : 'all',
											model : 'local',
											hiddenName:'spendTime',
											//id:'spendTime',
											width:150,
											store:[
												['0','1小时'],
												['1','2小时'],
												['2','半个工作日'],
												['3','1个工作日'],
												['4','2个工作日'],
												['5','2个以上工作日']
											],
			                            	width:150,
				                          	fieldLabel:'花费时间'
				                          }
				                    ]
				            }]
						},{
							xtype:"fieldset",
							id:'againsetmsg',
							style:'margin:5px;',
							width : 640,
							layout:'column',
							items:[{
					        		layout:'form',
					        		columnWidth :.5,
					        		items:[{
				                            	xtype:'combo',
				                            	hiddenName:'cusPleased',
				                            	//id:'cusPleased',
				                            	forceSelection : true,
				                            	triggerAction : 'all',
												model : 'local',
				                            	width:150,
				                            	fieldLabel:'满意程度',
				                            	store:[
				                            		['1','非常不满意'],
				                            		['2','不满意'],
				                            		['3','一般'],
				                            		['4','满意'],
				                            		['5','非常满意']
				                            	]
				                            },{
												xtype:'radiogroup',
					                           	id:'radioisstop',
					                           	fieldLabel:'是否关闭<span style="color:red">*</span>',
					                           	items: [{
								                    inputValue:'1',
								                    boxLabel: '是',
								                    name:'isAccept',
								                    checked:_record==null?false:(_record[0].data.isAccept=="1"?true:false)
								                }, {
								                    inputValue:'0',
								                    name:'isAccept',
								                    boxLabel: '否',
								                    checked:_record==null?true:(_record[0].data.isAccept=="0"?true:false)
								                }]				                            
				                            },{
						                           xtype:'textarea',
						                           name:'complaintFeedback',
						                           id:'complaintFeedback',
						                           fieldLabel:'客户反馈',
						                           width:150,
						                           height:50,
						                           maxLength:250,
								            	   maxLengthText:'长度不能超过250个汉字'
						                       }
		                           	]
				                    },{
				                    layout:'form',
				                    columnWidth :.5,
				                    items:[{
			                            	xtype:'datetimefield',
			                            	name:'againTime',
			                            	id:'againTime',
			                            	fieldLabel:'回访时间',
			                            	width:150,
			                            	format:'Y-m-d H:i:s'
			                            },{
			                            	xtype:'textarea',
			                            	name:'remark',
				                          	fieldLabel:'备注',
				                          	id:'remark',
				                          	width:150,
				                          	height:50,
				                          	maxLength:250,
					            			maxLengthText:'长度不能超过250个汉字'
			                            }
				                    ]
				            }]
						}]
                    
				});
		operTitle="新建投诉单";
		if(_record!=null){
			operTitle="投诉单处理";
			//parent.parent.Ext.getCmp("id").setValue(_record[0].data.id);
			form.load({
				url : sysPath+ "/cus/cusComplaintAction!list.action",
				params:{filter_EQL_id:_record[0].data.id,privilege:privilege,limit : pageSize},
				success:function(resp){
					//parent.parent.Ext.getCmp('complaintcuscombo').setValue(_record[0].data.cusRecordId);
					top.Ext.getCmp('complaintcuscombo').setRawValue(_record[0].data.complaintCus);
					if(type=='duty'){
						//parent.parent.Ext.getCmp('dutyName').setValue(userName);
						top.Ext.getCmp('actualDutyTime').setValue(new Date());
					}
				}
			})
		}
		var win = new top.Ext.Window({
			title : operTitle,
			width : 670,
			plain : true,
			modal : true,
			items : form,
			buttonAlign : "center",
			buttons : [{
				text : "保存",
				iconCls : 'save',
				handler : function() {
					if (form.getForm().isValid()) {
						form.getForm().submit({
							url : sysPath + "/cus/cusComplaintAction!save.action?privilege=170",
							waitMsg : '正在保存数据...',
							success : function(form, action) {
								win.hide(), 
								top.Ext.Msg.alert(alertTitle,action.result.msg);
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
						carTitle='投诉单处理';
						form.load({
						url : sysPath
								+ "/sys/cusComplaintAction!list.action",
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
		if(parent.cusRecordId!=null){
			top.Ext.getCmp('complaintcuscombo').setRawValue(parent.cusName);
			top.Ext.getCmp('complaintcuscombo').setDisabled(true);
		}else{
			top.Ext.getCmp('complaintcuscombo').setDisabled(false);
		}
	}
	/*
	function duty(record){
		var form1=new Ext.form.FormPanel({
			frame : true,
			border : false,
			bodyStyle : 'padding:5px 5px 5px',
			labelWidth : 90,
			width : 400,
			labelAlign : "right",
			items:[
			{xtype:'textfield',name:'dutyName',id:'dutyName',fieldLabel:'处理人',value:userName,readOnly:true},
			{xtype:'numberfield',name:'dealCost',id:'dealCost',fieldLabel:'处理成本<span style="color:red">*</span>',allowBlank:false,blankText:'处理成本不能为空！'},
			{xtype:'textfield',name:'dealResult',id:'dealResult',fieldLabel:'处理结果'},
			{xtype:'datefield',name:'dutyTime',id:'dutyTime',fieldLabel:'处理时间',format:'Y-m-d',value:new Date()}
			]
					
		});
		var win1 = new Ext.Window({
			title : operTitle,
			width : 400,
			closeAction : 'hide',
			plain : true,
			modal : true,
			items : form1,
			buttonAlign : "center",
			buttons : [{
				text : "保存",
				iconCls : 'save',
				handler : function() {
					if (form1.getForm().isValid()) {
						form1.getForm().submit({
							url : sysPath + "/cus/cusComplaintAction!duty.action",
							waitMsg : '正在保存数据...',
							params:{
								comId:record[0].data.id
							},
							success : function(form, action) {
								win1.hide(), 
								Ext.Msg.alert(alertTitle,'保存成功！');
										menuStoreReload();
							},
							failure : function(form, action) {
								if (action.faliureType == Ext.form.Action.SERVER_INVALID) {
									Ext.Msg.alert(alertTitle, "服务器连接异常，请联系管理员！");
								} else {
									if (action.result.msg) {
										win1.hide();
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
					form1.getForm().reset();
				}
			}, {
				text : "取消",
				handler : function() {
					win1.close();
				}
			}]
		});
		win1.on('hide', function() {
					form1.destroy();
				});
		win1.show();
	}
	function accept(record){
		var form2=new Ext.form.FormPanel({
			frame : true,
			border : false,
			bodyStyle : 'padding:5px 5px 5px',
			labelWidth : 90,
			fileUpload : true,
			width : 400,
			labelAlign : "right",
			items:[
				{
						xtype : 'textfield',
						inputType : 'file',
						width:220,
						fieldLabel:'实施附件',
						name:'upFile'
				}
			]
					
		});
		var win2 = new Ext.Window({
			title : operTitle,
			width : 400,
			closeAction : 'hide',
			plain : true,
			modal : true,
			items : form2,
			buttonAlign : "center",
			buttons : [{
				text : "保存",
				iconCls : 'save',
				handler : function() {
					if (form2.getForm().isValid()) {
						form2.getForm().submit({
							url : sysPath + "/cus/cusComplaintAction!accept.action",
							waitMsg : '正在保存数据...',
							params:{
								comId:record[0].data.id
							},
							success : function(form, action) {
								win2.hide(), 
								Ext.Msg.alert(alertTitle,
										action.result.msg);
										menuStoreReload();
							},
							failure : function(form, action) {
								if (action.faliureType == Ext.form.Action.SERVER_INVALID) {
									Ext.Msg.alert(alertTitle, "服务器连接异常，请联系管理员！");
								} else {
									if (action.result.msg) {
										win2.hide();
										Ext.Msg.alert(alertTitle,
												action.result.msg);
									}
								}
							}
						});
					}
				}
			}, {
				text : "取消",
				handler : function() {
					win2.close();
				}
			}]
		});
		win2.on('hide', function() {
					form2.destroy();
				});
		win2.show();
	}*/
	function menuStoreReload(){
		menuStore.reload({
			params:{
				start : 0,
				privilege:privilege,
				limit : pageSize
			}
		});
	}
    //end
});
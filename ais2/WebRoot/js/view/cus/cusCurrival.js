var privilege=191;
var operTitle;
var fields=[
			{name:'id'},
            {name: 'curName'},
            {name: 'cusId'},
            {name: 'cusName'},
            {name: 'choiceReason'},
            {name: 'curGoodness'},
            {name:'curBadness'},
            {name:'teamworkTime'},
            {name:'teamworkWeight'},
            {name:'keyLinkman'},
            {name:'curProjiect'},
            {name: 'createName'},
            {name: 'createTime'},
            {name: 'updateName'},
            {name: 'updateTime'},
            {name:'cusRecordId'},
            {name:'cpName'},
            {name:'status'},
            {name:'ts'}
];
Ext.onReady(function() {
    var menuStore = new Ext.data.Store({
        storeId:"menuStore",
        baseParams:{privilege:privilege,filter_EQL_cusRecordId:parent.cusRecordId,filter_EQL_status:1},
        proxy: new Ext.data.HttpProxy({url:sysPath+"/cus/cusCurrivalAction!ralaList.action"}),
        reader: new Ext.data.JsonReader({
            root: 'result', totalProperty: 'totalCount'
        }, fields)
    });
   // menuStore.load();
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
var cusRecordStore=new Ext.data.Store({
		storeId:'cusRecordStore',
		baseParams:{limit:pageSize},
		proxy:new Ext.data.HttpProxy({url:sysPath+"/cus/cusRecordAction!list.action"}),
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
        	{name:'id'},
        	{name:'cusName'}
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
            {header:'联系人ID',dataIndex:"cusRecordId",hidden: true, hideable: false},
            {header:'竞争对手名称',dataIndex:"curName",width:80},
            {header:"代理公司",dataIndex:"cpName",width:80},
            {header:"选择竞争对手原因",dataIndex:"choiceReason",width:160},
            {header:"竞争对手优势",dataIndex:"curGoodness",width:100},
            {header:"竞争对手劣势",dataIndex:"curBadness",width:100},
            {header:"合作时间",dataIndex:"teamworkTime",width:80},
            {header:"合作货量",dataIndex:"teamworkWeight",width:80},
            {header:"关键联系人",dataIndex:"keyLinkman",width:80},
            {header:"提供方案",dataIndex:"curProjiect",width:80,renderer:function(v){
            	if(v!=''){
            		return '<a href="'+exceptionImagesUrl+v+'" target="_blank">查看附件</a>';
            	}
            }},
            {header:"创建人",dataIndex:"createName",width:50,hidden: true},
            {header:"创建时间",dataIndex:"createTime",width:50,hidden: true},
            {header:"修改人",dataIndex:"updateName",width:50,hidden: true},
            {header:"修改时间",dataIndex:"updateTime",width:50,hidden: true}
           
        ]),
        store:menuStore,
        tbar: [
            {
                text:'<B>新增</B>', id:'addbtn',tooltip:'增加竞争对手信息', iconCls: 'userAdd',handler:function() {
                	if(parent.cusRecordId==null){
                		cusOper(null);
                	}else{
                		cusOpernoNull(null);
                	}
            } },
            '-',
            {
                text:'<B>修改</B>',id:'updatebtn',disabled:true, tooltip:'修改竞争对手信息', iconCls: 'userEdit', handler: function() {
                var _record = menuGrid.getSelectionModel().getSelections();
                if (_record.length < 1) {
						parent.Ext.Msg.alert("系统消息", "请选择您要操作的行！");
						return false;
					} else if (_record.length > 1) {
						parent.Ext.Msg.alert("系统消息", "一次只能修改一行！");
						return false;
					}else{
						if(parent.cusRecordId==null){
	                		cusOper(_record);
	                	}else{
	                		cusOpernoNull(_record);
	                	}
					}
            } } ,
            '-',
            {
                text:'<B>作废</B>',id:'deletebtn',disabled:true, tooltip:'作废竞争对手信息', iconCls: 'userDelete',
                handler: function(){
                	var _records = menuGrid.getSelectionModel().getSelections();
                	if (_records.length < 1) {
						parent.Ext.Msg.alert(alertTitle, "请选择您要操作的行！");
						return false;
					} 
					Ext.Msg.confirm(alertTitle, "确定要作废所选的"+_records.length+"行记录吗？", function(btnYes) {
						if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
								
								Ext.Ajax.request({
								url : sysPath+ "/cus/cusCurrivalAction!delete.action",
								params : {
									cId : _records[0].data.id,
									privilege:privilege
								},
								success : function(resp) {
								var respText = Ext.util.JSON.decode(resp.responseText);
								Ext.Msg.alert(alertTitle,'作废成功！');
								menuStoreReload();
										}
									});
								}
							});
					
                }
            },'-','&nbsp;&nbsp;',
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
            },
            '-','&nbsp;',
            {
            	xtype : 'combo',
            	triggerAction : 'all',
    			model : 'local',
    			hiddenId : 'checkItems',
    			hiddenName : 'checkItems',
    			name : 'checkItemstext',
            	store: [
            			['', '查询全部'], 
    					['LIKES_curName', '竞争对手名称'],
    					['LIKES_keyLinkman','关键联系人']
    				   ],
    			width : 100,
    			listeners : {
    						'select' : function(combo, record, index) {
    							if (combo.getValue() == '') {
    								Ext.getCmp("searchContent").disable();
    								Ext.getCmp("searchContent").show();
    								Ext.getCmp("searchContent").setValue("");
    							} else {
    								Ext.getCmp("searchContent").enable();
    								Ext.getCmp("searchContent").show();
    							}
    							Ext.getCmp("searchContent").focus(true, true);
    						}
    					}
            },'-','客商名称:',{
            	xtype:'combo',
				triggerAction :'all',
				model : 'local',
				id:'searcuscombo',
				minChars : 0,
				width:120,
				store:cusRecordStore,
				queryParam :'filter_LIKES_cusName',
				pageSize:pageSize,
				valueField :'id',
			    displayField :'cusName',
			    hiddenName : 'cusId',
				emptyText : '选择类型',
				forceSelection : true
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
    	var cusId=Ext.getCmp('searcuscombo').getValue();
    	if(parent.cusRecordId!=null){
    		menuStore.baseParams = {
				checkItems : Ext.get("checkItems").dom.value,
				privilege:privilege,
				filter_EQL_status:1,
				itemsValue : Ext.get("searchContent").dom.value,
				//filter_EQL_cusRecordId:cusId,
				filter_EQL_cusRecordId:parent.cusRecordId
			}
    	}else{
    		menuStore.baseParams = {
				checkItems : Ext.get("checkItems").dom.value,
				privilege:privilege,
				filter_EQL_status:1,
				itemsValue : Ext.get("searchContent").dom.value,
				filter_EQL_cusRecordId:cusId
			}
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
    function cusOper(_record) {
	var form = new Ext.form.FormPanel({
					frame : true,
					border : false,
					bodyStyle : 'padding:5px 5px 5px',
					labelWidth : 120,
					fileUpload : true,
					width : 450,
					labelAlign : "right",
					reader : new Ext.data.JsonReader({
			            root: 'result', totalProperty: 'totalCount'
			        }, fields),
					items:[
                           
						   {xtype:'hidden',name:'ts',id:'ts'},
						   {xtype:'hidden',name:'cusId',id:'cusId',value:parent.cusId},
						   {xtype:'hidden',name:'cusName',id:'cusName',value:parent.cusName},
						   {xtype:'hidden',name:'cusRecordId',id:'cusRecordId',value:parent.cusRecordId},
						   {xtype:'hidden',name:'departId',value:bussDepart},
						   {xtype:'hidden',id:'id',name:'id'},
                           {
                           		xtype : 'textfield',
                           		triggerAction : 'all',
    							id:'curName',
    							fieldLabel : '竞争对手名称<span style="color:red">*</span>',
    							triggerAction : 'all',
    							valueField : 'curName',
    							displayField :'curName',
    							name :'curName',
    							width:160,
    							allowBlank : false,
    							blankText:'竞争对手不能为空！',
    							maxLength:50,
								maxLengthText:'长度不能超过50个汉字'
                           },
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
						    			Ext.getCmp('cusName').setValue(Ext.get('cuscombo').dom.value);
						    			Ext.apply(linkManStore.baseParams,{
											filter_EQL_cusRecordId:combo.getValue()					
										});
						    			Ext.Ajax.request({
						    				url:sysPath+'/cus/cusRecordAction!list.action',
						    				params:{
						    					filter_EQL_id:combo.getValue()
						    				},
						    				success:function(resp){
												var respText= Ext.util.JSON.decode(resp.responseText);
												if(respText.result.length<1){
													parent.Ext.Msg.alert(alertTitle,'该代理不属于客户,请到客户列表进行维护！');
													return;
												}else{
													Ext.getCmp('cusId').setValue(respText.result[0].cusId);
													Ext.Ajax.request({
														url:sysPath+'/cus/cusLinkManAction!ralaList.action',
									    				params:{
									    					filter_EQL_cusRecordId:respText.result[0].id,
									    					privilege:152
									    				},success:function(resp1){
									    					var respText1= Ext.util.JSON.decode(resp1.responseText);
									    					if(respText1.result.length<1){
									    						parent.Ext.Msg.alert(alertTitle,'该代理对应的客户没有联系人,请到客户档案管理进行维护！');
																return;
									    					}else{
									    						Ext.getCmp('linkmancombo').setValue(respText1.result[0].name);
									    					}
									    				}
													});
												}
						    				}	
						    			});
						    		}
						    	}
                           },
                           {
                           		xtype:'numberfield',
                           		name:'teamworkWeight',
                           		fieldLabel:'合作预期货量(吨)',
                           		width:160,
                           		maxValue:99999.99,
								maxLength:8
                           },{
                           		xtype:'datefield',
                           		fieldLabel:'开始合作时间',
                           		format:'Y-m-d',
                           		name:'teamworkTime',
                           		width:160
                           },{
                           		xtype : 'combo',
    							typeAhead : true,
    							forceSelection : true,
    							width:160,
    							queryParam : 'filter_LIKES_name',
    							minChars : 0,
    							fieldLabel : '关键联系人<span style="color:red">*</span>',
    							store : linkManStore,
    							pageSize : pageSize,
    							triggerAction : 'all',
    							model:'remote',
    							id:'linkmancombo',
    							valueField : 'linkMan',
    							displayField : 'linkMan',
    							name : 'keyLinkman',
    							emptyText : "请选择",
    							allowBlank : false,
    							blankText : "关键联系人不能为空！"
                           		
                           },
                           {
								xtype : 'textfield',
								inputType : 'file',
								width:220,
								fieldLabel:'提供方案',
								name:'upFile'
							},{
                           		xtype:'textarea',
                           		name:'curGoodness',
                           		id:'curGoodness',
                           		width:220,
                           		height:60,
                           		fieldLabel:'竞争对手优势',
                           		maxLength:250,
								maxLengthText:'长度不能超过250个汉字'
                           },{
                           		xtype:'textarea',
                           		name:'curBadness',
                           		id:'curBadness',
                           		width:220,
                           		height:60,
                           		fieldLabel:'竞争对手劣势',
                           		maxLength:250,
								maxLengthText:'长度不能超过250个汉字'
                           },{
								xtype:'textarea',
								name:'choiceReason',
								width:60,
								width:220,
								maxLength:250,
								maxLengthText:'长度不能超过250个汉字',
								fieldLabel:'选择对手原因'
							}
                    ]
                    
				});
		operTitle="添加竞争对手信息";
		if(_record!=null){
			operTitle="修改竞争对手信息";
			Ext.getCmp("id").setValue(_record[0].data.id);
			form.load({
				url : sysPath+ "/cus/cusCurrivalAction!ralaList.action",
				params:{filter_EQL_id:_record[0].data.id,privilege:privilege,limit : pageSize},
				success:function(){
				}
			})
		}
		var win = new Ext.Window({
			title : operTitle,
			width : 460,
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
							url : sysPath + "/cus/cusCurrivalAction!save.action?privilege=191",
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
									menuStoreReload();
								} else {
									if (action.result.msg) {
										win.hide();
										Ext.Msg.alert(alertTitle,
												'保存失败!');
												menuStoreReload();
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
						carTitle='修改个性化要求信息';
						form.load({
						url : sysPath
								+ "/sys/cusCurrivalAction!ralaList.action",
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
			Ext.getCmp('cusRecordId').setDisabled(true);
			Ext.getCmp('cuscombo').setDisabled(false);
		}else{
			Ext.getCmp('cusRecordId').setDisabled(false);
			Ext.getCmp('cuscombo').setDisabled(true);
		}
	}
	function cusOpernoNull(_record) {
	var form = new top.Ext.form.FormPanel({
					frame : true,
					border : false,
					bodyStyle : 'padding:5px 5px 5px',
					labelWidth : 120,
					fileUpload : true,
					width : 450,
					labelAlign : "right",
					reader : new Ext.data.JsonReader({
			            root: 'result', totalProperty: 'totalCount'
			        }, fields),
					items:[
                           
						   {xtype:'hidden',name:'ts',id:'ts'},
						   {xtype:'hidden',name:'cusId',id:'cusId',value:parent.cusId},
						   {xtype:'hidden',name:'cusName',id:'cusName',value:parent.cusName},
						   {xtype:'hidden',name:'cusRecordId',id:'cusRecordId',value:parent.cusRecordId},
						   {xtype:'hidden',name:'departId',value:bussDepart},
						   {xtype:'hidden',id:'id',name:'id'},
                           {
                           		xtype : 'textfield',
                           		triggerAction : 'all',
    							id:'curName',
    							fieldLabel : '竞争对手名称<span style="color:red">*</span>',
    							triggerAction : 'all',
    							valueField : 'curName',
    							displayField :'curName',
    							name :'curName',
    							width:160,
    							allowBlank : false,
    							blankText:'竞争对手不能为空！',
    							maxLength:50,
								maxLengthText:'长度不能超过50个汉字'
                           },
                           {
                           		xtype:'combo',
								fieldLabel:'客户名称<span style="color:red">*</span>',
								triggerAction :'all',
								model : 'local',
						    	id:'cuscombo',
						    	minChars : 0,
						    	store:cusRecordStore,
						    	queryParam :'filter_LIKES_cusName',
						    	pageSize:pageSize,
						    	valueField :'id',
			    				displayField :'cusName',
			    				hiddenName : 'cusRecordId',
								emptyText : '选择类型',
						    	forceSelection : true,
						    	blankText : "客商名称不能为空!",
						    	allowBlank : false,
						    	listeners:{
						    		'select':function(combo){
						    			top.Ext.getCmp('cusName').setValue(Ext.get('cuscombo').dom.value);
						    			Ext.Ajax.request({
						    				url:sysPath+'/cus/cusRecordAction!list.action',
						    				params:{
						    					filter_EQL_cusId:combo.getValue()
						    				},
						    				success:function(resp){
												var respText= Ext.util.JSON.decode(resp.responseText);
												if(respText.result.length<1){
													top.Ext.Msg.alert(alertTitle,'该代理不属于客户,请到客户列表进行维护！');
													return;
												}else{
													Ext.getCmp('cusId').setValue(respText.result[0].cusId);
													Ext.Ajax.request({
														url:sysPath+'/cus/cusLinkManAction!ralaList.action',
									    				params:{
									    					filter_EQL_cusRecordId:respText.result[0].id,
									    					privilege:152
									    				},success:function(resp1){
									    					var respText1= Ext.util.JSON.decode(resp1.responseText);
									    					if(respText1.result.length<1){
									    						top.Ext.Msg.alert(alertTitle,'该代理对应的客户没有联系人,请到客户档案管理进行维护！');
																return;
									    					}else{
									    						top.Ext.getCmp('keyLinkman').setValue(respText1.result[0].name);
									    					}
									    				}
													});
												}
						    				}	
						    			});
						    		}
						    	}
                           },
                           {
                           		xtype:'numberfield',
                           		name:'teamworkWeight',
                           		fieldLabel:'合作预期货量(吨)',
                           		width:160,
                           		maxValue:99999.99,
								maxLength:8
                           },{
                           		xtype:'datefield',
                           		fieldLabel:'开始合作时间',
                           		format:'Y-m-d',
                           		name:'teamworkTime',
                           		width:160
                           },{
                           		xtype : 'combo',
    							typeAhead : true,
    							forceSelection : true,
    							width:160,
    							queryParam : 'filter_LIKES_name',
    							minChars : 0,
    							fieldLabel : '关键联系人<span style="color:red">*</span>',
    							store : linkManStore,
    							pageSize : pageSize,
    							triggerAction : 'all',
    							model:'remote',
    							id:'linkmancombo',
    							valueField : 'linkMan',
    							displayField : 'linkMan',
    							name : 'keyLinkman',
    							emptyText : "请选择",
    							allowBlank : false,
    							blankText : "关键联系人不能为空！"
                           		
                           },
                           {
								xtype : 'textfield',
								inputType : 'file',
								width:220,
								fieldLabel:'提供方案',
								name:'upFile'
							},{
                           		xtype:'textarea',
                           		name:'curGoodness',
                           		id:'curGoodness',
                           		width:220,
                           		height:60,
                           		fieldLabel:'竞争对手优势',
                           		maxLength:250,
								maxLengthText:'长度不能超过250个汉字'
                           },{
                           		xtype:'textarea',
                           		name:'curBadness',
                           		id:'curBadness',
                           		width:220,
                           		height:60,
                           		fieldLabel:'竞争对手劣势',
                           		maxLength:250,
								maxLengthText:'长度不能超过250个汉字'
                           },{
								xtype:'textarea',
								name:'choiceReason',
								width:60,
								width:220,
								maxLength:250,
								maxLengthText:'长度不能超过250个汉字',
								fieldLabel:'选择对手原因'
							}
                    ]
                    
				});
		operTitle="添加竞争对手信息";
		if(_record!=null){
			operTitle="修改竞争对手信息";
			top.Ext.getCmp("id").setValue(_record[0].data.id);
			form.load({
				url : sysPath+ "/cus/cusCurrivalAction!ralaList.action",
				params:{filter_EQL_id:_record[0].data.id,privilege:privilege,limit : pageSize},
				success:function(){
				}
			})
		}
		var win = new top.Ext.Window({
			title : operTitle,
			width : 460,
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
							url : sysPath + "/cus/cusCurrivalAction!save.action?privilege=191",
							waitMsg : '正在保存数据...',
							success : function(form, action) {
								//alert(action.);
								win.hide(), 
								top.Ext.Msg.alert(alertTitle,
										action.result.msg);
										menuStoreReload();
							},
							failure : function(form, action) {
								if (action.faliureType == Ext.form.Action.SERVER_INVALID) {
									top.Ext.Msg.alert(alertTitle, "服务器连接异常，请联系管理员！");
									menuStoreReload();
								} else {
									if (action.result.msg) {
										win.hide();
										top.Ext.Msg.alert(alertTitle,
												'保存失败!');
												menuStoreReload();
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
						carTitle='修改个性化要求信息';
						form.load({
						url : sysPath
								+ "/sys/cusCurrivalAction!ralaList.action",
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
			top.Ext.getCmp('cuscombo').setDisabled(false);
		}else{
			top.Ext.getCmp('cuscombo').setValue(parent.cusRecordId);
			top.Ext.getCmp('cuscombo').setRawValue(parent.cusName);
			top.Ext.getCmp('cuscombo').setDisabled(true);
			top.Ext.getCmp('cusRecordId').setDisabled(false);
			Ext.apply(linkManStore.baseParams,{
				filter_EQL_cusRecordId:parent.cusRecordId					
			});
		}
	}
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
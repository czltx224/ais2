var privilege=152;
var operTitle;
var fields=[
			{name:'id'},
            {name: 'demandType'},
            {name: 'demandContext'},
            {name: 'demandMan'},
            {name: 'isAccept'},
            {name: 'filePath'},
            {name:'createName'},
            {name:'createTime'},
            {name: 'updateName'},
            {name: 'updateTime'},
            {name:'cusRecordId'},
            {name:'status'},
            {name:'ts'}
];
Ext.onReady(function() {
    var menuStore = new Ext.data.Store({
        storeId:"menuStore",
        baseParams:{privilege:privilege,filter_EQL_cusRecordId:parent.cusRecordId,filter_EQL_departId:bussDepart,filter_EQL_status:1},
        proxy: new Ext.data.HttpProxy({url:sysPath+"/cus/cusDemandAction!list.action"}),
        reader: new Ext.data.JsonReader({
            root: 'result', totalProperty: 'totalCount'
        }, fields)
    });
    menuStore.load();
    var linkManStore = new Ext.data.Store({
        storeId:"menuStore",
        baseParams:{privilege:152,filter_EQL_cusRecordId:parent.cusRecordId},
        proxy: new Ext.data.HttpProxy({url:sysPath+"/cus/cusLinkManAction!ralaList.action"}),
        reader: new Ext.data.JsonReader({
            root: 'result', totalProperty: 'totalCount'
        }, [
        {name:'id'},
        {name:'linkMan',mapping:'name'}
        ])
    });
//要求类型Store
	var requestTypeStore=new Ext.data.Store({
		storeId:"requestTypeStore",
		baseParams:{filter_EQL_basDictionaryId:6,privilege:16},
		proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/dictionaryAction!ralaList.action"}),
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
        	{name:'id'},
        	{name:'requestType',mapping:'typeName'}
        	])
	});
    //所在企业职责Store
var dutyStore=new Ext.data.Store({
		storeId:'dutyStore',
		baseParams:{filter_EQL_basDictionaryId:44,privilege:16},
		proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/dictionaryAction!ralaList.action"}),
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
        	{name:'id'},
        	{name:'duty',mapping:'typeName'}
        	])
});
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
            {header:'需求类型',dataIndex:"demandType",width:80},
            {header:"需求内容",dataIndex:"demandContext",width:80},
            {header:"需求对接人",dataIndex:"demandMan",width:80},
            {header:"是否采纳",dataIndex:"isAccept",width:80,renderer:function(v){
            	if(v==1){
            		return '是';
            	}else if(v==0){
            		return '否';
            	}
            }},
            {header:"采纳方案(附件)",dataIndex:"filePath",width:80,renderer:function(v){
            	if(v!=''){
            		return '<a href="'+exceptionImagesUrl+v+'" target="_blank">查看附件</a>';
            	}
            }},
            {header:"需求采集人",dataIndex:"createName",width:80},
            {header:"需求采集时间",dataIndex:"createTime",width:80},
            {header:"修改人",dataIndex:"updateName",width:50,hidden: true},
            {header:"修改时间",dataIndex:"updateTime",width:50,hidden: true}
           
        ]),
        store:menuStore,
        tbar: [
            {
                text:'<B>新增客户需求</B>', id:'addbtn',tooltip:'增加客户需求', iconCls: 'userAdd',handler:function() {
                	if(parent.cusRecordId==null){
                		cusOpr(null);
                	}else{
                		cusOpernoNull(null);
                	}
            } },
            '-',
            {
                text:'<B>修改客户需求</B>',id:'updatebtn',disabled:true, tooltip:'修改客户需求信息', iconCls: 'userEdit', handler: function() {
                var _record = menuGrid.getSelectionModel().getSelections();
                if (_record.length < 1) {
						parent.Ext.Msg.alert("系统消息", "请选择您要操作的行！");
						return false;
					} else if (_record.length > 1) {
						parent.Ext.Msg.alert("系统消息", "一次只能修改一行！");
						return false;
					}else{
						if(parent.cusRecordId==null){
	                		cusOpr(_record);
	                	}else{
	                		cusOpernoNull(_record);
	                	}
					}
            } } ,
            '-',
            {
                text:'<B>作废</B>',id:'deletebtn',disabled:true, tooltip:'作废客户需求信息', iconCls: 'userDelete',
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
								url : sysPath+ "/cus/cusDemandAction!delete.action",
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
    					['LIKES_demandType', '需求类型']
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
        })
    });
    function searchCusRequest() {
		menuStore.baseParams = {
			checkItems : Ext.get("checkItems").dom.value,
			privilege:privilege,
			filter_EQL_cusRecordId:parent.cusRecordId,
			filter_EQL_departId:bussDepart,
			filter_EQL_status:1,
			itemsValue : Ext.get("searchContent").dom.value
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
    });
    function cusOper(_record) {
	var form = new Ext.form.FormPanel({
					frame : true,
					border : false,
					bodyStyle : 'padding:5px 5px 5px',
					labelWidth : 90,
					fileUpload : true,
					width : 450,
					labelAlign : "right",
					reader : new Ext.data.JsonReader({
			            root: 'result', totalProperty: 'totalCount'
			        }, fields),
					items:[
                           
						   {xtype:'hidden',name:'ts',id:'ts'},
						   {xtype:'hidden',name:'departId',value:bussDepart},
						   {xtype:'hidden',name:'cusRecordId',id:'cusRecordId',value:parent.cusRecordId},
						   {xtype:'hidden',id:'id',name:'id'},
                           {
                           		xtype : 'combo',
                           		triggerAction : 'all',
    							id:'comboDemandType',
    							fieldLabel : '需求类型<span style="color:red">*</span>',
    							store : requestTypeStore,
    							triggerAction : 'all',
    							valueField : 'requestType',
    							displayField :'requestType',
    							hiddenName :'demandType',
    							emptyText : "请选择",
    							width:200,
    							allowBlank : false,
    							blankText:'需求类型不能为空！'
                           },
                           {
                           		xtype:'textarea',
                           		fieldLabel:'需求内容',
                           		height:65,
                           		maxLength:1000,
								maxLengthText:'长度不能超过1000个汉字',
                           		id:'demandContext',
                           		name:'demandContext',
                           		width:200
                           },
                           {
                           		xtype : 'combo',
    							typeAhead : true,
    							forceSelection : true,
    							queryParam : 'filter_LIKES_name',
    							minChars : 0,
    							fieldLabel : '需求对接人<span style="color:red">*</span>',
    							store : linkManStore,
    							pageSize : pageSize,
    							triggerAction : 'all',
    							valueField : 'linkMan',
    							id:'linkmancombo',
    							width:200,
    							displayField : 'linkMan',
    							hiddenName : 'demandMan',
    							emptyText : "请选择",
    							allowBlank : false,
    							blankText : "需求对接人不能为空！"
                           },
                           {
                           		xtype:'radiogroup',
							    id:'radioisAccept',
							    fieldLabel:'是否采纳',
							    items: [{
									inputValue:'1',
									boxLabel: '是',
									name:'isAccept',
									checked:_record==null?true:(_record[0].data.isAccept=="1"?true:false)
								}, {
									inputValue:'0',
									name:'isAccept',
									boxLabel: '否',
									checked:_record==null?false:(_record[0].data.isAccept=="0"?true:false)
								}]
                           },
                           {
								xtype : 'textfield',
								inputType : 'file',
								width:220,
								fieldLabel:'实施附件',
								name:'upFile'
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
								id:'cuscombo',
								minChars:0,
								emptyText:'请选择',
								fieldLabel:'客户名称<span style="color:red">*</span>',
								hiddenName:'cusRecordId',
								width:200,
								allowBlank:false,
								blankText:'客户名称不能为空！',
								listeners:{
									'select':function(combo){
										Ext.getCmp('linkmancombo').clearValue();
										Ext.apply(linkManStore.baseParams,{
											filter_EQL_cusRecordId:combo.getValue()
										});
										Ext.getCmp('linkmancombo').store.load();
									}						                            	
	                        	}
							}
                    ]
                    
				});
		operTitle="添加客户需求信息";
		if(_record!=null){
			operTitle="修改客户需求信息";
			Ext.getCmp("id").setValue(_record[0].data.id);
			form.load({
				url : sysPath+ "/cus/cusDemandAction!list.action",
				params:{filter_EQL_id:_record[0].data.id,privilege:privilege,limit : pageSize},
				success:function(){
				}
			})
		}
		var win = new Ext.Window({
			title : operTitle,
			width : 450,
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
							url : sysPath + "/cus/cusDemandAction!save.action?privilege=153",
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
						carTitle='修改客户需求信息';
						form.load({
						url : sysPath
								+ "/sys/cusDemandAction!list.action",
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
		}else{
			Ext.getCmp('cuscombo').setDisabled(true);
		}
	}
	function cusOpernoNull(_record) {
	var form = new top.Ext.form.FormPanel({
					frame : true,
					border : false,
					bodyStyle : 'padding:5px 5px 5px',
					labelWidth : 90,
					fileUpload : true,
					width : 450,
					labelAlign : "right",
					reader : new Ext.data.JsonReader({
			            root: 'result', totalProperty: 'totalCount'
			        }, fields),
					items:[
                           
						   {xtype:'hidden',name:'ts',id:'ts'},
						   {xtype:'hidden',name:'departId',value:bussDepart},
						   {xtype:'hidden',name:'cusRecordId',id:'cusRecordId',value:parent.cusRecordId},
						   {xtype:'hidden',id:'id',name:'id'},
                           {
                           		xtype : 'combo',
                           		triggerAction : 'all',
    							id:'comboDemandType',
    							fieldLabel : '需求类型<span style="color:red">*</span>',
    							store : requestTypeStore,
    							triggerAction : 'all',
    							valueField : 'requestType',
    							displayField :'requestType',
    							hiddenName :'demandType',
    							emptyText : "请选择",
    							width:200,
    							allowBlank : false,
    							blankText:'需求类型不能为空！'
                           },
                           {
                           		xtype:'textarea',
                           		fieldLabel:'需求内容',
                           		height:65,
                           		maxLength:1000,
								maxLengthText:'长度不能超过1000个汉字',
                           		id:'demandContext',
                           		name:'demandContext',
                           		width:200
                           },
                           {
                           		xtype : 'combo',
    							typeAhead : true,
    							forceSelection : true,
    							queryParam : 'filter_LIKES_name',
    							minChars : 0,
    							fieldLabel : '需求对接人<span style="color:red">*</span>',
    							store : linkManStore,
    							pageSize : pageSize,
    							triggerAction : 'all',
    							valueField : 'linkMan',
    							id:'linkmancombo',
    							width:200,
    							displayField : 'linkMan',
    							hiddenName : 'demandMan',
    							emptyText : "请选择",
    							allowBlank : false,
    							blankText : "需求对接人不能为空！"
                           },
                           {
                           		xtype:'radiogroup',
							    id:'radioisAccept',
							    fieldLabel:'是否采纳',
							    items: [{
									inputValue:'1',
									boxLabel: '是',
									name:'isAccept',
									checked:_record==null?true:(_record[0].data.isAccept=="1"?true:false)
								}, {
									inputValue:'0',
									name:'isAccept',
									boxLabel: '否',
									checked:_record==null?false:(_record[0].data.isAccept=="0"?true:false)
								}]
                           },
                           {
								xtype : 'textfield',
								inputType : 'file',
								width:220,
								fieldLabel:'实施附件',
								name:'upFile'
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
								id:'cuscombo',
								minChars:0,
								emptyText:'请选择',
								fieldLabel:'客户名称<span style="color:red">*</span>',
								hiddenName:'cusRecordId',
								width:200,
								allowBlank:false,
								blankText:'客户名称不能为空！',
								listeners:{
									'select':function(combo){
										top.Ext.getCmp('linkmancombo').clearValue();
										Ext.apply(linkManStore.baseParams,{
											filter_EQL_cusRecordId:combo.getValue()
										});
										top.Ext.getCmp('linkmancombo').store.load();
									}						                            	
	                        	}
							}
                    ]
                    
				});
		operTitle="添加客户需求信息";
		if(_record!=null){
			operTitle="修改客户需求信息";
			top.Ext.getCmp("id").setValue(_record[0].data.id);
			form.load({
				url : sysPath+ "/cus/cusDemandAction!list.action",
				params:{filter_EQL_id:_record[0].data.id,privilege:privilege,limit : pageSize},
				success:function(){
				}
			})
		}
		var win = new top.Ext.Window({
			title : operTitle,
			width : 450,
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
							url : sysPath + "/cus/cusDemandAction!save.action?privilege=153",
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
						carTitle='修改客户需求信息';
						form.load({
						url : sysPath
								+ "/sys/cusDemandAction!list.action",
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
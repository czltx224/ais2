var privilege=152;
var operTitle;
var fields=[
			{name:'id'},
			{name: 'cusName'},
            {name: 'name'},
            {name: 'tel'},
            {name: 'sex'},
            {name: 'duty'},
            {name: 'birthday'},
            {name: 'isPivot'},
            {name: 'hobbiesInterests'},
            {name: 'linkman'},
            {name: 'relation'},
            {name:'lastVisistTime'},
            {name:'createName'},
            {name:'createTime'},
            {name: 'updateName'},
            {name: 'updateTime'},
            {name:'cusRecordId'},
            {name:'status'},
            {name:'phone'},
            {name:'ts'}
];
Ext.onReady(function() {
    var menuStore = new Ext.data.Store({
        storeId:"menuStore",
        baseParams:{privilege:privilege,filter_EQL_cusRecordId:parent.cusRecordId,filter_EQL_status:1},
        proxy: new Ext.data.HttpProxy({url:sysPath+"/cus/cusLinkManAction!ralaList.action"}),
        reader: new Ext.data.JsonReader({
            root: 'result', totalProperty: 'totalCount'
        }, fields)
    });
   // menuStore.load();
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
            {header:'客户名称',dataIndex:"cusName",width:80},
            {header:'姓名',dataIndex:"name",width:80},
            {header:"电话",dataIndex:"tel",width:80},
            {header:"手机",dataIndex:"phone",width:80},
            {header:"性别",dataIndex:"sex",width:80,renderer:function(v){
            	if(v==1){
            		return '男';
            	}else{
            		return '女';
            	}
            }},
            {header:"所在企业职责",dataIndex:"duty",width:80},
            {header:"是否是关键人物",dataIndex:"isPivot",width:80,renderer:function(v){
            	if(v==1){
            		return '是';
            	}else if(v==0){
            		return '否';
            	}
            }},
            {header:"生日",dataIndex:"birthday",width:80},
            {header:"兴趣爱好",dataIndex:"hobbiesInterests",width:80},
            {header:"关联联系人",dataIndex:"linkman",width:80},
            {header:"关系",dataIndex:"relation",width:80},
            {header:"最近拜访时间",dataIndex:"lastVisistTime",width:80},
            {header:"创建人",dataIndex:"crateName",width:50,hidden: true},
            {header:"创建时间",dataIndex:"createTime",width:50,hidden: true},
            {header:"修改人",dataIndex:"updateName",width:50,hidden: true},
            {header:"修改时间",dataIndex:"updateTime",width:50,hidden: true}
           
        ]),
        store:menuStore,
        tbar: [
            {
                text:'<B>新增联系人</B>', id:'addbtn',tooltip:'增加联系人信息', iconCls: 'userAdd',handler:function() {
                	if(parent.cusRecordId==null){
                		cusOper(null);
                	}else{
                		cusOprnoNull(null);
                	}
            } },
            '-',
            {
                text:'<B>修改联系人</B>',id:'updatebtn',disabled:true, tooltip:'修改联系人信息', iconCls: 'userEdit', handler: function() {
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
	                		cusOprnoNull(_record);
	                	}
					}
            } } ,/*
            '-',
            {
                text:'<B>作废</B>',id:'deletebtn',disabled:true, tooltip:'作废联系人信息', iconCls: 'userDelete',
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
								url : sysPath+ "/cus/cusLinkManAction!delete.action",
								params : {
									ids : ids,
									privilege:privilege
								},
								success : function(resp) {
								var respText = Ext.util.JSON.decode(resp.responseText);
								Ext.Msg.alert(alertTitle,"作废成功<br/>");
								menuStoreReload();
										}
									});
								}
							});
					
                }
            },'-','&nbsp;&nbsp;',*/
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
    					['LIKES_name', '姓名'],
    					['LIKES_tel','电话']
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
   menuGrid.render();
   function searchCusRequest() {	
		menuStore.baseParams = {
			checkItems : Ext.get("checkItems").dom.value,
			privilege:privilege,
			filter_EQL_cusRecordId:parent.cusRecordId,
			filter_EQL_status:1,
			itemsValue : Ext.get("searchContent").dom.value
		}
		var editbtn = Ext.getCmp('updatebtn');
		//var deletebtn = Ext.getCmp('deletebtn');

		editbtn.setDisabled(true);
		//deletebtn.setDisabled(true);
		menuStoreReload();
		
	}
    menuGrid.on('click', function() {
        var _record = menuGrid.getSelectionModel().getSelections();
        var updatebtn = Ext.getCmp('updatebtn');
        //var deletebtn = Ext.getCmp('deletebtn');
        if (_record.length==1) {       	
            if(updatebtn){
            	updatebtn.setDisabled(false);
            }
            /*if(deletebtn){
            	deletebtn.setDisabled(false);
            }*/
        }else if(_record.length>1){
        	 if(updatebtn){
            	updatebtn.setDisabled(true);
            }
           /* if(deletebtn){
            	deletebtn.setDisabled(false);
            }*/
        } else {
            if(updatebtn){
            	updatebtn.setDisabled(true);
            }
			/*if(deletebtn){
            	deletebtn.setDisabled(true);
			}*/
        }
    });
    function cusOper(_record) {
	var form = new Ext.form.FormPanel({
					frame : true,
					border : false,
					bodyStyle : 'padding:5px 5px 5px',
					labelWidth : 90,
					width : 600,
					labelAlign : "right",
					reader : new Ext.data.JsonReader({
			            root: 'result', totalProperty: 'totalCount'
			        }, fields),
					items:[
                           {
					        layout : 'column',
					        items:[{
					        		layout:'form',
					        		columnWidth :.5,
					        		items:[
					        				{xtype:'hidden',name:'ts',id:'ts'},
						   					{xtype:'hidden',name:'id',id:'id'},
						   					{xtype:'hidden',name:'departId',value:bussDepart},
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
				                            	blankText:'客户名称不能为空！'
								   			},
								          	{xtype:'textfield',fieldLabel:'姓名<span style="color:red">*</span>',name:'name',allowBlank:false,blankText:'姓名不能为空！',maxLength:100,maxLengthText:'长度不能大于100个汉字',width:160},
								          	{xtype:'textfield',fieldLabel:'电话<span style="color:red">*</span>',name:'tel',allowBlank:false,blankText:'电话不能为空！',maxLength:100,maxLengthText:'长度不能大于100个字符',width:160},
				                            {
				                            	xtype:'combo',
				                            	store:dutyStore,
				                            	displayField:'duty',
				                            	valueField:'duty',
				                            	triggerAction : 'all',
				                            	model:'local',
				                            	emptyText:'请选择',
				                            	fieldLabel:'所在企业职责<span style="color:red">*</span>',
				                            	name:'duty',
				                            	width:160,
				                            	allowBlank:false,
				                            	blankText:'所在企业职责不能为空！'
				                            },
								          	{
								          		xtype:'textfield',
								          		fieldLabel:'关联联系人',
								          		width:160,
								          		maxLength:100,
								          		maxLengthText:'长度不能大于100个汉字',
								          		name:'linkman'
								          	},	
								   			{xtype:'textfield',fieldLabel:'兴趣爱好',name:'hobbiesInterests',maxLength:100,maxLengthText:'长度不能大于100个汉字',width:160}
		                           	]
				                    },{
				                    layout:'form',
				                    columnWidth :.5,
				                    items:[
				                           {
				                           		xtype:'radiogroup',
					                           	id:'radiosex',
					                           	fieldLabel:'性别<span style="color:red">*</span>',
					                           	items: [{
								                    inputValue:'1',
								                    boxLabel: '男',
								                    name:'sex',
								                    checked:_record==null?true:(_record[0].data.sex=="1"?true:false)
								                }, {
								                    inputValue:'0',
								                    name:'sex',
								                    boxLabel: '女',
								                    checked:_record==null?false:(_record[0].data.sex=="0"?true:false)
								                }]
				                           	},
				                           {xtype:'datefield',fieldLabel:'生日',name:'birthday',id:'birthday',format:'Y-m-d',width:160},
				                           {xtype:'textfield',fieldLabel:'手机',name:'phone',id:'phone',width:160},
				                           {
				                           		xtype:'radiogroup',
					                           	id:'radioisPivot',
					                           	fieldLabel:'是否关键人物<span style="color:red">*</span>',
					                           	items: [{
								                    inputValue:'1',
								                    boxLabel: '是',
								                    name:'isPivot',
								                    checked:_record==null?false:(_record[0].data.isPivot=="1"?true:false)
								                }, {
								                    inputValue:'0',
								                    name:'isPivot',
								                    boxLabel: '否',
								                    checked:_record==null?true:(_record[0].data.isPivot=="0"?true:false)
								                }]
				                           	},
				                           {xtype:'textfield',fieldLabel:'关系',name:'relation',maxLength:10,maxLengthText:'长度不能大于10个汉字！',width:160},
				                           {xtype:'datefield',fieldLabel:'最近拜访时间<span style="color:red">*</span>',name:'lastVisistTime',allowBlank:false,blankText:'最近拜访时间不能为空！',format:'Y-m-d',width:160}

				                    ]
				            }]
                    }
                    ]
                    
				});
		operTitle="添加联系人信息";
		if(_record!=null){
			operTitle="修改联系人信息";
			Ext.getCmp("id").setValue(_record[0].data.id);
			form.load({
				url : sysPath+ "/cus/cusLinkManAction!ralaList.action",
				params:{filter_EQL_id:_record[0].data.id,privilege:privilege,limit : pageSize},
				success:function(){
					Ext.getCmp('cuscombo').setValue(_record[0].data.cusRecordId);
					Ext.getCmp('cuscombo').setRawValue(_record[0].data.cusName);
				}
			})
		}
		var win = new Ext.Window({
			title : operTitle,
			width : 600,
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
							url : sysPath + "/cus/cusLinkManAction!save.action?privilege=152",
							waitMsg : '正在保存数据...',
							success : function(form, action) {
								win.hide(), 
								Ext.Msg.alert(alertTitle,
										'保存成功');
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
						carTitle='修改联系人信息';
						form.load({
						url : sysPath
								+ "/sys/cusRequestAction!ralaList.action",
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
			//Ext.getCmp('cuscombo').show();
		}else{
			Ext.getCmp('cuscombo').setRawValue(parent.cusName);
			Ext.getCmp('cuscombo').setDisabled(true);
		}
	}
	 function cusOprnoNull(_record) {
	var form = new top.Ext.form.FormPanel({
					frame : true,
					border : false,
					bodyStyle : 'padding:5px 5px 5px',
					labelWidth : 90,
					width : 600,
					labelAlign : "right",
					reader : new Ext.data.JsonReader({
			            root: 'result', totalProperty: 'totalCount'
			        }, fields),
					items:[
                           {
					        layout : 'column',
					        items:[{
					        		layout:'form',
					        		columnWidth :.5,
					        		items:[
					        				{xtype:'hidden',name:'ts',id:'ts'},
						   					{xtype:'hidden',name:'id',id:'id'},
						   					{xtype:'hidden',name:'departId',value:bussDepart},
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
				                            	id:'cuscombo',
				                            	minChars:0,
				                            	emptyText:'请选择',
				                            	fieldLabel:'客户名称<span style="color:red">*</span>',
				                            	hiddenName:'cusRecordId',
				                            	width:160,
				                            	allowBlank:false,
				                            	blankText:'客户名称不能为空！'
								   			},
								          	{xtype:'textfield',fieldLabel:'姓名<span style="color:red">*</span>',name:'name',allowBlank:false,blankText:'姓名不能为空！',maxLength:100,maxLengthText:'长度不能大于100个汉字',width:160},
								          	{xtype:'textfield',fieldLabel:'电话<span style="color:red">*</span>',name:'tel',allowBlank:false,blankText:'电话不能为空！',maxLength:100,maxLengthText:'长度不能大于100个字符',width:160},
				                            {
				                            	xtype:'combo',
				                            	store:dutyStore,
				                            	displayField:'duty',
				                            	valueField:'duty',
				                            	triggerAction : 'all',
				                            	model:'local',
				                            	emptyText:'请选择',
				                            	fieldLabel:'所在企业职责<span style="color:red">*</span>',
				                            	name:'duty',
				                            	width:160,
				                            	allowBlank:false,
				                            	blankText:'所在企业职责不能为空！'
				                            },
								          	{
								          		xtype:'textfield',
								          		fieldLabel:'关联联系人',
								          		width:160,
								          		maxLength:100,
								          		maxLengthText:'长度不能大于100个汉字',
								          		name:'linkman'
								          	},	
								   			{xtype:'textfield',fieldLabel:'兴趣爱好',name:'hobbiesInterests',maxLength:100,maxLengthText:'长度不能大于100个汉字',width:160}
		                           	]
				                    },{
				                    layout:'form',
				                    columnWidth :.5,
				                    items:[
				                           {
				                           		xtype:'radiogroup',
					                           	id:'radiosex',
					                           	fieldLabel:'性别<span style="color:red">*</span>',
					                           	items: [{
								                    inputValue:'1',
								                    boxLabel: '男',
								                    name:'sex',
								                    checked:_record==null?true:(_record[0].data.sex=="1"?true:false)
								                }, {
								                    inputValue:'0',
								                    name:'sex',
								                    boxLabel: '女',
								                    checked:_record==null?false:(_record[0].data.sex=="0"?true:false)
								                }]
				                           	},
				                           {xtype:'datefield',fieldLabel:'生日',name:'birthday',id:'birthday',format:'Y-m-d',width:160},
				                           {xtype:'textfield',fieldLabel:'手机',name:'phone',id:'phone',width:160},
				                           {
				                           		xtype:'radiogroup',
					                           	id:'radioisPivot',
					                           	fieldLabel:'是否关键人物<span style="color:red">*</span>',
					                           	items: [{
								                    inputValue:'1',
								                    boxLabel: '是',
								                    name:'isPivot',
								                    checked:_record==null?false:(_record[0].data.isPivot=="1"?true:false)
								                }, {
								                    inputValue:'0',
								                    name:'isPivot',
								                    boxLabel: '否',
								                    checked:_record==null?true:(_record[0].data.isPivot=="0"?true:false)
								                }]
				                           	},
				                           {xtype:'textfield',fieldLabel:'关系',name:'relation',maxLength:10,maxLengthText:'长度不能大于10个汉字！',width:160},
				                           {xtype:'datefield',fieldLabel:'最近拜访时间<span style="color:red">*</span>',name:'lastVisistTime',allowBlank:false,blankText:'最近拜访时间不能为空！',format:'Y-m-d',width:160}

				                    ]
				            }]
                    }
                    ]
                    
				});
		operTitle="添加联系人信息";
		if(_record!=null){
			operTitle="修改联系人信息";
			top.Ext.getCmp("id").setValue(_record[0].data.id);
			form.load({
				url : sysPath+ "/cus/cusLinkManAction!ralaList.action",
				params:{filter_EQL_id:_record[0].data.id,privilege:privilege,limit : pageSize},
				success:function(){
					top.Ext.getCmp('cuscombo').setValue(_record[0].data.cusRecordId);
					top.Ext.getCmp('cuscombo').setRawValue(_record[0].data.cusName);
				}
			})
		}
		var win = new top.Ext.Window({
			title : operTitle,
			width : 600,
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
							url : sysPath + "/cus/cusLinkManAction!save.action?privilege=152",
							waitMsg : '正在保存数据...',
							success : function(form, action) {
								win.hide(), 
								top.Ext.Msg.alert(alertTitle,
										'保存成功！');
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
						carTitle='修改联系人信息';
						form.load({
						url : sysPath
								+ "/sys/cusRequestAction!ralaList.action",
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
			//Ext.getCmp('cuscombo').show();
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
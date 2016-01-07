var popupWin;
var privilege=56;
var carTitle;
Ext.onReady(function() {
	//要求阶段Store
	var requestStageStore=new Ext.data.Store({
		storeId:"requestStageStore",
		proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/dictionaryAction!ralaList.action"}),
		baseParams:{filter_EQL_basDictionaryId:5,privilege:16},
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
        	{name:'id'},
        	{name:'requestStage',mapping:'typeName'}
        	])
	});
	requestStageStore.load();
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
	requestTypeStore.load();
	
	//客户信息Store
	var cpStore=new Ext.data.Store({
		storeId:"cpStore",
		method:'post',
		baseParams:{filter_EQS_custprop:"发货代理",privilege:61,limit:pageSize},
		proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/customerAction!ralaList.action"}),
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
        	{name:'cpId',mapping:'id'},
        	{name:'cpName',mapping:'cusName'}
        	])
	});
	//cpStore.load();
	
	
    var menuStore = new Ext.data.Store({
        storeId:"menuStore",
        baseParams:{privilege:privilege},
        proxy: new Ext.data.HttpProxy({url:sysPath+"/sys/cusRequestAction!ralaList.action"}),
        reader: new Ext.data.JsonReader({
            root: 'result', totalProperty: 'totalCount'
        }, [
            {name:'id'},
            {name: 'cpName'},
            {name: 'cpId'},
            {name: 'cusName'},
            {name: 'cusTel'},
            {name: 'requestStage'},
            {name: 'requestType'},
            {name: 'request'},
            {name: 'createName'},
            {name: 'createTime'},
            {name: 'updateName'},
            {name: 'updateTime'},
            {name:'ts'}
        ])
    });
    //start
    var sm = new Ext.grid.CheckboxSelectionModel({});
    var rowNum = new Ext.grid.RowNumberer({
        header:'序号', width:35, sortable:true
    });
    
    var menuGrid = new Ext.grid.GridPanel({
        renderTo:'menuGrid',
        height:Ext.lib.Dom.getViewHeight(), 
        width:Ext.lib.Dom.getViewWidth(),
        viewConfig : {
			columnsText : "显示的列",
			forceFit : true
		},
		autoScroll:true, 
		autoExpandColumn:1,
        frame:true, 
        loadMask:true,
        sm:sm,
        cm:new Ext.grid.ColumnModel([rowNum,sm,
            {header:'编号',dataIndex:"id",hidden: true, hideable: false},
            {header:'时间戳',dataIndex:"ts",hidden: true, hideable: false},
            {header:'代理编号',dataIndex:"cpId",hidden: true, hideable: false},
            {header:'代理名称',dataIndex:"cpName",width:80},
            {header:"客户名称",dataIndex:"cusName",width:80},
            {header:"客户电话",dataIndex:"cusTel",width:80},
            {header:"要求阶段",dataIndex:"requestStage",width:80},
            {header:"要求类型",dataIndex:"requestType",width:80},
            {header:"客户要求",dataIndex:"request",width:150},
            {header:"创建人",dataIndex:"createName",width:50,hidden: true},
            {header:"创建时间",dataIndex:"createTime",width:50,hidden: true},
            {header:"更新人",dataIndex:"updateName",width:50,hidden: true},
            {header:"更新时间",dataIndex:"updateTime",width:50,hidden: true}
           
        ]),
        store:menuStore,
        tbar: [{text:'<b>导出</b>',iconCls:'sort_down',handler:function() {
                	parent.exportExl(menuGrid);
        } },'-',
            {
                text:'<B>增加</B>', id:'addbtn',tooltip:'增加客户个性化要求', iconCls: 'userAdd',handler:function() {
                	cusOper(null);
            } },
            '-',
            {
                text:'<B>修改</B>',id:'updatebtn',disabled:true, tooltip:'修改客户个性化要求', iconCls: 'userEdit', handler: function() {
                var _record = menuGrid.getSelectionModel().getSelections();
                if (_record.length < 1) {
						parent.Ext.Msg.alert("系统消息", "请选择您要操作的行！");
						return false;
					} else if (_record.length > 1) {
						parent.Ext.Msg.alert("系统消息", "一次只能修改一行！");
						return false;
					}else{
						cusOper(_record);
					}
            } } ,
            '-',
            {
                text:'<B>删除</B>',id:'deletebtn',disabled:true, tooltip:'删除选择个性化要求', iconCls: 'userDelete',
                handler: function(){
                	var _records = menuGrid.getSelectionModel().getSelections();
                	if (_records.length < 1) {
						parent.Ext.Msg.alert(alertTitle, "请选择您要操作的行！");
						return false;
					} 
					Ext.Msg.confirm(alertTitle, "确定要删除所选的"+_records.length+"行记录吗？", function(btnYes) {
						if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
								var ids="";
								for(var i=0;i<_records.length;i++){
									ids=ids+_records[i].data.id+",";
								}
								Ext.Ajax.request({
								url : sysPath+ "/sys/cusRequestAction!delete.action",
								params : {
									ids : ids,
									privilege:privilege
								},
								success : function(resp) {
								var respText = Ext.util.JSON.decode(resp.responseText);
								Ext.Msg.alert(alertTitle,"删除成功<br/>");
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
            '-','&nbsp;&nbsp;',
            {
            	xtype : 'combo',
            	triggerAction : 'all',
    			model : 'local',
    			hiddenId : 'checkItems',
    			hiddenName : 'checkItems',
    			name : 'checkItemstext',
            	store: [
            			['', '查询全部'], 
    					['LIKES_cpName', '代理名称'],
    					['LIKES_cusName','客户名称'],
    					['LIKES_cusTel','客户电话']
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
		menuStore.proxy = new Ext.data.HttpProxy({
					method : 'POST',
					url : sysPath
								+ "/sys/cusRequestAction!ralaList.action",
						params:{privilege:privilege,limit : pageSize}
				});

		menuStore.baseParams = {
			checkItems : Ext.get("checkItems").dom.value,
			privilege:privilege,
			itemsValue : Ext.get("searchContent").dom.value
		}
		var editbtn = Ext.getCmp('updatebtn');
		var deletebtn = Ext.getCmp('deletebtn');

		editbtn.setDisabled(true);
		deletebtn.setDisabled(true);
		menuStoreReload();
		
	}
     //查询
     
   menuGrid.render();
   /*
   menuStore.load({
   params:{
   	limit:pageSize,
   	filter_EQL_id:0
   	}
   });*/
   

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
   // var _records = menuGrid.getSelectionModel().getSelections();
	var form = new Ext.form.FormPanel({
					frame : true,
					border : false,
					bodyStyle : 'padding:5px 5px 5px',
					labelAlign : "left",
					labelWidth : 70,
					width : 400,
					labelAlign : "right",
					labelWidth : 70,
					defaults : {
						xtype : "textfield",
						width : 230
					},
					reader : new Ext.data.JsonReader({
			            root: 'result', totalProperty: 'totalCount'
			        }, [
			        	{name:'cpId'},
			            {name:'cpName'},
			            {name: 'cusName'},
			            {name: 'cusTel'},
			            {name: 'request'},
			            {name: 'requestStage'},
			            {name: 'requestType'}
			        ]),
					items:[
						   {xtype:'hidden',name:'ts',id:'ts'},
						   {xtype:'hidden',name:'id',id:'cusRequestId'},
						   {xtype:'hidden',id:'cpName',name:'cpName',value:'*'},
                           {
                           		xtype : 'combo',
    							forceSelection : true,
    							id:'comboCpName',
    							queryParam : 'filter_LIKES_cusName',
    							minChars : 0,
    							fieldLabel : '代理名称',
    							store : cpStore,
    							pageSize : pageSize,
    							triggerAction : 'all',
    							valueField : 'cpId',
    							displayField :'cpName',
    							hiddenName :'cpId',
    							emptyText : "*",
    							allowBlank : true,
    							listeners:{
    								'blur':function(thiscom){
							            if(thiscom.getValue()==""){
											thiscom.setValue("");
											thiscom.setRawValue("*");
							            }
    								},
    								'select':function(){
    									var name=Ext.get("comboCpName").dom.value;
										Ext.getCmp('cpName').setValue(name);
    								}
    							}
                           },
                           {
                           		xtype:'textfield',fieldLabel:'客户名称',
                           		id:'textCusName',
                           		name:'cusName',
                           		maxLength:20,
                           		value:"*",
                           		listeners:{
    								'blur':function(thiscom){
							            if(thiscom.getValue()==""){
											thiscom.setValue("*");
							            }
    								}
                           		}
                           },
                           {
                           		xtype:'textfield',
                           		id:'textCusTel',
                           		fieldLabel:'客户电话',
                           		name:'cusTel',
                           		value:"*",
                           		maxLength:20,
                           		listeners:{
    							'blur':function(thiscom){
								    if(thiscom.getValue()==""){
								     	thiscom.setValue("*");
								     }
    							   }
                           		 }
                           },
                           {
                           		xtype : 'combo',
    							typeAhead : true,
    							forceSelection : true,
    							queryParam : 'filter_LIKES_typeName',
    							minChars : 0,
    							fieldLabel : '要求阶段<span style="color:red">*</span>',
    							store : requestStageStore,
    							pageSize : pageSize,
    							triggerAction : 'all',
    							valueField : 'requestStage',
    							displayField : 'requestStage',
    							hiddenName : 'requestStage',
    							emptyText : "请输入要求阶段",
    							allowBlank : false,
    							blankText : "要求阶段不能为空！"
                           },
                           {
                           		xtype : 'combo',
    							typeAhead : true,
    							forceSelection : true,
    							queryParam : 'filter_LIKES_typeName',
    							minChars : 0,
    							fieldLabel : '要求类型<span style="color:red">*</span>',
    							store : requestTypeStore,
    							pageSize : pageSize,
    							triggerAction : 'all',
    							valueField : 'requestType',
    							displayField : 'requestType',
    							hiddenName : 'requestType',
    							emptyText : "请输入要求类型",
    							allowBlank : false,
    							blankText : "要求类型不能为空！"
                           },
                           {
                           		labelAlign : 'top',
								xtype : 'textarea',
								fieldLabel:'客户要求<span style="color:red">*</span>',
								name:'request',
								height : 100,
								allowBlank : false,
    							blankText : "客户个性化要求不能为空！"
							}
                    ]
                    
				});
		operTitle="添加个性化要求";
		if(_record!=null){
			operTitle="修改个性化要求";
			Ext.getCmp("cusRequestId").setValue(_record[0].data.id);
			form.load({
				url : sysPath+ "/sys/cusRequestAction!ralaList.action",
				params:{filter_EQL_id:_record[0].data.id,privilege:privilege,limit : pageSize},
				success:function(){
					Ext.getCmp("comboCpName").setValue(_record[0].data.cpId);
					Ext.getCmp("ts").setValue(_record[0].data.ts);
					Ext.getCmp("comboCpName").setRawValue(_record[0].data.cpName);
				}
			})
		}
		var win = new Ext.Window({
			title : operTitle,
			width : 400,
			closeAction : 'hide',
			plain : true,
			modal : true,
			items : form,
			buttonAlign : "center",
			buttons : [{
				text : "保存",
				iconCls : 'save',
				handler : function() {
					var cpName=Ext.getCmp("comboCpName");
					var cusTel=Ext.getCmp("textCusTel");
					if(cpName.getValue()==""){
						cpName.setValue("");
						cpName.setRawValue("*");
					}
					if(cpName.getValue()=="*"&&cusTel.getValue()=="*"){
						Ext.Msg.alert(alertTitle,
						"代理名称、客户电话不能同时为空!!");
						return false;
            		}
					if (form.getForm().isValid()) {
						form.getForm().submit({
							url : sysPath + "/sys/cusRequestAction!save.action?privilege=26",
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
						carTitle='修改个性化要求信息';
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
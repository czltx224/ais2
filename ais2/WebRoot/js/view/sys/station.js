var popupWin;
var privilege=54;
function parentHref(){
	location.href=sysPath+'/sys/stationAction!findStationList.action';
}

var fields=[
			{name:'stationId'},
            {name: 'stationName'},
            {name: 'parentStationId'},
            {name: 'parentStationName'},
            {name: 'createName'},
            {name: 'createTime'},
            {name: 'updateName'},
            {name: 'updateTime'},
            {name:'ts'}
];
Ext.onReady(function() {
    var menuStore = new Ext.data.Store({
        storeId:"menuStore",
        baseParams:{
        	filter_EQL_parentStationId:parent.parent_id,
        	limit : pageSize,
        	privilege:privilege
        },
        proxy: new Ext.data.HttpProxy({url:sysPath+"/sys/stationAction!ralaList.action"}),
        reader: new Ext.data.JsonReader({
            root: 'result', totalProperty: 'totalCount'
        }, fields)
    });
    var stationStore = new Ext.data.Store({ 
            storeId:"parentstationStore",                        
            proxy: new Ext.data.HttpProxy({url:sysPath+"/sys/stationAction!list.action",method:'post'}),
            reader: new Ext.data.JsonReader({
            root: 'result', totalProperty:'totalCount'
           }, [  
                 {name:'stationName',mapping:'text'},        
                 {name:'stationId',mapping:'id'}
              ])
     });
    //start
    var sm = new Ext.grid.CheckboxSelectionModel({});
    var rowNum = new Ext.grid.RowNumberer({
        header:'序号', width:50, sortable:true
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
            {header:'编号',dataIndex:"stationId",hidden: true, hideable: false},
            {header:'时间戳',dataIndex:"ts",hidden: true, hideable: false},
            {header:"岗位名称",dataIndex:"stationName",width:50},
            {header:"上级岗位编号",dataIndex:"parentStationId",width:50,hidden: true, hideable: false},
            {header:"上级岗位名称",dataIndex:"parentStationName",width:50},
            {header:"创建人",dataIndex:"createName",width:50},
            {header:"创建时间",dataIndex:"createTime",width:50},
            {header:"更新人",dataIndex:"updateName",width:50},
            {header:"更新时间",dataIndex:"updateTime",width:50}
           
        ]),
        store:menuStore,
        tbar: [
            {
                text:'<B>增加</B>', id:'addbtn',tooltip:'增加地区', iconCls: 'userAdd',handler:function() {
                	addMenu();
            } },
            '-',
            {
                text:'<B>修改</B>',id:'updatebtn',disabled:true, tooltip:'修改选择的岗位信息', iconCls: 'userEdit', handler: function() {
                var _record = menuGrid.getSelectionModel().getSelections();
                if (_record.length < 1) {
						parent.Ext.Msg.alert("系统消息", "请选择您要操作的行！");
						return false;
					} else if (_record.length > 1) {
						parent.Ext.Msg.alert("系统消息", "一次只能修改一行！");
						return false;
					}else{
						updateMenu(_record);
					}
            } } ,
            '-',
            {
                text:'<B>删除</B>',id:'deletebtn',disabled:true, tooltip:'删除选择的岗位', iconCls: 'userDelete',
                handler: function(){
                	var _records = menuGrid.getSelectionModel().getSelections();

					if (_records.length < 1) {
						parent.Ext.Msg.alert(alertTitle, "请选择您要操作的行！");
						return false;
					} 
					parent.Ext.Msg.confirm(alertTitle, "确定要删除所选的"+_records.length+"行记录吗？", function(btnYes) {
						if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
								var ids="";
								for(var i=0;i<_records.length;i++){
									ids=ids+_records[i].data.stationId+",";
								}
								Ext.Ajax.request({
								url : sysPath+ "/sys/stationAction!delete.action",
								params : {
									ids : ids,
									privilege:privilege
								},
								success : function(resp) {
								var respText = Ext.util.JSON.decode(resp.responseText);
								parent.Ext.Msg.alert(alertTitle,"删除成功<br/>");
								menuStore.reload({params : {
												limit : pageSize,
												filter_EQL_parentStationId: _records[0].data.parentStationId,
												privilege:privilege
												}
											});
										}
									});
									parent.reloadFunctionTree();
								}
							});
					
                }
            },'-','&nbsp;&nbsp;',
            {
            	xtype : 'textfield',
    			id :'searchContent',
    			name : 'stationName',
    			width : 100,
    			enableKeyEvents:true,
    			listeners : {
				 	keyup:function(textField, e){
			         	if(e.getKey() == 13){
			                 searchStation();
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
    					['LIKES_stationName', '岗位名称']
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
            	handler : searchStation
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
     //查询
     
    function searchStation() {
    	menuStore.proxy = new Ext.data.HttpProxy({
				method : 'POST',
				url : sysPath
						+ "/sys/stationAction!ralaList.action",
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
   menuGrid.render();
   menuStore.load();
   
   
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
    function addMenu() {
    	var _records = menuGrid.getSelectionModel().getSelections();
	var form = new parent.Ext.form.FormPanel({
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
					items:[
							{xtype:'hidden',name:'ts'},
                           {xtype:'hidden',name:'parentStationName',id:'parentStationName'},
                           {
                           		xtype : 'combo',
	        				   fieldLabel: '上级岗位<span style="color:red">*</span>',
					           allowBlank : false,
					           forceSelection : true,
						       minChars : 1,
						       listWidth:245,
						       id:'parentIdcombo',
						       hiddenName:'parentStationId',
						       triggerAction : 'all',
							   store: stationStore,
							   pageSize : pageSize,
							   queryParam : 'filter_LIKES_stationName',
							   displayField : 'stationName',
							   valueField : 'stationId',
							   blankText : "上级岗位名称不能为空！",
							   emptyText : "请选择岗位名称",
							   listeners:{
							   		select:function(combo){
							   			parent.Ext.getCmp('parentStationName').setValue(parent.Ext.get('parentIdcombo').dom.value);
							   		}
							   }
                           	},
                           {xtype:'textfield',fieldLabel:'岗位名称<span style="color:red">*</span>',name:'stationName',allowBlank:false,blankText:'岗位名称不能为空！',maxLength:20}
                    ]
				});
		var win = new parent.Ext.Window({
			title : '添加岗位信息',
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
					if (form.getForm().isValid()) {
						form.getForm().submit({
							url : sysPath + "/sys/stationAction!save.action?privilege=23",
							waitMsg : '正在保存数据...',
							success : function(form, action) {
								win.hide(), 
								parent.Ext.Msg.alert(alertTitle,
										action.result.msg);
								menuStore.reload({params : {
												limit : pageSize,
												filter_EQL_parentStationId:parent.parent_id,
												privilege:privilege
												}
											});
								parent.reloadFunctionTree();
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
					form.getForm().reset();
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
	
	
	 function updateMenu(_record) {
    	var _records = menuGrid.getSelectionModel().getSelections();
		var form = new parent.Ext.form.FormPanel({
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
					url : sysPath
    				+ "/sys/stationAction!list.action?filter_EQL_id="
    				+ _record[0].data.stationId,
					 reader: new Ext.data.JsonReader({
          			 root: 'result', totalProperty: 'totalCount'
      		  		}, fields),
					items:[
						   {xtype:'hidden',name:'ts'},
						   {xtype:'hidden',name:'stationId',id:'stationId'},
						   {xtype:'hidden',name:'createName',id:'createName'},
						   {xtype:'hidden',name:'createTime',id:'createTime'},
                           {xtype:'hidden',name:'parentStationName',value:_record[0].data.parentStationName},
                       		{
	                       		xtype : 'combo',
                           		id:'upStationCombo',
    							typeAhead : true,
    							forceSelection : true,
    							queryParam : 'filter_LIKES_stationName',
    							minChars :0,
    							fieldLabel : '上级岗位<span style="color:red">*</span>',
    							store : stationStore,
    							pageSize : pageSize,
    							triggerAction : 'all',
    							valueField : 'stationId',
    							displayField : 'stationName',
    							hiddenName : 'parentStationId',
    							emptyText : "请选择岗位名称",
    							allowBlank : false,
							    listeners:{
							   		select:function(combo){
							   			parent.Ext.getCmp('parentStationName').setValue(parent.Ext.get('upStationCombo').dom.value);
							   		}
							   }
                           	},
                           {xtype:'textfield',fieldLabel:'岗位名称<span style="color:red">*</span>',name:'stationName',id:'stationName',allowBlank:false,blankText:'岗位名称不能为空！',maxLength:20}
                    ]
				});
		form.getForm().load({
    			waitMsg : '正在载入数据...',
    			success : function(_form, action) {
	    		    parent.Ext.getCmp("upStationCombo").setValue(_record[0].data.parentStationId);
	    		    parent.Ext.getCmp("upStationCombo").setRawValue(_record[0].data.parentStationName);
	    			parent.Ext.getCmp("stationName").setRawValue(_record[0].data.stationName);
	    			parent.Ext.getCmp("stationId").setValue(_record[0].data.stationId);
    			},
    			failure : function(_form, action) {
    				parent.Ext.MessageBox.alert(alertTitle, '载入失败');
    			}
    	});
		var win = new parent.Ext.Window({
			title : '修改岗位信息',
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
					if (form.getForm().isValid()) {
						form.getForm().submit({
							url : sysPath + "/sys/stationAction!save.action?privilege=23",
							waitMsg : '正在保存数据...',
							success : function(form, action) {
								win.hide(), 
								parent.Ext.Msg.alert(alertTitle,
										action.result.msg);
								menuStore.reload({params : {
												privilege:privilege,
												limit : pageSize,
												filter_EQL_parentStationId:parent.parent_id
												}
											});
								parent.reloadFunctionTree();
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
			},{
				text : "重置",
				handler : function() {
					form.load({
						url : sysPath
								+ "/sys/stationAction!ralaList.action",
						params:{filter_EQL_stationId:_record[0].data.stationId,privilege:privilege,limit : pageSize}
						});
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
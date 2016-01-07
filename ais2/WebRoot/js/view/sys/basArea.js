var popupWin;
var privilege=55;
function parentHref(){
	location.href=sysPath+'/sys/basAreaAction!findAreaList.action';
}
Ext.onReady(function() {
	
	var addrStore=new Ext.data.Store({
		storeId:"addrStore",
		proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/dictionaryAction!ralaList.action"}),
		baseParams:{filter_EQL_basDictionaryId:3,privilege:16},
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
        	{name:'id'},
        	{name:'areaType',mapping:'typeName'}
        	])
	});
	addrStore.load();
	
	var departStartStore=new Ext.data.Store({
		storeId:"departStartStore",
		method:'post',
		baseParams:{limit:pageSize,filter_EQL_isBussinessDepa:1},
		proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/departAction!findAll.action"}),
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
        	{name:'departId'},
        	{name:'departName'}
        	])
	});
	//departStartStore.load();
	
	var departEndStore=new Ext.data.Store({
		storeId:"departEndStore",
		method:'post',
		baseParams:{limit:pageSize,filter_EQL_isBussinessDepa:1},
		proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/departAction!findAll.action"}),
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
        	{name:'departId'},
        	{name:'departName'}
        	])
	});
	//departEndStore.load();
	
	var devModeStore=new Ext.data.Store({
		storeId:"devModeStore",
		baseParams:{filter_EQL_basDictionaryId:4,privilege:16},
		proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/dictionaryAction!ralaList.action"}),
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
        	{name:'id'},
        	{name:'develpMode',mapping:'typeName'}
        	])
	});
	devModeStore.load();
	// start custom
	var cusStore=new Ext.data.Store({
		storeId:"cusStore",
		baseParams:{limit:pageSize},
		proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/customerAction!getTraCustomer.action"}),
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
        	{name:'id'},
        	{name:'cusName'}
        	])
	});
	cusStore.load();
	//end custom
	var areaFields=[
		{name:'id'},
        {name: 'areaName'},
        {name: 'areaType'},
        {name: 'distriDepartId'},
        {name: 'distriDepartName'},
        {name: 'endDepartId'},
        {name: 'endDepartName'},
        {name: 'develpMode'},
        {name:'parentId'},
        {name:'cusName'},
        {name:'parentName'},
        {name:'areaRank'}
	];
	var areaStore=new Ext.data.Store({
		storeId:"areaStore",
		proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/basAreaAction!ralaList.action"}),
		baseParams:{privilege:55,limit:pageSize,filter_NES_areaRank:'镇/街道'},
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },areaFields)
	});
    var menuStore = new Ext.data.Store({
        storeId:"menuStore",
        proxy: new Ext.data.HttpProxy({url:sysPath+"/sys/basAreaAction!ralaList.action"}),
        baseParams:{filter_EQL_parentId:parent.parent_id,limit : pageSize,privilege:privilege},
        reader: new Ext.data.JsonReader({
            root: 'result', totalProperty: 'totalCount'
        }, [
            {name:'id'},
            {name: 'areaName'},
            {name: 'areaType'},
            {name: 'distriDepartId'},
            {name: 'distriDepartName'},
            {name: 'endDepartId'},
            {name: 'endDepartName'},
            {name: 'develpMode'},
            {name:'parentId'},
            {name:'cusId'},
            {name:'cusName'},
            {name:'parentName'},
            {name:'areaRank'},
            {name:'ts'}
        ])
    });
    //start
    var sm = new Ext.grid.CheckboxSelectionModel({});
    var rowNum = new Ext.grid.RowNumberer({
        header:'序号', width:35,sortable:true
    });
    
    
    var menuGrid = new Ext.grid.GridPanel({
        renderTo:'menuGrid',
        //id:'menuGrid',
        height:Ext.lib.Dom.getViewHeight(), 
        width:Ext.lib.Dom.getViewWidth(),
        viewConfig : {
			columnsText : "显示的列",
			forceFit : true
		},
		autoScroll:true, autoExpandColumn:1,
        frame:true, 
        loadMask:true,
        sm:sm,
        cm:new Ext.grid.ColumnModel([rowNum,sm,
            {header:'编号',dataIndex:"id",hidden: true, hideable: false},
            {header:'时间戳',dataIndex:"ts",hidden: true, hideable: false},
            {header:'终端部门ID',dataIndex:"endDepartId",hidden: true, hideable: false},
            {header:"地区名称",dataIndex:"areaName",width:50},
            {header:"上级地区",dataIndex:"parentId",width:50},
            {header:"上级地区",dataIndex:"parentName",width:50},
            {header:"地址类型",dataIndex:"areaType",width:50},
            {header:"地区级别",dataIndex:"areaRank",width:50},
            {header:"配送部门名称",dataIndex:"distriDepartName",width:50},
       		{header:"终端部门名称",dataIndex:"endDepartName",width:50},
            {header:"配送方式",dataIndex:"develpMode",width:50},
            {header:"中转/外发公司",dataIndex:"cusName",width:50}
        ]),
        store:menuStore,
        tbar: [{text:'<b>导出</b>',iconCls:'sort_down',handler:function() {
                	parent.parent.exportExl(menuGrid);
        } },'-',
            {
                text:'<B>增加</B>', id:'addbtn',tooltip:'增加地区', iconCls: 'userAdd',handler:function() {
                	addMenu();
            } },
            '-',
            {
                text:'<B>修改</B>',id:'updatebtn',disabled:true, tooltip:'修改选择的地区信息', iconCls: 'userEdit', handler: function() {
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
                text:'<B>删除</B>',id:'deletebtn',disabled:true, tooltip:'删除选择的地区', iconCls: 'userDelete',
                handler: function(){
                	var _records = menuGrid.getSelectionModel().getSelections();

					if (_records.length != 1) {
						parent.Ext.Msg.alert(alertTitle, "一次只能操作一行数据！");
						return false;
					}
					parent.Ext.Msg.confirm(alertTitle, "确定要删除所选的"+_records.length+"行记录吗？", function(btnYes) {
						if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
								//var ids="";
								//for(var i=0;i<_records.length;i++){
									//ids=ids+_records[i].data.id+",";
								//}
								Ext.Ajax.request({
									url : sysPath+ "/sys/basAreaAction!delete.action",
										params : {
											id : _records[0].data.id,
											privilege:privilege
										},
										success : function(resp) {
											var respText = Ext.util.JSON.decode(resp.responseText);
											parent.Ext.Msg.alert(alertTitle,respText.msg);
											if(respText.success){
												menuStore.reload({
													params : {
															start : 0,
															limit : pageSize,
															filter_EQL_parentId: _records[0].data.parentId,
															privilege:privilege
													}
												});
												parent.reloadFunctionTree();
											}
										}
									});
								}
							});
					
                }
            },'-','&nbsp;&nbsp;',
            {
            	xtype : 'textfield',
    			id :'searchContent',
    			name : 'areaName',
    			width : 80,
    			enableKeyEvents:true,
    			listeners : {
			 		keyup:function(textField, e){
		                     if(e.getKey() == 13){
		                     	searchArea();
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
    			editable:false,
    			name : 'checkItemstext',
            	store: [
            			['', '查询全部'], 
    					['LIKES_areaName', '地区名称'],
    					['LIKES_areaType','地址类型'],
    					['LIKES_distriDepartName','配送部门'],
    					['LIKES_endDepartName','终端配送部门'],
    					['LIKES_develpMode','配送方式'],
    					['LIKES_cusName','客商名称']
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
            	handler : searchArea
            }
        ],
        bbar: new Ext.PagingToolbar({
            pageSize: pageSize, 
            store: menuStore,
            privilege:privilege, 
            displayInfo: true,
            displayMsg: '显示第 {0} 条到 {1} 条记录，共 {2} 条', emptyMsg: "没有记录信息显示"
        })
    });
    menuGrid.render();
    menuStore.load();
    //查询
    function searchArea() {
    	menuStore.proxy = new Ext.data.HttpProxy({
				method : 'POST',
				url : sysPath
						+ "/sys/basAreaAction!ralaList.action",
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
						   {xtype:'hidden',id:'hidDisName',name:'distriDepartName'},
						   {xtype:'hidden',id:'hidEndName',name:'endDepartName'},
						   {xtype:'hidden',id:'cusName',name:'cusName'},
                           {xtype:'hidden',name:'parentName',id:'parentName'},
                           {
                           		xtype:'combo',
                           		hiddenName:'parentId',
                           		id:'parentIdcombo',
                           		fieldLabel:'上级地区<span style="color:red">*</span>',
                           		forceSelection : true,
                           		triggerAction : 'all',
                           		store:areaStore,
                           		pageSize:pageSize,
    							queryParam : 'filter_LIKES_areaName',
    							minChars : 0,
    							valueField : 'id',
    							displayField : 'areaName',
                           		//value:parent.parent_name,
                           		listeners:{
                           			'select':function(combo){
                           				parent.Ext.getCmp('parentName').setValue(parent.Ext.get('parentId').dom.value);
                           			}
                           		}
                           	},
                           {xtype:'textfield',fieldLabel:'地区名称<span style="color:red">*</span>',name:'areaName',allowBlank:false,blankText:'地区名称不能为空！',maxLength:20},
                           {
                           		xtype : 'combo',
    							typeAhead : true,
    							forceSelection : true,
    							queryParam : 'filter_LIKES_typeName',
    							minChars : 0,
    							fieldLabel : '地址类型<span style="color:red">*</span>',
    							store : addrStore,
    							triggerAction : 'all',
    							valueField : 'areaType',
    							displayField : 'areaType',
    							hiddenName : 'areaType',
    							emptyText : "请输入地址类型",
    							allowBlank : false,
    							blankText : "地址类型不能为空！"
                           },
                           {
                        	    xtype:'combo',
                           		name:'areaRank',
                           		fieldLabel:'地区级别<span style="color:red">*</span>',
                           		forceSelection : true,
                           		triggerAction : 'all',
                           		store:[
                           			['省','省'],
                           			['市','市'],
                           			['区/县','区/县'],
                           			['镇/街道','镇/街道']
                           		],
    							valueField : 'areaRank',
    							displayField : 'areaRank'
                           },
                           {
                           		xtype : 'combo',
    							typeAhead : true,
    							forceSelection : true,
    							queryParam : 'filter_LIKES_departName',
    							minChars : 0,
    							id:'comboDisId',
    							fieldLabel : '配送部门',
    							store : departStartStore,
    							pageSize : pageSize,
    							triggerAction : 'all',
    							valueField : 'departId',
    							displayField : 'departName',
    							hiddenName : 'distriDepartId',
    							emptyText : "请输入配送部门",
    							listeners : {
									'select' : function(combo, record, index) {
											var name=parent.Ext.get("comboDisId").dom.value;
											parent.Ext.getCmp('hidDisName').setValue(name);
									}
								}
                           },
                           {
                           		xtype : 'combo',
    							typeAhead : true,
    							forceSelection : true,
    							queryParam : 'filter_LIKES_departName',
    							minChars : 0,
    							fieldLabel : '终端部门',
    							store : departEndStore,
    							pageSize : pageSize,
    							id:'comboEndDepartId',
    							triggerAction : 'all',
    							valueField : 'departId',
    							displayField : 'departName',
    							hiddenName : 'endDepartId',
    							emptyText : "请输入终端部门",
    							//allowBlank : false,
    							//blankText : "终端部门不能为空！",
    							listeners : {
									'select' : function(combo, record, index) {
											var name=parent.Ext.get("comboEndDepartId").dom.value;
											parent.Ext.getCmp('hidEndName').setValue(name);
									}
								}
                           },
                           {
                           		xtype : 'combo',
    							typeAhead : true,
    							forceSelection : true,
    							queryParam : 'filter_LIKES_develpMode',
    							minChars : 0,
    							fieldLabel : '配送方式<span style="color:red">*</span>',
    							store : devModeStore,
    							triggerAction : 'all',
    							valueField : 'develpMode',
    							displayField : 'develpMode',
    							hiddenName : 'develpMode',
    							emptyText : "请输入配送方式",
    							allowBlank : false,
    							blankText : "配送方式不能为空！"
                           },
							 {
                           		xtype : 'combo',
    							//typeAhead : true,
    							forceSelection : true,
    							queryParam : 'filter_LIKES_cusName',
    							minChars : 0,
    							fieldLabel : '客商名称',
    							store : cusStore,
    							pageSize : pageSize,
    							triggerAction : 'all',
    							valueField : 'id',
    							displayField : 'cusName',
    							hiddenName : 'cusId',
    							id:'combocusId',
    							value:'',
    							emptyText : "请输入客商名称",
    							listeners:{
    								'select':function(combo){
    									parent.Ext.getCmp('cusName').setValue(parent.Ext.get('combocusId').dom.value);
    								}
    							}
                           }
                    ]
				});
		/*
		form.on('render',function(){
			var ar="";
			var areaRank=parent.Ext.getCmp('areaRank');
			var parentId=parent.parent_id;
			Ext.Ajax.request({
				url:sysPath+'/sys/basAreaAction!ralaList.action',
				params:{
					privilege:privilege,
					limit:pageSize,
					filter_EQL_id:parentId
				},success:function(resp){
					var respText = Ext.util.JSON.decode(resp.responseText);
					ar=respText.result[0].areaRank;
					if(ar.indexOf('省')>=0){
						areaRank.setValue('市');
					}else if(ar.indexOf('市')>=0){
						areaRank.setValue('区/县');
					}else if(ar.indexOf('区')>=0){
						areaRank.setValue('镇/街道');
					}
					else{
						areaRank.setValue('此地区系统无法识别！');
					}
				}
			});
		});
		*/
		var win = new parent.Ext.Window({
			title : '添加地区信息',
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
							url : sysPath + "/sys/basAreaAction!save.action?privilege=55",
							waitMsg : '正在保存数据...',
							success : function(form, action) {
								win.hide(), 
								parent.Ext.Msg.alert(alertTitle,'保存成功！');
								menuStore.reload({params : {
												start : 0,
												limit : pageSize,
												privilege:privilege,
												filter_EQL_parentId:parent.parent_id
												}
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
						parent.reloadFunctionTree();
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
    //end
    function updateMenu(_record) {
    	var _records = menuGrid.getSelectionModel().getSelections();
		var form = new parent.Ext.form.FormPanel({
					frame : true,
					border : false,
					bodyStyle : 'padding:5px 5px 5px',
					labelAlign : "left",
					labelWidth : 70,
					width : 400,
					url:sysPath+'/sys/basAreaAction!ralaList.action?filter_EQL_id='+_record[0].data.id,
					baseParams:{privilege:privilege},
					reader : new Ext.data.JsonReader({
			            root: 'result', totalProperty: 'totalCount'
			        }, [
			            {name:'areaName'},
			            {name:'areaRank'},
			            {name: 'areaType'},
			            {name: 'cusName'},
			            {name: 'distriDepartId'},
			            {name: 'distriDepartName'},
			            {name: 'endDepartId'},
			            {name: 'endDepartName'},
			            {name: 'develpMode'},
			            {name: 'ts'},
			            {name:'parentName'}
			        ]),
					labelAlign : "right",
					labelWidth : 70,
					defaults : {
						xtype : "textfield",
						width : 230
					},
					items:[
						   {xtype:'hidden',name:'ts'},
						   {xtype:'hidden',id:'hidDisName',name:'distriDepartName'},
						   {xtype:'hidden',id:'hidEndName',name:'endDepartName'},
						   {xtype:'hidden',id:'cusName',name:'cusName'},
						   {xtype:'hidden',name:'id',value:_record[0].data.id},
                           {xtype:'hidden',name:'parentName',id:'parentName',value:_record[0].data.parentName},
                           {
                           		xtype:'combo',
                           		hiddenName:'parentId',
                           		//hiddenName:'parentId',
                           		id:'parentIdcombo',
                           		fieldLabel:'上级地区<span style="color:red">*</span>',
                           		forceSelection : true,
                           		triggerAction : 'all',
                           		store:areaStore,
                           		disabled:true,
                           		pageSize:pageSize,
    							queryParam : 'filter_LIKES_areaName',
    							minChars : 0,
    							valueField : 'id',
    							displayField : 'areaName',
                           		listeners:{
                           			'select':function(combo){
                           				parent.Ext.getCmp('parentName').setValue(parent.Ext.get('parentIdcombo').dom.value);
                           			}
                           		}
                           },
                           {xtype:'textfield',fieldLabel:'地区名称<span style="color:red">*</span>',name:'areaName',allowBlank:false,blankText:'地区名称不能为空！',maxLength:20},
                           {
                           		xtype : 'combo',
                           		id:'comboAddr',
    							typeAhead : true,
    							forceSelection : true,
    							queryParam : 'filter_LIKES_areaType',
    							minChars : 0,
    							fieldLabel : '地址类型<span style="color:red">*</span>',
    							store : addrStore,
    							pageSize : pageSize,
    							triggerAction : 'all',
    							valueField : 'areaType',
    							displayField : 'areaType',
    							hiddenName : 'areaType',
    							emptyText : "请输入地址类型",
    							allowBlank : false,
    							blankText : "地址类型不能为空！"
                           },
                           {
                        	    xtype:'combo',
                           		name:'areaRank',
                           		fieldLabel:'地区级别<span style="color:red">*</span>',
                           		forceSelection : true,
                           		triggerAction : 'all',
                           		store:[
                           			['省','省'],
                           			['市','市'],
                           			['区/县','区/县'],
                           			['镇/街道','镇/街道']
                           		],
    							valueField : 'areaRank',
    							displayField : 'areaRank'
                           },
                           {
                           		xtype : 'combo',
    							typeAhead : true,
    							forceSelection : true,
    							queryParam : 'filter_LIKES_departName',
    							minChars : 0,
    							id:'comboDisId',
    							fieldLabel : '配送部门',
    							store : departStartStore,
    							pageSize : pageSize,
    							triggerAction : 'all',
    							valueField : 'departId',
    							displayField : 'departName',
    							hiddenName : 'distriDepartId',
    							emptyText : "请输入配送部门",
    							listeners : {
									'select' : function(combo, record, index) {
											var name=parent.Ext.get("comboDisId").dom.value;
											parent.Ext.getCmp('hidDisName').setValue(name);
									}
								}
                           },
                           {
                           		xtype : 'combo',
    							typeAhead : true,
    							forceSelection : true,
    							queryParam : 'filter_LIKES_departName',
    							minChars : 0,
    							fieldLabel : '终端部门',
    							store : departEndStore,
    							pageSize : pageSize,
    							id:'comboEndDepartId',
    							triggerAction : 'all',
    							valueField : 'departId',
    							displayField : 'departName',
    							hiddenName : 'endDepartId',
    							emptyText : "请输入终端部门",
    							//allowBlank : false,
    							//blankText : "终端部门不能为空！",
    							listeners : {
									'select' : function(combo, record, index) {
											var name=parent.Ext.get("comboEndDepartId").dom.value;
											parent.Ext.getCmp('hidEndName').setValue(name);
									}
								}
                           },
                           {
                           		xtype : 'combo',
                           		id:'comboDevMode',
    							typeAhead : true,
    							forceSelection : true,
    							queryParam : 'filter_LIKES_develpMode',
    							minChars : 0,
    							fieldLabel : '配送方式<span style="color:red">*</span>',
    							store : devModeStore,
    							pageSize : pageSize,
    							triggerAction : 'all',
    							valueField : 'develpMode',
    							displayField : 'develpMode',
    							hiddenName : 'develpMode',
    							emptyText : "请输入配送方式",
    							allowBlank : false,
    							blankText : "配送方式不能为空！"
                           },
                           // start custom
							 {
                           		xtype : 'combo',
    							typeAhead : true,
    							forceSelection : true,
    							queryParam : 'filter_LIKES_cusName',
    							minChars : 0,
    							fieldLabel : '客商名称',
    							store : cusStore,
    							pageSize : pageSize,
    							triggerAction : 'all',
    							valueField : 'id',
    							displayField : 'cusName',
    							hiddenName : 'cusId',
    							id:'combocusId',
    							value:'',
    							emptyText : "请输入客商名称",
    							listeners:{
    								'select':function(combo){
    									parent.Ext.getCmp('cusName').setValue(parent.Ext.get('combocusId').dom.value);
    								}
    							}
                           }
							//end custom
                    ]
				});
				form.load({
    				waitMsg : '正在载入数据...',
    				success : function(_form, action) {
    					parent.Ext.getCmp("parentIdcombo").setValue(_record[0].data.parentId);
    					parent.Ext.getCmp("parentIdcombo").setRawValue(_record[0].data.parentName);
    					parent.Ext.getCmp("parentName").setValue(_record[0].data.parentName);
    					
    					
    					parent.Ext.getCmp('parentIdcombo').setDisabled(true);
    					parent.Ext.getCmp("parentName").setDisabled(true);
    					//var disId=parent.Ext.getCmp('comboDisId').getValue();
    					//var endId=parent.Ext.getCmp('comboEndDepartId').getValue();
    					/*
		   				departStartStore.reload({
		   					params:{
							start : 0,
							privilege:privilege,
							limit : pageSize,
							filter_EQL_departId:disId
							
						}
		   				});*/
    					if(_record[0].data.distriDepartId==0){
    						parent.Ext.getCmp('comboDisId').setValue("");
    					}else{
    						parent.Ext.getCmp('comboDisId').setValue(_record[0].data.distriDepartId);
		   					parent.Ext.getCmp('comboDisId').setRawValue(_record[0].data.distriDepartName);
    					}
    					
		   				if(_record[0].data.endDepartId==0){
		   					parent.Ext.getCmp('comboEndDepartId').setValue("");
		   				}else{
		   					parent.Ext.getCmp('comboEndDepartId').setValue(_record[0].data.endDepartId);
		   					parent.Ext.getCmp('comboEndDepartId').setRawValue(_record[0].data.endDepartName);
		   				}
		   				
		   				if(_record[0].data.cusId==0){
		   					parent.Ext.getCmp('combocusId').setValue("");
		   				}else{
		   					parent.Ext.getCmp('combocusId').setValue(_record[0].data.cusId);
		   					parent.Ext.getCmp('combocusId').setRawValue(_record[0].data.cusName);
		   				}
    				},
    				failure : function(_form, action) {
    					parent.Ext.MessageBox.alert(alertTitle, '载入失败');
    				}
    			});
		var win = new parent.Ext.Window({
			title : '修改地区信息',
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
							url : sysPath + "/sys/basAreaAction!save.action",
							waitMsg : '正在保存数据...',
							success : function(form, action) {
								win.hide(), 
								parent.Ext.Msg.alert(alertTitle,
										action.result.msg);
								menuStore.reload({params : {
												start : 0,
												limit : pageSize,
												privilege:privilege,
												filter_EQL_parentId:parent.parent_id
												}
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
						parent.reloadFunctionTree();
					}
				}
			}, {
				text : "重置",
				handler : function() {
					form.load({
    				waitMsg : '正在载入数据...',
    				success : function(_form, action) {
    					var disId=parent.Ext.getCmp('comboDisId').getValue();
    					var endId=parent.Ext.getCmp('comboEndDepartId').getValue();
		   				departStartStore.reload({
		   					params:{
							start : 0,
							privilege:privilege,
							limit : pageSize,
							filter_EQL_departId:disId
							
						}
		   				});
		   				departEndStore.reload({
		   					params:{
							start : 0,
							privilege:privilege,
							filter_EQL_departId:endId
						}
		   				});
		   				
    					form.load();
    				},
    				failure : function(_form, action) {
    					parent.Ext.MessageBox.alert(alertTitle, '载入失败');
    				}
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
});
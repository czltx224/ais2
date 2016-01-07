var privilege=53;
var popupWin;
function parentHref(){
	location.href=sysPath+'/sys/departAction!toDepartList.action';
}
Ext.onReady(function() {
	
	//var _records = parent.document.parent_id;
	var stationStore=new Ext.data.Store({
		storeId:"stationStore",
		proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/stationAction!ralaList.action"}),
		baseParams:{privilege:54,limit:pageSize},
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
        	{name:'stationId'},
        	{name:'stationName'}
        	])
	});
	var addressTypeStore = new Ext.data.Store({ 
			storeId:"addressTypeStore",
			baseParams:{filter_EQL_basDictionaryId:3,privilege:16},
			proxy:new Ext.data.HttpProxy({url:sysPath+'/sys/dictionaryAction!ralaList.action'}),
			reader:new Ext.data.JsonReader({
	                    root:'result',
	                    totalProperty:'totalCount'
	        },[
        	{name:'addressTypeId',mapping:'typeCode'},
        	{name:'addressTypeName',mapping:'typeName'}
        	])
		});
	//stationStore.load();
	
    var menuStore = new Ext.data.Store({
        storeId:"menuStore",
        proxy: new Ext.data.HttpProxy({url:sysPath+"/sys/departAction!getDepartByParentId.action"}),
        baseParams:{parentId:parent.parent_id,limit:pageSize},
        reader: new Ext.data.JsonReader({
            root: 'result', totalProperty: 'totalCount'
        }, [
            {name:'departId'},
            {name: 'departName'},
             {name: 'departNo'},
            {name: 'createName'},
            {name: 'isBussinessDepa',convert:function(v){
            	if(v==0){
            		return '否';
            	}else{
            		return '是';
            	}
            }},
            {name: 'createTime'},
            {name: 'parentName'},
            {name:'parentId'},
            {name:'isCusDepart'},
            {name:'stationId',mapping:'leadStation'},
            {name:'stationName',mapping:'staName'},
            {name:'ts'},
            {name:'addr'},
            {name:'telephone'},
            {name:'updateName'},
            {name:'updateTime'},
            {name:'owntakeType'}
        ])
    });
    //start
    var sm = new Ext.grid.CheckboxSelectionModel({
    
    });
    var rowNum = new Ext.grid.RowNumberer({
        header:'序号', width:50, sortable:true
    });
    var menuGrid = new Ext.grid.GridPanel({
        renderTo: 'menuGrid',
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
            {header:'编号',dataIndex:"departId",hidden: true, hideable: true},
            {header:'时间戳',dataIndex:"ts",hidden: true, hideable: false},
            {header:"部门名称",dataIndex:"departName",width:50,sortable:true},
            {header:"岗位编号",dataIndex:"stationId",width:50,hidden:true},
            {header:"部门编码",dataIndex:"departNo",width:50},
            {header:"上级部门名称",dataIndex:"parentName",width:50,sortable:true},
            {header:"负责岗位",dataIndex:"stationName",width:50,sortable:true},
       		{header:"属于业务部门",dataIndex:"isBussinessDepa",width:50,sortable:true},
       		{header:"属于客服部门",dataIndex:"isCusDepart",width:50,renderer:function(v){
       			if(v==1){
       				return "是";
       			}else{
       				return "否";
       			}
       		}},
            {header:"创建人",dataIndex:"createName",width:50,hidden: true},
           {header:"创建时间",dataIndex:"createTime",width:50,hidden: true},
           {header:"更新人",dataIndex:"updateName",width:50,hidden: true},
           {header:"更新时间",dataIndex:"updateTime",width:50,hidden: true},
           {header:"地址",dataIndex:"addr",width:50,sortable:true},
           {header:"联系电话",dataIndex:"telephone",width:50,sortable:true}
           
        ]),
        store:menuStore,
        tbar: [
            {
                text:'<B>增加</B>', id:'addbtn',tooltip:'增加部门', iconCls: 'userAdd',handler:function() {
                	addMenu();
            } },
            '-',
            {
                text:'<B>修改</B>',id:'updatebtn',disabled:true, tooltip:'修改选择的部门信息', iconCls: 'userEdit', handler: function() {
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
                text:'<B>删除</B>',id:'deletebtn',disabled:true, tooltip:'删除选择的部门', iconCls: 'userDelete',
                handler: function(){
                	var _records = menuGrid.getSelectionModel().getSelections();
					if(_records.length !=1 ){
						parent.Ext.Msg.alert(alertTitle,"一次只能操作一条数据<br/>");
						return false;
					}

					parent.Ext.Msg.confirm("系统提示", "确定要删除所选的"+_records.length+"行记录吗？", function(btnYes) {
						if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
								//var ids="";
								//for(var i=0;i<_records.length;i++){
									//ids=ids+_records[i].data.departId+",";
								//}
								Ext.Ajax.request({
									url : sysPath+ "/sys/departAction!delete.action",
									params : {
										id : _records[0].data.departId,
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
													parentId: _records[0].data.parentId
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
    			name : 'departName',
    			width : 100,
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
    			name : 'checkItemstext',
            	store: [
            			['', '查询全部'], 
    					['LIKES_departName', '部门名称'],
    					['LIKES_addr','部门地址'],
    					['LIKES_telephone','联系电话']
    				   ],
    			width:100,
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
            displayInfo: true,
            displayMsg: '显示第 {0} 条到 {1} 条记录，共 {2} 条', emptyMsg: "没有记录信息显示"
        })
    });
      //查询
    function searchArea() {
    		menuStore.proxy = new parent.Ext.data.HttpProxy({
    					method : 'POST',
    					url : sysPath+'/sys/departAction!findAll.action'
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
    		menuStore.reload({
    					params : {
    						start : 0,
    						limit : pageSize
    					}
    				});

    	}
    menuGrid.render();
    menuStore.load({
    	
    	});

	
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
    	//var _records = menuGrid.getSelectionModel().getSelections();
		var form = new parent.Ext.form.FormPanel({
					frame : true,
					border : false,
					bodyStyle : 'padding:5px 5px 5px',
					width : 400,
					labelAlign : "right",
					labelWidth : 90,
					defaults : {
						xtype : "textfield",
						width : 230
					},
					items:[
							{xtype:'hidden',name:'ts'},
                           {xtype:'hidden',name:'parentId',value:parent.parent_id},
                           {xtype:'hidden',name:'type',value:'add'},
                           {xtype:'textfield',fieldLabel:'上级部门',disabled:true,value:parent.parent_name},
                           {xtype:'textfield',fieldLabel:'上级部门编码',id:'departNo',name:'departNo',readOnly:true,value:parent.parent_no},
                           {xtype:'textfield',fieldLabel:'部门名称<span style="color:red">*</span>',name:'departName',allowBlank:false,blankText:'部门名称不能为空！',maxLength:20},
                           {
                           		xtype : 'combo',
    							typeAhead : true,
    							forceSelection : true,
    							queryParam : 'filter_LIKES_stationName',
    							minChars : 0,
    							fieldLabel : '负责岗位<span style="color:red">*</span>',
    							store : stationStore,
    							pageSize : pageSize,
    							triggerAction : 'all',
    							valueField : 'stationId',
    							displayField : 'stationName',
    							hiddenName : 'stationId',
    							emptyText : "请输入岗位名称",
    							allowBlank : false,
    							blankText : "岗位名称不能为空！"
                           },
                           {
                           	xtype:'radiogroup',
                           	id:'isBussinessDepa',
                           	fieldLabel:'属于业务部门<span style="color:red">*</span>',
                           	items: [{
			                    inputValue:'1',
			                    boxLabel: '是',
			                    name:'isBussinessDepa'
			                }, {
			                    inputValue:'0',
			                    name:'isBussinessDepa',
			                    boxLabel: '否',
			                    checked:true
			                }]
                           },
                           {
                           	xtype:'radiogroup',
                           	id:'isCusDepartradio',
                           	fieldLabel:'属于客服部门<span style="color:red">*</span>',
                           	items: [{
			                    inputValue:'1',
			                    boxLabel: '是',
			                    name:'isCusDepart'
			                }, {
			                    inputValue:'0',
			                    name:'isCusDepart',
			                    boxLabel: '否',
			                    checked:true
			                }]
                           },{
                           		xtype : "combo",
								triggerAction : 'all',
								forceSelection : true,
								minChars : 0,
								typeAhead : true,
								fieldLabel:'自提类型',
								//emptyText:'请选择',
								store : addressTypeStore,
								displayField : 'addressTypeName',
								valueField : 'addressTypeName',
								name : 'owntakeType',
								id:'owntakeType'
                           },
                           {xtype:'textfield',fieldLabel:'部门地址',name:'addr',maxLength:200},
                           {xtype:'textfield',fieldLabel:'联系电话',name:'telephone',maxLength:20}
                    ]
				});
				
		var win = new parent.Ext.Window({
			title : '添加部门信息',
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
					var isbus=form.getForm().getValues()['isBussinessDepa'];
					if(isbus==1){
						if(parent.Ext.getCmp('owntakeType').getValue()==''){
							parent.Ext.Msg.alert(alertTitle,'业务部门的自提类型不能为空！');
							return;
						}
					}
					if (form.getForm().isValid()) {
						form.getForm().submit({
							url : sysPath + "/sys/departAction!save.action",
							params:{
								privilege:privilege
							},
							waitMsg : '正在保存数据...',
							success : function(form, action) {
								win.hide(), 
								parent.Ext.Msg.alert(alertTitle,
										action.result.msg);
								menuStore.reload({params : {
												start : 0,
												limit : pageSize,
												parentId:parent.parent_id
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
		var form = new parent.Ext.form.FormPanel({
					frame : true,
					border : false,
					bodyStyle : 'padding:5px 5px 5px',
					labelAlign : "left",
					labelWidth : 70,
					width : 400,
					url : sysPath
    				+ "/sys/departAction!findAll.action?filter_EQL_departId="
    				+ _record[0].data.departId,
					reader : new Ext.data.JsonReader({
			            root: 'result', totalProperty: 'totalCount'
			        }, [
			            {name:'departId'},
			            {name:'departNo'},
			            {name: 'departName'},
			            {name: 'createName'},
			            {name: 'isBussinessDepa',convert:function(v){
			            	if(v==0){
			            		return '否';
			            	}else{
			            		return '是';
			            	}
			            }},
			            {name: 'createTime'},
			            {name: 'parentName'},
			            {name:'parentId'},
			            {name:'stationId',mapping:'leadStation'},
			            {name:'stationName',mapping:'staName'},
			            {name:'ts'},
			            {name:'addr'},
			            {name:'telephone'},
			            {name:'updateName'},
			            {name:'updateTime'},
			            {name:'owntakeType'}
			        ]),
					labelAlign : "right",
					labelWidth : 100,
					defaults : {
						xtype : "textfield",
						width : 230
					},
					items:[
                           {xtype:'hidden',name:'departId',value:_record[0].data.departId},
                           {xtype:'hidden',name:'ts'},
                           {xtype:'hidden',name:'type',value:'update'},
                           {xtype:'hidden',name:'parentId',value:_record[0].data.parentId},
                           {xtype:'textfield',fieldLabel:'上级部门',disabled:true,value:_record[0].data.parentName},
                           {xtype:'textfield',fieldLabel:'上级部门编码',id:'parentDepartNo',name:'parentDepartNo',readOnly:true},
                           {xtype:'textfield',fieldLabel:'部门名称<span style="color:red">*</span>',name:'departName',allowBlank:false,blankText:'部门名称不能为空！',maxLength:20,value:_record[0].data.departName},
                           {xtype:'textfield',fieldLabel:'部门编码',name:'departNo',readOnly:true,value:_record[0].data.departNo},
                           {
                           		xtype : 'combo',
                           		id:'upStationCombo',
    							typeAhead : true,
    							forceSelection : true,
    							queryParam : 'filter_LIKES_stationName',
    							minChars :0,
    							fieldLabel : '负责岗位<span style="color:red">*</span>',
    							store : stationStore,
    							pageSize : pageSize,
    							triggerAction : 'all',
    							valueField : 'stationId',
    							displayField : 'stationName',
    							hiddenName : 'stationId',
    							emptyText : "请选择岗位名称",
    							allowBlank : false
                           },
                           {
                           	xtype:'radiogroup',
                           	id:'raidoIsbuss',
                           	fieldLabel:'属于业务部门<span style="color:red">*</span>',
                           	items: [{
			                    inputValue:'1',
			                    boxLabel: '是',
			                    name:'isBussinessDepa',
			                    checked:_record[0].data.isBussinessDepa=="是"?true:false
			                }, {
			                    inputValue:'0',
			                    name:'isBussinessDepa',
			                    boxLabel: '否',
			                    checked:_record[0].data.isBussinessDepa=="否"?true:false
			                }]
                           },
                           {
                           	xtype:'radiogroup',
                           	id:'isCusDepartradio',
                           	fieldLabel:'属于客服部门<span style="color:red">*</span>',
                           	items: [{
			                    inputValue:'1',
			                    boxLabel: '是',
			                    name:'isCusDepart',
			                    checked:_record[0].data.isCusDepart=="1"?true:false
			                }, {
			                    inputValue:'0',
			                    name:'isCusDepart',
			                    boxLabel: '否',
			                    checked:_record[0].data.isCusDepart=="0"?true:false
			                }]
                           },{
                           		xtype : "combo",
								triggerAction : 'all',
								forceSelection : true,
								minChars : 0,
								typeAhead : true,
								fieldLabel:'自提类型',
								store : addressTypeStore,
								displayField : 'addressTypeName',
								valueField : 'addressTypeName',
								name : 'owntakeType',
								id:'owntakeType'
                           },
                           {xtype:'textfield',fieldLabel:'部门地址',name:'addr',maxLength:200},
                           {xtype:'textfield',fieldLabel:'联系电话',name:'telephone',maxLength:20}
                    ]
				});
				
			form.getForm().load({
    			waitMsg : '正在载入数据...',
    			success : function(_form, action) {
	    		    parent.Ext.getCmp("upStationCombo").setValue(_record[0].data.stationId);
	    		    parent.Ext.getCmp("upStationCombo").setRawValue(_record[0].data.stationName);
	    		    Ext.Ajax.request({
	    		    	url:sysPath+'/sys/departAction!findAll.action',
	    		    	params:{
	    		    		filter_EQL_departId:_record[0].data.parentId
	    		    	},success:function(resp){
	    		    		var respText = Ext.util.JSON.decode(resp.responseText);
	    		    		parent.Ext.getCmp('parentDepartNo').setValue(respText.result[0].departNo);
	    		    	}
	    		    });
    			},
    			failure : function(_form, action) {
    				parent.Ext.MessageBox.alert(alertTitle, '载入失败');
    			}
    	});
		
		var win = new parent.Ext.Window({
			title : '更新部门信息',
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
					var isbus=form.getForm().getValues()['isBussinessDepa'];
					if(isbus==1){
						if(parent.Ext.getCmp('owntakeType').getValue()==''){
							parent.Ext.Msg.alert(alertTitle,'业务部门的自提类型不能为空！');
							return;
						}
					}
					if (form.getForm().isValid()) {
						form.getForm().submit({
							url : sysPath + "/sys/departAction!save.action",
							params:{
								privilege:privilege
							},
							waitMsg : '正在保存数据...',
							success : function(form, action) {
								win.hide(), 
								parent.Ext.Msg.alert(alertTitle,
										action.result.msg);
								menuStore.reload({params : {
												start : 0,
												limit : pageSize,
												parentId:parent.parent_id
												}
											});
								parent.reloadFunctionTree();
							},
							failure : function(form, action) {
								if (action.faliureType == Ext.form.Action.SERVER_INVALID) {
									parent.Ext.Msg.alert(alertTitle, "服务器连接异常，请联系管理员！");
								} else {
									if (action.result.msg) {
										win.hide();
										parent.Ext.Msg.alert(alertTitle,
												action.result.msg);
									}
								}
							}
						});
					}
				}
			},  {
				text : "重置",
				handler : function() {
					form.load({
					url : sysPath
								+ "/sys/departAction!findAll.action",
					params:{filter_EQL_departId:_record[0].data.departId,limit : pageSize},
					success:function(){
					//var typeid = parent.Ext.getCmp('upStationCombo').getValue();
					 var _record = menuGrid.getSelectionModel().getSelections();
						parent.Ext.getCmp("upStationCombo").setValue(_record[0].data.stationId);
	    		    	parent.Ext.getCmp("upStationCombo").setRawValue(_record[0].data.stationName);
	    		     	parent.Ext.getCmp("raidoIsbuss").reset();
					}
					});
				}
			},{
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
   
});
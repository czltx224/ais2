var privilege=116;
var popupWin;
function parentHref(){
	location.href=sysPath+'/sys/exceptionTypeAction!excepList.action';
}
Ext.onReady(function() {
	
	var nodeStore= new Ext.data.Store({
			storeId:"nodeStore",
			proxy:new Ext.data.HttpProxy({url:sysPath+"/stock/oprNodeAction!list.action"}),
			reader:new Ext.data.JsonReader({
	                    root:'result',
	                    totalProperty:'totalCount'
	        },[
        	{name:'nodeOrder',mapping:'id'},
        	{name:'outName',mapping:'outName'}
        	])
	});
	
	var departStore = new Ext.data.Store({ 
            storeId:"departStore",                        
            proxy: new Ext.data.HttpProxy({url:sysPath+"/sys/departAction!ralaList.action",method:'post'}),
            baseParams:{privilege:53},
            reader: new Ext.data.JsonReader({
            root: 'result', totalProperty:'totalCount'
           }, [  
                 {name:'departName', mapping:'departName'},    
                 {name:'departId', mapping: 'departId'}
              ]),                                      
            sortInfo:{field:'departId',direction:'ASC'}
     });
	
    var menuStore = new Ext.data.Store({
        storeId:"menuStore",
        proxy: new Ext.data.HttpProxy({url:sysPath+"/sys/exceptionTypeAction!list.action"}),
        baseParams:{
        	limit:pageSize
        },
        reader: new Ext.data.JsonReader({
            root: 'result', totalProperty: 'totalCount'
        }, [
            {name:'id'},
            {name: 'nodeId'},
            {name: 'nodeName'},
            {name: 'isOpen',convert:function(v){
            	if(v==0){
            		return '否';
            	}else{
            		return '是';
            	}
            }},
            {name: 'isSmsAgency',convert:function(v){
            	if(v==0){
            		return '否';
            	}else{
            		return '是';
            	}
            }},
            {name: 'isSmsCusd',convert:function(v){
            	if(v==0){
            		return '否';
            	}else{
            		return '是';
            	}
            }},
            {name: 'typeName'},
            {name: 'dealFlow'},
            {name: 'createName'},
            {name: 'createTime'},
            {name: 'updateName'},
 			{name: 'doDepartName'},
			{name: 'doDepartId'},
			{name: 'bussDepartId'},
            {name: 'updateTime'},
            {name: 'remark'},
            {name: 'ts'},
            {name: 'isDoPiece',convert:function(v){
            	if(v==1){
            		return '是';
            	}else{
            		return '否';
            	}
            }}
        ])
    });
    
    //start
    var sm = new Ext.grid.CheckboxSelectionModel({
    
    });
    var rowNum = new Ext.grid.RowNumberer({
        header:'序号', width:35, sortable:true
    });
    var menuGrid = new Ext.grid.GridPanel({
        renderTo: 'menuGrid',
        //id:'menuGrid',
        height:Ext.lib.Dom.getViewHeight()-2, 
        width:Ext.lib.Dom.getViewWidth()-3,
        stripeRows : true, // 斑马线
        autoScroll : true,
        viewConfig : {
			columnsText : "显示的列",
			scrollOffset: 0,
			forceFit : true,
			autoScroll:true
		},
		frame : true,
		loadMask : true,
		autoExpandColumn:1,
        sm:sm,
        cm:new Ext.grid.ColumnModel([rowNum,sm,
            {header:'编号',dataIndex:"id",hidden: true, hideable: false},
            {header:'时间戳',dataIndex:"ts",hidden: true, hideable: false},
            {header:"异常类型名称",dataIndex:"typeName",width:50},
            {header:"操作环节名称",dataIndex:"nodeName",width:50},
            {header:"处理部门",dataIndex:"doDepartName",width:80,hidden:true,hideable:false },
            {header:"是否可填件数",dataIndex:"isDoPiece",width:50},
            {header:"是否网营公开",dataIndex:"isOpen",width:50},
            {header:"是否语音通知代理",dataIndex:"isSmsAgency",width:60},
            {header:"是否短信通知客户",dataIndex:"isSmsCusd",width:60},
            {header:"对应处理措施",dataIndex:"dealFlow",width:80},
            {header:"创建人",dataIndex:"createName",width:50,hidden: true},
            {header:"创建时间",dataIndex:"createTime",width:50,hidden: true},
            {header:"更新人",dataIndex:"updateName",width:50,hidden: true},
            {header:"更新时间",dataIndex:"updateTime",width:50,hidden: true},
            {header:"备注",dataIndex:"remark",width:150}
        ]),
        store:menuStore,
        tbar: ['&nbsp;&nbsp;',{
                text:'<B>增加</B>', 
                id:'addbtn',
                tooltip:'增加异常类型', 
                iconCls: 'userAdd',
                handler:function() {
                	addMenu();
            	} 
               },'-',{
                text:'<B>修改</B>',
                id:'updatebtn',
                disabled:true, 
                tooltip:'修改选择的异常类型信息', 
                iconCls: 'userEdit', 
                handler: function() {
                var _record = menuGrid.getSelectionModel().getSelections();
                if (_record.length < 1) {
						Ext.Msg.alert("系统消息", "请选择您要操作的行！");
						return false;
					} else if (_record.length > 1) {
						Ext.Msg.alert("系统消息", "一次只能修改一行！");
						return false;
					}else{
						updateMenu(_record);
					}
            	} 
            },'-',{
                text:'<B>删除</B>',
                id:'deletebtn',
                disabled:true, 
                tooltip:'删除异常类型', 
                iconCls: 'userDelete',
                handler: function(){
                	var _records = menuGrid.getSelectionModel().getSelections();
					if(_records.length<1){
						Ext.Msg.alert(alertTitle,"请选择要删除的行<br/>");
						return false;
					}

					Ext.Msg.confirm("系统提示", "确定要删除所选的"+_records.length+"行记录吗？", function(btnYes) {
						if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
								var ids="";
								for(var i=0;i<_records.length;i++){
									ids=ids+_records[i].data.id+",";
								}
								Ext.Ajax.request({
									url : sysPath+ "/sys/exceptionTypeAction!delete.action",
									params : {
										ids : ids,
										privilege:privilege
									},
									success : function(resp) {
										var respText = Ext.util.JSON.decode(resp.responseText);
										Ext.Msg.alert(alertTitle,"删除成功<br/>");
										menuStore.reload({params : {
														start : 0,
														limit : pageSize
												}
										});
									}
								});
							}
						});
					
                }
            },'-','&nbsp;&nbsp;',{
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
            },'-','&nbsp;&nbsp;',{
            	xtype : 'combo',
            	triggerAction : 'all',
    			model : 'local',
    			hiddenId : 'checkItems',
    			hiddenName : 'checkItems',
    			name : 'checkItemstext',
            	store: [
            			['', '查询全部'], 
    					['LIKES_nodeName', '操作环节名称'],
    					['LIKES_typeName','异常类型名称']
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
    		},'-','&nbsp;&nbsp;',{
            	text:'<B>查询</B>',
            	id:'btn', 
            	iconCls: 'btnSearch',
            	hidden:false,
            	handler : searchArea
            }
        ],bbar: new Ext.PagingToolbar({
            pageSize: pageSize, 
            store: menuStore, 
            displayInfo: true,
            displayMsg: '显示第 {0} 条到 {1} 条记录，共 {2} 条', emptyMsg: "没有记录信息显示"
        })
    });
      //查询
    function searchArea() {
    		menuStore.baseParams = {
				checkItems : Ext.get("checkItems").dom.value,
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
  //  	alert(parent.parent_name);
    	//var _records = menuGrid.getSelectionModel().getSelections();
		var form = new Ext.form.FormPanel({
					frame : true,
					border : false,
					bodyStyle : 'padding:5px 5px 5px',
					labelAlign : "right",
					labelWidth : 100,
					defaults : {
						xtype : "textfield",
						width : 230
					},
					width : 400,					
					items:[{
							xtype : 'combo',
							triggerAction : 'all',
							store : nodeStore,
							emptyText : "请选择操作节点名称",
							allowBlank : false,
							width:150,
							minChars : 1,
							queryParam:'filter_LIKES_outName',
							fieldLabel:'操作节点',
							displayField : 'outName',//显示值，与fields对应
							valueField : 'nodeOrder',//value值，与fields对应
							hiddenName : 'nodeId',
							id:'nodeOrderCombo',
							anchor : '95%',
							pageSize:20,
							enableKeyEvents:true,
							listeners : {
								'select':function(combo){

                        		 },
                        		 render:function(combo){
                        		 	 
                        		 }
						   	}
						   },
                           {xtype:'textfield',
                           	anchor : '95%',
                           	fieldLabel:'异常类型名称<span style="color:red">*</span>',
                           	id:'typeName2',
                           	name:'typeName',
                           	pageSize:20,
                           	allowBlank:false,
                           	blankText:'异常类型名称不能为空！',
                           	maxLength:20,
                           	enableKeyEvents:true,
							listeners : {
                        		 render:function(combo){
                  
                        		 }
						   	}
                           
                           },{
                           	xtype:'radiogroup',
                           	anchor : '85%',
                           	fieldLabel:'输入件数<span style="color:red">*</span>',
                           	items: [{
			                    inputValue:'1',
			                    boxLabel: '是',
			                    name:'isDoPiece',
			                    checked:true
			                }, {
			                    inputValue:'0',
			                    name:'isDoPiece',
			                    boxLabel: '否'
			                }]
                           },{
                           	xtype:'radiogroup',
                           	anchor : '85%',
                           	fieldLabel:'网营公开<span style="color:red">*</span>',
                           	items: [{
			                    inputValue:'1',
			                    boxLabel: '是',
			                    name:'isOpen',
			                    checked:true
			                }, {
			                    inputValue:'0',
			                    name:'isOpen',
			                    boxLabel: '否'
			                }]
                           },{
                           	xtype:'radiogroup',
                           	anchor : '85%',
                           	fieldLabel:'语音通知代理<span style="color:red">*</span>',
                           	items: [{
			                    inputValue:'1',
			                    boxLabel: '是',
			                    name:'isSmsAgency',
			                    checked:true
			                }, {
			                    inputValue:'0',
			                    name:'isSmsAgency',
			                    boxLabel: '否'
			                }]
                           },
                                   {
                           	xtype:'radiogroup',
                           	anchor : '85%',
                           	fieldLabel:'短信通知客户<span style="color:red">*</span>',
                           	items: [{
			                    inputValue:'1',
			                    boxLabel: '是',
			                    name:'isSmsCusd',
			                    checked:true
			                }, {
			                    inputValue:'0',
			                    name:'isSmsCusd',
			                    boxLabel: '否'
			                }]
                           },{
							labelAlign : 'top',
							anchor : '95%',
							xtype : 'textarea',
							name : 'dealFlow',
							fieldLabel : '对应处理措施',
							height : 45
						   },{
							labelAlign : 'top',
							anchor : '95%',
							xtype : 'textarea',
							name : 'remark',
							fieldLabel : '备注',
							height : 65
							},
						   {
							xtype : 'combo',
							triggerAction : 'all',
							store : departStore,
							emptyText : "请选择处理操作部门名称",
							allowBlank : true,
							pageSize:20,
							hidden:true,
							width:150,
							minChars : 1,
							queryParam:'filter_LIKES_departName',
						//	fieldLabel:'处理部门',
							displayField : 'departName',//显示值，与fields对应
							valueField : 'departId',//value值，与fields对应
							hiddenName : 'doDepartId',
							id:'doDepartCombo',
							anchor : '95%'
						   }
                    ]
				});
				
		var win = new Ext.Window({
			title : '添加异常类型',
			width : 400,
			height:403,
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
							url : sysPath + "/sys/exceptionTypeAction!save.action",
							params:{
								privilege:privilege ,
								nodeName:Ext.getCmp('nodeOrderCombo').getRawValue(),
								doDepartName:Ext.getCmp('doDepartCombo').getRawValue(),
								bussDepartId:bussDepart
							},
							waitMsg : '正在保存数据...',
							success : function(form, action) {
								win.hide(), 
								Ext.Msg.alert(alertTitle,action.result.msg);
								menuStore.reload({params : {
												start : 0,
												limit : pageSize
											}
											});
							},
							failure : function(form, action) {
								if (action.faliureType == Ext.form.Action.SERVER_INVALID) {
									Ext.Msg.alert(alertTitle, "服务器连接异常，请联系管理员！");
								} else {
									if (action.result.msg) {
										win.hide();
										Ext.Msg.alert(alertTitle,action.result.msg);
									}
								}
							}
						});
					}
				}
			}, {
				text : "重置",
				iconCls : 'refresh',
				handler : function() {
					form.getForm().reset();
				}
			}, {
				text : "取消",
				iconCls : 'groupClose',
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
		var form = new Ext.form.FormPanel({
					frame : true,
					border : false,
					bodyStyle : 'padding:5px 5px 5px',
					labelAlign : "right",
					labelWidth : 100,
					defaults : {
						xtype : "textfield",
						width : 230
					},
					
					width : 400,
					url : sysPath+ "/sys/exceptionTypeAction!findAll.action?filter_EQL_id="+ _record[0].data.id,
					reader : new Ext.data.JsonReader({
			            root: 'result', totalProperty: 'totalCount'
			        }, [
			            {name:'id'},
			            {name: 'nodeId'},
			            {name: 'nodeName'},
			            {name: 'isDoPiece',convert:function(v){
			            	if(v==0){
			            		return '否';
			            	}else{
			            		return '是';
			            	}
			            }},
			            {name: 'isOpen',convert:function(v){
			            	if(v==0){
			            		return '否';
			            	}else{
			            		return '是';
			            	}
			            }},
			            {name: 'isSmsAgency',convert:function(v){
			            	if(v==0){
			            		return '否';
			            	}else{
			            		return '是';
			            	}
			            }},
			            {name: 'isSmsCusd',convert:function(v){
			            	if(v==0){
			            		return '否';
			            	}else{
			            		return '是';
			            	}
			            }},
			            {name: 'typeName'},
			            {name: 'parentType'},
			            {name: 'dealFlow'},
			            {name: 'createName'},
			            {name: 'createTime'},
			            {name: 'updateName'},
			            {name: 'updateTime'},
			            {name: 'doDepartName'},
						{name: 'doDepartId'},
						{name: 'bussDepartId'},
			            {name: 'remark'},
			            {name: 'ts'}
			        ]),
					items:[{xtype:'hidden',name:'id',anchor : '95%'},
						   {xtype:'hidden',name:'ts',anchor : '95%'},
                           {
							xtype : 'combo',
							triggerAction : 'all',
							store : nodeStore,
							emptyText : "请选择操作节点名称",
							allowBlank : false,
							width:150,
							minChars : 1,
							queryParam:'filter_LIKES_outName',
							pageSize:20,
							fieldLabel:'节点名称',
							mode : "local",//获取本地的值
							displayField : 'outName',//显示值，与fields对应
							valueField : 'nodeOrder',//value值，与fields对应
							hiddenName : 'nodeId',
							id:'nodeOrderCombo',
							anchor : '95%',
							enableKeyEvents:true,
							listeners : {
								'select':function(combo){
								   
                        		 }
						   	}
						   },
                           {xtype:'textfield',
                           	anchor : '95%',
                           	fieldLabel:'异常类型名称<span style="color:red">*</span>',
                           	name:'typeName',
                           	id:'typeName2',
                           	
                           	allowBlank:false,
                           	blankText:'异常类型名称不能为空！',
                           	maxLength:20,
                           	listeners:{
                           		render:function(c){
                           		}
                           	}
                           },{
                           	xtype:'radiogroup',
                           	anchor : '85%',
                           	fieldLabel:'输入件数<span style="color:red">*</span>',
                           	items: [{
			                    inputValue:'1',
			                    boxLabel: '是',
			                    name:'isDoPiece',
			                   	checked:_record[0].data.isDoPiece=="是"?true:false
			                }, {
			                    inputValue:'0',
			                    name:'isDoPiece',
			                    boxLabel: '否',
			                    checked:_record[0].data.isDoPiece=="否"?true:false
			                }]
                           }
						   ,{
                           	xtype:'radiogroup',
                           	anchor : '95%',
                           	fieldLabel:'网营公开<span style="color:red">*</span>',
                           	id:'isOpen',
                           	items: [{
			                    inputValue:'1',
			                    boxLabel: '是',
			                    name:'isOpen',
			                    checked:_record[0].data.isOpen=="是"?true:false
			                }, {
			                    inputValue:'0',
			                    name:'isOpen',
			                    boxLabel: '否',
			                    checked:_record[0].data.isOpen=="否"?true:false
			                }]
                           },{
                           	xtype:'radiogroup',
                           	anchor : '95%',
                           	id:'isSmsAgency',
                           	fieldLabel:'语音通知代理<span style="color:red">*</span>',
                           	items: [{
			                    inputValue:'1',
			                    boxLabel: '是',
			                    name:'isSmsAgency',
			                    checked:_record[0].data.isSmsAgency=="是"?true:false
			                }, {
			                    inputValue:'0',
			                    name:'isSmsAgency',
			                    boxLabel: '否',
			                    checked:_record[0].data.isSmsAgency=="否"?true:false
			                }]
                           },
                                   {
                           	xtype:'radiogroup',
                           	anchor : '95%',
                           	id:'isSmsCusd',
                           	fieldLabel:'短信通知客户<span style="color:red">*</span>',
                           	items: [{
			                    inputValue:'1',
			                    boxLabel: '是',
			                    name:'isSmsCusd',
			                    checked:_record[0].data.isSmsCusd=="是"?true:false
			                }, {
			                    inputValue:'0',
			                    name:'isSmsCusd',
			                    boxLabel: '否',
			                    checked:_record[0].data.isSmsCusd=="否"?true:false
			                }]
                           },{
							labelAlign : 'top',
							anchor : '95%',
							xtype : 'textarea',
							name : 'dealFlow',
							fieldLabel : '对应处理措施',
							height : 45,
							value:_record[0].data.dealFlow
						   },{
							labelAlign : 'top',
							anchor : '95%',
							xtype : 'textarea',
							name : 'remark',
							maxLength:500,
							fieldLabel : '备注',
							height : 65,
							value:_record[0].data.remark
							},{
							xtype : 'combo',
							triggerAction : 'all',
							store : departStore,
							emptyText : "请选择处理操作部门名称",
							allowBlank:true,
							hidden:true,
							width:150,
							minChars : 1,
							queryParam:'filter_LIKES_departName',
							pageSize:20,
							//fieldLabel:'处理部门',
							displayField : 'departName',//显示值，与fields对应
							valueField : 'departId',//value值，与fields对应
							hiddenName : 'doDepartId',
							id:'doDepartCombo',
							anchor : '95%'
						   }
                    ]
				});
				departStore.load();
				form.load({
					url : sysPath+ "/sys/exceptionTypeAction!list.action",
					params:{filter_EQL_id:_record[0].data.id,limit : pageSize},
					success:function(){
						var _record = menuGrid.getSelectionModel().getSelections();
						Ext.getCmp("nodeOrderCombo").setValue(_record[0].data.nodeId);
	    		    	Ext.getCmp("nodeOrderCombo").setRawValue(_record[0].data.nodeName);
	    		     	Ext.getCmp("isSmsAgency").reset();
	    		     	Ext.getCmp("isSmsCusd").reset();
	    		     	Ext.getCmp("isOpen").reset();
                   		if(Ext.getCmp('typeName2').getValue()==Ext.getCmp('nodeOrderCombo').getRawValue()){
                   			Ext.getCmp('typeName2').disable();
                   		}
	    		     	
					}
				});
		
		var win = new Ext.Window({
			title : '修改异常类型',
			width : 400,
			height:403,
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
							url : sysPath + "/sys/exceptionTypeAction!save.action",
							params:{
								privilege:privilege
							},
							waitMsg : '正在保存数据...',
							success : function(form, action) {
								win.hide(), 
								Ext.Msg.alert(alertTitle,action.result.msg);
								menuStore.reload({params : {
												start : 0,
												limit : pageSize
											}
								});
							},
							failure : function(form, action) {
								if (action.faliureType == Ext.form.Action.SERVER_INVALID) {
									Ext.Msg.alert(alertTitle, "服务器连接异常，请联系管理员！");
								} else {
									if (action.result.msg) {
										win.hide();
										Ext.Msg.alert(alertTitle,action.result.msg);
									}
								}
							}
						});
					}
				}
			},  {
				text : "重置",
				iconCls : 'refresh',
				handler : function() {
					form.load({
					url : sysPath+ "/sys/exceptionTypeAction!findAll.action",
					params:{filter_EQL_id:_record[0].data.id,limit : pageSize},
					success:function(){
						var _record = menuGrid.getSelectionModel().getSelections();
						Ext.getCmp("nodeOrderCombo").setValue(_record[0].data.nodeId);
	    		    	Ext.getCmp("nodeOrderCombo").setRawValue(_record[0].data.nodeName);
	    		     	Ext.getCmp("isSmsAgency").reset();
	    		     	Ext.getCmp("isSmsCusd").reset();
	    		     	Ext.getCmp("isOpen").reset();
	    		     	
	    		     	
					}
					});
				}
			},{
				text : "取消",
				iconCls : 'groupClose',
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

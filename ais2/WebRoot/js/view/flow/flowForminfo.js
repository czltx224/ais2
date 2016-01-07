var popupWin;
var privilege=241;
var searchUrl="flow/flowForminfoAction!ralaList.action";//查询地址
var dictionaryUrl='sys/dictionaryAction!ralaList.action';//数据字典地址
var carTitle;
var fields=[
			{name:'id'},
            {name: 'objType'},
            {name:'objName'},
            {name: 'tableType'},
            {name: 'objDesc'},
            {name: 'objTablename'},
            {name: 'status'},
            {name: 'createName'},
            {name: 'createTime'},
            {name: 'updateName'},
            {name: 'updateTime'},
            {name:'ts'}
];
Ext.onReady(function() {
    var forminfoStore = new Ext.data.Store({
        storeId:"forminfoStore",
        baseParams:{privilege:privilege},
        proxy: new Ext.data.HttpProxy({url:sysPath+"/"+searchUrl}),
        reader: new Ext.data.JsonReader({
            root: 'result', totalProperty: 'totalCount'
        }, fields)
    });
	 //流程类型283
		flowTypeStore= new Ext.data.Store({ 
			storeId:"addressTypeStore",
			baseParams:{filter_EQL_basDictionaryId:283,privilege:16},
			proxy:new Ext.data.HttpProxy({url:sysPath+"/"+dictionaryUrl}),
			reader:new Ext.data.JsonReader({
	                    root:'result',
	                    totalProperty:'totalCount'
	        },[
        	{name:'id',mapping:'id'},
        	{name:'flowType',mapping:'typeName'}
        	])
		});
    //start
    var sm = new Ext.grid.CheckboxSelectionModel({});
    var rowNum = new Ext.grid.RowNumberer({
        header:'序号', width:35, sortable:true
    });
    
    var menuGrid = new Ext.grid.GridPanel({
        renderTo:'showView',
        height:Ext.lib.Dom.getViewHeight(), 
        width:Ext.lib.Dom.getViewWidth(),
        viewConfig : {
    		scrollOffset: 0,
			//forceFit : true,
			autoScroll:true
		},
		stripeRows : true,
		//autoExpandColumn:1,
        frame:true, 
        loadMask:true,
        sm:sm,
        cm:new Ext.grid.ColumnModel([rowNum,sm,
        	{header:'ID',dataIndex:"id",hidden: true, hideable: false},
            {header:'表单名称',dataIndex:"objName"},
            {header:'表单描述',dataIndex:"objDesc"},
            {header:'表单类型',dataIndex:"objType",width:80,renderer:function(v){
            	if(v==1){
            		return "实际表";
            	}else if(v==2){
					return "虚拟表";            	
            	}
            }},
            {header:'实际表类型',dataIndex:"tableType",width:80,renderer:function(v){
            	if(v==1){
            		return "主表";
            	}else if(v==2){
 	           		return "明细表";
            	}
            }},
            {header:"数据库表名",dataIndex:"objTablename",width:160},
           // {header:"流程类型",dataIndex:"contactWay",width:80},
            {header:"状态",dataIndex:"status",width:80,renderer:function(v){
            	if(v==1){
            		return "正常";
            	}else{
            		return "作废";
            	}
            }},
            {header:"创建人",dataIndex:"createName",width:80},
            {header:"创建时间",dataIndex:"createTime",width:80},
            {header:"更新人",dataIndex:"updateName",width:50,hidden:true},
            {header:"更新时间",dataIndex:"updateTime",width:50,hidden:true}
           
        ]),
        store:forminfoStore,
        tbar: [
        	/*{text:'<b>导出</b>',iconCls:'sort_down',handler:function() {
                	parent.exportExl(menuGrid);
        } },'-',*/
            {
                text:'<B>新增</B>', id:'addbtn',tooltip:'新增表单信息', iconCls: 'userAdd',handler:function() {
                	cusOper(null);
            } },
            '-',
            {
                text:'<B>修改</B>',id:'updatebtn',disabled:true, tooltip:'修改表单信息', iconCls: 'userEdit', handler: function() {
                var _record = menuGrid.getSelectionModel().getSelections();
                if (_record.length < 1) {
						parent.Ext.Msg.alert("系统消息", "请选择您要操作的行！");
						return false;
					} else if (_record.length > 1) {
						parent.Ext.Msg.alert("系统消息", "一次只能修改一行！");
						return false;
					}else{
						var recordId = _record[0].data.id;
						var objType = _record[0].data.objType;
						var href="";
						if(objType==1){
							href="/flow/flowForminfoAction!gotoUpdateForm.action?objType="+objType+"&formId="+recordId+"&tableName="+_record[0].data.objTablename;
						}else{
							href="/flow/flowForminfoAction!gotoUpdateForm.action?objType="+objType+"&formId="+recordId;
						}
						var node=new Ext.tree.TreeNode({id:'editFlowForm',leaf :false,text:'表单修改'});
				      	node.attributes={href1:href};
				        parent.toAddTabPage(node,true);
					}
            } } ,
            '-',
            {
                text:'<B>删除</B>',id:'deletebtn',disabled:true, tooltip:'删除表单信息', iconCls: 'userDelete',
                handler: function(){
                	var _records = menuGrid.getSelectionModel().getSelections();
                	if (_records.length < 1) {
						parent.Ext.Msg.alert(alertTitle, "请选择您要操作的行！");
						return false;
					} 
					Ext.Msg.confirm(alertTitle, "确定要删除所选的"+_records.length+"行记录吗？", function(btnYes) {
						if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
								Ext.Ajax.request({
								url : sysPath+ "/flow/flowForminfoAction!delete.action",
								params : {
									formId :_record[0].data.id,
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
            }
            /*,'-','流程类型:',
            {
            	xtype : 'combo',
				triggerAction : 'all',
				width : 120,
				typeAhead : true,
				queryParam : 'filter_LIKES_lineName',
				forceSelection : true,
				resizable:true,
				emptyText:'请选择',
				minChars : 0,
				store : flowTypeStore,
				mode:'remote',
				valueField : 'flowType',//value值，与fields对应
				displayField : 'flowType',//显示值，与fields对应
				hiddenName:'flowType',
				id:'searchFlowType'
            }*/
            ,'-',
            {
            	xtype : 'textfield',
    			id:'searchContent',
    			name : 'stationName',
    			width : 100,
    			enableKeyEvents:true,
    			listeners : {
			 		keyup:function(textField, e){
		                     if(e.getKey() == 13){
		                     	searchForminfo();
		                  }
			 		}
	 			}
            },
            '-',
            {
            	xtype : 'combo',
            	triggerAction : 'all',
    			model : 'local',
    			hiddenId : 'checkItems',
    			hiddenName : 'checkItems',
    			name : 'checkItemstext',
            	store: [
            			['', '查询全部'], 
    					['LIKES_objName', '表单名称'],
    					['LIKES_objTablename','数据库表名']
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
            	handler : searchForminfo
            }
        ],
        bbar: new Ext.PagingToolbar({
            pageSize: pageSize,
            privilege:privilege, 
            store: forminfoStore, 
            displayInfo: true,
            displayMsg: '显示第 {0} 条到 {1} 条记录，共 {2} 条', emptyMsg: "没有记录信息显示"
        })
    });
     function searchForminfo() {
		forminfoStore.proxy = new Ext.data.HttpProxy({
					method : 'POST',
					url : sysPath
								+ "/"+searchUrl,
						params:{privilege:privilege,limit : pageSize}
				});

		forminfoStore.baseParams = {
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
   		//var _records = menuGrid.getSelectionModel().getSelections();
		var form = new Ext.form.FormPanel({
					frame : true,
					border : false,
					bodyStyle : 'padding:5px 5px 5px',
					labelAlign : "left",
					labelWidth : 80,
					width : 400,
					labelAlign : "right",
					defaults : {
						xtype : "textfield",
						width : 230
					},
					items:[
						   {xtype:'hidden',name:'ts'},
                           {xtype:'textfield',name:'objName',fieldLabel:'表单名称<span style="color:red">*</span>'},
                           {
                           		xtype : 'combo',
    							typeAhead : true,
    							forceSelection : true,
    							fieldLabel : '表单类型<span style="color:red">*</span>',
    							store : [
    								['1','实际表'],
    								['2','虚拟表']
    							],
    							triggerAction : 'all',
    							id:'comboobjtype',
    							hiddenName : 'objType',
    							emptyText : "请选择",
    							allowBlank : false,
    							blankText : "表单类型不能为空！"
                           },
                           {
                           		xtype : 'combo',
    							typeAhead : true,
    							forceSelection : true,
    							id:'combotabletype',
    							fieldLabel : '实际表类型<span style="color:red">*</span>',
    							store : [
    								['1','主表'],
    								['2','明细表']
    							],
    							triggerAction : 'all',
    							hiddenName : 'tableType',
    							emptyText : "请选择"
                           },
                           {
                           		xtype:'textarea',
                           		name:'objDesc',
                           		fieldLabel:'表单描述',
                           		id:'objDesc',
                           		height:60
                           }
                    ]
				});
		var win = new Ext.Window({
			title : '添加表单信息',
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
							url : sysPath + "/flow/flowForminfoAction!save.action?privilege=241",
							waitMsg : '正在保存数据...',
							success : function(form, action) {
								win.hide(), 
								Ext.Msg.alert(alertTitle,'保存成功！');
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
	function menuStoreReload(){
		forminfoStore.reload({
			params:{
				start : 0,
				privilege:privilege,
				limit : pageSize
			}
		});
	}
    //end
});
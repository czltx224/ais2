var popupWin;
var privilege=244;
var searchUrl="flow/flowPipeinfoAction!ralaList.action";//查询地址
var dictionaryUrl='sys/dictionaryAction!ralaList.action';//数据字典地址
var carTitle,pipeinfoStore,flowTypeStore,forminfoStore;
var fields=[
			{name:'id'},
            {name: 'objName'},
            {name:'formName'},
            {name:'dicObjType'},
            {name: 'objType'},
            {name: 'formId'},
            {name: 'isCanautoflow'},
            {name: 'isRtx'},
            {name:'objDesc'},
            {name:'isDelete'},
            {name:'status'},
            {name: 'createName'},
            {name: 'createTime'},
            {name: 'updateName'},
            {name: 'updateTime'},
            {name:'ts'}
];
Ext.onReady(function() {
    pipeinfoStore = new Ext.data.Store({
        storeId:"forminfoStore",
        baseParams:{privilege:privilege,filter_EQL_status:1},
        proxy: new Ext.data.HttpProxy({url:sysPath+"/"+searchUrl}),
        reader: new Ext.data.JsonReader({
            root: 'result', totalProperty: 'totalCount'
        }, fields)
    });
	 //流程类型283
		flowTypeStore= new Ext.data.Store({ 
			storeId:"flowTypeStore",
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
	forminfoStore = new Ext.data.Store({
        storeId:"forminfoStore",
        baseParams:{privilege:241,filter_EQL_status:1},
        proxy: new Ext.data.HttpProxy({url:sysPath+"/flow/flowForminfoAction!ralaList.action"}),
        reader: new Ext.data.JsonReader({
            root: 'result', totalProperty: 'totalCount'
        }, [
        	{name:'id'},
        	{name:'objName'}
        ])
    });
    //start
    var sm = new Ext.grid.CheckboxSelectionModel({});
    var rowNum = new Ext.grid.RowNumberer({
        header:'序号', width:35, sortable:true
    });
     var cusOutTime=new Ext.menu.Menu({
     	//id:'stopflowbtn',
	 	items:[{text : '<B>启用流程</B>',
	    		handler :function(){ 
	    			Ext.Msg.confirm(alertTitle, "确定要启用流程吗？", function(btnYes) {
						if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
							var _records = menuGrid.getSelectionModel().getSelections();
	    					flowStopOrStart(_records,'start');
						}
					});
	    		}
	    	},'-',	
	    	{text : '<B>停用流程</B>',
	    		handler :function(){
	    			Ext.Msg.confirm(alertTitle, "确定要停用流程吗？", function(btnYes) {
						if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
							var _records = menuGrid.getSelectionModel().getSelections();
	    					flowStopOrStart(_records,'stop');
						}
					});
	    		}
	    	}]
	});
	var querytbar = new Ext.Toolbar({
				items : ['流程名称:',{
	            	xtype : 'textfield',
	    			id:'pipeName',
	    			name : 'pipeName',
	    			width : 120
	            },
	            '-','流程分类:',
	            {
	            	xtype : "combo",
					triggerAction : 'all',
					name:'flowType',
					id:'searchFlowType',
					store:flowTypeStore,
					valueField:'id',
					queryParam : 'filter_LIKES_typeName',
					triggerAction : 'all',
					pageSize : pageSize,
					forceSelection : true,
					minChars : 0,
					displayField:'flowType',
					width:120,
					model:'local'
	            },
	            '-','流程表单:',
	            {
	            	xtype : "combo",
					triggerAction : 'all',
					name:'forminfo',
					id:'searchForminfo',
					store:forminfoStore,
					queryParam : 'filter_LIKES_objName',
					triggerAction : 'all',
					pageSize : pageSize,
					forceSelection : true,
					minChars : 0,
					valueField:'id',
					displayField:'objName',
					width:120,
					model:'local'
	            },
	            '-','是否有效:',
	            {
	            	xtype : "combo",
					triggerAction : 'all',
					name:'searchIsDelete',
					id:'searchIsDelete',
					store:[
						['1','是'],
						['0','否']
					],
					width:60,
					model:'local'
	            },
	            '-',
	            {
	            	text:'<B>搜索</B>',
	            	id:'btn', 
	            	iconCls: 'btnSearch',
	            	hidden:false,
	            	handler : searchPipeinfo
	            }]
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
            {header:'流程名称',dataIndex:"objName"},
            {header:'流程分类ID',dataIndex:"objType",hidden: true, hideable: false},
            {header:'流程分类',dataIndex:"dicObjType"},
            {header:'流程表单ID',dataIndex:"formId",hidden: true, hideable: false},
            {header:'流程表单',dataIndex:"formName"},
            {header:'是否自动流转',dataIndex:"isCanautoflow",width:80,renderer:function(v){
            	if(v==1){
            		return "是";
            	}else if(v==0){
 	           		return "否";
            	}
            }},
            {header:'是否RTX提醒',dataIndex:"isRtx",width:80,renderer:function(v){
            	if(v==1){
            		return "是";
            	}else if(v==0){
 	           		return "否";
            	}
            }},
            {header:'是否停用',dataIndex:"isDelete",width:80,renderer:function(v){
            	if(v==0){
            		return "是";
            	}else if(v==1){
 	           		return "否";
            	}
            }},
            {header:"创建人",dataIndex:"createName",width:80},
            {header:"创建时间",dataIndex:"createTime",width:80},
            {header:"更新人",dataIndex:"updateName",width:50,hidden:true},
            {header:"更新时间",dataIndex:"updateTime",width:50,hidden:true}
           
        ]),
        store:pipeinfoStore,
        tbar: [
        	/*{text:'<b>导出</b>',iconCls:'sort_down',handler:function() {
                	parent.exportExl(menuGrid);
        } },'-',*/
            {
                text:'<B>新增</B>', id:'addbtn',tooltip:'新增流程信息', iconCls: 'userAdd',handler:function() {
                	cusOper(null);
            } },
            '-',
            {
                text:'<B>修改</B>',id:'updatebtn',disabled:true, tooltip:'修改流程信息', iconCls: 'userEdit', handler: function() {
                var _record = menuGrid.getSelectionModel().getSelections();
                if (_record.length < 1) {
						parent.Ext.Msg.alert("系统消息", "请选择您要操作的行！");
						return false;
					} else if (_record.length > 1) {
						parent.Ext.Msg.alert("系统消息", "一次只能修改一行！");
						return false;
					}else{
						var recordId = _record[0].data.id;
						var dicObjType=_record[0].data.dicObjType;
						var pipeName=_record[0].data.objName;
						var formId=_record[0].data.formId;
						var formName=_record[0].data.formName;
						var node=new Ext.tree.TreeNode({id:'editPipeInfo',leaf :false,text:'流程修改'});
				      	node.attributes={href1:"flow/flowPipeinfoAction!gotoUpdatePipe.action?flowId="+recordId+'&formName='+formName+'&dicObjType='+dicObjType+'&flowName='+pipeName+'&formId='+formId};
				        parent.toAddTabPage(node,true);
					}
            } } ,
            '-',
            	new Ext.Button({
   					text : '<B>流程停用/启用</B>',
   					tooltip : '停用/启用流程',
   					disabled:true,
   					id:'stopflowbtn',
   					menu: cusOutTime
   				}),
            	/*text:'<B>流程停用/启用</B>',id:'stopflowbtn',disabled:true, tooltip:'停用流程信息',
                menu:cusOutTime*/
            '-',
            {
                text:'<B>删除</B>',id:'deletebtn',disabled:true, tooltip:'删除流程信息', iconCls: 'userDelete',
                handler: function(){
                	var _records = menuGrid.getSelectionModel().getSelections();
                	if (_records.length < 1) {
						parent.Ext.Msg.alert(alertTitle, "请选择您要操作的行！");
						return false;
					} 
					Ext.Msg.confirm(alertTitle, "确定要删除所选的"+_records.length+"行记录吗？", function(btnYes) {
						if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
							Ext.Ajax.request({
								url : sysPath+ "/flow/flowPipeinfoAction!delete.action",
								params : {
									flowId :_record[0].data.id,
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
            ,'-',
            {
            	text:'<B>查看表单</B>',id:'showformbtn',disabled:true, tooltip:'查看表单信息',
                handler: function(){
                	var _record = menuGrid.getSelectionModel().getSelections();
                	var recordId = _record[0].data.formId;
					var node=new Ext.tree.TreeNode({id:'showFlowfrom',leaf :false,text:'表单修改'});
			      	node.attributes={href1:"/flow/flowForminfoAction!gotoUpdateForm.action?formId="+recordId};
			        parent.toAddTabPage(node,true);
                }
            },
            '-', 
            	{
            	text:'<B>查看流程图</B>',id:'showimgbtn',disabled:true, tooltip:'查看流程图信息',
                handler: function(){
                	var _record = menuGrid.getSelectionModel().getSelections();
                	var flowId = _record[0].data.id;
                	var formId = _record[0].data.formId;
                	var flowName=_record[0].data.objName;
                	var node=new Ext.tree.TreeNode({id:'editFlowlayout',leaf :false,text:'流程图设置'});
			      	node.attributes={href1:"/flow/flowPipeinfoAction!toFlowlayout.action?formId="+formId+"&flowId="+flowId+'&flowName='+flowName};
			        parent.toAddTabPage(node,true);
                }
            }
        ],
        bbar: new Ext.PagingToolbar({
            pageSize: pageSize,
            privilege:privilege, 
            store: pipeinfoStore, 
            displayInfo: true,
            displayMsg: '显示第 {0} 条到 {1} 条记录，共 {2} 条', emptyMsg: "没有记录信息显示"
        }),
        listeners:{
        	'render':function(){
        		querytbar.render(this.tbar);
        	}
        }
    });
       menuGrid.render();
     function searchPipeinfo() {
		var pName=Ext.getCmp('pipeName').getValue();
		var pType=Ext.getCmp('searchFlowType').getValue();
		var pForm=Ext.getCmp('searchForminfo').getValue();
		var pDelete=Ext.getCmp('searchIsDelete').getValue();
		pipeinfoStore.baseParams = {
			filter_LIKES_objName:pName,
			filter_LIKES_objType:pType,
			filter_EQL_formId:pForm,
			filter_EQL_isDelete:pDelete
		}
		var editbtn = Ext.getCmp('updatebtn');
		var deletebtn = Ext.getCmp('deletebtn');

		editbtn.setDisabled(true);
		deletebtn.setDisabled(true);
		menuStoreReload();
		
	}
     //查询
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
        var stopbtn=Ext.getCmp('stopflowbtn');
        var formbtn=Ext.getCmp('showformbtn');
        var imgbtn=Ext.getCmp('showimgbtn'); 
        if (_record.length==1) {       	
            if(updatebtn){
            	updatebtn.setDisabled(false);
            }
            if(deletebtn){
            	deletebtn.setDisabled(false);
            }
            if(stopbtn){
            	stopbtn.setDisabled(false);
            }
            if(formbtn){
            	formbtn.setDisabled(false);
            }
            if(imgbtn){
            	imgbtn.setDisabled(false);
            }
        } else {
            if(updatebtn){
            	updatebtn.setDisabled(true);
            }
			if(deletebtn){
            	deletebtn.setDisabled(true);
			}
			 if(stopbtn){
            	stopbtn.setDisabled(true);
            }
            if(formbtn){
            	formbtn.setDisabled(true);
            }
            if(imgbtn){
            	imgbtn.setDisabled(true);
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
						   {xtype:'hidden',name:'status',value:1},
						   {xtype:'hidden',name:'isDelete',value:1},
                           {xtype:'textfield',name:'objName',fieldLabel:'流程名称<span style="color:red">*</span>'},
                           {
                           		xtype : 'combo',
    							typeAhead : true,
    							forceSelection : true,
    							fieldLabel : '流程类型<span style="color:red">*</span>',
    							store :flowTypeStore,
    							triggerAction : 'all',
    							id:'comboflowType',
    							valueField:'id',
    							displayField:'flowType',
    							hiddenName : 'objType',
    							emptyText : "请选择",
    							queryParam : 'filter_LIKES_typeName',
								pageSize : pageSize,
								minChars : 0,
    							allowBlank : false,
    							blankText : "流程类型不能为空！"
                           },
                           {
                           		xtype : 'combo',
    							typeAhead : true,
    							forceSelection : true,
    							id:'comboforminfo',
    							fieldLabel : '流程表单<span style="color:red">*</span>',
    							store :forminfoStore,
    							valueField:'id',
    							displayField:'objName',
    							queryParam : 'filter_LIKES_objName',
								pageSize : pageSize,
								minChars : 0,
    							triggerAction : 'all',
    							hiddenName : 'formId',
    							emptyText : "请选择",
    							allowBlank:false,
    							blankText:'流程表单不能为空'
                           },
                           {
                           		xtype:'radiogroup',
	                           	id:'isCanautoflow',
	                           	fieldLabel:'自动流转<span style="color:red">*</span>',
	                           	items: [{
				                    inputValue:'1',
				                    boxLabel: '是',
				                    name:'isCanautoflow',
				                    checked:true
				                }, {
				                    inputValue:'0',
				                    name:'isCanautoflow',
				                    boxLabel: '否'
				                }]
                           },
                           {
                           		xtype:'radiogroup',
	                           	id:'isRtx',
	                           	fieldLabel:'RTX提醒<span style="color:red">*</span>',
	                           	items: [{
				                    inputValue:'1',
				                    boxLabel: '是',
				                    name:'isRtx',
				                    checked:true
				                }, {
				                    inputValue:'0',
				                    name:'isRtx',
				                    boxLabel: '否'
				                }]
                           },
                           {
                           		xtype:'textarea',
                           		name:'objDesc',
                           		fieldLabel:'流程备注',
                           		id:'objDesc',
                           		height:60
                           }
                    ]
				});
		var win = new Ext.Window({
			title : '添加流程信息',
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
							url : sysPath + "/flow/flowPipeinfoAction!save.action?privilege=244",
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
    //end
});
function menuStoreReload(){
	pipeinfoStore.reload({
		params:{
			start : 0,
			privilege:privilege,
			filter_EQL_status:1,
			limit : pageSize
		}
	});
}
//流程停用或者启用
function flowStopOrStart(_record,dType){
	Ext.Ajax.request({
		url : sysPath+ "/flow/flowPipeinfoAction!stopOrStartFlow.action",
		params : {
			formId :_record[0].data.id,
			dFlag:dType,
			privilege:privilege
		},
		success : function(resp) {
			var respText = Ext.util.JSON.decode(resp.responseText);
				Ext.Msg.alert(alertTitle,"删除成功<br/>");
				menuStoreReload();
		}
	});
}
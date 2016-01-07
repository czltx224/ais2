var popupWin;
var privilege=241;
var nodeprivilege=245;
var exportprivilege=246;
var searchUrl="flow/flowForminfoAction!ralaList.action";//查询地址
var dictionaryUrl='sys/dictionaryAction!ralaList.action';//数据字典地址
var nodeinfoStore,forminfoStore,fieldTypeStore,exportStore,exportPanel,nodeinfoPanel;
var pipefields=[
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
var nodeinfoFields=[
	{name:'id'},
	{name:'objName'},//节点名称
	{name:'pipeId'},//流程ID
	{name:'pipeName'},//流程名称
	{name:'nodeType'},//节点类型
	{name:'isReject'},//是否允许退回
	{name:'rejectnodeId'},//退回节点
	{name:'rejectnodeName'},//退回节点名称
	{name:'perPage'},//节点预处理页面
	{name:'afterPage'},//节点后处理页面
	{name:'isRtx'},//是否rtx提醒(0不提醒\1提醒)
	{name:'subBtnName'},//提交按钮名称
	{name:'saveBtnName'},//保存按钮名称
	{name:'isAutoflow'},//是否自动流转(0不自动流转\1自动流转)
	{name:'status'},
	{name: 'createName'},
    {name: 'createTime'},
    {name: 'updateName'},
    {name: 'updateTime'},
    {name:'ts'}
];
var exportFields=[
	{name:'id'},
	{name:'startnodeId'},//前一节点ID
	{name:'endnodeId'},//后一节点id 
	{name:'condition'},//出口条件
	{name:'conditionRemark'},//出口条件
	{name:'linkName'},//出口名称 
	{name:'pipeId'},//流程id
	{name:'linkFrom'},//开始连接点
	{name:'linkTo'},//结束连接点
	{name:'x1'},//图形坐标
	{name:'x2'},
	{name:'x3'},
	{name:'x4'},
	{name:'x5'},
	{name:'y1'},
	{name:'y2'},
	{name:'y3'},
	{name:'y4'},
	{name:'y5'},
	{name:'startnodeName'},//前一节点名称
	{name:'endnodeName'},//后一节点名称
	{name:'pipeName'},//流程名称
	{name:'status'},
	{name:'createTime'},
	{name:'createName'},
	{name:'updateTime'},
	{name:'updateName'},
	{name:'ts'}
];
Ext.onReady(function() {
    forminfoStore = new Ext.data.Store({
        storeId:"forminfoStore",
        baseParams:{privilege:privilege,filter_EQL_status:1},
        proxy: new Ext.data.HttpProxy({url:sysPath+"/"+searchUrl}),
        reader: new Ext.data.JsonReader({
            root: 'result', totalProperty: 'totalCount'
        },[
        	{name:'id'},
        	{name:'objName'}
        ])
    });
    //节点信息Store
	nodeinfoStore=new Ext.data.Store({
		storeId:"nodeinfoStore",
		baseParams:{privilege:nodeprivilege,filter_EQL_status:1,filter_EQL_pipeId:pipeId},
		proxy:new Ext.data.HttpProxy({url:sysPath+"/flow/flowNodeinfoAction!ralaList.action"}),
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },nodeinfoFields)
	});
	//节点信息Store
	nodeinfoStore1=new Ext.data.Store({
		storeId:"nodeinfoStore1",
		baseParams:{privilege:nodeprivilege,filter_EQL_status:1,filter_EQL_pipeId:pipeId},
		proxy:new Ext.data.HttpProxy({url:sysPath+"/flow/flowNodeinfoAction!ralaList.action"}),
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },nodeinfoFields)
	});
	nodeinfoStore1.load();
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
	//出口管理Store
	exportStore= new Ext.data.Store({ 
		storeId:"exportStore",
		baseParams:{filter_EQL_status:1,privilege:exportprivilege,filter_EQL_pipeId:pipeId},
		proxy:new Ext.data.HttpProxy({url:sysPath+"/flow/flowExportAction!ralaList.action"}),
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },exportFields)
	});
    //start
    var sm = new Ext.grid.CheckboxSelectionModel({});
    var rowNum = new Ext.grid.RowNumberer({
        header:'序号', width:35, sortable:true
    });
    var sm1 = new Ext.grid.CheckboxSelectionModel({});
    var rowNum1 = new Ext.grid.RowNumberer({
        header:'序号', width:35, sortable:true
    });
   var pipeinfotb=new Ext.Toolbar({
		width:Ext.lib.Dom.getViewWidth(),
		id:'pipeinfotb',
		items:['&nbsp;&nbsp;',{
    			text : '<B>保存</B>',
    			id : 'forminfosavebtn',
    			tooltip : '保存流程信息',
    			iconCls : 'save',
    			handler:function(){
    				if (pipeformpanel.getForm().isValid()) {
						pipeformpanel.getForm().submit({
							url : sysPath + "/flow/flowPipeinfoAction!save.action?privilege=244",
							waitMsg : '正在保存数据...',
							success : function(form, action) {
								Ext.Msg.alert(alertTitle,'保存成功！');
								pipeformpanel.getForm().reset();
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
    		}]
	});
   var pipeformpanel = new Ext.form.FormPanel({
   		id:'pipeformpanel',
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
		url : sysPath
		+ "/flow/flowPipeinfoAction!ralaList.action?filter_EQL_id="
		+ pipeId+'&privilege=244',
		reader : new Ext.data.JsonReader({
            root: 'result', totalProperty: 'totalCount'
        },pipefields),
        tbar:pipeinfotb,
		items:[
			   {xtype:'hidden',name:'id'},
			   {xtype:'hidden',name:'ts'},
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
	                    name:'isCanautoflow'
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
	                    name:'isRtx'
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
        ],listeners:{
        	'render':function(){
        		pipeformpanel.getForm().load({
        			success : function(_form, action) {
        				Ext.getCmp('comboflowType').setRawValue(dicObjType);
        				Ext.getCmp('comboforminfo').setRawValue(formName);
        				//alert(action.result.msg);
		    		    //parent.Ext.getCmp("upStationCombo").setValue(_record[0].data.stationId);
		    		   // parent.Ext.getCmp("upStationCombo").setRawValue(_record[0].data.stationName);
		    		    
	    			}
        		});
        	}
        }
	});
    nodeinfoPanel = new Ext.grid.GridPanel({
    	id:'nodeinfoPanel',
        height:Ext.lib.Dom.getViewHeight()-20, 
        width:Ext.lib.Dom.getViewWidth()-5,
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
            {header:'节点名称',dataIndex:"objName"},
            {header:'节点类型',dataIndex:"nodeType"},
            {header:'所属流程ID',dataIndex:"pipeId",hidden: true, hideable: false},
            {header:'所属流程',dataIndex:"pipeName"},
            {header:'是否允许退回',dataIndex:"isReject",width:80,renderer:function(v){
            	if(v==1){
            		return "是";
            	}else if(v==0){
            		return "否";	
            	}
            }},
            {header:'退回节点ID',dataIndex:"rejectnodeId",width:80,hidden: true, hideable: false},
            {header:'退回节点名称',dataIndex:"rejectnodeName",width:80},
            {header:"是否Rtx提醒",dataIndex:"isRtx",width:80,renderer:function(v){
            	if(v==1){
            		return "是";
            	}else if(v==0){
            		return "否";	
            	}
            }},
            {header:"是否自动流转",dataIndex:"isAutoflow",width:80,renderer:function(v){
            	if(v==1){
            		return "是";
            	}else if(v==0){
            		return "否";	
            	}
            }},
            {header:"提交按钮名称",dataIndex:"subBtnName"},
            {header:"保存按钮名称",dataIndex:"saveBtnName",width:80},
            {header:"创建人",dataIndex:"createName",width:80},
            {header:"创建时间",dataIndex:"createTime",width:80},
            {header:"更新人",dataIndex:"updateName",width:50,hidden:true},
            {header:"更新时间",dataIndex:"updateTime",width:50,hidden:true}
           
        ]),
        store:nodeinfoStore,
        tbar: [
        	/*{text:'<b>导出</b>',iconCls:'sort_down',handler:function() {
                	parent.exportExl(menuGrid);
        } },'-',*/
            {
                text:'<B>新增</B>', id:'nodeaddbtn',tooltip:'新增表单信息', iconCls: 'userAdd',handler:function() {
                	addNodeinfo(null,pipeId,pipeName,null);
            } },
            '-',
            {
            	text:'<B>节点操作者</B>', id:'nodeoperbtn',tooltip:'添加节点操作者', iconCls: 'userAdd',handler:function() {
                	var _record = nodeinfoPanel.getSelectionModel().getSelections();
                	setRalarule(_record[0].data.pipeId,_record[0].data.id);
            	
            }},
            '-',
            {
                text:'<B>修改</B>',id:'nodeupdatebtn',disabled:true, tooltip:'修改流程信息', iconCls: 'userEdit', handler: function() {
                var _record = nodeinfoPanel.getSelectionModel().getSelections();
                if (_record.length < 1) {
						Ext.Msg.alert("系统消息", "请选择您要操作的行！");
						return false;
					} else if (_record.length > 1) {
						Ext.Msg.alert("系统消息", "一次只能修改一行！");
						return false;
					}else{
						addNodeinfo(_record,pipeId,pipeName,null);
					}
            } } ,
            '-',
            {
                text:'<B>删除</B>',id:'nodedeletebtn',disabled:true, tooltip:'删除流程信息', iconCls: 'userDelete',
                handler: function(){
                	var _records = nodeinfoPanel.getSelectionModel().getSelections();
                	if (_records.length < 1) {
						parent.Ext.Msg.alert(alertTitle, "请选择您要操作的行！");
						return false;
					} 
					Ext.Msg.confirm(alertTitle, "确定要删除所选的"+_records.length+"行记录吗？", function(btnYes) {
						if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
								Ext.Ajax.request({
								url : sysPath+ "/flow/flowNodeinfoAction!delete.action",
								params : {
									nodeId : _records[0].data.id,
									privilege:privilege
								},
								success : function(resp) {
								var respText = Ext.util.JSON.decode(resp.responseText);
								Ext.Msg.alert(alertTitle,"删除成功<br/>");
								nodeinfoReload();
										}
									});
								}
							});
					
                }
            }
            ,/*'-',
            {
            	xtype : 'textfield',
    			id:'searchContent',
    			name : 'stationName',
    			width : 100,
    			enableKeyEvents:true,
    			listeners : {
			 		keyup:function(textField, e){
		                     if(e.getKey() == 13){
		                     	formfieldSerach();
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
    					['LIKES_labelName','显示名称']
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
            },*/
            '-',
            {
            	text:'<B>搜索</B>',
            	id:'searchfieldbtn', 
            	iconCls: 'btnSearch',
            	hidden:false,
            	handler : nodeinfoSerach
            }
        ],
        bbar: new Ext.PagingToolbar({
            pageSize: pageSize,
            privilege:nodeprivilege, 
            store: nodeinfoStore, 
            displayInfo: true,
            displayMsg: '显示第 {0} 条到 {1} 条记录，共 {2} 条', emptyMsg: "没有记录信息显示"
        })
    });
	nodeinfoPanel.on('click', function() {
        var _record = nodeinfoPanel.getSelectionModel().getSelections();
        var nodeoperbtn = Ext.getCmp('nodeoperbtn');
        var nodeupdatebtn = Ext.getCmp('nodeupdatebtn');
        var nodedelbtn= Ext.getCmp('nodedeletebtn');
        if (_record.length==1) {       	
            if(nodeoperbtn){
            	nodeoperbtn.setDisabled(false);
            }
            if(nodeupdatebtn){
            	nodeupdatebtn.setDisabled(false);
            }
            if(nodedelbtn){
            	nodedelbtn.setDisabled(false);
            }
        } else {
            if(nodeoperbtn){
            	nodeoperbtn.setDisabled(true);
            }
			if(nodeupdatebtn){
            	nodeupdatebtn.setDisabled(true);
			}
			if(nodedelbtn){
            	nodedelbtn.setDisabled(true);
            }
        }
    });
    exportPanel = new Ext.grid.EditorGridPanel({
    	id:'exportPanel',
        height:Ext.lib.Dom.getViewHeight()-20, 
        width:Ext.lib.Dom.getViewWidth()-5,
        viewConfig : {
    		scrollOffset: 0,
			//forceFit : true,
			autoScroll:true
		},
		stripeRows : true,
		//autoExpandColumn:1,
        frame:true, 
        loadMask:true,
        clicksToEdit :'auto',
        sm:sm1,
        cm:new Ext.grid.ColumnModel([rowNum1,sm1,
        	{header:'编号',dataIndex:"id",hidden: true, hideable: false},
        	{header:'出口名称',dataIndex:"linkName",editor:new Ext.form.TextField({
        		name:'linkName',
        		id:'linkName'
        	})},
        	{header:'前一节点ID',dataIndex:"startnodeId",hidden: true, hideable: false},
            {header:'当前节点',dataIndex:"startnodeName",width:200},
            {header:'目标节点ID',dataIndex:"endnodeId",hidden: true, hideable: false},
            {header:'目标节点',dataIndex:"endnodeName",width:200,editor: new Ext.form.ComboBox({
				typeAhead : true,
				forceSelection : true,
				triggerAction : 'all',
				mode:'local',
				resizable : true,
				queryParam :'filter_LIKES_objName',
				minChars:0,
				displayField:'objName',
//				store:new Ext.data.Store({
//					storeId:"nodeinfoStore",
//					baseParams:{privilege:nodeprivilege,filter_EQL_status:1,filter_EQL_pipeId:pipeId},
//					proxy:new Ext.data.HttpProxy({url:sysPath+"/flow/flowNodeinfoAction!ralaList.action"}),
//					reader:new Ext.data.JsonReader({
//			                    root:'result',
//			                    totalProperty:'totalCount'
//			        },nodeinfoFields)
//				}),
				store:nodeinfoStore1,
				valueField:'id',
				emptyText:'请选择',
				listeners:{
					'select':function(combo){
						var rd = exportPanel.getSelectionModel().getSelected();
						rd.set('endnodeId',combo.getValue());
						combo.setValue(combo.getRawValue());
						rd.set('endnodeName',combo.getRawValue());
					}	
				}
			})},
            {header:'所属流程ID',dataIndex:"pipeId",hidden: true, hideable: false},
            {header:'所属流程',dataIndex:"pipeName"},
            {header:'出口条件',dataIndex:"condition",width:200,editor:new Ext.form.TextField({
        		name:'condition',
        		id:'condition'
        	})},
        	{header:'条件备注',dataIndex:"conditionRemark",width:200,editor:new Ext.form.TextField({
        		name:'conditionRemark',
        		id:'conditionRemark'
        	})},
            {header:"创建人",dataIndex:"createName",width:80},
            {header:"创建时间",dataIndex:"createTime",width:80},
            {header:"更新人",dataIndex:"updateName",width:50,hidden:true},
            {header:"更新时间",dataIndex:"updateTime",width:50,hidden:true}
           
        ]),
        store:exportStore,
        tbar: [
        	/*{text:'<b>导出</b>',iconCls:'sort_down',handler:function() {
                	parent.exportExl(menuGrid);
        } },'-',*/
        	{
        		xtype:'combo',
        		name:'curNode',
        		id:'combocurNode',
        		emptyText:'请选择当前节点',
        		store:nodeinfoStore1,
        		triggerAction : 'all',
        		minChars:0,
        		queryParam : 'filter_LIKES_objName',
        		typeAhead : true,
				forceSelection : true,
        		displayField:'objName',
        		valueField:'id',
        		width:200
        	},
            {
                text:'<B>新增</B>', id:'exportaddbtn',tooltip:'新增表单信息', iconCls: 'userAdd',handler:function() {
                	addExport(null);
            } },
            '-',
            {
                text:'<B>删除</B>',id:'exportdeletebtn',disabled:true, tooltip:'删除表单信息', iconCls: 'userDelete',
                handler: function(){
                	var _records = exportPanel.getSelectionModel().getSelections();
                	if (_records.length < 1) {
						parent.Ext.Msg.alert(alertTitle, "请选择您要操作的行！");
						return false;
					} 
					Ext.Msg.confirm(alertTitle, "确定要删除所选的"+_records.length+"行记录吗？", function(btnYes) {
						if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
							var id=_records[0].data.id;
							if(id == null){
								exportStore.remove(_records);
							}else{
								Ext.Ajax.request({
									url : sysPath+ "/flow/flowExportAction!delete.action",
									params : {
										exportId : id,
										privilege:privilege
									},
									success : function(resp) {
										var respText = Ext.util.JSON.decode(resp.responseText);
										Ext.Msg.alert(alertTitle,"删除成功<br/>");
										exportSerach();
									}
								});
							}
								
						}
					});
					
                }
            }
            ,
            {
            	text:'<B>保存</B>', id:'exportsavebtn',tooltip:'保存出口信息', iconCls: 'save',handler:function() {
            		var records= exportStore.getModifiedRecords();
					if(records.length>0){
						Ext.Msg.confirm(alertTitle, "确定要调整这" + records.length + "条记录吗？", function(
						btnYes) {
						if (btnYes == 'yes' || btnYes == 'ok' || btnYes == true) {
								saveExport(records);
							}
						});
					}else{
						Ext.Msg.alert(alertTitle, "您没有做任何改动,不需调整！");
					}
            	} 
            },
            '-',
            /*'-',
            {
            	xtype : 'textfield',
    			id:'searchContent',
    			name : 'stationName',
    			width : 100,
    			enableKeyEvents:true,
    			listeners : {
			 		keyup:function(textField, e){
		                     if(e.getKey() == 13){
		                     	formfieldSerach();
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
    					['LIKES_labelName','显示名称']
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
            },*/
            '-',
            {
            	text:'<B>搜索</B>',
            	id:'searchexportbtn', 
            	iconCls: 'btnSearch',
            	hidden:false,
            	handler : exportSerach
            }
        ],
        bbar: new Ext.PagingToolbar({
            pageSize: pageSize,
            privilege:exportprivilege, 
            store: exportStore, 
            displayInfo: true,
            displayMsg: '显示第 {0} 条到 {1} 条记录，共 {2} 条', emptyMsg: "没有记录信息显示"
        })
    });
	exportPanel.on('click', function() {
        var _record = exportPanel.getSelectionModel().getSelections();
        //var nodeoperbtn = Ext.getCmp('exportaddbtn');
       // var nodeupdatebtn = Ext.getCmp('exportupdatebtn');
        var nodedelbtn= Ext.getCmp('exportdeletebtn');
        if (_record.length==1) {       	
//            if(nodeupdatebtn){
//            	nodeupdatebtn.setDisabled(false);
//            }
            if(nodedelbtn){
            	nodedelbtn.setDisabled(false);
            }
        } else {
//			if(nodeupdatebtn){
//            	nodeupdatebtn.setDisabled(true);
//			}
			if(nodedelbtn){
            	nodedelbtn.setDisabled(true);
            }
        }
    });
    /*
	formlinkPanel = new Ext.grid.GridPanel({
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
        	{header:'虚拟表ID',dataIndex:"pId",hidden: true, hideable: false},
            {header:'虚拟表名称',dataIndex:"PName"},
            {header:'实际表ID',dataIndex:"oId",hidden: true, hideable: false},
            {header:'实际表名称',dataIndex:"OName"},
            {header:"创建人",dataIndex:"createName",width:80},
            {header:"创建时间",dataIndex:"createTime",width:80},
            {header:"更新人",dataIndex:"updateName",width:50,hidden:true},
            {header:"更新时间",dataIndex:"updateTime",width:50,hidden:true}
           
        ]),
        store:formlinkStore,
        tbar: [
            {
                text:'<B>新增</B>', id:'linkaddbtn',tooltip:'新增表单关联', iconCls: 'userAdd',handler:function() {
                	addFormLink(null);
            } },
            '-',
            {
                text:'<B>修改</B>',id:'linkupdatebtn',disabled:true, tooltip:'修改表单关联', iconCls: 'userEdit', handler: function() {
                var _record = formfieldPanel.getSelectionModel().getSelections();
                if (_record.length < 1) {
						Ext.Msg.alert("系统消息", "请选择您要操作的行！");
						return false;
					} else if (_record.length > 1) {
						Ext.Msg.alert("系统消息", "一次只能修改一行！");
						return false;
					}else{
						addFormLink(_record);
					}
            } } ,
            '-',
            {
                text:'<B>删除</B>',id:'linkdeletebtn',disabled:true, tooltip:'删除表单信息', iconCls: 'userDelete',
                handler: function(){
                	var _records = formfieldPanel.getSelectionModel().getSelections();
                	if (_records.length < 1) {
						parent.Ext.Msg.alert(alertTitle, "请选择您要操作的行！");
						return false;
					} 
					Ext.Msg.confirm(alertTitle, "确定要删除所选的"+_records.length+"行记录吗？", function(btnYes) {
						if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
								Ext.Ajax.request({
								url : sysPath+ "/flow/flowFormlinkAction!delete.action",
								params : {
									linkId : _records[0].data.id,
									privilege:privilege
								},
								success : function(resp) {
								var respText = Ext.util.JSON.decode(resp.responseText);
								Ext.Msg.alert(alertTitle,"删除成功<br/>");
								formlinkSerach();
										}
									});
								}
							});
					
                }
            }/*
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
    					['LIKES_labelName','显示名称']
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
            }*//*,
            '-',
            {
            	text:'<B>搜索</B>',
            	id:'searchfieldbtn', 
            	iconCls: 'btnSearch',
            	hidden:false,
            	handler : formlinkSerach
            }
        ],
        bbar: new Ext.PagingToolbar({
            pageSize: pageSize,
            privilege:privilege, 
            store: formfieldStore, 
            displayInfo: true,
            displayMsg: '显示第 {0} 条到 {1} 条记录，共 {2} 条', emptyMsg: "没有记录信息显示"
        })
    });
    formlinkPanel.on('click', function() {
        var _record = formfieldPanel.getSelectionModel().getSelections();
        var updatebtn = Ext.getCmp('linkupdatebtn');
        var deletebtn = Ext.getCmp('linkdeletebtn');
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
    });*/
    var tablePanel=new Ext.TabPanel({
	   	renderTo:Ext.getBody(),
	    id: "mainTab",   
	    activeTab: 0,
	    width:Ext.lib.Dom.getViewWidth(),
	    height:Ext.lib.Dom.getViewHeight(),
	    defaults: {   
	        autoScroll: true,   
	        autoHeight:true,   
	        style: "padding:0 0 0 0"
	    },
	    items:[   
	       {title:"流程信息", id:'tabpipeinfo',tabTip:"流程信息管理",items:[pipeformpanel]},
	       {title:"节点管理",id:'tabnodeinfo',tabTip:'节点管理',items:[nodeinfoPanel]},
	       {title:"出口管理",id:'tabformlink',tabTip:'出口管理',items:[exportPanel]}
		]
	});
	
	/*
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
    function cusOper(_record) {
   		//var _records = menuGrid.getSelectionModel().getSelections();
		
	}
	function menuStoreReload(){
		forminfoStore.reload({
			params:{
				start : 0,
				privilege:privilege,
				limit : pageSize
			}
		});
	}*/
    //end
});
/**
 * 添加节点信息
 * @param {} _record
 */

/**
 * 表单字段Store重新加载
 */
function nodeinfoReload(){
	nodeinfoStore.reload({
		params:{
			privilege:nodeprivilege,
			start : 0,
			limit : pageSize
		}
	});
}
/**
 * 节点信息查询
 */
function nodeinfoSerach(){
	nodeinfoStore.baseParams = {
		filter_EQL_status:1,
		filter_EQL_pipeId:pipeId
	}
	var editbtn = Ext.getCmp('nodeupdatebtn');
	var deletebtn = Ext.getCmp('nodedeletebtn');
	var nodeoperbtn = Ext.getCmp('nodeoperbtn');

	editbtn.setDisabled(true);
	deletebtn.setDisabled(true);
	nodeoperbtn.setDisabled(true);
	nodeinfoReload();
}
/**
 * 表单关联查询
 */
function exportSerach(){
	exportStore.load({
		params:{
			limit:50,
			start:0
		}
	});
}
//添加出口信息
function addExport(){
	 var curNodeId=Ext.getCmp('combocurNode').getValue();
	 if(curNodeId == ''){
	 	Ext.Msg.alert(alertTitle,'请选择当前节点再新增！');
	 	
	 }else{
	 	var store=new Ext.data.Record.create(exportFields);
		 var noFaxRecord=new store();
		 noFaxRecord.set("startnodeId",curNodeId);
		 noFaxRecord.set("startnodeName",Ext.get('combocurNode').dom.value);
		 noFaxRecord.set("pipeId",pipeId);
		 noFaxRecord.set("pipeName",pipeName);
		 noFaxRecord.set("createName",userName);
		 noFaxRecord.set("createTime",new Date().format('Y-m-d'));
		 exportStore.add(noFaxRecord);
		 
	 }
}

//保存出口信息
function saveExport(records){
	var datas="";
	for(var i=0;i<records.length;i++){
		var id=records[i].data.id;
		var endnodeId=records[i].data.endnodeId;
		var linkName=records[i].data.linkName;
		var pipeId=records[i].data.pipeId;
		var condition=records[i].data.condition;
		var conditionRemark=records[i].data.conditionRemark;
		if(endnodeId==null || linkName == null ||(condition!=null && conditionRemark == null)){
			Ext.Msg.alert(alertTitle,'目标节点或者出口名字以及出口条件备注都不能为空!');
			return false;
		}
		if(id == null){
			id='';
		}
		if(condition == null){
			condition = '';
		}
		if(conditionRemark == null || conditionRemark.trim() == 'undefined'){
			conditionRemark = '';
		}
		if(i>0)
		{
			datas+='&';
		}
		datas+="exportList["+i+"].id="+id+'&'
				+"exportList["+i+"].endnodeId="+endnodeId+'&'
				+"exportList["+i+"].startnodeId="+records[i].data.startnodeId+'&'
				+"exportList["+i+"].linkName="+records[i].data.linkName+'&'
				+"exportList["+i+"].pipeId="+records[i].data.pipeId+'&'
				+"exportList["+i+"].conditionRemark="+conditionRemark+'&'
				+"exportList["+i+"].condition="+condition;
	}
	Ext.Ajax.request({
		url : sysPath+'/flow/flowExportAction!save.action',
		params :datas,
		method:'post',
		success : function(resp) {
			var respText = Ext.util.JSON.decode(resp.responseText);
			if(respText.success){
				Ext.Msg.alert(alertTitle,respText.msg);
				exportStore.reload();
				exportStore.commitChanges();
			}else{
				Ext.Msg.alert(alertTitle,respText.msg);
			}
		}
	});
}
var popupWin;
var privilege=241;
var searchUrl="flow/flowForminfoAction!ralaList.action";//查询地址
var dictionaryUrl='sys/dictionaryAction!ralaList.action';//数据字典地址
var formfieldTitle,formlinkTitle,formfieldStore,forminfoStore,fieldTypeStore,formlinkPanel,formlinkStore;
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
var formFieldFields=[
	{name:'id'},
	{name:'formId'},
	{name:'fieldName'},
	{name:'fieldTypeName'},
	{name:'htmlType'},
	{name:'fieldType'},
	{name:'fieldAttr'},
	{name:'fieldCheck'},
	{name:'labelName'},
	{name:'orderFields'},
	{name:'status'},
	{name: 'createName'},
    {name: 'createTime'},
    {name: 'updateName'},
    {name: 'updateTime'},
    {name:'ts'}
];
var formlinkFields=[
	{name:'id',mapping:'id'},
	{name:'pid'},
	{name:'oid'},
	{name:'status'},
	{name:'createTime'},
	{name:'createName'},
	{name:'updateTime'},
	{name:'updateName'},
	{name:'ts'},
	{name:'OName'},
	{name:'PName'}
];
Ext.onReady(function() {
    forminfoStore = new Ext.data.Store({
        storeId:"forminfoStore",
        baseParams:{privilege:privilege,filter_EQL_objType:1},
        proxy: new Ext.data.HttpProxy({url:sysPath+"/"+searchUrl}),
        reader: new Ext.data.JsonReader({
            root: 'result', totalProperty: 'totalCount'
        }, fields)
    });
    //表单字段Store
	formfieldStore=new Ext.data.Store({
		storeId:"formfieldStore",
		baseParams:{privilege:242,filter_EQL_status:1,filter_EQL_formId:formId},
		proxy:new Ext.data.HttpProxy({url:sysPath+"/flow/flowFormfieldAction!ralaList.action"}),
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },formFieldFields)
	});
	//表单关系Store
	formlinkStore= new Ext.data.Store({ 
		storeId:"formlinkStore",
		baseParams:{filter_EQL_status:1,privilege:243},
		proxy:new Ext.data.HttpProxy({url:sysPath+"/flow/flowFormlinkAction!ralaList.action"}),
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },formlinkFields)
	});
	fieldTypeStore=new Ext.data.Store({
		storeId:'fieldTypeStore',
		baseParams:{ralasafe:true},
		proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/dictionaryAction!list.action"}),
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
        	{name:'id'},
        	{name:'fieldType',mapping:'name'}
        	])
	});
    //start
    var sm = new Ext.grid.CheckboxSelectionModel({});
    var rowNum = new Ext.grid.RowNumberer({
        header:'序号', width:35, sortable:true
    });
   var forminfotb=new Ext.Toolbar({
		width:Ext.lib.Dom.getViewWidth(),
		id:'faxToolbar',
		items:['&nbsp;&nbsp;',{
    			text : '<B>保存</B>',
    			id : 'forminfosavebtn',
    			tooltip : '保存表单信息',
    			iconCls : 'save',
    			handler:function(){
    				if (forminfoPanel.getForm().isValid()) {
						forminfoPanel.getForm().submit({
							url : sysPath + "/flow/flowForminfoAction!save.action?privilege=241",
							waitMsg : '正在保存数据...',
							success : function(form, action) {
								Ext.Msg.alert(alertTitle,'保存成功！');
								forminfoPanel.getForm().reset();
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
   var forminfoPanel = new Ext.form.FormPanel({
   		id:'forminfoPanel',
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
		+ "/flow/flowForminfoAction!ralaList.action?filter_EQL_id="
		+ formId+'&privilege='+privilege,
		reader : new Ext.data.JsonReader({
            root: 'result', totalProperty: 'totalCount'
        }, [
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
        ]),
		tbar: forminfotb,
		items:[
			   {xtype:'hidden',name:'ts'},
			   {xtype:'hidden',name:'id',id:'id'},
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
               		xtype:'textfield',
               		name:'objTablename',
               		id:'objTablename',
               		fieldLabel:'数据库表名',
               		readOnly:true
               },
               {
               		xtype:'textarea',
               		name:'objDesc',
               		fieldLabel:'表单描述',
               		id:'objDesc',
               		height:60
               }
        ],
        listeners:{
        	'render':function(){
        		forminfoPanel.getForm().load();
        	}
        }
	});
    var formfieldPanel = new Ext.grid.GridPanel({
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
            {header:'字段名称',dataIndex:"fieldName"},
            {header:'字段类型',dataIndex:"htmlType",renderer:function(v){
            	if(v==1){
            		return '单行文本';
            	}else if(v==2){
            		return '整数';
            	}else if(v==3){
            		return '浮点数';
            	}else if(v==4){
					return '数据字典';            	
            	}
            }},
            {header:'表现形式ID',dataIndex:"objType",hidden: true, hideable: false},
            {header:'表现形式',dataIndex:"fieldTypeName",width:80},
            {header:'文本长度',dataIndex:"fieldAttr",width:80},
            {header:"字段验证",dataIndex:"fieldCheck",width:80},
            {header:"显示名称",dataIndex:"labelName",width:80},
            {header:"状态",dataIndex:"status",width:80,renderer:function(v){
            	if(v==1){
            		return "正常";
            	}else{
            		return "作废";
            	}
            }},
            {header:"排序顺序",dataIndex:"orderFields",width:80},
            {header:"创建人",dataIndex:"createName",width:80},
            {header:"创建时间",dataIndex:"createTime",width:80},
            {header:"更新人",dataIndex:"updateName",width:50,hidden:true},
            {header:"更新时间",dataIndex:"updateTime",width:50,hidden:true}
           
        ]),
        store:formfieldStore,
        tbar: [
        	/*{text:'<b>导出</b>',iconCls:'sort_down',handler:function() {
                	parent.exportExl(menuGrid);
        } },'-',*/
            {
                text:'<B>新增</B>', id:'addbtn',tooltip:'新增表单信息', iconCls: 'userAdd',handler:function() {
                	addFormField(null);
            } },
            '-',
            {
                text:'<B>修改</B>',id:'updatebtn',disabled:true, tooltip:'修改表单信息', iconCls: 'userEdit', handler: function() {
                var _record = formfieldPanel.getSelectionModel().getSelections();
                if (_record.length < 1) {
						Ext.Msg.alert("系统消息", "请选择您要操作的行！");
						return false;
					} else if (_record.length > 1) {
						Ext.Msg.alert("系统消息", "一次只能修改一行！");
						return false;
					}else{
						addFormField(_record);
					}
            } } ,
            '-',
            {
                text:'<B>删除</B>',id:'deletebtn',disabled:true, tooltip:'删除表单信息', iconCls: 'userDelete',
                handler: function(){
                	var _records = formfieldPanel.getSelectionModel().getSelections();
                	if (_records.length < 1) {
						parent.Ext.Msg.alert(alertTitle, "请选择您要操作的行！");
						return false;
					} 
					Ext.Msg.confirm(alertTitle, "确定要删除所选的"+_records.length+"行记录吗？", function(btnYes) {
						if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
								Ext.Ajax.request({
								url : sysPath+ "/flow/flowFormfieldAction!delete.action",
								params : {
									fieldId : _records[0].data.id,
									privilege:privilege
								},
								success : function(resp) {
								var respText = Ext.util.JSON.decode(resp.responseText);
								Ext.Msg.alert(alertTitle,"删除成功<br/>");
								formfiedReload();
										}
									});
								}
							});
					
                }
            }
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
            },
            '-',
            {
            	text:'<B>搜索</B>',
            	id:'searchfieldbtn', 
            	iconCls: 'btnSearch',
            	hidden:false,
            	handler : formfieldSerach
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
	formfieldPanel.on('click', function() {
        var _record = formfieldPanel.getSelectionModel().getSelections();
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
            }*/,
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
    });
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
	       {title:"表单信息", id:'tabforminfo',tabTip:"表单信息管理",items:[forminfoPanel]},
	       {title:"字段管理",id:'tabformfield',tabTip:'表单字段管理',items:[formfieldPanel]},
	       {title:"表单关系",id:'tabformlink',tabTip:'表单之间关系建立',items:[formlinkPanel]}
		],
		listeners:{
			'render':function(tabPanel){
				Ext.Ajax.request({
					url : sysPath+ "/flow/flowForminfoAction!ralaList.action",
					params : {
						filter_EQL_id :formId,
						privilege:241
					},
					success : function(resp) {
						var respText = Ext.util.JSON.decode(resp.responseText);
						var objT=respText.result[0].objType;
						if(objT==1){
							tabPanel.hideTabStripItem('tabformlink');
						}else{
							tabPanel.hideTabStripItem('tabformfield');
						}
					}
				});
			}
		}
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
 * 添加表单字段
 * @param {} _record
 */
function addFormField(_record){
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
		reader : new Ext.data.JsonReader({
            root: 'result', totalProperty: 'totalCount'
        },formFieldFields),
		items:[
			   {xtype:'hidden',name:'ts'},
			   {xtype:'hidden',name:'formId',id:'formId',value:formId},
			   {xtype:'hidden',name:'tableName',id:'tableName',value:tableName},
			   {xtype:'hidden',name:'id',id:'id'},
               {
               		xtype : 'combo',
					typeAhead : true,
					forceSelection : true,
					fieldLabel : '字段类型<span style="color:red">*</span>',
					store : [
						['1','单行文本'],
						['2','整数'],
						['3','浮点数'],
						['4','数据字典']
					],
					triggerAction : 'all',
					id:'combohtmltype',
					hiddenName : 'htmlType',
					emptyText : "请选择",
					blankText : '字段类型不能为空！',
					allowBlank : false,
					listeners:{
						'select':function(combo){
							if(combo.getValue()=='1'){
								Ext.getCmp('fieldAttr').setDisabled(false);
								Ext.getCmp('combofieldType').setDisabled(true);
							}else if(combo.getValue()=='4'){
								Ext.getCmp('combofieldType').setDisabled(false);
								Ext.getCmp('fieldAttr').setDisabled(true);
							}else{
								Ext.getCmp('fieldAttr').setDisabled(true);
								Ext.getCmp('combofieldType').setDisabled(true);
							}
						}
					
					}
               },
               {
               		xtype :'combo',
					triggerAction : 'all',
					hiddenName:'fieldType',
					id:'combofieldType',
					store:fieldTypeStore,
					valueField:'id',
					displayField:'fieldType',
					minChars : 1,
					queryParam : 'filter_LIKES_name',
					pageSize:pageSize,
					model:'local',
					disabled:true,
					emptyText:'请选择',
					fieldLabel : '表现形式'
               },
               {
               		xtype:'numberfield',
               		name:'fieldAttr',
               		disabled:true,
               		id:'fieldAttr',
               		fieldLabel:'文本长度',
               		maxValue :5000,
               		minValue:1
               },
               {
               		xtype:'textfield',
               		name:'labelName',
               		fieldLabel:'显示名称',
               		id:'labelName',
               		maxLength:100,
					maxLengthText:'长度不能超过50个汉字'
               },
               {
               		xtype:'numberfield',
               		name:'orderFields',
               		id:'orderFields',
               		fieldLabel:'排序顺序',
               		maxLength:9,
					maxLengthText:'长度不能超过9个数字'
               }
        ]
	});
	formfieldTitle="新增表单字段";
	if(_record!=null){
		formfieldTitle="修改表单字段";
		Ext.getCmp("id").setValue(_record[0].data.id);
		form.load({
			url : sysPath+ "/flow/flowFormfieldAction!list.action",
			params:{filter_EQL_id:_record[0].data.id,limit : pageSize},
			success:function(_form, action){
				var t=_record[0].data.htmlType;
				if(t=='1'){
					Ext.getCmp('fieldAttr').setDisabled(false);
					Ext.getCmp('combofieldType').setDisabled(true);
				}else if(t=='4'){
					Ext.getCmp('combofieldType').setDisabled(false);
					Ext.getCmp('combofieldType').setValue(_record[0].data.objType);
					Ext.getCmp('combofieldType').setRawValue(_record[0].data.fieldTypeName);
					Ext.getCmp('fieldAttr').setDisabled(true);
				}else{
					Ext.getCmp('fieldAttr').setDisabled(true);
					Ext.getCmp('combofieldType').setDisabled(true);
				}
			}
		})
	}
	var win = new Ext.Window({
		title : formfieldTitle,
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
						url : sysPath + "/flow/flowFormfieldAction!save.action",
						waitMsg : '正在保存数据...',
						success : function(form, action) {
							win.hide(), 
							Ext.Msg.alert(alertTitle,'保存成功！');
							formfiedReload();
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
//新增表单关联
function addFormLink(_record){
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
		reader : new Ext.data.JsonReader({
            root: 'result', totalProperty: 'totalCount'
        },formlinkFields),
		items:[
			   {xtype:'hidden',name:'ts'},
			   {xtype:'hidden',name:'status',value:1},
			   {xtype:'hidden',name:'pid',id:'pid',value:formId},
			   {xtype:'hidden',name:'id',id:'id'},
               {
               		xtype :'combo',
					triggerAction : 'all',
					hiddenName:'oid',
					id:'combolinkType',
					store:forminfoStore,
					valueField:'id',
					displayField:'objName',
					minChars : 1,
					queryParam : 'filter_LIKES_objName',
					pageSize:pageSize,
					allowBlank:false,
					blankText:'包含表单不能为空！',
					model:'local',
					emptyText:'请选择',
					fieldLabel : '包含表单<span style="color:red">*</span>'
               }
        ]
	});
	formlinkTitle="新增表单关联";
	if(_record!=null){
		formlinkTitle="修改表单关联";
		Ext.getCmp("id").setValue(_record[0].data.id);
		form.load({
			url : sysPath+ "/flow/flowFormlinkAction!ralaList.action",
			params:{filter_EQL_id:_record[0].data.id,limit : pageSize,privilege:243},
			success:function(_form, action){
				Ext.getCmp('combolinkType').setValue(_record[0].data.oid);
				Ext.getCmp('combolinkType').setRawValue(_record[0].data.OName);
			}
		})
	}
	var win = new Ext.Window({
		title : formlinkTitle,
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
						url : sysPath + "/flow/flowFormlinkAction!save.action?privilege=243",
						waitMsg : '正在保存数据...',
						success : function(form, action) {
							//win.hide(), 
							Ext.Msg.alert(alertTitle,'保存成功！');
							formlinkSerach();
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
/**
 * 表单字段Store重新加载
 */
function formfiedReload(){
	formfieldStore.reload({
		params:{
			privilege:242,
			start : 0,
			limit : pageSize
		}
	});
}
/**
 * 表单字段查询
 */
function formfieldSerach(){
	formfieldStore.baseParams = {
		checkItems : Ext.get("checkItems").dom.value,
		privilege:242,
		filter_EQL_formId:formId,
		filter_EQL_status:1,
		itemsValue : Ext.get("searchContent").dom.value
	}
	var editbtn = Ext.getCmp('updatebtn');
	var deletebtn = Ext.getCmp('deletebtn');

	editbtn.setDisabled(true);
	deletebtn.setDisabled(true);
	formfiedReload();
}
/**
 * 表单关联查询
 */
function formlinkSerach(){
	formlinkStore.reload();
}
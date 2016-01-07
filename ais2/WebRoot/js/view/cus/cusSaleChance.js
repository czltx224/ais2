var privilege=171;
var operTitle;
var fields=[
			{name:'id'},
			{name:'cusName'},
            {name: 'targetNum'},
            {name: 'startTime'},
            {name: 'endTime'},
            {name: 'completeNum'},
            {name: 'completeRate'},
            {name: 'timeUser'},
            {name: 'createName'},
            {name: 'createTime'},
            {name: 'updateName'},
            {name: 'updateTime'},
            {name:'cusRecordId'},
            {name:'status'},
            {name:'ts'}
];
Ext.onReady(function() {
	
    var menuStore = new Ext.data.Store({
        storeId:"menuStore",
        baseParams:{privilege:privilege,filter_EQL_cusRecordId:parent.cusRecordId,filter_EQL_status:1,filter_EQL_departId:bussDepart},
        proxy: new Ext.data.HttpProxy({url:sysPath+"/cus/cusSaleChanceAction!ralaList.action"}),
        reader: new Ext.data.JsonReader({
            root: 'result', totalProperty: 'totalCount'
        }, fields)
    });
    if(parent.cusRecordId!=null){
    	menuStore.load();
    }
     var cusRecordStore=new Ext.data.Store({
		storeId:'cusRecordStore', 
		paseParams:{limit:pageSize,filter_EQL_departId:bussDepart},
		proxy: new Ext.data.HttpProxy({url:sysPath+"/cus/cusRecordAction!list.action"}),
        reader: new Ext.data.JsonReader({
            root: 'result', totalProperty: 'totalCount'
        }, [{name:'cusId',mapping:'id'},{name:'cusName'}])
    });
    //start
    var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:true});
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
            {header:'指标值',dataIndex:"targetNum",width:80},
            {header:"指标开始日期",dataIndex:"startTime",width:80},
            {header:"指标结束日期",dataIndex:"endTime",width:80},
            {header:"实际完成指标",dataIndex:"completeNum",width:80},
            {header:"指标完成率",dataIndex:"completeRate",width:80},
            {header:"指标时间使用率",dataIndex:"timeUser",width:100,renderer:function(v){
            	return v*100+'%';
            }},
            {header:"创建人",dataIndex:"createName",width:80,hidden: true},
            {header:"创建时间",dataIndex:"createTime",width:80,hidden: true},
            {header:"修改人",dataIndex:"updateName",width:50,hidden: true},
            {header:"修改时间",dataIndex:"updateTime",width:50,hidden: true}
           
        ]),
        store:menuStore,
        tbar: [
            {
                text:'<B>新增销售机会</B>', id:'addbtn',tooltip:'增加销售机会', iconCls: 'userAdd',handler:function() {
                	if(parent.cusRecordId==null){
                		cusOper(null);
                	}else{
                		cusOpernoNull(null);
                	}
            } },
            '-',
            {
                text:'<B>修改销售机会</B>',id:'updatebtn',disabled:true, tooltip:'修改销售机会', iconCls: 'userEdit', handler: function() {
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
	                		cusOpernoNull(_record);
	                	}
					}
            } } ,
            '-',
            {
                text:'<B>删除</B>',id:'deletebtn',disabled:true, tooltip:'删除销售机会', iconCls: 'userDelete',
                handler: function(){
                	var _records = menuGrid.getSelectionModel().getSelections();
                	if (_records.length < 1) {
						parent.Ext.Msg.alert(alertTitle, "请选择您要操作的行！");
						return false;
					} 
					Ext.Msg.confirm(alertTitle, "确定要删除所选的"+_records.length+"行记录吗？", function(btnYes) {
						if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
								var ids=_records[0].data.id;
								Ext.Ajax.request({
								url : sysPath+ "/cus/cusSaleChanceAction!delete.action",
								params : {
									scId : ids,
									privilege:privilege
								},
								success : function(resp) {
								var respText = Ext.util.JSON.decode(resp.responseText);
								Ext.Msg.alert(alertTitle,respText.msg);
								menuStoreReload();
										}
									});
								}
							});
					
                }
            },
            '-','&nbsp;客户:',
            {
            	xtype : 'combo',
            	triggerAction : 'all',
            	forceSelection : true,
            	pageSize:pageSize,
            	id:'cuscombo',
    			model : 'local',
    			hiddenName : 'cusName',
            	store: cusRecordStore,
            	valueField:'cusId',
            	displayField:'cusName',
    			width : 100
            },
            '-',
            {
            	text:'<B>搜索</B>',
            	id:'btnsearch', 
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
        }),
        listeners:{
        	'render':function(){
        		if(parent.viewtype=='mainview'){
		    		//Ext.getCmp('btnsearch').setDisabled(true);
		    		Ext.getCmp('cuscombo').setDisabled(true);
		    	}
        	}
        }
    });
    function searchCusRequest() {
    	var cusId=Ext.getCmp('cuscombo').getValue();
    	if(cusId!=''){
    		menuStore.baseParams = {
				filter_EQL_cusRecordId:cusId
			}
    	}
		var editbtn = Ext.getCmp('updatebtn');
		var deletebtn = Ext.getCmp('deletebtn');

		editbtn.setDisabled(true);
		deletebtn.setDisabled(true);
		menuStoreReload();
		
	}
   menuGrid.render();
    menuGrid.on('click', function() {
        var _record = menuGrid.getSelectionModel().getSelections();
        var updatebtn = Ext.getCmp('updatebtn');
        var deletebtn = Ext.getCmp('deletebtn');
        var acceptbtn = Ext.getCmp('acceptbtn');
        var dutybtn = Ext.getCmp('dutybtn');
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
	var form = new Ext.form.FormPanel({
					frame : true,
					border : false,
					bodyStyle : 'padding:5px 5px 5px',
					labelWidth : 90,
					width : 400,
					labelAlign : "right",
					reader : new Ext.data.JsonReader({
			            root: 'result', totalProperty: 'totalCount'
			        }, fields),
					items:[
					        {xtype:'hidden',name:'ts',id:'ts'},
						   	{xtype:'hidden',name:'id',id:'id'},
						   	{xtype:'hidden',name:'cusRecordId',id:'cusRecordId',value:parent.cusRecordId},
						   	{xtype:'hidden',name:'departId',value:bussDepart},
							{
								   				xtype:'combo',
				                            	store:cusRecordStore,
				                            	displayField:'cusName',
				                            	valueField:'cusId',
				                            	pageSize:pageSize,
				                            	queryParam:'filter_LIKES_cusName',
				                            	triggerAction : 'all',
				                            	minChars:0,
				                            	model:'local',
				                            	id:'cuscomboa',
				                            	emptyText:'请选择',
				                            	fieldLabel:'客户名称<span style="color:red">*</span>',
				                            	hiddenName:'cusRecordId',
				                            	width:160,
				                            	allowBlank:false,
				                            	blankText:'客户名称不能为空！'
							},
							{	
								xtype:'textfield',
								fieldLabel:'指标值<span style="color:red">*</span>',
								name:'targetNum',
								allowBlank:false,
								blankText:'指标值不能为空！' ,
								width:160
							},
							{
								 xtype:'datefield',
								 fieldLabel:'指标开始时间<span style="color:red">*</span>',
								 name:'startTime',
								 format:'Y-m-d',
								 value:new Date(),
								 width:160,
								 id:'startTime',
								 allowBlank:false,
								 blankText:'指标开始时间不能为空！'
							},
				            {
				                xtype:'datefield',
								fieldLabel:'指标结束时间<span style="color:red">*</span>',
								name:'endTime',
								format:'Y-m-d',
								value:new Date().add(Date.DAY, 7),
								 width:160,
								id:'endTime',
								allowBlank:false,
								blankText:'指标结束时间不能为空！'
				    							
				           },
				           {xtype:'textfield',name:'createName',id:'createName',fieldLabel:'创建人',value:userName,width:160},
				           {xtype:'datefield',name:'createTime',id:'createTime',fieldLabel:'创建时间',value:new Date(),format:'Y-m-d',width:160},
				           {xtype:'textfield',name:'updateName',id:'updateName',fieldLabel:'修改人',value:userName,width:160},
				           {xtype:'datefield',name:'updateTime',id:'updateTime',fieldLabel:'修改时间',value:new Date(),format:'Y-m-d',width:160}
                    ]
                    
				});
		operTitle="添加销售机会信息";
		Ext.getCmp('createName').setDisabled(false);
		Ext.getCmp('createTime').setDisabled(false);
		Ext.getCmp('updateName').setDisabled(true);
		Ext.getCmp('updateTime').setDisabled(true);
		if(_record!=null){
			operTitle="修改销售机会信息";
			Ext.getCmp("id").setValue(_record[0].data.id);
			form.load({
				url : sysPath+ "/cus/cusSaleChanceAction!ralaList.action",
				params:{filter_EQL_id:_record[0].data.id,privilege:privilege,limit : pageSize},
				success:function(){
					Ext.getCmp('updateName').setValue(userName);
					Ext.getCmp('updateTime').setValue(new Date());
					Ext.getCmp('cuscombo').setValue(_record[0].data.cusRecordId);
					Ext.getCmp('cuscombo').setRawValue(_record[0].data.cusName);
				}
			})
			Ext.getCmp('createName').setDisabled(true);
			Ext.getCmp('createTime').setDisabled(true);
			Ext.getCmp('updateName').setDisabled(false);
			Ext.getCmp('updateTime').setDisabled(false);
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
					if (form.getForm().isValid()) {
						form.getForm().submit({
							url : sysPath + "/cus/cusSaleChanceAction!save.action?privilege=171",
							waitMsg : '正在保存数据...',
							success : function(form, action) {
								win.hide(), 
								Ext.Msg.alert(alertTitle,'保存成功!');
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
						carTitle='修改销售机会信息';
						form.load({
						url : sysPath
								+ "/sys/cusSaleChanceAction!ralaList.action",
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
	 function cusOpernoNull(_record) {
	var form = new top.Ext.form.FormPanel({
					frame : true,
					border : false,
					bodyStyle : 'padding:5px 5px 5px',
					labelWidth : 90,
					width : 400,
					labelAlign : "right",
					reader : new Ext.data.JsonReader({
			            root: 'result', totalProperty: 'totalCount'
			        }, fields),
					items:[
					        {xtype:'hidden',name:'ts',id:'ts'},
						   	{xtype:'hidden',name:'id',id:'id'},
						   	{xtype:'hidden',name:'cusRecordId',id:'cusRecordId',value:parent.cusRecordId},
						   	{xtype:'hidden',name:'departId',value:bussDepart},
							{
								   				xtype:'combo',
				                            	store:cusRecordStore,
				                            	displayField:'cusName',
				                            	valueField:'cusId',
				                            	pageSize:pageSize,
				                            	queryParam:'filter_LIKES_cusName',
				                            	triggerAction : 'all',
				                            	model:'local',
				                            	minChars:0,
				                            	id:'cuscomboa',
				                            	emptyText:'请选择',
				                            	fieldLabel:'客户名称<span style="color:red">*</span>',
				                            	hiddenName:'cusRecordId',
				                            	width:160,
				                            	allowBlank:false,
				                            	blankText:'客户名称不能为空！'
							},
							{	
								xtype:'textfield',
								fieldLabel:'指标值<span style="color:red">*</span>',
								name:'targetNum',
								allowBlank:false,
								blankText:'指标值不能为空！' ,
								width:160
							},
							{
								 xtype:'datefield',
								 fieldLabel:'指标开始时间<span style="color:red">*</span>',
								 name:'startTime',
								 format:'Y-m-d',
								 value:new Date(),
								 width:160,
								 id:'startTime',
								 allowBlank:false,
								 blankText:'指标开始时间不能为空！'
							},
				            {
				                xtype:'datefield',
								fieldLabel:'指标结束时间<span style="color:red">*</span>',
								name:'endTime',
								format:'Y-m-d',
								value:new Date().add(Date.DAY, 7),
								 width:160,
								id:'endTime',
								allowBlank:false,
								blankText:'指标结束时间不能为空！'
				    							
				           },
				           {xtype:'textfield',name:'createName',id:'createName',fieldLabel:'创建人',value:userName,width:160},
				           {xtype:'datefield',name:'createTime',id:'createTime',fieldLabel:'创建时间',value:new Date(),format:'Y-m-d',width:160},
				           {xtype:'textfield',name:'updateName',id:'updateName',fieldLabel:'修改人',value:userName,width:160},
				           {xtype:'datefield',name:'updateTime',id:'updateTime',fieldLabel:'修改时间',value:new Date(),format:'Y-m-d',width:160}
                    ]
                    
				});
		operTitle="添加销售机会信息";
		top.Ext.getCmp('createName').setDisabled(false);
		top.Ext.getCmp('createTime').setDisabled(false);
		top.Ext.getCmp('updateName').setDisabled(true);
		top.Ext.getCmp('updateTime').setDisabled(true);
		if(_record!=null){
			operTitle="修改销售机会信息";
			top.Ext.getCmp("id").setValue(_record[0].data.id);
			form.load({
				url : sysPath+ "/cus/cusSaleChanceAction!ralaList.action",
				params:{filter_EQL_id:_record[0].data.id,privilege:privilege,limit : pageSize},
				success:function(){
					top.Ext.getCmp('updateName').setValue(userName);
					top.Ext.getCmp('updateTime').setValue(new Date());
					top.Ext.getCmp('cuscombo').setValue(_record[0].data.cusRecordId);
					top.Ext.getCmp('cuscombo').setRawValue(_record[0].data.cusName);
				}
			})
			top.Ext.getCmp('createName').setDisabled(true);
			top.Ext.getCmp('createTime').setDisabled(true);
			top.Ext.getCmp('updateName').setDisabled(false);
			top.Ext.getCmp('updateTime').setDisabled(false);
		}
		var win = new top.Ext.Window({
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
					if (form.getForm().isValid()) {
						form.getForm().submit({
							url : sysPath + "/cus/cusSaleChanceAction!save.action?privilege=171",
							waitMsg : '正在保存数据...',
							success : function(form, action) {
								win.hide(), 
								top.Ext.Msg.alert(alertTitle,'保存成功!');
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
						carTitle='修改销售机会信息';
						form.load({
						url : sysPath
								+ "/sys/cusSaleChanceAction!ralaList.action",
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
		top.Ext.getCmp('cusRecordId').setDisabled(false);
		top.Ext.getCmp('cuscomboa').setRawValue(parent.cusName);
		top.Ext.getCmp('cuscomboa').setDisabled(true);
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
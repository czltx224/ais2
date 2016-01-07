var privilege=177;
var operTitle;
var fields=[
			{name:'id'},
            {name: 'cusId'},
            {name: 'cusName'},
            {name: 'lowWeight'},
            {name: 'overweightRate'},
            {name:'createName'},
            {name:'createTime'},
            {name: 'updateName'},
            {name: 'updateTime'},
            {name:'ts'}
];
Ext.onReady(function() {
    var menuStore = new Ext.data.Store({
        storeId:"menuStore",
        baseParams:{privilege:privilege,limit:pageSize},
        proxy: new Ext.data.HttpProxy({url:sysPath+"/cus/cusOverManagerAction!ralaList.action"}),
        reader: new Ext.data.JsonReader({
            root: 'result', totalProperty: 'totalCount'
        }, fields)
    });
    var cusStore=new Ext.data.Store({
		storeId:"cusStore",
		baseParams:{privilege:61,filter_EQS_custprop:'发货代理'},
		proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/customerAction!ralaList.action"}),
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
        	{name:'id'},
        	{name:'cusName'}
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
            {header:'客商编号',dataIndex:"cusId",hidden: true, hideable: false},
            {header:'客商名称',dataIndex:"cusName",width:80},
            {header:"最低允许超重重量",dataIndex:"lowWeight",width:80}, 
            {header:"超重费率",dataIndex:"overweightRate",width:80},
            {header:"创建人",dataIndex:"createName",width:80},
            {header:"创建时间",dataIndex:"createTime",width:80},
            {header:"修改人",dataIndex:"updateName",width:50,hidden: true},
            {header:"修改时间",dataIndex:"updateTime",width:50,hidden: true}
           
        ]),
        store:menuStore,
        tbar: [
            {
                text:'<B>新增</B>', id:'addbtn',tooltip:'增加超重设置', iconCls: 'userAdd',handler:function() {
                	cusOper(null);
            } },
            '-',
            {
                text:'<B>修改</B>',id:'updatebtn',disabled:true, tooltip:'修改超重设置', iconCls: 'userEdit', handler: function() {
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
                text:'<B>删除</B>',id:'deletebtn',disabled:true, tooltip:'删除超重设置', iconCls: 'userDelete',
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
								url : sysPath+ "/cus/cusOverManagerAction!delete.action",
								params : {
									ids : ids,
									privilege:privilege
								},
								success : function(resp) {
								var respText = Ext.util.JSON.decode(resp.responseText);
								Ext.Msg.alert(alertTitle,'删除成功');
								menuStoreReload();
										}
									});
								}
							});
					
                }
            },
            '-','&nbsp;客商名称:',
            {
            	xtype : 'combo',
            	triggerAction : 'all',
            	forceSelection : true,
            	pageSize:pageSize,
            	minChars:0,
            	queryParam :'filter_LIKES_cusName',
            	id:'searchCombo',
    			model : 'local',
    			name : 'cusName',
    			resizable:true,
            	store: cusStore,
            	valueField:'id',
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
        })
    });
    function searchCusRequest() {
		menuStore.load({
		params:{
			filter_EQL_cusId:Ext.getCmp('searchCombo').getValue()		
		}
		});
	}
   menuGrid.render();
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
	var form = new Ext.form.FormPanel({
					frame : true,
					border : false,
					bodyStyle : 'padding:5px 5px 5px',
					labelWidth : 110,
					width : 400,
					labelAlign : "right",
					reader : new Ext.data.JsonReader({
			            root: 'result', totalProperty: 'totalCount'
			        }, fields),
					items:[
                           
						   {xtype:'hidden',name:'ts',id:'ts'},
						   {xtype:'hidden',id:'id',name:'id'},
						   {xtype:'hidden',id:'cusName',name:'cusName'},
                           {
                           		xtype:'combo',
								fieldLabel:'代理公司',
								triggerAction :'all',
								model : 'local',
						    	id:'cuscombo',
						    	minChars : 0,
						    	store:cusStore,
						    	queryParam :'filter_LIKES_cusName',
						    	pageSize:pageSize,
						    	valueField :'id',
			    				displayField :'cusName',
			    				hiddenName : 'cusId',
								emptyText : '选择类型',
						    	forceSelection : true,
						    	listeners:{
						    		'select':function(){
						    			Ext.getCmp('cusName').setValue(Ext.get('cuscombo').dom.value);
						    		},
						    		'blur':function(){
						    			Ext.getCmp('cusName').setValue(Ext.get('cuscombo').dom.value);
						    		}
						    	}
                           },
                           {
                           		xtype:'numberfield',
                           		fieldLabel:'最低允许超重重量<span style="color:red">*</span>',
                           		id:'lowWeight',
                           		maxValue:99999.99,
								maxLength:8,
                           		name:'lowWeight',
                           		width:160,
                           		blankText : "最低允许超重重量不能为空!",
						    	allowBlank : false
                           },
                           {
                           		xtype:'numberfield',
                           		fieldLabel:'超重费率<span style="color:red">*</span>',
                           		id:'overweightRate',
                           		maxValue:99999999.99,
								maxLength:11,
                           		name:'overweightRate',
                           		width:160,
                           		blankText : "超重费率不能为空!",
						    	allowBlank : false
                           }
                    ]
                    
				});
		operTitle="添加超重设置";
		if(_record!=null){
			operTitle="修改超重设置";
			Ext.getCmp("id").setValue(_record[0].data.id);
			form.load({
				url : sysPath+ "/cus/cusOverManagerAction!ralaList.action",
				params:{filter_EQL_id:_record[0].data.id,privilege:privilege,limit : pageSize},
				success:function(resp){
					Ext.getCmp('cuscombo').setValue(_record[0].data.cusId);
					Ext.getCmp('cuscombo').setRawValue(_record[0].data.cusName);
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
					if (form.getForm().isValid()) {
						form.getForm().submit({
							url : sysPath + "/cus/cusOverManagerAction!save.action?privilege=177",
							waitMsg : '正在保存数据...',
							success : function(form, action) {
								win.hide(), 
								Ext.Msg.alert(alertTitle,
										"保存成功！");
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
						carTitle='修改超重设置';
						form.load({
						url : sysPath
								+ "/sys/cusOverManagerAction!ralaList.action",
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
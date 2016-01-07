var privilege=270;
var operTitle;
var searchUrl="cus/cusGoodsRankAction!list.action";
var fields=[
	{name:'id'},
	{name: 'rankName'},
    {name: 'minVal'},
    {name: 'maxVal'},
    {name:'createName'},
    {name:'createTime'},
    {name: 'updateName'},
    {name: 'updateTime'},
    {name:'ts'}
];
Ext.onReady(function() {
    var menuStore = new Ext.data.Store({
        storeId:"menuStore",
        baseParams:{limit:pageSize},
        proxy: new Ext.data.HttpProxy({url:sysPath+"/"+searchUrl}),
        reader: new Ext.data.JsonReader({
            root: 'result', totalProperty: 'totalCount'
        }, fields)
    });
    //start
    var sm = new Ext.grid.CheckboxSelectionModel({});
    var rowNum = new Ext.grid.RowNumberer({
        header:'序号', width:35, sortable:true
    });
    
    var menuGrid = new Ext.grid.GridPanel({
        renderTo:'showView',
    	id : 'myrecordGrid',
    	height : Ext.lib.Dom.getViewHeight()-5,
    	width : Ext.lib.Dom.getViewWidth()-5,
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
            {header:'等级名称',dataIndex:"rankName",width:80},
            {header:'最小值',dataIndex:"minVal",width:80},
            {header:"最大值",dataIndex:"maxVal",width:80},
            {header:"创建人",dataIndex:"createName",width:80},
            {header:"创建时间",dataIndex:"createTime",width:80},
            {header:"修改人",dataIndex:"updateName",width:50,hidden: true},
            {header:"修改时间",dataIndex:"updateTime",width:50,hidden: true}
           
        ]),
        store:menuStore,
        tbar: [
            {
                text:'<B>新增等级</B>', id:'addbtn',tooltip:'增加货量等级', iconCls: 'userAdd',handler:function() {
                	cusOper(null);
            } },
            '-',
            {
                text:'<B>修改等级</B>',id:'updatebtn',disabled:true, tooltip:'修改货量等级', iconCls: 'userEdit', handler: function() {
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
                text:'<B>删除</B>',id:'deletebtn',disabled:true, tooltip:'删除货量等级', iconCls: 'userDelete',
                handler: function(){
                	var _records = menuGrid.getSelectionModel().getSelections();
                	if (_records.length < 1) {
						parent.Ext.Msg.alert(alertTitle, "请选择您要操作的行！");
						return false;
					} 
					Ext.Msg.confirm(alertTitle, "确定要作废所选的"+_records.length+"行记录吗？", function(btnYes) {
						if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
								var ids="";
								for(var i=0;i<_records.length;i++){
									ids=ids+_records[i].data.id+",";
								}
								Ext.Ajax.request({
								url : sysPath+ "/cus/cusGoodsRankAction!delete.action",
								params : {
									ids : ids,
									privilege:privilege
								},
								success : function(resp) {
								var respText = Ext.util.JSON.decode(resp.responseText);
								if(respText.success){
									Ext.Msg.alert(alertTitle,"删除成功!");
								}else{
									Ext.Msg.alert(alertTitle,respText.msg);
								}
								
								menuStoreReload();
										}
									});
								}
						});
					
                }
            }/*
            ,'-','&nbsp;&nbsp;',
            {
            	xtype : 'textfield',
    			id:'searchContent',
    			name : 'stationName',
    			width : 100,
    			enableKeyEvents:true,
    			listeners : {
			 		keyup:function(textField, e){
		                     if(e.getKey() == 13){
		                     	searchCusRequest();
		                  }
			 		}
	 			}
            },
            '-','&nbsp;',
            {
            	xtype : 'combo',
            	triggerAction : 'all',
    			model : 'local',
    			hiddenId : 'checkItems',
    			hiddenName : 'checkItems',
    			name : 'checkItemstext',
            	store: [
            			['', '查询全部'], 
    					['LIKES_name', '姓名'],
    					['LIKES_tel','电话']
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
            ,'-',
            {
            	text:'<B>搜索</B>',
            	id:'btn', 
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
   menuGrid.render();
   function searchCusRequest() {	
//		menuStore.baseParams = {
//			checkItems : Ext.get("checkItems").dom.value,
//			privilege:privilege,
//			filter_EQL_cusRecordId:parent.cusRecordId,
//			filter_EQL_status:1,
//			itemsValue : Ext.get("searchContent").dom.value
//		}
		var editbtn = Ext.getCmp('updatebtn');
		//var deletebtn = Ext.getCmp('deletebtn');

		editbtn.setDisabled(true);
		//deletebtn.setDisabled(true);
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
    function cusOper(_record) {
	var form = new Ext.form.FormPanel({
					frame : true,
					border : false,
					bodyStyle : 'padding:5px 5px 5px',
					labelWidth : 90,
					width : 350,
					labelAlign : "right",
					reader : new Ext.data.JsonReader({
			            root: 'result', totalProperty: 'totalCount'
			        }, fields),
					defaults : {
						xtype : "textfield",
						width : 200
					},
					items:[{xtype:'hidden',name:'id',id:'id'},
							{xtype:'hidden',name:'ts',id:'ts'},
						{
							xtype:'textfield',
							name:'rankName',
							id:'rankName',
							fieldLabel:'等级名称',
							maxLength:50,
							maxLengthText:'长度不能超过50个汉字',
							allowBlank:true
						},{
							xtype:'numberfield',
							name:'minVal',
							id:'minVal',
							fieldLabel:'最小值(kg)<span style="color:red">*</span>',
							maxValue:99999,
							minValue:0,
							allowBlank:false,
							blankText:'最小值不能为空!'
						},{
							xtype:'numberfield',
							name:'maxVal',
							id:'maxVal',
							fieldLabel:'最大值(kg)<span style="color:red">*</span>',
							maxValue:99999,
							minValue:1,
							allowBlank:false,
							blankText:'最大值不能为空!'
						}
                    ]
                    
				});
		operTitle="添加货量等级信息";
		if(_record!=null){
			operTitle="修改货量等级信息";
			Ext.getCmp("id").setValue(_record[0].data.id);
			form.load({
				url : sysPath+ "/"+searchUrl,
				params:{filter_EQL_id:_record[0].data.id,limit : pageSize},
				success:function(){
				}
			})
		}
		var win = new Ext.Window({
			title : operTitle,
			width : 350,
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
							url : sysPath + "/cus/cusGoodsRankAction!save.action?privilege=270",
							waitMsg : '正在保存数据...',
							success : function(form, action) {
								form.reset();
								Ext.getCmp('rankName').focus(true,true);
								Ext.Msg.alert(alertTitle,
										action.result.msg);
										menuStoreReload();
							},
							failure : function(form, action) {
								if (action.faliureType == Ext.form.Action.SERVER_INVALID) {
									Ext.Msg.alert(alertTitle, "服务器连接异常，请联系管理员！");
								} else {
									if (action.result.msg) {
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
						operTitle='修改货量等级信息';
						form.load({
						url : sysPath
								+"/"+searchUrl,
						params:{filter_EQL_id:_record[0].data.id,limit : pageSize}
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
				limit : pageSize
			}
		});
	}
    //end
});
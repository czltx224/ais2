var privilege=178;
var operTitle;
var fields=[
			{name:'id'},
            {name: 'customerId'},
            {name: 'customerName'},
            {name: 'flightMainNo'},
            {name: 'weight'},
            {name: 'rate'},
            {name:'amount'},
            {name:'remark'},
            {name: 'auditStatus'},
            {name: 'auditName'},
            {name:'auditTime'},
            {name:'auditRemark'},
            {name: 'customerService'},
            {name:'cusDepartCode'},
            {name:'departId'},
            {name: 'departName'},
            {name: 'faxWeight'},
            {name: 'flightWeight'},
            {name:'rejectTime'},
            {name:'rejectName'},
            {name:'createTime'},
            {name:'createName'},
            {name:'updateTime'},
            {name:'updateName'},
            {name:'status'},
            {name:'ts'}
];
Ext.onReady(function() {
    var menuStore = new Ext.data.Store({
        storeId:"menuStore",
        baseParams:{privilege:privilege,limit:pageSize,filter_EQL_status:1},
        proxy: new Ext.data.HttpProxy({url:sysPath+"/cus/cusOverWeightAction!ralaList.action"}),
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
            {header:'主单号',dataIndex:"flightMainNo",width:80},
            {header:'客商编号',dataIndex:"customerId",hidden: true, hideable: false},
            {header:'客商名称',dataIndex:"customerName",width:80},
            {header:"超重日期",dataIndex:"createTime",width:80},
            {header:"黄单重量",dataIndex:"flightWeight",width:80},
            {header:"传真重量",dataIndex:"faxWeight",width:80},
            {header:"超重重量",dataIndex:"weight",width:80},
            {header:"超重费率",dataIndex:"rate",width:80},
            {header:"超重金额",dataIndex:"amount",width:80},
            {header:"客服员",dataIndex:"customerService",width:80},
            {header:"审核状态",dataIndex:'auditStatus',width:80,renderer:function(v){
            	if(v==1){
            	return '未审核';
            	}else if(v==2){
					return '已审核';            		
            	}else if(v==3){
            		return '已否决';
            	}
            }},
            {header:"审核人",dataIndex:"auditName",width:80},
            {header:"审核时间",dataIndex:"auditTime",width:80},
            {header:"否决人",dataIndex:"rejectName",width:80},
            {header:"否决时间",dataIndex:"rejectTime",width:80},
            {header:"备注",dataIndex:"auditRemark",width:150}
           
        ]),
        store:menuStore,
        tbar: [
            {
                text:'<B>审核</B>', id:'updatebtn',disabled:true,tooltip:'审核', iconCls: 'userAdd',handler:function() {
                	var _record = menuGrid.getSelectionModel().getSelections();
                	if(_record[0].data.auditStatus==1){
                		cusOper(_record,'aduit');
                	}else{
                		Ext.Msg.alert(alertTitle,'只能审核未审核的数据！');
                	}
            } },
            '-',
            {
                text:'<B>否决</B>',id:'deletebtn',disabled:true, tooltip:'否决', iconCls: 'userEdit', handler: function() {
                var _record = menuGrid.getSelectionModel().getSelections();
                cusOper(_record,'faxreturn');
                /*
                Ext.Ajax.request({
                	url:sysPath+'/cus/cusOverWeightAction!reject.action',
                	params:{
                		ooId:_record[0].data.id
                	},
                	success:function(resp){
                		var respText = Ext.util.JSON.decode(resp.responseText);
                		Ext.Msg.alert(alertTitle,respText.msg);
                		if(respText.success){
                			menuStoreReload();
                		}
                	}
                });*/
            } } ,
            '-',
            {text:'<b>导出</b>',iconCls:'sort_down',handler:function() {
                	parent.exportExl(menuGrid);
        } },'-','&nbsp;&nbsp;',
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
            '-','&nbsp;客商名称:',
            {
            	xtype : 'combo',
            	triggerAction : 'all',
            	forceSelection : true,
            	queryParam :'filter_LIKES_cusName',
            	pageSize:pageSize,
            	id:'searchCombo',
    			model : 'local',
    			minChars:0,
    			hiddenName : 'cusName',
            	store: cusStore,
            	valueField:'id',
            	displayField:'cusName',
    			width : 100
            },
            '-','审核状态:',{
				 xtype:'combo',
				 name:'auditStatussea',
				 id:'auditStatussea',
				 forceSelection : true,
				 triggerAction : 'all',
				 model : 'local',
				 width:100,
				 store:[
				 	['1','未审核'],
				 	['2','已审核'],
				 	['3','已否决']
				 ]
            },'-','客服员:',
            {
            	xtype:'textfield',
            	name:'cusServicesea',
            	width:100,
            	id:'cusServicesea'
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
    	//alert(Ext.getCmp('searchCombo').getValue());
    	Ext.apply(menuStore.baseParams, {
    		filter_EQL_customerId:Ext.getCmp('searchCombo').getValue(),
    		filter_EQL_auditStatus:Ext.getCmp('auditStatussea').getValue(),
    		filter_LIKES_customerService:Ext.getCmp('cusServicesea').getValue()
    	});
    	menuStore.reload();
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
    function cusOper(_record,type) {
		var form = new Ext.form.FormPanel({
					frame : true,
					border : false,
					bodyStyle : 'padding:5px 5px 5px',
					labelWidth : 90,
					width : 400,
					labelAlign : "right",
					defaults : {
						xtype : "textfield",
						width : 230
					},
					items:[
						   {
						   		xtype:'textfield',
						   		fieldLabel:'客商名称',
						   		disabled:true,
						   		name:'cuscombo',
						   		id:'cuscombo'
						   },
						   {
						   		xtype:'textfield',
						   		fieldLabel:'主单号',
						   		disabled:true,
						   		name:'flightMainNo',
						   		id:'flightMainNo'
						   },
						   {
						   		xtype:'textfield',
						   		fieldLabel:'主单重量',
						   		disabled:true,
						   		name:'faxWeight',
						   		id:'faxWeight'
						   },
						   {
						   		xtype:'textfield',
						   		fieldLabel:'黄单重量',
						   		disabled:true,
						   		name:'flightWeight',
						   		id:'flightWeight'
						   } ,
						   {
						   		xtype:'textfield',
						   		fieldLabel:'超重重量',
						   		disabled:true,
						   		name:'overweight',
						   		id:'overweight'
						   }
						   ,
						   {
						   		xtype:'textfield',
						   		fieldLabel:'费率',
						   		disabled:true,
						   		name:'overrate',
						   		id:'overrate'
						   } ,
						   {
						   		xtype:'textfield',
						   		fieldLabel:'超重金额',
						   		disabled:true,
						   		name:'overprice',
						   		id:'overprice'
						   }
						   ,
                           {
                           		xtype:'textarea',
                           		height:60,
                           		fieldLabel:'审核备注',
                           		name:'aduitRemark',
                           		id:'aduitRemark'
                           }
                    ]
                    
				});
		if(type=='aduit'){
			operTitle='主单超重审核';
		}else if(type='faxreturn'){
			operTitle='主单超重否决';
			
		}
		var win = new Ext.Window({
			title : operTitle,
			width : 410,
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
							url : sysPath + "/cus/cusOverWeightAction!aduit.action",
							params:{
								ooId:_record[0].data.id,
								aduitRemark:Ext.getCmp('aduitRemark').getValue(),
								type:type
							},
							waitMsg : '正在保存数据...',
							success : function(form, action) {
								win.hide(), 
								Ext.Msg.alert(alertTitle,
										action.result.msg);
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
			},  {
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
		Ext.getCmp('cuscombo').setValue(_record[0].data.customerName);
		Ext.getCmp('flightMainNo').setValue(_record[0].data.flightMainNo);
		Ext.getCmp('faxWeight').setValue(_record[0].data.faxWeight);
		Ext.getCmp('flightWeight').setValue(_record[0].data.flightWeight);
		Ext.getCmp('overprice').setValue(_record[0].data.amount);
		Ext.getCmp('overrate').setValue(_record[0].data.rate);
		Ext.getCmp('overweight').setValue(_record[0].data.weight);
		if(type=='aduit'){
			document.getElementById('aduitRemark').parentNode.previousSibling.innerHTML ='审核备注:';
		}else if(type='faxreturn'){
			document.getElementById('aduitRemark').parentNode.previousSibling.innerHTML ='否决备注:';
		}
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
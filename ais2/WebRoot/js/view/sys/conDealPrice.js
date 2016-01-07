var popupWin;
var privilege=57;
var carTitle;
var fields=[
	{name:'id'},
            {name: 'cusName'},
            {name: 'dispatchAgency'},
            {name: 'contactWay'},
            {name: 'ismobile',convert:function(v){
            	if(v==0){
            		return '否';
            	}else{
            		return '是';
            	}
            }
            },
            {name: 'cityOwnPrice'},
            {name: 'cityOwnMinPrice'},
            {name: 'flyOwnPrice'},
            {name: 'flyOwnMinPrice'},
            {name: 'citySendPrice'},
            {name: 'citySendMinPrice'},
            {name: 'cusAddr'},
            {name: 'sendRequire'},
            {name: 'startTime'},
            {name: 'stopTime'},
            {name: 'isstop',convert:function(v){
            	if(v==0){
            		return '否';
            	}else{
            		return '是';
            	}
              }
            },
            {name: 'createName'},
            {name: 'createTime'},
            {name: 'updateName'},
            {name: 'updateTime'},
            {name:'ts'}
];
Ext.onReady(function() {
    var menuStore = new Ext.data.Store({
        storeId:"menuStore",
        baseParams:{privilege:privilege},
        proxy: new Ext.data.HttpProxy({url:sysPath+"/sys/conDealPriceAction!ralaList.action"}),
        reader: new Ext.data.JsonReader({
            root: 'result', totalProperty: 'totalCount'
        }, fields)
    });
    //客商Store
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
        renderTo:'menuGrid',
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
            {header:'编号',dataIndex:"id",hidden: true, hideable: false},
            {header:'时间戳',dataIndex:"ts",hidden: true, hideable: false},
            {header:'收货人名称',dataIndex:"cusName",width:80},
            {header:"发货代理",dataIndex:"dispatchAgency",width:80},
            {header:"联系电话",dataIndex:"contactWay",width:80},
            {header:"是否为手机",dataIndex:"ismobile",width:80},
            {header:"市内自提价格",dataIndex:"cityOwnPrice",width:80,sortable:true},
            {header:"市内自提最低一票",dataIndex:"cityOwnMinPrice",width:80,sortable:true},
            {header:"机场自提价格",dataIndex:"flyOwnPrice",width:80,sortable:true},
            {header:"机场自提最低一票",dataIndex:"flyOwnMinPrice",width:80,sortable:true},
            {header:"市内送货价格",dataIndex:"citySendPrice",width:80,sortable:true},
            {header:"市内送货最低一票",dataIndex:"citySendMinPrice",width:80,sortable:true},
            {header:"客户地址",dataIndex:"cusAddr",width:150},
            {header:"协议开始时间",dataIndex:"startTime",width:80,sortable:true},
            {header:"协议终止时间",dataIndex:"stopTime",width:80,sortable:true},
            {header:"是否停用",dataIndex:"isstop",width:80},
            {header:"创建人",dataIndex:"createName",width:50,hidden:true},
            {header:"创建时间",dataIndex:"createTime",width:50,hidden:true},
            {header:"更新人",dataIndex:"updateName",width:50,hidden:true},
            {header:"更新时间",dataIndex:"updateTime",width:50,hidden:true}
           
        ]),
        store:menuStore,
        tbar: [{text:'<b>导出</b>',iconCls:'sort_down',handler:function() {
                	parent.exportExl(menuGrid);
        } },'-',
            {
                text:'<B>增加</B>', id:'addbtn',tooltip:'增加客户个性化要求', iconCls: 'userAdd',handler:function() {
                	cusOper(null);
            } },
            '-',
            {
                text:'<B>修改</B>',id:'updatebtn',disabled:true, tooltip:'修改客户个性化要求', iconCls: 'userEdit', handler: function() {
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
                text:'<B>删除</B>',id:'deletebtn',disabled:true, tooltip:'删除选择个性化要求', iconCls: 'userDelete',
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
								url : sysPath+ "/sys/conDealPriceAction!delete.action",
								params : {
									ids : ids,
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
            },'-','&nbsp;&nbsp;',
            {
            	xtype : 'textfield',
    			id:'searchContent',
    			name : 'stationName',
    			width : 100,
    			enableKeyEvents:true,
    			listeners : {
			 		keyup:function(textField, e){
		                     if(e.getKey() == 13){
		                     	searchConDealPrice();
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
    					['LIKES_cusName', '收货人'],
    					['LIKES_contactWay','联系电话']
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
            	handler : searchConDealPrice
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
     function searchConDealPrice() {
		menuStore.proxy = new Ext.data.HttpProxy({
					method : 'POST',
					url : sysPath
								+ "/sys/conDealPriceAction!ralaList.action",
						params:{privilege:privilege,limit : pageSize}
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
   // var _records = menuGrid.getSelectionModel().getSelections();
	var form = new Ext.form.FormPanel({
					frame : true,
					border : false,
					bodyStyle : 'padding:5px 5px 5px',
					labelAlign : "left",
					labelWidth : 120,
					width : 600,
					labelAlign : "right",
					reader : new Ext.data.JsonReader({
			            root: 'result', totalProperty: 'totalCount'
			        },fields),
					items:[
	
					{
				
					        layout : 'column',
					        items:[{
					        		layout:'form',
					        		columnWidth : .5,
					        		items:[
					        				{xtype:'hidden',name:'ts'},
								         	{xtype:'hidden',name:'id',id:'conDealPriceId'},
								          	{xtype:'textfield',fieldLabel:'收货人<span style="color:red">*</span>',name:'cusName',allowBlank:false,blankText:'收货人不能为空！',maxLength:20},
								          	{xtype:'numberfield',fieldLabel:'市内自提价格<span style="color:red">*</span>',name:'cityOwnPrice',allowBlank:false,blankText:'市内自提价格不能为空！',maxLength:4},
				                            {xtype:'numberfield',fieldLabel:'机场自提价格<span style="color:red">*</span>',name:'flyOwnPrice',allowBlank:false,blankText:'机场自提价格不能为空！',maxLength:4},
								          	{xtype:'numberfield',fieldLabel:'市内送货价格<span style="color:red">*</span>',name:'citySendPrice',allowBlank:false,blankText:'市内送货价格！',maxLength:4},	
								   			{
								   				xtype:'combo',
												fieldLabel:'代理公司',
												triggerAction :'all',
												model : 'local',
						    					id:'cuscombo',
						    					resizable : true,
						    					width:120,
						    					minChars : 0,
						    					store:cusStore,
						    					queryParam :'filter_LIKES_cusName',
						    					pageSize:pageSize,
						    					valueField :'cusName',
			    								displayField :'cusName',
			    								hiddenName : 'dispatchAgency',
												emptyText : '请选择',
						    					forceSelection : true
								   			},
								   		    {xtype:'textfield',fieldLabel:'联系电话',name:'contactWay',maxLength:20},
				                            {xtype:'datefield',fieldLabel:'协议开始时间<span style="color:red">*</span>',name:'startTime',allowBlank:false,blankText:'协议开始时间不能为空！',format:'Y-m-d',width:125}
		                           	]
				                    },{
				                    layout:'form',
				                    columnWidth : .5,
				                    items:[
				                    			{
						                           	xtype:'radiogroup',
						                           	id:'radioisstop',
						                           	fieldLabel:'是否停用<span style="color:red">*</span>',
						                           	items: [{
									                    inputValue:'1',
									                    boxLabel: '是',
									                    name:'isstop',
									                    checked:_record==null?false:(_record[0].data.isstop=="是"?true:false)
									                }, {
									                    inputValue:'0',
									                    name:'isstop',
									                    boxLabel: '否',
									                    checked:_record==null?true:(_record[0].data.isstop=="否"?true:false)
									                }]
						                           },
						                           {xtype:'numberfield',fieldLabel:'市内自提最低一票<span style="color:red">*</span>',name:'cityOwnMinPrice',allowBlank:false,blankText:'市内最低一票不能为空！',maxLength:4},
						                           {xtype:'numberfield',fieldLabel:'机场自提最低一票<span style="color:red">*</span>',name:'flyOwnMinPrice',allowBlank:false,blankText:'机场自提最低一票不能为空！',maxLength:4},
						                           {xtype:'numberfield',fieldLabel:'市内送货最低一票<span style="color:red">*</span>',name:'citySendMinPrice',allowBlank:false,blankText:'市内送货最低一票！',maxLength:4},
						                           {xtype:'textfield',fieldLabel:'客户地址<span style="color:red">*</span>',name:'cusAddr',allowBlank:false,blankText:'客户地址不能为空！',maxLength:200},
						                           {
				                           				xtype:'radiogroup',
				                           				id:'radioismobile',
				                           				fieldLabel:'是否为手机<span style="color:red">*</span>',
				                           				items: [{
							                    			inputValue:'1',
							                    			boxLabel: '是',
							                    			name:'ismobile',
							                    			checked:_record==null?true:(_record[0].data.ismobile=="是"?true:false)
							                			}, {
							                    			inputValue:'0',
									                    	name:'ismobile',
									                    	boxLabel: '否',
									                    	checked:_record==null?false:(_record[0].data.ismobile=="否"?true:false)
									                	
									                	}]
						                           },
						                           {xtype:'datefield',fieldLabel:'协议截止时间<span style="color:red">*</span>',name:'stopTime',allowBlank:false,blankText:'协议开始时间不能为空！',format:'Y-m-d',width:125}
				                    ]
				            }]
                    }]
                    
				});
		operTitle="添加收货人协议价格";
		if(_record!=null){
		    var _cid=_record[0].data.id;
			operTitle="修改收货人协议价格";
			Ext.getCmp("conDealPriceId").setValue(_cid);
			form.load({
				url : sysPath+ "/sys/conDealPriceAction!ralaList.action",
				params:{filter_EQL_id:_cid,privilege:privilege,limit : pageSize},
				success:function(){
					//Ext.getCmp('cuscombo').setValue(_record[0].data.dispatchAgency);
				}
			})
		}
		var win = new Ext.Window({
			title : operTitle,
			width : 580,
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
						if(Ext.getCmp('cuscombo').getValue()=='' && Ext.getCmp().getValue()==''){
							Ext.Msg.alert(alertTitle,'代理公司和收货人电话不能同时为空!');
							return;
						}else{
							form.getForm().submit({
								url : sysPath + "/sys/conDealPriceAction!save.action?privilege=57",
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
				}
			}, {
				text : "重置",
				handler : function() {
					if(_record!=null){
					var _cid=_record[0].data.id;
						carTitle='修改收货人协议价';
						form.load({
						url : sysPath
								+ "/sys/conDealPriceAction!ralaList.action",
						params:{filter_EQL_id:_cid,privilege:privilege,limit : pageSize},
						success:function(){
							Ext.getCmp("radioisstop").reset();
							Ext.getCmp("radioismobile").reset();
						}
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
var popupWin;
var privilege=236;
var carTitle,menuField,menuStore,cusDepartStore,areaStore,targetTypeStore,cusTargetGrid,cusServiceStore;
var cusServiceUrl='user/userAction!list.action';//客服员查询地址
Ext.onReady(function() {
	menuField=[
            {name:'id'},
            {name: 'targetType'},
            {name: 'departId'},
            {name: 'departName'},
            {name: 'targetNum'},
            {name: 'targetTime'},
            {name: 'cusDepartCode'},
            {name: 'cusDepartName'},
            {name: 'customerService'},
            {name: 'createName'},
            {name: 'createTime'},
            {name: 'updateName'},
            {name: 'updateTime'},
            {name:'ts'}
        ];
	
    menuStore = new Ext.data.Store({
        storeId:"menuStore",
        baseParams:{privilege:privilege},
        proxy: new Ext.data.HttpProxy({url:sysPath+"/cus/marketTargetAction!list.action"}),
        reader: new Ext.data.JsonReader({
            root: 'result', totalProperty: 'totalCount'
        },menuField )
    });
    //客服员 cusServiceUrl
	cusServiceStore=new Ext.data.Store({
        storeId:"cusServiceStore",
		proxy:new Ext.data.HttpProxy({url:sysPath+"/"+cusServiceUrl}),
		baseParams:{limit:pageSize,filter_EQS_status:1},
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
    	{name:'cusServiceId',mapping:'id'},
    	{name:'cusServiceName',mapping:'userName'}
    	])
	});
    cusDepartStore=new Ext.data.Store({
		storeId:"cusDepartStore",
		method:'post',
		baseParams:{limit:pageSize,filter_EQL_isBussinessDepa:0,privilege:53,filter_EQL_isCusDepart:1},
		proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/departAction!ralaList.action"}),
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
        	{name:'departNo'},
        	{name:'departName'}
        	])
	});
	areaStore=new Ext.data.Store({
		storeId:"areaStore",
		method:'post',
		baseParams:{limit:pageSize,filter_EQL_isBussinessDepa:1},
		proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/departAction!findAll.action"}),
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
        	{name:'departId'},
        	{name:'departName'}
        	])
	});
	//配送方式Store
	targetTypeStore=new Ext.data.Store({
		storeId:"targetTypeStore",
		baseParams:{filter_EQL_basDictionaryId:226,privilege:16},
		proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/dictionaryAction!ralaList.action"}),
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
        	{name:'id'},
        	{name:'targetType',mapping:'typeName'}
        	])
	});
    //start
    var sm = new Ext.grid.CheckboxSelectionModel({});
    var rowNum = new Ext.grid.RowNumberer({
        header:'序号', width:35, sortable:true
    });
    
    cusTargetGrid = new Ext.grid.GridPanel({
        renderTo:'showView',
        height:Ext.lib.Dom.getViewHeight(), 
        width:Ext.lib.Dom.getViewWidth()-5,
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
            {header:'指标类型',dataIndex:"targetType"},
            {header:'指标值',dataIndex:"targetNum",width:80},
            {header:"指标时间",dataIndex:"targetTime",width:80},
            {header:"客服部门编码",dataIndex:"cusDepartCode",width:80,hidden: true, hideable: false},
            {header:"客服部门名称",dataIndex:"cusDepartName",width:80},
            {header:"客服员",dataIndex:"customerService",width:80},
            {header:"部门ID",dataIndex:"departId",width:80,hidden: true, hideable: false},
            {header:"部门名称",dataIndex:"departName",width:150},
            {header:"创建人",dataIndex:"createName",width:50,hidden: true},
            {header:"创建时间",dataIndex:"createTime",width:50,hidden: true},
            {header:"修改人",dataIndex:"updateName",width:50,hidden: true},
            {header:"修改时间",dataIndex:"updateTime",width:50,hidden: true}
           
        ]),
        store:menuStore,
        tbar: [{text:'<b>导出</b>',iconCls:'sort_down',handler:function() {
                	parent.exportExl(cusTargetGrid);
        } },'-',
            {
                text:'<B>增加</B>', id:'addbtn',tooltip:'增加客户个性化要求', iconCls: 'userAdd',handler:function() {
                	cusOper(null);
            } },
            '-',
            {
                text:'<B>修改</B>',id:'updatebtn',disabled:true, tooltip:'修改客户个性化要求', iconCls: 'userEdit', handler: function() {
                var _record = cusTargetGrid.getSelectionModel().getSelections();
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
                	var _records = cusTargetGrid.getSelectionModel().getSelections();
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
								url : sysPath+ "/cus/marketTargetAction!delete.action",
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
            },'-','&nbsp;','统计时间:',{
            	xtype:'datefield',
            	name:'counttime',
            	id:'counttime',
            	format:'Y-m',
            	allowBlank:false,
            	blankText:'统计时间不能为空',
            	value:new Date()
            },
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
    					['LIKES_targetName', '指标名称'],
    					['LIKES_cusName','客户名称'],
    					['LIKES_cusTel','客户电话']
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
            	id:'tarBtnSearch', 
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
     	if(Ext.getCmp('counttime').isValid()){
     		var countt = Ext.getCmp('counttime').getValue();
     		menuStore.baseParams = {
				checkItems : Ext.get("checkItems").dom.value,
				filter_GED_targetTime:Ext.get('counttime').dom.value+'-1',
				filter_LED_targetTime:Ext.get('counttime').dom.value+'-'+getDaysInMonth(new Date(countt).getFullYear(),new Date(countt).getMonth()+1),
				privilege:privilege,
				itemsValue : Ext.get("searchContent").dom.value
			}
			var editbtn = Ext.getCmp('updatebtn');
			var deletebtn = Ext.getCmp('deletebtn');
	
			editbtn.setDisabled(true);
			deletebtn.setDisabled(true);
			menuStoreReload();
     	}
	}
     //查询
     
  // cusTargetGrid.render();
   /*
   menuStore.load({
   params:{
   	limit:pageSize,
   	filter_EQL_id:0
   	}
   });*/
   

    cusTargetGrid.on('click', function() {
        var _record = cusTargetGrid.getSelectionModel().getSelections();
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
			labelWidth : 70,
			width : 400,
			labelAlign : "right",
			labelWidth : 70,
			defaults : {
				xtype : "textfield",
				width : 230
			},
			reader : new Ext.data.JsonReader({
	            root: 'result', totalProperty: 'totalCount'
	        }, menuField),
			items:[
				   {xtype:'hidden',name:'ts',id:'ts'},
				   {xtype:'hidden',name:'id',id:'id'},
				   {xtype:'hidden',name:'cusDepartName',id:'cusDepartName'},
				   {xtype:'hidden',name:'targetTime',id:'targetTime'},
				   {xtype:'hidden',name:'departName',id:'departName'},
                   {
                   		xtype : 'combo',
						typeAhead : true,
						forceSelection : true,
						id:'targetTypeCombo',
						queryParam : 'filter_LIKES_typeName',
						minChars : 0,
						fieldLabel : '指标类型<span style="color:red">*</span>',
						store : targetTypeStore,
						triggerAction : 'all',
						valueField : 'id',
						displayField :'targetType',
						name :'targetType',
						allowBlank : false,
						blankText:'指标类型不能为空！'
                   },
                   {
                   		xtype:'numberfield',
                   		fieldLabel:'指标值<span style="color:red">*</span>',
                   		id:'targetNum',
                   		name:'targetNum',
                   		maxValue:99999999.99,
						maxLength:11,
						allowBlank:false,
						blankText:'指标值不能为空！'
                   },
                   {
                   		xtype : 'combo',
						forceSelection : true,
						queryParam : 'filter_LIKES_departName',
						id:'comboarea',
						minChars : 0,
						fieldLabel : '区域<span style="color:red">*</span>',
						store : areaStore,
						pageSize : pageSize,
						triggerAction : 'all',
						valueField : 'departId',
						displayField : 'departName',
						hiddenName : 'departId',
						emptyText : "请选择",
						allowBlank : false,
						blankText : "区域不能为空！",
						listeners:{
							'select':function(){
								Ext.getCmp('departName').setValue(Ext.get('comboarea').dom.value);
							}
						}
                   },
                   {
                   		xtype : 'combo',
						forceSelection : true,
						queryParam : 'filter_LIKES_departName',
						id:'combocusdepart',
						minChars : 0,
						fieldLabel : '客服部门<span style="color:red">*</span>',
						store : cusDepartStore,
						pageSize : pageSize,
						triggerAction : 'all',
						valueField : 'departNo',
						displayField : 'departName',
						hiddenName : 'cusDepartCode',
						emptyText : "请选择",
						allowBlank : false,
						blankText : "客服部门不能为空！",
						listeners:{
							'select':function(){
								Ext.getCmp('cusDepartName').setValue(Ext.get('combocusdepart').dom.value);
							}
						}
                   },{
                   		xtype : "combo",
						forceSelection : true,
						pageSize : pageSize,
						//selectOnFocus : true,
						resizable : true,
						minChars : 0,
						queryParam : 'filter_LIKES_userName',
						triggerAction : 'all',
						store : cusServiceStore,
						fieldLabel : '客服员',
						mode : "remote",// 从服务器端加载值
						valueField : 'cusServiceName',// value值，与fields对应
						displayField : 'cusServiceName',// 显示值，与fields对应
					    name : 'customerService',
					    id:'searchcusService'
                   },
                   {
                   		xtype:'datefield',
                   		fieldLabel:'指标时间<span style="color:red">*</span>',
                   		name:'targetTime1',
                   		id:'targetTime1',
                   		format:'Y-m',
                   		value:new Date(),
                   		allowBlank:false,
						blankText:'指标时间不能为空！'
                   }
            ]
            
		});
		operTitle="添加营销指标";
		if(_record!=null){
			operTitle="修改营销指标";
			Ext.getCmp("id").setValue(_record[0].data.id);
			form.load({
				url : sysPath+ "/cus/marketTargetAction!list.action",
				params:{filter_EQL_id:_record[0].data.id,privilege:privilege,limit : pageSize},
				success:function(){
					//Ext.getCmp('searchcusService').setValue(_record[0].data.customerService);
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
					Ext.getCmp('targetTime').setValue(Ext.getCmp('targetTime1').getValue().format('Y-m-d'));
					if (form.getForm().isValid()) {
						form.getForm().submit({
							url : sysPath + "/cus/marketTargetAction!save.action?privilege=236",
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
			}, {
				text : "重置",
				handler : function() {
					if(_record!=null){
						carTitle='修改个性化要求信息';
						form.load({
						url : sysPath
								+ "/cus/marketTargetAction!ralaList.action",
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
function getDaysInMonth(year,month){
      month = parseInt(month,10)+1;
      var temp = new Date(year+"/"+month+"/0");
      return temp.getDate();
}

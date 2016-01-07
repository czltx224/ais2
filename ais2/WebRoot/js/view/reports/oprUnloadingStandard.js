//卸货时效标准设置js
var privilege=239;//权限参数
var gridSearchUrl="reports/oprUnloadingStandardAction!ralaList.action";//查询地址
var saveUrl="reports/oprUnloadingStandardAction!save.action";//保存地址
var delUrl="reports/oprUnloadingStandardAction!delete.action";//删除地址
var departUrl='sys/departAction!findDepart.action';//业务部门查询地址
var dictionaryUrl='sys/dictionaryAction!ralaList.action';//数据字典地址
var rowWidth=70;
var  fields=['id','carType','unloadingStandardTime',
			'departName','departId','createName','createTime','updateName','updateTime','ts'];
		
        
	    //业务部门
		departStore = new Ext.data.Store({
	        storeId:"departStore",
	        proxy: new Ext.data.HttpProxy({url:sysPath+'/'+departUrl}),
	        baseParams:{privilege:53,filter_EQL_isBussinessDepa:1,filter_LIKES_departName:'配送中心'},
	        reader: new Ext.data.JsonReader({
	            root: 'resultMap', totalProperty: 'totalCount'
	        }, [
	            {name:'departId',mapping:'DEPARTID'},
	            {name: 'departName',mapping:'DEPARTNAME'}
	        ])
        });
        //车型21
		carTypeStore	= new Ext.data.Store({ 
			storeId:"carTypeStore",
			baseParams:{filter_EQL_basDictionaryId:21,privilege:16},
			proxy:new Ext.data.HttpProxy({url:sysPath+"/"+dictionaryUrl}),
			reader:new Ext.data.JsonReader({
	                    root:'result',
	                    totalProperty:'totalCount'
	        },[
        	{name:'typeCode',mapping:'typeCode'},
        	{name:'typeName',mapping:'typeName'}
        	])
		});
	var tbar=[{text:'<b>导出</b>',iconCls:'sort_down',handler:function() {
                	parent.exportExl(enterPortGrid);
        } },'-',{text:'<b>新增</b>',iconCls:'userAdd',id:'basenterPortAdd',tooltip : '新增出港标准',handler:function() {
                	saveenterPort(null);
            } },'-',
	 	{text:'<b>修改</b>',iconCls:'userEdit',id:'basenterPortEdit',disabled:true,tooltip : '修改出港标准',handler:function(){
	 	var enterPort =Ext.getCmp('enterPortCenter');
		var _records = enterPort.getSelectionModel().getSelections();
		
						if (_records.length < 1) {
							Ext.Msg.alert(alertTitle, "请选择您要修改的行！");
							return false;
						} else if (_records.length > 1) {
							Ext.Msg.alert(alertTitle, "一次只能修改一行！");
							return false;
						}
	 	saveenterPort(_records);}},'-',
	 	{text:'<b>删除</b>',iconCls:'userDelete',id:'basenterPortDelete',disabled:true,tooltip : '删除出港标准',handler:function(){
	 		var enterPort =Ext.getCmp('enterPortCenter');
			var _records = enterPort.getSelectionModel().getSelections();
	
					if (_records.length < 1) {
						Ext.Msg.alert(alertTitle, "请选择您要删除的行！");
						return false;
					} 
					var ids="";
					for(var i=0;i<_records.length;i++){
					
						 ids=ids+_records[i].data.id+',';
					}
					Ext.Msg.confirm(alertTitle, "确定要删除"+_records.length+"条记录吗？", function(
									btnYes) {
								if (btnYes == 'yes' || btnYes == 'ok'
										|| btnYes == true) {
									
									Ext.Ajax.request({
										url : sysPath+'/'
												+ delUrl,
										params : {
											ids : ids,
										    privilege:privilege,
										    limit : pageSize
										},
										success : function(resp) {
											var respText = Ext.util.JSON
													.decode(resp.responseText);

											Ext.Msg.alert(alertTitle,
													  "删除成功!");
											dataReload();
										}
									});
								}
							});
	 	}},'-',{text : '<b>搜索</b>',id : 'btn',iconCls : 'btnSearch',
    					handler : searchenterPort
    				}		
	 	];	
var twoBar = new Ext.Toolbar([
	'部门名称:',{
		xtype : 'combo',
		triggerAction : 'all',
		store : departStore,
		mode : "remote",//获取服务器的值
		valueField : 'departId',//value值，与fields对应
		displayField : 'departName',//显示值，与fields对应
		id:'searchDeptName',
		width : 100,
		listeners:{
			select:function(combo,e){
				searchenterPort();
			}
		}
	},'-',{
    		xtype : 'datefield',
    		id : 'startDate',
    		format : 'Y-m-d',
    		emptyText : "开始时间",
    		anchor : '95%',
    		hidden : true,
    		width : 100,
    		disabled : true,
    		listeners : {
    			'select' : function() {
    			   var start = Ext.getCmp('startDate').getValue()
    			      .format("Y-m-d");
    			   Ext.getCmp('endDate').setMinValue(start);
    		     }
    		}
    	}, '&nbsp;&nbsp;', {
    		xtype : 'datefield',
    		id : 'endDate',
    		format : 'Y-m-d',
    		emptyText : "结束时间",
    		hidden : true,
    		width : 100,
    		disabled : true,
    		anchor : '95%'
    	},'-',
	{xtype:'textfield',blankText:'查询数据',id:'searchContent', enableKeyEvents:true,listeners : {
	 		keyup:function(textField, e){
                 if(e.getKey() == 13){
                 	searchenterPort();
                 }
	 		}
	 	}},
	 	'-',{ 
	 	
    					xtype : "combo",
    					width : 100,
    					triggerAction : 'all',
    					id:'searchSelectBox',
    					model : 'local',
    					hiddenId : 'checkItems',
    					hiddenName : 'checkItems',
    					name : 'checkItemstext',
    					store : [['', '查询全部'],
    							['LIKES_carType', '车辆类型'],
    						    ['EQL_unloadingStandardTime', '标准时长'],
    						    ['LIKES_createName', '创建人'],
    						    ['LIKES_updateName', '修改人'],
    						    ['createTime', '创建时间'],
    						    ['updateTime', '修改时间']
    						    ],
    							
    					emptyText : '选择类型',
    					editable : false,
    					forceSelection : true,
    					listeners : {
    						
    						'select' : function(combo, record, index) {
    							if (combo.getValue() == 'createTime' || combo.getValue()=='updateTime') {
    								Ext.getCmp("startDate").enable();
    								Ext.getCmp("startDate").show();

    								Ext.getCmp("endDate").enable();
    								Ext.getCmp("endDate").show();
    								
    								Ext.getCmp("searchContent").disable();
    								Ext.getCmp("searchContent").hide();
    								Ext.getCmp("searchContent").setValue("");
    							}
    						 	else{
    						  		Ext.getCmp("startDate").hide();
    								Ext.getCmp("startDate").setValue("");
    								
    								Ext.getCmp("endDate").hide();
    								Ext.getCmp("endDate").setValue("");
    								
    								Ext.getCmp("searchContent").enable();
    								Ext.getCmp("searchContent").show();
    								if(Ext.getCmp("searchContent").getValue().length>0){
    									searchenterPort();
    								}
    							
    							}
    						}
    					}
    					
    				}
])

	 	
Ext.onReady(function() {
   	dateStore = new Ext.data.Store({
        storeId:"dateStore",
        proxy: new Ext.data.HttpProxy({url : sysPath+ "/"+gridSearchUrl,
						params:{privilege:privilege,limit : pageSize}}),
        reader: new Ext.data.JsonReader({
            root: 'result', totalProperty: 'totalCount'
        }, fields)
    });
   
   var sm = new Ext.grid.CheckboxSelectionModel({});
	var rowNum = new Ext.grid.RowNumberer({
        header:'序号', width:20
    });
    enterPortGrid = new Ext.grid.GridPanel({
        renderTo: Ext.getBody(),
        id:'enterPortCenter',
        height:Ext.lib.Dom.getViewHeight(),		
        width:Ext.lib.Dom.getViewWidth(),
        viewConfig : {
			columnsText : "显示的列",
			sortAscText : "升序",
			sortDescText : "降序",
			forceFit : true
		},
		autoScroll:false, autoExpandColumn:1,
        frame:true, 
        loadMask:true,
        sm:sm,
        cm:new Ext.grid.ColumnModel([rowNum,sm,
        		{header: 'ID', dataIndex: 'id',width:rowWidth,sortable : true,hidden:true},
 				{header: '部门名称', dataIndex: 'departName',width:rowWidth,sortable : true},
        		{header: '车辆类型', dataIndex: 'carType',width:rowWidth,sortable : true},
        		{header: '标准卸货时间', dataIndex: 'unloadingStandardTime',width:rowWidth,sortable : true},
 				{header: '创建人', dataIndex: 'createName',width:rowWidth,sortable : true},
 				{header: '创建时间', dataIndex: 'createTime',width:rowWidth,sortable : true},
 				{header: '修改人', dataIndex: 'updateName',width:rowWidth,sortable : true},
 				{header: '修改时间', dataIndex: 'updateTime',width:rowWidth,sortable : true}
        ]),
        store:dateStore,
        tbar: tbar,
  	    listeners: {
            render: function(){
                twoBar.render(this.tbar);
            }
        },
        bbar: new Ext.PagingToolbar({
            pageSize: pageSize, 
            privilege:privilege,
            store: dateStore, 
            displayInfo: true,
            displayMsg: '显示第 {0} 条到 {1} 条记录，共 {2} 条', 
            emptyMsg: "没有记录信息显示"
        })
    });
   
	//searchenterPort();
	
	    enterPortGrid.on('click', function() {
	       selabled();
	     	
	    });
		enterPortGrid.on('rowdblclick',function(grid,index,e){
		var _records = enterPortGrid.getSelectionModel().getSelections();
				
				if (_records.length ==1) {
					saveenterPort(_records);
				}
			 	
		});
    });
    
    
	
   function searchenterPort() {
		dateStore.proxy = new Ext.data.HttpProxy({
					method : 'POST',
					url : sysPath
								+ "/"+gridSearchUrl,
						params:{privilege:privilege,limit : pageSize}
				});
			var searchDept = Ext.getCmp('searchDeptName').getValue();
			Ext.apply(dateStore.baseParams, {
					checkItems : Ext.get("checkItems").dom.value,
					itemsValue : Ext.get("searchContent").dom.value,
					filter_EQL_departId:searchDept,
					filter_GED_createTime:'',
					filter_LED_createTime:'',
					filter_GED_updateTime:'',
					filter_LED_updateTime:'',
					privilege:privilege,
					start:0
			})
			 if (Ext.getCmp('searchSelectBox').getValue() == 'createTime') {
				 Ext.apply(dateStore.baseParams, {
						filter_GED_createTime :(!Ext.getCmp("startDate").hidden?(Ext.get("startDate").dom.value=="开始时间"?"":Ext.get("startDate").dom.value):""),
						filter_LED_createTime : (!Ext.getCmp("endDate").hidden?(Ext.get("endDate").dom.value=="结束时间"?"":Ext.get("endDate").dom.value):""),
						checkItems : '',
						itemsValue : ''
				});
    		} else if (Ext.getCmp('searchSelectBox').getValue() == 'updateTime') {
				 Ext.apply(dateStore.baseParams, {
						filter_GED_updateTime:(!Ext.getCmp("startDate").hidden?(Ext.get("startDate").dom.value=="开始时间"?"":Ext.get("startDate").dom.value):""),
						filter_LED_updateTime : (!Ext.getCmp("endDate").hidden?(Ext.get("endDate").dom.value=="结束时间"?"":Ext.get("endDate").dom.value):""),
						checkItems : '',
						itemsValue : ''
				});
    		} 
		
		var editbtn = Ext.getCmp('basenterPortEdit');
		var deletebtn = Ext.getCmp('basenterPortDelete');

		editbtn.setDisabled(true);
		deletebtn.setDisabled(true);

		dataReload();
		
		
	}
function saveenterPort(_records) {
					if(_records!=null)
					var cid=_records[0].data.id;
					var form = new Ext.form.FormPanel({
								labelAlign : 'left',
								frame : true,
								url:sysPath+'/'+gridSearchUrl,
								baseParams:{
				    				filter_EQL_id:cid,
									privilege:privilege,
									limit : pageSize},
								bodyStyle : 'padding:5px 5px 0',
								width : 300,
								reader : new Ext.data.JsonReader({
			            root: 'result', totalProperty: 'totalCount'
			        }, fields),
					labelAlign : "right",
						items : [{
											layout : 'column',
											items : [{
														columnWidth : .95,
														layout : 'form',
														items : [{
																	name : "id",
																	xtype : "hidden"
																},{
																	name : "ts",
																	xtype : "hidden"
																}, {
																	name:'departId',
																	id:'departId',
																	xtype:'hidden'
																
																},
																{
																	xtype : 'combo',
																	triggerAction : 'all',
																	store : departStore,
																	fieldLabel : '部门名称<font style="color:red;">*</font>',
																	queryParam : 'filter_LIKES_departName',
																	forceSelection : true,
																	minChars : 0,
																	pageSize:comboSize,
																	mode : "remote",//获取服务器的值
																	valueField : 'departId',//value值，与fields对应
																	displayField : 'departName',//显示值，与fields对应
																	name:'departName',
																	allowBlank : false,
																	anchor : '95%',
																	listeners:{
																		select:function(combo,e){
																			Ext.getCmp('departId').setValue(combo.getValue());
																		}
																	}
																},{
																	xtype : "combo",
																	fieldLabel:'车辆类型<font style="color:red;">*</font>',
																	allowBlank : false,
																	queryParam : 'filter_LIKES_typeName',
																	minChars : 0,
																	forceSelection : true,
																	triggerAction : 'all',
																	pageSize:comboSize,
																	store : carTypeStore,
																	mode : "remote",// 从本地载值
																	valueField : 'typeName',// value值，与fields对应
																	displayField : 'typeName',// 显示值，与fields对应
																    name : 'carType',
																	anchor : '95%'
																},{
																	xtype : "numberfield",
																	fieldLabel:'标准卸货时长<font style="color:red;">*</font>',
																	allowBlank : false,
																	allowNegative :false,
																	minValue:1,
																    name : 'unloadingStandardTime',
																	anchor : '95%'
																}]

													}]
													
										}]
										});
									
		enterPortTitle='添加出港标准信息';
		if(cid!=null){
		enterPortTitle='修改出港标准信息';
		//departStore.load();
		//Ext.getCmp('mycombo').store=departStore;
		form.load({
						waitMsg : '正在载入数据...',
		    			success : function(_form, action) {
		    			},
		    			failure : function(_form, action) {
		    				Ext.MessageBox.alert(alertTitle, '载入失败');
		    			}
					});
					}
		var win = new Ext.Window({
		title : enterPortTitle,
		width : 300,
		closeAction : 'hide',
		plain : true,
		resizable : false,
		modal : true,
		items : form,
		buttonAlign : "center",	buttons : [{
			text : "保存",
			iconCls : 'groupSave',
			handler : function() {
				if (form.getForm().isValid()) {
					this.disabled = true;//只能点击一次
					form.getForm().submit({
						url : sysPath
								+ '/'+saveUrl,
								params:{privilege:privilege,limit : pageSize},
						waitMsg : '正在保存数据...',
						success : function(form, action) {
							win.hide(), Ext.Msg.alert(alertTitle,
									action.result.msg, function() {
										dataReload();
									});
						},
						failure : function(form, action) {
							if (action.faliureType == Ext.form.Action.SERVER_INVALID) {
								Ext.Msg.alert(alertTitle, "服务器连接异常，请联系管理员！");
							} else {
								if (action.result.msg) {
									Ext.Msg.alert(alertTitle,
											action.result.msg, function() {
												dataReload();
											});

								}
							}
						}
					});
			}
			}
		}, {
				text : "重置",
				iconCls : 'refresh',
				handler : function() {
					if(cid==null){
						form.getForm().reset();
					}
					if(cid!=null){
						form.load({
						waitMsg : '正在载入数据...',
		    			success : function(_form, action) {
				   			
		    			},
		    			failure : function(_form, action) {
		    				Ext.MessageBox.alert(alertTitle, '载入失败');
		    			}
					});
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
 function dataReload(){
			dateStore.reload({
			params : {
				start : 0,
				privilege:privilege,
				limit : pageSize
			}
		});
		selabled();
		
 }
 
 function selabled(){
 	 var _record = enterPortGrid.getSelectionModel().getSelections();
 	
        var updatebtn = Ext.getCmp('basenterPortEdit');
        var deletebtn = Ext.getCmp('basenterPortDelete');
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
 }

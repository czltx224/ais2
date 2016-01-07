//签收和回单确收报表剔除送货员管理js
var privilege=264;//权限参数
var gridSearchUrl="reports/oprScanNotIncludeSendmanAction!list.action";//查询地址
var saveUrl="reports/oprScanNotIncludeSendmanAction!save.action";//保存地址
var delUrl="reports/oprScanNotIncludeSendmanAction!delete.action";//删除地址
var departUrl='sys/departAction!findDepart.action';//业务部门查询地址
var dictionaryUrl='sys/dictionaryAction!ralaList.action';//数据字典地址
var searchUserUrl='user/userAction!list.action';//客服员查询地址
var rowWidth=70;
var  fields=['id','userId','userName','remark','departId','departName',
			'createName','createTime','updateName','updateTime','ts'];
		
        //送货员 
		sendmanStore=new Ext.data.Store({
	        storeId:"sendmanStore",
			proxy:new Ext.data.HttpProxy({url:sysPath+"/"+searchUserUrl}),
			reader:new Ext.data.JsonReader({
	              root:'result',
	              totalProperty:'totalCount'
	        },[
        	{name:'userId',mapping:'id'},
        	{name:'userName',mapping:'userName'}
        	])
        	
		});
	   //配送中心
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
      
	var tbar=[{text:'<b>导出</b>',iconCls:'sort_down',handler:function() {
                	parent.exportExl(notIncludeSendmanGrid);
        } },'-',{text:'<b>新增</b>',iconCls:'userAdd',id:'basenterPortAdd',tooltip : '新增剔除送货员',handler:function() {
                	savenotIncludeSendman(null);
            } },'-',
	 	{text:'<b>修改</b>',iconCls:'userEdit',id:'basnotIncludeSendmanEdit',disabled:true,tooltip : '修改剔除送货员',handler:function(){
	 	var notIncludeSendman =Ext.getCmp('notIncludeSendmanCenter');
		var _records = notIncludeSendman.getSelectionModel().getSelections();
		
						if (_records.length < 1) {
							Ext.Msg.alert(alertTitle, "请选择您要修改的行！");
							return false;
						} else if (_records.length > 1) {
							Ext.Msg.alert(alertTitle, "一次只能修改一行！");
							return false;
						}
	 	savenotIncludeSendman(_records);}},'-',
	 	{text:'<b>删除</b>',iconCls:'userDelete',id:'basnotIncludeSendmanDelete',disabled:true,tooltip : '删除剔除送货员',handler:function(){
	 		var notIncludeSendman =Ext.getCmp('notIncludeSendmanCenter');
			var _records = notIncludeSendman.getSelectionModel().getSelections();
	
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
    					handler : searchnotIncludeSendman
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
				searchnotIncludeSendman();
			}
		}
	},'-',{
    		xtype : 'datefield',
    		id : 'startDate',
    		format : 'Y-m-d',
    		emptyText : "开始时间",
    		anchor : '95%',
    		hidden : true,
    		value:new Date().add(Date.DAY, -7),
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
    		value:new Date(),
    		width : 100,
    		disabled : true,
    		anchor : '95%'
    	},'-',
	{xtype:'textfield',blankText:'查询数据',id:'searchContent', enableKeyEvents:true,listeners : {
	 		keyup:function(textField, e){
                 if(e.getKey() == 13){
                 	searchnotIncludeSendman();
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
    							['LIKES_userName', '送货员'],
    						    ['LIKES_createName', '创建人'],
    						    ['LIKES_updateName', '修改人'],
    						    ['LIKES_remark', '备注'],
    						    ['TIME_createTime', '创建时间'],
    						    ['TIME_updateTime', '修改时间']],
    					emptyText : '选择类型',
    					editable : false,
    					forceSelection : true,
    					listeners : {
    						
    						'select' : function(combo, record, index) {
    							if (combo.getValue() == 'TIME_createTime' || combo.getValue()=='TIME_updateTime') {
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
    							}
    						},
    						keyup:function(combo, record, index) {
    							 if(e.getKey() == 13){
				                 	searchnotIncludeSendman();
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
    notIncludeSendmanGrid = new Ext.grid.GridPanel({
        renderTo: Ext.getBody(),
        id:'notIncludeSendmanCenter',
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
 				{header: '剔除送货员ID', dataIndex: 'userId',width:rowWidth,sortable : true,hidden:true},
 				{header: '剔除送货员', dataIndex: 'userName',width:rowWidth,sortable : true},
 				{header: '部门ID', dataIndex: 'departId',width:rowWidth,sortable : true,hidden:true},
 				{header: '部门名称', dataIndex: 'departName',width:rowWidth,sortable : true},
 				{header: '备注', dataIndex: 'remark',width:rowWidth*2,sortable : true},
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
	    notIncludeSendmanGrid.on('click', function() {
	       selabled();
	     	
	    });
		notIncludeSendmanGrid.on('rowdblclick',function(grid,index,e){
		var _records = notIncludeSendmanGrid.getSelectionModel().getSelections();
				
				if (_records.length ==1) {
					savenotIncludeSendman(_records);
				}
			 	
		});
    });
    
    
	
   function searchnotIncludeSendman() {
		dateStore.baseParams={privilege:privilege,limit : pageSize};
		
			var searchDept = Ext.getCmp('searchDeptName').getValue();
			Ext.apply(dateStore.baseParams, {
					checkItems : Ext.get("checkItems").dom.value,
					itemsValue : Ext.get("searchContent").dom.value,
					filter_EQL_departId:searchDept
			})
			 if (Ext.getCmp('searchSelectBox').getValue() == 'TIME_createTime') {
				 Ext.apply(dateStore.baseParams, {
						filter_GED_createTime :(!Ext.getCmp("startDate").hidden?(Ext.get("startDate").dom.value=="开始时间"?"":Ext.get("startDate").dom.value):""),
						filter_LED_createTime : (!Ext.getCmp("endDate").hidden?(Ext.get("endDate").dom.value=="结束时间"?"":Ext.get("endDate").dom.value):"")
				});
    		} else if (Ext.getCmp('searchSelectBox').getValue() == 'TIME_updateTime') {
				 Ext.apply(dateStore.baseParams, {
						filter_GED_updateTime:(!Ext.getCmp("startDate").hidden?(Ext.get("startDate").dom.value=="开始时间"?"":Ext.get("startDate").dom.value):""),
						filter_LED_updateTime : (!Ext.getCmp("endDate").hidden?(Ext.get("endDate").dom.value=="结束时间"?"":Ext.get("endDate").dom.value):"")
				});
    		} 
		var editbtn = Ext.getCmp('basnotIncludeSendmanEdit');
		var deletebtn = Ext.getCmp('basnotIncludeSendmanDelete');
		
		editbtn.setDisabled(true);
		deletebtn.setDisabled(true);

		dataReload();
	}
function savenotIncludeSendman(_records) {
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
					labelWidth:80,
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
																},{
																	name:'userId',
																	id:'userId',
																	xtype:'hidden'
																
																},{
																	xtype : 'combo',
																	triggerAction : 'all',
																	store : sendmanStore,
																	queryParam : 'filter_LIKES_userName',
																	fieldLabel : '送货员名称<font style="color:red;">*</font>',
																	pageSize:comboSize,
																	resizable : true,
																	forceSelection : true,
																	minChars : 0,
																	mode : "remote",//获取服务器的值
																	valueField : 'userId',//value值，与fields对应
																	displayField : 'userName',//显示值，与fields对应
																	name:'userName',
																	allowBlank : false,
																	anchor : '95%',
																	listeners:{
																		select:function(combo,e){
																			Ext.getCmp('userId').setValue(combo.getValue());
																		}
																	}
																},{
																	xtype : 'textarea',
																	labelAlign : 'left',
																	height:60,
																	fieldLabel : '备注说明',
																	name : 'remark',
																	anchor : '95%'
																}]
														}]
													
										}]
										});
									
		notIncludeSendmanTitle='添加剔除送货员信息';
		if(cid!=null){
			notIncludeSendmanTitle='修改剔除送货员信息';
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
		title : notIncludeSendmanTitle,
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
					
					//departStore.load();
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
 	 var _record = notIncludeSendmanGrid.getSelectionModel().getSelections();
 	
        var updatebtn = Ext.getCmp('basnotIncludeSendmanEdit');
        var deletebtn = Ext.getCmp('basnotIncludeSendmanDelete');
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
 
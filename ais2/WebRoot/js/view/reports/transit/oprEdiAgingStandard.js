//中转时效标准管理js
var privilege=274;//权限参数  未定
var gridSearchUrl="reports/oprEdiAgingStandardAction!list.action";//查询地址
var saveUrl="reports/oprEdiAgingStandardAction!save.action";//保存地址
var delUrl="reports/oprEdiAgingStandardAction!delete.action";//删除地址

var authorityDepartUrl='sys/basRightDepartAction!findDepartName.action';//权限部门查询地址
var departUrl='sys/departAction!findDepart.action';//业务部门查询地址
var searchWidth=70;
var defaultWidth=80;
var pubauthorityDepart=bussDepart;
var  fields=[{name:"id",mapping:'id'},
     		{name:'transitHour',mapping:'transitHour'},
     		{name:'caroutHour',mapping:'caroutHour'},
     		{name:'signhour',mapping:'signhour'},
     		{name:'deptName',mapping:'deptName'},
     		{name:'deptId',mapping:'deptId'},
     		{name:'createName',mapping:'createName'},
     		{name:'createTime',mapping:'createTime'},
     		{name:'updateName',mapping:'updateName'},
     		{name:'updateTime',mapping:'updateTime'},
     		{name:'ts',mapping:'ts'}];
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
		 //权限部门
        authorityDepartStore = new Ext.data.Store({ 
            storeId:"authorityDepartStore",                        
            proxy: new Ext.data.HttpProxy({url:sysPath+"/"+authorityDepartUrl,method:'post'}),
            baseParams:{
            	privilege:63,
            	filter_EQL_userId:userId
            },
            reader: new Ext.data.JsonReader({
            root: 'resultMap', totalProperty: 'totalCount'
           }, [  {name:'departName', mapping:'DEPARTNAME',type:'string'},
            {name:'departId', mapping:'RIGHTDEPARTID',type:'string'}             
              ])    
        });
		
	var tbar=[{text:'<b>导出</b>',iconCls:'sort_down',handler:function() {
                	parent.exportExl(oprEdiAgingStandardGrid);
        } }, '-',
		{text:'<b>新增</b>',iconCls:'userAdd',id:'basoprEdiAgingStandardAdd',tooltip : '新增装卸分拨货量',handler:function() {
                	saveoprEdiAgingStandard(null);
            } },'-',
	 	{text:'<b>修改</b>',iconCls:'userEdit',id:'basoprEdiAgingStandardEdit',disabled:true,tooltip : '修改装卸分拨货量',handler:function(){
	 	var oprEdiAgingStandard =Ext.getCmp('oprEdiAgingStandardCenter');
		var _records = oprEdiAgingStandard.getSelectionModel().getSelections();
		
						if (_records.length < 1) {
							Ext.Msg.alert(alertTitle, "请选择您要修改的行！");
							return false;
						} else if (_records.length > 1) {
							Ext.Msg.alert(alertTitle, "一次只能修改一行！");
							return false;
						}
	 	saveoprEdiAgingStandard(_records);}},'-',
	 	{text:'<b>删除</b>',iconCls:'userDelete',id:'basoprEdiAgingStandardDelete',disabled:true,tooltip : '删除装卸分拨货量',handler:function(){
	 		var oprEdiAgingStandard =Ext.getCmp('oprEdiAgingStandardCenter');
			var _records = oprEdiAgingStandard.getSelectionModel().getSelections();
	
					if (_records.length < 1) {
						Ext.Msg.alert(alertTitle, "请选择您要删除的行！");
						return false;
					} 

					Ext.Msg.confirm(alertTitle, "确定要删除"+_records.length+"条记录吗？", function(
									btnYes) {
								if (btnYes == 'yes' || btnYes == 'ok'
										|| btnYes == true) {
									var ids="";
									for(var i=0;i<_records.length;i++){
									
										 ids=ids+_records[i].data.id+',';
									}
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
	 	}},'-',
	 		{
    			xtype : 'datefield',
    		id : 'startDate',
    		format : 'Y-m-d',
    		emptyText : "开始时间",
    		anchor : '95%',
    		value:new Date().add(Date.DAY, -7),
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
    		value:new Date(),
    		hidden : true,
    		width : 100,
    		disabled : true,
    		anchor : '95%'
    	},
		{xtype:'textfield',blankText:'查询数据',id:'searchContent', enableKeyEvents:true,listeners : {
	 		keyup:function(textField, e){
               if(e.getKey() == 13){
               		searchoprEdiAgingStandard();
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
    							['LIKES_createName','创建人'],
    							['LIKES_updateName','修改人'],
    						    ['GED_createTime','创建时间'],
    						    ['GED_updateTime', '修改时间']],
    							
    					emptyText : '选择类型',
    					editable : false,
    					forceSelection : true,
    					listeners : {
    						'select' : function(combo, record, index) {
    							if (combo.getValue().indexOf('Time')>0 ){
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
    						}
    					}
    					
    				}
    			,'部门名称:',{xtype : "combo",
				width : searchWidth+20,
				editable:true,
				triggerAction : 'all',
				// typeAhead : true,
				queryParam : 'filter_LIKES_departName',
				pageSize : comboSize,
				forceSelection : true,
				resizable:true,
				minChars : 0,
				store : authorityDepartStore,
				resizable:true,
				forceSelection : true,
				valueField : 'departId',//value值，与fields对应
				displayField : 'departName',//显示值，与fields对应
			    id:'authorityDepartId', 
			    enableKeyEvents:true,listeners : {
	 		    keyup:function(textField, e){
                     if(e.getKey() == 13){
                     	searchoprEdiAgingStandard();
                     }
	 		}
	 	
	 	}},'-',{text : '<b>查询</b>',id : 'btn',iconCls : 'btnSearch',
   					handler : searchoprEdiAgingStandard
   		}
	 	];	

Ext.onReady(function() {
   	dateStore = new Ext.data.Store({
        storeId:"dateStore",
        proxy: new Ext.data.HttpProxy({url : sysPath+ "/"+gridSearchUrl,
		baseParams:{privilege:privilege,limit : pageSize,filter_t_departId:pubauthorityDepart}}),
        reader: new Ext.data.JsonReader({
            root: 'result', totalProperty: 'totalCount'
        }, fields)
    });
   
   var sm = new Ext.grid.CheckboxSelectionModel({});
	var rowNum = new Ext.grid.RowNumberer({
        header:'序号', width:20
    });
    oprEdiAgingStandardGrid = new Ext.grid.GridPanel({
        renderTo: Ext.getBody(),
        id:'oprEdiAgingStandardCenter',
        height:Ext.lib.Dom.getViewHeight(),		
        width:Ext.lib.Dom.getViewWidth(),
        viewConfig : {
			columnsText : "显示的列",
			sortAscText : "升序",
			sortDescText : "降序",
			forceFit : true
		},
		autoScroll:false, 
		autoExpandColumn:1,
        frame:true, 
        loadMask:true,
        sm:sm,
        cm:new Ext.grid.ColumnModel([rowNum,sm,
        		{header: '序号ID', dataIndex: 'id',sortable:true,hidden:true,width:defaultWidth},
 				{header: '部门ID', dataIndex: 'deptId',sortable : true,width:defaultWidth},
 				{header: '部门名称', dataIndex: 'deptName',sortable : true,width:defaultWidth},
 				{header: '中转标准用时', dataIndex: 'transitHour',sortable : true,width:defaultWidth},
       			{header: '出库标准用时', dataIndex: 'caroutHour',sortable : true,width:defaultWidth},
        		{header: '签收标准用时', dataIndex: 'signhour',sortable : true,width:defaultWidth},
 				{header: '创建人', dataIndex: 'createName',sortable : true,width:defaultWidth},
 				{header: '创建时间', dataIndex: 'createTime',sortable : true,width:defaultWidth+50},
 				{header: '修改人', dataIndex: 'updateName',sortable : true,width:defaultWidth},
 				{header: '修改时间', dataIndex: 'updateTime',sortable : true,width:defaultWidth+50},
 				{header: '时间戳', dataIndex: 'ts',sortable : true,width:defaultWidth,hidden:true}
        ]),
        store:dateStore,
        tbar: tbar,
        bbar: new Ext.PagingToolbar({
            pageSize: pageSize, 
            privilege:privilege,
            store: dateStore, 
            displayInfo: true,
            displayMsg: '显示第 {0} 条到 {1} 条记录，共 {2} 条', 
            emptyMsg: "没有记录信息显示"
        })
    });
	
	    oprEdiAgingStandardGrid.on('click', function() {
	       selabled();
	     	
	    });
		oprEdiAgingStandardGrid.on('rowdblclick',function(grid,index,e){
			var _records = oprEdiAgingStandardGrid.getSelectionModel().getSelections();
			if (_records.length ==1) {
				saveoprEdiAgingStandard(_records);
			}
		});
    });
    
    
	
   function searchoprEdiAgingStandard() {
		dateStore.baseParams={privilege:privilege,limit : pageSize};
		
		var authorityDepartId=Ext.getCmp('authorityDepartId').getValue();
		if(null!=authorityDepartId && authorityDepartId>0){
			pubauthorityDepart=authorityDepartId;
		}else{
			pubauthorityDepart=bussDepart;
		}
		var checkItems = Ext.get("checkItems").dom.value;
		var itemsValue = Ext.get("searchContent").dom.value;
		
		Ext.apply(dateStore.baseParams, {
			filter_EQL_deptId:pubauthorityDepart,
			privilege:privilege,
			limit : pageSize
		});
		if("GED_createTime"==checkItems){
			Ext.apply(dateStore.baseParams, {
				filter_GED_createTime :(!Ext.getCmp("startDate").hidden?(Ext.get("startDate").dom.value=="开始时间"?"":Ext.get("startDate").dom.value):""),
				filter_LED_createTime : (!Ext.getCmp("endDate").hidden?(Ext.get("endDate").dom.value=="结束时间"?"":Ext.get("endDate").dom.value):"")
			});
		}else if("GED_updateTime"==checkItems){
			Ext.apply(dateStore.baseParams, {
				filter_GED_updateTime :(!Ext.getCmp("startDate").hidden?(Ext.get("startDate").dom.value=="开始时间"?"":Ext.get("startDate").dom.value):""),
				filter_LED_updateTime : (!Ext.getCmp("endDate").hidden?(Ext.get("endDate").dom.value=="结束时间"?"":Ext.get("endDate").dom.value):"")
			});
		}else{
			Ext.apply(dateStore.baseParams, {
				checkItems:checkItems,
				itemsValue:itemsValue
			});
		}
		
		var editbtn = Ext.getCmp('basoprEdiAgingStandardEdit');
		var deletebtn = Ext.getCmp('basoprEdiAgingStandardDelete');

		editbtn.setDisabled(true);
		deletebtn.setDisabled(true);

		dataReload();
	}
function saveoprEdiAgingStandard(_records) {
					var cid;
					var saveFlag=false;
					if(_records!=null){
					    cid=_records[0].data.id;
					    saveFlag=true;
					}
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
																},{
																	name:'deptId',
																	id:'deptId',
																	xtype:'hidden'
																
																},{xtype : 'combo',
																	triggerAction : 'all',
																	store : departStore,
																	fieldLabel : '部门名称<font style="color:red;">*</font>',
																	queryParam : 'filter_LIKES_departName',
																	forceSelection : true,
																	minChars : 0,
																	mode : "remote",//获取服务器的值
																	valueField : 'departId',//value值，与fields对应
																	displayField : 'departName',//显示值，与fields对应
																	name:'deptName',
																	allowBlank : false,
																	anchor : '95%',
																	listeners:{
																		select:function(combo,e){
																			Ext.getCmp('deptId').setValue(combo.getValue());
																		}
																	}
																 },{
													            	xtype : 'numberfield',
													            	name:'transitHour',
													            	allowBlank : false,
																	allowNegative :false,
																	maxLength:10,
													            	fieldLabel : '点到标准用时<font style="color:red;">*</font>',
													            	anchor : '95%'
													            },{
													            	xtype : 'numberfield',
													            	name:'caroutHour',
													            	allowBlank : false,
																	allowNegative :false,
																	maxLength:10,
													            	fieldLabel : '出库标准用时<font style="color:red;">*</font>',
													            	anchor : '95%'
													            },{
													            	xtype : 'numberfield',
													            	name:'signhour',
													            	allowBlank : false,
																	allowNegative :false,
																	maxLength:10,
													            	fieldLabel : '签收标准用时<font style="color:red;">*</font>',
													            	anchor : '95%'
													            }
																]

													}]
													
											}]
										});
									
		oprEdiAgingStandardTitle='添加装卸分拨货量信息';
		if(cid!=null){
		oprEdiAgingStandardTitle='修改装卸分拨货量信息';
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
		title : oprEdiAgingStandardTitle,
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
					form.getForm().submit({
						url : sysPath
								+ '/'+saveUrl,
								params:{privilege:privilege,limit : pageSize},
						waitMsg : '正在保存数据...',
						success : function(form, action) {
							 Ext.Msg.alert(alertTitle,
									action.result.msg, function() {
										dataReload();
										if(_records!=null){
											win.hide();
										}
										else{
											form.reset();
										}
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
 	 var _record = oprEdiAgingStandardGrid.getSelectionModel().getSelections();
 	
        var updatebtn = Ext.getCmp('basoprEdiAgingStandardEdit');
        var deletebtn = Ext.getCmp('basoprEdiAgingStandardDelete');
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
//干线车时效标准设置js
var privilege=202;//权限参数
var gridSearchUrl="reports/oprArteryCarLimitationAction!list.action";//查询地址
var saveUrl="reports/oprArteryCarLimitationAction!save.action";//保存地址
var delUrl="reports/oprArteryCarLimitationAction!delete.action";//删除地址
var departUrl='sys/departAction!findDepart.action';//业务部门查询地址
var dictionaryUrl='sys/dictionaryAction!ralaList.action';//数据字典地址
var rowWidth=70;
var  fields=['id','startDepartId','startDepartName','endDepartId',
			'endDepartName','outCarTime','reachCarTime','runCarTime',
			'countTime','countRange',
			'deptName','deptId','createName','createTime','updateName','updateTime','ts'];
		
        
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
        //操作方式224
		operationStore	= new Ext.data.Store({ 
			storeId:"operationStore",
			baseParams:{filter_EQL_basDictionaryId:224,privilege:16},
			proxy:new Ext.data.HttpProxy({url:sysPath+"/"+dictionaryUrl}),
			reader:new Ext.data.JsonReader({
	                    root:'result',
	                    totalProperty:'totalCount'
	        },[
        	{name:'operationId',mapping:'typeCode'},
        	{name:'operationName',mapping:'typeName'}
        	])
		});
		
        //自动计算频率201
		countRangeStore	= new Ext.data.Store({ 
			storeId:"countRangeStore",
			baseParams:{filter_EQL_basDictionaryId:201,privilege:16},
			proxy:new Ext.data.HttpProxy({url:sysPath+"/"+dictionaryUrl}),
			reader:new Ext.data.JsonReader({
	                    root:'result',
	                    totalProperty:'totalCount'
	        },[
        	{name:'countRangeId',mapping:'typeCode'},
        	{name:'countRangeName',mapping:'typeName'}
        	])
		});
	var tbar=[{text:'<b>导出</b>',iconCls:'sort_down',handler:function() {
                	parent.exportExl(enterPortGrid);
        } },'-',{text:'<b>新增</b>',iconCls:'userAdd',id:'basenterPortAdd',tooltip : '新增干线车标准',handler:function() {
                	saveenterPort(null);
            } },'-',
	 	{text:'<b>修改</b>',iconCls:'userEdit',id:'basenterPortEdit',disabled:true,tooltip : '修改干线车标准',handler:function(){
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
	 	{text:'<b>删除</b>',iconCls:'userDelete',id:'basenterPortDelete',disabled:true,tooltip : '删除干线车标准',handler:function(){
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
		queryParam : 'filter_LIKES_departName',
		pageSize : comboSize,
		mode : "remote",//获取服务器的值
		valueField : 'departId',//value值，与fields对应
		displayField : 'departName',//显示值，与fields对应
		id:'searchDeptName',
		minChars : 0,
		forceSelection : true,
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
    							['EQS_countTime','自动统计时间'],
    							['EQS_countRange','计算频率'],
    							['EQS_outCarTime','发车时间'],
    							['EQS_reachCarTime', '到达时间'],
    						    ['EQS_runCarTime', '车辆运行时间']],
    							
    					emptyText : '选择类型',
    					editable : false,
    					forceSelection : true,
    					listeners : {
    						
    						'select' : function(combo, record, index) {
    							if (combo.getValue() == 'EQD_createTime' || combo.getValue()=='EQD_updateTime') {
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
        width:Ext.lib.Dom.getViewWidth()-1,
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
 				{header: '部门名称', dataIndex: 'deptName',width:rowWidth,sortable : true},
        		{header: '始发部门', dataIndex: 'startDepartName',width:rowWidth,sortable : true},
       			{header: '到达部门', dataIndex: 'endDepartName',width:rowWidth,sortable : true},
        		{header: '发车时间', dataIndex: 'outCarTime',width:rowWidth,sortable : true},
 				{header: '到达时间', dataIndex: 'reachCarTime',width:rowWidth,sortable : true},
 				{header: '运行时间', dataIndex: 'runCarTime',width:rowWidth,sortable : true},
 				{header: '自动统计时间', dataIndex: 'countTime',width:rowWidth,sortable : true},
 				{header: '自动统计维度', dataIndex: 'countRange',width:rowWidth,sortable : true}
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
					filter_EQL_deptId:searchDept,
					filter_GED_createTime:'',
					filter_LED_createTime:'',
					filter_GED_updateTime:'',
					filter_LTD_updateTime:'',
					privilege:privilege,
					start:0
			})
			 if (Ext.getCmp('searchSelectBox').getValue() == 'EQD_createTime') {
				 Ext.apply(dateStore.baseParams, {
						filter_GED_createTime :(!Ext.getCmp("startDate").hidden?(Ext.get("startDate").dom.value=="开始时间"?"":Ext.get("startDate").dom.value):""),
						filter_LED_createTime : (!Ext.getCmp("endDate").hidden?(Ext.get("endDate").dom.value=="结束时间"?"":Ext.get("endDate").dom.value):""),
						checkItems : '',
						itemsValue : ''
				});
    		} else if (Ext.getCmp('searchSelectBox').getValue() == 'EQD_updateTime') {
				 Ext.apply(dateStore.baseParams, {
						filter_GED_updateTime:(!Ext.getCmp("startDate").hidden?(Ext.get("startDate").dom.value=="开始时间"?"":Ext.get("startDate").dom.value):""),
						filter_LTD_updateTime : (!Ext.getCmp("endDate").hidden?(Ext.get("endDate").dom.value=="结束时间"?"":Ext.get("endDate").dom.value):""),
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
								width : 500,
								reader : new Ext.data.JsonReader({
			            root: 'result', totalProperty: 'totalCount'
			        }, fields),
					labelAlign : "right",
						items : [{
											layout : 'column',
											items : [{
														columnWidth : .5,
														layout : 'form',
														items : [{
																	name : "id",
																	xtype : "hidden"
																},{
																	name : "ts",
																	xtype : "hidden"
																}, {
																	name:'deptId',
																	id:'deptId',
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
																	name:'startDepartId',
																	id:'startDepartId',
																	xtype:'hidden'
																},{
																	xtype : 'combo',
																	triggerAction : 'all',
																	store : departStore,
																	fieldLabel : '始发部门<font style="color:red;">*</font>',
																	queryParam : 'filter_LIKES_departName',
																	forceSelection : true,
																	minChars : 0,
																	mode : "remote",//获取服务器的值
																	valueField : 'departId',//value值，与fields对应
																	displayField : 'departName',//显示值，与fields对应
																	name:'startDepartName',
																	allowBlank : false,
																	anchor : '95%',
																	listeners:{
																		select:function(combo,e){
																			Ext.getCmp('startDepartId').setValue(combo.getValue());
																		}
																	}
																},{
																	name:'endDepartId',
																	id:'endDepartId',
																	xtype:'hidden'
																},{
																	xtype : 'combo',
																	triggerAction : 'all',
																	store : departStore,
																	fieldLabel : '到达部门<font style="color:red;">*</font>',
																	queryParam : 'filter_LIKES_departName',
																	forceSelection : true,
																	minChars : 0,
																	mode : "remote",//获取服务器的值
																	valueField : 'departId',//value值，与fields对应
																	displayField : 'departName',//显示值，与fields对应
																	name:'endDepartName',
																	allowBlank : false,
																	anchor : '95%',
																	listeners:{
																		select:function(combo,e){
																			Ext.getCmp('endDepartId').setValue(combo.getValue());
																		}
																	}
																},{
																	xtype : "combo",
																	editable:false,
																	triggerAction : 'all',
																	fieldLabel:'计算频率<font style="color:red;">*</font>',
																	allowBlank : false,
																	store : countRangeStore,
																	mode : "remote",// 从本地载值
																	//valueField : 'distributionModeId',// value值，与fields对应
																	displayField : 'countRangeName',// 显示值，与fields对应
																    name : 'countRange',
																	anchor : '95%'
																}]
														},{
														
														columnWidth : .5,
														layout : 'form',
														items : [{
																	xtype : 'timefield',
																	labelAlign : 'left',
																	format:'H:i',
																	forceSelection : true,
																	minChars : 0,
																	fieldLabel : '干线车发车时间<font style="color:red;">*</font>',
																	name : 'outCarTime',
																	maxLength:5,
																	allowBlank : false,
																	anchor : '95%'
																},{
																	xtype : 'timefield',
																	labelAlign : 'left',
																	format:'H:i',
																	forceSelection : true,
																	minChars : 0,
																	fieldLabel : '干线车到达时间<font style="color:red;">*</font>',
																	name : 'reachCarTime',
																	maxLength:5,
																	allowBlank : false,
																	anchor : '95%'
																},{
																	xtype : 'numberfield',
																	labelAlign : 'left',
																	fieldLabel : '车辆运行时间<font style="color:red;">*</font>',
																	name : 'runCarTime',
																	maxLength:5,
																	allowBlank : false,
																	anchor : '95%'
																},{
																	xtype : 'timefield',
																	labelAlign : 'left',
																	allowBlank : false,
																	format:'H:i',
																	forceSelection : true,
																	minChars : 0,
																	fieldLabel : '自动计算时间<font style="color:red;">*</font>',
																	maxLength:5,
																	name : 'countTime',
																	anchor : '95%'
																}
														]

													}]
													
										}]
										});
									
		enterPortTitle='添加干线车标准信息';
		if(cid!=null){
		enterPortTitle='修改干线车标准信息';
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
		width : 500,
		closeAction : 'hide',
		plain : true,
		resizable : false,
		listeners:{
			'beforeshow':function(){
				run();
			}
		},
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
												this.disabled = false;//可以修改继续保存
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
 
 function run(){
    var all = Ext.query('input[type!=hidden]'); // 
    Ext.each(all, function(o, i, all) { // 
    		  // Ext.get(o).dom.
               Ext.get(o).addKeyMap({   
                    key : 13,   
                    fn : function() {   
                        try {   
                            all[i + 1].focus()   
                        } catch (e) {   
                            event.keyCode = 9  
                        }   
                        if (all[i + 1]   
                                && /button|reset|submit/.test(all[i + 1].type))   
                            all[i + 1].click(); // 
  
                        return true;   
                    }   
                })   
            });   
    Ext.getBody().focus(); //
  
    try {   
        var el;   
        if (typeof eval(xFocus) == 'object') { // 
            el = Ext.getDom(xFocus).tagName == 'input'  
                    ? Ext.getDom(xFocus)   
                    : Ext.get(xFocus).first('input', true); // 
        } else {   
            el = all[xFocus || 0]; // 
        }   
        el.focus();   
    } catch (e) {   
    }   
}   
Ext.isReady ? run() : Ext.onReady(run); // 
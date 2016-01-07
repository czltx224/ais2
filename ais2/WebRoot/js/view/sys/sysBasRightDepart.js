//权限部门管理js
var privilege=63;//权限参数
var gridSearchUrl="sys/basRightDepartAction!ralaList.action";
var saveUrl="sys/basRightDepartAction!save.action";
var delUrl="sys/basRightDepartAction!delete.action";
var userUrl='user/userAction!list.action';//用户查询地址
var departUrl='sys/departAction!findAll.action';

var departStore = new Ext.data.Store({
        storeId:"departStore",
        proxy: new Ext.data.HttpProxy({url:sysPath+'/'+departUrl}),
        baseParams:{filter_EQL_isBussinessDepa:1},
        
        reader: new Ext.data.JsonReader({
            root: 'result', totalProperty: 'totalCount'
        }, [
            {name:'departId'},
            {name: 'departName'}
        ])
        });
	//用户 userUrl
		cusServiceStore=new Ext.data.Store({
	        autoLoad:true,
	        storeId:"cusServiceStore",
			proxy:new Ext.data.HttpProxy({url:sysPath+"/"+userUrl}),
			reader:new Ext.data.JsonReader({
	                    root:'result',
	                    totalProperty:'totalCount'
	        },[
        	{name:'userId',mapping:'id'},
        	{name:'userName',mapping:'userName'}
        	])
        	
		});
 fields=["id",'userId','userName','rightDepartid','departName','createName','createTime','updateName','updateTime','ts'];
		
	 var tbar=[{text:'<b>新增</b>',iconCls:'userAdd',id:'rightDepartAdd',tooltip : '新增权限部门',handler:function() {
	 			
                	saverightDepart(null);
            } },'-',
	 	{text:'<b>修改</b>',iconCls:'userEdit',id:'rightDepartEdit',disabled:true,tooltip : '修改权限部门信息',handler:function(){
	 			var rightDepart =Ext.getCmp('rightDepartCenter');
				var _records = rightDepart.getSelectionModel().getSelections();
	
					if (_records.length < 1) {
						top.Ext.Msg.alert(alertTitle, "请选择您要修改的行！");
						return false;
					} else if (_records.length > 1) {
						top.Ext.Msg.alert(alertTitle, "一次只能修改一行！");
						return false;
					}
					//alert(_records[0].data.id);
	 				saverightDepart(_records);
	 	}},'-',
	 	{text:'<b>删除</b>',iconCls:'userDelete',id:'rightDepartDelete',disabled:true,tooltip : '删除权限部门',handler:function(){
	 		var rightDepart =Ext.getCmp('rightDepartCenter');
			var _records = rightDepart.getSelectionModel().getSelections();
	
					if (_records.length < 1) {
						top.Ext.Msg.alert(alertTitle, "请选择您要删除的行！");
						return false;
					} 

					top.Ext.Msg.confirm(alertTitle, "确定要删除"+_records.length+"条记录吗？", function(
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
											ids :ids,
											 privilege:privilege,limit : pageSize
										},
										success : function(resp) {
											var respText = Ext.util.JSON
													.decode(resp.responseText);

											top.Ext.Msg.alert(alertTitle,
													  "删除成功!");

											dataReload();
										}
									});
								}
							});
	 	}},'-',{
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
	 	{xtype:'textfield',blankText:'查询数据',id:'searchContent',enableKeyEvents:true,listeners : {
	 		
	 		keyup:function(textField, e){

                     if(e.getKey() == 13){

                     	searchrightDepart();

                     }
	 		}
	 		}
	 	},
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
    							['EQD_createTime', '创建日期'],
    							['EQD_updateTime', '修改日期']],
    					emptyText : '选择类型',
    					forceSelection : true,
    					listeners : {
    						
    						'select' : function(combo, record, index) {
    							if(combo.getValue()==''){searchrightDepart();}
    							if (combo.getValue() == 'EQD_createTime' || combo.getValue() == 'EQD_updateTime') {
    								Ext.getCmp("startDate").enable();
    								Ext.getCmp("startDate").show();

    								Ext.getCmp("endDate").enable();
    								Ext.getCmp("endDate").show();
    								
    								Ext.getCmp("searchContent").disable();
    								Ext.getCmp("searchContent").hide();
    								Ext.getCmp("searchContent").setValue("");
    							}else{
    					         	
    						  		Ext.getCmp("startDate").hide();
    								Ext.getCmp("startDate").setValue("");

    								
    								Ext.getCmp("endDate").hide();
    								Ext.getCmp("endDate").setValue("");
    								
    						      	Ext.getCmp("searchContent").enable();
    								Ext.getCmp("searchContent").show();
    								
    								if(Ext.getCmp("searchContent").getValue().length>0){
    									searchrightDepart();
    								}
    							
    							}
    						}
    					}
    					
    				},'-','部门:', {
								xtype : 'combo',
								triggerAction : 'all',
								id:'searchDepart',
								typeAhead : true,
								queryParam : 'filter_LIKES_departName',
								store : departStore,
								pageSize : pageSize,
								forceSelection : true,
								fieldLabel : '部门<font style="color:red;">*</font>',
								minChars : 0,
								valueField : 'departId',//value值，与fields对应
								displayField : 'departName',//显示值，与fields对应
								hiddenName:'departId',
								anchor : '95%',
								enableKeyEvents:true,listeners : {
						 		    select:function(combo, e){
					                    searchrightDepart();
						 		    }
						 		}
							},'-',{text : '<b>搜索</b>',id : 'btn',iconCls : 'btnSearch',
    					handler : searchrightDepart
    				}			
	 	];
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
    rightDepartGrid = new Ext.grid.GridPanel({
        renderTo: Ext.getBody(),
        id:'rightDepartCenter',
        height:Ext.lib.Dom.getViewHeight(),		
        width:Ext.lib.Dom.getViewWidth(),
        viewConfig : {
			columnsText : "显示的列",
			sortAscText : "升序",
			sortDescText : "降序",
			forceFit : true
		},
		
        frame:true, 
        loadMask:true,
        sm: sm,
        cm:new Ext.grid.ColumnModel([rowNum,sm,
     		{header: 'ID', dataIndex: 'id',width:60,sortable : true,hidden:true},
	        {header: '权限部门ID', dataIndex: 'rightDepartid',width:60,sortable : true,hidden:true},
  			{header: '部门名称', dataIndex: 'departName',width:60,sortable : true},
  			{header: '分配用户', dataIndex: 'userName',width:60,sortable : true},
  			{header: '创建人', dataIndex: 'createName',width:60,sortable : true},
  			{header: '创建时间', dataIndex: 'createTime',width:60,sortable : true},
  			{header: '修改人', dataIndex: 'updateName',width:60,sortable : true},
  			{header: '修改时间', dataIndex: 'updateTime',width:60,sortable : true},
  			{header: '时间戳', dataIndex: 'ts',width:100,sortable : true,hidden:true}
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
   
	//searchrightDepart();
	
    rightDepartGrid.on('click', function() {
        selabled();
    });
    rightDepartGrid.on('rowdblclick',function(grid,index,e){
		var _records = rightDepartGrid.getSelectionModel().getSelections();
				
				if (_records.length ==1) {
					saverightDepart(_records);
				}
			 	
		});
    });
    
	

function searchrightDepart() {
		dateStore.proxy = new Ext.data.HttpProxy({
					method : 'POST',
					url : sysPath+'/'+gridSearchUrl,
					params : {
						start : 0,
						privilege:privilege,
						limit : pageSize
					}
					
				});
			var searchDepart = Ext.getCmp('searchDepart').getValue();
	 		dateStore.on('beforeload', function(store,options)
		 	{
	 			Ext.apply(options.params, {
					filter_EQL_rightDepartid : searchDepart
				})
		 		if (Ext.getCmp('searchSelectBox').getValue() == 'EQD_updateTime') {
			 		Ext.apply(options.params, {
			 			filter_GED_updateTime :(!Ext.getCmp("startDate").hidden?(Ext.get("startDate").dom.value=="开始时间"?"":Ext.get("startDate").dom.value):""),
	    				filter_LTD_updateTime : (!Ext.getCmp("endDate").hidden?(Ext.get("endDate").dom.value=="结束时间"?"":Ext.get("endDate").dom.value):"")
	    						
			 		})
			 	}else if (Ext.getCmp('searchSelectBox').getValue() == 'EQD_createTime') {
			 		Ext.apply(options.params, {
			 			filter_GED_updateTime :(!Ext.getCmp("startDate").hidden?(Ext.get("startDate").dom.value=="开始时间"?"":Ext.get("startDate").dom.value):""),
    					filter_LTD_updateTime : (!Ext.getCmp("endDate").hidden?(Ext.get("endDate").dom.value=="结束时间"?"":Ext.get("endDate").dom.value):"")
			 		})
			 	}else{
			 		Ext.apply(options.params, {
						checkItems : Ext.get("checkItems").dom.value,
						itemsValue : Ext.get("searchContent").dom.value
					})
			 	}
		 	});
		
		var editbtn = Ext.getCmp('rightDepartEdit');
		var deletebtn = Ext.getCmp('rightDepartDelete');

		editbtn.setDisabled(true);
		deletebtn.setDisabled(true);

		dataReload();
		
	}
function saverightDepart(_records) {
					if(_records!=null)
					var did=_records[0].data.id;
					var form = new Ext.form.FormPanel({
								labelAlign : 'left',
								frame : true,
								url:sysPath+'/'+gridSearchUrl,
								baseParams:{
				    				filter_EQL_id:did,
									privilege:privilege,
									limit : pageSize},
								bodyStyle : 'padding:5px 5px 0',
								width : 350,
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
																},{xtype : "combo",
																		//typeAhead : true,
																		pageSize : comboSize,
																		fieldLabel : '分配客户<font style="color:red;">*</font>',
																		forceSelection : true,
																		selectOnFocus : true,
																		resizable : true,
																		minChars : 0,
																		queryParam : 'filter_LIKES_userName',
																		id:'xbUserId',
																		triggerAction : 'all',
																		store : cusServiceStore,
																		mode : "remote",// 从服务器端加载值
																		valueField : 'userId',// value值，与fields对应
																		displayField : 'userName',// 显示值，与fields对应
																	    hiddenName : 'userId',
																	    anchor : '95%'
																},{
																	xtype : 'combo',
																	triggerAction : 'all',
																	id:'mycombo',
																	//typeAhead : true,
																	queryParam : 'filter_LIKES_departName',
																	store : departStore,
																	pageSize : pageSize,
																	allowBlank : false,
																	emptyText : "请选择部门",
																	forceSelection : true,
																	fieldLabel : '部门<font style="color:red;">*</font>',
																	minChars : 0,
																	valueField : 'departId',//value值，与fields对应
																	displayField : 'departName',//显示值，与fields对应
																	hiddenName:'rightDepartid',
																	anchor : '95%'
																}]
													}]
													
										}]
							});
		
		rightDepartTitle='添加权限部门信息';
		if(did!=null){
			departStore.load();
			rightDepartTitle='修改权限部门信息';
			
			form.load({
						waitMsg : '正在载入数据...',
		    			success : function(_form, action) {
				   			//form.load();,_records[0].data.departName
				   			Ext.getCmp('mycombo').setValue(_records[0].data.rightDepartid);
				   			Ext.getCmp('mycombo').setRawValue(_records[0].data.departName);
				   			
				   			Ext.getCmp('xbUserId').setValue(_records[0].data.userId);
				   			Ext.getCmp('xbUserId').setRawValue(_records[0].data.userName);
		    			},
		    			failure : function(_form, action) {
		    				Ext.MessageBox.alert(alertTitle, '载入失败');
		    			}
					});
		
		}

		var win = new Ext.Window({
		title : rightDepartTitle,
		width : 350,
		closeAction : 'hide',
		plain : true,
		modal : true,
		items : form,
		buttonAlign : "center",
		buttons : [{
			text : "保存",
			iconCls : 'groupSave',
			handler : function() {
				if (form.getForm().isValid()) {
					this.disabled = true;
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
									Ext.Msg.alert(alertTitle,action.result.msg);
		
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
				if(did==null){
					form.getForm().reset();
				}
				if(did!=null){
					rightDepartTitle='修改权限部门信息';
					form.load({
						waitMsg : '正在载入数据...',
		    			success : function(_form, action) {
				   			//form.load();,_records[0].data.departName
				   			Ext.getCmp('mycombo').setValue(_records[0].data.rightDepartid);
				   			Ext.getCmp('mycombo').setRawValue(_records[0].data.departName);
				   			
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
	var _record = rightDepartGrid.getSelectionModel().getSelections();
        var updatebtn = Ext.getCmp('rightDepartEdit');
        var deletebtn = Ext.getCmp('rightDepartDelete');
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
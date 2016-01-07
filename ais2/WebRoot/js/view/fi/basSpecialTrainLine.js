//专车线路管理js
var privilege=94;//权限参数
var gridSearchUrl="fi/basSpecialTrainLineAction!list.action";//专车线路查询地址
var gridSearchfindListUrl="fi/basSpecialTrainLineAction!findList.action";//专车线路明细查询地址
var savespecialTrainLineDetailUrl="fi/basSpecialTrainLineDetailAction!save.action";//专车线路细表保存地址
var delspecialTrainLineDetailUrl="fi/basSpecialTrainLineDetailAction!delete.action";//专车线路细表删除地址
var saveUrl="fi/basSpecialTrainLineAction!save.action";//保存地址 
var delUrl="fi/basSpecialTrainLineAction!delete.action";//删除地址
var authorityDepartUrl='sys/basRightDepartAction!findDepartName.action';//权限部门查询地址
var searchAddrUrl='sys/basAreaAction!list.action';//地区查询地址
var userUrl='user/userAction!list.action';//用户查询地址
var gridRemarkUrl='stock/oprRemarkAction!list.action';
var searchWidth=70;
var searchDepart=bussDepart;
var pubId;
var  fields=[{name:"id",mapping:'id'},
     		{name:'lineName',mapping:'lineName'},
     		{name:'createName',mapping:'createName'},
     		{name:'createTime',mapping:'createTime'},
     		{name:'updateName',mapping:'updateName'},
     		{name:'updateTime',mapping:'updateTime'},
     		{name:'ts',mapping:'ts'},
     		{name:'areaName',mapping:'areaName'},
     		{name:'departId',mapping:'departId'},
     		{name:'departName',mapping:'departName'}];
     		
var  fieldstwo=[{name:"id",mapping:'ID'},
     		{name:'lineName',mapping:'LINE_NAME'},
     		{name:'createName',mapping:'CREATE_NAME'},
     		{name:'createTime',mapping:'CREATE_TIME'},
     		{name:'updateName',mapping:'UPDATE_NAME'},
     		{name:'updateTime',mapping:'UPDATE_TIME'},
     		{name:'ts',mapping:'TS'},
     		{name:'areaName',mapping:'AREA_NAME'},
     		{name:'departId',mapping:'DEPART_ID'},
     		{name:'departName',mapping:'DEPART_NAME'},
     		{name:'detailId',mapping:'DETAIL_ID'}];
      //用户查询地址 cusServiceUrl
		userStore=new Ext.data.Store({
	        storeId:"userStore",
			proxy:new Ext.data.HttpProxy({url:sysPath+"/"+userUrl}),
			reader:new Ext.data.JsonReader({
	                    root:'result',
	                    totalProperty:'totalCount'
	        },[
        	{name:'userId',mapping:'id'},
        	{name:'userName',mapping:'userName'}
        	
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
        
       //区域名称
     	areaStore= new Ext.data.Store({ 
			storeId:"endAddStore",
			baseParams:{privilege:55,limit:comboSize},
			proxy:new Ext.data.HttpProxy({url:sysPath+"/"+searchAddrUrl}),
			reader:new Ext.data.JsonReader({
	                    root:'result',
	                    totalProperty:'totalCount'
	        },[
        	{name:'areaName',mapping:'text'}
        	])
		});
		//线路名称
		lineStore= new Ext.data.Store({ 
			autoLoad:true,
			storeId:"lineStore",
			baseParams:{privilege:privilege,limit:comboSize},
			proxy:new Ext.data.HttpProxy({url:sysPath+"/"+gridSearchUrl}),
			reader:new Ext.data.JsonReader({
	                    root:'result',
	                    totalProperty:'totalCount'
	        },[
        	{name:'lineId',mapping:'id'},
        	{name:'lineName',mapping:'lineName'}
        	])
		});
    var menu = new Ext.menu.Menu({
		    id: 'receiptMenu',
		    items: [{text:'<b>添加专车线路</b>',iconCls : 'userAdd',tooltip : '添加专车线路',handler:function() {
          				savebasSpecialTrainLine(null);
            } },{text:'<b>添加线路地区</b>',iconCls : 'userAdd',tooltip : '添加线路地区',handler:function() {
           				savebasSpecialTrainLineDetail(null);
            } } ]
				});
	var menuUpdate = new Ext.menu.Menu({
		    id: 'menuUpdate',
		    items: [{text:'<b>修改专车线路</b>',iconCls:'userEdit',id:'basSpecialTrainLineEdit',handler:function(){
				 		var basSpecialTrainLine =Ext.getCmp('basSpecialTrainLineCenter');
						var _records = basSpecialTrainLine.getSelectionModel().getSelections();
					
						if (_records.length < 1) {
							Ext.Msg.alert(alertTitle, "请选择您要修改的行！");
							return false;
						} else if (_records.length > 1) {
							Ext.Msg.alert(alertTitle, "一次只能修改一行！");
							return false;
						}
				 		savebasSpecialTrainLine(_records);}
				 	},{text:'<b>修改线路地区</b>',iconCls : 'userEdit',tooltip : '修改线路地区',handler:function() {
				 		var basSpecialTrainLine =Ext.getCmp('basSpecialTrainLineCenter');
						var _records = basSpecialTrainLine.getSelectionModel().getSelections();
					
						if (_records.length < 1) {
							Ext.Msg.alert(alertTitle, "请选择您要修改的行！");
							return false;
						} else if (_records.length > 1) {
							Ext.Msg.alert(alertTitle, "一次只能修改一行！");
							return false;
						}
           				savebasSpecialTrainLineDetail(_records);
            } } ]
	});
	var menuDelete = new Ext.menu.Menu({
		    id: 'menuDelete',
		    items: [{text:'<b>删除专车线路</b>',iconCls:'userDelete',id:'basSpecialTrainLineDelete',handler:function(){
	 		var basSpecialTrainLine =Ext.getCmp('basSpecialTrainLineCenter');
			var _records = basSpecialTrainLine.getSelectionModel().getSelections();
	
					if (_records.length < 1) {
						Ext.Msg.alert(alertTitle, "请选择您要删除的行！");
						return false;
					} 

					Ext.Msg.confirm(alertTitle, "确定要删除这"+_records.length+"条记录吗？", function(
									btnYes) {
								if (btnYes == 'yes' || btnYes == 'ok'
										|| btnYes == true) {
									var ids="";
									for(var i=0;i<_records.length;i++){
									
										 ids=ids+_records[i].data.id+',';
									}
									Ext.Ajax.request({
										url : sysPath+'/'+ delUrl,
										params : {
											ids : ids,
										    privilege:privilege,
										    limit : pageSize
										},
										success : function(resp) {
											var respText = Ext.util.JSON
													.decode(resp.responseText);
											if(respText.success){
												Ext.Msg.alert(alertTitle,
														  "删除成功!");
												dataReload();
											}else{
												Ext.Msg.alert(alertTitle,
														  respText.msg);
											}
										}
									});
								}
							});
	 	}},'-',{text:'<b>删除线路地区</b>',iconCls : 'userDelete',tooltip : '删除线路地区',handler:function() {
				 		var basSpecialTrainLine =Ext.getCmp('basSpecialTrainLineCenter');
						var _records = basSpecialTrainLine.getSelectionModel().getSelections();
					
						if (_records.length < 1) {
							Ext.Msg.alert(alertTitle, "请选择您要删除的行！");
							return false;
						} 
           				Ext.Msg.confirm(alertTitle, "确定要删除这"+_records.length+"条记录吗？", function(
									btnYes) {
								if (btnYes == 'yes' || btnYes == 'ok'
										|| btnYes == true) {
									var ids="";
									for(var i=0;i<_records.length;i++){
									
										 ids+=+_records[i].data.detailId;
										 if(i!=_records.length){
										 	ids+=',';
										 }
									}
									Ext.Ajax.request({
										url : sysPath+'/'
												+ delspecialTrainLineDetailUrl,
										params : {
											ids : ids,
										    privilege:privilege,
										    limit : pageSize
										},
										success : function(resp) {
											var respText = Ext.util.JSON
													.decode(resp.responseText);
											if(respText.success){
												Ext.Msg.alert(alertTitle,
														  "删除成功!");
												dataReload();
											}else{
												Ext.Msg.alert(alertTitle,
														  respText.msg);
											}
										}
									});
								}
							});
            } } ]
	});
	 var specialTrainAdd=new Ext.Button(
	 	{
			text : '<B>添加</B>',
			id : 'specialTrainAdd',
			tooltip : '添加操作',
			iconCls : 'groupAdd',
			menu: menu
   		});	
   	var specialTrainUpdate=new Ext.Button(
	 	{
			text : '<B>修改</B>',
			id : 'specialTrainUpdate',
			tooltip : '修改操作',
			iconCls : 'groupEdit',
			menu: menuUpdate
   		});	
   		var specialTrainDelete=new Ext.Button(
	 	{
			text : '<B>删除</B>',
			id : 'specialTrainDelete',
			tooltip : '删除操作',
			iconCls : 'groupDelete',
			menu: menuDelete
   		});	
	var tbar=[{text:'<b>导出</b>',iconCls:'sort_down',handler:function() {
                	parent.exportExl(basSpecialTrainLineGrid);
        } },'-',
		specialTrainAdd,'-',specialTrainUpdate,'-',specialTrainDelete,
	 	{
    			xtype : 'datefield',
    		id : 'startDate',
    		format : 'Y-m-d',
    		emptyText : "开始时间",
    		anchor : '95%',
    		value:new Date().add(Date.DAY, -7),
    		hidden : false,
    		width : 100,
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
    		hidden : false,
    		width : 100,
    		anchor : '95%'
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
    						    ['EQD_createTime','创建时间'],
    						    ['EQD_updateTime', '最后修改时间']],
    							
    					emptyText : '查询全部',
    					editable : false,
    					forceSelection : true,
    					listeners : {
    						
    						'select' : function(combo, record, index) {
    							//查询全部
    							if(combo.getValue()==''){
    								Ext.getCmp("startDate").setValue("");
    								Ext.getCmp("endDate").setValue("");
    								Ext.getCmp("searchcreateName").setValue("");
    								Ext.getCmp("searchupdateName").setValue("");
    								Ext.getCmp("searchAuthorityDepart").setValue("");
    								
    								searchbasSpecialTrainLine();
    							}
    						}
    					}
    					
    				}
	 	];	

var queryTbar=new Ext.Toolbar(['创建人:',{xtype : "combo",
				width : searchWidth+20,
				// typeAhead : true,
				pageSize : comboSize,
				forceSelection : true,
				selectOnFocus : true,
				resizable : true,
				minChars : 0,
				queryParam : 'filter_LIKES_userName',
				triggerAction : 'all',
				store : userStore,
				mode : "remote",// 从服务器端加载值
				valueField : 'userId',// value值，与fields对应
				displayField : 'userName',// 显示值，与fields对应
			    name : 'createName',
			    id:'searchcreateName', 
			    enableKeyEvents:true,listeners : {
				keyup:function(textField, e){
	                 if(e.getKey() == 13){
	                 	searchbasSpecialTrainLine();
	                 }
				}
	 		}
	 	},'-','修改人:',{xtype : "combo",
				width : searchWidth+20,
				// typeAhead : true,
				pageSize : comboSize,
				forceSelection : true,
				selectOnFocus : true,
				resizable : true,
				minChars : 0,
				queryParam : 'filter_LIKES_userName',
				triggerAction : 'all',
				store : userStore,
				mode : "remote",// 从服务器端加载值
				valueField : 'userId',// value值，与fields对应
				displayField : 'userName',// 显示值，与fields对应
			    name : 'updateName',
			    id:'searchupdateName', 
			    enableKeyEvents:true,listeners : {
					 keyup:function(textField, e){
	                 if(e.getKey() == 13){
	                 	searchbasSpecialTrainLine();
	                 }
	 		}
	 	}},'-','部门名称：',
			{xtype : 'combo',
				width : searchWidth+20,
				triggerAction : 'all',
				id:'searchAuthorityDepart',
				pageSize:comboSize,
				store : authorityDepartStore,
				resizable:true,
				forceSelection : true,
				valueField : 'departId',//value值，与fields对应
				displayField : 'departName',//显示值，与fields对应
				editable : false,
				name : 'departId'
				
			},'-','线路名称：',{
				xtype:'textfield',id:'searclineName',
				width : searchWidth+20,
				enableKeyEvents:true,listeners : {
					 keyup:function(textField, e){
	                 if(e.getKey() == 13){
	                 	searchbasSpecialTrainLine();
	                 }
					}
				}
			},'-','区域名称：',{
				xtype:'textfield',id:'searchAreaName',
				width : searchWidth+20,
				enableKeyEvents:true,listeners : {
					 keyup:function(textField, e){
	                 if(e.getKey() == 13){
	                 	searchbasSpecialTrainLine();
	                 }
					}
				}
			},'-',
	 		{text : '<b>查询</b>',id : 'btn',iconCls : 'btnSearch',
    					handler : searchbasSpecialTrainLine
    		}
	 	]);	
	 	
Ext.onReady(function() {
   	dateStore = new Ext.data.Store({
        storeId:"dateStore",
        proxy: new Ext.data.HttpProxy({url : sysPath+ "/"+gridSearchfindListUrl,
		params:{privilege:privilege,limit : pageSize}}),
		sortInfo:{field:'createTime',direction:'DESC'},
        reader: new Ext.data.JsonReader({
            root: 'resultMap', totalProperty: 'totalCount'
        }, fieldstwo)
    });
   
   var sm = new Ext.grid.CheckboxSelectionModel({});
	var rowNum = new Ext.grid.RowNumberer({
        header:'序号', width:20
    });
    basSpecialTrainLineGrid = new Ext.grid.GridPanel({
        renderTo: Ext.getBody(),
        id:'basSpecialTrainLineCenter',
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
        		{header: '序号ID', dataIndex: 'id',sortable:true,hidden:true},
       			{header: '线路名称', dataIndex: 'lineName',sortable : true},
       			{header: '线路明细ID', dataIndex: 'detailId',sortable : true},
       			{header: '地区', dataIndex: 'areaName',sortable : true},
        		{header: '创建人', dataIndex: 'createName',sortable : true},
        		{header: '创建时间', dataIndex: 'createTime',sortable : true},
 				{header: '修改人', dataIndex: 'updateName',sortable : true},
 				{header: '修改时间', dataIndex: 'updateTime',sortable : true},
 				{header: '部门名称', dataIndex: 'departName',sortable : true}
        ]),
        store:dateStore,
        tbar: tbar,
        listeners: {
                    render: function(){
                        queryTbar.render(this.tbar);
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
		basSpecialTrainLineGrid.on('rowdblclick',function(grid,index,e){
		var _records = basSpecialTrainLineGrid.getSelectionModel().getSelections();
				if (_records.length ==1) {
					searchSpecialTrainLineDetail(_records);
				}
			 	
		});
    });
    
    
	
   function searchbasSpecialTrainLine() {
		dateStore.baseParams={privilege:privilege,limit : pageSize};
		
			var searchcreateName = Ext.getCmp('searchcreateName').getRawValue();
			var searchupdateName = Ext.getCmp('searchupdateName').getRawValue();
			var searchAuthorityDepart = Ext.getCmp('searchAuthorityDepart').getValue();
			var searclineName = Ext.getCmp('searclineName').getValue();
			var searchAreaName = Ext.getCmp('searchAreaName').getValue();
			if(searchAuthorityDepart.length>0){
				searchDepart=searchAuthorityDepart;
			}else{
				searchDepart=bussDepart;
			}
			
			 Ext.apply(dateStore.baseParams, {
				filter_LIKES_d_createName:searchcreateName,
				filter_LIKES_d_updateName:searchupdateName,
				filter_t_departId:searchDepart,
				filter_LIKES_t_lineName:searclineName,
				filter_LIKES_d_areaName:searchAreaName,
				privilege:privilege,
				limit : pageSize
				
			});
			 if  (Ext.getCmp('searchSelectBox').getValue() == 'EQD_createTime') {
    				  Ext.apply(dateStore.baseParams, {
    				  		filter_countCheckItems:'d.create_time',
    						filter_startCount:(!Ext.getCmp("startDate").hidden?(Ext.get("startDate").dom.value=="开始时间"?"":Ext.get("startDate").dom.value):""),
    						filter_endCount : (!Ext.getCmp("endDate").hidden?(Ext.get("endDate").dom.value=="结束时间"?"":Ext.get("endDate").dom.value):"")
    					});
    		} else if (Ext.getCmp('searchSelectBox').getValue() == 'EQD_updateTime') {
    				 Ext.apply(dateStore.baseParams, {
    				 		filter_countCheckItems:'d.create_time',
    						filter_startCount:(!Ext.getCmp("startDate").hidden?(Ext.get("startDate").dom.value=="开始时间"?"":Ext.get("startDate").dom.value):""),
    						filter_endCount : (!Ext.getCmp("endDate").hidden?(Ext.get("endDate").dom.value=="结束时间"?"":Ext.get("endDate").dom.value):"")
    					});
    		}
		
		dataReload();
	}
function savebasSpecialTrainLine(_records) {
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
														columnWidth : .9,
														layout : 'form',
														items : [{
																	name : "id",
																	xtype : "hidden"
																},{
																	name : "ts",
																	xtype : "hidden"
																},{
																	name:'lineName',
																	id:'lineNameId',
																	xtype:'textfield',
																	allowBlank : false,
																	blankText : "线路名称不能为空！",
																	fieldLabel : '线路名称<font style="color:red;">*</font>',
																	anchor : '95%'
																}
																]
													}]
													
										}]
										});
									
		basSpecialTrainLineTitle='添加专车线路信息';
		if(cid!=null){
		basSpecialTrainLineTitle='修改专车线路信息';
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
		title : basSpecialTrainLineTitle,
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
										win.hide();
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
 function setLineName(){
 	var record = basSpecialTrainLineGrid.getSelectionModel().getSelected();
	if(record!=null){
		Ext.getCmp('lineNameId').setValue(record.data.id);
	}
 }
 function savebasSpecialTrainLineDetail(_records){
 				
 				var did;
 				var detailId;
				if(_records!=null){
					did=_records[0].data.id;
					detailId=_records[0].data.detailId;
 				}
					var form = new Ext.form.FormPanel({
								labelAlign : 'left',
								frame : true,
								url:sysPath+'/'+gridSearchfindListUrl,
								baseParams:{
				    				filter_t_id:did,
				    				filter_d_id:detailId
									},
								bodyStyle : 'padding:5px 5px 0',
								width : 300,
								reader : new Ext.data.JsonReader({
			            root: 'resultMap', totalProperty: 'totalCount'
			        },fieldstwo),
					labelAlign : "right",
				
						items : [{
								layout : 'column',
								items : [{
										columnWidth : .9,
										layout : 'form',
										items : [{
													name : "departId",
													xtype : "hidden",
													value:searchDepart
												},{
													name : "detailId",
													xtype : "hidden"
												},{
													name : "ts",
													xtype : "hidden"
												},{
													name : "specialTrainLineId",
													id:'lineNameId',
													xtype : "hidden"
												},{
													xtype : "combo",
													triggerAction : 'all',
													queryParam : 'filter_LIKES_lineName',
													pageSize : comboSize,
													forceSelection : true,
													resizable:true,
													minChars : 0,
													fieldLabel:'专车线路',
													allowBlank : false,
													store : lineStore,
													mode : "remote",// 
													valueField : 'lineId',// value值，与fields对应
													displayField : 'lineName',// 显示值，与fields对应
													name:'lineName',
													anchor : '95%',
												    enableKeyEvents:true,
													listeners : {
													    select:function(combo, e){
												             Ext.getCmp('lineNameId').setValue(combo.getValue());
														}
													}
												},{
													xtype : "combo",
													triggerAction : 'all',
													queryParam : 'filter_LIKES_areaName',
													pageSize : comboSize,
													forceSelection : true,
													resizable:true,
													minChars : 0,
													fieldLabel:'区域名称',
													allowBlank : false,
													store : areaStore,
													mode : "remote",// 从本地载值
													valueField : 'areaName',// value值，与fields对应
													displayField : 'areaName',// 显示值，与fields对应
													name : 'areaName',
													id:'areaNameId',
													anchor : '95%',
													enableKeyEvents:true,
													listeners : {
													    keyup:function(textField, e){
												             if(e.getKey() == 13){
												             	//searchprojectRate();
												             }
														}
													}
												}
											]
									}]
										
							}]
							});
		basSpecialTrainLineTitle='添加专车线路地区信息';
		if(did!=null){
		basSpecialTrainLineTitle='修改专车线路地区信息';
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
		title : basSpecialTrainLineTitle,
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
					var specialTrainLineId=Ext.getCmp('lineNameId').getValue();
					var areaName=Ext.getCmp('areaNameId').getRawValue();
				
					form.getForm().submit({
					url : sysPath
							+ '/'+savespecialTrainLineDetailUrl,
							params:{privilege:privilege,limit : pageSize,id:detailId},
					waitMsg : '正在保存数据...',
					success : function(form, action) {
						 Ext.Msg.alert(alertTitle,
								action.result.msg, function() {
									dataReload();
									win.hide();
								});
					},
					failure : function(form, action) {
						if (action.faliureType == Ext.form.Action.SERVER_INVALID) {
							Ext.Msg.alert(alertTitle, "服务器连接异常，请联系管理员！");
						} else {
							if (action.result.msg) {
								Ext.Msg.alert(alertTitle,
										action.result.msg, function() {
											Ext.getCmp('lineNameId').focus();
										});

							}
						}
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
		setLineName();
 }

 function dataReload(){
			dateStore.reload({
			params : {
				start : 0,
				privilege:privilege,
				limit : pageSize
			}
		});
 }
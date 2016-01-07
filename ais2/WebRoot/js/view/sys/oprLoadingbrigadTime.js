//货物自提出库排班js
var privilege=228;//权限参数
var gridSearchUrl="stock/oprLoadingbrigadeTimeAction!ralaList.action";//查询地址
var saveUrl="stock/oprLoadingbrigadeTimeAction!save.action";//保存地址
var delUrl="stock/oprLoadingbrigadeTimeAction!delete.action";//删除地址
var dictionaryUrl='sys/dictionaryAction!ralaList.action';//数据字典地址
var loadingbrigadeUrl='sys/loadingbrigadeAction!list.action';//装卸组分拨组查询地址
var authorityDepartUrl='sys/basRightDepartAction!ralaList.action';//权限部门查询地址
var searchWidth=70;
var pubauthorityDepart=bussDepart;
var  fields=[{name:"id",mapping:'id'},
     		{name:'startDate',mapping:'startDate'},
     		{name:'endDate',mapping:'endDate'},
     		{name:'startDateString',mapping:'startDate'},
     		{name:'endDateString',mapping:'endDate'},
     		{name:'loadingbrigadId',mapping:'loadingbrigadId'},
     		{name:'groupId',mapping:'groupId'},
     		{name:'loadingbrigadName',mapping:'loadingbrigadName'},
     		{name:'groupName',mapping:'groupName'},
     		{name:'createName',mapping:'createName'},
     		{name:'createTime',mapping:'createTime'},
     		{name:'updateName',mapping:'updateName'},
     		{name:'updateTime',mapping:'updateTime'},
     		{name:'ts',mapping:'ts'},
     		{name:'departId',mapping:'departId'},
     		{name:'departName',mapping:'departName'}];
		//装卸组
	 loadingbrigadeStore= new Ext.data.Store({ 
		storeId:"loadingbrigadeStore",
		baseParams:{filter_EQL_type:0,filter_EQL_departId:bussDepart},
		proxy:new Ext.data.HttpProxy({url:sysPath+"/"+loadingbrigadeUrl}),
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
    	{name:'loadingbrigadeId',mapping:'id'},
    	{name:'loadingbrigadeName',mapping:'loadingName'}
    	])
	});
	
	//分拨组
	 dispatchGroupStore= new Ext.data.Store({ 
		storeId:"dispatchGroupStore",
		baseParams:{filter_EQL_type:1,filter_EQL_departId:bussDepart},
		proxy:new Ext.data.HttpProxy({url:sysPath+"/"+loadingbrigadeUrl}),
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
    	{name:'dispatchGroupId',mapping:'id'},
    	{name:'dispatchGroupName',mapping:'loadingName'}
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
            root: 'result', totalProperty: 'totalCount'
           }, [  {name:'departName', mapping:'departName',type:'string'},
            {name:'departId', mapping:'rightDepartid',type:'string'}             
              ])    
        });
		
	var tbar=[{text:'<b>导出</b>',iconCls:'sort_down',handler:function() {
                	parent.exportExl(oprLoadingBrigadGrid);
        } },'-',
		{text:'<b>新增</b>',iconCls:'userAdd',id:'basoprLoadingBrigadAdd',tooltip : '新增排班时间',handler:function() {
                	saveoprLoadingBrigad(null);
            } },'-',
	 	{text:'<b>修改</b>',iconCls:'userEdit',id:'basoprLoadingBrigadEdit',disabled:true,tooltip : '修改排班时间',handler:function(){
	 	var oprLoadingBrigad =Ext.getCmp('oprLoadingBrigadCenter');
		var _records = oprLoadingBrigad.getSelectionModel().getSelections();
		
						if (_records.length < 1) {
							Ext.Msg.alert(alertTitle, "请选择您要修改的行！");
							return false;
						} else if (_records.length > 1) {
							Ext.Msg.alert(alertTitle, "一次只能修改一行！");
							return false;
						}
	 	saveoprLoadingBrigad(_records);}},'-',
	 	{text:'<b>删除</b>',iconCls:'userDelete',id:'basoprLoadingBrigadDelete',disabled:true,tooltip : '删除排班时间',handler:function(){
	 		var oprLoadingBrigad =Ext.getCmp('oprLoadingBrigadCenter');
			var _records = oprLoadingBrigad.getSelectionModel().getSelections();
	
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
    			xtype : 'timefield',
    		id : 'startDate',
    		format:'H:i',
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
    		xtype : 'timefield',
    		id : 'endDate',
    		format:'H:i',
    		emptyText : "结束时间",
    		hidden : true,
    		width : 100,
    		disabled : true,
    		anchor : '95%'
    	},
	{xtype:'textfield',blankText:'查询数据',id:'searchContent', enableKeyEvents:true,listeners : {
	 		
	 		keyup:function(textField, e){

                     if(e.getKey() == 13){

                     	searchoprLoadingBrigad();

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
    							['EQS_createName', '创建人'],
    						    ['EQS_updateName', '修改人'],
    						    ['EQD_startDate', '开始日期'],
    						    ['EQD_endDate', '结束日期'],
    						    ['EQD_createTime','创建时间'],
    						    ['EQD_updateTime', '最后修改时间']],
    							
    					emptyText : '选择类型',
    					editable : false,
    					forceSelection : true,
    					listeners : {
    						
    						'select' : function(combo, record, index) {
    							
    							if (combo.getValue() == 'EQD_startDate' || 
    							combo.getValue() == 'EQD_endDate' ||
    							combo.getValue() == 'EQD_createTime' || 
    							combo.getValue() == 'EQD_updateTime' ) {
    								
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
    									searchoprLoadingBrigad();
    								}
    							
    							}
    							//查询全部
    							if(combo.getValue()==''){
    								
    								Ext.getCmp("searchdistributionMode").setValue("");
    								Ext.getCmp("searchtakeMode").setValue("");
    								Ext.getCmp("searchCpName").setValue("");
    								Ext.getCmp("searchtranMode").setValue("");
    								Ext.getCmp("searchaddressType").setValue("");
    								Ext.getCmp("searchContent").setValue("");
    								Ext.getCmp("searchvaluationType").setValue("");
    								
    								searchoprLoadingBrigad();
    							}
    						}
    					}
    					
    				}
    			
	 	];	

var queryTbar=new Ext.Toolbar([
	'部门名称:',{xtype : "combo",
				width : searchWidth+20,
				editable:true,
				triggerAction : 'all',
				typeAhead : true,
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
                     	searchoprLoadingBrigad();
                     }
	 		}
	 	
	 	}},
			'装卸组:<font color="red"><B>*</B></font>', {
			xtype : "combo",
			width : 100,
			id : 'loadingbrigade',
			queryParam : 'filter_LIKES_loadingName',
			pageSize : comboSize,
			minChars : 0,
			editable:true,
			forceSelection : true,
			selectOnFocus : true,
			resizable : true,
			triggerAction : 'all',
			store : loadingbrigadeStore,
			minChars : 0,
			mode : "remote",// 从本地加载值
			valueField : 'loadingbrigadeId',// value值，与fields对应
			displayField : 'loadingbrigadeName',// 显示值，与fields对应
			hiddenName : 'loadingbrigadeId'

	}, '-', '分拨组:<font color="red"><B>*</B></font>', {

			xtype : "combo",
			width : 100,
			id : 'dispatchGroup',
			typeAhead : true,
			queryParam : 'filter_LIKES_loadingName',
			pageSize : comboSize,
			minChars : 0,
			editable:true,
			forceSelection : true,
			selectOnFocus : true,
			resizable : true,
			triggerAction : 'all',
			store : dispatchGroupStore,
			minChars : 0,
			mode : "remote",// 从本地载值
			valueField : 'dispatchGroupId',// value值，与fields对应
			displayField : 'dispatchGroupName',// 显示值，与fields对应
			name : 'dispatchGroup'
	},
	 		{text : '<b>查询</b>',id : 'btn',iconCls : 'btnSearch',
    					handler : searchoprLoadingBrigad
    		}
	 	]);	
	 	
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
        header:'序号', width:30
    });
    oprLoadingBrigadGrid = new Ext.grid.GridPanel({
        renderTo: Ext.getBody(),
        id:'oprLoadingBrigadCenter',
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
       			{header: '开始日期', dataIndex: 'startDate',sortable : true},
        		{header: '结束日期', dataIndex: 'endDate',sortable : true},
        		{header: '装卸组', dataIndex: 'loadingbrigadName',sortable : true},
 				{header: '分拨组', dataIndex: 'groupName',sortable : true},
 				{header: '部门名称', dataIndex: 'departName',sortable : true},
 				{header: '创建人', dataIndex: 'createName',sortable : true},
 				{header: '创建时间', dataIndex: 'createTime',sortable : true},
 				{header: '修改人', dataIndex: 'updateName',sortable : true},
 				{header: '修改时间', dataIndex: 'updateTime',sortable : true},
 				{header: '时间戳', dataIndex: 'ts',sortable : true,hidden:true}
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
	
	    oprLoadingBrigadGrid.on('click', function() {
	       selabled();
	     	
	    });
		oprLoadingBrigadGrid.on('rowdblclick',function(grid,index,e){
		var _records = oprLoadingBrigadGrid.getSelectionModel().getSelections();
				if (_records.length ==1) {
					saveoprLoadingBrigad(_records);
				}
			 	
		});
    });
    
    
	
   function searchoprLoadingBrigad() {
		dateStore.proxy = new Ext.data.HttpProxy({
					method : 'POST',
					url : sysPath
								+ "/"+gridSearchUrl,
						params:{privilege:privilege,limit : pageSize}
				});
			var dispatchGroup=Ext.getCmp('dispatchGroup').getValue();
			var loadingbrigade=Ext.getCmp('loadingbrigade').getValue();
			
			var authorityDepartId=Ext.getCmp('authorityDepartId').getValue();
			if(null!=authorityDepartId && authorityDepartId>0){
				pubauthorityDepart=authorityDepartId;
			}

			 Ext.apply(dateStore.baseParams, {
				filter_EQL_departId:pubauthorityDepart,
				privilege:privilege,
				limit : pageSize,
				filter_EQL_loadingbrigadId:loadingbrigade,
				filter_EQL_groupId:dispatchGroup,
				checkItems : '',
				itemsValue : '',
				filter_GED_startDate:'',
				filter_LED_startDate:'',
				filter_GED_endDate:'',
				filter_LED_endDate:'',
				filter_GED_createTime:'',
				filter_LED_createTime:'',
				filter_GED_updateTime:'',
				filter_LED_updateTime:''
			});
			 if (Ext.getCmp('searchSelectBox').getValue() == 'EQD_startDate') {
					 Ext.apply(dateStore.baseParams, {
    						filter_GED_startDate :(!Ext.getCmp("startDate").hidden?(Ext.get("startDate").dom.value=="开始时间"?"":Ext.get("startDate").dom.value):""),
    						filter_LED_startDate : (!Ext.getCmp("endDate").hidden?(Ext.get("endDate").dom.value=="结束时间"?"":Ext.get("endDate").dom.value):"")
    					});

    		} else if (Ext.getCmp('searchSelectBox').getValue() == 'EQD_endDate') {
    				  Ext.apply(dateStore.baseParams, {
    						filter_GED_endDate:(!Ext.getCmp("startDate").hidden?(Ext.get("startDate").dom.value=="开始时间"?"":Ext.get("startDate").dom.value):""),
    						filter_LED_endDate : (!Ext.getCmp("endDate").hidden?(Ext.get("endDate").dom.value=="结束时间"?"":Ext.get("endDate").dom.value):"")
    					});
    		} else if (Ext.getCmp('searchSelectBox').getValue() == 'EQD_createTime') {
    				 Ext.apply(dateStore.baseParams, {
    						filter_GED_createTime:(!Ext.getCmp("startDate").hidden?(Ext.get("startDate").dom.value=="开始时间"?"":Ext.get("startDate").dom.value):""),
    						filter_LED_createTime : (!Ext.getCmp("endDate").hidden?(Ext.get("endDate").dom.value=="结束时间"?"":Ext.get("endDate").dom.value):"")
    					});
    		} else if (Ext.getCmp('searchSelectBox').getValue() == 'EQD_updateTime') {
    				 Ext.apply(dateStore.baseParams, {
    						filter_GED_updateTime:(!Ext.getCmp("startDate").hidden?(Ext.get("startDate").dom.value=="开始时间"?"":Ext.get("startDate").dom.value):""),
    						filter_LED_updateTime : (!Ext.getCmp("endDate").hidden?(Ext.get("endDate").dom.value=="结束时间"?"":Ext.get("endDate").dom.value):"")
    					});
    		} else{	
    			 Ext.apply(dateStore.baseParams, {
    						checkItems : Ext.get("checkItems").dom.value,
							itemsValue : Ext.get("searchContent").dom.value
    			});
			}
		
		var editbtn = Ext.getCmp('basoprLoadingBrigadEdit');
		var deletebtn = Ext.getCmp('basoprLoadingBrigadDelete');

		editbtn.setDisabled(true);
		deletebtn.setDisabled(true);

		dataReload();
		
		
	}
function saveoprLoadingBrigad(_records) {
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
																}, 
																{xtype : "timefield",
																	fieldLabel:'排班开始时间<font style="color:red;">*</font>',
																	format:'H:i',
																	allowBlank : false,
																    name : 'startDateString',
																    id:'startDateId', 
																    anchor : '95%',
																	    enableKeyEvents:true,listeners : {
																	    keyup:function(textField, e){
																             if(e.getKey() == 13){
																             	//searchoprLoadingBrigad();
																             }
																	}
																
																}}
																,  {
																	xtype : 'timefield',
																	fieldLabel:'排班结束时间<font style="color:red;">*</font>',
																	format:'H:i',
																	allowBlank : false,
																	name:'endDateString',
																	id:'endDateId',
																	anchor : '95%',
																	enableKeyEvents:true,listeners : {
																		keyup:function(textField, e){
																	             if(e.getKey() == 13){
																	             	//searchoprLoadingBrigad();
																	             }
																		}
																	
																	}
																},
																{xtype : "combo",
																	queryParam : 'filter_LIKES_loadingName',
																	pageSize : comboSize,
																	minChars : 0,
																	editable:true,
																	allowBlank : false,
																	forceSelection : true,
																	selectOnFocus : true,
																	resizable : true,
																	fieldLabel:'装卸组<font style="color:red;">*</font>',
																	triggerAction : 'all',
																	store : loadingbrigadeStore,
																	minChars : 0,
																	mode : "remote",// 从本地加载值
																	valueField : 'loadingbrigadeId',// value值，与fields对应
																	displayField : 'loadingbrigadeName',// 显示值，与fields对应
																	hiddenName : 'loadingbrigadId',
																	anchor : '95%',
																    enableKeyEvents:true,listeners : {
																	    keyup:function(combo, e){
																             if(e.getKey() == 13){
																             	//searchoprLoadingBrigad();
																             }
																			}
																	
																	}},  {
																	xtype : "combo",
																		typeAhead : true,
																		queryParam : 'filter_LIKES_loadingName',
																		pageSize : comboSize,
																		minChars : 0,
																		editable:true,
																		allowBlank : false,
																		forceSelection : true,
																		selectOnFocus : true,
																		resizable : true,
																		fieldLabel:'分拨组<font style="color:red;">*</font>',
																		triggerAction : 'all',
																		store : dispatchGroupStore,
																		minChars : 0,
																		mode : "remote",// 从本地载值
																		valueField : 'dispatchGroupId',// value值，与fields对应
																		displayField : 'dispatchGroupName',// 显示值，与fields对应
																		hiddenName : 'groupId',
																		anchor : '95%',
																		 enableKeyEvents:true,listeners : {
																 		    keyup:function(textField, e){
															                     if(e.getKey() == 13){
															                     	//searchoprLoadingBrigad();
															                     }
																 		    }
																 	
																 		}
																}]

													}]
											}]
										});
									
		oprLoadingBrigadTitle='添加排班时间信息';
		if(cid!=null){
		oprLoadingBrigadTitle='修改排班时间信息';
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
		title : oprLoadingBrigadTitle,
		width : 350,
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
						url : sysPath+ '/'+saveUrl,
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
				   			//form.load();,_records[0].data.departName
				   			Ext.getCmp('mycombo').setValue(_records[0].data.departId);
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
				filter_EQL_departId:pubauthorityDepart,
				privilege:privilege,
				limit : pageSize
			}
		});
		
		selabled();
		
 }
 
 function selabled(){
 	 var _record = oprLoadingBrigadGrid.getSelectionModel().getSelections();
 	
        var updatebtn = Ext.getCmp('basoprLoadingBrigadEdit');
        var deletebtn = Ext.getCmp('basoprLoadingBrigadDelete');
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
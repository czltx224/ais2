//定时任务管理js
var privilege=228;//权限参数
var gridSearchUrl="timing/qrtzTriggersAction!findAll.action";//查询地址
var saveUrl="timing/qrtzTriggersAction!save.action";//保存地址
var stopUrl='timing/qrtzTriggersAction!stopTigger.action';//暂停任务地址
var stopAllUrl='timing/qrtzTriggersAction!stopAll.action';//暂停所有任务地址
var stopGroupUrl='timing/qrtzTriggersAction!stopGroup.action';//暂停任务组地址
var startUrl='timing/qrtzTriggersAction!startTigger.action';//开始任务地址
var startAllUrl='timing/qrtzTriggersAction!startAll.action';//开始所有任务地址
var startGroupUrl='timing/qrtzTriggersAction!startGroup.action';//开始任务组地址
var removeTriggerUrl='timing/qrtzTriggersAction!removeTrigger.action';//删除任务地址
var searchWidth=70;
var colWidth=80;
var pubauthorityDepart=bussDepart;
var  fields=['TRIGGERNAME','TRIGGERGROUP','JOBNAME','JOBGROUP','ISVOLATILE','DESCRIPTION',
			'NEXTFIRETIME','PREVFIRETIME','PRIORITY','TRIGGERSTATE','TRIGGERTYPE','ENDTIME',
			'STARTTIME','CALENDARNAME','MISFIREINSTR',
			'JOBBEAN','JOBSQL','JOBJAR','TRIGGERSCRIPT','HESSIANURL'];
		var menu = new Ext.menu.Menu({
		    id: 'receiptMenu',
		    items: [{text:'<b>开始任务</b>',iconCls : 'group',tooltip : '开始任务',handler:function() {
	            	var oprLoadingBrigad =Ext.getCmp('oprLoadingBrigadCenter');
					var _records = oprLoadingBrigad.getSelectionModel().getSelections();
					if (_records.length < 1) {
						Ext.Msg.alert(alertTitle, "请选择您要开始的任务！");
						return false;
					} 
					var datas="";
					for(var i=0;i<_records.length;i++){
						if(i>0){
							datas+='&';
						}
					   datas += "objList["+i+"].jobName="+_records[i].data.JOBNAME+'&'
				   			 +"objList["+i+"].jobGroup="+_records[i].data.JOBGROUP;
					}
					Ext.Ajax.request({
						url : sysPath+'/'+ startUrl,
						params : datas,
						success : function(resp) {
							var respText = Ext.util.JSON.decode(resp.responseText);
							if(respText.success){
								Ext.Msg.alert(alertTitle,"开始任务成功!");
								searchoprLoadingBrigad();
							}else{
								alertMsg(respText.msg);
							}
						},
						failure :function(resp){
							var respText = Ext.util.JSON.decode(resp.responseText);
							Ext.Msg.alert(alertTitle,"开始任务失败！");
						}
					});	
	            } },{text:'<b>暂停任务</b>',iconCls : 'group',tooltip : '暂停任务',handler:function() {
            		var oprLoadingBrigad =Ext.getCmp('oprLoadingBrigadCenter');
					var _records = oprLoadingBrigad.getSelectionModel().getSelections();
					if (_records.length < 1) {
						Ext.Msg.alert(alertTitle, "请选择您要暂停的任务！");
						return false;
					} 
					var datas="";
					for(var i=0;i<_records.length;i++){
						if(i>0){
							datas+='&';
						}
					   datas += "objList["+i+"].jobName="+_records[i].data.JOBNAME+'&'
				   			 +"objList["+i+"].jobGroup="+_records[i].data.JOBGROUP;
					}
					Ext.Ajax.request({
						url : sysPath+'/'+ stopUrl,
						params : datas,
						success : function(resp) {
							var respText = Ext.util.JSON.decode(resp.responseText);
							if(respText.success){
								Ext.Msg.alert(alertTitle,"暂停任务成功!");
								searchoprLoadingBrigad();
							}else{
								alertMsg(respText.msg);
							}
						},
						failure :function(resp){
							var respText = Ext.util.JSON.decode(resp.responseText);
							Ext.Msg.alert(alertTitle,"暂停任务失败！");
						}
					});	
						
	            } },{text:'<b>开始任务组</b>',iconCls : 'group',tooltip : '开始任务组',handler:function() {
	            	var oprLoadingBrigad =Ext.getCmp('oprLoadingBrigadCenter');
					var _records = oprLoadingBrigad.getSelectionModel().getSelections();
					if (_records.length < 1) {
						Ext.Msg.alert(alertTitle, "请选择您要开始的任务组！");
						return false;
					}else if (_records.length > 1) {
						Ext.Msg.alert(alertTitle, "您选择的任务组过多！");
						return false;
					} 
					Ext.Ajax.request({
						url : sysPath+'/'+ startGroupUrl,
						params : {groupName:_records[0].data.JOBGROUP},
						success : function(resp) {
							var respText = Ext.util.JSON.decode(resp.responseText);
							if(respText.success){
								Ext.Msg.alert(alertTitle,"开始任务组成功!");
								searchoprLoadingBrigad();
							}else{
								alertMsg(respText.msg);
							}
						},
						failure :function(resp){
							var respText = Ext.util.JSON.decode(resp.responseText);
							Ext.Msg.alert(alertTitle,"开始任务组失败！");
						}
					});	
	            }},{text:'<b>暂停任务组</b>',iconCls : 'group',tooltip : '暂停任务组',handler:function() {
	            	var oprLoadingBrigad =Ext.getCmp('oprLoadingBrigadCenter');
					var _records = oprLoadingBrigad.getSelectionModel().getSelections();
					if (_records.length < 1) {
						Ext.Msg.alert(alertTitle, "请选择您要暂停的任务组！");
						return false;
					}else if (_records.length > 1) {
						Ext.Msg.alert(alertTitle, "您选择的任务组过多！");
						return false;
					} 
					Ext.Ajax.request({
						url : sysPath+'/'+ stopGroupUrl,
						params : {groupName:_records[0].data.JOBGROUP},
						success : function(resp) {
							var respText = Ext.util.JSON.decode(resp.responseText);
							if(respText.success){
								Ext.Msg.alert(alertTitle,"暂停任务组成功!");
								searchoprLoadingBrigad();
							}else{
								alertMsg(respText.msg);
							}
						},
						failure :function(resp){
							var respText = Ext.util.JSON.decode(resp.responseText);
							Ext.Msg.alert(alertTitle,"暂停任务组失败！");
						}
					});	
	            }},{text:'<b>开始所有任务</b>',iconCls : 'group',tooltip : '开始所有任务',handler:function() {
					Ext.Ajax.request({
						url : sysPath+'/'+ startAllUrl,
						success : function(resp) {
							var respText = Ext.util.JSON.decode(resp.responseText);
							if(respText.success){
								Ext.Msg.alert(alertTitle,"开始所有任务成功!");
								searchoprLoadingBrigad();
							}else{
								alertMsg(respText.msg);
							}
						},
						failure :function(resp){
							var respText = Ext.util.JSON.decode(resp.responseText);
							Ext.Msg.alert(alertTitle,"开始所有任务失败！");
						}
					});	
	            } },{text:'<b>暂停所有任务</b>',iconCls : 'group',tooltip : '暂停所有任务',handler:function() {
					Ext.Ajax.request({
						url : sysPath+'/'+ stopAllUrl,
						success : function(resp) {
							var respText = Ext.util.JSON.decode(resp.responseText);
							if(respText.success){
								Ext.Msg.alert(alertTitle,"暂停所有任务成功!");
								searchoprLoadingBrigad();
							}else{
								alertMsg(respText.msg);
							}
						},
						failure :function(resp){
							var respText = Ext.util.JSON.decode(resp.responseText);
							Ext.Msg.alert(alertTitle,"暂停所有任务失败！");
						}
					});						
	            } }
		    ]
		});
				
		 var taskBtn=new Ext.Button({
   					text : '<B>任务操作</B>',
   					id : 'receiptBtn',
   					tooltip : '任务菜单',
   					iconCls : 'table',
   					menu: menu
   				});	
	var tbar=[{text:'<b>导出</b>',iconCls:'sort_down',handler:function() {
                	parent.exportExl(oprLoadingBrigadGrid);
        } },'-',taskBtn,'-',
		{text:'<b>新增</b>',iconCls:'userAdd',id:'basoprLoadingBrigadAdd',tooltip : '新增定时任务',handler:function() {
                	saveoprLoadingBrigad(null);
            } },'-',
	 	{text:'<b>修改</b>',iconCls:'userEdit',id:'basoprLoadingBrigadEdit',disabled:true,tooltip : '修改定时任务',handler:function(){
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
	 	{text:'<b>删除</b>',iconCls:'userDelete',id:'delButton',disabled:true,tooltip : '删除定时任务',handler:function(){
	 		var oprLoadingBrigad =Ext.getCmp('oprLoadingBrigadCenter');
			var _records = oprLoadingBrigad.getSelectionModel().getSelections();
		
				if (_records.length < 1) {
					Ext.Msg.alert(alertTitle, "请选择您要修改的行！");
					return false;
				} else if (_records.length > 1) {
					Ext.Msg.alert(alertTitle, "一次只能修改一行！");
					return false;
				}
				Ext.Msg.confirm(alertTitle, "确定要删除吗？", function(
						btnYes) {
					if (btnYes == 'yes' || btnYes == 'ok'
							|| btnYes == true) {
						removeTrigger(_records);
					}
				});
	 		}},'-',
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
    							['LIKES_T_triggerName', 'Trigger名称'],
    						    ['LIKES_T_jobName', 'JobName名称'],
    						    ['LIKES_T_jobGroup', 'JobGroup名称'],
    						    ['LIKES_T_triggerGroup', 'TriggerGroup名称'],
    						    ['LIKES_T_triggerState', 'Trigger状态'],
    						    ['LIKES_T_triggerType', 'Trigger类型']],
    							
    					emptyText : '选择类型',
    					editable : false,
    					forceSelection : true,
    					listeners : {
    						'keyup' : function(combo,e) {
    							if(e.getKey()==13){
    								searchoprLoadingBrigad();
    							}
    						}
    					}
    					
    				}
    			
	 	];	

var queryTbar=new Ext.Toolbar([
			'任务组',{
				xtype:'textfield',
				width:searchWidth,
				id:'searchGroupName'
			},
			'Trigger组',{
				xtype:'textfield',
				width:searchWidth,
				id:'searchTriggerGroup'
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
            root: 'resultMap', totalProperty: 'totalCount'
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
			sortDescText : "降序"
			//forceFit : true
		},
		autoScroll:false, 
		//autoExpandColumn:1,
        frame:true, 
        loadMask:true,
        sm:sm,
        cm:new Ext.grid.ColumnModel([rowNum,sm,
        		{header: 'Trigger名称', dataIndex: 'TRIGGERNAME',sortable:true,width:colWidth+20},
       			{header: 'Trigger分组', dataIndex: 'TRIGGERGROUP',sortable : true,width:colWidth},
        		{header: 'job名称', dataIndex: 'JOBNAME',sortable : true,width:colWidth},
        		{header: 'job组', dataIndex: 'JOBGROUP',sortable : true,width:colWidth},
 				{header: '下次执行时间', dataIndex: 'NEXTFIRETIME',sortable : true,width:colWidth+40},
 				{header: '上次执行时间', dataIndex: 'PREVFIRETIME',sortable : true,width:colWidth+40},
 				{header: '执行脚本', dataIndex: 'TRIGGERSCRIPT',sortable : true,width:colWidth},
 				{header: '优先级', dataIndex: 'PRIORITY',sortable : true,width:colWidth},
 				{header: 'trigger状态', dataIndex: 'TRIGGERSTATE',sortable : true,width:colWidth
 					,renderer:function renderDescn(v, cellmeta, record, rowIndex, columnIndex, store){
 					if(v=='PAUSED'){
						cellmeta.css = 'x-grid-back-red';
					}else if(v=='WAITING'){
						cellmeta.css = 'x-grid-back-yellow';
					}else if(v=='ACQUIRED'){
						cellmeta.css = 'x-grid-back-green';
					}
					return v;
				}
 				},
 				{header: 'trigger类型', dataIndex: 'TRIGGERTYPE',sortable : true,width:colWidth},
 				{header: '开始时间', dataIndex: 'STARTTIME',sortable : true,width:colWidth+40},
 				{header: '结束时间', dataIndex: 'ENDTIME',sortable : true,width:colWidth+40},
 				{header: '描述', dataIndex: 'DESCRIPTION',sortable : true,width:colWidth},
 				{header: '执行类', dataIndex: 'JOBBEAN',sortable : true,width:colWidth},
 				{header: '执行SQL', dataIndex: 'JOBSQL',sortable : true,width:colWidth},
 				{header: '执行JAR包', dataIndex: 'JOBJAR',sortable : true,width:colWidth},
 				{header: '执行路径', dataIndex: 'HESSIANURL',sortable : true,width:colWidth}
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
			var searchGroupName=Ext.getCmp('searchGroupName').getValue();
			var searchTriggerGroup=Ext.getCmp('searchTriggerGroup').getValue();
			
			 Ext.apply(dateStore.baseParams, {
			 	filter_LIKES_T_jobGroup:searchGroupName,
			 	filter_LIKES_T_triggerGroup:searchTriggerGroup,
				privilege:privilege,
				limit : pageSize,
				filter_checkItems : Ext.get("checkItems").dom.value,
				filter_itemsValue : Ext.get("searchContent").dom.value
			});
		
		var editbtn = Ext.getCmp('basoprLoadingBrigadEdit');

		editbtn.setDisabled(true);

		dataReload();
		
		
	}
function saveoprLoadingBrigad(_records) {
		var triggerName='';
		var triggerGroup='';
		if(_records!=null){
			triggerName = _records[0].data.TRIGGERNAME;
			triggerGroup = _records[0].data.TRIGGERGROUP;
		}
		var form = new Ext.form.FormPanel({
					labelAlign : 'left',
					frame : true,
					url:sysPath+'/'+gridSearchUrl,
					baseParams:{
	    				filter_T_triggerName:triggerName,
	    				filter_T_triggerGroup:triggerGroup,
						privilege:privilege,
						limit : pageSize
					},
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
											items : [
													{xtype : "textfield",
														fieldLabel:'Trigger名称<font style="color:red;">*</font>',
														allowBlank : false,
													    name : 'job.triggerName',
													    id:'triggerNameId',
													    anchor : '95%',
														    enableKeyEvents:true,listeners : {
														    keyup:function(textField, e){
													             if(e.getKey() == 13){
													             	//searchoprLoadingBrigad();
													             }
														}
													
													}}
													,  {
														xtype : 'textfield',
														fieldLabel:'Trigger分组<font style="color:red;">*</font>',
														allowBlank : false,
														name:'job.triggerGroup',
														id:'triggerGroupId',
														anchor : '95%',
														enableKeyEvents:true,listeners : {
															keyup:function(textField, e){
														             if(e.getKey() == 13){
														             	//searchoprLoadingBrigad();
														             }
															}
														
														}
													},{
														xtype : 'textfield',
														fieldLabel:'triggerScript<font style="color:red;">*</font>',
														allowBlank : false,
														name:'job.triggerScript',
														id:'triggerScriptId',
														anchor : '95%',
														enableKeyEvents:true,listeners : {
															keyup:function(textField, e){
														             if(e.getKey() == 13){
														             	//searchoprLoadingBrigad();
														             }
															}
														
														}
													},{
														xtype : 'textfield',
														fieldLabel:'jobName名称<font style="color:red;">*</font>',
														allowBlank : false,
														name:'job.jobName',
														id:'jobNameId',
														anchor : '95%',
														enableKeyEvents:true,listeners : {
															keyup:function(textField, e){
														             if(e.getKey() == 13){
														             	//searchoprLoadingBrigad();
														             }
															}
														
														}
													},{
														xtype : 'textfield',
														fieldLabel:'jobGroup分组<font style="color:red;">*</font>',
														allowBlank : false,
														name:'job.jobGroup',
														id:'jobGroupId',
														anchor : '95%',
														enableKeyEvents:true,listeners : {
															keyup:function(textField, e){
														             if(e.getKey() == 13){
														             	//searchoprLoadingBrigad();
														             }
															}
														
														}
													},{
														xtype : 'textfield',
														fieldLabel:'执行路径',
														name:'job.hessianUrl',
														id:'hessianUrlId',
														anchor : '95%',
														enableKeyEvents:true,listeners : {
															keyup:function(textField, e){
														             if(e.getKey() == 13){
														             	//searchoprLoadingBrigad();
														             }
															}
														
														}
													},{
														xtype : 'textfield',
														fieldLabel:'jobSql',
														name:'job.jobSql',
														id:'jobSqlId',
														anchor : '95%',
														enableKeyEvents:true,listeners : {
															keyup:function(textField, e){
														             if(e.getKey() == 13){
														             	//searchoprLoadingBrigad();
														             }
															}
														
														}
													},{
														xtype : 'textfield',
														fieldLabel:'jobBean',
														name:'job.jobBean',
														id:'jobBeanId',
														anchor : '95%',
														enableKeyEvents:true,listeners : {
															keyup:function(textField, e){
														             if(e.getKey() == 13){
														             	//searchoprLoadingBrigad();
														             }
															}
														
														}
													},{
														xtype : 'textfield',
														fieldLabel:'jobJar',
														name:'job.jobJar',
														id:'jobJarId',
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
									
		oprLoadingBrigadTitle='添加定时任务信息';
		if(triggerName!=null && triggerName.length>0){
			oprLoadingBrigadTitle='修改定时任务信息';
			form.load({
				waitMsg : '正在载入数据...',
	   			success : function(_form, action) {
	   				var respText = Ext.util.JSON.decode(action.response.responseText);
	   				
	   				if(respText.resultMap.length>0){
	   					setFormValue(respText.resultMap[0]);
	   				}else{
	   					alertMsg('载入失败！');
	   				}
	   			},
	   			failure : function(_form, action) {
	   				var respText = Ext.util.JSON.decode(action.response.responseText);
	   				
	   				if(respText.resultMap.length>0){
	   					setFormValue(respText.resultMap[0]);
	   				}else{
	   					alertMsg('载入失败！');
	   				}
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
						url : sysPath
								+ '/'+saveUrl,
								params:{privilege:privilege,limit : pageSize},
						waitMsg : '正在保存数据...',
						success : function(form, action) {
							 Ext.Msg.alert(alertTitle,"保存成功！", function() {
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
					if(triggerName==null){
						form.getForm().reset();
					}
					if(triggerName!=null){
						form.load({
						waitMsg : '正在载入数据...',
		    			success : function(_form, action) {
				   			
		    			},
		    			failure : function(_form, action) {
		    				var respText = Ext.util.JSON.decode(action.response.responseText);
			   				if(respText.resultMap.length>0){
			   					setFormValue(respText.resultMap[0]);
			   				}else{
			   					alertMsg('载入失败！');
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
 
 //给form设值
function setFormValue(result){
	Ext.getCmp('triggerNameId').setValue(result.TRIGGERNAME);
	Ext.getCmp('triggerGroupId').setValue(result.TRIGGERGROUP);
	Ext.getCmp('jobNameId').setValue(result.JOBNAME);
	Ext.getCmp('triggerScriptId').setValue(result.TRIGGERSCRIPT);
	Ext.getCmp('jobGroupId').setValue(result.JOBGROUP);
	Ext.getCmp('jobBeanId').setValue(result.JOBBEAN);
	Ext.getCmp('jobSqlId').setValue(result.JOBSQL);
	Ext.getCmp('jobJarId').setValue(result.JOBJAR);
	Ext.getCmp('hessianUrlId').setValue(result.HESSIANURL);
}
 //重新加载
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
 //弹出异常信心
 function alertMsg(msg){
 	Ext.Msg.alert(alertTitle,"<font color=red><b>"+msg+"</b></font>");
 	dataReload();
 }
 
 //设置按钮是否可以编辑
 function selabled(){
 	 var _record = oprLoadingBrigadGrid.getSelectionModel().getSelections();
 	
        var updatebtn = Ext.getCmp('basoprLoadingBrigadEdit');
        var delButton = Ext.getCmp('delButton');
        if (_record.length==1) {       	
            if(updatebtn){
            	updatebtn.setDisabled(false);
            	delButton.setDisabled(false);
            }
        }else if(_record.length>1){
        	 if(updatebtn){
            	updatebtn.setDisabled(true);
            	delButton.setDisabled(true);
            }
        } else {
            if(updatebtn){
            	updatebtn.setDisabled(true);
            	delButton.setDisabled(true);
            }
        }
 }
 function removeTrigger(records){
 	if(null==records || records.length<1){
 		Ext.Msg.alert(alertTitle,"请选择您要删除的定时任务！");
 		return;
 	}
 	
	var jobName=records[0].data.JOBNAME;
   	var jobGroup=records[0].data.JOBGROUP;
	Ext.Ajax.request({
		url : sysPath+'/'+ removeTriggerUrl,
		params : {
			jobName:jobName,
			jobGroup:jobGroup
		},
		success : function(resp) {
			var respText = Ext.util.JSON.decode(resp.responseText);
			if(respText.success){
				Ext.Msg.alert(alertTitle,"删除任务成功!");
				searchoprLoadingBrigad();
			}else{
				alertMsg(respText.msg);
			}
		},
		failure :function(resp){
			var respText = Ext.util.JSON.decode(resp.responseText);
			Ext.Msg.alert(alertTitle,"删除任务失败！");
		}
	});	
 }
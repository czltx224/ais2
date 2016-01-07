//库存区域管理
var privilege=69;//权限参数
var gridSearchUrl="stock/oprStoreAreaAction!ralaList.action";
var saveUrl="stock/oprStoreAreaAction!save.action";
var delUrl="stock/oprStoreAreaAction!delete.action";
var departUrl='sys/departAction!findAll.action';
var dictionaryUrl='sys/dictionaryAction!ralaList.action';
var searchCusNameUrl='stock/oprStoreAreaAction!findCusName.action';
var searchPkAreaclUrl='stock/oprStoreAreaAction!findPkAreacl.action';
var authorityDepartUrl='sys/basRightDepartAction!ralaList.action';

fields=["id",'areaName','tranMode','cpArea','towhere','createTime','createName','updateTime','updateName',
                    'ts','departId','takeMoke','distributionMode','overmemoDepart'];
        //配送方式4
		distributionModeStore	= new Ext.data.Store({ 
			storeId:"distributionModeStore",
			baseParams:{filter_EQL_basDictionaryId:4,privilege:16},
			proxy:new Ext.data.HttpProxy({url:sysPath+"/"+dictionaryUrl}),
			reader:new Ext.data.JsonReader({
	                    root:'result',
	                    totalProperty:'totalCount'
	        },[
        	{name:'distributionModeId',mapping:'typeCode'},
        	{name:'distributionModeName',mapping:'typeName'}
        	])
		});
		 //提货方式14
		takeMokeStore	= new Ext.data.Store({ 
			storeId:"takeMokeStore",
			baseParams:{filter_EQL_basDictionaryId:14,privilege:16},
			proxy:new Ext.data.HttpProxy({url:sysPath+"/"+dictionaryUrl}),
			reader:new Ext.data.JsonReader({
	                    root:'result',
	                    totalProperty:'totalCount'
	        },[
        	{name:'takeMokeId',mapping:'typeCode'},
        	{name:'takeMokeName',mapping:'typeName'}
        	])
		});
		 //运输方式18
		tranModeStore	= new Ext.data.Store({ 
			storeId:"tranModeStore",
			baseParams:{filter_EQL_basDictionaryId:18,privilege:16},
			proxy:new Ext.data.HttpProxy({url:sysPath+"/"+dictionaryUrl}),
			reader:new Ext.data.JsonReader({
	                    root:'result',
	                    totalProperty:'totalCount'
	        },[
        	{name:'tranModeId',mapping:'typeCode'},
        	{name:'tranModeName',mapping:'typeName'}
        	])
		});
		//代理区域
		cpAreaStore = new Ext.data.Store({
	        storeId:"cpAreaStore",
	        proxy: new Ext.data.HttpProxy({url:sysPath+'/'+searchPkAreaclUrl}),
	        reader: new Ext.data.JsonReader({
	         //   root: 'data', totalProperty: 'totalCount'
	        }, [
	            {name:'pkAreacl',mapping:'pkAreacl'}
	        ])
        });
		//去向
		towhereStore = new Ext.data.Store({
	        storeId:"towhereStore",
	        proxy: new Ext.data.HttpProxy({url:sysPath+'/'+searchCusNameUrl}),
	        reader: new Ext.data.JsonReader({
	          //  root: 'data', totalProperty: 'totalCount'
	        }, [
	            {name:'cusName'}
	        ])
        });
		//内部交接部门(业务部门)
		departStore = new Ext.data.Store({
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
        
     	
	 var tbar=[{text:'<b>新增</b>',iconCls:'userAdd',id:'storeAreaAdd',tooltip : '新增库存区域',handler:function() {
	 			
                	savestoreArea(null);
            } },'-',
	 	{text:'<b>修改</b>',iconCls:'userEdit',id:'storeAreaEdit',disabled:true,tooltip : '修改库存区域信息',handler:function(){
	 	var storeArea =Ext.getCmp('storeAreaCenter');
				var _records = storeArea.getSelectionModel().getSelections();
	
					if (_records.length < 1) {
						top.Ext.Msg.alert(alertTitle, "请选择您要修改的行！");
						return false;
					} else if (_records.length > 1) {
						top.Ext.Msg.alert(alertTitle, "一次只能修改一行！");
						return false;
					}
					//alert(_records[0].data.id);
	 	savestoreArea(_records);}},'-',
	 	{text:'<b>删除</b>',iconCls:'userDelete',id:'storeAreaDelete',disabled:true,tooltip : '删除库存区域',handler:function(){
	 		var storeArea =Ext.getCmp('storeAreaCenter');
			var _records = storeArea.getSelectionModel().getSelections();
	
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
                     	searchstoreArea();
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
    							['LIKES_areaName', '区域名称'],
    							['LIKES_tranMode','运输方式'],
    							['LIKES_cpArea','代理区域名称'],
    							['LIKES_towhere','去向'],
    							['LIKES_takeMoke','提货方式'],
    							['LIKES_distributionMode','配送方式'],
    							['EQD_createTime', '创建时间'],
    							['EQD_updateTime', '修改时间']],
    					emptyText : '选择查询方式类型',
    					forceSelection : true,
    					listeners : {
    						
    						'select' : function(combo, record, index) {
    							if(combo.getValue()==''){searchstoreArea();}
    							if (combo.getValue() == 'EQD_createTime') {
    								Ext.getCmp("startDate").enable();
    								Ext.getCmp("startDate").show();

    								Ext.getCmp("endDate").enable();
    								Ext.getCmp("endDate").show();
    								
    								Ext.getCmp("searchContent").disable();
    								Ext.getCmp("searchContent").hide();
    								Ext.getCmp("searchContent").setValue("");
    							}else if(combo.getValue() == 'EQD_updateTime') {
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
    									searchstoreArea();
    								}
    							
    							}
    						}
    					}
    					
    				},'-','部门：',
    				{xtype : 'combo',
						triggerAction : 'all',
						id:'searchAuthorityDepart',
						pageSize:comboSize,
						store : authorityDepartStore,
						resizable:true,
						forceSelection : true,
						valueField : 'departId',//value值，与fields对应
						displayField : 'departName',//显示值，与fields对应
						editable : false,
						name : 'departId',
						anchor : '95%'
						
    				},
    			'-',{text : '<b>搜索</b>',id : 'btn',iconCls : 'btnSearch',
    					handler : searchstoreArea
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
        header:'序号', width:40
    });
    storeAreaGrid = new Ext.grid.GridPanel({
        renderTo: Ext.getBody(),
        id:'storeAreaCenter',
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
       			{header: '区域名称', dataIndex: 'areaName',sortable : true},
			        {header: '代理名称', dataIndex: 'cpArea',sortable : true},
			        {header: '配送方式', dataIndex: 'distributionMode',sortable : true},
    				{header: '提货方式', dataIndex: 'takeMoke',sortable : true},
    				{header: '运输方式', dataIndex: 'tranMode',sortable : true},
    				{header: '去向', dataIndex: 'towhere',sortable : true},
    				{header: '内部交接终端部门', dataIndex: 'overmemoDepart',sortable : true}
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
   
	//searchstoreArea();
	
    storeAreaGrid.on('click', function() {
        selabled();
    });
    storeAreaGrid.on('rowdblclick',function(grid,index,e){
		var _records = storeAreaGrid.getSelectionModel().getSelections();
				
				if (_records.length ==1) {
					savestoreArea(_records);
				}
			 	
		});
    });
    
	

function searchstoreArea() {
		dateStore.proxy = new Ext.data.HttpProxy({
					method : 'POST',
					url : sysPath+'/'+gridSearchUrl,
					params : {
						start : 0,
						privilege:privilege,
						limit : pageSize
					}
					
				});
	  var adepartId=bussDepart;
	  var searchAuthorityDepart=Ext.getCmp('searchAuthorityDepart').getValue();

	  if(searchAuthorityDepart.length>0){
	  	adepartId=searchAuthorityDepart;
	  }
	  
	  if (Ext.getCmp('searchSelectBox').getValue() == 'EQD_createTime') {
    			 Ext.apply(dateStore.baseParams, {
    						filter_GED_createTime:(!Ext.getCmp("startDate").hidden?(Ext.get("startDate").dom.value=="开始时间"?"":Ext.get("startDate").dom.value):""),
    						filter_LTD_createTime : (!Ext.getCmp("endDate").hidden?(Ext.get("endDate").dom.value=="结束时间"?"":Ext.get("endDate").dom.value):""),
    						checkItems : '',
    						itemsValue : '',
    						filter_EQS_departId:adepartId,
    						privilege:privilege,
							start:0
    					});
    		} else if (Ext.getCmp('searchSelectBox').getValue() == 'EQD_updateTime') {
    			 Ext.apply(dateStore.baseParams, {
    						filter_GED_updateTime:(!Ext.getCmp("startDate").hidden?(Ext.get("startDate").dom.value=="开始时间"?"":Ext.get("startDate").dom.value):""),
    						filter_LTD_updateTime : (!Ext.getCmp("endDate").hidden?(Ext.get("endDate").dom.value=="结束时间"?"":Ext.get("endDate").dom.value):""),
    						checkItems : '',
    						itemsValue : '',
    						filter_EQS_departId:adepartId,
    						privilege:privilege,
							start:0
    					});
    		}else{	
				dateStore.baseParams = {
				checkItems : Ext.get("checkItems").dom.value,
				privilege:privilege,
				filter_EQS_departId:adepartId,
				start:0,
				itemsValue : Ext.get("searchContent").dom.value
				}
    		}
		
		var editbtn = Ext.getCmp('storeAreaEdit');
		var deletebtn = Ext.getCmp('storeAreaDelete');

		editbtn.setDisabled(true);
		deletebtn.setDisabled(true);

		dataReload();
		
	}
function savestoreArea(_records) {
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
								width : 400,
								defaults : {allowBlank : false},  
								reader : new Ext.data.JsonReader({
			            root: 'result', totalProperty: 'totalCount'
			        }, fields),
					labelAlign : "right",
				
						items : [{
											layout : 'column',
											items : [{
														layout : 'form',
														items : [{
																	name : "id",
																	xtype : "hidden"
																},{
																	name : "ts",
																	xtype : "hidden"
																},{
																	name : "departId",
																	value:bussDepart,
																	xtype : "hidden"
																},
																 {
																	xtype : 'textfield',
																	id:'myfirstfocus',
																	labelAlign : 'left',
																	fieldLabel : '区域名称<font style="color:red;">*</font>',
																	name : 'areaName',
																	allowBlank : false,
																	maxLength:20,
																	blankText : "区域名称不能为空！",
																	anchor : '95%',
																	enableKeyEvents:true,listeners : {
															 		blur:function(field,e){
															 			if(null!=field.getValue() && field.getValue().length>0){
														                     	Ext.Ajax.request({
																				url : sysPath+'/'
																						+ gridSearchUrl,
																				params :{privilege:privilege,filter_EQS_areaName:field.getValue(),filter_EQL_departId:bussDepart},
																				success : function(resp) {
																					var jdata = Ext.util.JSON.decode(resp.responseText);
																						if(jdata.result.length>0){
																							top.Ext.Msg.alert(alertTitle,'该库存区域已经存在！',function(){
																								field.setValue('');
																								return;
																							});
																						}
																				}
																			});
															 			}
																 		}
																	}
																 },  {
																	xtype : 'combo',
																	triggerAction : 'all',
																	typeAhead : true,
																	queryParam : 'filter_LIKES_pkAreacl',
																	store : cpAreaStore,
																	pageSize : comboSize,
																	forceSelection : true,
																	resizable:true,
																	fieldLabel : '代理区域',
																	minChars : 0,
																	editable : true,
																	displayField : 'pkAreacl',//显示值，与fields对应
																	name:'cpArea',
																	anchor : '95%'
																},  
																 {
																	xtype : 'combo',
																	triggerAction : 'all',
																	typeAhead : true,
																	queryParam : 'filter_LIKES_typeName',
																	store : distributionModeStore,
																	pageSize : comboSize,
																	resizable:true,
																	forceSelection : true,
																	fieldLabel : '配送方式',
																	editable : true,
																	minChars : 0,
																	valueField : 'distributionModeId',//value值，与fields对应
																	displayField : 'distributionModeName',//显示值，与fields对应
																	name:'distributionMode',
																	anchor : '95%'
																},{
																	xtype : 'combo',
																	triggerAction : 'all',
																	typeAhead : true,
																	queryParam : 'filter_LIKES_typeName',
																	store : takeMokeStore,
																	pageSize : comboSize,
																	forceSelection : true,
																	resizable:true,
																	fieldLabel : '提货方式',
																	minChars : 0,
																	editable : true,
																	valueField : 'takeMokeId',//value值，与fields对应
																	displayField : 'takeMokeName',//显示值，与fields对应
																	name:'takeMoke',
																	anchor : '95%'
																},{
																	xtype : 'combo',
																	triggerAction : 'all',
																	typeAhead : true,
																	queryParam : 'filter_LIKES_typeName',
																	store : tranModeStore,
																	pageSize : comboSize,
																	minChars : 0,
																	resizable:true,
																	forceSelection : true,
																	fieldLabel : '运输方式',
																	valueField : 'tranModeId',//value值，与fields对应
																	displayField : 'tranModeName',//显示值，与fields对应
																	name:'tranMode',
																	anchor : '95%'
																},{
																	xtype : 'combo',
																	triggerAction : 'all',
																	typeAhead : true,
																	queryParam : 'filter_LIKES_cusName',
																	store : towhereStore,
																	pageSize : comboSize,
																	resizable:true,
																	forceSelection : true,
																	fieldLabel : '去向',
																	minChars : 0,
																	displayField : 'cusName',//显示值，与fields对应
																	name:'towhere',
																	anchor : '95%'
																},{
																	xtype : 'combo',
																	triggerAction : 'all',
																	typeAhead : true,
																	queryParam : 'filter_LIKES_departName',
																	store : departStore,
																	pageSize : comboSize,
																	forceSelection : true,
																	resizable:true,
																	fieldLabel : '内部交接部门',
																	minChars : 0,
																	editable : true,
																	valueField : 'departId',//value值，与fields对应
																	displayField : 'departName',//显示值，与fields对应
																	name:'overmemoDepart',
																	anchor : '95%'
																}]
													}]
													
										}]
							});
		
		storeAreaTitle='添加库存区域信息';
		//Ext.getCmp('myfirstfocus').focus();
		if(did!=null){
			departStore.load();
			storeAreaTitle='修改库存区域信息';
			
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
		title : storeAreaTitle,
		width : 400,
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
									win.hide();
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
				
				departStore.load();
				if(did==null){
					
					form.getForm().reset();
				
				}
				if(did!=null){
					storeAreaTitle='修改库存区域信息';
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
	var _record = storeAreaGrid.getSelectionModel().getSelections();
        var updatebtn = Ext.getCmp('storeAreaEdit');
        var deletebtn = Ext.getCmp('storeAreaDelete');
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
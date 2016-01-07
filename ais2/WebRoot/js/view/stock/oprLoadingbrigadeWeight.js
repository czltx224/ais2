//装卸分拨组货量管理js
var privilege=91;//权限参数  未定
var gridSearchUrl="stock/oprLoadingbrigadeWeightAction!findSqlList.action";//查询地址
var gridSearchUrlList="stock/oprLoadingbrigadeWeightAction!list.action";//list查询地址
var saveUrl="stock/oprLoadingbrigadeWeightAction!save.action";//保存地址
var delUrl="stock/oprLoadingbrigadeWeightAction!delete.action";//删除地址

var authorityDepartUrl='sys/basRightDepartAction!findDepartName.action';//权限部门查询地址
var loadingbrigadeUrl='sys/loadingbrigadeAction!list.action';//装卸组分拨组查询地址
var searchWidth=70;
var defaultWidth=80;
var pubauthorityDepart=bussDepart;
var  fields=[{name:"id",mapping:'ID'},
     		{name:"overmemoNo",mapping:'OVERMEMO_NO'},
     		{name:'dno',mapping:'D_NO'},
     		{name:'goods',mapping:'GOODS'},
     		{name:'weight',mapping:'WEIGHT'},
     		{name:'bulk',mapping:'BULK'},
     		{name:'piece',mapping:'PIECE'},
     		{name:'loadingbrigadeId',mapping:'LOADINGBRIGADE_ID'},
     		{name:'loadingbrigadeName',mapping:'LOADINGBRIGADENAME'},
     		{name:'dispatchId',mapping:'DISPATCH_ID'},
     		{name:'dispatchName',mapping:'DISPATCHNAME'},
     		{name:'loadingbrigadeType',mapping:'LOADINGBRIGADE_TYPE'},
     		{name:'departId',mapping:'DEPART_ID'},
     		{name:'createName',mapping:'CREATE_NAME'},
     		{name:'createTime',mapping:'CREATE_TIME'},
     		{name:'updateName',mapping:'UPDATE_NAME'},
     		{name:'updateTime',mapping:'UPDATE_TIME'},
     		{name:'ts',mapping:'TS'}];
	//分拨组
	 dispatchGroupStore= new Ext.data.Store({ 
		storeId:"dispatchGroupStore",
		baseParams:{filter_EQL_type:1,filter_EQL_departId:bussDepart},
		proxy:new Ext.data.HttpProxy({url:sysPath+"/"+loadingbrigadeUrl}),
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },["id","loadingName"])
	});
	//装卸组
	 loadingbrigadeStore= new Ext.data.Store({ 
		storeId:"loadingbrigadeStore",
		baseParams:{filter_EQL_type:0,filter_EQL_departId:bussDepart},
		proxy:new Ext.data.HttpProxy({url:sysPath+"/"+loadingbrigadeUrl}),
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
       },["id","loadingName"])
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
        //装卸类型
      var loadingbrigadeTypeStore = new Ext.data.SimpleStore({
			 autoLoad:true, //此处设置为自动加载
  			  data:[['0','卸货'],
            		['1','装货'],
            		['2','提货'],
            		['4','接货']],
   			 fields:["typeId","typeName"]
		});
		
	var tbar=[{text:'<b>导出</b>',iconCls:'sort_down',handler:function() {
                	parent.exportExl(oprLoadingbrigadeWeightGrid);
        } }, '-',
		{text:'<b>新增</b>',iconCls:'userAdd',id:'basoprLoadingbrigadeWeightAdd',tooltip : '新增装卸分拨货量',handler:function() {
                	saveoprLoadingbrigadeWeight(null);
            } },'-',
	 	{text:'<b>修改</b>',iconCls:'userEdit',id:'basoprLoadingbrigadeWeightEdit',disabled:true,tooltip : '修改装卸分拨货量',handler:function(){
	 	var oprLoadingbrigadeWeight =Ext.getCmp('oprLoadingbrigadeWeightCenter');
		var _records = oprLoadingbrigadeWeight.getSelectionModel().getSelections();
		
						if (_records.length < 1) {
							Ext.Msg.alert(alertTitle, "请选择您要修改的行！");
							return false;
						} else if (_records.length > 1) {
							Ext.Msg.alert(alertTitle, "一次只能修改一行！");
							return false;
						}
	 	saveoprLoadingbrigadeWeight(_records);}},'-',
	 	{text:'<b>删除</b>',iconCls:'userDelete',id:'basoprLoadingbrigadeWeightDelete',disabled:true,tooltip : '删除装卸分拨货量',handler:function(){
	 		var oprLoadingbrigadeWeight =Ext.getCmp('oprLoadingbrigadeWeightCenter');
			var _records = oprLoadingbrigadeWeight.getSelectionModel().getSelections();
	
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
               		searchoprLoadingbrigadeWeight();
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
    							['goods','品名'],
    							['piece','票数'],
    							['weight','重量'],
    							['bulk','体积'],
    							['t_createName','创建人'],
    							['t_updateName','修改人'],
    						    ['t.create_time','创建时间'],
    						    ['t.update_time', '修改时间']],
    							
    					emptyText : '选择类型',
    					editable : false,
    					forceSelection : true,
    					listeners : {
    						'select' : function(combo, record, index) {
    							if (combo.getValue().indexOf('_time')>0 ){
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
                     	searchoprLoadingbrigadeWeight();
                     }
	 		}
	 	
	 	}}
	 	];	

var queryTbar=new Ext.Toolbar([
	'交接单号:',{xtype:'textfield',id:'searchOvermemoNo',
	width:searchWidth-10, 
	   enableKeyEvents:true,listeners : {
	 		keyup:function(textField, e){
                if(e.getKey() == 13){
                	searchoprLoadingbrigadeWeight();
                }
	 		}
	 }},'-',
	 '配送单号:',{xtype:'textfield',id:'searchDno',width:searchWidth-10, 
	   enableKeyEvents:true,listeners : {
	 		keyup:function(textField, e){
                if(e.getKey() == 13){
                	searchoprLoadingbrigadeWeight();
                }
	 		}
	 }},'-',
	 '分拨组:',{xtype:'combo',id:'searchDispatchName',
	 	width:searchWidth+20,
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
			valueField : 'id',// value值，与fields对应
			displayField : 'loadingName',// 显示值，与fields对应
			
	   		enableKeyEvents:true,listeners : {
	 		keyup:function(textField, e){
                if(e.getKey() == 13){
                	searchoprLoadingbrigadeWeight();
                }
	 		}
	 }},'-',
	 '装卸组:',{xtype:'combo',
	 		id:'searchLoadingbrigadeName',
	 		width:searchWidth+20,
			typeAhead : true,
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
			mode : "remote",// 从本地载值
			valueField : 'id',// value值，与fields对应
			displayField : 'loadingName',// 显示值，与fields对应
			
	   		enableKeyEvents:true,listeners : {
	 		keyup:function(textField, e){
                if(e.getKey() == 13){
                	searchoprLoadingbrigadeWeight();
                }
	 		}
	 }},'-','-','装卸类型:',
            {
            	xtype:'combo',
            	id:'loadingType',
            	width:100,
            	triggerAction : 'all',
            	emptyText:'请选择',
				forceSelection : true,
				editable : true,
				mode : "local",//获取本地的值
            	store:loadingbrigadeTypeStore,
            	valueField : 'typeId',// value值，与fields对应
				displayField : 'typeName'// 显示值，与fields对应
            },'-',
 		{text : '<b>查询</b>',id : 'btn',iconCls : 'btnSearch',
   					handler : searchoprLoadingbrigadeWeight
   		}
 	]);	
	 	
Ext.onReady(function() {
   	dateStore = new Ext.data.Store({
        storeId:"dateStore",
        proxy: new Ext.data.HttpProxy({url : sysPath+ "/"+gridSearchUrl,
		baseParams:{privilege:privilege,limit : pageSize,filter_t_departId:pubauthorityDepart}}),
        reader: new Ext.data.JsonReader({
            root: 'resultMap', totalProperty: 'totalCount'
        }, fields)
    });
   
   var sm = new Ext.grid.CheckboxSelectionModel({});
	var rowNum = new Ext.grid.RowNumberer({
        header:'序号', width:20
    });
    oprLoadingbrigadeWeightGrid = new Ext.grid.GridPanel({
        renderTo: Ext.getBody(),
        id:'oprLoadingbrigadeWeightCenter',
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
 				{header: '交接单号', dataIndex: 'overmemoNo',sortable : true,width:defaultWidth},
       			{header: '配送单号', dataIndex: 'dno',sortable : true,width:defaultWidth},
        		{header: '件数', dataIndex: 'piece',sortable : true,width:defaultWidth},
        		{header: '重量', dataIndex: 'weight',sortable : true,width:defaultWidth},
 				{header: '体积', dataIndex: 'bulk',sortable : true,width:defaultWidth},
 				{header: '装卸种类', dataIndex: 'loadingbrigadeType',sortable : true,width:defaultWidth
 					,renderer:function(v){
 						if(v==0){
 							return '卸货';
 						}else if(v==1){
 							return '装货';
 						}else if(v==2){
 							return '提货';
 						}else if(v==4){
 							return '接货';
 						}else{
 							return '其他';
 						}	
			        }
 				},
 				{header: '品名', dataIndex: 'goods',sortable : true,width:defaultWidth},
 				{header: '分拨组ID', dataIndex: 'dispatchId',sortable : true,width:defaultWidth,hidden:true},
 				{header: '分拨组', dataIndex: 'dispatchName',sortable : true,width:defaultWidth},
 				{header: '装卸组ID', dataIndex: 'loadingbrigadeId',sortable : true,width:defaultWidth,hidden:true},
 				{header: '装卸组', dataIndex: 'loadingbrigadeName',sortable : true,width:defaultWidth},
 				{header: '创建人', dataIndex: 'createName',sortable : true,width:defaultWidth},
 				{header: '创建时间', dataIndex: 'createTime',sortable : true,width:defaultWidth+50},
 				{header: '修改人', dataIndex: 'updateName',sortable : true,width:defaultWidth},
 				{header: '修改时间', dataIndex: 'updateTime',sortable : true,width:defaultWidth+50},
 				{header: '时间戳', dataIndex: 'ts',sortable : true,width:defaultWidth,hidden:true}
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
	
	    oprLoadingbrigadeWeightGrid.on('click', function() {
	       selabled();
	     	
	    });
		oprLoadingbrigadeWeightGrid.on('rowdblclick',function(grid,index,e){
			var _records = oprLoadingbrigadeWeightGrid.getSelectionModel().getSelections();
			if (_records.length ==1) {
				saveoprLoadingbrigadeWeight(_records);
			}
		});
    });
    
    
	
   function searchoprLoadingbrigadeWeight() {
		dateStore.baseParams={privilege:privilege,limit : pageSize};
		
		var authorityDepartId=Ext.getCmp('authorityDepartId').getValue();
		if(null!=authorityDepartId && authorityDepartId>0){
			pubauthorityDepart=authorityDepartId;
		}else{
			pubauthorityDepart=bussDepart;
		}
		var startCount = Ext.get('startDate').dom.value;
		var endCount = Ext.get('endDate').dom.value;
		
		var checkItems = Ext.get('checkItems').dom.value;
		var itemsValue = Ext.get('searchContent').dom.value;

		var searchDno = Ext.get('searchDno').dom.value;
		var searchOvermemoNo = Ext.get('searchOvermemoNo').dom.value;
		var searchDispatchId = Ext.getCmp('searchDispatchName').getValue();
		var searchLoadingbrigadeId = Ext.getCmp('searchLoadingbrigadeName').getValue();
		var loadingType = Ext.getCmp('loadingType').getValue();
		// alert(searchDispatchId);
		Ext.apply(dateStore.baseParams, {
				filter_t_dNo:searchDno,
				filter_t_dispatchId:searchDispatchId,
				filter_t_loadingbrigadeId:searchLoadingbrigadeId,
				filter_t_loadingbrigadeType:loadingType,
				filter_t_overmemoNo:searchOvermemoNo,
				filter_t_departId:pubauthorityDepart,
				privilege:privilege,
				limit : pageSize
		});
		
		if(checkItems.indexOf('_time')>0){
			Ext.apply(dateStore.baseParams, {
				filter_countCheckItems:checkItems,
		 	 	filter_startCount:startCount,
		 	 	filter_endCount:endCount
   			});
		}else{
			Ext.apply(dateStore.baseParams, {
				filter_checkItems : checkItems,
				filter_itemsValue : itemsValue
			});
		}
		
		var editbtn = Ext.getCmp('basoprLoadingbrigadeWeightEdit');
		var deletebtn = Ext.getCmp('basoprLoadingbrigadeWeightDelete');

		editbtn.setDisabled(true);
		deletebtn.setDisabled(true);

		dataReload();
	}
function saveoprLoadingbrigadeWeight(_records) {
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
				    				filter_t_id:cid,
									privilege:privilege,
									limit : pageSize},
								bodyStyle : 'padding:5px 5px 0',
								width : 500,
								reader : new Ext.data.JsonReader({
			            root: 'resultMap', totalProperty: 'totalCount'
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
																},{xtype:'combo',
																	typeAhead : true,
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
																	mode : "remote",// 从本地载值
																	valueField : 'id',// value值，与fields对应
																	displayField : 'loadingName',// 显示值，与fields对应
																	fieldLabel : '装卸组',
																	hiddenName:'loadingbrigadeId',
																	anchor : '95%'
																 },{xtype:'combo',
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
																	valueField : 'id',// value值，与fields对应
																	displayField : 'loadingName',// 显示值，与fields对应
																	fieldLabel : '分拨组',
																	hiddenName:'dispatchId',
																	anchor : '95%'
																 },
																 {
													            	xtype:'combo',
													            	hiddenName:'loadingbrigadeType',
													            	width:100,
													            	emptyText:'请选择',
													            	allowBlank : false,
																	forceSelection : true,
																	triggerAction : 'all',
																	editable : true,
																	mode : "local",//获取本地的值
													            	store:loadingbrigadeTypeStore,
													            	valueField : 'typeId',// value值，与fields对应
																	displayField : 'typeName',// 显示值，与fields对应
													            	fieldLabel : '装卸类型<font style="color:red;">*</font>',
																	anchor : '95%'
													            },{
													            	xtype : 'textfield',
													            	name:'goods',
													            	fieldLabel : '品名',
													            	anchor : '95%'
													            }
																]

													}, {
														columnWidth : .5,
														layout : 'form',
														items : [ {
													            	xtype : 'numberfield',
													            	name:'dno',
													            	fieldLabel : '配送单号',
													            	anchor : '95%'
													            },{
													            	xtype : 'numberfield',
													            	name:'overmemoNo',
													            	fieldLabel : '交接单号',
													            	anchor : '95%'
													            },{
																	xtype : 'numberfield',
																	labelAlign : 'left',
																	allowBlank : false,
																	fieldLabel : '重量<font style="color:red;">*</font>',
																	blankText : "重量不能为空！",
																	nanText:'请输入数字',
																	allowNegative :false,
																	maxLength:10,
																	name : 'weight',
																	anchor : '95%'
																}, {
																	xtype : 'numberfield',
																	labelAlign : 'left',
																	fieldLabel : '件数<font style="color:red;">*</font>',
																	blankText : "件数不能为空！",
																	nanText:'请输入数字',
																	maxLength:10,
																	allowNegative :false,
																	allowBlank : false,
																	name : 'piece',
																	anchor : '95%'
																}, {
																	xtype : 'numberfield',
																	labelAlign : 'left',
																	fieldLabel : '体积<font style="color:red;">*</font>',
																	blankText : "体积不能为空！",
																	nanText:'请输入数字',
																	maxLength:10,
																	allowNegative :false,
																	allowBlank : false,
																	name : 'bulk',
																	anchor : '95%'
																}]
													}]
													
											}]
										});
									
		oprLoadingbrigadeWeightTitle='添加装卸分拨货量信息';
		if(cid!=null){
		oprLoadingbrigadeWeightTitle='修改装卸分拨货量信息';
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
		title : oprLoadingbrigadeWeightTitle,
		width : 500,
		closeAction : 'hide',
		plain : true,
		resizable : false,

		modal : true,
		items : form,
		buttonAlign : "center",	buttons : [{
			text : "保存",
			iconCls : 'groupSave',
			handler : function() {
				var loadingbrigadeId = form.getForm().findField('loadingbrigadeId').getValue();
				
				var dispatchId = form.getForm().findField('dispatchId').getValue();
				if(0==loadingbrigadeId && 0==dispatchId){
					Ext.Msg.alert(alertTitle, "装卸组和分拨组必须选择一个！");
					return;
				}
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
 	 var _record = oprLoadingbrigadeWeightGrid.getSelectionModel().getSelections();
 	
        var updatebtn = Ext.getCmp('basoprLoadingbrigadeWeightEdit');
        var deletebtn = Ext.getCmp('basoprLoadingbrigadeWeightDelete');
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
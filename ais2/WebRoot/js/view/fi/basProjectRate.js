//项目客户协议价js
var privilege=103;//权限参数
var gridSearchUrl="fi/basProjectRateAction!ralaList.action";//查询地址
var saveUrl="fi/basProjectRateAction!save.action";//保存地址
var delUrl="fi/basProjectRateAction!delete.action";//删除地址
var dictionaryUrl='sys/dictionaryAction!ralaList.action';//数据字典地址
var searchCusNameUrl='stock/oprStoreAreaAction!findCusName.action';//查询代理公司地址
var searchAddrUrl='sys/basAreaAction!ralaList.action';//地区查询地址
var authorityDepartUrl='sys/basRightDepartAction!ralaList.action';//权限部门查询地址
var searchCusUrl="sys/customerAction!list.action";//查询发货代理
var searchWidth=70;
var searchDepartId=bussDepart;
var  fields=[{name:"id",mapping:'id'},
     		{name:"conditionUnit",mapping:'conditionUnit'},
     		{name:'addFee',mapping:'addFee'},
     		{name:'minValue',mapping:'minValue'},
     		{name:'maxValue',mapping:'maxValue'},
     		{name:'countWay',mapping:'countWay'},
     		{name:'endAdd',mapping:'endAdd'},
     		{name:'rate',mapping:'rate'},
     		{name:'createName',mapping:'createName'},
     		{name:'createTime',mapping:'createTime'},
     		{name:'updateName',mapping:'updateName'},
     		{name:'updateTime',mapping:'updateTime'},
     		{name:'ts',mapping:'ts'},
     		{name:'lowFee',mapping:'lowFee'},
     		{name:'cusId',mapping:'cusId'},
     		{name:'cusName',mapping:'cusName'},
     		{name:'departName',mapping:'departName'},
     		{name:'departId',mapping:'departId'}];
		//目的站
     	endAddStore= new Ext.data.Store({ 
			storeId:"endAddStore",
			baseParams:{privilege:55,limit:comboSize},
			proxy:new Ext.data.HttpProxy({url:sysPath+"/"+searchAddrUrl}),
			reader:new Ext.data.JsonReader({
	                    root:'result',
	                    totalProperty:'totalCount'
	        },[
        	{name:'endAddId',mapping:'id'},
        	{name:'endAddName',mapping:'areaName'}
        	])
		});
		//代理公司
		cusNameStore= new Ext.data.Store({ 
			storeId:"cusNameStore",
			baseParams:{filter_EQL_isProjectcustomer:1},
			proxy:new Ext.data.HttpProxy({url:sysPath+"/"+searchCusUrl}),
			reader:new Ext.data.JsonReader({
	                   root:'result', totalProperty:'totalCount'
	        },[
        	{name:'cusName',mapping:'cusName'},
        	{name:'cusId',mapping:'id'}
        	])
		});
		 //计费方式20
		countWayStore	= new Ext.data.Store({ 
			storeId:"countWayStore",
			baseParams:{filter_EQL_basDictionaryId:20,privilege:16},
			proxy:new Ext.data.HttpProxy({url:sysPath+"/"+dictionaryUrl}),
			reader:new Ext.data.JsonReader({
	                    root:'result',
	                    totalProperty:'totalCount'
	        },[
        	{name:'countWayId',mapping:'typeCode'},
        	{name:'countWayName',mapping:'typeName'}
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
                	parent.exportExl(projectRateGrid);
        		} },'-',
		{text:'<b>新增</b>',iconCls:'userAdd',id:'basprojectRateAdd',tooltip : '新增项目客户协议价',handler:function() {
                	saveprojectRate(null);
            } },'-',
	 	{text:'<b>修改</b>',iconCls:'userEdit',id:'basprojectRateEdit',disabled:true,tooltip : '修改项目客户协议价',handler:function(){
	 	var projectRate =Ext.getCmp('projectRateCenter');
		var _records = projectRate.getSelectionModel().getSelections();
		
						if (_records.length < 1) {
							Ext.Msg.alert(alertTitle, "请选择您要修改的行！");
							return false;
						} else if (_records.length > 1) {
							Ext.Msg.alert(alertTitle, "一次只能修改一行！");
							return false;
						}
	 	saveprojectRate(_records);}},'-',
	 	{text:'<b>删除</b>',iconCls:'userDelete',id:'basprojectRateDelete',disabled:true,tooltip : '删除项目客户协议价',handler:function(){
	 		var projectRate =Ext.getCmp('projectRateCenter');
			var _records = projectRate.getSelectionModel().getSelections();
	
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

                     	searchprojectRate();

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
    							['EQS_countWay','计价方式'],
    							['EQS_conditionUnit','单位条件'],
    							['EQL_lowFee','最低一票'],
    							['GEL_minValue','大于最小值'],
    							['LEL_maxValue','小于最大值'],
    							['EQS_startAddr','开始地址'],
    							['EQS_endAddr','结束地址'],
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
    									searchprojectRate();
    								}
    							
    							}
    							//查询全部
    							if(combo.getValue()==''){
    								Ext.getCmp("searchCpName").setValue("");
    								Ext.getCmp("searchAuthorityDepart").setValue("");
    								searchprojectRate();
    							}
    						}
    					}
    					
    				}
	 	];	

var queryTbar=new Ext.Toolbar([
	 	
	 	'客商名称:',{xtype : "combo",
				width : searchWidth+20,
				editable:true,
				triggerAction : 'all',
				// typeAhead : true,
				queryParam : 'filter_LIKES_cusName',
				pageSize : pageSize,
				forceSelection : true,
				resizable:true,
				minChars : 0,
				store : cusNameStore,
				mode : "remote",// 从本地载值
				valueField : 'cusName',// value值，与fields对应
				displayField : 'cusName',// 显示值，与fields对应
			    name : 'cpName',
			    id:'searchCpName', 
			    enableKeyEvents:true,listeners : {
	 		    keyup:function(textField, e){
                     if(e.getKey() == 13){
                     	searchprojectRate();
                     }
	 		}
	 	
	 	}},	'-',
	 	'权限部门：',
    				{xtype : 'combo',
						triggerAction : 'all',
						id:'searchAuthorityDepart',
						pageSize:comboSize,
						store : authorityDepartStore,
						resizable:true,
						forceSelection : true,
						valueField : 'departId',//value值，与fields对应
						displayField : 'departName',//显示值，与fields对应
						editable : true,
						name : 'departId',
						anchor : '95%'
						
    				},'-',
	 		{text : '<b>查询</b>',id : 'btn',iconCls : 'btnSearch',
    					handler : searchprojectRate
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
        header:'序号', width:20
    });
    projectRateGrid = new Ext.grid.GridPanel({
        renderTo: Ext.getBody(),
        id:'projectRateCenter',
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
 				{header: '客商名称', dataIndex: 'cusName',sortable : true},
       			{header: '单位条件', dataIndex: 'conditionUnit',sortable : true},
        		{header: '最大数值', dataIndex: 'maxValue',sortable : true},
        		{header: '最小数值', dataIndex: 'minValue',sortable : true},
 				{header: '计算方式', dataIndex: 'countWay',sortable : true},
 				{header: '目的站', dataIndex: 'endAdd',sortable : true},
 				{header: '费率', dataIndex: 'rate',sortable : true},
 				{header: '最低一票', dataIndex: 'lowFee',sortable : true},
 				{header: '追加费用', dataIndex: 'addFee',sortable : true},
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
            filter_EQL_departId:searchDepartId,
            store: dateStore, 
            displayInfo: true,
            displayMsg: '显示第 {0} 条到 {1} 条记录，共 {2} 条', 
            emptyMsg: "没有记录信息显示"
        })
    });
	
	    projectRateGrid.on('click', function() {
	       selabled();
	     	
	    });
		projectRateGrid.on('rowdblclick',function(grid,index,e){
		var _records = projectRateGrid.getSelectionModel().getSelections();
				
				if (_records.length ==1) {
					saveprojectRate(_records);
				}
			 	
		});
    });
    
    
	
   function searchprojectRate() {
		dateStore.proxy = new Ext.data.HttpProxy({
					method : 'POST',
					url : sysPath
								+ "/"+gridSearchUrl,
						params:{privilege:privilege,limit : pageSize}
				});
			var searchCpName = Ext.getCmp('searchCpName').getRawValue();
			var searchAuthorityDepart=Ext.getCmp('searchAuthorityDepart').getValue();
			
			if(searchAuthorityDepart.length>0){
				searchDepartId=searchAuthorityDepart;
			}else{
				searchDepartId=bussDepart;
			}
		
				Ext.apply(dateStore.baseParams, {
					filter_EQS_cusName:searchCpName,
					filter_EQL_departId:searchDepartId,
					//filter_NEL_status:0,
					privilege:privilege,
					limit : pageSize,
					checkItems : '',
					itemsValue : '',
					filter_GED_createTime:'',
					filter_LED_createTime:'',
					filter_GED_updateTime:'',
					filter_LED_updateTime:''
				});
			 if  (Ext.getCmp('searchSelectBox').getValue() == 'EQD_createTime') {
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
		var editbtn = Ext.getCmp('basprojectRateEdit');
		var deletebtn = Ext.getCmp('basprojectRateDelete');

		editbtn.setDisabled(true);
		deletebtn.setDisabled(true);

		dataReload();
		
		
	}
function saveprojectRate(_records) {
				
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
																},{
																	name : "cusId",
																	id:'cusId',
																	xtype : "hidden"
																}, {xtype : "combo",
																	editable:true,
																	triggerAction : 'all',
																	// typeAhead : true,
																	queryParam : 'filter_LIKES_cusName',
																	pageSize : comboSize,
																	forceSelection : true,
																	resizable:true,
																	minChars : 0,
																	fieldLabel:'客商名称<font style="color:red;">*</font>',
																	allowBlank : false,
																	store : cusNameStore,
																	mode : "remote",// 从本地载值
																	valueField : 'cusId',// value值，与fields对应
																	displayField : 'cusName',// 显示值，与fields对应
																	name : 'cusName',
																	id:'cpNameId', 
																	anchor : '95%',
																    enableKeyEvents:true,listeners : {
																    'select':function(combo,e){
																    	Ext.getCmp('cusId').setValue(combo.getValue());
																    },
																    keyup:function(combo, e){
															             if(e.getKey() == 13){
															             	//searchprojectRate();
															             }
																		}
																	
																	}},
																{
																	xtype : 'combo',
																	triggerAction : 'all',
																	store : countWayStore,
																	minChars : 0,
																	editable:false,
																	fieldLabel:'条件单位<font style="color:red;">*</font>',
																	allowBlank : false,
																	forceSelection : true,
																	//valueField : 'countWayId',//value值，与fields对应
																	displayField : 'countWayName',//显示值，与fields对应
																	name:'conditionUnit',
																	id:'conditionUnitId',
																	anchor : '95%',
																	 enableKeyEvents:true,listeners : {
															 		    keyup:function(textField, e){
														                     if(e.getKey() == 13){
														                     	//searchprojectRate();
														                     }
															 		    }
															 	
															 		}
																},{
																	xtype : 'numberfield',
																	labelAlign : 'left',
																	fieldLabel : '最小数值<font style="color:red;">*</font>',
																	blankText : "最小数值不能为空！",
																	nanText:'请输入数字',
																	maxLength:10,
																	allowBlank:false,
																	allowNegative :false,
																	name : 'minValue',
																	id:'minValueId',
																	anchor : '95%'
																}, {
																	xtype : 'numberfield',
																	labelAlign : 'left',
																	fieldLabel : '最大数值<font style="color:red;">*</font>',
																	blankText : "最大数值不能为空！",
																	nanText:'请输入数字',
																	maxLength:10,
																	allowNegative :false,
																	allowBlank:false,
																	name : 'maxValue',
																	id:'maxValueId',
																	anchor : '95%'
																}, {
																	xtype : 'numberfield',
																	labelAlign : 'left',
																	fieldLabel : '追加费用',
																	blankText : "追加费用不能为空！",
																	nanText:'请输入数字',
																	maxValue:99999999.99,
																	minValue:0.0,
																	maxLength:11,
																	allowNegative :false,
																	name : 'addFee',
																	id:'addFeeId',
																	anchor : '95%'
																}]

													}, {
														columnWidth : .5,
														layout : 'form',
														items : [{
																	xtype : 'combo',
																	triggerAction : 'all',
																	store : countWayStore,
																	minChars : 0,
																	editable:false,
																	fieldLabel:'计费方式<font style="color:red;">*</font>',
																	allowBlank : false,
																	forceSelection : true,
																	//valueField : 'countWayId',//value值，与fields对应
																	displayField : 'countWayName',//显示值，与fields对应
																	name:'countWay',
																	id:'countWayId',
																	anchor : '95%',
																	 enableKeyEvents:true,listeners : {
															 		    keyup:function(textField, e){
														                     if(e.getKey() == 13){
														                     	//searchprojectRate();
														                     }
															 		    }
															 	
															 		}
																},  {
																	xtype : "combo",
																	triggerAction : 'all',
																	queryParam : 'filter_LIKES_areaName',
																	pageSize : comboSize,
																	forceSelection : true,
																	resizable:true,
																	minChars : 0,
																	fieldLabel:'目的站',
																	allowBlank : false,
																	store : endAddStore,
																	mode : "remote",// 从本地载值
																	//valueField : 'endAddId',// value值，与fields对应
																	displayField : 'endAddName',// 显示值，与fields对应
																	name : 'endAdd',
																	id:'endAddId',
																	anchor : '95%',
																	    enableKeyEvents:true,listeners : {
																	    keyup:function(textField, e){
																             if(e.getKey() == 13){
																             	//searchprojectRate();
																             }
																	}
																	}
																},{
																	xtype : 'numberfield',
																	labelAlign : 'left',
																	fieldLabel : '最低一票',
																	blankText : "最低一票不能为空！",
																	nanText:'请输入数字',
																	allowNegative :false,
																	maxLength:10,
																	name : 'lowFee',
																	anchor : '95%'
																}, {
																	xtype : 'numberfield',
																	labelAlign : 'left',
																	allowBlank : false,
																	fieldLabel : '费率<font style="color:red;">*</font>',
																	blankText : "费率不能为空！",
																	nanText:'请输入数字',
																	allowNegative :false,
																	maxLength:10,
																	name : 'rate',
																	anchor : '95%'
																}]
													}]
													
										}]
										});
									
		projectRateTitle='添加项目客户协议价信息';
		if(cid!=null){
		projectRateTitle='修改项目客户协议价信息';
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
		title : projectRateTitle,
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
				var minValueId=Ext.getCmp('minValueId').getValue();
				var maxValueId=Ext.getCmp('maxValueId').getValue();
				
				if(maxValueId.length==0 && minValueId.length==0){
					Ext.Msg.alert(alertTitle,'最小值和最大值至少要输入其中之一！');
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
											Ext.getCmp('cpNameId').focus();
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
				filter_EQL_departId:searchDepartId,
				//filter_NEL_status:0,
				privilege:privilege,
				limit : pageSize
			}
		});
		
		selabled();
		
 }
 
 function selabled(){
 	 var _record = projectRateGrid.getSelectionModel().getSelections();
 	
        var updatebtn = Ext.getCmp('basprojectRateEdit');
        var deletebtn = Ext.getCmp('basprojectRateDelete');
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
//内部结算划分js
var privilege=169;//权限参数
var gridSearchUrl="fi/fiInternalDivideAction!ralaList.action";//内部结算划分查询地址
var saveUrl="fi/fiInternalDivideAction!save.action";//内部结算划分保存地址
var invalidUrl="fi/fiInternalDivideAction!invalid.action";//内部结算划分作废地址
var auditUrl="fi/fiInternalDivideAction!audit.action";//内部结算划分审核地址
var cancelAuditUrl="fi/fiInternalDivideAction!cancelAudit.action";//内部结算划分撤销审核地址
var departUrl='sys/departAction!findAll.action';//部门查询地址
var searchCustomerUrl='sys/customerAction!ralaList.action';//查询客商地址
var departUrl='sys/departAction!findAll.action';//部门查询地址
var searchWidth=70;
var colWidth=90;
var pubauthorityDepart=bussDepart;
var  fields=['id','dno','bearDepartId','bearDepartName','benefitDepartId','benefitDepartName','amount',
			 'auditStatus','auditName','auditTime','auditRemark',
			 'remark','departId','departName','createTime','createName','updateTime','updateName','ts'];

        statusStore=new Ext.data.SimpleStore({
			 autoLoad:true, //此处设置为自动加载
  			  data:[['1','未审核'],['2','已审核'],['0','作废']],
   			 fields:["statusId","statusName"]
		});
		//客商名称
		customerStore= new Ext.data.Store({
			autoLoad:true,
			storeId:"customerStore",
			baseParams:{privilege:61},
			proxy:new Ext.data.HttpProxy({url:sysPath+"/"+searchCustomerUrl}),
			reader:new Ext.data.JsonReader({
	        	root:'result', totalProperty:'totalCount'
	        },[
        	{name:'cusName',mapping:'cusName'},
        	{name:'cusId',mapping:'id'}
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
        
	var tbar=[
		{text:'<b>新增</b>',iconCls:'userAdd',id:'fiInternalSpecialRateAdd',tooltip : '新增内部结算划分',handler:function() {
                	savefiInternalSpecialRateRate(null);
            } },'-',
	 	{text:'<b>修改</b>',iconCls:'userEdit',id:'fiInternalSpecialRateEdit',disabled:true,tooltip : '修改内部结算划分',handler:function(){
	 	var fiInternalSpecialRateRate =Ext.getCmp('fiInternalSpecialRateRateCenter');
		var _records = fiInternalSpecialRateRate.getSelectionModel().getSelections();
		
						if (_records.length < 1) {
							Ext.Msg.alert(alertTitle, "请选择您要修改的行！");
							return false;
						} else if (_records.length > 1) {
							Ext.Msg.alert(alertTitle, "一次只能修改一行！");
							return false;
						}
						if(_records[0].data.auditStatus==0){
							Ext.Msg.alert(alertTitle, "该内部结算划分已经作废，不允许更改！");
							return false;
						}else if(_records[0].data.auditStatus==2){
							Ext.Msg.alert(alertTitle, "该内部结算划分已经审核，不允许更改！");
							return false;
						}
	 				savefiInternalSpecialRateRate(_records);}},'-',
	 	{text:'<b>作废</b>',iconCls:'userDelete',id:'fiInternalSpecialRateDelete',disabled:true,tooltip : '作废内部结算划分',handler:function(){
	 		var fiInternalSpecialRateRate =Ext.getCmp('fiInternalSpecialRateRateCenter');
			var _records = fiInternalSpecialRateRate.getSelectionModel().getSelections();
	
					if (_records.length < 1) {
						Ext.Msg.alert(alertTitle, "请选择您要作废的行！");
						return false;
					}
					for(var i=0;i<_records.length;i++){
						if(_records[i].data.auditStatus==0){
							Ext.Msg.alert(alertTitle, "您所选择的第"+(i+1)+"行已经作废！");
							return false;
						}else if(_records[i].data.auditStatus!=1){
							Ext.Msg.alert(alertTitle, "您所选择的第"+(i+1)+"行不是未审核状态，不允许作废！");
							return false;
						}		
					}

					Ext.Msg.confirm(alertTitle, "确定要作废这"+_records.length+"条记录吗？", function(
									btnYes) {
								if (btnYes == 'yes' || btnYes == 'ok'
										|| btnYes == true) {
									var ids="";
									for(var i=0;i<_records.length;i++){
									
										 ids=ids+_records[i].data.id+',';
									}
									Ext.Ajax.request({
										url : sysPath+'/'
												+ invalidUrl,
										params : {
											ids : ids,
										    privilege:privilege,
										    limit : pageSize
										},
										success : function(resp) {
											var respText = Ext.util.JSON
													.decode(resp.responseText);

											Ext.Msg.alert(alertTitle,
													  "作废成功!");
											dataReload();
										}
									});
								}
							});
	 	}},'-',
	 	{text:'<b>审核</b>',iconCls:'groupPass',tooltip : '审核内部结算划分',handler:function() {
            var fiInternalSpecialRateRate =Ext.getCmp('fiInternalSpecialRateRateCenter');
			var _records = fiInternalSpecialRateRate.getSelectionModel().getSelections();
	
			if (_records.length < 1) {
				Ext.Msg.alert(alertTitle, "请选择您要审核的记录！");
				return false;
			}else if(_records.length > 1){
				Ext.Msg.alert(alertTitle, "一次只允许审核一条记录！");
				return false;
			}else if(_records[0].data.auditStatus ==2){
				Ext.Msg.alert(alertTitle, "该记录已经审核！");
				return false;
			}else if(_records[0].data.auditStatus ==0){
				Ext.Msg.alert(alertTitle, "该记录已经作废，不允许审核！");
				return false;
			}
              auditfiInternalSpecialRateRate(_records);
        } },'-',
        {text:'<b>撤销审核</b>',iconCls:'sort_down',tooltip : '撤销审核内部结算划分',handler:function() {
              var fiInternalSpecialRateRate =Ext.getCmp('fiInternalSpecialRateRateCenter');
			  var _records = fiInternalSpecialRateRate.getSelectionModel().getSelections();
		
				if (_records.length < 1) {
					Ext.Msg.alert(alertTitle, "请选择您要修改的行！");
					return false;
				} 
				if(_records[0].data.auditStatus!=2){
					Ext.Msg.alert(alertTitle, "该内部结算划分不是已审核状态，不能撤销！");
					return false;
				}
               cancelAudit(_records);
        } },'-',
	 	{text:'<b>导出</b>',iconCls:'sort_down',tooltip : '导出内部结算划分',handler:function() {
               parent.exportExl(fiInternalSpecialRateRateGrid);
        } },'-',
        {text:'<b>打印</b>',iconCls:'printBtn',tooltip : '打印内部结算划分',handler:function() {
            	alert('功能待实现..');
        } },'-',
        {text:'<b>查询</b>',iconCls:'btnSearch',tooltip : '查询内部结算划分',handler:function() {
            	searchfiInternalSpecialRateRate();
        } }
	 	];	

var queryTbar=new Ext.Toolbar([
		'创建日期','-',{xtype : 'datefield',
    		id : 'startDate',
    		format : 'Y-m-d',
    		emptyText : "开始时间",
    		anchor : '95%',
    		value:new Date().add(Date.DAY, -7),
    		hidden : false,
    		width : 100,
    		disabled : false,
    		listeners : {
    			'select' : function() {
    			   var start = Ext.getCmp('startDate').getValue()
    			      .format("Y-m-d");
    			   Ext.getCmp('endDate').setMinValue(start);
    		     }
    		}
    	}, '&nbsp;至&nbsp;', {
    		xtype : 'datefield',
    		id : 'endDate',
    		format : 'Y-m-d',
    		emptyText : "结束时间",
    		value:new Date(),
    		hidden : false,
    		width : 100,
    		disabled : false,
    		anchor : '95%'
    	},'客商名称:',{xtype : "combo",
				width : searchWidth+20,
				editable:true,
				triggerAction : 'all',
				typeAhead : true,
				queryParam : 'filter_LIKES_cusName',
				pageSize : pageSize,
				forceSelection : true,
				resizable:true,
				minChars : 0,
				store : customerStore,
				//mode : "remote",// 从本地载值
				valueField : 'cusId',// value值，与fields对应
				displayField : 'cusName',// 显示值，与fields对应
			    id:'searchCusName', 
			    enableKeyEvents:true,listeners : {
	 		    keyup:function(textField, e){
                     if(e.getKey() == 13){
                     	searchfiInternalSpecialRateRate();
                     }
	 		}
	 	
	 	}},'-','状态:',{
	 		xtype : 'combo',
			typeAhead : true,
			triggerAction : 'all',
			store : statusStore,
			editable:true,
			id:'searchStatus',
			width:searchWidth,
			forceSelection : true,
			mode : "local",//从服务器端加载值
			valueField : 'statusId',//value值，与fields对应
			displayField : 'statusName',//显示值，与fields对应
			enableKeyEvents:true,
			listeners : {
	 			keyup:function(textField, e){
		             if(e.getKey() == 13){
		             	searchfiInternalSpecialRateRate();
		             }
	 			}
			}
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
    fiInternalSpecialRateRateGrid = new Ext.grid.GridPanel({
        renderTo: Ext.getBody(),
        id:'fiInternalSpecialRateRateCenter',
        height:Ext.lib.Dom.getViewHeight(),		
        width:Ext.lib.Dom.getViewWidth(),
        viewConfig : {
			columnsText : "显示的列",
			sortAscText : "升序",
			sortDescText : "降序"
//			forceFit : true
		},
		autoScroll:false, 
//		autoExpandColumn:1,
        frame:true, 
        loadMask:true,
        sm:sm,
        cm:new Ext.grid.ColumnModel([rowNum,sm,
        		{header: 'ID', dataIndex: 'id',sortable : true,width:colWidth,hidden:true},
       			{header: '配送单号', dataIndex: 'dno',sortable : true,width:colWidth},
        		{header: '承担部门', dataIndex: 'bearDepartName',sortable : true,width:colWidth},
        		{header: '收益部门', dataIndex: 'benefitDepartName',sortable : true,width:colWidth},
 				{header: '划分金额', dataIndex: 'amount',sortable : true,width:colWidth},
 				{header: '备注', dataIndex: 'remark',sortable : true,width:colWidth},
 				{header: '状态', dataIndex: 'auditStatus',sortable : true,width:colWidth
 					,renderer:function renderDescn(v, cellmeta, record, rowIndex, columnIndex, store){
 						//fiInternalSpecialRateRateGrid.getView().getRow(rowIndex).style.backgroundColor='#BFBFC1';
						return v==1?'未审核':(v==2?'已审核':'作废');
		        	}
 				},
 				{header: '审核人', dataIndex: 'auditName',sortable : true,width:colWidth},
 				{header: '审核时间', dataIndex: 'auditTime',sortable : true,width:colWidth},
 				{header: '审核备注', dataIndex: 'auditRemark',sortable : true,width:colWidth},
 				{header: '创建部门', dataIndex: 'departName',sortable : true,width:colWidth},
 				{header: '创建人', dataIndex: 'createName',sortable : true,width:colWidth},
 				{header: '创建时间', dataIndex: 'createTime',sortable : true,width:colWidth},
 				{header: '修改人', dataIndex: 'updateName',sortable : true,width:colWidth},
 				{header: '修改时间', dataIndex: 'updateTime',sortable : true,width:colWidth}
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
	
	    fiInternalSpecialRateRateGrid.on('click', function() {
	       selabled();
	     	
	    });
		fiInternalSpecialRateRateGrid.on('rowdblclick',function(grid,index,e){
		var _records = fiInternalSpecialRateRateGrid.getSelectionModel().getSelections();
				
				if (_records.length ==1) {
					savefiInternalSpecialRateRate(_records);
				}
			 	
		});
    });
	
   function searchfiInternalSpecialRateRate() {
		dateStore.proxy = new Ext.data.HttpProxy({
					method : 'POST',
					url : sysPath
								+ "/"+gridSearchUrl,
						params:{privilege:privilege,limit : pageSize}
				});
			var searchCusName = Ext.getCmp('searchCusName').getValue();
			var searchStatus = Ext.getCmp('searchStatus').getValue();
			Ext.apply(dateStore.baseParams, {
				filter_EQL_aduitStatus:searchStatus,
				filter_EQL_customerId:searchCusName,
				filter_GED_createTime:(!Ext.getCmp("startDate").hidden?(Ext.get("startDate").dom.value=="开始时间"?"":Ext.get("startDate").dom.value):""),
				filter_LED_createTime:(!Ext.getCmp("endDate").hidden?(Ext.get("endDate").dom.value=="结束时间"?"":Ext.get("endDate").dom.value):""),
//					filter_NEL_status:0,
				privilege:privilege,
				limit : pageSize,
				checkItems : '',
				itemsValue : ''
			});
		var editbtn = Ext.getCmp('fiInternalSpecialRateEdit');
		var deletebtn = Ext.getCmp('fiInternalSpecialRateDelete');

		editbtn.setDisabled(true);
		deletebtn.setDisabled(true);

		dataReload();
		
		
	}
function savefiInternalSpecialRateRate(_records) {
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
																	name : "bearDepartId",
																	id:'bearDepartId',
																	xtype : "hidden"
																},{
																	name : "benefitDepartId",
																	id:'benefitDepartId',
																	xtype : "hidden"
																},{
																	xtype : 'combo',
																	triggerAction : 'all',
																	store : departStore,
																	listWidth:245,
																	fieldLabel:'承担部门<font style="color:red;">*</font>',
																	typeAhead : true,
																	queryParam : 'filter_LIKES_departName',
																	pageSize : comboSize,
																	resizable:true,
																	minChars :0,
																	allowBlank : false,
																	forceSelection : true,
																	valueField : 'departId',//value值，与fields对应
																	displayField : 'departName',//显示值，与fields对应
																	name:'bearDepartName',
																	anchor : '95%',
																	enableKeyEvents:true,listeners : {
															 		    select:function(combo, e){
															 		    	Ext.getCmp('bearDepartId').setValue(combo.getValue());
															 		    }
															 		}
																},{xtype : 'combo',
																	xtype : 'combo',
																	triggerAction : 'all',
																	store : departStore,
																	listWidth:245,
																	minChars : 0,
																	fieldLabel:'收益部门<font style="color:red;">*</font>',
																	typeAhead : true,
																	queryParam : 'filter_LIKES_departName',
																	pageSize : comboSize,
																	resizable:true,
																	allowBlank : false,
																	forceSelection : true,
																	valueField : 'departId',//value值，与fields对应
																	displayField : 'departName',//显示值，与fields对应
																	name:'benefitDepartName',
																	anchor : '95%',
																	enableKeyEvents:true,listeners : {
															 		    select:function(combo, e){
															 		    	Ext.getCmp('benefitDepartId').setValue(combo.getValue());
															 		    }
															 		}
																}]

													}, {
														columnWidth : .5,
														layout : 'form',
														items : [{
																	xtype :'numberfield',
																	name:'dno',
																	fieldLabel:'配送单号<font style="color:red;">*</font>',
																	allowNegative :false,
																	minValue:0,
																	allowBlank : false,
																	maxLength :6,
																	anchor : '95%'
																},{
																	xtype :'numberfield',
																	name:'amount',
																	fieldLabel:'划分金额<font style="color:red;">*</font>',
																	allowNegative :false,
																	allowBlank : false,
																	minValue:0,
																	maxLength :10,
																	anchor : '95%'
																}]
													}]
													
										},{
											xtype : 'textarea',
											labelAlign : 'left',
											height:40,
											id:'remarkId',
											fieldLabel : '备注',
											name : 'remark',
											anchor : '95%',
											enableKeyEvents:true,
											listeners : {
									 		keyup:function(textField, e){
									             if(e.getKey() == 13){
									             	Ext.getCmp('saveBtn').focus();
									             }
									 			}
											}
										}]
										});
									
		fiInternalSpecialRateRateTitle='添加内部结算划分信息';
		if(cid!=null){
		fiInternalSpecialRateRateTitle='修改内部结算划分信息';
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
		title : fiInternalSpecialRateRateTitle,
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
			id:'saveBtn',
			text : "保存",
			iconCls : 'groupSave',
			handler : function() {
				if (form.getForm().isValid()) {
					//this.disabled = true;//只能点击一次
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
										}else{
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
function auditfiInternalSpecialRateRate(_records) {
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
																	xtype : 'combo',
																	triggerAction : 'all',
																	store : departStore,
																	listWidth:245,
																	fieldLabel:'承担部门<font style="color:red;">*</font>',
																	typeAhead : true,
																	queryParam : 'filter_LIKES_departName',
																	pageSize : comboSize,
																	resizable:true,
																	minChars :0,
																	disabled : true,
																	allowBlank : false,
																	forceSelection : true,
																	valueField : 'departId',//value值，与fields对应
																	displayField : 'departName',//显示值，与fields对应
																	name:'bearDepartName',
																	anchor : '95%'
																},{xtype : 'combo',
																	xtype : 'combo',
																	triggerAction : 'all',
																	store : departStore,
																	listWidth:245,
																	minChars : 0,
																	fieldLabel:'收益部门<font style="color:red;">*</font>',
																	typeAhead : true,
																	queryParam : 'filter_LIKES_departName',
																	pageSize : comboSize,
																	resizable:true,
																	disabled : true,
																	allowBlank : false,
																	forceSelection : true,
																	valueField : 'departId',//value值，与fields对应
																	displayField : 'departName',//显示值，与fields对应
																	name:'benefitDepartName',
																	anchor : '95%'
																},{
																	xtype : 'textarea',
																	labelAlign : 'left',
																	height:40,
																	disabled : true,
																	fieldLabel : '备注',
																	name : 'remark',
																	anchor : '95%'
																}]

													}, {
														columnWidth : .5,
														layout : 'form',
														items : [{
																	xtype :'numberfield',
																	name:'dno',
																	fieldLabel:'配送单号<font style="color:red;">*</font>',
																	allowNegative :false,
																	minValue:0,
																	disabled : true,
																	allowBlank : false,
																	maxLength :6,
																	anchor : '95%'
																},{
																	xtype :'numberfield',
																	name:'amount',
																	fieldLabel:'划分金额<font style="color:red;">*</font>',
																	allowNegative :false,
																	allowBlank : false,
																	minValue:0,
																	maxLength :10,
																	anchor : '95%'
																},{
																	xtype : 'textarea',
																	labelAlign : 'left',
																	height:40,
																	id:'remarkId',
																	fieldLabel : '审核备注',
																	name : 'auditRemark',
																	anchor : '95%',
																	enableKeyEvents:true,
																	listeners : {
															 		keyup:function(textField, e){
															             if(e.getKey() == 13){
															             	Ext.getCmp('saveBtn').focus();
															             }
															 			}
																	}
																}]
													}]
													
										}]
										});
									
		fiInternalSpecialRateRateTitle='审核内部结算划分信息';
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
		var win = new Ext.Window({
		title : fiInternalSpecialRateRateTitle,
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
			id:'saveBtn',
			text : "保存",
			iconCls : 'groupSave',
			handler : function() {
				if (form.getForm().isValid()) {
					//this.disabled = true;//只能点击一次
					form.getForm().submit({
						url : sysPath
								+ '/'+auditUrl,
								params:{privilege:privilege,limit : pageSize},
						waitMsg : '正在保存数据...',
						success : function(form, action) {
							 Ext.Msg.alert(alertTitle,
									action.result.msg, function() {
										dataReload();
										if(_records!=null){
											win.hide();
										}else{
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
 function cancelAudit(_records){
 	
 	for(var i=0;i<_records.length;i++){
		if(_records[i].data.auditStatus!=2){
			Ext.Msg.alert(alertTitle, "您所选择的第"+(i+1)+"行不是已审核状态！");
			return false;
		}		
	}

	Ext.Msg.confirm(alertTitle, "确定要撤销审核这"+_records.length+"条记录吗？", function(
					btnYes) {
				if (btnYes == 'yes' || btnYes == 'ok'
						|| btnYes == true) {
					var ids="";
					for(var i=0;i<_records.length;i++){
					
						 ids=ids+_records[i].data.id+',';
					}
					Ext.Ajax.request({
						url : sysPath+'/'
								+ cancelAuditUrl,
						params : {
							ids : ids,
						    privilege:privilege,
						    limit : pageSize
						},
						success : function(resp) {
							var respText = Ext.util.JSON
									.decode(resp.responseText);

							Ext.Msg.alert(alertTitle,
									  "撤销成功!");
							dataReload();
						}
					});
				}
			});
 	
 }
 function dataReload(){
			dateStore.reload({
			params : {
				start : 0,
				//filter_NEL_status:0,
				departId1:pubauthorityDepart,
				privilege:privilege,
				limit : pageSize
			}
		});
		
		selabled();
		
 }
 
 function selabled(){
 	 var _record = fiInternalSpecialRateRateGrid.getSelectionModel().getSelections();
 	
        var updatebtn = Ext.getCmp('fiInternalSpecialRateEdit');
        var deletebtn = Ext.getCmp('fiInternalSpecialRateDelete');
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
 Ext.override(Ext.grid.RowSelectionModel, {
      selectRow: function(index, keepExisting, preventViewNotify) {
	        if (this.isLocked() || (index < 0 || index >= this.grid.store.getCount()) 
	        				|| (keepExisting && this.isSelected(index)) 
	        				   || (Number(this.grid.store.getAt(index).get("auditStatus")) == 0)) {
	        //根据每行的一个标识字段来判断是否选中
	        
	            return;
	        }
	        var r = this.grid.store.getAt(index);
	        if (r && this.fireEvent('beforerowselect', this, index, keepExisting, r) !== false) {
	            if (!keepExisting || this.singleSelect) {
	                this.clearSelections();
	            }
	            this.selections.add(r);
	            this.last = this.lastActive = index;
	            if (!preventViewNotify) {
	                this.grid.getView().onRowSelect(index);
	            }
	            this.fireEvent('rowselect', this, index, r);
	            this.fireEvent('selectionchange', this);
	    	    }
	
    	}
	});
 function run(){
    var all = Ext.query('input[type!=hidden]'); // 查找所有非隱藏元素   
    Ext.each(all, function(o, i, all) { // 遍曆並添加enter的監聽  
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
                            all[i + 1].click(); // 如果點擊則觸發click事件   
  
                        return true;   
                    }   
                })   
            });   
    Ext.getBody().focus(); // 使頁面獲取焦點，否則下面設定默認焦點的功能不靈驗   
  
    try {   
        var el;   
        if (typeof eval(xFocus) == 'object') { // 如果傳入的是id或dom節點   
            el = Ext.getDom(xFocus).tagName == 'input'  
                    ? Ext.getDom(xFocus)   
                    : Ext.get(xFocus).first('input', true); // 找到input框   
        } else {   
            el = all[xFocus || 0]; // 通過索引號找   
        }   
        el.focus();   
    } catch (e) {   
    }   
}   
Ext.isReady ? run() : Ext.onReady(run); // 頁面加載完成後添加表單導航 
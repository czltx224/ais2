//内部结算协议价js
var privilege=165;//权限参数
var gridSearchUrl="fi/fiInternalRateAction!ralaList.action";//内部协议价查询地址
var saveUrl="fi/fiInternalRateAction!save.action";//内部协议价保存地址
var invalidUrl="fi/fiInternalRateAction!invalid.action";//内部协议价作废地址
var departUrl='sys/departAction!findAll.action';//部门查询地址
var searchWidth=70;
var colWidth=80;
var pubauthorityDepart=bussDepart;
var  fields=['id','startDepartId','startDepartName','endDepartId','endDepartName','xbFrommention','xbFrommentionLowest',
			 'xbCity','xbCityLowest','xbSuburbs','xbSuburbsLowest','transitFrommention','transitFrommentionLowest','transitCity',
			 'transitCityLowest','transitSuburbs','transitSuburbsLowest','outFrommention','outFrommentionLowest','outDelivery',
			 'outDeliveryLowest','remark','departId','departName','createTime','createName','updateTime','updateName','ts','status'];


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
        
        statusStore=new Ext.data.SimpleStore({
			 autoLoad:true, //此处设置为自动加载
  			  data:[['1','正常'],['0','作废']],
   			 fields:["statusId","statusName"]
		});
	var tbar=[
		{text:'<b>新增</b>',iconCls:'userAdd',id:'fiInternalRateAdd',tooltip : '新增内部结算协议价',handler:function() {
                	savefiInternalRateRate(null);
            } },'-',
	 	{text:'<b>修改</b>',iconCls:'userEdit',id:'fiInternalRateEdit',disabled:true,tooltip : '修改内部结算协议价',handler:function(){
	 	var fiInternalRateRate =Ext.getCmp('fiInternalRateRateCenter');
		var _records = fiInternalRateRate.getSelectionModel().getSelections();
		
						if (_records.length < 1) {
							Ext.Msg.alert(alertTitle, "请选择您要修改的行！");
							return false;
						} else if (_records.length > 1) {
							Ext.Msg.alert(alertTitle, "一次只能修改一行！");
							return false;
						}
//						if(_records[0].data.status==0){
//							Ext.Msg.alert(alertTitle, "该内部结算协议价已经作废，不允许更改！");
//							return false;
//						}
	 				savefiInternalRateRate(_records);}},'-',
	 	{text:'<b>作废</b>',iconCls:'userDelete',id:'fiInternalRateDelete',disabled:true,tooltip : '作废内部结算协议价',handler:function(){
	 		var fiInternalRateRate =Ext.getCmp('fiInternalRateRateCenter');
			var _records = fiInternalRateRate.getSelectionModel().getSelections();
	
					if (_records.length < 1) {
						Ext.Msg.alert(alertTitle, "请选择您要作废的行！");
						return false;
					}
					for(var i=0;i<_records.length;i++){
						if(_records[i].data.status==0){
							Ext.Msg.alert(alertTitle, "您选择的第"+(i+1)+"条内部结算协议价已经作废！");
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
	 	{text:'<b>导出</b>',iconCls:'sort_down',tooltip : '导出内部结算协议价',handler:function() {
                	parent.exportExl(fiInternalRateRateGrid);
        } },'-',
        {text:'<b>打印</b>',iconCls:'printBtn',tooltip : '打印内部结算协议价',handler:function() {
            	alert('功能待实现..');
        } },'-',
        {text:'<b>查询</b>',iconCls:'btnSearch',tooltip : '查询内部结算协议价',handler:function() {
            	searchfiInternalRateRate();
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
    	},'-','始发部门:',{xtype : "combo",
			editable:true,
			triggerAction : 'all',
			queryParam : 'filter_LIKES_departName',
			forceSelection : true,
			resizable:true,
			width:searchWidth,
			minChars : 0,
			pageSize:comboSize,
			store : departStore,
			listWidth:245,
			valueField : 'departId',// value值，与fields对应
			displayField : 'departName',// 显示值，与fields对应
			id:'searchStartDepart',
		    enableKeyEvents:true,
		    listeners : {
			   keyup:function(combo, e){
		             if(e.getKey() == 13){
		             	searchfiInternalRateRate();
		             }
	 			}
		
		}},
		'-','到达部门:',{xtype : "combo",
			editable:true,
			triggerAction : 'all',
			queryParam : 'filter_LIKES_departName',
			forceSelection : true,
			resizable:true,
			width:searchWidth,
			minChars : 0,
			pageSize:comboSize,
			store : departStore,
			listWidth:245,
			valueField : 'departId',// value值，与fields对应
			displayField : 'departName',// 显示值，与fields对应
			id:'searchEndDepart',
		    enableKeyEvents:true,
		    listeners : {
			   keyup:function(combo, e){
		             if(e.getKey() == 13){
		             	searchfiInternalRateRate();
		             }
	 			}
		
		}},'状态:',{
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
		             	searchfiInternalRateRate();
		             }
	 			}
			}
	 	}	
	 	]);	
	 	
Ext.onReady(function() {
   	dateStore = new Ext.data.Store({
        storeId:"dateStore",
        proxy: new Ext.data.HttpProxy({url : sysPath+ "/"+gridSearchUrl,
		baseParams:{privilege:privilege,limit : pageSize}}),
        reader: new Ext.data.JsonReader({
            root: 'result', totalProperty: 'totalCount'
        }, fields)
    });
   
   var sm = new Ext.grid.CheckboxSelectionModel({});
	var rowNum = new Ext.grid.RowNumberer({
        header:'序号', width:20
    });
    fiInternalRateRateGrid = new Ext.grid.GridPanel({
        renderTo: Ext.getBody(),
        id:'fiInternalRateRateCenter',
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
       			{header: '始发部门', dataIndex: 'startDepartName',sortable : true,width:colWidth},
        		{header: '到达部门', dataIndex: 'endDepartName',sortable : true,width:colWidth},
        		{header: '新邦自提', dataIndex: 'xbFrommention',sortable : true,width:colWidth},
 				{header: '新邦自提最低一票', dataIndex: 'xbFrommentionLowest',sortable : true,width:colWidth},
 				{header: '新邦市内送货', dataIndex: 'xbCity',sortable : true,width:colWidth},
 				{header: '新邦市内送货最低一票', dataIndex: 'xbCityLowest',sortable : true,width:colWidth},
 				{header: '新邦郊区送货', dataIndex: 'xbSuburbs',sortable : true,width:colWidth},
 				{header: '新邦郊区送货最低一票', dataIndex: 'xbSuburbsLowest',sortable : true,width:colWidth},
 				{header: '中转自提', dataIndex: 'transitFrommention',sortable : true,width:colWidth},
 				{header: '中转自提最低一票', dataIndex: 'transitFrommentionLowest',sortable : true,width:colWidth},
 				{header: '中转市内送货', dataIndex: 'transitCity',sortable : true,width:colWidth},
 				{header: '中转市内送货最低一票', dataIndex: 'transitCityLowest',sortable : true,width:colWidth},
 				{header: '中转郊区送货', dataIndex: 'transitSuburbs',sortable : true,width:colWidth},
 				{header: '中转郊区送货最低一票', dataIndex: 'transitSuburbsLowest',sortable : true,width:colWidth},
 				{header: '外发自提', dataIndex: 'outFrommention',sortable : true,width:colWidth},
 				{header: '外发自提最低一票', dataIndex: 'outFrommentionLowest',sortable : true,width:colWidth},
 				{header: '外发送货', dataIndex: 'outDelivery',sortable : true,width:colWidth},
 				{header: '外发送货最低一票', dataIndex: 'outDeliveryLowest',sortable : true,width:colWidth},
 				{header: '创建部门', dataIndex: 'departName',sortable : true,width:colWidth},
 				{header: '创建时间', dataIndex: 'createTime',sortable : true,width:colWidth},
 				{header: '创建人', dataIndex: 'createName',sortable : true,width:colWidth},
 				{header: '修改时间', dataIndex: 'updateTime',sortable : true,width:colWidth},
 				{header: '修改人', dataIndex: 'updateName',sortable : true,width:colWidth},
 				{header: '状态', dataIndex: 'status',sortable : true,width:colWidth
					,renderer:function(v){
								return v==1?'正常':'作废';
		        	}
 				}
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
	
	    fiInternalRateRateGrid.on('click', function() {
	       selabled();
	     	
	    });
		fiInternalRateRateGrid.on('rowdblclick',function(grid,index,e){
		var _records = fiInternalRateRateGrid.getSelectionModel().getSelections();
				
				if (_records.length ==1) {
					savefiInternalRateRate(_records);
				}
			 	
		});
    });
    
    
	
   function searchfiInternalRateRate() {
		dateStore.proxy = new Ext.data.HttpProxy({
					method : 'POST',
					url : sysPath+ "/"+gridSearchUrl,
					baseParams:{privilege:privilege,limit : pageSize}
				});
			var searchEndDepart = Ext.getCmp('searchEndDepart').getValue();
			var searchStartDepart = Ext.getCmp('searchStartDepart').getValue();
			var searchStatus = Ext.getCmp('searchStatus').getValue();
			
				Ext.apply(dateStore.baseParams,{
					filter_EQL_endDepartId:searchEndDepart,
					filter_EQL_startDepartId:searchStartDepart,
					filter_EQL_status:searchStatus,
					filter_GED_createTime:(!Ext.getCmp("startDate").hidden?(Ext.get("startDate").dom.value=="开始时间"?"":Ext.get("startDate").dom.value):""),
    				filter_LED_createTime:(!Ext.getCmp("endDate").hidden?(Ext.get("endDate").dom.value=="结束时间"?"":Ext.get("endDate").dom.value):""),
    				privilege:privilege,
					checkItems : '',
					itemsValue : ''
				});
		
		var editbtn = Ext.getCmp('fiInternalRateEdit');
		var deletebtn = Ext.getCmp('fiInternalRateDelete');

		editbtn.setDisabled(true);
		deletebtn.setDisabled(true);

		dataReload();
		
		
	}
function savefiInternalRateRate(_records) {
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
																},{
																	name : "startDepartId",
																	id:'startDepartId',
																	xtype : "hidden"
																}, {
																	name : "endDepartId",
																	id:'endDepartId',
																	xtype : "hidden"
																},
																{xtype : "combo",
																	editable:true,
																	triggerAction : 'all',
																	fieldLabel:'始发部门<font style="color:red;">*</font>',
																	allowBlank : false,
																	queryParam : 'filter_LIKES_departName',
																	forceSelection : true,
																	resizable:true,
																	minChars : 0,
																	pageSize:comboSize,
																	store : departStore,
																	listWidth:245,
																	valueField : 'departId',// value值，与fields对应
																	displayField : 'departName',// 显示值，与fields对应
																    name : 'startDepartName',
																    anchor : '95%',
																    enableKeyEvents:true,
																    listeners : {
																	    blur:function(combo, e){
																         	 Ext.getCmp('startDepartId').setValue(combo.getValue());	
																	    } ,
																	    keyup:function(combo, e){
															             if(e.getKey() == 13){
															                 Ext.getCmp('startDepartId').setValue(combo.getValue());	
															             }
																	    }
																
																}}
																,  {
																	xtype :'numberfield',
																	name:'xbFrommention',
																	fieldLabel:'新邦自提',
																	allowNegative :false,
																	value:0,
																	minValue:0,
																	maxLength :6,
																	anchor : '95%'
																}, {
																	xtype :'numberfield',
																	name:'xbCity',
																	fieldLabel:'新邦市内送货',
																	allowNegative :false,
																	minValue:0,
																	value:0,
																	maxLength :6,
																	anchor : '95%'
																},{
																	xtype :'numberfield',
																	name:'xbSuburbs',
																	fieldLabel:'新邦郊区送货',
																	allowNegative :false,
																	minValue:0,
																	value:0,
																	maxLength :6,
																	anchor : '95%'
																},{
																	xtype :'numberfield',
																	name:'transitFrommention',
																	fieldLabel:'中转自提',
																	allowNegative :false,
																	minValue:0,
																	value:0,
																	maxLength :6,
																	anchor : '95%'
																},{
																	xtype :'numberfield',
																	name:'transitCity',
																	fieldLabel:'中转市内送货',
																	allowNegative :false,
																	minValue:0,
																	value:0,
																	maxLength :6,
																	anchor : '95%'
																},{
																	xtype :'numberfield',
																	name:'transitSuburbs',
																	fieldLabel:'中转郊区送货',
																	allowNegative :false,
																	minValue:0,
																	value:0,
																	maxLength :6,
																	anchor : '95%'
																},{
																	xtype :'numberfield',
																	name:'outFrommention',
																	fieldLabel:'外发自提',
																	allowNegative :false,
																	minValue:0,
																	value:0,
																	maxLength :6,
																	anchor : '95%'
																},{
																	xtype :'numberfield',
																	name:'outDelivery',
																	fieldLabel:'外发送货',
																	allowNegative :false,
																	minValue:0,
																	value:0,
																	maxLength :6,
																	anchor : '95%'
																}]

													}, {
														columnWidth : .5,
														layout : 'form',
														items : [ 
																{xtype : "combo",
																	editable:true,
																	triggerAction : 'all',
																	fieldLabel:'到达部门<font style="color:red;">*</font>',
																	allowBlank : false,
																	queryParam : 'filter_LIKES_departName',
																	forceSelection : true,
																	resizable:true,
																	minChars : 0,
																	pageSize:comboSize,
																	store : departStore,
																	listWidth:245,
																	valueField : 'departId',// value值，与fields对应
																	displayField : 'departName',// 显示值，与fields对应
																    name : 'endDepartName',
																    anchor : '95%',
																    enableKeyEvents:true,listeners : {
																	    blur:function(combo, e){
																         	 Ext.getCmp('endDepartId').setValue(combo.getValue());	
																	    },
																	    keyup:function(combo, e){
															             if(e.getKey() == 13){
															             		 Ext.getCmp('endDepartId').setValue(combo.getValue());	
															             }
																	    }
																	}
																
																}, {
																	xtype :'numberfield',
																	name:'xbFrommentionLowest',
																	fieldLabel:'最低一票',
																	allowNegative :false,
																	minValue:0,
																	value:0,
																	maxLength :6,
																	anchor : '95%'
																},{
																	xtype :'numberfield',
																	name:'xbCityLowest',
																	fieldLabel:'最低一票',
																	allowNegative :false,
																	minValue:0,
																	value:0,
																	maxLength :6,
																	anchor : '95%'
																},{
																	xtype :'numberfield',
																	name:'xbSuburbsLowest',
																	fieldLabel:'最低一票',
																	allowNegative :false,
																	minValue:0,
																	value:0,
																	maxLength :6,
																	anchor : '95%'
																},{
																	xtype :'numberfield',
																	name:'transitFrommentionLowest',
																	fieldLabel:'最低一票',
																	allowNegative :false,
																	minValue:0,
																	value:0,
																	maxLength :6,
																	anchor : '95%'
																},{
																	xtype :'numberfield',
																	name:'transitCityLowest',
																	fieldLabel:'最低一票',
																	allowNegative :false,
																	minValue:0,
																	value:0,
																	maxLength :6,
																	anchor : '95%'
																},{
																	xtype :'numberfield',
																	name:'transitSuburbsLowest',
																	fieldLabel:'最低一票',
																	allowNegative :false,
																	minValue:0,
																	value:0,
																	maxLength :6,
																	anchor : '95%'
																},{
																	xtype :'numberfield',
																	name:'outFrommentionLowest',
																	fieldLabel:'最低一票',
																	allowNegative :false,
																	minValue:0,
																	value:0,
																	maxLength :6,
																	anchor : '95%'
																},{
																	xtype :'numberfield',
																	name:'outDeliveryLowest',
																	fieldLabel:'最低一票',
																	allowNegative :false,
																	minValue:0,
																	value:0,
																	maxLength :6,
																	anchor : '95%'
																},{
																	xtype : 'combo',
																	typeAhead : true,
																	triggerAction : 'all',
																	store : statusStore,
																	editable:false,
																	fieldLabel:'状态',
																	allowBlank : false,
																	forceSelection : true,
																	mode : "local",//从服务器端加载值
																	valueField : 'statusId',//value值，与fields对应
																	displayField : 'statusName',//显示值，与fields对应
																	hiddenName:'status',
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
									
		fiInternalRateRateTitle='添加内部结算协议价信息';
		if(cid!=null){
		fiInternalRateRateTitle='修改内部结算协议价信息';
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
		title : fiInternalRateRateTitle,
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
 	 var _record = fiInternalRateRateGrid.getSelectionModel().getSelections();
 	
        var updatebtn = Ext.getCmp('fiInternalRateEdit');
        var deletebtn = Ext.getCmp('fiInternalRateDelete');
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
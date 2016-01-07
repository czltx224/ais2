//内部结算明细js
var privilege=167;//权限参数
var gridSearchUrl="fi/fiInternalDetailAction!ralaList.action";//内部协议价查询地址
var departUrl='sys/departAction!findAll.action';//部门查询地址
var searchCustomerUrl='sys/customerAction!ralaList.action';//查询客商地址
var dictionaryUrl='sys/dictionaryAction!ralaList.action';//数据字典地址
var searchWidth=100;
var colWidth=90;
var  fields=['id','startDepartId','startDepartName','endDepartId','endDepartName','settlementType','distributionMode',
			 'amount','sourceData','sourceNo','belongsDepartId','belongsDepartName','dno','remark','departId',
			 'departName','createTime','createName','updateTime','updateName','ts','status'];

		//内部交接部门(业务部门)
		departStore = new Ext.data.Store({
        storeId:"departStore",
	        proxy: new Ext.data.HttpProxy({url:sysPath+'/'+departUrl,
	        baseParams:{filter_EQS_isBussinessDepa:1}}),
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
		
	var tbar=[
	 	{text:'<b>导出</b>',iconCls:'sort_down',tooltip : '导出内部结算特殊协议价',handler:function() {
                	parent.exportExl(fiInternalDetailGrid);
        } },'-',
        {text:'<b>打印</b>',iconCls:'printBtn',tooltip : '打印内部结算特殊协议价',handler:function() {
            	alert('功能待实现..');
        } },'-',
        {text:'<b>查询</b>',iconCls:'btnSearch',tooltip : '查询内部结算特殊协议价',handler:function() {
            	searchfiInternalDetail();
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
		             	searchfiInternalDetail();
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
		             	searchfiInternalDetail();
		             }
	 			}
		
		}},'-','配送单号:',{
	 		xtype:'numberfield',
	 		width:searchWidth,
	 		id:'searchDno',
	 		enableKeyEvents:true,
			listeners : {
	 			keyup:function(textField, e){
		             if(e.getKey() == 13){
		             	searchfiInternalDetail();
		             }
	 			},blur:function(){
	 				searchfiInternalDetail();
	 			}
			}
	 	},'-','状态:',{
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
	 			select:function(combo, e){
		           searchfiInternalDetail();
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
    fiInternalDetailGrid = new Ext.grid.GridPanel({
        renderTo: Ext.getBody(),
        id:'fiInternalDetailCenter',
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
        		{header: '收入部门', dataIndex: 'belongsDepartName',sortable : true,width:colWidth},
        		/*{header: '结算类型', dataIndex: 'settlementType',sortable : true,width:colWidth
       				,renderer:function(v){
       					return v==1?'收入':'支出';
								
		        	}
       			},*/
        		{header: '金额', dataIndex: 'amount',sortable : true,width:colWidth,
	        		renderer:function(value, cellMeta, record, rowIndex, columnIndex, store){
	        				var settlementType=record.data['settlementType'];
	        				var amount=value;
	        				if(settlementType==2){
	        					amount=amount*-1;
	        				}
								return  amount	
			        	}
		        	},
        		{header: '状态', dataIndex: 'status',sortable : true,width:colWidth
					,renderer:function(v){
								return v==1?'正常':'<font color="#0000FF">作废</font>';
		        	}
 				},
        		{header: '配送单号', dataIndex: 'dno',sortable : true,width:colWidth},
 				{header: '配送方式', dataIndex: 'distributionMode',sortable : true,width:colWidth},
 				{header: '数据来源', dataIndex: 'sourceData',sortable : true,width:colWidth},
 				{header: '来源单号', dataIndex: 'sourceNo',sortable : true,width:colWidth},
 				{header: '备注', dataIndex: 'remark',sortable : true,width:colWidth},
 				{header: '创建部门', dataIndex: 'departName',sortable : true,width:colWidth},
 				{header: '创建时间', dataIndex: 'createTime',sortable : true,width:colWidth},
 				{header: '创建人', dataIndex: 'createName',sortable : true,width:colWidth},
 				{header: '修改时间', dataIndex: 'updateTime',sortable : true,width:colWidth},
 				{header: '修改人', dataIndex: 'updateName',sortable : true,width:colWidth}
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
		fiInternalDetailGrid.on('rowdblclick',function(grid,index,e){
		var _records = fiInternalDetailGrid.getSelectionModel().getSelections();
				
				if (_records.length ==1) {
					savefiInternalDetail(_records);
				}
			 	
		});
    });
    
    
	
   function searchfiInternalDetail() {
		dateStore.proxy = new Ext.data.HttpProxy({
					method : 'POST',
					url : sysPath
								+ "/"+gridSearchUrl,
						params:{privilege:privilege,limit : pageSize}
				});
			var searchEndDepart = Ext.getCmp('searchEndDepart').getValue();
			var searchStartDepart = Ext.getCmp('searchStartDepart').getValue();
//			var searchCusName = Ext.getCmp('searchCusName').getValue();
			var searchStatus = Ext.getCmp('searchStatus').getValue();
			var searchDno = Ext.getCmp('searchDno').getValue();
			Ext.apply(dateStore.baseParams, {
				filter_EQL_status:searchStatus,
				filter_EQL_endDepartId:searchEndDepart,
				filter_EQL_startDepartId:searchStartDepart,
				filter_EQL_dno:searchDno,
//					filter_EQL_customerId:searchCusName,
				filter_GED_createTime:(!Ext.getCmp("startDate").hidden?(Ext.get("startDate").dom.value=="开始时间"?"":Ext.get("startDate").dom.value):""),
				filter_LED_createTime:(!Ext.getCmp("endDate").hidden?(Ext.get("endDate").dom.value=="结束时间"?"":Ext.get("endDate").dom.value):""),
				privilege:privilege,
				limit : pageSize,
				checkItems : '',
				itemsValue : ''
			});
		var editbtn = Ext.getCmp('fiInternalSpecialRateEdit');
		var deletebtn = Ext.getCmp('fiInternalSpecialRateDelete');

		dataReload();
		
		
	}
function savefiInternalDetail(_records) {
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
																	name : "customerId",
																	id:'customerId',
																	xtype : "hidden"
																},{
																	xtype : 'combo',
																	triggerAction : 'all',
																	store : cusTypeStore,
																	minChars : 0,
																	editable:false,
																	fieldLabel:'客户类型<font style="color:red;">*</font>',
																	allowBlank : false,
																	forceSelection : true,
																	//valueField : 'valuationTypeId',//value值，与fields对应
																	displayField : 'cusTypeName',//显示值，与fields对应
																	name:'customerType',
																	anchor : '95%'
																},{xtype : 'combo',
																	typeAhead : true,
																	queryParam : 'filter_LIKES_cusName',
																	triggerAction : 'all',
																	id:'outCusId',
																	store : customerStore,
																	pageSize : comboSize,
																	allowBlank : false,
																	forceSelection : true,
																	selectOnFocus:true,
																	resizable:true,
																	minChars :0,
																	fieldLabel : '客商名称',
																	mode : "remote",//从服务器端加载值
																	valueField : 'cusId',//value值，与fields对应
																	displayField : 'cusName',//显示值，与fields对应
																	name:'customerName',
																	anchor : '95%',
																	 enableKeyEvents:true,listeners : {
															 		    select:function(combo, e){
														                     Ext.getCmp('customerId').setValue(combo.getValue());
															 		    }
															 	
															 		}
																},{
																	xtype : 'combo',
																	triggerAction : 'all',
																	store : valuationTypeStore,
																	minChars : 0,
																	editable:false,
																	fieldLabel:'计费方式<font style="color:red;">*</font>',
																	allowBlank : false,
																	forceSelection : true,
																	//valueField : 'valuationTypeId',//value值，与fields对应
																	displayField : 'valuationTypeName',//显示值，与fields对应
																	name:'valuationType',
																	id:'valuationTypeId',
																	anchor : '95%',
																	 enableKeyEvents:true,listeners : {
															 		    keyup:function(textField, e){
														                     if(e.getKey() == 13){
														                     	//searchcqCorporateRate();
														                     }
															 		    }
															 	
															 		}
																}]

													}, {
														columnWidth : .5,
														layout : 'form',
														items : [{
																	xtype :'numberfield',
																	name:'rate',
																	fieldLabel:'费率',
																	allowNegative :false,
																	minValue:0,
																	value:0,
																	maxLength :6,
																	anchor : '95%'
																},{
																	xtype :'numberfield',
																	name:'lowest',
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
									
		fiInternalDetailTitle='添加内部结算特殊协议价信息';
		if(cid!=null){
		fiInternalDetailTitle='修改内部结算特殊协议价信息';
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
		title : fiInternalDetailTitle,
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
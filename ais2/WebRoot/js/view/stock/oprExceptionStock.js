//异常库存管理JS
var privilege=115;
var gridSearchUrl="stock/oprExceptionStockAction!ralaList.action";//异常库存查询地址
var toNormalStockUrl='stock/oprExceptionStockAction!toNormalStock.action';//到正常库存地址
var exceptionOutStockUrl ='stock/oprExceptionStockAction!exceptionOutStock.action';//异常出库地址
var searchCusNameUrl='sys/customerAction!list.action';//查询代理公司地址
var dictionaryUrl='sys/dictionaryAction!ralaList.action';//数据字典地址
var defaultWidth=80;
var searchWidth=80; 

var  fields=['id','dno','piece','weight','consignee','cpName','exceptionEnterName','exceptionEnterTime','exceptionOutName','exceptionOutTime','exceptionStatus','departId',
			'departName','consigneeAddr','outStockNo','gowhere','distributionMode','returnType','sourceType','configneeFee','cpFee','paymentCollection','returnObj','sourceNo',
			'addConfigneeFee','addCpFee','outCost','createName','createTime','updateName','updateTime','ts','flightNo','flightDate','flightMainNo','subNo',
			'takeMode','faxCreateDate','goods','faxPiece','cusWeight','faxBulk','goodsStatus','outSender','outLoad','outStockObjName','outRemark'];
	//代理公司
		cpNameStore= new Ext.data.Store({ 
			storeId:"cpNameStore",
			baseParams:{privilege:69},
			proxy:new Ext.data.HttpProxy({url:sysPath+"/"+searchCusNameUrl}),
			reader:new Ext.data.JsonReader({
	                    root:'result', totalProperty:'totalCount'
	        },[
        	{name:'cusName',mapping:'cusName'},
        	{name:'cusId',mapping:'id'}
        	])
		});
		//出库途径110
		returnLoadStore	= new Ext.data.Store({ 
			storeId:"returnLoadStore",
			baseParams:{filter_EQL_basDictionaryId:110,privilege:16},
			proxy:new Ext.data.HttpProxy({url:sysPath+"/"+dictionaryUrl}),
			reader:new Ext.data.JsonReader({
	                    root:'result',
	                    totalProperty:'totalCount'
	        },[
        	{name:'returnLoadId',mapping:'typeCode'},
        	{name:'returnLoadName',mapping:'typeName'}
        	])
		});
		returnForStore=new Ext.data.SimpleStore({
			 autoLoad:true, //此处设置为自动加载
  			  data:[['2','收货人'],['1','代理']],
   			 fields:["returnForId","returnForName"]
		});
		exceptionStore= new Ext.data.SimpleStore({
			 autoLoad:true, //此处设置为自动加载
  			  data:[['','全部'],['0','已作废'],['1','正常'],['2','已入库'],['3','已出库']],
   			 fields:["exceptionId","exceptionName"]
		});
	
	 	outManStore	= new Ext.data.Store({ 
			baseParams:{privilege:23},
			proxy:new Ext.data.HttpProxy({url:sysPath+"/user/userAction!list.action"}),
			reader:new Ext.data.JsonReader({
	                    root:'result',
	                    totalProperty:'totalCount'
	        },[
        	{name:'userName',mapping:'userName'},
        	{name:'userId',mapping:'id'}
        	])
		});
	var tbar=[
            {text:'<b>转正常库存</b>',iconCls:'groupPass',tooltip : '正常库存',handler:function() {
                var overmemo =Ext.getCmp('overmemoCenter');
			    var _records = overmemo.getSelectionModel().getSelections();
	
					if (_records.length < 1) {
						Ext.Msg.alert(alertTitle, "请选择您要操作的行！");
						return false;
					} 
					var ids="";
					for(var i=0;i<_records.length;i++){
						if(_records[i].data.status==0){
							Ext.Msg.alert(alertTitle, "您选择的第几"+(i+1)+"条已经是正常库存！");
							return;
						}
						 ids=ids+_records[i].data.id+',';
					}
					Ext.Msg.confirm(alertTitle, "确定要将这"+_records.length+"条异常库存转为正常库存吗？", function(
									btnYes) {
								if (btnYes == 'yes' || btnYes == 'ok'
										|| btnYes == true) {
									savestockChange(ids);
								}
							});
            } },'-',
            {text:'<b>异常出库</b>',iconCls:'table',tooltip : '异常出库',handler:function() {
	            var grid =Ext.getCmp('overmemoCenter');
				var _records = grid.getSelectionModel().getSelections();
				if(_records.length<1){
					Ext.Msg.alert(alertTitle, '请选择你要出库的记录！');
					return;
				}
				else if(_records.length>1){
					Ext.Msg.alert(alertTitle, '一次只允许出库一条记录！');
					return;
				}else if(_records[0].data.piece<1){
					Ext.Msg.alert(alertTitle, '这票货没有库存，无法出库！');
					return;
				}
				outStock(_records);
            } },'-',
	 	{text:'<b>导出</b>',iconCls:'sort_down',tooltip : '导出数据',
	 		handler:function(){
	 					parent.exportExl(overmemoGrid);
	 		}
	 	}
	 		,'-',
	{xtype:'textfield',blankText:'查询数据',id:'searchContent', enableKeyEvents:true,listeners : {
	 		
	 		keyup:function(textField, e){

                     if(e.getKey() == 13){

                     	searchExceptionStock();

                     }
	 		}
	 	}},'-',{ 
	 	
    					xtype : "combo",
    					width : 60,
    					triggerAction : 'all',
    					id:'searchSelectBox',
    					model : 'local',
    					hiddenId : 'checkItems',
    					hiddenName : 'checkItems',
    					name : 'checkItemstext',
    					store : [['LIKES_goods', '品名'],
    							['LIKES_consigneeAddr','地址'],
    							['LIKES_flightNo','航班号'],
    							['LIKES_subNo','分单号'],
    							['LIKES_flightMainNo','主单号']],
    							
    					emptyText : '选择查询方式',
    					editable : false,
    					forceSelection : true,
    					listeners : {
    						'select' : function(combo, record, index) {
								if(Ext.getCmp("searchContent").getValue().length>0){
									searchExceptionStock();
								}
    						}
    					}
    					
    		},'-','异常库存状态:',{
    			xtype : "combo",
				width : searchWidth+20,
				editable:true,
				triggerAction : 'all',
				store :exceptionStore,
				mode : "local",// 从本地载值
				valueField : 'exceptionId',// value值，与fields对应
				displayField : 'exceptionName',// 显示值，与fields对应
			    id:'searchisException', 
				editable : true,
				enableKeyEvents:true,
				listeners : {
					'select' : function(combo, record, index) {
						if(combo.getValue().length>0){
							searchExceptionStock();
						}
					}
				}
    		}
	 	];
	 	
	 	var queryTbar=new Ext.Toolbar([
	 	'代理公司:',{xtype : "combo",
				width : searchWidth+20,
				editable:true,
//				triggerAction : 'all',
//				typeAhead : true,
				queryParam : 'filter_LIKES_cusName',
				pageSize : comboSize,
				resizable:true,
				minChars : 0,
				store : cpNameStore,
				mode : "remote",// 从本地载值
				valueField : 'cusName',// value值，与fields对应
				displayField : 'cusName',// 显示值，与fields对应
			    name : 'cpName',
			    id:'searchCpName', 
			    enableKeyEvents:true,listeners : {
	 		    keyup:function(textField, e){
                     if(e.getKey() == 13){
                     	searchcqCorporateRate();
                     }
	 		}
	 	
	 	}},	'-','收货人:',{xtype:'textfield',id:'searchConsignee',width : searchWidth, enableKeyEvents:true,listeners : {
	 		keyup:function(textField, e){
                     if(e.getKey() == 13){
                     	searchExceptionStock();
                     }
	 		}
	 	}},'-','去向:',{xtype:'textfield',id:'searchGowhere',width : searchWidth,enableKeyEvents:true,listeners : {
	 		keyup:function(textField, e){
                     if(e.getKey() == 13){
                     	searchExceptionStock();
                     }
	 		}
	 	}},'-','配送单号:',{xtype:'textfield',id:'searchDno', width : searchWidth,enableKeyEvents:true,listeners : {
	 		keyup:function(textField, e){
                     if(e.getKey() == 13){
                     	searchExceptionStock();
                     }
	 		}
	 	}},'-',{text : '<b>查询</b>',id : 'btn',iconCls : 'btnSearch',
    					handler : searchExceptionStock
    				}	
	 	]);	


	 	
Ext.onReady(function() {
   	dateStore = new Ext.data.Store({
        storeId:"dateStore",
        proxy: new Ext.data.HttpProxy({url : sysPath+ "/"+gridSearchUrl}),
        baseParams:{privilege:privilege,limit : pageSize},
        reader: new Ext.data.JsonReader({
            root: 'result', totalProperty: 'totalCount'
        }, fields)
    });
   
   var sm = new Ext.grid.CheckboxSelectionModel({});
	var rowNum = new Ext.grid.RowNumberer({
        header:'序号', width:30
    });
    overmemoGrid = new Ext.grid.GridPanel({
        renderTo: Ext.getBody(),
        id:'overmemoCenter',
        height:Ext.lib.Dom.getViewHeight()-1,		
        width:Ext.lib.Dom.getViewWidth()-1,
        viewConfig : {
			columnsText : "显示的列",
			sortAscText : "升序",
			sortDescText : "降序"
			//forceFit : true
		},
		autoScroll : true,
		//autoSizeColumns: true,
		frame : false,
		loadMask : true,
		stripeRows : true,
        sm:sm,
        cm:new Ext.grid.ColumnModel([rowNum,sm,
       			{header: '异常库存ID', dataIndex: 'id',width:defaultWidth,sortable : true,hidden:true},
       			{header: '配送单号', dataIndex: 'dno',width:defaultWidth,sortable : true},
       			{header: '品名', dataIndex: 'goods',width:defaultWidth,sortable : true},
 				{header: '到付提送费', dataIndex: 'configneeFee',width:defaultWidth,sortable : true},
 				{header: '预付提送费', dataIndex: 'cpFee',width:defaultWidth,sortable : true},
       			{header: '录单件数', dataIndex: 'faxPiece',width:defaultWidth,sortable : true},
       			{header: '库存件数', dataIndex: 'piece',width:defaultWidth,sortable : true},
 				{header: '录单重量', dataIndex: 'cusWeight',width:defaultWidth,sortable : true},
 				{header: '折合重量', dataIndex: 'weight',width:defaultWidth,sortable : true},
 				{header: '体积', dataIndex: 'faxBulk',width:defaultWidth,sortable : true},
       			{header: '货物状态', dataIndex: 'goodsStatus',width:defaultWidth,sortable : true},
       			{header: '配送方式', dataIndex: 'distributionMode',width:defaultWidth,sortable : true},
       			{header: '提送方式', dataIndex: 'takeMode',width:defaultWidth,sortable : true},
       			{header: '代理公司', dataIndex: 'cpName',width:defaultWidth,sortable : true},
 				{header: '收货人', dataIndex: 'consignee',width:defaultWidth,sortable : true},
 				{header: '收货人地址', dataIndex: 'consigneeAddr',width:defaultWidth,sortable : true},
       			{header: '去向', dataIndex: 'gowhere',width:defaultWidth,sortable : true},
       			{header: '主单号', dataIndex: 'flightMainNo',width:defaultWidth,sortable : true},
       			{header: '分单号', dataIndex: 'subNo',width:defaultWidth,sortable : true},
       			{header: '航班号', dataIndex: 'flightNo',width:defaultWidth,sortable : true},
 				{header: '异常状态', dataIndex: 'exceptionStatus',width:defaultWidth,sortable : true
 					,renderer:function(v){
						if(v==0){
							return '已作废';
						}else if(v==1){
							return '正常';
						}else if(v==2){
							return '已入库';
						}else if(v==3){
							return '已出库';
						}
	        		}
 				},
       			{header: '传真时间', dataIndex: 'faxCreateDate',width:defaultWidth,sortable : true},
 				{header: '入库时间', dataIndex: 'exceptionEnterTime',width:defaultWidth,sortable : true},
 				{header: '入库人', dataIndex: 'exceptionEnterName',width:defaultWidth,sortable : true},
 				{header: '出库人', dataIndex: 'exceptionOutName',width:defaultWidth,sortable : true},
 				{header: '出库时间', dataIndex: 'exceptionOutTime',width:defaultWidth,sortable : true},
 				{header: '出库单号', dataIndex: 'outStockNo',width:defaultWidth,sortable : true},
 				{header: '出库对象', dataIndex: 'outStockObj',width:defaultWidth,sortable : true},
 				{header: '出库成本', dataIndex: 'outCost',width:defaultWidth,sortable : true},
 				{header: '返货类型', dataIndex: 'returnType',width:defaultWidth,sortable : true},
 				{header: '来源类型', dataIndex: 'sourceType',width:defaultWidth,sortable : true},
 				{header: '来源单号', dataIndex: 'sourceNo',width:defaultWidth,sortable : true},
 				{header: '出库成本', dataIndex: 'outCost',width:defaultWidth,sortable : true},
 				{header: '送货员', dataIndex: 'outSender',width:defaultWidth,sortable : true},
 				{header: '出库途径', dataIndex: 'outLoad',width:defaultWidth,sortable : true},
 				{header: '出库备注', dataIndex: 'outRemark',width:defaultWidth,sortable : true},
 				{header: '追加到付提送费', dataIndex: 'addConfigneeFee',width:defaultWidth,sortable : true},
 				{header: '追加预付提送费', dataIndex: 'addCpFee',width:defaultWidth,sortable : true}
       			
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
            store: dateStore, 
            displayInfo: true,
            displayMsg: '显示第 {0} 条到 {1} 条记录，共 {2} 条', 
            emptyMsg: "没有记录信息显示"
        })
    });
   // searchExceptionStock();
  });
    
    
	
   function searchExceptionStock() {
		dateStore.baseParams={
			start:0,
			privilege:privilege,
			limit : pageSize,
			filter_EQL_departId:bussDepart
		};
				var searchCpName = Ext.getCmp('searchCpName').getRawValue();
				var searchConsignee = Ext.getCmp('searchConsignee').getValue();
				var searchGowhere = Ext.getCmp('searchGowhere').getValue();
				var searchDno = Ext.get('searchDno').dom.value;
				var searchisException = Ext.getCmp('searchisException').getValue();
				
				if(null!=searchDno && searchDno.length>1){
					 Ext.apply(dateStore.baseParams, {
					 	filter_EQL_dno:searchDno
					 });
				}else{
		 			 Ext.apply(dateStore.baseParams, {
	    					filter_EQS_cpName:searchCpName,
	    					filter_LIKES_consignee:searchConsignee,
	    					filter_LIKES_gowhere:searchGowhere,
	    					filter_EQL_exceptionStatus:searchisException,
							checkItems : Ext.get("checkItems").dom.value,
							itemsValue : Ext.get("searchContent").dom.value
	    			});
	 			}
		dataReload();
	}

function savestockChange(ids) {
					var form = new Ext.form.FormPanel({
								labelAlign : 'left',
								frame : true,
								url:sysPath+'/'+toNormalStockUrl,
								baseParams:{
				    						exceptionStockIds : ids},
								bodyStyle : 'padding:5px 5px 0',
								width : 400,
								defaults : {allowBlank : false},  
								reader : new Ext.data.JsonReader({
			            root: 'result', totalProperty: 'totalCount'
			        }, fields),
					labelAlign : "right",
				
						items : [{
									layout : 'form',
									items : [{
											xtype : 'textarea',
											labelAlign : 'left',
											allowBlank : false,
											fieldLabel : '备注<font color="red"><B>*</B></font>',
											name : 'remark',
											height:40,
											maxLength:200,
											anchor : '95%'
										}]
											
								}]
							});
		
		var win = new Ext.Window({
		title : '库存转化备注填写',
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
								+ '/'+toNormalStockUrl,
								params:{privilege:privilege,limit : pageSize},
						waitMsg : '正在保存数据...',
						success : function(form, action) {
							win.hide(), Ext.Msg.alert(alertTitle,"转化成功！", function() {
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
var sty = 'background:gray';
//异常出库
function outStock (_records){
	var dno=_records[0].data.dno;
	var form = new Ext.form.FormPanel({
		labelAlign : 'right',
		frame : true,
		url:sysPath+'/'+gridSearchUrl,
		baseParams:{
			filter_EQL_dno:dno,
			privilege:privilege,
			limit : pageSize},
		bodyStyle : 'padding:5px 5px 0',
		width : 500,
		defaults : {enableKeyEvents:true},  
		reader : new Ext.data.JsonReader({
	    root: 'result', totalProperty: 'totalCount'
		}, fields),
	items : [{
		layout : 'column',
			items : [{
						columnWidth : .5,
						layout : 'form',
						items : [{
									name : "ts",
									xtype : "hidden"
								},{
									name : "id",
									xtype : "hidden"
								},{
									xtype : 'numberfield',
									fieldLabel : '配送单号',
									allowBlank : false,
									style:sty,
									readOnly:true,
									blankText : "配送单号不能为空！",
									maxLength :10,
									id:'outDno',
									name : 'dno',
									anchor : '95%'
							       },{
									xtype : 'textfield',
									readOnly:true,
									style:sty,
									fieldLabel : '收货人',
									name:'consignee',
									id:'consigneeId',
									anchor : '95%'
							       },{
									xtype : 'textfield',
									readOnly:true,
									style:sty,
									fieldLabel : '收货人地址',
									name:'consigneeAddr',
									id:'addrId',
									anchor : '95%'
							       },{
									xtype : 'textfield',
									readOnly:true,
									style:sty,
									fieldLabel : '配送方式',
									name:'distributionMode',
									id:'distributionModeId',
									anchor : '95%'
							       },{
									xtype : 'textfield',
									readOnly:true,
									style:sty,
									fieldLabel : '提送方式',
									name:'takeMode',
									id:'takeModeId',
									anchor : '95%'
							       },{
									xtype : 'textfield',
									readOnly:true,
									style:sty,
									fieldLabel : '代理公司',
									name:'cpName',
									id:'cpNameId',
									anchor : '95%'
							       },{
									xtype : 'textfield',
									readOnly:true,
									style:sty,
									fieldLabel : '去向',
									name:'gowhere',
									id:'gowhereId',
									anchor : '95%'
							       },{
									xtype : 'combo',
									triggerAction : 'all',
									store : returnForStore,
									forceSelection : true,
									fieldLabel : '出库对象<font color=red>*</font>',
									mode : "local",// 从本地载值
									editable : true,
									allowBlank : false,
									minChars : 0,
									valueField : 'returnForId',//value值，与fields对应
									displayField : 'returnForName',//显示值，与fields对应
									name:'vo.outStockObj',
									id:'outStockObjId',
									anchor : '95%',
									listeners:{
										'select':function(combo,e){
											if(combo.getValue()==1){
												Ext.getCmp('outStockObjName').setValue(_records[0].data.cpName);
												Ext.getCmp('outCostId').focus(true,true);
												Ext.getCmp('outSender').setValue('');
												Ext.getDom('outSender').readOnly=true;
											}else if(combo.getValue()==2){
												Ext.getCmp('outStockObjName').setValue(_records[0].data.consignee);
												Ext.getDom('outSender').readOnly=false;
												Ext.getDom('outSender').allowBlank=false;
												Ext.getCmp('outCostId').focus(true,true);
											}
										}
									}
							       },{
									xtype : 'textfield',
									fieldLabel : '出库对象名称<font color=red>*</font>',
									allowBlank : false,
									name : 'vo.outStockObjName',
									id:'outStockObjName',
									anchor : '95%'
							       },{
									xtype : 'numberfield',
									fieldLabel : '出库成本<font color=red>*</font>',
									maxLength :10,
									allowBlank : false,
									name : 'vo.outCost',
									id : 'outCostId',
									anchor : '95%'
							       }]

						},{
							columnWidth : .5,
							layout : 'form',
							items : [{
									xtype : 'numberfield',
									readOnly:true,
									style:sty,
									fieldLabel : '重量',
									name:'weight',
									anchor : '95%'
							       },{
									xtype : 'numberfield',
									readOnly:true,
									style:sty,
									//disabled:true,
									fieldLabel : '体积',
									name:'faxBulk',
									anchor : '95%'
							       },{
									xtype : 'numberfield',
									maxLength :10,
									style:sty,
									fieldLabel : '件数',
//									ctCls :'backcolor:"red"',
									readOnly:true,
//									disabled:true,
									name : 'piece',
									anchor : '95%'
							       },{
										xtype : 'numberfield',
										fieldLabel : '到付提送费',
										readOnly:true,
										style:sty,
										name : 'configneeFee',
										anchor : '95%'
										
								       },{
										xtype : 'numberfield',
										fieldLabel : '预付提送费',
										maxLength :10,
										readOnly:true,
										style:sty,
										name : 'cpFee',
										anchor : '95%'
								       },{
										xtype : 'numberfield',
										fieldLabel : '代收货款',
										maxLength :10,
										style:sty,
										readOnly:true,
//										disabled:true,
										name : 'paymentCollection',
										anchor : '95%'
								       },{
										xtype : 'numberfield',
										fieldLabel : '追加到付提送费',
										maxLength :10,
//										readOnly:true,
//										disabled:true,
										name : 'vo.addConfigneeFee',
										anchor : '95%'
								       },{
										xtype : 'numberfield',
										fieldLabel : '追加预付提送费',
										maxLength :10,
//										readOnly:true,
//										disabled:true,
										name : 'vo.addCpFee',
										anchor : '95%'
								       },{
										xtype : 'combo',
										fieldLabel : '出库人',
										triggerAction : 'all',
										store : outManStore,//userName
										queryParam : 'filter_LIKES_userName',
										pageSize : comboSize,
										minChars : 0,
										//forceSelection : true,
										mode : "remote",// 从服务器载值
										editable : true,
										minChars : 0,
										id:'outSenderId',
										valueField : 'userId',//value值，与fields对应
										displayField : 'userName',//显示值，与fields对应
										name:'vo.outSender',
										anchor : '95%'
								       },{
										xtype : 'combo',
										triggerAction : 'all',
										store : returnLoadStore,
										forceSelection : true,
										fieldLabel : '出库途径',
										mode : "remote",// 从服务器载值
										editable : true,
										minChars : 0,
										valueField : 'returnLoadId',//value值，与fields对应
										displayField : 'returnLoadName',//显示值，与fields对应
										name:'vo.outLoad',
										id:'outLoad',
										anchor : '95%',
										listeners:{
										'select':function(combo,e){
												Ext.getCmp('outStockNoId').focus(true,true);
										}
									}
								       },{
										xtype : 'numberfield',
										fieldLabel : '出库单号',
										maxLength :20,
										name : 'vo.outStockNo',
										id:'outStockNoId',
										anchor : '95%'
								       }
									]
						}]
						
						
			},{
				xtype : 'textarea',
				height :50,
				fieldLabel : '出库备注',
				name : 'vo.outRemark',
				id:'remark',
				anchor : '95%'
			}
			]
		});
		form.load({
				waitMsg : '正在载入数据...',
    			success : function(_form, action) {
		   			Ext.getCmp('returnFor').focus();
    			},
    			failure : function(_form, action) {
    				Ext.MessageBox.alert(alertTitle, '载入失败');
    			}
		});
		var win = new Ext.Window({
		title : '异常出库',
		width : 500,
		closeAction : 'hide',
		plain : true,
		modal : true,
		items : form,
		buttonAlign : "center",
		buttons : [{
			text : "保存",
			iconCls : 'groupSave',
			id:'saveBtn',
			handler : function() {
				if (form.getForm().isValid()) {
					//this.disabled = true;//只能点击一次
					form.getForm().submit({
						url : sysPath
								+ '/'+exceptionOutStockUrl,
						waitMsg : '正在保存数据...',
						success : function(form, action) {
							Ext.Msg.alert(alertTitle, "异常出库成功！",function(){	
								win.hide(),
								dateStore.reload();
								
							}
							);
						},
						failure : function(form, action) {
							if (action.faliureType == Ext.form.Action.SERVER_INVALID) {
								Ext.Msg.alert(alertTitle, "服务器连接异常，请联系管理员！");
							} else {
								Ext.Msg.alert(alertTitle, "异常出库失败!");
							}
						}
					});
				}
			}
		}, {
		
			text : "重置",
			iconCls : 'refresh',
			handler : function() {
				if(dno==null){
					form.getForm().reset();
				}
				if(dno!=null){
					form.getForm().reset();
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
		},  {
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
				limit:pageSize
				}
			})
		}
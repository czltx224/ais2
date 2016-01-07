//返货管理JS
var privilege=88;//权限参数
var gridSearchUrl="instock/oprReturnGoodsAction!ralaList.action";//查询地址
var gridTotalUrl="instock/oprReturnGoodsAction!findTotalCount.action";//统计地址
var saveRegistrationUrl="instock/oprReturnGoodsAction!saveRegistration.action";//返货登记地址
var saveEnterStockUrl="instock/oprReturnGoodsAction!saveEnterStock.action";//返货入库地址
var saveOutStockUrl="instock/oprReturnOutAction!save.action";//返货出库地址
var saveAuditReturnGoodsUrl="instock/oprReturnGoodsAction!auditReturnGoods.action";//返货登记审核地址
var dictionaryUrl='sys/dictionaryAction!ralaList.action';//数据字典地址
var searchCusNameUrl='sys/customerAction!list.action';//查询代理公司地址
var gridSearchFaxUrl='fax/oprFaxInAction!list.action';
var allowRegistration='instock/oprReturnGoodsAction!allowRegistration.action';
var judgeUrl = 'fi/fiOutCostAction!returnCheck.action';
var searchWidth=60;
var defaultWidth=80;

		//返货途径110
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
  		//返货类型33
		returnGoodsTypeStore	= new Ext.data.Store({ 
			storeId:"returnGoodsTypeStore",
			baseParams:{filter_EQL_basDictionaryId:33,privilege:16},
			proxy:new Ext.data.HttpProxy({url:sysPath+"/"+dictionaryUrl}),
			reader:new Ext.data.JsonReader({
	                    root:'result',
	                    totalProperty:'totalCount'
	        },[
        	{name:'returnGoodsTypeId',mapping:'typeCode'},
        	{name:'returnGoodsTypeName',mapping:'typeName'}
        	])
		});
		 //送货类型4
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
		
		//代理公司
		cpNameStore= new Ext.data.Store({ 
			storeId:"cpNameStore",
			proxy:new Ext.data.HttpProxy({url:sysPath+"/"+searchCusNameUrl}),
			reader:new Ext.data.JsonReader({
	                    root:'result', totalProperty:'totalCount'
	        },[
        	{name:'cusName',mapping:'cusName'},
        	{name:'cusId',mapping:'id'}
        	])
		});
		isNotExceptionStore=new Ext.data.SimpleStore({
			 autoLoad:true, //此处设置为自动加载
  			  data:[['','-请选择-'],['2','是'],['1','否']],
   			 fields:["isNotExceptionId","isNotExceptionName"]
		});
		
		
		dutyPartyStore=new Ext.data.SimpleStore({
			 autoLoad:true, //此处设置为自动加载
  			  data:[['1','新邦'],['2','收货人'],['3','代理']],
   			 fields:["dutyPartyId","dutyPartyName"]
		});
		
		returnForStore=new Ext.data.SimpleStore({
			 autoLoad:true, //此处设置为自动加载
  			  data:[['2','收货人'],['1','代理']],
   			 fields:["returnForId","returnForName"]
		});
		
		//返货状态
		returnStatusStore=new Ext.data.SimpleStore({
			 autoLoad:true, //此处设置为自动加载
  			  data:[['1','已返货'],['2','已入库'],['3','已出库']],
   			 fields:["statusId","statusName"]
		});
		//返货审核状态
		returnAuditStore=new Ext.data.SimpleStore({
			 autoLoad:true, //此处设置为自动加载
  			  data:[['1','已审核'],['0','未审核']],
   			 fields:["statusId","statusName"]
		});

var  fields=['id','dno','returnNum','returnType','outNo','dutyParty','consigneeFee','paymentCollection','returnComment',
				'returnCost','returnDepartName','returnDepart','createName','createTime','updateName','updateTime','ts',
				'cpName','flightNo','consignee','consigneeTel','addr','cqWeight','realGoWhere','cusValueAddFee',
				'distributionMode','outType','status','bulk','takeMode','auditStatus','faxPiece'];
var summary = new Ext.ux.grid.GridSummary();

var totalfields=['totalNum','totalPiece','totalcost','totalConsigneefee','totalPayment'];
	 
	var tbar=[{text:'<b>导出</b>',iconCls:'sort_down',tooltip : '导出',handler:function() {
					parent.exportExl(returnGoodsGrid);
            } },'-',{text:'<b>返货登记</b>',iconCls:'table',tooltip : '返货登记',handler:function() {
            var returnGoods =Ext.getCmp('returnGoodsCenter');
			var _records = returnGoods.getSelectionModel().getSelections();
					//alert('登记');
			returnGoodsRegistration(_records);
            } },'-',{text:'<b>返货审核</b>',iconCls:'groupPass',
            	handler:function() {
	               	var returnGoods =Ext.getCmp('returnGoodsCenter');
					var _records = returnGoods.getSelectionModel().getSelections();
					if(_records.length<1){
						Ext.Msg.alert(alertTitle, '请选择你要进行审核的记录！');
						return;
					}
					else if(_records.length>1){
						Ext.Msg.alert(alertTitle, '一次只允许审核一条返货记录！');
						return;
					}
					else if(_records[0].data.auditStatus==1){
						Ext.Msg.alert(alertTitle, '该返货已经审核！');
						return;
					}
//					else if(_records[0].data.returnCost<1){
//						Ext.Msg.alert(alertTitle, '该返货没有返货成本！');
//						return;
//					}
					returnGoodsAudit(_records[0]);
           	 	} 
           },'-',
            {text:'<b>返货入库</b>',iconCls:'table',tooltip : '返货入库',handler:function() {
	            var returnGoods =Ext.getCmp('returnGoodsCenter');
				var _records = returnGoods.getSelectionModel().getSelections();
				
				if(_records.length<1){
					Ext.Msg.alert(alertTitle, '请选择你要入库的记录！');
					return;
				}
				for(var i=0;i<_records.length;i++){
					if(_records[i].data.status!=1){
						Ext.Msg.alert(alertTitle, '您选择的第'+(i+1)+'条返货数据不能入库！');
						return;
					}else if(_records[i].data.auditStatus!=1){
						Ext.Msg.alert(alertTitle, '该返货还没有审核！');
						return;
					}
				}
				
				
				returnGoodsEnterStock(_records);
            } }
            ,{text : '<b>查询</b>',id : 'btn',iconCls : 'btnSearch',
    					handler : searchreturnGoods
    		}
	 	];
	 	var threeBar = new Ext.Toolbar([
		{xtype:'textfield',id:'searchContent',
			width:searchWidth,
			enableKeyEvents:true,
			hidden:true,
			listeners : {
	 		keyup:function(textField, e){
                if(e.getKey() == 13){
                	searchreturnGoods();
                }
	 		}
	 	}},{
    			xtype : 'datefield',
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
    	}, '&nbsp;&nbsp;', {
    		xtype : 'datefield',
    		id : 'endDate',
    		format : 'Y-m-d',
    		emptyText : "结束时间",
    		value:new Date(),
    		hidden : false,
    		width : 100,
    		disabled : false,
    		anchor : '95%'
    	},'-',{ 
	 	
    					xtype : "combo",
    					width : 100,
    					triggerAction : 'all',
    					//id:'checkItems',
    					model : 'local',
    					hiddenId : 'checkItems',
    					hiddenName : 'checkItems',
    					name : 'checkItemstext',
    					store : [['','查询全部'],
    							['LIKES_outNo','实配单号'],
    							['LIKES_consignee','收货人'],
    							['LIKES_addr','收货人地址'],
    							['LIKES_consigneeTel','收货人电话'],
    							['LIKES_realGoWhere','去向'],
    							['EQD_createTime','返货登记日期'],
    							['EQD_updateTime','最后修改日期']
    							],
    							
    					editable : false,
    					forceSelection : true,
    					listeners : {
    						keyup:function(textField, e){
			                    if(e.getKey() == 13){
			                     	searchreturnGoods();
			                    }
			 				},
    						'select' : function(combo, record, index) {
								
    							if (combo.getValue() == 'EQD_updateTime' || combo.getValue() == 'EQD_createTime') {
    								Ext.getCmp("startDate").enable();
    								Ext.getCmp("startDate").show();

    								Ext.getCmp("endDate").enable();
    								Ext.getCmp("endDate").show();
    								
    								Ext.getCmp("searchContent").disable();
    								Ext.getCmp("searchContent").hide();
    								Ext.getCmp("searchContent").setValue("");
    							}else{
    								Ext.getCmp("startDate").hide();
    								Ext.getCmp("startDate").disable();
    								Ext.getCmp("endDate").hide();
    								Ext.getCmp("endDate").disable();
    								
    						      	Ext.getCmp("searchContent").enable();
    								Ext.getCmp("searchContent").show();
    								
    							}
    							
    							if(Ext.getCmp("searchContent").getValue().length>0){
									searchreturnGoods();
								}else if (combo.getValue().length==0){
									searchreturnGoods();
								}
    							
    						},afterRender: function(combo, record, index) {
					       　　	combo.setValue('EQD_createTime');
							}
    					}
    					
    				}
    				,'-','返货状态:',{
    				xtype : "combo",
					width : searchWidth+20,
					triggerAction : 'all',
					resizable:true,
					store : returnStatusStore,
					mode : "local",// 从本地载值
					valueField : 'statusId',// value值，与fields对应
					displayField : 'statusName',// 显示值，与fields对应
				    name : 'statusName',
				    id:'searchstatus', 
				    enableKeyEvents:true,
				    listeners : {
		 		    keyup:function(textField, e){
	                     if(e.getKey() == 13){
                     		searchreturnGoods();
			             }
				 		}
				    }
    				} ,'-','审核状态:',{
    				xtype : "combo",
					width : searchWidth+20,
					triggerAction : 'all',
					resizable:true,
					store : returnAuditStore,
					mode : "local",// 从本地载值
					valueField : 'statusId',// value值，与fields对应
					displayField : 'statusName',// 显示值，与fields对应
				    name : 'statusName',
				    id:'searchAuditstatus', 
				    enableKeyEvents:true,
				    listeners : {
		 		    keyup:function(textField, e){
	                     if(e.getKey() == 13){
                     		searchreturnGoods();
			             }
				 		}
				    }
    				} 	
	 	])
	 	var queryTbar=new Ext.Toolbar([
	 	
    		'配送单号:',{xtype:'numberfield',id:'searchDno',
    		width:searchWidth+20, 
    		enableKeyEvents:true,
    		listeners : {
	 		keyup:function(textField, e){
                     if(e.getKey() == 13){
                     	searchreturnGoods();
                     }
	 		}
	 	}},	'-',
	 	'代理公司:',{xtype : "combo",
				width : searchWidth+20,
				editable:true,
				triggerAction : 'all',
				//typeAhead : true,
				queryParam : 'filter_LIKES_cusName',
				pageSize : comboSize,
				forceSelection : true,
				resizable:true,
				minChars : 0,
				store : cpNameStore,
				mode : "remote",// 从本地载值
				valueField : 'cusName',// value值，与fields对应
				displayField : 'cusName',// 显示值，与fields对应
			    name : 'cpName',
			    id:'searchCpName', 
			    enableKeyEvents:true,
			    listeners : {
	 		    keyup:function(textField, e){
                     if(e.getKey() == 13){
                     	searchreturnGoods();
                     }
	 		}
	 	
	 	}},	'-',
	 	'航班号:',{xtype:'textfield',id:'searchFlightNo',width:searchWidth, enableKeyEvents:true,listeners : {
	 		keyup:function(textField, e){
                     if(e.getKey() == 13){
                     	searchreturnGoods();
                     }
	 		}
	 	}},	'-',
	 	'返货类型:',{xtype : "combo",
				width : searchWidth,
				editable:true,
				triggerAction : 'all',
				store : returnGoodsTypeStore,
				mode : "remote",// 从本地载值
				valueField : 'returnGoodsTypeId',// value值，与fields对应
				displayField : 'returnGoodsTypeName',// 显示值，与fields对应
			    name : 'returnGoodsType',
			    id:'searchreturnGoodsType', 
			    enableKeyEvents:true,listeners : {
	 		    keyup:function(textField, e){
                     if(e.getKey() == 13){
                     	searchreturnGoods();
                     }
	 		}
	 	
	 	}},'-','送货类型:',{xtype : "combo",
				width : searchWidth,
				editable:true,
				triggerAction : 'all',
				store : distributionModeStore,
				mode : "remote",// 从本地载值
				valueField : 'distributionModeId',// value值，与fields对应
				displayField : 'distributionModeName',// 显示值，与fields对应
			    name : 'distributionMode',
			    id:'searchdistributionMode', 
			    enableKeyEvents:true,listeners : {
	 		    keyup:function(combo, e){
                     if(e.getKey() == 13){
                     	searchreturnGoods();
                     }
	 		}
	 	
	 	}}
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
    
    totalStore = new Ext.data.Store({
        storeId:"totalStore",
        proxy: new Ext.data.HttpProxy({url : sysPath+ "/"+gridTotalUrl}),
        baseParams:{privilege:privilege,limit : pageSize},
        reader: new Ext.data.JsonReader({
            root: 'result', totalProperty: 'totalCount'
        }, totalfields)
    });
   
   var sm = new Ext.grid.CheckboxSelectionModel();
   var rowNum = new Ext.grid.RowNumberer({
        header:'序号', width:20
    });
    returnGoodsGrid = new Ext.grid.GridPanel({
        renderTo: Ext.getBody(),
        id:'returnGoodsCenter',
        height:Ext.lib.Dom.getViewHeight(),		
        width:Ext.lib.Dom.getViewWidth(),
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
        plugins : [summary], // 合计
        loadMask : {
			msg : '正在加载表格数据,请稍等...'
		},
        cm:new Ext.grid.ColumnModel([rowNum,sm,
        		
       			{header: '配送单号', dataIndex: 'dno',width:defaultWidth,sortable : true},
       			{header: '实配单号', dataIndex: 'outNo',width:defaultWidth,sortable : true},
       			{header: '航班号', dataIndex: 'flightNo',width:defaultWidth,sortable : true},
       			{header: '回单序号', dataIndex: 'id',width:defaultWidth,sortable : true,hidden:true},
       			{header: '发货代理', dataIndex: 'cpName',width:defaultWidth,sortable : true},
       			{header: '返货件数', dataIndex: 'returnNum',width:defaultWidth,sortable : true},
       			{header: '录单件数', dataIndex: 'faxPiece',width:defaultWidth,sortable : true},
       			{header: '录单重量', dataIndex: 'cqWeight',width:defaultWidth,sortable : true},
       			{header: '到付提送费', dataIndex: 'consigneeFee',width:defaultWidth,sortable : true},
 				{header: '代收货款', dataIndex: 'paymentCollection',width:defaultWidth,sortable : true},
 				{header: '到付增值服务费', dataIndex: 'cusValueAddFee',width:defaultWidth,sortable : true},
 				{header: '返货成本', dataIndex: 'returnCost',width:defaultWidth,sortable : true},
 				{header: '状态', dataIndex: 'status',width:defaultWidth,sortable : true
 					,renderer:function(v){
						return v==1?'已返货':(v==2?'已入库':(v==3?'已出库':'删除'));
	        		}
 				},
 				{header: '审核状态', dataIndex: 'auditStatus',width:defaultWidth,sortable : true
 					,renderer:function(v){
						return v==1?'已审核':'未审核';
	        		}
 				},
 				{header: '责任方', dataIndex: 'dutyParty',width:defaultWidth,sortable : true},
       			{header: '收货人', dataIndex: 'consignee',width:defaultWidth,sortable : true},
       			{header: '收货人地址', dataIndex: 'addr',width:defaultWidth,sortable : true},
 				{header: '收货人电话', dataIndex: 'consigneeTel',width:defaultWidth,sortable : true},
       			{header: '去向', dataIndex: 'realGoWhere',width:defaultWidth,sortable : true},
       			{header: '返货方式', dataIndex: 'returnType',width:defaultWidth,sortable : true},
       			{header: '送货类型', dataIndex: 'distributionMode',width:defaultWidth,sortable : true},
       			{header: '提货方式', dataIndex: 'takeMode',width:defaultWidth,sortable : true},
       			{header: '返货登记时间', dataIndex: 'createTime',width:defaultWidth,sortable : true},
       			{header: '最后修改时间', dataIndex: 'updateTime',width:defaultWidth,sortable : true},
       			{header: '返货原因', dataIndex: 'returnComment',width:defaultWidth,sortable : true}
        ]),
        store:dateStore,
      
        tbar: tbar,
        listeners: {
                    render: function(){
                        queryTbar.render(this.tbar);
                        threeBar.render(this.tbar);
                    }
                },
        bbar: new Ext.PagingToolbar({
            pageSize: pageSize, 
            privilege:privilege,
            store: dateStore, 
            displayInfo: true,
            displayMsg: '显示第 {0} 条到 {1} 条记录，共 {2} 条', 
            emptyMsg: "没有记录信息显示",
            plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
            items : ['-', '&nbsp;&nbsp;', '-', {
				text : '合计',
				iconCls : 'addIcon',
				handler : function() {
					summary.toggleSummary();
				}
			}]
        })
    });
   // searchreturnGoods();
  });
    
    
	
   function searchreturnGoods() {
		
		var checkItems=Ext.get("checkItems").dom.value;

		var searchreturnType = Ext.getCmp('searchreturnGoodsType').getRawValue();
		var searchdistributionMode=Ext.getCmp('searchdistributionMode').getRawValue();
		var searchCpName=Ext.getCmp('searchCpName').getRawValue();
		var searchDno=Ext.getCmp('searchDno').getValue();
		var searchFlightNo=Ext.getCmp('searchFlightNo').getValue();
		var searchstatus=Ext.getCmp('searchstatus').getValue();
		var searchAuditstatus = Ext.getCmp('searchAuditstatus').getValue();
		if(null!=searchDno && ""!=searchDno){
			dateStore.baseParams={
				filter_EQL_dno:searchDno,
				privilege:privilege,
		 		limit : pageSize
			}
		}else{
			dateStore.baseParams={
			 		filter_EQS_returnType:searchreturnType,
			 		filter_EQS_distributionMode:searchdistributionMode,
			 		filter_EQL_status:searchstatus,
			 		filter_LIKES_flightNo:searchFlightNo,
			 		filter_LIKES_cpName:searchCpName,
			 		filter_EQL_auditStatus:searchAuditstatus,
			 		privilege:privilege,
			 		limit : pageSize
			};
			if(checkItems.indexOf('Time')>0){
	   			
	   			 var searchTime = checkItems.substring(checkItems.indexOf('_')+1);
	   			 	
	   			 if(searchTime=='createTime'){
	   			 	Ext.apply(dateStore.baseParams,{
	   			 		filter_GED_createTime:(!Ext.getCmp("startDate").hidden?(Ext.get("startDate").dom.value=="开始时间"?"":Ext.get("startDate").dom.value):""),
	   			 		filter_LED_createTime:(!Ext.getCmp("endDate").hidden?(Ext.get("endDate").dom.value=="结束时间"?"":Ext.get("endDate").dom.value):"")
	   			 	})
	   			 }else if(searchTime=='updateTime'){
	   			 	Ext.apply(dateStore.baseParams,{
	   			 		filter_GED_updateTime:(!Ext.getCmp("startDate").hidden?(Ext.get("startDate").dom.value=="开始时间"?"":Ext.get("startDate").dom.value):""),
	   			 		filter_LED_updateTime:(!Ext.getCmp("endDate").hidden?(Ext.get("endDate").dom.value=="结束时间"?"":Ext.get("endDate").dom.value):"")
	   			 	})
	   			 }
	   			 
	   		}else{
	 			 Ext.apply(dateStore.baseParams, {
						checkItems : Ext.get("checkItems").dom.value,
						itemsValue : Ext.get("searchContent").dom.value
	   			});
	   		}
	 	}
	 	 //已经添加
	 	 //Ext.apply(dateStore.baseParams, {
		 //	filter_EQL_returnDepart:bussDepart
  		 //});
	 	
		dateStore.load();
		fnSumInfo();
		
//		totalStore.baseParams=dateStore.baseParams;
//		totalStore.reload();
//		Ext.Ajax.request({
//			url : sysPath+'/'
//					+ gridTotalUrl,
//			params :dateStore.baseParams,
//			success : function(resp) {
//				var respText = Ext.util.JSON.decode(resp.responseText);
//				//alert(respText.TOTALNUM);
//			}
//		});
   
	}
		/**
		 * 汇总表格
		 */
		function fnSumInfo() {
			Ext.Ajax.request({
				url : sysPath+'/'+gridTotalUrl,
				params:dateStore.baseParams,
				success : function(response) { // 回调函数有1个参数
					summary.toggleSummary(true);
					//this.view.summary.setStyle('overflow-x', 'auto');
					summary.setSumValue(Ext.decode(response.responseText));
				},
				failure : function(response) {
					Ext.MessageBox.alert(alertTitle, '汇总数据失败');
				}
			});
		}

//返货入库
function returnGoodsEnterStock(_records){
	top.Ext.Msg.confirm(alertTitle, '您确定要入库吗？', function(btnYes) {
	if (btnYes == 'yes' || btnYes == 'ok' || btnYes == true) {
		var ids="";
		for(var i=0;i<_records.length;i++){
//			if(_records[i].data.returnType=='整票返货' || _records[i].data.returnType=='部分返货'){
			 	ids=ids+_records[i].data.id+',';
//			}else{
//				Ext.Msg.alert(alertTitle, '您选择的第'+(i+1)+'条记录不是整票返货,也不是部分返货！');
//				return;
//					
//			}
		}
			Ext.Ajax.request({
						url : sysPath+'/'
								+ saveEnterStockUrl,
						params : {
							ids :ids,
							 privilege:privilege
						},
						success : function(resp) {
							var respText = Ext.util.JSON
									.decode(resp.responseText);
							if(respText.success){
								top.Ext.Msg.alert(alertTitle, "返货入库成功!");
										dataReload();
							}else{
								top.Ext.Msg.alert(alertTitle, respText.msg);
							}
						}
					});			
			}
	});

}
//返货登记
function returnGoodsRegistration (_records){
	var form = new Ext.form.FormPanel({
	labelAlign : 'right',
	frame : true,
	bodyStyle : 'padding:5px 5px 0',
	width : 500,
	reader : new Ext.data.JsonReader({
    root: 'result', totalProperty: 'totalCount'
	}, fields),
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
									xtype : 'numberfield',
									fieldLabel : '配送单号<font color=red>*</font>',
									allowBlank : false,
									blankText : "配送单号不能为空！",
									maxLength :10,
									id:'regDno',
									name : 'dno',
									anchor : '95%',
									enableKeyEvents:true,
									listeners:{
										'keyup':function(field,e){
											if(e.getKey()!=13){
												return;
											}else{
												allowRegist(field,form);
											}
										},'blur':function(field,e){
											allowRegist(field,form);
										}
									}
							       },{
							       	xtype : 'textarea',
							       	readOnly:true,
							       	fieldLabel : '收货人信息',
							       	id:'consigneeInfo',
							       	anchor : '95%'
							       },{
									xtype : 'textfield',
									readOnly:true,
//									fieldLabel : '送货方式',
									allowBlank : false,
									hidden:true,
									name:'outType',
									id:'outTypeId',
									anchor : '95%',
									enableKeyEvents:true,
									listeners:{
										keyup:function(field, e){
					                     if(e.getKey() == 13){
					                     	Ext.getCmp('dutyParty').focus(true,true);
					                     }}
									}
							       },{
										xtype : 'combo',
										triggerAction : 'all',
										store : dutyPartyStore,
										emptyText : "请选择责任方",
										forceSelection : true,
										allowBlank : false,
										fieldLabel : '责任造成方<font color=red>*</font>',
										mode : "local",// 从本地载值
										editable : false,
										minChars : 0,
										valueField : 'dutyPartyId',//value值，与fields对应
										displayField : 'dutyPartyName',//显示值，与fields对应
										name:'dutyParty',
										id:'dutyParty',
										anchor : '95%',
										enableKeyEvents:true,
										listeners:{
										keyup:function(field, e){
					                     if(e.getKey() == 13){
					                     	if(Ext.getCmp("outNoId").getEl().dom.readOnly){
					                     		Ext.getCmp('returnCostId').focus(true,true);
					                     	}else{
					                     		Ext.getCmp('outNoId').focus(true,true);
					                     	}
					                     }}
									}
								       },{
									xtype : 'numberfield',
									maxLength :10,
									fieldLabel : '实配单号',
									id:'outNoId',
									name : 'outNo',
									anchor : '95%',
									enableKeyEvents:true,
									listeners:{
										keyup:function(field, e){
											if(e.getKey() == 13){
						                     if(Ext.getCmp("returnCostId").getEl().dom.readOnly){
					                     		Ext.getCmp('returnType').focus(true,true);
					                     	}else{
					                     		Ext.getCmp('returnCostId').focus(true,true);
					                     	}
					                     }
										}
							      	}
							       },{
									xtype : 'numberfield',
									fieldLabel : '返货成本',
									maxLength :10,
									//allowNegative : false, 
									value:0,
									name : 'returnCost',
									id:'returnCostId',
									anchor : '95%',
									enableKeyEvents:true,
									listeners:{
										keyup:function(field, e){
						                     if(e.getKey() == 13){
						                     	Ext.getCmp('returnType').focus();
				                    	 }}
									}
							       }]

						},{
							columnWidth : .5,
							layout : 'form',
							items : [{
									xtype : 'combo',
									triggerAction : 'all',
									typeAhead : true,
									store : returnGoodsTypeStore,
									emptyText : "请选择返货类型",
									forceSelection : true,
									allowBlank : false,
									fieldLabel : '返货类型<font color=red>*</font>',
									editable : true,
									minChars : 0,
									valueField : 'returnGoodsTypeId',//value值，与fields对应
									displayField : 'returnGoodsTypeName',//显示值，与fields对应
									name:'returnType',
									id:'returnType',
									anchor : '95%',
									enableKeyEvents:true,
									listeners:{
										keyup:function(combo, e){
											if(e.getKey() != 13){
						                     	return;
				                    	 	}
											Ext.getCmp('returnComment').focus();
											}
				                    	 
				                    	 },
				                    	 blur:function(combo, e){
											Ext.getCmp('returnComment').focus();
										}
							       },{
										xtype : 'numberfield',
										fieldLabel : '返货件数<font color=red>*</font>',
										allowBlank : false,
										blankText : "返货件数不能为空！",
										maxLength :4,
										minValue:1,
										//readOnly:true,
										id:'returnNum',
										name : 'returnNum',
										anchor : '95%',
										enableKeyEvents:true,
										listeners:{
										keyup:function(combo, e){
						                     if(e.getKey() == 13){
						                     	if(combo.getValue()!=1){
						                     		Ext.getCmp('consigneeFee').focus();
						                     	}
				                    	 }}
										}
								       },{
										xtype : 'numberfield',
										fieldLabel : '到付提送费',
										maxLength :10,
										name : 'consigneeFee',
										id:'consigneeFee',
										readOnly:true,
										anchor : '95%',
										value:0,
										allowNegative : false, 
										enableKeyEvents:true,
										listeners:{
											keyup:function(combo, e){
							                     if(e.getKey() == 13){
							                     	if(combo.getValue()!=1){
							                     		Ext.getCmp('paymentCollection').focus();
							                     	}
					                    	 }}
										}
								       },{
										xtype : 'numberfield',
										fieldLabel : '代收货款',
										id:'paymentCollection',
										maxLength :10,
										readOnly:true,
										value:0,
										allowNegative : false, 
										name : 'paymentCollection',
										anchor : '95%',
										enableKeyEvents:true,
										listeners:{
										keyup:function(combo, e){
						                     if(e.getKey() == 13){
						                     	if(combo.getValue()!=1){
						                     		Ext.getCmp('returnComment').focus();
						                     	}
				                    	 }}
										}
								       },{
										xtype : 'textarea',
										fieldLabel : '返货原因',
										name : 'returnComment',
										id:'returnComment',
										anchor : '95%',
										enableKeyEvents:true,
										listeners:{
											keyup:function(combo, e){
							                     if(e.getKey() == 13){
							                     	if(combo.getValue()!=1){
							                     		Ext.getCmp('saveBtn').focus(true,true);
							                     	}
						                	 }}
											}
								       }
									]
						}]
						
						
			}
			]
		});
		var win = new Ext.Window({
		title : '返货登记',
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
				var flag=false;
				if (form.getForm().isValid()) {
					//Ext.Msg.confirm(alertTitle, "是否需要审核？", function(
					//	btnYes) {
					//	if (btnYes == 'yes' || btnYes == 'ok' || btnYes == true) {
					//		flag=true;
					
							form.getForm().submit({
								url : sysPath
										+ '/'+saveRegistrationUrl,
										params:{privilege:privilege,aduitFlag:flag},
								waitMsg : '正在保存数据...',
								success : function(form, action) {
									Ext.Msg.alert(alertTitle, "返货登记成功！",function(){	
										dateStore.reload();
										form.reset();
										Ext.getCmp('regDno').focus();
									}
									);
								},
								failure : function(a, action) {
									if (action.faliureType == Ext.form.Action.SERVER_INVALID) {
										Ext.Msg.alert(alertTitle, "服务器连接异常，请联系管理员！");
									} else {
											Ext.Msg.alert(alertTitle, action.result.msg);
									}
								}
							});
						/*}else{
							form.getForm().submit({
								url : sysPath
										+ '/'+saveRegistrationUrl,
										params:{privilege:privilege,aduitFlag:flag},
								waitMsg : '正在保存数据...',
								success : function(form, action) {
									Ext.Msg.alert(alertTitle, "返货登记成功！",function(){	
										dateStore.reload();
										form.reset();
										Ext.getCmp('regDno').focus();
									}
									);
								},
								failure : function(a, action) {
									if (action.faliureType == Ext.form.Action.SERVER_INVALID) {
										Ext.Msg.alert(alertTitle, "服务器连接异常，请联系管理员！");
									} else {
											Ext.Msg.alert(alertTitle, action.result.msg);
									}
								}
							});
						}
					});*/
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

//返货审核审核
function returnGoodsAudit(record){
	var form = new Ext.form.FormPanel({
	labelAlign : 'right',
	frame : true,
	url:sysPath+'/'+gridSearchUrl,
	baseParams:{
		filter_EQL_id:record.data.id,
		privilege:privilege
	},
	bodyStyle : 'padding:5px 5px 0',
	width : 500,
	reader : new Ext.data.JsonReader({
    root: 'result', totalProperty: 'totalCount'
	}, fields),
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
									xtype : 'numberfield',
									fieldLabel : '配送单号<font color=red>*</font>',
									readOnly:true,
									blankText : "配送单号不能为空！",
									maxLength :10,
									name : 'dno',
									anchor : '95%'
							       },{
							       	xtype : 'textarea',
							       	readOnly:true,
							       	fieldLabel : '收货人信息',
							       	id:'consigneeInfo',
							       	anchor : '95%'
							       },{
									xtype : 'textfield',
									readOnly:true,
//									fieldLabel : '送货方式',
									readOnly:true,
									hidden:true,
									name:'outType',
									anchor : '95%'
							       },{
										xtype : 'combo',
										triggerAction : 'all',
										store : dutyPartyStore,
										emptyText : "请选择责任方",
										forceSelection : true,
										fieldLabel : '责任造成方<font color=red>*</font>',
										mode : "local",// 从本地载值
										editable : false,
										readOnly:true,
										minChars : 0,
										valueField : 'dutyPartyId',//value值，与fields对应
										displayField : 'dutyPartyName',//显示值，与fields对应
										name:'dutyParty',
										anchor : '95%'
								   },{
									xtype : 'numberfield',
									maxLength :10,
									readOnly:true,
									fieldLabel : '实配单号',
									name : 'outNo',
									anchor : '95%'
							       },{
									xtype : 'numberfield',
									fieldLabel : '返货成本',
									maxLength :10,
									readOnly:true,
									value:0,
									name : 'returnCost',
									anchor : '95%'
							       }]

						},{
							columnWidth : .5,
							layout : 'form',
							items : [{
									xtype : 'combo',
									triggerAction : 'all',
									typeAhead : true,
									store : returnGoodsTypeStore,
									emptyText : "请选择返货类型",
									forceSelection : true,
									fieldLabel : '返货类型<font color=red>*</font>',
									editable : true,
									readOnly:true,
									minChars : 0,
									valueField : 'returnGoodsTypeId',//value值，与fields对应
									displayField : 'returnGoodsTypeName',//显示值，与fields对应
									name:'returnType',
									anchor : '95%'
							       },{
										xtype : 'numberfield',
										fieldLabel : '返货件数<font color=red>*</font>',
										readOnly:true,
										blankText : "返货件数不能为空！",
										maxLength :4,
										name : 'returnNum',
										anchor : '95%'
								       },{
										xtype : 'numberfield',
										fieldLabel : '到付提送费',
										maxLength :10,
										readOnly:true,
										name : 'consigneeFee',
										anchor : '95%',
										value:0,
										readOnly:true
								       },{
										xtype : 'numberfield',
										fieldLabel : '代收货款',
										maxLength :10,
										value:0,
										readOnly:true,
										name : 'paymentCollection',
										anchor : '95%'
								       },{
										xtype : 'textarea',
										readOnly:true,
										fieldLabel : '返货原因',
										name : 'returnComment',
										anchor : '95%'
								       }
									]
						}]
						
						
			}
			]
		});
		
		if(record!=null){
			form.load({
				waitMsg : '正在载入数据...',
    			success : function(_form, action) {
    				Ext.Ajax.request({
						url : sysPath+'/'
								+ gridSearchFaxUrl,
						params :{filter_EQL_dno:record.data.dno},
						success : function(resp) {
							var jdata = Ext.util.JSON.decode(resp.responseText);
							if(jdata.success && jdata.result.length>0){
								Ext.getCmp('consigneeInfo').setValue(jdata.result[0].consignee+"/"+jdata.result[0].consigneeTel+"/"+jdata.result[0].addr+"/"+jdata.result[0].distributionMode);
							}
						}
					});
    				//给收货人信息赋值
    				//Ext.getCmp('consigneeInfo').setValue();
    			},
    			failure : function(_form, action) {
    				Ext.MessageBox.alert(alertTitle, '载入失败');
    			}
			});
		}
		
		var win = new Ext.Window({
		title : '返货审核审核',
		width : 500,
		closeAction : 'hide',
		plain : true,
		modal : true,
		items : form,
		buttonAlign : "center",
		buttons : [{
			text : "审核",
			iconCls : 'groupSave',
			id:'saveBtn',
			handler : function() {
				if (form.getForm().isValid()) {
					if(record.data.distributionMode=='外发'){
						Ext.Ajax.request({
							url : sysPath+'/'
									+ judgeUrl,
							params : {
								dno:record.data.dno
							},
							success : function(resp) {
								var respText = resp.responseText;
								if(respText!=null){
									top.Ext.Msg.confirm(alertTitle, '<font color=red>'+respText+'</font>'+"是否确定要审核？", function(btnYes) {
										if (btnYes == 'yes' || btnYes == 'ok' || btnYes == true) {
											form.getForm().submit({
												url : sysPath
														+ '/'+saveAuditReturnGoodsUrl,
												params:{privilege:privilege,returnGoodsId:record.id},
												waitMsg : '正在保存数据...',
												success : function(form, action) {
													Ext.Msg.alert(alertTitle, "审核成功！",function(){	
														win.hide();
														searchreturnGoods();
													});
												},
												failure : function(a, action) {
													if (action.faliureType == Ext.form.Action.SERVER_INVALID) {
														Ext.Msg.alert(alertTitle, "服务器连接异常，请联系管理员！");
													} else {
															Ext.Msg.alert(alertTitle, action.result.msg);
													}
												}
											});
										}
									});
								}else{
									top.Ext.Msg.alert(alertTitle, respText.msg);
								}
							},
							failure : function(response) {
								Ext.Msg.alert(alertTitle, '审核验证失败！！');
							}
						});
					}else{
						form.getForm().submit({
							url : sysPath
									+ '/'+saveAuditReturnGoodsUrl,
							params:{privilege:privilege,returnGoodsId:record.id},
							waitMsg : '正在保存数据...',
							success : function(form, action) {
								Ext.Msg.alert(alertTitle, "审核成功！",function(){	
									win.hide();
									searchreturnGoods();
								});
							},
							failure : function(a, action) {
								if (action.faliureType == Ext.form.Action.SERVER_INVALID) {
									Ext.Msg.alert(alertTitle, "服务器连接异常，请联系管理员！");
								} else {
									Ext.Msg.alert(alertTitle, action.result.msg);
								}
							}
						});
					}
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
				start : 0,
				privilege:privilege,
				limit : pageSize
			}
		});
		
 }
 function allowRegist(field,form){
// 	alert(form.getForm().findField('dno').getValue());
	if(field.getValue().length<1){
			return;
	}
	Ext.getCmp('outNoId').setValue('');
	Ext.getCmp('returnCostId').setValue('');
		Ext.Ajax.request({
			url : sysPath+'/'
					+ allowRegistration,
			params :{privilege:68,filter_EQL_dno:Number(field.getValue())},
			success : function(resp) {
				var jdata = Ext.util.JSON.decode(resp.responseText);
					if(jdata.msg){
						Ext.Msg.alert(alertTitle,jdata.msg,function(){
							field.focus(true,true);
							return;
						}
						);
							
					}else{
						if(jdata.length==0){
							Ext.Msg.alert(alertTitle,'您输入的配送单号不存在！',function(){
							
								field.setValue('');
								form.getForm().reset();
								field.focus(true,true);
								return;
							});
						}else{
						
							var distributionMode=jdata.distributionMode;
							Ext.getCmp('outTypeId').setValue(distributionMode);
							Ext.getCmp('consigneeInfo').setValue(jdata.consignee+"/"+jdata.consigneeTel+"/"+jdata.addr+"/"+jdata.distributionMode);
							Ext.getCmp('outNoId').setValue(jdata.town);
							
							Ext.getCmp('returnNum').setValue(jdata.piece);
							Ext.getCmp('consigneeFee').setValue(jdata.consigneeFee);
							Ext.getCmp('paymentCollection').setValue(jdata.paymentCollection);
															
							if(distributionMode=='新邦'){
								Ext.getCmp("returnCostId").getEl().dom.readOnly=true;
								Ext.getCmp("outNoId").getEl().dom.readOnly=false;
								
							}else{
								Ext.getCmp("outNoId").getEl().dom.readOnly=true;
								Ext.getCmp("returnCostId").getEl().dom.readOnly=false;
							}
							Ext.getCmp('dutyParty').focus();
						}
					}
			}
		});
 }
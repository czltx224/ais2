//主单及重量调整
var privilege=102;//权限参数
var gridSearchUrl="stock/oprMainOrderAdjustAction!ralaList.action";//查询地址
var updateMainNoAndWeightUrl="stock/oprMainOrderAdjustAction!updateMainNoAndWeight.action";//主单及货量调整调整地址
var searchFaxUrl='fax/oprFaxInAction!ralaList.action';//传真表查询地址
var searchCusNameUrl='sys/customerAction!list.action';//查询代理公司地址
var searchWidth=60;
var defaultWidth=80;
  		
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

var  fields=[{name:'id',mapping:'id'},{name:'dno',mapping:'dno'},'oldFlightMainNo','newFlightMainNo','oldWeight','newWeight','oldConsigneeFee','newConsigneeFee','adjustMoney','departId','departName',
				'fdno','cusId','cpName','subNo','flightNo','flightMainNo','flightTime','flightDate','addr','consignee','gowhere','piece','consigneeRate','consigneeFee',
				'bulk','goods','goodsStatus','createName','createTime','updateName','updateTime','ts','cusWeight','inDepartId','whoCash'];


	var tbar=[{text:'<b>调整</b>',iconCls:'table',tooltip : '调整',handler:function() {
					//Ext.Msg.alert(alertTitle, "调整！");
					
					var records= dateStore.getModifiedRecords();
					if(records.length>0){
						Ext.Msg.confirm(alertTitle, "确定要调整这" + records.length + "条记录吗？", function(
						btnYes) {
						if (btnYes == 'yes' || btnYes == 'ok' || btnYes == true) {
								adjustMainNoAndWeight(records);
							}
						});
					}else{
						Ext.Msg.alert(alertTitle, "您没有做任何改动,不需调整！");
					}
            } }
            ,'-',{text:'<b>导出</b>',iconCls:'sort_down',tooltip : '导出',handler:function() {
					
					parent.exportExl(mainOrderAdjustGrid);

            } },'-',
	{xtype:'textfield',id:'searchContent',width:searchWidth, enableKeyEvents:true,listeners : {
	 		
	 		keyup:function(textField, e){

                     if(e.getKey() == 13){

                     	searchmainOrderAdjust();

                     }
	 		}
	 	}},{
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
    	},'-',{ 
	 	
    					xtype : "combo",
    					width : 100,
    					triggerAction : 'all',
    					id:'searchSelectBox',
    					model : 'local',
    					hiddenId : 'checkItems',
    					hiddenName : 'checkItems',
    					name : 'checkItemstext',
    					store : [['','查询全部'],
    							['LIKES_consignee','收货人'],
    							['LIKES_addr','收货人地址'],
    							['LIKES_gowhere','去向'],
    							['LIKES_goods','品名'],
    							['LIKES_goodsStatus','货物状态'],
    							['EQD_flightDate','航班日期'],
    							['EQD_updateTime','调整时间']
    							],
    							
    					editable : false,
    					forceSelection : true,
    					listeners : {
    						
    						'select' : function(combo, record, index) {
								
    							if (combo.getValue() == 'EQD_flightDate' || combo.getValue() == 'EQD_updateTime' ) {
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
    								Ext.getCmp("startDate").disable();
    								Ext.getCmp("endDate").hide();
    								Ext.getCmp("startDate").setValue("");
    								Ext.getCmp("endDate").disable();
    								
    						      	Ext.getCmp("searchContent").enable();
    								Ext.getCmp("searchContent").show();
    								
    							}
    							if (combo.getValue().length==0){
									
									
									Ext.getCmp("searchflightMainNo").setValue('');
									Ext.getCmp("searchflightNo").setValue('');
									Ext.getCmp("searchsubNo").setValue('');
									Ext.getCmp("searchDno").setValue('');
									Ext.getCmp("searchCpName").setValue('');
									Ext.getCmp("searchContent").setValue('');
									searchmainOrderAdjust();
								}else if(Ext.getCmp("searchContent").getValue().length>0){
									searchmainOrderAdjust();
								}
    							
    						}
    					}
    					
    				}
	 	];
	 	
	 	var queryTbar=new Ext.Toolbar([
	 	
    		'主单号:',{xtype:'textfield',id:'searchflightMainNo',width:searchWidth, enableKeyEvents:true,listeners : {
	 		
	 		keyup:function(textField, e){
                     if(e.getKey() == 13){
                     	searchmainOrderAdjust();
                     }
	 		}
	 	}},	'-',
	 		'航班号:',{xtype:'textfield',id:'searchflightNo',width:searchWidth, enableKeyEvents:true,listeners : {
	 		
	 		keyup:function(textField, e){
                     if(e.getKey() == 13){
                     	searchmainOrderAdjust();
                     }
	 		}
	 	}},	'-',
	 		'分单号:',{xtype:'textfield',id:'searchsubNo',width:searchWidth, enableKeyEvents:true,listeners : {
	 		
	 		keyup:function(textField, e){
                     if(e.getKey() == 13){
                     	searchmainOrderAdjust();
                     }
	 		}
	 	}},	'-',
	 		'配送单号:',{xtype:'numberfield',id:'searchDno',width:searchWidth, enableKeyEvents:true,listeners : {
	 		
	 		keyup:function(textField, e){

                     if(e.getKey() == 13){

                     	searchmainOrderAdjust();

                     }
	 		}
	 	}},	'-',
	 	'代理公司:',{xtype : "combo",
				width : searchWidth+20,
				editable:true,
				triggerAction : 'all',
				typeAhead : true,
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
			    enableKeyEvents:true,listeners : {
	 		
	 		    keyup:function(textField, e){

                     if(e.getKey() == 13){

                     	searchmainOrderAdjust();

                     }
	 		}
	 	
	 	}},	'-',
	 	
	 		{text : '<b>查询</b>',id : 'btn',iconCls : 'btnSearch',
    					handler : searchmainOrderAdjust
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
   var rowNum = new Ext.grid.RowNumberer({
        header:'序号', width:20
    });
    mainOrderAdjustGrid = new Ext.grid.EditorGridPanel({
        renderTo: Ext.getBody(),
        id:'mainOrderAdjustCenter',
        height:Ext.lib.Dom.getViewHeight()-2,		
        width:Ext.lib.Dom.getViewWidth(),
        viewConfig : {
			columnsText : "显示的列",
			sortAscText : "升序",
			sortDescText : "降序"
			//forceFit : true
		},
		autoScroll : true,
		//autoSizeColumns: true,
		sm: new Ext.grid.RowSelectionModel({ 
            }),  
		frame : false,
		clicksToEdit:1,
		loadMask : true,
		stripeRows : true,
        cm:new Ext.grid.ColumnModel([rowNum,
       			{header: '配送单号', dataIndex: 'fdno',width:defaultWidth,sortable : true},
       			{header: '货物当前状态', dataIndex: 'goodsStatus',width:defaultWidth,sortable : true},
       			{header: '调整前主单号', dataIndex: 'flightMainNo',width:defaultWidth,sortable : true
       					,renderer:function renderDescn(value, cellmeta, record, rowIndex, columnIndex, store){
							if(record.data["oldFlightMainNo"] ==0)
								return record.data["flightMainNo"];
							else
							  	return "<font color='red'>"+record.data["oldFlightMainNo"]+"</font>";
							}
       			},
       			{header: '调整后主单号', dataIndex: 'newFlightMainNo',width:defaultWidth,sortable : true
       				,css :colcss ,editor: new Ext.form.TextField({
								}) 
       			},
       			{header: '分单号', dataIndex: 'subNo',width:defaultWidth,sortable : true},
       			{header: '代理公司', dataIndex: 'cpName',width:defaultWidth,sortable : true},
 				{header: '航班号', dataIndex: 'flightNo',width:defaultWidth,sortable : true},
       			{header: '航班落地时间', dataIndex: 'flightTime',width:defaultWidth,sortable : true},
       			{header: '收货人', dataIndex: 'consignee',width:defaultWidth,sortable : true},
       			{header: '收货人地址', dataIndex: 'addr',width:defaultWidth,sortable : true},
       			{header: '去向', dataIndex: 'gowhere',width:defaultWidth,sortable : true},
 				{header: '件数', dataIndex: 'piece',width:defaultWidth,sortable : true},
 				{header: '费率', dataIndex: 'consigneeRate',width:defaultWidth,sortable : true},
 				{header: '调整前计费重量', dataIndex: 'oldWeight',width:defaultWidth,sortable : true
 						,renderer:function renderDescn(value, cellmeta, record, rowIndex, columnIndex, store){
							if(record.data["oldWeight"] ==0)
								return record.data["cusWeight"];
							else
							  	return "<font color='red'>"+record.data["oldWeight"]+"</font>";
							}
 				},
 				{header: '调整后计费重量', dataIndex: 'newWeight',width:defaultWidth,sortable : true
 					,css :colcss ,editor: new Ext.form.NumberField({
 						allowNegative :false,
 						listeners:{
 							blur:function(row,e){
 						
 								var record = mainOrderAdjustGrid.getSelectionModel().getSelected();
								//alert(record.data.fdno);
 								//getConsigneeRate(record);
 							}
 						}
						}) 
 				},
 				{header: '调整前提送费', dataIndex: 'consigneeFee',width:defaultWidth,sortable : true
 					,renderer:function renderDescn(value, cellmeta, record, rowIndex, columnIndex, store){
						if(record.data["oldConsigneeFee"] ==0)
							return record.data["consigneeFee"];
						else
						  	return "<font color='red'>"+record.data["oldConsigneeFee"]+"</font>";
					}
 				},
 				{header: '调整后提送费', dataIndex: 'newConsigneeFee',width:defaultWidth,sortable : true},
 				{header: '调整费差额', dataIndex: 'adjustMoney',width:defaultWidth,sortable : true},
 				{header: '体积', dataIndex: 'bulk',width:defaultWidth,sortable : true},
 				{header: '品名', dataIndex: 'goods',width:defaultWidth,sortable : true},
 				{header: '调整人', dataIndex: 'updateName',width:defaultWidth,sortable : true},
 				{header: '调整时间', dataIndex: 'updateTime',width:defaultWidth,sortable : true},
 				{header: '调整部门', dataIndex: 'departName',width:defaultWidth,sortable : true},
 				{header: '时间戳', dataIndex: 'ts',width:defaultWidth,sortable : true,hidden:true},
 				{header: '付款方', dataIndex: 'whoCash',width:defaultWidth,sortable : true,hidden:true
 					,renderer:function (v, cellmeta, record, rowIndex, columnIndex, store){
						if(v=='预付'){
							//record.set('newConsigneeFee',newConsigneeFee);
						}
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
            afterPageText: '共{0}页',
		    beforePageText: '当前页',
            displayInfo: true,
            displayMsg: '显示第 {0} 条到 {1} 条记录，共 {2} 条', 
            emptyMsg: "没有记录信息显示"
        })
    });
    
    var sm = mainOrderAdjustGrid.getSelectionModel();

         sm.onEditorKey = function(field, e) {
             var k = e.getKey();
             if (k == e.ENTER) {
                 e.stopEvent();
             } 
         };

   // searchmainOrderAdjust();
  });
    
    
	//查询方法
   function searchmainOrderAdjust() {
   	dateStore.baseParams={
   			filter_EQS_inDepartId:bussDepart,
   			privilege:privilege,
   			limit : pageSize
   		};
			
				var searchflightMainNo = Ext.getCmp('searchflightMainNo').getValue();
				var searchflightNo=Ext.getCmp('searchflightNo').getValue();
				var searchsubNo=Ext.getCmp('searchsubNo').getValue();
				var searchDno=Ext.get('searchDno').dom.value;
				var searchCpName=Ext.getCmp('searchCpName').getRawValue();
			if(null!=searchDno && searchDno.length>1){
				 Ext.apply(dateStore.baseParams, {
					filter_EQL_fdno:searchDno
				});
			}else{
				 Ext.apply(dateStore.baseParams, {
				 	filter_EQS_flightMainNo:searchflightMainNo,
			 		filter_EQS_flightNo:searchflightNo,
			 		filter_EQS_subNo:searchsubNo,
			 		filter_LIKES_cpName:searchCpName,
			 		checkItems : '',
			 		itemsValue : ''
				 });
				 	 
	    			var searchItems=Ext.get("checkItems").dom.value;
	    			 if(searchItems=='EQD_flightDate'){
	    			 	Ext.apply(dateStore.baseParams, {
	    			 		filter_GED_flightDate:(!Ext.getCmp("startDate").hidden?(Ext.get("startDate").dom.value=="开始时间"?"":Ext.get("startDate").dom.value):""),
	    			 		filter_LED_flightDate:(!Ext.getCmp("endDate").hidden?(Ext.get("endDate").dom.value=="结束时间"?"":Ext.get("endDate").dom.value):"")
	    			 	})
	    			 }else if(searchItems=='EQD_updateTime'){
	    			 	Ext.apply(dateStore.baseParams, {
	    			 		filter_GED_updateTime:(!Ext.getCmp("startDate").hidden?(Ext.get("startDate").dom.value=="开始时间"?"":Ext.get("startDate").dom.value):""),
	    			 		filter_LED_updateTime:(!Ext.getCmp("endDate").hidden?(Ext.get("endDate").dom.value=="结束时间"?"":Ext.get("endDate").dom.value):"")
	    			 	})
	    			 }else{
			 			 Ext.apply(dateStore.baseParams, {
								checkItems : Ext.get("checkItems").dom.value,
								itemsValue : Ext.get("searchContent").dom.value
		    			 });
		    		}
			 }
			 
			 dataReload();
	}

//设置提送费和调整费差额
function setconsigneeFee(newConsigneeFee){
	
	var record = mainOrderAdjustGrid.getSelectionModel().getSelected();
	record.set('newConsigneeFee',newConsigneeFee);
	record.set('adjustMoney',(newConsigneeFee-record.get('consigneeFee')));
	//record.commit();
}
//调整主单号和重量方法
function adjustMainNoAndWeight(records){

	mainOrderAdjustGrid.stopEditing();
	var datas="";
	for(var i=0;i<records.length;i++){
//		if(records[i].data.whoCash=='到付'){
//			
//		}else if(records[i].data.whoCash=='预付'){
//			
//		}
		if(i>0)
		{
			datas+='&';
		}
		datas+="mainIds["+i+"].dno="+records[i].data.fdno+'&'
				+"mainIds["+i+"].newFlightMainNo="+records[i].data.newFlightMainNo+'&'
				+"mainIds["+i+"].newWeight="+records[i].data.newWeight;
	}
	
	Ext.Ajax.request({
						url : sysPath+'/'
								+ updateMainNoAndWeightUrl+"?privilege="+privilege,
						params :datas,
						success : function(resp) {
							var respText = Ext.util.JSON.decode(resp.responseText);
							if(respText.success){
								Ext.Msg.alert(alertTitle,"调整成功!");
								dataReload();
								dateStore.commitChanges();
								//dateStore.rejectChanges();
							}else{
								Ext.Msg.alert(alertTitle,respText.msg);
							}
						}
					});
}
//重新加载方法
 function dataReload(){
 			dateStore.commitChanges();
			dateStore.reload({
			params : {
				privilege:privilege,
				limit : pageSize
			}
		});
		
 }

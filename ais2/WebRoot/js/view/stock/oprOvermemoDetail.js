//入库综合处理js
var privilege=48;//权限参数
var gridSearchUrl="stock/oprOvermemoDetailAction!findOvermemoDetail.action";
var revokeUrl="stock/oprOvermemoDetailAction!revokedOvermemo.action";
var dictionaryUrl='sys/dictionaryAction!ralaList.action';
var carUrl = "bascar/basCarAction!list.action";
var loadingbrigadeUrl='sys/loadingbrigadeAction!list.action';//装卸组分拨组查询地址
var defaultWidth=80;

var  fields=[{name:"idd",mapping:'IDD'},
     		{name:'overmemoId',mapping:'OVERMEMOID'},
     		{name:"dno",mapping:'DNO'},
     		{name:'subNo',mapping:'SUBNO'},
     		{name:'realPiece',mapping:'REALPIECE'},
     		{name:'piece',mapping:'PIECE'},
     		{name:'cusId',mapping:'CUSID'},
     		{name:'cpName',mapping:'CPNAME'},
     		{name:'weight',mapping:'WEIGHT'},
     		{name:'flightNo',mapping:'FLIGHTNO'},
     		{name:'consignee',mapping:'CONSIGNEE'},
     		{name:'addr',mapping:'ADDR'},
     		{name:'goods',mapping:'GOODS'},
     		{name:'createTime',mapping:'CREATETIME'},
     		{name:'carCode',mapping:'CARCODE'},
     		{name:'status',mapping:'STATUS'},
     		{name:'stockAreaName',mapping:'STOCKAREANAME'},
     		{name:'loadingbrigadeName',mapping:'LOADINGBRIGADENAME'},
     		{name:'dispatchGroup',mapping:'DISPATCHGROUP'},
     		{name:'flightMainNo',mapping:'FLIGHTMAINNO'},
     		{name:'goodsStatus',mapping:'GOODSSTATUS'},
     		{name:'realGoWhere',mapping:'REALGOWHERE'},
     		{name:'loadingbrigadeWeightId',mapping:'LOADINGBRIGADEWEIGHTID'}];
     //车辆
	  carStore = new Ext.data.Store({
	        storeId:"carStore",
			proxy:new Ext.data.HttpProxy({url:sysPath+"/"+carUrl}),
			reader:new Ext.data.JsonReader({
	                    root:'result',
	                    totalProperty:'totalCount'
	        },[
        	{name:'carId',mapping:'id'},
        	{name:'carCode',mapping:'carCode'}
        	])
        	
		});
		
		//分拨组
	 dispatchGroupStore= new Ext.data.Store({ 
		storeId:"dispatchGroupStore",
		proxy:new Ext.data.HttpProxy({url:sysPath+"/"+loadingbrigadeUrl}),
		baseParams:{filter_EQL_type:1,filter_EQL_departId:bussDepart},
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
    	{name:'dispatchGroupId',mapping:'id'},
    	{name:'dispatchGroupName',mapping:'loadingName'}
    	])
	});
	//装卸组
	 loadingbrigadeStore= new Ext.data.Store({ 
		storeId:"loadingbrigadeStore",
		proxy:new Ext.data.HttpProxy({url:sysPath+"/"+loadingbrigadeUrl}),
		baseParams:{filter_EQL_type:0,filter_EQL_departId:bussDepart},
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
    	{name:'loadingbrigadeId',mapping:'id'},
    	{name:'loadingbrigadeName',mapping:'loadingName'}
    	])
	});
			
	var tbar=[{text:'<b>撤销点到</b>',iconCls:'revoked',tooltip : '撤销点到',handler:function() {
                	var overmemo =Ext.getCmp('overmemoCenter');
			var _records = overmemo.getSelectionModel().getSelections();
	
					if (_records.length < 1) {
						Ext.Msg.alert(alertTitle, "请选择您要操作的行！");
						return false;
					} 

					Ext.Msg.confirm(alertTitle, "确定要撤销点到这"+_records.length+"条记录吗？", function(
									btnYes) {
								if (btnYes == 'yes' || btnYes == 'ok'
										|| btnYes == true) {
									var ids="";
									for(var i=0;i<_records.length;i++){
									
										   //ids=ids+_records[i].data.id+',';
										   ids += "overIds["+i+"].overmemoDetailId="+_records[i].data.idd+'&'
										   	   	+ "overIds["+i+"].loadingbrigadeWeightId="+_records[i].data.loadingbrigadeWeightId+'&'
										   	   	+ "overIds["+i+"].dno="+_records[i].data.dno+'&'
										   	   	+ "overIds["+i+"].surePiece="+_records[i].data.realPiece+'&'
										   	   	+ "overIds["+i+"].cqWeight="+_records[i].data.weight+'&'
										   	   	+ "overIds["+i+"].overmemoNo="+_records[i].data.overmemoId+'&';
									}
									Ext.Ajax.request({
										url : sysPath+'/'
												+ revokeUrl+"?privilege="+privilege,
										params :ids,
										success : function(resp) {
											var respText = Ext.util.JSON.decode(resp.responseText);
											if(respText.success){
												Ext.Msg.alert(alertTitle,"撤销点到成功!");
												searchOvermemo();
											}else{
												Ext.Msg.alert(alertTitle,respText.msg);
											}
										}
									});
								}
							});
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

                     	searchOvermemo();

                     }
	 		}
	 	}},'-',{ 
	 	
    					xtype : "combo",
    					width : 100,
    					triggerAction : 'all',
    					id:'searchSelectBox',
    					model : 'local',
    					hiddenId : 'checkItems',
    					hiddenName : 'checkItems',
    					name : 'checkItemstext',
    					store : [
    							['','查询全部'],
    							['t_dNo','配送单号'],
    							['t_flightMainNo','主单号'],
    							['LIKES_t_subNo','分单号'],
    							['t_overmemo','交接单号'],
    							['LIKES_t_consignee','收货人'],
    							['LIKES_t_flightNo','航班号'],
    							['LIKES_t_goods', '品名'],
    							['LIKES_t_storageArea','库存区域'],
    							['LIKES_t_cpName','代理公司']],
    							
    					emptyText : '选择查询方式',
    					editable : false,
    					forceSelection : true,
    					listeners : {
    						'select' : function(combo, record, index) {
   								if(Ext.getCmp("searchContent").getValue().length>0){
   									searchOvermemo();
   								}
    						}
    					}
    					
    				},
	 	'-', '装卸组：', {
			xtype : "combo",
			width : 100,
			id : 'loadingbrigade',
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
			mode : "remote",// 从本地加载值
			valueField : 'loadingbrigadeId',// value值，与fields对应
			displayField : 'loadingbrigadeName',// 显示值，与fields对应
			hiddenName : 'loadingbrigadeId'

	}, '-', '分拨组：', {
			xtype : "combo",
			width : 100,
			id : 'dispatchGroup',
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
			valueField : 'dispatchGroupId',// value值，与fields对应
			displayField : 'dispatchGroupName',// 显示值，与fields对应
			name : 'dispatchGroup'
	}];
	 	
	 	var queryTbar=new Ext.Toolbar([
	 	'-','交接单号:',{xtype:'textfield',blankText:'交接单号',
	 	width:defaultWidth,
	 	id:'searchOvermemeoId', enableKeyEvents:true,
	 	listeners : {
	 		keyup:function(textField, e){
                     if(e.getKey() == 13){
                     	searchOvermemo();
                     }
	 		}
	 	}},'-','车牌号:',{ 
		 	xtype : 'combo',
			width:defaultWidth,
			id:'mycombo',
			queryParam : 'filter_LIKES_carCode',
			triggerAction : 'all',
			store : carStore,
			pageSize : comboSize,
			selectOnFocus:true,
			resizable:true,
			minChars : 0,
			mode : "remote",//从服务器端加载值
			valueField : 'carId',//value值，与fields对应
			displayField : 'carCode',//显示值，与fields对应
			enableKeyEvents:true,
			listeners : {
	 		keyup:function(textField, e){
                     if(e.getKey() == 13){
                     	searchOvermemo();
                     }
	 		}
	 	}},'-','入库时间：',{
    		xtype : 'datefield',
    		id : 'startDate',
    		format : 'Y-m-d',
    		value:new Date().add(Date.DAY, -7),
    		emptyText : "开始时间",
    		width:defaultWidth+20,
    		editable : true,
    		listeners : {
    			'select' : function() {
    			   var start = Ext.getCmp('startDate').getValue().format("Y-m-d");
    			   Ext.getCmp('endDate').setMinValue(start);
    		     }
    		}
    	}, '&nbsp;到&nbsp;', {
    		xtype : 'datefield',
    		id : 'endDate',
    		format : 'Y-m-d',
    		value:new Date(),
    		emptyText : "结束时间",
    		width:defaultWidth+20,
    		editable : true
    	},'-',{text : '<b>查询</b>',id : 'btn',iconCls : 'btnSearch',
    					handler : searchOvermemo
    				}	
	 	]);	


	 	
Ext.onReady(function() {
   	dateStore = new Ext.data.Store({
        storeId:"dateStore",
        proxy: new Ext.data.HttpProxy({url : sysPath+ "/"+gridSearchUrl}),
        baseParams:{privilege:privilege,limit : pageSize},
        reader: new Ext.data.JsonReader({
            root: 'resultMap', totalProperty: 'totalCount'
        }, fields)
    });
    //dateStore.on("load" ,function( store, records,options ){alert(records.length);});
    
   var sm = new Ext.grid.CheckboxSelectionModel({});
	 var rowNum = new Ext.grid.RowNumberer({
        header:'序号', width:20
    });
    overmemoGrid = new Ext.grid.GridPanel({
        renderTo: Ext.getBody(),
        id:'overmemoCenter',
        height:Ext.lib.Dom.getViewHeight()-1,		
        width:Ext.lib.Dom.getViewWidth()-1,
		autoScroll : true,
		//autoSizeColumns: true,
		frame : false,
		loadMask : true,
	//	stripeRows : true,
        sm:sm,
        cm:new Ext.grid.ColumnModel([rowNum,sm,
       			{header: '配送单号', dataIndex: 'dno',width:defaultWidth,sortable : true},
       			{header: '货物状态', dataIndex: 'goodsStatus',width:defaultWidth,sortable : true},
        		{header: '交接明细编号', dataIndex: 'idd',width:defaultWidth,sortable : true},
       			{header: '交接单号', dataIndex: 'overmemoId',width:defaultWidth,sortable : true},
       			{header: '主单号', dataIndex: 'flightMainNo',width:defaultWidth,sortable : true},
       			{header: '分单号', dataIndex: 'subNo',width:defaultWidth,sortable : true},
       			{header: '航班号', dataIndex: 'flightNo',width:defaultWidth,sortable : true},
       			{header: '代理公司', dataIndex: 'cpName',width:defaultWidth,sortable : true},
 				{header: '车牌号', dataIndex: 'carCode',width:defaultWidth,sortable : true},
       			{header: '去向', dataIndex: 'realGoWhere',width:defaultWidth*2,sortable : true},
       			{header: '应到件数', dataIndex: 'piece',width:defaultWidth,sortable : true},
 				{header: '实到件数', dataIndex: 'realPiece',width:defaultWidth,sortable : true},
 				{header: '收货人', dataIndex: 'consignee',width:defaultWidth,sortable : true},
 				{header: '收货人地址', dataIndex: 'addr',width:defaultWidth,sortable : true},
       			{header: '品名', dataIndex: 'goods',width:defaultWidth,sortable : true},
       			{header: '库存区域', dataIndex: 'stockAreaName',width:defaultWidth,sortable : true},
 				{header: '重量', dataIndex: 'weight',width:defaultWidth,sortable : true},
 				{header: '状态', dataIndex: 'status',width:defaultWidth,sortable : true
 					,renderer:function(v){
 						if(v==1){
							return "已点到";
						}else if(v==2){
							return "部分点到";
						}else {
							return "未点到";
						} 
			        }
 				},
 				{header: '入库时间', dataIndex: 'createTime',width:defaultWidth,sortable : true},
 				{header: '装卸组', dataIndex: 'loadingbrigadeName',width:defaultWidth,sortable : true},
 				{header: '装卸组ID', dataIndex: 'loadingbrigadeId',width:defaultWidth,hidden:true},
 				{header: '装卸组货量表ID', dataIndex: 'loadingbrigadeWeightId',hidden:true},
 				{header: '分拨组', dataIndex: 'dispatchGroup',width:defaultWidth,sortable : true}
        ]),
        store:dateStore,
        tbar: tbar,
        listeners: {
                    render: function(){
                        queryTbar.render(this.tbar);
                    }
                },
        bbar: new Ext.PagingToolbar({
            store: dateStore, 
            pageSize: pageSize,
            afterPageText: '共{0}页',
		    beforePageText: '当前页',
            displayInfo: true,
            displayMsg: '显示第 {0} 条到 {1} 条记录，共 {2} 条', 
            emptyMsg: "没有记录信息显示"
        })
    });
  });
    
    
	
   function searchOvermemo() {
		dateStore.baseParams = {
			start : 0,
			privilege:privilege,
			limit : pageSize
		};
				var searchOvermemeoId = Ext.getCmp('searchOvermemeoId').getValue();
				var loadingbrigadeId=Ext.getCmp('loadingbrigade').getValue();
				var dispatchGroup=Ext.getCmp('dispatchGroup').getRawValue();
				var checkItems =  Ext.get("checkItems").dom.value;
				var searchContent =  Ext.get("searchContent").dom.value;
				//alert(searchContent.length);
				if(checkItems=="EQL_dno" && searchContent.length>1){
					Ext.apply(dateStore.baseParams, {
						filter_checkItems : checkItems,
						filter_itemsValue : searchContent
					});
				}else{
		 			 Ext.apply(dateStore.baseParams, {
		 			 		filter_countCheckItems:'t.create_time',
	   						filter_startCount:(!Ext.getCmp("startDate").hidden?(Ext.get("startDate").dom.value=="开始时间"?"":Ext.get("startDate").dom.value):""),
	   						filter_endCount : (!Ext.getCmp("endDate").hidden?(Ext.get("endDate").dom.value=="结束时间"?"":Ext.get("endDate").dom.value):""),
	    					filter_t_overmemo:searchOvermemeoId,
	    					filter_LIKES_o_carCode:Ext.getCmp('mycombo').getRawValue(),
	    					filter_g_loadingName:dispatchGroup,
	    					filter_l_loadingName:loadingbrigadeId,
							filter_checkItems : checkItems,
							filter_itemsValue : searchContent
	    			});
    			}
		dataReload();
	}

 function dataReload(){
			dateStore.load({
			params : {
				privilege:privilege,
				limit : pageSize
			}
		});
		
 }

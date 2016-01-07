//个性化要求执行情况js
var privilege=148;//权限参数
var gridSearchUrl="stock/oprRequestDoAction!findRequestDoList.action";
var defaultWidth=80;

var  fields=[{name:"cusId",mapping:'CUS_ID'},
     		{name:'cpName',mapping:'CP_NAME'},
     		{name:"dno",mapping:'D_NO'},
     		{name:'subNo',mapping:'SUB_NO'},
     		{name:'flightMainNo',mapping:'FLIGHT_MAIN_NO'},
     		{name:'flightNo',mapping:'FLIGHT_NO'},
     		{name:'traFee',mapping:'TRA_FEE'},
     		{name:'cpFee',mapping:'CP_FEE'},
     		{name:'consigneeFee',mapping:'CONSIGNEE_FEE'},
     		{name:'cusValueAddFee',mapping:'CUS_VALUE_ADD_FEE'},
     		{name:'cpValueAddFee',mapping:'CP_VALUE_ADD_FEE'},
     		{name:'flightDate',mapping:'FLIGHT_DATE'},
     		{name:'faxCreateTime',mapping:'FAX_CREATE_TIME'},
     		{name:'consignee',mapping:'CONSIGNEE'},
     		{name:'consigneeTel',mapping:'CONSIGNEE_TEL'},
     		{name:'piece',mapping:'PIECE'},
     		{name:'cusWeight',mapping:'CUS_WEIGHT'},
     		{name:'bulk',mapping:'BULK'},
     		{name:'id',mapping:'ID'},
     		{name:'requestStage',mapping:'REQUEST_STAGE'},
     		{name:'request',mapping:'REQUEST'},
     		{name:'oprMan',mapping:'OPR_MAN'},
     		{name:'isOpr',mapping:'IS_OPR'},
     		{name:'requestType',mapping:'REQUEST_TYPE'},
     		{name:'createName',mapping:'CREATE_NAME'},
     		{name:'remark',mapping:'REMARK'},
     		{name:'createTime',mapping:'CREATE_TIME'},
     		{name:'updateName',mapping:'UPDATE_NAME'},
     		{name:'updateTime',mapping:'UPDATE_TIME'},
     		{name:'ts',mapping:'TS'},
     		{name:'isException',mapping:'IS_EXCEPTION'},
     		{name:'sonderzugPrice',mapping:'SONDERZUG_PRICE'}];
     		
      isOprStore= new Ext.data.SimpleStore({
			 //autoLoad:true, //此处设置为自动加载
  			  data:[['','全部'],['0','未执行'],['1','已执行'],['2','执行失败']],
   			 fields:["isOprId","isOprName"]
		});
			
	var tbar=[{text:'<b>导出</b>',iconCls:'sort_down',tooltip : '导出数据',
	 		handler:function(){
	 				parent.exportExl(requestDoStation);
	 		}
	 	},'-',
	{xtype:'textfield',blankText:'查询数据',id:'searchContent', enableKeyEvents:true,listeners : {
	 		keyup:function(textField, e){
                     if(e.getKey() == 13){
                     	searchrequestDoStation();
                     }
	 		}
	 	}},{
    			xtype : 'datefield',
    		id : 'startDate',
    		format : 'Y-m-d',
    		emptyText : "开始时间",
    		anchor : '95%',
    		value:new Date().add(Date.DAY, -30),
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
    					store : [
    							['', '查询全部'],
    							['LIKES_r.create_Name', '创建人'],
    							['LIKES_opr_Man','执行人'],
    							['LIKES_sub_No','分单号'],
    							['LIKES_request','个性化要求'],
    							['LIKES_request_Stage','要求阶段'],
    							['LIKES_request_Type','类型'],
    							['LIKES_flight_no','航班号'],
    							['LIKES_consignee','收货人'],
    							['LIKES_consignee_Tel','收获人电话'],
    							['LIKES_cp_Name','代理名称'],
    							['TIME_flight_date','航班日期'],
    							['TIME_fax_create_time','传真时间']],
    							
    					emptyText : '选择查询方式',
    					editable : false,
    					forceSelection : true,
    					listeners : {
    						
    						'select' : function(combo, record, index) {
   								if (combo.getValue().indexOf('TIME')==0) {
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
    							
    							if(Ext.getCmp("searchContent").getValue().length>0){
									searchrequestDoStation();
								}else if (combo.getValue().length==0){
									searchrequestDoStation();
								}
    						}
    					}
    					
    				},{text : '<b>查询</b>',id : 'btn',iconCls : 'btnSearch',
    					handler : searchrequestDoStation
    				}	
	 	];	

var queryTbar = new Ext.Toolbar([
		'执行状态:', {
			xtype : 'combo',
			id : 'searchIsOpr',
			store : isOprStore,
			editable:false,
			triggerAction : 'all',
			forceSelection : true,
			selectOnFocus : true,
			mode : "local",// 从本地加载值
			valueField : 'isOprId',// value值，与fields对应
			displayField : 'isOprName',// 显示值，与fields对应
			width:defaultWidth,
			enableKeyEvents : true,
			listeners : {
				keyup : function(textField, e) {
					if (e.getKey() == 13) {
						searchrequestDoStation();
					}
				}
			}
		}, '-', '配送单号:', {
			xtype : 'numberfield',
			id : 'searchDno',
			width:defaultWidth,
			enableKeyEvents : true,
			listeners : {
				keyup : function(textField, e) {
					if (e.getKey() == 13) {
						searchrequestDoStation();
					}
				}
			}
		}, '-', '主单号:', {
			xtype : 'textfield',
			id : 'searchFlightMainNo',
			width:80,
			enableKeyEvents : true,
			listeners : {
				keyup : function(textField, e) {
					if (e.getKey() == 13) {
						searchrequestDoStation();
					}
				}
			}
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
    requestDoStation = new Ext.grid.GridPanel({
        renderTo: Ext.getBody(),
        id:'requestDoStationCenter',
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
 				{header: '个性化要求', dataIndex: 'request',width:defaultWidth,sortable : true},
 				{header: '需求阶段', dataIndex: 'requestStage',width:defaultWidth,sortable : true},
 				{header: '操作人', dataIndex: 'oprMan',width:defaultWidth,sortable : true},
 				{header: '执行状态', dataIndex: 'isOpr',width:defaultWidth,sortable : true
 						,renderer:function(v){
 							return v==0?'未执行':(v==1?'已执行':'执行失败');
			        	}
 				},
 				{header: '类型', dataIndex: 'requestType',width:defaultWidth,sortable : true},
 				{header: '创建人', dataIndex: 'createName',width:defaultWidth,sortable : true},
 				{header: '创建时间', dataIndex: 'createTime',width:defaultWidth,sortable : true},
       			{header: '主单号', dataIndex: 'flightMainNo',width:defaultWidth,sortable : true},
       			{header: '分单号', dataIndex: 'subNo',width:defaultWidth,sortable : true},
       			{header: '航班号', dataIndex: 'flightNo',width:defaultWidth,sortable : true},
       			{header: '代理', dataIndex: 'cpName',width:defaultWidth,sortable : true},
       			{header: '航班日期', dataIndex: 'flightDate',width:defaultWidth,sortable : true},
       			{header: '传真日期', dataIndex: 'faxCreateTime',width:defaultWidth,sortable : true},
       			{header: '客户名称', dataIndex: 'consignee',width:defaultWidth,sortable : true},
       			{header: '客户电话', dataIndex: 'consigneeTel',width:defaultWidth,sortable : true},
 				{header: '件数', dataIndex: 'piece',width:defaultWidth,sortable : true},
 				{header: '重量', dataIndex: 'cusWeight',width:defaultWidth,sortable : true},
 				{header: '体积', dataIndex: 'bulk',width:defaultWidth,sortable : true},
 				{header: '预付增值服务费', dataIndex: 'cpValueAddFee',width:defaultWidth,sortable : true},
 				{header: '增值服务费总额', dataIndex: 'cusValueAddFee',width:defaultWidth,sortable : true},
 				{header: '预付提送费', dataIndex: 'cpValueAddFee',width:defaultWidth,sortable : true},
 				{header: '到付提送费', dataIndex: 'cusValueAddFee',width:defaultWidth,sortable : true},
 				{header: '中转费', dataIndex: 'traFee',width:defaultWidth,sortable : true},
 				{header: '专车费', dataIndex: 'sonderzugPrice',width:defaultWidth,sortable : true},
 				{header: '修改人', dataIndex: 'updateName',width:defaultWidth,sortable : true},
 				{header: '修改时间', dataIndex: 'updateTime',width:defaultWidth,sortable : true},
 				{header: '备注', dataIndex: 'remark',width:defaultWidth,sortable : true},
 				{header: '时间戳', dataIndex: 'ts',width:defaultWidth,sortable : true},
 				{header: '是否异常', dataIndex: 'isException',width:defaultWidth,sortable : true},
 				{header: '个性化执行ID', dataIndex: 'id',width:defaultWidth,sortable : true}
        ]),
        store:dateStore,
      
        tbar: tbar,
        listeners : {
			render : function() {
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
    
    
	
   function searchrequestDoStation() {
		dateStore.baseParams = {
			start : 0,
			privilege:privilege,
			limit : pageSize
		};
				
			var searchFlightMainNo = Ext.getCmp('searchFlightMainNo').getValue();
			var searchDno = Ext.get('searchDno').dom.value;
			var searchIsOpr = Ext.getCmp('searchIsOpr').getValue();
			var checkItems=Ext.get("checkItems").dom.value;
			
			if(null!=searchDno && searchDno.length>1){
				Ext.apply(dateStore.baseParams, { 
					filter_dno:searchDno
				});
			}else{
	 			Ext.apply(dateStore.baseParams, { 
						filter_isOpr:searchIsOpr,
						filter_flightMainNo:searchFlightMainNo,
						filter_GED_flight_date:'',
						filter_LED_flight_date:'',
						filter_GED_fax_create_time:'',
						filter_LED_fax_create_time:'',
						itemsValue:'',
						itemsValue:''
				});
				if(checkItems.indexOf('TIME')==0){
				 var searchTime = checkItems.substring(checkItems.indexOf('_')+1);
				 if(searchTime=='flight_date'){
				 	Ext.apply(dateStore.baseParams, {
				 		filter_GED_flight_date:(!Ext.getCmp("startDate").hidden?(Ext.get("startDate").dom.value=="开始时间"?"":Ext.get("startDate").dom.value):""),
				 		filter_LED_flight_date:(!Ext.getCmp("endDate").hidden?(Ext.get("endDate").dom.value=="结束时间"?"":Ext.get("endDate").dom.value):"")
				 	})
				 }else if(searchTime=='fax_create_time'){
				 	Ext.apply(dateStore.baseParams, {
				 		filter_GED_fax_create_time:(!Ext.getCmp("startDate").hidden?(Ext.get("startDate").dom.value=="开始时间"?"":Ext.get("startDate").dom.value):""),
				 		filter_LED_fax_create_time:(!Ext.getCmp("endDate").hidden?(Ext.get("endDate").dom.value=="结束时间"?"":Ext.get("endDate").dom.value):"")
				 	})
				 }
	    	     }else{
		 			Ext.apply(dateStore.baseParams, {
							checkItems : Ext.get("checkItems").dom.value,
							itemsValue : Ext.get("searchContent").dom.value
	    			});
	    		}
    		}
	 			
		dataReload();
	}

 function dataReload(){
			dateStore.reload({
			params : {
				privilege:privilege,
				limit : pageSize
			}
		});
		
 }

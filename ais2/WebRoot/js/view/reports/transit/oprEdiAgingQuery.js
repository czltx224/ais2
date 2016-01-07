//EDI时效查询js
var privilege=91;//权限参数  未定
var gridSearchUrl="reports/transitCountAction!findEdiAgingQuery.action";//查询地址
var gridSearchTotalUrl="reports/transitCountAction!findEdiAgingQueryTotal.action";//查询合计地址

var searchCusNameUrl='fi/basTraShipmentRateAction!findShipmentCustomer.action';//查询中转和外发的代理公司地址
var authorityDepartUrl='sys/basRightDepartAction!findDepartName.action';//权限部门查询地址
var gridCustomerUrl="sys/customerAction!list.action";//客商查询地址
var searchWidth=70;
var defaultWidth=80;
var pubauthorityDepart=bussDepart;
var  fields=[{name:"GOWHERE",mapping:'GOWHERE'},
     		{name:"GOWHERE_ID",mapping:'GOWHERE_ID'},
     		{name:'PIECE',mapping:'PIECE'},
     		{name:'CUS_WEIGHT',mapping:'CUS_WEIGHT'},
     		{name:'BULK',mapping:'BULK'},
     		'OK_FLAG','D_NO','OUT_TIME','REACH_TIME','CAROUT_TIME',
     		'SIGN_TIME','TRANSITHOUR','OUTSTOCKHOUR','SIGNHOUR','CP_NAME',
     		'IN_DEPART','IN_DEPART_ID','TRANSIT_HOUR','CAROUT_HOUR','SIGN_HOUR',
     		'TRANSITSTANDARD','CAROUTSTANDARD','SIGNSTANDARD'];
	
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
        
        //中转客商名称
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
		//代理公司
		cusNameStore= new Ext.data.Store({ 
			storeId:"cusNameStore",
			baseParams:{filter_EQS_custprop:'发货代理'},
			proxy:new Ext.data.HttpProxy({url:sysPath+"/"+gridCustomerUrl}),
			reader:new Ext.data.JsonReader({
	                  root:'result', totalProperty:'totalCount'
	        },[
        	{name:'cusName',mapping:'cusName'},
        	{name:'cusId',mapping:'id'}
        	])
		});
		//是否达标
		standardStore=new Ext.data.SimpleStore({
			 autoLoad:true, //此处设置为自动加载
  			  data:[
  			  ['','全部'],
  			  ['否','否'],
  			  ['是','是']],
   			 fields:["standardId","standardName"]
		});
	var tbar=[{text:'<b>导出</b>',iconCls:'sort_down',
			handler:function() {
                	parent.exportExl(ediAgingQueryGrid);
        } }, '-','时间选择:',{
			xtype : "combo",
			width : defaultWidth+20,
			triggerAction : 'all',
			forceSelection : true,
			model : 'local',
			hiddenId : 'mycheckItems',
			hiddenName : 'mycheckItems',
			store : [
					['s.out_time', '中转日期'],
					['d.ok_flag_createtime', '点到日期'],
					['o.create_time', '出库日期'],
					['n.create_time', '签收日期']],
			listeners: {   
				afterRender: function(combo) {   
		       　　combo.setValue('s.out_time');
				}
			},
			emptyText : '选择类型'
								
			},'-',
	 		{
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
    			   var start = Ext.getCmp('startDate').getValue().format("Y-m-d");
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
    	},'-','中转公司:',{xtype : "combo",
				width : searchWidth+20,
				editable:true,
				triggerAction : 'all',
				listWidth:120,
				// typeAhead : true,
				queryParam : 'filter_LIKES_cusName',
				pageSize : comboSize,
				forceSelection : true,
				resizable:true,
				minChars : 0,
				store : cpNameStore,
				resizable:true,
				forceSelection : true,
				valueField : 'cusId',//value值，与fields对应
				displayField : 'cusName',//显示值，与fields对应
			    id:'searchCusName', 
			    enableKeyEvents:true,listeners : {
	 		   		 keyup:function(textField, e){
                    	 if(e.getKey() == 13){
                     		searchediAgingQuery();
                     	}
	 				}
	 			}
	 		},'-','部门名称:',{xtype : "combo",
				width : searchWidth+20,
				editable:true,
				triggerAction : 'all',
				// typeAhead : true,
				queryParam : 'filter_LIKES_departName',
				pageSize : comboSize,
				forceSelection : true,
				resizable:true,
				listWidth:120,
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
                     	searchediAgingQuery();
                     }
	 		}
	 	
	 	}}
	 	,'-',{text : '<b>查询</b>',id : 'btn',iconCls : 'btnSearch',
   					handler : searchediAgingQuery
   		}
	];
var twoBar = new Ext.Toolbar([
	'配送单号:',{
		xtype:'numberfield',
		id:'searchDno',
		width:searchWidth,
		enableKeyEvents:true,listeners : {
	 		keyup:function(textField, e){
                if(e.getKey() == 13){
                	searchediAgingQuery();
                }
	 		}
	 	}
	},'-','代理公司:',{
				xtype:'combo',
				width : searchWidth+20,
				editable:true,
				triggerAction : 'all',
				// typeAhead : true,
				queryParam : 'filter_LIKES_cusName',
				pageSize : comboSize,
				// forceSelection : true,
				resizable:true,
				minChars : 0,
				store : cusNameStore,
				mode : "remote",// 从本地载值
				valueField : 'cusId',// value值，与fields对应
				displayField : 'cusName',// 显示值，与fields对应
			    id:'searchCustomerName', 
			    enableKeyEvents:true,
			    listeners : {
	 		    	keyup:function(textField, e){
	                     if(e.getKey() == 13){
	                     	searchediAgingQuery();
	                     }
	 				}
	 			}
		},'-','点到是否达标：', {

			xtype : "combo",
			width : searchWidth+20,
			id : 'searchTransitStandard',
			typeAhead : true,
			editable:true,
			forceSelection : true,
			selectOnFocus : true,
			resizable : true,
			triggerAction : 'all',
			store : standardStore,
			mode : "local",// 从本地载值
			valueField : 'standardId',// value值，与fields对应
			displayField : 'standardName',// 显示值，与fields对应
			 enableKeyEvents:true,listeners : {
	 		    keyup:function(textField, e){
                     if(e.getKey() == 13){
                     	searchediAgingQuery();
                     }
	 			}
	 		}
		},'-','出库是否达标：', {

			xtype : "combo",
			width : searchWidth+20,
			id : 'searchCarOutStandard',
			typeAhead : true,
			editable:true,
			forceSelection : true,
			selectOnFocus : true,
			resizable : true,
			triggerAction : 'all',
			store : standardStore,
			mode : "local",// 从本地载值
			valueField : 'standardId',// value值，与fields对应
			displayField : 'standardName',// 显示值，与fields对应
			 enableKeyEvents:true,listeners : {
	 		    keyup:function(textField, e){
                     if(e.getKey() == 13){
                     	searchediAgingQuery();
                     }
	 			}
	 		}
		},'-','签收是否达标：', {

			xtype : "combo",
			width : searchWidth+20,
			id : 'searchSignStandard',
			typeAhead : true,
			editable:true,
			forceSelection : true,
			selectOnFocus : true,
			resizable : true,
			triggerAction : 'all',
			store : standardStore,
			mode : "local",// 从本地载值
			valueField : 'standardId',// value值，与fields对应
			displayField : 'standardName',// 显示值，与fields对应
			name : 'dispatchGroup',
			 enableKeyEvents:true,listeners : {
	 		    keyup:function(textField, e){
                     if(e.getKey() == 13){
                     	searchediAgingQuery();
                     }
	 			}
	 		}
		}
]);		
var summary = new Ext.ux.grid.GridSummary();	 	
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
    ediAgingQueryGrid = new Ext.grid.GridPanel({
        renderTo: Ext.getBody(),
        id:'ediAgingQueryCenter',
        height:Ext.lib.Dom.getViewHeight(),		
        width:Ext.lib.Dom.getViewWidth(),
        viewConfig : {
			columnsText : "显示的列",
			sortAscText : "升序",
			sortDescText : "降序"
		},
		autoScroll:false, 
        frame:true, 
        loadMask:true,
        plugins : [summary], // 合计
        sm:sm,
        cm:new Ext.grid.ColumnModel([rowNum,sm,
        		{header: '中转公司ID', dataIndex: 'GOWHERE_ID',sortable:true,hidden:true,width:defaultWidth},
       			{header: '录单部门ID', dataIndex: 'IN_DEPART_ID',sortable : true,width:defaultWidth,hidden:true},
       			{header: '录单部门', dataIndex: 'IN_DEPART',sortable : true,width:defaultWidth},
       			{header: '配送单号', dataIndex: 'D_NO',sortable : true,width:defaultWidth},
 				{header: '代理公司', dataIndex: 'CP_NAME',sortable : true,width:defaultWidth},
 				{header: '中转公司', dataIndex: 'GOWHERE',sortable : true,width:defaultWidth},
        		{header: '件数', dataIndex: 'PIECE',sortable : true,width:defaultWidth},
        		{header: '重量(KG)', dataIndex: 'CUS_WEIGHT',sortable : true,width:defaultWidth},
 				{header: '体积(方)', dataIndex: 'BULK',sortable : true,width:defaultWidth,hidden:true},
 				{header: '中转是否达标', dataIndex: 'TRANSITSTANDARD',sortable : true,width:defaultWidth
 					,renderer:function renderDescn(v, cellmeta, record, rowIndex, columnIndex, store){
						if(v=='是'){
							cellmeta.css = 'x-grid-back-green';
						}else{
							cellmeta.css = 'x-grid-back-red';
						}
						return v;
					}
 				},
 				{header: '出库是否达标', dataIndex: 'CAROUTSTANDARD',sortable : true,width:defaultWidth
					,renderer:function renderDescn(v, cellmeta, record, rowIndex, columnIndex, store){
						if(v=='是'){
							cellmeta.css = 'x-grid-back-green';
						}else{
							cellmeta.css = 'x-grid-back-red';
						}
						return v;
					} 				
 				},
 				{header: '签收是否达标', dataIndex: 'SIGNSTANDARD',sortable : true,width:defaultWidth
 					,renderer:function renderDescn(v, cellmeta, record, rowIndex, columnIndex, store){
						if(v=='是'){
							cellmeta.css = 'x-grid-back-green';
						}else{
							cellmeta.css = 'x-grid-back-red';
						}
						return v;
					}
 				},
 				{header: '中转时间', dataIndex: 'OUT_TIME',sortable : true,width:defaultWidth*1.2},
 				{header: '中转点到时间', dataIndex: 'REACH_TIME',sortable : true,width:defaultWidth*1.2},
 				{header: '中转出库时间', dataIndex: 'CAROUT_TIME',sortable : true,width:defaultWidth*1.2},
 				{header: '中转签收时间', dataIndex: 'SIGN_TIME',sortable : true,width:defaultWidth*1.2},
 				{header: '点到用时', dataIndex: 'TRANSITHOUR',sortable : true,width:defaultWidth},
 				{header: '点到标准用时', dataIndex: 'TRANSIT_HOUR',sortable : true,width:defaultWidth},
 				{header: '出库用时', dataIndex: 'OUTSTOCKHOUR',sortable : true,width:defaultWidth},
 				{header: '出库标准用时', dataIndex: 'OUTSTOCK_HOUR',sortable : true,width:defaultWidth},
 				{header: '签收用时', dataIndex: 'SIGNHOUR',sortable : true,width:defaultWidth},
 				{header: '签收标准用时', dataIndex: 'SIGN_HOUR',sortable : true,width:defaultWidth}
        ]),
        store:dateStore,
        tbar: tbar,
        listeners : {
			render : function() {
				twoBar.render(this.tbar);
			},afterlayout:function(){
                if(c_gowhereId!=null && c_gowhereId.length>0){
				    Ext.apply(dateStore.baseParams, {
	   					filter_f_gowhereId:c_gowhereId,
						filter_countCheckItems:c_countCheckItems,
						filter_startCount:c_startDate,
						filter_endCount:c_endDate,
						filter_f_inDepartId:c_curBussDepart
	   				});
					dataReload();
					fnSumInfo();
				}
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
    });
    
    /**
		 * 汇总信息
		 */
		function fnSumInfo() {
			Ext.Ajax.request({
				url : sysPath+'/'+gridSearchTotalUrl,
				params:dateStore.baseParams,
				success : function(response) {
					summary.toggleSummary(true);
					summary.setSumValue(Ext.decode(response.responseText));
				},
				failure : function(response) {
					Ext.MessageBox.alert(alertTitle, '汇总数据失败');
				}
			});
		}
	
   function searchediAgingQuery() {
   
		dateStore.baseParams={privilege:privilege,limit : pageSize};
		
		var authorityDepartId=Ext.getCmp('authorityDepartId').getValue();
		if(null!=authorityDepartId && authorityDepartId>0){
			pubauthorityDepart=authorityDepartId;
		}else{
			pubauthorityDepart=bussDepart;
		}
		var startCount = Ext.get('startDate').dom.value;
		var endCount = Ext.get('endDate').dom.value;
		var cusId = Ext.getCmp('searchCusName').getValue();
		
		var searchDno = Ext.get('searchDno').dom.value;
		var searchCustomerName = Ext.get('searchCustomerName').dom.value;
		
		var mycheckItems = Ext.get('mycheckItems').dom.value;
		var searchTransitStandard = Ext.getCmp('searchTransitStandard').getValue();
		var searchCarOutStandard = Ext.getCmp('searchCarOutStandard').getValue();
		var searchSignStandard = Ext.getCmp('searchSignStandard').getValue();

		Ext.apply(dateStore.baseParams, {
				filter_transitStandard:searchTransitStandard,
				filter_carOutStandard:searchCarOutStandard,
				filter_signStandard:searchSignStandard,
				filter_countCheckItems:mycheckItems,
				filter_f_gowhereId:cusId,
				filter_f_dNo:searchDno,
				filter_LIKES_f_cpName:searchCustomerName,
		 	 	filter_startCount:startCount,
		 	 	filter_endCount:endCount,
				filter_f_inDepartId:pubauthorityDepart,
				privilege:privilege,
				limit : pageSize
		});
		
		dataReload();
		fnSumInfo();
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

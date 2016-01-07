//中转汇总统计js
var privilege=91;//权限参数  未定
var gridSearchUrl="reports/transitCountAction!findTransitCount.action";//查询地址
var gridSearchTotalUrl="reports/transitCountAction!findTransitCountTotal.action";//查询合计地址

var gridDetailUrl="reports/transitCountAction!toTransitDetail.action";//中转明细页面跳转地址
var searchCusNameUrl='fi/basTraShipmentRateAction!findShipmentCustomer.action';//查询中转和外发的代理公司地址
var authorityDepartUrl='sys/basRightDepartAction!findDepartName.action';//权限部门查询地址
var searchWidth=70;
var defaultWidth=90;
var pubauthorityDepart=bussDepart;
var  fields=[{name:"GOWHERE",mapping:'GOWHERE'},
     		{name:"GOWHERE_ID",mapping:'GOWHERE_ID'},
     		{name:'TOTALTICKET',mapping:'TOTALTICKET'},
     		{name:'TOTALPIECE',mapping:'TOTALPIECE'},
     		{name:'TOTALWEIGHT',mapping:'TOTALWEIGHT'},
     		{name:'YUFU',mapping:'YUFU'},
     		{name:'DAOFU',mapping:'DAOFU'},
     		{name:'PAYMENT_COLLECTION',mapping:'PAYMENT_COLLECTION'},
     		{name:'TRA_FEE',mapping:'TRA_FEE'},'PREKGCOST','NOTCONFIRMNUM','NOTSETTLEAMOUNT','NOTPAYAMOUNT'];
	
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
	var tbar=[{text:'<b>导出</b>',iconCls:'sort_down',
			handler:function() {
                	parent.exportExl(transitCountGrid);
        } },{text:'<b>查看明细</b>',iconCls:'searchDetail',
				handler:function() {
                	searchDetail();
        		} 
        }, '-','中转日期:',
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
                     		searchtransitCount();
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
                     	searchtransitCount();
                     }
	 		}
	 	
	 	}}
	 	,'-',{text : '<b>查询</b>',id : 'btn',iconCls : 'btnSearch',
   					handler : searchtransitCount
   		}
	];		
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
    transitCountGrid = new Ext.grid.GridPanel({
        renderTo: Ext.getBody(),
        id:'transitCountCenter',
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
 				{header: '中转公司', dataIndex: 'GOWHERE',sortable : true,width:defaultWidth},
       			{header: '总票数', dataIndex: 'TOTALTICKET',sortable : true,width:defaultWidth},
        		{header: '总件数', dataIndex: 'TOTALPIECE',sortable : true,width:defaultWidth},
        		{header: '总重量', dataIndex: 'TOTALWEIGHT',sortable : true,width:defaultWidth},
 				{header: '预付金额(元)', dataIndex: 'YUFU',sortable : true,width:defaultWidth},
 				{header: '提送费(元)', dataIndex: 'DAOFU',sortable : true,width:defaultWidth},
 				{header: '代收货款(元)', dataIndex: 'PAYMENT_COLLECTION',sortable : true,width:defaultWidth},
 				{header: '到付金额(元)', dataIndex: 'DAOFU',sortable : true,width:defaultWidth},
 				{header: '总成本(元)', dataIndex: 'TRA_FEE',sortable : true,width:defaultWidth},
 				{header: '每KG成本(元)', dataIndex: 'PREKGCOST',sortable : true,width:defaultWidth},
 				{header: '未返回单数', dataIndex: 'NOTCONFIRMNUM',sortable : true,width:defaultWidth},
 				{header: '未结到付余额(元)', dataIndex: 'NOTSETTLEAMOUNT',sortable : true,width:defaultWidth},
 				{header: '未付成本余额(元)', dataIndex: 'NOTPAYAMOUNT',sortable : true,width:defaultWidth}
        ]),
        store:dateStore,
        tbar: tbar,
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
    
    //添加双击事件，双击查看明细
    transitCountGrid.on('rowdblclick',function(grid,index,e){
		searchDetail();
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
	
   function searchtransitCount() {
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
		// alert(cusId);
		Ext.apply(dateStore.baseParams, {
				filter_countCheckItems:"s.out_time",//出库日期
				filter_f_gowhereId:cusId,
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
 function searchDetail(){
 	var _records = transitCountGrid.getSelectionModel().getSelections();
	if (_records.length < 1) {
		Ext.Msg.alert(alertTitle, "请选择您查看的信息！");
		return false;
	} else if (_records.length > 1) {
		Ext.Msg.alert(alertTitle, "一次只能查看一条信息！");
		return false;
	}
	var startCount = Ext.get('startDate').dom.value;
	var endCount = Ext.get('endDate').dom.value;
	var gowhereId = _records[0].get('GOWHERE_ID');
	
	var node=new Ext.tree.TreeNode({leaf :false,text:'中转明细查询'});
    node.attributes={href1:gridDetailUrl+'?gowhereId='+gowhereId
    									+'&curBussDepart='+pubauthorityDepart
    									+'&startDate='+startCount
    									+'&endDate='+endCount
    									+'&countCheckItems='+'s.out_time'};
    parent.toAddTabPage(node,true);
 }

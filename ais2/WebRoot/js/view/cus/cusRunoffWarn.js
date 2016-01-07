var operTitle;
var privilege=158;
var fields=[
			{name:'cusName',mapping:'CUS_NAME'},
            {name: 'id',mapping:'ID'},
            {name: 'lastBuss',mapping:'LAST_BUSS'},
            {name: 'lastCommunicate',mapping:'LAST_COMMUNICATE'},
            {name: 'warnDeliveryCycle',mapping:'WARN_DELIVERY_CYCLE'},
            {name:'serviceName',mapping:'SERVICE_NAME'},
            {name:'noDiliverDay',mapping:'NO_DILIVER_DAY'},
            {name:'noDevelopDay',mapping:'NO_DEVELOP_DAY'}
];
Ext.onReady(function() {
    var menuStore = new Ext.data.Store({
        storeId:"menuStore",
        baseParams:{limit:pageSize,privilege:privilege,filter_EQL_cusRecordId:parent.cusRecordId},
        proxy: new Ext.data.HttpProxy({url:sysPath+"/cus/cusRecordAction!findWarn.action"}),
        reader: new Ext.data.JsonReader({
            root: 'resultMap', totalProperty: 'totalCount'
        }, fields)
    });
    var cusRecordStore=new Ext.data.Store({
		storeId:'cusRecordStore',
		baseParams:{limit:pageSize,filter_EQL_departId:bussDepart},
		proxy:new Ext.data.HttpProxy({url:sysPath+"/cus/cusRecordAction!list.action"}),
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
        	{name:'id'},
        	{name:'cusName'}
        	])
	});
    //start
    var sm = new Ext.grid.CheckboxSelectionModel({});
    var rowNum = new Ext.grid.RowNumberer({
        header:'序号', width:35, sortable:true
    });
    
    var menuGrid = new Ext.grid.GridPanel({
        renderTo:'showView',
    	id : 'myrecordGrid',
    	height : Ext.lib.Dom.getViewHeight(),
    	width : Ext.lib.Dom.getViewWidth()-1,
    	frame : false,
    	loadMask : true,
    	stripeRows : true,
    	viewConfig:{
    	   scrollOffset: 0,
    	   autoScroll : true
    	},
        sm:sm,
        cm:new Ext.grid.ColumnModel([rowNum,sm,
            {header:'客户ID',dataIndex:"id",hidden: true, hideable: false},
            {header:'客户名称',dataIndex:"cusName",width:80},
            {header:'最后发货时间',dataIndex:"lastBuss",width:80},
            {header:"最后活动时间",dataIndex:"lastCommunicate",width:80},
            {header:"未发货天数",dataIndex:"noDiliverDay",width:80,sortable:true},
            {header:"未活动天数",dataIndex:"noDevelopDay",width:80,sortable:true},
            {header:"预警周期",dataIndex:"warnDeliveryCycle",width:80},
            {header:"负责客服员",dataIndex:"serviceName",width:80}
        ]),
        store:menuStore,
        tbar: ['&nbsp;','客户:',
            {
            	xtype : 'combo',
            	triggerAction : 'all',
            	forceSelection : true,
            	pageSize:pageSize,
            	id:'searcuscombo',
    			model : 'local',
    			hiddenName : 'cusName',
            	store: cusRecordStore,
            	valueField:'id',
            	displayField:'cusName',
    			width : 100
            },
            '-','客服员:',{
            	xtype:'textfield',
            	id:'cusService',
            	width:100,
            	name:'cusService'
            },'-',
            {
            	text:'<B>搜索</B>',
            	id:'btn', 
            	iconCls: 'btnSearch',
            	hidden:false,
            	handler : searchCusRequest
            }
        ],
        bbar: new Ext.PagingToolbar({
            pageSize: pageSize,
            privilege:privilege, 
            store: menuStore, 
            displayInfo: true,
            displayMsg: '显示第 {0} 条到 {1} 条记录，共 {2} 条', emptyMsg: "没有记录信息显示"
        })
    });
    function searchCusRequest() {
    	menuStore.baseParams ={privilege:privilege,limit : pageSize,start:0};
    	var cusId=Ext.getCmp('searcuscombo').getValue();
    	var cusService = Ext.getCmp('cusService').getValue();
    	Ext.apply();
		menuStoreReload(menuStore.baseParams,{
			cusRecordId:cusId,
			cusService:cusService
		});
	}
	function menuStoreReload(){
		menuStore.reload({
			params:{
				privilege:privilege,
				start : 0,
				limit : pageSize
			}
		});
	}
    //end
});
//财务内部结算报表js
var searchMapUrl='fi/fiInternalDetailAction!reportList.action';
var departUrl='sys/departAction!findAll.action';//部门查询地址
var gridCustomerUrl='sys/customerAction!ralaList.action';//查询客商地址
var dictionaryUrl='sys/dictionaryAction!ralaList.action';//数据字典地址
var privilege=168;
var colWidth=80;
var searchWidth=80;
var fields=[
{name:'create_time',mapping:'CREATE_TIME'},
{name:'d_no',mapping:'D_NO'},
{name:'SETTLEMENT_TYPE',mapping:'SETTLEMENT_TYPE'},
{name:'shouru',mapping:'SHOURU'},
 {name:'zhichu',mapping:'ZHICHU'},
 {name:'distribution_mode',mapping:'DISTRIBUTION_MODE'},
 {name:'belongs_depart_id',mapping:'BELONGS_DEPART_ID'},
 {name:'belongs_depart_name',mapping:'BELONGS_DEPART_NAME'},
  {name:'weight',mapping:'WEIGHT'},
  {name:'piece',mapping:'PIECE'},
  {name:'bulk',mapping:'BULK'},
  {name:'take_mode',mapping:'TAKE_MODE'},
  {name:'area_type',mapping:'AREA_TYPE'},
  {name:'consignee',mapping:'CONSIGNEE'},
  {name:'cp_name',mapping:'CP_NAME'}
];
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
  			  data:[['','-全部-'],['1','正常'],['0','作废']],
   			 fields:["statusId","statusName"]
		});
		//发货代理
		cpNameStore= new Ext.data.Store({ 
			storeId:"cpNameStore",
			baseParams:{filter_EQS_custprop:'发货代理'},
			proxy:new Ext.data.HttpProxy({url:sysPath+"/"+gridCustomerUrl}),
			reader:new Ext.data.JsonReader({
	                  root:'result', totalProperty:'totalCount'
	        },[
        	{name:'cusName',mapping:'cusName'},
        	{name:'cusId',mapping:'id'}
        	])
		});
	
	var tbar=[
	 	{text:'<b>导出</b>',iconCls:'sort_down',tooltip : '导出内部结算信息',handler:function() {
                	parent.exportExl(fiInternalReportGrid);
        } },'-',
        {text:'<b>打印</b>',iconCls:'printBtn',tooltip : '打印内部结算信息',handler:function() {
            	alert('功能待实现..');
        } },'-',
        {text:'<b>统计</b>',iconCls:'btnSearch',tooltip : '查询内部结算信息',handler:function() {
            	searchfiInternalReport();
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
    	},'-','所属部门:',{xtype : "combo",
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
			id:'searchBelongsDepartId',
		    enableKeyEvents:true,
		    listeners : {
			   keyup:function(combo, e){
		             if(e.getKey() == 13){
		             	searchfiInternalReport();
		             }
	 			}
		
		}},
		'-','配送单号:',{
	 		xtype:'numberfield',
	 		width:searchWidth,
	 		id:'searchDno',
	 		enableKeyEvents:true,
			listeners : {
	 			keyup:function(textField, e){
		             if(e.getKey() == 13){
		             	searchfiInternalReport();
		             }
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
		           searchfiInternalReport();
	 			}
			}
	 	}
	 	]);
	 	
Ext.onReady(function() {
   	dateStore = new Ext.data.Store({
        storeId:"dateStore",
        proxy: new Ext.data.HttpProxy({url : sysPath+ "/"+searchMapUrl}),
        baseParams:{privilege:privilege,limit : pageSize},
        reader: new Ext.data.JsonReader({
            root: 'resultMap', totalProperty: 'totalCount'
        }, fields)
    });
   
   var sm = new Ext.grid.CheckboxSelectionModel({});
	var rowNum = new Ext.grid.RowNumberer({
        header:'序号', width:30
    });
    fiInternalReportGrid = new Ext.grid.GridPanel({
        renderTo: Ext.getBody(),
        id:'fiInternalReportCenter',
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
            {header:'创建时间',dataIndex:"create_time",width:colWidth,sortable:true},
            {header:'配送单号',dataIndex:"d_no",width:colWidth,sortable:true},
            {header:'所属部门',dataIndex:"belongs_depart_name",width:colWidth,sortable:true},
            {header:'发货代理',dataIndex:"cp_name",width:colWidth,sortable:true},
            {header:'件数',dataIndex:'piece',width:colWidth,sortable:true},
			{header:'重量',dataIndex:'weight',width:colWidth,sortable:true},
			{header:'体积',dataIndex:'bulk',width:colWidth,sortable:true},
			{header:'收入',dataIndex:'shouru',width:colWidth,sortable:true},
			{header:'成本',dataIndex:'zhichu',width:colWidth,sortable:true},
			{header:'配送方式',dataIndex:'distribution_mode',width:colWidth,sortable:true},
			{header:'提货方式',dataIndex:'take_mode',width:colWidth,sortable:true},
			{header:'地址类型',dataIndex:'area_type',width:colWidth,sortable:true},
			{header:'收货人姓名',dataIndex:'consignee',width:colWidth,sortable:true}
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
  });
    
    
	
 function searchfiInternalReport() {
 	dateStore.proxy = new Ext.data.HttpProxy({
				method : 'POST',
				url : sysPath + "/" + searchMapUrl
				
			});
	//var dispatchGroup=Ext.getCmp('dispatchGroup').getValue();
	var searchBelongsDepartId = Ext.getCmp('searchBelongsDepartId').getValue();
	var searchStatus = Ext.getCmp('searchStatus').getValue();
	var searchDno = Ext.getCmp('searchDno').getValue();
	
 	Ext.apply(dateStore.baseParams, {
 		filter_status:searchStatus,
 		filter_belongsDepartId:searchBelongsDepartId,
 		limit:pageSize,
 		filter_dno:searchDno
 		//filter_cpName:searchCpName
 	});
	dataReload();
}


 function dataReload(){
			dateStore.reload({
			params : {
				start : 0,
				limit:pageSize
				}
			})
		}
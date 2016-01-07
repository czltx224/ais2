var operTitle;
var defaultWidth=60;
var thanField=[
	{name:''}
];
var menuField=[
	{name:'cusDepartCode',mapping:'CUS_DEPART_CODE'},
	{name:'cusDepartName',mapping:'CUS_DEPART_NAME'},
	{name:'largeCusCount',mapping:'LARGE_CUS_COUNT'},
	{name:'largeCusIncome',mapping:'LARGE_CUS_INCOME'},
	{name:'impCusCount',mapping:'IMP_CUS_COUNT'},
	{name:'impCusIncome',mapping:'IMP_CUS_INCOME'},
	{name:'offenCusCount',mapping:'OFFEN_CUS_COUNT'},
	{name:'offenCusIncome',mapping:'OFFEN_CUS_INCOME'},
	{name:'disCusCount',mapping:'DIS_CUS_COUNT'},
	{name:'disCusIncome',mapping:'DIS_CUS_INCOME'},
	{name:'sumCount',mapping:'SUM_COUNT'},
	{name:'sumIncome',mapping:'SUM_INCOME'}
];
var rankMonField=[
	{name:'importanceLevel',mapping:'IMPORTANCE_LEVEL'},
	{name:'1月'},
	{name:'2月'},
	{name:'3月'},
	{name:'4月'},
	{name:'5月'},
	{name:'6月'},
	{name:'7月'},
	{name:'8月'},
	{name:'9月'},
	{name:'10月'},
	{name:'11月'},
	{name:'12月'}
];
var cusvscusField=[
	{},
	{}
];
var menuStore,departStore,rankMonStore,loopthanPanel,cusvscusPanel,cusvscusStore,countRangeStore,cusRecordStore;
Ext.onReady(function() {
    //月度统计分析Store
    menuStore = new Ext.data.Store({
        storeId:"menuStore",
        baseParams:{limit:pageSize},
        proxy: new Ext.data.HttpProxy({url:sysPath+"/cus/cusAnalyseAction!findCusRankMsg.action"}),
        reader: new Ext.data.JsonReader({
        	root: 'resultMap', totalProperty: 'totalCount'
        }, menuField)
    });
    countRangeStore	= new Ext.data.Store({ 
			autoLoad:true, 
			storeId:"countRangeStore",
			baseParams:{filter_EQL_basDictionaryId:201,privilege:16},
			proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/dictionaryAction!ralaList.action"}),
			reader:new Ext.data.JsonReader({
	                    root:'result',
	                    totalProperty:'totalCount'
	        },[
        	{name:'countRangeId',mapping:'typeCode'},
        	{name:'countRangeName',mapping:'typeName'}
        	])
		});
    rankMonStore=new Ext.data.Store({
        storeId:"rankMonStore",
        baseParams:{limit:pageSize},
        proxy: new Ext.data.HttpProxy({url:sysPath+"/cus/cusAnalyseAction!findCusRankMonMsg.action"}),
        reader: new Ext.data.JsonReader({
        	root: 'resultMap', totalProperty: 'totalCount'
        }, rankMonField)
    });
    //权限部门
	departStore = new Ext.data.Store({ 
            storeId:"menuStore",                        
            proxy: new Ext.data.HttpProxy({url:sysPath+"/sys/departAction!ralaList.action",method:'post'}),
            baseParams:{privilege:53},
            reader: new Ext.data.JsonReader({
            root: 'result', totalProperty:'totalCount'
           }, [  
                 {name:'departName', mapping:'departName'},    
                 {name:'departId', mapping: 'departId'}
              ])                                      
     });
     cusRecordStore=new Ext.data.Store({
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
    //环比/同比Store
    loopthanStore=new Ext.data.Store({
        storeId:"loopthanStore",
        baseParams:{limit:pageSize},
        proxy: new Ext.data.HttpProxy({url:sysPath+"/cus/cusAnalyseAction!findCusRankThan.action"}),
        reader: new Ext.data.JsonReader({
        }, thanField)
    });
    //客服员客户分析
    cusvscusStore=new Ext.data.Store({
        storeId:"cusvscusStore",
        remoteSort :true,
        baseParams:{limit:pageSize},
        proxy: new Ext.data.HttpProxy({url:sysPath+"/cus/cusAnalyseAction!findCusVsCus.action"}),
        reader: new Ext.data.JsonReader({
        }, cusvscusField)
    });
    //重要程度Store
	var importanceLevelStore=new Ext.data.Store({
			storeId:'importanceLevelStore',
			baseParams:{filter_EQL_basDictionaryId:35,privilege:16},
			proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/dictionaryAction!ralaList.action"}),
			reader:new Ext.data.JsonReader({
	                    root:'result',
	                    totalProperty:'totalCount'
	        },[
	        	{name:'id'},
	        	{name:'importanceLevel',mapping:'typeName'}
	        	])
	});
    var sm = new Ext.grid.CheckboxSelectionModel({});
    var rowNum = new Ext.grid.RowNumberer({
        header:'序号', width:35, sortable:true
    });
    var continentGroupRow=[
    	{ header:'',align: 'center',colspan: 2},
    	{ header:'大客户',align: 'center',colspan: 2},
    	{ header:'重点客户',align: 'center',colspan: 2},
    	{ header:'常客',align: 'center',colspan: 2},
    	{ header:'散客',align: 'center',colspan: 2},
    	{ header:'合计',align: 'center',colspan: 2}
    ];
    var group = new Ext.ux.grid.ColumnHeaderGroup({
        rows: [continentGroupRow]
    });
    /*
	//各部门增值收入情况Panel
	departFeePanel=new Ext.grid.GridPanel({
       //	renderTo:Ext.getBody(),
		region:'center',
    	id : 'departFeePanel',
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
        cm:new Ext.grid.ColumnModel([rowNum,
        	{header:'部门编码',dataIndex:"cusDepartCode",hidden: true, hideable: false},
            {header:'部门名称',dataIndex:"cusDepartName"},
           // {header:'燃油附加费',dataIndex:'fatFee'},
            ///{header:'进仓费',dataIndex:'instoreFee'},
           // {header:'搬运费',dataIndex:"carryFee"}, 
           ///{header:'仓储费',dataIndex:"storageFee"},
           // {header:'上楼费',dataIndex:"comeupFee"},
            {header:'增值收入合计	',dataIndex:"addsumFee"},
            {header:'营业额',dataIndex:"income"},
            {header:'增值收入占比',dataIndex:"feeRate"}
        ]),
        store:departFeeStore,         
        tbar: [
        	'时间维度:',{
        		xtype : 'combo',
    			id:'dateType',
    			value:'月',
    			name : 'dateType',
    			forceSelection : true,
				triggerAction : 'all',
				model : 'local',
				store:[
					['月','月'],
					['日','日']
				],
    			width : 100,
        		allowBlank:false,
    			blankText:'统计时间维度不能为空！'
        	},'-','时间:',
            {
            	xtype : 'datefield',
    			id:'wholeDate',
    			value:new Date(),
    			format:'Y-m-d',
    			name : 'wholeDate',
    			forceSelection : true,
				triggerAction : 'all',
				model : 'local',
    			width : 100,
    			allowBlank:false,
    			blankText:'日期不能为空！'
            },
            '-',
            {
            	text:'<B>统计</B>',
            	id:'btnSearch', 
            	iconCls: 'btnSearch',
            	hidden:false,
            	handler : searchCusRequest
            }
        ]
    });*/
    //客户等级月度分析Grid
     menuGrid = new Ext.grid.GridPanel({
    	id : 'myrecordGrid',
    	region:'north',
    	height : Ext.lib.Dom.getViewHeight()/2-20,
    	width : Ext.lib.Dom.getViewWidth()-5,
    	frame : false,
    	loadMask : true,
    	stripeRows : true,
    	plugins: group,
    	viewConfig:{
    		scrollOffset: 0,
    		autoScroll : true
    	},
        sm:sm,
        cm:new Ext.grid.ColumnModel([rowNum,
            {header:'部门名称',dataIndex:'cusDepartName'},
            {header:'客户数',dataIndex:'largeCusCount',xtype: 'numbercolumn',format:'0,000'},
            {header:'收入',dataIndex:'largeCusIncome',xtype: 'numbercolumn',format:'0,000'},
            {header:'客户数',dataIndex:'impCusCount',xtype: 'numbercolumn',format:'0,000'},
            {header:'收入',dataIndex:'impCusIncome',xtype: 'numbercolumn',format:'0,000'},
            {header:'客户数',dataIndex:'offenCusCount',xtype: 'numbercolumn',format:'0,000'},
            {header:'收入',dataIndex:'offenCusIncome',xtype: 'numbercolumn',format:'0,000'},
            {header:'客户数',dataIndex:'disCusCount',xtype: 'numbercolumn',format:'0,000'}, 
            {header:'收入',dataIndex:'disCusIncome',xtype: 'numbercolumn',format:'0,000'},
            {header:'客户数',dataIndex:'sumCount',xtype: 'numbercolumn',format:'0,000'},
            {header:'收入',dataIndex:'sumIncome',xtype: 'numbercolumn',format:'0,000'}
        ]),
        store:menuStore,
        tbar: ['日期：',
            	{
            	xtype : 'datefield',
    			id:'countStartDate',
    			value:new Date().add(Date.DAY,-7),
    			format:'Y-m-d',
    			name : 'countStartDate',
    			forceSelection : true,
				triggerAction : 'all',
				model : 'local',
    			width : 100,
    			allowBlank:false,
    			blankText:'日期不能为空！'
            },'-',{
            	xtype : 'datefield',
    			id:'countEndDate',
    			value:new Date(),
    			format:'Y-m-d',
    			name : 'countEndDate',
    			forceSelection : true,
				triggerAction : 'all',
				model : 'local',
    			width : 100,
    			allowBlank:false,
    			blankText:'日期不能为空！'
            },'-','日期类型:',{
            	xtype:'combo',
            	name:'dateType',
            	id:'dateTypecombo',
            	forceSelection : true,
				triggerAction : 'all',
				editable:false,
				allowBlank:false,
				blankText:'日期类型不能为空!',
				model : 'local',
				value:'firstDate',
    			width : 100,
            	store:[
            		['firstDate','首次发货日期'],
            		['lastDate','最后发货日期']
            	]
            },
            {
            	text:'<B>统计</B>',
            	id:'btnSearch', 
            	iconCls: 'btnSearch',
            	hidden:false,
            	handler : menuStoreReload
            }
        ]
    });
    var startone = new Ext.form.DateField ({
		id : 'startone',
		format : 'Y-m-d',
		allowBlank : false,
		emptyText : "开始时间",
		value:new Date().add(Date.DAY, -7),
		width : defaultWidth+40
	});
    	
    var endone = new Ext.form.DateField ({
		id : 'endone',
		format : 'Y-m-d',
		allowBlank : false,
		emptyText : "结束时间",
		value:new Date(),
		width : defaultWidth+40
	});
	function removeOne(){
	   		menuGrid.getTopToolbar().remove(startone);
			menuGrid.getTopToolbar().remove(endone);
	}
	function addOne(){
		menuGrid.getTopToolbar().insert(6,startone);
		menuGrid.getTopToolbar().insert(8,endone);
	}
    //客户等级年度分析Grid
     rankMonGrid = new Ext.grid.GridPanel({
    	id : 'rankMonGrid',
    	region:'sourth',
    	height : Ext.lib.Dom.getViewHeight()/2-10,
    	width : Ext.lib.Dom.getViewWidth()-5,
    	frame : false,
    	loadMask : true,
    	stripeRows : true,
    	viewConfig:{
    		scrollOffset: 0,
    		autoScroll : true
    	},
        sm:sm,
        cm:new Ext.grid.ColumnModel([rowNum,
            {header:'客户分类',dataIndex:'importanceLevel'},
            {header:'1月',dataIndex:'1月',xtype: 'numbercolumn',format:'0,000'},
            {header:'2月',dataIndex:'2月',xtype: 'numbercolumn',format:'0,000'},
            {header:'3月',dataIndex:'3月',xtype: 'numbercolumn',format:'0,000'},
            {header:'4月',dataIndex:'4月',xtype: 'numbercolumn',format:'0,000'},
            {header:'5月',dataIndex:'5月',xtype: 'numbercolumn',format:'0,000'},
            {header:'6月',dataIndex:'6月',xtype: 'numbercolumn',format:'0,000'},
            {header:'7月',dataIndex:'7月',xtype: 'numbercolumn',format:'0,000'}, 
            {header:'8月',dataIndex:'8月',xtype: 'numbercolumn',format:'0,000'},
            {header:'9月',dataIndex:'9月',xtype: 'numbercolumn',format:'0,000'},
            {header:'10月',dataIndex:'10月',xtype: 'numbercolumn',format:'0,000'},
            {header:'11月',dataIndex:'11月',xtype: 'numbercolumn',format:'0,000'},
            {header:'12月',dataIndex:'12月',xtype: 'numbercolumn',format:'0,000'}
        ]),
        store:rankMonStore,
        tbar: [{text:'<b>导出</b>',iconCls:'sort_down',handler:function() {
                	parent.exportExl(menuGrid);
        	} },'-','统计维度',{
			xtype : "combo",
			forceSelection : true,
			triggerAction : 'all',
			store : countRangeStore,
			value:'日',
			mode : "remote",// 从本地载值
			//valueField : 'distributionModeId',// value值，与fields对应
			displayField : 'countRangeName',// 显示值，与fields对应
		    id : 'searchCountRange',
			width : defaultWidth,
			listeners:{
				select:function(combo,e){
					var v = combo.getValue();
					if(v=='日'){
						removeOne();
						startone = new Ext.form.DateField ({
				    		id : 'startone',
				    		format : 'Y-m-d',
				    		allowBlank : false,
				    		emptyText : "开始时间",
				    		value:new Date().add(Date.DAY, -7),
				    		width : defaultWidth+40
				    	});
				    	endone = new Ext.form.DateField ({
				    		id : 'endone',
				    		format : 'Y-m-d',
				    		allowBlank : false,
				    		emptyText : "结束时间",
				    		value:new Date(),
				    		width : defaultWidth+40
				    	});
				    	addOne();
				    	
					}else if(v=='周'){
						removeOne();
						startone = new Ext.form.NumberField ({
				    		id : 'startone',
				    		allowNegative :false,
				    		allowBlank : false,
				    		value:new Date().getWeekOfYear()-1,
				    		minValue:1,
				    		maxValue:52,
				    		maxLength:2,
				    		width : defaultWidth
				    	});
				    	endone = new Ext.form.NumberField  ({
				    		id : 'endone',
				    		allowNegative :false,
				    		allowBlank : false,
				    		value:new Date().getWeekOfYear(),
				    		minValue:1,
				    		maxValue:52,
				    		maxLength:2,
				    		width : defaultWidth
				    	});
				    	addOne();
					}else if(v=='月'){
						removeOne();
						startone = new Ext.form.DateField ({
							xtype:'datefield',
				    		id : 'startone',
				    		allowBlank : false,
				    		format : 'Y-m',
				    		emptyText : "开始时间",
				    		value:new Date().add(Date.MONTH, -1),
				    		width : defaultWidth+40
				    	});
				    	endone = new Ext.form.DateField ({
				    		id : 'endone',
				    		format : 'Y-m',
				    		allowBlank : false,
				    		emptyText : "结束时间",
				    		value:new Date(),
				    		width : defaultWidth+40
				    	});
				    	addOne();
					}else if(v=='年'){
						removeOne();
						startone = new Ext.form.NumberField ({
				    		id : 'startone',
				    		allowBlank : false,
				    		allowNegative :false,
				    		value:new Date().getFullYear()-1,
				    		minValue:1,
				    		width : defaultWidth
				    	});
				    	endone = new Ext.form.NumberField  ({
				    		id : 'endone',
				    		allowBlank : false,
				    		allowNegative :false,
				    		value:new Date().getFullYear(),
				    		minValue:1,
				    		width : defaultWidth
				    	});
				    	addOne();
					}
					menuGrid.getTopToolbar().doLayout(true);
				}
			}
			},
			'开始:',startone,' 至 ',endone,'-','统计类型:',{
            	xtype:'combo',
            	name:'countType',
            	id:'countType',
            	width : 60,
            	value:'货量',
            	forceSelection : true,
				triggerAction : 'all',
				model : 'local',
				blankText:'统计类型不能为空',
				allowBlank:false,
            	store:[
            		['货量','货量'],
            		['收入','收入'],
            		['票数','票数']
            	]
            },'-',
            '部门:',{
				xtype : 'combo',
				id:'cuscomboTypeDepart',
				hiddenId : 'departId',
    			hiddenName : 'departId',
    			queryParam :'filter_LIKES_departName',
				triggerAction : 'all',
				store : departStore,
				width:100,
				listWidth:245,
				minChars : 0,
				forceSelection : true,
				pageSize:pageSize,
				displayField : 'departName',//显示值，与fields对应
				valueField : 'departId',//value值，与fields对应
				name : 'departId'
			},'-','客户',
			{
				xtype:'combo',
				store:cusRecordStore,
				displayField:'cusName',
				valueField:'id',
				pageSize:pageSize,
				queryParam:'filter_LIKES_cusName',
				triggerAction : 'all',
				model:'local',
				listWidth:245,
				emptyText:'统计单个客户',
				id:'cusRecordcombo',
				minChars:0,
				hiddenName:'cusRecordId',
				width:100
			},
            {
            	text:'<B>统计</B>',
            	id:'btnSearchrMon', 
            	iconCls: 'btnSearch',
            	hidden:false,
            	handler : rankMonStoreReload
            }/*
            ,{
            	text:'<B>查看图表</B>',
            	id:'shwoChar', 
            	iconCls: 'btnSearch',
            	hidden:false,
            	handler : showRankMonChart
            }*/
        ]
    });

    //增值服务费环比Panel
    loopthanPanel=new Ext.grid.GridPanel({
		region:'center',
    	id : 'loopthanPanel',
    	height : Ext.lib.Dom.getViewHeight()-20,
    	width : Ext.lib.Dom.getViewWidth()-5,
    	frame : false,
    	loadMask : true,
    	stripeRows : true,
    	viewConfig:{
    		scrollOffset: 0,
    		autoScroll : true
    	},
        sm:sm,
        cm:new Ext.grid.ColumnModel([rowNum,
            {header:'费用类型',dataIndex:"feeName"},
            {header:'收入增长',dataIndex:'sumWeight',xtype: 'numbercolumn',format:'0,000'}
        ]),
        store:loopthanStore,
        tbar: [{text:'<b>导出</b>',iconCls:'sort_down',handler:function() {
                	parent.exportExl(loopthanPanel);
        	}},'客户类型:',
        	{
        		xtype : 'combo',
				store:importanceLevelStore,
				name:'importanceLevel',
				valueField:'importanceLevel',
				displayField:'importanceLevel',
				allowBlank:true,
				width:100,
				forceSelection : true,
				triggerAction : 'all',
				id:'importanceLevel',
				value:'大客户',
				model:'local'
        	},'-',
        	'比较类型:',
        	{
        		xtype:'combo',
            	name:'countType',
            	id:'countTypeThan',
            	width : 100,
            	value:'货量',
            	forceSelection : true,
				triggerAction : 'all',
				model : 'local',
				blankText:'统计类型不能为空',
				allowBlank:false,
            	store:[
            		['货量','货量'],
            		['收入','收入'],
            		['票数','票数']
            	]
        	},'-',
        	'比较月份:',
            {
            	xtype : 'datefield',
    			id:'beforeDate',
    			value:new Date().add(Date.MONTH, -1),
    			format:'Y-m',
    			name : 'goodrankdate',
    			forceSelection : true,
				triggerAction : 'all',
				model : 'local',
    			width : 100,
    			allowBlank:false,
    			blankText:'日期不能为空！'
            },
            '-',{
            	xtype : 'datefield',
    			id:'afterDate',
    			value:new Date(),
    			format:'Y-m',
    			name : 'goodrankdate',
    			forceSelection : true,
				triggerAction : 'all',
				model : 'local',
    			width : 100,
    			allowBlank:false,
    			blankText:'日期不能为空！'
            },'-',
            {
            	text:'<B>统计</B>',
            	id:'btnSearchGoods', 
            	iconCls: 'btnSearch',
            	hidden:false,
            	handler : function(){
            		if(Ext.getCmp('importanceLevel').isValid() && Ext.getCmp('beforeDate').isValid() && Ext.getCmp('afterDate').isValid()){
            			searchthan();
            		}
            	}
            }
        ]
    });
    //客服员客服分析Panel
    cusvscusPanel=new Ext.grid.GridPanel({
		region:'center',
    	id : 'cusvscusPanel',
    	height : Ext.lib.Dom.getViewHeight()-30,
    	width : Ext.lib.Dom.getViewWidth()-5,
    	frame : false,
    	loadMask : true,
    	stripeRows : true,
    	viewConfig:{
    		scrollOffset: 0,
    		autoScroll : true
    	},
        sm:sm,
        cm:new Ext.grid.ColumnModel([rowNum,
            {header:'客户名称',dataIndex:"cusName"},
            {header:'客服员名称',dataIndex:'customerService'}
            //{header:'客服员名称',dataIndex:'sumWeight',xtype: 'numbercolumn',format:'0,000'}
        ]),
        store:cusvscusStore,
        tbar: [{text:'<b>导出</b>',iconCls:'sort_down',handler:function() {
                	parent.exportExl(cusvscusPanel);
        	} },'-',
        	'比较类型:',
        	{
        		xtype:'combo',
            	name:'countType',
            	id:'cuscountTypeThan',
            	width : 100,
            	value:'货量',
            	forceSelection : true,
				triggerAction : 'all',
				model : 'local',
				blankText:'统计类型不能为空',
				allowBlank:false,
            	store:[
            		['货量','货量'],
            		['收入','收入'],
            		['票数','票数']
            	]
        	},'-',
        	'比较月份:',
            {
            	xtype : 'datefield',
    			id:'cusbeforeDate',
    			value:new Date().add(Date.MONTH, -1),
    			format:'Y-m',
    			name : 'goodrankdate',
    			forceSelection : true,
				triggerAction : 'all',
				model : 'local',
    			width : 100,
    			allowBlank:false,
    			blankText:'日期不能为空！'
            },
            '-',{
            	xtype : 'datefield',
    			id:'cusafterDate',
    			value:new Date(),
    			format:'Y-m',
    			name : 'goodrankdate',
    			forceSelection : true,
				triggerAction : 'all',
				model : 'local',
    			width : 100,
    			allowBlank:false,
    			blankText:'日期不能为空！'
            },'-','客服员：',{
            	xtype:'textfield',
            	name:'customerService',
            	value:userName,
            	width:100,
            	allowBlank:false,
            	blankText:'客服员不能为空！',
            	id:'customertext'
            },
            {
            	text:'<B>统计</B>',
            	id:'cusbtnSearchGoods', 
            	iconCls: 'btnSearch',
            	hidden:false,
            	handler : function(){
            		if(Ext.getCmp('customertext').isValid() && Ext.getCmp('cusbeforeDate').isValid() && Ext.getCmp('cusafterDate').isValid()){
            			searchCusVsCus();
            		}
            	}
            }
        ],
        bbar : new Ext.PagingToolbar({
				pageSize : pageSize, 
				store : cusvscusStore,
				displayInfo : true,
				displayMsg : '显示第 {0} 条到 {1} 条记录，共 {2} 条',
				emptyMsg : "没有记录信息显示"
		})
    });
    var tablePanel=new Ext.TabPanel({
	   	renderTo:Ext.getBody(),
	    id: "mainTab",   
	    activeTab: 0,
	    width:Ext.lib.Dom.getViewWidth(),
	    height:Ext.lib.Dom.getViewHeight(),
	    defaults: {   
	        autoScroll: true,   
	        autoHeight:true,   
	        style: "padding:0 0 0 0"
	    },   
	    items:[   
	       {title:"客户等级分析", tabTip:"客户等级分析",items:[menuGrid,rankMonGrid]},
	       {title:"客户对比分析",tabTip:'客户对比分析',items:[loopthanPanel]},
	       {title:"客服员客户分析",tabTip:'客服员客户分析',items:[cusvscusPanel]}
		]
	});
	
    //end
	 
});
function menuStoreReload(){
	if(Ext.getCmp('countStartDate').isValid()&& Ext.getCmp('countEndDate').isValid()){
		var startDate = Ext.get('countStartDate').dom.value;
		var endDate  = Ext.get('countEndDate').dom.value;
		var dateType = Ext.getCmp('dateTypecombo').getValue();
		menuStore.reload({
			params:{
				start : 0,
				limit : pageSize,
				startDate:startDate,
				endDate:endDate,
				dateType:dateType
			}
		});
		menuStore.on('load',function(){
			var div =document.getElementById('showView');
			while(div.hasChildNodes()) //当div下还存在子节点时 循环继续
			{	
				div.removeChild(div.firstChild);
			}
		});
	}
}
/**
 * 客户等级年度统计
 */
function rankMonStoreReload(){
	var countType="";
	var cType=Ext.getCmp('countType').getValue();
	if(Ext.getCmp('startone').isValid()&&Ext.getCmp('countType').isValid()&&Ext.getCmp('startone').isValid()&&Ext.getCmp('searchCountRange').isValid()){
		if(cType=='货量'){
			countType='weight';
		}else if(cType=='件数'){
			countType='piece';
		}else if(cType=='收入'){
			countType='income';
		}
		var startCount = Ext.get('startone').dom.value;
		var endCount = Ext.get('endone').dom.value;
		var countRange = Ext.getCmp('searchCountRange').getValue();
		var cusRecordId = Ext.getCmp('cusRecordcombo').getValue();
		
		var date1 = Date.parseDate(startCount,'Y-m-d');
		var date2 = Date.parseDate(endCount,'Y-m-d');
		var d = (date2-date1)/(86400000);//算出天数 86400000=24*60*60*1000
		
		if(searchCountRange=='日' && d>30){
			Ext.Msg.alert(alertTitle,'按日统计最大不能超过30天！');
			return false;
		}
		Ext.apply(rankMonStore.baseParams,{
				countType:countType,
				cusRecordId:cusRecordId,
				startCount:startCount,
				endCount:endCount,
				countRange:countRange,
				departId:Ext.getCmp('cuscomboTypeDepart').getValue()
		});
		rankMonStore.load();
		
		rankMonStore.on('load',function(){
			var sm = new Ext.grid.CheckboxSelectionModel({});
	    	var rowNum = new Ext.grid.RowNumberer({
	       	 	header:'序号', width:35, sortable:true
	    	});
			var cmItems = []; 
			var afields=[];
			cmItems.push(rowNum);
			if(cusRecordId == null || cusRecordId == ''){
				afields.push('IMPORTANCE_LEVEL');
				cmItems.push({header:'客户分类',dataIndex:'IMPORTANCE_LEVEL'});
			}else{
				afields.push('CUS_NAME');
				cmItems.push({header:'客户名称',dataIndex:'CUS_NAME'});
			}
			
			reDoGrid(cmItems,afields,rankMonStore,rankMonGrid);
			
			var unit='';
			var countType=Ext.getCmp('countType').getValue();
			if(countType=='货量'){
				unit='吨';
			}else if(countType=='收入'){
				unit='万元';
			}else{
				unit='千票';
			}
			var showafields=[];
			if(cusRecordId == null || cusRecordId == ''){
				showafields.push('IMPORTANCE_LEVEL');
			}else{
				showafields.push('CUS_NAME');
			}
			var myarr = timeHead(rankMonStore,showafields);
			var strXml='';
			var strCate='<categories>';
			var strDataSet='';
			for(var j=0;j<myarr.length;j++){
	    		strCate+="<category label='"+myarr[j]+"'/>";
	    	}
			strCate+='</categories>';
			for(var i=0;i<rankMonStore.getCount();i++){
				var menuRecord = rankMonStore.getAt(i);
    			var json = menuRecord.json;
    			menuRecord.data = menuRecord.json;
				strDataSet+="<dataset seriesName='"+menuRecord.get(showafields[0]).trim()+"'>";
				for(var j=0;j<myarr.length;j++){
	    			var value=rankMonStore.getAt(i).get(myarr[j]);
	    			if(unit=='吨'){
						value=value/1000;
					}else if(unit=='万元'){
						value=value/10000;
					}else{
						value=value/1000;
					}
					strDataSet+="<set value='"+value+"'/>";
	    		}
				strDataSet+="</dataset>";
			}
			var styles="<styles><definition><style type='font' name='CaptionFont' size='15' color='666666' /><style type='font' name='SubCaptionFont' bold='0' /></definition><application><apply toObject='caption' styles='CaptionFont' /><apply toObject='SubCaption' styles='SubCaptionFont' /></application></styles>";
			strXml="<chart caption='"+countType+"统计' numdivlines='9' divLineAlpha='100' lineThickness='3' anchorRadius='3' showValues='0' numVDivLines='22' formatNumberScale='0' slantLabels='1' anchorRadius='2' anchorBgAlpha='50' showAlternateVGridColor='1' anchorAlpha='100' animation='1' limitsDecimalPrecision='0' divLineDecimalPrecision='1' numberSuffix='"+unit+"'>"+strCate+strDataSet+styles+"</chart>";
			var fusionPanel=new Ext.ux.Chart.Fusion.Panel({
				collapsible:false,
				chartCfg:{
					id:'rankMonChart',
					params:{
						flashVars:{
							debugMode:0,
							lang:'EN'
						}
					}
				},
				autoScroll:true,
				id:'chartpanel2',
				chartURL:'chars/fcf/MSLine.swf',
				dataXML:strXml,
				width:Ext.lib.Dom.getViewWidth()-40,
				height:300
			});
			var win = new Ext.Window({
				title : '图表信息',
				width : Ext.lib.Dom.getViewWidth()-20,
				height: 350,
				closeAction : 'hide',
				plain : true,
				modal : true,
				animateTarget:'btnSearchrMon',
				items : fusionPanel,
				buttonAlign : "center",
				buttons : [ {
					text : "关闭",
					handler : function() {
						win.close();
					}
				}]
			});
			win.on('hide', function() {
				fusionPanel.destroy();
			});
			win.show();
		});
	}
}

function searchthan(){
	var countType=Ext.getCmp('countTypeThan').getValue();
	if(countType=='货量'){
		unit='吨';
	}else if(countType=='收入'){
		unit='万元';
	}else{
		unit='千票';
	}
	var beforeDate=Ext.getCmp('beforeDate').getValue().format('Y-m-d');
	var afterDate=Ext.getCmp('afterDate').getValue().format('Y-m-d');
	var dombefore=Ext.get('beforeDate').dom.value;
	var domafter=Ext.get('afterDate').dom.value;
	var cusType=Ext.getCmp('importanceLevel').getValue();
	
	var sm = new Ext.grid.CheckboxSelectionModel({});
    var rowNum = new Ext.grid.RowNumberer({
        header:'序号', width:35, sortable:true
    });
	var cmItems = []; 
	var afields=[];
	afields.push({name:'departName',mapping:'DEPART_NAME'});
	afields.push({name:'cusName',mapping:'CUS_NAME'});
	afields.push({name:'beforeCount',mapping:'BEFORE_COUNT'});
	afields.push({name:'afterCount',mapping:'AFTER_COUNT'});
	afields.push({name:'dValue',mapping:'D_VALUE'});
	
	cmItems.push(rowNum);
	cmItems.push({header:'区域',dataIndex:'departName'});
	cmItems.push({header:'客商名称',dataIndex:'cusName'});
	cmItems.push({header:Ext.get('beforeDate').dom.value+countType,dataIndex:'beforeCount'});
	cmItems.push({header:Ext.get('afterDate').dom.value+countType,dataIndex:'afterCount'});
	cmItems.push({header:'对比',dataIndex:'dValue'});
	
	loopthanStore=new Ext.data.Store({
        storeId:"loopthanStore",
        baseParams:{limit:pageSize},
        proxy: new Ext.data.HttpProxy({url:sysPath+"/cus/cusAnalyseAction!findCusRankThan.action"}),
        reader: new Ext.data.JsonReader({
        	root: 'resultMap', totalProperty: 'totalCount'
        }, afields)
    });
    loopthanPanel.reconfigure(loopthanStore ,new Ext.grid.ColumnModel(cmItems));
	loopthanStore.load({
		params:{
			beforeDate:beforeDate,
			afterDate:afterDate,
			countType:countType,
			cusType:cusType
		}
	});
	loopthanPanel.doLayout();
	
	loopthanStore.on('load',function(){
		if(countType=='货量'){
			unit='吨';
		}else if(countType=='收入'){
			unit='万元';
		}else{
			unit='千票';
		}
		var strXml='';
    	var strCate='<categories>';
    	var strDataSet='';
    	var sumSet='';
    	var lineSet='';
    	for(var i=0;i<loopthanStore.getCount();i++){
    		var aValue=loopthanStore.getAt(i).get("afterCount");
    		var bValue=loopthanStore.getAt(i).get("beforeCount");
    		if(unit=='吨'){
				aValue=aValue/1000;
				bValue=bValue/1000;
			}else if(unit=='万元'){
				aValue=aValue/10000;
				bValue=bValue/10000;
			}else{
				aValue=aValue/1000;
				bValue=bValue/1000;
			}
    		sumSet+="<set value='"+aValue+"'/>";
    		lineSet+="<set value='"+bValue+"'/>";
    		strCate+="<category label='"+loopthanStore.getAt(i).get("cusName")+"'/>";
    	}
		strCate+='</categories>';
		strDataSet+="<dataset seriesName='"+dombefore+"' color='50BD4A'>"+lineSet+"</dataset>";
		strDataSet+="<dataset seriesName='"+domafter+"' color='A66EDD'>"+sumSet+"</dataset>";
		strXml+="<styles><definition><style type='font' color='666666' name='CaptionFont' size='15' /><style type='font' name='SubCaptionFont' bold='0' /></definition><application><apply toObject='caption' styles='CaptionFont' /><apply toObject='SubCaption' styles='SubCaptionFont' /></application></styles>";
		strXml="<chart caption='"+dombefore+","+domafter+cusType+countType+"对比' palette='2' animation='1' formatNumberScale='0' numberSuffix='"+unit+"' showValues='0' numDivLines='4' legendPosition='BOTTOM'>"+strCate+strDataSet+strXml+"</chart>";
		var fusionPanel=new Ext.ux.Chart.Fusion.Panel({
				collapsible:false,
				chartCfg:{
					id:'chart1',
					params:{
						flashVars:{
							debugMode:0,
							lang:'EN'
						}
					}
				},
				autoScroll:true,
				id:'chartpanel',
				chartURL:'chars/fcf/MSColumn3D.swf?ChartNoDataText=对不起，图表中无数据显示。',
				dataXML:strXml,
				width:Ext.lib.Dom.getViewWidth(),
				height:300
			});
			var div =document.getElementById('showView');
			while(div.hasChildNodes()) //当div下还存在子节点时 循环继续
			{	
				div.removeChild(div.firstChild);
			}
			fusionPanel.render('showView');
	});
}

function searchCusVsCus(){
	var countType=Ext.getCmp('cuscountTypeThan').getValue();
	if(countType=='货量'){
		unit='吨';
	}else if(countType=='收入'){
		unit='万元';
	}else{
		unit='千票';
	}
	var beforeDate=Ext.getCmp('cusbeforeDate').getValue().format('Y-m-d');
	var afterDate=Ext.getCmp('cusafterDate').getValue().format('Y-m-d');
	var dombefore=Ext.get('cusbeforeDate').dom.value;
	var domafter=Ext.get('cusafterDate').dom.value;
	var customerService=Ext.getCmp('customertext').getValue();
	
	var sm = new Ext.grid.CheckboxSelectionModel({});
    var rowNum = new Ext.grid.RowNumberer({
        header:'序号', width:35, sortable:true
    });
	var cmItems = []; 
	var afields=[];
	//afields.push({name:'departName',mapping:'DEPART_NAME'});
	afields.push({name:'cusName',mapping:'CUS_NAME'});
	afields.push({name:'beforeCount',mapping:'BEFORE_COUNT'});
	afields.push({name:'afterCount',mapping:'AFTER_COUNT'});
	afields.push({name:'dValue',mapping:'D_VALUE'});
	afields.push({name:'principal',mapping:'PRINCIPAL'});
	
	cmItems.push(rowNum);
	//cmItems.push({header:'区域',dataIndex:'departName'});
	cmItems.push({header:'客户名称',dataIndex:'cusName'});
	cmItems.push({header:Ext.get('cusbeforeDate').dom.value+countType,dataIndex:'beforeCount',sortable:true});
	cmItems.push({header:Ext.get('cusafterDate').dom.value+countType,dataIndex:'afterCount',sortable:true});
	cmItems.push({header:'增长额',dataIndex:'dValue',sortable:true});
	cmItems.push({header:'客服员',dataIndex:'principal'});
	
	cusvscusStore=new Ext.data.Store({
        storeId:"cusvscusStore",
        baseParams:{limit:pageSize},
        proxy: new Ext.data.HttpProxy({url:sysPath+"/cus/cusAnalyseAction!findCusVsCus.action"}),
        reader: new Ext.data.JsonReader({
        	root: 'resultMap', totalProperty: 'totalCount'
        }, afields)
    });
    cusvscusPanel.reconfigure(cusvscusStore ,new Ext.grid.ColumnModel(cmItems));
    cusvscusStore.baseParams={};
    Ext.apply(cusvscusStore.baseParams,{
    	limit:pageSize,
    	start:0,
    	beforeDate:beforeDate,
		afterDate:afterDate,
		countType:countType,
		customerService:customerService
    });
	cusvscusStore.load();
	cusvscusPanel.doLayout();
	/*
	cusvscusStore.on('load',function(){
		if(countType=='货量'){
			unit='吨';
		}else if(countType=='收入'){
			unit='万元';
		}else{
			unit='票';
		}
		var strXml='';
    	var strCate='<categories>';
    	var strDataSet='';
    	var sumSet='';
    	var lineSet='';
    	for(var i=0;i<cusvscusStore.getCount();i++){
    		var aValue=cusvscusStore.getAt(i).get("afterCount");
    		var bValue=cusvscusStore.getAt(i).get("beforeCount");
    		if(unit=='吨'){
				aValue=aValue/1000;
				bValue=bValue/1000;
			}else if(unit=='万元'){
				aValue=aValue/10000;
				bValue=bValue/10000;
			}
    		sumSet+="<set value='"+aValue+"'/>";
    		lineSet+="<set value='"+bValue+"'/>";
    		strCate+="<category label='"+cusvscusStore.getAt(i).get("cusName")+"'/>";
    	}
		strCate+='</categories>';
		strDataSet+="<dataset seriesName='"+dombefore+"' color='50BD4A'>"+lineSet+"</dataset>";
		strDataSet+="<dataset seriesName='"+domafter+"' color='A66EDD'>"+sumSet+"</dataset>";
		strXml+="<styles><definition><style type='font' color='666666' name='CaptionFont' size='15' /><style type='font' name='SubCaptionFont' bold='0' /></definition><application><apply toObject='caption' styles='CaptionFont' /><apply toObject='SubCaption' styles='SubCaptionFont' /></application></styles>";
		strXml="<chart caption='"+dombefore+","+domafter+countType+"对比' palette='2' animation='1' formatNumberScale='0' numberSuffix='"+unit+"' showValues='0' numDivLines='4' legendPosition='BOTTOM'>"+strCate+strDataSet+strXml+"</chart>";
		var fusionPanel=new Ext.ux.Chart.Fusion.Panel({
				collapsible:false,
				chartCfg:{
					id:'chart1',
					params:{
						flashVars:{
							debugMode:0,
							lang:'EN'
						}
					}
				},
				autoScroll:true,
				id:'chartpanel',
				chartURL:'chars/fcf/MSColumn3D.swf?ChartNoDataText=对不起，图表中无数据显示。',
				dataXML:strXml,
				width:Ext.lib.Dom.getViewWidth(),
				height:300
			});
			var div =document.getElementById('showView');
			while(div.hasChildNodes()) //当div下还存在子节点时 循环继续
			{	
				div.removeChild(div.firstChild);
			}
			fusionPanel.render('showView');
	});*/
}

function timeHead(store,afield){
 	var myarr=store.reader.jsonData.resultMap;
	if(myarr.length>0){
		var newArr= new Array();
		var num=0;
		for(var temp in myarr[0]){
			var flag=true;
			for(var j=0;j<afield.length;j++){
				if(afield[j]==temp){
					flag=false;
					break;
				}
			}
			if(flag){
				newArr[num] =temp;
				num++;
			}
		}
		newArr.sort();
		
		return newArr;
	}
 }
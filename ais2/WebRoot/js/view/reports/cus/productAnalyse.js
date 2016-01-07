var defaultWidth=60;
var fields=[
	{name:'trafficMode',mapping:'TRAFFIC_MODE'},
	{name:'cusDepartName',mapping:'CUS_DEPART_NAME'},
	{name:'productType',mapping:'PRODUCT_TYPE'},
	{name:'sumTicket',mapping:'SUMTICKET'},
	{name:'ticketRate',mapping:'TICKETRATE'},
	{name:'sumWeight',mapping:'SUMWEIGHT'},
	{name:'weightRate',mapping:'WEIGHTRATE'},
	{name:'sumIncome',mapping:'SUMINCOME'},
	{name:'incomeRate',mapping:'INCOMERATE'}
];
var menuField=[
	{name:'PRODUCT_TYPE'},
	{name:'TRAFFIC_MODE'},
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
var goodRankField=[
	{name:'trafficMode',mapping:'TRAFFIC_MODE'},
	{name:'productType',mapping:'PRODUCT_TYPE'},
	{name:'oneTen',mapping:'ONE_TEN'},
	{name:'tenThirty',mapping:'TEN_THIRTY'},
	{name:'thirtySixty',mapping:'THIRTY_SIXTY'},
	{name:'sixtyOnehundered',mapping:'SIXTY_ONEHUNDRED'},
	{name:'onehunderedTwofivety',mapping:'ONEHUNDRED_TWOFIVETY'},
	{name:'twofivetyFivehundred',mapping:'TWOFIVETY_FIVEHUNDRED'},
	{name:'fivehundredOneton',mapping:'FIVEHUNDRED_ONETON'},
	{name:'oneton',mapping:'ONETON'},
	{name:'sumWeight',mapping:'SUMWEIGHT'}
];
var wholeStore,menuStore,goodsRankStore,trafficModeStore,wholeGridPanel,menuGrid,goodsRankPanel;
Ext.onReady(function() {
    //货量销售分析Store
    menuStore = new Ext.data.Store({
        storeId:"menuStore",
        baseParams:{limit:pageSize},
        proxy: new Ext.data.HttpProxy({url:sysPath+"/cus/productAnalyseAction!findCountMsg.action"}),
        reader: new Ext.data.JsonReader({
            root: 'resultMap', totalProperty: 'totalCount'
        }, menuField)
    });
    //运输方式Store
	trafficModeStore=new Ext.data.Store({
		storeId:"trafficModeStore",
		proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/dictionaryAction!ralaList.action"}),
		baseParams:{filter_EQL_basDictionaryId:18,privilege:16},
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
        	{name:'id'},
        	{name:'trafficMode',mapping:'typeName'}
        	])
	});
    //整体产品分析Store
	wholeStore = new Ext.data.Store({
        storeId:"wholeStore",
        baseParams:{limit:pageSize},
        proxy: new Ext.data.HttpProxy({url:sysPath+"/cus/productAnalyseAction!findProWholeMsg.action"}),
        reader: new Ext.data.JsonReader({
            root: 'resultMap', totalProperty: 'totalCount'
        }, fields)
    });
    
    //货量等级Store
    goodsRankStore=new Ext.data.Store({
        storeId:"goodsRankStore",
        baseParams:{limit:pageSize},
        proxy: new Ext.data.HttpProxy({url:sysPath+"/cus/productAnalyseAction!findGoodsRank.action"}),
        reader: new Ext.data.JsonReader({
           // root: 'resultMap', totalProperty: 'totalCount'
        }, goodRankField)
    });
    var sm = new Ext.grid.CheckboxSelectionModel({});
    var rowNum = new Ext.grid.RowNumberer({
        header:'序号', width:35, sortable:true
    });
    
//    var endone = new Ext.form.DateField ({
//    		id : 'endone',
//    		format : 'Y-m-d',
//    		allowBlank : false,
//    		emptyText : "结束时间",
//    		value:new Date(),
//    		width : 120
//    	});
//   	function removeOne(){
//   		wholeGridPanel.getTopToolbar().remove(startone);
//		wholeGridPanel.getTopToolbar().remove(endone);
//   	}
//   	function addOne(){
//   		wholeGridPanel.getTopToolbar().insert(6,startone);
//		wholeGridPanel.getTopToolbar().insert(8,endone);
//   	}
    
	//整体产品分析Panel
	wholeGridPanel=new Ext.grid.GridPanel({
       //	renderTo:Ext.getBody(),
		region:'center',
    	id : 'wholeGridPanel',
    	height : Ext.lib.Dom.getViewHeight()-30,
    	width : Ext.lib.Dom.getViewWidth()-10,
    	frame : false,
    	loadMask : true,
    	stripeRows : true,
    	viewConfig:{
    		scrollOffset: 0,
    		autoScroll : true
    	},
        sm:sm,
        cm:new Ext.grid.ColumnModel([rowNum,
            {header:'运输方式',dataIndex:"trafficMode"},
            {header:'产品',dataIndex:'productType'},
            {header:'客服部门',dataIndex:"cusDepartName"},
            {header:'票数',dataIndex:'sumTicket'},
            {header:'票数占比',dataIndex:"ticketRate",renderer:function(v){
            	return parseFloat(v*100).toFixed(2)+'%';
            }},
            {header:'货量(kg)',dataIndex:"sumWeight"},
            {header:'货量占比',dataIndex:"weightRate",renderer:function(v){
            	return parseFloat(v*100).toFixed(2)+'%';
            }},
            {header:'收入(元)',dataIndex:"sumIncome"},
            {header:'收入占比',dataIndex:"incomeRate"
            ,renderer:function(v){
            	return parseFloat(v*100).toFixed(2)+'%';                  
            }}
        ]),
        store:wholeStore,         
        tbar: [{text:'<b>导出</b>',iconCls:'sort_down',handler:function() {
                	parent.exportExl(wholeGridPanel);
        	} },'时间:',
            {
            	xtype : 'datefield',
    			id:'wholeStartdate',
    			value:new Date().add(Date.DAY, -30),
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
            	xtype : 'datefield',
    			id:'wholeEnddate',
    			value:new Date(),
    			format:'Y-m-d',
    			name : 'wholeDate',
    			forceSelection : true,
				triggerAction : 'all',
				model : 'local',
    			width : 100,
    			allowBlank:false,
    			blankText:'日期不能为空！'
            },'-','产品',{
            	xtype:'combo',
				name:'productType',
				id:'producttypecombo',
				forceSelection : true,
				disabled:true,
				width:80,
				triggerAction : 'all',
				model : 'local',
				width:80,
				store:[
					['新邦自提','新邦自提'],
					['新邦送货','新邦送货'],
					['中转送货','中转送货'],
					['专车送货','专车送货'],
					['中转自提','中转自提'],
					['外发自提','外发自提'],
					['外发送货','外发送货']
				]
            },'范围:',{
				xtype:'combo',
				name:'productTypeScope',
				id:'productTypeScope',
				forceSelection : true,
				triggerAction : 'all',
				model : 'local',
				value:'all',
				width:80,
				store:[
					['all','全部'],
					['one','指定产品'],
					['','忽略']
				],
				listeners:{
					'select':function(combo){
						if(combo.getValue()=='' || combo.getValue()=='all'){
							Ext.getCmp('producttypecombo').setDisabled(true);
						}else{
							Ext.getCmp('producttypecombo').setDisabled(false);
						}
					}
				}
			},'-','运输方式:',
        			{
        				xtype:'combo',
						triggerAction : 'all',
    					model : 'local',
    					name:'trafficMode',
    					disabled:true,
    					id:'trafficModecombo',
    					valueField : 'trafficMode',
						displayField : 'trafficMode',
						name : 'trafficMode',
						width:80,
						store:trafficModeStore
        			},'范围:',{
        				xtype:'combo',
        				name:'trafficModeScope',
        				id:'trafficModeScope',
        				forceSelection : true,
						triggerAction : 'all',
						model : 'local',
						value:'all',
						width:80,
        				store:[
        					['all','全部'],
        					['one','指定方式'],
        					['','忽略']
        				],
        				listeners:{
        					'select':function(combo){
        						if(combo.getValue()=='' || combo.getValue()=='all'){
        							Ext.getCmp('trafficModecombo').setDisabled(true);
        						}else{
        							Ext.getCmp('trafficModecombo').setDisabled(false);
        						}
        					}
        				}
        			},
            {
            	text:'<B>统计</B>',
            	id:'btnSearch', 
            	iconCls: 'btnSearch',
            	hidden:false,
            	handler : searchCusRequest
            }
        ],
        bbar : new Ext.PagingToolbar({
				pageSize : pageSize, 
				store : wholeStore,
				displayInfo : true,
				displayMsg : '显示第 {0} 条到 {1} 条记录，共 {2} 条',
				emptyMsg : "没有记录信息显示"
		})
    });
    wholeGridPanel.on('render',function(){
    	searchTbar.render(wholeGridPanel.tbar);
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
	var menusearchTbar= new Ext.Toolbar({
    		width : Ext.lib.Dom.getViewWidth(),
    		items : ['部门:',
        			{
        				xtype : 'combo',
						id:'menucomboTypeDepart',
						hiddenId : 'departId',
		    			hiddenName : 'departId',
		    			queryParam :'filter_LIKES_departName',
						triggerAction : 'all',
						store : departStore,
						width:100,
						listWidth:245,
						minChars : 0,
						disabled:true,
						forceSelection : true,
						fieldLabel:'部门名称',
						pageSize:pageSize,
						displayField : 'departName',//显示值，与fields对应
						valueField : 'departId',//value值，与fields对应
						name : 'departId'
        			},'范围:',{
        				xtype:'combo',
        				name:'departScope',
        				id:'menudepartScope',
        				forceSelection : true,
						triggerAction : 'all',
						model : 'local',
						width:80,
						value:'',
        				store:[
        					['all','全部'],
        					['one','指定部门'],
        					['','忽略']
        				],
        				listeners:{
        					'select':function(combo){
        						if(combo.getValue()=='' || combo.getValue()=='all'){
        							Ext.getCmp('menucomboTypeDepart').setDisabled(true);
        						}else{
        							Ext.getCmp('menucomboTypeDepart').setDisabled(false);
        						}
        					}
        				}
        			},'-','代理:',{
        				xtype:'combo',
						triggerAction :'all',
						model : 'local',
    					id:'menucuscombo',
    					resizable : true,
    					width:100,
    					pageSize:pageSize,
    					minChars : 0,
    					disabled:true,
    					listWidth:200,
    					store:cusStore,
    					queryParam :'filter_LIKES_cusName',
    					valueField :'id',
						displayField :'cusName',
						hiddenName : 'cusId'
        			},'范围:',{
        				xtype:'combo',
        				name:'cusScope',
        				id:'menucusScope',
        				forceSelection : true,
						triggerAction : 'all',
						model : 'local',
						value:'',
						width:80,
        				store:[
        					['all','全部'],
        					['one','指定代理'],
        					['','忽略']
        				],
        				listeners:{
        					'select':function(combo){
        						if(combo.getValue()=='' || combo.getValue()=='all'){
        							Ext.getCmp('menucuscombo').setDisabled(true);
        						}else{
        							Ext.getCmp('menucuscombo').setDisabled(false);
        						}
        					}
        				}
        			},'-','目的站:',{
				xtype:'combo',
				id:'menucitycombo',
				name:'city',
				minChars : 0,
				triggerAction : 'all',
				pageSize:pageSize,
				disabled:true,
				width:80,
				valueField : 'areaName',
				queryParam : 'filter_LIKES_areaName',
				store:areaCityStore,
				displayField : 'areaName',
				forceSelection:true
			},'范围:',{
				xtype:'combo',
				name:'endCity',
				id:'menuendCitycombo',
				forceSelection : true,
				triggerAction : 'all',
				model : 'local',
				value:'',
				width:80,
				store:[
					['all','全部'],
					['one','指定目的站'],
					['','忽略']
				],
				listeners:{
					'select':function(combo){
						if(combo.getValue()=='' || combo.getValue()=='all'){
							Ext.getCmp('menucitycombo').setDisabled(true);
						}else{
							Ext.getCmp('menucitycombo').setDisabled(false);
						}
					}
				}
			}
        ]
    	});
    	
    	var menusearchTbar2= new Ext.Toolbar({
    		width : Ext.lib.Dom.getViewWidth(),
    		items : ['产品',{
            	xtype:'combo',
				name:'productType',
				id:'menuproducttypecombo',
				forceSelection : true,
				disabled:true,
				width:80,
				triggerAction : 'all',
				model : 'local',
				width:80,
				store:[
					['新邦自提','新邦自提'],
					['新邦送货','新邦送货'],
					['中转送货','中转送货'],
					['专车送货','专车送货'],
					['中转自提','中转自提'],
					['外发自提','外发自提'],
					['外发送货','外发送货']
				]
            },'范围:',{
				xtype:'combo',
				name:'productTypeScope',
				id:'menuproductTypeScope',
				forceSelection : true,
				triggerAction : 'all',
				model : 'local',
				value:'all',
				width:80,
				store:[
					['all','全部'],
					['one','指定产品'],
					['','忽略']
				],
				listeners:{
					'select':function(combo){
						if(combo.getValue()=='' || combo.getValue()=='all'){
							Ext.getCmp('menuproducttypecombo').setDisabled(true);
						}else{
							Ext.getCmp('menuproducttypecombo').setDisabled(false);
						}
					}
				}
			},'-','运输方式:',
        			{
        				xtype:'combo',
						triggerAction : 'all',
    					model : 'local',
    					name:'trafficMode',
    					disabled:true,
    					id:'menutrafficModecombo',
    					valueField : 'trafficMode',
						displayField : 'trafficMode',
						name : 'trafficMode',
						width:80,
						store:trafficModeStore
        			},'范围:',{
        				xtype:'combo',
        				name:'trafficModeScope',
        				id:'menutrafficModeScope',
        				forceSelection : true,
						triggerAction : 'all',
						model : 'local',
						value:'all',
						width:80,
        				store:[
        					['all','全部'],
        					['one','指定方式'],
        					['','忽略']
        				],
        				listeners:{
        					'select':function(combo){
        						if(combo.getValue()=='' || combo.getValue()=='all'){
        							Ext.getCmp('menutrafficModecombo').setDisabled(true);
        						}else{
        							Ext.getCmp('menutrafficModecombo').setDisabled(false);
        						}
        					}
        				}
        			}
        	]
    	});
    //产品销售、货量趋势Panel
     menuGrid = new Ext.grid.GridPanel({
       	//renderTo:Ext.getBody(),
    	id : 'myrecordGrid',
    	region:'center',
    	height : Ext.lib.Dom.getViewHeight()-30,
    	width : Ext.lib.Dom.getViewWidth()-10,
    	frame : false,
    	loadMask : true,
    	stripeRows : true,
    	viewConfig:{
    		scrollOffset: 0,
    		autoScroll : true
    	},
        sm:sm,
        cm:new Ext.grid.ColumnModel([rowNum,
        	{header:'运输方式',dataIndex:"TRAFFIC_MODE"},
            {header:'产品类型',dataIndex:'PRODUCT_TYPE'},
            {header:'一月',dataIndex:'1月',xtype: 'numbercolumn',format:'0,000'},
            {header:'二月',dataIndex:'2月',xtype: 'numbercolumn',format:'0,000'},
            {header:'三月',dataIndex:'3月',xtype: 'numbercolumn',format:'0,000'},
            {header:'四月',dataIndex:'4月',xtype: 'numbercolumn',format:'0,000'},
            {header:'五月',dataIndex:'5月',xtype: 'numbercolumn',format:'0,000'},
            {header:'六月',dataIndex:'6月',xtype: 'numbercolumn',format:'0,000'},
            {header:'七月',dataIndex:'7月',xtype: 'numbercolumn',format:'0,000'}, 
            {header:'八月',dataIndex:'8月',xtype: 'numbercolumn',format:'0,000'},
            {header:'九月',dataIndex:'9月',xtype: 'numbercolumn',format:'0,000'},
            {header:'十月',dataIndex:'10月',xtype: 'numbercolumn',format:'0,000'},
            {header:'十一月',dataIndex:'11月',xtype: 'numbercolumn',format:'0,000'},
            {header:'十二月',dataIndex:'12月',xtype: 'numbercolumn',format:'0,000'}
        ]),
        store:menuStore,
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
			'开始:',startone,' 至 ',endone,'-','统计类型',{
            	xtype:'combo',
            	name:'countType',
            	id:'countType',
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
            }
            ,'-',
            {
            	text:'<B>统计</B>',
            	id:'btnSearch', 
            	iconCls: 'btnSearch',
            	hidden:false,
            	handler : menuStoreReload
            }
        ],
        bbar : new Ext.PagingToolbar({
				pageSize : pageSize, 
				store : menuStore,
				displayInfo : true,
				displayMsg : '显示第 {0} 条到 {1} 条记录，共 {2} 条',
				emptyMsg : "没有记录信息显示"
		}),
        listeners: {
            render: function(){
                menusearchTbar.render(this.tbar);
    			menusearchTbar2.render(this.tbar);
            }
        }
    });
    var rankSearchTbar= new Ext.Toolbar({
    		width : Ext.lib.Dom.getViewWidth(),
    		items : ['部门:',
        			{
        				xtype : 'combo',
						id:'rankcomboTypeDepart',
						hiddenId : 'departId',
		    			hiddenName : 'departId',
		    			queryParam :'filter_LIKES_departName',
						triggerAction : 'all',
						store : departStore,
						width:100,
						listWidth:245,
						minChars : 0,
						disabled:true,
						forceSelection : true,
						fieldLabel:'部门名称',
						pageSize:pageSize,
						displayField : 'departName',//显示值，与fields对应
						valueField : 'departId',//value值，与fields对应
						name : 'departId'
        			},'范围:',{
        				xtype:'combo',
        				name:'departScope',
        				id:'rankdepartScope',
        				forceSelection : true,
						triggerAction : 'all',
						model : 'local',
						width:80,
						value:'',
        				store:[
        					['all','全部'],
        					['one','指定部门'],
        					['','忽略']
        				],
        				listeners:{
        					'select':function(combo){
        						if(combo.getValue()=='' || combo.getValue()=='all'){
        							Ext.getCmp('rankcomboTypeDepart').setDisabled(true);
        						}else{
        							Ext.getCmp('rankcomboTypeDepart').setDisabled(false);
        						}
        					}
        				}
        			},'-','代理:',{
        				xtype:'combo',
						triggerAction :'all',
						model : 'local',
    					id:'rankcuscombo',
    					resizable : true,
    					width:100,
    					pageSize:pageSize,
    					minChars : 0,
    					disabled:true,
    					listWidth:200,
    					store:cusStore,
    					queryParam :'filter_LIKES_cusName',
    					valueField :'id',
						displayField :'cusName',
						hiddenName : 'cusId'
        			},'范围:',{
        				xtype:'combo',
        				name:'cusScope',
        				id:'rankcusScope',
        				forceSelection : true,
						triggerAction : 'all',
						model : 'local',
						value:'',
						width:80,
        				store:[
        					['all','全部'],
        					['one','指定代理'],
        					['','忽略']
        				],
        				listeners:{
        					'select':function(combo){
        						if(combo.getValue()=='' || combo.getValue()=='all'){
        							Ext.getCmp('rankcuscombo').setDisabled(true);
        						}else{
        							Ext.getCmp('rankcuscombo').setDisabled(false);
        						}
        					}
        				}
        			},'-','目的站:',{
				xtype:'combo',
				id:'rankcitycombo',
				name:'city',
				minChars : 0,
				triggerAction : 'all',
				pageSize:pageSize,
				disabled:true,
				width:80,
				valueField : 'areaName',
				queryParam : 'filter_LIKES_areaName',
				store:areaCityStore,
				displayField : 'areaName',
				forceSelection:true
			},'范围:',{
				xtype:'combo',
				name:'endCity',
				id:'rankendCitycombo',
				forceSelection : true,
				triggerAction : 'all',
				model : 'local',
				value:'',
				width:80,
				store:[
					['all','全部'],
					['one','指定目的站'],
					['','忽略']
				],
				listeners:{
					'select':function(combo){
						if(combo.getValue()=='' || combo.getValue()=='all'){
							Ext.getCmp('rankcitycombo').setDisabled(true);
						}else{
							Ext.getCmp('rankcitycombo').setDisabled(false);
						}
					}
				}
			}
        ]
    	});
    //货量等级Panel
    goodsRankPanel=new Ext.grid.GridPanel({
       //	renderTo:Ext.getBody(),
		region:'center',
    	id : 'goodsRankPanel',
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
        	{header:'运输方式',dataIndex:"trafficMode"},
            {header:'产品类型',dataIndex:"productType"},
            {header:'总计',dataIndex:'sumWeight',xtype: 'numbercolumn',format:'0,000'}
        ]),
        store:goodsRankStore,
        tbar: [{text:'<b>导出</b>',iconCls:'sort_down',handler:function() {
                	parent.exportExl(goodsRankPanel);
        	} },'日期:',
            {
            	xtype : 'datefield',
    			id:'rankStartDate',
    			value:new Date().add(Date.DAY, -7),
    			format:'Y-m-d',
    			name : 'startDate',
				triggerAction : 'all',
    			width : 100,
    			allowBlank:false,
    			blankText:'日期不能为空！'
            },{
            	xtype : 'datefield',
    			id:'rankEndDate',
    			value:new Date(),
    			format:'Y-m-d',
    			name : 'endDate',
				triggerAction : 'all',
    			width : 100,
    			allowBlank:false,
    			blankText:'日期不能为空！'
            },'-','产品',{
            	xtype:'combo',
				name:'productType',
				id:'rankproductTypecombo',
				forceSelection : true,
				disabled:true,
				width:80,
				triggerAction : 'all',
				model : 'local',
				width:80,
				store:[
					['新邦自提','新邦自提'],
					['新邦送货','新邦送货'],
					['中转送货','中转送货'],
					['专车送货','专车送货'],
					['中转自提','中转自提'],
					['外发自提','外发自提'],
					['外发送货','外发送货']
				]
            },'范围:',{
				xtype:'combo',
				name:'productTypeScope',
				id:'rankproductTypeScope',
				forceSelection : true,
				triggerAction : 'all',
				model : 'local',
				value:'all',
				width:80,
				store:[
					['all','全部'],
					['one','指定产品'],
					['','忽略']
				],
				listeners:{
					'select':function(combo){
						if(combo.getValue()=='' || combo.getValue()=='all'){
							Ext.getCmp('rankproductTypecombo').setDisabled(true);
						}else{
							Ext.getCmp('rankproductTypecombo').setDisabled(false);
						}
					}
				}
			},'-','运输方式:',
        			{
        				xtype:'combo',
						triggerAction : 'all',
    					model : 'local',
    					name:'trafficMode',
    					disabled:true,
    					id:'ranktrafficModecombo',
    					valueField : 'trafficMode',
						displayField : 'trafficMode',
						name : 'trafficMode',
						width:80,
						store:trafficModeStore
        			},'范围:',{
        				xtype:'combo',
        				name:'trafficModeScope',
        				id:'ranktrafficModeScope',
        				forceSelection : true,
						triggerAction : 'all',
						model : 'local',
						value:'all',
						width:80,
        				store:[
        					['all','全部'],
        					['one','指定方式'],
        					['','忽略']
        				],
        				listeners:{
        					'select':function(combo){
        						if(combo.getValue()=='' || combo.getValue()=='all'){
        							Ext.getCmp('ranktrafficModecombo').setDisabled(true);
        						}else{
        							Ext.getCmp('ranktrafficModecombo').setDisabled(false);
        						}
        					}
        				}
        			},
            {
            	text:'<B>统计</B>',
            	id:'btnSearchGoods', 
            	iconCls: 'btnSearch',
            	hidden:false,
            	handler : searchGoodsRank
            }
        ],
        bbar : new Ext.PagingToolbar({
				pageSize : pageSize, 
				store : goodsRankStore,
				displayInfo : true,
				displayMsg : '显示第 {0} 条到 {1} 条记录，共 {2} 条',
				emptyMsg : "没有记录信息显示"
		}),
		listeners:{
        	'render':function(){
        		rankSearchTbar.render(this.tbar);
        	}
        }
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
	       {title:"整体产品分析", tabTip:"整体产品分析",items:[wholeGridPanel]},
	       {title:"产品销售/货量趋势",tabTip:'产品销售/货量趋势',items:[menuGrid]},
	       {title:"货量等级分析",tabTip:'货量等级分析',items:[goodsRankPanel]}
		]
	});
});

function searchCusRequest() {
	if(Ext.getCmp('wholeStartdate').isValid()&&Ext.getCmp('wholeEnddate').isValid()){
			var startDate=Ext.get('wholeStartdate').dom.value;
			var endDate=Ext.get('wholeEnddate').dom.value;
			
			var cusId = Ext.getCmp('cuscombo').getValue();
			var cusScope = Ext.getCmp('cusScope').getValue();
			
			var departId = Ext.getCmp('comboTypeDepart').getValue();
			var departScope = Ext.getCmp('departScope').getValue();
			
			var trafficMode = Ext.getCmp('trafficModecombo').getValue();
			var trafficModeScope = Ext.getCmp('trafficModeScope').getValue();
			
			var endCity = Ext.getCmp('citycombo').getValue();
			var endCityScope = Ext.getCmp('endCitycombo').getValue();
			
			var productType = Ext.getCmp('producttypecombo').getValue();
			var productTypeScope = Ext.getCmp('productTypeScope').getValue();
		
			
			var sm = new Ext.grid.CheckboxSelectionModel({});
		    var rowNum = new Ext.grid.RowNumberer({
		        header:'序号', width:35, sortable:true
		    });
			var cmItems = []; 
			var afields=[];
			cmItems.push(rowNum);
			if(departScope != ''){
				afields.push({name:'cusDepartName',mapping:'CUS_DEPART_NAME'});
				cmItems.push({header:'部门名称',dataIndex:'cusDepartName',width:120});
			}
			if(cusScope != ''){
				afields.push({name:'cusName',mapping:'CUS_NAME'});
				cmItems.push({header:'代理名称',dataIndex:'cusName',width:120});
			}
			if(trafficModeScope != ''){
				afields.push({name:'trafficMode',mapping:'TRAFFIC_MODE'});
				cmItems.push({header:'运输方式',dataIndex:'trafficMode',width:80});
			}
			if(endCityScope != ''){
				afields.push({name:'endCity',mapping:'END_CITY'});
				cmItems.push({header:'目的站',dataIndex:'endCity',width:80});
			}
			if(productTypeScope != ''){
				afields.push({name:'productType',mapping:'PRODUCT_TYPE'});
				cmItems.push({header:'产品类型',dataIndex:'productType',width:120});
			}
			afields.push({name:'sumTicket',mapping:'SUMTICKET'});
			cmItems.push({header:'票数',dataIndex:'sumTicket'});
			
			afields.push({name:'ticketRate',mapping:'TICKETRATE'});
			cmItems.push({header:'票数占比',dataIndex:"ticketRate",renderer:function(v){return parseFloat(v*100).toFixed(2)+'%';}});
			
			afields.push({name:'sumWeight',mapping:'SUMWEIGHT'});
			cmItems.push({header:'货量(kg)',dataIndex:"sumWeight"});
			
			afields.push({name:'weightRate',mapping:'WEIGHTRATE'});
			cmItems.push({header:'货量占比',dataIndex:"weightRate",renderer:function(v){return parseFloat(v*100).toFixed(2)+'%';}});
			
			afields.push({name:'sumIncome',mapping:'SUMINCOME'});
			cmItems.push({header:'收入(元)',dataIndex:"sumIncome"});
			
			afields.push({name:'incomeRate',mapping:'INCOMERATE'});
			cmItems.push({header:'收入占比',dataIndex:"incomeRate",renderer:function(v){return parseFloat(v*100).toFixed(2)+'%';}});
            
			wholeStore = new Ext.data.Store({
		        storeId:"wholeStore",
		        baseParams:{limit:pageSize},
		        proxy: new Ext.data.HttpProxy({url:sysPath+"/cus/productAnalyseAction!findProWholeMsg.action"}),
		        reader: new Ext.data.JsonReader({
		            root: 'resultMap', totalProperty: 'totalCount'
		        }, afields)
		    });
		    fields=afields;
			wholeGridPanel.reconfigure(wholeStore,new Ext.grid.ColumnModel(cmItems));
			Ext.apply(wholeStore.baseParams,{
				limit:pageSize,
				start:0,
				startDate:startDate,
				endDate:endDate,
				cusId:cusId,
				cusScope:cusScope,
				departId:departId,
				departScope:departScope,
				trafficMode:trafficMode,
				trafficModeScope:trafficModeScope,
				endCity:endCity,
				endCityScope:endCityScope,
				productType:productType,
				productTypeScope:productTypeScope
			});
			wholeStore.load();
			
			
			wholeStore.on('load',function(){
			var strXml="";
	    	var sets="";
	    	var styles="<styles><definition><style type='font' name='CaptionFont' size='15' color='666666' /><style type='font' name='SubCaptionFont' bold='0' /></definition><application><apply toObject='caption' styles='CaptionFont' /><apply toObject='SubCaption' styles='SubCaptionFont' /></application></styles>";
			for(var i=0;i<wholeStore.getCount();i++){
				sets+="<set label='";
				if(departScope != ''){
					sets+=wholeStore.getAt(i).get('cusDepartName')+',';
				}
				if(cusScope != ''){
					sets+=wholeStore.getAt(i).get('cusName')+',';
				}
				if(trafficModeScope != ''){
					sets+=wholeStore.getAt(i).get('trafficMode')+',';
				}
				if(endCityScope != ''){
					sets+=wholeStore.getAt(i).get('endCity')+',';
				}
				if(productTypeScope != ''){
					sets+=wholeStore.getAt(i).get('productType')+',';
				}
				if(sets.indexOf(',')>0 && (sets.indexOf(',')==sets.length-1)){
					sets = sets.substring(0,sets.length-1);
				}
				sets+="' value='"+wholeStore.getAt(i).get('incomeRate')*100+"' isSliced='0'/>";
				//sets+="<set label='"+wholeStore.getAt(i).get('trafficMode')+','+wholeStore.getAt(i).get('productType')+"' value='"+wholeStore.getAt(i).get('incomeRate')*100+"' isSliced='0'/>";
			}
			strXml="<chart basefontsize='13' caption='"+startDate+"至"+endDate+"收入占比' palette='2' animation='1' formatNumberScale='0' pieSliceDepth='30' startingAngle='125' numberSuffix='%' >"+sets+styles+"</chart>";
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
				chartURL:'chars/fcf/Pie3D.swf',
				dataXML:strXml,
				width:Ext.lib.Dom.getViewWidth()-20,
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
}
	
function menuStoreReload(){
//	if(!panduan()){
//		return;
//	}
	if(Ext.getCmp('startone').isValid()&&Ext.getCmp('countType').isValid()&&Ext.getCmp('endone').isValid()){
		var startDate=Ext.get('startone').dom.value;
		var endDate=Ext.get('endone').dom.value;
		var countRange = Ext.getCmp('searchCountRange').getValue();
		
		var date1 = Date.parseDate(startDate,'Y-m-d');
		var date2 = Date.parseDate(endDate,'Y-m-d');
		var d = (date2-date1)/86400000;//算出天数86400000=24*60*60*1000
		
		if(countRange=='日' && d>30){
			Ext.Msg.alert(alertTitle,'按日统计最大不能超过30天！');
			return false;
		}
		
		var cusId = Ext.getCmp('menucuscombo').getValue();
		var cusScope = Ext.getCmp('menucusScope').getValue();
		
		var departId = Ext.getCmp('menucomboTypeDepart').getValue();
		var departScope = Ext.getCmp('menudepartScope').getValue();
		
		var trafficMode = Ext.getCmp('menutrafficModecombo').getValue();
		var trafficModeScope = Ext.getCmp('menutrafficModeScope').getValue();
		
		var endCity = Ext.getCmp('menucitycombo').getValue();
		var endCityScope = Ext.getCmp('menuendCitycombo').getValue();
		
		var productType = Ext.getCmp('menuproducttypecombo').getValue();
		var productTypeScope = Ext.getCmp('menuproductTypeScope').getValue();
		
	
		var countType='';
		var type=Ext.getCmp('countType').getValue();
		if(type=='货量'){
			countType='weight';
		}else if(type='收入'){
			countType='income';
		}else{
			countType='ticket';
		}
		
		var sm = new Ext.grid.CheckboxSelectionModel({});
	    var rowNum = new Ext.grid.RowNumberer({
	        header:'序号', width:35, sortable:true
	    });
		var cmItems = []; 
		var afields=[];
		cmItems.push(rowNum);
		if(departScope != ''){
			//afields.add();
			afields.push('CUS_DEPART_NAME');
			//afield[0]=''
			cmItems.push({header:'部门名称',dataIndex:'CUS_DEPART_NAME',width:120});
		}
		if(cusScope != ''){
			afields.push('CUS_NAME');
			cmItems.push({header:'代理名称',dataIndex:'CUS_NAME',width:120});
		}
		if(trafficModeScope != ''){
			afields.push('TRAFFIC_MODE');
			cmItems.push({header:'运输方式',dataIndex:'TRAFFIC_MODE',width:80});
		}
		if(endCityScope != ''){
			afields.push('END_CITY');
			cmItems.push({header:'目的站',dataIndex:'END_CITY',width:80});
		}
		if(productTypeScope != ''){
			afields.push('PRODUCT_TYPE');
			cmItems.push({header:'产品类型',dataIndex:'PRODUCT_TYPE',width:80});
		}
		
		menuStore = new Ext.data.Store({
	        storeId:"menuStore",
	        baseParams:{limit:pageSize},
	        proxy: new Ext.data.HttpProxy({url:sysPath+"/cus/productAnalyseAction!findCountMsg.action"}),
	        reader: new Ext.data.JsonReader({
	            root: 'resultMap', totalProperty: 'totalCount'
	        }, afields)
	    });
		Ext.apply(menuStore.baseParams,{
			limit:pageSize,
			start:0,
			countType:countType,
			startCount:startDate,
			endCount:endDate,
			countRange:countRange,
			cusId:cusId,
			cusScope:cusScope,
			departId:departId,
			departScope:departScope,
			trafficMode:trafficMode,
			trafficModeScope:trafficModeScope,
			endCity:endCity,
			endCityScope:endCityScope,
			productType:productType,
			productTypeScope:productTypeScope
		});
		menuStore.load();
		
		
		menuStore.on('load',function(){
			reDoGrid(cmItems,afields,menuStore,menuGrid);
	    	var unit='';
	    	var countType=Ext.getCmp('countType').getValue();
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
	    	var showafields=[];
	    	if(departScope != ''){
				showafields.push('CUS_DEPART_NAME');
			}
			if(cusScope != ''){
				showafields.push('CUS_NAME');
			}
			if(trafficModeScope != ''){
				showafields.push('TRAFFIC_MODE');
			}
			if(endCityScope != ''){
				showafields.push('END_CITY');
			}
			if(productTypeScope != ''){
				showafields.push('PRODUCT_TYPE');
			}
	    	var myarr = timeHead(menuStore,showafields);
	    	for(var j=0;j<myarr.length;j++){
	    		strCate+="<category label='"+myarr[j]+"'/>";
	    	}
			strCate+='</categories>';
	    	for(var i=0;i<menuStore.getCount();i++){
	    		strDataSet+="<dataset seriesName='"
	    		for(var k=0;k<showafields.length;k++){
	    			strDataSet+=menuStore.getAt(i).get(showafields[k]);
	    			if(k!=showafields.length-1){
	    				strDataSet+=",";
	    			}
	    		}
				strDataSet+="'>";
				for(var j=0;j<myarr.length;j++){
					var menuRecord = menuStore.getAt(i);
	    			var json = menuRecord.json;
	    			menuRecord.data = menuRecord.json;
					var value=menuRecord.get(myarr[j]);
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
						id:'chart2',
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
					width:Ext.lib.Dom.getViewWidth()-20,
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
}
function searchGoodsRank(){
	if(Ext.getCmp('rankStartDate').isValid()&&Ext.getCmp('rankEndDate').isValid()){
		Ext.Ajax.request({
			url:sysPath+'/cus/cusGoodsRankAction!getGoodsRank.action',
			params:{
				limit:pageSize,
				start:0
			},
			success:function(resp){
				var respText = Ext.util.JSON.decode(resp.responseText);
				if(respText.result.length<1){
					Ext.Msg.alert(alertTitle,'请先设置货量等级再尝试统计！');
					return;
				}else{
					var startDate = Ext.get('rankStartDate').dom.value;
					var endDate = Ext.get('rankEndDate').dom.value;
					var cusId = Ext.getCmp('rankcuscombo').getValue();
					var cusScope = Ext.getCmp('rankcusScope').getValue();
					
					var departId = Ext.getCmp('rankcomboTypeDepart').getValue();
					var departScope = Ext.getCmp('rankdepartScope').getValue();
					
					var trafficMode = Ext.getCmp('ranktrafficModecombo').getValue();
					var trafficModeScope = Ext.getCmp('ranktrafficModeScope').getValue();
					
					var endCity = Ext.getCmp('rankcitycombo').getValue();
					var endCityScope = Ext.getCmp('rankendCitycombo').getValue();
					
					var productType = Ext.getCmp('rankproductTypecombo').getValue();
					var productTypeScope = Ext.getCmp('rankproductTypeScope').getValue();
					
				
					var sm = new Ext.grid.CheckboxSelectionModel({});
				    var rowNum = new Ext.grid.RowNumberer({
				        header:'序号', width:35, sortable:true
				    });
					var cmItems = []; 
					var afields=[];
					cmItems.push(rowNum);
					
					if(departScope != ''){
						afields.push('CUS_DEPART_NAME');
						cmItems.push({header:'部门名称',dataIndex:'CUS_DEPART_NAME',width:120});
					}
					if(cusScope != ''){
						afields.push('CUS_NAME');
						cmItems.push({header:'代理名称',dataIndex:'CUS_NAME',width:120});
					}
					if(trafficModeScope != ''){
						afields.push('TRAFFIC_MODE');
						cmItems.push({header:'运输方式',dataIndex:'TRAFFIC_MODE',width:80});
					}
					if(endCityScope != ''){
						afields.push('END_CITY');
						cmItems.push({header:'目的站',dataIndex:'END_CITY',width:80});
					}
					if(productTypeScope != ''){
						afields.push('PRODUCT_TYPE');
						cmItems.push({header:'产品类型',dataIndex:'PRODUCT_TYPE',width:80});
					}
					afields.push('SUM_WEIGHT');
					cmItems.push({header:'合计',dataIndex:'SUM_WEIGHT',width:80,xtype:'numbercolumn',format:'0,000',sortabled:true});
					goodsRankStore = new Ext.data.Store({
				        storeId:"goodsRankStore",
				        baseParams:{limit:pageSize},
				        proxy: new Ext.data.HttpProxy({url:sysPath+"/cus/productAnalyseAction!findGoodsRank.action"}),
				        reader: new Ext.data.JsonReader({
				            root: 'resultMap', totalProperty: 'totalCount'
				        }, afields)
				    });
					Ext.apply(goodsRankStore.baseParams,{
						limit:pageSize,
						start:0,
						startDate:startDate,
						endDate:endDate,
						cusId:cusId,
						cusScope:cusScope,
						departId:departId,
						departScope:departScope,
						trafficMode:trafficMode,
						trafficModeScope:trafficModeScope,
						endCity:endCity,
						endCityScope:endCityScope,
						productType:productType,
						productTypeScope:productTypeScope
					});
					goodsRankStore.load();
					goodsRankStore.on('load',function(){
						reDoGrid(cmItems,afields,goodsRankStore,goodsRankPanel);
					});
				}
			}
		});
		
	}
}

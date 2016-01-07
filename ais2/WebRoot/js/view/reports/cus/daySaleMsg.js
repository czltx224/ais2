var dayprivilege=68;
var defaultWidth=60;
var mainSearchUrl='cus/sonderzugMsgAction!findSaleDayMsg.action';
var wholeSellFields,wholeSellStore,wholeSellPanel,departStore,countRangeStore,myCheckboxGroup,fields,mainStore,menuGrid;
Ext.onReady(function() {
	fields=[
		{name:'monTarNum',mapping:'MON_TAR_NUM'},
		{name:'dayAvg',mapping:'DAY_AVG'},
		{name:'dayNum',mapping:'DAY_NUM'},
		{name:'dayRate',mapping:'DAY_RATE'},
		{name:'addNum',mapping:'ADD_NUM'},
		{name:'addRate',mapping:'ADD_RATE'},
		{name:'shouldNum',mapping:'SHOULD_NUM'},
		{name:'shouldDiffer',mapping:'SHOULD_DIFFER'},
		{name:'tarDiffer',mapping:'TAR_DIFFER'},
		{name:'remainDayNum',mapping:'REMAIN_DAY_NUM'},
		{name:'cusDepartName',mapping:'CUS_DEPART_NAME'},
		{name:'customerService',mapping:'CUSTOMER_SERVICE'},
		{name:'dutyName',mapping:'DUTY_NAME'}
	];
	wholeSellFields=[
		
	];
	mainStore=new Ext.data.Store({
		storeId:"mainStore",
		baseParams:{limit:pageSize,privilege:dayprivilege},
		proxy:new Ext.data.HttpProxy({url:sysPath+'/'+mainSearchUrl}),
		reader:new Ext.data.JsonReader({
                    root:'resultMap',
                    totalProperty:'totalCount'
        },fields)
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
	wholeSellStore=new Ext.data.Store({
		storeId:"wholeSellStore",
		baseParams:{limit:pageSize,privilege:dayprivilege},
		proxy:new Ext.data.HttpProxy({url:sysPath+'/cus/sellAnalyseAction!findWholeSellMsg.action'}),
		reader:new Ext.data.JsonReader({
			root:'resultMap',
            totalProperty:'totalCount'
        },wholeSellFields)
	});
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
    //start
    var sm = new Ext.grid.CheckboxSelectionModel({});
    var rowNum = new Ext.grid.RowNumberer({
        header:'序号', width:35, sortable:true
    });

    /*
    var targetradio=new Ext.form.Radio({
    		id:'targetradio',
	        boxLabel:'指标',
	        inputValue:'1',
	        checked:true,
	        listeners:{
	            'check':function(){
	                if(targetradio.getValue()){
	                    targetRateradio.setValue(false);
	                    targetradio.setValue(true);
	                }
	            }
           }
	    });
    var targetRateradio=new Ext.form.Radio({
        boxLabel:'完成率',
        id:'targetRateradio',
        inputValue:'0',
        listeners:{
            'check':function(){
                if(targetRateradio.getValue()){
                    targetradio.setValue(false);
                    targetRateradio.setValue(true);
                }
            }
        }
    });
    */
    var continentGroupRow = [
    	{colspan: 3},
        {header: '日销售额',align: 'center',colspan: 3},
        {header: '本月累计销售额',align: 'center',colspan: 3}
    ];

    var togroup = new Ext.ux.grid.ColumnHeaderGroup({
        rows: [continentGroupRow]
    });

     menuGrid = new Ext.grid.GridPanel({
        //renderTo:'showView',
        height:Ext.lib.Dom.getViewHeight()-30, 
        width:Ext.lib.Dom.getViewWidth()-10,
        frame : false,
    	loadMask : true,
    	stripeRows : true,
    	viewConfig:{
    		scrollOffset: 0,
    		autoScroll : true
    	},
        cm:new Ext.grid.ColumnModel([rowNum,
            {header:'部门',dataIndex:"cusDepartName",width:120},
            {header:"负责人",dataIndex:"dutyName",width:50},
            {header:"本月指标",dataIndex:"monTarNum",width:80,sortable:true},
            {header:"日均指标",dataIndex:"dayAvg",width:70,sortable:true},
            {header:'当日完成',dataIndex:'dayNum',width:70,sortable:true},
            {header:"当日完成率",dataIndex:"dayRate",width:80,sortable:true},
            {header:"累计完成",dataIndex:"addNum",width:80,sortable:true},
            {header:'累计完成率',dataIndex:'addRate',width:80,sortable:true},
            {header:"应完成进度",dataIndex:"shouldNum",width:80,sortable:true},
            {header:"超额完成",dataIndex:"shouldDiffer",width:80,sortable:true},
            {header:"指标差额",dataIndex:"tarDiffer",width:80,sortable:true},
            {header:"剩余日均指标",dataIndex:"remainDayNum",width:80,sortable:true}
           
        ]),
       // plugins: togroup,
        store:mainStore,
        tbar: [{text:'<b>导出</b>',iconCls:'sort_down',handler:function() {
                	parent.exportExl(menuGrid);
        	} },'-',
            '统计日期:',
            {
				xtype : 'datefield',
				format : 'Y-m-d',
				width : 100,
				value : new Date(),
				blankText : "[日期]不能为空！",
				name : 'startDate',
				id : 'startDate'
			},
            '-',
            '统计类型:',
            {
				xtype : 'combo',
    			id:'daycountType',
    			value:'收入',
    			name : 'daycountType',
    			forceSelection : true,
				triggerAction : 'all',
				model : 'local',
				store:[
					['收入','收入'],
					['货量','货量'],
					['票数','票数']
				],
    			width : 70,
        		allowBlank:false,
    			blankText:'统计类型不能为空！'
			},'部门:',
			{
				xtype : 'combo',
				id:'comboTypeDepart',
				hiddenId : 'departId',
    			hiddenName : 'departId',
    			queryParam :'filter_LIKES_departName',
				triggerAction : 'all',
				store : departStore,
				width:120,
				listWidth:245,
				minChars : 0,
				forceSelection : true,
				fieldLabel:'部门名称',
				pageSize:pageSize,
				displayField : 'departName',//显示值，与fields对应
				valueField : 'departId',//value值，与fields对应
				name : 'departId'
			},'数据类型：',{
				xtype:'combo',
				name:'numberType',
				id:'numberTypecombo',
				value:'客服部门',
				forceSelection : true,
				triggerAction : 'all',
				model : 'local',
				width:100,
				editable:false,
				store:[
					['客服部门','客服部门'],
					['客服员','客服员']
				]
			},
            '-',
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
            privilege:dayprivilege, 
            store: mainStore, 
            displayInfo: true,
            displayMsg: '显示第 {0} 条到 {1} 条记录，共 {2} 条', emptyMsg: "没有记录信息显示"
        })
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
   		wholeSellPanel.getTopToolbar().remove(startone);
		wholeSellPanel.getTopToolbar().remove(endone);
	}
	function addOne(){
		wholeSellPanel.getTopToolbar().insert(6,startone);
		wholeSellPanel.getTopToolbar().insert(8,endone);
	}
	myCheckboxGroup = new Ext.form.CheckboxGroup({    
        id:'myGroup',
        width:100,
        vertical :true,
        height:25,
        itemCls: 'x-check-group-alt',
        columns :2,
	    items: [    
	       {boxLabel: '指标', name: 'target',inputValue:'target'},    
	       {boxLabel: '完成率', name: 'targetRate',inputValue:'targetRate'}      
	    ]
   });    
       
   //CheckboxGroup取值方法   
    wholeSellPanel=new Ext.grid.GridPanel({
        id:'wholeSellPanel',
        height:Ext.lib.Dom.getViewHeight()-35, 
        width:Ext.lib.Dom.getViewWidth()-10,
        frame : false,
    	loadMask : true,
    	stripeRows : true,
    	viewConfig:{
    		scrollOffset: 0,
    		autoScroll : true
    	},
        cm:new Ext.grid.ColumnModel([rowNum,
            {header:'部门',dataIndex:"cusDepartName",width:80},
            {header:"1月",dataIndex:"dutyName",width:80},
            {header:"2月",dataIndex:"dayTicket",width:80},
            {header:"3月",dataIndex:"dayWeight",width:80},
            {header:'4月',dataIndex:'dayIncome',width:80},
            {header:'5月',dataIndex:'dayIncome',width:80},
            {header:'6月',dataIndex:'dayIncome',width:80},
            {header:'7月',dataIndex:'dayIncome',width:80},
            {header:'8月',dataIndex:'dayIncome',width:80},
            {header:'9月',dataIndex:'dayIncome',width:80},
            {header:'10月',dataIndex:'dayIncome',width:80},
            {header:'11月',dataIndex:'dayIncome',width:80},
            {header:'12月',dataIndex:'dayIncome',width:80}
        ]),
        store:wholeSellStore,
        tbar: [{text:'<b>导出</b>',iconCls:'sort_down',handler:function() {
                	parent.exportExl(wholeSellPanel);
        	} },
        	'-','统计维度',{
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
					wholeSellPanel.getTopToolbar().doLayout(true);
				}
			}
			},
			'开始:',startone,' 至 ',endone,
            '-',
            '统计类型:',
            {
				xtype : 'combo',
    			id:'countType',
    			value:'收入',
    			name : 'countType',
    			forceSelection : true,
				triggerAction : 'all',
				model : 'local',
				store:[
					['收入','收入'],
					['货量','货量'],
					['票数','票数']
				],
    			width : 70,
        		allowBlank:false,
    			blankText:'统计类型不能为空！'
			},
            '-','部门:',
			{
				xtype : 'combo',
				id:'salecomboTypeDepart',
				hiddenId : 'departId',
    			hiddenName : 'departId',
    			queryParam :'filter_LIKES_departName',
				triggerAction : 'all',
				store : departStore,
				width:100,
				listWidth:245,
				minChars : 0,
				forceSelection : true,
				fieldLabel:'部门名称',
				pageSize:pageSize,
				displayField : 'departName',//显示值，与fields对应
				valueField : 'departId',//value值，与fields对应
				name : 'departId'
			},'-',myCheckboxGroup,'-',
            {
            	text:'<B>搜索</B>',
            	id:'btn', 
            	iconCls: 'btnSearch',
            	hidden:false,
            	handler : searchWholeMsg
            }
        ],
        bbar : new Ext.PagingToolbar({
			pageSize : pageSize, 
			store : wholeSellStore,
			displayInfo : true,
			displayMsg : '显示第 {0} 条到 {1} 条记录，共 {2} 条',
			emptyMsg : "没有记录信息显示"
		})
   	   });
   	//销售指标Panel
   	   /*
    sellTargetPanel = new Ext.grid.GridPanel({
        id:'sellTargetPanel',
        height:Ext.lib.Dom.getViewHeight()-20, 
        width:Ext.lib.Dom.getViewWidth(),
        viewConfig : {
			columnsText : "显示的列",
			forceFit : true
		},
		autoScroll:true, 
		autoExpandColumn:1,
        frame:true, 
        loadMask:true,
        cm:new Ext.grid.ColumnModel([rowNum,
             {header:'部门',dataIndex:"cusDepartName",width:80},
            {header:"1月",dataIndex:"dutyName",width:80},
            {header:"2月",dataIndex:"dayTicket",width:80},
            {header:"3月",dataIndex:"dayWeight",width:80},
            {header:'4月',dataIndex:'dayIncome',width:80},
            {header:'5月',dataIndex:'dayIncome',width:80},
            {header:'6月',dataIndex:'dayIncome',width:80},
            {header:'7月',dataIndex:'dayIncome',width:80},
            {header:'8月',dataIndex:'dayIncome',width:80},
            {header:'9月',dataIndex:'dayIncome',width:80},
            {header:'10月',dataIndex:'dayIncome',width:80},
            {header:'11月',dataIndex:'dayIncome',width:80},
            {header:'12月',dataIndex:'dayIncome',width:80}
           
        ]),
        store:sellTargetStore,
        tbar: [{text:'<b>导出</b>',iconCls:'sort_down',handler:function() {
                	parent.exportExl(sellTargetPanel);
        	} },'-',
            '统计日期:',
            {
				xtype : 'combo',
    			id:'countDateTarget',
    			value:new Date().getYear()+'-01-01',
    			name : 'countDateTarget',
    			forceSelection : true,
    			editable:false,
				triggerAction : 'all',
				model : 'local',
    			width : 100,
    			store:[
    				['2009-01-01','2009'],
					['2010-01-01','2010'],
					['2011-01-01','2011'],
					['2012-01-01','2012'],
					['2013-01-01','2013'],
					['2014-01-01','2014'],
					['2015-01-01','2015'],
					['2016-01-01','2016'],
					['2017-01-01','2017'],
					['2018-01-01','2018'],
					['2019-01-01','2019'],
					['2020-01-01','2020'],
					['2021-01-01','2021']
    			]
			},'-',
            '指标类型:',
            {
				xtype : 'combo',
    			id:'targetType',
    			value:'收入',
    			name : 'targetType',
    			forceSelection : true,
				triggerAction : 'all',
				model : 'local',
				store:[
					['收入','收入'],
					['货量','货量'],
					['票数','票数']
				],
    			width : 100,
        		allowBlank:false,
    			blankText:'指标类型不能为空！'
			},
			'统计区域:',
            {
				xtype : 'combo',
    			id:'countAreaTar',
    			value:'depart',
    			name : 'countAreaTar',
    			forceSelection : true,
				triggerAction : 'all',
				model : 'local',
				store:[
					['area','区域'],
					['depart','客服部门']
				],
    			width : 100,
        		allowBlank:false,
    			blankText:'统计区域不能为空！'
			},'-',
			targetradio,targetRateradio,
            '-',
            {
            	text:'<B>搜索</B>',
            	id:'btnt', 
            	iconCls: 'btnSearch',
            	hidden:false,
            	handler : searchSellTarget
            }
        ]
    });
    */
    /**
     * 指标完成率Panel
     */
    /*
    finishRatePanel=new Ext.grid.GridPanel({
        id:'finishRatePanel',
        height:Ext.lib.Dom.getViewHeight()-20, 
        width:Ext.lib.Dom.getViewWidth(),
        viewConfig : {
			columnsText : "显示的列",
			forceFit : true
		},
		autoScroll:true, 
		autoExpandColumn:1,
        frame:true, 
        loadMask:true,
        cm:new Ext.grid.ColumnModel([rowNum,
             {header:'部门',dataIndex:"cusDepartName",width:80},
            {header:"1月",dataIndex:"dutyName",width:80},
            {header:"2月",dataIndex:"dayTicket",width:80},
            {header:"3月",dataIndex:"dayWeight",width:80},
            {header:'4月',dataIndex:'dayIncome',width:80},
            {header:'5月',dataIndex:'dayIncome',width:80},
            {header:'6月',dataIndex:'dayIncome',width:80},
            {header:'7月',dataIndex:'dayIncome',width:80},
            {header:'8月',dataIndex:'dayIncome',width:80},
            {header:'9月',dataIndex:'dayIncome',width:80},
            {header:'10月',dataIndex:'dayIncome',width:80},
            {header:'11月',dataIndex:'dayIncome',width:80},
            {header:'12月',dataIndex:'dayIncome',width:80}
           
        ]),
        store:finishRateStore,
        tbar: [{text:'<b>导出</b>',iconCls:'sort_down',handler:function() {
                	parent.exportExl(finishRatePanel);
        	} },'-',
            '统计日期:',
            {
				xtype : 'combo',
    			id:'countDateFinish',
    			value:'2011-01-01',
    			name : 'countDateFinish',
    			forceSelection : true,
    			editable:false,
				triggerAction : 'all',
				model : 'local',
    			width : 100,
    			store:[
    				['2009-01-01','2009'],
					['2010-01-01','2010'],
					['2011-01-01','2011'],
					['2012-01-01','2012'],
					['2013-01-01','2013'],
					['2014-01-01','2014'],
					['2015-01-01','2015'],
					['2016-01-01','2016'],
					['2017-01-01','2017'],
					['2018-01-01','2018'],
					['2019-01-01','2019'],
					['2020-01-01','2020']
    			]
			},'-',
            '指标类型:',
            {
				xtype : 'combo',
    			id:'finishType',
    			value:'收入',
    			name : 'finishType',
    			forceSelection : true,
				triggerAction : 'all',
				model : 'local',
				store:[
					['收入','收入'],
					['货量','货量'],
					['票数','票数']
				],
    			width : 100,
        		allowBlank:false,
    			blankText:'指标类型不能为空！'
			},
			'统计区域:',
            {
				xtype : 'combo',
    			id:'countAreaFinish',
    			value:'depart',
    			name : 'countAreaFinish',
    			forceSelection : true,
				triggerAction : 'all',
				model : 'local',
				store:[
					['area','区域'],
					['depart','客服部门']
				],
    			width : 100,
        		allowBlank:false,
    			blankText:'统计区域不能为空！'
			},
            '-',
            {
            	text:'<B>搜索</B>',
            	id:'btnf', 
            	iconCls: 'btnSearch',
            	hidden:false,
            	handler : searchFinishRate
            }
        ]
    });*/
     function searchCusRequest() {
     	if(Ext.getCmp('startDate').isValid()){
     		var cmItems = []; 
			var afields=[];
			
			var sm = new Ext.grid.CheckboxSelectionModel({});
		    var rowNum = new Ext.grid.RowNumberer({
		        header:'序号', width:35, sortable:true
		    });
    
     		var startDate=Ext.get('startDate').dom.value;
     		var countType = Ext.getCmp('daycountType').getValue();
     		var departId = Ext.getCmp('comboTypeDepart').getValue();
     		var numType = Ext.getCmp('numberTypecombo').getValue();
     		
     		if(numType == '客服部门'){
     			cmItems.push(rowNum);
				afields.push('CUS_DEPART_NAME');
				cmItems.push({header:'部门',dataIndex:"CUS_DEPART_NAME",width:120});
				afields.push('DUTY_NAME');
				cmItems.push({header:"负责人",dataIndex:"DUTY_NAME",width:50});
     		}else{
     			cmItems.push(rowNum);
				afields.push('CUS_DEPART_NAME');
				cmItems.push({header:'部门',dataIndex:"CUS_DEPART_NAME",width:120});
     			afields.push('CUSTOMER_SERVICE');
				cmItems.push({header:"客服员",dataIndex:"CUSTOMER_SERVICE",width:80});
     		}
     		afields.push('MON_TAR_NUM');
			cmItems.push({header:"本月指标",dataIndex:"MON_TAR_NUM",width:80,sortable:true});
			afields.push('DAY_AVG');
			cmItems.push({header:"日均指标",dataIndex:"DAY_AVG",width:70,sortable:true});
			afields.push('DAY_NUM');
			cmItems.push({header:'当日完成',dataIndex:'DAY_NUM',width:70,sortable:true});
			afields.push('DAY_RATE');
			cmItems.push({header:"当日完成率",dataIndex:"DAY_RATE",width:80,sortable:true});
			afields.push('ADD_NUM');
			cmItems.push({header:"累计完成",dataIndex:"ADD_NUM",width:80,sortable:true});
			afields.push('ADD_RATE');
			cmItems.push({header:'累计完成率',dataIndex:'ADD_RATE',width:80,sortable:true});
			afields.push('SHOULD_NUM');
			cmItems.push({header:"应完成进度",dataIndex:"SHOULD_NUM",width:80,sortable:true});
			afields.push('SHOULD_DIFFER');
			cmItems.push({header:"超额完成",dataIndex:"SHOULD_DIFFER",width:80,sortable:true});
			afields.push('TAR_DIFFER');
			cmItems.push({header:"指标差额",dataIndex:"TAR_DIFFER",width:80,sortable:true});
			afields.push('REMAIN_DAY_NUM');
			cmItems.push({header:"剩余日均指标",dataIndex:"REMAIN_DAY_NUM",width:80,sortable:true,renderer:function(v){
				if(Number(v)<0){
					return '<span style="font-weight:bold;color:red">已完成</span>';
				}else{
					return v;
				}
			}});
			
			mainStore=new Ext.data.Store({
				storeId:"mainStore",
				baseParams:{limit:pageSize,privilege:dayprivilege},
				proxy:new Ext.data.HttpProxy({url:sysPath+'/'+mainSearchUrl}),
				reader:new Ext.data.JsonReader({
		                    root:'resultMap',
		                    totalProperty:'totalCount'
		        },afields)
			});
		    fields=afields;
			menuGrid.reconfigure(mainStore,new Ext.grid.ColumnModel(cmItems));
			
			
			mainStore.baseParams = {
				saleDate:startDate,
				countType:countType,
				departId:departId,
				numberType:numType
			}
			mainStore.load();
			
			mainStore.on('load',function(){
			 	  var record = mainStore.getAt(mainStore.getCount()-1);
			 	  if(numType == '客服部门'){
			 	  	if(record.get("CUS_DEPART_NAME") == '合计'){
				 	  	mainStore.remove(record);
				 	  }
			 	  }else{
			 	  	if(record.get("CUSTOMER_SERVICE") == '合计'){
				 	  	mainStore.remove(record);
				 	  }
			 	  }
			 	  var dayNums = 0;//当日完成
				  var addNums = 0;//累计完成
				  var monNums = 0;//本月指标
				  var dayAvg = 0;//日均指标
				  var tarDiffer = 0;//指标差额
				  var remainDayNum = 0;
			 	  for(var i=0;i<mainStore.getCount();i++){
			 	  		dayNums += Number(mainStore.getAt(i).get('DAY_NUM'));
			 	  		addNums += Number(mainStore.getAt(i).get('ADD_NUM'));
			 	  		monNums += Number(mainStore.getAt(i).get('MON_TAR_NUM'));
			 	  		dayAvg += Number(mainStore.getAt(i).get('DAY_AVG'));
			 	  		tarDiffer += Number(mainStore.getAt(i).get('TAR_DIFFER'));
			 	  		remainDayNum += Number(mainStore.getAt(i).get('REMAIN_DAY_NUM'));
			 	  		
			 	  }
			 	  if(mainStore.getCount()>0){
			 	  	 var shouldNum = mainStore.getAt(0).get('SHOULD_NUM');
			 	  	 var shouldN = Number(shouldNum.substring(0,shouldNum.length-1));
//			 	  	 if(numType == '客服部门'){
			 	  	  var store=new Ext.data.Record.create([{name:'CUS_DEPART_NAME'},{name:'DAY_NUM'},{name:'ADD_NUM'},{name:'MON_TAR_NUM'}]);
			 	  	  var noFaxRecord=new store();
					  noFaxRecord.set("CUS_DEPART_NAME",'合计');
						  noFaxRecord.set("DAY_NUM",Number(dayNums).toFixed(2));
					  noFaxRecord.set("ADD_NUM",Number(addNums).toFixed(2));
					  noFaxRecord.set("MON_TAR_NUM",Number(monNums).toFixed(2));
					  noFaxRecord.set("DAY_AVG",Number(dayAvg).toFixed(2));
					  noFaxRecord.set("TAR_DIFFER",Number(tarDiffer).toFixed(2));
					  noFaxRecord.set("SHOULD_NUM",shouldNum);
					  noFaxRecord.set("REMAIN_DAY_NUM",Number(remainDayNum).toFixed(2));
					  

					  noFaxRecord.set("DAY_RATE",Number((dayNums/(dayAvg == 0?1:dayAvg))*100).toFixed(2)+'%');
					  noFaxRecord.set("ADD_RATE",Number((addNums/(monNums == 0?1:monNums))*100).toFixed(2)+'%');
					  noFaxRecord.set("SHOULD_DIFFER",Number((addNums/(monNums == 0?1:monNums))*100-shouldN).toFixed(2)+'%');
					  
					  mainStore.add(noFaxRecord);
//				 	  }else{
//				 	  	 var store=new Ext.data.Record.create([{name:'CUSTOMER_SERVICE'},{name:'DAY_NUM'},{name:'ADD_NUM'},{name:'MON_TAR_NUM'}]);
//				 	  	 var noFaxRecord=new store();
//						 noFaxRecord.set("CUSTOMER_SERVICE",'合计');
//						 noFaxRecord.set("DAY_NUM",dayNums);
//						 noFaxRecord.set("ADD_NUM",addNums);
//						 noFaxRecord.set("MON_TAR_NUM",monNums);
//						 noFaxRecord.set("DAY_AVG",dayAvg);
//						 noFaxRecord.set("TAR_DIFFER",tarDiffer);
//						 noFaxRecord.set("REMAIN_DAY_NUM",remainDayNum);
//						 noFaxRecord.set("SHOULD_NUM",shouldNum);
//						 
//						 noFaxRecord.set("DAY_RATE",Number((dayNums/(dayAvg == 0?1:dayAvg))*100).toFixed(2)+'%');
//						 noFaxRecord.set("ADD_RATE",Number((addNums/(monNums == 0?1:monNums))*100).toFixed(2)+'%');
//						 noFaxRecord.set("SHOULD_DIFFER",Number((addNums/(monNums == 0?1:monNums))*100-shouldN).toFixed(2)+'%');
//						 mainStore.add(noFaxRecord);
//				 	  }
			 	  }
				  
				    var strXml='';
			    	var strCate ='<categories>';
			    	var strDataSet='';
			    	for(var j=0;j<mainStore.getCount()-1;j++){
			    		if(numType == '客服部门'){
					 	  	strCate+="<category label='"+mainStore.getAt(j).get('CUS_DEPART_NAME').substring(4,mainStore.getAt(j).get('CUS_DEPART_NAME').length)+"'/>";
					 	  }else{
					 	  	strCate+="<category label='"+mainStore.getAt(j).get('CUSTOMER_SERVICE')+"'/>";
					 	  }
			    	}
					strCate+='</categories>';
					strDataSet+="<dataset seriesName='累计完成' color='50BD4A' plotBorderColor='50BD4A' renderAs='column'>";
					for(var j=0;j<mainStore.getCount()-1;j++){
		    			strDataSet+="<set value='"+mainStore.getAt(j).get('ADD_NUM')+"'/>";
		    		}
					strDataSet+="</dataset>";
					strDataSet+="<dataset seriesName='本月指标' renderAs='area'>";
					for(var j=0;j<mainStore.getCount()-1;j++){
		    			strDataSet+="<set value='"+mainStore.getAt(j).get('MON_TAR_NUM')+"'/>";
		    		}
					strDataSet+="</dataset>";
					var styles="<styles><definition><style name='captionFont' type='font' size='15' /></definition><application><apply toObject='caption' styles='captionfont' /></application></styles>";
					strXml="<chart caption='指标完成情况' showLabels='1' showValues='0'  plotFillAlpha='70' numVDivLines='10' showAlternateVGridColor='1' canvasBorderThickness='1' showPlotBorder='0' plotBorderThickness='0' startAngX='7' endAngX='7' startAngY='-18' endAngY='-18' zgapplot='20' zdepth='60' exeTime='2' chartOrder='column,area'>"+strCate+strDataSet+styles+"</chart>";
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
						chartURL:'chars/fcf/MSCombi3D.swf',
						dataXML:strXml,
						width:Ext.lib.Dom.getViewWidth()-20,
						height:400
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
    var tablePanel=new Ext.TabPanel({
	   	renderTo:Ext.getBody(),
	    id: "mainTab",   
	    activeTab: 0,
	    width:Ext.lib.Dom.getViewWidth()-5,
	    height:Ext.lib.Dom.getViewHeight(),
	    defaults: {   
	        autoScroll: true,   
	        autoHeight:true,   
	        style: "padding:0 0 0 0"
	    },   
	    items:[   
	       {title:"销售日报表", tabTip:"销售日报表",items:[menuGrid]},
	       {title:"销售趋势",tabTip:'销售趋势',items:[wholeSellPanel]}
	       //{title:"营销指标设置",tabTip:'营销指标设置',items:[cusTargetGrid]}
		]
	});
    //end
});
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
/**
 * 销售完成情况统计
 */
function searchWholeMsg(){
	var countType=Ext.getCmp('countType').getValue();
	var startCount = Ext.get('startone').dom.value;
	var endCount = Ext.get('endone').dom.value;
	var departId = Ext.getCmp('salecomboTypeDepart').getValue();
	var countRange = Ext.getCmp('searchCountRange').getValue();
	var group = Ext.getCmp('myGroup');
	var groups="";
	var groupL = group.items.length;
	var j=0;
	for(var i=0;i<groupL;i++){
		if(group.items.itemAt(i).checked){
			j++;
			if(j>1){
				groups+=',';	
			}
			groups+=group.items.itemAt(i).getRawValue();
		}
	}
	if(countRange == '日'){
		var sDate = Date.parseDate(startCount,'Y-m-d');
		var eDate = Date.parseDate(endCount,'Y-m-d');
		
		if(sDate.getMonth()!=eDate.getMonth()){
			Ext.Msg.alert(alertTitle,'请勿跨月统计');
			return ;
		}
	}
	var sm = new Ext.grid.CheckboxSelectionModel({});
    var rowNum = new Ext.grid.RowNumberer({
        header:'序号', width:35, sortable:true
    });
	if(Ext.getCmp('startone').isValid()&&Ext.getCmp('endone').isValid()&&Ext.getCmp('countType').isValid()){
		var cmItems = []; 
		var afields=[];
		var showafields=[];
		showafields.push('CUS_DEPART_NAME')
		cmItems.push(rowNum);
		//统计区域
		afields.push('CUS_DEPART_NAME');
		cmItems.push({header:'部门名称',dataIndex:'CUS_DEPART_NAME',width:120});
		//实际维度
		/*
		if(dateType=='月'){
			//统计类型
			if(countType=='income'){
				for(var i=1;i<=12;i++){
					afields.push({name:i+'月'});
					cmItems.push({header:i+'月(万元)',dataIndex:i+'月',width:80,renderer:function(v){return v/10000;}});
				}
			}else if(countType=='weight'){
				for(var i=1;i<=12;i++){
					afields.push({name:i+'月'});
					cmItems.push({header:i+'月(吨)',dataIndex:i+'月',width:80,renderer:function(v){return v/1000;}});
				}
			}else{
				for(var i=1;i<=12;i++){
					afields.push({name:i+'月'});
					cmItems.push({header:i+'月(千票)',dataIndex:i+'月',width:80,renderer:function(v){return v/1000;}});
				}
			}
		}else{
			var date=new Date(countDate);
			//统计类型
			if(countType=='income'){
				for(var i=1;i<=date.getDaysInMonth();i++){
					afields.push({name:i+'日'});
					cmItems.push({header:i+'日(元)',dataIndex:i+'日',width:80});
				}
			}else if(countType=='weight'){
				for(var i=1;i<=date.getDaysInMonth();i++){
					afields.push({name:i+'日'});
					cmItems.push({header:i+'日(kg)',dataIndex:i+'日',width:80});
				}
			}else{
				for(var i=1;i<=date.getDaysInMonth();i++){
					afields.push({name:i+'日'});
					cmItems.push({header:i+'日(票)',dataIndex:i+'日',width:80});
				}
			}
		}
		wholeSellFields = afields;
		wholeSellStore=new Ext.data.Store({
			storeId:"wholeSellStore1",
			baseParams:{limit:pageSize,privilege:privilege},
			proxy:new Ext.data.HttpProxy({url:sysPath+'/cus/sellAnalyseAction!findWholeSellMsg.action'}),
			reader:new Ext.data.JsonReader({
				root:'resultMap',
            	totalProperty:'totalCount'
	        },afields)
		});
		wholeSellPanel.reconfigure(wholeSellStore,new Ext.grid.ColumnModel(cmItems));
		if(dateType=='日'){
			countDate=countDate.format('Y-m-d');
		}*/
		Ext.apply(wholeSellStore.baseParams,{
			countType:countType,
			countRange:countRange,
			limit:pageSize,
			start:0,
			departId:departId,
			groups:groups,
			startCount:startCount,
			endCount:endCount
		});
		wholeSellStore.load();
		wholeSellStore.on('load',function(){
			var flag = false;
			colWidth=100;
			reDoGrid(cmItems,afields,wholeSellStore,wholeSellPanel);
			var unit='';
	    	if(countType=='货量'){
				unit='吨';
			}else if(countType=='收入'){
				unit='万元';
			}else{
				unit='千票';
			}
			/*
			if((groups != '')){
				var g = groups.split(',');
				if(g.length>1){
					flag = true;
				}else if(g.length==1){
					if(g == 'target'){
						flag = true;
					}
				}
			}*/
			//if(flag){
				var strXml='';
		    	var strCate='<categories>';
		    	var strDataSet='';
				var myarr = timeHead(wholeSellStore,showafields);
		    	for(var j=0;j<myarr.length;j++){
		    		if(myarr[j].indexOf('指标')<0 && myarr[j].indexOf('完成率')<0){
		    			strCate+="<category label='"+myarr[j]+"'/>";
		    		}
		    	}
				strCate+='</categories>';
				for(var i=0;i<wholeSellStore.getCount();i++){
					var menuRecord = wholeSellStore.getAt(i);
	    			var json = menuRecord.json;
	    			menuRecord.data = menuRecord.json;
		    		strDataSet+="<dataset seriesName='"
		    		strDataSet+=menuRecord.get('CUS_DEPART_NAME');
					strDataSet+="'>";
					for(var j=0;j<myarr.length;j++){
		    			if(myarr[j].indexOf('指标')<0 && myarr[j].indexOf('完成率')<0){
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
		    		}
					strDataSet+="</dataset>";
				}
				/*
				strDataSet+="<dataset seriesName='实际完成' color='B1D1DC' plotBorderColor='B1D1DC' renderAs='column'>";
				var departName = "";
				for(var j=0;j<myarr.length;j++){
					var menuRecord = wholeSellStore.getAt(0);
	    			var json = menuRecord.json;
	    			menuRecord.data = menuRecord.json;
	    			departName = menuRecord.get('CUS_DEPART_NAME');
	    			if(myarr[j].indexOf('指标')<0 && myarr[j].indexOf('完成率')<0){
	    				var value=menuRecord.get(myarr[j]);
	    				strDataSet+="<set value='"+value+"'/>";
	    			}
	    		}
				strDataSet+="</dataset>";
				strDataSet+="<dataset seriesName='指标值' color='C8A1D1' plotBorderColor='C8A1D1' renderAs='area'>";
				for(var j=0;j<myarr.length;j++){
					var menuRecord = wholeSellStore.getAt(0);
	    			var json = menuRecord.json;
	    			menuRecord.data = menuRecord.json;
	    			departName = menuRecord.get('CUS_DEPART_NAME');
					if(myarr[j].indexOf('指标')>=0){
	    				var value=menuRecord.get(myarr[j]);
	    				strDataSet+="<set value='"+value+"'/>";
	    			}
	    		}
				strDataSet+="</dataset>";
				*/
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
					height:400
				});
				var div =document.getElementById('showView');
				while(div.hasChildNodes()) //当div下还存在子节点时 循环继续
				{	
					div.removeChild(div.firstChild);
				}
				fusionPanel.render('showView');
			//}
		});
	}
}

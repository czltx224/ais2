var operTitle;
var departFields=[
	{name:'cusDepartCode',mapping:'CUS_DEPART_CODE'},
	{name:'cusDepartName',mapping:'CUS_DEPART_NAME'},
	//{name:'signFee',mapping:'SIGN_FEE'},
	//{name:'fatFee',mapping:'FAT_FEE'},
	//{name:'instoreFee',mapping:'INSTORE_FEE'},
	//{name:'carryFee',mapping:'CARRY_FEE'},
	//{name:'storageFee',mapping:'STORAGE_FEE'},
	//{name:'comeupFee',mapping:'COMEUP_FEE'},
	{name:'addsumFee',mapping:'ADDSUM_FEE'},
	//{name:'income',mapping:'INCOME'},
	{name:'feeRate',mapping:'FEE_RATE'}
];
var menuField=[
	{name:'FEE_NAME'},
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
var departFeeStore,menuStore,goodsRankStore,carTypeStore,departFeePanel,menuGrid,loopthanPanel;
Ext.onReady(function() {
    //年度统计分析Store
    menuStore = new Ext.data.Store({
        storeId:"menuStore",
        baseParams:{limit:pageSize},
        proxy: new Ext.data.HttpProxy({url:sysPath+"/stock/oprValueAddFeeAction!findAddFeeMsg.action"}),
        reader: new Ext.data.JsonReader({
        }, menuField)
    });
    //部门增值收入Store
	departFeeStore = new Ext.data.Store({
        storeId:"departFeeStore",
        baseParams:{limit:pageSize},
        proxy: new Ext.data.HttpProxy({url:sysPath+"/stock/oprValueAddFeeAction!findDepartFeeMsg.action"}),
        reader: new Ext.data.JsonReader({
        }, departFields)
    });
    var departStore = new Ext.data.Store({ 
            storeId:"departStore",                        
            proxy: new Ext.data.HttpProxy({url:sysPath+"/sys/departAction!ralaList.action",method:'post'}),
            baseParams:{privilege:53},
            reader: new Ext.data.JsonReader({
            root: 'result', totalProperty:'totalCount'
           }, [  
                 {name:'departName', mapping:'departName'},    
                 {name:'departId', mapping: 'departId'},
                 {name:'departNo', mapping: 'departNo'}
              ])
     });
    //环比/同比Store
    loopthanStore=new Ext.data.Store({
        storeId:"loopthanStore",
        baseParams:{limit:pageSize},
        proxy: new Ext.data.HttpProxy({url:sysPath+"/stock/oprValueAddFeeAction!findFeeThen.action"}),
        reader: new Ext.data.JsonReader({
        }, goodRankField)
    });
    var sm = new Ext.grid.CheckboxSelectionModel({});
    var rowNum = new Ext.grid.RowNumberer({
        header:'序号', width:35, sortable:true
    });
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
            	id:'btnSearchdepart', 
            	iconCls: 'btnSearch',
            	hidden:false,
            	handler : searchCusRequest
            }
        ]
    });
    //增值服务费月度统计Panel
     menuGrid = new Ext.grid.GridPanel({
    	id : 'myrecordGrid',
    	region:'center',
    	height : Ext.lib.Dom.getViewHeight()-20,
    	width : Ext.lib.Dom.getViewWidth(),
    	frame : false,
    	loadMask : true,
    	stripeRows : true,
    	viewConfig:{
    		scrollOffset: 0,
    		autoScroll : true
    	},
        sm:sm,
        cm:new Ext.grid.ColumnModel([rowNum,
            {header:'费用名称',dataIndex:'FEE_NAME'},
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
        tbar: ['时间维度:',{
        			xtype : 'combo',
	    			id:'dateTypey',
	    			value:'月',
	    			name : 'dateTypey',
	    			forceSelection : true,
					triggerAction : 'all',
					model : 'local',
					store:[
						['月','月'],
						['日','日']
					],
	    			width : 80,
	        		allowBlank:false,
	    			blankText:'统计时间维度不能为空！',
	    			listeners:{
	    				'select':function(combo){
	    					if(combo.getValue()=="月"){
	    						Ext.getCmp('countDateDay').setDisabled(true);
	    						Ext.getCmp('countDateDay').hide();
	    						Ext.getCmp('countDateMon').setDisabled(false);
	    						Ext.getCmp('countDateMon').show();
	    					}else{
	    						Ext.getCmp('countDateMon').setDisabled(true);
	    						Ext.getCmp('countDateMon').hide();
	    						Ext.getCmp('countDateDay').setDisabled(false);
	    						Ext.getCmp('countDateDay').show();
	    					}
	    				}
	    			}
        		},'-','统计时间：',
            	{
            	xtype : 'combo',
    			id:'countDateMon',
    			value:new Date().getYear()+'-01-01',
    			name : 'countDate',
    			forceSelection : true,
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
    			],
    			allowBlank:false,
    			blankText:'统计时间不能为空！'
            },{
            	xtype:'datefield',
            	name:'countDateDay',
            	hidden:true,
            	width:100,
            	id:'countDateDay',
            	format:'Y-m-d',
            	value:new Date()
            },'-','部门名称:',{
            	xtype : 'combo',
				id:'comboTypeDepart',
    			name : 'departName',
    			queryParam:'filter_LIKES_departName',
				triggerAction : 'all',
				store : departStore,
				width:120,
				emptyText:'请选择',
				listWidth:245,
				minChars : 1,
				forceSelection : true,
				editable : true,
				pageSize:comboSize,
				displayField : 'departName',//显示值，与fields对应
				valueField : 'departNo'//value值，与fields对应
            	
            },'-',
            {
            	text:'<B>统计</B>',
            	id:'btnSearch', 
            	iconCls: 'btnSearch',
            	hidden:false,
            	handler : menuStoreReload
            }
        ]
    });
    //增值服务费环比Panel
    loopthanPanel=new Ext.grid.GridPanel({
		region:'center',
    	id : 'loopthanPanel',
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
            {header:'费用类型',dataIndex:"feeName"},
            {header:'收入增长',dataIndex:'sumWeight',xtype: 'numbercolumn',format:'0,000'}
        ]),
        store:loopthanStore,
        tbar: ['比较月份:',
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
            	handler : searchthan
            }
        ]
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
	       {title:"增值收入分析", tabTip:"增值收入分析",items:[menuGrid]},
	       {title:"各部门增值收入情况",tabTip:'各部门增值收入情况',items:[departFeePanel]},
	       {title:"增值收入环比/同比",tabTip:'增值收入环比/同比',items:[loopthanPanel]}
		]
	});
	
    //end
	    
	    //增值服务类型
	 carTypeStore= new Ext.data.Store({ 
		autoLoad:true, 
		storeId:"",
		baseParams:{filter_EQL_basDictionaryId:145,privilege:16},
		proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/dictionaryAction!ralaList.action"}),
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
        {name:'id',mapping:'id'},
    	{name:'typeCode',mapping:'typeCode'},
    	{name:'typeName',mapping:'typeName'}
    	])
	});
	carTypeStore.load();
});

function searchCusRequest() {
		var rowNum = new Ext.grid.RowNumberer({
	        header:'序号', width:35, sortable:true
	    });
		if(Ext.getCmp('wholeDate').isValid()&&Ext.getCmp('dateType').isValid()){
			var wholeDate=Ext.get('wholeDate').dom.value;
				
			
			var cmItems = []; 
			var afields=[];
			cmItems.push(rowNum);
			cmItems.push({header:'部门编码',dataIndex:'cusDepartCode',hidden: true, hideable: false});
			cmItems.push({header:'部门名称',dataIndex:'cusDepartName'});
			
			afields.push({name:'cusDepartCode',mapping:'CUS_DEPART_CODE'});
			afields.push({name:'cusDepartName',mapping:'CUS_DEPART_NAME'});
			afields.push({name:'feeIncome',mapping:'INCOME'});
			afields.push({name:'addsumFee',mapping:'ADDSUM_FEE'});
			afields.push({name:'feeRate',mapping:'FEE_RATE'});
			
			for(var i=0;i<carTypeStore.getCount();i++){
				//cmConfig={header:carTypeStore.getAt(i).get("typeName"),dataIndex :carTypeStore.getAt(i).get("typeName"),xtype: 'numbercolumn',format:'0,000'};
				afields.push({name:'m'+carTypeStore.getAt(i).get("id"),mapping:'M'+carTypeStore.getAt(i).get("id")});
				cmItems.push({header:carTypeStore.getAt(i).get("typeName"),dataIndex :'m'+carTypeStore.getAt(i).get("id"),xtype: 'numbercolumn',format:'0,000'});
	 		}
			cmItems.push({header:'增值收入合计',dataIndex:"addsumFee",xtype: 'numbercolumn',format:'0,000'});
	        cmItems.push({header:'营业额',dataIndex:"feeIncome",xtype: 'numbercolumn',format:'0,000'});
	        cmItems.push({header:'增值收入占比',dataIndex:"feeRate"});
	        
	       // Ext.apply(carTypeStore.fields,afields);
	        
	        //部门增值收入Store
			departFeeStore = new Ext.data.Store({
		        storeId:"departFeeStore1",
		        baseParams:{limit:pageSize},
		        proxy: new Ext.data.HttpProxy({url:sysPath+"/stock/oprValueAddFeeAction!findDepartFeeMsg.action"}),
		        reader: new Ext.data.JsonReader({
		        }, afields)
		    });
	       
	        departFeePanel.reconfigure(departFeeStore,new Ext.grid.ColumnModel(cmItems));
	         departFeeStore.load({
					params:{
						msgDate:wholeDate,
						dateType:Ext.getCmp('dateType').getValue()
					}
			});
	        departFeePanel.doLayout();
	        departFeeStore.on('load',function(){
		    	var strXml='';
		    	var strCate='<categories>';
		    	var strDataSet='';
		    	var sumSet='';
		    	var lineSet='';
		    	for(var i=0;i<departFeeStore.getCount();i++){
		    		var value=departFeeStore.getAt(i).get("feeRate").trim();
		    		var numVal=value.substring(0,(value).length-1);
		    		sumSet+="<set value='"+departFeeStore.getAt(i).get("addsumFee")+"'/>";
		    		lineSet+="<set value='"+numVal+"'/>";
		    		strCate+="<category label='"+departFeeStore.getAt(i).get("cusDepartName")+"'/>";
		    	}
				strCate+='</categories>';
				strDataSet+="<dataset seriesName='增值收入合计' color='50BD4A'>"+sumSet+"</dataset>";
				strDataSet+="<dataset seriesName='增值收入占比' parentYAxis='S'>"+lineSet+"</dataset>";
				strXml+="<styles><definition><style type='font' color='666666' name='CaptionFont' size='15' /><style type='font' name='SubCaptionFont' bold='0' /></definition><application><apply toObject='caption' styles='CaptionFont' /><apply toObject='SubCaption' styles='SubCaptionFont' /></application></styles>";
				strXml="<chart caption='增值收入分析' sNumberSuffix='%' sYAxisName='增营比'  palette='1' animation='1' formatNumberScale='0' numberPrefix='￥' showValues='0' seriesNameInToolTip='0'>"+strCate+strDataSet+strXml+"</chart>";
				//alert(strXml);
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
						chartURL:'chars/fcf/MSColumn3DLineDY.swf?ChartNoDataText=对不起，图表中无数据显示。',
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
}
	
function menuStoreReload(){
	if(Ext.getCmp('countDateMon').isValid()&&Ext.getCmp('dateTypey').isValid()){
		var cmItems = []; 
		var afields=[];
		var rowNum = new Ext.grid.RowNumberer({
	        header:'序号', width:35, sortable:true
	    });
	    
	    afields.push({name:'feeName',mapping:'FEE_NAME'});
		cmItems.push(rowNum);
		cmItems.push({header:'费用类型',dataIndex:'feeName'});
		var dateType=Ext.getCmp('dateTypey').getValue();
		if(dateType=='月'){
			for(var i=1;i<=12;i++){
				afields.push({name:i+'月'});
				cmItems.push({header:i+'月',dataIndex :i+'月',xtype: 'numbercolumn',format:'0,000'});
			}
			Ext.apply(menuStore.baseParams,{
				msgDate:Ext.getCmp('countDateMon').getValue()
			});
		}else{
			for(var i=1;i<=31;i++){
				afields.push({name:i+'日'});
				cmItems.push({header:i+'日',dataIndex :i+'日',xtype: 'numbercolumn',format:'0,000'});
			}
			Ext.apply(menuStore.baseParams,{
				msgDate:Ext.getCmp('countDateDay').getValue()
			});
		}
		menuStore = new Ext.data.Store({
		        storeId:"menuStore1",
		        baseParams:{limit:pageSize},
		        proxy: new Ext.data.HttpProxy({url:sysPath+"/stock/oprValueAddFeeAction!findAddFeeMsg.action"}),
		        reader: new Ext.data.JsonReader({
		        }, afields)
		    });
		menuGrid.reconfigure(menuStore,new Ext.grid.ColumnModel(cmItems));
		if(dateType=='月'){
			Ext.apply(menuStore.baseParams,{
				msgDate:Ext.getCmp('countDateMon').getValue()
			});
		}else{
			Ext.apply(menuStore.baseParams,{
				msgDate:Ext.getCmp('countDateDay').getValue()
			});
		}
		menuStore.reload({
			params:{
				start : 0,
				limit : pageSize,
				departCode:Ext.getCmp('comboTypeDepart').getValue(),
				dateType:dateType
			}
		});
		menuGrid.doLayout();
		menuStore.on('load',function(){
			var div =document.getElementById('showView');
			while(div.hasChildNodes()) //当div下还存在子节点时 循环继续
			{	
				div.removeChild(div.firstChild);
			}
		});
	}
}
function searchthan(){
	var beforeDate=Ext.getCmp('beforeDate').getValue().format('Y-m-d');
	var afterDate=Ext.getCmp('afterDate').getValue().format('Y-m-d');
	var dombefore=Ext.get('beforeDate').dom.value;
	var domafter=Ext.get('afterDate').dom.value;
	var sm = new Ext.grid.CheckboxSelectionModel({});
    var rowNum = new Ext.grid.RowNumberer({
        header:'序号', width:35, sortable:true
    });
	var cmItems = []; 
	var afields=[];
	afields.push({name:'feeName',mapping:'FEE_NAME'});
	afields.push({name:'beforeDate',mapping:'BEFORE_DATE'});
	afields.push({name:'afterDate',mapping:'AFTER_DATE'});
	afields.push({name:'incomeAdd',mapping:'INCOME_ADD'});
	
	cmItems.push(rowNum);
	cmItems.push({header:'费用类型',dataIndex:'feeName'});
	cmItems.push({header:Ext.get('beforeDate').dom.value+'收入',dataIndex:'beforeDate'});
	cmItems.push({header:Ext.get('afterDate').dom.value+'收入',dataIndex:'afterDate'});
	cmItems.push({header:'收入增长',dataIndex:'incomeAdd'});
	
	loopthanStore=new Ext.data.Store({
        storeId:"loopthanStore",
        baseParams:{limit:pageSize},
        proxy: new Ext.data.HttpProxy({url:sysPath+"/stock/oprValueAddFeeAction!findFeeThen.action"}),
        reader: new Ext.data.JsonReader({
        }, afields)
    });
    loopthanPanel.reconfigure(loopthanStore ,new Ext.grid.ColumnModel(cmItems));
	loopthanStore.load({
		params:{
			beforeDate:beforeDate,
			afterDate:afterDate
		}
	});
	loopthanPanel.doLayout();
	
	loopthanStore.on('load',function(){
		var strXml='';
    	var strCate='<categories>';
    	var strDataSet='';
    	var sumSet='';
    	var lineSet='';
    	for(var i=0;i<loopthanStore.getCount();i++){
    		sumSet+="<set value='"+loopthanStore.getAt(i).get("afterDate")+"'/>";
    		lineSet+="<set value='"+loopthanStore.getAt(i).get("beforeDate")+"'/>";
    		strCate+="<category label='"+loopthanStore.getAt(i).get("feeName")+"'/>";
    	}
		strCate+='</categories>';
		strDataSet+="<dataset seriesName='"+dombefore+"' color='50BD4A'>"+lineSet+"</dataset>";
		strDataSet+="<dataset seriesName='"+domafter+"' color='A66EDD'>"+sumSet+"</dataset>";
		strXml+="<styles><definition><style type='font' color='666666' name='CaptionFont' size='15' /><style type='font' name='SubCaptionFont' bold='0' /></definition><application><apply toObject='caption' styles='CaptionFont' /><apply toObject='SubCaption' styles='SubCaptionFont' /></application></styles>";
		strXml="<chart caption='"+dombefore+","+domafter+"增值收入对比' palette='2' animation='1' formatNumberScale='0' numberPrefix='$' showValues='0' numDivLines='4' legendPosition='BOTTOM'>"+strCate+strDataSet+strXml+"</chart>";
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
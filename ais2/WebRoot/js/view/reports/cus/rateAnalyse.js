var rateGrid,rateStore,rateField,defaultWidth=60;
Ext.onReady(function (){
	var startone = new Ext.form.DateField ({
		id : 'startone',
		format : 'Y-m-d',
		allowBlank : false,
		emptyText : "开始时间",
		value:new Date().add(Date.DAY, -7),
		width : defaultWidth+40
	});
    var sm = new Ext.grid.CheckboxSelectionModel({});
    var rowNum = new Ext.grid.RowNumberer({
        header:'序号', width:35, sortable:true
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
	   		rateGrid.getTopToolbar().remove(startone);
			rateGrid.getTopToolbar().remove(endone);
	}
	function addOne(){
		rateGrid.getTopToolbar().insert(6,startone);
		rateGrid.getTopToolbar().insert(8,endone);
	}
	rateField = [
	];
	rateStore	= new Ext.data.Store({ 
			storeId:"rateStore",
			baseParams:{limit:pageSize},
			proxy:new Ext.data.HttpProxy({url:sysPath+"/cus/rateAnalyseAction!rateAnalyse.action"}),
			reader:new Ext.data.JsonReader({
	                    root:'result',
	                    totalProperty:'totalCount'
	        },rateField)
	});
     rateGrid = new Ext.grid.GridPanel({
       	renderTo:Ext.getBody(),
    	id : 'rateGrid',
    	region:'center',
    	height : Ext.lib.Dom.getViewHeight()-5,
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
        store:rateStore,
        tbar: [{text:'<b>导出</b>',iconCls:'sort_down',handler:function() {
                	parent.exportExl(rateGrid);
        	} },'-','统计维度',{
			xtype : "combo",
			forceSelection : true,
			triggerAction : 'all',
			store : countRangeStore,
			value:'日',
			mode : "remote",// 从本地载值
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
					rateGrid.getTopToolbar().doLayout(true);
				}
			}
			},
			'开始:',startone,' 至 ',endone,'-','统计类型',{
            	xtype:'combo',
            	name:'countType',
            	id:'countType',
            	width : 80,
            	value:'价格',
            	forceSelection : true,
				triggerAction : 'all',
				model : 'local',
				blankText:'统计类型不能为空',
				allowBlank:false,
            	store:[
            		['价格','价格'],
            		['折扣','折扣']
            	]
            }
            ,'-',
            {
            	text:'<B>统计</B>',
            	id:'btnSearch', 
            	iconCls: 'btnSearch',
            	hidden:false,
            	handler : countRate
            }
        ],
        bbar : new Ext.PagingToolbar({
				pageSize : pageSize, 
				store : rateStore,
				displayInfo : true,
				displayMsg : '显示第 {0} 条到 {1} 条记录，共 {2} 条',
				emptyMsg : "没有记录信息显示"
		}),
        listeners: {
            render: function(){
                searchTbar.render(this.tbar);
    			searchTbar2.render(this.tbar);
            }
        }
    });
});
function countRate(){
	if(Ext.getCmp('searchCountRange').isValid() && Ext.getCmp('countType').isValid()&&Ext.getCmp('startone').isValid()&&Ext.getCmp('endone').isValid()){
		var countType = Ext.getCmp('countType').getValue();
		
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
		
		var startCount = Ext.get('startone').dom.value;
		var endCount = Ext.get('endone').dom.value;
		var countRange = Ext.getCmp('searchCountRange').getValue();
		
		var date1 = Date.parseDate(startCount,'Y-m-d');
		var date2 = Date.parseDate(endCount,'Y-m-d');
		var d = (date2-date1)/86400000;//算出天数86400000=24*60*60*1000
		
		if(countRange=='日' && d>30){
			Ext.Msg.alert(alertTitle,'按日统计最大不能超过30天！');
			return false;
		}
		
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
		rateStore	= new Ext.data.Store({ 
			storeId:"rateStore",
			baseParams:{limit:pageSize},
			proxy:new Ext.data.HttpProxy({url:sysPath+"/cus/rateAnalyseAction!rateAnalyse.action"}),
			reader:new Ext.data.JsonReader({
	                    root:'resultMap',
	                    totalProperty:'totalCount'
	        },afields)
		});
		Ext.apply(rateStore.baseParams,{
			limit:pageSize,
			start:0,
			countType:countType,
			startCount:startCount,
			endCount:endCount,
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
		rateStore.load();
		
		rateStore.on('load',function(){
			reDoGrid(cmItems,afields,rateStore,rateGrid);
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
	    	var myarr = timeHead(rateStore,showafields);
	    	for(var j=0;j<myarr.length;j++){
	    		strCate+="<category label='"+myarr[j]+"'/>";
	    	}
			strCate+='</categories>';

	    	for(var i=0;i<rateStore.getCount();i++){
	    		strDataSet+="<dataset seriesName='"
	    		for(var k=0;k<showafields.length;k++){
	    			strDataSet+=rateStore.getAt(i).get(showafields[k]);
	    			if(k!=showafields.length-1){
	    				strDataSet+=",";
	    			}
	    		}
				strDataSet+="'>";
				for(var j=0;j<myarr.length;j++){
					var menuRecord = rateStore.getAt(i);
	    			var json = menuRecord.json;
	    			menuRecord.data = menuRecord.json;
					var value=menuRecord.get(myarr[j]);
	    			strDataSet+="<set value='"+value+"'/>";
	    		}
				strDataSet+="</dataset>";
			}
			var styles="<styles><definition><style type='font' name='CaptionFont' size='15' color='666666' /><style type='font' name='SubCaptionFont' bold='0' /></definition><application><apply toObject='caption' styles='CaptionFont' /><apply toObject='SubCaption' styles='SubCaptionFont' /></application></styles>";
			strXml="<chart caption='"+countType+"统计' numdivlines='9' divLineAlpha='100' lineThickness='3' anchorRadius='3' showValues='0' numVDivLines='22' formatNumberScale='0' slantLabels='1' anchorRadius='2' anchorBgAlpha='50' showAlternateVGridColor='1' anchorAlpha='100' animation='1' limitsDecimalPrecision='0' divLineDecimalPrecision='1'>"+strCate+strDataSet+styles+"</chart>";
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
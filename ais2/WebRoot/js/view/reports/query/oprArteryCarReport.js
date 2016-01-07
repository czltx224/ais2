//干线车时效报表查询显示js
var searchReportUrl='reports/oprArteryCarReportAction!findList.action';//干线车时效报表查询地址
var dictionaryUrl='sys/dictionaryAction!ralaList.action';//数据字典地址
var carUrl = "bascar/basCarAction!list.action";//车辆查询地址
var privilege=197;
var defaultWidth=60;
var colWidth=80;

var fields=['ID','STARTDEPARTID','STARTDEPARTNAME','ENDDEPARTID','ENDDEPARTNAME','CARNUMBER','CARCODE','COUNTDATE','STANDARDREACHCARTIME',
			'TRUEREACHCARTIME','STANDARDOUTTIME','TRUEOUTTIME','STANDARDUSEHOUR','TRUEUSEHOUR','STANDARDREACHSTOCKTIME',
			'TRUEREACHSTOCKTIME','REACHCARISSTANDARD','OUTCARISSTANDARD','RUNISSTANDARD','REACHSTOCKISSTANDARD'
			,'DRIVERNAME','CARGONAME','REACHNAME','LOADINGUNLOADINGGROUP','DUTYUNIT'];
			
		//是否达标
		isStandard=new Ext.data.SimpleStore({
			 autoLoad:true, //此处设置为自动加载
  			  data:[['1','是'],['0','否']],
   			 fields:["standardId","standardName"]
		});
		
 		//自动计算频率201
		countRangeStore	= new Ext.data.Store({ 
			storeId:"countRangeStore",
			baseParams:{filter_EQL_basDictionaryId:201,privilege:16},
			proxy:new Ext.data.HttpProxy({url:sysPath+"/"+dictionaryUrl}),
			reader:new Ext.data.JsonReader({
	                    root:'result',
	                    totalProperty:'totalCount'
	        },[
        	{name:'countRangeId',mapping:'typeCode'},
        	{name:'countRangeName',mapping:'typeName'}
        	])
		});
		//车牌号
		carStore = new Ext.data.Store({
			storeId : "carStore",
			proxy : new Ext.data.HttpProxy({
						url : sysPath + "/" + carUrl
					}),
			reader : new Ext.data.JsonReader({
						root : 'result',
						totalProperty : 'totalCount'
					}, [{
								name : 'carId',
								mapping : 'id'
							}, {
								name : 'carCode',
								mapping : 'carCode'
							}])

		});
	var startone = new Ext.form.DateField ({
    		id : 'startone',
    		format : 'Y-m-d',
    		emptyText : "开始时间",
    		value:new Date().add(Date.DAY, -7),
    		width : defaultWidth+40
    	});
    	
    var endone = new Ext.form.DateField ({
    		id : 'endone',
    		format : 'Y-m-d',
    		emptyText : "结束时间",
    		value:new Date(),
    		width : defaultWidth+40
    	});
   	function removeOne(){
   		reportGrid.getTopToolbar().remove(startone);
		reportGrid.getTopToolbar().remove(endone);
   	}
   	function addOne(){
   		reportGrid.getTopToolbar().insert(6,startone);
		reportGrid.getTopToolbar().insert(8,endone);
   	}
	var tbar=[
			'时间选择:',{
			xtype : "combo",
			width : defaultWidth+20,
			triggerAction : 'all',
			forceSelection : true,
			model : 'local',
			hiddenId : 'checkItems',
			hiddenName : 'checkItems',
			store : [
					['countDate', '发车日期']],
			listeners: {   
				afterRender: function(combo) {   
		       　　combo.setValue('countDate');
				}
			},
			emptyText : '选择类型'
								
			},'-',
			'统计维度',{
			xtype : "combo",
			forceSelection : true,
			triggerAction : 'all',
			fieldLabel:'计算频率<font style="color:red;">*</font>',
			store : countRangeStore,
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
				    		emptyText : "开始时间",
				    		value:new Date().add(Date.DAY, -7),
				    		width : defaultWidth+40
				    	});
				    	endone = new Ext.form.DateField ({
				    		id : 'endone',
				    		format : 'Y-m-d',
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
				    		value:new Date().getWeekOfYear()-1,
				    		minValue:1,
				    		maxValue:52,
				    		maxLength:2,
				    		width : defaultWidth
				    	});
				    	endone = new Ext.form.NumberField  ({
				    		id : 'endone',
				    		allowNegative :false,
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
				    		format : 'Y-m',
				    		emptyText : "开始时间",
				    		value:new Date().add(Date.MONTH, -1),
				    		width : defaultWidth+40
				    	});
				    	endone = new Ext.form.DateField ({
				    		id : 'endone',
				    		format : 'Y-m',
				    		emptyText : "结束时间",
				    		value:new Date(),
				    		width : defaultWidth+40
				    	});
				    	addOne();
					}else if(v=='年'){
						removeOne();
						startone = new Ext.form.NumberField ({
				    		id : 'startone',
				    		allowNegative :false,
				    		value:new Date().getFullYear()-1,
				    		minValue:1,
				    		width : defaultWidth
				    	});
				    	endone = new Ext.form.NumberField  ({
				    		id : 'endone',
				    		allowNegative :false,
				    		value:new Date().getFullYear(),
				    		minValue:1,
				    		width : defaultWidth
				    	});
				    	addOne();
					}
					reportGrid.getTopToolbar().doLayout(true);
				}
			}
		},
		'开始:',startone,' 至 ',endone,'-',{text : '<b>统计</b>',id : 'btn',iconCls : 'btnSearch',
    					handler : searchSreport
    	},'-',
	 	{text:'<b>导出</b>',iconCls:'sort_down',tooltip : '导出数据',
	 		handler:function(){
	 		   parent.exportExl(reportGrid);
	 		}
	 	}
	 			
	 	];	
	 	var twoBar = new Ext.Toolbar([ '车牌号:', {
			xtype : 'combo',
			// typeAhead : true,
			id : 'carcombo',
			queryParam : 'filter_LIKES_carCode',
			triggerAction : 'all',
			store : carStore,
			pageSize : comboSize,
			selectOnFocus : true,
			resizable : true,
			minChars : 0,
			mode : "remote",// 从服务器端加载值
			valueField : 'carId',// value值，与fields对应
			displayField : 'carCode',// 显示值，与fields对应
			hiddenName : 'carId',
			width:defaultWidth,
			enableKeyEvents : true,
			listeners : {
				keyup : function(textField, e) {

					if (e.getKey() == 13) {
						searchSreport();
					}
				}
			}
		},'-','发车是否达标:',{
	 			xtype : "combo",
				width : defaultWidth,
				editable:true,
				triggerAction : 'all',
				forceSelection : true,
				store : isStandard,
				mode : "local",// 从本地载值
				valueField : 'standardId',// value值，与fields对应
				displayField : 'standardName',// 显示值，与fields对应
			    id:'searchOutStandard'
	 		},'-','运行是否达标:',{
	 			xtype : "combo",
				width : defaultWidth,
				editable:true,
				triggerAction : 'all',
				forceSelection : true,
				store : isStandard,
				mode : "local",// 从本地载值
				valueField : 'standardId',// value值，与fields对应
				displayField : 'standardName',// 显示值，与fields对应
			    id:'searchCarRunStandard'
	 		},'-','到车是否达标:',{
	 			xtype : "combo",
				width : defaultWidth,
				editable:true,
				triggerAction : 'all',
				forceSelection : true,
				store : isStandard,
				mode : "local",// 从本地载值
				valueField : 'standardId',// value值，与fields对应
				displayField : 'standardName',// 显示值，与fields对应
			    id:'searchEnterStandard'
	 		}
		]);
		
	var threeBar = new Ext.Toolbar([ 
			'航班落地时间:',{
			xtype : 'timefield',
			labelAlign : 'left',
			fieldLabel : '起飞时间',
			format:'H:i',
			id:'searchFlightTime',
			width : defaultWidth
		},'-','时间:',{
			xtype : "combo",
			width : defaultWidth+20,
			triggerAction : 'all',
			forceSelection : true,
			model : 'local',
			hiddenId : 'checkHour',
			hiddenName : 'checkHour',
			store : [
					['standardUseHour', '车辆标准用时'],
					['trueUseHour', '车辆实际用时']],
			listeners: {   
				afterRender: function(combo) {   
		       　　combo.setValue('standardUseHour');
				}
			},
			emptyText : '选择类型'
								
			},'-',{
				xtype:'numberfield',
				id:'startHour',
				forceSelection : true,
				width:defaultWidth-20
			},'至',{
				xtype:'numberfield',
				id:'endHour',
				forceSelection : true,
				width:defaultWidth-20
			},'自选条件',{
			xtype : "combo",
			width : defaultWidth+20,
			triggerAction : 'all',
			forceSelection : true,
			model : 'local',
			hiddenId : 'myCheckItems',
			hiddenName : 'myCheckItems',
			store : [
					['carNumber', '车次号'],
					['dutyUnit', '责任单位'],
					['driverName', '司机'],
					['cargoName', '配载员'],
					['reachName', '点到人'],
					['loadingUnloadingGroup', '装卸组']],
			listeners: {   
				
			},
			emptyText : '选择类型'
			},'-',{
				xtype:'textfield',
				id:'myCheckItemsValue',
				forceSelection : true,
				width:defaultWidth
			}
	]);
Ext.onReady(function() {
   	dateStore = new Ext.data.Store({
        storeId:"dateStore",
        proxy: new Ext.data.HttpProxy({url : sysPath+ "/"+searchReportUrl}),
        baseParams:{privilege:privilege,limit : pageSize},
        reader: new Ext.data.JsonReader({
            root: 'resultMap', totalProperty: 'totalCount'
        }, fields)
    });
   
   var sm = new Ext.grid.CheckboxSelectionModel({});
	var rowNum = new Ext.grid.RowNumberer({
        header:'序号', width:30
    });
    reportGrid = new Ext.grid.GridPanel({
        renderTo: Ext.getBody(),
        id:'reportCenter',
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
             {header:'始发部门',dataIndex:"STARTDEPARTNAME",width:colWidth,sortable:true},
            {header:'达到部门',dataIndex:"ENDDEPARTNAME",width:colWidth,sortable:true},
            {header:'车次号',dataIndex:"CARNUMBER",width:colWidth,sortable:true},
            {header:'车牌号',dataIndex:'CARCODE',width:colWidth,sortable:true},
			{header:'发车日期',dataIndex:'COUNTDATE',width:colWidth+20,sortable:true},
			{header:'到车是否达标',dataIndex:'REACHCARISSTANDARD',width:colWidth,sortable:true,hidden:true},
			{header:'发车是否达标',dataIndex:'OUTCARISSTANDARD',width:colWidth,sortable:true
				,renderer:function renderDescn(v, cellmeta, record, rowIndex, columnIndex, store){
					if(v=='是'){
						cellmeta.css = 'x-grid-back-green';
					}else{
						cellmeta.css = 'x-grid-back-red';
					}
					return v;
				}
			},
			{header:'运行是否达标',dataIndex:'RUNISSTANDARD',width:colWidth,sortable:true
				,renderer:function renderDescn(v, cellmeta, record, rowIndex, columnIndex, store){
					if(v=='是'){
						cellmeta.css = 'x-grid-back-green';
					}else{
						cellmeta.css = 'x-grid-back-red';
					}
					return v;
				}
			},
			{header:'到车是否达标',dataIndex:'REACHSTOCKISSTANDARD',width:colWidth,sortable:true
				,renderer:function renderDescn(v, cellmeta, record, rowIndex, columnIndex, store){
					if(v=='是'){
						cellmeta.css = 'x-grid-back-green';
					}else{
						cellmeta.css = 'x-grid-back-red';
					}
					return v;
				}
			},
			{header:'标准发车时间',dataIndex:'STANDARDOUTTIME',width:colWidth+20,sortable:true},
			{header:'实际发车时间',dataIndex:'TRUEOUTTIME',width:colWidth+20,sortable:true},
			{header:'标准到车时间',dataIndex:'STANDARDREACHSTOCKTIME',width:colWidth+20,sortable:true},
			{header:'实际到车时间',dataIndex:'TRUEREACHSTOCKTIME',width:colWidth+20,sortable:true},
			{header:'标准用时',dataIndex:'STANDARDUSEHOUR',width:colWidth,sortable:true},
			{header:'实际用时',dataIndex:'TRUEUSEHOUR',width:colWidth,sortable:true},
			{header:'司机',dataIndex:'DRIVERNAME',width:colWidth,sortable:true},
			{header:'配载员',dataIndex:'CARGONAME',width:colWidth,sortable:true},
			{header:'点到人',dataIndex:'REACHNAME',width:colWidth,sortable:true},
			{header:'装卸组',dataIndex:'LOADINGUNLOADINGGROUP',width:colWidth,sortable:true},
			{header:'责任单位',dataIndex:'DUTYUNIT',width:colWidth,sortable:true}
        ]),
        store:dateStore,
        tbar: tbar,
        listeners : {
			render : function() {
				twoBar.render(this.tbar);
				threeBar.render(this.tbar);
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
    
	
 function searchSreport() {
 	dateStore.proxy = new Ext.data.HttpProxy({
				method : 'POST',
				url : sysPath + "/" + searchReportUrl
				
			});
	if(!Ext.getCmp('startone').isValid()){
		Ext.Msg.alert(alertTitle,'开始时间格式不正确!');
		return;
	}
	if(!Ext.getCmp('endone').isValid()){
		Ext.Msg.alert(alertTitle,'结束时间格式不正确!');
		return;
	}
	if(!Ext.getCmp('startHour').isValid()){
		Ext.Msg.alert(alertTitle,'开始用时格式不正确!');
		return;
	}
	if(!Ext.getCmp('endHour').isValid()){
		Ext.Msg.alert(alertTitle,'结束用时格式不正确!');
		return;
	}
	var searchCountRange = Ext.getCmp('searchCountRange').getRawValue();
	if(null==searchCountRange || searchCountRange.length<1){
		searchCountRange='日';
	}
	var startCount = Ext.get('startone').dom.value;
	var endCount = Ext.get('endone').dom.value;
	var carCode = Ext.get('carcombo').dom.value;
	var searchOutStandard = Ext.getCmp('searchOutStandard').getRawValue();
	var searchEnterStandard = Ext.getCmp('searchEnterStandard').getRawValue();
	var searchCarRunStandard = Ext.getCmp('searchCarRunStandard').getRawValue();
//	var searchCarNumber = Ext.getCmp('searchCarNumber').getValue();
	var searchFlightTime = Ext.getCmp('searchFlightTime').getValue();
	//alert(startCount+'-'+endCount);searchFlightTime   searchDuty
	var countCheckItems = Ext.get('checkItems').dom.value;
//	var searchDuty = Ext.getCmp('searchDuty').getValue();
	
	var checkHour = Ext.get('checkHour').dom.value;
	var startHour = Ext.getCmp('startHour').getValue();
	var endHour = Ext.getCmp('endHour').getValue();
	
	var myCheckItems = Ext.get('myCheckItems').dom.value;
	var myCheckItemsValue = Ext.getCmp('myCheckItemsValue').getValue();
	if(startHour>endHour){
		Ext.Msg.alert(alertTitle,'开始用时不能大于结束用时!');
		return;
	}
			
	if(startCount=='开始时间'){
		startCount='';
	}
	if(endCount=='结束时间'){
		endCount='';
	}
	
 	 Ext.apply(dateStore.baseParams, {
 	 	filter_countRange:searchCountRange,
 	 	filter_startCount:startCount,
 	 	filter_carCode:carCode,
 	 	filter_endCount:endCount,
 	 	filter_outcarIsStandard:searchOutStandard,
 	 	filter_reachIsStandard:searchEnterStandard,
 	 	filter_runIsStandard:searchCarRunStandard,
// 	 	filter_carNumber:searchCarNumber,
 	 	filter_flightLandingTime:searchFlightTime,
 	 	filter_countCheckItems:countCheckItems,
// 	 	filter_dutyUnit:searchDuty,
 	 	filter_checkHour:checkHour,
 	 	filter_startHour:startHour,
 	 	filter_endHour:endHour,
 	 	filter_myCheckItems:myCheckItems,
 	 	filter_myCheckItemsValue:myCheckItemsValue,
 		limit:pageSize
 	});
	dataReload();
}
 function dataReload(){
	dateStore.reload({
	params : {
		start : 0,
		privilege:privilege,
		limit:pageSize
		}
	})
}

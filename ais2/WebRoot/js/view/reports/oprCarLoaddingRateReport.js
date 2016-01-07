//车辆装载率报表查询显示js
var searchReportUrl='reports/carLoadingRateReportAction!findList.action';//时效报表查询地址
var dictionaryUrl='sys/dictionaryAction!ralaList.action';//数据字典地址
var carUrl = "bascar/basCarAction!list.action";//车辆查询地址
var authorityDepartUrl='sys/basRightDepartAction!findDepartName.action';//权限部门查询地址
var privilege=197;
var defaultWidth=60;
var colWidth=110;
var pubauthorityDepart=bussDepart;
var fields=['ROUTENUMBER','CARCODE','TYPECODE','MAXLOADWEIGHT','WEIGHT','LOADINGRATE','STARTTIME','DEPTID','DEPTNAME','USECARTYPE','RENTCARRESULT','LOADINGNAME'];
			
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
		//车型21
		carTypeStore	= new Ext.data.Store({ 
			storeId:"carTypeStore",
			baseParams:{filter_EQL_basDictionaryId:21,privilege:16},
			proxy:new Ext.data.HttpProxy({url:sysPath+"/"+dictionaryUrl}),
			reader:new Ext.data.JsonReader({
	                    root:'result',
	                    totalProperty:'totalCount'
	        },[
        	{name:'typeCode',mapping:'typeCode'},
        	{name:'typeName',mapping:'typeName'}
        	])
		});
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
   		enterPortReportGrid.getTopToolbar().remove(startone);
		enterPortReportGrid.getTopToolbar().remove(endone);
   	}
   	function addOne(){
   		enterPortReportGrid.getTopToolbar().insert(6,startone);
		enterPortReportGrid.getTopToolbar().insert(8,endone);
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
					['startTime', '发车日期']],
			listeners: {   
				afterRender: function(combo) {   
		       　　combo.setValue('startTime');
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
					enterPortReportGrid.getTopToolbar().doLayout(true);
				}
			}
		},
		'开始:',startone,' 至 ',endone,'-',{text : '<b>统计</b>',id : 'btn',iconCls : 'btnSearch',
    					handler : searchSenterPortReport
    	},'-',
	 	{text:'<b>导出</b>',iconCls:'sort_down',tooltip : '导出数据',
	 		handler:function(){
	 		   parent.exportExl(enterPortReportGrid);
	 		}
	 	}
	 			
	 	];	
	 	var twoBar = new Ext.Toolbar([ '车牌号:', {
			xtype : 'combo',
			typeAhead : true,
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
						searchSenterPortReport();
					}
				}
			}
		},'字段:',{
			xtype : "combo",
			width : defaultWidth+30,
			triggerAction : 'all',
			forceSelection : true,
			model : 'local',
			hiddenId : 'checkHour',
			hiddenName : 'checkHour',
			store : [
					['maxloadWeight', '最大载重(T)'],
					['weight', '实际载重(KG)'],
					["loadingrate", '装载率(%)']],
			listeners: {   
				afterRender: function(combo) {   
		       　　combo.setValue('maxloadWeight');
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
					['LIKES_useCarType', '用车类型'],
					['LIKES_rentCarResult', '车辆用途'],
					['LIKES_loadingName', '装卸组']],
			listeners: {   
				
			},
			emptyText : '选择类型'
			},'-',{
				xtype:'textfield',
				id:'myCheckItemsValue',
				forceSelection : true,
				width:defaultWidth,
				enableKeyEvents : true,
				listeners : {
					keyup : function(textField, e) {
						if (e.getKey() == 13) {
							searchSenterPortReport();
						}
					}
				}
			}
		]);
		
	var threeBar = new Ext.Toolbar([ 
		'车次号', {
			xtype:'numberfield',
			id:'searchDno',
			width:defaultWidth,
			enableKeyEvents:true,
			listeners:{
				keyup:function(f,e){
					if(e.getKey()==13){
						searchSenterPortReport();
					}
				}
			}
		},'-','车辆类型:',{
			xtype : 'combo',
			typeAhead : true,
			id : 'searchCarType',
			queryParam : 'filter_LIKES_typeName',
			triggerAction : 'all',
			store : carTypeStore,
			pageSize : comboSize,
			selectOnFocus : true,
			resizable : true,
			minChars : 0,
			mode : "remote",// 从服务器端加载值
			displayField : 'typeName',// 显示值，与fields对应
			width:defaultWidth,
			enableKeyEvents : true,
			listeners : {
				keyup : function(textField, e) {
					if (e.getKey() == 13) {
						searchSenterPortReport();
					}
				}
			}
			
		},'部门名称:',{xtype : "combo",
				width : defaultWidth+20,
				editable:true,
				triggerAction : 'all',
				typeAhead : true,
				queryParam : 'filter_LIKES_departName',
				pageSize : comboSize,
				forceSelection : true,
				resizable:true,
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
                     	searchSenterPortReport();
                     }
	 		}
	 	
	 	}}
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
    enterPortReportGrid = new Ext.grid.GridPanel({
        renderTo: Ext.getBody(),
        id:'enterPortReportCenter',
        height:Ext.lib.Dom.getViewHeight()-1,		
        width:Ext.lib.Dom.getViewWidth()-1,
        viewConfig : {
			columnsText : "显示的列",
			sortAscText : "升序",
			sortDescText : "降序",
			forceFit : true
		},
		autoScroll : true,
		autoSizeColumns: true,
		frame : false,
		loadMask : true,
		stripeRows : true,
        sm:sm,
       cm:new Ext.grid.ColumnModel([rowNum,sm,
            {header:'车次号',dataIndex:"ROUTENUMBER",width:colWidth,sortable:true},
			{header:'车牌号',dataIndex:'CARCODE',width:colWidth,sortable:true},
			{header:'部门名称',dataIndex:'DEPTNAME',width:colWidth,sortable:true},
			{header:'用车类型',dataIndex:'USECARTYPE',width:colWidth,sortable:true},
			{header:'车辆用途',dataIndex:'RENTCARRESULT',width:colWidth,sortable:true},
			{header:'装卸组',dataIndex:'LOADINGNAME',width:colWidth,sortable:true},
			{header:'发车时间',dataIndex:'STARTTIME',width:colWidth+40,sortable:true},
			{header:'车辆类型',dataIndex:'TYPECODE',width:colWidth,sortable:true},
			{header:'最大载重',dataIndex:'MAXLOADWEIGHT',width:colWidth,sortable:true},
			{header:'实际载重',dataIndex:'WEIGHT',width:colWidth,sortable:true},
			{header:'装载率',dataIndex:'LOADINGRATE',width:colWidth,sortable:true
				,renderer:function(v){
					return v+'%';
				}
			}
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
    
	
 function searchSenterPortReport() {
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
	var carcombo = Ext.getCmp('carcombo').getRawValue();
	var searchDno = Ext.getCmp('searchDno').getValue();
	
	//alert(startCount+'-'+endCount);searchFlightTime   searchDuty
	var countCheckItems = Ext.get('checkItems').dom.value;
	var searchCarType = Ext.getCmp('searchCarType').getRawValue();
	var checkHour = Ext.get('checkHour').dom.value;
	var startHour = Ext.getCmp('startHour').getValue();
	var endHour = Ext.getCmp('endHour').getValue();
	
	var authorityDepartId=Ext.getCmp('authorityDepartId').getValue();
	if(null!=authorityDepartId && authorityDepartId>0){
		pubauthorityDepart=authorityDepartId;
	}else{
		pubauthorityDepart=bussDepart;
	}
	if(startCount=='开始时间'){
		startCount='';
	}
	if(endCount=='结束时间'){
		endCount='';
	}
	var myCheckItems = Ext.get('myCheckItems').dom.value;
	var myCheckItemsValue = Ext.getCmp('myCheckItemsValue').getValue();
	
 	 Ext.apply(dateStore.baseParams, {
 	 	filter_countRange:searchCountRange,
 	 	filter_startCount:startCount,
 	 	filter_endCount:endCount,
 	 	filter_carCode:carcombo,
 	 	filter_typeCode:searchCarType,
 	 	filter_LIKES_routeNumber:searchDno,
 	 	filter_deptid:pubauthorityDepart,
 	 	filter_countCheckItems:countCheckItems,
 	 	filter_checkHour:checkHour,
 	 	filter_startHour:startHour,
 	 	filter_endHour:endHour,
 	 	filter_myCheckItems:myCheckItems,
 	 	filter_myCheckItemsValue:myCheckItemsValue,
 		limit:pageSize
 	});
	dataReload();
}
/*
dateStore.on('load',function(){
    	var strXml='';
    	var strCate='<categories>';
    	var strDataSet='';
    	for(var j=1;j<=dateStore.length;j++){
    		var strMon=j+'月';
			strCate+="<category label='"+strMon+"'/>";
		}
		strCate+='</categories>';
    	for(var i=0;i<dateStore.getCount();i++){
    				if(dateStore.getAt(i).get('COUNTTYPE').trim()=='收入'){
    					strDataSet+="<dataset seriesName='"+dateStore.getAt(i).get('COUNTTYPE').trim()+"' color='1A2CA6'>";
						for(var j=1;j<=12;j++){
							var mon=j+'月';
							var value=dateStore.getAt(i).get(mon).trim();
							strDataSet+="<set value='"+value+"'/>";
						}
						strDataSet+="</dataset>";
    				}
    				if(dateStore.getAt(i).get('COUNTTYPE').trim()=='毛利金额'){
    					strDataSet+="<dataset seriesName='"+dateStore.getAt(i).get('COUNTTYPE').trim()+"' color='50BD4A'>";
						for(var j=1;j<=12;j++){
							var mon=j+'月';
							var value=dateStore.getAt(i).get(mon).trim();
							strDataSet+="<set value='"+value+"'/>";
						}
						strDataSet+="</dataset>";
    				}
					if(dateStore.getAt(i).get('COUNTTYPE').trim()=='毛利率'){
						strDataSet+="<dataset seriesName='"+dateStore.getAt(i).get('COUNTTYPE').trim()+"' parentYAxis='S'>";
						for(var j=1;j<=12;j++){
							var mon=j+'月';
							var value=dateStore.getAt(i).get(mon).trim();
							var numVal=value.substring(0,(value).length-1);
							strDataSet+="<set value='"+numVal+"'/>";
						}
						strDataSet+="</dataset>";
					}
				}
				strXml+="<styles><definition><style type='font' color='666666' name='CaptionFont' size='15' /><style type='font' name='SubCaptionFont' bold='0' /></definition><application><apply toObject='caption' styles='CaptionFont' /><apply toObject='SubCaption' styles='SubCaptionFont' /></application></styles>";
				strXml="<chart caption='盈利分析' XAxisName='月份' sNumberSuffix='%' sYAxisName='盈利率'  palette='1' animation='1' formatNumberScale='0' numberPrefix='￥' showValues='0' seriesNameInToolTip='0'>"+strCate+strDataSet+strXml+"</chart>";
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
						chartURL:'chars/fcf/MSColumn3DLineDY.swf',
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
*/
 function dataReload(){
	dateStore.reload({
	params : {
		start : 0,
		privilege:privilege,
		limit:pageSize
		}
	})
}

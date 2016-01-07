//货量分析
var fields=[
			{name:'COUNTTYPE'},
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
var defaultWidth=60,menuStore,cusStore,menuGrid,countRangeStore;
Ext.onReady(function() {
    menuStore = new Ext.data.Store({
        storeId:"menuStore",
        baseParams:{limit:pageSize,countDate:new Date(),cusId:parent.cusId},
        proxy: new Ext.data.HttpProxy({url:sysPath+"/cus/cusGoodsAnalyAction!findGoodsMsg.action"}),
        reader: new Ext.data.JsonReader({
            root: 'resultMap', totalProperty: 'totalCount'
        }, fields)
    });
    cusStore=new Ext.data.Store({
		storeId:"cusStore",
		baseParams:{privilege:61,filter_EQS_custprop:'发货代理'},
		proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/customerAction!ralaList.action"}),
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
        	{name:'id'},
        	{name:'cusName'}
        	])
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
    var sm = new Ext.grid.CheckboxSelectionModel({});
    var rowNum = new Ext.grid.RowNumberer({
        header:'序号', width:35, sortable:true
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
    menuGrid = new Ext.grid.GridPanel({
       	renderTo:Ext.getBody(),
    	id : 'myrecordGrid',
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
            {header:'统计类型',dataIndex:"COUNTTYPE"},
            {header:'一月',dataIndex:'1月'},
            {header:'二月',dataIndex:'2月'},
            {header:'三月',dataIndex:"3月"},
            {header:'四月',dataIndex:"4月"},
            {header:'五月',dataIndex:"5月"},
            {header:'六月',dataIndex:"6月"},
            {header:'七月',dataIndex:"7月"},
            {header:'八月',dataIndex:"8月"},
            {header:'九月',dataIndex:"9月"},
            {header:'十月',dataIndex:"10月"},
            {header:'十一月',dataIndex:"11月"},
            {header:'十二月',dataIndex:"12月"}
        ]),
        store:menuStore,
        tbar: [
            {text:'<b>导出</b>',iconCls:'sort_down',handler:function() {
                	parent.exportExl(menuGrid);
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
					menuGrid.getTopToolbar().doLayout(true);
				}
			}
			},
			'开始:',startone,' 至 ',endone,'-','代理公司：',{
            	xtype:'combo',
				anchor:'100%',
				triggerAction :'all',
				model : 'local',
				id:'cuscombo',
				minChars : 0,
				width:120,
				store:cusStore,
				queryParam :'filter_LIKES_cusName',
				pageSize:pageSize,
				valueField :'id',
			    displayField :'cusName',
			    hiddenName : 'cusId',
				emptyText : '选择类型'
            }
            ,'-',
            {
            	text:'<B>统计</B>',
            	id:'btnSearch', 
            	iconCls: 'btnSearch',
            	hidden:false,
            	handler : searchCusRequest
            }
        ],
        listeners:{
        	'render':function(){
        		if(parent.viewtype=='mainview'){
		    		Ext.getCmp('cuscombo').setDisabled(true);
		    	}
        	}
        }
    });
    function searchCusRequest() {
    	var cusId=Ext.getCmp('cuscombo').getValue();
    	var countRange = Ext.getCmp('searchCountRange').getValue();
    	var startCount = Ext.get('startone').dom.value;
    	var endCount = Ext.get('endone').dom.value;
    	
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
		afields.push("COUNTTYPE");
		cmItems.push({header:'统计类型',dataIndex:'COUNTTYPE',width:80});
		
	     if(parent.cusId != null){
	     	cusId = parent.cusId;
	     }
		Ext.apply(menuStore.baseParams,{
			startCount:startCount,
			endCount:endCount,
			cusId:cusId,
			countRange:countRange
		});
    	menuStore.load();
    	menuStore.on('load',function(){
    		reDoGrid(cmItems,afields,menuStore,menuGrid);
    		
    		var showafields=[];
    		showafields.push("COUNTTYPE");
    		var myarr = timeHead(menuStore,showafields);
	    	var strXml='';
	    	for(var i=0;i<menuStore.getCount();i++){
				if(menuStore.getAt(i).get('COUNTTYPE').trim()=='票'){
					continue;
				}
				for(var j=0;j<myarr.length;j++){
					var menuRecord = menuStore.getAt(i);
	    			var json = menuRecord.json;
	    			menuRecord.data = menuRecord.json;
					var value=menuRecord.get(myarr[j]);
	    			strXml+="<set name='"+myarr[j]+"' value='"+value+"'/>'";
	    		}
			}
			strXml="<chart animation='1' caption='货量统计' xAxisName='月份' yAxisName='货量'  rotateYAxisName='1' showValues='1'  decimalPrecision='0' showNames='1'  baseFontSize='12' outCnvBaseFontSiz='20' numberSuffix=' KG'  pieSliceDepth='30' formatNumberScale='0'>"+strXml+"</chart>";
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
				chartURL:'chars/fcf/Column3D.swf',//定义图表显示类型，例如：直方，饼图等
				dataXML:strXml,
				width:Ext.lib.Dom.getViewWidth()-20,
				height:250
			});
			var div =document.getElementById('showView');
			while(div.hasChildNodes()) //当div下还存在子节点时 循环继续
			{	
				div.removeChild(div.firstChild);
			}
			fusionPanel.render('showView');
	    });
    	
	}
   menuGrid.render();
    menuGrid.on('click', function() {
        var _record = menuGrid.getSelectionModel().getSelections();
        var updatebtn = Ext.getCmp('addbtn');
        if (_record.length==1) {       	
            if(updatebtn&&type=='发货代理'){
            	updatebtn.setDisabled(false);
            }
        }else if(_record.length>1){
        	 if(updatebtn){
            	updatebtn.setDisabled(true);
            }
        } else {
            if(updatebtn){
            	updatebtn.setDisabled(true);
            }
        }
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
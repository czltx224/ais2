//送货货量报表js
var searchMapUrl='reports/sendGoodsReport!findSendGoods.action';//送货货量报表查询地址
var searchDetailMapUrl='reports/sendGoodsReport!findSendGoodsDetail.action';//送货货量明细查询地址
var searchDetailCountUrl='reports/sendGoodsReport!findSendGoodsDetailCount.action';//送货货量明细统计汇总地址
var authorityDepartUrl='sys/basRightDepartAction!findDepartName.action';//权限部门查询地址
var dictionaryUrl='sys/dictionaryAction!ralaList.action';//数据字典地址
var gridCustomerUrl="sys/customerAction!list.action";//客商查询地址
var cusServiceUrl='user/userAction!list.action';//送货员查询地址
var privilege=146;
var defaultWidth=80;
var colWidth=80;
var searchDepartId=bussDepart;
var fields=['DEPART_ID','DEPART_NAME','FNAME','SUMCOL'];
var detailFields=[
{name:'riqi',mapping:'RIQI'},
{name:'endDepartId',mapping:'END_DEPART_ID'},
{name:'endDepartName',mapping:'END_DEPART_NAME'},
{name:'qianshou',mapping:'QIANSHOU'},
{name:'amqiandan',mapping:'AMQIANDAN'},
{name:'pmqiandan',mapping:'PMQIANDAN'},
{name:'ampiece',mapping:'AMPIECE'},
 {name:'pmpiece',mapping:'PMPIECE'},
 {name:'amcountpiece',mapping:'AMCOUNTPIECE'},
 {name:'pmcountpiece',mapping:'PMCOUNTPIECE'},
 {name:'amweight',mapping:'AMWEIGHT'},
  {name:'pmweight',mapping:'PMWEIGHT'},
  {name:'unloadStartTime',mapping:'UNLOAD_START_TIME'},
  {name:'unloadEndTime',mapping:'UNLOAD_END_TIME'},
  {name:'amshouru',mapping:'AMSHOURU'},
  {name:'pmshouru',mapping:'PMSHOURU'},
  {name:'returnNum',mapping:'RETURN_NUM'},
  {name:'stopFee',mapping:'STOP_FEE'},
  {name:'useCarType',mapping:'USE_CAR_TYPE'}
];

		//送货员 cusServiceUrl
		cusServiceStore=new Ext.data.Store({
	        autoLoad:true,
	        storeId:"cusServiceStore",
			proxy:new Ext.data.HttpProxy({url:sysPath+"/"+cusServiceUrl}),
			reader:new Ext.data.JsonReader({
	                    root:'result',
	                    totalProperty:'totalCount'
	        },[
        	{name:'cusServiceId',mapping:'id'},
        	{name:'cusServiceName',mapping:'userName'}
        	])
        	
		});
		//代理公司
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
    //统计纬度201
		countRangeStore	= new Ext.data.Store({ 
			autoLoad:true, 
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
   		sendGoodsGrid.getTopToolbar().remove(startone);
		sendGoodsGrid.getTopToolbar().remove(endone);
   	}
   	function addOne(){
   		sendGoodsGrid.getTopToolbar().insert(6,startone);
		sendGoodsGrid.getTopToolbar().insert(8,endone);
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
					['t.create_time', '统计日期']],
			listeners: {   
				afterRender: function(combo) {   
		       　　combo.setValue('t.create_time');
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
					sendGoodsGrid.getTopToolbar().doLayout(true);
				}
			}
		},
		'开始:',startone,' 至 ',endone,'-',{text : '<b>统计</b>',id : 'btn',iconCls : 'btnSearch',
    					handler : searchsendGoods
    	},'-',
	 	{text:'<b>导出</b>',iconCls:'sort_down',tooltip : '导出数据',
	 		handler:function(){
	 		   parent.exportExl(sendGoodsGrid);
	 		}
	 	},'-','<font color=red>默认统计一个月之内的数据</font>'
	 	];
	var twobar = new Ext.Toolbar([
		'部门名称:',{xtype : "combo",
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
                     	searchsendGoods();
                     }
	 		}
	 	
	 	}},'-','代理公司:',{xtype : "combo",
				width : defaultWidth,
				editable:true,
				triggerAction : 'all',
				typeAhead : true,
				queryParam : 'filter_LIKES_cusName',
				pageSize : comboSize,
				forceSelection : true,
				resizable:true,
				minChars : 0,
				store : cpNameStore,
				mode : "remote",// 从本地载值
				valueField : 'cusName',// value值，与fields对应
				displayField : 'cusName',// 显示值，与fields对应
			    name : 'cpName',
			    id:'searchCpName', 
			    enableKeyEvents:true,listeners : {
	 		    keyup:function(textField, e){
                     if(e.getKey() == 13){
                     	searchSeparateDial();
                     }
	 		}
	 	
	 	}},'-','送货员:',{xtype : "combo",
				width : defaultWidth,
				typeAhead : true,
				pageSize : comboSize,
				forceSelection : true,
				selectOnFocus : true,
				resizable : true,
				minChars : 0,
				queryParam : 'filter_LIKES_userName',
				triggerAction : 'all',
				store : cusServiceStore,
				mode : "remote",// 从服务器端加载值
				valueField : 'cusServiceId',// value值，与fields对应
				displayField : 'cusServiceName',// 显示值，与fields对应
			    name : 'cusServiceName',
			    id:'searchcusService', 
			    enableKeyEvents:true
	 		}
	 	]);	
	 	
Ext.onReady(function() {
//汇总
 summary = new Ext.ux.grid.GridSummary({autoScroll : true});
   	dateStore = new Ext.data.Store({
        storeId:"dateStore",
        proxy: new Ext.data.HttpProxy({url : sysPath+ "/"+searchMapUrl}),
        baseParams:{privilege:privilege,limit : pageSize},
        listeners:{
        	'load':function(store,e){
       			var cmItems = [sm,rowNum]; 
				var afields=['DEPART_ID','DEPART_NAME','FNAME','SUMCOL'];
				cmItems.push( {header:'部门编号',dataIndex:"DEPART_ID",width:colWidth,sortable:true,hidden:true});
				cmItems.push({header:"部门名称",dataIndex :'DEPART_NAME',width:colWidth,sortable:true});
            	cmItems.push({header:'统计名称',dataIndex:"FNAME",width:colWidth,sortable:true});
				cmItems.push({header:"合计",dataIndex :'SUMCOL',width:colWidth,sortable:true});
				
				reDoGrid(cmItems,afields,store,sendGoodsGrid);
	        }
        },
        reader: new Ext.data.JsonReader({
            root: 'resultMap', totalProperty: 'totalCount'
        }, fields)
    });
   
   dateDetailStore = new Ext.data.Store({
        storeId:"dateDetailStore",
        proxy: new Ext.data.HttpProxy({url : sysPath+ "/"+searchDetailMapUrl}),
        baseParams:{privilege:privilege,limit : pageSize},
        listeners:{
        	load:function(store){
        		fnSumInfo();
        	}
        },
        reader: new Ext.data.JsonReader({
            root: 'resultMap', totalProperty: 'totalCount'
        }, detailFields)
    });
   var sm = new Ext.grid.CheckboxSelectionModel({});
    var sm2 = new Ext.grid.CheckboxSelectionModel({});
	var rowNum = new Ext.grid.RowNumberer({
        header:'序号', width:30
    });
    sendGoodsGrid = new Ext.grid.GridPanel({
        renderTo: Ext.getBody(),
        id:'sendGoodsCenter',
        region : 'north',
        height:Ext.lib.Dom.getViewHeight()/2,		
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
            {header:'部门编号',dataIndex:"DEPART_ID",width:colWidth,sortable:true,hidden:true},
            {header:'部门名称',dataIndex:"DEPART_NAME",width:colWidth,sortable:true},
            {header:'统计名称',dataIndex:"FNAME",width:colWidth,sortable:true},
            {header:"合计",dataIndex :'SUMCOL',width:colWidth,sortable:true}
        ]),
        store:dateStore,
        tbar: tbar,
        listeners : {
			render : function() {
				twobar.render(this.tbar);
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
    
    sendGoodsDetailGrid = new Ext.grid.GridPanel({
        renderTo: Ext.getBody(),
        id:'sendGoodsDetailCenter',
        region : 'south',
        height:Ext.lib.Dom.getViewHeight()/2,		
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
        sm:sm2,
         plugins : [summary], // 合计
       cm:new Ext.grid.ColumnModel([rowNum,sm2,
            {header:'日期',dataIndex:"riqi",width:defaultWidth,sortable:true},
            {header:'送货员',dataIndex:"endDepartName",width:defaultWidth,sortable:true},
            {header:'上午送货收入',dataIndex:"amshouru",width:defaultWidth,sortable:true},
            {header:'下午送货收入',dataIndex:"pmshouru",width:defaultWidth,sortable:true},
            {header:'上午送货票数',dataIndex:'ampiece',width:defaultWidth,sortable:true},
			{header:'下午送货票数',dataIndex:'pmpiece',width:defaultWidth,sortable:true},
			{header:'上午折合票数',dataIndex:'amcountpiece',width:defaultWidth,sortable:true},
			{header:'下午折合票数',dataIndex:'pmcountpiece',width:defaultWidth,sortable:true},
			{header:'上午重量',dataIndex:'amweight',width:defaultWidth,sortable:true},
			{header:'下午重量',dataIndex:'pmweight',width:defaultWidth,sortable:true},
			{header:'上午签单费用',dataIndex:'amqiandan',width:defaultWidth,sortable:true},
			{header:'下午签单费用',dataIndex:'pmqiandan',width:defaultWidth,sortable:true},
			{header:'早班发车时间',dataIndex:'unloadStartTime',width:defaultWidth,sortable:true},
			{header:'早班返回时间',dataIndex:'unloadEndTime',width:defaultWidth,sortable:true},
			{header:'返货票数',dataIndex:'returnNum',width:defaultWidth,sortable:true},
			{header:'签收票数',dataIndex:'qianshou',width:defaultWidth,sortable:true},
			{header:'停车费',dataIndex:'stopFee',width:defaultWidth,sortable:true},
			{header:'上午用车类型',dataIndex:'useCarType',width:defaultWidth,sortable:true}
        ]),
        store:dateDetailStore,
        tbar:[
        	{text:'<b>导出</b>',iconCls:'sort_down',handler:function() {
                	parent.exportExl(sendGoodsDetailGrid);
        } }
        ],
        bbar: new Ext.PagingToolbar({
            pageSize: pageSize, 
            store: dateDetailStore,
            displayInfo: true,
            displayMsg: '显示第 {0} 条到 {1} 条记录，共 {2} 条', 
            emptyMsg: "没有记录信息显示",
            plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
            items : ['-', '&nbsp;&nbsp;', '-', {
				text : '合计',
				iconCls : 'addIcon',
				handler : function() {
					summary.toggleSummary();
				}
			}]
        })
    });
  });
    
	/**
	 * 汇总表格
	 */
	function fnSumInfo() {
		Ext.Ajax.request({
			url : sysPath+'/'+searchDetailCountUrl,
			params:dateDetailStore.baseParams,
			success : function(response) { // 回调函数有1个参数
				summary.toggleSummary(true);
				//this.view.summary.setStyle('overflow-x', 'auto');
				summary.setSumValue(Ext.decode(response.responseText));
			},
			failure : function(response) {
				Ext.MessageBox.alert('提示', '汇总数据失败');
			}
		});
	}
	
 function searchsendGoods() {
 	dateStore.baseParams={start : 0,limit:pageSize};
	var authorityDepartId=Ext.getCmp('authorityDepartId').getValue();
	var searchcusService = Ext.getCmp('searchcusService').getRawValue();
	var searchCpName = Ext.getCmp('searchCpName').getRawValue();
	if(authorityDepartId.length>0){
		searchDepartId=authorityDepartId;
	}else{
		searchDepartId=bussDepart;
	}
	 if(!panduan()){
		return;
	 }
 	 Ext.apply(dateStore.baseParams, {
 		filter_countRange:searchCountRange,
 	 	filter_startCount:startCount,
 	 	filter_endCount:endCount,
 	 	filter_countCheckItems:countCheckItems,
 		filter_D_departId:searchDepartId,
 		filter_LIKES_f_cpName:searchCpName,
 		filter_LIKES_endDepartName:searchcusService
 	});
	dataReload();

 	 Ext.apply(dateDetailStore.baseParams, {
 		filter_countRange:searchCountRange,
 	 	filter_startCount:startCount,
 	 	filter_endCount:endCount,
 	 	filter_countCheckItems:countCheckItems,
 		filter_startDepartId:searchDepartId,
 		filter_LIKES_F_cpName:searchCpName,
 		filter_LIKES_endDepartName:searchcusService
 	});
	dateDetailStore.reload({
		params : {
			start : 0,
			limit:pageSize
		}
	});
}


 function dataReload(){
			dateStore.reload({
			params : {
				start : 0,
				limit:pageSize
				}
			})
		}
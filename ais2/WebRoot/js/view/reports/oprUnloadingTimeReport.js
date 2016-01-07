//卸货时效报表js
var searchMapUrl='reports/unloadingTimeReport!findUnloadingTimeList.action';//卸货时效查询地址
var searchDetailMapUrl='reports/unloadingTimeReport!findUnloadingTimeDetailList.action';//卸货时效明细查询地址
var authorityDepartUrl='sys/basRightDepartAction!findDepartName.action';//权限部门查询地址
var dictionaryUrl='sys/dictionaryAction!ralaList.action';//数据字典地址
var loadingbrigadeUrl='sys/loadingbrigadeAction!list.action';//装卸组分拨组查询地址
var privilege=146;
var defaultWidth=80;
var colWidth=80;
var searchDepartId=bussDepart;
var fields=['ONAME','END_DEPART_ID','DEPART_NAME','TAISHU'];

var detailFields=[
	{name:'createTime',mapping:'CREATE_TIME'},
	{name:'carId',mapping:'CAR_ID'},
	{name:'carCode',mapping:'CAR_CODE'},
	{name:'totalWeight',mapping:'TOTAL_WEIGHT'},
	{name:'unloadStartTime',mapping:'UNLOAD_START_TIME'},
	{name:'unloadEndTime',mapping:'UNLOAD_END_TIME'},
	{name:'trueTime',mapping:'TRUETIME'},
	{name:'unloadingStandredTime',mapping:'UNLOADING_STANDARD_TIME'},
	{name:'flag',mapping:'FLAG'},'LOADINGBRIGADEID','LOADINGNAME'
];
	//装卸组
	 loadingbrigadeStore= new Ext.data.Store({ 
		storeId:"loadingbrigadeStore",
		baseParams:{filter_EQL_type:0,filter_EQL_departId:bussDepart},
		proxy:new Ext.data.HttpProxy({url:sysPath+"/"+loadingbrigadeUrl}),
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
    	{name:'loadingbrigadeId',mapping:'id'},
    	{name:'loadingbrigadeName',mapping:'loadingName'}
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
   		unloadingTimeGrid.getTopToolbar().remove(startone);
		unloadingTimeGrid.getTopToolbar().remove(endone);
   	}
   	function addOne(){
   		unloadingTimeGrid.getTopToolbar().insert(6,startone);
		unloadingTimeGrid.getTopToolbar().insert(8,endone);
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
					['unload_end_time', '卸车结束日期']],
			listeners: {   
				afterRender: function(combo) {   
		       　　combo.setValue('unload_end_time');
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
					unloadingTimeGrid.getTopToolbar().doLayout(true);
				}
			}
		},
		'开始:',startone,' 至 ',endone,'-',{text : '<b>统计</b>',id : 'btn',iconCls : 'btnSearch',
    					handler : searchSunloadingTime
    	},'-',
	 	{text:'<b>导出</b>',iconCls:'sort_down',tooltip : '导出数据',
	 		handler:function(){
	 		   parent.exportExl(unloadingTimeGrid);
	 		}
	 	},'-','<font color=red>默认统计一个月之内的数据</font>'
	 	];
	var twobar=new Ext.Toolbar(['部门名称:',{xtype : "combo",
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
                     	searchSunloadingTime();
                     }
	 		}
	 	
	 	}},'-','装卸组:', {

			xtype : "combo",
			width : 100,
			id : 'loadingName',
			queryParam : 'filter_LIKES_loadingName',
			pageSize : comboSize,
			minChars : 0,
			editable:true,
			forceSelection : true,
			selectOnFocus : true,
			resizable : true,
			triggerAction : 'all',
			store : loadingbrigadeStore,
			minChars : 0,
			mode : "remote",// 从本地加载值
			valueField : 'loadingbrigadeId',// value值，与fields对应
			displayField : 'loadingbrigadeName',// 显示值，与fields对应
			hiddenName : 'loadingbrigadeId'

	}
	 			
	 	]);	
	 	
Ext.onReady(function() {
   	dateStore = new Ext.data.Store({
        storeId:"dateStore",
        proxy: new Ext.data.HttpProxy({url : sysPath+ "/"+searchMapUrl}),
        baseParams:{privilege:privilege,limit : pageSize,filter_EQL_endDepartId:searchDepartId},
        listeners:{
        	'load':function(store,e){
        	
       			var cmItems = [sm,rowNum]; 
       			
				var afields=['ONAME','END_DEPART_ID','DEPART_NAME','TAISHU'];
				cmItems.push( {header:'到达部门ID',dataIndex:"END_DEPART_ID",width:colWidth,sortable:true,hidden:true});
				cmItems.push({header:"到达部门",dataIndex :'DEPART_NAME',width:colWidth,sortable:true});
				cmItems.push({header:"统计名称",dataIndex :'ONAME',width:colWidth,sortable:true});
				cmItems.push({header:"合计",dataIndex :'TAISHU',width:colWidth,sortable:true
					,renderer:function renderDescn(v, cellmeta, record, rowIndex, columnIndex, store){
						if(v>0){
							//cellmeta.css = 'x-grid-back-blue';
						}
						return v;
					}
				});
				reDoGrid(cmItems,afields,store,unloadingTimeGrid);
	        }
        },
        reader: new Ext.data.JsonReader({
            root: 'resultMap', totalProperty: 'totalCount'
        }, fields)
    });
   dateDetailStore =  new Ext.data.Store({
        storeId:"dateDetailStore",
        proxy: new Ext.data.HttpProxy({url : sysPath+ "/"+searchDetailMapUrl}),
        baseParams:{privilege:privilege,limit : pageSize},
        reader: new Ext.data.JsonReader({
            root: 'resultMap', totalProperty: 'totalCount'
        }, detailFields)
    });
   var sm = new Ext.grid.CheckboxSelectionModel({});
    var sm2 = new Ext.grid.CheckboxSelectionModel({});
	var rowNum = new Ext.grid.RowNumberer({
        header:'序号', width:30
    });
    unloadingTimeGrid = new Ext.grid.GridPanel({
        renderTo: Ext.getBody(),
        id:'unloadingTimeCenter',
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
            {header:'到达部门ID',dataIndex:"endDepartId",width:80,sortable:true,hidden:true},
            {header:'到达部门',dataIndex:"departName",width:80,sortable:true},
            {header:'统计名称',dataIndex:"oname",width:80,sortable:true
            	,renderer:function (v, cellmeta, record, rowIndex, columnIndex, store){
						if(v=='xiehuo'){
							return '卸货及时台数';
						}else if(v=='reachNum'){
							return '到达车辆台数';
						}else if(v=='else'){
							return '其他';
						}else if(v=='jishilv'){
							return '卸货及时率';
						}
 					}
            },
            {header:'合计',dataIndex:"taishu",width:80,sortable:true}
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
            filter_EQL_endDepartId:searchDepartId,
            displayInfo: true,
            displayMsg: '显示第 {0} 条到 {1} 条记录，共 {2} 条', 
            emptyMsg: "没有记录信息显示"
        })
    });
    
     unloadingTimeDetailGrid = new Ext.grid.GridPanel({
        renderTo: Ext.getBody(),
        id:'unloadingTimeDetailCenter',
        region : 'south',
        height:Ext.lib.Dom.getViewHeight()/2,		
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
        sm:sm2,
        cm:new Ext.grid.ColumnModel([rowNum,sm2,
            {header:'卸车日期',dataIndex:"createTime",width:80,sortable:true},
            {header:'车牌',dataIndex:"carCode",width:80,sortable:true},
            {header:'重量',dataIndex:"totalWeight",width:80,sortable:true},
            {header:'装卸组',dataIndex:'LOADINGNAME',width:70,sortable:true},
            {header:'开始卸车时间',dataIndex:'unloadStartTime',width:70,sortable:true},
			{header:'卸车结束时间',dataIndex:'unloadEndTime',width:70,sortable:true},
			{header:'实际卸车时长(分钟)',dataIndex:'trueTime',width:70,sortable:true},
			{header:'标准卸车时长',dataIndex:'unloadingStandredTime',width:70,sortable:true},
			{header:'卸货是否及时',dataIndex:'flag',width:70,sortable:true
				,renderer:function (v, cellmeta, record, rowIndex, columnIndex, store){
						return v==1?'是':'否';
 					}
			}
        ]),
        store:dateDetailStore,
        tbar:[{text:'<b>导出</b>',iconCls:'sort_down',tooltip : '导出数据',
	 		handler:function(){
	 		   //parent.exportExl(unloadingTimeGrid);
	 		   parent.exportExl(unloadingTimeDetailGrid);
	 		}
	 	}],
        bbar: new Ext.PagingToolbar({
            pageSize: pageSize, 
            store: dateDetailStore, 
            displayInfo: true,
            displayMsg: '显示第 {0} 条到 {1} 条记录，共 {2} 条', 
            emptyMsg: "没有记录信息显示"
        })
    });
    
     unloadingTimeGrid.on('rowclick', function() {
	     var record = unloadingTimeGrid.getSelectionModel().getSelected();
	     if(null!=record){
	     	
	     	var flag='';
	     	if(record.data.ONAME=="卸车及时台数"){
	     		flag='1';
	     	}else if(record.data.ONAME=="不及时台数"){
	     		flag='0';
	     	}
	     	
	        Ext.apply(dateDetailStore.baseParams, {
	     		filter_flag:flag
	     	});
	     	
	     	dateDetailStoreReload(record.data.END_DEPART_ID,record);
	     }
	 });
  });
    
    
	
 function searchSunloadingTime() {
	var pubauthorityDepart=Ext.getCmp('authorityDepartId').getValue();
	if(pubauthorityDepart.length>0){
		searchDepartId=pubauthorityDepart;
	}else{
		searchDepartId=bussDepart;
	}
	if(!panduan()){
		return;
	}
	var loadingName =Ext.getCmp('loadingName').getRawValue();
 	 dateStore.baseParams={
 		filter_countRange:searchCountRange,
 	 	filter_startCount:startCount,
 	 	filter_endCount:endCount,
 	 	filter_countCheckItems:countCheckItems,
 	 	
 		limit:pageSize,
 	    filter_endDepartId:searchDepartId,
 	    filter_loadingName:loadingName
 		//filter_cpName:searchCpName
 	};
 	
 	Ext.apply(dateDetailStore.baseParams, {
 		//filter_countDate:countDate,
		filter_EQL_endDepartId:searchDepartId,
		filter_loadingName:loadingName,
		filter_flag:'',
		limit:pageSize
 	});
	dataReload();
	dateDetailStoreReload(searchDepartId,null);
}
function dateDetailStoreReload(deptId,record){
	if(null!=record){
		
	}
	dateDetailStore.reload({
		params : {
			start : 0,
			filter_countRange:searchCountRange,
	 	 	filter_startCount:startCount,
	 	 	filter_endCount:endCount,
	 	 	filter_countCheckItems:countCheckItems,
			filter_EQL_endDepartId:deptId,
			limit:pageSize
		}
	})
}

 function dataReload(){
			dateStore.reload({
			params : {
				start : 0,
				limit:pageSize
				}
			})
		}
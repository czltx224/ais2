//出港时效报表查询显示js
var searchReportUrl='reports/oprOutPortReportAction!findList.action';//时效报表查询地址
var dictionaryUrl='sys/dictionaryAction!ralaList.action';//数据字典地址
var carUrl = "bascar/basCarAction!list.action";//车辆查询地址
var searchCusNameUrl='sys/customerAction!list.action';//查询代理公司地址
var authorityDepartUrl='sys/basRightDepartAction!findDepartName.action';//权限部门查询地址
var privilege=197;
var defaultWidth=60;
var colWidth=100;
var pubauthorityDepart=bussDepart;
var fields=['ID','OUTDATE','DNO','CPNAME','OPERATIONTYPE','OUTSTOCKTIME','OUTSTANDARDTIME','OUTISSTANDART',
			'OUTDEFERHOUR','DEPTID','DEPTNAME','REACHTIME','COUNTDATE','REAL_GO_WHERE'];
			
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

	//代理公司
	cpNameStore= new Ext.data.Store({ 
		storeId:"cpNameStore",
		baseParams:{privilege:61},
		proxy:new Ext.data.HttpProxy({url:sysPath+"/"+searchCusNameUrl}),
		reader:new Ext.data.JsonReader({
                    root:'result', totalProperty:'totalCount'
        },[
    	{name:'cusName',mapping:'cusName'},
    	{name:'cusId',mapping:'id'}
    	])
	});
	 //操作方式224
		operationStore	= new Ext.data.Store({ 
			storeId:"operationStore",
			baseParams:{filter_EQL_basDictionaryId:224,privilege:16},
			proxy:new Ext.data.HttpProxy({url:sysPath+"/"+dictionaryUrl}),
			reader:new Ext.data.JsonReader({
	                    root:'result',
	                    totalProperty:'totalCount'
	        },[
        	{name:'operationId',mapping:'typeCode'},
        	{name:'operationName',mapping:'typeName'}
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
					['countDate', '统计日期'],
					['outDate', '出库日期'],
					['reachTime', '点到日期']],
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
					enterPortReportGrid.getTopToolbar().doLayout(true);
				}
			}
		},
		'开始:',startone,' 至 ',endone,'-',{text : '<b>统计</b>',id : 'btn',iconCls : 'btnSearch',
    					handler : searchSenterPortReport
    	},'-',{text:'<b>详细信息</b>',iconCls:'searchDetail',
				handler:function() {
                	searchInformation();
        		} 
        },
	 	{text:'<b>导出</b>',iconCls:'sort_down',tooltip : '导出数据',
	 		handler:function(){
	 		   parent.exportExl(enterPortReportGrid);
	 		}
	 	}
	 			
	 	];	
	 	var twoBar = new Ext.Toolbar([ '发货代理:',{xtype : "combo",
				width : defaultWidth+20,
				editable:true,
				triggerAction : 'all',
				//typeAhead : true,
				queryParam : 'filter_LIKES_cusName',
				pageSize : pageSize,
				//forceSelection : true,
				resizable:true,
				minChars : 0,
				store : cpNameStore,
				//mode : "remote",// 从本地载值
				valueField : 'cusName',// value值，与fields对应
				displayField : 'cusName',// 显示值，与fields对应
			    id:'searchCpName', 
			    enableKeyEvents:true,
			    listeners : {
	 		   	 keyup:function(combo, e){
                     if(e.getKey() == 13){
                     	//searchcqCorporateRate();
                     }
	 		}
	 	
	 	}},'-','出库是否达标:',{
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
	 		},'-','实际去向:',{
	 			xtype:'textfield',
	 			id:'searchRealGoWhere',
	 			width:100,
	 			enableKeyEvents:true,
			    listeners : {
	 		   	    keyup:function(combo, e){
	                     if(e.getKey() == 13){
	                     	searchSenterPortReport();
	                     }
	 				}
	 			}
	 		}
		]);
		
	var threeBar = new Ext.Toolbar([ 
		'配送单号:', {
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
		},'-','责任单位:',{
			xtype:'textfield',
			id:'searchDuty',
			width:defaultWidth+20
			
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
	 	
	 	}},'-','操作方式:',{ xtype : 'combo',
				triggerAction : 'all',
				store : operationStore,
				forceSelection : true,
				mode : "remote",//获取服务器的值
				valueField : 'operationName',//value值，与fields对应
				displayField : 'operationName',//显示值，与fields对应
				id:'searchOperationType',
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
    enterPortReportGrid = new Ext.grid.GridPanel({
        renderTo: Ext.getBody(),
        id:'enterPortReportCenter',
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
            {header:'统计日期',dataIndex:"COUNTDATE",width:colWidth+20,sortable:true},
			{header:'点到时间',dataIndex:'REACHTIME',width:colWidth,sortable:true},
            {header:'出库日期',dataIndex:"OUTDATE",width:colWidth,sortable:true},
			{header:'配送单号',dataIndex:'DNO',width:colWidth,sortable:true},
			{header:'发货代理',dataIndex:'CPNAME',width:colWidth,sortable:true},
			{header:'操作方式',dataIndex:'OPERATIONTYPE',width:colWidth,sortable:true},
			{header:'实际去向',dataIndex:'REAL_GO_WHERE',width:colWidth+50,sortable:true},
			{header:'出库时间',dataIndex:'OUTSTOCKTIME',width:colWidth,sortable:true},
			{header:'出港最迟时间',dataIndex:'OUTSTANDARDTIME',width:colWidth,sortable:true},
			{header:'出港是否达标',dataIndex:'OUTISSTANDART',width:colWidth,sortable:true,align:'center'
				,renderer:function renderDescn(v, cellmeta, record, rowIndex, columnIndex, store){
					if(v=='是'){
						cellmeta.css = 'x-grid-back-green';
					}else{
						cellmeta.css = 'x-grid-back-red';
					}
					return v;
				}
			},
			{header:'延时数',dataIndex:'OUTDEFERHOUR',width:colWidth,sortable:true},
			{header:'部门名称',dataIndex:'DEPTNAME',width:colWidth,sortable:true}
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
    //添加双击事件，双击查看明细
    enterPortReportGrid.on('rowdblclick',function(grid,index,e){
		searchInformation();
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

	var searchCountRange = Ext.getCmp('searchCountRange').getRawValue();
	if(null==searchCountRange || searchCountRange.length<1){
		searchCountRange='日';
	}
	var startCount = Ext.get('startone').dom.value;
	var endCount = Ext.get('endone').dom.value;
	var searchCpName = Ext.getCmp('searchCpName').getRawValue();
	var searchOutStandard = Ext.getCmp('searchOutStandard').getRawValue();
	var searchDno = Ext.getCmp('searchDno').getValue();
	
	//alert(startCount+'-'+endCount);searchFlightTime   searchDuty
	var countCheckItems = Ext.get('checkItems').dom.value;
	var searchDuty = Ext.getCmp('searchDuty').getValue();
	var searchRealGoWhere = Ext.get('searchRealGoWhere').dom.value;
	
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
	var searchOperationType = Ext.get('searchOperationType').dom.value;
 	 Ext.apply(dateStore.baseParams, {
 	 	filter_LIKES_f_realGoWhere:searchRealGoWhere,
 	 	filter_countRange:searchCountRange,
 	 	filter_startCount:startCount,
 	 	filter_t_cpName:searchCpName,
 	 	filter_endCount:endCount,
 	 	filter_outIsStandart:searchOutStandard,
 	 	filter_t_dno:searchDno,
 	 	filter_deptId:pubauthorityDepart,
 	 	filter_countCheckItems:countCheckItems,
 	 	filter_LIKES_takeDutyUnit:searchDuty,
 	 	filter_operationType:searchOperationType,
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
 function searchInformation(){
 	var rd = enterPortReportGrid.getSelectionModel().getSelected();
 	if(null!=rd){
		var vdno = rd.get('DNO');
		searchDnoInfo(vdno,pubauthorityDepart);
	}
 }
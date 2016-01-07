//装卸货量js
var popupWin;
var dictionaryUrl='sys/dictionaryAction!ralaList.action';//数据字典地址
var searchMapUrl='reports/inuploadGoods!findInuploadGoods.action';//装卸货量统计地址
var loadingbrigadeUrl='sys/loadingbrigadeAction!list.action';//装卸分拨组查询地址
var authorityDepartUrl='sys/basRightDepartAction!findDepartName.action';//权限部门查询地址
var cusServiceUrl='user/userAction!list.action';//用户查询地址
var privilege=57;
var defaultWidth=60;
var colWidth=80;
var carTitle;
var fields=[
	{name:'LOADING_NAME'},
    {name: 'LOADINGBRIGADE_TYPE'},
    {name:'SUMCOL'}
];
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
		
		var cusStore= new Ext.data.Store({ 
			storeId:"cusStore",
			proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/customerAction!list.action"}),
			reader:new Ext.data.JsonReader({
	                  root:'result', totalProperty:'totalCount'
	        },[
        	{name:'cusName',mapping:'cusName'},
        	{name:'cusId',mapping:'id'}
        	])
		});
		
    var loadingbrigadeStore= new Ext.data.Store({ 
		storeId:"loadingbrigadeStore",
		baseParams:{filter_EQL_type:0},
		proxy:new Ext.data.HttpProxy({url:sysPath+"/"+loadingbrigadeUrl}),
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
    	{name:'loadingbrigadeId',mapping:'id'},
    	{name:'loadingbrigadeName',mapping:'loadingName'}
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
   		menuGrid.getTopToolbar().remove(startone);
		menuGrid.getTopToolbar().remove(endone);
   	}
   	function addOne(){
   		menuGrid.getTopToolbar().insert(6,startone);
		menuGrid.getTopToolbar().insert(8,endone);
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
					['t.createTime', '统计日期']],
			listeners: {   
				afterRender: function(combo) {   
		       　　combo.setValue('t.createTime');
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
					menuGrid.getTopToolbar().doLayout(true);
				}
			}
		},
		'开始:',startone,' 至 ',endone,'-',{text : '<b>统计</b>',id : 'btn',iconCls : 'btnSearch',
    					handler : count
    	},'-',
	 	{text:'<b>导出</b>',iconCls:'sort_down',tooltip : '导出数据',
	 		handler:function(){
	 		   parent.exportExl(menuGrid);
	 		}
	 	},'-','<font color=red>默认统计一个周之内的数据</font>'
	 	];
	 	
 //送货员 cusServiceUrl
	var sendManStore=new Ext.data.Store({
	        storeId:"sendManStore",
			proxy:new Ext.data.HttpProxy({url:sysPath+"/"+cusServiceUrl}),
			baseParams:{filter_EQL_bussDepart:bussDepart},
			reader:new Ext.data.JsonReader({
	                    root:'result',
	                    totalProperty:'totalCount'
	        },[
        	{name:'userId',mapping:'id'},
        	{name:'userName',mapping:'userName'}
        	])
        	
		});
		var sendManStore2=new Ext.data.Store({
	        storeId:"sendManStore2",
			proxy:new Ext.data.HttpProxy({url:sysPath+"/"+cusServiceUrl}),
			baseParams:{filter_EQL_bussDepart:bussDepart},
			reader:new Ext.data.JsonReader({
	                    root:'result',
	                    totalProperty:'totalCount'
	        },[
        	{name:'userId',mapping:'id'},
        	{name:'userName',mapping:'userName'}
        	])
        	
		});
		var sendManSearchStore=new Ext.data.Store({
	        storeId:"sendManSearchStore",
			proxy:new Ext.data.HttpProxy({url:sysPath+"/"+cusServiceUrl}),
			baseParams:{filter_EQL_bussDepart:bussDepart},
			reader:new Ext.data.JsonReader({
	                    root:'result',
	                    totalProperty:'totalCount'
	        },[
        	{name:'userId',mapping:'id'},
        	{name:'userName',mapping:'userName'}
        	])
        	
		});
var sendGrid2;
function removeRecord(){
  	var record = sendGrid2.getSelectionModel().getSelected();
    sendManStore2.remove(record); 
	// sendGrid2.doLayout();
}
function choseSendMan(){
	var sendrownum=new Ext.grid.RowNumberer({
		header:'序号',
        width : 30,
		sortable : true	
	});
	var sendsm = new Ext.grid.CheckboxSelectionModel({});
	var sendbar = new Ext.Toolbar([
			'送货员名称:',{
			xtype : "combo",
			width : defaultWidth+20,
			editable:true,
			triggerAction : 'all',
			// typeAhead : true,
			queryParam : 'filter_LIKES_userName',
			pageSize : comboSize,
			resizable:true,
			minChars : 0,
			mode:'remote',
			store : sendManSearchStore,
			resizable:true,
			forceSelection : true,
			valueField : 'userName',//value值，与fields对应
			displayField : 'userName',//显示值，与fields对应
		    id:'searchSendMan', 
		    enableKeyEvents:true,
		    listeners : {
 		    	keyup:function(combo, e){
                    if(e.getKey() == 13){
                    	sendManStore.reload({
                    		params:{
                    			filter_LIKES_userName:combo.getValue()
                    		}
                    	});
                    }
 				}
 			}
 		},'-',{
 			text:'',
 			iconCls : 'btnSearch',
 			handler:function(){
 			   var sendManName = Ext.get('searchSendMan').dom.value;
	 		  sendManStore.reload({
              		params:{
              			filter_LIKES_userName:sendManName
              		}
              });
	 		}
 			
 		}
 		]);
	var sendGrid = new Ext.grid.GridPanel({
		id:'sendGrid',
		//height : Ext.lib.Dom.getViewHeight(),
		width : 200,
		height: 400,
		autoScroll : true,  //面板上的body元素
	 	viewConfig : {
				scrollOffset: 0,
				autoScroll:true
		},
		frame : true,
	//	enableColumnResize:false, //关闭列的自适大小功能
		autoWidth:false,
	//	clicksToEdit:1,
		loadMask : true,
		sm:sendsm,
		tbar:sendbar,
		stripeRows : true,
		columns:[ sendrownum,sendsm,
					{header: '编号', dataIndex: 'userId',width:55,sortable : true},
					{header: '送货员', dataIndex: 'userName',width:70,sortable : true}
			    ],
		store :sendManStore,
		listeners : {
			'rowclick' : function(f,rowIndex,e){
				var record = f.getSelectionModel().getSelections();
				findSearch(record);
			}
		},
		bbar : new Ext.PagingToolbar({
			pageSize : pageSize, 
			store : sendManStore,
			displayInfo : true,
			displayMsg : '显示第 {0} 条到 {1} 条记录，共 {2} 条',
			emptyMsg : "没有记录信息显示"
		})
	});
	sendGrid2 = new Ext.grid.GridPanel({
		id:'sendGrid2',
		width : 200,
		height: 400,
		autoScroll : true,  //面板上的body元素
	 	viewConfig : {
				scrollOffset: 0,
				autoScroll:true
		},
		frame : true,
		autoWidth:false,
		loadMask : true,
		stripeRows : true,
		columns:[ 
					{header: '编号', dataIndex: 'userId',width:55,sortable : true,hidden:true},
					{header: '送货员', dataIndex: 'userName',width:100,sortable : true},
					{header: '移除', dataIndex: 'userName',width:50,
						renderer:function renderDescn(v, cellmeta, record, rowIndex, columnIndex, store){
       						//dateStore.remove(rowIndex);dateStore.remove(record)
       						return "<a href='#' onclick='removeRecord();'>删除</a>";
       					}
       				}
			    ],
		store :sendManStore2
	});
	var sendManform =   new Ext.form.FormPanel({
		labelAlign : 'left',
			frame : true,
			width : 400,
			autoScroll : true,  //面板上的body元素
			bodyStyle : 'padding:5px 5px 0',
			reader : new Ext.data.JsonReader({
            root: 'result', totalProperty: 'totalCount'
        }, fields),
        labelAlign : "right",
		items : [{
			layout : 'column',
				items : [{
							columnWidth : .5,
							layout : 'form',
							items : [sendGrid]

						},{
							columnWidth : .5,
							layout : 'form',
							items : [sendGrid2]
						}]
		}]
	});
	var sendwin = new Ext.Window({
		title : '选择送货员',
		width : 400,
		closeAction : 'hide',
		autoScroll : true,  //面板上的body元素
		plain : true,
		modal : false,
		collapsible:true,
		resizable : false,
		items : sendManform,
		buttonAlign : "center",
		tbar:[
			{	text:'添加',
				iconCls : 'groupAdd',
				handler : function() {
					var rds = sendGrid.getSelectionModel().getSelections();
					sendManStore2.insert(0,rds);
					//sendGrid2.dolayout();
				}
			}
		],
		buttons : [{
			text : "确定",
			iconCls : 'groupSave',
			id:'saveBtn',
			handler : function() {
				var sendMan='';
				for(var i=0;i<sendManStore2.getCount();i++){
					sendMan+=sendManStore2.getAt(i).get('userName');
					if(i!=sendManStore2.getCount()-1){
						sendMan+=',';
					}
				}	
				Ext.getCmp('searchUserName').setValue(sendMan);
				sendwin.hide();
			}
		},  {
			text : "取消",
			handler : function() {
				sendwin.hide();
			}
		}]
	});
	sendwin.show();
	}
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
                     	count();
                     }
	 			}
	 		}
	 	},'装卸组:',
            {
            	xtype : "combo",
				width : 100,
				id : 'loadingbrigade',
				queryParam : 'filter_LIKES_loadingName',
				pageSize : pageSize,
				minChars : 0,
				forceSelection : true,
				selectOnFocus : true,
				triggerAction : 'all',
				store : loadingbrigadeStore,
				minChars : 0,
				emptyText:'请选择',
				mode : "remote",// 从本地加载值
				valueField : 'loadingbrigadeId',// value值，与fields对应
				displayField : 'loadingbrigadeName',// 显示值，与fields对应
				hiddenName : 'loadingbrigadeId'
            	
            },'-','类型:',
            {
            	xtype:'combo',
            	id:'loadingType',
            	name:'loadingType',
            	width:100,
            	emptyText:'请选择',
            	forceSelection : true,
				selectOnFocus : true,
            	triggerAction : 'all',
				model : 'local',
            	store:[
            		['0','卸货'],
            		['1','装货'],
            		['2','提货'],
            		['4','接货']
            	],
            	blankText:'请选择'
            },'-','代理公司:',
            {
            	xtype:'combo',
				anchor:'100%',
				triggerAction :'all',
				model : 'local',
				id:'combocus',
				minChars : 0,
				width:100,
				store:cusStore,
				queryParam :'filter_LIKES_cusName',
				pageSize:pageSize,
				valueField :'cusId',
				displayField :'cusName',
				forceSelection : true
            },'-','送货员:',
            {
            	xtype : 'textfield',
				id:'searchUserName',
				width:200
			},{
				text : "选择送货员",
				iconCls:'groupEdit',
				handler : function() {
					choseSendMan();
				}
			}
        ]);	

	
	
Ext.onReady(function() {
     dateStore = new Ext.data.Store({
        storeId:"dateStore",
        baseParams:{limit:pageSize},
        proxy: new Ext.data.HttpProxy({url:sysPath+"/"+searchMapUrl}),
        listeners:{
        	'load':function(store,e){
       			var cmItems = [sm,rowNum]; 
				var afields=['LOADING_NAME','LOADINGBRIGADE_TYPE','SUMCOL'];
				cmItems.push( {header:'装卸组',dataIndex:"LOADING_NAME",width:colWidth,sortable:true});
				cmItems.push({header:"统计类型",dataIndex :'LOADINGBRIGADE_TYPE',width:colWidth,sortable:true});
				cmItems.push({header:"合计",dataIndex :'SUMCOL',width:colWidth,sortable:true
					,renderer:function renderDescn(v, cellmeta, record, rowIndex, columnIndex, store){
						if(v>0){
							//cellmeta.css = 'x-grid-back-blue';
						}
						return v;
					}
				});
				reDoGrid(cmItems,afields,store,menuGrid);
	        }
        },
        reader: new Ext.data.JsonReader({
            root: 'resultMap', totalProperty: 'totalCount'
        }, fields)
    });
    //start
    var sm = new Ext.grid.CheckboxSelectionModel({});
    var rowNum = new Ext.grid.RowNumberer({
        header:'序号', width:35, sortable:true
    });
    
    menuGrid = new Ext.grid.GridPanel({
        renderTo:'showView',
        height:Ext.lib.Dom.getViewHeight(), 
        width:Ext.lib.Dom.getViewWidth(),
        viewConfig : {
    		scrollOffset: 0,
			autoScroll:true
		},
		stripeRows : true,
        frame:true, 
        loadMask:true,
        sm:sm,
        cm:new Ext.grid.ColumnModel([rowNum,sm,
            {header:'装卸组',dataIndex:"LOADING_NAME",width:colWidth},
            {header:'统计类型',dataIndex:"LOADINGBRIGADE_TYPE",width:colWidth},
            {header:'合计',dataIndex:"SUMCOL",width:colWidth,sortable:true}
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
            privilege:privilege, 
            store: dateStore, 
            displayInfo: true,
            displayMsg: '显示第 {0} 条到 {1} 条记录，共 {2} 条', emptyMsg: "没有记录信息显示"
        })
    });
});

function count() {
	if(!panduan()){
		return;
	}
	var authorityDepartId=Ext.getCmp('authorityDepartId').getValue();
	if(null!=authorityDepartId && authorityDepartId>0){
		pubauthorityDepart=authorityDepartId;
	}else{
		pubauthorityDepart=bussDepart;
	}
	var sendMans = Ext.get('searchUserName').dom.value;
	
	dateStore.baseParams={
		filter_sendMans:sendMans,
		filter_departId:pubauthorityDepart,
		filter_countRange:searchCountRange,
 	 	filter_startCount:startCount,
 	 	filter_endCount:endCount,
 	 	filter_countCheckItems:countCheckItems, 
		filter_loadingName:Ext.getCmp('loadingbrigade').getRawValue(),
		filter_loadingbrigadeType:Ext.getCmp('loadingType').getRawValue(),
		filter_cusId:Ext.getCmp('combocus').getValue(),
		limit:pageSize,
		start:0
	};
	dateStore.reload({
		limit:pageSize,
		start:0
	});
	}
   //menuGrid.render();
   function exportmsg(){
		parent.exportExl(menuGrid);
	}
    function showswf() {
    var panel=new Ext.Panel({
        iconCls:'chart',
        //title: '装卸货量图表',
        frame:true,
        width:580,
        height:300,
        layout:'fit',
        items: {
            xtype: 'linechart',
            store: dateStore,
            url: '../../resources/charts.swf',
            xField: 'LOADINGBRIGADE_TYPE',
            yField: 'COL1',
            yAxis: new Ext.chart.NumericAxis({
                displayName: 'COL1',
                labelRenderer : Ext.util.Format.numberRenderer('0,0')
            }),
            tipRenderer : function(chart, record){
                return Ext.util.Format.number(record.data.COL1, '0,0') + ' visits in ' + record.data.LOADINGBRIGADE_TYPE;
            }
        }
    });
		var win = new Ext.Window({
			title : "装卸货量图表",
			width : 580,
			closeAction : 'hide',
			plain : true,
			modal : true,
			items : panel,
			buttonAlign : "center",
			buttons : [  {
				text : "取消",
				handler : function() {
					win.close();
				}
			}]
		});
		win.on('hide', function() {
					panel.destroy();
				});
		win.show();
	}

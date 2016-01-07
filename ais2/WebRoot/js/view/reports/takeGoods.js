//提货货量统计报表
var dictionaryUrl='sys/dictionaryAction!ralaList.action';//数据字典地址
var searchMapUrl='reports/takeGoodsAction!findTakeGoods.action';//提货货量统计查询地址
var authorityDepartUrl='sys/basRightDepartAction!findDepartName.action';//权限部门查询地址
var popupWin;
var defaultWidth=80;
var colWidth=80;
var privilege=57;
var isbussDepart=1;//是业务部门
var custprop='提货公司';
var fields=['RANGE','AREATAKEGOODS','SUMCOL'];
var cusStore=new Ext.data.Store({
		storeId:"cusStore",
		method:'post',
		baseParams:{limit:pageSize,privilege:61,filter_EQS_custprop:custprop},
		proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/customerAction!list.action"}),
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
        	{name:'cusId',mapping:'id'},
        	{name:'cusName'}
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
	 	},'-','<font color=red>默认统计一个月之内的数据</font>'
	 	];
	 	
	var twobar = new Ext.Toolbar([
			'部门名称:',{xtype : "combo",
				width : 120,
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
	 	
	 	}},'-','提货处:',
            {
            	xtype : "combo",
			    width : 120,
			    triggerAction : 'all',
			    model : 'local',
			    queryParam : 'filter_LIKES_cusName',
			    pageSize:pageSize,
			    id:'takegooddepart',
			    resizable:true,
			    valueField : 'cusId',
    			displayField : 'cusName',
    			hiddenName : 'cusId',
			    name : 'checkItemscar',
			    store :cusStore,
			    emptyText : '选择类型',
			    forceSelection : true
            }
	]);
Ext.onReady(function() {
     menuStore = new Ext.data.Store({
        storeId:"menuStore",
        baseParams:{limit:pageSize},
        proxy: new Ext.data.HttpProxy({url:sysPath+"/"+searchMapUrl}),
        listeners:{
        	'load':function(store,e){
       			var cmItems = [sm,rowNum]; 
				var afields=['RANGE','AREATAKEGOODS','SUMCOL'];
				cmItems.push( {header:'区域',dataIndex:"RANGE",width:colWidth,sortable:true});
				cmItems.push({header:"提货处",dataIndex :'AREATAKEGOODS',width:colWidth,sortable:true});
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
            {header:'区域',dataIndex:"RANGE",width:80},
            {header:'提货处',dataIndex:"AREATAKEGOODS",width:80},
            {header:'合计',dataIndex:"SUMCOL",width:80,sortable:true}
        ]),
        store:menuStore,
        tbar: tbar,
        listeners : {
			render : function() {
				twobar.render(this.tbar);
			}
		},
        bbar: new Ext.PagingToolbar({
            pageSize: pageSize,
            privilege:privilege, 
            store: menuStore, 
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
	Ext.apply(menuStore.baseParams, {
		filter_countRange:searchCountRange,
 	 	filter_startCount:startCount,
 	 	filter_endCount:endCount,
 	 	filter_countCheckItems:countCheckItems, 
		filter_endDepartId:pubauthorityDepart,
		filter_startDepartId:Ext.getCmp('takegooddepart').getValue()
	});
	menuStore.load();
	}
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
            store: menuStore,
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
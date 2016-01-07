//神州数码数据对接统计查询js
var privilege=48;//权限参数
var gridSearchUrl="stock/digitalChinaExchangeAction!findCount.action";//神州数码数据对接统计查询地址
var gridTotalUrl="stock/digitalChinaExchangeAction!findCountSum.action";//统计地址
var defaultWidth=80;

var  fields=['CHUANZHEN','CHUKU','PEICHEDUIJIE','QIANSHOUDUIJIE','DUIJIE','CREATE_TIME'];
var summary = new Ext.ux.grid.GridSummary();
	//对接类型
	typeStore=new Ext.data.SimpleStore({
		 autoLoad:true, //此处设置为自动加载
 			  data:[['','-全部-'],['1','配送对接'],['2','签收对接']],
  			 fields:["propertyId","propertyName"]
	});
	var tbar=[
	 	{text:'<b>导出</b>',iconCls:'sort_down',tooltip : '导出数据',
	 		handler:function(){
	 				parent.exportExl(digitalChinaExchangeGrid);
	 		}
	 	},'对接时间:',{ 
    		xtype : 'datefield',
    		id : 'startDate',
    		format : 'Y-m-d',
    		emptyText : "开始时间",
    		anchor : '95%',
    		value:new Date().add(Date.DAY, -7),
    		hidden : false,
    		width : 100,
    		disabled : false,
    		listeners : {
    			'select' : function() {
    			   var start = Ext.getCmp('startDate').getValue().format("Y-m-d");
    			   Ext.getCmp('endDate').setMinValue(start);
    		     }
    		}
    	}, '&nbsp;&nbsp;', {
    		xtype : 'datefield',
    		id : 'endDate',
    		format : 'Y-m-d',
    		emptyText : "结束时间",
    		value:new Date(),
    		hidden : false,
    		width : 100,
    		disabled : false,
    		anchor : '95%'
    	},'-','分单号:',{
			xtype:'textfield',
			id:'searchSubNo',
			width : defaultWidth,
			enableKeyEvents:true,
			listeners : {
	 		    keyup:function(textField, e){
                     if(e.getKey() == 13){
                     	searchdigitalChinaExchange();
                     }
                 }
         	}
		},'-','配送单号:',{
			xtype:'numberfield',
			id:'searchDno',
			width : defaultWidth,
			enableKeyEvents:true,
			listeners : {
	 		    keyup:function(textField, e){
                     if(e.getKey() == 13){
                     	searchdigitalChinaExchange();
                     }
                 }
         	}
		},'-','交接单号:',{
			xtype:'numberfield',
			id:'searchOvermemo',
			width : defaultWidth,
			enableKeyEvents:true,
			listeners : {
	 		    keyup:function(textField, e){
                     if(e.getKey() == 13){
                     	searchdigitalChinaExchange();
                     }
                 }
         	}
		},'-','对接类型:',{
			xtype : 'combo',
			triggerAction : 'all',
			store : typeStore,
			forceSelection : true,
			editable : false,
			mode : "local",//获取本地的值
			displayField : 'propertyName',//显示值，与fields对应
			valueField : 'propertyId',//value值，与fields对应
			id:'typeFlag',
			width:80,
			enableKeyEvents:true,
			listeners : {
	 		    keyup:function(textField, e){
                     if(e.getKey() == 13){
                     	searchdigitalChinaExchange();
                     }
                 }
         	}
		},'-',{text : '<b>查询</b>',id : 'btn',iconCls : 'btnSearch',
   					handler : searchdigitalChinaExchange
   		}];
	 	
Ext.onReady(function() {
   	dateStore = new Ext.data.Store({
        storeId:"dateStore",
        proxy: new Ext.data.HttpProxy({url : sysPath+ "/"+gridSearchUrl}),
        baseParams:{privilege:privilege,limit : pageSize},
        reader: new Ext.data.JsonReader({
            root: 'resultMap', totalProperty: 'totalCount'
        }, fields)
    });
    //dateStore.on("load" ,function( store, records,options ){alert(records.length);});
    
   var sm = new Ext.grid.CheckboxSelectionModel({});
	 var rowNum = new Ext.grid.RowNumberer({
        header:'序号', width:20
    });
    digitalChinaExchangeGrid = new Ext.grid.GridPanel({
        renderTo: Ext.getBody(),
        id:'digitalChinaExchangeCenter',
        height:Ext.lib.Dom.getViewHeight()-1,		
        width:Ext.lib.Dom.getViewWidth()-1,
		autoScroll : true,
		//autoSizeColumns: true,
		frame : false,
		loadMask : true,
		plugins : [summary], // 合计
	//	stripeRows : true,
        sm:sm,
        cm:new Ext.grid.ColumnModel([rowNum,sm,
       			{header: '传真日期', dataIndex: 'CREATE_TIME',width:defaultWidth*2,sortable : true},
       			{header: '传真票数', dataIndex: 'CHUANZHEN',width:defaultWidth*2,sortable : true},
       			{header: '出库票数', dataIndex: 'CHUKU',width:defaultWidth*2,sortable : true},
       			{header: '对接票数', dataIndex: 'DUIJIE',width:defaultWidth*2,sortable : true},
       			{header: '配车对接票数', dataIndex: 'PEICHEDUIJIE',width:defaultWidth*2,sortable : true},
       			{header: '签收对接票数', dataIndex: 'QIANSHOUDUIJIE',width:defaultWidth*2,sortable : true}
        ]),
        store:dateStore,
        tbar: tbar,
        bbar: new Ext.PagingToolbar({
            store: dateStore, 
            pageSize: pageSize,
            afterPageText: '共{0}页',
		    beforePageText: '当前页',
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
			url : sysPath+'/'+gridTotalUrl,
			params:dateStore.baseParams,
			success : function(response) { // 回调函数有1个参数
				summary.toggleSummary(true);
				//this.view.summary.setStyle('overflow-x', 'auto');
				summary.setSumValue(Ext.decode(response.responseText));
			},
			failure : function(response) {
				Ext.MessageBox.alert(alertTitle, '汇总数据失败');
			}
		});
	}
    
	
   function searchdigitalChinaExchange() {
		dateStore.baseParams = {
			privilege:privilege,
			limit : pageSize
		};
		var startCount = Ext.get('startDate').dom.value;
		var endCount = Ext.get('endDate').dom.value;
		var searchSubNo = Ext.get('searchSubNo').dom.value;
		var searchDno = Ext.get('searchDno').dom.value;
		var searchOvermemo = Ext.get('searchOvermemo').dom.value;
		var typeFlag = Ext.getCmp('typeFlag').getValue();
 	    Ext.apply(dateStore.baseParams, {
		 	filter_countCheckItems:'f.create_time',
	 	 	filter_startCount:startCount=='开始时间'?'':startCount,
 	 		filter_endCount:endCount=='结束时间'?'':endCount
   		});
		dataReload();
		fnSumInfo();
	}
 function dataReload(){
	dateStore.reload({
		params : {
			start : 0,
			privilege:privilege,
			limit : pageSize
		}
	});
 }
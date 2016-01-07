//短信签收记录查询js
var privilege=48;//权限参数
var gridSearchUrl="stock/sysQianshouAction!findRecordList.action";//短信签收记录查询地址
var defaultWidth=80;

var  fields=['SYSQSID','TEL','CONTEXT','CREATETIME','SUCCESS_FLAG','ERRER_INFO'];
	//发送是否成功
	successStore=new Ext.data.SimpleStore({
		 autoLoad:true, //此处设置为自动加载
 			  data:[['','-全部-'],['1','成功'],['0','失败']],
  			 fields:["propertyId","propertyName"]
	});
	var tbar=[
	 	{text:'<b>导出</b>',iconCls:'sort_down',tooltip : '导出数据',
	 		handler:function(){
	 				parent.exportExl(qianshouRecordGrid);
	 		}
	 	},'创建时间:',{ 
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
    	},'-','发送内容:',{
			xtype:'textfield',
			id:'searchContext',
			width : defaultWidth,
			enableKeyEvents:true,
			listeners : {
	 		    keyup:function(textField, e){
                     if(e.getKey() == 13){
                     	searchqianshouRecord();
                     }
                 }
         	}
		},'-','发送号码:',{
			xtype:'textfield',
			id:'searchTel',
			width : defaultWidth,
			enableKeyEvents:true,
			listeners : {
	 		    keyup:function(textField, e){
                     if(e.getKey() == 13){
                     	searchqianshouRecord();
                     }
                 }
         	}
		},'-','发送结果:',{
			xtype : 'combo',
			triggerAction : 'all',
			store : successStore,
			forceSelection : true,
			editable : false,
			mode : "local",//获取本地的值
			displayField : 'propertyName',//显示值，与fields对应
			valueField : 'propertyId',//value值，与fields对应
			id:'searchSuccessFlag',
			width:80,
			enableKeyEvents:true,
			listeners : {
	 		    keyup:function(textField, e){
                     if(e.getKey() == 13){
                     	searchqianshouRecord();
                     }
                 }
         	}
		},'-',{text : '<b>查询</b>',id : 'btn',iconCls : 'btnSearch',
   					handler : searchqianshouRecord
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
    qianshouRecordGrid = new Ext.grid.GridPanel({
        renderTo: Ext.getBody(),
        id:'qianshouRecordCenter',
        height:Ext.lib.Dom.getViewHeight()-1,		
        width:Ext.lib.Dom.getViewWidth()-1,
		autoScroll : true,
		//autoSizeColumns: true,
		frame : false,
		loadMask : true,
	//	stripeRows : true,
        sm:sm,
        cm:new Ext.grid.ColumnModel([rowNum,sm,
        		{header: 'ID', dataIndex: 'SYSQSID',width:defaultWidth,sortable : true},
       			{header: '发送号码', dataIndex: 'TEL',width:defaultWidth*2,sortable : true},
       			{header: '发送内容', dataIndex: 'CONTEXT',width:defaultWidth*2,sortable : true},
       			{header: '发送时间', dataIndex: 'CREATETIME',width:defaultWidth*2,sortable : true},
       			{header: '发送是否成功', dataIndex: 'SUCCESS_FLAG',width:defaultWidth*2,sortable : true,
       				renderer:function(v){
       					return v=='1'?'<font color=green><b>成功</b></font>':'<font color=red>失败</font>';
       				}
       			},
       			{header: '发送说明', dataIndex: 'ERRER_INFO',width:defaultWidth*2,sortable : true}
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
            emptyMsg: "没有记录信息显示"
        })
    });
  });
    
    
	
   function searchqianshouRecord() {
		dateStore.baseParams = {
			privilege:privilege,
			limit : pageSize
		};
		var startCount = Ext.get('startDate').dom.value;
		var endCount = Ext.get('endDate').dom.value;
		var searchContext = Ext.get('searchContext').dom.value;
		var searchTel = Ext.get('searchTel').dom.value;
		var searchSuccessFlag = Ext.getCmp('searchSuccessFlag').getValue();
 	    Ext.apply(dateStore.baseParams, {
 	    	filter_successFlag:searchSuccessFlag,
 	    	filter_LIKES_t_context:searchContext,
 	    	filter_LIKES_t_tel:searchTel,
		 	filter_countCheckItems:'t.createtime',
	 	 	filter_startCount:startCount=='开始时间'?'':startCount,
 	 		filter_endCount:endCount=='结束时间'?'':endCount
   		});
		dataReload();
	}
 function dataReload(){
	dateStore.load({
		params : {
			start : 0,
			privilege:privilege,
			limit : pageSize
		}
	});
 }
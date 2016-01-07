var privilege=32;
var gridSearchUrl="stock/oprOvermemoAction!ralaList.action";
var saveUrl="stock/oprOvermemoAction!save.action";
var delUrl="stock/oprOvermemoAction!delete.action";
var departUrl='sys/departAction!findAll.action';

var  fields=[{name:"id",mapping:'id'},
     		{name:"startDepartId",mapping:'startDepartId'},
     		{name:"startDepartName"},
     		{name:'endDepartId',mapping:'endDepartId'},
     		{name:'startTime',mapping:'startTime'},
     		{name:'endTime',mapping:'endTime'},
     		{name:'unloadStartTime',mapping:'unloadStartTime'},
     		{name:'unloadEndTime',mapping:'unloadEndTime'},
     		{name:'overmemoType',mapping:'overmemoType'},
     		{name:'carId',mapping:'carId'},
     		{name:'status',mapping:'status'},
     		{name:'remark',mapping:'remark'},
     		{name:'createTime',mapping:'createTime'},
     		{name:'createName',mapping:'createName'},
     		{name:'updateTime',mapping:'updateTime'},
     		{name:'updateName',mapping:'updateName'},
     		{name:'orderfields',mapping:'orderfields'},
     		{name:'oprOvermemoDetails',mapping:'oprOvermemoDetails'},
     		{name:'ts',mapping:'ts'}];

     
		
	    departStore = new Ext.data.Store({
	        autoLoad:true,
	        storeId:"departStore",
			proxy:new Ext.data.HttpProxy({url:sysPath+"/"+departUrl}),
			reader:new Ext.data.JsonReader({
	                    root:'result',
	                    totalProperty:'totalCount'
	        },[
        	{name:'departId'},
        	{name:'departName'}
        	])
        	
		});
		departStore.load({
			params : {
				start : 0,
				privilege:privilege,
				limit : pageSize
			}});
	var tbar=[{text:'<b>撤销点到</b>',iconCls:'userAdd',tooltip : '撤销点到',handler:function() {
                	alert('测试');
            } },'-',
	 	{text:'<b>导出</b>',iconCls:'userEdit',tooltip : '导出数据',
	 		handler:function(){
	 				alert('导出');	
	 		}
	 	}
	 		,'-',
	{xtype:'textfield',blankText:'查询数据',id:'searchContent', enableKeyEvents:true,listeners : {
	 		
	 		keyup:function(textField, e){

                     if(e.getKey() == 13){

                     	searchOvermemo();

                     }
	 		}
	 	}},'-',{ 
	 	
    					xtype : "combo",
    					width : 100,
    					triggerAction : 'all',
    					id:'searchSelectBox',
    					model : 'local',
    					hiddenId : 'checkItems',
    					hiddenName : 'checkItems',
    					name : 'checkItemstext',
    					store : [['', '查询全部'],
    							['LIKES_carCode', '品名'],
    							['LIKES_id','主单号'],
    							['LIKES_cartrunkNo','分单号'],
    							['LIKES_engineNo','库存区域']],
    							
    					emptyText : '选择查询方式',
    					editable : false,
    					forceSelection : true,
    					listeners : {
    						
    						'select' : function(combo, record, index) {
    								if(Ext.getCmp("searchContent").getValue().length>0){
    									searchOvermemo();
    								}
    							
    							
    						}
    					}
    					
    				},
	 	'-',{
    		xtype : 'datefield',
    		id : 'startDate',
    		format : 'Y-m-d',
    		emptyText : "选择开始时间",
    		anchor : '95%',
    		width : 100,
    		disabled : true,
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
    		emptyText : "选择结束时间",
    		width : 100,
    		disabled : true,
    		anchor : '95%'
    	},'-',{text : '<b>查询</b>',id : 'btn',iconCls : 'btnSearch',
    					handler : searchOvermemo
    				}
	 	];	


	 	
Ext.onReady(function() {
   	dateStore = new Ext.data.Store({
        storeId:"dateStore",
        proxy: new Ext.data.HttpProxy({url : sysPath+ "/"+gridSearchUrl,
						params:{privilege:privilege,limit : pageSize}}),
        reader: new Ext.data.JsonReader({
            root: 'result', totalProperty: 'totalCount'
        }, fields)
    });
   
   var sm = new Ext.grid.CheckboxSelectionModel({});
	var rowNum = new Ext.grid.RowNumberer({
        header:'序号', width:20
    });
    overmemoGrid = new Ext.grid.GridPanel({
        renderTo: Ext.getBody(),
        id:'overmemoCenter',
        height:Ext.lib.Dom.getViewHeight(),		
        width:Ext.lib.Dom.getViewWidth(),
        viewConfig : {
			columnsText : "显示的列",
			sortAscText : "升序",
			sortDescText : "降序",
			forceFit : true
		},
		autoScroll:false, autoExpandColumn:1,
        frame:true, 
        loadMask:true,
        sm:sm,
        cm:new Ext.grid.ColumnModel([rowNum,sm,
        		{header: '始发部门', dataIndex: 'startDepartId',sortable : true},
       			{header: '到达部门', dataIndex: 'endDepartId',sortable : true},
       			{header: '发车时间', dataIndex: 'startTime',sortable : true},
       			{header: '到车时间', dataIndex: 'endTime',sortable : true},
       			{header: '卸车开始时间', dataIndex: 'unloadStartTime',sortable : true},
       			{header: '卸车结束时间', dataIndex: 'unloadEndTime',sortable : true},
       			{header: '交接单类型', dataIndex: 'overmemoType',sortable : true},
       			{header: '车辆ID', dataIndex: 'carId',sortable : true},
       			{header: '车辆状态', dataIndex: 'status',sortable : true},
       			{header: '排序字段', dataIndex: 'orderfields',sortable : true},
 				{header: '备注', dataIndex: 'remark',sortable : true}
        ]),
        store:dateStore,
      
        tbar: tbar,
        bbar: new Ext.PagingToolbar({
            pageSize: pageSize, 
            privilege:privilege,
            store: dateStore, 
            displayInfo: true,
            displayMsg: '显示第 {0} 条到 {1} 条记录，共 {2} 条', 
            emptyMsg: "没有记录信息显示"
        })
    });
    searchOvermemo();
	overmemoGrid.on('rowdblclick',function(grid,index,e){
	var _records = overmemoGrid.getSelectionModel().getSelections();
			
			if (_records.length ==1) {
				saveCar(_records);
			}
		 	
	});
    });
    
    
	
   function searchOvermemo() {
		dateStore.proxy = new Ext.data.HttpProxy({
					method : 'POST',
					url : sysPath
								+ "/"+gridSearchUrl,
						params:{privilege:privilege,limit : pageSize}
				});
	 
				dateStore.baseParams = {
				checkItems : Ext.get("checkItems").dom.value,
				privilege:privilege,
				start:0,
				itemsValue : Ext.get("searchContent").dom.value
		
		}

		dataReload();
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

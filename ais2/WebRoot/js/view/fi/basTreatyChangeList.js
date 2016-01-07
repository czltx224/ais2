//修改记录查询
var privilege=104;//权限参数
var gridSearchUrl="fi/basTreatyChangeListAction!ralaList.action";//查询地址
var authorityDepartUrl='sys/basRightDepartAction!findDepartName.action';//权限部门查询地址
var gridSearchTableNameUrl="fi/basTreatyChangeListAction!findTableName.action";//记录表名查询地址
var cusServiceUrl='user/userAction!list.action';//用户查询地址
var searchWidth=70;
var searchDepart=bussDepart;
var  fields=[{name:"id",mapping:'id'},
    		{name:'tableName',mapping:'tableName'},
     		{name:'chinaName',mapping:'chinaName'},
     		{name:'doNo',mapping:'doNo'},
     		{name:'updateBefore',mapping:'updateBefore'},
     		{name:'updateFater',mapping:'updateFater'},
     		{name:'updateContent',mapping:'updateContent'},
     		{name:'updateName',mapping:'updateName'},
     		{name:'updateTime',mapping:'updateTime'},
     		{name:'departId',mapping:'departId'},
     		{name:'departName',mapping:'departName'}];
     	
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
        
        //修改人 cusServiceUrl
		cusServiceStore=new Ext.data.Store({
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
		
		//修改表
		updateTableStore=new Ext.data.Store({
	        storeId:"updateTableStore",
			proxy:new Ext.data.HttpProxy({url:sysPath+"/"+gridSearchTableNameUrl}),
		
			reader:new Ext.data.JsonReader({
	                    root:'resultMap',
	                    totalProperty:'totalCount'
	        },[
        	{name:'updateTableName',mapping:'TABLE_NAME'},
        	{name:'updateTableChinaName',mapping:'CHINA_NAME'}
        	])
        	
		});
        
	var tbar=[{text:'<b>导出</b>',iconCls:'sort_down',handler:function() {
                	parent.exportExl(basTreatyChangeListGrid);
        } },'-',
	 	'修改时间：',{
    		xtype : 'datefield',
    		id : 'startDate',
    		format : 'Y-m-d',
    		emptyText : "开始时间",
    		anchor : '95%',
    		value:new Date().add(Date.DAY, -7),
    		//hidden : true,
    		width : 100,
    		//disabled : true,
    		listeners : {
    			'select' : function() {
    			   var start = Ext.getCmp('startDate').getValue()
    			      .format("Y-m-d");
    			   Ext.getCmp('endDate').setMinValue(start);
    		     }
    		}
    	}, '&nbsp;&nbsp;至：', {
    		xtype : 'datefield',
    		id : 'endDate',
    		format : 'Y-m-d',
    		emptyText : "结束时间",
    		value:new Date(),
    		//hidden : true,
    		width : 100,
    		//disabled : true,
    		anchor : '95%',
    		enableKeyEvents:true,listeners : {
	 		keyup:function(textField, e){
                 if(e.getKey() == 13){
                 	searchbasTreatyChangeList();
                 }
	 		}
    	}
    	},'-','操作表主键：',
	{xtype:'textfield',blankText:'操作表主键',id:'searchDoNo', 
			enableKeyEvents:true,listeners : {
	 		keyup:function(textField, e){
                 if(e.getKey() == 13){
                 	searchbasTreatyChangeList();
                 }
	 		}
	 	}}
	 	];	

var queryTbar=new Ext.Toolbar([
		'修改表名：',
			{xtype : 'combo',
				width : searchWidth+20,
				triggerAction : 'all',
				id:'searchupdateTable',
				store : updateTableStore,
				// typeAhead : true,
				pageSize : comboSize,
				forceSelection : true,
				selectOnFocus : true,
				resizable : true,
				minChars : 0,
				queryParam : 'filter_LIKES_CHINA_NAME',
				valueField : 'updateTableName',//value值，与fields对应
				displayField : 'updateTableChinaName',//显示值，与fields对应
				editable : true,
				name : 'updateTable'
				
			},'-','部门名称：',
			{xtype : 'combo',
				width : searchWidth+20,
				triggerAction : 'all',
				id:'searchAuthorityDepart',
				pageSize:comboSize,
				store : authorityDepartStore,
				resizable:true,
				forceSelection : true,
				valueField : 'departId',//value值，与fields对应
				displayField : 'departName',//显示值，与fields对应
				editable : false,
				name : 'departId'
				
			},'-','修改人姓名:',{xtype : "combo",
				width : searchWidth+20,
				// typeAhead : true,
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
			    enableKeyEvents:true,listeners : {
					 	'afterRender':{fn:
					 	function(combo){
					        　　combo.setValue(userId);
					        	combo.setRawValue(userName);
					 	}
					 	
 	
 	
	 		}
	 	}},'-','修改内容:',{
	 		xtype:'textfield',id:'searchUpdateContent',width : searchWidth+20,
	 		 enableKeyEvents:true,listeners : {
					 	keyup:function(textField, e){
                 if(e.getKey() == 13){
                 	searchbasTreatyChangeList();
                 }
	 		}
	 	}},{text : '<b>查询</b>',id : 'btn',iconCls : 'btnSearch',
    					handler : searchbasTreatyChangeList
    		}
	 	]);	
	 	
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
    basTreatyChangeListGrid = new Ext.grid.GridPanel({
        renderTo: Ext.getBody(),
        id:'basTreatyChangeListCenter',
        height:Ext.lib.Dom.getViewHeight(),		
        width:Ext.lib.Dom.getViewWidth(),
		autoScroll:true, 
        frame:true, 
        loadMask:true,
        sm:sm,
        cm:new Ext.grid.ColumnModel([rowNum,sm,
        		{header: '序号ID', dataIndex: 'id',sortable:true,width:60,hidden:true},
       			{header: '操作表名', dataIndex: 'tableName',width:150,sortable : true},
        		{header: '中文表名', dataIndex: 'chinaName',width:100,sortable : true},
        		{header: '操作表主键', dataIndex: 'doNo',width:80,sortable : true},
 				{header: '修改前的值', dataIndex: 'updateBefore',width:300,sortable : true},
 				{header: '修改后的值', dataIndex: 'updateFater',width:300,sortable : true},
 				{header: '修改的内容', dataIndex: 'updateContent',width:100,sortable : true},
 				{header: '修改人', dataIndex: 'updateName',width:80,sortable : true},
 				{header: '修改时间', dataIndex: 'updateTime',width:80,sortable : true},
 				{header: '修改人部门名称', dataIndex: 'departName',width:100,sortable : true}
        ]),
        store:dateStore,
        tbar: tbar,
        listeners: {
                    render: function(){
                        queryTbar.render(this.tbar);
                    }
                },
        bbar: new Ext.PagingToolbar({
            pageSize: pageSize, 
            privilege:privilege,
            store: dateStore, 
            displayInfo: true,
            displayMsg: '显示第 {0} 条到 {1} 条记录，共 {2} 条', 
            emptyMsg: "没有记录信息显示"
        })
    });
		
    });
    
    
	
   function searchbasTreatyChangeList() {
		dateStore.proxy = new Ext.data.HttpProxy({
					method : 'POST',
					url : sysPath
								+ "/"+gridSearchUrl,
						params:{privilege:privilege,limit : pageSize}
				});
			var searchAuthorityDepart=Ext.getCmp('searchAuthorityDepart').getValue();
			var searchupdateTable=Ext.getCmp('searchupdateTable').getValue();
			var searchDoNo=Ext.getCmp('searchDoNo').getValue();
			var searchcusService = Ext.getCmp('searchcusService').getRawValue();
			var searchUpdateContent=Ext.getCmp('searchUpdateContent').getValue();
			if(searchAuthorityDepart.length>0){
				searchDepart=searchAuthorityDepart;
			}
	
			Ext.apply(dateStore.baseParams, {
				filter_EQL_departId:searchDepart,
				filter_EQS_tableName:searchupdateTable,
				filter_EQL_doNo:searchDoNo,
				filter_EQS_updateName:searchcusService,
				filter_LIKES_updateContent:searchUpdateContent,
				privilege:privilege,
				limit : pageSize,
				checkItems : '',
				itemsValue : '',
				filter_GED_updateTime:(!Ext.getCmp("startDate").hidden?(Ext.get("startDate").dom.value=="开始时间"?"":Ext.get("startDate").dom.value):""),
				filter_LTD_updateTime : (!Ext.getCmp("endDate").hidden?(Ext.get("endDate").dom.value=="结束时间"?"":Ext.get("endDate").dom.value):"")
			});
    	
		dataReload();
	}

 function dataReload(){
			dateStore.reload({
			params : {
				start : 0,
				filter_EQL_departId:searchDepart,
				privilege:privilege,
				limit : pageSize
			}
		});
 }
//货物日志查看
var privilege=78;//权限参数
var gridSearchUrl="stock/oprHistoryAction!list.action";//货物日志查询地址
var departUrl='sys/departAction!findAll.action';//部门查询地址
var searchNodeUrl='stock/oprNodeAction!list.action';//节点查询地址
var cusServiceUrl='user/userAction!list.action';//用户查询地址
var searchWidth=80;
var defaultWidth=80;

var  fields=['id','oprName','oprNode','oprComment','oprTime','oprMan','oprDepart','oprType','dno'];
		
		//内部交接部门(业务部门)
		departStore = new Ext.data.Store({
        storeId:"departStore",
	        proxy: new Ext.data.HttpProxy({url:sysPath+'/'+departUrl,params:{filter_EQL_isBussinessDepa:1}}),
	        reader: new Ext.data.JsonReader({
	            root: 'result', totalProperty: 'totalCount'
	        }, [
	            {name:'departId'},
	            {name: 'departName'}
	        ])
        });
        
        nodeStore = new Ext.data.Store({
       		storeId:"nodeStore",
	        proxy: new Ext.data.HttpProxy({url:sysPath+'/'+searchNodeUrl}),
	        reader: new Ext.data.JsonReader({
	            root: 'result', totalProperty: 'totalCount'
	        }, [
	            {name:'id'},
	            {name: 'nodeName'}
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
	var tbar=[{text:'<b>导出</b>',iconCls:'table',tooltip : '导出',handler:function() {
					
					parent.exportExl(historyLogGrid);

            } }
    			,'-','业务部门:',{xtype : "combo",
					width : searchWidth+20,
					triggerAction : 'all',
					typeAhead : true,
					queryParam : 'filter_LIKES_departName',
					pageSize : comboSize,
					minChars : 0,
					editable:true,
					forceSelection : true,
					selectOnFocus : true,
					resizable : true,
					store :departStore,
					mode : "remote",// 从本地载值
					valueField : 'departId',// value值，与fields对应
					displayField : 'departName',// 显示值，与fields对应
				    name : 'departName',
				    id:'searchdepartName', 
				    enableKeyEvents:true,listeners : {
		 		   		 keyup:function(textField, e){
		                     if(e.getKey() == 13){
		                     	searchhistoryLog();
		                     }
		 			}
	 		}},'-','操作节点:',{xtype : "combo",
					width : searchWidth+20,
					triggerAction : 'all',
					typeAhead : true,
					queryParam : 'filter_LIKES_nodeName',
					pageSize : comboSize,
					minChars : 0,
					editable:true,
					forceSelection : true,
					selectOnFocus : true,
					resizable : true,
					store :nodeStore,
					mode : "remote",// 从本地载值
					valueField : 'id',// value值，与fields对应
					displayField : 'nodeName',// 显示值，与fields对应
				    id:'searchNode', 
				    enableKeyEvents:true,listeners : {
		 		   		 keyup:function(textField, e){
		                     if(e.getKey() == 13){
		                     	searchhistoryLog();
		                     }
		 			}
	 		}},'-','操作人:',{xtype : "combo",
				width : searchWidth+20,
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
			    id:'searchcusService', 
			    enableKeyEvents:true,
			    listeners : {
			 		'afterRender':
				 	function(combo){
				        　　combo.setValue(userId);
				          combo.setRawValue(userName);
				 	},
				 	keyup:function(textField, e){
	                     if(e.getKey() == 13){
	                     	searchhistoryLog();
	                     }
				 	}
	 		}}
	 	];
	 	
	 	var queryTbar=new Ext.Toolbar([
    		
    		'操作时间:',{
    		xtype : 'datefield',
    		id : 'startDate',
    		format : 'Y-m-d',
    		emptyText : "开始时间",
    		anchor : '95%',
    		value:new Date().add(Date.DAY, -7),
    		width : 100,
    		enableKeyEvents:true,
    		listeners : {
    		      keyup:function(textField, e){
                     if(e.getKey() == 13){
                     	searchhistoryLog();
                     }
    		      }
    		}
    	}, '&nbsp;至&nbsp;', {
    		xtype : 'datefield',
    		id : 'endDate',
    		format : 'Y-m-d',
    		emptyText : "结束时间",
    		value:new Date(),
    		width : 100,
    		anchor : '95%',
    		enableKeyEvents:true,
    		listeners : {
    			 keyup:function(textField, e){
                     if(e.getKey() == 13){
                     	searchhistoryLog();
                     }
    			 }
    		}
    	},'-',
	 		{xtype:'textfield',id:'searchContent',width:searchWidth, enableKeyEvents:true,listeners : {
	 			keyup:function(textField, e){
                     if(e.getKey() == 13){
                     	searchhistoryLog();
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
					store : [['','查询全部'],
							['EQL_dno', '配送单号'],
							['LIKES_oprComment', '操作内容']],
							//['EQL_oprType','节点类型']],
							
					emptyText : '选择查询方式',
					editable : false,
					forceSelection : true,
					listeners : {
						'select' : function(combo, record, index) {
							if(Ext.getCmp("searchContent").getValue().length>0){
								searchhistoryLog();
							}
						}
					}
					
				},
	 		{text : '<b>查询</b>',id : 'btn',iconCls : 'btnSearch',
    					handler : searchhistoryLog
    		}
	 	]);	

	 	
Ext.onReady(function() {
   	dateStore = new Ext.data.Store({
        storeId:"dateStore",
        proxy: new Ext.data.HttpProxy({url : sysPath+ "/"+gridSearchUrl}),
        baseParams:{privilege:privilege,limit : pageSize},
        sortInfo :{field: "oprTime", direction: "ASC"},
        reader: new Ext.data.JsonReader({
            root: 'result', totalProperty: 'totalCount'
        }, fields)
    });
   var rowNum = new Ext.grid.RowNumberer({
        header:'序号', width:20
    });
    var sm=new Ext.grid.CheckboxSelectionModel();
    historyLogGrid = new Ext.grid.GridPanel({
        renderTo: Ext.getBody(),
        id:'historyLogCenter',
        height:Ext.lib.Dom.getViewHeight()-2,		
        width:Ext.lib.Dom.getViewWidth(),
        viewConfig : {
			columnsText : "显示的列",
			sortAscText : "升序",
			sortDescText : "降序",
			forceFit : true
		},
		autoScroll : true,
		autoSizeColumns: true,
		sm: sm,
		frame : false,
		loadMask : true,
		stripeRows : true,
        cm : new Ext.grid.ColumnModel([rowNum,sm,
			{header : '日志ID',dataIndex : 'id',sortable : true,hidden:true},
			{header : '配送单号',dataIndex : 'dno',sortable : true},
			{header : '操作节点名称',dataIndex : 'oprName',sortable : true},
			{header : '操作节点',dataIndex : 'oprNode',sortable : true,hidden:true},
			{header : '操作内容',dataIndex : 'oprComment',sortable : true},
			{header : '操作时间',dataIndex : 'oprTime',sortable : true},
			{header : '操作者',dataIndex : 'oprMan',sortable : true},
			{header : '操作部门',dataIndex : 'oprDepart',sortable : true},
			{header : '节点类型',dataIndex : 'oprType',sortable : true,hidden:true}
		]),
        store:dateStore,
        tbar: tbar,
        listeners: {
                    render: function(){
                        queryTbar.render(this.tbar);
                    }
                },
        bbar: new Ext.PagingToolbar({
            store: dateStore, 
            afterPageText: '共{0}页',
		    beforePageText: '当前页',
            displayInfo: true,
            displayMsg: '显示第 {0} 条到 {1} 条记录，共 {2} 条', 
            emptyMsg: "没有记录信息显示"
        })
    });
  });
    
    
	//查询方法
   function searchhistoryLog() {
   	dateStore.proxy = new Ext.data.HttpProxy({
			method : 'POST',
			url : sysPath+ "/"+gridSearchUrl,
				params:{privilege:privilege,limit : pageSize}
		});
			var searchcusService=Ext.getCmp('searchcusService').getRawValue();
			var searchNode=Ext.getCmp('searchNode').getValue();
			//var searchDno=Ext.getCmp('searchDno').getValue();
			var searchdepartName=Ext.getCmp('searchdepartName').getRawValue();

			 dateStore.on('beforeload', function(store,options)
			 {
			 	 Ext.apply(options.params, {
			 	 	filter_EQS_oprDepart:searchdepartName,
					filter_EQL_oprNode:searchNode,
					filter_EQS_oprMan:searchcusService,
					filter_GED_oprTime:(!Ext.getCmp("startDate").hidden?(Ext.get("startDate").dom.value=="开始时间"?"":Ext.get("startDate").dom.value):""),
    			 	filter_LED_oprTime:(!Ext.getCmp("endDate").hidden?(Ext.get("endDate").dom.value=="结束时间"?"":Ext.get("endDate").dom.value):""),
	 			 	checkItems : Ext.get("checkItems").dom.value,
					itemsValue : Ext.get("searchContent").dom.value
    			});
			 });
			 
			 dataReload();
	}
//重新加载方法
 function dataReload(){
			dateStore.reload({
			params : {
				privilege:privilege,
				limit : pageSize
			}
		});
		
 }

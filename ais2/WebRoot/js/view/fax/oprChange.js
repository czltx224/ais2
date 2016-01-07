Ext.onReady(function() {
	var flowDetailStore=new Ext.data.Store({
    		baseParams:{limit:pageSize,changeMainId:53},
                proxy: new Ext.data.HttpProxy({url:sysPath+'/fax/oprChangeDetailAction!findDetail.action'}),
                reader:new Ext.data.JsonReader({
            root: 'resultMap', totalProperty: 'totalCount'
        }, [
        	{name:'changeFieldZh',mapping:'CHANGE_FIELD_ZH'},
        	{name:'changePre',mapping:'CHANGE_PRE'},
        	{name:'changePost',mapping:'CHANGE_POST'}
        	])
    	});
	flowDetailStore.load();
    var grid = new Ext.grid.GridPanel({
    	storeId:'flowDetailStore',
        store: flowDetailStore,
        columns: [
            {header: '更改项', width: 160, sortable: true, dataIndex: 'changeFieldZh'},
            {header: '更改前值', width: 75, sortable: true, dataIndex: 'changePre'},
            {header: '更改后值', width: 75, sortable: true,  dataIndex: 'changePost'}
        ],
        stripeRows: true,
        height: 160,
        autoWidth: true,
        stateful: true
    });
        var myData2= [
        ['张三','新建','2011-01-01'],
        ['张三','审批','2011-01-01'],
        ['张三','审批','2011-01-01'],
        ['张三','审批','2011-01-01'],
        ['张三','审批','2011-01-01'],
        ['张三','审批','2011-01-01'],
        ['张三','审批','2011-01-01'],
        ['张三','审批','2011-01-01'],
        ['张三','审批','2011-01-01']
        
        
    ];


    var store2 = new Ext.data.ArrayStore({
        fields: [
           {name: 'company'},
           {name: 'price'},
           {name: 'change'}
        ]
    });

    store2.loadData(myData2);

    var grid2 = new Ext.grid.GridPanel({
        store: store2,
        columns: [
            {id:'company',header: '更改项', width: 160, sortable: true, dataIndex: 'company'},
            {header: '更改前值', width: 75, sortable: true, dataIndex: 'price'},
            {header: '更改后值', width: 75, sortable: true,  dataIndex: 'change'}
        ],
        stripeRows: true,
        height: 160,
        autoWidth: true,
        stateful: true,
        stateId: 'store2' 
    });
	new Ext.Panel({
	  tbar: [
            {
                text:'<B>审核</B>', id:'addbtn',tooltip:'审核流程', iconCls: 'userAdd',handler:function() {
                	//addMenu();
            } },
            '-',
            {
                text:'<B>知会</B>',disabled:true, tooltip:'知会流程', iconCls: 'userEdit', handler: function() {
            } } ,
             {
                text:'<B>否决</B>',disabled:true, tooltip:'否决流程', iconCls: 'userEdit', handler: function() {
            } }],
		renderTo:'showView',
		frame : true,
		layout: 'border',
		height:Ext.lib.Dom.getViewHeight(), 
        width:Ext.lib.Dom.getViewWidth(),
		items:[
						{
	                     region:'north',
	                     title:'表单主信息',
	                     html:'表单主信息',
	                     height:100,
						 border:false,
	                     layout: 'fit',
	                     items:[
	                     	new Ext.form.FormPanel({
	                     		frame : true,
								border : false,
								bodyStyle : 'padding:5px 5px 5px',
								labelAlign : "left",
								labelWidth : 70,
								width : 400,
								items:[{
									layout : 'column',
									items : [	{		
													columnWidth : .3,
													layout : 'form',
													items : [{xtype:'textfield',fieldLabel:'流程名称',disabled:true},
						                     				{xtype:'textfield',fieldLabel:'创建人', name:'departId'}
					                            			]
					                            	},{
					                            	columnWidth : .3,
													layout : 'form',
													items : [{xtype:'textfield',fieldLabel:'创建时间',name:'ts'},
					                            			{xtype:'textfield',fieldLabel:'创建部门',disabled:true}
					                            			]
					                            	},{
					                            	
						                            	columnWidth : .3,
														layout : 'form',
														items : [{xtype:'textarea',fieldLabel:'备注',name:'ts',width:200}
						                            	]
					                            	}
			                            ]
		                            }
		                            ]
                            })
	                     ]
	                    },
						
				        {
				        region: 'south',
				        title: '流程处理',
				        collapsible: false,
						id:'flow',
				        split: false,
				        height:200,
				        width:Ext.lib.Dom.getViewWidth()/2-10,
				        collapsed:false,
				        items:grid2
				   	   },{
					   	 region:'center', 
					   	 title: '更改数据项',
	                     id: 'centerPanel', 
	                     layout: 'fit',
	                   //  width:Ext.lib.Dom.getViewWidth()/2-10,
	                     autoScroll:false,
	                     items:grid
	                     }
		
		]
		}
	);
});
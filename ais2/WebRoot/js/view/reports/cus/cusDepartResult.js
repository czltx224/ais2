var rightDepartStore,mainStore,continentGroupRow,togroup,menuGrid,mainFiled,tbar,bbar;
Ext.onReady(function(){
	mainFiled=[];
	mainStore = new Ext.data.Store({
		 storeId:"mainStore",                        
         proxy: new Ext.data.HttpProxy({
          	url:sysPath+"/fax/oprFaxInAction!searchCusResult.action",
          	method:'post'
         }),
         baseParams:{limit:pageSize},
         reader: new Ext.data.JsonReader({
            root: 'resultMap', totalProperty: 'totalCount'
         }, 
         mainFiled)  
	});
	//权限部门
	rightDepartStore = new Ext.data.Store({ 
            storeId:"rightDepartStore",                        
            proxy: new Ext.data.HttpProxy({url:sysPath+"/sys/basRightDepartAction!findDepartName.action",method:'post'}),
            baseParams:{
            	filter_EQL_userId:userId
            },
            reader: new Ext.data.JsonReader({
            root: 'resultMap', totalProperty: 'totalCount'
           }, [{name:'departName', mapping:'DEPARTNAME'},
               {name:'departId', mapping:'RIGHTDEPARTID'}             
              ])    
    });
    var sm = new Ext.grid.CheckboxSelectionModel({});
    var rowNum = new Ext.grid.RowNumberer({
        header:'序号', width:35, sortable:true
    });
    continentGroupRow = [
    	{colspan: 2},
    	{header: '空港配送广州业务一处',align: 'center',colspan: 3},
        {header: '空港配送广州业务二处',align: 'center',colspan: 3}
    ];
	
    togroup = new Ext.ux.grid.ColumnHeaderGroup({
        rows: [continentGroupRow]
    });
    tbar = new Ext.Toolbar({
    	items:[{text:'<b>导出</b>',iconCls:'sort_down',handler:function() {
                	parent.exportExl(menuGrid);
        	} },'-','日期类型:',{
        		xtype:'combo',
        		name:'dateType',
        		id:'dateType',
        		value:'日',
        		forceSelection : true,
				triggerAction : 'all',
				model : 'local',
				editable:false,
				width:60,
        		store:[
        			['日','日'],
        			['月','月']
        		],
        		listeners:{
        			'select':function(combo){
        				if(combo.getValue()=='日'){
        					Ext.getCmp('startone').setValue(new Date().add(Date.DAY,-7));
        				}else if(combo.getValue()=='月'){
        					Ext.getCmp('startone').setValue(new Date().add(Date.MONTH,-1));
        				}
        			}
        		}
        	},
            '统计日期:',
            {
				id : 'startone',
				xtype:'datefield',
				format : 'Y-m-d',
				allowBlank : false,
				emptyText : "开始时间",
				value:new Date().add(Date.DAY, -7)
			},{
				id : 'endone',
				xtype:'datefield',
				format : 'Y-m-d',
				allowBlank : false,
				emptyText : "结束时间",
				value:new Date()
			},'部门:',
			{
				xtype:'combo',
				name:'departId',
				id:'comboRightDepart',
				store:rightDepartStore,
				triggerAction :'all',
				model : 'local',
				resizable : true,
				width:120,
				pageSize:pageSize,
				minChars : 0,
				listWidth:200,
				valueField :'departId',
				displayField :'departName'
			},
            '-',
            {
            	text:'<B>搜索</B>',
            	id:'btn', 
            	iconCls: 'btnSearch',
            	hidden:false,
            	handler : function(){
            		if( Ext.getCmp('startone').isValid()&&Ext.getCmp('endone').isValid()){
						searchCusResult();
					}
            	}
            }
        ]
    });
    bbar = new Ext.PagingToolbar({
            pageSize: pageSize,
            store: mainStore, 
            displayInfo: true,
            displayMsg: '显示第 {0} 条到 {1} 条记录，共 {2} 条', emptyMsg: "没有记录信息显示"
     });
    menuGrid = new Ext.grid.GridPanel({
        renderTo:'showView',
        height:Ext.lib.Dom.getViewHeight()-10, 
        width:Ext.lib.Dom.getViewWidth()-1,
        frame : false,
    	loadMask : true,
    	stripeRows : true,
    	viewConfig:{
    		scrollOffset: 0,
    		autoScroll : true
    	},
        cm:new Ext.grid.ColumnModel([rowNum,
        	{header:"统计时间",dataIndex:"dutyName",width:80},
        	{header:"票数",dataIndex:"dutyName",width:80},
            {header:"货量",dataIndex:"dayTicket",width:80},
            {header:"营业额",dataIndex:"dayWeight",width:80},
            {header:'票数',dataIndex:'dayIncome',width:80},
            {header:'货量',dataIndex:'dayIncome',width:80},
            {header:'营业额',dataIndex:'dayIncome',width:80}
        ]),
        plugins: togroup,
        store:mainStore,
        tbar: tbar,
        bbar:bbar
    });
});
function searchCusResult(){
	var dateType = Ext.getCmp('dateType').getValue();
	var startDate = Ext.get('startone').dom.value;
	var endDate = Ext.get('endone').dom.value;
	var departId = Ext.getCmp('comboRightDepart').getValue();
	
	var cmItems = []; 
	var afields=[];
	var continentGroupRow1=[];
	
	var sm = new Ext.grid.CheckboxSelectionModel({});
    var rowNum = new Ext.grid.RowNumberer({
        header:'序号', width:35, sortable:true
    });
    
    cmItems.push(rowNum);
    continentGroupRow1.push({colspan: 2});
    afields.push('DAY_TIME');
	cmItems.push({header:'统计时间',dataIndex:'DAY_TIME',sortable:true});
    
	Ext.Ajax.request({
		url:sysPath+'/cus/marketTargetAction!searchCusDepart.action',
		params:{
			departId:departId
		},
		success:function(resp){
			var respText = Ext.util.JSON.decode(resp.responseText);
			for(var i=0;i<respText.length;i++){
				continentGroupRow1.push({header: respText[i].CUS_DEPART_NAME,align:'center' ,colspan: 3});
				
				afields.push('TICKET'+i);
				cmItems.push({header:'票数',dataIndex:'TICKET'+i,sortable:true});
				
				afields.push('WEIGHT'+i);
				cmItems.push({header:'货量',dataIndex:'WEIGHT'+i,sortable:true});
				
				afields.push('INCOME'+i);
				cmItems.push({header:'营业额',dataIndex:'INCOME'+i,sortable:true});
			}
			afields.push('SUM_TICKET');
			cmItems.push({header:'总票数',dataIndex:'SUM_TICKET',sortable:true});
			afields.push('SUM_WEIGHT');
			cmItems.push({header:'总重量',dataIndex:'SUM_WEIGHT',sortable:true});
			afields.push('SUM_INCOME');
			cmItems.push({header:'总营业额',dataIndex:'SUM_INCOME',sortable:true});
			
			
			continentGroupRow1.push({header: '总额',align: 'center',colspan: 3});
			
    		mainField = afields;
			mainStore=new Ext.data.Store({
				storeId:"mainStore",
				baseParams:{limit:pageSize},
				proxy: new Ext.data.HttpProxy({
		          	url:sysPath+"/cus/cusDepartResultAction!searchCusResult.action",
		          	method:'post'
		         }),
				reader:new Ext.data.JsonReader({
					root:'resultMap',
	            	totalProperty:'totalCount'
		        },afields)
			});
			
			var div =document.getElementById('showView');
			while(div.hasChildNodes()) //当div下还存在子节点时 循环继续
			{	
				div.removeChild(div.firstChild);
			}
			togroup = new Ext.ux.grid.ColumnHeaderGroup({
		        rows: [continentGroupRow1]
		    });
		    
		    menuGrid = new Ext.grid.GridPanel({
		        renderTo:'showView',
		        height:Ext.lib.Dom.getViewHeight()-10, 
		        width:Ext.lib.Dom.getViewWidth()-1,
		        frame : false,
		    	loadMask : true,
		    	stripeRows : true,
		    	viewConfig:{
		    		scrollOffset: 0,
		    		autoScroll : true
		    	},
		        cm:new Ext.grid.ColumnModel(cmItems),
		        plugins: togroup,
		        store:mainStore,
		        tbar: [{text:'<b>导出</b>',iconCls:'sort_down',handler:function() {
			                	parent.exportExl(menuGrid);
			        	} },'-','日期类型:',{
			        		xtype:'combo',
			        		name:'dateType',
			        		id:'dateType',
			        		value:'日',
			        		forceSelection : true,
							triggerAction : 'all',
							model : 'local',
							editable:false,
							width:60,
			        		store:[
			        			['日','日'],
			        			['月','月']
			        		],
			        		listeners:{
			        			'select':function(combo){
			        				if(combo.getValue()=='日'){
			        					Ext.getCmp('startone').setValue(new Date().add(Date.DAY,-7));
			        				}else if(combo.getValue()=='月'){
			        					Ext.getCmp('startone').setValue(new Date().add(Date.MONTH,-1));
			        				}
			        			}
			        		}
			        	},
			            '统计日期:',
			            {
							id : 'startone',
							xtype:'datefield',
							format : 'Y-m-d',
							allowBlank : false,
							emptyText : "开始时间",
							value:new Date().add(Date.DAY, -7)
						},{
							id : 'endone',
							xtype:'datefield',
							format : 'Y-m-d',
							allowBlank : false,
							emptyText : "结束时间",
							value:new Date()
						},'部门:',
						{
							xtype:'combo',
							name:'departId',
							id:'comboRightDepart',
							store:rightDepartStore,
							triggerAction :'all',
							model : 'local',
							resizable : true,
							width:120,
							pageSize:pageSize,
							minChars : 0,
							listWidth:200,
							valueField :'departId',
							displayField :'departName'
						},
			            '-',
			            {
			            	text:'<B>搜索</B>',
			            	id:'btn', 
			            	iconCls: 'btnSearch',
			            	hidden:false,
			            	handler : function(){
			            		if( Ext.getCmp('startone').isValid()&&Ext.getCmp('endone').isValid()){
									searchCusResult();
								}
			            	}
			            }
			        ],
		        bbar:new Ext.PagingToolbar({
			            pageSize: pageSize,
			            store: mainStore, 
			            displayInfo: true,
			            displayMsg: '显示第 {0} 条到 {1} 条记录，共 {2} 条', emptyMsg: "没有记录信息显示"
			     })
		    });
			Ext.apply(mainStore.baseParams,{
				dateType:dateType,
				startDate:startDate,
				endDate:endDate,
				limit:pageSize,
				start:0,
				rightDepart:departId
			});
			mainStore.load();
		}
	});
}
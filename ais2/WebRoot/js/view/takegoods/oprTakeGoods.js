//机场调度管理JS
var popupWin;
var privilege=56;
var custprop='航空公司';
var carGuardStore;
var summary = new Ext.ux.grid.GridSummary();
Ext.onReady(function() {
	carGuardStore=new Ext.data.Store({
		storeId:"carGuardStore",
		proxy:new Ext.data.HttpProxy({url:sysPath+"/takegoods/oprTakeAction!findCarGuard.action"}),
		baseParams:{limit:pageSize},
		reader:new Ext.data.JsonReader({
                    root:'resultMap',
                    totalProperty:'totalCount'
        },[
        	{name:'id',mapping:'ID'},
        	//{name:'takeAddr',mapping:'TAKE_ADDR'},
        	{name:'disCarNo',mapping:'DIS_CAR_NO'},
        	{name:'carStatus',mapping:'CAR_STATUS'},
        	{name:'planCarTime',mapping:'PLAN_CAR_TIME'},
        	{name:'disCarTon',mapping:'DIS_CAR_TON'},
        	{name:'disShuntWeight',mapping:'DIS_SHUNT_WEIGHT'},
        	{name:'disShuntPiece',mapping:'DIS_SHUNT_PIECE'},
        	{name:'createName',mapping:'CREATE_NAME'},
        	{name:'createTime',mapping:'CREATE_TIME'},
        	{name:'updateName',mapping:'UPDATE_NAME'},
        	{name:'updateTime',mapping:'UPDATE_TIME'}
        	])
	});
	
	var oprFaxStore=new Ext.data.Store({
		storeId:"oprFaxStore",
		proxy:new Ext.data.HttpProxy({url:sysPath+"/takegoods/oprTakeAction!findTakeGoods.action"}),
		baseParams:{limit:pageSize},
		reader:new Ext.data.JsonReader({
                    root:'resultMap',
                    totalProperty:'totalCount'
        },[
        	{name:'sonderzug',mapping:'SONDERZUG'},
        	{name:'flightDate',mapping:'FLIGHT_DATE'},
        	{name:'ticketNum',mapping:'TICKETNUM'},
        	{name:'cqWeight',mapping:'CQWEIGHT'},
        	{name:'standardStarttime',mapping:'STANDARD_STARTTIME'},
        	{name:'standardEndtime',mapping:'STANDARD_ENDTIME'},
        	{name:'piece',mapping:'PIECE'},
        	{name:'flightNo',mapping:'FLIGHT_NO'},
        	{name:'cusName',mapping:'CUS_NAME'}
        	])
	});
	//航班号Store
	var flightStore=new Ext.data.Store({
		storeId:"flightStore",
		proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/basFlightAction!list.action"}),
		baseParams:{limit:pageSize,privilege:62},
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
        	{name:'flightId',mapping:'id'},
        	{name:'flightNumber'},
        	{name:'customerName',mapping:'cusName'}
        	])
	});
	//提货公司Store
	var customerStore=new Ext.data.Store({
		storeId:"customerStore",
		baseParams:{limit:pageSize,privilege:61,filter_EQS_custprop:'提货公司'},
		proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/customerAction!list.action"}),
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
        	{name:'cusId',mapping:'id'},
        	{name:'cusName',mapping:'cusName'}
        	])
	});
	var departStore=new Ext.data.Store({
		storeId:"departStore",
		proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/departAction!findAll.action"}),
		baseParams:{limit:pageSize,privilege:53,filter_EQL_isBussinessDepa:1},
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
        	{name:'departId',mapping:'departId'},
        	{name:'departName',mapping:'departName'}
        	])
	});
    //start
    var sm = new Ext.grid.CheckboxSelectionModel();
    var rowNum = new Ext.grid.RowNumberer({
        header:'序号', width:35, sortable:true
    });
    var tb = new Ext.Toolbar({
    	width : Ext.lib.Dom.getViewWidth(),
    	items:[{text:'<b>导出</b>',iconCls:'sort_down',handler:function() {
                	parent.exportExl(menuGrid);
        		} },'-',{
                text:'<B>调车申请</B>', id:'addbtn',tooltip:'调车申请',disabled:true, iconCls: 'userAdd',handler:function() {
                	Ext.Msg.confirm(alertTitle, "确定要调车申请吗？", function(btnYes) {
                		if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
                			var records=menuGrid.getSelectionModel().getSelections();
                			shuntApply(records);
                		}
                	});
            } },'-','班次号:',
            {
            	xtype:'combo',
				triggerAction : 'all',
				model : 'local',
				id:'flightcombo',
				width:80,
				minChars : 0,
				hiddenName:'flightNo',
				queryParam : 'filter_LIKES_flightNumber',
				pageSize:pageSize,
				valueField : 'flightNumber',
				displayField : 'flightNumber',
				store:flightStore,
				emptyText : '选择类型',
				forceSelection : true
            },
            '-','&nbsp;航班状态:',
            {
            	xtype : 'combo',
            	triggerAction : 'all',
    			model : 'local',
    			hiddenId : 'checkItems',
    			hiddenName : 'checkItems',
    			name : 'checkItemstext',
            	store: [
            			['', '查询全部'], 
    					['0', '航班起飞'],
    					['1','航班落地']
    				   ],
    			width : 80
            },
            '-','提货处:',{
            	xtype:'combo',
            	triggerAction : 'all',
				model : 'local',
				id:'custcombo',
				width:80,
				minChars : 0,
				hiddenName:'cusId',
				queryParam : 'filter_EQS_cusName',
				pageSize:pageSize,
				valueField : 'cusId',
				displayField : 'cusName',
				store:customerStore,
				emptyText : '选择类型',
				forceSelection : true
            },'-','航班落地时段:',{
            	xtype:'timefield',
            	increment:60,
            	format:'G:i',
            	width:80,
            	name:'startTime',
            	id:'startTime'
            },'-',{
            	xtype:'timefield',
            	format:'G:i',
            	width:80,
            	increment:60,
            	name:'endTime',
            	id:'endTime'
            }
        ]
    });
    var queryTbar = new Ext.Toolbar({
		items : ['业务部门:', {
					xtype : 'combo',
					typeAhead : true,
					forceSelection : true,
					id : 'departCombo',
					minChars : 0,
					queryParam : 'filter_LIKES_departName',
					hiddenName : 'departCombo',
					valueField : 'departId',
					displayField : 'departName',
					triggerAction : 'all',
					store:departStore,
					width : 120,
					listeners:{
						'render':function(combo){
							combo.setValue(bussDepart);
							combo.setRawValue(bussDepartName);
						}
					}
				},'-','专车',{
				
					xtype:'combo',
					hiddenName:'sonderzug',
					id:'sonderzugcombo',
					model : 'local',
					editable:false,
					forceSelection : true,
					triggerAction : 'all',
					value:'',
	    			width : 80,
					store:[
						['','全部'],
						['1','是'],
						['0','否']
					]
				},
	            {
	            	text:'<B>搜索</B>',
	            	id:'btn', 
	            	iconCls: 'btnSearch',
	            	hidden:false,
	            	handler : searchTakeGoods
	            }
		]
	});
    var menuGrid = new Ext.grid.GridPanel({
    	renderTo:Ext.getBody(),
    	region : 'north',
        width:Ext.lib.Dom.getViewWidth(),
        height:Ext.lib.Dom.getViewHeight()/2,	
        plugins : [summary],
        bodyStyle : 'padding:0px',
        viewConfig : {
			columnsText : "显示的列",
			forceFit : true
		},
		tbar:tb,
		autoScroll:true, 
		autoExpandColumn:1,
        frame:false, 
        loadMask:true,
        sm:new Ext.grid.CheckboxSelectionModel(),
        cm:new Ext.grid.ColumnModel([rowNum,sm,
            {header:'班次号',dataIndex:'flightNo',width:50},
            {header:'班次日期',dataIndex:'flightDate',width:50},
            {header:'提货处',dataIndex:'cusName',width:50},
//            {header:'航班状态',dataIndex:'CUS_NAME',width:50},
            {header:"航班起飞时间",dataIndex:"standardStarttime",width:50},
            {header:"航班落地时间",dataIndex:"standardEndtime",width:50},
//            {header:"预计装车时间",dataIndex:"createName",width:100},
            {header:'货量(KG)',dataIndex:'cqWeight',width:50},
            {header:'票数',dataIndex:'ticketNum',width:50},
            {header:'件数',dataIndex:'piece',width:50},
            {header:'专车',dataIndex:'sonderzug',width:50}
           
        ]),
        store:oprFaxStore,
        bbar: new Ext.PagingToolbar({
            pageSize: pageSize,
            privilege:privilege, 
            store: oprFaxStore, 
            displayInfo: true,
            displayMsg: '显示第 {0} 条到 {1} 条记录，共 {2} 条', emptyMsg: "没有记录信息显示"
        }),
        listeners : {
			render : function() {
				queryTbar.render(this.tbar);
			}
        }
    });
    
    
    var guardPanel = new Ext.grid.GridPanel({
    	renderTo:Ext.getBody(),
    	region : 'sourth',
        width:Ext.lib.Dom.getViewWidth(),
        height:Ext.lib.Dom.getViewHeight()/2,	
        bodyStyle : 'padding:0px',
        viewConfig : {
			columnsText : "显示的列",
			forceFit : true
		},
		tbar:['调车时间',
			{
				xtype:'datefield',
				name:'countTime',
				id:'countTime',
				value:new Date(),
				format:'Y-m-d'
			},{
				text:'<B>搜索</B>',
            	id:'btnsearch', 
            	iconCls: 'btnSearch',
            	hidden:false,
            	handler : searchCarGuard
			}
		],
		autoScroll:true, 
		autoExpandColumn:1,
        frame:false, 
        loadMask:true,
        sm:new Ext.grid.CheckboxSelectionModel(),
        cm:new Ext.grid.ColumnModel([rowNum,sm,
            //{header:'提货处',dataIndex:'takeAddr',width:50},
            {header:'申请货量',dataIndex:'disShuntWeight',width:50,renderer:function(v){
            	return v+'KG';
            }},
            {header:'申请件数',dataIndex:'disShuntPiece',width:50},
            {header:'车牌号',dataIndex:'disCarNo',width:50},
            {header:'车型',dataIndex:'disCarTon',width:50,renderer:function(v){
            	return Number(v)*1000+'KG';
            }},
            {header:'车辆状态',dataIndex:'carStatus',width:50},
            {header:'调车时间',dataIndex:'createTime',width:50},
            {header:'发车时间',dataIndex:'planCarTime',width:50}
            //{header:'到车时间',dataIndex:'sonderzug',width:50},
            //{header:'配送单号',dataIndex:'sonderzug',width:50}
        ]),
        store:carGuardStore,
        bbar: new Ext.PagingToolbar({
            pageSize: pageSize,
            privilege:privilege, 
            store: carGuardStore, 
            displayInfo: true,
            displayMsg: '显示第 {0} 条到 {1} 条记录，共 {2} 条', emptyMsg: "没有记录信息显示"
        })
    });

     function searchTakeGoods() {
     	/*
		oprFaxStore.proxy = new Ext.data.HttpProxy({
					method : 'POST',
					url : sysPath
								+ "/sys/oprTakeAction!findTakeGoods.action",
						params:{privilege:privilege,limit : pageSize}
				});
		
		oprFaxStore.baseParams = {
			checkItems : Ext.get("checkItems").dom.value,
			privilege:privilege,
			itemsValue : Ext.get("searchContent").dom.value
		}
		var editbtn = Ext.getCmp('updatebtn');
		var deletebtn = Ext.getCmp('deletebtn');

		editbtn.setDisabled(true);
		deletebtn.setDisabled(true);
		menuStoreReload();*/
     	 Ext.apply(oprFaxStore.baseParams, {
     	 	flightNo:Ext.getCmp('flightcombo').getValue(),
			cusId:Ext.getCmp('custcombo').getValue(),
			startTime:Ext.getCmp('startTime').getValue(),
			endTime:Ext.getCmp('endTime').getValue(),
			departId:Ext.getCmp('departCombo').getValue(),
			isSonderzug:Ext.getCmp('sonderzugcombo').getValue(),
			limit:pageSize
     	 });
		oprFaxStore.reload({
			callback :function(){
				//if(oprFaxStore.getCount()>0){
					fnSumInfo();
				//}
			}
		});
	}
  // mypanel.render();
  // mypanel.doLayout();
	menuGrid.render();
	menuGrid.doLayout();
   menuGrid.on('click', function() {
        var _record = menuGrid.getSelectionModel().getSelections();
        var updatebtn = Ext.getCmp('addbtn');
        if (_record.length>=1) {       	
            if(updatebtn){
            	updatebtn.setDisabled(false);
            }
        }else {
            if(updatebtn){
            	updatebtn.setDisabled(true);
            }
        }
    });
    //end
   function fnSumInfo() {
		Ext.Ajax.request({
			url : sysPath+'/takegoods/oprTakeAction!inquerySum.action',
			params:{
				start : 0,
				flightNo:Ext.getCmp('flightcombo').getValue(),
				cusId:Ext.getCmp('custcombo').getValue(),
				startTime:Ext.getCmp('startTime').getValue(),
				endTime:Ext.getCmp('endTime').getValue(),
				departId:Ext.getCmp('departCombo').getValue(),
				isSonderzug:Ext.getCmp('sonderzugcombo').getValue(),
				limit : pageSize
			},
			success : function(response) { // 回调函数有1个参数
				summary.toggleSummary(true);
				summary.setSumValue(Ext.decode(response.responseText));
			},
			failure : function(response) {
				Ext.MessageBox.alert(alertTitle, '汇总数据失败');
			}
		});		
	}
});
function shuntApply(record){
	var stores='';
	for(var i=0;i<record.length;i++){
		stores+="shuntApplyList["+i+"].flightNo="+record[i].data.flightNo
		+"&shuntApplyList["+i+"].shuntWeight="+record[i].data.cqWeight
		+"&shuntApplyList["+i+"].shuntPiece="+record[i].data.piece
		+"&shuntApplyList["+i+"].takeAddr="+record[i].data.cusName;
		if(i!=record.length-1){
			stores+="&";
		}
		//+"&shuntApplyList["+i+"].flightEndTime="+record[i].flightEndTime;
	}
	Ext.Ajax.request({
		url:sysPath+'/takegoods/oprTakeAction!shuntApply.action',
		params:stores,
		success:function(resp){
			var respText = Ext.util.JSON.decode(resp.responseText);
			Ext.Msg.alert(alertTitle,respText.msg);
		}
	});
}
function searchCarGuard(){
	var time=Ext.get('countTime').dom.value;
	carGuardStore.load({
		params:{
			countTime:time
		}
	});
}
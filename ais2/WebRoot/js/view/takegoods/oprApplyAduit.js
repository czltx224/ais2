//调车申请审核	JS
var popupWin;
var privilege=223;
var oprFaxStore,carStore,oprShuntApplyStore,driverStore,collectionStore,collectionPanel ;
Ext.onReady(function() {
	//机场调度Store
	oprFaxStore=new Ext.data.Store({
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
        	{name:'piece',mapping:'PIECE'},
        	{name:'flightNo',mapping:'FLIGHT_NO'},
        	{name:'cusName',mapping:'CUS_NAME'}
        	])
	});
	//车辆Store
    carStore=new Ext.data.Store({
		storeId:"oprFaxStore",
		proxy:new Ext.data.HttpProxy({url:sysPath+"/bascar/basCarAction!ralaList.action"}),
		baseParams:{limit:pageSize,privilege:20},
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
        	{name:"carCode",mapping:'carCode'},
     		{name:'departId',mapping:'departId'},
     		{name:'departName',mapping:'departName'},
     		{name:'typeCode',mapping:'typeCode'},
     		{name:'cartrunkNo',mapping:'cartrunkNo'},
     		{name:'engineNo',mapping:'engineNo'},
     		{name:'buyDate',mapping:'buyDate'},
     		{name:'loadWeight',mapping:'loadWeight'},
     		{name:'maxloadWeight',mapping:'maxloadWeight'},
     		{name:'loadCube',mapping:'loadCube'},
     		{name:'maxloadCube',mapping:'maxloadCube'},
     		{name:'remark',mapping:'remark'},
     		{name:'type',mapping:'type'},
     		{name:'property',mapping:'property'},
     		{name:'comfirtBy',mapping:'comfirtBy'},
     		{name:'comfirtDate',mapping:'comfirtDate'},
     		{name:'ts',mapping:'ts'},
     		{name:'gpsNo',mapping:'gpsNo'},
     		{name:'carBrand',mapping:'carBrand'},
     		{name:'carStatus'}
        	])
	});
	//调车申请Store
	oprShuntApplyStore=new Ext.data.Store({
		id:"oprShuntApplyStore",
		proxy:new Ext.data.HttpProxy({url:sysPath+"/takegoods/oprTakeAction!findShuntApply.action"}),
		baseParams:{limit:pageSize},
		reader:new Ext.data.JsonReader({
                    root:'resultMap',
                    totalProperty:'totalCount'
        },[
        	{name:'id',mapping:'ID'},
        	{name:'flightNo',mapping:'FLIGHT_NO'},
        	{name:'shuntWeight',mapping:'SHUNT_WEIGHT'},
        	{name:'shuntPiece',mapping:'SHUNT_PIECE'},
        	{name:'takeAddr',mapping:'TAKE_ADDR'},
        	{name:'flightEndTime',mapping:'FLIGHT_END_TIME'},
        	{name:'uploadTime',mapping:'UPLOAD_TIME'},
        	{name:'disCarNo',mapping:'DIS_CAR_NO'},
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
	
	//司机资料数据 
	driverStore = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({url:sysPath+"/driver/driverAction!ralaList.action",method:'post'}),
            baseParams:{
             	privilege:22
            },
            reader: new Ext.data.JsonReader(
        	{
        		root: 'result', totalProperty: 'totalCount'
      		}, [{name:'driverName', mapping:'driverName',type:'string'},        
           		 {name:'id', mapping:'id'}
          		])    
	});

    //start
    var sm = new Ext.grid.CheckboxSelectionModel({});
    var rowNum = new Ext.grid.RowNumberer({
        header:'序号', width:35, sortable:true
    });
    var tb = new Ext.Toolbar({
    	width : Ext.lib.Dom.getViewWidth(),
    	items:[{
                text:'<B>申请审核</B>', disabled:true,id:'addbtn',tooltip:'调车申请审核', iconCls: 'userAdd',handler:function() {
                	var record=menuGrid.getSelectionModel().getSelected();
                	applyAduit(record);
            }},'-',
            {
				xtype :'textfield',
				id : 'searchContent',
				name : 'itemsValue',
				width : 100
    		},'-',
            {
            	xtype : "combo",
				width : 100,
				triggerAction : 'all',
				id:'comboCheckItems',
				model : 'local',
				hiddenId : 'checkItems',
				hiddenName : 'checkItems',
				name : 'checkItemstext',
				store : [
						['', '查询全部'],
						['LIKES_flightNo', '班次号'],
						['LIKES_takeAddr', '提货点']
						],
				emptyText : '选择类型',
				forceSelection : true,
				listeners : {
					'select' : function(combo, record, index) { // override
						if (combo.getValue() == '') {
							Ext.getCmp("searchContent").disable();
							Ext.getCmp("searchContent").show();
							Ext.getCmp("searchContent").setValue("");
						}else {
							Ext.getCmp("searchContent").enable();
							Ext.getCmp("searchContent").focus(true,true);
						}
					}
				}
            },'-',
            {
            	text:'<B>搜索</B>',
            	id:'btn', 
            	iconCls: 'btnSearch',
            	hidden:false,
            	handler : searchTakeGoods
            }
        ]
    });
    var expander = new Ext.ux.grid.RowExpander({
    	height:280,
        tpl : '<div id="dno_{id}" style="height:210px;width:1126px" ></div>',
        listeners : {
			'expand' : function(record, body, rowIndex){
		 		createTablePanel(body.get("id"));
			 }
		}
	});
    var menuGrid = new Ext.grid.GridPanel({
        region : "center",
        height:Ext.lib.Dom.getViewHeight(), 
        width:Ext.lib.Dom.getViewWidth(),
        bodyStyle : 'padding:0px',
        viewConfig : {
			columnsText : "显示的列",
			forceFit : true
		},
		autoScroll:true, 
		autoExpandColumn:1,
		plugins : [expander],
        frame:false, 
        loadMask:true,
        sm:sm,
        cm:new Ext.grid.ColumnModel([expander,rowNum,sm,
        	{header:'ID',dataIndex:'id',width:50,hidden: true, hideable: false},
            {header:'班次号',dataIndex:'flightNo',width:50},
            {header:'总重量',dataIndex:'shuntWeight',width:50},
            {header:'总件数',dataIndex:'shuntPiece',width:50},
            {header:'提货点',dataIndex:'takeAddr',width:50},
            {header:'航班落地时间',dataIndex:'flightEndTime',width:50},
            {header:'预计装车时间',dataIndex:'uploadTime',width:50},
            {header:'预计发车时间',dataIndex:'planCarTime',width:50},
            {header:'已派车车牌号',dataIndex:'disCarNo',width:50},
            {header:'已派车总吨位',dataIndex:'disCarTon',width:50},
            {header:'已派车总重量',dataIndex:'disShuntWeight',width:50},
            {header:'已派车总件数',dataIndex:'disShuntPiece',width:50}
        ]),
        store:oprShuntApplyStore,
        bbar: new Ext.PagingToolbar({
            pageSize: pageSize,
            privilege:privilege, 
            store: oprShuntApplyStore, 
            displayInfo: true,
            displayMsg: '显示第 {0} 条到 {1} 条记录，共 {2} 条', emptyMsg: "没有记录信息显示"
        })
    });
    var mypanel = new Ext.Panel({
    		layout : "border",
    		id : 'view',
    		renderTo : 'showView',
    		labelAlign : 'left',
    		height : Ext.lib.Dom.getViewHeight(),
    		width : Ext.lib.Dom.getViewWidth(),
    		bodyStyle : 'padding:0px',
    		layout : "border",
    		tbar : tb,
    		frame : false,
    		items : [menuGrid]
    });
     function searchTakeGoods() {
     	Ext.apply(oprShuntApplyStore.baseParams, {
			checkItems : Ext.get("checkItems").dom.value,
			itemsValue : Ext.get("searchContent").dom.value
		});
     	oprShuntApplyStore.load();
	}
   menuGrid.render();
   
   
    menuGrid.on('click', function() {
        var _record = menuGrid.getSelectionModel().getSelections();
        var updatebtn = Ext.getCmp('addbtn');
        if (_record.length==1) {       	
            if(updatebtn){
            	updatebtn.setDisabled(false);
            }
           
        }else {
            if(updatebtn){
            	updatebtn.setDisabled(false);
            }
        }
    });
});

function applyAduit(record){
	var form = new Ext.form.FormPanel({
		frame : true,
		border : false,
		bodyStyle : 'padding:5px 5px 5px',
		width : 400,
		labelAlign : "right",
		labelWidth : 85,
		defaults : {
			xtype : "textfield",
			width : 230
		},
		items:[
			{xtype:'hidden',name:'shuntApplyId',id:'shuntApplyId',value:record.data.id},
		   {
			    xtype:'combo',
				mode: 'local',
				id:'flightNocombo',
				name:'flightNo',
				valueField : 'flightNo',
				displayField : 'flightNo',
				queryParams:'flightNo',
				minChars:30,
				triggerAction:'all',
				maxHeight: 200, 
				listWidth:230,
				forceSelection:false,
				maxLength:200,
				maxLengthText:'长度不能超过200个字符',
				emptyText:'请选择',
				allowBlank:false,
				blankText:'班次号不能为空!!',
				hideTrigger:true,
				fieldLabel:'班次号<span style="color:red">*</span>',
				tpl: '<div id="filghtPanel" style="height:300px"></div>',
				store:new Ext.data.SimpleStore({fields:["flightNo","flightNo"],data:[[]]})
		   },
		   {
		   	  xtype:'textfield',
		   	  name:'disShuntWeight',
		   	  id:'disShuntWeight',
		   	  fieldLabel:'总重量',
		   	  readOnly:true
		   },{
		   	  xtype:'textfield',
		   	  name:'disShuntPiece',
		   	  id:'disShuntPiece',
		   	  fieldLabel:'总件数',
		   	  readOnly:true
		   },{
			    xtype:'combo',
				mode: 'local',
				id:'disCarNo',
				name:'disCarNo',
				valueField : 'disCarNo',
				displayField : 'disCarNo',
				queryParams:'disCarNo',
				minChars:30,
				triggerAction:'all',
				maxHeight: 300, 
				listWidth:400,
				forceSelection:false,
				maxLength:200,
				maxLengthText:'长度不能超过200个字符',
				emptyText:'请选择',
				allowBlank:false,
				blankText:'车牌号不能为空!!',
				hideTrigger:true,
				fieldLabel:'车牌号<span style="color:red">*</span>',
				tpl: '<div id="carPanel" style="height:300px"></div>',
				store:new Ext.data.SimpleStore({fields:["disCarNo","disCarNo"],data:[[]]}),
				listeners : {
					keyup:function(combo, e){
						if((Ext.get('disCarNo').dom.value).length>=2){
							carStore.load({
								params:{
									filter_LIKES_carCode:Ext.get('disCarNo').dom.value
								}
							});
						}
					}
				}
		   },{
		   	  xtype:'textfield',
		   	  name:'disCarTon',
		   	  id:'disCarTon',
		   	  fieldLabel:'吨位',
		   	  readOnly:true
		   },{
		   	  xtype:'hidden',
		   	  name:'driverName',
		   	  id:'driverName'
		   },{
			   	xtype:'combo',
			   	fieldLabel:'司机名称',
			   	hiddenId:'driverId',
			   	id:'driverId1',
				triggerAction : 'all',
				store : driverStore,
				queryParam : 'filter_LIKES_driverName',
				allowBlank : true,
				minChars : 0,
				editable : true,
				displayField : 'driverName',//显示值，与fields对应
				valueField : 'id',//value值，与fields对应
				hiddenName : 'driverId',
				pageSize:comboSize,
				listeners:{
					select:function(combo){
						Ext.getCmp('driverName').setValue(Ext.get('driverId1').dom.value);
					}
				}
		   },
		   	{
		   	 xtype:'timefield',
		   	 name:'planCarTime',
		   	 id:'planCarTime',
		   	 increment:30,
		   	 fieldLabel:'预计发车时间<span style="color:red">*</span>',
		   	 allowBlank:false,
			 blankText:'预计发车时间不能为空!!',
		   	 format:'H:i'
		   }
        ] 
	});
				
	var win = new Ext.Window({
		title : '审核调车申请',
		width : 400,
		closeAction : 'hide',
		plain : true,
		modal : true,
		items : form,
		buttonAlign : "center",
		buttons : [{
			text : "保存",
			iconCls : 'save',
			handler : function() {
				if (form.getForm().isValid()) {
					form.getForm().submit({
						url : sysPath + "/takegoods/oprShuntApplyDetailAction!save.action",
						params:{
							privilege:privilege
						},
						waitMsg : '正在保存数据...',
						success : function(form, action) {
							win.hide(), 
							Ext.Msg.alert(alertTitle,
									action.result.msg);
							oprShuntApplyStore.reload({params : {
								start : 0,
								limit : pageSize
								}
							});
						},
						failure : function(form, action) {
							if (action.faliureType == Ext.form.Action.SERVER_INVALID) {
								Ext.Msg.alert(alertTitle, "服务器连接异常，请联系管理员！");
							} else {
								if (action.result.msg) {
									win.hide();
									Ext.Msg.alert(alertTitle,
											action.result.msg);
								}
							}
						}
					});
				}
			}
		}, {
			text : "取消",
			handler : function() {
				win.close();
			}
		}]
	});
	win.on('hide', function() {
				form.destroy();
				consigneeGrid.destroy();
				carGrid.destroy();
			});
	win.show();
	
	var sm = new Ext.grid.CheckboxSelectionModel({});
    var rowNum = new Ext.grid.RowNumberer({
        header:'序号', width:35, sortable:true
    });
	var consigneeGrid=new Ext.grid.GridPanel({
    		height : 300,
    		id : 'consigneeGrid',
    		frame : false,
    		loadMask : true,
    		stripeRows : true,
    		onViewClick :Ext.emptyFn,
    		onSelect:Ext.emptyFn,
    		viewConfig:{
    			scrollOffset: 0,
    			autoScroll : true
    		},
    		sm:sm,
    		cm:new Ext.grid.ColumnModel([rowNum,sm,{
	    			header:'班次号',
	    			dataIndex:'flightNo'
	    		},{
	    			header:'重量',
	    			dataIndex:'cqWeight'
	    		},{
	    			header:'件数',
	    			dataIndex:'piece'
	    		}
    		]),
    		ds:oprFaxStore,
    		tbar:[
    		{
    			text:'<b>确定</b>',
    			disabled:true,
    			id:'confirmyes',
    			iconCls: 'save',
    			handler:function(){
    				var record=consigneeGrid.getSelectionModel().getSelections();
    				var tWeight=0;
    				var tPiece=0;
    				var flightN="";
    				for(var i=0;i<record.length;i++){
    					tWeight+=Number(record[i].data.cqWeight);
    					tPiece+=Number(record[i].data.piece);
    					flightN+=record[i].data.flightNo;
    					if(i!=record.length-1){
    						flightN+=",";	
    					}
    				}
    				Ext.getCmp('disShuntWeight').setValue(tWeight);
    				Ext.getCmp('disShuntPiece').setValue(tPiece);
    				Ext.getCmp('flightNocombo').setValue(flightN);
    				Ext.getCmp('flightNocombo').collapse();
    			}
    		}]
    });
    consigneeGrid.on('click', function() {
        var _record = consigneeGrid.getSelectionModel().getSelections();
        var updatebtn = Ext.getCmp('confirmyes');
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
    //车辆表格
    var carGrid=new Ext.grid.GridPanel({
    		height : 300,
    		id : 'carGrid',
    		frame : false,
    		loadMask : true,
    		stripeRows : true,
    		onViewClick :Ext.emptyFn,
    		onSelect:Ext.emptyFn,
    		viewConfig:{
    			scrollOffset: 0,
    			autoScroll : true
    		},
    		sm:sm,
    		cm:new Ext.grid.ColumnModel([rowNum,sm,{
	    			header:'车牌号',
	    			dataIndex:'carCode'
	    		},{
	    			header:'理论载重',
	    			dataIndex:'loadWeight'
	    		},{
	    			header:'GPS编码',
	    			dataIndex:'gpsNo'
	    		},{
	    			header:'车辆状态',
	    			dataIndex:'carStatus'
	    		}
    		]),
    		ds:carStore,
    		tbar:[
    		{
    			text:'<b>确定</b>',
    			disabled:true,
    			id:'carconfirmyes',
    			iconCls: 'save',
    			handler:function(){
    				var record=carGrid.getSelectionModel().getSelected();
    				Ext.getCmp('disCarNo').setValue(record.data.carCode);
    				Ext.getCmp('disCarTon').setValue(Number(record.data.loadWeight));
    				Ext.getCmp('disCarNo').collapse();
    			}
    		}]
    });
    carGrid.on('click', function() {
        var _record = carGrid.getSelectionModel().getSelections();
        var updatebtn = Ext.getCmp('carconfirmyes');
        if (_record.length==1) {       	
            if(updatebtn){
            	updatebtn.setDisabled(false);
            }
        }else {
            if(updatebtn){
            	updatebtn.setDisabled(true);
            }
        }
    });
	Ext.getCmp("flightNocombo").on('expand',function(){  
		if(consigneeGrid.rendered){
			consigneeGrid.doLayout();
		}else{
			consigneeGrid.render('filghtPanel');
			oprFaxStore.load({
				params:{
					flightNos:record.data.flightNo
				}
			});
		}
    });
    
    Ext.getCmp("disCarNo").on('expand',function(){  
		if(carGrid.rendered){
			carGrid.doLayout();
		}else{
			carGrid.render('carPanel');
			carStore.load();
		}
    });
}

function createTablePanel(shuntApplyId){
	var sm = new Ext.grid.CheckboxSelectionModel({});
    var rowNum = new Ext.grid.RowNumberer({
        header:'序号', width:35, sortable:true
    });
	//审核明细Store
	collectionStore = new Ext.data.Store({
		storeId : "collectionStore",
		baseParams:{
			limit:pageSize,
			filter_EQL_shuntApplyId:shuntApplyId,
			filter_EQL_status:1
		},
		proxy : new Ext.data.HttpProxy({
			url : sysPath + "/takegoods/oprShuntApplyDetailAction!list.action" 
		}),
		reader : new Ext.data.JsonReader({
			root : 'result',
			totalProperty : 'totalCount'
		},[
		  {name:'id'},
		  {name:'disCarNo'},
		  {name:'disCarTon'},
		  {name:'disShuntPiece'},
		  {name:'disShuntWeight'},
		  {name:'routeNumber'},
		  {name:'shuntApplyId'},
		  {name:'planCarTime'},
		  {name:'driverName'},
		  {name:'createTime'},
		  {name:'createName'},
		  {name:'updatName'},
		  {name:'updateTime'}
		])
	});
	collectionPanel=new Ext.grid.GridPanel({
		region : "center",
    	id : 'collectionPanel',
    	height : Ext.lib.Dom.getViewHeight(),
    	width : Ext.lib.Dom.getViewWidth()-1,
    	frame : false,
    	loadMask : true,
    	stripeRows : true,
    	viewConfig:{
    		scrollOffset: 0,
    		autoScroll : true
    	},
    	tbar:[
            {
            	text:'<b>撤销审核</b>',disabled:true,id:'delbtn',tooltip:'调车申请作废', iconCls: 'userDelete',handler:function() {
            		var _records=collectionPanel.getSelectionModel().getSelected();
                	Ext.Msg.confirm(alertTitle, "确定要作废所选的记录吗？", function(btnYes) {
                		if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
		                	Ext.Ajax.request({
		                		url:sysPath+'/takegoods/oprShuntApplyDetailAction!delete.action',
		                		params:{
		                			osadId:_records.data.id
		                		},success:function(resp){
		                			var respText=Ext.decode(resp.responseText);
		                			Ext.Msg.alert(alertTitle,respText.msg);
		                			collectionStore.load();
		                			oprShuntApplyStore.load();
		                		}
		                	});
                		}
                	});
                	
            	}
            }
    	],
    	sm:sm,
    	cm : new Ext.grid.ColumnModel([rowNum,sm,
			{
				header : '编号',
				dataIndex : 'id',
				width:80,hidden: true,
				hideable: false
			}, {
				header : '车次号',
				dataIndex : 'routeNumber',
				width:80,
				hidden: true
			}, {
				header:'车牌号',
				dataIndex:'disCarNo',
				width:80
			},{
				header : '司机',
				dataIndex : 'money',width:80
			}, {
				header : '配车重量',
				sortable : true,
				dataIndex : 'disShuntWeight',
				width:80
			}, {
				header : '配车吨位',
				dataIndex : 'disCarTon',
				width:80
			}, {
				header : '配车件数',
				dataIndex : 'disShuntPiece',
				width:80
			}, {
				header : '计划发车时间',
				dataIndex : 'planCarTime',
				width:80
			}, {
				header : '审核人',
				dataIndex : 'createName',
				width:80
			}, {
				header : '审核时间',
				dataIndex : 'createTime',width:80
			}, {
				header : '撤销人',
				hidden : true,
				dataIndex : 'updateName',width:80
			}, {
				header : '撤销时间',
				hidden : true,
				dataIndex : 'updateTime',
				width:80
			}

		]),
		ds : collectionStore,
		bbar : new Ext.PagingToolbar({
					pageSize : pageSize, // 默然为25，会在store的proxy后面传limit
					store : collectionStore,
					displayInfo : true,
					displayMsg : '显示第 {0} 条到 {1} 条记录，共 {2} 条',
					emptyMsg : "没有记录信息显示"
		})
	});
	if(collectionPanel.rendered){
		collectionPanel.doLayout();
	}else{
		collectionPanel.render('dno_'+shuntApplyId);
		collectionStore.load();
		collectionPanel.setHeight(200);
	}
	collectionPanel.on('click',function(){
    	var delbtn=Ext.getCmp("delbtn");
    	var _record = collectionPanel.getSelectionModel().getSelections();
    	if (_record.length==1) {       	
            if(delbtn){
            	delbtn.setDisabled(false);
            }
        }else {
            if(delbtn){
            	delbtn.setDisabled(true);
            }
        }
    });
}
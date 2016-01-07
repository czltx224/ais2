//到车确认JS
var carArriveUrl="stock/oprOvermemoAction!ralaList.action";
var dictionaryUrl='sys/dictionaryAction!ralaList.action';
var privilege=42;
var loadingbrigadeUrl='sys/loadingbrigadeAction!list.action';//装卸组分拨组查询地址
var inputWeight=0.0;
var addArriveRecord,totalStore,carArriveDetailStore,loadingbrigadeStore;

var  fields=[{name:"id"},
			{name:'totalWeight'},
			{name:'totalPiece'},
			{name:'totalTicket'},
     		{name:"startDepartId"},
     		{name:"startDepartName"},
     		{name:'endDepartId'},
     		{name:'endDepartName'},
     		{name:'startTime'},
     		{name:'endTime'},
     		{name:'unloadStartTime'},
     		{name:'unloadEndTime'},
     		{name:'overmemoType'},
     		{name:'carId'},
     		{name:'carType'},
     		{name:'carCode'},
     		{name:'status',convert:function(v){
     			if(v==0){
     				return '已发车';
     			}else if(v==1){
     				return '已到车确认';
     			}else if(v==2){
     				return '卸车开始';
     			}else if(v==3){
     				return '卸车结束';
     			 }
     			}
     		},
     		{name:'remark'},
     		{name:'createTime'},
     		{name:'createName'},
     		{name:'updateTime'},
     		{name:'updateName'},
     		{name:'orderfields'},
     		{name:'lockNo'},
     		{name:'routeNumber'},
     		{name:'ts'}];
     		
			//车辆状态
     		carStatus=new Ext.data.SimpleStore({
			 autoLoad:true, //此处设置为自动加载
  			  data:[['0','已发车'],['1','已到车确认'],['2','卸车开始'],['3','卸车结束']],
   			 fields:["statusId","statusName"]
		});
		
Ext.onReady(function() {
		loadingbrigadeStore= new Ext.data.Store({ 
			storeId:"loadingbrigadeStore",
			baseParams:{filter_EQL_type:0,filter_EQL_departId:bussDepart},
			proxy:new Ext.data.HttpProxy({url:sysPath+"/"+loadingbrigadeUrl}),
			reader:new Ext.data.JsonReader({
	                    root:'result',
	                    totalProperty:'totalCount'
	        },[
	    	{name:'loadingbrigadeId',mapping:'id'},
	    	{name:'loadingbrigadeName',mapping:'loadingName'}
	    	])
		});
		//用车类型
		 var carTypeStore= new Ext.data.Store({ 
			autoLoad:true, 
			storeId:"carTypeStore",
			baseParams:{filter_EQL_basDictionaryId:30,privilege:16},
			proxy:new Ext.data.HttpProxy({url:sysPath+"/"+dictionaryUrl}),
			reader:new Ext.data.JsonReader({
	                    root:'result',
	                    totalProperty:'totalCount'
	        },[
	    	{name:'id',mapping:'typeCode'},
	    	{name:'carTypeName',mapping:'typeName'}
	    	])
		});
			//车辆用途加载
		 var carDoStore= new Ext.data.Store({ 
			autoLoad:true, 
			storeId:"carDoStore",
			baseParams:{filter_EQL_basDictionaryId:31,privilege:16},
			proxy:new Ext.data.HttpProxy({url:sysPath+"/"+dictionaryUrl}),
			reader:new Ext.data.JsonReader({
	                    root:'result',
	                    totalProperty:'totalCount'
	        },[
	    	{name:'id',mapping:'typeCode'},
	    	{name:'carDoName',mapping:'typeName'}
	    	])
		});
		
    	var carArriveStore = new Ext.data.Store({
    		    baseParams:{privilege:privilege,limit:pageSize,filter_EQL_endDepartId:bussDepart},
                proxy: new Ext.data.HttpProxy({url:sysPath+'/'+carArriveUrl}),
                reader:new Ext.data.JsonReader({
            root: 'result', totalProperty: 'totalCount'
        }, fields)
        });
    	
    	var carStore=new Ext.data.Store({
    		baseParams:{privilege:20,limit:pageSize},
                proxy: new Ext.data.HttpProxy({url:sysPath+'/bascar/basCarAction!list.action'}),
                reader:new Ext.data.JsonReader({
            root: 'result', totalProperty: 'totalCount'
        }, [
        	{name:'carId',mapping:'id'},
        	{name:'carCode'}
        	])
    	});
		
		var cusStore=new Ext.data.Store({
		storeId:"cusStore",
		method:'post',
		baseParams:{limit:pageSize,privilege:61,filter_EQS_custprop:'提货公司'},
		proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/customerAction!ralaList.action"}),
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
        	{name:'cusId',mapping:'id'},
        	{name:'cusName'}
        	])
		});
		var oprStartStore=new Ext.data.Store({
		storeId:"oprStartStore",
		method:'post',
		proxy:new Ext.data.HttpProxy({url:sysPath+"/stock/oprOvermemoAction!findStartDepart.action"}),
		reader:new Ext.data.JsonReader({
                    root:'resultMap',
                    totalProperty:'totalCount'
        },[
        	{name:'STARTNAME'}
        	])
		});
		
		var overTypeStore=new Ext.data.Store({
		storeId:"addrStore",
		proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/dictionaryAction!ralaList.action"}),
		baseParams:{filter_EQL_basDictionaryId:11,privilege:16},
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
        	{name:'overId',mapping:'id'},
        	{name:'overType',mapping:'typeName'}
        	])
		});
		overTypeStore.load();
		
		var flightStore=new Ext.data.Store({
		storeId:"flightStore",
		proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/basFlightAction!ralaList.action"}),
		baseParams:{limit:pageSize,privilege:62},
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
        	{name:'flightId',mapping:'id'},
        	{name:'flightNumber'}
        	])
		});
		 var printmenu = new Ext.menu.Menu({
		 	items:[{
    					text : '<B>车辆签单打印</B>',
    					id : 'printmsgbtn',
    					disabled:true,
    					tooltip : '车辆签单打印',
    					iconCls : 'printBtn',
    					handler:function (){
    						var _record = recordGrid.getSelectionModel().getSelections();
	    					if(_record.length<1){
	    						Ext.Msg.alert(alertTitle, "请选择您要操作的行！");
	    					}
	    					else if(_record.length>1){
	    						Ext.Msg.alert(alertTitle, "一次只能操作一行！");
	    					}else{
	    						searCarArrive();
	    						if(_record[0].get('status')!='卸车结束'){
	    							Ext.Msg.alert(alertTitle, "只有卸车结束才可以打印车辆签单！",function(){
   									});
   									return;
   								}
	    						isprintMsg(_record);
	    						//parent.print('4',{print_routeNumber:_record[0].data.routeNumber});
	    					}
    					}
    				}, '-',{
    					text : '<B>理货清单打印</B>',
    					tooltip : '理货清单打印',
    					iconCls : 'printBtn',
    					handler:function (){
    						var _record = recordGrid.getSelectionModel().getSelections();
	    					if(_record.length<1){
	    						Ext.Msg.alert(alertTitle, "请选择您要操作的行！");
	    						return;
	    					}
	    					else if(_record.length>1){
	    						Ext.Msg.alert(alertTitle, "一次只能操作一行！");
	    						return;
	    					}
    						parent.print('7',{print_overmemoId:_record[0].data.id});
    					}
    				}]
		 	});
    	var tb = new Ext.Toolbar({
    		width : Ext.lib.Dom.getViewWidth(),
    		items : [{text:'<b>导出</b>',iconCls:'sort_down',handler:function() {
                		parent.exportExl(recordGrid);
        			} },'-','&nbsp;&nbsp;', {
    					text : '<B>到车确认</B>',
    					id : 'confirmbtn',
    					disabled:true,
    					tooltip : '到车确认',
    					iconCls : 'groupAdd',
    					handler:function(){
    						var _record = recordGrid.getSelectionModel().getSelections();
	    					if(_record.length<1){
	    						Ext.Msg.alert(alertTitle, "请选择您要操作的行！");
	    					}
	    					else if(_record.length>1){
	    						Ext.Msg.alert(alertTitle, "一次只能操作一行！");
	    					}else{
    							var _record = recordGrid.getSelectionModel().getSelections();
    							carArriveConfirm(_record);
    						}
    					}
    				}, '-', {
    					text : '<B>到车确认撤销</B>',
    					id : 'returnbtn',
    					disabled:true,
    					tooltip : '撤销已经到车确认的信息',
    					iconCls : 'groupEdit',
    					handler:function(){
    						var _record = recordGrid.getSelectionModel().getSelections();
    						if(_record.length<1){
					    				Ext.Msg.alert(alertTitle, "请选择您要操作的行！");
					    				return false;
					    	}else{
					    		Ext.Msg.confirm(alertTitle, "确定撤销吗？", function(btnYes) {
				    				if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
						    			uploadreturn(_record);
						    		}
			    				})
					    	}
    					}
    				}, '-', {
    					text : '<B>手动新增</B>',
    					id : 'addbtn',
    					tooltip : '手动新增交接单与交接单明细',
    					iconCls : 'tabAdd',
    					handler:function(){
    						addCarArrive();
    					}
    				}, '-', {
    					text : '<B>卸车开始</B>',
    					disabled:true,
    					id : 'uploadstartbtn',
    					tooltip : '开始卸车',
    					iconCls : 'groupPass',
    					handler:function(){
	    					var _record = recordGrid.getSelectionModel().getSelections();
	    					if(_record.length<1){
	    						Ext.Msg.alert(alertTitle, "请选择您要操作的行！");
	    					}
	    					else if(_record.length>1){
	    						Ext.Msg.alert(alertTitle, "一次只能操作一行！");
	    					}else{
	    						Ext.Msg.confirm(alertTitle, "确定开始卸车吗？", function(btnYes) {
	    						if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
			    						upload(_record);
			    					}
	    						})
	    					}
    				}
    				}, '-', {
    					text : '<B>卸车结束</B>',
    					id : 'uploadendbtn',
    					disabled:true,
    					iconCls : 'groupNotPass',
    					handler:function (){
    						var _record = recordGrid.getSelectionModel().getSelections();
	    					if(_record.length<1){
	    						Ext.Msg.alert(alertTitle, "请选择您要操作的行！");
	    					}
	    					else if(_record.length>1){
	    						Ext.Msg.alert(alertTitle, "一次只能操作一行！");
	    					}else{
	    						searCarArrive();
	    						if(_record[0].get('status')!='卸车开始'){
	    							Ext.Msg.alert(alertTitle, "只有卸车开始的记录才能操作卸车结束！",function(){
   									});
   									return;
   								}
	    						Ext.Msg.confirm(alertTitle, "您确定要结束卸车吗？", function(btnYes) {
	    							if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
		    							loadEndCar(_record);
			    					}
	    						})
	    					}
    					}
    				},'-',{
    					text:'<b>打印</b>',
    					menu:printmenu
    				},'-', {
    					xtype :'textfield',
    					id : 'searchContent',
    					name : 'itemsValue',
    					width : 120
    				} ,'-', {
    					xtype : 'datefield',
    					format:'Y-m-d',
    					hidden:true,
    					id : 'datefieldstart',
    					name : 'startTime',
    					width : 120
    				}, {
    					xtype : 'datefield',
    					id:'datefieldend',
    					format:'Y-m-d',
    					hidden:true,
    					name : 'endTime',
    					width : 120
    				}, {
    					xtype : "combo",
    					width : 120,
    					hidden:'true',
    					triggerAction : 'all',
    					id:'comboStatus',
    					hiddenId : 'combostaid',
    					hiddenName : 'combostaid',
    					model : 'local',
    					name : 'checkItemstext',
    					store : [
    							['', '查询全部'],
    							['0', '已发车'],
    							['1', '已到车确认'],
    							['2',"卸车开始"],
    							['3', '卸车结束']
    							],
    					emptyText : '选择类型',
    					forceSelection : true
    					}
    				, {
    					xtype : "combo",
    					width : 120,
    					triggerAction : 'all',
    					id:'comboCheckItems',
    					model : 'local',
    					hiddenId : 'checkItems',
    					hiddenName : 'checkItems',
    					name : 'checkItemstext',
    					store : [
    							['', '查询全部'],
    							['LIKES_startTime', '发车时间'],
    							['LIKES_endTime', '到车时间'],
    							['LIKES_unloadEndTime',"卸车结束时间"],
    							['EQL_totalTicket', '票数'],
    							['EQL_status', '']],
    					emptyText : '选择类型',
    					forceSelection : true,
    					listeners : {
    						'select' : function(combo, record, index) { // override
    							
    							if (combo.getValue() == '') {
    								Ext.getCmp("searchContent").disable();
    								Ext.getCmp("searchContent").show();
    								Ext.getCmp("searchContent").setValue("");
    								
    								Ext.getCmp("comboStatus").hide();
    								Ext.getCmp("comboStatus").setValue("");
    								Ext.getCmp("datefieldstart").hide();
    								Ext.getCmp("datefieldend").setValue("");
    								Ext.getCmp("datefieldstart").setValue("");
    								Ext.getCmp("datefieldend").hide();
    							} else if(combo.getValue() == 'LIKES_startTime'||combo.getValue() == 'LIKES_endTime'||combo.getValue() == 'LIKES_unloadEndTime') {
    								Ext.getCmp("searchContent").hide();
    								Ext.getCmp("searchContent").setValue("");
    								Ext.getCmp("comboStatus").hide();
    								Ext.getCmp("comboStatus").setValue("");
    								Ext.getCmp("datefieldstart").show();
    								Ext.getCmp("datefieldend").show();
    							} else if(combo.getValue()=='EQL_status'){
    								Ext.getCmp("comboStatus").show();
    								
    								Ext.getCmp("searchContent").hide();
    								Ext.getCmp("datefieldend").hide();
    								Ext.getCmp("datefieldstart").hide();
    								Ext.getCmp("searchContent").setValue("");
    								Ext.getCmp("datefieldend").setValue("");
    								Ext.getCmp("datefieldstart").setValue("");
    							}else {
    								Ext.getCmp("comboStatus").hide();
    								
    								Ext.getCmp("datefieldend").setValue("");
    								Ext.getCmp("datefieldstart").setValue("");
    								
    								Ext.getCmp("datefieldend").hide();
    								Ext.getCmp("datefieldstart").hide();
    								
    								Ext.getCmp("searchContent").enable();
    								Ext.getCmp("searchContent").show();
    								Ext.getCmp("searchContent").focus(true,true);
    							}
    						}
    					}
    				}]
    	});

    	var form1 = new Ext.form.FormPanel({
    				id : 'form1',
    				frame : true,
    				width : 100,
    				cls : 'displaynone',
    				hidden : true,
    				items : [],
    				buttons : []
    			});
    	form1.render(document.body);

    	var rowNum = new Ext.grid.RowNumberer({
    				header : '序号',
    				width : 35,
    				sortable : true
    			});

    	var recordGrid = new Ext.grid.GridPanel({
    				region : "center",
    				id : 'myrecordGrid',
    				height : Ext.lib.Dom.getViewHeight(),
    				width : Ext.lib.Dom.getViewWidth()-1,
    				frame : false,
    				loadMask : true,
    				stripeRows : true,
    				viewConfig:{
    				   scrollOffset: 0,
    				   autoScroll : true
    				},
    				cm : new Ext.grid.ColumnModel([rowNum,
    						new Ext.grid.CheckboxSelectionModel(),
    						{
    							header : '交接单号',sortable:true,
    							dataIndex : 'id',width:80
    						}, {
    							header : '车牌号',sortable:true,
    							dataIndex : 'carCode',width:80
    						}, {
    							header:'车次号',sortable:true,
    							dataIndex:'routeNumber',
    							width:80
    						},{
    							header : '重量(KG)',sortable:true,
    							dataIndex : 'totalWeight',width:80
    						}, {
    							header : '件数',
    							sortable : true,
    							dataIndex : 'totalPiece',width:60
    						}, {
    							header : '票数',sortable:true,
    							dataIndex : 'totalTicket',width:60
    						}, {
    							header : '始发部门',sortable:true,
    							dataIndex : 'startDepartName',width:80
    						}, {
    							header : '车辆状态',sortable:true,
    							dataIndex : 'status',width:80
    						}, {
    							header : '到达部门',sortable:true,
    							dataIndex : 'endDepartName',width:80
    						}, {
    							header : '发车时间',
    							sortable : true,
    							dataIndex : 'startTime',width:80
    						}, {
    							header : '到车时间',
    							sortable : true,
    							dataIndex : 'endTime',width:80
    						}, {
    							header : '卸车开始时间',
    							sortable : true,
    							dataIndex : 'unloadStartTime',width:80
    						}, {
    							header : '卸车结束时间',
    							sortable : true,
    							dataIndex : 'unloadEndTime',width:80
    						}, {
    							header : '交接单类型',sortable:true,
    							dataIndex : 'overmemoType',width:80
    						}, {
    							header : '备注',sortable:true,
    							dataIndex : 'remark',width:80
    						}, {
    							header : '锁号',sortable:true,
    							dataIndex : 'lockNo',width:80
    						}, {
    							header : '车辆类型',sortable:true,
    							dataIndex : 'carType',width:80
    						}
//    						, {
//    							header : '车辆位置',
//    							dataIndex : 'carWhere',width:80
//    						}

    				]),
    				sm : new Ext.grid.CheckboxSelectionModel(),
    				ds : carArriveStore,
    				bbar : new Ext.PagingToolbar({
    							pageSize : pageSize, // 默然为25，会在store的proxy后面传limit
    							store : carArriveStore,
    							displayInfo : true,
    							displayMsg : '显示第 {0} 条到 {1} 条记录，共 {2} 条',
    							emptyMsg : "没有记录信息显示"
    						})
    			});
		recordGrid.on('click', function() {
        var _record = recordGrid.getSelectionModel().getSelections();
        var confirmbtn = Ext.getCmp('confirmbtn');
        var returnbtn = Ext.getCmp('returnbtn');
        var uploadbtn=Ext.getCmp('uploadstartbtn');
        var uploadendbtn=Ext.getCmp('uploadendbtn');
        
        var printbtn=Ext.getCmp('printmsgbtn');
       if (_record.length==1) {
       		uploadendbtn.setDisabled(false);       	
            if(confirmbtn){
            	confirmbtn.setDisabled(false);
            }
            if(returnbtn){
            	returnbtn.setDisabled(false);
            }
            if(uploadbtn){
            	uploadbtn.setDisabled(false);
            }
            if(printbtn){
            	printbtn.setDisabled(false);
            }
        }else if(_record.length>1){
        	uploadendbtn.setDisabled(true);       	
        	if(returnbtn){
            	returnbtn.setDisabled(true);
            }
            if(confirmbtn){
            	confirmbtn.setDisabled(true);
            }
        }
    });
    	// 布局
    	var mypanel = new Ext.Panel({
    		layout : "border",
    		//title : "到车确认",
    		id : 'view',
    		el : 'showView',
    		labelAlign : 'left',
    		height : Ext.lib.Dom.getViewHeight(),
    		width : Ext.lib.Dom.getViewWidth(),
    		bodyStyle : 'padding:1px',
    		layout : "border",
    		tbar : tb,
    		frame : false,
    		items : [recordGrid]
    	});

    	// 审核操作tbar第二行
    	mypanel.on('render', function() {
    		var tbar = new Ext.Toolbar({
    					items : ['', '车牌号:', {
    								xtype : "combo",
			    					width : 120,
			    					triggerAction : 'all',
			    					model : 'remote',
			    					id:'combocar',
			    					queryParam : 'filter_LIKES_carCode',
			    					pageSize:pageSize,
			    					minChars : 2,
			    					valueField : 'carId',
    								displayField : 'carCode',
    								resizable:true,
    								hiddenName : 'carId',
			    					name : 'checkItemscar',
			    					store :carStore,
			    					emptyText : '选择类型'

    							}, '-', '', '提货点:', {
    								xtype : "combo",
			    					width : 120,
			    					triggerAction : 'all',
			    					model : 'local',
			    					queryParam : 'filter_LIKES_cusName',
			    					pageSize:pageSize,
			    					id:'combodepart',
			    					resizable:true,
			    					valueField : 'STARTNAME',
    								displayField : 'STARTNAME',
    								hiddenName : 'STARTNAME',
			    					name : 'checkItemscar',
			    					store :oprStartStore,
			    					emptyText : '选择类型',
			    					forceSelection : true

    							}, '-', '交接单类型:', {
    								xtype : "combo",
			    					width : 120,
			    					queryParam : 'filter_LIKES_typeName',
			    					triggerAction : 'all',
			    					model : 'local',
			    					id:'comboovermemo',
			    					valueField : 'overType',
    								displayField : 'overType',
    								hiddenName : 'overType',
			    					name : 'checkItemsover',
			    					store :overTypeStore,
			    					emptyText : '选择类型',
			    					forceSelection : true

    							}, '-','车辆状态:',{
    								xtype : "combo",
			    					width : 120,
			    					triggerAction : 'all',
			    					mode : "local",//获取本地的值
			    					resizable:true,
			    					valueField : 'statusId',
    								displayField : 'statusName',
			    					id : 'searchStatus',
			    					forceSelection : true,
			    					store :carStatus
    								
    							},'-', '交接单号:', {
    								xtype : 'textfield',
			    					id : 'overmemono',
			    					name : 'itemsValue',
			    					width : 120
    							},'-', {
	    							text : '<b>搜索</b>',
	    							id : 'btn',
	    							iconCls : 'btnSearch',
	    							handler:searCarArrive
    					}
    					]
    				});
    		tbar.render(mypanel.tbar);
    	}

    	);
    	mypanel.render();
    	mypanel.doLayout();
    	recordGrid.render();
    	
    	
        //手动新增
    	function addCarArrive(){
    		var fields1=[
    				{name:'dno'},
		        	{name:'flightMainNo'},
		        	{name:'subNo'},
		        	{name:'consignee'},
		        	{name:'cpName'},
		        	{name:'cusId'},
		        	{name:'distributionMode'},
		        	{name:'takeMode'},
		        	{name:'addr'},
		        	{name:'flightNo'},
		        	{name:'piece'},
		        	{name:'cusWeight'},
		        	{name:'isException'},
		        	{name:'ts'}
    			];
    		carArriveDetailStore = new Ext.data.Store({
    			id:'carArriveDetailStore',
    		    baseParams:{limit:200},
                proxy: new Ext.data.HttpProxy({url:sysPath+'/fax/oprFaxInAction!findMainMsg.action'}),
                reader:new Ext.data.JsonReader({
            root: 'result', totalProperty: 'totalCount'
        }, fields1)
        });
    	
    	totalStore=new Ext.data.Store({
    			 id:'totalStore',
    			 reader:new Ext.data.JsonReader({
	            root: 'result', totalProperty: 'totalCount'
	        }, fields1)
    	});
    	
    	var goodsTypeStore= new Ext.data.Store();
    	
    	addArriveRecord = new Ext.grid.GridPanel({
    				region : "center",
    				id : 'myrecordGrid1',
    				height : 130,
    				width : 650,
    				autoScroll : true,
    				//autoSizeColumns: true,
    				frame : false,
    				loadMask : true,
    				stripeRows : true,
    				viewConfig:{
    				  // forceFit: true,
    				   scrollOffset: 0
    				},
    				cm : new Ext.grid.ColumnModel([rowNum,
    						new Ext.grid.CheckboxSelectionModel(), 
    						{header : '配送单号',dataIndex:'dno',width:80}, 
    						{header : '主单号',dataIndex : 'flightMainNo',width:80}, 
    						{header : '分单号',dataIndex : 'subNo',width:80}, 
    						{header : '收货人',dataIndex : 'consignee',width:80},
    						{header : '发货代理',dataIndex : 'cpName',width:80},
    						{header : '配送方式',dataIndex : 'distributionMode',width:80},
    						{header : '提货方式',dataIndex : 'takeMode',width:80},
    						{header : '收货人地址',dataIndex : 'addr',width:80}, 
    						{header : '航班号',dataIndex : 'flightNo',width:80},
    						{header : '件数',dataIndex : 'piece',width:60}, 
    						{header : '重量',dataIndex : 'cusWeight',width:80},
    						{header : '是否异常',dataIndex:'isException',width:80,renderer:function(v,m){
    							if(v==0){
    								return "否";
    							}else{
    								//m.css="x-liuh";
    								return "是";
    							}
    							}	
    						}
    						/*,
    						{header : '操作',dataIndex:'dno',width:60,renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){
    							//删除一条记录
    							return "<a href='#' onclick='delTotalStore("+rowIndex+");'>删除</a>";
    						}
    						}*/
    				]),
    				sm : new Ext.grid.CheckboxSelectionModel(),
    				ds : totalStore
    			});
    	addArriveRecord.on('click',function(){
    		var _record = addArriveRecord.getSelectionModel().getSelections();
	        var deletebtn = Ext.getCmp('delbtn');
	       if (_record.length>=1) {       	
	            if(deletebtn){
	            	deletebtn.setDisabled(false);
	            }
	        } else {
				if(deletebtn){
	            	deletebtn.setDisabled(true);
				}
	        }
    	});
    		//删除一条记录
    		var fiInterField= [
			     {name:'flightMainNo'},
			     {name:'flightPiece'},
			     {name:'filghtWeight'},
			     {name:'customerId'},
			     {name:'goodsName'}
			     ];
    	    var fiInterStore=new Ext.data.Store({
    			 id:'fiInterStore',
    			 reader:new Ext.data.JsonReader({
	            root: 'result', totalProperty: 'totalCount'
			     },fiInterField)
		    });
    		var form = new Ext.form.FormPanel({
    				id:'addForm',
					frame : true,
					border : false,
					bodyStyle : 'padding:5px 5px 5px',
					labelAlign : "right",
					labelWidth : 80,
					width : 650,
					height:420,
					items:[{
						 layout : 'column',
						 items:[{
							 layout:'form',
							 columnWidth:.5,
							 items:[
								  {
    						xtype:'hidden',
    						name:'carId',
    						id:'carId'
    					},{   xtype:'combo',
									 fieldLabel:'车牌号<span style="color:red">*</span>',
									 name:'carId',
									 width:200,
									 triggerAction : 'all',
			    					 model : 'local',
			    					 minChars : 0,
			    					 id:'combocar1',
			    					 queryParam : 'filter_LIKES_carCode',
			    					 pageSize:pageSize,
			    					 valueField : 'carId',
    								 displayField : 'carCode',
    								 name:'carCode',
									 store:carStore,
									 emptyText : '选择类型',
			    					 allowBlank : false,
			    					 enableKeyEvents : true,
			    					listeners:{
			    						select:function(combo,e){
			    							Ext.Ajax.request({
			    								url:sysPath+'/takegoods/oprShuntApplyDetailAction!findMaxRouteNumber.action',
			    								params:{
			    									carNo:Ext.get('combocar1').dom.value
			    								},success:function(resp){
			    									var respText=Ext.decode(resp.responseText);
			    									if(respText!=null&&respText!='null'){
			    										Ext.getCmp('routeNumber').setValue(respText);
			    									}else{
			    										Ext.getCmp('routeNumber').setValue('');
			    									}
			    								}
			    							});
			    							if(combo.getValue()==combo.getRawValue()){
			    								Ext.getCmp('carId').setValue(''); 
			    							}else{
			    								Ext.getCmp('carId').setValue(combo.getValue());
			    							}
			    						},
			    						keyup:function(textField, e){
			    							if(e.getKey() == 13){
			    								Ext.getCmp('comboDisId').focus(true,true);
			    							}
			    						}
			    					 }
									 },{xtype:'hidden',name:'startDepartName',id:'startDepartName'},
								 	 {
										xtype:'combo',
										fieldLabel:'提货点<span style="color:red">*</span>',
								        hiddenName:'startDepartId',
								        width:200,
										blankText:'提货点不能为空!',
										typeAhead : true,
    									forceSelection : true,
    									queryParam : 'filter_LIKES_cusName',
    									minChars : 0,
    									id:'comboDisId',
    									store : cusStore,
    									pageSize : pageSize,
    									triggerAction : 'all',
    									valueField : 'cusId',
    									displayField : 'cusName',
    									emptyText : "选择类型",
    									allowBlank : false,
    									enableKeyEvents : true,
    									listeners:{
    										select:function(combo){
    											Ext.getCmp('goodsName').clearValue();
    											
   												goodsTypeStore.proxy=new Ext.data.HttpProxy({
											 		url : sysPath + "/fi/fiDeliveryPriceAction!list.action",
													method:'post'
											 	});
											 	
											 	Ext.apply(goodsTypeStore.baseParams,{
    												filter_EQL_customerId:combo.getValue(),
													filter_EQL_departId:bussDepart,
													filter_EQL_isdelete:1

    											});
   											
									            goodsTypeStore.reader= new Ext.data.JsonReader({
													root : 'result',
													totalProperty : 'totalCount'
												}, [{
													name : 'goodsType',
													mapping : 'goodsType'
												}, {
													name : 'id',
													mapping : 'id'
												}])
    											
    											Ext.getCmp('goodsName').store.load({callback:function(store){
    												if(store.length==0){
    													Ext.Msg.alert(alertTitle,"该航空公司没有货物类型，请到提货协议价中维护！");
    												}
    											}});
    											Ext.getCmp('startDepartName').setValue(Ext.get('comboDisId').dom.value);
    										},
				    						keyup:function(textField, e){
				    							if(e.getKey() == 13){
				    								Ext.getCmp('lockNo').focus(true,true);
				    							}
				    						}
    									}
    									
								 },
								 {		
								 	xtype:'textfield',
								 	fieldLabel:'锁号<span style="color:red">*</span>',
								 	id:'lockNo',
								 	name:'lockNo',
								 	maxLength:20,
								 	width:200,
								 	allowBlank:false,
								 	blankText:'锁号不能为空!',
								 	enableKeyEvents : true,
								 	listeners:{
			    						keyup:function(textField, e){
			    							if(e.getKey() == 13){
			    								Ext.getCmp('comboovermemonew').focus(true,true);
			    							}
			    						}
								 	}
								 },
								 {xtype:'numberfield',fieldLabel:'总件数',name:'totalPiece',maxLength:5,width:200,readOnly:true,id:'totalPiece'},
								 {xtype:'textarea',width:200,fieldLabel:'备注',height:70,name:'remark'}
								]
							 },{
    						 layout:'form',
    						 columnWidth:.5,
    						 items:[
    							{
    								xtype : "combo",
    								fieldLabel:'交接单类型<span style="color:red">*</span>',
			    					width : 200,
			    					queryParam : 'filter_LIKES_typeName',
			    					triggerAction : 'all',
			    					model : 'local',
			    					id:'comboovermemonew',
			    					valueField : 'overType',
    								displayField : 'overType',
			    					name : 'overmemoType',
			    					store :overTypeStore,
			    					emptyText : '选择类型',
			    					forceSelection : true,
			    					allowBlank : false,
			    					enableKeyEvents : true,
								 	listeners:{
			    						keyup:function(textField, e){
			    							if(e.getKey()==13){
			    								if(textField.getValue()=='上门接货'){
			    									showCombobox('loadingbrigade');
			    									Ext.getCmp('loadingbrigade').focus(true,true);
			    								}else{
			    									hideCombobox('loadingbrigade');
			    									Ext.getCmp('startTimedate').focus(true,true);
			    								}
			    							}
			    						},
			    						select:function(combo){
			    							if(combo.getValue()=='上门接货'){
		    									showCombobox('loadingbrigade');
		    								}else{
		    									hideCombobox('loadingbrigade');
		    								}
			    						}
								 	}
    							},{
    								xtype : "combo",
									width : 200,
									id : 'loadingbrigade',
									queryParam : 'filter_LIKES_loadingName',
									fieldLabel:'装卸组<span style="color:red">*</span>',
									pageSize : comboSize,
									minChars : 0,
									editable:true,
									forceSelection : true,
									selectOnFocus : true,
									resizable : true,
									triggerAction : 'all',
									store : loadingbrigadeStore,
									minChars : 0,
									mode : "remote",// 从本地加载值
									valueField : 'loadingbrigadeId',// value值，与fields对应
									displayField : 'loadingbrigadeName',// 显示值，与fields对应
									hiddenName : 'loadingbrigadeId',
									allowBlank : false,
			    					enableKeyEvents : true,
			    					listeners:{
			    						keyup:function(textField, e){
			    							if(e.getKey()==13){
			    								Ext.getCmp('startTimedate').focus(true,true);
			    							}
			    						}
			    					}
    							},
    							{xtype:'hidden',name:'ts'},
								{
									xtype:'datetimefield',
									fieldLabel:'发车时间<span style="color:red">*</span>',
									name:'startTime',
									id:'startTimedate',
									format:'Y-m-d H:i:s',
									allowBlank:false,
									width:200,
									allowBlank:false,
									blankText:'发车时间不能为空!',
									invalidText:'时间格式必须为：××××-××-×× ××:××:××',
									enableKeyEvents : true,
								 	listeners:{
			    						keyup:function(textField, e){
			    							if(e.getKey()==13){
			    								Ext.getCmp('dicCarType').focus(true,true);
			    							}
			    						}
								 	}
								},
								{
									xtype : 'combo',
									id:'dicCarType',
									triggerAction : 'all',
									selectOnFocus : true, 
									store : carTypeStore,
									mode:'local',
									fieldLabel:'用车类型<span style="color:red">*</span>',
									width:200,
									allowBlank : false,
									blankText:'用车类型不能为空！',
									forceSelection : true,
									editable : false,
									displayField : 'carTypeName',
									valueField : 'carTypeName',
									name : 'useCarType',
									enableKeyEvents : true,
								 	listeners:{
			    						keyup:function(textField, e){
			    							if(e.getKey()==13){
			    								Ext.getCmp('dicCarDo').focus(true,true);
			    							}
			    						}
								 	}
								},{
									xtype : 'combo',
									id:'dicCarDo',
									triggerAction : 'all',
									store : carDoStore,
									mode:'local',
									width:200,
									fieldLabel:'车辆用途<span style="color:red">*</span>',
									allowBlank : false,
									blankText:'车辆用途不能为空！',
									forceSelection : true,
									editable : false,
									displayField : 'carDoName',//显示值，与fields对应
									valueField : 'carDoName',//value值，与fields对应
									name : 'rentCarResult',
									enableKeyEvents : true,
								 	listeners:{
			    						keyup:function(textField, e){
			    							if(e.getKey()==13){
			    								Ext.getCmp('fightMainNo').focus(true,true);
			    							}
			    						}
								 	}
								},
								{xtype:'numberfield',fieldLabel:'总票数',name:'totalTicket',maxValue:99999.99,width:200,readOnly:true,id:'totalTicket'},
								{xtype:'numberfield',fieldLabel:'总重量',name:'totalWeight',maxValue:99999.99,width:200,readOnly:true,id:'totalWeight'},
								{xtype:'numberfield',fieldLabel:'车次号',name:'routeNumber',readOnly:true,id:'routeNumber',width:200}
    							 ]
    						}]
						},
						{
							layout:'column',
							items:[{
									layout:'form',
							 		columnWidth:.33,
							 		labelWidth:70,
							 		items:[
							 				{xtype:'textfield',fieldLabel:'主单号<span style="color:red">*</span>',maxLength:100,
							 					id:'fightMainNo',
							 				//allowBlank:false,
							 				//blankText:'主单号不能为空!',
							 					enableKeyEvents:true,
							 					listeners : {
										 		keypress:function(textField, e){
									                     if(e.getKey() == 13){
									                     	setStartCity(textField);
									                     	Ext.getCmp('pieceid').focus(true,true);
									                  }
										 			},change:function(textField,e){
										 				setStartCity(textField);
										 			}
								 				}
							 				}
							 			]
									},{
										layout:'form',
										columnWidth:.33,
										labelWidth:50,
								 		items:[
								 				{xtype:'numberfield',id:'pieceid',fieldLabel:'件数',maxLength:5,
									 				enableKeyEvents:true,
								 					listeners : {
											 		keypress:function(textField, e){
										                     if(e.getKey() == 13){
										                     	Ext.getCmp('weight').focus(true,true);
										                  }
											 			}
									 				}
								 				}
								 			]
									},{
										layout:'form',
										columnWidth:.33,
										labelWidth:50,
										items:[{
											xtype:'numberfield',
											id:'weight',
											fieldLabel:'重量<span style="color:red">*</span>',
											minValue:0.1,
											maxValue:99999.99,
											maxLength:8,
											enableKeyEvents:true,
								 			listeners : {
											 	keypress:function(textField, e){
										              if(e.getKey() == 13){
										                    Ext.getCmp('goodsName').focus(true,true);
										              }
											 	}
									 		}
										}]
									},{
										
									}
								]
						},{
							layout:'column',
							items:[{
								layout:'form',
							 	columnWidth:.33,
							 	labelWidth:70,
							 	items:[{
							 		xtype:'combo',
							 		fieldLabel:'货物类型<span style="color:red">*</span>',
							 		name:'goodsName',
							 		store:goodsTypeStore,
							 		triggerAction : 'all',
							 		forceSelection : true,
							 		valueField:'goodsType',
							 		displayField:'goodsType',
							 		width:120,
							 		id:'goodsName',
							 		enableKeyEvents:true,
							 		listeners:{
							 			  keypress:function(textField, e){
						                     if(e.getKey() == 13){
						                     	Ext.getCmp('comboflight').focus(true,true);
						                  }
							 			}

							 		}
							 	}]
							},{
								layout:'form',
							 	columnWidth:.33,
							 	labelWidth:50,
							 	items:[{
							 		xtype : "textfield",
    								fieldLabel:'始发站',
			    					id:'comboflight',
    								hiddenName : 'startCity',
			    					name : 'checkItemsflight',
			    					enableKeyEvents:true,
				 					listeners : {
							 			keypress:function(textField, e){
						                     if(e.getKey() == 13){
						                     	Ext.getCmp('addbtn').focus(true,true);
						                  }
							 			}
					 				}
							 	}]
							},{
								layout:'form',
								columnWidth:.1,
						 		items:[
						 				{xtype:'button',
						 				 id:'addbtn',
						 				 text:'添加',
						 				 handler:function(){
						 				 	if(form.getForm().isValid()){
						 					if(Ext.getCmp("fightMainNo").getValue()!=''&&Ext.getCmp('weight').getValue()!=''&&Ext.getCmp('goodsName').getValue()!=''){
						 						
						 					var flag=true;
						 					var fightMainNo=Ext.getCmp("fightMainNo").getValue();
						 					var flightNo=Ext.getCmp("comboflight").getValue();
						 					carArriveDetailStore.load({
												params:{
						 							flightMainNo:fightMainNo,
						 							//flightNo:flightNo,
						 							limit:200
												}
						 					});
						 					carArriveDetailStore.on('load',function(){
						 						var fmnval=Ext.getCmp("fightMainNo").getValue();
						 						var piece=Ext.getCmp("pieceid").getValue();
						 						var yweight=Ext.getCmp("weight").getValue();
						 						inputWeight=yweight;
						 						var detailPiece=0;
						 						var detailWeight=0;
						 						//判断是是否重复操作
												for(var i=0;i<totalStore.getCount();i++){
													if(fmnval==totalStore.getAt(i).get("flightMainNo")){
														flag=false;
														break;
													}else{
														flag=true;
													}
												}								 						
						 						if(flag){
						 							var store1=new Ext.data.Record.create(fiInterField);
						 							var fiRecord=new store1();
						 							fiRecord.set('flightMainNo',fmnval);
						 							fiRecord.set('flightWeight',yweight);
						 							if(piece==''){
						 								var tpiece=0;
						 								for(var i=0;i<carArriveDetailStore.getCount();i++){
						 									tpiece+=carArriveDetailStore.getAt(i).get('piece');
						 								}
						 								fiRecord.set('flightPiece',tpiece);
						 							}
						 							else{
						 								fiRecord.set('flightPiece',piece);
						 							}
						 							fiRecord.set('customerId',Ext.getCmp('comboDisId').getValue());
						 							fiRecord.set('customerName',Ext.get('comboDisId').dom.value);
						 							fiRecord.set('startcity',Ext.getCmp('comboflight').getValue());
						 							fiRecord.set('goodsName',Ext.getCmp('goodsName').getValue());
						 							fiInterStore.add(fiRecord);
						 							
						 							var detailCount=carArriveDetailStore.getTotalCount();
						 							//如果没有数据，则是无传真的货物
						 							if(detailCount==0){
						 								if(Ext.getCmp('pieceid').getValue()==''){
						 									Ext.Msg.alert(alertTitle,'无传真货物件数不能为空',onfocus);
						 									function onfocus(){
						 										Ext.getCmp('pieceid').focus(true,true);
						 										return false;
						 									}
						 								}else{
						 									var store=new Ext.data.Record.create(fields1);
							 								 var flightNo=Ext.getCmp('comboflight').getValue();
							 								 var noFaxRecord=new store();
							 								 noFaxRecord.set("cusId",0);
							 								 noFaxRecord.set("dno",0);
							 								 noFaxRecord.set("cpName","无传真");
							 								 noFaxRecord.set("subNo",0);
							 								 noFaxRecord.set("consignee","无传真");
							 								 noFaxRecord.set("flightMainNo",fmnval);
							 								 noFaxRecord.set("distributionMode",'');
							 								 noFaxRecord.set("takeMode","无传真");
							 								 noFaxRecord.set("addr","无传真");
							 								 noFaxRecord.set("piece",Ext.getCmp('pieceid').getValue());
							 								 if(flightNo!=null||flightNo!=''){
							 								 	noFaxRecord.set("flightNo",Ext.get('comboflight').dom.value);
							 								 }
							 								 else{
							 								 	noFaxRecord.set("flightNo",null);
							 								 }
							 								 noFaxRecord.set("cusWeight",Ext.getCmp('weight').getValue());
							 								 noFaxRecord.set("isException",1);
							 								 noFaxRecord.set("flightMainNo",fmnval);
							 								//add start 无传真
							 								 carArriveDetailStore.add(noFaxRecord);
							 								 
							 								Ext.getCmp('fightMainNo').setValue('');
										 					Ext.getCmp('pieceid').setValue('');
										 					Ext.getCmp('weight').setValue('');
										 					Ext.getCmp('comboflight').setValue('');
						 								}
						 							}else{
						 								if(detailCount>200){
						 									Ext.Msg.alert(alertTitle,'不能超过200条数据！');
						 									return;
						 								}
						 								for(var i=0;i<detailCount;i++){
							 								detailPiece=detailPiece+Number(carArriveDetailStore.getAt(i).get("piece"));
							 								detailWeight=detailWeight+Number(carArriveDetailStore.getAt(i).get("cusWeight"));
							 							}
						 								//输入的件数和主单件数不同，则是异常
						 								if(piece!=""){
						 									if(piece!=detailPiece){
								 								for(var i=0;i<detailCount;i++){
								 									carArriveDetailStore.getAt(i).set('isException',1);
								 								}
								 							}else{
								 								for(var i=0;i<detailCount;i++){
								 									carArriveDetailStore.getAt(i).set('isException',0);
								 								}
								 							}
						 								}
						 								else{
						 									for(var i=0;i<detailCount;i++){
							 									carArriveDetailStore.getAt(i).set('isException',0);
							 								}
						 								}
						 								
						 								if(yweight!=''){
						 									if(yweight!=detailWeight){
						 										if(yweight>detailWeight){
						 											Ext.getCmp('showMsg').getEl().update('<span style="color:red">超重'+(yweight-detailWeight)+'kg！</span>');
						 										}else if(yweight<detailWeight){
						 											Ext.getCmp('showMsg').getEl().update('<span style="color:red">少重'+(detailWeight-yweight)+'kg！</span>');
						 										}else{
						 											Ext.getCmp('showMsg').getEl().update('');
						 										}
								 								for(var i=0;i<detailCount;i++){
								 									carArriveDetailStore.getAt(i).set('isException',1);
								 								}
								 							}else{
								 								Ext.getCmp('showMsg').getEl().update('');
								 								for(var i=0;i<detailCount;i++){
								 									carArriveDetailStore.getAt(i).set('isException',0);
								 								}
								 							}
						 								}
						 								
						 								Ext.getCmp('fightMainNo').setValue('');
										 			    Ext.getCmp('pieceid').setValue('');
										 				Ext.getCmp('weight').setValue('');
										 				Ext.getCmp('comboflight').setValue('');
						 							}
						 							var records=carArriveDetailStore.getRange();
						 							totalStore.add(records);
						 							var totalcount=totalStore.getCount();
						 							//为总件数、总票数、总重量赋值
						 							var totalPiece=0;
						 							var totalWeight=0;
							 						for(var i=0;i<totalcount;i++){
							 							var fmn=totalStore.getAt(i).get("fightMainNo");
							 							totalPiece=Math.round(totalPiece+Number(totalStore.getAt(i).get("piece")));
							 							totalWeight=Math.round(totalWeight+Number(totalStore.getAt(i).get("cusWeight")));
							 						}
							 						Ext.getCmp("totalPiece").setValue(totalPiece);
							 						Ext.getCmp("totalWeight").setValue(totalWeight);
							 						Ext.getCmp("totalTicket").setValue(totalcount);
							 						form.render();
							 						//Ext.getCmp('fightMainNo').setValue('');
										 			//Ext.getCmp('pieceid').setValue('');
										 			//Ext.getCmp('weight').setValue('');
										 			//Ext.getCmp('comboflight').setValue('');
										 			Ext.getCmp('fightMainNo').focus(true,true);
							 						return false;
						 						}else{
							 						Ext.Msg.alert(alertTitle, "请勿重复操作!");
							 						flag=true;
						 							return false;
						 						}
						 					});
						 				 }else{
						 				 	parent.Ext.Msg.alert(alertTitle,'主单号及重量和货物类型不能为空!',function(){Ext.getCmp('fightMainNo').focus(true,true);});
						 				 }
						 				 }
						 					}
						 				}
						 			]
							},
							{
								layout:'form',
								columnWidth:.1,
								items:[{
									xtype:'button',
					 				id:'delbtn',
					 				disabled:true,
					 				text:'删除',
					 				handler:function(){
					 					delTotalStore();
					 				}
								}]
							},
							{
								layout:'form',
								columnWidth:.1,
								items:[{
									xtype:'label',
									name:'showMsg',
									id:'showMsg'
								}]
							}]
						},{
							layout:'form',
							items:[
								addArriveRecord
							]
						}
					]
    		});
    		
    		var win = new Ext.Window({
			title : '手动添加交接单',
			width : 650,
			height: 460,
			closeAction : 'hide',
			plain : true,
			modal : true,
			items : form,
			buttonAlign : "center",
			buttons : [{
				text : "保存",
				iconCls : 'save',
				handler : function() {
					var flag=false;
					if (form.getForm().isValid()) {
						var dno='';
						for(var i=0;i<totalStore.getCount();i++){
							var record=totalStore.getAt(i);
							dno+=record.get("dno");
							if(i!=(totalStore.getCount()-1)){
								dno+=',';
							}
							if((i+1)==totalStore.getCount()){
								Ext.Ajax.request({
									url:sysPath+'/stock/oprOvermemoDetailAction!findOverDetail.action',
									async:false,
									params:{
										limit:pageSize,
										dno:dno
									},
									success:function(resp){
										 var respText = Ext.util.JSON.decode(resp.responseText);
										 if(respText.length>0){
										 	Ext.Msg.alert(alertTitle,'明细列表中包含已新增的配送单，请勿重复添加！');
										 	return false;
										 }else{
										 	var stores="";
										for(var i=0;i<totalStore.getCount();i++){
											var record=totalStore.getAt(i);
											
											if(i==0){
												stores+="oprDetails["+i+"].dno="+record.get("dno")
												+"&oprDetails["+i+"].flightMainNo="+record.get("flightMainNo")
												+"&oprDetails["+i+"].subNo="+record.get("subNo")
												+"&oprDetails["+i+"].consignee="+record.get("consignee")
												+"&oprDetails["+i+"].cpName="+record.get("cpName")
												+"&oprDetails["+i+"].cusId="+record.get("cusId")
												+"&oprDetails["+i+"].distributionMode="+record.get("distributionMode")
												+"&oprDetails["+i+"].takeMode="+record.get("takeMode")
												+"&oprDetails["+i+"].addr="+record.get("addr")
												+"&oprDetails["+i+"].flightNo="+record.get("flightNo")
												+"&oprDetails["+i+"].status=0"
												+"&oprDetails["+i+"].piece="+record.get("piece")
												+"&oprDetails["+i+"].weight="+record.get("cusWeight")
												+"&oprDetails["+i+"].isException="+record.get("isException")+"";
											}else{
												stores+="&oprDetails["+i+"].dno="+record.get("dno")
												+"&oprDetails["+i+"].flightMainNo="+record.get("flightMainNo")
												+"&oprDetails["+i+"].subNo="+record.get("subNo")
												+"&oprDetails["+i+"].consignee="+record.get("consignee")
												+"&oprDetails["+i+"].cpName="+record.get("cpName")
												+"&oprDetails["+i+"].cusId="+record.get("cusId")
												+"&oprDetails["+i+"].distributionMode="+record.get("distributionMode")
												+"&oprDetails["+i+"].takeMode="+record.get("takeMode")
												+"&oprDetails["+i+"].addr="+record.get("addr")
												+"&oprDetails["+i+"].flightNo="+record.get("flightNo")
												+"&oprDetails["+i+"].status=0"
												+"&oprDetails["+i+"].piece="+record.get("piece")
												+"&oprDetails["+i+"].weight="+record.get("cusWeight")
												+"&oprDetails["+i+"].isException="+record.get("isException")+"";
											}
										}
										var fiInter="";
										for(var i=0;i<fiInterStore.getCount();i++){
											var record=fiInterStore.getAt(i);
											if(i==0){
												fiInter+="fiList["+i+"].flightMainNo="+record.get("flightMainNo")
												+"&fiList["+i+"].flightPiece="+record.get("flightPiece")
												+"&fiList["+i+"].flightWeight="+record.get("flightWeight")
												+"&fiList["+i+"].customerId="+record.get("customerId")
												+"&fiList["+i+"].customerName="+record.get("customerName")
												+"&fiList["+i+"].goodsType="+record.get("goodsName")
												+"&fiList["+i+"].startcity="+record.get("startcity")+""
											}else{
												fiInter+="&fiList["+i+"].flightMainNo="+record.get("flightMainNo")
												+"&fiList["+i+"].flightPiece="+record.get("flightPiece")
												+"&fiList["+i+"].flightWeight="+record.get("flightWeight")
												+"&fiList["+i+"].customerId="+record.get("customerId")
												+"&fiList["+i+"].customerName="+record.get("customerName")
												+"&fiList["+i+"].goodsType="+record.get("goodsName")
												+"&fiList["+i+"].startcity="+record.get("startcity")+""
											}
										}
										form.getForm().submit({
											url : sysPath + "/stock/oprOvermemoAction!handAddConfirm.action",
											params:stores+"&"+fiInter,
											waitMsg : '正在保存数据...',
											success : function(form, action) {
												win.hide();
												Ext.Msg.alert(alertTitle,"保存成功!");
												carArriveStore.reload({
													params : {
																start : 0,
																limit : pageSize,
																privilege:privilege
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
								});
							}
						}
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
				});
		win.show();
    	}
    	//查询
    	function searCarArrive(){
    		var carno=Ext.getCmp("combocar").getRawValue();
    		var startDepart=Ext.getCmp("combodepart").getValue();
    		var overType=Ext.getCmp("comboovermemo").getValue();
    		var overNo=Ext.getCmp("overmemono").getValue();
    		var combo=Ext.getCmp("comboCheckItems");
    		var searchStatus = Ext.getCmp('searchStatus').getValue();
    		if(combo.getValue()=='LIKES_startTime'){
    			var start=Ext.get("datefieldstart").dom.value;
    			var end=Ext.get("datefieldend").dom.value;
    			Ext.apply(carArriveStore.baseParams, {
   						filter_GED_startTime : start,
   						filter_LED_startTime : end,
   						filter_GED_endTime :'',
   						filter_LED_endTime :'',
   						filter_GED_unloadEndTime :'',
   						filter_LED_unloadEndTime :'',
   						checkItems :'',
   						itemsValue :''
   					});

    		}else if(combo.getValue() == 'LIKES_endTime'){
    			var start=Ext.get("datefieldstart").dom.value;
    			var end=Ext.get("datefieldend").dom.value;
    			Ext.apply(carArriveStore.baseParams, {
   						filter_GED_endTime : start,
   						filter_LED_endTime : end,
   						filter_GED_startTime :'',
   						filter_LED_startTime :'',
   						filter_GED_unloadEndTime :'',
   						filter_LED_unloadEndTime :'',
   						checkItems :'',
   						itemsValue :''
   					});
    		}else if(combo.getValue() == 'LIKES_unloadEndTime'){
    			var start=Ext.get("datefieldstart").dom.value;
    			var end=Ext.get("datefieldend").dom.value;
    			Ext.apply(carArriveStore.baseParams, {
   						filter_GED_unloadEndTime: start,
   						filter_LED_unloadEndTime: end,
   						filter_GED_startTime :'',
   						filter_LED_startTime :'',
   						filter_GED_endTime :'',
   						filter_LED_endTime :'',
   						checkItems :'',
   						itemsValue :''
   					});
    		}else if(combo.getValue() == 'EQL_status'){
    			Ext.apply(carArriveStore.baseParams, {
   						checkItems : Ext.get("checkItems").dom.value,
   						itemsValue : Ext.get("combostaid").dom.value,
   						filter_GED_startTime :'',
   						filter_LED_startTime :'',
   						filter_GED_endTime:'',
   						filter_LED_endTime:'',
   						filter_GED_unloadEndTime:'',
   						filter_LED_unloadEndTime:''
   					});
    		}else{
    			Ext.apply(carArriveStore.baseParams, {
   						checkItems : Ext.get("checkItems").dom.value,
   						itemsValue : Ext.get("searchContent").dom.value,
   						filter_GED_startTime :'',
   						filter_LED_startTime:'',
   						filter_GED_endTime:'',
   						filter_LED_endTime:'',
   						filter_GED_unloadEndTime:'',
   						filter_LED_unloadEndTime:''
   					});
    		}
    		Ext.apply(carArriveStore.baseParams, {
   						start : 0,
    					limit : pageSize,
    					privilege:privilege,
    					filter_EQL_status:searchStatus,
    					filter_EQS_carCode:carno,
    					filter_EQS_startDepartName:startDepart,
    					filter_EQS_overmemoType:overType,
    					filter_EQL_id:overNo
   			});
    		carArriveStore.load();
    	}
    	//是否能做到车确认操作
    	function carArriveConfirm(_record){
			Ext.Ajax.request( {
				url : sysPath + "/stock/oprOvermemoAction!isCarArriveConfirm.action",
				params : {
					carArriveIds : _record[0].data.id,
					routeNumber:_record[0].data.routeNumber,
					dno:_record[0].data.dno,
					privilege : privilege
				},
				success : function(resp) {
					var respText = Ext.util.JSON.decode(resp.responseText);
					if(!respText.success){
						Ext.Msg.alert(alertTitle,respText.msg);
					}else{
						carArrive();
					}
				}
			});
    		
    	}
    	//判断是否能打印清单
    	function isprintMsg(_record){
    		Ext.Ajax.request( {
				url : sysPath + "/stock/oprOvermemoAction!isPrintMsg.action",
				params : {
					id : _record[0].data.id,
					routeNumber:_record[0].data.routeNumber,
					privilege : privilege
				},
				success : function(resp) {
					var respText = Ext.util.JSON.decode(resp.responseText);
					if(!respText.success){
						Ext.Msg.alert(alertTitle,respText.msg);
					}else{
						parent.print('4',{print_routeNumber:_record[0].data.routeNumber});
					}
				}
			});
    	}
    	
    	
    	//卸车结束
    	function loadEndCar(_record){
    		Ext.Ajax.request( {
				url : sysPath + "/stock/oprOvermemoAction!carEndUpload.action",
				params : {
					id : _record[0].data.id,
					routeNumber:_record[0].data.routeNumber
				},
				success : function(resp) {
					searCarArrive();
					Ext.Msg.alert(alertTitle,"结束卸车");
				},
				failure : function(_form, action) {
					Ext.MessageBox.alert(alertTitle,"卸车结束失败");
				}
			});
    	}
    	//到车确认
    	function carArrive(){
    		var orderdata=[
	    		['sub_no','分单号'],
	    		['consignee','收货人'],
	    		['addr','收货人地址'],
	    		['piece','件数'],
	    		['weight','重量']
	    		];
	    	var myReader = new Ext.data.ArrayReader({}, [
	    	{name:'orderId'},
			{name: 'orderName'}
			]);
			 var orderStore=new Ext.data.Store({
				data: orderdata,
				reader: myReader
			});
			 
			 var orderdata1=[
				['STORAGE_AREA','库存区域'],
	    		['cp_name','代理公司'],
	    		['FLIGHT_MAIN_NO','主单号']
	    		
	    		];
	    	var myReader1 = new Ext.data.ArrayReader({}, [
	    	{name:'orderId'},
			{name: 'orderName'}
			]);
			 var orderStore1=new Ext.data.Store({
				data: orderdata1,
				reader: myReader1
			});
			 var orderGridpanl = new parent.Ext.grid.GridPanel( {
				id : 'myrecordGrid2',
				height : 300,
				width : Ext.lib.Dom.getViewWidth(),
				autoScroll : true,
				autoExpandColumn : 1,
				frame : false,
				loadMask : true,
				stripeRows : true,
				viewConfig : {
					forceFit : true,
					scrollOffset : 0
				},
				cm : new parent.Ext.grid.ColumnModel( [
						new parent.Ext.grid.CheckboxSelectionModel(), {
							header : '排序字段',
							dataIndex : 'orderId',
							disabled : true,
							hidden : true
						}, {
							header : '排序字段',
							dataIndex : 'orderName'
						} ]),
				sm : new parent.Ext.grid.CheckboxSelectionModel(),
				ds : orderStore
			});
			  var orderGridpanl2 = new parent.Ext.grid.GridPanel( {
				id : 'myrecordGri',
				height : 300,
				width : Ext.lib.Dom.getViewWidth(),
				autoScroll : true,
				autoExpandColumn : 1,
				frame : false,
				loadMask : true,
				stripeRows : true,
				viewConfig : {
					forceFit : true,
					scrollOffset : 0
				},
				cm : new parent.Ext.grid.ColumnModel( [
						new parent.Ext.grid.CheckboxSelectionModel(), {
							header : '排序字段',
							dataIndex : 'orderId',
							disabled : true,
							hidden : true
						}, {
							header : '排序字段',
							dataIndex : 'orderName'
						} ]),
				sm : new parent.Ext.grid.CheckboxSelectionModel(),
				ds : orderStore1
			});
    		var form = new parent.Ext.form.FormPanel({
					frame : true,
					border : false,
					bodyStyle : 'padding:1px 1px 1px',
					labelWidth : 70,
					labelAlign :'right',
					width : 320,
					items:[{
						 layout : 'column',
						 items:[{
							 layout:'column',
							 columnWidth:.4,
							 items:[
								 orderGridpanl
								]
							 },{
    						 layout:'column',
    						 buttonAlign:'center',
    						 columnWidth:.2,
    						 items:[
    							 {html : '<pre><p> </p><p> </p><p> </p><p> </p><p> </p><p> </p><p> </p><p> </p></pre>'},
    							 {xtype:'button',
    							  text:'==>>',
    							  width:50,
    							  handler:function(){
    								 var order1=orderGridpanl.getSelectionModel().getSelections();
    								 for(var i=0;i<order1.length;i++){
    									 orderStore.remove(order1[i]);
    								 }
    								 orderStore1.add(order1);
    								 form.render();
    							  } 
    							  },
    							  {html:'<pre><p> </p></pre>'},
    							  {xtype:'button',
    							  text:'<<==',
    							  width:50,
    							  handler:function(){
    								  var order2=orderGridpanl2.getSelectionModel().getSelections();
    								  for(var i=0;i<order2.length;i++){
    									  orderStore1.remove(order2[i]);
    								  }
    								  orderStore.add(order2);
    								  form.render();
    							  } 
    							  },
    							  {xtype:'hidden',name:'carArriveIds',id:'carArriveIds'},
    							  {xtype:'hidden',name:'orderbyName',id:'orderbyName'},
    							  {xtype:'hidden',name:'routeNumber',id:'routeNumber'}
    							 ]
    						},{
    						 layout:'column',
    						 columnWidth:.4,
    						 items:[
    							orderGridpanl2
    							 ]
    						}]
						}]
    		});
    		var win = new parent.Ext.Window({
			title : '添加排序信息',
			width : 350,
			closeAction : 'hide',
			plain : true,
			modal : true,
			items : form,
			buttonAlign : "center",
			buttons : [{
				text : "保存",
				iconCls : 'save',
				handler : function() {
				    var _record=recordGrid.getSelectionModel().getSelections();
				    orderGridpanl2.getSelectionModel().selectAll();
				    var record=orderGridpanl2.getSelectionModel().getSelections();
				    var orderName="";
				    for(var i=0;i<record.length;i++){
				    	orderName=orderName+record[i].data.orderId;
				    	if(i!=(record.length-1)){
				    		orderName+=',';
				    	}
				    }
				    parent.Ext.getCmp("carArriveIds").setValue(_record[0].data.id);
				    parent.Ext.getCmp("orderbyName").setValue(orderName);
				    parent.Ext.getCmp("routeNumber").setValue(_record[0].data.routeNumber);
					if (form.getForm().isValid()) {
						form.getForm().submit({
							url : sysPath + "/stock/oprOvermemoAction!carArriveConfirm.action",
							waitMsg : '正在保存数据...',
							success : function(form, action) {
								win.hide();
								//理货清单打印
						        parent.print('7',{print_overmemoId:_record[0].data.id});
								//Ext.Msg.alert(alertTitle,action.result.msg);
								carArriveStore.load();
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
				});
		win.show();
    	}
    	
    	//卸车开始
	function upload(_record){
		Ext.Ajax.request( {
				url : sysPath + "/stock/oprOvermemoAction!carUpload.action",
				params : {
					routeNumber:_record[0].data.routeNumber,
					privilege : privilege
				},
				success : function(resp) {
					var respText = Ext.util.JSON.decode(resp.responseText);
					if(respText.success){
						Ext.Msg.alert(alertTitle,respText.msg);
						carArriveStore.reload({
						params : {
							start : 0,
							limit : pageSize,
							privilege : privilege
						}
						});
					}else{
						Ext.Msg.alert(alertTitle,respText.msg);
					}
					
				}
		});
	}
	//到车确认撤销
	function uploadreturn(_record){
		var ids=_record[0].data.id;
		Ext.Ajax.request( {
				url : sysPath + "/stock/oprOvermemoAction!carUploadReturn.action",
				params : {
					routeNumber:_record[0].data.routeNumber,
					carArriveIds : ids,
					privilege : privilege
				},
				success : function(resp) {
					var respText = Ext.util.JSON.decode(resp.responseText);
					if(respText.success){
						Ext.Msg.alert(alertTitle,respText.msg);
						carArriveStore.reload({
						params : {
							start : 0,
							limit : pageSize,
							privilege : privilege
						}
					});
					}else{
						Ext.Msg.alert(alertTitle,respText.msg);
					}
				}
		});
	}
});
		//删除
    	function delTotalStore(){
    		Ext.Msg.confirm(alertTitle, "确定要删除吗？", function(btnYes) {
		    	if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
		    			var records=addArriveRecord.getSelectionModel().getSelections();
		    			//var yweight=Ext.getCmp("weight").getValue();
		    		    var form=Ext.getCmp("addForm");
				    	//var totalStore=Ext.StoreMgr.get("totalStore");
				    	/*
						if(totalStore.getCount()==1){
							totalStore.removeAt(0); 
						}else{
							totalStore.removeAt(rowIndex); 
						}*/
				    	//var carArriveDetailStore=Ext.StoreMgr.get("carArriveDetailStore");
				    	totalStore.remove(records);
				    	var detailCount=carArriveDetailStore.getTotalCount();
				    	var totalcount=totalStore.getCount();
						var totalPiece=0;
						var totalWeight=0;
				    	//为总件数、总票数、总重量赋值
						for(var i=0;i<totalcount;i++){
							var fmn=totalStore.getAt(i).get("fightMainNo");
							totalPiece=Math.round(totalPiece+Number(totalStore.getAt(i).get("piece")));
							totalWeight=Math.round(totalWeight+Number(totalStore.getAt(i).get("cusWeight")));
							
						}
						if(inputWeight!=''){
							if(inputWeight!=totalWeight){
								if(inputWeight>totalWeight){
									Ext.getCmp('showMsg').getEl().update('<span style="color:red">超重'+(inputWeight-totalWeight)+'kg！</span>');
								}else if(inputWeight<totalWeight){
									Ext.getCmp('showMsg').getEl().update('<span style="color:red">少重'+(totalWeight-inputWeight)+'kg！</span>');
								}else{
									Ext.getCmp('showMsg').getEl().update('');
								}
								
								for(var i=0;i<detailCount;i++){
 									carArriveDetailStore.getAt(i).set('isException',1);
 								}
 							}else{
 								Ext.getCmp('showMsg').getEl().update('');
 								for(var i=0;i<detailCount;i++){
 									carArriveDetailStore.getAt(i).set('isException',0);
 								}
 							}
						}
						Ext.getCmp("totalPiece").setValue(totalPiece);
						Ext.getCmp("totalWeight").setValue(totalWeight);
						Ext.getCmp("totalTicket").setValue(totalcount);
						form.render();
			   }
		  })
       }
       //设置始发站信息
       function setStartCity(textField){
       		Ext.Ajax.request({
         		url:sysPath+'/fax/oprFaxInAction!ralaList.action',
				params:{
					filter_EQS_flightMainNo:textField.getValue(),
					privilege:68
				},success:function(resp){
					var respText = Ext.util.JSON.decode(resp.responseText);
					if(respText.result.length>0){
						var flightNum=respText.result[0].flightNo;
						Ext.Ajax.request({
							url:sysPath+'/sys/basFlightAction!ralaList.action',
							params:{
								filter_EQS_flightNumber:flightNum,
								privilege:62
							},success:function(resp1){
								var respText1 = Ext.util.JSON.decode(resp1.responseText);
								if(respText1.result.length>0){
									Ext.getCmp('comboflight').setValue(respText1.result[0].endCity);
								}
							}
						});
					}
				}
          });
       }
function hideCombobox(cid){
	Ext.getCmp(cid).hide();
	Ext.getCmp(cid).setDisabled(true);
	Ext.getCmp(cid).getEl().up('.x-form-item').setDisplayed(false);
}
function showCombobox(cid){
	Ext.getCmp(cid).show();
	Ext.getCmp(cid).setDisabled(false);
	Ext.getCmp(cid).getEl().up('.x-form-item').setDisplayed(true);
}
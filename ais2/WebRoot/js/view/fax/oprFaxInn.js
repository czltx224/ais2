//提货方式Store 不从数据字典取值 如要修改则直接加入或者修改值即可
var takeModeStore=[
	['市内送货','市内送货'],
	['市内自提','市内自提'],
	['机场自提','机场自提']
];
//业务部门ID 空港配送深圳配送中心
var szDepartId = 492;
var privilege=68;
var fiveWidth=80;
var fourWidth=90;
var commentWidth=110;
var summary = new Ext.ux.grid.GridSummary();
var addFeeRecordStore,requestStore,faxStore;
Ext.onReady(function(){
	var faxfields=[
		{name:'dno'},
		{name:'cusId'},
		{name:'cpName'},
		{name:'flightNo'},
		{name:'flightDate'},
		{name:'flightTime'},
		{name:'trafficMode'},
		{name:'flightMainNo'},
		{name:'subNo'},
		{name:'distributionMode'},
		{name:'takeMode'},
		{name:'receiptType'},
		{name:'consignee'},
		{name:'consigneeTel'},
		{name:'city'},
		{name:'town'},
		{name:'street'},
		{name:'addr'},
		{name:'piece'},
		{name:'cqWeight'},
		{name:'cusWeight'},
		{name:'bulk'},
		{name:'inDepartId'},
		{name:'inDepart'},
		{name:'curDepartId'},
		{name:'curDepart'},
		{name:'endDepartId'},
		{name:'endDepart'},
		{name:'distributionDepartId'},
		{name:'distributionDepart'},
		{name:'greenChannel'},
		{name:'urgentService'},
		{name:'wait'},
		{name:'sonderzug'},
		{name:'carType'},
		{name:'roadType'},
		{name:'remark'},
		{name:'status'},
		{name:'barCode'},
		{name:'paymentCollection'},
		{name:'traFee'},
		{name:'traFeeRate'},
		{name:'cpRate'},
		{name:'cpFee'},
		{name:'consigneeRate'},
		{name:'consigneeFee'},
		{name:'whoCash'},
		{name:'faxMainId'},
		{name:'customerService'},
		{name:'cusValueAddFee'},
		{name:'cpValueAddFee'},
		{name:'goodsStatus'},
		{name:'declaredValue'},
		{name:'goods'},
		{name:'valuationType'},
		{name:'areaType'},
		{name:'sonderzugPrice'},
		{name:'normTraRate'},
		{name:'normCpRate'},
		{name:'normConsigneeRate'},
		{name:'normSonderzugPrice'},
		{name:'cusDepartName'},
		{name:'cusDepartId'},
		{name:'goWhere',mapping:'gowhere'},
		{name:'goWhereId'},
		{name:'cpSonderzugPrice'}
		];
	faxStore=new Ext.data.Store({
		storeId:"faxStore",
		proxy:new Ext.data.HttpProxy({url:sysPath+"/fax/oprFaxInAction!ralaList.action"}),
		baseParams:{limit:pageSize,privilege:privilege},
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },faxfields)
	});
	var secod=0;
	
	//航班号Store
	var flightStore=new Ext.data.Store({
		storeId:"flightStore",
		proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/basFlightAction!ralaList.action"}),
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
	//配送方式Store
	var devModeStore=new Ext.data.Store({
		storeId:"devModeStore",
		baseParams:{filter_EQL_basDictionaryId:4,privilege:16},
		proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/dictionaryAction!ralaList.action"}),
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
        	{name:'id'},
        	{name:'develpMode',mapping:'typeName'}
        	])
	});

	//提送付款方Store
	var payerStore=new Ext.data.Store({
		storeId:"payerStore",
		baseParams:{filter_EQL_basDictionaryId:17,privilege:16},
		proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/dictionaryAction!ralaList.action"}),
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
        	{name:'id'},
        	{name:'whoCash',mapping:'typeName'}
        	])
	});
//	//提货方式Store
//	var takeModeStore=new Ext.data.Store({
//		storeId:"payerStore",
//		baseParams:{filter_EQL_basDictionaryId:14,privilege:16},
//		proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/dictionaryAction!ralaList.action"}),
//		reader:new Ext.data.JsonReader({
//                    root:'result',
//                    totalProperty:'totalCount'
//        },[
//        	{name:'id'},
//        	{name:'takeMode',mapping:'typeName'}
//        	])
//	});
	//地址类型Sotre
	var addrStore=new Ext.data.Store({
		storeId:"addrStore",
		proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/dictionaryAction!ralaList.action"}),
		baseParams:{filter_EQL_basDictionaryId:3,privilege:16},
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
        	{name:'id'},
        	{name:'areaType',mapping:'typeName'}
        	])
	});
	
	//回单类型Store
	var receiptTypeStore=new Ext.data.Store({
		storeId:"receiptTypeStore",
		proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/dictionaryAction!ralaList.action"}),
		baseParams:{filter_EQL_basDictionaryId:15,privilege:16},
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
        	{name:'id'},
        	{name:'receiptType',mapping:'typeName'}
        	])
	});

	//运输方式Store
	var trafficModeStore=new Ext.data.Store({
		storeId:"trafficModeStore",
		proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/dictionaryAction!ralaList.action"}),
		baseParams:{filter_EQL_basDictionaryId:18,privilege:16},
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
        	{name:'id'},
        	{name:'trafficMode',mapping:'typeName'}
        	])
	});

	//客商Store
	var cusStore=new Ext.data.Store({
		storeId:"cusStore",
		baseParams:{privilege:61,filter_EQS_custprop:'发货代理',filter_EQL_status:1},
		proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/customerAction!ralaList.action"}),
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
        	{name:'id'},
        	{name:'cusName'}
        	])
	});
	//付款方式Store
	var payMethodStore=new Ext.data.Store({
		storeId:"payMethodStore",
		proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/dictionaryAction!ralaList.action"}),
		baseParams:{filter_EQL_basDictionaryId:19,privilege:16},
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
        	{name:'id'},
        	{name:'payMethod',mapping:'typeName'}
        	])
	});
	//计费方式Store
	var chargeModeStore=new Ext.data.Store({
		storeId:"chargeModeStore",
		proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/dictionaryAction!ralaList.action"}),
		baseParams:{filter_EQL_basDictionaryId:20,privilege:16},
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
        	{name:'id'},
        	{name:'chargeMode',mapping:'typeName'}
        	])
	});
	//专车车型Store
	var carTypeStore=new Ext.data.Store({
		storeId:"carTypeStore",
		proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/dictionaryAction!ralaList.action"}),
		baseParams:{filter_EQL_basDictionaryId:21,privilege:16},
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
        	{name:'id'},
        	{name:'carType',mapping:'typeName'}
        	])
	});
	//专车路型Store
	var roadTypeStore=new Ext.data.Store({
		storeId:"roadTypeStore",
		proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/dictionaryAction!ralaList.action"}),
		baseParams:{filter_EQL_basDictionaryId:22,privilege:16},
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
        	{name:'id'},
        	{name:'roadType',mapping:'typeName'}
        	])
	});
	//增值服务费Store
	var addFeeStore=new Ext.data.Store({
		storeId:"roadTypeStore",
		proxy:new Ext.data.HttpProxy({url:sysPath+"/basvalueaddfee/basValueAddFeeAction!ralaList.action"}),
		baseParams:{limit:pageSize,privilege:25},
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
        	{name:'id'},
        	{name:'feeName'}
        	])
	});
	var areaFields=[
			{name:'id'},
            {name: 'areaName'},
            {name: 'areaType'},
            {name: 'distriDepartId'},
            {name: 'distriDepartName'},
            {name: 'endDepartId'},
            {name: 'endDepartName'},
            {name: 'develpMode'},
            {name:'parentId'},
            {name:'cusName'},
            {name:'parentName'},
            {name:'areaRank'}
		];
	//地区 市Store
	var areaCityStore=new Ext.data.Store({
		storeId:"areaCityStore",
		proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/basAreaAction!ralaList.action"}),
		baseParams:{privilege:55,limit:pageSize,filter_LIKES_areaRank:'市'},
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },areaFields)
	});
	
	//地区 区/县Store
	var areaTownStore=new Ext.data.Store({
		storeId:"areaTownStore",
		proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/basAreaAction!ralaList.action"}),
		baseParams:{privilege:55,limit:pageSize,filter_LIKES_areaRank:'区'},
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },areaFields)
	});
	//地区镇/街道Store
	var areaStreetStore=new Ext.data.Store({
		storeId:"areaStreetStore",
		proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/basAreaAction!ralaList.action"}),
		baseParams:{privilege:55,limit:pageSize,filter_LIKES_areaRank:'街道'},
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },areaFields)
	});
	//收货人信息Store
	var consigneeStore=new Ext.data.Store({
		storeId:"consigneeStore",
		proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/conInfoAction!findConInfo.action"}),
		reader:new Ext.data.JsonReader({
                  //  root:'result',
                  //  totalProperty:'totalCount'
        },[
        	{name:'id'},
        	{name:'consigneeName'},
        	{name:'consigneeTel'},
        	{name:'consigneeAddr'},
        	{name:'addrType'},
        	{name:'distributionMode'},
        	{name:'city'},
        	{name:'town'},
        	{name:'endDepart'},
        	{name:'endDepartId'},
        	{name:'street'},
        	{name:'goWhere'},
        	{name:'goWhereId'},
        	{name:'cusRecordId'},
        	{name:'distributionDepart'},
        	{name:'distributionDepartId'},
        	{name:'ts'}
        	])
	});
	//要求阶段Store
	var requestStageStore=new Ext.data.Store({
		storeId:"requestStageStore",
		proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/dictionaryAction!ralaList.action"}),
		baseParams:{filter_EQL_basDictionaryId:5,privilege:16},
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
        	{name:'id'},
        	{name:'requestStage',mapping:'typeName'}
        	])
	});
	//要求类型Store
	var requestTypeStore=new Ext.data.Store({
		storeId:"requestTypeStore",
		baseParams:{filter_EQL_basDictionaryId:6,privilege:16},
		proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/dictionaryAction!ralaList.action"}),
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
        	{name:'id'},
        	{name:'requestType',mapping:'typeName'}
        	])
	});
	//个性化要求要求类型Store
	/*
	var requestTypeMainStore=new Ext.data.Store({
		storeId:"requestTypeMainStore",
		baseParams:{limit:pageSize},
		proxy:new Ext.data.HttpProxy({url:sysPath+"/stock/requestTypeMainAction!list.action"}),
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
        	{name:'id'},
        	{name:'requestType',mapping:'requestType'}
        	])
	});
	//个性化要求要求明细Store
	var requestTypeDetailStore=new Ext.data.Store({
		storeId:"requestTypeDetailStore",
		baseParams:{limit:pageSize},
		proxy:new Ext.data.HttpProxy({url:sysPath+"/stock/requestTypeDetailAction!list.action"}),
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
        	{name:'id'},
        	{name:'requestItem',mapping:'requestItem'}
        	])
	});*/
	function newFax(){
		Ext.getCmp('addDetailBtn').setDisabled(false);
		Ext.getCmp('faxToolbar').items.item(1).setText('<b>新增明细</b>');
		form.getForm().reset();
		addFeeStore.removeAll();
		requestStore.removeAll();
		faxStore.removeAll();
		Ext.getCmp('flightcombo').setDisabled(false);
		Ext.getCmp('trafficModecombo').focus(true,true);
	}
	/*
	var runner = new Ext.util.TaskRunner(); 
	function addFax(){
		Ext.getCmp('trafficModecombo').focus(true,true);
		runner.stopAll();
		Ext.getCmp('timelabel').setText(0);
		addTime();
	}
	function addTime(){
		runner.start({      //任务被调用的方法 
          run: function (){ 
              var time=Ext.getCmp('timelabel').text;
			Ext.getCmp('timelabel').setText(Number(time)+1);
          }, 
          interval: 1000 //一秒执行一次 
       }); 
	}*/
	var tb=new Ext.Toolbar({
		width:Ext.lib.Dom.getViewWidth(),
		id:'faxToolbar',
		items:['&nbsp;&nbsp;',{
    			text : '<B>新增明细</B>',
    			id : 'addDetailBtn',
    			tooltip : '新增传真',
    			iconCls : 'groupAdd',
    			handler:function(){
    				saveFax();
    			}
    		},'-','&nbsp;&nbsp;', {
    			text : '<B>新开单(Ctrl+s)</B>',
    			id : 'newfaxbtn',
    			tooltip : '重新开单',
    			handler:function(){
    				newFax();
    			}
    		},'-','&nbsp;&nbsp;', {
    			text : '<B>定位</B>',
    			id : 'resetfaxbtn',
    			tooltip : '定位',
    			handler:function(){
    				location();
    			}
    		},'-',{
    			xtype:'label',
    			id:'showMsg',
    			width:380
    		},'-',{
    			xtype:'label',
    			id:'showEndDepart',
    			width:100
    		}]
	});
	function location(){
		var locationStore=new Ext.data.Store({
		storeId:"locationStore",
		proxy:new Ext.data.HttpProxy({url:sysPath+"/fax/oprFaxInAction!ralaList.action"}),
		baseParams:{limit:pageSize,privilege:privilege},
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },faxfields)
		});
		var locationRecord = new Ext.grid.GridPanel({
    				region : "center",
    				id : 'locationRecord',
    				height : 200,
    				width : 480,
    				autoScroll : true,
    				frame : false,
    				loadMask : true,
    				stripeRows : true,
    				viewConfig:{
    				   scrollOffset: 0
    				},
    				cm : new Ext.grid.ColumnModel([rowNum,
    						new Ext.grid.CheckboxSelectionModel(), 
    						{header : '',dataIndex:'flightMainNo',width:100}, 
    						{header : '配送单号',dataIndex : 'dno',width:100}, 
    						{header : '代理公司',dataIndex : 'cpName',width:150}
    				]),
    				sm : new Ext.grid.CheckboxSelectionModel(),
    				ds : locationStore,
    				tbar:['主单号:',{
    					xtype:'textfield',
    					id:'searchMainNo'
    				},'-','配送单号：',{
    					xtype:'textfield',
    					id:'searchdno'
    				},'-',{
    					text:'搜索',
    					iconCls: 'btnSearch',
    					handler:searchLocation
    				}],
    				listeners:{
    					rowdblclick:function(grid, rowindex, e){
    						var record=grid.getSelectionModel().getSelected();
    						locationSearch(record,win);
    					}
    				}
    	}); 
    	function searchLocation(){
    		locationStore.load({
    			params:{
    				filter_EQS_flightMainNo:Ext.getCmp('searchMainNo').getValue(),
    				filter_EQS_dno:Ext.getCmp('searchdno').getValue()
    			}
    		});
    	}
    	var form1 = new Ext.form.FormPanel({
    				id:'locationForm',
					frame : false,
					border : false,
					bodyStyle : 'padding:0px 0px 0px',
					labelAlign : "right",
					labelWidth : 80,
					width : 480,
					height:200 ,
					items:[locationRecord]
    	});
    	var win = new Ext.Window({
			title : '定位开单',
			width : 500,
			height:270,
			closeAction : 'hide',
			plain : true,
			modal : true,
			items : form1,
			buttonAlign : "center",
			buttons:[{
				text : "确定",
				handler : function() {
					var record = locationRecord.getSelectionModel().getSelections();
					if(record.length!=1){
						Ext.Msg.alert(alertTitle,'只能选择一条数据!');
						return;
					}else{
						locationSearch(record[0],win);
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
					form1.destroy();
				});
		win.show();
	}
	var rowNum = new Ext.grid.RowNumberer({
    		header : '序号',
    		width : 35,
    		sortable : true
    });
	var detailRecord = new Ext.grid.GridPanel({
    				id : 'myrecordGrid13',
    				height:Ext.lib.Dom.getViewHeight()-392,
    				width : Ext.lib.Dom.getViewWidth()-20,
    				frame : false,
    				loadMask : true,
    				stripeRows : true,
    				viewConfig:{
						autoScroll : true,
    				   scrollOffset: 0
    				},
    				plugins : [summary],
    				cm : new Ext.grid.ColumnModel([rowNum,
    						{header:'配送单号',dataIndex:'dno'},
    						{header:'主单号',dataIndex:'flightMainNo',width:80},
    						{header:'班次号',dataIndex:'flightNo',width:50},
    						{header:'班次日期',dataIndex:'flightDate',hidden:true},
    						{header:'班次时间',dataIndex:'flightTime',hidden:true,hideable: false},
    						{header:'运输方式',dataIndex:'trafficMode',width:50},
    						{header:'代理公司编号',dataIndex:'cusId',hidden:true},
    						{header:'代理公司',dataIndex:'cpName',width:80},
    						{header : '<span style="color:red">代收运费</span>',dataIndex:'paymentCollection',width:80},
    						{header : '分单号',dataIndex:'subNo',width:80}, 
    						{header : '件数',dataIndex:'piece',width:50},
    						{header : '重量KG',dataIndex:'cqWeight',width:80},
    						{header : '预付提送费',dataIndex:'cpFee',width:80},
    						{header : '到付提送费',dataIndex:'consigneeFee',width:80},
    						{header : '提货方式',dataIndex:'takeMode',width:80},
    						{header : '回单类型',dataIndex:'receiptType',width:80},
    						{header:'是否专车',dataIndex:'sonderzug',renderer:function(v){
    							if(v==0){
	    								return "否";
	    							}else if(v==1){
	    								return "是";
	    							}
    							}	
    						},
    						{header:'车型',dataIndex:'carType',width:60},
    						{header:'路型',dataIndex:'roadType',width:60},
    						{header:'预付专车费',dataIndex:'cpSonderzugPrice',width:50},
    						{header:'到付专车费',dataIndex:'sonderzugPrice',width:50},
    						{header : '收货人姓名',dataIndex:'consignee',width:80},
    						{header : '收货人电话',dataIndex:'consigneeTel',width:80}, 
    						{header : '所在市',dataIndex:'city',width:80},
    						{header : '区/县',dataIndex:'town',width:80},
    						{header : '镇/街道',dataIndex:'street',width:80}, 
    						{header : '地址类型',dataIndex:'areaType',width:80},
    						{header : '收货人地址',dataIndex:'addr',width:80},
    						{header : '配送方式',dataIndex:'distributionMode',width:80},
    						{header : '配送部门',dataIndex:'distributionDepart',width:80},
    						{header : '供应商',dataIndex:'goWhere',width:80},
    						{header : '货物名称',dataIndex:'goods',width:80},
    						{header : '计价方式',dataIndex:'valuationType',width:80},
    						{header : '计费重量KG',dataIndex:'cusWeight',width:80},
    						{header : '体积m3',dataIndex:'bulk',width:80},
    						{header : '提送付款方',dataIndex:'whoCash',width:80},
    						{header : '中转费率',dataIndex:'traFeeRate',width:80},
    						{header : '中转费',dataIndex:'traFee',width:80},
    						{header : '预付提送费率',dataIndex:'cpRate',width:80},
    						{header : '预付增值费',dataIndex:'cpValueAddFee',width:80},
    						{header : '到付提送费率',dataIndex:'consigneeRate',width:80},
    						{header : '到付增值费',dataIndex:'cusValueAddFee',width:80},
    						//{header : '送货要求',dataIndex:'goods',width:100},
    						{header : '备注',dataIndex:'remark',width:100},
    						{header : '当前部门编号',dataIndex:'curDepartId',hidden:true,hideable: false},
    						{header : '供应商ID',dataIndex:'goWhereId',hidden:true,hideable: false},
    						{header : '配送部门编号',dataIndex:'distributionDepartId',hidden:true,hideable: false},
    						{header : '配送部门',dataIndex:'distributionDepart',hidden:true,hideable: false},
    						{header : '终端部门编号',dataIndex:'endDepartId',hidden:true,hideable: false},
    						{header : '录单部门编号',dataIndex:'inDepartId',hidden:true,hideable: false}
    				]),
    				ds : faxStore,
    				listeners:{
    					rowdblclick:function(grid, rowindex, e){
    						Ext.Msg.confirm(alertTitle, "确定要修改吗？", function(btnYes) {
    							if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
    								var record=grid.getSelectionModel().getSelected();
    								Ext.Ajax.request({
    									url:sysPath+'/fax/oprFaxInAction!list.action',
    									params:{
    										filter_EQL_dno:record.data.dno,
    										privilege:privilege
    									},
    									success:function(resp){
    										var respText = Ext.util.JSON.decode(resp.responseText);
    										Ext.Ajax.request({
    											url:sysPath+'/stock/oprHistoryAction!list.action',
    											params:{
    												privilege:78,
    												filter_EQL_dno:record.data.dno
    											},
    											success:function(resp1){
    												var respText1 = Ext.util.JSON.decode(resp1.responseText);
    												if(respText.result[0].createName==userName&&respText1.result.length==1){
    													Ext.getCmp('dno').setValue(record.data.dno);
					    								Ext.getCmp('trafficModecombo').setValue(record.data.trafficMode);
					    								Ext.getCmp("cuscombo").setValue(record.data.cusId);
					    								Ext.getCmp("cpName").setValue(record.data.cpName);
					    								Ext.getCmp("cuscombo").setRawValue(record.data.cpName);
					    								Ext.getCmp('flightMain').setValue(record.data.flightMainNo);
					    								Ext.getCmp('flightcombo').setValue(record.data.flightNo);
					    								Ext.Ajax.request({
					    									url:sysPath+'/sys/basFlightAction!ralaList.action',
					    									params:{
					    										privilege : 62,
					    										limit:pageSize,
					    										filter_EQS_flightNumber:record.data.flightNo
					    									},
					    									success:function(resp){
					    										var respText = Ext.util.JSON.decode(resp.responseText);
					    										Ext.getCmp('endCity').setValue(respText.result[0].endCity);
					    									}
					    								});
					    								Ext.getCmp('arrivetime').setValue(record.data.flightDate);
					    								Ext.getCmp('flightTime').setValue(record.data.flightTime);
					    								if(record.data.sonderzug==1){
					    									totalSonPrice = Number(record.data.cpSonderzugPrice)+Number(record.data.sonderzugPrice);
					    									Ext.getCmp('sonderzug').setValue(true);
					    								}
					    								if(record.data.wait==1){
					    									Ext.getCmp('wait').setValue(true);
					    									Ext.getCmp('waitVal').setValue(1);
					    								}
					    								if(record.data.urgentService==1){
					    									Ext.getCmp('urgentService').setValue(true);
					    									Ext.getCmp('urgentServiceVal').setValue(1);
					    								}
													    addFeeRecordStore.proxy = new Ext.data.HttpProxy({
															method : 'POST',
															url : sysPath+'/stock/oprValueAddFeeAction!list.action'
														});
														addFeeRecordStore.load({
															params:{
																filter_EQL_dno:record.data.dno,
																filter_EQL_status:0,
																limit:pageSize
															}
														});
														requestStore.proxy = new Ext.data.HttpProxy({
															method : 'POST',
															url : sysPath+'/stock/oprRequestDoAction!list.action'
														});
														requestStore.load({
															params:{
																filter_EQL_dno:record.data.dno,
																limit:pageSize
															}
														});
					    								Ext.getCmp('cartypecombo').setValue(record.data.carType);
					    								Ext.getCmp('roadtypecombo').setValue(record.data.roadType);
					    								Ext.getCmp('cpSonderzugPrice').setValue(record.data.cpSonderzugPrice);
					    								Ext.getCmp('sonderzugPrice').setValue(record.data.sonderzugPrice);
					    								Ext.getCmp('subNo').setValue(record.data.subNo);
					    								Ext.getCmp('takeModecombo').setValue(record.data.takeMode);
					    								Ext.getCmp('receiptTypecombo').setValue(record.data.receiptType);
					    								Ext.getCmp('distributionMode').setValue(record.data.distributionMode);
					    								if(record.data.distributionMode == '中转'){
					    									cashTraFee = record.data.traFee;
					    								}
					    								Ext.getCmp('consigneeTel').setValue(record.data.consigneeTel);
					    								Ext.getCmp('consignee').setValue(record.data.consignee);
					    								Ext.getCmp('city').setValue(record.data.city);
					    								Ext.getCmp('town').setValue(record.data.town);
					    								Ext.getCmp('street').setValue(record.data.street);
					    								Ext.getCmp('consigneeAddress').setValue(record.data.addr);
					    								Ext.getCmp('goWhere').setValue(record.data.goWhere);
					    								Ext.getCmp('goWhereId').setValue(record.data.goWhereId);
					    								//Ext.getCmp('goWhereId').setRawValue(record.data.goWhere);
					    								Ext.getCmp('areaType').setValue(record.data.areaType);
					    								Ext.getCmp('consigneeAddress').setValue(record.data.addr);
					    								Ext.getCmp('goods').setValue(record.data.goods);
					    								Ext.getCmp('chargemodecombo').setValue(record.data.valuationType);
					    								Ext.getCmp('piece').setValue(record.data.piece );
					    								Ext.getCmp('bulk').setValue(record.data.bulk);
					    								Ext.getCmp('cusWeight').setValue(record.data.cusWeight);
					    								Ext.getCmp('cqWeight').setValue(record.data.cqWeight);
					    								Ext.getCmp('payerCombo').setValue(record.data.whoCash);
					    								if(record.data.whoCash == '预付'){
					    									cashCpFee = record.data.cpFee;
					    								}else if(record.data.whoCash == '到付'){
					    									cashConsigneeFee = record.data.consigneeFee;
					    								}else{
					    									cashPrice = Number(record.data.cpFee)+Number(record.data.consigneeFee);
					    								}
					    								Ext.getCmp('traFeeRate').setValue(record.data.traFeeRate);
					    								Ext.getCmp('traFee').setValue(record.data.traFee);
					    								Ext.getCmp('cpRate').setValue(record.data.cpRate);
					    								Ext.getCmp('cpFee').setValue(record.data.cpFee);
					    								Ext.getCmp('paymentCollection').setValue(record.data.paymentCollection);
					    								Ext.getCmp('consigneeRate').setValue(record.data.consigneeRate);
					    								Ext.getCmp('consigneeFee').setValue(record.data.consigneeFee);
					    								Ext.getCmp('endDepartId').setValue(record.data.endDepartId);
					    								Ext.getCmp('endDepart').setValue(record.data.endDepart);
					    								//Ext.getCmp('customerService').setValue(record.data.customerService);
					    								Ext.getCmp('remark').setValue(record.data.remark);
					    								Ext.getCmp('distributionDepartId').setValue(record.data.distributionDepartId);
					    								Ext.getCmp('distributionDepart').setValue(record.data.distributionDepart);
					    								Ext.getCmp('normSonderzugPrice').setValue(record.data.normSonderzugPrice);
					    								Ext.getCmp('normTraRate').setValue(record.data.normTraRate);
					    								Ext.getCmp('normCpRate').setValue(record.data.normCpRate);
					    								Ext.getCmp('normConsigneeRate').setValue(record.data.normConsigneeRate);
					    								Ext.getCmp('cpValueAddFee').setValue(record.data.cpValueAddFee);
					    								Ext.getCmp('cusValueAddFee').setValue(record.data.cusValueAddFee);
					    								Ext.getCmp('totalAddFee').setValue(Number(record.data.cpValueAddFee)+Number(record.data.cusValueAddFee));
					    								faxStore.removeAt(rowindex);
					    								Ext.getCmp('faxToolbar').items.item(1).setText('<b>保存明细</b>');
    													Ext.getCmp('sendrequire').setDisabled(true);
    												}else{
    													Ext.Msg.alert(alertTitle,'只有录入人并且此货物没有做任何操作才能修改');
    													return false;
    												}
    											}
    										});
    										
    									}
    								});
    							}
    						});
    					}
    				}
    });
     //增值服务费Store
    addFeeRecordStore=new Ext.data.Store({
    			 id:'addFeeRecordStore',
    			 reader:new Ext.data.JsonReader({
	            root: 'result', totalProperty: 'totalCount'
	     }, [
	     {name:'feeNameId'},
	     {name:'feeName'},
	     {name:'feeCount'},
	     {name:'feeLink'},
	     {name:'payMan'},
	     {name:'payType'}
	     ])
    });
    /*
    addFeeRecordStore.proxy = new parent.Ext.data.HttpProxy({
		method : 'POST',
			url : sysPath+'/basvalueaddfee/basValueAddFeeAction!list.action'
		});
	addFeeRecordStore.load({
		params:{
			filter_EQL_autoFee:1
		}
	});*/
	addFeeRecordStore.on('load',function(){
		var cpValue=0;
		var cusValue=0;
		var payMan='';
		for(var i=0;i<addFeeRecordStore.getCount();i++){
			var payType = addFeeRecordStore.getAt(i).get('payMan');
			if(payType==''){
				payMan = addFeeRecordStore.getAt(i).get('payType');
			}else{
				payMan = payType;
			}
			if(payMan == '预付'){
				cpValue+=addFeeRecordStore.getAt(i).get('feeCount');
			}else{
				cusValue+=addFeeRecordStore.getAt(i).get('feeCount');
			}
		}
		Ext.getCmp('cusValueAddFee').setValue(cusValue);
		Ext.getCmp('cpValueAddFee').setValue(cpValue);
		Ext.getCmp('totalAddFee').setValue(cpValue+cusValue);
	});
    var addFeeRecord=new Ext.grid.GridPanel({
    	region : "center",
    	id : 'addFeeRecord',
    	height:Ext.lib.Dom.getViewHeight()-370,
    	frame : false,
    	loadMask : true,
    	stripeRows : true,
    	viewConfig:{
			autoScroll : true,
    		scrollOffset: 0
    	},
    	cm : new Ext.grid.ColumnModel([rowNum,new Ext.grid.CheckboxSelectionModel({}),
    		{header:'payType',dataIndex:'payType',hidden:true},
    		{header:'费用类型Id',dataIndex:'feeNameId',hidden:true},
    		{header : '费用类型',dataIndex:'feeName',width:100}, 
    		{header : '金额',dataIndex:'feeCount',width:80},
    		{header:'付款方',dataIndex:'payMan',width:80,renderer:function(v,m,record,rowIndex,colIndex ,store){
    			if(v == ''){
    				return record.get('payType');
    			}else{
    				return v;
    			}
    		}}
    	]),
    	sm:new Ext.grid.CheckboxSelectionModel({}),
    	ds:addFeeRecordStore
    }); 
    function setaddrmsg(respText){
		if(form.findByType("checkbox")[0].getValue()||Ext.getCmp('takeModecombo').getValue()=='机场自提'){
			Ext.getCmp('distributionMode').setValue("新邦");
			Ext.getCmp('distributionDepartId').setValue(bussDepart);
			Ext.getCmp('distributionDepart').setValue(bussDepartName);
			Ext.getCmp('endDepart').setValue(bussDepartName);
			Ext.getCmp('endDepartId').setValue(bussDepart);
			Ext.getCmp('goWhere').setValue('');
			Ext.getCmp('goWhereId').setValue('');
		}else{
			Ext.getCmp('distributionMode').setValue(respText.result[0].develpMode);
			if(respText.result[0].distriDepartId == null || respText.result[0].distriDepartId ==''){
				Ext.getCmp('distributionDepartId').setValue(bussDepart);
				Ext.getCmp('distributionDepart').setValue(bussDepartName);
			}else{
				Ext.getCmp('distributionDepartId').setValue(respText.result[0].distriDepartId);
				Ext.getCmp('distributionDepart').setValue(respText.result[0].distriDepartName);
			}
			if(respText.result[0].endDepartId== null || respText.result[0].endDepartId==''){
				Ext.getCmp('endDepart').setValue(bussDepartName);
				Ext.getCmp('endDepartId').setValue(bussDepart);
			}else{
				Ext.getCmp('endDepart').setValue(respText.result[0].endDepartName);
				Ext.getCmp('endDepartId').setValue(respText.result[0].endDepartId);
			}
			Ext.getCmp('goWhere').setValue(respText.result[0].cusName);
			Ext.getCmp('goWhereId').setValue(respText.result[0].cusId);
		}
		Ext.getCmp('areaType').setValue(respText.result[0].areaType);
		Ext.getCmp('areaRank').setValue(respText.result[0].areaRank);
		
		if(respText.result[0].endDepartId==null){
			Ext.getCmp('showEndDepart').getEl().update('终端部门:'+bussDepartName);
		}else{
			Ext.getCmp('showEndDepart').getEl().update('终端部门:'+respText.result[0].endDepartName);
		}							    				
    }
    //增值服务费panel
    var addFeePanel=new Ext.Panel({
    	height:Ext.lib.Dom.getViewHeight()-360,
    	width:Ext.lib.Dom.getViewWidth()-15,
    	items:[{
    		layout:'table',
    		layoutConfig: {columns:6},
    		bodyStyle:'padding:5px 0px 0px 0px',
    		defaults:{border:false,layout:'form',frame:false,labelAlign:'right',labelWidth:65},
    		items:[{
    			colspan:1,
    			width:Ext.lib.Dom.getViewWidth()/5.5,
    			items:[{
    				xtype:'combo',
    				name:'feeType',
    				store:addFeeStore,
    				fieldLabel:'费用类型',
    				triggerAction : 'all',
					queryParam : 'filter_LIKES_feeName',
					model : 'local',
					id:'combofeetype',
					valueField : 'id',
				    displayField : 'feeName',
					emptyText : '选择类型',
					forceSelection : true,
    				width:100,
    				enableKeyEvents:true,
					listeners : {
						keypress:function(combo, e){
							if(e.getKey() == 13){
								Ext.getCmp('uprice').focus(true,true);
							}
						}
					}
    			}]
    		},{
    			colspan:1,
    			width:Ext.lib.Dom.getViewWidth()/5.5,
    			items:[{
    				xtype:'numberfield',
    				id:'uprice',
    				name:'uprice',
    				fieldLabel:'金额',
    				width:100,
    				enableKeyEvents:true,
					listeners : {
						keypress:function(textField, e){
							if(e.getKey() == 13){
								Ext.getCmp('addFeeBth').focus(true,true);
							}
						}
					}
    			}]
    		},{
    			colspan:1,
    			width:Ext.lib.Dom.getViewWidth()/5.5,
    			items:[{
    				xtype:'combo',
	    			store:[
	    				['预付','预付'],
	    				['到付','到付']
	    			],
	    			value:'预付',
	    			fieldLabel:'付款方',
	    			id:'addFeeWhocash',
	    			triggerAction : 'all',
	    			width:100,
	    			forceSelection : true,
	    			name:'addFeeWhocash'/*
	    			,
	    			listeners:{
	    				'select':function(combo){
	    					if(combo.getValue()=='预付'){
	    						var totp=Ext.getCmp('totalAddFee').getValue();
	    						if(totp!=''){
	    							Ext.getCmp('cpValueAddFee').setValue(totp);
	    							Ext.getCmp('cusValueAddFee').setValue(0);
	    						}
	    						for(var i=0;i<addFeeRecordStore.getCount();i++){
							  		var r=addFeeRecordStore.getAt(i);
							  		r.set('payMan','预付');
							  	}
	    					}else if(combo.getValue()=='到付'){
	    						var totp=Ext.getCmp('totalAddFee').getValue();
	    						if(totp!=''){
	    							Ext.getCmp('cusValueAddFee').setValue(totp);
	    							Ext.getCmp('cpValueAddFee').setValue(0);
	    						}
	    						for(var i=0;i<addFeeRecordStore.getCount();i++){
							  		var r=addFeeRecordStore.getAt(i);
							  		r.set('payMan','到付');
							  	}
	    					}
	    				}
	    			}*/
    			}]
    		},{
    			colspan:1,
    			width:80,
    			items:[{
    				xtype:'button',
    				id:'addFeeBth',
    				text:'添加',
    				handler:function(){
    					if(Ext.getCmp('combofeetype').isValid()&&Ext.getCmp('uprice').isValid()){
    						var flag=false;
	    					for(var i=0;i<addFeeRecordStore.getCount();i++){
	    						if(addFeeRecordStore.getAt(i).get("feeNameId")==Ext.getCmp('combofeetype').getValue()){
	    							flag=true;
	    						}
	    					}
	    					if(flag){
	    						Ext.Msg.alert(alertTitle,'请勿重复添加');
	    						//function onfocus(){
									Ext.getCmp('combofeetype').focus(true,true);
									return;
	    						//}
	    					}else{
	    					  if(Ext.getCmp('combofeetype').getValue()==''||Ext.getCmp('uprice').getValue()==''||Ext.getCmp('addFeeWhocash').getValue()==''){
	    					  		Ext.Msg.alert(alertTitle,'请勿添加空值！');
	    					  		return false;
	    					  }else{
	    					  	  Ext.getCmp('combofeetype').focus(true,true);
		    					  var store=new Ext.data.Record.create([{name:'feeNameId'},{name:'feeName'},{name:'feeCount'},{name:'feeLink'},{name:'payMan'}]);
		    					  var noFaxRecord=new store();
		    					  noFaxRecord.set("feeNameId",Ext.getCmp('combofeetype').getValue());
								  noFaxRecord.set("feeName",Ext.get('combofeetype').dom.value);
								  noFaxRecord.set("feeCount",Ext.getCmp('uprice').getValue());
								  noFaxRecord.set("payMan",Ext.getCmp('addFeeWhocash').getValue());
								  noFaxRecord.set("feeLink",'传真录入');
								  addFeeRecordStore.add(noFaxRecord);
								  var totalPrice=0;
								  for(var i=0;i<addFeeRecordStore.getCount();i++){
		    						totalPrice+=Number(addFeeRecordStore.getAt(i).get('feeCount'));
		    					  }
								  Ext.getCmp('totalAddFee').setValue(totalPrice);
								  if(Ext.getCmp('addFeeWhocash').getValue()=='预付'){
								  	Ext.getCmp('cpValueAddFee').setValue(Number(Ext.getCmp('cpValueAddFee').getValue())+Number(Ext.getCmp('uprice').getValue()));
								  }else if(Ext.getCmp('addFeeWhocash').getValue()=='到付'){
								  	Ext.getCmp('cusValueAddFee').setValue(Number(Ext.getCmp('cusValueAddFee').getValue())+Number(Ext.getCmp('uprice').getValue()));
								  }
	    					  }
	    					  
	    					}
    					}
    				}
    			}]
    		},{
    			colspan:1,
    			width:80,
    			items:[{
    				xtype:'button',
    				id:'delFeeBth',
    				text:'删除',
    				handler:function(){
						var _record = addFeeRecord.getSelectionModel().getSelections();
						if(_record.length<1){
							Ext.Msg.alert(alertTitle,'请选择删除的行!!');
						}else{
							var totalp=0;
							for(var i=0;i<_record.length;i++){
								totalp+=_record[i].data.feeCount;
							}
							addFeeRecordStore.remove(_record);
							var p=Number(Ext.getCmp('totalAddFee').getValue())-totalp;
							Ext.getCmp('totalAddFee').setValue(p);
							/*
							if(Ext.getCmp('addFeeWhocash').getValue()=='预付'){
							  	Ext.getCmp('cpValueAddFee').setValue(p);
							  	Ext.getCmp('cusValueAddFee').setValue(0);
							 }else if(Ext.getCmp('addFeeWhocash').getValue()=='到付'){
							  	Ext.getCmp('cusValueAddFee').setValue(p);
							  	Ext.getCmp('cpValueAddFee').setValue(0);
							 }*/
							for(var i=0;i<_record.length;i++){
								var record=_record[i];
								if(record.get('payMan')=='预付'){
									Ext.getCmp('cpValueAddFee').setValue(Number(Ext.getCmp('cpValueAddFee').getValue())-Number(record.get('feeCount')));
								}else if(record.get('payMan')=='到付'){
									Ext.getCmp('cusValueAddFee').setValue(Number(Ext.getCmp('cusValueAddFee').getValue())-Number(record.get('feeCount')));
								}
							}
							//alert(Ext.getCmp('cpValueAddFee').getValue()+','+Ext.getCmp('cusValueAddFee').getValue());
						}
    				}
    			}]
    		},{
    			colspan:1,
    			width:Ext.lib.Dom.getViewWidth()/4.5,
    			items:[{
    				xtype:'numberfield',
    				name:'totalAddFee',
    				id:'totalAddFee',
    				fieldLabel:'合计',
    				value:0,
    				width:100
    			}]
    		}]
    	},{
    		layout:'form',
    		bodyStyle:'padding:0px 0px 0px 0px',
    		items:[addFeeRecord]
    	}]
    });
    //个性化要求Store
    requestStore=new Ext.data.Store({
    	id:'requestStore',
    	proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/cusRequestAction!findRequest.action"}),
		baseParams:{limit:pageSize},
    	reader:new Ext.data.JsonReader({
	    root: 'result', totalProperty: 'totalCount'
	     }, [
	     {name:'requestStage'},
	     {name:'request'},
	     {name:'requestType'}
	     ])
    });
    //要求类型Store
    /*
	var requestTypeStore=new Ext.data.Store({
		storeId:"requestTypeStore",
		baseParams:{filter_EQL_basDictionaryId:6,privilege:16},
		proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/dictionaryAction!ralaList.action"}),
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
        	{name:'id'},
        	{name:'requestType',mapping:'typeName'}
        	])
	});*/
	var requestMsaArr={'totalCount':'1','result':[{
        requestid:1,
        requestStage:'请选择',
        requestMsgName:'请选择'
    }]};
    var requestMsgStore=new Ext.data.Store({
    	storeId:'requestMsgStore',
    	proxy:new Ext.data.MemoryProxy(requestMsaArr), 
    	reader:new Ext.data.JsonReader({
	    root: 'result', totalProperty: 'totalCount'
	     }, [
	     {name:'requestid'},
	     {name:'requestStage'},
	     {name:'requestMsgName'}
	     ])
    });
    //个性化要求gridpanel
    var requestRecord=new Ext.grid.GridPanel({
    	region : "center",
    	id : 'requestRecord',
    	height:Ext.lib.Dom.getViewHeight()-370,
    	frame : false,
    	loadMask : true,
    	stripeRows : true,
    	viewConfig:{
			autoScroll : true,
    		scrollOffset: 0
    	},
    	cm : new Ext.grid.ColumnModel([rowNum,new Ext.grid.CheckboxSelectionModel({}),
    		{header : '要求阶段',dataIndex:'requestStage',width:80},
    		{header : '要求类型',dataIndex:'requestType',width:80},
    		{header : '个性化要求',dataIndex:'request',width:200}
    	]),
    	sm:new Ext.grid.CheckboxSelectionModel({}),
    	ds:requestStore
    }); 
    //个性化要求panel
    var requestDoPanel=new Ext.Panel({
    	height:Ext.lib.Dom.getViewHeight()-360,
    	width:Ext.lib.Dom.getViewWidth()-15,
    	items:[{xtype:'hidden',name:'requestT',id:'requestT'},
    			   {xtype:'hidden',name:'requestS',id:'requestS'},
    			   {xtype:'hidden',name:'requetsItem',id:'requetsItem'},{
    		layout:'table',
    		layoutConfig: {columns:5},
    		bodyStyle:'padding:5px 0px 0px 0px',
    		defaults:{border:false,layout:'form',frame:false,labelAlign:'right',labelWidth:65},
    		items:[{
    			colspan:1,
    			width:175,
    			items:[{
    				xtype:'combo',
    				id:'requestTypedcombo',
    				fieldLabel:'要求类型',
    				name:'requestType',
    				triggerAction : 'all',
    				emptyText : '请选择',
    				minChar:0,
    				forceSelection : true,
    				model : 'local',
    				width:80,
    				store:requestTypeStore,
    				valueField : 'requestType',
				    displayField : 'requestType',
    				enableKeyEvents:true,
					listeners : {
						keypress:function(textField, e){
							if(e.getKey() == 13){
								//if(Ext.getCmp('requesttypecombo').getValue()==4){
								//	Ext.getCmp('requeststagecombo').focus(true,true);
								//}else{
									Ext.getCmp('requeststagecombo').focus(true,true);
								//}
								
							}
						}
					}
    			}]
    			},
    			{
    			colspan:1,
    			width:150,
    			labelWidth:60,
    			items:[{
    				xtype:'combo',
    				id:'requeststagecombo',
    				fieldLabel:'要求阶段',
    				name:'requestStage',
    				triggerAction : 'all',
    				emptyText : '选择类型',
    				minChar:0,
    				forceSelection : true,
    				model : 'local',
    				width:80,
    				store:requestStageStore,
    				valueField : 'requestStage',
				    displayField : 'requestStage',
    				enableKeyEvents:true,
					listeners : {
						keypress:function(textField, e){
							if(e.getKey() == 13){
								Ext.getCmp('requestContent').focus(true,true);
							}
						}
					}
    			}]
    		},{
    			colspan:1,
    			width:300,
    			labelWidth:60,
    			items:[{
    				xtype:'textfield',
    				id:'requestContent',
    				fieldLabel:'要求内容',
    				name:'request',
    				width:220,
    				enableKeyEvents:true,
					listeners : {
						keypress:function(textField, e){
							if(e.getKey() == 13){
								Ext.getCmp('addrequestBth').focus(true,true);
							}
						}
					}
    			}]
    		},{
    			colspan:1,
    			width:60,
    			items:[{
    				xtype:'button',
    				id:'addrequestBth',
    				text:'添加',
    				handler:function(){
    					/*
    					var flag=false;
	    					for(var i=0;i<requestStore.getCount();i++){
	    						if(requestStore.getAt(i).get("request")==Ext.get('requestcombo').dom.value){
	    							flag=true;
	    						}
	    					}
	    					if(flag){
	    						Ext.Msg.alert(alertTitle,'请勿重复添加');
								Ext.getCmp('requesttypecombo').focus(true,true);
									return;
	    					}else{*/
						if(Ext.getCmp('requeststagecombo').getValue()==''||Ext.getCmp('requestContent').getValue()==''||Ext.getCmp('requestTypedcombo').getValue()==''){
							Ext.Msg.alert(alertTitle,'请勿添加空值！');
							return false;
						}else{
						  Ext.getCmp('requeststagecombo').focus(true,true);
						  var store=new Ext.data.Record.create([{name:'request'},{name:'requestStage'}]);
						  var noFaxRecord=new store();
						  noFaxRecord.set("request",Ext.getCmp('requestContent').getValue());
						  noFaxRecord.set("requestType",Ext.getCmp('requestTypedcombo').getValue());
						  noFaxRecord.set("requestStage",Ext.getCmp('requeststagecombo').getValue());
						  requestStore.add(noFaxRecord);
						}
	    					//}
    				}
    			}]
    		},{
    			colspan:1,
    			width:60,
    			items:[{
    				xtype:'button',
    				id:'delrequestBth',
    				text:'删除',
    				handler:function(){
						var _record = requestRecord.getSelectionModel().getSelections();
						if(_record.length<1){
							Ext.Msg.alert(alertTitle,'请选择删除的行!!');
						}else{
							requestStore.remove(_record);
						}
    				}
    			}]
    		}
    		,{
    			colspan:1,
    			width:Ext.lib.Dom.getViewWidth()/5,
    			items:[{
    				xtype:'label',
    				html:'',
    				width:150
    			}]
    		}]
    	},{
    		layout:'form',
    		bodyStyle:'padding:0px 0px 0px 0px',
    		items:[requestRecord]
    	}]
    });
    var isone=true;
    var two=true;
	var tablePanel=new Ext.TabPanel({   
	    id: "mainTab",   
	    activeTab: 0,
	    height:Ext.lib.Dom.getViewHeight()-350,
	    defaults: {   
	        autoScroll: true,   
	        autoHeight:true,   
	        style: "padding:0 0 0 0"  
	    },   
	    items:[   
	       {id:'faxTabPanel',title:"主单明细", tabTip:"主单明细列表", items:[detailRecord]},   
	        {id:'addFeeTabPanel',title:"增值服务费",tabTip:'增值服务费',items:[addFeePanel]},   
	        {id:'requestDoPanel',title:"个性化要求",html:'客户个性化要求',items:[requestDoPanel]} 
	    ],
	    listeners:{
	    	tabchange:function(tab,e){
				if(tab.activeTab.id=="requestDoPanel"){
					if(isone){
						if(Ext.get('cuscombo').dom.value!='选择类型'||Ext.getCmp('consignee').getValue()!=''){
							/*
							requestStore.load({
								params:{
									cpName:Ext.get('cuscombo').dom.value,
									cusTel:Ext.getCmp('consigneeTel').getValue()
								}
							});*/
						}
						isone=false;
					}
				}
			}
	    }
	});   
	var domWidth=Ext.lib.Dom.getViewWidth()-15;
	var comWidth=domWidth/4;
	var scomWidth=domWidth/5;
	var sepcomWidth=domWidth/6;
    var domHeight=Ext.lib.Dom.getViewHeight();
	var form = new Ext.form.FormPanel({
    				id:'addfaxform',
					frame : true,
					renderTo:Ext.getBody(),
					labelAlign : "right",
					bodyStyle:'padding:0px 0px 0px 0px',
					labelWidth : 60,
					width:Ext.lib.Dom.getViewWidth(),
					height:Ext.lib.Dom.getViewHeight(),
					tbar:tb,
					keys:[{
						key:Ext.EventObject.ENTER,   
  						fn:function(){
  							//this.getEl().applyStyle("backgroundColor:red");
  						},   
  						scope:this
					}],
					items:[
							{
									xtype:"fieldset",
									//title:"主单信息",
									layout:'table',
									id:'setmainmsg',
									//height:38,
									style:'margin:1px;',
									layoutConfig: {columns:10},
									bodyStyle:'padding:5px 0px 0px 0px',
									defaults:{border:false,layout:'form',frame:false,labelAlign:'right',labelWidth:65},
									items:[{
											colspan:2,
											width:scomWidth,
											//labelWidth:60,
											items:[{
													xtype:'combo',
													fieldLabel:'运输方式',
													//anchor:'100%',
													triggerAction : 'all',
													queryParam : 'filter_LIKES_typeName',
							    					model : 'local',
							    					id:'trafficModecombo',
							    					valueField : 'trafficMode',
				    								displayField : 'trafficMode',
				    								name : 'trafficMode',
				    								width:fourWidth,
													store:trafficModeStore,
													value:'空运',
													emptyText : '选择类型',
							    					forceSelection : true,
							    					enableKeyEvents:true,
								 					listeners : {
												 		'keyup':function(textField, e){
											                     if(e.getKey() == 13){
											                     	Ext.getCmp('cuscombo').focus(true,true);
											                     	changeBgcolor('trafficModecombo','cuscombo');
											                  }
												 		},
										 				'select':function(combo, record, index){
										 					var val=Ext.getCmp('trafficModecombo').getValue();
										 					var fieldNo= Ext.getCmp('flightcombo').getEl().parent().parent().parent().first();
											                if(val=='空运'){
															  fieldNo.dom.innerHTML='班次号<span style="color:red">*</span>:';
															  Ext.getCmp('flightcombo').allowBlank=false;
											                }else if(val=='汽运'){
											                  fieldNo.dom.innerHTML='班次号:';
															  Ext.getCmp('flightcombo').allowBlank=true;
											                }else if(val=='铁路运输'){
															 // fieldNo.dom.innerHTML='车次号<span style="color:red">*</span>:';
															  //fieldTime.dom.innerHTML='到达时间:';
											                }
										 				}
											 		}
												}]
										},{
											colspan:2,
											width:scomWidth,
											//labelWidth:60,
											items:[
												{xtype:'hidden',name:'cpName',id:'cpName'},
												{
												xtype:'combo',
												fieldLabel:'代理公司<span style="color:red">*</span>',
												triggerAction :'all',
												model : 'local',
						    					id:'cuscombo',
						    					resizable : true,
						    					width:fourWidth,
						    					pageSize:pageSize,
						    					minChars : 0,
						    					listWidth:200,
						    					store:cusStore,
						    					queryParam :'filter_LIKES_cusName',
						    					valueField :'id',
			    								displayField :'cusName',
			    								hiddenName : 'cusId',
												emptyText : '选择类型',
						    					forceSelection : true,
						    					blankText : "代理公司不能为空!",
						    					allowBlank : false,
						    					enableKeyEvents:true,
								 					listeners : {
												 		keyup:function(textField, e){
											                  if(e.getKey() == 13){
											                	  Ext.Ajax.request({
																	url : sysPath + "/sys/customerAction!list.action",
																	params : {
																		filter_EQL_id:textField.getValue(),
																		filter_EQL_isProjectcustomer:1
																	},
																	success : function(res) {
																		var respText1 = Ext.util.JSON.decode(res.responseText);
																		if(respText1.result.length>0){
																			Ext.getCmp('receiptTypecombo').setValue('指定原件返回');
																		}
																	}
																 });
											                     Ext.getCmp('flightMain').focus(true,true);
											                     changeBgcolor('cuscombo','flightMain');
											                  }
												 		},
												 		select:function(combo){
															Ext.getCmp('flightcombo').setDisabled(false);
															isone=true;
															var cusName=Ext.get('cuscombo').dom.value;
															if(combo.getValue()==null){
																combo.setValue('0');
												 				combo.setRawValue(cusName);
												 			}
												 			Ext.getCmp('cpName').setValue(cusName);
												 		}
									 				}
												}]
										},{
											colspan:2,
											width:scomWidth,
											labelWidth:60,
											items:[{
													xtype:'textfield',
													fieldLabel:'主单号<span style="color:red">*</span>',
													id:'flightMain',
													//anchor:'100%',
													name:'flightMainNo',
													width:fourWidth,
													enableKeyEvents:true,
													blankText : "主单号不能为空!",
						    						allowBlank : false,
								 					listeners : {
											 		keyup:function(textField, e){
										                     if(e.getKey() == 13){
										                     	Ext.Ajax.request({
													         		url:sysPath+'/fax/oprFaxInAction!ralaList.action',
																	params:{
																		filter_EQS_flightMainNo:textField.getValue(),
																		filter_GED_createTime:new Date().add(Date.DAY, -1).format('Y-m-d'),
																		filter_EQL_cusId:Ext.getCmp('cuscombo').getValue(),
																		privilege:privilege
																	},success:function(resp){
																		//Ext.example.msg('提示信息','该主单号曾经录入过传真。');
																		var respText = Ext.util.JSON.decode(resp.responseText);
																		if(respText.result.length>0){
																			//Ext.example.msg('提示信息','该主单号曾经录入过传真。');
																			Ext.getCmp('showMsg').getEl().update('<span style="color:red">该主单号已经存在！</span>');
																		}else{
																			Ext.getCmp('showMsg').getEl().update('');
																		}
																	}
													          });
										                     	Ext.getCmp('flightcombo').focus(true,true);
										                     	changeBgcolor('flightMain','flightcombo');
										                  }
											 			}
									 				}
												}]
										},{
											colspan:2,
											width:scomWidth,
											labelWidth:60,
											items:[{xtype:'hidden',id:'customerName',name:'customerName'},
												{xtype:'hidden',id:'endCity',name:'endCity'},
												{xtype:'hidden',id:'flightTime',name:'flightTime'},
												{
													xtype:'combo',
													fieldLabel:'班次号<span style="color:red">*</span>',
													triggerAction : 'all',
							    					model : 'local',
							    					id:'flightcombo',
							    					width:fourWidth,
							    					resizable : true,
							    					minChars : 0,
							    					hiddenName:'flightNo',
							    					queryParam : 'filter_LIKES_flightNumber',
							    					pageSize:pageSize,
							    					valueField : 'flightNumber',
				    								displayField : 'flightNumber',
													store:flightStore,
							    					forceSelection :true,
							    					blankText : "班次号不能为空!",
							    					allowBlank : false,
							    					enableKeyEvents:true,
								 					listeners : {
												 		'keyup':function(textField, e){
															if(e.getKey()==13){
																var val=Ext.getCmp('trafficModecombo').getValue();
																if(Ext.getCmp('trafficModecombo').getValue()=='汽运'){
																	textField.setValue(Ext.get('flightcombo').dom.value);
																	Ext.getCmp('arrivetime').focus(true,true);
																	changeBgcolor('flightcombo','arrivetime');
																}else{
																	if(Ext.getCmp('trafficModecombo').getValue()=='空运'){
																		if(textField.getValue()!=Ext.get('flightcombo').dom.value){
																			textField.setValue('');
																		}
																		Ext.getCmp('arrivetime').focus(true,true);
																		changeBgcolor('flightcombo','arrivetime');
																	}
																}
															}
												 		},
												 		'select':function(){
											                var flightNo=Ext.getCmp('flightcombo').getValue();
															 Ext.Ajax.request( {
															    url : sysPath + "/sys/basFlightAction!ralaList.action",
																params : {
																	filter_EQS_flightNumber:flightNo,
																	privilege:62
																},
																success : function(resp) {
																	var respText = Ext.util.JSON.decode(resp.responseText);
																	Ext.getCmp('customerName').setValue(respText.result[0].cusName);
																	Ext.getCmp('endCity').setValue(respText.result[0].endCity);
																	Ext.getCmp('flightTime').setValue(respText.result[0].standardEndtime);
																}
															});
											                 	 
												 		}
													}
												}]
										},{
											colspan:2,
											width:scomWidth,
											items:[
												{
													xtype:'datefield',id:'arrivetime',
													name:'flightDate',fieldLabel:'班次日期',
													//anchor:'100%',
													format:'Y-m-d',
													value:new Date(),
													allowBlank:false,
													blankText:'班次日期不能为空！',
													width:fourWidth,
													enableKeyEvents:true,
													listeners : {
											 		keyup:function(textField, e){
										                     if(e.getKey() == 13){
										                    	textField.blur();
										                     	Ext.getCmp('subNo').focus(true,true);
										                     	changeBgcolor('arrivetime','subNo');
										                  }
											 			}
									 				}
												}]
										}
										]
								},{
									xtype:'fieldset',
									//title:"专车信息",
									id:'setsongder',
									layout:'table',
									//height:38,
									style:'margin:2px;',
									layoutConfig: {columns:7},
									bodyStyle:'padding:5px 0px 0px 0px',
									defaults:{border:false,layout:'form',frame:false,labelAlign:'right',labelWidth:65},
									items:[
										{	
											colspan:1,
											width:100,
											labelWidth:60,
											items:[{xtype:'hidden',name:'sonderzug',id:'sonderzugVal',value:'0'},{
												xtype:'checkbox',
												fieldLabel:'专车送货',
												id:'sonderzug',
												name:'sonderzugs',
												boxLabel:'',
												width:30,
												inputValue:'0',
												enableKeyEvents: true, 
												listeners : {
											 		specialkey:function(checkbox, e){
										               if(e.getKey() == 17){
										            	   if(form.findByType("checkbox")[0].getValue()){
										            		   Ext.getCmp('sonderzug').setValue(false);
										            	   }else{
										            		   Ext.getCmp('sonderzug').setValue(true);
										            	   }
										               }else if(e.getKey()==13){
										               	/*
										            	   if(form.findByType("checkbox")[0].getValue()){
										            		   Ext.getCmp('sonderzugVal').setValue(1);
										            		  
										            	   }else{
										            		   Ext.getCmp('sonderzugVal').setValue(0);
										            	   }*/
										            	    Ext.getCmp('cartypecombo').focus(true,true);
										               }
											 		},check:function(checkbox,flag){
											 			var comcity=Ext.getCmp('city');
													 	var comtown=Ext.getCmp('town');
													 	var comstreet=Ext.getCmp('street');
													 	var comdis=Ext.getCmp('distributionMode');
													 	var comaddr=Ext.getCmp('consigneeAddress');
													 	var comaddrType=Ext.getCmp('areaType');
											 			if(flag){
											 				 Ext.getCmp('sonderzugVal').setValue(1);
											 				 Ext.getCmp('cartypecombo').setDisabled(false);
											 				 Ext.getCmp('roadtypecombo').setDisabled(false);
											 				 Ext.getCmp('sonderzugPrice').setDisabled(false);
											 				 Ext.getCmp('cpSonderzugPrice').setDisabled(false);
											 				// Ext.getCmp('wait').setDisabled(false);
											 				 
											 				 Ext.getCmp('takeModecombo').setValue('市内送货');
													 		 comdis.setValue('新邦');
													 		 comaddrType.setValue('市内');
											 				 Ext.getCmp('takeModecombo').getEl().dom.readOnly=true;
											 				 
											 				 Ext.getCmp('distributionMode').setValue('新邦');
											 			}else{
											 				Ext.getCmp('sonderzugVal').setValue(0);
											 				Ext.getCmp('cartypecombo').setDisabled(true);
											 				Ext.getCmp('roadtypecombo').setDisabled(true);
											 				Ext.getCmp('sonderzugPrice').setDisabled(true);
											 				Ext.getCmp('cpSonderzugPrice').setDisabled(true);
											 				//Ext.getCmp('wait').setDisabled(true);
											 				Ext.getCmp('takeModecombo').getEl().dom.readOnly=false;
											 				
											 			}
											 		}
									 			}
										}]
										},
										{
											colspan:1,
											width:sepcomWidth,
											labelWidth : 40,
											items:[{
												xtype:'combo',
												fieldLabel:'车型',
												width:fiveWidth,
												disabled:true,
												triggerAction : 'all',
						    					id:'cartypecombo',
						    					name:'carType',
						    					queryParam : 'filter_LIKES_typeName',
						    					valueField : 'carType',
			    								displayField : 'carType',
			    								hiddenName : 'carType',
												store:carTypeStore,
												emptyText : '选择类型',
						    					forceSelection : true,
						    					allowBlank : true,
						    					enableKeyEvents: true, 
												listeners : {
											 		select:function(e){
														 Ext.getCmp('roadtypecombo').focus(true,true);
											 		},blur:function(combo){
											 			if(form.findByType("checkbox")[0].getValue()&&combo.getValue()==''){
											 				Ext.Msg.alert(alertTitle,"车型不能为空!!");
																//function onfocus(){
																	Ext.getCmp('cartypecombo').focus(true,true);
																//}
											 			}
											 		}
									 			}
											}
											]
										},
										{
											colspan:1,
											width:sepcomWidth,
											labelWidth : 40,
											items:[{
												xtype:'combo',
												fieldLabel:'路型',
												disabled:true,
												triggerAction : 'all',
						    					id:'roadtypecombo',
						    					name:'roadType',
						    					queryParam : 'filter_LIKES_typeName',
						    					valueField : 'roadType',
			    								displayField : 'roadType',
			    								hiddenName : 'roadType',
												store:roadTypeStore,
												emptyText : '选择类型',
						    					forceSelection : true,
						    					allowBlank : true,
												width:fiveWidth,
												enableKeyEvents: true, 
												listeners : {
											 		select:function(e){
														Ext.getCmp('wait').focus(true,true);
											 		},blur:function(combo){
											 			if(form.findByType("checkbox")[0].getValue()&&combo.getValue()==''){
											 				Ext.Msg.alert(alertTitle,"路型不能为空!!");
																//function onfocus(){
																	Ext.getCmp('roadtypecombo').focus(true,true);
																//}
											 			}
											 		}
											 	}
											}]
										},
										{
											colspan:1,
											width:sepcomWidth+15,
											labelWidth : 60,
											items:[{xtype:'hidden',name:'normSonderzugPrice',id:'normSonderzugPrice'},{
												xtype:'textfield',
												id:'sonderzugPrice',
												name:'sonderzugPrice',
												disabled:true,
												readOnly:true,
												width:fiveWidth,
												fieldLabel:'到付专车'
												}
											]
										},{
											colspan:1,
											width:sepcomWidth+15,
											labelWidth : 60,
											items:[{
												xtype:'textfield',
												id:'cpSonderzugPrice',
												name:'cpSonderzugPrice',
												disabled:true,
												readOnly:true,
												width:fiveWidth,
												fieldLabel:'预付专车'
												}
											]
										},
										{
											colspan:1,
											width:sepcomWidth+15,
											width:110,
											labelWidth:80,
											items:[{xtype:'hidden',name:'wait',id:'waitVal',value:'0'},{
												xtype:'checkbox',
												id:'wait',
												name:'wait1',
												inputValue:'0',
												fieldLabel:'等通知放货',
												//disabled:true,
												boxLabel:'',
												width:20,
												enableKeyEvents: true, 
												listeners : {
											 		specialkey:function(checkbox, e){
										               if(e.getKey() == 17){
										            	   if(form.findByType("checkbox")[1].getValue()){
										            		   Ext.getCmp('wait').setValue(false);
										            	   }else{
										            		   Ext.getCmp('wait').setValue(true);
										            	   }
										               }else if(e.getKey()==13){
										            	   if(form.findByType("checkbox")[1].getValue()){
										            		   Ext.getCmp('waitVal').setValue(1);
										            	   }else{
										            		   Ext.getCmp('waitVal').setValue(0);
										            	   }
										            	   Ext.getCmp('subNo').focus(true,true);
										               }
											 		},
											 		check:function(checkbox,flag){
											 			if(flag){
											 				 Ext.getCmp('waitVal').setValue(1);
											 			}else{
											 				 Ext.getCmp('waitVal').setValue(0);
											 			}
											 		}
									 			 }
												}
											]
										},
										{
											colspan:1,
											labelWidth:40,
											width:scomWidth,
											items:[{xtype:'hidden',name:'urgentService',id:'urgentServiceVal',value:'0'},{
												xtype:'checkbox',
												id:'urgentService',
												name:'urgent',
												fieldLabel:'加急',
												width:30,
												boxLabel:'',
												listeners : {
											 		specialkey:function(checkbox, e){
										               if(e.getKey() == 17){
										            	   if(form.findByType("checkbox")[2].getValue()){
										            		   Ext.getCmp('urgentService').setValue(false);
										            	   }else{
										            		   Ext.getCmp('urgentService').setValue(true);
										            	   }
										               }else if(e.getKey()==13){
										            	   if(form.findByType("checkbox")[2].getValue()){
										            		   Ext.getCmp('urgentServiceVal').setValue(1);
										            	   }else{
										            		   Ext.getCmp('urgentServiceVal').setValue(0);
										            	   }
										            	   Ext.getCmp('subNo').focus(true,true);
										            	   return;
										               }
											 		},
											 		check:function(checkbox,flag){
											 			if(flag){
											 				 Ext.getCmp('urgentServiceVal').setValue(1);
											 			}else{
											 				 Ext.getCmp('urgentServiceVal').setValue(0);
											 			}
											 		}
									 			 }
												
											  }
											]
										}
									]
								},{//第三个配送信息
									xtype:'fieldset',
									//title:"配送信息",
									id:'setdevtype',
									layout:'table',
									//height:93,
									style:'margin:1px;',
									layoutConfig: {columns:8},
									bodyStyle:'padding:5px 0px 0px 0px',
									defaults:{border:false,layout:'form',frame:false,labelAlign:'right',labelWidth:70},
									items:[
										{
											colspan:2,
											width:comWidth,
											labelWidth:80,
											items:[{
												xtype:'textfield',
												id:'subNo',
												name:'subNo',
												fieldLabel:'分单号',
												width:commentWidth,
												enableKeyEvents:true,
												listeners : {
											 		keyup:function(textField, e){
										                  if(e.getKey() == 13){
										                     	Ext.getCmp('takeModecombo').focus(true,true);
										                     	changeBgcolor('subNo','takeModecombo');
										                  }
											 		}
									 			}
											}]
										},{
											colspan:2,
											width:comWidth,
											labelWidth:80,
											items:[{
												xtype:'combo',
												fieldLabel:'提货方式',
												triggerAction : 'all',
												width:commentWidth,
												model:'local',
						    					id:'takeModecombo',
						    					valueField : 'takeMode',
			    								displayField : 'takeMode',
			    								name : 'takeMode',
												store:takeModeStore,
												emptyText : '选择类型',
												editable :false,
												//value:'市内自提',
						    					forceSelection : true,
						    					enableKeyEvents:true,
						    					listeners : {
											 		keyup:function(combo, e){
										                  if(e.getKey() == 13){
										                  		var comcity=Ext.getCmp('city');
													 			var comtown=Ext.getCmp('town');
													 			var comstreet=Ext.getCmp('street');
													 			var comdis=Ext.getCmp('distributionMode');
													 			var comaddr=Ext.getCmp('consigneeAddress');
													 			var comaddrType=Ext.getCmp('areaType');
													 			/*
													 			if(combo.getValue()=='机场自提'){
													 				comcity.setValue('广州市');
													 				comtown.setValue('白云区');
													 				comstreet.setValue('白云国际机场');
													 				comaddr.setValue('广州市白云区白云国际机场');
													 				comdis.setValue('新邦');
													 				comaddrType.setValue('市内');
													 			}*/
										                     	Ext.getCmp('receiptTypecombo').focus(true,true);
										                     	changeBgcolor('takeModecombo','receiptTypecombo');
										                  }
											 		},
											 		render:function(combo){
											 			if(bussDepart == szDepartId){
											 				combo.setValue('机场自提');
											 			}else{
											 				combo.setValue('市内送货');
											 			}
											 		}
									 			}
											}]
										},{
											colspan:2,
											width:comWidth,
											labelWidth:80,
											items:[{
												xtype:'combo',
												width:commentWidth,
												fieldLabel:'回单类型',
												triggerAction : 'all',
						    					 id:'receiptTypecombo',
						    					 queryParam : 'filter_LIKES_typeName',
						    					 valueField : 'receiptType',
			    								 displayField : 'receiptType',
			    								 hiddenName : 'receiptType',
			    								 model:'local',
												 store:receiptTypeStore,
												 emptyText : '选择类型',
												 value:'新邦货单',
												 editable :false,
						    					 forceSelection : true,
						    					 enableKeyEvents:true,
						    					 listeners : {
											 		keyup:function(textField, e){
										                     if(e.getKey() == 13){
										                     	Ext.getCmp('consigneeTel').focus(true,true);
										                     	changeBgcolor('receiptTypecombo','consigneeTel');
										                  }
											 			}
									 			}
											}]
										},{
											colspan:2,
											labelWidth:80,
											width:comWidth,
											items:[{
												xtype:'textfield',
												fieldLabel:'配送方式',
												readOnly:true,
												id:'distributionMode',
												name:'distributionMode',
												width:commentWidth
											}]
										},{
											colspan:2,
											width:comWidth,
											labelWidth:80,
											items:[{
												xtype:'combo',
												mode: 'local',
												id:'consigneeTel',
												name:'consigneeTel',
												valueField : 'consigneeTel',
    											displayField : 'consigneeTel',
												width:commentWidth,
												queryParams:'consigneeTel',
												minChars:30,
												triggerAction:'all',
												maxHeight: 500, 
    											listWidth:520,
												forceSelection:false,
												maxLength:200,
												maxLengthText:'长度不能超过200个字符',
												emptyText:'136../021-784..',
												//allowBlank:false,
												//blankText:'电话不能为空!!',
												hideTrigger:true,
												fieldLabel:'收货人电话<span style="color:red">*</span>',
												tpl: '<div id="panel" style="height:300px"></div>',
												store:new Ext.data.SimpleStore({fields:["consigneeTel","consigneeTel"],data:[[]]}),
    											enableKeyEvents:true,
    											listeners : {
											 		keyup:function(combo, e){
										               if(e.getKey() == 13){
										                   combo.setValue(Ext.get('consigneeTel').dom.value);
										                   if(combo.getValue()!=''){
										                     Ext.getCmp('consignee').focus(true,true);
										                     changeBgcolor('consigneeTel','consignee');
										                   }
										                   combo.collapse();
										               }
										               else if(e.getKey()==40){
										            	   consigneeGrid.getSelectionModel().selectFirstRow();
										            	   var row=consigneeGrid.getView().getRow(0); 
            												Ext.get(row).focus();
										               }else{
										               		//document.getElementById('panel').style.display="";
										               	   if((Ext.get('consigneeTel').dom.value).length>2){
										               	   		if((Ext.get('consigneeTel').dom.value).length<10)
										               	   		{
											               	   		combo.expand();
												               		consigneeStore.load({
												               			params:{
												               				tel:Ext.get('consigneeTel').dom.value
												               			}
												               		});
										               	   		}
										               	   }
										               }
											 		}
									 			}
											}]
										},{
											colspan:2,
											width:comWidth,
											labelWidth:80,
											items:[{
												xtype:'textfield',
												id:'consignee',
												name:'consignee',
												width:commentWidth,
												fieldLabel:'收货人姓名<span style="color:red">*</span>',
												allowBlank:false,
												blankText:'姓名不能为空!!',
												maxLength:100,
												maxLengthText:'长度不能超过100个汉字',
												enableKeyEvents:true,
												listeners : {
											 		keyup:function(textField, e){
										               if(e.getKey() == 13){
										                   Ext.getCmp('city').focus(true,true);
										                   changeBgcolor('consignee','city');
										                }
											 	    }
									 			}
											}]
										},{
											colspan:2,
											width:comWidth,
											labelWidth:80,
											items:[
												{
												xtype:'combo',
												id:'city',
												name:'city',
												minChars : 0,
												triggerAction : 'all',
												pageSize:pageSize,
												width:commentWidth,
												valueField : 'areaName',
												queryParam : 'filter_LIKES_areaName',
												store:areaCityStore,
    											displayField : 'areaName',
												fieldLabel:'市<span style="color:red">*</span>',
												forceSelection:true,
												enableKeyEvents:true,
												allowBlank:false,
												emptyText:'请选择',
												blankText:'所在市不能为空!!',
						    					listeners : {
											 		keyup:function(combo, e){
										                     if(e.getKey() == 13){
										                     	//Ext.getCmp('town').clearValue();
										                    	var parentId=Ext.getCmp('city').getValue();
										                    	Ext.apply(areaTownStore.baseParams,{
													 				filter_EQS_parentName:parentId
													 			});
													 			if(combo.getValue()!=''){
													 				Ext.getCmp('town').focus(true,true);
													 				changeBgcolor('city','town');
													 				//Ext.getCmp('town').store.load();
													 			}
										                  	}
											 		},
											 		select :function(combo,e){
											 			Ext.getCmp('town').clearValue();
											 			var parentId=Ext.getCmp('city').getValue();			 		
											 			Ext.apply(areaTownStore.baseParams,{
											 				filter_EQS_parentName:parentId
											 			});
											 			//Ext.getCmp('town').store.load();
											 		}
									 			}
											}]
										},{
											colspan:2,
											labelWidth:80,
											width:comWidth,
											items:[{
												xtype:'combo',
												id:'town',
												name:'town',
												model:'remote',
												minChars : 0,
												triggerAction : 'all',
												typeAhead : true,
												fieldLabel:'区/县<span style="color:red">*</span>',
												queryParam : 'filter_LIKES_areaName',
												pageSize:pageSize,
												store:areaTownStore,
												valueField : 'areaName',
												forceSelection:true,
												//hideTrigger:true,
    											displayField : 'areaName',
    											allowBlank:false,
    											emptyText:'请选择',
    											width:commentWidth,
												blankText:'所在县不能为空!!',
												enableKeyEvents:true,
						    					 listeners : {
						    					 	select:function(combo,record,index){
											 			var parentId=combo.getValue();
											 			Ext.apply(areaStreetStore.baseParams,{
											 				filter_EQS_parentName:parentId
											 			});
											 			//Ext.getCmp('street').store.load();
											 		},
											 		keyup:function(combo, e){
										                     if(e.getKey() == 13){
										                     	//Ext.getCmp('street').clearValue();
										                    	var parentId=Ext.get('town').dom.value;
										                    	Ext.getCmp('town').setValue(parentId);
										                    	Ext.apply(areaStreetStore.baseParams,{
											 						filter_EQS_parentName:parentId
											 					});
											 					if(combo.getValue()!=''){
											 						Ext.getCmp('street').focus(true,true);
											 						changeBgcolor('town','street');
										                     		combo.collapse();
											 					}
											 					//Ext.getCmp('street').store.load();
										                  }
											 		},
											 		expand:function(combo){
											 			areaTownStore.reload({
											 				params:{
											 					filter_EQS_parentName:Ext.getCmp('city').getValue()
											 				}
											 			});
											 		},
											 		blur:function(combo){
											 			var city=Ext.getCmp('city').getValue();
										 				var town=Ext.getCmp('town').getValue();
										 				var street = Ext.getCmp('street').getValue();
													 	if(Ext.getCmp('city').isValid()&& Ext.getCmp('street').isValid()){
										 					Ext.Ajax.request( {
															    url : sysPath + "/sys/basAreaAction!getAreaMsg.action",
																params : {
																	city:city,
																	town:town,
																	street:street
																},
																success : function(resp) {
																	var respText = Ext.util.JSON.decode(resp.responseText);
																	setaddrmsg(respText);
																}
															});
										 				
										 				}				 		
											 		}
									 			}
											}]
										}
										,{
											colspan:2,
											labelWidth:80,
											width:200,
											items:[{
												xtype:'combo',
												fieldLabel:'镇/街道',
												id:'street',
												name:'street',
												model:'remote',
												minChars : 0,
												triggerAction : 'all',
												forceSelection:true,
												hideTrigger:true,
												typeAhead : true,
												store:areaStreetStore,
												queryParam : 'filter_LIKES_areaName',
												pageSize:pageSize,
												valueField : 'areaName',
    											displayField : 'areaName',
												width:commentWidth,
												enableKeyEvents:true,
						    					 listeners : {
											 		select:function(combo,record,index){
//											 			var aRank='街道';
//														var streetId=combo.getValue();
//															if(streetId==''){
//																streetId=Ext.getCmp('town').getValue();
//																aRank='区';
//															}
											 				var city=Ext.getCmp('city').getValue();
											 				var town=Ext.getCmp('town').getValue();
											 				var street=Ext.getCmp('street').getValue();
											 				if(street == null){
											 					street="";
											 				}
											 				if(Ext.getCmp('city').isValid()&& Ext.getCmp('street').isValid()){
											 					Ext.Ajax.request( {
																    url : sysPath + "/sys/basAreaAction!getAreaMsg.action",
																	params : {
																		city:city,
																		town:town,
																		street:street
																	},
																	success : function(resp) {
																		var respText = Ext.util.JSON.decode(resp.responseText);
																		setaddrmsg(respText);
																	}
																});
											 				
											 				}
														Ext.getCmp('consigneeAddress').setValue(city+town+street);
											 			
											 		},keyup:function(combo,e){
											 			if(e.getKey()==13){
											 				combo.collapse();
											 				if(combo.getValue()==''){
											 					if(Ext.get('street').dom.value == 'null' || Ext.get('street').dom.value == null){
											 						combo.setValue('');
											 					}else{
											 						combo.setValue(Ext.get('street').dom.value);
											 					}
											 					
											 				}
											 				/*
											 				var aRank='街道';
											 				var street=Ext.get('street').dom.value;
											 				var streetId=street;
											 				if(streetId==''){
																streetId=Ext.getCmp('town').getValue();
																aRank='区';
															}
											 				Ext.Ajax.request( {
															    url : sysPath + "/sys/basAreaAction!ralaList.action",
																params : {
																	filter_EQS_areaName:streetId,
																	filter_LIKES_areaRank:aRank,
																	privilege:55
																},
																success : function(resp) {
																	var respText = Ext.util.JSON.decode(resp.responseText);
																	if(respText.result.length<1){
																		Ext.Ajax.request({
																			url : sysPath + "/sys/basAreaAction!ralaList.action",
																			params : {
																				filter_EQS_areaName:Ext.getCmp('town').getValue(),
																				filter_LIKES_areaRank:'区',
																				privilege:55
																			},
																			success:function(resp1){
																				var respText1 = Ext.util.JSON.decode(resp1.responseText);
																				Ext.getCmp('showMsg').getEl().update('<span style="color:red">您输入的街道在地区资料里面不存在!</span>');
																				setaddrmsg(respText1);
																			}
																		});
																	}else{
																		setaddrmsg(respText);	
																	}
																	
																}
															});
											 				Ext.getCmp('street').setValue(street);
											 				var town=Ext.getCmp('town').getValue();
															var city=Ext.getCmp('city').getValue();*/
											 				var city=Ext.getCmp('city').getValue();
											 				var town=Ext.getCmp('town').getValue();
											 				var street=Ext.getCmp('street').getValue();
											 				if(street == null){
											 					street="";
											 				}
														 	if(Ext.getCmp('city').isValid()&& Ext.getCmp('street').isValid()){
											 					Ext.Ajax.request( {
																    url : sysPath + "/sys/basAreaAction!getAreaMsg.action",
																	params : {
																		city:city,
																		town:town,
																		street:street
																	},
																	success : function(resp) {
																		var respText = Ext.util.JSON.decode(resp.responseText);
																		setaddrmsg(respText);
																	}
																});
											 				
											 				}
															var addrCom = Ext.getCmp('consigneeAddress');
															addrCom.setValue(city+town+street);
												 			
												 			var addrLength = addrCom.getValue().length;
												 			addrCom.focus(false,true);
												 			addrCom.selectText(addrLength,addrLength);
												 			//addrCom.getValue().charAt(addrLength)='\n';
												 			
												 			//var range = Ext.getCmp('consigneeAddress').createTextRange(); //建立文本选区    
													    	//range.moveStart('character', Ext.getCmp('consigneeAddress').getValue().length); //选区的起点移到最后去   
													    	//range.collapse(true);    
													    	//range.select();   
												 			changeBgcolor('street','consigneeAddress');
											 			}
											 			
											 		}
											 		,expand:function(combo){
											 			areaStreetStore.load({
										                    params:{
										                    	filter_EQS_parentName:Ext.getCmp('town').getValue()
										                    }
										                 });
											 		}
									 			}
											}]
										},{
											colspan:4,
											labelWidth:80,
											items:[
												{
												xtype:'textfield',
												fieldLabel:'收货人地址<span style="color:red">*</span>',
												id:'consigneeAddress',
												name:'addr',
												width:comWidth+110,
												allowBlank:true,
												blankText:'收货人地址不能为空！',
												maxLength:250,
												maxLengthText:'长度不能超过250个汉字',
												enableKeyEvents:true,
						    					 listeners : {
													keyup:function(textfield,e){
														if(e.getKey()==13){
															Ext.getCmp('goods').focus(true,true);
															changeBgcolor('consigneeAddress','goods');
														}
													}
						    					 }
												
											}]
										},{
											colspan:2,
											labelWidth:80,
											items:[{
												xtype:'textfield',
												fieldLabel:'供应商',
												name:'gowhere',
												id:'goWhere',
												width:110,
												readOnly:true
								
											}]
										}]
								},{//货物信息
									xtype:'fieldset',
									id:'setgoods',
									//title:"货物信息",
									//height:120,
									layout:'table',
									style:'margin:2px;',
									layoutConfig: {columns:8},
									bodyStyle:'padding:5px 0px 0px 0px',
									defaults:{border:false,layout:'form',frame:false,labelAlign:'right',labelWidth:70},
									items:[
										{
											colspan:2,
											labelWidth:80,
											width:comWidth,
											items:[{
												xtype:'textfield',
												fieldLabel:'货物名称',
												id:'goods',
												name:'goods',
												maxLength:50,
												maxLengthText:'长度不能超过50个汉字',
												width:commentWidth,
												enableKeyEvents:true,
												listeners:{
													keyup:function(textfield,e){
														if(e.getKey()==13){
															Ext.getCmp('chargemodecombo').focus(true,true);
															changeBgcolor('goods','chargemodecombo');
														}
													}
												}
											}]
										},{
											colspan:2,
											labelWidth:80,
											width:comWidth,
											items:[{
												xtype:'combo',
												fieldLabel:'计费方式',
												triggerAction : 'all',
												name:'valuationType',
						    					id:'chargemodecombo',
						    					queryParam : 'filter_LIKES_typeName',
						    					valueField : 'chargeMode',
			    								displayField : 'chargeMode',
												store:chargeModeStore,
												emptyText : '选择类型',
												value:'重量',
												editable :false,
						    					forceSelection : true,
						    					allowBlank : false,
						    					blankText:'计费方式不能为空!',
												width:commentWidth,
												enableKeyEvents:true,
												listeners:{
													keyup:function(textfield,e){
														if(e.getKey()==13){
															Ext.getCmp('piece').focus(true,true);
															changeBgcolor('chargemodecombo','piece');
														}
													}
												}
											}]
										},{
											colspan:2,
											labelWidth:80,
											width:comWidth,
											items:[{
												xtype:'numberfield',
												fieldLabel:'件数<span style="color:red">*</span>',
												width:commentWidth,
												id:'piece',
												name:'piece',
												allowBlank : false,
						    					blankText:'件数不能为空!',
						    					minValue:1,
						    					maxLength:7,
												enableKeyEvents:true,
												listeners:{
													keyup:function(textfield,e){
														if(e.getKey()==13){
															if(Ext.getCmp('chargemodecombo').getValue()=='体积'){
																Ext.getCmp('bulk').focus(true,true);
																changeBgcolor('piece','bulk');
															}else{
																Ext.getCmp('cqWeight').focus(true,true);
																changeBgcolor('piece','cqWeight');
															}
														}
													}
												}
											}]
										},{
											colspan:2,
											labelWidth:80,
											width:comWidth,
											items:[{
												xtype:'numberfield',
												id:'bulk',
												name:'bulk',
												fieldLabel:'体积m³',
												maxValue:99999.99,
												minValue:0.0,
												decimalPrecision :2,
												maxLength:8,
												width:commentWidth,
												value:0,
												enableKeyEvents:true,
												listeners:{
													keyup:function(textfield,e){
														if(e.getKey()==13){
															 Ext.getCmp('cqWeight').focus(true,true);
															 changeBgcolor('bulk','cqWeight');
														}
													}
												}
											}]
										},{
											colspan:2,
											labelWidth:80,
											width:comWidth,
											items:[{
												xtype:'numberfield',
												id:'cqWeight',
												name:'cqWeight',
												maxValue:99999.99,
												maxLength:8,
												minValue:1,
												allowBlank:false,
												blankText:'代理不能为空!!',
												fieldLabel:'代理重量KG<span style="color:red">*</span>',
												width:commentWidth,
												enableKeyEvents:true,
												listeners:{
													keyup:function(textfield,e){
														if(e.getKey()==13){
															Ext.getCmp('cusWeight').setValue(textfield.getValue());
															Ext.getCmp('cusWeight').focus(true,true);
															changeBgcolor('cqWeight','cusWeight');
														}
													},blur:function (textfield){
														Ext.getCmp('cusWeight').setValue(textfield.getValue());
													}
											   }
											}]
										},{
											colspan:2,
											labelWidth:80,
											width:comWidth,
											items:[{
												xtype:'numberfield',
												id:'cusWeight',
												name:'cusWeight',
												fieldLabel:'计费重量KG<span style="color:red">*</span>',
												width:commentWidth,
												allowBlank:false,
												blankText:'计费重量不能为空!!',
												maxValue:99999.99,
												minValue:1,
												maxLength:8,
												enableKeyEvents:true,
												listeners:{
													keyup:function(textfield,e){
														if(e.getKey()==13){
															Ext.getCmp('paymentCollection').focus(true,true);
															changeBgcolor('cusWeight','paymentCollection');
														}
													}
											   }
											}]
										},{
											colspan:2,
											labelWidth:80,
											width:comWidth,
											items:[{
												xtype:'numberfield',
												id:'paymentCollection',
												name:'paymentCollection',
												fieldLabel:'代收运费<span style="color:red">*</span>',
												allowBlank:false,
												maxValue:99999999.99,
												minValue:0.0,
												maxLength:11,
												blankText:'代收运费不能为空!',
												width:commentWidth,
												enableKeyEvents:true,
												listeners:{
													keyup:function(textfield,e){
														if(e.getKey()==13){
															Ext.getCmp('payerCombo').focus(true,true);
															changeBgcolor('paymentCollection','payerCombo');
														}
													}
											    }
											}]
										},{
											colspan:2,
											labelWidth:80,
											width:comWidth,
											items:[{
												xtype:'combo',
												fieldLabel:'付款方',
												width:commentWidth,
												triggerAction : 'all',
						    					id:'payerCombo',
						    					name:'whoCash',
						    					queryParam : 'filter_LIKES_typeName',
						    					valueField : 'whoCash',
			    								displayField : 'whoCash',
			    								hiddenName : 'whoCash',
												store:payerStore,
												emptyText : '选择类型',
												editable :false,
						    					forceSelection : true,
						    					allowBlank : false,
						    					enableKeyEvents:true,
												listeners:{
													keypress:function(textfield,e){
														if(e.getKey()==13){
															if(!islocation){
																requestStore.proxy = new Ext.data.HttpProxy({
																	method : 'POST',
																	url : sysPath+'/sys/cusRequestAction!findRequest.action'
																});
																requestStore.load({
																	params:{
																		cpName:Ext.get('cuscombo').dom.value,
																		cusTel:Ext.get('consigneeTel').dom.value
																	}
																});
															}
															if(textfield.getValue()=='预付'){
																Ext.getCmp('totalAddFee').setValue(0);
																Ext.getCmp('cpValueAddFee').setValue(0);
									    						Ext.getCmp('cusValueAddFee').setValue(0);
																addFeeRecordStore.removeAll();
//																Ext.getCmp('addFeeWhocash').setValue(textfield.getValue());
//																var totp=Ext.getCmp('totalAddFee').getValue();
//									    						if(totp!=''){
//									    							Ext.getCmp('cpValueAddFee').setValue(totp);
//									    							Ext.getCmp('cusValueAddFee').setValue(0);
//									    						}
//									    						for(var i=0;i<addFeeRecordStore.getCount();i++){
//															  		var r=addFeeRecordStore.getAt(i);
//															  		r.set('payMan','预付');
//															  	}
															  	
																//Ext.getCmp('cpFee').getEl().dom.readOnly=true;
																Ext.getCmp('consigneeFee').setValue(0);
																Ext.getCmp('consigneeRate').setValue(0);
																Ext.getCmp('sonderzugPrice').setValue(0);
																//Ext.getCmp('consigneeFee').getEl().dom.readOnly=true;
																Ext.getCmp('cpSonderzugPrice').getEl().dom.readOnly=true;
																Ext.getCmp('sonderzugPrice').getEl().dom.readOnly=true;
																if(Ext.getCmp('cuscombo').getValue()==''){
																	Ext.Msg.alert(alertTitle,'代理不能为空!!');
																	return;
																}else{
																	if(form.getForm().isValid()){
																		countPrice(textfield);
																	}
																	Ext.getCmp('sendrequire').focus(true,true);
																	changeBgcolor('payerCombo','sendrequire');
																}
															}else if(textfield.getValue()=='到付'){
																addFeeRecordStore.proxy = new parent.Ext.data.HttpProxy({
																	method : 'POST',
																		url : sysPath+'/basvalueaddfee/basValueAddFeeAction!list.action'
																	});
																addFeeRecordStore.load({
																	params:{
																		filter_EQL_autoFee:1,
																		filter_EQS_payMan:'到付'
																	}
																});
																Ext.getCmp('addFeeWhocash').setValue(textfield.getValue());
//																var totp=Ext.getCmp('totalAddFee').getValue();
//																if(totp!=''){
//										    						Ext.getCmp('cusValueAddFee').setValue(totp);
//										    						Ext.getCmp('cpValueAddFee').setValue(0);
//										    					}
//										    					for(var i=0;i<addFeeRecordStore.getCount();i++){
//															  		var r=addFeeRecordStore.getAt(i);
//															  		r.set('payMan','到付');
//															  	}
															  	
																//Ext.getCmp('cpFee').getEl().dom.readOnly=true;
																//Ext.getCmp('consigneeFee').getEl().dom.readOnly=true;
																Ext.getCmp('cpSonderzugPrice').getEl().dom.readOnly=true;
																Ext.getCmp('sonderzugPrice').getEl().dom.readOnly=true;
																if(Ext.getCmp('consigneeTel').getValue()==''){
																	Ext.Msg.alert(alertTitle,'收货人电话不能为空!!');
																	return;
																}else{
																	if(form.getForm().isValid()){
																		countPrice(textfield);
																	}
																	Ext.getCmp('cpFee').setValue(0);
																	Ext.getCmp('cpRate').setValue(0);
																	Ext.getCmp('cpSonderzugPrice').setValue(0);
																	Ext.getCmp('sendrequire').focus(true,true);
																	changeBgcolor('payerCombo','sendrequire');
																}
															}else if(textfield.getValue()=='双方付'){
//																Ext.getCmp('addFeeWhocash').setValue(textfield.getValue());
//																var totp=Ext.getCmp('totalAddFee').getValue();
//									    						if(totp!=''){
//									    							Ext.getCmp('cpValueAddFee').setValue(totp);
//									    							Ext.getCmp('cusValueAddFee').setValue(0);
//									    						}
//									    						for(var i=0;i<addFeeRecordStore.getCount();i++){
//															  		var r=addFeeRecordStore.getAt(i);
//															  		r.set('payMan','预付');
//															  	}
															  	addFeeRecordStore.proxy = new parent.Ext.data.HttpProxy({
																	method : 'POST',
																		url : sysPath+'/basvalueaddfee/basValueAddFeeAction!list.action'
																	});
																addFeeRecordStore.load({
																	params:{
																		filter_EQL_autoFee:1,
																		filter_EQS_payMan:'到付'
																	}
																});
																if(form.getForm().isValid()){
																		countPrice(textfield);
																	}
																//Ext.getCmp('cpFee').getEl().dom.readOnly=false;
																Ext.getCmp('cpSonderzugPrice').getEl().dom.readOnly=false;
																Ext.getCmp('sonderzugPrice').getEl().dom.readOnly=false;
																//Ext.getCmp('consigneeFee').getEl().dom.readOnly=false;
																Ext.getCmp('cpFee').focus(true,true);
																changeBgcolor('payerCombo','cpFee');
															}
															
														}
													},
													select:function(textfield){
														if(!islocation){
															requestStore.proxy = new Ext.data.HttpProxy({
																method : 'POST',
																url : sysPath+'/sys/cusRequestAction!findRequest.action'
															});
															requestStore.load({
																params:{
																	cpName:Ext.get('cuscombo').dom.value,
																	cusTel:Ext.get('consigneeTel').dom.value
																}
															});
														}
														if(textfield.getValue()=='预付'){
																Ext.getCmp('totalAddFee').setValue(0);
																Ext.getCmp('cpValueAddFee').setValue(0);
									    						Ext.getCmp('cusValueAddFee').setValue(0);
																addFeeRecordStore.removeAll();
//																Ext.getCmp('addFeeWhocash').setValue(textfield.getValue());
//																var totp=Ext.getCmp('totalAddFee').getValue();
//									    						if(totp!=''){
//									    							Ext.getCmp('cpValueAddFee').setValue(totp);
//									    							Ext.getCmp('cusValueAddFee').setValue(0);
//									    						}
//									    						for(var i=0;i<addFeeRecordStore.getCount();i++){
//															  		var r=addFeeRecordStore.getAt(i);
//															  		r.set('payMan','预付');
//															  	}
																//Ext.getCmp('cpFee').getEl().dom.readOnly=true;
																Ext.getCmp('sonderzugPrice').setValue(0);
																Ext.getCmp('consigneeFee').setValue(0);
																Ext.getCmp('consigneeRate').setValue(0);
																//Ext.getCmp('consigneeFee').getEl().dom.readOnly=true;
																Ext.getCmp('cpSonderzugPrice').getEl().dom.readOnly=true;
																Ext.getCmp('sonderzugPrice').getEl().dom.readOnly=true;
																if(Ext.getCmp('cuscombo').getValue()==''){
																	Ext.Msg.alert(alertTitle,'代理不能为空!!');
																	return;
																}else{
																	if(form.getForm().isValid()){
																		countPrice(textfield);
																	}
																	Ext.getCmp('sendrequire').focus(true,true);
																	changeBgcolor('payerCombo','sendrequire');
																}
															}else if(textfield.getValue()=='到付'){
//																Ext.getCmp('addFeeWhocash').setValue(textfield.getValue());
//																var totp=Ext.getCmp('totalAddFee').getValue();
//																if(totp!=''){
//										    						Ext.getCmp('cusValueAddFee').setValue(totp);
//										    						Ext.getCmp('cpValueAddFee').setValue(0);
//										    					}
//										    					for(var i=0;i<addFeeRecordStore.getCount();i++){
//															  		var r=addFeeRecordStore.getAt(i);
//															  		r.set('payMan','到付');
//															  	}
																addFeeRecordStore.proxy = new parent.Ext.data.HttpProxy({
																	method : 'POST',
																		url : sysPath+'/basvalueaddfee/basValueAddFeeAction!list.action'
																	});
																addFeeRecordStore.load({
																	params:{
																		filter_EQL_autoFee:1,
																		filter_EQS_payMan:'到付'
																	}
																});
																//Ext.getCmp('cpFee').getEl().dom.readOnly=true;
																//Ext.getCmp('consigneeFee').getEl().dom.readOnly=true;
																
																Ext.getCmp('cpSonderzugPrice').getEl().dom.readOnly=true;
																Ext.getCmp('sonderzugPrice').getEl().dom.readOnly=true;
																if(Ext.getCmp('consigneeTel').getValue()==''){
																	Ext.Msg.alert(alertTitle,'收货人电话不能为空!!');
																	return;
																}else{
																	if(form.getForm().isValid()){
																		countPrice(textfield);
																	}
																	Ext.getCmp('cpSonderzugPrice').setValue(0);
																	Ext.getCmp('cpFee').setValue(0);
																	Ext.getCmp('cpRate').setValue(0);
																	Ext.getCmp('sendrequire').focus(true,true);
																	changeBgcolor('payerCombo','sendrequire');
																}
															}else if(textfield.getValue()=='双方付'){
//																Ext.getCmp('addFeeWhocash').setValue(textfield.getValue());
//																var totp=Ext.getCmp('totalAddFee').getValue();
//									    						if(totp!=''){
//									    							Ext.getCmp('cpValueAddFee').setValue(totp);
//									    							Ext.getCmp('cusValueAddFee').setValue(0);
//									    						}
//									    						for(var i=0;i<addFeeRecordStore.getCount();i++){
//															  		var r=addFeeRecordStore.getAt(i);
//															  		r.set('payMan','预付');
//															  	}
																addFeeRecordStore.proxy = new parent.Ext.data.HttpProxy({
																	method : 'POST',
																		url : sysPath+'/basvalueaddfee/basValueAddFeeAction!list.action'
																	});
																addFeeRecordStore.load({
																	params:{
																		filter_EQL_autoFee:1,
																		filter_EQS_payMan:'到付'
																	}
																});
																if(form.getForm().isValid()){
																		countPrice(textfield);
																	}
																//Ext.getCmp('cpFee').getEl().dom.readOnly=false;
																Ext.getCmp('cpSonderzugPrice').getEl().dom.readOnly=false;
																Ext.getCmp('sonderzugPrice').getEl().dom.readOnly=false;
																//Ext.getCmp('consigneeFee').getEl().dom.readOnly=false;
																Ext.getCmp('cpFee').focus(true,true);
																changeBgcolor('payerCombo','cpFee');
															}
													},
													render:function(combo){
														if(bussDepart == szDepartId){
															combo.setValue('到付');
														}else{
															combo.setValue('预付');
														}
													}
											    }
											}]
										},{
											colspan:2,
											labelWidth:80,
											width:comWidth,
											items:[{xtype:'hidden',name:'normTraRate',id:'normTraRate'},{
												xtype:'numberfield',
												name:'traFeeRate',
												id:'traFeeRate',
												fieldLabel:'中转费率',
												readOnly:true,
												width:commentWidth
											}]
										},{
											colspan:2,
											labelWidth:80,
											width:comWidth,
											items:[{
												xtype:'numberfield',
												fieldLabel:'中转费',
												name:'traFee',
												id:'traFee',
												maxValue:99999999.99,
												minValue:0.0,
												maxLength:11,
												width:commentWidth
											}]
										},{
											colspan:2,
											labelWidth:80,
											width:comWidth,
											items:[{xtype:'hidden',name:'normCpRate',id:'normCpRate'},{
												xtype:'numberfield',
												id:'cpRate',
												name:'cpRate',
												fieldLabel:'预付提送费率',
												readOnly:true,
												width:commentWidth
											}]
										},{
											colspan:2,
											labelWidth:80,
											width:comWidth,
											items:[{
												xtype:'numberfield',
												fieldLabel:'预付提送费',
												maxValue:99999999.99,
												minValue:0.0,
												maxLength:11,
												id:'cpFee',
												name:'cpFee',
												width:commentWidth,
												enableKeyEvents:true,
												listeners:{
													keyup:function(textfield,e){
														if(e.getKey()==13){
																Ext.getCmp('consigneeFee').focus(true,true);
																changeBgcolor('cpFee','consigneeFee');
														}
													}
												}
											}]
										},{
											colspan:2,
											labelWidth:80,
											width:comWidth,
											items:[{xtype:'hidden',name:'normConsigneeRate',id:'normConsigneeRate'},{
												xtype:'numberfield',
												id:'consigneeRate',
												name:'consigneeRate',
												fieldLabel:'到付提送费率',
												readOnly:true,
												width:commentWidth
											}]
										},{
											colspan:2,
											labelWidth:80,
											width:comWidth,
											items:[{
												xtype:'numberfield',
												id:'consigneeFee',
												name:'consigneeFee',
												fieldLabel:'到付提送费',
												maxValue:99999999.99,
												minValue:0.0,
												maxLength:11,
												width:commentWidth,
												enableKeyEvents:true,
												listeners:{
													keyup:function(textfield,e){
														if(e.getKey()==13){
																Ext.getCmp('sendrequire').focus(true,true);
																changeBgcolor('consigneeFee','sendrequire');
														}
													}
												}
											}]
										},{
											colspan:2,
											labelWidth:80,
											width:comWidth,
											items:[{
													xtype:'textfield',
													id:'sendrequire',
													name:'sendrequire',
													fieldLabel:'送货要求',
													enableKeyEvents:true,
													maxLength:100,
													maxLengthText:'长度不能超过100个汉字',
													width:commentWidth,
													listeners:{
														keypress:function(textfield,e){
															if(e.getKey()==13){
																Ext.getCmp('remark').focus(true,true);
																changeBgcolor('sendrequire','remark');
															}
														}
												    }
											}]
										},{
											colspan:2,
											labelWidth:60,
											width:comWidth,
											items:[{
													xtype:'textfield',
													id:'remark',
													name:'remark',
													fieldLabel:'备注',
													width:commentWidth+20,
													enableKeyEvents:true,
													listeners:{
														keypress:function(textfield,e){
															if(e.getKey()==13){
																Ext.getCmp('addDetailBtn').focus(true,true);
																changeBgcolor('remark','addDetailBtn');
															}
														}
												    }
											}]
										}]
								},{
									layout:'table',
									style:'margin:0px;',
									layoutConfig: {columns:2},
									bodyStyle:'padding:1px 0px 1px 0px',
									items:[{
											colspan:1,
											width:domWidth-110,
											items:[{
												xtype:'label',
												html:'<pre></pre>'
											}]
										}
									]
								},
								{xtype:'hidden',name:'dno',id:'dno'},
								{xtype:'hidden',name:'endDepartId',id:'endDepartId'},
								{xtype:'hidden',name:'endDepart',id:'endDepart'},
								{xtype:'hidden',name:'inDepartId',id:'inDepartId',value:bussDepart},
								{xtype:'hidden',name:'inDepart',id:'inDepart',value:bussDepartName},
								{xtype:'hidden',name:'curDepartId',id:'curDepartId',value:bussDepart},
								{xtype:'hidden',name:'curDepart',id:'curDepart',value:bussDepartName},
								{xtype:'hidden',id:'areaType',name:'areaType'},
								{xtype:'hidden',name:'faxMainId',id:'faxMainId',value:'0'},
								{xtype:'hidden',name:'areaRank',id:'areaRank'},
								{xtype:'hidden',name:'goWhereId',id:'goWhereId'},
								{xtype:'hidden',name:'distributionDepartId',id:'distributionDepartId'},
								{xtype:'hidden',name:'distributionDepart',id:'distributionDepart'},
								{xtype:'hidden',name:'cusDepartCode',id:'cusDepartCode',value:departId},
								{xtype:'hidden',name:'cpValueAddFee',id:'cpValueAddFee'},
								{xtype:'hidden',name:'cusValueAddFee',id:'cusValueAddFee'}
								,tablePanel
						]
	});
	function saveFax(){
		//countPrice(Ext.getCmp('payerCombo'));
		if(form.getForm().isValid()) {
			Ext.Msg.confirm(alertTitle, "确定要保存？", function(btnYes) {
				if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
					Ext.getCmp('addDetailBtn').setDisabled(true);
					var saveflag=msgValidate(form);
					if(saveflag){
						Ext.Ajax.request({
							url:sysPath+"/fax/oprFaxInAction!ralaList.action",
							params:{
								privilege:68,
								limit:pageSize,
								filter_EQS_flightMainNo:Ext.getCmp('flightMain').getValue(),
								filter_EQS_subNo:Ext.getCmp('subNo').getValue(),
								filter_EQL_cusId:Ext.getCmp('cuscombo').getValue(),
								filter_EQS_consigneeTel:Ext.getCmp('consigneeTel').getValue(),
								filter_EQS_piece:Ext.getCmp('piece').getValue(),
								filter_EQS_paymentCollection:Ext.getCmp('paymentCollection').getValue(),
								filter_EQS_cqWeight:Ext.getCmp('cqWeight').getValue()
							},
							success:function(resp1){
								var respText1 = Ext.util.JSON.decode(resp1.responseText);
								if(respText1.result.length>0){
									Ext.Msg.confirm(alertTitle, "此票货物已存在,是否继续添加?", function(btnYes) {
						    			if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
						    				//flag=true;
						    				setFaxMainNo();
								    	}else{
								    		Ext.getCmp('addDetailBtn').setDisabled(false);
								    	}
					    			});
								}else{
									setFaxMainNo();
								}
							}
						});
					}
				}
			});
		}
	}
	//var flag=true;
	//设置代理传真主单号
	function setFaxMainNo(){
		//获得代理传真主单号
		Ext.Ajax.request({
			url:sysPath+"/fax/oprFaxInAction!ralaList.action",
			params:{
				privilege:68,
				limit:pageSize,
				filter_EQS_flightMainNo:Ext.getCmp('flightMain').getValue()
			},
			success:function(resp){
				var respText = Ext.util.JSON.decode(resp.responseText);
				if(respText.result.length>0){
					Ext.getCmp('faxMainId').setValue(respText.result[0].faxMainId);
				}
				formSubmit();
				/*
				if(flag){
					flag=false;
					formSubmit();
				}else{
					Ext.Msg.alert(alertTitle,'正在提交...请勿重复按回车键！');
						flag=true;
				}*/
			}
		});
	}
	//表单提交
	function formSubmit(){
		var traMode=Ext.getCmp('trafficModecombo').getValue();
		var domCpname=Ext.get('cuscombo').dom.value;
		var cusId=Ext.getCmp('cuscombo').getValue();
		if(cusId==null){
			Ext.getCmp('cuscombo').setValue('0');
			Ext.getCmp('cuscombo').setRawValue(domCpname);
			cusId=0;
		}
		var cpName=Ext.get('cuscombo').dom.value;
		var endCity=Ext.getCmp('endCity').getValue();
		var filgthNo=Ext.get('flightcombo').dom.value;
		var flightMainNo=Ext.getCmp('flightMain').getValue();
		var flightDate=Ext.get('arrivetime').dom.value;
		var flightTime=Ext.getCmp('flightTime').getValue();
		//var cusService=Ext.getCmp('customerService').getValue();
		//var cusDepartCode=Ext.getCmp('cusDepartCode').getValue();
		//var cusDepartName=Ext.getCmp('cusDepartName').getValue();
		var customerName=Ext.getCmp('customerName').getValue();
		var sendRequire = Ext.getCmp('sendrequire').getValue();
		if(sendRequire != null && sendRequire != ''){
			  var store=new Ext.data.Record.create([{name:'request'},{name:'requestStage'},{name:'requestType'}]);
			  var noFaxRecord=new store();
			  noFaxRecord.set("request",sendRequire);
			  noFaxRecord.set("requestType",'时效');
			  noFaxRecord.set("requestStage",'送货');
			  requestStore.add(noFaxRecord);
		}
		/*
		if(Ext.getCmp('customerService').getValue()==''){
			Ext.Msg.alert(alertTitle,"该代理不存在相对的客服员,请维护后再录传真!!");
			return;
		}*/
		var datas="";
		var payMan='';
		for(var i=0;i<addFeeRecordStore.getCount();i++){
			var payType = addFeeRecordStore.getAt(i).get('payMan');
			if(payType==''){
				payMan = addFeeRecordStore.getAt(i).get('payType');
			}else{
				payMan = payType;
			}
		}
		for(var i=0;i<addFeeRecordStore.getCount();i++){
			if(i>0){
				datas+='&';
			}
			datas += "addFeeList["+i+"].feeName="+addFeeRecordStore.getAt(i).get('feeName')+'&'
				  +"addFeeList["+i+"].feeCount="+addFeeRecordStore.getAt(i).get('feeCount')+'&'
				  +"addFeeList["+i+"].payType="+payMan;
				  //+"addFeeList["+i+"].feeLink="+addFeeRecordStore.getAt(i).get('feeLink')+'&'
				  //+"addFeeList["+i+"].payMan="+addFeeRecordStore.getAt(i).get('payMan');
		}
		var datareq="";
		for(var i=0;i<requestStore.getCount();i++){
			if(i>0){
				datareq+='&';
			}
			datareq += "requestList["+i+"].requestStage="+requestStore.getAt(i).get('requestStage')+'&'
				  +"requestList["+i+"].request="+requestStore.getAt(i).get('request')+'&'
				  +"requestList["+i+"].requestType="+requestStore.getAt(i).get('requestType');
		}
		var param="";
		if(datas!=''&&datareq!=''){
			param=datas+"&"+datareq;
		}else if(datas!=''&&datareq==''){
			param=datas;
		}else if(datas==''&&datareq!=''){
			param=datareq;
		}
		form.getForm().submit({
				url : sysPath + "/fax/oprFaxInAction!save.action",
				params:param,
				waitMsg : '正在保存数据...',
				success : function(form, action) {
						islocation=false;//重置是否为定位修改的标识
						Ext.Msg.alert(alertTitle,action.result.msg,onfocus);
						function onfocus(){
						Ext.getCmp('subNo').focus(true,true);
						changeBgcolor('addDetailBtn','subNo');
						Ext.getCmp('sendrequire').setDisabled(false);
						addFeeRecordStore.removeAll();
						requestStore.removeAll();
//						addFeeRecordStore.proxy = new parent.Ext.data.HttpProxy({
//							method : 'POST',
//			    					url : sysPath+'/basvalueaddfee/basValueAddFeeAction!list.action'
//			    		});
//						addFeeRecordStore.load({
//							params:{
//								filter_EQL_autoFee:1
//							}
//						});
						Ext.getCmp('addDetailBtn').setDisabled(false);
						//flag=true;
						Ext.getCmp('addfaxform').getForm().reset();
						Ext.getCmp('trafficModecombo').setValue(traMode);
						Ext.getCmp('customerName').setValue(customerName);
						Ext.getCmp('endCity').setValue(endCity);
						Ext.getCmp('flightcombo').setValue(filgthNo);
						Ext.getCmp('flightMain').setValue(flightMainNo);
						Ext.getCmp('flightTime').setValue(flightTime);
						if(cusId==0){
							Ext.Ajax.request({
								url:sysPath+'/sys/customerAction!ralaList.action',
								params:{
									privilege:61,
									filter_EQS_cusName:cpName
								},
								success:function(resp){
									var respText = Ext.util.JSON.decode(resp.responseText);
									Ext.getCmp('cuscombo').setValue(respText.result[0].id);
									Ext.getCmp('cuscombo').setRawValue(respText.result[0].cusName);
								}
							});
						}else{
							Ext.getCmp('cuscombo').setValue(cusId+'')
    						Ext.getCmp('cuscombo').setRawValue(cpName);
						}
    					Ext.getCmp('cpName').setRawValue(cpName);
    					Ext.getCmp('inDepartId').setValue(bussDepart);
    					Ext.getCmp('inDepart').setValue(bussDepartName);
    					Ext.getCmp('curDepartId').setValue(bussDepart);
    					Ext.getCmp('curDepart').setValue(bussDepartName);
    					Ext.getCmp('arrivetime').setValue(flightDate);
						//return;
					//}
					faxStore.load({
						params:{
							filter_EQS_flightMainNo:Ext.getCmp('flightMain').getValue(),
							filter_EQL_cusId:cusId,
							filter_GED_createTime:new Date().add(Date.HOUR,-1).format('Y-m-d G:i:s')
						},
						callback :function(){
							if(faxStore.getCount()>0){
								fnSumInfo(Ext.getCmp('flightMain').getValue());
							}
						}
					});
					}
				},
				failure : function(form, action) {
					if (action.faliureType == Ext.form.Action.SERVER_INVALID) {
						Ext.Msg.alert(alertTitle, "服务器连接异常，请联系管理员！");
					} else {
						if (action.result.msg) {
							
							Ext.Msg.alert(alertTitle,
							action.result.msg);
							Ext.getCmp('addDetailBtn').setDisabled(false);
						}
					}
				}
			});
	}
	var consigneeGrid=new Ext.grid.GridPanel({
    		//title : '收货人信息',
    		height : 300,
    		//width : Ext.lib.Dom.getViewWidth(),
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
    		cm:new Ext.grid.ColumnModel([rowNum,{
	    			header:'收货人姓名',
	    			dataIndex:'consigneeName'
	    		},{
	    			header:'收货人电话',
	    			dataIndex:'consigneeTel'
	    		},{
	    			header:'收货人地址',
	    			dataIndex:'consigneeAddr'
	    		},{
	    			header:'地址类型',
	    			dataIndex:'addrType'
	    		},{
	    			header:'配送方式',
	    			dataIndex:'distributionMode'
	    		},{
	    			header:'终端部门',
	    			dataIndex:'endDepartId',
	    			hidden:true
	    		},{
	    			header:'终端部门',
	    			dataIndex:'endDepart'
	    		},{
	    			header:'市',
	    			dataIndex:'city',
	    			hidden:true
	    		},{
	    			header:'区/县',
	    			dataIndex:'town',
	    			hidden:true
	    		},{
	    			header:'镇/街道',
	    			dataIndex:'street',
	    			hidden:true
	    		},{
	    			header:'去向ID',
	    			dataIndex:'goWhereId',
	    			hidden:true
	    		},{
	    			header:'去向',
	    			dataIndex:'goWhere',
	    			hidden:true
	    		},{
	    			header:'配送部门',
	    			dataIndex:'distributionDepart',
	    			hidden:true
	    		},{
	    			header:'配送部门ID',
	    			dataIndex:'distributionDepartId',
	    			hidden:true
	    		}
    		]),
    		ds:consigneeStore,
    		listeners:{
    			rowdblclick:function(grid, rowindex, e){
    				var record=grid.getSelectionModel().getSelected();
    				if(form.findByType("checkbox")[0].getValue()||Ext.getCmp('takeModecombo').getValue()=='机场自提'){
						Ext.getCmp('distributionMode').setValue("新邦");
					}else{
						Ext.getCmp('distributionMode').setValue(record.data.distributionMode);
					}
    				Ext.getCmp('consignee').setValue(record.data.consigneeName);
    				Ext.getCmp('consigneeAddress').setValue(record.data.consigneeAddr);
    				Ext.getCmp('consigneeTel').setValue(record.data.consigneeTel);
    				Ext.getCmp('areaType').setValue(record.data.addrType);
    				Ext.getCmp('city').setValue(record.data.city);
	    			Ext.getCmp('town').setValue(record.data.town);
	    			Ext.getCmp('street').setValue(record.data.street);
    				Ext.getCmp('endDepart').setValue(record.data.endDepart);
    				Ext.getCmp('endDepartId').setValue(record.data.endDepartId);
    				Ext.getCmp('goWhere').setValue(record.data.goWhere);
    				Ext.getCmp('goWhereId').setValue(record.data.goWhereId);
    				Ext.getCmp('distributionDepart').setValue(record.data.distributionDepart);
    				Ext.getCmp('showEndDepart').getEl().update('终端部门:'+record.data.distributionDepart);
    				Ext.getCmp('distributionDepartId').setValue(record.data.distributionDepartId);
    				Ext.getCmp('consigneeTel').collapse();
    				Ext.getCmp('goods').focus(true,true);
    				
    				changeBgcolor('consigneeTel','goods');
    			},
    			keydown:function(e){
    				if(e.getKey()==13){
    					var record=consigneeGrid.getSelectionModel().getSelected();
	    				Ext.getCmp('consignee').setValue(record.data.consigneeName);
	    				Ext.getCmp('consigneeAddress').setValue(record.data.consigneeAddr);
	    				Ext.getCmp('consigneeTel').setValue(record.data.consigneeTel);
	    				if(form.findByType("checkbox")[0].getValue()||Ext.getCmp('takeModecombo').getValue()=='机场自提'){
							Ext.getCmp('distributionMode').setValue("新邦");
						}else{
							Ext.getCmp('distributionMode').setValue(record.data.distributionMode);
						}
	    				Ext.getCmp('endDepartId').setValue(record.data.endDepartId);
	    				Ext.getCmp('endDepart').setValue(record.data.endDepart);
	    				Ext.getCmp('areaType').setValue(record.data.addrType);
	    				Ext.getCmp('goWhere').setValue(record.data.goWhere);
						Ext.getCmp('goWhereId').setValue(record.data.goWhereId);
	    				Ext.getCmp('city').setValue(record.data.city);
	    				Ext.getCmp('town').setValue(record.data.town);
	    				Ext.getCmp('street').setValue(record.data.street);
	    				Ext.getCmp('distributionDepart').setValue(record.data.distributionDepart);
	    				Ext.getCmp('showEndDepart').getEl().update('终端部门:'+record.data.distributionDepart);
	    				Ext.getCmp('distributionDepartId').setValue(record.data.distributionDepartId);
	    				Ext.getCmp('consigneeTel').collapse();
	    				Ext.getCmp('consigneeAddress').focus(true,true);
	    				
	    				changeBgcolor('consigneeTel','consigneeAddress');
	    				
    				}
    			}
    		}
    });
    
	Ext.getCmp("consigneeTel").on('expand',function(){  
			if(consigneeGrid.rendered){
				consigneeGrid.doLayout();
			}else{
    			consigneeGrid.render('panel');
    			consigneeStore.load();
    		}
    });
    consigneeGrid.getStore().on('load',function(s,records){ 
        var girdcount=0; 
        s.each(function(r){ 
            if(r.get('cusRecordId')!=null){ 
                consigneeGrid.getView().getRow(girdcount).style.backgroundColor='#00C000';
            }
            girdcount=girdcount+1; 
        }); 
	}); 
	Ext.getCmp("consigneeTel").on('collapse',function(){
    	return false;
    });
    
    Ext.get("addfaxform").addKeyListener({
                key:[83],
                ctrl:true
	},newFax);
});
var cashPrice=0;//提送费
var cashCpFee=0;//预付提送费
var cashConsigneeFee=0;//到付提送费
var totalSonPrice=0;//专车费总额
var cashTraFee=0;//中转费
function countPrice(textfield) {
	var form = Ext.getCmp('addfaxform');
	var town = Ext.getCmp('town').getValue();
	var street = Ext.getCmp('street').getValue();
	var city = Ext.getCmp('city').getValue();
	var roadType = Ext.getCmp('roadtypecombo').getValue();
	var cusId = Ext.getCmp('cuscombo').getValue();
	var cusN=Ext.get('cuscombo').dom.value;
	if(cusId==null){
		cusId=0;
		Ext.getCmp('cuscombo').setValue('0');
		Ext.getCmp('cuscombo').setRawValue(cusN);
	}
	var addrType = Ext.getCmp('areaType').getValue();
	var startCity = Ext.getCmp('endCity').getValue();
	var valuationType = Ext.getCmp('chargemodecombo').getValue();
	var trafficMode = Ext.getCmp('trafficModecombo').getValue();
	if(trafficMode=='汽运'){
		if(bussDepart == szDepartId){
			startCity='深圳市';
		}else{
			startCity='广州市';
		}
	}else if(trafficMode=='空运'){
		if(startCity==null || startCity ==''){
			if(bussDepartName == szDepartId){
				startCity='深圳市';
			}else{
				startCity='广州市';
			}
		}
	}
	var takeMode = Ext.getCmp('takeModecombo').getValue();
	var distributeMode = Ext.getCmp('distributionMode').getValue();
	var cusWeight = Number(Ext.getCmp('cusWeight').getValue());//计费重量
	var cusName= Ext.getCmp('goWhere').getValue();
	var disdepartId=Ext.getCmp('distributionDepartId').getValue();
	var disdepartName=Ext.getCmp('distributionDepart').getValue();
	var goWhereId=Ext.getCmp('goWhereId').getValue();
	var tel = Ext.getCmp('consigneeTel').getValue();
	var whoCash = textfield.getValue();
	var piece = Ext.getCmp('piece').getValue();
	var bulk = Ext.getCmp('bulk').getValue();
	var tels;
	if (tel.indexOf('/') < 0) {
		tels = tel;
	} else {
		tels = tel.split('/');
	}
	if(form.findByType("checkbox")[0].getValue()){
		takeMode = '机场自提';
	}
	Ext.Ajax.request({
		url:sysPath+'/fi/faxRateSearchAction!searchRate.action',
		method:'post',
		params:{
			limit:pageSize,
			start:0,
			cusId:cusId,
			whoCash:whoCash,
			cusWeight:cusWeight,
			//cqWeight:cqWeight,
			takeMode:takeMode,
			distributionMode:distributeMode,
			bulk:bulk,
			piece:piece,
			disDepartId:disdepartId,
			cpName:cusN,
			cusName:cusName,
			cpId:goWhereId,
			consigneeTel:tels,
			addrType:addrType,
			valuationType:valuationType,
			startCity:startCity,
			trafficMode:trafficMode,
			city:city,
			town:town,
			street:street
		},
		success:function(resp){
			var respText = Ext.util.JSON.decode(resp.responseText);
			if(respText.resultMap.length>0){
				if(whoCash =='预付'||whoCash =='双方付'){
					Ext.Ajax.request({
						url : sysPath + "/sys/customerAction!list.action",
						params : {
							filter_EQL_id:cusId,
							filter_EQL_isProjectcustomer:1
						},
						success : function(res) {
							var resText1 = Ext.util.JSON.decode(res.responseText);
							// 是项目客户
							if (resText1.result.length > 0) {
								var cpProRate = respText.resultMap[0].cpProRate;
								var cpProFee = respText.resultMap[0].cpProFee;
								
								if(cpProRate == null || cpProRate ==''){
									Ext.Msg.alert(alertTitle,'此项目客户的协议价格不存在，请维护协议价后再次尝试!');
									Ext.getCmp('cpFee').setValue(0);
									Ext.getCmp('cpRate').setValue(0);
									Ext.getCmp('showMsg').getEl().update('<span style="color:red">件数为:'+piece+',重量为:'+cusWeight+',体积为:'+bulk+',目的站为:'+city+'的项目客户协议价格不存在！</span>');
									return;
								}else{
									cashCpFee = cpProFee;
									cashPrice = cpProFee;
									Ext.getCmp('cpFee').setValue(cpProFee);
									Ext.getCmp('cpRate').setValue(cpProRate);
									Ext.getCmp('showMsg').getEl().update('<span style="color:red">已找到项目客户协议价!</span>');
								}
							}else{
								var cpRate = respText.resultMap[0].cpRate;
								var cpFee = respText.resultMap[0].cpFee;
								if(cpFee == null || cpFee == ''){
									var stRate = respText.resultMap[0].stRate;
									var stFee = respText.resultMap[0].stFee;
									if(stFee == null || stFee == ''){
										Ext.getCmp('cpFee').setValue(0);
										Ext.getCmp('cpRate').setValue(0);
										Ext.Msg.alert(alertTitle,'该代理无协议价，并且无公布价，请维护协议价后再次尝试!');
										Ext.getCmp('showMsg').getEl().update('<span style="color:red">运输方式为:'+trafficMode+',提货方式为:'+takeMode+',配送方式为:'+distributeMode+',地址类型为:'+addrType+',始发站为:'+startCity+'的公布价不存在！</span>');
										return;
									}else{
										
										cashCpFee = stFee;
										cashPrice = stFee;
										Ext.getCmp('cpFee').setValue(stFee);
										Ext.getCmp('cpRate').setValue(stRate);
										Ext.getCmp('showMsg').getEl().update('<span style="color:red">该代理无协议价,取公布价!</span>');
									}
								}else{
									cashCpFee = cpFee;
									cashPrice = cpFee;
									Ext.getCmp('cpFee').setValue(cpFee);
									Ext.getCmp('cpRate').setValue(cpRate);
									Ext.getCmp('showMsg').getEl().update('<span style="color:red">已找到代理协议价!</span>');
								}
							}
						}
					});
					
					Ext.getCmp('consigneeFee').setValue(0);
					Ext.getCmp('consigneeRate').setValue(0);
					
					Ext.getCmp('normCpRate').setValue(respText.resultMap[0].stRate);
				}else{
					var conRate = respText.resultMap[0].conRate;
					var conFee = respText.resultMap[0].conFee;
					if(conFee != null && conFee != ''){
						cashConsigneeFee = conFee;
						Ext.getCmp('consigneeFee').setValue(conFee);
						Ext.getCmp('consigneeRate').setValue(conRate);
						Ext.getCmp('showMsg').getEl().update('<span style="color:red">已找到收货人协议价!</span>');
					}else{
						Ext.Ajax.request({
							url : sysPath + "/sys/customerAction!list.action",
							params : {
								filter_EQL_id:cusId,
								filter_EQL_isProjectcustomer:1
							},
							success : function(res) {
								var respText1 = Ext.util.JSON.decode(res.responseText);
								if(respText1.result.length>0){
									var cpProRate = respText.resultMap[0].cpProRate;
									var cpProFee = respText.resultMap[0].cpProFee;
									
									if(cpProRate == null || cpProRate ==''){
										var stRate = respText.resultMap[0].stRate;
										var stFee = respText.resultMap[0].stFee;
										
										if(stFee == null || stFee == ''){
											Ext.getCmp('consigneeFee').setValue(0);
											Ext.getCmp('consigneeRate').setValue(0);
											Ext.Msg.alert(alertTitle,'该收货人无协议价，并且无公布价，请维护协议价后再次尝试!');
											Ext.getCmp('showMsg').getEl().update('<span style="color:red">运输方式为:'+trafficMode+',提货方式为:'+takeMode+',配送方式为:'+distributeMode+',地址类型为:'+addrType+',始发站为:'+startCity+'的公布价不存在！</span>');
											return;
										}else{
											cashConsigneeFee = stFee;
											Ext.getCmp('consigneeFee').setValue(stFee);
											Ext.getCmp('consigneeRate').setValue(stRate);
											Ext.getCmp('showMsg').getEl().update('<span style="color:red">该收货人无协议价,并且无项目客户协议价，取公布价!</span>');
										}
									}else{
										cashCpFee = cpProFee;
										cashPrice = cpProFee;
										Ext.getCmp('consigneeFee').setValue(cpProFee);
										Ext.getCmp('consigneeRate').setValue(cpProRate);
										Ext.getCmp('showMsg').getEl().update('<span style="color:red">已找到项目客户协议价!</span>');
									}
								}else{
									var stRate = respText.resultMap[0].stRate;
									var stFee = respText.resultMap[0].stFee;
									if(stFee == null || stFee == ''){
										Ext.getCmp('consigneeFee').setValue(0);
										Ext.getCmp('consigneeRate').setValue(0);
										Ext.Msg.alert(alertTitle,'该收货人无协议价，并且无公布价，请维护协议价后再次尝试!');
										Ext.getCmp('showMsg').getEl().update('<span style="color:red">运输方式为:'+trafficMode+',提货方式为:'+takeMode+',配送方式为:'+distributeMode+',地址类型为:'+addrType+',始发站为:'+startCity+'的公布价不存在！</span>');
										return;
									}else{
										cashConsigneeFee = stFee;
										Ext.getCmp('consigneeFee').setValue(stFee);
										Ext.getCmp('consigneeRate').setValue(stRate);
										Ext.getCmp('showMsg').getEl().update('<span style="color:red">该收货无协议价,取公布价!</span>');
									}
									
									Ext.getCmp('cpFee').setValue(0);
									Ext.getCmp('cpRate').setValue(0);
									
									Ext.getCmp('normConsigneeRate').setValue(respText.resultMap[0].stRate);
									
								}
							}
						});
					}
					//到付
				}
				
				if(distributeMode =='中转'){
					//if(whoCash =='预付'){
						Ext.Ajax.request({
							url : sysPath + "/sys/customerAction!list.action",
							params : {
								filter_EQL_id:cusId,
								filter_EQL_isProjectcustomer:1
							},
							success : function(res) {
								var respText1 = Ext.util.JSON.decode(res.responseText);
								if(respText1.result.length>0){
									//项目客户
									var traProRate = respText.resultMap[0].traProRate;
									var traProFee = respText.resultMap[0].traProFee;
									
									if(traProFee == null || traProFee == ''){
										var traRate = respText.resultMap[0].traRate;
										var traFee = respText.resultMap[0].traFee;
										if(traFee ==null || traFee == ''){
											Ext.Msg.alert(alertTitle,'<span style="color:red">供应商:'+cusName+',运输方式:'+trafficMode+',<br/>提货方式:'+takeMode+',地址类型:'+addrType+',部门:'+disdepartName+'的中转协议价格不存在！</span>');
											Ext.getCmp('showMsg').getEl().update('<span style="color:red">供应商:'+cusName+',运输方式:'+trafficMode+',提货方式:'+takeMode+',地址类型:'+addrType+',部门:'+disdepartName+'的中转协议价格不存在！</span>');
											return false;
										}else{
											cashTraFee = traProFee;
											Ext.getCmp('traFeeRate').setValue(traRate);
											Ext.getCmp('traFee').setValue(traFee);
										}
									}else{
										cashTraFee = traProFee;
										Ext.getCmp('traFeeRate').setValue(traProRate);
										Ext.getCmp('traFee').setValue(traProFee);
										Ext.getCmp('showMsg').getEl().update('<span style="color:red">已找到项目客户中转协议价!</span>');
									}
								}else{
									var traRate = respText.resultMap[0].traRate;
									var traFee = respText.resultMap[0].traFee;
									if(traFee ==null || traFee == ''){
										Ext.Msg.alert(alertTitle,'<span style="color:red">供应商:'+cusName+',运输方式:'+trafficMode+',<br/>提货方式:'+takeMode+',地址类型:'+addrType+',部门:'+disdepartName+'的中转协议价格不存在！</span>');
										Ext.getCmp('showMsg').getEl().update('<span style="color:red">供应商:'+cusName+',运输方式:'+trafficMode+',提货方式:'+takeMode+',地址类型:'+addrType+',部门:'+disdepartName+'的中转协议价格不存在！</span>');
										return false;
									}else{
										cashTraFee = traFee;
										Ext.getCmp('traFeeRate').setValue(traRate);
										Ext.getCmp('traFee').setValue(traFee);
									}
								}
							}
						});
					//}
					//else{
//						var traRate = respText.resultMap[0].traRate;
//						var traFee = respText.resultMap[0].traFee;
//						if(traFee ==null || traFee == ''){
//							Ext.getCmp('traFeeRate').setValue(0);
//							Ext.getCmp('traFee').setValue(0);
//							Ext.Msg.alert(alertTitle,'<span style="color:red">供应商:'+cusName+',运输方式:'+trafficMode+',<br/>提货方式:'+takeMode+',地址类型:'+addrType+',部门:'+disdepartName+'的中转协议价格不存在！</span>');
//							Ext.getCmp('showMsg').getEl().update('<span style="color:red">供应商:'+cusName+',运输方式:'+trafficMode+',提货方式:'+takeMode+',地址类型:'+addrType+',部门:'+disdepartName+'的中转协议价格不存在！</span>');
//							return false;
//						}else{
//							cashTraFee = traFee;
//							Ext.getCmp('traFeeRate').setValue(traRate);
//							Ext.getCmp('traFee').setValue(traFee);
//						}
					//}
				}else{
					Ext.getCmp('traFeeRate').setValue(0);
					Ext.getCmp('traFee').setValue(0);
				}
				Ext.getCmp('normTraRate').setValue(respText.resultMap[0].traRate);
			}else{
				Ext.Msg.alert(alertTitle,'价格查询错误，请联系系统管理员!');
			}
		}
	});
	/*
	if(takeMode == '市内自提'&&distributeMode=='新邦'){
		Ext.Ajax.request({
			url:sysPath+'/sys/departAction!findAll.action',
			params:{
				filter_EQL_departId:Ext.getCmp('endDepartId').getValue(),
				limit:pageSize
			},success:function(resp){
				var respText = Ext.util.JSON.decode(resp.responseText);
				if(respText.result.length>0){
					var endDepartN=Ext.getCmp('endDepart').getValue();
					addrType=respText.result[0].owntakeType;
					if(addrType == '' || addrType == null){
						Ext.Msg.alert(alertTitle,'部门：'+endDepartN+'的自提类型为空，请到地区表维护.');
						return;
					}
				}
			}
		});
	}*/
	//获得标准专车协议价
	function findNormSondPrice(type){
		Ext.Ajax.request({
			url : sysPath
					+ "/fi/stSpecialTrainRateAction!findRate.action",
			params : {
				limit : pageSize,
				roadType : roadType,
				town : town,
				street : street,
				city:city,
				departId : bussDepart
			},
			success : function(resp1) {
				var respText1 = Ext.util.JSON
						.decode(resp1.responseText);
				if (respText1.resultMap.length < 1) {
					Ext.Msg.alert(alertTitle, '路型为:'+roadType+',地区名称为:'+street+',或'+town+'或'+city+'的标准专车协议价格不存在!');
					return false;
				}else{
					if(type=='st'){
						setSpecialRate(respText1);
						setNormSpecialRate(respText1);
					}else if(type=='cp'){
						setNormSpecialRate(respText1);
					}
				}
				
			}
		});
	}
	// 专车
	if (form.findByType("checkbox")[0].getValue()) {
		Ext.Ajax.request({
			url : sysPath + "/fi/specialTrainRateAction!findRate.action",
			params : {
				limit : pageSize,
				cusId : cusId,
				roadType : roadType,
				town : town,
				street : street,
				city:city,
				//addrType : addrType,
				departId : bussDepart
			},
			success : function(resp) {
				var respText = Ext.util.JSON.decode(resp.responseText);
				if (respText.resultMap.length < 1) {
					// 无专车协议价
					Ext.Msg.alert(alertTitle, '路型为:'+roadType+',地区名称为:'+street+',或'+town+'或'+city+'的标准专车协议价格不存在!');					
					
				} else {
					var respText = Ext.util.JSON.decode(resp.responseText);
					setSpecialRate(respText);
					findNormSondPrice('cp');
					Ext.getCmp('showMsg').getEl().update('<span style="color:red">已找到专车协议价格，编号为：'+respText.resultMap[0].ID+'！</span>');
				}
			}
		});
	}
	
	/*
	if (distributeMode == "中转") {
		if(textfield.getValue()=='预付'){
			Ext.Ajax.request({
				url : sysPath + "/sys/customerAction!ralaList.action",
				params : {
					privilege : 61,
					filter_EQL_id:cusId,
					filter_EQL_isProjectcustomer:1
				},
				success : function(res) {
					var resText = Ext.util.JSON.decode(res.responseText);
					// 不是项目客户
					if (resText.result.length < 1) {
						findTraFee(cusName,trafficMode,takeMode,addrType,disdepartId,valuationType,cqWeight,disdepartName);
					}
				}
			});
		}else{
			findTraFee(cusName,trafficMode,takeMode,addrType,disdepartId,valuationType,cqWeight,disdepartName);
		}
		
	}else{
		Ext.getCmp('traFeeRate').setValue(0);
		Ext.getCmp('traFee').setValue(0);
		Ext.getCmp('normTraRate').setValue(0);
	}
	*/
	// 设置标准公布价费率
	/*
	function findNormpubprice(type){
		Ext.Ajax.request({
			url : sysPath
					+ "/fi/cqStCorporateRateAction!findRate.action",
			params : {
				limit : pageSize,
				takeMode : takeMode,
				trafficMode : trafficMode,
				distributionMode : distributeMode,
				addressType : addrType,
				departId : bussDepart,
				startCity : startCity,
				city : city,
				town : town,
				street : street
			},
			success : function(resp2) {
				var respText2 = Ext.util.JSON
						.decode(resp2.responseText);
				// 该目的站没有公布价
				if (respText2.result.length < 1) {
					
					Ext.Ajax.request({
						url : sysPath
								+ "/fi/cqStCorporateRateAction!list.action",
						params : {
							filter_EQS_takeMode : takeMode,
							filter_EQS_trafficMode : trafficMode,
							filter_EQS_distributionMode : distributeMode,
							filter_EQL_status : 2,
							limit : pageSize,
							filter_EQS_addressType : addrType,
							filter_EQL_departId : bussDepart,
							filter_EQS_startAddr : startCity,
							//filter_GED_endDate : new Date().format('Y-m-d'),
							filter_LED_startDate : new Date()
									.format('Y-m-d')
						},
						success : function(resp3) {
							// 无此条件的公布价
							var respText3 = Ext.util.JSON
									.decode(resp3.responseText);
							if (respText3.result.length < 1) {
								Ext.getCmp('cpRate').setValue(0);
								Ext.getCmp('cpFee').setValue(0);
								Ext.Msg.alert(alertTitle,'无公布价，请尽快维护!!');
								Ext.getCmp('showMsg').getEl().update('<span style="color:red">运输方式为:'+trafficMode+',提货方式为:'+takeMode+',配送方式为:'+distributeMode+',地址类型为:'+addrType+',始发站为:'+startCity+'的公布价不存在！</span>');
								return false;
							} else {
								setCpStFee(respText3,
										cqWeight,type);
							}
						}
					});
				} else {
					setCpStFee(respText2, cqWeight,type);
				}
			}
		});
	}
	*/
	// 查询标准到付费率
	/*
	function findNormconprice(type){
		Ext.Ajax.request({
			url : sysPath
					+ "/fi/cqStCorporateRateAction!findRate.action",
			params : {
				limit : pageSize,
				takeMode : takeMode,
				trafficMode : trafficMode,
				distributionMode : distributeMode,
				addressType : addrType,
				departId : bussDepart,
				startCity : startCity,
				city : city,
				town : town,
				street : street
			},
			success : function(resp1) {
				var respText1 = Ext.util.JSON
						.decode(resp1.responseText);
				if(respText1.result.length<1){
					Ext.Ajax.request({
						url : sysPath+ "/fi/cqStCorporateRateAction!list.action",
						params : {
							filter_EQS_takeMode : takeMode,
							filter_EQS_trafficMode : trafficMode,
							filter_EQS_distributionMode : distributeMode,
							filter_EQL_status : 2,
							limit : pageSize,
							filter_EQS_addressType : addrType,
							filter_EQL_departId : bussDepart,
							filter_EQS_startAddr : startCity,
							//filter_GED_endDate : new Date().format('Y-m-d'),
							filter_LED_startDate : new Date().format('Y-m-d')
						},
						success : function(resp3) {
							// 无此条件的公布价
							var respText3 = Ext.util.JSON.decode(resp3.responseText);
							if (respText3.result.length < 1) {
								Ext.getCmp('consigneeRate').setValue(0);
								Ext.getCmp('consigneeFee').setValue(0);
								Ext.Msg.alert(alertTitle,'该收货人无特殊协议价并且无公布价，请尽快维护!!');
								Ext.getCmp('showMsg').getEl().update('<span style="color:red">运输方式为:'+trafficMode+',提货方式为:'+takeMode+',配送方式为:'+distributeMode+',地址类型为:'+addrType+',始发站为:'+startCity+'的公布价不存在！</span>');
								return false;
							} else {
								setConStFee(respText3,cqWeight,type);
							}
						}
					});
				}else{
					setConStFee(respText1,cqWeight,type);
				}
			}
		});
	}
	*/
	// 设置代理公布价

	/*
	function setCpStFee(respText1, cqWeight,type) {
		if (cqWeight >= 0 && cqWeight < 500) {
			if(type=='st'){
				Ext.getCmp('cpRate').setValue(Number(respText1.result[0].stage1Rate));
				Ext.getCmp('normCpRate').setValue(Number(respText1.result[0].stage1Rate));
				var price = Math.round(Number(respText1.result[0].stage1Rate)* cqWeight);
				var minPrice = respText1.result[0].lowPrice;
				if (price < minPrice) {
					Ext.getCmp('cpFee').setValue(minPrice);
					cashPrice=minPrice;
					cashCpFee=minPrice;
				} else {
					Ext.getCmp('cpFee').setValue(price);
					cashPrice=price;
					cashCpFee=price;
				}
				Ext.getCmp('showMsg').getEl().update('<span style="color:red">该代理无协议价，已找到公布价，只能按重量计费！价格编号:'+respText1.result[0].id+'</span>');
			}else{
				Ext.getCmp('normCpRate').setValue(Number(respText1.result[0].stage1Rate));
			}
		} else if (cqWeight >= 500 && cqWeight < 1000) {
			if(type=='st'){
				Ext.getCmp('cpRate').setValue(Number(respText1.result[0].stage2Rate));
				Ext.getCmp('normCpRate').setValue(Number(respText1.result[0].stage2Rate));
				var price = Math
					.round(Number(respText1.result[0].stage2Rate)
							* cqWeight);
				var minPrice = respText1.result[0].lowPrice;
				if (price < minPrice) {
					cashCpFee=minPrice;
					Ext.getCmp('cpFee').setValue(minPrice);
					cashPrice=minPrice;
				} else {
					Ext.getCmp('cpFee').setValue(price);
					cashPrice=price;
					cashCpFee=price;
				}
				Ext.getCmp('showMsg').getEl().update('<span style="color:red">该代理无协议价，已找到公布价，只能按重量计费！价格编号:'+respText1.result[0].id+'</span>');
			}else{
				Ext.getCmp('normCpRate').setValue(Number(respText1.result[0].stage2Rate));
			}
		} else {
			if(type=='st'){
				Ext.getCmp('cpRate').setValue(Number(respText1.result[0].stage3Rate));
				Ext.getCmp('normCpRate').setValue(Number(respText1.result[0].stage3Rate));
				var price = Math.round(Number(respText1.result[0].stage3Rate)* cqWeight);
				var minPrice = respText1.result[0].lowPrice;
				if (price < minPrice) {
					Ext.getCmp('cpFee').setValue(minPrice);
					cashPrice=minPrice;
					cashCpFee=minPrice;
				} else {
					Ext.getCmp('cpFee').setValue(price);
					cashPrice=price;
					cashCpFee=price;
				}
				Ext.getCmp('showMsg').getEl().update('<span style="color:red">该代理无协议价，已找到公布价，只能按重量计费！价格编号:'+respText1.result[0].id+'</span>');
			}else{
				Ext.getCmp('normCpRate').setValue(Number(respText1.result[0].stage3Rate));
			}
		}
	}
	*/
	//设置代理协议价

	/*
	function setCqFee(respText, valuationType, cqWeight) {
		if (valuationType == '重量') {
			if (cqWeight > 0 && cqWeight < 500) {
				Ext
						.getCmp('cpRate')
						.setValue(Number(respText.result[0].stage1Rate));
				var price = Math
						.round(Number(respText.result[0].stage1Rate)
								* cqWeight);
				var minPrice = respText.result[0].lowPrice;
				if (price < minPrice) {
					cashCpFee=minPrice;
					Ext.getCmp('showMsg').getEl().update('<span style="color:red">已找到代理协议价，按重量计费,取最低一票！价格编号:'+respText.result[0].id+'</span>');
					Ext.getCmp('cpFee').setValue(minPrice);
				} else {
					cashCpFee=price;
					Ext.getCmp('showMsg').getEl().update('<span style="color:red">已找到代理协议价，按重量计费！价格编号:'+respText.result[0].id+'</span>');
					Ext.getCmp('cpFee').setValue(price);
				}
			} else if (cqWeight >= 500 && cqWeight < 1000) {
				Ext
						.getCmp('cpRate')
						.setValue(Number(respText.result[0].stage2Rate));
				var price = Math
						.round(Number(respText.result[0].stage2Rate)
								* cqWeight);
				var minPrice = respText.result[0].lowPrice;
				if (price < minPrice) {
					cashCpFee=minPrice;
					Ext.getCmp('cpFee').setValue(minPrice);
					Ext.getCmp('showMsg').getEl().update('<span style="color:red">已找到代理协议价，按重量计费,取最低一票！价格编号:'+respText.result[0].id+'</span>');
				} else {
					cashCpFee=price;
					Ext.getCmp('showMsg').getEl().update('<span style="color:red">已找到代理协议价，按重量计费！价格编号:'+respText.result[0].id+'</span>');
					Ext.getCmp('cpFee').setValue(price);
				}
			} else {
				Ext
						.getCmp('cpRate')
						.setValue(Number(respText.result[0].stage3Rate));
				var price = Math
						.round(Number(respText.result[0].stage3Rate)
								* cqWeight);
				var minPrice = respText.result[0].lowPrice;
				if (price < minPrice) {
					cashCpFee=minPrice;
					Ext.getCmp('cpFee').setValue(minPrice);
					Ext.getCmp('showMsg').getEl().update('<span style="color:red">已找到代理协议价，按重量计费,取最低一票！价格编号:'+respText.result[0].id+'</span>');
				} else {
					cashCpFee=price;
					Ext.getCmp('cpFee').setValue(price);
					Ext.getCmp('showMsg').getEl().update('<span style="color:red">已找到代理协议价，按重量计费！价格编号:'+respText.result[0].id+'</span>');
				}
			}
		} else if (valuationType == '体积') {
			Ext.getCmp('cpRate').setValue(Number(respText.result[0].stage1Rate));
			var price=Math.round(Number(respText.result[0].stage1Rate)* Number(Ext.getCmp('bulk').getValue()));
			var minPrice=respText.result[0].lowPrice;
			if(price<minPrice){
				cashCpFee=minPrice;
				Ext.getCmp('cpFee').setValue(minPrice);
				Ext.getCmp('showMsg').getEl().update('<span style="color:red">已找到代理协议价，按体积计费,取最低一票！价格编号:'+respText.result[0].id+'</span>');
			}else{
				cashCpFee=price;
				Ext.getCmp('cpFee').setValue(price);
				Ext.getCmp('showMsg').getEl().update('<span style="color:red">已找到代理协议价，按体积计费！价格编号:'+respText.result[0].id+'</span>');
			}
			
		} else if (valuationType == '件数') {
			Ext.getCmp('cpRate').setValue(Number(respText.result[0].stage1Rate));
			var price=Math.round(Number(respText.result[0].stage1Rate)* Number(Ext.getCmp('piece').getValue()));
			var minPrice=respText.result[0].lowPrice;
			if(price<minPrice){
				cashCpFee=minPrice;
				Ext.getCmp('cpFee').setValue(minPrice);
				Ext.getCmp('showMsg').getEl().update('<span style="color:red">已找到代理协议价，按件数计费,取最低一票！价格编号:'+respText.result[0].id+'</span>');
			}else{
				cashCpFee=price;
				Ext.getCmp('cpFee').setValue(price);
				Ext.getCmp('showMsg').getEl().update('<span style="color:red">已找到代理协议价，按件数计费！价格编号:'+respText.result[0].id+'</span>');
			}
			
		}
	}
	*/
	//设置收货公布价
	/*
	function setConStFee(respText1,cqWeight,type){
		if (cqWeight >= 0 && cqWeight < 500) {
			if(type=='st'){
				Ext.getCmp('consigneeRate').setValue(Number(respText1.result[0].stage1Rate));
				Ext.getCmp('normConsigneeRate').setValue(Number(respText1.result[0].stage1Rate));
				var price = Math.round(Number(respText1.result[0].stage1Rate)* cqWeight);
				var minPrice = respText1.result[0].lowPrice;
				if (price < minPrice) {
					Ext.getCmp('consigneeFee')
							.setValue(minPrice);
					cashConsigneeFee=minPrice;
				} else {
					Ext.getCmp('consigneeFee').setValue(price);
					cashConsigneeFee=price;
				}
				Ext.getCmp('showMsg').getEl().update('<span style="color:red">该收货人无协议价，已找到公布价！价格编号：'+respText1.result[0].id+'</span>');
			}else{
				Ext.getCmp('normConsigneeRate').setValue(Number(respText1.result[0].stage1Rate));
			}
			
		} else if (cqWeight >= 500 && cqWeight < 1000) {
			if(type=='st'){
				Ext.getCmp('consigneeRate').setValue(Number(respText1.result[0].stage2Rate));
				Ext.getCmp('normConsigneeRate').setValue(Number(respText1.result[0].stage2Rate));
				var price = Math
					.round(Number(respText1.result[0].stage2Rate)
							* cqWeight);
				var minPrice = respText1.result[0].lowPrice;
				if (price < minPrice) {
					Ext.getCmp('consigneeFee')
							.setValue(minPrice);
					cashConsigneeFee=minPrice;
				} else {
					Ext.getCmp('consigneeFee').setValue(price);
					cashConsigneeFee=price;
				}
				Ext.getCmp('showMsg').getEl().update('<span style="color:red">该收货人无协议价，已找到公布价！价格编号：'+respText1.result[0].id+'</span>');
			}else{
				Ext.getCmp('normConsigneeRate').setValue(Number(respText1.result[0].stage2Rate));
			}
		} else {
			if(type=='st'){
				Ext.getCmp('consigneeRate').setValue(Number(respText1.result[0].stage3Rate));
				Ext.getCmp('normConsigneeRate').setValue(Number(respText1.result[0].stage3Rate));
				var price = Math.round(Number(respText1.result[0].stage3Rate)* cqWeight);
				var minPrice = respText1.result[0].lowPrice;
				if (price < minPrice) {
					Ext.getCmp('consigneeFee')
							.setValue(minPrice);
					cashConsigneeFee=minPrice;
				} else {
					Ext.getCmp('consigneeFee').setValue(price);
					cashConsigneeFee=price;
				}
				Ext.getCmp('showMsg').getEl().update('<span style="color:red">该收货人无协议价，已找到公布价！价格编号：'+respText1.result[0].id+'</span>');
			}else{
				Ext.getCmp('normConsigneeRate').setValue(Number(respText1.result[0].stage3Rate));
			}
		}
	}
	*/
	// 付款方为预付
	/*
	if (textfield.getValue() == '预付'||textfield.getValue()=='双方付') {
		var piece=Ext.getCmp('piece').getValue();
		var bulk=Ext.getCmp('bulk').getValue();
		// 判断是否为项目客户
		Ext.Ajax.request({
			url : sysPath + "/sys/customerAction!ralaList.action",
			params : {
				privilege : 61,
				filter_EQL_id:cusId,
				filter_EQL_isProjectcustomer:1
			},
			success : function(res) {
				var resText = Ext.util.JSON.decode(res.responseText);
				// 是项目客户
				if (resText.result.length > 0) {
					Ext.Ajax.request({
						url:sysPath+"/fi/basProjectRateAction!findRate.action",
						params:{
							piece:piece,
							weight:cqWeight,
							cusId:cusId,
							bulk:bulk,
							city:city
						},
						success:function(respp){
							var resppText = Ext.util.JSON.decode(respp.responseText);
							if(resppText.resultMap.length<1){
								Ext.getCmp('cpRate').setValue(0);
								Ext.getCmp('cpFee').setValue(0);
								Ext.Msg.alert(alertTitle,'此项目客户的协议价格不存在，请尽快维护!!');
								Ext.getCmp('showMsg').getEl().update('<span style="color:red">件数为:'+piece+',重量为:'+cqWeight+',体积为:'+bulk+',目的站为:'+city+'的项目客户协议价格不存在！</span>');
								return false;
							}else{
								var valuaType=resppText.resultMap[0].COUNT_WAY;
								var prate=Number(resppText.resultMap[0].RATE);
								var plowFee=Number(resppText.resultMap[0].LOW_FEE);
								var pprice=0;
								if(valuaType=='件数'){
									pprice=Number(Ext.getCmp('piece').getValue())*prate;
								}else if(valuaType=='重量'){
									pprice=cqWeight*prate;
								}
								else if(valuaType=='体积'){
									pprice=Number(Ext.getCmp('bulk').getValue())*prate;
								}
								if(pprice<plowFee){
									pprice=plowFee;
								}
								pprice = Math.round(pprice);
								cashPrice=pprice;
								Ext.getCmp('chargemodecombo').setValue(valuaType);
								Ext.getCmp('cpRate').setValue(prate);
								Ext.getCmp('cpFee').setValue(pprice);
								Ext.getCmp('showMsg').getEl().update('<span style="color:red">此代理为项目客户，已找到项目客户协议价！价格编号:'+resppText.resultMap[0].ID+'</span>');
								Ext.getCmp('traFee').setValue('');
								Ext.getCmp('traFeeRate').setValue('');
								Ext.getCmp('flightcombo').setDisabled(true);
								if(distributeMode == "中转"){
									findProjectTraFee(cusId,cqWeight,cusName,takeMode,addrType,trafficMode,disdepartId,piece,bulk,goWhereId,valuationType,disdepartName);
								}
							}
						}
					});
				} else {
					// 取代理协议价
					Ext.Ajax.request({
						url : sysPath + "/fi/cqCorporateRateAction!findRate.action",
						params : {
							valuationType : valuationType,
							cusId : cusId,
							limit : pageSize,
							takeMode : takeMode,
							trafficMode : trafficMode,
							distributionMode : distributeMode,
							addressType : addrType,
							departId : bussDepart,
							startCity : startCity,
							city : city,
							town : town,
							street : street
						},
						success : function(resp) {
							var respText = Ext.util.JSON.decode(resp.responseText);
							// 该代理目的站没有协议价格
							if (respText.result.length < 1) {
								Ext.Ajax.request({
									url : sysPath + "/fi/cqCorporateRateAction!list.action",
									params : {
										filter_EQS_valuationType : valuationType,
										filter_EQL_cusId : cusId,
										filter_EQS_takeMode : takeMode,
										filter_EQS_trafficMode : trafficMode,
										filter_EQS_distributionMode : distributeMode,
										filter_EQL_status : 2,
										limit : pageSize,
										filter_EQS_addressType : addrType,
										filter_EQL_departId : bussDepart,
										filter_EQS_startAddr : startCity,
										filter_GED_endDate : new Date().format('Y-m-d'),
										filter_LED_startDate : new Date().format('Y-m-d')
									},
									success : function(resp1) {
										var respText1 = Ext.util.JSON
												.decode(resp1.responseText);
										// 该代理无协议价格 取公布价
										if (respText1.result.length < 1) {
											findNormpubprice('st');
										} else {
											setCqFee(respText1, valuationType, cqWeight);
											findNormpubprice('cq');
										}
									}
								});
								// 有协议价格
							} else {
								setCqFee(respText, valuationType, cqWeight);
								findNormpubprice('cq');
							}
						}
					});
					
				}
			}
		});
		Ext.getCmp('consigneeRate').setValue("");
		Ext.getCmp('consigneeFee').setValue("");
		Ext.getCmp('normConsigneeRate').setValue('');
		// 付款方为到付
	} else if (textfield.getValue() == '到付') {
		var tel = Ext.getCmp('consigneeTel').getValue();
		var name = Ext.getCmp('consignee').getValue();
		var tels;
		if (tel.indexOf('/') < 0) {
			tels = tel;
		} else {
			tels = tel.split('/');
		}
		Ext.Ajax.request({
			url : sysPath + "/sys/conDealPriceAction!findRate.action",
			params : {
				limit : pageSize,
				tels : tels
			},
			success : function(resp) {
				var respText = Ext.util.JSON.decode(resp.responseText);
				// 此收货人无协议价格
				if (respText.result.length < 1) {
					findNormconprice('st');
				} else {
					findNormconprice('cq');
					setConsigneeFee(takeMode,respText,cqWeight);
				}
			}
		});
		Ext.getCmp('cpRate').setValue("");
		Ext.getCmp('cpFee').setValue("");
		Ext.getCmp('normCpRate').setValue('');
		//双方付
	}
	*/

}
//设置专车协议价
function setSpecialRate(respText1){
	var who=Ext.getCmp('payerCombo').getValue();
	var sondId='';
	if(who=='预付'||who=='双方付'){
		sondId='cpSonderzugPrice';
	}else if(who=='到付'){
		sondId='sonderzugPrice';
	}
	var carType=Ext.getCmp('cartypecombo').getValue();
	if(respText1.resultMap.length>1){
		if(carType=="金杯车"){
			totalSonPrice=respText1.resultMap[0].GOLD_CUP_CAR;
			Ext.getCmp(sondId).setValue(respText1.resultMap[0].GOLD_CUP_CAR);
		}else if(carType=="面包车"){
			totalSonPrice=respText1.resultMap[0].VAN;
			Ext.getCmp(sondId).setValue(respText1.resultMap[0].VAN);
		}else if(carType=="冷藏车"){
			totalSonPrice=respText1.resultMap[0].CHILL_CAR;
			Ext.getCmp(sondId).setValue(respText1.resultMap[0].CHILL_CAR);
		}else if(carType=="2吨车"){
			totalSonPrice=respText1.resultMap[0].TWO_TON_CAR;
			Ext.getCmp(sondId).setValue(respText1.resultMap[0].TWO_TON_CAR);
		}else if(carType=="3吨车"){
			totalSonPrice=respText1.resultMap[0].THREE_TON_CAR;
			Ext.getCmp(sondId).setValue(respText1.resultMap[0].THREE_TON_CAR);
		}else if(carType=="5吨车"){
			totalSonPrice=respText1.resultMap[0].FIVE_TON_CAR;
			Ext.getCmp(sondId).setValue(respText1.resultMap[0].FIVE_TON_CAR);
		}
	}else{
		if(carType=="金杯车"){
			totalSonPrice=respText1.resultMap[0].GOLD_CUP_CAR;
			Ext.getCmp(sondId).setValue(respText1.resultMap[0].GOLD_CUP_CAR);
		}else if(carType=="面包车"){
			totalSonPrice=respText1.resultMap[0].VAN;
			Ext.getCmp(sondId).setValue(respText1.resultMap[0].VAN);
		}else if(carType=="冷藏车"){
			totalSonPrice=respText1.resultMap[0].CHILL_CAR;
			Ext.getCmp(sondId).setValue(respText1.resultMap[0].CHILL_CAR);
		}else if(carType=="2吨车"){
			totalSonPrice=respText1.resultMap[0].TWO_TON_CAR;
			Ext.getCmp(sondId).setValue(respText1.resultMap[0].TWO_TON_CAR);
		}else if(carType=="3吨车"){
			totalSonPrice=respText1.resultMap[0].THREE_TON_CAR;
			Ext.getCmp(sondId).setValue(respText1.resultMap[0].THREE_TON_CAR);
		}else if(carType=="5吨车"){
			totalSonPrice=respText1.resultMap[0].FIVE_TON_CAR;
			Ext.getCmp(sondId).setValue(respText1.resultMap[0].FIVE_TON_CAR);
		}
	}
	
}
//设置标准专车价格
function setNormSpecialRate(respText1){
	var carType=Ext.getCmp('cartypecombo').getValue();
	if(respText1.resultMap.length>1){
		if(carType=="金杯车"){
			Ext.getCmp('normSonderzugPrice').setValue(respText1.resultMap[0].GOLD_CUP_CAR);
		}else if(carType=="面包车"){
			Ext.getCmp('normSonderzugPrice').setValue(respText1.resultMap[0].VAN);
		}else if(carType=="冷藏车"){
			Ext.getCmp('normSonderzugPrice').setValue(respText1.resultMap[0].CHILL_CAR);
		}else if(carType=="2吨车"){
			Ext.getCmp('normSonderzugPrice').setValue(respText1.resultMap[0].TWO_TON_CAR);
		}else if(carType=="3吨车"){
			Ext.getCmp('normSonderzugPrice').setValue(respText1.resultMap[0].THREE_TON_CAR);
		}else if(carType=="5吨车"){
			Ext.getCmp('normSonderzugPrice').setValue(respText1.resultMap[0].FIVE_TON_CAR);
		}
	}else{
		if(carType=="金杯车"){
			Ext.getCmp('normSonderzugPrice').setValue(respText1.resultMap[0].GOLD_CUP_CAR);
		}else if(carType=="面包车"){
			Ext.getCmp('normSonderzugPrice').setValue(respText1.resultMap[0].VAN);
		}else if(carType=="冷藏车"){
			Ext.getCmp('normSonderzugPrice').setValue(respText1.resultMap[0].CHILL_CAR);
		}else if(carType=="2吨车"){
			Ext.getCmp('normSonderzugPrice').setValue(respText1.resultMap[0].TWO_TON_CAR);
		}else if(carType=="3吨车"){
			Ext.getCmp('normSonderzugPrice').setValue(respText1.resultMap[0].THREE_TON_CAR);
		}else if(carType=="5吨车"){
			Ext.getCmp('normSonderzugPrice').setValue(respText1.resultMap[0].FIVE_TON_CAR);
		}
	}
	
}
//查询中转协议价
/*
function findTraFee(cusName,trafficMode,takeMode,addrType,disdepartId,valuationType,cqWeight,disdepartName){
	Ext.Ajax.request({
		url : sysPath + "/fi/basTraShipmentRateAction!ralaList.action",
		params : {
			limit : pageSize,
			privilege : 100,
			filter_EQS_cusName :cusName,
			filter_EQS_trafficMode : trafficMode,
			filter_EQS_takeMode : takeMode,
			filter_EQS_areaType : addrType,
			filter_EQL_status : 2,
			filter_EQL_departId : disdepartId,
			filter_EQS_valuationType:valuationType,
			filter_GED_endDate : new Date().format('Y-m-d'),
			filter_LED_startDate : new Date().format('Y-m-d')
		},
		success : function(resp) {
			var respText = Ext.util.JSON.decode(resp.responseText);
			if (respText.result.length < 1) {
				// 无协议价格
				Ext.Msg.alert(alertTitle,'<span style="color:red">供应商:'+cusName+',运输方式:'+trafficMode+',<br/>提货方式:'+takeMode+',地址类型:'+addrType+',部门:'+disdepartName+'的中转协议价格不存在！</span>');
				Ext.getCmp('showMsg').getEl().update('<span style="color:red">供应商:'+cusName+',运输方式:'+trafficMode+',提货方式:'+takeMode+',地址类型:'+addrType+',部门:'+disdepartName+'的中转协议价格不存在！</span>');
				return false;
			} else {
				setTraFeeRate(respText,cqWeight);
			}
		}
	});
}
*/
//设置中转协议价
/*
function setTraFeeRate(respText,cqWeight){
	var valuationType1=respText.result[0].valuationType;
	if(valuationType1=='重量'){
		if (cqWeight >= 0 && cqWeight < 500) {
			
			Ext
					.getCmp('traFeeRate')
					.setValue(Number(respText.result[0].stage1Rate));
			Ext
					.getCmp('normTraRate')
					.setValue(Number(respText.result[0].stage1Rate));
			var price = Math
					.round(Number(respText.result[0].stage1Rate)
							* cqWeight);
			var minPrice = respText.result[0].lowPrice;
			if (price < minPrice) {
				Ext.getCmp('traFee').setValue(minPrice);
				cashTraFee=minPrice;
				Ext.getCmp('showMsg').getEl().update('<span style="color:red">已找到中转协议价,取最低一票！</span>');
			} else {
				Ext.getCmp('traFee').setValue(price);
				cashTraFee=price;
				Ext.getCmp('showMsg').getEl().update('<span style="color:red">已找到中转协议价,按500KG计费！</span>');
			}
		} else if (cqWeight >= 500 && cqWeight < 1000) {
			Ext
					.getCmp('traFeeRate')
					.setValue(Number(respText.result[0].stage2Rate));
			Ext
					.getCmp('normTraRate')
					.setValue(Number(respText.result[0].stage2Rate));
			var price = Math
					.round(Number(respText.result[0].stage2Rate)
							* cqWeight);
			var minPrice = respText.result[0].lowPrice;
			if (price < minPrice) {
				Ext.getCmp('traFee').setValue(minPrice);
				cashTraFee=minPrice;
				Ext.getCmp('showMsg').getEl().update('<span style="color:red">已找到中转协议价,取最低一票！</span>');
			} else {
				cashTraFee=price;
				Ext.getCmp('traFee').setValue(price);
				Ext.getCmp('showMsg').getEl().update('<span style="color:red">已找到中转协议价,按1000KG计费！</span>');
			}
		} else {
			Ext
					.getCmp('traFeeRate')
					.setValue(Number(respText.result[0].stage3Rate));
					Ext
					.getCmp('normTraRate')
					.setValue(Number(respText.result[0].stage3Rate));
			var price = Math
					.round(Number(respText.result[0].stage3Rate)
							* cqWeight);
			var minPrice = respText.result[0].lowPrice;
			if (price < minPrice) {
				cashTraFee=minPrice;
				Ext.getCmp('traFee').setValue(minPrice);
				Ext.getCmp('showMsg').getEl().update('<span style="color:red">已找到中转协议价,取最低一票！</span>');
			} else {
				cashTraFee=price;
				Ext.getCmp('traFee').setValue(price);
				Ext.getCmp('showMsg').getEl().update('<span style="color:red">已找到中转协议价,按1000KG以上计费！</span>');
			}
		}
	}else if (valuationType1 == '体积') {
		Ext.getCmp('traFeeRate').setValue(Number(respText.result[0].stage1Rate));
		var price=Math.round(Number(respText.result[0].stage1Rate)* Number(Ext.getCmp('bulk').getValue()));
		var minPrice=respText.result[0].lowPrice;
		if(price<minPrice){
			cashTraFee=minPrice;
			Ext.getCmp('traFee').setValue(minPrice);
			Ext.getCmp('showMsg').getEl().update('<span style="color:red">已找到中转协议价，按体积计费,取最低一票！价格编号:'+respText.result[0].id+'</span>');
		}else{
			cashTraFee=price;
			Ext.getCmp('traFee').setValue(price);
			Ext.getCmp('showMsg').getEl().update('<span style="color:red">已找到中转协议价，按体积计费！价格编号:'+respText.result[0].id+'</span>');
		}
							
	} else if (valuationType1 == '件数') {
		Ext.getCmp('traFeeRate').setValue(Number(respText.result[0].stage1Rate));
		var price=Math.round(Number(respText.result[0].stage1Rate)* Number(Ext.getCmp('piece').getValue()));
		var minPrice=respText.result[0].lowPrice;
		if(price<minPrice){
			cashTraFee=minPrice;
			Ext.getCmp('traFee').setValue(minPrice);
			Ext.getCmp('showMsg').getEl().update('<span style="color:red">已找到中转协议价，按件数计费,取最低一票！价格编号:'+respText.result[0].id+'</span>');
		}else{
			cashTraFee=price;
			Ext.getCmp('traFee').setValue(price);
			Ext.getCmp('showMsg').getEl().update('<span style="color:red">已找到中转协议价，按件数计费！价格编号:'+respText.result[0].id+'</span>');
		}
							
	}
}
*/
//查询项目客户中转协议价
/*
function findProjectTraFee(cusId,cqWeight,cusName,takeMode,addrType,trafficMode,disdepartId,piece,bulk,goWhereId,valuationType,disdepartName){
	Ext.Ajax.request({
		url : sysPath + "/fi/basProjCusTransitRateAction!findProTraRate.action",
		params : {
			limit : pageSize,
			cusId :cusId,
			cpId:goWhereId,
			takeMode:takeMode,
			trafficMode:trafficMode,
			areaType:addrType,
			disDepartId:disdepartId,
			weight:cqWeight,
			bulk:bulk,
			piece:piece
		},
		success : function(resp) {
			var respText = Ext.util.JSON.decode(resp.responseText);
			if (respText.resultMap.length < 1) {
				//该项目客户针对该供应商没有协议价
				Ext.Ajax.request({
					url : sysPath + "/fi/basProjCusTransitRateAction!findProTraRate.action",
					params : {
						limit : pageSize,
						cusId :cusId,
						takeMode:takeMode,
						trafficMode:trafficMode,
						areaType:addrType,
						disDepartId:disdepartId,
						weight:cqWeight,
						bulk:bulk,
						piece:piece
					},success:function(resp1){
						var respText1 = Ext.util.JSON.decode(resp1.responseText);
						if(respText1.resultMap.length<1){
							//此项目客户没有中转协议价，取标准协议价
							findTraFee(cusName,trafficMode,takeMode,addrType,disdepartId,valuationType,cqWeight,disdepartName);
						}else{
							setProTraRate(respText1,cqWeight);
						}
					}
				});
			} else {
				setProTraRate(respText,cqWeight);
			}
		}
  });
}
*/
//设置项目客户中转协议价
/*
function setProTraRate(respText,cqWeight){
	var valuaType=respText.resultMap[0].VALUATION_TYPE;
	var prate=Number(respText.resultMap[0].RATE);
	var plowFee=Number(respText.resultMap[0].LOW_FEE);
	var pprice=0;
	if(valuaType=='件数'){
		pprice=Number(Ext.getCmp('piece').getValue())*prate;
	}else if(valuaType=='重量'){
		pprice=cqWeight*prate;
	}
	else if(valuaType=='体积'){
		pprice=Number(Ext.getCmp('bulk').getValue())*prate;
	}
	if(pprice<plowFee){
		pprice=plowFee;
	}
	pprice=Math.round(pprice);
	//Ext.getCmp('chargemodecombo').setValue(valuaType);
	Ext.getCmp('traFeeRate').setValue(prate);
	Ext.getCmp('traFee').setValue(pprice);
	cashTraFee=pprice;
	Ext.getCmp('showMsg').getEl().update('<span style="color:red">此代理为项目客户，已找到项目客户中转协议价！价格编号:'+respText.resultMap[0].ID+'</span>');
}
*/
//设置收货人协议价
/*
function setConsigneeFee(takeMode,respText,cqWeight){
	if (takeMode == "机场自提") {
			var flyOwnPrice = respText.result[0].flyOwnPrice;
			var minPrice = respText.result[0].flyOwnMinPrice;
			var price = Math.round(Number(flyOwnPrice) * cqWeight);
			Ext.getCmp('consigneeRate').setValue(flyOwnPrice);
			if (price < minPrice) {
				Ext.getCmp('consigneeFee').setValue(minPrice);
				cashConsigneeFee=minPrice;
			} else {
				Ext.getCmp('consigneeFee').setValue(price);
				cashConsigneeFee=price;
			}
		} else if (takeMode == "市内自提") {
			var cityOwnPrice = respText.result[0].cityOwnPrice;
			var price = Math.round(Number(cityOwnPrice) * cqWeight);
			var minPrice = respText.result[0].cityOwnMinPrice;
			Ext.getCmp('consigneeRate').setValue(cityOwnPrice);
			if (price < minPrice) {
				Ext.getCmp('consigneeFee').setValue(minPrice);
				cashConsigneeFee=minPrice;
			} else {
				Ext.getCmp('consigneeFee').setValue(price);
				cashConsigneeFee=price;
			}
		} else if (takeMode == "市内送货") {
			var citySendPrice = respText.result[0].citySendPrice;
			var price = Math
					.round(Number(citySendPrice) * cqWeight);
			var minPrice = respText.result[0].citySendMinPrice;
			Ext.getCmp('consigneeRate').setValue(citySendPrice);
			if (price < minPrice) {
				Ext.getCmp('consigneeFee').setValue(minPrice);
				cashConsigneeFee=minPrice;
			} else {
				Ext.getCmp('consigneeFee').setValue(price);
				cashConsigneeFee=price;
			}
		}
		Ext.getCmp('showMsg').getEl().update('<span style="color:red">此收货人有特殊协议价，按特殊协议价计费！价格编号：'+respText.result[0].id+'</span>');
}
*/
var islocation=false;//是否为定位修改
/**
 * 定位修改
 * @param {} record
 * @param {} win
 */
function locationSearch(record,win){
	islocation=true;
	var dno=record.data.dno;
	faxStore.reload({
		params:{
			filter_EQS_dno:dno
		}
	});
	
	win.close();
}
/**
 * 保存前一点点验证
 * @param {} form
 * @return {Boolean}
 */
function msgValidate(form){
	
	var chargeMode=Ext.getCmp('chargemodecombo').getValue();
	if(chargeMode=="体积"&&(Ext.getCmp('bulk').getValue()==''||Ext.getCmp('bulk').getValue()==0)){
		Ext.getCmp('addDetailBtn').setDisabled(false);
		Ext.getCmp('bulk').focus(true,true);
		Ext.Msg.alert(alertTitle,"体积不能为空!!");
		return false;
	}
	if(form.findByType("checkbox")[0].getValue()){
		var sonderPrice=Ext.getCmp('sonderzugPrice').getValue();
		var cpsonderPrice=Ext.getCmp('cpSonderzugPrice').getValue();
		if((sonderPrice==''||sonderPrice==0)&&(cpsonderPrice==''||cpsonderPrice==0)){
			Ext.getCmp('addDetailBtn').setDisabled(false);
			Ext.Msg.alert(alertTitle,'无专车费，无法保存!!');
			return false;
		}
	}
	if(Ext.getCmp('distributionMode').getValue()=='中转'){
		if(Ext.getCmp('traFee').getValue()==''||Ext.getCmp('traFee').getValue()==0){
			Ext.getCmp('addDetailBtn').setDisabled(false);
			Ext.Msg.alert(alertTitle,'无中转费，无法保存!!');
			return false;
		}
		if(Number(Ext.getCmp('traFee').getValue())>cashTraFee){
			Ext.getCmp('addDetailBtn').setDisabled(false);
			Ext.Msg.alert(alertTitle,'中转费比系统计算的'+cashTraFee+'元大，无法保存!!');
			return false;
		}
	}
	if(Ext.getCmp('payerCombo').getValue()=='预付'){
		if(Number(Ext.getCmp('cpFee').getValue())<cashCpFee){
			Ext.getCmp('addDetailBtn').setDisabled(false);
			Ext.Msg.alert(alertTitle,'预付提送费比系统计算的'+cashCpFee+'元小,无法保存!');
			return false;
		}
	}
	if(Ext.getCmp('payerCombo').getValue()=='预付'&&(Ext.getCmp('cpFee').getValue()==''||Ext.getCmp('cpFee').getValue()==0)){
		Ext.getCmp('addDetailBtn').setDisabled(false);
		Ext.Msg.alert(alertTitle,'预付提送费为空,无法保存!');
		return false;
	}
	if(Ext.getCmp('payerCombo').getValue()=='到付'){
		if(Number(Ext.getCmp('consigneeFee').getValue())<cashConsigneeFee){
			Ext.getCmp('addDetailBtn').setDisabled(false);
			Ext.Msg.alert(alertTitle,'到付提送费比系统计算的'+cashConsigneeFee+'元小,无法保存!');
			return false;
		}
	}
	if(Ext.getCmp('payerCombo').getValue()=='到付'&&(Ext.getCmp('consigneeFee').getValue()==''||Ext.getCmp('consigneeFee').getValue()==0)){
		Ext.getCmp('addDetailBtn').setDisabled(false);
		Ext.Msg.alert(alertTitle,'到付提送费为空,无法保存!');
		return false;
	}
	if(Ext.getCmp('payerCombo').getValue()=='双方付'){
		var cpFee=Number(Ext.getCmp('cpFee').getValue());
		var consigneeFee=Number(Ext.getCmp('consigneeFee').getValue());
		var cpSonderPrice=Number(Ext.getCmp('cpSonderzugPrice').getValue());
		var sonderPrice=Number(Ext.getCmp('sonderzugPrice').getValue());
		if((cpFee+consigneeFee)<cashPrice){
			Ext.getCmp('addDetailBtn').setDisabled(false);
			Ext.Msg.alert(alertTitle,'预付提送费+到付提送费不能小于'+cashPrice+'元!');
			return false;
		}
		if (form.findByType("checkbox")[0].getValue()) {
			if(cpSonderPrice+sonderPrice<totalSonPrice){
				Ext.getCmp('addDetailBtn').setDisabled(false);
				Ext.Msg.alert(alertTitle,'预付专车费+到付专车费不能小于'+totalSonPrice+'元!');
				return false;
			}
		}
	}
	return true;
}
function changeBgcolor(sourceId,targetId){
	if(sourceId!='addDetailBtn'){
		Ext.getCmp(sourceId).el.setStyle({
			'backgroundColor':'#FFFFFF'
		});
	}
	if(targetId!='addDetailBtn'){
		Ext.getCmp(targetId).el.setStyle({
			'backgroundColor':'#50BD4A'
		});
	}
}
function fnSumInfo(flightMainNo) {
	Ext.Ajax.request({
		url : sysPath+'/fax/oprFaxInAction!inquerySum.action',
		params:{
			start : 0,
			flightMainNo:flightMainNo,
			createTime:new Date().add(Date.DAY, -1).format('Y-m-d'),
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

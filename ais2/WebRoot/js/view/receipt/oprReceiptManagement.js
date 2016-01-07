//回单管理
var privilege=85;//权限参数
var gridSearchUrl="receipt/oprReceiptAction!ralaList.action";//查询权限地址
var gridSearchListUrl="receipt/oprReceiptAction!list.action";//查询地址
var gridSearchSqlUrl="receipt/oprReceiptAction!sqlRalaList.action";//查询地址
var saveReportUrl="receipt/oprReceiptAction!saveReport.action";//回单入库点到地址
var saveOutUrl="receipt/oprReceiptAction!saveOut.action";//回单寄出地址
var saveGetUrl="receipt/oprReceiptAction!saveGet.action";//回单领取地址
var saveConfirmUrl="receipt/oprReceiptAction!saveConfirm.action";//回单确收地址
var cancelConfirmUrl="receipt/oprReceiptAction!cancelConfirm.action";//回单确收撤销地址
var cancelOutUrl="receipt/oprReceiptAction!cancelOut.action";//回单寄出撤销地址
var dictionaryUrl='sys/dictionaryAction!ralaList.action';//数据字典地
var searchCusNameUrl='sys/customerAction!list.action';//查询代理公司地址
var gridSearchNodeUrl='stock/oprNodeAction!list.action';//货物状态节点表
var cusServiceUrl='user/userAction!list.action';//寄出人查询地址
var consigneeStoreUrl='sys/conInfoAction!list.action';//收货人查询地址
var searchFaxUrl='fax/oprFaxInAction!list.action';//传真表查询地址
var searchCpNameUrl='stock/oprStoreAreaAction!findCusName.action';//查询中转外发公司地址
var searchWidth=80;
var defaultWidth=80; 
var searchDno;
var addCount;
var pubauthorityDepart=bussDepart;
var record_start=0;
		
		//寄出人 cusServiceUrl
		cusServiceStore=new Ext.data.Store({
	        autoLoad:true,
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
		//货物状态
		var goodsnewStore= new Ext.data.SimpleStore({
			 autoLoad:true, //此处设置为自动加载
  			 data:[ ['','--全部--'],
  			 		['om.status>0','到车确认'],['om.status=0','未到车确认'],
  			 		['t.reach_status>0','入库点到'],['(t.reach_status=0 or t.reach_status is null)','未入库点到'],
  			 		['t.out_status=3','货物预配'],
  			 		['t.out_status=1','已出库'],['(t.out_status=0 or t.out_status is null)','未出库'],
  			 		['f.SONDERZUG=0','非专车'],['f.SONDERZUG=1','专车']],
   			 fields:['goodsStatusValue',"goodsStatusName"]
		});
	
  		//配送方式4
		distributionModeStore	= new Ext.data.Store({ 
			storeId:"distributionModeStore",
			baseParams:{filter_EQL_basDictionaryId:4,privilege:16},
			proxy:new Ext.data.HttpProxy({url:sysPath+"/"+dictionaryUrl}),
			reader:new Ext.data.JsonReader({
	                    root:'result',
	                    totalProperty:'totalCount'
	        },[
        	{name:'distributionModeName',mapping:'typeName'}
        	])
		});
		 //提货方式14
		takeModeStore	= new Ext.data.Store({ 
			storeId:"takeModeStore",
			baseParams:{filter_EQL_basDictionaryId:14,privilege:16},
			proxy:new Ext.data.HttpProxy({url:sysPath+"/"+dictionaryUrl}),
			reader:new Ext.data.JsonReader({
	                    root:'result',
	                    totalProperty:'totalCount'
	        },[
        	{name:'takeModeId',mapping:'typeCode'},
        	{name:'takeModeName',mapping:'typeName'}
        	])
		});
		 //回单类型15
		receiptTypeStore	= new Ext.data.Store({ 
			storeId:"receiptTypeStore",
			baseParams:{filter_EQL_basDictionaryId:15,privilege:16},
			proxy:new Ext.data.HttpProxy({url:sysPath+"/"+dictionaryUrl}),
			reader:new Ext.data.JsonReader({
	                    root:'result',
	                    totalProperty:'totalCount'
	        },[
        	{name:'receiptTypeName',mapping:'typeName'}
        	])
		});
		//代理公司
		cpNameStore= new Ext.data.Store({ 
			storeId:"cpNameStore",
			proxy:new Ext.data.HttpProxy({url:sysPath+"/"+searchCusNameUrl}),
			reader:new Ext.data.JsonReader({
	                    root:'result', totalProperty:'totalCount'
	        },[
        	{name:'cusName',mapping:'cusName'},
        	{name:'cusId',mapping:'id'}
        	])
		});
		//寄出方式
		outWayStore= new Ext.data.Store({ 
			storeId:"outWayStore",
			baseParams:{filter_EQL_basDictionaryId:29,privilege:16},
			proxy:new Ext.data.HttpProxy({url:sysPath+"/"+dictionaryUrl}),
			reader:new Ext.data.JsonReader({
	                    root:'result',
	                    totalProperty:'totalCount'
	        },[
        	{name:'outWayName',mapping:'typeName'}
        	])
		});
		//收货人Store
	   consigneeStore=new Ext.data.Store({
		storeId:"consigneeStore",
		proxy:new Ext.data.HttpProxy({url:sysPath+"/"+consigneeStoreUrl}),
		reader:new Ext.data.JsonReader({
                 root:'result',
                 totalProperty:'totalCount'
        },[
        	{name:'consigneeName'}
        	])
	});
	//中转外发公司
		cpNameStore2= new Ext.data.Store({ 
			storeId:"cpNameStore2",
			baseParams:{privilege:69},
			proxy:new Ext.data.HttpProxy({url:sysPath+"/"+searchCpNameUrl}),
			reader:new Ext.data.JsonReader({
	                   // root:'result', totalProperty:'totalCount'
	        },[
        	{name:'cusName',mapping:'cusName'},
        	{name:'cusId',mapping:'id'}
        	])
		});
		isNotExceptionStore=new Ext.data.SimpleStore({
			 autoLoad:true, //此处设置为自动加载
  			 data:[['','-请选择-'],['2','是'],['1','否']],
   			 fields:["isNotExceptionId","isNotExceptionName"]
		});
		
		statusStore=new Ext.data.SimpleStore({
			 autoLoad:true, //此处设置为自动加载
  			 data:[['','-请选择-'],['1','已入库'],['2','已领取'],['3','已出库'],['4','已确收'],['5','已寄出'],['6','已扫描']],
   			 fields:["statusId","statusName"]
		});
		
		confirmStatusStore=new Ext.data.SimpleStore({
			 autoLoad:true, //此处设置为自动加载
  			 data:[[1,'正常'],[2,'异常']],
   			 fields:["confirmStatusId","confirmStatusName"]
		});	
		printStore=new Ext.data.SimpleStore({
			 autoLoad:true, //此处设置为自动加载
  			  data:[['','全部'],['0','未开单'],['1','已开单']],
   			 fields:["printId","printName"]
		});	
/*
var  fields=[{name:'id',mapping:'id'},{name:'dno',mapping:'dno'},'printNo','printNum','receiptType','reachStatus','reachNum','reachMan','reachTime','getStatus','getMan','getTime','confirmStatus','confirmNum','confirmMan',
				'confirmTime','confirmRemark','outStatus','outWay','outNo','outCompany','outMan','outTime','outCost','scanStauts','scanMan','scanTime','scanAddr','createName','createTime','updateName','updateTime','ts',
				'curStatus','distributionMode','gowhere','cpName','prewiredId','overmemoId','request'];
*/
var  fields=[
	{name:'id',mapping:'ID'},{name:'dno',mapping:'DNO'},
	{name:'printNo',mapping:'PRINTNO'},
	{name:'printNum',mapping:'PRINTNUM'},
	{name:'receiptType',mapping:'RECEIPTTYPE'},
	{name:'reachStatus',mapping:'REACHSTATUS'},
	{name:'reachNum',mapping:'REACHNUM'},
	{name:'reachMan',mapping:'REACHMAN'},
	{name:'reachTime',mapping:'REACHTIME'},
	{name:'getStatus',mapping:'GETSTATUS'},
	{name:'getMan',mapping:'GETMAN'},{name:'subNo',mapping:'SUBNO'},
	{name:'getTime',mapping:'GETTIME'},
	{name:'confirmStatus',mapping:'CONFIRMSTATUS'},
	{name:'confirmNum',mapping:'CONFIRMNUM'},{name:'confirmMan',mapping:'CONFIRMMAN'},
	{name:'confirmTime',mapping:'CONFIRMTIME'},{name:'confirmRemark',mapping:'CONFIRMREMARK'},
	{name:'outStatus',mapping:'OUTSTATUS'},{name:'outWay',mapping:'OUTWAY'},{name:'outNo',mapping:'OUTNO'},
	{name:'outCompany',mapping:'OUTCOMPANY'},{name:'outMan',mapping:'OUTMAN'},{name:'outTime',mapping:'OUTTIME'},
	{name:'outCost',mapping:'OUTCOST'},{name:'scanStatus',mapping:'SCANSTATUS'},{name:'scanMan',mapping:'SCANMAN'},
	{name:'scanTime',mapping:'SCANTIME'},{name:'scanAddr',mapping:'SCANADDR'},{name:'createName',mapping:'CREATENAME'},
	{name:'createTime',mapping:'CREATETIME'},{name:'updateName',mapping:'UPDATENAME'},{name:'updateTime',mapping:'UPDATETIME'},
	{name:'ts',mapping:'TS'},{name:'curStatus',mapping:'CURSTATUS'},{name:'distributionMode',mapping:'DISTRIBUTIONMODE'},
	{name:'gowhere',mapping:'GOWHERE'},{name:'cpName',mapping:'CPNAME'},{name:'prewiredId',mapping:'PREWIREDID'},
	{name:'overmemoId',mapping:'OVERMEMOID'},{name:'goodsStatus',mapping:'GOODSSTATUS'},{name:'getNum',mapping:'GETNUM'},
	{name:'outStockNum',mapping:'OUTSTOCKNUM'},{name:'outStockMan',mapping:'OUTSTOCKMAN'},{name:'urgentStatus',mapping:'URGENTSTATUS'},
	{name:'outStockTime',mapping:'OUTSTOCKTIME'},{name:'outStockStatus',mapping:'OUTSTOCKSTATUS'},
	{name:'signMan',mapping:'SIGNMAN'},'SCAN_NUM','CONSIGNEE','PIECE','TRAFFIC_MODE','FLIGHT_MAIN_NO','TAKE_MODE','ADDR','SONDERZUG','FLIGHT_NO','REALGOWHERE'];

	  var menu = new Ext.menu.Menu({
	    id: 'receiptMenu',
	    items: [{text:'<b>回单入库</b>',iconCls : 'group',tooltip : '入库点到',handler:function() {
            var receipt =Ext.getCmp('receiptCenter');
			var _records = receipt.getSelectionModel().getSelections();
//				for(var i=0;i<_records.length;i++){
//					if(_records[i].data.receiptType.indexOf('原件')<0){
//						Ext.Msg.alert(alertTitle,'只有原件签单才允许入库！');
//						return;
//					}
//				}
					if (_records.length < 1) {
						receiptReport(null);
					}else{
						if(isNotAllow(_records,null)){
							receiptReport(_records);
						}
					}
            } },{text:'<b>回单领取</b>',iconCls : 'group',tooltip : '回单领取',handler:function() {
            var receipt =Ext.getCmp('receiptCenter');
			var _records = receipt.getSelectionModel().getSelections();
	
					if (_records.length < 1) {
						Ext.Msg.alert(alertTitle, "请选择您要操作的行！");
						return false;
					} 
					
					if(isNotAllow(_records,'已入库')){
						receiptGet(_records);
					}
            } },{text:'<b>回单确收</b>',iconCls : 'group',tooltip : '回单确收',handler:function() {
            var receipt =Ext.getCmp('receiptCenter');
				var _records = receipt.getSelectionModel().getSelections();
				receiptConfirm();
					 
            } },{text:'<b>回单寄出</b>',iconCls : 'group',tooltip : '回单寄出',
            	handler:function() {
					receiptOut();
            	}
             }
				    ]
		});
		
		var cancelmenu = new Ext.menu.Menu({
	    items: [{text:'<b>撤销确收</b>',iconCls : 'group',tooltip : '撤销确收',handler:function() {
            var receipt =Ext.getCmp('receiptCenter');
			var _records = receipt.getSelectionModel().getSelections();
				if (_records.length < 1) {
					Ext.Msg.alert(alertTitle, "请选择您要撤销的回单！");
				}else{
					//撤销确收
					var ids='';
					for(var i=0;i<_records.length;i++){
						 ids+=_records[i].data.id+',';
					}
					var alertmsg="您确定要撤销确收这"+_records.length+"票货吗？";
					if(isNotAllow(_records,'已确收')){
					Ext.Msg.confirm(alertTitle, alertmsg, function(
							btnYes) {
						if (btnYes == 'yes' || btnYes == 'ok' || btnYes == true) {
								Ext.Ajax.request({
									url : sysPath+'/'
											+ cancelConfirmUrl+"?privilege="+privilege,
									params :{receiptIds:ids},
									success : function(resp) {
										var respText = Ext.util.JSON.decode(resp.responseText);
										if(respText.success){
											Ext.Msg.alert(alertTitle, "撤销成功！");
											searchreceipt();
										}else{
											Ext.Msg.alert(alertTitle, respText.msg);
										}	
									}
								});
							}
						});
					}
				}
				
            } },{text:'<b>撤销寄出</b>',iconCls : 'group',tooltip : '撤销寄出',handler:function() {
            var receipt =Ext.getCmp('receiptCenter');
			var _records = receipt.getSelectionModel().getSelections();
	
				if (_records.length < 1) {
					Ext.Msg.alert(alertTitle, "请选择您要撤销的回单！");
				}else{
					if(isNotAllow(_records,'已寄出')){
					//撤销寄出
					var ids='';
					for(var i=0;i<_records.length;i++){
						 ids+=_records[i].data.id+',';
					}
					var alertmsg="您确定要撤销寄出这"+_records.length+"票货吗？";
					if(isNotAllow(_records,'已寄出')){
					Ext.Msg.confirm(alertTitle, alertmsg, function(
							btnYes) {
						if (btnYes == 'yes' || btnYes == 'ok' || btnYes == true) {
								Ext.Ajax.request({
									url : sysPath+'/'
											+ cancelOutUrl+"?privilege="+privilege,
									params :{receiptIds:ids},
									success : function(resp) {
										var respText = Ext.util.JSON.decode(resp.responseText);
										if(respText.success){
											Ext.Msg.alert(alertTitle, "撤销成功！");
											searchreceipt();
										}else{
											Ext.Msg.alert(alertTitle, respText.msg);
										}	
									}
								});
							}
						});
						}
					}
				}
				
            } }
		  ]
		});
				
		 var receiptBtn=new Ext.Button({
			text : '<B>回单操作</B>',
			id : 'receiptBtn',
			tooltip : '回单菜单',
			iconCls : 'group',
			menu: menu
		});		
   		 var receiptCancelBtn=new Ext.Button({
			text : '<B>撤销操作</B>',
			id : 'receiptCancelBtn',
			tooltip : '撤销菜单',
			iconCls : 'revoked',
			menu: cancelmenu
		});		
   	   var printMenuBtn = new Ext.Button({
			text : '<B>打印提货单</B>',
			id : 'printMenuBtn',
			tooltip : '打印提货单',
			iconCls : 'printBtn',
			menu: new Ext.menu.Menu({
	    		items: [
	    		{text:'<b>提货单打印</b>',iconCls:'printBtn',tooltip : '单据打印',handler:function() {
	            	var receipt =Ext.getCmp('receiptCenter');
					var _records = receipt.getSelectionModel().getSelections();
					if (_records.length < 1) {
						Ext.Msg.alert(alertTitle, "请选择您要打印的行！");
						return false;
					}
					var dnos="";
					for(var i=0;i<_records.length;i++){
						dnos+=_records[i].data.dno+",";
					}
					parent.print('1',{print_dnos:dnos});
            } },{text:'<b>大长江打印</b>',iconCls:'printBtn',tooltip : '大长江打印',handler:function() {
	            	var receipt =Ext.getCmp('receiptCenter');
					var _records = receipt.getSelectionModel().getSelections();
					if (_records.length < 1) {
						Ext.Msg.alert(alertTitle, "请选择您要打印的行！");
						return false;
					}
					var dnos="";
					for(var i=0;i<_records.length;i++){
						dnos+=_records[i].data.dno+",";
					}
					parent.print('21',{print_dnos:dnos});
            } }]
	    	})
		});	

	var tbar=[printMenuBtn,
            {text:'<b>返单打印</b>',iconCls:'printBtn',
            	handler:function() {
	            var receipt =Ext.getCmp('receiptCenter');
				var _records = receipt.getSelectionModel().getSelections();
					if (_records.length < 1) {
						Ext.Msg.alert(alertTitle, "请选择您要打印的行！");
						return false;
					}
					var dnos="";
					for(var i=0;i<_records.length;i++){
						dnos+=_records[i].data.dno+",";
					}
					if(isNotAllow(_records,'已确收')){
						parent.print('15',{print_dnos:dnos});
					}
            } }
	 		,'-',receiptBtn,'-',receiptCancelBtn
            ,'-',{text:'<b>导出</b>',iconCls:'sort_down',tooltip : '导出',handler:function() {
					parent.exportExl(receiptGrid);

            } },'-',{xtype:'checkbox',id : 'isNotAdd', boxLabel:'<B>累计查询</B>'
    				},'-',
	 	
	 		{text : '<b>查询</b>',id : 'btn',iconCls : 'btnSearch',
    					handler : searchreceipt
    		},
    		{text : '<b>清空</b>',id : 'clearbtn',iconCls : 'btnLeft',
    					handler : function(){
    						totalStore.removeAll();
    						dateStore.removeAll();
    						receiptGrid.doLayout();
    					}
    		}
	 	];
	 	var threeBar = new Ext.Toolbar([
	 	'中转客商:',{xtype : "combo",
				width : searchWidth,
				listWidth:searchWidth+20,
				editable:true,
				triggerAction : 'all',
				// typeAhead : true,
				queryParam : 'filter_LIKES_cusName',
				pageSize : pageSize,
				resizable:true,
				minChars : 0,
				store : cpNameStore2,
				mode : "remote",// 从本地载值
				valueField : 'cusName',// value值，与fields对应
				displayField : 'cusName',// 显示值，与fields对应
			    id:'searchCpName', 
			    enableKeyEvents:true,listeners : {
	 		    keyup:function(textField, e){
                     if(e.getKey() == 13){
                     	searchreceipt();
                     }
	 		}
	 	
	 	}},	'-',
	 		'回单类型:',{xtype : "combo",
				width : searchWidth,
				editable:true,
				triggerAction : 'all',
				store : receiptTypeStore,
				mode : "remote",// 从本地载值
				valueField : 'receiptTypeName',// value值，与fields对应
				displayField : 'receiptTypeName',// 显示值，与fields对应
			    name : 'receiptType',
			    id:'searchreceiptType', 
			    enableKeyEvents:true,listeners : {
	 		    keyup:function(combo, e){
                    if(e.getKey() == 13){
                    	searchreceipt();
                    }
	 			}
	 	
	 	}},'-',
	 	'货物状态:',{xtype : "combo",
				width : searchWidth,
				editable:false,
				triggerAction : 'all',
				resizable:true,
				store : goodsnewStore,
				mode : "local",// 从本地载值
				valueField : 'goodsStatusValue',// value值，与fields对应
				displayField : 'goodsStatusName',// 显示值，与fields对应
			    id:'searchGoodsStatus', 
			    enableKeyEvents:true,listeners : {
		 		    keyup:function(textField, e){
	                     if(e.getKey() == 13){
	                     	searchreceipt();
	                     }
		 			}
	 			}
	 		},'-','是否开单:',{ 
	 	xtype : 'combo',
		width:searchWidth, 
		editable : false,
		triggerAction : 'all',
		store : printStore,
		mode:'local',
		emptyText:'--请选择--',
		valueField : 'printId',//value值，与fields对应
		displayField : 'printName',//显示值，与fields对应
		id:'searchPrint', 
		enableKeyEvents:true,listeners : {
	 		keyup:function(textField, e){
               if(e.getKey() == 13){
               	searchreceipt();
               }
	 		}
	 	}},'-',
	 	'回单状态:',{xtype : "combo",
				width : searchWidth-20,
				editable:false,
				triggerAction : 'all',
				store :statusStore,
				mode : "local",// 从本地载值
				valueField : 'statusId',// value值，与fields对应
				displayField : 'statusName',// 显示值，与fields对应
			    name : 'curStatus',
			    id:'searchcurStatus', 
			    enableKeyEvents:true,listeners : {
	 		    keyup:function(textField, e){
                     if(e.getKey() == 13){
                     	searchreceipt();
                     }
	 			}
	 	
	 		}},'-',
		{xtype:'textfield',id:'searchContent',
			width:searchWidth,
			enableKeyEvents:true,
			hidden : true,
			listeners : {
	 		keyup:function(textField, e){
	             if(e.getKey() == 13){
	             	searchreceipt();
	             }
	 		}
	 	}},{
    		xtype : 'datefield',
    		id : 'startDate',
    		format : 'Y-m-d',
    		emptyText : "开始时间",
    		value:new Date().add(Date.DAY, -2),
    		hidden : false,
    		width : 100,
    		disabled : false
    	}, '&nbsp;&nbsp;', {
    		xtype : 'datefield',
    		id : 'endDate',
    		format : 'Y-m-d',
    		emptyText : "结束时间",
    		value:new Date(),
    		hidden : false,
    		width : 100,
    		disabled : false
    	},'-',{ 
	 	
    					xtype : "combo",
    					width : 100,
    					triggerAction : 'all',
    					id:'searchSelectBox',
    					model : 'local',
    					forceSelection : true,
    					hiddenId : 'checkItems',
    					hiddenName : 'checkItems',
    					name : 'checkItemstext',
    					store : [['','查询全部'],
    							['LIKES_f_realGoWhere','去向'],
    							['LIKES_f_flightNo','航班号'],
	 			 				['overmemo','实配单号'],
	 			 				['oprPrewiredId','预配单号'],
    							['LIKES_f_consignee','收货人'],
    							['LIKES_f_trafficMode','运输方式'],
    							['LIKES_r_reachMan','入库人'],
    							['LIKES_r_getMan','领单人'],
    							['LIKES_r_outStockMan','出库人'],
    							['LIKES_r_confirmMan','确收人'],
    							['LIKES_r_outMan','寄出人'],
    							['LIKES_s_signMan','签收人'],
    							['r.reach_time','入库日期'],
    							['r.get_time','出库日期'],
    							['r.confirm_time','确收日期'],
    							['r.out_time','寄出日期'],
    							['f.create_time','录单日期']
    							],
    							
    					editable : false,
    					listeners : {
    						'select' : function(combo, record, index) {
								
    							if (combo.getValue() == 'r.reach_time' || 
    							    	combo.getValue() == 'r.get_time' || 
    							    	combo.getValue() == 'r.confirm_time' || 
    							    	combo.getValue() == 'r.out_time' || 
    							    	combo.getValue() == 'f.create_time') {
    								Ext.getCmp("startDate").enable();
    								Ext.getCmp("startDate").show();
									Ext.getCmp("startDate").setValue(new Date().add(Date.DAY, -2).format('Y-m-d'));
    								
    								Ext.getCmp("endDate").enable();
    								Ext.getCmp("endDate").show();
    								Ext.getCmp("endDate").setValue(new Date().format('Y-m-d'));
    								
    								Ext.getCmp("searchContent").disable();
    								Ext.getCmp("searchContent").hide();
    								Ext.getCmp("searchContent").setValue("");
    							}else{
    								Ext.getCmp("startDate").hide();
    								Ext.getCmp("startDate").setValue("");
    								Ext.getCmp("startDate").disable();
    								Ext.getCmp("endDate").hide();
    								Ext.getCmp("startDate").setValue("");
    								Ext.getCmp("endDate").disable();
    						      	Ext.getCmp("searchContent").enable();
    								Ext.getCmp("searchContent").show();
    								
    							}
    						},
    						afterRender: function(combo, record, index) {
					       　　	combo.setValue('f.create_time');
							},keyup:function(combo, e){
			                     if(e.getKey() == 13){
			                     	searchreceipt();
				                   }
					 		}  
    					}
    					
    				}
	 	]);
	 	var queryTbar=new Ext.Toolbar([
    		'配送单号:',{xtype:'numberfield',id:'searchDno',
    		   width:searchWidth-10, 
    		   enableKeyEvents:true,
    		   selectOnFocus:true,
    		   listeners : {
	 			keyup:function(textField, e){
                     if(e.getKey() == 13){
                     	searchreceipt();
	                    }
		 		}  
		 		}
	 	},	'-','主单号:',{xtype:'textfield',id:'flightMainId',width:searchWidth-10, enableKeyEvents:true,listeners : {
	 		keyup:function(textField, e){
                     if(e.getKey() == 13){
                     	searchreceipt();
                     }
	 		}
	 	}},'-','分单号:',{xtype:'textfield',id:'subNoId',width:searchWidth-10, enableKeyEvents:true,listeners : {
	 		keyup:function(textField, e){
                     if(e.getKey() == 13){
                     	searchreceipt();
                     }
	 		}
	 	}},'-',
	 	'代理公司:',{xtype : "combo",
				width : searchWidth,
				editable:true,
				triggerAction : 'all',
				//typeAhead : true,
				queryParam : 'filter_LIKES_cusName',
				pageSize : comboSize,
				resizable:true,
				minChars : 0,
				store : cpNameStore,
				mode : "remote",// 从本地载值
				valueField : 'cusName',// value值，与fields对应
				displayField : 'cusName',// 显示值，与fields对应
			    id:'searchCusName', 
			    enableKeyEvents:true,listeners : {
	 		    keyup:function(textField, e){
                     if(e.getKey() == 13){
                     	searchreceipt();
                     }
	 		}
	 	
	 	}},	'-',
	 	'配送方式:',{xtype : "combo",
				width : searchWidth-20,
				editable:true,
				triggerAction : 'all',
				store : distributionModeStore,
				mode : "remote",// 从本地载值
				valueField : 'distributionModeName',// value值，与fields对应
				displayField : 'distributionModeName',// 显示值，与fields对应
			    name : 'distributionMode',
			    id:'searchdistributionMode', 
			    enableKeyEvents:true,listeners : {
	 		    keyup:function(textField, e){
                     if(e.getKey() == 13){
                     	searchreceipt();
                     }
	 		}
	 	
	 	}},'-','确收异常:',{ 
	 	xtype : 'combo',
		width:searchWidth-20, 
		editable : false,
		triggerAction : 'all',
		store : isNotExceptionStore,
		mode:'local',
		emptyText:'--请选择--',
		valueField : 'isNotExceptionId',//value值，与fields对应
		displayField : 'isNotExceptionName',//显示值，与fields对应
		name:'isNotException',
		id:'searchisNotException', 
		enableKeyEvents:true,listeners : {
	 		keyup:function(textField, e){
               if(e.getKey() == 13){
               	searchreceipt();
               }
	 		}
	 	
	 	}},
	 	'-','提货方式:',{
	    xtype : 'combo',
		width:searchWidth, 
		editable : true,
		triggerAction : 'all',
		store : takeModeStore,
		mode:'remote',
		valueField : 'takeModeId',//value值，与fields对应
		displayField : 'takeModeName',//显示值，与fields对应
		name:'takeMode',
		id:'searchtakeMode',
		enableKeyEvents:true,listeners : {
	 		keyup:function(textField, e){
                     if(e.getKey() == 13){
                     	searchinform();
                     }
	 			}
	 		}
	 	}
	 	]);	

totalStore = new Ext.data.Store({
        storeId:"totalStore",
        reader: new Ext.data.JsonReader({
            root: 'resultMap', totalProperty: 'totalCount'
        }, fields)
    });
	 	
Ext.onReady(function() {
   	dateStore = new Ext.data.Store({
        storeId:"dateStore",
        proxy: new Ext.data.HttpProxy({url : sysPath+ "/"+gridSearchSqlUrl}),
        baseParams:{privilege:privilege,limit : pageSize},
        reader: new Ext.data.JsonReader({
            root: 'resultMap', totalProperty: 'totalCount'
        }, fields)
    });
 
   var sm = new Ext.grid.CheckboxSelectionModel();
   var rowNum = new Ext.grid.RowNumberer({
        header:'序号', width:20,
        renderer:function(value,metadata,record,rowIndex){
         	record_start++;
	　　　	return　record_start;
	　　}

    });
 	
 	
    var expander = new Ext.ux.grid.RowExpander({
    	height:280,
        tpl : '<div id="dno_{dno}" style="height:380px;width:1056px" ></div>',
        listeners : {
			'expand' : function(record, body, rowIndex){
				var foo=body.get("dno");

		 		var tableP = new Ext.TabPanel({
    				animScroll:true,
    				id:'tabPanls',
    				autoScroll:true,
    				tbar : [
						{text:'<b>删除图片</b>',
						 iconCls:'delete',
						 handler:function() {
						 	if(tableP.getActiveTab().getId()=="image"){
						 		Ext.Msg.alert(alertTitle, "无图片可以删除");
						 		return ;
						 	}
				 			Ext.Msg.confirm(alertTitle, "您确定要删除这张图片吗？", function(btnYes) {
						    	if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
									Ext.Ajax.request({
										url : sysPath + '/receipt/oprReceiptAction!delImageAddr.action',
										params:{
											id:body.get("dno"),
											numString:tableP.getActiveTab().getId()
										},
										success : function(resp) {
											Ext.Msg.alert(alertTitle, "图片删除成功",function(){
													dateStore.load();
											});
										},
										failure : function(form, action){
											Ext.Msg.alert(alertTitle, "删除图片失败！请联系管理员");
										}
									});
							    }
						    });
							
			             }
			        }],
					width:Ext.lib.Dom.getViewWidth(),
					listeners:{
						render:function(tab,e){
						}
					},items:[
						
					  ]
				});
				
		 		Ext.Ajax.request({
					url : sysPath + '/receipt/oprReceiptAction!getImage.action',
					params:{
						id:body.get("dno")
					},
					success : function(resp) {
						
						var jdata = Ext.util.JSON.decode(resp.responseText);
						if(jdata.length==0){
							var img1 = new Ext.Panel({
								autoScroll:true,
							 	height:380,
							 	id:'image',
							 	frame:false,
							 	title : '无图片',
								collapsed:false,
								autoHeight:true,
								autoWidth:true,
								html : '没有上传扫描图片',
								cls : 'empty'
							});
							
							tableP.add(img1);
							tableP.activate(img1);
						}
						
						for(var i=0;i<jdata.length;i++){
							var img1 = new Ext.Panel({
								autoScroll:true,
							 	height:380,
							 	id:'tab_'+i,
							 	split : false,
							 	frame:false,
							 	layout : 'accordion',
							 	title : '扫描图片'+(i+1),
								collapsed:false,
								autoHeight:true,
								autoWidth:true,
								html : '<center><img id="img1" src="'+jdata[i]+'" style="cursor:hand" ></img><center>',
								cls : 'empty'
							});
							
							tableP.add(img1);
						}
						tableP.activate(0);
					},
					failure : function(form, action){
						Ext.Msg.alert(alertTitle, "获取图片失败！请联系管理员");
					}
				});
				
		   		tableP.setHeight(380);
				if(tableP.rendered){
					tableP.doLayout();
				}else{
		   			tableP.render("dno_"+foo);
		   		}
			}
		}
    });		
    receiptGrid = new Ext.grid.GridPanel({
        renderTo: Ext.getBody(),
        id:'receiptCenter',
        height:Ext.lib.Dom.getViewHeight()-1,		
        width:Ext.lib.Dom.getViewWidth()-1,
        viewConfig : {
			//columnsText : "显示的列",
			//sortAscText : "升序",
			//sortDescText : "降序"
			//forceFit : true
		},
		autoScroll : true,
		plugins : [expander],
		//autoSizeColumns: true,
		frame : false,
		loadMask : true,
		stripeRows : true,
        sm:sm,
        cm:new Ext.grid.ColumnModel([expander,sm,
        		
       			{header: '配送单号', dataIndex: 'dno',width:defaultWidth,sortable : true},
       			{header: '删除',width:45
       				,renderer:function renderDescn(v, cellmeta, record, rowIndex, columnIndex, store){
       					//dateStore.remove(rowIndex);dateStore.remove(record)
       					return "<a href='#' onclick='removeRecord();'>删除</a>";
       				}
       			},
 				{header: '货物状态', dataIndex: 'goodsStatus',width:defaultWidth,sortable : true
 					,renderer:function renderDescn(v, cellmeta, record, rowIndex, columnIndex, store){
 						//SONDERZUG
 						if(record.data['SONDERZUG']==1){
 							return v+'<font color="red"><b> 专</b></font>';
 						}else{
 							return v;
 						}
 					}
 				},
 				{header: '回单状态', dataIndex: 'curStatus',width:defaultWidth-20,sortable : true},
 				{header: '最后更新时间', dataIndex: 'updateTime',width:defaultWidth+20,sortable : true},
       			{header: '是否开单', dataIndex: 'printNum',width:defaultWidth,sortable : true
       				,renderer:function renderDescn(v, cellmeta, record, rowIndex, columnIndex, store){
 						if(v>0){
 							cellmeta.css = 'x-grid-back-green';
 							return '已开单';
 						}else{
							return "未开单";
 						}
	        		}
       			},
       			{header: '主单号', dataIndex: 'FLIGHT_MAIN_NO',width:defaultWidth,sortable : true},
       			{header: '分单号', dataIndex: 'subNo',width:defaultWidth,sortable : true},
       			{header: '航班号', dataIndex: 'FLIGHT_NO',width:defaultWidth,sortable : true},
       			{header: '收货人信息', dataIndex: 'CONSIGNEE',width:defaultWidth+40,sortable : true
       				,renderer:function renderDescn(v, cellmeta, record, rowIndex, columnIndex, store){
       					return record.data['CONSIGNEE']+'/'+record.data['ADDR'];
       				}
       			},
       			{header: '开单件数', dataIndex: 'PIECE',width:defaultWidth-20,sortable : true},
       			{header: '打印次数', dataIndex: 'printNum',width:defaultWidth-30,sortable : true,hidden:true},
       			{header: '加急状态', dataIndex: 'urgentStatus',width:defaultWidth-20,sortable : true
       				,renderer:function renderDescn(v, cellmeta, record, rowIndex, columnIndex, store){
       					if(v=="1"){
       						return '<font color="red"><b>加急</b></font>';
       					}else{
       						return "正常";
       					}
       				 }
       			},
 				{header: '扫描份数', dataIndex: 'SCAN_NUM',width:defaultWidth-20,sortable : true},
       			{header: '代理公司', dataIndex: 'cpName',width:defaultWidth,sortable : true},
       			{header: '中转客商', dataIndex: 'gowhere',width:defaultWidth,sortable : true},
       			{header: '回单序号', dataIndex: 'id',width:defaultWidth,sortable : true,hidden:true},
       			{header: '签收人', dataIndex: 'signMan',width:defaultWidth,sortable : true},
       			{header: '回单类型', dataIndex: 'receiptType',width:defaultWidth,sortable : true},
       			{header: '配送方式', dataIndex: 'distributionMode',width:defaultWidth-20,sortable : true},
       			{header: '提货方式', dataIndex: 'TAKE_MODE',width:defaultWidth-10,sortable : true},
       			{header: '运输方式', dataIndex: 'TRAFFIC_MODE',width:defaultWidth-20,sortable : true},
       			{header: '去向', dataIndex: 'REALGOWHERE',width:defaultWidth,sortable : true},
				{header: '预配单号', dataIndex: 'prewiredId',width:defaultWidth,sortable : true},
       			{header: '实配单号', dataIndex: 'overmemoId',width:defaultWidth,sortable : true},
       			{header: '入库时间', dataIndex: 'reachTime',width:defaultWidth,sortable : true},
       			{header: '入库人', dataIndex: 'reachMan',width:defaultWidth,sortable : true},
       			{header: '入库份数', dataIndex: 'reachNum',width:defaultWidth,sortable : true},
       			{header: '领单人', dataIndex: 'getMan',width:defaultWidth,sortable : true},
       			{header: '领单份数', dataIndex: 'getNum',width:defaultWidth,sortable : true},
 				{header: '领单时间', dataIndex: 'getTime',width:defaultWidth,sortable : true},
 				{header: '出库人', dataIndex: 'outStockMan',width:defaultWidth,sortable : true},
       			{header: '出库份数', dataIndex: 'outStockNum',width:defaultWidth,sortable : true},
 				{header: '出库时间', dataIndex: 'outStockTime',width:defaultWidth,sortable : true},
 				{header: '确收人', dataIndex: 'confirmMan',width:defaultWidth,sortable : true},
 				{header: '确收份数', dataIndex: 'confirmNum',width:defaultWidth,sortable : true},
 				{header: '确收时间', dataIndex: 'confirmTime',width:defaultWidth,sortable : true},
 				{header: '寄出人', dataIndex: 'outMan',width:defaultWidth,sortable : true},
 				{header: '寄出公司', dataIndex: 'outCompany',width:defaultWidth,sortable : true},
 				{header: '寄出费用', dataIndex: 'outCost',width:defaultWidth,sortable : true},
 				{header: '寄出时间', dataIndex: 'outTime',width:defaultWidth,sortable : true},
 				{header: '寄出途径', dataIndex: 'outWay',width:defaultWidth,sortable : true},
 				{header: '寄出单号', dataIndex: 'outNo',width:defaultWidth,sortable : true},
 				{header: '扫描状态', dataIndex: 'scanStatus',width:defaultWidth,sortable : true
 					,renderer:function renderDescn(v, cellmeta, record, rowIndex, columnIndex, store){
 						if(v==1){
 							cellmeta.css = 'x-grid-back-green';
 						}
						return v==1?'已扫描':"未扫描";
						//cellmeta.css = 'x-grid-back-'+record.get('KPICOLOR');
	        		}
 				},
 				{header: '扫描人', dataIndex: 'scanMan',width:defaultWidth,sortable : true},
 				{header: '扫描时间', dataIndex: 'scanTime',width:defaultWidth,sortable : true},
 				{header: '扫描地址', dataIndex: 'scanAddr',width:defaultWidth,sortable : true,hidden:true},
 				//{header: '寄出状态', dataIndex: 'outStatus',width:defaultWidth,sortable : true},
 				{header: '是否异常', dataIndex: 'confirmStatus',width:defaultWidth,sortable : true
					,renderer:function renderDescn(v, cellmeta, record, rowIndex, columnIndex, store){
							if(v==2){
								cellmeta.css = 'x-grid-back-red';
							}
							return v==1?'否':(v==2?"是":"未确认");
	        		}},
 				{header: '异常跟踪描述', dataIndex: 'confirmRemark',width:defaultWidth,sortable : true}
        ]),
        store:dateStore,
        tbar: tbar,
        listeners: {
                    render: function(){
                        queryTbar.render(this.tbar);
                        threeBar.render(this.tbar);
                    }
                },
        bbar: new Ext.PagingToolbar({
            pageSize: pageSize, 
            privilege:privilege,
            store: dateStore, 
            afterPageText: '共{0}页',
		    beforePageText: '当前页',
			listeners:{
				beforechange:function(){
					if(Ext.getCmp('isNotAdd').checked){ 
						Ext.Msg.alert(alertTitle, "目前是累积查询，不能点击下一页！");
						return false;
					}
				}
			},
            displayInfo: true,
            displayMsg: '显示第 {0} 条到 {1} 条记录，共 {2} 条', 
            emptyMsg: "没有记录信息显示"
	        })
    });
  });
    
   function searchreceipt() {
   		dateStore.baseParams ={
   			filter_bussDepart:bussDepart,
   			privilege:privilege,
   			limit : pageSize,
   			start:0
   		};
   		
			addCount=0;
			var ids="";
			for(var i=0;i<totalStore.data.length;i++){
				addCount++;
				ids=ids+totalStore.getAt(i).get('id')+',';
			}
			if(!Ext.getCmp('isNotAdd').checked){
				ids="";
			}
   			var checkItems=Ext.get("checkItems").dom.value;
   
			var searchdistributionMode = Ext.getCmp('searchdistributionMode').getRawValue();
			var searchisNotException=Ext.getCmp('searchisNotException').getValue();
			var searchreceiptType=Ext.getCmp('searchreceiptType').getRawValue();
			searchDno=Ext.get('searchDno').dom.value;
			var searchCusName=Ext.getCmp('searchCusName').getRawValue();
			var searchCpName = Ext.getCmp('searchCpName').getRawValue();
			var searchcurStatus=Ext.getCmp('searchcurStatus').getRawValue();
			if(searchcurStatus=='-请选择-'){
				searchcurStatus='';
			}
			var searchtakeMode = Ext.get('searchtakeMode').dom.value;
			var searchgoodsStatusValue=Ext.getCmp('searchGoodsStatus').getValue();
			//alert(searchgoodsStatusValue);
			
			var startCount = Ext.get('startDate').dom.value;
			var endCount = Ext.get('endDate').dom.value;
			var countCheckItems = Ext.get('checkItems').dom.value;
			var searchPrint = Ext.getCmp('searchPrint').getValue();
		 	record_start=0;
		 	if(null!=searchDno && searchDno.length>1){
		 		Ext.apply(dateStore.baseParams, {
		 			filter_dno:searchDno,
		 			filter_ids:ids
		 		});	
		 	}else{
			 	Ext.apply(dateStore.baseParams, {
	 			 		filter_f_distributionMode:searchdistributionMode,
	 			 		filter_r_receiptType:searchreceiptType,
	 			 		filter_dno:searchDno,
	 			 		filter_ids:ids,
	 			 		filter_f_takeMode:searchtakeMode,
	 			 		filter_LIKES_f_flightMainNo:Ext.getCmp('flightMainId').getValue(),
	 			 		filter_LIKES_f_subNo:Ext.getCmp('subNoId').getValue(),
	 			 		filter_r_curStatus:searchcurStatus,
	 			 		filter_searchgoodsStatusValue:searchgoodsStatusValue,
	 			 		filter_LIKES_f_cpName:searchCusName,
	 			 		filter_LIKES_f_gowhere:searchCpName,
	 			 		filter_r_confirmStatus:searchisNotException,
	 			 		privilege:privilege,
	 			 		filter_printNum:searchPrint,
	 			 		filter_checkItems : '',
	 			 		filter_itemsValue : ''
	   			});
				if(checkItems.indexOf('_time')>0){
					 Ext.apply(dateStore.baseParams, {
						filter_countCheckItems:countCheckItems,
				 	 	filter_startCount:startCount,
				 	 	filter_endCount:endCount
		   			});
		   		}else{
		 			 Ext.apply(dateStore.baseParams, {
						filter_checkItems : Ext.get("checkItems").dom.value,
						filter_itemsValue : Ext.get("searchContent").dom.value
		   			});
		   		}
	   		}
		dateStore.load({callback:function(){
			var isNotAdd =Ext.getCmp('isNotAdd');
			if(isNotAdd.checked){
				addRecord(dateStore);
				receiptGrid.doLayout();
				receiptGrid.getSelectionModel().selectAll();
				Ext.getCmp('searchDno').focus(true,true);
			}else{
				totalStore.removeAll();
			}
		}});
		
  
	}
//回单入库点到	
function receiptReport(_records){
			var i=0;
			var dno;
			var rid;
			var reportInform;
			if(_records!=null){
				dno=_records[i].data.dno;
				rid=_records[i].data.id;
				reportInform=_records[i].data.CONSIGNEE+'/'+_records[i].data.ADDR+'/'+
				_records[i].data.cpName+'/'+_records[i].data.receiptType+'/'+_records[i].data.TAKE_MODE+'/'+_records[i].data.distributionMode;
			}
			var form = new Ext.form.FormPanel({
						labelAlign : 'left',
						frame : true,
						width:400,
						bodyStyle : 'padding:5px 5px 0',
						reader : new Ext.data.JsonReader({
			            root: 'result', totalProperty: 'totalCount'
			        }, fields),
					labelAlign : "right",
							items : [{
										layout : 'column',
											items : [{
											columnWidth : .95,
											layout : 'form',
											items : [
													{
														name : "id",
														value:rid,
														id:'reportId',
														xtype : "hidden"
													},{
														xtype : 'numberfield',
														labelAlign : 'left',
														value:dno,
														id:'reportDno',
														fieldLabel : '配送单号<font style="color:red;">*</font>',
														name : 'dno',
														anchor : '95%',
														enableKeyEvents:true,
														listeners : {
															keyup:function(field,e){
																 if(e.getKey() != 13){
																 	return;
																 }
																if(field.getValue()==null || field.getValue()==''){
																		return;
																}
																Ext.Ajax.request({
																	url : sysPath+'/'
																			+ gridSearchSqlUrl+"?privilege="+privilege,
																	params :{filter_dno:field.getValue()},
																	loadMask : true,
																	success : function(resp) {
																		var respText = Ext.util.JSON.decode(resp.responseText);
																		if(respText.success){
																			try{
																			if(respText.resultMap.length==0){
																				Ext.getCmp('reportDno').setValue("");
																				Ext.getCmp('reportInform').setValue('您输入的配送单号不存在回单！');
																				Ext.getCmp('reportDno').focus();
																			}else if(respText.resultMap.length>0){
																				if(respText.resultMap[0].curStatus!=null){
																					//top.Ext.Msg.alert(alertTitle, "该回单已经点到过了！");
																					Ext.getCmp('reportDno').setValue("");
																					Ext.getCmp('reportInform').setValue('该回单已经入库了,不能再次入库！');
																					Ext.getCmp('reportDno').focus();
																					return;
																				}
																				Ext.getCmp('reportId').setValue(respText.resultMap[0].ID);
																				Ext.getCmp('reportDno').setValue(respText.resultMap[0].DNO);
																				Ext.getCmp('reportInform').setValue(
																				respText.resultMap[0].CONSIGNEE+'/'+respText.resultMap[0].ADDR+'/'
																				+respText.resultMap[0].CPNAME+'/'+respText.resultMap[0].RECEIPTTYPE+'/'+respText.resultMap[0].TAKE_MODE
																				+'/'+respText.resultMap[0].DISTRIBUTIONMODE);
																				Ext.getCmp('reportNum').focus(true,true);
																			}
																			}catch(e){
																				
																			}
																		}else{
																			Ext.Msg.alert(alertTitle,respText.msg);
																		}
																	}
																});
															}
										 				}
												       },{
														xtype : 'textarea',
														labelAlign : 'left',
														readOnly  :true,
														allowBlank : false,
														height:40,
														fieldLabel : '回单信息',
														value:reportInform,
														id : 'reportInform',
														anchor : '95%'
												     },{
														xtype : 'numberfield',
														labelAlign : 'left',
														allowBlank : false,
														maxLength :2,
														id:'reportNum',
														blankText : "入库份数不能为空！",
														fieldLabel : '入库份数<font style="color:red;">*</font>',
														name : 'reachNum',
														anchor : '95%',
														allowNegative :false,
														minValue :1,
														enableKeyEvents:true,
														listeners : {
												 		keyup:function(field, e){
											                     if(e.getKey() == 13){
											                     	Ext.getCmp('reportBtn').focus(true,true);
											                     }
											              
												 			}
										 				}
												       }]
											}]
										}]
									});
									
		var win = new Ext.Window({
		title : '回单入库点到',
		width : 400,
		closeAction : 'hide',
		plain : true,
		modal : true,
		items : form,
		buttonAlign : "center",

		buttons : [{
			text : "保存",
			iconCls : 'groupSave',
			id:'reportBtn',
			handler : function() {
				if (form.getForm().isValid()) {
					form.getForm().submit({
						url : sysPath
								+ '/'+saveReportUrl,
								params:{privilege:privilege},
						waitMsg : '正在保存数据...',
						success : function(form, action) {
									searchreceipt();
									if(_records!=null && _records.length>(i+1)){
										i++;
										Ext.getCmp('reportId').setValue(_records[i].data.id);
										Ext.getCmp('reportDno').setValue(_records[i].data.dno);
										Ext.getCmp('reportNum').focus();
									}else{
										Ext.getCmp('reportDno').setValue('');
										Ext.getCmp('reportInform').setValue('');
										Ext.getCmp('reportDno').focus();
									
									}
								},
						failure : function(form, action) {
							if (action.faliureType == Ext.form.Action.SERVER_INVALID) {
								top.Ext.Msg.alert(alertTitle, "服务器连接异常，请联系管理员！");
							} else {
								if (action.result.msg) {
									top.Ext.Msg.alert(alertTitle,
											action.result.msg, function() {
												dataReload();
											});
		
								}
							}
						}
					});
				}
			}
		},  {
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
//回单出库
function receiptGet(_records){
					var ids="";
					var dnos="";
					for(var i=0;i<_records.length;i++){
						 ids+=_records[i].data.id+',';
				 		 dnos+=_records[i].data.dno+',';
					}
					var form = new Ext.form.FormPanel({
								labelAlign : 'left',
								frame : true,
								bodyStyle : 'padding:5px 5px 0',
								width : 350,
								reader : new Ext.data.JsonReader({
					            root: 'result', totalProperty: 'totalCount'
					        }, fields),
							labelAlign : "right",
							
								items : [{
										layout : 'column',
											items : [{
											layout : 'form',
											columnWidth : .95,
											items : [
													{
														name : "ids",
														value:ids,
														xtype : "hidden"
													},{
														name : "dnos",
														value:dnos,
														fieldLabel : '配送单号',
														xtype : "textarea",
														readOnly:true,
														anchor : '95%'
													},{
														xtype : 'textfield',
														labelAlign : 'left',
														readOnly  :false,
														allowBlank : false,
														maxLength :20,
														id:'getManId',
														blankText : "领单人不能为空！",
														fieldLabel : '领单人<font style="color:red;">*</font>',
														name : 'getMan',
														anchor : '95%',
														enableKeyEvents:true,
														listeners : {
												 		keyup:function(field, e){
											                     if(e.getKey() == 13){
											                     	Ext.getCmp('myGetNum').focus(true,true);
											                     }
											              
												 			}
										 				}
												       },{
												       	xtype:'numberfield',
												       	labelAlign : 'left',
														allowBlank : false,
														name:'getNum',
														fieldLabel : '领单份数<font style="color:red;">*</font>',
														allowNegative :false,
														minValue :1,
														id:'myGetNum',
														anchor : '95%',
														enableKeyEvents:true,
														listeners : {
												 		keyup:function(field, e){
											                     if(e.getKey() == 13){
											                     	Ext.getCmp('getBtn').focus(true,true);
											                     }
											              
												 			}
										 				}
												       	
												       }]
											}]
										}]
									});
		var win = new Ext.Window({
		title : '回单领取',
		width : 350,
		closeAction : 'hide',
		plain : true,
		modal : true,
		items : form,
		buttonAlign : "center",
		buttons : [{
			text : "保存",
			iconCls : 'groupSave',
			id:'getBtn',
			handler : function() {
				if (form.getForm().isValid()) {
					this.disabled = true;
					
					form.getForm().submit({
						url : sysPath
								+ '/'+saveGetUrl,
								params:{privilege:privilege},
						waitMsg : '正在保存数据...',
						success : function(form, action) {
							win.hide(), Ext.Msg.alert(alertTitle,
									'领单成功'
									, function() {
										searchreceipt();
									//	for(var i=0;i<_records.length;i++){
									//		searchreceiptByDno(_records[i].data.dno);
									//	}
									}
									);
						},
						failure : function(form, action) {
							if (action.faliureType == Ext.form.Action.SERVER_INVALID) {
								Ext.Msg.alert(alertTitle, "服务器连接异常，请联系管理员！");
							} else {
								if (action.result.msg) {
									Ext.Msg.alert(alertTitle,
											action.result.msg, function() {
												dataReload();
											});
		
								}
							}
						}
					});
				}
			}
		},  {
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
//回单寄出
function receiptOut(){
var receiptOutFields=[
		{name:'dno'},
      	{name:'flightMainNo'},
      	{name:'subNo'},
      	{name:'consigneeInfo'},
      	{name:'cpName'},
      	{name:'piece'}
]
var receiptOutStore = new Ext.data.Store({
        storeId:"receiptConfirmStore",
        reader: new Ext.data.JsonReader({
            root: 'result', totalProperty: 'totalCount'
        },receiptOutFields)
    });
var out_record_index = 0;
var outRowNum = new Ext.grid.RowNumberer({
        header:'序号', width:30,
        renderer:function(value,metadata,record,rowIndex){
         	out_record_index++;
	　　　	return　out_record_index;
	　　}
    });
var outsm = new Ext.grid.CheckboxSelectionModel();
var outReceiptGrid = new Ext.grid.GridPanel({
 				region : "center",
 				id : 'confirmReceiptGrid',
 				height : 250,
 				width : 450,
 				autoScroll : true,
 				frame : false,
 				loadMask : true,
 				stripeRows : true,
 				viewConfig:{
 				   scrollOffset: 0
 				},
 				cm : new Ext.grid.ColumnModel([outRowNum,
 						outsm, 
 						{header : '配送单号',dataIndex:'dno',width:80}, 
 						{header : '主单号',dataIndex : 'flightMainNo',width:80}, 
 						{header : '分单号',dataIndex : 'subNo',width:60}, 
 						{header : '收货人信息',dataIndex : 'consigneeInfo',width:200},
 						{header : '发货代理',dataIndex : 'cpName',width:80},
 						{header : '件数',dataIndex : 'piece',width:60}
 				]),
 				sm : outsm,
 				ds : receiptOutStore,
 				listeners:{
 					click:function(){
			    		var records = this.getSelectionModel().getSelections();
				        var deletebtn = Ext.getCmp('outDelBtn');
				        if (records.length>=1) {       	
				            if(deletebtn){
				            	deletebtn.setDisabled(false);
				            }
				        } else {
							if(deletebtn){
				            	deletebtn.setDisabled(true);
							}
				        }
				     }
 				}
    	});
	 	out_record_index=0;
		var outform = new Ext.form.FormPanel({
    				id:'outform',
					frame : true,
					border : false,
					bodyStyle : 'padding:5px 5px 5px',
					labelAlign : "right",
					labelWidth : 70,
					width : 500,
					items:[{
						 layout : 'column',
						 items:[
						 {
							 layout:'form',
							 columnWidth:.6,
							 items:[
								{xtype : 'numberfield',
								labelAlign : 'left',
								fieldLabel : '配送单号<font style="color:red;"><b>*</b></font>',
								name : 'dno',
								selectOnFocus:true,
								maxLength:10,
								id:'outDno',
								anchor : '95%',
								enableKeyEvents:true,
								listeners:{
						 			keyup:function(field, e){
					                     if(e.getKey() == 13){
					                     	Ext.getCmp('outAddBtn').focus(true,true);
					                     }
						 			}
								}
						       }
    					]},{
								layout:'form',
								columnWidth:.2,
								items:[{
									xtype:'button',
					 				disabled:false,
					 				id:'outAddBtn',
					 				text:' 添 加 ',
					 				handler:function(){
					 					var vdno = Ext.get('outDno').dom.value;
					 					if (Ext.getCmp('outDno').isValid()) {
					 						for (var i = 0; i < receiptOutStore.data.length; i++) {
												if (receiptOutStore.getAt(i).get('dno') == vdno) {
													top.Ext.Msg.alert(alertTitle,'该配送单号已经存在!');
													Ext.getCmp('outDno').focus(true,true);
													return;
												}
											}
					 						Ext.Ajax.request({
												url : sysPath+'/'+ gridSearchSqlUrl,
												params :{filter_dno:vdno},
												success : function(resp) {
													var rstore = Ext.util.JSON.decode(resp.responseText);
													var result = rstore.resultMap;
													try{
														if(result.length==0){
															top.Ext.Msg.alert(alertTitle,'您输入的配送单号不存在!',
																function(){
																	Ext.getCmp('outDno').focus();
																});
														}else if(result.length>0){
															
															if(null==result[0].CONFIRMSTATUS || result[0].CONFIRMSTATUS==0){
																top.Ext.Msg.alert(alertTitle,'该回单还没有确收!',
																	function(){
																		Ext.getCmp('outDno').focus();
																		return false;
																	});
															}else if(result[0].OUTSTATUS==1){
																top.Ext.Msg.alert(alertTitle,'该回单已经寄出!',
																	function(){
																		Ext.getCmp('outDno').focus();
																		return false;
																	});
															}else{
																var recd=(new Ext.data.Record({
																 	dno:result[0].DNO,
																 	flightMainNo:result[0].FLIGHT_MAIN_NO,
																 	consigneeInfo:result[0].CONSIGNEE+'/'+result[0].CONSIGNEETEL+'/'+result[0].ADDR,
																 	subNo:result[0].SUBNO,
																 	cpName:result[0].CPNAME,
																 	piece:result[0].PIECE
																 }));
																 receiptOutStore.insert(0,recd);
																 Ext.getCmp('outDno').focus(true,true);
															 }
														}
													}catch(e){
														
													}
												}
											});
					 					}else{
					 						var vdno=Ext.getCmp('outDno');
					 						if(!vdno.validate()){
					 							top.Ext.Msg.alert(alertTitle,'请输入配送单号!',
													function(){
														vdno.focus();
														return;
													});
					 						}
					 					}	
					 				}
								}]
							},{
								layout:'form',
								columnWidth:.2,
								items:[{
									xtype:'button',
					 				disabled:true,
					 				id:'outDelBtn',
					 				text:'删除',
					 				handler:function(){
								    	var records=outReceiptGrid.getSelectionModel().getSelections();
								    	if(records.length<1){
								    		Ext.Msg.alert(alertTitle,'请选中您要删除寄出的回单！');
					 						return false;
								    	}
					 					Ext.Msg.confirm(alertTitle, "确定要删除吗？", function(btnYes) {
									    	if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
												receiptOutStore.remove(records); 
										   }
									  });
					 				}
								}]
							}
    						]
						},{
							layout:'column',
							items:[{
									layout:'form',
									columnWidth : .5,
									items : [{
									xtype : 'combo',
									//typeAhead : true,
									allowBlank : false,
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
									id:'outManId',
									blankText : "寄出人不能为空！",
									fieldLabel : '寄出人<font style="color:red;">*</font>',
									name : 'outMan',
									anchor : '95%',
									enableKeyEvents:true,
									listeners : {
							 			keyup:function(field, e){
						                     if(e.getKey() == 13){
						                     	Ext.getCmp('outWay').focus(true,true);
						                     }
							 			},render:function(combo, e){
							 				combo.setValue(userName);
							 			}
					 				}
							       },{
									xtype:'combo',
									editable:false,
									triggerAction : 'all',
									allowBlank : false,
									blankText : "寄出方式不能为空！",
									fieldLabel:'寄出方式<font style="color:red;">*</font>',
									store:outWayStore,
									mode:'remote',
									id:'outWay',
									valueField:'outWayName',
									displayField:'outWayName',
									name:'outWay',
									id:'outWay',
									anchor : '95%',
									enableKeyEvents:true,
									listeners : {
							 			keyup:function(combo, e){
					                     	if(e.getKey() == 13){
					                     		if(combo.getValue()=='内部带单'){
								 					Ext.getCmp('outCompany').disable();
								 					Ext.getCmp('outNo').disable();
								 					Ext.getCmp('outCost').disable();
								 					Ext.getCmp('outBtn').focus(true,true);
								 				}else{
								 					Ext.getCmp('outCompany').enable();
								 					Ext.getCmp('outNo').enable();
								 					Ext.getCmp('outCost').enable();
					                     			Ext.getCmp('outCompany').focus();
					                     		}
					                     	}
							 			},select:function(combo,e){
							 				if(combo.getValue()=='内部带单'){
							 					Ext.getCmp('outCompany').disable();
							 					Ext.getCmp('outNo').disable();
							 					Ext.getCmp('outCost').disable();
							 					Ext.getCmp('outBtn').focus(true,true);
							 				}else{
							 					Ext.getCmp('outCompany').enable();
							 					Ext.getCmp('outNo').enable();
							 					Ext.getCmp('outCost').enable();
				                     			Ext.getCmp('outCompany').focus();
							 				}
							 			}
					 				}
							       }]
							   },{
									columnWidth : .5,
									layout : 'form',
									items : [
										{
											xtype : 'textfield',
											labelAlign : 'left',
											readOnly  :false,
											allowBlank : false,
											maxLength :50,
											blankText : "物流公司不能为空！",
											fieldLabel : '物流公司<font style="color:red;">*</font>',
											name : 'outCompany',
											id:'outCompany',
											anchor : '95%',
											enableKeyEvents:true,
											listeners : {
									 		keyup:function(field, e){
								                     if(e.getKey() == 13){
								                     	Ext.getCmp('outNo').focus();
								                     }
								              
									 			}
							 				}
									       },{
											xtype : 'textfield',
											labelAlign : 'left',
											readOnly  :false,
											allowBlank : false,
											maxLength :20,
											blankText : "寄出单号不能为空！",
											fieldLabel : '寄出单号<font style="color:red;">*</font>',
											name : 'outNo',
											id:'outNo',
											anchor : '95%',
											enableKeyEvents:true,
											listeners : {
									 		keyup:function(field, e){
								                     if(e.getKey() == 13){
								                     	Ext.getCmp('outCost').focus(true,true);
								                     }
								              
									 			}
							 				}
									       },{
											xtype : 'numberfield',
											labelAlign : 'left',
											readOnly  :false,
											allowBlank : false,
											maxLength :7,
											blankText : "寄出费用不能为空！",
											fieldLabel : '寄出费用<font style="color:red;">*</font>',
											name : 'outCost',
											value:0,
											id:'outCost',
											anchor : '95%',
											allowNegative :false,
											enableKeyEvents:true,
											listeners : {
									 			keyup:function(field, e){
								                     if(e.getKey() == 13){
								                     	Ext.getCmp('outBtn').focus(true,true);
								                     }
								              
									 			}
							 				}
									       }
									]
								}]
						},{
							layout:'column',
							items:[{
									layout:'form',
									columnWidth : .95,
									items:[
										outReceiptGrid
									]
							}]
						}
					]
    		});
		var win = new Ext.Window({
		title : '回单寄出',
		width : 500,
		closeAction : 'hide',
		plain : true,
		modal : true,
		items : outform,
		buttonAlign : "center",
		buttons : [{
			text : "保存",
			iconCls : 'groupSave',
			id:'outBtn',
			handler : function() {
				if(receiptOutStore.data.length<1){
					Ext.Msg.alert(alertTitle, "没有要寄出的回单！");
					return;
				}
				if(!Ext.getCmp('outManId').isValid()){
					top.Ext.Msg.alert(alertTitle, "寄出人不能为空！",function(){
						Ext.getCmp('outManId').focus(true,true);
					});
					return;
				}
				if(!Ext.getCmp('outWay').isValid()){
					top.Ext.Msg.alert(alertTitle, "寄出方式不能为空！",function(){
						Ext.getCmp('outWay').focus(true,true);
					});
					return;
				}
				if(!Ext.getCmp('outCompany').isValid()){
					top.Ext.Msg.alert(alertTitle, "寄出公司不能为空！",function(){
						Ext.getCmp('outCompany').focus(true,true);
					});
					return;
				}
				if(!Ext.getCmp('outNo').isValid()){
					top.Ext.Msg.alert(alertTitle, "寄出单号不能为空！",function(){
						Ext.getCmp('outNo').focus(true,true);
					});
					return;
				}
				if(!Ext.getCmp('outCost').isValid()){
					top.Ext.Msg.alert(alertTitle, "寄出费用不能为空！",function(){
						Ext.getCmp('outCost').focus(true,true);
					});
					return;
				}
					var dnos='';
					for (var i = 0; i < receiptOutStore.data.length; i++) {
						var vrecord = receiptOutStore.getAt(i);
						dnos+=vrecord.data.dno+',';
					}
					if(!outform.getForm().isValid()){
						Ext.Msg.alert(alertTitle,'请输入必要信息！');
						return;
					}
					outform.getForm().submit({
						url : sysPath+ '/'+saveOutUrl,
						params:{dnos:dnos},
						waitMsg : '正在保存数据...',
						success : function(form, action) {
							win.hide();
							Ext.Msg.alert(alertTitle,'回单寄出成功！',function(){		
								//searchreceipt();
							});
						},
						failure : function(form, action) {
							if (action.faliureType == Ext.form.Action.SERVER_INVALID) {
								Ext.Msg.alert(alertTitle, "服务器连接异常，请联系管理员！");
							} else {
								if (action.result.msg) {
									Ext.Msg.alert(alertTitle,
											action.result.msg);
		
								}
							}
						}
					});
			}
		},  {
			text : "取消",
			handler : function() {
				win.hide();
			}
		}]
	});
	win.on('hide', function() {
				outform.destroy();
			});
	win.show();
}
//回单确收
function receiptConfirm(_records){
var receiptConfirmFields=[
		{name:'dno'},
      	{name:'flightMainNo'},
      	{name:'subNo'},
      	{name:'consigneeInfo'},
      	{name:'cpName'},
      	{name:'piece'},
      	{name:'confirmStatus'},
      	{name:'remark'},
      	{name:'confirmNum'}
]
var receiptConfirmStore = new Ext.data.Store({
        storeId:"receiptConfirmStore",
        reader: new Ext.data.JsonReader({
            root: 'result', totalProperty: 'totalCount'
        },receiptConfirmFields)
    });
var confirm_record_index = 0;
var confirmRowNum = new Ext.grid.RowNumberer({
        header:'序号', width:30,
        renderer:function(value,metadata,record,rowIndex){
         	confirm_record_index++;
	　　　	return　confirm_record_index;
	　　}
    });
var confirmReceiptGrid = new Ext.grid.GridPanel({
 				region : "center",
 				id : 'confirmReceiptGrid',
 				height : 250,
 				width : 500,
 				autoScroll : true,
 				frame : false,
 				loadMask : true,
 				stripeRows : true,
 				viewConfig:{
 				   scrollOffset: 0
 				},
 				cm : new Ext.grid.ColumnModel([confirmRowNum,
 						new Ext.grid.CheckboxSelectionModel(), 
 						{header : '配送单号',dataIndex:'dno',width:80}, 
 						{header : '确收份数',dataIndex:'confirmNum',width:60}, 
 						{header : '主单号',dataIndex : 'flightMainNo',width:80}, 
 						{header : '分单号',dataIndex : 'subNo',width:60}, 
 						{header : '收货人信息',dataIndex : 'consigneeInfo',width:200},
 						{header : '发货代理',dataIndex : 'cpName',width:80},
 						{header : '件数',dataIndex : 'piece',width:60}, 
 						{header : '是否异常',dataIndex:'confirmStatus',width:60,
 							renderer:function(v,m){
	 							return v==2?"<font color=red>是</font>":"否";
 							}	
 						},
 						{header : '备注',dataIndex : 'remark',width:100}
 				]),
 				sm : new Ext.grid.CheckboxSelectionModel(),
 				ds : receiptConfirmStore,
 				listeners:{
 					click:function(){
			    		var records = this.getSelectionModel().getSelections();
				        var deletebtn = Ext.getCmp('confirmDelBtn');
				        if (records.length>=1) {       	
				            if(deletebtn){
				            	deletebtn.setDisabled(false);
				            }
				        } else {
							if(deletebtn){
				            	deletebtn.setDisabled(true);
							}
				        }
				     }
 				}
    	});
	var confirm_record_index=0;
			var form = new Ext.form.FormPanel({
    				id:'addForm',
					frame : true,
					border : false,
					bodyStyle : 'padding:5px 5px 5px',
					labelAlign : "right",
					labelWidth : 60,
					width : 500,
					items:[{
						 layout : 'column',
						 items:[
						 {
							 layout:'form',
							 columnWidth:.5,
							 items:[
								{xtype : 'numberfield',
								labelAlign : 'left',
								allowBlank : false,
								fieldLabel : '配送单号<font style="color:red;"><b>*</b></font>',
								name : 'dno',
								selectOnFocus:true,
								maxLength:10,
								id:'comfirmDno',
								anchor : '95%',
								enableKeyEvents:true,
								listeners:{
						 			keyup:function(field, e){
					                     if(e.getKey() == 13){
					                     	Ext.getCmp('comfirmNum').focus(true,true);
					                     }
						 			}
								}
						       },
						       {xtype:'textfield',
								fieldLabel:'备注',
								id:'comfirmRemark',
								anchor : '95%'
						       }
    					]},{
    						 layout:'form',
    						 columnWidth:.3,
    						 items:[
						       	{
								xtype : 'numberfield',
								labelAlign : 'left',
								allowBlank : false,
								maxLength :4,
								selectOnFocus:true,
								id:'comfirmNum',
								blankText : "确收份数不能为空！",
								fieldLabel : '确收份数<font style="color:red;"><b>*</b></font>',
								name : 'confirmNum',
								allowNegative :false,
								minValue :1,
								anchor : '95%',
								enableKeyEvents:true,
								listeners : {
					 			   keyup:function(field, e){
				                     if(e.getKey() == 13){
				                     	Ext.getCmp('confirmAddBtn').focus(true,true);
				                     }
						 		   }
				 				}
						       },{xtype:'combo',
								editable:false,
								triggerAction : 'all',
								allowBlank : false,
								blankText : "确认状态不能为空！",
								fieldLabel:'确认状态',
								store:confirmStatusStore,
								mode:'local',
								id:'comboconfirmStatus',
								valueField:'confirmStatusId',
								displayField:'confirmStatusName',
								hiddenName:'confirmStatus',
								anchor : '95%',
								enableKeyEvents:true,
								listeners : {
									afterRender: function(combo) {   
							       　　combo.setValue('1');
									}
								}
						       }
    						]
    						},{
								layout:'form',
								columnWidth:.1,
								items:[{
									xtype:'button',
					 				disabled:false,
					 				id:'confirmAddBtn',
					 				text:'添加',
					 				handler:function(){
					 					var vdno = Ext.get('comfirmDno').dom.value;
					 					var vnum = Ext.get('comfirmNum').dom.value;
					 					if (form.getForm().isValid()) {
					 						for (var i = 0; i < receiptConfirmStore.data.length; i++) {
												if (receiptConfirmStore.getAt(i).get('dno') == vdno) {
													top.Ext.Msg.alert(alertTitle,'该配送单号已经存在!');
													Ext.getCmp('comfirmDno').focus(true,true);
													return;
												}
											}
					 						Ext.Ajax.request({
												url : sysPath+'/'+ gridSearchSqlUrl,
												params :{filter_dno:vdno},
												success : function(resp) {
													var rstore = Ext.util.JSON.decode(resp.responseText);
													var result = rstore.resultMap;
													try{
														if(result.length==0){
															top.Ext.Msg.alert(alertTitle,'您输入的配送单号不存在!',
																function(){
																	Ext.getCmp('comfirmDno').focus();
																});
														}else if(result.length>0){
															
															if(result[0].CONFIRMSTATUS==1){
																top.Ext.Msg.alert(alertTitle,'该回单已经确收!',
																	function(){
																		Ext.getCmp('comfirmDno').focus();
																		return false;
																	});
															}else if(null==result[0].SIGNMAN || result[0].SIGNMAN.length<1){
																top.Ext.Msg.alert(alertTitle,'该回单还没有签收，请先签收!',
																	function(){
																		Ext.getCmp('comfirmDno').focus();
																		return false;
																	});
															}else{
																var recd=(new Ext.data.Record({
																 	dno:result[0].DNO,
																 	confirmNum:vnum,
																 	flightMainNo:result[0].FLIGHT_MAIN_NO,
																 	consigneeInfo:result[0].CONSIGNEE+'/'+result[0].CONSIGNEETEL+'/'+result[0].ADDR,
																 	subNo:result[0].SUBNO,
																 	cpName:result[0].CPNAME,
																 	piece:result[0].PIECE,
																 	confirmStatus:Ext.getCmp('comboconfirmStatus').getValue(),
																 	remark:Ext.get('comfirmRemark').dom.value
																 }));
																 receiptConfirmStore.insert(0,recd);
																 Ext.getCmp('comfirmDno').focus(true,true);
															 }
														}
													}catch(e){
														
													}
												}
											});
					 					}else{
					 						var vdno=Ext.getCmp('comfirmDno');
					 						var vcomfirmNum=Ext.getCmp('comfirmNum');
					 						if(!vdno.validate()){
					 							top.Ext.Msg.alert(alertTitle,'请输入配送单号!',
													function(){
														vdno.focus();
														return;
													});
					 						}else if(!vcomfirmNum.validate()){
					 							top.Ext.Msg.alert(alertTitle,'请输入确收件数!',
													function(){
														vcomfirmNum.focus();
														return;
													});
					 						}
					 					}	
					 				}
								}]
							},{
								layout:'form',
								columnWidth:.1,
								items:[{
									xtype:'button',
					 				disabled:true,
					 				id:'confirmDelBtn',
					 				text:'删除',
					 				handler:function(){
								    	var records=confirmReceiptGrid.getSelectionModel().getSelections();
								    	if(records.length<1){
								    		Ext.Msg.alert(alertTitle,'请选中您要删除的回单！');
					 						return false;
								    	}
					 					Ext.Msg.confirm(alertTitle, "确定要删除吗？", function(btnYes) {
									    	if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
												receiptConfirmStore.remove(records); 
										   }
									  });
					 				}
								}]
							}
    						]
						},{
							layout:'form',
							items:[
								confirmReceiptGrid
							]
						}
					]
    		});
		var win = new Ext.Window({
		title : '回单确收',
		width : 500,
		closeAction : 'hide',
		plain : true,
		modal : true,
		items : form,
		buttonAlign : "center",
		buttons : [{
			text : "保存",
			iconCls : 'groupSave',
			id:'comfirmBtn',
			handler : function() {
				if(receiptConfirmStore.data.length<1){
					Ext.Msg.alert(alertTitle, "没有要确收的回单！");
					return;
				}
					var datas='';
					for (var i = 0; i < receiptConfirmStore.data.length; i++) {
						var vrecord = receiptConfirmStore.getAt(i);
						if(i>0){
							datas+='&';
						}	
						datas +="confirmVo["+i+"].dno="+vrecord.data.dno+'&'
						      +"confirmVo["+i+"].remark="+vrecord.data.remark+'&'
						      +"confirmVo["+i+"].confirmStatus="+vrecord.data.confirmStatus+'&'
						      +"confirmVo["+i+"].confirmNum="+vrecord.data.confirmNum;
					}
					form.getForm().submit({
						url : sysPath
								+ '/'+saveConfirmUrl,
								params:datas,
						waitMsg : '正在保存数据...',
						success : function(form, action) {
							win.hide();
							Ext.Msg.alert(alertTitle,'回单确收成功！',function(){		
								//searchreceipt();
							});
						},
						failure : function(form, action) {
							if (action.faliureType == Ext.form.Action.SERVER_INVALID) {
								Ext.Msg.alert(alertTitle, "服务器连接异常，请联系管理员！");
							} else {
								if (action.result.msg) {
									Ext.Msg.alert(alertTitle,
											action.result.msg);
		
								}
							}
						}
					});
			}
		},  {
			text : "取消",
			handler : function() {
				win.hide();
			}
		}]
	});
	win.on('hide', function() {
				form.destroy();
			});
	win.show();
}

function searchreceiptByDno(dno) {

		dateStore.proxy = new Ext.data.HttpProxy({
			method : 'POST',
			url : sysPath+ "/"+gridSearchSqlUrl,
				params:{privilege:privilege,limit : pageSize}
		});
		searchDno=dno;
	 	dateStore.removeAll();
		dateStore.reload({callback:function(){
			
			for(var i=0;i<totalStore.data.length;i++){
				if(totalStore.getAt(i).get('dno')==dno){
					totalStore.removeAt(i);
				}
			}
			addRecord(dateStore);
			receiptGrid.doLayout();
		}});
		
	}

//判断是否允许执行该操作
function isNotAllow(_records,allowStatus){
	
	for(var i=0;i<_records.length;i++){
		
		var curStatus=_records[i].data.curStatus;
		
		if(curStatus!=allowStatus){
			if(null==allowStatus){
				Ext.Msg.alert(alertTitle, "您选择的第"+(i+1)+"条回单记录已经入库！");
			}else{
				Ext.Msg.alert(alertTitle, "您选择的第"+(i+1)+"条回单记录不是"+allowStatus+"状态！");
			}
			return false;
		}
	}
	return true;
	
}
function removeRecord(){
  	var record = receiptGrid.getSelectionModel().getSelected();
    dateStore.remove(record); 
    totalStore.remove(record);
	receiptGrid.doLayout();
}
function addRecord(newStore){
	record_start=0;
	for(var i=0;i<newStore.data.length;i++){
		totalStore.insert(0,newStore.getAt(i));
	}
	dateStore.removeAll();
	for(var i=0;i<totalStore.data.length;i++){
		dateStore.insert(0,totalStore.getAt(i));
	}
	
}
 function dataReload(){ 
			dateStore.reload({
			params : {
				start : 0,
				privilege:privilege,
				limit : pageSize
			}
		});
		receiptGrid.getSelectionModel().selectAll();
		
 }
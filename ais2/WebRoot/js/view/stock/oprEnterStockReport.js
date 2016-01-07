//入库点到js
var privilege = 47;//权限参数
var gridSearchUrl = "stock/oprOvermemoDetailAction!ralaList.action";
var gridSearchSQLUrl = "stock/oprOvermemoDetailAction!findEnterReport.action";
var gridSearchFaxUrl = 'stock/oprOvermemoDetailAction!findByDNO.action';//单票添加地址
var reportConfirmUrl="stock/oprOvermemoDetailAction!reportConfirm.action";//入库点到地址
var dictionaryUrl='sys/dictionaryAction!ralaList.action';
var carUrl = "bascar/basCarAction!list.action";
var loadingbrigadeUrl='sys/loadingbrigadeAction!list.action';//装卸组分拨组查询地址
var searchMemoByCar='stock/oprOvermemoAction!findByCar.action';
var hessianTestUrl="stock/oprOvermemoDetailAction!insertEdiTest2.action";//EDI跨项目调用测试地址
var searchCusNameUrl='sys/customerAction!list.action';//查询代理公司地址   
var defaultWidth=80;     
var dateStore;                  

   isOprStore= new Ext.data.SimpleStore({
			 //autoLoad:true, //此处设置为自动加载
  			  data:[[0,'未执行'],[1,'已执行'],[2,'执行失败']],
   			 fields:["isOprId","isOprName"]
		});
	//分拨组
	 dispatchGroupStore= new Ext.data.Store({ 
		storeId:"dispatchGroupStore",
		baseParams:{filter_EQL_type:1,filter_EQL_departId:bussDepart},
		proxy:new Ext.data.HttpProxy({url:sysPath+"/"+loadingbrigadeUrl}),
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
    	{name:'dispatchGroupId',mapping:'id'},
    	{name:'dispatchGroupName',mapping:'loadingName'}
    	])
	});
	//装卸组
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
	//代理公司
		cpNameStore= new Ext.data.Store({ 
			storeId:"cpNameStore",
			baseParams:{privilege:61,filter_NES_custprop:'新邦'},
			proxy:new Ext.data.HttpProxy({url:sysPath+"/"+searchCusNameUrl}),
			reader:new Ext.data.JsonReader({
	                    root:'result', totalProperty:'totalCount'
	        },[
        	{name:'cusName',mapping:'cusName'},
        	{name:'cusId',mapping:'id'}
        	])
		});
var fields = [{
			name : "id",
			mapping : 'ID'
		}, {
			name : 'overmemoId',
			mapping : 'OVERMEMOID'
		}, {
			name : "dno",
			mapping : 'DNO'
		}, {
			name : 'subNo',
			mapping : 'SUBNO'
		}, {
			name : 'realPiece',
			mapping : 'REALPIECE'
		}, {
			name : 'piece',
			mapping : 'PIECE'
		}, {
			name : 'cusId',
			mapping : 'CUSID'
		}, {
			name : 'cpName',
			mapping : 'CPNAME'
		}, {
			name : 'weight',
			mapping : 'CQWEIGHT'
		}, {
			name : 'flightInfo',
			mapping : 'FLIGHTINFO'
		}, {
			name : 'distributionMode',
			mapping : 'DISTRIBUTIONMODE'
		}, {
			name : 'takeMode',
			mapping : 'TAKEMODE'
		}, {
			name : 'goods',
			mapping : 'GOODS'
		}, {
			name : 'request',
			mapping : 'REQUEST'
		}, {
			name : 'carId',
			mapping : 'CARID'
		}, {
			name : 'carCode',
			mapping : 'CARCODE'
		}, {
			name : 'status',
			mapping : 'STATUS'
		}, {
			name : 'isOpr',
			mapping : 'ISOPR'
		}, {
			name : 'remark',
			mapping : 'REMARK'
		}, {
			name : 'stockAreaName',
			mapping : 'STOCKAREANAME'
		},{name:'surePiece',mapping:'TOTALPIECE'} ,
		{name:'splitNum',mapping:'SPLITNUM'},
		{name:'receiptType',mapping:'RECEIPTTYPE'},
		{name:'addr',mapping:'ADDR'},
		{name:'consignee',mapping:'CONSIGNEE'},
		{name:'flightMainNo',mapping:'FLIGHTMAINNO'},
		{name:'bulk',mapping:'BULK'},
		{name:'distributionMode',mapping:'DISTRIBUTIONMODE'},
		{name:'takeMode',mapping:'TAKEMODE'},
		{name:'requestDoId',mapping:'REQUESTDOID'},
		{name:'orderfield',mapping:'ORDERFIELDS'},
		{name:'totalPiece',mapping:'TOTALPIECE'},
		{name:'shouldPiece',mapping:'SHOULDPIECE'},
		{name:'consigneeTel',mapping:'CONSIGNEETEL'},
		{name:'consignee',mapping:'CONSIGNEE'},
		{name:'splitNum',mapping:'SPLITNUM'}];

carStore = new Ext.data.Store({
			storeId : "carStore",
			proxy : new Ext.data.HttpProxy({
						url : sysPath + "/" + carUrl
					}),
			reader : new Ext.data.JsonReader({
						root : 'result',
						totalProperty : 'totalCount'
					}, [{
								name : 'carId',
								mapping : 'id'
							}, {
								name : 'carCode',
								mapping : 'carCode'
							}])

		});
function isAirPort(_records){
	for(var i=0;i<_records.length;i++){
			if(_records[i].data.takeMode=='机场自提'){
				return true;
			}
		}
		return false;
}

var tbar = [{
	text : '<b>点到确认</b>',
	iconCls : 'save',
	tooltip : '点到确认',
	handler : function() {
		enterStock();
	}
}, '-', {
	text : '<b>单票添加</b>',
	iconCls : 'groupAdd',
	tooltip : '单票添加',
	handler : function() {
		addcolumn();
		
	}},
	'-', '装卸组:<font color="red"><B>*</B></font>',{

			xtype : "combo",
			width : 100,
			id : 'loadingbrigade',
			queryParam : 'filter_LIKES_loadingName',
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
			hiddenName : 'loadingbrigadeId'

	}, '-', '分拨组:<font color="red"><B>*</B></font>', {

			xtype : "combo",
			width : 100,
			id : 'dispatchGroup',
			typeAhead : true,
			queryParam : 'filter_LIKES_loadingName',
			pageSize : comboSize,
			minChars : 0,
			editable:true,
			forceSelection : true,
			selectOnFocus : true,
			resizable : true,
			triggerAction : 'all',
			store : dispatchGroupStore,
			minChars : 0,
			mode : "remote",// 从本地载值
			valueField : 'dispatchGroupId',// value值，与fields对应
			displayField : 'dispatchGroupName',// 显示值，与fields对应
			name : 'dispatchGroup'
	},{text : '<b>清空</b>',id : 'clearbtn',iconCls : 'btnLeft',
		    handler : function(){
				dateStore.removeAll();
				receiptGrid.doLayout();
		    }
   	}];
var countTbar=new Ext.Toolbar([
		'总票数：',{xtype:'textfield',
		id:'countNum', 
		width:defaultWidth-30,
		style:'background:gray',
		value:0,
		readOnly:true
		},
		'总开单件数：',{xtype:'textfield',
		id:'countFaxPiece', 
		width:defaultWidth-30,
		style:'background:gray',
		value:0,
		readOnly:true
		},
		'总应到件数：',{xtype:'textfield',
		id:'countShouldPiece', 
		width:defaultWidth-30,
		style:'background:gray',
		value:0,
		readOnly:true
		},
		'总已到件数：',{xtype:'textfield',
		id:'countRealPiece', 
		width:defaultWidth-30,
		style:'background:gray',
		value:0,
		readOnly:true
		}
	]);
	/**
 	 *统计页面上的记录
 	 */
 	 function setCount(records){
 	 	var vcountNum=0;
 	 	var vFaxPiece=0;
 	 	var vcountShouldPiece=0;
 	 	var vcountRealPiece=0;
 	 	
 	 	for(var i=0;i<records.length;i++){
 	 		vcountNum++;
 	 		vFaxPiece+=(records[i].data.totalPiece==null?0:records[i].data.totalPiece);
 	 		vcountShouldPiece+=(records[i].data.shouldPiece==null?0:records[i].data.shouldPiece);
 	 		vcountRealPiece+=(records[i].data.realPiece==null?0:records[i].data.realPiece);
 	 	}
 	 	Ext.getCmp('countNum').setValue(vcountNum);
 	 	Ext.getCmp('countFaxPiece').setValue(vFaxPiece);
 	 	Ext.getCmp('countShouldPiece').setValue(vcountShouldPiece);
 	 	Ext.getCmp('countRealPiece').setValue(vcountRealPiece);
 	 }
var queryTbar = new Ext.Toolbar(['-', '车牌号:', {
			xtype : 'combo',
			//typeAhead : true,
			id : 'carcombo',
			queryParam : 'filter_LIKES_carCode',
			triggerAction : 'all',
			store : carStore,
			pageSize : comboSize,
			//forceSelection : true,
			selectOnFocus : true,
			resizable : true,
			minChars : 0,
			mode : "remote",// 从服务器端加载值
			valueField : 'carId',// value值，与fields对应
			displayField : 'carCode',// 显示值，与fields对应
			hiddenName : 'carId',
			width:80,
			enableKeyEvents : true,
			listeners : {
				keyup : function(textField, e) {

					if (e.getKey() == 13) {
						searchOvermemo();
					}
				}
			}
		}, '-', '交接单号:', {
			xtype : 'numberfield',
			id : 'searchOvermemoId',
			width:80,
			enableKeyEvents : true,
			listeners : {

				keyup : function(textField, e) {

					if (e.getKey() == 13) {

						searchOvermemo();

					}
				}
			}
		}, '-', '主单号:', {
			xtype : 'textfield',
			id : 'searchFlightMainNo',
			width:80,
			enableKeyEvents : true,
			listeners : {
				keyup : function(textField, e) {
					if (e.getKey() == 13) {
						searchOvermemo();
					}
				}
			}
		}, '-','代理名称:',{xtype : "combo",
				width : defaultWidth+20,
				editable:true,
				triggerAction : 'all',
				queryParam : 'filter_LIKES_cusName',
				pageSize : pageSize,
				forceSelection : true,
				resizable:true,
				minChars : 0,
				store : cpNameStore,
				//mode : "remote",// 从本地载值
				valueField : 'cusName',// value值，与fields对应
				displayField : 'cusName',// 显示值，与fields对应
			    name : 'cpName',
			    id:'searchCpName', 
			    enableKeyEvents:true,listeners : {
	 		    keyup:function(textField, e){
                     if(e.getKey() == 13){
                     	searchOvermemo();
                     }
	 		}
	 	
	 	}},'-', {
			text : '<b>查询</b>',
			id : 'btn',
			iconCls : 'btnSearch',
			handler : searchOvermemo
		}]);

Ext.onReady(function() {
	dateStore = new Ext.data.Store({
				storeId : "dateStore",
				proxy : new Ext.data.HttpProxy({
							url : sysPath + "/" + gridSearchSQLUrl,
							baseParams : {
								privilege : privilege,
								filter_EQS_endDepartId:bussDepart,
								filter_EQS_status:0,
								limit : pageSize
							}
						}),
				sortInfo :{field: "dno", direction: "DESC"},
				reader : new Ext.data.JsonReader({
							root : 'resultMap',
							totalProperty : 'totalCount'
						}, fields)
			});

	var sm = new Ext.grid.CheckboxSelectionModel({});
	var rowNum = new Ext.grid.RowNumberer({
				header : '序号',
				width : 30
			});
	overmemoGrid = new Ext.grid.EditorGridPanel({
		renderTo : Ext.getBody(),
		id : 'overmemoCenter',
		autoShow:true,
		height : Ext.lib.Dom.getViewHeight()-1,
		width : Ext.lib.Dom.getViewWidth()-1,
		viewConfig : {
			columnsText : "显示的列",
			sortAscText : "升序",
			sortDescText : "降序"
			//forceFit : true
		},
		autoScroll : true,
		clicksToEdit:1,
		//autoSizeColumns: true,
		frame : false,
		loadMask : true,
		stripeRows : true,
		sm : sm,
		cm : new Ext.grid.ColumnModel([rowNum, sm, 
				{
					header : '配送单号',
					dataIndex : 'dno',
					sortable : true
				},{
					header : '代理公司',
					dataIndex : 'cpName',
					width:defaultWidth,
					sortable : true
				},{
					header : '代理ID',
					dataIndex : 'cusId',
					hidden:true,
					width:defaultWidth,
					sortable : true
				},{
					header : '航班信息',
					width:defaultWidth+10,
					dataIndex : 'flightInfo',
					sortable : true
				},{
					header : '航班落地时间',
					width:defaultWidth+30,
					dataIndex : 'flightTime',
					hidden:true,
					sortable : true
				},{
					header : '主单号',
					dataIndex : 'flightMainNo',
					width:defaultWidth,
					sortable : true
				},{
					header : '分单号',
					dataIndex : 'subNo',
					width:defaultWidth,
					sortable : true
				},{
					header : '开单件数',
					dataIndex : 'totalPiece',
					width:defaultWidth,
					sortable : true
				}, {
					header : '应到件数',
					dataIndex : 'shouldPiece',
					width:defaultWidth,
					sortable : true
				}, {
					header : '已到件数',
					width:defaultWidth,
					dataIndex : 'realPiece',
					sortable : true
				},{
					header : '实到件数',
					width:defaultWidth,
					dataIndex:'surePiece',
					css :colcss,
					sortable : true,
					editor : new Ext.grid.GridEditor(
						new Ext.form.NumberField({
						allowNegative :false,
						style: 'text-align:left',
						selectOnFocus:true
					}))
				},{
					header : '拆单份数',
					dataIndex:'splitNum',
					width:defaultWidth,
					css :colcss,
					editor : new Ext.grid.GridEditor(
			 			new Ext.form.NumberField({
							allowNegative :false,
							style: 'text-align:left',
							selectOnFocus:true
					}))
				},{
					header : '配送方式',
					width:defaultWidth,
					dataIndex : 'distributionMode',
					//hidden:true,
					sortable : true
				},{
					header : '提货方式',
					width:defaultWidth,
					dataIndex : 'takeMode',
					//hidden:true,
					sortable : true
				}, {
					header : '状态',
					width:defaultWidth,
					dataIndex : 'status',
					sortable : true,
					renderer:function(v){
						if(null==v || v==0){
							return "未点到";
						}else if(v==1){
							return "已点到";
						}else if(v==2){
							return "部分点到";
						}
			        }
				},{
					header : '交接单明细编号',
					dataIndex : 'id',
					hidden:true,
					width:defaultWidth,
					sortable : true
				},  {
					header : '代理重量',
					width:defaultWidth,
					dataIndex : 'weight',
					sortable : true
				},{
					header : '原件签单',
					dataIndex : 'receiptType',
					width:defaultWidth,
					renderer:function(v){
 									if(v=='送货原件返回' || v=='指定原件返回'){
 										return '是';
 									}else{
 										return '否';
 									}
			        		},
					sortable : true
				}, {
					header : '个性化要求',
					width:defaultWidth,
					dataIndex : 'request',
					sortable : true
				},{
					header : '要求执行情况',
					width:defaultWidth,
					dataIndex : 'isOpr',
					css:colcss,
					renderer:function renderDescn(v, cellmeta, record, rowIndex, columnIndex, store){
						//var req =record.data['request'];
						//if(null!=req && req.length>0){
						//	cellmeta.css = colcss;
						//}
						if(v==1){
							return '已执行';
						}else if(v==0){
							return '未执行';
						}else{
							return '';
						}
					},
					editor:{
						xtype:'checkbox',
						boxLabel :'执行'
					},
					sortable : true
				},  {
					header : '备注',
					width:defaultWidth,
					dataIndex:'remark',
					css :colcss,
					editor : new Ext.grid.GridEditor(new Ext.form.TextField({
							}))
				}, {
					header : '交接单号',
					dataIndex : 'overmemoId',
					width:defaultWidth,
					sortable : true
				}, {
					header : '库存区域',
					dataIndex : 'stockAreaName',
					width:defaultWidth,
					sortable : true
				}, {
					header : '品名',
					dataIndex : 'goods',
					width:defaultWidth,
					sortable : true
				}, {
					header : '车牌号',
					dataIndex : 'carCode',
					width:defaultWidth,
					sortable : true
				}, {
					header : '体积',
					width:defaultWidth,
					dataIndex : 'bulk',
					hidden:true,
					sortable : true
				},{
					header : '个性化要求表ID',
					width:defaultWidth,
					dataIndex : 'requestDoId',
					hidden:true,
					sortable : true
				},{
					header : '航空主货单',
					width:defaultWidth,
					dataIndex : 'flightMainNo',
					hidden:true,
					sortable : true
				},{
					header : '收货人',
					hidden:true,
					width:defaultWidth,
					dataIndex : 'consignee',
					sortable : true
				}, {
					header : '收货人地址',
					dataIndex : 'addr',
					width:defaultWidth,
					hidden:true,
					sortable : true
				},{
					header : '收货人信息',
					dataIndex : 'consigneeInfo',
					width:defaultWidth+40,
					sortable : true
						,renderer:function renderDescn(value, cellmeta, record, rowIndex, columnIndex, store){
							return (record.data["consignee"]==null?'':record.data["consignee"])+"<font color='red'>/</font>"+(record.data["consigneeTel"]==null?'':record.data["consigneeTel"]);
						}
				}]),
		store : dateStore,

		tbar : tbar,
		listeners : {
			render : function() {
				queryTbar.render(this.tbar);
				countTbar.render(this.tbar);
			},
			click:function(grid,e){
            	setCount(this.getSelectionModel().getSelections());
            }
		},
		bbar : new Ext.PagingToolbar({
            pageSize: pageSize, 
            privilege:privilege,
            store: dateStore, 
            afterPageText: '共{0}页',
		    beforePageText: '当前页',
            displayInfo: true,
            displayMsg: '显示第 {0} 条到 {1} 条记录，共 {2} 条', 
            emptyMsg: "没有记录信息显示"
	     })
	});
	 overmemoGrid.on('beforeedit',function(obj){

        //field：控制到某一列，record.get("name")：控制到某一行
        var record = obj.record;
        if(obj.field=='isOpr' && obj.record.data.isOpr==null){
                return false;//不可编辑
        }
        return true;         //可编辑
    });
});

function searchOvermemo() {
	dateStore.proxy = new Ext.data.HttpProxy({
				method : 'POST',
				url : sysPath + "/" + gridSearchSQLUrl,
				params : {
					privilege : privilege,
					filter_EQS_endDepartId:bussDepart,
					filter_EQS_status:0,
					limit : pageSize
				}
			});
	var carCode = Ext.getCmp('carcombo').getRawValue();
	var carId=Ext.getCmp('carcombo').getValue();
	var searchOvermemoId = Ext.getCmp('searchOvermemoId').getValue();
	var searchFlightMainNo = Ext.getCmp('searchFlightMainNo').getValue();
	var cpName =Ext.getCmp('searchCpName').getRawValue();
	
	if(searchOvermemoId!=null && searchOvermemoId.length>0){
		dateStore.sortInfo ={field:"subNo", direction: "DESC"};
	}else{
		dateStore.sortInfo ={field:"dno", direction: "DESC"};
	}
	Ext.apply(dateStore.baseParams, {
				filter_EQS_overmemoId : searchOvermemoId,
				filter_EQS_endDepartId:bussDepart,
				filter_EQL_flightMainNo:searchFlightMainNo,
				filter_LIKES_carCode : carCode,
				filter_LIKES_cpName:cpName,
				filter_EQS_status:0,
				privilege : privilege,
				start : 0
			});

	dataReload();
			
}

function dataReload() {
	dateStore.load({
				params : {
					start : 0,
					privilege : privilege,
					filter_EQS_endDepartId:bussDepart,
					filter_EQS_status:0,
					limit : pageSize
				},sortInfo :{field:'dno',direction: "DESC"}
			});
}

/*
var bodymap = new Ext.KeyMap(Ext.getBody(), {
     key: 113, 
     fn: function(){
		addcolumn();
     }
 });*/
 function addcolumn(){
 	var recd;
 	var departAlertMsg='';
 	var sflag=false;
	var form = new Ext.form.FormPanel({
			labelAlign : 'left',
			frame : true,
			width : 400,
			bodyStyle : 'padding:5px 5px 0',
			reader : new Ext.data.JsonReader({
            root: 'result', totalProperty: 'totalCount'
        }, fields),
		labelAlign : "right",
				items : [{
							layout : 'column',
							items : [{
							columnWidth : .95,
							selectOnFocus:true,
							layout : 'form',
								items : [{
											xtype : 'numberfield',
											labelAlign : 'left',
											id:'singleDno',
											fieldLabel : '配送单号<font style="color:red;">*</font>',
											name : 'dno',
											allowBlank : false,
											anchor : '95%',
											enableKeyEvents:true,
											listeners : {
												blur:function(field,e){
													if(field.getValue()==null || field.getValue()==''){
															return;
													}
														for (var i = 0; i < dateStore.data.length; i++) {
															if (dateStore.getAt(i).get('dno') == field.getValue()) {
																	Ext.getCmp('reportInform').setValue('该配送单号已经存在！');
																	Ext.getCmp('singleDno').focus(true,true);
																	sflag =false;
																	return ;
															}
														}
													
													Ext.Ajax.request({
														url : sysPath+'/'
																+ gridSearchFaxUrl+"?dno="+field.getValue()+"&authority=yes&privilege="+privilege,
														success : function(resp) {
															var jdata = Ext.util.JSON.decode(resp.responseText);
															if(null != jdata.success && !jdata.success){
																	sflag =false;
																	Ext.Msg.alert(alertTitle,jdata.msg);
															}else if(null==jdata.success && jdata.length==0){
																	sflag =false;
																	Ext.getCmp('reportInform').setValue('您输入的配送单号不存在 或者 已经出库 或者已预配！');
																	Ext.getCmp('singleDno').focus(true,true);
															}else {
																if(jdata[0].DISTRIBUTION_DEPART_ID!=bussDepart){
																	departAlertMsg="此票货物的配送部门为<font color=red>"+jdata[0].DISTRIBUTION_DEPART+"</font>，确定要添加吗？";
																}else if(jdata[0].END_DEPART_ID!=bussDepart){
																	departAlertMsg="此票货物的终端部门为<font color=red>"+jdata[0].END_DEPART+"</font>，确定要添加吗？";
																}
																recd = addRecord(jdata);
																sflag =true;
															 }
														}
													});
													},
									 			keyup:function(field, e){
								                     if(e.getKey() == 13){
									                    if(field.getValue()==null || field.getValue()==''){
																return;
														}else{
								                     		Ext.getCmp('reportBtn').focus(true,true);
								                     	}
								                     }
									 			}
							 				}
									       },{
											xtype : 'textarea',
											labelAlign : 'left',
											readOnly  :true,
											style:'color:red',
											allowBlank : false,
											height:60,
											fieldLabel : '配送单信息',
											id : 'reportInform',
											anchor : '95%'
									     }]
								}]
							}]
						});
		Ext.getCmp('singleDno').focus();							
		var win = new Ext.Window({
		title : '单票添加',
		width : 400,
		closeAction : 'hide',
		plain : true,
		modal : true,
		items : form,
		buttonAlign : "center",

		buttons : [{
			text : "确定",
			iconCls : 'groupSave',
			id:'reportBtn',
			handler : function() {
				if (form.getForm().isValid() && sflag) {
					if(departAlertMsg!=null && departAlertMsg.length>1){
						Ext.Msg.confirm(alertTitle, departAlertMsg, function(
								btnYes) {
							if (btnYes == 'yes' || btnYes == 'ok' || btnYes == true) {
								 dateStore.insert(0,recd);
								 departAlertMsg='';
								 Ext.getCmp('reportInform').setValue("");
								 Ext.getCmp('singleDno').focus(true,true);
							}
						});
					}else{
						 dateStore.insert(0,recd);
						 Ext.getCmp('reportInform').setValue("");
						 Ext.getCmp('singleDno').focus(true,true);
					}
				}else{
					Ext.getCmp('singleDno').focus(true,true);
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
function doSearch(recd,searchOvermemoId){
	
		var carId=Ext.getCmp('carcombo').getValue();
			var realPiece=0;

			if(dateStore.data.length>0){
				realPiece = dateStore.getAt(0).get('realPiece');
				
				if(realPiece==null){
					Ext.Msg.alert(alertTitle,'请先填写您未填写的单据！',function(){
						return;
					});
				}else{
					Ext.Ajax.request({
						url : sysPath + '/'+ searchMemoByCar+"?carId="+carId+'&overmemoId='+searchOvermemoId,
						success : function(resp) {
							if(resp.responseText>1){
								Ext.Msg.alert(alertTitle,'此车牌号有多个为点到的交接单号,请输入正确的交接单号！',function(){
									Ext.getCmp('searchOvermemoId').focus(true,true);
								});
								return;
							}else if(resp.responseText==0){
								Ext.Msg.alert(alertTitle,'没有您要查找的交接单号',function(){
									Ext.getCmp('searchOvermemoId').focus(true,true);
								});
								return;
						}else{
							dateStore.insert(0, recd); 
							overmemoGrid.doLayout(true);
							overmemoGrid.getSelectionModel().selectRow(0);  
							overmemoGrid.startEditing(0, 2); 
						}}
						});
				}
			}else{
			Ext.Ajax.request({
						url : sysPath + '/'+ searchMemoByCar+"?carId="+carId+'&overmemoId='+searchOvermemoId,
						success : function(resp) {
							if(resp.responseText>1){
								Ext.Msg.alert(alertTitle,'此车牌号有多个为点到的交接单号,请输入正确的交接单号！',function(){
									Ext.getCmp('searchOvermemoId').focus(true,true);
								});
								return;
							}else if(resp.responseText==0){
								Ext.Msg.alert(alertTitle,'没有您要查找的交接单号',function(){
									Ext.getCmp('searchOvermemoId').focus(true,true);
								});
								return;
						}else{
							dateStore.insert(0, recd); 
							overmemoGrid.doLayout(true);
							overmemoGrid.getSelectionModel().selectRow(0);  
							overmemoGrid.startEditing(0, 2); 
						}}
						});
			}
}
function enterStock(){
		//overmemo.click();
		overmemoGrid.stopEditing();
		var overmemo = Ext.getCmp('overmemoCenter');
		var _records = overmemo.getSelectionModel().getSelections();
		
		if (_records.length < 1) {
			Ext.Msg.alert(alertTitle, "请选择您要操作的行！");
			return false;
		}
		var loadingbrigade=Ext.getCmp('loadingbrigade').getValue();
		if(loadingbrigade==""){
			Ext.Msg.alert(alertTitle,'请选择装卸组');
			return;
		}
		var disgroup=Ext.getCmp('dispatchGroup').getValue();
		if(disgroup==""){
			Ext.Msg.alert(alertTitle,'请选择分拨组');
			return;
		}
				var flag = isAirPort(_records);
				var alertmsg;
				if(flag){
					alertmsg = "您选择的货物当中有机场自提货物！！！ 确定要点到这" + _records.length + "条记录吗？";
				}else{
					alertmsg = "确定要点到这" + _records.length + "条记录吗？";
				}
				Ext.Msg.confirm(alertTitle, alertmsg, function(
						btnYes) {
					if (btnYes == 'yes' || btnYes == 'ok' || btnYes == true) {
						var datas = "";
						var flag=false;
						for(var i=0;i<_records.length;i++){
										var teshu=_records[i].data.request;
										var yuanjian=_records[i].data.receiptType;
										var beizhu=_records[i].data.remark;
//										alert('isOpr'+_records[i].get('dno'));
										var iszhi=_records[i].data.isOpr;
										//alert(iszhi);
										var zhixing=iszhi==true?1:0;
//										alert(zhixing);
										if(null==_records[i].data.dno || _records[i].data.dno<0){
											Ext.Msg.alert(alertTitle,'第'+(i+1)+'条货物无传真,不允许点到！');
											return;
										}
										if((null!=teshu && teshu.length>0) && (null!=yuanjian && yuanjian.length>0)){
											if(zhixing!=1 && (null==beizhu || beizhu.length==0)){
												Ext.Msg.alert(alertTitle,'第'+(i+1)+'条数据个性化要求未处理，请填写备注！');
												return;
											}
										}
									if(null==_records[i].data.shouldPiece || null==_records[i].data.weight){
										
										Ext.Msg.alert(alertTitle,'第'+(i+1)+'条数不允许点到，请把信息填写完整！');
										return;
									}else{
										if(_records[i].data.surePiece==null){
											Ext.Msg.alert(alertTitle,'实到件数不允许为空！');
											return;
										}
										//alert(_records[i].data.splitNum);
										if(i>0)
										{
											datas+='&';
										}	
										   var perWeight = _records[i].data.surePiece/_records[i].data.totalPiece;
										   if(_records[i].data.totalPiece==0 || _records[i].data.surePiece==0){
										   		perWeight=0;
										   }
										   datas +="overIds["+i+"].overmemoDetailId="+_records[i].data.id+'&'
										               +"overIds["+i+"].dno="+_records[i].data.dno+'&'
										               +"overIds["+i+"].piece="+(_records[i].data.shouldPiece==null?0:_records[i].data.shouldPiece)+'&'
										               +"overIds["+i+"].cqWeight="+(_records[i].data.weight==null?0:(_records[i].data.weight*perWeight))+'&'
										               +"overIds["+i+"].overmemoNo="+_records[i].data.overmemoId+'&'
										               +"overIds["+i+"].reqRemark="+(_records[i].data.remark==null?'':_records[i].data.remark)+'&'
										               +"overIds["+i+"].request="+_records[i].data.request+'&'
										               +"overIds["+i+"].isOpr="+zhixing+'&'
										               +"overIds["+i+"].requestDoId="+(_records[i].data.requestDoId==null?'':_records[i].data.requestDoId)+'&'
										               +"overIds["+i+"].loadingbrigadeId="+loadingbrigade+'&'
										               +"overIds["+i+"].dispatchId="+disgroup+'&'
										               +"overIds["+i+"].takeMode="+_records[i].data.takeMode+'&'
										               +"overIds["+i+"].surePiece="+(_records[i].data.surePiece==null?0:_records[i].data.surePiece)+'&' //实到件数
										               +"overIds["+i+"].splitNum="+(_records[i].data.splitNum==null?0:_records[i].data.splitNum);
									}
								
									if(i==_records.length-1){
										flag=true;
									}
							}
							if(flag){
								Ext.Ajax.request({
									url : sysPath+'/'
											+ reportConfirmUrl+"?privilege="+privilege+"&authority=yes",
									params :datas,
									success : function(resp) {
										var respText = Ext.util.JSON.decode(resp.responseText);
										if(respText.success){
											Ext.Msg.alert(alertTitle,"入库点到成功!");
											searchOvermemo();
										}else{
											Ext.Msg.alert(alertTitle,respText.msg);
										}
									}
								});
							}
					}
				});
}
function addRecord(jdata){
	var oid='';
	var searchOvermemoId = Ext.getCmp('searchOvermemoId').getValue();
	if(null!=searchOvermemoId && searchOvermemoId.length>0){
		oid=searchOvermemoId;
	}
   var recd=(new Ext.data.Record({
      dno:jdata[0].DNO,
      subNo:jdata[0].SUBNO,
      overmemoId:oid,
      id:'',
      requestDoId:jdata[0].REQUESTDOID,
      flightMainNo:jdata[0].FLIGHTMAINNO, 
      bulk:jdata[0].BULK,
      addr:jdata[0].ADDR,
      consignee:jdata[0].CONSIGNEE,
      consigneeTel:jdata[0].CONSIGNEETEL,
      flightInfo:jdata[0].FLIGHTNO+'/'+jdata[0].FLIGHTTIME,
      cpName:jdata[0].CPNAME,
      takeMode:jdata[0].TAKEMODE,//提货方式
      distributionMode:jdata[0].DISTRIBUTIONMODE,//配货方式
      //stockAreaName:jdata[0].库存区域
      goods:jdata[0].GOODS,//品名
      status:jdata[0].STATUS,
      cusId:jdata[0].CUSID,
      remark:jdata[0].REQREMARK,
      splitNum:0,
      surePiece:jdata[0].PIECE,
      shouldPiece:jdata[0].PIECE,
      totalPiece:jdata[0].PIECE,
      weight:jdata[0].CQWEIGHT,
      realPiece:jdata[0].REALPIECE,// 已到件数
      request:jdata[0].REQUEST,//request 个性化要求
      isOpr:jdata[0].ISOPR,//isOpr 是否执行，（要求执行情况）
      receiptType:jdata[0].RECEIPTTYPE//是否有原件
   
    }));
  Ext.getCmp('reportInform').setValue(jdata[0].CONSIGNEE+'/'
                                      +jdata[0].CONSIGNEETEL+'/'
                                      +jdata[0].FLIGHTMAINNO+'/'
                                      +jdata[0].FLIGHTNO+'/'
                                      +jdata[0].DISTRIBUTIONMODE);
   return recd;
}

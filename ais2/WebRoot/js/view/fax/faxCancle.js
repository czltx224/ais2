var privilege=68;
var carTitle;
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
		{name:'cusDepartId'}
		];
var faxStore;
Ext.onReady(function() {
    faxStore=new Ext.data.Store({
		storeId:"faxStore",
		proxy:new Ext.data.HttpProxy({url:sysPath+"/fax/oprFaxInAction!ralaList.action"}),
		baseParams:{limit:pageSize,privilege:privilege,filter_EQL_status:1},
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },faxfields)
   });
   var sm = new Ext.grid.CheckboxSelectionModel({});
   var rowNum = new Ext.grid.RowNumberer({
    		header : '序号',
    		width : 35,
    		sortable : true
    });
	var detailRecord = new Ext.grid.GridPanel({
    				renderTo:'menuGrid',
			        height:Ext.lib.Dom.getViewHeight(), 
			        width:Ext.lib.Dom.getViewWidth(),
			        viewConfig : {
			    		scrollOffset: 0,
						autoScroll:true
					},
					stripeRows : true,
			        frame:true, 
			        loadMask:true,
			        sm:sm,
    				tbar:[{
    					text:'<b>作废</b>',id:'deletebtn',disabled:true, tooltip:'作废传真信息', iconCls: 'userDelete',handler:function(){
    						var _records = detailRecord.getSelectionModel().getSelections();
    						Ext.Msg.confirm(alertTitle, "确定要删除所选的"+_records.length+"行记录吗？", function(btnYes) {
    							if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
    								var ids="";
									for(var i=0;i<_records.length;i++){
										ids=ids+_records[i].data.dno+",";
									}
    								faxCancle(ids);
    							}
    						});
    					}
    				},'-','&nbsp;&nbsp;',
		            {
		            	xtype : 'textfield',
		    			id:'searchContent',
		    			name : 'stationName',
		    			width : 100,
		    			enableKeyEvents:true,
		    			listeners : {
					 		keyup:function(textField, e){
				                     if(e.getKey() == 13){
				                     	searchFaxInfo();
				                  }
					 		}
			 			}
		            },
		            '-','&nbsp;&nbsp;',
		            {
		            	xtype : 'combo',
		            	triggerAction : 'all',
		    			model : 'local',
		    			hiddenId : 'checkItems',
		    			hiddenName : 'checkItems',
		    			name : 'checkItemstext',
		            	store: [
		            			['', '查询全部'], 
		    					['EQL_dno', '配送单号'],
		    					['EQS_flightMainNo','主单号'],
		    					['LIKES_consignee','收货人姓名'],
		    					['LIKES_consigneeTel','收货人电话']
		    				   ],
		    			width : 100,
		    			listeners : {
		    						'select' : function(combo, record, index) {
		    							if (combo.getValue() == '') {
		    								Ext.getCmp("searchContent").disable();
		    								Ext.getCmp("searchContent").show();
		    								Ext.getCmp("searchContent").setValue("");
		    							} else {
		    								Ext.getCmp("searchContent").enable();
		    								Ext.getCmp("searchContent").show();
		    							}
		    							Ext.getCmp("searchContent").focus(true, true);
		    						}
		    					}
		            },
		            '-',
		            {
		            	text:'<B>搜索</B>',
		            	id:'btn', 
		            	iconCls: 'btnSearch',
		            	hidden:false,
		            	handler : searchFaxInfo
		            }],
    				cm : new Ext.grid.ColumnModel([rowNum,sm,
    						{header:'配送单号',dataIndex:'dno'},
    						{header:'主单号',dataIndex:'flightMainNo'},
    						{header:'班次号',dataIndex:'flightNo'},
    						{header:'班次日期',dataIndex:'flightDate',hidden:true},
    						{header:'班次时间',dataIndex:'flightTime',hidden:true,hideable: false},
    						{header:'运输方式',dataIndex:'trafficMode',hidden:true},
    						{header:'代理公司编号',dataIndex:'cusId',hidden:true},
    						{header:'代理公司',dataIndex:'cpName'},
    						{header:'是否专车',dataIndex:'sonderzug',renderer:function(v){
    							if(v==0){
	    								return "否";
	    							}else if(v==1){
	    								return "是";
	    							}
    							}	
    						},
    						{header:'车型',dataIndex:'carType',width:80},
    						{header:'路型',dataIndex:'roadType',width:80},
    						{header:'专车费',dataIndex:'sonderzugPrice',width:80},
    						{header : '分单号',dataIndex:'subNo',width:80}, 
    						{header : '提货方式',dataIndex:'takeMode',width:80},
    						{header : '回单类型',dataIndex:'receiptType',width:80},
    						{header : '收货人姓名',dataIndex:'consignee',width:80},
    						{header : '收货人电话',dataIndex:'consigneeTel',width:80}, 
    						{header : '所在市',dataIndex:'city',width:80},
    						{header : '区/县',dataIndex:'town',width:80},
    						{header : '镇/街道',dataIndex:'street',width:80}, 
    						{header : '地址类型',dataIndex:'areaType',width:80},
    						{header : '收货人地址',dataIndex:'addr',width:80},
    						{header : '配送方式',dataIndex:'distributionMode',width:80},
    						{header : '配送部门',dataIndex:'distributionDepart',width:80},
    						{header : '中转费率',dataIndex:'traFee',width:80},
    						{header : '货物名称',dataIndex:'goods',width:80},
    						{header : '计价方式',dataIndex:'valuationType',width:80},
    						{header : '件数',dataIndex:'piece',width:80},
    						{header : '重量KG',dataIndex:'cqWeight',width:80},
    						{header : '计费重量KG',dataIndex:'cusWeight',width:80},
    						{header : '体积m3',dataIndex:'bulk',width:80},
    						{header : '代收运费',dataIndex:'paymentCollection',width:80},
    						{header : '提送付款方',dataIndex:'whoCash',width:80},
    						{header : '中转费率',dataIndex:'traFeeRate',width:80},
    						{header : '中转费',dataIndex:'traFee',width:80},
    						{header : '预付提送费率',dataIndex:'cpRate',width:80},
    						{header : '预付提送费',dataIndex:'cpFee',width:80},
    						{header : '到付提送费率',dataIndex:'consigneeRate',width:80},
    						{header : '到付提送费',dataIndex:'consigneeFee',width:80},
    						{header : '送货要求',dataIndex:'goods',width:100},
    						{header : '备注',dataIndex:'remark',width:100},
    						{header : '当前部门编号',dataIndex:'curDepartId',hidden:true,hideable: false},
    						{header : '配送部门编号',dataIndex:'distributionDepartId',hidden:true,hideable: false},
    						{header : '配送部门',dataIndex:'distributionDepart',hidden:true,hideable: false},
    						{header : '终端部门编号',dataIndex:'endDepartId',hidden:true,hideable: false},
    						{header : '录单部门编号',dataIndex:'inDepartId',hidden:true,hideable: false}
    				]),
    				ds : faxStore,
			        bbar: new Ext.PagingToolbar({
			            pageSize: pageSize,
			            privilege:privilege, 
			            store: faxStore, 
			            displayInfo: true,
			            displayMsg: '显示第 {0} 条到 {1} 条记录，共 {2} 条', emptyMsg: "没有记录信息显示"
			        })
    });
    detailRecord.on('click', function() {
        var _record = detailRecord.getSelectionModel().getSelections();
        var deletebtn = Ext.getCmp('deletebtn');
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
    //end
});

function searchFaxInfo() {
		faxStore.baseParams = {
			privilege:privilege, 
			limit:pageSize,
			filter_EQL_status:1,
			checkItems : Ext.get("checkItems").dom.value,
			itemsValue : Ext.get("searchContent").dom.value
		}
		var deletebtn = Ext.getCmp('deletebtn');
		deletebtn.setDisabled(true);
		faxStore.reload();
		
}
function faxCancle(ids){
	Ext.Ajax.request({
		url:sysPath+'/fax/oprFaxCancelAction!delete.action',
		params:{
			ids:ids
		},success:function(resp){
			var respText = Ext.util.JSON.decode(resp.responseText);
			Ext.Msg.alert(alertTitle,respText.msg);
			faxStore.reload();
		}
	});
}
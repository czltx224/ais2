var operTitle;
var privilege=152;
var fields=[
			{name:'CONSIGNEE'},
            {name: 'CONSIGNEE_TEL'},
            {name: 'ADDR'},
            {name: 'WEIGHT'},
            {name: 'CONSIGNEEFEE'},
            {name: 'CPFEE'},
            {name:'COSTAMOUNT'},
            {name:'WEIGHTRATE'},
            {name: 'SONDER'},
            {name: 'CITYSEND'},
            {name:'CITYOWN'},
            {name:'CPVALADDFEE'},
            {name:'PROFIT'},
            {name:'PAYCOLL'},
            {name:'PROFITRATE'}
];
Ext.onReady(function() {
    var menuStore = new Ext.data.Store({
        storeId:"menuStore",
        baseParams:{limit:pageSize,cusId:parent.cusId,type:parent.cusType,consignee:parent.cusName},
        proxy: new Ext.data.HttpProxy({url:sysPath+"/fax/oprFaxInAction!findCusInfo.action"}),
        reader: new Ext.data.JsonReader({
            root: 'resultMap', totalProperty: 'totalCount'
        }, fields)
    });
    var cusStore=new Ext.data.Store({
		storeId:"cusStore",
		baseParams:{privilege:61,filter_EQS_custprop:'发货代理'},
		proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/customerAction!ralaList.action"}),
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
        	{name:'id'},
        	{name:'cusName'}
        	])
	});
    //start
    var sm = new Ext.grid.CheckboxSelectionModel({});
    var rowNum = new Ext.grid.RowNumberer({
        header:'序号', width:35, sortable:true
    });
    
    var menuGrid = new Ext.grid.GridPanel({
        renderTo:Ext.getBody(),
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
        sm:sm,
        cm:new Ext.grid.ColumnModel([rowNum,sm,
            {header:'名称',dataIndex:"CONSIGNEE"},
            {header:'电话',dataIndex:"CONSIGNEE_TEL"},
           // {header:'地址',dataIndex:"ADDR"},
            {header:'货量',dataIndex:"WEIGHT",hidden: true, hideable: false},
            {header:'货量百分比',dataIndex:"WEIGHTRATE",width:80},
            {header:"到付总额",dataIndex:"CONSIGNEEFEE",width:80},
            {header:"预付总额",dataIndex:"CPFEE",width:80},
            {header:"成本总额",dataIndex:"COSTAMOUNT",width:80},
            {header:"利润总额",dataIndex:"PROFIT",width:80},
            {header:"利润率",dataIndex:"PROFITRATE",width:80},
            {header:"专车",dataIndex:"SONDER",width:80},
            {header:"市内送货",dataIndex:"CITYSEND",width:80},
            {header:"市内自提",dataIndex:"CITYOWN",width:80},
            {header:"代收总额",dataIndex:"PAYCOLL",width:80}
        ]),
        store:menuStore,
        tbar: [
            {
                text:'<B>收货人转客户</B>', id:'addbtn',disabled:true,tooltip:'将收货人转为客户', iconCls: 'userAdd',handler:function(text,e) {
                	 var _record = menuGrid.getSelectionModel().getSelections();
                	 if(parent.cusType==null){
                	 	var node=new Ext.tree.TreeNode({id:'addConsterList',leaf :false,text:text.getText()});
				      	node.attributes={href1:"/cus/cusRecordAction!input.action?consigName="+_record[0].data.CONSIGNEE+"&consigTel="+_record[0].data.CONSIGNEE_TEL+"&mainType='single'"};
				        parent.toAddTabPage(node,true);
                	 }else{
                	 	parent.conConvertCus(_record[0].data.CONSIGNEE,_record[0].data.CONSIGNEE_TEL);
                	 }
            } },
            '-',
            {
                text:'<B>货量统计</B>',id:'updatebtn', tooltip:'查看货量统计饼图', iconCls: 'userEdit', handler: function() {
               	showChart(parent.cusType);
                
            } } ,'-','&nbsp;&nbsp;',
            {
            	xtype : 'textfield',
    			id:'searchContent',
    			name : 'stationName',
    			width : 100,
    			enableKeyEvents:true,
    			listeners : {
			 		keyup:function(textField, e){
		                     if(e.getKey() == 13){
		                     	searchCusRequest();
		                  }
			 		}
	 			}
            },{
            	xtype:'datefield',
            	name:'startTime',
            	id:'startTime',
            	hidden:true,
            	format:'Y-m-d'
            },{
            	xtype:'datefield',
            	name:'endTime',
            	id:'endTime',
            	hidden:true,
            	format:'Y-m-d'
            },
            '-','&nbsp;',
            {
            	xtype : 'combo',
            	triggerAction : 'all',
    			model : 'local',
    			id : 'checkItems',
    			name : 'checkItemstext',
            	store: [
            			['', '查询全部'], 
    					['LIKES_name', '收货人名称'],
    					['LIKES_tel', '收货人电话'],
    					['LIKES_startTime','时间']
    				   ],
    			width : 100,
    			listeners : {
    						'select' : function(combo, record, index) {
    							if (combo.getValue() == '') {
    								Ext.getCmp("searchContent").disable();
    								Ext.getCmp("searchContent").show();
    								Ext.getCmp("searchContent").setValue("");
    								
    								Ext.getCmp("endTime").disable();
    								Ext.getCmp("endTime").hide();
    								
    								Ext.getCmp("startTime").disable();
    								Ext.getCmp("startTime").hide();
    								//Ext.getCmp("endTime").setValue("");
    							}else if(combo.getValue()=='LIKES_startTime'){
    								Ext.getCmp("searchContent").disable();
    								Ext.getCmp("searchContent").hide();
    								Ext.getCmp("searchContent").setValue("");
    								
    								Ext.getCmp("endTime").enable();
    								Ext.getCmp("endTime").show();
    								
    								Ext.getCmp("startTime").enable();
    								Ext.getCmp("startTime").show();
    							} else {
    								Ext.getCmp("searchContent").enable();
    								Ext.getCmp("searchContent").show();
    								
    								Ext.getCmp("endTime").disable();
    								Ext.getCmp("endTime").hide();
    								
    								Ext.getCmp("startTime").disable();
    								Ext.getCmp("startTime").hide();
    							}
    							Ext.getCmp("searchContent").focus(true, true);
    						}
    					}
            },'-','发货代理:',
            {
            	xtype : 'combo',
            	forceSelection : true,
            	pageSize:pageSize,
            	forceSelection : true,
            	minChars:0,
            	queryParam :'filter_LIKES_cusName',
            	triggerAction :'all',
            	id:'cuscombo',
    			model : 'local',
    			hiddenName : 'cusName',
            	store: cusStore,
            	valueField:'id',
            	displayField:'cusName',
    			width : 120
            },
            '-',
            {
            	text:'<B>搜索</B>',
            	id:'btn', 
            	iconCls: 'btnSearch',
            	hidden:false,
            	handler : searchCusRequest
            }
        ],
        bbar: new Ext.PagingToolbar({
            pageSize: pageSize,
            store: menuStore, 
            displayInfo: true,
            displayMsg: '显示第 {0} 条到 {1} 条记录，共 {2} 条', emptyMsg: "没有记录信息显示"
        }),
        listeners:{
        	'render':function(){
        		if(parent.viewtype=='mainview'){
		    		//Ext.getCmp('btnsearch').setDisabled(true);
		    		Ext.getCmp('cuscombo').setDisabled(true);
		    	}
        	}
        }
    });
    function searchCusRequest() {
    	var searchCon=Ext.getCmp('searchContent').getValue();
		var checkItem=Ext.getCmp("checkItems").getValue();
		var cusId=Ext.getCmp('cuscombo').getValue();
		if(parent.cusId==null){
			Ext.apply(menuStore.baseParams,{
				cusId:cusId,
				type:'发货代理'
			});
		}
		if(checkItem=='LIKES_name'){
			Ext.apply(menuStore.baseParams,{
				cusName:searchCon,
				startTime:'',
				cusTel:'',
				endTime:''
			});
		}
		else if(checkItem=='LIKES_tel'){
			Ext.apply(menuStore.baseParams,{
				cusTel:searchCon,
				startTime:'',
				cusName:'',
				endTime:''
			});
		}else if(checkItem=='LIKES_startTime'){
			Ext.apply(menuStore.baseParams,{
				startTime:Ext.get('startTime').dom.value,
				endTime:Ext.get('endTime').dom.value
			});
		}else{
			Ext.apply(menuStore.baseParams,{
				cusTel:'',
				cusName:'',
				startTime:'',
				endTime:''
			});
		}
		menuStoreReload();
		
	}
   menuGrid.render();
    menuGrid.on('click', function() {
        var _record = menuGrid.getSelectionModel().getSelections();
        var updatebtn = Ext.getCmp('addbtn');
        if (_record.length==1) {       	
            if((updatebtn&&parent.cusType=='发货代理')||(updatebtn&&parent.cusType==null)){
            	updatebtn.setDisabled(false);
            }
        }else if(_record.length>1){
        	 if(updatebtn){
            	updatebtn.setDisabled(true);
            }
        } else {
            if(updatebtn){
            	updatebtn.setDisabled(true);
            }
        }
    });
    function showChart(type){
    	if(menuStore.getCount()<2){
    		Ext.Msg.alert(alertTitle,'数据少于2条，不需要查看！');
    		return false;
    	}else{
	    	var strXml='';
	    	/*
	    	for(var i=0;i<menuStore.getCount();i++){
						var con=menuStore.getAt(i).get('CONSIGNEE');
						var wei=menuStore.getAt(i).get('WEIGHT');
						strXml+="<set name='"+con+"' value='"+wei+"'/>'";
			}*/
	    	for(var i=0;i<menuStore.getCount();i++){
	    		var con=menuStore.getAt(i).get('CONSIGNEE');
				var wei=menuStore.getAt(i).get('WEIGHT');
				strXml+="<set name='"+con+"' value='"+wei+"'/>'";
				if(i>=9){
					break;
				}
	    	}
			var subCap='';
			var xName='';
			if(type=='发货代理'){
				subCap='按收货人统计';
				xName='收货人';
			}else if(type=='收货人'){
				subCap='按发货代理统计';
				xName='发货代理';
			}else{
				subCap='按收货人统计';
				xName='收货人';
			}
			//strXml="<chart animation='1' caption='货量统计' subCaption='"+subCap+"' xAxisName='"+xName+"' yAxisName='货量'  rotateYAxisName='1' showValues='1'  decimalPrecision='0' showNames='1'  baseFontSize='12' outCnvBaseFontSiz='20' numberSuffix=' KG'  pieSliceDepth='30' formatNumberScale='0'>"+strXml+"</chart>";
			strXml="<chart caption='货量TOP10' yAxisName='货量' bgColor='F1F1F1' showValues='0' canvasBorderThickness='1' canvasBorderColor='999999' plotFillAngle='330' plotBorderColor='999999' showAlternateVGridColor='1' divLineAlpha='0' formatNumberScale='0' numberSuffix='kg' baseFontSize='14'>"+strXml+"</chart>";
			var fusionPanel=new Ext.ux.Chart.Fusion.Panel({
							collapsible:false,
							chartCfg:{
								id:'chart1',
								params:{
									flashVars:{
										debugMode:0,
										lang:'EN'
									}
								}
							},
							autoScroll:true,
							id:'chartpanel',
							chartURL:'chars/fcf/Bar2D.swf',//定义图表显示类型，例如：直方，饼图等
							dataXML:strXml,
							width:Ext.lib.Dom.getViewWidth()-40,
							height:300
			});
	    	var win = new Ext.Window({
				title : operTitle,
				width : Ext.lib.Dom.getViewWidth()-20,
				height: 350,
				closeAction : 'hide',
				plain : true,
				modal : true,
				items : fusionPanel,
				buttonAlign : "center",
				buttons : [ {
					text : "关闭",
					handler : function() {
						win.close();
					}
				}]
			});
			win.on('hide', function() {
						fusionPanel.destroy();
					});
			win.show();
	    }
    }
	function menuStoreReload(){
		menuStore.reload({
			params:{
				start : 0,
				limit : pageSize
			}
		});
	}
    //end
});
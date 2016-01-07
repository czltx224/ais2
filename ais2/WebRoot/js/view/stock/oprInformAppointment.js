//通知预约js
var privilege=73;//权限参数
var gridSearchSQLUrl="stock/oprInformAction!getResultMapQuery.action";//通知预约SQL查询地址
var saveUrl="stock/oprInformAction!saveInformAppointment.action";
var dictionaryUrl='sys/dictionaryAction!ralaList.action';//数据字典查询地址
var cusServiceUrl='user/userAction!list.action';//客服员查询地址
var gridRemarkUrl='stock/oprRemarkAction!list.action';//备注记录查询地址
var saveRemarkUrl='stock/oprRemarkAction!save.action';//备注记录保持地址
var gridRequestUrl='stock/oprRequestDoAction!list.action';//个性化要求查询地址 无权限查询
var saveRequestUrl='stock/oprRequestDoAction!save.action';//个性化要求保存地址
var gridInformDetailUrl='stock/oprInformDetailAction!findDetailByDno.action';//通知历史记录查询地址
var gridHistoryUrl='stock/oprHistoryAction!findHistoryByDno.action';//历史交易查询地址
var searchWidth=80;
var defaultWidth=80;  
var pub_panel_width=680;
var pub_panel_height=250;
var pub_rownumber_width=40;
var pubdno;
var pubts;
var putReachStatus=1;
var pub_index;

  		//配送方式4
		distributionModeStore	= new Ext.data.Store({ 
			storeId:"distributionModeStore",
			baseParams:{filter_EQL_basDictionaryId:4,privilege:16},
			proxy:new Ext.data.HttpProxy({url:sysPath+"/"+dictionaryUrl}),
			reader:new Ext.data.JsonReader({
	                    root:'result',
	                    totalProperty:'totalCount'
	        },[
        	{name:'distributionModeId',mapping:'typeCode'},
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
		
		 //通知方式25
		informTypeStore	= new Ext.data.Store({ 
			storeId:"informTypeStore",
			baseParams:{filter_EQL_basDictionaryId:25,privilege:16},
			proxy:new Ext.data.HttpProxy({url:sysPath+"/"+dictionaryUrl}),
			reader:new Ext.data.JsonReader({
	                    root:'result',
	                    totalProperty:'totalCount'
	        },[
        	{name:'informTypeId',mapping:'typeCode'},
        	{name:'informTypeName',mapping:'typeName'}
        	])
		});
		informResultStore	= new Ext.data.SimpleStore({ 
			autoLoad:true,
			data:[[1,'成功'],[0,'失败']],
			fields:['informResultId','informResultName']
		});
		reachStore	= new Ext.data.SimpleStore({ 
			autoLoad:true,
			data:[['','--全部--'],['1','已点到'],['0','未点到']],
			fields:['reachId','reachName']
		});
		 //执行阶段5
		requestStageStore	= new Ext.data.Store({ 
			storeId:"requestStageStore",
			baseParams:{filter_EQL_basDictionaryId:5,privilege:16},
			proxy:new Ext.data.HttpProxy({url:sysPath+"/"+dictionaryUrl}),
			reader:new Ext.data.JsonReader({
	                    root:'result',
	                    totalProperty:'totalCount'
	        },[
        	{name:'requestStageId',mapping:'typeCode'},
        	{name:'requestStageName',mapping:'typeName'}
        	])
		});
		 //要求类型6
		requestTypeStore	= new Ext.data.Store({ 
			storeId:"requestTypeStore",
			baseParams:{filter_EQL_basDictionaryId:6,privilege:16},
			proxy:new Ext.data.HttpProxy({url:sysPath+"/"+dictionaryUrl}),
			reader:new Ext.data.JsonReader({
	                    root:'result',
	                    totalProperty:'totalCount'
	        },[
        	{name:'requestTypeId',mapping:'typeCode'},
        	{name:'requestTypeName',mapping:'typeName'}
        	])
		});
		
		
		 //预约情况 26
		appointmentStore	= new Ext.data.Store({ 
			storeId:"appointmentStore",
			baseParams:{filter_EQL_basDictionaryId:26,privilege:16},
			proxy:new Ext.data.HttpProxy({url:sysPath+"/"+dictionaryUrl}),
			reader:new Ext.data.JsonReader({
	                    root:'result',
	                    totalProperty:'totalCount'
	        },[
        	{name:'appointmentId',mapping:'typeCode'},
        	{name:'appointmentName',mapping:'typeName'}
        	])
		});
		//客服员 cusServiceUrl
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
		printStore=new Ext.data.SimpleStore({
			 autoLoad:true, //此处设置为自动加载
  			  data:[['','全部'],['0','未开单'],['1','已开单']],
   			 fields:["printId","printName"]
		});	

var  fields=[
	{name:'dno',mapping:'DNO'},{name:'cusId',mapping:'CUSID'},
	{name:'cpName',mapping:'CPNAME'},{name:'flightNo',mapping:'FLIGHTNO'},
	{name:'flightDate',mapping:'FLIGHTDATE'},{name:'flightTime',mapping:'FLIGHTTIME'},
	{name:'trafficMode',mapping:'TRAFFICMODE'},{name:'flightMainNo',mapping:'FLIGHTMAINNO'},
	{name:'subNo',mapping:'SUBNO'},{name:'distributionMode',mapping:'DISTRIBUTIONMODE'},
	{name:'takeMode',mapping:'TAKEMODE'},{name:'receiptType',mapping:'RECEIPTTYPE'},
	{name:'consignee',mapping:'CONSIGNEE'},{name:'consigneeTel',mapping:'CONSIGNEETEL'},
	{name:'consigneePho',mapping:'CONSIGNEEPHO'},{name:'city',mapping:'CITY'},
	{name:'town',mapping:'TOWN'},{name:'addr',mapping:'ADDR'},{name:'piece',mapping:'PIECE'},
	{name:'cusWeight',mapping:'CUSWEIGHT'},{name:'bulk',mapping:'BULK'},
	{name:'status',mapping:'STATUS'},{name:'goodsStatus',mapping:'GOODSSTATUS'},
	{name:'cpRate',mapping:'CPRATE'},{name:'cpFee',mapping:'CPFEE'},
	{name:'consigneeRate',mapping:'CONSIGNEERATE'},{name:'consigneeFee',mapping:'CONSIGNEEFEE'},
	{name:'customerService',mapping:'CUSTOMERSERVICE'},{name:'areaType',mapping:'AREATYPE'},
	{name:'paymentCollection',mapping:'PAYMENTCOLLECTION'},{name:'informNum',mapping:'INFORMNUM'},
	{name:'lastInformTime',mapping:'LASTINFORMTIME'},{name:'lastServiceName',mapping:'LASTSERVICENAME'},
	{name:'lastInformCus',mapping:'LASTINFORMCUS'},{name:'lastInformResult',mapping:'LASTINFORMRESULT'},
	{name:'request',mapping:'REQUEST'},{name:'stockPiece',mapping:'STOCKPIECE'},{name:'nodeColor',mapping:'NODECOLOR'},
	{name:'realPiece',mapping:'REALPIECE'},{name:'getSendFee',mapping:'GETSENDFEE'},
	{name:'countFee',mapping:'COUNTFEE'},{name:'reachStatus',mapping:'REACHSTATUS'},
	{name:'ts',mapping:'TS'},{name:'departId',mapping:'DEPARTID'},
	{name:'cusValueAddFee',mapping:'CUSVALUEADDFEE'},{name:'cpValueAddFee',mapping:'CPVALUEADDFEE'},
	{name:'faxRemark',mapping:'FAXREMARK'},'PRINT_NUM',];
     		
	 
	var tbar=[{text:'<b>通知客户</b>',iconCls:'table',tooltip : '通知客户',handler:function() {
            var inform =Ext.getCmp('informCenter');
			var _records = inform.getSelectionModel().getSelections();
	
					if (_records.length < 1) {
						Ext.Msg.alert(alertTitle, "请选择您要操作的行！");
						return false;
					} 
					else if(_records.length > 1){
						Ext.Msg.alert(alertTitle, "您选择的行过多，请选择一行数据进行操作！");
						return false;
					}
					//if(_records[0].data.)
				informappointment(_records);
            } }
	 		,'-',
		{xtype:'textfield',
		  id:'searchContent',
		  width:searchWidth,
		  enableKeyEvents:true,
		  listeners : {
	 		keyup:function(textField, e){
                if(e.getKey() == 13){
                	searchinform();
                }
	 		}
	 	}},'-',{ 
	 	
    					xtype : "combo",
    					width : 100,
    					triggerAction : 'all',
    					id:'searchSelectBox',
    					model : 'local',
    					hiddenId : 'checkItems',
    					hiddenName : 'checkItems',
    					name : 'checkItemstext',
    					store : [['','查询全部'],
    							['f.d_no', '配送单号'],
    							['f.flight_No', '航班号'],
    							['f.flight_Main_No','主单号'],
    							['f.cp_Name','代理公司'],
    							['f.sub_No','分单号'],
    							['f.addr','收货人地址']],
    							
    					emptyText : '选择查询方式',
    					editable : false,
    					forceSelection : true,
    					enableKeyEvents:true,
    					listeners : {
    						'keyup' : function(e) {
   								 if(e.getKey() == 13){
			                     	searchinform();
			                     }
    						}
    					}
    					
    				},
    		'-','收货人:',
    		{xtype:'textfield',id:'searchConsignee',
    			width:searchWidth,
    			enableKeyEvents:true,
    			listeners : {
    				keyup:function(textField, e){
	                     if(e.getKey() == 13){
	                     	searchinform();
	                     }
	 		}
	 	}},'-','收货人电话:',{xtype:'textfield',id:'searchConsigneeTel',width:searchWidth, enableKeyEvents:true,listeners : {
	 			keyup:function(textField, e){
                     if(e.getKey() == 13){
                     	searchinform();
                     }
	 			}
	 		}
	 	},'-','客服员:',{xtype : "combo",
				width : searchWidth,
				typeAhead : true,
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
			    name : 'cusServiceName',
			    id:'searchcusService', 
			    enableKeyEvents:true,listeners : {
					 	'afterRender':{fn:
					 	function(combo){
					 		
					        　　combo.setValue(userId);
					        	combo.setRawValue(userName);
					 	}
					 	
 	
 	
	 		}
	 	}},'-','是否开单:',{ 
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
               		searchinform();
               }
	 		},render:function(combo,e){
	 			combo.setValue('0');
	 		}
	 	}}
	 	];
	 	
	 	var queryTbar=new Ext.Toolbar([
	 	'-','配送方式:',{xtype : "combo",
				width : searchWidth,
				editable:true,
				triggerAction : 'all',
				store : distributionModeStore,
				mode : "remote",// 从本地载值
				valueField : 'distributionModeId',// value值，与fields对应
				displayField : 'distributionModeName',// 显示值，与fields对应
			    name : 'distributionMode',
			    id:'searchdistributionMode', 
			    enableKeyEvents:true,listeners : {
	 		
	 		    keyup:function(textField, e){

                     if(e.getKey() == 13){

                     	searchinform();

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
	 		},render:function(combo,e){
	 			combo.setValue('机场自提');
	 		}
	 	
	 	}},
	 	'-','预约情况:',{ 
	 	xtype : 'combo',
		width:searchWidth, 
		editable : true,
		triggerAction : 'all',
		store : appointmentStore,
		mode:'remote',
		valueField : 'appointmentId',//value值，与fields对应
		displayField : 'appointmentName',//显示值，与fields对应
		name:'appointment',
		id:'searchIsAppointment', 
		enableKeyEvents:true,listeners : {
	 		keyup:function(textField, e){
                if(e.getKey() == 13){
                	searchinform();
                }
	 		}
		}},
		'-','是否点到:',{ 
	 	xtype : 'combo',
		width:searchWidth, 
		editable : false,
		triggerAction : 'all',
		store : reachStore,
		mode:'local',
		valueField : 'reachId',//value值，与fields对应
		displayField : 'reachName',//显示值，与fields对应
		id:'searchIsReach', 
		enableKeyEvents:true,listeners : {
	 		keyup:function(textField, e){
                if(e.getKey() == 13){
                	searchinform();
                }
	 		},render:function(combo,e){
	 			combo.setValue('1');
	 		}
		}},
	 	'-','航班日期：',{
    		xtype : 'datefield',
    		id : 'startDate',
    		format : 'Y-m-d',
    		emptyText : "开始时间",
    		 value:new Date().add(Date.DAY, -7),
    		anchor : '95%',
    		width : 100,
    		editable : true,
    		listeners : {
    			'select' : function() {
    			   var start = Ext.getCmp('startDate').getValue().format("Y-m-d");
    			   Ext.getCmp('endDate').setMinValue(start);
    		     }
    		}
    	}, '&nbsp;到&nbsp;', {
    		xtype : 'datefield',
    		id : 'endDate',
    		format : 'Y-m-d',
    		emptyText : "结束时间",
    		width : 100,
    		editable : true,
    		value:new Date(),
    		anchor : '95%'
    	},'-',{text : '<b>查询</b>',id : 'btn',iconCls : 'btnSearch',
    					handler : searchinform
    				}	
	 	]);	


	 	
Ext.onReady(function() {
   	dateStore = new Ext.data.Store({
        storeId:"dateStore",
        proxy: new Ext.data.HttpProxy({url : sysPath+ "/"+gridSearchSQLUrl}),
        baseParams:{privilege:privilege,limit : pageSize},
 		listeners:{
            'load':function(s,records){   
		        var girdcount=0; 
		        s.each(function(r){ 
		            Ext.getCmp('informCenter').getView().getRow(girdcount).style.backgroundColor=r.get('nodeColor');
		           	girdcount=girdcount+1;  //
		        })
    		}	
        },
        reader: new Ext.data.JsonReader({
            root: 'resultMap', totalProperty: 'totalCount'
        }, fields)
    });
   
   sm = new Ext.grid.CheckboxSelectionModel({singleSelect :true});
   var rowNum = new Ext.grid.RowNumberer({
        header:'序号', width:pub_rownumber_width
    });
    informGrid = new Ext.grid.GridPanel({
        renderTo: Ext.getBody(),
        id:'informCenter',
        height:Ext.lib.Dom.getViewHeight()-1,		
        width:Ext.lib.Dom.getViewWidth()-1,
      
		autoScroll : true,
		//autoSizeColumns: true,
		frame : false,
		loadMask : true,
		stripeRows : true,
        sm:sm,
        cm:new Ext.grid.ColumnModel([rowNum,sm,
        		
       			{header: '配送单号', dataIndex: 'dno',width:defaultWidth,sortable : true},
       			{header: '主单号', dataIndex: 'flightMainNo',width:defaultWidth,sortable : true},
       			{header: '分单号', dataIndex: 'subNo',width:defaultWidth,sortable : true},
       			{header: '货物状态', dataIndex: 'goodsStatus',width:defaultWidth,sortable : true},
       			{header: '是否开单', dataIndex: 'PRINT_NUM',width:defaultWidth,sortable : true
       				,renderer:function renderDescn(v, cellmeta, record, rowIndex, columnIndex, store){
 						if(v>0){
 							cellmeta.css = 'x-grid-back-green';
 							return '已开单';
 						}else{
							return "未开单";
 						}
	        		}
       			},
 				{header: '最后通知结果', dataIndex: 'lastInformResult',width:defaultWidth,sortable : true
 						,renderer:function renderDescn(v, cellmeta, record, rowIndex, columnIndex, store){
							if(null==record.data["lastServiceName"] || record.data["lastServiceName"]=='')
								return '';
							else
							  	return v==1?'成功':(v==0?'失败':'');
 						}
 				},
       			{header: '收货人', dataIndex: 'consignee',width:defaultWidth,sortable : true},
       			{header: '收货人地址', dataIndex: 'addr',width:defaultWidth,sortable : true},
       			{header: '收货人电话', dataIndex: 'consigneeTel',width:defaultWidth,sortable : true},
       			{header: '代理公司ID', dataIndex: 'cusId',width:defaultWidth,sortable : true},
       			{header: '代理公司', dataIndex: 'cpName',width:defaultWidth,sortable : true
       				,renderer:function(v, metadata, record, rowIndex, columnIndex, store){
			    	 return '<a href="#" onclick="openCustomerInfo('+record.get('cusId')+');" >'+v+'</a>';
			    	}
       			},
       			{header: '客服员', dataIndex: 'customerService',width:defaultWidth,sortable : true},
 				{header: '代收', dataIndex: 'paymentCollection',width:defaultWidth,sortable : true},
 				{header: '预付', dataIndex: 'cpFee',width:defaultWidth,sortable : true},
 				{header: '增值服务费', dataIndex: 'cpValueAddFee',width:defaultWidth,sortable : true},
 				{header: '提送费', dataIndex: 'consigneeFee',width:defaultWidth,sortable : true},
 				{header: '合计', dataIndex: 'countFee',width:defaultWidth,sortable : true},
 				{header: '开单件数', dataIndex: 'piece',width:defaultWidth,sortable : true},
 				{header: '重量', dataIndex: 'cusWeight',width:defaultWidth,sortable : true},
 				{header: '已到件数', dataIndex: 'realPiece',width:defaultWidth,sortable : true,hidden:true},
 				{header: '库存', dataIndex: 'stockPiece',width:defaultWidth,sortable : true},
 				{header: '提货方式', dataIndex: 'takeMode',width:defaultWidth,sortable : true},
 				{header: '配送方式', dataIndex: 'distributionMode',width:defaultWidth,sortable : true},
 				{header: '航班号', dataIndex: 'flightNo',width:defaultWidth,sortable : true},
 				{header: '航班日期', dataIndex: 'flightDate',width:defaultWidth,sortable : true},
 				{header: '分单号', dataIndex: 'subNo',width:defaultWidth,sortable : true},
 				{header: '个性化要求', dataIndex: 'request',width:defaultWidth,sortable : true},
 				{header: '运输方式', dataIndex: 'trafficMode',width:defaultWidth,sortable : true},
 				{header: '通知次数', dataIndex: 'informNum',width:defaultWidth,sortable : true},
 				{header: '最后通知客服员', dataIndex: 'lastServiceName',width:defaultWidth,sortable : true},
 				{header: '最后通知客户', dataIndex: 'lastInformCus',width:defaultWidth,sortable : true},
 				{header: '最后通知时间', dataIndex: 'lastInformTime',width:defaultWidth,sortable : true},
 				{header: '时间戳', dataIndex: 'ts',width:defaultWidth,hidden:true}
        ]),
        store:dateStore,
        tbar: tbar,
        listeners: {
          	render: function(){
              	queryTbar.render(this.tbar);
          	},rowclick:function(grid,index,e){
          		pub_index = index;
          		//var v_dno = grid.store.getAt(index).get("dno");
          		//alert(v_dno);
          	}
      	},
        bbar: new Ext.PagingToolbar({
            pageSize: pageSize, 
            privilege:privilege,
            store: dateStore, 
            displayInfo: true,
            displayMsg: '显示第 {0} 条到 {1} 条记录，共 {2} 条', 
            emptyMsg: "没有记录信息显示"
        })
    });
   // searchinform();
    
    if(null!=informDno && informDno.length>0){
    	dateStore.reload({
			params : {
				start : 0,
				privilege:privilege,
				filter_EQL_dno:informDno,
				limit : pageSize
			}
		});
    }
  });
    
    
	
   function searchinform() {
		dateStore.baseParams ={
			privilege:privilege,
			limit : pageSize
		};
				var searchdistributionMode = Ext.getCmp('searchdistributionMode').getRawValue();
				var searchtakeMode = Ext.getCmp('searchtakeMode').getRawValue();
				//var searchisNotArrive=Ext.getCmp('searchisNotArrive').getValue();
				//putReachStatus=searchisNotArrive;
				var searchIsAppointment=Ext.getCmp('searchIsAppointment').getValue();
				var searchConsignee = Ext.getCmp('searchConsignee').getValue();
				//var searchAddr = Ext.getCmp('searchAddr').getValue();
				var searchcusService=Ext.getCmp('searchcusService').getRawValue();
				var searchConsigneeTel = Ext.getCmp('searchConsigneeTel').getValue();
				
				var searchIsReach = Ext.getCmp('searchIsReach').getValue();
				
				var checkItems =  Ext.get("checkItems").dom.value;
				var searchContent =  Ext.get("searchContent").dom.value;
				var searchPrint = Ext.getCmp('searchPrint').getValue();
				if(checkItems=="f.d_no" && searchContent.length>1){
					 Ext.apply(dateStore.baseParams, {
					 	checkItems : checkItems,
						itemsValue : searchContent
					 });
				}else{
		 			 Ext.apply(dateStore.baseParams, {
		 			 		filter_reachStatus:searchIsReach,
		 			 		filter_printNum:searchPrint,
		 			 		filter_EQS_notifyStatus:searchIsAppointment,
		 			 		//filter_EQS_reachStatus:putReachStatus,
		 			 		filter_EQS_customerService:searchcusService,
		 			 		filter_GED_flightDate:(!Ext.getCmp("startDate").hidden?(Ext.get("startDate").dom.value=="开始时间"?"":Ext.get("startDate").dom.value):""),
	   						filter_LED_flightDate : (!Ext.getCmp("endDate").hidden?(Ext.get("endDate").dom.value=="结束时间"?"":Ext.get("endDate").dom.value):""),
		 			 		filter_EQS_distributionMode:searchdistributionMode,
		 			 		filter_EQS_takeMode:searchtakeMode,
		 			 		filter_LIKES_consignee:searchConsignee,
		 			 		filter_LIKES_consigneeTel:searchConsigneeTel,
							checkItems : checkItems,
							itemsValue : searchContent
	    			});
    			}
	 			
		dataReload();
	}
	var historyDoFlag=true;
	var historyInformFlag=true;
	var requestFlag=true;
	var remarkFlag=true;
function informappointment(_records) {
				historyDoFlag=true;
				historyInformFlag=true;
				requestFlag=true;
				remarkFlag=true;
				var dno=_records[0].data.dno;
				pubts=_records[0].data.ts;
				pubdno=dno;
				newRemarkGrid();
				newrequestGrid();
				newhistoryInformGrid();
				newhistoryDoGrid();
					var form = new Ext.form.FormPanel({
								labelAlign : 'left',
								id:'informForm',
								frame : true,
								//closable :true,
								//width : 700,
								url:sysPath+'/'+gridSearchSQLUrl,
								baseParams:{
				    				filter_EQL_dno:pubdno,
									privilege:privilege,
									limit : pageSize},
								bodyStyle : 'padding:0 0 0',
								reader : new Ext.data.JsonReader({
						            root: 'resultMap', totalProperty: 'totalCount'
						        }, fields),
								labelAlign : "right",
									items : [{
											layout : 'column',
											items : [{
														columnWidth : .33,
														layout : 'form',
														items : [{
																	name : "id",
																	xtype : "hidden"
																},{
																	name : "ts",
																	xtype : "hidden"
																},{
																	xtype : 'textfield',
																	labelAlign : 'left',
																	readOnly  :true,
																	fieldLabel : '配送单号',
																	name : 'dno',
																	anchor : '95%'
															       },{
																	xtype : 'textfield',
																	labelAlign : 'left',
																	readOnly  :true,
																	fieldLabel : '分单号',
																	name : 'subNo',
																	anchor : '95%'
															       },{
																	xtype : 'textfield',
																	labelAlign : 'left',
																	fieldLabel : '发货代理',
																	name : 'cpName',
																	readOnly  :true,
																	anchor : '95%'
															       },{
																	xtype : 'textfield',
																	labelAlign : 'left',
																	fieldLabel : '配送方式',
																	readOnly  :true,
																	name : 'distributionMode',
																	anchor : '95%'
															       },{
																	xtype : 'textfield',
																	labelAlign : 'left',
																	readOnly  :true,
																	fieldLabel : '提货方式',
																	name : 'takeMode',
																	anchor : '95%'
															       }]

													}, {
														columnWidth : .33,
														layout : 'form',
														items : [{
																	xtype : 'textfield',
																	labelAlign : 'left',
																	readOnly  :true,
																	fieldLabel : '件数',
																	name : 'piece',
																	anchor : '95%'
															       },{
																	xtype : 'textfield',
																	labelAlign : 'left',
																	readOnly  :true,
																	fieldLabel : '重量',
																	name : 'cusWeight',
																	anchor : '95%'
															       },{
																	xtype : 'textfield',
																	labelAlign : 'left',
																	readOnly  :true,
																	fieldLabel : '预付费',
																	name : 'cpFee',
																	anchor : '95%'
															       },{
																	xtype : 'textfield',
																	labelAlign : 'left',
																	readOnly  :true,
																	fieldLabel : '航班号',
																	name : 'flightNo',
																	anchor : '95%'
															       },{
																	xtype : 'textfield',
																	labelAlign : 'left',
																	readOnly  :true,
																	fieldLabel : '主单号',
																	name : 'flightMainNo',
																	anchor : '95%'
															       }]
													},{
														columnWidth : .33,
														layout : 'form',
														items : [{
																	xtype : 'textfield',
																	labelAlign : 'left',
																	readOnly  :true,
																	fieldLabel : '收货人',
																	name : 'consignee',
																	anchor : '95%'
															       },{
																	xtype : 'textfield',
																	labelAlign : 'left',
																	readOnly  :true,
																	fieldLabel : '收货人电话',
																	name : 'consigneeTel',
																	anchor : '95%'
															       },{
																	xtype : 'textfield',
																	labelAlign : 'left',
																	readOnly  :true,
																	fieldLabel : '代收货款',
																	name : 'paymentCollection',
																	anchor : '95%'
															       },{
																	xtype : 'textfield',
																	labelAlign : 'left',
																	readOnly  :true,
																	fieldLabel : '增值服务费',
																	name : 'cusValueAddFee',
																	anchor : '95%'
															       },{
																	xtype : 'textfield',
																	labelAlign : 'left',
																	readOnly  :true,
																	fieldLabel : '提送费',
																	name : 'consigneeFee',
																	anchor : '95%'
															       }
																]
													}]
													
													
										},{
											layout:'column',
											labelAlign : "right",
												items:[{
														layout:'form',
												 		columnWidth:.66,
												 		items:[
												 			  { xtype : 'textfield',
																labelAlign : 'right',
																readOnly  :true,
																fieldLabel : '收货人地址',
																name : 'addr',
																anchor : '100%'
														       }
												 		]
												 },{
														layout:'form',
												 		columnWidth:.33,
												 		items:[
												 			  { xtype : 'textfield',
																labelAlign : 'right',
																readOnly  :true,
																fieldLabel : '合计',
																name : 'countFee',
																anchor : '95%'
														       }
												 		]
												 }]
										},{
											layout:'column',
											labelAlign : "right",
												items:[{
														layout:'form',
												 		columnWidth:.95,
												 		items:[
												 			  { xtype : 'textfield',
																labelAlign : 'right',
																readOnly  :true,
																fieldLabel : '最新备注',
																name : 'faxRemark',
																anchor : '100%'
														       }
												 		]
												 }]
										},new Ext.TabPanel({
											    activeTab: 0,
											    id:'informTabPanel',
											    height : pub_panel_height-38,
	    										width:pub_panel_width-38,
											    enableTabScroll:true,  
											    layoutOnTabChange:true,   
											    listeners:{
											     	tabchange:function(tab,e){
												    	if(tab.activeTab.id=="remarkGrid" && remarkFlag){
												    		remarkFlag=false;
															remarkStoreReLoad();
												    	}else if(tab.activeTab.id=="requestGrid" && requestFlag){
												    		requestFlag=false;
												    		requestStoreReLoad();
												    	}else if(tab.activeTab.id=="historyInformGrid" && historyInformFlag){
												    		historyInformFlag=false;
												    		historyInformStoreReload();
												    	}else if(tab.activeTab.id=="historyDoGrid" && historyDoFlag){
												    		historyDoFlag=false;
												    		historyDoStoreReload();
												    	}
												    },render:function(tab,e){
												    	 choseTabPanel(tab);
												    }
											    },
											    items:[
											      requestGrid, remarkGrid,historyDoGrid,historyInformGrid
											    ]
										})],
										
										tbar:mytbar
										});
									
		carTitle='通知客户';
		form.load({
			waitMsg : '正在载入数据...',
   			success : function(_form, action) {
	   			//Ext.getCmp('mycombo').setRawValue(_records[0].data.departName);
	   			
   			},
   			failure : function(_form, action) {
   				Ext.MessageBox.alert(alertTitle, '载入失败');
   			}
		});
		
		 wind = new Ext.Window({
				title : carTitle,
    			width:pub_panel_width,
				height:485,
				autoScroll:true,
				plain : true,
				closeAction:'hide', 
		        modal:true,  
				items : form
					
					});
		wind.on('hide', function() {
				form.destroy();
			});
		wind.show();
		
 }
 var mytbar=[{
text : "保存信息",
	iconCls : 'groupSave',
			handler : function() {
					var myinformType=Ext.getCmp('myinformType').getRawValue();
					if(myinformType.length<1){
						Ext.Msg.alert(alertTitle,"请选择通知类型!");
						return;
					}
					var myinformResult=Ext.getCmp('myinformResult').getValue();

					if(myinformResult.length==0){
						Ext.Msg.alert(alertTitle,"请选择通知结果!");
						return;
					}
							Ext.Ajax.request({
								url : sysPath+'/'
										+ saveUrl,
								params :{privilege:privilege,dno:pubdno,informResult:myinformResult,informType:myinformType,ts:pubts},
								success : function(resp) {
									var respText = Ext.util.JSON.decode(resp.responseText);
									
									if(respText.success){
										Ext.Msg.alert(alertTitle,"通知成功!");
										//wind.hide();
										dataReload();
									}else{
										Ext.Msg.alert(alertTitle,respText.msg);
									}
								}
							});
						
					}
				},  {
							text : "关闭",
							iconCls:'groupClose',
							handler : function() {
								wind.hide();
							}
						},'-','通知方式：<font style="color:red;">*</font>', {
						    xtype : 'combo',
							width:searchWidth, 
							editable : false,
							triggerAction : 'all',
							allowBlank : false,
							blankText : "通知方式不能为空！",
							store : informTypeStore,
							mode:'remote',
							id:'myinformType',
							valueField : 'informTypeId',//value值，与fields对应
							displayField : 'informTypeName',//显示值，与fields对应
							name:'informType',
							listeners:{
								render: function(combo) {   
						       　　combo.setValue('电话');
								}
							}
						},'-','通知结果：<font style="color:red;">*</font>',{
							xtype:'combo',
							width:searchWidth,
							editable:false,
							triggerAction : 'all',
							allowBlank : false,
							blankText : "通知结果不能为空！",
							store:informResultStore,
							mode:'local',
							id:'myinformResult',
							valueField:'informResultId',
							displayField:'informResultName',
							hiddenName:'informResult',
							listeners:{
								render: function(combo) {   
						       　　combo.setValue('1');
								}
							}
						},{
							xtype:'button',
							text : "更改申请",
							handler : function() {
								var node=new Ext.tree.TreeNode({id:'inform_'+pubdno,leaf :false,text:"更改提送方式申请"});
						      	node.attributes={href1:'fax/oprChangeAction!input.action?dno='+pubdno};
						        parent.toAddTabPage(node,true);
							}
						},{
							xtype:'button',
							text : "上一票",
							iconCls:'sort_up',
							handler : function() {
								if(pub_index>0){
									pub_index--;
          							var v_dno = informGrid.store.getAt(pub_index).get("dno");
          							searchMoveDno(v_dno);
								}else{
									Ext.Msg.alert(alertTitle, "这已经是第一票！");
								}
							}
						},{
							xtype:'button',
							text : "下一票",
							iconCls:'sort_down',
							//sort_down
							handler : function() {
								if(pub_index<informGrid.store.getCount()-1){
									pub_index++;
	          						var v_dno = informGrid.store.getAt(pub_index).get("dno");
	          						searchMoveDno(v_dno);
	          					}else{
									Ext.Msg.alert(alertTitle, "这已经是最后一票！");
								}
							}
						}
		          
		          ];
 var remarkStore= new Ext.data.Store({
 					 id:'remarkStore',
	    			 baseParams:{limit : pageSize,privilege:75},
	    			 proxy:new Ext.data.HttpProxy({url:sysPath+"/"+gridRemarkUrl}),
	    			sortInfo :{field: "createTime", direction: "DESC"},
	    			 listeners:{'beforeload': function(){
	    			 	 newRemarkGrid();
						Ext.apply(Ext.getCmp("remarkGrid").store.baseParams, { filter_EQL_dno: pubdno });
						}
						}, 
	    			 reader:new Ext.data.JsonReader({
		            	root: 'result', totalProperty: 'totalCount'
		       	 	}, ['id','remark','createName','createTime','updateName','updateTime','dno','ts'])
    			});
var requestStore=new Ext.data.Store({
	    			 id:'requestStore',
	    			 baseParams:{limit : pageSize,privilege:56},
	    			 sortInfo :{field: "createTime", direction: "DESC"},
	    			 proxy:new Ext.data.HttpProxy({url:sysPath+"/"+gridRequestUrl}),
	    			  listeners:{'beforeload': function()
						{
							newrequestGrid();
						Ext.apply(Ext.getCmp("requestGrid").store.baseParams, { filter_EQL_dno: pubdno });
						}}, 
	    			 reader:new Ext.data.JsonReader({
		            	root: 'result', totalProperty: 'totalCount'
		       	 	}, ['id','dno','requestStage','request','oprMan','isOpr','requestType','createName','remark','createTime','updateName','updateTime','ts','isException'])
    			});
function newRemarkGrid(){
 var rwidth=80;
 remarkGrid = new Ext.grid.GridPanel({
					id:'remarkGrid',
    				height : pub_panel_height,
    				width:pub_panel_width,
    				//width:600,
    				title:'备注记录',
    				autoScroll : true,
    				//autoSizeColumns: true,
    				frame : false,
    				loadMask : true,
    				stripeRows : true,
    				viewConfig:{
    				  forceFit: true
    				},
    				cm : new Ext.grid.ColumnModel([new Ext.grid.RowNumberer({
								header:'序号', width:pub_rownumber_width
							}),
    						{header : '备注内容',dataIndex:'remark',sortable : true,width:rwidth}, 
    						{header : '配送单号',dataIndex : 'dno',sortable : true,width:rwidth},
    						{header : '创建人',dataIndex : 'createName',sortable : true,width:rwidth}, 
    						{header : '创建时间',dataIndex : 'createTime',sortable : true,width:rwidth}
    				]),
    				tbar:[{text:'<b>添加备注</b>',iconCls:'userAdd',tooltip : '添加备注',handler:function() {
							 addRemark(null);
			            }
			        }],
    				bbar: new Ext.PagingToolbar({
			            pageSize: pageSize, 
			            store: remarkStore, 
			            displayInfo: true,
			            displayMsg: '显示第 {0} 条到 {1} 条记录，共 {2} 条', 
			            emptyMsg: "没有记录信息显示"
			        }),
    				ds :remarkStore
    			});
}
var requestRecord;
function newrequestGrid(){
var rsm=new Ext.grid.CheckboxSelectionModel();
 requestGrid = new Ext.grid.GridPanel({
    				id : 'requestGrid',
    				height : pub_panel_height,
    				width:pub_panel_width,
    				title:'个性化要求',
    				autoScroll : true,
    				autoSizeColumns: true,
    				frame : false,
    				loadMask : true,
    				stripeRows : true,
    				viewConfig:{
    				   forceFit: true
    				},
    				cm : new Ext.grid.ColumnModel([new Ext.grid.RowNumberer({
								header:'序号', width:pub_rownumber_width
							}),rsm, 
    						{header : '配送单号',dataIndex : 'dno',sortable : true},
    						{header : '执行阶段',dataIndex : 'requestStage',sortable : true},
    						{header : '执行要求',dataIndex : 'request',sortable : true},
    						{header : '执行人',dataIndex : 'oprMan',sortable : true},
    						{header : '是否执行',dataIndex : 'isOpr',sortable : true,
    							renderer:function(v){
 									return v==1?'执行':'未执行';
			        		}
    						
    						},
    						{header : '类型',dataIndex : 'requestType',sortable : true},
    						{header : '备注',dataIndex : 'remark',sortable : true}
    				]),
    				 sm: rsm,
    				tbar:[{text:'<b>添加个性化要求</b>',iconCls:'userAdd',tooltip : '添加个性化要求',handler:function() {
						saveRequest(null);
			        }},{text:'<b>修改个性化要求</b>',iconCls:'userEdit',tooltip : '修改个性化要求',handler:function() {
						
						if (null==requestRecord) {
							top.Ext.Msg.alert(alertTitle, "请选择您要修改的行！");
						}else{
							var isOpr=requestRecord.data.isOpr;
							var requestStage=requestRecord.data.requestStage;
							
							if(isOpr==1){
								top.Ext.Msg.alert(alertTitle, "该个性化要求已经被执行，不能修改！");
								return;
							}
							if(requestStage!='送货' && requestStage!='出库'){
								Ext.Msg.alert(alertTitle, "该个性化要求不允许修改！");
								return false;
							}
							saveRequest(requestRecord);
						}
			        }}],
    				bbar: new Ext.PagingToolbar({
			            pageSize: pageSize, 
			            store: requestStore, 
			            displayInfo: true,
			            displayMsg: '显示第 {0} 条到 {1} 条记录，共 {2} 条', 
			            emptyMsg: "没有记录信息显示"
			        }),
    				ds :requestStore 
	});

	requestGrid.on('rowdblclick',function(grid,index,e){
		var record = grid.getSelectionModel().getSelected();
				
				if (record!=null) {
					saveRequest(record);
				}
			 	
		});
		
		requestGrid.on('rowclick', function(grid,index,e) {
	      	requestRecord = grid.getSelectionModel().getSelected();
	    });
}

var historyInformStore=new Ext.data.Store({
	    			 id:'historyInformStore',
	    			 baseParams:{limit : pageSize,privilege:77},
	    			 sortInfo :{field: "CREATETIME", direction: "DESC"},
	    			 proxy:new Ext.data.HttpProxy({url:sysPath+"/"+gridInformDetailUrl}),
	    			 listeners:{'beforeload': function()
						{
							newhistoryInformGrid();
							Ext.apply(historyInformGrid.store.baseParams, {filter_EQL_dno: pubdno });
						}}, 
	    			 reader:new Ext.data.JsonReader({
		            	root: 'resultMap', totalProperty: 'totalCount'
		       	 	}, ['ID','INFORMID','SERVICENAME','INFORMTIME','CUSREQUEST','CUSOPTIONS','INFORMTYPE','INFORMRESULT','CUSNAME','CUSADDR',
		       	 	'CUSTEL','CUSMOBILE','INPAYMENTCOLLECTION','CPFEE','DELIVERYFEE','REMARK','CREATENAME','CREATETIME','UPDATENAME','UPDATETIME','TS'])
    			});
 function newhistoryInformGrid(){
 historyInformGrid = new Ext.grid.GridPanel({
    				height : pub_panel_height,
    				width:pub_panel_width,
    				title:'历史通知记录',
    				id:'historyInformGrid',
    				autoScroll : true,
    				//autoSizeColumns: true,
    				frame : false,
    				loadMask : true,
    				stripeRows : true,
    				
    				cm : new Ext.grid.ColumnModel([new Ext.grid.RowNumberer({
								header:'序号', width:pub_rownumber_width
							}),
    						{header : '通知预约ID',dataIndex : 'INFORMID',sortable : true},
    						{header : '客服员',dataIndex : 'SERVICENAME',sortable : true},
    						{header : '通知时间',dataIndex : 'INFORMTIME',sortable : true},
    						{header : '客户要求',dataIndex : 'CUSREQUEST',sortable : true},
    						
    						{header : '客户意见',dataIndex : 'CUSOPTIONS',sortable : true},
    						
    						{header : '通知结果',dataIndex : 'INFORMRESULT',sortable : true,
    							renderer:function(v){
 									return v==1?'成功':'失败';
			        			}
    						},
    						{header : '通知方式',dataIndex : 'INFORMTYPE',sortable : true},
    						{header : '客户姓名',dataIndex : 'CUSNAME',sortable : true},
    						{header : '客户电话',dataIndex : 'CUSTEL',sortable : true},
    						{header : '客户手机',dataIndex : 'CUSMOBILE',sortable : true},
    						{header : '客户地址',dataIndex : 'CUSADDR',sortable : true},
    						{header : '代收',dataIndex : 'INPAYMENTCOLLECTION',sortable : true},
    						{header : '预付',dataIndex : 'CPFEE',sortable : true},
    						{header : '提送费',dataIndex : 'DELIVERYFEE',sortable : true},
    						{header : '备注',dataIndex : 'REMARK',sortable : true}
    						
    				]),
    				//tbar:[{text:'<b>历史通知记录</b>',iconCls:'userAdd',tooltip : '历史通知记录',handler:function() {
					//	alert('留着备用');
			       // }}],
    				bbar: new Ext.PagingToolbar({
			            pageSize: pageSize, 
			            store: historyInformStore, 
			            displayInfo: true,
			            displayMsg: '显示第 {0} 条到 {1} 条记录，共 {2} 条', 
			            emptyMsg: "没有记录信息显示"
			        }),
    				ds :historyInformStore
		});
 }

 //var gridHistoryUrl
var historyDoStore=new Ext.data.Store({
	    			 id:'historyDoStore',
	    			 baseParams:{limit : pageSize,privilege:78},
	    			 proxy:new Ext.data.HttpProxy({url:sysPath+"/"+gridHistoryUrl}),
	    			 listeners:{'beforeload': function()
						{
							newhistoryDoGrid();
						Ext.apply(Ext.getCmp("historyDoGrid").store.baseParams, { filter_EQL_dno: pubdno });
						}}, 
	    			 reader:new Ext.data.JsonReader({
	    			   root: 'resultMap', totalProperty: 'totalCount'
		       	 	}, ['ID','OPRNAME','OPRNODE','OPRCOMMENT','OPRTIME','OPRMAN','OPRDEPART','DNO','OPRTYPE'])
    			});
function newhistoryDoGrid(){
 historyDoGrid = new Ext.grid.GridPanel({
    				id : 'historyDoGrid',
    				height : pub_panel_height,
    				width:pub_panel_width,
    				title:'历史操作记录',
    				autoScroll : true,
    				//autoSizeColumns: true,
    				frame : false,
    				loadMask : true,
    				stripeRows : true,
    				cm : new Ext.grid.ColumnModel([new Ext.grid.RowNumberer({
								header:'序号', width:pub_rownumber_width
							}),
    						{header : '操作ID',dataIndex : 'ID',sortable : true,hidden:true},
    						{header : '操作节点名称',dataIndex : 'OPRNAME',sortable : true},
    						{header : '操作节点',dataIndex : 'OPRNODE',sortable : true,hidden:true},
    						{header : '操作内容',dataIndex : 'OPRCOMMENT',sortable : true,width:200},
    						{header : '操作时间',dataIndex : 'OPRTIME',sortable : true},
    						{header : '操作者',dataIndex : 'OPRMAN',sortable : true},
    						{header : '操作部门',dataIndex : 'OPRDEPART',sortable : true},
    						{header : '配送单号',dataIndex : 'DNO',sortable : true},
    						{header : '节点类型',dataIndex : 'OPRTYPE',sortable : true,hidden:true}
    				]),
    				//tbar:[{text:'<b>操作历史通知记录</b>',iconCls:'userAdd',tooltip : '操作历史通知记录',handler:function() {
					//	alert('留着备用');
			        // }}],
    				bbar: new Ext.PagingToolbar({
			            pageSize: pageSize, 
			           store: historyDoStore, 
			            displayInfo: true,
			            displayMsg: '显示第 {0} 条到 {1} 条记录，共 {2} 条', 
			            emptyMsg: "没有记录信息显示"
			        }),
    				ds :historyDoStore 
		});
}
 function historyDoStoreReload(){
 	historyDoStore.reload(
 	{	
			params : {
				start : 0,
				filter_EQL_dno:pubdno,
				privilege:78,
				limit : pageSize
			}
 	}
 	)
 }
 function remarkStoreReLoad(){
 	remarkStore.reload({
 			sortInfo :{field: "createTime", direction: "DESC"},
			params : {
				start : 0,
				filter_EQL_dno:pubdno,
				privilege:75,
				limit : pageSize
			}
		});
 }
 
 function historyInformStoreReload(){
 	historyInformStore.reload({
 			sortInfo :{field: "CREATETIME", direction: "DESC"},
			params : {
				start : 0,
				filter_EQL_dno:pubdno,
				limit : pageSize
			}
		});
 }
 function addRemark(_records){
 	if(_records!=null)
					var did=_records[0].data.id;
					var form = new Ext.form.FormPanel({
								labelAlign : 'left',
								frame : true,
								url:sysPath+'/'+gridRemarkUrl,
								baseParams:{
				    				filter_EQL_id:did,
				    				privilege:75},
								bodyStyle : 'padding:5px 5px 0',
								width : 400,
								reader : new Ext.data.JsonReader({
			            root: 'result', totalProperty: 'totalCount'
			        }, ['id','remark','createName','createTime','updateName','updateTime','dno','ts']),
					labelAlign : "right",
				
						items : [{
										layout : 'form',
										items : [{
													name : "id",
													xtype : "hidden"
												},{
													name : "ts",
													xtype : "hidden"
												},{
													xtype : 'hidden',
													name : 'dno',
													value:pubdno
												},
												 {
													xtype : 'textarea',
													labelAlign : 'left',
													fieldLabel : '备注内容<font style="color:red;">*</font>',
													name : 'remark',
													height:40,
													maxLength:200,
													allowBlank : false,
													blankText : "备注内容不能为空！",
													anchor : '95%'
												}]
													
										}]
							});
		
		storeAreaTitle='添加备注信息';
		if(did!=null){
			storeAreaTitle='修改备注信息';
			
			form.load({
						waitMsg : '正在载入数据...',
		    			success : function(_form, action) {
		    				
		    			},
		    			failure : function(_form, action) {
		    				Ext.MessageBox.alert(alertTitle, '载入失败');
		    			}
					});
		
		}

		var win = new Ext.Window({
		title : storeAreaTitle,
		width : 400,
		closeAction : 'hide',
		plain : true,
		modal : true,
		items : form,
		buttonAlign : "center",
		buttons : [{
			text : "保存",
			iconCls : 'groupSave',
			handler : function() {
				if (form.getForm().isValid()) {
					this.disabled = true;
					
					form.getForm().submit({
						url : sysPath
								+ '/'+saveRemarkUrl,
								params:{privilege:75,limit : pageSize},
						waitMsg : '正在保存数据...',
						success : function(form, action) {
							win.hide(), Ext.Msg.alert(alertTitle,
									action.result.msg, function() {
										remarkStoreReLoad();
									});
						},
						failure : function(form, action) {
							if (action.faliureType == Ext.form.Action.SERVER_INVALID) {
								Ext.Msg.alert(alertTitle, "服务器连接异常，请联系管理员！");
							} else {
								if (action.result.msg) {
									win.hide();
									Ext.Msg.alert(alertTitle,
											action.result.msg, function() {
												remarkStoreReLoad();
											});
		
								}
							}
						}
					});
				}
			}
		}, {
		
			text : "重置",
			iconCls : 'refresh',
			handler : function() {
				if(did==null){
					form.getForm().reset();
				}
				if(did!=null){
					storeAreaTitle='修改备注信息';
					form.load({
						waitMsg : '正在载入数据...',
		    			success : function(_form, action) {
				   			
		    			},
		    			failure : function(_form, action) {
		    				Ext.MessageBox.alert(alertTitle, '载入失败');
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
  function requestStoreReLoad(){
 	
 	requestStore.reload({
 			sortInfo :{field: "createTime", direction: "DESC"},
			params : {
				start : 0,
				privilege:76,
				filter_EQL_dno:pubdno,
				limit : pageSize
			}
		});
 }
 function saveRequest(record){
 	
// 	//判断是否可以添加
// 	Ext.Ajax.request({
//			url : sysPath + '/'
//			+ gridRequestUrl,
//			params:{filter_EQL_dno:Number(pubdno)},
//			success : function(resp) {
//			var jdata = Ext.util.JSON.decode(resp.responseText);
//			for(var i=0;i<jdata.result.length;i++){
//				if(jdata.result[i].requestStage=='通知阶段'){
//					Ext.Msg.alert(alertTitle, "该个性化要求已经存在，请修改！");
//					return ;
//				}else if(jdata.result[i].isOpr==1){
//					Ext.Msg.alert(alertTitle, "该个性化要求已经执行，不能修改！");
//					return ;
//				}
//			}
			
			
		requestStageStore.load();
		requestTypeStore.load();
 		if(record!=null)
					var did=record.data.id;
					var form = new Ext.form.FormPanel({
								labelAlign : 'left',
								frame : true,
								url:sysPath+'/'+gridRequestUrl,
								baseParams:{
				    				filter_EQL_id:did,privilege:76},
								bodyStyle : 'padding:5px 5px 0',
								width : 400,
								reader : new Ext.data.JsonReader({
			            root: 'result', totalProperty: 'totalCount'
			        }, ['id','dno','requestStage','request','oprMan','isOpr','requestType','createName','remark','createTime','updateName','updateTime','ts','isException']),
					labelAlign : "right",
				
						items : [{
										layout : 'form',
										items : [{
													name : "id",
													xtype : "hidden"
												},{
													name : "ts",
													xtype : "hidden"
												},{
													xtype : 'hidden',
													name : 'dno',
													value:pubdno
												},{
													xtype : 'hidden',
													name : 'isOpr',
													value:'0'
												},{
													xtype : 'hidden',
													name : 'oprMan'
												},{
													xtype : 'hidden',
													name : 'isException'
												},
												 {
													xtype : 'textfield',
													labelAlign : 'left',
													fieldLabel : '执行要求<font style="color:red;">*</font>',
													name : 'request',
													allowBlank : false,
													maxLength:50,
													anchor : '95%'
												},{
													xtype : 'combo',
													triggerAction : 'all',
													typeAhead : true,
													queryParam : 'filter_LIKES_typeName',
													store : requestStageStore,
													pageSize : comboSize,
													resizable:true,
													forceSelection : true,
													fieldLabel : '执行阶段<font style="color:red;">*</font>',
													editable : false,
													minChars : 0,
													valueField : 'requestStageId',//value值，与fields对应
													displayField : 'requestStageName',//显示值，与fields对应
													name:'requestStage',
													id:'trequestStage',
													anchor : '95%'
												},
												{
													xtype : 'combo',
													triggerAction : 'all',
													typeAhead : true,
													queryParam : 'filter_LIKES_typeName',
													store : requestTypeStore,
													pageSize : comboSize,
													resizable:true,
													forceSelection : true,
													fieldLabel : '要求类型<font style="color:red;">*</font>',
													editable : false,
													minChars : 0,
													valueField : 'requestTypeId',//value值，与fields对应
													displayField : 'requestTypeName',//显示值，与fields对应
													name:'requestType',
													anchor : '95%'
												},{
													xtype : 'textarea',
													labelAlign : 'left',
													fieldLabel : '备注',
													name : 'remark',
													height:40,
													maxLength:200,
													anchor : '95%'
												}]
													
										}]
							});
		
		storeAreaTitle='添加个性化要求信息';
		if(did!=null){
			storeAreaTitle='修改个性化要求信息';
			
			form.load({
						waitMsg : '正在载入数据...',
		    			success : function(_form, action) {
		    				
		    			},
		    			failure : function(_form, action) {
		    				Ext.MessageBox.alert(alertTitle, '载入失败');
		    			}
					});
		
		}

		var win = new Ext.Window({
		title : storeAreaTitle,
		width : 400,
		closeAction : 'hide',
		plain : true,
		modal : true,
		items : form,
		buttonAlign : "center",
		buttons : [{
			text : "保存",
			iconCls : 'groupSave',
			handler : function() {
				
				if (form.getForm().isValid()) {
					this.disabled = true;
					
					form.getForm().submit({
						url : sysPath
								+ '/'+saveRequestUrl,
								params:{privilege:56,limit : pageSize},
						waitMsg : '正在保存数据...',
						success : function(form, action) {
							win.hide(), Ext.Msg.alert(alertTitle,
									action.result.msg, function() {
										requestStoreReLoad();
									});
						},
						failure : function(form, action) {
							if (action.faliureType == Ext.form.Action.SERVER_INVALID) {
								Ext.Msg.alert(alertTitle, "服务器连接异常，请联系管理员！");
							} else {
								if (action.result.msg) {
									win.hide();
									Ext.Msg.alert(alertTitle,
											action.result.msg, function() {
												requestStoreReLoad();
											});
		
								}
							}
						}
				});
				}
			}
		}, {
		
			text : "重置",
			iconCls : 'refresh',
			handler : function() {
				if(did==null){
					form.getForm().reset();
				}
				if(did!=null){
					storeAreaTitle='修改个性化要求信息';
					form.load({
						waitMsg : '正在载入数据...',
		    			success : function(_form, action) {
				   			
		    			},
		    			failure : function(_form, action) {
		    				Ext.MessageBox.alert(alertTitle, '载入失败');
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
//			}
//		});
 		
 	
 }
 
 function panduan(_records){
 		
 		if(_records){
 			return true;
 		}
		Ext.Ajax.request({
			url : sysPath + '/'
			+ gridRequestUrl,
			params:{filter_EQL_dno:Number(pubdno)},
			success : function(resp) {
			var jdata = Ext.util.JSON.decode(resp.responseText);
			for(var i=0;i<jdata.result.length;i++){
				if(jdata.result[i].requestStage!='送货' && jdata.result[i].requestStage!='出库'){
					Ext.Msg.alert(alertTitle, "该个性化要求不允许修改！");
					return false;
				}
			}
			return true;
			}
		})
 }

 function dataReload(){
		dateStore.reload({
			params : {
				start : 0,
				privilege:privilege,
				limit : pageSize
			}
		});
		
 }
 
 //显示代理信息
	function openCustomerInfo(cusId){
					var  fields=[{name:"id",mapping:'id'},
						{name:"cusCode",mapping:'cusCode'},
			     		{name:"cusName",mapping:'cusName'},
			     		{name:'cusAdd',mapping:'cusAdd'},
			     		{name:'custprop',mapping:'custprop'},
			     		{name:'custshortname',mapping:'custshortname'},
			     		{name:'email',mapping:'email'},
			     		{name:'engname',mapping:'engname'},
			     		{name:'fax1',mapping:'fax1'},
			     		{name:'fax2',mapping:'fax2'},
			     		{name:'legalbody',mapping:'legalbody'},
			     		{name:'linkman1',mapping:'linkman1'},
			     		{name:'linkman2',mapping:'linkman2'},
			     		{name:'linkman3',mapping:'linkman3'},
			     		{name:'memo',mapping:'memo'},
			     		{name:'phone1',mapping:'phone1'},
			     		{name:'phone2',mapping:'phone2'},
			     		{name:'phone3',mapping:'phone3'},
			     		{name:'pkAreacl',mapping:'pkAreacl'},
			     		{name:'pkCubasdoc1',mapping:'pkCubasdoc1'},
			     		{name:'trade',mapping:'trade'},
			     		{name:'url',mapping:'url'},
			     		{name:'createName',mapping:'createName'},
			     		{name:'createTime',mapping:'createTime'},
			     		{name:'updateName',mapping:'updateName'},
			     		{name:'updateTime',mapping:'updateTime'},
			     		{name:'bankNumber',mapping:'bankNumber'},
			     		{name:'settlement',mapping:'settlement'},
			     		{name:'createBank',mapping:'createBank'},
			     		{name:'isProjectcustomer',mapping:'isProjectcustomer'},
			     		{name:'ts',mapping:'ts'}];
     		
						var form = new Ext.form.FormPanel({
								labelAlign : 'right',
								frame : true,
								bodyStyle : 'padding:5px 5px 0',
								width : 650,
								labelWidth: 70,
								reader : new Ext.data.JsonReader({root: 'result', totalProperty: 'totalCount'}, fields),
						items : [{
											layout : 'column',
											labelAlign : 'right',
											items : [{
														columnWidth : .33,
														layout : 'form',
														
														items : [{
																	xtype : 'hidden',
																	fieldLabel : 'id',
																	name : 'id'
																},{
																	name : "ts",
																	xtype : "hidden"
																},{
																	xtype : 'textfield',
																	fieldLabel : '客商编码',
																	name : 'cusCode',
																	readOnly:true,
																	anchor : '95%'
																},{
																	xtype : 'textfield',
																	fieldLabel : '公司编码',
																	name : 'pkCubasdoc1',
																	readOnly:true,
																	anchor : '95%'
																},{
																	xtype : 'textfield',
																	fieldLabel : '所属行业',
																	name : 'trade',
																	readOnly:true,
																	anchor : '95%'
																},{
																	xtype : 'textfield',
																	labelAlign : 'left',
																	fieldLabel : '银行帐号',
																	name : 'bankNumber',
																	readOnly:true,
																	anchor : '95%'
																},{
																	xtype : 'textfield',
																	fieldLabel : '联系人1',
																	name : 'linkman1',
																	readOnly:true,
																	anchor : '95%'
																},{
																	xtype : 'textfield',
																	labelAlign : 'left',
																	fieldLabel : '电话1',
																	name : 'phone1',
																	readOnly:true,
																	anchor : '95%'
																},{
																	xtype : 'textfield',
																	fieldLabel : '传真1',
																	name : 'fax1',
																	readOnly:true,
																	anchor : '95%'
																},{
																	xtype : 'textfield',
																	fieldLabel : '外文名称',
																	name : 'engname',
																	readOnly:true,
																	anchor : '95%'
																}]

													},{
														columnWidth : .33,
														layout : 'form',
														items : [{
																	xtype : 'textfield',
																	fieldLabel : '客商名称',
																	name : 'cusName',
																	readOnly:true,
																	anchor : '95%'
																},{
									                           		xtype : 'textfield',
									    							name : 'custprop',
									    							fieldLabel : '客商类型',
									    							readOnly:true,
									    							anchor : '95%'
									                           }/*,{
																	xtype : 'textfield',
																	fieldLabel : '客商类型',
																	name : 'custprop',
																	maxLength:10,
																	allowBlank : false,
																	blankText : "客商类型不能为空！",
																	anchor : '95%'
																}*/,{
																	xtype : 'textfield',
																	fieldLabel : '法人',
																	name : 'legalbody',
																	readOnly:true,
																	anchor : '95%'
																},{
																	xtype : 'textfield',
																	fieldLabel : '开户行',
																	name : 'createBank',
																	readOnly:true,
																	anchor : '95%'
																},{
																	xtype : 'textfield',
																	fieldLabel : '联系人2',
																	name : 'linkman2',
																	readOnly:true,
																	anchor : '95%'
																},{
																	xtype : 'textfield',
																	labelAlign : 'left',
																	fieldLabel : '电话2',
																	name : 'phone2',
																	readOnly:true,
																	anchor : '95%'
																},{
																	xtype : 'textfield',
																	fieldLabel : '传真2',
																	name : 'fax2',
																	readOnly:true,
																	anchor : '95%'
																}, {
																	xtype : 'textfield',
																	readOnly:true,
																	id:'isProjectcustomer',
																	fieldLabel : '项目客户',
																	name : 'isProjectcustomer',
																	anchor : '95%'
																}]

													},{
														columnWidth : .33,
														layout : 'form',
														items : [{
																	xtype : 'textfield',
																	fieldLabel : '客商地址',
																	name : 'cusAdd',
																	readOnly:true,
																	anchor : '95%'
																},{
																	xtype : 'textfield',
																	fieldLabel : '客商简称',
																	name : 'custshortname',
																	readOnly:true,
																	anchor : '95%'
																},{
																	xtype : 'textfield',
																	fieldLabel : 'E-mail',
																	name : 'email',
																	readOnly:true,
																	anchor : '95%'
																},{
																	xtype : 'textfield',
																	fieldLabel : '地区分类',
																	name : 'pkAreacl',
																	readOnly:true,
																	anchor : '95%'
																},{
																	xtype : 'textfield',
																	fieldLabel : '联系人3',
																	name : 'linkman3',
																	readOnly:true,
																	anchor : '95%'
																},{
																	xtype : 'textfield',
																	labelAlign : 'left',
																	fieldLabel : '电话3',
																	name : 'phone3',
																	readOnly:true,
																	anchor : '95%'
																},{
																	xtype : 'textfield',
																	fieldLabel : 'web网址',
																	name : 'url',
																	readOnly:true,
																	anchor : '95%'
																},{
																	xtype : 'textfield',
																	fieldLabel : '结算方式',
																	name : 'settlement',
																	readOnly:true,
																	anchor : '95%'
																}]

													}]
									},{
											xtype : 'textarea',
											fieldLabel : '备注',
											name : 'memo',
											readOnly:true,
											height:70,
											anchor : '95%'
										}]
									});
									
			form.load({
				url : sysPath+ "/sys/customerAction!list.action",
				params:{filter_EQL_id:cusId,privilege:61},
				success : function(resp) {
					var isp =Ext.getCmp('isProjectcustomer');
					if(isp.getValue()==1){
						isp.setValue('是');
					}else{
						isp.setValue('否');
					}
				}
			});
		
		var win = new Ext.Window({
		title : '查看客商信息',
		width : 650,
		closeAction : 'hide',
		plain : true,
		resizable : false,
		modal : true,
		items : form,
		buttonAlign : "center",	buttons : [{
				text : "重置",
				iconCls : 'refresh',
				handler : function() {
						form.load({
							url : sysPath+ "/sys/customerAction!list.action",
							params:{filter_EQL_id:cusId,privilege:privilege},
							success : function(resp) {
								var isp =Ext.getCmp('isProjectcustomer');
								if(isp.getValue()==1){
									isp.setValue('是');
								}else{
									isp.setValue('否');
								}
							}
						});							
				}
		}, {
				text : "关闭",
				iconCls:'groupClose',
				handler : function() {
					win.close();
				}
			}]});
		win.on('hide', function() {
					form.destroy();
				});
		win.show();
	}
	
	function searchMoveDno(v_dno){
		pubdno = v_dno;
		var informForm = Ext.getCmp('informForm').getForm();
		informForm.baseParams={
			filter_EQL_dno:pubdno,
			privilege:privilege,
			limit : pageSize
   		};
   		//重新加载form表单
   		informForm.load();
   		
   		//重置tab判断值
   		remarkFlag=true;
   		requestFlag=true;
   		historyInformFlag=true;
   		historyDoFlag=true;
   		
   		//默认选中个性化要求执行面板
   		var tab = Ext.getCmp('informTabPanel');
   		choseTabPanel(tab);
   		
	}
	
	function choseTabPanel(tab){
   		tab.setActiveTab('requestGrid');
   		requestStoreReLoad();
    	Ext.Ajax.request({
			url : sysPath+'/'+ gridRequestUrl,
			params : {
				start : 0,
				filter_EQL_dno:pubdno,
				limit : pageSize
			},
			success : function(resp) {
				var respText = Ext.util.JSON.decode(resp.responseText);
				var result = respText.result;
				//如果没有个性化要求则选择备注面板
				if(result.length==0){
					tab.setActiveTab('remarkGrid');
					remarkStoreReLoad();
				}
			}
		});
	}

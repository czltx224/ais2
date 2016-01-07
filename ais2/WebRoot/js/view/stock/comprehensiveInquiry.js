//综合查询JS
Ext.QuickTips.init();
var privilege=68;
var gridRemarkUrl='stock/oprRemarkAction!list.action';
var saveUrl="stock/oprInformAction!saveInformAppointment.action";
var saveRequestUrl='stock/oprRequestDoAction!save.action';
var dictionaryUrl='sys/dictionaryAction!ralaList.action';
var gridRequestUrl='stock/oprRequestDoAction!list.action';
var saveRemarkUrl='stock/oprRemarkAction!save.action';
var saveRemarkSUrl='stock/oprRemarkAction!saveRemarks.action';
var updateFlightUrl='fax/oprFaxInAction!updateFlight.action';
var gridHistoryUrl='stock/oprHistoryAction!findHistoryByDno.action';
var gridExceptionUrl='exception/oprExceptionAction!getAll.action';   //120
var gridInformDetailUrl='stock/oprInformDetailAction!findDetailByDno.action';//通知历史记录查询地址
var ralaListUrl="stock/oprValueAddFeeAction!list.action";
var form;

Ext.onReady(function() {
	/*
	Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
	*/
	Ext.QuickTips.init();
	var tplWidth = Ext.lib.Dom.getViewWidth()-60;
	var domWidth=Ext.lib.Dom.getViewWidth()-15;
	var comWidth=domWidth/4;
	var scomWidth=domWidth/6;
	var commentWidth=110;
    var domHeight=Ext.lib.Dom.getViewHeight();
	
	var fields=[{name:'dno',mapping:'dno'},'cpname','goodsstatus',
	'flightno','flightdate','curdepart','piece','realgowhere',
	'subno','consigneetel','goods','cusweight','cashName',  // 收银员
    'cashTime','doStoreName','cpSonderzugprice','fiTraAudit',
	'doStoreStatus','doStoreTime','airportstartstatus','collectMsg',
    'consigneefee','cusvalueaddfee','cpvalueaddfee','traPayStatus',
    'gowhere','dismode','createtime','takemode','isOprException',
    'customerservice','receipttype','urgentservice','traPayTime',
    'curstatus','stockpiece','stockweight','sonderzugprice','sonderzug',
    'enddepart','curdepart','indepart','createtime','noticeGoodStatus',
    'stockcreatetime','nodeorder','cusadd','trafee','trafficmode',
    'customerName','confirmtime','confirmname','flightmainno','cusId', //代理ID 
	'gowhereId',  //供应商ID
    'sumitDate','signsource','status','notifystatus','reachName','dnoIncomeFee',
    'confirmstatus','cashstatus','fisurestatus','cqweight','goodStatusColor',
    'indepartid','dogooddate','reachstatus','cpfee','returnStatus','cashMoney',
    'getstatus','outstatus','scanstatus','realSignMan','serviceDepartName','isUrgentStatus',
    'printnum','distributiondepartid','remark','airportendtime','distributiondepartname',
    'airportstarttime','carNoNum','bulk','phoneNoticeSign','isException',
    'oreachstatus','waitnotice','osuoutstatus','discreatetime','osuoutstatustime','request',
    'cusdepartname','oicreatename','paymentcollection','consigneeInfoString','signStatus','isFlyLate'];
	
	//作废状态
	var delStatusStore=new Ext.data.SimpleStore({
			 auteLoad:true, //此处设置为自动加载
  			 data:[['0','已作废'],['1','未作废']],
   			 fields:["delStatus","delStatusName"]
	});

	//是否录签收0:未签收,1 已签收
	var signStatusStore=new Ext.data.SimpleStore({
			 auteLoad:true, //此处设置为自动加载
  			 data:[['0','未签收'],['1','已签收']],
   			 fields:["signStatus","signStatusName"]
	});
	
	//是否专车
	var oneCarStore=new Ext.data.SimpleStore({
			 auteLoad:true, //此处设置为自动加载
  			 data:[['0','否'],['1','是']],
   			 fields:["sonderzug","sonderzugName"]
	});
	
	//是否出库
	var isOvermemoStore=new Ext.data.SimpleStore({
			 auteLoad:true, //此处设置为自动加载
  			 data:[['0','未出库'],['1','已出库'],['2','分批出库']],
   			 fields:["overmemo","overmemoName"]
	});
	
	/*是否加急
	var isUrgentStatusStore=new Ext.data.SimpleStore({
			 auteLoad:true, //此处设置为自动加载
  			 data:[['0','正常'],['1','加急']],
   			 fields:["isUrgentStatus","isUrgentStatusName"]
	});*/
	
	//是否通知预约
	var isNoticeStatusStore=new Ext.data.SimpleStore({
			 auteLoad:true, //此处设置为自动加载
  			 data:[['0','否'],['1','是']],
   			 fields:["isNoticeStatus","isNoticeStatusName"]
	});	
	
	//是否等通知放货
	var noticeGoodStatusStore=new Ext.data.SimpleStore({
			 auteLoad:true, //此处设置为自动加载
  			 data:[['0','否'],['1','是']],
   			 fields:["noticeGoodStatus","noticeGoodStatusName"]
	});
	
	var goodsnewStore2= new Ext.data.Store({
			storeId:"goodsnewStore2",
			proxy:new Ext.data.HttpProxy({url:sysPath+"/stock/oprNodeAction!getList.action"}),
			reader:new Ext.data.JsonReader({
	                    root:'resultMap',
	                    totalProperty:'totalCount'
	        },
	        [{name:'goodsStatus',mapping:'ID'},
        	{name:'goodsStatusName',mapping:'NODENAME'}]),
        	sortInfo:{field:'goodsStatus',direction:'ASC'}
	});
	
	
	//签收单状态
	var signDanStatusStore=new Ext.data.SimpleStore({
			 auteLoad:true, //此处设置为自动加载
  			 data:[['1','已点到'],['0','未点到'],['2','已出库'],['3','未出库'],
  			 	   ['4','已确收'],['5','未确收'],['6','已寄出'],['7','未寄出'],
  			 	   ['8','已收描'],['9','未扫描'],['10','已打印'],['11','未打印']
  			 		],
   			 fields:["signDanStatus","signDanStatusName"]
	});
	
	//返货状态
	var returnStatusStore=new Ext.data.SimpleStore({
			 auteLoad:true, //此处设置为自动加载
  			 data:[['0','正常'],['1','整票返货'],['2','部分返货'],['3','拆零返货']],
   			 fields:["id","name"]
	});
	
	//运输方式
	var trafficmodeStatusStore=new Ext.data.SimpleStore({
			 auteLoad:true, //此处设置为自动加载
  			 data:[['0','汽运'],['1','空运']],
   			 fields:["id","name"]
	});
	
	//
	var doMoneyStatusStore=new Ext.data.SimpleStore({
			 auteLoad:true, //此处设置为自动加载
  			 data:[['0','未收银'],['1','收银确认'],['2','财务确收']],
   			 fields:["doMoneyStatus","doMoneyStatusName"]
	});
	
	var bussStore = new Ext.data.Store({ 
            storeId:"bussStore",                        
            proxy: new Ext.data.HttpProxy({url:sysPath+"/sys/departAction!findDepart.action",method:'post'}),
            baseParams:{
               privilege:53,
               filter_EQL_isBussinessDepa:1
            },
            reader: new Ext.data.JsonReader({
                        root: 'resultMap', totalProperty:'totalCount'
                    },[ {name:'departName', mapping:'DEPARTNAME'},    
                        {name:'departId', mapping: 'DEPARTID'}
            ]),                                      
            sortInfo:{field:'departId',direction:'DESC'}
     });

	//是否异常状态
	var isErrorStatusStore=new Ext.data.SimpleStore({
			 auteLoad:true, //此处设置为自动加载
  			 data:[['0','异常'],['1','到货异常']],
   			 fields:["isErrorStatus","isErrorStatusName"]
	});

	//是否点到
	var isDoToStatusStore=new Ext.data.SimpleStore({
			 auteLoad:true, //此处设置为自动加载
  			 data:[['0','否'],['1','是']],
   			 fields:["isDoToStatus","isDoToStatusName"]
	});

		//是否已审核中转成本
	var traPayStatusStore=new Ext.data.SimpleStore({
			 auteLoad:true, //此处设置为自动加载
  			 data:[['0','未审核'],['1','已审核']],
   			 fields:["id","name"]
	});
	//是否开单
	var isDoDanStatusStore=new Ext.data.SimpleStore({
			 auteLoad:true, //此处设置为自动加载
  			 data:[['0','否'],['1','是']],
   			 fields:["isDoDanStatus","isDoDanStatusName"]
	});

	/*是否回单确收确认
	var isHuiShouStatusStore=new Ext.data.SimpleStore({
			 auteLoad:true, //此处设置为自动加载
  			 data:[['0','否'],['1','是']],
   			 fields:["isHuiShouStatus","isHuiShouStatusName"]
	});*/
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


	//配送方式
	var distributionStore = new Ext.data.Store({ 
            storeId:"distributionStore",                        
            proxy: new Ext.data.HttpProxy({url:sysPath+"/"+dictionaryUrl,method:'post'}),
            baseParams:{
             	privilege:16,
				filter_EQL_basDictionaryId:4
            },
            reader: new Ext.data.JsonReader({
            root: 'result', totalProperty: 'totalCount'
           }, [  {name:'typeName', mapping:'typeName',type:'string'}           
              ])    
    });
	distributionStore.load();
	
	//客服部门
	var serviceDepartStore = new Ext.data.Store({ 
            storeId:"serviceDepartStore",                        
            proxy: new Ext.data.HttpProxy({url:sysPath+"/sys/departAction!findDepart.action",method:'post'}),
            baseParams:{
               filter_EQL_isCusDepart:1
            },
            reader: new Ext.data.JsonReader({
            root: 'resultMap', totalProperty:'totalCount'
           }, [  
                 {name:'departName', mapping:'DEPARTNAME'},    
                 {name:'departId', mapping: 'DEPARTNO'}
              ]),                                    
            sortInfo:{field:'departId',direction:'ASC'}
     });
	
	//权限部门
	var rightDepartStore2 = new Ext.data.Store({ 
            storeId:"rightDepartStore2",                        
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
	
	//提货方式
	var doGoodsStore = new Ext.data.Store({ 
            storeId:"doGoodsStore",                        
            proxy: new Ext.data.HttpProxy({url:sysPath+"/"+dictionaryUrl,method:'post'}),
            baseParams:{
             	privilege:16,
				filter_EQL_basDictionaryId:14
            },
            reader: new Ext.data.JsonReader({
            root: 'result', totalProperty: 'totalCount'
           }, [  {name:'typeName', mapping:'typeName',type:'string'}           
              ])    
    });
	doGoodsStore.load();
	
	//查询条件
	var queryConditionStore=new Ext.data.SimpleStore({
			 auteLoad:true, //此处设置为自动加载
  			 data:[['doDanPeople','录单员'],  //['doMoneyPeople','收银员'],
  			 	   ['kanDanPeople','开单员'],  //['StockFee','仓储费'],
  			 	   ['areaCounty','区县'],  //['doGoodsMondyFee','预付费'],
  			 	   ['GoodsName','货物名称'],['GoodsPiece','件数'],
  			 	   ['GoodsWeight','重量'],['doGoodsFee','代收货款']
  			 ],
   			 fields:["queryCondition","queryConditionName"]
	});

    var expander = new Ext.ux.grid.RowExpander({
    	height:280,
        tpl : '<div id="dno_{dno}" style="height:280px;width:1126px" ></div>',
        listeners : {
			'expand' : function(record, body, rowIndex){
		 		createTablePanel(body.get("dno"));
			 }
		}
    });

function createTablePanel(foo){
			//更改记录字段
  var fieldsFax=[{name:'id',mapping:'T1_ID'},
				{name:'isSystem',mapping:'T1_IS_SYSTEM'},
				{name:'createName',mapping:'T1_CREATE_NAME'},
				{name:'createTime',mapping:'T1_CREATE_TIME'},
				{name:'updateName',mapping:'T1_UPDATE_NAME'},
				{name:'updateTime',mapping:'T1_UPDATE_TIME'},
				{name:'remark',mapping:'T1_REMARK'},
				{name:'status',mapping:'T1_STATUS'},
				{name:'dno',mapping:'T1_D_NO'},
				{name:'departId',mapping:'T1_DEPART_ID'},
				{name:'changeField',mapping:'T0_CHANGE_FIELD'},
				{name:'changeFieldZh',mapping:'T0_CHANGE_FIELD_ZH'},
				{name:'changePre',mapping:'T0_CHANGE_PRE'},
				{name:'changePost',mapping:'T0_CHANGE_POST'}];
	
	//增值服务费字段
	var fields=[
		'id','status',
		'dno',  //配送单号
		'feeName',  //客商
		'feeCount', 'payType',
		'createTime',  //
		'createName',   //
		'updateTime',  //
		'updateName',   //
		'ts'];
        var jsonread= new Ext.data.JsonReader({root:'result',totalProperty:'totalCount'},fields);
        var jsonreadFax= new Ext.data.JsonReader({root:'resultMap',totalProperty:'totalCount'},fieldsFax);
	
	//通知预约信息
	var historyInformStore=new Ext.data.Store({
   			 id:'historyInformStore',
   			 baseParams:{
   			 	limit : pageSize,
   			 	filter_EQL_dno: foo,
   			 	privilege:77
   			 
   			 },
   			 sortInfo :{field: "CREATETIME", direction: "DESC"},
   			 proxy:new Ext.data.HttpProxy({url:sysPath+"/"+gridInformDetailUrl}),
   			 listeners:{'beforeload': function(){
					Ext.apply(historyInformGrid.store.baseParams, { filter_EQL_dno: foo });
					 
				}}, 
   			 reader:new Ext.data.JsonReader({
            	root: 'resultMap', totalProperty: 'totalCount'
       	 	}, ['ID','INFORMID','SERVICENAME','INFORMTIME','CUSREQUEST','CUSOPTIONS','INFORMTYPE','INFORMRESULT','CUSNAME','CUSADDR',
       	 	'CUSTEL','CUSMOBILE','INPAYMENTCOLLECTION','CPFEE','DELIVERYFEE','REMARK','CREATENAME','CREATETIME','UPDATENAME','UPDATETIME','TS'])
  	});
	
	//增值服务费明细
	var dataStore = new Ext.data.Store({
           proxy: new Ext.data.HttpProxy({url:sysPath+"/"+ralaListUrl}),
           baseParams:{
           	limit: pageSize,
           	filter_EQL_dno:foo
           },
           reader:jsonread,
           sortInfo:{field:'id',direction:'DESC'}
	});
	
	//更改记录
	var faxChangeStore = new Ext.data.Store({
           proxy: new Ext.data.HttpProxy({url:sysPath+"/fax/oprChangeAction!selectQuery.action"}),
           baseParams:{
	           	limit: pageSize,
	           	dno:foo
           },
           reader:jsonreadFax,
           sortInfo:{field:'createTime',direction:'ASC'}
	});
	
	//更改记录表格
	var faxChangeGrid=new Ext.grid.EditorGridPanel({
		id:'faxChangeGrid'+foo,
		frame : false,
		border : true,
		title:'更改记录',
 		width:tplWidth-120,
		viewConfig : {
			scrollOffset: 0,
			autoScroll:true,
			columnsText : "显示的列",
			sortAscText : "升序",
			sortDescText : "降序"
		},
		frame : false,
		loadMask : true,
		stripeRows : true,
		columns:[ new Ext.grid.RowNumberer({
								header:'序号', width:20
							}),
       			{header: '编号', dataIndex: 'id',width:55,sortable : true},
		        {header: '配送单号 ', dataIndex: 'dno',width:80,sortable : true},
		        {header: '客商', dataIndex: 'isSystem',width:90,sortable : true},
		        {header: '更改字段', dataIndex: 'changeField',width:80,sortable : true},
		        {header: '字段名称', dataIndex: 'changeFieldZh',width:80,sortable : true},
		        {header: '更改前值', dataIndex: 'changePre',width:60,sortable : true},　
		        {header: '更改后值', dataIndex: 'changePost',width:60,sortable : true},
		        {header: '创建人', dataIndex: 'createName',width:80,sortable : true},
		        {header: '创建时间', dataIndex: 'createTime',width:80,sortable : true,
		        		renderer:function(v){
		        			 return v.substr(0,10) ;
		        		}
		        },
		        {header: '修改人', dataIndex: 'updateName',width:80,sortable : true},
		        {header: '修改时间', dataIndex: 'updateTime',width:80,sortable : true},
				{header: '状态', dataIndex: 'status',width:90,sortable : true,renderer:function(v){
		        					if(v=='1'){
		        						return '未审核';
		        					}else if(v=='2'){
		        						return '已审核';
		        					}else if(v=='3'){
		        						return '审核不通过';
		        					}else if(v=='4'){
		        						return '已知会';
		        					}else{
		        						 return '';
		        					}}},
		        {header: '备注', dataIndex: 'remark',width:120,sortable : true},
		        {header: '创建部门ID', dataIndex: 'departId',width:80,hidden:true,sortable : true}],
		store : faxChangeStore,
		bbar : new Ext.PagingToolbar({
				pageSize : pageSize, 
				store : faxChangeStore,
				displayInfo : true,
				displayMsg : '显示第 {0} 条到 {1} 条记录，共 {2} 条',
				emptyMsg : "没有记录信息显示"
		})
	});
	
	//备注
	var remarkStore= new Ext.data.Store({
					 storeId:'remarkStore'+foo,
	    			 baseParams:{limit : pageSize,filter_EQL_dno:foo,privilege:247},
	    			 proxy:new Ext.data.HttpProxy({url:sysPath+"/"+gridRemarkUrl}),
	    			 sortInfo :{field: "createTime", direction: "DESC"},
	    			 reader:new Ext.data.JsonReader({
	    			 
		            	root: 'result', totalProperty: 'totalCount'
		       	 	}, ['id','remark','createName','createTime','updateName','updateTime','dno','ts'])
    			});
	
	//备注表格
	var remarkGrid = new Ext.grid.GridPanel({
					id:'remarkGrid'+foo,
    				width:tplWidth,
    				region : 'center',
					heigth:280,
    				title:'备注记录',
    				autoScroll : true,
    				border : true,
    				//autoSizeColumns: true,
    				frame : false,
    				enableHdMenu:true,
    				loadMask : true,
    				trackMouseOver:true,
    				stripeRows : true,
    				viewConfig:{
    				  autoScroll : true,
    				  forceFit: true
    				},
    				cm : new Ext.grid.ColumnModel([new Ext.grid.RowNumberer({
								header:'序号', width:20
							}),
    						{header : '备注内容',dataIndex:'remark',sortable : true,width:80}, 
    						{header : '配送单号',dataIndex : 'dno',sortable : true,width:80},
    						{header : '创建人',dataIndex : 'createName',sortable : true,width:80}, 
    						{header : '创建时间',dataIndex : 'createTime',sortable : true,width:80}
    				]),
    				tbar:[{text:'<b>添加备注</b>',height:20,iconCls:'userAdd',tooltip : '添加备注',handler:function() {
							addRemark(null,foo);
			            }
			        }],
    				bbar: new Ext.PagingToolbar({
			            pageSize: pageSize, 
			            store: remarkStore, 
			            displayInfo: true,
			            displayMsg: '显示第 {0} 条到 {1} 条记录，共 {2} 条', 
			            emptyMsg: "没有记录信息显示"
			        }),ds :remarkStore
    			});
	
	//增值服务费明细
	var recordGrid=new Ext.grid.EditorGridPanel({
		id:'recordGrid'+foo,
		frame : false,
		border : true,
		title:'增值服务费明细',
 		width:tplWidth-120,
		viewConfig : {
			scrollOffset: 0,
			autoScroll:true,
			columnsText : "显示的列",
			sortAscText : "升序",
			sortDescText : "降序"
		},
		frame : false,
		loadMask : true,
		stripeRows : true,
		columns:[ new Ext.grid.RowNumberer({
								header:'序号', width:35
							}),
       			{header: '编号', dataIndex: 'id',width:55,sortable : true},
		        {header: '配送单号 ', dataIndex: 'dno',width:80,sortable : true},
		        {header: '增值服务费名称', dataIndex: 'feeName',width:120,sortable : true},
		        {header: '金额', dataIndex: 'feeCount',width:100,sortable : true},
		        {header: '付款方式', dataIndex: 'payType',width:70,sortable : true},
		         {header: '更改状态', dataIndex: 'status',width:70,sortable : true,renderer:function(v){
			        					if(v=='1') return '已更改';
			        					if(v=='0') return '未更改';
			        					}},
		        {header: '创建时间', dataIndex: 'createTime',width:120,sortable : true},
		        {header: '修改人', dataIndex: 'updateName',width:80,sortable : true},
		        {header: '修改时间', dataIndex: 'updateTime',width:120,hidden:true,sortable : true},
		        {header: '时间戳', dataIndex: 'ts',width:55,hidden:true,sortable : true}
		    ],
		store : dataStore,
		bbar : new Ext.PagingToolbar({
				pageSize : pageSize, 
				store : dataStore,
				displayInfo : true,
				displayMsg : '显示第 {0} 条到 {1} 条记录，共 {2} 条',
				emptyMsg : "没有记录信息显示"
		})
	});
	
			//历史通知记录
		 historyInformGrid = new Ext.grid.GridPanel({
   				height : 190,
   				title:'通知预约记录',
   				id:'historyInformGrid'+foo,
   				autoScroll : true,
   				//autoSizeColumns: true,
   				frame : false,
				border : true,
   				loadMask : true,
   				stripeRows : true,
   				width:tplWidth-120,
   				cm : new Ext.grid.ColumnModel([new Ext.grid.RowNumberer({
							header:'序号', width:20
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

		var requestStore=new Ext.data.Store({
					 storeId:'requestStore'+foo,
	    			 baseParams:{limit : pageSize,filter_EQL_dno:foo,privilege:76},
	    			 sortInfo :{field: "createTime", direction: "DESC"},
	    			 proxy:new Ext.data.HttpProxy({url:sysPath+"/"+gridRequestUrl}),
	    			 reader:new Ext.data.JsonReader({
		            	root: 'result', totalProperty: 'totalCount'
		       	 	}, ['id','dno','requestStage','request','oprMan','isOpr','requestType','createName','remark','createTime','updateName','updateTime','ts'])
    			});
  		
  		requestStageStore.load();
		requestTypeStore.load();
  		//个性化要求
    	var requestGrid = new Ext.grid.GridPanel({
    				id : "requestGrid"+foo,
    				width:tplWidth,
    				region : 'center',
					heigth:280,
    				title:'个性化要求',
    				autoScroll : true,
    				autoDestroy :false,
    				//autoSizeColumns: true,
    				frame : false,
    				border : true,
    				loadMask : true,
    				stripeRows : true,
    				viewConfig:{
    				   forceFit: true
    				},
    				cm : new Ext.grid.ColumnModel([new Ext.grid.RowNumberer({
								header:'序号', width:20
							}),
							{header : '时间戳',dataIndex : 'ts',hidden:true,sortable : true},
    						{header : '配送单号',dataIndex : 'dno',sortable : true},
    						{header : '执行阶段',dataIndex : 'requestStage',sortable : true},
    						{header : '执行要求',dataIndex : 'request',sortable : true},
    						{header : '执行人',dataIndex : 'oprMan',sortable : true},
    						{header : '是否执行',dataIndex : 'isOpr',sortable : true,
    							renderer:function(v){
    								if(v=='1'){
    									return '执行';
    								}else if(v=='0'){
 										return '未执行';
    								}else if(v=='2'){
    									return '执行失败';
    								}else{
    									return '';
    								}
			        		}
    						
    						},
    						{header : '类型',dataIndex : 'requestType',sortable : true},
    						{header : '备注',dataIndex : 'remark',sortable : true}
    				]),
    				tbar:[{text:'<b>添加个性化要求</b>',iconCls:'userAdd',tooltip : '添加个性化要求',handler:function() {
						saveRequest(null,foo);
			        }},{text:'<b>修改个性化要求</b>',iconCls:'userEdit',tooltip : '修改个性化要求',handler:function() {
						var _records = requestGrid.getSelectionModel().getSelections();
						
						if (_records.length < 1) {
							top.Ext.Msg.alert(alertTitle, "请选择您要修改的行！");
							return false;
						} else if (_records.length > 1) {
							top.Ext.Msg.alert(alertTitle, "一次只能修改一行！");
							return false;
						}
						var isOpr=_records[0].data.isOpr;
						var requestStage=_records[0].data.requestStage;
						
						if(isOpr==1){
							top.Ext.Msg.alert(alertTitle, "改个性化要求已经被执行，不能修改！");
							return;
						}
					/*
						if(requestStage!='通知阶段'){
							top.Ext.Msg.alert(alertTitle, "改个性化要求不在通知阶段，不能修改！");
							return;
						}*/
					
						saveRequest(_records);
			        }}],
    				bbar: new Ext.PagingToolbar({
			            pageSize: pageSize, 
			            store: requestStore, 
			            displayInfo: true,
			            displayMsg: '显示第 {0} 条到 {1} 条记录，共 {2} 条', 
			            emptyMsg: "没有记录信息显示"
			        }),	ds :requestStore 
		});
	
		//历史操作记录
	var historyDoStore=new Ext.data.Store({
	    			 baseParams:{limit : pageSize,filter_EQL_dno:foo,privilege:78},
	    			 proxy:new Ext.data.HttpProxy({url:sysPath+"/"+gridHistoryUrl}),
	    			 reader:new Ext.data.JsonReader({
		            	root: 'resultMap', totalProperty: 'totalCount'
		       	 	}, ['ID','OPRNAME','OPRNODE','OPRCOMMENT','OPRTIME','OPRMAN','OPRDEPART','DNO','OPRTYPE'])
    			});
	
	//历史操作记录
   	var historyDoGrid = new Ext.grid.GridPanel({  
    				id : 'historyDoGrid'+foo,
    				region : 'right',
    				title:'历史操作记录',
    				autoScroll : true,
    				//autoSizeColumns: true,
    				frame : false,
					border : true,
    				loadMask : true,
    				stripeRows : true,
    				width:tplWidth,
					heigth:280,
					viewConfig:{
    				  forceFit: true
    				},
    				cm : new Ext.grid.ColumnModel([new Ext.grid.RowNumberer({
								header:'序号', width:20
							}),
    						{header : '操作节点名称',dataIndex : 'OPRNAME',width:65,sortable : true},
    						{header : '操作节点',dataIndex : 'OPRNODE',hidden:true,sortable : true},
    						{header : '操作内容',dataIndex : 'OPRCOMMENT',width:200,sortable : true},
    						{header : '操作时间',dataIndex : 'OPRTIME',width:85,sortable : true},
    						{header : '操作者',dataIndex : 'OPRMAN',width:85,sortable : true},
    						{header : '操作部门',dataIndex : 'OPRDEPART',width:85,sortable : true},
    						{header : '配送单号',dataIndex : 'DNO',width:65,sortable : true},
    						{header : '节点类型',dataIndex : 'OPRTYPE',hidden:true,sortable : true}
    				]),
    				//tbar:[{text:'<b>操作历史记录</b>',iconCls:'userAdd',tooltip : '操作历史记录',handler:function() {
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
	
		//异常信息
		var exceptionStore=new Ext.data.Store({
	    			 baseParams:{limit : pageSize,filter_EQL_dno:foo},
	    			 sortInfo :{field: "id", direction: "ASC"},
	    			 proxy:new Ext.data.HttpProxy({url:sysPath+"/"+gridExceptionUrl}),
	    			 reader:new Ext.data.JsonReader({
		            	root: 'result', totalProperty: 'totalCount'
		       	 	},[{name:"id",mapping:'id'},'dno','cusName','flightMainNo','flightNo','subNo',
										'consignee','piece','weight','exceptionType1','exceptionNode',
										'exceptionType2','exceptionName','exceptionTime','exceptionRepar',
										'exceptionReparTime','exceptionReparCost','dutyDepartId','dutyDepartName',
										'exceptionPiece','exceptionAdd','exceptionDescribe','suggestion','status',
										'finalResult','dealName','dealTime','dealReasult','isCp','isCus',
										'isWeb','exptionAdd','finalDuty','finalPiece','qm','qmTime','qmSuggestion',
										'submitQualified','dealQualified','reparQualified','createTime','createName',
										'updateTime','updateName','ts','departId','departName','createDepartId','createDepartName'])
    			});
		//异常信息
   		var exceptionGrid = new Ext.grid.GridPanel({  
    				id : 'exceptionGrid'+foo,
    				region : 'right',
    				title:'异常记录',
    				autoScroll : true,
    				//autoSizeColumns: true,
    				frame : false,
					border : true,
    				loadMask : true,
    				stripeRows : true,
    				width:tplWidth,
					heigth:280,
					viewConfig:{
						scrollOffset: 0,
						autoScroll:true
    				},
    				columns:[ rownum,//sm,
			        {header: '配送单号', dataIndex: 'dno',width:60},
			        {header: '异常发生环节', dataIndex: 'exceptionNode',width:60},
			        {header: '异常类型', dataIndex: 'exceptionType1',width:90},
			        {header: '异常发现人', dataIndex: 'exceptionName',width:80},
			        {header: '异常发现时间', dataIndex: 'exceptionTime',width:85},　
			        {header: '异常修复人', dataIndex: 'exceptionRepar',width:75},
			        {header: '修复时间', dataIndex: 'exceptionReparTime',width:80},
			        {header: '修复成本', dataIndex: 'exceptionReparCost',width:60},
			        {header: '处理部门', dataIndex: 'dutyDepartName',width:80},
			        {header: '异常件数', dataIndex: 'exceptionPiece',width:60},
			        {header: '异常处理人', dataIndex: 'dealName',width:80},
			        {header: '异常处理时间', dataIndex: 'dealTime',width:95},
			        {header: '是否通知代理', dataIndex: 'isCp',width:95,renderer:function(v){
			        					if(v==1) return '是';
			        					if(v==0) return '否';
			        					}},
			        {header: '是否通知客户', dataIndex: 'isCus',width:95,renderer:function(v){
			        					if(v==1) return '是';
			        					if(v==0) return '否';
			        					}},
			        {header: '是否网营体现', dataIndex: 'isWeb',width:95,renderer:function(v){
			        					if(v==1) return '是';
			        					if(v==0) return '否';
			        					}},
			        {header: '最终责任人', dataIndex: 'finalDuty',width:85},
			        {header: '最终责任件数', dataIndex: 'finalPiece',width:85},
			        {header: '品管', dataIndex: 'qm',width:70},
			        {header: '品管处理时间', dataIndex: 'qmTime',width:85},
			        {header: '提交是否合格', dataIndex: 'submitQualified',width:95,renderer:function(v){
			        					if(v==1) return '是';
			        					if(v==0) return '否';
			        					}},
			        {header: '处理是否合格', dataIndex: 'dealQualified',width:95,renderer:function(v){
			        					if(v==1) return '是';
			        					if(v==0) return '否';
			        					}},
			        {header: '修复是否合格', dataIndex: 'reparQualified',width:95,renderer:function(v){
			        					if(v==1) return '是';
			        					if(v==0) return '否';
			        					}},
			        {header: '创建部门', dataIndex: 'createDepartName',width:100}
			    ],
    				//sm : new Ext.grid.CheckboxSelectionModel(),
    				//tbar:[{text:'<b>操作历史记录</b>',iconCls:'userAdd',tooltip : '操作历史记录',handler:function() {
					//	alert('留着备用');
			        // }}],
    				bbar: new Ext.PagingToolbar({
			            pageSize: pageSize, 
			            store: exceptionStore, 
			            displayInfo: true,
			            displayMsg: '显示第 {0} 条到 {1} 条记录，共 {2} 条', 
			            emptyMsg: "没有记录信息显示"
			        }),
    				ds :exceptionStore 
		});


		var tableP = new Ext.TabPanel({
    			//	id:'tableP',
					activeTab: 0,
					width:tplWidth,
					listeners:{tabchange:function(tab,e){
							if(tab.activeTab.id=="remarkGrid"+foo){
									remarkStore.reload({
							 			sortInfo :{field: "createTime", direction: "DESC"},
										params : {
											start : 0,
											privilege:75,
											limit : pageSize
										}
									});
							}else if(tab.activeTab.id=="requestGrid"+foo){
									 requestStore.reload({
							 			sortInfo :{field: "createTime", direction: "DESC"},
										params : {
											start : 0,
											privilege:76,
											limit : pageSize
										}
									});
							}else if(tab.activeTab.id=="historyDoGrid"+foo){
							   		historyDoStore.reload({	
												params : {
													start : 0,
													privilege:78,
													limit : pageSize
												}
									});
							}else if(tab.activeTab.id=="exceptionGrid"+foo){
									exceptionStore.reload({	
									 		sortInfo :{field: "id", direction: "ASC"},
												params : {
													start : 0,
													limit : pageSize
												}
									});
							}else if(tab.activeTab.id=='historyInformGrid'+foo){
									historyInformStore.reload({	
									 		sortInfo :{field: "ID", direction: "ASC"},
												params : {
													start : 0,
													limit : pageSize
												}
									});
							}else if(tab.activeTab.id=='recordGrid'+foo){
									dataStore.reload({	
									 		sortInfo :{field: "id", direction: "ASC"},
												params : {
													start : 0,
													limit : pageSize
												}
									});
							}else if(tab.activeTab.id=='faxChangeGrid'+foo){
									faxChangeStore.reload({	
									 		sortInfo :{field: "id", direction: "ASC"},
												params : {
													start : 0,
													limit : pageSize
												}
									});
							}
						}
					},items:[
						historyDoGrid,remarkGrid,requestGrid,exceptionGrid,historyInformGrid,recordGrid,faxChangeGrid
					  ]
		})

		if(tableP.rendered){
			tableP.doLayout();
		}else{
   			tableP.render("dno_"+foo);
   		}
   		tableP.setHeight(280);
}
	
		
 	function addRemark(_records,foo){
 					var storeString = 'remarkGrid'+foo;
					var formRemark = new Ext.form.FormPanel({
								labelAlign : 'left',
								frame : true,
								url:sysPath+'/'+gridRemarkUrl,
								baseParams:{
				    				filter_EQL_id:_records,
				    				privilege:247},
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
													value:foo
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
		if(_records!=null){
			storeAreaTitle='修改备注信息';
			formRemark.load({
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
		items : formRemark,
		buttonAlign : "center",
		buttons : [{
			text : "保存",
			iconCls : 'groupSave',
			handler : function() {
				if (formRemark.getForm().isValid()) {
					this.disabled = true;
					
					formRemark.getForm().submit({
						url : sysPath
								+ '/'+saveRemarkUrl,
						waitMsg : '正在保存数据...',
						success : function(form, action) {
							win.hide(), Ext.Msg.alert(alertTitle,
									action.result.msg, function() {
										  Ext.getCmp(storeString).getStore().reload({
											params : {
												start : 0,
												filter_EQL_dno:foo,
												limit : pageSize
											}
										});
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
												Ext.getCmp(storeString).getStore().reload({
													params : {
														start : 0,
														filter_EQL_dno:foo,
														limit : pageSize
													}
												});
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
				if(_records==null){
					formRemark.getForm().reset();
				}
				if(_records!=null){
					storeAreaTitle='修改备注信息';
					formRemark.load({
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
				formRemark.destroy();
			});
	win.show();
 	
 }

 function saveRequest(_records,foo){
 	
 	/*判断是否可以添加
 	Ext.Ajax.request({
			url : sysPath + '/'
			+ gridRequestUrl,
			params:{filter_EQL_dno:Number(foo)},
			success : function(resp) {
			var jdata = Ext.util.JSON.decode(resp.responseText);
			for(var i=0;i<jdata.result.length;i++){
				if(jdata.result[i].requestStage=='通知阶段'){
					Ext.Msg.alert(alertTitle, "该个性化要求已经存在，请修改！");
					return ;
				}
			}
			*/
			
		requestStageStore.load();
		requestTypeStore.load();
 		if(_records!=null)
					var did=_records[0].data.id;
					var formReq = new Ext.form.FormPanel({
								labelAlign : 'left',
								frame : true,
								url:sysPath+'/'+gridRequestUrl,
								baseParams:{
				    				filter_EQL_id:did,privilege:56},
								bodyStyle : 'padding:5px 5px 0',
								width : 400,
								reader : new Ext.data.JsonReader({
			            root: 'result', totalProperty: 'totalCount'
			        }, ['id','dno','requestStage','request','oprMan','isOpr','requestType','createName','remark','createTime','updateName','updateTime','ts']),
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
													value:foo
												},{
													xtype : 'hidden',
													name : 'isOpr',
													value:0
												},
												 {
													xtype : 'textfield',
													labelAlign : 'left',
													fieldLabel : '执行要求<font style="color:red;">*</font>',
													name : 'request',
													allowBlank : false,
													maxLength:50,
													blankText : "备注内容不能为空！",
													anchor : '95%'
												},{
													xtype : 'combo',
													triggerAction : 'all',
													typeAhead : true,
													queryParam : 'filter_LIKES_typeName',
													store : requestStageStore,
													pageSize : 20,
													resizable:true,
													emptyText : "请选择执行阶段",
													forceSelection : true,
													fieldLabel : '执行阶段',
													editable : true,
													minChars : 0,
													valueField : 'requestStageId',//value值，与fields对应
													displayField : 'requestStageName',//显示值，与fields对应
													name:'requestStage',
													listeners: {  
												         afterRender: function(combo) {
												         	var num=requestStageStore.getTotalCount();
												         	for(var i=0;i<num;i++){
												         		if(combo.store.getAt(i).get('requestStageName')=='送货'){
												         			combo.setValue(combo.store.getAt(i).get('requestStageId'));
												         		}
												         	}
												        }
												    } ,
													id:'trequestStage',
													anchor : '95%'
												},
												{
													xtype : 'combo',
													triggerAction : 'all',
													typeAhead : true,
													queryParam : 'filter_LIKES_typeName',
													store : requestTypeStore,
													pageSize : 20,
													resizable:true,
													emptyText : "请选择要求类型",
													forceSelection : true,
													fieldLabel : '要求类型',
													editable : true,
													minChars : 0,
													listeners: {  
												         afterRender: function(combo) {
												         	var num=requestTypeStore.getTotalCount();
												         	for(var i=0;i<num;i++){
												         		if(combo.store.getAt(i).get('requestTypeName')=='时效'){
												         			combo.setValue(combo.store.getAt(i).get('requestTypeId'));
												         		}
												         	}
												        }
												    } ,
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
			
			formReq.load({
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
		items : formReq,
		buttonAlign : "center",
		buttons : [{
			text : "保存",
			iconCls : 'groupSave',
			handler : function() {
				var trequestStage=Ext.getCmp('trequestStage').getRawValue();
				Ext.Ajax.request({
					url : sysPath + '/'
					+ gridRequestUrl,
					params:{filter_EQL_dno:Number(foo)},
					success : function(resp) {
					var jdata = Ext.util.JSON.decode(resp.responseText);
					for(var i=0;i<jdata.result.length;i++){
						if(jdata.result[i].requestStage==trequestStage){
							Ext.Msg.alert(alertTitle, "该个性化要求在该执行阶段已存在，请修改！");
							return false;
						}
					}
				if (formReq.getForm().isValid()) {
					this.disabled = true;
					
					formReq.getForm().submit({
						url : sysPath+ '/'+saveRequestUrl,
						params:{privilege:76,limit : pageSize},
						waitMsg : '正在保存数据...',
						success : function(form, action) {
							win.hide(), Ext.Msg.alert(alertTitle,
									action.result.msg, function() {
										Ext.getCmp('requestGrid'+foo).getStore().reload({
								 			sortInfo :{field: "createTime", direction: "DESC"},
											params : {
												start : 0,
												privilege:56,
												filter_EQL_dno:foo,
												limit : pageSize
											}
									    });
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
												Ext.getCmp('requestGrid'+foo).getStore().reload({
										 			sortInfo :{field: "createTime", direction: "DESC"},
													params : {
														start : 0,
														privilege:76,
														filter_EQL_dno:foo,
														limit : pageSize
													}
											    });
											});
		
								}
							}
						}
					});
				}
				}
			})
				
			}
		}, {
		
			text : "重置",
			iconCls : 'refresh',
			handler : function() {
				if(did==null){
					formReq.getForm().reset();
				}
				if(did!=null){
					storeAreaTitle='修改个性化要求信息';
					formReq.load({
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
				formReq.destroy();
			});
	win.show();
}
 var menu = new Ext.menu.Menu({
	    items: [{text:'<b>找货单打印</b>',iconCls:'printBtn',
					handler:function() {
						var records = grid.getSelectionModel().getSelections();
						if(records.length>0){
							var dnos='';
							for(var i=0;i<records.length;i++){
		            			dnos+=records[i].data.dno+',';
							}
							parent.print('6',{print_dnos:dnos});
						}else{
							Ext.Msg.alert(alertTitle,'请选择你要打印的单据！');
						}
		        	} 
		        },{text:'<b>标签打印</b>',iconCls:'printBtn',
					handler:function() {
						var records = grid.getSelectionModel().getSelections();
						if(records.length>0){
							var dnos='';
							for(var i=0;i<records.length;i++){
		            			dnos+=records[i].data.dno+',';
							}
							var win = new Ext.Window({
								title : '请输入打印份数，默认为一份.',
								width : 300,
								closeAction : 'hide',
								plain : true,
								modal : true,
								layout:'fit',
								items : [
								//new Ext.form.Label ({
								//	text  : '默认只打印一份，不然则根据输入的份数打印，也可以按照件数打印。',
								//	height:10
								//							
								//}),
								new Ext.form.NumberField ({
									//fieldLabel : '打印份数',
									id:'printNum'
															
								})],
								buttonAlign : "center",
								buttons : [
								{
									text : "打印",
									iconCls : 'printBtn',
									handler : function() {
									    var printNumobj = Ext.getCmp('printNum');
										var printNum = printNumobj.getValue();
										if(!printNumobj.validate()){
											Ext.Msg.alert(alertTitle,'请输入正确的打印份数！');
											return;
										}
										//alert(printNum);
										parent.print('11',{print_dnos:dnos,print_printNum:printNum});
										win.close();
									}
								},{
									text : "按照件数打印",
									iconCls : 'printBtn',
									handler : function() {
										parent.print('11',{print_dnos:dnos,print_byPiece:"true"});
										win.close();
									}
								},{
									text : "取消",
									handler : function() {
										win.close();
									}
								}]
								
							});
							win.show();
							/*Ext.Msg.prompt(alertTitle,"请输入打印份数,默认为一份！",function(btn, text){
								if (btn == 'yes' || btn == 'ok' || btn == true) {
									parent.print('11',{print_dnos:dnos,print_printNum:text});
								}
							});*/
							
						}else{
							Ext.Msg.alert(alertTitle,'请选择你要打印标签的单据！');
						}
		        	} 
		        }
			]
		});
	
	var functionMenu = new Ext.menu.Menu({
	    items: [
									{
										text:'<B>通知预约</B>',
										id : 'speak',
										disabled : true,
										iconCls:'phone',
										handler : function(){
											var records = grid.getSelectionModel().getSelections();
											if(records.length>0){
												if(records[0].get("stockpiece")>0){
													var node=new Ext.tree.TreeNode({id:'speak'+records[0].get("dno"),leaf :false,text:"通知预约"});
										      		node.attributes={href1:'stock/oprInformAction!input.action?informDno='+records[0].get("dno")};
										       		parent.toAddTabPage(node,true);
												}else{
													Ext.Msg.alert(alertTitle,'配送单号为'+records[0].get("dno")+'的记录库存件数为0，不能进行通知预约！');
												}
											}else{
												Ext.Msg.alert(alertTitle,'请选择您需要预知预约的数据！');
											}
										}
									},{
										text:'<B>添加备注</B>',
										id : 'addRemark',
										iconCls:'groupAdd',
										//disabled : true,
										handler : function(){
											var records = grid.getSelectionModel().getSelections();
											if(records.length<=0){
												Ext.Msg.alert(alertTitle,'请选择你要添加备注的数据！');
												return;
											}
											var formRemark = new Ext.form.FormPanel({
												labelAlign : 'left',
												frame : true,
												bodyStyle : 'padding:5px 5px 0',
												width : 400,
												labelAlign : "right",
												items : [{
													layout : 'form',
													items : [{
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
											var win = new Ext.Window({
											title : '添加备注信息',
											width : 400,
											closeAction : 'hide',
											plain : true,
											modal : true,
											items : formRemark,
											buttonAlign : "center",
											buttons : [{
												text : "保存",
												iconCls : 'groupSave',
												handler : function() {
													if (formRemark.getForm().isValid()) {
														this.disabled = true;
														var ids = "";
			    										for(var i = 0; i < records.length; i++) {
															ids += records[i].data.dno + ",";
														}  		
														formRemark.getForm().submit({
															url : sysPath+ '/'+saveRemarkSUrl,
															params:{
																privilege:75,
																limit : pageSize,
																ids:ids
															},
															waitMsg : '正在保存数据...',
															success : function(form, action) {
																win.hide();
																Ext.Msg.alert(alertTitle,action.result.msg);
															},
															failure : function(form, action) {
																if (action.faliureType == Ext.form.Action.SERVER_INVALID) {
																	Ext.Msg.alert(alertTitle, "服务器连接异常，请联系管理员！");
																} 
															}
														});
														this.disabled = false;
													}
												}
											},{
												text : "取消",
												handler : function() {
													win.close();
												}
											}]
										});
										win.on('hide', function() {
													formRemark.destroy();
												});
										win.show();
										}
									},{
										text:'<B>更改班次</B>',
										id : 'updateFlight',
										iconCls:'groupEdit',
										//disabled : true,
										handler : function(){
											var records = grid.getSelectionModel().getSelections();
											if(records.length<=0){
												Ext.Msg.alert(alertTitle,'请选择你更改的数据！');
												return;
											}
											for(var i=0;i<records.length;i++){
												if(0!=records[i].get('oreachstatus')){
													Ext.Msg.alert(alertTitle,"已点到的货物不能更改航班号。");
													return ;						
												}
											}
											
											var flightStore=new Ext.data.Store({   //
											    storeId:"flightStore",                        
									            proxy: new Ext.data.HttpProxy({
									            url:sysPath+"/sys/basFlightAction!list.action",
									            method:'post'
									            }),
									            baseParams:{privilege:62},
									            reader: new Ext.data.JsonReader({
									            root: 'result', totalProperty: 'totalCount'
									           }, [  {name:'flightNo', mapping:'flightNumber',type:'string'}
									              ])
										   	});
											
											var formRemark = new Ext.form.FormPanel({
												labelAlign : 'left',
												frame : true,
												bodyStyle : 'padding:5px 5px 0',
												width : 400,
												labelAlign : "right",
												items : [{
													layout : 'form',
													items : [{
																xtype : 'combo',
																id:'comboTypeFlight',
																triggerAction : 'all',
																store : flightStore,
																minChars : 1,
																hideTrigger:true,
																editable:true,
																fieldLabel : '航班号',
																allowBlank : true,
																listWidth:245,
													    		allowBlank : false,
																emptyText : "请选择航班号",
																editable : true,
															    queryParam : 'filter_LIKES_flightNumber',
																pageSize:50,
																anchor : '95%',
																displayField : 'flightNo',//显示值，与fields对应
																valueField : 'flightNo',//value值，与fields对应
																name : 'oprFaxIn.flightNo',
																listeners:{
																	select:function(v){
																		Ext.Ajax.request({
																			url : sysPath+'/sys/basFlightAction!list.action',
																			params:{
																				privilege:62,
																				filter_EQS_flightNumber:v.getValue(),
																				limit : 50
																			},
																			success : function(response2){
																				if(Ext.decode(response2.responseText).result!=''){
																					Ext.getCmp("fTime").setValue(Ext.decode(response2.responseText).result[0].standardEndtime);
																				}
																			},
																			failure : function(response){
																				Ext.MessageBox.alert(alertTitle, '查询航班信息失败');
																			}
																		});
																	}
																}
														    },{
													    		xtype : 'datefield',
													    		id : 'flightDate',
													    		format : 'Y-m-d',
													    		fieldLabel : '航班日期',
													    		emptyText : "请选择航班日期",
													    		anchor : '95%',
													    		value:new Date(),
													    		allowBlank : false,
																name:'oprFaxIn.flightDate',
													    		listeners : {
													    		}
												    		},{
																xtype : 'timefield',
																labelAlign : 'left',
																fieldLabel : '航班时间',
																format:'H:i',
																id:'fTime',
																name : 'oprFaxIn.flightTime',
																allowBlank : false,
																blankText : "起飞时间不能为空！",
																anchor : '95%'
															},{
																xtype:'checkbox',
																fieldLabel : '航班是否延误',
																name : 'isFlyLateName',
																id:'isFlyLateId',
																anchor : '95%'
															}]
													}]
												});
											var win = new Ext.Window({
											title : '更改航班信息',
											width : 400,
											closeAction : 'hide',
											plain : true,
											modal : true,
											items : formRemark,
											buttonAlign : "center",
											buttons : [{
												text : "保存",
												iconCls : 'groupSave',
												handler : function() {
													var isFlyLate = Ext.getCmp('isFlyLateId').checked;
													if (formRemark.getForm().isValid()) {
														this.disabled = true;
														var ids = "";
			    										for(var i = 0; i < records.length; i++) {
															ids += records[i].data.dno + ",";
														}  		
														formRemark.getForm().submit({
															url : sysPath+ '/'+updateFlightUrl,
															params:{
																ids:ids,
																isFlyLate:(isFlyLate==true?'1':'0')
															},
															waitMsg : '正在保存数据...',
															success : function(form, action) {
																win.hide();
																Ext.Msg.alert(alertTitle,action.result.msg,function(){
																	queryStore();
																});
															},
															failure : function(form, action) {
																if (action.faliureType == Ext.form.Action.SERVER_INVALID) {
																	Ext.Msg.alert(alertTitle, "服务器连接异常，请联系管理员！");
																} 
															}
														});
														this.disabled = false;
													}
												}
											},{
												text : "取消",
												handler : function() {
													win.close();
												}
											}]
										});
										win.on('hide', function() {
													formRemark.destroy();
												});
										win.show();
										}
									},{
										text:'<B>同步客服员</B>',
										id : 'cusService',
										iconCls:'refresh',
										handler : function(){
											var records = grid.getSelectionModel().getSelections();
											if(records.length != 1){
												Ext.Msg.alert(alertTitle,'一次只能操作一条数据！');
												return;
											}else{
												Ext.Msg.confirm(alertTitle, "确定要同步所选的"+records.length+"行记录吗？", function(btnYes) {
													if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
														refreshCusService(records);
													}
												});
												
											}
											
										}
									}
			]
		});
		
	 var meanBtn=new Ext.Button({
		text : '<B>打印</B>',
		tooltip : '打印菜单',
		iconCls : 'group',
		menu: menu
 	});		
    
 	var functionMeanBtn=new Ext.Button({
		text : '<B>功能菜单</B>',
		tooltip : '功能菜单菜单',
		iconCls : 'group',
		menu: functionMenu
 	});	
 	
var onebar = new Ext.Toolbar({
					id:'onebar',
					hideBorders:true,
				//	height : 26,
					//region : 'north',
					    items :  ['&nbsp;',{
					    				text:'<B>信息统计(A)</B>',
										id : 'total',
										iconCls:'fieldEdit',
										handler : function(){
											doTotalInfo();
										}
									},'-',{
					    				text:'<B>数据导出(T)</B>',
										id : 'submitbtn5',
										//disabled : true,
										iconCls:'outExcel',
										handler : function(){
											parent.exportExl(grid);
										}
									},'-',meanBtn,'-',functionMeanBtn,'-',{
										text:'<B>条件重置(W)</B>',
										id : 'submitbtn4',
										//disabled : true,
										iconCls:'refresh',
										handler : resetForm
									},'-',
									{
										text:'<B>详细信息(E)</B>',
										id : 'dnoInfo',
										disabled : true,
										iconCls:'tabAdd',
										handler : getDnoInfo
									},'-',
									{
										text:'<B>高级查询(R)</B>',
										id : 'submitbtn3',
										//disabled : true,
//										iconCls:'btnSearch',
										iconCls:'gjQuery',
										handler : advanceQuery
									},'-',{
										text:'<B>查&nbsp;&nbsp;询(Q)</B>',
										id : 'submitbtn2',
										iconCls:'btnSearch',
										//disabled : true,
										handler : queryStore
									}
									
									,'&nbsp;&nbsp;',{
					    			xtype:'label',
					    			id:'showMsg',
					    			width:200
					    		}
				]
	});


			var qForm = new Ext.form.FormPanel({
						region : 'north',
						id:"qForm",
						iconCls:'btnSearch',
						title : '<span style="font-weight:normal">查询条件<span>',
						collapsible : true,
						border : true,
						draggable:true,
						hideBorders:true,
						//tbar:onebar,
			
						frame : true, //是否渲染表单面板背景色
						labelAlign : 'right', // 标签对齐方式
						bodyStyle : 'padding:3 5 0', // 表单元素和表单面板的边距
						buttonAlign : 'center',
						height : 118,
						items : [{
									layout : 'column',
									border : false,
									items : [{
												columnWidth : .2,
												layout : 'form',
												defaultType : 'textfield',
												labelWidth : 75, // 标签宽度
												border : false,
												items : [{
															fieldLabel : '配送单号',
															name : 'olist[0].dno',
															//style:'background:#D5E1F2',
															id:'dno',
															allowDecimals : false, // 是否允许输入小数
															allowNegative : true, 
															//focusClass:"x-form-focus",
															xtype : 'numberfield', // 设置为数字输入框类型
															anchor : '95%',
															enableKeyEvents:true,
															listeners : {
																	'focus':function(){  
																			Ext.getCmp("dno").selectText();
                        											 }, 
														 			keyup:function(numberfield, e){
														                if(e.getKey() == 13 ){
//														                	Ext.getCmp('dno').getEl().dom.style.background='';
														                	
														                	Ext.getCmp('flightMainNo').focus();
//																			Ext.getCmp('flightMainNo').getEl().dom.style.background='#D5E1F2';
														                 }
														 			}
														 	}
														}, {
															fieldLabel : '收货人信息',
															name : 'olist[0].consignee',
															id:'consignee',
															xtype : 'textfield', // 设置为数字输入框类型
														//	allowDecimals : false, // 是否允许输入小数
														//	allowNegative : false, // 是否允许输入负数
															maxLength : 20,
															anchor : '95%',
															enableKeyEvents:true,
																listeners : {
																		'focus':function(){  
																				Ext.getCmp("consignee").selectText();
                        											  	}, 
														 				keyup:function(numberfield, e){
														                     if(e.getKey() == 13 ){
																				Ext.getCmp('consigneePhone').focus();
//																				Ext.getCmp('consignee').getEl().dom.style.background='';
//														                	
//																				Ext.getCmp('consigneePhone').getEl().dom.style.background='#D5E1F2';
														                     }
														 				}
														 	}
														},{
															fieldLabel : '客服员', // 标签
															id:'customerService',
															xtype:'textfield',
															name : 'olist[0].customerService', // name:后台根据此name属性取值
															maxLength : 20, // 可输入的最大文本长度,不区分中英文字符
															allowBlank : true,
															anchor : '95%',
															enableKeyEvents:true,
															listeners : {
																'focus':function(){  
																	Ext.getCmp("customerService").selectText();
                        										 }, 
														 		 keyup:function(numberfield, e){
														             if(e.getKey() == 13 ){
																		 Ext.getCmp('flightNo').focus();
//																		 Ext.getCmp('customerService').getEl().dom.style.background='';
//														                	
//																		 Ext.getCmp('flightNo').getEl().dom.style.background='#D5E1F2';
														              }
														 		 }
														 	}
														}]
											}, {
												columnWidth : .2,
												layout : 'form',
												labelWidth : 75, // 标签宽度
												defaultType : 'textfield',
												border : false,
												items : [{
															fieldLabel : '主单号', // 标签
															id : 'flightMainNo',
															maxLength : 20,
															name : 'olist[0].flightMainNo', // name:后台根据此name属性取值
															allowBlank : true, // 是否允许为空
															anchor : '95%', // 宽度百分比
															enableKeyEvents:true,
															listeners : {
																'focus':function(){  
																	Ext.getCmp("flightMainNo").selectText();
                        										 }, 
														 		 keyup:function(numberfield, e){
														             if(e.getKey() == 13 ){
																		 Ext.getCmp('subNo').focus();
																		 
//																		 Ext.getCmp('flightMainNo').getEl().dom.style.background='';
//														                	
//																		 Ext.getCmp('subNo').getEl().dom.style.background='#D5E1F2';
														              }
														 		 }
														 	}
														},{
															fieldLabel : '供应商', // 标签
															id:'goWhere',
															name : 'olist[0].goWhere', // name:后台根据此name属性取值
															maxLength : 20, // 可输入的最大文本长度,不区分中英文字符
															allowBlank : true,
															anchor : '95%',
															enableKeyEvents:true,
															listeners : {
																'focus':function(){  
																	Ext.getCmp("goWhere").selectText();
                        										 }, 
														 		 keyup:function(numberfield, e){
														             if(e.getKey() == 13 ){
																		 Ext.getCmp('consignee').focus();
//																		 Ext.getCmp('goWhere').getEl().dom.style.background='';
//														                	
//																 		 Ext.getCmp('consignee').getEl().dom.style.background='#D5E1F2';
														              }
														 		 }
														 	}
														},/* {
															fieldLabel : '收货人电话',
															name : 'olist[0].consigneePhone',
															id:'consigneePhone',
															maxLength : 20,
															xtype : 'numberfield', // 设置为数字输入框类型
														//	allowDecimals : false, // 是否允许输入小数
														//	allowNegative : false, // 是否允许输入负数
															maxLength : 15,
															anchor : '95%',
															enableKeyEvents:true,
															listeners : {
																'focus':function(){  
																	Ext.getCmp("consigneePhone").selectText();
                        										 }, 
														 		 keyup:function(numberfield, e){
														             if(e.getKey() == 13 ){
																		 Ext.getCmp('addr').focus();
//																		 Ext.getCmp('consigneePhone').getEl().dom.style.background='';
//														                	
//																		 Ext.getCmp('comboRightDepart2').getEl().dom.style.background='#D5E1F2';
														              }
														 		 }
														 	}
														},*/{
															xtype : 'combo',
															id:'comboRightDepart2',
															triggerAction : 'all',
															store : rightDepartStore2,
															mode:'local',
															allowBlank : false,
															emptyText : "请选择部门名称",
															forceSelection : false,
															fieldLabel:'部门',
															editable : false,
															displayField : 'departName',//显示值，与fields对应
															valueField : 'departId',//value值，与fields对应
															anchor : '95%',
															enableKeyEvents:true,
															listeners : {
																 render:function(combo){
															
																 		rightDepartStore2.load({
																				params : {
																					start : 0,
																					limit : 500
																				},callback :function(v){
																					var flag=true;
																					for(var i=0;i<rightDepartStore2.getCount();i++){
																						if(rightDepartStore2.getAt(i).get("departId")==bussDepart){
																							flag=false;
																						}
																					}
																					if(flag){
																						var store=new Ext.data.Record.create([{name:'departName'},{name:'departId'}]);
																						var record=new store();
																						record.set("departId",bussDepart);
																						record.set("departName",bussDepartName);
																						rightDepartStore2.add(record);		
																					}
																						combo.setValue(bussDepart);
																				}
																		});
																 		
																 },
																 'focus':function(){  
																	Ext.getCmp("comboRightDepart2").selectText();
                        										 }, 
                        										 expand:function(){
                        										 	if(rightDepartStore2.getCount()==0){
                        										 		var store=new Ext.data.Record.create([{name:'departName'},{name:'departId'}]);
																		var record=new store();
																		record.set("departId",bussDepart);
																		record.set("departName",bussDepartName);
																		rightDepartStore2.add(record);
                        										 	}
                        										 },
														 		 keyup:function(numberfield, e){
														             if(e.getKey() == 13 ){
																		Ext.getCmp('faxInStartDate').focus();
//																		Ext.getCmp('comboRightDepart2').getEl().dom.style.background='';
//														                	
//																		Ext.getCmp('goodsStatus').getEl().dom.style.background='#D5E1F2';
														              }
														 		 }
														 	}
											    }]
											}, /*{
												columnWidth : .25,
												layout : 'form',
												labelWidth : 70, // 标签宽度
												defaultType : 'textfield',
												border : false,
												items : [/*{
															fieldLabel : '收货人地址', // 标签
															id:'addr',
															xtype : 'textfield',
															name : 'olist[0].addr', // name:后台根据此name属性取值
															maxLength : 25, // 可输入的最大文本长度,不区分中英文字符
															anchor : '95%',
															enableKeyEvents:true,
															listeners : {
																'focus':function(){  
																	Ext.getCmp("addr").selectText();
                        										 }, 
														 		 keyup:function(numberfield, e){
														             if(e.getKey() == 13 ){
														             	 Ext.getCmp('goodsStatus').focus();
//														             	 Ext.getCmp('addr').getEl().dom.style.background='';
//														                	
//																		 Ext.getCmp('faxInStartDate').getEl().dom.style.background='#D5E1F2';
														              }
														 		 }
														 	}
														},*//*]
											},*/{
												columnWidth : .2,
												layout : 'form',
												labelWidth : 75, // 标签宽度
												defaultType : 'textfield',
												border : false,
												items : [{
															fieldLabel : '代理名称', // 标签
															id:'cpName',
															name : 'olist[0].cpName', // name:后台根据此name属性取值
															maxLength : 20, // 可输入的最大文本长度,不区分中英文字符
															allowBlank : true,
															anchor : '95%',
															enableKeyEvents:true,
															listeners : {
																'focus':function(){  
																	Ext.getCmp("cpName").selectText();
                        										 }, 
														 		 keyup:function(numberfield, e){
														             if(e.getKey() == 13 ){
																		 Ext.getCmp('goWhere').focus();
//																		 Ext.getCmp('cpName').getEl().dom.style.background='';
//														                	
//																		 Ext.getCmp('goWhere').getEl().dom.style.background='#D5E1F2';
														              }
														 		 }
														 	}
														},{
																	xtype : 'combo',
																	id:'goodsStatus',
																	triggerAction : 'all',
																	store : goodsnewStore2,
																	emptyText : "请选择起始货物状态",
																	pageSize:20,
																	width:70,
																	queryParam :'filter_LIKES_nodeName',
																	triggerAction : 'all',
																	typeAhead : true,
																	forceSelection : true,
																	minChars : 1,
																	listWidth:245,
																	//editable:false,
																	fieldLabel:'起始状态',
																//	mode : "local",//获取本地的值
																	displayField : 'goodsStatusName',//显示值，与fields对应
																	valueField : 'goodsStatus',//value值，与fields对应
																	name : 'olist[0].goodsStatus',
																	anchor : '95%',
																	enableKeyEvents:true,
																	listeners : {
																		'focus':function(){  
																				Ext.getCmp("goodsStatus").selectText();
                        											  	}, 
														 				keyup:function(numberfield, e){
														                     if(e.getKey() == 13 ){
																				Ext.getCmp('goodsStatusTwo').focus();
//																				Ext.getCmp('goodsStatus').getEl().dom.style.background='';
//														                	
//																		 		Ext.getCmp('faxInEndDate').getEl().dom.style.background='#D5E1F2';
														                     }
														 				}
														 			}
														    	},{
																	xtype : 'datefield',
																	fieldLabel : '录单日期',
													    			id : 'faxInStartDate',
													    			name:'olist[0].faxInStartDate',
														    		format : 'Y-m-d',
														    		allowBlank : false,
														    		emptyText : "选择开始日期",
														    		anchor : '95%',
														    		editable : false,
														    		value:new Date().add(Date.DAY, -7),
													    		    enableKeyEvents:true,
																	listeners : {
																		'focus':function(){  
																				Ext.getCmp("faxInStartDate").selectText();
                        											  	 }, 
                        											  	'select':function() {
															    			   var start = Ext.getCmp('faxInStartDate').getValue().format("Y-m-d");
															    			   Ext.getCmp('faxInEndDate').setMinValue(start);
														    		     },
														    		     render:function(){

														    		     },
														 				 keyup:function(numberfield, e){
														                     if(e.getKey() == 13 ){
																				Ext.getCmp('faxInEndDate').focus();
//																				Ext.getCmp('faxInStartDate').getEl().dom.style.background='';
//														                	
//																		 		Ext.getCmp('goodsStatusTwo').getEl().dom.style.background='#D5E1F2';
														                     }
														 				 }
														 			}
																}]
											},{
												columnWidth : .2,
												layout : 'form',
												labelWidth : 75, // 标签宽度
												defaultType : 'textfield',
												border : false,
												items : [{
																	fieldLabel : '班次号', // 标签
																	xtype : 'textfield',
																	id:'flightNo',
																	name : 'olist[0].flightNo', // name:后台根据此name属性取值
																	maxLength : 20, // 可输入的最大文本长度,不区分中英文字符
																	allowBlank : true,
																	anchor : '95%', // 宽度百分比
																	enableKeyEvents:true,
																	listeners : {
																		'focus':function(){  
																				Ext.getCmp("flightNo").selectText();
                        											  	}, 
														 				keyup:function(numberfield, e){
														                     if(e.getKey() == 13 ){
																				Ext.getCmp('comboRightDepart2').focus();
																				
//																				Ext.getCmp('flightNo').getEl().dom.style.background='';
//														                	
//																		 		Ext.getCmp('addr').getEl().dom.style.background='#D5E1F2';
														                     }
														 				}
														 			}
																},{
															xtype : 'combo',
															id:'goodsStatusTwo',
															emptyText : "请选择终止货物状态",
															//	editable:false,
															triggerAction : 'all',
															store : goodsnewStore2,
															listWidth:245,
															width:70,
															pageSize:20,
															queryParam :'filter_LIKES_nodeName',
															typeAhead : true,
															forceSelection : true,
															minChars : 1,
															
															fieldLabel:'终止状态',
														//	mode : "local",//获取本地的值
															displayField : 'goodsStatusName',//显示值，与fields对应
															valueField : 'goodsStatus',//value值，与fields对应
															name : 'olist[0].goodsStatusTwo',
															anchor : '95%',
															enableKeyEvents:true,
															listeners : {
																'focus':function(){  
																		Ext.getCmp("goodsStatusTwo").selectText();
                      											 }, 
												 				 keyup:function(numberfield, e){
												                    if(e.getKey() == 13 ){
																		 Ext.getCmp('customerService').focus();
//																		 Ext.getCmp('goodsStatusTwo').getEl().dom.style.background='';
//														                	
//																 		 Ext.getCmp('customerService').getEl().dom.style.background='#D5E1F2';
												                     }
												 				 }
												 			}
												    	},{
															xtype : 'datefield',
												    		id : 'faxInEndDate',
												    		name:'olist[0].faxInEndDate',
												    		fieldLabel : '-----至',
												    		format : 'Y-m-d',
												    		allowBlank : false,
												    		emptyText : "选择结束日期",
												    		anchor : '95%',
												    		editable : false,
												    		value:new Date(),
												    		enableKeyEvents:true,
															listeners : {
																'focus':function(){  
																		Ext.getCmp("faxInEndDate").selectText();
                      											 }, 
												 				 keyup:function(numberfield, e){
												                     if(e.getKey() == 13 ){
//												                     	 Ext.getCmp('faxInEndDate').getEl().dom.style.background='';
																		advanceQuery();
												                     }
												 				 }
												 			}
														}
													]
											},{
												columnWidth : .2,
												layout : 'form',
												labelWidth : 75, // 标签宽度
												defaultType : 'textfield',
												border : false,
												items : [{
														    		xtype : 'textfield',
																	fieldLabel : '分单号', // 标签
																	id:'faxSubNo',
																	name : 'olist[0].subNo', // name:后台根据此name属性取值
																	maxLength : 20, // 可输入的最大文本长度,不区分中英文字符
																	allowBlank : true,
																	anchor : '95%',// 宽度百分比
																	enableKeyEvents:true,
																	listeners : {
																		'focus':function(){  
																			Ext.getCmp("faxSubNo").selectText();
		                        										 }, 
																 		 keyup:function(numberfield, e){
																             if(e.getKey() == 13 ){
																				 Ext.getCmp('cpName').focus();
		//																		 Ext.getCmp('subNo').getEl().dom.style.background='';
		//														                	
		//																		 Ext.getCmp('cpName').getEl().dom.style.background='#D5E1F2';
																              }
																 		 }
																 	}
																}, /*{
																	xtype : 'combo',
																	id:'distribution',
																	triggerAction : 'all',
																	hideTrigger:true,
																	store : distributionStore,
																	forceSelection : true,
																	fieldLabel:'配送方式',
																//	emptyText : "请选择配送方式",
																	//	editable:false,
																	mode : "local",//获取本地的值
																	displayField : 'typeName',//显示值，与fields对应
																	valueField : 'typeName',//value值，与fields对应
																	name : 'olist[0].distribution',
																	anchor : '95%',
																	enableKeyEvents:true,
																	listeners : {
																		'focus':function(){  
																				Ext.getCmp("distribution").selectText();
                        											  	}, 
														 				keyup:function(numberfield, e){
														                     if(e.getKey() == 13 ){
																				Ext.getCmp('signStatus').focus();
														                     }
														 				}
														 			}
														    	},*/{
																	fieldLabel : '配送方式', // 标签
																	xtype : 'textfield',
																	maxLength : 20, // 可输入的最大文本长度,不区分中英文字符
																	allowBlank : true,
																	id:'distribution',
																	anchor : '95%'
																},{
																	fieldLabel : '去向', // 标签
																	xtype : 'textfield',
																	name : 'olist[0].realgowhere', // name:后台根据此name属性取值
																	maxLength : 20, // 可输入的最大文本长度,不区分中英文字符
																	allowBlank : true,
																	id:'realgowhere0',
																	anchor : '95%'
																}/* {
																	xtype : 'combo',
																	id:'distribution',
																	triggerAction : 'all',
																	hideTrigger:true,
																	store : distributionStore,
																	forceSelection : true,
																	fieldLabel:'配送方式',
																//	emptyText : "请选择配送方式",
																	//	editable:false,
																	mode : "local",//获取本地的值
																	displayField : 'typeName',//显示值，与fields对应
																	valueField : 'typeName',//value值，与fields对应
																	name : 'olist[0].distribution',
																	anchor : '95%',
																	enableKeyEvents:true,
																	listeners : {
																		'focus':function(){  
																				Ext.getCmp("distribution").selectText();
                        											  	}, 
														 				keyup:function(numberfield, e){
														                     if(e.getKey() == 13 ){
																				Ext.getCmp('signStatus').focus();
														                     }
														 				}
														 			}
														    	},{
																	xtype : 'combo',
																	id:'signStatus',
																	hideTrigger:true,
																	triggerAction : 'all',
																	store : signStatusStore,
																	fieldLabel:'签收状态',
															//		emptyText : "请选择是否录签收",
																	forceSelection : true,
																	mode : "local",//获取本地的值
																	displayField : 'signStatusName',//显示值，与fields对应
																	valueField : 'signStatus',//value值，与fields对应
																	name : 'olist[0].signStatus',
																	anchor : '95%',
																	enableKeyEvents:true,
																	listeners : {
																		'focus':function(){  
																			Ext.getCmp("signStatus").selectText();
		                        										 }, 
																 		 keyup:function(numberfield, e){
																             if(e.getKey() == 13 ){
																				 Ext.getCmp('delStatus').focus();
																              }
																 		 }
																 	}
														       }*/]
											}]
								}],
			　　listeners : {
			　	　'render' : function() {
			　		　this.findByType('textfield')[0].focus(true, true); //第一个textfield获得焦点
			　　	  }
			　　}
					});

			// 定义自动当前页行号
			var rownum = new Ext.grid.RowNumberer({
						header : '序号',
						width : 35
					});
			//定义多选
			var sm = new Ext.grid.CheckboxSelectionModel();
			// 定义列模型
			var cm = new Ext.grid.ColumnModel([expander,sm,rownum,
			  {header : '货物状态',dataIndex : 'goodsstatus',width : 110,sortable : true,
	  				renderer: function(v, metadata, record, rowIndex, columnIndex, store) {
	  					//判断是否航班延误（1为延误）
	  					if(record.get('isFlyLate')==1){
	  						v='<font color=red>'+v+'</font>';
	  					}
	  					//判断是否等通知放货（1为等）
      					if(record.get('noticeGoodStatus')==1){
      						 v +='<font color=red><b> 等</b></font>'
      					}
      					//判断是否专车（1为专车）
      					if(record.get('sonderzug')==1){
      						 v +='<font color=red><b> 专</b></font>'
      					}
      					
      					//判断返货状态（1为整票返货，2为部分返货，3为拆零返货）
      					if(record.get('returnStatus')==1){
      						v +='<font color=red><b> 整返</b></font>'
      					}else if(record.get('returnStatus')==2){
      						v +='<font color=red><b> 部返</b></font>'
      					}else if(record.get('returnStatus')==3){
      						v +='<font color=red><b> 拆返</b></font>'
      					}
      					//判断是否异常（1为异常）
      					if(record.get('isOprException')==1){
      						 v +='<font color=red><b> 异</b></font>'
      					}
      					return v;
      				}
			  },
			  {header : '配送单号',dataIndex : 'dno',width : 75,sortable : true}, 
			  {header : '代理公司',dataIndex : 'cpname',sortable : true,width : 130,
			  		renderer:function(v, metadata, record, rowIndex, columnIndex, store){
			    	// alert(v);
			    	 return '<a href="#" onclick="openCustomerInfo('+record.get('cusId')+');" >'+v+'</a>';
			  }},
			  {header : '航班信息',dataIndex : 'flightno',width : 125,sortable : true},
			 {header : '件数',dataIndex : 'piece',width : 45,sortable : true}, 
			  {header : '库存件数',dataIndex : 'stockpiece',width : 65,sortable : true},
			/*
			   {header : '运作异常',dataIndex : 'isOprException',width : 65,sortable : true,
			  		renderer:function(v){
			    		if(v=='1'){
			    			return '<span style="color:red"><b>是</b></span>';
			    		}else{
			    			return '否';
			    		}
			    	}
			   },*/
			  {header : '代理重量',dataIndex : 'cqweight',width : 65,sortable : true},
			  
		      {header : '收入',dataIndex : 'dnoIncomeFee',width : 75},
		      {header : '签收单类型',dataIndex : 'receipttype',width : 80},
			  {header : '主单号',dataIndex : 'flightmainno',width : 100,sortable : true},
			  {header : '分单号',dataIndex : 'subno',width : 85,sortable : true},
			  {header : '签收人',dataIndex : 'realSignMan',width : 60,sortable : true,
					renderer: function(v, metadata, record, rowIndex, columnIndex, store) {
				    	return '<a href="#" onclick="openPhoto('+record.get('dno')+');" >'+v+'</a>';
				    }
				},
				{header : '计费重量',dataIndex : 'cusweight',width : 65,sortable : true},
			    {header : '收货人信息',dataIndex : 'consigneeInfoString',width : 270}, 
			/*, {
				header : '货物当前部门',
				dataIndex : 'curdepart',
				width : 85
			}, {
				header : '提货公司',
				dataIndex : 'customerName',
				width : 75
			}*/
			 {
				header : '备注',
				dataIndex : 'remark',
				width : 200,
				sortable : true
			},
			{header : '开单状态',dataIndex : 'printnum',width : 65,sortable : true,
			    renderer:function renderDescn(v, cellmeta, record, rowIndex, columnIndex, store){
 						if(v>0){
 							cellmeta.css = 'x-grid-back-green';
 							return '<span style=color:red>已开单</span>';
 						}else{
							return "未开单";
 						}
	        		}
			    },
			  {
				header : '当前部门',
				dataIndex : 'curdepart',
				width : 110,
				sortable : true,
			  	renderer:function(v){
			  		var rightDepartId=Ext.getCmp('comboRightDepart2').getRawValue();
			    	if(v!=rightDepartId){
				    	return '<span style="color:red"><b>'+v+'</b></span>';
			    	}else{
			    		return v;
			    	}
			    }	
			  },{header : '库存异常',dataIndex : 'isException',width : 65,sortable : true,
			  	renderer:function(v){
			    	if(v==1){
				    	return '<span style="color:red"><b>是</b></span>';
			    	}else{
			    		return "否";
			    	}
			    }},
			  {header : '运输方式',dataIndex : 'trafficmode',width : 65,sortable : true}, 
			  {header : '加急状态',dataIndex : 'isUrgentStatus',width : 65,sortable : true,
				renderer:function(v){
			    	if(v==1){
				    	return '<span style="color:red">加急</span>';
			    	}else{
			    		return '否'
			    	}
			    }}, 
			 {header : '个性要求',dataIndex : 'request',width : 200,sortable : true},
			  {header : '预付提送费',dataIndex : 'cpfee',width : 75,sortable : true},
			{header : '到付提送费',dataIndex : 'consigneefee',width : 75,sortable : true},
			{header : '中转成本',dataIndex : 'trafee',width : 75,sortable : true},
			{header : '预付增值费',dataIndex : 'cpvalueaddfee',width : 75,sortable : true},
			{
				header : '到付增值费',
				dataIndex : 'cusvalueaddfee',
				sortable : true,
				width : 85
			},{
				header : '预付专车费',
				dataIndex : 'cpSonderzugprice',
				width : 85,
				sortable : true
			},{
				header : '到付专车费',
				dataIndex : 'sonderzugprice',
				width : 85,
				sortable : true
			},{
				header : '代收运费',
				dataIndex : 'paymentcollection',
				width : 65,
				sortable : true,
				renderer:function(v){
			    	return '<span style="color:red">'+v+'</span>';
			    }	
			},{header : '收银合计',dataIndex : 'cashMoney',width : 85},
			  {header : '体积',dataIndex : 'bulk',width : 55,sortable : true},

			  {header : '配送方式',dataIndex : 'dismode',width : 75},
			  {header : '去向',dataIndex : 'realgowhere',width : 150},
			  {header : '供应商',dataIndex : 'gowhere',width : 80,renderer:function(v, metadata, record, rowIndex, columnIndex, store){
			    	// alert(v);
			    	 return '<a href="#" onclick="openCustomerInfo('+record.get('gowhereId')+');" >'+v+'</a>';
			    }},
			  {header : '提货方式',dataIndex : 'takemode',width : 75},
			  {header : '品名',dataIndex : 'goods',width : 80},
			  {header : '是否签收',dataIndex : 'signStatus',width : 75,
				renderer:function(v){
			    	if(v==1){
				    	return "已签收";
			    	}else if(v==2){
			    		return "短信签收";
			    	}else if(v==0){
			    		return "未签收";
			    	}else if(v==3){
			    		return '<span style="color:red">异常签收</span>'
			    	}
			    }
			  },
			 {header : '是否短信录签收',
				dataIndex : 'phoneNoticeSign',
				width : 100,
				renderer:function(v){
			    	if(v=='1'){
				    	return "是";
			    	}else{
			    		return "否";
			    	}
			    }
			},
			{
				header : '签收时间',
				dataIndex : 'sumitDate',
				width : 110,
				sortable : true,
				renderer:function(v){
			    	return  v;
			    }
			},
			{
				header : '收银状态',
				dataIndex : 'cashstatus',
				width : 65,
				renderer:function(v){
			     	return v=='1'?'已收银':'未收银';
			    }
			},{
				header : '收银员',
				dataIndex : 'cashName',
				width : 55
			},{
				header : '收银时间',
				dataIndex : 'cashTime',
				width : 110
			},{
				header : '委托日期',
				dataIndex : 'createtime',
				width : 110,
				sortable : true,
				renderer:function(v){
			    	return  v;
			    }
			},{
				header : '中转成本审核',
				dataIndex : 'fiTraAudit',
				width : 85,
				sortable : true,
				renderer:function(v){
			     	return v=='1'?'已审核':'未审核';
			    }
			},{
				header : '中转成本支付',
				dataIndex : 'traPayStatus',
				width : 85,
				sortable : true,
				renderer:function(v){
			     	return v=='1'?'已支付':'未支付';
			    }
			},{
				header : '中转成本支付时间',
				dataIndex : 'traPayTime',
				sortable : true,
				width : 120
			}
			
			/*,{
				header : '车牌号',
				dataIndex : 'carNoNum',
				sortable : true,
				width : 85
			}*/,{
				header : '机场发车状态',
				dataIndex : 'airportstartstatus',
				width : 85,
				sortable : true,
				renderer:function(v){
					if(v==1){
						return '已发车';
					}else{
				    	return  "未发车";
					}
			    }
			 },{
				header : '机场发车时间',
				dataIndex : 'airportstarttime',
				width : 110,
				sortable : true,
				renderer:function(v){
			    	return  v;
			    }
			},{
				header : '到车确认状态',
				dataIndex : 'doStoreStatus',
				width : 95,
				sortable : true,
				renderer:function(v){
			    	if(v=="1"){
						return "已做到车确认";
			    	}else{
			    		 return "未做到车确认";
			    	}
			    }
			},{
				header : '到车确认人',
				dataIndex : 'doStoreName',
				width : 80,
				sortable : true,
				renderer:function(v){
			    	return v;
			    }
			},{
				header : '到车确认时间',
				dataIndex : 'doStoreTime',
				width : 110,
				sortable : true,
				renderer:function(v){
			    	return v;
			    }  
			},{
				header : '点到状态',
				dataIndex : 'oreachstatus',
				width : 60,
				sortable : true,
				renderer:function(v){
			    	if(v=="1"){
						return "已点到";
			    	}else if(v=="0"){
			    		return "未点到";
			    	}else if(v=="2"){
			    		return "部分点到";
			    	}else{
			    		return v;
			    	}
			    }  
			},{
				header : '点到人',
				dataIndex : 'reachName',
				width : 60,
				sortable : true,
				renderer:function(v){
			    	return v;
			    }
			},{
				header : '点到时间',
				dataIndex : 'stockcreatetime',
				width : 110,
				sortable : true,
				renderer:function(v){
			    	return v;
			    }
			},{
				header : '出库状态',
				dataIndex : 'osuoutstatus',
				width : 85,
				sortable : true,
				renderer:function(v){
			    	if(v=="1"){
						return "已出库";
			    	}else if(v=="0"){
			    		return " 未出库";
			    	}else if(v=="2"){
			    		return "<span style=color:red>异常出库</span>";
			    	}else if(v=='3'){
			    		return "已预配";
			    	}
			    }
			},{
				header : '出库时间',
				dataIndex : 'osuoutstatustime',
				width : 110,
				sortable : true,
				renderer:function(v){
			    	return v;
			    }
			},
			{header : '回单状态',dataIndex : 'curstatus',width : 65,sortable : true},
			  {header : '回单确收人',dataIndex : 'confirmname',width : 75,sortable : true},
			  {header : '回单确收时间',dataIndex : 'confirmtime',width : 110,sortable : true},
			  
			/*{
				header : '配车时间',
				dataIndex : 'discreatetime',
				width : 85,
				sortable : true,
				renderer:function(v){
			    	return v;
			    }
			}{
				header : '代理所在的省市',
				dataIndex : 'cusadd',
				sortable : true,
				width : 95
			},*/{
				header : '客服员',
				dataIndex : 'customerservice',
				sortable : true,
				width : 65
			},{
				header : '客服部门',sortable : true,
				dataIndex : 'serviceDepartName',
				width : 120
			},
			 {header : '录单时间',dataIndex : 'createtime',width : 110,sortable:true}, 
			{
				header : '录单部门',
				dataIndex : 'indepart',
				width : 110,
				sortable : true
					
			}/*,{
				header : '录单部门',
				dataIndex : 'indepart',
				width : 110,
				sortable : true
					
			}*/,{
				header : '配送部门',
				dataIndex : 'distributiondepartname',
				width : 110,
				sortable : true
					
			},{
				header : '终端部门',
				dataIndex : 'enddepart',
				width : 110,
				sortable : true
					
			}/*,{
				header : '是否短信录签收',
				dataIndex : 'phoneNoticeSign',
				width : 95,renderer:function(v){
			        			return v=='0'?'否':'是';
			        		}
			}*/
			]);

			/*************************************************************************************************************************************
			 * 数据存储
			 */
			var store = new Ext.data.Store({
						// 获取数据的方式
						proxy : new Ext.data.HttpProxy({
									method : 'POST',
									url : sysPath+"/fax/oprFaxInAction!inquery.action"
								}),
						baseParams:{
			            	limit : 10
			            },
						// 数据读取器
				        reader: new Ext.data.JsonReader({
				            root: 'result',totalProperty:'totalCount',totalObject:'totalObject'
				        }, fields),
				        remoteSort : true,    //远程排序?
 						//sortInfo : Object,    //{field: "fieldName", direction: "ASC|DESC"}这样的排序对象
				        sortInfo:{field:'dno',direction:'DESC'},
				        listeners:{
			                'load':function(s,records){   //goodStatusColor
							        var girdcount=0; 
							        s.each(function(r){ 
							            grid.getView().getRow(girdcount).style.backgroundColor=r.get('goodStatusColor');
							        	/*
							            if(r.get('goodsstatus')=='入库点到'){ 
							                grid.getView().getRow(girdcount).style.backgroundColor='#F6CECE';
							            }else if(r.get('goodsstatus')=='传真录入'){
							            	grid.getView().getRow(girdcount).style.backgroundColor='#FBEFEF';
							            }else if(r.get('goodsstatus')=='通知预约'){
							            	grid.getView().getRow(girdcount).style.backgroundColor='#FAAC58';
							            }else if(r.get('goodsstatus')=='签收录入'){
							            	grid.getView().getRow(girdcount).style.backgroundColor='#2EFEF7';  //发车确认
							            }else if(r.get('goodsstatus')=='发车确认'){
							            	grid.getView().getRow(girdcount).style.backgroundColor='#58ACFA';  //
							            }else if(r.get('goodsstatus')=='撤消收银'){
							            	grid.getView().getRow(girdcount).style.backgroundColor='#737300';  //
							            }else if(r.get('goodsstatus')=='配送单收银'){
							            	grid.getView().getRow(girdcount).style.backgroundColor='#58FAA8';  //
							            }else if(r.get('goodsstatus')=='收银确认'){
							            	grid.getView().getRow(girdcount).style.backgroundColor='#58FA58';  //
							            }else if(r.get('goodsstatus')=='付款确认'){
							            	grid.getView().getRow(girdcount).style.backgroundColor='#58FA90';  //
							            }else if(r.get('goodsstatus')=='返货入库'){
							            	grid.getView().getRow(girdcount).style.backgroundColor='#66FF33';  //
							            }else if(r.get('goodsstatus')=='签收录入'){
							            	grid.getView().getRow(girdcount).style.backgroundColor='#2EFEF7';  //
							            }else if(r.get('goodsstatus')=='回单确收'){
							            	grid.getView().getRow(girdcount).style.backgroundColor='#2EFEF７';  //
							            }else if(r.get('goodsstatus')=='成本审核'){
							            	grid.getView().getRow(girdcount).style.backgroundColor='#58FA9F';  //
							            }*/
						            	girdcount=girdcount+1;  //
							        })
			        			}	
		                }
					});

				var goodStatusStr=Ext.getCmp('goodsStatus').getValue();
			   	var startOrder=goodStatusStr==""?"":goodStatusStr.substring(goodStatusStr.indexOf(".")+1);
			   	var goodStatusStrTwo=Ext.getCmp('goodsStatusTwo').getValue();
			   	var startOrderTwo=goodStatusStrTwo==""?"":goodStatusStr.substring(goodStatusStrTwo.indexOf(".")+1);

			// 翻页排序时带上查询条件
			store.on('beforeload', function(){
						this.baseParams={
				 				 'olist[0].rightDepartId':Ext.getCmp('comboRightDepart2').getValue()==''?bussDepart:Ext.getCmp('comboRightDepart2').getValue(),
								 'olist[0].consignee':Ext.getCmp('consignee').getValue(),
								// 'olist[0].consigneePhone':Ext.getCmp('consigneePhone').getValue(),
								 'olist[0].cpName':Ext.getCmp('cpName').getValue(),
								 'olist[0].customerService':Ext.getCmp('customerService').getValue(),	
								 'olist[0].delStatus':Ext.getCmp('delStatus').getValue(),	
								 'olist[0].distribution':Ext.getCmp('distribution').getValue(),
								 'olist[0].distributionDepartId':Ext.getCmp('distributionDepartId').getValue(),
								 'olist[0].dno':Ext.getCmp('dno').getValue(),	
								 'olist[0].doGoodEndDate':Ext.getCmp('doGoodEndDate').getValue()=="选择结束日期"?"":Ext.getCmp('doGoodEndDate').getRawValue(),
								 'olist[0].doGoodStartDate':Ext.getCmp('doGoodStartDate').getValue()=="选择开始日期"?"":Ext.getCmp('doGoodStartDate').getRawValue(),
								 'olist[0].doGoods':Ext.getCmp('doGoods').getValue(),	
								 'olist[0].doMoneyStatus':Ext.getCmp('doMoneyStatus').getValue(),	
								 'olist[0].endDate':Ext.getCmp('endDate').getValue()=="选择结束日期"?"":Ext.getCmp('endDate').getRawValue(),
								 'olist[0].endDepartId':Ext.getCmp('endDepartId').getValue(),
								 'olist[0].faxInStartDate':Ext.getCmp('faxInStartDate').getValue()=="选择开始日期"?"":Ext.getCmp('faxInStartDate').getRawValue(),
								 'olist[0].faxInEndDate':Ext.getCmp('faxInEndDate').getValue()=="选择结束日期"?"":Ext.getCmp('faxInEndDate').getRawValue(),
								 'olist[0].flightMainNo':Ext.getCmp('flightMainNo').getValue(),
								 'olist[0].flightNo':Ext.getCmp('flightNo').getValue(),
								 'olist[0].goWhere':Ext.getCmp('goWhere').getValue(),
								 'olist[0].goodsStatus':startOrder,
								 'olist[0].goodsStatusTwo':startOrderTwo,
								 'olist[0].isDoDanStatus':Ext.getCmp('isDoDanStatus').getValue(),
								 'olist[0].isDoToStatus':Ext.getCmp('isDoToStatus').getValue(),
								 'olist[0].isErrorStatus':Ext.getCmp('isErrorStatus').getValue(),
								 'olist[0].isNoticeStatus':Ext.getCmp('isNoticeStatus').getValue(),
								// 'olist[0].isUrgentStatus':Ext.getCmp('isUrgentStatus').getValue(),
								 'olist[0].noticeGoodStatus':Ext.getCmp('noticeGoodStatus').getValue(),
								 'olist[0].overmemo':Ext.getCmp('overmemo').getValue(),
								 'olist[0].returnStatus':Ext.getCmp('returnGoods').getValue(),
								 'olist[0].queryCondition':Ext.getCmp('queryCondition').getValue(),
								 'olist[0].queryConditionSelect':Ext.getCmp('queryConditionSelect').getValue(),	
								 'olist[0].serviceDepartName':Ext.getCmp('serviceDepartId').getValue(),  // 不是客服名字是客服Code
								 'olist[0].signDanStatus':Ext.getCmp('signDanStatus').getValue(),
								 'olist[0].signStatus':Ext.getCmp('signStatus').getValue(),
								 'olist[0].sonderzug':Ext.getCmp('sonderzug').getValue(),
								 'olist[0].trafficmode':Ext.getCmp('trafficmode').getValue(),
								 'olist[0].startDate':Ext.getCmp('startDate').getValue()=="选择开始日期"?"":Ext.getCmp('startDate').getRawValue(),
								 'olist[0].startDepartId':Ext.getCmp('startDepartId').getValue(),
								 'olist[0].subNo':Ext.getCmp('faxSubNo').getValue(),
								 'olist[0].fiTraAudit':Ext.getCmp('costAudit').getValue(),
								 'olist[0].realgowhere':Ext.getCmp('realgowhere0').getValue(),
								 limit : bbar.pageSize
				 			}
			});
			
			store.on('load', function(store,records,options){
				if(store.getCount()!=0){
					Ext.getCmp('collectMsg').getEl().update('<span style="color:red"><a href="#" onclick="doTotalInfo();">'+store.getAt(0).get('collectMsg')+'</a></span>');
				}else{
					Ext.getCmp('collectMsg').getEl().update('<span style="color:red"><a href="#" onclick="doTotalInfo();">总票数：0，总件数：0，未到件数：0，总重量：0</a></span>');
				}
			});
			
			// 每页显示条数下拉选择框
			var pagesize_combo = new Ext.form.ComboBox({
						name : 'pagesize',
						triggerAction : 'all',
						mode : 'local',
						store : new Ext.data.ArrayStore({
									fields : ['value', 'text'],
									data : [[5, '5条/页'],[10, '10条/页'], [20, '20条/页'], [50, '50条/页'], [100, '100条/页'], [250, '250条/页'], [500, '500条/页']]
								}),
						valueField : 'value',
						displayField : 'text',
						value : '20',
						editable : false,
						width :68
					});

			// 改变每页显示条数reload数据
			pagesize_combo.on("select", function(comboBox) {
						bbar.pageSize = parseInt(comboBox.getValue());
						number = parseInt(comboBox.getValue());
						queryStore();
					});
			var number = parseInt(pagesize_combo.getValue());
			
			// 分页工具栏
			var bbar = new Ext.PagingToolbar({
						pageSize : number,
						id:'bbar',
						store : store,
						displayInfo : true,
						displayMsg : '显示{0}条到{1}条,共{2}条',
						emptyMsg : "没有符合条件的记录",
						items : ['-', '&nbsp;&nbsp;', pagesize_combo,'&nbsp;&nbsp;&nbsp;&nbsp;',{
					    			xtype:'label',
					    			id:'collectMsg',
					    			width:200
					    		}]
					});
					
					

			// 表格实例
			var grid = new Ext.grid.GridPanel({
						region : 'center', // 和VIEWPORT布局模型对应，充当center区域布局
						// collapsible : true,
						//border : true,
						// 表格面板标题,默认为粗体，我不喜欢粗体，这里设置样式将其格式为正常字体
						//title : '<span style="font-weight:normal">查询结果</span>',
						autoScroll : true,
					//	hideHeaders:true,
						frame : false,
					//	autoExpandColumn: 'consigneeInfoString',
						columnLines:true,  //True表示为在列分隔处显示分隔符。默认为false
						enableHdMenu:true,  //True表示为在头部出现下拉按钮，以激活头部菜单
					//	enableColumnHide:
					//	floating:true,//浮动面板
					//	draggable:true,//面板拖动
						trackMouseOver:true,
					//	enableColumnMove:false,
					//	enableDragDrop:true,
						//enableColumnResize:false,  //可以拉伸。
						id:'grid',
						//plugins: expander,
						store : store, // 数据存储
						plugins : [expander],
						stripeRows : true, // 斑马线
						cm : cm, // 列模型
						sm:sm,
						bbar : bbar,// 分页工具栏
						viewConfig : {
							// 不产横向生滚动条, 各列自动扩展自动压缩, 适用于列数比较少的情况
							forceFit : false,
							columnsText : "显示的列",
							sortAscText : "升序",
							sortDescText : "降序"
						
						},
						loadMask : {
							msg : '正在加载表格数据,请稍等...'
						}
					});

	

			/* 布局  
			var viewport = new Ext.Viewport({
						layout : 'border',
						items : [qForm]
					});*/
	var panel = new Ext.Panel({
						layout : 'border',
						id:'mpanel',
						frame:true,
						border:true,
				    //	autoWidth: true,
					    height: Ext.lib.Dom.getViewHeight(),
					    width : Ext.lib.Dom.getViewWidth(),
					    renderTo: Ext.getBody(),
					    tbar:onebar,
						items : [qForm,grid]
						
					});
					
	form = new Ext.form.FormPanel({
								labelAlign : 'left',
								frame : true,
							//	url:sysPath+'/user/userAction!ralaList.action?privilege=17',
								id:'formG',
								bodyStyle : 'padding:3 5 0',
							//    width : 600,
							    labelWidth : 100, 
							    border : true,
							    layout : 'form',
							//	reader :jsonread,
					            labelAlign : "right",
					        /*    listeners : {
									'render' : function() {
										this.findByType('combo')[0].focus(); //第一个textfield获得焦点
									}
								},*/
						        items : [{
											layout : 'column',
											border : false,
											items : [{
														columnWidth : .34,
														layout : 'form',
														border : false,
														labelWidth : 90, // 标签宽度
														items : [{
																	xtype : 'combo',
																	id:'signDanStatus',
																	triggerAction : 'all',
																	store : signDanStatusStore,
																	emptyText : "请选择签收单状态",
																	//	editable:false,
																	fieldLabel:'签收单状态',
																	mode : "local",//获取本地的值
																	displayField : 'signDanStatusName',//显示值，与fields对应
																	valueField : 'signDanStatus',//value值，与fields对应
																	name : 'olist[0].signDanStatus',
																	anchor : '95%',
																	enableKeyEvents:true,
																	listeners : {
																		'focus':function(){  
																				Ext.getCmp("signDanStatus").selectText();
                        											  	}, 
														 				keyup:function(numberfield, e){
														                     if(e.getKey() == 13 ){
																				Ext.getCmp('distribution').focus();
														                     }
														 				}
														 			}
														    	},{
																	xtype : 'combo',
																	id:'delStatus',
																	triggerAction : 'all',
																	store : delStatusStore,
																	emptyText : "请选择货物作废状态",
																//	editable:false,
																	forceSelection : true,
																	fieldLabel:'作废状态',
																	mode : "local",//获取本地的值
																	displayField : 'delStatusName',//显示值，与fields对应
																	valueField : 'delStatus',//value值，与fields对应
																	name : 'olist[0].delStatus',
																	anchor : '95%',
																	enableKeyEvents:true,
																	listeners : {
																		'focus':function(){  
																				Ext.getCmp("delStatus").selectText();
                        											  	}, 
														 				keyup:function(numberfield, e){
														                     if(e.getKey() == 13 ){
																				Ext.getCmp('doGoodStartDate').focus();
														                     }
														 				}
														 			}
														    	},{
																	xtype : 'combo',
																	id:'doGoods',
																	triggerAction : 'all',
																	store : doGoodsStore,
																	forceSelection : true,
																	emptyText : "请选择提货方式",
																	//	editable:false,
																	fieldLabel:'提货方式',
																	mode : "local",//获取本地的值
																	displayField : 'typeName',//显示值，与fields对应
																	valueField : 'typeName',//value值，与fields对应
																	name : 'olist[0].doGoods',
																	anchor : '95%',
																	enableKeyEvents:true,
																	listeners : {
																		'focus':function(){  
																				Ext.getCmp("doGoods").selectText();
                        											  	}, 
														 				keyup:function(numberfield, e){
														                     if(e.getKey() == 13 ){
																				Ext.getCmp('startDate').focus();
														                     }
														 				}
														 			}
														    	},{
																	xtype : 'combo',
																	id:'isNoticeStatus',
																	triggerAction : 'all',
																	store : isNoticeStatusStore,
																	fieldLabel:'通知预约',
																	//	editable:false,
																	forceSelection : true,
																	emptyText : "请选择是否通知预约",
																	mode : "local",//获取本地的值
																	displayField : 'isNoticeStatusName',//显示值，与fields对应
																	valueField : 'isNoticeStatus',//value值，与fields对应
																	name : 'olist[0].isNoticeStatus',
																	anchor : '95%',
																	enableKeyEvents:true,
																	listeners : {
																		'focus':function(){  
																				Ext.getCmp("isNoticeStatus").selectText();
                        											  	}, 
														 				keyup:function(numberfield, e){
														                     if(e.getKey() == 13 ){
																				Ext.getCmp('sonderzug').focus();
														                     }
														 				}
														 			}
														    	}
														    	
														    	/*,{
																	xtype : 'combo',
																	id:'isHuiShouStatus',
																	triggerAction : 'all',
																	store : isHuiShouStatusStore,
																	fieldLabel:'是否回收确认',
																	mode : "local",//获取本地的值
																	displayField : 'isHuiShouStatusName',//显示值，与fields对应
																	valueField : 'isHuiShouStatus',//value值，与fields对应
																	name : 'isHuiShouStatus',
																	anchor : '95%'
														    	},{
																	xtype : 'combo',
																	id:'isUrgentStatus',
																	triggerAction : 'all',
																	emptyText : "请选择是否加急",
																	//	editable:false,
																	store : isUrgentStatusStore,
																	fieldLabel:'<span style="color:red">加急</span>',
																	mode : "local",//获取本地的值
																	displayField : 'isUrgentStatusName',//显示值，与fields对应
																	valueField : 'isUrgentStatus',//value值，与fields对应
																	name : 'olist[0].isUrgentStatus',
																	anchor : '95%',
																	enableKeyEvents:true,
																	listeners : {
																		'focus':function(){  
																				Ext.getCmp("isUrgentStatus").selectText();
                        											  	}, 
														 				keyup:function(numberfield, e){
														                     if(e.getKey() == 13 ){
																				Ext.getCmp('distribution').focus();
														                     }
														 				}
														 			}
														    	}*/,{
																	xtype : 'combo',
																	id:'doMoneyStatus',
																	triggerAction : 'all',
																	store : doMoneyStatusStore,
																	fieldLabel:'收银状态',
																	emptyText : "请选择收银状态",
																	//	editable:false,
																	mode : "local",//获取本地的值
																	displayField : 'doMoneyStatusName',//显示值，与fields对应
																	valueField : 'doMoneyStatus',//value值，与fields对应
																	name : 'olist[0].doMoneyStatus',
																	anchor : '95%',
																	enableKeyEvents:true,
																	listeners : {
																		'focus':function(){  
																				Ext.getCmp("doMoneyStatus").selectText();
                        											  	}, 
														 				keyup:function(numberfield, e){
														                     if(e.getKey() == 13 ){
																				Ext.getCmp('isErrorStatus').focus();
														                     }
														 				}
														 			}
														    	},{
																	xtype : 'combo',
																	id:'startDepartId',
																	triggerAction : 'all',
																	store : bussStore,
																	listWidth:245,
																	minChars : 1,
																	queryParam : 'filter_LIKES_departName',
																	forceSelection : true,
																	emptyText : "请选择开单部门名称",
																	fieldLabel:'开单部门',
																	allowBlank : true,
																	pageSize:500,
																	displayField : 'departName',//显示值，与fields对应
																	valueField : 'departId',//value值，与fields对应
																	name : 'olist[0].startDepartId',
																	anchor : '95%',
																	enableKeyEvents:true,
																	listeners : {
																	 	render:function(combo){
																	    },
																			'focus':function(){  
																					Ext.getCmp("startDepartId").selectText();
	                        											  	}, 
															 				keyup:function(numberfield, e){
															                     if(e.getKey() == 13 ){
																					Ext.getCmp('isDoDanStatus').focus();
															                     }
															 				}
															 			}
																	
															    },{
																	xtype : 'combo',
																	id:'distributionDepartId',
																	triggerAction : 'all',
																	store : bussStore,
																	listWidth:245,
																	emptyText : "请选择配送部门名称",
																	forceSelection : true,
																	minChars : 1,
																	queryParam : 'filter_LIKES_departName',
																	fieldLabel:'配送部门',
																	editable : true,
																	pageSize:50,
																	displayField : 'departName',//显示值，与fields对应
																	valueField : 'departId',//value值，与fields对应
																	name : 'olist[0].distributionDepartId',
																	anchor : '95%',
																	enableKeyEvents:true,
																	listeners : {
																		'focus':function(){  
																				Ext.getCmp("distributionDepartId").selectText();
                        											  	}, 
														 				keyup:function(numberfield, e){
														                     if(e.getKey() == 13 ){
																				Ext.getCmp('serviceDepartId').focus();
														                     }
														 				}
														 			}
																	
															    },{
																	xtype : 'combo',
																	id:'costAudit',
																	triggerAction : 'all',
																	store : traPayStatusStore,
																	forceSelection : true,
																	fieldLabel:'中转成本审核',
																	emptyText : "请选择是否审核",
																	//	editable:false,
																	mode : "local",//获取本地的值
																	displayField : 'name',//显示值，与fields对应
																	valueField : 'id',//value值，与fields对应
																	name : 'olist[0].fiTraAudit',
																	anchor : '95%',
																	enableKeyEvents:true,
																	listeners : {
																		'focus':function(){  
																				Ext.getCmp("costAudit").selectText();
                        											  	}, 
														 				keyup:function(numberfield, e){
														                     if(e.getKey() == 13 ){
																				Ext.getCmp('distributionDepartId').focus();
														                     }
														 				}
														 			}
														    	},{
																	xtype : 'combo',
																	id:'signStatus',
																	triggerAction : 'all',
																	store : signStatusStore,
																	fieldLabel:'签收状态',
															//		emptyText : "请选择是否录签收",
																	forceSelection : true,
																	mode : "local",//获取本地的值
																	displayField : 'signStatusName',//显示值，与fields对应
																	valueField : 'signStatus',//value值，与fields对应
																	name : 'olist[0].signStatus',
																	anchor : '95%',
																	enableKeyEvents:true,
																	listeners : {
																		'focus':function(){  
																			Ext.getCmp("signStatus").selectText();
		                        										 }, 
																 		 keyup:function(numberfield, e){
																             if(e.getKey() == 13 ){
																				 Ext.getCmp('delStatus').focus();
																              }
																 		 }
																 	}
														       }]

													},{
														columnWidth : .33,
														layout : 'form',
														border : false,
														labelWidth : 80, // 标签宽度
														items : [{xtype : 'datefield',
																	fieldLabel : '送货日期',
													    			id : 'doGoodStartDate',
													    			name:'olist[0].doGoodStartDate',
														    		format : 'Y-m-d',
														    		emptyText : "选择开始日期",
														    		anchor : '95%',
														    		enableKeyEvents:true,
														    		listeners : {
														    			'select' : function() {
														    			   var start = Ext.getCmp('doGoodStartDate').getValue().format("Y-m-d");
														    			   Ext.getCmp('doGoodEndDate').setMinValue(start);
														    		     },
														    		     'focus':function(){  
																				Ext.getCmp("doGoodStartDate").selectText();
                        											  	 }, 
														 				 keyup:function(numberfield, e){
														                     if(e.getKey() == 13 ){
																				Ext.getCmp('doGoodEndDate').focus();
														                     }
														 				 }
													    		    }
																},{
																	xtype : 'datefield',
																	fieldLabel : '收货日期',
													    			id : 'startDate',
													    			name:'olist[0].startDate',
														    		format : 'Y-m-d',
														    		emptyText : "选择开始日期",
														    		anchor : '95%',
														    		enableKeyEvents:true,
														    		listeners : {
														    			'select' : function() {
														    			   var start = Ext.getCmp('startDate').getValue().format("Y-m-d");
														    			   Ext.getCmp('endDate').setMinValue(start);
														    		     },
														    		     'focus':function(){  
																				Ext.getCmp("startDate").selectText();
                        											  	 }, 
														 				 keyup:function(numberfield, e){
														                     if(e.getKey() == 13 ){
																				Ext.getCmp('endDate').focus();
														                     }
														 				 }
													    		    }
																},{
																	xtype : 'combo',
																	id:'sonderzug',
																	triggerAction : 'all',
																	store : oneCarStore,
																	emptyText : "请选择是否专车",
																	//	editable:false,
																	forceSelection : true,
																	fieldLabel:'是否专车',
																	mode : "local",//获取本地的值
																	displayField : 'sonderzugName',//显示值，与fields对应
																	valueField : 'sonderzug',//value值，与fields对应
																	name : 'olist[0].sonderzug',
																	anchor : '95%',
																	enableKeyEvents:true,
																	listeners : {
																		'focus':function(){  
																				Ext.getCmp("sonderzug").selectText();
                        											  	}, 
														 				keyup:function(numberfield, e){
														                     if(e.getKey() == 13 ){
																				Ext.getCmp('isDoToStatus').focus();
														                     }
														 				}
														 			}
														    	},{
														    		xtype : 'combo',
																	id:'isErrorStatus',
																	triggerAction : 'all',
																	store : isErrorStatusStore,
																	fieldLabel:'是否异常',
																	emptyText : "请选择是否异常",
																	//	editable:false,
																	mode : "local",//获取本地的值
																	displayField : 'isErrorStatusName',//显示值，与fields对应
																	valueField : 'isErrorStatus',//value值，与fields对应
																	name : 'olist[0].isErrorStatus',
																	anchor : '95%',
																	enableKeyEvents:true,
																	listeners : {
																		'focus':function(){  
																				Ext.getCmp("isErrorStatus").selectText();
                        											  	}, 
														 				keyup:function(numberfield, e){
														                     if(e.getKey() == 13 ){
																				Ext.getCmp('noticeGoodStatus').focus();
														                     }
														 				}
														 			}
																},{
																	xtype : 'combo',
																	id:'isDoDanStatus',
																	triggerAction : 'all',
																	store : isDoDanStatusStore,
																	fieldLabel:'是否开单',
																	emptyText : "请选择是否开单",
																	//	editable:false,
																	mode : "local",//获取本地的值
																	displayField : 'isDoDanStatusName',//显示值，与fields对应
																	valueField : 'isDoDanStatus',//value值，与fields对应
																	name : 'olist[0].isDoDanStatus',
																	anchor : '95%',
																	enableKeyEvents:true,
																	listeners : {
																		'focus':function(){  
																				Ext.getCmp("isDoDanStatus").selectText();
                        											  	}, 
														 				keyup:function(numberfield, e){
														                     if(e.getKey() == 13 ){
																				Ext.getCmp('overmemo').focus();
														                     }
														 				}
														 			}
																	
														    	},{
																   xtype : 'combo',
																   id:'serviceDepartId',
										        				   fieldLabel: '客服部门',
														           queryParam : 'filter_LIKES_departName',
															       minChars : 1,
															       triggerAction : 'all',
															       forceSelection : true,
																   store: serviceDepartStore,
																   pageSize : 50,
																   listWidth:245,
																   displayField : 'departName',
																   valueField : 'departId',
																   name : 'olist[0].serviceDepartId',
																   anchor : '95%',
																   emptyText : "请选择客服部门名称",
																   enableKeyEvents:true,
																	listeners : {
                        											  	'focus':function(){  
																				Ext.getCmp("serviceDepartId").selectText();
                        											  	}, 
														 				keyup:function(numberfield, e){
														                     if(e.getKey() == 13 ){
																				Ext.getCmp('endDepartId').focus();
														                     }
														 				}
														 			}
										        				},{
																	xtype : 'combo',
																	id:'returnGoods',
																	triggerAction : 'all',
																	store : returnStatusStore,
																	emptyText : "请选择返货状态",
																	//	editable:false,
																	fieldLabel:'返货状态',
																	mode : "local",//获取本地的值
																	displayField : 'name',//显示值，与fields对应
																	valueField : 'id',//value值，与fields对应
																	name : 'olist[0].returnStatus',
																	anchor : '95%',
																	enableKeyEvents:true,
																	listeners : {
																		'focus':function(){  
																				Ext.getCmp("queryCondition").selectText();
                        											  	}, 
														 				keyup:function(numberfield, e){
														                     if(e.getKey() == 13 ){
																				Ext.getCmp('queryConditionSelect').focus();
														                     }
														 				}
														 			}
														    	},{
																	xtype : 'combo',
																	id:'queryCondition',
																	triggerAction : 'all',
																	store : queryConditionStore,
																	emptyText : "请选择查询条件",
																	//	editable:false,
																	fieldLabel:'自选查询',
																	mode : "local",//获取本地的值
																	displayField : 'queryConditionName',//显示值，与fields对应
																	valueField : 'queryCondition',//value值，与fields对应
																	name : 'olist[0].queryCondition',
																	anchor : '95%',
																	enableKeyEvents:true,
																	listeners : {
																		'focus':function(){  
																				Ext.getCmp("queryCondition").selectText();
                        											  	}, 
														 				keyup:function(numberfield, e){
														                     if(e.getKey() == 13 ){
																				Ext.getCmp('queryConditionSelect').focus();
														                     }
														 				}
														 			}
														    	}]

													}, {
														columnWidth : .33,
														layout : 'form',
														border : false,
														labelWidth : 80, // 标签宽度
														items : [{
																	xtype : 'datefield',
														    		id : 'doGoodEndDate',
														    		name:'olist[0].doGoodEndDate',
														    		fieldLabel : '------至',
														    		format : 'Y-m-d',
														    		emptyText : "选择结束日期",
														    		anchor : '95%',
														    		enableKeyEvents:true,
																	listeners : {
																		'focus':function(){  
																				Ext.getCmp("doGoodEndDate").selectText();
                        											  	}, 
														 				keyup:function(numberfield, e){
														                     if(e.getKey() == 13 ){
																				Ext.getCmp('doGoods').focus();
														                     }
														 				}
														 			}
																},{
																	xtype : 'datefield',
														    		id : 'endDate',
														    		name:'olist[0].endDate',
														    		fieldLabel : '------至',
														    		format : 'Y-m-d',
														    		emptyText : "选择结束日期",
														    		anchor : '95%',
														    		enableKeyEvents:true,
																	listeners : {
																		'focus':function(){  
																				Ext.getCmp("endDate").selectText();
                        											  	}, 
														 				keyup:function(numberfield, e){
														                     if(e.getKey() == 13 ){
																				Ext.getCmp('isNoticeStatus').focus();
														                     }
														 				}
														 			}
																},{
																	xtype : 'combo',
																	id:'isDoToStatus',
																	triggerAction : 'all',
																	store : isDoToStatusStore,
																	fieldLabel:'是否点到',
																	emptyText : "请选择是否点到",
																	//	editable:false,
																	mode : "local",//获取本地的值
																	displayField : 'isDoToStatusName',//显示值，与fields对应
																	valueField : 'isDoToStatus',//value值，与fields对应
																	name : 'olist[0].isDoToStatus',
																	anchor : '95%',
																	enableKeyEvents:true,
																	listeners : {
																		'focus':function(){  
																				Ext.getCmp("isDoToStatus").selectText();
                        											  	}, 
														 				keyup:function(numberfield, e){
														                     if(e.getKey() == 13 ){
																				Ext.getCmp('doMoneyStatus').focus();
														                     }
														 				}
														 			}
														    	},{
																	xtype : 'combo',
																	id:'noticeGoodStatus',
																	triggerAction : 'all',
																	store : noticeGoodStatusStore,
																	fieldLabel:'等通知放货',
																	emptyText : "请选择是否等通知放货",
																	//	editable:false,
																	mode : "local",//获取本地的值
																	displayField : 'noticeGoodStatusName',//显示值，与fields对应
																	valueField : 'noticeGoodStatus',//value值，与fields对应
																	name : 'olist[0].noticeGoodStatus',
																	anchor : '95%',
																	enableKeyEvents:true,
																	listeners : {
																		'focus':function(){  
																				Ext.getCmp("noticeGoodStatus").selectText();
                        											  	}, 
														 				keyup:function(numberfield, e){
														                     if(e.getKey() == 13 ){
																				Ext.getCmp('startDepartId').focus();
														                     }
														 				}
														 			}
														    	},{
																	xtype : 'combo',
																	id:'overmemo',
																	triggerAction : 'all',
																	store : isOvermemoStore,
																	forceSelection : true,
																	fieldLabel:'是否出库',
																	emptyText : "请选择是否出库",
																	//	editable:false,
																	mode : "local",//获取本地的值
																	displayField : 'overmemoName',//显示值，与fields对应
																	valueField : 'overmemo',//value值，与fields对应
																	name : 'olist[0].overmemo',
																	anchor : '95%',
																	enableKeyEvents:true,
																	listeners : {
																		'focus':function(){  
																				Ext.getCmp("overmemo").selectText();
                        											  	}, 
														 				keyup:function(numberfield, e){
														                     if(e.getKey() == 13 ){
																				Ext.getCmp('distributionDepartId').focus();
														                     }
														 				}
														 			}
														    	},{
																	xtype : 'combo',
																	id:'endDepartId',
																	triggerAction : 'all',
																	store : bussStore,
																	listWidth:245,
																	emptyText : "请选择终端部门名称",
																	queryParam : 'filter_LIKES_departName',
																	forceSelection : true,
																	minChars : 1,
																	fieldLabel:'终端部门',
																	editable : true,
																	pageSize:50,
																	displayField : 'departName',//显示值，与fields对应
																	valueField : 'departId',//value值，与fields对应
																	name : 'olist[0].endDepartId',
																	anchor : '95%',
																	enableKeyEvents:true,
																	listeners : {
																		'focus':function(){  
																				Ext.getCmp("endDepartId").selectText();
                        											  	}, 
														 				keyup:function(numberfield, e){
														                     if(e.getKey() == 13 ){
																				Ext.getCmp('queryCondition').focus();
														                     }
														 				}
														 			}
															    },{
																	xtype : 'combo',
																	id:'trafficmode',
																	triggerAction : 'all',
																	store : trafficmodeStatusStore,
																	emptyText : "请选择运输方式",
																	//	editable:false,
																	fieldLabel:'运输方式',
																	mode : "local",//获取本地的值
																	displayField : 'name',//显示值，与fields对应
																	valueField : 'name',//value值，与fields对应
																	name : 'olist[0].trafficmode',
																	anchor : '95%',
																	enableKeyEvents:true,
																	listeners : {
																		'focus':function(){  
																				Ext.getCmp("queryCondition").selectText();
                        											  	}, 
														 				keyup:function(numberfield, e){
														                     if(e.getKey() == 13 ){
																				Ext.getCmp('queryConditionSelect').focus();
														                     }
														 				}
														 			}
														    	},{
																	fieldLabel : '--------->', // 标签
																	xtype : 'textfield',
																	name : 'olist[0].queryConditionSelect', // name:后台根据此name属性取值
																	maxLength : 20, // 可输入的最大文本长度,不区分中英文字符
																	allowBlank : true,
																	id:'queryConditionSelect',
																	anchor : '95%',// 宽度百分比
																/*	validator:function(msg){
																		    var c=Ext.getCmp('queryCondition');
																		    var b=Ext.getCmp("queryConditionSelect").getValue();
																			if(c.getValue()=="GoodsPiece"){
	                        											     	var regex=/^[1-9]\d*$/;
	                        											        msg="请输入正确的件数";
	                        											     	if(!regex.test(b)){
	                        											     	 	return msg;
	                        											     	    return false;
	                        											     	}else{
	                        											     		return true;
	                        											     	}
                        											     	}else if(c.getValue()=="GoodsWeight"||c.getValue()=="doGoodsFee"){
	                        											     	var regex=/^\d+(\.\d+)?$/;
	                        											     	var regex2=/^[1-9]\d*|0$/;
			                        											if(c.getValue()=="GoodsWeight") msg="请输入正确的重量";
			                        											if(c.getValue()=="doGoodsFee") msg="请输入正确的代收货款";
			                        											if(regex.test(b)||regex2.test(b)){
			                        											 	return true;
			                        											}else{
				                        											return msg;
			                        											    return false;
			                        											}
	                        											    }else if(c.getValue()==""){
	                        											    	if(b!=""){
	                        											    		msg="请选择自选查询条件";
	                        											    		return msg;
	                        											    		return false;
	                        											    	}else{
	                        											    		return true;
	                        											    	}
	                        											    }else{
	                        											    	return true;
	                        											    }
	                        											    return true;
																	},*/
																	enableKeyEvents:true,
																	listeners : {
																		'focus':function(){  
																				Ext.getCmp("queryConditionSelect").selectText();
                        											  	}, 
														 				keyup:function(numberfield, e){
														                     if(e.getKey() == 13){
																				winG.hide();
					    														queryStore();
														                     }
														 				}
														 			}
																}]
													}]
													
										}]
										});
		
		
		
		
		winG = new Ext.Window({
			title : "高级查询",
			width : 850,
			id:'winG',
			iconCls:'gjQuery',
			closeAction : 'hide',
			plain : true,
		    resizable : false,	
			modal : false,
			collapsible:true,
			plain:true,
			//layout : 'fit', // 设置窗口布局模式
			floating:true,
			shadow:true,
			//constrain : true, // 设置窗口是否可以溢出父容器
			minButtonWidth:50,
			shim:false,
			hideBorders:true,
			titleCollapse:true,
			animateTarget:'submitbtn3',
		    //resizable : false,	
			listeners: {
		　　		'show': function() {
		　　			this.findByType('combo')[0].focus(true, true); //第一个combo,textfield获得焦点
		　　		}
	　　		}, 
			items : form,
			buttonAlign : "right",	
			buttons : [ 
			   {
					text : "确定",
					iconCls : 'groupAdd',
					hidden:true,
					handler : function(){
						winG.hide();
					}
			   },
			   {
					text : "查询",
					iconCls : 'groupSave',
					handler : function(){
						winG.collapse();
					    queryStore();
			   		}
			   },
			   {
					text : "重置",
					iconCls : 'refresh',
					handler : function(){
							 form.getForm().reset();
				    }
			   }, 
			   {
					text : "取消(D)",
					iconCls : 'groupClose',
					handler : function() {

					 form.getForm().reset();
		 			 winG.hide();
		 			 Ext.getCmp("dno").focus();
		 			 Ext.getCmp("dno").selectText();

					  
				    }
    		   }]
								
		
		});
		
										
	function advanceQuery(){
		winG.show();
		winG.expand();
	}

	grid.on('click', function() {
		select();
	});

/*
	grid.getStore().on('load',function(s,records){
        var girdcount=0;
        s.each(function(r){
            if(r.get('goodsstatus')=='传真录入'){
                grid.getView().getRow(girdcount).style.backgroundColor='#FFFF00';
            }else if(r.get('goodsstatus')=='test数据'){
                grid.getView().getRow(girdcount).style.backgroundColor='#FF1493';
            }else if(r.get('goodsstatus')=='1'){
                grid.getView().getRow(girdcount).style.backgroundColor='#DCDCDC';
            }
            girdcount=girdcount+1;
        });
    });*/


	function queryStore(){
		Ext.getCmp('showMsg').getEl().update('');
		if(!form.getForm().isValid()){
			Ext.Msg.alert(alertTitle,"高级查询条件格式不符合要求", function() {
				advanceQuery();
			});
			return;
		}
	
		/*
		var ves =Ext.getCmp('faxInEndDate').getValue();
		//alert(ves); returnj\;  .format("Y-m-d")
		var vss =Ext.getCmp('faxInStartDate').getValue();
		var sDate = ves.getTime() - vss.getTime();
        //var passDate = sYear * 365 + sMonth * 30 + sDate ;
        //var date3=date2.getTime()-date1.getTime()  //时间差的毫秒
        var passDate=Math.floor(sDate/(24*3600*1000));
		if(passDate>30){
			Ext.Msg.alert(alertTitle,"查询条件不符合要求，只能查询30天以内的数据", function(){
				Ext.getCmp('faxInEndDate').markInvalid("只能查询30天以内的数据");
				Ext.getCmp('faxInStartDate').markInvalid("只能查询30天以内的数据");
				Ext.getCmp('faxInEndDate').focus();
			});
			return;
		}*/

		
	/*	if(!qForm.getForm().isValid()){
			Ext.Msg.alert(alertTitle,"查询条件格式不符合要求", function() {
			
			});
			return ;
		}*/
		var num=bbar.pageSize;
		var goodStatusStr=Ext.getCmp('goodsStatus').getValue();
	   	var startOrder=goodStatusStr==""?"":goodStatusStr.substring(goodStatusStr.indexOf(".")+1);
	   	var goodStatusStrTwo=Ext.getCmp('goodsStatusTwo').getValue();
	   	var startOrderTwo=goodStatusStrTwo==""?"":goodStatusStr.substring(goodStatusStrTwo.indexOf(".")+1);		
		store.baseParams= {
			 'olist[0].rightDepartId':Ext.getCmp('comboRightDepart2').getValue()==''?bussDepart:Ext.getCmp('comboRightDepart2').getValue(),
			 'olist[0].consignee':Ext.getCmp('consignee').getValue(),
		//	 'olist[0].consigneePhone':Ext.getCmp('consigneePhone').getValue(),
			 'olist[0].cpName':Ext.getCmp('cpName').getValue(),
			 'olist[0].customerService':Ext.getCmp('customerService').getValue(),	
			 'olist[0].delStatus':Ext.getCmp('delStatus').getValue(),	
			 'olist[0].distribution':Ext.getCmp('distribution').getValue(),
			 'olist[0].distributionDepartId':Ext.getCmp('distributionDepartId').getValue(),
			 'olist[0].dno':Ext.getCmp('dno').getValue(),	
			 'olist[0].doGoodEndDate':Ext.getCmp('doGoodEndDate').getValue()==""?"":Ext.getCmp('doGoodEndDate').getRawValue(),
			 'olist[0].doGoodStartDate':Ext.getCmp('doGoodStartDate').getValue()==""?"":Ext.getCmp('doGoodStartDate').getRawValue(),
			 'olist[0].doGoods':Ext.getCmp('doGoods').getValue(),	
			 'olist[0].doMoneyStatus':Ext.getCmp('doMoneyStatus').getValue(),	
			 'olist[0].endDate':Ext.getCmp('endDate').getValue()==""?"":Ext.getCmp('endDate').getRawValue(),
			 'olist[0].endDepartId':Ext.getCmp('endDepartId').getValue(),
			 'olist[0].faxInStartDate':Ext.getCmp('faxInStartDate').getValue()==""?"":Ext.getCmp('faxInStartDate').getRawValue(),
			 'olist[0].faxInEndDate':Ext.getCmp('faxInEndDate').getValue()==""?"":Ext.getCmp('faxInEndDate').getRawValue(),
			 'olist[0].flightMainNo':Ext.getCmp('flightMainNo').getValue(),
			 'olist[0].flightNo':Ext.getCmp('flightNo').getValue(),
			 'olist[0].goWhere':Ext.getCmp('goWhere').getValue(),
 			 'olist[0].goodsStatus':startOrder,
			 'olist[0].goodsStatusTwo':startOrderTwo,
			 'olist[0].isDoDanStatus':Ext.getCmp('isDoDanStatus').getValue(),
			 'olist[0].isDoToStatus':Ext.getCmp('isDoToStatus').getValue(),
			 'olist[0].returnStatus':Ext.getCmp('returnGoods').getValue(),
			 'olist[0].isErrorStatus':Ext.getCmp('isErrorStatus').getValue(),
			 'olist[0].isNoticeStatus':Ext.getCmp('isNoticeStatus').getValue(),
			// 'olist[0].isUrgentStatus':Ext.getCmp('isUrgentStatus').getValue(),
			 'olist[0].noticeGoodStatus':Ext.getCmp('noticeGoodStatus').getValue(),
			 'olist[0].overmemo':Ext.getCmp('overmemo').getValue(),
			 'olist[0].queryCondition':Ext.getCmp('queryCondition').getValue(),
			 'olist[0].queryConditionSelect':Ext.getCmp('queryConditionSelect').getValue()==""?null:Ext.getCmp('queryConditionSelect').getValue(),	
			 'olist[0].serviceDepartName':Ext.getCmp('serviceDepartId').getValue(),  // 不是客服名字是客服Code
			 'olist[0].signDanStatus':Ext.getCmp('signDanStatus').getValue(),
			 'olist[0].signStatus':Ext.getCmp('signStatus').getValue(),
			 'olist[0].sonderzug':Ext.getCmp('sonderzug').getValue(),
			 'olist[0].trafficmode':Ext.getCmp('trafficmode').getValue(),
			 'olist[0].startDate':Ext.getCmp('startDate').getValue()==""?"":Ext.getCmp('startDate').getRawValue(),
			 'olist[0].startDepartId':Ext.getCmp('startDepartId').getValue(),
			 //'olist[0].subNo':Ext.getCmp('faxSubNo').getValue(),
			 'olist[0].subNo':Ext.getCmp('faxSubNo').getValue(),
			 'olist[0].fiTraAudit':Ext.getCmp('costAudit').getValue(),
			 'olist[0].realgowhere':Ext.getCmp('realgowhere0').getValue(),
			 limit : num
		};
 					//Ext.apply(store.baseParams,dataString);
 					
 			store.reload({
				params : {
					start : 0,
					limit : num
				},callback :function(reponse){
					if(reponse.length==0){
					
					}
				}
			});
			
	}
	
	
	function getDnoInfo(){
		
		var dnoInfobtn = Ext.getCmp('dnoInfo');
		var record = grid.getSelectionModel().getSelections();
		if (record.length == 0||record.length>1) {
			dnoInfobtn.setDisabled(true);
			Ext.Msg.alert(alertTitle, '请选择一条您需要查看详细信息的记录');
			return false;
		}
		
		var node=new Ext.tree.TreeNode({
			id:'dnoInfo'+record[0].data.dno,
			leaf :true,
			text:"详细信息"
		});

		var v = Ext.getCmp('comboRightDepart2').getValue();
		if(v==''){
			v=bussDepart;
		}
		node.attributes={
			href1:'fax/oprFaxInAction!info.action?dno='+record[0].data.dno+'&rightDepart='+v
		};
	  	parent.toAddTabPage(node,true);
	}
	
	function select(){
		var vnetmusicRecord = grid.getSelectionModel().getSelections();
		var dnoInfobtn = Ext.getCmp('dnoInfo');
		var speak = Ext.getCmp('speak');
		if (vnetmusicRecord.length == 1) {
			dnoInfobtn.setDisabled(false);
			speak.setDisabled(false);
		} else if (vnetmusicRecord.length > 1) {
			dnoInfobtn.setDisabled(true);
			speak.setDisabled(true);
		} else {
			dnoInfobtn.setDisabled(true);
			speak.setDisabled(true);
		}
	}
	
	
	 var totalForm = new Ext.form.FormPanel({
								labelAlign : 'left',
								frame : true,
							//	url:sysPath+'/user/userAction!ralaList.action?privilege=17',
							    border:true,
								bodyStyle : 'padding:0px 0px 5px',
							    width : 500,
							    labelWidth : 100,
							//	reader :jsonread,
					            labelAlign : "right",
						        items : [{
											layout : 'column',
											items : [{
														columnWidth : .5,
														layout : 'form',
														items : [ {
																	xtype : 'textfield',
																	labelAlign : 'left',
																	fieldLabel : '总票数(票)',
																	name : 'totalDno',
																	id:'totalDno',
																	readOnly:true,
																	allowBlank : false,
																	anchor : '95%'
																}, {
																	xtype : 'textfield',
																	labelAlign : 'left',
																	fieldLabel : '总件数(件)',
																	name : 'totalPrice',
																	id : 'totalPrice',
																	readOnly:true,
																	allowBlank : false,
																	anchor : '95%'
																}, {
																	xtype : 'textfield',
																	labelAlign : 'left',
																	fieldLabel : '总体积(方)',
																	name : 'totalV',
																	id : 'totalV',
																	readOnly:true,
																	allowBlank : false,
																	anchor : '95%'
																},{
																	xtype : 'textfield',
																	labelAlign : 'left',
																	fieldLabel : '总中转费(元)',
																	name : 'userCode',
																	id : 'totalTraFee',
																	readOnly:true,
																	allowBlank : false,
																	anchor : '95%'
																},{
																	xtype : 'textfield',
																	labelAlign : 'left',
																	fieldLabel : '总预付专车(元)',
																	name : 'totalSonP',
																	id : 'totalSonP',
																	readOnly:true,
																	allowBlank : false,
																	anchor : '95%'
																},{
																	xtype : 'textfield',
																	labelAlign : 'left',
																	fieldLabel : '总到付专车(元)',
																	name : 'totalSon',
																	id : 'totalSon',
																	readOnly:true,
																	allowBlank : false,
																	anchor : '95%'
																}]

													}, {
														columnWidth : .5,
														layout : 'form',
														items : [{
																	xtype : 'textfield',
																	labelAlign : 'left',
																	fieldLabel : '总重量(Kg)',
																	name : 'totalWeight',
																	id : 'totalWeight',
																	readOnly:true,
																	allowBlank : false,
																	anchor : '95%'
																},{
																	xtype : 'textfield',
																	labelAlign : 'left',
																	fieldLabel : '总库存件数(元)',
																	name : 'totalStorePiece',
																	id : 'totalStorePiece',
																	readOnly:true,
																	allowBlank : false,
																	anchor : '95%'
																},{
																	xtype : 'textfield',
																	labelAlign : 'left',
																	fieldLabel : '总代收货款(元)',
																	name : 'userCode',
																	id : 'totalP',
																	readOnly:true,
																	allowBlank : false,
																	anchor : '95%'
																},{
																	xtype : 'textfield',
																	labelAlign : 'left',
																	fieldLabel : '代理数(个)',
																	id : 'totalcp',
																	readOnly:true,
																	allowBlank : false,
																	anchor : '95%'
																},{
																	xtype : 'textfield',
																	labelAlign : 'left',
																	fieldLabel : '总预付合计(元)',
																	name : 'totalYuFee',
																	id : 'totalYuFee',
																	readOnly:true,
																	allowBlank : false,
																	anchor : '95%'
																},{
																	xtype : 'textfield',
																	labelAlign : 'left',
																	fieldLabel : '总到付合计(元)',
																	name : 'userCode',
																	id : 'totalDaoFee',
																	readOnly:true,
																	allowBlank : false,
																	anchor : '95%'
																},{
																	xtype : 'textfield',
																	labelAlign : 'left',
																	fieldLabel : '收入合计(元)',
																	name : 'totalFee',
																	id : 'totalFee',
																	readOnly:true,
																	allowBlank : false,
																	anchor : '95%'
																}]
													}]
													
										}]
										});
	
	
		var totalWin = new Ext.Window({
			title : "信息统计",
			id:'totalWin',
			width : 500,
			iconCls:'fieldEdit',
			closeAction : 'hide',
			plain : true,
		    resizable : false,	
			modal : false,
			collapsible:true,
			plain:true,
			floating:true,
			shadow:true,
			constrainHeader:true,
			minButtonWidth:50,
			shim:false,
			hideBorders:true,
			titleCollapse:true,
			animateTarget:'total',
		//	animCollapse:true, 
			items : totalForm,
			buttonAlign : "right",	buttons : [{
						text : "重新统计",
						iconCls : 'refresh',
						handler : function() {
							queryTotal();
						}
					  },{
						text : "数据清空",
						iconCls : 'userDelete',
						handler : function(){
							finalZero();
						
						}
					},{
						text : "隐藏窗口",
						iconCls : 'groupClose',
						handler : function() {
						   totalWin.hide();
						}
					}]
		});
		
		totalWin.on('hide',function(){ totalWin.hide();});
	function finalZero(){
		Ext.getCmp('totalYuFee').setValue(0);
		Ext.getCmp('totalDaoFee').setValue(0);
		
		Ext.getCmp('totalDno').setValue(0);
		Ext.getCmp('totalTraFee').setValue(0);
		Ext.getCmp('totalP').setValue(0);
		Ext.getCmp('totalStorePiece').setValue(0);
		Ext.getCmp('totalWeight').setValue(0);

		Ext.getCmp('totalSonP').setValue(0);
		Ext.getCmp('totalcp').setValue(0);
		
		Ext.getCmp('totalPrice').setValue(0);
		Ext.getCmp('totalV').setValue(0);
		Ext.getCmp('totalSon').setValue(0);
		Ext.getCmp('totalFee').setValue(0);
	}
	
	Ext.getCmp('showMsg').getEl().update('温馨提示：快捷键为Ctrl加每个按钮的后的字母。');
	
	function resetForm(){
		form.getForm().reset();
		qForm.getForm().reset();
		Ext.getCmp('comboRightDepart2').setValue(bussDepart);
	}
	
			// 查询表格数据
	function queryBalanceInfo(pForm) {
		var params = pForm.getValues();
		params.start = 0;
		params.limit = bbar.pageSize;
		store.load({
				params : params
		});
	}
	
	//键盘事件 打开主单信息
	 Ext.get("mpanel").addKeyListener({
                key:[69],
                ctrl:true
                //alt:true
            },getDnoInfo);
	
	//键盘事件 导出
	 Ext.get("mpanel").addKeyListener({
                key:[84],
                ctrl:true
                //alt:true
            },function(){
            	parent.exportExl(grid);
            });
	
		//键盘事件 信息统计
	 Ext.get("mpanel").addKeyListener({
                key:[65],
                ctrl:true
                //alt:true
            },function(){
            	totalInfo();
            });
	
		//键盘事件 查询
	Ext.get("mpanel").addKeyListener({
                key:[81],
                ctrl:true
                //shift:true
            },queryStore);
       
       //键盘事件 高级查询     
    Ext.get("mpanel").addKeyListener({
                key:[82],
                ctrl:true
                //alt:true
                //shift:true
            },advanceQuery);
     
      //键盘事件 重置     
     Ext.get("mpanel").addKeyListener({
                key:[87],
                ctrl:true
                //alt:true
                //shift:true
            },resetForm);       
            
 	/*winG.addKeyListener({
                key:[68],
                ctrl:true
                //alt:true
                //shift:true
    },function(){
         	 form.getForm().reset();
		 	 winG.hide();
		 	 Ext.getCmp("dno").focus();
			 Ext.getCmp("dno").selectText();
    });  */ 
    if(null!=pub_flightMainNo && pub_flightMainNo.length>0){
    	Ext.getCmp('flightMainNo').setValue(pub_flightMainNo);
    	queryStore();
    }        
            
            
});
	function doTotalInfo(){
		var ccc=Ext.getCmp('totalWin');
		if(ccc.isVisible()){
			ccc.setVisible(false);
		}else{
			queryTotal();
			ccc.show();
			ccc.expand();
		}
	}
			
	function totalInfo(){
		queryTotal();
		Ext.getCmp('totalWin').show();
		Ext.getCmp('totalWin').expand();
	}
	
	function queryTotal(){
		var goodStatusStr=Ext.getCmp('goodsStatus').getValue();
	   	var startOrder=goodStatusStr==""?"":goodStatusStr.substring(goodStatusStr.indexOf(".")+1);
	   	var goodStatusStrTwo=Ext.getCmp('goodsStatusTwo').getValue();
	   	var startOrderTwo=goodStatusStrTwo==""?"":goodStatusStr.substring(goodStatusStrTwo.indexOf(".")+1);
			Ext.Ajax.request({
					url : sysPath+"/fax/oprFaxInAction!inqueryTotal.action",
					params:{
						 'olist[0].rightDepartId':Ext.getCmp('comboRightDepart2').getValue()==''?bussDepart:Ext.getCmp('comboRightDepart2').getValue(),
						 'olist[0].consignee':Ext.getCmp('consignee').getValue(),
			//			 'olist[0].consigneePhone':Ext.getCmp('consigneePhone').getValue(),
						 'olist[0].cpName':Ext.getCmp('cpName').getValue(),
						 'olist[0].customerService':Ext.getCmp('customerService').getValue(),	
						 'olist[0].delStatus':Ext.getCmp('delStatus').getValue(),	
						 'olist[0].distribution':Ext.getCmp('distribution').getValue(),
						 'olist[0].distributionDepartId':Ext.getCmp('distributionDepartId').getValue(),
						 'olist[0].dno':Ext.getCmp('dno').getValue(),	
						 'olist[0].doGoodEndDate':Ext.getCmp('doGoodEndDate').getValue()=="选择结束日期"?"":Ext.getCmp('doGoodEndDate').getRawValue(),
						 'olist[0].doGoodStartDate':Ext.getCmp('doGoodStartDate').getValue()=="选择开始日期"?"":Ext.getCmp('doGoodStartDate').getRawValue(),
						 'olist[0].doGoods':Ext.getCmp('doGoods').getValue(),	
						 'olist[0].doMoneyStatus':Ext.getCmp('doMoneyStatus').getValue(),	
						 'olist[0].endDate':Ext.getCmp('endDate').getValue()=="选择结束日期"?"":Ext.getCmp('endDate').getRawValue(),
						 'olist[0].endDepartId':Ext.getCmp('endDepartId').getValue(),
						 'olist[0].faxInStartDate':Ext.getCmp('faxInStartDate').getValue()=="选择开始日期"?"":Ext.getCmp('faxInStartDate').getRawValue(),
						 'olist[0].faxInEndDate':Ext.getCmp('faxInEndDate').getValue()=="选择结束日期"?"":Ext.getCmp('faxInEndDate').getRawValue(),
						 'olist[0].flightMainNo':Ext.getCmp('flightMainNo').getValue(),
						 'olist[0].flightNo':Ext.getCmp('flightNo').getValue(),
						 'olist[0].goWhere':Ext.getCmp('goWhere').getValue(),
						 'olist[0].goodsStatus':startOrder,
						 'olist[0].goodsStatusTwo':startOrderTwo,
						 'olist[0].isDoDanStatus':Ext.getCmp('isDoDanStatus').getValue(),
						 'olist[0].isDoToStatus':Ext.getCmp('isDoToStatus').getValue(),
						 'olist[0].isErrorStatus':Ext.getCmp('isErrorStatus').getValue(),
						 'olist[0].isNoticeStatus':Ext.getCmp('isNoticeStatus').getValue(),
						// 'olist[0].isUrgentStatus':Ext.getCmp('isUrgentStatus').getValue(),
						 'olist[0].noticeGoodStatus':Ext.getCmp('noticeGoodStatus').getValue(),
						 'olist[0].overmemo':Ext.getCmp('overmemo').getValue(),
						 'olist[0].returnStatus':Ext.getCmp('returnGoods').getValue(),
						 'olist[0].queryCondition':Ext.getCmp('queryCondition').getValue(),
						 'olist[0].queryConditionSelect':Ext.getCmp('queryConditionSelect').getValue(),	
						 'olist[0].serviceDepartName':Ext.getCmp('serviceDepartId').getValue(),  // 不是客服名字是客服Code
						 'olist[0].signDanStatus':Ext.getCmp('signDanStatus').getValue(),
						 'olist[0].signStatus':Ext.getCmp('signStatus').getValue(),
						 'olist[0].sonderzug':Ext.getCmp('sonderzug').getValue(),
						 'olist[0].trafficmode':Ext.getCmp('trafficmode').getValue(),
						 'olist[0].startDate':Ext.getCmp('startDate').getValue()=="选择开始日期"?"":Ext.getCmp('startDate').getRawValue(),
						 'olist[0].startDepartId':Ext.getCmp('startDepartId').getValue(),
						 'olist[0].subNo':Ext.getCmp('faxSubNo').getValue(),
						 'olist[0].fiTraAudit':Ext.getCmp('costAudit').getValue(),
						 'olist[0].realgowhere':Ext.getCmp('realgowhere0').getValue(),
						 limit : Ext.getCmp('bbar').pageSize
					},
					success : function(response2){
						if(Ext.decode(response2.responseText).result!=''){
							var result=Ext.decode(response2.responseText);
							Ext.getCmp('totalYuFee').setValue(result.totalYuFee);
							Ext.getCmp('totalDaoFee').setValue(result.totalDaoFee);
							
							Ext.getCmp('totalSonP').setValue(result.cpSonderzugprice);
							Ext.getCmp('totalDno').setValue(result.dno);
							Ext.getCmp('totalTraFee').setValue(result.trafee);
							Ext.getCmp('totalP').setValue(result.paymentcollection);
							Ext.getCmp('totalStorePiece').setValue(result.stockpiece);
							Ext.getCmp('totalWeight').setValue(result.cusweight);
		
							Ext.getCmp('totalPrice').setValue(result.piece);
							Ext.getCmp('totalV').setValue(result.bulk);
							Ext.getCmp('totalSon').setValue(result.sonderzugprice);
							Ext.getCmp('totalFee').setValue(result.totalFee);
							Ext.getCmp('totalcp').setValue(result.totalCp);
						}else{
							Ext.MessageBox.alert(alertTitle, '信息统计失败，数据库出错，请重试');
						}
					},
					failure : function(response){
						Ext.MessageBox.alert(alertTitle, '信息统计失败，数据库出错，请重试');
					}
				});
	}
	
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
				url : sysPath+ "/sys/customerAction!ralaList.action",
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
							url : sysPath+ "/sys/customerAction!ralaList.action",
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
				handler : function() {
					win.close();
				}
			}]});
		win.on('hide', function() {
					form.destroy();
				});
		win.show();
	}


	function openPhoto(dno){
 		var tableP = new Ext.TabPanel({
    				animScroll:true,
    				autoScroll:true,
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
						id:dno
					},
					success : function(resp) {
						
						var jdata = Ext.util.JSON.decode(resp.responseText);
						
						if(jdata.length==0){
							var img1 = new Ext.Panel({
								autoScroll:true,
							 	height:380,
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
							 	id:'tab'+i,
							 	split : false,
							//	collapsible : true,
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
						tableP.activate('tab0');
					},
					failure : function(form, action){
						Ext.Msg.alert(alertTitle, "获取图片失败！请联系管理员");
					}
				});
	
		popupWin = new Ext.Window({
					title : '图片查看',
					layout : 'fit',
					width : 800,
					minimizable:true,
   					maximizable:true,  
					resizable:true,
				//	maximized:true,
					height : 520,
					closeAction : 'hide',
					plain : true,
					modal : true,
					items : tableP
				});
		popupWin.on('hide', function() {
					tableP.destroy();
				});
		popupWin.show();
	}
	
	function refreshCusService(records){
			var curCusService = records[0].data.customerservice;
			Ext.Ajax.request({
				url:sysPath+'/sys/basCusServiceAction!list.action',
				params:{
					limit:pageSize,
					start:0,
					filter_EQL_departId:records[0].data.indepartid,
					filter_EQL_cusId:records[0].data.cusId
				},success:function(resp){
					var respText = Ext.util.JSON.decode(resp.responseText);
					if(respText.result.length!=1){
						Ext.Msg.alert(alertTitle,'该代理的客服员存在多个或者不存在，请联系系统管理员');
						return;
					}else{
						var cusservice = respText.result[0].serviceName;
						if(curCusService == cusservice){
							Ext.Msg.alert(alertTitle,'该条数据客服员与系统一致，如果需要同步，请分配客服员之后再同步!');
							return;
						}else{
							Ext.Ajax.request({
								url:sysPath+'/fax/oprFaxInAction!refreshCus.action',
								params:{
									dnos:records[0].data.dno
								},success:function(resp1){
									var respText1 = Ext.util.JSON.decode(resp1.responseText);
									Ext.Msg.alert(alertTitle,respText1.msg);
								}
							});
						}
					}
				}
			});
	}
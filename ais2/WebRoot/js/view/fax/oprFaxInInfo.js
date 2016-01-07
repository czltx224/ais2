
var gridRemarkUrl='stock/oprRemarkAction!list.action';
var saveUrl="stock/oprInformAction!saveInformAppointment.action";
var saveRequestUrl='stock/oprRequestDoAction!save.action';
var dictionaryUrl='sys/dictionaryAction!ralaList.action';
var gridRequestUrl='stock/oprRequestDoAction!list.action';
var saveRemarkUrl='stock/oprRemarkAction!save.action';
var gridInformDetailUrl='stock/oprInformDetailAction!findDetailByDno.action';//通知历史记录查询地址
var gridHistoryUrl='stock/oprHistoryAction!findHistoryByDno.action';//历史交易查询地址
var gridExceptionUrl='exception/oprExceptionAction!getAll.action';   //120
var ralaListUrl="stock/oprValueAddFeeAction!list.action";
	
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
					
Ext.onReady(function(){
		
		Ext.QuickTips.init();
		foo=dno;
		var tplWidth = Ext.lib.Dom.getViewWidth();
		var domWidth=Ext.lib.Dom.getViewWidth();
		var comWidth=domWidth/4;
		var scomWidth=domWidth/6;
		var commentWidth=110;
    	var domHeight=Ext.lib.Dom.getViewHeight();
		
	var fields=[
		'id','status','payType',
		'dno',  //配送单号
		'feeName',  //客商
		'feeCount',  
		'createTime',  //
		'createName',   //
		'updateTime',  //
		'updateName',   //
		'ts'];
		
        var jsonread= new Ext.data.JsonReader({root:'result',totalProperty:'totalCount'},fields);
        var jsonreadFax= new Ext.data.JsonReader({root:'resultMap',totalProperty:'totalCount'},fieldsFax);
		
			 //执行阶段
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
           reader:jsonread
	});
	
	var faxChangeStore = new Ext.data.Store({
           proxy: new Ext.data.HttpProxy({url:sysPath+"/fax/oprChangeAction!selectQuery.action"}),
           baseParams:{
	           	limit: pageSize,
	           	dno:foo
           },
           reader:jsonreadFax,
           sortInfo:{field:'createTime',direction:'ASC'}
	});
	
		
	var remarkStore= new Ext.data.Store({
			 storeId:'remarkStore'+foo,
   			 baseParams:{limit : pageSize,filter_EQL_dno:foo,privilege:75},
   			 proxy:new Ext.data.HttpProxy({url:sysPath+"/"+gridRemarkUrl}),
   			 sortInfo :{field: "createTime", direction: "DESC"},
   			 reader:new Ext.data.JsonReader({
            	root: 'result', totalProperty: 'totalCount'
       	 	}, ['id','remark','createName','createTime','updateName','updateTime','dno','ts'])
  			});
	
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
		        {header: '创建时间', dataIndex: 'createTime',width:80,sortable : true},
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
	
	var remarkGrid = new Ext.grid.GridPanel({
					id:'remarkGrid'+foo,
    				frame : false,
					border : true,
    				width:tplWidth-120,
    				region : 'center',
					heigth:280,
    				title:'备注记录',
    				autoScroll : true,
    				//autoSizeColumns: true,
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
		
    	var requestGrid = new Ext.grid.GridPanel({
    				id : "requestGrid"+foo,
    				region : 'center',
					heigth:280,
    				title:'个性化要求',
    				autoScroll : true,
    				autoDestroy :false,
    				//autoSizeColumns: true,
    				frame : false,
					border : true,
    				width:tplWidth-120,
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
		
	var historyDoStore=new Ext.data.Store({
	    			 baseParams:{limit : pageSize,filter_EQL_dno:foo,privilege:78},
	    			 proxy:new Ext.data.HttpProxy({url:sysPath+"/"+gridHistoryUrl}),
	    			 reader:new Ext.data.JsonReader({
		            	root: 'resultMap', totalProperty: 'totalCount'
		       	 	}, ['ID','OPRNAME','OPRNODE','OPRCOMMENT','OPRTIME','OPRMAN','OPRDEPART','DNO','OPRTYPE'])
    			});
   	var historyDoGrid = new Ext.grid.GridPanel({  
    				id : 'historyDoGrid'+foo,
    				region : 'right',
    				title:'历史操作记录',
    				autoScroll : true,
    				//autoSizeColumns: true,
    				frame : false,
					border : true,
    				width:tplWidth-120,
    				loadMask : true,
    				stripeRows : true,
					heigth:280,
					viewConfig:{
    				  forceFit: true
    				},
    				cm : new Ext.grid.ColumnModel([new Ext.grid.RowNumberer({
								header:'序号', width:20
							}),
    						{header : '操作节点名称',dataIndex : 'OPRNAME',sortable : true},
    						{header : '操作节点',dataIndex : 'OPRNODE',sortable : true},
    						{header : '操作内容',dataIndex : 'OPRCOMMENT',sortable : true},
    						{header : '操作时间',dataIndex : 'OPRTIME',sortable : true},
    						{header : '操作者',dataIndex : 'OPRMAN',sortable : true},
    						{header : '操作部门',dataIndex : 'OPRDEPART',sortable : true},
    						{header : '配送单号',dataIndex : 'DNO',sortable : true},
    						{header : '节点类型',dataIndex : 'OPRTYPE',sortable : true}
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
	
   		var exceptionGrid = new Ext.grid.GridPanel({  
    				id : 'exceptionGrid'+foo,
    				region : 'right',
    				title:'异常记录',
    				autoScroll : true,
    				//autoSizeColumns: true,
    				frame : false,
					border : true,
    				width:tplWidth-120,
    				loadMask : true,
    				stripeRows : true,
					heigth:280,
					viewConfig:{
						scrollOffset: 0,
						autoScroll:true
    				},
    				columns:[new Ext.grid.RowNumberer({
								header:'序号', width:20
							}),
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
			width:tplWidth-500,
			height:100,
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
							 		sortInfo :{field: "OPRTIME", direction: "ASC"},
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
				 remarkGrid,requestGrid,historyDoGrid,exceptionGrid,historyInformGrid,recordGrid,faxChangeGrid
			  ]
		})
		
   		tableP.setHeight(235);
		
		var fields=[{name:'dno',mapping:'dno'},'cpname','goodsstatus',
			'flightno','flightdate','curdepart','piece',
			'subno','consigneetel','goods','cusweight','cpSonderzugprice',
		    'consigneefee','cusvalueaddfee','cpvalueaddfee',
		    'gowhere','dismode','createtime','takemode',
		    'customerservice','receipttype','urgentservice',
		    'curstatus','stockpiece','stockweight','sonderzugprice',
		    'enddepart','curdepart','indepart','createtime',
		    'stockcreatetime','nodeorder','cusadd','trafee',
		    'customerName','confirmtime','flightmainno',  
		    'sumitDate','signsource','status','notifystatus',
		    'confirmstatus','cashstatus','fisurestatus',
		    'indepartid','dogooddate','reachstatus','cpfee','flightTime',
		    'getstatus','outstatus','scanstatus','realSignMan',
		    'printnum','distributiondepartid','remark','airportendtime',
		    'airportstarttime','carNoNum','bulk','phoneNoticeSign',
		    'oreachstatus','waitnotice','osuoutstatus','discreatetime',
		    'cusdepartname','oicreatename','paymentcollection','consigneeInfoString'];
				
		var jsonread= new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'},
                    fields);
		
		var formInfo = new Ext.form.FormPanel({
			/*
    				id:'addfaxform',
					frame : true,
					renderTo:Ext.getBody(),
					labelAlign : "right",
					bodyStyle:'padding:0px 0px 0px 0px',
					labelWidth : 60,
					width:tplWidth-102,
					height:320,
				*/	
					region : 'center',
					frame : true,
				//	url:sysPath+'/user/userAction!ralaList.action?privilege=17',
					//bodyStyle : 'padding:3 5 0',
				    width : Ext.lib.Dom.getViewWidth()-2,
				    height:Ext.lib.Dom.getViewHeight()-2,
				    autoHeight:true,
					reader :jsonread,
					border : true,
					items:[{
									xtype:"fieldset",
									title:"主单信息",
									layout:'table',
									id:'setmainmsg',
									//height:40,
									width:tplWidth-33,
									style:'margin:2px;',
									layoutConfig: {columns:5},
									bodyStyle:'padding:0px 0px 0px 0px',
									defaults:{border:false,layout:'form',frame:false,labelAlign:'right',labelWidth:65},
									items:[{
											colspan:1,
											width:(tplWidth)/5-10,
											items:[{
													fieldLabel : '配送单号',
													name : 'olist[0].dno',
													id:'dno',
													readOnly:true,
													xtype : 'numberfield', // 设置为数字输入框类型
													anchor : '95%',
													enableKeyEvents:true
												},{
													xtype:'textfield',
													fieldLabel:'班次号',
													id:'flightcombo',
													anchor:'95%',
													readOnly:true,
													name:'flightMainNo'
												}]
										},{
											colspan:1,
											width:(tplWidth)/5-10,
											items:[{
													xtype:'textfield',
													id:'cusName2',
													name:'cusName2',
													readOnly:true,
													fieldLabel:'代理公司',
													anchor:'95%'
												  },{
													xtype:'textfield',
													fieldLabel:'航空公司',
													id:'flightCompany',
													anchor:'95%',
													readOnly:true,
													name:'flightMainNo'
												}]
										},{
											colspan:1,
											width:(tplWidth)/5-10,
											items:[{
													xtype:'textfield',
													fieldLabel:'主单号',
													id:'flightMain2',
													anchor:'95%',
													readOnly:true,
													name:'flightMainNo2'
												},{
													xtype:'textfield',
													id:'startCity',
													name:'startCity',
													readOnly:true,
													fieldLabel:'起飞城市',
													anchor:'95%'
												}]
										},{
											colspan:1,
											width:(tplWidth)/5-10,
											items:[{
													xtype:'textfield',
													id:'trafficModecombo2',
													name:'trafficModecombo2',
													readOnly:true,
													fieldLabel:'运输方式',
													anchor:'95%'
												},{
													xtype:'textfield',
													fieldLabel:'起飞时间',
													id:'startTime',
													anchor:'95%',
													readOnly:true,
													name:'flightMainNo'
												}]
										},{
											colspan:1,
											width:(tplWidth)/5-10,
											items:[{
													xtype : 'textfield',
									    			id : 'flightDate',
										    		format : 'Y-m-d',
										    		name:'flightMainNo2',
										    		fieldLabel:'航班日期',
										    		anchor:'95%'
												},{
													xtype:'textfield',
													fieldLabel:'落地时间',
													id:'endTime',
													anchor:'95%',
													readOnly:true,
													name:'flightMainNo'
												}]
										}
										]
								}/*
										{
											colspan:1,
											width:(tplWidth)/5,
											items:[{
												xtype:'textfield',
												id:'sonderzugPrice',
												name:'sonderzugPrice',
												readOnly:true,
												fieldLabel:'专车费',
												anchor : '95%'
												}
											]
										},*/
									,{
									xtype:'fieldset',
									title:"配送单明细",
									id:'setdevtype',
									layout:'table',
								//	height:93,
									width:tplWidth-33,
									style:'margin:2px;',
									layoutConfig: {columns:4},
									bodyStyle:'padding:0px 0px 0px 0px',
									defaults:{border:false,layout:'form',frame:false,labelAlign:'right',labelWidth:80},
									items:[{
											colspan:1,
											width:(tplWidth)/4-12,
											items:[{
													xtype:'textfield',
													id:'subNo2',
													anchor:'95%',
													name:'subNo2',
													readOnly:true,
													fieldLabel:'分单号'
											},{
													xtype:'textfield',
													id:'sonderzug',
													anchor:'95%',
													name:'sonderzug',
													readOnly:true,
													fieldLabel:'是否专车'
											},{
													xtype:'textfield',
													fieldLabel:'收货人',
													readOnly:true,
													anchor:'95%',
													id:'name2',
													name:'name2',
													readOnly:true
											}]
										},{
											colspan:1,
											width:(tplWidth)/4-12,
											items:[{
													xtype:'textfield',
													id:'takeMode2',
													readOnly:true,
													anchor:'95%',
													name:'takeMode2',
													fieldLabel:'提货方式'
											},{
													xtype:'textfield',
													fieldLabel:'车型',
													id:'cartypecombo2',
													anchor : '95%',
													readOnly:true,
													name:'cartypecombo'
											},{
													xtype:'textfield',
													fieldLabel:'联系电话',
													anchor:'95%',
													readOnly:true,
													id:'phone2',
													name:'phone2'
											}]
										},{
											colspan:1,
											width:(tplWidth)/4-12,
											items:[{
													xtype:'textfield',
													id:'receiptType2',
													readOnly:true,
													anchor:'95%',
													name:'receiptType2',
													fieldLabel:'回单类型'
											},{
													xtype:'textfield',
													fieldLabel:'路型',
													id:'louType',
													anchor : '95%',
													readOnly:true,
													name:'louType'
											},{
													xtype:'textfield',
													fieldLabel:'市',
													readOnly:true,
													anchor:'95%',
													id:'areaName2',
													name:'areaName2'
											}]
										},{
											colspan:1,
											width:(tplWidth)/4-12,
											items:[{
												xtype:'textfield',
												fieldLabel:'配送方式',
												readOnly:true,
												anchor:'95%',
												id:'distributionMode2',
												name:'distributionMode2'
											},{
												xtype:'textfield',
												fieldLabel:'等通知放货',
												readOnly:true,
												anchor:'95%',
												id:'isUrgentStatus',
												name:'isUrgentStatus'
											},{
												xtype:'textfield',
												fieldLabel:'区/县',
												readOnly:true,
												id:'town2',
												anchor:'95%',
												name:'town2'
											}]
										},{
											colspan:1,
											width:(tplWidth)/4-12,
											items:[{
													xtype:'textfield',
													fieldLabel:'镇/街道',
													readOnly:true,
													id:'street2',
													anchor:'95%',
													name:'street2'
											}]
										},{
											colspan:1,
											width:(tplWidth)/4-12,
											items:[{
												xtype:'textfield',
												fieldLabel:'货物名称',
												id:'goods2',
												anchor:'95%',
												name:'goods2',
												readOnly:true
											 }]
										},{
											colspan:2,
											items:[{
												xtype:'textfield',
												fieldLabel:'收货地址',
												id:'cddress2',
												name:'addr2',
												readOnly:true,
												anchor:'97%'
												
											}]
										},
										{
											colspan:1,
											width:(tplWidth)/4-12,
											items:[{
												xtype:'textfield',
												fieldLabel:'计费方式',
												id:'valuationType',
												name:'valuationType',
												readOnly:true,
												anchor:'95%'
											},{
												xtype:'numberfield',
												id:'bulk',
												anchor:'95%',
												name:'bulk',
												fieldLabel:'体积',
												readOnly:true
											},{
												xtype:'textfield',
												id:'inDepart',
												anchor:'95%',
												name:'bulk',
												fieldLabel:'开单部门',
												readOnly:true
											}]
										},{
											colspan:1,
											width:(tplWidth)/4-12,
											items:[{
												xtype:'numberfield',
												id:'cusWeight',
												name:'cusWeight',
												readOnly:true,
												anchor:'95%',
												fieldLabel:'开单重量'
											},{
												xtype:'textfield',
												id:'whoCash',
												name:'whoCash',
												anchor:'95%',
												readOnly:true,
												fieldLabel:'付款方'
											},{
												xtype:'textfield',
												id:'disDepart',
												anchor:'95%',
												name:'bulk',
												fieldLabel:'配送部门',
												readOnly:true
											}]
										},{
											colspan:1,
											width:(tplWidth)/4-12,
											items:[{
												xtype:'textfield',
												id:'cqWeight',
												name:'cqWeight',
												readOnly:true,
												anchor:'95%',
												fieldLabel:'计费重量'
											},{
												xtype:'textfield',
												id:'goodStatus',
												name:'goodStatus',
												readOnly:true,
												anchor:'95%',
												fieldLabel:'货物状态'
											},{
												xtype:'textfield',
												id:'endDepart',
												anchor:'95%',
												name:'bulk',
												fieldLabel:'终端部门',
												readOnly:true
											}]
										},{
											colspan:1,
											width:(tplWidth)/4-12,
											items:[{
												xtype:'textfield',
												fieldLabel:'件数',
												id:'piece',
												anchor:'95%',
												readOnly:true,
												name:'piece'
											},{
												xtype:'textfield',
												id:'realGoWhere',
												name:'realGoWhere',
												readOnly:true,
												anchor:'95%',
												fieldLabel:'货物去向'
											},{
												xtype:'textfield',
												fieldLabel:'当前部门',
												name:'curdepart',
												anchor:'95%',
												id:'curdepart',
												readOnly:true
											 }]
										}]
								},{
									xtype:'fieldset',
									title:"费用成本信息",
									id:'setgoodss',
									layout:'table',
								//	height:93,
								//	hideLabel:true,
								    hideMode:'offsets',
									width:tplWidth-33,
									style:'margin:2px;',
									layoutConfig: {columns:4},
									bodyStyle:'padding:0px 0px 0px 0px',
									defaults:{border:false,layout:'form',frame:false,labelAlign:'right',labelWidth:80},
					 				items:[{
											colspan:1,
											width:(tplWidth)/4-12,
											items:[{
												xtype:'numberfield',
												id:'normCpRate',
												anchor:'95%',
												name:'normCpRate',
												fieldLabel:'预付公布价',
												readOnly:true
											},{
												xtype:'numberfield',
												id:'cpvalueaddfee',
												anchor:'95%',
												name:'cusvalueaddfee',
												fieldLabel:'预付增值费',
												readOnly:true
											},{
												xtype:'textfield',
												id:'traFeeRate',
												name:'traFeeRate',
												fieldLabel:'中转价',
												anchor:'95%',
												readOnly:true
											},{
												xtype:'textfield',
												id:'normSonderzugPrice',
												name:'normSonderzugPrice',
												fieldLabel:'专车公布价',
												readOnly:true,
												anchor:'95%'
											 },{
												xtype:'textfield',
												id:'cashStatus',
												name:'cashStatus',
												fieldLabel:'收银状态',
												readOnly:true,
												anchor:'95%'
											  },{
												xtype:'textfield',
												id:'payTraStatus',
												name:'payTraStatus',
												fieldLabel:'中转成本支付状态',
												readOnly:true,
												anchor:'95%'
											  }]
								
										},{
											colspan:1,
											width:(tplWidth)/4-12,
											items:[{
												xtype:'numberfield',
												id:'cpRate',
												anchor:'95%',
												name:'cpRate',
												fieldLabel:'预付实际价',
												readOnly:true
												
											},{
												xtype:'numberfield',
												fieldLabel:'预付提送费',
												id:'cpFee',
												anchor:'95%',
												name:'cpFee',
												readOnly:true
											},{
												xtype:'numberfield',
												fieldLabel:'中转费',
												name:'traFee',
												anchor:'95%',
												id:'traFee',
												readOnly:true
											 },{
												xtype:'numberfield',
												id:'cpSonderzugPrice',
												name:'cpSonderzugPrice',
												fieldLabel:'预付专车费',
												readOnly:true,
												value:0,
												anchor:'95%'
											  },{
												xtype:'textfield',
												fieldLabel:'收银员',
												name:'cashName',
												anchor:'95%',
												id:'cashName',
												readOnly:true
											 },{
												xtype:'textfield',
												id:'payTraTime',
												name:'payTraTime',
												fieldLabel:'中转成本支付时间',
												readOnly:true,
												anchor:'95%'
											  }]
								
										},{
											colspan:1,
											width:(tplWidth)/4-12,
											items:[{
												xtype:'numberfield',
												id:'normConsigneeRate',
												name:'normConsigneeRate',
												fieldLabel:'到付公布价',
												readOnly:true,
												anchor:'95%'
											 },{
												xtype:'numberfield',
												id:'cusvalueaddfee',
												name:'cpvalueaddfee',
												fieldLabel:'到付增值费',
												readOnly:true,
												anchor:'95%'
											  },{
												xtype:'numberfield',
												id:'paymentCollection',
												name:'paymentCollection',
												fieldLabel:'代收货款',
												anchor:'95%',
												readOnly:true
											 },{
												xtype:'numberfield',
												id:'sonderzugPrice',
												name:'sonderzugPrice',
												fieldLabel:'到付专车费',
												readOnly:true,
												anchor:'95%'
											  },{
												xtype:'textfield',
												fieldLabel:'收银时间',
												name:'cashTime',
												anchor:'95%',
												id:'cashTime',
												readOnly:true
											 },{
												xtype:'textfield',
												id:'serviceName',
												name:'serviceName',
												fieldLabel:'客服员',
												readOnly:true,
												anchor:'95%'
											  }]
								
										},{
											colspan:1,
											width:(tplWidth)/4-12,
											items:[{
												xtype:'numberfield',
												id:'consigneeRate',
												name:'consigneeRate',
												fieldLabel:'到付实际价',
												readOnly:true,
												anchor:'95%'
											},{
												xtype:'numberfield',
												id:'consigneeFee',
												name:'consigneeFee',
												fieldLabel:'到付提送费',
												readOnly:true,
												anchor:'95%'
											  },{
												xtype:'textfield',
												fieldLabel:'预付合计',
												name:'totalYuFee',
												anchor:'95%',
												id:'totalYuFee',
												readOnly:true
											 },{
												xtype:'textfield',
												fieldLabel:'到付合计',
												name:'totalDaoFee',
												anchor:'95%',
												id:'totalDaoFee',
												readOnly:true
											 },{
												xtype:'textfield',
												fieldLabel:'中转成本审核',
												name:'traAudit',
												anchor:'95%',
												id:'traAudit',
												readOnly:true
											 },{
												xtype:'textfield',
												id:'serviceDepartName',
												name:'serviceDepartName',
												fieldLabel:'客服部门',
												readOnly:true,
												anchor:'95%'
											  }] 
								
										}]
								},tableP]
	});

	/*	var panel = new Ext.Panel({
						layout : 'border',
						id:'mpanel',
				    //	autoWidth: true,
					    height: Ext.lib.Dom.getViewHeight()-2,
					    width : Ext.lib.Dom.getViewWidth()-2,
					    renderTo: Ext.getBody(),
					   // tbar:onebar,
						items : [formInfo]
						
					});*/
					
					var panel = new Ext.Panel({
						layout : 'border',
						id:'mpanel',
				    	autoWidth: true,
					    height: 770,
					    width : Ext.lib.Dom.getViewWidth(),
					    renderTo: Ext.getBody(),
					//    tbar:onebar,
						items : [formInfo]
						
			});
	
	Ext.Ajax.request({
			url : sysPath+'/fax/oprFaxInAction!inqueryOne.action',
			params:{
				dno:dno,
				rightDepart:rightDepart,
				start : 0,
				limit : 1
			},
			success : function(response) { // 回调函数有1个参数
				Ext.getCmp("trafficModecombo2").setValue(Ext.decode(response.responseText).trafficmode);
				Ext.getCmp("dno").setValue(Ext.decode(response.responseText).dno);
				Ext.getCmp("endTime").setValue(Ext.decode(response.responseText).flightTime);
				
				Ext.getCmp("flightMain2").setValue(Ext.decode(response.responseText).flightmainno);
				Ext.getCmp("flightcombo").setValue(Ext.decode(response.responseText).flightNo);
				Ext.getCmp("flightDate").setValue(Ext.decode(response.responseText).flightdateString);
				
		//		formInfo.findByType('radiogroup')[0].setValue(Ext.decode(response.responseText).sonderzug);
		//		formInfo.findByType('radiogroup')[1].setValue(Ext.decode(response.responseText).waitnotice);
		//		formInfo.findByType('radiogroup')[2].setValue(Ext.decode(response.responseText).urgentservice);
				if(Ext.decode(response.responseText).sonderzug=='1'){
					Ext.getCmp("sonderzug").setValue("是");
				}else{
					Ext.getCmp("sonderzug").setValue("否");
				}
		
				Ext.getCmp("cartypecombo2").setValue(Ext.decode(response.responseText).oicartype);
				Ext.getCmp("louType").setValue(Ext.decode(response.responseText).roadtype);
			//	Ext.getCmp("sonderzugPrice").setValue(Ext.decode(response.responseText).sonderzugprice);
				Ext.getCmp("subNo2").setValue(Ext.decode(response.responseText).subno);
				Ext.getCmp("takeMode2").setValue(Ext.decode(response.responseText).takemode);
				Ext.getCmp("receiptType2").setValue(Ext.decode(response.responseText).receipttype);
				Ext.getCmp("distributionMode2").setValue(Ext.decode(response.responseText).dismode);
				Ext.getCmp("phone2").setValue(Ext.decode(response.responseText).consigneetel);
				Ext.getCmp("name2").setValue(Ext.decode(response.responseText).consignee);
				Ext.getCmp("areaName2").setValue(Ext.decode(response.responseText).city);
				Ext.getCmp("town2").setValue(Ext.decode(response.responseText).town);
				
				Ext.getCmp("street2").setValue(Ext.decode(response.responseText).street);
				Ext.getCmp("cddress2").setValue(Ext.decode(response.responseText).addr);
				Ext.getCmp("goods2").setValue(Ext.decode(response.responseText).goods);
				Ext.getCmp("valuationType").setValue(Ext.decode(response.responseText).valuationtype);
				Ext.getCmp("piece").setValue(Ext.decode(response.responseText).piece);
				Ext.getCmp("bulk").setValue(Ext.decode(response.responseText).bulk);
				Ext.getCmp("cusWeight").setValue(Ext.decode(response.responseText).cusweight);
				Ext.getCmp("cpvalueaddfee").setValue(Ext.decode(response.responseText).cpvalueaddfee);
				Ext.getCmp("cusvalueaddfee").setValue(Ext.decode(response.responseText).cusvalueaddfee);
				Ext.getCmp("cqWeight").setValue(Ext.decode(response.responseText).cqweight);
				Ext.getCmp("paymentCollection").setValue(Ext.decode(response.responseText).paymentcollection);
				Ext.getCmp("whoCash").setValue(Ext.decode(response.responseText).whocash);
			//	Ext.getCmp("traFeeRate").setValue(Ext.decode(response.responseText).trafeerate);
				Ext.getCmp("traFee").setValue(Ext.decode(response.responseText).trafee);
				Ext.getCmp("cpRate").setValue(Ext.decode(response.responseText).cprate);
				Ext.getCmp("cpFee").setValue(Ext.decode(response.responseText).cpfee);
			//	Ext.getCmp("consigneeRate").setValue(Ext.decode(response.responseText).consigneerate);
				Ext.getCmp("consigneeFee").setValue(Ext.decode(response.responseText).consigneefee);
				Ext.getCmp("serviceDepartName").setValue(Ext.decode(response.responseText).serviceDepartName);
				Ext.getCmp("serviceName").setValue(Ext.decode(response.responseText).customerservice);
				Ext.getCmp("cashTime").setValue(Ext.decode(response.responseText).cashTime);
				Ext.getCmp("cashName").setValue(Ext.decode(response.responseText).cashName);
				
				Ext.getCmp("cpSonderzugPrice").setValue(Ext.decode(response.responseText).cpSonderzugprice);
				
				
				if(Ext.decode(response.responseText).cashstatus=='1'){
					Ext.getCmp("cashStatus").setValue("已收银");
				}else{
					Ext.getCmp("cashStatus").setValue("未收银");
				}
				
				if(Ext.decode(response.responseText).fiTraAudit=='1'){
					Ext.getCmp("traAudit").setValue("已审核");
				}else{
					Ext.getCmp("traAudit").setValue("未审核");
				}
				if(Ext.decode(response.responseText).traPayStatus=='1'){
					Ext.getCmp("payTraStatus").setValue("已支付");
				}else{
					Ext.getCmp("payTraStatus").setValue("未支付");
				}
				
				Ext.getCmp("payTraTime").setValue(Ext.decode(response.responseText).traPayTime);
				
				Ext.getCmp("inDepart").setValue(Ext.decode(response.responseText).indepart);
				Ext.getCmp("disDepart").setValue(Ext.decode(response.responseText).distributiondepartname);
				Ext.getCmp("endDepart").setValue(Ext.decode(response.responseText).enddepart);
				
				Ext.getCmp("curdepart").setValue(Ext.decode(response.responseText).curdepart);
				if(Ext.decode(response.responseText).trafficmode=='空运'){
					Ext.Ajax.request({
						url : sysPath+'/sys/basFlightAction!list.action',
						params:{
							privilege:62,
							filter_EQS_flightNumber:Ext.decode(response.responseText).flightNo,
							limit : 50
						},
						success : function(response2){
							if(Ext.decode(response2.responseText).result!=''){
								Ext.getCmp("flightCompany").setValue(Ext.decode(response2.responseText).result[0].cusName);
								Ext.getCmp("startCity").setValue(Ext.decode(response2.responseText).result[0].startCity);
								Ext.getCmp("startTime").setValue(Ext.decode(response2.responseText).result[0].standardStarttime);
								Ext.getCmp("endTime").setValue(Ext.decode(response2.responseText).result[0].standardEndtime);
							}else{
								Ext.MessageBox.alert(alertTitle, '查询不到航班号'+Ext.decode(response.responseText).flightNo+'的信息，请在航班信息管理模块维护');
							}
						},
						failure : function(response){
							Ext.MessageBox.alert(alertTitle, '查询航班信息失败');
						}
					});
				}

				Ext.Ajax.request({
					url : sysPath+'/fax/oprFaxInAction!list.action',
					params:{
						filter_EQL_dno:Ext.getCmp('dno').getValue()
					},
					success : function(response2){
						if(Ext.decode(response2.responseText).result!=''){
							Ext.getCmp("consigneeRate").setValue(Ext.decode(response2.responseText).result[0].consigneeRate);
							Ext.getCmp("normConsigneeRate").setValue(Ext.decode(response2.responseText).result[0].normConsigneeRate);  
							Ext.getCmp("normCpRate").setValue(Ext.decode(response2.responseText).result[0].normCpRate);
							Ext.getCmp("goodStatus").setValue(Ext.decode(response2.responseText).result[0].goodsStatus);
							if(Ext.decode(response2.responseText).result[0].wait=='1'){
								Ext.getCmp("isUrgentStatus").setValue("是");
							}else{
								Ext.getCmp("isUrgentStatus").setValue("否");
							}
							
							Ext.getCmp("cusName2").setValue(Ext.decode(response2.responseText).result[0].cpName);
							Ext.getCmp("realGoWhere").setValue(Ext.decode(response2.responseText).result[0].realGoWhere);
//							Ext.getCmp("normTraRate").setValue(Ext.decode(response2.responseText).result[0].normTraRate);  
							Ext.getCmp("traFeeRate").setValue(Ext.decode(response2.responseText).result[0].traFeeRate);  
							Ext.getCmp("sonderzugPrice").setValue(Ext.decode(response2.responseText).result[0].sonderzugPrice);   
							Ext.getCmp("normSonderzugPrice").setValue(Ext.decode(response2.responseText).result[0].normSonderzugPrice);
							
						//	var v3=parseFloat(totalCpValueAddFee)+parseFloat(inPaymentCollection)+parseFloat(consigneeFee);
                       //		Ext.getCmp('totalPaymentCollection').setValue(Math.ceil(v3));   // 总费用
							var cpFee=	Ext.getCmp('cpFee').getValue();    //预付提送费
							
							var cusvalueaddfee=	Ext.getCmp('cusvalueaddfee').getValue();  //预付增值费   consigneeFee   
							var consigneeFee=	Ext.getCmp('consigneeFee').getValue();
							var cpvalueaddfee=	Ext.getCmp('cpvalueaddfee').getValue();
							var sonderzugPrice=	Ext.getCmp('sonderzugPrice').getValue();
							var cpSonderzugPrice=	Ext.getCmp('cpSonderzugPrice').getValue();
//							if(Ext.getCmp('whoCash').getValue=='预付'){
							  	Ext.getCmp('totalYuFee').setValue(parseFloat(cpFee)+parseFloat(cpvalueaddfee)+parseFloat(cpSonderzugPrice));
//							  	Ext.getCmp('totalDaoFee').setValue(parseFloat(consigneeFee)+parseFloat(cusvalueaddfee));
//							}else{
//								Ext.getCmp('totalYuFee').setValue(parseFloat(cpFee)+parseFloat(cpvalueaddfee));
							  	Ext.getCmp('totalDaoFee').setValue(parseFloat(consigneeFee)+parseFloat(cusvalueaddfee)+parseFloat(sonderzugPrice));
//							}
							
						}else{
							Ext.MessageBox.alert(alertTitle, '查询费率失败');
						}
					},
					failure : function(response){
						Ext.MessageBox.alert(alertTitle, '查询费率失败');
					}
				});
			},
			failure : function(response) {
				Ext.MessageBox.alert(alertTitle, '主单信息数据查询失败');
				return;
			}
	});
	
	
	 	function addRemark(_records,foo){
 					var storeString = 'remarkGrid'+foo;
					var formRemark = new Ext.form.FormPanel({
								labelAlign : 'left',
								frame : true,
								url:sysPath+'/'+gridRemarkUrl,
								baseParams:{
				    				filter_EQL_id:_records,
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
								params:{privilege:75,limit : pageSize},
						waitMsg : '正在保存数据...',
						success : function(form, action) {
							win.hide(), Ext.Msg.alert(alertTitle,
									action.result.msg, function() {
										  Ext.getCmp(storeString).getStore().reload({
								 			sortInfo :{field: "createTime", direction: "DESC"},
											params : {
												start : 0,
												filter_EQS_dno:foo,
												privilege:75,
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
										 			sortInfo :{field: "createTime", direction: "DESC"},
													params : {
														start : 0,
														filter_EQS_dno:foo,
														privilege:75,
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
				    				filter_EQL_id:did,privilege:76},
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
													id:'trequestStage',
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
												privilege:76,
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
		

});

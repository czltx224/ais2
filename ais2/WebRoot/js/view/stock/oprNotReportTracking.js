//未到主单跟踪查询js
var privilege=255;//权限参数
var gridSearchUrl="stock/oprOvermemoDetailAction!findNotReportTracking.action";//未到主单查询地址
var dictionaryUrl='sys/dictionaryAction!ralaList.action';//数据字典地址
var authorityDepartUrl='sys/basRightDepartAction!findDepartName.action';//权限部门查询地址
var searchCusNameUrl='sys/customerAction!list.action';//查询代理公司地址
var searchFlightUrl='sys/basFlightAction!list.action';//航班查询地址
var gridTotalUrl='stock/oprOvermemoDetailAction!totalNotReportTracking.action';
var searchFlightMainNoUrl='fax/oprFaxInAction!inputQuery.action';//综合查询地址
var searchFaxDetailUrl='stock/oprOvermemoDetailAction!findNotReportTrackDetail.action';//传真表查询地址
var defaultWidth=80;
var searchDepartId=bussDepart;

var summary = new Ext.ux.grid.GridSummary();
var  fields=['FLIGHT_NO',
	  'FLIGHT_MAIN_NO',
	  'CP_NAME',
	  'PIECE',
	  'WEIGHT',
	  'CREATE_TIME',
	  'FLIGHT_TIME',
	  'START_CITY',
	  'OVERMEMO','NOTPIECE','CLOB_STATUS_REALPIECE',
	  'ROWNUM'];
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
		//航班查询
		flightNoStore= new Ext.data.Store({ 
			storeId:"flightNoStore",
			proxy:new Ext.data.HttpProxy({url:sysPath+"/"+searchFlightUrl}),
			reader:new Ext.data.JsonReader({
	                    root:'result', totalProperty:'totalCount'
	        },[
        	{name:'flightNumber', mapping:'flightNumber',type:'string'}])
		});
    	//权限部门
        authorityDepartStore = new Ext.data.Store({ 
            storeId:"authorityDepartStore",                        
            proxy: new Ext.data.HttpProxy({url:sysPath+"/"+authorityDepartUrl,method:'post'}),
            baseParams:{
            	privilege:63,
            	filter_EQL_userId:userId
            },
            reader: new Ext.data.JsonReader({
            root: 'resultMap', totalProperty: 'totalCount'
           }, [  {name:'departName', mapping:'DEPARTNAME',type:'string'},
            {name:'departId', mapping:'RIGHTDEPARTID'}             
              ])    
        });
   var startone = new Ext.form.DateField ({
    		id : 'startone',
    		format : 'Y-m-d',
    		emptyText : "开始时间",
    		value:new Date().add(Date.DAY, -7),
    		width : defaultWidth+40
    	});
    	
    var endone = new Ext.form.DateField ({
    		id : 'endone',
    		format : 'Y-m-d',
    		emptyText : "结束时间",
    		value:new Date(),
    		width : defaultWidth+40
    	});
	var tbar=[{text:'<b>导出</b>',iconCls:'sort_down',tooltip : '导出数据',
	 		handler:function(){
	 				parent.exportExl(overmemoGrid);
	 		}
	 	},'-',{text:'<b>查看详细信息</b>',iconCls:'userEdit',tooltip : '查看详细信息',
	 			handler:function(){
	 				var _records = overmemoGrid.getSelectionModel().getSelections();
					if (_records.length < 1) {
						Ext.Msg.alert(alertTitle, "请选择您查看的未到主单信息！");
						return false;
					} else if (_records.length > 1) {
						Ext.Msg.alert(alertTitle, "一次只能查看一条未到主单信息！");
						return false;
					}
					var flightMainNo = _records[0].get('FLIGHT_MAIN_NO');
		 			var node=new Ext.tree.TreeNode({leaf :false,text:'综合查询'});
			      	node.attributes={href1:searchFlightMainNoUrl+'?pub_flightMainNo='+flightMainNo};
			        parent.toAddTabPage(node,true);
	 			}
	 	},'时间选择:',{
			xtype : "combo",
			width : defaultWidth+20,
			triggerAction : 'all',
			forceSelection : true,
			model : 'local',
			hiddenId : 'checkItemsTime',
			hiddenName : 'checkItemsTime',
			store : [
					['f.create_time', '录单日期']],
			listeners: {   
				afterRender: function(combo) {   
		       　　combo.setValue('f.create_time');
				}
			},
			emptyText : '选择类型'
								
			},'-',
		'开始:',startone,' 至 ',endone];
	 	
	 	var queryTbar=new Ext.Toolbar([
	 	'-','主单号:',{xtype:'textfield',blankText:'交接单号',
	 	width:defaultWidth,
	 	id:'searchFlightMainNo', enableKeyEvents:true,listeners : {
	 		keyup:function(textField, e){
                     if(e.getKey() == 13){
                     	searchOvermemo();
                     }
	 		}
	 	}},'-','代理公司:',{ 
		 	xtype : 'combo',
			width:defaultWidth,
			id:'searchCusName',
			queryParam : 'filter_LIKES_cusName',
			triggerAction : 'all',
			store : cpNameStore,
			pageSize : comboSize,
			selectOnFocus:true,
			resizable:true,
			minChars : 0,
			mode : "remote",//从服务器端加载值
			valueField : 'cusId',//value值，与fields对应
			displayField : 'cusName',//显示值，与fields对应
			enableKeyEvents:true,
			listeners : {
	 		keyup:function(textField, e){
                if(e.getKey() == 13){
                	searchOvermemo();
                }
	 		}
	 	}},'-','航班号：',
		{xtype : 'combo',
			triggerAction : 'all',
			id:'searchFlightNumber',
			pageSize:comboSize,
			queryParam : 'filter_LIKES_flightNumber',
			store : flightNoStore,
			resizable:true,
			minChars : 0,
			//forceSelection : true,
			mode : "remote",//从服务器端加载值
			valueField : 'flightNumber',//value值，与fields对应
			displayField : 'flightNumber',//显示值，与fields对应
			editable : true,
			width:defaultWidth,
			listeners : {
	 		keyup:function(textField, e){
               if(e.getKey() == 13){
               	searchOvermemo();
               }
	 		}
	 	}
		},'-',
	{xtype:'textfield',blankText:'查询数据',
		id:'searchContent', 
		width:defaultWidth+20,
		enableKeyEvents:true,
		listeners : {
	 		keyup:function(textField, e){
               if(e.getKey() == 13){
               	searchOvermemo();
               }
	 		}
	 	}},'-',{ 
	 	
    					xtype : "combo",
    					width : defaultWidth+20,
    					triggerAction : 'all',
    					id:'searchSelectBox',
    					model : 'local',
    					hiddenId : 'checkItems',
    					hiddenName : 'checkItems',
    					name : 'checkItemstext',
    					store : [['LIKES_l_startCity','航班始发']],
    							
    					editable : false,
    					forceSelection : true,
    					listeners : {
    						'select' : function(combo, record, index) {
   								if(Ext.getCmp("searchContent").getValue().length>0){
   									searchOvermemo();
   								}
    						},
    						afterRender: function(combo) {   
					       　　combo.setValue('LIKES_l_startCity');
							}
    					}
    					
    				},'-',{text : '<b>查询</b>',id : 'btn',iconCls : 'btnSearch',
    					handler : searchOvermemo
    				}	
	 	]);	
	var countTbar=new Ext.Toolbar([
		'总票数：',{xtype:'textfield',
		id:'countNum', 
		width:defaultWidth-30,
		style:'background:gray',
		value:0,
		readOnly:true
		},
		'总应到件数：',{xtype:'textfield',
		id:'countPiece', 
		width:defaultWidth-30,
		style:'background:gray',
		value:0,
		readOnly:true
		},
		'总未到件数：',{xtype:'textfield',
		id:'countNotPiece', 
		width:defaultWidth-30,
		style:'background:gray',
		value:0,
		readOnly:true
		},
		'总重量：',{xtype:'textfield',
		id:'countWeight', 
		width:defaultWidth-30,
		style:'background:gray',
		value:0,
		readOnly:true
		}
	]);
		/**
		 * 汇总信息
		 */
		function fnSumInfo() {
			Ext.Ajax.request({
				url : sysPath+'/'+gridTotalUrl,
				params:dateStore.baseParams,
				success : function(response) {
					summary.toggleSummary(true);
					summary.setSumValue(Ext.decode(response.responseText));
				},
				failure : function(response) {
					Ext.MessageBox.alert(alertTitle, '汇总数据失败');
				}
			});
		}
 	/**
 	 *统计页面上的记录
 	 */
 	 function setCount(records){
 	 	if(null==records || 'undefined'==records){
 	 		Ext.getCmp('countNum').setValue(0);
 	 		Ext.getCmp('countPiece').setValue(0);
 	 		Ext.getCmp('countNotPiece').setValue(0);
 	 		Ext.getCmp('countWeight').setValue(0);
 	 		return;
 	 	}
 	 	var vcountNum=0;
 	 	var vcountPiece=0;
 	 	var vcountNotPiece=0;
 	 	var vcountWeight=0;
 	 	
 	 	for(var i=0;i<records.length;i++){
 	 		vcountNum++;
 	 		vcountPiece+=(records[i].data.PIECE==null?0:records[i].data.PIECE);
 	 		vcountNotPiece+=(records[i].data.NOTPIECE==null?0:records[i].data.NOTPIECE);
 	 		vcountWeight+=(records[i].data.WEIGHT==null?0:records[i].data.WEIGHT);
 	 	}
 	 	Ext.getCmp('countNum').setValue(vcountNum);
 	 	Ext.getCmp('countPiece').setValue(vcountPiece);
 	 	Ext.getCmp('countNotPiece').setValue(vcountNotPiece);
 	 	Ext.getCmp('countWeight').setValue(vcountWeight);
 	 }
Ext.onReady(function() {
  var mainNoExpander = new Ext.ux.grid.RowExpander({
        tpl : '<div id="dno_{ROWNUM}" style="height:200px;" ></div>',
        // expandOnDblClick : true,
        listeners : {
			'expand' : function(record, body, rowIndex){
		   				  dnofields=['D_NO','SUB_NO','FLIGHT_NO','FLIGHT_MAIN_NO','CONSIGNEE','ADDR','CONSIGNEE_TEL',
					    		   'CONSIGNEE_FEE','CP_NAME','PIECE','CUS_WEIGHT','END_DEPART','DISTRIBUTION_MODE','DISTRIBUTION_DEPART','CUR_DEPART',
					    		   'AREA_RANK','AREA_TYPE','REAL_GOWHERE','TRAFFIC_MODE','TAKE_MODE','REACH_STATUS'];//需要再加
					     dnoStore = new Ext.data.Store({
					        proxy: new Ext.data.HttpProxy({url:sysPath+"/"+searchFaxDetailUrl}),
					        baseParams:{
					        	limit: pageSize
					        },
					        sortInfo : {field: "D_NO", direction: "ASC"},
					        reader: new Ext.data.JsonReader({
					        			root: 'resultMap',
					                    totalProperty:'totalCount'},
					                    dnofields)
					        }); 
						 dnosm = new Ext.grid.CheckboxSelectionModel();
						  dnoGrid =new Ext.grid.GridPanel({
								height : 200,
								width : Ext.lib.Dom.getViewWidth()-50,
								autoScroll : true,  //面板上的body元素
							 	viewConfig : {
										scrollOffset: 0,
										autoScroll:true
								},
								frame : true,
								border : false,
								loadMask : true,
								sm:dnosm,
								stripeRows : true,
								columns:[dnosm,
											{header: '配送单号', dataIndex: 'D_NO',width:defaultWidth,sortable : true},
											{header: '点到状态', dataIndex: 'REACH_STATUS',width:defaultWidth,sortable : true,
												renderer:function(v){
													return v==1?'已点到':(v==2?'部分点到':'未点到');
												}
											},
									        {header: '主单号', dataIndex: 'FLIGHT_MAIN_NO',width:defaultWidth,sortable : true},
									        {header: '分单号', dataIndex: 'SUB_NO',width:defaultWidth,sortable : true},　
									        {header: '发货代理', dataIndex: 'CP_NAME',width:defaultWidth,hidden:true,sortable : true},
											{header: '件数', dataIndex: 'PIECE',width:defaultWidth,sortable : true},
											{header: '重量', dataIndex: 'CUS_WEIGHT',width:defaultWidth,sortable : true},
											{header: '收货人', dataIndex: 'CONSIGNEE',width:defaultWidth,sortable : true},
											{header: '收货人电话', dataIndex: 'CONSIGNEE_TEL',width:defaultWidth,sortable : true},
											{header: '收货人地址', dataIndex: 'ADDR',width:defaultWidth*2,sortable : true},
											{header: '配送方式', dataIndex: 'DISTRIBUTION_MODE',width:defaultWidth,sortable : true},
											{header: '提货方式', dataIndex: 'TAKE_MODE',width:defaultWidth,sortable : true},
											{header: '运输方式', dataIndex: 'TRAFFIC_MODE',width:defaultWidth,sortable : true},
											{header: '提送费', dataIndex: 'CONSIGNEE_FEE',width:defaultWidth,sortable : true},
											{header: '终端部门', dataIndex: 'END_DEPART',width:defaultWidth+20,sortable : true},
											{header: '配送部门', dataIndex: 'DISTRIBUTION_DEPART',width:defaultWidth+20,sortable : true}
									    ],
								store :dnoStore,
								bbar : new Ext.PagingToolbar({
									pageSize : pageSize, 
									store : dnoStore,
									displayInfo : true,
									displayMsg : '显示第 {0} 条到 {1} 条记录，共 {2} 条',
									emptyMsg : "没有记录信息显示"
								})
							});
		   				
			   			dnoStore.reload({params : {filter_flightMainNo:body.get('FLIGHT_MAIN_NO')}});
			   			dnoGrid.render("dno_"+body.get('ROWNUM'));
					}
			 }
    });
   	dateStore = new Ext.data.Store({
        storeId:"dateStore",
        proxy: new Ext.data.HttpProxy({url : sysPath+ "/"+gridSearchUrl}),
        baseParams:{privilege:privilege,limit : pageSize},
        // summary.toggleSummary(true);
        // summary.setSumValue(Ext.decode(totalObject));
        reader: new Ext.data.JsonReader({
            root: 'resultMap', totalProperty: 'totalCount'
        }, fields)	
    });
   
   var sm = new Ext.grid.CheckboxSelectionModel();
	 var rowNum = new Ext.grid.RowNumberer({
        header:'序号', width:20
    });
    overmemoGrid = new Ext.grid.GridPanel({
        renderTo: Ext.getBody(),
        id:'overmemoCenter',
        height:Ext.lib.Dom.getViewHeight()-1,		
        width:Ext.lib.Dom.getViewWidth()-1,
		autoScroll : true,
		//autoSizeColumns: true,
		viewConfig : {
			columnsText : "显示的列",
			sortAscText : "升序",
			sortDescText : "降序",
			forceFit : true
		},
		frame : false,
		loadMask : true,
		stripeRows : true,
		plugins : [summary,mainNoExpander], // 合计
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		},
        sm:sm,
        cm:new Ext.grid.ColumnModel([mainNoExpander,rowNum,sm,
       			{header: 'ROWNUM', dataIndex: 'ROWNUM',width:defaultWidth+20,sortable : true},
       			{header: '主单号', dataIndex: 'FLIGHT_MAIN_NO',width:defaultWidth+20,sortable : true},
 				{header: '到车状态', dataIndex: 'CLOB_STATUS_REALPIECE',width:200,sortable : true},
       			{header: '代理公司', dataIndex: 'CP_NAME',width:defaultWidth+50,sortable : true},
       			{header: '录单日期', dataIndex: 'CREATE_TIME',width:defaultWidth,sortable : true},
       			{header: '航班号', dataIndex: 'FLIGHT_NO',width:defaultWidth,sortable : true},
       			{header: '航班落地时间', dataIndex: 'FLIGHT_TIME',width:defaultWidth,sortable : true},
       			{header: '应到件数', dataIndex: 'PIECE',width:defaultWidth,sortable : true},
       			{header: '未到件数', dataIndex: 'NOTPIECE',width:defaultWidth,sortable : true},
 				{header: '主单重量', dataIndex: 'WEIGHT',width:defaultWidth,sortable : true},
 				{header: '航班始发', dataIndex: 'START_CITY',width:defaultWidth,sortable : true}
        ]),
        store:dateStore,
        tbar: tbar,
        listeners: {
            render: function(){
                queryTbar.render(this.tbar);
                countTbar.render(this.tbar);
            },
            rowclick:function(grid,e){
            	setCount(this.getSelectionModel().getSelections());
            }
        },
        bbar: new Ext.PagingToolbar({
            pageSize: pageSize, 
            privilege:privilege,
            store: dateStore, 
            displayInfo: true,
            displayMsg: '显示第 {0} 条到 {1} 条记录，共 {2} 条', 
            emptyMsg: "没有记录信息显示",
            plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
            items : ['-', '&nbsp;&nbsp;', '-', {
				text : '合计',
				iconCls : 'addIcon',
				handler : function() {
					summary.toggleSummary();
				}
			}]
        })
    });
  });
	
   function searchOvermemo() {
		dateStore.baseParams={privilege:privilege,limit : pageSize};
		
				if(!Ext.getCmp('startone').isValid()){
					Ext.Msg.alert(alertTitle,'开始时间格式不正确!');
					return;
				}
				if(!Ext.getCmp('endone').isValid()){
					Ext.Msg.alert(alertTitle,'结束时间格式不正确!');
					return;
				}
				
				var startCount = Ext.get('startone').dom.value;
				var endCount = Ext.get('endone').dom.value;
				var searchFlightMainNo = Ext.getCmp('searchFlightMainNo').getValue();
				var searchCusName = Ext.getCmp('searchCusName').getRawValue();
				var searchFlightNumber = Ext.getCmp('searchFlightNumber').getRawValue();
				var countCheckItems = Ext.get('checkItemsTime').dom.value;

	 			 Ext.apply(dateStore.baseParams, {
	 			 		filter_countCheckItems:countCheckItems,
				 	 	filter_startCount:startCount,
				 	 	filter_endCount:endCount,
				 	 	filter_LIKES_f_flightNo:searchFlightNumber.toUpperCase(),
				 	 	filter_LIKES_f_cpName:searchCusName,
				 	 	filter_f_inDepartId:bussDepart,
    					filter_LIKES_f_flightMainNo:searchFlightMainNo,
						filter_checkItems : Ext.get("checkItems").dom.value,
						filter_itemsValue : Ext.get("searchContent").dom.value
    			});
	 			
		dataReload();
		fnSumInfo();
		setCount();
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

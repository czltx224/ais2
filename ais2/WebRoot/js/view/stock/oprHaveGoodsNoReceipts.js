	//有货无单单据匹配管理js
	var matchingUrl="stock/haveGoodsNoReceiptsAction!saveMatching.action";//有货无单单据匹配地址
	var privilege=108;//权限参数
	var gridSearchUrl="/stock/haveGoodsNoReceiptsAction!findNoFaxList.action";//查询地址
	var saveUrl="stock/oprSignAction!saveSignStatus.action";
	var customerGridSearchUrl="sys/customerAction!list.action";
	var areaListUrl="stock/oprStoreAreaAction!list.action";
	var searchFlightMainNoUrl='fax/oprFaxInAction!list.action';//传真表查询地址
	var booleanId =false; 
	var defaultWidth=70;
	 var fields=[{name:"overmemo",mapping:'OVERMEMO'},
    			{name:"id",mapping:'ID'},
    			{name:"dno",mapping:'D_NO'},
    			{name:"piece",mapping:'PIECE'},
    			{name:"weight",mapping:'WEIGHT'},
    			{name:"status",mapping:'STATUS'},
    			{name:"startDepartId",mapping:'START_DEPART_ID'},
    			{name:"startDepartName",mapping:'START_DEPART_NAME'},
    			{name:"startTime",mapping:'START_TIME'},
    			{name:"endTime",mapping:'END_TIME'},
    			{name:"flightMainNo",mapping:'FLIGHT_MAIN_NO'},
    			{name:"remark",mapping:'REMARK'}
		];
    var fields2=[{name:"flightNo",mapping:'T1_FLIGHT_NO'},
    			{name:'flightMainNo',mapping:'T0_FLIGHT_MAIN_NO'},
    			{name:'flightDate',mapping:'T1_FLIGHT_DATE'},
    			{name:'cpName',mapping:'T1_CP_NAME'},
    			{name:'cusName',mapping:'T2_CUS_NAME'},
    			{name:'id',mapping:'ID'},
    			{name:'startCity',mapping:'STARTCITY'},
    			{name:'totalPiece',mapping:'TOTAL_PIECE'},
    			{name:'totalWeight',mapping:'TOTAL_WEIGHT'},
    			{name:'realWeight',mapping:'REAL_WEIGHT'},
    			{name:'ts',mapping:'TS'},
    			{name:'departName',mapping:'DEPART_NAME'}];
	
Ext.onReady(function(){
	Ext.QuickTips.init();
var rownum=new Ext.grid.RowNumberer({
		header:'序号',
        width : 35,
		sortable : true	
	});
	
	var rownum2=new Ext.grid.RowNumberer({
		header:'序号',
        width : 35,
		sortable : true	
	});

    var jsonread= new Ext.data.JsonReader({
                    root:'resultMap',
                    totalProperty:'totalCount'},
                    fields);
     
    var jsonread2= new Ext.data.JsonReader({
                    root:'resultMap',
                    totalProperty:'totalCount'},
                    fields2);
                              
	var sm = new Ext.grid.CheckboxSelectionModel({
		width:25,
		moveEditorOnEnter:true, 
		id:'checkId',
		singleSelect :true,
		name:'checkId'
	});

	var sm2 = new Ext.grid.CheckboxSelectionModel({
		width:25,
		singleSelect :true,
		moveEditorOnEnter:true, 
		id:'checkId2',
		name:'checkId2'
	});
	
	var dataStore = new Ext.data.Store({
                proxy: new Ext.data.HttpProxy({url:sysPath+gridSearchUrl}),
                baseParams:{
                	limit: pageSize,
                	privilege:privilege
                },
                sortInfo : {field: "id", direction: "ASC"},
                reader:jsonread
    });
	var copyDataStore = new Ext.data.Store({
              proxy: new Ext.data.HttpProxy({url:sysPath+"/fax/oprFaxMainAction!findAll.action"}),
              baseParams:{limit: pageSize},
              sortInfo : {field: "id", direction: "ASC"},
              reader:jsonread2
    }); 

	var expander = new Ext.grid.RowExpander({
		tpl : new Ext.Template(//'<p style=margin-left:70px;><span style=color:Teal;>项目ID</span><br><span>{xmid}</span></p>',
				'<p style=margin-left:70px;><span style=color:Teal;>备注</span><br><span>{remark}</span></p>'),
		// 设置行双击是否展开
		expandOnDblClick : true
	});
	var mainNoExpander = new Ext.ux.grid.RowExpander({
        tpl : '<div id="dno_{id}" style="height:200px;" ></div>',
        // expandOnDblClick : true,
        listeners : {
			'expand' : function(record, body, rowIndex){
		   				  dnofields=['dno','subNo','flightNo','flightMainNo','consignee','addr','consigneeTel',
					    		   'consigneeFee','cpName','piece','cusWeight','endDepart','distributionMode','distributionDepart','curDepart',
					    		   'areaRank','areaType','realGoWhere','trafficMode','takeMode'];//需要再加
					     dnoStore = new Ext.data.Store({
					        proxy: new Ext.data.HttpProxy({url:sysPath+"/"+searchFlightMainNoUrl}),
					        baseParams:{
					        	limit: pageSize
					        },
					        sortInfo : {field: "dno", direction: "ASC"},
					        reader: new Ext.data.JsonReader({
					        			root: 'result',
					                    totalProperty:'totalCount'},
					                    dnofields)
					        }); 
						 dnosm = new Ext.grid.CheckboxSelectionModel();
						  dnoGrid =new Ext.grid.GridPanel({
								height : 200,
								width : Ext.lib.Dom.getViewWidth()*0.5-50,
								autoScroll : true,  //面板上的body元素
							 	viewConfig : {
										scrollOffset: 0,
										autoScroll:true
								},
								frame : true,
								loadMask : true,
								sm:dnosm,
								stripeRows : true,
								columns:[dnosm,
											{header: '配送单号', dataIndex: 'dno',width:defaultWidth,sortable : true},
									        {header: '主单号', dataIndex: 'flightMainNo',width:defaultWidth,sortable : true},
									        {header: '分单号', dataIndex: 'subNo',width:defaultWidth,sortable : true},　
									        {header: '发货代理', dataIndex: 'cpName',width:defaultWidth,hidden:true,sortable : true},
											{header: '件数', dataIndex: 'piece',width:defaultWidth,sortable : true},
											{header: '重量', dataIndex: 'cusWeight',width:defaultWidth,sortable : true},
											{header: '收货人', dataIndex: 'consignee',width:defaultWidth,sortable : true},
											{header: '收货人电话', dataIndex: 'consigneeTel',width:defaultWidth,sortable : true},
											{header: '收货人地址', dataIndex: 'addr',width:defaultWidth*2,sortable : true},
											{header: '配送方式', dataIndex: 'distributionMode',width:defaultWidth,sortable : true},
											{header: '提货方式', dataIndex: 'takeMode',width:defaultWidth,sortable : true},
											{header: '运输方式', dataIndex: 'trafficMode',width:defaultWidth,sortable : true},
											{header: '提送费', dataIndex: 'consigneeFee',width:defaultWidth,sortable : true},
											{header: '终端部门', dataIndex: 'endDepart',width:defaultWidth+20,sortable : true},
											{header: '配送部门', dataIndex: 'distributionDepart',width:defaultWidth+20,sortable : true}
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
		   				
			   			dnoStore.reload({params : {filter_EQS_flightMainNo:body.get('flightMainNo')}});
			   			dnoGrid.render("dno_"+body.get('id'));
					}
			 }
    });
		var adminRadio=new Ext.form.Radio({
	        boxLabel:'精确查找',
	        inputValue:'1',
	        checked:true,
	        listeners:{
	            'check':function(){
	                //alert(adminRadio.getValue());
	                if(adminRadio.getValue()){
	                    userRadio.setValue(false);
	                    adminRadio.setValue(true);
	                	Ext.getCmp('two').disable();
	                	//findAll();
	                	var f =Ext.getCmp('userCenter');
	                	var record = f.getSelectionModel().getSelections();
						findSearch(record);
	                }
	            }
           }
	    });
	    var userRadio=new Ext.form.Radio({
	        boxLabel:'模糊查找',
	        inputValue:'0',
	        listeners:{
	            'check':function(){
	                if(userRadio.getValue()){
	                    adminRadio.setValue(false);
	                    userRadio.setValue(true);
	                    Ext.getCmp('two').enable();
	                   // findAll();
	                    var f =Ext.getCmp('userCenter');
	                    var record = f.getSelectionModel().getSelections();
						findSearch(record);
	                }
	            }
	        }
	    });
		
 		var twobar = new Ext.Toolbar({
 				id:'twobar',
		    	items : ['&nbsp;','<B>主单号</B>'
		    			 ,'-','&nbsp;',
		    		    adminRadio,'&nbsp;&nbsp;&nbsp;',userRadio,'&nbsp;','跨度:',
  		    		   {xtype : 'numberfield',
       	                id:'two',
       	                disabled:true,
        			    name: 'two',
        			    maxLength : 2,
		                width:30,
		                value:0,
		                enableKeyEvents:true,
		                listeners : {
			    			'blur' : function(f) {
			    			   if(f.getValue()==''){
									f.setValue(0);
			    			   }
			    		     }
		    		    }
		              },'-','&nbsp;&nbsp;','是否点到：',{
		              	xtype : 'checkbox',
		              	id:'reachFlag'
		              }]
		});

      	var threebar = new Ext.Toolbar({
		    	items : ['&nbsp;','<B>系统主单号</B>','-',
		 							   	'&nbsp;','主单号:',
						              　　{xtype : 'textfield',
			        	                id:'flightMainNo',
				        			    name: 'flightMainNo',
				        			    maxLength : 10,
						                width:(Ext.lib.Dom.getViewWidth()*0.5-100)*0.1,
						                enableKeyEvents:true,
						                listeners : {
							    			'keyup' : function(numberfield, e){
							                     if(e.getKey() == 13 ){
													findByDetail();
							                     }
							 			 	}
						    		    }
						            },'&nbsp;','航班号:',
						              　　{xtype : 'textfield',
			        	                id:'flightNo',
				        			    name: 'flightNo',
				        			    maxLength : 10,
						                width:(Ext.lib.Dom.getViewWidth()*0.5-100)*0.1,
						                enableKeyEvents:true,
						            	listeners : {
							    			'keyup' : function(numberfield, e){
							                     if(e.getKey() == 13 ){
													findByDetail();
							                     }
							 			 	}
						    		    }
						            },'&nbsp;','件数:',
    	   				 			　　{xtype : 'numberfield',
			        	                fieldLabel: '件数',
			        	                id:'piece',
				        			    name: 'piece',
				        			    maxLength : 10,
						                width:(Ext.lib.Dom.getViewWidth()*0.5-100)*0.1,
						                enableKeyEvents:true,
						            	listeners : {
							    			'keyup' : function(numberfield, e){
							                     if(e.getKey() == 13 ){
													findByDetail();
							                     }
							 			 	}
						    		    }
						              },'&nbsp;','重量:',
								    	{xtype : 'numberfield',
			        	                fieldLabel: '重量',
			        	                id:'weight',
				        			    name: 'weight',
				        			    maxLength : 10,
						                width:(Ext.lib.Dom.getViewWidth()*0.5-100)*0.1,
						                enableKeyEvents:true,
						                listeners : {
							    			'keyup' : function(numberfield, e){
							                     if(e.getKey() == 13 ){
													findByDetail();
							                     }
							 			 	}
						    		    }
						              }]
		});
		
	 var summary = new Ext.ux.grid.GridSummary();
     var summary2 = new Ext.ux.grid.GridSummary();
     
     var recordGrid=new Ext.grid.EditorGridPanel({
     	id:'userCenter',
		width : Ext.lib.Dom.getViewWidth()*0.5,
		height : Ext.lib.Dom.getViewHeight(),
		autoScroll : true,  //面板上的body元素
	 	viewConfig : {
				scrollOffset: 0,
				autoScroll:true
		},
		frame : true,
		border : true,
		//enableColumnResize:false, //关闭列的自适大小功能
		autoWidth:false,
	//	clicksToEdit:1,
		plugins : [expander],
		loadMask : true,
		sm:sm,
		tbar:twobar,
		stripeRows : true,
		columns:[ expander,sm,
        			{header: '编号', dataIndex: 'id',width:55,sortable : true},
					{header: '配送单号', dataIndex: 'dno',width:55,sortable : true},
			        {header: '点到状态', dataIndex: 'status',width:60,sortable : true,
				       		 renderer:function(v){
				        		if(v=='1'){ 
				        			return '已点到';
				        		}else if(v=='2'){ 
				        			return '部分点到';
				        		}else{
				        			return '未点到';
				        		}
				        	}
				     },
			        {header: '主单号', dataIndex: 'flightMainNo',width:70,sortable : true},
			        {header: '发货代理', dataIndex: 'cpName',width:70,hidden:true,sortable : true},
			        {header: '始发部门', dataIndex: 'startDepartName',width:80,sortable : true},　
					{header: '件数', dataIndex: 'piece',width:50,sortable : true},
					{header: '重量', dataIndex: 'weight',width:60,sortable : true},
					{header: '到车日期', dataIndex: 'endTime',width:100,sortable : true},
					{header: '备注', dataIndex: 'remark',width:100,sortable : true}
			    ],
		store :dataStore,
		listeners : {
			'rowclick' : function(f,rowIndex,e){
				var record = recordGrid.getSelectionModel().getSelections();
				findSearch(record);
			}
		},
		bbar : new Ext.PagingToolbar({
					pageSize : pageSize, 
					store : dataStore,
					displayInfo : true,
					displayMsg : '显示第 {0} 条到 {1} 条记录，共 {2} 条',
					emptyMsg : "没有记录信息显示"
		})
		
	});
     
        var recordGrid2=new Ext.grid.GridPanel({
        	id:'userCenter2',
			height : 10,
			width : Ext.lib.Dom.getViewWidth()*0.5-2,
			height : Ext.lib.Dom.getViewHeight(),
			autoScroll : true,  //面板上的body元素
			viewConfig : {
				scrollOffset: 0,
				autoScroll:true
			},
			frame : true,
			border : true,
		//	enableColumnResize:false, //关闭列的自适大小功能
			autoWidth:false,
			plugins : [mainNoExpander],
			loadMask : true,
			sm:sm2,
			tbar:threebar,
			stripeRows : true,
			columns:[mainNoExpander,sm2,rownum2,
					{header: '编号', dataIndex: 'id',width:60},
			        {header: '主单号', dataIndex: 'flightMainNo',width:60,sortable : true},
			        {header: '始发站', dataIndex: 'startCity',width:60,sortable : true},
			        {header: '件数', dataIndex: 'totalPiece',width:60},
			        {header: '重量', dataIndex: 'totalWeight',width:60,sortable : true},
			        {header: '真实重量', dataIndex: 'realWeight',width:80,sortable : true},
			        {header: '代理名称', dataIndex: 'cpName',width:100,sortable : true},
			        {header: '提货处', dataIndex: 'cusName',width:100,sortable : true},
			        {header: '航班号', dataIndex: 'flightNo',width:100,sortable : true},
			        {header: '航班时间', dataIndex: 'flightDate',width:100,sortable : true},
			        {header: '部门名称', dataIndex: 'departName',width:100,sortable : true},
			        {header: '时间戳', dataIndex: 'ts',width:60,hidden:true,sortable : true}
			    ],
			store : copyDataStore,
			bbar : new Ext.PagingToolbar({
					id:'toolBar2',
					pageSize : pageSize, 
					store : copyDataStore,
					displayInfo : true,
					displayMsg : '显示第 {0} 条到 {1} 条记录，共 {2} 条',
					emptyMsg : "没有记录信息显示"
					
			})
	});

      var form = new Ext.form.FormPanel({
								labelAlign : 'left',
								frame : true,
								border : true,
								renderTo:Ext.getBody(),
								bodyStyle : 'padding:0px 0px 0px',
							    height : Ext.lib.Dom.getViewHeight(),
								width : Ext.lib.Dom.getViewWidth(),
								labelAlign : "right",
									tbar:['&nbsp;',{
										text : '<B>手工匹配</B>',
										id : 'submitbtn',
										tooltip : '手工匹配',
										iconCls:'userEdit',
										handler : sumitStore
												
									},'-','&nbsp;','<B>到车日期：</B>',
									  { xtype : 'datefield',
							    		id : 'startDate',
							    		format : 'Y-m-d',
							    		emptyText : "开始时间",
							    		value:new Date().add(Date.DAY, -7),
							    		width : 100,
							    		listeners : {
							    			'select' : function() {
							    			   var start = Ext.getCmp('startDate').getValue()
							    			      .format("Y-m-d");
							    			   Ext.getCmp('endDate').setMinValue(start);
							    		     }
							    		}
						    		},'&nbsp;','<B>至</B>','&nbsp;',{
							    		xtype : 'datefield',
							    		id : 'endDate',
							    		value:new Date(),
							    		format : 'Y-m-d',
							    		emptyText : "结束时间",
							    		width : 100,
							    		enableKeyEvents:true
						    	    },'&nbsp;','<B>主单号:</B>',
						              　　{xtype : 'textfield',
			        	                id:'flightMainNo2',
				        			    name: 'flightMainNo2',
				        			    maxLength : 10,
						                width:80,
						                enableKeyEvents:true,
						                listeners:{
						                	keyup:function(f,e){
						                		if(e.getKey()==13){
						                			findAll();
						                		}
						                	}
						                }
						            },'-','&nbsp;',{
				    				     text : '<b>查询</b>',
				    				     id : 'btn2',
				    				     iconCls : 'btnSearch',
				    					 handler : findAll
				    				  },'-','&nbsp;&nbsp;',{
							    			xtype:'label',
							    			id:'showMsg',
							    			width:380
							    }],
						        items : [{
											layout : 'column',
											items : [{
														columnWidth : 0.5,
														layout : 'form',
														items : [recordGrid]

													},{
														columnWidth : 0.5,
														layout : 'form',
														items : [recordGrid2]
													}]
													
										}]
					});
		form.render();
		recordGrid.setHeight(Ext.lib.Dom.getViewHeight()-35);
		recordGrid2.setHeight(Ext.lib.Dom.getViewHeight()-35);
		
		function findSearch(record){
			if(record.length==0){
					copyDataStore.removeAll();
				}else{
				
					if(Ext.getCmp('two').validate()){
						
						if(userRadio.getValue()){
							Ext.apply(copyDataStore.baseParams={
					 	 		piece:record[0].data.piece,
					 	 		weight:record[0].data.weight,
								xvalue:Ext.getCmp('two').getValue(),
								flightMainNo:record[0].data.flightMainNo
							});
						
						}else{
							Ext.apply(copyDataStore.baseParams={
					 	 		//piece:record[0].data.piece,
					 	 		//weight:record[0].data.piece,
					 	 		flightMainNo:record[0].data.flightMainNo
							});
						}
						copyDataStore.reload({
							params : {
								start : 0,
								limit : pageSize
							}
						});
						
					}else{
						Ext.getCmp('showMsg2').getEl().update('<span style="color:red">模糊查询跨度不宜过大，请输入一个两位数。</span>');
					}
				
				}
		}
		function findAll(){
			var nof =Ext.getCmp('flightMainNo2').validate();
				
			Ext.apply(dataStore.baseParams,{
				filter_countCheckItems:'end_time',
				filter_startCount:(!Ext.getCmp("startDate").hidden?(Ext.get("startDate").dom.value=="开始时间"?"":Ext.get("startDate").dom.value):""),
				filter_endCount : (!Ext.getCmp("endDate").hidden?(Ext.get("endDate").dom.value=="结束时间"?"":Ext.get("endDate").dom.value):""),
	 	 		filter_flightMainNo:Ext.getCmp('flightMainNo2').getValue()
			});
			
			Ext.apply(copyDataStore.baseParams,{
	 	 		flightMainNo:Ext.getCmp('flightMainNo2').getValue()
			});
			dataStore.reload({
				params : {
					start : 0,
					limit : pageSize
				}
			});
			/*
			copyDataStore.reload({
					params : {
						start : 0,
						limit : pageSize
					}
			});
			*/
		}
		function sumitStore(){
			Ext.getCmp('showMsg').getEl().update('<span style="color:red"></span>');
			var records = recordGrid.getSelectionModel().getSelections();
		  	var records2 = recordGrid2.getSelectionModel().getSelections();
			if(records.length<1){
				Ext.getCmp('showMsg').getEl().update('<span style="color:red">进行手工匹配，必须选择一条黄单记录</span>');
				return;
			}
			if(records2.length<1){
				Ext.getCmp('showMsg').getEl().update('<span style="color:red">进行手工匹配，必须选择一条主单记录</span>');
				return;
			}
			Ext.Msg.confirm(alertTitle,'您确定要手工匹配这票货物吗?',function(btnYes) {
						if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
							var datas="";
							var reachFlag = Ext.getCmp('reachFlag').checked;
							for(var i=0;i<records.length;i++){
								datas+="mathList["+i+"].dno="+records[i].data.dno+'&'
									 +"mathList["+i+"].id="+records[i].data.id+'&'
									 +"mathList["+i+"].flightMainNo="+records2[0].data.flightMainNo+'&'
									 +"mathList["+i+"].mainId="+records2[0].data.id+'&'
									 +"mathList["+i+"].overmemoNo="+records[0].data.overmemo+'&'
									 +"mathList["+i+"].reachFlag="+reachFlag;
								if(i!=records.length-1){
									datas+='&';
								}
							}
							Ext.Ajax.request({
									url : sysPath+ "/"+matchingUrl,
									method : 'post',
									params : datas,
									waitMsg : '正在保存匹配数据...',
									success : function(resp) {
										var jdata = Ext.util.JSON.decode(resp.responseText);
										if(jdata.success){
											Ext.Msg.alert(alertTitle,"匹配成功！");
											findAll();
										}else{
											Ext.Msg.alert(alertTitle,jdata.msg);
										}
									},
									failure : function(resp) {
										var jdata = Ext.util.JSON.decode(resp.responseText);
										Ext.Msg.alert(alertTitle,jdata.msg);
									}
								});
						}
					});
		}
});
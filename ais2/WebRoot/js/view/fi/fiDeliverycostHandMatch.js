	Ext.QuickTips.init();
	var privilege=74;
	var comboxPage=comboSize;
	var saveUrl="stock/oprSignAction!saveSignStatus.action";
	var customerGridSearchUrl="sys/customerAction!list.action";
	var areaListUrl="stock/oprStoreAreaAction!list.action";
	var ralaListUrl="stock/oprPrewiredDetailAction!ralaList.action";
	var booleanId =false; 
	var pageSize=200;
	
	var fields=[
		'id',
		'faxMainNo',
		'matStatus',
		'flightMainNo',
		'customerId',
		'customerName',
		'faxPiece',
		'flightPiece',
		'faxWeight',
		'flightWeight',
		'flightAmount',
		'boardAmount',
		'amount',
		'price',
		'isLowestStatus',
		'remark',
		'diffWeight',
		'diffAmount',
		'startcity',
		'status',
		'createName',
		'createTime',
		'createDeptid',
		'createDept',
		'reviewDept',
		'reviewUser',
		'reviewDate',
		'reviewRemark',
		'ts',
		'feeType'];
    
    var fields2=[{name:"flightNo",mapping:'T1_FLIGHT_NO'},
    			{name:'flightMainNo',mapping:'T0_FLIGHT_MAIN_NO'},
    			{name:'flightDate',mapping:'T1_FLIGHT_DATE'},
    			{name:'cpName',mapping:'T1_CP_NAME'},
    			{name:'cusName',mapping:'T2_CUS_NAME'},
    			{name:'id',mapping:'ID'},
    			{name:'createTime',mapping:'CREATE_TIME'},
    			
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
        width : 35
	});
	
	var rownum2=new Ext.grid.RowNumberer({
		header:'序号',
        width : 35
	});

    var jsonread= new Ext.data.JsonReader({
                    root:'result',
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
		name:'checkId',
		listeners : {
			selectionchange:function(){
				//var vnetmusicRecord = recordGrid2.getSelectionModel().getSelections();
				
			}
		}
	});

	var sm2 = new Ext.grid.CheckboxSelectionModel({
		width:25,
		moveEditorOnEnter:true, 
		id:'checkId2',
		name:'checkId2'
	});
	
	var form1 = new Ext.form.FormPanel({
				id : 'form1',
				frame : true,
				width : 100,
				cls : 'displaynone',
				hidden : true,
				items : [],
				buttons : []
			});
	form1.render(document.body);
	
	var dataStore = new Ext.data.Store({
                proxy: new Ext.data.HttpProxy({url:sysPath+"/fi/fiDeliverycostAction!ralaList.action"}),
                baseParams:{
                	limit: pageSize,
                	filter_EQL_matStatus:0,
                	filter_EQL_departId:bussDepart,
                	privilege:137
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
	                }
	            }
	        }
	    });

 	var twobar = new Ext.Toolbar({
 				id:'twobar',
		    	items : ['&nbsp;','<B>黄单号</B>'
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
		              },'-','&nbsp;&nbsp;',
		              	{
		    			xtype:'label',
		    			id:'showMsg2',
		    			width:380
						}]
		});

		function findByDetail(){
			Ext.apply(copyDataStore.baseParams={
		 	 		piece:Ext.getCmp('piece').getValue(),
		 	 		weight:Ext.getCmp('weight').getValue(),
					type:1,
					flightMainNo:Ext.getCmp('flightMainNo').getValue(),
					flightNo:Ext.getCmp('flightNo').getValue()
			});

			copyDataStore.reload({
				params : {
					start : 0,
					limit : pageSize
				}
			});
		}

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
     
     var recordGrid=new Ext.grid.EditorGridPanel({
		id:'userCenter',
		height : 200,
		width : Ext.lib.Dom.getViewWidth()*0.5-5,
		autoScroll : true,  //面板上的body元素
	 	viewConfig : {
				scrollOffset: 0,
				autoScroll:true
		},
		frame : true,
	//	enableColumnResize:false, //关闭列的自适大小功能
		autoWidth:false,
	//	clicksToEdit:1,
		loadMask : true,
		sm:sm,
		tbar:twobar,
		stripeRows : true,
		columns:[ rownum,sm,
					{header: '编号', dataIndex: 'id',width:55,sortable : true},
			        {header: '匹配状态', dataIndex: 'matStatus',width:60,sortable : true,
				       		 renderer:function(v){
				        		if(v=='1'){ 
				        			return '自动匹配';
				        		}else if(v=='2'){
				        			return '手工匹配';
				        		}else if(v=='0'){
				        			return '未匹配';
				        		}else{
				        			return '';
				        		}
				        	}
				     },
			        {header: '黄单号', dataIndex: 'flightMainNo',width:70,sortable : true},
			        {header: '客商ID', dataIndex: 'customerId',width:70,hidden:true,sortable : true},
			        {header: '提货点', dataIndex: 'customerName',width:80,sortable : true},　
					{header: '黄单件数', dataIndex: 'flightPiece',width:70,sortable : true},
					{header: '黄单重量', dataIndex: 'flightWeight',width:70,sortable : true},
					{header: '黄单金额', dataIndex: 'flightAmount',width:70,sortable : true},
					{header: '板费', dataIndex: 'boardAmount',width:70,sortable : true},
			        {header: '总金额', dataIndex: 'amount',width:70,sortable : true},  
			        
			        {header: '单价', dataIndex: 'price',width:70,sortable : true},
			        {header: '结算方式', dataIndex: 'feeType',width:70,sortable : true},
			        {header: '是否最低一票', dataIndex: 'isLowestStatus',width:70,sortable : true,
			        		 renderer:function(v){
				        		if(v=='1'){ 
				        			return '是';
				        		}else if(v=='0'){
				        			return '否';
				        		}else{
				        			return '';
				        		}
				        	}},
			       	{header: '始发站', dataIndex: 'startcity',width:80,sortable : true},
			       	{header: '创建人', dataIndex: 'createName',width:80,hidden:true,sortable : true},
			        {header: '创建时间', dataIndex: 'createTime',width:80,hidden:true,sortable : true},
			        {header: '创建部门ID', dataIndex: 'createDeptid',width:80,hidden:true,sortable : true},
			        {header: '创建部门', dataIndex: 'createDept',width:80,hidden:true,sortable : true},
			        {header: '备注', dataIndex: 'remark',width:80,sortable : true}
			    ],
		store :dataStore,
		listeners : {
			'rowclick' : function(f,rowIndex,e){

				var record = f.getSelectionModel().getSelections();
				if(record.length==0){
					copyDataStore.removeAll();
				}else{
				
					if(Ext.getCmp('two').validate()){
						
						if(userRadio.getValue()){
							Ext.apply(copyDataStore.baseParams={
					 	 		piece:record[0].data.flightPiece,
					 	 		weight:record[0].data.flightWeight,
								xvalue:Ext.getCmp('two').getValue(),
								flightMainNo:record[0].data.flightMainNo
							});
						
						}else{
							Ext.apply(copyDataStore.baseParams={
					 	 		piece:record[0].data.flightPiece,
					 	 		weight:record[0].data.flightWeight,
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
			autoScroll : true,  //面板上的body元素
			viewConfig : {
				scrollOffset: 0,
				autoScroll:true
			},
			frame : true,
		//	enableColumnResize:false, //关闭列的自适大小功能
			autoWidth:false,
			loadMask : true,
			sm:sm2,
			tbar:threebar,			
			stripeRows : true,
			columns:[rownum2,sm2,
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
			        {header: '创建时间', dataIndex: 'createTime',width:80,sortable : true},
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
								renderTo:Ext.getBody(),
								bodyStyle : 'padding:0px 0px 0px',
							    height : Ext.lib.Dom.getViewHeight()-2,
								width : Ext.lib.Dom.getViewWidth()-1,
								labelAlign : "right",
									tbar:['&nbsp;',{
										text : '<B>手工匹配</B>',
										id : 'submitbtn',
										tooltip : '手工匹配',
										iconCls:'userEdit',
										handler : sumitStore
												
									},'-','&nbsp;','<B>黄单录入日期：</B>',
									  { xtype : 'datefield',
							    		id : 'startDate2',
							    		format : 'Y-m-d',
							    		emptyText : "选择开始时间",
							    		width : 80,
							    		listeners : {
							    			'select' : function() {
							    			   var start = Ext.getCmp('startDate2').getValue().format("Y-m-d");
							    			   Ext.getCmp('endDate2').setMinValue(start);
							    		     }
							    		}
						    		},'&nbsp;','<B>至</B>','&nbsp;',{
							    		xtype : 'datefield',
							    		id : 'endDate2',
							    		format : 'Y-m-d',
							    		emptyText : "选择结束时间",
							    		width : 80,
							    		enableKeyEvents:true
						    	    },'-','&nbsp;','<B>主单录入日期：</B>',
									  { xtype : 'datefield',
							    		id : 'startDate',
							    		format : 'Y-m-d',
							    		emptyText : "选择开始时间",
							    		width : 80,
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
							    		format : 'Y-m-d',
							    		emptyText : "选择结束时间",
							    		width : 80,
							    		enableKeyEvents:true
						    	    },'&nbsp;','<B>主单号:</B>',
						              　　{xtype : 'textfield',
			        	                id:'flightMainNo2',
				        			    name: 'flightMainNo2',
				        			    maxLength : 10,
						                width:80,
						                enableKeyEvents:true
						            },'-','&nbsp;',{
				    				     text : '<b>查询</b>',
				    				     id : 'btn2',
				    				     iconCls : 'btnSearch',
				    					 handler : findAll
				    				  },'&nbsp;&nbsp;',{
							    			xtype:'label',
							    			id:'showMsg',
							    			width:380
							    }],
								listeners: {
					                    render: function(){
					                     
					   
					                    }
					            },
						        items : [{
											layout : 'column',
											items : [{
														columnWidth : .5,
														layout : 'form',
														items : [recordGrid]

													},{
														columnWidth : .5,
														layout : 'form',
														items : [recordGrid2]
													}]
													
										}]
					});
     
     
	

	
		form.render();
		recordGrid.setHeight(Ext.lib.Dom.getViewHeight()-35);
		recordGrid2.setHeight(Ext.lib.Dom.getViewHeight()-35);
		
		function selectDistributionMode(){
			
		
		}
		
		function findAll(){
			var start='';
			var end='';
			var startDatef =Ext.getCmp('startDate').validate();
			var endDateff =Ext.getCmp('endDate').validate();
			var startDatef2 =Ext.getCmp('startDate2').validate();
			var endDateff2 =Ext.getCmp('endDate2').validate();
			var nof =Ext.getCmp('flightMainNo2').validate();
			if(startDatef&&endDateff&&nof&&startDatef2&&endDateff2){
					if(Ext.getCmp('startDate').getValue()!=''){
						var start = Ext.getCmp('startDate').getValue().format("Y-m-d");
					}
					if(Ext.getCmp('endDate').getValue()!=''){
						var end = Ext.getCmp('endDate').getValue().format("Y-m-d");
					}
					if(Ext.getCmp('startDate2').getValue()!=''){
						var start2 = Ext.getCmp('startDate2').getValue().format("Y-m-d");
					}
					if(Ext.getCmp('endDate2').getValue()!=''){
						var end2 = Ext.getCmp('endDate2').getValue().format("Y-m-d");
					}
					if(Ext.getCmp('flightMainNo2').getValue()==''){
							Ext.apply(copyDataStore.baseParams={
					 	 		startDate:start,
					 	 		endDate:end
							});
					}else{
							Ext.apply(copyDataStore.baseParams={
					 	 		 flightMainNo:Ext.getCmp('flightMainNo2').getValue()
							});
					}
					
					Ext.apply(dataStore.baseParams={
			 	 		filter_GED_createTime : start2,
						filter_LED_createTime : end2,
						filter_EQL_matStatus:0,
                		filter_EQL_departId:bussDepart,
                		privilege:137
					});
				
					dataStore.reload({
							params : {
								start : 0,
								limit : pageSize
							}
					});
					
					copyDataStore.reload({
							params : {
								start : 0,
								limit : pageSize
							}
					});
			}else{
				Ext.Msg.alert(alertTitle,'填写的查询条件不符合格式要求。', function(){});
			}
		}


		function changeColour(rowIndex, columnIndex){
			
		}

 
		
		function sumitStore(){
			var records = recordGrid.getSelectionModel().getSelections(); //黄单
		  	var records2 = recordGrid2.getSelectionModel().getSelections();//主单
			if(records.length==0||records2.length==0){
				Ext.Msg.alert(alertTitle,'进行手工匹配，必须选择一条或多条黄单记录和主单记录', function(){});
				return;
			}
			if(records2.length!=1&&records.length!=1){
				Ext.Msg.alert(alertTitle,'进行手工匹配，必须选择一条主单记录', function(){});
				return;
			}else{
				var str='';
				if(records2.length!=1&&records.length==1){
					str='<span style="color:red">您选择的是多条主单匹配一条黄单记录，请确认</span>。确定要多票匹配这些记录吗?';
				}else if(records.length!=1&&records2.length==1){
					str='<span style="color:red">您选择的是多条黄单匹配一条主单记录，请确认</span>。确定要多票匹配这些记录吗?';
				}else{
					str='您确定要手工匹配这些货物吗?';
				}
				Ext.Msg.confirm(alertTitle,str,function(btnYes){
					if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
						var ids="";
						for(var i=0;i<records.length;i++){
		 					if(i==0){
								ids += "aa["+i+"].id="+records[i].get("id")+"&aa["+i+"].ts="+records[i].get("ts")
								+"&aa["+i+"].faxId=";
								for(var j=0;j<records2.length;j++){
									if(j!=records2.length-1){
										ids +=records2[j].get("id")+",";
									}else{
										ids +=records2[j].get("id");
									}
								}
							}else{
								ids += "&aa["+i+"].id="+records[i].get("id")+"&aa["+i+"].ts="+records[i].get("ts")
								+"&aa["+i+"].faxId=";
								for(var j=0;j<records2.length;j++){
									if(j!=records2.length-1){
										ids +=records2[j].get("id")+",";
									}else{
										ids +=records2[j].get("id");
									}
								}
							}
						}
						ids+="&privilege="+privilege;

						form1.getForm().doAction('submit', {
							url : sysPath+ "/fi/fiDeliverycostAction!saveMat.action",
							method : 'post',
							params : ids,
							waitMsg : '正在保存匹配数据...',
							success : function(form, action) {
								Ext.Msg.alert(alertTitle,action.result.msg,
									function() {
										copyDataStore.reload();
										dataStore.reload();
									});
							},
							failure : function(form, action) {
								Ext.Msg.alert(alertTitle,action.result.msg,
									function(){});
							}
						});
					}
				});

			}
			
		}

});



















	

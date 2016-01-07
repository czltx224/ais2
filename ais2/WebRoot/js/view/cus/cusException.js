	Ext.QuickTips.init();
	var privilege=120;
	var comboxPage=comboSize;
	var saveUrl="stock/oprSignAction!saveSignStatus.action";
	var customerGridSearchUrl="sys/customerAction!list.action";
	var areaListUrl="stock/oprStoreAreaAction!list.action";
	var ralaListUrl="exception/oprExceptionAction!getAll.action";
	var booleanId =false; 

Ext.onReady(function(){
	Ext.QuickTips.init();
	var rownum=new Ext.grid.RowNumberer({
		header:'序号',
        width : 35,
		sortable : true
		
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
	
	//异常责任部门
	var serviceDepartStore = new Ext.data.Store({ 
            storeId:"serviceDepartStore",                        
            proxy: new Ext.data.HttpProxy({url:sysPath+"/sys/departAction!findAll.action",method:'post'}),
            reader: new Ext.data.JsonReader({
            root: 'result', totalProperty:'totalCount'
           }, [  
                 {name:'departName', mapping:'departName'},    
                 {name:'departId', mapping: 'departNo'}
              ])
    });
	
	var fields=[{name:"id",mapping:'id'},'dno','cusName','flightMainNo','flightNo','subNo',
										'consignee','piece','weight','exceptionType1','exceptionNode',
										'exceptionType2','exceptionName','exceptionTime','exceptionRepar',
										'exceptionReparTime','exceptionReparCost','dutyDepartId','dutyDepartName',
										'exceptionPiece','exceptionAdd','exceptionDescribe','suggestion','status',
										'finalResult','dealName','dealTime','dealReasult','isCp','isCus',
										'isWeb','exptionAdd','finalDuty','finalPiece','qm','qmTime','qmSuggestion',
										'submitQualified','dealQualified','reparQualified','createTime','createName','isDoPiece',
										'updateTime','updateName','ts','departId','departName','createDepartId','createDepartName'];
	
    var jsonread= new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'},
                    fields);
                              
	var sm = new Ext.grid.CheckboxSelectionModel({
		width:25,
		singleSelect: true,
		moveEditorOnEnter:true, 
		id:'checkId',
		name:'checkId'
	});
	
	var menuStore = new Ext.data.Store({ 
            storeId:"menuStore",                        
            proxy: new Ext.data.HttpProxy({url:sysPath+"/sys/departAction!ralaList.action",method:'post'}),
            baseParams:{privilege:53},
            reader: new Ext.data.JsonReader({
            root: 'result', totalProperty:'totalCount'
           }, [  
                 {name:'departName', mapping:'departName'},    
                 {name:'departId', mapping: 'departId'}
              ])
     });
	
	//异常环节
	var nodeStore= new Ext.data.Store({
			storeId:"nodeStore",
			proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/exceptionTypeAction!list.action"}),
		/*	baseParams:{
				privilege:116,
				filter_EQL_parentId:0
			},*/
			reader:new Ext.data.JsonReader({
	                    root:'result',
	                    totalProperty:'totalCount'
	        },[
        	{name:'nodeId',mapping:'id'},
        	{name:'nodeName',mapping:'nodeName'}
        	])
	});
	nodeStore.load();
	
		//exceptionClassStore主异常类型
	var exceptionClassStore= new Ext.data.Store({
			storeId:"exceptionClassStore",
			proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/exceptionTypeAction!list.action"}),
			reader:new Ext.data.JsonReader({
	                    root:'result',
	                    totalProperty:'totalCount'
	        },[
        	{name:'id',mapping:'id'},
        	{name:'exceptionType1',mapping:'typeName'}
        	])
	});
	exceptionClassStore.load();
	
	//发货代理
	var	cusStore = new Ext.data.Store({
			storeId : "cusStore",
			proxy : new Ext.data.HttpProxy({
				url : sysPath + "/" + customerGridSearchUrl,method:'post'
			}),
			reader : new Ext.data.JsonReader({
				root : 'result',
				totalProperty : 'totalCount'
			}, [    
				   {name : 'cpName',mapping :'cusName'}
			])
	});
	
	//是否通知代理
	var isCpStore=new Ext.data.SimpleStore({
			 auteLoad:true, //此处设置为自动加载
  			 data:[['0','是'],['1','否']],
   			 fields:["isCp","isCpName"]
	});

	//是否通知客户
	var isCusStore=new Ext.data.SimpleStore({
			 auteLoad:true, //此处设置为自动加载
  			 data:[['0','否'],['1','是']],
   			 fields:["isCus","isCusName"]
	});

	//是否网银体现
	var isWebStore=new Ext.data.SimpleStore({
			 auteLoad:true, //此处设置为自动加载
  			 data:[['0','否'],['1','是']],
   			 fields:["isWeb","isWebName"]
	});
	
	//是否修复
	var isReparStore=new Ext.data.SimpleStore({
			 auteLoad:true, //此处设置为自动加载
  			 data:[['0','否'],['1','是']],
   			 fields:["isRepar","isReparName"]
	});

	//提交合格
	var submitQualifiedStore=new Ext.data.SimpleStore({
			 auteLoad:true, //此处设置为自动加载
  			 data:[['0','不合格'],['1','合格']],
   			 fields:["submitQualified","submitQualifiedName"]
	});
	
	//处理合格
	var dealQualifiedStore=new Ext.data.SimpleStore({
			 auteLoad:true, //此处设置为自动加载
  			 data:[['0','不合格'],['1','合格']],
   			 fields:["dealQualified","dealQualifiedName"]
	});
	
	//修复合格
	var reparQualifiedStore=new Ext.data.SimpleStore({
			 auteLoad:true, //此处设置为自动加载
  			 data:[['0','不合格'],['1','合格']],
   			 fields:["reparQualified","reparQualifiedStoreName"]
	});
	
	
	var dataStore = new Ext.data.Store({
                proxy: new Ext.data.HttpProxy({url:sysPath+"/"+ralaListUrl}),
                baseParams:{limit: pageSize},
                sortInfo : {field: "id", direction: "ASC"},
                reader:jsonread
    });
 	
 	var twobar = new Ext.Toolbar({
		    	items : ['&nbsp;&nbsp;','<B>异常时间:</B>',{
				xtype : 'datefield',
    			id : 'startDate',
	    		format : 'Y-m-d',
	    		emptyText : "选择开始时间",
	    		value:new Date().add(Date.MONTH, -1),
	    		width : 75,
	    		listeners : {
	    			'select' : function() {
	    			   var start = Ext.getCmp('startDate').getValue()
	    			      .format("Y-m-d");
	    			   Ext.getCmp('endDate').setMinValue(start);
	    		     }
    		    }
				},'<B>&nbsp;&nbsp;至:</B>',{
				xtype : 'datefield',
	    		id : 'endDate',
	    		format : 'Y-m-d',
	    		value:new Date(),
	    		emptyText : "选择结束时间",
	    		width : 75,
	    		anchor : '95%'
			},'-','&nbsp;&nbsp;','<B>异常环节:</B>',
			  {
				xtype : 'combo',
				triggerAction : 'all',
				store : nodeStore,
				emptyText : "请选择异常环节",
				allowBlank : true,
				width:75,
				fieldLabel:'节点名称',
				mode : "local",//获取本地的值
				displayField : 'nodeName',//显示值，与fields对应
				valueField : 'nodeName',//value值，与fields对应
				hiddenName : 'nodeName',
				id:'nodeOrderCombo',
				enableKeyEvents:true,
				listeners : {
					'select':function(combo){
					   
            		 }
			   	}
			   },'-','&nbsp;&nbsp;','<B>异常类型:</B>',
			  {
				xtype : 'combo',
				triggerAction : 'all',
				store : exceptionClassStore,
				emptyText : "请选择异常类型",
				allowBlank : true,
				width:75,
				fieldLabel:'异常类型',
				mode : "local",//获取本地的值
				displayField : 'exceptionType1',//显示值，与fields对应
				valueField : 'exceptionType1',//value值，与fields对应
				hiddenName : 'exceptionType1',
				id:'exceptionTypeCombo',
				enableKeyEvents:true,
				listeners : {
					'select':function(combo){
					   
            		 }
			   	}
			   },'-','&nbsp;&nbsp;','<B>配送单号<span style="color:red">*</span>:</B>',
               {xtype : 'textfield',
                fieldLabel: '配送单号', 
                id:'dNo',
			    name: 'dNo',
			    maxLength : 10,
                width:75,
                enableKeyEvents:true,
	            listeners : {
	 				keyup:function(textField, e){
	                     if(e.getKey() == 13){
	                     	searchLog();
	                     }
	 				}
	 			}
              },'-',
				'&nbsp;&nbsp;',{
			     text : '<b>查询</b>',
			     id : 'btn',
			     iconCls : 'btnSearch',
				 handler : searchLog
			}]
		});
     
	var recordGrid=new Ext.grid.EditorGridPanel({
		renderTo:'recordGrid',
		renderTo:Ext.getBody(),
	//	el : 'recordGrid',
		id:'userCenter',
		height : Ext.lib.Dom.getViewHeight()-2,
		width : Ext.lib.Dom.getViewWidth()-2,
	//	autoScroll : true,
		viewConfig : {
			scrollOffset: 0,
			//forceFit : true,
			autoScroll:true
		},
		//autoExpandColumn : 1,
		frame : false,
	//	clicksToEdit:1,
		loadMask : true,
		sm:sm,
		stripeRows : true,
		tbar:[{
				text:'<B>新增异常</B>',
				iconCls : 'userAdd',
				id:'addbtn',
				handler : function() {
						updateException();
				}
			},'-',{
				text:'<b>删除异常</b>',
			 	disabled : true,
				id : 'deletebtn',
			 	iconCls:'userDelete',
			 	handler : function() {
					deleteException();
				} 
			},'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;',
			{	xtype:'textfield',
	 	        id : 'itemsValue',
	 	        disabled:true,
		        name : 'itemsValue',
	            enableKeyEvents:true,
	            listeners : {
	 				keyup:function(textField, e){
	                     if(e.getKey() == 13){
	                     	searchLog();
	                     }
	 				}
	 			}
	        },{
				xtype : 'combo',
				id:'comboTypeDepart',
				hidden : true,
				triggerAction : 'all',
				store : serviceDepartStore,
				width : 100,
				listWidth:245,
				minChars : 1,
				allowBlank : true,
				emptyText : "请选择部门名称",
				forceSelection : true,
				fieldLabel:'部门名称',
				editable : true,
				pageSize:30,
				displayField : 'departName',//显示值，与fields对应
				valueField : 'departId',//value值，与fields对应
				name : 'departId',
				anchor : '100%'
		    },{
				xtype : 'combo',
				id:'comboTypeFlight',
				hidden : true,
				hiddenId : 'flightNo',
    			hiddenName : 'flightNo',
				triggerAction : 'all',
			//	store : flightStore,
				minChars : 1,
				width : 100,
				listWidth:245,
				allowBlank : true,
				emptyText : "请选择航班号",
				forceSelection : true,
				editable : true,
			    queryParam : 'filter_LIKES_flightNo',
				pageSize:comboxPage,
				displayField : 'flightNo',//显示值，与fields对应
				valueField : 'flightNo',//value值，与fields对应
				name : 'flightNo',
				anchor : '100%',
				enableKeyEvents:true,
				    listeners : {
						keyup:function(textField, e){
				         if(e.getKey() == 13){
				                   	searchLog();
				         }
					}
				}
		    },'-',{
                xtype : "combo",
    			id:"comboselect",
   				width : 100,
 				triggerAction : 'all',
    			model : 'local',
    			hiddenId : 'checkItems',
    			hiddenName : 'checkItems',
    			name : 'checkItemstext',
    			store : [['', '查询全部'], 
    					['LIKES_flightMainNo', '主单号'],
    					['LIKES_subNo', '分单号'],
    					['LIKES_flightNo', '航班号'],
    					['LIKES_consignee', '收货人'],
    					['LIKES_goods', '品名'],
    					['EQL_dutyDepartId','处理部门'],
    					['LIKES_qm', '品管姓名']
    					],
    			emptyText : '选择查询类型',
    			forceSelection : true,
    			listeners : {
    				'select' : function(combo, record, index) { 
    					if (combo.getValue() == '') {
    						Ext.getCmp("itemsValue").show();
    						Ext.getCmp("itemsValue").disable();
    						Ext.getCmp("itemsValue").setValue("");
    	
    						Ext.getCmp("comboTypeDepart").disable();
    						Ext.getCmp("comboTypeDepart").hide();
    						Ext.getCmp("comboTypeDepart").setValue("");
    					}else if(combo.getValue() == 'EQS_cusName'){
    						Ext.getCmp("itemsValue").disable();
    						Ext.getCmp("itemsValue").hide();
    						Ext.getCmp("itemsValue").setValue("");
    						
    						Ext.getCmp("comboTypeDepart").disable();
    						Ext.getCmp("comboTypeDepart").hide();
    						Ext.getCmp("comboTypeDepart").setValue("");
    						
    					
    					}else if(combo.getValue() == 'EQL_dutyDepartId') {
    						Ext.getCmp("comboTypeDepart").setValue("");
    						Ext.getCmp("comboTypeDepart").show();
    						Ext.getCmp("comboTypeDepart").enable();
    								
    						Ext.getCmp("itemsValue").disable();
    						Ext.getCmp("itemsValue").hide();
    						Ext.getCmp("itemsValue").setValue("");
    					}else{  //
    						Ext.getCmp("itemsValue").enable();
    						Ext.getCmp("itemsValue").hide();
    						Ext.getCmp("itemsValue").show();
    						
    						Ext.getCmp("comboTypeDepart").disable();
    						Ext.getCmp("comboTypeDepart").hide();
    						Ext.getCmp("comboTypeDepart").setValue("");
    					}
    				}
    			 }
    		},'-','&nbsp;&nbsp;',
    		  {
	   			xtype:'label',
	   			id:'showMsg',
	   			width:380
	   		}],
			columns:[ rownum,sm,
			        {header: '配送单号', dataIndex: 'dno',width:60},
        			{header: '代理公司', dataIndex: 'cusName',width:65},
			        {header: '运单号', dataIndex: 'flightMainNo',width:60},
			        {header: '航班号', dataIndex: 'flightNo',width:60},
			        {header: '分单号', dataIndex: 'subNo',width:60},
			        {header: '收货人', dataIndex: 'consignee',width:60},
			        {header: '件数', dataIndex: 'piece',width:60},
			        {header: '重量', dataIndex: 'weight',width:60},
			        {header: '异常环节', dataIndex: 'exceptionNode',width:60},
			        {header: '异常类型', dataIndex: 'exceptionType1',width:60},
			        {header: '异常发现人', dataIndex: 'exceptionName',width:80},
			        {header: '异常发现时间', dataIndex: 'exceptionTime',width:85},　
			        {header: '异常修复人', dataIndex: 'exceptionRepar',hidden:true,width:75},
			        {header: '修复时间', dataIndex: 'exceptionReparTime',hidden:true,width:60},
			        {header: '修复成本', dataIndex: 'exceptionReparCost',hidden:true,width:60},
			        {header: '处理部门', dataIndex: 'dutyDepartName',width:80},
			        {header: '异常件数', dataIndex: 'exceptionPiece',width:60},
			        
			        {header: '异常处理人', dataIndex: 'dealName',width:60},
			        {header: '异常处理时间', dataIndex: 'dealTime',width:80},
			        {header: '是否通知代理', dataIndex: 'isCp',width:60,renderer:function(v){
			        					if(v==1) return '是';
			        					if(v==0) return '否';
			        					}},
			        {header: '是否通知客户', dataIndex: 'isCus',width:60,renderer:function(v){
			        					if(v==1) return '是';
			        					if(v==0) return '否';
			        					}},
			        {header: '是否网营体现', dataIndex: 'isWeb',width:60,renderer:function(v){
			        					if(v==1) return '是';
			        					if(v==0) return '否';
			        					}},
			        {header: '最终责任人', dataIndex: 'finalDuty',width:60},
			        {header: '最终责任件数', dataIndex: 'finalPiece',width:60},
			        {header: '品管', dataIndex: 'qm',width:60},
			        {header: '异常损失金额', dataIndex: 'exceptionMoney',width:80},
			        {header: '品管处理时间', dataIndex: 'qmTime',width:60},
			        {header: '提交是否合格', dataIndex: 'submitQualified',width:75,renderer:function(v){
			        					if(v==1) return '是';
			        					if(v==0) return '否';
			        					}},
			        {header: '处理是否合格', dataIndex: 'dealQualified',width:75,renderer:function(v){
			        					if(v==1) return '是';
			        					if(v==0) return '否';
			        					}},
			        {header: '修复是否合格', dataIndex: 'reparQualified',width:75,renderer:function(v){
			        					if(v==1) return '是';
			        					if(v==0) return '否';
			        					}},
			        {header: '创建部门', dataIndex: 'createDepartName',width:100}
			    ],
			store : dataStore,
			listeners: {
                    render: function(){
                        twobar.render(this.tbar);
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

	
	recordGrid.render();
	
	function searchLog() {
			Ext.getCmp('showMsg').getEl().update('<span style="color:red"></span>');
		   　dataStore.proxy = new Ext.data.HttpProxy({
		  			method : 'POST',
					url : sysPath+'/'+ralaListUrl+'?privilege='+privilege
			});
						
			var exceptionType=Ext.getCmp("exceptionTypeCombo").getRawValue();  // 异常类型
			var dNo=Ext.get("dNo").dom.value;　　// 配送单号　　stocktadeId
			if(dNo==''){
					var nodeName=Ext.getCmp("nodeOrderCombo").getRawValue();　　// 异常环节
					var start='';
					var end ='';
					if(Ext.getCmp('startDate').getValue()!=""){
					   	var start = Ext.getCmp('startDate').getValue().format("Y-m-d");
					}
					if(Ext.getCmp('endDate').getValue()!=""){
					   	var end = Ext.getCmp('endDate').getValue().format("Y-m-d");
					}
					
					if (Ext.getCmp('comboselect').getValue() == 'EQL_dutyDepartId'){	
		    			Ext.apply(dataStore.baseParams={
							filter_LIKES_exceptionType2: exceptionType,
							filter_LIKES_exceptionType1:nodeName,
							filter_GED_createTime : start,
			    		    filter_LED_createTime : end,
			    		    filter_EQS_cusName:parent.cusName,
							filter_LIKES_dutyDepartId:Ext.getCmp("comboTypeDepart").getValue(),
						    limit : pageSize
						});
		    		}else {
						Ext.apply(dataStore.baseParams={
							filter_LIKES_exceptionType2: exceptionType,
							filter_LIKES_exceptionType1:nodeName,
							filter_GED_createTime : start,
			    		    filter_LED_createTime : end,
			    		    filter_EQS_cusName:parent.cusName,
							checkItems : Ext.get("checkItems").dom.value,
							itemsValue : Ext.get("itemsValue").dom.value,
						    limit : pageSize
						});
					}
			}else{
				var regex=/^[1-9]\d*$/;
			    if(!regex.test(dNo)){
			       	Ext.getCmp('showMsg').getEl().update('<span style="color:red">配送单号输入格式不正确，无法查询。</span>');
			       	Ext.getCmp('dNo').focus();	
			       	Ext.getCmp('dNo').markInvalid("配送单号输入格式不正确");
					Ext.getCmp('dNo').selectText();	
			     
					return;
			    }
				Ext.getCmp('showMsg').getEl().update('<span style="color:red">输入配送单号查询时，其他查询条件将无效。</span>');
				Ext.apply(dataStore.baseParams={
							filter_EQL_dno:dNo
				});
			
			
			}
			dataStore.reload({
					params : {
						start : 0,
						limit : pageSize
					}
			});
		
	}
		
	recordGrid.on('click', function() {
		select();
	});
		
	recordGrid.on('rowdblclick',function(grid,index,e){
		var _records = recordGrid.getSelectionModel().getSelections();
		if (_records.length ==1) {
			//sumitStore(_records);
		}
	});
		
		
	function select(){
			var vnetmusicRecord = recordGrid.getSelectionModel().getSelections();
				//var updatebtn = Ext.getCmp('updatebtn');
				var deletebtn = Ext.getCmp('deletebtn');
				if (vnetmusicRecord.length == 1) {
					//updatebtn.setDisabled(false);
					deletebtn.setDisabled(false);
				} else if (vnetmusicRecord.length > 1) {
					//updatebtn.setDisabled(true);
					deletebtn.setDisabled(false);
				} else {
					deletebtn.setDisabled(true);
					//updatebtn.setDisabled(true);
				}
	}
		
    function updateException(_record) {
    		if(_record==null){
				var node=new Ext.tree.TreeNode({id:'keel_'+(Math.random()*10+1),leaf :false,text:"异常处理"});
		      	node.attributes={href1:'exception/oprExceptionAction!showForm.action'};
		        top.toAddTabPage(node,true);
    		}
    }
    	
    function deleteException(){
    		var _records = recordGrid.getSelectionModel().getSelections();
			if (_records.length == 0) {
				 Ext.Msg.alert(alertTitle, '请选择一条您需要删除的数据');
				 return false;
			}else{
			Ext.Ajax.request({
				url : sysPath+ "/exception/oprExceptionAction!list.action",
				params:{
					filter_EQL_id:_records[0].data.id,
					limit : pageSize
				},
				success : function(response) { // 回调函数有1个参数  departId
					if(Ext.decode(response.responseText).result[0].createDepartId==departId){
						
						if(Ext.decode(response.responseText).result[0].status=='2'){
							Ext.Msg.alert(alertTitle, '处理意见部门已处理相关信息，无法删除此异常记录');
							return false;
						}else if(Ext.decode(response.responseText).result[0].status=='3'){
							Ext.Msg.alert(alertTitle, '最终意见处理完成，无法删除此异常记录');
							return false;
						}else if(Ext.decode(response.responseText).result[0].status=='0'){
								     Ext.Msg.confirm(alertTitle,'数据删除后将不可恢复，您确定要删除这<span style="color:red">&nbsp;'+ _records.length + '&nbsp;</span>行数据吗?',function(btnYes) {
												if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
													
									        var ids = "";
    										for(var i = 0; i < _records.length; i++) {
												ids += _records[i].data.id + ",";
											}  	
										form1.getForm().doAction('submit', {
											url : sysPath+ "/exception/oprExceptionAction!delete.action",
											method : 'post',
											params : {
   													ids : ids,
   													privilege:privilege
   											},
											waitMsg : '正在删除数据...',
											success : function(form1, action) {
												Ext.Msg.alert(alertTitle,"数据删除成功",
														function() {
															dataStore.reload()
														});
											},
											failure : function(form1, action) {
												Ext.Msg.alert(alertTitle,"数据删除失败",
														function() {
															dataStore.reload();
															 select();
														});
											}
										});
										}
									});
					}else{
						Ext.Msg.alert(alertTitle, '您无权限无法删除异常信息');
						return false;
					}
				}else{
					Ext.Msg.alert(alertTitle, '您无权限无法删除异常信息');
					return false;
				}
			},
			failure : function(response) {
				Ext.Msg.alert(alertTitle, '无法判断您的删除权限，拒绝删除异常信息');
			}
		});	
	
	  		}
    }									

});
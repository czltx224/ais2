	Ext.QuickTips.init();
	var privilege=23;
	var comboxPage=comboSize;
	var saveUrl="user/userAction!save.action";
	var ralaListUrl="user/userAction!ralaList.action";
	
Ext.onReady(function(){
	Ext.QuickTips.init();
	var rownum=new Ext.grid.RowNumberer({
		header:'序号',
        width : 35,
		sortable : true	
	});
 
	var fields=[{name:"id",mapping:'id'},{name:"departId",mapping:'departId'},'userCode','loginName','userName','birthdayType',
	                                     {name:"departName",mapping:'departName'},'birthday','workstatus','hrstatus','manCode',
                                         {name:"stationId",mapping:'stationId'}, 'offTel','telPhone','sex','updateName',
                                         {name:"stationName",mapping:'stationName'},'stationNames','duty','status',
                                         {name:"bussDepart",mapping:'bussDepart'}, 'userLevel','createName','createTime','updateName',
                                         {name:"bussDepartName",mapping:'bussDepartName'},'password','updateTime','ts'];
	
	var jsonread= new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'},
                    fields);
	var sm = new Ext.grid.CheckboxSelectionModel();
	
	var sexStore=new Ext.data.SimpleStore({
			 auteLoad:true, //此处设置为自动加载
  			  data:[['1','男'],['0','女']],
   			 fields:["sex","sexName"]
	});
	
	var workstatusStore=new Ext.data.SimpleStore({
			 auteLoad:true, //此处设置为自动加载
  			  data:[['1','正常'],['0','离职']],
   			 fields:["workstatus","workstatusName"]
	});
	
	var hrstatusStore=new Ext.data.SimpleStore({
			 auteLoad:true, //此处设置为自动加载
  			  data:[['1','正常'],['0','试用期']],
   			 fields:["hrsstatus","hrsstatusName"]
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
        storeId:"dataStore",
        proxy: new Ext.data.HttpProxy({url:sysPath+"/"+ralaListUrl}),
        baseParams:{privilege:privilege},
        reader:jsonread
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
              ]),                                      
            sortInfo:{field:'departId',direction:'ASC'}
     });
     menuStore.load();
     
     var stationStore = new Ext.data.Store({ 
            storeId:"stationStore",                        
            proxy: new Ext.data.HttpProxy({url:sysPath+"/sys/stationAction!list.action",method:'post'}),
            reader: new Ext.data.JsonReader({
            root: 'result', totalProperty:'totalCount'
           }, [  
                 {name:'stationName',mapping:'text'},        
                 {name:'stationId',mapping:'id'}
              ]),                                  
            sortInfo:{field:'stationId',direction:'ASC'}
     });
     stationStore.load();

	 var bussStore = new Ext.data.Store({ 
            storeId:"bussStore",                        
            proxy: new Ext.data.HttpProxy({url:sysPath+"/sys/departAction!ralaList.action",method:'post'}),
            baseParams:{
               privilege:53,
               filter_EQL_isBussinessDepa:1
            },
            reader: new Ext.data.JsonReader({
                        root: 'result', totalProperty:'totalCount'
                    },[ {name:'bussDepartName', mapping:'departName'},    
                        {name:'bussDepart', mapping: 'departId'}
            ]),                                      
            sortInfo:{field:'bussDepart',direction:'ASC'}
     });
     bussStore.load();
     
	var recordGrid=new Ext.grid.GridPanel({
	//	renderTo:'recordGrid',
		renderTo:Ext.getBody(),
		id:'userCenter',
		height : Ext.lib.Dom.getViewHeight()-1,
		width : Ext.lib.Dom.getViewWidth()-1,
		//autoScroll : true,
		viewConfig : {
			scrollOffset: 0,
			columnsText : "显示的列",
			sortAscText : "升序",
			sortDescText : "降序",
			//forceFit : true,
			autoScroll:true
		},
	//	autoExpandColumn : 1,
		frame : true,
		loadMask : true,
		sm:sm,
		stripeRows : true,
		tbar:[{text:'<b>导出</b>',iconCls:'sort_down',handler:function() {
                	parent.exportExl(recordGrid);
        } },'-','&nbsp;&nbsp;',{
				text:'<B>添加</B>',
				tooltip : '增加员工信息',
				iconCls : 'userAdd',
				id:'addbtn',
				handler : function() {
						updateUser();
				}
			}, '-',{
				text : '<B>修改</B>',
				id : 'updatebtn',
				disabled : true,
				tooltip : '修改员工信息',
				iconCls:'userEdit',
				handler : function() {
						var _records = recordGrid.getSelectionModel().getSelections();
						if (_records.length == 0) {
							parent.Ext.Msg.alert('系统消息', '请选择一条您需要修改的数据');
						} else if (_records.length > 1) {
							parent.Ext.Msg.confirm('系统消息',
									'修改数据只能选择一数据，请确认您是否真的修改', function(btnYes) {
										if (btnYes == 'yes' || btnYes == 'ok'
												|| btnYes == true) {
											recordGrid.getSelectionModel()
													.clearSelections();
										}
									});
						} else {
							updateUser(_records[0]);
						}
				}
			},'-',{
				text:'<b>删除</b>',
			 	disabled : true,
				id : 'deletebtn',
			 	iconCls:'userDelete',
			 	tooltip : '删除员工信息',
			 	handler : function() {
					deleteUser();
				} 
			},'-',{
				xtype : 'datefield',
    			id : 'startDate',
	    		format : 'Y-m-d',
	    		emptyText : "选择开始时间",
	    		anchor : '95%',
	    		hidden : true,
	    		width : 100,
	    		disabled : true,
	    		listeners : {
	    			'select' : function() {
	    			   var start = Ext.getCmp('startDate').getValue()
	    			      .format("Y-m-d");
	    			   Ext.getCmp('endDate').setMinValue(start);
	    		     }
    		    }
			},{
				xtype : 'combo',
				id:'comboType',
				hidden : true,
				hiddenId : 'sexName',
    			hiddenName : 'sexName',
				triggerAction : 'all',
				store : sexStore,
				allowBlank : true,
				emptyText : "请选择性别",
				forceSelection : true,
				fieldLabel:'性别名称',
				editable : true,
				mode : "local",//获取本地的值
				displayField : 'sexName',//显示值，与fields对应
				valueField : 'sex',//value值，与fields对应
				name : 'sex',
				anchor : '95%'
	    	},{
				xtype : 'combo',
				id:'comboTypeDepart',
				hidden : true,
				hiddenId : 'departName',
    			hiddenName : 'departName',
				triggerAction : 'all',
				store : menuStore,
				width:210,
				listWidth:245,
				minChars : 1,
				allowBlank : true,
				emptyText : "请选择部门名称",
				forceSelection : true,
				fieldLabel:'部门名称',
				editable : true,
				pageSize:comboxPage,
				displayField : 'departName',//显示值，与fields对应
				valueField : 'departId',//value值，与fields对应
				name : 'departId',
				anchor : '100%'
		    },{
				xtype : 'combo',
				id:'combohrsstatus',
				hidden : true,
				hiddenId : 'hrsstatus',
    			hiddenName : 'hrsstatus',
				triggerAction : 'all',
				store : hrstatusStore,
				allowBlank : true,
				emptyText : "请选择人事状态",
				forceSelection : true,
				fieldLabel:'人事状态名称',
				editable : true,
				mode : "local",//获取本地的值
				displayField : 'hrsstatusName',//显示值，与fields对应
				valueField : 'hrsstatus',//value值，与fields对应
				name : 'hrsstatus',
				anchor : '95%'
	    	},{
				xtype : 'combo',
				id:'combowork',
				hidden : true,
				hiddenId : 'workstatus',
    			hiddenName : 'workstatus',
				triggerAction : 'all',
				store : workstatusStore,
				allowBlank : true,
				emptyText : "请选择工作状态",
				forceSelection : true,
				fieldLabel:'工作状态名称',
				editable : true,
				mode : "local",//获取本地的值
				displayField : 'workstatusName',//显示值，与fields对应
				valueField : 'workstatus',//value值，与fields对应
				name : 'workstatus',
				anchor : '95%'
	    	},{
				xtype : 'datefield',
	    		id : 'endDate',
	    		format : 'Y-m-d',
	    		emptyText : "选择结束时间",
	    		hidden : true,
	    		width : 100,
	    		disabled : true,
	    		anchor : '95%'
			},{
				xtype:'textfield',
	 	        id : 'itemsValue',
		        name : 'itemsValue',
	            blankText:'查询用户数据',
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
    			store : [   ['', '查询全部'], 
			    			['LIKES_userCode', '用户编号'],
			    			['LIKES_loginName', '登录账号'],
			    			['LIKES_stationName', '岗位名称'],
			    			['LIKES_userName', '用户名'],
	    					['EQS_sex', '性别'],
	    					['EQD_birthday', '生日日期'],
			    			['EQS_workstatus', '工作状态'],
			    			['EQS_hrstatus', '人事状态'],
			    			['LIKES_manCode', '身份证号码'],
	    					['EQS_departId', "部门名称"],
	    					['LIKES_createName', '创建人'],
	    					['LIKES_updateName', '修改人'],
	    					['EQD_createTime', '创建时间'],
	    					['EQD_updateTime', '修改时间']
    					],
    					emptyText : '选择查询类型',
    					forceSelection : true,
    					listeners : {
    						'select' : function(combo, record, index) { 
    							if (combo.getValue() == 'EQD_createTime') {
    								Ext.getCmp("startDate").enable();
    								Ext.getCmp("startDate").show();

    								Ext.getCmp("endDate").enable();
    								Ext.getCmp("endDate").show();
    								
    								Ext.getCmp("itemsValue").disable();
    								Ext.getCmp("itemsValue").hide();
    								Ext.getCmp("itemsValue").setValue("");
    								
    								Ext.getCmp("combohrsstatus").disable();
    								Ext.getCmp("combohrsstatus").hide();
    								Ext.getCmp("combohrsstatus").setValue("");		
    								
    								Ext.getCmp("combowork").disable();
    								Ext.getCmp("combowork").hide();
    								Ext.getCmp("combowork").setValue("");
    								
    								Ext.getCmp("comboType").disable();
    								Ext.getCmp("comboType").hide();
    								Ext.getCmp("comboType").setValue("");
    								
    								Ext.getCmp("comboTypeDepart").disable();
    								Ext.getCmp("comboTypeDepart").hide();
    								Ext.getCmp("comboTypeDepart").setValue("");
    							}else if (combo.getValue() == 'EQD_birthday') {
    								Ext.getCmp("startDate").enable();
    								Ext.getCmp("startDate").show();

    								Ext.getCmp("endDate").enable();
    								Ext.getCmp("endDate").show();
    								
    								Ext.getCmp("itemsValue").disable();
    								Ext.getCmp("itemsValue").hide();
    								Ext.getCmp("itemsValue").setValue("");
    								
    								Ext.getCmp("combohrsstatus").disable();
    								Ext.getCmp("combohrsstatus").hide();
    								Ext.getCmp("combohrsstatus").setValue("");	
    								
    								Ext.getCmp("combowork").disable();
    								Ext.getCmp("combowork").hide();
    								Ext.getCmp("combowork").setValue("");
    								
    								Ext.getCmp("comboType").disable();
    								Ext.getCmp("comboType").hide();
    								Ext.getCmp("comboType").setValue("");
    							
    							    Ext.getCmp("comboTypeDepart").disable();
    								Ext.getCmp("comboTypeDepart").hide();
    								Ext.getCmp("comboTypeDepart").setValue("");
    							}else if(combo.getValue() == 'EQD_updateTime') {
    								Ext.getCmp("startDate").enable();
    								Ext.getCmp("startDate").show();

    								Ext.getCmp("endDate").enable();
    								Ext.getCmp("endDate").show();
    								
    							 	Ext.getCmp("itemsValue").disable();
    								Ext.getCmp("itemsValue").hide();
    								Ext.getCmp("itemsValue").setValue("");
    								
    								Ext.getCmp("combohrsstatus").disable();
    								Ext.getCmp("combohrsstatus").hide();
    								Ext.getCmp("combohrsstatus").setValue("");
    								
    								Ext.getCmp("comboType").disable();
    								Ext.getCmp("comboType").hide();
    								Ext.getCmp("comboType").setValue("");
    								
    								Ext.getCmp("combowork").disable();
    								Ext.getCmp("combowork").hide();
    								Ext.getCmp("combowork").setValue("");    
    								
    								Ext.getCmp("comboTypeDepart").disable();
    								Ext.getCmp("comboTypeDepart").hide();
    								Ext.getCmp("comboTypeDepart").setValue("");
    						 	}else if(combo.getValue() == 'EQS_departId'){
    						 		Ext.getCmp("startDate").disable();
    						  		Ext.getCmp("startDate").hide();
    								Ext.getCmp("startDate").setValue("");

    								Ext.getCmp("endDate").disable();
    								Ext.getCmp("endDate").hide();
    								Ext.getCmp("endDate").setValue("");
    						 	
    						 		Ext.getCmp("itemsValue").disable();
    								Ext.getCmp("itemsValue").hide();
    								Ext.getCmp("itemsValue").setValue("");
    						 	
    						 		Ext.getCmp("comboTypeDepart").setValue("");
    								Ext.getCmp("comboTypeDepart").show();
    								Ext.getCmp("comboTypeDepart").enable();
    								
    								Ext.getCmp("comboType").disable();
    								Ext.getCmp("comboType").hide();
    								Ext.getCmp("comboType").setValue("");
    								
    								Ext.getCmp("combowork").disable();
    								Ext.getCmp("combowork").hide();
    								Ext.getCmp("combowork").setValue("");
    								
    								Ext.getCmp("combohrsstatus").disable();
    								Ext.getCmp("combohrsstatus").hide();
    								Ext.getCmp("combohrsstatus").setValue("");
    						 	}else if(combo.getValue() == 'EQS_sex'){
    						 		Ext.getCmp("startDate").disable();
    						  		Ext.getCmp("startDate").hide();
    								Ext.getCmp("startDate").setValue("");

    								Ext.getCmp("endDate").disable();
    								Ext.getCmp("endDate").hide();
    								Ext.getCmp("endDate").setValue("");
    						 	
    						 		Ext.getCmp("itemsValue").disable();
    								Ext.getCmp("itemsValue").hide();
    								Ext.getCmp("itemsValue").setValue("");
    						 	
    						 		Ext.getCmp("comboType").setValue("");
    								Ext.getCmp("comboType").show();
    								Ext.getCmp("comboType").enable();
    								
    								Ext.getCmp("combowork").disable();
    								Ext.getCmp("combowork").hide();
    								Ext.getCmp("combowork").setValue("");
    								
    								Ext.getCmp("combohrsstatus").disable();
    								Ext.getCmp("combohrsstatus").hide();
    								Ext.getCmp("combohrsstatus").setValue("");
    								
    								Ext.getCmp("comboTypeDepart").disable();
    								Ext.getCmp("comboTypeDepart").hide();
    								Ext.getCmp("comboTypeDepart").setValue("");
    						 	}else if(combo.getValue() == 'EQS_hrstatus'){
    						 		Ext.getCmp("startDate").disable();
    						  		Ext.getCmp("startDate").hide();
    								Ext.getCmp("startDate").setValue("");

    								Ext.getCmp("endDate").disable();
    								Ext.getCmp("endDate").hide();
    								Ext.getCmp("endDate").setValue("");
    						 	
    						 		Ext.getCmp("itemsValue").disable();
    								Ext.getCmp("itemsValue").hide();
    								Ext.getCmp("itemsValue").setValue("");
    								
    								Ext.getCmp("comboType").disable();
    								Ext.getCmp("comboType").hide();
    								Ext.getCmp("comboType").setValue("");
    						 	
    						 		Ext.getCmp("combohrsstatus").setValue("");
    								Ext.getCmp("combohrsstatus").show();
    								Ext.getCmp("combohrsstatus").enable();
    								
    								Ext.getCmp("combowork").disable();
    								Ext.getCmp("combowork").hide();
    								Ext.getCmp("combowork").setValue("");
    								
    								Ext.getCmp("comboTypeDepart").disable();
    								Ext.getCmp("comboTypeDepart").hide();
    								Ext.getCmp("comboTypeDepart").setValue("");
    						 	}else if(combo.getValue() == 'EQS_workstatus'){
    						 		Ext.getCmp("combowork").setValue("");
    								Ext.getCmp("combowork").show();
    								Ext.getCmp("combowork").enable();
    						 	
    						 		Ext.getCmp("startDate").disable();
    						  		Ext.getCmp("startDate").hide();
    								Ext.getCmp("startDate").setValue("");

    								Ext.getCmp("endDate").disable();
    								Ext.getCmp("endDate").hide();
    								Ext.getCmp("endDate").setValue("");
    						 	
    						 		Ext.getCmp("itemsValue").disable();
    								Ext.getCmp("itemsValue").hide();
    								Ext.getCmp("itemsValue").setValue("");
    								
    								Ext.getCmp("comboType").disable();
    								Ext.getCmp("comboType").hide();
    								Ext.getCmp("comboType").setValue("");
    						 	
    						 		Ext.getCmp("combohrsstatus").disable();
    								Ext.getCmp("combohrsstatus").hide();
    								Ext.getCmp("combohrsstatus").setValue("");
    								
    								Ext.getCmp("comboTypeDepart").disable();
    								Ext.getCmp("comboTypeDepart").hide();
    								Ext.getCmp("comboTypeDepart").setValue("");
    						 	}else{
    						 		Ext.getCmp("combowork").disable();
    								Ext.getCmp("combowork").hide();
    								Ext.getCmp("combowork").setValue("");
    						 	
    						 		Ext.getCmp("combohrsstatus").disable();
    								Ext.getCmp("combohrsstatus").hide();
    								Ext.getCmp("combohrsstatus").setValue("");
    						 	
    						 		Ext.getCmp("comboType").disable();
    								Ext.getCmp("comboType").hide();
    								Ext.getCmp("comboType").setValue("");
    								
    					         	Ext.getCmp("startDate").disable();
    						  		Ext.getCmp("startDate").hide();
    								Ext.getCmp("startDate").setValue("");

    								Ext.getCmp("endDate").disable();
    								Ext.getCmp("endDate").hide();
    								Ext.getCmp("endDate").setValue("");
    								
    						      	Ext.getCmp("itemsValue").enable();
    								Ext.getCmp("itemsValue").show();
    							
    								Ext.getCmp("comboTypeDepart").disable();
    								Ext.getCmp("comboTypeDepart").hide();
    								Ext.getCmp("comboTypeDepart").setValue("");
    							}
    						}
    					}
    				},'-',{
    				     text : '<b>查询</b>',
    				     id : 'btn',
    				     iconCls : 'btnSearch',
    					 handler : searchLog
    				}
			],
	
			columns:[rownum,sm,
					{header: 'id', dataIndex: 'id',	hidden : true,sortable : true},
        		    {header: '用户编号' , dataIndex: 'userCode',width:60},
        			{header: '登录账号' , dataIndex: 'loginName',width:60},
			        {header: '用户名'   , dataIndex: 'userName',width:60},
			        {header: '生日类型' , dataIndex: 'birthdayType',hidden:true,width:50,renderer:function(v){
			        					return v=='0'?'农历':'阳历';
			        					}},
			        {header: '生日日期' , dataIndex: 'birthday',width:60,hidden:true,sortable : true},
    				{header: '工作状态' , dataIndex: 'workstatus',width:60,renderer:function(v){
			        					return v=='0'?'离职':'正常';
			        					}},
    			    {header: '人事状态' , dataIndex: 'hrstatus',width:60,renderer:function(v){
			        					return v=='0'?'试用期':'正常';
			        					}},
    			    {header: '身份证号码', dataIndex: 'manCode',width:130,sortable : true},
    			    {header: '办公电话'  , dataIndex: 'offTel',width:70},
    			    {header: '移动电话'  , dataIndex: 'telPhone',width:80,sortable : true},
    			    {header: '性别', dataIndex: 'sex',width:40,renderer:function(v){
			        					return v=='0'?'女':'男';
			        					}},
    			    {header: '部门名称', dataIndex: 'departName',width:110},
    			    {header: '岗位名称', dataIndex: 'stationName',width:130},
    			    {header: '从岗名称', dataIndex: 'stationNames',width:80,hidden : true},
					{header: '状态', dataIndex: 'status',renderer:function(v){
										if(v=='0'){
											return '删除';
										}else if(v=='1'){
											return '正常';
										}else{
											return '出错，无状态';
										}
			        					},width:40},
				//	{header: '安全级别', dataIndex: 'userLevel'},
					{header: '创建人', dataIndex: 'createName',width:40,hidden : true},
					{header: '创建时间', dataIndex: 'createTime',width:60,hidden : true,sortable : true},
					{header: '修改人', dataIndex: 'updateName',hidden : true,width:40},
					{header: '修改时间', dataIndex: 'updateTime',hidden : true,sortable : true,width:60},
					{header: '所属业务部门', dataIndex: 'bussDepartName',width:110},
    			    {header: '岗位职责', dataIndex: 'duty',width:120,hidden : true}],
    			    
			store : dataStore,
			bbar : new Ext.PagingToolbar({
					pageSize : pageSize, 
					store : dataStore,
					displayInfo : true,
					displayMsg : '显示第 {0} 条到 {1} 条记录，共 {2} 条',
					emptyMsg : "没有记录信息显示"
			})
	});

		function searchLog() {

				if (Ext.getCmp('comboselect').getValue() == 'EQD_createTime') {
    						var start='';
					    	var end ='';
					    	if(Ext.getCmp('startDate').getValue()!=""){
					    		var start = Ext.getCmp('startDate').getValue().format("Y-m-d");
					    	}
					    	if(Ext.getCmp('endDate').getValue()!=""){
					    		var end = Ext.getCmp('endDate').getValue().format("Y-m-d");
					    	}
    				Ext.apply(dataStore.baseParams, {
    						filter_GED_createTime : start,
    						filter_LED_createTime : end,
    						privilege:privilege,
    						checkItems : '',
    						itemsValue : ''
    				});
    			}else if (Ext.getCmp('comboselect').getValue() == 'EQD_updateTime'){
    					var start='';
					    	var end ='';
					    	if(Ext.getCmp('startDate').getValue()!=""){
					    		var start = Ext.getCmp('startDate').getValue().format("Y-m-d");
					    	}
					    	if(Ext.getCmp('endDate').getValue()!=""){
					    		var end = Ext.getCmp('endDate').getValue().format("Y-m-d");
					    	}
    					Ext.apply(dataStore.baseParams, {
    						filter_GED_updateTime : start,
    						filter_LED_updateTime : end,
    						privilege:privilege,
    						checkItems : '',
    						itemsValue : ''
    					});
    			}else if (Ext.getCmp('comboselect').getValue() == 'EQD_birthday'){
    						var start='';
					    	var end ='';
					    	if(Ext.getCmp('startDate').getValue()!=""){
					    		var start = Ext.getCmp('startDate').getValue().format("Y-m-d");
					    	}
					    	if(Ext.getCmp('endDate').getValue()!=""){
					    		var end = Ext.getCmp('endDate').getValue().format("Y-m-d");
					    	}
    				Ext.apply(dataStore.baseParams, {
    						filter_GED_birthday : start,
    						filter_LED_birthday : end,
    						privilege:privilege,
    						checkItems : '',
    						itemsValue : ''
    				});
    			}else if (Ext.getCmp('comboselect').getValue() == 'EQS_sex'){	
    					dataStore.baseParams = {
							checkItems : Ext.getCmp('comboselect').getValue(),
							privilege:privilege,
							itemsValue : Ext.get("sexName").dom.value
			   		 	}
    			}else if (Ext.getCmp('comboselect').getValue() == 'EQS_workstatus'){	
    			
    					dataStore.baseParams = {
							checkItems : Ext.get("checkItems").dom.value,
							privilege:privilege,
							itemsValue : Ext.get("workstatus").dom.value
			   		 	}
    			}else if (Ext.getCmp('comboselect').getValue() == 'EQS_hrstatus'){	
    					dataStore.baseParams = {
							checkItems : Ext.getCmp('comboselect').getValue(),
							itemsValue : Ext.get("hrsstatus").dom.value
			   		 	}
    			}else if (Ext.getCmp('comboselect').getValue() == 'EQS_departId'){	
    					dataStore.baseParams = {
							checkItems : Ext.getCmp('comboselect').getValue(),
							privilege:privilege,
							itemsValue : Ext.get("departName").dom.value
			   		 	}
    			}else {	
					dataStore.baseParams = {
						checkItems : Ext.get("checkItems").dom.value,
						privilege:privilege,
						itemsValue : Ext.get("itemsValue").dom.value
			   		 }
				}

			dataStore.reload({
					params : {
						start : 0,
						limit : pageSize
					}
				});
				
		select();
		
	}
		
		recordGrid.on('click', function() {
			select();
		});

		recordGrid.on('rowdblclick',function(grid,index,e){
		        var _records = recordGrid.getSelectionModel().getSelections();
				if (_records.length ==1) {
					updateUser(_records[0]);
				}
			 	
		});
		function select(){
			var vnetmusicRecord = recordGrid.getSelectionModel().getSelections();
				var updatebtn = Ext.getCmp('updatebtn');
				var deletebtn = Ext.getCmp('deletebtn');
				if (vnetmusicRecord.length == 1) {
					updatebtn.setDisabled(false);
					deletebtn.setDisabled(false);
				} else if (vnetmusicRecord.length > 1) {
					deletebtn.setDisabled(false);
					updatebtn.setDisabled(true);
				} else {
					deletebtn.setDisabled(true);
					updatebtn.setDisabled(true);
				}
		}
		
			function deleteUser(){
			  var _records = Ext.getCmp('userCenter').getSelectionModel().getSelections();
			  if (_records.length == 0) {
				 Ext.Msg.alert(alertTitle, '请选择一条您需要删除的数据');
				 return false;
			  }else {  
			     Ext.Msg.confirm(alertTitle,'数据删除后将不可恢复，您确定要删除这<span style="color:red">&nbsp;'+ _records.length + '&nbsp;</span>行数据吗?',function(btnYes) {
										if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
													
									        var ids = "";
    										for(var i = 0; i < _records.length; i++) {
												ids += _records[i].data.id + ",";
											}  	
										form1.getForm().doAction('submit', {
											url : sysPath+ "/user/userAction!deleteStatusById.action",
											method : 'post',
											params : {
   													ids : ids,
   													privilege:privilege
   											},
											waitMsg : '正在删除数据...',
											success : function(form1, action) {
												Ext.Msg.alert(
														alertTitle,
														action.result.msg,
														function() {
															dataStore.reload()
														});
											},
											failure : function(form1, action) {
												Ext.Msg.alert(
														alertTitle,
														action.result.msg,
														function() {
															dataStore.reload();
															 select();
														});
											}
										});
										}
									});
	   
	  }
	}
	
	
	
	function updateUser(_record) {
					 var form = new Ext.form.FormPanel({
								labelAlign : 'left',
								frame : true,
							//	url:sysPath+'/user/userAction!ralaList.action?privilege=17',
							
								bodyStyle : 'padding:5px 5px 5px',
							    width : 600,
							    labelWidth : 70,
								reader :jsonread,
					            labelAlign : "right",
						        items : [{
											layout : 'column',
											items : [{
														columnWidth : .5,
														layout : 'form',
														items : [{	id:"id",
																	name : "id",
																	xtype : "hidden"
																},{	id:"password",
																	name : "password",
																	value: "123456",
																	xtype : "hidden"
																},{
																	name : "ts",
																	xtype : "hidden"
																}, {
																	xtype : 'textfield',
																	labelAlign : 'left',
																	fieldLabel : '用户编号<span style="color:red">*</span>',
																	name : 'userCode',
																	maxLength:10,
																//	value:_record[0].data.userCode,
																	allowBlank : false,
																	blankText : "用户编号不能为空！",
																	anchor : '95%',
																	listeners : {
																		'blur':function(com){
																			Ext.getCmp('loginName').setValue(com.getValue());
																		 }
																	}
																},  {
																	xtype : 'textfield',
																	labelAlign : 'left',
																	fieldLabel : '用户姓名<span style="color:red">*</span>',
																	name : 'userName',
																	maxLength:20,
																//	value:_record[0].data.userName,
																	allowBlank : false,
																	blankText : "用户名不能为空！",
																	anchor : '95%'
																}, {xtype: 'radiogroup',
													                fieldLabel: '性别<span style="color:red">*</span>',
													                id:"sexnum",
													                columns: 3,
													                defaults: {
													                    name: 'sex' 
													                },
													                items: [{
													                    inputValue: '1',
													                    boxLabel: '男',
													                    checked:_record==null?true:(_record.data.sex=="1"?true:false)
													                }, {
													                    boxLabel: '女',
													                    inputValue: '0',
													                    checked:_record==null?false:(_record.data.sex=="0"?true:false)
													                }]
																},{
																	xtype : 'textfield',
																	labelAlign : 'left',
																	fieldLabel : '登录账号<span style="color:red">*</span>',
																	name : 'loginName',
																	id:'loginName',
																	maxLength:20,
																	//readOnly:true,
																	blankText : "登录账号不能为空！",
																	anchor : '95%'
																},  {xtype: 'radiogroup',
													                fieldLabel: '生日类型',
													                id:"birthdaynum",
													                columns: 3,
													                defaults: {
													                    name: 'birthdayType' 
													                },
													                items: [{
													                    inputValue: '0',
													                    boxLabel: '农历',
													                    checked:_record==null?true:(_record.data.birthdayType=="0"?true:false)
													                }, {
													                    inputValue: '1',
													                    boxLabel: '阳历',
													                    checked:_record==null?false:(_record.data.birthdayType=="1"?true:false)
													                }]
																}, {fieldLabel: '生日日期',
																    name: 'birthday',
																    labelAlign : 'left',
																    xtype:'datefield',
																    format : 'Y-m-d',
																    width : 150,
																    editable : false,
																//	allowBlank : false,
																	anchor : '95%'
																},{xtype : 'textfield',
																	labelAlign : 'left',
																	fieldLabel : '身份证号<span style="color:red">*</span>',
																	name : 'manCode',
																	maxLength:20,
																	allowBlank : false,
																	blankText : "身份证号码不能为空！",
																	anchor : '95%'
																},{
										        				   xtype : 'textfield',
																   labelAlign : 'left',
																   fieldLabel : '从岗名称',
																   name : 'stationNames',
																   maxLength:100,
																   anchor : '95%'
																}]

													}, {
														columnWidth : .5,
														layout : 'form',
														items : [{  xtype: 'radiogroup',
													                fieldLabel: '工作状态<span style="color:red">*</span>',
													                columns: 3,
													                id:'workstatusnum',
													                defaults: {
													                    name: 'workstatus' 
													                },
													                items: [{
													                    inputValue: '1',
													                    boxLabel: '正常',
													                    checked:_record==null?true:(_record.data.workstatus=="1"?true:false)
													                }, {
													                    inputValue: '0',
													                    boxLabel: '离职',
													                    checked:_record==null?false:(_record.data.workstatus=="0"?true:false)
													                }]
																},{xtype: 'radiogroup',
													                fieldLabel: '人事状态<span style="color:red">*</span>',
													                columns: 3,
													                id:"hrstatusnum",
													                defaults: {
													                    name: 'hrstatus' 
													                },
													                items: [{
													                    inputValue: '0',
													                    boxLabel: '试用',
													                    checked:_record==null?true:(_record.data.hrstatus=="0"?true:false)
													                },{
													                    inputValue: '1',
													                    boxLabel: '正常',
													                    checked:_record==null?false:(_record.data.hrstatus=="1"?true:false)
													                }]
																},{
																	xtype : 'numberfield',
																	labelAlign : 'left',
																	fieldLabel : '办公电话<span style="color:red">*</span>',
																	maxLength:20,
																	allowBlank : false,
																	name : 'offTel',
																	anchor : '95%',
																	blankText : "办公电话不能为空！"
																}, {
																	xtype : 'numberfield',
																	labelAlign : 'left',
																	fieldLabel : '移动电话<span style="color:red">*</span>',
																	maxLength:20,
																	allowBlank : false,
																	name : 'telPhone',
																	anchor : '95%',
																	blankText : "移动电话不能为空！"
																},{
																   xtype : 'combo',
										        				   fieldLabel: '部门名称<span style="color:red">*</span>',
														           allowBlank : false,
														           queryParam : 'filter_LIKES_departName',
															       minChars : 1,
															       listWidth:245,
															       triggerAction : 'all',
																   typeAhead:false,
															       forceSelection : true,
																   store: menuStore,
																   id:'departIdNa',
																//   minListWidth:'auto',
																   pageSize : comboxPage,
																   displayField : 'departName',
																   valueField : 'departId',
																   hiddenName : 'departId',
																   name:'departName',
																   blankText : "部门名称不能为空！",
																   anchor : '95%',
																   emptyText : "请选择部门名称"
																
										        				},{
																   xtype : 'combo',
										        				   fieldLabel: '岗位名称<span style="color:red">*</span>',
														           allowBlank : false,
														           typeAhead:false,
														           forceSelection : true,
															       minChars : 1,
															       listWidth:245,
															       id:'stationIdNa',
															       name:'stationName',
															      // minListWidth:'95%',
															       triggerAction : 'all',
																   store: stationStore,
																   pageSize : comboxPage,
																   queryParam : 'filter_LIKES_stationName',
																   displayField : 'stationName',
																   valueField : 'stationId',
																   hiddenName : 'stationId',
																   blankText : "岗位名称不能为空！",
																   anchor : '95%',
																   emptyText : "请选择岗位名称"
											
										        				},{
																   xtype : 'combo',
										        				   fieldLabel: '业务部门<span style="color:red">*</span>',
														           allowBlank : false,
															       minChars : 1,
															       listWidth:245,
															       name:'bussDepartName',
															       typeAhead:false,
															     //  minListWidth:'auto',
															       blankText : "所属业务部门不能为空！",
															       forceSelection : true,
															       triggerAction : 'all',
																   store: bussStore,
																   pageSize : comboxPage,
																   queryParam : 'filter_LIKES_departName',
																   displayField : 'bussDepartName',
																   valueField : 'bussDepart',
																   hiddenName : 'bussDepart',
																   id:'bussDepartNa',
																   anchor : '95%',
																   emptyText : "请选择所属业务部门"	,
																   enableKeyEvents:true,
																		listeners : {
															          		select:function(com){
															          		 
															          		 }
															 				
															 			}
										        				}]
													}]
													
										},{
											labelAlign : 'top',
											xtype : 'textarea',
											name : 'duty',
											maxLength:500,
											fieldLabel : '岗位职责',
											height : 50,
											width:'95%'
										}]
										});
		
							
		carTitle='增加用户信息';
		//Ext.getCmp('bussDepartNa').setValue(205);
		//Ext.getCmp('bussDepartNa').setRawValue('广州配送中心');
		if(_record!=null){
			carTitle='修改用户信息';
		//	form.getForm().loadRecord(_record);
		//	menuStore.load();
			//stationStore.load();
		//	bussStore.load();
			form.load({
				url : sysPath+ "/"+ralaListUrl,
				params:{filter_EQL_id: _record.data.id, privilege:privilege,limit : pageSize},
				success : function(response){
					Ext.getCmp('bussDepartNa').setRawValue(_record.data.bussDepartName);
		   			Ext.getCmp('departIdNa').setRawValue(_record.data.departName);
		    		Ext.getCmp('stationIdNa').setRawValue(_record.data.stationName);
				}
			})
			
			
		}
			
		var win = new Ext.Window({
			title : carTitle,
			width : 600,
			closeAction : 'hide',
			plain : true,
		    resizable : false,	
			modal : true,
			items : form,
			buttonAlign : "center",	buttons : [{
				text : "保存",
				iconCls : 'groupSave',
				handler : function() {
					if (form.getForm().isValid()) {
						this.disabled = true;//只能点击一次
						form.getForm().submit({
							url : sysPath+ '/'+saveUrl,
							params:{
								privilege:privilege,
								status:1,
								limit : pageSize},
							waitMsg : '正在保存数据...',
							success : function(form, action) {
										win.hide(), 
										Ext.Msg.alert(alertTitle,action.result.msg, function() {
												reload();
										});
							},
							failure : function(form, action) {
								this.disabled = false;//只能点击一次
								if (action.faliureType == Ext.form.Action.SERVER_INVALID) {
									Ext.Msg.alert(alertTitle, "服务器连接异常，请联系管理员！");
								} else {
									if (!action.result.success) {
										Ext.Msg.alert(alertTitle,action.result.msg, function() {
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
						if(_record==null){
							form.getForm().reset();
						
						}
						if(_record!=null){
							carTitle='修改用户信息';
						//	menuStore.load();
						//	stationStore.load();
						//	bussStore.load();
							form.load({
							    url : sysPath+"/"+ralaListUrl,
							    params:{filter_EQL_id:_record.data.id,privilege:privilege,limit : pageSize},
							    success : function(response){
									Ext.getCmp('bussDepartNa').setRawValue(_record.data.bussDepartName);
						   			Ext.getCmp('departIdNa').setRawValue(_record.data.departName);
						    		Ext.getCmp('stationIdNa').setRawValue(_record.data.stationName);
								}
						   });
						   Ext.getCmp("sexnum").reset();
						   Ext.getCmp("birthdaynum").reset();
						   Ext.getCmp("workstatusnum").reset();
						   Ext.getCmp("hrstatusnum").reset();
						      
									
		             	}
					}
				 }, {
						text : "取消",
						handler : function() {
						   win.close();
						   select();
						}
					}]
								
		
		});
	
		win.on('hide', function() {
					form.destroy();
					select();
				});
		
		win.show();
		
 }

	function reload(){
		 dataStore.reload()
	}

});



















	Ext.QuickTips.init();
	var privilege=238;
	var comboxPage=15;
	var saveUrl="fi/fiBusinessFeePriceAction!save.action";
	var searchCustomerUrl='sys/customerAction!list.action';//查询客商地址
	var ralaListUrl="fi/fiBusinessFeePriceAction!list.action";
	
Ext.onReady(function(){
	Ext.QuickTips.init();
	var rownum=new Ext.grid.RowNumberer({
		header:'序号',
        width : 35,
		sortable : true	
	});
 
 	    var fields=['id',
					'customerId',  
					'customerName',           // 发货代理
					'settlement',         // 费率类型（重量/营业额/固定金额）
					'rate',               //费率
					'departName',       //业务部门
					'departId',        //业务部门
					'createTime',             //创建时间
					'createName',           //创建人
					'updateTime',             //修改时间
					'updateName',          //修改人
					'remark',        //备注
					'isDelete',     //作废状态(0：已作废；1：正常)
					'ts',          //时间戳
					'accountNum',   //收款账号
					'incomeCustomerName',  //收款客商
					'bank',             //开户行
					'phone',   //联系方式
					'reviewUser',    //审核人
					'reviewDate',      //审核时间
					'status',   //状态(0：新增；1：已审核)
					'incomeCustomerId'];
	
	var jsonread= new Ext.data.JsonReader(
    	         {  root:'result',
                    totalProperty:'totalCount'
                  },fields);
                  
	var sm = new Ext.grid.CheckboxSelectionModel();
	
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
                proxy: new Ext.data.HttpProxy({url:sysPath+"/"+ralaListUrl}),
                baseParams:{
                	filter_EQL_isDelete:1,
                	limit: pageSize
                },
                reader:jsonread
    });
    
	//权限部门
	var rightDepartStore2 = new Ext.data.Store({ 
            storeId:"rightDepartStore2",                        
            proxy: new Ext.data.HttpProxy({url:sysPath+"/sys/basRightDepartAction!findDepartName.action",method:'post'}),
            baseParams:{
            	privilege:63,
            	filter_EQL_userId:userId
            },
            reader: new Ext.data.JsonReader({
            root: 'resultMap', totalProperty: 'totalCount'
           }, [{name:'departName', mapping:'DEPARTNAME',type:'string'},
               {name:'departId', mapping:'RIGHTDEPARTID',type:'string'}             
              ])    
    });
    
	//客商名称
	customerStore= new Ext.data.Store({
		autoLoad:true,
		storeId:"customerStore",
		baseParams:{privilege:61},
		proxy:new Ext.data.HttpProxy({url:sysPath+"/"+searchCustomerUrl}),
		reader:new Ext.data.JsonReader({
        	root:'result', totalProperty:'totalCount'
        },[
       	{name:'cusName',mapping:'cusName'},
       	{name:'cusId',mapping:'id'}
       	])
	});
	
	//计算方式
	var settlementStore=new Ext.data.SimpleStore({
		 auteLoad:true, //此处设置为自动加载
 		 data:[['1','重量'],['0','营业额'],['2','固定金额']],
  		 fields:["id","name"]
	});
	
	 var onebar = new Ext.Toolbar({	frame : true,
		items : ['&nbsp;&nbsp;','创建日期：',{
    		xtype : 'datefield',
    		id : 'startDate',
    		format : 'Y-m-d',
    		emptyText : "开始时间",
    		width:80,
    		editable : true,
    		listeners : {
    			'select' : function() {
    			   var start = Ext.getCmp('startDate').getValue().format("Y-m-d");
    			   Ext.getCmp('endDate').setMinValue(start);
    		     }
    		}
    	}, '&nbsp;至&nbsp;', {
    		xtype : 'datefield',
    		id : 'endDate',
    		format : 'Y-m-d',
    		emptyText : "结束时间",
    		width:80,
    		editable : true
    	},'-','&nbsp;&nbsp;','创建部门：',{
			xtype : 'combo',
			id:'comboRightDepart2',
			hiddenId : 'dictionaryName',
   			hiddenName : 'olist[0].rightDepartId',
			triggerAction : 'all',
			store : rightDepartStore2,
			mode:'local',
			width:90,
			listWidth:245,
			allowBlank : false,
			emptyText : "请选择部门名称",
			forceSelection : false,
			fieldLabel:'部门',
			editable : false,
			pageSize:500,
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
				 		
				 },'focus':function(){  
					Ext.getCmp("comboRightDepart2").selectText();
            										 }, expand:function(){
            										 	if(rightDepartStore2.getCount()==0){
            										 		var store=new Ext.data.Record.create([{name:'departName'},{name:'departId'}]);
						var record=new store();
						record.set("departId",bussDepart);
						record.set("departName",bussDepartName);
						rightDepartStore2.add(record);
            										 	}
            										 }
		 	}
     },'-','&nbsp;&nbsp;',{
			xtype:'textfield',
 	        id : 'itemsValue',
	        name : 'itemsValue',
            blankText:'查询数据字典',
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
				typeAhead : false,
				forceSelection : true,
				width : 100,
				hidden:true,
				diabled:true,
				id:'comboType',
				mode:'local',
				triggerAction : 'all',
				store : settlementStore,
				displayField : 'name',
				valueField : 'name',
				emptyText : "请选择计算方式",
				enableKeyEvents:true,
	            listeners : {
	 				keyup:function(textField, e){
	                     if(e.getKey() == 13){
	                     	searchLog();
	                     }
	 				}
	 			}
					
			},'&nbsp;&nbsp;',{
                xtype : "combo",
    			id:"comboselect",
   				width : 100,
 				triggerAction : 'all',
    			model : 'local',
    			hiddenId : 'checkItems',
    			hiddenName : 'checkItems',
    			name : 'checkItemstext',
    			store : [['','查询全部'],
						 ['LIKES_customerName','发货代理'],
						 ['EQS_settlement','计算方式'],
						 ['LIKES_incomeCustomerName','收款方'],
						 ['LIKES_accountNum','收款账号'],
						 ['LIKES_bank','开户银行']
						 ],
				emptyText : '选择查询类型',
				forceSelection : true,
					listeners : {
					'select' : function(combo, record, index) { // override default onSelect to
											// do redirect
						if (combo.getValue() == '') {
							Ext.getCmp("comboType").disable();
							Ext.getCmp("comboType").hide();
							Ext.getCmp("comboType").setValue("");
	
							Ext.getCmp("itemsValue").disable();
							Ext.getCmp("itemsValue").show();
							Ext.getCmp("itemsValue").setValue("");
						}else if(combo.getValue() == 'EQS_settlement') {
							Ext.getCmp("comboType").setValue("");
							Ext.getCmp("comboType").show();
							Ext.getCmp("comboType").enable();
							
							Ext.getCmp("itemsValue").disable();
							Ext.getCmp("itemsValue").hide();
							Ext.getCmp("itemsValue").setValue("");
						}else{
							Ext.getCmp("comboType").disable();
							Ext.getCmp("comboType").hide();
							Ext.getCmp("comboType").setValue("");
							
							Ext.getCmp("itemsValue").enable();
							Ext.getCmp("itemsValue").show();
						}
					}
				}
			},'-']
		});
	
	var recordGrid=new Ext.grid.GridPanel({
	//	renderTo:'recordGrid',
		renderTo:Ext.getBody(),
		id:'userCenter',
		height : Ext.lib.Dom.getViewHeight()-1,
		width : Ext.lib.Dom.getViewWidth()-1,
		autoScroll : true,
		viewConfig : {
			columnsText : "显示的列",
			sortAscText : "升序",
			sortDescText : "降序",
			forceFit : true
		},
		autoExpandColumn : 1,
		frame : false,
		loadMask : true,
		sm:sm,
		stripeRows : true,
		tbar:['&nbsp;&nbsp;',{
				text:'<B>新增</B>',
				iconCls : 'userAdd',
				id:'addbtn',
				handler : function() {
						updateUser();
				}
			}, '-',{
				text : '<B>修改</B>',
				id : 'updatebtn',
				disabled : true,
				iconCls:'userEdit',
				handler : function() {
						var _records = recordGrid.getSelectionModel().getSelections();
						if(_records.length == 0) {
							Ext.Msg.alert(alertTitle, '请选择一条您需要修改的数据');
						}else if(_records.length > 1) {
							Ext.Msg.alert(alertTitle,' 一次只能选择修改一条数据');
						}else{
							if(_records[0].get('status')==0){
							 	updateUser(_records[0]);
							}else{
								Ext.Msg.alert(alertTitle,'已经审核过的数据，不能再修改');
							}
						}
				}
			},'-',{
				text:'<b>审核</b>',
			 	disabled : true,
				id : 'audit',
			 	iconCls:'groupPass',
			 	handler : function() {
			 		var _records = recordGrid.getSelectionModel().getSelections();
					if(_records.length == 0) {
						Ext.Msg.alert(alertTitle, '请选择您要审核的数据');
					}else{
						for(var i=0;i<_records.length;i++){
							if(_records[i].get('status')!=0){
								Ext.Msg.alert(alertTitle,'已经审核过的数据，不能重复审核');
								return;
							}
						}
						auditPrice(_records);
					}
				} 
			},'-',{
				text:'<b>删除</b>',
			 	disabled : true,
				id : 'deletebtn',
			 	iconCls:'userDelete',
			 	handler : function() {
					deletePrice();
				} 
			},'-',{
			     text : '<b>搜索</b>',
			     id : 'btn',
			     iconCls : 'btnSearch',
				 handler : searchLog
			}],
			columns:[rownum,sm,
        		    {header: 'ID', dataIndex: 'id',hidden:true,sortable : true},
			      	{header: '发货代理ID', dataIndex: 'customerId',hidden:true,width:50,sortable : true},
					{header: '发货代理', dataIndex: 'customerName',width:110,sortable : true},
    			    
    			    {header: '计算方式', dataIndex: 'settlement',width:55},
    			    {header: '费率', dataIndex: 'rate',width:80,hidden : true},
					{header: '收款方ID', dataIndex: 'incomeCustomerId',hidden:true,width:50,sortable : true},
					{header: '收款方', dataIndex: 'incomeCustomerName',width:110,sortable : true},
        			{header: '收款账号', dataIndex: 'accountNum',sortable : true},
    			    {header: '开户行', dataIndex: 'bank',width:80},
    				{header: '联系方式', dataIndex: 'phone',width:80},
					{header: '审核状态', dataIndex: 'status',renderer:function(v){
										if(v=='0'){
											return '新增';
										}else if(v=='1'){
											return '已审核';
										}else{
											return v;
										}
			        					},width:55},
			    	{header: '审核人', dataIndex: 'reviewUser',width:60,sortable : true},
			        {header: '审核时间', dataIndex: 'reviewDate',width:110,sortable : true},　
			       	{header: '部门ID', dataIndex: 'departId',hidden : true,width:110},
			       	{header: '部门名称', dataIndex: 'departName',width:110},
					{header: '创建人', dataIndex: 'createName',width:40,hidden : true},
					{header: '创建时间', dataIndex: 'createTime',width:60,hidden : true,sortable : true},
					{header: '修改人', dataIndex: 'updateName',hidden : true,width:40},
					{header: '修改时间', dataIndex: 'updateTime',hidden : true,sortable : true,width:60},
			        {header: '时间戳', dataIndex: 'ts',width:55,hidden:true,sortable : true},
    			    {header: '备注', dataIndex: 'remark',width:120}],
			store : dataStore,
			listeners: {
                    render: function(){
                        onebar.render(this.tbar);
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

		function searchLog() {
			var comboRightDepart2 =Ext.getCmp('comboRightDepart2').validate();
			if(!comboRightDepart2){
				return;
			}
			
			var start='';
	    	var end ='';
			var dId= Ext.getCmp('comboRightDepart2').getValue();
	    	if(Ext.getCmp('startDate').getValue()!=""){
	    		var start = Ext.getCmp('startDate').getValue().format("Y-m-d");
	    	}
	    	if(Ext.getCmp('endDate').getValue()!=""){
	    		var end = Ext.getCmp('endDate').getValue().format("Y-m-d");
	    	}
			 if(Ext.getCmp('comboselect').getValue()=='EQS_settlement'){
			 	 dataStore.baseParams = {
					checkItems : Ext.get("checkItems").dom.value,
				 	itemsValue : Ext.get("comboType").dom.value,
				 	filter_EQL_isDelete:1,
				 	filter_EQL_departId:dId,
				 	filter_GED_createTime:(!Ext.getCmp("startDate").hidden?(Ext.get("startDate").dom.value=="开始时间"?"":Ext.get("startDate").dom.value):""),
					filter_LED_createTime:(!Ext.getCmp("endDate").hidden?(Ext.get("endDate").dom.value=="结束时间"?"":Ext.get("endDate").dom.value):""),
				 	limit : pageSize
				 }
			 }else{
			 	dataStore.baseParams = {
					checkItems : Ext.get("checkItems").dom.value,
				 	itemsValue : Ext.get("itemsValue").dom.value,
				 	filter_GED_createTime:(!Ext.getCmp("startDate").hidden?(Ext.get("startDate").dom.value=="开始时间"?"":Ext.get("startDate").dom.value):""),
					filter_LED_createTime:(!Ext.getCmp("endDate").hidden?(Ext.get("endDate").dom.value=="结束时间"?"":Ext.get("endDate").dom.value):""),
				 	filter_EQL_isDelete:1,
				 	filter_EQL_departId:dId,
				 	limit : pageSize
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

		function select(){
			var vnetmusicRecord = recordGrid.getSelectionModel().getSelections();
			var updatebtn = Ext.getCmp('updatebtn');
			var deletebtn = Ext.getCmp('deletebtn');
			var audit = Ext.getCmp('audit');
			if (vnetmusicRecord.length == 1) {
				updatebtn.setDisabled(false);
				deletebtn.setDisabled(false);
				audit.setDisabled(false);
			} else if (vnetmusicRecord.length > 1) {
				deletebtn.setDisabled(false);
				updatebtn.setDisabled(true);
				audit.setDisabled(false);
			} else {
				deletebtn.setDisabled(true);
				updatebtn.setDisabled(true);
				audit.setDisabled(true);
			}
		}
	
	function auditPrice(records){
		Ext.Msg.confirm(alertTitle,'您确定要审核这<span style="color:red">&nbsp;'+ records.length + '&nbsp;</span>行数据吗?',function(btnYes){
			if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
		        var ids="";
				for(var i=0;i<records.length;i++){
 					if(i==0){
						ids += "aa["+i+"].id="+records[i].get("id")+"&aa["+i+"].ts="+records[i].get("ts");
					}else{
						ids += "&aa["+i+"].id="+records[i].get("id")+"&aa["+i+"].ts="+records[i].get("ts");
					}
				}	
				ids+="&privilege="+privilege;  	
				form1.getForm().doAction('submit', {
					url : sysPath+ "/fi/fiBusinessFeePriceAction!audit.action",
					method : 'post',
					params : ids,
					waitMsg : '正在处理数据...',
					success : function(form1, action) {
						Ext.Msg.alert(alertTitle,"数据处理成功",function() {
									dataStore.reload();
								});
					},
					failure : function(form1, action) {
						Ext.Msg.alert(alertTitle,"数据处理失败",function() {
									dataStore.reload();
									select();
								});
					}
				});
			}
		});
	}
	
		/*
		* 删除
		*/
	function deletePrice(){
		  var _records = Ext.getCmp('userCenter').getSelectionModel().getSelections();
		  if (_records.length == 0) {
			 Ext.Msg.alert(alertTitle, '请选择一条您需要删除的数据');
			 return;
		  }else {
		     Ext.Msg.confirm(alertTitle,'数据删除后将不可恢复，您确定要删除这<span style="color:red">&nbsp;'+ _records.length + '&nbsp;</span>行数据吗?',function(btnYes){
				if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
			        var ids = "";
					for(var i = 0; i < _records.length; i++) {
						ids += _records[i].data.id + ",";
					}  	
					form1.getForm().doAction('submit', {
						url : sysPath+ "/fi/fiBusinessFeePriceAction!deleteByStatus.action",
						method : 'post',
						params : {
							ids : ids,
							privilege:privilege
						},
						waitMsg : '正在删除数据...',
						success : function(form1, action) {
							Ext.Msg.alert(alertTitle,"数据删除成功",function() {
										dataStore.reload();
									});
						},
						failure : function(form1, action) {
							Ext.Msg.alert(
									alertTitle,
									"数据删除失败",
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
																},{
																	name : "ts",
																	xtype : "hidden"
																},{xtype : 'combo',
																	typeAhead : true,
																	queryParam : 'filter_LIKES_cusName',
																	triggerAction : 'all',
																	id:'cusId',
																	store : customerStore,
																	pageSize : comboSize,
																	allowBlank : false,
																	forceSelection : true,
																	selectOnFocus:true,
																	resizable:true,
																	listWidth:245,
																	minChars :1,
																	fieldLabel : '发货客商<font style="color:red;">*</font>',
																	mode : "remote",//从服务器端加载值
																	valueField : 'cusId',//value值，与fields对应
																	displayField : 'cusName',//显示值，与fields对应
																	hiddenName:'customerId',
																	anchor : '95%'
																},{
																	xtype : 'numberfield',
										                           	 name:'rate',
										                           	 id:'rate',
										                           	 anchor : '95%',
										                           	 allowNegative:true,
										                           	 decimalPrecision:2,
										                           	 maxLength : 20,
										                           	 fieldLabel:'费率<span style="color:red">*</span>',
										                           	 allowBlank:false,blankText:'费率不能为空！',
																	 enableKeyEvents:true,
																	 listeners : {
																		'focus':function(v){  
																				v.selectText();
										              					 }, 
															 				keyup:function(numberfield, e){
															                     if(e.getKey() == 13 ){
																					Ext.getCmp('remark').focus();
															                     }
															 				}
														 			 }
													 			},{
																	xtype : 'textfield',
																	labelAlign : 'left',
																	fieldLabel : '收款账号<span style="color:red">*</span>',
																	name : 'accountNum',
																	id:'accountNum',
																	maxLength:50,
																	readOnly:false,
																	blankText : "账号不能为空！",
																	anchor : '95%'
																},{xtype : 'textfield',
																	labelAlign : 'left',
																	fieldLabel : '联系方式<span style="color:red">*</span>',
																	name : 'phone',
																	maxLength:20,
																	allowBlank : false,
																	blankText : "联系方式不能为空！",
																	anchor : '95%'
																}]

													}, {
														columnWidth : .5,
														layout : 'form',
														items : [{
																   xtype : 'combo',
										        				   fieldLabel: '计算方式<span style="color:red">*</span>',
														           allowBlank : false,
															       triggerAction : 'all',
																   typeAhead:false,
															       forceSelection : true,
																   store: settlementStore,
																   id:'settlement',
																   mode : "local",//获取本地的值
																   displayField : 'name',
																   valueField : 'name',
																   name:'settlement',
																   blankText : "计算方式不能为空！",
																   anchor : '95%',
																   emptyText : "请选择费率计算方式"
																
										        			 },{xtype : 'combo',
																	typeAhead : true,
																	queryParam : 'filter_LIKES_cusName',
																	triggerAction : 'all',
																	id:'incomeCusId',
																	store : customerStore,
																	pageSize : comboSize,
																	allowBlank : false,
																	forceSelection : true,
																	selectOnFocus:true,
																	resizable:true,
																	listWidth:245,
																	minChars :1,
																	fieldLabel : '收款方<font style="color:red;">*</font>',
																	mode : "remote",//从服务器端加载值
																	valueField : 'cusId',//value值，与fields对应
																	displayField : 'cusName',//显示值，与fields对应
																	hiddenName:'incomeCustomerId',
																	anchor : '95%'
																},{
																	xtype : 'textfield',
																	labelAlign : 'left',
																	fieldLabel : '开户行<span style="color:red">*</span>',
																	name : 'bank',
																	id:'bank',
																	maxLength:50,
																	readOnly:false,
																	blankText : "开户行不能为空！",
																	anchor : '95%'
																}]
													}]
													
										},{
											labelAlign : 'top',
											xtype : 'textarea',
											name : 'remark',
											maxLength:500,
											fieldLabel : '备注',
											height : 50,
											width:'95%'
										}]
										});
		
							
				carTitle='增加业务费协议价';
				if(_record!=null){
					carTitle='修改业务费协议价';
					form.load({
						url : sysPath+ "/"+ralaListUrl,
						params:{filter_EQL_id: _record.data.id, privilege:privilege,limit : pageSize},
						success : function(response){
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
										customerName:Ext.getCmp('cusId').getRawValue(),
										incomeCustomerName:Ext.getCmp('incomeCusId').getRawValue(),
										limit : pageSize},
									waitMsg : '正在保存数据...',
									success : function(form, action) {
												win.hide(), 
												Ext.Msg.alert(alertTitle,action.result.msg, function() {
														dataStore.reload();
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
							this.disabled = false;//
						}
					}, {
						text : "重置",
						iconCls : 'refresh',
						handler : function() {		
								if(_record==null){
									form.getForm().reset();
								
								}else{
									carTitle='修改业务费协议价';
									form.load({
									    url : sysPath+"/"+ralaListUrl,
									    params:{filter_EQL_id:_record.data.id,privilege:privilege,limit : pageSize},
									    success : function(response){
											Ext.getCmp('bussDepartNa').setRawValue(_record.data.bussDepartName);
								   			Ext.getCmp('departIdNa').setRawValue(_record.data.departName);
								    		Ext.getCmp('stationIdNa').setRawValue(_record.data.stationName);
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
							select();
						});
				
				win.show();
				
		 }

});
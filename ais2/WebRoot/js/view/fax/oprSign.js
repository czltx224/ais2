//签收录入JS
var signUrl="stock/oprSignAction!ralaList.action";
var requestStage='签收';
var privilege=89;
var  fields=[{name:'id'},
			{name:'dno'},
			{name:'signMan'},
			{name:'identityCard'},
     		{name:'replaceSign'},
     		{name:'reIdentityCard'},
     		{name:'scanAdd'},
     		{name:'signSource'},
     		{name:'createName'},
     		{name:'createTime'},
     		{name:'updateTime'},
     		{name:'updateName'},
     		{name:'ts'},
     		{name:'departId'},
     		{name:'cusId'},
     		{name:'cusName'},
     		{name:'mainNo'},
     		{name:'subNo'},
     		{name:'flightNo'},
     		{name:'distributionMode'},
     		{name:'takeMode'},
     		{name:'consignee'},
     		{name:'consigneeTel'},
     		{name:'signType'},
     		{name:'faxTime'},
     		{name:'isSignException'},'cardType','remark','signStatus'];
	exceptionStore=new Ext.data.SimpleStore({
			 autoLoad:true, //此处设置为自动加载
  			  data:[['1','是'],['0','否']],
   			 fields:["propertyId","propertyName"]
		});

Ext.onReady(function() {
		
    	var signStore = new Ext.data.Store({
    		    baseParams:{privilege:privilege,limit:pageSize,filter_EQL_faxStatus:1},
                proxy: new Ext.data.HttpProxy({url:sysPath+'/'+signUrl}),
                reader:new Ext.data.JsonReader({
            	root: 'result', totalProperty: 'totalCount'
        		}, fields)
        });
		
		var flightStore=new Ext.data.Store({
			storeId:"flightStore",
			proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/basFlightAction!ralaList.action"}),
			baseParams:{limit:pageSize,privilege:62},
			reader:new Ext.data.JsonReader({
	                    root:'result',
	                    totalProperty:'totalCount'
	        },[
	        	{name:'flightId',mapping:'id'},
	        	{name:'flightNumber'}
	        	])
		});
		var cusStore=new Ext.data.Store({
			storeId:"cusStore",
			proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/customerAction!list.action"}),
			reader:new Ext.data.JsonReader({
	                    root:'result',
	                    totalProperty:'totalCount'
	        },[
	        	{name:'id'},
	        	{name:'cusName'}
	        	])
		});
		
		var devModeStore=new Ext.data.Store({
			storeId:"devModeStore",
			baseParams:{filter_EQL_basDictionaryId:4,privilege:16},
			proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/dictionaryAction!ralaList.action"}),
			reader:new Ext.data.JsonReader({
	                    root:'result',
	                    totalProperty:'totalCount'
	        },[
	        	{name:'id'},
	        	{name:'develpMode',mapping:'typeName'}
	        	])
		});
		
		var signSourceStore=new Ext.data.Store({
			storeId:"signSourceStore",
			baseParams:{filter_EQL_basDictionaryId:34,privilege:16},
			proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/dictionaryAction!ralaList.action"}),
			reader:new Ext.data.JsonReader({
	                    root:'result',
	                    totalProperty:'totalCount'
	        },[
	        	{name:'id'},
	        	{name:'signSource',mapping:'typeName'}
	        	])
		});
		
		var takeModeStore=new Ext.data.Store({
			storeId:"takeModeStore",
			baseParams:{filter_EQL_basDictionaryId:14,privilege:16},
			proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/dictionaryAction!ralaList.action"}),
			reader:new Ext.data.JsonReader({
	                    root:'result',
	                    totalProperty:'totalCount'
	        },[
	        	{name:'id'},
	        	{name:'takeMode',mapping:'typeName'}
	        	])
		});
    	var tb = new Ext.Toolbar({
    		width : Ext.lib.Dom.getViewWidth(),
    		items : ['&nbsp;&nbsp;', {
    					text : '<B>签收录入</B>',
    					id : 'confirmbtn',
    					tooltip : '手动录入签收',
    					iconCls : 'groupAdd',
    					handler:function(){
    						signInput();
    					}
    				}, '-', {
    					text : '<B>签收作废</B>',
    					id : 'delbtn',
    					tooltip : '签收作废',
    					disabled:true,
    					iconCls : 'groupEdit',
    					handler:function(){
    						Ext.Msg.confirm(alertTitle, "确定作废吗？", function(btnYes) {
	    						if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
			    					var _record = recordGrid.getSelectionModel().getSelections();
			    					var vdons ='';
			    					for(var i=0;i<_record.length;i++){
			    						vdons+=+_record[i].data.dno+',';
			    					}
			    					
			    						if(_record.length<1){
			    							Ext.Msg.alert(alertTitle, "请选择您要操作的行！");
			    							return false;
			    						}else{
			    							Ext.Ajax.request({
												url : sysPath+ "/stock/oprSignAction!signReturn.action",
												params : {
													dnos:vdons,
													privilege:privilege
												},
												success : function(resp) {
													var respText = Ext.util.JSON.decode(resp.responseText);
													if(respText.success){
														Ext.Msg.alert(alertTitle,"作废成功<br/>");
														searchSign();
													}else{
														Ext.Msg.alert(alertTitle,respText.msg);
													}
												}
											});
			    						}
			    				}
    						})
    					}
    				}, '-', '&nbsp;', {
    					xtype :'textfield',
    					id : 'searchContent',
    					hidden:true,
    					name : 'itemsValue',
    					width : 120,
    					enableKeyEvents:true,
    					listeners:{
    						 keyup:function(textField, e){
    							if(e.getKey()==13){
    								searchSign();
    							}
    						}
    					}
    				}, {
    					xtype:'combo',
    					fieldLabel:'签收来源',
    					store:signSourceStore,
    					width : 120,
    					minChar:1,
			    		triggerAction : 'all',
			    		model : 'local',
			    		queryParam : 'filter_LIKES_typeName',
			    		pageSize:pageSize,
			    		id:'combosignsource',
			    		valueField : 'id',
    					displayField : 'signSource',
    					hiddenName : 'id',
    					hidden:true,
			    		forceSelection : true,
			    		enableKeyEvents:true,
    					listeners:{
    						 keyup:function(textField, e){
    							if(e.getKey()==13){
    								searchSign();
    							}
    						}
    					}
    				},{
    					xtype:'combo',
    					fieldLabel:'航班号',
    					store:flightStore,
    					width : 120,
    					minChars : 0,
			    		triggerAction : 'all',
			    		model : 'local',
			    		queryParam : 'filter_LIKES_flightNumber',
			    		pageSize:20,
			    		pageSize:pageSize,
			    		id:'comboflight',
			    		valueField : 'id',
    					displayField : 'flightNumber',
    					hiddenName : 'id',
    					hidden:true,
			    		emptyText : '选择类型',
			    		forceSelection : true,
			    		enableKeyEvents:true,
    					listeners:{
    						 keyup:function(textField, e){
    							if(e.getKey()==13){
    								searchSign();
    							}
    						}
    					}
    				},{
    					xtype:'combo',
    					fieldLabel:'配送方式',
    					width : 120,
    					triggerAction : 'all',
    					queryParam : 'filter_LIKES_typeName',
			    		id:'combodevelpmode',
			    		valueField : 'id',
			    		store:devModeStore,
    					displayField : 'develpMode',
    					hiddenName : 'id',
    					hidden:true,
			    		emptyText : '选择类型',
			    		forceSelection : true,
			    		enableKeyEvents:true,
    					listeners:{
    						 keyup:function(textField, e){
    							if(e.getKey()==13){
    								searchSign();
    							}
    						}
    					}
    				},{
    					xtype:'combo',
    					fieldLabel:'提货方式',
    					store:takeModeStore,
    					triggerAction : 'all',
    					queryParam : 'filter_LIKES_typeName',
    					forceSelection : true,
    					id:'combotakemode',
			    		valueField : 'id',
    					displayField : 'takeMode',
    					hiddenName : 'id',
    					hidden:true,
    					emptyText : '选择类型',
    					width:120,
    					enableKeyEvents:true,
    					listeners:{
    						 keyup:function(textField, e){
    							if(e.getKey()==13){
    								searchSign();
    							}
    						}
    					}
    				},'-', '&nbsp;', {
    					xtype : 'datefield',
    					format:'Y-m-d',
    					hidden:false,
    					id : 'datefieldstart',
    					name : 'startTime',
    					value:new Date().add(Date.DAY, -7),
    					width : 120
    				} ,'-', '&nbsp;', {
    					xtype : 'datefield',
    					id:'datefieldend',
    					format:'Y-m-d',
    					value:new Date(),
    					hidden:false,
    					name : 'endTime',
    					width : 120
    				}, {
    					xtype : "combo",
    					width : 120,
    					triggerAction : 'all',
    					id:'comboCheckItems',
    					model : 'local',
    					hiddenId : 'checkItems',
    					hiddenName : 'checkItems',
    					name : 'checkItemstext',
    					store : [
    							['', '查询全部'],
    							['LIKES_faxTime', '传真时间'],
    							['LIKES_createTime', '签收时间'],
    							['LIKES_mainNo',"主单号"],
    							['EQS_flightNo',"航班号"],
    							//['EQS_cardType','证件类型'],
    							['EQS_replaceSign','代签人'],
    							['EQS_signMan','签收人'],
    							['EQS_signSource','签收来源'],
    							['EQS_distributionMode','配送方式'],
    							['EQS_takeMode','提货方式']
    							],
    					emptyText : '选择类型',
    					forceSelection : true,
    					listeners : {
    						'select' : function(combo, record, index) { // override
    							if (combo.getValue() == '') {
    								Ext.getCmp("searchContent").disable();
    								Ext.getCmp("searchContent").show();
    								Ext.getCmp("searchContent").setValue("");
    								
    								Ext.getCmp("comboflight").hide();
    								Ext.getCmp("comboflight").setValue("");
    								
    								Ext.getCmp("combotakemode").hide();
    								Ext.getCmp("combotakemode").setValue("");
    								
    								Ext.getCmp("combodevelpmode").hide();
    								Ext.getCmp("combodevelpmode").setValue("");
    								
    								Ext.getCmp("combosignsource").hide();
    								Ext.getCmp("combosignsource").setValue("");
    								
    								Ext.getCmp("datefieldend").hide();
    								Ext.getCmp("datefieldstart").hide();
    								Ext.getCmp("datefieldend").setValue("");
    								Ext.getCmp("datefieldstart").setValue("");
    							} else if(combo.getValue() == 'LIKES_faxTime'||combo.getValue() == 'LIKES_createTime') {
    								Ext.getCmp("searchContent").hide();
    								Ext.getCmp("searchContent").setValue("");
    								
    								Ext.getCmp("comboflight").hide();
    								Ext.getCmp("comboflight").setValue("");
    								
    								Ext.getCmp("combotakemode").hide();
    								Ext.getCmp("combotakemode").setValue("");
    								
    								Ext.getCmp("combodevelpmode").hide();
    								Ext.getCmp("combodevelpmode").setValue("");
    								
    								Ext.getCmp("combosignsource").hide();
    								Ext.getCmp("combosignsource").setValue("");
    								
    								Ext.getCmp("datefieldstart").show();
    								Ext.getCmp("datefieldend").show();
    							} else if(combo.getValue()=='EQS_distributionMode'){
    								Ext.getCmp("searchContent").hide();
    								Ext.getCmp("searchContent").setValue("");
    								Ext.getCmp("combodevelpmode").show();
    								
    								Ext.getCmp("comboflight").hide();
    								Ext.getCmp("comboflight").setValue("");
    								
    								Ext.getCmp("combotakemode").hide();
    								Ext.getCmp("combotakemode").setValue("");
    								
    								Ext.getCmp("combosignsource").hide();
    								Ext.getCmp("combosignsource").setValue("");
    								
    								Ext.getCmp("datefieldend").hide();
    								Ext.getCmp("datefieldstart").hide();
    								Ext.getCmp("datefieldend").setValue("");
    								Ext.getCmp("datefieldstart").setValue("");
    							}else if(combo.getValue()=='EQS_flightNo'){
    								Ext.getCmp("searchContent").hide();
    								Ext.getCmp("searchContent").setValue("");
    								
    								Ext.getCmp("comboflight").show();
    								
    								Ext.getCmp("combotakemode").hide();
    								Ext.getCmp("combotakemode").setValue("");
    								
    								Ext.getCmp("combodevelpmode").hide();
    								Ext.getCmp("combodevelpmode").setValue("");
    								
    								Ext.getCmp("combosignsource").hide();
    								Ext.getCmp("combosignsource").setValue("");
    								
    								Ext.getCmp("datefieldend").hide();
    								Ext.getCmp("datefieldstart").hide();
    								Ext.getCmp("datefieldend").setValue("");
    								Ext.getCmp("datefieldstart").setValue("");
    							}else if(combo.getValue()=='EQS_takeMode'){
    								Ext.getCmp("searchContent").hide();
    								Ext.getCmp("searchContent").setValue("");
    								Ext.getCmp("combotakemode").show();
    								
    								Ext.getCmp("comboflight").hide();
    								Ext.getCmp("comboflight").setValue("");
    								
    								Ext.getCmp("combodevelpmode").hide();
    								Ext.getCmp("combodevelpmode").setValue("");
    								
    								Ext.getCmp("combosignsource").hide();
    								Ext.getCmp("combosignsource").setValue("");
    								
    								Ext.getCmp("datefieldend").hide();
    								Ext.getCmp("datefieldstart").hide();
    								Ext.getCmp("datefieldend").setValue("");
    								Ext.getCmp("datefieldstart").setValue("");
    							}else if(combo.getValue()=='EQS_signSource'){
    								Ext.getCmp("combosignsource").show();
    								
    								Ext.getCmp("searchContent").hide();
    								Ext.getCmp("searchContent").setValue("");
    								
    								Ext.getCmp("combotakemode").hide();
    								Ext.getCmp("combotakemode").setValue("");
    								
    								Ext.getCmp("comboflight").hide();
    								Ext.getCmp("comboflight").setValue("");
    								
    								Ext.getCmp("combodevelpmode").hide();
    								Ext.getCmp("combodevelpmode").setValue("");
    								
    								Ext.getCmp("datefieldend").hide();
    								Ext.getCmp("datefieldstart").hide();
    								Ext.getCmp("datefieldend").setValue("");
    								Ext.getCmp("datefieldstart").setValue("");
    							}
    							else {
    								Ext.getCmp("comboflight").hide();
    								Ext.getCmp("combodevelpmode").hide();
    								Ext.getCmp("combotakemode").hide();
    								Ext.getCmp("combosignsource").hide();
    								Ext.getCmp("datefieldend").hide();
    								Ext.getCmp("datefieldstart").hide();
    								
    								Ext.getCmp("comboflight").setValue("");
    								Ext.getCmp("combodevelpmode").setValue("");
    								Ext.getCmp("combotakemode").setValue("");
    								Ext.getCmp("combosignsource").setValue("");
    								Ext.getCmp("datefieldend").setValue("");
    								Ext.getCmp("datefieldstart").setValue("");
    								
    								Ext.getCmp("searchContent").enable();
    								Ext.getCmp("searchContent").show();
    								Ext.getCmp("searchContent").focus(true,true);
    							}
    						},afterRender: function(combo) {   
					       　　combo.setValue('LIKES_createTime');
							}
    					}
    				}]
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

    	var rowNum = new Ext.grid.RowNumberer({
    				header : '序号',
    				width : 35,
    				sortable : true
    	});

    	var recordGrid = new Ext.grid.GridPanel({
    				region : "center",
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
    				cm : new Ext.grid.ColumnModel([rowNum,
    						new Ext.grid.CheckboxSelectionModel(),
    						{
    							header : '配送单号',
    							dataIndex : 'dno',width:80,sortable : true
    						},{
    							header : '签收状态',
    							dataIndex : 'signStatus',width:60,sortable : true,
    							renderer:function renderDescn(v, cellmeta, record, rowIndex, columnIndex, store){
    								if(v==0){
    									cellmeta.css = 'x-grid-back-yellow';
    									return '未签收';
    								}else if(v==1){
    									cellmeta.css = 'x-grid-back-green';
    									return '已签收';
    								}else if(v==2){
    									cellmeta.css = 'x-grid-back-green';
    									return '短信签收';
    								}else if(v==3){
    									cellmeta.css = 'x-grid-back-red';
    									return '异常签收';
    								}
						        }
    						}, {
    							header : '代理公司',sortable : true,
    							dataIndex : 'cusName',width:80
    						}, {
    							header : '主单号',
    							sortable : true,
    							dataIndex : 'mainNo',width:60
    						}, {
    							header : '分单号',sortable : true,
    							dataIndex : 'subNo',width:60
    						}, {
    							header : '航班号',sortable : true,
    							dataIndex : 'flightNo',width:60
    						}, {
    							header : '传真时间',sortable : true,
    							dataIndex : 'faxTime',width:80
    						}, {
    							header : '签收时间',sortable : true,
    							dataIndex : 'createTime',width:100
    						}, {
    							header : '配送方式',sortable : true,
    							dataIndex : 'distributionMode',width:60
    						}, {
    							header : '提货方式',sortable : true,
    							dataIndex : 'takeMode',width:80
    						}, {
    							header : '收货人',sortable : true,
    							dataIndex : 'consignee',width:80
    						}, {
    							header : '收货人电话',sortable : true,
    							dataIndex : 'consigneeTel',width:80
    						}, {
    							header : '签收类型',sortable : true,
    							dataIndex : 'signType',width:80
    						}, {
    							header : '签收人',sortable : true,
    							dataIndex : 'signMan',width:80
    						}, {
    							header : '证件类型',sortable : true,
    							dataIndex : 'cardType',width:80
    						}, {
    							header : '签收人证件',sortable : true,
    							dataIndex : 'identityCard',width:80
    						}, {
    							header : '代签人',sortable : true,
    							dataIndex : 'replaceSign',width:80
    						}, {
    							header : '代签人证件',sortable : true,
    							dataIndex : 'reIdentityCard',width:100
    						}, {
    							header : '签收来源',sortable : true,
    							dataIndex : 'signSource',width:100
    						},{
    							header : '是否异常签收',sortable : true,
    							dataIndex : 'isSignException',width:80
    							,renderer:function(v,e){
    								return v==1?'<font color=red>是</font>':'否';
    							}
    						}, {
    							header : '签收备注',sortable : true,
    							dataIndex : 'remark',width:80
    						}
    				]),
    				sm : new Ext.grid.CheckboxSelectionModel(),
    				ds : signStore,
    				bbar : new Ext.PagingToolbar({
    							pageSize : pageSize, // 默然为25，会在store的proxy后面传limit
    							store : signStore,
    							displayInfo : true,
    							displayMsg : '显示第 {0} 条到 {1} 条记录，共 {2} 条',
    							emptyMsg : "没有记录信息显示"
    				})
    	});
		recordGrid.on('click', function() {
        	var _record = recordGrid.getSelectionModel().getSelections();
        	var delbtn=Ext.getCmp('delbtn');
        	if(_record.length<1){
        		if(delbtn){
        			delbtn.setDisabled(true);
        		}
        	}else if(_record.length=1){
        		delbtn.setDisabled(false);
        	}
    	});
    	// 布局
    	var mypanel = new Ext.Panel({
    		layout : "border",
    		//title : "签收录入",
    		id : 'view',
    		el : 'showView',
    		labelAlign : 'left',
    		height : Ext.lib.Dom.getViewHeight(),
    		width : Ext.lib.Dom.getViewWidth(),
    		bodyStyle : 'padding:1px',
    		layout : "border",
    		tbar : tb,
    		frame : false,
    		items : [recordGrid]
    	});

    	// 审核操作tbar第二行
    	mypanel.on('render', function() {
    		var tbar = new Ext.Toolbar({
    					items : ['', '', '配送单号:', {
    								xtype : "textfield",
			    					width : 110,
			    					id:'tdno',
			    					name:'dno',
			    					enableKeyEvents:true,
			    					listeners:{
			    						'keyup':function(textField, e){
			    							if(e.getKey()==13 && textField.getValue()!=''){
			    								searchSign();
			    							}
			    						 }
			    					}

    							}, '-', '', '发货代理:', {
    								xtype : "combo",
			    					width : 110,
			    					triggerAction : 'all',
			    					model : 'local',
			    					queryParam : 'filter_LIKES_cusName',
			    					pageSize:pageSize,
			    					id:'combocus',
			    					minChars:0,
			    					resizable:true,
			    					valueField : 'id',
    								displayField : 'cusName',
    								hiddenName : 'id',
			    					store :cusStore,
			    					emptyText : '选择类型',
			    					forceSelection : true,
			    					enableKeyEvents:true,
			    					listeners:{
			    						 keyup:function(textField, e){
			    							if(e.getKey()==13){
			    								searchSign();
			    							}
			    						}
			    					}

    							}, '-', '', '收货人:', {
    								xtype : "combo",
			    					width : 110,
			    					id:'consignees',
			    					xtype : "textfield",
			    					name:'consignees',
			    					enableKeyEvents:true,
			    					listeners:{
			    						 keyup:function(textField, e){
			    							if(e.getKey()==13){
			    								searchSign();
			    							}
			    						}
			    					}

    							}, '-','', '分单号:', {
    								xtype : 'textfield',
			    					id : 'subNo',
			    					name : 'subNo',
			    					width : 110,
			    					enableKeyEvents:true,
			    					listeners:{
			    						 keyup:function(textField, e){
			    							if(e.getKey()==13){
			    								searchSign();
			    							}
			    						}
			    					}
    							}, '-', '签收状态:', {
    								xtype : 'combo',
    								triggerAction : 'all',
			    					id : 'signStatus',
			    					name : 'signStatus',
			    					store:[
			    						['','全部'],
			    						['0','未签收'],
			    						['1','已签收'],
			    						['2','短信签收'],
			    						['3','异常签收']
			    					],
			    					//签收状态 ,0,，未签收，1，已签收，2，短信签收 3，异常签收
			    					width:60,
			    					enableKeyEvents:true,
			    					listeners:{
			    						 keyup:function(textField, e){
			    							if(e.getKey()==13){
			    								searchSign();
			    							}
			    						}
			    					}
    							}, '-', {
	    							text : '<b>搜索</b>',
	    							id : 'btn',
	    							iconCls : 'btnSearch',
	    							handler:searchSign
    					}]
    				});
    		tbar.render(mypanel.tbar);
    	});
    	mypanel.render();
    	mypanel.doLayout();
    	recordGrid.render();	   
    	function signInput(){
    	var requestDoId='';
    		var form = new Ext.form.FormPanel({
    				id:'addForm',
					frame : true,
					bodyStyle : 'padding:5px 5px 5px',
					labelAlign : "right",
					width : 500,
					items:[{
					        layout : 'column',
					        items:[{
					        		columnWidth : .5,
					        		layout:'form',
					        		items:[
					        				{xtype:'hidden',name:'ts'},
					        				{xtype:'hidden',name:'requestId',id:'requestId'},
					        				{xtype:'hidden',name:'signSource',value:'AIS系统手工签收'},
								          	{
									          	xtype:'textfield',
									          	fieldLabel:'配送单号<span style="color:red">*</span>',
									          	name:'dno',
									          	id:'dno',
									          	allowBlank:false,
									          	blankText:'配送单号不能为空！',
									          	maxLength:20,
									          	anchor : '95%',
									          	enableKeyEvents:true,
									          	listeners : {
											 		keyup:function(textField, e){
										                  if(e.getKey() == 13){
										                     	if(textField.getValue()==null || textField.getValue().length<1){
										                     		Ext.getCmp('dno').focus(true,true);
										                     		return;
										                     	}else{
										                     		searchConInfo();
										                     	}
										                  }
														
													},
						    						 blur:function(textField){
						    						 	if(textField.getValue()==null || textField.getValue().length<1){
								                     		Ext.getCmp('dno').focus(true,true);
								                     		return;
								                     	}else{
								                     		searchConInfo();
								                     	}
						    						 }
									 			}
								          	
								          	},
								          	{	
								          		xtype:'textfield',
								          		fieldLabel:'个性化要求',
								          		readOnly:true,
								          		name:'request',
								          		anchor : '95%',
								          		id:'request'
								          	},
				                            {
				                            	xtype:'textfield',
				                            	fieldLabel:'签收人<span style="color:red">*</span>',
				                            	id:'signMan',name:'signMan',
				                            	allowBlank:false,blankText:'签收人不能为空！',
				                            	enableKeyEvents:true,
				                            	anchor : '95%',
									          	listeners : {
											 		keyup:function(textField, e){
										                     if(e.getKey() == 13){
										                     	Ext.getCmp('isSignExceptionId').focus(true,true);
										                  }
											 		}
									 			}
				                            },
				                            {
				                            	xtype : 'combo',
				                            	fieldLabel:'是否异常<span style="color:red">*</span>',
				                            	triggerAction : 'all',
				                            	forceSelection : true,
				                            	allowBlank:false,
				                            	editable : false,
				                            	mode : "local",//获取本地的值
				                            	id :'isSignExceptionId',
				                            	displayField : 'propertyName',//显示值，与fields对应
												valueField : 'propertyId',//value值，与fields对应
				                            	store:exceptionStore,
				                            	anchor : '95%',
				                            	enableKeyEvents:true,
				                            	listeners : {
											 		keyup:function(combo, e){
									                     if(e.getKey() == 13){
									                     	if(combo.getValue()==1){
									                     		Ext.getCmp('remark').focus(true,true);
									                     	}else{
									                     		Ext.getCmp('btnsave').focus(true,true);
									                     	}
									                  	 }
										 			},render:function(combo,e){
											 			combo.setValue('0');
											 		}
									 			}
				                            }
		                           		   ]
				                    },{
				                    layout:'form',
				                    columnWidth : .5,
				                    items:[
						                   {xtype:'textfield',
							                   fieldLabel:'收货人信息<span style="color:red">*</span>',
							                   name:'consignee',
							                   id:'consignee',
							                   readOnly:true,
							                   anchor : '95%',
							                   allowBlank:false,
							                   blankText:'收货人不能为空！'
						                   
						                   },
						                   {
							                   	xtype:'combo',
							                   	fieldLabel:'是否执行<span style="color:red">*</span>',
							                   	hiddenId:'isOpr',
							                   	hiddenName:'isOpr',
							                   	forceSelection : true,
							                   	mode : "local",//获取本地的值
							                   	allowBlank:false,
							                    editable : false,
							                   	triggerAction : 'all',
							                   	displayField : 'propertyName',//显示值，与fields对应
												valueField : 'propertyId',//value值，与fields对应
							                   	store:exceptionStore,
							                   	anchor : '95%',
							                   	enableKeyEvents:true,
							                   	listeners : {
											 		keyup:function(combo, e){
										                     if(e.getKey() == 13){
										                     	Ext.getCmp('signMan').focus(true,true);
										                  }
											 		},render:function(combo,e){
											 			combo.setValue('0');
											 		}
									 			}
						                   },
						                   {
						                   		xtype:'textfield',
						                   		fieldLabel:'身份证号码',
						                   		name:'identityCard',
						                   		id:'identityCard',
						                   		anchor : '95%',
						                   		//allowBlank:false,
						                   		enableKeyEvents:true,
									          	listeners : {
											 		keyup:function(textField, e){
										                 if(e.getKey() == 13){
										                     	Ext.getCmp('remark').focus(true,true);
										                  }
											 		}
									 			}
						                   	}
				                    ]
				            }]
                    },{
                    	xtype:'textarea',
                    	fieldLabel:'签收备注',
                    	id:'remark',
                    	name:'remark',
                    	anchor : '95%',
                    	enableKeyEvents:true,
							listeners : {
								keyup:function(textField, e){
									if(e.getKey() == 13){
										Ext.getCmp('btnsave').focus(true,true);
									}
								}
							}
                    }]
    		});
    		
    		var win = new Ext.Window({
			title : '签单手工录入',
			width : 500,
			closeAction : 'hide',
			plain : true,
			modal : true,
			items : form,
			buttonAlign : "center",
			buttons : [{
				text : "保存",
				id:'btnsave',
				iconCls : 'save',
				handler : function() {
					if (form.getForm().isValid()) {
						//this.disabled = true;//只能点击一次
						//var isException = form.getForm().('isException').getValue();
						var isException = Ext.getCmp('isSignExceptionId').getValue();
						//alert(isException);
						//var isOpr = Ext.getCmp('isOpr').getValue();
						var isOpr = Ext.get('isOpr').dom.value;
						if(isOpr==0 && requestDoId!=null && requestDoId.length>0){
							Ext.Msg.confirm(alertTitle, "这票货个性化要求未执行,是否继续签收？", function(
									btnYes) {
									if (btnYes == 'yes' || btnYes == 'ok' || btnYes == true) {
										if(isException==1){
											Ext.Msg.confirm(alertTitle, "这票货有异常,确定要继续签收吗？", function(
												btnYes) {
												if (btnYes == 'yes' || btnYes == 'ok' || btnYes == true) {
													formsub(form);
												}
											});
										}else{
											formsub(form);
										}
									}
								});
						}else if(isException==1){
							Ext.Msg.confirm(alertTitle, "这票货有异常,确定要继续签收吗？", function(
								btnYes) {
								if (btnYes == 'yes' || btnYes == 'ok' || btnYes == true) {
									formsub(form);
								}
							});
						}else{
							formsub(form);
						}
					}
				}
				}, {
					text : "重置",
					handler : function() {
						form.getForm().reset();
					}
				}, {
					text : "取消",
					handler : function() {
						win.hide();
					}
				}]
			});
			win.on('hide', function() {
						form.destroy();
					});
			win.show();
    	}
    	function formsub(form){
    		var isException = Ext.getCmp('isSignExceptionId').getValue();
    			Ext.Ajax.request({
							url:sysPath+'/'+signUrl,
							params:{
								privilege:privilege,
								filter_EQL_dno:Ext.getCmp('dno').getValue()
							},
							success:function(resp){
								var respText = Ext.util.JSON.decode(resp.responseText);
									if(respText.result.length>0){
										if(respText.result[0].signStatus>0){
											Ext.Msg.alert(alertTitle,'该配送单已经签收，签收人为'+respText.result[0].signMan+'，如需再签收，请先作废!',
												function (){
													Ext.getCmp('signMan').setValue(respText.result[0].signMan);
													Ext.getCmp('dno').focus(true,true);
													return;
											});
										}else{
											form.getForm().submit({
												url : sysPath + "/stock/oprSignAction!save.action",
												params:{
													isSignException:isException,
													privilege:privilege
												},
												waitMsg : '正在保存数据...',
												success : function(form, action) {
													form.reset();
													//searchSign();
													Ext.Msg.alert(alertTitle,'保存成功!!',
													function (){
														Ext.getCmp('dno').focus(true,true);
														return;
													});
												},
												failure : function(form, action) {
													if (action.faliureType == Ext.form.Action.SERVER_INVALID) {
														Ext.Msg.alert(alertTitle, "服务器连接异常，请联系管理员！");
													} else {
														if (action.result.msg) {
															Ext.Msg.alert(alertTitle,
																	action.result.msg);
														}
													}
												}
											});		
										}
									}else{
									}
								}
							});
    	}
    	//查询
    	function searchSign(){
    		var dno=Ext.getCmp("tdno").getValue();
    		var cusId=Ext.getCmp("combocus").getValue();
    		var consignee=Ext.getCmp("consignees").getValue();
    		var subNo=Ext.getCmp("subNo").getValue();
    		var signStatus=Ext.getCmp("signStatus").getValue();
    		var combo=Ext.getCmp("comboCheckItems");
    		Ext.apply(signStore.baseParams, {
				filter_GED_faxTime : '',
				filter_LED_faxTime : '',
				filter_GED_createTime : '',
   				filter_LED_createTime : '',
				checkItems : '',
				itemsValue : ''
			});
    		if(combo.getValue()=='LIKES_faxTime'){
    			var start=Ext.get("datefieldstart").dom.value;
    			var end=Ext.get("datefieldend").dom.value;
    			Ext.apply(signStore.baseParams, {
   						filter_GED_faxTime : start,
   						filter_LED_faxTime : end,
   						filter_EQS_subNo:subNo,
						filter_EQL_dno:dno,
						filter_EQL_faxStatus:1,
						filter_EQL_cusId:cusId,
						filter_LIKES_consignee:consignee,
						filter_EQL_signStatus:signStatus
   					});

    		}else if(combo.getValue() == 'LIKES_createTime'){
    			var start=Ext.get("datefieldstart").dom.value;
    			var end=Ext.get("datefieldend").dom.value;
    			Ext.apply(signStore.baseParams, {
   						filter_GED_createTime : start,
   						filter_LED_createTime : end,
   						filter_EQS_subNo:subNo,
						filter_EQL_dno:dno,
						filter_EQL_faxStatus:1,
						filter_EQL_cusId:cusId,
						filter_LIKES_consignee:consignee,
						filter_EQL_signStatus:signStatus
   					});
    		}else if(combo.getValue() == 'EQS_distributionMode'){
    			//alert(Ext.get("combodevelpmode").dom.value);
    			Ext.apply(signStore.baseParams, {
   						checkItems : Ext.get("checkItems").dom.value,
   						itemsValue : Ext.get("combodevelpmode").dom.value,
   						filter_EQS_subNo:subNo,
						filter_EQL_dno:dno,
						filter_EQL_faxStatus:1,
						filter_EQL_cusId:cusId,
						filter_LIKES_consignee:consignee,
						filter_EQL_signStatus:signStatus
   					});
    		}else if(combo.getValue() == 'EQS_takeMode'){
    			Ext.apply(signStore.baseParams, {
   						checkItems : Ext.get("checkItems").dom.value,
   						itemsValue : Ext.get("combotakemode").dom.value,
   						filter_EQS_subNo:subNo,
						filter_EQL_dno:dno,
						filter_EQL_faxStatus:1,
						filter_EQL_cusId:cusId,
						filter_LIKES_consignee:consignee,
						filter_EQL_signStatus:signStatus
   					});
    		}else if(combo.getValue() == 'EQS_flightNo'){
    			Ext.apply(signStore.baseParams, {
   						checkItems : Ext.get("checkItems").dom.value,
   						itemsValue : Ext.get("comboflight").dom.value,
   						filter_EQS_subNo:subNo,
						filter_EQL_dno:dno,
						filter_EQL_faxStatus:1,
						filter_EQL_cusId:cusId,
						filter_LIKES_consignee:consignee,
						filter_EQL_signStatus:signStatus
   					});
    		}else if(combo.getValue()=='EQS_signSource'){
    			Ext.apply(signStore.baseParams, {
   						checkItems : Ext.get("checkItems").dom.value,
   						itemsValue : Ext.get("combosignsource").dom.value,
   						filter_EQS_subNo:subNo,
						filter_EQL_dno:dno,
						filter_EQL_faxStatus:1,
						filter_EQL_cusId:cusId,
						filter_LIKES_consignee:consignee,
						filter_EQL_signStatus:signStatus
   				});
    		}
    		else{
    			Ext.apply(signStore.baseParams, {	
   						checkItems : Ext.get("checkItems").dom.value,
   						itemsValue : Ext.get("searchContent").dom.value,
   						filter_EQS_subNo:subNo,
						filter_EQL_dno:dno,
						filter_EQL_faxStatus:1,
						filter_EQL_cusId:cusId,
						filter_LIKES_consignee:consignee,
						filter_EQL_signStatus:signStatus
   					});

    		}
    		signStore.load({
    				params : {
    					start : 0,
    					limit : pageSize,
    					privilege:privilege
    				}
    		});
    	}
    	
});

function searchConInfo(){
	Ext.Ajax.request({
		url:sysPath+"/"+signUrl,
		params:{
			privilege:privilege,
			filter_EQL_dno:Ext.getCmp('dno').getValue()
		},
		success:function(resp){
			var respText = Ext.util.JSON.decode(resp.responseText);
			if(respText.result.length>0){
				
				if(respText.result[0].signStatus>0){
					Ext.Msg.alert(alertTitle,'该配送单已经签收，签收人为'+respText.result[0].signMan+'，如需再签收，请先作废!',
						function (){
							Ext.getCmp('signMan').setValue(respText.result[0].signMan);
							Ext.getCmp('dno').focus(true,true);
							return;
					});
				}else{
					Ext.getCmp('consignee').setValue(respText.result[0].consignee+'/'+respText.result[0].consigneeTel+'/'+respText.result[0].addr);
						Ext.Ajax.request({
							url:sysPath+"/stock/oprRequestDoAction!list.action",
							params:{
								limit:2,
								filter_EQL_dno:Ext.getCmp('dno').getValue(),
								filter_EQS_requestStage:requestStage
							},
							success:function(resp){
								var respText = Ext.util.JSON.decode(resp.responseText);
								if(respText.result.length>0){
									Ext.getCmp('request').setValue(respText.result[0].request);
									Ext.getCmp('requestId').setValue(respText.result[0].id);
								}
							}
					});
					Ext.getCmp('signMan').focus(true,true);
				}
         			
			}else{
				Ext.Msg.alert(alertTitle,'配送单号不存在!!',
					function onfocus(){
						Ext.getCmp('dno').focus(true,true);
						return;
					}
				);
			}
		}
	});	
}
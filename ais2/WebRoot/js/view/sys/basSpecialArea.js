	Ext.QuickTips.init();
	var comboxPage=50;
	privilege=258;
	var ralaList="bas/basSpecialAreaAction!ralaList.action";
Ext.onReady(function(){
	Ext.QuickTips.init();
	var rownum=new Ext.grid.RowNumberer({
		header:'序号',
        width : 35,
		sortable : true	
	});
 
    var fields=['id', 
	'areaName',  //区名
	'areaId',
	'develpMode',  //s配送方式
	'cusId','areaType','areaRank',
	'distriDepartName',  //配送部门
	'distriDepartId',
	'endDepartName',  //终端部门
	'endDepartId',  
	'createName',   
	'createTime',
	'updateName',
	'updateTime','typeName',
	'ts','cusName',
	'departId',  //操作人业务部门
	'departName'];
	
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
        proxy: new Ext.data.HttpProxy({url:sysPath+"/"+ralaList}),
        baseParams:{limit: pageSize,privilege:privilege},
        reader:jsonread
    });
	
	var addrStore=new Ext.data.Store({
		storeId:"addrStore",
		proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/dictionaryAction!ralaList.action"}),
		baseParams:{filter_EQL_basDictionaryId:3,privilege:16},
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
        	{name:'id'},
        	{name:'areaType',mapping:'typeName'}
        	])
	});
	addrStore.load();
	
	var departStartStore=new Ext.data.Store({
		storeId:"departStartStore",
		method:'post',
		baseParams:{limit:pageSize,filter_EQL_isBussinessDepa:1},
		proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/departAction!findAll.action"}),
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
        	{name:'departId'},
        	{name:'departName'}
        	])
	});
	//departStartStore.load();
	
	var departEndStore=new Ext.data.Store({
		storeId:"departEndStore",
		method:'post',
		baseParams:{limit:pageSize,filter_EQL_isBussinessDepa:1},
		proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/departAction!findAll.action"}),
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
        	{name:'departId'},
        	{name:'departName'}
        	])
	});
	//departEndStore.load();
	
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
	devModeStore.load();
	// start custom
	var cusStore=new Ext.data.Store({
		storeId:"cusStore",
		baseParams:{limit:pageSize},
		proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/customerAction!getTraCustomer.action"}),
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
        	{name:'id'},
        	{name:'cusName'}
        	])
	});
	cusStore.load();

	var basAreaStore=new Ext.data.Store({
		storeId:"basAreaStore",
		baseParams:{privilege:55},
		proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/basAreaAction!ralaList.action"}),
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
        	{name:'id'},
        	{name:'areaName'}
        	])
	});
	cusStore.load();
    
   	var areaRankStore=new Ext.data.SimpleStore({
		auteLoad:true, //此处设置为自动加载
 		data:[
   			['省','省'],
   			['市','市'],
   			['区/县','区/县'],
   			['镇/街道','镇/街道']],
  		fields:["areaRankId","areaRank"]
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
				text:'<B>添加</B>',
				iconCls : 'userAdd',
				id:'addbtn',
				handler : function() {
						updateSpecialArea(null);
				}
			}, '-',{
				text : '<B>修改</B>',
				id : 'updatebtn',
				iconCls:'userEdit',
				handler : function() {
						var _records = recordGrid.getSelectionModel().getSelections();
						if (_records.length == 0) {
							Ext.Msg.alert(alertTitle, '请选择一条您需要修改的数据');
						}else if (_records.length > 1) {
							Ext.Msg.alert(alertTitle,'修改数据只能选择一数据');
						}else{
							updateSpecialArea(_records[0]);
						}
				}
			},'-',{
				text:'<b>删除</b>',
				id : 'deletebtn',
			 	iconCls:'userDelete',
			 	handler : function(){
			 		var _records = recordGrid.getSelectionModel().getSelections();
					if (_records.length == 0) {
						Ext.Msg.alert(alertTitle, '请选择一条您需要删除的数据');
					}else{
						deleteSpecialArea(_records);
					}
				} 
			},'-',{
				xtype:'textfield',
	 	        id : 'itemsValue',
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
				id:'comboType',
				hidden : true,
				typeAhead:true, // 自动填充
				pageSize : comboxPage,
				hiddenId : 'dictionaryName',
    			hiddenName : 'dictionaryName',
				triggerAction : 'all',
			//	store : menuStore,
				allowBlank : true,　//指定参数是否允许为空
				width:210,
				minChars : 1,
				queryParam : 'filter_LIKES_name',
				emptyText : "请选择类别名称",
				forceSelection : true,  //模糊查询
				fieldLabel:'类别名称',
				editable : true,  // 可编辑
			//	mode : "local",//获取本地的值
				displayField : 'basDictionaryName',//显示值，与fields对应
				valueField : 'basDictionaryId',//value值，与fields对应
				name : 'basDictionaryId',
				anchor : '95%'
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
						 ['LIKES_areaName','地区名称'],
						 ['LIKES_develpMode','配送方式'],
						 ['LIKES_typeName','地址类型'],
						 ['LIKES_cusName','供应商'],
						 ['LIKES_distriDepartName','配送部门'],
						 ['LIKES_endDepartName','终端部门'],
						 ['LIKES_distriDepartName','配送部门'],
						 ['LIKES_createName','创建人']],
				emptyText : '选择查询类型',
				forceSelection : true
 				},'-',{
 				     text : '<b>搜索</b>',
 				     id : 'btn',
 				     iconCls : 'btnSearch',
 					 handler : searchLog
 				}
			],
			columns:[rownum,
        			sm,
        		    {header: 'ID', dataIndex: 'id',hidden:true,sortable : true},
        			{header: '地区ID', dataIndex: 'areaId',hidden:true,sortable : true},
        			{header: '地区名称', dataIndex: 'areaName',sortable : true},
        			{header: '配送方式', dataIndex: 'develpMode',sortable : true}, 
        			{header: '地址类型', dataIndex: 'areaType',sortable : true}, 
        			{header: '地区等级', dataIndex: 'areaRank',sortable : true},
        			{header: '客商ID', dataIndex: 'cusId',hidden:true,sortable : true},
        			{header: '供应商', dataIndex: 'cusName',sortable : true},
        			{header: '配送部门', dataIndex: 'distriDepartName',sortable : true},
        			{header: '配送部门ID', dataIndex: 'distriDepartId',hidden:true,sortable : true},
        			{header: '终端部门', dataIndex: 'endDepartName',sortable : true},
        			{header: '终端部门ID', dataIndex: 'endDepartId',hidden:true,sortable : true},
					{header: '部门名称', dataIndex: 'departName',width:120,sortable : true},
        			{header: '创建人', dataIndex: 'createName',width:80},
					{header: '创建时间', dataIndex: 'createTime',width:80,sortable : true},
					{header: '修改人', dataIndex: 'updateName',width:80},
					{header: '修改时间', dataIndex: 'updateTime',sortable : true,width:80},
					{header: '时间戳', dataIndex: 'ts',sortable : true,hidden:true}
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

	function searchLog() {
		 dataStore.baseParams = {
			checkItems : Ext.get("checkItems").dom.value,
			privilege:privilege,
			itemsValue : Ext.get("itemsValue").dom.value
   		 }
 		 dataStore.reload({
			params : {
				start : 0,
				limit : pageSize
			}
		 });
	} 

		recordGrid.on('rowdblclick',function(grid,index,e){
	        var _records = recordGrid.getSelectionModel().getSelections();
		    if (_records.length ==1) {
				updateSpecialArea(_records[0]);
			}
		});
			
			function deleteSpecialArea(_records){
			     Ext.Msg.confirm(alertTitle,'数据删除后将不可恢复，您确定要删除这<span style="color:red">&nbsp;'+ _records.length + '&nbsp;</span>行数据吗?',function(btnYes) {
					if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
			        var ids = "";
					for(var i = 0; i < _records.length; i++) {
						ids += _records[i].data.id + ",";
					}  	
					form1.getForm().doAction('submit', {
						url : sysPath+ "/bas/basSpecialAreaAction!delete.action",
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
									});
						}
					});
				}
			});
	}
	
	function updateSpecialArea(record) {

		var form = new Ext.form.FormPanel({
					frame : true,
					border : false,
					bodyStyle : 'padding:5px 5px 5px',
					labelAlign : "left",
					width : 400,
					reader :jsonread,
					labelAlign : "right",
					labelWidth : 90,
					items:[
						   {xtype:'hidden',name:'ts'},
						   {xtype:'hidden',name:'id'},
						   {xtype:'hidden',id:'hidDisName',name:'distriDepartName'},
						   {xtype:'hidden',id:'hidEndName',name:'endDepartName'},
						   {xtype:'hidden',id:'hidEndName',name:'endDepartName'},
						   {xtype:'hidden',id:'areaNameS',name:'areaName'},  
						    {xtype:'hidden',id:'cusName',name:'cusName'},  
                           {xtype : 'combo',
    							typeAhead : true,
    							id:'areaIds',
    							forceSelection : true,
    							queryParam : 'filter_LIKES_areaName',
    							minChars : 1,
    							anchor : '90%',
    							fieldLabel : '地区名称<span style="color:red">*</span>',
    							store : basAreaStore,
    							pageSize : pageSize,
    							triggerAction : 'all',
    							valueField : 'id',
    							displayField : 'areaName',
    							hiddenName : 'areaId',
    							emptyText : "请选择地区名称",
    							allowBlank : false,
    							blankText : "地区名称不能为空！",
    							listeners : {
									'select' : function(combo, record, index) {
											var name=Ext.get("areaIds").dom.value;
											Ext.getCmp('areaNameS').setValue(name);
											Ext.Ajax.request({
												url:sysPath+"/sys/basAreaAction!ralaList.action",
												params:{
												privilege:55,
												filter_EQL_id:combo.getValue()
												
												},
												success:function(resp){
													var respText = Ext.util.JSON.decode(resp.responseText);
													if(respText.result.length<1){
														Ext.Msg.alert(alertTitle,"数据异常,地区ID："+combo.getValue()+"对应的地区信息为空，请联系系统管理员");
													}else{
														Ext.getCmp('areaRank').setValue(respText.result[0].areaRank);
													}
												}
											});
									}
								}
                           },{
                           		xtype : 'textfield',
                           		id:'areaRank',
    							//typeAhead : true,
    							//forceSelection : true,
                           		readOnly:true,
    							fieldLabel : '地区等级<span style="color:red">*</span>',
//    							store : areaRankStore,
//    							triggerAction : 'all',
//    							valueField : 'areaRank',
//    							displayField : 'areaRank',
//    							mode : "local",//获取本地的值
    							name : 'areaRank',
    							//emptyText : "请输入地址类型",
    							//allowBlank : false,
    							anchor : '90%'
    							//blankText : "地址类型不能为空！"
                           },{
                           		xtype : 'combo',
    							typeAhead : true,
    							forceSelection : true,
    							queryParam : 'filter_LIKES_typeName',
    							minChars : 0,
    							fieldLabel : '地址类型<span style="color:red">*</span>',
    							store : addrStore,
    							triggerAction : 'all',
    							valueField : 'areaType',
    							displayField : 'areaType',
    							hiddenName : 'areaType',
    							emptyText : "请输入地址类型",
    							allowBlank : false,
    							anchor : '90%',
    							blankText : "地址类型不能为空！"
                           },
                           {
                           		xtype : 'combo',
    							typeAhead : true,
    							forceSelection : true,
    							queryParam : 'filter_LIKES_departName',
    							minChars : 0,
    							id:'comboDisId',
    							fieldLabel : '配送部门',
    							store : departStartStore,
    							pageSize : pageSize,
    							anchor : '90%',
    							triggerAction : 'all',
    							valueField : 'departId',
    							displayField : 'departName',
    							hiddenName : 'distriDepartId',
    							emptyText : "请输入配送部门",
    							listeners : {
									'select' : function(combo, record, index) {
											var name=Ext.get("comboDisId").dom.value;
											Ext.getCmp('hidDisName').setValue(name);
									}
								}
                           },
                           {
                           		xtype : 'combo',
    							typeAhead : true,
    							forceSelection : true,
    							queryParam : 'filter_LIKES_departName',
    							minChars : 0,
    							fieldLabel : '终端部门',
    							store : departEndStore,
    							pageSize : pageSize,
    							anchor : '90%',
    							id:'comboEndDepartId',
    							triggerAction : 'all',
    							valueField : 'departId',
    							displayField : 'departName',
    							hiddenName : 'endDepartId',
    							emptyText : "请输入终端部门",
    							//allowBlank : false,
    							//blankText : "终端部门不能为空！",
    							listeners : {
									'select' : function(combo, record, index) {
											var name=Ext.get("comboEndDepartId").dom.value;
											Ext.getCmp('hidEndName').setValue(name);
									}
								}
                           },
                           {
                           		xtype : 'combo',
    							typeAhead : true,
    							forceSelection : true,
    							queryParam : 'filter_LIKES_develpMode',
    							minChars : 0,
    							fieldLabel : '配送方式<span style="color:red">*</span>',
    							store : devModeStore,
    							triggerAction : 'all',
    							valueField : 'develpMode',
    							id:'developcombo',
    							anchor : '90%',
    							displayField : 'develpMode',
    							hiddenName : 'develpMode',
    							emptyText : "请输入配送方式",
    							allowBlank : false,
    							blankText : "配送方式不能为空！"
                           },
							 {
                           		xtype : 'combo',
    							//typeAhead : true,
    							forceSelection : true,
    							queryParam : 'filter_LIKES_cusName',
    							minChars : 0,
    							fieldLabel : '客商名称',
    							store : cusStore,
    							pageSize : pageSize,
    							triggerAction : 'all',
    							valueField : 'id',
    							displayField : 'cusName',
    							hiddenName : 'cusId',
    							id:'combocusId',
    							anchor : '90%',
    							value:'',
    							emptyText : "请输入客商名称",
    							listeners:{
    								'select':function(combo){
    									Ext.getCmp('cusName').setValue(Ext.get('combocusId').dom.value);
    								}
    							}
                           }
                    ]
				});
		
		carTitle='新增特殊地区';
		if(record!=null){
			carTitle='修改特殊地区';
			basAreaStore.load();
			cusStore.load();
			addrStore.load();
			departStartStore.load();
			departEndStore.load();
			devModeStore.load();
			form.load({
				url : sysPath+ "/"+ralaList,
				waitMsg : '正在加载数据...',
				params:{filter_EQL_id: record.data.id, privilege:privilege,limit : pageSize},
				success : function(response){
				}
			});	
		}

		var win = new Ext.Window({
			title : carTitle,
			width : 400,
			closeAction : 'hide',
			plain : true,
			modal : true,
			items : form,
			buttonAlign : "center",
			buttons : [{
				text : "保存",
				iconCls : 'save',
				handler : function() {
					if (form.getForm().isValid()) {
						var developMode=Ext.getCmp('developcombo').getValue();
						if(developMode == '中转' && Ext.getCmp('combocusId').getValue()==''){
							Ext.Msg.alert(alertTitle,'中转供应商不能为空!');
						}else{
							form.getForm().submit({
								url : sysPath + "/bas/basSpecialAreaAction!save.action?privilege=55",
								waitMsg : '正在保存数据...',
								success : function(form, action) {
									win.hide(), 
									Ext.Msg.alert(alertTitle,'保存成功！');
									dataStore.reload();
								},
								failure : function(form, action) {
									if (action.faliureType == Ext.form.Action.SERVER_INVALID) {
										Ext.Msg.alert(alertTitle, "服务器连接异常，请联系管理员！");
									} else {
										if (action.result.msg) {
											win.hide();
											Ext.Msg.alert(alertTitle,action.result.msg);
										}
									}
								}
							});
						}
					}
				}
			}, {
				text : "重置",
				handler : function() {
					if(record!=null){
						form.load({
							url : sysPath+ "/"+ralaList,
							waitMsg : '正在加载数据...',
							params:{filter_EQL_id: record.data.id, privilege:privilege,limit : pageSize},
							success : function(response){
							}
						});	
					}else{
						form.getForm().reset();
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
});
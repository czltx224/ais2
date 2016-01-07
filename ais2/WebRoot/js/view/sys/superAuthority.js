	Ext.QuickTips.init();
	var privilege=262;
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
 
	var fields=[{name:"id",mapping:'id'},
	{name:"departId",mapping:'departId'},'userCode','loginName','userName','birthdayType',
	{name:"departName",mapping:'departName'},'birthday','workstatus','hrstatus','manCode',
    {name:"stationId",mapping:'stationId'}, 'offTel','telPhone','sex','updateName',
    {name:"stationName",mapping:'stationName'},'stationNames','duty','status','userLevelString',
    {name:"bussDepart",mapping:'bussDepart'}, 'userLevel','createName','createTime','updateName',
    {name:"bussDepartName",mapping:'rightDepart'},'password','updateTime','ts'];
	
	var jsonread= new Ext.data.JsonReader({
         root:'result',
         totalProperty:'totalCount'},
         fields);
                    
	var sm = new Ext.grid.CheckboxSelectionModel();
	
	levelTypeStore	= new Ext.data.Store({ 
		storeId:"levelTypeStore",
		baseParams:{filter_EQL_basDictionaryId:300,privilege:16},
		proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/dictionaryAction!ralaList.action"}),
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
       	{name:'id',mapping:'typeCode'},
       	{name:'name',mapping:'typeName'}
       	])
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
     
	var recordGrid=new Ext.grid.GridPanel({
		renderTo:Ext.getBody(),
	//	region : 'north', // 和VIEWPORT布局模型对应，充当center区域布局
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
		tbar:['&nbsp;&nbsp;',{
				text:'<B>等级设置</B>',
				iconCls : 'userAdd',
				id:'addbtn',
				handler : function() {
					updateLevel();
				}
			},'&nbsp;', '-','&nbsp;',{
				text : '<B>密码重置</B>',
				id : 'updatebtn',
				iconCls:'userEdit',
				handler : function() {
						startUserPassword();
				}
			},'&nbsp;', '-','&nbsp;', {
				xtype : 'combo',
				id:'comboTypeDepart',
				hidden : true,
				hiddenId : 'departName',
    			hiddenName : 'departName',
				triggerAction : 'all',
				store : menuStore,
				width:210,
				 queryParam : 'filter_LIKES_departName',
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
		    },'&nbsp;', {
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
			},'&nbsp;', '-','&nbsp;', {
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
			    			['LIKES_userName', '用户名'],
	    					['EQS_departId', "部门名称"]
    					],
    					emptyText : '选择查询类型',
    					forceSelection : true,
    					listeners : {
    						'select' : function(combo, record, index) { 
    							if(combo.getValue() == 'EQS_departId'){
					 	
    						 		Ext.getCmp("itemsValue").disable();
    								Ext.getCmp("itemsValue").hide();
    								Ext.getCmp("itemsValue").setValue("");
    						 	
    						 		Ext.getCmp("comboTypeDepart").setValue("");
    								Ext.getCmp("comboTypeDepart").show();
    								Ext.getCmp("comboTypeDepart").enable();

    						 	}else{

    								
    						      	Ext.getCmp("itemsValue").enable();
    								Ext.getCmp("itemsValue").show();
    							
    								Ext.getCmp("comboTypeDepart").disable();
    								Ext.getCmp("comboTypeDepart").hide();
    								Ext.getCmp("comboTypeDepart").setValue("");
    							}
    						}
    					}
    				},'&nbsp;', '-','&nbsp;', {
    				     text : '<b>查询</b>',
    				     id : 'btn',
    				     iconCls : 'btnSearch',
    					 handler : searchLog
    				}
			],
	
			columns:[sm,rownum,
					{header: 'id', dataIndex: 'id',	hidden : true,sortable : true},
        		    {header: '用户编号' , dataIndex: 'userCode',width:60},
        			{header: '登录账号' , dataIndex: 'loginName',width:60},
			        {header: '用户名'   , dataIndex: 'userName',width:60},
			        {header: '生日类型' , dataIndex: 'birthdayType',hidden:true,width:50,renderer:function(v){
			        					return v=='0'?'农历':'阳历';
			        					}},
			        {header: '生日日期' , dataIndex: 'birthday',width:60,hidden:true,sortable : true},
    				{header: '工作状态' , dataIndex: 'workstatus',hidden:true,width:60,renderer:function(v){
			        					return v=='0'?'离职':'正常';
			        					}},
    			    {header: '人事状态' , dataIndex: 'hrstatus',hidden:true,width:60,renderer:function(v){
			        					return v=='0'?'试用期':'正常';
			        					}},
    			    {header: '身份证号码', dataIndex: 'manCode',hidden:true,width:130,sortable : true},
    			    {header: '办公电话'  , dataIndex: 'offTel',hidden:true,width:100},
    			    {header: '移动电话'  , dataIndex: 'telPhone',width:80,sortable : true},
    			    {header: '性别', dataIndex: 'sex',hidden:true,width:40,renderer:function(v){
			        					return v=='0'?'女':'男';
			        					}},
    			    {header: '部门名称', dataIndex: 'departName',width:150},
    			    {header: '岗位名称', dataIndex: 'stationName',width:180},
    			    {header: '从岗名称', dataIndex: 'stationNames',width:80,hidden : true},
					{header: '状态', dataIndex: 'status',hidden:true,renderer:function(v){
										if(v=='0'){
											return '删除';
										}else if(v=='1'){
											return '正常';
										}else{
											return '出错，无状态';
										}
			        					},width:40},
					{header: '用户级别', dataIndex: 'userLevel'},
					{header: '用户级别', dataIndex: 'userLevelString'},
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
	
function updateLevel() {
	var _records = recordGrid.getSelectionModel().getSelections();
	if (_records.length == 0) {
		Ext.Msg.alert('系统消息', '请选择一条您需要修改的数据');
		return;
	}else if(_records.length > 1) {
		Ext.Msg.alert('系统消息', '只能选择一条数据进行修改');
		return;
	}
	var qForm = new Ext.form.FormPanel({
	region : 'center',
	border : true,
	labelWidth : 90, // 标签宽度
	// frame : true, //是否渲染表单面板背景色
	labelAlign : 'right', // 标签对齐方式
	bodyStyle : 'padding:3 5 0', // 表单元素和表单面板的边距
	buttonAlign : 'center',
	height : 100,
	width:580,
	items : [{
				layout : 'column',
				border : false,
				items : [{
							columnWidth : .5,
							layout : 'form',
							labelWidth : 70, // 标签宽度
							defaultType : 'textfield',
							border : false,
							items : [{
										name : 'uid',
										id:'uid',
										xtype : "hidden"
									},{
										name : 'ts',
										id:'ts',
										xtype : "hidden"
									},{
										fieldLabel : '工号',
										name : 'grbm',
										id:'gh',
										readOnly:true,
										xtype : 'textfield', // 设置为数字输入框类型
										anchor : '100%'
									},{
										fieldLabel : '用户名', // 标签
										name : 'name', // name:后台根据此name属性取值
										id:'name',
										maxLength : 20, // 可输入的最大文本长度,不区分中英文字符
										allowBlank : true,
										readOnly:true,
										anchor : '100%'// 宽度百分比
									},{
										fieldLabel : '岗位名称', // 标签
										name : 'gw', // name:后台根据此name属性取值
										maxLength : 20, // 可输入的最大文本长度,不区分中英文字符
										allowBlank : true,
										readOnly:true,
										id:'gw',
										anchor : '100%'// 宽度百分比
									}]
						}, {
							columnWidth : .5,
							layout : 'form',
							labelWidth : 70, // 标签宽度
							defaultType : 'textfield',
							border : false,
							items : [{
										fieldLabel : '登录帐号', // 标签
										id : 'dl',
										readOnly:true,
										name : 'xm', // name:后台根据此name属性取值
										allowBlank : true, // 是否允许为空
										maxLength : 6, // 可输入的最大文本长度,不区分中英文字符
										anchor : '100%' // 宽度百分比
									},{
										fieldLabel : '部门名称',
										name : 'bm',
										id:'bm',
										readOnly:true,
										xtype : 'textfield', // 设置为数字输入框类型
										anchor : '100%'
									},{
										xtype : 'combo',
										triggerAction : 'all',
										typeAhead : true,
										store : levelTypeStore,
										resizable:true,
										allowBlank : false, // 是否允许为空
										emptyText : "用户等级",
										forceSelection : true,
										fieldLabel : '用户等级<span style="color:red">*</span>',
										editable : true,
										minChars : 0,
										valueField : 'id',//value值，与fields对应
										displayField : 'name',//显示值，与fields对应
										name:'name',
										id:'level',
										anchor : '100%'
									}]
						}, {
							columnWidth : .33,
							layout : 'form',
							labelWidth : 90, // 标签宽度
							defaultType : 'textfield',
							border : false,
							items : []
						}]
			}]
	});						
	
	var userId=_records[0].data.id;
	Ext.Ajax.request({
		url : sysPath+ "/"+ralaListUrl,
		params:{filter_EQL_id:userId,privilege:privilege,limit : pageSize},
		success : function(resp) {
			var text=Ext.decode(resp.responseText).result[0];
			Ext.getCmp("name").setValue(text.userName);
			Ext.getCmp("gw").setValue(text.stationName);
			Ext.getCmp("bm").setValue(text.departName);
			Ext.getCmp("dl").setValue(text.loginName);
			Ext.getCmp("ts").setValue(_records[0].data.ts);
			Ext.getCmp("uid").setValue(text.id);
			Ext.getCmp("gh").setValue(text.userCode);
			Ext.getCmp("level").setValue(text.userLevel);
			Ext.getCmp("level").setRawValue(text.userLevelString);
		},
		failure : function(response) {
			Ext.Msg.alert(alertTitle,"加载用户的数据失败");
		}
	});
			
	var win = new Ext.Window({
		title : "用户权限等级设置",
		width : 600,
		closeAction : 'hide',
		plain : true,
	    resizable : false,	
		modal : true,
		items : qForm,
		buttonAlign : "center",	buttons : [{
			text : "保存",
			iconCls : 'groupSave',
			handler : function() {
					if(Ext.getCmp("uid").getValue()==""){
						Ext.Msg.alert(alertTitle,"请选择用户再进行保存");
						return;
					}
					 if(!Ext.getCmp("level").validate()){
					 	Ext.Msg.alert(alertTitle,"请选择用户等级",
							function() {
								Ext.getCmp('uid').focus();
      							Ext.getCmp('uid').markInvalid("请选择用户等级");
							});
							return;
					 }
				     Ext.Msg.confirm(alertTitle,'您确定要修改用户等级数据吗?',function(btnYes) {
						if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true){ 	
							qForm.getForm().doAction('submit', {
								url : sysPath+ "/user/userAction!saveLevel.action",
								method : 'post',
								params : {
										id:Ext.getCmp("uid").getValue(),
										userLevel:Ext.getCmp("level").getValue(),
										privilege:23
								},
								waitMsg : '正在保存数据...',
								success : function(form1, action) {
									Ext.Msg.alert(
											alertTitle,
											action.result.msg,
											function() {
												dataStore.reload()
												win.close();
											});
								},
								failure : function(form1, action) {
									Ext.Msg.alert(
											alertTitle,
											action.result.msg,
											function() {
												dataStore.reload();
											});
								}
							});
							qForm.getForm().reset();
						}
					});
			}
		}, {
			text : "重置",
			iconCls : 'refresh',
			handler : function() {		
					if(_records.lenght==0){
						form.getForm().reset();
					}else{
						var userId=_records[0].data.id;
						Ext.Ajax.request({
							url : sysPath+ "/"+ralaListUrl,
							params:{filter_EQL_id:userId,privilege:privilege,limit : pageSize},
							success : function(resp) {
								var text=Ext.decode(resp.responseText).result[0];
								Ext.getCmp("name").setValue(text.userName);
								Ext.getCmp("gw").setValue(text.stationName);
								Ext.getCmp("bm").setValue(text.departName);
								Ext.getCmp("dl").setValue(text.loginName);
								Ext.getCmp("ts").setValue(_records[0].data.ts);
								Ext.getCmp("uid").setValue(text.id);
								Ext.getCmp("gh").setValue(text.userCode);
								Ext.getCmp("level").setValue(text.userLevel);
								Ext.getCmp("level").setRawValue(text.userLevelString);
							},
							failure : function(response) {
								Ext.Msg.alert(alertTitle,"加载用户的数据失败");
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
		qForm.destroy();
	});
	
	win.show();
 }
	
	function startUserPassword(length){
		var _records = recordGrid.getSelectionModel().getSelections();
		if (_records.length == 0) {
			Ext.Msg.alert('系统消息', '请选择您需要重置密码的数据');
			return;
		}
	     Ext.Msg.confirm(alertTitle,'请慎重使用密码重置功能！您确定要重置这<span style="color:red">&nbsp;'+ _records.length + '&nbsp;</span>行记录的登录密码吗?',function(btnYes) {
			if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
		        var ids = "";
				for(var i = 0; i < _records.length; i++) {
					ids += _records[i].data.id + ",";
				}  	
				form1.getForm().doAction('submit', {
					url : sysPath+ "/user/userAction!startPassword2.action",
					method : 'post',
					params : {
						ids : ids,
						privilege:privilege
					},
					waitMsg : '正在重置密码...',
					success : function(form1, action) {
						Ext.Msg.alert(alertTitle,action.result.msg);
					},
					failure : function(form1, action) {
						Ext.Msg.alert(alertTitle,action.result.msg);
					}
				});
			}
		});
	}
		
		function searchLog() {
		 if(Ext.getCmp('comboselect').getValue() == 'EQS_departId'){	
 			dataStore.baseParams = {
				checkItems : Ext.getCmp('comboselect').getValue(),
				privilege:privilege,
				limit : pageSize,
				itemsValue : Ext.get("departName").dom.value
   		 	}
  		 }else {	
			dataStore.baseParams = {
				checkItems : Ext.get("checkItems").dom.value,
				privilege:privilege,
				limit : pageSize,
				itemsValue : Ext.get("itemsValue").dom.value
	   		 }
		 }

		 dataStore.reload({
			params : {
				start : 0,
				limit : pageSize
			}
		 });
		}

});
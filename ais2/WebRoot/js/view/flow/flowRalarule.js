var fieldType=288;//表单人力资源（数据字典为人员档案的ID,切勿在数据字典里面删除该数据，否则程序将出错）
var stationType=289;//表单岗位字段（数据字典为岗位档案的ID,切勿在数据字典里面删除该数据，否则程序将出错）
function setRalarule(pipeId,nodeId){
	var ruleprivilege=256;
	var ralaruleFields=[
		{name:'id'},
		{name:'nodeId'},
		{name:'wfoptType'},
		{name:'shareType'},
		{name:'userobjType'},
		{name:'stationobjType'},
		{name:'usershareType'},
		{name:'roleobjType'},
		{name:'userIds'},
		{name:'formfieldId'},
		{name:'roleobjId'},
		{name:'wfoperatornodeId'},
		{name:'stationId'},
		{name:'userName'},
		{name:'formName'},
		{name:'formFieldName'},
		{name:'nodeName'},
		{name:'stationName'},
		{name:'status'},
		{name:'roleName'},
		{name:'createTime'},
		{name:'createName'},
		{name:'updateTime'},
		{name:'updateName'},
		{name:'ts'}
	];
	//人员Store
	var cusServiceStore=new Ext.data.Store({
		autoLoad:true,
        storeId:"cusServiceStore",
		proxy:new Ext.data.HttpProxy({url:sysPath+"/user/userAction!list.action"}),
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
    	{name:'id',mapping:'id'},
    	{name:'userName',mapping:'userName'}
    	])
    	
	});
	//节点信息Store
	var nodeinfoStore=new Ext.data.Store({
		storeId:"nodeinfoStore",
		baseParams:{privilege:245,filter_EQL_status:1,filter_EQL_pipeId:pipeId},
		proxy:new Ext.data.HttpProxy({url:sysPath+"/flow/flowNodeinfoAction!ralaList.action"}),
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
        {name:'id'},
        {name:'objName'}
        ])
	});
	//表单人力资源字段Store
	var formfieldStore=new Ext.data.Store({
		storeId:"formfieldStore",
		baseParams:{privilege:242,filter_EQL_status:1,filter_EQL_formId:formId,filter_EQL_fieldType:fieldType},
		proxy:new Ext.data.HttpProxy({url:sysPath+"/flow/flowFormfieldAction!ralaList.action"}),
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
        	{name:'id'},
        	{name:'labelName'}
        ])
	});
	//表单岗位字段Store
	var formstationStore=new Ext.data.Store({
		storeId:"formstationStore",
		baseParams:{privilege:242,filter_EQL_status:1,filter_EQL_formId:formId,filter_EQL_fieldType:stationType},
		proxy:new Ext.data.HttpProxy({url:sysPath+"/flow/flowFormfieldAction!ralaList.action"}),
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
        	{name:'id'},
        	{name:'labelName'}
        ])
	});
	//岗位Store
	var stationStore = new Ext.data.Store({ 
            storeId:"stationStore",                        
            proxy: new Ext.data.HttpProxy({url:sysPath+"/sys/stationAction!list.action",method:'post'}),
            reader: new Ext.data.JsonReader({
            root: 'result', totalProperty:'totalCount'
           }, [  
                 {name:'stationName',mapping:'text'},        
                 {name:'stationId',mapping:'id'}
              ])
     });
     //权限规则Store
     var ralaruleStore = new Ext.data.Store({ 
            storeId:"ralaruleStore",        
            baseParams:{privilege:ruleprivilege,filter_EQL_status:1,filter_EQL_nodeId:nodeId},
            proxy: new Ext.data.HttpProxy({url:sysPath+"/flow/flowRalaruleAction!ralaList.action",method:'post'}),
            reader: new Ext.data.JsonReader({
            root: 'result', totalProperty:'totalCount'
           },ralaruleFields)
     });
     var tb=new Ext.Toolbar({
		width:Ext.lib.Dom.getViewWidth(),
		id:'flowRalaTbar',
		items:['&nbsp;&nbsp;',{
    			text : '<B>保存</B>',
    			id : 'addFlowRalaBtn',
    			tooltip : '保存规则',
    			iconCls : 'save',
    			handler:function(){
    				if (form.getForm().isValid()) {
						form.getForm().submit({
							url : sysPath + "/flow/flowRalaruleAction!save.action?privilege="+ruleprivilege,
							waitMsg : '正在保存数据...',
							success : function(form, action) {
								//win.hide(), 
								Ext.Msg.alert(alertTitle,'保存成功！');
								ralaruleStore.reload();
							},
							failure : function(form, action) {
								if (action.faliureType == Ext.form.Action.SERVER_INVALID) {
									Ext.Msg.alert(alertTitle, "服务器连接异常，请联系管理员！");
								} else {
									if (action.result.msg) {
										win.hide();
										Ext.Msg.alert(alertTitle,
												action.result.msg);
									}
								}
							}
						});
					}
    			}
    		}]
	});
	var form = new Ext.form.FormPanel({
		frame : true,
		border : false,
		bodyStyle : 'padding:5px 5px 5px',
		labelAlign : "left",
		labelWidth : 90,
		width : 600,
		labelAlign : "right",
		defaults : {
			xtype : "textfield",
			width : 230
		},
		tbar:tb,
		items:[{
					xtype:'hidden',
					name:'status',
					id:'status',
					value:1
				},{
					xtype:'hidden',
					name:'nodeId',
					id:'nodeId',
					value:nodeId
				},
               {
               		xtype : 'combo',
					typeAhead : true,
					forceSelection : true,
					fieldLabel : '共享类型<span style="color:red">*</span>',
					store : [
						['1','人员类型'],
						['2','岗位类型'],
						['3','特定矩阵下的岗位体系'],
						['4','角色类型']
					],
					triggerAction : 'all',
					id:'combosharetype',
					hiddenName : 'shareType',
					emptyText : "请选择",
					blankText : '共享类型不能为空！',
					allowBlank : false,
					listeners:{
						'select':function(combo){
							if(combo.getValue()=='1'){
								showCombobox('combomantype');
								
		                        hideCombobox('combostationtype');
		                        hideCombobox('combostation');
		                        hideCombobox('comboroletype');
		                        hideCombobox('combousershareType');
		                        
								
							}else if(combo.getValue()=='2'){
								hideCombobox('combomantype');
								hideCombobox('comboroletype');
								hideCombobox('combouser');
								hideCombobox('combousershareType');
								
								showCombobox('combostationtype');
								showCombobox('combostation');
								
							}else if(combo.getValue()=='3'){
								hideCombobox('combomantype');
								hideCombobox('combonodeoper');
								hideCombobox('combouser');
								hideCombobox('comboformfield');
								hideCombobox('comboformstation');
								hideCombobox('comboroletype');
								
								showCombobox('combostationtype');
								showCombobox('combostation');
								showCombobox('combousershareType');
								
								
							}else if(combo.getValue()=='4'){
								hideCombobox('combomantype');
								hideCombobox('combonodeoper');
								hideCombobox('combouser');
								hideCombobox('comboformfield');
								hideCombobox('comboformstation');
								hideCombobox('combousershareType');
								hideCombobox('combostationtype');
								
								showCombobox('comboroletype');
							}
						}
					
					}
               },
               {
               		xtype : 'combo',
					typeAhead : true,
					forceSelection : true,
					fieldLabel : '人员类型<span style="color:red">*</span>',
					store : [
						['1','指定人员'],
						['2','流程操作者字段'],
						['3','表单人力资源字段']
					],
					triggerAction : 'all',
					id:'combomantype',
					hiddenName : 'userobjType',
					emptyText : "请选择",
					blankText : '人员类型不能为空！',
					allowBlank : false,
					listeners:{
						'select':function(combo){
							if(combo.getValue()=='1'){
								showCombobox('combouser');
								
								hideCombobox('combonodeoper');
								
								hideCombobox('comboformfield');
								
								hideCombobox('comboformstation');
							}else if(combo.getValue()=='2'){
								hideCombobox('combouser');
								
								showCombobox('combonodeoper');
								
								hideCombobox('comboformfield');
								hideCombobox('comboformstation');
							}else if(combo.getValue()=='3'){
								hideCombobox('combouser');
								hideCombobox('comboformstation');
								
								hideCombobox('combonodeoper');
								
								showCombobox('comboformfield');
							}
						}
					
					}
               },{
               		xtype : 'combo',
					typeAhead : true,
					forceSelection : true,
					fieldLabel : '岗位<span style="color:red">*</span>',
					store : [
						['1','指定岗位'],
						['2','流程操作者字段'],
						['3','表单人力资源字段'],
						['4','表单岗位字段']
					],
					triggerAction : 'all',
					id:'combostationtype',
					hiddenName : 'stationobjType',
					emptyText : "请选择",
					blankText : '岗位不能为空！',
					allowBlank : false,
					listeners:{
						'select':function(combo){
							if(combo.getValue()=='1'){
								showCombobox('combostation');
								
								hideCombobox('combonodeoper');
								
								hideCombobox('comboformfield');
								
								hideCombobox('comboformstation');
							}else if(combo.getValue()=='2'){
								hideCombobox('combostation');
								
								hideCombobox('comboformstation');
								
								showCombobox('combonodeoper');
								
								hideCombobox('comboformfield');
							}else if(combo.getValue()=='3'){
								hideCombobox('combostation');
								
								hideCombobox('combonodeoper');
								
								hideCombobox('comboformstation');
								
								showCombobox('comboformfield');
								
							}else if(combo.getValue()=='4'){
								hideCombobox('combostation');
								
								hideCombobox('combonodeoper');
								
								hideCombobox('comboformfield');
								
								showCombobox('comboformstation');
							}
						}
					
					}
               },
               {
               		xtype : 'combo',
					typeAhead : true,
					forceSelection : true,
					selectOnFocus : true,
					resizable : true,
					minChars : 0,
					pageSize:pageSize,
					fieldLabel:'人员名字<span style="color:red">*</span>',
					queryParam : 'filter_LIKES_userName',
					triggerAction : 'all',
					store : cusServiceStore,
					mode : "remote",// 从服务器端加载值
					valueField : 'id',// value值，与fields对应
					displayField : 'userName',// 显示值，与fields对应
				    hiddenName : 'userIds',
				    id:'combouser',
				    allowBlank : false,
				    blankText:'人员不能为空'
               },
               {
               		xtype:'combo',
	        		hiddenName:'wfoperatornodeId',
	        		id:'combonodeoper',
	        		emptyText:'请选择',
	        		store:nodeinfoStore,
	        		triggerAction : 'all',
	        		minChars:0,
	        		queryParam : 'filter_LIKES_objName',
	        		typeAhead : true,
					forceSelection : true,
	        		displayField:'objName',
	        		fieldLabel:'节点操作者<span style="color:red">*</span>',
	        		valueField:'id',
	        		allowBlank : false,
	        		blankText:'节点操作者不能为空！'
               },
               {
               		xtype:'combo',
	        		hiddenName:'fromfieldId',
	        		id:'comboformfield',
	        		emptyText:'请选择',
	        		store:formfieldStore,
	        		triggerAction : 'all',
	        		minChars:0,
	        		typeAhead : true,
					forceSelection : true,
	        		displayField:'labelName',
	        		fieldLabel:'相关字段<span style="color:red">*</span>',
	        		valueField:'id',
	        		allowBlank : false,
	        		blankText:'相关字段不能为空！'
               },
               {
               		xtype : 'combo',
				   fieldLabel: '岗位名称<span style="color:red">*</span>',
		           allowBlank : false,
		           typeAhead:false,
		           forceSelection : true,
			       minChars : 1,
			       id:'combostation',
			       triggerAction : 'all',
				   store: stationStore,
				   pageSize : pageSize,
				   queryParam : 'filter_LIKES_stationName',
				   displayField : 'stationName',
				   valueField : 'stationId',
				   hiddenName : 'stationId',
				   blankText : "岗位名称不能为空！",
				   emptyText : "请选择岗位名称"
               },
               {
               		xtype:'combo',
	        		hiddenName:'fromfieldId',
	        		id:'comboformstation',
	        		emptyText:'请选择',
	        		store:formstationStore,
	        		triggerAction : 'all',
	        		minChars:0,
	        		typeAhead : true,
					forceSelection : true,
	        		displayField:'labelName',
	        		fieldLabel:'岗位相关字段<span style="color:red">*</span>',
	        		valueField:'id',
	        		allowBlank : false,
	        		blankText:'相关字段不能为空！'
               },
               {
               		xtype:'combo',
	        		hiddenName:'roleobjType',
	        		id:'comboroletype',
	        		emptyText:'请选择',
	        		store:[
//	        			['1','直接上级岗位'],
//	        			['2','所有上级岗位']
	        		],
	        		triggerAction : 'all',
	        		typeAhead : true,
					forceSelection : true,
	        		fieldLabel:'角色类型<span style="color:red">*</span>',
	        		allowBlank : false,
	        		blankText:'角色类型不能为空！'
               },{
               		xtype:'combo',
	        		hiddenName:'usershareType',
	        		id:'combousershareType',
	        		emptyText:'请选择',
	        		store:[
	        			['1','直接上级岗位'],
	        			['2','所有上级岗位'],
	        			['3','岗位部门负责人']
	        		],
	        		triggerAction : 'all',
	        		typeAhead : true,
					forceSelection : true,
	        		fieldLabel:'矩阵体系<span style="color:red">*</span>',
	        		allowBlank : false,
	        		blankText:'矩阵体系不能为空！'
               },
               {
               	   xtype : 'combo',
				   fieldLabel: '审批类型<span style="color:red">*</span>',
		           allowBlank : false,
		           typeAhead:false,
		           forceSelection : true,
			       id:'combowfoptType',
			       hiddenName:'wfoptType',
			       triggerAction : 'all',
				   store : [
						['1','审批'],
						['2','知会'],
						['3','审核'],
						['4','依次逐个审批']
					],
				   blankText : "审批类型不能为空！",
				   emptyText : "请选择"
               }
        ]
	});
	var sm = new Ext.grid.CheckboxSelectionModel({});
    var rowNum = new Ext.grid.RowNumberer({
        header:'序号', width:35, sortable:true
    });
	var menuGrid = new Ext.grid.GridPanel({
        height:200, 
        width:600,
        viewConfig : {
    		scrollOffset: 0,
			autoScroll:true
		},
		stripeRows : true,
        frame:true, 
        loadMask:true,
        sm:sm,
        cm:new Ext.grid.ColumnModel([rowNum,sm,
        	{header:'ID',dataIndex:"id",hidden: true, hideable: false},
            {header:'共享类型',dataIndex:"shareType",renderer:function(v){
            	if(v==1){
            		return "人员类型";
            	}else if(v==2){
            		return "岗位类型";
            	}else if(v==3){
            		return "特定矩阵下的岗位体系";
            	}else if(v==4){
            		return "角色类型";
            	}
            }},
            {header:'审批类型',dataIndex:"wfoptType",renderer:function(v){
            	if(v==1){
            		return "审批";
            	}else if(v==2){
            		return "知会";
            	}else if(v==3){
            		return "审核";
            	}else{
            		return "依次逐个审批";
            	}
            }},
            {header:'人员类型',dataIndex:"userobjType",renderer:function(v){
            	if(v==1){
            		return "指定人员";
            	}else if(v==2){
            		return "流程操作者字段";
            	}else if(v==3){
            		return "表单人力资源字段";
            	}
            }},
            {header:'岗位类型',dataIndex:"stationobjType",renderer:function(v){
            	if(v==1){
            		return "指定岗位";
            	}else if(v==2){
            		return "流程操作者字段";
            	}else if(v==3){
            		return "表单人力资源字段";
            	}else if(v==4){
					return "表单岗位字段";            	
            	}
            }},
            {header:'人员ID',dataIndex:"userIds",hidden: true, hideable: false},
            {header:'人员名称',dataIndex:"userName"},
            
            {header:'表单字段ID',dataIndex:"formfieldId",hidden: true, hideable: false},
            {header:'表单字段',dataIndex:"formfieldName"},
            
            {header:'节点ID',dataIndex:"wfoperatornodeId",hidden: true, hideable: false},
            {header:'流程节点名称',dataIndex:"nodeName"},
            
            {header:'岗位ID',dataIndex:"stationId",hidden: true, hideable: false},
            {header:'岗位名称',dataIndex:"stationName"},
            {header:'矩阵体系',dataIndex:"usershareType",renderer:function(v){
            	if(v==1){
            		return "直接上级岗位";
            	}else if(v==2){
            		return "所有上级岗位";
            	}else if(v ==3){
            		return "岗位部门负责人";
            	}
            }},
            {header:"创建人",dataIndex:"createName",width:80,hidden: true},
            {header:"创建时间",dataIndex:"createTime",width:80,hidden: true},
            {header:"更新人",dataIndex:"updateName",width:50,hidden:true},
            {header:"更新时间",dataIndex:"updateTime",width:50,hidden:true}
           
        ]),
        store:ralaruleStore,
        tbar: [{
                text:'<B>删除</B>',id:'raladeletebtn',disabled:true, tooltip:'删除表单信息', iconCls: 'userDelete',
                handler: function(){
                	var _records = menuGrid.getSelectionModel().getSelections();
                	if (_records.length < 1) {
						parent.Ext.Msg.alert(alertTitle, "请选择您要操作的行！");
						return false;
					} 
					Ext.Msg.confirm(alertTitle, "确定要删除所选的"+_records.length+"行记录吗？", function(btnYes) {
						if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
								Ext.Ajax.request({
								url : sysPath+ "/flow/flowRalaruleAction!delete.action",
								params : {
									ralaId :_records[0].data.id,
									privilege:ruleprivilege
								},
								success : function(resp) {
								var respText = Ext.util.JSON.decode(resp.responseText);
								Ext.Msg.alert(alertTitle,"删除成功<br/>");
								form.getForm().reset();
								ralaruleStore.reload();
										}
									});
								}
							});
					
                }
            }
        ]
    });
    menuGrid.on('click',function(){
    	 var _record = menuGrid.getSelectionModel().getSelections();
         var updatebtn = Ext.getCmp('raladeletebtn');
         if (_record.length==1) {       	
            if(updatebtn){
            	updatebtn.setDisabled(false);
            }
         } else {
            if(updatebtn){
            	updatebtn.setDisabled(true);
            }
         }
    });
    
    var win = new Ext.Window({
		title : '节点操作者管理',
		width : 600,
		closeAction : 'hide',
		plain : true,
		modal : true,
		layout:'form',
		items : [form,menuGrid],
		buttonAlign : "center",
		buttons : [{
			text : "关闭",
			handler : function() {
				win.close();
			}
		}]
	});
	ralaruleStore.load();
	win.on('hide', function() {
				form.destroy();
			});
	win.show();
}

function hideCombobox(cid){
	Ext.getCmp(cid).hide();
	Ext.getCmp(cid).setDisabled(true);
	Ext.getCmp(cid).getEl().up('.x-form-item').setDisplayed(false);
}
function showCombobox(cid){
	Ext.getCmp(cid).show();
	Ext.getCmp(cid).setDisabled(false);
	Ext.getCmp(cid).getEl().up('.x-form-item').setDisplayed(true);
}
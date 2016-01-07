var dictionaryUrl='sys/dictionaryAction!ralaList.action';//数据字典地址
var nodeprivilege=245;
var nodeinfoFields=[
	{name:'id'},
	{name:'objName'},//节点名称
	{name:'pipeId'},//流程ID
	{name:'pipeName'},//流程名称
	{name:'nodeType'},//节点类型
	{name:'isReject'},//是否允许退回
	{name:'rejectnodeId'},//退回节点
	{name:'rejectnodeName'},//退回节点名称
	{name:'perPage'},//节点预处理页面
	{name:'afterPage'},//节点后处理页面
	{name:'isRtx'},//是否rtx提醒(0不提醒\1提醒)
	{name:'subBtnName'},//提交按钮名称
	{name:'saveBtnName'},//保存按钮名称
	{name:'isAutoflow'},//是否自动流转(0不自动流转\1自动流转)
	{name:'status'},
	{name: 'createName'},
    {name: 'createTime'},
    {name: 'updateName'},
    {name: 'updateTime'},
    {name:'ts'}
];
function addNodeinfo(_record,pipeId,pipeName,nodeId){
	var nNodeId=null;
	if(_record != null){
		nNodeId=_record[0].data.id;
	}else if(nodeId != null){
		nNodeId=nodeId;
	}
	//节点类型
	var nodeTypeStore= new Ext.data.Store({ 
			storeId:"nodeTypeStore",
			baseParams:{filter_EQL_basDictionaryId:286,privilege:16},
			proxy:new Ext.data.HttpProxy({url:sysPath+"/"+dictionaryUrl}),
			reader:new Ext.data.JsonReader({
	                    root:'result',
	                    totalProperty:'totalCount'
	        },[
        	{name:'id',mapping:'id'},
        	{name:'nodeType',mapping:'typeName'}
        	])
	});
	//回退节点Store
	var renodeStore= new Ext.data.Store({ 
			storeId:"renodeStore",
			baseParams:{privilege:nodeprivilege,filter_EQL_pipeId:pipeId},
			proxy:new Ext.data.HttpProxy({url:sysPath+"/flow/flowNodeinfoAction!ralaList.action"}),
			reader:new Ext.data.JsonReader({
	                    root:'result',
	                    totalProperty:'totalCount'
	        },[
        	{name:'id',mapping:'id'},
        	{name:'objName'}
        	])
	});
	var form = new Ext.form.FormPanel({
		frame : true,
		border : false,
		bodyStyle : 'padding:5px 5px 5px',
		labelAlign : "left",
		labelWidth : 85,
		width : 400,
		labelAlign : "right",
		defaults : {
			xtype : "textfield",
			width : 230
		},
		reader : new Ext.data.JsonReader({
            root: 'result', totalProperty: 'totalCount'
        },nodeinfoFields),
		items:[
			   {xtype:'hidden',name:'ts'},
			   {xtype:'hidden',name:'id',id:'id'},
			   //{xtype:'hidden',name:'drawxpos',id:'drawxpos',value:-1},
			   //{xtype:'hidden',name:'drawypos',id:'drawypos',value:-1},
			   {xtype:'hidden',name:'status',id:'status',value:1},
			   {
			   		xtype:'textfield',
			   		name:'objName',
			   		id:'objName',
			   		fieldLabel:'节点名称<span style="color:red">*</span>',
			   		maxLength:25,
					maxLengthText:'长度不能超过25个汉字'
			   },
			   {xtype:'hidden',name:'pipeId',id:'pipeId',value:pipeId},
			   {xtype:'textfield',name:'pipeName',id:'pipeName',fieldLabel:'所属流程',readOnly:true,value:pipeName},
               {
               		xtype : 'combo',
					typeAhead : true,
					forceSelection : true,
					fieldLabel : '节点类型<span style="color:red">*</span>',
					store :nodeTypeStore,
					valueField:'nodeType',
					displayField:'nodeType',
					triggerAction : 'all',
					id:'combonodetype',
					name : 'nodeType',
					emptyText : "请选择",
					blankText : '节点类型不能为空！',
					allowBlank : false
               },
               {
               		xtype:'radiogroup',
                   	id:'nodeisReject',
                   	fieldLabel:'允许退回<span style="color:red">*</span>',
                   	items: [{
	                    inputValue:'1',
	                    boxLabel: '是',
	                    name:'isReject',
	                    checked:_record==null?false:_record!=null && _record[0].data.isReject=="1"?true:false
	                }, {
	                    inputValue:'0',
	                    name:'isReject',
	                    boxLabel: '否',
	                    checked:_record==null?true:_record!=null && _record[0].data.isReject=="0"?true:false
	                }]
               },
               {
               		xtype : 'combo',
					typeAhead : true,
					forceSelection : true,
					fieldLabel : '退回节点',
					store :renodeStore,
					valueField:'id',
					displayField:'objName',
					triggerAction : 'all',
					id:'comborenode',
					hiddenName : 'rejectnodeId',
					emptyText : "请选择"
               },
               {
               		xtype:'textfield',
               		name:'perPage',
               		fieldLabel:'节点预处理',
               		id:'perPage',
               		maxLength:125,
					maxLengthText:'长度不能超过125个汉字'
               },
               {
               		xtype:'textfield',
               		name:'afterPage',
               		id:'afterPage',
               		fieldLabel:'节点后处理',
               		maxLength:125,
					maxLengthText:'长度不能超过125个汉字'
               },
               {
               		xtype:'textfield',
               		name:'subBtnName',
               		id:'subBtnName',
               		fieldLabel:'提交按钮名称',
               		maxLength:25,
					maxLengthText:'长度不能超过25个汉字'
               },
               {
               		xtype:'textfield',
               		name:'saveBtnName',
               		id:'saveBtnName',
               		fieldLabel:'保存按钮名称',
               		maxLength:25,
					maxLengthText:'长度不能超过25个汉字'
               },
               {
               		xtype:'radiogroup',
                   	id:'nodeisAutoflow',
                   	fieldLabel:'自动流转<span style="color:red">*</span>',
                   	items: [{
	                    inputValue:'1',
	                    boxLabel: '是',
	                    name:'isAutoflow',
	                    checked:_record==null?false:_record!=null && _record[0].data.isAutoflow=="1"?true:false
	                }, {
	                    inputValue:'0',
	                    name:'isAutoflow',
	                    boxLabel: '否',
	                    checked:_record==null?true:_record!=null && _record[0].data.isAutoflow=="0"?true:false
	                }]
               },{
               		xtype:'radiogroup',
                   	id:'nodeisRxt',
                   	fieldLabel:'是否RTX提醒<span style="color:red">*</span>',
                   	items: [{
	                    inputValue:'1',
	                    boxLabel: '是',
	                    checked:_record==null?false:_record!=null && _record[0].data.isRtx=="1"?true:false,
	                    name:'isRtx'
	                }, {
	                    inputValue:'0',
	                    name:'isRtx',
	                    boxLabel: '否',
	                    checked:_record==null?true:_record!=null && _record[0].data.isRtx=="0"?true:false
	                }]
               }
        ]
	});
	formfieldTitle="新增节点信息";
	if(nNodeId!=null){
		formfieldTitle="修改节点信息";
		Ext.getCmp("id").setValue(nNodeId);
		form.load({
			url : sysPath+ "/flow/flowNodeinfoAction!ralaList.action",
			params:{filter_EQL_id:nNodeId,limit : pageSize,privilege:245},
			success:function(_form, action){
				if(_record !=null){
					var rejectnodeName=_record[0].data.rejectnodeName;
					Ext.getCmp('comborenode').setRawValue(rejectnodeName);
				}else{
					Ext.Ajax.request({
						url:sysPath+ "/flow/flowNodeinfoAction!ralaList.action",
						params:{
							filter_EQL_id:nNodeId,
							limit : pageSize,
							privilege:245
						},
						success:function(resp){
							var respText = Ext.util.JSON.decode(resp.responseText);
							var rejectnodeName=respText.result[0].rejectnodeName;
							var isRtx=respText.result[0].isRtx;
							var isAutoflow=respText.result[0].isAutoflow;
							var isReject=respText.result[0].isReject;
							
							Ext.getCmp('comborenode').setRawValue(rejectnodeName);
							Ext.getCmp('nodeisRxt').setValue(isRtx);
							Ext.getCmp('nodeisAutoflow').setValue(isAutoflow);
							Ext.getCmp('nodeisReject').setValue(isReject);
						}
					});
				}
			}
		})
	}
	var win = new Ext.Window({
		title : formfieldTitle,
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
					var isReject = form.getForm().getValues()['isReject'];
					var comborenode=Ext.getCmp('comborenode').getValue();
					if(isReject == 1 && comborenode==''){
						Ext.Msg.alert(alertTitle,'回退节点不能为空！');
						return;
					}else{
						form.getForm().submit({
							url : sysPath + "/flow/flowNodeinfoAction!save.action?privilege="+nodeprivilege,
							waitMsg : '正在保存数据...',
							success : function(form, action) {
								win.hide(), 
								Ext.Msg.alert(alertTitle,'保存成功！');
								nodeinfoReload();
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
			}
		}, {
			text : "重置",
			handler : function() {
				form.getForm().reset();
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
var privilege=264;
var searchUrl="flow/flowPipeinfoAction!list.action";//查询地址
var ralaUrl="flow/flowRalaGiveAction!ralaList.action";
var dictionaryUrl='sys/dictionaryAction!ralaList.action';//数据字典地址
var pipeinfoStore,cusServiceStore,ralaGiveStore,operTitle;
var fields=[
	{name:'id'},
	{name:'pipeId'},
	{name:'pipeName'},
	{name:'startDate'},
	{name:'endDate'},
	{name:'startTime'},
	{name:'endTime'},
	{name:'giveId'},
	{name:'userId'},
	{name:'createName'},
	{name:'createTime'},
	{name:'updateName'},
	{name:'updateTime'},
	{name:'remark'},
	{name:'departId'},
	{name:'departName'},
	{name:'userName'},
	{name:'giveName'},
    {name: 'ts'}
];

Ext.onReady(function() {
    pipeinfoStore = new Ext.data.Store({
        storeId:"pipeinfoStore",
        proxy: new Ext.data.HttpProxy({url:sysPath+"/"+searchUrl}),
        reader: new Ext.data.JsonReader({
            root: 'result', totalProperty: 'totalCount'
        }, [
			{name:'id'},
            {name: 'objName'}
		])
    });
    ralaGiveStore = new Ext.data.Store({
        storeId:"ralaGiveStore",
        proxy: new Ext.data.HttpProxy({url:sysPath+"/"+ralaUrl}),
        baseParams:{privilege:privilege,limit:pageSize,filter_EQL_userId:userId},
        reader: new Ext.data.JsonReader({
            root: 'result', totalProperty: 'totalCount'
        }, fields)
    });
    //人员Store
	cusServiceStore=new Ext.data.Store({
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
    //start
    var sm = new Ext.grid.CheckboxSelectionModel({});
    var rowNum = new Ext.grid.RowNumberer({
        header:'序号', width:35, sortable:true
    });
    var menuGrid = new Ext.grid.GridPanel({
        renderTo:'showView',
        height:Ext.lib.Dom.getViewHeight(), 
        width:Ext.lib.Dom.getViewWidth(),
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
        	{header:'流程ID',dataIndex:"pipeId",hidden: true, hideable: false},
            {header:'流程名称',dataIndex:"pipeName"},
            {header:"授予人ID",dataIndex:"giveId",width:50,hidden:true, hideable: false},
            {header:"授予人",dataIndex:"giveName",width:50},
            {header:'开始日期',dataIndex:"startDate",width:80},
            {header:"结束日期",dataIndex:"endDate",width:80},
            {header:"开始时间",dataIndex:"startTime",width:80},
            {header:"结束时间",dataIndex:"endTime",width:80},
            {header:"授权人ID",dataIndex:"userId",hidden:true, hideable: false},
            {header:"授权人",dataIndex:"userName",width:50}
           
        ]),
        store:ralaGiveStore,
        tbar: [
        	{   
        		text : '<B>新增授权</B>',id:'flowralagive', iconCls: 'userAdd',
	    		handler :function(){ 
	    			flowRalaGive(null);
	    		}
	    	},'-',{   
        		text : '<B>修改授权</B>',id:'upflowralagive', iconCls: 'userEdit',
	    		handler :function(){ 
	    			var _records = menuGrid.getSelectionModel().getSelections();
	    			flowRalaGive(_records);
	    		}
	    	},'-',	
	    	{text : '<B>权限收回</B>',id:'flowralarecover',disabled:true, iconCls: 'userDelete',
	    		handler :function(){
	    			Ext.Msg.confirm(alertTitle, "确定要收回权限吗？", function(btnYes) {
						if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
							var _records = menuGrid.getSelectionModel().getSelections();
							var ids="";
							for(var i=0;i<_records.length;i++){
								ids=ids+_records[i].data.id+",";
							}
	    					Ext.Ajax.request({
								url : sysPath+ "/flow/flowRalaGiveAction!delete.action",
								params : {
									ids : ids,
									privilege:privilege
								},
								success : function(resp) {
									var respText = Ext.util.JSON.decode(resp.responseText);
									Ext.Msg.alert(alertTitle,"删除成功<br/>");
									menuStoreReload();
								}
							});
						}
					});
	    		}
	    	},'流程名称:',
            {
            	xtype:'textfield',
            	name:'pipeName',
            	id:'searchPipename',
            	width:100
            },
            '-',
            {
            	text:'<B>搜索</B>',
            	id:'btn', 
            	iconCls: 'btnSearch',
            	hidden:false,
            	handler : searchPipeinfo
            }
        ],
        bbar: new Ext.PagingToolbar({
            pageSize: pageSize,
            privilege:privilege, 
            store: ralaGiveStore, 
            displayInfo: true,
            displayMsg: '显示第 {0} 条到 {1} 条记录，共 {2} 条', emptyMsg: "没有记录信息显示"
        })
    });
       menuGrid.render();
     function searchPipeinfo() {
		var pName=Ext.getCmp('searchPipename').getValue();
		ralaGiveStore.baseParams = {
			filter_LIKES_pipeName:pName
		}
		var editbtn = Ext.getCmp('upflowralagive');
		var deletebtn = Ext.getCmp('flowralarecover');

		editbtn.setDisabled(true);
		deletebtn.setDisabled(true);
		menuStoreReload();
		
	}
     //查询
   /*
   menuStore.load({
   params:{
   	limit:pageSize,
   	filter_EQL_id:0
   	}
   });*/
    menuGrid.on('click', function() {
        var _record = menuGrid.getSelectionModel().getSelections();
        var updatebtn = Ext.getCmp('upflowralagive');
        var deletebtn = Ext.getCmp('flowralarecover');

        if (_record.length==1) {       	
            if(updatebtn){
            	updatebtn.setDisabled(false);
            }
            if(deletebtn){
            	deletebtn.setDisabled(false);
            }
        } else if(_record.length>1){
        	if(updatebtn){
            	updatebtn.setDisabled(true);
            }
        	if(deletebtn){
            	deletebtn.setDisabled(false);
            }
        }else {
            if(updatebtn){
            	updatebtn.setDisabled(true);
            }
			if(deletebtn){
            	deletebtn.setDisabled(true);
			}
        }
    });
    //end
});
function menuStoreReload(){
	ralaGiveStore.reload({
		params:{
			privilege:privilege,
			filter_EQL_userId:userId,
			start : 0,
			limit : pageSize
		}
	});
}

function flowRalaGive(_records){
	var form = new Ext.form.FormPanel({
		frame : true,
		border : false,
		bodyStyle : 'padding:5px 5px 5px',
		labelAlign : "left",
		labelWidth : 80,
		width : 400,
		labelAlign : "right",
		defaults : {
			xtype : "textfield",
			width : 230
		},
		reader: new Ext.data.JsonReader({
            root: 'result', totalProperty: 'totalCount'
        }, fields),
		items:[
			   {xtype:'hidden',name:'ts'},
			   {xtype:'hidden',name:'id',id:'flowRalaId'},
			   {xtype:'hidden',name:'userId',value:userId},
               {
               		xtype : 'combo',
					typeAhead : true,
					forceSelection : true,
					resizable : true,
					minChars : 0,
					pageSize:pageSize,
					fieldLabel:'流程名称<span style="color:red">*</span>',
					queryParam : 'filter_LIKES_objName',
					triggerAction : 'all',
					store : pipeinfoStore,
					mode : "remote",// 从服务器端加载值
					valueField : 'id',// value值，与fields对应
					displayField : 'objName',// 显示值，与fields对应
				    hiddenName : 'pipeId',
				    id:'combopipe',
				    allowBlank : false,
				    blankText:'流程不能为空'
               		
               	},
               {
               		xtype : 'combo',
					forceSelection : true,
					resizable : true,
					minChars : 0,
					pageSize:pageSize,
					fieldLabel:'赋予人<span style="color:red">*</span>',
					queryParam : 'filter_LIKES_userName',
					triggerAction : 'all',
					store : cusServiceStore,
					mode : "remote",// 从服务器端加载值
					valueField : 'id',// value值，与fields对应
					displayField : 'userName',// 显示值，与fields对应
				    hiddenName : 'giveId',
				    id:'combouser',
				    allowBlank : false,
				    blankText:'人员不能为空'
               },
               {
               		xtype:'datefield',
               		name:'startDate',
               		fieldLabel:'开始日期',
               		id:'startDate',
               		format:'Y-m-d'
               },
               {
               		xtype:'datefield',
               		name:'endDate',
               		fieldLabel:'结束日期',
               		id:'endDate',
               		format:'Y-m-d'
               },
               {
               		xtype:'timefield',
               		name:'startTime',
               		fieldLabel:'开始时间',
               		id:'startTime',
               		format:'H:i',
               		increment:60
               },
               {
               		xtype:'timefield',
               		name:'endTime',
               		fieldLabel:'结束时间',
               		id:'endTime',
               		format:'H:i',
               		increment:60
               },
               {
               		xtype:'textarea',
               		name:'remark',
               		fieldLabel:'备注',
               		id:'remark',
               		height:60
               }
        ]
	});
	operTitle="添加授权信息";
		if(_records!=null){
		    var _cid=_records[0].data.id;
			operTitle="修改授权信息";
			Ext.getCmp("flowRalaId").setValue(_cid);
			form.load({
				url : sysPath+"/"+ralaUrl,
				params:{filter_EQL_id:_cid,privilege:privilege,limit : pageSize},
				success:function(){
					var pipeName = _records[0].data.pipeName;
					var giveName = _records[0].data.giveName;
					Ext.getCmp('combopipe').setRawValue(pipeName);
					Ext.getCmp('combouser').setRawValue(giveName);
				}
		})
	}
	var win = new Ext.Window({
		title : operTitle,
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
					form.getForm().submit({
						url : sysPath + "/flow/flowRalaGiveAction!save.action?privilege="+privilege,
						waitMsg : '正在保存数据...',
						success : function(form, action) {
							win.hide(), 
							Ext.Msg.alert(alertTitle,'保存成功！');
							menuStoreReload();
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
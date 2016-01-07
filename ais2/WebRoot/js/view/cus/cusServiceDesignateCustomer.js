//客服员指派客户js
var privilege=48;//权限参数
var gridSearchUrl="cus/cusServiceAction!findServiceDesignate.action";//客服员指派客户查询地址
var saveServiceDesignateUrl="cus/cusServiceAction!saveServiceDesignate.action";//客服员指派客户保存地址
var deleteServiceDesignateUrl="cus/cusServiceAction!deleteServiceDesignate.action";//客服员指派客户删除地址
var cusServiceUrl='user/userAction!list.action';//客服员查询地址
var cusRecordSearchUrl='cus/customerListAction!findCustomerList.action';//客户查询地址
var defaultWidth=80;

var  fields=['CUS_RECORD_ID','CUS_NAME','CUS_ID','USER_CODE','USER_NAME','SERVICE_NAME','CREATE_TIME','CREATE_NAME'];
	//客服员 cusServiceUrl
	cusServiceStore=new Ext.data.Store({
        storeId:"cusServiceStore",
		proxy:new Ext.data.HttpProxy({url:sysPath+"/"+cusServiceUrl}),
		baseParams:{filter_EQL_bussDepart:bussDepart},
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
       	{name:'cusServiceId',mapping:'id'},
       	{name:'cusServiceName',mapping:'userName'},
       	{name:'userCode',mapping:'userCode'}
       	])
       	
	});
	
	cusRecordStore=new Ext.data.Store({
        storeId:"cusRecordStore",
		proxy:new Ext.data.HttpProxy({url:sysPath+"/"+cusRecordSearchUrl}),
		reader:new Ext.data.JsonReader({
                    root:'resultMap',
                    totalProperty:'totalCount'
        },[
       	{name:'recordId',mapping:'ID'},
       	{name:'cusName',mapping:'CUS_NAME'}
       	])
       	
	});
	
	var tbar=[
	 	{text:'<b>导出</b>',iconCls:'sort_down',tooltip : '导出数据',
	 		handler:function(){
	 				parent.exportExl(designateGrid);
	 		}
	 	},'-',{text:'<b>新增</b>',iconCls:'userAdd',id:'basDesignateGridAdd',handler:function() {
                	saveDesignateGrid(null);
            } },'-',
	 	{text:'<b>复制</b>',iconCls:'userEdit',id:'basDesignateGridEdit',disabled:true,handler:function(){
			var _records = designateGrid.getSelectionModel().getSelections();
			if (_records.length < 1) {
				Ext.Msg.alert(alertTitle, "请选择您要复制的行！");
				return false;
			} else if (_records.length > 1) {
				Ext.Msg.alert(alertTitle, "一次只能复制一行！");
				return false;
			}
	 		saveDesignateGrid(_records);
	 	}},'-',
	 	{text:'<b>删除</b>',iconCls:'userDelete',id:'basDesignateGridDelete',disabled:true,tooltip : '删除车辆',handler:function(){
			var _records = designateGrid.getSelectionModel().getSelections();
			if (_records.length < 1) {
				Ext.Msg.alert(alertTitle, "请选择您要删除的行！");
				return false;
			} 
			var datas="";
			for(var i=0;i<_records.length;i++){
				if(i>0){
					datas+='&';
				}	
				datas+='cusServiceList['+i+'].cusRecordId='+_records[i].data.CUS_RECORD_ID+'&'
				     +'cusServiceList['+i+'].userCode='+_records[i].data.USER_CODE;
			}
			Ext.Msg.confirm(alertTitle, "确定要删除"+_records.length+"条记录吗？", function(
							btnYes) {
						if (btnYes == 'yes' || btnYes == 'ok'
								|| btnYes == true) {
							
							Ext.Ajax.request({
								url : sysPath+'/'
										+ deleteServiceDesignateUrl,
								params : datas,
								success : function(resp) {
									var respText = Ext.util.JSON
											.decode(resp.responseText);
									if(respText.success){
										Ext.Msg.alert(alertTitle,"删除成功!");
										dataReload();
									}else{
										Ext.Msg.alert(alertTitle,respText.msg);
									}
								}
							});
						}
					});
	 	}},'-','创建时间:',{ 
    		xtype : 'datefield',
    		id : 'startDate',
    		format : 'Y-m-d',
    		emptyText : "开始时间",
    		anchor : '95%',
    		value:new Date().add(Date.DAY, -7),
    		hidden : false,
    		width : 100,
    		disabled : false,
    		listeners : {
    			'select' : function() {
    			   var start = Ext.getCmp('startDate').getValue().format("Y-m-d");
    			   Ext.getCmp('endDate').setMinValue(start);
    		     }
    		}
    	}, '&nbsp;&nbsp;', {
    		xtype : 'datefield',
    		id : 'endDate',
    		format : 'Y-m-d',
    		emptyText : "结束时间",
    		value:new Date(),
    		hidden : false,
    		width : 100,
    		disabled : false,
    		anchor : '95%'
    	},'-','被指派用户编码：',{
    		xtype:'textfield',
			id:'searchUserCode',
			width : 80,
			enableKeyEvents:true,
			listeners : {
				keyup:function(textField, e){
               		if(e.getKey() == 13){
               			searchdesignate();
               		}
				}
			}
    	},'-',{text : '<b>查询</b>',id : 'btn',iconCls : 'btnSearch',
   					handler : searchdesignate
   		}];
var twobar = new Ext.Toolbar([
		'-','客户名称:',{
			xtype:'combo',
			triggerAction : 'all',
			store : cusRecordStore,
			width:100,
			pageSize : pageSize,
			queryParam : 'filter_LIKES_cusName',
			selectOnFocus:true,
			resizable:true,
			minChars : 0,
			mode : "remote",//从服务器端加载值
			valueField : 'recordId',//value值，与fields对应
			displayField : 'cusName',//显示值，与fields对应
			id:'searchCusName',
			enableKeyEvents:true,
			listeners : {
				keyup:function(textField, e){
               		if(e.getKey() == 13){
               			searchdesignate();
               		}
				}
			}
		},'-','日常维护客服员名称：',{
			xtype:'combo',
			triggerAction : 'all',
			store : cusServiceStore,
			width:100,
			pageSize : pageSize,
			queryParam : 'filter_LIKES_userName',
			selectOnFocus:true,
			resizable:true,
			minChars : 0,
			mode : "remote",//从服务器端加载值
			valueField : 'userCode',//value值，与fields对应
			displayField : 'cusServiceName',//显示值，与fields对应
			id:'searchCusServiceName',
			enableKeyEvents:true,
			listeners : {
				keyup:function(textField, e){
               		if(e.getKey() == 13){
               			searchdesignate();
               		}
				}
			}
		},'-','被指派客服员名称：',{
			xtype:'combo',
			triggerAction : 'all',
			store : cusServiceStore,
			width:100,
			pageSize : pageSize,
			queryParam : 'filter_LIKES_userName',
			selectOnFocus:true,
			resizable:true,
			minChars : 0,
			mode : "remote",//从服务器端加载值
			valueField : 'userCode',//value值，与fields对应
			displayField : 'cusServiceName',//显示值，与fields对应
			id:'searchUserName',
			enableKeyEvents:true,
			listeners : {
				keyup:function(textField, e){
               		if(e.getKey() == 13){
               			searchdesignate();
               		}
				}
			}
		}
]); 	
Ext.onReady(function() {
   	dateStore = new Ext.data.Store({
        storeId:"dateStore",
        proxy: new Ext.data.HttpProxy({url : sysPath+ "/"+gridSearchUrl}),
        baseParams:{privilege:privilege,limit : pageSize},
        reader: new Ext.data.JsonReader({
            root: 'resultMap', totalProperty: 'totalCount'
        }, fields)
    });
    //dateStore.on("load" ,function( store, records,options ){alert(records.length);});
    
   var sm = new Ext.grid.CheckboxSelectionModel({});
	 var rowNum = new Ext.grid.RowNumberer({
        header:'序号', width:20
    });
    designateGrid = new Ext.grid.GridPanel({
        renderTo: Ext.getBody(),
        id:'designateCenter',
        height:Ext.lib.Dom.getViewHeight()-1,		
        width:Ext.lib.Dom.getViewWidth()-1,
		autoScroll : true,
		//autoSizeColumns: true,
		frame : false,
		loadMask : true,
	//	stripeRows : true,
        sm:sm,
        cm:new Ext.grid.ColumnModel([rowNum,sm,
        		{header: '客户ID', dataIndex: 'CUS_RECORD_ID',width:defaultWidth,sortable : true},
        		{header: '客商ID', dataIndex: 'CUS_ID',width:defaultWidth,sortable : true},
       			{header: '客户名称', dataIndex: 'CUS_NAME',width:defaultWidth*2,sortable : true},
       			{header: '指派客服员编码', dataIndex: 'USER_CODE',width:defaultWidth*2,sortable : true},
       			{header: '指派客服员', dataIndex: 'USER_NAME',width:defaultWidth*2,sortable : true},
       			{header: '日常维护人', dataIndex: 'SERVICE_NAME',width:defaultWidth*2,sortable : true},
       			{header: '创建人', dataIndex: 'CREATE_NAME',width:defaultWidth*2,sortable : true},
       			{header: '创建时间', dataIndex: 'CREATE_TIME',width:defaultWidth*2,sortable : true}
        ]),
        store:dateStore,
        tbar: tbar,
        listeners : {
			render : function() {
				twobar.render(this.tbar);
			}
		},
        bbar: new Ext.PagingToolbar({
            store: dateStore, 
            pageSize: pageSize,
            afterPageText: '共{0}页',
		    beforePageText: '当前页',
            displayInfo: true,
            displayMsg: '显示第 {0} 条到 {1} 条记录，共 {2} 条', 
            emptyMsg: "没有记录信息显示"
        })
    });
    
      designateGrid.on('click', function() {
	       selabled();
	  });
  });
    
    
	
   function searchdesignate() {
		dateStore.baseParams = {
			privilege:privilege,
			limit : pageSize
		};
		var startCount = Ext.get('startDate').dom.value;
		var endCount = Ext.get('endDate').dom.value;
		var searchCusName = Ext.get('searchCusName').dom.value;
		var searchCusServiceName = Ext.get('searchCusServiceName').dom.value;
		var searchUserName = Ext.get('searchUserName').dom.value;
		var searchUserCode = Ext.get('searchUserCode').dom.value;
 	    Ext.apply(dateStore.baseParams, {
 	    	filter_t_userCode:searchUserCode,
 	    	filter_LIKES_u_userName:searchUserName,
 	    	filter_LIKES_r_cusName:searchCusName,
 	    	filter_LIKES_b_serviceName:searchCusServiceName,
		 	filter_countCheckItems:'t.create_time',
	 	 	filter_startCount:startCount=='开始时间'?'':startCount,
 	 		filter_endCount:endCount=='结束时间'?'':endCount
   		});
		dataReload();
	}
 function dataReload(){
	dateStore.load({
		params : {
			start : 0,
			privilege:privilege,
			limit : pageSize
		}
	});
	selabled();
 }
  function selabled(){
 	 var _record = designateGrid.getSelectionModel().getSelections();
 	
        var updatebtn = Ext.getCmp('basDesignateGridEdit');
        var deletebtn = Ext.getCmp('basDesignateGridDelete');
        if (_record.length==1) {       	
            if(updatebtn){
            	updatebtn.setDisabled(false);
            }
            if(deletebtn){
            	deletebtn.setDisabled(false);
            }
        }else if(_record.length>1){
        	 if(updatebtn){
            	updatebtn.setDisabled(true);
            }
            if(deletebtn){
            	deletebtn.setDisabled(false);
            }
        } else {
            if(updatebtn){
            	updatebtn.setDisabled(true);
            }
			if(deletebtn){
            	deletebtn.setDisabled(true);
			}
        }
 }
 function saveDesignateGrid(records){
 		var cid;
 		var vuserCode;
		if(records!=null){
			cid=records[0].data.CUS_RECORD_ID;
			vuserCode=records[0].data.USER_CODE;
		}
		var form = new Ext.form.FormPanel({
					labelAlign : 'left',
					frame : true,
					url:sysPath+'/'+gridSearchUrl,
					baseParams:{
	    				filter_t_cusRecordId:cid,
	    				filter_t_userCode:vuserCode,
						limit : pageSize},
					bodyStyle : 'padding:5px 5px 0',
					width : 350,
					reader : new Ext.data.JsonReader({
            root: 'resultMap', totalProperty: 'totalCount'
        }, fields),
		labelAlign : "right",
	
			items : [{
				layout : 'column',
				items : [{
					columnWidth : .9,
					layout : 'form',
					items : [{
								name : "cusRecordId",
								id:'cusRecordId',
								xtype : "hidden"
							},{
								name : "userCode",
								id:'userCode',
								xtype : "hidden"
							},{
								name : "ts",
								id:'tsId',
								xtype : "hidden"
							},{
								xtype:'combo',
								triggerAction : 'all',
								store : cusRecordStore,
								pageSize : pageSize,
								allowBlank : false,
								emptyText : "请选择客户名称",
								forceSelection : true,
								queryParam : 'filter_LIKES_cusName',
								selectOnFocus:true,
								resizable:true,
								fieldLabel : '客户名称<font style="color:red;">*</font>',
								minChars : 0,
								mode : "remote",//从服务器端加载值
								valueField : 'recordId',//value值，与fields对应
								displayField : 'cusName',//显示值，与fields对应
								id:'cusNameId',
								anchor : '95%',
								enableKeyEvents:true,
								listeners : {
					 				select:function(combo, e){
					                     Ext.getCmp('cusRecordId').setValue(combo.getValue());
					 				}
					 			}
							},{
								xtype:'combo',
								triggerAction : 'all',
								store : cusServiceStore,
								pageSize : pageSize,
								allowBlank : false,
								emptyText : "请选择客服员名称",
								queryParam : 'filter_LIKES_userName',
								forceSelection : true,
								selectOnFocus:true,
								resizable:true,
								fieldLabel : '客服员名称<font style="color:red;">*</font>',
								minChars : 0,
								mode : "remote",//从服务器端加载值
								valueField : 'userCode',//value值，与fields对应
								displayField : 'cusServiceName',//显示值，与fields对应
								id:'cusServiceNameId',
								anchor : '95%',
								enableKeyEvents:true,
								listeners : {
					 				select:function(combo, e){
					                    Ext.getCmp('userCode').setValue(combo.getValue());
					 				}
					 			}
							}
							]
				}]
						
			}]
			});
									
		basSpecialTrainLineTitle='添加指派客服员信息';
		if(cid!=null){
			basSpecialTrainLineTitle='复制指派客服员信息';
			
			Ext.Ajax.request({
				url : sysPath+ "/"+gridSearchUrl,
				params:{
    				filter_t_cusRecordId:cid,
    				filter_t_userCode:vuserCode,
					limit : pageSize},
				success : function(resp) {
					var text=Ext.decode(resp.responseText).resultMap[0];
					Ext.getCmp("cusRecordId").setValue(text.CUS_RECORD_ID);
					Ext.getCmp("userCode").setValue(text.USER_CODE);
					Ext.getCmp("tsId").setValue(text.TS);
					Ext.getCmp("cusNameId").setValue(text.CUS_NAME);
					Ext.getCmp("cusServiceNameId").setValue(text.USER_NAME);
				},
				failure : function(response) {
					Ext.Msg.alert(alertTitle,"加载数据失败");
				}
			});
			/*form.load({
				waitMsg : '正在载入数据...',
    			success : function(_form, action) {
    				alert(action.USER_CODE);
    			},
    			failure : function(_form, action) {
    				Ext.MessageBox.alert(alertTitle, '载入失败');
    			}
			});*/
		}
		var win = new Ext.Window({
		title : basSpecialTrainLineTitle,
		width : 350,
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
					form.getForm().submit({
						url : sysPath
								+ '/'+saveServiceDesignateUrl,
						params:{privilege:privilege,limit : pageSize},
						waitMsg : '正在保存数据...',
						success : function(form, action) {
							 Ext.Msg.alert(alertTitle,
									action.result.msg, function() {
										dataReload();
										win.hide();
									});
						},
						failure : function(form, action) {
							if (action.faliureType == Ext.form.Action.SERVER_INVALID) {
								Ext.Msg.alert(alertTitle, "服务器连接异常，请联系管理员！");
							} else {
								if (action.result.msg) {
									Ext.Msg.alert(alertTitle,
											action.result.msg, function() {
												dataReload();
											});

								}
							}
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
				});
		
		win.show();
 }
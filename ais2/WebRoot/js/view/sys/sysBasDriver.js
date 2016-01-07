//司机管理js
var privilege=22;//权限参数
var gridSearchUrl="driver/driverAction!ralaList.action";
var saveUrl="driver/driverAction!save.action";
var delUrl="driver/driverAction!delete.action";
var departUrl='sys/departAction!ralaList.action';//业务部门查询地址

fields=["id",'userCode','driverName','sex','driverAge','address','cityCode','postalCode','phone',
                    'identityCard','departId','departName','startDate','stopDate','stopFlag','createName','createTime','updateName','updateTime',
                    'ts'];

     	stopFlagStore=new Ext.data.SimpleStore({
			 autoLoad:true, //此处设置为自动加载
  			  data:[[0,'正常'],[1,'离职']],
   			 fields:["stopFlagId","stopFlagName"]
		});
		sexStore=new Ext.data.SimpleStore({
			 autoLoad:true, //此处设置为自动加载
  			  data:[[0,'女'],[1,'男']],
   			 fields:["sexId","sexName"]
		});
		
		departStore = new Ext.data.Store({
        storeId:"departStore",
        baseParams:{privilege:53},
        proxy: new Ext.data.HttpProxy({url:sysPath+'/'+departUrl}),
        reader: new Ext.data.JsonReader({
            root: 'result', totalProperty: 'totalCount'
        }, [
            {name:'departId'},
            {name: 'departName'}
        ])
        });
	 var tbar=[{text:'<b>导出</b>',iconCls:'sort_down',handler:function() {
                	parent.exportExl(driverGrid);
        } },'-',{text:'<b>新增</b>',iconCls:'userAdd',id:'driverAdd',tooltip : '新增司机',handler:function() {
	 			
                	saveDriver(null);
            } },'-',
	 	{text:'<b>修改</b>',iconCls:'userEdit',id:'driverEdit',disabled:true,tooltip : '修改司机信息',handler:function(){
	 	var driver =Ext.getCmp('driverCenter');
				var _records = driver.getSelectionModel().getSelections();
	
					if (_records.length < 1) {
						top.Ext.Msg.alert(alertTitle, "请选择您要修改的行！");
						return false;
					} else if (_records.length > 1) {
						top.Ext.Msg.alert(alertTitle, "一次只能修改一行！");
						return false;
					}
					//alert(_records[0].data.id);
	 	saveDriver(_records);}},'-',
	 	{text:'<b>删除</b>',iconCls:'userDelete',id:'driverDelete',disabled:true,tooltip : '删除司机',handler:function(){
	 		var driver =Ext.getCmp('driverCenter');
			var _records = driver.getSelectionModel().getSelections();
	
					if (_records.length < 1) {
						top.Ext.Msg.alert(alertTitle, "请选择您要删除的行！");
						return false;
					} 

					top.Ext.Msg.confirm(alertTitle, "确定要删除"+_records.length+"条记录吗？", function(
									btnYes) {
								if (btnYes == 'yes' || btnYes == 'ok'
										|| btnYes == true) {
									var ids="";
									for(var i=0;i<_records.length;i++){
									
										 ids=ids+_records[i].data.id+',';
									}
									
									Ext.Ajax.request({
										url : sysPath+'/'
												+ 'driver/driverAction!delete.action',
										params : {
											ids :ids,
											 privilege:privilege,limit : pageSize
										},
										success : function(resp) {
											var respText = Ext.util.JSON
													.decode(resp.responseText);

											top.Ext.Msg.alert(alertTitle,
													  "删除成功!");

											dataReload();
										}
									});
								}
							});
	 	}},'-',{
    			xtype : 'datefield',
    		id : 'startDate',
    		format : 'Y-m-d',
    		emptyText : "开始时间",
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
    	}, '&nbsp;&nbsp;', {
    		xtype : 'datefield',
    		id : 'endDate',
    		format : 'Y-m-d',
    		emptyText : "结束时间",
    		hidden : true,
    		width : 100,
    		disabled : true,
    		anchor : '95%'
    	},'-',
	 	{xtype:'textfield',blankText:'查询数据',id:'searchContent',enableKeyEvents:true,listeners : {
	 		
	 		keyup:function(textField, e){

                     if(e.getKey() == 13){

                     	searchDriver();

                     }
	 		}
	 		}
	 	},
	 	'-',{
    					xtype : "combo",
    					width : 100,
    					triggerAction : 'all',
    					id:'searchSelectBox',
    					model : 'local',
    					hiddenId : 'checkItems',
    					hiddenName : 'checkItems',
    					name : 'checkItemstext',
    					store : [['', '查询全部'], 
    							['LIKES_driverName', '姓名'],
    							['LIKES_userCode','工号'],
    							['LIKES_cityCode','城市'],
    							['LIKES_address','住址'],
    							['LIKES_postalCode','邮编'],
    							['LIKES_phone','电话'],
    							['EQD_startDate', '雇佣日期'],
    							['EQD_createTime', '创建日期']],
    					emptyText : '选择类型',
    					forceSelection : true,
    					listeners : {
    						
    						'select' : function(combo, record, index) {
    							if(combo.getValue()==''){searchDriver();}
    							if (combo.getValue() == 'EQD_startDate') {
    								Ext.getCmp("startDate").enable();
    								Ext.getCmp("startDate").show();

    								Ext.getCmp("endDate").enable();
    								Ext.getCmp("endDate").show();
    								
    								Ext.getCmp("searchContent").disable();
    								Ext.getCmp("searchContent").hide();
    								Ext.getCmp("searchContent").setValue("");
    							}else if(combo.getValue() == 'EQD_createTime') {
    								Ext.getCmp("startDate").enable();
    								Ext.getCmp("startDate").show();

    								Ext.getCmp("endDate").enable();
    								Ext.getCmp("endDate").show();
    								
    							 	Ext.getCmp("searchContent").disable();
    								Ext.getCmp("searchContent").hide();
    								Ext.getCmp("searchContent").setValue("");
    								
    						 	}else{
    					         	
    						  		Ext.getCmp("startDate").hide();
    								Ext.getCmp("startDate").setValue("");

    								
    								Ext.getCmp("endDate").hide();
    								Ext.getCmp("endDate").setValue("");
    								
    						      	Ext.getCmp("searchContent").enable();
    								Ext.getCmp("searchContent").show();
    								
    								if(Ext.getCmp("searchContent").getValue().length>0){
    									searchDriver();
    								}
    							
    							}
    						}
    					}
    					
    				},'-',{text : '<b>搜索</b>',id : 'btn',iconCls : 'btnSearch',
    					handler : searchDriver
    				}			
	 	];
Ext.onReady(function() {

   	dateStore = new Ext.data.Store({
        storeId:"dateStore",
        proxy: new Ext.data.HttpProxy({url : sysPath+ "/"+gridSearchUrl,
						params:{privilege:privilege,limit : pageSize}}),
        reader: new Ext.data.JsonReader({
            root: 'result', totalProperty: 'totalCount'
        }, fields)
    });
   
    var sm = new Ext.grid.CheckboxSelectionModel({});
	  var rowNum = new Ext.grid.RowNumberer({
        header:'序号', width:20
    });
    driverGrid = new Ext.grid.GridPanel({
        renderTo: Ext.getBody(),
        id:'driverCenter',
        height:Ext.lib.Dom.getViewHeight(),		
        width:Ext.lib.Dom.getViewWidth(),
        viewConfig : {
			columnsText : "显示的列",
			sortAscText : "升序",
			sortDescText : "降序",
			forceFit : true
		},
		
        frame:true, 
        loadMask:true,
        sm: sm,
        cm:new Ext.grid.ColumnModel([rowNum,sm,
       			{header: '工号', dataIndex: 'userCode',width:60,sortable : true},
			        {header: '姓名', dataIndex: 'driverName',width:60,sortable : true},
			        {header: '性别', dataIndex: 'sex',width:60,sortable : true
			        			,renderer:function(v){
			        					return v==0?'女':'男';
			        		}
			        },
    				{header: '驾龄', dataIndex: 'driverAge',width:60,sortable : true},
    				{header: '住址', dataIndex: 'address',width:60,sortable : true},
    				{header: '城市', dataIndex: 'cityCode',width:60,sortable : true},
    				{header: '邮编', dataIndex: 'postalCode',width:60,sortable : true},
    				{header: '电话', dataIndex: 'phone',width:60,sortable : true},
    				{header: '驾驶证', dataIndex: 'identityCard',width:100,sortable : true},
    				{header: '登记部门', dataIndex: 'departName',width:100,sortable : true},
    				{header: '状态', dataIndex: 'stopFlag',width:60,sortable : true,
    					renderer:function(v){
			        					return v==0?'正常':'离职';
			        					}
    				},
    				{header: '雇佣日期', dataIndex: 'startDate',width:100,sortable : true},
    				{header: '离职日期', dataIndex: 'stopDate',width:60,sortable : true},
    				{header: '创建人', dataIndex: 'createName',width:60,sortable : true},
    				{header: '创建日期', dataIndex: 'createTime',width:100,sortable : true}
        ]),
        store:dateStore,
      
        tbar: tbar,
        bbar: new Ext.PagingToolbar({
            pageSize: pageSize, 
            privilege:privilege,
            store: dateStore, 
            displayInfo: true,
            displayMsg: '显示第 {0} 条到 {1} 条记录，共 {2} 条', 
            emptyMsg: "没有记录信息显示"
        })
    });
   
	//searchDriver();
	
    driverGrid.on('click', function() {
        selabled();
    });
    driverGrid.on('rowdblclick',function(grid,index,e){
		var _records = driverGrid.getSelectionModel().getSelections();
				
				if (_records.length ==1) {
					saveDriver(_records);
				}
			 	
		});
    });
    
	

function searchDriver() {
		dateStore.proxy = new Ext.data.HttpProxy({
					method : 'POST',
					url : sysPath+'/'+gridSearchUrl,
					params : {
						start : 0,
						privilege:privilege,
						limit : pageSize
					}
					
				});
	 if (Ext.getCmp('searchSelectBox').getValue() == 'EQD_startDate') {
    			 Ext.apply(dateStore.baseParams, {
    						filter_GED_startDate :(!Ext.getCmp("startDate").hidden?(Ext.get("startDate").dom.value=="开始时间"?"":Ext.get("startDate").dom.value):""),
    						filter_LTD_startDate : (!Ext.getCmp("endDate").hidden?(Ext.get("endDate").dom.value=="结束时间"?"":Ext.get("endDate").dom.value):""),
    						checkItems : '',
    						itemsValue : '',
    						privilege:privilege,
							start:0
    					});
    		} else if (Ext.getCmp('searchSelectBox').getValue() == 'EQD_createTime') {
    		 Ext.apply(dateStore.baseParams, {
    						filter_GED_createTime :(!Ext.getCmp("startDate").hidden?(Ext.get("startDate").dom.value=="开始时间"?"":Ext.get("startDate").dom.value):""),
    						filter_LTD_createTime : (!Ext.getCmp("endDate").hidden?(Ext.get("endDate").dom.value=="结束时间"?"":Ext.get("endDate").dom.value):""),
    						checkItems : '',
    						itemsValue : '',
    						privilege:privilege,
							start:0
    					});
    		}else{	
				dateStore.baseParams = {
				checkItems : Ext.get("checkItems").dom.value,
				privilege:privilege,
				start:0,
				itemsValue : Ext.get("searchContent").dom.value
		}
		}

		
		var editbtn = Ext.getCmp('driverEdit');
		var deletebtn = Ext.getCmp('driverDelete');

		editbtn.setDisabled(true);
		deletebtn.setDisabled(true);

		dataReload();
		
	}
function saveDriver(_records) {
					if(_records!=null)
					var did=_records[0].data.id;
					var form = new Ext.form.FormPanel({
								labelAlign : 'left',
								frame : true,
								url:sysPath+'/'+gridSearchUrl,
								baseParams:{
				    				filter_EQL_id:did,
									privilege:privilege,
									limit : pageSize},
								bodyStyle : 'padding:5px 5px 0',
								width : 700,
								reader : new Ext.data.JsonReader({
			            root: 'result', totalProperty: 'totalCount'
			        }, fields),
					labelAlign : "right",
				
						items : [{
											layout : 'column',
											items : [{
														columnWidth : .5,
														layout : 'form',
														items : [{
																	name : "id",
																	xtype : "hidden"
																},{
																	name : "ts",
																	xtype : "hidden"
																},
																 {
																	xtype : 'textfield',
																	labelAlign : 'left',
																	fieldLabel : '工号',
																	name : 'userCode',
																	maxLength:5,
																	anchor : '95%'
																},  {
																	xtype : 'textfield',
																	labelAlign : 'left',
																	fieldLabel : '姓名<font style="color:red;">*</font>',
																	name : 'driverName',
																	maxLength:30,
																	allowBlank : false,
																	blankText : "姓名不能为空！",
																	anchor : '95%'
																},  {
																	xtype : 'combo',
																	triggerAction : 'all',
																	store : sexStore,
																	allowBlank : false,
																	emptyText : "请选性别",
																	forceSelection : true,
																	fieldLabel:'性别<font style="color:red;">*</font>',
																	editable : false,
																	mode : "local",//获取本地的值
																	valueField : 'sexId',//value值，与fields对应
																	displayField : 'sexName',//显示值，与fields对应
																	hiddenName:'sex',
																	anchor : '95%'
																}, 
																 {
																	xtype : 'numberfield',
																	labelAlign : 'left',
																	fieldLabel : '驾龄<font style="color:red;">*</font>',
																	name : 'driverAge',
																	maxLength:2,
																	allowBlank : false,
																	blankText : "驾龄不能为空！",
																	anchor : '95%'
																},{
																	xtype : 'combo',
																	triggerAction : 'all',
																	id:'mycombo',
																	typeAhead : true,
																	queryParam : 'filter_LIKES_departName',
																	store : departStore,
																	pageSize : comboSize,
																	allowBlank : false,
																	emptyText : "请选择部门",
																	forceSelection : true,
																	fieldLabel : '登记部门<font style="color:red;">*</font>',
																	minChars : 0,
																	editable : false,
																	valueField : 'departId',//value值，与fields对应
																	displayField : 'departName',//显示值，与fields对应
																	hiddenName:'departId',
																	anchor : '95%'
																},  {
																	xtype : 'textfield',
																	labelAlign : 'left',
																	fieldLabel : '城市',
																	maxLength:20,
																	name : 'cityCode',
																	anchor : '95%'
																},
																 {
																	xtype : 'numberfield',
																	labelAlign : 'left',
																	fieldLabel : '邮编',
																	maxLength:20,
																	nanText:'请输入数字',
																	name : 'postalCode',
																	anchor : '95%'
																	
																},  {
																	
																	xtype:'numberfield',
																	labelAlign : 'left',
																	editable : false,
																	allowBlank : false,
																	maxLength:20,
																	fieldLabel : '电话<font style="color:red;">*</font>',
																	nanText:'请输入数字',
																	blankText : "电话不能为空！",
																	name : 'phone',
																	anchor : '95%'
																
																}]

													}, 
													{
														columnWidth : .5,
														layout : 'form',
														items : [{
																	xtype : 'numberfield',
																	labelAlign : 'left',
																	fieldLabel : '驾驶证号码<font style="color:red;">*</font>',
																	blankText : "驾驶证号码不能为空！",
																	allowBlank : false,
																	maxLength:20,
																	nanText:'请输入数字',
																	name : 'identityCard',
																	anchor : '95%'
																}, {
																	labelAlign : 'left',
																	fieldLabel : '雇佣日期<font style="color:red;">*</font>',
																	format:'Y-m-d',
																	xtype:'datefield',
																	blankText : "雇佣日期不能为空！",
																	editable : false,
																	allowBlank : false,
																	name : 'startDate',
																	anchor : '95%'
																}, {
																	labelAlign : 'left',
																	fieldLabel : '离职日期',
																	xtype:'datefield',
																	format:'Y-m-d',
																	editable : false,
																	name : 'stopDate',
																	anchor : '95%'
																},
							
																{
																	xtype : 'combo',
																	triggerAction : 'all',
																	store : stopFlagStore,
																	allowBlank : false,
																	emptyText : "请选择状态",
																	forceSelection : true,
																	fieldLabel:'状态<font style="color:red;">*</font>',
																	minChars : 0,
																	editable : false,
																	mode : "local",//获取本地的值
																	valueField : 'stopFlagId',//value值，与fields对应
																	displayField : 'stopFlagName',//显示值，与fields对应
																	hiddenName:'stopFlag',
																	anchor : '95%'
																		
																},  {
																	xtype : 'textfield',
																	labelAlign : 'left',
																	fieldLabel : '住址',
																	name : 'address',
																	maxLength:200,
																	anchor : '95%'
																},{
																	xtype : 'textfield',
																	labelAlign : 'left',
																	fieldLabel : '创建人',
																	disabled :true,
																	maxLength:30,
																	name : 'createName',
																	anchor : '95%'
																},{
																	labelAlign : 'left',
																	fieldLabel : '创建日期',
																	xtype:'datefield',
																	disabled :true,
																	format:'Y-m-d',
																	editable : false,
																	name : 'createTime',
																	anchor : '95%'
																}]
													}]
													
										}]
							});
		
		driverTitle='添加司机信息';
		if(did!=null){
			departStore.load();
			driverTitle='修改司机信息';
			
			form.load({
						waitMsg : '正在载入数据...',
		    			success : function(_form, action) {
				   			//form.load();,_records[0].data.departName
				   			Ext.getCmp('mycombo').setValue(_records[0].data.departId);
				   			Ext.getCmp('mycombo').setRawValue(_records[0].data.departName);
				   			
		    			},
		    			failure : function(_form, action) {
		    				Ext.MessageBox.alert(alertTitle, '载入失败');
		    			}
					});
		
		}

		var win = new Ext.Window({
		title : driverTitle,
		width : 700,
		closeAction : 'hide',
		plain : true,
		modal : true,
		items : form,
		buttonAlign : "center",
		buttons : [{
			text : "保存",
			iconCls : 'groupSave',
			handler : function() {
				if (form.getForm().isValid()) {
					this.disabled = true;
					
					form.getForm().submit({
						url : sysPath
								+ '/'+saveUrl,
								params:{privilege:privilege,limit : pageSize},
						waitMsg : '正在保存数据...',
						success : function(form, action) {
							win.hide(), Ext.Msg.alert(alertTitle,
									action.result.msg, function() {
										dataReload();
									});
						},
						failure : function(form, action) {
							if (action.faliureType == Ext.form.Action.SERVER_INVALID) {
								Ext.Msg.alert(alertTitle, "服务器连接异常，请联系管理员！");
							} else {
								if (action.result.msg) {
									win.hide();
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
		
			text : "重置",
			iconCls : 'refresh',
			handler : function() {
				
				departStore.load();
				if(did==null){
					
					form.getForm().reset();
				
				}
				if(did!=null){
					driverTitle='修改司机信息';
					form.load({
						waitMsg : '正在载入数据...',
		    			success : function(_form, action) {
				   			//form.load();,_records[0].data.departName
				   			Ext.getCmp('mycombo').setValue(_records[0].data.departId);
				   			Ext.getCmp('mycombo').setRawValue(_records[0].data.departName);
				   			
		    			},
		    			failure : function(_form, action) {
		    				Ext.MessageBox.alert(alertTitle, '载入失败');
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
 function dataReload(){
			dateStore.reload({
			params : {
				start : 0,
				privilege:privilege,
				limit : pageSize
			}
		});
		selabled();
 }
function selabled(){
	var _record = driverGrid.getSelectionModel().getSelections();
        var updatebtn = Ext.getCmp('driverEdit');
        var deletebtn = Ext.getCmp('driverDelete');
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
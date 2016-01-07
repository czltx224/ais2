//增值服务费管理js
var privilege=25;//权限参数
var gridSearchUrl="basvalueaddfee/basValueAddFeeAction!ralaList.action";
var saveUrl="basvalueaddfee/basValueAddFeeAction!save.action";
var delUrl="basvalueaddfee/basValueAddFeeAction!delete.action";
var dictionaryUrl='sys/dictionaryAction!ralaList.action';

var  fields=[{name:"id",mapping:'id'},
     		{name:"feeName",mapping:'feeName'},
     		{name:'feeSubject',mapping:'feeSubject'},
     		{name:'feeCount',mapping:'feeCount'},
     		{name:'autoFee',mapping:'autoFee'},
     		{name:'createName',mapping:'createName'},
     		{name:'createTime',mapping:'createTime'},
     		{name:'updateName',mapping:'updateName'},
     		{name:'updateTime',mapping:'updateTime'},
     		{name:'ts',mapping:'ts'},
     		{name:'payMan',mapping:'payMan'},
     		{name:'payRule',mapping:'payRule'},
     		{name:'feeLink',mapping:'feeLink'}];
    autoFeeStore=new Ext.data.SimpleStore({
			 autoLoad:true, //此处设置为自动加载
  			  data:[['1','自动收费'],['0','手动收费']],
   			 fields:["autoFeeId","autoFeeName"]
		});
	//增值服务类型
	 var addFeeStore= new Ext.data.Store({ 
		storeId:"addFeeStore",
		baseParams:{filter_EQL_basDictionaryId:145,privilege:16},
		proxy:new Ext.data.HttpProxy({url:sysPath+"/"+dictionaryUrl}),
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
    	{name:'typeCode',mapping:'typeCode'},
    	{name:'typeName',mapping:'typeName'}
    	])
	});
	
	payManStore	= new Ext.data.Store({ 
			storeId:"payManStore",
			baseParams:{filter_EQL_basDictionaryId:8,privilege:16},
			proxy:new Ext.data.HttpProxy({url:sysPath+"/"+dictionaryUrl}),
			reader:new Ext.data.JsonReader({
	                    root:'result',
	                    totalProperty:'totalCount'
	        },[
        	{name:'payManId',mapping:'typeCode'},
        	{name:'payManName',mapping:'typeName'}
        	])
		});
	feeLinkStore = new Ext.data.Store({ 
			storeId:"feeLinkStore",
			baseParams:{filter_EQL_basDictionaryId:10,privilege:16},
			proxy:new Ext.data.HttpProxy({url:sysPath+"/"+dictionaryUrl}),
			reader:new Ext.data.JsonReader({
	                    root:'result',
	                    totalProperty:'totalCount'
	        },[
        	{name:'feeLinkId',mapping:'typeCode'},
        	{name:'feeLinkName',mapping:'typeName'}
        	])
		});
	feeSubjectStore = new Ext.data.Store({ 
			storeId:"feeSubjectStore",
			baseParams:{filter_EQL_basDictionaryId:9,privilege:16},
			proxy:new Ext.data.HttpProxy({url:sysPath+"/"+dictionaryUrl}),
			reader:new Ext.data.JsonReader({
	                    root:'result',
	                    totalProperty:'totalCount'
	        },[
        	{name:'feeSubjectId',mapping:'typeCode'},
        	{name:'feeSubjectName',mapping:'typeName'}
        	])
		});
	
  	var tbar=[{text:'<b>导出</b>',iconCls:'sort_down',handler:function() {
                	parent.exportExl(valueAddFeeGrid);
        } },'-',{text:'<b>新增</b>',iconCls:'userAdd',id:'basValueAddFeeAdd',tooltip : '新增增值服务费',handler:function() {
                	saveValueAddFee(null);
            } },'-',
	 	{text:'<b>修改</b>',iconCls:'userEdit',id:'basValueAddFeeEdit',disabled:true,tooltip : '修改增值服务费',handler:function(){
	 	var valueAddFee =Ext.getCmp('valueAddFeeCenter');
	var _records = valueAddFee.getSelectionModel().getSelections();
	
					if (_records.length < 1) {
						Ext.Msg.alert(alertTitle, "请选择您要修改的行！");
						return false;
					} else if (_records.length > 1) {
						Ext.Msg.alert(alertTitle, "一次只能修改一行！");
						return false;
					}
	 	saveValueAddFee(_records[0].data.id);}},'-',
	 	{text:'<b>删除</b>',iconCls:'userDelete',id:'basValueAddFeeDelete',disabled:true,tooltip : '删除增值服务费',handler:function(){
	 		var valueAddFee =Ext.getCmp('valueAddFeeCenter');
			var _records = valueAddFee.getSelectionModel().getSelections();
	
					if (_records.length < 1) {
						Ext.Msg.alert(alertTitle, "请选择您要删除的行！");
						return false;
					} 

					Ext.Msg.confirm(alertTitle, "确定要删除"+_records.length+"条记录吗？", function(
									btnYes) {
								if (btnYes == 'yes' || btnYes == 'ok'
										|| btnYes == true) {
									var ids="";
									for(var i=0;i<_records.length;i++){
									
										 ids=ids+_records[i].data.id+',';
									}
									Ext.Ajax.request({
										url : sysPath+'/'
												+ delUrl,
										params : {
											ids : ids,
										    privilege:privilege,
										    limit : pageSize
										},
										success : function(resp) {
											var respText = Ext.util.JSON
													.decode(resp.responseText);

											Ext.Msg.alert(alertTitle,
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
	 	{xtype:'textfield',blankText:'查询数据',id:'searchContent', enableKeyEvents:true,listeners : {
	 		
	 		keyup:function(textField, e){

                     if(e.getKey() == 13){

                     	searchValueAddFee();

                     }
	 		}
	 	}},
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
    							['LIKES_feeName', '增值服务类型'],
    							['LIKES_feeSubject','会计科目'],
    							['LIKES_feeCount','收费金额'],
    							['LIKES_createName','创建人'],
    							['LIKES_updateName','修改人'],
    							['LIKES_feeLink','收费环节'],
    							['EQD_createTime', '创建时间'],
    						    ['EQD_updateTime', '修改时间']],
    							
    					emptyText : '选择类型',
    					editable : false,
    					forceSelection : true,
    					listeners : {
    						
    						'select' : function(combo, record, index) {
    							if(combo.getValue()==''){searchValueAddFee();}
    							if (combo.getValue() == 'EQD_createTime') {
    								Ext.getCmp("startDate").enable();
    								Ext.getCmp("startDate").show();

    								Ext.getCmp("endDate").enable();
    								Ext.getCmp("endDate").show();
    								
    								Ext.getCmp("searchContent").disable();
    								Ext.getCmp("searchContent").hide();
    								Ext.getCmp("searchContent").setValue("");
    							}else if(combo.getValue() == 'EQD_updateTime') {
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
    									searchValueAddFee();
    								}
    							
    							}
    						}
    					}
    					
    				},'-',{text : '<b>搜索</b>',id : 'btn',iconCls : 'btnSearch',
    					handler : searchValueAddFee
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
    valueAddFeeGrid = new Ext.grid.GridPanel({
        renderTo: Ext.getBody(),
        id:'valueAddFeeCenter',
        height:Ext.lib.Dom.getViewHeight(),		
        width:Ext.lib.Dom.getViewWidth(),
        viewConfig : {
			columnsText : "显示的列",
			sortAscText : "升序",
			sortDescText : "降序",
			forceFit : true
		},
		autoScroll:false, autoExpandColumn:1,
        frame:true, 
        loadMask:true,
        sm:sm,
        cm:new Ext.grid.ColumnModel([rowNum,sm,
        {header: '增值服务类型', dataIndex: 'feeName',sortable : true},
       			{header: '会计科目', dataIndex: 'feeSubject',sortable : true},
        		{header: '收费金额', dataIndex: 'feeCount',sortable : true},
 				{header: '收费方式', dataIndex: 'autoFee',sortable : true
 					,renderer:function(v){
			        					return v==0?'手动收费':'自动收费';
			        		}
 				},
 				{header: '付款方', dataIndex: 'payMan',sortable : true},
 				{header: '收费规则', dataIndex: 'payRule',sortable : true},
 				{header: '收费环节', dataIndex: 'feeLink',sortable : true},
 				{header: '创建人', dataIndex: 'createName',sortable : true},
 				{header: '创建时间', dataIndex: 'createTime',sortable : true},
 				{header: '修改人', dataIndex: 'updateName',sortable : true},
 				{header: '修改时间', dataIndex: 'updateTime',sortable : true}
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
	//searchValueAddFee();
	
    valueAddFeeGrid.on('click', function() {
       selabled();
     
    });
    valueAddFeeGrid.on('rowdblclick',function(grid,index,e){
		var _records = valueAddFeeGrid.getSelectionModel().getSelections();
				
				if (_records.length ==1) {
					saveValueAddFee(_records[0].data.id);
				}
			 	
		});
    });
    
	
   function searchValueAddFee() {
		dateStore.proxy = new Ext.data.HttpProxy({
					method : 'POST',
					url : sysPath
								+ "/"+gridSearchUrl,
						params:{privilege:privilege,limit : pageSize}
				});
	 if (Ext.getCmp('searchSelectBox').getValue() == 'EQD_createTime') {
    			 Ext.apply(dateStore.baseParams, {
    						filter_GED_createTime:(!Ext.getCmp("startDate").hidden?(Ext.get("startDate").dom.value=="开始时间"?"":Ext.get("startDate").dom.value):""),
    						filter_LTD_createTime : (!Ext.getCmp("endDate").hidden?(Ext.get("endDate").dom.value=="结束时间"?"":Ext.get("endDate").dom.value):""),
    						checkItems : '',
    						itemsValue : '',
    						privilege:privilege,
							start:0
    					});
    		} else if (Ext.getCmp('searchSelectBox').getValue() == 'EQD_updateTime') {
    			 Ext.apply(dateStore.baseParams, {
    						filter_GED_updateTime:(!Ext.getCmp("startDate").hidden?(Ext.get("startDate").dom.value=="开始时间"?"":Ext.get("startDate").dom.value):""),
    						filter_LTD_updateTime : (!Ext.getCmp("endDate").hidden?(Ext.get("endDate").dom.value=="结束时间"?"":Ext.get("endDate").dom.value):""),
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
		
		var editbtn = Ext.getCmp('basValueAddFeeEdit');
		var deletebtn = Ext.getCmp('basValueAddFeeDelete');

		editbtn.setDisabled(true);
		deletebtn.setDisabled(true);

		dataReload();
		
	}
function saveValueAddFee(cid) {
			var form = new Ext.form.FormPanel({
						labelAlign : 'left',
						frame : true,
						bodyStyle : 'padding:5px 5px 0',
						width : 500,
						reader : new Ext.data.JsonReader({
		           root: 'result', totalProperty: 'totalCount'
		       }, fields),
			labelAlign : "right",
		
				items : [{layout : 'column',
									items : [{
												columnWidth : .5,
												layout : 'form',
												items : [{
															name : "id",
															xtype : "hidden"
														},{
															name : "ts",
															xtype : "hidden"
														}, {
															xtype : 'combo',
															labelAlign : 'left',
															fieldLabel : '增值服务类型<font style="color:red;">*</font>',
															name : 'feeName',
															queryParam : 'filter_LIKES_typeName',
															store:addFeeStore,
															pageSize : comboSize,
															editable:true,
															triggerAction : 'all',
															typeAhead : true,
															resizable:true,
															minChars : 0,
															forceSelection : true,
															displayField : 'typeName',//显示值，与fields对应
															valueField : 'typeName',//value值，与fields对应
															allowBlank : false,
															blankText : "增值服务类型不能为空！",
															anchor : '95%'
														},  {
														
															xtype : 'combo',
															triggerAction : 'all',
															store : feeSubjectStore,
															forceSelection : true,
															mode : "remote",//获取本地的值
															displayField : 'feeSubjectName',//显示值，与fields对应
															valueField : 'feeSubjectId',//value值，与fields对应
															name : 'feeSubject',
															fieldLabel : '会计科目',
															anchor : '95%'
														},  {
															xtype : 'numberfield',
															labelAlign : 'left',
															fieldLabel : '收费金额(元)<font style="color:red;">*</font>',
															name : 'feeCount',
															emptyText:'必填项',
															maxLength:10,
															allowBlank : false,
															blankText : "收费金额不能为空！",
															anchor : '95%'
														}, {
															xtype : 'combo',
															triggerAction : 'all',
															store : autoFeeStore,
															allowBlank : false,
															emptyText : '请选择收费方式',
															emptyText:'必填选',
															forceSelection : true,
															fieldLabel:'收费方式<font style="color:red;">*</font>',
															mode : "local",//获取本地的值
															displayField : 'autoFeeName',//显示值，与fields对应
															valueField : 'autoFeeId',//value值，与fields对应
															hiddenName : 'autoFee',
															anchor : '95%'
														},{
															xtype : 'combo',
															triggerAction : 'all',
															store : payManStore,
															allowBlank : false,
															emptyText : '请选择付款方',
															forceSelection : true,
															emptyText:'必填选',
															fieldLabel:'付款方<font style="color:red;">*</font>',
															editable : false,
															mode : "remote",//获取本地的值
															displayField : 'payManName',//显示值，与fields对应
															valueField : 'payManId',//value值，与fields对应
															name : 'payMan',
															anchor : '95%'
														}]
		
											}, {
												columnWidth : .5,
												layout : 'form',
												items : [{
												
															xtype : 'combo',
															triggerAction : 'all',
															store : feeLinkStore,
															emptyText : '请选择收费环节',
															forceSelection : true,
															mode : "remote",//获取本地的值
															displayField : 'feeLinkName',//显示值，与fields对应
															valueField : 'feeLinkId',//value值，与fields对应
															name : 'feeLink',
															fieldLabel : '收费环节',
															anchor : '95%'
														},{
															xtype : 'textfield',
															labelAlign : 'left',
															fieldLabel : '创建人',
															disabled :true,
															maxLength:20,
															name : 'createName',
															anchor : '95%'
														},{
															labelAlign : 'left',
															fieldLabel : '创建日期',
															xtype:'datefield',
															format:'Y-m-d',
															disabled :true,
															name : 'createTime',
															anchor : '95%'
														},{
															xtype : 'textfield',
															labelAlign : 'left',
															fieldLabel : '修改人',
															maxLength:20,
															disabled :true,
															name : 'updateName',
															anchor : '95%'
														},{
															labelAlign : 'left',
															fieldLabel : '修改日期',
															xtype:'datefield',
															format:'Y-m-d',
															disabled :true,
															name : 'updateTime',
															anchor : '95%'
														}]
											}]
											
								},{
									labelAlign : 'top',
									xtype : 'textarea',
									name : 'payRule',
									fieldLabel : '收费规则',
									height : 100,
									width:'95%'
								}]
								});
							
		valueAddFeeTitle='添加增值服务费信息';
		if(cid!=null){
			valueAddFeeTitle='修改增值服务费信息';
			form.load({
			url : sysPath
					+ "/"+gridSearchUrl,
			params:{filter_EQL_id:cid,privilege:privilege,limit : pageSize}
		});
		}
		
		var win = new Ext.Window({
		title : valueAddFeeTitle,
		width : 500,
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
					
					
					if(cid==null){
						
						form.getForm().reset();
					
					}
					if(cid!=null){
						valueAddFeeTitle='修改增值服务费信息';
						form.load({
						url : sysPath
								+ "/"+gridSearchUrl,
						params:{filter_EQL_id:cid,privilege:privilege,limit : pageSize}
					})		
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
 	 var _record = valueAddFeeGrid.getSelectionModel().getSelections();
 	
        var updatebtn = Ext.getCmp('basValueAddFeeEdit');
        var deletebtn = Ext.getCmp('basValueAddFeeDelete');
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

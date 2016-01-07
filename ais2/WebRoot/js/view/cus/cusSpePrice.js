var privilege=152;
var operTitle;
var cId;
var cName;
var dictionaryUrl='sys/dictionaryAction!ralaList.action';//数据字典地址
var  spefields=[{name:"id",mapping:'id'},
     		{name:"specialTrainLineId",mapping:'specialTrainLineId'},
     		{name:"specialTrainLineName",mapping:'specialTrainLineName'},
     		{name:'van',mapping:'van'},
     		{name:'goldCupCar',mapping:'goldCupCar'},
     		{name:'twoTonCar',mapping:'twoTonCar'},
     		{name:'threeTonCar',mapping:'threeTonCar'},
     		{name:'fiveTonCar',mapping:'fiveTonCar'},
     		{name:'chillCar',mapping:'chillCar'},
     		{name:'status',mapping:'status'},
     		{name:'createName',mapping:'createName'},
     		{name:'createTime',mapping:'createTime'},
     		{name:'updateName',mapping:'updateName'},
     		{name:'updateTime',mapping:'updateTime'},
     		{name:'ts',mapping:'ts'},
     		{name:'departId',mapping:'departId'},
     		{name:'roadType',mapping:'roadType'}];
var gridSearchUrl="fi/cqStCorporateRateAction!ralaList.action";//查询地址
var dictionaryUrl='sys/dictionaryAction!ralaList.action';//数据字典地址
var authorityDepartUrl='sys/basRightDepartAction!ralaList.action';//权限部门查询地址
Ext.onReady(function() {
    var datespeStore = new Ext.data.Store({
        storeId:"datespeStore",
        proxy: new Ext.data.HttpProxy({url : sysPath+ "/fi/basStSpecialTrainRateAction!ralaList.action",
		baseParams:{privilege:93,limit : pageSize}}),
        reader: new Ext.data.JsonReader({
            root: 'result', totalProperty: 'totalCount'
        }, spefields)
    });
//专车线路 specialTrainLineId
     	var specialTrainLineStore= new Ext.data.Store({ 
			storeId:"specialTrainLineStore",
			baseParams:{privilege:94},
			proxy:new Ext.data.HttpProxy({url:sysPath+"/fi/basSpecialTrainLineAction!list.action"}),
			reader:new Ext.data.JsonReader({
	                    root:'result',
	                    totalProperty:'totalCount'
	        },[
        	{name:'specialTrainLineId',mapping:'id'},
        	{name:'specialTrainLineName',mapping:'lineName'}
        	])
		});
		var cusStore=new Ext.data.Store({
		storeId:"cusStore",
		baseParams:{privilege:61,filter_EQS_custprop:'发货代理'},
		proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/customerAction!ralaList.action"}),
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
        	{name:'id'},
        	{name:'cusName'}
        	])
		});
		//路型
     	var roadTypeStore	= new Ext.data.Store({ 
			storeId:"roadTypeStore",
			baseParams:{filter_EQL_basDictionaryId:22,privilege:16},
			proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/dictionaryAction!ralaList.action"}),
			reader:new Ext.data.JsonReader({
	                    root:'result',
	                    totalProperty:'totalCount'
	        },[
        	{name:'roadTypeId',mapping:'typeCode'},
        	{name:'roadTypeName',mapping:'typeName'}
        	])
		});
    //start
    var sm = new Ext.grid.CheckboxSelectionModel({});
    var rowNum = new Ext.grid.RowNumberer({
        header:'序号', width:35, sortable:true
    });
    
    var menuGrid = new Ext.grid.GridPanel({
        renderTo:'showView',
    	id : 'basspestgrid',
    	height : Ext.lib.Dom.getViewHeight(),
    	width : Ext.lib.Dom.getViewWidth()-1,
    	frame : false,
    	loadMask : true,
    	stripeRows : true,
    	viewConfig:{
    		scrollOffset: 0,
    		autoScroll : true
    	},
        sm:sm,
        store:datespeStore,
        cm:new Ext.grid.ColumnModel([rowNum,sm,
            {header: '序号ID', dataIndex: 'id',sortable:true,hidden:true},
					       			{header: '序号ID', dataIndex: 'id',sortable:true,hidden:true},
					 				{header: '线路', dataIndex: 'specialTrainLineName',sortable : true},
					       			{header: '面包车', dataIndex: 'van',sortable : true},
					        		{header: '金杯车', dataIndex: 'goldCupCar',sortable : true},
					 				{header: '2吨车', dataIndex: 'twoTonCar',sortable : true},
					 				{header: '3吨车', dataIndex: 'threeTonCar',sortable : true},
					 				{header: '5吨车', dataIndex: 'fiveTonCar',sortable : true},
					 				{header: '冷藏车', dataIndex: 'chillCar',sortable : true},
					 				{header: '路型', dataIndex: 'roadType',sortable : true},
					 				{header: '状态', dataIndex: 'status',sortable : true
					 						,renderer:function(v){
					 									return v==1?'未审核':'审核';
								        		}
					 				}
					        	]),
                                 tbar: {
                                    xtype: 'toolbar',
                                    items: [
                                        {
                                            xtype: 'button',
                                            id:'discountspebtn',
                                            text: '<b>打折生成协议价</b>',
                                            disabled:true,
                                            handler:function(){
                                            	var record=Ext.getCmp('basspestgrid').getSelectionModel().getSelections();
                                            	discoutnSpe(record);
                                            }
                                        },
                                        {
                                            xtype: 'tbseparator'
                                        },
                                        {
                                            xtype: 'button',
                                            text: '<b>导出</b>'
                                        },{
                                            xtype: 'tbseparator'
                                        },{
                                            xtype : 'combo',
											triggerAction : 'all',
											width : 120,
											typeAhead : true,
											queryParam : 'filter_LIKES_lineName',
											pageSize : pageSize,
											forceSelection : true,
											resizable:true,
											minChars : 0,
											store : specialTrainLineStore,
											mode:'remote',
											fieldLabel:'专车线路',
											valueField : 'specialTrainLineId',//value值，与fields对应
											displayField : 'specialTrainLineName',//显示值，与fields对应
											hiddenName:'specialTrainLineId',
											id:'searchspecialTrainLine',
											enableKeyEvents:true,listeners : {
												keyup:function(textField, e){
											             if(e.getKey() == 13){
											             	searchspecialTrainRate();
											             }
												}
											
											}
                                        },{
                                            xtype: 'tbseparator'
                                        },{
                                            xtype: 'button',
                                            text:'<b>搜索</b>',
                                            iconCls : 'btnSearch',
                                            handler:function(){
                                            	searchspecialTrainRate();
                                            }
                                        }
                                    ]
                                },
                                bbar: new Ext.PagingToolbar({
						            pageSize: pageSize, 
						            privilege:90,
						            store: datespeStore, 
						            displayInfo: true,
						            displayMsg: '显示第 {0} 条到 {1} 条记录，共 {2} 条', 
						            emptyMsg: "没有记录信息显示"
						        }),
						        listeners: {rowclick:function(grid,rowindex,e){
				                    	var record=Ext.getCmp('basspestgrid').getSelectionModel().getSelections();
				                    	if(record.length==1){
				                    		Ext.getCmp('discountspebtn').setDisabled(false);
				                    	}
				                    }
			                	}
    });
     function searchspecialTrainRate() {
		datespeStore.proxy = new Ext.data.HttpProxy({
					method : 'POST',
					url : sysPath
								+ "/fi/basStSpecialTrainRateAction!ralaList.action"
		});
		var searchspecialTrainLine=Ext.getCmp('searchspecialTrainLine').getValue();
		datespeStore.reload({
			params : {
				filter_EQL_specialTrainLineId:searchspecialTrainLine,
				filter_NEL_status:0,
				filter_EQL_departId:bussDepart,
				privilege:93,
				limit : pageSize
			}
		});
} 
   menuGrid.render();
    function discoutnSpe(_records){
		var form = new Ext.form.FormPanel({
			labelAlign : 'right',
			frame : true,
			bodyStyle : 'padding:5px 5px 0',
			width : 400,
			items:[{
				xtype:'numberfield',
				name:'rebate',
				width:150,
				id:'rebate',
				fieldLabel:'折扣<font style="color:red;">*</font>'
			},{
				xtype : 'combo',
            	forceSelection : true,
            	pageSize:pageSize,
            	forceSelection : true,
            	minChars:0,
            	queryParam :'filter_LIKES_cusName',
            	triggerAction :'all',
            	id:'cuscombo',
    			model : 'local',
    			hiddenName : 'cusId',
            	store: cusStore,
            	valueField:'id',
            	displayField:'cusName',
    			width : 150,
    			fieldLabel:'代理公司<font style="color:red;">*</font>',
    			allowBlank:false,
    			blankText:'代理公司不能为空！'
				
			}]
		});
		if(parent.cusId==null){
			Ext.getCmp('cuscombo').setDisabled(false);
		}else{
			Ext.getCmp('cuscombo').setValue(parent.cusName);
			Ext.getCmp('cuscombo').setDisabled(true);
		}
		var win = new Ext.Window({
			title : '打折生成协议价',
			width : 400,
			closeAction : 'hide',
			items : form,
			buttonAlign : "center",
			buttons : [{
				text : "保存",
				iconCls : 'groupSave',
				handler : function() {
					if (form.getForm().isValid()) {
						if(parent.cusId==null){
								cId=Ext.getCmp('cuscombo').getValue();
								cName=Ext.get('cuscombo').dom.value;
						}else{
								cId=parent.cusId;
								cName=parent.cusName;
						}
						var ids="";
						for(var i=0;i<_records.length;i++){
							ids=ids+_records[i].data.id+",";
						}
						form.getForm().submit({
							url : sysPath
									+ '/fi/stSpecialTrainRateAction!discountSpe.action',
							params:{
								ids:ids,
								cusId:cId,
								cusName:cName
								},
							waitMsg : '正在保存数据...',
							success : function(form, action) {
								 Ext.Msg.alert(alertTitle,
										action.result.msg);
										win.hide();
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
				}
			}
			, {
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
	function menuStoreReload(){
		menuStore.reload({
			params:{
				start : 0,
				privilege:privilege,
				limit : pageSize
			}
		});
	}
    //end
});
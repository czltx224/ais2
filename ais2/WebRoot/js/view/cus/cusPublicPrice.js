var privilege=152;
var operTitle;
var cId;
var cName;
var dictionaryUrl='sys/dictionaryAction!ralaList.action';//数据字典地址
var  fields=[{name:"id",mapping:'id'},
     		{name:'startDate',mapping:'startDate'},
     		{name:'endDate',mapping:'endDate'},
     		{name:'trafficMode',mapping:'trafficMode'},
     		{name:'distributionMode',mapping:'distributionMode'},
     		{name:'takeMode',mapping:'takeMode'},
     		{name:'lowPrice',mapping:'lowPrice'},
     		{name:'stage1Rate',mapping:'stage1Rate'},
     		{name:'stage2Rate',mapping:'stage2Rate'},
     		{name:'stage3Rate',mapping:'stage3Rate'},
     		{name:'status',mapping:'status'},
     		{name:'createName',mapping:'createName'},
     		{name:'createTime',mapping:'createTime'},
     		{name:'updateName',mapping:'updateName'},
     		{name:'updateTime',mapping:'updateTime'},
     		{name:'ts',mapping:'ts'},
     		{name:'departId',mapping:'departId'},
     		{name:'addressType',mapping:'addressType'},
     		{name:'valuationType',mapping:'valuationType'},
     		{name:'startAddr',mapping:'startAddr'},
     		{name:'endAddr',mapping:'endAddr'}];    
var gridSearchUrl="fi/cqStCorporateRateAction!ralaList.action";//查询地址
var dictionaryUrl='sys/dictionaryAction!ralaList.action';//数据字典地址
var authorityDepartUrl='sys/basRightDepartAction!ralaList.action';//权限部门查询地址
Ext.onReady(function() {
    var dateStore = new Ext.data.Store({
        storeId:"dateStore",
        proxy: new Ext.data.HttpProxy({url : sysPath+ "/"+gridSearchUrl,
						params:{privilege:90,limit : pageSize}}),
        reader: new Ext.data.JsonReader({
            root: 'result', totalProperty: 'totalCount'
        }, fields)
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
    //计费方式20
	var valuationTypeStore	= new Ext.data.Store({ 
			storeId:"valuationTypeStore",
			baseParams:{filter_EQL_basDictionaryId:20,privilege:16},
			proxy:new Ext.data.HttpProxy({url:sysPath+"/"+dictionaryUrl}),
			reader:new Ext.data.JsonReader({
	                    root:'result',
	                    totalProperty:'totalCount'
	        },[
        	{name:'valuationTypeId',mapping:'typeCode'},
        	{name:'valuationTypeName',mapping:'typeName'}
        	])
	});
    //配送方式4
		var distributionModeStore	= new Ext.data.Store({ 
			storeId:"distributionModeStore",
			baseParams:{filter_EQL_basDictionaryId:4,privilege:16},
			proxy:new Ext.data.HttpProxy({url:sysPath+"/"+dictionaryUrl}),
			reader:new Ext.data.JsonReader({
	                    root:'result',
	                    totalProperty:'totalCount'
	        },[
        	{name:'distributionModeId',mapping:'typeCode'},
        	{name:'distributionModeName',mapping:'typeName'}
        	])
		});
		
		 //提货方式14
		var takeModeStore	= new Ext.data.Store({ 
			storeId:"takeModeStore",
			baseParams:{filter_EQL_basDictionaryId:14,privilege:16},
			proxy:new Ext.data.HttpProxy({url:sysPath+"/"+dictionaryUrl}),
			reader:new Ext.data.JsonReader({
	                    root:'result',
	                    totalProperty:'totalCount'
	        },[
        	{name:'takeModeId',mapping:'typeCode'},
        	{name:'takeModeName',mapping:'typeName'}
        	])
		});
		
		 //运输方式18
		var tranModeStore	= new Ext.data.Store({ 
			storeId:"tranModeStore",
			baseParams:{filter_EQL_basDictionaryId:18,privilege:16},
			proxy:new Ext.data.HttpProxy({url:sysPath+"/"+dictionaryUrl}),
			reader:new Ext.data.JsonReader({
	                    root:'result',
	                    totalProperty:'totalCount'
	        },[
        	{name:'tranModeId',mapping:'typeCode'},
        	{name:'tranModeName',mapping:'typeName'}
        	])
		});
		 //地区类型18
		var addressTypeStore	= new Ext.data.Store({ 
			storeId:"addressTypeStore",
			baseParams:{filter_EQL_basDictionaryId:3,privilege:16},
			proxy:new Ext.data.HttpProxy({url:sysPath+"/"+dictionaryUrl}),
			reader:new Ext.data.JsonReader({
	                    root:'result',
	                    totalProperty:'totalCount'
	        },[
        	{name:'addressTypeId',mapping:'typeCode'},
        	{name:'addressTypeName',mapping:'typeName'}
        	])
		});
    //start
	var queryTbar=new Ext.Toolbar([
	 	'-','配送方式:',{
	 			xtype : "combo",
				width : 100,
				//editable:true,
				triggerAction : 'all',
				minChars:0,
				store : distributionModeStore,
				valueField : 'distributionModeName',// value值，与fields对应
				displayField : 'distributionModeName',// 显示值，与fields对应
			    name : 'distributionMode',
			    id:'searchdistributionMode', 
			    enableKeyEvents:true,listeners : {
	 		    keyup:function(textField, e){

                     if(e.getKey() == 13){

                     	searchcqStCorporateRate();

                     }
	 		}
	 	
	 	}},
	 	'-','提货方式:',{
	 	xtype : "combo",
		width : 70,
		triggerAction : 'all',
		model : 'local',
		id:'searchtakeMode',
		valueField : 'takeModeName',
    	displayField : 'takeModeName',
		store :takeModeStore,
		emptyText : '请选择',
		forceSelection : true,
		enableKeyEvents:true,listeners : {
	 		keyup:function(textField, e){
                     if(e.getKey() == 13){

                     	searchcqStCorporateRate();
                     }
	 		}
	 	}},'-',
	 	
	 	'运输方式:',{
			xtype : 'combo',
			triggerAction : 'all',
			width : 70,
			store : tranModeStore,
			minChars : 0,
			//editable:true,
			forceSelection : true,
			valueField : 'tranModeName',//value值，与fields对应
			displayField : 'tranModeName',//显示值，与fields对应
			name:'tranMode',
			id:'searchtranMode',
			 enableKeyEvents:true,listeners : {
	 		    keyup:function(textField, e){
                     if(e.getKey() == 13){
                     	searchcqStCorporateRate();
                     }
	 		    }
	 	
	 		}
		},	'-',
		'地址类型:',{xtype : "combo",
				width : 70,
				editable:true,
				triggerAction : 'all',
				forceSelection : true,
				minChars : 0,
				store : addressTypeStore,
				valueField : 'addressTypeName',
				displayField : 'addressTypeName',
			    name : 'addressType',
			    id:'searchaddressType', 
			    enableKeyEvents:true,listeners : {
	 		    keyup:function(textField, e){
                     if(e.getKey() == 13){
                     	searchcqCorporateRate();
                     }
	 		}
	 	
	 	}},	'-',
	 	'计费方式:',{
			xtype : 'combo',
			triggerAction : 'all',
			width : 70,
			store : valuationTypeStore,
			minChars : 0,
			editable:true,
			fieldLabel:'计费方式',
			forceSelection : true,
			valueField : 'valuationTypeName',//value值，与fields对应
			displayField : 'valuationTypeName',//显示值，与fields对应
			name:'valuationType',
			id:'searchvaluationType',
			 enableKeyEvents:true,listeners : {
	 		    keyup:function(textField, e){
                     if(e.getKey() == 13){
                     	searchcqStCorporateRate();
                     }
	 		    }
	 	
	 		}
		},
	 		{text : '<b>查询</b>',id : 'btn',iconCls : 'btnSearch',
    					handler : searchcqStCorporateRate
    		}
]);
    var sm = new Ext.grid.CheckboxSelectionModel({});
    var rowNum = new Ext.grid.RowNumberer({
        header:'序号', width:35, sortable:true
    });
    
    var menuGrid = new Ext.grid.GridPanel({
        renderTo:'showView',
    	id : 'basstgrid',
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
        store:dateStore,
        cm:new Ext.grid.ColumnModel([rowNum,sm,
            {header: '序号ID', dataIndex: 'id',sortable:true,hidden:true},
   			{header: '开始日期', dataIndex: 'startDate',sortable : true},
    		{header: '结束日期', dataIndex: 'endDate',sortable : true},
    		{header: '地区类型', dataIndex: 'addressType',sortable : true},
			{header: '运输方式', dataIndex: 'trafficMode',sortable : true},
			{header: '配送方式', dataIndex: 'distributionMode',sortable : true},
			{header: '提货方式', dataIndex: 'takeMode',sortable : true},
			{header: '计费方式', dataIndex: 'valuationType',sortable : true},
			{header: '最低一票', dataIndex: 'lowPrice',sortable : true},
			{header: '500KG以下等级价', dataIndex: 'stage1Rate',sortable : true},
			{header: '1000KG等级', dataIndex: 'stage2Rate',sortable : true},
			{header: '1000KG以上等级', dataIndex: 'stage3Rate',sortable : true},
			{header: '开始地址', dataIndex: 'startAddr',sortable : true},
			{header: '结束地址', dataIndex: 'endAddr',sortable : true},
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
                    id:'discountbtn',
                    text: '<b>打折生成协议价</b>',
                    disabled:true,
                    handler:function(){
                    	var record=Ext.getCmp('basstgrid').getSelectionModel().getSelections();
                    	discoutnCq(record);
                    }
                },
                {
                    xtype: 'tbseparator'
                },
                {
                    xtype: 'button',
                    text: '<b>导出</b>'
                }
            ]
        },
        bbar: new Ext.PagingToolbar({
            pageSize: pageSize, 
            privilege:90,
            store: dateStore, 
            displayInfo: true,
            displayMsg: '显示第 {0} 条到 {1} 条记录，共 {2} 条', 
            emptyMsg: "没有记录信息显示"
        }),
        listeners: {
            render: function(){
                queryTbar.render(this.tbar);
            },rowclick:function(grid,rowindex,e){
            	var record=Ext.getCmp('basstgrid').getSelectionModel().getSelections();
            	if(record.length==1){
            		Ext.getCmp('discountbtn').setDisabled(false);
            	}
            }
    	}
    });
     function searchcqStCorporateRate() {
		dateStore.proxy = new Ext.data.HttpProxy({
					method : 'POST',
					url : sysPath
								+ "/"+gridSearchUrl,
						params:{privilege:90,limit : pageSize}
				});
			var searchdistributionMode = Ext.getCmp('searchdistributionMode').getRawValue();
			var searchtakeMode = Ext.getCmp('searchtakeMode').getRawValue();
			var searchtranMode = Ext.getCmp('searchtranMode').getRawValue();
			var searchaddressType = Ext.getCmp('searchaddressType').getRawValue();
			var searchvaluationType = Ext.getCmp('searchvaluationType').getRawValue();
		dateStore.reload({
			params : {
				start : 0,
				filter_EQS_distributionMode:searchdistributionMode,
				filter_EQS_takeMode:searchtakeMode,
				filter_EQS_trafficMode:searchtranMode,
				filter_EQS_valuationType:searchvaluationType,
				filter_EQS_addressType:searchaddressType,
				filter_NEL_status:0,
				filter_EQL_departId:bussDepart,
				privilege:90,
				limit : pageSize
			}
		});
}
   menuGrid.render();
    function discoutnCq(record){
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
				xtype:'datefield',
				name:'startTime',
				id:'startTime',
				format:'Y-m-d',
				width:150,
				value:new Date(),
				fieldLabel:'开始时间<font style="color:red;">*</font>'
			},{
				xtype:'datefield',
				name:'endTime',
				id:'endTime',
				width:150,
				format:'Y-m-d',
				fieldLabel:'结束时间<font style="color:red;">*</font>'
			},{
				xtype : 'combo',
            	forceSelection : true,
            	pageSize:pageSize,
            	forceSelection : true,
            	minChars:0,
            	queryParam :'filter_LIKES_cusName',
            	triggerAction :'all',
            	valueField:'id',
            	displayField:'cusName',
    			model : 'local',
    			id:'cuscombo',
    			hiddenName : 'cusId',
            	store: cusStore,
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
						for(var i=0;i<record.length;i++){
							ids=ids+record[i].data.id+",";
						}
						form.getForm().submit({
							url : sysPath
									+ '/fi/cqStCorporateRateAction!discountCq.action',
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
			}, {			
					text : "重置",
					iconCls : 'refresh',
					handler : function() {	
						form.getForm().reset();
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
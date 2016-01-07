//代理专车协议价js
var privilege=92;//权限参数
var gridSearchUrl="fi/basSpecialTrainRateAction!ralaList.action";//查询地址
var saveUrl="fi/basSpecialTrainRateAction!save.action";//保存地址
var delUrl="fi/basSpecialTrainRateAction!deleteStatus.action";//删除地址
var auditUrl="fi/basSpecialTrainRateAction!auditStatus.action";//审核地址
var dictionaryUrl='sys/dictionaryAction!ralaList.action';//数据字典地址
var searchCusNameUrl='sys/customerAction!list.action';//查询代理公司地址
var searchspecialTrainLineUrl='fi/basSpecialTrainLineAction!list.action';//专车线路查询地址94
var authorityDepartUrl='sys/basRightDepartAction!findDepartName.action';//权限部门查询地址
var searchWidth=60;
var pubauthorityDepart=bussDepart;
var  fields=[{name:"id",mapping:'id'},
     		{name:"specialTrainLineId",mapping:'specialTrainLineId'},
     		{name:"specialTrainLineName",mapping:'specialTrainLineName'},
     		{name:'van',mapping:'van'},
     		{name:'goldCupCar',mapping:'goldCupCar'},
     		{name:'twoTonCar',mapping:'twoTonCar'},
     		{name:'threeTonCar',mapping:'threeTonCar'},
     		{name:'fiveTonCar',mapping:'fiveTonCar'},
     		{name:'chillCar',mapping:'chillCar'},
     		{name:'cusId',mapping:'cusId'},
     		{name:'status',mapping:'status'},
     		{name:'cusName',mapping:'cusName'},
     		{name:'createName',mapping:'createName'},
     		{name:'createTime',mapping:'createTime'},
     		{name:'updateName',mapping:'updateName'},
     		{name:'updateTime',mapping:'updateTime'},
     		{name:'ts',mapping:'ts'},
     		{name:'departId',mapping:'departId'},
     		{name:'roadType',mapping:'roadType'}];
     	 //权限部门
        authorityDepartStore = new Ext.data.Store({ 
            storeId:"authorityDepartStore",                        
            proxy: new Ext.data.HttpProxy({url:sysPath+"/"+authorityDepartUrl,method:'post'}),
            baseParams:{
            	privilege:63,
            	filter_EQL_userId:userId
            },
            reader: new Ext.data.JsonReader({
            root: 'resultMap', totalProperty: 'totalCount'
           }, [  {name:'departName', mapping:'DEPARTNAME',type:'string'},
            {name:'departId', mapping:'RIGHTDEPARTID',type:'string'}             
              ])    
        });
     	//专车线路 specialTrainLineId
     	specialTrainLineStore= new Ext.data.Store({ 
			storeId:"specialTrainLineStore",
			autoLoad:true,
			baseParams:{privilege:94},
			proxy:new Ext.data.HttpProxy({url:sysPath+"/"+searchspecialTrainLineUrl}),
			reader:new Ext.data.JsonReader({
	                    root:'result',
	                    totalProperty:'totalCount'
	        },[
        	{name:'specialTrainLineId',mapping:'id'},
        	{name:'specialTrainLineName',mapping:'lineName'}
        	])
		});
		//路型
     	roadTypeStore	= new Ext.data.Store({ 
			storeId:"roadTypeStore",
			baseParams:{filter_EQL_basDictionaryId:22,privilege:16},
			proxy:new Ext.data.HttpProxy({url:sysPath+"/"+dictionaryUrl}),
			reader:new Ext.data.JsonReader({
	                    root:'result',
	                    totalProperty:'totalCount'
	        },[
        	{name:'roadTypeId',mapping:'typeCode'},
        	{name:'roadTypeName',mapping:'typeName'}
        	])
		});
		roadTypeStore.load();
		//客商名称
		cpNameStore= new Ext.data.Store({ 
			storeId:"cpNameStore",
			baseParams:{privilege:61,filter_EQS_custprop:'发货代理'},
			proxy:new Ext.data.HttpProxy({url:sysPath+"/"+searchCusNameUrl}),
			reader:new Ext.data.JsonReader({
	                    root:'result', totalProperty:'totalCount'
	        },[
        	{name:'cusName',mapping:'cusName'},
        	{name:'cusId',mapping:'id'}
        	])
		});
		 //地区类型18
		addressTypeStore	= new Ext.data.Store({ 
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
		//协议价状态
		localStatus=new Ext.data.SimpleStore({
			 autoLoad:true, //此处设置为自动加载
  			  data:[['','全部'],['1','未审核'],['2','已审核']],
   			 fields:["statusId","statusName"]
		});
	var tbar=[{text:'<b>导出</b>',iconCls:'sort_down',handler:function() {
                	parent.exportExl(specialTrainRateGrid);
       	 } },'-',{text:'<b>审核</b>',iconCls:'groupPass',id:'basspecialTrainRateaudit',tooltip : '审核专车协议价',handler:function() {
                	var specialTrainRate =Ext.getCmp('specialTrainRateCenter');
					var _records = specialTrainRate.getSelectionModel().getSelections();
                	if (_records.length < 1) {
							Ext.Msg.alert(alertTitle, "请选择您要审核的行！");
							return false;
						} 
					for(var i=0;i<_records.length;i++){
						if(_records[i].data.status==2){
							Ext.Msg.alert(alertTitle, "您选择的第"+(i+1)+"行记录已经审核过了！");
							return false;
						}
					}
                	Ext.Msg.confirm(alertTitle, "确定要审核这"+_records.length+"条记录吗？", function(
									btnYes) {
								if (btnYes == 'yes' || btnYes == 'ok'
										|| btnYes == true) {
									var ids="";
									for(var i=0;i<_records.length;i++){
									
										 ids=ids+_records[i].data.id+',';
									}
									Ext.Ajax.request({
										url : sysPath+'/'
												+ auditUrl,
										params : {
											ids : ids,
										    privilege:privilege
										},
										success : function(resp) {
											Ext.Msg.alert(alertTitle,
													  "审核成功!");
											dataReload();
										}
									});
								}
							});
            } },'-',
		{text:'<b>新增</b>',iconCls:'userAdd',id:'basspecialTrainRateAdd',tooltip : '新增专车协议价',handler:function() {
                	savespecialTrainRate(null);
            } },'-',
	 	{text:'<b>修改</b>',iconCls:'userEdit',id:'basspecialTrainRateEdit',disabled:true,tooltip : '修改专车协议价',handler:function(){
	 	var specialTrainRate =Ext.getCmp('specialTrainRateCenter');
		var _records = specialTrainRate.getSelectionModel().getSelections();
		
						if (_records.length < 1) {
							Ext.Msg.alert(alertTitle, "请选择您要修改的行！");
							return false;
						} else if (_records.length > 1) {
							Ext.Msg.alert(alertTitle, "一次只能修改一行！");
							return false;
						}
	 	savespecialTrainRate(_records);}},'-',
	 	{text:'<b>删除</b>',iconCls:'userDelete',id:'basspecialTrainRateDelete',disabled:true,tooltip : '删除专车协议价',handler:function(){
	 		var specialTrainRate =Ext.getCmp('specialTrainRateCenter');
			var _records = specialTrainRate.getSelectionModel().getSelections();
	
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
	 	}},'-',
	 		
	 	{
    			xtype : 'datefield',
    		id : 'startDate',
    		format : 'Y-m-d',
    		emptyText : "开始时间",
    		anchor : '95%',
    		value:new Date().add(Date.DAY, -7),
    		hidden : true,
    		width : 100,
    		disabled : true,
    		enableKeyEvents:true,
    		listeners : {
    			'select' : function() {
    			   var start = Ext.getCmp('startDate').getValue()
    			      .format("Y-m-d");
    			   Ext.getCmp('endDate').setMinValue(start);
    		     },
    		     keyup:function(textField, e){
                     if(e.getKey() == 13){
                     	searchspecialTrainRate();
                     }
    		}
    		}
    	}, '&nbsp;&nbsp;', {
    		xtype : 'datefield',
    		id : 'endDate',
    		format : 'Y-m-d',
    		emptyText : "结束时间",
    		value:new Date(),
    		hidden : true,
    		width : 100,
    		disabled : true,
    		anchor : '95%',
    		enableKeyEvents:true,
    		listeners : {
	 		keyup:function(textField, e){
                     if(e.getKey() == 13){
                     	searchspecialTrainRate();
                     }
	 		}
    		}
    	},
    	{xtype:'numberfield',	emptyText : "开始价格",hidden : true,id:'searchStart',width : searchWidth-20, enableKeyEvents:true,listeners : {
	 		keyup:function(textField, e){
                     if(e.getKey() == 13){
                     	searchspecialTrainRate();
                     }
	 		}
	 	}},'-',
	 	{xtype:'numberfield',emptyText : "结束价格",	hidden : true,id:'searchEnd',width : searchWidth-20,
	 	enableKeyEvents:true,listeners : {
	 		keyup:function(textField, e){
                     if(e.getKey() == 13){
                     	searchspecialTrainRate();
                     }
	 		}
	 	}},
	{xtype:'textfield',blankText:'查询数据',id:'searchContent',width : searchWidth, enableKeyEvents:true,listeners : {
	 		
	 		keyup:function(textField, e){
                     if(e.getKey() == 13){
                     	searchspecialTrainRate();
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
    							['EQS_roadType','路型'],
    							['EQN_vanCar','面包车'],
    							['EQN_goldCupCar', '金杯车'],
    							['EQN_chillCar', '冷藏车'],
    							['EQN_twoTonCar', '2吨车'],
    						    ['EQN_threeTonCar', '3吨车'],
    						    ['EQN_fiveTonCar', '5吨车'],
    						    ['EQD_createTime','创建时间'],
    						    ['EQD_updateTime', '最后修改时间']],
    							
    					emptyText : '选择类型',
    					editable : false,
    					forceSelection : true,
    					listeners : {
    						
    						'select' : function(combo, record, index) {
    							
    							if (
    							combo.getValue() == 'EQD_createTime' || 
    							combo.getValue() == 'EQD_updateTime' ) {
    								
    								Ext.getCmp("startDate").enable();
    								Ext.getCmp("startDate").show();

    								Ext.getCmp("endDate").enable();
    								Ext.getCmp("endDate").show();
    								
    								Ext.getCmp("searchContent").disable();
    								Ext.getCmp("searchContent").setValue("");
    								Ext.getCmp("searchContent").hide();
    								
    								Ext.getCmp("searchStart").disable();
    								Ext.getCmp("searchStart").hide();
    								Ext.getCmp("searchStart").setValue("");

    								Ext.getCmp("searchEnd").disable();
    								Ext.getCmp("searchEnd").setValue("");
    								Ext.getCmp("searchEnd").hide();
    							}else if(combo.getValue().indexOf('Car')>0){
    								Ext.getCmp("searchStart").enable();
    								Ext.getCmp("searchStart").show();

    								Ext.getCmp("searchEnd").enable();
    								Ext.getCmp("searchEnd").show();
    								
    								
    								Ext.getCmp("searchContent").disable();
    								Ext.getCmp("searchContent").hide();
    								Ext.getCmp("searchContent").setValue("");
    								
    								Ext.getCmp("startDate").hide();
    								Ext.getCmp("startDate").setValue("");
    								
    								Ext.getCmp("endDate").hide();
    								Ext.getCmp("endDate").setValue("");
    							}
    						 	else{
    								Ext.getCmp("searchStart").hide();
    								Ext.getCmp("searchEnd").setValue("");
    					         	Ext.getCmp("searchStart").disable();

    								Ext.getCmp("searchEnd").hide();
    								Ext.getCmp("searchEnd").setValue("");
    								Ext.getCmp("searchEnd").disable();
    								
    						  		Ext.getCmp("startDate").hide();
    								Ext.getCmp("startDate").setValue("");
    								
    								Ext.getCmp("endDate").hide();
    								Ext.getCmp("endDate").setValue("");
    								
    								Ext.getCmp("searchContent").enable();
    								Ext.getCmp("searchContent").show();
    								
    								if(Ext.getCmp("searchContent").getValue().length>0){
    									searchspecialTrainRate();
    								}
    							
    							}
    							//查询全部 
    							if(Ext.getCmp("searchSelectBox").getValue().length==0){
    									Ext.getCmp("searchCpName").setValue("");
    									Ext.getCmp("searchContent").setValue("");
    									Ext.getCmp("searchspecialTrainLine").setValue("");
    									searchspecialTrainRate();
    							}
    						}
    					}
    					
    				}
    				
	 	];	
	 var queryTbar=new Ext.Toolbar([
		'客商名称:',{xtype : "combo",
				width : searchWidth+20,
				editable:true,
				triggerAction : 'all',
				// typeAhead : true,
				queryParam : 'filter_LIKES_cusName',
				pageSize : comboSize,
				forceSelection : true,
				resizable:true,
				minChars : 0,
				store : cpNameStore,
				mode : "remote",// 从本地载值
				valueField : 'cusName',// value值，与fields对应
				displayField : 'cusName',// 显示值，与fields对应
			    name : 'cpName',
			    id:'searchCpName', 
			    enableKeyEvents:true,listeners : {
	 		    keyup:function(textField, e){
                     if(e.getKey() == 13){
                     	searchspecialTrainRate();
                     }
	 		}
	 	
	 	}},	'-',
	 	'专车线路:',{
			xtype : 'combo',
			triggerAction : 'all',
			width : searchWidth+20,
			// typeAhead : true,
			queryParam : 'filter_LIKES_lineName',
			pageSize : comboSize,
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
		},	'-',
		'部门名称:',{xtype : "combo",
				width : searchWidth+20,
				editable:true,
				triggerAction : 'all',
				// typeAhead : true,
				queryParam : 'filter_LIKES_departName',
				pageSize : comboSize,
				forceSelection : true,
				resizable:true,
				minChars : 0,
				store : authorityDepartStore,
				resizable:true,
				forceSelection : true,
				valueField : 'departId',//value值，与fields对应
				displayField : 'departName',//显示值，与fields对应
			    id:'authorityDepartId', 
			    enableKeyEvents:true,listeners : {
	 		    keyup:function(textField, e){
                     if(e.getKey() == 13){
                     	searchspecialTrainRate();
                     }
	 		}
	 	
	 	}},'-','状态:',{
			xtype : 'combo',
			id:'comboStatus',
			triggerAction : 'all',
			store : localStatus,
			allowBlank : true,
			forceSelection : true,
			editable : true,
			mode : "local",//获取本地的值
			displayField : 'statusName',//显示值，与fields对应
			valueField : 'statusId',//value值，与fields对应
			width:searchWidth
		},'-',
		{text : '<b>查询</b>',id : 'btn',iconCls : 'btnSearch',
			handler : searchspecialTrainRate
		}
	]);	
Ext.onReady(function() {
   	dateStore = new Ext.data.Store({
        storeId:"dateStore",
        proxy: new Ext.data.HttpProxy({url : sysPath+ "/"+gridSearchUrl,
		baseParams:{privilege:privilege,
			limit : pageSize}}),
		//sortInfo :{field: "createTime", direction: "DESC"},
        reader: new Ext.data.JsonReader({
            root: 'result', totalProperty: 'totalCount'
        }, fields)
    });
   
   var sm = new Ext.grid.CheckboxSelectionModel({});
	var rowNum = new Ext.grid.RowNumberer({
        header:'序号', width:20
    });
    specialTrainRateGrid = new Ext.grid.GridPanel({
        renderTo: Ext.getBody(),
        id:'specialTrainRateCenter',
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
        		{header: '序号ID', dataIndex: 'id',sortable:true,hidden:true},
 				{header: '线路', dataIndex: 'specialTrainLineName',sortable : true},
 				//{header: '地区类型', dataIndex: 'addressType',sortable : true},
       			{header: '面包车', dataIndex: 'van',sortable : true},
        		{header: '金杯车', dataIndex: 'goldCupCar',sortable : true},
 				{header: '2吨车', dataIndex: 'twoTonCar',sortable : true},
 				{header: '3吨车', dataIndex: 'threeTonCar',sortable : true},
 				{header: '5吨车', dataIndex: 'fiveTonCar',sortable : true},
 				{header: '冷藏车', dataIndex: 'chillCar',sortable : true},
 				{header: '客商名称', dataIndex: 'cusName',sortable : true},
 				{header: '路型', dataIndex: 'roadType',sortable : true},
 				{header: '状态', dataIndex: 'status',sortable : true
					,renderer:function(v){
						return v==1?'未审核':'审核';
		        	}
 				}
        ]),
        store:dateStore,
        tbar: tbar,
        listeners: {
                render: function(){
                    queryTbar.render(this.tbar);
                }
            },
        bbar: new Ext.PagingToolbar({
            pageSize: pageSize, 
            privilege:privilege,
            store: dateStore, 
            displayInfo: true,
            displayMsg: '显示第 {0} 条到 {1} 条记录，共 {2} 条', 
            emptyMsg: "没有记录信息显示"
        })
    });
	
	    specialTrainRateGrid.on('click', function() {
	       selabled();
	     	
	    });
		specialTrainRateGrid.on('rowdblclick',function(grid,index,e){
		var _records = specialTrainRateGrid.getSelectionModel().getSelections();
				
				if (_records.length ==1) {
					savespecialTrainRate(_records);
				}
			 	
		});
    });
    
    
	
   function searchspecialTrainRate() {
		dateStore.proxy = new Ext.data.HttpProxy({
					method : 'POST',
					url : sysPath
								+ "/"+gridSearchUrl,
						params:{privilege:privilege,limit : pageSize}
				});
			
			var searchCpName = Ext.getCmp('searchCpName').getRawValue();
			var checkItems=Ext.get("checkItems").dom.value;
			//var searchaddressType = Ext.getCmp('searchaddressType').getRawValue();
			var searchspecialTrainLine=Ext.getCmp('searchspecialTrainLine').getValue();
			var authorityDepartId=Ext.getCmp('authorityDepartId').getValue();
			if(null!=authorityDepartId && authorityDepartId>0){
				pubauthorityDepart=authorityDepartId;
			}
			var status=Ext.getCmp('comboStatus').getValue();
			Ext.apply(dateStore.baseParams, {
				filter_EQS_cusName:searchCpName,
				filter_EQL_specialTrainLineId:searchspecialTrainLine,
				//filter_EQS_addressType:searchaddressType,
				privilege:privilege,
	            filter_NEL_status:0,
	            filter_EQL_status:status,
				filter_EQL_departId:pubauthorityDepart,
				limit : pageSize,
				checkItems : '',
				itemsValue : '',
				filter_GEN_van:'',
				filter_LEN_van:'',
				filter_GEN_goldCupCar:'',
				filter_LEN_goldCupCar:'',
				filter_GEN_chillCar:'',
				filter_LEN_chillCar:'',
				filter_GEN_twoTonCar:'',
				filter_LEN_twoTonCar:'',
				filter_GEN_threeTonCar:'',
				filter_LEN_threeTonCar:'',
				filter_GEN_fiveTonCar:'',
				filter_LEN_fiveTonCar:'',
				filter_GED_createTime:'',
				filter_LED_createTime:'',
				filter_GED_updateTime:'',
				filter_LED_updateTime:''
				
			});
			
			if(checkItems.indexOf('Car')>0){
				var searchCar = checkItems.substring(checkItems.indexOf('_')+1);
    			 
    			 if(searchCar=='vanCar'){
    				Ext.apply(dateStore.baseParams, {
						filter_GEN_van:(!Ext.getCmp("searchStart").hidden?(Ext.get("searchStart").dom.value=="开始价格"?"":Ext.get("searchStart").dom.value):""),
						filter_LEN_van:(!Ext.getCmp("searchEnd").hidden?(Ext.get("searchEnd").dom.value=="结束价格"?"":Ext.get("searchEnd").dom.value):"")
					});
    			 }else if(searchCar=='goldCupCar'){
    			 	Ext.apply(dateStore.baseParams, {
						filter_GEN_goldCupCar:(!Ext.getCmp("searchStart").hidden?(Ext.get("searchStart").dom.value=="开始价格"?"":Ext.get("searchStart").dom.value):""),
						filter_LEN_goldCupCar:(!Ext.getCmp("searchEnd").hidden?(Ext.get("searchEnd").dom.value=="结束价格"?"":Ext.get("searchEnd").dom.value):"")
					});
    			 }else if(searchCar=='chillCar'){
    			 	Ext.apply(dateStore.baseParams, {
						filter_GEN_chillCar:(!Ext.getCmp("searchStart").hidden?(Ext.get("searchStart").dom.value=="开始价格"?"":Ext.get("searchStart").dom.value):""),
						filter_LEN_chillCar:(!Ext.getCmp("searchEnd").hidden?(Ext.get("searchEnd").dom.value=="结束价格"?"":Ext.get("searchEnd").dom.value):"")
					});
    			 }else if(searchCar=='twoTonCar'){
    			 	Ext.apply(dateStore.baseParams, {
						filter_GEN_twoTonCar:(!Ext.getCmp("searchStart").hidden?(Ext.get("searchStart").dom.value=="开始价格"?"":Ext.get("searchStart").dom.value):""),
						filter_LEN_twoTonCar:(!Ext.getCmp("searchEnd").hidden?(Ext.get("searchEnd").dom.value=="结束价格"?"":Ext.get("searchEnd").dom.value):"")
					});
    			 }else if(searchCar=='threeTonCar'){
    			 	Ext.apply(dateStore.baseParams, {
						filter_GEN_threeTonCar:(!Ext.getCmp("searchStart").hidden?(Ext.get("searchStart").dom.value=="开始价格"?"":Ext.get("searchStart").dom.value):""),
						filter_LEN_threeTonCar:(!Ext.getCmp("searchEnd").hidden?(Ext.get("searchEnd").dom.value=="结束价格"?"":Ext.get("searchEnd").dom.value):"")
					});
    			 }else if(searchCar=='fiveTonCar'){
    			 	Ext.apply(dateStore.baseParams, {
						filter_GEN_fiveTonCar:(!Ext.getCmp("searchStart").hidden?(Ext.get("searchStart").dom.value=="开始价格"?"":Ext.get("searchStart").dom.value):""),
						filter_LEN_fiveTonCar:(!Ext.getCmp("searchEnd").hidden?(Ext.get("searchEnd").dom.value=="结束价格"?"":Ext.get("searchEnd").dom.value):"")
					});
    			 }
			}
			 if (Ext.getCmp('searchSelectBox').getValue() == 'EQD_createTime') {
    				 Ext.apply(dateStore.baseParams, {
    						filter_GED_createTime:(!Ext.getCmp("startDate").hidden?(Ext.get("startDate").dom.value=="开始时间"?"":Ext.get("startDate").dom.value):""),
    						filter_LED_createTime : (!Ext.getCmp("endDate").hidden?(Ext.get("endDate").dom.value=="结束时间"?"":Ext.get("endDate").dom.value):"")
    					});
    		} else if (Ext.getCmp('searchSelectBox').getValue() == 'EQD_updateTime') {
    				Ext.apply(dateStore.baseParams, {
    						filter_GED_updateTime:(!Ext.getCmp("startDate").hidden?(Ext.get("startDate").dom.value=="开始时间"?"":Ext.get("startDate").dom.value):""),
    						filter_LED_updateTime : (!Ext.getCmp("endDate").hidden?(Ext.get("endDate").dom.value=="结束时间"?"":Ext.get("endDate").dom.value):"")
    					});
    		} else{	
    			 Ext.apply(dateStore.baseParams, {
    						checkItems : Ext.get("checkItems").dom.value,
							itemsValue : Ext.get("searchContent").dom.value
    			});
			}
		
		var editbtn = Ext.getCmp('basspecialTrainRateEdit');
		var deletebtn = Ext.getCmp('basspecialTrainRateDelete');

		editbtn.setDisabled(true);
		deletebtn.setDisabled(true);

		dataReload();
		
		
	}
function savespecialTrainRate(_records) {
	
					if(_records!=null)
					var cid=_records[0].data.id;
					var form = new Ext.form.FormPanel({
								labelAlign : 'left',
								frame : true,
								url:sysPath+'/'+gridSearchUrl,
								baseParams:{
				    				filter_EQL_id:cid,
									privilege:privilege,
									limit : pageSize},
								bodyStyle : 'padding:5px 5px 0',
								width : 500,
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
																{xtype : "combo",
																	editable:false,
																	triggerAction : 'all',
																	fieldLabel:'路型',
																	allowBlank : false,
																	store : roadTypeStore,
																	mode : "local",// 从本地载值
																	//valueField : 'roadTypeId',// value值，与fields对应
																	displayField : 'roadTypeName',// 显示值，与fields对应
																    name : 'roadType',
																    id:'roadTypeId', 
																    anchor : '95%',
																	    enableKeyEvents:true,listeners : {
																	    keyup:function(textField, e){
																             if(e.getKey() == 13){
																             	//searchspecialTrainRate();
																             }
																	}
																
																}}
																,  {
																	xtype : 'combo',
																	triggerAction : 'all',
																	// typeAhead : true,
																	queryParam : 'filter_LIKES_lineName',
																	pageSize : comboSize,
																	forceSelection : true,
																	resizable:true,
																	minChars : 0,
																	store : specialTrainLineStore,
																	mode:'remote',
																	fieldLabel:'专车线路',
																	allowBlank : false,
																	valueField : 'specialTrainLineId',//value值，与fields对应
																	displayField : 'specialTrainLineName',//显示值，与fields对应
																	hiddenName:'specialTrainLineId',
																	id:'specialTrainLine',
																	anchor : '95%',
																	enableKeyEvents:true,listeners : {
																		keyup:function(textField, e){
																	             if(e.getKey() == 13){
																	             	//searchspecialTrainRate();
																	             }
																		}
																	
																	}
																},{
																	name : "cusId",
																	id:'cusId',
																	xtype : "hidden"
																}, 
																{xtype : "combo",
																	editable:true,
																	triggerAction : 'all',
																	// typeAhead : true,
																	queryParam : 'filter_LIKES_cusName',
																	pageSize : comboSize,
																	forceSelection : true,
																	resizable:true,
																	minChars : 0,
																	fieldLabel:'客商名称',
																	allowBlank : false,
																	store : cpNameStore,
																	mode : "remote",// 从本地载值
																	valueField : 'cusId',// value值，与fields对应
																	displayField : 'cusName',// 显示值，与fields对应
																	name : 'cusName',
																	id:'cpNameId', 
																	anchor : '95%',
																    enableKeyEvents:true,listeners : {
																    'select':function(combo,e){
																    	Ext.getCmp('cusId').setValue(combo.getValue());
																    },
																    keyup:function(combo, e){
															             if(e.getKey() == 13){
															             	//searchspecialTrainRate();
															             }
																		}
																	
																    }
																}, {
																	xtype : 'numberfield',
																	labelAlign : 'left',
																	allowBlank : false,
																	fieldLabel : '面包车<font style="color:red;">*</font>',
																	blankText : "面包车不能为空！",
																	nanText:'请输入数字',
																	allowNegative :false,
																	maxLength:10,
																	name : 'van',
																	anchor : '95%'
																}]

													}, {
														columnWidth : .5,
														layout : 'form',
														items : [ {
																	xtype : 'numberfield',
																	labelAlign : 'left',
																	fieldLabel : '2吨车<font style="color:red;">*</font>',
																	blankText : "2吨车不能为空！",
																	nanText:'请输入数字',
																	allowNegative :false,
																	allowBlank : false,
																	maxLength:10,
																	name : 'twoTonCar',
																	anchor : '95%'
																}, {
																	xtype : 'numberfield',
																	labelAlign : 'left',
																	fieldLabel : '3吨车<font style="color:red;">*</font>',
																	blankText : "3吨车不能为空！",
																	nanText:'请输入数字',
																	allowNegative :false,
																	allowBlank : false,
																	maxLength:10,
																	name : 'threeTonCar',
																	anchor : '95%'
																},{
																	xtype : 'numberfield',
																	labelAlign : 'left',
																	fieldLabel : '5吨车<font style="color:red;">*</font>',
																	blankText : "5吨车不能为空！",
																	nanText:'请输入数字',
																	allowNegative :false,
																	allowBlank : false,
																	maxLength:10,
																	name : 'fiveTonCar',
																	anchor : '95%'
																},{
																	xtype : 'numberfield',
																	labelAlign : 'left',
																	fieldLabel : '冷藏车<font style="color:red;">*</font>',
																	blankText : "冷藏车不能为空！",
																	nanText:'请输入数字',
																	allowNegative :false,
																	allowBlank : false,
																	maxLength:10,
																	name : 'chillCar',
																	anchor : '95%'
																}, {
																	xtype : 'numberfield',
																	labelAlign : 'left',
																	allowBlank : false,
																	fieldLabel : '金杯车<font style="color:red;">*</font>',
																	blankText : "金杯车不能为空！",
																	nanText:'请输入数字',
																	allowNegative :false,
																	maxLength:10,
																	name : 'goldCupCar',
																	anchor : '95%'
																}
																, {
																	xtype : "hidden",
																	hidden:true,
																	value:1,
																	name : 'status'
																}]
													}]
													
										}]
										});
									
		specialTrainRateTitle='添加专车协议价信息';
		if(cid!=null){
		specialTrainRateTitle='修改专车协议价信息';
		//departStore.load();
		//Ext.getCmp('mycombo').store=departStore;
		form.load({
						waitMsg : '正在载入数据...',
		    			success : function(_form, action) {
		    				Ext.getCmp('specialTrainLine').setRawValue(_records[0].data.specialTrainLineName);
		    			},
		    			failure : function(_form, action) {
		    				Ext.MessageBox.alert(alertTitle, '载入失败');
		    			}
					});
					}
		var win = new Ext.Window({
		title : specialTrainRateTitle,
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
					form.getForm().submit({
						url : sysPath
								+ '/'+saveUrl,
								params:{privilege:privilege,limit : pageSize},
						waitMsg : '正在保存数据...',
						success : function(form, action) {
							 Ext.Msg.alert(alertTitle,
									action.result.msg, function() {
										dataReload();
										if(_records!=null){
											win.hide();
										}
										else{
											form.reset();
											Ext.getCmp('roadTypeId').focus();
										}
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
						
				text : "重置",
				iconCls : 'refresh',
				handler : function() {
					
					//departStore.load();
					if(cid==null){
						
						form.getForm().reset();
					
					}
					if(cid!=null){
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
				filter_NEL_status:0,
				filter_EQL_departId:pubauthorityDepart,
				privilege:privilege,
				limit : pageSize
			}
		});
		
		selabled();
		
 }
 
 function selabled(){
 	 var _record = specialTrainRateGrid.getSelectionModel().getSelections();
 	
        var updatebtn = Ext.getCmp('basspecialTrainRateEdit');
        var deletebtn = Ext.getCmp('basspecialTrainRateDelete');
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
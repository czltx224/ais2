//公布价管理js
var privilege=90;//权限参数
var gridSearchUrl="fi/cqStCorporateRateAction!ralaList.action";//查询地址
var saveUrl="fi/cqStCorporateRateAction!save.action";//保存地址
var delUrl="fi/cqStCorporateRateAction!deleteStatus.action";//删除地址
var auditUrl="fi/cqStCorporateRateAction!auditStatus.action";//审核地址
var dictionaryUrl='sys/dictionaryAction!ralaList.action';//数据字典地址
var searchAddrUrl='sys/basAreaAction!list.action';//地区查询地址
var authorityDepartUrl='sys/basRightDepartAction!findDepartName.action';//权限部门查询地址
var searchWidth=70;
var pubauthorityDepart=bussDepart;
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
     	//开始地址
     	startAddrStore= new Ext.data.Store({ 
			storeId:"startAddrStore",
			baseParams:{filter_EQS_areaRank:'市',privilege:55,limit:comboSize},
			proxy:new Ext.data.HttpProxy({url:sysPath+"/"+searchAddrUrl}),
			reader:new Ext.data.JsonReader({
	                    root:'result',
	                    totalProperty:'totalCount'
	        },[
        	{name:'startAddrName',mapping:'text'}
        	])
		});
		
		//结束地址
		endAddrStore= new Ext.data.Store({ 
			storeId:"endAddrStore",
			baseParams:{privilege:55,limit:comboSize},
			proxy:new Ext.data.HttpProxy({url:sysPath+"/"+searchAddrUrl}),
			reader:new Ext.data.JsonReader({
	                    root:'result',
	                    totalProperty:'totalCount'
	        },[
        	{name:'endAddrName',mapping:'text'}
        	])
		});
     	//配送方式4
		distributionModeStore	= new Ext.data.Store({ 
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
		takeModeStore	= new Ext.data.Store({ 
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
		tranModeStore	= new Ext.data.Store({ 
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
		
	    //计费方式20
		valuationTypeStore	= new Ext.data.Store({ 
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
                	parent.exportExl(cqStCorporateRateGrid);
       			 } },'-',{text:'<b>审核</b>',iconCls:'groupPass',id:'bascqStCorporateRateaudit',tooltip : '审核代理标准协议价',handler:function() {
                	var cqStCorporateRate =Ext.getCmp('cqStCorporateRateCenter');
					var _records = cqStCorporateRate.getSelectionModel().getSelections();
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
            } },'-',{text:'<b>下载模版</b>',iconCls:'',handler:function() {
                	upLoadFile();
        } }, '-',
        {text:'<b>导入</b>',iconCls:'sort_up',handler:function() {
                	importExl();
        } },'-',
		{text:'<b>新增</b>',iconCls:'userAdd',id:'bascqStCorporateRateAdd',tooltip : '新增代理标准协议价',handler:function() {
                	savecqStCorporateRate(null);
            } },'-',
	 	{text:'<b>修改</b>',iconCls:'userEdit',id:'bascqStCorporateRateEdit',disabled:true,tooltip : '修改代理标准协议价',handler:function(){
	 	var cqStCorporateRate =Ext.getCmp('cqStCorporateRateCenter');
		var _records = cqStCorporateRate.getSelectionModel().getSelections();
		
						if (_records.length < 1) {
							Ext.Msg.alert(alertTitle, "请选择您要修改的行！");
							return false;
						} else if (_records.length > 1) {
							Ext.Msg.alert(alertTitle, "一次只能修改一行！");
							return false;
						}
	 	savecqStCorporateRate(_records);}},'-',
	 	{text:'<b>删除</b>',iconCls:'userDelete',id:'bascqStCorporateRateDelete',disabled:true,tooltip : '删除代理标准协议价',handler:function(){
	 		var cqStCorporateRate =Ext.getCmp('cqStCorporateRateCenter');
			var _records = cqStCorporateRate.getSelectionModel().getSelections();
	
					if (_records.length < 1) {
						Ext.Msg.alert(alertTitle, "请选择您要删除的行！");
						return false;
					} 

					Ext.Msg.confirm(alertTitle, "确定要删除这"+_records.length+"条记录吗？", function(
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
	 	'计费方式:',{
			xtype : 'combo',
			triggerAction : 'all',
			width : searchWidth,
			store : valuationTypeStore,
			minChars : 0,
			editable:true,
			fieldLabel:'计费方式',
			forceSelection : true,
			//valueField : 'valuationTypeId',//value值，与fields对应
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
		},'-',
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
    		value:new Date(),
    		hidden : true,
    		width : 100,
    		disabled : true,
    		anchor : '95%'
    	},
	{xtype:'textfield',blankText:'查询数据',id:'searchContent', enableKeyEvents:true,listeners : {
	 		
	 		keyup:function(textField, e){

                     if(e.getKey() == 13){

                     	searchcqStCorporateRate();

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
    							['EQL_lowPrice','最低一票'],
    							['EQS_startAddr','开始地址'],
    							['EQS_endAddr','结束地址'],
    							//['LIKES_remark','KG'],
    							//['EQL_discount','折扣'],
    							['EQD_startDate', '开始日期'],
    						    ['EQD_endDate', '结束日期'],
    						    ['EQD_createTime','创建时间'],
    						    ['EQD_updateTime', '最后修改时间']],
    							
    					emptyText : '选择类型',
    					editable : false,
    					forceSelection : true,
    					listeners : {
    						
    						'select' : function(combo, record, index) {
    							
    							if (combo.getValue() == 'EQD_startDate' || 
    							combo.getValue() == 'EQD_endDate' ||
    							combo.getValue() == 'EQD_createTime' || 
    							combo.getValue() == 'EQD_updateTime' ) {
    								
    								Ext.getCmp("startDate").enable();
    								Ext.getCmp("startDate").show();

    								Ext.getCmp("endDate").enable();
    								Ext.getCmp("endDate").show();
    								
    								
    								Ext.getCmp("searchContent").disable();
    								Ext.getCmp("searchContent").hide();
    								Ext.getCmp("searchContent").setValue("");
    							}
    						 	else{
    					         	
    						  		Ext.getCmp("startDate").hide();
    								Ext.getCmp("startDate").setValue("");
    								
    								Ext.getCmp("endDate").hide();
    								Ext.getCmp("endDate").setValue("");
    								
    								Ext.getCmp("searchContent").enable();
    								Ext.getCmp("searchContent").show();
    								if(Ext.getCmp("searchContent").getValue().length>0){
    									searchcqStCorporateRate();
    								}
    							
    							}
    							
    							//查询全部
    							if(combo.getValue()==''){
    								
    								Ext.getCmp("searchdistributionMode").setValue("");
    								Ext.getCmp("searchtakeMode").setValue("");
    								Ext.getCmp("searchtranMode").setValue("");
    								Ext.getCmp("searchContent").setValue("");
    								Ext.getCmp("searchvaluationType").setValue("");
    								Ext.getCmp("searchaddressType").setValue("");
    								
    								searchcqStCorporateRate();
    							}
    						}
    					}
    					
    				}
	 	];	

var queryTbar=new Ext.Toolbar([
	 	'-','配送方式:',{xtype : "combo",
				width : searchWidth,
				editable:true,
				triggerAction : 'all',
				store : distributionModeStore,
				mode : "",// 从本地载值
				//valueField : 'distributionModeId',// value值，与fields对应
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
	    xtype : 'combo',
		width:searchWidth+20, 
		editable : true,
		triggerAction : 'all',
		store : takeModeStore,
		mode:'',
		//valueField : 'takeModeId',//value值，与fields对应
		displayField : 'takeModeName',//显示值，与fields对应
		name:'takeMode',
		id:'searchtakeMode',
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
			width : searchWidth+20,
			store : tranModeStore,
			minChars : 0,
			editable:true,
			forceSelection : true,
			//valueField : 'tranModeId',//value值，与fields对应
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
				width : searchWidth+20,
				editable:true,
				triggerAction : 'all',
				forceSelection : true,
				minChars : 0,
				store : addressTypeStore,
				mode : "",// 从本地载值
				//valueField : 'addressTypeId',// value值，与fields对应
				displayField : 'addressTypeName',// 显示值，与fields对应
			    name : 'addressType',
			    id:'searchaddressType', 
			    enableKeyEvents:true,listeners : {
	 		    keyup:function(textField, e){
                     if(e.getKey() == 13){
                     	searchcqCorporateRate();
                     }
	 		}
	 	
	 	}},	'-',
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
                     	searchcqStCorporateRate();
                     }
	 		}
	 	
	 	}},
	 		{text : '<b>查询</b>',id : 'btn',iconCls : 'btnSearch',
    					handler : searchcqStCorporateRate
    		}
	 	]);	
	 	
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
    cqStCorporateRateGrid = new Ext.grid.GridPanel({
        renderTo: Ext.getBody(),
        id:'cqStCorporateRateCenter',
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
       			{header: '开始日期', dataIndex: 'startDate',sortable : true},
        		{header: '结束日期', dataIndex: 'endDate',sortable : true,hidden:true},
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
	
	    cqStCorporateRateGrid.on('click', function() {
	       selabled();
	     	
	    });
		cqStCorporateRateGrid.on('rowdblclick',function(grid,index,e){
		var _records = cqStCorporateRateGrid.getSelectionModel().getSelections();
				
				if (_records.length ==1) {
					savecqStCorporateRate(_records);
				}
			 	
		});
    });
    
    
	
   function searchcqStCorporateRate() {
		dateStore.baseParams={privilege:privilege,limit : pageSize};
		
			var searchdistributionMode = Ext.getCmp('searchdistributionMode').getRawValue();
			var searchtakeMode = Ext.getCmp('searchtakeMode').getRawValue();
			var searchtranMode = Ext.getCmp('searchtranMode').getRawValue();
			var searchvaluationType = Ext.getCmp('searchvaluationType').getRawValue();
			var searchaddressType = Ext.getCmp('searchaddressType').getRawValue();
			var authorityDepartId=Ext.getCmp('authorityDepartId').getValue();
			if(null!=authorityDepartId && authorityDepartId>0){
				pubauthorityDepart=authorityDepartId;
			}
			var status=Ext.getCmp('comboStatus').getValue();
				 Ext.apply(dateStore.baseParams, {
					filter_EQS_distributionMode:searchdistributionMode,
					filter_EQS_takeMode:searchtakeMode,
					filter_EQS_trafficMode:searchtranMode,
					filter_EQS_valuationType:searchvaluationType,
					filter_EQS_addressType:searchaddressType,
					filter_NEL_status:0,
					filter_EQL_status:status,
					filter_EQL_departId:pubauthorityDepart,
					privilege:privilege,
					limit : pageSize,
					checkItems : '',
					itemsValue : '',
					filter_GED_startDate:'',
					filter_LED_startDate:'',
					filter_GED_endDate:'',
					filter_LED_endDate:'',
					filter_GED_createTime:'',
					filter_LED_createTime:'',
					filter_GED_updateTime:'',
					filter_LED_updateTime:''
				});
			 if (Ext.getCmp('searchSelectBox').getValue() == 'EQD_startDate') {
					 Ext.apply(dateStore.baseParams, {
    						filter_GED_startDate :(!Ext.getCmp("startDate").hidden?(Ext.get("startDate").dom.value=="开始时间"?"":Ext.get("startDate").dom.value):""),
    						filter_LED_startDate : (!Ext.getCmp("endDate").hidden?(Ext.get("endDate").dom.value=="结束时间"?"":Ext.get("endDate").dom.value):"")
    					});
    			

    		} else if (Ext.getCmp('searchSelectBox').getValue() == 'EQD_endDate') {
    				 Ext.apply(dateStore.baseParams, {
    						filter_GED_endDate:(!Ext.getCmp("startDate").hidden?(Ext.get("startDate").dom.value=="开始时间"?"":Ext.get("startDate").dom.value):""),
    						filter_LED_endDate : (!Ext.getCmp("endDate").hidden?(Ext.get("endDate").dom.value=="结束时间"?"":Ext.get("endDate").dom.value):"")
    					});
    		} else if (Ext.getCmp('searchSelectBox').getValue() == 'EQD_createTime') {
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
		
		var editbtn = Ext.getCmp('bascqStCorporateRateEdit');
		var deletebtn = Ext.getCmp('bascqStCorporateRateDelete');

		editbtn.setDisabled(true);
		deletebtn.setDisabled(true);

		dataReload();
		
		
	}
function savecqStCorporateRate(_records) {
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
																	fieldLabel:'配送方式<font style="color:red;">*</font>',
																	allowBlank : false,
																	store : distributionModeStore,
																	mode : "",// 从本地载值
																	//valueField : 'distributionModeId',// value值，与fields对应
																	displayField : 'distributionModeName',// 显示值，与fields对应
																    name : 'distributionMode',
																    id:'distributionModeId', 
																    anchor : '95%',
																	    enableKeyEvents:true,listeners : {
																	    keyup:function(textField, e){
																             if(e.getKey() == 13){
																             	//searchcqStCorporateRate();
																             }
																	}
																
																}}
																,  {
																	xtype : 'combo',
																	editable : false,
																	triggerAction : 'all',
																	store : takeModeStore,
																	mode:'',
																	allowBlank : false,
																	fieldLabel:'提货方式<font style="color:red;">*</font>',
																	//valueField : 'takeModeId',//value值，与fields对应
																	displayField : 'takeModeName',//显示值，与fields对应
																	name:'takeMode',
																	id:'takeModeId',
																	anchor : '95%',
																	enableKeyEvents:true,listeners : {
																		keyup:function(textField, e){
																	             if(e.getKey() == 13){
																	             	//searchcqStCorporateRate();
																	             }
																		}
																	
																	}
																}, {
																	xtype : 'combo',
																	triggerAction : 'all',
																	store : tranModeStore,
																	minChars : 0,
																	editable:false,
																	allowBlank : false,
																	fieldLabel:'运输方式<font style="color:red;">*</font>',
																	forceSelection : true,
																	//valueField : 'tranModeId',//value值，与fields对应
																	displayField : 'tranModeName',//显示值，与fields对应
																	name:'trafficMode',
																	id:'tranModeId',
																	anchor : '95%',
																	 enableKeyEvents:true,listeners : {
															 		    keyup:function(textField, e){
														                     if(e.getKey() == 13){
														                     	//searchcqStCorporateRate();
														                     }
															 		    }
															 	
															 		}
																},{
																	xtype : "combo",
																	editable:false,
																	triggerAction : 'all',
																	forceSelection : true,
																	minChars : 0,
																	fieldLabel:'地区类型<font style="color:red;">*</font>',
																	allowBlank : false,
																	store : addressTypeStore,
																	//valueField : 'addressTypeId',// value值，与fields对应
																	displayField : 'addressTypeName',// 显示值，与fields对应
																	name : 'addressType',
																	id:'addressTypeId',
																	anchor : '95%',
																	    enableKeyEvents:true,listeners : {
																	    keyup:function(textField, e){
																             if(e.getKey() == 13){
																             	//searchcqCorporateRate();
																             }
																	}
																	}
																},{
																	xtype : 'combo',
																	triggerAction : 'all',
																	store : valuationTypeStore,
																	minChars : 0,
																	editable:false,
																	fieldLabel:'计费方式<font style="color:red;">*</font>',
																	allowBlank : false,
																	forceSelection : true,
																	//valueField : 'valuationTypeId',//value值，与fields对应
																	displayField : 'valuationTypeName',//显示值，与fields对应
																	name:'valuationType',
																	id:'valuationTypeId',
																	anchor : '95%',
																	 enableKeyEvents:true,listeners : {
															 		    keyup:function(textField, e){
														                     if(e.getKey() == 13){
														                     	//searchcqCorporateRate();
														                     }
															 		    }
															 	
															 		}
																},{
																	xtype : "combo",
																	triggerAction : 'all',
																	queryParam : 'filter_LIKES_areaName',
																	pageSize : comboSize,
																	forceSelection : true,
																	resizable:true,
																	minChars : 0,
																	fieldLabel:'开始地址<font style="color:red;">*</font>',
																	allowBlank : false,
																	store : startAddrStore,
																	mode : "remote",// 从本地载值
																	valueField : 'startAddrName',// value值，与fields对应
																	displayField : 'startAddrName',// 显示值，与fields对应
																	name : 'startAddr',
																	id:'startAddrId',
																	anchor : '95%',
																	    enableKeyEvents:true,listeners : {
																	    keyup:function(textField, e){
																             if(e.getKey() == 13){
																             	//searchcqCorporateRate();
																             }
																	}
																	}
																},{
																	xtype : "combo",
																	triggerAction : 'all',
																	queryParam : 'filter_LIKES_areaName',
																	pageSize : comboSize,
																	forceSelection : true,
																	resizable:true,
																	minChars : 0,
																	fieldLabel:'结束地址',
																	store : endAddrStore,
																	mode : "remote",// 从本地载值
																	valueField : 'endAddrName',// value值，与fields对应
																	displayField : 'endAddrName',// 显示值，与fields对应
																	name : 'endAddr',
																	id:'endAddrId',
																	anchor : '95%',
																	    enableKeyEvents:true,listeners : {
																	    keyup:function(textField, e){
																             if(e.getKey() == 13){
																             	//searchcqCorporateRate();
																             }
																	}
																	}
																}]

													}, {
														columnWidth : .5,
														layout : 'form',
														items : [{
																	xtype : 'numberfield',
																	labelAlign : 'left',
																	allowBlank : false,
																	fieldLabel : '最低一票<font style="color:red;">*</font>',
																	blankText : "最低一票不能为空！",
																	nanText:'请输入数字',
																	allowNegative :false,
																	maxLength:10,
																	name : 'lowPrice',
																	anchor : '95%'
																}, {
																	xtype : 'numberfield',
																	labelAlign : 'left',
																	fieldLabel : '500KG以下<font style="color:red;">*</font>',
																	blankText : "500KG以下等级价不能为空！",
																	nanText:'请输入数字',
																	allowNegative :false,
																	allowBlank : false,
																	maxLength:10,
																	name : 'stage1Rate',
																	anchor : '95%'
																}, {
																	xtype : 'numberfield',
																	labelAlign : 'left',
																	fieldLabel : '500KG至1000KG<font style="color:red;">*</font>',
																	blankText : "500KG至1000KG<等级价不能为空！",
																	nanText:'请输入数字',
																	allowNegative :false,
																	allowBlank : false,
																	maxLength:10,
																	name : 'stage2Rate',
																	anchor : '95%'
																},{
																	xtype : 'numberfield',
																	labelAlign : 'left',
																	fieldLabel : '1000KG以上<font style="color:red;">*</font>',
																	blankText : "1000KG以上等级价不能为空！",
																	nanText:'请输入数字',
																	allowNegative :false,
																	allowBlank : false,
																	maxLength:10,
																	name : 'stage3Rate',
																	anchor : '95%'
																}, {
																	xtype : "hidden",
																	hidden:true,
																	value:1,
																	name : 'status'
																},{
																	
																	labelAlign : 'left',
																	fieldLabel : '开始日期<font style="color:red;">*</font>',
																	xtype:'datefield',
																	format:'Y-m-d',
																	blankText : "开始日期不能为空！",
																	editable : false,
																	allowBlank : false,
																	name : 'startDate',
																	anchor : '95%'
																},{
																	
																	labelAlign : 'left',
																	//fieldLabel : '结束日期',
																	xtype:'datefield',
																	format:'Y-m-d',
																	editable : true,
																	hidden:true,
																	name : 'endDate',
																	anchor : '95%'
																}]
													}]
													
										}]
										});
									
		cqStCorporateRateTitle='添加代理标准协议价信息';
		if(cid!=null){
		cqStCorporateRateTitle='修改代理标准协议价信息';
		//departStore.load();
		//Ext.getCmp('mycombo').store=departStore;
		form.load({
						waitMsg : '正在载入数据...',
		    			success : function(_form, action) {
		    			},
		    			failure : function(_form, action) {
		    				Ext.MessageBox.alert(alertTitle, '载入失败');
		    			}
					});
					}
		var win = new Ext.Window({
		title : cqStCorporateRateTitle,
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
											Ext.getCmp('distributionModeId').focus();
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
 
  function importExl(){
 	var form2= new Ext.form.FormPanel({
					labelAlign : 'left',
					frame : true,
					fileUpload : true,
					bodyStyle : 'padding:5px 5px 5px',
				    width : 480,
				    labelWidth :100,
		            labelAlign : "right",
			        items : [
						{xtype : 'textfield',
						 inputType : 'file',
						 fieldLabel : '协议价Excel',
						 name : 'upLoadExcel',
						 id:'upLoadExcel',
						 anchor : '100%'
						}]
							});
		var win = new Ext.Window({
			title : '导入协议价',
			width : 500,
			closeAction : 'hide',
			plain : true,
		    resizable : false,	
			modal : true,
			items :form2,
			buttonAlign : "center",	buttons : [{
				text : "保存",
				id:'savebtn',
				iconCls : 'groupSave',
				handler : function() {
					if (form2.getForm().isValid()) {
						this.disabled = true;//只能点击一次
						form2.getForm().submit({
							url : sysPath+ '/fi/cqStCorporateRateAction!saveExcel.action',
							method : 'post',
							waitMsg : '正在处理数据...',
							success : function(form, action) {	
								win.hide(), 
								Ext.Msg.alert(alertTitle,action.result.msg,function(){
										dataReload();										
								});
							},
							failure : function(form, action) {
								if (action.faliureType == Ext.form.Action.SERVER_INVALID) {
									Ext.Msg.alert(alertTitle, "服务器连接异常，请联系管理员！");
								} else {
									if (action.result.msg) {
										Ext.Msg.alert(alertTitle,action.result.msg);
	
									}
								}
							}
						});
						this.disabled = false;//只能点击一次
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
					form2.destroy();
				});
		
		win.show();
	}
 
 
 	function upLoadFile(){
		window.location.href=cqStCorporateRateExcelUrl;
	}
 
 function selabled(){
 	 var _record = cqStCorporateRateGrid.getSelectionModel().getSelections();
 	
        var updatebtn = Ext.getCmp('bascqStCorporateRateEdit');
        var deletebtn = Ext.getCmp('bascqStCorporateRateDelete');
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
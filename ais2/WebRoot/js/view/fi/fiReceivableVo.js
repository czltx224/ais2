	Ext.QuickTips.init();
	var privilege=160; //160
	var comboxPage=comboSize;
	var saveUrl="sys/loadingbrigadeAction!save.action";
	
	var ralaListUrl="fi/fiReceivableVoAction!ralaList.action";
	
	//账龄分析报表
	
Ext.onReady(function(){
	Ext.QuickTips.init();
	var rownum=new Ext.grid.RowNumberer({
        width : 25,
		sortable : true	
	});
 
    var fields=[ 'arrearsFee','creditAmount','lsCreditAmount','creditDay','dayNum','useFee',
    'createTime','extendedDayNum','serviceName','serviceDepartCode','extendsionLimit',
    'serviceDepartName','cusName','cusId','departName','departId','status'];  // 供应商
    
    
    var jsonread= new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'},
                    fields);
                              
	var sm = new Ext.grid.CheckboxSelectionModel();

	
	
	var dataStore = new Ext.data.Store({
                proxy: new Ext.data.HttpProxy({url:sysPath+"/"+ralaListUrl}),
                baseParams:{
                	limit:pageSize,
                	privilege:privilege
                },
                reader:jsonread
    });

	//是否超期
	var isTimeStore=new Ext.data.SimpleStore({
			 auteLoad:true, //此处设置为自动加载
  			 data:[['0','否'],['1','是']],
   			 fields:["id","name"]
	});
	
	 //是否超额
	var isDiffFeeStore=new Ext.data.SimpleStore({
			 auteLoad:true, //此处设置为自动加载
  			 data:[['0','否'],['1','是']],
   			 fields:["id","name"]
	});
    
     //是否超额
	var isDiffTimeFeeStore=new Ext.data.SimpleStore({
			 auteLoad:true, //此处设置为自动加载
  			 data:[['0','否'],['1','是']],
   			 fields:["id","name"]
	});
    
    	//客商Store
	var customerStore = new Ext.data.Store({
		storeId : "customerStore",
		baseParams : {
			limit : pageSize,
			privilege : 61//,
			//filter_EQS_custprop:custprop
		},
		proxy : new Ext.data.HttpProxy({
			url : sysPath + "/sys/customerAction!list.action" 
		}),
		reader : new Ext.data.JsonReader({
					root : 'result',
					totalProperty : 'totalCount'
		}, [
		{name:'cusName'},
		{name:'id'}
		])
	});
    
    //提货方式
	var doGoodsStore = new Ext.data.Store({ 
            storeId:"doGoodsStore",                        
            proxy: new Ext.data.HttpProxy({url:sysPath+"/sys/basDictionaryAction!ralaList.action",method:'post'}),
            baseParams:{
             	privilege:16,
				itemsValue:14,
             	checkItems:'EQS_basDictionaryId'
            },
            reader: new Ext.data.JsonReader({
            root: 'result', totalProperty: 'totalCount'
           }, [  {name:'typeName', mapping:'typeName',type:'string'}           
              ])    
    });
    doGoodsStore.load();
 	
	var bussStore = new Ext.data.Store({ 
            storeId:"bussStore",                        
            proxy: new Ext.data.HttpProxy({url:sysPath+"/sys/basRightDepartAction!ralaList.action",method:'post'}),
            baseParams:{
            	privilege:63,
            	filter_EQL_userId:userId
            },
            reader: new Ext.data.JsonReader({
            root: 'result', totalProperty: 'totalCount'
           }, [{name:'departName', mapping:'departName',type:'string'},
               {name:'departId', mapping:'rightDepartid',type:'string'}             
              ])    
    });

    //var summary = new Ext.ux.grid.GridSummary();
   	
   	var tobar  = new Ext.Toolbar({
		items : ['&nbsp;','<B>所属部门:</B>', {
				xtype : 'combo',
				id:'comboType',
				typeAhead:true,
				selectOnFocus:true,
				hiddenId : 'dictionaryName',
    			hiddenName : 'dictionaryName',
				triggerAction : 'all',
				store : bussStore,
				width:80,
				mode:'local',
				editable : false,
				allowBlank : false,
				listWidth:245,
				minChars : 1,
				allowBlank : true,
				queryParam : 'filter_LIKES_departName',
				emptyText : "请选择部门名称",
				forceSelection : true,
				fieldLabel:'部门名称',
				editable : true,
				pageSize:comboxPage,
				displayField : 'departName',//显示值，与fields对应
				valueField : 'departId',//value值，与fields对应
				name : 'departId',
				enableKeyEvents:true,
	    		listeners : {
	    			render:function(combo){
				 		bussStore.load({
								params : {
									start : 0,
									limit : 500
								},callback :function(v){
									var flag=true;
									for(var i=0;i<bussStore.getCount();i++){
										if(bussStore.getAt(i).get("departId")==bussDepart){
											flag=false;
										}
									}
									if(flag){
										var store=new Ext.data.Record.create([{name:'departName'},{name:'departId'}]);
										var record=new store();
										record.set("departId",bussDepart);
										record.set("departName",bussDepartName);
										bussStore.add(record);		
									}
										combo.setValue(bussDepart);
								}
						});
				 		
				   },
	    		     keyup:function(numberfield, e){
	                     if(e.getKey() == 13 ){
							searchLog();
	                     }
	 				 }
	    		}
		    },'-','&nbsp;','<B>代理:</B>',{
				xtype : 'combo',
				typeAhead : false,
				forceSelection : true,
				minChars : 1,
				selectOnFocus:true,
				listWidth:245,
				triggerAction : 'all',
				store : customerStore,
				pageSize : pageSize,
				queryParam : 'filter_LIKES_cusName',
				id : 'combocus',
				valueField : 'id',
				displayField : 'cusName',
				hiddenName : 'cusName',
				emptyText:'请选择代理名称',
				width : 70,
				enableKeyEvents : true,
				listeners : {
					select:function(v){
						//alert(v.getValue());
					},
	    		     keyup:function(numberfield, e){
	                     if(e.getKey() == 13 ){
							searchLog();
	                     }
	 				 }
	    		}
			},/*'-','&nbsp;','<B>提货方式:</B>',{
				xtype : 'combo',
				id:'doGoods',
				selectOnFocus:true,
				triggerAction : 'all',
				store : doGoodsStore,
				forceSelection : true,
				emptyText : "请选择提货方式",
				//	editable:false,
				fieldLabel:'提货方式',
				mode : "local",//获取本地的值
				displayField : 'typeName',//显示值，与fields对应
				valueField : 'typeName',//value值，与fields对应
				name : 'doGoods',
				width:70,
				enableKeyEvents:true,
				listeners : {
	 				keyup:function(numberfield, e){
	                     if(e.getKey() == 13 ){
							searchLog();
	                     }
	 				}
	 			}
	    	},*/'-','&nbsp;',{
				xtype : 'combo',
				id:'distribution',
				triggerAction : 'all',
				store : isTimeStore,
				forceSelection : true,
				width : 80,
				disabled:true,
				hidden:true,
				selectOnFocus:true,
				emptyText : "请选是否超期",
				//	editable:false,
				mode : "local",//获取本地的值
				displayField : 'name',//显示值，与fields对应
				valueField : 'id',//value值，与fields对应
				name : 'isTime',
				enableKeyEvents:true,
				listeners : {
	 				keyup:function(numberfield, e){
	                     if(e.getKey() == 13 ){
							searchLog();
	                     }
	 				}
	 			}
	    	},{
				xtype : 'combo',
				id:'diffFee',
				triggerAction : 'all',
				store : isDiffFeeStore,
				forceSelection : true,
				width : 80,
				disabled:true,
				hidden:true,
				selectOnFocus:true,
				emptyText : "请选是否超额",
				//	editable:false,
				mode : "local",//获取本地的值
				displayField : 'name',//显示值，与fields对应
				valueField : 'id',//value值，与fields对应
				name : 'diffFee',
				enableKeyEvents:true,
				listeners : {
	 				keyup:function(numberfield, e){
	                     if(e.getKey() == 13 ){
							searchLog();
	                     }
	 				}
	 			}
	    	},{
				xtype : 'combo',
				id:'diffTimeFee',
				triggerAction : 'all',
				store : isDiffTimeFeeStore,
				forceSelection : true,
				width : 80,
				disabled:true,
				hidden:true,
				selectOnFocus:true,
				emptyText : "请选择是否超期超额",
				//	editable:false,
				mode : "local",//获取本地的值
				displayField : 'name',//显示值，与fields对应
				valueField : 'id',//value值，与fields对应
				name : 'diffFee',
				enableKeyEvents:true,
				listeners : {
	 				keyup:function(numberfield, e){
	                     if(e.getKey() == 13 ){
							searchLog();
	                     }
	 				}
	 			}
	    	},{
				xtype:'textfield',
	 	        id : 'itemsValue',
		        name : 'itemsValue',
		        disabled:true,
		        width : 80,
	            blankText:'查询用户数据',
	            enableKeyEvents:true,
	            listeners : {
	 				keyup:function(textField, e){
	                     if(e.getKey() == 13){
	                     	searchLog();
	                     }
	 				}
	 			}
			},'-',{
                xtype : "combo",
    			id:"comboselect",
   				width : 80,
 				triggerAction : 'all',
    			model : 'local',
    			hiddenId : 'checkItems',
    			hiddenName : 'checkItems',
    			name : 'checkItemstext',
  				store : [['', '查询全部'], 
	    			['extendedDayNum', '是否超期'],
	    			['EQL_extendedDayNum', '是否超期超额'],
	    			['diffFee', '是否超额']],
				emptyText : '选择查询类型',
				forceSelection : true,
				listeners : {
					'select' : function(combo, record, index) { 
						if (combo.getValue() == 'extendedDayNum') {
							Ext.getCmp("distribution").enable();
							Ext.getCmp("distribution").show();
							Ext.getCmp("distribution").setValue("");

							Ext.getCmp("itemsValue").setValue("");
							Ext.getCmp("itemsValue").disable();
							Ext.getCmp("itemsValue").hide();
							
							Ext.getCmp("diffFee").disable();
							Ext.getCmp("diffFee").hide();
							Ext.getCmp("diffFee").setValue("");	
							
							Ext.getCmp("diffTimeFee").disable();
							Ext.getCmp("diffTimeFee").hide();
							Ext.getCmp("diffTimeFee").setValue("");	
						}else if (combo.getValue() == 'EQL_extendedDayNum') {
							Ext.getCmp("diffTimeFee").enable();
							Ext.getCmp("diffTimeFee").show();
							Ext.getCmp("diffTimeFee").setValue("");	

							Ext.getCmp("distribution").disable();
							Ext.getCmp("distribution").hide();
							Ext.getCmp("distribution").setValue("");
							
							Ext.getCmp("itemsValue").setValue("");
							Ext.getCmp("itemsValue").disable();
							Ext.getCmp("itemsValue").hide();
							
							Ext.getCmp("diffFee").disable();
							Ext.getCmp("diffFee").hide();
							Ext.getCmp("diffFee").setValue("");	
						}else if(combo.getValue() == 'diffFee'){
							Ext.getCmp("diffFee").enable();
							Ext.getCmp("diffFee").show();
							Ext.getCmp("diffFee").setValue("");	

							Ext.getCmp("distribution").disable();
							Ext.getCmp("distribution").hide();
							Ext.getCmp("distribution").setValue("");
							
							Ext.getCmp("itemsValue").disable();
							Ext.getCmp("itemsValue").hide();
							Ext.getCmp("itemsValue").setValue("");
							
							Ext.getCmp("diffTimeFee").disable();
							Ext.getCmp("diffTimeFee").hide();
							Ext.getCmp("diffTimeFee").setValue("");	
						}else{
							Ext.getCmp("distribution").disable();
							Ext.getCmp("distribution").hide();
							Ext.getCmp("distribution").setValue("");
							
							Ext.getCmp("diffTimeFee").disable();
							Ext.getCmp("diffTimeFee").hide();
							Ext.getCmp("diffTimeFee").setValue("");	
							
							Ext.getCmp("diffFee").disable();
							Ext.getCmp("diffFee").hide();
							Ext.getCmp("diffFee").setValue("");	
							
							Ext.getCmp("itemsValue").enable();
							Ext.getCmp("itemsValue").disable();
							Ext.getCmp("itemsValue").setValue("");	
						}
					}
				}
    		}]});
		
	var recordGrid=new Ext.grid.GridPanel({
	//	renderTo:'recordGrid',
		renderTo:Ext.getBody(),
		id:'userCenter',
		height : Ext.lib.Dom.getViewHeight()-1,
		width : Ext.lib.Dom.getViewWidth()-1,
		autoScroll : true,
		viewConfig : {
			columnsText : "显示的列",
			sortAscText : "升序",
			sortDescText : "降序",
			forceFit : false
		},
		//autoExpandColumn : 1,
		frame : false,
		loadMask : true,
		sm:sm,
	//	plugins : [summary],
		stripeRows : true,
		tbar:['&nbsp;',{text:'<b>导出</b>',iconCls:'sort_down',handler:function() {
                	parent.exportExl(recordGrid);
        } },/*'-','&nbsp;',{
			text : '<b>打印</b>',
			iconCls : 'table',
			id : 'printbtn',
			tooltip : '打印',
			handler : printinfo
		},*/'-','&nbsp;',{
		     text : '<b>查询</b>',
		     id : 'btn',
		     iconCls : 'btnSearch',
			 handler : searchLog
		},'-','&nbsp;',{
   			xtype:'label',
   			id:'showMsg',
   			width:380
   		}],
		columns:[ rownum,sm,
        			{header: '所属部门', dataIndex: 'departName',width:80,sortable : true},
        			{header: '客商名称', dataIndex: 'cusName',width:90,sortable : true},		
        			{header: '欠款初始日期', dataIndex: 'createTime',width:80,sortable : true},
      				{header: '信用额度', dataIndex: 'creditAmount',width:70,sortable : true},
        			{header: '临时信用额度', dataIndex: 'lsCreditAmount',width:80,sortable : true},
        			{header: '欠款总额', dataIndex: 'arrearsFee',width:70,sortable : true},
        			{header: '可用额度', dataIndex: 'useFee',width:70,sortable : true,
        				renderer: function(v, metadata, record, rowIndex, columnIndex, store) {
					 		if(record.get('creditAmount')+record.get('lsCreditAmount')-record.get('arrearsFee')>0){
					 			return record.get('creditAmount')+record.get('lsCreditAmount')-record.get('arrearsFee');
					 		}else{
						 		return 0;
					 		}
	      				}
        			},
			        {header: '信用天数', dataIndex: 'creditDay',width:60,sortable : true},
			        {header: '欠款天数', dataIndex: 'dayNum',width:70,sortable : true},
			        {header: '临时延期天数', dataIndex: 'extendsionLimit',width:90,sortable : true},
					{header: '超期天数', dataIndex: 'extendedDayNum',width:60,sortable : true},
        			{header: '客服员', dataIndex: 'serviceName',width:60,sortable : true},
        			{header: '所属部门ID', dataIndex: 'serviceDepartCode',hidden : true,width:60,sortable : true},
        			{header: '客商ID', dataIndex: 'cusId',width:60,hidden:true,sortable : true},
        			{header: '状态', dataIndex: 'status',width:60,sortable : true,
        				renderer:function(v){
				        		if(v=='1'){ 
				        			//return '<span style="color:green">正常</span>';
				        			return '正常';
				        		}else if(v=='2'){
				        			return '<span style="color:brown">超期</span>';
				        		}else if(v=='3'){
				        			return '<span style="color:orange">超额</span>';
				        		}else{
				        			return '<span style="color:red">超期超额</span>';
				        		}
				        	}},
        			{header: '客服部门', dataIndex: 'serviceDepartName',width:90,sortable : true}],
			store : dataStore,
			listeners: {
              render: function(){
                  tobar.render(this.tbar);
              }
            },
			bbar : new Ext.PagingToolbar({
					pageSize : pageSize, 
					store : dataStore,
					displayInfo : true,
					displayMsg : '显示第 {0} 条到 {1} 条记录，共 {2} 条',
					emptyMsg : "没有记录信息显示"
			})
	});

	function searchLog() {
			Ext.getCmp('showMsg').getEl().update('<span style="color:red"></span>');
			var comboTypeBoolean =Ext.getCmp('comboType').validate();
			if(!comboTypeBoolean){
				Ext.Msg.alert(alertTitle,"请选择所属部门后，再查询",function() {
					Ext.getCmp('comboType').markInvalid("必须填写所属部门");
					Ext.getCmp('comboType').focus();									
												});
				return false;
			}
			var comboType=Ext.getCmp("comboType").getValue();
			
			var combocus=Ext.getCmp("combocus").getValue();

			dataStore.baseParams={
					privilege:privilege,
					limit : pageSize	
			};

			if (Ext.getCmp('comboselect').getValue() == 'extendedDayNum'){
				var distribution=Ext.getCmp("distribution").getValue();
				if(distribution=='0'){
	    			Ext.apply(dataStore.baseParams, {
						filter_EQL_cusId:combocus,
						filter_EQL_departId:comboType,
						filter_EQL_extendedDayNum:0
						
					});
				}else if(distribution=='1'){
					Ext.apply(dataStore.baseParams, {
						filter_EQL_cusId:combocus,
						filter_EQL_departId:comboType,
						filter_GTL_extendedDayNum:0
					});
				}else{
					Ext.apply(dataStore.baseParams, {
						filter_EQL_cusId:combocus,
						filter_EQL_departId:comboType
					});
				}
			}else if(Ext.getCmp('comboselect').getValue() == 'distribution'){
				Ext.apply(dataStore.baseParams, {
						filter_EQL_cusId:combocus,
						filter_EQL_departId:comboType,
		    		    filter_EQL_extendedDayNum:Ext.getCmp('itemsValue').getValue()
				});
			}else if(Ext.getCmp('comboselect').getValue() == 'diffFee'){
				var diffFee=Ext.getCmp("diffFee").getValue();
				if(diffFee=='1'){
	    			Ext.apply(dataStore.baseParams, {
						filter_EQL_cusId:combocus,
						filter_EQL_departId:comboType,
		    		    filter_GEL_status:3
					});
				}else if(diffFee=='0'){
					Ext.apply(dataStore.baseParams, {
						filter_EQL_cusId:combocus,
						filter_EQL_departId:comboType,
						 filter_LEL_status:2
					});
				}else{
					Ext.apply(dataStore.baseParams, {
						filter_EQL_cusId:combocus,
						filter_EQL_departId:comboType
					});
				}
			}else if(Ext.getCmp('comboselect').getValue() == 'EQL_extendedDayNum'){
				var diffTimeFee=Ext.getCmp("diffTimeFee").getValue();
				if(diffTimeFee==1){
					Ext.apply(dataStore.baseParams, {
						filter_EQL_cusId:combocus,
						filter_EQL_departId:comboType,
		    		    filter_EQL_status:4
					});
				}else{
					Ext.apply(dataStore.baseParams, {
						filter_EQL_cusId:combocus,
						filter_EQL_departId:comboType,
		    		    filter_EQL_status:4
					});
				}
				
			}else{
				Ext.apply(dataStore.baseParams, {
						filter_EQL_cusId:combocus,
						filter_EQL_departId:comboType
				});
			}
				
			dataStore.reload({
					params : {
						start : 0,
						limit : pageSize
					}
			});
		
	}

});


/**
 * 导出按钮事件
 */
function exportinfo() {
	Ext.Msg.alert("提示", "正在开发中...");
}

/**
 * 打印按钮事件
 */
function printinfo() {
	Ext.Msg.alert("提示", "正在开发中...");
}

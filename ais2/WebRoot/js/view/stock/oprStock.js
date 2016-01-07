//库存管理JS
var privilege=115;
var gridSearchUrl="stock/oprStockAction!findStock.action";//库存查询地址 
var toExceptionStockUrl="stock/oprStockAction!toExceptionStock.action";//转异常库存地址
var searchCusNameUrl='sys/customerAction!list.action';//查询代理公司地址
var defaultWidth=80;
var searchWidth=80; 

var  fields=[{name:"id",mapping:'ID'},
			{name:"dno",mapping:'DNO'},
     		{name:'distributionMode',mapping:'DISTRIBUTIONMODE'},
     		{name:"takeMode",mapping:'TAKEMODE'},
     		{name:'cpName',mapping:'CPNAME'},
     		{name:'consignee',mapping:'CONSIGNEE'},
     		{name:'addr',mapping:'ADDR'},
     		{name:'realGoWhere',mapping:'REALGOWHERE'},
     		{name:'flightMainNo',mapping:'FLIGHTMAINNO'},
     		{name:'subNo',mapping:'SUBNO'},
     		{name:'flightNo',mapping:'FLIGHTNO'},
     		{name:'createTime',mapping:'CREATETIME'},
     		{name:'enterStockTime',mapping:'ENTERSTOCKTIME'},
     		{name:'stockTime',mapping:'STOCKTIME'},
     		{name:'isException',mapping:'ISEXCEPTION'},
     		{name:'goods',mapping:'GOODS'},
     		{name:'piece',mapping:'PIECE'},
     		{name:'weight',mapping:'WEIGHT'},
     		{name:'bulk',mapping:'BULK'},
     		{name:'departId',mapping:'DEPARTID'},'GOODSSTATUS'];
	//代理公司
		cpNameStore= new Ext.data.Store({ 
			storeId:"cpNameStore",
			proxy:new Ext.data.HttpProxy({url:sysPath+"/"+searchCusNameUrl}),
			reader:new Ext.data.JsonReader({
	                    root:'result', totalProperty:'totalCount'
	        },[
        	{name:'cusName',mapping:'cusName'},
        	{name:'cusId',mapping:'id'}
        	])
		});
		
		isExceptionStore= new Ext.data.SimpleStore({
			 autoLoad:true, //此处设置为自动加载
  			  data:[['','全部'],['0','否'],['1','是']],
   			 fields:["isExceptionId","isExceptionName"]
		});
		
	var tbar=[{text:'<b>转异常库存</b>',iconCls:'groupPass',tooltip : '异常库存',handler:function() {
                	var overmemo =Ext.getCmp('overmemoCenter');
			var _records = overmemo.getSelectionModel().getSelections();
	
					if (_records.length < 1) {
						Ext.Msg.alert(alertTitle, "请选择您要操作的行！");
						return false;
					} 

					var ids="";
					for(var i=0;i<_records.length;i++){
						if(_records[i].data.status==1){
							Ext.Msg.alert(alertTitle, "您选择的第几"+(i+1)+"票货物已经是异常库存！");
							return;
						}else if(_records[i].data.piece<1){
							Ext.Msg.alert(alertTitle, "您选择的第几"+(i+1)+"票货物没有库存！");
							return;
						}
						 ids=ids+_records[i].data.id+',';
					}
					Ext.Msg.confirm(alertTitle, "确定要设置这"+_records.length+"条记录为异常库存吗？", function(
									btnYes) {
								if (btnYes == 'yes' || btnYes == 'ok'
										|| btnYes == true) {
									savestockChange(ids);
								}
							});
            } },'-',
	 	{text:'<b>导出</b>',iconCls:'sort_down',tooltip : '导出数据',
	 		handler:function(){
	 					parent.exportExl(overmemoGrid);
	 		}
	 	}
	 		,'-',
	{xtype:'textfield',blankText:'查询数据',id:'searchContent', enableKeyEvents:true,listeners : {
	 		
	 		keyup:function(textField, e){

                     if(e.getKey() == 13){

                     	searchExceptionStock();

                     }
	 		}
	 	}},'-',{ 
	 	
    					xtype : "combo",
    					width : 60,
    					triggerAction : 'all',
    					id:'searchSelectBox',
    					model : 'local',
    					hiddenId : 'checkItems',
    					hiddenName : 'checkItems',
    					name : 'checkItemstext',
    					store : [['LIKES_goods', '品名'],
    							['LIKES_F_addr','地址'],
    							['LIKES_F_flightNo','航班号'],
    							['F_subNo','分单号'],
    							['LIKES_F_flightMainNo','主单号'],
    							['LIKES_F_goodsStatus','货物状态']],
    							
    					emptyText : '选择查询方式',
    					editable : false,
    					forceSelection : true,
    					listeners : {
    						'select' : function(combo, record, index) {
								if(Ext.getCmp("searchContent").getValue().length>0){
									searchExceptionStock();
								}
    						}
    					}
    					
    		},'-','是否异常:',{
    			xtype : "combo",
				width : searchWidth+20,
				editable:true,
				triggerAction : 'all',
				store :isExceptionStore,
				mode : "local",// 从本地载值
				valueField : 'isExceptionId',// value值，与fields对应
				displayField : 'isExceptionName',// 显示值，与fields对应
			    name : 'isException',
			    id:'searchisException', 
				editable : true,
				enableKeyEvents:true,
				listeners : {
					'select' : function(combo, record, index) {
						if(combo.getValue().length>0){
							searchExceptionStock();
						}
					}
				}
    		}
	 	];
	 	
	 	var queryTbar=new Ext.Toolbar([
	 	'代理公司:',{xtype : "combo",
				width : searchWidth+20,
				editable:true,
				triggerAction : 'all',
				typeAhead : true,
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
                     	searchcqCorporateRate();
                     }
	 		}
	 	
	 	}},	'-','收货人:',{xtype:'textfield',id:'searchConsignee',width : searchWidth, enableKeyEvents:true,listeners : {
	 		keyup:function(textField, e){
                     if(e.getKey() == 13){
                     	searchExceptionStock();
                     }
	 		}
	 	}},'-','去向:',{xtype:'textfield',id:'searchGowhere',width : searchWidth,enableKeyEvents:true,listeners : {
	 		keyup:function(textField, e){
                     if(e.getKey() == 13){
                     	searchExceptionStock();
                     }
	 		}
	 	}},'-','配送单号:',{xtype:'textfield',id:'searchDno', width : searchWidth,enableKeyEvents:true,listeners : {
	 		keyup:function(textField, e){
                     if(e.getKey() == 13){
                     	searchExceptionStock();
                     }
	 		}
	 	}},'-',{text : '<b>查询</b>',id : 'btn',iconCls : 'btnSearch',
    					handler : searchExceptionStock
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
   
   var sm = new Ext.grid.CheckboxSelectionModel({});
	var rowNum = new Ext.grid.RowNumberer({
        header:'序号', width:30
    });
    overmemoGrid = new Ext.grid.GridPanel({
        renderTo: Ext.getBody(),
        id:'overmemoCenter',
        height:Ext.lib.Dom.getViewHeight()-1,		
        width:Ext.lib.Dom.getViewWidth()-1,
        viewConfig : {
			columnsText : "显示的列",
			sortAscText : "升序",
			sortDescText : "降序"
			//forceFit : true
		},
		autoScroll : true,
		//autoSizeColumns: true,
		frame : false,
		loadMask : true,
		stripeRows : true,
        sm:sm,
        cm:new Ext.grid.ColumnModel([rowNum,sm,
       			{header: '库存ID', dataIndex: 'id',width:defaultWidth,sortable : true,hidden:true},
       			{header: '配送单号', dataIndex: 'dno',width:defaultWidth,sortable : true},
       			{header: '货物状态', dataIndex: 'GOODSSTATUS',width:defaultWidth,sortable : true},
       			{header: '品名', dataIndex: 'goods',width:defaultWidth,sortable : true},
       			{header: '件数', dataIndex: 'piece',width:defaultWidth,sortable : true},
 				{header: '重量', dataIndex: 'weight',width:defaultWidth,sortable : true},
 				{header: '体积', dataIndex: 'bulk',width:defaultWidth,sortable : true},
       			{header: '配送方式', dataIndex: 'distributionMode',width:defaultWidth,sortable : true},
       			{header: '提送方式', dataIndex: 'takeMode',width:defaultWidth,sortable : true},
       			{header: '代理公司', dataIndex: 'cpName',width:defaultWidth,sortable : true},
 				{header: '收货人', dataIndex: 'consignee',width:defaultWidth,sortable : true},
 				{header: '收货人地址', dataIndex: 'addr',width:defaultWidth,sortable : true},
       			{header: '去向', dataIndex: 'realGoWhere',width:defaultWidth,sortable : true},
       			{header: '主单号', dataIndex: 'flightMainNo',width:defaultWidth,sortable : true},
       			{header: '分单号', dataIndex: 'subNo',width:defaultWidth,sortable : true},
       			{header: '航班号', dataIndex: 'flightNo',width:defaultWidth,sortable : true},
 				{header: '是否异常', dataIndex: 'isException',width:defaultWidth,sortable : true
 					,renderer:function(v){
 									return v==1?'是':'否';
			        		}
 				},
       			{header: '传真时间', dataIndex: 'createTime',width:defaultWidth,sortable : true},
 				{header: '入库时间', dataIndex: 'enterStockTime',width:defaultWidth,sortable : true},
 				{header: '库存时间', dataIndex: 'stockTime',width:defaultWidth,sortable : true}
       			
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
            store: dateStore, 
            displayInfo: true,
            displayMsg: '显示第 {0} 条到 {1} 条记录，共 {2} 条', 
            emptyMsg: "没有记录信息显示"
        })
    });
   // searchExceptionStock();
  });
    
    
	
   function searchExceptionStock() {
		dateStore.baseParams ={privilege:privilege,limit : pageSize,filter_s_departId:bussDepart};
				var searchCpName = Ext.getCmp('searchCpName').getRawValue();
				var searchConsignee = Ext.getCmp('searchConsignee').getValue();
				var searchGowhere = Ext.getCmp('searchGowhere').getValue();
				var searchDno = Ext.get('searchDno').dom.value;
				var searchisException = Ext.getCmp('searchisException').getValue();
				
				if(null!=searchDno && searchDno.length>1){
					 Ext.apply(dateStore.baseParams, {
					 	filter_F_d_no:searchDno
					 });
				}else{
		 			 Ext.apply(dateStore.baseParams, {
	    					filter_LIKES_F_cpName:searchCpName,
	    					filter_LIKES_F_consignee:searchConsignee,
	    					filter_LIKES_realGoWhere:searchGowhere,
	    					filter_isException:searchisException,
							filter_checkItems : Ext.get("checkItems").dom.value,
							filter_itemsValue : Ext.get("searchContent").dom.value
	    			});
	    		}
		dataReload();
	}

function savestockChange(ids,status) {
					var form = new Ext.form.FormPanel({
								labelAlign : 'left',
								frame : true,
								url:sysPath+'/'+toExceptionStockUrl,
								baseParams:{
				    						stockIds : ids},
								bodyStyle : 'padding:5px 5px 0',
								width : 400,
								defaults : {allowBlank : false},  
								reader : new Ext.data.JsonReader({
			            root: 'result', totalProperty: 'totalCount'
			        }, fields),
					labelAlign : "right",
				
						items : [{
									layout : 'form',
									items : [{
											xtype : 'textarea',
											labelAlign : 'left',
											allowBlank : false,
											fieldLabel : '备注<font color="red"><B>*</B></font>',
											name : 'remark',
											height:40,
											maxLength:200,
											anchor : '95%'
										}]
											
								}]
							});
		
		var win = new Ext.Window({
		title : '库存转化备注填写',
		width : 400,
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
								+ '/'+toExceptionStockUrl,
								params:{privilege:privilege,limit : pageSize},
						waitMsg : '正在保存数据...',
						success : function(form, action) {
							win.hide(), Ext.Msg.alert(alertTitle,"转换成功！", function() {
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
				limit:pageSize
				}
			})
		}
//车辆管理js
var privilege=20;//权限参数
var gridSearchUrl="bascar/basCarAction!ralaList.action";//车辆查询地址
var saveUrl="bascar/basCarAction!save.action";//车辆保存地址
var delUrl="bascar/basCarAction!delete.action";//车辆删除地址
var departUrl='sys/departAction!findAll.action';//部门查询地址
var dictionaryUrl='sys/dictionaryAction!ralaList.action';//数据字典地址

var  fields=[{name:"id",mapping:'id'},
     		{name:"carCode",mapping:'carCode'},
     		{name:'departId',mapping:'departId'},
     		{name:'departName',mapping:'departName'},
     		{name:'typeCode',mapping:'typeCode'},
     		{name:'cartrunkNo',mapping:'cartrunkNo'},
     		{name:'engineNo',mapping:'engineNo'},
     		{name:'buyDate',mapping:'buyDate'},
     		{name:'loadWeight',mapping:'loadWeight'},
     		{name:'maxloadWeight',mapping:'maxloadWeight'},
     		{name:'loadCube',mapping:'loadCube'},
     		{name:'maxloadCube',mapping:'maxloadCube'},
     		{name:'remark',mapping:'remark'},
     		{name:'type',mapping:'type'},
     		{name:'property',mapping:'property'},
     		{name:'comfirtBy',mapping:'comfirtBy'},
     		{name:'comfirtDate',mapping:'comfirtDate'},
     		{name:'createName',mapping:'createName'},
     		{name:'createTime',mapping:'createTime'},
     		{name:'updateName',mapping:'updateName'},
     		{name:'updateTime',mapping:'updateTime'},
     		{name:'ts',mapping:'ts'},
     		{name:'gpsNo',mapping:'gpsNo'},
     		{name:'carBrand',mapping:'carBrand'}];

     	localType=new Ext.data.SimpleStore({
			 autoLoad:true, //此处设置为自动加载
  			  data:[['1','自有车辆'],['2','外包车'],['3','临时外包车']],
   			 fields:["typeId","typeName"]
		});
		
		localProperty=new Ext.data.SimpleStore({
			 autoLoad:true, //此处设置为自动加载
  			  data:[['1','正常'],['2','黑车']],
   			 fields:["propertyId","propertyName"]
		});
		
	    departStore = new Ext.data.Store({
	        storeId:"departStore",
			proxy:new Ext.data.HttpProxy({url:sysPath+"/"+departUrl}),
			baseParams:{privilege:53},
			reader:new Ext.data.JsonReader({
	                    root:'result',
	                    totalProperty:'totalCount'
	        },[
        	{name:'departId'},
        	{name:'departName'}
        	])
        	
		});
		//车型21
		carTypeStore	= new Ext.data.Store({ 
			storeId:"carTypeStore",
			baseParams:{filter_EQL_basDictionaryId:21,privilege:16},
			proxy:new Ext.data.HttpProxy({url:sysPath+"/"+dictionaryUrl}),
			reader:new Ext.data.JsonReader({
	                    root:'result',
	                    totalProperty:'totalCount'
	        },[
        	{name:'typeCode',mapping:'typeCode'},
        	{name:'typeName',mapping:'typeName'}
        	])
		});
	var tbar=[{text:'<b>导出</b>',iconCls:'sort_down',handler:function() {
                	parent.exportExl(carGrid);
        } },'-',{text:'<b>新增</b>',iconCls:'userAdd',id:'basCarAdd',tooltip : '新增车辆',handler:function() {
                	saveCar(null);
            } },'-',
	 	{text:'<b>修改</b>',iconCls:'userEdit',id:'basCarEdit',disabled:true,tooltip : '修改车辆',handler:function(){
	 	var car =Ext.getCmp('carCenter');
		var _records = car.getSelectionModel().getSelections();
		
						if (_records.length < 1) {
							Ext.Msg.alert(alertTitle, "请选择您要修改的行！");
							return false;
						} else if (_records.length > 1) {
							Ext.Msg.alert(alertTitle, "一次只能修改一行！");
							return false;
						}
	 	saveCar(_records);}},'-',
	 	{text:'<b>删除</b>',iconCls:'userDelete',id:'basCarDelete',disabled:true,tooltip : '删除车辆',handler:function(){
	 		var car =Ext.getCmp('carCenter');
			var _records = car.getSelectionModel().getSelections();
	
					if (_records.length < 1) {
						Ext.Msg.alert(alertTitle, "请选择您要删除的行！");
						return false;
					} 
					var ids="";
					for(var i=0;i<_records.length;i++){
					
						 ids=ids+_records[i].data.id+',';
					}
					Ext.Msg.confirm(alertTitle, "确定要删除"+_records.length+"条记录吗？", function(
									btnYes) {
								if (btnYes == 'yes' || btnYes == 'ok'
										|| btnYes == true) {
									
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
    	{
			xtype : 'combo',
			id:'comboType',
			hidden : true,
			triggerAction : 'all',
			store : localType,
			allowBlank : true,
			emptyText : "请选择车源车源",
			forceSelection : true,
			fieldLabel:'车源',
			editable : true,
			mode : "local",//获取本地的值
			displayField : 'typeName',//显示值，与fields对应
			valueField : 'typeId',//value值，与fields对应
			name : 'type',
			anchor : '95%'
		},'-',
		 {
			xtype : 'combo',
			typeAhead : true,
			id:'comboDepart',
			hidden : true,
			queryParam : 'filter_LIKES_departName',
			triggerAction : 'all',
			store : departStore,
			pageSize : pageSize,
			emptyText : "请选择登记网点",
			forceSelection : true,
			fieldLabel : '登记网点',
			minChars : 0,
			mode : "local",//获取本地的值
			valueField : 'departId',//value值，与fields对应
			displayField : 'departName',//显示值，与fields对应
			hiddenName:'departId',
			anchor : '95%'
		},
		{xtype:'textfield',blankText:'查询数据',id:'searchContent', 
			enableKeyEvents:true,listeners : {
	 			keyup:function(textField, e){
                     if(e.getKey() == 13){
                     	searchCar();
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
    							['LIKES_carCode', '车牌号'],
    							['LIKES_typeCode','车型'],
    							['LIKES_cartrunkNo','车架号'],
    							['LIKES_engineNo','发动机'],
    							['LIKES_remark','备注'],
    							['EQS_type','车源'],
    							['EQS_departId','登记网点'],
    							['EQD_buyDate', '购买时间'],
    						    ['EQD_comfirtDate', '打入时间']],
    							
    					emptyText : '选择类型',
    					editable : false,
    					forceSelection : true,
    					listeners : {
    						
    						'select' : function(combo, record, index) {
    							if(combo.getValue()==''){searchCar();}
    							if (combo.getValue() == 'EQD_buyDate') {
    								Ext.getCmp("startDate").enable();
    								Ext.getCmp("startDate").show();

    								Ext.getCmp("endDate").enable();
    								Ext.getCmp("endDate").show();
    								
    								Ext.getCmp("comboType").setValue("");
    								Ext.getCmp("comboType").hide();
    								
    								Ext.getCmp("comboDepart").setValue("");
    								Ext.getCmp("comboDepart").hide();
    								
    								Ext.getCmp("searchContent").disable();
    								Ext.getCmp("searchContent").hide();
    								Ext.getCmp("searchContent").setValue("");
    							}else if(combo.getValue() == 'EQD_comfirtDate') {
    								Ext.getCmp("startDate").enable();
    								Ext.getCmp("startDate").show();

    								Ext.getCmp("endDate").enable();
    								Ext.getCmp("endDate").show();
    								
    								Ext.getCmp("comboType").setValue("");
    								Ext.getCmp("comboType").hide();
    								
    								Ext.getCmp("comboDepart").setValue("");
    								Ext.getCmp("comboDepart").hide();
    								
    							 	Ext.getCmp("searchContent").disable();
    								Ext.getCmp("searchContent").hide();
    								Ext.getCmp("searchContent").setValue("");
    								
    						 	}else if(combo.getValue() == 'EQS_type') {
    								Ext.getCmp("startDate").hide();
    								Ext.getCmp("startDate").setValue("");

    								
    								Ext.getCmp("endDate").hide();
    								Ext.getCmp("endDate").setValue("");
    								
    							 	Ext.getCmp("searchContent").disable();
    								Ext.getCmp("searchContent").hide();
    								Ext.getCmp("searchContent").setValue("");
    								
    								Ext.getCmp("comboDepart").setValue("");
    								Ext.getCmp("comboDepart").hide();
    								
    								Ext.getCmp("comboType").setValue("");
    								Ext.getCmp("comboType").show();
    						 	}else if(combo.getValue() == 'EQS_departId') {
    						 		departStore.reload({
									params : {
										start : 0,
										limit : pageSize
									}});
    								Ext.getCmp("startDate").hide();
    								Ext.getCmp("startDate").setValue("");

    								
    								Ext.getCmp("endDate").hide();
    								Ext.getCmp("endDate").setValue("");
    								
    							 	Ext.getCmp("searchContent").disable();
    								Ext.getCmp("searchContent").hide();
    								Ext.getCmp("searchContent").setValue("");
    								
    								
    								Ext.getCmp("comboType").setValue("");
    								Ext.getCmp("comboType").hide();
    								
    								Ext.getCmp("comboDepart").setValue("");
    								Ext.getCmp("comboDepart").show();
    						 	}
    						 	else{
    					         	
    						  		Ext.getCmp("startDate").hide();
    								Ext.getCmp("startDate").setValue("");

    								
    								Ext.getCmp("endDate").hide();
    								Ext.getCmp("endDate").setValue("");
    								
    								Ext.getCmp("comboType").setValue("");
    								Ext.getCmp("comboType").hide();
    								
    						      	Ext.getCmp("searchContent").enable();
    								Ext.getCmp("searchContent").show();
    								
    								Ext.getCmp("comboDepart").setValue("");
    								Ext.getCmp("comboDepart").hide();
    								if(Ext.getCmp("searchContent").getValue().length>0){
    									searchCar();
    								}
    							
    							}
    						}
    					}
    					
    				},'-',{text : '<b>搜索</b>',id : 'btn',iconCls : 'btnSearch',
    					handler : searchCar
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
    carGrid = new Ext.grid.GridPanel({
        renderTo: Ext.getBody(),
        id:'carCenter',
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
        {header: '车牌号', dataIndex: 'carCode',width:60,sortable : true},
       			{header: '登记网点名称', dataIndex: 'departName',width:100,sortable : true},
        		{header: '车型', dataIndex: 'typeCode',width:60,sortable : true},
 				{header: '车架号', dataIndex: 'cartrunkNo',width:60,sortable : true},
 				{header: '发动机', dataIndex: 'engineNo',width:60,sortable : true},
 				{header: '购买时间', dataIndex: 'buyDate',width:100,sortable : true},
 				{header: '理论载重', dataIndex: 'loadWeight',width:60,sortable : true},
 				{header: '实际载重', dataIndex: 'maxloadWeight',width:60,sortable : true},
 				{header: '理论方数', dataIndex: 'loadCube',width:60,sortable : true},
 				{header: '实际方数', dataIndex: 'maxloadCube',width:60,sortable : true},
 				{header: '车辆品牌', dataIndex: 'carBrand',width:60,sortable : true},
 				{header: '打入人', dataIndex: 'comfirtBy',width:60,sortable : true,hidden:true},
 				{header: '打入时间', dataIndex: 'comfirtDate',width:100,sortable : true,hidden:true},
 				{header: '车源', dataIndex: 'type',width:60,sortable : true
 						,renderer:function(v){
 									return v==1?'自有车辆':(v==2?'外包车':'临时外包车');
			        				//if(v==1) return '自有车辆';
			        				//else if(v==2) return '外包车';
			        				//else if(v==3) return '临时外包车';
			        		}
 				},
 				{header: '属性', dataIndex: 'property',width:60,sortable : true
 					,renderer:function(v){
			        					return v==1?'正常':'黑车';
			        		}
 				},
 				{header: 'GPS编码', dataIndex: 'gpsNo',width:60,sortable : true},
 				{header: '备注', dataIndex: 'remark',width:60,sortable : true}
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
   
	//searchCar();
	
	    carGrid.on('click', function() {
	       selabled();
	     	
	    });
		carGrid.on('rowdblclick',function(grid,index,e){
		var _records = carGrid.getSelectionModel().getSelections();
				
				if (_records.length ==1) {
					saveCar(_records);
				}
			 	
		});
    });
    
    
	
   function searchCar() {
		dateStore.proxy = new Ext.data.HttpProxy({
					method : 'POST',
					url : sysPath
								+ "/"+gridSearchUrl,
						params:{privilege:privilege,limit : pageSize}
				});
			 if (Ext.getCmp('searchSelectBox').getValue() == 'EQD_buyDate') {
					 Ext.apply(dateStore.baseParams, {
    						filter_GED_buyDate :(!Ext.getCmp("startDate").hidden?(Ext.get("startDate").dom.value=="开始时间"?"":Ext.get("startDate").dom.value):""),
    						filter_LED_buyDate : (!Ext.getCmp("endDate").hidden?(Ext.get("endDate").dom.value=="结束时间"?"":Ext.get("endDate").dom.value):""),
    						checkItems : '',
    						itemsValue : '',
    						privilege:privilege,
							start:0
    					});
    			

    		} else if (Ext.getCmp('searchSelectBox').getValue() == 'EQD_comfirtDate') {
    				 Ext.apply(dateStore.baseParams, {
    						filter_GED_comfirtDate:(!Ext.getCmp("startDate").hidden?(Ext.get("startDate").dom.value=="开始时间"?"":Ext.get("startDate").dom.value):""),
    						filter_LED_comfirtDate : (!Ext.getCmp("endDate").hidden?(Ext.get("endDate").dom.value=="结束时间"?"":Ext.get("endDate").dom.value):""),
    						checkItems : '',
    						itemsValue : '',
    						privilege:privilege,
							start:0
    					});
    		} else if (Ext.getCmp('searchSelectBox').getValue() == 'EQS_type') {
    			var typeValue=Ext.getCmp("comboType").getValue();
    			Ext.apply(dateStore.baseParams, {
    						checkItems : Ext.get("checkItems").dom.value,
    						itemsValue : typeValue
    					});
    		}else if (Ext.getCmp('searchSelectBox').getValue() == 'EQS_departId') {
    			var depart=Ext.getCmp("comboDepart").getValue();
    			Ext.apply(dateStore.baseParams, {
    						checkItems : Ext.get("checkItems").dom.value,
    						itemsValue : depart
    					});
    		}else{	
				dateStore.baseParams = {
				checkItems : Ext.get("checkItems").dom.value,
				privilege:privilege,
				start:0,
				itemsValue : Ext.get("searchContent").dom.value
		}
		}
		
		var editbtn = Ext.getCmp('basCarEdit');
		var deletebtn = Ext.getCmp('basCarDelete');

		editbtn.setDisabled(true);
		deletebtn.setDisabled(true);

		dataReload();
		
		
	}
function saveCar(_records) {
				
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
																fieldLabel : '车牌号<font style="color:red;">*</font>',
																name : 'carCode',
																maxLength:20,
																allowBlank : false,
																blankText : "车牌号不能为空",
																maxText:'车牌号过长',
																anchor : '95%'
															       
															       }
																,  {
																	xtype : 'combo',
																	typeAhead : true,
																	id:'mycombo',
																	queryParam : 'filter_LIKES_departName',
																	triggerAction : 'all',
																	store : departStore,
																	pageSize : pageSize,
																	allowBlank : false,
																	emptyText : "请选择登记网点",
																	forceSelection : true,
																	selectOnFocus:true,
																	resizable:true,
																	fieldLabel : '登记网点<font style="color:red;">*</font>',
																	minChars : 0,
																	mode : "remote",//从服务器端加载值
																	valueField : 'departId',//value值，与fields对应
																	displayField : 'departName',//显示值，与fields对应
																	hiddenName:'departId',
																	anchor : '95%'
																},  {
																	xtype : 'combo',
																	typeAhead : true,
																	queryParam : 'filter_LIKES_typeName',
																	triggerAction : 'all',
																	store : carTypeStore,
																	pageSize : pageSize,
																	allowBlank : false,
																	emptyText : "请选择车型",
																	forceSelection : true,
																	selectOnFocus:true,
																	resizable:true,
																	fieldLabel : '车型<font style="color:red;">*</font>',
																	minChars : 0,
																	mode : "remote",//从服务器端加载值
																	valueField : 'typeName',//value值，与fields对应
																	displayField : 'typeName',//显示值，与fields对应
																	name:'typeCode',
																	anchor : '95%'
																},  {
																	xtype : 'textfield',
																	labelAlign : 'left',
																	fieldLabel : '车架号<font style="color:red;">*</font>',
																	name : 'cartrunkNo',
																	maxLength:25,
																	anchor : '95%'
																},  {
																	xtype : 'textfield',
																	labelAlign : 'left',
																	fieldLabel : '发动机<font style="color:red;">*</font>',
																	name : 'engineNo',
																	maxLength:20,
																	anchor : '95%'
																},  {
																	
																	labelAlign : 'left',
																	fieldLabel : '购买时间<font style="color:red;">*</font>',
																	xtype:'datefield',
																	format:'Y-m-d',
																	blankText : "购买时间不能为空！",
																	editable : false,
																	allowBlank : false,
																	name : 'buyDate',
																	anchor : '95%'
																},{
																	xtype : 'combo',
																	triggerAction : 'all',
																	store : localType,
																	allowBlank : false,
																	emptyText : "请选择车源车源",
																	forceSelection : true,
																	fieldLabel:'车源<font style="color:red;">*</font>',
																	editable : false,
																	mode : "local",//获取本地的值
																	displayField : 'typeName',//显示值，与fields对应
																	valueField : 'typeId',//value值，与fields对应
																	hiddenName : 'type',
																	anchor : '95%'
																},{
																	xtype : 'combo',
																	triggerAction : 'all',
																	store : localProperty,
																	allowBlank : false,
																	emptyText : "请选择属性",
																	forceSelection : true,
																	fieldLabel:'属性<font style="color:red;">*</font>',
																	editable : false,
																	mode : "local",//获取本地的值
																	displayField : 'propertyName',//显示值，与fields对应
																	valueField : 'propertyId',//value值，与fields对应
																	hiddenName : 'property',
																	anchor : '95%'
																}]

													}, {
														columnWidth : .5,
														layout : 'form',
														items : [{
																	xtype : 'numberfield',
																	labelAlign : 'left',
																	fieldLabel : '理论载重<font style="color:red;">*</font>',
																	blankText : "理论载重不能为空！",
																	allowBlank : false,
																	nanText:'请输入数字',
																	maxLength:10,
																	name : 'loadWeight',
																	anchor : '95%'
																}, {
																	xtype : 'numberfield',
																	labelAlign : 'left',
																	allowBlank : false,
																	fieldLabel : '实际载重<font style="color:red;">*</font>',
																	blankText : "实际载重不能为空！",
																	nanText:'请输入数字',
																	maxLength:10,
																	name : 'maxloadWeight',
																	anchor : '95%'
																}, {
																	xtype : 'numberfield',
																	labelAlign : 'left',
																	allowBlank : false,
																	fieldLabel : '理论方数<font style="color:red;">*</font>',
																	blankText : "理论方数不能为空！",
																	nanText:'请输入数字',
																	maxLength:10,
																	name : 'loadCube',
																	anchor : '95%'
																}, {
																	xtype : 'numberfield',
																	labelAlign : 'left',
																	allowBlank : false,
																	fieldLabel : '实际方数<font style="color:red;">*</font>',
																	blankText : "实际方数不能为空！",
																	nanText:'请输入数字',
																	maxLength:10,
																	name : 'maxloadCube',
																	anchor : '95%'
																},{
																	xtype : 'textfield',
																	labelAlign : 'left',
																	fieldLabel : 'GPS编码',
																	maxLength:20,
																	name : 'gpsNo',
																	anchor : '95%'
																}, {
																	xtype : 'textfield',
																	labelAlign : 'left',
																	fieldLabel : '车辆品牌',
																	maxLength:10,
																	name : 'carBrand',
																	anchor : '95%'
																},{
																	xtype : 'textfield',
																	labelAlign : 'left',
																	fieldLabel : '打入人',
																	maxLength:10,
																	disabled : true,
																	name : 'comfirtBy',
																	anchor : '95%'
																}, {
																	
																	labelAlign : 'left',
																	disabled : true,
																	fieldLabel : '打入时间',
																	xtype:'datefield',
																	format:'Y-m-d',
																	name : 'comfirtDate',
																	anchor : '95%'
															
																}]
													}]
													
										},{
											labelAlign : 'top',
											xtype : 'textarea',
											name : 'remark',
											
											fieldLabel : '备注',
											height : 100,
											width:'95%'
										}]
										});
									
		carTitle='添加车辆信息';
		if(cid!=null){
		carTitle='修改车辆信息';
		//departStore.load();
		//Ext.getCmp('mycombo').store=departStore;
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
		title : carTitle,
		width : 700,
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
				privilege:privilege,
				limit : pageSize
			}
		});
		
		selabled();
		
 }
 
 function selabled(){
 	 var _record = carGrid.getSelectionModel().getSelections();
 	
        var updatebtn = Ext.getCmp('basCarEdit');
        var deletebtn = Ext.getCmp('basCarDelete');
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
//交接单查询js
var privilege=48;//权限参数
var gridSearchUrl="stock/oprOvermemoDetailAction!overmemoSearch.action";//交接单查询地址
var carUrl = "bascar/basCarAction!list.action";//车辆查询地址
var dictionaryUrl='sys/dictionaryAction!ralaList.action';//数据字典地址
var authorityDepartUrl='sys/basRightDepartAction!findDepartName.action';//权限部门查询地址
var gridDetailUrl="stock/oprOvermemoDetailAction!toOvermemoDetailSearch.action";//转到交接单明细地址
var rollBackCarUrl="stock/oprOvermemoDetailAction!rollBackCar.action";//撤销发车确认
var defaultWidth=80;
var searchDepartId=bussDepart;
var  fields=['ID',
	  'STARTDEPARTID',
	  'ENDDEPARTID',
	  'STARTTIME',
	  'ENDTIME',
	  'UNLOADSTARTTIME',
	  'UNLOADENDTIME',
	  'OVERMEMOTYPE',
	  'CARID',
	  'STATUS',
	  'REMARK',
	  'CREATETIME',
	  'CREATENAME', 
	  'UPDATETIME',
	  'UPDATENAME',
	  'TS',
	  'ORDERFIELDS',
	  'TOTALTICKET',
	  'TOTALPIECE',
	 'TOTALWEIGHT',
	  'LOCKNO',
	  'ENDDEPARTNAME',
	  'STARTDEPARTNAME',
	  'ROUTENUMBER', 
	  'PRINTNUM',
	  'CARCODE','USECARTYPE','RENTCARRESULT','ISSEPARATEDELIVERY'];
	
	//是否点到
	var statusStore=new Ext.data.SimpleStore({
			 auteLoad:true, //此处设置为自动加载
  			 data:[['0','未点到'],['1','已点到']],
   			 fields:["statusId","statusName"]
	});
	var aloneStore=new Ext.data.SimpleStore({
			 auteLoad:true, //此处设置为自动加载
  			 data:[['','全部'],['0','否'],['1','是']],
   			 fields:["id","name"]
	});
     //车辆
	  carStore = new Ext.data.Store({
	        storeId:"carStore",
			proxy:new Ext.data.HttpProxy({url:sysPath+"/"+carUrl}),
			reader:new Ext.data.JsonReader({
	                    root:'result',
	                    totalProperty:'totalCount'
	        },[
        	{name:'carId',mapping:'id'},
        	{name:'carCode',mapping:'carCode'}
        	])
        	
		});
	//自动计算频率201
		countRangeStore	= new Ext.data.Store({ 
			storeId:"countRangeStore",
			baseParams:{filter_EQL_basDictionaryId:201,privilege:16},
			proxy:new Ext.data.HttpProxy({url:sysPath+"/"+dictionaryUrl}),
			reader:new Ext.data.JsonReader({
	                    root:'result',
	                    totalProperty:'totalCount'
	        },[
        	{name:'countRangeId',mapping:'typeCode'},
        	{name:'countRangeName',mapping:'typeName'}
        	])
		});
		
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
            {name:'departId', mapping:'RIGHTDEPARTID'}             
              ])    
        });
    var startone = new Ext.form.DateField ({
    		id : 'startone',
    		format : 'Y-m-d',
    		emptyText : "开始时间",
    		value:new Date().add(Date.DAY, -7),
    		width : defaultWidth+40
    	});
    var endone = new Ext.form.DateField ({
    		id : 'endone',
    		format : 'Y-m-d',
    		emptyText : "结束时间",
    		value:new Date(),
    		width : defaultWidth+40
    	});
   	function removeOne(){
   		overmemoGrid.getTopToolbar().remove(startone);
		overmemoGrid.getTopToolbar().remove(endone);
   	}
   	function addOne(){
   		overmemoGrid.getTopToolbar().insert(7,startone);
		overmemoGrid.getTopToolbar().insert(9,endone);
   	}
	var tbar=[
	 	{text:'<b>撤销发车</b>',iconCls:'groupEdit',tooltip : '撤销发车',
	 		handler:function(){
	 			var _records = overmemoGrid.getSelectionModel().getSelections();
				if (_records.length < 1) {
					Ext.Msg.alert(alertTitle, "请选择您要撤销的交接单！");
					return false;
				} else if (_records.length > 1) {
					Ext.Msg.alert(alertTitle, "一次只能撤销一个车次号！");
					return false;
				}
				if(_records[0].data.STARTDEPARTID!=bussDepart){
					Ext.Msg.alert(alertTitle,'始发部门不是当前业务部门，不允许撤销发车！');
					return;
				}
				var routeNumber = _records[0].data.ROUTENUMBER;
				Ext.Ajax.request({
					url : sysPath+"/"+ gridSearchUrl,
					params :{privilege:privilege,filter_t_routeNumber:routeNumber},
					success : function(resp) {
						var respText = Ext.util.JSON.decode(resp.responseText);
						if(respText.success){
							Ext.Msg.confirm(alertTitle, "此车次号为<font color=red>"+routeNumber+"</font>共计<font color=red>"+respText.resultMap.length+"</font>个实配单，您确定要<font color=red>撤销发车</font>吗？", function(
									btnYes) {
								if (btnYes == 'yes' || btnYes == 'ok' || btnYes == true) {
						 			Ext.Ajax.request({
										url : sysPath+"/"+ rollBackCarUrl,
										params :{privilege:privilege,routeNumber:routeNumber},
										success : function(resp) {
											var respText = Ext.util.JSON.decode(resp.responseText);
											if(respText.success){
												Ext.Msg.alert(alertTitle,"撤销发车成功!");
												searchOvermemo();
											}else{
												Ext.Msg.alert(alertTitle,respText.msg);
											}
										}
									});
								}
							});
						}else{
							Ext.Msg.alert(alertTitle,"后台出错！");
						}
					}
				});
				return;
	 			//alert('撤销'+routeNumber);//rollBackCarUrl
	 			
	 		}
	 	},{text:'<b>导出</b>',iconCls:'sort_down',tooltip : '导出数据',
	 		handler:function(){
	 				parent.exportExl(overmemoGrid);
	 		}
	 	},{text:'<b>查看明细</b>',iconCls:'searchDetail',
	 		handler:function(){
	 			//overmemoId
	 			var _records = overmemoGrid.getSelectionModel().getSelections();
				if (_records.length < 1) {
					Ext.Msg.alert(alertTitle, "请选择您查看的交接单！");
					return false;
				} else if (_records.length > 1) {
					Ext.Msg.alert(alertTitle, "一次只能查看一条交接单信息！");
					return false;
				}
				var overmemoId = _records[0].get('ID');
	 			var node=new Ext.tree.TreeNode({leaf :false,text:'交接单明细查询'});
		      	node.attributes={href1:gridDetailUrl+'?overmemoId='+overmemoId};
		        parent.toAddTabPage(node,true);
	 		}
	 	},{text:'<b>签单打印</b>',
	 	   iconCls:'printBtn',
		   handler:function() {
				var records = overmemoGrid.getSelectionModel().getSelections();
				if(records.length<1){
					Ext.Msg.alert(alertTitle,"请选择要打印的单据！");
					return;
				}else if (records.length>1){
					Ext.Msg.alert(alertTitle,"一次只允许打印一个单据！");
					return;
				}
				var form1 = new Ext.form.FormPanel({
					id : 'form1',
					frame : true,
					width : 100,
					cls : 'displaynone',
					hidden : true,
					items : [],
					buttons : []
				});
				form1.render(document.body);
				
				Ext.Msg.confirm(alertTitle,'您确定需要打印这数据吗?',function(btnYes){
					if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
						form1.getForm().doAction('submit', {
							url : sysPath+ "/stock/oprOvermemoAction!printOutCar.action",
							method : 'post',
							params : {
 								id:records[0].data.ROUTENUMBER
 							},
							waitMsg : '正在保存数据...',
							success : function(form, action) {
							Ext.Msg.alert(alertTitle,action.result.msg,
									function(){parent.print('4',{print_routeNumber:records[0].data.ROUTENUMBER});});
							},
							failure : function(form, action) {
										Ext.Msg.alert(alertTitle,action.result.msg);
							}
						});
					}
				});
							
	
		  } },
		   '时间选择:',{
			xtype : "combo",
			width : defaultWidth+20,
			triggerAction : 'all',
			forceSelection : true,
			model : 'local',
			hiddenId : 'checkItemsTime',
			hiddenName : 'checkItemsTime',
			store : [
					['t.startTime', '发车时间'],
					['t.endTime', '到达时间'],
					['unloadStartTime', '开始卸车时间'],
					['unloadEndTime', '卸车结束时间']],
			listeners: {   
				afterRender: function(combo) {   
		       　　combo.setValue('t.startTime');
				}
			},
			emptyText : '选择类型'
								
			},'-',
			'统计维度',{
			xtype : "combo",
			forceSelection : true,
			triggerAction : 'all',
			fieldLabel:'计算频率<font style="color:red;">*</font>',
			store : countRangeStore,
			mode : "remote",// 从本地载值
			//valueField : 'distributionModeId',// value值，与fields对应
			displayField : 'countRangeName',// 显示值，与fields对应
		    id : 'searchCountRange',
			width : defaultWidth,
			listeners:{
				select:function(combo,e){
					var v = combo.getValue();
					if(v=='日'){
						removeOne();
						startone = new Ext.form.DateField ({
				    		id : 'startone',
				    		format : 'Y-m-d',
				    		emptyText : "开始时间",
				    		value:new Date().add(Date.DAY, -7),
				    		width : defaultWidth+40
				    	});
				    	endone = new Ext.form.DateField ({
				    		id : 'endone',
				    		format : 'Y-m-d',
				    		emptyText : "结束时间",
				    		value:new Date(),
				    		width : defaultWidth+40
				    	});
				    	addOne();
				    	
					}else if(v=='周'){
						removeOne();
						startone = new Ext.form.NumberField ({
				    		id : 'startone',
				    		allowNegative :false,
				    		value:new Date().getWeekOfYear()-1,
				    		minValue:1,
				    		maxValue:52,
				    		maxLength:2,
				    		width : defaultWidth
				    	});
				    	endone = new Ext.form.NumberField  ({
				    		id : 'endone',
				    		allowNegative :false,
				    		value:new Date().getWeekOfYear(),
				    		minValue:1,
				    		maxValue:52,
				    		maxLength:2,
				    		width : defaultWidth
				    	});
				    	addOne();
					}else if(v=='月'){
						removeOne();
						startone = new Ext.form.DateField ({
							xtype:'datefield',
				    		id : 'startone',
				    		format : 'Y-m',
				    		emptyText : "开始时间",
				    		value:new Date().add(Date.MONTH, -1),
				    		width : defaultWidth+40
				    	});
				    	endone = new Ext.form.DateField ({
				    		id : 'endone',
				    		format : 'Y-m',
				    		emptyText : "结束时间",
				    		value:new Date(),
				    		width : defaultWidth+40
				    	});
				    	addOne();
					}else if(v=='年'){
						removeOne();
						startone = new Ext.form.NumberField ({
				    		id : 'startone',
				    		allowNegative :false,
				    		value:new Date().getFullYear()-1,
				    		minValue:1,
				    		width : defaultWidth
				    	});
				    	endone = new Ext.form.NumberField  ({
				    		id : 'endone',
				    		allowNegative :false,
				    		value:new Date().getFullYear(),
				    		minValue:1,
				    		width : defaultWidth
				    	});
				    	addOne();
					}
					overmemoGrid.getTopToolbar().doLayout(true);
				}
			}
		},
		'开始:',startone,' 至 ',endone,'-','状态:',{
			xtype:'combo',
			store:statusStore,
			width:defaultWidth,
			id:'searchStatus',
			valueField : 'statusId',//value值，与fields对应
			displayField : 'statusName',//显示值，与fields对应
			forceSelection : true,
			triggerAction : 'all',
			mode:'local'
			
		}];
	 	
	 	var queryTbar=new Ext.Toolbar([
	 	'-','交接单号:',{xtype:'textfield',blankText:'交接单号',
	 	width:defaultWidth,
	 	id:'searchOvermemeoId', enableKeyEvents:true,listeners : {
	 		keyup:function(textField, e){
                     if(e.getKey() == 13){
                     	searchOvermemo();
                     }
	 		}
	 	}},'-','车牌号:',{ 
		 	xtype : 'combo',
			typeAhead : true,
			width:defaultWidth,
			id:'mycombo',
			queryParam : 'filter_LIKES_carCode',
			triggerAction : 'all',
			store : carStore,
			pageSize : comboSize,
			selectOnFocus:true,
			resizable:true,
			minChars : 0,
			mode : "remote",//从服务器端加载值
			valueField : 'carId',//value值，与fields对应
			displayField : 'carCode',//显示值，与fields对应
			hiddenName:'carId',
			
			enableKeyEvents:true,
			listeners : {
	 		keyup:function(textField, e){

                     if(e.getKey() == 13){
                     	searchOvermemo();
                     }
	 		}
	 	}},'-','权限部门：',
		{xtype : 'combo',
			triggerAction : 'all',
			id:'searchAuthorityDepart',
			pageSize:comboSize,
			store : authorityDepartStore,
			resizable:true,
			forceSelection : true,
			mode : "remote",//从服务器端加载值
			valueField : 'departId',//value值，与fields对应
			displayField : 'departName',//显示值，与fields对应
			editable : true,
			name : 'departId',
			width:defaultWidth
		},'-'
		,'单独送货',{xtype : 'combo',
			triggerAction : 'all',
			id:'searchAlone',
			pageSize:comboSize,
			store : aloneStore,
			resizable:true,
			forceSelection : true,
			mode : "local",//从服务器端加载值
			valueField : 'id',//value值，与fields对应
			displayField : 'name',//显示值，与fields对应
			editable : true,
			width:defaultWidth
		},'-',
	{xtype:'textfield',blankText:'查询数据',
		id:'searchContent', 
		width:defaultWidth+20,
		enableKeyEvents:true,
		listeners : {
	 		keyup:function(textField, e){
               if(e.getKey() == 13){
               	searchOvermemo();
               }
	 		}
	 	}},'-',{ 
	 	
    					xtype : "combo",
    					width : defaultWidth+20,
    					triggerAction : 'all',
    					id:'searchSelectBox',
    					model : 'local',
    					hiddenId : 'checkItems',
    					hiddenName : 'checkItems',
    					name : 'checkItemstext',
    					store : [
    							['', '查询全部'],
    							['LIKES_startDepartName', '始发部门'],
    							['LIKES_endDepartName','最终去向'],
    							['LIKES_overmemoType','交接单类型'],
    							['LIKES_t_useCarType','用车类型'],
    							['LIKES_t_rentCarResult','车辆用途'],
    							['totalTicket','总票数'],
    							['totalWeight','总重量'],
    							['totalPiece','总件数'],
    							['LIKES_lockNo','锁号'],
    							['LIKES_T_routeNumber','车次号'],
    							['r_printNum','打印次数']],
    							
    					emptyText : '选择查询方式',
    					editable : false,
    					forceSelection : true,
    					listeners : {
    						'select' : function(combo, record, index) {
    								if(Ext.getCmp("searchContent").getValue().length>0){
    									searchOvermemo();
    								}
    							
    							
    						}
    					}
    					
    				},'-',{text : '<b>查询</b>',id : 'btn',iconCls : 'btnSearch',
    					handler : searchOvermemo
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
    
   var sm = new Ext.grid.CheckboxSelectionModel({singleSelect :true});
	 var rowNum = new Ext.grid.RowNumberer({
        header:'序号', width:20
    });
    overmemoGrid = new Ext.grid.GridPanel({
        renderTo: Ext.getBody(),
        id:'overmemoCenter',
        height:Ext.lib.Dom.getViewHeight()-1,		
        width:Ext.lib.Dom.getViewWidth()-1,
		autoScroll : true,
		//autoSizeColumns: true,
		frame : false,
		loadMask : true,
	//	stripeRows : true,
        sm:sm,
        cm:new Ext.grid.ColumnModel([rowNum,sm,
       			{header: '交接单号', dataIndex: 'ID',width:defaultWidth,sortable : true},
       			{header: '始发部门', dataIndex: 'STARTDEPARTNAME',width:defaultWidth,sortable : true},
       			{header: '最终去向', dataIndex: 'ENDDEPARTNAME',width:defaultWidth,sortable : true},
       			{header: '车牌号', dataIndex: 'CARCODE',width:defaultWidth,sortable : true},
       			{header: '用车类型', dataIndex: 'USECARTYPE',width:defaultWidth,sortable : true},
       			{header: '车辆用途', dataIndex: 'RENTCARRESULT',width:defaultWidth,sortable : true},
       			{header: '总票数', dataIndex: 'TOTALTICKET',width:defaultWidth,sortable : true},
 				{header: '总重量', dataIndex: 'TOTALWEIGHT',width:defaultWidth,sortable : true},
 				{header: '总件数', dataIndex: 'TOTALPIECE',width:defaultWidth,sortable : true},
 				{header: '锁号', dataIndex: 'LOCKNO',width:defaultWidth,sortable : true},
 				{header: '车次号', dataIndex: 'ROUTENUMBER',width:defaultWidth,sortable : true},
       			{header: '开始时间', dataIndex: 'STARTTIME',width:defaultWidth+20,sortable : true},
       			{header: '结束时间', dataIndex: 'ENDTIME',width:defaultWidth+20,sortable : true},
       			{header: '开始卸车时间', dataIndex: 'UNLOADSTARTTIME',width:defaultWidth+20,sortable : true},
       			{header: '卸车结束时间', dataIndex: 'UNLOADENDTIME',width:defaultWidth+20,sortable : true},
       			{header: '交接单类型', dataIndex: 'OVERMEMOTYPE',width:defaultWidth,sortable : true},
 				{header: '状态', dataIndex: 'STATUS',width:defaultWidth,sortable : true
 					,renderer:function(v){
 						if(v==1){
 							return '已到车确认';
 						}else if(v==2){
 							return '卸车开始';
 						}else if(v==3){
 							return '卸车结束';
 						}else if(v==0){
 							return '已发车';
 						}
 					}
 				},
 				{header: '是否单独送货', dataIndex: 'ISSEPARATEDELIVERY',width:defaultWidth,sortable : true
 					,renderer:function(v){
 						return v==1?'是':'否';
 					}
 				},
 				{header: '备注', dataIndex: 'REMARK',width:defaultWidth,sortable : true},
 				{header: '打印次数', dataIndex: 'PRINTNUM',width:defaultWidth,sortable : true}
        ]),
        store:dateStore,
      
        tbar: tbar,
        listeners: {
                    render: function(){
                        queryTbar.render(this.tbar);
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
  });
    
    
	
   function searchOvermemo() {
		dateStore.baseParams={privilege:privilege,limit : pageSize};
		
				if(!Ext.getCmp('startone').isValid()){
					Ext.Msg.alert(alertTitle,'开始时间格式不正确!');
					return;
				}
				if(!Ext.getCmp('endone').isValid()){
					Ext.Msg.alert(alertTitle,'结束时间格式不正确!');
					return;
				}
				var searchCountRange = Ext.getCmp('searchCountRange').getRawValue();
				if(null==searchCountRange || searchCountRange.length<1){
					searchCountRange='日';
				}
				var startCount = Ext.get('startone').dom.value;
				var endCount = Ext.get('endone').dom.value;
				var searchOvermemeoId = Ext.getCmp('searchOvermemeoId').getValue();
				var countCheckItems = Ext.get('checkItemsTime').dom.value;
				
				var searchAuthorityDepart=Ext.getCmp('searchAuthorityDepart').getValue();
				var searchAlone = Ext.getCmp('searchAlone').getValue();
				
				if(searchAuthorityDepart>0){
					searchDepartId=searchAuthorityDepart;
				}else{
					searchDepartId=bussDepart;
				}
				//alert(searchDepartId);
				var searchStatus = Ext.getCmp('searchStatus').getValue();
	 			 Ext.apply(dateStore.baseParams, {
	 			 		filter_countCheckItems:countCheckItems,
			 			filter_countRange:searchCountRange,
				 	 	filter_startCount:startCount=='开始时间'?'':startCount,
				 	 	filter_endCount:endCount=='结束时间'?'':endCount,
				 	 	filter_OR_endDepartId$startDepartId:searchDepartId,
				 	 	filter_t_status:searchStatus,
    					filter_t_id:searchOvermemeoId,
    					filter_isSeparateDelivery:searchAlone,
    					filter_carCode:Ext.getCmp('mycombo').getRawValue(),
						filter_checkItems : Ext.get("checkItems").dom.value,
						filter_itemsValue : Ext.get("searchContent").dom.value
    			});
	 			
		dataReload();
	}

 function dataReload(){
			dateStore.reload({
			params : {
				start : 0,
				privilege:privilege,
				limit : pageSize
			}
		});
		
 }

//交接单明细查询js
var privilege=48;//权限参数
var gridSearchUrl="stock/oprOvermemoDetailAction!overmemoDetailSearch.action";//交接单明细查询地址
var carUrl = "bascar/basCarAction!list.action";//车辆查询地址
var dictionaryUrl='sys/dictionaryAction!ralaList.action';//数据字典地址
var authorityDepartUrl='sys/basRightDepartAction!findDepartName.action';//权限部门查询地址
var defaultWidth=80;
var searchDepartId=bussDepart;

var  fields=['ID','OVERMEMO','DNO','SUBNO','REALPIECE',
     		'PIECE','CUSID','CPNAME','WEIGHT','FLIGHTNO',
     		'CONSIGNEE','ADDR','GOODS','CREATETIME','STATUS',
     		'STORAGEAREA','FLIGHTMAINNO','ISEXCEPTION','CREATE_TIME',
     		'UPDATE_NAME','UPDATETIME','DISTRIBUTIONMODE','TAKEMODE',
     		'RENUM','CARCODE','STARTDEPARTNAME','ENDDEPARTNAME','CONSIGNEEFEE',
     		'CPFEE','OVERMEMOTYPE','STARTTIME','ENDTIME'];
     	
	//是否点到
	var statusStore=new Ext.data.SimpleStore({
			 auteLoad:true, //此处设置为自动加载
  			 data:[['','全部'],['0','未点到'],['1','已点到']],
   			 fields:["statusId","statusName"]
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
            {name:'departId', mapping:'RIGHTDEPARTID',type:'string'}             
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
   		overmemoGrid.getTopToolbar().insert(11,startone);
		overmemoGrid.getTopToolbar().insert(13,endone);
   	}
	var tbar=[
	 	{text:'<b>导出</b>',iconCls:'sort_down',tooltip : '导出数据',
	 		handler:function(){
	 				parent.exportExl(overmemoGrid);
	 		}
	 	},'时间选择:',{
			xtype : "combo",
			width : defaultWidth+20,
			triggerAction : 'all',
			forceSelection : true,
			model : 'local',
			hiddenId : 'checkItemsTime',
			hiddenName : 'checkItemsTime',
			store : [
					['o.endTime', '到车时间'],
					['o.startTime', '发车时间'],
					['t.createTime', '创建时间']],
			listeners: {   
				afterRender: function(combo) {   
		       　　combo.setValue('o.startTime');
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
			valueField : 'departId',//value值，与fields对应
			displayField : 'departName',//显示值，与fields对应
			editable : true,
			name : 'departId',
			width:defaultWidth
			
 				},'-',
	{xtype:'textfield',blankText:'查询数据',id:'searchContent', enableKeyEvents:true,listeners : {
	 		
	 		keyup:function(textField, e){

                     if(e.getKey() == 13){

                     	searchOvermemo();

                     }
	 		}
	 	}},'-',{ 
	 	
    					xtype : "combo",
    					width : 100,
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
    							['LIKES_t_dNo','配送单号'],
    							['LIKES_t_flightMainNo','主单号'],
    							['LIKES_t_subNo','分单号'],
    							['LIKES_t_flightNo','航班号'],
    							['LIKES_o_overmemoType','交接单类型'],
    							['LIKES_t_goods','品名'],
    							['LIKES_t_storageArea','库存区域'],
    							['LIKES_t_consignee','收货人'],
    							['LIKES_t_addr','收货人地址']],
    							
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
    
   var sm = new Ext.grid.CheckboxSelectionModel({});
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
        		{header: '交接明细编号', dataIndex: 'ID',width:defaultWidth,sortable : true},
       			{header: '配送单号', dataIndex: 'DNO',width:defaultWidth,sortable : true},
       			{header: '交接单号', dataIndex: 'OVERMEMO',width:defaultWidth,sortable : true},
       			{header: '主单号', dataIndex: 'FLIGHTMAINNO',width:defaultWidth,sortable : true},
       			{header: '分单号', dataIndex: 'SUBNO',width:defaultWidth,sortable : true},
       			{header: '航班号', dataIndex: 'FLIGHTNO',width:defaultWidth,sortable : true},
       			{header: '到付提送费', dataIndex: 'CONSIGNEEFEE',width:defaultWidth,sortable : true},
       			{header: '预付提送费', dataIndex: 'CPFEE',width:defaultWidth,sortable : true},
       			{header: '交接单类型', dataIndex: 'OVERMEMOTYPE',width:defaultWidth,sortable : true},
       			{header: '始发部门', dataIndex: 'STARTDEPARTNAME',width:defaultWidth,sortable : true},
       			{header: '最终去向', dataIndex: 'ENDDEPARTNAME',width:defaultWidth,sortable : true},
       			{header: '代理公司', dataIndex: 'CPNAME',width:defaultWidth,sortable : true},
       			{header: '品名', dataIndex: 'GOODS',width:defaultWidth,sortable : true},
       			{header: '库存区域', dataIndex: 'STORAGEAREA',width:defaultWidth,sortable : true},
       			{header: '应到件数', dataIndex: 'PIECE',width:defaultWidth,sortable : true},
 				{header: '实到件数', dataIndex: 'REALPIECE',width:defaultWidth,sortable : true},
 				{header: '收货人', dataIndex: 'CONSIGNEE',width:defaultWidth,sortable : true},
 				{header: '收货人地址', dataIndex: 'ADDR',width:defaultWidth,sortable : true},
 				{header: '重量', dataIndex: 'WEIGHT',width:defaultWidth,sortable : true},
 				{header: '车牌号', dataIndex: 'CARCODE',width:defaultWidth,sortable : true},
 				{header: '状态', dataIndex: 'STATUS',width:defaultWidth,sortable : true
 					,renderer:function(v){
 						return v==1?"已点到":'未点到';
			        }
 				},
 				{header: '发车时间', dataIndex: 'STARTTIME',width:defaultWidth+20,sortable : true},
 				{header: '到车时间', dataIndex: 'ENDTIME',width:defaultWidth+20,sortable : true},
 				{header: '创建时间', dataIndex: 'CREATETIME',width:defaultWidth+20,sortable : true},
 				{header: '回单出库份数', dataIndex: 'RENUM',width:defaultWidth,sortable : true}
        ]),
        store:dateStore,
      
        tbar: tbar,
        listeners: {
                    render: function(){
                        queryTbar.render(this.tbar);
                    },	afterlayout:function(){
                       if(overmemoId!=null && overmemoId.length>0){
						    Ext.apply(dateStore.baseParams, {
		    					filter_EQL_overmemo:overmemoId
		    				});
							dataReload();
						}
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
		dateStore.baseParams = {
			privilege:privilege,
			limit : pageSize
		};
				var searchOvermemeoId = Ext.getCmp('searchOvermemeoId').getValue();
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
				var countCheckItems = Ext.get('checkItemsTime').dom.value;
				var searchAuthorityDepart=Ext.getCmp('searchAuthorityDepart').getValue();
				
				if(searchAuthorityDepart>0){
					searchDepartId=searchAuthorityDepart;
				}else{
					searchDepartId=bussDepart;
				}
				var searchStatus = Ext.getCmp('searchStatus').getValue();
				var checkItems =  Ext.get("checkItems").dom.value;
				var searchContent =  Ext.get("searchContent").dom.value;
				if(checkItems=="LIKES_t_dNo" && searchContent.length>1){
					Ext.apply(dateStore.baseParams, {
						filter_OR_O_endDepartId$startDepartId:searchDepartId,
						filter_checkItems : checkItems,
						filter_itemsValue : searchContent
					});
				}else{
		 			 Ext.apply(dateStore.baseParams, {
		 			 		filter_countCheckItems:countCheckItems,
				 			filter_countRange:searchCountRange,
					 	 	filter_startCount:startCount=='开始时间'?'':startCount,
				 	 		filter_endCount:endCount=='结束时间'?'':endCount,
	    					filter_EQL_overmemo:searchOvermemeoId,
	    					filter_OR_O_endDepartId$startDepartId:searchDepartId,
	    					filter_t_status:searchStatus,
	    					filter_EQS_carCode:Ext.getCmp('mycombo').getRawValue(),
							filter_checkItems : checkItems,
							filter_itemsValue : searchContent
	    			});
    			}
	 			
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

//客户列表JS
var privilege=153;
var gridSearchUrl="cus/customerListAction!findCustomerList.action";//客户列表查询地址
var cusStopUrl="cus/customerListAction!stopCustomer.action";//客户停用地址
var cusStartUrl="cus/customerListAction!startCustomer.action";//客户启用地址
var distributionCustomerUrl="cus/customerListAction!distributionCustomer.action";//客户停用地址
var cusRecordMainUrl='/cus/cusRecordAction!gotoMain.action';//客户档案地址
var departUrl='sys/departAction!findAll.action';//部门查询地址
var referToDepartUrl ='cus/customerListAction!referToDepart.action';//分配客户到部门地址
var findTableRemarkUrl = 'cus/cusRecordAction!findTableRemark.action';//查询CUS_RECORD表的备注
var customSearchSaveUrl="cus/customerListAction!customSearchSave.action";//自定义查询保存地址
var customSearchUrl="cus/customerListAction!customSearch.action";//自定义查询客户列表地址
var cusSearchUrl = "cus/cusSearchAction!findCusSearch.action";//自定义查询地址
var authorityDepartUrl='sys/basRightDepartAction!findDepartName.action';//权限部门查询地址
var pubauthorityDepart=bussDepart;
var defaultWidth=100;
var searchWidth=60; 
var recordId;
var searchChinese='';
var searchType;
var  fields=[{name:"id",mapping:'ID'},
			{name:"cusName",mapping:'CUS_NAME'},
			{name:"cusId",mapping:'CUS_ID'},
			{name:"shortName",mapping:'SHORT_NAME'},
     		{name:'importanceLevel',mapping:'IMPORTANCE_LEVEL'},
     		{name:'profitType',mapping:'PROFIT_TYPE'},
     		{name:"attentionClassify",mapping:'ATTENTION_CLASSIFY'},
     		{name:'type1',mapping:'TYPE1'},
     		{name:'manCount',mapping:'MAN_COUNT'},
     		{name:'area',mapping:'AREA'},
     		{name:'bussAirport',mapping:'BUSS_AIRPORT'},
     		{name:'bussTel',mapping:'BUSS_TEL'},
     		{name:'lastCommunicate',mapping:'LAST_COMMUNICATE'},
     		{name:'lastBuss',mapping:'LAST_BUSS'},
     		{name:'startBuss',mapping:'START_BUSS'},
     		{name:'developLevel',mapping:'DEVELOP_LEVEL'},
     		{name:'attentionRemark',mapping:'ATTENTION_REMARK'},
     		{name:'mainBussiness',mapping:'MAIN_BUSSINESS'},
     		{name:'expectedCargo',mapping:'EXPECTED_CARGO'},
     		{name:'expectedTurnover',mapping:'EXPECTED_TURNOVER'},
     		{name:'phone',mapping:'PHONE'},
     		{name:'companyRemark',mapping:'COMPANY_REMARK'},
     		{name:'companyEmail',mapping:'COMPANY_EMAIL'},
     		{name:'fax',mapping:'FAX'},
     		{name:'addr',mapping:'ADDR'},
     		{name:'website',mapping:'WEBSITE'},
     		{name:'serviceId',mapping:'SERVICE_ID'},
     		{name:'serviceName',mapping:'SERVICE_NAME'},
     		{name:'departCode',mapping:'DEPARTCODE'},
     		{name:'departCodeName',mapping:'DEPARTCODENAME'},
     		{name:'status',mapping:'STATUS'}
     		];
var remakFields=['CHINANAME','TABLENAME','COLUMNNAME','DATATYPE','REMARK'];
	tableRemarkStore = new Ext.data.Store({
        storeId:"departStore",
        proxy: new Ext.data.HttpProxy({url:sysPath+'/'+findTableRemarkUrl}),
        reader: new Ext.data.JsonReader({
            root: 'resultMap', totalProperty: 'totalCount'
        },remakFields)
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
	   //内部交接部门(业务部门)
		departStore = new Ext.data.Store({
        storeId:"departStore",
        proxy: new Ext.data.HttpProxy({url:sysPath+'/'+departUrl}),
        reader: new Ext.data.JsonReader({
            root: 'result', totalProperty: 'totalCount'
        }, [
            {name:'departId'},
            {name: 'departName'}
        ])
        });	
       //自定义查询
      customStore = new Ext.data.Store({
        storeId:"customStore",
        proxy: new Ext.data.HttpProxy({url:sysPath+'/'+cusSearchUrl}),
        baseParams:{filter_createName:userName,filter_departCode:departId},
        reader: new Ext.data.JsonReader({
            root: 'resultMap', totalProperty: 'totalCount'
        }, [
            {name:'id',mapping:'ID'},
            {name: 'tableCh',mapping:'TABLE_CH'},
            {name: 'tableEn',mapping:'TABLE_EN'},{name:'createName',mapping: 'CREATE_NAME'},
            {name:'searchStatement',mapping: 'SEARCH_STATEMENT'},{name:'createTime',mapping: 'CREATE_TIME'},
            {name:'updateName',mapping: 'UPDATE_NAME'},{name:'updateTime',mapping: 'UPDATE_TIME'},
            {name:'ts',mapping: 'TS'},{name:'departCode',mapping: 'DEPART_CODE'},
            {name:'title',mapping:'TITLE'}
            
        ])
        });
        statusStore=new Ext.data.SimpleStore({
			 autoLoad:true, //此处设置为自动加载
  			  data:[['1','正常'],['0','停用']],
   			 fields:["statusId","statusName"]
		});
	var symbolStore=new Ext.data.SimpleStore({
			 autoLoad:true, //此处设置为自动加载
  			 data:[
  			 //['','-请选择符号-'],
  			 ['=','等于'],
  			 ['>','大于'],
  			 ['<','小于'],
  			 ['>=','大于等于'],
  			 ['<=','小于等于'],
  			 ['!=','不等于'],
  			 ['LIKE','包含']],
   		   fields:["typeId","typeName"]
		});
		
	var connectStore=new Ext.data.SimpleStore({
			 autoLoad:true, //此处设置为自动加载
  			 data:[
  			 //['','-请选择符号-'],
  			 ['AND','并且'],
  			 ['OR','或者']],
   		   fields:["connectId","connectName"]
		});
	var bracketStore=new Ext.data.SimpleStore({
			 autoLoad:true, //此处设置为自动加载
  			 data:[
  			 //['','-请选择符号-'],
  			 ['1','('],
  			 ['2',')']],
   		   fields:["bracketId","bracketName"]
		});
     var menu = new Ext.menu.Menu({
			id: 'searchMenu',
			items: [
				{text:'<b>查询</b>',iconCls : 'btnSearch',tooltip : '查询',handler:function() {
           			searchcustomerList();
            	} 
            }
			],
			listeners:{
				'beforeshow':function(m,e){
					
				}
			}
			});
	
		 var searchBtn=new Ext.Button({
   					text : '<B>查询</B>',
   					id : 'searchBtn',
   					tooltip : '回单菜单',
   					iconCls : 'btnSearch',
   					menu: menu
   				});

	var tbar=[
		{text : '客户全景',
			handler : function(text,e){
				var record = customerListGrid.getSelectionModel().getSelected();
    		    if(null==record){
    		    	Ext.Msg.alert(alertTitle,'请选择一条你要查看的客商信息！');
    		    	return;
    		    }else{
					var node=new Ext.tree.TreeNode({id:'recordFiles',leaf :false,text:text.getText()});
			      	node.attributes={href1:cusRecordMainUrl+'?recordId='+record.data.id};
			        parent.toAddTabPage(node,true);
    		    }
			}
    	},'-',
    	{text : '新增客户',
    		handler : function(text,e){
    			toTabPanel(null,text);
    		}
    	},'-',
    	{text : '修改客户',
    		handler : function(text,e){
    		    var record = customerListGrid.getSelectionModel().getSelected();
    		    if(null==record){
    		    	Ext.Msg.alert(alertTitle,'请选择一条你要修改的客商信息！');
    		    	return;
    		    }else{
    		    	toTabPanel(record,text);
    		    }
    		}
    	},'-',
    	{text : '分配客服员',
    		handler : function(text,e){
    			var _record = customerListGrid.getSelectionModel().getSelections();
    			//var node=new Ext.tree.TreeNode({id:'distributionCustomerId',leaf :false,text:text.getText()});
		      	//node.attributes={href1:"/"+distributionCustomerUrl};
		        //parent.toAddTabPage(node,true);
    			if(_record.length<1){
    				Ext.Msg.alert(alertTitle,'请选择要分配的客户');
    				return;
    			}else{
    				assign(_record);	
    			}
    		}
    	},'-',	
    	{text : '分配到部门',
    		handler : function(text,e){
    			var _record = customerListGrid.getSelectionModel().getSelections();
    			if(_record.length<1){
    				Ext.Msg.alert(alertTitle,'请选择要分配的客户！');
    				return;
    			}
    			referToDepart(_record);
    		}
    	},'-','权限部门:',{xtype : "combo",
				width : searchWidth+20,
				editable:true,
				triggerAction : 'all',
				typeAhead : true,
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
                     	searchcqCorporateRate();
                     }
	 		}
	 	
	 	}},'-',	
		{xtype:'textfield',blankText:'查询数据',id:'searchContent', 
			width:defaultWidth,
		 	enableKeyEvents:true,listeners : {
	 		keyup:function(textField, e){
               if(e.getKey() == 13){
               	searchcustomerList();
               }
	 		}
	 	}},'-',{ 
	 	
  					xtype : "combo",
  					width : defaultWidth,
  					triggerAction : 'all',
  					id:'searchSelectBox',
  					model : 'local',
  					hiddenId : 'checkItems',
  					hiddenName : 'checkItems',
  					name : 'checkItemstext',
  					store : [['EQS_id', '客户编码'],
  							['LIKES_cus_name','客户名称'],
  							['LIKES_importance_Level','重要程度'],
  							['LIKES_develop_Level','开发阶段'],
  							['LIKES_area','地域']],
  							
  					emptyText : '选择查询方式',
  					editable : false,
  					forceSelection : true,
  					listeners : {
  						'select' : function(combo, record, index) {
						if(Ext.getCmp("searchContent").getValue().length>0){
							searchcustomerList();
						}
  						}
  					}
    					
    		},'-',
    		searchBtn
	 	];
	 	
	 var cusType = new Ext.menu.Menu({
	 	items:[{text : '热点客户',
	    		handler :function(){ searchcustomerList('ATTENTION_CLASSIFY','热点关注',true);}
	    	},'-',
	    	{text : 'VIP客户',
	    		handler :function(){ searchcustomerList('IMPORTANCE_LEVEL','VIP客户',true);}
	    	},'-',	
	    	{text : '重点客户',
	    		handler :function(){ searchcustomerList('IMPORTANCE_LEVEL','重点客户',true);}
	    	},'-',	
	    	{text : '项目客户',
	    		handler :function(){ searchcustomerList('IMPORTANCE_LEVEL','项目客户',true);}
	    	},'-',	
	    	{text : '潜在客户',
	    		handler :function(){ searchcustomerList('DEVELOP_LEVEL','潜在客户',true);}
	    	},'-',	
	    	{text : '合作客户',
	    		handler :function(){ searchcustomerList('DEVELOP_LEVEL','合作客户',true);}
	    	},'-',	
	    	{text : '流失客户',
	    		handler :function(){ searchcustomerList('DEVELOP_LEVEL','流失客户',true);}
	    	}]
	 });
	 var cusOutTime=new Ext.menu.Menu({
		 	items:[{text : '一个月未联系客户',
		    		handler :function(){ searchcustomerList('LAST_COMMUNICATE',1,true);}
		    	},'-',	
		    	{text : '一天以上未走货客户',
		    		handler :function(){ searchcustomerList('LAST_BUSS',1,true);}
		    	},'-',	
		    	{text : '三天以上未走货客户',
		    		handler :function(){ searchcustomerList('LAST_BUSS',3,true);}
		    	},'-',
		    	{text : '一周以上未走货客户',
		    		handler :function(){ searchcustomerList('LAST_BUSS',7,true);}
		    	}]
		 
		 });
	 var queryTbar=new Ext.Toolbar([
		 		new Ext.Button({
   					text : '<B>客户类型</B>',
   					tooltip : '客户类型',
   					menu: cusType
   				}),'-',	
   				new Ext.Button({
   					text : '<B>走货客户</B>',
   					tooltip : '走货客户',
   					menu: cusOutTime
   				}),'-',
   				{text : '停用客户',
		    		handler : function(){
		    			//cusStopUrl
		    			var _records = customerListGrid.getSelectionModel().getSelections();
		    			if(_records.length<1){
		    				Ext.Msg.alert(alertTitle,'请选择你要停用的客户！');
		    				return;
		    			}
		    			Ext.Msg.confirm(alertTitle, "你确定要停用这"+_records.length+"个客户吗？", function(
								btnYes) {
							if (btnYes == 'yes' || btnYes == 'ok' || btnYes == true) {
		    		
				    			var ids="";
				    			for(var i=0;i<_records.length;i++){
				    				ids+=_records[i].data.id+',';
				    			}
				    			Ext.Ajax.request({
									url : sysPath+'/'
											+ cusStopUrl+"?privilege="+privilege,
									params :{ids:ids},
									success : function(resp) {
										var respText = Ext.util.JSON.decode(resp.responseText);
										if(respText.success){
											Ext.Msg.alert(alertTitle,"客户停用成功!");
											searchcustomerList();
										}else{
											Ext.Msg.alert(alertTitle,respText.msg);
										}
									}
								});
						}
						});
		    		}
		    	},'-',
		    	{text : '启用客户',
		    		handler : function(){
		    			//cusStopUrl
		    			var _records = customerListGrid.getSelectionModel().getSelections();
		    			if(_records.length<1){
		    				Ext.Msg.alert(alertTitle,'请选择你要启用的客户！');
		    				return;
		    			}
		    			Ext.Msg.confirm(alertTitle, "你确定要启用这"+_records.length+"个客户吗？", function(
								btnYes) {
							if (btnYes == 'yes' || btnYes == 'ok' || btnYes == true) {
		    		
				    			var ids="";
				    			for(var i=0;i<_records.length;i++){
				    				ids+=_records[i].data.id+',';
				    			}
				    			Ext.Ajax.request({
									url : sysPath+'/'
											+ cusStartUrl+"?privilege="+privilege,
									params :{ids:ids},
									success : function(resp) {
										var respText = Ext.util.JSON.decode(resp.responseText);
										if(respText.success){
											Ext.Msg.alert(alertTitle,"客户启用成功!");
											searchcustomerList();
										}else{
											Ext.Msg.alert(alertTitle,respText.msg);
										}
									}
								});
						}
						});
		    		}
		    	},'-',
		    	'客户状态:',{
   					xtype:'combo',
   					width:80,
   					store:statusStore,
   					triggerAction : 'all',
					forceSelection : true,
					editable : false,
					mode : "local",//获取本地的值
					displayField : 'statusName',//显示值，与fields对应
					valueField : 'statusId',//value值，与fields对应
					id:'searchStatus',
					enableKeyEvents:true,listeners : {
			 			keyup:function(combo, e){
		                     if(e.getKey() == 13){
		                     	searchcustomerList();
		                     }
			 			},render:function(combo,e){
			 				combo.setValue('1');
			 			}
			 		}
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
   
   var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:false});
	var rowNum = new Ext.grid.RowNumberer({
        header:'序号', width:30
    });
    customerListGrid = new Ext.grid.GridPanel({
        renderTo: Ext.getBody(),
        id:'customerListCenter',
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
       			{header: '客户编码', dataIndex: 'id',width:defaultWidth-30,sortable : true},
       			{header: '部门编码', dataIndex: 'departCode',width:defaultWidth,sortable : true,hidden:true},
       			{header: '部门名称', dataIndex: 'departCodeName',width:defaultWidth,sortable : true},
       			{header: '客户编号', dataIndex: 'cusId',width:defaultWidth-30,sortable : true},
       			{header: '客户名称', dataIndex: 'cusName',width:defaultWidth+50,sortable : true},
       			{header: '简称', dataIndex: 'shortName',width:defaultWidth,sortable : true},
       			{header: '固定电话', dataIndex: 'phone',width:defaultWidth,sortable : true},
       			{header: '网址', dataIndex: 'website',width:defaultWidth,sortable : true},
       			{header: '传真', dataIndex: 'fax',width:defaultWidth,sortable : true},
       			{header: '企业邮箱', dataIndex: 'companyEmail',width:defaultWidth,sortable : true},
       			{header: '地址', dataIndex: 'addr',width:defaultWidth,sortable : true},
       			{header: '重要程度', dataIndex: 'importanceLevel',width:defaultWidth-30,sortable : true},
       			{header: '盈利性分类', dataIndex: 'profitType',width:defaultWidth-30,sortable : true},
       			{header: '主营业务', dataIndex: 'mainBussiness',width:defaultWidth-30,sortable : true},
       			{header: '人员规模(人)', dataIndex: 'manCount',width:defaultWidth,sortable : true},
       			{header: '开发阶段', dataIndex: 'developLevel',width:defaultWidth,sortable : true},
       			{header: '近期关注', dataIndex: 'attentionClassify',width:defaultWidth,sortable : true},
       			{header: '热点说明', dataIndex: 'attentionRemark',width:150},
       			{header: '自定义类型', dataIndex: 'type1',width:defaultWidth,sortable : true},
 				{header: '地域', dataIndex: 'area',width:defaultWidth-40,sortable : true},
 				{header: '业务机场', dataIndex: 'bussAirport',width:defaultWidth,sortable : true},
 				{header: '业务联系电话', dataIndex: 'bussTel',width:defaultWidth,sortable : true},
 				{header: '预计货量', dataIndex: 'expectedCargo',width:defaultWidth,sortable : true},
 				{header: '预计营业额', dataIndex: 'expectedTurnover',width:defaultWidth,sortable : true},
       			{header: '最后发货时间', dataIndex: 'lastCommunicate',width:defaultWidth+20,sortable : true},
       			{header: '最后沟通时间', dataIndex: 'lastBuss',width:defaultWidth+20,sortable : true},
       			{header: '开始合作日期', dataIndex: 'startBuss',width:defaultWidth+20,sortable : true},
       			{header: '公司简介', dataIndex: 'companyRemark',width:defaultWidth+100,sortable : true},
       			{header: '唯一负责人', dataIndex: 'serviceName',width:defaultWidth-30},
       			{header: '状态', dataIndex: 'status',width:defaultWidth-40
       				,renderer:function(v){
       					//状态 0,删除，1，正常
       					return v=='1'?"正常":"停用";
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
        	filter_EQL_endDepartId:bussDepart,
            pageSize: pageSize, 
            store: dateStore, 
            displayInfo: true,
            displayMsg: '显示第 {0} 条到 {1} 条记录，共 {2} 条', 
            emptyMsg: "没有记录信息显示"
        })
    });
    customStore.reload({callback:function(){
    
    for(var i=0;i<customStore.data.length;i++){
    	var searchId = customStore.getAt(i).get('id');
		Ext.getCmp('searchMenu').addItem(new Ext.menu.Item({text:customStore.getAt(i).get('title'),
						id:searchId+"",
						 handler:function(item,e){
								dateStore.proxy = new Ext.data.HttpProxy({
									method : 'POST',
									url : sysPath+ "/"+customSearchUrl+"?filter_searchId="+item.getId(),
									baseParams:{limit : pageSize}
								});
								dataReload();
						 }
						 }));
		}
		
		Ext.getCmp('searchMenu').addItem(new Ext.menu.Item({text:'<b>自定义查询</b>',iconCls : 'group',tooltip : '自定义查询',handler:function() {
           			alertSearch();
            	}
            }));
    
    }});
  });
  
	 function searchcustomerList(searchItem,searchValue,flag) {
		dateStore.proxy = new Ext.data.HttpProxy({
					method : 'POST',
					url : sysPath+ "/"+gridSearchUrl,
						params:{limit : pageSize}
				});
				var authorityDepartId=Ext.getCmp('authorityDepartId').getValue();
				if(null!=authorityDepartId && authorityDepartId>0){
					pubauthorityDepart=authorityDepartId;
				}else{
					pubauthorityDepart=bussDepart;
				}
				var searchStatus = Ext.getCmp('searchStatus').getValue();
				 dateStore.on('beforeload', function(store,options)
				 {
				 	Ext.apply(options.params, {
				 		filter_status:searchStatus,
				 		filter_authorityDepartId:pubauthorityDepart,
				 		filter_checkItems : Ext.get("checkItems").dom.value,
						filter_itemsValue : Ext.get("searchContent").dom.value
				 	});
				 	
				 	if(flag){
					 	Ext.apply(options.params, {
					 		filter_searchItem : searchItem,
							filter_searchValue : searchValue
					 	});
				 	}else{
				 		Ext.apply(options.params, {
					 		filter_searchItem : '',
							filter_searchValue : ''
						});
				 	}
				 });
	 			
		dataReload();
	}
	
	function toTabPanel(record,text){
		if(record!=null){
			recordId = record.data.id;
			var node=new Ext.tree.TreeNode({id:'editConsterList',leaf :false,text:text.getText()});
	      	node.attributes={href1:"/cus/cusRecordAction!input.action?recordId="+recordId};
	        parent.toAddTabPage(node,true);
        }else{
        	var node=new Ext.tree.TreeNode({id:'addConsterList',leaf :false,text:text.getText()});
	      	node.attributes={href1:"/cus/cusRecordAction!input.action"};
	        parent.toAddTabPage(node,true);
        }
	}
	function referToDepart(_records){
		var ids='';
	 	for(var i=0;i<_records.length;i++){
	 		ids+=_records[i].data.id;
	 		if(i!=(_records.length-1)){
	 			ids+=',';
	 		}
	 	}
		var form = new Ext.form.FormPanel({
					labelAlign : 'left',
					frame : true,
					bodyStyle : 'padding:5px 5px 0',
					width : 400,
					labelAlign : "right",
				
						items : [{
								layout : 'column',
								items : [{
										columnWidth : .9,
										layout : 'form',
										items : [{
													xtype : 'combo',
													triggerAction : 'all',
													//typeAhead : true,
													queryParam : 'filter_LIKES_departName',
													store : departStore,
													pageSize : comboSize,
													forceSelection : true,
													resizable:true,
													fieldLabel : '部门名称',
													minChars : 0,
													editable : true,
													valueField : 'departId',//value值，与fields对应
													displayField : 'departName',//显示值，与fields对应
													hiddenName:'departName',
													id:'departNameId',
													anchor : '95%'
												}
											]
									}]
										
							}]
							});
		var win = new Ext.Window({
		title : '分配客户到部门',
		width : 400,
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
					var departNameId = Ext.getCmp('departNameId').getValue();
					Ext.Ajax.request({
						url : sysPath+'/'
								+ referToDepartUrl+"?privilege="+privilege,
						params :{ids:ids,departId:departNameId},
						success : function(resp) {
							var respText = Ext.util.JSON.decode(resp.responseText);
							if(respText.success){
								Ext.Msg.alert(alertTitle,'分配成功！');
								win.close();
								searchcustomerList();
							}else{
								Ext.Msg.alert(alertTitle,respText.msg);
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


	function alertSearch(){
		newSearchGrid();
		var form = new Ext.form.FormPanel({
					labelAlign : 'left',
					frame : true,
					bodyStyle : 'padding:5px 5px 0',
					width : 700,
					height:400,
					labelAlign : "right",
				
						items : [{
							layout:'column',
							items:[{
										layout:'form',
										columnWidth:.1,
										labelWidth:8,
								 		items:[
								 				{
								 					xtype : 'combo',
													triggerAction : 'all',
													store : bracketStore,
													forceSelection : true,
													editable : true,
													id:'searchleftBracket',
													mode : "local",//获取本地的值
													displayField : 'bracketName',//显示值，与fields对应
													valueField : 'bracketId',//value值，与fields对应
													anchor : '100%'
								 				}
								 			]
									},{
										layout:'form',
										columnWidth:.15,
										labelWidth:50,
								 		items:[
								 				{
								 					xtype : 'combo',
													triggerAction : 'all',
													store : connectStore,
													emptyText : "连接符",
													forceSelection : true,
													fieldLabel:'连接符',
													editable : false,
													allowBlank:false,
													id:'searchAnd',
													mode : "local",//获取本地的值
													displayField : 'connectName',//显示值，与fields对应
													valueField : 'connectId',//value值，与fields对应
													anchor : '100%'
								 				}
								 			]
									},{
									layout:'form',
							 		columnWidth:.25,
							 		labelWidth:60,
							 		items:[
							 				{
							 					xtype : 'combo',
												triggerAction : 'all',
												typeAhead : true,
												queryParam : 'filter_remarkName',
												store : tableRemarkStore,
												minChars : 0,
												resizable:true,
												pageSize:comboSize,
												allowBlank:false,
												forceSelection : true,
												//hideTrigger:true,
												model:'remote',
												id:'searchColumn',
												fieldLabel : '条件字段',
												valueField : 'COLUMNNAME',//value值，与fields对应
												displayField : 'REMARK',//显示值，与fields对应
												hiddenName:'COLUMNNAME',
												anchor : '100%',
												enableKeyEvents:true,
												listeners : {
													select:function(combo, e){
														
														Ext.Ajax.request({
															url : sysPath+'/'+ findTableRemarkUrl,
															params :{filter_remarkName: combo.getRawValue()},
															success : function(resp) {
																var respText = Ext.util.JSON.decode(resp.responseText);
																if(respText.success){
																	searchType=respText.resultMap[0].DATATYPE;
																}else{
																	Ext.Msg.alert(alertTitle,respText.msg);
																}
															}
														});
													}
												}
								 			}
							 			]
									},{
										layout:'form',
										columnWidth:.15,
										labelWidth:50,
								 		items:[
								 				{
								 					xtype : 'combo',
													triggerAction : 'all',
													store : symbolStore,
													emptyText : "请选择操作符",
													forceSelection : true,
													fieldLabel:'操作符',
													editable : false,
													allowBlank:false,
													id:'searchSymbol',
													mode : "local",//获取本地的值
													displayField : 'typeName',//显示值，与fields对应
													valueField : 'typeId',//value值，与fields对应
													name : 'type',
													anchor : '100%'
								 				}
								 			]
									},{
										layout:'form',
										labelWidth:15,
										columnWidth:.15,
										items:[{
											xtype:'textfield',
											name:'value',
											fieldLabel:'值',
											allowBlank:false,
											id:'searchValue',
											anchor : '100%',
											enableKeyEvents:true,
								 			listeners : {
											 	keypress:function(textField, e){
										              if(e.getKey() == 13){
										                    Ext.getCmp('comboflight').focus(true,true);
										              }
											 	}
									 		}
										}]
									},{
										layout:'form',
										columnWidth:.08,
										labelWidth:1,
								 		items:[
								 				{
								 					xtype : 'combo',
													triggerAction : 'all',
													store : bracketStore,
													forceSelection : true,
													editable : true,
													id:'searchrightBracket',
													mode : "local",//获取本地的值
													displayField : 'bracketName',//显示值，与fields对应
													valueField : 'bracketId',//value值，与fields对应
													anchor : '100%'
								 				}
								 			]
									},{
										layout:'form',
										columnWidth:.05,
								 		items:[
								 				{xtype:'button',
								 				 id:'addbtn',
								 				 text:'添加',
								 				 handler:function(){
								 					var columNname = Ext.getCmp('searchColumn').getValue();
								 					var chinaColum = Ext.getCmp('searchColumn').getRawValue();
								 					var symbols = Ext.getCmp('searchSymbol').getValue();
								 					var symbolsName = Ext.getCmp('searchSymbol').getRawValue();
								 					var value = Ext.getCmp('searchValue').getValue();
								 					var searchAnd = Ext.getCmp('searchAnd').getValue();
								 					var searchAndName = Ext.getCmp('searchAnd').getRawValue();
								 					var searchrightBracket = Ext.getCmp('searchrightBracket').getRawValue();
								 					var searchleftBracket = Ext.getCmp('searchleftBracket').getRawValue();
								 					
								 					if(columNname.length>0 && symbols.length>0 && value.length>0 && searchAnd.length>0){
								 						//alert(columNname+','+symbols+','+value);
								 						
								 						var red=new Ext.data.Record({chinaColum:chinaColum,columNname:columNname,
								 													  symbols:symbols,symbolsName:symbolsName,
								 													  searchAnd:searchAnd,searchAndName:searchAndName,
								 													  searchrightBracket:searchrightBracket,searchleftBracket:searchleftBracket,
								 													  value:value,searchType:searchType});
								 						searchStore.add(red);
								 						setSearchRemark();
								 					}
								 				 }
								 				}
								 			]
									},{
										layout:'form',
										columnWidth:.05,
								 		items:[{xtype:'button',
								 				 id:'delbtn',
								 				 text:'删除',
								 				 handler:function(){
								 						var records = searchGrid.getSelectionModel().getSelections();
								 						searchStore.remove(records);
								 						setSearchRemark();
								 					}
								 				}
								 			]
									}
								]
						},searchGrid,
						{items:[{xtype:'textarea',id:'searchRemark',fieldLabel :'拼接条件：',width:650,height:100}]
						}]
							});
		var win = new Ext.Window({
		title : '自定义查询',
		width : 700,
		height:400,
		closeAction : 'hide',
		plain : true,
		resizable : false,
		modal : true,
		items : form,
		buttonAlign : "center",	buttons : [{
			text : "重置",
			iconCls : 'refresh',
			handler : function() {
				form.getForm().reset();
//				Ext.getCmp('searchAnd').setValue("");
//				Ext.getCmp('searchColumn').setValue("");
//				Ext.getCmp('searchSymbol').setValue("");
//				Ext.getCmp('searchValue').setValue("");
//				Ext.getCmp('searchRemark').setValue("");
				searchStore.removeAll();
			}
		},{
			text : "保存为模板",
			iconCls : 'groupSave',
			handler : function() {
				var searchRemark =Ext.getCmp('searchRemark').getValue();
				if(null==searchRemark || searchRemark.length<1){
					return;
				}
				Ext.Msg.prompt(alertTitle, "请输入自定义查询名称：", function(
						btnYes,text) {
					if (btnYes == 'yes' || btnYes == 'ok' || btnYes == true) {
						if(null==text || text.length<1){
							Ext.Msg.alert(alertTitle,'请输入名称！');
							return;
						}
						
						Ext.Ajax.request({
							url : sysPath+'/'
									+ customSearchSaveUrl+"?flag="+true,
							params :{searchStatement:searchRemark,searchTitle:text,searchChinese:searchChinese},
							success : function(resp) {
								var respText = Ext.util.JSON.decode(resp.responseText);
								if(respText.success){
									Ext.Msg.alert(alertTitle,'保存成功！');
									win.close();
									searchcustomerList();
								}else{
									Ext.Msg.alert(alertTitle,respText.msg);
								}
							}
						});
					}
				});
					
			}
		}, {
			text : "查询",
			iconCls : 'btnSearch',
			handler : function() {
					var searchRemark =Ext.getCmp('searchRemark').getValue();
					dateStore.reload({
						params : {
							start : 0,
							searchStatement:searchRemark,
							limit:pageSize
						},callback :function(r,options,success){
							if(!success){
								Ext.Msg.alert(alertTitle,'您拼写的查询条件不符合规范！');	
							}
//							if(!respText.success){
//								alert('报个错看看！');
//							}
						}
					})
					
			}
		},{
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
var searchFields=['columNname','symbols','symbolsName','value','searchAndName','searchAnd','searchleftBracket','searchrightBracket','type'];
function newSearchGrid(){
	searchStore = new Ext.data.Store({
        storeId:"searchStore",
        proxy: new Ext.data.HttpProxy({url : sysPath+ "/"+gridSearchUrl,
						params:{privilege:privilege,limit : pageSize}}),
        reader: new Ext.data.JsonReader({
            root: 'result', totalProperty: 'totalCount'
        }, searchFields)
    });
   
   var sm2 = new Ext.grid.CheckboxSelectionModel({});
	var rowNum2 = new Ext.grid.RowNumberer({
        header:'序号', width:30
    });
    searchGrid = new Ext.grid.GridPanel({
        id:'searchCenter',
        width:650,
        height:200,
        viewConfig : {
			columnsText : "显示的列",
			sortAscText : "升序",
			sortDescText : "降序"
		},
		autoScroll:false, 
		frame:false,
        loadMask:true,
        sm:sm2,
        cm:new Ext.grid.ColumnModel([rowNum2,sm2,
       			{header: '连接符ID', dataIndex: 'searchAnd',width:100,sortable : true,hidden:true},
       			{header: '连接符', dataIndex: 'searchAndName',width:100,sortable : true},
        		{header: '字段', dataIndex: 'columNname',width:100,sortable : true,hidden:true},
        		{header: '字段名称', dataIndex: 'chinaColum',width:100,sortable : true},
       			{header: '操作符ID', dataIndex: 'symbols',width:100,sortable : true,hidden:true},
       			{header: '操作符', dataIndex: 'symbolsName',width:100,sortable : true},
       			{header: '左括号', dataIndex: 'searchleftBracket',width:100,sortable : true,hidden:true},
       			{header: '右括号', dataIndex: 'searchrightBracket',width:100,sortable : true,hidden:true},
        		{header: '值', dataIndex: 'value',width:100,sortable : true},
        		{header: '类型', dataIndex: 'type',width:100,sortable : true}
        ]),
        store:searchStore,
      
        bbar: new Ext.PagingToolbar({
            pageSize: pageSize, 
            store: searchStore, 
            displayInfo: true,
            displayMsg: '显示第 {0} 条到 {1} 条记录，共 {2} 条', 
            emptyMsg: "没有记录信息显示"
        })
    });
}
function setSearchRemark(){
	var myType="";
	searchChinese="";
	for(var i=0;i<searchStore.data.length;i++){
		var columNname= searchStore.getAt(i).get('columNname');
		var symbols= searchStore.getAt(i).get('symbols');
		var searchAnd= searchStore.getAt(i).get('searchAnd');
		var value= searchStore.getAt(i).get('value');
		var searchrightBracket = searchStore.getAt(i).get('searchrightBracket');
	    var searchleftBracket = searchStore.getAt(i).get('searchleftBracket');
	    var type = searchStore.getAt(i).get('searchType');
	    
		if("LIKE"==symbols){
			value='%'+value+'%';
		}
		
		if(type=='DATE'){
			value="to_date('"+value+"','yyyy-MM-dd')";
			myType+=" "+searchleftBracket+ searchAnd+" "+columNname+" "+ symbols +" "+ value+" "+searchrightBracket;
		}else{
			myType+=" "+searchleftBracket+ searchAnd+" "+columNname+" "+ symbols +" '"+ value+"'"+searchrightBracket;
		}
		searchChinese+=" "+searchleftBracket+searchStore.getAt(i).get('searchAndName')+" "+searchStore.getAt(i).get('chinaColum')+" "
						+searchStore.getAt(i).get('symbolsName')+" '"+searchStore.getAt(i).get('value')+"'"+searchrightBracket;
	}
	Ext.getCmp('searchRemark').setValue(myType);
}

 function dataReload(){
			dateStore.reload({
			params : {
				start : 0,
				limit:pageSize
				}
			})
}
var cusServiceStore=new Ext.data.Store({
			autoLoad:true,
	        storeId:"cusServiceStore",
			proxy:new Ext.data.HttpProxy({url:sysPath+"/user/userAction!list.action"}),
			reader:new Ext.data.JsonReader({
	                    root:'result',
	                    totalProperty:'totalCount'
	        },[
        	{name:'cusServiceId',mapping:'id'},
        	{name:'cusServiceName',mapping:'userName'}
        	])
        	
});
function assign(records){
	var rowNum = new Ext.grid.RowNumberer({
        header:'序号', width:30
    });
	var dateStore = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({url:sysPath+"/"+this.gridSearchUrl}),
        baseParams:{pageSize: pageSize},
        reader:new Ext.data.JsonReader(
    	         {  root:'resultMap',
                    totalProperty:'totalCount'
                  },fields)
    });
    dateStore.add(records);
	var rowWidth=60;
	var cusGrid = new Ext.grid.GridPanel({
    	id : 'cusGrid',
    	height : 350,
    	frame : false,
    	loadMask : true,
    	stripeRows : true,
    	viewConfig:{
    		scrollOffset: 0,
    		autoScroll : true
    	},
        cm:new Ext.grid.ColumnModel([rowNum,
            {header: '客户ID', dataIndex: 'id',width:30},
	        {header: '客户名称', dataIndex: 'cusName',width:rowWidth},
			{header: '重要程度', dataIndex: 'importanceLevel',width:rowWidth},
			{header: '开发阶段', dataIndex: 'developLevel',width:rowWidth}
        ]),
        store:dateStore
    });
	var form = new Ext.form.FormPanel({
			frame : true,
			border : false,
			bodyStyle : 'padding:5px 5px 5px',
			labelAlign : "left",
			labelWidth : 65,
			width : 510,
			labelAlign : "right",
			reader : new Ext.data.JsonReader({
	            root: 'result', totalProperty: 'totalCount'
	        },fields),
			items:[
			{
		        layout : 'column',
		        items:[{
		        		layout:'form',
		        		columnWidth : .5,
		        		items:[
		        			cusGrid	
                       	]
	                    },{
		                    layout:'form',
		                    columnWidth : .5,
		                    items:[
		                    {
		                    	xtype : "combo",
								//width : searchWidth,
		                    	fieldLabel:'客服员',
								typeAhead : true,
								pageSize : comboSize,
								forceSelection : true,
								selectOnFocus : true,
								resizable : true,
								minChars : 0,
								queryParam : 'filter_LIKES_userName',
								triggerAction : 'all',
								store : cusServiceStore,
								mode : "remote",// 从服务器端加载值
								valueField : 'cusServiceId',// value值，与fields对应
								displayField : 'cusServiceName',// 显示值，与fields对应
							    name : 'cusServiceName',
							    id:'assescusService',
							    allowBlank:false,
							    blankText:'客服员不能为空！'
		                    }
	                    ]
		            }]
            }]
            
	});
	var win = new Ext.Window({
		title : '分配客服员',
		width : 580,
		closeAction : 'hide',
		plain : true,
		modal : true,
		items : form,
		buttonAlign : "center",
		buttons : [{
			text : "保存",
			iconCls : 'save',
			handler : function() {
				if (form.getForm().isValid()) {
					var cusRecordIds="";
					for(var i=0;i<records.length;i++){
						cusRecordIds += records[i].data.id;
						if(i!=(records.length-1)){
							cusRecordIds +=',';
						}
					}
					var userId=Ext.getCmp('assescusService').getValue();
					form.getForm().submit({
						url : sysPath + "/cus/cusServiceAction!saveCusService.action?privilege=157",
						params:{
							cusRecordIds:cusRecordIds,
							userId:userId,
							flag:true
						},
						waitMsg : '正在保存数据...',
						success : function(form, action) {
							win.hide(), 
							Ext.Msg.alert(alertTitle,
									'保存成功！');
						},
						failure : function(form, action) {
							if (action.faliureType == Ext.form.Action.SERVER_INVALID) {
								Ext.Msg.alert(alertTitle, "服务器连接异常，请联系管理员！");
							} else {
								if (action.result.msg) {
									win.hide();
									Ext.Msg.alert(alertTitle,
											action.result.msg);
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
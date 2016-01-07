//短信发送记录查询JS
var privilege=266;//权限参数
var gridSearchUrl="message/smsSendsmsRecordAction!list.action";//查询地址
var authorityDepartUrl='sys/basRightDepartAction!findDepartName.action';//权限部门查询地址
var searchWidth=70;
var searchDepart=bussDepartName;
var  fields=[{name:"id",mapping:'id'},
    		{name:'tel',mapping:'tel'},
     		{name:'context',mapping:'context'},
     		{name:'sendDepart',mapping:'sendDepart'},
     		{name:'sendName',mapping:'sendName'},
     		{name:'sysNo',mapping:'sysNo'},
     		{name:'smstype',mapping:'smstype'},
     		{name:'receiver',mapping:'receiver'},
     		{name:'billno',mapping:'billno'},
     		{name:'ipAddr',mapping:'ipAddr'},
     		{name:'createTime',mapping:'createTime'},
     		{name:'createName',mapping:'createName'},
     		{name:'systemName',mapping:'systemName'}];
     	
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
    var expander = new Ext.grid.RowExpander({
		tpl : new Ext.Template(//'<p style=margin-left:70px;><span style=color:Teal;>项目ID</span><br><span>{xmid}</span></p>',
				'<p style=margin-left:70px;><span style=color:Teal;>发送内容</span><br><span>{context}</span></p>'),
		// 设置行双击是否展开
		expandOnDblClick : true
	});
        
	var tbar=[{text:'<b>导出</b>',iconCls:'sort_down',handler:function() {
                	parent.exportExl(smsSendsmsRecordGrid);
        } },'-',
	 	'创建时间：',{
    		xtype : 'datefield',
    		id : 'startDate',
    		format : 'Y-m-d',
    		emptyText : "开始时间",
    		anchor : '95%',
    		value:new Date().add(Date.DAY, -7),
    		//hidden : true,
    		width : 100,
    		//disabled : true,
    		listeners : {
    			'select' : function() {
    			   var start = Ext.getCmp('startDate').getValue()
    			      .format("Y-m-d");
    			   Ext.getCmp('endDate').setMinValue(start);
    		     }
    		}
    	}, '&nbsp;&nbsp;至：', {
    		xtype : 'datefield',
    		id : 'endDate',
    		format : 'Y-m-d',
    		emptyText : "结束时间",
    		value:new Date(),
    		//hidden : true,
    		width : 100,
    		//disabled : true,
    		anchor : '95%',
    		enableKeyEvents:true,listeners : {
	 		keyup:function(textField, e){
                 if(e.getKey() == 13){
                 	searchsmsSendsmsRecord();
                 }
	 		}
    	}
    	},'-',{xtype:'textfield',blankText:'查询数据',id:'searchItemsContent', 
	 			enableKeyEvents:true,listeners : {
	 			keyup:function(textField, e){
                     if(e.getKey() == 13){
                     	searchsmsSendsmsRecord();
                     }
	 			}
	 		}
	 	},'-',{ 
   					xtype : "combo",
   					width : 100,
   					triggerAction : 'all',
   					id:'searchSelectBox',
   					model : 'local',
   					hiddenId : 'checkItems',
   					hiddenName : 'checkItems',
   					store : [['', '查询全部'],
   							['LIKES_sendName','发送人'],
   							['LIKES_ipAddr','发送IP'],
   							['LIKES_receiver','接收对象'],
   							['EQS_systemName','系统名称'],
   							['EQL_sysNo','发送平台编号'],
   							['LIKES_smstype','信息类型']],
    					emptyText : '选择类型',
    					editable : false,
    					forceSelection : true,
    					listeners : {
    						keyup:function(combo, e){
			                     if(e.getKey() == 13){
			                     	searchsmsSendsmsRecord();
			                     }
				 			}
    					}
    					
    		}
	 	];	

var queryTbar=new Ext.Toolbar([
		'部门名称：',
			{xtype : 'combo',
				width : searchWidth+20,
				triggerAction : 'all',
				id:'searchAuthorityDepart',
				pageSize:comboSize,
				store : authorityDepartStore,
				resizable:true,
				forceSelection : true,
				valueField : 'departId',//value值，与fields对应
				displayField : 'departName',//显示值，与fields对应
				editable : false,
				name : 'departId'
				
			},
			'-','短信内容:',{
	 		xtype:'textfield',id:'searchContent',width : searchWidth+20,
	 		 enableKeyEvents:true,listeners : {
				keyup:function(textField, e){
                 if(e.getKey() == 13){
                 	searchsmsSendsmsRecord();
                 }
	 		}
	 	}},'-','接收号码:',{
	 		xtype:'textfield',id:'searchTel',width : searchWidth+20,
	 		 enableKeyEvents:true,listeners : {
				keyup:function(textField, e){
                 if(e.getKey() == 13){
                 	searchsmsSendsmsRecord();
                 }
	 		}
	 	}},'-',{text : '<b>查询</b>',id : 'btn',iconCls : 'btnSearch',
    			handler : searchsmsSendsmsRecord
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
    smsSendsmsRecordGrid = new Ext.grid.GridPanel({
        renderTo: Ext.getBody(),
        id:'smsSendsmsRecordCenter',
        height:Ext.lib.Dom.getViewHeight(),		
        width:Ext.lib.Dom.getViewWidth(),
		autoScroll:true, 
        frame:true, 
        loadMask:true,
        plugins : [expander],
        sm:sm,
        cm:new Ext.grid.ColumnModel([expander,rowNum,sm,
        		{header: '序号ID', dataIndex: 'id',sortable:true,width:60,hidden:true},
       			{header: '系统名称', dataIndex: 'systemName',width:80,sortable : true},
        		{header: '发送部门', dataIndex: 'sendDepart',width:100,sortable : true},
        		{header: '发送人', dataIndex: 'sendName',width:80,sortable : true},
 				{header: '发送内容', dataIndex: 'context',width:300,sortable : true},
 				{header: '接收号码', dataIndex: 'tel',width:80,sortable : true},
 				{header: '发送IP', dataIndex: 'ipAddr',width:80,sortable : true},
 				{header: '接收对象', dataIndex: 'receiver',width:80,sortable : true},
 				{header: '信息类型', dataIndex: 'smstype',width:100,sortable : true},
 				{header: '创建人', dataIndex: 'createName',width:100,sortable : true},
 				{header: '创建时间', dataIndex: 'createTime',width:100,sortable : true},
 				{header: '发送平台编号', dataIndex: 'sysNo',width:80,sortable : true},
 				{header: '运单号', dataIndex: 'billno',width:80,sortable : true}
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
		
    });
    
   function searchsmsSendsmsRecord() {
			var searchAuthorityDepart=Ext.get('searchAuthorityDepart').dom.value;
			var searchContent=Ext.getCmp('searchContent').getValue();
			var searchTel = Ext.get('searchTel').dom.value;
			
			if(searchAuthorityDepart.length>0){
				searchDepart=searchAuthorityDepart;
			}
			dateStore.baseParams = {
				filter_GED_createTime:(!Ext.getCmp("startDate").hidden?(Ext.get("startDate").dom.value=="开始时间"?"":Ext.get("startDate").dom.value):""),
    			filter_LED_createTime : (!Ext.getCmp("endDate").hidden?(Ext.get("endDate").dom.value=="结束时间"?"":Ext.get("endDate").dom.value):""),
    			filter_EQS_sendDepart:searchDepart,
    			filter_LIKES_context:searchContent,
    			filter_LIKES_tel:searchTel,
    			privilege:privilege,
				limit : pageSize,
    			itemsValue : Ext.get('searchItemsContent').dom.value,
				checkItems : Ext.get('checkItems').dom.value
			}
    	
		dateStore.load();
	}
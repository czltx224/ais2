//客户自定义查询权限控制js
var privilege=175;//权限参数
var gridSearchUrl="cus/cusSearchAction!ralaList.action";//客户自定义查询权限控制查询地址
var findSearchListUrl='cus/cusSearchAction!findSearchList.action';//客户自定义查询查询地址
var gridDelUrl="cus/cusSearchAction!delete.action";//客户自定义查询权限控制删除地址
var gridAuthorizedUrl="cus/cusSearchAction!authorized.action";//客户自定义查询授权地址
var departUrl='sys/departAction!findAll.action';//部门查询地址
var searchWidth=80;
var colWidth=100;
//var  fields=['id','tableCh','tableEn','searchStatement','departCode','title','createTime','createName','updateTime','updateName','ts','searchChinese','departName'];
var  fields=[{name:'id',mapping:'ID'},
			{name:'tableCh',mapping:'TABLECH'},
			{name:'tableEn',mapping:'TABLEEN'},
			{name:'searchStatement',mapping:'SEARCHSTATEMENT'},
			{name:'departCode',mapping:'DEPARTCODE'},
			{name:'title',mapping:'TITLE'},
			{name:'createTime',mapping:'CREATETIME'},
			{name:'createName',mapping:'CREATENAME'},
			{name:'updateTime',mapping:'UPDATETIME'},
			{name:'updateName',mapping:'UPDATENAME'},
			{name:'ts',mapping:'TS'},
			{name:'searchChinese',mapping:'SEARCHCHINESE'},
			{name:'departName',mapping:'DEPARTNAME'}];

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
	var tbar=[
		{text:'<b>授权</b>',iconCls:'userEdit',id:'cusSearchAuthorityAdd',tooltip : '授权客户自定义查询',handler:function() {
            var cusSearchAuthorityRate =Ext.getCmp('cusSearchAuthorityRateCenter');
		    var _records = cusSearchAuthorityRate.getSelectionModel().getSelections();
			if (_records.length < 1) {
				Ext.Msg.alert(alertTitle, "请选择您要授权的行！");
				return false;
			}
            var ids="";
			for(var i=0;i<_records.length;i++){
				 ids=ids+_records[i].data.id+',';
				 if(_records[i].data.createName!=userName){
				 	Ext.Msg.alert(alertTitle, "您选择的行中有不是你创建的，不能授权！");
					return false;
				 }
			} 
            cusSearchAuthority(ids);
         } },'-',
	 	
        {text:'<b>删除</b>',iconCls:'userDelete',id:'cusSearchAuthorityDelete',tooltip : '删除客户自定义查询',handler:function() {
         	var cusSearchAuthorityRate =Ext.getCmp('cusSearchAuthorityRateCenter');
		    var _records = cusSearchAuthorityRate.getSelectionModel().getSelections();
			if (_records.length < 1) {
				Ext.Msg.alert(alertTitle, "请选择您要删除的行！");
				return false;
			} 
            var ids="";
			for(var i=0;i<_records.length;i++){
				 ids=ids+_records[i].data.id+',';
				 if(_records[i].data.createName!=userName){
				 	Ext.Msg.alert(alertTitle, "您选择的行中有不是你创建的，不允许删除！");
					return false;
				 }
			}
			
			Ext.Msg.confirm(alertTitle, "确定要删除"+_records.length+"条记录吗？", function(
							btnYes) {
				if (btnYes == 'yes' || btnYes == 'ok'
						|| btnYes == true) {
					
					Ext.Ajax.request({
						url : sysPath+'/'
								+ gridDelUrl,
						params : {
							ids : ids,
						    privilege:privilege,
						    limit : pageSize
						},
						success : function(resp) {
							var respText = Ext.util.JSON
									.decode(resp.responseText);
							if(respText.success){
								Ext.Msg.alert(alertTitle,"删除成功!");
								dataReload();
							}else{
								Ext.Msg.alert(alertTitle,respText.msg);
							}
						}
					});
				}
			});
	
        } },'-',
	 	{text:'<b>导出</b>',iconCls:'sort_down',tooltip : '导出客户自定义查询',handler:function() {
               parent.exportExl(cusSearchAuthorityRateGrid);
        } },'-',
        {text:'<b>查询</b>',iconCls:'btnSearch',tooltip : '查询客户自定义查询',handler:function() {
            	searchcusSearchAuthority();
        } }
	 	];	

var queryTbar=new Ext.Toolbar([
		'创建日期','-',{xtype : 'datefield',
    		id : 'startDate',
    		format : 'Y-m-d',
    		emptyText : "开始时间",
    		anchor : '95%',
    		//value:new Date().add(Date.DAY, -7),
    		hidden : false,
    		width : 100,
    		disabled : false,
    		listeners : {
    			'select' : function() {
    			   var start = Ext.getCmp('startDate').getValue()
    			      .format("Y-m-d");
    			   Ext.getCmp('endDate').setMinValue(start);
    		     }
    		}
    	}, '&nbsp;至&nbsp;', {
    		xtype : 'datefield',
    		id : 'endDate',
    		format : 'Y-m-d',
    		emptyText : "结束时间",
    		value:new Date(),
    		hidden : false,
    		width : 100,
    		disabled : false,
    		anchor : '95%'
    	},'-',{xtype:'textfield',blankText:'查询数据',id:'searchContent', 
			width:searchWidth,
		 	enableKeyEvents:true,listeners : {
	 		keyup:function(textField, e){
               if(e.getKey() == 13){
               	searchcusSearchAuthority();
               }
	 		}
	 	}},'-',{ 
	 	
  					xtype : "combo",
  					width : searchWidth,
  					triggerAction : 'all',
  					id:'searchSelectBox',
  					model : 'local',
  					hiddenId : 'checkItems',
  					hiddenName : 'checkItems',
  					name : 'checkItemstext',
  					store : [['','查询全部'],
  							['LIKES_title', '查询名称'],
  							['LIKES_tableCh','表中文名'],
  							['LIKES_tableEn','表英文名'],
  							['LIKES_t_createName','创建人'],
  							['LIKES_t_updateName','修改人']],
  							
  					emptyText : '选择查询方式',
  					editable : false,
  					forceSelection : true,
  					listeners : {
  						'select' : function(combo, record, index) {
							if(Ext.getCmp("searchContent").getValue().length>0){
								searchcusSearchAuthority();
							}
  						}
  					}
    					
    		}
	 	]);	
	 	
Ext.onReady(function() {
   	dateStore = new Ext.data.Store({
        storeId:"dateStore",
        proxy: new Ext.data.HttpProxy({url : sysPath+ "/"+findSearchListUrl,
						params:{privilege:privilege,limit : pageSize}}),
        reader: new Ext.data.JsonReader({
            root: 'resultMap', totalProperty: 'totalCount'
        }, fields)
    });
   
   var sm = new Ext.grid.CheckboxSelectionModel({});
	var rowNum = new Ext.grid.RowNumberer({
        header:'序号', width:20
    });
    cusSearchAuthorityRateGrid = new Ext.grid.GridPanel({
        renderTo: Ext.getBody(),
        id:'cusSearchAuthorityRateCenter',
        height:Ext.lib.Dom.getViewHeight(),		
        width:Ext.lib.Dom.getViewWidth(),
        viewConfig : {
			columnsText : "显示的列",
			sortAscText : "升序",
			sortDescText : "降序"
//			forceFit : true
		},
		autoScroll:false, 
//		autoExpandColumn:1,
        frame:true, 
        loadMask:true,
        sm:sm,
        cm:new Ext.grid.ColumnModel([rowNum,sm,
        		{header: 'ID', dataIndex: 'id',sortable : true,width:colWidth,hidden:true},
       			{header: '查询名称', dataIndex: 'title',sortable : true,width:colWidth},
       			{header: '表中文名', dataIndex: 'tableCh',sortable : true,width:colWidth},
        		{header: '表英文名', dataIndex: 'tableEn',sortable : true,width:colWidth,hidden:true},
        		{header: '查询条件SQL', dataIndex: 'searchStatement',sortable : true,width:colWidth*2,hidden:true},
        		{header: '查询条件', dataIndex: 'searchChinese',sortable : true,width:colWidth*2},
 				{header: '分配部门编码', dataIndex: 'departCode',sortable : true,width:colWidth,hidden:true},
 				{header: '分配部门名称', dataIndex: 'departName',sortable : true,width:colWidth},
 				{header: '创建人', dataIndex: 'createName',sortable : true,width:colWidth},
 				{header: '创建时间', dataIndex: 'createTime',sortable : true,width:colWidth+40},
 				{header: '修改人', dataIndex: 'updateName',sortable : true,width:colWidth},
 				{header: '修改时间', dataIndex: 'updateTime',sortable : true,width:colWidth+40},
 				{header: '时间戳', dataIndex: 'ts',sortable : true,width:colWidth,hidden:true}
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
	
	    cusSearchAuthorityRateGrid.on('click', function() {
	       selabled();
	     	
	    });
    });
	
   function searchcusSearchAuthority() {
		dateStore.proxy = new Ext.data.HttpProxy({
					method : 'POST',
					url : sysPath+ "/"+findSearchListUrl,
					params:{privilege:privilege,limit : pageSize}
				});
			Ext.apply(dateStore.baseParams, {
				filter_countCheckItems:'t.create_time',
				filter_startCount:(!Ext.getCmp("startDate").hidden?(Ext.get("startDate").dom.value=="开始时间"?"":Ext.get("startDate").dom.value):""),
				filter_endCount:(!Ext.getCmp("endDate").hidden?(Ext.get("endDate").dom.value=="结束时间"?"":Ext.get("endDate").dom.value):""),
				privilege:privilege,
				limit : pageSize,
				itemsValue : Ext.get("searchContent").dom.value,
				checkItems : Ext.get("checkItems").dom.value
			});
		var deletebtn = Ext.getCmp('cusSearchAuthorityDelete');
		deletebtn.setDisabled(true);
		dataReload();
	}
 function cusSearchAuthority(ids){
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
													allowBlank:false,
													fieldLabel : '授权到：',
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
		title : '自定义查询授权',
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
								+ gridAuthorizedUrl+"?privilege="+privilege,
						params :{ids:ids,departId:departNameId},
						success : function(resp) {
							var respText = Ext.util.JSON.decode(resp.responseText);
							if(respText.success){
								Ext.Msg.alert(alertTitle,'授权成功！');
								win.close();
								searchcusSearchAuthority();
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
 	 var _record = cusSearchAuthorityRateGrid.getSelectionModel().getSelections();
 	
        var deletebtn = Ext.getCmp('cusSearchAuthorityDelete');
        if (_record.length==1) {       	
            if(deletebtn){
            	deletebtn.setDisabled(false);
            }
        }else if(_record.length>1){
            if(deletebtn){
            	deletebtn.setDisabled(false);
            }
        } else {
			if(deletebtn){
            	deletebtn.setDisabled(true);
			}
        }
 }

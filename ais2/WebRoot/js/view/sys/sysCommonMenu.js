
Ext.onReady(function(){
	
	var fields=[{name:"id",mapping:'id'},
	'accessValue',
	'name',
	'href','nodeId',
	'createName',
	'createTime',
	'updateName',
	'updateTime',
	'ts','sortNum',
	'userId',
	'userName'];
	
	var jsonread= new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'},
                    fields);
	
    var tree = new Ext.tree.TreePanel({
        renderTo:Ext.getBody(),
        title: '系统菜单',
       	height : Ext.lib.Dom.getViewHeight()-15,
		width : Ext.lib.Dom.getViewWidth()*.35-5,
        useArrows:false,
        autoScroll:true,
        animate:true,
        enableDD:true,
        containerScroll: true,
        rootVisible: false,
        frame: true,
        checked : false,
        root: new Ext.tree.AsyncTreeNode({
						id : '0',
						text : '系统菜单',
						// 显示一个未选中的复选框
						checked : false,
						expanded : true

					}),
        
        // auto create TreeLoader
        dataUrl: sysPath+'/login/loginAction!getCommonMenu.action',
        
        listeners: {
            'checkchange': function(node, checked){
                if(checked){
                    node.getUI().addClass('complete');
                }else{
                    node.getUI().removeClass('complete');
                }
            }
        },
        
        buttons: [{
            text: '<B>重新加载</B>',
            iconCls : 'refresh',
            handler: function(){
                  tree.root.reload();
            }
        },'-',{
            text: '<B>保存设置</B>',
            iconCls : 'groupSave',
            handler: function(){
                var ids = '', selNodes = tree.getChecked();
                var i=0;
                Ext.each(selNodes, function(node){
					if(node.leaf==true){
						
						if(ids.length > 0){
                        	ids += '&';
                    	}
	                    ids += "sysCmMuList["+i+"].accessValue="+node.attributes.orderNum+"&sysCmMuList["+i+"].name="+node.text+
	                    	"&sysCmMuList["+i+"].nodeId="+node.id+
	                    	"&sysCmMuList["+i+"].href="+node.attributes.href1+
	                    	"&sysCmMuList["+i+"].userName="+userName+
	                    	"&sysCmMuList["+i+"].sortNum="+i+
	                    	"&sysCmMuList["+i+"].userId="+userId;
	                    i=i+1;
	                    
					}
                });
               	if(ids.length <= 0){
	                Ext.Msg.show({
	                    title: alertTitle, 
	                    msg : '无数据保存，您没有选择任何菜单或者选择的不是菜单。',
	                    icon: Ext.Msg.INFO,
	                    minWidth: 200,
	                    buttons:  Ext.Msg.OK
	                });
                }else{
                	  Ext.Msg.confirm(alertTitle,'您确定要保存这些数据吗?',function(btnYes) {
						if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {	
							form1.getForm().doAction('submit', {
								url : sysPath+ "/sys/sysCommonMenuAction!save.action",
								method : 'post',
								params :  ids,
								waitMsg : '正在保存数据...',
								success : function(form1, action) {
									Ext.Msg.alert(
											alertTitle,
											action.result.msg,
											function() {
												dataStore.reload();
												parent.commonMenuLoad();
											});
								},
								failure : function(form1, action) {
									Ext.Msg.alert(
											alertTitle,
											action.result.msg,
											function() {
												dataStore.reload();
											});
								}
							});
						}
					});
                
                
                
                }
            }
        }]
    });
	
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
	
	var sm = new Ext.grid.CheckboxSelectionModel();
	
	var cm=new Ext.grid.RowNumberer({
						header:'序号',
				        width : 35,
						sortable : true	
					})
	
	var dataStore = new Ext.data.Store({
        storeId:"dataStore",
        proxy: new Ext.data.HttpProxy({url:sysPath+"/sys/sysCommonMenuAction!list.action"}),
        baseParams:{
			filter_EQL_userId:userId,
			limit : 200        		
        },
        reader:jsonread,
        sortInfo:{field:'sortNum',direction:'ASC'}
    });

		var fourbar = new Ext.Toolbar({	frame : true,
			items : ['&nbsp;','创建时间:',{
				xtype : 'datefield',
    			id : 'startDate',
	    		format : 'Y-m-d',
	    		emptyText : "选择开始时间",
	    		anchor : '95%',
	    		width : 100,
	    		listeners : {
	    			'select' : function() {
	    			   var start = Ext.getCmp('startDate').getValue()
	    			      .format("Y-m-d");
	    			   Ext.getCmp('endDate').setMinValue(start);
	    		     }
    		    }
			},'&nbsp;','至','&nbsp;',{
				xtype : 'datefield',
	    		id : 'endDate',
	    		format : 'Y-m-d',
	    		emptyText : "选择结束时间",
	    		width : 100,
	    		anchor : '95%'
			},'&nbsp;','-',{
			     text : '<b>删除</b>',
			     id : 'delbtn',
			     iconCls : 'userDelete',
				 handler : function(){
				 	deleteCommonMenu();
				 }
			},{
			     text : '<b>查询</b>',
			     id : 'btn',
			     iconCls : 'btnSearch',
				 handler : function(){
				 	searchLog();
				 }
			}]
		});

	  var recordGrid=new Ext.grid.GridPanel({
			id:'userCenter',
			height : Ext.lib.Dom.getViewHeight()-8,
			width :Ext.lib.Dom.getViewWidth()*.65-5,
			autoScroll : true,  //面板上的body元素
			viewConfig : {
				scrollOffset: 0,
				autoScroll:true
			},
			frame : true,
			border : true,
			tbar:fourbar,
		//	enableColumnResize:false, //关闭列的自适大小功能
			autoWidth:false,
			loadMask : true,
			sm:sm,
			stripeRows : true,
			columns:[ cm,
					sm,
					 {header: 'ID', dataIndex: 'id',width:80,hidden : true},
			        {header: '权限值', dataIndex: 'nodeId',width:60,hidden : true},
			        {header: '菜单名称', dataIndex: 'name',width:120,sortable : true},
			         {
					header : '排序操作',
					sortable : true,
					dataIndex : 'sortNum',
					width : 120,
					align : 'center',
					renderer : function(value, metaData, record, rowIndex,
							colIndex, store) {
						var size = dataStore.getTotalCount();
						if(size!=1){
							if (rowIndex + 1 == 1) {// 第一行,显示向下
								return "<div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<image src='../images/arrow_down.gif' id='downimage' onclick='downOnClick();'/></div>";
							} else if (rowIndex + 1 == size) {// 最后一行,显示向上
								return "<div><image src='../images/arrow_up.gif' id='upimage' onclick='upOnClick();'></div>";
							} else {// 同时显示向上和向下
								return "<div><image src='../images/arrow_up.gif' id='upimage' onclick='upOnClick();'/>&nbsp;&nbsp;&nbsp;<image src='../images/arrow_down.gif' id='downimage' onclick='downOnClick();'/></div>";
							}
						}
					}

				},
			        {header: '路径', dataIndex: 'href',width:120,sortable : true,hidden : true},
			        {header: '用户姓名', dataIndex: 'userName',hidden : true,width:80,sortable : true},
			        {header: '时间戳', dataIndex: 'ts',width:60,hidden : true},
			        {header: '排序', dataIndex: 'sortNum',width:60},
			        {header: '创建人', dataIndex: 'createName',width:80},
					{header: '创建时间', dataIndex: 'createTime',width:120,sortable : true},
					{header: '修改人', dataIndex: 'updateName',width:80},
					{header: '修改时间', dataIndex: 'updateTime',sortable : true,width:120}
			    ],
			store : dataStore,
			bbar : new Ext.PagingToolbar({
					id:'toolBar2',
					pageSize : 200, 
					store : dataStore,
					displayInfo : true,
					displayMsg : '显示第 {0} 条到 {1} 条记录，共 {2} 条',
					emptyMsg : "没有记录信息显示"
					
			})
	});
    
     var form = new Ext.form.FormPanel({
		labelAlign : 'left',
		frame : true,
		border : false,
		renderTo:Ext.getBody(),
		bodyStyle : 'padding:1px 1px 1px',
	    height : Ext.lib.Dom.getViewHeight(),
		width : Ext.lib.Dom.getViewWidth(),
		labelAlign : "right",
		listeners: {
            render: function(){
               // twobar.render(this.tbar);
              //  threebar.render(this.tbar);
            }
          },
        items : [{
			layout : 'column',
			items : [{
				columnWidth : .35,
				layout : 'form',
				items : [tree]

			},{
				columnWidth : .65,
				layout : 'form',
				items : [recordGrid]
			}]
					
		}]
	});
     
		form.render();
    
     //   tree.getRootNode().expand(true);
        
  	function searchLog() {
		var start='';
    	var end ='';
    	if(Ext.getCmp('startDate').getValue()!=""){
    		var start = Ext.getCmp('startDate').getValue().format("Y-m-d");
    	}
    	if(Ext.getCmp('endDate').getValue()!=""){
    		var end = Ext.getCmp('endDate').getValue().format("Y-m-d");
    	}
		
		Ext.apply(dataStore.baseParams, {
			filter_GED_createTime : start,
			filter_LED_createTime : end
		});

		dataStore.reload({
			params : {
				start : 0,
				limit : 200
			}
		});
	}
        
        function deleteCommonMenu(){
			  var _records = recordGrid.getSelectionModel().getSelections();
			  if (_records.length == 0) {
				 Ext.Msg.alert(alertTitle, '请选择一条您需要删除的数据');
				 return false;
			  }else {  
			     Ext.Msg.confirm(alertTitle,'数据删除后将不可恢复，您确定要删除这<span style="color:red">&nbsp;'+ _records.length + '&nbsp;</span>行数据吗?',function(btnYes) {
					if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
								
			        var ids = "";
					for(var i = 0; i < _records.length; i++) {
						ids += _records[i].data.id + ",";
					}  	
					form1.getForm().doAction('submit', {
						url : sysPath+ "/sys/sysCommonMenuAction!delete.action",
						method : 'post',
						params : {
							ids : ids
						},
						waitMsg : '正在删除数据...',
						success : function(form1, action) {
							Ext.Msg.alert(
									alertTitle,
									"删除成功",
									function() {
										dataStore.reload();
										parent.commonMenuLoad();
									});
						},
						failure : function(form1, action) {
							Ext.Msg.alert(
									alertTitle,
									"删除失败",
									function() {
										dataStore.reload();
									});
						}
					});
					}
				});
	   
	  }
	}
});
	/*
		点击图片向上调用此方法
	*/
	function upOnClick() {
		var grid = Ext.getCmp('userCenter');
		var record = grid.getSelectionModel().getSelected();
		Ext.getCmp("form1").form.doAction('submit', {
					url : sysPath+ "/sys/sysCommonMenuAction!changeSort.action",
					method : 'post',
					params : {
							id : record.get("id"),
							sort:'up'
						},
					waitMsg : '正在保存数据...',
					success : function(form, action) {
						Ext.Msg.alert(alertTitle, action.result.msg,
								function() {
									var dataStore=Ext.StoreMgr.get("dataStore");
									dataStore.reload();
									parent.commonMenuLoad();
								});
					},
					failure : function(form, action) {
						Ext.Msg.alert(alertTitle, action.result.msg);
					}
				});
	
	}
	
	/*
	点击图片向下调用此方法
	*/
	function downOnClick() {
		var grid = Ext.getCmp('userCenter');
		var record = grid.getSelectionModel().getSelected();
		Ext.getCmp("form1").form.doAction('submit', {
					url : sysPath+ "/sys/sysCommonMenuAction!changeSort.action",
					method : 'post',
					params : {
							id : record.get("id"),
							sort:'down'
						},
					waitMsg : '正在保存数据...',
					success : function(form, action) {
						Ext.Msg.alert(alertTitle, action.result.msg,
								function() {
									var dataStore=Ext.StoreMgr.get("dataStore");
									dataStore.reload();
									parent.commonMenuLoad();
								});
					},
					failure : function(form, action) {
						Ext.Msg.alert(alertTitle, action.result.msg);
					}
				});
	}

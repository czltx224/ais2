var dateStore;
var billjsonread;
var params ;
var jsonread;
var gridLoadMask;
var sm = new Ext.grid.CheckboxSelectionModel({
       		 
        }); 

Ext.ns("xbwl");  
 xbwl.MyPanelUi = Ext.extend(Ext.Panel, {

    autoWidth: true,
//    height: Ext.lib.Dom.getViewHeight(),
    renderTo: Ext.getBody(),
    renderTo: 'show',
    layout : 'border',
    constructor:function(config){
    	config = config || {};
        Ext.apply(this, config);
   	  	xbwl.MyPanelUi.superclass.constructor.call(this);
    },
    getGrid:function(){
    	return Ext.getCmp("center");
    },
    getForm:function(){
    	return Ext.getCmp("east");
    },

    initComponent: function() {
    	 jsonread= new Ext.data.JsonReader(
    	         {  root:'result',
                    totalProperty:'totalCount'
                  },this.fields);
         
        dateStore = new Ext.data.Store({
                proxy: new Ext.data.HttpProxy({url:sysPath+"/"+this.gridSearchUrl}),
                baseParams:{limit: pageSize},
                reader:jsonread
            });
    	

		dateStore.load();
 
 
        this.items = 
            [{  xtype : 'grid',
              	region : 'center',
              	id : "userCenter",
              	ds : dateStore,
			    title : this.gridTitle,
			    store : this.data,
			    columns : this.columns,
			    selModel : this.sm,
			    loadMask : true,
			    viewConfig : {
					columnsText : "显示的列",
					sortAscText : "升序",
					sortDescText : "降序",
					forceFit : true
				},
			   columnLines : true,
			   sm : sm,
           	   bbar: new Ext.PagingToolbar({
		            pageSize: pageSize,
		            store: dateStore,
		            displayInfo: true,
		            displayMsg : '显示第 {0} 条到 {1} 条记录，共 {2} 条',
					emptyMsg : "没有记录信息显示"
		        })}
		        ,
		        {
            	xtype : 'form',
				title : '新增和修改',
		        region : 'east',
		        id : 'east',
		        collapsed : true,
		        frame : true,
		        split : true,
		        collapsible : true,
		        border : false,
		        width : 300,
		        defaultType : 'textfield',
		        defaults : {width: 150},  
		        items : this.formItems,
		        buttonAlign : "center",
				reader : this.formReader,
		        listeners :{
		        	'beforerender':function(){
		        		gridLoadMask = new Ext.LoadMask(Ext.get('userCenter'),{msg:"界面已经锁定"});
		        	}, 	
		        	'beforeexpand' :function ( panel, animate ){
		        		gridLoadMask.show();
		        		 Ext.getCmp('btn').setDisabled(true);
		        		 Ext.getCmp('updatebtn').setDisabled(true);
	                     Ext.getCmp('deletebtn').setDisabled(true); 

		        } ,
		        	'beforecollapse':function ( panel, animate ){
	        			gridLoadMask.hide();
	        			 Ext.getCmp('btn').setDisabled(false);
	        			 var vnetmusicRecord = Ext.getCmp('userCenter').getSelectionModel().getSelections();
							var updatebtn = Ext.getCmp('updatebtn');
							var deletebtn = Ext.getCmp('deletebtn');
							if (vnetmusicRecord.length == 1) {
								updatebtn.setDisabled(false);
								deletebtn.setDisabled(false);
							} else if (vnetmusicRecord.length > 1) {
								updatebtn.setDisabled(true);
								deletebtn.setDisabled(false);
							} else {
								deletebtn.setDisabled(true);
								updatebtn.setDisabled(true);
							}
		      	  } 
		        },
		        tbar: [ {
				        text: '<b>提交</b>',
				        formBind : true, 
				        disabled : false,
				        id : 'form',
				        iconCls : 'groupSave',
				        handler : function() {
				            var form = Ext.getCmp("east").getForm();
				            if (form.isValid()) {
				            	this.disable();
				                form.submit({
				                	waitMsg : '数据处理中,请稍候...', 
				                	url:'basDictionaryAction!save.action', 
				                    success: function(form, action) {
				                       Ext.Msg.alert('成功', action.result.msg);
				                       dateStore.reload();
				                       	var e = Ext.getCmp('east');
				                       	e.collapse();
				                       	Ext.getCmp('btn').setDisabled(false);
				                    },
				                    failure: function(form, action) {
				                        Ext.Msg.alert('失败', action.result.msg);
				                    }
				                });
				                    this.enable();
				            }else {
				            	parent.Ext.Msg.alert('输入不规范', "  请重新输入  ");
				            }
				        }
				    },'-',{
				        text : '<b>重置</b>',
				        iconCls : 'refresh',
				        handler : function() {
				        	if(Ext.getCmp("id").getValue()==""){
				            	Ext.getCmp("east").getForm().reset();
				        	}else{
				        		Ext.getCmp("east").getForm().load({
				        		       url:sysPath+"/sys/basDictionaryAction!ralaList.action?privilege=18",
				        			   params:{filter_EQL_id:Ext.getCmp("id").getValue()}, 
				        			   waitMsg : '正在载入数据...',
				        				success:function(){},
				        				failure:function(){} 
				        		});
				        	}
				        }
				    }]
            
            } 
        ];
 
        xbwl.MyPanelUi.superclass.initComponent.call(this);

    }
 
});

Ext.onReady(function(){

  var  form1 = new Ext.form.FormPanel({
	   				id : 'form1',
	   				frame : true,
	   				width : 100,
	   				cls : 'displaynone',
	   				hidden : true,
	   				items : [],
	   				buttons : []
	   			});
		form1.render(document.body);
    	   var menuStore = new Ext.data.Store({ 
            storeId:"menuStore",                        
            proxy: new Ext.data.HttpProxy({url:sysPath+"/sys/dictionaryAction!list.action",method:'post'}),
            reader: new Ext.data.JsonReader({
              root: 'result',
              id: 'id'
              }, [    
                   {name:'name'}, 
                   {name:'id'}
                 ]
             ), 
            sortInfo:{field:'id',direction:'ASC'}
         });

         	menuStore.load();
     var fields=[{name:"id",mapping:'id'},'typeName','typeCode','basDictionaryId','basDictionaryName'];
     
      
      
        var formItems=[  { id:'id', name: 'id',hidden:true},
        				  {xtype : 'combo',
        				   model : 'local',
        				   fieldLabel: '类别名称',
				           allowBlank : false,
					       minChars : 1,
					       triggerAction : 'all',
						   store: menuStore,
						   pageSize : 20,
						   valueField : 'id',
						   displayField : 'name',
						   hiddenName : 'basDictionaryId',
						   emptyText : "请选择类型"	
        				},
	        			{  fieldLabel: '条目名称', 
	        			   name: 'typeName',
	        			   maxLength : 20,
	        			   allowBlank : false,
			               blankText : '必须填写条目名称'
		                 },
        				{fieldLabel: '类别编码', name: 'typeCode'},
        				{ name: 'privilege',hidden:true,value:18}
		                ];

        var columns= [
        			sm,
        		    {header: 'ID', dataIndex: 'id',sortable : true},
        			{header: '条目名称', dataIndex: 'typeName',sortable : true},
			        {header: '类别编码', dataIndex: 'typeCode',sortable : true},
    				{header: '类别名称', dataIndex: 'basDictionaryName'}
			    ];
	  params={privilege:14};	
	 

	 var tbar=[{text:'<b>新增</b>',
	            iconCls:'userAdd',
	            id : 'addbtn',
	            tooltip : '新增字典',
	            handler : function() {
	            	Ext.getCmp("east").getForm().reset();
					getForm();
					
		  		} 	
		  	},
	            '-',
	 	{text:'<b>修改</b>',
	 	 disabled : true,
	 	 iconCls:'userEdit',
	 	 id : 'updatebtn',
	 	 tooltip : '修改字典',
	 	  handler : function() {
			saveForm();
		  } 
	 	 
	 	 },
	 	 '-',
	 	{	text:'<b>删除</b>',
		 	disabled : true,
			id : 'deletebtn',
		 	iconCls:'userDelete',
		 	tooltip : '删除字典',
		 	handler : function() {
				deleteForm();
			} 
	 	},
	 	'-',
	 	{xtype:'textfield',
	 	 id : 'itemsValue',
		 name : 'itemsValue',
	     blankText:'查询数据'},
	 	'-',{
    					xtype : "combo",
    					width : 100,
    					id:"comboselect",
    					triggerAction : 'all',
    					model : 'local',
    					hiddenId : 'checkItems',
    					hiddenName : 'checkItems',
    					name : 'checkItemstext',
    					store : [['','查询全部'],
    					         ['EQS_id','ID'],
    							 ['LIKES_typeName','条目名称'],
    							 ['EQS_typeCode','类型编码'],
    							 ['LIKES_basDictionaryName','类别名称']
    							 ],
    					emptyText : '选择类型',
    					forceSelection : true,
    					listeners : {
    						'select' : function(combo, record, index) { // override default onSelect to
    												// do redirect
    							if (combo.getValue() == '') {
    								Ext.getCmp("itemsValue").disable();
    								Ext.getCmp("itemsValue").show();
    								Ext.getCmp("itemsValue").setValue("");
    							} else {
    								Ext.getCmp("itemsValue").enable();
    								Ext.getCmp("itemsValue").show();
    							}
    						}
    					}
    					
    				},
    				'-',{text : '<b>搜索</b>',
    				       id : 'btn',
    				       iconCls : 'btnSearch',
    					   handler : searchLog,
    					   disabled : false
    				}
    				];	
	 	
	 	
	 	
	 	var billjsonread= new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
                }, [
                    {name:'basDictionaryId',mapping:"basDictionaryId"},
                    {name:'basDictionaryName',mapping:'basDictionaryName'},
                    {name:'typeName',mapping:'typeName'},
                    {name:'id',mapping:'id'},
                    {name:'typeCode',mapping:'typeCode'}
                ]);
                    
	 var my=new xbwl.MyPanelUi({
								 gridSearchUrl:"sys/basDictionaryAction!ralaList.action?privilege=18",
								 saveUrl:"sys/basDictionaryAction!input.action?privilege=18",
								 fields:fields,
								 sm:sm,
								 formReader:billjsonread,
								 height: Ext.lib.Dom.getViewHeight() ,
								 width: Ext.lib.Dom.getViewWidth() ,
								 itemsPerPage:pageSize,
								 columns:columns,
								 formItems:formItems,
								 params:params,
								 tbar:tbar
								 }).show();   
		my.getGrid();
		
		Ext.getCmp('userCenter').on('click', function() {
				var vnetmusicRecord = Ext.getCmp('userCenter').getSelectionModel().getSelections();
				var updatebtn = Ext.getCmp('updatebtn');
				var deletebtn = Ext.getCmp('deletebtn');
				if (vnetmusicRecord.length == 1) {
					updatebtn.setDisabled(false);
					deletebtn.setDisabled(false);
				} else if (vnetmusicRecord.length > 1) {
					updatebtn.setDisabled(true);
					deletebtn.setDisabled(false);
				} else {
					deletebtn.setDisabled(true);
					updatebtn.setDisabled(true);

				}
			});
   function deleteForm(){
	  var e = Ext.getCmp('east');  //
	  var form = Ext.getCmp("east").getForm();
	  var _records = Ext.getCmp('userCenter').getSelectionModel().getSelections();
	  if (_records.length == 0) {
		 parent.Ext.Msg.alert('系统消息', '请选择一条您需要删除的数据');
		 return false;
	  }else {
	     parent.Ext.Msg.confirm('系统消息','数据删除后将不可恢复，您确定要删除这'+ _records.length + '行数据吗?',function(btnYes) {
										if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
											//	this.deletebtn.disabled = true;
									        var ids = "";
    										for(var i = 0; i < _records.length; i++) {
												ids += _records[i].data.id + ",";
											}  	
									        	
										    form1.getForm().doAction('submit', {
											url : sysPath+ "/sys/basDictionaryAction!delete.action",
											method : 'post',
											params : {ids : ids},
											waitMsg : '正在删除数据...',
											success : function(form, action) {
												      parent.Ext.Msg.alert(
														"系统提示",
														"数据删除成功",
														function() {
															dateStore.reload()
														});
											},
											failure : function(form, action) {
												parent.Ext.Msg.alert(
														"系统提示",
														"数据删除失败",
														function(){
															dateStore.reload()
														});
											}
										});
										}
									});
	   
	  }
	}
	function searchLog() {
		  dateStore.proxy = new Ext.data.HttpProxy({
					method : 'POST',
					url : sysPath+'/sys/basDictionaryAction!ralaList.action?privilege=18'
		  });

		 dateStore.baseParams = {
			checkItems : Ext.get("checkItems").dom.value,
		 	itemsValue : Ext.get("itemsValue").dom.value
		 }
		 Ext.getCmp('addbtn').setDisabled(false);
		 Ext.getCmp('updatebtn').setDisabled(false);
		 dateStore.reload({
					params : {
						start : 0,
						limit : pageSize
					}
				});
	} 

	function saveForm(){
	  var _records = Ext.getCmp('userCenter').getSelectionModel().getSelections();
	  var e = Ext.getCmp('east');
	  if ((_records.length == 0)) {
		 parent.Ext.Msg.alert('系统消息', '请选择一条您需要修改的数据');
		 return false;
	  }else if(_records.length>1) {
 		 parent.Ext.Msg.alert('系统消息', '只能选择一条数据进行修改');
		 return false;
	  }else{
	     e.expand();
	     Ext.getCmp('updatebtn').setDisabled(true);
	     Ext.getCmp('deletebtn').setDisabled(true); 
	     Ext.getCmp('btn').setDisabled(true);
	  }
	}
	

	function getForm(){
		Ext.getCmp('east').expand();
     	Ext.getCmp('updatebtn').setDisabled(true);
	    Ext.getCmp('deletebtn').setDisabled(true);
	    Ext.getCmp('btn').setDisabled(true);
	}

});
	

	

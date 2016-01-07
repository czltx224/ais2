var privilege=123;
Ext.onReady(function() {
	
	//客户信息Store
	var flowStore=new Ext.data.Store({
		storeId:"cpStore",
		method:'post',
		baseParams:{filter_EQS_custprop:"发货代理",privilege:61,limit:pageSize},
		proxy:new Ext.data.HttpProxy({url:sysPath+"/fax/oprChangeAction!ralaList.action"}),
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
        	{name:'id'},
        	{name:'isSystem'},
        	{name:'createName'},
        	{name:'createTime'},
        	{name:'remark'},
        	{name:'status'},
        	{name:'dno'}
        	])
	});
    //start
    var sm = new Ext.grid.CheckboxSelectionModel({});
    var rowNum = new Ext.grid.RowNumberer({
        header:'序号', width:35, sortable:true
    });
    
    var menuGrid = new Ext.grid.GridPanel({
        renderTo:'showView',
        height:Ext.lib.Dom.getViewHeight(), 
        width:Ext.lib.Dom.getViewWidth(),
        viewConfig : {
			columnsText : "显示的列",
			forceFit : true
		},
		autoScroll:true, autoExpandColumn:1,
        frame:true, 
        loadMask:true,
        sm:sm,
        cm:new Ext.grid.ColumnModel([rowNum,sm,
            {header:'流程名称',dataIndex:"remark"},
            //{header:'流程类型',dataIndex:"ts",hidden: true, hideable: false},
            {header:'当前流程',dataIndex:"cpId",hidden: true, hideable: false},
            {header:'流程创建人',dataIndex:"createName",width:80},
            {header:"流程创建时间",dataIndex:"createTime",width:80},
            {header:"流程状态",dataIndex:"status",width:80,renderer:function(v){
	    			if(v==0){
	    				return "删除";
	    			}else if(v==1){
	    				return "未审核";
	    			}else if(v==2){
	    				return "已审核";
	    			}
    			}
    		}
           
        ]),
        store:flowStore,
        tbar: [
             {
            	xtype : 'textfield',
    			id :'searchContent',
    			name : 'departName',
    			width : 100,
    			enableKeyEvents:true,
    			listeners : {
			 		keyup:function(textField, e){
		                     if(e.getKey() == 13){
		                     	searchArea();
		                  }
			 		}
	 			}
            },
            '-','&nbsp;&nbsp;',
            {
            	xtype : 'combo',
            	triggerAction : 'all',
    			model : 'local',
    			hiddenId : 'checkItems',
    			hiddenName : 'checkItems',
    			name : 'checkItemstext',
            	store: [
            			['', '查询全部'], 
    					['LIKES_departName', '部门名称'],
    					['LIKES_addr','部门地址'],
    					['LIKES_telephone','联系电话']
    				   ],
    			width:100,
    			listeners : {
    						'select' : function(combo, record, index) {
    							if (combo.getValue() == '') {
    								Ext.getCmp("searchContent").disable();
    								Ext.getCmp("searchContent").show();
    								Ext.getCmp("searchContent").setValue("");
    							} else {
    								Ext.getCmp("searchContent").enable();
    								Ext.getCmp("searchContent").show();
    								
    							}
    							Ext.getCmp("searchContent").focus(true, true);
    							
    						}
    					}
    		},
            {
            	text:'<B>搜索</B>',
            	id:'btn', 
            	iconCls: 'btnSearch',
            	hidden:false,
            	handler:function(){
					searchCusRequest();            	
            	}
            } ],
        bbar: new Ext.PagingToolbar({
            pageSize: pageSize,
            privilege:privilege, 
            store: flowStore, 
            displayInfo: true,
            displayMsg: '显示第 {0} 条到 {1} 条记录，共 {2} 条', emptyMsg: "没有记录信息显示"
        }),
        listeners :{rowdblclick : function( grid,rowIndex, eventObject ) {
            
	            var node=new Ext.tree.TreeNode({id:'keel_'+rowIndex,leaf :false,text : grid.getStore().getAt(rowIndex).get('remark')});
	           		 node.attributes={href1:'fax/oprChangeAction!dealChange.action'};
	            	 toAddTabPage(node,true);
	            	 
	            }
	            	
            }
    });
     function searchCusRequest() {
		flowStore.proxy = new Ext.data.HttpProxy({
					method : 'POST',
					url : sysPath
								+ "/fax/oprChangeAction!ralaList.action",
						params:{privilege:privilege,limit : pageSize}
				});
		flowStore.baseParams = {
			checkItems : Ext.get("checkItems").dom.value,
			privilege:privilege,
			itemsValue : Ext.get("searchContent").dom.value
		}
		flowStore.reload();
		
	}   
  	menuGrid.render();
    menuGrid.on('click', function() {
        var _record = menuGrid.getSelectionModel().getSelections();
        var updatebtn = Ext.getCmp('updatebtn');
        var deletebtn = Ext.getCmp('deletebtn');
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
    });
    tabPanl= new Ext.TabPanel();
    function toAddTabPage(node,flag) {//添加tabpanel标签栏
	        var getTabPage = null;
	        if (flag || node.getDepth() > 0) {  //如果不是根节点root
	            var tabItems = tabPanl.items;//获取已经生成的tabpanl
	            var val = null;
	            tabItems.each(function(item) {//对已经生成的tabpanel进行迭代
	                if (item.id == node.id) {//如果为已经生成的tabpanel
	                    val = node.id;
	                    getTabPage = item;
	                    return false;
	                }
	            });
	            if (val != null) {//激活已经以生成的tabpanel
	                tabPanl.setActiveTab(getTabPage);
	            } else {
	                var index = tabItems.length;
	                if (index > 18) {
	                    Ext.Msg.alert('提示', '标签数量过多,请关闭不必要的标签项');
	                } else {
	                    var tabPage = tabPanl.add({
	                        title:node.text,
	                        layout:'fit', 
	                        titleCollapse: true ,
	                        id:'keel_'+node.id,
	                        autoDestroy:true,
	                        html : '<iframe class="iframe" id="tabIframe_'+node.id+'" frameborder="0"  style="width:100%; height:100%;" src="' +sysPath+"/"+node.attributes.href1+ '"/>',
			                closable : true,
			                listeners : {
				                'beforedestroy' : function(o) {
				                    var _id = o.id.substring(o.id.indexOf('_') + 1);
				                    var el = document.getElementById("tabIframe_" +_id);
				                    if(el){
				                        el.src = 'javascript:false';
				                        try{
				                            var iframe = el.contentWindow;
				                            iframe.document.readyState="complete";
				                            iframe.document.write('');
				                            iframe.close();
				                            if(Ext.isIE){
			                                    CollectGarbage();
			                                }
				                        }catch(e){};
				                       
				                        el.parentNode.removeChild(el);
				                    }
				                    o.html=null;
	                  			  tabPanl.remove(o);
              				  }
			              },

	                        closable:true
	                    });
	                    tabPanl.setActiveTab(tabPage);
	                }
	            }
	        }
	        
	        
	    }
});
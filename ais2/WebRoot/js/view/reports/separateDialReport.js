//分拨货量报表js
var searchMapUrl='reports/inuploadGoods!getSeparateDialReport.action';
var loadingbrigadeUrl='sys/loadingbrigadeAction!list.action';//分拨组查询地址
var dictionaryUrl='sys/dictionaryAction!ralaList.action';//数据字典地址
var gridCustomerUrl="sys/customerAction!list.action";//客商查询地址
var authorityDepartUrl='sys/basRightDepartAction!findDepartName.action';//权限部门查询地址
var privilege=146;
var defaultWidth=80;
var colWidth=80;
var fields=['DISPATCH_ID','FNAME','LOADING_NAME','CPNAME','SUMCOL'];
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
	//统计名称
	countNameStore=new Ext.data.SimpleStore({
			 autoLoad:true, //此处设置为自动加载
  			  data:[['inStockNum','入库票数'],['inStockWeight','入库重量'],
  			  ['outStockNum','出库票数'],['outStockWeight','出库重量']],
   			 fields:["countId","countName"]
		});
	//代理公司
		cpNameStore= new Ext.data.Store({ 
			storeId:"cpNameStore",
			baseParams:{filter_EQS_custprop:'发货代理'},
			proxy:new Ext.data.HttpProxy({url:sysPath+"/"+gridCustomerUrl}),
			reader:new Ext.data.JsonReader({
	                  root:'result', totalProperty:'totalCount'
	        },[
        	{name:'cusName',mapping:'cusName'},
        	{name:'cusId',mapping:'id'}
        	])
		});
	//分拨组
	 dispatchGroupStore= new Ext.data.Store({ 
		storeId:"dispatchGroupStore",
		baseParams:{filter_EQL_type:1},
		proxy:new Ext.data.HttpProxy({url:sysPath+"/"+loadingbrigadeUrl}),
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
    	{name:'dispatchGroupId',mapping:'id'},
    	{name:'dispatchGroupName',mapping:'loadingName'}
    	])
	});
	 function removeOne(){
   		eparateDialGrid.getTopToolbar().remove(startone);
		eparateDialGrid.getTopToolbar().remove(endone);
   	}
   	function addOne(){
   		eparateDialGrid.getTopToolbar().insert(6,startone);
		eparateDialGrid.getTopToolbar().insert(8,endone);
   	}
	//统计纬度201
	countRangeStore	= new Ext.data.Store({ 
			autoLoad:true, 
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
		var startone = new Ext.form.DateField ({
    		id : 'startone',
    		format : 'Y-m-d',
    		allowBlank : false,
    		emptyText : "开始时间",
    		value:new Date().add(Date.DAY, -7),
    		width : defaultWidth+40
    	});
    	
    var endone = new Ext.form.DateField ({
    		id : 'endone',
    		format : 'Y-m-d',
    		allowBlank : false,
    		emptyText : "结束时间",
    		value:new Date(),
    		width : defaultWidth+40
    	});
	var tbar=[
			'时间选择:',{
			xtype : "combo",
			width : defaultWidth+20,
			triggerAction : 'all',
			forceSelection : true,
			model : 'local',
			hiddenId : 'checkItems',
			hiddenName : 'checkItems',
			store : [
					['t.createTime', '统计日期']],
			listeners: {   
				afterRender: function(combo) {   
		       　　combo.setValue('t.createTime');
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
				    		allowBlank : false,
				    		emptyText : "开始时间",
				    		value:new Date().add(Date.DAY, -7),
				    		width : defaultWidth+40
				    	});
				    	endone = new Ext.form.DateField ({
				    		id : 'endone',
				    		format : 'Y-m-d',
				    		allowBlank : false,
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
				    		allowBlank : false,
				    		value:new Date().getWeekOfYear()-1,
				    		minValue:1,
				    		maxValue:52,
				    		maxLength:2,
				    		width : defaultWidth
				    	});
				    	endone = new Ext.form.NumberField  ({
				    		id : 'endone',
				    		allowNegative :false,
				    		allowBlank : false,
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
				    		allowBlank : false,
				    		format : 'Y-m',
				    		emptyText : "开始时间",
				    		value:new Date().add(Date.MONTH, -1),
				    		width : defaultWidth+40
				    	});
				    	endone = new Ext.form.DateField ({
				    		id : 'endone',
				    		format : 'Y-m',
				    		allowBlank : false,
				    		emptyText : "结束时间",
				    		value:new Date(),
				    		width : defaultWidth+40
				    	});
				    	addOne();
					}else if(v=='年'){
						removeOne();
						startone = new Ext.form.NumberField ({
				    		id : 'startone',
				    		allowBlank : false,
				    		allowNegative :false,
				    		value:new Date().getFullYear()-1,
				    		minValue:1,
				    		width : defaultWidth
				    	});
				    	endone = new Ext.form.NumberField  ({
				    		id : 'endone',
				    		allowBlank : false,
				    		allowNegative :false,
				    		value:new Date().getFullYear(),
				    		minValue:1,
				    		width : defaultWidth
				    	});
				    	addOne();
					}
					eparateDialGrid.getTopToolbar().doLayout(true);
				}
			}
		},
		'开始:',startone,' 至 ',endone,'-',{text : '<b>统计</b>',id : 'btn',iconCls : 'btnSearch',
    					handler : searchSeparateDial
    	},'-',
	 	{text:'<b>导出</b>',iconCls:'sort_down',tooltip : '导出数据',
	 		handler:function(){
	 		   parent.exportExl(eparateDialGrid);
	 		}
	 	},'-','<font color=red>默认统计一个月之内的数据</font>'
	 	];	
	var twobar=new Ext.Toolbar([
			'部门名称:',{xtype : "combo",
				width : 100,
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
                     	searchSeparateDial();
                     }
	 			}
	 		}
	 	},'分拨组：', {
			xtype : "combo",
			width : 80,
			id : 'dispatchGroup',
			typeAhead : true,
			queryParam : 'filter_LIKES_typeName',
			pageSize : comboSize,
			minChars : 0,
			editable:true,
			forceSelection : true,
			selectOnFocus : true,
			resizable : true,
			triggerAction : 'all',
			store : dispatchGroupStore,
			minChars : 0,
			mode : "remote",// 从本地载值
			valueField : 'dispatchGroupId',// value值，与fields对应
			displayField : 'dispatchGroupName',// 显示值，与fields对应
			name : 'dispatchGroup',
			 enableKeyEvents:true,listeners : {
	 		    keyup:function(textField, e){
                     if(e.getKey() == 13){
                     	searchSeparateDial();
                     }
	 			}
	 		}
		},'-','统计字段：', {

			xtype : "combo",
			width : 80,
			id : 'searchCountName',
			typeAhead : true,
			editable:true,
			forceSelection : true,
			selectOnFocus : true,
			resizable : true,
			triggerAction : 'all',
			store : countNameStore,
			mode : "local",// 从本地载值
			valueField : 'countId',// value值，与fields对应
			displayField : 'countName',// 显示值，与fields对应
			name : 'dispatchGroup',
			 enableKeyEvents:true,listeners : {
	 		    keyup:function(textField, e){
                     if(e.getKey() == 13){
                     	searchSeparateDial();
                     }
	 			}
	 		}
		},'-','代理公司:',{xtype : "combo",
				width : 80,
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
                     	searchSeparateDial();
                     }
	 		}
	 	
	 	}}
	 	]);	
	 	
Ext.onReady(function() {
   	dateStore = new Ext.data.Store({
        storeId:"dateStore",
        proxy: new Ext.data.HttpProxy({url : sysPath+ "/"+searchMapUrl}),
        baseParams:{privilege:privilege,limit : pageSize},
         listeners:{
        	'load':function(store,e){
       			var cmItems = [sm,rowNum]; 
				var afields=['DISPATCH_ID','FNAME','LOADING_NAME','SUMCOL'];
				cmItems.push({header:"分拨组ID",dataIndex :'DISPATCH_ID',width:colWidth,sortable:true,hidden:true});
				cmItems.push({header:"分拨组",dataIndex :'LOADING_NAME',width:colWidth,sortable:true});
				cmItems.push({header:"统计名称",dataIndex :'FNAME',width:colWidth,sortable:true});
				cmItems.push({header:"合计",dataIndex :'SUMCOL',width:colWidth,sortable:true});
				
				reDoGrid(cmItems,afields,store,eparateDialGrid);
	        }
        },
        reader: new Ext.data.JsonReader({
            root: 'resultMap', totalProperty: 'totalCount'
        },fields)
    });
   
   var sm = new Ext.grid.CheckboxSelectionModel({});
	var rowNum = new Ext.grid.RowNumberer({
        header:'序号', width:40
    });
    eparateDialGrid = new Ext.grid.GridPanel({
        renderTo: Ext.getBody(),
        id:'eparateDialCenter',
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
           {header:'分拨组ID',dataIndex:"DISPATCH_ID",width:colWidth,sortable:true,hidden:true},
           {header:'分拨组',dataIndex:"LOADINGNAME",width:colWidth,sortable:true},
           {header:'统计名称',dataIndex:"FNAME",width:colWidth,sortable:true},
           {header:'合计',dataIndex:"SUMCOL",width:colWidth,sortable:true}
        ]),
        store:dateStore,
        listeners : {
			render : function() {
				twobar.render(this.tbar);
			}
		},
        tbar: tbar,
        bbar: new Ext.PagingToolbar({
            pageSize: pageSize, 
            store: dateStore, 
            displayInfo: true,
            displayMsg: '显示第 {0} 条到 {1} 条记录，共 {2} 条', 
            emptyMsg: "没有记录信息显示"
        })
    });
    
  });
    
    
	
 function searchSeparateDial() {
 	dateStore.proxy = new Ext.data.HttpProxy({
				method : 'POST',
				url : sysPath + "/" + searchMapUrl
				
			});
	if(!panduan()){
		return;
	}
	var dispatchGroup=Ext.getCmp('dispatchGroup').getValue();
	var searchCpName = Ext.getCmp('searchCpName').getRawValue();
	var searchCountName = Ext.getCmp('searchCountName').getRawValue();
	var authorityDepartId=Ext.getCmp('authorityDepartId').getValue();
	if(null!=authorityDepartId && authorityDepartId>0){
		pubauthorityDepart=authorityDepartId;
	}else{
		pubauthorityDepart=bussDepart;
	}
 	 Ext.apply(dateStore.baseParams, {
 	 	filter_departId:pubauthorityDepart,
 		filter_countRange:searchCountRange,
 	 	filter_startCount:startCount,
 	 	filter_endCount:endCount,
 	 	filter_countCheckItems:countCheckItems, 
 		filter_dispatchId:dispatchGroup,
 		filter_fname:searchCountName,
 		filter_cpName:searchCpName,
 		limit:pageSize
 	});
	dataReload();
}
 //弹出异常信心
 function alertMsg(msg){
 	Ext.Msg.alert(alertTitle,"<font color=red><b>"+msg+"</b></font>");
 }

 function dataReload(){
			dateStore.reload({
			params : {
				start : 0,
				limit:pageSize
				}
			})
		}
//更改记录查询JS
var popupWin;
var privilege=233;
var changeMsgStore,changeGrid;
var fields=[
	{name:'changeNo'},
	{name:'dno'},
	{name:'isSystem'},
	{name:'remark'},
	{name:'status'},
	{name:'changeField'},
	{name:'changeFieldZh'},
	{name:'changePre'},
	{name:'changePost'},
	{name:'departId'},
	{name:'departName'},
	{name:'createName'},
	{name:'createTime'},
	{name:'updateName'},
	{name:'updateTime'},
	{name:'changeDetailId'}
];
Ext.onReady(function() {

	changeMsgStore=new Ext.data.Store({
		storeId:"changeMsgStore",
		proxy:new Ext.data.HttpProxy({url:sysPath+"/fax/oprChangeAction!ralaList.action"}),
		baseParams:{limit:pageSize,privilege:privilege,filter_EQL_faxStatus:1},
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },fields)
	});
    //start
    var sm = new Ext.grid.CheckboxSelectionModel({});
    var rowNum = new Ext.grid.RowNumberer({
        header:'序号', width:35, sortable:true
    });
    var tb = new Ext.Toolbar({
    	width : Ext.lib.Dom.getViewWidth(),
    	items:[{text:'<b>导出</b>',iconCls:'sort_down',handler:function() {
                	parent.exportExl(changeGrid);
        		} },'-',{
            	text : '<b>打印更改通知单</b>',iconCls : 'printBtn',handler :function(){
            		var _records = changeGrid.getSelectionModel().getSelections();
            		if (_records.length < 1) {
						Ext.Msg.alert(alertTitle, "请选择您要打印的更改单！");
						return false;
					} 
					var changeNos='';
					var changeNo=_records[0].data.changeNo;
					var vdno=_records[0].data.dno;
					for(var i=0;i<_records.length;i++){
						if(vdno!=_records[i].data.dno){
							Ext.Msg.alert(alertTitle, "请选择同一个配送单号的更改记录！");
							return false;
						}
						if(changeNo!=_records[0].data.changeNo){
							Ext.Msg.alert(alertTitle, "请选择同一个更改单号的更改记录！");
							return false;
						}
						
						changeNos+=_records[i].data.changeDetailId+',';
					}
					parent.print('14',{print_changeNos:changeNos,print_dno:vdno,print_changeNo:changeNo});
            	}
            },'-','创建时间:',
			 {
				xtype : 'datefield',
				format:'Y-m-d',
				value:new Date().add(Date.DAY, -7),
				id : 'datefieldstart',
				name : 'startTime',
				width : 100
			} ,'-', '&nbsp;', {
				xtype : 'datefield',
				id:'datefieldend',
				format:'Y-m-d',
				value:new Date(),
				name : 'endTime',
				width : 100
			},'-',
            {
            	xtype:'textfield',
            	name:'searchContent',
            	id:'searchContent',
            	width:100
            },
            '-',{
            	xtype : "combo",
				width : 100,
				triggerAction : 'all',
				model : 'local',
				id : 'checkItems',
				name : 'checkItems',
				name : 'checkItemstext',
				store : [
						['', '查询全部'],
						['EQL_dno', '配送单号'],
						['LIKES_changeFieldZh','更改项'],
						
						['LIKES_createName','创建人']
						],
				emptyText : '选择类型',
				forceSelection : true,
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
					}
				}
            },{
            	text : '<b>搜索</b>',id : 'btn',iconCls : 'btnSearch',handler : searchChange
            }
        ]
    });
    changeGrid = new Ext.grid.GridPanel({
    	renderTo:'showView',
    	region : 'north',
        width:Ext.lib.Dom.getViewWidth(),
        height:Ext.lib.Dom.getViewHeight(),	
        bodyStyle : 'padding:0px',
        viewConfig : {
			columnsText : "显示的列",
			forceFit : true
		},
		tbar:tb,
		autoScroll:true, 
		autoExpandColumn:1,
        frame:false, 
        loadMask:true,
        sm:sm,
        cm:new Ext.grid.ColumnModel([rowNum,sm,
            {header:'更改单号',dataIndex:'changeNo',width:50},
            {header:'配送单号',dataIndex:'dno',width:50},
            {header:'更改明细单号',dataIndex:'changeDetailId',width:50},
            {header:'是否系统默认',dataIndex:'isSystem',width:50,renderer:function(v){
            	if(v==1){
            		return '是';
            	}else{
            		return '否';
            	}
            }},
            {header:'更改英文字段',dataIndex:'changeField',width:50,hidden: true},
            {header:'更改项',dataIndex:'changeFieldZh',width:50},
            {header:'更改前的值',dataIndex:'changePre',width:50},
            {header:'更改后的值',dataIndex:'changePost',width:50},
            {header:'部门编号',dataIndex:'departId',hidden: true, hideable: false},
            {header:'更改部门',dataIndex:'departName',width:50},
            {header:'状态',dataIndex:'status',width:50,renderer:function(v){
            	if(v==0){
            		return '已删除';
            	}else if(v==1){
            		return '未审核';
            	}else if(v==2){
            		return '已审核';
            	}else if(v==3){
            		return '审核不通过';
            	}else if(v==4){
            		return '已知会';
            	}
            }},
            {header:'创建人',dataIndex:'createName',width:50},
            {header:'创建时间',dataIndex:'createTime',width:50},
            {header:'备注',dataIndex:'remark',width:100}
        ]),
        store:changeMsgStore,
        bbar: new Ext.PagingToolbar({
            pageSize: pageSize,
            privilege:privilege, 
            store: changeMsgStore, 
            displayInfo: true,
            displayMsg: '显示第 {0} 条到 {1} 条记录，共 {2} 条', emptyMsg: "没有记录信息显示"
        })
    });
	changeGrid.render();
	changeGrid.doLayout();
   
});
function searchChange() {
	var start=Ext.get("datefieldstart").dom.value;
    var end=Ext.get("datefieldend").dom.value;
 	changeMsgStore.baseParams = {
		limit:pageSize,
		privilege : privilege,
		filter_EQL_faxStatus:1,
		filter_GED_createTime:start,
		filter_LED_createTime:end,
		checkItems : Ext.getCmp("checkItems").getValue(),
		itemsValue : Ext.getCmp("searchContent").getValue()
	}
	changeMsgStore.load();
}
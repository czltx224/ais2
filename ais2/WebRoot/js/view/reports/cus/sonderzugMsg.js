var privilege=68;
var mainSearchUrl='cus/sonderzugMsgAction!findSonderMsg.action';
var carTitle;
Ext.onReady(function() {
	var fields=[
		{name:'dno',mapping:'D_NO'},
		{name:'cpName',mapping:'CP_NAME'},
		{name:'piece',mapping:'PIECE'},
		{name:'cusWeight',mapping:'CUS_WEIGHT'},
		{name:'customerService',mapping:'CUSTOMER_SERVICE'},
		{name:'cusDepartName',mapping:'CUS_DEPART_NAME'},
		{name:'totalSon',mapping:'TOTAL_SON'},
		{name:'createTime',mapping:'CREATE_TIME'}
	];
	var mainStore=new Ext.data.Store({
		storeId:"mainStore",
		baseParams:{limit:pageSize,privilege:privilege},
		proxy:new Ext.data.HttpProxy({url:sysPath+'/'+mainSearchUrl}),
		reader:new Ext.data.JsonReader({
                    root:'resultMap',
                    totalProperty:'totalCount'
        },fields)
	});
	
    //start
    var sm = new Ext.grid.CheckboxSelectionModel({});
    var rowNum = new Ext.grid.RowNumberer({
        header:'序号', width:35, sortable:true
    });
    var normspe=new Ext.form.Radio({
    		id:'normradio',
	        boxLabel:'正常专车',
	        inputValue:'1',
	        checked:true,
	        listeners:{
	            'check':function(){
	                if(normspe.getValue()){
	                    exceptionspe.setValue(false);
	                    normspe.setValue(true);
	                }
	            }
           }
	    });
    var exceptionspe=new Ext.form.Radio({
        boxLabel:'异常专车',
        id:'exceptionradio',
        inputValue:'0',
        listeners:{
            'check':function(){
                if(exceptionspe.getValue()){
                    normspe.setValue(false);
                    exceptionspe.setValue(true);
                }
            }
        }
    });
    var menuGrid = new Ext.grid.GridPanel({
        renderTo:'showView',
        height:Ext.lib.Dom.getViewHeight(), 
        width:Ext.lib.Dom.getViewWidth(),
        viewConfig : {
			columnsText : "显示的列",
			forceFit : true
		},
		autoScroll:true, 
		autoExpandColumn:1,
        frame:true, 
        loadMask:true,
        sm:sm,
        cm:new Ext.grid.ColumnModel([rowNum,sm,
            {header:'配送单号',dataIndex:"dno",width:50},
            {header:"代理公司",dataIndex:"cpName",width:80},
            {header:"件数",dataIndex:"piece",width:50,sortable:true},
            {header:"重量",dataIndex:"cusWeight",width:50,sortable:true},
            {header:"收入合计",dataIndex:"totalSon",width:50,sortable:true
            	/*,renderer:function(value, metaData, record, rowIndex, colIndex, store){
            	return Number(record.data.sonderzugPrice)+Number(record.data.cpSonderzugPrice);
            	}*/
            
            },
            {header:"录单日期",dataIndex:"createTime",width:50
            },
            {header:"客服员",dataIndex:"customerService",width:50},
            {header:"客服部门",dataIndex:"cusDepartName",width:50}
           
        ]),
        store:mainStore,
        tbar: [{text:'<b>导出</b>',iconCls:'sort_down',handler:function() {
                	parent.exportExl(menuGrid);
        	} },'-',
            '录单日期:',
            {
				xtype : 'datefield',
				format : 'Y-m-d',
				width : 80,
				value : new Date().add(Date.DAY, -30),
				blankText : "[录单日期从]不能为空！",
				name : 'startDate',
				id : 'startDate'
			}, '至', {
				xtype : 'datefield',
				format : 'Y-m-d',
				width : 80,
				value : new Date(),
				blankText : "[录单日期至]不能为空！",
				name : 'endDate',
				id : 'endDate'
			},'-',
				normspe,exceptionspe,
            '-',
            {
            	text:'<B>搜索</B>',
            	id:'btn', 
            	iconCls: 'btnSearch',
            	hidden:false,
            	handler : searchCusRequest
            }
        ],
        bbar: new Ext.PagingToolbar({
            pageSize: pageSize,
            privilege:privilege, 
            store: mainStore, 
            displayInfo: true,
            displayMsg: '显示第 {0} 条到 {1} 条记录，共 {2} 条', emptyMsg: "没有记录信息显示"
        })
    });
     function searchCusRequest() {
     	var isException=0;
     	if(Ext.getCmp('normradio').getValue()){
     		isException=0;
     	}
     	if(Ext.getCmp('exceptionradio').getValue()){
     		isException=1;
     	}
     	var startDate=Ext.get('startDate').dom.value;
     	var endDate=Ext.get('endDate').dom.value;
		mainStore.baseParams = {
			endTime:endDate,
			startTime:startDate,
			limit:pageSize,
			isException:isException
		}
		menuStoreReload();
		
	}
     //查询
     
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
	function menuStoreReload(){
		mainStore.reload({
			params:{
				start : 0,
				privilege:privilege,
				limit : pageSize
			}
		});
	}
    //end
});
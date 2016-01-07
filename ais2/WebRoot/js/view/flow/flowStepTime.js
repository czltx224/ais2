var popupWin;
var privilege=244;
var searchUrl="flow/workFlowstepAction!getStepTime.action";//查询地址
var stepTimeStore,pipeinfoStore;
var fields=[
            {name:'pipeName',mapping:'PIPE_NAME'},
            {name:'nodeName',mapping:'NODE_NAME'},
            {name:'avgTime',mapping:'AVG_TIME'},
            {name: 'updateTime',mapping:'NODE_ID'}
];
Ext.onReady(function() {
    stepTimeStore = new Ext.data.Store({
        storeId:"stepTimeStore",
        proxy: new Ext.data.HttpProxy({url:sysPath+"/"+searchUrl}),
        reader: new Ext.data.JsonReader({
          //  root: 'resultMap', totalProperty: 'totalCount'
        }, fields)
    });
	pipeinfoStore = new Ext.data.Store({
        storeId:"pipeinfoStore",
        proxy: new Ext.data.HttpProxy({url:sysPath+"/flow/flowPipeinfoAction!list.action"}),
        reader: new Ext.data.JsonReader({
            root: 'result', totalProperty: 'totalCount'
        }, [
			{name:'id'},
            {name: 'objName'}
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
    		scrollOffset: 0,
			autoScroll:true
		},
		stripeRows : true,
        frame:true, 
        loadMask:true,
        sm:sm,
        cm:new Ext.grid.ColumnModel([rowNum,sm,
            {header:'流程名称',dataIndex:"pipeName",width:120},
            {header:'节点名称',dataIndex:"nodeName",width:120},
            {header:'平均耗时',dataIndex:"avgTime"}
           
        ]),
        store:stepTimeStore,
        tbar: ['流程:',{
        		xtype : 'combo',
				forceSelection : true,
				resizable : true,
				minChars : 0,
				pageSize:pageSize,
				queryParam : 'filter_LIKES_objName',
				triggerAction : 'all',
				width:120,
				store : pipeinfoStore,
				mode : "remote",// 从服务器端加载值
				valueField : 'id',// value值，与fields对应
				displayField : 'objName',// 显示值，与fields对应
			    hiddenName : 'pipeId',
			    id:'combopipe'
        	},
        	'创建日期:', {
				xtype : 'datefield',
				format : 'Y-m-d',
				width : 90,
				value : new Date().add(Date.DAY, -30),
				hiddenName : 'stateDate',
				id : 'startDate'
			}, '至', {
				xtype : 'datefield',
				format : 'Y-m-d',
				width : 90,
				value : new Date(),
				id : 'endDate',
				hiddenName : 'endDate'
			},'-',{
				xtype:'combo',
				hiddenName:'recordSize',
				id:'pageSizecombo',
				model : 'local',
				editable:false,
				forceSelection : true,
				triggerAction : 'all',
				value:'30',
    			width : 80,
				store:[
					['10','10条'],
					['30','30条'],
					['50','50条'],
					['100','100条']
				]
			},'-','统计类型',{
				xtype:'combo',
				hiddenName:'countType',
				id:'countTypecombo',
				model : 'local',
				editable:false,
				forceSelection : true,
				triggerAction : 'all',
				value:'node',
    			width : 80,
				store:[
					['node','节点'],
					['applyman','审批人']
				]
			},
            {
            	text:'<B>搜索</B>',
            	id:'btn', 
            	iconCls: 'btnSearch',
            	hidden:false,
            	handler : searchStepTime
            },{
            	text:'<B>查看图表</B>',
            	id:'showcharbtn', 
            	hidden:false,
            	iconCls: 'showChart',
            	handler : function(){
            		if(stepTimeStore.getCount()<2){
            			Ext.Msg.alert(alertTitle,'数据少于两条，无法统计！');
            		}else{
            			showChart();
            		}
            	}
            }
        ]
    });
    menuGrid.render();
	function searchStepTime(){
		var recordSize=Ext.getCmp('pageSizecombo').getValue();
		var pipeId=Ext.getCmp('combopipe').getValue();
		var startDate=Ext.get('startDate').dom.value;
		var endDate=Ext.get('endDate').dom.value;
		var countType=Ext.getCmp('countTypecombo').getValue();
		var cmItems = []; 
		var afields=[];
		afields.push({name:'pipeName',mapping:'PIPE_NAME'});
		afields.push({name:'avgTime',mapping:'AVG_TIME'});
		afields.push({name:'avgTimeStr',mapping:'AVG_TIME_STR'});
		afields.push({name:'auditCount',mapping:'AUDIT_COUNT',sortabel:true});
		cmItems.push(rowNum);
		cmItems.push({header:'流程名称',dataIndex:'pipeName'});
		if(countType =='node'){
			afields.push({name:'nodeName',mapping:'NODE_NAME'});
			afields.push({name:'nodeId',mapping:'NODE_ID'});
			
			cmItems.push({header:'节点名称',dataIndex:"nodeName",width:120});
		}else{
			afields.push({name:'updateName',mapping:'UPDATE_NAME'});
			cmItems.push({header:'操作人',dataIndex:"updateName"});
		}
		cmItems.push({header:'审批次数',dataIndex:'auditCount'});
		cmItems.push({header:'平均耗时',dataIndex:'avgTimeStr',width:120});
        stepTimeStore = new Ext.data.Store({
	        storeId:"stepTimeStore",
	        proxy: new Ext.data.HttpProxy({url:sysPath+"/"+searchUrl}),
	        reader: new Ext.data.JsonReader({
	        }, afields)
	    });
	    stepTimeStore.baseParams={};
	    stepTimeStore.baseParams = {
			startDate:startDate,
			endDate:endDate,
			pipeId:pipeId,
			countType:countType,
			recordSize:recordSize
		}
        menuGrid.reconfigure(stepTimeStore,new Ext.grid.ColumnModel(cmItems));
        stepTimeStore.load();
        stepTimeStore.on('load',function(){
        	
        });
        menuGrid.doLayout();
	};
});
function menuStoreReload(){
	stepTimeStore.load();
}
function showChart(){
	var countType=Ext.get('countTypecombo').dom.value;
	var strXml='';
	var strCate="<categories>";
	var strDataSet='';
	var sumSet='';
	var lineSet='';
	var numVal=0;
	for(var i=0;i<stepTimeStore.getCount();i++){
		var value=Number(stepTimeStore.getAt(i).get("avgTime"));
		sumSet+="<set value='"+value+"'/>";
		numVal+=value;
		lineSet+="<set value='"+numVal+"'/>";
		if(countType=='节点'){
			strCate+="<category label='"+stepTimeStore.getAt(i).get("nodeName")+"'/>";
		}else{
			strCate+="<category label='"+stepTimeStore.getAt(i).get("updateName")+"'/>";
		}
		
	}
	strCate+='</categories>';
	strDataSet+="<dataset seriesName='"+countType+"'>"+sumSet+"</dataset>";
	strDataSet+="<dataset seriesname='累计值' parentYAxis='S'>"+lineSet+"</dataset>";
	strXml+="<styles><definition><style type='font' color='666666' name='CaptionFont' size='15' /><style type='font' name='SubCaptionFont' bold='0' /></definition><application><apply toObject='caption' styles='CaptionFont' /><apply toObject='SubCaption' styles='SubCaptionFont' /></application></styles>";
	strXml="<chart caption='流程"+countType+"时效统计' sNumberSuffix='h' sYAxisName='累加值'  palette='1' animation='1' formatNumberScale='0' numberSuffix='h' showValues='0' seriesNameInToolTip='0'>"+strCate+strDataSet+strXml+"</chart>";
	
	var fusionPanel=new Ext.ux.Chart.Fusion.Panel({
			collapsible:false,
			chartCfg:{
				id:'chart1',
				params:{
					flashVars:{
						debugMode:0,
						lang:'EN'
					}
				}
			},
			autoScroll:true,
			id:'chartpanel',
			chartURL:'chars/fcf/MSColumn3DLineDY.swf?ChartNoDataText=对不起，图表中无数据显示。',
			dataXML:strXml,
			width:Ext.lib.Dom.getViewWidth(),
			height:300
	});
	
	var win = new Ext.Window({
		title : '图表查看',
		width : Ext.lib.Dom.getViewWidth()-20,
		height: 350,
		closeAction : 'hide',
		plain : true,
		modal : true,
		items : fusionPanel,
		buttonAlign : "center",
		buttons : [ {
			text : "关闭",
			handler : function() {
				win.close();
			}
		}]
	});
	win.on('hide', function() {
				fusionPanel.destroy();
			});
	win.show();
}
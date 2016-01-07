var cusId;
var cusRecordId;
var cusName;
var cusType;
var cusDevelop;
var viewtype='mainview';
    var sm = new Ext.grid.CheckboxSelectionModel({});
	var rowNum = new Ext.grid.RowNumberer({
        header:'序号', width:20
    });
    CargoTrend = Ext.extend(Ext.Panel, {
    height: 300,
    layout: {
        type: 'fit'
    },
    title: '分析统计报表',

    initComponent: function() {
        this.items = [
            {
                xtype: 'tabpanel',
                activeTab: 0,
                height:300,
                items: [
                    {
                    	xtype: 'panel',
                        title: '货量分析',
                        frame:true,
                        html:'<iframe src="'+sysPath+'/cus/cusGoodsAnalyAction!input.action" height=100% width=100%/>'
                    },
                    {
                        xtype: 'panel',
                        title: '产品结构分析',
                        html:'<iframe src="'+sysPath+'/cus/cusProTypeAction!input.action" height=100% width=100%/>'
                    },
                    {
                        xtype: 'panel',
                        title: '盈利分析',
                        html:'<iframe src="'+sysPath+'/cus/cusProfitAction!input.action" height=100% width=100%/>'
                    }
                ]
            }
        ];
        MyPanelUi.superclass.initComponent.call(this);
    }
});

/*   ---------------------------专车----------------------------------------------- */
 //报价管理   
 MyPanelUi = Ext.extend(Ext.Panel, {
    height: 371,
    layout: {
        type: 'fit'
    },
    title: '报价管理',
    initComponent: function() {
        this.items = [
            {
                xtype: 'tabpanel',
                activeItem: 0,
                activeTab: 3,
                items: [{
                	xtype: 'panel',
                    layout: {
                        type: 'fit'
                    },
                    title: '价格查询',
                    frame : true,
                    html:'<iframe src="'+sysPath+'/cus/cusQuoteAction!input.action" height=100% width=100%/>'
                },
                   {
                        xtype: 'panel',
                        layout: {
                            type: 'fit'
                        },
                        title: '公司公布价',
                        frame : true,
                        html:'<iframe src="'+sysPath+'/cus/cusPublicPriceAction!input.action" height=100% width=100%/>'
                    },{
                        xtype: 'panel',
                        layout: {
                            type: 'fit'
                        },
                        title: '代理协议价',
                        frame : true,
                        html:'<iframe src="'+sysPath+'/fi/cqCorporateRateAction!input.action" height=100% width=100%/>'
                    },
                    {
                        xtype: 'panel',
                        layout: {
                            type: 'fit'
                        },
                        title: '专车标准协议价',
                        frame:true,
                       	html:'<iframe src="'+sysPath+'/cus/cusPublicPriceAction!spePrice.action" height=100% width=100%/>'
                    },
                    {
                        xtype: 'panel',
                        layout: {
                            type: 'fit'
                        },
                        title: '专车协议价',
                        frame:true,
                        html:'<iframe src="'+sysPath+'/fi/basSpecialTrainRateAction!input.action" height=100% width=100%/>'
                    }
                ]
            }
        ];
        MyPanelUi.superclass.initComponent.call(this);
    }
});
 
function conConvertCus(name,tel){
	window.frames["cusrecordIframe"].parentSetMsg(name,tel); 
}

MyViewportUi = Ext.extend(Ext.Panel, {
	renderTo:Ext.getBody(),
    initComponent: function() {
        this.items = [
            {
                xtype: 'panel',
                autoScroll: true,
                height: Ext.lib.Dom.getViewHeight(),
                title: '',
                items: [
                    {
                        xtype: 'tabpanel',
                        height: 470,
                        width: Ext.lib.Dom.getViewWidth()-20,
                        activeItem: 0,
                        activeTab: 2,
                        items: [
                            {
                                xtype: 'panel',
                                title: '客户资料管理',
                                frame:true,
                                html:'<iframe id="cusrecordIframe" src="'+sysPath+'/cus/cusRecordAction!input.action" height=100% width=100%/>'
                            },
                            {
                                xtype: 'panel',
                                title: '应收应付往来',
                                frame : true,
                                html:'<iframe src="'+sysPath+'/cus/cusPaymentAction!input.action" height=100% width=100%/>'
                                
                            },
                            {
                                xtype: 'panel',
                                title: '联系人信息',
                                frame : true,
                                html:'<iframe src="'+sysPath+'/cus/cusLinkManAction!input.action" height=100% width=100%/>'
                            },
                            {
                                xtype: 'panel',
                                title: '客户需求',
                                frame : true,
                                html:'<iframe src="'+sysPath+'/cus/cusDemandAction!input.action" height=100% width=100%/>'
                            },{
                            	xtype: 'panel',
                                title: '竞争对手管理',
                                frame : true,
                                html:'<iframe src="'+sysPath+'/cus/cusCurrivalAction!input.action" height=100% width=100%/>'
                            }
                        ]
                    },
                    {
                        xtype: 'panel',
                        height: 400,
                        id:'coninfoPanel',
                        collapsible:true,
                        title: '收货人信息管理',
                      	frame:true,
                      	html:'<iframe src="'+sysPath+'/cus/cusInfoAction!input.action" height=100% width=100%/>'
                        
                    },
                    {
                        xtype: 'panel',
                        title: '活动管理',
                        id:'developPanel',
                        height: 400,
              			frame : true,
                        collapsible:true,
                        html:'<iframe src="'+sysPath+'/cus/cusDevelopAction!input.action" height=100% width=100%/>'
                    },
                    new MyPanelUi({
                    	collapseFirst:true ,
        				collapsible:true
                    }),
                    new CargoTrend({
                    	collapseFirst:true ,
        				collapsible:true
                    }),
                    {
                        xtype: 'panel',
                        height: 600,
                        title: '品质管理',
                         layout: {
				                            type: 'fit'
				                        },
                        collapsible:true,
                        items: [ {
		                        xtype: 'tabpanel',
		                        height: 600,
		                        activeItem: 0,
		                        activeTab: 0,
		                        
		                        items: [
		                        		{
		                        		xtype: 'panel',
		                                 layout: {
				                            type: 'fit'
				                        },
		                                title:'异常管理',
		                                html:'<iframe src="'+sysPath+'/cus/cusExceptionAction!input.action" height=100% width=100%/>'
		                                
			                        	
	                            },{
	                                title:'品质数据',
	                                 html:'<iframe src="'+sysPath+'/reports/oprEnterPortKpiAction!toKpiReport.action" height=100% width=100%/>'
	                                }
		                        ]
                        
                            
                   		 }]
                    },
                    {
                        xtype: 'panel',
                        height: 300,
                        collapsible:true,
                        id:'complaintPanel',
                        frame:true,
                        title: '投诉与建议',
                        html:'<iframe id="complaintiframe" src="'+sysPath+'/cus/cusComplaintAction!input.action" height=100% width=100%/>'
                    },
                    {
                        xtype: 'panel',
                        height: 300,
                        title: '销售机会管理',
                        collapsible:true,
                        html:'<iframe id="saleChanceiframe" src="'+sysPath+'/cus/cusSaleChanceAction!input.action" height=100% width=100%/>'
                    }]
            }
        ];
        MyViewportUi.superclass.initComponent.call(this);
    }
});

Ext.onReady(function(){
			cusRecordId=cRId;
			cusId=cusI;
			cusName=cusN;
			cusType=cusT;
			cusDevelop=cusDev;
			new MyViewportUi();
		});

Ext.onReady(function() { 
	var tabPanel=new Ext.TabPanel({
		renderTo:'showView',
    	id : 'basstgrid',
    	activeItem: 0,
        activeTab: 3,
        height : Ext.lib.Dom.getViewHeight(),
    	width : Ext.lib.Dom.getViewWidth()-1,
    	frame : false,
    	loadMask : true,
    	items:[{
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
	});
});
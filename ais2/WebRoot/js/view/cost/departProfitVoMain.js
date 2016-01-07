
Ext.onReady(function() { 
	var tabPanel=new Ext.TabPanel({
		renderTo:Ext.getBody(),
    	id : 'basstgrid',
    	activeItem: 0,
        activeTab: 1,
        height : Ext.lib.Dom.getViewHeight(),
    	width : Ext.lib.Dom.getViewWidth()-1,
    	frame : false,
    	loadMask : true,
    	items:[{
                	xtype: 'panel',
                    layout: {
                        type: 'fit'
                    },
                    title: '单票统计分析',
                    frame : true,
                    html:'<iframe src="'+sysPath+'/fi/departProfitVoAction!dnototal.action" height=100% width=100%/>'
                },
    			{
                        xtype: 'panel',
                        layout: {
                            type: 'fit'
                        },
                        title: '汇总统计分析',
                        frame : true,
                        html:'<iframe src="'+sysPath+'/fi/departProfitVoAction!cptotal.action" height=100% width=100%/>'
                    }
    	]
	});
});
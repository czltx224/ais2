
Ext.onReady(function() {
    var tb=new Ext.Toolbar({
	width:Ext.lib.Dom.getViewWidth()-10,
	id:'faxToolbar',
	renderTo:'pagemenubar',
	items:['&nbsp;&nbsp;',{
			text : '<B>保存</B>',
			id : 'addFlowMsgBtn',
			tooltip : '保存流程图信息',
			iconCls : 'save',
			handler:function(){
				onSave();
			}
		}]
	});
});
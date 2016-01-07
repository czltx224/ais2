//短信发送js
var privilege=257;//权限参数
var sendMessageUrl="message/textMessagesAction!sendMsg.action";//短信群发地址

Ext.onReady(function(){
    var mainForm = new Ext.form.FormPanel({
    	id:'mainForm',
		frame : true,
		renderTo:Ext.getBody(),
		bodyStyle : 'padding:0px 0px 0px',
	    height : Ext.lib.Dom.getViewHeight(),
		width : Ext.lib.Dom.getViewWidth(),
		labelAlign  : "right",
		tbar:[
			{
				text : '<b>发送</b>',
				iconCls : 'save',
				tooltip : '发送短信',
				handler : function() {
					sendMessage();
				}
			},{
				text : '<b>重置</b>',
				iconCls : 'refresh',
				tooltip : '发送短信',
				handler : function() {
					mainForm.getForm().reset();
				}
			}	
		],
       items : [{
		layout : 'column',
		items : [{
					columnWidth : .95,
					layout : 'form',
					items : [
						{
							xtype : 'textarea',
							labelAlign : 'right',
							id:'consigneeTels',
							fieldLabel : '请输入手机号码,中间用逗号隔开<font style="color:red;">*</font>',
							name : 'consigneeTels',
							allowBlank : false,
							anchor : '95%',
							enableKeyEvents:true,
							maxLength:200,
							height:100
						},
						{
							xtype : 'textarea',
							labelAlign : 'right',
							id:'textMessageContent',
							fieldLabel : '短信内容：<font style="color:red;">*</font>',
							name : 'textMessageContent',
							allowBlank : false,
							anchor : '95%',
							maxLength:60,
							enableKeyEvents:true,
							height:200
						},{
							xtype : 'textfield',
							labelAlign : 'right',
							id:'receiver',
							fieldLabel : '接收对象：<font style="color:red;">*</font>',
							name : 'receiver',
							allowBlank : false,
							anchor : '95%',
							maxLength:20,
							enableKeyEvents:true
						}
						
					]
				}]
					
		}]
 	});
 });  
 
 function sendMessage(){
 
 	var form = Ext.getCmp('mainForm');
 	if(!form.getForm().isValid()){
 	 	Ext.Msg.alert(alertTitle, "您输入的信息不合法！");
 		return;
 	}
 	form.getForm().submit({
 		url : sysPath+'/'+sendMessageUrl,
		waitMsg : '正在保存数据...',
		success : function(form, action) {
			if(action.result.success){
				Ext.Msg.alert(alertTitle,"发送成功！");
			}else{
				Ext.Msg.alert(alertTitle,action.result.msg);
			}			
		},
		failure : function(form, action) {
			if(action.result.msg){
		    	Ext.Msg.alert(alertTitle, action.result.msg);
		    }else{
		    	Ext.Msg.alert(alertTitle, "发送失败！");
		    }
		    
		}
	});
 }
	
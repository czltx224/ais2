Ext.onReady(function(){
	if( parent.myMask){
		parent.myMask.hide();
	}

	Ext.Ajax.on('requestcomplete',function(conn,response,options) {    
	    if(response.statusText==401){   
	        Ext.Msg.alert('��ʾ', '�Ự��ʱ�������µ�¼!', function(){   
	            window.location = '/login.jsp';    
	        });   
	    }   
}); 
	
});
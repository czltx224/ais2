Ext.onReady(function(){
	if( parent.myMask){
		parent.myMask.hide();
	}

	Ext.Ajax.on('requestcomplete',function(conn,response,options) {    
	    if(response.statusText==401){   
	        Ext.Msg.alert('提示', '会话超时，请重新登录!', function(){   
	            window.location = '/login.jsp';    
	        });   
	    }   
}); 
	
});
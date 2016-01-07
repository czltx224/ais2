Ext.define("xbwl.simpleView",{
	extend: 'Ext.container.Viewport', 
	constructor : function(a){
		
		this.data=Ext.create('Ext.data.Store', {
		    storeId:'simpsonsStore',
		    fields:a.fields,
		    autoLoad: false,
		    folderSort:true,
		    pageSize: a.itemsPerPage, // items per page
		    proxy: {
		        type: 'ajax',
		        url: sysPath+"/"+a.gridSearchUrl,  // url that will load data with respect to start and limit params
		        params : a.params,
		        reader: {
		            type: 'json',
		            root: 'result',
		            totalProperty: 'totalCount'
		        }
		    }
		});
	   
		this.data.load();
		
		this.eastPanel = Ext.create('Ext.form.Panel',{
			title:'新增和修改',
	        region:'east',
	        id:'east',
	        frame: true,
	        split:true,
	        collapsible: true,
	        collapsed:true,
	        border:false,
	        width:300,
	        defaultType: 'textfield',
	        items:a.formItems,
	        buttonAlign : "center",
	        tbar: [ {
			        text: '提交',
			        formBind: true, //only enabled once the form is valid
			        disabled: true,
			        handler: function() {
			            var form = this.up('form').getForm();
			            if (form.isValid()) {
			                form.submit({
			                    success: function(form, action) {
			                       Ext.Msg.alert('Success', action.result.msg);
			                    },
			                    failure: function(form, action) {
			                        Ext.Msg.alert('Failed', action.result.msg);
			                    }
			                });
			            }
			        }
			    },{
			        text: '重置',
			        handler: function() {
			            this.up('form').getForm().reset();
			        }
			    }]
		});
		
		this.centerPanel =Ext.create('Ext.grid.Panel', {
				region : 'center',
			    title: a.gridTitle,
			    store: this.data,
			    columns: a.columns,
			    selModel: a.sm,
			    columnLines: true,
			    height: 200,
			    width: 400,
		        listeners: {
	                selectionchange: function(model, records) {
	                    if (records[0]) {
	                        Ext.getCmp("east").getForm().loadRecord(records[0]);
	                    }
                }
            }
			});

		
		Ext.apply(this,{
			layout : 'border',
			items : [this.eastPanel,this.centerPanel]
		},a);
		this.callParent();

	}

	}

)


Ext.onReady(function(){
	var fields=["id",{name:"sysDepartName",mapping:'sysDepart',convert : function(v) {
                    	if(v && v!=null){
	              				  return v.departName;
	                    	}else{
	                    		return '';
	                    	}
           				 } 
                    },{
                    	name:"departId",mapping:'sysDepart',convert : function(v) {
                    	if(v && v!=null){
	              				  return v.departId;
	                    	}else{
	                    		return '';
	                    	}
           				 } 
                    
                    },{name:"stationName",mapping:'sysStation',convert : function(v) {
                    	if(v && v!=null){
	              				  return v.stationName;
	                    	}else{
	                    		return '';
	                    	}
           				 } 
                    },{
                    	name:"stationId",mapping:'sysStation',convert : function(v) {
                    	if(v && v!=null){
	              				  return v.stationId;
	                    	}else{
	                    		return '';
	                    	}
           				 } 
                    
                    },'userCode','loginName','userName','password','birthdayType','birthday','workstatus','hrstatus',
                    'manCode','offTel','telPhone','sex','stationIds','duty','status','userLevel','createName','createTime',
                    'updateName','updateTime'];
        var sm = Ext.create('Ext.selection.CheckboxModel',{
       		 dataIndex: 'id'
        }); 
        
        var formItems=[
        				{fieldLabel: 'ID', name: 'id',hidden:true},
        				{fieldLabel: '工号', name: 'userCode'},
        				{fieldLabel: '用户姓名', name: 'userName'},
        				{
			                xtype: 'radiogroup',
			                fieldLabel: '生日类型',
			                columns: 3,
			                defaults: {
			                    name: 'birthdayType' //Each radio has the same name so the browser will make sure only one is checked at once
			                },
			                items: [{
			                    inputValue: '0',
			                    boxLabel: '农历'
			                }, {
			                    inputValue: '1',
			                    boxLabel: '阳历'
			                }]
        				},
        				{fieldLabel: '出生日期', name: 'birthday',xtype:'datefield'},
        				{fieldLabel: '身份证号', name: 'manCode'},
        				{fieldLabel: '办公电话', name: 'offTel'},
        				{fieldLabel: '手机', name: 'telPhone'},
        				{
			                xtype: 'radiogroup',
			                fieldLabel: '性别',
			                columns: 3,
			                defaults: {
			                    name: 'sex' //Each radio has the same name so the browser will make sure only one is checked at once
			                },
			                items: [{
			                    inputValue: '0',
			                    boxLabel: '男'
			                }, {
			                    inputValue: '1',
			                    boxLabel: '女'
			                }]
        				},
        				{fieldLabel: '工作职责', name: 'duty',xtype:'textarea'}
		                ];
                    
        var columns= [
        			Ext.create('Ext.grid.RowNumberer'),
        			{header: '工号', dataIndex: 'userCode'},
			        {header: '用户名称', dataIndex: 'userName',width: 200},
			        {header: '生日类型', dataIndex: 'birthdayType',
			        				renderer:function(v){
			        					return v=='0'?'农历':'阳历';
			        					}
			        				},
    				{header: '出生日期', dataIndex: 'userName'},
    				{header: '身份证号', dataIndex: 'birthday'},
    				{header: '办公电话', dataIndex: 'offTel'},
    				{header: '手机', dataIndex: 'telPhone'},
    				{header: '性别', dataIndex: 'sex',
    								renderer:function(v){
			        					return v=='0'?'男':'女';
			        					}
		        				},
    				{header: '工作职责', dataIndex: 'duty'}
    				
			        				
			    ];
		var params={privilege:14};	    

                    
	Ext.create("xbwl.simpleView",{gridTitle:'用户管理',
								 gridSearchUrl:"user/userAction!ralaList.action",
								 fields:fields,
								 sm:sm,
								 itemsPerPage:50,
								 columns:columns,
								 formItems:formItems,
								 params:params}).show();
});
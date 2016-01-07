 var areaCityStore,cusStore,departStore,searchTbar,searchTbar2,trafficModeStore;
 Ext.onReady(function (){
 	areaCityStore=new Ext.data.Store({
		storeId:"areaCityStore",
		proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/basAreaAction!ralaList.action"}),
		baseParams:{privilege:55,limit:pageSize,filter_LIKES_areaRank:'市'},
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
        	{name:'areaName'},
        	{name:'id'}
        ])
	});
	trafficModeStore=new Ext.data.Store({
		storeId:"trafficModeStore",
		proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/dictionaryAction!ralaList.action"}),
		baseParams:{filter_EQL_basDictionaryId:18,privilege:16},
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
        	{name:'id'},
        	{name:'trafficMode',mapping:'typeName'}
        	])
	});
	countRangeStore	= new Ext.data.Store({ 
			autoLoad:true, 
			storeId:"countRangeStore",
			baseParams:{filter_EQL_basDictionaryId:201,privilege:16},
			proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/dictionaryAction!ralaList.action"}),
			reader:new Ext.data.JsonReader({
	                    root:'result',
	                    totalProperty:'totalCount'
	        },[
        	{name:'countRangeId',mapping:'typeCode'},
        	{name:'countRangeName',mapping:'typeName'}
        	])
	});
    //客商Store
	cusStore=new Ext.data.Store({
		storeId:"cusStore",
		baseParams:{privilege:61,filter_EQS_custprop:'发货代理',filter_EQL_status:1},
		proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/customerAction!ralaList.action"}),
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
        	{name:'id'},
        	{name:'cusName'}
        	])
	});
   departStore = new Ext.data.Store({ 
            storeId:"menuStore",                        
            proxy: new Ext.data.HttpProxy({url:sysPath+"/sys/departAction!ralaList.action",method:'post'}),
            baseParams:{privilege:53},
            reader: new Ext.data.JsonReader({
            root: 'result', totalProperty:'totalCount'
           }, [  
                 {name:'departName', mapping:'departName'},    
                 {name:'departId', mapping: 'departId'}
              ])                                      
     });
	searchTbar= new Ext.Toolbar({
    		width : Ext.lib.Dom.getViewWidth(),
    		items : ['部门:',
        			{
        				xtype : 'combo',
						id:'comboTypeDepart',
						hiddenId : 'departId',
		    			hiddenName : 'departId',
		    			queryParam :'filter_LIKES_departName',
						triggerAction : 'all',
						store : departStore,
						width:100,
						listWidth:245,
						minChars : 0,
						disabled:true,
						forceSelection : true,
						fieldLabel:'部门名称',
						pageSize:pageSize,
						displayField : 'departName',//显示值，与fields对应
						valueField : 'departId',//value值，与fields对应
						name : 'departId'
        			},'范围:',{
        				xtype:'combo',
        				name:'departScope',
        				id:'departScope',
        				forceSelection : true,
						triggerAction : 'all',
						model : 'local',
						width:80,
						value:'',
        				store:[
        					['all','全部'],
        					['one','指定部门'],
        					['','忽略']
        				],
        				listeners:{
        					'select':function(combo){
        						if(combo.getValue()=='' || combo.getValue()=='all'){
        							Ext.getCmp('comboTypeDepart').setDisabled(true);
        						}else{
        							Ext.getCmp('comboTypeDepart').setDisabled(false);
        						}
        					}
        				}
        			},'-','代理:',{
        				xtype:'combo',
						triggerAction :'all',
						model : 'local',
    					id:'cuscombo',
    					resizable : true,
    					width:100,
    					pageSize:pageSize,
    					minChars : 0,
    					disabled:true,
    					listWidth:200,
    					store:cusStore,
    					queryParam :'filter_LIKES_cusName',
    					valueField :'id',
						displayField :'cusName',
						hiddenName : 'cusId'
        			},'范围:',{
        				xtype:'combo',
        				name:'cusScope',
        				id:'cusScope',
        				forceSelection : true,
						triggerAction : 'all',
						model : 'local',
						value:'',
						width:80,
        				store:[
        					['all','全部'],
        					['one','指定代理'],
        					['','忽略']
        				],
        				listeners:{
        					'select':function(combo){
        						if(combo.getValue()=='' || combo.getValue()=='all'){
        							Ext.getCmp('cuscombo').setDisabled(true);
        						}else{
        							Ext.getCmp('cuscombo').setDisabled(false);
        						}
        					}
        				}
        			},'-','目的站:',{
				xtype:'combo',
				id:'citycombo',
				name:'city',
				minChars : 0,
				triggerAction : 'all',
				pageSize:pageSize,
				disabled:true,
				width:80,
				valueField : 'areaName',
				queryParam : 'filter_LIKES_areaName',
				store:areaCityStore,
				displayField : 'areaName',
				forceSelection:true
			},'范围:',{
				xtype:'combo',
				name:'endCity',
				id:'endCitycombo',
				forceSelection : true,
				triggerAction : 'all',
				model : 'local',
				value:'',
				width:80,
				store:[
					['all','全部'],
					['one','指定目的站'],
					['','忽略']
				],
				listeners:{
					'select':function(combo){
						if(combo.getValue()=='' || combo.getValue()=='all'){
							Ext.getCmp('citycombo').setDisabled(true);
						}else{
							Ext.getCmp('citycombo').setDisabled(false);
						}
					}
				}
			}
        ]
    	});
    	
    	searchTbar2= new Ext.Toolbar({
    		width : Ext.lib.Dom.getViewWidth(),
    		items : ['产品',{
            	xtype:'combo',
				name:'productType',
				id:'producttypecombo',
				forceSelection : true,
				disabled:true,
				width:80,
				triggerAction : 'all',
				model : 'local',
				width:80,
				store:[
					['新邦自提','新邦自提'],
					['新邦送货','新邦送货'],
					['中转送货','中转送货'],
					['专车送货','专车送货'],
					['中转自提','中转自提'],
					['外发自提','外发自提'],
					['外发送货','外发送货']
				]
            },'范围:',{
				xtype:'combo',
				name:'productTypeScope',
				id:'productTypeScope',
				forceSelection : true,
				triggerAction : 'all',
				model : 'local',
				value:'all',
				width:80,
				store:[
					['all','全部'],
					['one','指定产品'],
					['','忽略']
				],
				listeners:{
					'select':function(combo){
						if(combo.getValue()=='' || combo.getValue()=='all'){
							Ext.getCmp('producttypecombo').setDisabled(true);
						}else{
							Ext.getCmp('producttypecombo').setDisabled(false);
						}
					}
				}
			},'-','运输方式:',
        			{
        				xtype:'combo',
						triggerAction : 'all',
    					model : 'local',
    					name:'trafficMode',
    					disabled:true,
    					id:'trafficModecombo',
    					valueField : 'trafficMode',
						displayField : 'trafficMode',
						name : 'trafficMode',
						width:80,
						store:trafficModeStore
        			},'范围:',{
        				xtype:'combo',
        				name:'trafficModeScope',
        				id:'trafficModeScope',
        				forceSelection : true,
						triggerAction : 'all',
						model : 'local',
						value:'all',
						width:80,
        				store:[
        					['all','全部'],
        					['one','指定方式'],
        					['','忽略']
        				],
        				listeners:{
        					'select':function(combo){
        						if(combo.getValue()=='' || combo.getValue()=='all'){
        							Ext.getCmp('trafficModecombo').setDisabled(true);
        						}else{
        							Ext.getCmp('trafficModecombo').setDisabled(false);
        						}
        					}
        				}
        			}
        	]
    	});
 });
 
 function timeHead(store,afield){
 	var myarr=store.reader.jsonData.resultMap;
	if(myarr.length>0){
		var newArr= new Array();
		var num=0;
		for(var temp in myarr[0]){
			var flag=true;
			for(var j=0;j<afield.length;j++){
				if(afield[j]==temp){
					flag=false;
					break;
				}
			}
			if(flag){
				newArr[num] =temp;
				num++;
			}
		}
		newArr.sort();
		
		return newArr;
	}
 }
 
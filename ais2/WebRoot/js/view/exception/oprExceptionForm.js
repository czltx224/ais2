//异常新增和处理JS
var createMan="";
var doMan="";
var qmMan="";
var status='0';
var serviceDepartName;
var onebar;
var form;
var privilege=120;
var v='';
var v1='';
var v2='';
var v3='';

if(stationIds.indexOf(stationId)!=-1){
	qmMan="品管";
}

Ext.onReady(function() {
	Ext.QuickTips.init();
	
	var form1 = new Ext.form.FormPanel({
				id : 'form1',
				frame : true,
				width : 100,
				cls : 'displaynone',
				hidden : true,
				items : [],
				buttons : []
			});
	form1.render(document.body);

	var fields=[{name:"id",mapping:'id'},'dno','cusName','flightMainNo','flightNo','subNo','exceptionNode',
										'consignee','consigneeAdd','piece','weight','exceptionType1',
										'exceptionType2','exceptionName','exceptionTime','exceptionRepar',
										'exceptionReparTime','exceptionReparCost','dutyDepartId','dutyDepartName',
										'exceptionPiece','exceptionAdd','exceptionDescribe','suggestion','status',
										'finalResult','dealName','dealTime','dealReasult','isCp','isCus','isRepar',
										'isWeb','exptionAdd','finalDuty','finalPiece','qm','qmTime','qmSuggestion',
										'submitQualified','dealQualified','reparQualified','createTime','createName',
										'updateTime','updateName','ts','departId','departName','createDepartId','createDepartName'];
	
    var jsonread= new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'},
                    fields);

	//异常责任部门
	var serviceDepartStore = new Ext.data.Store({ 
            storeId:"serviceDepartStore",                        
            proxy: new Ext.data.HttpProxy({url:sysPath+"/sys/departAction!findDepart.action",method:'post'}),
            baseParams:{
               privilege:53
            },
            reader: new Ext.data.JsonReader({
            	root: 'resultMap', totalProperty:'totalCount'
           	 },[{name:'departName', mapping:'DEPARTNAME'},    
                {name:'departId', mapping: 'DEPARTID'}])
     });
		
	//异常环节
/*	var nodeStore= new Ext.data.Store({
			storeId:"nodeStore",
			proxy:new Ext.data.HttpProxy({url:sysPath+"/stock/oprNodeAction!list.action"}),
			reader:new Ext.data.JsonReader({
	                    root:'result',
	                    totalProperty:'totalCount'
	        },[
        	{name:'nodeOrder',mapping:'nodeOrder'},
        	{name:'outName',mapping:'outName'}
        	])
	});
	nodeStore.load();*/
	var nodeStore= new Ext.data.Store({
			storeId:"nodeStore",
			proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/exceptionTypeAction!getExceTypeNode.action"}),
			baseParams:{
				privilege:116
			},
			reader:new Ext.data.JsonReader({
	                    root:'resultMap',
	                    totalProperty:'totalCount'
	        },[
        	{name:'id',mapping:'ID'},
        	{name:'exceptionNode',mapping:'NODENAME'}
        	])
	});
	
	//exceptionClassStore主异常类型
	var exceptionClassStore= new Ext.data.Store({
			storeId:"exceptionClassStore",
			proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/exceptionTypeAction!list.action"}),
			baseParams:{
				privilege:116
			},
			reader:new Ext.data.JsonReader({
	                    root:'result',
	                    totalProperty:'totalCount'
	        },[
        	{name:'id',mapping:'id'},
        	{name:'typeName',mapping:'typeName'}
        	])
	});
//	exceptionClassStore.load();
		
		
		function creatDealFun(object){
		 	if(doMan=="责任人"){
		 		if(status=='0'){
					object.disable();
				}else if(status=='1'){
					object.enable();
				}else if(status=='2'){
					object.enable();
				}else{
					object.disable();
				}
			}else{
				object.disable();
			}							
		}
		
		function creatQmFun(object0){
			if(qmMan=="品管"){
		       	if(status==0){
		       		object0.disable();
		       	}else{
		       		object0.setValue(new Date());
			       	object0.enable();
		    	}
		   	}else{
		   		object0.disable();
		   	}
		}
		
		function creatPeopleFun(object0){
			if(createMan=="创建人"){
		       	if(status==1){
			       	object0.enable();
		       	}else{
		       		object0.disable();
		    	}
		   	}else{
		   		object0.disable();
		   	}
		}
		
	if(formId!=''){
		Ext.Ajax.request({
			url : sysPath+ "/exception/oprExceptionAction!list.action",
			params:{
				filter_EQL_id:formId,
				limit : pageSize
			},
			success : function(response) { // 回调函数有1个参数  departId
				status=Ext.decode(response.responseText).result[0].status;
				if(Ext.decode(response.responseText).result[0].createDepartId.indexOf(departId) != -1){
					createMan="创建人";
				}
				if(Ext.decode(response.responseText).result[0].dutyDepartId.indexOf(departId) != -1){
					doMan="责任人";
				}
				createPanel();
				
				var object0=Ext.getCmp('savebtn');
		        if(createMan=="创建人"){
		        	if(status==1){
			        	object0.enable();
		         	}else{
		         		object0.disable();
		       		}
		       	}
				
				
				var object=Ext.getCmp('dobtn');
		        if(doMan=="责任人"){
		       		if(status==1||status==2){
			       		object.enable();
		       		}else{
		      			object.disable();
	        		}
	         	}
		         
		        var object2=Ext.getCmp('finalbtn');
			    if(createMan=="创建人"){
			   		if(status==2){
			       		object2.enable();
			   		}else{
			   			object2.disable();
			   		}
				}                
		        
		        var object3=Ext.getCmp('qmbtn');
				if(qmMan=="品管"){
				   object3.enable();
				}	                  
		         
		         		  
			},
			failure : function(response) {
				Ext.Msg.alert(alertTitle, '页面加载不成功，请联系维护人员');
			}
		});			
 	}else{
 		createPanel();
 	}
	
	
	function createPanel(){
				var onebar = new Ext.Toolbar({
					id:'onebar',
				//	height : 26,
					//region : 'north',
					    items :  ['&nbsp;&nbsp;',
									{
										text:'<B>反馈异常</B>',
										id : 'savebtn',
										disabled : true,
										tooltip : '保存或者修改异常信息',
										iconCls:'groupSave',
										listeners : {
											'render':function(){
												if(formId==''){
													Ext.getCmp('savebtn').enable();
												}
											 }
										},
										handler : saveException
									},'-','&nbsp;&nbsp;',
									{
										text:'<B>重置</B>',
										id : 'clearbtn',
										//disabled : true,
										tooltip : '重置',
										iconCls:'refresh',
										handler : resetException
									},'-','&nbsp;&nbsp;',
									{
										text:'<B>处理异常</B>',
										id : 'dobtn',
										disabled : true,
										tooltip : '处理异常信息',
										iconCls:'refresh',
										listeners : {
											'render':function(){
											 }
										},
										handler : doException
									},'-','&nbsp;&nbsp;',
									{
										text:'<B>最终处理</B>',
										id : 'finalbtn',
										disabled : true,
										tooltip : '最终处理',
										iconCls:'refresh',
										listeners : {
											'render':function(){
												 
											 }
										},
										handler : finalDoException
									},'-','&nbsp;&nbsp;',
									{
										text:'<B>品管审核</B>',
										id : 'qmbtn',
										disabled : true,
										tooltip : '品管审核',
										iconCls:'refresh',
										listeners : {
											'render':function(){
											 
											 }
										},
										handler : qmDoException
									},'-','&nbsp;&nbsp;',{
							   			xtype:'label',
							   			id:'showMsg',
							   			width:380
							   		}
					]
			});	
	
		var form = new Ext.form.FormPanel({
								//labelAlign : 'left',
								region : 'center',
								frame : true,
								fileUpload : true,
							//	url:sysPath+'/user/userAction!ralaList.action?privilege=17',
								//bodyStyle : 'padding:3 5 0',
							    width : Ext.lib.Dom.getViewWidth()-2,
							    height:Ext.lib.Dom.getViewHeight()-2,
							    autoHeight:true,
								reader :jsonread,
								border : true,
								labelWidth : 60, 
								labelAlign : "right",
						        items : [
						      	   {
									xtype:'fieldset',
									title:"配送单信息",
									id:'setdev0',
									autoWidth:true,
									autoHeight : true,
									//layout:'column',
									labelWidth : 80,
								//	defaults:{width : 1100,border:false,layout:'form',frame:false,labelAlign:'right',labelWidth:70},
									//height:193,
									//style:'margin:1px;',
									//layoutConfig: {columns:5},
									//bodyStyle:'padding:5px 0px 0px 0px',
									//defaults:{border:false,layout:'form',frame:false,labelAlign:'right',labelWidth:70},
									//defaults : {width : 1100},
									items:[
										{
											layout : 'column',
											border : false,
											items : [{
														columnWidth : .25,
														layout : 'form',
														border : false,
														labelWidth : 80, // 标签宽度
														items : [
														   {name : "ts",
														    id:'ts',
															xtype : "hidden"
														 },{name : "id",
														    id:'eId',
															xtype : "hidden"
														 },{
															fieldLabel : '配送单号<span style="color:red">*</span>',
															name : 'dno',
															id:'dno',
															maxLength:10,
															disabled:true,
															allowBlank : false,
															xtype : 'numberfield', // 设置为数字输入框类型
															anchor : '100%',
															enableKeyEvents:true,
															listeners : {
																keyup:function(numberfield, e){
														                if(e.getKey() == 13 ){
																			Ext.getCmp('nodeOrderCombo2').focus();
														                 }
														 		},
												 				'render':function(numberfield){
												                    if(formDno!=""){
												                    	numberfield.setValue(formDno);
												                    	numberfield.disable();
												                    	Ext.Ajax.request({
												 							url:sysPath+"/fax/oprFaxInAction!list.action",
																			params:{
																				privilege:68,
																				filter_EQL_dno:formDno
																			},
																			success : function(response) { // 回调函数有1个参数
																				Ext.getCmp('piece').setValue(Ext.decode(response.responseText).result[0].piece);
																				Ext.getCmp('cusName').setValue(Ext.decode(response.responseText).result[0].cpName);
																				Ext.getCmp('weight').setValue(Ext.decode(response.responseText).result[0].cqWeight);
																				Ext.getCmp('flightMainNo').setValue(Ext.decode(response.responseText).result[0].flightMainNo);
																				Ext.getCmp('consignee').setValue(Ext.decode(response.responseText).result[0].consignee);
																				Ext.getCmp('subNo').setValue(Ext.decode(response.responseText).result[0].subNo);
																				Ext.getCmp('flightNo').setValue(Ext.decode(response.responseText).result[0].flightNo);
																			//	serviceDepartName=Ext.decode(response.responseText).result[0].cusDepartName;
																				//Ext.getCmp('serviceDepartId').setRawValue(serviceDepartName);
																				//Ext.getCmp('serviceDepartId').setValue(Ext.decode(response.responseText).result[0].cusDepartId);
																				var vstr1= '代理客商名称：'+Ext.decode(response.responseText).result[0].cpName;
																				var vstr2= '收货人信息：'+Ext.decode(response.responseText).result[0].consignee;
																				vstr2+='<span style="color:red">/</span>'+Ext.decode(response.responseText).result[0].consigneeTel;
																				vstr2+='<span style="color:red">/</span>'+Ext.decode(response.responseText).result[0].addr;
																				Ext.getCmp('showMsg1').getEl().update(vstr2);
																				Ext.Ajax.request({
														 							url:sysPath+"/sys/customerAction!list.action",
																					params:{
																						privilege:68,
																						filter_EQL_id:Ext.decode(response.responseText).result[0].cusId
																					},
																					success : function(response2) { // 回调函数有1个参数
																						vstr1+='<span style="color:red">/</span>'+Ext.decode(response2.responseText).result[0].phone1;
																						vstr1+='<span style="color:red">/</span>'+Ext.decode(response2.responseText).result[0].phone2;
																						vstr1+='<span style="color:red">/</span>'+Ext.decode(response2.responseText).result[0].phone3;
																						vstr1+='<span style="color:red">/</span>'+Ext.decode(response2.responseText).result[0].cusAdd;
																						Ext.getCmp('showMsg2').getEl().update(vstr1);
																					},
																					failure : function(response) {
																						Ext.getCmp('showMsg2').getEl().update(vstr1);
																					}
																				});	
																			},
																			failure : function(response) {
																				
																			}
																		});	
												                    }else{
												                    	numberfield.enable();
												                    }
												 				 },
												 				 blur:function(numberfield){
												 				 	if(formDno==""){
												 				 		var val;
												 				 		if(numberfield.getValue()==''){
												 				 			return;
												 				 		}else{
												 				 			val=numberfield.getValue();
												 				 		}
												 				 		Ext.Ajax.request({
												 							url:sysPath+"/fax/oprFaxInAction!list.action",
																			params:{
																				privilege:68,
																				filter_EQL_dno:val
																			},
																			success : function(response) {
																				if(Ext.decode(response.responseText).result!=''){
																					Ext.getCmp('piece').setValue(Ext.decode(response.responseText).result[0].piece);
																					Ext.getCmp('piece').enable();
																					Ext.getCmp('cusName').setValue(Ext.decode(response.responseText).result[0].cpName);
																					Ext.getCmp('cusName').enable();
																					Ext.getCmp('weight').setValue(Ext.decode(response.responseText).result[0].cqWeight);
																					Ext.getCmp('weight').enable();
																					Ext.getCmp('flightMainNo').setValue(Ext.decode(response.responseText).result[0].flightMainNo);
																					Ext.getCmp('flightMainNo').enable();
																					Ext.getCmp('consignee').setValue(Ext.decode(response.responseText).result[0].consignee);
																					Ext.getCmp('consignee').enable();
																					Ext.getCmp('subNo').setValue(Ext.decode(response.responseText).result[0].subNo);
																					Ext.getCmp('subNo').enable();
																					Ext.getCmp('flightNo').setValue(Ext.decode(response.responseText).result[0].flightNo);
																				    Ext.getCmp('flightNo').enable();
																				   
																				    serviceDepartName=Ext.decode(response.responseText).result[0].cusDepartName;
																				    Ext.getCmp('serviceDepartId').setValue(Ext.decode(response.responseText).result[0].cusDepartCode);
																				    Ext.getCmp('serviceDepartId').setRawValue(serviceDepartName);
																				    var vstr1= '代理客商名称：'+Ext.decode(response.responseText).result[0].cpName;
																				    
																					var vstr2= '收货人信息：'+Ext.decode(response.responseText).result[0].consignee;
																					vstr2+='<span style="color:red">/</span>'+Ext.decode(response.responseText).result[0].consigneeTel;
																					vstr2+='<span style="color:red">/</span>'+Ext.decode(response.responseText).result[0].addr;
																					Ext.getCmp('showMsg1').getEl().update(vstr2);
																					Ext.Ajax.request({
															 							url:sysPath+"/sys/customerAction!list.action",
																						params:{
																							privilege:68,
																							filter_EQL_id:Ext.decode(response.responseText).result[0].cusId
																						},
																						success : function(response2) { // 回调函数有1个参数
																							 if(Ext.decode(response2.responseText).result.length==1){
																								vstr1+='<span style="color:red">/</span>'+Ext.decode(response2.responseText).result[0].phone1;
																								vstr1+='<span style="color:red">/</span>'+Ext.decode(response2.responseText).result[0].phone2;
																								vstr1+='<span style="color:red">/</span>'+Ext.decode(response2.responseText).result[0].phone3;
																								vstr1+='<span style="color:red">/</span>'+Ext.decode(response2.responseText).result[0].cusAdd;
																								Ext.getCmp('showMsg2').getEl().update(vstr1);
																							 }else{
																							 	Ext.getCmp('showMsg2').getEl().update('<span style="color:red">这票货的代理公司不存在，请注意。</span>');
																							 }
																						},
																						failure : function(response) {
																							Ext.getCmp('showMsg2').getEl().update(vstr1);
																						}
																					});	
																				    
																				}else{
																						Ext.getCmp('showMsg').getEl().update('<span style="color:red">没有这票货物，请重新输入配送单号。</span>');
																						numberfield.markInvalid("没有这票货物，请重新输入配送单号");
																						numberfield.selectText();
																						numberfield.focus();
																						
																						Ext.getCmp('piece').setValue('');
																						Ext.getCmp('piece').disable();
																						Ext.getCmp('cusName').setValue('');
																						Ext.getCmp('cusName').disable();
																						Ext.getCmp('weight').setValue('');
																						Ext.getCmp('weight').disable();
																						Ext.getCmp('flightMainNo').setValue('');
																						Ext.getCmp('flightMainNo').disable();
																						Ext.getCmp('consignee').setValue('');
																						Ext.getCmp('consignee').disable();
																						Ext.getCmp('subNo').setValue('');
																						Ext.getCmp('subNo').disable();
																						Ext.getCmp('flightNo').setValue('');
																					    Ext.getCmp('flightNo').disable();
																					    
																					    Ext.getCmp('serviceDepartId').setRawValue('');
																						Ext.getCmp('serviceDepartId').setValue('');
																				
																				}
																			},
																			failure : function(response) {
																				
																			}
																		});
																	}
												 				 }
												 				 
												 			}
														},{
															fieldLabel : '件数',
															name : 'piece',
															id:'piece',
															readOnly:true,
															disabled:true,
															xtype : 'numberfield', // 设置为数字输入框类型
															anchor : '100%'
														}]

													},{
														columnWidth : .25,
														layout : 'form',
														border : false,
														labelWidth : 80, // 标签宽度
														items : [{
															fieldLabel : '代理名称', // 标签
															id:'cusName',
															readOnly:true,
															xtype : 'textfield',
															name : 'cusName', // name:后台根据此name属性取值
															disabled:true,
															anchor : '100%'
														},{
															fieldLabel : '重量',
															name : 'weight',
															readOnly:true,
															id:'weight',
															disabled:true,
															xtype : 'numberfield', // 设置为数字输入框类型
															anchor : '100%'
														}]

													},{
														columnWidth : .25,
														layout : 'form',
														border : false,
														labelWidth : 80, // 标签宽度
														items : [{
															fieldLabel : '运单号', // 标签
															id : 'flightMainNo',
															readOnly:true,
															disabled:true,
															xtype : 'textfield',
															name : 'flightMainNo', // name:后台根据此name属性取值
															allowBlank : true, // 是否允许为空
															anchor : '100%'
															
														},{
															fieldLabel : '收货人姓名', // 标签
															id : 'consignee',
															readOnly:true,
															disabled:true,
															xtype : 'textfield',
															name : 'consignee', // name:后台根据此name属性取值
															anchor : '100%'
														}]

													},{
														columnWidth : .25,
														layout : 'form',
														border : false,
														labelWidth : 80, // 标签宽度
														items : [{
															fieldLabel : '分单号', // 标签
															id:'subNo',
															readOnly:true,
															disabled:true,
															xtype : 'textfield',
															name : 'subNo', // name:后台根据此name属性取值
															maxLength : 20, // 可输入的最大文本长度,不区分中英文字符
															allowBlank : true,
															anchor : '100%'
															
														},{
																	fieldLabel : '航班号',
																	xtype : 'textfield',
																	id:'flightNo',
																	readOnly:true,
																	disabled:true,
																	name : 'flightNo',
																	allowBlank : true,
																	anchor : '100%'	
																}]

													}]
													
										}]},
									//*******************************************************	
									{
									xtype:'fieldset',
									title:"异常反馈部门填写信息",
									id:'setdev1',
								//	layout:'column',
									//height:93,
									//style:'margin:1px;',
								//	layoutConfig: {columns:10},
									//bodyStyle:'padding:5px 0px 0px 0px',
							
								
									autoWidth:true,
									autoHeight : true,
									labelWidth : 80,
								//	defaults:{width : 1100,border:false,layout:'form',frame:false,labelAlign:'right',labelWidth:70},
									items:[{
											layout : 'column',
											border : false,
											items : [{
														columnWidth : .25,
														layout : 'form',
														border : false,
														labelWidth : 80, // 标签宽度
														items : [{
															xtype : 'combo',
															triggerAction : 'all',
															store : nodeStore,
															emptyText : "请选择异常环节",
															allowBlank : false,
															editable:false,
															minChars : 1,
															queryParam:'filter_LIKES_exceptionNode',
															fieldLabel:'异常环节<span style="color:red">*</span>',
															displayField : 'exceptionNode',//显示值，与fields对应
															valueField : 'exceptionNode',//value值，与fields对应
															hiddenName : 'exceptionNode',
															id:'nodeOrderCombo2',
															anchor : '100%',
															enableKeyEvents:true,
															listeners : {
																keyup:function(numberfield, e){
														                if(e.getKey() == 13 ){
																			Ext.getCmp('exceptionTypeCombo2').focus();
														                 }
														 		},
																'render':function(){
																	var combon=Ext.getCmp('nodeOrderCombo2');	
																	if(formId!=''){
													                    creatPeopleFun(combon);												                   
																	}else{
																		combon.enable();
																	}
												 				 },
												 				 select:function(combo,recode,index){
																	 	exceptionClassStore.proxy=new Ext.data.HttpProxy({
																	 		url:sysPath+"/sys/exceptionTypeAction!list.action",
																	 		method:'post'
																	 	});
																	 	exceptionClassStore.baseParams={
															               privilege:116,
															               filter_EQS_nodeName:combo.getValue()
															            }
															            exceptionClassStore.reader=new Ext.data.JsonReader({
																                     root: 'result', totalProperty:'totalCount'
																                },[
																		        	{name:'id',mapping:'id'},
																		        	{name:'typeName',mapping:'typeName'}
																		        	])
																        exceptionClassStore.load();
													 				 	Ext.getCmp('showMsg').getEl().update('<span style="color:red"></span>');
																
																}
															}
														   },{
															fieldLabel : '修复人', // 标签
															id : 'exceptionRepar',
															xtype : 'textfield',
															maxLength:50,
															name : 'exceptionRepar', // name:后台根据此name属性取值
															allowBlank : true, // 是否允许为空
															anchor : '100%',
															enableKeyEvents:true,
															listeners : {
																keyup:function(numberfield, e){
														                if(e.getKey() == 13 ){
																			Ext.getCmp('exceptionReparTime').focus();
														                 }
														 		},
																'render':function(){
																	var combon=Ext.getCmp('exceptionRepar');
																	if(formId!=''){
													                    creatPeopleFun(combon);												                   
																	}else{
																		combon.enable();
																	}
												 				 }
															}
														},{
															fieldLabel : '发现人<span style="color:red">*</span>', // 标签
															id : 'exceptionName',
															xtype : 'textfield',
															maxLength:50,
															allowBlank : false,
															name : 'exceptionName', // name:后台根据此name属性取值
															anchor : '100%',
															enableKeyEvents:true,
															listeners : {
																keyup:function(numberfield, e){
														                if(e.getKey() == 13 ){
																			Ext.getCmp('exceptionDescribe').focus();
														                 }
														 		},
																'render':function(){
																	var combon=Ext.getCmp('exceptionName');
												                    if(formId!=''){
													                    creatPeopleFun(combon);												                   
																	}else{
																		combon.enable();
																	}																                   
												 				 }
															}
															
														}]

													},{
														columnWidth : .25,
														layout : 'form',
														border : false,
														labelWidth : 80, // 标签宽度
														items : [{
															xtype : 'combo',
															triggerAction : 'all',
															store : exceptionClassStore,
															emptyText : "请选择异常类型",
															allowBlank : false,
															editable:false,
															fieldLabel:'异常类型<span style="color:red">*</span>',
															mode : "local",//获取本地的值
															displayField : 'typeName',//显示值，与fields对应
															valueField : 'id',//value值，与fields对应
															name : 'exceptionType1',
															id:'exceptionTypeCombo2',
															anchor : '100%',
															enableKeyEvents:true,
															listeners : {
																keyup:function(numberfield, e){
														                if(e.getKey() == 13 ){
																			Ext.getCmp('serviceDepartId').focus();
														                 }
														 		},
												 				'render':function(){
																	var combon=Ext.getCmp('exceptionTypeCombo2');
																	if(formId!=''){
													                    creatPeopleFun(combon);												                   
																	}else{
																		combon.enable();
																	}												                   
												 				 },
												 				 focus:function(com){
												 				 	if(Ext.getCmp('nodeOrderCombo2').getValue()==''){
												 				 		Ext.getCmp('showMsg').getEl().update('<span style="color:red">请先选择异常环节，再选择异常类型。</span>');
																       	Ext.getCmp('nodeOrderCombo2').focus();
																       	Ext.getCmp('nodeOrderCombo2').markInvalid("输入的配送单号格式不正确");
												 				 	}
												 				 
												 				 },
												 				 select:function(c){
												 				 	Ext.Ajax.request({
											 							url:sysPath+"/sys/exceptionTypeAction!list.action",
																		params:{
																			filter_EQL_id:c.getValue()
																		},
																		success : function(response2) { // 回调函数有1个参数
																			if(Ext.decode(response2.responseText).result[0].isDoPiece==1){
																				Ext.getCmp('exceptionPiece').enable();
																			}else{
																				Ext.getCmp('exceptionPiece').disable();
																			}
																			//Ext.getCmp('showMsg2').getEl().update(vstr1);
																		},
																		failure : function(response) {
																			//Ext.getCmp('showMsg2').getEl().update(vstr1);
																		}
																	});	
												 				 
												 				 }
															}
														   },{
														   	xtype : 'datefield',
														    fieldLabel : '修复时间',
											    			id : 'exceptionReparTime',
											    			name:'exceptionReparTime',
												    		format : 'Y-m-d h:m:s',
												    		anchor : '100%',
												    		enableKeyEvents:true,
															listeners : {
																keyup:function(numberfield, e){
														                if(e.getKey() == 13 ){
																			Ext.getCmp('exceptionReparCost').focus();
														                 }
														 		},
																'render':function(){
																	var combon=Ext.getCmp('exceptionReparTime');
												                    if(formId!=''){
													                    creatPeopleFun(combon);												                   
																	}else{
																		combon.enable();
																	}																                   
												 				 }
															}
															}/*,{xtype : 'datefield',
											    			id : 'exceptionTime',
											    			name:'exceptionTime',
											    		//	hidden:true,
															allowBlank : false,
															value:new Date(),
												    		format : 'Y-m-d h:m:s',
												    		emptyText : "选择异常发现时间",
												    		anchor : '95%',
												    		listeners : {
																'render':function(){
																	var combon=Ext.getCmp('exceptionTime');
												                    if(formId!=''){
													                    creatPeopleFun(combon);												                   
																	}else{
																		combon.enable();
																	}																                   
												 				 }
															}
														}*/]

													},{
														columnWidth : .25,
														layout : 'form',
														border : false,
														labelWidth : 80, // 标签宽度
														items : [
														   {xtype : 'combo',
															id:'serviceDepartId',
										        			fieldLabel: '处理部门<span style="color:red">*</span>',
														    queryParam : 'filter_LIKES_departName',
															minChars : 1,
															allowBlank : false,
															triggerAction : 'all',
															forceSelection : true,
															store: serviceDepartStore,
															pageSize : 50,
															listWidth:245,
															displayField : 'departName',
															valueField : 'departId',
															name:'dutyDepartName',
															anchor : '100%',
															emptyText : "请选择处理部门",
															enableKeyEvents:true,
															listeners : {
																select:function(v){
																	//alert(v.getValue()+","+v.getRawValue());
																},
																keyup:function(numberfield, e){
													                if(e.getKey() == 13 ){
																		if(Ext.getCmp('exceptionPiece').disabled){
																			Ext.getCmp('exceptionRepar').focus();
																		}else{
																			Ext.getCmp('serviceDepartId').focus();
																		}
													                 }
														 		},
																'render':function(com){
																	var combon=Ext.getCmp('serviceDepartId');
												                    if(formId!=''){
													                    creatPeopleFun(combon);												                   
																	}else{
																		combon.enable();
																	}
																	if(serviceDepartName!=''){
																		//combon.setRawValue(serviceDepartName);
																	}
												 				 }
															}
															
										        		},{
															fieldLabel : '修复金额',
															name : 'exceptionReparCost',
															id:'exceptionReparCost',
															maxLength:13,
															xtype : 'numberfield', // 设置为数字输入框类型
															anchor : '100%',
															enableKeyEvents:true,
															listeners : {
																keyup:function(numberfield, e){
														                if(e.getKey() == 13 ){
																			Ext.getCmp('exceptionName').focus();
														                 }
														 		},
																'render':function(){
																	var combon=Ext.getCmp('exceptionReparCost');
												                    if(formId!=''){
													                    creatPeopleFun(combon);												                   
																	}else{
																		combon.enable();
																	}																                   
												 				 }
															}
														}]

													},{
														columnWidth : .25,
														layout : 'form',
														border : false,
														labelWidth : 80, // 标签宽度
														items : [{
															fieldLabel : '异常件数',
															name : 'exceptionPiece',
															allowBlank : true,
															id:'exceptionPiece',
															maxLength:8,
															xtype : 'numberfield', // 设置为数字输入框类型
															anchor : '100%',
															enableKeyEvents:true,
															listeners : {
																keyup:function(numberfield, e){
														                if(e.getKey() == 13 ){
																			Ext.getCmp('exceptionRepar').focus();
														                 }
														 		},
																'render':function(){
																	var combon=Ext.getCmp('exceptionPiece');
												                    if(formId!=''){
													                    creatPeopleFun(combon);										                   
																	}else{
																		combon.enable();
																	}																                   
												 				 }
															}
														},{
															fieldLabel : '反馈人', // 标签
															id : 'createName',
															xtype : 'textfield',
															readOnly:true,
															maxLength:20,
															allowBlank : false,
															name : 'createName', // name:后台根据此name属性取值
															anchor : '100%',
															listeners : {
																'render':function(){
																	var object=Ext.getCmp('createName');
												                    if(formId!=''){
													                    creatPeopleFun(object);												                   
																	}else{
																		object.enable();
																		object.setValue(userName);
																	}				
												 				 }
															}
															}]

													}] //**************************
													
										}, {
											layout : 'column',
											border : false,
											items : [{
														columnWidth : .4,
														layout : 'form',
														border : false,
														labelWidth : 80, // 标签宽度
														items : [{
															xtype : 'textfield',
															inputType : 'file',
															fieldLabel : '货物异常图片',
															name : 'exceptionAdds',
															id:'exceptionAds',
															anchor : '100%',
															vtype : "imgVtype",
															listeners : {
																'render':function(){
																	var combon=Ext.getCmp('exceptionAds');
												                    if(formId!=''){
													                    creatPeopleFun(combon);												                   
																	}else{
																		combon.enable();
																	}																                   
												 				 }
															}
														}]

													},{
														columnWidth : .05,
														layout : 'form',
														border : false,
														labelWidth : 100, // 标签宽度
														items : [
															 {xtype:'button',
							    							  text:'查看图片',
							    							  width:50,
							    							  listeners : {
																'render':function(button){
												                    if(formId!=''){
																		button.enable();
																	}else{
													                    button.disable();											                   
																	}																                   
												 				 }
															  },
							    							  handler:function(){
							    							 		var img1 = new Ext.Panel({
																		html : '<center><img id="img1"  width="500" height="275"  style="cursor:hand" ></img><center>',
																		cls : 'empty'
																	});
																
							    							  
							    							  		var mymodify = new Ext.FormPanel({
																			region : 'center',
																			width : 500,
																			labelAlign : 'left',
																			bodyStyle : 'padding:0px 0px 0px',
																			id : 'newadd',
																			layout : 'form',
																			labelSeparator : '：',
																			url:sysPath+'/exception/oprExceptionAction!list.action?filter_EQL_id='
																					+formId,
																			reader : jsonread,
																			items : [img1]
																	
																	});
																	
																	mymodify.load({
																			waitMsg : '正在载入数据...',
																			success : function(_form, action) {
															
																				Ext.get("img1").dom.src = exceptionImagesUrl
																						+ action.result.data.exceptionAdd;
																			},
																			failure : function(_form, action) {
																				Ext.MessageBox.alert('编辑', '载入失败');
																			}
																		}
															
																);
																	var bigpanel = new Ext.Panel({
																			frame : true,
																			border : false,
																			layout : 'border',
																			height : 350,
																			bodyStyle : 'padding:5px 5px 5px',
																			labelAlign : "left",
																			buttonAlign : "right",
																			buttons : [ {
																				text : '关闭',
																				iconCls : 'deleteIcon',
																				handler : function() {
																					popupWin.hide();
																				}
																			}],
																			items : [mymodify]
															
																	});
																	
																		popupWin = new Ext.Window({
																					title : '查看异常图片',
																					layout : 'fit',
																					width : 500,
																					height : 350,
																					closeAction : 'hide',
																					plain : true,
																					modal : true,
																					items : bigpanel
																				});
																		popupWin.on('hide', function() {
																					bigpanel.destroy();
																				});
																		popupWin.show();
							    							  }
							    							 }
														]

													},{
														columnWidth : .05,
														layout : 'form',
														border : false,
														labelWidth : 80, // 标签宽度
														items : [
															
														]

													},{
														columnWidth : .4,
														layout : 'form',
														border : false,
														labelWidth : 100, // 标签宽度
														items : [{
															xtype : 'textfield',
															inputType : 'file',
															fieldLabel : '货物修复后图片',
															name : 'exptionAdds',
															id:'exptionAdds',
															anchor : '100%',
															vtype : "imgVtype",
															listeners : {
																'render':function(){
																	var combon=Ext.getCmp('exptionAdds');
												                    if(formId!=''){
													                    creatPeopleFun(combon);												                   
																	}else{
																		combon.enable();
																	}																                   
												 				 }
															}
														}
															
														]
													},{
														columnWidth : .05,
														layout : 'form',
														border : false,
														labelWidth : 80, // 标签宽度
														items : [
															 {xtype:'button',
							    							  text:'查看图片',
							    							  width:50,
							    							  listeners : {
																'render':function(button){
												                    if(formId!=''){
																		button.enable();
																	}else{
													                    button.disable();											                   
																	}																                   
												 				 }
															  },
							    							  handler:function(){
							    							 		var img2 = new Ext.Panel({
																		html : '<center><img id="img2"  width="500" height="275"  style="cursor:hand" ></img><center>',
																		cls : 'empty'
																	});
																
							    							  
							    							  		var mymodify = new Ext.FormPanel({
																			region : 'center',
																			width : 500,
																			labelAlign : 'left',
																			bodyStyle : 'padding:0px 0px 0px',
																			id : 'newadd2',
																			layout : 'form',
																			labelSeparator : '：',
																			url:sysPath+'/exception/oprExceptionAction!list.action?filter_EQL_id='
																					+formId,
																			reader : jsonread,
																			items : [img2]
																	
																	});
																	
																	mymodify.load({
																			waitMsg : '正在载入数据...',
																			success : function(_form, action) {
															
																				Ext.get("img2").dom.src = exceptionImagesUrl
																						+ action.result.data.exptionAdd;
															
																			},
																			failure : function(_form, action) {
																				Ext.MessageBox.alert('编辑', '载入失败');
																			}
																		}
															
																	);
																	
																	var bigpanel = new Ext.Panel({
																			frame : true,
																			border : false,
																			layout : 'border',
																			height : 350,
																			bodyStyle : 'padding:5px 5px 5px',
																			labelAlign : "left",
																			buttonAlign : "right",
																			buttons : [ {
																				text : '关闭',
																				iconCls : 'deleteIcon',
																				handler : function() {
																					popupWin.hide();
																				}
																			}],
																			items : [mymodify]
															
																	});
																	
																		popupWin = new Ext.Window({
																					title : '查看异常图片',
																					layout : 'fit',
																					width : 500,
																					height : 350,
																					closeAction : 'hide',
																					plain : true,
																					modal : true,
																					items : bigpanel
																				});
																		popupWin.on('hide', function() {
																					bigpanel.destroy();
																				});
																		popupWin.show();
							    							  
							    							  
							    							  
							    								  
							    							  }
							    							  }
														]

													}]
										},{
											layout : 'column',
											border : false,
											items : [{
														columnWidth : .3333,
														layout : 'form',
														border : false,
														labelWidth : 80, // 标签宽度
														items : [{
															labelAlign : 'top',
															xtype : 'textarea',
															id:'exceptionDescribe',
															name : 'exceptionDescribe',
															maxLength:2000,
															allowBlank : false,
															fieldLabel : '异常描述<span style="color:red">*</span>',
															height : 50,
															anchor : '100%',
															enableKeyEvents:true,
															listeners : {
																keyup:function(numberfield, e){
														                if(e.getKey() == 13 ){
																			Ext.getCmp('suggestion').focus();
														                 }
														 		},
																'render':function(){
																	var combon=Ext.getCmp('exceptionDescribe');
												                    if(formId!=''){
													                    creatPeopleFun(combon);												                   
																	}else{
																		combon.enable();
																	}														                   
												 				 }
															}
														}]

													},{
														columnWidth : .3333,
														layout : 'form',
														border : false,
														labelWidth : 80, // 标签宽度
														items : [{
															labelAlign : 'top',
															xtype : 'textarea',
															maxLength:2000,
															id:'suggestion',
															allowBlank : false,
															name : 'suggestion',
															fieldLabel : '异常处理意见<span style="color:red">*</span>',
															height : 50,
															width:'100%',
															enableKeyEvents:true,
															listeners : {
																keyup:function(numberfield, e){
														                if(e.getKey() == 13 ){
																			Ext.getCmp('savebtn').focus();
														                 }
														 		},
																'render':function(){
																	var combon=Ext.getCmp('suggestion');
												                    if(formId!=''){
													                    creatPeopleFun(combon);												                   
																	}else{
																		combon.enable();
																	}														                   
												 				 }
															}
														}]

													},{
														columnWidth : .3333,
														layout : 'form',
														border : false,
														labelWidth : 80, // 标签宽度
														items : [{
															labelAlign : 'top',
															xtype : 'textarea',
															maxLength:2000,
															name : 'finalResult',
															id:'finalResult',
															fieldLabel : '异常最终处理<span style="color:red">*</span>',
															height : 50,
															width:'100%',
															listeners : {
																'render':function(){
																	var object=Ext.getCmp('finalResult');
												                    if(formId!=''){
													                   if(createMan=="创建人"){
																		   	if(status=='0'){
																		   		object.disable();
																			}else if(status=='1'){
																				object.disable();
																			}else if(status=='2'){
																				object.enable();
																			}else{
																				object.disable();
																			}
																		}else{
																			object.disable();
																		}					
																	}else{
																		object.disable();
																	}	
												 				 }
															}
														}]
										
												}]
										}]},
										{xtype:'fieldset',
										title:"客服部门填写信息",
										id:'setdev2',
									//	layout:'column',
										//height:93,
									//	style:'margin:1px;',
									//	layoutConfig: {columns:10},
										//bodyStyle:'padding:5px 0px 0px 0px',
										//defaults:{width : 1100,border:false,layout:'form',frame:false,labelAlign:'right',labelWidth:70},
										items:[{
											layout : 'column',
											border : false,
											items : [{
														columnWidth : .3333,
														layout : 'form',
														border : false,
														labelWidth : 80, // 标签宽度
														items : [{
															fieldLabel : '异常处理人<span style="color:red">*</span>', // 标签
															id : 'dealName',
															xtype : 'textfield',
															maxLength:20,
															name : 'dealName', // name:后台根据此name属性取值
															anchor : '100%',
															listeners : {
																'render':function(c){
																	var object=Ext.getCmp('dealName');
																	if(formId!=''){
													                    if(doMan=="责任人"){
																			 if(status=='0'){
																					object.disable();
																				}else if(status=='1'){
																					c.setValue(userName);
													
																					object.enable();
																				}else if(status=='2'){
															
																					c.setValue(userName);
																					object.enable();
																				}else{
																					object.disable();
																				}
																			}else{
																				object.disable();
																			}			
																		}else{
																			object.disable();
																		}			
												 				 }
															}
														}]

													},{
														columnWidth : .05,
														layout : 'form',
														border : false,
														labelWidth : 80, // 标签宽度
														items : []

													},{
														columnWidth : .3,
														layout : 'form',
														border : false,
														labelWidth : 80, // 标签宽度
														items : [{
											    			xtype:'label',
											    			id:'showMsg1'
											    		}]

													},{
														columnWidth : .3,
														layout : 'form',
														border : false,
														labelWidth : 80, // 标签宽度
														items : [{
											    			xtype:'label',
											    			id:'showMsg2'
											    		}]

													}]
										},{
											layout : 'column',
											border : false,
											items : [{
														columnWidth : .3333,
														layout : 'form',
														border : false,
														labelWidth : 80, // 标签宽度
														items : [{
															labelAlign : 'top',
															xtype : 'textarea',
															id:'dealReasult',
															maxLength:2000,
															name : 'dealReasult',
															fieldLabel : '处理结果<span style="color:red">*</span>',
															height : 90,
															width:'100%',
															listeners : {
																'render':function(){
																	var object=Ext.getCmp('dealReasult');
												                    if(formId!=''){
													                    creatDealFun(object);												                   
																	}else{
																		object.disable();
																	}							                   
												 				 }
															}
														}]

													},{
														columnWidth : .05,
														layout : 'form',
														border : false,
														labelWidth : 80, // 标签宽度
														items : []

													},{
														columnWidth : .5,
														layout : 'form',
														border : false,
														labelWidth : 160, // 标签宽度
														items : [{
										                           	xtype:'radiogroup',
										                           	anchor : '100%',
										                           	id:'isCp',
										                           	fieldLabel:'是否短信自动通知代理客商<span style="color:red">*</span>',
										                           	listeners : {
																		'render':function(){
																			var object=Ext.getCmp('isCp');
														                    if(formId!=''){
															                    creatDealFun(object);												                   
																			}else{
																				object.disable();
																			}								                   
														 				 }
																	},
										                           	items: [{
													                    inputValue:'1',
													                    boxLabel: '是',
													                    name:'isCp'
													                }, {
													                    inputValue:'0',
													                    checked:true,
													                    name:'isCp',
													                    boxLabel: '否'
													                    
													                }]
										                           },{
										                           	xtype:'radiogroup',
										                           	anchor : '100%',
										                           	id:'isCus',
										                           	listeners : {
																		'render':function(){
																			var object=Ext.getCmp('isCus');
														                    if(formId!=''){
															                    creatDealFun(object);												                   
																			}else{
																				object.disable();
																			}									                   
														 				 }
																	},
										                           	fieldLabel:'是否短信自动通知收货人<span style="color:red">*</span>',
										                           	items: [{
													                    inputValue:'1',
													                    boxLabel: '是',
													                    name:'isCus'
													                }, {
													                    checked:true,
													                    inputValue:'0',
													                    name:'isCus',
													                    boxLabel: '否'
													                }]
										                           },{
										                           	xtype:'radiogroup',
										                           	anchor : '100%',
										                           	id:'isWeb',
										                           	listeners : {
																		'render':function(){
																			var object=Ext.getCmp('isWeb');
														                    if(formId!=''){
															                    creatDealFun(object);												                   
																			}else{
																				object.disable();
																			}									                   
														 				 }
																	},
										                           	fieldLabel:'是否网营自动显示异常<span style="color:red">*</span>',
										                           	items: [{
													                    inputValue:'1',
													                    boxLabel: '是',
													                    name:'isWeb'
													                }, {
													                    checked:true,
													                    inputValue:'0',
													                    name:'isWeb',
													                    boxLabel: '否'
													                }]
										                           }]

													}]
										}]},{xtype:'fieldset',
										title:"品管审核信息",
										id:'setdev3',
									//	disabled:true,
									//	layout:'column',
										//height:93,
									//	style:'margin:1px;',
									//	layoutConfig: {columns:10},
									//	bodyStyle:'padding:5px 0px 0px 0px',
									//	defaults:{width : 800,border:false,layout:'form',frame:false,labelAlign:'right',labelWidth:70},
										items:[{
											layout : 'column',
											border : false,
											items : [{
														columnWidth : .25,
														layout : 'form',
														border : false,
														labelWidth : 80, // 标签宽度
														items : [{
															xtype : 'textfield',
															fieldLabel : '最终责任人',
															name : 'finalDuty',
															id:'finalDuty',
															maxLength:20,
															anchor : '100%',
															enableKeyEvents:true,
															listeners : {
																keyup:function(numberfield, e){
														                if(e.getKey() == 13 ){
																			Ext.getCmp('finalPiece').focus();
														                 }
														 		},
																'render':function(){
																	var object=Ext.getCmp('finalDuty');
												                    creatQmFun(object);					                   
												 				 }
															}
														},{
															fieldLabel : '异常损失金额',
															name : 'exceptionMoney',
															id:'exceptionMoney',
															decimalPrecision:2,
															allowNegative:false,
															maxLength:10,
															xtype : 'numberfield', // 设置为数字输入框类型
															anchor : '100%',
															enableKeyEvents:true,
															listeners : {
																keyup:function(numberfield, e){
														              
														 		},
																'render':function(){
																	var object=Ext.getCmp('exceptionMoney');
												                    creatQmFun(object);					                   
												 				 }
															}
															}]

													},{
														columnWidth : .25,
														layout : 'form',
														border : false,
														labelWidth : 100, // 标签宽度
														items : [{
															fieldLabel : '最终责任件数<span style="color:red">*</span>',
															name : 'finalPiece',
															id:'finalPiece',
															allowBlank : false,
															maxLength:10,
															xtype : 'numberfield', // 设置为数字输入框类型
															anchor : '100%',
															enableKeyEvents:true,
															listeners : {
																keyup:function(numberfield, e){
														                if(e.getKey() == 13 ){
																			Ext.getCmp('qmSuggestion').focus();
														                 }
														 		},
																'render':function(){
																	var object=Ext.getCmp('finalPiece');
												                    creatQmFun(object);					                   
												 				 }
															}
															}]

													},{
														columnWidth : .25,
														layout : 'form',
														border : false,
														labelWidth : 80, // 标签宽度
														items : [{
															fieldLabel : '品管处理人<span style="color:red">*</span>',
															name : 'qm',
															maxLength:20,
															id:'qm',
															readOnly:true,
															xtype : 'textfield', // 设置为数字输入框类型
															anchor : '100%',
															listeners : {
																'render':function(){
																	var object=Ext.getCmp('qm');
												                    if(qmMan=="品管"){
																       	if(status==0){
																       		object.disable();
																       	}else{
																	       	object.enable();
																       		object.setValue(userName);
																    	}
																   	}else{
																   		object.disable();
																   	}				                   
												 				 }
															}
															}]
										
												},{
														columnWidth : .25,
														layout : 'form',
														border : false,
														labelWidth : 80, // 标签宽度
														items : [
														   {xtype : 'datefield',
														    fieldLabel : '处理时间<span style="color:red">*</span>', 
											    			id : 'qmTime',
											    			name:'qmTime',
												    		format : 'Y-m-d h:m:s',
												    		emptyText : "选择品管处理时间",
												    		anchor : '100%',
												    		listeners : {
																'render':function(){
																	var object=Ext.getCmp('qmTime');
												                    creatQmFun(object);					                   
												 				 }
															}
														}]
										
												}]
										},{
											layout : 'column',
											border : false,
											items : [{
														columnWidth : .7,
														layout : 'form',
														border : false,
														labelWidth : 80, // 标签宽度
														items : [{
															labelAlign : 'top',
															xtype : 'textarea',
															id:'qmSuggestion',
															maxLength:2000,
															name : 'qmSuggestion',
															fieldLabel : '品管异常处理意见<span style="color:red">*</span>',
															height : 65,
															width:'95%',
															listeners : {
																'render':function(){
																	var object=Ext.getCmp('qmSuggestion');
												                    creatQmFun(object);					                   
												 				 }
															}
														}]

													}]
										},{
											layout : 'column',
											border : false,
											items : [{
														columnWidth : .3,
														layout : 'form',
														border : false,
														labelWidth : 80, // 标签宽度
														items : [{
										                           	xtype:'radiogroup',
										                           	anchor : '85%',
										                           	id:'submitQualified',
										                           	fieldLabel:'提交情况<span style="color:red">*</span>',
										                           	listeners : {
																		'render':function(){
																			var object=Ext.getCmp('submitQualified');
														                    creatQmFun(object);					                   
														 				 }
																	},
										                           	items: [{
													                    checked:true,
													                    inputValue:'1',
													                    boxLabel: '合格',
													                    name:'submitQualified'
													                }, {
													                    inputValue:'0',
													                    name:'submitQualified',
													                    boxLabel: '不合格'
													                }]
										                           }]

													},{
														columnWidth : .3,
														layout : 'form',
														border : false,
														labelWidth : 80, // 标签宽度
														items : [{
										                           	xtype:'radiogroup',
										                           	anchor : '85%',
										                            id:'dealQualified',
										                           	fieldLabel:'处理情况<span style="color:red">*</span>',
										                           	listeners : {
																		'render':function(){
																			var object=Ext.getCmp('dealQualified');
														                    creatQmFun(object);					                   
														 				 }
																	},
										                           	items: [{
													                    checked:true,
													                    inputValue:'1',
													                    boxLabel: '合格',
													                    name:'dealQualified'
													                }, {
													                    inputValue:'0',
													                    name:'dealQualified',
													                    boxLabel: '不合格'
													                }]
										                           }]

													},{
														columnWidth : .3,
														layout : 'form',
														border : false,
														labelWidth : 80, // 标签宽度
														items : [{
										                           	xtype:'radiogroup',
										                           	anchor : '85%',
										                           	id:'reparQualified',
										                           	fieldLabel:'修复情况<span style="color:red">*</span>',
										                           	listeners : {
																		'render':function(){
																			var object=Ext.getCmp('reparQualified');
														                    creatQmFun(object);					                   
														 				 }
																	},
										                           	items: [{
													                    checked:true,
													                    inputValue:'1',
													                    boxLabel: '合格',
													                    name:'reparQualified'
													                }, {
													                    inputValue:'0',
													                    name:'reparQualified',
													                    boxLabel: '不合格'
													                }]
										                           }]

													}]
										}]}]
										
										
										});
		     resetException();
	
			var panel = new Ext.Panel({
						layout : 'border',
						id:'mpanel',
				    //	autoWidth: true,
					    height: 720,
					    width : Ext.lib.Dom.getViewWidth()-18,
					    renderTo: Ext.getBody(),
					    tbar:onebar,
						items : [form]
						
			});
			function resetException(){
				serviceDepartStore.load();
			 	if(formDno!=""){
					form.load({
						url : sysPath+ "/exception/oprExceptionAction!list.action",
						params:{filter_EQL_id:formId,limit : pageSize},
						success : function(response) { // 回调函数有1个参数
							//Ext.getCmp('piece').setValue(Ext.decode(response.responseText).result[0].piece);
							//alert(Ext.decode(response.responseText).result[0].isCp);
							//if(Ext.decode(response.responseText).result[0].isCp==1){
							//Ext.getCmp("isCp").getEl().dom.check=true;
							v=Ext.getCmp('dealReasult').getValue();
							v1=Ext.getCmp('exceptionDescribe').getValue();
							v2=Ext.getCmp('suggestion').getValue();
							v3=Ext.getCmp('finalResult').getValue();
							Ext.getCmp("isCp").reset();
					     	Ext.getCmp("isCus").reset();
					     	Ext.getCmp("isWeb").reset();
					    	Ext.getCmp("submitQualified").reset();
					     	Ext.getCmp("dealQualified").reset();
					     	Ext.getCmp("reparQualified").reset();
						
							var object=Ext.getCmp('dealName');
							if(formId!=''){
		                   		if(doMan=="责任人"){
								    if(status=='0'){
										object.disable();
									}else if(status=='1'){
										object.setValue(userName);
										object.enable();
									}else if(status=='2'){
										object.setValue(userName);
										object.enable();
									}else{
										object.disable();
									}
								}else{
									object.disable();
								}			
							}else{
								object.disable();
							}
							var qm = Ext.getCmp('qm');
							if(qm.disabled==false&&qm.getValue()==''){
								qm.setValue(userName);
							}
							var qmTime = Ext.getCmp('qmTime');
							if(qmTime.disabled==false&&qmTime.getValue()==''){
								qmTime.setValue(new Date());
							}
						}
					});
				}else{
				      	form.getForm().reset();
				}
			}

			function saveException(){
				
				var photo1 =Ext.getCmp('exceptionAds').validate();
				var photo2 =Ext.getCmp('exptionAdds').validate();
				if(!photo1||!photo2){
					Ext.Msg.alert(alertTitle,"导入图片格式不正确，请重新导入");			
				}
			 	
			 	var dno =Ext.getCmp('dno').validate();
			 	var nodeOrderCombo2 =Ext.getCmp('nodeOrderCombo2').validate();
			 	var exceptionTypeCombo2 =Ext.getCmp('exceptionTypeCombo2').validate();
			 	var exceptionPiece =Ext.getCmp('exceptionPiece').validate();
			 	var exceptionName =Ext.getCmp('exceptionName').validate();
			
			 	//var exceptionTime =Ext.getCmp('exceptionTime').validate();
			 	
			 	var exceptionDescribe =Ext.getCmp('exceptionDescribe').validate();
			 	var suggestion =Ext.getCmp('suggestion').validate();
			
				Ext.getCmp('showMsg').getEl().update('<span style="color:red"></span>');
			 	if(dno&&nodeOrderCombo2&&exceptionTypeCombo2&&exceptionPiece&&exceptionName&&exceptionDescribe&&suggestion){
						if(v1!=''){
							if(v1!=Ext.getCmp('exceptionDescribe').getValue()){
								v1+='/'+Ext.getCmp('exceptionDescribe').getValue();
							}
						}else{
							v1=Ext.getCmp('exceptionDescribe').getValue();
						}
						
						if(v2!=''){
							if(v2!=Ext.getCmp('suggestion').getValue()){
								v2+='/'+Ext.getCmp('suggestion').getValue();
							}
						}else{
							v2=Ext.getCmp('suggestion').getValue();
						}
						
					//	alert(Ext.getCmp('serviceDepartId').getRawValue());
						form.getForm().submit({
							url:sysPath+"/exception/oprExceptionAction!save.action",
							method : 'post',
							params:{
								privilege:privilege,
								status:1,
							//	suggestion:v2,
							//	exceptionDescribe:v1,
							//	dutyDepartName:Ext.getCmp('serviceDepartId').getRawValue(),
								dutyDepartId:Ext.getCmp('serviceDepartId').getValue()
							},
							waitMsg : '正在保存数据...',
							success : function(form, action) {
								Ext.Msg.alert(alertTitle,"数据保存成功，请重新打开页面",function(){
									Ext.getCmp("dobtn").disable();
									Ext.getCmp("savebtn").disable();
									Ext.getCmp("clearbtn").disable();
									Ext.getCmp("finalbtn").disable();
									Ext.getCmp("qmbtn").disable();
								});
								Ext.getCmp('showMsg').getEl().update('<span style="color:red">保存成功，为保持数据的准确性，请重新打开页面。</span>');
							},
							failure : function(form, action) {
										Ext.Msg.alert(alertTitle,"数据保存失败", function(){});
							}
						});
					}else{
						Ext.getCmp('showMsg').getEl().update('<span style="color:red">填写的货物异常信息不符合要求，请改正后再反馈。</span>');
					}
			 	}
					function finalDoException(){
						if(status==2){
							if(Ext.getCmp('finalResult').getValue()==''){
								Ext.Msg.alert(alertTitle,"异常最终处理不允许为空",function() {
										Ext.getCmp('finalResult').markInvalid("异常最终处理不允许为空");
										Ext.getCmp('finalResult').focus();									
																	});
								return false;
							}
							if(v3!=''){
								if(v3!=Ext.getCmp('finalResult').getValue()){
									v3+='/'+Ext.getCmp('finalResult').getValue();
								}
							}else{
								v3=Ext.getCmp('finalResult').getValue();
							}
							form1.getForm().doAction('submit', {
								url:sysPath+"/exception/oprExceptionAction!save.action",
								method : 'post',
								params : {
									status:3,
									ts:Ext.getCmp('ts').getValue(),
									finalResult:v3,
									id:Ext.getCmp('eId').getValue(),
			   						privilege:privilege
			   					},
								waitMsg : '正在保存数据...',
								success : function(form1, action) {
										Ext.Msg.alert(alertTitle,"数据保存成功，请重新打开页面",function(){
											Ext.getCmp("dobtn").disable();
											Ext.getCmp("savebtn").disable();
											Ext.getCmp("clearbtn").disable();
											Ext.getCmp("finalbtn").disable();
											Ext.getCmp("qmbtn").disable();
										});
										Ext.getCmp('showMsg').getEl().update('<span style="color:red">保存成功，为保持数据的准确性，请重新打开页面。</span>');
								},
								failure : function(form1, action) {
										Ext.Msg.alert(alertTitle,"数据保存失败");
								}
							});
						
						}
			}
			
			
			function qmDoException(){
				if(Ext.getCmp('qm').getValue()==''){
					Ext.Msg.alert(alertTitle,"品管姓名不允许为空",function() {
							Ext.getCmp('qm').markInvalid("品管姓名姓名不允许为空");
							Ext.getCmp('qm').focus();									
														});
					return false;
				}
				if(!Ext.getCmp('finalPiece').isValid()){
					Ext.Msg.alert(alertTitle,"最终责任件数不允许为空",function() {
							Ext.getCmp('finalPiece').markInvalid("最终责任件数不允许为空");
							Ext.getCmp('finalPiece').focus();								
														});
					return false;
				}
				/*if(Ext.getCmp('finalDuty').getValue()==''){
					Ext.Msg.alert(alertTitle,"最终责任人不允许为空",function() {
							Ext.getCmp('finalDuty').markInvalid("最终责任人人姓名不允许为空");
							Ext.getCmp('finalDuty').focus();									
														});
					return false;
				}*/
				if(Ext.getCmp('qmTime').getValue()==''){
					Ext.Msg.alert(alertTitle,"处理时间不允许为空",function() {
							Ext.getCmp('qmTime').markInvalid("处理人时间不允许为空");
							Ext.getCmp('qmTime').focus();								
														});
					return false;
				}
				if(Ext.getCmp('qmSuggestion').getValue()==''){
					Ext.Msg.alert(alertTitle,"异常处理意见不允许为空",function() {
							Ext.getCmp('qmSuggestion').markInvalid("异常处理意见不允许为空");
							Ext.getCmp('qmSuggestion').focus();								
														});
					return false;
				}
				form1.getForm().doAction('submit', {
					url:sysPath+"/exception/oprExceptionAction!save.action",
					method : 'post',
					params : {
						finalDuty:Ext.getCmp('finalDuty').getValue(),
						finalPiece:Ext.getCmp('finalPiece').getValue(),
						qm:Ext.getCmp('qm').getValue(),
						ts:Ext.getCmp('ts').getValue(),
						id:Ext.getCmp('eId').getValue(),
						qmTime:Ext.getCmp('qmTime').getValue(),
						qmSuggestion:Ext.getCmp('qmSuggestion').getValue(),
						submitQualified:form.getForm().getValues()["submitQualified"],
						dealQualified:form.getForm().getValues()["dealQualified"],
						reparQualified:form.getForm().getValues()["reparQualified"],
   						privilege:privilege
   					},
					waitMsg : '正在保存数据...',
					success : function(form1, action) {
							Ext.Msg.alert(alertTitle,"数据保存成功，请重新打开页面",function(){
								Ext.getCmp("dobtn").disable();
								Ext.getCmp("savebtn").disable();
								Ext.getCmp("clearbtn").disable();
								Ext.getCmp("finalbtn").disable();
								Ext.getCmp("qmbtn").disable();
							});
							Ext.getCmp('showMsg').getEl().update('<span style="color:red">保存成功，为保持数据的准确性，请重新打开页面。</span>');
					},
					failure : function(form1, action) {
							Ext.Msg.alert(alertTitle,"数据保存失败");
					}
				});
			}
			
			function doException(){
				if(Ext.getCmp('dealName').getValue()==''){
					Ext.Msg.alert(alertTitle,"处理人姓名不允许为空",function() {
							Ext.getCmp('dealName').markInvalid("处理人姓名不允许为空");
							Ext.getCmp('dealName').focus();									
														});
					return false;
				}
				if(Ext.getCmp('dealReasult').getValue()==''){
					Ext.Msg.alert(alertTitle,"处理结果不允许为空",function() {
							Ext.getCmp('dealReasult').markInvalid("处理人姓名不允许为空");
							Ext.getCmp('dealReasult').focus();								
														});
					return false;
				}
				 
				form1.getForm().doAction('submit', {
					url:sysPath+"/exception/oprExceptionAction!save.action",
					method : 'post',
					params : {
						status:2,
						ts:Ext.getCmp('ts').getValue(),
						id:Ext.getCmp('eId').getValue(),
						dealName:Ext.getCmp('dealName').getValue(),
						dealReasult:Ext.getCmp('dealReasult').getValue(),
						isCp:form.getForm().getValues()["isCp"],
						isCus:form.getForm().getValues()["isCus"],
						isWeb:form.getForm().getValues()["isWeb"],
   						privilege:privilege
   					},
					waitMsg : '正在保存数据...',
					success : function(form1, action) {
							Ext.Msg.alert(alertTitle,"数据保存成功，请重新打开页面",function(){
								Ext.getCmp("dobtn").disable();
								Ext.getCmp("savebtn").disable();
								Ext.getCmp("clearbtn").disable();
								Ext.getCmp("finalbtn").disable();
								Ext.getCmp("qmbtn").disable();
							});
							Ext.getCmp('showMsg').getEl().update('<span style="color:red">保存成功，为保持数据的准确性，请重新打开页面。</span>');
					},
					failure : function(form1, action) {
							Ext.Msg.alert(alertTitle,"数据保存失败");
							Ext.getCmp('showMsg').getEl().update('<span style="color:red">数据保存失败。</span>');
					}
				});
			}
 }
});
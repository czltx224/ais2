//货物实配JS
	var comboxPage=comboSize;
	var privilege=74;
	var loadingbrigadeUrl='sys/loadingbrigadeAction!list.action';
	var dictionaryUrl='sys/dictionaryAction!ralaList.action';
	var pageSize=200;
	var fields=[{name:"id",mapping:'id'},
    			{name:'autostowMode',mapping:'autostowMode'},
    			{name:'toWhere',mapping:'toWhere'},
    			{name:'weight',mapping:'weight'},
    			{name:'piece',mapping:'piece'},
    			{name:'votes',mapping:'votes'},
    			{name:'createTime',mapping:'createTime'},
    			{name:'createName',mapping:'createName'},
    			{name:'updateName',mapping:'updateName'},
    			{name:'updateTime',mapping:'updateTime'},
    			{name:'ts',mapping:'ts'},
    			{name:'departId',mapping:'departId'},
    			{name:'departName',mapping:'departName'}];
	
	var detailFields=[{name:"wId",mapping:'WID'},
    			{name:'autostowMode',mapping:'AUTOSTOWMODE'},
    			{name:'dno',mapping:'DNO'},
    			{name:'toWhere',mapping:'TOWHERE'},
    			{name:'consignee',mapping:'CONSIGNEE'},
    			{name:'request',mapping:'REQUEST'},
    			{name:'weight',mapping:'WEIGHT'},
    			{name:'piece',mapping:'PIECE'},
    			{name:'addr',mapping:'ADDR'},
    			{name:'trafficMode',mapping:'TRAFFICMODE'},
    			{name:'remark',mapping:'REMARK'},
    			{name:'goWhere',mapping:'GOWHERE'},
    			{name:'realPiece',mapping:'REALPIECE'},
    			{name:'realPiece2',mapping:'REALPIECE2'},
    			{name:'receiptType',mapping:'RECEIPTTYPE'},
    			{name:'consigneeRate',mapping:'CONSIGNEERATE'},
    			{name:'consigneeFee',mapping:'CONSIGNEEFEE'},
    			{name:'town',mapping:'TOWN'},
    			{name:'cpName',mapping:'CPNAME'},
    			{name:'reachNum',mapping:'REACHNUM'},
    			{name:'recepiptNum',mapping:'REACHNUM'},
    			{name:'cpFee',mapping:'CPFEE'},
    			{name:'traFee',mapping:'TRAFEE'},
    			{name:'feeAuditStatus',mapping:'FEEAUDISTATUS'},
    			{name:'printNum',mapping:'PRINTNUM'},
    			{name:'paymentCollection',mapping:'PAYMENTCOLLECTION'},'FLIGHT_MAIN_NO'];
	 var summary = new Ext.ux.grid.GridSummary();
    			
Ext.onReady(function(){

	Ext.QuickTips.init();


    var jsonread= new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'},
                    fields);  ///jsonreadDetail
                  
    var jsonreadDetail= new Ext.data.JsonReader({
                    root:'resultMap',
                    totalProperty:'totalCount'},
                    detailFields);            
	var sm = new Ext.grid.CheckboxSelectionModel({
		width:25,
		moveEditorOnEnter:true, 
		id:'checkId',
		name:'checkId',
		listeners : {
			selectionchange:function(){
				
		
			}
		}
	});

	var sm2 = new Ext.grid.CheckboxSelectionModel({
		width:25,
		moveEditorOnEnter:true, 
		id:'checkId2',
		name:'checkId2',
		listeners : {
			selectionchange:function(){
				setTotalCount();
			}
		}
	});
	
	function setTotalCount(){
		recordGrid2.stopEditing();
		var vnetmusicRecord = recordGrid2.getSelectionModel().getSelections();
				var count=oprPrewiredDetailDataStore.getCount();
				var wpiece=0;
				var wweight=0;
				var wcost=0;
				var wdf=0;
				var wpy=0;

				for(var j=0;j<vnetmusicRecord.length;j++){;
			 		if(vnetmusicRecord[j].data.realPiece==null||vnetmusicRecord[j].data.realPiece==''){
			 			wpiece+=0;
			 		}else{
						wpiece+=parseFloat(vnetmusicRecord[j].data.realPiece);
			 		}
			 		
			 		if(vnetmusicRecord[j].data.traFee==null||vnetmusicRecord[j].data.traFee==''){
			 			wcost+=0;
			 		}else{
						wcost+=parseFloat(vnetmusicRecord[j].data.traFee);
			 		}
			 		
			 		if(vnetmusicRecord[j].data.cpFee==null||vnetmusicRecord[j].data.cpFee==''){
			 			wdf+=0;
			 		}else{
						wdf+=parseFloat(vnetmusicRecord[j].data.cpFee);
			 		}
			 		
			 		if(vnetmusicRecord[j].data.paymentCollection==null||vnetmusicRecord[j].data.paymentCollection==''){
			 			wpy+=0;
			 		}else{
						wpy+=parseFloat(vnetmusicRecord[j].data.paymentCollection);
			 		}
			 		
			 		if(vnetmusicRecord[j].data.weight==null||vnetmusicRecord[j].data.weight==''){
			 			wweight+=0;
			 		}else{
						wweight+=parseFloat(vnetmusicRecord[j].data.weight);
			 		}
			 	}
			 	Ext.getCmp('totalDno').setValue(vnetmusicRecord.length);
			 	
			 	Ext.getCmp('totalNum').setValue(wpiece);
			 	Ext.getCmp('totalWeight').setValue(wweight);
			 	
			 	Ext.getCmp('showTotalMsg').getEl().update('<span style="color:red">总成本:'+wcost+', 总代收货款:'+wpy+', 总到付:'+wdf+'</span>');
			 	
			 //	alert('<span style="color:red">总成本:'+wcost+', 总代收货款:'+wpy+', 总到付:'+wdf+'</span>');
			 	
			 	if(vnetmusicRecord.length>0){
			 		Ext.getCmp('saveOprwd').enable();
			 	}
			 	Ext.getCmp('showMsg').getEl().update('<span style="color:red"></span>');
	}
	//sm2.lock();
	//var sm2=new Ext.grid.CheckboxSelectionModel({singleSelect:true});
	
	/*
	//费用审核Store
	var moneyStore=new Ext.data.SimpleStore({
			 auteLoad:true, //此处设置为自动加载
  			  data:[['1','已审核'],['0','未审核'],['-1','全部']],
   			 fields:["feeNum","feeName"]
	});*/
	
	//分拨组
	 dispatchGroupStore= new Ext.data.Store({ 
		autoLoad:true, 
		storeId:"dispatchGroupStore",
		proxy:new Ext.data.HttpProxy({
			url:sysPath+"/"+loadingbrigadeUrl}),
		baseParams:{
             	filter_EQL_type:1,
             	filter_EQL_departId:bussDepart
        },
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
    	{name:'dispatchGroupId',mapping:'id'},
    	{name:'dispatchGroupName',mapping:'loadingName'}
    	])
	});
	
	//装卸组
	 loadingbrigadeStore= new Ext.data.Store({ 
		autoLoad:true, 
		storeId:"loadingbrigadeStore",
		proxy:new Ext.data.HttpProxy({
			url:sysPath+"/"+loadingbrigadeUrl}),
		baseParams:{
             	filter_EQL_type:0,
             	filter_EQL_departId:bussDepart
        },
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
    	{name:'loadingbrigadeId',mapping:'id'},
    	{name:'loadingbrigadeName',mapping:'loadingName'}
    	])
	});

	//预配方式
	var oprPrewiredStore = new Ext.data.Store({ 
            storeId:"dictionaryStore",                        
            proxy: new Ext.data.HttpProxy({url:sysPath+"/"+dictionaryUrl,method:'post'}),
            baseParams:{
             	privilege:16,
				filter_EQL_basDictionaryId:28
            },
            reader: new Ext.data.JsonReader({
            root: 'result', totalProperty: 'totalCount'
           }, [  {name:'typeName', mapping:'typeName',type:'string'}           
              ])    
    });
    oprPrewiredStore.load();
    
//配载方式
	var oprPrewiredStore2 = new Ext.data.Store({ 
            storeId:"dictionaryStore2",                        
            proxy: new Ext.data.HttpProxy({url:sysPath+"/"+dictionaryUrl,method:'post'}),
            baseParams:{
             	privilege:16,
				filter_EQL_basDictionaryId:28
            },
            reader: new Ext.data.JsonReader({
            root: 'result', totalProperty: 'totalCount'
           }, [  {name:'typeName', mapping:'typeName',type:'string'}           
              ])    
    });
	
	var changeStore = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({url:sysPath+"/user/userAction!list.action",method:'post'}),
            baseParams:{
             	privilege:23
            },
            reader: new Ext.data.JsonReader(
            	{
            		root: 'result', totalProperty: 'totalCount'
          		}, [{name:'name', mapping:'userName',type:'string'},        
               		 {name:'id', mapping:'id'}
              		])    
	});
	
	var oprPrewiredDataStore = new Ext.data.Store({   // 主预配表显示数据
                proxy: new Ext.data.HttpProxy({url:sysPath+"/stock/oprPrewiredAction!ralaList.action"}),
                baseParams:{limit: pageSize},
                reader:jsonread
    });
	var oprPrewiredCopyStore = new Ext.data.Store({ // 主预配表累计保存数据
              fields: fields
    }); 
	
	var oprPrewiredDetailDataStore = new Ext.data.Store({  //详细预配表显示数据
				id:"oprPrewiredDetailDataStore",
                proxy: new Ext.data.HttpProxy({url:sysPath+"/stock/oprPrewiredAction!getAllDetail.action"}),
                baseParams:{limit: pageSize},
                reader:jsonreadDetail
    }); 

	var oprPrewiredDetailCopyDataStore = new Ext.data.Store({  //详细预配表累计保存数据
              fields: detailFields
    }); 
    
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
    
	var onebar = new Ext.Toolbar({
					id:'onebar',
					    items :  ['&nbsp;&nbsp;',{
										text : '<B>预配单作废</B>',
										id : 'deleSubmit',
										disabled : false,
										tooltip : '预配单作废',
										iconCls:'userDelete',
										handler : deleOprw
									},'-','&nbsp;&nbsp;',
									{text:'<b>预配清单打印</b>',iconCls:'printBtn',
										handler:function() {
											var records = recordGrid.getSelectionModel().getSelections();
											
											if(records.length<1){
												Ext.Msg.alert(alertTitle,"请选择要打印的预配单据！");
												return;
											}else if (records.length>1){
												Ext.Msg.alert(alertTitle,"一次只允许打印一个预配单据！");
												return;
											}
											
							            	parent.print('2',{print_prewiredId:records[0].data.id});
							        } },'-','&nbsp;&nbsp;',
									{
										text : '<B>实&nbsp;&nbsp;配</B>',
										id : 'saveOprwd',
										disabled : true,
										tooltip : '实配',
										iconCls:'userAdd',
										handler : sumitStore
									},'-','&nbsp;&nbsp;',{
							    			xtype:'label',
							    			id:'showMsg',
							    			width:380
							    		}]
	
	
	});

	// 查询工具条
 	var twobar = new Ext.Toolbar({
 				id:'twobar',
		    	items : ['&nbsp;','<B>查询条件</B>','&nbsp;','-','&nbsp;','预配方式:',
		    						   {xtype : 'combo',
										id:'dic',
										hiddenId : 'dictionaryName',
						    			hiddenName : 'dictionaryName',
										triggerAction : 'all',
										mode:'local',
										store : oprPrewiredStore,
										width:85,
										allowBlank : false,
										emptyText : "请选择预配方式",
										forceSelection : true,
										editable : true,
										displayField : 'typeName',//显示值，与fields对应
										valueField : 'typeName',//value值，与fields对应
										name : 'typeName',
										anchor : '100%',
										enableKeyEvents:true,
							            listeners : {
							            	render:function(combo){
							            		combo.setValue('市内送货');
							            		Ext.getCmp('oprPre').setValue(combo.getValue());
							            	},
							 				keyup:function(textField, e){
							                     if(e.getKey() == 13){
							                     	searchLog();
							                     }
							 				},
							 				select:function(c){
							 					var combo = Ext.getCmp('oprPre');
							 					combo.setValue(c.getValue());
							 					if(combo.getValue()=='部门交接'){
												 	changeStore.proxy=new Ext.data.HttpProxy({
												 		url:sysPath+"/sys/departAction!findDepart.action",
												 		method:'post'
												 	});
												 	changeStore.baseParams={
										               privilege:53,
										               filter_EQL_isBussinessDepa:1
										            }
										            changeStore.reader=new Ext.data.JsonReader({
											                     root: 'resultMap', totalProperty:'totalCount'
											                },[ {name:'name', mapping:'DEPARTNAME'},    
											                     {name:'id', mapping: 'DEPARTID'}
											            ])
											        changeStore.load();
											        Ext.getCmp('dicTo1').setValue('');
											        Ext.getCmp('dicTo1').show();
											        Ext.getCmp('dicTo1').enable();
											        
											        Ext.getCmp('dicTo2').setValue('');
											       	Ext.getCmp('dicTo2').hide();
											       	Ext.getCmp('dicTo2').disable();
											       	
											       	Ext.getCmp('dicTo3').setValue('');
											       	Ext.getCmp('dicTo3').hide();
											       	Ext.getCmp('dicTo3').disable();
											       	
											       	Ext.getCmp('dicTo4').setValue('');
											       	Ext.getCmp('dicTo4').hide();
											       	Ext.getCmp('dicTo4').disable();
											       
												 }else if(combo.getValue()=='中转'){
												 	changeStore.proxy=new Ext.data.HttpProxy({
												 		url:sysPath+"/sys/customerAction!list.action",
												 		method:'post'
												 	});
												 	changeStore.baseParams={
										               privilege:61,
										               filter_EQL_status:1,
										               filter_EQS_custprop:combo.getValue()
										            }
										            changeStore.reader=new Ext.data.JsonReader({
											                     root: 'result', totalProperty:'totalCount'
											                },[ {name:'name', mapping:'cusName'},    
											                     {name:'id', mapping: 'id'}
											            ])
											        changeStore.load();
											        
											        Ext.getCmp('dicTo2').setValue('');
											        Ext.getCmp('dicTo2').show();
											        Ext.getCmp('dicTo2').enable();
											        
											        Ext.getCmp('dicTo1').setValue('');
											       	Ext.getCmp('dicTo1').hide();
											       	Ext.getCmp('dicTo1').disable();
											       	
											       	Ext.getCmp('dicTo3').setValue('');
											       	Ext.getCmp('dicTo3').hide();
											       	Ext.getCmp('dicTo3').disable();
											       	
											       	Ext.getCmp('dicTo4').setValue('');
											       	Ext.getCmp('dicTo4').hide();
											       	Ext.getCmp('dicTo4').disable();
												 }else if(combo.getValue()=='外发'){
												 	changeStore.proxy=new Ext.data.HttpProxy({
												 		url:sysPath+"/sys/customerAction!list.action",
												 		method:'post'
												 	});
												 	changeStore.baseParams={
										               privilege:61,
										               filter_EQL_status:1,
										               filter_EQS_custprop:combo.getValue()
										            }
										            changeStore.reader=new Ext.data.JsonReader({
											                     root: 'result', totalProperty:'totalCount'
											                },[ {name:'name', mapping:'cusName'},    
											                     {name:'id', mapping: 'id'}
											            ])
											        changeStore.load();
											        
											        Ext.getCmp('dicTo3').setValue('');
											        Ext.getCmp('dicTo3').show();
											        Ext.getCmp('dicTo3').enable();
											        
											        Ext.getCmp('dicTo2').setValue('');
											       	Ext.getCmp('dicTo2').hide();
											       	Ext.getCmp('dicTo2').disable();
											       	
											       	Ext.getCmp('dicTo1').setValue('');
											       	Ext.getCmp('dicTo1').hide();
											       	Ext.getCmp('dicTo1').disable();
											       	
											       	Ext.getCmp('dicTo4').setValue('');
											       	Ext.getCmp('dicTo4').hide();
											       	Ext.getCmp('dicTo4').disable();
												 }else{
												 	changeStore.proxy=new Ext.data.HttpProxy({
												 		url:sysPath+"/user/userAction!list.action",
												 		method:'post'
												 	})
												 	changeStore.baseParams={
										             	privilege:23
										            }
										            changeStore.reader=new Ext.data.JsonReader({
										            		root: 'result', totalProperty: 'totalCount'
										          		}, [{name:'name', mapping:'userName',type:'string'},        
										               		 {name:'id', mapping:'id'}
										             ])
										            changeStore.load();
												 	Ext.getCmp('dicTo4').setValue('');
												 	Ext.getCmp('dicTo4').show();
											        Ext.getCmp('dicTo4').enable();
											        
											        Ext.getCmp('dicTo2').setValue('');
											       	Ext.getCmp('dicTo2').hide();
											       	Ext.getCmp('dicTo2').disable();
											       	
											       	Ext.getCmp('dicTo3').setValue('');
											       	Ext.getCmp('dicTo3').hide();
											       	Ext.getCmp('dicTo3').disable();
											       	
											       	Ext.getCmp('dicTo1').setValue('');
											       	Ext.getCmp('dicTo1').hide();
											       	Ext.getCmp('dicTo1').disable();
												 }
											
											}
							 			}
								    },'&nbsp;','预配单号:',
						 				{xtype : 'textfield',
			        	                fieldLabel: '预配单号',
			        	                labelAlign : 'right',
			        	                id:'oprPrewiredId',
				        			    name: 'oprPrewiredIdarea',
				        			    maxLength : 10,
						                anchor : '95%',
						                width:85,
						                regex:/^[1-9]\d*$/,
						                regexText:'输入内容格式不符合要求',
						                allowNegative:false,
						                enableKeyEvents:true,
							            listeners : {
							 				keyup:function(textField, e){
							                     if(e.getKey() == 13){
							                     	searchLog();
							                     }
							 				}
							 			}
						              },'&nbsp;','预配去向:',
    	   				 			　　{xtype : 'textfield',
			        	                fieldLabel: '预配去向',
			        	                id:'oprPrewiredGoWhere',
				        			    name: 'oprPrewiredGoWhereGoWhere',
				        			    maxLength : 10,
						                anchor : '95%',
						                width:73,
						                enableKeyEvents:true,
						                listeners : {
							 				keyup:function(textField, e){
							                     if(e.getKey() == 13){
							                     	searchLog();
							                     }
							 				}
							 			}
						              }/*,'-','&nbsp;&nbsp;','<B>费用审核:</B>',
								       {
										xtype : 'combo',
										id:'comboType',
										hiddenId : 'FeeName',
							    		hiddenName : 'feeNum',
										triggerAction : 'all',
										store : moneyStore,
										width:100,
										allowBlank : true,
										emptyText : "请选择费用审核类型",
										forceSelection : true,
										fieldLabel:'费用审核类型',
										editable : true,
										mode : "local",//获取本地的值
										displayField : 'feeName',//显示值，与fields对应
										valueField : 'feeNum',//value值，与fields对应
										anchor : '95%',
										listeners: {   
											 afterRender: function(combo) {   
												 var firstValue = combo.store.getAt(2).get('feeName'); 
												 if(combo.getValue()==null || combo.getValue()=='') 
													 combo.setValue(firstValue);
											 }
										
										}
								     }*/,'&nbsp;',
								       {xtype : 'checkbox',
			        	                id:'checkbox',
			        	                name:'checkbox',
			        	                boxLabel:'累计查询',
						                anchor : '95%'
						               },'&nbsp;&nbsp;','-','&nbsp;&nbsp;',{
    				     text : '<B>查询</B>',
    				     id : 'btn',
    				     iconCls : 'btnSearch',
    					 handler : searchLog
    				}]
		});

		//实配提交数据工具栏
      	var threebar = new Ext.Toolbar({
		    	items : ['&nbsp;','<B>实配数据</B>','&nbsp;','-','&nbsp;','实配方式<span style="color:red">*</span>:',
		    						   {xtype : 'combo',
										id:'oprPre',
										hiddenId : 'dictionaryName',
						    			hiddenName : 'dictionaryName',
										triggerAction : 'all',
								//		mode:'local',
										store : oprPrewiredStore2,
										width:80,
										minChars : 1,
										queryParam:'filter_LIKES_typeName',
										allowBlank : true,
										emptyText : "请选择实配方式",
										forceSelection : true,
										editable : true,
										displayField : 'typeName',//显示值，与fields对应
										valueField : 'typeName',//value值，与fields对应
										name : 'typeName',
										anchor : '100%',
										listeners:{
											select:function(combo,recode,index){
											Ext.getCmp('showMsg').getEl().update('');
											
											if(combo.getValue()=='部门交接'){
											
												 	changeStore.proxy=new Ext.data.HttpProxy({
												 		url:sysPath+"/sys/departAction!ralaList.action",
												 		method:'post'
												 	});
												 	changeStore.baseParams={
										               privilege:53,
										               filter_EQL_isBussinessDepa:1
										            }
										            changeStore.reader=new Ext.data.JsonReader({
											                     root: 'result', totalProperty:'totalCount'
											                },[ {name:'name', mapping:'departName'},    
											                     {name:'id', mapping: 'departId'}
											            ])
											        changeStore.load();
											        Ext.getCmp('dicTo1').show();
											        Ext.getCmp('dicTo1').enable();
											        Ext.getCmp('dicTo1').setValue('');
											        
											       	Ext.getCmp('dicTo2').hide();
											       	Ext.getCmp('dicTo2').disable();
											       	Ext.getCmp('dicTo2').setValue('');
											       	
											       	Ext.getCmp('dicTo3').hide();
											       	Ext.getCmp('dicTo3').disable();
											       	Ext.getCmp('dicTo3').setValue('');
											       	
											       	Ext.getCmp('dicTo4').hide();
											       	Ext.getCmp('dicTo4').disable();
											       	Ext.getCmp('dicTo4').setValue('');
											       
												 }else if(combo.getValue()=='中转'){
												 
												 	changeStore.proxy=new Ext.data.HttpProxy({
												 		url:sysPath+"/sys/customerAction!ralaList.action",
												 		method:'post'
												 	});
												 	changeStore.baseParams={
										               privilege:61,
										               filter_EQS_custprop:combo.getValue()
										            }
										            changeStore.reader=new Ext.data.JsonReader({
											                     root: 'result', totalProperty:'totalCount'
											                },[ {name:'name', mapping:'cusName'},    
											                     {name:'id', mapping: 'id'}
											            ])
											        changeStore.load();
											        
											        Ext.getCmp('dicTo2').setValue('');
											        Ext.getCmp('dicTo2').show();
											        Ext.getCmp('dicTo2').enable();
											        
											        Ext.getCmp('dicTo1').setValue('');
											       	Ext.getCmp('dicTo1').hide();
											       	Ext.getCmp('dicTo1').disable();
											       	
											       	Ext.getCmp('dicTo3').setValue('');
											       	Ext.getCmp('dicTo3').hide();
											       	Ext.getCmp('dicTo3').disable();
											       	
											       	Ext.getCmp('dicTo4').setValue('');
											       	Ext.getCmp('dicTo4').hide();
											       	Ext.getCmp('dicTo4').disable();
												 }else if(combo.getValue()=='外发'){

												 	changeStore.proxy=new Ext.data.HttpProxy({
												 		url:sysPath+"/sys/customerAction!ralaList.action",
												 		method:'post'
												 	});
												 	changeStore.baseParams={
										               privilege:61,
										               filter_EQS_custprop:combo.getValue()
										            }
										            changeStore.reader=new Ext.data.JsonReader({
											                     root: 'result', totalProperty:'totalCount'
											                },[ {name:'name', mapping:'cusName'},    
											                     {name:'id', mapping: 'id'}
											            ])
											        changeStore.load();
											        
											        Ext.getCmp('dicTo3').show();
											        Ext.getCmp('dicTo3').setValue('');
											        Ext.getCmp('dicTo3').enable();
											        
											       	Ext.getCmp('dicTo2').hide();
											       	Ext.getCmp('dicTo2').setValue('');
											       	Ext.getCmp('dicTo2').disable();
											       	
											       	Ext.getCmp('dicTo1').hide();
											       	Ext.getCmp('dicTo1').setValue('');
											       	Ext.getCmp('dicTo1').disable();
											       	
											       	Ext.getCmp('dicTo4').hide();
											       	Ext.getCmp('dicTo4').setValue('');
											       	Ext.getCmp('dicTo4').disable();
												 }else{
												 	
												 	changeStore.proxy=new Ext.data.HttpProxy({
												 		url:sysPath+"/user/userAction!ralaList.action",
												 		method:'post'
												 	})
												 	changeStore.baseParams={
										             	privilege:23
										            }
										            changeStore.reader=new Ext.data.JsonReader({
										            		root: 'result', totalProperty: 'totalCount'
										          		}, [{name:'name', mapping:'userName',type:'string'},        
										               		 {name:'id', mapping:'id'}
										             ])
										            changeStore.load();
												 	
												 	Ext.getCmp('dicTo4').show();
												 	Ext.getCmp('dicTo4').setValue('');
											        Ext.getCmp('dicTo4').enable();
											        
											       	Ext.getCmp('dicTo2').hide();
											       	Ext.getCmp('dicTo2').setValue('');
											       	Ext.getCmp('dicTo2').disable();
											       	
											       	Ext.getCmp('dicTo3').hide();
											       	Ext.getCmp('dicTo3').setValue('');
											       	Ext.getCmp('dicTo3').disable();
											       	
											       	Ext.getCmp('dicTo1').hide();
											       	Ext.getCmp('dicTo1').setValue('');
											       	Ext.getCmp('dicTo1').disable();
												 }
											
											}
										
										}
								    },'&nbsp;','实配去向<span style="color:red">*</span>:',
		    						 {xtype : 'combo',
										id:'dicTo1',
										hiddenId : 'toName',
						    			hiddenName : 'toName',
										triggerAction : 'all',
										store : changeStore,
										width:80,
										hidden:true,
										disabled:true,
										minChars : 1,
										queryParam:'filter_LIKES_departName',
										listWidth:245,
										allowBlank : true,
										emptyText : "请选择实配去向",
										//forceSelection : true,
										editable : true,
										displayField : 'name',//显示值，与fields对应
										valueField : 'id',//value值，与fields对应
										name : 'name',
										anchor : '100%',
										pageSize:comboxPage,
										enableKeyEvents:true,
							            listeners : {
							 				focus:function(){
							                     if(Ext.getCmp("oprPre").getValue()==""){
							                     	Ext.getCmp('showMsg').getEl().update('<span style="color:red">请先选择实配方式，再选择实配去向。</span>');
													Ext.getCmp("oprPre").markInvalid("必须选择先选择实配方式");
													Ext.getCmp("oprPre").focus(true,true);
							                     	//Ext.MessageBox.alert(alertTitle, '必须先选择实配方式',function(){			
							                     	//	Ext.getCmp('oprPre').markInvalid("");
													//	Ext.getCmp('oprPre').focus(true,true);	
													//});
							                     }
							 				},select:function(v){
							 					if(v.getValue!=''){
							                    	Ext.Ajax.request({
							 							url:sysPath+"/user/userAction!ralaList.action",
														params:{
															privilege:23,
															filter_EQL_id:v.getValue()
														},
														success : function(response) { // 回调函数有1个参数
															if(Ext.decode(response.responseText).result.length!=0){
																Ext.getCmp('dno').setValue(Ext.decode(response.responseText).result[0].telPhone);
															}
														},
														failure : function(response) {
															
														}
													});	
												}
							 				},
							 				blur:function(combo,e){
							 					if(combo.getValue()==''){
							 						combo.setValue(Ext.get('dicTo1').dom.value);
							 						combo.setRawValue(Ext.get('dicTo1').dom.value);
							 					}
							 				}
							 			}},{
							 			xtype : 'combo',
										id:'dicTo2',
										hiddenId : 'toName',
						    			hiddenName : 'toName',
										triggerAction : 'all',
										store : changeStore,
										hidden:true,
										disabled:true,
										width:80,
										minChars : 1,
										queryParam:'filter_LIKES_cusName',
										listWidth:245,
										allowBlank : true,
										emptyText : "请选择实配去向",
										forceSelection : true,
										editable : true,
										displayField : 'name',//显示值，与fields对应
										valueField : 'id',//value值，与fields对应
										name : 'name',
										anchor : '100%',
										pageSize:comboxPage,
										enableKeyEvents:true,
							            listeners : {
							 				focus:function(){
							                     if(Ext.getCmp("oprPre").getValue()==""){
							                     	Ext.getCmp('showMsg').getEl().update('<span style="color:red">请先选择实配方式，再选择实配去向。</span>');
													Ext.getCmp("oprPre").markInvalid("必须选择先选择实配方式");
													Ext.getCmp("oprPre").focus(true,true);
							                     }
							 				}
							 			}},
							 			 {xtype : 'combo',
										id:'dicTo3',
										hiddenId : 'toName',
						    			hiddenName : 'toName',
										triggerAction : 'all',
										store : changeStore,
										width:80,
										hidden:true,
										disabled:true,
										minChars : 1,
										queryParam:'filter_LIKES_cusName',
										listWidth:245,
										allowBlank : true,
										emptyText : "请选择实配去向",
										forceSelection : true,
										editable : true,
										displayField : 'name',//显示值，与fields对应
										valueField : 'id',//value值，与fields对应
										name : 'name',
										anchor : '100%',
										pageSize:comboxPage,
										enableKeyEvents:true,
							            listeners : {
							 				focus:function(){
							                     if(Ext.getCmp("oprPre").getValue()==""){
							                     	Ext.getCmp('showMsg').getEl().update('<span style="color:red">请先选择实配方式，再选择实配去向。</span>');
													Ext.getCmp("oprPre").markInvalid("必须选择先选择实配方式");
													Ext.getCmp("oprPre").focus(true,true);
							                     }
							 				}
							 			}},
							 			 {xtype : 'combo',
										id:'dicTo4',
										hiddenId : 'toName',
						    			hiddenName : 'toName',
										triggerAction : 'all',
										store : changeStore,
										width:80,
										minChars : 1,
										queryParam:'filter_LIKES_userName',
										listWidth:245,
										allowBlank : true,
										emptyText : "请选择实配去向",
										//forceSelection : true,
										editable : true,
										displayField : 'name',//显示值，与fields对应
										valueField : 'id',//value值，与fields对应
										name : 'name',
										anchor : '100%',
										pageSize:comboxPage,
										enableKeyEvents:true,
							            listeners : {
							 				focus:function(){
							                     if(Ext.getCmp("oprPre").getValue()==""){
							                     	Ext.getCmp('showMsg').getEl().update('<span style="color:red">请先选择实配方式，再选择实配去向。</span>');
													Ext.getCmp("oprPre").markInvalid("必须选择先选择实配方式");
													Ext.getCmp("oprPre").focus(true,true);
							                     }
							 				},
							 				select:function(v){
												if(v.getValue!=''){
							                    	Ext.Ajax.request({
							 							url:sysPath+"/user/userAction!ralaList.action",
														params:{
															privilege:23,
															filter_EQL_id:v.getValue()
														},
														success : function(response) { // 回调函数有1个参数
															Ext.getCmp('dno').setValue(Ext.decode(response.responseText).result[0].telPhone);
														},
														failure : function(response) {
															
														}
													});	
												
												}
							 				},
							 				blur:function(combo,e){
							 					if(combo.getValue()==''){
							 						combo.setValue(Ext.get('dicTo4').dom.value);
							 						combo.setRawValue(Ext.get('dicTo4').dom.value);
							 					}
							 				}
							 			}
								    },'&nbsp;','手机:',
						              　　{xtype : 'textfield',
			        	                fieldLabel: '手机号码',
			        	                id:'dno',
				        			    name: 'dno',
				        			    regex:/^[1-9]\d*$/,
						                regexText:'输入内容格式不符合要求',
				        			    maxLength : 14,
						                anchor : '95%',
						                width:95,
						                enableKeyEvents:true
						            
						            },'&nbsp;','装卸组:',
								    {   xtype : "combo",
										width : 80,
										emptyText : "请选择装卸组",
										id : 'loadingbrigade',
										editable:true,
										triggerAction : 'all',
										store : loadingbrigadeStore,
										minChars : 1,
										mode : "local",// 从本地加载值
										valueField : 'loadingbrigadeId',// value值，与fields对应
										displayField : 'loadingbrigadeName',// 显示值，与fields对应
										hiddenName : 'loadingbrigadeId'
								     },'&nbsp;','分拨组:',
								      {
										xtype : "combo",
										width : 80,
										emptyText : "请选择分拨组",
										id : 'dispatchGroup',
										typeAhead : true,
										editable:true,
										triggerAction : 'all',
										store : dispatchGroupStore,
										minChars : 1,
										mode : "local",// 从本地载值
										valueField : 'dispatchGroupId',// value值，与fields对应
										displayField : 'dispatchGroupName',// 显示值，与fields对应
										name : 'dispatchGroup'
										}
									]
		});
		
		var fourbar = new Ext.Toolbar({	frame : true,
							items : ['&nbsp;','配送单号:',
							
									{xtype : 'textfield',
			        	                fieldLabel: '配送单号',
			        	                labelAlign : 'right',
			        	                id:'oprdno',
				        			    name: 'oprdnoName',
				        			    maxLength : 10,
						                anchor : '95%',
						                width:85,
						                regex:/^[1-9]\d*$/,
						                regexText:'输入内容格式不符合要求',
						                allowNegative:false,
						                enableKeyEvents:true,
										listeners : {
									 		keyup:function(textField, e){
								               if(e.getKey() == 13){
								                    addOneStore();
								               }
									 		}
							 			}
						              },'&nbsp;',
									{
										text : '<B>单票添加</B>',
										id : 'submitbtn8',
										//disabled : true,
										tooltip : '单票添加',
										iconCls:'groupAdd',
										handler : addOneStore
									},'-','&nbsp;&nbsp;','总票数:',
								    	{xtype : 'numberfield',
			        	                fieldLabel: '总票数',
			        	                id:'totalDno',
				        			    name: 'totalDno',
				        			    readOnly:true,
				        			    maxLength : 10,
						                width:35,
						                enableKeyEvents:true
						              },'&nbsp;','总件数:',
								    	{xtype : 'numberfield',
			        	                fieldLabel: '总件数',
			        	                id:'totalNum',
				        			    name: 'totalNum',
				        			    readOnly:true,
				        			    maxLength : 10,
						                width:35,
						                enableKeyEvents:true
						              },'&nbsp;','总重量:',
								    	{xtype : 'numberfield',
			        	                fieldLabel: '总载重量',
			        	                id:'totalWeight',
				        			    name: 'totalWeight',
				        			    readOnly:true,
				        			    maxLength : 10,
						                width:35,
						                enableKeyEvents:true
						              },'-','&nbsp;',{
							    			xtype:'label',
							    			id:'showTotalMsg',
							    			width:380
							    		}]
							 			
		
		});

     
     var recordGrid=new Ext.grid.EditorGridPanel({
		id:'userCenter',
		height : 200,
		width : Ext.lib.Dom.getViewWidth()*0.18,
		autoScroll : true,  //面板上的body元素
	 	viewConfig : {
				scrollOffset: 0,
				autoScroll:true
		},
		frame : true,
	//	plugins: summary,
		//enableColumnResize:false, //关闭列的自适大小功能
		autoWidth:false,
	//	clicksToEdit:1,
	//	plugins : [summary],
		loadMask : true,
		sm:sm,
		stripeRows : true,
		columns:[ sm,
			        {header: '配载类型', dataIndex: 'autostowMode',width:60},
			        {header: '预配单号', dataIndex: 'id',width:60},
        			{header: '预配去向', dataIndex: 'toWhere',width:65},
			        {header: '预配载重', dataIndex: 'weight',width:60,hidden:true},
			        {header: '预配件数', dataIndex: 'piece',width:80,hidden:true},
			        {header: '预配票数', dataIndex: 'votes',width:100,hidden:true},
			        {header: '创建人', dataIndex: 'createName',width:60,hidden:true},
			        {header: '创建时间', dataIndex: 'createTime',width:80,hidden:true}],
		store :oprPrewiredDataStore,
		listeners : {
			'rowclick' : function(f,e){

				if(Ext.getCmp('dic').getValue()=='部门交接'){
					oprPrewiredDetailDataStore.proxy = new Ext.data.HttpProxy({
							method : 'POST',
							url : sysPath+'/stock/oprPrewiredAction!getAllDetailDepart.action'
					});
				}else if(Ext.getCmp('dic').getValue()=='专车'){
					oprPrewiredDetailDataStore.proxy = new Ext.data.HttpProxy({
							method : 'POST',
							url : sysPath+'/stock/oprPrewiredAction!getAllDetailCar.action'
					});
				}else{
					oprPrewiredDetailDataStore.proxy = new Ext.data.HttpProxy({
							method : 'POST',
							url:sysPath+"/stock/oprPrewiredAction!getAllDetail.action"
					});
				}
				
				var ids="";
				var vnetmusicRecord = recordGrid.getSelectionModel().getSelections();
				if(vnetmusicRecord.length!=0){
				
						for(var i = 0; i < vnetmusicRecord.length; i++) {
							ids += vnetmusicRecord[i].data.id + ",";
						}
						
						Ext.apply(oprPrewiredDetailDataStore.baseParams={
						 	 		ids:ids
						});
						
						oprPrewiredDetailDataStore.reload({
								params : {
									start : 0,
									privilege:privilege,
									limit : pageSize
								},callback :function(){
									var count=oprPrewiredDetailDataStore.getCount();
									var wpiece=0;
									var wweight=0;
									var wpiece2=0;
									for(var j=0;j<oprPrewiredDetailDataStore.getCount();j++){
								 		wpiece+=parseFloat(oprPrewiredDetailDataStore.getAt(j).get("piece"));
										wweight+=parseFloat(oprPrewiredDetailDataStore.getAt(j).get("weight"));
										if(oprPrewiredDetailDataStore.getAt(j).get("piece2")==''||oprPrewiredDetailDataStore.getAt(j).get("realPiece2")==null){
											
											wpiece2+=parseFloat(oprPrewiredDetailDataStore.getAt(j).get("realPiece2"));
										}else{
											wpiece2+=parseFloat(oprPrewiredDetailDataStore.getAt(j).get("realPiece2"));
										}
								 	}
								 	var s ='{"weight":'+'"'+wweight+'kg","dno":'+'"'+count+'票","autostowMode":"","piece":'+'"'+wpiece+'件","realPiece2":'+'"库存'+wpiece2+'件"}';
								 	summary.toggleSummary(true);
									summary.setSumValue(Ext.decode(s));
								}
						});
				}else{
					oprPrewiredDetailDataStore.removeAll();
				}
			 }
		},
		bbar : new Ext.PagingToolbar({
					pageSize : pageSize, 
					store : oprPrewiredDataStore,
					displayInfo : true,
					displayMsg : '显示第 {0} 条到 {1} 条记录，共 {2} 条',
					emptyMsg : "没有记录信息显示"
		})
	});
     
        var recordGrid2=new Ext.grid.EditorGridPanel({
			id:'userCenter2',
			height : 10,
			width :Ext.lib.Dom.getViewWidth()*0.82-11,
			autoScroll : true,  //面板上的body元素
			viewConfig : {
				scrollOffset: 0,
				autoScroll:true
			},
			frame : true,
		//	enableColumnResize:false, //关闭列的自适大小功能
			autoWidth:false,
			clicksToEdit:1,
			tbar:fourbar,
			plugins : [summary],
			loadMask : true,
			sm:sm2,
			stripeRows : true,
			/*
			listeners : {
				'afteredit' : function(f,e){
					if(f.record.data.realPiece2<f.originalValue||f.originalValue<0){
						Ext.MessageBox.alert(alertTitle, '实配件数不能大于库存件数或者小于零',function(){
							recordGrid2.startEditing(f.row,f.column); 
						});
					}
				 }
			},*/
			columns:[ sm2,
			        {header: '配送单号', dataIndex: 'dno',width:80,sortable : true},
			        {header: '主单号', dataIndex: 'FLIGHT_MAIN_NO',width:80,sortable : true},
			        {header: '录单件数', dataIndex: 'piece',width:60,sortable : true,renderer:function(v){
			        						return (v==''||v==null)?0:v;
			        					}},
			        {header: '库存件数', dataIndex: 'realPiece2',width:60	,sortable : true,renderer:function(v){
			        						return (v==''||v==null)?0:v;
			        					}},
			        {header: '配载件数<span style="color:red"><B>*</B></span>', dataIndex: 'realPiece',
			         			editor: new Ext.form.NumberField({
               				       allowBlank: true,
               				       style: 'text-align:left',
                			       allowNegative: false,
                			       listeners:{
                   						'focus':function(f){
                        				 	f.selectText();
                        				 },
                        			 	 blur:function(f){
                        			 	 	setTotalCount();
                        			 	 }
											
                			       }
               	            	}),width:100,css : 'background: #CAE3FF;',renderer:function(v){
			        						return (v==''||v==null)?0:v;
			        					}
			         },
			         {header: '回单库存数', dataIndex: 'reachNum',sortable : true,width:80,renderer:function(v){
			        						return (v==''||v==null)?0:v;
			        					}},
			        					
			        					
			         {header: '回单出库数<span style="color:red"><B>*</B></span>', dataIndex: 'recepiptNum',width:100,
			        			editor: new Ext.form.NumberField({
               				       allowBlank: true,
               				       style: 'text-align:left',
                			       allowNegative: false,
                			       listeners:{
                   						'focus':function(f){
                        				 	f.selectText();
                        				 },
                        				 'render':function(f){
                        				 	if(f.getValue==''||f.getValue==null){
                        				 		f.setValue(1);
                        				 	}else{
                        				 		f.setValue(1);
                        				 	}
                        				 }
                			       }
               	            	}),css : 'background: #CAE3FF;',renderer:function(v){
			        						return (v==''||v==null)?0:v;
			        					}
			        },
			        {header: '收货人区域', dataIndex: 'town',width:85,sortable : true},
			        {header: '收货人信息', dataIndex: 'consignee',width:150,sortable : true,
						renderer: function(v, metadata, record, rowIndex, columnIndex, store) {
					    	return v+'/'+record.get('addr');
					    }},
					 {header: '发货代理', dataIndex: 'cpName',width:85,sortable : true},
			        {header: '个性化要求', dataIndex: 'request',width:85,sortable : true},
			        {header: '备注', dataIndex: 'remark',width:90,sortable : true},
			 		{header: '预配单号', dataIndex: 'wId',width:60,sortable : true},
			 		{header: '预配类型', dataIndex: 'autostowMode',width:60,sortable : true},
			 		{header: '预配去向', dataIndex: 'toWhere',width:60,sortable : true},
			        {header: '签单类型', dataIndex: 'receiptType',width:60,sortable : true},
			        {header: '运输方式', dataIndex: 'trafficMode',width:60,sortable : true},
			        {header: '重量', dataIndex: 'weight',width:60,sortable : true},
			   
			        {header: '成本', dataIndex: 'cpFee',width:60,sortable : true},
			        {header: '代收款', dataIndex: 'paymentCollection',width:60,sortable : true},
			        {header: '到付提送费', dataIndex: 'consigneeFee',width:90,sortable : true},
			        {header: '到付提送费率', dataIndex: 'consigneeRate',width:95,sortable : true},
			        {header: '费用是否审核', dataIndex: 'feeAuditStatus',sortable : true,width:80,
			        					renderer:function(v){
			        						return v=='0'?'未审核':'已审核';
			        					}},
			        {header: '开单打印次数',sortable : true, dataIndex: 'printNum',width:80,
			        					renderer:function(v){
			        						v=v==null?0:v;
			        						return v+'次';
			        					}},
			    	{header : '操作',dataIndex:'dno',width:60,renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){
    							//删除一条记录
    							return "<a href='#' onclick='delTotalStore("+rowIndex+");'>移除</a>";
    						}
    				}
			    ],
			store : oprPrewiredDetailDataStore,
			listeners:{
				'mousedown':function(){
					setTotalCount();
				}
			},
			bbar : new Ext.PagingToolbar({
					id:'toolBar2',
					pageSize : pageSize, 
					store : oprPrewiredDetailDataStore,
					displayInfo : true,
					displayMsg : '显示第 {0} 条到 {1} 条记录，共 {2} 条',
					emptyMsg : "没有记录信息显示"
					
			})
	});
	
	Ext.override(Ext.grid.RowSelectionModel, {
      selectRow: function(index, keepExisting, preventViewNotify) {
	        if (this.isLocked() || (index < 0 || index >= this.grid.store.getCount()) 
	        				|| (keepExisting && this.isSelected(index)) 
	        				   || (Number(this.grid.store.getAt(index).get("realPiece2")) == 0)) {
	        				   
	        //根据每行的一个标识字段来判断是否选中
	        	//Ext.MessageBox.alert(alertTitle, '预配件数为零，你不能选择些项');
	            if(this.grid.store.getAt(index).get('autostowMode')!='专车'){
	        		Ext.getCmp('showMsg').getEl().update('<span style="color:red">库存为0的货物不能做实配，无法选择其记录(专车除外)。</span>');
		            return;
	            }
	        }
	        var r = this.grid.store.getAt(index);
	        if (r && this.fireEvent('beforerowselect', this, index, keepExisting, r) !== false) {
	            if (!keepExisting || this.singleSelect) {
	                this.clearSelections();
	            }
	            this.selections.add(r);
	            this.last = this.lastActive = index;
	            if (!preventViewNotify) {
	                this.grid.getView().onRowSelect(index);
	            }
	            this.fireEvent('rowselect', this, index, r);
	            this.fireEvent('selectionchange', this);
	    	    }
	
    	}
	});
	
		function select(){
		Ext.getCmp('showMsg').getEl().update('<span style="color:red"></span>');
			var vnetmusicRecord = recordGrid.getSelectionModel().getSelections();
				var dele = Ext.getCmp('deleSubmit');
				if (vnetmusicRecord.length == 1) {
					dele.setDisabled(false);
				} else if (vnetmusicRecord.length > 1) {
					dele.setDisabled(false);
				} else {
					dele.setDisabled(true);
				}
				
				if(oprPrewiredDetailDataStore.getCount()!=0){
					Ext.getCmp('saveOprwd').enable();
				}else{
					Ext.getCmp('saveOprwd').disable();
				}
		}

		
      	var form = new Ext.form.FormPanel({
      							id:'myForm',
								labelAlign : 'left',
								frame : true,
								renderTo:Ext.getBody(),
								bodyStyle : 'padding:0px 0px 0px',
							    height : Ext.lib.Dom.getViewHeight()-2,
								width : Ext.lib.Dom.getViewWidth()-1,
								labelAlign : "right",
								tbar:onebar,
								listeners: {
					                render: function(){
					                    twobar.render(this.tbar);
					                    threebar.render(this.tbar);
					                }
					            },
						        items : [{
											layout : 'column',
											items : [{
														columnWidth : .18,
														layout : 'form',
														items : [recordGrid]

													},{
														columnWidth : .82,
														layout : 'form',
														buttonAlign:'center',
														items : [recordGrid2]
													}]
										}]
					});
     
     
	

	
		form.render();
		recordGrid.setHeight((Ext.lib.Dom.getViewHeight()-3*Ext.getCmp('twobar').getHeight())-15);
		recordGrid2.setHeight((Ext.lib.Dom.getViewHeight()-3*Ext.getCmp('twobar').getHeight())-15);
			
		function searchLog(){
			Ext.getCmp('showMsg').getEl().update('');
			if(Ext.getCmp('dic').getValue()==""){
					Ext.getCmp('showMsg').getEl().update('<span style="color:red">请先选择预配方式，再点击查询。</span>');
					Ext.getCmp("dic").markInvalid("查询时必须先选择预配方式");
					Ext.getCmp("dic").focus(true,true);
					Ext.getCmp('dic').selectText();	
					
				/*	Ext.Msg.alert(alertTitle,"必须先选择预配方式才能查询", function() {			
							Ext.getCmp('dic').markInvalid("预配方式不能为空");
							Ext.getCmp('dic').focus();	
							Ext.getCmp('dic').selectText();	
					});*/
					return;
			}
			
			
			var checkbox = Ext.get("checkbox").dom.checked;
			
			var oprPrewiredType = Ext.getCmp("dic").getValue();  //预配方式
			
			var oprPrewiredId = Ext.getCmp('oprPrewiredId').getValue()
			var regex=/^[1-9]\d*$/;
			if(oprPrewiredId!=""){
			    if(!regex.test(oprPrewiredId)){
			       	Ext.getCmp('showMsg').getEl().update('<span style="color:red">输入的预配单号格式不正确，无法查询。</span>');
			       	Ext.getCmp('oprPrewiredId').markInvalid("输入内容格式不正确");
					Ext.getCmp('oprPrewiredId').focus();	
					Ext.getCmp('oprPrewiredId').selectText();	
					
					/*		
			       	Ext.Msg.alert(alertTitle,"输入内容格式不正确", function() {			
							Ext.getCmp('oprPrewiredId').markInvalid("输入内容格式不正确");
							Ext.getCmp('oprPrewiredId').focus();	
							Ext.getCmp('oprPrewiredId').selectText();	
					});*/
				
						return;
			    }
		    }
			
			var oprPrewiredId = Ext.getCmp("oprPrewiredId").getValue();  //预配ID
			var oprPrewiredGoWhere = Ext.getCmp("oprPrewiredGoWhere").getValue();
			
			/*var feeId = Ext.getCmp("comboType").getValue();
			if(feeId=="全部"){
				feeId=-1;
			}*/
			var ids="";
			if(checkbox){
				if(oprPrewiredDataStore.getCount()!=0){
					for(i=0;i<oprPrewiredDataStore.getCount();i++){  
						oprPrewiredCopyStore.add(oprPrewiredDataStore.getAt(i));
					}
					
				}
			}
			/*
			if(Ext.getCmp('dic').getValue()=='部门交接'){
				oprPrewiredDetailDataStore.proxy = new Ext.data.HttpProxy({
						method : 'POST',
						url : sysPath+'/stock/oprPrewiredAction!getAllDetailDepart.action'
				});
			}else if(Ext.getCmp('dic').getValue()=='专车'){
				oprPrewiredDetailDataStore.proxy = new Ext.data.HttpProxy({
						method : 'POST',
						url : sysPath+'/stock/oprPrewiredAction!getAllDetailCar.action'
				});
			}else{
				oprPrewiredDetailDataStore.proxy = new Ext.data.HttpProxy({
						method : 'POST',
						url:sysPath+"/stock/oprPrewiredAction!getAllDetail.action"
				});
			}*/
			
			
			Ext.apply(oprPrewiredDataStore.baseParams={
	 	 				filter_EQL_id:oprPrewiredId,
			 	 		filter_EQS_autostowMode:oprPrewiredType,
			 	 		filter_LIKES_toWhere:oprPrewiredGoWhere,
			    		limit : pageSize
			});
			oprPrewiredDataStore.reload({
					params : {
						start : 0,
						privilege:privilege,
						limit : pageSize
					},callback :function(){
					
						
						if(Ext.get("checkbox").dom.checked){
							var arrayObj2 = new Array();
					
								 for(j=0;j<oprPrewiredCopyStore.getCount();j++){
									 for(i=0;i<oprPrewiredDataStore.getCount();i++){
										 	if(oprPrewiredDataStore.getAt(i).get("id")==oprPrewiredCopyStore.getAt(j).get("id")){
												arrayObj2.push(oprPrewiredDataStore.getAt(i).get("id"));
										 	}
									 }
								 }
					
							for(var i=0;i<arrayObj2.length;i++){
								 for(j=0;j<oprPrewiredCopyStore.getCount();j++){
								 	if(oprPrewiredCopyStore.getAt(j).get("id")==arrayObj2[i]){
									 		oprPrewiredCopyStore.removeAt(j);
								 	}
								 }
							}
							
							if(oprPrewiredCopyStore.getCount()!=0){
								for(i=0;i<oprPrewiredCopyStore.getCount();i++){
									oprPrewiredDataStore.add(oprPrewiredCopyStore.getAt(i));
								}
							}
						}
					//recordGrid.getSelectionModel().selectAll();
						
					}
			});
		}

						
		function deleOprw(){
				Ext.getCmp('showMsg').getEl().update('');
 			  var _records = recordGrid.getSelectionModel().getSelections();
			  if (_records.length == 0) {
				 Ext.Msg.alert(alertTitle, '请选择一条您需要作废的数据');
				 return false;
			  }else {
			     Ext.Msg.confirm(alertTitle,'数据作废后将不可恢复，您确定要作废这<span style="color:red">&nbsp;'+ _records.length + '&nbsp;</span>行数据吗?',function(btnYes) {
						if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
							var ids = "";
    						for(var i = 0; i < _records.length; i++) {
								ids += _records[i].data.id + ",";
							}  	
							form1.getForm().doAction('submit', {
								url : sysPath+ "/stock/oprPrewiredAction!updateStatus.action",
								method : 'post',
								params : {
   									ids : ids,
   									privilege:privilege
   								},
								waitMsg : '正在删除数据...',
								success : function(form1, action) {
										Ext.Msg.alert(alertTitle,action.result.msg,
										function() {
											searchLog();
											select();
											oprPrewiredDetailDataStore.removeAll();
										});
								},
								failure : function(form1, action) {
										Ext.Msg.alert(alertTitle,action.result.msg,
														function() {
															searchLog();
															 select();
															 oprPrewiredDetailDataStore.removeAll();
															 
											});
								}
							});
						}
					});
	   
				}
		}
		
		function printOprw(){
			Ext.Msg.alert(alertTitle,"暂时不提供打印服务,正在开发中...", function(){
				select();		
			});
		}
				
		function sumitStore(){
			  setTotalCount();
			  Ext.getCmp('showMsg').getEl().update(' ');
 			  var _records = recordGrid2.getSelectionModel().getSelections();
 			  var str="";
 			  var goWhere="";
 			  
 			  //判断分拨组和装卸组是否为空
 			  var loadingbrigade  = Ext.getCmp('loadingbrigade').getValue();
 			  var dispatchGroup = Ext.getCmp('dispatchGroup').getValue();
 			 
 			 /*
 			  //alert(dispatchGroup);
 			  if(null==loadingbrigade ||  loadingbrigade.length<1){
 			  	Ext.Msg.alert(alertTitle,'装卸组不能为空！');
 			  	return;
 			  }
 			  if(null==dispatchGroup ||  dispatchGroup.length<1){
 			  	Ext.Msg.alert(alertTitle,'分拨组不能为空！');
 			  	return;
 			  }
 			  */
 			  //判断结束
 			  if(Ext.getCmp("oprPre").getValue()==""){
 			  		Ext.getCmp('showMsg').getEl().update('<span style="color:red">实配方式不允许空值，请选择。</span>');
 			  		Ext.getCmp('oprPre').markInvalid("实配方式不允许空值");
					Ext.getCmp('oprPre').focus();	
						return;
 			  }
 			  
 			  if(Ext.getCmp("dicTo1").isVisible()){
 			  	goWhere=Ext.getCmp("dicTo1").getRawValue();
 			  	if(Ext.getCmp("dicTo1").getValue()==""){
 			  		Ext.getCmp('showMsg').getEl().update('<span style="color:red">实配去向不允许空值，请填写。</span>');
 			  		Ext.getCmp('dicTo1').markInvalid("实配去向不允许空值");
					Ext.getCmp('dicTo1').focus();	
 			  		
 					return;
 			  	}
 			  }
 			  
 			  if(Ext.getCmp("dicTo2").isVisible()){
 			  	goWhere=Ext.getCmp("dicTo2").getRawValue();
 			  	if(Ext.getCmp("dicTo2").getValue()==""){
 			  		Ext.getCmp('showMsg').getEl().update('<span style="color:red">实配去向不允许空值，请填写。</span>');
 			  		Ext.getCmp('dicTo2').markInvalid("实配去向不允许空值");
					Ext.getCmp('dicTo2').focus();	
						return;
 			  	}
 			  }
 			  if(Ext.getCmp("dicTo3").isVisible()){
 			  	goWhere=Ext.getCmp("dicTo3").getRawValue();
 			  	if(Ext.getCmp("dicTo3").getValue()==""){
 			  		Ext.getCmp('showMsg').getEl().update('<span style="color:red">实配去向不允许空值，请填写。</span>');
 			  		Ext.getCmp('dicTo3').markInvalid("实配去向不允许空值");
					Ext.getCmp('dicTo3').focus();	
					
						return;
 			  	}
 			  }
 			  if(Ext.getCmp("dicTo4").isVisible()){
 			  	goWhere=Ext.getCmp("dicTo4").getRawValue();
 			  	if(Ext.getCmp("dicTo4").getValue()==""){
 			  		Ext.getCmp('showMsg').getEl().update('<span style="color:red">实配去向不允许空值，请填写。</span>');
 			  		Ext.getCmp('dicTo4').markInvalid("实配去向不允许空值");
					Ext.getCmp('dicTo4').focus();
						return;
 			  	}
 			  }
 			  
 			  if(Ext.getCmp("dno").getValue()==""){
 			  		if(Ext.getCmp("oprPre").getValue()=='市内送货'||Ext.getCmp("oprPre").getValue()=='专车'){
	 			  		Ext.getCmp('showMsg').getEl().update('<span style="color:red">实配方式为市内送货和专车时，签收手机号码不允许为空，请填写。</span>');
 			  			Ext.getCmp('dno').markInvalid("签收手机号码不允许空值");
						Ext.getCmp('dno').focus();
	 			  		 
						return;
 			  		}
 			  }
 			  
 			  var autoDistribution  = Ext.getCmp("oprPre").getValue();
 			  for(i=0;i<_records.length;i++){
 			   
 			  	if(_records[i].data.recepiptNum<0){
 			  		Ext.getCmp('showMsg').getEl().update('<span style="color:red">输入的回单出库数不允许为负数，请改正(配送单号为'+_records[i].data.dno+')。</span>');
					/*Ext.Msg.alert(alertTitle,"输入的出库签单份数不允许为负数", function() {			
							_records.startEditing(i,5); 
					});*/
					return;	
				}
 			  	
				if(_records[i].data.realPiece<0){
					Ext.getCmp('showMsg').getEl().update('<span style="color:red">输入的配载件数不允许为负数，请改正(配送单号为'+_records[i].data.dno+')。</span>');
					/*Ext.Msg.alert(alertTitle,"输入的配载件数不允许为负数", function() {			
							_records.startEditing(i,9); 
					});*/
					return;	
				}
				
				if(_records[i].data.autostowMode!='专车'){
					if(_records[i].data.realPiece>_records[i].data.realPiece2){
						Ext.getCmp('showMsg').getEl().update('<span style="color:red">输入的配载件数不能大于库存件数，请改正((配送单号为'+_records[i].data.dno+')。</span>');
						/*Ext.Msg.alert(alertTitle,"输入的配载件数不能大于库存件数", function() {			
								_records.startEditing(i,9); 
						});*/
						return;	
					}
					
					/*if(_records[i].data.recepiptNum>_records[i].data.reachNum){
						Ext.Msg.alert(alertTitle,"输入的出库签单份数不能大于库存签单份数", function() {			
								_records.startEditing(i,5); 
							});
							return;	
						}*/
				  	}
				}
				
				
 			  
 			  	for(i=0;i<_records.length;i++){
	 			  	if(_records[i].get('autostowMode')!=''||_records[i].get('autostowMode')!=null){
	 			  		if(autoDistribution=='专车'){  //专车类型判断
	 			  			
	 			  		}else if(autoDistribution=='市内送货'){  //市内送货判断
	 			  			if(_records[i].get('autostowMode')!='市内送货'&&_records[i].get('autostowMode')!='专车'){
	 			  				Ext.Msg.alert(alertTitle,"市内送货实配时，不允许添加除专车和市内送货外的其他类型货物");
	 			  				return;
	 			  			}
	 			  		}else if(autoDistribution=='部门交接'){  //部门交接货物 不用判断
	 			  			
	 			  		}else{
							 if(_records[i].get('autostowMode')!=autoDistribution){
								Ext.getCmp('showMsg').getEl().update('<span style="color:red">存在其他的预配方式，不允许提交(配送单号为'+_records[i].data.dno+')。</span>');
								return;	
							 }
						}
					}else{
						str="注意单票添加货物，没有预配方式，请确保是同一配载类型！</b>";
					}
 			  	}
 			  
				  if (_records.length == 0) {
					Ext.getCmp('showMsg').getEl().update('<span style="color:red">请选择您需要实配的记录。</span>');
					 return false;
				  }else {
				  	var ble=false;
				  	var ble2=false;
				  	var s=_records[0].data.toWhere;
				  	var di=_records[0].data.dno
				  	for(i=0;i<_records.length;i++){
				  		if(_records[i].data.toWhere!=''){
					  		if(goWhere!=_records[i].data.toWhere){
					  			ble=true;
					  			if(ble){	
					  				str+='配送单号为<span style="color:red">'+_records[i].data.dno+'</span>填写的实配去向<span style="color:red">'+goWhere+'</span>和预配去向<span style="color:red">'+_records[i].data.toWhere+'</span>不一致，请确定实配去向准确！</b>';}
					  		}
				  		}
				  		
				  		if(i!=1){
				  		}
		 				if(s!=_records[i].data.toWhere){
		 					ble2=true;
		 					if(ble2){		
		 						str+='配送单号为<span style="color:red">'+_records[i].data.dno+'</span>的数据与配送单号为<span style="color:red">'+di+'</span>的预配去向不一致，请确定实配去向准确！</span></b>';}
		 				}
	 			  	}
				  	
				    Ext.Msg.confirm(alertTitle,str+'<b>您确定要实配这<span style="color:red">&nbsp;'+ _records.length + '&nbsp;</span>票货物吗?',function(btnYes) {
						if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
							var ids = "";
							var dnos="";
							var telPhone=Ext.getCmp('dno').getValue();
    						for(var i = 0; i < _records.length; i++) {
    							for(var j=0;j<oprPrewiredDetailDataStore.getCount();j++){
    								if(oprPrewiredDetailDataStore.getAt(j).get('wId')==_records[i].get("wId")){
	    								if(oprPrewiredDetailDataStore.getAt(j).get('dno')!=_records[i].get("dno")){
												dnos += oprPrewiredDetailDataStore.getAt(j).get('dno') + ",";
	    								}
    								}
								}
								
    							if(i==0){
    								var rea;
    								if(_records[i].get("reachNum")==null){
    									rea=0;
    								}else{
    									rea=_records[i].get("reachNum");
    								}
    								var realpiece;
    								if(_records[i].get("realPiece")==null){
    									realpiece=0;
    								}else{
    									realpiece=_records[i].get("realPiece");
    								}
									ids += "aa["+i+"].dno="+_records[i].get("dno")+"&aa["+i+"].realPiece="+realpiece+"&aa["+i+"].wId="+_records[i].get("wId")+"&aa["+i+"].reachNum="+rea+"&aa["+i+"].telPhone="+telPhone;

								}else{
									var reaN;
									if(_records[i].get("reachNum")==null){
	    								reaN=0;	
	    							}else{
	    								reaN=_records[i].get("reachNum")
	    							}
	    							if(_records[i].get("realPiece")==null){
    									realpiece=0;
    								}else{
    									realpiece=_records[i].get("realPiece");
    								}
									ids += "&aa["+i+"].dno="+_records[i].get("dno")+"&aa["+i+"].realPiece="+realpiece+"&aa["+i+"].wId="+_records[i].get("wId")+"&aa["+i+"].reachNum="+rea+"&aa["+i+"].telPhone="+telPhone;
								}
							} 
							if(ids!=""){
							    ids+="&";
							}
							ids+="privilege="+privilege;
						    ids+="&loadingbrigade="+Ext.getCmp("loadingbrigade").getRawValue();
							ids+="&loadingbrigadeId="+Ext.getCmp("loadingbrigade").getValue();
							ids+="&dispatchGroup="+Ext.getCmp("dispatchGroup").getValue();
							ids+="&autostowMode="+Ext.getCmp("oprPre").getValue();
						//	ids+="&totalDno="+Ext.getCmp("totalDno").getValue();
						//	ids+="&totalNum="+Ext.getCmp("totalNum").getValue();
						//	ids+="&totalWeight="+Ext.getCmp("totalWeight").getValue();
							if(Ext.getCmp("dicTo1").isVisible()){
								ids+="&endDepartName="+Ext.getCmp("dicTo1").getRawValue();
								ids+="&endDepartId="+Ext.getCmp("dicTo1").getValue();
							}
							if(Ext.getCmp("dicTo2").isVisible()){
								ids+="&endDepartName="+Ext.getCmp("dicTo2").getRawValue();
								ids+="&endDepartId="+Ext.getCmp("dicTo2").getValue();
							}
							if(Ext.getCmp("dicTo3").isVisible()){
								ids+="&endDepartName="+Ext.getCmp("dicTo3").getRawValue();
								ids+="&endDepartId="+Ext.getCmp("dicTo3").getValue();
							}
							if(Ext.getCmp("dicTo4").isVisible()){
								ids+="&endDepartName="+Ext.getCmp("dicTo4").getRawValue();
								ids+="&endDepartId="+Ext.getCmp("dicTo4").getValue();
							}
							ids+="&dnos="+dnos;

							form.getForm().doAction('submit', {
								url : sysPath+ "/stock/oprPrewiredAction!saveOvem.action",
								method : 'post',
								params : ids,
								waitMsg : '正在保存实配数据...',
								success : function(form, action) {
										parent.ovemPrint('3',{print_overmemoId:action.result.value});
										oprPrewiredDetailDataStore.removeAll();
										searchLog();
										},
								failure : function(form, action) {
									Ext.Msg.alert(alertTitle,action.result.msg,
										function() {
											searchLog();
												
									});
								}
							});
						}
					});
	   
				}
 			 
 			 
 			 
 			 
		}
		
		// 单票添加
		function addOneStore(){
			Ext.getCmp('showMsg').getEl().update('<span style="color:red"></span>');
			var oprdno = Ext.getCmp('oprdno').getValue()
			var regex=/^[1-9]\d*$/;
			if(oprdno!=""){
			    if(!regex.test(oprdno)){
			       	Ext.getCmp('showMsg').getEl().update('<span style="color:red">输入配送单号格式不正确，无法单票添加。</span>');
			       	Ext.getCmp('oprdno').focus();
			       	Ext.getCmp('oprdno').markInvalid("输入的配送单号格式不正确");
					Ext.getCmp('oprdno').selectText();	
			     
					return;
			    }
		    }else{
		    	Ext.getCmp('showMsg').getEl().update('<span style="color:red">配送单号不能为空，请填写。</span>');
			    Ext.getCmp('oprdno').focus();	
				Ext.getCmp('oprdno').markInvalid("内容不能为空");
		    	
		    	return;
		    }
			//迭代保存已有记录
			if(oprPrewiredDetailDataStore.getCount()!=0){
					for(i=0;i<oprPrewiredDetailDataStore.getCount();i++){
						if(oprPrewiredDetailDataStore.getAt(i).get('dno')==oprdno){ 
							recordGrid2.getSelectionModel().selectRow(i);
							Ext.getCmp('showMsg').getEl().update('<span style="color:red">配送单号'+oprdno+'的记录已经存在，不能重复添加。</span>');
							return;
						}
						oprPrewiredDetailCopyDataStore.add(oprPrewiredDetailDataStore.getAt(i));
					}
			}
				Ext.Ajax.request({
						url:sysPath+"/stock/oprPrewiredAction!getAllByDno.action",
					params:{
						dno:oprdno,
						autostowMode:Ext.getCmp('oprPre').getValue()
					},
					success : function(resp) {
						var jdata = Ext.util.JSON.decode(resp.responseText);
						if(jdata.length==0){
							Ext.getCmp('showMsg').getEl().update('<span style="color:red">没有查询到配送单号为'+oprdno+'这票货的数据。</span>');
						}
						if(null != jdata.success && !jdata.success){
							Ext.Msg.alert(alertTitle,"<font color=red>"+jdata.msg+"</font>",
								function(e){
									Ext.getCmp('oprdno').focus();
								});
						}else{
							oprPrewiredDetailDataStore.add(new Ext.data.Record({
								wId:jdata.resultMap[0].WID==null?'':jdata.resultMap[0].WID,
								autostowMode:jdata.resultMap[0].AUTOSTOWMODE==null?'':jdata.resultMap[0].AUTOSTOWMODE,
								dno:jdata.resultMap[0].DNO==null?'':jdata.resultMap[0].DNO,
								toWhere:jdata.resultMap[0].TOWHERE==null?'':jdata.resultMap[0].TOWHERE,
								consignee:jdata.resultMap[0].CONSIGNEE==null?'':jdata.resultMap[0].CONSIGNEE,
								request:jdata.resultMap[0].REQUEST==null?'':jdata.resultMap[0].REQUEST,
								weight:jdata.resultMap[0].WEIGHT==null?'0':jdata.resultMap[0].WEIGHT,
								piece:jdata.resultMap[0].PIECE==null?'0':jdata.resultMap[0].PIECE,
								addr:jdata.resultMap[0].ADDR==null?'':jdata.resultMap[0].ADDR,
								cpName:jdata.resultMap[0].CPNAME==null?'':jdata.resultMap[0].CPNAME,
								goWhere:jdata.resultMap[0].GOWHERE==null?'':jdata.resultMap[0].GOWHERE,
								realPiece:jdata.resultMap[0].REALPIECE==null?'0':jdata.resultMap[0].REALPIECE,
								realPiece2:jdata.resultMap[0].REALPIECE2==null?'0':jdata.resultMap[0].REALPIECE2,
								receiptType:jdata.resultMap[0].RECEIPTTYPE==null?'':jdata.resultMap[0].RECEIPTTYPE,
								consigneeRate:jdata.resultMap[0].CONSIGNEERATE==null?'':jdata.resultMap[0].CONSIGNEERATE,
								consigneeFee:jdata.resultMap[0].CONSIGNEEFEE==null?'':jdata.resultMap[0].CONSIGNEEFEE,
								town:jdata.resultMap[0].TOWN==null?'':jdata.resultMap[0].TOWN,
								reachNum:jdata.resultMap[0].REACHNUM==null?'0':jdata.resultMap[0].REACHNUM,
								recepiptNum:jdata.resultMap[0].RECEPIPTNUM==null?'0':jdata.resultMap[0].RECEPIPTNUM,
								cpFee:jdata.resultMap[0].CPFEE==null?'':jdata.resultMap[0].CPFEE,
								traFee:jdata.resultMap[0].TRAFEE==null?'':jdata.resultMap[0].TRAFEE,
								feeAuditStatus:jdata.resultMap[0].FEEAUDITSTATUS==null?'':jdata.resultMap[0].FEEAUDITSTATUS,
								printNum:jdata.resultMap[0].PRINTNUM==null?'0':jdata.resultMap[0].PRINTNUM,
								paymentCollection:jdata.resultMap[0].PAYMENTCOLLECTION==null?'':jdata.resultMap[0].PAYMENTCOLLECTION,
								FLIGHT_MAIN_NO:jdata.resultMap[0].FLIGHT_MAIN_NO==null?'':jdata.resultMap[0].FLIGHT_MAIN_NO
								
							}));
			
							var count=oprPrewiredDetailDataStore.getCount();
							var wpiece=0;
							var wweight=0;
							var wpiece2=0;
							//信息汇总
							for(var j=0;j<oprPrewiredDetailDataStore.getCount();j++){
						 		wpiece+=parseFloat(oprPrewiredDetailDataStore.getAt(j).get("piece"));
								wweight+=parseFloat(oprPrewiredDetailDataStore.getAt(j).get("weight"));
								if(oprPrewiredDetailDataStore.getAt(j).get("piece2")==''||oprPrewiredDetailDataStore.getAt(j).get("realPiece2")==null){
									
									wpiece2+=parseFloat(oprPrewiredDetailDataStore.getAt(j).get("realPiece2"));
								}else{
									wpiece2+=parseFloat(oprPrewiredDetailDataStore.getAt(j).get("realPiece2"));
								}
						 	}
						 	var s ='{"weight":'+'"'+wweight+'kg","dno":'+'"'+count+'票","autostowMode":"","piece":'+'"'+wpiece+'件","realPiece2":'+'"库存'+wpiece2+'件"}';
						 	summary.toggleSummary(true);
							summary.setSumValue(Ext.decode(s));
							
							Ext.Ajax.request({
	 							url:sysPath+"/stock/oprStatusAction!list.action",
								params:{
									filter_EQL_dno:oprdno
								},
								success : function(response) { // 回调函数有1个参数
									var decode=Ext.decode(response.responseText);
									if(decode.result[0].returnStatus!=0&&decode.result[0].returnStatus!=null){
										var msg='';
										if(decode.result[0].returnStatus==1){
											msg='整票返货';
										}else if(decode.result[0].returnStatus==2){
											msg='部分返货';
										}else if(decode.result[0].returnStatus==3){
											msg='拆零返货';
										}
										Ext.getCmp('showMsg').getEl().update('<span style="color:red">配送单号为'+oprdno+'的货物已'+msg+'。</span>');
									}
								},
								failure : function(response){
								}
							});	
						}
					},
					failure : function(response) {
						Ext.getCmp('showMsg').getEl().update(vstr1);
					}
				});
			Ext.getCmp('oprdno').selectText();
			}
});
						/**
						 * 汇总表格
		function fnSumInfo(ids) {
			Ext.Ajax.request({
				url : sysPath+'/stock/oprPrewiredAction!getTotalDetail.action?privilege='+privilege,
				params:{
					ids:ids
				},
				success : function(response) { // 回调函数有1个参数
					summary.toggleSummary(true);
					summary.setSumValue(Ext.decode(response.responseText));
				},
				failure : function(response) {
					Ext.MessageBox.alert(alertTitle, '汇总数据失败');
				}
			});		
		}
						 */

				//删除
    	function delTotalStore(rowIndex){
    		var ids="";
    		Ext.Msg.confirm(alertTitle, "确定要移除这票货物吗？此动作仅仅从页面上移除这条记录。", function(btnYes) {
		    	if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
		    		    var form=Ext.getCmp("myForm");
				    	var totalStore=Ext.StoreMgr.get("oprPrewiredDetailDataStore");
				    	totalStore.removeAt(rowIndex); 
				   		
						var count=totalStore.getCount();
						var wpiece=0;
						var wweight=0;
						var wpiece2=0;
						for(var j=0;j<totalStore.getCount();j++){
					 		wpiece+=parseFloat(totalStore.getAt(j).get("piece"));
							wweight+=parseFloat(totalStore.getAt(j).get("weight"));
							if(totalStore.getAt(j).get("piece2")==''||totalStore.getAt(j).get("realPiece2")==null){
								wpiece2+=parseFloat(totalStore.getAt(j).get("realPiece2"));
							}else{
								wpiece2+=parseFloat(totalStore.getAt(j).get("realPiece2"));
							}
					 	}
					 	var s ='{"weight":'+'"'+wweight+'kg","dno":'+'"'+count+'票","autostowMode":"","piece":'+'"'+wpiece+'件","realPiece2":'+'"库存'+wpiece2+'件"}';
					 	summary.toggleSummary(true);
						summary.setSumValue(Ext.decode(s));
						
						form.render();
			   }
		  })
       }

	


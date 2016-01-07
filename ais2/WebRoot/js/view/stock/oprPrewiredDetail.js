//货物预配JS
	Ext.QuickTips.init();
	var privilege=74;
	var comboxPage=comboSize;
	var saveUrl="stock/oprSignAction!saveSignStatus.action";
	var customerGridSearchUrl="sys/customerAction!list.action";
	var areaListUrl="stock/oprStoreAreaAction!list.action";
	var ralaListUrl="stock/oprPrewiredDetailAction!ralaList.action";
	var dictionaryUrl='sys/dictionaryAction!ralaList.action';
	var searchCusNameUrl='sys/customerAction!list.action';//查询代理公司地址
	var booleanId =false; 
	var queryPageSize=100;
	var fields=[{name:"dno",mapping:'T0_D_NO'},
				{name:"wait",mapping:'WAIT'},
    			{name:'piece',mapping:'T0_PIECE'},
    			{name:'cpName',mapping:'T0_CPNAME'},
    			{name:'remark',mapping:'T0_REMARK'},
    			{name:'town',mapping:'T0_TOWN'},
    			{name:'takeMode',mapping:'T0_TAKE_MODE'},
    			{name:'curDepart',mapping:'T0_CUR_DEPART_NAME'},
    			{name:'goods',mapping:'T0_GOODS'},
    			{name:'flightDate',mapping:'T0_FLIGHT_DATE'},
    			{name:'goodsStatus',mapping:'T0_GOODS_STATUS'},
    			{name:'flightNo',mapping:'FLIGHT_NO'},
    			{name:'consignee',mapping:'T0_CONSIGNEE'},
    			{name:'consigneeTel',mapping:'T0_CONSIGNEE_TEL'},
    			{name:'town',mapping:'T0_TOWN'},
    			{name:'flightMainNo',mapping:'FLIGHT_MAIN_NO'},
    			{name:'addr',mapping:'T0_ADDR'},//t0_take_mode
    			{name:'cusWeight',mapping:'T0_CUS_WEIGHT'},
    			{name:'gowhere',mapping:'T0_GOWHERE'},
    			{name:'realPiece',mapping:'T1_PIECE'},
    			{name:'request',mapping:'T4_REQUEST'},
    			{name:'distributionMode',mapping:'T0_DISTRIBUTION_MODE'},
    			{name:'createTime',mapping:'T0_CREATE_TIME'},
    			{name:'city',mapping:'T0_CITY'},
    			{name:'sonderzug',mapping:'T0_SONDERZUG'}];
	
	var stockStore = new Ext.data.Store({
              fields: fields
    });
Ext.onReady(function(){

	Ext.QuickTips.init();


    var jsonread= new Ext.data.JsonReader({
                    root:'resultMap',
                    totalProperty:'totalCount'},
                    fields);
                              
	var sm = new Ext.grid.CheckboxSelectionModel({
		width:25,
		moveEditorOnEnter:true, 
		id:'checkId',
		name:'checkId'
	});

	var sm2 = new Ext.grid.CheckboxSelectionModel({
		width:25,
		moveEditorOnEnter:true, 
		id:'checkId2',
		name:'checkId2'
	});
	
 	var bussStore = new Ext.data.Store({ 
           storeId:"bussStore",                        
           proxy: new Ext.data.HttpProxy({url:sysPath+"/sys/departAction!findDepart.action",method:'post'}),
           baseParams:{
              privilege:53,
              filter_EQL_isBussinessDepa:1
              },
           reader: new Ext.data.JsonReader({
                       root: 'resultMap', totalProperty:'totalCount'
                   },[ {name:'bussDepartName', mapping:'DEPARTNAME'},    
                       {name:'bussDepart', mapping: 'DEPARTID'}
           ]),                                      
           sortInfo:{field:'bussDepart',direction:'ASC'}
    });
	
	//配载方式
	var dictionaryStore = new Ext.data.Store({ 
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

	//预配方式
	var dictionaryStore2 = new Ext.data.Store({ 
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
    dictionaryStore2.load();
	//代理公司
	cpNameStore= new Ext.data.Store({ 
		storeId:"cpNameStore",
		baseParams:{privilege:61},
		proxy:new Ext.data.HttpProxy({url:sysPath+"/"+searchCusNameUrl}),
		reader:new Ext.data.JsonReader({
                    root:'result', totalProperty:'totalCount'
        },[
    	{name:'cusName',mapping:'cusName'},
    	{name:'cusId',mapping:'id'}
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
	changeStore.load();
	
	var dataStore = new Ext.data.Store({
                proxy: new Ext.data.HttpProxy({url:sysPath+"/"+ralaListUrl}),
                baseParams:{limit: queryPageSize},
                sortInfo : {field: "dno", direction: "ASC"},
                reader:jsonread,
                listeners:{
	                'load':function(s,records){ 
					        var girdcount=0; 
					        s.each(function(r){ 
					            if(r.get('wait')==1&&r.get('distributionMode')!='外发'&&r.get('distributionMode')!='中转'&& r.get('distributionMode')!='部门交接'){ 
					                recordGrid.getView().getRow(girdcount).style.backgroundColor='gray';
					            }
				            	girdcount=girdcount+1; 
					        })
	        			}	
                }
    });
	Ext.override(Ext.grid.RowSelectionModel, {
      selectRow: function(index, keepExisting, preventViewNotify) {
	        if (this.isLocked() || (index < 0 || index >= this.grid.store.getCount()) 
	        				|| (keepExisting && this.isSelected(index)) 
	        				   || (Number(this.grid.store.getAt(index).get("wait")) == 1)
	        				   	&&this.grid.store.getAt(index).get("distributionMode")!='外发'
	        				   	&&this.grid.store.getAt(index).get("distributionMode")!='中转'
	        				   	&&this.grid.store.getAt(index).get("distributionMode")!='部门交接') {
	        //根据每行的一个标识字段来判断是否选中
	            return;
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
	var copyDataStore = new Ext.data.Store({
              proxy: new Ext.data.HttpProxy({url:sysPath+"/"+ralaListUrl}),
              baseParams:{limit: queryPageSize},
              sortInfo : {field: "dno", direction: "ASC"},
              reader:jsonread
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

 	var twobar = new Ext.Toolbar({
 				id:'twobar',
		    	items : ['&nbsp;','<B>查询条件</B>','&nbsp;','-','&nbsp;','配载方式<span style="color:red">*</span>:',
		    						   {xtype : 'combo',
										id:'dic',
										hiddenId : 'dictionaryName',
						    			hiddenName : 'dictionaryName',
										triggerAction : 'all',
										mode:'local',
										store : dictionaryStore2,
										width:93,
										allowBlank : false,
										emptyText : "请选择配载方式",
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
							 					Ext.getCmp('oprPre').setValue(c.getValue());
							 					var combo = Ext.getCmp('oprPre');
							 					if(combo.getValue()=='部门交接'){
							 						Ext.getCmp('bud').show();
							 						Ext.getCmp('bussDepart').show();
							 						//Ext.getCmp('goWhere').hide();
							 						//Ext.getCmp('gow').hide();
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
												 //	Ext.getCmp('gow').show();
							 						//Ext.getCmp('goWhere').show();
							 						Ext.getCmp('bussDepart').hide();
							 						Ext.getCmp('bud').hide();
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
												 	//Ext.getCmp('gow').show();
							 						//Ext.getCmp('goWhere').show();
							 						Ext.getCmp('bussDepart').hide();
							 						Ext.getCmp('bud').hide();
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
												 	//Ext.getCmp('gow').show();
							 						//Ext.getCmp('goWhere').show();
							 						Ext.getCmp('bussDepart').hide();
							 						Ext.getCmp('bud').hide();
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
								    },'&nbsp;','送货区域:',
						 				{xtype : 'textfield',
			        	                fieldLabel: '送货区域', 
			        	                id:'area',
				        			    name: 'area',
				        			    maxLength : 10,
						                anchor : '95%',
						                width:96,
						                enableKeyEvents:true,
							            listeners : {
							 				keyup:function(textField, e){
							                     if(e.getKey() == 13){
							                     	searchLog();
							                     }
							 				}
							 			}
						              },'&nbsp;',{
							    			xtype:'label',
							    			id:'bud',
							    			hidden:true,
							    			html:'到达部门:',
							    			width:30
							    		},
						              {
									   xtype : 'combo',
			        				   fieldLabel: '业务部门',
							           allowBlank : true,
							           id:'bussDepart',
								       minChars : 0,
								       hidden:true,
								       width:88,
								       listWidth : 250,
								       forceSelection : true,
								       triggerAction : 'all',
									   store: bussStore,
									   pageSize : comboxPage,
									   queryParam : 'filter_LIKES_departName',
									   displayField : 'bussDepartName',
									   valueField : 'bussDepart'
			        				},/*{
							    			xtype:'label',
							    			id:'gow',
							    			html:'去向:',
							    			width:30
							    		},
    	   				 			　　{xtype : 'textfield',
			        	                fieldLabel: '去向',
			        	                id:'goWhere',
				        			    name: 'goWhere',
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
						              },*/'&nbsp;&nbsp;',
								    	{xtype : 'checkbox',
			        	                 id:'checkbox',
			        	                 name:'checkbox',
			        	                 boxLabel:'累计查询',
						                 anchor : '95%'
						            } ,'&nbsp;&nbsp;','-','&nbsp;&nbsp;',{
    				     text : '<b>查询</b>',
    				     id : 'btn',
    				     iconCls : 'btnSearch',
    					 handler : selectDistributionMode
    				}]
		});

      	var threebar = new Ext.Toolbar({
		    	items : ['&nbsp;','<B>预配数据</B>','&nbsp;','-','&nbsp;','预配方式<span style="color:red">*</span>:',
									    {xtype : 'combo',
										id:'oprPre',
										hiddenId : 'dictionaryName',
						    			hiddenName : 'dictionaryName',
										triggerAction : 'all',
								//		mode:'local',
										store : dictionaryStore2,
										width:93,
										minChars : 1,
										queryParam:'filter_LIKES_typeName',
										allowBlank : true,
										emptyText : "请选择预配方式",
										forceSelection : true,
										editable : true,
										displayField : 'typeName',//显示值，与fields对应
										valueField : 'typeName',//value值，与fields对应
										name : 'typeName',
										anchor : '100%',
										listeners:{
											select:function(combo,recode,index){
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
								    },'&nbsp;','预配去向<span style="color:red">*</span>:',
		    						   {xtype : 'combo',
										id:'dicTo1',
										hiddenId : 'toName',
						    			hiddenName : 'toName',
										triggerAction : 'all',
										store : changeStore,
										hideTrigger:true,
										width:93,
										hidden:true,
										disabled:true,
										minChars : 1,
										queryParam:'filter_LIKES_departName',
										listWidth:245,
										allowBlank : true,
										emptyText : "请选择预配去向",
									//	forceSelection : true,
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
							                     	Ext.MessageBox.alert(alertTitle, '必须先选择预配方式',function(){			
							                     		Ext.getCmp('oprPre').markInvalid("");
														Ext.getCmp('oprPre').focus(true,true);	
													});
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
										hideTrigger:true,
										disabled:true,
										width:93,
										minChars : 1,
										queryParam:'filter_LIKES_cusName',
										listWidth:245,
										allowBlank : true,
										emptyText : "请选择预配去向",
									//	forceSelection : true,
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
							                     	Ext.MessageBox.alert(alertTitle, '必须先选择预配方式',function(){			
							                     		Ext.getCmp('oprPre').markInvalid("");
														Ext.getCmp('oprPre').focus(true,true);	
													});
							                     }
							 				}
							 			}},
							 			 {xtype : 'combo',
										id:'dicTo3',
										hiddenId : 'toName',
						    			hiddenName : 'toName',
										triggerAction : 'all',
										store : changeStore,
										width:93,
										hideTrigger:true,
										hidden:true,
										disabled:true,
										minChars : 1,
										queryParam:'filter_LIKES_cusName',
										listWidth:245,
										allowBlank : true,
										emptyText : "请选择预配去向",
									//	forceSelection : true,
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
							                     	Ext.MessageBox.alert(alertTitle, '必须先选择预配方式',function(){			
							                     		Ext.getCmp('oprPre').markInvalid("");
														Ext.getCmp('oprPre').focus(true,true);	
													});
							                     }
							 				}
							 			}},
							 			 {xtype : 'combo',
										id:'dicTo4',
										hiddenId : 'toName',
						    			hiddenName : 'toName',
										triggerAction : 'all',
										store : changeStore,
										width:93,
										hideTrigger:true,
										minChars : 1,
										queryParam:'filter_LIKES_userName',
										listWidth:245,
										allowBlank : true,
										emptyText : "请选择预配去向",
									//	forceSelection : true,
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
							                     	Ext.MessageBox.alert(alertTitle, '必须先选择预配方式',function(){			
							                     		Ext.getCmp('oprPre').markInvalid("");
														Ext.getCmp('oprPre').focus(true,true);	
													});
							                     }
							 				}
							 			}
								    },'&nbsp;',
								    	{xtype : 'checkbox',
			        	                 id:'orderCheckbox',
			        	                 name:'OrderCheckbox',
			        	                 checked:true,
			        	                 boxLabel:'排序打印',
						                 anchor : '95%'
						            },'&nbsp;','总票数:',
						              　　{xtype : 'textfield',
			        	                fieldLabel: '总票数',
			        	                id:'dno',
				        			    name: 'dno',
				        			    readOnly:true,
				        			    maxLength : 10,
						                width:60,
						                enableKeyEvents:true
						            
						            },'&nbsp;','总件数:',
    	   				 			　　{xtype : 'textfield',
			        	                fieldLabel: '总件数',
			        	                id:'totalNum',
				        			    name: 'totalNum',
				        			    maxLength : 10,
				        			    readOnly:true,
						                width:60,
						                enableKeyEvents:true
						            
						              },'&nbsp;','总重量:',
								    	{xtype : 'textfield',
			        	                fieldLabel: '总载重量',
			        	                id:'weight',
				        			    name: 'weight',
				        			    readOnly:true,
				        			    maxLength : 10,
						                width:60,
						                enableKeyEvents:true
						              }]
		});
		
	 var summary = new Ext.ux.grid.GridSummary();
     var summary2 = new Ext.ux.grid.GridSummary();
     
     var recordGrid=new Ext.grid.EditorGridPanel({
		id:'userCenter',
		height : 200,
		width : Ext.lib.Dom.getViewWidth()*0.65-10,
		autoScroll : true,  //面板上的body元素
	 	viewConfig : {
				scrollOffset: 0,
				autoScroll:true
		},
		frame : true,
		border : true,
		//enableColumnResize:false, //关闭列的自适大小功能
		autoWidth:false,
	//	clicksToEdit:1,
		plugins : [summary],
		loadMask : true,
		sm:sm,
		stripeRows : true,
		
		columns:[ sm,
        			{header: '货物状态', dataIndex: 'goodsStatus',width:80,sortable : true,  //css : 'background: #CAE3FF;',
        				renderer: function(v, metadata, record, rowIndex, columnIndex, store) {
        					var val=v;
        					if(record.get('wait')==1){
        						val+='<font color=red><b>等</b></font>'
        					}
        					if(record.get('sonderzug')=='1'){
						 		val+='<font color=red><b>专</b></font>'
        					}
        					return val;
        				}
        			},
        			{header: '发货代理', dataIndex: 'cpName',width:80,sortable : true},
        			{header: '是否专车', dataIndex: 'sonderzug',width:80,sortable : true,hidden:true},
			        {header: '配送单号', dataIndex: 'dno',width:80,sortable : true},  //
			        {header: '当前部门', dataIndex: 'curDepart',width:90,sortable : true,
			        	renderer: function(v, metadata, record, rowIndex, columnIndex, store) {
					    	if(v!=bussDepartName){
					    		return '<font color=red>'+v+'</font>';
					    	}else{
					    		return v;
					    	}
					    }
					 },  //
			        {header: '主单号', dataIndex: 'flightMainNo',width:80,sortable : true},
			        {header: '是否等通知放货', dataIndex: 'wait',width:80,sortable : true,hidden:true},
			        {header: '提货方式', dataIndex: 'takeMode',width:60,sortable : true},
			        {header: '供应商', dataIndex: 'gowhere',width:60,sortable : true},
			        {header: '所属区域', dataIndex: 'town',width:60,sortable : true},
			        			        {header: '收货人信息', dataIndex: 'consignee',width:150,sortable : true,
			        	renderer: function(v, metadata, record, rowIndex, columnIndex, store) {
					    	return v+'/'+record.get('addr');
					    }},

	  				{header: '个性化要求', dataIndex: 'request',width:100},
        			 {header: '备注', dataIndex: 'remark',width:80,sortable : true},
					{header: '配载方式', dataIndex: 'distributionMode',width:60,sortable : true,
			        		  renderer:function(value){
			        		  	if(value=='新邦'){
							      	return '专车';
			        		  	}else{
			        		  		return value;
			        		  	}
			        		  
							  }},
			        {header: '航班号', dataIndex: 'flightNo',width:60,sortable : true},
			        {header: '航班日期', dataIndex: 'flightDate',width:80,sortable : true,renderer:function(value){
						      	return value.substr(0,10);
							  }
					},
			        {header: '件数', dataIndex: 'piece',width:60,sortable : true},
			        {header: '库存件数', dataIndex: 'realPiece',width:60,sortable : true},
			        {header: '重量', dataIndex: 'cusWeight',width:60,sortable : true},
			     //   {header: '收货人地址', dataIndex: 'addr',width:150,sortable : true},
			        {header: '品名', dataIndex: 'goods',width:80,sortable : true}
			    ],
		store :dataStore,
		bbar : new Ext.PagingToolbar({
					pageSize : queryPageSize, 
					store : dataStore,
					displayInfo : true,
					displayMsg : '显示第 {0} 条到 {1} 条记录，共 {2} 条',
					emptyMsg : "没有记录信息显示"
		})
		
	});
     
        var recordGrid2=new Ext.grid.GridPanel({
			id:'userCenter2',
			height : 10,
			width : Ext.lib.Dom.getViewWidth()*0.3-2,
			autoScroll : true,  //面板上的body元素
			viewConfig : {
				scrollOffset: 0,
				autoScroll:true
			},
			frame : true,
			border : true,
		//	enableColumnResize:false, //关闭列的自适大小功能
			autoWidth:false,
			plugins : [summary2],
			loadMask : true,
			sm:sm2,
			stripeRows : true,
			columns:[ sm2,
			        {header: '配送单号', dataIndex: 'dno',width:60},
			        {header: '配载方式', dataIndex: 'distributionMode',width:60,sortable : true},
			        {header: '收货人姓名', dataIndex: 'consignee',width:80,sortable : true},
			        {header: '件数', dataIndex: 'piece',width:60,sortable : true},
			        {header: '库存件数', dataIndex: 'realPiece',width:60,sortable : true},
			        {header: '重量', dataIndex: 'cusWeight',width:60}
			    ],
			store : copyDataStore,
			bbar : new Ext.PagingToolbar({
					id:'toolBar2',
					pageSize : queryPageSize, 
					store : copyDataStore,
					displayInfo : true,
					displayMsg : '显示第 {0} 条到 {1} 条记录，共 {2} 条',
					emptyMsg : "没有记录信息显示"
					
			})
	});

      var form = new Ext.form.FormPanel({
								labelAlign : 'left',
								frame : true,
								border : true,
								renderTo:Ext.getBody(),
								bodyStyle : 'padding:0px 0px 0px',
							    height : Ext.lib.Dom.getViewHeight(),
								width : Ext.lib.Dom.getViewWidth(),
								labelAlign : "right",
									tbar:['&nbsp;&nbsp;',{
										text : '<B>货物预配</B>',
										id : 'submitbtn',
										disabled : true,
										tooltip : '货物预配',
										iconCls:'userEdit',
										handler : sumitStore
												
									},'-','&nbsp;&nbsp;',
			        				{	xtype:'textfield',
							 	        id : 'itemsValue',
							 	        hidden:true,
							 	        disable:true,
								        name : 'itemsValue',
							            enableKeyEvents:true,
							            listeners : {
							 				keyup:function(textField, e){
							                     if(e.getKey() == 13){
							                     	searchLog();
							                     }
							 				}
							 			}
							        },{ xtype : 'datefield',
							    		id : 'startDate',
							    		format : 'Y-m-d',
							    		emptyText : "选择开始时间",
							    		anchor : '95%',
							    		width : 100,
							    		value: new Date().add(Date.DAY, -7) ,// filter_GED_createTime : new Date().add(Date.DAY, -7).format("Y-m-d"),
							    		enableKeyEvents:true,
							    		listeners : {
							    			'select' : function() {
							    			   var start = Ext.getCmp('startDate').getValue().format("Y-m-d");
							    			   Ext.getCmp('endDate').setMinValue(start);
							    		     },
							    		     keyup:function(textField, e){
							                     if(e.getKey() == 13){
							                     	searchLog();
							                     }
							 				 }
							    		}
						    		},'-',{
							    		xtype : 'datefield',
							    		id : 'endDate',
							    		format : 'Y-m-d',
							    		emptyText : "选择结束时间",
							    		width : 100,
							    		value: new Date(),
							    		anchor : '95%',
							    		enableKeyEvents:true,
							    		listeners : {
							    		     keyup:function(textField, e){
							                     if(e.getKey() == 13){
							                     	searchLog();
							                     }
							 				 }
							    		}
						    	    },'-',{
						                xtype : "combo",
						    			id:"comboselect",
						   				width : 100,
						 				triggerAction : 'all',
						    			model : 'local',
						    			editable : false,
						    			hiddenId : 'checkItems',
						    			hiddenName : 'checkItems',
						    			name : 'checkItemstext',
						    			store : [['f.dNo', '配送单号'],
						    			        ['f.flightMainNo', '主单号'],
						    					['f.create_time', '录入时间'],
						    					['f.piece', '件数'],
						    					['LIKES_f.gowhere','供应商'],
						    					['LIKES_f.consignee', '收货人姓名'],
						    					['LIKES_f.goods', '品名']
						    					],
						    			emptyText : '选择查询类型',
						    			forceSelection : true,
						    			enableKeyEvents:true,
						    			listeners : {
						    					 keyup:function(textField, e){
								                     if(e.getKey() == 13){
								                     	searchLog();
								                     }
								 				 },
								 				 'render':function(combo, record, index){
								    				combo.setValue('f.create_time');
								    			 },
							    				'select' : function(combo, record, index) { 
							    					if (combo.getValue() == 't0.CREATE_TIME') {
							    						
							    						Ext.getCmp("startDate").enable();
							    						Ext.getCmp("startDate").show();
							    						Ext.getCmp("endDate").enable();
							    						Ext.getCmp("endDate").show();
							    								
							    						Ext.getCmp("itemsValue").disable();
							    						Ext.getCmp("itemsValue").hide();
							    						Ext.getCmp("itemsValue").setValue("");
							    					}else {
							    						Ext.getCmp("startDate").disable();
							    						Ext.getCmp("startDate").hide();
							    					    Ext.getCmp("startDate").setValue("");
							
							    						Ext.getCmp("endDate").disable();
							    						Ext.getCmp("endDate").hide();
							    						Ext.getCmp("endDate").setValue("");
							    						
							    						
							    						Ext.getCmp("itemsValue").setValue("");
							    						Ext.getCmp("itemsValue").show();
							    						Ext.getCmp("itemsValue").enable();
							    					}
							    				}
						    
						    		}},'-','发货代理:',{xtype : "combo",
											width : 100,
											editable:true,
											triggerAction : 'all',
											//typeAhead : true,
											queryParam : 'filter_LIKES_cusName',
											pageSize : pageSize,
											//forceSelection : true,
											resizable:true,
											minChars : 0,
											store : cpNameStore,
											//mode : "remote",// 从本地载值
											valueField : 'cusName',// value值，与fields对应
											displayField : 'cusName',// 显示值，与fields对应
										    id:'searchCpName', 
										    enableKeyEvents:true,
										    listeners : {
								 		   	 keyup:function(combo, e){
							                     if(e.getKey() == 13){
							                     	searchLog();
							                     }
								 		}
								 	
								 	}},'&nbsp;&nbsp;',{
							    			xtype:'label',
							    			id:'showMsg',
							    			width:380
							    		}],
								listeners: {
					                    render: function(){
					                        twobar.render(this.tbar);
					                        threebar.render(this.tbar);
					                    }
					            },
						        items : [{
											layout : 'column',
											items : [{
														columnWidth : .653,
														layout : 'form',
														items : [recordGrid]

													}, {
														columnWidth : .047,
														layout : 'form',
														buttonAlign:'center',
														items : [
															 {html : '<pre><p></p><p> &nbsp;</p><p> &nbsp;</p><p> </p><p> </p><p> </p><p> </p><p> </p><p></p><p> &nbsp;</p></pre>'}, 
																 {xtype:'button',
																  id:'button1',
																  disabled:true,
								    							  text:'<B>==>></B>',
								    							  width:Ext.lib.Dom.getViewWidth()*.0407,
								    							 // y:(Ext.lib.Dom.getViewHeight()-3*Ext.getCmp('twobar').getHeight()-10)*0.5,
								    							  handler:function(){
								    								 var order1=recordGrid.getSelectionModel().getSelections();
								    								 for(var i=0;i<order1.length;i++){
								    									 dataStore.remove(order1[i]);
								    								 }
								    								 ids="";
								    								 if(dataStore.getCount()!=0){
								    								 		for(i=0;i<dataStore.getCount();i++){
																				ids += dataStore.getAt(i).get('dno') + ",";
																			}
									    								 	Ext.Ajax.request({
																				url : sysPath+'/stock/oprPrewiredDetailAction!ajaxTotalSum.action?privilege='+privilege,
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
								    								 	
								    								 
																	 }else{
																	 	  ids="-1";
																	      Ext.Ajax.request({
																				url : sysPath+'/stock/oprPrewiredDetailAction!ajaxTotalSum.action?privilege='+privilege,
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
																	 var ids2="";
																	 var arrayObj = new Array();
																	 if(copyDataStore.getCount()!=0){
																			 for(j=0;j<copyDataStore.getCount();j++){
																				 for(i=0;i<order1.length;i++){
																				 	if(order1[i].data.dno==copyDataStore.getAt(j).get("dno")){
																				 		arrayObj.push(copyDataStore.getAt(j).get("dno"));
																				 	}
																				 }
																			 }
																	 }
																	 for(var i=0;i<arrayObj.length;i++){
																		 for(j=0;j<copyDataStore.getCount();j++){
																		 	if(copyDataStore.getAt(j).get("dno")==arrayObj[i]){
																		 		copyDataStore.removeAt(j);
																		 	}
																		 }
																	 }
																	 copyDataStore.add(order1);
								    								 if(copyDataStore.getCount()!=0){
								    								 		for(i=0;i<copyDataStore.getCount();i++){
																				ids2 += copyDataStore.getAt(i).get('dno') + ",";
																			}
									    								 	Ext.Ajax.request({
																				url : sysPath+'/stock/oprPrewiredDetailAction!ajaxTotalSum.action?privilege='+privilege,
																				params:{
																					ids:ids2
																				},
																				success : function(response) { // 回调函数有1个参数
																					summary2.toggleSummary(true);
																					summary2.setSumValue(Ext.decode(response.responseText));
																					Ext.getCmp("dno").setValue(Ext.decode(response.responseText).dno);
																					Ext.getCmp("totalNum").setValue(Ext.decode(response.responseText).piece);
																					Ext.getCmp("weight").setValue(Ext.decode(response.responseText).cusWeight);
																					
																				},
																				failure : function(response) {
																					Ext.MessageBox.alert(alertTitle, '汇总数据失败');
																				}
																			});
								    								 	
								    								 
																	 }else{
																	 		ids2="-1";
																	 		Ext.Ajax.request({
																				url : sysPath+'/stock/oprPrewiredDetailAction!ajaxTotalSum.action?privilege='+privilege,
																				params:{
																					ids:ids2
																				},
																				success : function(response) { // 回调函数有1个参数
																					summary2.toggleSummary(true);
																					summary2.setSumValue(Ext.decode(response.responseText));
																					Ext.getCmp("dno").setValue(Ext.decode(response.responseText).dno);
																					Ext.getCmp("totalNum").setValue(Ext.decode(response.responseText).piece);
																					Ext.getCmp("weight").setValue(Ext.decode(response.responseText).cusWeight);
																				},
																				failure : function(response) {
																					Ext.MessageBox.alert(alertTitle, '汇总数据失败');
																				}
																			});
																	 }
								    								 form.render();
								    								 if(copyDataStore.getCount()==0){
								    								     	Ext.getCmp("submitbtn").setDisabled(true);
								    								 }else{
								    								     	Ext.getCmp("submitbtn").setDisabled(false);
								    								 }
								    								 
								    								 if(dataStore.getCount()==0){
								    								 	Ext.getCmp("button1").disable();
								    								 }else{
								    								 	Ext.getCmp("button1").enable();
								    								 }
								    								 
								    								 if(copyDataStore.getCount()==0){
								    								 	Ext.getCmp("button2").disable();
								    								 }else{
								    								 	Ext.getCmp("button2").enable();
								    								 }
								    							  }
								    							  },
								    								{html:'<pre><p> </p></pre>'},
								    							    {xtype:'button',
								    							     id:'button2',
								    							     disabled:true,
								    							     text:'<B><<==</B>',
								    							     width:Ext.lib.Dom.getViewWidth()*.0407,
								    							     handler:function(){
								    								     var order2=recordGrid2.getSelectionModel().getSelections();
								    								     for(var i=0;i<order2.length;i++){
								    									     copyDataStore.remove(order2[i]);
								    								     }
								    								     var ids2="";
									    								 if(copyDataStore.getCount()!=0){
									    								 		for(i=0;i<copyDataStore.getCount();i++){
																					ids2 += copyDataStore.getAt(i).get('dno') + ",";
																				}
										    								 	Ext.Ajax.request({
																					url : sysPath+'/stock/oprPrewiredDetailAction!ajaxTotalSum.action?privilege='+privilege,
																					params:{
																						ids:ids2
																					},
																					success : function(response) { // 回调函数有1个参数
																						summary2.toggleSummary(true);
																						summary2.setSumValue(Ext.decode(response.responseText));
																						Ext.getCmp("dno").setValue(Ext.decode(response.responseText).dno);
																						Ext.getCmp("totalNum").setValue(Ext.decode(response.responseText).piece);
																						Ext.getCmp("weight").setValue(Ext.decode(response.responseText).cusWeight);
																					},
																					failure : function(response) {
																						Ext.MessageBox.alert(alertTitle, '汇总数据失败');
																					}
																				});
									    								 	
									    								 
																		 }else{
																		 	ids2="-1";
																		 	Ext.Ajax.request({
																					url : sysPath+'/stock/oprPrewiredDetailAction!ajaxTotalSum.action?privilege='+privilege,
																					params:{
																						ids:ids2
																					},
																					success : function(response) { // 回调函数有1个参数
																						summary2.toggleSummary(true);
																						summary2.setSumValue(Ext.decode(response.responseText));
																						Ext.getCmp("dno").setValue(Ext.decode(response.responseText).dno);
																						Ext.getCmp("totalNum").setValue(Ext.decode(response.responseText).piece);
																						Ext.getCmp("weight").setValue(Ext.decode(response.responseText).cusWeight);
																					},
																					failure : function(response) {
																						Ext.MessageBox.alert(alertTitle, '汇总数据失败');
																					}
																				});
																		 
																		 }
																		 var arrayObj2 = new Array();
								    								     if(dataStore.getCount()!=0){
																			 for(j=0;j<dataStore.getCount();j++){
																				 for(i=0;i<order2.length;i++){
																				 	if(order2[i].data.dno==dataStore.getAt(j).get("dno")){
																				 			arrayObj2.push(dataStore.getAt(j).get("dno"));
																				 	}
																				 }
																			 	
																			 }
																	 	}
																	 	for(var i=0;i<arrayObj2.length;i++){
																			dataStore.removeAt(arrayObj2[i]);
																			for(j=0;j<dataStore.getCount();j++){
																			 	if(dataStore.getAt(j).get("dno")==arrayObj[i]){
																			 		dataStore.removeAt(j);
																			 	}
																		 	}
																	 	}
																	 	
								    								     dataStore.add(order2);
								    								     ids="";
								    								 	 if(dataStore.getCount()!=0){
								    								 		for(i=0;i<dataStore.getCount();i++){
																				ids += dataStore.getAt(i).get('dno') + ",";
																			}
									    								 	Ext.Ajax.request({
																				url : sysPath+'/stock/oprPrewiredDetailAction!ajaxTotalSum.action?privilege='+privilege,
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
								    								 	
								    								 
																	 	}else{
																	 		ids="-1";
																	 		Ext.Ajax.request({
																				url : sysPath+'/stock/oprPrewiredDetailAction!ajaxTotalSum.action?privilege='+privilege,
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
								    								     form.render();
								    								     if(copyDataStore.getCount()==0){
								    								     	Ext.getCmp("submitbtn").setDisabled(true);
								    								     }else{
								    								     	Ext.getCmp("submitbtn").setDisabled(false);
								    								     }
								    								     
								    								     if(copyDataStore.getCount()==0){
										    								 Ext.getCmp("button2").disable();
										    							 }else{
										    								 Ext.getCmp("button2").enable();
										    							 }
								    								     
								    								     if(dataStore.getCount()==0){
										    								 Ext.getCmp("button1").disable();
										    							 }else{
										    								 Ext.getCmp("button1").enable();
										    							 }
								    							     } 
								    							    }]
													},{
														columnWidth : .3,
														layout : 'form',
														items : [recordGrid2]
													}]
													
										}]
					});
     
     
	

	
		form.render();
		recordGrid.setHeight(Ext.lib.Dom.getViewHeight()-3*Ext.getCmp('twobar').getHeight()-10);
		recordGrid2.setHeight(Ext.lib.Dom.getViewHeight()-3*Ext.getCmp('twobar').getHeight()-10);
		
		function selectDistributionMode(){
			if(Ext.getCmp("dic").getValue()==""){
				Ext.getCmp('showMsg').getEl().update('<span style="color:red">必须先选择配载方式才能查询。</span>');
						Ext.getCmp("dic").markInvalid("必须选择配载方式才能查询");
						Ext.getCmp("dic").focus(true,true);
			//	Ext.MessageBox.alert(alertTitle, '请选择配载方式',function(){
					
			//		});
			}else{
				searchLog();
			}
		
		}
		
		function searchLog(){
			Ext.getCmp('showMsg').getEl().update('');
		  	var checkbox = Ext.get("checkbox").dom.checked;
			if(Ext.getCmp('comboselect').getValue()=="t0_D_NO"||Ext.getCmp('comboselect').getValue()=="t0_PIECE"){
				var num = Ext.getCmp('itemsValue').getValue()
				var regex=/^[1-9]\d*$/;
		      	if(!regex.test(num)){
		       		Ext.Msg.alert(alertTitle,"数字格式输入不正确", function() {			
								return;		
					});
					return;
		        }
			
			}
			var area = Ext.getCmp("area").getValue();
			var dic = Ext.getCmp("dic").getValue();
	//		var  goWhere=Ext.getCmp("goWhere").getValue();
			
			var start = Ext.get('startDate').dom.value;
			var end = Ext.get('endDate').dom.value;
			var ids="";
			if(checkbox){
				if(dataStore.getCount()!=0){
					for(i=0;i<dataStore.getCount();i++){
						ids += dataStore.getAt(i).get('dno') + ",";
						stockStore.add(dataStore.getAt(i));
					}
				}
			}
			
			dataStore.proxy = new Ext.data.HttpProxy({
				method : 'POST',
				url : sysPath+'/stock/oprPrewiredDetailAction!selecList.action?privilege='+privilege
			});	
			var searchCpName = Ext.get('searchCpName').dom.value;
			dataStore.baseParams={
						filter_LIKES_f_cpName:searchCpName,
			 	 		filter_startCount : start,
			    		filter_endCount : end,
			    		filter_countCheckItems:'f.create_time',
			    		filter_OR_f_endDepartId$distributionDepartId:Ext.getCmp('bussDepart').getValue(),
			    		filter_checkItems : Ext.getCmp('comboselect').getValue(),
			    		filter_itemsValue : Ext.getCmp('itemsValue').getValue(),
			    		distributionMode:dic,
			    		filter_f_area:area,
			    		filter_ids:ids,
			    		limit : queryPageSize
			};
			dataStore.reload({
					params : {
						start : 0,
						limit : queryPageSize
					},callback :function(v){
						if(Ext.get("checkbox").dom.checked){
							if(stockStore.getCount()!=0){
								for(i=0;i<stockStore.getCount();i++){
									dataStore.add(stockStore.getAt(i));
								}
							}
						}
						
						if(dataStore.getCount()==0){
				    		 Ext.getCmp("button1").disable();
						}else{
							 Ext.getCmp("button1").enable();
						}

						fnSumInfo();
						
					}
			});
		}

			function changeColour(rowIndex, columnIndex){
				//   grid.getColumnModel().select(rowIndex, columnIndex).getEl().dom.style.color = "red";
						//	alert(rowIndex);
						//	alert(columnIndex);
						// 	alert(recordGrid.getSelectionModel().getCell(rowIndex,columnIndex));
						// recordGrid.getView().getCell(rowIndex,columnIndex).style.backgroundColor='#FF1493';
							//recordGrid.getview().getcell(rowIndex,columnIndex).getEl().dom.style.color ='red';
							
						
						//return "<span style='color:red;font-weight:bold;'>红男</span>";
			}

			/**
			 * 汇总表格
			 */
			function fnSumInfo() {
			
				var area = Ext.getCmp("area").getValue();
				var dic = Ext.getCmp("dic").getValue();
//				var  goWhere=Ext.getCmp("goWhere").getValue();
				var start='';
				var end='';
				var ids="";
				if(Ext.getCmp('startDate').getValue()!=''){
					var start = Ext.getCmp('startDate').getValue().format("Y-m-d");
				}
				if(Ext.getCmp('endDate').getValue()!=''){
					var end = Ext.getCmp('endDate').getValue().format("Y-m-d");
				}
				
				if(dataStore.getCount()!=0){
					for(i=0;i<dataStore.getCount();i++){
						ids += dataStore.getAt(i).get('dno') + ",";
					}
					if(stockStore.getCount()!=0){
						stockStore.removeAll();
					}
					
					Ext.Ajax.request({
						url : sysPath+'/stock/oprPrewiredDetailAction!ajaxTotalSum.action?privilege='+privilege,
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
			}
			
			function sumitStore(){
				Ext.getCmp('showMsg').getEl().update('');
			  var arrayObj3 = new Array();
			  var boolean = true;
 			  var val;
 			  	
 			  if(Ext.getCmp("dicTo1").isVisible()){
 			  	val=Ext.getCmp("dicTo1").getRawValue();
 			  	if(Ext.getCmp("dicTo1").getValue()==""){
 			  	//	Ext.Msg.alert(alertTitle,"预配去向不允许空值", function() {			
							
				//	});
					Ext.getCmp('showMsg').getEl().update('<span style="color:red">预配去向不允许为空，请填写。</span>');
					Ext.getCmp('dicTo1').markInvalid("预配去向不允许空值");
					Ext.getCmp('dicTo1').focus();	
						return;
 			  	}
 			  }
 			  
 			  if(Ext.getCmp("dicTo2").isVisible()){
 			  val=Ext.getCmp("dicTo2").getRawValue();
 			  	if(Ext.getCmp("dicTo2").getValue()==""){
 			  		//Ext.Msg.alert(alertTitle,"预配去向不允许空值", function() {			
							
				//	});
					Ext.getCmp('showMsg').getEl().update('<span style="color:red">预配去向不允许为空，请填写。</span>');
					Ext.getCmp('dicTo2').markInvalid("预配去向不允许空值");
					Ext.getCmp('dicTo2').focus();	
						return;
 			  	}
 			  }
 			  if(Ext.getCmp("dicTo3").isVisible()){
 			  	val=Ext.getCmp("dicTo3").getRawValue();
 			  	if(Ext.getCmp("dicTo3").getValue()==""){
 			  		//Ext.Msg.alert(alertTitle,"预配去向不允许空值", function() {			
							
					//});
					Ext.getCmp('showMsg').getEl().update('<span style="color:red">预配去向不允许为空，请填写。</span>');
					Ext.getCmp('dicTo3').markInvalid("预配去向不允许空值");
					Ext.getCmp('dicTo3').focus();	
						return;
 			  	}
 			  }
 			  if(Ext.getCmp("dicTo4").isVisible()){
 			  	val=Ext.getCmp("dicTo4").getRawValue();
 			  	if(Ext.getCmp("dicTo4").getValue()==""){
 			  	//Ext.Msg.alert(alertTitle,"预配去向不允许空值", function() {			
							
				//	});
					Ext.getCmp('showMsg').getEl().update('<span style="color:red">预配去向不允许为空，请填写。</span>');
					Ext.getCmp('dicTo4').markInvalid("预配去向不允许空值");
					Ext.getCmp('dicTo4').focus();	
						return;
				}
 			  }
				
				if(Ext.getCmp("oprPre").getValue()==''){
					/*Ext.MessageBox.alert(alertTitle, '请选择配载方式',function(){
					});*/
					
					Ext.getCmp('showMsg').getEl().update('<span style="color:red">请选择配载方式。</span>');
					Ext.getCmp("oprPre").markInvalid("必须选择配载方式");
					Ext.getCmp("oprPre").focus(true,true);
					return;
				
				}else if(Ext.getCmp("oprPre").getValue()=='部门交接'){
					for(j=0;j<copyDataStore.getCount();j++){
						if(copyDataStore.getAt(j).get('distributionMode')!='部门交接'){
							//Ext.MessageBox.alert(alertTitle, '存在其他配载方式的货物，不予以提交',function(){
								
							//});
							Ext.getCmp('showMsg').getEl().update('<span style="color:red">配送单号为'+copyDataStore.getAt(j).get('dno')+'的配载方式不是部门交接，不能预配。</span>');
							arrayObj3.push(j);
							boolean=false;
						//	recordGrid2.getSelectionModel().selectRow(j);  
						}
					}
				
				
				}else if(Ext.getCmp("oprPre").getValue()=='外发'){
					for(j=0;j<copyDataStore.getCount();j++){
						if(copyDataStore.getAt(j).get('distributionMode')!='外发'){
							//Ext.MessageBox.alert(alertTitle, '存在其他配载方式的货物，不予以提交',function(){
								
							//});
							Ext.getCmp('showMsg').getEl().update('<span style="color:red">配送单号为'+copyDataStore.getAt(j).get('dno')+'的配载方式不是外发，不能做货物预配。</span>');
							arrayObj3.push(j);
							boolean=false;
						//	recordGrid2.getSelectionModel().selectRow(j);   
						}
					}
				
				}else if(Ext.getCmp("oprPre").getValue()=='中转'){
					for(j=0;j<copyDataStore.getCount();j++){
						if(copyDataStore.getAt(j).get('distributionMode')!='中转'){
							//Ext.MessageBox.alert(alertTitle, '存在其他配载方式的货物，不予以提交',function(){
								
							//});
							Ext.getCmp('showMsg').getEl().update('<span style="color:red">配送单号为'+copyDataStore.getAt(j).get('dno')+'的配载方式不是中转，不能做货物预配。</span>');
							arrayObj3.push(j);
							boolean=false;
						//	recordGrid2.getSelectionModel().selectRow(j);   
						}
					}
				
				}else if(Ext.getCmp("oprPre").getValue()=='市内送货'){
					for(j=0;j<copyDataStore.getCount();j++){
						if(copyDataStore.getAt(j).get('distributionMode')!='市内送货'){
							//Ext.MessageBox.alert(alertTitle, '存在其他配载方式的货物，不予以提交',function(){
							//});
							Ext.getCmp('showMsg').getEl().update('<span style="color:red">配送单号为'+copyDataStore.getAt(j).get('dno')+'的配载方式不是市内送货，不能做货物预配。</span>');
							arrayObj3.push(j);
							boolean=false;
						//	recordGrid2.getSelectionModel().selectRow(j); 
						}
					}	
				}else if(Ext.getCmp("oprPre").getValue()=='专车'){
					for(j=0;j<copyDataStore.getCount();j++){
						if(copyDataStore.getAt(j).get('distributionMode')!='专车'){
							//Ext.MessageBox.alert(alertTitle, '存在其他配载方式的货物，不予以提交',function(){
							//});
							Ext.getCmp('showMsg').getEl().update('<span style="color:red">配送单号为'+copyDataStore.getAt(j).get('dno')+'的配载方式不是专车，不能做货物预配。</span>');
							arrayObj3.push(j);
							boolean=false;
						//	recordGrid2.getSelectionModel().selectRow(j); 
						}
					}	
				}
				
				recordGrid2.getSelectionModel().selectRows(arrayObj3);   
				
				if(boolean){
					if(copyDataStore.getCount()!=0){
									var ids4="";
									var str="";
									var orderCheckbox = Ext.get("orderCheckbox").dom.checked;
									
									for(i=0;i<copyDataStore.getCount();i++){
										ids4 += copyDataStore.getAt(i).get('dno') + ",";
									}
									
									if(orderCheckbox){
										    		var orderdata=[
												    		['ID','预配明细单号'],
												    		['PIECE','传真件数'],
												    		['WEIGHT','传真重量'],
												    		['CONSIGNEE','收货人'],
												    		['ADDR','收货人地址'],
												    		['GOWHERE','去向'],
												    		['FLIGHT_NO','航班号']];
												    	var myReader = new Ext.data.ArrayReader({}, [
												    	{name:'orderId'},
														{name: 'orderName'}
														]);
														 var orderStore=new Ext.data.Store({
															data: orderdata,
															reader: myReader
														});
														 
														 var orderdata1=[
												    		['D_NO','配送单号']];
												    	var myReader1 = new Ext.data.ArrayReader({}, [
												    	{name:'orderId'},
														{name: 'orderName'}
														]);
														 var orderStore1=new Ext.data.Store({
															data: orderdata1,
															reader: myReader1
														});
														 var orderGridpanl = new Ext.grid.GridPanel( {
															id : 'myrecordGrid2',
															height : 300,
															width : Ext.lib.Dom.getViewWidth(),
															autoScroll : true,
															autoExpandColumn : 1,
															frame : false,
															loadMask : true,
															stripeRows : true,
															viewConfig : {
																forceFit : true,
																scrollOffset : 0
															},
															cm : new Ext.grid.ColumnModel( [
																	new Ext.grid.CheckboxSelectionModel(), {
																		header : '备选排序字段',
																		dataIndex : 'orderId',
																		disabled : true,
																		hidden : true
																	}, {
																		header : '备选排序字段',
																		dataIndex : 'orderName'
																	} ]),
															sm : new Ext.grid.CheckboxSelectionModel(),
															ds : orderStore
														});
														  var orderGridpanl2 = new Ext.grid.GridPanel( {
															id : 'myrecordGri',
															height : 300,
															width : Ext.lib.Dom.getViewWidth(),
															autoScroll : true,
															autoExpandColumn : 1,
															frame : false,
															loadMask : true,
															stripeRows : true,
															viewConfig : {
																forceFit : true,
																scrollOffset : 0
															},
															cm : new Ext.grid.ColumnModel( [
																	new Ext.grid.CheckboxSelectionModel(), {
																		header : '排序字段',
																		dataIndex : 'orderId',
																		disabled : true,
																		hidden : true
																	}, {
																		header : '排序字段',
																		dataIndex : 'orderName'
																	} ]),
															sm : new Ext.grid.CheckboxSelectionModel(),
															ds : orderStore1
														});
											    		var form = new Ext.form.FormPanel({
																frame : true,
																border : false,
																bodyStyle : 'padding:5px 5px 5px',
																labelWidth : 70,
																labelAlign :'right',
																width : 320,
																items:[{xtype: 'radiogroup',
													                fieldLabel: '排序顺序',
													                columns: 3,
													                anchor : '95%',
													                id:"isBoardStatus",
													                defaults: {
													                    name: 'isBoardStatus' 
													                },
													                listeners : {
													                },
													                items: [{
													                    inputValue: '1',
													                    boxLabel: '正序',
													                    checked:true
													                },{
													                    inputValue: '0',
													                    boxLabel: '倒序'
													                }]},{
																	 layout : 'column',
																	 items:[
																	 {
																		 layout:'column',
																		 columnWidth:.4,
																		 items:[
																			 orderGridpanl
																			]
																		 },{
											    						 layout:'column',
											    						 buttonAlign:'center',
											    						 columnWidth:.2,
											    						 items:[
											    							 {html : '<pre><p></p><p> &nbsp;</p><p> &nbsp;</p><p> </p><p> </p><p> </p><p> </p><p> </p></pre>'},
											    							 {xtype:'button',
											    							  text:'==>>',
											    							  width:50,
											    							  handler:function(){
											    								 var order1=orderGridpanl.getSelectionModel().getSelections();
											    								 for(var i=0;i<order1.length;i++){
											    									 orderStore.remove(order1[i]);
											    								 }
											    								 orderStore1.add(order1);
											    								 form.render();
											    							  }
											    							  },
											    								{html:'<pre><p> </p></pre>'},
											    							    {xtype:'button',
											    							     text:'<<==',
											    							     width:50,
											    							     handler:function(){
											    								     var order2=orderGridpanl2.getSelectionModel().getSelections();
											    								     for(var i=0;i<order2.length;i++){
											    									     orderStore1.remove(order2[i]);
											    								     }
											    								     orderStore.add(order2);
											    								     form.render();
											    							     } 
											    							    }
											    							 ]
											    						},{
											    						 layout:'column',
											    						 columnWidth:.4,
											    						 items:[
											    							orderGridpanl2
											    							 ]
											    						}]
																	}]
											    		});
											    		var win = new Ext.Window({
														title : '添加排序信息',
														width : 350,
														closeAction : 'hide',
														plain : true,
														modal : true,
														items : form,
														buttonAlign : "center",
														buttons : [{
															text : "预配保存",
															iconCls : 'save',
															handler : function() {
															    var _record=recordGrid.getSelectionModel().getSelections();
															    orderGridpanl2.getSelectionModel().selectAll();
															    var record=orderGridpanl2.getSelectionModel().getSelections();
															    var orderName="";
							
															    for(var i=0;i<record.length;i++){
															 	    if(i==0) { orderName=record[i].data.orderId;}
															    	if(i!=0) orderName=orderName+","+record[i].data.orderId;
															    }
																if(form.getForm().getValues()["isBoardStatus"]=="1"){
																	for(var i=0;i<record.length;i++){
															 	    	if(i==0) { orderName=record[i].data.orderId+" ASC ";}
															    		if(i!=0) orderName=orderName+","+record[i].data.orderId+" ASC ";
															    	}
																}else{
																	for(var i=0;i<record.length;i++){
															 	    	if(i==0) { orderName=record[i].data.orderId+" DESC ";}
															    		if(i!=0) orderName=orderName+","+record[i].data.orderId+" DESC ";
															    	}
																}

																form1.getForm().doAction('submit', {
																	url : sysPath+'/stock/oprPrewiredDetailAction!saveOprPrewired.action?privilege='+privilege,
																	method : 'post',
																	params:{
																		peopleName:val,
																	    distributionMode:Ext.getCmp("oprPre").getRawValue(),
																	    ids:ids4,
																	    orderFields:orderName,
																	    totalPiece:Ext.getCmp("totalNum").getValue(),
																	    totalDno:Ext.getCmp("dno").getValue(),
																	    totalWeight:Ext.getCmp("weight").getValue()
																	},
																	waitMsg : '正在保存数据...',
																	success : function(form1, action) {
																		win.close();
																		Ext.Msg.alert(alertTitle,action.result.msg,
																				function() {
																					copyDataStore.removeAll();
																					parent.print('2',{print_prewiredId:action.result.value});
																				});
																	},
																	failure : function(form1, action) {
																		win.close();
																		Ext.Msg.alert(alertTitle,action.result.msg);
																	}
																});
															}
														},{
															text : "取消",
															handler : function() {
																win.close();
														 }
														}]
													});
													win.on('hide', function() {
																form.destroy();
															});
													win.show();
									}else{
										Ext.Msg.confirm(alertTitle,'您确定要提交这<span style="color:red">&nbsp;'+ copyDataStore.getCount() + '&nbsp;</span>行数据吗?',function(btnYes) {
											if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
												
												form1.getForm().doAction('submit', {
													url : sysPath+'/stock/oprPrewiredDetailAction!saveOprPrewired.action?privilege='+privilege,
													method : 'post',
													params:{
														peopleName:val,
													    distributionMode:Ext.getCmp("oprPre").getRawValue(),
													    ids:ids4,
													    totalPiece:Ext.getCmp("totalNum").getValue(),
													    totalDno:Ext.getCmp("dno").getValue(),
													    totalWeight:Ext.getCmp("weight").getValue()
													},
													waitMsg : '正在保存数据...',
													success : function(form1, action) {
														Ext.Msg.alert(alertTitle,action.result.msg,
																function() {
																	copyDataStore.removeAll();
																	parent.print('2',{print_prewiredId:action.result.value});
																});
													},
													failure : function(form1, action) {
														Ext.Msg.alert(alertTitle,action.result.msg);
													}
												});
											
											}
										});
									}
									
					}else{
						//Ext.MessageBox.alert(alertTitle, '无记录保存！');
						Ext.getCmp('showMsg').getEl().update('<span style="color:red">无数据预配。</span>');
			
					}
				}
			}

});
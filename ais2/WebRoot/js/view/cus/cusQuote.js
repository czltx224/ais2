var fiveWidth=110;
var form;
//运输方式Store
	var areaFields=[
			{name:'id'},
            {name: 'areaName'},
            {name: 'areaType'},
            {name: 'distriDepartId'},
            {name: 'distriDepartName'},
            {name: 'endDepartId'},
            {name: 'endDepartName'},
            {name: 'develpMode'},
            {name:'parentId'},
            {name:'cusName'},
            {name:'parentName'},
            {name:'areaRank'}
	];
	//提货方式Store
	var takeModeStore=new Ext.data.Store({
		storeId:"payerStore",
		baseParams:{filter_EQL_basDictionaryId:14,privilege:16},
		proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/dictionaryAction!ralaList.action"}),
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
        	{name:'id'},
        	{name:'takeMode',mapping:'typeName'}
        	])
	});
	var trafficModeStore=new Ext.data.Store({
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

	//客商Store
	var cusStore=new Ext.data.Store({
		storeId:"cusStore",
		baseParams:{privilege:61,filter_EQS_custprop:'发货代理'},
		proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/customerAction!ralaList.action"}),
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
        	{name:'id'},
        	{name:'cusName'}
        	])
	});
	//提送付款方Store
	var payerStore=new Ext.data.Store({
		storeId:"payerStore",
		baseParams:{filter_EQL_basDictionaryId:17,privilege:16},
		proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/dictionaryAction!ralaList.action"}),
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
        	{name:'id'},
        	{name:'whoCash',mapping:'typeName'}
        	])
	});
	//计费方式Store
	var chargeModeStore=new Ext.data.Store({
		storeId:"chargeModeStore",
		proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/dictionaryAction!ralaList.action"}),
		baseParams:{filter_EQL_basDictionaryId:20,privilege:16},
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
        	{name:'id'},
        	{name:'chargeMode',mapping:'typeName'}
        	])
	});
	//专车车型Store
	var carTypeStore=new Ext.data.Store({
		storeId:"carTypeStore",
		proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/dictionaryAction!ralaList.action"}),
		baseParams:{filter_EQL_basDictionaryId:21,privilege:16},
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
        	{name:'id'},
        	{name:'carType',mapping:'typeName'}
        	])
	});
	//专车路型Store
	var roadTypeStore=new Ext.data.Store({
		storeId:"roadTypeStore",
		proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/dictionaryAction!ralaList.action"}),
		baseParams:{filter_EQL_basDictionaryId:22,privilege:16},
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
        	{name:'id'},
        	{name:'roadType',mapping:'typeName'}
        	])
	});
	//地区 市Store
	var areaCityStore=new Ext.data.Store({
		storeId:"areaCityStore",
		proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/basAreaAction!ralaList.action"}),
		baseParams:{privilege:55,limit:pageSize,filter_LIKES_areaRank:'市'},
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },areaFields)
	});
	
	//地区 区/县Store
	var areaTownStore=new Ext.data.Store({
		storeId:"areaTownStore",
		proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/basAreaAction!ralaList.action"}),
		baseParams:{privilege:55,limit:pageSize,filter_LIKES_areaRank:'区'},
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },areaFields)
	});
	//地区镇/街道Store
	var areaStreetStore=new Ext.data.Store({
		storeId:"areaStreetStore",
		proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/basAreaAction!ralaList.action"}),
		baseParams:{privilege:55,limit:pageSize,filter_LIKES_areaRank:'街道'},
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },areaFields)
	});
Ext.onReady(function() {
	var domWidth=Ext.lib.Dom.getViewWidth()-15;
	var scomWidth=domWidth/4;
var tb = new Ext.Toolbar({
	width : Ext.lib.Dom.getViewWidth(),
	id : 'faxToolbar',
	items : ['&nbsp;&nbsp;', {
				text : '<B>查询</B>',
				id : 'addDetailBtn',
				tooltip : '查询价格',
				iconCls : 'groupAdd',
				handler : function() {
					searchRate();
				}
			},'-',{
				text:'<b>计算金额</>',
				id : 'countFee',
				tooltip : '计算金额',
				iconCls : 'groupAdd',
				handler : function() {
					 saveCusRecord();
				}
				
			},'-',{
    			xtype:'label',
    			id:'showMsg',
    			width:380
	}]
});
		form = new Ext.form.FormPanel({
			id:'addfaxform',
			frame : true,
			renderTo:Ext.getBody(),
			labelAlign : "right",
			bodyStyle:'padding:0px 0px 0px 0px',
			labelWidth : 60,
			width:Ext.lib.Dom.getViewWidth(),
			height:Ext.lib.Dom.getViewHeight(),
			tbar:tb,
			items:[{
				xtype:'fieldset',
				id:'setmsg',
				layout:'table',
				style:'margin:2px;',
				layoutConfig: {columns:4},
				bodyStyle:'padding:5px 0px 0px 0px',
				defaults:{border:false,layout:'form',frame:false,labelAlign:'right',labelWidth:65},
				items:[{
						colspan:1,
						width:scomWidth,
						labelWidth:80,
						items:[{
							xtype:'textfield',
							name:'linkTel',
							id:'linkTel',
							width:fiveWidth,
							fieldLabel:'电话',
							listener:{
								blur:function(textfield){
									Ext.Ajax.request({
										url:sysPath+'/cus/cusLinkManAction!ralaList.action',
										params:{
											filter_EQS_tel:textfield.getValue()
										},
										success:function(resp){
											var respText = Ext.util.JSON.decode(resp.responseText);
											var cusRid=respText1.result[0].cusRecordId;
											Ext.Ajax.request({
												url:sysPath+'/cus/cusRecordAction!list.action',
												params:{
													filter_EQL_id:cusRid,
													filter_EQS_isCq:'发货代理'
												},
												success:function(resp1){
													var respText1 = Ext.util.JSON.decode(resp1.responseText);
													if(resp1.result.length>0){
														Ext.getCmp('cuscombo').setValue(respText1.result[0].cusId);
														Ext.getCmp('cuscombo').setRawValue(respText1.result[0].cusName);
													}
												}
											});
										}
									});
								}
							}
						}]
					},
					{
						colspan:1,
						width:scomWidth,
						labelWidth:80,
						items:[{
							xtype:'combo',
							fieldLabel:'代理公司',
							triggerAction :'all',
							model : 'local',
	    					id:'cuscombo',
	    					resizable : true,
	    					width:fiveWidth,
	    					pageSize:pageSize,
	    					minChars : 0,
	    					store:cusStore,
	    					queryParam :'filter_LIKES_cusName',
	    					valueField :'id',
							displayField :'cusName',
							name : 'cusId',
							emptyText : '选择类型',
	    					forceSelection : true
						}]
					},{
						colspan:1,
						width:scomWidth,
						labelWidth:80,
						items:[{
							xtype:'combo',
							fieldLabel:'结束地址<span style="color:red">*</span>',
							triggerAction :'all',
							model : 'local',
	    					id:'endCity',
	    					resizable : true,
	    					width:fiveWidth,
	    					editable:false,
	    					minChars : 0,
	    					value:'广州市',
	    					store:[
	    						['广州市','广州市'],
	    						['深圳市','深圳市']
	    					],
							name : 'endCity'
						}]
					},
					{
				  	colspan:1,
					width:scomWidth,
					labelWidth:80,
					items:[
						{
							xtype:'combo',
							id:'city',
							name:'city',
							minChars : 0,
							triggerAction : 'all',
							pageSize:pageSize,
							width:fiveWidth,
							valueField : 'areaName',
							queryParam : 'filter_LIKES_areaName',
							store:areaCityStore,
							displayField : 'areaName',
							fieldLabel:'市<span style="color:red">*</span>',
							forceSelection:true,
							enableKeyEvents:true,
							allowBlank:false,
							emptyText:'请选择',
							blankText:'所在市不能为空!!',
	    					listeners : {
						 		select :function(combo,e){
						 			Ext.getCmp('town').clearValue();
						 			var parentId=combo.getValue();		 		
						 			Ext.apply(areaTownStore.baseParams,{
						 				filter_EQS_parentName:parentId
						 			});
						 			Ext.getCmp('town').store.load();
						 		}
				 			}
					}]	
				},{
					colspan:1,
					labelWidth:80,
					width:scomWidth,
					items:[{
						xtype:'combo',
						id:'town',
						name:'town',
						model:'remote',
						minChars : 0,
						triggerAction : 'all',
						typeAhead : true,
						fieldLabel:'区/县<span style="color:red">*</span>',
						queryParam : 'filter_LIKES_areaName',
						pageSize:pageSize,
						store:areaTownStore,
						valueField : 'areaName',
						forceSelection:true,
						displayField : 'areaName',
						allowBlank:false,
						emptyText:'请选择',
						width:fiveWidth,
						blankText:'所在县不能为空!!',
						enableKeyEvents:true,
    					 listeners : {
    					 	select:function(combo,record,index){
    					 		Ext.getCmp('street').clearValue();
					 			var parentId=combo.getValue();
					 			Ext.apply(areaStreetStore.baseParams,{
					 				filter_EQS_parentName:parentId
					 			});
					 			Ext.getCmp('street').store.load();
					 		},
					 		blur:function(combo){
								Ext.Ajax.request({
									url : sysPath + "/sys/basAreaAction!ralaList.action",
									params : {
										filter_EQS_areaName:combo.getValue(),
										filter_LIKES_areaRank:'区',
										privilege:55
									},
									success:function(resp1){
										var respText1 = Ext.util.JSON.decode(resp1.responseText);
										setaddrmsg(respText1);
									}
								});					 		
					 		}
			 			}
					}]
				},{
					colspan:1,
					labelWidth:80,
					width:200,
					items:[{
						xtype:'combo',
						fieldLabel:'镇/街道',
						id:'street',
						name:'street',
						model:'remote',
						minChars : 0,
						triggerAction : 'all',
						forceSelection:true,
						hideTrigger:true,
						typeAhead : true,
						store:areaStreetStore,
						queryParam : 'filter_LIKES_areaName',
						pageSize:pageSize,
						valueField : 'areaName',
						displayField : 'areaName',
						emptyText:'请选择',
						width:fiveWidth,
						enableKeyEvents:true,
    					 listeners : {
					 		blur:function(combo){
									var aRank='街道';
									var streetId=combo.getValue();
									if(streetId==''){
										streetId=Ext.getCmp('town').getValue();
										aRank='区';
									}
								 	Ext.Ajax.request( {
									    url : sysPath + "/sys/basAreaAction!ralaList.action",
										params : {
											filter_EQS_areaName:streetId,
											filter_LIKES_areaRank:aRank,
											privilege:55
										},
										success : function(resp) {
											var respText = Ext.util.JSON.decode(resp.responseText);
											if(respText.result.length<1){
												Ext.Ajax.request({
													url : sysPath + "/sys/basAreaAction!ralaList.action",
													params : {
														filter_EQS_areaName:Ext.getCmp('town').getValue(),
														filter_LIKES_areaRank:'区',
														privilege:55
													},
													success:function(resp1){
														var respText1 = Ext.util.JSON.decode(resp1.responseText);
														Ext.getCmp('showMsg').getEl().update('<span style="color:red">您输入的街道在地区资料里面不存在!</span>');
														setaddrmsg(respText1);
													}
												});
											}else{
												setaddrmsg(respText);
											}
											
										}
									});			 			
					 		}
			 			}
					}]
				},{
					colspan:1,
					width:scomWidth,
					labelWidth:80,
					items:[{
						xtype:'textfield',
						fieldLabel:'公布价',
						readOnly:true,
						id:'publicRate',
						name:'publicRate',
						width:fiveWidth
					}]
				},{
					colspan:1,
					width:scomWidth,
					labelWidth:80,
					items:[{
						xtype:'textfield',
						fieldLabel:'协议价',
						readOnly:true,
						id:'corporate',
						name:'corporate',
						width:fiveWidth
					}]
				}]
			},
			{
				xtype:'fieldset',
				id:'setshowmsg',
				layout:'table',
				style:'margin:2px;',
				layoutConfig: {columns:4},
				bodyStyle:'padding:5px 0px 0px 0px',
				defaults:{border:false,layout:'form',frame:false,labelAlign:'right',labelWidth:65},
				items:[	
						{
							colspan:1,
							labelWidth:80,
							width:scomWidth,
							items:[{
								xtype:'combo',
								fieldLabel:'运输方式',
								triggerAction : 'all',
								queryParam : 'filter_LIKES_typeName',
		    					model : 'local',
		    					id:'trafficModecombo',
		    					valueField : 'trafficMode',
								displayField : 'trafficMode',
								name : 'trafficMode',
								width:fiveWidth,
								store:trafficModeStore,
								value:'空运',
		    					forceSelection : true
							}]
						},
						{
							colspan:1,
							labelWidth:80,
							width:scomWidth,
							items:[{
								xtype:'combo',
								fieldLabel:'提货方式',
								triggerAction : 'all',
								width:fiveWidth,
						    	id:'takeModecombo',
						    	queryParam : 'filter_LIKES_typeName',
						    	valueField : 'takeMode',
			    				displayField : 'takeMode',
			    				name : 'takeMode',
								store:takeModeStore,
								emptyText : '选择类型',
								editable :false,
								value:'市内送货',
						    	forceSelection : true
							}]
						},{
							colspan:1,
							labelWidth:80,
							width:scomWidth,
							items:[{
								xtype:'combo',
								fieldLabel:'计费方式',
								triggerAction : 'all',
								name:'valuationType',
		    					id:'chargemodecombo',
		    					queryParam : 'filter_LIKES_typeName',
		    					valueField : 'chargeMode',
								displayField : 'chargeMode',
								store:chargeModeStore,
								emptyText : '选择类型',
								value:'重量',
								editable :false,
		    					forceSelection : true,
		    					allowBlank : false,
		    					blankText:'计费方式不能为空!',
								width:fiveWidth,
								enableKeyEvents:true
							}]
						},{
							colspan:1,
							labelWidth:80,
							width:scomWidth,
							items:[{
								xtype:'textfield',
								fieldLabel:'件数',
								allowBlank : false,
								width:fiveWidth,
								id:'piece',
								name:'piece',
		    					maxLength:7,
								enableKeyEvents:true
							}]
						},{
							colspan:1,
							labelWidth:80,
							width:scomWidth,
							items:[{
								xtype:'numberfield',
								id:'cusWeight',
								name:'cusWeight',
								fieldLabel:'计费重量',
								allowBlank : false,
								width:fiveWidth,
								maxValue:99999.99,
								maxLength:8,
								enableKeyEvents:true
							}]
						},{
							colspan:1,
							labelWidth:80,
							width:scomWidth,
							items:[{
								xtype:'numberfield',
								id:'bulk',
								name:'bulk',
								fieldLabel:'体积m³',
								maxValue:99999.99,
								maxLength:8,
								width:fiveWidth,
								value:0,
								enableKeyEvents:true
							}]
						},{
					colspan:1,
					width:scomWidth,
					labelWidth:80,
					items:[{
						xtype:'textfield',
						fieldLabel:'配送方式',
						readOnly:true,
						id:'distributionMode',
						name:'distributionMode',
						width:fiveWidth
					}]
				},{
					colspan:1,
					width:scomWidth,
					labelWidth:80,
					items:[{
						xtype:'textfield',
						fieldLabel:'地址类型',
						readOnly:true,
						id:'areaType',
						name:'areaType',
						width:fiveWidth
					}]
				},{
					colspan:1,
					width:scomWidth,
					labelWidth:80,
					items:[{
						xtype:'textfield',
						fieldLabel:'供应商',
						readOnly:true,
						id:'goWhere',
						name:'goWhere',
						width:fiveWidth
					}]
				},{
					colspan:1,
					width:scomWidth,
					labelWidth:80,
					items:[{
						xtype:'combo',
						fieldLabel:'付款方',
						width:fiveWidth,
						triggerAction : 'all',
    					id:'payerCombo',
    					name:'whoCash',
    					queryParam : 'filter_LIKES_typeName',
    					valueField : 'whoCash',
						displayField : 'whoCash',
						hiddenName : 'whoCash',
						store:payerStore,
						emptyText : '选择类型',
						editable :false,
						value:'到付',
    					forceSelection : true,
    					allowBlank : false,
    					enableKeyEvents:true
					}]
				},{
						colspan:1,
						labelWidth:80,
						width:scomWidth,
						items:[{
							xtype:'numberfield',
							name:'traFeeRate',
							id:'traFeeRate',
							fieldLabel:'中转费率',
							readOnly:true,
							width:fiveWidth
						}]
					},{
						colspan:1,
						labelWidth:80,
						width:scomWidth,
						items:[{
							xtype:'numberfield',
							fieldLabel:'中转费',
							name:'traFee',
							id:'traFee',
							maxValue:99999999.99,
							maxLength:11,
							readOnly:true,
							width:fiveWidth
						}]
					},{
						colspan:1,
						labelWidth:80,
						width:scomWidth,
						items:[{
							xtype:'numberfield',
							fieldLabel:'预付提送费',
							maxValue:99999999.99,
							maxLength:11,
							id:'cpFee',
							name:'cpFee',
							readOnly:true,
							width:fiveWidth,
							enableKeyEvents:true
						}]
					},{
							colspan:1,
							labelWidth:80,
							width:scomWidth,
							items:[{
								xtype:'numberfield',
								id:'consigneeFee',
								name:'consigneeFee',
								fieldLabel:'到付提送费',
								readOnly:true,
								maxValue:99999999.99,
								maxLength:11,
								width:fiveWidth
								
							}]
						}
				]
			},
			{
				xtype:'fieldset',
				id:'setsongder',
				layout:'table',
				style:'margin:2px;',
				layoutConfig: {columns:4},
				bodyStyle:'padding:5px 0px 0px 0px',
				defaults:{border:false,layout:'form',frame:false,labelAlign:'right',labelWidth:65},
				items:[
					{	
						colspan:1,
						labelWidth:80,
						width:scomWidth,
						items:[{xtype:'hidden',name:'sonderzug',id:'sonderzugVal',value:'0'},{
							xtype:'checkbox',
							fieldLabel:'专车送货',
							id:'sonderzug',
							name:'sonderzugs',
							boxLabel:'',
							width:30,
							inputValue:'0',
							enableKeyEvents: true, 
							listeners : {
						 		specialkey:function(checkbox, e){
					               if(e.getKey() == 17){
					            	   if(form.findByType("checkbox")[0].getValue()){
					            		   Ext.getCmp('sonderzug').setValue(false);
					            	   }else{
					            		   Ext.getCmp('sonderzug').setValue(true);
					            	   }
					               }else if(e.getKey()==13){
					            	    Ext.getCmp('cartypecombo').focus(true,true);
					               }
						 		},check:function(checkbox,flag){
						 			var comcity=Ext.getCmp('city');
								 	var comtown=Ext.getCmp('town');
								 	var comstreet=Ext.getCmp('street');
								 	var comdis=Ext.getCmp('distributionMode');
								 	var comaddr=Ext.getCmp('consigneeAddress');
								 	var comaddrType=Ext.getCmp('areaType');
						 			if(flag){
						 				 Ext.getCmp('sonderzugVal').setValue(1);
						 				 Ext.getCmp('cartypecombo').setDisabled(false);
						 				 Ext.getCmp('roadtypecombo').setDisabled(false);
						 				 Ext.getCmp('sonderzugPrice').setDisabled(false);
						 				 
						 				 Ext.getCmp('takeModecombo').setValue('机场自提');
								 		 comdis.setValue('新邦');
								 		 comaddrType.setValue('市内');
						 				 Ext.getCmp('takeModecombo').getEl().dom.readOnly=true;
						 				 
						 				 Ext.getCmp('distributionMode').setValue('新邦');
						 			}else{
						 				Ext.getCmp('sonderzugVal').setValue(0);
						 				Ext.getCmp('cartypecombo').setDisabled(true);
						 				Ext.getCmp('roadtypecombo').setDisabled(true);
						 				Ext.getCmp('sonderzugPrice').setDisabled(true);
						 				Ext.getCmp('takeModecombo').getEl().dom.readOnly=false;
						 				
						 			}
						 		}
				 			}
					}]
					},
					{
						colspan:1,
						width:scomWidth,
						labelWidth:80,
						items:[{
							xtype:'combo',
							fieldLabel:'车型',
							width:fiveWidth,
							disabled:true,
							triggerAction : 'all',
	    					id:'cartypecombo',
	    					name:'carType',
	    					queryParam : 'filter_LIKES_typeName',
	    					valueField : 'carType',
							displayField : 'carType',
							hiddenName : 'carType',
							store:carTypeStore,
							emptyText : '选择类型',
	    					forceSelection : true,
	    					allowBlank : false,
	    					enableKeyEvents: true
						}
						]
					},
					{
						colspan:1,
						width:scomWidth,
						labelWidth:80,
						items:[{
							xtype:'combo',
							fieldLabel:'路型',
							disabled:true,
							triggerAction : 'all',
	    					id:'roadtypecombo',
	    					name:'roadType',
	    					queryParam : 'filter_LIKES_typeName',
	    					valueField : 'roadType',
							displayField : 'roadType',
							hiddenName : 'roadType',
							store:roadTypeStore,
							emptyText : '选择类型',
	    					forceSelection : true,
	    					allowBlank : false,
							width:fiveWidth,
							enableKeyEvents: true
						}]
					},
					{
						colspan:1,
						width:scomWidth,
						labelWidth:80,
						items:[{xtype:'hidden',name:'normSonderzugPrice',id:'normSonderzugPrice'},{
							xtype:'textfield',
							id:'sonderzugPrice',
							name:'sonderzugPrice',
							disabled:true,
							readOnly:true,
							width:fiveWidth,
							fieldLabel:'专车费'
							}
						]
					}
				]
			}],
			listeners:{
				render:function(form){
					if(parent.cusRecordId!=null){
						Ext.getCmp('linkTel').setDisabled(true);
						//Ext.getCmp('cusId').setDisabled(false);
						Ext.getCmp('cuscombo').setValue(parent.cusName);
						//Ext.getCmp('cuscombo').setRawValue(parent.cusName);
						//Ext.getCmp('cusId').setValue(parent.cusId);
						Ext.getCmp('cuscombo').setDisabled(true);
					}else{
						Ext.getCmp('linkTel').setDisabled(false);
						Ext.getCmp('cuscombo').setDisabled(false);
						//Ext.getCmp('cusId').setDisabled(true);
					}
				}
			}
	});			
});

function searchRate(){
	var town = Ext.getCmp('town').getValue();
	var street = Ext.getCmp('street').getValue();
	var city = Ext.getCmp('city').getValue();
	var roadType = Ext.getCmp('roadtypecombo').getValue();
	var cusId = Ext.getCmp('cuscombo').getValue();
	if(parent.cusRecordId!=null){
		cusId=parent.cusId;
	}
	var addrType = Ext.getCmp('areaType').getValue();
	var startCity = Ext.getCmp('endCity').getValue();
	var valuationType = Ext.getCmp('chargemodecombo').getValue();
	var trafficMode = Ext.getCmp('trafficModecombo').getValue();
	var whoCash=Ext.getCmp('payerCombo').getValue();
	var takeMode = Ext.getCmp('takeModecombo').getValue();
	var distributeMode = Ext.getCmp('distributionMode').getValue();
		Ext.Ajax.request({
			url : sysPath
					+ "/fi/cqStCorporateRateAction!findRate.action",
			params : {
				limit : pageSize,
				takeMode : takeMode,
				trafficMode : trafficMode,
				distributionMode : distributeMode,
				addressType : addrType,
				departId : bussDepart,
				startCity : startCity,
				city : city,
				town : town,
				street : street
			},
			success : function(resp2) {
				var respText2 = Ext.util.JSON
						.decode(resp2.responseText);
				// 该目的站没有公布价
				if (respText2.result.length < 1) {
					Ext.Ajax.request({
						url : sysPath
								+ "/fi/cqStCorporateRateAction!list.action",
						params : {
							filter_EQS_takeMode : takeMode,
							filter_EQS_trafficMode : trafficMode,
							filter_EQS_distributionMode : distributeMode,
							filter_EQL_status : 2,
							limit : pageSize,
							filter_EQS_addressType : addrType,
							filter_EQL_departId : bussDepart,
							filter_EQS_startAddr : startCity,
							filter_LED_startDate : new Date()
									.format('Y-m-d')
						},
						success : function(resp3) {
							// 无此条件的公布价
							var respText3 = Ext.util.JSON
									.decode(resp3.responseText);
							if (respText3.result.length < 1) {
								//Ext.getCmp('cpRate').setValue(0);
								Ext.getCmp('cpFee').setValue(0);
								Ext.Msg.alert(alertTitle,'无公布价，请尽快维护!!');
								Ext.getCmp('showMsg').getEl().update('<span style="color:red">运输方式为:'+trafficMode+',提货方式为:'+takeMode+',配送方式为:'+distributeMode+',地址类型为:'+addrType+',始发站为:'+startCity+'的公布价不存在！</span>');
								return false;
							} else {
								Ext.getCmp('publicRate').setValue(respText3.result[0].stage1Rate);
								Ext.getCmp('corporate').setValue(0);									
							}
						}
					});
				} else {
					Ext.getCmp('publicRate').setValue(respText2.result[0].stage1Rate);
					Ext.getCmp('corporate').setValue(0);									
				}
			}
		});
		if(cusId!=''){
			// 付款方为预付
			if (whoCash == '预付'||whoCash=='双方付') {
				if(cusId==''||cusId==null){
					Ext.Msg.alert(alertTitle,'代理公司为空，不能计算代理协议价，全部取公布价',onfocus);
					function onfocus(){
						return false;
					}
				}else{
				var piece=Ext.getCmp('piece').getValue();
				var bulk=Ext.getCmp('bulk').getValue();
				var cqWeight=Ext.getCmp('cusWeight').getValue();
				// 判断是否为项目客户
				Ext.Ajax.request({
					url : sysPath + "/sys/customerAction!ralaList.action",
					params : {
						privilege : 61,
						filter_EQL_id:cusId,
						filter_EQL_isProjectcustomer:1
					},
					success : function(res) {
						var resText = Ext.util.JSON.decode(res.responseText);
						// 是项目客户
						if (resText.result.length > 0) {
							Ext.Ajax.request({
								url:sysPath+"/fi/basProjectRateAction!findRate.action",
								params:{
									piece:piece,
									weight:cqWeight,
									cusId:cusId,
									bulk:bulk,
									city:city,
									town:town
								},
								success:function(respp){
									var resppText = Ext.util.JSON.decode(respp.responseText);
									if(resppText.resultMap.length<1){
										Ext.getCmp('cpFee').setValue(0);
										Ext.Msg.alert(alertTitle,'此项目客户的协议价格不存在，请尽快维护!!');
										Ext.getCmp('showMsg').getEl().update('<span style="color:red">件数为:'+piece+',重量为:'+cqWeight+',体积为:'+bulk+'的项目客户协议价格不存在！</span>');
										return false;
									}else{
										var valuaType=resppText.resultMap[0].COUNT_WAY;
										var prate=Number(resppText.resultMap[0].RATE);
										var plowFee=Number(resppText.resultMap[0].LOW_FEE);
										var pprice=0;
										if(valuaType=='件数'){
											pprice=Number(Ext.getCmp('piece').getValue())*prate;
										}else if(valuaType='重量'){
											pprice=cqWeight*prate;
										}
										else if(valuaType='体积'){
											pprice=Number(Ext.getCmp('bulk').getValue())*prate;
										}
										if(pprice<plowFee){
											pprice=plowFee;
										}
										cashPrice=pprice;
										Ext.getCmp('chargemodecombo').setValue(valuaType);
										Ext.getCmp('corporate').setValue(prate);
										//Ext.getCmp('publicRate').setValue(0);
										Ext.getCmp('cpFee').setValue(pprice);
										Ext.getCmp('showMsg').getEl().update('<span style="color:red">此代理为项目客户，已找到项目客户协议价！价格编号:'+resppText.resultMap[0].ID+'</span>');
									}
								}
							});
						} else {
							// 取代理协议价
							Ext.Ajax.request({
								url : sysPath + "/fi/cqCorporateRateAction!findRate.action",
								params : {
									valuationType : valuationType,
									cusId : cusId,
									limit : pageSize,
									takeMode : takeMode,
									trafficMode : trafficMode,
									distributionMode : distributeMode,
									addressType : addrType,
									departId : bussDepart,
									startCity : startCity,
									city : city,
									town : town,
									street : street
								},
								success : function(resp) {
									var respText = Ext.util.JSON.decode(resp.responseText);
									// 该代理目的站没有协议价格
									if (respText.result.length < 1) {
										Ext.Ajax.request({
											url : sysPath + "/fi/cqCorporateRateAction!list.action",
											params : {
												filter_EQS_valuationType : valuationType,
												filter_EQL_cusId : cusId,
												filter_EQS_takeMode : takeMode,
												filter_EQS_trafficMode : trafficMode,
												filter_EQS_distributionMode : distributeMode,
												filter_EQL_status : 2,
												limit : pageSize,
												filter_EQS_addressType : addrType,
												filter_EQL_departId : bussDepart,
												filter_EQS_startAddr : startCity,
												filter_GED_endDate : new Date().format('Y-m-d'),
												filter_LED_startDate : new Date().format('Y-m-d')
											},
											success : function(resp1) {
												var respText1 = Ext.util.JSON
														.decode(resp1.responseText);
												// 该代理无协议价格 取公布价
												if (respText1.result.length >0) {
													Ext.getCmp('corporate').setValue(respText.result[0].stage1Rate);
													//Ext.getCmp('publicRate').setValue(0);
												}
											}
										});
										// 有协议价格
									} else {
										Ext.getCmp('corporate').setValue(respText.result[0].stage1Rate);
										//Ext.getCmp('publicRate').setValue(0);
									}
								}
							});
							
						}
					}
				});
				Ext.getCmp('consigneeFee').setValue("");
				}
				// 付款方为到付
			}
		}
}
function setaddrmsg(respText){
		if(form.findByType("checkbox")[0].getValue()||Ext.getCmp('takeModecombo').getValue()=='机场自提'){
			Ext.getCmp('distributionMode').setValue("新邦");
		}else{
			Ext.getCmp('distributionMode').setValue(respText.result[0].develpMode);
		}
		Ext.getCmp('areaType').setValue(respText.result[0].areaType);
		Ext.getCmp('goWhere').setValue(respText.result[0].cusName);
    }
function findNormpubprice(type,takeMode,trafficMode,distributeMode,addrType,startCity,city,town,street){
		Ext.Ajax.request({
			url : sysPath
					+ "/fi/cqStCorporateRateAction!findRate.action",
			params : {
				limit : pageSize,
				takeMode : takeMode,
				trafficMode : trafficMode,
				distributionMode : distributeMode,
				addressType : addrType,
				departId : bussDepart,
				startCity : startCity,
				city : city,
				town : town,
				street : street
			},
			success : function(resp2) {
				var respText2 = Ext.util.JSON
						.decode(resp2.responseText);
				// 该目的站没有公布价
				if (respText2.result.length < 1) {
					Ext.Ajax.request({
						url : sysPath
								+ "/fi/cqStCorporateRateAction!list.action",
						params : {
							filter_EQS_takeMode : takeMode,
							filter_EQS_trafficMode : trafficMode,
							filter_EQS_distributionMode : distributeMode,
							filter_EQL_status : 2,
							limit : pageSize,
							filter_EQS_addressType : addrType,
							filter_EQL_departId : bussDepart,
							filter_EQS_startAddr : startCity,
							//filter_GED_endDate : new Date().format('Y-m-d'),
							filter_LED_startDate : new Date()
									.format('Y-m-d')
						},
						success : function(resp3) {
							// 无此条件的公布价
							var respText3 = Ext.util.JSON
									.decode(resp3.responseText);
							if (respText3.result.length < 1) {
								//Ext.getCmp('cpRate').setValue(0);
								Ext.getCmp('cpFee').setValue(0);
								Ext.Msg.alert(alertTitle,'无公布价，请尽快维护!!');
								Ext.getCmp('showMsg').getEl().update('<span style="color:red">运输方式为:'+trafficMode+',提货方式为:'+takeMode+',配送方式为:'+distributeMode+',地址类型为:'+addrType+',始发站为:'+startCity+'的公布价不存在！</span>');
								return false;
							} else {
								setCpStFee(respText3,
										cqWeight,type);
							}
						}
					});
				} else {
					setCpStFee(respText2, cqWeight,type);
				}
			}
		});
	}
function setCpStFee(respText1, cqWeight,type) {
		if (cqWeight >= 0 && cqWeight < 500) {
			if(type=='st'){
				Ext.getCmp('publicRate').setValue(Number(respText1.result[0].stage1Rate));
				Ext.getCmp('corporate').setValue(0);
				//Ext.getCmp('normCpRate').setValue(Number(respText1.result[0].stage1Rate));
				var price = Math.floor(Number(respText1.result[0].stage1Rate)* cqWeight);
				var minPrice = respText1.result[0].lowPrice;
				if (price < minPrice) {
					Ext.getCmp('cpFee').setValue(minPrice);
					cashPrice=minPrice;
				} else {
					Ext.getCmp('cpFee').setValue(price);
					cashPrice=price;
				}
				Ext.getCmp('showMsg').getEl().update('<span style="color:red">该代理无协议价，已找到公布价，只能按重量计费！价格编号:'+respText1.result[0].id+'</span>');
			}
		} else if (cqWeight >= 500 && cqWeight < 1000) {
			if(type=='st'){
				//Ext.getCmp('cpRate').setValue(Number(respText1.result[0].stage2Rate));
				Ext.getCmp('publicRate').setValue(Number(respText1.result[0].stage2Rate));
				Ext.getCmp('corporate').setValue(0);
				var price = Math
					.floor(Number(respText1.result[0].stage2Rate)
							* cqWeight);
				var minPrice = respText1.result[0].lowPrice;
				if (price < minPrice) {
					Ext.getCmp('cpFee').setValue(minPrice);
					cashPrice=minPrice;
				} else {
					Ext.getCmp('cpFee').setValue(price);
					cashPrice=price;
				}
				Ext.getCmp('showMsg').getEl().update('<span style="color:red">该代理无协议价，已找到公布价，只能按重量计费！价格编号:'+respText1.result[0].id+'</span>');
			}
		} else {
			if(type=='st'){
				Ext.getCmp('publicRate').setValue(Number(respText1.result[0].stage3Rate));
				Ext.getCmp('corporate').setValue(0);
				//Ext.getCmp('normCpRate').setValue(Number(respText1.result[0].stage3Rate));
				var price = Math.floor(Number(respText1.result[0].stage3Rate)* cqWeight);
				var minPrice = respText1.result[0].lowPrice;
				if (price < minPrice) {
					Ext.getCmp('cpFee').setValue(minPrice);
					cashPrice=minPrice;
				} else {
					Ext.getCmp('cpFee').setValue(price);
					cashPrice=price;
				}
				Ext.getCmp('showMsg').getEl().update('<span style="color:red">该代理无协议价，已找到公布价，只能按重量计费！价格编号:'+respText1.result[0].id+'</span>');
			}
		}
	}
	function setCqFee(respText, valuationType, cqWeight) {
		if (valuationType == '重量') {
			if (cqWeight > 0 && cqWeight < 500) {
				Ext.getCmp('corporate').setValue(Number(respText.result[0].stage1Rate));
				Ext.getCmp('pulibcRate').setValue(0);
				var price = Math
						.floor(Number(respText.result[0].stage1Rate)
								* cqWeight);
				var minPrice = respText.result[0].lowPrice;
				if (price < minPrice) {
					Ext.getCmp('cpFee').setValue(minPrice);
				} else {
					Ext.getCmp('cpFee').setValue(price);
				}
			} else if (cqWeight >= 500 && cqWeight < 1000) {
				var price = Math
						.floor(Number(respText.result[0].stage2Rate)
								* cqWeight);
				Ext.getCmp('corporate').setValue(Number(respText.result[0].stage2Rate));
				Ext.getCmp('pulibcRate').setValue(0);
				var minPrice = respText.result[0].lowPrice;
				if (price < minPrice) {
					Ext.getCmp('cpFee').setValue(minPrice);
				} else {
					Ext.getCmp('cpFee').setValue(price);
				}
			} else {
				var price = Math
						.floor(Number(respText.result[0].stage3Rate)
								* cqWeight);
				Ext.getCmp('corporate').setValue(Number(respText.result[0].stage3Rate));
				Ext.getCmp('pulibcRate').setValue(0);
				var minPrice = respText.result[0].lowPrice;
				if (price < minPrice) {
					Ext.getCmp('cpFee').setValue(minPrice);
					Ext.getCmp('showMsg').getEl().update('<span style="color:red">已找到代理协议价，按重量计费,取最低一票！价格编号:'+respText.result[0].id+'</span>');
				} else {
					Ext.getCmp('cpFee').setValue(price);
					Ext.getCmp('showMsg').getEl().update('<span style="color:red">已找到代理协议价，按重量计费！价格编号:'+respText.result[0].id+'</span>');
				}
			}
		} else if (valuationType == '体积') {
			var price=Math.floor(Number(respText.result[0].stage1Rate)* Number(Ext.getCmp('bulk').getValue()));
			var minPrice=respText.result[0].lowPrice;
			Ext.getCmp('corporate').setValue(Number(respText.result[0].stage1Rate));
			Ext.getCmp('pulibcRate').setValue(0);
			if(price<minPrice){
				Ext.getCmp('cpFee').setValue(minPrice);
				Ext.getCmp('showMsg').getEl().update('<span style="color:red">已找到代理协议价，按体积计费,取最低一票！价格编号:'+respText.result[0].id+'</span>');
			}else{
				Ext.getCmp('cpFee').setValue(price);
				Ext.getCmp('showMsg').getEl().update('<span style="color:red">已找到代理协议价，按体积计费！价格编号:'+respText.result[0].id+'</span>');
			}
			
		} else if (valuationType == '件数') {
			var price=Math.floor(Number(respText.result[0].stage1Rate)* Number(Ext.getCmp('piece').getValue()));
			var minPrice=respText.result[0].lowPrice;
			Ext.getCmp('corporate').setValue(Number(respText.result[0].stage1Rate));
			Ext.getCmp('pulibcRate').setValue(0);
			if(price<minPrice){
				Ext.getCmp('cpFee').setValue(minPrice);
				Ext.getCmp('showMsg').getEl().update('<span style="color:red">已找到代理协议价，按件数计费,取最低一票！价格编号:'+respText.result[0].id+'</span>');
			}else{
				Ext.getCmp('cpFee').setValue(price);
				Ext.getCmp('showMsg').getEl().update('<span style="color:red">已找到代理协议价，按件数计费！价格编号:'+respText.result[0].id+'</span>');
			}
			
		}
	}
	//设置收货人标准协议价
	function setConStFee(respText1,cqWeight,type){
		if (cqWeight >= 0 && cqWeight < 500) {
			if(type=='st'){
				//Ext.getCmp('consigneeRate').setValue(Number(respText1.result[0].stage1Rate));
				//Ext.getCmp('normConsigneeRate').setValue(Number(respText1.result[0].stage1Rate));
				Ext.getCmp('publicRate').setValue(Number(respText1.result[0].stage1Rate));
				Ext.getCmp('corporate').setValue(0);
				var price = Math.floor(Number(respText1.result[0].stage1Rate)* cqWeight);
				var minPrice = respText1.result[0].lowPrice;
				if (price < minPrice) {
					Ext.getCmp('consigneeFee')
							.setValue(minPrice);
				} else {
					Ext.getCmp('consigneeFee').setValue(price);
				}
				Ext.getCmp('showMsg').getEl().update('<span style="color:red">该收货人无协议价，已找到公布价！价格编号：'+respText1.result[0].id+'</span>');
			}else{
				//Ext.getCmp('consigneeRate').setValue(Number(respText1.result[0].stage1Rate));
			}
			
		} else if (cqWeight >= 500 && cqWeight < 1000) {
			if(type=='st'){
				//Ext.getCmp('consigneeRate').setValue(Number(respText1.result[0].stage2Rate));
				//Ext.getCmp('normConsigneeRate').setValue(Number(respText1.result[0].stage2Rate));
				Ext.getCmp('publicRate').setValue(Number(respText1.result[0].stage2Rate));
				Ext.getCmp('corporate').setValue(0);
				var price = Math
					.floor(Number(respText1.result[0].stage2Rate)
							* cqWeight);
				var minPrice = respText1.result[0].lowPrice;
				if (price < minPrice) {
					Ext.getCmp('consigneeFee')
							.setValue(minPrice);
				} else {
					Ext.getCmp('consigneeFee').setValue(price);
				}
				Ext.getCmp('showMsg').getEl().update('<span style="color:red">该收货人无协议价，已找到公布价！价格编号：'+respText1.result[0].id+'</span>');
			}else{
				//Ext.getCmp('consigneeRate').setValue(Number(respText1.result[0].stage2Rate));
			}
		} else {
			if(type=='st'){
				//Ext.getCmp('consigneeRate').setValue(Number(respText1.result[0].stage3Rate));
				//Ext.getCmp('normConsigneeRate').setValue(Number(respText1.result[0].stage3Rate));
				var price = Math.floor(Number(respText1.result[0].stage3Rate)* cqWeight);
				var minPrice = respText1.result[0].lowPrice;
				Ext.getCmp('publicRate').setValue(Number(respText1.result[0].stage3Rate));
				Ext.getCmp('corporate').setValue(0);
				if (price < minPrice) {
					Ext.getCmp('consigneeFee')
							.setValue(minPrice);
				} else {
					Ext.getCmp('consigneeFee').setValue(price);
				}
				Ext.getCmp('showMsg').getEl().update('<span style="color:red">该收货人无协议价，已找到公布价！价格编号：'+respText1.result[0].id+'</span>');
			}else{
				//Ext.getCmp('consigneeRate').setValue(Number(respText1.result[0].stage3Rate));
			}
		}
}
//获得标准专车协议价
function findNormSondPrice(type,roadType,town,street){
	Ext.Ajax.request({
		url : sysPath
				+ "/fi/stSpecialTrainRateAction!findRate.action",
		params : {
			limit : pageSize,
			roadType : roadType,
			town : town,
			street : street,
			departId : bussDepart
		},
		success : function(resp1) {
			var respText1 = Ext.util.JSON
					.decode(resp1.responseText);
			if (respText1.resultMap.length < 1) {
				Ext.Msg.alert(alertTitle, '路型为:'+roadType+',地区名称为:'+street+',或'+town+'的标准专车协议价格不存在!');
				return false;
			}else{
				if(type=='st'){
					setSpecialRate(respText1);
				}
			}
			
		}
	});
}

function findNormconprice(type,cqWeight,takeMode,trafficMode,distributeMode,addrType,startCity,city,town,street){
		Ext.Ajax.request({
			url : sysPath
					+ "/fi/cqStCorporateRateAction!findRate.action",
			params : {
				limit : pageSize,
				takeMode : takeMode,
				trafficMode : trafficMode,
				distributionMode : distributeMode,
				addressType : addrType,
				departId : bussDepart,
				startCity : startCity,
				city : city,
				town : town,
				street : street
			},
			success : function(resp1) {
				var respText1 = Ext.util.JSON
						.decode(resp1.responseText);
				if(respText1.result.length<1){
					Ext.Ajax.request({
						url : sysPath+ "/fi/cqStCorporateRateAction!list.action",
						params : {
							filter_EQS_takeMode : takeMode,
							filter_EQS_trafficMode : trafficMode,
							filter_EQS_distributionMode : distributeMode,
							filter_EQL_status : 2,
							limit : pageSize,
							filter_EQS_addressType : addrType,
							filter_EQL_departId : bussDepart,
							filter_EQS_startAddr : startCity,
							//filter_GED_endDate : new Date().format('Y-m-d'),
							filter_LED_startDate : new Date().format('Y-m-d')
						},
						success : function(resp3) {
							// 无此条件的公布价
							var respText3 = Ext.util.JSON.decode(resp3.responseText);
							if (respText3.result.length < 1) {
								//Ext.getCmp('consigneeRate').setValue(0);
								Ext.getCmp('consigneeFee').setValue(0);
								Ext.Msg.alert(alertTitle,'该收货人无特殊协议价并且无公布价，请尽快维护!!');
								Ext.getCmp('showMsg').getEl().update('<span style="color:red">运输方式为:'+trafficMode+',提货方式为:'+takeMode+',配送方式为:'+distributeMode+',地址类型为:'+addrType+',始发站为:'+startCity+'的公布价不存在！</span>');
								return false;
							} else {
								setConStFee(respText3,cqWeight,type);
							}
						}
					});
				}else{
					setConStFee(respText1,cqWeight,type);
				}
			}
		});
	}
function saveCusRecord(){
	if(form.getForm().isValid()){
	var town = Ext.getCmp('town').getValue();
	var street = Ext.getCmp('street').getValue();
	var city = Ext.getCmp('city').getValue();
	var roadType = Ext.getCmp('roadtypecombo').getValue();
	var cusId = Ext.getCmp('cuscombo').getValue();
	if(cusId==''){
		cusId=parent.cusId;
	}
	var addrType = Ext.getCmp('areaType').getValue();
	var startCity = Ext.getCmp('endCity').getValue();
	var valuationType = Ext.getCmp('chargemodecombo').getValue();
	var trafficMode = Ext.getCmp('trafficModecombo').getValue();
	var takeMode = Ext.getCmp('takeModecombo').getValue();
	var distributeMode = Ext.getCmp('distributionMode').getValue();
	var cqWeight = Number(Ext.getCmp('cusWeight').getValue());//计费重量
	var whoCash=Ext.getCmp('payerCombo').getValue();
	var cusName=Ext.getCmp('goWhere').getValue();

	// 专车
	if (form.findByType("checkbox")[0].getValue()) {
		if(cusId=='' || cusId == null){
			findNormSondPrice('st',roadType,town,street);
		}else{
			Ext.Ajax.request({
				url : sysPath + "/fi/specialTrainRateAction!findRate.action",
				params : {
					limit : pageSize,
					cusId : cusId,
					roadType : roadType,
					town : town,
					street : street,
					departId : bussDepart
				},
				success : function(resp) {
					var respText = Ext.util.JSON.decode(resp.responseText);
					if (respText.resultMap.length < 1) {
						// 该代理无协议价格
						findNormSondPrice('st',roadType,town,street);					
						
					} else {
						var respText = Ext.util.JSON.decode(resp.responseText);
						setSpecialRate(respText);
						Ext.getCmp('showMsg').getEl().update('<span style="color:red">已找到专车协议价格，编号为：'+respText.resultMap[0].ID+'！</span>');
					}
				}
			});
		}
	}
	if (distributeMode == "中转"||distributeMode=='外发') {
		Ext.Ajax.request({
			url : sysPath + "/fi/basTraShipmentRateAction!ralaList.action",
			params : {
				limit : pageSize,
				privilege : 100,
				filter_EQS_cusName :cusName,
				filter_EQS_trafficMode : trafficMode,
				filter_EQS_takeMode : takeMode,
				filter_EQS_areaType : addrType,
				filter_EQL_status : 2,
				filter_EQL_departId : bussDepart,
				filter_EQS_valuationType:valuationType,
				filter_GED_endDate : new Date().format('Y-m-d'),
				filter_LED_startDate : new Date().format('Y-m-d')
			},
			success : function(resp) {
				var respText = Ext.util.JSON.decode(resp.responseText);
				if (respText.result.length < 1) {
					// 无协议价格
					Ext.Msg.alert(alertTitle,'此供应商无中转协议价格，请维护!');
					Ext.getCmp('showMsg').getEl().update('<span style="color:red">供应商为:'+cusName+',运输方式为:'+trafficMode+',提货方式为:'+takeMode+',地址类型为:'+addrType+',计价方式为:'+valuationType+'的中转协议价格不存在！</span>');
					return false;
				} else {
					var valuationType1=respText.result[0].valuationType;
					if(valuationType1=='重量'){
						if (cqWeight >= 0 && cqWeight < 500) {
							
							Ext
									.getCmp('traFeeRate')
									.setValue(Number(respText.result[0].stage1Rate));
							var price = Math
									.floor(Number(respText.result[0].stage1Rate)
											* cqWeight);
							var minPrice = respText.result[0].lowPrice;
							if (price < minPrice) {
								Ext.getCmp('traFee').setValue(minPrice);
								Ext.getCmp('showMsg').getEl().update('<span style="color:red">已找到中转协议价,取最低一票！</span>');
							} else {
								Ext.getCmp('traFee').setValue(price);
								Ext.getCmp('showMsg').getEl().update('<span style="color:red">已找到中转协议价,按500KG计费！</span>');
							}
						} else if (cqWeight >= 500 && cqWeight < 1000) {
							Ext
									.getCmp('traFeeRate')
									.setValue(Number(respText.result[0].stage2Rate));
							var price = Math
									.floor(Number(respText.result[0].stage2Rate)
											* cqWeight);
							var minPrice = respText.result[0].lowPrice;
							if (price < minPrice) {
								Ext.getCmp('traFee').setValue(minPrice);
								Ext.getCmp('showMsg').getEl().update('<span style="color:red">已找到中转协议价,取最低一票！</span>');
							} else {
								Ext.getCmp('traFee').setValue(price);
								Ext.getCmp('showMsg').getEl().update('<span style="color:red">已找到中转协议价,按1000KG计费！</span>');
							}
						} else {
							Ext
									.getCmp('traFeeRate')
									.setValue(Number(respText.result[0].stage3Rate));
							var price = Math
									.floor(Number(respText.result[0].stage3Rate)
											* cqWeight);
							var minPrice = respText.result[0].lowPrice;
							if (price < minPrice) {
								Ext.getCmp('traFee').setValue(minPrice);
								Ext.getCmp('showMsg').getEl().update('<span style="color:red">已找到中转协议价,取最低一票！</span>');
							} else {
								Ext.getCmp('traFee').setValue(price);
								Ext.getCmp('showMsg').getEl().update('<span style="color:red">已找到中转协议价,按1000KG以上计费！</span>');
							}
						}
					}else if (valuationType1 == '体积') {
						Ext
									.getCmp('traFeeRate')
									.setValue(Number(respText.result[0].stage1Rate));
						var price=Math.floor(Number(respText.result[0].stage1Rate)* Number(Ext.getCmp('bulk').getValue()));
						var minPrice=respText.result[0].lowPrice;
						if(price<minPrice){
							Ext.getCmp('traFee').setValue(minPrice);
							Ext.getCmp('showMsg').getEl().update('<span style="color:red">已找到中转协议价，按体积计费,取最低一票！价格编号:'+respText.result[0].id+'</span>');
						}else{
							Ext.getCmp('traFee').setValue(price);
							Ext.getCmp('showMsg').getEl().update('<span style="color:red">已找到中转协议价，按体积计费！价格编号:'+respText.result[0].id+'</span>');
						}
											
					} else if (valuationType1 == '件数') {
						Ext
									.getCmp('traFeeRate')
									.setValue(Number(respText.result[0].stage1Rate));
						var price=Math.floor(Number(respText.result[0].stage1Rate)* Number(Ext.getCmp('piece').getValue()));
						var minPrice=respText.result[0].lowPrice;
						if(price<minPrice){
							Ext.getCmp('traFee').setValue(minPrice);
							Ext.getCmp('showMsg').getEl().update('<span style="color:red">已找到中转协议价，按件数计费,取最低一票！价格编号:'+respText.result[0].id+'</span>');
						}else{
							Ext.getCmp('traFee').setValue(price);
							Ext.getCmp('showMsg').getEl().update('<span style="color:red">已找到中转协议价，按件数计费！价格编号:'+respText.result[0].id+'</span>');
						}
											
					}
				}
			}
		});
	}else{
		Ext.getCmp('traFeeRate').setValue('');
		Ext.getCmp('traFee').setValue('');
	}
	
	// 付款方为预付
	if (whoCash == '预付'||whoCash=='双方付') {
		if(cusId==''||cusId==null){
			Ext.Msg.alert(alertTitle,'预付时代理公司为空，则是计算公布价。');
		}
		else{
		var piece=Ext.getCmp('piece').getValue();
		var bulk=Ext.getCmp('bulk').getValue();
		// 判断是否为项目客户
		Ext.Ajax.request({
			url : sysPath + "/sys/customerAction!ralaList.action",
			params : {
				privilege : 61,
				filter_EQL_id:cusId,
				filter_EQL_isProjectcustomer:1
			},
			success : function(res) {
				var resText = Ext.util.JSON.decode(res.responseText);
				// 是项目客户
				if (resText.result.length > 0) {
					Ext.Ajax.request({
						url:sysPath+"/fi/basProjectRateAction!findRate.action",
						params:{
							piece:piece,
							weight:cqWeight,
							cusId:cusId,
							bulk:bulk,
							town:town,
							city:city
						},
						success:function(respp){
							var resppText = Ext.util.JSON.decode(respp.responseText);
							if(resppText.resultMap.length<1){
								//Ext.getCmp('cpRate').setValue(0);
								Ext.getCmp('cpFee').setValue(0);
								Ext.Msg.alert(alertTitle,'此项目客户的协议价格不存在，请尽快维护!!');
								Ext.getCmp('showMsg').getEl().update('<span style="color:red">件数为:'+piece+',重量为:'+cqWeight+',体积为:'+bulk+'的项目客户协议价格不存在！</span>');
								return false;
							}else{
								var valuaType=resppText.resultMap[0].COUNT_WAY;
								var prate=Number(resppText.resultMap[0].RATE);
								var plowFee=Number(resppText.resultMap[0].LOW_FEE);
								var pprice=0;
								if(valuaType=='件数'){
									pprice=Number(Ext.getCmp('piece').getValue())*prate;
								}else if(valuaType='重量'){
									pprice=cqWeight*prate;
								}
								else if(valuaType='体积'){
									pprice=Number(Ext.getCmp('bulk').getValue())*prate;
								}
								if(pprice<plowFee){
									pprice=plowFee;
								}
								cashPrice=pprice;
								Ext.getCmp('chargemodecombo').setValue(valuaType);
								//Ext.getCmp('cpRate').setValue(prate);
								Ext.getCmp('cpFee').setValue(pprice);
								Ext.getCmp('showMsg').getEl().update('<span style="color:red">此代理为项目客户，已找到项目客户协议价！价格编号:'+resppText.resultMap[0].ID+'</span>');
							}
						}
					});
				} else {
					// 取代理协议价
					Ext.Ajax.request({
						url : sysPath + "/fi/cqCorporateRateAction!findRate.action",
						params : {
							valuationType : valuationType,
							cusId : cusId,
							limit : pageSize,
							takeMode : takeMode,
							trafficMode : trafficMode,
							distributionMode : distributeMode,
							addressType : addrType,
							departId : bussDepart,
							startCity : startCity,
							city : city,
							town : town,
							street : street
						},
						success : function(resp) {
							var respText = Ext.util.JSON.decode(resp.responseText);
							// 该代理目的站没有协议价格
							if (respText.result.length < 1) {
								Ext.Ajax.request({
									url : sysPath + "/fi/cqCorporateRateAction!list.action",
									params : {
										filter_EQS_valuationType : valuationType,
										filter_EQL_cusId : cusId,
										filter_EQS_takeMode : takeMode,
										filter_EQS_trafficMode : trafficMode,
										filter_EQS_distributionMode : distributeMode,
										filter_EQL_status : 2,
										limit : pageSize,
										filter_EQS_addressType : addrType,
										filter_EQL_departId : bussDepart,
										filter_EQS_startAddr : startCity,
										filter_GED_endDate : new Date().format('Y-m-d'),
										filter_LED_startDate : new Date().format('Y-m-d')
									},
									success : function(resp1) {
										var respText1 = Ext.util.JSON
												.decode(resp1.responseText);
										// 该代理无协议价格 取公布价
										if (respText1.result.length < 1) {
											findNormpubprice('st',takeMode,trafficMode,distributeMode,addrType,startCity,city,town,street);
										} else {
											setCqFee(respText1, valuationType, cqWeight);
											//findNormpubprice('cq');
										}
									}
								});
								// 有协议价格
							} else {
								setCqFee(respText, valuationType, cqWeight);
								//findNormpubprice('cq');
							}
						}
					});
					
				}
			}
		});
		//Ext.getCmp('consigneeRate').setValue("");
		Ext.getCmp('consigneeFee').setValue("");
		}
		// 付款方为到付
	} else if (whoCash == '到付') {
		
		var tel = Ext.getCmp('linkTel').getValue();
		if(tel == '' || tel == null){
			findNormconprice('st',cqWeight,takeMode,trafficMode,distributeMode,addrType,startCity,city,town,street);
		}
		else{
		//var name = Ext.getCmp('consignee').getValue();
		var tels;
		if (tel.indexOf('/') < 0) {
			tels = tel;
		} else {
			tels = tel.split('/');
		}
		Ext.Ajax.request({
			url : sysPath + "/sys/conDealPriceAction!findRate.action",
			params : {
				limit : pageSize,
				tels : tels
			},
			success : function(resp) {
				var respText = Ext.util.JSON.decode(resp.responseText);
				// 此收货人无协议价格
				if (respText.result.length < 1) {
					findNormconprice('st',cqWeight,takeMode,trafficMode,distributeMode,addrType,startCity,city,town,street);
				} else {

					if (takeMode == "机场自提") {
						var flyOwnPrice = respText.result[0].flyOwnPrice;
						var minPrice = respText.result[0].flyOwnMinPrice;
						var price = Math.floor(Number(flyOwnPrice) * cqWeight);
						Ext.getCmp('corporate').setValue(flyOwnPrice);
						Ext.getCmp('publicRate').setValue(0);
						if (price < minPrice) {
							Ext.getCmp('consigneeFee').setValue(minPrice);
						} else {
							Ext.getCmp('consigneeFee').setValue(price);
						}
					} else if (takeMode == "市内自提") {
						var cityOwnPrice = respText.result[0].cityOwnPrice;
						var price = Math.floor(Number(cityOwnPrice) * cqWeight);
						var minPrice = respText.result[0].cityOwnMinPrice;
						Ext.getCmp('corporate').setValue(cityOwnPrice);
						Ext.getCmp('publicRate').setValue(0);
						if (price < minPrice) {
							Ext.getCmp('consigneeFee').setValue(minPrice);
						} else {
							Ext.getCmp('consigneeFee').setValue(price);
						}
					} else if (takeMode == "市内送货") {
						var citySendPrice = respText.result[0].citySendPrice;
						var price = Math
								.floor(Number(citySendPrice) * cqWeight);
						var minPrice = respText.result[0].citySendMinPrice;
						Ext.getCmp('corporate').setValue(citySendPrice);
						Ext.getCmp('publicRate').setValue(0);
						if (price < minPrice) {
							Ext.getCmp('consigneeFee').setValue(minPrice);
						} else {
							Ext.getCmp('consigneeFee').setValue(price);
						}
					}
					Ext.getCmp('showMsg').getEl().update('<span style="color:red">此收货人有特殊协议价，按特殊协议价计费！价格编号：'+respText.result[0].id+'</span>');
				}
			}
		});
		}
		//Ext.getCmp('cpRate').setValue("");
		Ext.getCmp('cpFee').setValue("");
		//双方付
	}
	}
}
function setSpecialRate(respText1){
	var carType=Ext.getCmp('cartypecombo').getValue();
	if(respText1.resultMap.length>1){
		if(carType=="金杯车"){
		Ext.getCmp('sonderzugPrice').setValue(respText1.resultMap[0][0].GOLD_CUP_CAR);
		}else if(carType=="面包车"){
			Ext.getCmp('sonderzugPrice').setValue(respText1.resultMap[0][0].VAN);
		}else if(carType=="冷藏车"){
			Ext.getCmp('sonderzugPrice').setValue(respText1.resultMap[0][0].CHILL_CAR);
		}else if(carType=="2吨车"){
			Ext.getCmp('sonderzugPrice').setValue(respText1.resultMap[0][0].TWO_TON_CAR);
		}else if(carType=="3吨车"){
			Ext.getCmp('sonderzugPrice').setValue(respText1.resultMap[0][0].THREE_TON_CAR);
		}else if(carType=="5吨车"){
			Ext.getCmp('sonderzugPrice').setValue(respText1.resultMap[0][0].FIVE_TON_CAR);
		}
	}else{
		if(carType=="金杯车"){
		Ext.getCmp('sonderzugPrice').setValue(respText1.resultMap[0].GOLD_CUP_CAR);
		}else if(carType=="面包车"){
			Ext.getCmp('sonderzugPrice').setValue(respText1.resultMap[0].VAN);
		}else if(carType=="冷藏车"){
			Ext.getCmp('sonderzugPrice').setValue(respText1.resultMap[0].CHILL_CAR);
		}else if(carType=="2吨车"){
			Ext.getCmp('sonderzugPrice').setValue(respText1.resultMap[0].TWO_TON_CAR);
		}else if(carType=="3吨车"){
			Ext.getCmp('sonderzugPrice').setValue(respText1.resultMap[0].THREE_TON_CAR);
		}else if(carType=="5吨车"){
			Ext.getCmp('sonderzugPrice').setValue(respText1.resultMap[0].FIVE_TON_CAR);
		}
	}
	
}
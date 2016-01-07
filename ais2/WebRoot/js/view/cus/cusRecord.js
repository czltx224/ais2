// 客户资料管理
var privilege=151;
var fields=[
	{name:'id'},
	{name:'cusName'},
	{name:'importanceLevel'},
	{name:'attentionClassify'},
	{name:'type1'},
	{name:'area'},
	{name:'bussAirport'},
	{name:'bussTel'},
	{name:'isCq'},
	{name:'manCount'},
	{name:'developLevel'},
	{name:'lastBuss'},
	{name:'addr'},
	{name:'mainBussiness'},
	{name:'cusOrigin'},
	{name:'cusFrom'},
	{name:'registerName'},
	{name:'aayuNum'},
	{name:'registerDate'},
	{name:'phone'},
	{name:'businessEntity'},
	{name:'lastCommunicate'},
	{name:'scopeBusiness'},
	{name:'expectedCargo'},
	{name:'expectedTurnover'},
	{name:'deliveryCycle'},
	{name:'information'},
	{name:'competeCom'},
	{name:'financialPositon'},
	{name:'startBuss'},
	{name:'createTime'},
	{name:'createName'},
	{name:'updateTime'},
	{name:'updateName'},
	{name:'ts'},
	{name:'departCode'},
	{name:'cusId'},
	{name:'status'},
	{name:'CompanyEmail'},
	{name:'webSite'},
	{name:'fax'},
	{name:'profitType'},
	{name:'attentionRemark'},
	{name:'province'},
	{name:'city'},
	{name:'companyRemark'},
	{name:'shortName'},
	{name:'departId'},
	{name:'principalId'},
	{name:'principal'},
	{name:'settlement'},
	{name:'isProjectcustomer'},
	{name:'warnDeliveryCycle'},
	{name:'remark'}
];
function parentSetMsg(name,tel){
	Ext.getCmp('cusRecordForm').getForm().reset();
	Ext.getCmp('cuscombo').setValue(name);
	Ext.getCmp('conType').setValue('conType');
	Ext.getCmp('phone').setValue(tel);
	Ext.getCmp('isCq').setValue('收货人');
	Ext.getCmp('cuscombo').focus(true,true);
}
//重要程度Store
var importanceLevelStore=new Ext.data.Store({
		storeId:'importanceLevelStore',
		baseParams:{filter_EQL_basDictionaryId:35,privilege:16},
		proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/dictionaryAction!ralaList.action"}),
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
        	{name:'id'},
        	{name:'importanceLevel',mapping:'typeName'}
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
//近期关注Store
var attentionClassifyStore=new Ext.data.Store({
		storeId:'attentionClassifyStore',
		baseParams:{filter_EQL_basDictionaryId:36,privilege:16},
		proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/dictionaryAction!ralaList.action"}),
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
        	{name:'id'},
        	{name:'attentionClassify',mapping:'typeName'}
        	])
});
//结算方式
var settlementStore=new Ext.data.Store({
	storeId:"settlementStore",
	proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/dictionaryAction!ralaList.action"}),
	baseParams:{filter_EQL_basDictionaryId:82,privilege:16},
	reader:new Ext.data.JsonReader({
                root:'result',
                totalProperty:'totalCount'
    },[
    	{name:'id'},
    	{name:'settlement',mapping:'typeName'}
    	])
});
//业务机场Store
var bussAirportStore=new Ext.data.Store({
		storeId:'bussAirportStore',
		baseParams:{filter_EQL_basDictionaryId:37,privilege:16},
		proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/dictionaryAction!ralaList.action"}),
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
        	{name:'id'},
        	{name:'bussAirport',mapping:'typeName'}
        	])
});
//客户类型Store
var isCqStore=new Ext.data.Store({
		storeId:'isCqStore',
		baseParams:{filter_EQL_basDictionaryId:38,privilege:16},
		proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/dictionaryAction!ralaList.action"}),
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
        	{name:'id'},
        	{name:'isCq',mapping:'typeName'}
        	])
});
//开发阶段Store
var developLevelStore=new Ext.data.Store({
		storeId:'developLevelStore',
		baseParams:{filter_EQL_basDictionaryId:39,privilege:16},
		proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/dictionaryAction!ralaList.action"}),
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
        	{name:'id'},
        	{name:'developLevel',mapping:'typeName'}
        	])
});
//主营业务Store
var mainBussinessStore=new Ext.data.Store({
		storeId:'mainBussinessStore',
		baseParams:{filter_EQL_basDictionaryId:40,privilege:16},
		proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/dictionaryAction!ralaList.action"}),
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
        	{name:'id'},
        	{name:'mainBussiness',mapping:'typeName'}
        	])
});
//客户来源类型Store
var cusOriginStore=new Ext.data.Store({
		storeId:'cusOriginStore',
		baseParams:{filter_EQL_basDictionaryId:41,privilege:16},
		proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/dictionaryAction!ralaList.action"}),
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
        	{name:'id'},
        	{name:'cusOrigin',mapping:'typeName'}
        	])
});
//客户来源Store
var cusFromStore=new Ext.data.Store({
		storeId:'cusFromStore',
		baseParams:{filter_EQL_basDictionaryId:43,privilege:16},
		proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/dictionaryAction!ralaList.action"}),
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
        	{name:'id'},
        	{name:'cusFrom',mapping:'typeName'}
        	])
});
Ext.onReady(function() {
	var tb = new Ext.Toolbar({
		width : Ext.lib.Dom.getViewWidth(),
		id : 'faxToolbar',
		items : ['&nbsp;&nbsp;', {
					text : '<B>保存</B>',
					id : 'addDetailBtn',
					tooltip : '保存新增或者修改',
					iconCls : 'groupAdd',
					handler : function() {
						 saveCusRecord();
					}
				}]
	});
	var form = new Ext.form.FormPanel({
		frame : true,
		id:'cusRecordForm',
		border : false,
		bodyStyle : 'padding:5px 0px 0px',
		renderTo:'showView',
		region:'center',
		width : Ext.lib.Dom.getViewWidth()-20,
		labelAlign : "right",
		labelWidth : 90,
        reader : new Ext.data.JsonReader({
            root: 'result', totalProperty: 'totalCount'
        }, fields),
        items : [{
				layout : 'column',
				labelAlign : 'right',
				items : [{
					columnWidth : .33,
					layout : 'form',
					items : [{xtype:'hidden',name:'id',id:'id'},
						{xtype:'hidden',name:'conType',id:'conType'},
						//{xtype:'hidden',name:'departId',id:'departId',value:bussDepart},
						//{xtype:'hidden',name:'departCode',id:'departCode',value:departId},
						//{xtype:'hidden',name:'principalId',id:'principalId',value:userId},
						//{xtype:'hidden',name:'principal',id:'principal',value:userName},
						//{xtype:'hidden',name:'status',id:'status',value:1},
						{
								xtype:'combo',
								fieldLabel:'客户名称<span style="color:red">*</span>',
								triggerAction :'all',
								id:'cuscombo',
								minChars : 0,
								editable:true,
								hideTrigger:true,
								store:cusStore,
								queryParam :'filter_LIKES_cusName',
								pageSize:pageSize,
								valueField :'cusName',
								displayField :'cusName',
								anchor : '80%',
								hiddenName : 'cusName',
								forceSelection : false,
								blankText : "客户名称不能为空!",
								allowBlank : false
							}, {
								xtype : 'combo',
								store:importanceLevelStore,
								name:'importanceLevel',
								valueField:'importanceLevel',
								displayField:'importanceLevel',
								allowBlank:false,
								blankText:'重要程度分类不能为空！',
								id:'importanceLevel',
								anchor : '80%',
								value:'常客',
								model:'local',
								fieldLabel : '重要程度分类<span style="color:red">*</span>'
							},{
								xtype:'textfield',
								name:'profitType',
								fieldLabel:'盈利性分类',
								readOnly:true,
								anchor : '80%',
								id:'profitType',
								value:'C类'
							}, {
								xtype : 'combo',
								name:'attentionClassify',
								id:'attentionClassify',
								store:attentionClassifyStore,
								valueField:'attentionClassify',
								displayField:'attentionClassify',
								allowBlank:false,
								anchor : '80%',
								blankText:'近期关注分类不能为空！',
								value:'非热点关注',
								fieldLabel : '近期关注分类<span style="color:red">*</span>'
							},{
								xtype:'textfield',
								fieldLabel:'热点说明',
								anchor : '80%',
								name:'attentionRemark'
							}, {
								xtype : 'textfield',
								name:'type1',
								id:'type1',
								fieldLabel : '自定义类型',
								maxLength:25,
								anchor : '80%',
								maxLengthText:'自定义类型不能多于25个汉字'
							}, {
								xtype : 'textfield',
								name:'area',
								id:'area',
								fieldLabel : '地域<span style="color:red">*</span>',
								allowBlank:false,
								anchor : '80%',
								blankText:'地域不能为空！',
								maxLength:25,
								maxLengthText:'地域不能多于25个汉字'
							}, {
								xtype : 'combo',
								triggerAction : 'all',
								name:'bussAirport',
								anchor : '80%',
								id:'bussAirport',
								store:bussAirportStore,
								valueField:'bussAirport',
								displayField:'bussAirport',
								model:'local',
								allowBlank:true,
								empytText:'请选择',
								fieldLabel : '业务机场'
							}, {
								xtype : 'textfield',
								name:'bussTel',
								id:'bussTel',
								anchor : '80%',
								fieldLabel : '业务联系电话<span style="color:red">*</span>',
								allowBlank:false,
								blankText:'业务联系电话不能为空！',
								maxLength:200,
								maxLengthText:'业务联系电话不能多于200个字符'
							}, {
								xtype : 'combo',
								triggerAction : 'all',
								name:'isCq',
								id:'isCq',
								editable:false,
								anchor : '80%',
								store:isCqStore,
								valueField:'isCq',
								displayField:'isCq',
								model:'local',
								allowBlank:false,
								blankText:'客户类型不能为空！',
								fieldLabel : '客户类型<span style="color:red">*</span>'
							}, {
								xtype : "combo",
								triggerAction : 'all',
								name:'developLevel',
								id:'developLevel',
								anchor : '80%',
								store:developLevelStore,
								valueField:'developLevel',
								displayField:'developLevel',
								model:'local',
								allowBlank:false,
								value:'潜在客户',
								blankText:'开发阶段不能为空！',
								fieldLabel : '开发阶段<span style="color:red">*</span>'
			
							}, {
								xtype : 'textfield',
								name:'fax',
								id:'fax',
								anchor : '80%',
								fieldLabel : '传真',
								maxLength:50,
								maxLengthText:'传真不能多于50个字符'
			
							},{
								xtype:'textarea',
								fieldLabel:'公司简介',
								name:'companyRemark',
								height:60,
								anchor : '95%',
								//width:100,
								maxLength:250,
								maxLengthText:'网址不能多于250个汉字'
								
							}]

						},{
							columnWidth : .33,
							layout : 'form',
							items : [{
							xtype:'textfield',
							fieldLabel:'简称',
							anchor : '80%',
							name:'shortName'
						},{
							xtype : 'combo',
							triggerAction : 'all',
							name:'mainBussiness',
							id:'mainBussiness',
							anchor : '80%',
							store:mainBussinessStore,
							valueField:'mainBussiness',
							displayField:'mainBussiness',
							model:'local',
							fieldLabel : '主营业务'
						}, {
							xtype:'textfield',
							fieldLabel:'省份',
							anchor : '80%',
							name:'province',
							id:'province'
						},{
							xtype:'textfield',
							fieldLabel:'城市',
							anchor : '80%',
							name:'city',
							id:'city',
							maxLength:10,
							maxLengthText:'城市不能多于10个汉字'
						},{
							xtype : 'textfield',
							name:'addr',
							id:'addr',
							fieldLabel : '地址',
							anchor : '80%',
							maxLength:100,
							maxLengthText:'地址不能多于100个汉字'
						}, {
							xtype : 'combo',
							name:'cusOrigin',
							triggerAction : 'all',
							id:'cusOrigin',
							anchor : '80%',
							store:cusOriginStore,
							valueField:'cusOrigin',
							displayField:'cusOrigin',
							model:'local',
							emptyText:'请选择',
							fieldLabel : '客户来源类型'
						}, {
							xtype : 'combo',
							name:'cusFrom',
							triggerAction : 'all',
							id:'cusFrom',
							anchor : '80%',
							store:cusFromStore,
							valueField:'cusFrom',
							displayField:'cusFrom',
							model:'local',
							emptyText:'请选择',
							allowBlank:false,
							blankText:'客户来源不能为空！',
							fieldLabel : '客户来源<span style="color:red">*</span>'
						}, {
							xtype : 'textfield',
							name:'registerName',
							id:'registerName',
							anchor : '80%',
							fieldLabel : '完整注册名',
							maxLength:100,
							maxLengthText:'完整注册名不能多于100个汉字'
						}, {
							xtype : 'textfield',
							name:'aayuNum',
							id:'aayuNum',
							anchor : '80%',
							fieldLabel : '工商号',
							maxLength:100,
							maxLengthText:'工商号不能多于100个汉字'
						}, {
							xtype : 'datefield',
							name:'registerDate',
							id:'registerDate',
							fieldLabel : '注册时间',
							anchor : '80%',
							format:'Y-m-d'
						}, {
							xtype : 'textfield',
							name:'phone',
							id:'phone',
							anchor : '80%',
							tabTip : '多个电话用/隔开',
							fieldLabel:'电话',
							maxLength:50,
							maxLengthText:'工商号不能多于50个字符'
						}, {
							xtype : 'textfield',
							name:'businessEntity',
							anchor : '80%',
							id:'businessEntity',
							fieldLabel : '企业法人',
							maxLength:200,
							maxLengthText:'企业法人不能多于100个汉字'
						}, {
							xtype : 'textfield',
							name:'companyEmail',
							id:'companyEmail',
							fieldLabel : '企业邮箱',
							anchor : '80%',
							maxLength:50,
							maxLengthText:'企业邮箱不能多于50个字符'
						},{
							xtype:'textfield',
							fieldLabel:'开始合作日期',
							name:'startBuss',
							anchor : '80%',
							id:'startBuss',
							readOnly:true
						}]
						},{
							columnWidth : .33,
							layout : 'form',
							items : [{
									xtype : 'textfield',
									name:'scopeBusiness',
									id:'scopeBusiness',
									anchor : '80%',
									fieldLabel : '经营范围'
								}, {
									xtype : 'numberfield',
									name:'expectedCargo',
									id:'expectedCargo',
									anchor : '80%',
									maxValue:99999.99,
									maxLength:8,
									fieldLabel : '预计月货量(吨)'
								}, {
									xtype : 'numberfield',
									name:'expectedTurnover',
									id:'expectedTurnover',
									anchor : '80%',
									maxValue:99999999.99,
									maxLength:11,
									fieldLabel : '预计月营业额(元)'
								}, {
									xtype : 'combo',
									name:'manCount',
									triggerAction : 'all',
									model:'local',
									anchor : '80%',
									store:[
										['50人以下','50人以下'],
										['50-200人','50-200人'],
										['200-500人','200-500人'],
										['500人以上','500人以上']
									],
									id:'manCount',
									fieldLabel : '人员规模'
								}, {
									xtype : 'numberfield',
									name:'deliveryCycle',
									id:'deliveryCycle',
									anchor : '80%',
									fieldLabel : '发货周期(天)'
	
								}, {
									xtype : 'numberfield',
									name:'warnDeliveryCycle',
									anchor : '80%',
									id:'warnDeliveryCycle',
									fieldLabel : '预警周期(天)'
	
								}, {
									xtype : 'textfield',
									name:'information',
									anchor : '80%',
									id:'information',
									fieldLabel : '行业资讯',
									maxLength:250,
									maxLengthText:'行业资讯不能多于250个汉字'
								}, {
									xtype : 'textfield',
									anchor : '80%',
									name:'competeCom',
									id:'competeCom',
									fieldLabel : '合作资料'
								}, {
									xtype : 'textfield',
									name:'financialPositon',
									id:'financialPositon',
									anchor : '80%',
									fieldLabel : '财务状况',
									maxLength:25,
									maxLengthText:'财务状况不能多于25个汉字'
								}, {
									xtype : 'textfield',
									name:'webSite',
									id:'webSite',
									fieldLabel : '网址',
									anchor : '80%',
									maxLength:50,
									maxLengthText:'网址不能多于50个字符'
								},{
									xtype : 'combo',
									name:'settlement',
									triggerAction : 'all',
									id:'settlementCombo',
									anchor : '80%',
									store:settlementStore,
									valueField:'settlement',
									displayField:'settlement',
									model:'local',
									emptyText:'请选择',
									fieldLabel : '结算方式'
								},{
									xtype : 'combo',
									triggerAction : 'all',
									anchor : '80%',
									store : [
										['1','是'],
										['0','否']
									],
									allowBlank : false,
									emptyText : "请选择",
									id:'isprojectcombo',
									forceSelection : true,
									fieldLabel : '是否项目客户',
									editable : false,
									mode : "local",// 获取本地的值
									value:'0',
									hiddenName : 'isProjectcustomer'
								},{
									xtype:'textfield',
									fieldLabel:'最后发货时间',
									anchor : '80%',
									readOnly:true,
									name:'lastBuss',
									id:'lastBuss'
								},{
								xtype:'textfield',
								readOnly:true,
								name:'lastCommunicate',
								anchor : '80%',
								id:'lastCommunicate',
								fieldLabel:'最后沟通时间'
							}]

						}]
		},{
				xtype : 'textarea',
				fieldLabel : '备注',
				name : 'remark',
				id:'remark',
				height:70,
				maxLength:1500,
				allowBlank : true,
				anchor : '95%'
			}],
		tbar : tb,
		listeners:{
			'render':function(form){
				if(parent.cusRecordId!=null){
					form.load({
						url : sysPath+ "/cus/cusRecordAction!list.action",
						params:{filter_EQL_id:parent.cusRecordId,limit : pageSize},
						success:function(){
							
						}
					})
					//setCusRecordMsg(parent.cusRecordId);
				}
			}
		}
	});
	if(cusRecordId!=''){
		form.load({
			url : sysPath+ "/cus/cusRecordAction!list.action",
			params:{filter_EQL_id:cusRecordId,limit : pageSize},
			success:function(){
				
			}
		})
		//setCusRecordMsg(cusRecordId);
	}
	if(mainType=="'single'"){
		parentSetMsg(consigName,consigTel);
	}
	function saveCusRecord(){
		var cusName=Ext.getCmp('cuscombo').getValue();
		var cType=Ext.getCmp('conType').getValue();
		var path='';
		if(cType!='conType'){
			path=sysPath+"/cus/cusRecordAction!save.action";			
		}else{
			path=sysPath+"/cus/cusRecordAction!save.action?conType=conType"
		}
		var cusType=Ext.getCmp('isCq').getValue();
		var settle=Ext.getCmp('settlementCombo').getValue();
		var isPro=Ext.get('isprojectcombo').dom.value;
		if(cusType=='发货代理'){
			if(settle==''||isPro==''){
				Ext.Msg.alert(alertTitle,'客户为发货代理时结算方式和是否为项目客户必填！',onfocus);
				function onfocus(){
					Ext.getCmp('settlementCombo').focus(true,true);
					return;
				}
			}else{
				submitForm(form,cusName,path);
			}
		}else{
			submitForm(form,cusName,path);
		}
	}
					
});

function setCusRecordMsg(cusRecId){
	Ext.Ajax.request({
			url:sysPath+'/cus/cusRecordAction!list.action',
			params:{
				limit:pageSize,
				filter_EQL_id:cusRecId
			},
			success:function(resp){
				var respText = Ext.util.JSON.decode(resp.responseText);
				var record=respText.result[0];
				Ext.getCmp('id').setValue(record.id);
				Ext.getCmp('cuscombo').setValue(record.cusName);
				Ext.getCmp('importanceLevel').setValue(record.importanceLevel);
				Ext.getCmp('profitType').setValue(record.profitType);
				Ext.getCmp('attentionClassify').setValue(record.attentionClassify);
				Ext.getCmp('type1').setValue(record.type1);
				Ext.getCmp('area').setValue(record.area);
				Ext.getCmp('bussAirport').setValue(record.bussAirport);
				Ext.getCmp('bussTel').setValue(record.bussTel);
				Ext.getCmp('isCq').setValue(record.isCq);
				Ext.getCmp('manCount').setValue(record.manCount);
				Ext.getCmp('developLevel').setValue(record.developLevel);
				Ext.getCmp('lastBuss').setValue(record.lastBuss);
				Ext.getCmp('province').setValue(record.province);
				Ext.getCmp('city').setValue(record.city);
				Ext.getCmp('addr').setValue(record.addr);
				Ext.getCmp('settlementCombo').setValue(record.settlement);
				Ext.getCmp('mainBussiness').setValue(record.mainBussiness);
				Ext.getCmp('cusOrigin').setValue(record.cusOrigin);
				Ext.getCmp('cusFrom').setValue(record.cusFrom);
				Ext.getCmp('registerName').setValue(record.registerName);
				Ext.getCmp('aayuNum').setValue(record.aayuNum);
				Ext.getCmp('registerDate').setValue(record.registerDate);
				Ext.getCmp('phone').setValue(record.phone);
				Ext.getCmp('businessEntity').setValue(record.businessEntity);
				Ext.getCmp('lastCommunicate').setValue(record.lastCommunicate);
				Ext.getCmp('scopeBusiness').setValue(record.scopeBusiness);
				Ext.getCmp('expectedCargo').setValue(record.expectedCargo);
				Ext.getCmp('expectedTurnover').setValue(record.expectedTurnover);
				Ext.getCmp('deliveryCycle').setValue(record.deliveryCycle);
				Ext.getCmp('information').setValue(record.information);
				Ext.getCmp('competeCom').setValue(record.competeCom);
				Ext.getCmp('financialPositon').setValue(record.financialPositon);
				Ext.getCmp('startBuss').setValue(record.startBuss);
			}
	});
}

function submitForm(form,cusName,path){
	if(form.getForm().isValid()){
		var id=Ext.getCmp("id").getValue();
		if(id==""){
			Ext.Ajax.request({
				url:sysPath+'/cus/cusRecordAction!list.action',
				params:{
					filter_EQS_cusName:cusName,
					filter_EQL_departId:bussDepart
				},success:function(resp){
					var respText = Ext.util.JSON.decode(resp.responseText);
					if(respText.result.length>0){
						Ext.Msg.alert(alertTitle,'该客户已经存在！');
						return;
					}else{
						formSubmit(form,path);
					}
				}
			});
		}else{
			formSubmit(form,path);
		}
		
	}
}

function formSubmit(form,path){
	form.getForm().submit({
		url : path,
		params:{
			privilege:privilege
		},
		waitMsg : '正在保存数据...',
		success : function(form1, action) {
			Ext.Msg.alert(alertTitle,action.result.msg);
			form.getForm().reset();
		},
		failure : function(form, action) {
			if (action.faliureType == Ext.form.Action.SERVER_INVALID) {
				Ext.Msg.alert(alertTitle, "服务器连接异常，请联系管理员！");
			} else {
				if (action.result.msg) {
					Ext.Msg.alert(alertTitle,action.result.msg);
					}
				}
			}
	});
}
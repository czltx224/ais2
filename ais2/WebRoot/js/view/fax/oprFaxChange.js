var privilege=68;
var fiveWidth=90;
var commentWidth=110;
var d_no;
var updateMsgStore,feeStore,addFeeRecordStore,faxStore,updateMsgRecord,autoFeeStore; 
Ext.onReady(function(){
	var faxfields=[
		{name:'dno'},
		{name:'cusId'},
		{name:'cpName'},
		{name:'flightNo'},
		{name:'flightDate'},
		{name:'flightTime'},
		{name:'trafficMode'},
		{name:'flightMainNo'},
		{name:'subNo'},
		{name:'distributionMode'},
		{name:'takeMode'},
		{name:'receiptType'},
		{name:'consignee'},
		{name:'consigneeTel'},
		{name:'city'},
		{name:'town'},
		{name:'street'},
		{name:'addr'},
		{name:'goWhereId'},
		{name:'goWhere',mapping:'gowhere'},
		{name:'piece'},
		{name:'cqWeight'},
		{name:'cusWeight'},
		{name:'bulk'},
		{name:'inDepart'},
		{name:'curDepart'},
		{name:'endDepart'},
		{name:'distributionDepart'},
		{name:'inDepartId'},
		{name:'curDepartId'},
		{name:'endDepartId'},
		{name:'distributionDepartId'},
		{name:'greenChannel'},
		{name:'urgentService'},
		{name:'wait'},
		{name:'sonderzug'},
		{name:'carType'},
		{name:'roadType'},
		{name:'remark'},
		{name:'status'},
		{name:'barCode'},
		{name:'paymentCollection'},
		{name:'traFee'},
		{name:'traFeeRate'},
		{name:'cpRate'},
		{name:'cpFee'},
		{name:'consigneeRate'},
		{name:'consigneeFee'},
		{name:'whoCash'},
		{name:'faxMainId'},
		{name:'customerService'},
		{name:'cusValueAddFee'},
		{name:'cpValueAddFee'},
		{name:'goodsStatus'},
		{name:'declaredValue'},
		{name:'goods'},
		{name:'valuationType'},
		{name:'areaType'},
		{name:'sonderzugPrice'},
		{name:'ts'},
		{name:'normTraRate'},
		{name:'normCpRate'},
		{name:'normConsigneeRate'},
		{name:'normSonderzugPrice'},
		{name:'cpSonderzugPrice'}
		];
	 faxStore=new Ext.data.Store({
		storeId:"faxStore",
		proxy:new Ext.data.HttpProxy({url:sysPath+"/fax/oprFaxInAction!ralaList.action"}),
		baseParams:{limit:pageSize,privilege:privilege},
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },faxfields)
	});
	var secod=0;
	
	//航班号Store
	var flightStore=new Ext.data.Store({
		storeId:"flightStore",
		proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/basFlightAction!ralaList.action"}),
		baseParams:{limit:pageSize,privilege:62},
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
        	{name:'flightId',mapping:'id'},
        	{name:'flightNumber'},
        	{name:'customerName',mapping:'cusName'}
        	])
	});
	//配送方式Store
	var devModeStore=new Ext.data.Store({
		storeId:"devModeStore",
		baseParams:{filter_EQL_basDictionaryId:4,privilege:16},
		proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/dictionaryAction!ralaList.action"}),
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
        	{name:'id'},
        	{name:'develpMode',mapping:'typeName'}
        	])
	});
	//更改类型Store
	var changeTypeStore=new Ext.data.Store({
		storeId:"changeTypeStore",
		baseParams:{filter_EQL_basDictionaryId:290,privilege:16},
		proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/dictionaryAction!ralaList.action"}),
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
        	{name:'id'},
        	{name:'changeType',mapping:'typeName'}
        	])
	});
	/*
	var locationStore=new Ext.data.Store({
		storeId:"locationStore",
		proxy:new Ext.data.HttpProxy({url:sysPath+"/fax/oprFaxInAction!ralaList.action"}),
		baseParams:{limit:pageSize,privilege:privilege},
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },faxfields)
	});*/
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
	
	var traStore=new Ext.data.Store({
		storeId:"cusStore",
		baseParams:{limit:pageSize},
		proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/customerAction!getTraCustomer.action"}),
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
        	{name:'goWhereId',mapping:'id'},
        	{name:'goWhere',mapping:'cusName'}
        	])
	});
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
	//地址类型Sotre
	var addrStore=new Ext.data.Store({
		storeId:"addrStore",
		proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/dictionaryAction!ralaList.action"}),
		baseParams:{filter_EQL_basDictionaryId:3,privilege:16},
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
        	{name:'id'},
        	{name:'areaType',mapping:'typeName'}
        	])
	});
	
	//回单类型Store
	var receiptTypeStore=new Ext.data.Store({
		storeId:"receiptTypeStore",
		proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/dictionaryAction!ralaList.action"}),
		baseParams:{filter_EQL_basDictionaryId:12,privilege:16},
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
        	{name:'id'},
        	{name:'receiptType',mapping:'typeName'}
        	])
	});

	//运输方式Store
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
	//付款方式Store
	var payMethodStore=new Ext.data.Store({
		storeId:"payMethodStore",
		proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/dictionaryAction!ralaList.action"}),
		baseParams:{filter_EQL_basDictionaryId:19,privilege:16},
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
        	{name:'id'},
        	{name:'payMethod',mapping:'typeName'}
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
	//增值服务费Store
	var addFeeStore=new Ext.data.Store({
		storeId:"roadTypeStore",
		proxy:new Ext.data.HttpProxy({url:sysPath+"/basvalueaddfee/basValueAddFeeAction!ralaList.action"}),
		baseParams:{limit:pageSize,privilege:25},
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
        	{name:'id'},
        	{name:'feeName'}
        	])
	});
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
	//收货人信息Store
	var consigneeStore=new Ext.data.Store({
		storeId:"consigneeStore",
		proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/conInfoAction!list.action"}),
		baseParams:{limit:pageSize},
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
        	{name:'id'},
        	{name:'consigneeName'},
        	{name:'consigneeTel'},
        	{name:'consigneeAddr'},
        	{name:'addrType'},
        	{name:'distributionMode'},
        	{name:'city'},
        	{name:'town'},
        	{name:'endDepart'},
        	{name:'street'},
        	{name:'goWhere'},
        	{name:'goWhereId'},
        	{name:'distributionDepart'},
        	{name:'ts'}
        	])
	});
	//确认修改
	function confirmChange(){
		//alert(Ext.getCmp('goWhereIdcombo').getValue());
		Ext.getCmp('savefaxbtn').setDisabled(false);
		updateMsgStore.removeAll();
		//var record = locationRecord.getSelectionModel().getSelections();
		//var d_no=record[0].data.dno;
		var faxRecord;
		for(var i=0;i<faxStore.getCount();i++){
			var d=faxStore.getAt(i).get("dno");
			if(d==d_no){
				faxRecord=faxStore.getAt(i);
				break;
			}
		}
		var store=new Ext.data.Record.create([
		 {name:'changeField'},
	     {name:'changeFieldZh'},
	     {name:'changePre'},
	     {name:'changePost'}]);
	    var fdata=faxRecord.data;
	    var trafficMode=Ext.getCmp('trafficModecombo').getValue();
		var cusId=Ext.getCmp('cuscombo').getValue();
		var cpName=Ext.getCmp('cpName').getValue();
		var flightMainNo=Ext.getCmp('flightMain').getValue();
		var flightNo=Ext.getCmp('flightcombo').getValue();
		var flightDate=Ext.getCmp('arrivetime').getValue().format('Y-m-d');
		var sonderzug=Ext.getCmp('sonderzugVal').getValue();
		var urgentService=Ext.getCmp('urgentServiceVal').getValue();
		var wait=Ext.getCmp('waitVal').getValue();
		var carType=Ext.getCmp('cartypecombo').getValue();
    	var roadType=Ext.getCmp('roadtypecombo').getValue();
		var sonderzugPrice=Ext.getCmp('sonderzugPrice').getValue();
		var cpSonderzugPrice=Ext.getCmp('cpSonderzugPrice').getValue();
		var subNo=Ext.getCmp('subNo').getValue();
		var takeMode=Ext.getCmp('takeModecombo').getValue();
		var receiptType=Ext.getCmp('receiptTypecombo').getValue();
		var distributionMode=Ext.getCmp('distributionMode').getValue();
		var consigneeTel=Ext.getCmp('consigneeTel').getValue();
		var consignee=Ext.getCmp('consignee').getValue();
		var city=Ext.get('city').dom.value;
		var town=Ext.get('town').dom.value;
		var street=Ext.get('street').dom.value;
		var addr=Ext.getCmp('consigneeAddress').getValue();
		var goWhere=Ext.getCmp('goWhere').getValue();
		var goWhereId=Ext.getCmp('goWhereIdcombo').getValue();
		var goods=Ext.getCmp('goods').getValue();
		var valuationType=Ext.getCmp('chargemodecombo').getValue();
		var piece=Ext.getCmp('piece').getValue();
		var bulk=Ext.getCmp('bulk').getValue();
		var cusWeight=Ext.getCmp('cusWeight').getValue();
		var cqWeight=Ext.getCmp('cqWeight').getValue();
		var whoCash=Ext.getCmp('payerCombo').getValue();
		var traFeeRate=Ext.getCmp('traFeeRate').getValue();
		var traFee=Ext.getCmp('traFee').getValue();
		var cpRate=Ext.getCmp('cpRate').getValue();
		var cpFee=Ext.getCmp('cpFee').getValue();
		var paymentCollection=Ext.getCmp('paymentCollection').getValue();
		var consigneeRate=Ext.getCmp('consigneeRate').getValue();
		var consigneeFee=Ext.getCmp('consigneeFee').getValue();
		var remark=Ext.getCmp('remark').getValue();
		var cpValueAddFee=Ext.getCmp('cpValueAddFee').getValue();
		var cusValueAddFee=Ext.getCmp('cusValueAddFee').getValue();
		
		var disDepartId=Ext.getCmp('distributionDepartId').getValue();
		var disDepart=Ext.getCmp('distributionDepart').getValue();
		var endDepartId=Ext.getCmp('endDepartId').getValue();
		var endDepart=Ext.getCmp('endDepart').getValue();
		
		if(cusId!=fdata.cusId){
			var noFaxRecord=new store();
			noFaxRecord.set('changeField','cusId');
			noFaxRecord.set('changeFieldZh','代理公司编号');
			noFaxRecord.set('changePre',fdata.cusId);
			noFaxRecord.set('changePost',cusId);
			
			var noFaxRecord1=new store();
			noFaxRecord1.set('changeField','cpName');
			noFaxRecord1.set('changeFieldZh','代理公司名称');
			noFaxRecord1.set('changePre',fdata.cpName);
			noFaxRecord1.set('changePost',cpName);
			updateMsgStore.add(noFaxRecord);
			updateMsgStore.add(noFaxRecord1);
		}
		if(trafficMode!=fdata.trafficMode){
			var noFaxRecord1=new store();
			noFaxRecord1.set('changeField','trafficMode');
			noFaxRecord1.set('changeFieldZh','运输方式');
			noFaxRecord1.set('changePre',fdata.trafficMode);
			noFaxRecord1.set('changePost',trafficMode);
			updateMsgStore.add(noFaxRecord1);
		}
		if(flightMainNo!=fdata.flightMainNo){
			var noFaxRecord1=new store();
			noFaxRecord1.set('changeField','flightMainNo');
			noFaxRecord1.set('changeFieldZh','主单号');
			noFaxRecord1.set('changePre',fdata.flightMainNo);
			noFaxRecord1.set('changePost',flightMainNo);
			updateMsgStore.add(noFaxRecord1);
		}
		if(flightNo!=fdata.flightNo){
			var noFaxRecord1=new store();
			noFaxRecord1.set('changeField','flightNo');
			noFaxRecord1.set('changeFieldZh','班次号');
			noFaxRecord1.set('changePre',fdata.flightNo);
			noFaxRecord1.set('changePost',flightNo);
			updateMsgStore.add(noFaxRecord1);
		}
		if(flightDate!=fdata.flightDate){
			var noFaxRecord1=new store();
			noFaxRecord1.set('changeField','flightDate');
			noFaxRecord1.set('changeFieldZh','班次日期');
			noFaxRecord1.set('changePre',fdata.flightDate);
			noFaxRecord1.set('changePost',flightDate);
			updateMsgStore.add(noFaxRecord1);
		}
		if(sonderzug!=fdata.sonderzug){
			var noFaxRecord1=new store();
			noFaxRecord1.set('changeField','sonderzug');
			noFaxRecord1.set('changeFieldZh','是否专车');
			if(fdata.sonderzug==1){
				noFaxRecord1.set('changePre','是');
			}else if(fdata.sonderzug==0){
				noFaxRecord1.set('changePre','否');
			}
			if(sonderzug==1){
				noFaxRecord1.set('changePost','是');
			}else if(sonderzug==0){
				noFaxRecord1.set('changePost','否');
			}
			updateMsgStore.add(noFaxRecord1);
		}
		if(urgentService!=fdata.urgentService){
			var noFaxRecord1=new store();
			noFaxRecord1.set('changeField','urgentService');
			noFaxRecord1.set('changeFieldZh','是否加急');
			if(fdata.urgentService==1){
				noFaxRecord1.set('changePre','是');
			}else if(fdata.urgentService==0){
				noFaxRecord1.set('changePre','否');
			}
			if(urgentService==1){
				noFaxRecord1.set('changePost','是');
			}else if(urgentService==0){
				noFaxRecord1.set('changePost','否');
			}
			updateMsgStore.add(noFaxRecord1);
		}
		if(wait!=fdata.wait){
			var noFaxRecord1=new store();
			noFaxRecord1.set('changeField','wait');
			noFaxRecord1.set('changeFieldZh','等通知放货');
			if(fdata.wait==1){
				noFaxRecord1.set('changePre','是');
			}else if(fdata.wait==0){
				noFaxRecord1.set('changePre','否');
			}
			if(wait==1){
				noFaxRecord1.set('changePost','是');
			}else if(wait==0){
				noFaxRecord1.set('changePost','否');
			}
			updateMsgStore.add(noFaxRecord1);
		}
		if(carType!=fdata.carType){
			var noFaxRecord1=new store();
			noFaxRecord1.set('changeField','carType');
			noFaxRecord1.set('changeFieldZh','车型');
			noFaxRecord1.set('changePre',fdata.carType);
			noFaxRecord1.set('changePost',carType);
			updateMsgStore.add(noFaxRecord1);
		}
		if(roadType!=fdata.roadType){
			var noFaxRecord1=new store();
			noFaxRecord1.set('changeField','roadType');
			noFaxRecord1.set('changeFieldZh','路型');
			noFaxRecord1.set('changePre',fdata.roadType);
			noFaxRecord1.set('changePost',roadType);
			updateMsgStore.add(noFaxRecord1);
		}
		if(sonderzugPrice!=fdata.sonderzugPrice){
			var noFaxRecord1=new store();
			noFaxRecord1.set('changeField','sonderzugPrice');
			noFaxRecord1.set('changeFieldZh','到付专车费');
			noFaxRecord1.set('changePre',fdata.sonderzugPrice);
			if(sonderzugPrice==''){
				sonderzugPrice=0;
			}
			noFaxRecord1.set('changePost',sonderzugPrice);
			updateMsgStore.add(noFaxRecord1);		
		}
		if(cpSonderzugPrice!=fdata.cpSonderzugPrice){
			var noFaxRecord1=new store();
			noFaxRecord1.set('changeField','cpSonderzugPrice');
			noFaxRecord1.set('changeFieldZh','预付专车费');
			noFaxRecord1.set('changePre',fdata.cpSonderzugPrice);
			if(cpSonderzugPrice==''){
				cpSonderzugPrice=0;
			}
			noFaxRecord1.set('changePost',cpSonderzugPrice);
			updateMsgStore.add(noFaxRecord1);
		}
		if(subNo!=fdata.subNo){
			var noFaxRecord1=new store();
			noFaxRecord1.set('changeField','subNo');
			noFaxRecord1.set('changeFieldZh','分单号');
			noFaxRecord1.set('changePre',fdata.subNo);
			noFaxRecord1.set('changePost',subNo);
			updateMsgStore.add(noFaxRecord1);
		}
		if(takeMode!=fdata.takeMode){
			var noFaxRecord1=new store();
			noFaxRecord1.set('changeField','takeMode');
			noFaxRecord1.set('changeFieldZh','提货方式');
			noFaxRecord1.set('changePre',fdata.takeMode);
			noFaxRecord1.set('changePost',takeMode);
			updateMsgStore.add(noFaxRecord1);
		}
		if(receiptType!=fdata.receiptType){
			var noFaxRecord1=new store();
			noFaxRecord1.set('changeField','receiptType');
			noFaxRecord1.set('changeFieldZh','回单类型');
			noFaxRecord1.set('changePre',fdata.receiptType);
			noFaxRecord1.set('changePost',receiptType);
			updateMsgStore.add(noFaxRecord1);
		}
		if(distributionMode!=fdata.distributionMode){
			var noFaxRecord1=new store();
			noFaxRecord1.set('changeField','distributionMode');
			noFaxRecord1.set('changeFieldZh','配送方式');
			noFaxRecord1.set('changePre',fdata.distributionMode);
			noFaxRecord1.set('changePost',distributionMode);
			updateMsgStore.add(noFaxRecord1);
		}
		if(consigneeTel!=fdata.consigneeTel){
			var noFaxRecord1=new store();
			noFaxRecord1.set('changeField','consigneeTel');
			noFaxRecord1.set('changeFieldZh','收货人电话');
			noFaxRecord1.set('changePre',fdata.consigneeTel);
			noFaxRecord1.set('changePost',consigneeTel);
			updateMsgStore.add(noFaxRecord1);
		}
		if(consignee!=fdata.consignee){
			var noFaxRecord1=new store();
			noFaxRecord1.set('changeField','consignee');
			noFaxRecord1.set('changeFieldZh','收货人姓名');
			noFaxRecord1.set('changePre',fdata.consignee);
			noFaxRecord1.set('changePost',consignee);
			updateMsgStore.add(noFaxRecord1);
		}
		if(city!=fdata.city){
			var noFaxRecord1=new store();
			noFaxRecord1.set('changeField','city');
			noFaxRecord1.set('changeFieldZh','市');
			noFaxRecord1.set('changePre',fdata.city);
			noFaxRecord1.set('changePost',city);
			updateMsgStore.add(noFaxRecord1);
		}
		if(town!=fdata.town){
			var noFaxRecord1=new store();
			noFaxRecord1.set('changeField','town');
			noFaxRecord1.set('changeFieldZh','区/县');
			noFaxRecord1.set('changePre',fdata.town);
			noFaxRecord1.set('changePost',town);
			updateMsgStore.add(noFaxRecord1);
		}
		if(street!=fdata.street){
			var noFaxRecord1=new store();
			noFaxRecord1.set('changeField','street');
			noFaxRecord1.set('changeFieldZh','镇/街道');
			noFaxRecord1.set('changePre',fdata.street);
			noFaxRecord1.set('changePost',street);
			updateMsgStore.add(noFaxRecord1);
		}
		if(addr!=fdata.addr){
			var noFaxRecord1=new store();
			noFaxRecord1.set('changeField','addr');
			noFaxRecord1.set('changeFieldZh','收货人地址');
			noFaxRecord1.set('changePre',fdata.addr);
			noFaxRecord1.set('changePost',addr);
			updateMsgStore.add(noFaxRecord1);
		}
		if(goWhereId!=fdata.goWhereId){
			var noFaxRecord1=new store();
			noFaxRecord1.set('changeField','goWhereId');
			noFaxRecord1.set('changeFieldZh','供应商Id');
			noFaxRecord1.set('changePre',fdata.goWhereId);
			noFaxRecord1.set('changePost',goWhereId);
			updateMsgStore.add(noFaxRecord1);
		}
		if(goWhere!=fdata.goWhere){
			var noFaxRecord1=new store();
			noFaxRecord1.set('changeField','gowhere');
			noFaxRecord1.set('changeFieldZh','供应商');
			noFaxRecord1.set('changePre',fdata.goWhere);
			noFaxRecord1.set('changePost',goWhere);
			updateMsgStore.add(noFaxRecord1);
		}
		if(goods!=fdata.goods){
			var noFaxRecord1=new store();
			noFaxRecord1.set('changeField','goods');
			noFaxRecord1.set('changeFieldZh','货物名称');
			noFaxRecord1.set('changePre',fdata.goods);
			noFaxRecord1.set('changePost',goods);
			updateMsgStore.add(noFaxRecord1);
		}
		if(valuationType!=fdata.valuationType){
			var noFaxRecord1=new store();
			noFaxRecord1.set('changeField','valuationType');
			noFaxRecord1.set('changeFieldZh','计费方式');
			noFaxRecord1.set('changePre',fdata.valuationType);
			noFaxRecord1.set('changePost',valuationType);
			updateMsgStore.add(noFaxRecord1);
		}
		if(piece!=fdata.piece){
			var noFaxRecord1=new store();
			noFaxRecord1.set('changeField','piece');
			noFaxRecord1.set('changeFieldZh','件数');
			noFaxRecord1.set('changePre',fdata.piece);
			noFaxRecord1.set('changePost',piece);
			updateMsgStore.add(noFaxRecord1);
		}
		if(bulk!=fdata.bulk){
			var noFaxRecord1=new store();
			noFaxRecord1.set('changeField','bulk');
			noFaxRecord1.set('changeFieldZh','体积');
			noFaxRecord1.set('changePre',fdata.bulk);
			noFaxRecord1.set('changePost',bulk);
			updateMsgStore.add(noFaxRecord1);
		}
		if(cusWeight!=fdata.cusWeight){
			var noFaxRecord1=new store();
			noFaxRecord1.set('changeField','cusWeight');
			noFaxRecord1.set('changeFieldZh','计费重量');
			noFaxRecord1.set('changePre',fdata.cusWeight);
			noFaxRecord1.set('changePost',cusWeight);
			updateMsgStore.add(noFaxRecord1);
		}
		if(cqWeight!=fdata.cqWeight){
			var noFaxRecord1=new store();
			noFaxRecord1.set('changeField','cqWeight');
			noFaxRecord1.set('changeFieldZh','开单重量');
			noFaxRecord1.set('changePre',fdata.cqWeight);
			noFaxRecord1.set('changePost',cqWeight);
			updateMsgStore.add(noFaxRecord1);
		}
		if(paymentCollection!=fdata.paymentCollection){
			var noFaxRecord1=new store();
			noFaxRecord1.set('changeField','paymentCollection');
			noFaxRecord1.set('changeFieldZh','代收运费');
			noFaxRecord1.set('changePre',fdata.paymentCollection);
			noFaxRecord1.set('changePost',paymentCollection);
			updateMsgStore.add(noFaxRecord1);		
		}
		if(whoCash!=fdata.whoCash){
			var noFaxRecord1=new store();
			noFaxRecord1.set('changeField','whoCash');
			noFaxRecord1.set('changeFieldZh','付款方');
			noFaxRecord1.set('changePre',fdata.whoCash);
			noFaxRecord1.set('changePost',whoCash);
			updateMsgStore.add(noFaxRecord1);		
		}
		if(traFeeRate!=fdata.traFeeRate){
			var noFaxRecord1=new store();
			noFaxRecord1.set('changeField','traFeeRate');
			noFaxRecord1.set('changeFieldZh','中转费率');
			noFaxRecord1.set('changePre',fdata.traFeeRate);
			noFaxRecord1.set('changePost',traFeeRate);
			updateMsgStore.add(noFaxRecord1);		
		}
		if(traFee!=fdata.traFee){
			var noFaxRecord1=new store();
			noFaxRecord1.set('changeField','traFee');
			noFaxRecord1.set('changeFieldZh','中转费');
			noFaxRecord1.set('changePre',fdata.traFee);
			noFaxRecord1.set('changePost',traFee);
			updateMsgStore.add(noFaxRecord1);		
		}
		if(cpRate!=fdata.cpRate){
			var noFaxRecord1=new store();
			noFaxRecord1.set('changeField','cpRate');
			noFaxRecord1.set('changeFieldZh','预付提送费率');
			noFaxRecord1.set('changePre',fdata.cpRate);
			noFaxRecord1.set('changePost',cpRate);
			updateMsgStore.add(noFaxRecord1);		
		}
		if(cpFee!=fdata.cpFee){
			var noFaxRecord1=new store();
			noFaxRecord1.set('changeField','cpFee');
			noFaxRecord1.set('changeFieldZh','预付提送费');
			noFaxRecord1.set('changePre',fdata.cpFee);
			noFaxRecord1.set('changePost',cpFee);
			updateMsgStore.add(noFaxRecord1);		
		}
		if(cpValueAddFee!=fdata.cpValueAddFee){
			var noFaxRecord1=new store();
			noFaxRecord1.set('changeField','cpValueAddFee');
			noFaxRecord1.set('changeFieldZh','预付增值费');
			noFaxRecord1.set('changePre',fdata.cpValueAddFee);
			noFaxRecord1.set('changePost',cpValueAddFee);
			updateMsgStore.add(noFaxRecord1);
		}
		if(consigneeRate!=fdata.consigneeRate){
			var noFaxRecord1=new store();
			noFaxRecord1.set('changeField','consigneeRate');
			noFaxRecord1.set('changeFieldZh','到付提送费率');
			noFaxRecord1.set('changePre',fdata.consigneeRate);
			noFaxRecord1.set('changePost',consigneeRate);
			updateMsgStore.add(noFaxRecord1);		
		}
		if(consigneeFee!=fdata.consigneeFee){
			var noFaxRecord1=new store();
			noFaxRecord1.set('changeField','consigneeFee');
			noFaxRecord1.set('changeFieldZh','到付提送费');
			noFaxRecord1.set('changePre',fdata.consigneeFee);
			noFaxRecord1.set('changePost',consigneeFee);
			updateMsgStore.add(noFaxRecord1);		
		}
		if(cusValueAddFee!=fdata.cusValueAddFee){
			var noFaxRecord1=new store();
			noFaxRecord1.set('changeField','cusValueAddFee');
			noFaxRecord1.set('changeFieldZh','到付增值费');
			noFaxRecord1.set('changePre',fdata.cusValueAddFee);
			noFaxRecord1.set('changePost',cusValueAddFee);
			updateMsgStore.add(noFaxRecord1);
		}
		if(remark!=fdata.remark){
			var noFaxRecord1=new store();
			noFaxRecord1.set('changeField','remark');
			noFaxRecord1.set('changeFieldZh','备注');
			noFaxRecord1.set('changePre',fdata.remark);
			noFaxRecord1.set('changePost',remark);
			updateMsgStore.add(noFaxRecord1);		
		}
		if(endDepartId != fdata.endDepartId){
			var noFaxRecord1=new store();
			noFaxRecord1.set('changeField','endDepartId');
			noFaxRecord1.set('changeFieldZh','终端部门Id');
			noFaxRecord1.set('changePre',fdata.endDepartId);
			if(endDepartId == null || endDepartId == ''){
				endDepartId = bussDepart;
			}
			noFaxRecord1.set('changePost',endDepartId);
			updateMsgStore.add(noFaxRecord1);
		}
		if(endDepart != fdata.endDepart){
			var noFaxRecord1=new store();
			noFaxRecord1.set('changeField','endDepart');
			noFaxRecord1.set('changeFieldZh','终端部门');
			noFaxRecord1.set('changePre',fdata.endDepart);
			if(endDepart == null || endDepart == ''){
				endDepart = bussDepartName;
			}
			noFaxRecord1.set('changePost',endDepart);
			updateMsgStore.add(noFaxRecord1);
		}
		if(disDepartId != fdata.distributionDepartId){
			var noFaxRecord1=new store();
			noFaxRecord1.set('changeField','distributionDepartId');
			noFaxRecord1.set('changeFieldZh','配送部门ID');
			noFaxRecord1.set('changePre',fdata.distributionDepartId);
			if(disDepartId == null || disDepartId == ''){
				disDepartId = bussDepart;
			}
			noFaxRecord1.set('changePost',disDepartId);
			updateMsgStore.add(noFaxRecord1);
		}
		if(disDepart != fdata.distributionDepart){
			var noFaxRecord1=new store();
			noFaxRecord1.set('changeField','distributionDepart');
			noFaxRecord1.set('changeFieldZh','配送部门');
			noFaxRecord1.set('changePre',fdata.distributionDepart);
			if(disDepart == null || disDepart == ''){
				disDepart = bussDepartName;
			}
			noFaxRecord1.set('changePost',disDepart);
			updateMsgStore.add(noFaxRecord1);
		}
		
		var isSystem=1;//是否为系统默认  默认为是
		var feedata=feeStore.getAt(0).data;
		var sonderzugPrice=feedata.sonderzugPrice;
		var cpSonderzugPrice=feedata.cpSonderzugPrice;
		var curSonderPrice=Ext.getCmp('sonderzugPrice').getValue();
		var curcpSonderPrice=Ext.getCmp('cpSonderzugPrice').getValue();
		var paymentCollection=feedata.paymentCollection;
		var curPayment=Ext.getCmp('paymentCollection').getValue();
		var traFee=feedata.traFee;
		var curTraFee=Ext.getCmp('traFee').getValue();
		var cpFee=feedata.cpFee;
		var curCpFee=Ext.getCmp('cpFee').getValue();
		var consigneeFee=feedata.consigneeFee;
		var updateRemark=Ext.getCmp('updateRemark').getValue();
		var curConsigneeFee=Ext.getCmp('consigneeFee').getValue();
		var cpAddFee = feedata.cpValueAddFee;
		var curCpAddFee=Ext.getCmp('cpValueAddFee').getValue();
		var cusAddFee=feedata.cusValueAddFee;
		var curCusAddFee=Ext.getCmp('cusValueAddFee').getValue();
		

		if((cpSonderzugPrice!=curcpSonderPrice&&cpSonderzugPrice>curcpSonderPrice)||(sonderzugPrice!=curSonderPrice&&sonderzugPrice>curSonderPrice)||(paymentCollection!=curPayment&&paymentCollection<curPayment)||(traFee!=curTraFee&&traFee<curTraFee)||(cpFee!=curCpFee&&cpFee>curCpFee)||(consigneeFee!=curConsigneeFee&&consigneeFee>curConsigneeFee)||(cpAddFee!=curCpAddFee&&cpAddFee>curCpAddFee)||(cusAddFee!=curCusAddFee&&cusAddFee>curCusAddFee)){
			isSystem=0;
		}
		if(whoCash!=fdata.whoCash && whoCash=='预付'){
			isSystem=0;
		}
		if(isSystem==0 && Ext.getCmp('changetypecombo').getValue() == '账款成本类'){
			Ext.getCmp('showMsg').getEl().update('<span style="color:red">您的更改需要走审核流程，如果系统计算存在误差，请重新修改！');
		}else{
			Ext.getCmp('showMsg').getEl().update('<span style="color:red">您的更改可直接通过，不需要走更改流程!');
		}
	}
	//保存修改
	function saveChange(){
		Ext.getCmp('savefaxbtn').setDisabled(true);
		
		if(updateMsgStore.getCount()<1){
			Ext.Msg.alert(alertTitle,'您没有修改记录或者没有确认修改，请勿保存!',onfocus);
			function onfocus(){
				return false;
			}
		}else{
			if(form.getForm().isValid()&&Ext.getCmp('changetypecombo').isValid()){
				if(msgValidate(form)){
					var myMask = new Ext.LoadMask(Ext.getBody(), {
		               	 msg: '正在保存，请稍后！',
		                removeMask: true //完成后移除
		        	});
		       		myMask.show();
					var isSystem=1;//是否为系统默认  默认为是
					var cusId=Ext.getCmp('cuscombo').getValue();
					var dno=Ext.getCmp('dno').getValue();
					var flightMainNo=Ext.getCmp('flightMain').getValue();
					var feedata=feeStore.getAt(0).data;
					var sonderzugPrice=feedata.sonderzugPrice;
					var cpSonderzugPrice=feedata.cpSonderzugPrice;
					var curSonderPrice=Ext.getCmp('sonderzugPrice').getValue();
					var curcpSonderPrice=Ext.getCmp('cpSonderzugPrice').getValue();
					var paymentCollection=feedata.paymentCollection;
					var curPayment=Ext.getCmp('paymentCollection').getValue();
					var traFee=feedata.traFee;
					var curTraFee=Ext.getCmp('traFee').getValue();
					var cpFee=feedata.cpFee;
					var curCpFee=Ext.getCmp('cpFee').getValue();
					var consigneeFee=feedata.consigneeFee;
					var updateRemark=Ext.getCmp('updateRemark').getValue();
					var curConsigneeFee=Ext.getCmp('consigneeFee').getValue();
					var cpAddFee = feedata.cpValueAddFee;
					var curCpAddFee=Ext.getCmp('cpValueAddFee').getValue();
					var cusAddFee=feedata.cusValueAddFee;
					var curCusAddFee=Ext.getCmp('cusValueAddFee').getValue();
					//var payer=Ext.getCmp('payerCombo').getValue();
					
					var changeType=Ext.getCmp('changetypecombo').getValue();
					var changeTypes="changeType="+changeType;

					if((cpSonderzugPrice!=curcpSonderPrice&&cpSonderzugPrice>curcpSonderPrice)||(sonderzugPrice!=curSonderPrice&&sonderzugPrice>curSonderPrice)||(paymentCollection!=curPayment&&paymentCollection<curPayment)||(traFee!=curTraFee&&traFee<curTraFee)||(cpFee!=curCpFee&&cpFee>curCpFee)||(consigneeFee!=curConsigneeFee&&consigneeFee>curConsigneeFee)||(cpAddFee!=curCpAddFee&&cpAddFee>curCpAddFee)||(cusAddFee!=curCusAddFee&&cusAddFee>curCusAddFee)){
						isSystem=0;
					}
//					if(payer=='双方付'){
//						if((sonderzugPrice+cpSonderzugPrice)>(curSonderPrice+curcpSonderPrice)||(cpFee+consigneeFee)>(curCpFee+curConsigneeFee)){
//							isSystem=0;
//						}
//					}
					for(var i=0;i<updateMsgStore.getCount();i++){
						var zhname=updateMsgStore.getAt(i).data.changeFieldZh;
						if((zhname=='代理公司名称'&&isSystem==1&&updateMsgStore.getCount()!=2)&&(zhname=='代理公司名称'&&isSystem==1&&updateMsgStore.getCount()!=4)){
							Ext.Msg.alert(alertTitle,'您更改了代理公司，其它项不能修改！');
							myMask.hide();
							return false;
						}
					}
					
					var changeDetail="";
					for(var i=0;i<updateMsgStore.getCount();i++){
						var dataPre="";
						var dataPost="";
						var data=updateMsgStore.getAt(i).data;
//						if(data.changeFieldZh=='代理公司名称'||data.changeFieldZh=='供应商'){
//							isSystem=0;
//						}
						if(data.changePre=='是'){
							dataPre="1";
						}else if(data.changePre=="否"){
							dataPre="0";
						}else{
							dataPre=data.changePre;			
						}
						if(data.changePost=='是'){
							dataPost="1";
						}else if(data.changePost=="否"){
							dataPost="0";
						}else{
							dataPost=data.changePost;			
						}
						if(i>0){
							changeDetail+="&";
						}
						changeDetail+="changeDetailList["+i+"].changeField="+data.changeField+"&changeDetailList["+i+"].changeFieldZh="+data.changeFieldZh+"&changeDetailList["+i+"].changePre="+dataPre+"&changeDetailList["+i+"].changePost="+dataPost+"";
					}
					changeDetail+="&dno="+dno+"&isSystem="+isSystem+"&remark="+updateRemark+"&departId="+bussDepart;
					var cId="";
					var sonderP=0;
					Ext.Ajax.request({
						url:sysPath+"/fax/oprChangeAction!ralaList.action",
						params:{
							filter_EQL_dno:dno,
							filter_EQL_status:1,
							filter_EQL_departId:bussDepart,
							privilege:233
						},
						success:function(resp){
							var upoprfaxin="ofi.dno="+dno+"&ofi.flightMainNo="+flightMainNo+"&ofi.ts=78945645";
							for(var i=0;i<updateMsgStore.getCount();i++){
								var dataPre="";
								var dataPost="";
								var data=updateMsgStore.getAt(i).data;
								if(data.changeFieldZh=='代理公司名称'){
									cId=data.changePost;
								}
								if(data.changeFieldZh=='专车费'){
									sonderP=data.changePost;
								}
//								if(data.changeFieldZh=='代理公司名称'||data.changeFieldZh=='供应商'){
//									isSystem=0;
//								}
								if(data.changePre=='是'){
									dataPre="1";
								}else if(data.changePre=="否"){
									dataPre="0";
								}else{
									dataPre=data.changePre;			
								}
								
								if(data.changePost=='是'){
									dataPost="1";
								}else if(data.changePost=="否"){
									dataPost="0";
								}else{
									dataPost=data.changePost;			
								}
								upoprfaxin+="&ofi."+data.changeField+"="+dataPost+"";
							}
							if(cId==""){
								upoprfaxin+="&ofi.cusId="+cusId;
							}
							/*
							if(sonderP==0){
								upoprfaxin+="&ofi.sonderzugPrice="+sonderP;
							}*/
			
							var respText = Ext.util.JSON.decode(resp.responseText);
							if(respText.result.length>0){
								Ext.Msg.alert(alertTitle,"该配送单更改项正在审核，无法继续更改！");
								Ext.getCmp('savefaxbtn').setDisabled(false);
								myMask.hide();
								return;
							}else{
								var datas="";
								for(var i=0;i<addFeeRecordStore.getCount();i++){
									if(i>0){
										datas+='&';
									}
									datas += "addFeeList["+i+"].feeName="+addFeeRecordStore.getAt(i).get('feeName')+'&'
							  			  +"addFeeList["+i+"].feeCount="+addFeeRecordStore.getAt(i).get('feeCount')+'&'
							  			  +"addFeeList["+i+"].id="+addFeeRecordStore.getAt(i).get('feeNameId')+'&'
							  			  +"addFeeList["+i+"].payType="+addFeeRecordStore.getAt(i).get('payType');
								}
								Ext.Ajax.request({
									url:sysPath+"/fax/oprChangeAction!save.action",
									params:changeDetail+"&"+upoprfaxin+"&"+datas+"&"+changeTypes,
									success:function(resp){
										var respText = Ext.util.JSON.decode(resp.responseText);
										if(respText.success){
											myMask.hide();
											Ext.Msg.alert(alertTitle,"保存成功！");
											Ext.getCmp('showMsg').getEl().update('<span style="color:red">您的更改项已经保存，数据更改情况请在\'我的流程-我的请求\'中查看！');
//											if(isSystem==1){
//												Ext.getCmp('showMsg').getEl().update('<span style="color:red">您更改的项不需要审核！');
//											}else{
//												Ext.getCmp('showMsg').getEl().update('<span style="color:red">您更改的项需要审核，申请已提交！');
//											}
											Ext.getCmp('savefaxbtn').setDisabled(true);
											form.getForm().reset();
											addFeeRecordStore.removeAll();
											updateMsgStore.removeAll();
			    							feeStore.removeAll();
			    							faxStore.removeAll();
										}else{
											Ext.getCmp('savefaxbtn').setDisabled(false);
											Ext.Msg.alert(alertTitle,respText.msg);
											myMask.hide();
										}
									}
								});
							}
						}
					});
				}
			}
		}
	}
	var tb=new Ext.Toolbar({
		width:Ext.lib.Dom.getViewWidth(),
		items:['&nbsp;&nbsp;', {
    			text : '<B>保存</B>',
    			id : 'savefaxbtn',
    			tooltip : '保存所有传真',
    			iconCls : 'save',
    			disabled:true,
    			handler:function(){
    				Ext.Msg.confirm(alertTitle, "是否确定保存？", function(btnYes) {
						if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
							saveChange();
						}
    				});
    			}
    		},'-','&nbsp;&nbsp;', {
    			text : '<B>定位</B>',
    			id : 'resetfaxbtn',
    			tooltip : '定位',
    			handler:function(){
    				location();
    			}
    		},'-','&nbsp;&nbsp;', {
    			text : '<B>清空数据</B>',
    			id : 'returnfaxbtn',
    			tooltip : '清除数据',
    			handler:function(){
    				Ext.Msg.confirm(alertTitle, "是否确定清空数据？", function(btnYes) {
						if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
							updateMsgStore.removeAll();
		    				faxStore.removeAll();
		    				form.getForm().reset();
						}
    				});
    			}
    		},'-','&nbsp;&nbsp;',{
    			text : '<B>确认修改</B>',
    			id : 'confirmUpdate',
    			tooltip : '修改确认',
    			handler:function(){
    				if(form.getForm().isValid()){
    					if(Ext.getCmp('changetypecombo').isValid()&& Ext.getCmp('updateRemark').isValid()){
    						confirmChange();
    					}else{
    						Ext.Msg.alert(alertTitle,'请选择更改类型或者填写更改备注后再次尝试!',onfocus);
    						function onfocus(){
    							Ext.getCmp('changetypecombo').focus(true,true);
    						}
    					}
    				}
    			}
    		},'-','更改类型<span style="color:red">*</span>',{
    			xtype:'combo',
    			id:'changetypecombo',
    			name:'changeType',
    			store:changeTypeStore,
    			forceSelection : true,
				allowBlank : false,
				width:80,
				valueField : 'changeType',
				displayField : 'changeType',
				triggerAction : 'all',
				blankText:'更改类型不能为空!'
    		},'-','&nbsp;&nbsp;更改备注<span style="color:red">*</span>',{
    			xtype:'textfield',
    			id:'updateRemark',
    			allowBlank:false,
    			blankText:'更改备注不能为空!',
    			name:'updateRemark',
    			width:200
    		},'-',{
    			xtype:'label',
    			id:'showMsg',
    			width:200
    		},'-',{
    			xtype:'label',
    			id:'showEndDepart',
    			width:100
    		}]
	});
	function location(){
		var locationRecord = new Ext.grid.GridPanel({
    				region : "center",
    				id : 'locationRecord',
    				height : 200,
    				width : 500,
    				autoScroll : true,
    				frame : false,
    				loadMask : true,
    				stripeRows : true,
    				viewConfig:{
    				   scrollOffset: 0
    				},
    				cm : new Ext.grid.ColumnModel([rowNum,
    						new Ext.grid.CheckboxSelectionModel({singleSelect:true}), 
    						{header:'ts',dataIndex:'ts',hidden:true,hideable: false},
    						{header:'配送单号',dataIndex:'dno'},
    						{header:'主单号',dataIndex:'flightMainNo',hidden:true},
    						{header:'班次号',dataIndex:'flightNo',hidden:true},
    						{header:'班次日期',dataIndex:'flightDate',hidden:true},
    						{header:'运输方式',dataIndex:'trafficMode',hidden:true},
    						{header:'代理公司编号',dataIndex:'cusId',hidden:true},
    						{header:'代理公司',dataIndex:'cpName',hidden:true},
    						{header : '分单号',dataIndex:'subNo',width:80}, 
    						{header : '提货方式',dataIndex:'takeMode',width:80},
    						{header : '回单类型',dataIndex:'receiptType',width:80},
    						{header : '收货人姓名',dataIndex:'consignee',width:80},
    						{header : '收货人电话',dataIndex:'consigneeTel',width:80}, 
    						{header : '所在市',dataIndex:'city',width:80},
    						{header : '区/县',dataIndex:'town',width:80}, 
    						{header : '地址类型',dataIndex:'areaType',width:80},
    						{header : '收货人地址',dataIndex:'addr',width:80},
    						{header:'供应商',dataIndex:'goWhere',width:80},
    						{header:'供应商ID',dataIndex:'goWhereId',width:80,hidden:true,hideable: false},
    						{header : '配送方式',dataIndex:'distributionMode',width:80},
    						{header : '配送部门',dataIndex:'distributionDepart',width:80},
    						{header : '中转费率',dataIndex:'traFee',width:80},
    						{header : '货物名称',dataIndex:'goods',width:80},
    						{header : '件数',dataIndex:'piece',width:80},
    						{header : '重量KG',dataIndex:'cqWeight',width:80},
    						{header : '计费重量KG',dataIndex:'cusWeight',width:80},
    						{header : '体积m3',dataIndex:'bulk',width:80},
    						{header : '代收运费',dataIndex:'paymentCollection',width:80},
    						{header : '提送付款方',dataIndex:'whoCash',width:80},
    						{header:'预付专车费',dataIndex:'cpSonderzugPrice',width:80},
    						{header:'到付专车费',dataIndex:'sonderzugPrice',width:80},
    						{header : '中转费率',dataIndex:'traFeeRate',width:80},
    						{header : '中转费',dataIndex:'traFee',width:80},
    						{header : '预付提送费率',dataIndex:'cpRate',width:80},
    						{header : '预付提送费',dataIndex:'cpFee',width:80},
    						{header : '到付提送费率',dataIndex:'consigneeRate',width:80},
    						{header : '到付提送费',dataIndex:'consigneeFee',width:80},
    						{header : '到付提送费',dataIndex:'consigneeFee',width:80},
    						{header : '备注',dataIndex:'remark',width:100,hidden:true,hideable: true}
    				]),
    				sm : new Ext.grid.CheckboxSelectionModel(),
    				ds : faxStore,
    				tbar:['主单号:',{
    					xtype:'textfield',
    					id:'searchMainNo'
    				},'-','配送单号：',{
    					xtype:'textfield',
    					id:'searchdno'
    				},'-',{
    					text:'搜索',
    					iconCls: 'btnSearch',
    					handler:searchLocation
    				}],
    				listeners:{
    					rowdblclick:function(grid, rowindex, e){
    						Ext.Msg.confirm(alertTitle, "确定要修改吗？", function(btnYes) {
    							if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
    								var record=grid.getSelectionModel().getSelected();
    								setMsg(record);
    								win.close();
    							}
    						});
    						
    					}
    				}
    	});
    	function searchLocation(){
    		faxStore.load({
    			params:{
    				filter_EQS_flightMainNo:Ext.getCmp('searchMainNo').getValue(),
    				filter_EQS_dno:Ext.getCmp('searchdno').getValue()
    			}
    		});
    	}
    	var form1 = new Ext.form.FormPanel({
    				id:'locationForm',
					frame : false,
					border : false,
					bodyStyle : 'padding:0px 0px 0px',
					labelAlign : "right",
					labelWidth : 80,
					width : 500,
					height:200 ,
					items:[locationRecord]
    	});
    	var win = new Ext.Window({
			title : '定位更改',
			width : 500,
			height:270,
			closeAction : 'hide',
			plain : true,
			modal : true,
			items : form1,
			buttonAlign : "center",
			buttons:[{
				text:'确认',
				handler:function(){
					var _records = locationRecord.getSelectionModel().getSelections();
                	if (_records.length != 1) {
						parent.Ext.Msg.alert(alertTitle, "一次只能操作一行！");
						return false;
					}else{
						Ext.Msg.confirm(alertTitle, "确定要修改吗？", function(btnYes) {
							if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
    								setMsg(_records[0]);
    								win.close();
							}
						});
					}
				}
			},{
				text : "取消",
				handler : function() {
					win.close();
				}
			}]
		});
		win.on('hide', function() {
					form1.destroy();
				});
		win.show();
	}
	var rowNum = new Ext.grid.RowNumberer({
    		header : '序号',
    		width : 35,
    		sortable : true
    });
 
    //费用Store
    feeStore=new Ext.data.Store({
    			 id:'feeStore',
    			 reader:new Ext.data.JsonReader({
	            root: 'result', totalProperty: 'totalCount'
	     }, [
	     {name:'sonderzugPrice'},
	     {name:'cpValueAddFee'},
	     {name:'cusValueAddFee'},
	     {name:'cpSonderzugPrice'},
	     {name:'paymentCollection'},
	     {name:'traFee'},
	     {name:'cpFee'},
	     {name:'consigneeFee'}
	     ])
    });
    //修改项Store
    updateMsgStore=new Ext.data.Store({
    			 id:'updateMsgStore',
    			 reader:new Ext.data.JsonReader({
	            root: 'result', totalProperty: 'totalCount'
	     }, [
	     {name:'changeField'},
	     {name:'changeFieldZh'},
	     {name:'changePre'},
	     {name:'changePost'}
	     ])
    }); 
     
    //修改项record
    var i=0;
    var arr=[];
    updateMsgRecord=new Ext.grid.GridPanel({
    	region : "center",
    	id : 'updateMsgRecord',
    	height:Ext.lib.Dom.getViewHeight()-365,
    	frame : false,
    	loadMask : true,
    	stripeRows : true,
    	viewConfig:{
			autoScroll : true,
    		scrollOffset: 0
    	},
    	cm : new Ext.grid.ColumnModel([rowNum,new Ext.grid.CheckboxSelectionModel({singleSelect:true}),
    		{header : '更改字段名',dataIndex:'changeField',width:80,hidden:true},
    		{header : '更改项',dataIndex:'changeFieldZh',width:80},
    		{header : '更改前的值',dataIndex:'changePre',width:80},
    		{header : '更改后的值',dataIndex:'changePost',width:150}
    		/*,
    		{header:'操作',dataIndex:'changeField',width:80,renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){
    			//删除一条记录
    			return "<a href='#' onclick='javascript:delUpdateMsgStore("+rowIndex+");'>删除</a>";
    			}
    		}*/
    	]),
    	sm:new Ext.grid.CheckboxSelectionModel({singleSelect:true}),
    	ds:updateMsgStore,
    	tbar:['&nbsp;&nbsp;&nbsp;&nbsp;',{
    		xtype:'button',
    		text:'<b>删除<b?',
    		iconCls: 'userDelete',
    		disabled:true,
    		id:'delDetailBtn',
    		handler:function(){
    			delUpdateMsgStore();
    		}
    	}]
    });
   	updateMsgRecord.on('click', function() {
        var _record = updateMsgRecord.getSelectionModel().getSelections();
        var deletebtn = Ext.getCmp('delDetailBtn');
       if (_record.length==1) {       	
            if(deletebtn){
            	deletebtn.setDisabled(false);
            }
        }else {
			if(deletebtn){
            	deletebtn.setDisabled(true);
			}
        }
    });
    function changeCountPrice(){
    	if(form.getForm().isValid()){
    		countPrice(Ext.getCmp('payerCombo'));
    		var task = new Ext.util.DelayedTask(refreshFeeStore);
    		task.delay(500); 
    	}
    }
    function refreshFeeStore(){
    	feeStore.removeAll();
    		var store=new Ext.data.Record.create([{name:'cpSonderzugPrice'},{name:'sonderzugPrice'},{name:'paymentCollection'},{name:'traFee'},{name:'cpFee'},{name:'consigneeFee'}]);
		    var noFaxRecord=new store();
		    noFaxRecord.set("sonderzugPrice",Ext.getCmp('sonderzugPrice').getValue());
		    noFaxRecord.set("cpSonderzugPrice",Ext.getCmp('cpSonderzugPrice').getValue());
		    noFaxRecord.set("paymentCollection",Ext.getCmp('paymentCollection').getValue());
			noFaxRecord.set("traFee",Ext.getCmp('traFee').getValue());
			noFaxRecord.set("cpFee",Ext.getCmp('cpFee').getValue());
			noFaxRecord.set("cpValueAddFee",Ext.getCmp('cpValueAddFee').getValue());
			noFaxRecord.set("cusValueAddFee",Ext.getCmp('cusValueAddFee').getValue());
			noFaxRecord.set("consigneeFee",Ext.getCmp('consigneeFee').getValue());
			feeStore.add(noFaxRecord);
    }
     //增值服务费Store
    addFeeRecordStore=new Ext.data.Store({
		 id:'addFeeRecordStore',
		 baseParams:{
		 	filter_EQL_status:0
		 },
		 proxy:new Ext.data.HttpProxy({url:sysPath+"/stock/oprValueAddFeeAction!list.action"}),
		 reader:new Ext.data.JsonReader({
         root: 'result', totalProperty: 'totalCount'
	     }, [
	     {name:'feeNameId',mapping:'id'},
	     {name:'feeName'},
	     {name:'feeCount'},
	     {name:'feeLink'},
	     {name:'payType'}
	     ])
    });
    
    autoFeeStore=new Ext.data.Store({
    	id:'autoFeeStore',
    	proxy:new Ext.data.HttpProxy({url:sysPath+"/basvalueaddfee/basValueAddFeeAction!list.action"}),
    	reader:new Ext.data.JsonReader({
	            root: 'result', totalProperty: 'totalCount'
	     }, [
	     {name:'id'},
	     {name:'feeName'},
	     {name:'feeCount'},
	     {name:'feeLink'},
	     {name:'payMan'}
	     ])
    });
    autoFeeStore.on('load',function(){
		var cpValue=0;
		var cusValue=0;
		for(var i=0;i<autoFeeStore.getCount();i++){
			var store=new Ext.data.Record.create([{name:'feeNameId',mapping:'id'},{name:'feeName'},{name:'feeCount'},{name:'feeLink'},{name:'payMan'}]);
		    var noFaxRecord=new store();
		    noFaxRecord.set("feeNameId",autoFeeStore.getAt(i).get('id'));
		    noFaxRecord.set("feeName",autoFeeStore.getAt(i).get('feeName'));
		    noFaxRecord.set("feeCount",autoFeeStore.getAt(i).get('feeCount'));
		    noFaxRecord.set("payType",autoFeeStore.getAt(i).get('payMan'));
		    noFaxRecord.set("feeLink",'更改申请');
		    addFeeRecordStore.add(noFaxRecord);
			if(autoFeeStore.getAt(i).get('payMan') == '预付'){
				cpValue+=autoFeeStore.getAt(i).get('feeCount');
			}else{
				cusValue+=autoFeeStore.getAt(i).get('feeCount');
			}
		}
		//alert(cusValue);
		Ext.getCmp('cusValueAddFee').setValue(Number(Ext.getCmp('cusValueAddFee').getValue())+cusValue);
		Ext.getCmp('totalAddFee').setValue(Number(Ext.getCmp('totalAddFee').getValue())+cusValue);
	});
    addFeeRecordStore.on('load',function(){
		var cpValue=0;
		var cusValue=0;
		for(var i=0;i<addFeeRecordStore.getCount();i++){
			if(addFeeRecordStore.getAt(i).get('payType') == '预付'){
				cpValue+=addFeeRecordStore.getAt(i).get('feeCount');
			}else{
				cusValue+=addFeeRecordStore.getAt(i).get('feeCount');
			}
		}
		Ext.getCmp('cusValueAddFee').setValue(cusValue);
		Ext.getCmp('cpValueAddFee').setValue(cpValue);
		Ext.getCmp('totalAddFee').setValue(cpValue+cusValue);
	});
    var addFeeRecord=new Ext.grid.GridPanel({
    	region : "center",
    	id : 'addFeeRecord',
    	height:Ext.lib.Dom.getViewHeight()-370,
    	frame : false,
    	loadMask : true,
    	stripeRows : true,
    	viewConfig:{
			autoScroll : true,
    		scrollOffset: 0
    	},
    	cm : new Ext.grid.ColumnModel([rowNum,new Ext.grid.CheckboxSelectionModel({}),
    		{header:'费用类型Id',dataIndex:'feeNameId',hidden:true},
    		{header : '费用类型',dataIndex:'feeName',width:100}, 
    		{header : '金额',dataIndex:'feeCount',width:80},
    		{header : '付款方',dataIndex:'payType',width:80}
    	]),
    	sm:new Ext.grid.CheckboxSelectionModel({}),
    	ds:addFeeRecordStore
    }); 
   
    //增值服务费panel
    var addFeePanel=new Ext.Panel({
    	height:Ext.lib.Dom.getViewHeight()-360,
    	width:Ext.lib.Dom.getViewWidth()-15,
    	items:[{
    		layout:'table',
    		layoutConfig: {columns:6},
    		bodyStyle:'padding:5px 0px 0px 0px',
    		defaults:{border:false,layout:'form',frame:false,labelAlign:'right',labelWidth:65},
    		items:[{
    			colspan:1,
    			width:Ext.lib.Dom.getViewWidth()/5.5,
    			items:[{
    				xtype:'combo',
    				name:'feeType',
    				store:addFeeStore,
    				fieldLabel:'费用类型',
    				triggerAction : 'all',
					queryParam : 'filter_LIKES_feeName',
					model : 'local',
					id:'combofeetype',
					valueField : 'id',
				    displayField : 'feeName',
					emptyText : '选择类型',
					forceSelection : true,
    				width:100,
    				enableKeyEvents:true,
					listeners : {
						keypress:function(combo, e){
							if(e.getKey() == 13){
								Ext.getCmp('uprice').focus(true,true);
							}
						}
					}
    			}]
    		},{
    			colspan:1,
    			width:Ext.lib.Dom.getViewWidth()/5.5,
    			items:[{
    				xtype:'numberfield',
    				id:'uprice',
    				name:'uprice',
    				fieldLabel:'金额',
    				width:100,
    				enableKeyEvents:true,
    			//	blankText:'金额不能为空!!',
					listeners : {
						keypress:function(textField, e){
							if(e.getKey() == 13){
								Ext.getCmp('addFeeBth').focus(true,true);
							}
						}
					}
    			}]
    		},{
    		
    			colspan:1,
    			width:Ext.lib.Dom.getViewWidth()/5.5,
    			items:[{
    				xtype:'combo',
	    			store:[
	    				['预付','预付'],
	    				['到付','到付']
	    			],
	    			value:'预付',
	    			fieldLabel:'付款方',
	    			id:'addFeeWhocash',
	    			triggerAction : 'all',
	    			width:100,
	    			forceSelection : true,
	    			name:'addFeeWhocash'/*
	    			,
	    			listeners:{
	    				'select':function(combo){
	    					if(combo.getValue()=='预付'){
	    						var totp=Ext.getCmp('totalAddFee').getValue();
	    						if(totp!=''){
	    							Ext.getCmp('cpValueAddFee').setValue(totp);
	    							Ext.getCmp('cusValueAddFee').setValue(0);
	    						}
	    						for(var i=0;i<addFeeRecordStore.getCount();i++){
							  		var r=addFeeRecordStore.getAt(i);
							  		r.set('payType','预付');
							  	}
	    					}else if(combo.getValue()=='到付'){
	    						var totp=Ext.getCmp('totalAddFee').getValue();
	    						if(totp!=''){
	    							Ext.getCmp('cusValueAddFee').setValue(totp);
	    							Ext.getCmp('cpValueAddFee').setValue(0);
	    						}
	    						for(var i=0;i<addFeeRecordStore.getCount();i++){
							  		var r=addFeeRecordStore.getAt(i);
							  		r.set('payType','到付');
							  	}
	    					}
	    				}
	    			}*/
    			}]
    		},{
    			colspan:1,
    			width:80,
    			items:[{
    				xtype:'button',
    				id:'addFeeBth',
    				text:'添加',
    				handler:function(){
    					if(Ext.getCmp('combofeetype').isValid()&&Ext.getCmp('uprice').isValid()){
    						var flag=false;
	    					for(var i=0;i<addFeeRecordStore.getCount();i++){
	    						if(addFeeRecordStore.getAt(i).get("feeNameId")==Ext.getCmp('combofeetype').getValue()){
	    							flag=true;
	    						}
	    					}
	    					if(flag){
	    						Ext.Msg.alert(alertTitle,'请勿重复添加',onfocus);
	    						function onfocus(){
									Ext.getCmp('combofeetype').focus(true,true);
									return;
	    						}
	    					}else{
	    						if(Ext.getCmp('combofeetype').getValue()==''||Ext.getCmp('uprice').getValue()==''||Ext.getCmp('addFeeWhocash').getValue()==''){
	    					  		Ext.Msg.alert(alertTitle,'请勿添加空值！');
	    					  		return false;
	    					  }else{
		    					  Ext.getCmp('combofeetype').focus(true,true);
		    					  var store=new Ext.data.Record.create([{name:'feeNameId'},{name:'feeName'},{name:'feeCount'},{name:'feeLink'},{name:'payMan'}]);
		    					  var noFaxRecord=new store();
		    					  noFaxRecord.set("feeNameId",Ext.getCmp('combofeetype').getValue());
								  noFaxRecord.set("feeName",Ext.get('combofeetype').dom.value);
								  noFaxRecord.set("feeCount",Ext.getCmp('uprice').getValue());
								  noFaxRecord.set("payType",Ext.getCmp('addFeeWhocash').getValue());
								  noFaxRecord.set("feeLink",'传真录入');
								  addFeeRecordStore.add(noFaxRecord);
								  var totalPrice=0;
								  for(var i=0;i<addFeeRecordStore.getCount();i++){
		    						totalPrice+=Number(addFeeRecordStore.getAt(i).get('feeCount'));
		    					  }
								  Ext.getCmp('totalAddFee').setValue(totalPrice);
								  if(Ext.getCmp('addFeeWhocash').getValue()=='预付'){
								  	Ext.getCmp('cpValueAddFee').setValue(Number(Ext.getCmp('cpValueAddFee').getValue())+Number(Ext.getCmp('uprice').getValue()));
								  }else if(Ext.getCmp('addFeeWhocash').getValue()=='到付'){
								  	Ext.getCmp('cusValueAddFee').setValue(Number(Ext.getCmp('cusValueAddFee').getValue())+Number(Ext.getCmp('uprice').getValue()));
								  }
	    					  }
	    					}
    					}
    				}
    			}]
    		},{
    			colspan:1,
    			width:80,
    			items:[{
    				xtype:'button',
    				id:'delFeeBth',
    				text:'删除',
    				handler:function(){
						var _record = addFeeRecord.getSelectionModel().getSelections();
						if(_record.length<1){
							Ext.Msg.alert(alertTitle,'请选择删除的行!!');
						}else{
							var totalp=0;
							for(var i=0;i<_record.length;i++){
								totalp+=_record[i].data.feeCount;
							}
							addFeeRecordStore.remove(_record);
							var p=Number(Ext.getCmp('totalAddFee').getValue())-totalp;
							Ext.getCmp('totalAddFee').setValue(p);
							/*
							if(Ext.getCmp('addFeeWhocash').getValue()=='预付'){
							  	Ext.getCmp('cpValueAddFee').setValue(p);
							  	Ext.getCmp('cusValueAddFee').setValue(0);
							 }else if(Ext.getCmp('addFeeWhocash').getValue()=='到付'){
							  	Ext.getCmp('cusValueAddFee').setValue(p);
							  	Ext.getCmp('cpValueAddFee').setValue(0);
							 }*/
							for(var i=0;i<_record.length;i++){
								var record=_record[i];
								if(record.get('payType')=='预付'){
									Ext.getCmp('cpValueAddFee').setValue(Number(Ext.getCmp('cpValueAddFee').getValue())-Number(record.get('feeCount')));
								}else if(record.get('payType')=='到付'){
									Ext.getCmp('cusValueAddFee').setValue(Number(Ext.getCmp('cusValueAddFee').getValue())-Number(record.get('feeCount')));
								}
							}
							//alert(Ext.getCmp('cpValueAddFee').getValue()+','+Ext.getCmp('cusValueAddFee').getValue());
						}
    				}
    			}]
    		},{
    			colspan:1,
    			width:Ext.lib.Dom.getViewWidth()/4.5,
    			items:[{
    				xtype:'numberfield',
    				name:'totalAddFee',
    				id:'totalAddFee',
    				fieldLabel:'合计',
    				value:0,
    				width:100
    			}]
    		},{xtype:'hidden',name:'cpValueAddFee',id:'cpValueAddFee'},{xtype:'hidden',name:'cusValueAddFee',id:'cusValueAddFee'}]
    	},{
    		layout:'form',
    		bodyStyle:'padding:0px 0px 0px 0px',
    		items:[addFeeRecord]
    	}]
    });
    /*
    //个性化要求Store
     var requestStore=new Ext.data.Store({
    	id:'requestStore',
    	proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/cusRequestAction!ralaList.action"}),
		baseParams:{limit:pageSize,privilege:56},
    	reader:new Ext.data.JsonReader({
	    root: 'result', totalProperty: 'totalCount'
	     }, [
	     {name:'requestStage'},
	     {name:'request'},
	     {name:'requestType'}
	     ])
    });*/
    //要求类型Store
	var requestTypeStore=new Ext.data.Store({
		storeId:"requestTypeStore",
		baseParams:{filter_EQL_basDictionaryId:6,privilege:16},
		proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/dictionaryAction!ralaList.action"}),
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
        	{name:'id'},
        	{name:'requestType',mapping:'typeName'}
        	])
	});
	var requestMsaArr={'totalCount':'1','result':[{
        requestid:1,
        requestStage:'请选择',
        requestMsgName:'请选择'
    }]};
    var requestMsgStore=new Ext.data.Store({
    	storeId:'requestMsgStore',
    	proxy:new Ext.data.MemoryProxy(requestMsaArr), 
    	reader:new Ext.data.JsonReader({
	    root: 'result', totalProperty: 'totalCount'
	     }, [
	     {name:'requestid'},
	     {name:'requestStage'},
	     {name:'requestMsgName'}
	     ])
    });
	var tablePanel=new Ext.TabPanel({   
    id: "mainTab",   
    activeTab: 0,
    height:Ext.lib.Dom.getViewHeight()-340,
    defaults: {   
        autoScroll: true,   
        autoHeight:true,   
        style: "padding:0 0 0 0"  
    },   
    items:[   
       {title:"更改明细", tabTip:"更改明细列表",items:[updateMsgRecord]},   
       {title:"增值服务费",tabTip:'增值服务费',items:[addFeePanel]}
    ],
    listeners:{
    	tabchange:function(tab,e){
			if(tab.activeTab.id=="requestDoPanel"){
				if(Ext.get('cuscombo').dom.value!='选择类型'||Ext.getCmp('consignee').getValue()!=''){
					/*
					requestStore.load({
						params:{
							filter_EQS_cpName:Ext.get('cuscombo').dom.value,
							filter_EQS_cusName:Ext.getCmp('consignee').getValue()
						}
					});
					if(requestStore.getCount()<1){
						requestStore.load({
							params:{
								filter_EQS_cpName:Ext.get('cuscombo').dom.value,
								filter_EQS_cusName:'*'
							}
						});
					}*/
				}
			}
		}
    }
	});   
	var domWidth=Ext.lib.Dom.getViewWidth()-15;
	var comWidth=domWidth/4;
	var scomWidth=domWidth/5;
	var sepcomWidth=domWidth/6;
    var domHeight=Ext.lib.Dom.getViewHeight();
    
    function setaddrmsg(respText){
    	var dno=Ext.getCmp('dno').getValue();
		if(form.findByType("checkbox")[0].getValue()||Ext.getCmp('takeModecombo').getValue()=='机场自提'){
			Ext.Ajax.request({
				url:sysPath+'/stock/oprStatusAction!list.action',
				params:{
					limit:pageSize,
					filter_EQL_dno:dno
				},success:function(resp){
					var respText1 = Ext.util.JSON.decode(resp.responseText);
					if(respText1.result[0].outStatus==0||respText1.result[0].outStatus==3){
						Ext.getCmp('distributionMode').setValue("新邦");
						Ext.getCmp('goWhere').setValue('');
						Ext.getCmp('goWhereIdcombo').setValue(0);
						Ext.getCmp('goWhereIdcombo').setRawValue('');
					}
				}
			});
			Ext.getCmp('distributionDepartId').setValue(bussDepart);
			Ext.getCmp('distributionDepart').setValue(bussDepartName);
			Ext.getCmp('endDepart').setValue(bussDepartName);
			Ext.getCmp('endDepartId').setValue(bussDepart);
		}else{
			Ext.Ajax.request({
				url:sysPath+'/stock/oprStatusAction!list.action',
				params:{
					limit:pageSize,
					filter_EQL_dno:dno
				},success:function(resp){
					var respText1 = Ext.util.JSON.decode(resp.responseText);
					if(respText1.result[0].outStatus==0||respText1.result[0].outStatus==2){
						Ext.getCmp('distributionMode').setValue(respText.result[0].develpMode);
						if(respText.result[0].cusId == null){
							Ext.getCmp('goWhereIdcombo').setValue(0);
							Ext.getCmp('goWhereIdcombo').setRawValue('');
							Ext.getCmp('goWhere').setValue('');
						}else{
							Ext.getCmp('goWhere').setValue(respText.result[0].cusName);
							Ext.getCmp('goWhereIdcombo').setValue(respText.result[0].cusId);
							Ext.getCmp('goWhereIdcombo').setRawValue(respText.result[0].cusName);
						}
					}
				}
			});
			if(respText.result[0].distriDepartId == null || respText.result[0].distriDepartId ==''){
				Ext.getCmp('distributionDepartId').setValue(bussDepart);
				Ext.getCmp('distributionDepart').setValue(bussDepartName);
			}else{
				Ext.getCmp('distributionDepartId').setValue(respText.result[0].distriDepartId);
				Ext.getCmp('distributionDepart').setValue(respText.result[0].distriDepartName);
			}
			if(respText.result[0].endDepartId== null || respText.result[0].endDepartId==''){
				Ext.getCmp('endDepart').setValue(bussDepartName);
				Ext.getCmp('endDepartId').setValue(bussDepart);
			}else{
				Ext.getCmp('endDepart').setValue(respText.result[0].endDepartName);
				Ext.getCmp('endDepartId').setValue(respText.result[0].endDepartId);
			}
		}
		//Ext.getCmp('endDepart').setValue(respText.result[0].endDepartName);
		//Ext.getCmp('endDepartId').setValue(respText.result[0].endDepartId);
		Ext.getCmp('areaType').setValue(respText.result[0].areaType);
		Ext.getCmp('areaRank').setValue(respText.result[0].areaRank);
		if(respText.result[0].endDepartName==null){
			Ext.getCmp('showEndDepart').getEl().update('终端部门:'+bussDepartName);
		}else{
			Ext.getCmp('showEndDepart').getEl().update('终端部门:'+respText.result[0].endDepartName);
		}
    }
    /*
    function setaddrmsg(respText){
		if(form.findByType("checkbox")[0].getValue()||Ext.getCmp('takeModecombo').getValue()=='机场自提'){
			Ext.getCmp('distributionMode').setValue("新邦");
			Ext.getCmp('distributionDepartId').setValue(bussDepart);
			Ext.getCmp('distributionDepart').setValue(bussDepartName);
			Ext.getCmp('endDepart').setValue(bussDepartName);
			Ext.getCmp('endDepartId').setValue(bussDepart);
			Ext.getCmp('goWhere').setValue('');
			Ext.getCmp('goWhereIdcombo').setValue('');
		}else{
			Ext.getCmp('distributionMode').setValue(respText.result[0].develpMode);
			Ext.getCmp('distributionDepartId').setValue(respText.result[0].distriDepartId);
			Ext.getCmp('distributionDepart').setValue(respText.result[0].distriDepartName);
			Ext.getCmp('endDepart').setValue(respText.result[0].endDepartName);
			Ext.getCmp('endDepartId').setValue(respText.result[0].endDepartId);
			Ext.getCmp('goWhere').setValue(respText.result[0].cusName);
			Ext.getCmp('goWhereIdcombo').setValue(respText.result[0].cusId);
			Ext.getCmp('goWhereIdcombo').setRawValue(respText.result[0].cusName);
		}
		//Ext.getCmp('endDepart').setValue(respText.result[0].endDepartName);
		//Ext.getCmp('endDepartId').setValue(respText.result[0].endDepartId);
		Ext.getCmp('areaType').setValue(respText.result[0].areaType);
		Ext.getCmp('areaRank').setValue(respText.result[0].areaRank);
		
		if(respText.result[0].endDepartName==null){
			Ext.getCmp('showEndDepart').getEl().update('终端部门:'+bussDepartName);
		}else{
			Ext.getCmp('showEndDepart').getEl().update('终端部门:'+respText.result[0].endDepartName);
		}							    				
    }*/
    //主页面布局
	var form = new Ext.form.FormPanel({
    				id:'addfaxform',
					frame : true,
					renderTo:Ext.getBody(),
					labelAlign : "right",
					bodyStyle:'padding:0px 0px 0px 0px',
					labelWidth : 60,
					width:Ext.lib.Dom.getViewWidth(),
					height:Ext.lib.Dom.getViewHeight(),
					tbar:tb,
					items:[
							{
									xtype:"fieldset",
									//title:"主单信息",
									layout:'table',
									id:'setmainmsg',
									//height:38,
									style:'margin:2px;',
									layoutConfig: {columns:5},
									bodyStyle:'padding:5px 0px 0px 0px',
									defaults:{border:false,layout:'form',frame:false,labelAlign:'right',labelWidth:65},
									items:[{
											colspan:1,
											width:scomWidth,
											items:[{
													xtype:'combo',
													fieldLabel:'运输方式',
													triggerAction : 'all',
													queryParam : 'filter_LIKES_typeName',
							    					model : 'local',
							    					editable:true,
							    					id:'trafficModecombo',
							    					valueField : 'trafficMode',
				    								displayField : 'trafficMode',
				    								name : 'trafficMode',
				    								width:fiveWidth,
													store:trafficModeStore,
													value:'空运',
													emptyText : '选择类型',
							    					forceSelection : true,
							    					enableKeyEvents:true,
								 					listeners : {
												 		'keyup':function(textField, e){
										                     if(e.getKey() == 13){
										                     	if(textField.getValue()==''){
																	textField.setValue(Ext.get('flightcombo').dom.value);
																}
										                     	Ext.getCmp('cuscombo').focus(true,true);
										                     }
												 		},
										 				'change':function(combo,newValue,oldValue){
										 					changeCountPrice();
										 				}
											 		}
												}]
										},{
											colspan:1,
											width:scomWidth,
											items:[
												{xtype:'hidden',name:'cpName',id:'cpName'},
												{
												xtype:'combo',
												fieldLabel:'代理公司<span style="color:red">*</span>',
												triggerAction :'all',
						    					id:'cuscombo',
						    					minChars : 0,
						    					width:fiveWidth,
						    					store:cusStore,
						    					queryParam :'filter_LIKES_cusName',
						    					pageSize:pageSize,
						    					valueField :'id',
			    								displayField :'cusName',
			    								hiddenName : 'cusId',
												emptyText : '选择类型',
						    					forceSelection : true,
						    					blankText : "代理公司不能为空!",
						    					allowBlank : false,
						    					enableKeyEvents:true,
								 					listeners : {
												 		keyup:function(textField, e){
											                  if(e.getKey() == 13){
											                  	 Ext.Ajax.request( {
																	url : sysPath + "/sys/basCusServiceAction!list.action",
																	params : {
																		cusId:Ext.getCmp('cuscombo').getValue(),
																		limit:pageSize
																	},
																	success : function(resp) {
																		var respText = Ext.util.JSON.decode(resp.responseText);
																		Ext.getCmp('customerService').setValue(respText.result[0].serviceName);
																	}
																 });
																 Ext.getCmp('cpName').setValue(Ext.get('cuscombo').dom.value);
											                     Ext.getCmp('flightMain').focus(true,true);
											                  }
												 		},
												 		select:function(){
												 			Ext.Ajax.request( {
																	url : sysPath + "/sys/basCusServiceAction!list.action",
																	params : {
																		cusId:Ext.getCmp('cuscombo').getValue(),
																		limit:pageSize
																	},
																	success : function(resp) {
																		var respText = Ext.util.JSON.decode(resp.responseText);
																		Ext.getCmp('customerService').setValue(respText.result[0].serviceName);
																	}
															});
												 			Ext.getCmp('cpName').setValue(Ext.get('cuscombo').dom.value);
												 			changeCountPrice();
												 		}
									 				}
												}]
										},{
											colspan:1,
											width:scomWidth,
											items:[{
													xtype:'textfield',
													fieldLabel:'主单号<span style="color:red">*</span>',
													id:'flightMain',
													name:'flightMainNo',
													width:fiveWidth,
													readOnly:true,
													enableKeyEvents:true,
													blankText : "主单号不能为空!",
						    						allowBlank : false,
								 					listeners : {
											 		keyup:function(textField, e){
										                     if(e.getKey() == 13){
										                     	Ext.getCmp('flightcombo').focus(true,true);
										                  }
											 			}
									 				}
												}]
										},{
											colspan:1,
											width:scomWidth,
											items:[{xtype:'hidden',id:'customerName',name:'customerName'},{xtype:'hidden',id:'endCity',name:'endCity'},
												{
													xtype:'combo',
													fieldLabel:'班次号<span style="color:red">*</span>',
													triggerAction : 'all',
							    					model : 'local',
							    					id:'flightcombo',
							    					width:fiveWidth,
							    					minChars : 0,
							    					hiddenName:'flightNo',
							    					queryParam : 'filter_LIKES_flightNumber',
							    					pageSize:pageSize,
							    					valueField : 'flightNumber',
				    								displayField : 'flightNumber',
													store:flightStore,
													emptyText : '选择类型',
													editable:true,
							    					forceSelection : true,
							    					blankText : "班次号不能为空!",
							    					allowBlank : false,
							    					enableKeyEvents:true,
								 					listeners : {
												 		keyup:function(textField, e){
															if(e.getKey()==13){
																Ext.getCmp('arrivetime').focus(true,true);
															}
												 		},
												 		select:function(combo){
											                var flightNo=combo.getValue();
															Ext.Ajax.request( {
															    url : sysPath + "/sys/basFlightAction!list.action",
																params : {
																	filter_EQS_flightNumber:flightNo,
																	limit:pageSize
																},
																success : function(resp) {
																	var respText = Ext.util.JSON.decode(resp.responseText);
																	Ext.getCmp('customerName').setValue(respText.result[0].cusName);
																	Ext.getCmp('endCity').setValue(respText.result[0].endCity);
																	changeCountPrice();
																}
															});
											                 	 
												 		}
													}
												}]
										},{
											colspan:1,
											width:scomWidth,
											items:[
												{
													xtype:'datefield',id:'arrivetime',
													name:'flightDate',fieldLabel:'班次日期',
													format:'Y-m-d',
													value:new Date(),
													allowBlank:false,
													blankText:'班次日期不能为空！',
													width:fiveWidth,
													enableKeyEvents:true,
													listeners : {
											 		keyup:function(textField, e){
										                     if(e.getKey() == 13){
										                    	textField.blur();
										                     	Ext.getCmp('subNo').focus(true,true);
										                  }
											 			}
									 				}
												}]
										}
										]
								},{
									xtype:'fieldset',
									//title:"专车信息",
									id:'setsongder',
									layout:'table',
									//height:38,
									style:'margin:2px;',
									layoutConfig: {columns:7},
									bodyStyle:'padding:5px 0px 0px 0px',
									defaults:{border:false,layout:'form',frame:false,labelAlign:'right',labelWidth:65},
									items:[
										{	
											colspan:1,
											width:100,
											labelWidth:60,
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
										            	   	if(form.findByType("checkbox")[0].getValue()){
										            		   Ext.getCmp('sonderzugVal').setValue(1);
										            		  
										            	   }else{
										            		   Ext.getCmp('sonderzugVal').setValue(0);
										            	   }
										            	    Ext.getCmp('cartypecombo').focus(true,true);
										               }
											 		},check:function(checkbox,flag){
											 			var disDepartId = Ext.getCmp('distributionDepartId').getValue();
											 			var disDepartName = Ext.getCmp('distributionDepart').getValue();
											 			var endDepartId = Ext.getCmp('endDepartId').getValue();
											 			var endDepartName = Ext.getCmp('endDepart').getValue();
											 			if(flag){
											 				Ext.getCmp('distributionMode').setValue('新邦');
											 				Ext.getCmp('takeModecombo').setValue('市内送货');
											 				Ext.getCmp('takeModecombo').getEl().dom.readOnly=true;
											 				Ext.getCmp('sonderzugVal').setValue(1);
											 				Ext.getCmp('cartypecombo').setDisabled(false);
											 				Ext.getCmp('roadtypecombo').setDisabled(false);
											 				Ext.getCmp('sonderzugPrice').setDisabled(false);
											 				Ext.getCmp('cpSonderzugPrice').setDisabled(false);
											 				Ext.getCmp('distributionDepartId').setValue(bussDepart);
											 				Ext.getCmp('distributionDepart').setValue(bussDepartName);
											 				Ext.getCmp('endDepartId').setValue(bussDepart);
											 				Ext.getCmp('endDepart').setValue(bussDepartName);
											 			}else{
											 				Ext.getCmp('sonderzugVal').setValue(0);
											 				Ext.getCmp('cartypecombo').setValue('');
											 				Ext.getCmp('cartypecombo').setDisabled(true);
											 				Ext.getCmp('roadtypecombo').setValue('');
											 				Ext.getCmp('roadtypecombo').setDisabled(true);
											 				Ext.getCmp('sonderzugPrice').setValue('');
											 				Ext.getCmp('sonderzugPrice').setDisabled(true);
											 				Ext.getCmp('cpSonderzugPrice').setValue('');
											 				Ext.getCmp('cpSonderzugPrice').setDisabled(true);
											 				
											 				Ext.getCmp('distributionDepartId').setValue(disDepartId);
											 				Ext.getCmp('distributionDepart').setValue(disDepartName);
											 				Ext.getCmp('endDepartId').setValue(endDepartId);
											 				Ext.getCmp('endDepart').setValue(endDepartName);
											 			}
											 		}
									 			}
										}]
										},
										{
											colspan:1,
											width:sepcomWidth,
											labelWidth : 40,
											items:[{
												xtype:'combo',
												fieldLabel:'车型',
												disabled:true,
												triggerAction : 'all',
												width:80,
						    					id:'cartypecombo',
						    					name:'carType',
						    					queryParam : 'filter_LIKES_typeName',
						    					valueField : 'carType',
			    								displayField : 'carType',
			    								hiddenName : 'carType',
												store:carTypeStore,
												emptyText : '选择类型',
						    					forceSelection : true,
						    					allowBlank : true,
						    					enableKeyEvents: true, 
												listeners : {
											 		select:function(e){
														 Ext.getCmp('roadtypecombo').focus(true,true);
														 changeCountPrice();
											 		},blur:function(combo){
											 			if(form.findByType("checkbox")[0].getValue()&&combo.getValue()==''){
											 				Ext.Msg.alert(alertTitle,"车型不能为空!!",onfocus);
																function onfocus(){
																	Ext.getCmp('cartypecombo').focus(true,true);
																}
											 			}
											 		},
											 		change:function(combo,newValue,oldValue){
											 			Ext.getCmp('roadtypecombo').setValue('高速');
											 		}
									 			}
											}
											]
										},
										{
											colspan:1,
											width:sepcomWidth,
											labelWidth:40,
											items:[{
												xtype:'combo',
												fieldLabel:'路型',
												disabled:true,
												triggerAction : 'all',
						    					id:'roadtypecombo',
						    					width:80,
						    					name:'roadType',
						    					queryParam : 'filter_LIKES_typeName',
						    					valueField : 'roadType',
			    								displayField : 'roadType',
			    								hiddenName : 'roadType',
												store:roadTypeStore,
												emptyText : '选择类型',
						    					forceSelection : true,
						    					allowBlank : true,
												enableKeyEvents: true, 
												listeners : {
											 		select:function(e){
														changeCountPrice();
											 		},blur:function(combo){
											 			if(form.findByType("checkbox")[0].getValue()&&combo.getValue()==''){
											 				Ext.Msg.alert(alertTitle,"路型不能为空!!",onfocus);
																function onfocus(){
																	Ext.getCmp('roadtypecombo').focus(true,true);
																}
											 			}
											 		}
											 	}
											}]
										},
										{
											colspan:1,
											width:sepcomWidth+15,
											labelWidth : 60,
											items:[{xtype:'hidden',name:'normSonderzugPrice',id:'normSonderzugPrice'},{
												xtype:'textfield',
												id:'sonderzugPrice',
												name:'sonderzugPrice',
												allowBlank:false,
												blankText:'到付专车费不能为空!',
												maxValue:99999999.99,
												minValue:0.0,
												maxLength:11,
												disabled:true,
												width:80,
												fieldLabel:'到付专车'
												}
											]
										},{
											colspan:1,
											width:sepcomWidth+15,
											labelWidth : 60,
											items:[{
												xtype:'textfield',
												id:'cpSonderzugPrice',
												name:'cpSonderzugPrice',
												allowBlank:false,
												blankText:'预付专车费不能为空!',
												maxValue:99999999.99,
												minValue:0.0,
												maxLength:11,
												disabled:true,
												width:80,
												fieldLabel:'预付专车'
												}
											]
										},
										{
											colspan:1,
											width:sepcomWidth+15,
											width:110,
											labelWidth:80,
											items:[{xtype:'hidden',name:'wait',id:'waitVal',value:'0'},{
												xtype:'checkbox',
												id:'wait',
												name:'wait1',
												inputValue:'0',
												fieldLabel:'等通知放货',
												boxLabel:'',
												width:20,
												enableKeyEvents: true, 
												listeners : {
											 		specialkey:function(checkbox, e){
										               if(e.getKey() == 17){
										            	   if(form.findByType("checkbox")[1].getValue()){
										            		   Ext.getCmp('wait').setValue(false);
										            	   }else{
										            		   Ext.getCmp('wait').setValue(true);
										            	   }
										               }else if(e.getKey()==13){
										            	   if(form.findByType("checkbox")[1].getValue()){
										            		   Ext.getCmp('waitVal').setValue(1);
										            	   }else{
										            		   Ext.getCmp('waitVal').setValue(0);
										            	   }
										            	   Ext.getCmp('subNo').focus(true,true);
										               }
											 		},check:function(checkbox,flag){
											 			if(flag){
										            		   Ext.getCmp('waitVal').setValue(1);
										            	   }else{
										            		   Ext.getCmp('waitVal').setValue(0);
										            	}
											 		}
									 			 }
												}
											]
										},
										{
											colspan:1,
											labelWidth:40,
											width:scomWidth,
											items:[{xtype:'hidden',name:'urgentService',id:'urgentServiceVal',value:'0'},{
												xtype:'checkbox',
												id:'urgentService',
												name:'urgent',
												fieldLabel:'加急',
												width:30,
												boxLabel:'',
												listeners : {
											 		specialkey:function(checkbox, e){
										               if(e.getKey() == 17){
										            	   if(form.findByType("checkbox")[2].getValue()){
										            		   Ext.getCmp('urgentService').setValue(false);
										            	   }else{
										            		   Ext.getCmp('urgentService').setValue(true);
										            	   }
										               }else if(e.getKey()==13){
										            	   if(form.findByType("checkbox")[2].getValue()){
										            		   Ext.getCmp('urgentServiceVal').setValue(1);
										            	   }else{
										            		   Ext.getCmp('urgentServiceVal').setValue(0);
										            	   }
										            	   Ext.getCmp('subNo').focus(true,true);
										            	   return;
										               }
											 		},
											 		check:function(checkbox,flag){
											 			if(flag){
											 				 Ext.getCmp('urgentServiceVal').setValue(1);
											 			}else{
											 				 Ext.getCmp('urgentServiceVal').setValue(0);
											 			}
											 		}
									 			 }
												
											  }
											]
										}
									]
								},{//第三个配送信息
									xtype:'fieldset',
									//title:"配送信息",
									id:'setdevtype',
									layout:'table',
									height:93,
									style:'margin:1px;',
									layoutConfig: {columns:8},
									bodyStyle:'padding:5px 0px 0px 0px',
									defaults:{border:false,layout:'form',frame:false,labelAlign:'right',labelWidth:70},
									items:[
										{
											colspan:2,
											width:comWidth,
											labelWidth:80,
											items:[{
												xtype:'textfield',
												id:'subNo',
												name:'subNo',
												fieldLabel:'分单号',
												width:commentWidth,
												enableKeyEvents:true,
												listeners : {
											 		keydown:function(textField, e){
										                  if(e.getKey() == 13){
										                     	Ext.getCmp('takeModecombo').focus(true,true);
										                  }
											 		}
									 			}
											}]
										},{
											colspan:2,
											width:comWidth,
											labelWidth:80,
											items:[{
												xtype:'combo',
												width:100,
												fieldLabel:'提货方式',
												triggerAction : 'all',
												width:commentWidth,
						    					id:'takeModecombo',
						    					queryParam : 'filter_LIKES_typeName',
						    					valueField : 'takeMode',
			    								displayField : 'takeMode',
			    								name : 'takeMode',
												store:takeModeStore,
												emptyText : '选择类型',
												value:'市内自提',
						    					forceSelection : true,
						    					allowBlank : true,
						    					enableKeyEvents:true,
						    					listeners : {
											 		keydown:function(textField, e){
										                  if(e.getKey() == 13){
										                     	Ext.getCmp('receiptTypecombo').focus(true,true);
										                  }
											 		},
											 		select:function(combo,newValue,oldValue){
											 			changeCountPrice();
											 		}
									 			}
											}]
										},{
											colspan:2,
											width:comWidth,
											labelWidth:80,
											items:[{
												xtype:'combo',
												width:commentWidth,
												fieldLabel:'回单类型',
												triggerAction : 'all',
						    					 id:'receiptTypecombo',
						    					 queryParam : 'filter_LIKES_typeName',
						    					 valueField : 'receiptType',
			    								 displayField : 'receiptType',
			    								 hiddenName : 'receiptType',
												 store:receiptTypeStore,
												 emptyText : '请选择',
												 value:'新邦货单',
						    					 forceSelection : true,
						    					 allowBlank : true,
						    					 enableKeyEvents:true,
						    					 listeners : {
											 		keydown:function(textField, e){
										                     if(e.getKey() == 13){
										                     	Ext.getCmp('consigneeTel').focus(true,true);
										                  }
											 			}
									 			}
											}]
										},{
											colspan:2,
											labelWidth:80,
											width:comWidth,
											items:[{
												xtype:'combo',
												fieldLabel:'配送方式',
												triggerAction : 'all',
												store:devModeStore,
												queryParam : 'filter_LIKES_typeName',
												id:'distributionMode',
												name:'distributionMode',
												valueField : 'develpMode',
			    								displayField : 'develpMode',
												width:commentWidth,
												emptyText:'请选择',
												allowBlank : false,
												blankText:'配送方式不能为空！',
												listeners:{
													change:function(combo,newVal,oldVal){
														changeCountPrice();
													},select:function(combo){
														if(combo.getValue()== '新邦'){
															Ext.getCmp('goWhereIdcombo').setValue('');
															Ext.getCmp('goWhere').setValue('');
														}
														changeCountPrice();
													}
												}
											}]
										},{
											colspan:2,
											width:comWidth,
											labelWidth:80,
											items:[{
												xtype:'combo',
												mode: 'local',
												id:'consigneeTel',
												name:'consigneeTel',
												valueField : 'consigneeTel',
    											displayField : 'consigneeTel',
												width:commentWidth,
												queryParams:'consigneeTel',
												minChars:30,
												triggerAction:'all',
												maxHeight: 500, 
    											listWidth:520,
												forceSelection:false,
												emptyText:'136../021-784..',
												allowBlank:false,
												blankText:'电话不能为空!!',
												hideTrigger:true,
												fieldLabel:'收货人电话<span style="color:red">*</span>',
												tpl: '<div id="panel" style="height:300px"></div>',
												store:new Ext.data.SimpleStore({fields:["consigneeTel","consigneeTel"],data:[[]]}),
    											enableKeyEvents:true,
    											listeners : {
											 		keyup:function(combo, e){
										               if(e.getKey() == 13){
										                   Ext.getCmp('consignee').focus(true,true);
										               }
										               else if(e.getKey()==40){
										            	   consigneeGrid.getSelectionModel().selectFirstRow();
										            	   var row=consigneeGrid.getView().getRow(0); 
            												Ext.get(row).focus();
										               }else{
										               	   if((Ext.get('consigneeTel').dom.value).length>2){
										               	   		if((Ext.get('consigneeTel').dom.value).length<11)
										               	   		{
											               	   		combo.expand();
												               		consigneeStore.load({
												               			params:{
												               				filter_LIKES_consigneeTel:Ext.get('consigneeTel').dom.value+'%'
												               			}
												               		});
										               	   		}
										               	   }
										               }
											 		}
									 			}
											}]
										},{
											colspan:2,
											width:comWidth,
											labelWidth:80,
											items:[{
												xtype:'textfield',
												id:'consignee',
												name:'consignee',
												width:commentWidth,
												fieldLabel:'收货人姓名<span style="color:red">*</span>',
												allowBlank:false,
												blankText:'姓名不能为空!!',
												enableKeyEvents:true,
												listeners : {
											 		keydown:function(textField, e){
										               if(e.getKey() == 13){
										                   Ext.getCmp('city').focus(true,true);
										                }
											 	    }
									 			}
											}]
										},{
											colspan:2,
											width:comWidth,
											labelWidth:80,
											items:[
												{
												xtype:'combo',
												id:'city',
												name:'city',
												minChars : 0,
												triggerAction : 'all',
												pageSize:pageSize,
												width:commentWidth,
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
											 		keyup:function(combo, e){
										                     if(e.getKey() == 13){
										                     	//Ext.getCmp('town').clearValue();
										                    	var parentId=Ext.getCmp('city').getValue();
										                    	Ext.apply(areaTownStore.baseParams,{
													 				filter_EQS_parentName:parentId
													 			});
													 			if(combo.getValue()!=''){
													 				Ext.getCmp('town').focus(true,true);
													 				//Ext.getCmp('town').store.load();
													 			}
										                  	}
											 		},
											 		select :function(combo,e){
											 			Ext.getCmp('town').clearValue();
											 			var parentId=Ext.getCmp('city').getValue();			 		
											 			Ext.apply(areaTownStore.baseParams,{
											 				filter_EQS_parentName:parentId
											 			});
											 			//Ext.getCmp('town').store.load();
											 		}
									 			}
											}]
										},{
											colspan:2,
											labelWidth:80,
											width:comWidth,
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
												//hideTrigger:true,
    											displayField : 'areaName',
    											allowBlank:false,
    											emptyText:'请选择',
    											width:commentWidth,
												blankText:'所在县不能为空!!',
												enableKeyEvents:true,
						    					 listeners : {
						    					 	select:function(combo,record,index){
											 			var parentId=combo.getValue();
											 			Ext.getCmp('street').clearValue();
											 			Ext.apply(areaStreetStore.baseParams,{
											 				filter_EQS_parentName:parentId
											 			});
											 			//Ext.getCmp('street').store.load();
											 		},
											 		keyup:function(combo, e){
										                     if(e.getKey() == 13){
										                     	//Ext.getCmp('street').clearValue();
										                    	var parentId=Ext.get('town').dom.value;
										                    	Ext.getCmp('town').setValue(parentId);
										                    	Ext.apply(areaStreetStore.baseParams,{
											 						filter_EQS_parentName:parentId
											 					});
											 					if(combo.getValue()!=''){
											 						Ext.getCmp('street').focus(true,true);
										                     		combo.collapse();
											 					}
											 					//Ext.getCmp('street').store.load();
										                  }
											 		},
											 		expand:function(combo){
											 			areaTownStore.reload({
											 				params:{
											 					filter_EQS_parentName:Ext.getCmp('city').getValue()
											 				}
											 			});
											 		},
											 		blur:function(combo){
														var city=Ext.getCmp('city').getValue();
										 				var town=Ext.getCmp('town').getValue();
										 				if(Ext.getCmp('city').isValid()&& Ext.getCmp('street').isValid()){
										 					Ext.Ajax.request( {
															    url : sysPath + "/sys/basAreaAction!getAreaMsg.action",
																params : {
																	city:city,
																	town:town,
																	street:""
																},
																success : function(resp) {
																	var respText = Ext.util.JSON.decode(resp.responseText);
																	setaddrmsg(respText);
																}
															});
										 				
										 				}					 		
											 		}
									 			}
											}]
										}
										,{
											colspan:2,
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
												width:commentWidth,
												enableKeyEvents:true,
						    					 listeners : {
											 		select:function(combo,record,index){
											 			var city=Ext.getCmp('city').getValue();
										 				var town=Ext.getCmp('town').getValue();
										 				var street=Ext.getCmp('street').getValue();
										 				if(Ext.getCmp('city').isValid()&& Ext.getCmp('street').isValid()){
										 					Ext.Ajax.request( {
															    url : sysPath + "/sys/basAreaAction!getAreaMsg.action",
																params : {
																	city:city,
																	town:town,
																	street:street
																},
																success : function(resp) {
																	var respText = Ext.util.JSON.decode(resp.responseText);
																	setaddrmsg(respText);
																}
															});
										 				
										 				}
														Ext.getCmp('consigneeAddress').setValue(city+town+street);
											 			
											 		},keyup:function(combo,e){
											 			if(e.getKey()==13){
											 				combo.collapse();
											 				var aRank='街道';
											 				var street=Ext.get('street').dom.value;
											 				Ext.getCmp('street').setValue(street);
											 				var city=Ext.getCmp('city').getValue();
											 				var town=Ext.getCmp('town').getValue();
											 				if(Ext.getCmp('city').isValid()&& Ext.getCmp('street').isValid()){
											 					Ext.Ajax.request( {
																    url : sysPath + "/sys/basAreaAction!getAreaMsg.action",
																	params : {
																		city:city,
																		town:town,
																		street:street
																	},
																	success : function(resp) {
																		var respText = Ext.util.JSON.decode(resp.responseText);
																		setaddrmsg(respText);
																	}
																});
											 				
											 				}
											 				var town=Ext.getCmp('town').getValue();
															var city=Ext.getCmp('city').getValue();
															Ext.getCmp('consigneeAddress').setValue(city+town+street);
												 			Ext.getCmp('consigneeAddress').focus(true,true);
											 			}
											 			
											 		}
											 		,expand:function(combo){
											 			areaStreetStore.load({
										                    params:{
										                    	filter_EQS_parentName:Ext.getCmp('town').getValue()
										                    }
										                 });
											 		}
									 			}
											}]
										},{
											colspan:4,
											labelWidth:80,
											items:[
												{
												xtype:'textfield',
												fieldLabel:'收货人地址<span style="color:red">*</span>',
												id:'consigneeAddress',
												name:'addr',
												allowBlank:false,
												blankText:'收货人地址不能为空！',
												width:comWidth+110,
												enableKeyEvents:true,
						    					 listeners : {
													keydown:function(textfield,e){
														if(e.getKey()==13){
															Ext.getCmp('goods').focus(true,true);
														}
													}
						    					 }
												
											}]
										},{
											colspan:2,
											labelWidth:80,
											items:[{
												xtype : 'combo',
				                           		id:'goWhereIdcombo',
				    							typeAhead : true,
				    							forceSelection : true,
				    							queryParam : 'filter_LIKES_cusName',
				    							minChars :0,
				    							fieldLabel : '供应商',
				    							store : traStore,
				    							pageSize : pageSize,
				    							triggerAction : 'all',
				    							valueField : 'goWhereId',
				    							displayField : 'goWhere',
				    							hiddenName : 'goWhereId',
												width:110,
												listeners:{
													change:function(combo,newVal,oldVal){
														Ext.getCmp('goWhere').setValue(Ext.get('goWhereIdcombo').dom.value);
														if(Ext.getCmp('distributionMode').getValue()=='中转'){
															changeCountPrice();
														}
													}
												}
											}]
										}]
								},{//货物信息
									xtype:'fieldset',
									id:'setgoods',
									//title:"货物信息",
									height:120,
									layout:'table',
									style:'margin:2px;',
									layoutConfig: {columns:8},
									bodyStyle:'padding:5px 0px 0px 0px',
									defaults:{border:false,layout:'form',frame:false,labelAlign:'right',labelWidth:70},
									items:[
										{
											colspan:2,
											labelWidth:80,
											width:comWidth,
											items:[{
												xtype:'textfield',
												fieldLabel:'货物名称',
												id:'goods',
												name:'goods',
												width:commentWidth,
												enableKeyEvents:true,
												listeners:{
													keydown:function(textfield,e){
														if(e.getKey()==13){
															Ext.getCmp('chargemodecombo').focus(true,true);
														}
													}
												}
											}]
										},{
											colspan:2,
											labelWidth:80,
											width:comWidth,
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
						    					forceSelection : true,
						    					allowBlank : false,
						    					blankText:'计费方式不能为空!',
												width:commentWidth,
												enableKeyEvents:true,
												listeners:{
													keydown:function(textfield,e){
														if(e.getKey()==13){
															Ext.getCmp('piece').focus(true,true);
														}
													},
													select:function(combo,newVal,oldVal){
														if(Ext.getCmp('payerCombo').getValue()=='预付'){
															changeCountPrice();
														}
													}
												}
											}]
										},{
											colspan:2,
											labelWidth:80,
											width:comWidth,
											items:[{
												xtype:'textfield',
												fieldLabel:'件数<span style="color:red">*</span>',
												width:commentWidth,
												id:'piece',
												name:'piece',
												allowBlank : false,
						    					blankText:'件数不能为空!',
												enableKeyEvents:true,
												listeners:{
													keydown:function(textfield,e){
														if(e.getKey()==13){
															Ext.getCmp('bulk').focus(true,true);
														}
													},change:function(combo,newVal,oldVal){
														if(Ext.getCmp('payerCombo').getValue()=='预付'&&Ext.getCmp('chargemodecombo').getValue()=='件数'){
															changeCountPrice();
														}
													}
												}
											}]
										},{
											colspan:2,
											labelWidth:80,
											width:comWidth,
											items:[{
												xtype:'numberfield',
												id:'bulk',
												name:'bulk',
												fieldLabel:'体积',
												width:commentWidth,
												value:0,
												enableKeyEvents:true,
												listeners:{
													keydown:function(textfield,e){
														if(e.getKey()==13){
															 Ext.getCmp('cusWeight').focus(true,true);
														}
													},change:function(combo,newVal,oldVal){
														if(Ext.getCmp('payerCombo').getValue()=='预付'&&Ext.getCmp('chargemodecombo').getValue()=='体积'){
															changeCountPrice();
														}
													}
												}
											}]
										},{
											colspan:2,
											labelWidth:80,
											width:comWidth,
											items:[{
												xtype:'numberfield',
												id:'cqWeight',
												name:'cqWeight',
												allowBlank:false,
												blankText:'开单重量不能为空!!',
												fieldLabel:'开单重量<span style="color:red">*</span>',
												width:commentWidth,
												enableKeyEvents:true,
												listeners:{
													keydown:function(textfield,e){
														if(e.getKey()==13){
															Ext.getCmp('cqWeight').setValue(textfield.getValue());
															Ext.getCmp('cqWeight').focus(true,true);
														}
													},blur:function (textfield){
														Ext.getCmp('cqWeight').setValue(textfield.getValue());
													}
											   }
											}]
										},{
											colspan:2,
											labelWidth:80,
											width:comWidth,
											items:[{
												xtype:'numberfield',
												id:'cusWeight',
												name:'cusWeight',
												fieldLabel:'计费重量<span style="color:red">*</span>',
												width:commentWidth,
												allowBlank:false,
												blankText:'计费重量不能为空!!',
												enableKeyEvents:true,
												listeners:{
													keydown:function(textfield,e){
														if(e.getKey()==13){
															Ext.getCmp('paymentCollection').focus(true,true);
														}
													},change:function(combo,newVal,oldVal){
														var payer=Ext.getCmp('payerCombo').getValue();
														var chargeMode=Ext.getCmp('chargemodecombo').getValue();
														if(payer=='到付'||(payer=='预付'&&chargeMode=='重量')){
															changeCountPrice();
														}
													}
											   }
											}]
										},{
											colspan:2,
											labelWidth:80,
											width:comWidth,
											items:[{
												xtype:'numberfield',
												id:'paymentCollection',
												name:'paymentCollection',
												fieldLabel:'代收货款<span style="color:red">*</span>',
												allowBlank:false,
												blankText:'代收货款不能为空!',
												maxValue:99999999.99,
												minValue:0.0,
												maxLength:11,
												width:commentWidth,
												enableKeyEvents:true,
												listeners:{
													keydown:function(textfield,e){
														if(e.getKey()==13){
															Ext.getCmp('payerCombo').focus(true,true);
														}
													}
											    }
											}]
										},{
											colspan:2,
											labelWidth:80,
											width:comWidth,
											items:[{
												xtype:'combo',
												fieldLabel:'付款方',
												width:commentWidth,
												triggerAction : 'all',
						    					id:'payerCombo',
						    					name:'whoCash',
						    					queryParam : 'filter_LIKES_typeName',
						    					valueField : 'whoCash',
			    								displayField : 'whoCash',
			    								hiddenName : 'whoCash',
												store:payerStore,
												emptyText : '选择类型',
												value:'预付',
						    					forceSelection : true,
						    					allowBlank : false,
						    					enableKeyEvents:true,
												listeners:{
													keypress:function(textfield,e){
														if(e.getKey()==13){
															if(textfield.getValue()=='预付'){
																Ext.getCmp('totalAddFee').setValue(0);
																Ext.getCmp('cusValueAddFee').setValue(0);
																Ext.getCmp('cpValueAddFee').setValue(0);
																addFeeRecordStore.removeAll();
																
																autoFeeStore.load({
																	params:{
																		filter_EQL_autoFee:1,
																		filter_EQS_payMan:'预付'
																	}
																});
																Ext.getCmp('consigneeFee').getEl().dom.readOnly=true;
																Ext.getCmp('consigneeRate').getEl().dom.readOnly=true;
																if(Ext.getCmp('cuscombo').getValue()==''){
																	Ext.Msg.alert(alertTitle,'代理不能为空!!',onfocus);
																	function onfocus(){
																		return;
																	}
																}else{
																	countPrice(textfield);
																	Ext.getCmp('remark').focus(true,true);
																}
															}else if(textfield.getValue()=='到付'){
																Ext.getCmp('totalAddFee').setValue(0);
																Ext.getCmp('cusValueAddFee').setValue(0);
																Ext.getCmp('cpValueAddFee').setValue(0);
																addFeeRecordStore.removeAll();
																
																autoFeeStore.load({
																	params:{
																		filter_EQL_autoFee:1,
																		filter_EQS_payMan:'到付'
																	}
																});
																Ext.getCmp('cpFee').getEl().dom.readOnly=true;
																Ext.getCmp('cpRate').getEl().dom.readOnly=true;
																if(Ext.getCmp('consigneeTel').getValue()==''){
																	Ext.Msg.alert(alertTitle,'收货人电话不能为空!!',onfocus);
																	function onfocus(){
																		return;
																	}
																}else{
																	countPrice(textfield);
																	Ext.getCmp('remark').focus(true,true);
																}
															}else if(textfield.getValue()=='双方付'){
																Ext.getCmp('totalAddFee').setValue(0);
																Ext.getCmp('cusValueAddFee').setValue(0);
																Ext.getCmp('cpValueAddFee').setValue(0);
																addFeeRecordStore.removeAll();
																autoFeeStore.load({
																	params:{
																		filter_EQL_autoFee:1,
																		filter_EQS_payMan:'到付'
																	}
																});
																countPrice(textfield);
																Ext.getCmp('cpFee').getEl().dom.readOnly=false;
																Ext.getCmp('consigneeFee').getEl().dom.readOnly=false;
																Ext.getCmp('cpFee').focus(true,true);
															}
															
														}
													},change:function(combo,newVal,oldVal){
														//changeCountPrice();
													},select:function(combo,record,index){
														if(combo.getValue()=='预付'){
															Ext.getCmp('totalAddFee').setValue(0);
															Ext.getCmp('cusValueAddFee').setValue(0);
															Ext.getCmp('cpValueAddFee').setValue(0);
															addFeeRecordStore.removeAll();
															
															autoFeeStore.load({
																params:{
																	filter_EQL_autoFee:1,
																	filter_EQS_payMan:'预付'
																}
															});
															Ext.getCmp('consigneeRate').setValue(0);
															Ext.getCmp('consigneeFee').setValue(0);
														}else if(combo.getValue()=='到付'){
															Ext.getCmp('totalAddFee').setValue(0);
															Ext.getCmp('cusValueAddFee').setValue(0);
															Ext.getCmp('cpValueAddFee').setValue(0);
															addFeeRecordStore.removeAll();
															autoFeeStore.load({
																params:{
																	filter_EQL_autoFee:1,
																	filter_EQS_payMan:'到付'
																}
															});
															Ext.getCmp('cpRate').setValue(0);
															Ext.getCmp('cpFee').setValue(0);
														}else if(combo.getValue()=='双方付'){
															Ext.getCmp('totalAddFee').setValue(0);
															Ext.getCmp('cusValueAddFee').setValue(0);
															Ext.getCmp('cpValueAddFee').setValue(0);
															addFeeRecordStore.removeAll();
															autoFeeStore.load({
																params:{
																	filter_EQL_autoFee:1,
																	filter_EQS_payMan:'到付'
																}
															});
															Ext.getCmp('cpRate').setValue(0);
															Ext.getCmp('cpFee').setValue(0);
															Ext.getCmp('consigneeRate').setValue(0);
															Ext.getCmp('consigneeFee').setValue(0);
														}
														changeCountPrice();
													}
											    }
											}]
										},{
											colspan:2,
											labelWidth:80,
											width:comWidth,
											items:[{xtype:'hidden',name:'normTraRate',id:'normTraRate'},{
												xtype:'numberfield',
												name:'traFeeRate',
												id:'traFeeRate',
												fieldLabel:'中转费率',
												readOnly:true,
												width:commentWidth
											}]
										},{
											colspan:2,
											labelWidth:80,
											width:comWidth,
											items:[{
												xtype:'numberfield',
												fieldLabel:'中转费',
												name:'traFee',
												id:'traFee',
												blankText:'中转费不能为空!',
												maxValue:99999999.99,
												minValue:0.0,
												maxLength:11,
												allowBlank:false,
												//readOnly:true,
												width:commentWidth
											}]
										},{
											colspan:2,
											labelWidth:80,
											width:comWidth,
											items:[{xtype:'hidden',name:'normCpRate',id:'normCpRate'},{
												xtype:'numberfield',
												id:'cpRate',
												name:'cpRate',
												fieldLabel:'预付提送费率',
												readOnly:true,
												width:commentWidth
											}]
										},{
											colspan:2,
											labelWidth:80,
											width:comWidth,
											items:[{
												xtype:'numberfield',
												fieldLabel:'预付提送费',
												allowBlank:false,
												maxValue:99999999.99,
												minValue:0.0,
												maxLength:11,
												blankText:'预付提送费不能为空!',
												id:'cpFee',
												name:'cpFee',
												width:commentWidth,
												enableKeyEvents:true,
												listeners:{
													keydown:function(textfield,e){
														if(e.getKey()==13){
																Ext.getCmp('consigneeFee').focus(true,true);
														}
													}
												}
											}]
										},{
											colspan:2,
											labelWidth:80,
											width:comWidth,
											items:[{xtype:'hidden',name:'normConsigneeRate',id:'normConsigneeRate'},{
												xtype:'numberfield',
												id:'consigneeRate',
												name:'consigneeRate',
												fieldLabel:'到付提送费率',
												width:commentWidth
											}]
										},{
											colspan:2,
											labelWidth:80,
											width:comWidth,
											items:[{
												xtype:'numberfield',
												id:'consigneeFee',
												name:'consigneeFee',
												fieldLabel:'到付提送费',
												blankText:'到付提送费不能为空!',
												maxValue:99999999.99,
												minValue:0.0,
												maxLength:11,
												allowBlank:false,
												width:commentWidth,
												enableKeyEvents:true,
												listeners:{
													keydown:function(textfield,e){
														if(e.getKey()==13){
																Ext.getCmp('remark').focus(true,true);
														}
													}
												}
											}]
										},{
											colspan:4,
											labelWidth:80,
											width:comWidth*2,
											items:[{
													xtype:'textfield',
													id:'remark',
													name:'remark',
													fieldLabel:'备注',
													width:comWidth+110,
													enableKeyEvents:true,
													listeners:{
														keydown:function(textfield,e){
															if(e.getKey()==13){
																Ext.getCmp('savefaxbtn').focus(true,true);
															}
														}
												    }
											}]
										}]
								},
								{xtype:'hidden',name:'dno',id:'dno'},
								{xtype:'hidden',name:'ts',id:'ts'},
								{xtype:'hidden',name:'endDepartId',id:'endDepartId'},
								{xtype:'hidden',name:'customerService',id:'customerService'},
								{xtype:'hidden',name:'endDepart',id:'endDepart'},
								{xtype:'hidden',name:'inDepartId',id:'inDepartId',value:bussDepart},
								{xtype:'hidden',name:'inDepart',id:'inDepart',value:bussDepartName},
								{xtype:'hidden',name:'curDepartId',id:'curDepartId',value:bussDepart},
								{xtype:'hidden',name:'curDepart',id:'curDepart',value:bussDepartName},
								{xtype:'hidden',id:'areaType',name:'areaType'},
								{xtype:'hidden',name:'faxMainId',id:'faxMainId',value:'0'},
								{xtype:'hidden',name:'areaRank',id:'areaRank'},
								{xtype:'hidden',name:'gowhere',id:'goWhere'},
								{xtype:'hidden',name:'distributionDepartId',id:'distributionDepartId'},
								{xtype:'hidden',name:'distributionDepart',id:'distributionDepart'}
								//{xtype:'hidden',name:'changeType',id:'changeTypeText'}
								,tablePanel
						],
						listeners:{
						afterlayout:function(){
							if(null!=changeDno && changeDno.length>0){
							 	var recd=new Ext.data.Record({
									dno:changeDno
							 	});
							 	var searchFaxUrl='/fax/oprFaxInAction!ralaList.action';
								Ext.Ajax.request( {
									url : sysPath + searchFaxUrl,
									params : {
											filter_EQL_dno:changeDno,
											privilege:68
									},
									success : function(resp) {
										var res = Ext.util.JSON.decode(resp.responseText);
										var jdata =res.result; 
										if(jdata.length>0){
											var recd=new Ext.data.Record({
											dno:jdata[0].dno==null?'':jdata[0].dno,
											cusId:jdata[0].cusId==null?'':jdata[0].cusId,
											cpName:jdata[0].cpName==null?'':jdata[0].cpName,
											flightNo:jdata[0].flightNo==null?'':jdata[0].flightNo,
											flightDate:jdata[0].flightDate==null?'':jdata[0].flightDate,
											flightTime:jdata[0].flightTime==null?'':jdata[0].flightTime,
											trafficMode:jdata[0].trafficMode==null?'':jdata[0].trafficMode,
											flightMainNo:jdata[0].flightMainNo==null?'':jdata[0].flightMainNo,
											subNo:jdata[0].subNo==null?'':jdata[0].subNo,
											distributionMode:jdata[0].distributionMode==null?'':jdata[0].distributionMode,
											takeMode:jdata[0].takeMode==null?'':jdata[0].takeMode,
											receiptType:jdata[0].receiptType==null?'':jdata[0].receiptType,
											consignee:jdata[0].consignee==null?'':jdata[0].consignee,
											consigneeTel:jdata[0].consigneeTel==null?'':jdata[0].consigneeTel,
											city:jdata[0].city==null?'':jdata[0].city,
											town:jdata[0].town==null?'':jdata[0].town,
											addr:jdata[0].addr==null?'':jdata[0].addr,
											piece:jdata[0].piece==null?'':jdata[0].piece,
											cqWeight:jdata[0].cqWeight==null?'':jdata[0].cqWeight,
											cusWeight:jdata[0].cusWeight==null?'':jdata[0].cusWeight,
											bulk:jdata[0].bulk==null?'':jdata[0].bulk,
											inDepartId:jdata[0].inDepartId==null?'':jdata[0].inDepartId,
											inDepart:jdata[0].inDepart==null?'':jdata[0].inDepart,
											curDepartId:jdata[0].curDepartId==null?'':jdata[0].curDepartId,
											curDepart:jdata[0].curDepart==null?'':jdata[0].curDepart,
											endDepartId:jdata[0].endDepartId==null?'':jdata[0].endDepartId,
											endDepart:jdata[0].endDepart==null?'':jdata[0].endDepart,
											goWhere:jdata[0].goWhere==null?'':jdata[0].goWhere,
											distributionDepartId:jdata[0].distributionDepartId==null?'':jdata[0].distributionDepartId,
											distributionDepart:jdata[0].distributionDepart==null?'':jdata[0].distributionDepart,
											greenChannel:jdata[0].greenChannel==null?'':jdata[0].greenChannel,
											urgentService:jdata[0].urgentService==null?'':jdata[0].urgentService,
											wait:jdata[0].wait==null?'':jdata[0].wait,
											sonderzug:jdata[0].sonderzug==null?'':jdata[0].sonderzug,
											carType:jdata[0].carType==null?'':jdata[0].carType,
											roadType:jdata[0].roadType==null?'':jdata[0].roadType,
											remark:jdata[0].remark==null?'':jdata[0].remark,
											status:jdata[0].status==null?'':jdata[0].status,
											barCode:jdata[0].barCode==null?'':jdata[0].barCode,
											paymentCollection:jdata[0].paymentCollection==null?'':jdata[0].paymentCollection,
											traFee:jdata[0].traFee==null?'':jdata[0].traFee,
											traFeeRate:jdata[0].traFeeRate==null?'':jdata[0].traFeeRate,
											cpRate:jdata[0].cpRate==null?'':jdata[0].cpRate,
											cpFee:jdata[0].cpFee==null?'':jdata[0].cpFee,
											consigneeRate:jdata[0].consigneeRate==null?'':jdata[0].consigneeRate,
											consigneeFee:jdata[0].consigneeFee==null?'':jdata[0].consigneeFee,
											whoCash:jdata[0].whoCash==null?'':jdata[0].whoCash,
											createName:jdata[0].createName==null?'':jdata[0].createName,
											createTime:jdata[0].createTime==null?'':jdata[0].createTime,
											updateName:jdata[0].updateName==null?'':jdata[0].updateName,
											updateTime:jdata[0].updateTime==null?'':jdata[0].updateTime,
											ts:jdata[0].ts==null?'':jdata[0].ts,
											faxMainId:jdata[0].faxMainId==null?'':jdata[0].faxMainId,
											customerService:jdata[0].customerService==null?'':jdata[0].customerService,
											cusValueAddFee:jdata[0].cusValueAddFee==null?'':jdata[0].cusValueAddFee,
											cpValueAddFee:jdata[0].cpValueAddFee==null?'':jdata[0].cpValueAddFee,
											goodsStatus:jdata[0].goodsStatus==null?'':jdata[0].goodsStatus,
											declaredValue:jdata[0].declaredValue==null?'':jdata[0].declaredValue,
											goods:jdata[0].goods==null?'':jdata[0].goods,
											valuationType:jdata[0].valuationType==null?'':jdata[0].valuationType,
											sonderzugPrice:jdata[0].sonderzugPrice==null?'':jdata[0].sonderzugPrice,
											areaType:jdata[0].areaType==null?'':jdata[0].areaType,
											street:jdata[0].street==null?'':jdata[0].street,
											areaRank:jdata[0].areaRank==null?'':jdata[0].areaRank,
											normTraRate:jdata[0].normTraRate==null?'':jdata[0].normTraRate,
											normCpRate:jdata[0].normCpRate==null?'':jdata[0].normCpRate,
											normConsigneeRate:jdata[0].normConsigneeRate==null?'':jdata[0].normConsigneeRate,
											normSonderzugPrice:jdata[0].normSonderzugPrice==null?'':jdata[0].normSonderzugPrice,
											cusDepartName:jdata[0].cusDepartName==null?'':jdata[0].cusDepartName,
											cusDepartCode:jdata[0].cusDepartCode==null?'':jdata[0].cusDepartCode,
											realGoWhere:jdata[0].realGoWhere==null?'':jdata[0].realGoWhere,
											goWhereId:jdata[0].goWhereId==null?'':jdata[0].goWhereId,
											cpSonderzugPrice:jdata[0].cpSonderzugPrice==null?'':jdata[0].cpSonderzugPrice
										});
											faxStore.removeAll();
											faxStore.add(recd);
											setMsg(recd);
														
										}
									}
								});
							}
					}
				}
	});
	//保存传真
	/*
	function saveFax(){
		if(msgValidate(form)){
			
		}

		var chargeMode=Ext.getCmp('chargemodecombo').getValue();
		if(chargeMode=="体积"&&Ext.getCmp('bulk').getValue()==''){
			Ext.Msg.alert(alertTitle,"体积不能为空!!",onfocus);
			function onfocus(){
				Ext.getCmp('bulk').focus(true,true);
				return;
			}
		}
		if(form.findByType("checkbox")[0].getValue()){
			if(Ext.getCmp('sonderzugPrice').getValue()==''){
				Ext.Msg.alert(alertTitle,'无专车费，无法保存!!');
				return;
			}
		}
		if(Ext.getCmp('distributionMode').getValue()=='中转'){
			if(Ext.getCmp('traFee').getValue()==''){
				Ext.Msg.alert(alertTitle,'无中转费，无法保存!!');
				return;
			}
		}
		if(form.getForm().isValid()) {
			if(Ext.getCmp('payerCombo').getValue()=='双方付'){
				var cpFee=Number(Ext.getCmp('cpFee').getValue());
				var consigneeFee=Number(Ext.getCmp('consigneeFee').getValue());
				if((cpFee+consigneeFee)<cashPrice){
					Ext.Msg.alert(alertTitle,'预付+到付提送费不能小于'+cashPrice+'元!');
					return false;
				}
			}
			Ext.Ajax.request({
				url:sysPath+"/fax/oprFaxInAction!ralaList.action",
				params:{
					privilege:68,
					limit:pageSize,
					filter_EQL_cusId:Ext.getCmp('cuscombo').getValue(),
					filter_EQS_consigneeTel:Ext.getCmp('consigneeTel').getValue(),
					filter_EQS_piece:Ext.getCmp('piece').getValue(),
					filter_EQS_paymentCollection:Ext.getCmp('paymentCollection').getValue(),
					//filter_EQS_curDepart:Ext.getCmp('curDepart').getValue(),
					filter_EQS_cusWeight:Ext.getCmp('cusWeight').getValue()
				},
				success:function(resp1){
					var respText1 = Ext.util.JSON.decode(resp1.responseText);
					if(respText1.result.length>0){
						Ext.Msg.confirm(alertTitle, "此票货物已存在,是否继续添加?", function(btnYes) {
			    			if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
			    				setFaxMainNo();
					    	}
		    			});
					}else{
						setFaxMainNo();
					}
				}
			});
		}
	}
	/*
	//设置代理传真主单号
	function setFaxMainNo(){
		//获得代理传真主单号
		Ext.Ajax.request({
			url:sysPath+"/fax/oprFaxInAction!ralaList.action",
			params:{
				privilege:68,
				limit:2,
				filter_EQS_flightMainNo:Ext.getCmp('flightMain').getValue()
			},
			success:function(resp){
				var respText = Ext.util.JSON.decode(resp.responseText);
				if(respText.result.length>0){
					Ext.getCmp('faxMainId').setValue(respText.result[0].faxMainId);
				}
				formSubmit();
			}
		});
	}
	//表单提交
	function formSubmit(){
		if(Ext.getCmp('customerService').getValue()==''){
			Ext.Msg.alert(alertTitle,"该代理不存在相对的客服员,请维护后再更改!!");
			return;
		}
		var datas="";
		for(var i=0;i<addFeeRecordStore.getCount();i++){
			if(i>0){
				datas+='&';
			}
			datas += "addFeeList["+i+"].feeName="+addFeeRecordStore.getAt(i).get('feeName')+'&'
				  +"addFeeList["+i+"].feeCount="+addFeeRecordStore.getAt(i).get('feeCount')+'&'
				  +"addFeeList["+i+"].feeLink="+addFeeRecordStore.getAt(i).get('feeLink')+'&'
				  +"addFeeList["+i+"].payType="+addFeeRecordStore.getAt(i).get('payType');
		}
		var datareq="";
		for(var i=0;i<requestStore.getCount();i++){
			if(i>0){
				datareq+='&';
			}
			datareq += "requestList["+i+"].requestStage="+requestStore.getAt(i).get('requestStage')+'&'
				  +"requestList["+i+"].request="+requestStore.getAt(i).get('request')+'&'
				  +"requestList["+i+"].requestType="+requestStore.getAt(i).get('requestType');
		}
		form.getForm().submit({
				url : sysPath + "/fax/oprFaxInAction!save.action",
				params:datas+"&"+datareq,
				waitMsg : '正在保存数据...',
				success : function(form, action) {
					Ext.Msg.alert(alertTitle,"保存成功!",onfocus);
					function onfocus(){
						Ext.getCmp('subNo').focus(true,true);
						return;
					}
					faxStore.load({
						params:{
							filter_EQS_flightMainNo:Ext.getCmp('flightMain').getValue()
						}
					});
				},
				failure : function(form, action) {
					if (action.faliureType == Ext.form.Action.SERVER_INVALID) {
						Ext.Msg.alert(alertTitle, "服务器连接异常，请联系管理员！");
					} else {
						if (action.result.msg) {
							Ext.Msg.alert(alertTitle,
							action.result.msg);
						}
					}
				}
			});
	}
	*/
	//收货人信息grid
	var consigneeGrid=new Ext.grid.GridPanel({
    		//title : '收货人信息',
    		height : 300,
    		//width : Ext.lib.Dom.getViewWidth(),
    		id : 'consigneeGrid',
    		frame : false,
    		loadMask : true,
    		stripeRows : true,
    		onViewClick :Ext.emptyFn,
    		onSelect:Ext.emptyFn,
    		viewConfig:{
    			scrollOffset: 0,
    			autoScroll : true
    		},
    		cm:new Ext.grid.ColumnModel([rowNum,{
	    			header:'收货人姓名',
	    			dataIndex:'consigneeName'
	    		},{
	    			header:'收货人电话',
	    			dataIndex:'consigneeTel'
	    		},{
	    			header:'收货人地址',
	    			dataIndex:'consigneeAddr'
	    		},{
	    			header:'地址类型',
	    			dataIndex:'addrType'
	    		},{
	    			header:'配送方式',
	    			dataIndex:'distributionMode'
	    		},{
	    			header:'终端部门',
	    			dataIndex:'endDepart'
	    		},{
	    			header:'市',
	    			dataIndex:'city',
	    			hidden:true
	    		},{
	    			header:'区/县',
	    			dataIndex:'town',
	    			hidden:true
	    		},{
	    			header:'镇/街道',
	    			dataIndex:'street',
	    			hidden:true
	    		},{
	    			header:'去向Id',
	    			dataIndex:'goWhereId',
	    			hidden:true
	    		},{
	    			header:'去向',
	    			dataIndex:'goWhere',
	    			hidden:true
	    		},{
	    			header:'配送部门',
	    			dataIndex:'distributionDepart',
	    			hidden:true
	    		}
    		]),
    		ds:consigneeStore,
    		listeners:{
    			rowdblclick:function(grid, rowindex, e){
    				var dno=Ext.getCmp('dno').getValue();
    				var record=grid.getSelectionModel().getSelected();
    				if(form.findByType("checkbox")[0].getValue()||Ext.getCmp('takeModecombo').getValue()=='机场自提'){
						Ext.Ajax.request({
							url:sysPath+'/stock/oprStatusAction!list.action',
							params:{
								limit:pageSize,
								filter_EQL_dno:dno
							},success:function(resp){
								var respText = Ext.util.JSON.decode(resp.responseText);
								if(respText.result[0].outStatus==0||respText.result[0].outStatus==2){
									 Ext.getCmp('distributionMode').setValue("新邦");
									Ext.getCmp('goWhere').setValue(record.data.goWhere);
				    				Ext.getCmp('goWhereIdcombo').setValue(record.data.goWhereId);
					    			Ext.getCmp('goWhereIdcombo').setRawValue(record.data.goWhere);
								}
							}
						});
					}else{
						Ext.Ajax.request({
							url:sysPath+'/stock/oprStatusAction!list.action',
							params:{
								limit:pageSize,
								filter_EQL_dno:dno
							},success:function(resp){
								var respText = Ext.util.JSON.decode(resp.responseText);
								if(respText.result[0].outStatus==0||respText.result[0].outStatus==2){
									Ext.getCmp('distributionMode').setValue(record.data.distributionMode);
									Ext.getCmp('goWhere').setValue(record.data.goWhere);
				    				Ext.getCmp('goWhereIdcombo').setValue(record.data.goWhereId);
					    			Ext.getCmp('goWhereIdcombo').setRawValue(record.data.goWhere);
								}
							}
						});
					}
    				Ext.getCmp('consignee').setValue(record.data.consigneeName);
    				Ext.getCmp('consigneeAddress').setValue(record.data.consigneeAddr);
    				Ext.getCmp('consigneeTel').setValue(record.data.consigneeTel);
    				Ext.getCmp('areaType').setValue(record.data.addrType);
    				Ext.getCmp('city').setValue(record.data.city);
	    			Ext.getCmp('town').setValue(record.data.town);
	    			Ext.getCmp('street').setValue(record.data.street);
    				Ext.getCmp('endDepart').setValue(record.data.endDepart);
    				Ext.getCmp('endDepartId').setValue(record.data.endDepartId);
    				Ext.getCmp('distributionDepart').setValue(record.data.distributionDepart);
    				Ext.getCmp('showEndDepart').getEl().update('终端部门:'+record.data.distributionDepart);
    				Ext.getCmp('distributionDepartId').setValue(record.data.distributionDepartId);
    				Ext.getCmp('consigneeTel').collapse();
    				Ext.getCmp('goods').focus(true,true);
    			},
    			keydown:function(e){
    				if(e.getKey()==13){
    					var record=consigneeGrid.getSelectionModel().getSelected();
	    				Ext.getCmp('consignee').setValue(record.data.consigneeName);
	    				Ext.getCmp('consigneeAddress').setValue(record.data.consigneeAddr);
	    				Ext.getCmp('consigneeTel').setValue(record.data.consigneeTel);
	    				if(form.findByType("checkbox")[0].getValue()||Ext.getCmp('takeModecombo').getValue()=='机场自提'){
							Ext.Ajax.request({
								url:sysPath+'/stock/oprStatusAction!list.action',
								params:{
									limit:pageSize,
									filter_EQL_dno:dno
								},success:function(resp){
									var respText = Ext.util.JSON.decode(resp.responseText);
									if(respText.result[0].outStatus==0||respText.result[0].outStatus==2){
										 Ext.getCmp('distributionMode').setValue("新邦");
										Ext.getCmp('goWhere').setValue(record.data.goWhere);
					    				Ext.getCmp('goWhereIdcombo').setValue(record.data.goWhereId);
						    			Ext.getCmp('goWhereIdcombo').setRawValue(record.data.goWhere);
									}
								}
							});
						}else{
							Ext.Ajax.request({
								url:sysPath+'/stock/oprStatusAction!list.action',
								params:{
									limit:pageSize,
									filter_EQL_dno:dno
								},success:function(resp){
									var respText = Ext.util.JSON.decode(resp.responseText);
									if(respText.result[0].outStatus==0||respText.result[0].outStatus==2){
										Ext.getCmp('distributionMode').setValue(record.data.distributionMode);
										Ext.getCmp('goWhere').setValue(record.data.goWhere);
					    				Ext.getCmp('goWhereIdcombo').setValue(record.data.goWhereId);
						    			Ext.getCmp('goWhereIdcombo').setRawValue(record.data.goWhere);
									}
								}
							});
						}
	    				Ext.getCmp('endDepartId').setValue(record.data.endDepartId);
	    				Ext.getCmp('endDepart').setValue(record.data.endDepart);
	    				Ext.getCmp('areaType').setValue(record.data.addrType);
	    				Ext.getCmp('city').setValue(record.data.city);
	    				Ext.getCmp('town').setValue(record.data.town);
	    				Ext.getCmp('street').setValue(record.data.street);
	    				Ext.getCmp('distributionDepart').setValue(record.data.distributionDepart);
	    				Ext.getCmp('showEndDepart').getEl().update('终端部门:'+record.data.distributionDepart);
	    				Ext.getCmp('distributionDepartId').setValue(record.data.distributionDepartId);
	    				Ext.getCmp('consigneeTel').collapse();
	    				Ext.getCmp('consigneeAddress').focus(true,true);
    				}
    			}
    		}
    });
    
	Ext.getCmp("consigneeTel").on('expand',function(){  
			if(consigneeGrid.rendered){
				consigneeGrid.doLayout();
			}else{
    			consigneeGrid.render('panel');
    			consigneeStore.load();
    		}
    });
    consigneeGrid.getStore().on('load',function(s,records){ 
        var girdcount=0; 
        s.each(function(r){ 
            if(r.get('cusRecordId')!=null){ 
                consigneeGrid.getView().getRow(girdcount).style.backgroundColor='#00C000';
            }
            girdcount=girdcount+1; 
        }); 
	}); 
	Ext.getCmp("consigneeTel").on('collapse',function(){
    	return false;
    }); 
});
var cashPrice=0;//提送费
function countPrice(textfield) {
	var form = Ext.getCmp('addfaxform');
	var town = Ext.getCmp('town').getValue();
	var street = Ext.getCmp('street').getValue();
	var city = Ext.getCmp('city').getValue();
	var roadType = Ext.getCmp('roadtypecombo').getValue();
	var disDepartId=Ext.getCmp('distributionDepartId').getValue();
	var cusId = Ext.getCmp('cuscombo').getValue();
	var cusN = Ext.get('cuscombo').dom.value;
	var addrType = Ext.getCmp('areaType').getValue();
	var startCity = Ext.getCmp('endCity').getValue();
	var goWhereId=Ext.getCmp('goWhereIdcombo').getValue();
	
	var tel = Ext.getCmp('consigneeTel').getValue();
	var whoCash = textfield.getValue();
	var piece = Ext.getCmp('piece').getValue();
	var bulk = Ext.getCmp('bulk').getValue();
	var tels;
	if (tel.indexOf('/') < 0) {
		tels = tel;
	} else {
		tels = tel.split('/');
	}
	//获得标准专车协议价
	function findNormSondPrice(type){
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
					//Ext.getCmp('showMsg').getEl().update('<span style="color:red">路型为:'+roadType+',地址类型为:'+addrType+',线路名称为:'+street+',或'+townText+'的协议价格不存在!</span>');
					return false;
				}else{
					if(type=='st'){
						setSpecialRate(respText1);
						setNormSpecialRate(respText1);
					}else if(type=='cp'){
						setNormSpecialRate(respText1);
					}
				}
				
			}
		});
	}
	// 专车
	if (form.findByType("checkbox")[0].getValue()) {
		Ext.Ajax.request({
			url : sysPath + "/fi/specialTrainRateAction!findRate.action",
			params : {
				limit : pageSize,
				cusId : cusId,
				roadType : roadType,
				town : town,
				street : street,
				//addrType : addrType,
				departId : bussDepart
			},
			success : function(resp) {
				var respText = Ext.util.JSON.decode(resp.responseText);
				if (respText.resultMap.length < 1) {
					// 该代理无协议价格
					Ext.Msg.alert(alertTitle, '路型为:'+roadType+',地区名称为:'+street+',或'+town+'或'+city+'的标准专车协议价格不存在!');						
					
				} else {
					var respText = Ext.util.JSON.decode(resp.responseText);
					setSpecialRate(respText);
					findNormSondPrice('cp');
					Ext.getCmp('showMsg').getEl().update('<span style="color:red">已找到专车协议价格，编号为：'+respText.resultMap[0].ID+'！</span>');
				}
			}
		});
	}
	var valuationType = Ext.getCmp('chargemodecombo').getValue();
	var trafficMode = Ext.getCmp('trafficModecombo').getValue();
	var takeMode = Ext.getCmp('takeModecombo').getValue();
	var distributeMode = Ext.getCmp('distributionMode').getValue();
	var disdepartName=Ext.getCmp('distributionDepart').getValue();
	var cusWeight = Number(Ext.getCmp('cusWeight').getValue());//计费重量
	var cusName= Ext.getCmp('goWhere').getValue();
	if(form.findByType("checkbox")[0].getValue()){
		takeMode = '机场自提';
	}
	Ext.Ajax.request({
		url:sysPath+'/fi/faxRateSearchAction!searchRate.action',
		method:'post',
		params:{
			limit:pageSize,
			start:0,
			cusId:cusId,
			whoCash:whoCash,
			cusWeight:cusWeight,
			takeMode:takeMode,
			distributionMode:distributeMode,
			bulk:bulk,
			piece:piece,
			disDepartId:disDepartId,
			cusName:cusName,
			cpName:cusN,
			cpId:goWhereId,
			consigneeTel:tels,
			addrType:addrType,
			valuationType:valuationType,
			startCity:startCity,
			trafficMode:trafficMode,
			city:city,
			town:town,
			street:street
		},
		success:function(resp){
			var respText = Ext.util.JSON.decode(resp.responseText);
			if(respText.resultMap.length>0){
				if(whoCash =='预付'||whoCash =='双方付'){
					Ext.Ajax.request({
						url : sysPath + "/sys/customerAction!list.action",
						params : {
							filter_EQL_id:cusId,
							filter_EQL_isProjectcustomer:1
						},
						success : function(res) {
							var resText1 = Ext.util.JSON.decode(res.responseText);
							// 是项目客户
							if (resText1.result.length > 0) {
								var cpProRate = respText.resultMap[0].cpProRate;
								var cpProFee = respText.resultMap[0].cpProFee;
								
								if(cpProRate == null || cpProRate ==''){
									Ext.Msg.alert(alertTitle,'此项目客户的协议价格不存在，请维护协议价后再次尝试!');
									Ext.getCmp('cpFee').setValue(0);
									Ext.getCmp('cpRate').setValue(0);
									Ext.getCmp('showMsg').getEl().update('<span style="color:red">件数为:'+piece+',重量为:'+cusWeight+',体积为:'+bulk+',目的站为:'+city+'的项目客户协议价格不存在！</span>');
									return;
								}else{
									cashCpFee = cpProFee;
									cashPrice = cpProFee;
									Ext.getCmp('cpFee').setValue(cpProFee);
									Ext.getCmp('cpRate').setValue(cpProRate);
									Ext.getCmp('showMsg').getEl().update('<span style="color:red">已找到项目客户协议价!</span>');
								}
							}else{
								var cpRate = respText.resultMap[0].cpRate;
								var cpFee = respText.resultMap[0].cpFee;
								if(cpFee == null || cpFee == ''){
									var stRate = respText.resultMap[0].stRate;
									var stFee = respText.resultMap[0].stFee;
									if(stFee == null || stFee == ''){
										Ext.getCmp('cpFee').setValue(0);
										Ext.getCmp('cpRate').setValue(0);
										Ext.Msg.alert(alertTitle,'该代理无协议价，并且无公布价，请维护协议价后再次尝试!');
										Ext.getCmp('showMsg').getEl().update('<span style="color:red">运输方式为:'+trafficMode+',提货方式为:'+takeMode+',配送方式为:'+distributeMode+',地址类型为:'+addrType+',始发站为:'+startCity+'的公布价不存在！</span>');
										return;
									}else{
										
										cashCpFee = stFee;
										cashPrice = stFee;
										Ext.getCmp('cpFee').setValue(stFee);
										Ext.getCmp('cpRate').setValue(stRate);
										Ext.getCmp('showMsg').getEl().update('<span style="color:red">该代理无协议价,取公布价!</span>');
									}
								}else{
									cashCpFee = cpFee;
									cashPrice = cpFee;
									Ext.getCmp('cpFee').setValue(cpFee);
									Ext.getCmp('cpRate').setValue(cpRate);
									Ext.getCmp('showMsg').getEl().update('<span style="color:red">已找到代理协议价!</span>');
								}
							}
						}
					});
					
					Ext.getCmp('consigneeFee').setValue(0);
					Ext.getCmp('consigneeRate').setValue(0);
					
					Ext.getCmp('normCpRate').setValue(respText.resultMap[0].stRate);
				}else{
					//到付
					var conRate = respText.resultMap[0].conRate;
					var conFee = respText.resultMap[0].conFee;
					if(conFee == null || conFee == ''){
						var stRate = respText.resultMap[0].stRate;
						var stFee = respText.resultMap[0].stFee;
						if(stFee == null || stFee == ''){
							Ext.getCmp('consigneeFee').setValue(0);
							Ext.getCmp('consigneeRate').setValue(0);
							Ext.Msg.alert(alertTitle,'该收货人无协议价，并且无公布价，请维护协议价后再次尝试!');
							Ext.getCmp('showMsg').getEl().update('<span style="color:red">运输方式为:'+trafficMode+',提货方式为:'+takeMode+',配送方式为:'+distributeMode+',地址类型为:'+addrType+',始发站为:'+startCity+'的公布价不存在！</span>');
							return;
						}else{
							cashConsigneeFee = stFee;
							Ext.getCmp('consigneeFee').setValue(stFee);
							Ext.getCmp('consigneeRate').setValue(stRate);
							Ext.getCmp('showMsg').getEl().update('<span style="color:red">该收货无协议价,取公布价!</span>');
						}
					}else{
						cashConsigneeFee = conFee;
						Ext.getCmp('consigneeFee').setValue(conFee);
						Ext.getCmp('consigneeRate').setValue(conRate);
						Ext.getCmp('showMsg').getEl().update('<span style="color:red">已找到收货人协议价!</span>');
					}
					
					Ext.getCmp('cpFee').setValue(0);
					Ext.getCmp('cpRate').setValue(0);
					
					Ext.getCmp('normConsigneeRate').setValue(respText.resultMap[0].stRate);
				}
				
				
				if(distributeMode =='中转'){
					if(whoCash =='预付'){
						Ext.Ajax.request({
							url : sysPath + "/sys/customerAction!list.action",
							params : {
								filter_EQL_id:cusId,
								filter_EQL_isProjectcustomer:1
							},
							success : function(res) {
								var respText1 = Ext.util.JSON.decode(res.responseText);
								if(respText1.result.length>0){
									//项目客户
									var traProRate = respText.resultMap[0].traProRate;
									var traProFee = respText.resultMap[0].traProFee;
									
									if(traProFee == null || traProFee == ''){
										var traRate = respText.resultMap[0].traRate;
										var traFee = respText.resultMap[0].traFee;
										if(traFee ==null || traFee == ''){
											Ext.Msg.alert(alertTitle,'<span style="color:red">供应商:'+cusName+',运输方式:'+trafficMode+',<br/>提货方式:'+takeMode+',地址类型:'+addrType+',部门:'+disdepartName+'的中转协议价格不存在！</span>');
											Ext.getCmp('showMsg').getEl().update('<span style="color:red">供应商:'+cusName+',运输方式:'+trafficMode+',提货方式:'+takeMode+',地址类型:'+addrType+',部门:'+disdepartName+'的中转协议价格不存在！</span>');
											return false;
										}else{
											cashTraFee = traProFee;
											Ext.getCmp('traFeeRate').setValue(traRate);
											Ext.getCmp('traFee').setValue(traFee);
										}
									}else{
										cashTraFee = traFee;
										Ext.getCmp('traFeeRate').setValue(traProRate);
										Ext.getCmp('traFee').setValue(traProFee);
										Ext.getCmp('showMsg').getEl().update('<span style="color:red">已找到项目客户中转协议价!</span>');
									}
								}else{
									var traRate = respText.resultMap[0].traRate;
									var traFee = respText.resultMap[0].traFee;
									if(traFee ==null || traFee == ''){
										Ext.Msg.alert(alertTitle,'<span style="color:red">供应商:'+cusName+',运输方式:'+trafficMode+',<br/>提货方式:'+takeMode+',地址类型:'+addrType+',部门:'+disdepartName+'的中转协议价格不存在！</span>');
										Ext.getCmp('showMsg').getEl().update('<span style="color:red">供应商:'+cusName+',运输方式:'+trafficMode+',提货方式:'+takeMode+',地址类型:'+addrType+',部门:'+disdepartName+'的中转协议价格不存在！</span>');
										return false;
									}else{
										cashTraFee = traFee;
										Ext.getCmp('traFeeRate').setValue(traRate);
										Ext.getCmp('traFee').setValue(traFee);
									}
								}
							}
						});
					}else{
						var traRate = respText.resultMap[0].traRate;
						var traFee = respText.resultMap[0].traFee;
						if(traFee ==null || traFee == ''){
							Ext.getCmp('traFeeRate').setValue(0);
							Ext.getCmp('traFee').setValue(0);
							Ext.Msg.alert(alertTitle,'<span style="color:red">供应商:'+cusName+',运输方式:'+trafficMode+',<br/>提货方式:'+takeMode+',地址类型:'+addrType+',部门:'+disdepartName+'的中转协议价格不存在！</span>');
							Ext.getCmp('showMsg').getEl().update('<span style="color:red">供应商:'+cusName+',运输方式:'+trafficMode+',提货方式:'+takeMode+',地址类型:'+addrType+',部门:'+disdepartName+'的中转协议价格不存在！</span>');
							return false;
						}else{
							cashTraFee = traFee;
							Ext.getCmp('traFeeRate').setValue(traRate);
							Ext.getCmp('traFee').setValue(traFee);
						}
					}
				}else{
					Ext.getCmp('traFeeRate').setValue(0);
					Ext.getCmp('traFee').setValue(0);
				}
				Ext.getCmp('normTraRate').setValue(respText.resultMap[0].traRate);
			}else{
				Ext.Msg.alert(alertTitle,'价格查询错误，请联系系统管理员!');
			}
		}
	});
	/*
	if (distributeMode == "中转") {
		Ext.Ajax.request({
			url : sysPath + "/sys/customerAction!ralaList.action",
			params : {
				privilege : 61,
				filter_EQL_id:cusId,
				filter_EQL_isProjectcustomer:1
			},
			success : function(res) {
				var resText = Ext.util.JSON.decode(res.responseText);
				// 不是项目客户
				if (resText.result.length < 1) {
					findTraFee(cusName,trafficMode,takeMode,addrType,disDepartId,valuationType,cqWeight);
				}
			}
		});
	}else{     
		Ext.getCmp('traFeeRate').setValue(0);
		Ext.getCmp('traFee').setValue(0);
		Ext.getCmp('normTraRate').setValue(0);
	}
	*/
	/*
	function findNormpubprice(type){
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
								Ext.getCmp('cpRate').setValue(0);
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
	}*/
	/**
	 * 查询收货人标准价格
	 * @param {} type
	 */
	/*
	function findNormconprice(type){
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
								Ext.getCmp('consigneeRate').setValue(0);
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
	}*/
	/**
	 * 设置代理公布价
	 * @param {} respText1
	 * @param {} cqWeight
	 * @param {} type
	 */
	/*
	function setCpStFee(respText1, cqWeight,type) {
		if (cqWeight >= 0 && cqWeight < 500) {
			if(type=='st'){
				Ext.getCmp('cpRate').setValue(Number(respText1.result[0].stage1Rate));
				Ext.getCmp('normCpRate').setValue(Number(respText1.result[0].stage1Rate));
				var price = Math.round(Number(respText1.result[0].stage1Rate)* cqWeight);
				var minPrice = respText1.result[0].lowPrice;
				if (price < minPrice) {
					Ext.getCmp('cpFee').setValue(minPrice);
					cashPrice=minPrice;
				} else {
					Ext.getCmp('cpFee').setValue(price);
					cashPrice=price;
				}
				Ext.getCmp('showMsg').getEl().update('<span style="color:red">该代理无协议价，已找到公布价，只能按重量计费！价格编号:'+respText1.result[0].id+'</span>');
			}else{
				Ext.getCmp('normCpRate').setValue(Number(respText1.result[0].stage1Rate));
			}
		} else if (cqWeight >= 500 && cqWeight < 1000) {
			if(type=='st'){
				Ext.getCmp('cpRate').setValue(Number(respText1.result[0].stage2Rate));
				Ext.getCmp('normCpRate').setValue(Number(respText1.result[0].stage2Rate));
				var price = Math
					.round(Number(respText1.result[0].stage2Rate)
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
			}else{
				Ext.getCmp('normCpRate').setValue(Number(respText1.result[0].stage2Rate));
			}
		} else {
			if(type=='st'){
				Ext.getCmp('cpRate').setValue(Number(respText1.result[0].stage3Rate));
				Ext.getCmp('normCpRate').setValue(Number(respText1.result[0].stage3Rate));
				var price = Math.round(Number(respText1.result[0].stage3Rate)* cqWeight);
				var minPrice = respText1.result[0].lowPrice;
				if (price < minPrice) {
					Ext.getCmp('cpFee').setValue(minPrice);
					cashPrice=minPrice;
				} else {
					Ext.getCmp('cpFee').setValue(price);
					cashPrice=price;
				}
				Ext.getCmp('showMsg').getEl().update('<span style="color:red">该代理无协议价，已找到公布价，只能按重量计费！价格编号:'+respText1.result[0].id+'</span>');
			}else{
				Ext.getCmp('normCpRate').setValue(Number(respText1.result[0].stage3Rate));
			}
		}
	}*/
	/**
	 * 设置代理协议价
	 * @param {} respText
	 * @param {} valuationType
	 * @param {} cqWeight
	 */
	/*
	function setCqFee(respText, valuationType, cqWeight) {
	if (valuationType == '重量') {
		if (cqWeight > 0 && cqWeight < 500) {
			Ext
					.getCmp('cpRate')
					.setValue(Number(respText.result[0].stage1Rate));
			var price = Math
					.round(Number(respText.result[0].stage1Rate)
							* cqWeight);
			var minPrice = respText.result[0].lowPrice;
			if (price < minPrice) {
				Ext.getCmp('cpFee').setValue(minPrice);
			} else {
				Ext.getCmp('cpFee').setValue(price);
			}
		} else if (cqWeight >= 500 && cqWeight < 1000) {
			Ext
					.getCmp('cpRate')
					.setValue(Number(respText.result[0].stage2Rate));
			var price = Math
					.round(Number(respText.result[0].stage2Rate)
							* cqWeight);
			var minPrice = respText.result[0].lowPrice;
			if (price < minPrice) {
				Ext.getCmp('cpFee').setValue(minPrice);
			} else {
				Ext.getCmp('cpFee').setValue(price);
			}
		} else {
			Ext
					.getCmp('cpRate')
					.setValue(Number(respText.result[0].stage3Rate));
			var price = Math
					.round(Number(respText.result[0].stage3Rate)
							* cqWeight);
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
		Ext.getCmp('cpRate').setValue(Number(respText.result[0].stage1Rate));
		var price=Math.round(Number(respText.result[0].stage1Rate)* Number(Ext.getCmp('bulk').getValue()));
		var minPrice=respText.result[0].lowPrice;
		if(price<minPrice){
			Ext.getCmp('cpFee').setValue(minPrice);
			Ext.getCmp('showMsg').getEl().update('<span style="color:red">已找到代理协议价，按体积计费,取最低一票！价格编号:'+respText.result[0].id+'</span>');
		}else{
			Ext.getCmp('cpFee').setValue(price);
			Ext.getCmp('showMsg').getEl().update('<span style="color:red">已找到代理协议价，按体积计费！价格编号:'+respText.result[0].id+'</span>');
		}
		
	} else if (valuationType == '件数') {
		Ext.getCmp('cpRate').setValue(Number(respText.result[0].stage1Rate));
		var price=Math.round(Number(respText.result[0].stage1Rate)* Number(Ext.getCmp('piece').getValue()));
		var minPrice=respText.result[0].lowPrice;
		if(price<minPrice){
			Ext.getCmp('cpFee').setValue(minPrice);
			Ext.getCmp('showMsg').getEl().update('<span style="color:red">已找到代理协议价，按件数计费,取最低一票！价格编号:'+respText.result[0].id+'</span>');
		}else{
			Ext.getCmp('cpFee').setValue(price);
			Ext.getCmp('showMsg').getEl().update('<span style="color:red">已找到代理协议价，按件数计费！价格编号:'+respText.result[0].id+'</span>');
		}
		
	}
}*/
	//设置收货人协议价
	/*
	function setConStFee(respText1,cqWeight,type){
		if (cqWeight >= 0 && cqWeight < 500) {
			if(type=='st'){
				Ext.getCmp('consigneeRate').setValue(Number(respText1.result[0].stage1Rate));
				Ext.getCmp('normConsigneeRate').setValue(Number(respText1.result[0].stage1Rate));
				var price = Math.round(Number(respText1.result[0].stage1Rate)* cqWeight);
				var minPrice = respText1.result[0].lowPrice;
				if (price < minPrice) {
					Ext.getCmp('consigneeFee')
							.setValue(minPrice);
				} else {
					Ext.getCmp('consigneeFee').setValue(price);
				}
				Ext.getCmp('showMsg').getEl().update('<span style="color:red">该收货人无协议价，已找到公布价！价格编号：'+respText1.result[0].id+'</span>');
			}else{
				Ext.getCmp('consigneeRate').setValue(Number(respText1.result[0].stage1Rate));
			}
			
		} else if (cqWeight >= 500 && cqWeight < 1000) {
			if(type=='st'){
				Ext.getCmp('consigneeRate').setValue(Number(respText1.result[0].stage2Rate));
				Ext.getCmp('normConsigneeRate').setValue(Number(respText1.result[0].stage2Rate));
				var price = Math
					.round(Number(respText1.result[0].stage2Rate)
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
				Ext.getCmp('consigneeRate').setValue(Number(respText1.result[0].stage2Rate));
			}
		} else {
			if(type=='st'){
				Ext.getCmp('consigneeRate').setValue(Number(respText1.result[0].stage3Rate));
				Ext.getCmp('normConsigneeRate').setValue(Number(respText1.result[0].stage3Rate));
				var price = Math.round(Number(respText1.result[0].stage3Rate)* cqWeight);
				var minPrice = respText1.result[0].lowPrice;
				if (price < minPrice) {
					Ext.getCmp('consigneeFee')
							.setValue(minPrice);
				} else {
					Ext.getCmp('consigneeFee').setValue(price);
				}
				Ext.getCmp('showMsg').getEl().update('<span style="color:red">该收货人无协议价，已找到公布价！价格编号：'+respText1.result[0].id+'</span>');
			}else{
				Ext.getCmp('consigneeRate').setValue(Number(respText1.result[0].stage3Rate));
			}
		}
	}
	*/
	// 付款方为预付
	/*
	if (textfield.getValue() == '预付'||textfield.getValue()=='双方付') {
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
									bulk:bulk
								},
								success:function(respp){
									var resppText = Ext.util.JSON.decode(respp.responseText);
									if(resppText.resultMap.length<1){
										Ext.getCmp('cpRate').setValue(0);
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
										}else if(valuaType=='重量'){
											pprice=cqWeight*prate;
										}
										else if(valuaType=='体积'){
											pprice=Number(Ext.getCmp('bulk').getValue())*prate;
										}
										if(pprice<plowFee){
											pprice=plowFee;
										}
										pprice = Math.round(pprice);
										cashPrice=pprice;
										Ext.getCmp('chargemodecombo').setValue(valuaType);
										Ext.getCmp('cpRate').setValue(prate);
										Ext.getCmp('cpFee').setValue(pprice);
										Ext.getCmp('showMsg').getEl().update('<span style="color:red">此代理为项目客户，已找到项目客户协议价！价格编号:'+resppText.resultMap[0].ID+'</span>');
										if(distributeMode == "中转"){
											findProjectTraFee(cusId,cqWeight,cusName,takeMode,addrType,trafficMode,disdepartId,piece,bulk,goWhereId,valuationType);
										}
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
													findNormpubprice('st');
												} else {
													setCqFee(respText1, valuationType, cqWeight);
													findNormpubprice('cq');
												}
											}
										});
										// 有协议价格
									} else {
										setCqFee(respText, valuationType, cqWeight);
										findNormpubprice('cq');
									}
								}
							});
							
						}
					}
				});
		Ext.getCmp('consigneeRate').setValue(0);
		Ext.getCmp('consigneeFee').setValue(0);
		// 付款方为到付
	} else if (textfield.getValue() == '到付') {
		var tel = Ext.getCmp('consigneeTel').getValue();
		var name = Ext.getCmp('consignee').getValue();
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
					findNormconprice('st');
				} else {
					findNormconprice('cq');
					if (takeMode == "机场自提") {
						var flyOwnPrice = respText.result[0].flyOwnPrice;
						var minPrice = respText.result[0].flyOwnMinPrice;
						var price = Math.round(Number(flyOwnPrice) * cqWeight);
						Ext.getCmp('consigneeRate').setValue(flyOwnPrice);
						if (price < minPrice) {
							Ext.getCmp('consigneeFee').setValue(minPrice);
						} else {
							Ext.getCmp('consigneeFee').setValue(price);
						}
					} else if (takeMode == "市内自提") {
						var cityOwnPrice = respText.result[0].cityOwnPrice;
						var price = Math.round(Number(cityOwnPrice) * cqWeight);
						var minPrice = respText.result[0].cityOwnMinPrice;
						Ext.getCmp('consigneeRate').setValue(cityOwnPrice);
						if (price < minPrice) {
							Ext.getCmp('consigneeFee').setValue(minPrice);
						} else {
							Ext.getCmp('consigneeFee').setValue(price);
						}
					} else if (takeMode == "市内送货") {
						var citySendPrice = respText.result[0].citySendPrice;
						var price = Math
								.round(Number(citySendPrice) * cqWeight);
						var minPrice = respText.result[0].citySendMinPrice;
						Ext.getCmp('consigneeRate').setValue(citySendPrice);
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
		Ext.getCmp('cpRate').setValue(0);
		Ext.getCmp('cpFee').setValue(0);
		//双方付
	}*/
	

}
//查询项目客户中转协议价
/*
function findProjectTraFee(cusId,cqWeight,cusName,takeMode,addrType,trafficMode,disdepartId,piece,bulk,goWhereId,valuationType){
	Ext.Ajax.request({
		url : sysPath + "/fi/basProjCusTransitRateAction!findProTraRate.action",
		params : {
			limit : pageSize,
			cusId :cusId,
			cpId:goWhereId,
			takeMode:takeMode,
			trafficMode:trafficMode,
			areaType:addrType,
			disDepartId:disdepartId,
			weight:cqWeight,
			bulk:bulk,
			piece:piece
		},
		success : function(resp) {
			var respText = Ext.util.JSON.decode(resp.responseText);
			if (respText.resultMap.length < 1) {
				//该项目客户针对该供应商没有协议价
				Ext.Ajax.request({
					url : sysPath + "/fi/basProjCusTransitRateAction!findProTraRate.action",
					params : {
						limit : pageSize,
						cusId :cusId,
						takeMode:takeMode,
						trafficMode:trafficMode,
						areaType:addrType,
						disDepartId:disdepartId,
						weight:cqWeight,
						bulk:bulk,
						piece:piece
					},success:function(resp1){
						var respText1 = Ext.util.JSON.decode(resp1.responseText);
						if(respText1.resultMap.length<1){
							//此项目客户没有中转协议价，取标准协议价
							findTraFee(cusName,trafficMode,takeMode,addrType,disdepartId,valuationType,cqWeight);
						}else{
							setProTraRate(respText1,cqWeight);
						}
					}
				});
			} else {
				setProTraRate(respText,cqWeight);
			}
		}
  });
}*/
//设置项目客户中转协议价
/*
function setProTraRate(respText,cqWeight){
	var valuaType=respText.resultMap[0].VALUATION_TYPE;
	var prate=Number(respText.resultMap[0].RATE);
	var plowFee=Number(respText.resultMap[0].LOW_FEE);
	var pprice=0;
	if(valuaType=='件数'){
		pprice=Number(Ext.getCmp('piece').getValue())*prate;
	}else if(valuaType== '重量'){
		pprice=cqWeight*prate;
	}
	else if(valuaType=='体积'){
		pprice=Number(Ext.getCmp('bulk').getValue())*prate;
	}
	if(pprice<plowFee){
		pprice=plowFee;
	}
	pprice = Math.round(pprice);
	//Ext.getCmp('chargemodecombo').setValue(valuaType);
	Ext.getCmp('traFeeRate').setValue(prate);
	Ext.getCmp('traFee').setValue(pprice);
	Ext.getCmp('showMsg').getEl().update('<span style="color:red">此代理为项目客户，已找到项目客户中转协议价！价格编号:'+respText.resultMap[0].ID+'</span>');
}
*/
//查询中转协议价
/*
function findTraFee(cusName,trafficMode,takeMode,addrType,disdepartId,valuationType,cqWeight){
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
			filter_EQL_departId : disdepartId,
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
				setTraFeeRate(respText,cqWeight);
			}
		}
	});
}
*/
//设置中转协议价
/*
function setTraFeeRate(respText,cqWeight){
	var valuationType1=respText.result[0].valuationType;
	if(valuationType1=='重量'){
		if (cqWeight >= 0 && cqWeight < 500) {
			
			Ext
					.getCmp('traFeeRate')
					.setValue(Number(respText.result[0].stage1Rate));
			Ext
					.getCmp('normTraRate')
					.setValue(Number(respText.result[0].stage1Rate));
			var price = Math
					.round(Number(respText.result[0].stage1Rate)
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
			Ext
					.getCmp('normTraRate')
					.setValue(Number(respText.result[0].stage2Rate));
			var price = Math
					.round(Number(respText.result[0].stage2Rate)
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
					Ext
					.getCmp('normTraRate')
					.setValue(Number(respText.result[0].stage3Rate));
			var price = Math
					.round(Number(respText.result[0].stage3Rate)
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
		Ext.getCmp('traFeeRate').setValue(Number(respText.result[0].stage1Rate));
		var price=Math.round(Number(respText.result[0].stage1Rate)* Number(Ext.getCmp('bulk').getValue()));
		var minPrice=respText.result[0].lowPrice;
		if(price<minPrice){
			Ext.getCmp('traFee').setValue(minPrice);
			Ext.getCmp('showMsg').getEl().update('<span style="color:red">已找到中转协议价，按体积计费,取最低一票！价格编号:'+respText.result[0].id+'</span>');
		}else{
			Ext.getCmp('traFee').setValue(price);
			Ext.getCmp('showMsg').getEl().update('<span style="color:red">已找到中转协议价，按体积计费！价格编号:'+respText.result[0].id+'</span>');
		}
							
	} else if (valuationType1 == '件数') {
		Ext.getCmp('traFeeRate').setValue(Number(respText.result[0].stage1Rate));
		var price=Math.round(Number(respText.result[0].stage1Rate)* Number(Ext.getCmp('piece').getValue()));
		var minPrice=respText.result[0].lowPrice;
		if(price<minPrice){
			Ext.getCmp('traFee').setValue(minPrice);
			Ext.getCmp('showMsg').getEl().update('<span style="color:red">已找到中转协议价，按件数计费,取最低一票！价格编号:'+respText.result[0].id+'</span>');
		}else{
			Ext.getCmp('traFee').setValue(price);
			Ext.getCmp('showMsg').getEl().update('<span style="color:red">已找到中转协议价，按件数计费！价格编号:'+respText.result[0].id+'</span>');
		}
							
	}
}*/
/**
 * 设置专车价格
 * @param {} respText1
 */
//设置专车协议价
function setSpecialRate(respText1){
	var who=Ext.getCmp('payerCombo').getValue();
	var sondId='';
	if(who=='预付'||who=='双方付'){
		sondId='cpSonderzugPrice';
	}else if(who=='到付'){
		sondId='sonderzugPrice';
	}
	var carType=Ext.getCmp('cartypecombo').getValue();
	if(carType=="金杯车"){
		totalSonPrice=respText1.resultMap[0].GOLD_CUP_CAR;
		Ext.getCmp(sondId).setValue(respText1.resultMap[0].GOLD_CUP_CAR);
	}else if(carType=="面包车"){
		totalSonPrice=respText1.resultMap[0].VAN;
		Ext.getCmp(sondId).setValue(respText1.resultMap[0].VAN);
	}else if(carType=="冷藏车"){
		totalSonPrice=respText1.resultMap[0].CHILL_CAR;
		Ext.getCmp(sondId).setValue(respText1.resultMap[0].CHILL_CAR);
	}else if(carType=="2吨车"){
		totalSonPrice=respText1.resultMap[0].TWO_TON_CAR;
		Ext.getCmp(sondId).setValue(respText1.resultMap[0].TWO_TON_CAR);
	}else if(carType=="3吨车"){
		totalSonPrice=respText1.resultMap[0].THREE_TON_CAR;
		Ext.getCmp(sondId).setValue(respText1.resultMap[0].THREE_TON_CAR);
	}else if(carType=="5吨车"){
		totalSonPrice=respText1.resultMap[0].FIVE_TON_CAR;
		Ext.getCmp(sondId).setValue(respText1.resultMap[0].FIVE_TON_CAR);
	}
	
}
//设置标准专车价格
function setNormSpecialRate(respText1){
	var carType=Ext.getCmp('cartypecombo').getValue();
	if(respText1.resultMap.length>1){
		if(carType=="金杯车"){
		Ext.getCmp('normSonderzugPrice').setValue(respText1.resultMap[0].GOLD_CUP_CAR);
		}else if(carType=="面包车"){
			Ext.getCmp('normSonderzugPrice').setValue(respText1.resultMap[0].VAN);
		}else if(carType=="冷藏车"){
			Ext.getCmp('normSonderzugPrice').setValue(respText1.resultMap[0].CHILL_CAR);
		}else if(carType=="2吨车"){
			Ext.getCmp('normSonderzugPrice').setValue(respText1.resultMap[0].TWO_TON_CAR);
		}else if(carType=="3吨车"){
			Ext.getCmp('normSonderzugPrice').setValue(respText1.resultMap[0].THREE_TON_CAR);
		}else if(carType=="5吨车"){
			Ext.getCmp('normSonderzugPrice').setValue(respText1.resultMap[0].FIVE_TON_CAR);
		}
	}else{
		if(carType=="金杯车"){
		Ext.getCmp('normSonderzugPrice').setValue(respText1.resultMap[0].GOLD_CUP_CAR);
		}else if(carType=="面包车"){
			Ext.getCmp('normSonderzugPrice').setValue(respText1.resultMap[0].VAN);
		}else if(carType=="冷藏车"){
			Ext.getCmp('normSonderzugPrice').setValue(respText1.resultMap[0].CHILL_CAR);
		}else if(carType=="2吨车"){
			Ext.getCmp('normSonderzugPrice').setValue(respText1.resultMap[0].TWO_TON_CAR);
		}else if(carType=="3吨车"){
			Ext.getCmp('normSonderzugPrice').setValue(respText1.resultMap[0].THREE_TON_CAR);
		}else if(carType=="5吨车"){
			Ext.getCmp('normSonderzugPrice').setValue(respText1.resultMap[0].FIVE_TON_CAR);
		}
	}
	
}
//删除修改项
function delUpdateMsgStore(){
	Ext.Msg.confirm(alertTitle, "确定要删除吗？", function(btnYes) {
	    if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
	    	//var updateMsgStore=Ext.StoreMgr.get('updateMsgStore');
	    	//var record=updateMsgStore.getAt(rowIndex);
	    	var record=updateMsgRecord.getSelectionModel().getSelected();
	    	var field=record.data.changeField;
	    	if(field=='trafficMode'){
	    		Ext.getCmp('trafficModecombo').setValue(record.data.changePre);
	    	}else if(field=='cusId'){
	    		Ext.getCmp("cuscombo").setValue(record.data.changePre);
				//Ext.getCmp("cuscombo").setRawValue(updateMsgStore.getAt(rowIndex+1).data.changePre);
				//updateMsgStore.removeAt(rowIndex);
				//updateMsgStore.removeAt(rowIndex);
				return;
	    	}else if(field=='cpName'){
	    		//Ext.getCmp("cuscombo").setValue(updateMsgStore.getAt(rowIndex-1).data.changePre)
				Ext.getCmp("cuscombo").setRawValue(record.data.changePre);
				//updateMsgStore.removeAt(rowIndex-1);
				//updateMsgStore.removeAt(rowIndex-1);
				return;
	    	}else if(field=='flightNo'){
	    		Ext.getCmp('flightcombo').setValue(record.data.changePre);
	    	}else if(field=='flightDate'){
	    		Ext.getCmp('arrivetime').setValue(record.data.changePre);
	    	}else if(field=='sonderzug'){
	    		if(record.data.changePre=='是'){
	    			Ext.getCmp('sonderzug').setValue(true);
	    			Ext.getCmp('sonderzugVal').setValue(1);
	    		}else{
	    			Ext.getCmp('sonderzug').setValue(false);
	    			Ext.getCmp('sonderzugVal').setValue(0);
	    		}
	    	}
	    	else if(field=='wait'){
	    		if(record.data.changePre=='是'){
	    			Ext.getCmp('wait').setValue(true);
	    			Ext.getCmp('waitVal').setValue(1);
	    		}else{
	    			Ext.getCmp('wait').setValue(false);
	    			Ext.getCmp('waitVal').setValue(0);
	    		}
	    	}else if(field=='urgentService'){
	    		if(record.data.changePre=='是'){
	    			Ext.getCmp('urgentService').setValue(true);
	    			Ext.getCmp('urgentServiceVal').setValue(1);
	    		}else{
	    			Ext.getCmp('urgentService').setValue(false);
	    			Ext.getCmp('urgentServiceVal').setValue(0);
	    		}
	    	}else if(field=='carType'){
	    		Ext.getCmp('cartypecombo').setValue(record.data.changePre);
	    	}else if(field=='roadType'){
	    		Ext.getCmp('roadtypecombo').setValue(record.data.changePre);
	    	}else if(field=='takeMode'){
	    		Ext.getCmp('takeModecombo').setValue(record.data.changePre);
	    	}else if(field=='receiptType'){
	    		Ext.getCmp('receiptTypecombo').setValue(record.data.changePre);
	    	}else if(field=='valuationType'){
	    		Ext.getCmp('chargemodecombo').setValue(record.data.changePre);
	    	}else if(field=='whoCash'){
	    		Ext.getCmp('payerCombo').setValue(record.data.changePre);
	    	}else if(field=='cpValueAddFee'||field=='cusValueAddFee'){
	    		Ext.Msg.alert(alertTitle,'增值服务费修改后不能删除，请到增值服务费明细进行手动修改！',onfocus);
	    		function onfocus(){
	    			return false;
	    		}
	    	}else if(field=='goWhere'){
	    		Ext.getCmp('goWhere').setValue(record.data.changePre);
	    		Ext.getCmp('goWhereIdcombo').setRawValue(record.data.changePre);
	    	}else if(field=='goWhereId'){
	    		Ext.getCmp('goWhereIdcombo').setValue(record.data.changePre);
	    	}
	    	else{
	    		Ext.getCmp(record.data.changeField).setValue(record.data.changePre);
	    	}
	    	//updateMsgStore.removeAt(rowIndex);
	    	if(field!='cpValueAddFee'&&field!='cusValueAddFee'){
	    		updateMsgStore.remove(record);
	    	}
		}
	});
}

function setMsg(record){
	d_no=record.data.dno;
	updateMsgStore.removeAll();
	feeStore.removeAll();
	addFeeRecordStore.load({
		params:{
			filter_EQL_dno:d_no
		}
	});
	var store=new Ext.data.Record.create([{name:'cpValueAddFee'},{name:'cusValueAddFee'},{name:'sonderzugPrice'},{name:'cpSonderzugPrice'},{name:'paymentCollection'},{name:'traFee'},{name:'cpFee'},{name:'consigneeFee'}]);
    var noFaxRecord=new store();
    noFaxRecord.set("sonderzugPrice",record.data.sonderzugPrice);
    noFaxRecord.set("cpSonderzugPrice",record.data.cpSonderzugPrice);
    noFaxRecord.set("paymentCollection",record.data.paymentCollection);
	noFaxRecord.set("traFee",record.data.traFee);
	noFaxRecord.set("cpValueAddFee",record.data.cpValueAddFee);
	noFaxRecord.set("cusValueAddFee",record.data.cusValueAddFee);
	noFaxRecord.set("cpFee",record.data.cpFee);
	noFaxRecord.set("consigneeFee",record.data.consigneeFee);
	feeStore.add(noFaxRecord);
	Ext.getCmp('dno').setValue(record.data.dno);
	Ext.getCmp('trafficModecombo').setValue(record.data.trafficMode);
	var fieldNo= Ext.getCmp('flightcombo').getEl().parent().parent().parent().first();
	if(record.data.trafficMode=='空运'){
		  fieldNo.dom.innerHTML='班次号<span style="color:red">*</span>:';
		  Ext.getCmp('flightcombo').allowBlank=false;
	  }else if(record.data.trafficMode=='汽运'){
	    fieldNo.dom.innerHTML='班次号:';
		Ext.getCmp('flightcombo').allowBlank=true;
	  }else if(record.data.trafficMode=='铁路运输'){
		  fieldNo.dom.innerHTML='班次号:';
		  Ext.getCmp('flightcombo').allowBlank=true;
	  }
	Ext.getCmp("cuscombo").setValue(record.data.cusId);
	Ext.getCmp("cuscombo").setRawValue(record.data.cpName);
	Ext.getCmp('flightMain').setValue(record.data.flightMainNo);
	Ext.getCmp('flightcombo').setValue(record.data.flightNo);
	//设置航班落地城市
	Ext.Ajax.request( {
		url : sysPath + "/sys/basFlightAction!ralaList.action",
		params : {
				filter_EQS_flightNumber:record.data.flightNo,
				privilege:62
		},
		success : function(resp) {
			var respText = Ext.util.JSON.decode(resp.responseText);
			Ext.getCmp('endCity').setValue(respText.result[0].endCity);
		}
	});
	Ext.getCmp('areaType').setValue(record.data.areaType);
	Ext.getCmp('arrivetime').setValue(record.data.flightDate);
	if(record.data.sonderzug==1){
		Ext.getCmp('sonderzug').setValue(true);
	}else{
		Ext.getCmp('sonderzug').setValue(false);
	}
	if(record.data.wait==1){
		Ext.getCmp('wait').setValue(true);
		Ext.getCmp('waitVal').setValue(1);
	}else{
		Ext.getCmp('wait').setValue(false);
		Ext.getCmp('waitVal').setValue(0);
	}
	if(record.data.urgentService==1){
		Ext.getCmp('urgentService').setValue(true);
		Ext.getCmp('urgentServiceVal').setValue(1);
	}else{
		Ext.getCmp('urgentService').setValue(false);
		Ext.getCmp('urgentServiceVal').setValue(0);
	}
	Ext.getCmp('cartypecombo').setValue(record.data.carType);
	Ext.getCmp('roadtypecombo').setValue(record.data.roadType);
	Ext.getCmp('sonderzugPrice').setValue(record.data.sonderzugPrice);
	Ext.getCmp('cpSonderzugPrice').setValue(record.data.cpSonderzugPrice);
	
	Ext.getCmp('subNo').setValue(record.data.subNo);
	Ext.getCmp('takeModecombo').setValue(record.data.takeMode);
	Ext.getCmp('receiptTypecombo').setValue(record.data.receiptType);
	Ext.getCmp('distributionMode').setValue(record.data.distributionMode);
	Ext.getCmp('consigneeTel').setValue(record.data.consigneeTel);
	Ext.getCmp('consignee').setValue(record.data.consignee);
	Ext.getCmp('city').setValue(record.data.city);
	Ext.getCmp('town').setValue(record.data.town);
	Ext.getCmp('street').setValue(record.data.street);
	Ext.getCmp('consigneeAddress').setValue(record.data.addr);
	Ext.getCmp('goWhere').setValue(record.data.goWhere);
	Ext.getCmp('goWhereIdcombo').setValue(record.data.goWhereId);

	Ext.getCmp('goWhereIdcombo').setRawValue(record.data.goWhere);
	Ext.getCmp('goods').setValue(record.data.goods);
	Ext.getCmp('chargemodecombo').setValue(record.data.valuationType);
	Ext.getCmp('piece').setValue(record.data.piece );
	Ext.getCmp('bulk').setValue(record.data.bulk);
	Ext.getCmp('cusWeight').setValue(record.data.cusWeight);
	Ext.getCmp('cqWeight').setValue(record.data.cqWeight);
	Ext.getCmp('payerCombo').setValue(record.data.whoCash);
	Ext.getCmp('traFeeRate').setValue(record.data.traFeeRate);
	Ext.getCmp('traFee').setValue(record.data.traFee);
	Ext.getCmp('cpRate').setValue(record.data.cpRate);
	Ext.getCmp('cpFee').setValue(record.data.cpFee);
	Ext.getCmp('paymentCollection').setValue(record.data.paymentCollection);
	Ext.getCmp('consigneeRate').setValue(record.data.consigneeRate);
	Ext.getCmp('consigneeFee').setValue(record.data.consigneeFee);
	Ext.getCmp('endDepartId').setValue(record.data.endDepartId);
	Ext.getCmp('endDepart').setValue(record.data.endDepart);
	Ext.getCmp('remark').setValue(record.data.remark);
	Ext.getCmp('distributionDepartId').setValue(record.data.distributionDepartId);
	Ext.getCmp('distributionDepart').setValue(record.data.distributionDepart);
	Ext.getCmp('normSonderzugPrice').setValue(record.data.normSonderzugPrice);
	Ext.getCmp('normTraRate').setValue(record.data.normTraRate);
	Ext.getCmp('normCpRate').setValue(record.data.normCpRate);
	Ext.getCmp('normConsigneeRate').setValue(record.data.normConsigneeRate);
	Ext.getCmp('cpValueAddFee').setValue(record.data.cpValueAddFee);
	Ext.getCmp('addFeeWhocash').setValue(record.data.whoCash);
	//if(record.data.cpValueAddFee>0){
		Ext.getCmp('totalAddFee').setValue(Number(record.data.cpValueAddFee)+Number(record.data.cusValueAddFee));
	//}
	//if(record.data.cusValueAddFee>0){
		//Ext.getCmp('totalAddFee').setValue(record.data.cusValueAddFee);
	//}
	Ext.getCmp('cusValueAddFee').setValue(record.data.cusValueAddFee);
	
	Ext.Ajax.request({
		url:sysPath+'/stock/oprStatusAction!list.action',
		params:{
			limit:pageSize,
			filter_EQL_dno:record.data.dno
		},success:function(resp){
			var respText = Ext.util.JSON.decode(resp.responseText);
			if(respText.result[0].outStatus==1){
				 Ext.getCmp('sonderzug').setDisabled(true);
				//Ext.getCmp('sonderzugPrice').setDisabled(true);
				//Ext.getCmp('cpSonderzugPrice').setDisabled(true);
				 Ext.getCmp('urgentService').setDisabled(true);
				 Ext.getCmp('wait').setDisabled(true);
				 Ext.getCmp('goWhereIdcombo').setDisabled(true);
				 Ext.getCmp('goWhere').setDisabled(true);
				 Ext.getCmp('receiptTypecombo').setDisabled(true);
				 Ext.getCmp('traFee').setDisabled(false);
				 Ext.getCmp('traFeeRate').setDisabled(false);
				 Ext.getCmp('distributionMode').setDisabled(true);
				 Ext.getCmp('roadtypecombo').setDisabled(true);
				 Ext.getCmp('cartypecombo').setDisabled(true);
				 Ext.getCmp('showMsg').getEl().update('<span style="color:red">此票货物已经出库,部分信息不能更改！</span>');
				 if(record.data.distributionMode=='新邦'){
				 	Ext.getCmp('city').setDisabled(true);
				    Ext.getCmp('town').setDisabled(true);
				    Ext.getCmp('street').setDisabled(true);
				    Ext.getCmp('wait').setDisabled(true);
				 }else{
				 	Ext.getCmp('city').setDisabled(false);
				    Ext.getCmp('town').setDisabled(false);
				    Ext.getCmp('street').setDisabled(false);
				    Ext.getCmp('wait').setDisabled(false);
				 }
				 
				 if(record.data.sonderzug==1){
				 	Ext.getCmp('sonderzugPrice').setDisabled(false);
					Ext.getCmp('cpSonderzugPrice').setDisabled(false);
				 }
				 
				 if(record.data.traFee!=null && record.data.traFee!=0){
				 	Ext.Ajax.request({
				 		url:sysPath+"/fi/fiTransitcostAcion!list.action",
				 		params:{
				 			filter_EQL_dno:record.data.dno
				 		},success:function(resp1){
				 			var respText1 = Ext.util.JSON.decode(resp1.responseText);
				 			if(respText1.result.length>0){
				 				Ext.Msg.alert(alertTitle,'该票货物中转成本已经付款，中转成本不能更改。如需更改，请先撤销付款。');
				 				Ext.getCmp('traFee').setDisabled(true);
								Ext.getCmp('traFeeRate').setDisabled(true);
				 			}else{
				 				Ext.getCmp('traFee').setDisabled(false);
								Ext.getCmp('traFeeRate').setDisabled(false);
				 			}
				 		}
				 	});
				 }
			}else{
				Ext.getCmp('receiptTypecombo').setDisabled(false);
				Ext.getCmp('sonderzug').setDisabled(false);
				Ext.getCmp('goWhereIdcombo').setDisabled(false);
				Ext.getCmp('goWhere').setDisabled(false);
				Ext.getCmp('urgentService').setDisabled(false);
				 Ext.getCmp('wait').setDisabled(false);
				//Ext.getCmp('sonderzugPrice').setDisabled(false);
				//Ext.getCmp('cpSonderzugPrice').setDisabled(false);
				Ext.getCmp('distributionMode').setDisabled(false);
				Ext.getCmp('city').setDisabled(false);
			    Ext.getCmp('town').setDisabled(false);
			    Ext.getCmp('street').setDisabled(false);
				if(record.data.distributionMode=='外发'){
					Ext.getCmp('showMsg').getEl().update('<span style="color:red">此票货物未出库,外发成本不能修改！</span>');
					Ext.getCmp('traFee').setDisabled(true);
					Ext.getCmp('traFeeRate').setDisabled(true);
				}
			}
		}
	});
	Ext.getCmp('showMsg').getEl().update('<span style="color:red">配送单号：'+record.data.dno+'</span>');
}

/**
 * 保存前一点点验证
 * @param {} form
 * @return {Boolean}
 */
function msgValidate(form){
	
	var chargeMode=Ext.getCmp('chargemodecombo').getValue();
	if(Ext.getCmp('distributionMode').getValue()=='中转'){
		if(Ext.getCmp('goWhere').getValue()==''||Ext.getCmp('goWhereIdcombo').getValue()==''){
			Ext.Msg.alert(alertTitle,'中转供应商不能为空！');
			return false;
		}
	}
	
	if(chargeMode=="体积"&&(Ext.getCmp('bulk').getValue()==''||Ext.getCmp('bulk').getValue()==0)){
		Ext.getCmp('bulk').focus(true,true);
		Ext.Msg.alert(alertTitle,"体积不能为空!!");
		return false;
	}
	/*
	if(form.findByType("checkbox")[0].getValue()){
		var sonderPrice=Ext.getCmp('sonderzugPrice').getValue();
		var cpsonderPrice=Ext.getCmp('cpSonderzugPrice').getValue();
		if((sonderPrice==''||sonderPrice==0)&&(cpsonderPrice==''||cpsonderPrice==0)){
			Ext.Msg.alert(alertTitle,'无专车费，无法保存!!');
			return false;
		}
	}
	if(Ext.getCmp('distributionMode').getValue()=='中转'){
		if(Ext.getCmp('traFee').getValue()==''||Ext.getCmp('traFee').getValue()==0||Ext.getCmp('goWhere').getValue()==''||Ext.getCmp('goWhereIdcombo').getValue()==''){
			Ext.Msg.alert(alertTitle,'无中转费，无法保存!!');
			return false;
		}
	}
	if(Ext.getCmp('payerCombo').getValue()=='预付'&&(Ext.getCmp('cpFee').getValue()==''||Ext.getCmp('cpFee').getValue()==0)){
		Ext.Msg.alert(alertTitle,'预付提送费为空,无法保存!');
		return false;
	}
	if(Ext.getCmp('payerCombo').getValue()=='到付'&&(Ext.getCmp('consigneeFee').getValue()==''||Ext.getCmp('consigneeFee').getValue()==0)){
		Ext.Msg.alert(alertTitle,'到付提送费为空,无法保存!');
		return false;
	}
	if(Ext.getCmp('payerCombo').getValue()=='双方付'){
		var cpFee=Number(Ext.getCmp('cpFee').getValue());
		var consigneeFee=Number(Ext.getCmp('consigneeFee').getValue());
		
		var cpSonderPrice=Number(Ext.getCmp('cpSonderzugPrice').getValue());
		var sonderPrice=Number(Ext.getCmp('sonderzugPrice').getValue());
		if((cpFee+consigneeFee)<cashPrice){
			Ext.Msg.alert(alertTitle,'预付提送费+到付提送费不能小于'+cashPrice+'元!');
			return false;
		}
		if (form.findByType("checkbox")[0].getValue()) {
			if(cpSonderPrice+sonderPrice<totalSonPrice){
				Ext.Msg.alert(alertTitle,'预付专车费+到付专车费不能小于'+totalSonPrice+'元!');
				return false;
			}
		}
	}*/
	return true;
}
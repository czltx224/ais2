//弹出异常信心
colWidth=80;
var newDateStore;
 function alertMsg(msg){
 	Ext.Msg.alert(alertTitle,"<font color=red><b>"+msg+"</b></font>");
 }
 
 //判断条件是否合格
 var searchCountRange;
function panduan(flag){
	 searchCountRange = Ext.getCmp('searchCountRange').getRawValue();
	if(null==searchCountRange || searchCountRange.length<1){
		searchCountRange='日';
	}
	if(!Ext.getCmp('startone').isValid()){
		Ext.Msg.alert(alertTitle,'开始时间格式不正确!');
		return false;
	}
	if(!Ext.getCmp('endone').isValid()){
		Ext.Msg.alert(alertTitle,'结束时间格式不正确!'); 
		return false;
	}
	
	 startCount = Ext.get('startone').dom.value;
	 endCount = Ext.get('endone').dom.value;
	
	var date1 = Date.parseDate(startCount,'Y-m-d');
	var date2 = Date.parseDate(endCount,'Y-m-d');
	var d = (date2-date1)/(24*60*60*1000);//算出天数
	
	if(searchCountRange=='日' && d>30){
		Ext.Msg.alert(alertTitle,'按日统计最大不能超过30天！');
		return false;
	}
	
	 countCheckItems = Ext.get('checkItems').dom.value;
	 return true;
}	
//动态生成GRID列名称
function reDoGrid(cmItems,afields,store,grid){
	//var afields=['ONAME','END_DEPART_ID','DEPART_NAME','TAISHU'];
	var myarr=store.reader.jsonData.resultMap;
	if(myarr.length>0){
		var newArr= new Array();
		var num=0;
		for(var temp in myarr[0]){
			var flag=true;
			for(var j=0;j<afields.length;j++){
				if(afields[j]==temp){
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
		
		for(var i=0;i<newArr.length;i++){
			afields.push(newArr[i]);
			cmItems.push({header:newArr[i],dataIndex :newArr[i],width:colWidth,sortable:true});
		}
	}
	else{
		if(null!=newDateStore){
			newDateStore.removeAll();
			grid.doLayout();
		}
		//alertMsg('没有查询到数据！');
		return;
	}
		
		newDateStore = new Ext.data.Store({
        storeId:"newDateStore",
        baseParams:{start : 0,limit:pageSize},
        proxy: new Ext.data.HttpProxy({url:store.proxy.url}),
        reader: new Ext.data.JsonReader({
        	 root: 'resultMap', totalProperty: 'totalCount'
        }, afields)
    });
	grid.reconfigure(newDateStore,new Ext.grid.ColumnModel(cmItems));
	store.fields=afields;
	newDateStore.baseParams = store.baseParams;
	
    newDateStore.load();
    grid.doLayout();
}
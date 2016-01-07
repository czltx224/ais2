//送货盈利报表js
var searchMapUrl='reports/sendGoodsProfitsReport!findSendGoodsProfits.action';//送货盈利报表查询地址
var authorityDepartUrl='sys/basRightDepartAction!findDepartName.action';//权限部门查询地址
var dictionaryUrl='sys/dictionaryAction!ralaList.action';//数据字典地址
var privilege=146;
var defaultWidth=80;
var colWidth=80;
var searchDepartId=bussDepart;
var newDateStore;
var fields=['ENDDEPART','ENDDEPARTID','FNAME','SUMCOL'];

		//权限部门
        authorityDepartStore = new Ext.data.Store({ 
            storeId:"authorityDepartStore",                        
            proxy: new Ext.data.HttpProxy({url:sysPath+"/"+authorityDepartUrl,method:'post'}),
            baseParams:{
            	privilege:63,
            	filter_EQL_userId:userId
            },
            reader: new Ext.data.JsonReader({
            root: 'resultMap', totalProperty: 'totalCount'
           }, [  {name:'departName', mapping:'DEPARTNAME',type:'string'},
            {name:'departId', mapping:'RIGHTDEPARTID',type:'string'}             
              ])    
        });
    //统计纬度201
		countRangeStore	= new Ext.data.Store({ 
			autoLoad:true, 
			storeId:"countRangeStore",
			baseParams:{filter_EQL_basDictionaryId:201,privilege:16},
			proxy:new Ext.data.HttpProxy({url:sysPath+"/"+dictionaryUrl}),
			reader:new Ext.data.JsonReader({
	                    root:'result',
	                    totalProperty:'totalCount'
	        },[
        	{name:'countRangeId',mapping:'typeCode'},
        	{name:'countRangeName',mapping:'typeName'}
        	])
		});
	var startone = new Ext.form.DateField ({
    		id : 'startone',
    		format : 'Y-m-d',
    		allowBlank : false,
    		emptyText : "开始时间",
    		value:new Date().add(Date.DAY, -7),
    		width : defaultWidth+40
    	});
    	
    var endone = new Ext.form.DateField ({
    		id : 'endone',
    		format : 'Y-m-d',
    		allowBlank : false,
    		emptyText : "结束时间",
    		value:new Date(),
    		width : defaultWidth+40
    	});
   	function removeOne(){
   		sendGoodsProfitsGrid.getTopToolbar().remove(startone);
		sendGoodsProfitsGrid.getTopToolbar().remove(endone);
   	}
   	function addOne(){
   		sendGoodsProfitsGrid.getTopToolbar().insert(6,startone);
		sendGoodsProfitsGrid.getTopToolbar().insert(8,endone);
   	}
   	var tbar=[
			'时间选择:',{
			xtype : "combo",
			width : defaultWidth+20,
			triggerAction : 'all',
			forceSelection : true,
			model : 'local',
			hiddenId : 'checkItems',
			hiddenName : 'checkItems',
			store : [
					['o.start_time', '发车日期']],
			listeners: {   
				afterRender: function(combo) {   
		       　　combo.setValue('o.start_time');
				}
			},
			emptyText : '选择类型'
								
			},'-',
			'统计维度',{
			xtype : "combo",
			forceSelection : true,
			triggerAction : 'all',
			fieldLabel:'计算频率<font style="color:red;">*</font>',
			store : countRangeStore,
			mode : "remote",// 从本地载值
			//valueField : 'distributionModeId',// value值，与fields对应
			displayField : 'countRangeName',// 显示值，与fields对应
		    id : 'searchCountRange',
			width : defaultWidth,
			listeners:{
				select:function(combo,e){
					var v = combo.getValue();
					if(v=='日'){
						removeOne();
						startone = new Ext.form.DateField ({
				    		id : 'startone',
				    		format : 'Y-m-d',
				    		allowBlank : false,
				    		emptyText : "开始时间",
				    		value:new Date().add(Date.DAY, -7),
				    		width : defaultWidth+40
				    	});
				    	endone = new Ext.form.DateField ({
				    		id : 'endone',
				    		format : 'Y-m-d',
				    		allowBlank : false,
				    		emptyText : "结束时间",
				    		value:new Date(),
				    		width : defaultWidth+40
				    	});
				    	addOne();
				    	
					}else if(v=='周'){
						removeOne();
						startone = new Ext.form.NumberField ({
				    		id : 'startone',
				    		allowNegative :false,
				    		allowBlank : false,
				    		value:new Date().getWeekOfYear()-1,
				    		minValue:1,
				    		maxValue:52,
				    		maxLength:2,
				    		width : defaultWidth
				    	});
				    	endone = new Ext.form.NumberField  ({
				    		id : 'endone',
				    		allowNegative :false,
				    		allowBlank : false,
				    		value:new Date().getWeekOfYear(),
				    		minValue:1,
				    		maxValue:52,
				    		maxLength:2,
				    		width : defaultWidth
				    	});
				    	addOne();
					}else if(v=='月'){
						removeOne();
						startone = new Ext.form.DateField ({
							xtype:'datefield',
				    		id : 'startone',
				    		allowBlank : false,
				    		format : 'Y-m',
				    		emptyText : "开始时间",
				    		value:new Date().add(Date.MONTH, -1),
				    		width : defaultWidth+40
				    	});
				    	endone = new Ext.form.DateField ({
				    		id : 'endone',
				    		format : 'Y-m',
				    		allowBlank : false,
				    		emptyText : "结束时间",
				    		value:new Date(),
				    		width : defaultWidth+40
				    	});
				    	addOne();
					}else if(v=='年'){
						removeOne();
						startone = new Ext.form.NumberField ({
				    		id : 'startone',
				    		allowBlank : false,
				    		allowNegative :false,
				    		value:new Date().getFullYear()-1,
				    		minValue:1,
				    		width : defaultWidth
				    	});
				    	endone = new Ext.form.NumberField  ({
				    		id : 'endone',
				    		allowBlank : false,
				    		allowNegative :false,
				    		value:new Date().getFullYear(),
				    		minValue:1,
				    		width : defaultWidth
				    	});
				    	addOne();
					}
					sendGoodsProfitsGrid.getTopToolbar().doLayout(true);
				}
			}
		},
		'开始:',startone,' 至 ',endone,'-',{text : '<b>统计</b>',id : 'btn',iconCls : 'btnSearch',
    					handler : searchsendGoodsProfits
    	},'-',
	 	{text:'<b>导出</b>',iconCls:'sort_down',tooltip : '导出数据',
	 		handler:function(){
	 		   parent.exportExl(sendGoodsProfitsGrid);
	 		}
	 	},'-','<font color=red>默认统计一个月之内的数据</font>'
	 	];
	var twobar = new Ext.Toolbar([
		'部门名称:',{xtype : "combo",
				width : defaultWidth+20,
				editable:true,
				triggerAction : 'all',
				typeAhead : true,
				queryParam : 'filter_LIKES_departName',
				pageSize : comboSize,
				forceSelection : true,
				resizable:true,
				minChars : 0,
				store : authorityDepartStore,
				resizable:true,
				forceSelection : true,
				valueField : 'departId',//value值，与fields对应
				displayField : 'departName',//显示值，与fields对应
			    id:'authorityDepartId', 
			    enableKeyEvents:true,listeners : {
	 		    keyup:function(textField, e){
                     if(e.getKey() == 13){
                     	searchsendGoodsProfits();
                     }
	 		}
	 	
	 	}}
	 	]);	
function newPictrue(newstore){
		var strXml='';
    	var strCate='';
    	var capTitle = '送货盈利';
    	var rate1='送货盈利';
		var model=sendGoodsProfitsGrid.getColumnModel();
		var colNum=model.getColumnCount();

		for(var i=0;i<dateStore.getCount();i++){
			var fname = dateStore.getAt(i).get('FNAME');
			if(fname=="送货盈利"){
				var boolflag =false;
				for(var j=0;j<colNum;j++){
					
					if(!model.isHidden(j) && model.getDataIndex(j)){
						var field=newstore.fields.map[model.getDataIndex(j)];
						var mapping=field.mapping==null?field.name:field.mapping;
						//alert(field+"===="+mapping);
						if(mapping=="SUMCOL"){
							boolflag=true;
							var v = newstore.getAt(i).get(mapping);
							strCate+="<set label='合计' value='"+(v==null?0:v)+"'/>";
							continue;
						}
						if(boolflag){
							var v = newstore.getAt(i).get(mapping);
							//设定统计日期
							strCate+="<set label='"+mapping+"' value='"+(v==null?0:v)+"'/>";
						}
					}
				}
			}
		}
			//alert(strCate);
			//更改标题
			strXml+="<chart caption='"+capTitle+"' xAxisName='"+capTitle+"' yAxisName='profits'"
			+"numberPrefix='￥' showLabels='1' showColumnShadow='1' animation='1' showAlternateHGridColor='1' AlternateHGridColor='ff5904'"
			+" divLineColor='ff5904' divLineAlpha='20' alternateHGridAlpha='5' canvasBorderColor='666666' "
			+"baseFontColor='666666' lineColor='FF5904' lineAlpha='85' showValues='1' rotateValues='1' valuePosition='auto'>"
			strXml+=strCate+"</chart>";
			//alert(strXml);
			var picturePanel=new Ext.ux.Chart.Fusion.Panel({
				collapsible:false,
				chartCfg:{
					id:'chart1',
					params:{
						flashVars:{
							debugMode:0,
							lang:'EN'
						}
					}
				},
				renderTo: 'southDiv',
				autoScroll:true,
				id:'picturePanel',
				chartURL:'chars/fcf/Line.swf',
				dataXML:strXml,
		        height:Ext.lib.Dom.getViewHeight()/2,		
		        width:Ext.lib.Dom.getViewWidth()-10
			});
	}	 	
Ext.onReady(function() {

   var sm = new Ext.grid.CheckboxSelectionModel({});
   var rowNum = new Ext.grid.RowNumberer({
        header:'序号', width:30
    });
   	dateStore = new Ext.data.Store({
        storeId:"dateStore",
        proxy: new Ext.data.HttpProxy({url : sysPath+ "/"+searchMapUrl}),
        baseParams:{privilege:privilege,limit : pageSize},
        listeners:{
        	'load':function(store,e){
       			var cmItems = [sm,rowNum]; 
				var afields=['ENDDEPART','ENDDEPARTID','FNAME','SUMCOL'];
				cmItems.push( {header:'部门编号',dataIndex:"ENDDEPARTID",width:colWidth,sortable:true,hidden:true});
				cmItems.push({header:"部门名称",dataIndex :'ENDDEPART',width:colWidth,sortable:true});
            	cmItems.push({header:'统计名称',dataIndex:"FNAME",width:colWidth,sortable:true});
				cmItems.push({header:"合计",dataIndex :'SUMCOL',width:colWidth,sortable:true});
				reDoGrid(cmItems,afields,store,sendGoodsProfitsGrid);
	        }
        },
        reader: new Ext.data.JsonReader({
            root: 'resultMap', totalProperty: 'totalCount'
        }, fields)
    });
    sendGoodsProfitsGrid = new Ext.grid.GridPanel({
        renderTo: 'northDiv',
        id:'sendGoodsProfitsCenter',
        //region : 'north',
        height:Ext.lib.Dom.getViewHeight()/2,		
        width:Ext.lib.Dom.getViewWidth()-1,
        viewConfig : {
			columnsText : "显示的列",
			sortAscText : "升序",
			sortDescText : "降序"
			//forceFit : true
		},
		autoScroll : true,
		//autoSizeColumns: true,
		frame : false,
		loadMask : true,
		stripeRows : true,
        sm:sm,
       cm:new Ext.grid.ColumnModel([rowNum,sm,
            {header:'部门编号',dataIndex:"ENDDEPARTID",width:colWidth,sortable:true,hidden:true},
            {header:'部门名称',dataIndex:"ENDDEPART",width:colWidth,sortable:true},
            {header:'统计名称',dataIndex:"FNAME",width:colWidth,sortable:true},
            {header:"合计",dataIndex :'SUMCOL',width:colWidth,sortable:true}
        ]),
        store:dateStore,
        tbar: tbar,
        listeners : {
			render : function() {
				twobar.render(this.tbar);
			}
		},
        bbar: new Ext.PagingToolbar({
            pageSize: pageSize, 
            store: dateStore,
            displayInfo: true,
            displayMsg: '显示第 {0} 条到 {1} 条记录，共 {2} 条', 
            emptyMsg: "没有记录信息显示"
        })
    });
  });
  
 function searchsendGoodsProfits() {
 	dateStore.baseParams={start : 0,limit:pageSize};
	var authorityDepartId=Ext.getCmp('authorityDepartId').getValue();
	var searchCountRange = Ext.getCmp('searchCountRange').getValue();
	if(searchCountRange.length==0){
		searchCountRange="日";
	}
	if(authorityDepartId.length>0){
		searchDepartId=authorityDepartId;
	}else{
		searchDepartId=bussDepart;
	}
	 if(!panduan()){
		return;
	 }
 	 Ext.apply(dateStore.baseParams, {
 		filter_countRange:searchCountRange,
 	 	filter_startCount:startCount,
 	 	filter_endCount:endCount,
 	 	filter_countCheckItems:countCheckItems,
 		filter_enddepartid:searchDepartId
 	});
	dataReload();
}
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
        listeners:{
        	'load':function(st,e){
        		//送货盈利报表图形
        		var div =document.getElementById('southDiv');
				while(div.hasChildNodes()){ //当div下还存在子节点时 循环继续
       				div.innerHTML = "";   
   				}
   				newPictrue(newDateStore);
        	}
        },
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
 function dataReload(){
			dateStore.reload({
			params : {
				start : 0,
				limit:pageSize
				}
			})
		}
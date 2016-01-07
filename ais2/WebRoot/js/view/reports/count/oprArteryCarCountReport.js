//干线车时效统计报表js
var searchReportUrl='reports/oprArteryCarReportAction!findCountReport.action';//干线车统计报表查询地址
var dictionaryUrl='sys/dictionaryAction!ralaList.action';//数据字典地址
var departUrl='sys/departAction!findAll.action';
var privilege=197;
var defaultWidth=60;
var colWidth=100;
var pubauthorityDepart=bussDepart;
var fields=['ID','MANAGERTARGET','TRUEFINISH','KPICOLOR','KPIYEAR','KPIMONTH','KPIDAY','KPIDATE','WARNINGPERCENT',
			'WARNINGRATE','QUALIFIEDNUM','TOTALNUM','DUTYDEPARTID','DUTYDEPARTNAME','PARENTDEPARTID','PARENTDEPARTNAME','COUNTRATENAME','OPR_DATE'];
			
		//内部交接部门(业务部门)
		departStore = new Ext.data.Store({
	        storeId:"departStore",
	        proxy: new Ext.data.HttpProxy({url:sysPath+'/'+departUrl}),
	        //baseParams:{filter_EQL_isBussinessDepa:1},
	        reader: new Ext.data.JsonReader({
	            root: 'result', totalProperty: 'totalCount'
	        }, [
	            {name:'departId'},
	            {name: 'departName'}
	        ])
        });
		
 		//自动计算频率201
		countRangeStore	= new Ext.data.Store({ 
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
    		emptyText : "开始时间",
    		value:new Date().add(Date.DAY, -7),
    		width : defaultWidth+40
    	});
    	
    var endone = new Ext.form.DateField ({
    		id : 'endone',
    		format : 'Y-m-d',
    		emptyText : "结束时间",
    		value:new Date(),
    		width : defaultWidth+40
    	});
   	function removeOne(){
   		kpiReportGrid.getTopToolbar().remove(startone);
		kpiReportGrid.getTopToolbar().remove(endone);
   	}
   	function addOne(){
   		kpiReportGrid.getTopToolbar().insert(6,startone);
		kpiReportGrid.getTopToolbar().insert(8,endone);
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
					['kpiDate', 'KPI日期'],
					['oprDate', '业务日期']],
			listeners: {   
				afterRender: function(combo) {   
		       　　combo.setValue('oprDate');
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
				    		emptyText : "开始时间",
				    		value:new Date().add(Date.DAY, -7),
				    		width : defaultWidth+40
				    	});
				    	endone = new Ext.form.DateField ({
				    		id : 'endone',
				    		format : 'Y-m-d',
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
				    		value:new Date().getWeekOfYear()-1,
				    		minValue:1,
				    		maxValue:52,
				    		maxLength:2,
				    		width : defaultWidth
				    	});
				    	endone = new Ext.form.NumberField  ({
				    		id : 'endone',
				    		allowNegative :false,
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
				    		format : 'Y-m',
				    		emptyText : "开始时间",
				    		value:new Date().add(Date.MONTH, -1),
				    		width : defaultWidth+40
				    	});
				    	endone = new Ext.form.DateField ({
				    		id : 'endone',
				    		format : 'Y-m',
				    		emptyText : "结束时间",
				    		value:new Date(),
				    		width : defaultWidth+40
				    	});
				    	addOne();
					}else if(v=='年'){
						removeOne();
						startone = new Ext.form.NumberField ({
				    		id : 'startone',
				    		allowNegative :false,
				    		value:new Date().getFullYear()-1,
				    		minValue:1,
				    		width : defaultWidth
				    	});
				    	endone = new Ext.form.NumberField  ({
				    		id : 'endone',
				    		allowNegative :false,
				    		value:new Date().getFullYear(),
				    		minValue:1,
				    		width : defaultWidth
				    	});
				    	addOne();
					}
					kpiReportGrid.getTopToolbar().doLayout(true);
				}
			}
		},
		'开始:',startone,' 至 ',endone,'-',{text : '<b>统计</b>',id : 'btn',iconCls : 'btnSearch',
    					handler : searchSenterPortReport
    	},'-',
	 	{text:'<b>导出</b>',iconCls:'sort_down',tooltip : '导出数据',
	 		handler:function(){
	 		   parent.exportExl(kpiReportGrid);
	 		}
	 	},'-','<font color=red>默认统计一个月之内的数据</font>'
	 	];	
		
	var threeBar = new Ext.Toolbar([ 
			'责任部门:',{xtype : "combo",
				width : defaultWidth+20,
				editable:true,
				triggerAction : 'all',
				queryParam : 'filter_LIKES_departName',
				pageSize : comboSize,
				resizable:true,
				minChars : 0,
				store : departStore,
				resizable:true,
				valueField : 'departId',//value值，与fields对应
				displayField : 'departName',//显示值，与fields对应
			    id:'dutyDepartId', 
			    enableKeyEvents:true,listeners : {
	 		    keyup:function(textField, e){
                     if(e.getKey() == 13){
                     	searchSenterPortReport();
                     }
	 		}
	 	
	 	}},'-',
	 	'上级部门:',{xtype : "combo",
				width : defaultWidth+20,
				editable:true,
				triggerAction : 'all',
				queryParam : 'filter_LIKES_departName',
				pageSize : comboSize,
				resizable:true,
				minChars : 0,
				store : departStore,
				resizable:true,
				valueField : 'departId',//value值，与fields对应
				displayField : 'departName',//显示值，与fields对应
			    id:'parentDepartId', 
			    enableKeyEvents:true,listeners : {
	 		    keyup:function(textField, e){
                     if(e.getKey() == 13){
                     	searchSenterPortReport();
                     }
	 		}
	 	
	 	}},'-','条件:',{
			xtype : "combo",
			width : defaultWidth+20,
			triggerAction : 'all',
			forceSelection : true,
			model : 'local',
			hiddenId : 'checkHour',
			hiddenName : 'checkHour',
			store : [
					['managerTarget', '管理指标'],
					['trueFinish', '实际完成'],
					['warningPercent', '预警偏差'],
					['warningRate', '预警偏差率'],
					['qualifiedNum', '合格数'],
					['totalNum', '总数']],
			listeners: {   
				afterRender: function(combo) {   
		       　　combo.setValue('managerTarget');
				}
			},
			emptyText : '选择类型'
								
			},'-',{
				xtype:'numberfield',
				id:'startHour',
				forceSelection : true,
				width:defaultWidth-20
			},'至',{
				xtype:'numberfield',
				id:'endHour',
				forceSelection : true,
				width:defaultWidth-20
			}
	]);
	var gz='广州配送中心';
   	var sz='空港配送深圳配送中心';
	function newPictrue(newstore,departName){
		var strXml='';
    	var strCate='<categories>';
    	var strDataSet='';
    	if(null!=departName && departName.length>0){
    		newstore.filter('DUTYDEPARTNAME',departName);
    	}
    	var capTitle = '干线车时效';
    	var rate1='到车准点率';
    	var rate2='发车准点率';
    	var rate3='运行达标率';
    	var rate4='准点到达率';
		var str1="<dataset seriesName='"+rate1+"' color='2C7DDA'>";
		var str2="<dataset seriesName='"+rate2+"' color='1A2CA6'>";
		var str3="<dataset seriesName='"+rate3+"' color='3ECD2E'>";
		var str4="<dataset seriesName='"+rate4+"' color='CC3300'>";
		var str5="<dataset seriesName='管理标准' parentYAxis='S' color='A046AD'>";
		
		var mydate =new Array();
		for(var i=0;i<newstore.getCount();i++){
			var strMon=newstore.getAt(i).get('OPR_DATE');
			var rateName= newstore.getAt(i).get('COUNTRATENAME');
			var v = newstore.getAt(i).get('TRUEFINISH');
			var v2 = newstore.getAt(i).get('MANAGERTARGET');
			
			//设定统计日期
			var myflag=true;
			if(i==0){
				mydate[0]=strMon;
				strCate+="<category label='"+strMon+"'/>";
				str5+="<set value='"+v2+"'/>";
			}
			for(var j=0;j<mydate.length;j++){
				if(mydate[j]==strMon){
					myflag=false;
					continue;
				}
			}
			if(myflag){
				strCate+="<category label='"+strMon+"'/>";
				str5+="<set value='"+v2+"'/>";
				mydate[mydate.length]=strMon;
			}
			//日期怎么确定...如上代码，搞定
			if(rateName==rate1){
				str1+="<set value='"+v+"'/>";
			}else if(rateName==rate2){
				str2+="<set value='"+v+"'/>";
			}else if(rateName==rate3){
				str3+="<set value='"+v+"'/>";
			}else if(rateName==rate4){
				str4+="<set value='"+v+"'/>";
			}
			
		}
		newstore.clearFilter();
		str1+="</dataset>";
		str2+="</dataset>";
		str3+="</dataset>";
		str4+="</dataset>";
		str5+="</dataset>";
		strDataSet=str1+str2+str3+str4+str5;
			//alert(strDataSet);
			strCate+='</categories>';
			//更改标题
			if(departName==gz){
	    		capTitle=gz+capTitle;
	    	}else if(departName==sz){
	    		capTitle=sz+capTitle;
	    	}
			strXml+="<styles><definition><style type='font' color='666666' name='CaptionFont' size='20' />"
			+"<style type='font' name='SubCaptionFont' size='10' bold='0' /></definition><application><apply toObject='caption' styles='CaptionFont' />"
			+"<apply toObject='XAxisName' styles='SubCaptionFont' /></application></styles>";
			strXml="<chart caption='"+capTitle+"' XAxisName='时效报表' palette='1' "
			+" animation='1' formatNumberScale='1' numberSuffix='%' showLabels='1' showvalues='1'"
			+"seriesNameInToolTip='1'>"+strCate+strDataSet+strXml+"</chart>";
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
				chartURL:'chars/fcf/MSColumnLine3D.swf',
				dataXML:strXml,
		        height:Ext.lib.Dom.getViewHeight()/2,		
		        width:Ext.lib.Dom.getViewWidth()-10
			});
	}
Ext.onReady(function() {
	//<!-- KPI统计报表 -->
   	dateStore = new Ext.data.Store({
        storeId:"dateStore",
        proxy: new Ext.data.HttpProxy({url : sysPath+ "/"+searchReportUrl}),
        baseParams:{privilege:privilege,limit : pageSize},
        sortInfo :{field: "OPR_DATE", direction: "ASC"},
        listeners:{
        	'load':function(){
        		//KPI时效报表统计图形化
        		var div =document.getElementById('southDiv');
				while(div.hasChildNodes()){ //当div下还存在子节点时 循环继续
       				div.innerHTML = "";   
   				}
 				newPictrue(dateStore,gz);
 				newPictrue(dateStore,sz);
	      	  }
        },
        reader: new Ext.data.JsonReader({
            root: 'resultMap', totalProperty: 'totalCount'
        }, fields)
    });
   
   var sm = new Ext.grid.CheckboxSelectionModel({});
	var rowNum = new Ext.grid.RowNumberer({
        header:'序号', width:30
    });
    kpiReportGrid = new Ext.grid.GridPanel({
        renderTo: 'northDiv',
        id:'enterPortReportCenter',
        //region : 'north',
        height:Ext.lib.Dom.getViewHeight()/2,		
        width:Ext.lib.Dom.getViewWidth()-10,
        viewConfig : {
			columnsText : "显示的列",
			sortAscText : "升序",
			sortDescText : "降序"
			//forceFit : true
		},
		autoScroll : true,
		//autoSizeColumns: true,
		frame : true,
		loadMask : true,
		stripeRows : true,
        sm:sm,
       cm:new Ext.grid.ColumnModel([rowNum,sm,
            {header:'KPI日期',dataIndex:"KPIDATE",width:colWidth+20,sortable:true,hidden:true},
            {header:'业务日期',dataIndex:"OPR_DATE",width:colWidth+20,sortable:true},
			{header:'责任部门',dataIndex:'DUTYDEPARTNAME',width:colWidth,sortable:true},
			{header:'统计率',dataIndex:'COUNTRATENAME',width:colWidth,sortable:true},
			{header:'管理指标',dataIndex:'MANAGERTARGET',width:colWidth,sortable:true,align:'center'
				,renderer:function renderDescn(v, cellmeta, record, rowIndex, columnIndex, store){
					return v+'%'; 
				}
			},
			{header:'实际完成',dataIndex:'TRUEFINISH',width:colWidth,sortable:true,align:'center' 
				,renderer:function renderDescn(v, cellmeta, record, rowIndex, columnIndex, store){
					cellmeta.css = 'x-grid-back-'+record.get('KPICOLOR');
					//return '<font color="'+record.get('KPICOLOR')+'">'+(v==null?'0':v)+'</font>';
					return v+'%'; 
				}
			},
			{header:'预警偏差',dataIndex:'WARNINGPERCENT',width:colWidth,sortable:true},
			{header:'预警偏差率',dataIndex:'WARNINGRATE',width:colWidth,sortable:true},
			{header:'合格数',dataIndex:'QUALIFIEDNUM',width:colWidth,sortable:true},
			{header:'总数',dataIndex:'TOTALNUM',width:colWidth,sortable:true},
			{header:'KPI颜色',dataIndex:'KPICOLOR',width:colWidth,sortable:true,hidden:true},
			{header:'年',dataIndex:'KPIYEAR',width:colWidth,sortable:true,align:'center' },
			{header:'月',dataIndex:'KPIMONTH',width:colWidth,sortable:true,align:'center' },
			{header:'日',dataIndex:'KPIDAY',width:colWidth,sortable:true,align:'center' },
			{header:'上级单位',dataIndex:'PARENTDEPARTNAME',width:colWidth,sortable:true}
        ]),
        store:dateStore,
        tbar: tbar,
        listeners : {
			render : function() {
				threeBar.render(this.tbar);
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
    
	
 function searchSenterPortReport() {
	if(!Ext.getCmp('startone').isValid()){
		Ext.Msg.alert(alertTitle,'开始时间格式不正确!');
		return;
	}
	if(!Ext.getCmp('endone').isValid()){
		Ext.Msg.alert(alertTitle,'结束时间格式不正确!');
		return;
	}
	
	if(!Ext.getCmp('startHour').isValid()){
		Ext.Msg.alert(alertTitle,'开始用时格式不正确!');
		return;
	}
	if(!Ext.getCmp('endHour').isValid()){
		Ext.Msg.alert(alertTitle,'结束用时格式不正确!');
		return;
	}
	dateStore.baseParams={
 		start : 0,
		privilege:privilege,
		limit:pageSize
 	};
	var checkHour = Ext.get('checkHour').dom.value;
	var startHour = Ext.getCmp('startHour').getValue();
	var endHour = Ext.getCmp('endHour').getValue();
	
	if(endHour!=0 &&startHour>endHour){
		Ext.Msg.alert(alertTitle,'开始数值不能大于结束数值!');
		return;
	}

	var searchCountRange = Ext.getCmp('searchCountRange').getRawValue();
	
	if(null==searchCountRange || searchCountRange.length<1){
		searchCountRange='日';
	}
	
	var startCount = Ext.get('startone').dom.value;
	var endCount = Ext.get('endone').dom.value;
	var countCheckItems = Ext.get('checkItems').dom.value;
	
	var dutyDepartId=Ext.getCmp('dutyDepartId').getValue();
	var parentDepartId=Ext.getCmp('parentDepartId').getValue();
	if(startCount=='开始时间'){
		startCount='';
	}
	if(endCount=='结束时间'){
		endCount='';
	}
	
 	 Ext.apply(dateStore.baseParams, {
 	 	filter_countRange:searchCountRange,
 	 	filter_startCount:startCount,
 	 	filter_endCount:endCount,
 	 	filter_dutyDepartId:dutyDepartId,
 	 	filter_parentDepartId:parentDepartId,
 	 	filter_countCheckItems:countCheckItems,
 	 	filter_checkHour:checkHour,
 	 	filter_startHour:startHour,
 	 	filter_endHour:endHour,
 		limit:pageSize
 	});
	dataReload();
}

 function dataReload(){
			dateStore.reload({
			params : {
				start : 0,
				privilege:privilege,
				limit:pageSize
				}
			})
		}
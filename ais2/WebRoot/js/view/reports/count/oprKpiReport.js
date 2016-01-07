//KPI统计报表js
var searchReportUrl='reports/oprEnterPortKpiAction!findKpiReport.action';//KPI统计报表查询地址
var dictionaryUrl='sys/dictionaryAction!ralaList.action';//数据字典地址
var authorityDepartUrl='sys/basRightDepartAction!findDepartName.action';//权限部门查询地址
var departUrl='sys/departAction!findDepart.action';//部门查询地址
var privilege=197;
var defaultWidth=60;
var colWidth=100;
var pubauthorityDepart=bussDepart;
var fields=['ID','KPINAME','MANAGERTARGET','TRUEFINISH','KPICOLOR','KPIYEAR','KPIMONTH','KPIDAY','KPIDATE','WARNINGPERCENT','COUNTRANGE',
			'WARNINGRATE','QUALIFIEDNUM','TOTALNUM','DUTYDEPARTID','DUTYDEPARTNAME','PARENTDEPARTID','PARENTDEPARTNAME','OPERATETYPE','OPR_DATE'];
			
		//内部交接部门(业务部门)
		departStore = new Ext.data.Store({
	        storeId:"departStore",
	        proxy: new Ext.data.HttpProxy({url:sysPath+'/'+departUrl}),
	        //baseParams:{filter_EQL_isBussinessDepa:1},
	        baseParams:{privilege:53,filter_EQL_isBussinessDepa:1},
	        reader: new Ext.data.JsonReader({
	            root: 'resultMap', totalProperty: 'totalCount'
	        }, [
	            {name:'departId',mapping:'DEPARTID'},
	            {name: 'departName',mapping:'DEPARTNAME'}
	        ])
        });
		
 		//自动计算频率201
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
		
		//KPI名称 225
		kpiStore	= new Ext.data.Store({ 
			storeId:"kpiStore",
			autoLoad:true, //此处设置为自动加载
			baseParams:{filter_EQL_basDictionaryId:225,privilege:16,filter_EQL_typeCode:1},
			proxy:new Ext.data.HttpProxy({url:sysPath+"/"+dictionaryUrl}),
			reader:new Ext.data.JsonReader({
	                    root:'result',
	                    totalProperty:'totalCount'
	        },[
        	{name:'typeCode'},
        	{name:'typeName'}
        	])
		});
		 //操作方式224
		operationStore	= new Ext.data.Store({ 
			storeId:"operationStore",
			baseParams:{filter_EQL_basDictionaryId:224,privilege:16},
			proxy:new Ext.data.HttpProxy({url:sysPath+"/"+dictionaryUrl}),
			reader:new Ext.data.JsonReader({
	                    root:'result',
	                    totalProperty:'totalCount'
	        },[
        	{name:'operationId',mapping:'typeCode'},
        	{name:'operationName',mapping:'typeName'}
        	])
		});
	 //权限部门
    authorityDepartStore = new Ext.data.Store({ 
        storeId:"authorityDepartStore",                        
        proxy: new Ext.data.HttpProxy({url:sysPath+"/"+authorityDepartUrl,method:'post'}),
        baseParams:{
        	privilege:63,
        	filter_EQL_userId:userId
        },
        reader: new Ext.data.JsonReader({
        root: 'result', totalProperty: 'totalCount'
       }, [  {name:'departName', mapping:'departName',type:'string'},
        {name:'departId', mapping:'rightDepartid',type:'string'}             
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
			'操作方式:',{ xtype : 'combo',
				triggerAction : 'all',
				store : operationStore,
				forceSelection : true,
				mode : "remote",//获取服务器的值
				valueField : 'operationName',//value值，与fields对应
				displayField : 'operationName',//显示值，与fields对应
				id:'searchOperationType',
				width:defaultWidth
			},'-',
			'责任部门:',{xtype : "combo",
				width : defaultWidth+20,
				editable:true,
				listWidth:200,
				triggerAction : 'all',
				//typeAhead : true,
				queryParam : 'filter_LIKES_departName',
				pageSize : comboSize,
				forceSelection : true,
				resizable:true,
				minChars : 0,
				store : departStore,
				resizable:true,
				forceSelection : true,
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
				listWidth:200,
				triggerAction : 'all',
				//typeAhead : true,
				queryParam : 'filter_LIKES_departName',
				pageSize : comboSize,
				forceSelection : true,
				resizable:true,
				minChars : 0,
				store : departStore,
				resizable:true,
				forceSelection : true,
				valueField : 'departId',//value值，与fields对应
				displayField : 'departName',//显示值，与fields对应
			    id:'parentDepartId', 
			    enableKeyEvents:true,listeners : {
	 		    keyup:function(textField, e){
                     if(e.getKey() == 13){
                     	searchSenterPortReport();
                     }
	 		}
	 	
	 	}},'-','KPI名称<font style="color:red;">*</font>',{
	 		xtype : 'combo',
	 		width : defaultWidth+20,
			triggerAction : 'all',
			pageSize : comboSize,
			forceSelection : true,
			allowBlank : false,
			typeAhead : true,
			queryParam : 'filter_LIKES_typeName',
			store : kpiStore,
			fieldLabel : 'KPI名称',
			minChars : 0,
			mode : "remote",//获取服务器的值
			valueField : 'typeName',//value值，与fields对应
			displayField : 'typeName',//显示值，与fields对应
			id:'searchKpiName'
	 	},'条件:',{
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
	function newPictrue(newstore,kpiName,departName){
		var strXml='';
    	var strCate='<categories>';
    	var strDataSet='';
    	var capTitle =kpiName;
    	if(null!=departName && departName.length>0){
    		newstore.filter('DUTYDEPARTNAME',departName);
    	}
    	/*for(var j=0;j<newstore.getCount();j++){
    		var strMon=newstore.getAt(j).get('KPIDATE');
			strCate+="<category label='"+strMon+"'/>";
		}*/
    	/*var partStore = newstore;
    	partStore.filter('DUTYDEPARTNAME',gz);
    	var gz_num = partStore.getCount();
    	partStore = newstore;
    	partStore.filter('DUTYDEPARTNAME',sz);
    	var sz_num = partStore.getCount();
    	partStore.clearFilter();
    	partStore.filter('KPINAME',kpiName);
    	for(var j=0;j<newstore.getCount();j++){
    		if(gz_num>=sz_num && newstore.getAt(j).get('DUTYDEPARTNAME')==gz){
	    		var strMon=newstore.getAt(j).get('KPIDATE');
				strCate+="<category label='"+strMon+"'/>";
    		}else if(gz_num<sz_num && newstore.getAt(j).get('DUTYDEPARTNAME')==sz){
    			var strMon=newstore.getAt(j).get('KPIDATE');
				strCate+="<category label='"+strMon+"'/>";
    		}
		}*/
		var strInfo="";
		if(capTitle=='进港时效'){
			var str1="<dataset seriesName='"+gz+"' color='1A2CA6'>";
			var str2="<dataset seriesName='"+sz+"' color='3ECD2E'>";
			var str3="<dataset seriesName='广州管理标准' parentYAxis='S'>";
			var str4="<dataset seriesName='深圳管理标准' parentYAxis='S'>";
			var mydate =new Array();
    		for(var i=0;i<newstore.getCount();i++){
    			var strMon=newstore.getAt(i).get('OPR_DATE');
				
				var v = newstore.getAt(i).get('TRUEFINISH');
				var v2 = newstore.getAt(i).get('MANAGERTARGET');
				//设定统计日期
				var myflag=true;
				if(i==0){
					mydate[0]=strMon;
					strCate+="<category label='"+strMon+"'/>";
				}
				for(var j=0;j<mydate.length;j++){
					if(mydate[j]==strMon){
						myflag=false;
						continue;
					}
				}
				if(myflag){
					strCate+="<category label='"+strMon+"'/>";
					mydate[mydate.length]=strMon;
				}
				//日期怎么确定...如上代码，搞定
				if(newstore.getAt(i).get('DUTYDEPARTNAME')==gz){
					str1+="<set value='"+(v==null?0:v)+"'/>";
					str3+="<set value='"+(v2==null?0:v2)+"'/>";
				}else if(newstore.getAt(i).get('DUTYDEPARTNAME')==sz){
					str2+="<set value='"+(v==null?0:v)+"'/>";
					str4+="<set value='"+(v2==null?0:v2)+"'/>";
				}
			}
			str1+="</dataset>";
			str2+="</dataset>";
			str3+="</dataset>";
			str4+="</dataset>";
			strInfo=str1+str2+str3+str4;
		}else if(capTitle=='出港时效' || capTitle=='签收时效' || capTitle=='回单确收时效'){
			var type1='市内送货';
			var type2='中转';
			var type3='外发';
			
			var arr1 = new Array();　//创建一个size长度的数组，注意Array的长度是可变的，所以不是上限，是长度
			var arr2 = new Array();
			var arr3 = new Array();
			var arr4 = new Array();
			var arr5 = new Array();
			var arr6 = new Array();
			
			var str1="<dataset seriesName='"+type1+"' color='2C7DDA'>";//市内送货
			var str2="<dataset seriesName='"+type2+"' color='CC3300'>";//中转
			var str4="<dataset seriesName='"+type3+"' color='3ECD2E'>";//外发
			var str3="<dataset seriesName='管理标准' parentYAxis='S'>";
			var countTitle = capTitle.substring(0,capTitle.indexOf('时效'));
			var strcount="<dataset seriesName='"+countTitle+"及时率' parentYAxis='S'color='A046AD'>";//统计
			
			var num1=0;
			var num2=0;
			var num3=0;
			var mydate =new Array();
			//alert(newstore.getCount());
			for(var i=0;i<newstore.getCount();i++){
				var v = newstore.getAt(i).get('TRUEFINISH')==null?'0':newstore.getAt(i).get('TRUEFINISH');
				var v2 = newstore.getAt(i).get('MANAGERTARGET');
				var totalNum = newstore.getAt(i).get('TOTALNUM');
				var qualifiedNum = newstore.getAt(i).get('QUALIFIEDNUM');
				//实际完成达标数/实际总数=及时率
				
				var operateType = newstore.getAt(i).get('OPERATETYPE');
				var strMon=newstore.getAt(i).get('OPR_DATE');
				
				//设定统计日期
				var myflag=true;
				if(i==0){
					mydate[0]=strMon;
					strCate+="<category label='"+strMon+"'/>";
					str3+="<set value='"+(v2==null?0:v2)+"'/>";
				}
				for(var j=0;j<mydate.length;j++){
					if(mydate[j]==strMon){
						myflag=false;
						continue;
					}
				}
				if(myflag){
					strCate+="<category label='"+strMon+"'/>";
					str3+="<set value='"+v2+"'/>";
					mydate[mydate.length]=strMon;
				}
				//日期怎么确定...如上代码，搞定
				//alert(operateType);
				if(type1==operateType){
					arr1[num1]=totalNum;
					arr2[num1]=qualifiedNum;
					num1++;
					str1+="<set value='"+(v==null?0:v)+"'/>";
				}else if(type2==operateType){
					arr3[num2]=totalNum;
					arr4[num2]=qualifiedNum;
					num2++;
					str2+="<set value='"+(v==null?0:v)+"'/>";
				}else if(type3==operateType){
					arr5[num3]=totalNum;
					arr6[num3]=qualifiedNum;
					num3++;
					str4+="<set value='"+(v==null?0:v)+"'/>";
				}
			};//循环结束
			//alert(mydate.length);
			//alert(mydate);
			for(var i=0;i<mydate.length;i++){
				var sumTotalNum=Number(arr1[i]==null?0:arr1[i])+Number(arr3[i]==null?0:arr3[i])+Number(arr5[i]==null?0:arr5[i]);
				var sumQualifiedNum=Number(arr2[i]==null?0:arr2[i])+Number(arr4[i]==null?0:arr4[i])+Number(arr6[i]==null?0:arr6[i]);
				var v = sumQualifiedNum/sumTotalNum;
				//alert(v);
				if(isNaN(v)){
					v=0;
				}
				strcount+="<set value='"+ (v==null?0:Math.round(v*100,2))+"'/>";
			}
			//alert(strcount);
			strcount+="</dataset>";
			str1+="</dataset>";
			str2+="</dataset>";
			str3+="</dataset>";
			str4+="</dataset>";
			strInfo=str1+str2+str4+strcount+str3;
			
		}else if(capTitle=='回单扫描时效'){
			var mydate =new Array();
			mydate[0]='';
			var arr1 = new Array();　//创建一个size长度的数组，注意Array的长度是可变的，所以不是上限，是长度
			var arr2 = new Array();
			var num1=0;
			var str1="<dataset seriesName='扫描及时率' color='2C7DDA'>";
			var str3="<dataset seriesName='管理标准' parentYAxis='S'>";
			var vtotalNum=0;
			var vqualifiedNum=0;
			for(var i=0;i<newstore.getCount();i++){
				var v = newstore.getAt(i).get('TRUEFINISH')==null?0:newstore.getAt(i).get('TRUEFINISH');
				var v2 = newstore.getAt(i).get('MANAGERTARGET');
				var totalNum = newstore.getAt(i).get('TOTALNUM')==null?0:newstore.getAt(i).get('TOTALNUM');
				var qualifiedNum = newstore.getAt(i).get('QUALIFIEDNUM')==null?0:newstore.getAt(i).get('QUALIFIEDNUM');
				//实际完成达标数/实际总数=及时率
				
				var operateType = newstore.getAt(i).get('OPERATETYPE');
				var strMon=newstore.getAt(i).get('OPR_DATE');
				
				var myflag=true;
				for(var j=0;j<mydate.length;j++){
					if(mydate[j]==strMon){
						myflag=false;
						break;
					}
				}
				if(myflag){
					strCate+="<category label='"+strMon+"'/>";
					str3+="<set value='"+(v2==null?0:v2)+"'/>";
					mydate[mydate.length]=strMon;
					
					vtotalNum=totalNum;
					vqualifiedNum=qualifiedNum;
					num1++;
					continue;
				}
				//日期怎么确定...如上代码，搞定
				vtotalNum+=totalNum;
				vqualifiedNum+=qualifiedNum;
				arr1[num1]=vtotalNum;
				arr2[num1]=vqualifiedNum;
			}
			for(var i=1;i<mydate.length;i++){
				var sumTotalNum=Number(arr1[i]==null?0:arr1[i]);
				var sumQualifiedNum=Number(arr2[i]==null?0:arr2[i]);
				var v = sumQualifiedNum/sumTotalNum;
				// alert(sumTotalNum+"--"+sumQualifiedNum);
				if(isNaN(v)){
					v=0;
				}
				str1+="<set value='"+ (v==null?0:Math.round(v*100,2))+"'/>";
			}
			str1+="</dataset>";
			str3+="</dataset>";
			strInfo=str1+str3;
			
		}else if(capTitle=='干线车时效'){
			var str1="<dataset seriesName='达标率' color='2C7DDA'>";
			var str3="<dataset seriesName='管理标准' parentYAxis='S'>";
			for(var i=0;i<newstore.getCount();i++){
				var strMon=newstore.getAt(i).get('OPR_DATE');
				strCate+="<category label='"+strMon+"'/>";
				var v = newstore.getAt(i).get('TRUEFINISH')==null?'0':newstore.getAt(i).get('TRUEFINISH');
				var v2 = newstore.getAt(i).get('MANAGERTARGET');
				
				str1+="<set value='"+(v==null?0:v)+"'/>";
				str3+="<set value='"+(v2==null?0:v2)+"'/>";
			}
			str1+="</dataset>";
			str3+="</dataset>";
			strInfo=str1+str3;
		}
			strCate+='</categories>';
			strDataSet=strInfo;
			//alert(strInfo);
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
        		var searchKpiName=Ext.getCmp('searchKpiName').getValue();
        		var div =document.getElementById('southDiv');
				while(div.hasChildNodes()){ //当div下还存在子节点时 循环继续
       				div.innerHTML = "";   
   				}
   				var querystore=new Ext.data.Store({});
        		if(null!=searchKpiName && searchKpiName.length>1){
        			dateStore.filter('KPINAME',searchKpiName);
        			querystore.add(dateStore.getRange());
        			dateStore.clearFilter();
        			// querystore.sort("KPIDATE","DESC");
   					if(querystore.getCount()>0){
   						if('进港时效'==searchKpiName){
   							newPictrue(querystore,searchKpiName,null);
   						}else{
   							newPictrue(querystore,searchKpiName,gz);
   							newPictrue(querystore,searchKpiName,sz);
   						}
   					}
        		}else{
        			for(var i=0;i<kpiStore.getCount();i++){
        				var kpiName = kpiStore.getAt(i).get('typeName');
        				//alert(kpiName);
        				dateStore.filter('KPINAME',kpiName);
        				querystore.add(dateStore.getRange());
        				dateStore.clearFilter();
        				// querystore.sort("KPIDATE","DESC");
        				//alert(querystore.getCount()+"   "+dateStore.getCount());
        				if(querystore.getCount()>0){
   							if('进港时效'==kpiName){
	   							newPictrue(querystore,kpiName,null);
	   						}else{
	   							newPictrue(querystore,kpiName,gz);
	   							newPictrue(querystore,kpiName,sz);
	   						}
   						}
   					}
   				}
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
            {header:'KPI名称',dataIndex:"KPINAME",width:colWidth,sortable:true},
			{header:'操作方式',dataIndex:'OPERATETYPE',width:colWidth,sortable:true},
			{header:'总数',dataIndex:'TOTALNUM',width:colWidth,sortable:true},
			{header:'合格数',dataIndex:'QUALIFIEDNUM',width:colWidth,sortable:true},
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
			{header:'统计纬度',dataIndex:'COUNTRANGE',width:colWidth,sortable:true},
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
 	if(!Ext.getCmp('searchKpiName').isValid()){
 		Ext.Msg.alert(alertTitle,'必须选择KPI名称!');
		return;
 	}
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
	var searchKpiName=Ext.getCmp('searchKpiName').getValue();
	if(startCount=='开始时间'){
		startCount='';
	}
	if(endCount=='结束时间'){
		endCount='';
	}
	var searchOperationType = Ext.get('searchOperationType').dom.value;
 	 Ext.apply(dateStore.baseParams, {
 	 	filter_countRange:searchCountRange,
 	 	filter_startCount:startCount,
 	 	filter_endCount:endCount,
 	 	filter_countCheckItems:countCheckItems,
 	 	filter_dutyDepartId:dutyDepartId,
 	 	filter_parentDepartId:parentDepartId,
 	 	filter_kpiName:searchKpiName,
 	 	filter_checkHour:checkHour,
 	 	filter_startHour:startHour,
 	 	filter_endHour:endHour,
 	 	filter_operateType:searchOperationType,
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
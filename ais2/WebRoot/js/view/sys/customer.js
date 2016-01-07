//var pageSize = 20;
var privilege=61;
var gridSearchUrl="sys/customerAction!ralaList.action";
var saveUrl="sys/customerAction!save.action";
var delUrl="sys/customerAction!delete.action";
 var dateStore,customertypeStore,isProjectcustomerStore,developLevelStore;

var  fields=[{name:"id",mapping:'id'},
			{name:"cusCode",mapping:'cusCode'},
     		{name:"cusName",mapping:'cusName'},
     		{name:'cusAdd',mapping:'cusAdd'},
     		{name:'custprop',mapping:'custprop'},
     		{name:'custshortname',mapping:'custshortname'},
     		{name:'email',mapping:'email'},
     		{name:'engname',mapping:'engname'},
     		{name:'fax1',mapping:'fax1'},
     		{name:'fax2',mapping:'fax2'},
     		{name:'legalbody',mapping:'legalbody'},
     		{name:'linkman1',mapping:'linkman1'},
     		{name:'linkman2',mapping:'linkman2'},
     		{name:'linkman3',mapping:'linkman3'},
     		{name:'memo',mapping:'memo'},
     		{name:'phone1',mapping:'phone1'},
     		{name:'phone2',mapping:'phone2'},
     		{name:'phone3',mapping:'phone3'},
     		{name:'pkAreacl',mapping:'pkAreacl'},
     		{name:'pkCubasdoc1',mapping:'pkCubasdoc1'},
     		{name:'trade',mapping:'trade'},
     		{name:'url',mapping:'url'},
     		{name:'createName',mapping:'createName'},
     		{name:'createTime',mapping:'createTime'},
     		{name:'updateName',mapping:'updateName'},
     		{name:'updateTime',mapping:'updateTime'},
     		{name:'bankNumber',mapping:'bankNumber'},
     		{name:'settlement',mapping:'settlement'},
     		{name:'createBank',mapping:'createBank'},
     		{name:'isProjectcustomer',mapping:'isProjectcustomer'},
     		{name:'ts',mapping:'ts'},'status'];

	
	var tbar=[{text:'<b>导出</b>',iconCls:'sort_down',handler:function() {
                	parent.exportExl(Ext.getCmp('carCenter'));
        } },'-',
            {text:'<b>新增</b>',iconCls:'userAdd',id:'basCarAdd',tooltip : '新增客商信息',handler:function() {saveCar(null,"save");}},'-',
            {text:'<b>修改</b>',iconCls:'userEdit',id:'basCarEdit',disabled:true,tooltip : '修改客商信息',handler:function(){
	 			var car =Ext.getCmp('carCenter');
				var _records = car.getSelectionModel().getSelections();//获取所有选中行
					
					if (_records.length < 1) {
						Ext.Msg.alert("系统消息", "请选择您要修改的行！");
						return false;
					} else if (_records.length > 1) {
						Ext.Msg.alert("系统消息", "一次只能修改一行！");
						return false;
					}else{
						if(_records[0].data.custprop =='发货代理'){
							Ext.Msg.alert(alertTitle,'发货代理请尝试到客户关系管理进行修改!');
							return false;
						}else{
							saveCar(_records[0].data.id,"update");
						}
					}}},'-',{text:'<b>查看</b>',iconCls:'userEdit',id:'showcusbtn',disabled:true,tooltip : '查看客商信息',handler:function(){
		 			var car =Ext.getCmp('carCenter');
					var _records = car.getSelectionModel().getSelections();//获取所有选中行
						
						if (_records.length < 1) {
							Ext.Msg.alert("系统消息", "请选择您要修改的行！");
							return false;
						} else if (_records.length > 1) {
							Ext.Msg.alert("系统消息", "一次只能修改一行！");
							return false;
						}else{
							saveCar(_records[0].data.id,"show");
						}}}/*,'-',
            {text:'<b>删除</b>',iconCls:'userDelete',id:'basCarDelete',disabled:true,tooltip : '删除客商信息',handler:function(){
	 		var car =Ext.getCmp('carCenter');
			var _records = car.getSelectionModel().getSelections();//获取所有选中行
					if (_records.length < 1) {
						Ext.Msg.alert("系统消息", "请选择您要操作的行！");
						return false;
					} 
					Ext.Msg.confirm("系统提示", "确定要删除所选的记录吗？", function(btnYes) {
								if (btnYes == 'yes' || btnYes == 'ok'
										|| btnYes == true) {
									var ids="";
									for(var i=0;i<_records.length;i++){
									
										 ids=ids+_records[i].data.id+',';
									}
									Ext.Ajax.request({
										url : sysPath+'/'
												+ delUrl,
										params : {
											ids : ids,
										    privilege:privilege
										},
										success : function(resp) {
											var respText = Ext.util.JSON.decode(resp.responseText);
											if(respText.success){
												Ext.Msg.alert(alertTitle,"删除成功!");
												dataReload();
											}else{
												Ext.Msg.alert(alertTitle,respText.msg);
											}
										}
									});
								}
							});
	 	}}*/,'-',{text:'<b>停用</b>',
	 			  iconCls:'sort_down',
	 			  handler:function() {
						var _records = Ext.getCmp('carCenter').getSelectionModel().getSelections();//获取所有选中行
						if (_records.length != 1) {
							Ext.Msg.alert(alertTitle, "请选择您要停用的客商！");
							return false;
						}else{
							Ext.Msg.confirm(alertTitle,'您确定要停用这个客商吗?',function(btnYes){
								if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true){
									var ids = "";
									for(var i = 0; i < _records.length; i++) {
										ids += _records[i].data.id + ",";
									}
									Ext.getCmp('formSumit').getForm().doAction('submit', {
										url : sysPath+ "/sys/customerAction!deleteStatus.action",
										method : 'post',
										params : {
											ids:ids,
											privilege:privilege
										},
										waitMsg : '正在保存数据...',
										success : function(form1, action) {
											Ext.Msg.alert(alertTitle,action.result.msg,
													function() {
														dateStore.reload();
													});
										},
										failure : function(form1, action) {
											Ext.Msg.alert(alertTitle,action.result.msg);
										}
									});
								}
							});
						}
		          }
		          },'-',
	 	{xtype:'textfield',blankText:'查询数据',id:'searchContent',
	 	enableKeyEvents : true,listeners : {
				keyup : function(textField, e) {
					if (e.getKey() == 13) {
						searchCar();
					}
				}
			}},
	 	'-',{
    					xtype : "combo",
    					width : 100,
    					triggerAction : 'all',
    					
    					model : 'local',
    					hiddenId : 'checkItems',
    					hiddenName : 'checkItems',
    					name : 'checkItemstext',
    					store : [['', '查询全部'],
    							//['EQS_id', 'ID'],
    							['LIKES_cusCode', '客商编号'],
    							['LIKES_cusName','客商名称'],
    							['LIKES_cusAdd','客商地址'],
    							['LIKES_email','客商EMAIL'],
    							['LIKES_custshortname','客商简称'],
    							['LIKES_legalbody','法人'],
    							['LIKES_trade','所属行业'],
    							['LIKES_createBank','开户银行'],
    							['LIKES_bankNumber','银行账号'],
    							['LIKES_pkAreacl','地区分类'],
    							['LIKES_custprop','客商类型']],
    					emptyText : '选择类型',
    					forceSelection : true,
    					listeners : {
    						'select' : function(combo, record, index) {
    							if (combo.getValue() == '') {
    								Ext.getCmp("searchContent").disable();
    								Ext.getCmp("searchContent").show();
    								Ext.getCmp("searchContent").setValue("");
    							} else {
    								Ext.getCmp("searchContent").enable();
    								Ext.getCmp("searchContent").show();
    								
    							}
    						}
    					}
    					
    				},'-',{text : '<b>搜索</b>',id : 'btn',iconCls : 'btnSearch',
    					handler : searchCar
    				}/*,
    	/*{text : '<b>高级查询</b>',iconCls : 'btnSearch',
    					handler : searchCar
    				}*/			
	 	];		
Ext.onReady(function() {
	var formSumit = new Ext.form.FormPanel({
		id : 'formSumit',
		frame : true,
		width : 100,
		cls : 'displaynone',
		hidden : true,
		items : [],
		buttons : []
	});
	formSumit.render(document.body);
	//开发阶段Store
	developLevelStore=new Ext.data.Store({
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
	
	//客商类型
	customertypeStore=new Ext.data.Store({
		storeId:"customertypeStore",
		proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/dictionaryAction!ralaList.action"}),
		baseParams:{filter_EQL_basDictionaryId:291,privilege:16},
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
        	{name:'id'},
        	{name:'custprop',mapping:'typeName'}
        	])
	});
	customertypeStore.load();

		//客商类型
	settlementStore=new Ext.data.Store({
		storeId:"customertypeStore",
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
	settlementStore.load();
	
	
   	dateStore = new Ext.data.Store({
        storeId:"dateStore",
        baseParams:{limit:pageSize,privilege : privilege},
        proxy: new Ext.data.HttpProxy({
        			url : sysPath+ "/"+gridSearchUrl
        	}),
        reader: new Ext.data.JsonReader({
            root: 'result', totalProperty: 'totalCount'
        }, fields)
    });
   
    // 是否项目客户
	isProjectcustomerStore = new Ext.data.SimpleStore({
				auteLoad : true, // 此处设置为自动加载
				data : [[0, '否'], [1, '是']],
				fields : ["isProjectcustomer", "isProjectcustomerName"]
			});
					
    var sm = new Ext.grid.CheckboxSelectionModel();
	  var rowNum = new Ext.grid.RowNumberer({
        header:'序号', width:25, sortable:true
    });
    var carGrid = new Ext.grid.GridPanel({
        renderTo: Ext.getBody(),
        id:'carCenter',
        height:Ext.lib.Dom.getViewHeight(),		
        width:Ext.lib.Dom.getViewWidth(),
        viewConfig : {
			columnsText : "显示的列",
			sortAscText : "升序",
			sortDescText : "降序",
			forceFit : true
		},
		autoScroll:true, autoExpandColumn:1,
        frame:true, 
        loadMask:true,
        sm:sm,
        cm:new Ext.grid.ColumnModel([rowNum,sm,
		        {header: 'id', dataIndex: 'id',sortable : true,hidden:true},
       			{header: '客商名称', dataIndex: 'cusName',width:60,sortable : true},
        		{header: '客商地址', dataIndex: 'cusAdd',width:60,sortable : true},
 				{header: '客商类型', dataIndex: 'custprop',width:60,sortable : true},
 				{header: '客商简称', dataIndex: 'custshortname',width:60,sortable : true},
 				{header: 'e-mail地址', dataIndex: 'email',width:60,sortable : true},
 				{header: '外文名称', dataIndex: 'engname',width:60,sortable : true},
 				{header: '传真1', dataIndex: 'fax1',width:60,sortable : true,hidden:true},
 				{header: '传真2', dataIndex: 'fax2',width:60,sortable : true,hidden:true},
 				{header: '法人', dataIndex: 'legalbody',width:60,sortable : true},
 				{header: '联系人1', dataIndex: 'linkman1',width:60,sortable : true,hidden:true},
 				{header: '联系人2', dataIndex: 'linkman2',width:60,sortable : true,hidden:true},
 				{header: '联系人3', dataIndex: 'linkman3',width:60,sortable : true,hidden:true},
 				{header: '备注', dataIndex: 'memo',width:60,sortable : true,hidden:true},
 				{header: '电话1', dataIndex: 'phone1',width:60,sortable : true,hidden:true},
 				{header: '电话2', dataIndex: 'phone2',width:60,sortable : true,hidden:true},
 				{header: '电话3', dataIndex: 'phone3',width:60,sortable : true,hidden:true},
		        {header: '客商编码', dataIndex: 'cusCode',width:60,sortable : true},
 				{header: '地区分类', dataIndex: 'pkAreacl',width:60,sortable : true},
 				{header: '客商总公司编码', dataIndex: 'pkCubasdoc1',width:60,sortable : true},
 				{header: '所属行业', dataIndex: 'trade',width:60,sortable : true},
 				{header: 'web网址', dataIndex: 'url',width:60,sortable : true,hidden:true},
 				{header: '创建人', dataIndex: 'createName',width:60,sortable : true,hidden:true},
 				{header: '创建时间', dataIndex: 'createTime',width:60,sortable : true,hidden:true},
 				{header: '修改人', dataIndex: 'updateName',width:60,sortable : true,hidden:true},
 				{header: '修改时间', dataIndex: 'updateTime',width:60,sortable : true,hidden:true},
 				{header: '银行账号', dataIndex: 'bankNumber',width:60,sortable : true},
 				{header: '开户行', dataIndex: 'createBank',width:60,sortable : true},
 				{header: '结算方式', dataIndex: 'settlement',width:60,sortable : true},
 				{header: '状态', dataIndex: 'status',width:60,sortable : true,renderer:function(v,metaData){
					return v==1?'正常':'停用';
				}},
 				{header: '是否项目客户', dataIndex: 'isProjectcustomer',width:60,sortable : true,renderer:function(v,metaData){
				if(v==0){
					v='否';
				}else{
					v='是';
				}
				return v;
			}},
 				{header: '时间戳', dataIndex: 'ts',width:60,sortable : true,hidden:true,hideable:false}
        ]),
        store:dateStore,
        
        tbar: tbar,
        bbar: new Ext.PagingToolbar({
            pageSize: pageSize, 
            store: dateStore, 
            displayInfo: true,
            displayMsg: '显示第 {0} 条到 {1} 条记录，共 {2} 条', 
            emptyMsg: "没有记录信息显示"
        })
    });
    carGrid.render();
    //dateStore.load();
	
	carGrid.getStore().on('load',function(s,records){ 
        var girdcount=0; 
        s.each(function(r){ 
            if(r.get('status')==0){ 
                carGrid.getView().getRow(girdcount).style.backgroundColor='#BFBFC1';
            }
            girdcount=girdcount+1; 
        }); 
	}); 
	
    carGrid.on('click', function() {
        var _record = carGrid.getSelectionModel().getSelections();//获得所有选中的行
        var updatebtn = Ext.getCmp('basCarEdit');//获得更新按钮
        var deletebtn = Ext.getCmp('basCarDelete');//获得删除按钮
        var showbtn = Ext.getCmp('showcusbtn');
         if (_record.length==1) {       	
            if(updatebtn){
            	updatebtn.setDisabled(false);
            }
            if(deletebtn){
            	deletebtn.setDisabled(false);
            }
            if(showbtn){
            	showbtn.setDisabled(false);
            }
        }else if(_record.length>1){
        	 if(updatebtn){
            	updatebtn.setDisabled(true);
            }
            if(deletebtn){
            	deletebtn.setDisabled(false);
            }
            if(showbtn){
            	showbtn.setDisabled(true);
            }
        } else {
            if(updatebtn){
            	updatebtn.setDisabled(true);
            }
			if(deletebtn){
            	deletebtn.setDisabled(true);
			}
			
			if(showbtn){
            	showbtn.setDisabled(true);
            }
        }

    });
    });
    
	
   function searchCar() {
		/*dateStore.proxy = new Ext.data.HttpProxy({
					method : 'POST',
					url : sysPath
								+ "/"+gridSearchUrl,
						params:{privilege:privilege}
				});
*/
		dateStore.baseParams = {
			limit:pageSize,
			privilege : privilege,
			checkItems : Ext.get("checkItems").dom.value,
			itemsValue : Ext.get("searchContent").dom.value
		}
		var userrolebtn = Ext.getCmp('basCarAdd');
		var updatebtn = Ext.getCmp('basCarEdit');

		userrolebtn.setDisabled(false);
		updatebtn.setDisabled(false);

		dataReload();
		
	}
	
function saveCar(cid,type) {
					var form = new Ext.form.FormPanel({
								labelAlign : 'right',
								frame : true,
								bodyStyle : 'padding:5px 5px 0',
								width : 650,
								labelWidth: 70,
								reader : new Ext.data.JsonReader({root: 'result', totalProperty: 'totalCount'}, fields),
						items : [{
											layout : 'column',
											labelAlign : 'right',
											items : [{
														columnWidth : .33,
														layout : 'form',
														
														items : [{
																	xtype : 'hidden',
																	fieldLabel : 'id',
																	name : 'id'
																},{
																	name : "ts",
																	xtype : "hidden"
																},{
																	xtype : 'textfield',
																	fieldLabel : '客商编码',
																	name : 'cusCode',
																	maxLength:10,
																	disabled:true,
																	allowBlank : false,
																	blankText : "客商编码不能为空！",
																	anchor : '95%'
																},{
																	xtype : 'textfield',
																	fieldLabel : '总公司编码',
																	name : 'pkCubasdoc1',
																	maxLength:10,
																	allowBlank : true,
																	anchor : '95%'
																},{
																	xtype : 'textfield',
																	fieldLabel : '所属行业',
																	name : 'trade',
																	maxLength:20,
																	allowBlank : false,
																	blankText : "所属行业不能为空！",
																	anchor : '95%'
																},{
																	xtype : 'textfield',
																	labelAlign : 'left',
																	
																	fieldLabel : '银行帐号',
																	name : 'bankNumber',
																	maxLength:50,
																	allowBlank : false,
																	blankText : "银行帐号不能为空！",
																	anchor : '95%'
																},{
																	xtype : 'textfield',
																	fieldLabel : '联系人1',
																	name : 'linkman1',
																	maxLength:50,
																	allowBlank : false,
																	blankText : "联系人1不能为空！",
																	anchor : '95%'
																},{
																	xtype : 'textfield',
																	labelAlign : 'left',
																	//regex: /(^[0-9]{3,4}\-[0-9]{7,8}$)|(^[0-9]{7,8}$)|(^\([0-9]{3,4}\)[0-9]{3,8}$)|(^0{0,1}13[0-9]{9}$)/, 
																	fieldLabel : '电话1',
																	name : 'phone1',
																	maxLength:13,
																	allowBlank : false,
																	blankText : "电话1不能为空！",
																	//regexText: '请输入正确的电话号码！',
																	anchor : '95%'
																},{
																	xtype : 'textfield',
																	fieldLabel : '传真1',
																	name : 'fax1',
																	maxLength:20,
																	allowBlank : false,
																	blankText : "传真1不能为空！",
																	anchor : '95%'
																},{
																	xtype : 'textfield',
																	fieldLabel : '外文名称',
																	name : 'engname',
																	maxLength:100,
																	allowBlank : true,
																	anchor : '95%'
																},{
																	xtype : 'textfield',
																	fieldLabel : '区域',
																	name : 'area',
																	maxLength:25,
																	maxLengthText:'区域不能多于25个字符',
																	allowBlank : true,
																	anchor : '95%'
																},{
																	xtype : 'textfield',
																	fieldLabel : '财务状态',
																	name : 'financialPositon',
																	maxLength:25,
																	maxLengthText:'财务状态不能多于25个字符',
																	allowBlank : true,
																	anchor : '95%'
																}]

													},{
														columnWidth : .33,
														layout : 'form',
														items : [{
																	xtype : 'textfield',
																	fieldLabel : '客商名称',
																	name : 'cusName',
																	maxLength:200,
																	allowBlank : false,
																	blankText : "客商名称不能为空！",
																	anchor : '95%'
																},{
									                           		xtype : 'combo',
									    							typeAhead : true,
									    							forceSelection : true,
									    							queryParam : 'filter_LIKES_typeName',
									    							minChars : 0,
									    							fieldLabel : '客商类型<span style="color:red">*</span>',
									    							store : customertypeStore,
									    							triggerAction : 'all',
									    							id : 'custpropId',
									    							valueField : 'custprop',
									    							displayField : 'custprop',
									    							hiddenName : 'custprop',
									    							emptyText : "请输入客商类型",
									    							allowBlank : false,
									    							blankText : "客商类型不能为空！",
									    							anchor : '95%'
									                           }/*,{
																	xtype : 'textfield',
																	fieldLabel : '客商类型',
																	name : 'custprop',
																	maxLength:10,
																	allowBlank : false,
																	blankText : "客商类型不能为空！",
																	anchor : '95%'
																}*/,{
																	xtype : 'textfield',
																	fieldLabel : '法人',
																	name : 'legalbody',
																	maxLength:10,
																	allowBlank : false,
																	blankText : "法人不能为空！",
																	anchor : '95%'
																},{
																	xtype : 'textfield',
																	fieldLabel : '开户行',
																	name : 'createBank',
																	maxLength:50,
																	allowBlank : false,
																	blankText : "开户行不能为空！",
																	anchor : '95%'
																},{
																	xtype : 'textfield',
																	fieldLabel : '联系人2',
																	name : 'linkman2',
																	maxLength:50,
																	allowBlank : true,
																	anchor : '95%'
																},{
																	xtype : 'textfield',
																	labelAlign : 'left',
																	fieldLabel : '电话2',
																	name : 'phone2',
																	maxLength:13,
																	allowBlank : true,
																	anchor : '95%'
																},{
																	xtype : 'textfield',
																	fieldLabel : '传真2',
																	name : 'fax2',
																	maxLength:20,
																	allowBlank : true,
																	anchor : '95%'
																}, {
																	xtype : 'combo',
																	triggerAction : 'all',
																	store : isProjectcustomerStore,
																	allowBlank : false,
																	emptyText : "请选择是否项目客户",
																	forceSelection : true,
																	fieldLabel : '项目客户',
																	editable : false,
																	mode : "local",// 获取本地的值
																	displayField : 'isProjectcustomerName',// 显示值，与fields对应
																	valueField : 'isProjectcustomer',// value值，与fields对应
																	hiddenName:'isProjectcustomer',
																	name : 'isProjectcustomer',
																	anchor : '95%'
																},{
																	xtype : "combo",
																	triggerAction : 'all',
																	name:'developLevel',
																	id:'developLevel',
																	anchor : '95%',
																	store:developLevelStore,
																	valueField:'developLevel',
																	displayField:'developLevel',
																	model:'local',
																	allowBlank:true,
																	fieldLabel : '开发阶段'
																}
																]

													},{
														columnWidth : .33,
														layout : 'form',
														items : [{
																	xtype : 'textfield',
																	fieldLabel : '客商地址',
																	name : 'cusAdd',
																	maxLength:200,
																	allowBlank : false,
																	blankText : "客商地址不能为空！",
																	anchor : '95%'
																},{
																	xtype : 'textfield',
																	fieldLabel : '客商简称',
																	name : 'custshortname',
																	maxLength:100,
																	allowBlank : false,
																	blankText : "客商简称不能为空！",
																	anchor : '95%'
																},{
																	xtype : 'textfield',
																	fieldLabel : 'E-mail',
																	name : 'email',
																	maxLength:200,
																	allowBlank : false,
																	vtype:"email",
																	vtypeText:"不是有效的邮箱地址",
																	anchor : '95%'
																},{
																	xtype : 'textfield',
																	fieldLabel : '地区分类',
																	name : 'pkAreacl',
																	maxLength:5,
																	allowBlank : false,
																	vtypeText:"地区分类不能为空",
																	anchor : '95%'
																},{
																	xtype : 'textfield',
																	fieldLabel : '联系人3',
																	name : 'linkman3',
																	maxLength:50,
																	allowBlank : true,
																	anchor : '95%'
																},{
																	xtype : 'textfield',
																	labelAlign : 'left',
																	fieldLabel : '电话3',
																	name : 'phone3',
																	maxLength:13,
																	allowBlank : true,
																	anchor : '95%'
																},{
																	xtype : 'textfield',
																	fieldLabel : 'web网址',
																	name : 'url',
																	maxLength:50,
																	allowBlank : true,
																	anchor : '95%'
																},{
									                           		xtype : 'combo',
									    							typeAhead : true,
									    							forceSelection : true,
									    							queryParam : 'filter_LIKES_typeName',
									    							minChars : 0,
									    							fieldLabel : '结算方式<span style="color:red">*</span>',
									    							store : settlementStore,
									    							triggerAction : 'all',
									    							id : 'settlementId',
									    							valueField : 'settlement',
									    							displayField : 'settlement',
									    							hiddenName : 'settlement',
									    							emptyText : "请输入结算方式",
									    							allowBlank : false,
									    							blankText : "结算方式不能为空！",
									    							anchor : '95%'
									                           },{
																	xtype : 'textfield',
																	fieldLabel : '经营范围',
																	name : 'scopeBusiness',
																	maxLength:250,
																	maxLengthText:'区域不能多于250个字符',
																	allowBlank : true,
																	anchor : '95%'
																}]

													}]
									},{
											xtype : 'textarea',
											fieldLabel : '备注',
											name : 'memo',
											height:70,
											maxLength:1500,
											allowBlank : true,
											anchor : '95%'
										}]
									});
									
		carTitle='添加客商信息';
		if(cid!=null){
			if(type=='show'){
				carTitle='查看客商信息';
			}else{
				carTitle='修改客商信息';
			}
			form.load({
			url : sysPath
					+ "/"+gridSearchUrl,
			params:{filter_EQL_id:cid,privilege:privilege}
		});
		}
		
		var win = new Ext.Window({
		title : carTitle,
		width : 650,
		closeAction : 'hide',
		plain : true,
	resizable : false,

		
		modal : true,
		items : form,
		buttonAlign : "center",	buttons : [{
			text : "保存",
			id:'btnsave',
			iconCls : 'groupSave',
			handler : function() {
			
		var custprop=Ext.getCmp('custpropId').getValue();//客商类型
		var settlement=Ext.getCmp('settlementId').getValue();//结算方式
		if(custprop=="中转"&&settlement!="现结"){
			Ext.Msg.alert("友情提示","中转客商的结算方式只能为<现结>!");
			return;
		}
		
				if (form.getForm().isValid()) {
					this.disabled = true;//只能点击一次
					form.getForm().submit({
						url : sysPath
								+ '/'+saveUrl,
								params:{privilege:privilege},
						waitMsg : '正在保存数据...',
						success : function(form, action) {
							win.hide(), Ext.Msg.alert("友情提示",
									action.result.msg, function() {
										dataReload();
									});
						},
						failure : function(form, action) {
							if (action.faliureType == Ext.form.Action.SERVER_INVALID) {
								Ext.Msg.alert("友情提示", "服务器连接异常，请联系管理员！");
							} else {
								if (action.result.msg) {
									win.hide();
									Ext.Msg.alert("友情提示",
											action.result.msg, function() {
												dataReload();
											});

								}
							}
						}
					});
			}
			}
		}, {
						
				text : "重置",
				iconCls : 'refresh',
				handler : function() {
					
					
					if(cid==null){
						
						form.getForm().reset();
					
					}
					if(cid!=null){
						carTitle='修改客商信息';
						form.load({
						url : sysPath
								+ "/"+gridSearchUrl,
						params:{filter_EQL_id:cid,privilege:privilege}
					})
								
		}
							}
						}, {
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
		if(type =='show'){
			Ext.getCmp('btnsave').setDisabled(true);
		}else{
			Ext.getCmp('btnsave').setDisabled(false);
		}
		
 }
 function dataReload(){
			dateStore.reload();
 }

//var pageSize = 2;
var comboxPage = 15;
var privilege = 111;
var departGridSearchUrl = "sys/departAction!findAll.action";
var responsibleGridSearchUrl = "user/userAction!ralaList.action?privilege=23"; // 负责人
var gridSearchUrl = "fi/fiCapitaaccountAction!ralaList.action";
var saveUrl = "fi/fiCapitaaccountAction!save.action";
var delUrl = "fi/fiCapitaaccountAction!delete.action";
var dateStore,departStore,accountTypeStore,paymentTypeStore,responsibleStore,isDeleteStore;

var sm = new Ext.grid.CheckboxSelectionModel();
var rowNum = new Ext.grid.RowNumberer({
		header : '序号',
		width : 25,
		sortable : true
	});
var fields = [{
			name : "id",
			mapping : 'id'
		}, {
			name : 'paymentTypeName',// 收支类型
			mapping : 'paymentTypeName'
		}, {
			name : 'accountTypeName',// 账号类型
			mapping : 'accountTypeName'
		}, {
			name : 'departName',// 所属部门名称
			mapping : 'departName'
		}, {
			name : 'departId',// 所属部门ID
			mapping : 'departId'
		}, {
			name : 'accountNum',// /账号
			mapping : 'accountNum'
		}, {
			name : 'accountName',// 账号名称
			mapping : 'accountName'
		}, {
			name : 'bank',// 开户行
			mapping : 'bank'
		}, {
			name : 'borrow',// 借
			mapping : 'borrow'
		}, {
			name : 'loan',//贷
			mapping : 'loan'
		}, {
			name : 'balance',// 余额
			mapping : 'balance'
		}, {
			name : "sourceData", // 数据来源
			mapping : 'sourceData'
		}, {
			name : "sourceNo", //来源单号
			mapping : 'sourceNo'
		}, {
			name : 'remark',// 摘要
			mapping : 'remark'
		}, {
			name : 'responsible',// 负责人
			mapping : 'responsible'
		}, {
			name : 'createName',
			mapping : 'createName'
		}, {
			name : 'createTime',
			mapping : 'createTime'
		}, {
			name : 'updateName',
			mapping : 'updateName'
		}, {
			name : 'updateTime',
			mapping : 'updateTime'
		}, {
			name : 'ts',
			mapping : 'ts'
		}];
var cm=new Ext.grid.ColumnModel([rowNum, sm, {
									header : 'id',
									dataIndex : 'id',
									hidden : true
								}, {
									header : '所属部门',
									dataIndex : 'departName',
									width : 120
								},{
									header : '收支类型',
									dataIndex : 'paymentTypeName',
									width : 60
								},{
									header : '账号类型',
									dataIndex : 'accountTypeName',
									width : 80
								}, {
									header : '借方金额',
									dataIndex : 'loan',
									width : 60
								}, {
									header : '贷方金额',
									dataIndex : 'borrow',
									width : 60
								}, {
									header : '余额',
									dataIndex : 'balance',
									width : 60
								}, {
									header : '数据来源',
									dataIndex : 'sourceData',
									width:80
								}, {
									header : ' 来源单号',
									dataIndex : 'sourceNo',
									width:65
								}, {
									header : '备注',
									dataIndex : 'remark',
									width : 100
								}, {
									header : '账号名称',
									dataIndex : 'accountName',
									width : 60
								}, {
									header : '账号',
									dataIndex : 'accountNum',
									width : 60
								},{
									header : '开户行',
									dataIndex : 'bank',
									width : 60
								}, {
									header : '账号负责人',
									dataIndex : 'responsible',
									width : 80
								}, {
									header : '创建人',
									dataIndex : 'createName',
									width : 60,
									hidden : true
								}, {
									header : '创建时间',
									dataIndex : 'createTime',
									sortable : true,
									width : 120
								}, {
									header : '修改人',
									dataIndex : 'updateName',
									width : 60,
									hidden : true
								}, {
									header : '修改时间',
									dataIndex : 'updateTime',
									width : 60,
									hidden : true
								}, {
									header : '时间戳',
									dataIndex : 'ts',
									width : 60,
									sortable : true,
									hidden : true,
									hideable:false
								}]);

var tbar = [{text:'<b>导出</b>',iconCls:'sort_down',handler:function() {
                	parent.exportExl(Ext.getCmp('carCenter'));
        } },'-', {
			text : '<b>打印</b>',
			iconCls : 'table',
			id : 'printbtn',
			tooltip : '打印',
			handler : printinfo
		}, '-', {
			text : '<b>搜索</b>',
			id : 'btn',
			iconCls : 'btnSearch',
			handler : searchmain
		}];

	//账号
	var numStore = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({url:sysPath+"/fi/fiCapitaaccountsetAction!list.action",method:'post'}),
            baseParams:{
             	privilege:20,
             	filter_EQL_isDelete:1
            },
            reader: new Ext.data.JsonReader(
            	{
            		root: 'result', totalProperty: 'totalCount'
          		}, [{name:'accountNum', mapping:'accountNum',type:'string'},        
               		 {name:'id', mapping:'id'}
              		])    
	});

	var peopleStore = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({url:sysPath+"/fi/fiCapitaaccountsetAction!list.action",method:'post'}),
            baseParams:{
             	privilege:20,
             	filter_EQL_isDelete:1
            },
            reader: new Ext.data.JsonReader(
            	{
            		root: 'result', totalProperty: 'totalCount'
          		}, [{name:'responsible', mapping:'responsible',type:'string'},        
               		 {name:'id', mapping:'id'}
              		])    
	});
Ext.onReady(function() {

	// 账号类型
	accountTypeStore = new Ext.data.Store({
				storeId : "accountTypeStore",
				proxy : new Ext.data.HttpProxy({
							url : sysPath
									+ "/sys/dictionaryAction!ralaList.action"
						}),
				baseParams : {
					filter_EQL_basDictionaryId : 103,
					privilege : 16
				},
				reader : new Ext.data.JsonReader({
							root : 'result',
							totalProperty : 'totalCount'
						}, [{
								name : 'id',
								mapping : 'id'
							}, {
								name : 'accountTypeName',
								mapping : 'typeName'
							}])
			});
			
	// 收支类型
	paymentTypeStore = new Ext.data.Store({
				storeId : "paymentTypeStore",
				proxy : new Ext.data.HttpProxy({
							url : sysPath
									+ "/sys/dictionaryAction!ralaList.action"
						}),
				baseParams : {
					filter_EQL_basDictionaryId : 104,
					privilege : 16
				},
				reader : new Ext.data.JsonReader({
							root : 'result',
							totalProperty : 'totalCount'
						}, [{
								name : 'id',
								mapping : 'id'
							}, {
								name : 'paymentTypeName',
								mapping : 'typeName'
							}])
			});
					
	//权限部门
	var departStore = new Ext.data.Store({ 
            storeId:"departStore",                        
            proxy: new Ext.data.HttpProxy({url:sysPath+"/sys/basRightDepartAction!ralaList.action",method:'post'}),
            baseParams:{
            	privilege:63,
            	filter_EQL_userId:userId
            },
            reader: new Ext.data.JsonReader({
            root: 'result', totalProperty: 'totalCount'
           }, [{name:'departName', mapping:'departName',type:'string'},
               {name:'departId', mapping:'rightDepartid',type:'string'}             
              ])    
    });

	// 查询主列表
	dateStore = new Ext.data.Store({
				storeId : "dateStore",
				baseParams:{limit:pageSize,privilege : privilege},
				proxy : new Ext.data.HttpProxy({
							url : sysPath + "/" + gridSearchUrl
						}),
				reader : new Ext.data.JsonReader({
							root : 'result',
							totalProperty : 'totalCount'
						}, fields)
			});

	var mainGrid = new Ext.grid.GridPanel({
				// renderTo : Ext.getBody(),
				region : "center",
				id : 'carCenter',
				height : Ext.lib.Dom.getViewHeight(),
				width : Ext.lib.Dom.getViewWidth(),
				viewConfig : {
					columnsText : "显示的列",
					sortAscText : "升序",
					sortDescText : "降序",
					//scrollOffset: 0,
					autoScroll:true
				},
				autoScroll : true,
				frame : true,
				loadMask : true,
				sm : sm,
				cm : cm,
				ds : dateStore,
				// tbar : tbar,
				bbar : new Ext.PagingToolbar({
							pageSize : pageSize,
							store : dateStore,
							displayInfo : true,
							displayMsg : '显示第 {0} 条到 {1} 条记录，共 {2} 条',
							emptyMsg : "没有记录信息显示"
						})
			});

	// 布局
	var mainpanel = new Ext.Panel({
				// title : "问题账款",
				id : 'view',
				el : 'mainGrid',
				labelAlign : 'left',
				height : Ext.lib.Dom.getViewHeight()-4,
				width : Ext.lib.Dom.getViewWidth(),
				bodyStyle : 'padding:1px',
				layout : "border",
				tbar : tbar,
				frame : false,
				items : [mainGrid]
			});

	mainpanel.on('render', function() {
		var tbarsearch = new Ext.Toolbar({
			items : ['流水日期', {
						xtype : 'datefield',
						format : 'Y-m-d',
						width : 82,
						value : new Date().add(Date.DAY, -7),
						blankText : "[流水日期从]不能为空！",
						hiddenId : 'stateDate',
						hiddenName : 'stateDate',
						id : 'stateDate'
					}, '至', {
						xtype : 'datefield',
						format : 'Y-m-d',
						width : 82,
						value : new Date(),
						blankText : "[流水日期从]不能为空！",
						hiddenId : 'endDate',
						hiddenName : 'endDate',
						id : 'endDate'
					}, '-', '所属部门', {
						xtype : 'combo',
						id:'departId',
						triggerAction : 'all',
						store : departStore,
						mode:'local',
						minChars : 1,
						listWidth:245,
						allowBlank : false,
						emptyText : "请选择部门名称",
						forceSelection : false,
						fieldLabel:'部门',
						editable : false,
						pageSize:500,
						displayField : 'departName',//显示值，与fields对应
						valueField : 'departId',//value值，与fields对应
						anchor : '95%',
						width : 80,
						enableKeyEvents:true,
						listeners : {
							afterRender:function(combo){
					 		departStore.load({
									params : {
										start : 0,
										limit : 500
									},callback :function(v){
										var flag=true;
										for(var i=0;i<departStore.getCount();i++){
											if(departStore.getAt(i).get("departId")==bussDepart){
												flag=false;
											}
										}
										if(flag){
											var store=new Ext.data.Record.create([{name:'departName'},{name:'departId'}]);
											var record=new store();
											record.set("departId",bussDepart);
											record.set("departName",bussDepartName);
											departStore.add(record);		
										}
											combo.setValue(bussDepart);
									}
							});}
						}
				}, '-', '账号类型', {
						xtype : 'combo',
						id : 'comboaccountType',
						triggerAction : 'all',
						store : accountTypeStore,
						emptyText : "请选择",
						forceSelection : true,
						editable : true,
						displayField : 'accountTypeName',// 显示值，与fields对应
						valueField : 'accountTypeName',// value值，与fields对应
						hiddenName:'accountTypeName',
						width : 60,
						anchor : '95%'
					}, '-','收支类型',{
					xtype : 'combo',
					id:'combopaymentType',
					typeAhead : true,
					forceSelection : true,
					queryParam : 'filter_LIKES_typeName',
					minChars : 0,
					store : paymentTypeStore,
					triggerAction : 'all',
					valueField : 'paymentTypeName',
					displayField : 'paymentTypeName',
					hiddenName : 'paymentTypeName',
					emptyText : "请选择",
					width:60
				}, '-', '负责人',{
					xtype : 'combo',
					id:'people2',
					hiddenId : 'people',
	    			hiddenName : 'people',
					triggerAction : 'all',
					hideTrigger:true,
					store : peopleStore,
					width:60,
					selectOnFocus:true,
					listWidth:245,
					allowBlank : true,
					minChars : 1,
					queryParam : 'filter_LIKES_responsible',
					forceSelection : false,
					editable : true,
					displayField : 'responsible',//显示值，与fields对应
					valueField : 'responsible',//value值，与fields对应
					name : 'responsible',
					anchor : '100%',
					pageSize:comboxPage,
					listeners : {
	
		 			}
				
		    }, '-', '账号',{xtype : 'combo',
				id:'num',
				hiddenId : 'toNamenum',
    			hiddenName : 'carId',
				triggerAction : 'all',
				hideTrigger:true,
				store : numStore,
				width:80,
				selectOnFocus:true,
				listWidth:245,
				allowBlank : true,
				minChars : 2,
				queryParam : 'filter_EQL_accountNum',
				forceSelection : false,
				editable : true,
				displayField : 'accountNum',//显示值，与fields对应
				valueField : 'accountNum',//value值，与fields对应
				name : 'accountNum',
				anchor : '100%',
				pageSize:comboxPage,
				listeners : {

	 			}
				
		    }]
		});
		tbarsearch.render(mainpanel.tbar);
	});

	mainpanel.render();
	mainpanel.doLayout();
	mainGrid.render();

	// 进入界面加载
	//dateStore.load({
	//			params : {
	//				privilege : privilege
	//			}
	//		});

	mainGrid.on('click', function() {
				var _record = mainGrid.getSelectionModel().getSelections();// 获得所有选中的行
				var updatebtn = Ext.getCmp('basCarEdit');// 获得更新按钮
				var deletebtn = Ext.getCmp('basCarDelete');// 获得删除按钮
				var reviewbtn = Ext.getCmp("basReview"); // 获得审核按钮

				if (_record.length == 1) {
					if (updatebtn) {
						updatebtn.setDisabled(false);
					}
					if (deletebtn) {
						deletebtn.setDisabled(false);
					}
					if (deletebtn) {
						reviewbtn.setDisabled(false);
					}
				} else if (_record.length > 1) {
					if (updatebtn) {
						updatebtn.setDisabled(true);
					}
					if (deletebtn) {
						deletebtn.setDisabled(false);
					}
				} else {
					if (updatebtn) {
						updatebtn.setDisabled(true);
					}
					if (deletebtn) {
						deletebtn.setDisabled(true);
					}
					if (reviewbtn) {
						reviewbtn.setDisabled(true);
					}
				}

			});
});

/**
 * 查询对账信息
 */
function searchmain() {
	dataReload();
}

function dataReload() {
	var stateDate = Ext.getCmp("stateDate").getValue().format('Y-m-d');
	var endDate = Ext.getCmp("endDate").getValue().format('Y-m-d');
	var departId = Ext.getCmp("departId").getValue(); // 所属部门

	var people = Ext.getCmp("people2").getRawValue(); //责任人
	var accountType = Ext.getCmp("comboaccountType").getValue();
	var paymentType = Ext.getCmp("combopaymentType").getValue();
	var num = Ext.getCmp("num").getValue(); // 账号


	Ext.apply(dateStore.baseParams, {
		filter_GED_createTime : stateDate,
		filter_LED_createTime : endDate,
		filter_LIKES_responsible:people,
		filter_LIKES_accountNum:num,
		filter_EQS_accountTypeName : accountType,
		filter_EQS_paymentTypeName : paymentType,
		filter_EQS_departId : departId,
		privilege:privilege
	});
	
	dateStore.reload({
				params : {
					start : 0
				}
			});
}


/**
 * 导出按钮事件
 */
function exportinfo(){
	Ext.Msg.alert("提示","正在开发中...");
}

/**
 * 打印按钮事件
 */
function printinfo(){
	Ext.Msg.alert("提示","正在开发中...");
}
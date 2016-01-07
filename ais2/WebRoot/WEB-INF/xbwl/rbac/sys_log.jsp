
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<jsp:include page="/common/common.jsp" />
	
    <script type="text/javascript" src="${pageContext.request.contextPath}/extjs3.1/ux/RowExpander.js"></script>
	
	<script type="text/javascript">
	Ext.QuickTips.init();

    ///非常重要必须设置（跟action对应）
    path="${pageContext.request.contextPath}/log";
	var pageSize = 20;
	
	var fields = [
			"id",
			"moduleName",
			"logType",
			"operAccount",
			"operStatus",
			"clientIp",
			"operDetail",
			"createdtime","invokeTime","startMem","endMem","startTotal","endTotal"];
			
			var sysLogStore = new Ext.data.JsonStore({
						root : "result",
						url : path+"/sysLog!list.action",
						totalProperty : "totalCount",
						fields : fields
			});
			
			var sysLogReader = new Ext.data.JsonReader({
				root:"result",
				totalProperty : "totalCount"
			},[
			    {name:"id",mapping:"id"},
			{name:"moduleName",mapping:"moduleName"},
			{name:"logType",mapping:"logType"},
			{name:"operAccount",mapping:"operAccount"},
			{name:"operDetail",mapping:"operDetail",convert: function(v){
				return v.replace(/<br>/g,"\n");
			}},
			{name:"operStatus",mapping:"operStatus"},
			{name:"clientIp",mapping:"clientIp"},
			{name:"createdtime",mapping:"createdtime"},
			{name:"invokeTime",mapping:"invokeTime"},
			{name:"startMem",mapping:"startMem"},
			{name:"endMem",mapping:"endMem"},
			{name:"startTotal",mapping:"startTotal"},
			{name:"endTotal",mapping:"endTotal"}
			]);
	
	

	Ext.onReady(function() {

	

		var sm = new Ext.grid.CheckboxSelectionModel({
					dataIndex : "id"
				});
				
		 var expander = new Ext.ux.grid.RowExpander({
			    	height:280,
			        tpl : '<div id="dno_{dno}" style="height:50px;" >{operDetail}</div>'
			       
			    }); 		

		var cm = new Ext.grid.ColumnModel([expander,sm,{
					header : "模块名称",
					dataIndex : "moduleName",
					sortable : true
				},{
					header : "日志类型",
					dataIndex : "logType",
					sortable : true
				},{
					header : "操作账号",
					dataIndex : "operAccount",
					sortable : true
				},{
					header : "操作状态",
					dataIndex : "operStatus",
					sortable : true
				},{
					header : "客户端IP",
					dataIndex : "clientIp",
					sortable : true
				},{
					header : "操作时间",
					dataIndex : "createdtime",
					sortable : true
				},{
					header : "方法执行时间(毫秒)",
					dataIndex : "invokeTime",
					sortable : true
				},{
					header : "方法执行开始内存(M)",
					dataIndex : "startMem",
					sortable : true
				},{
					header : "方法执行结束内存(M)",
					dataIndex : "endMem",
					sortable : true
				},{
					header : "方法执行总计内存(M)",
					dataIndex : "startTotal",
					sortable : true
				},{
					header : "方法执行结束内存(M)",
					dataIndex : "endTotal",
					sortable : true
				}
				
                            ]);
                            
                            

		var grid = new Ext.grid.GridPanel({
			id : "sysLogGridId",
			store : sysLogStore,
			sm : sm,
			cm : cm,
			el : 'sysLogGrid',
			height : Ext.lib.Dom.getViewHeight() - 1,
			width : Ext.lib.Dom.getViewWidth(),
			autoScroll : true,
			frame : false,
			plugins:expander,
			loadMask : true,
			stripeRows : true,
			viewConfig : {
				columnsText : "显示的列",
				sortAscText : "升序",
				sortDescText : "降序",
				forceFit : true
			},
			bbar : new Ext.PagingToolbar({
						store : sysLogStore,
						pageSize : pageSize,
						displayInfo : true,
						displayMsg : '显示第 {0} 条到 {1} 条记录，共 {2} 条',
						emptyMsg : "没有记录信息显示"
					}),
			tbar : ["", "", {
						id : "updatebtn",
						text : "<b>查看</b>",
						iconCls : "groupEdit",
						disabled : true,
						handler : function() {
							var r = grid.getSelectionModel().getSelected();
							if (r) {
								var rows = grid.getSelectionModel().getSelections();
								if (rows.length == 1) {
									viewSysLog(rows[0])
								} else {
									top.Ext.Msg.alert("友情提示", "一次只能查看一条记录！");
								}
							} else {
								top.Ext.Msg.alert("友情提示", "请先选择要查看的记录！");
							}
						}
					}, "-",{
						id : "deletebtn",
						text : "<b>删除</b>",
						iconCls : "groupDelete",
						handler : function() {
							var _records = grid.getSelectionModel().getSelections();

							if (_records.length < 1) {
								top.Ext.Msg.alert("系统消息", "请选择您要操作的行！");
								return false;
							}

							top.Ext.Msg.confirm("系统提示", "确定要删除所选的 "
											+ _records.length + " 行记录吗？", function(
											btnYes) {
										if (btnYes == 'yes' || btnYes == 'ok'
												|| btnYes == true) {
											var ids = "";
											for (var i = 0; i < _records.length; i++) {
												ids += _records[i].data.id
														+ ",";
											}

											Ext.Ajax.request({
												url : path
														+ "/sysLog!delete.action",
												params : {
													ids : ids
												},
												success : function(resp) {
													var respText = Ext.util.JSON
															.decode(resp.responseText);

													top.Ext.Msg.alert("友情提示",
															respText.msg + "<br/>");

													sysLogStore.reload({
																params : {
																	start : 0,
																	limit : pageSize
																}
															});
												}
											});
										}
									});
						}
					}, '-',{
						xtype : 'datefield',
						id : 'startDate',
						format : 'Y-m-d',
						emptyText : "选择开始时间",
						anchor : '95%',
						hidden : true,
						width : 100,
						disabled : true,
						allowBlank : false,
						blankText : "开始时间不能为空！",
						listeners : {
							'select' : function() {
								var start = Ext.getCmp('startDate').getValue()
										.format("Y-m-d");
								Ext.getCmp('endDate').setMinValue(start);
							}
						}
					}, '&nbsp;&nbsp;', {
						xtype : 'datefield',
						id : 'endDate',
						format : 'Y-m-d',
						emptyText : "选择结束时间",
						hidden : true,
						allowBlank : false,
						blankText : "结束时间不能为空！",
						width : 100,
						disabled : true,
						anchor : '95%'
					},{
						xtype : 'textfield',
						id : 'textValue',
						name : 'itemsValue',
						width : 120
					}, '&nbsp;', {
						xtype : "combo",
						width : 140,
						triggerAction : 'all',
						model : 'local',
						id : "comboCheckItems",
						hiddenName : 'checkItems',
						name : 'checkItemstext',
						store : [['', '查询全部']
								,['LIKES_moduleName', '模块名称']
								,['EQL_logType', '日志类型']
								,['LIKES_operAccount', '操作账号']
								,['LIKES_operStatus', '操作状态']
								,['LIKES_clientIp', '客户端IP']
								,['GTL_invokeTime', '执行时间大于']
								,['LTL_invokeTime', '执行时间小于']
								,['EQD_createdtime', '操作时间']
								],
						emptyText : '选择类型进行查询',
						forceSelection : true,
						listeners : {
							'select' : function(combo, record, index) {
								if (combo.getValue() == 'EQD_createdtime') {

									Ext.getCmp("startDate").enable();
									Ext.getCmp("startDate").show();

									Ext.getCmp("endDate").enable();
									Ext.getCmp("endDate").show();

									Ext.getCmp("textValue").disable();
									Ext.getCmp("textValue").hide();
									Ext.getCmp("textValue").setValue("");

								} else if(combo.getValue() == ""){
									
									Ext.getCmp("startDate").disable();
									Ext.getCmp("startDate").hide();
									Ext.getCmp("startDate").setValue("");

									Ext.getCmp("endDate").disable();
									Ext.getCmp("endDate").hide();
									Ext.getCmp("endDate").setValue("");

									Ext.getCmp("textValue").disable();
									Ext.getCmp("textValue").show();
									Ext.getCmp("textValue").setValue("");

								}else {
									Ext.getCmp("startDate").disable();
									Ext.getCmp("startDate").hide();
									Ext.getCmp("startDate").setValue("");

									Ext.getCmp("endDate").disable();
									Ext.getCmp("endDate").hide();
									Ext.getCmp("endDate").setValue("");

									Ext.getCmp("textValue").enable();
									Ext.getCmp("textValue").show();
									Ext.getCmp("textValue").setValue("");


								}
							}
						}
					}, '-', {
						text : '<b>搜索</b>',
						id : 'btn',
						iconCls : 'btnSearch',
						handler : search
					}]
		});

		// 当有选择行时，将修改，删除按钮变为可点击状态
		grid.on('click', function() {
					var adContentRecord = grid.getSelectionModel().getSelections();
					var addbtn = Ext.getCmp('addbtn');
					var updatebtn = Ext.getCmp('updatebtn');
					var editbtn = Ext.getCmp('editbtn');
					var editmenu = Ext.getCmp('editmenu');
					if (adContentRecord.length == 1) {
                                            if(addbtn){addbtn.setDisabled(false);}
					if(updatebtn){updatebtn.setDisabled(false);}
						if(editbtn){editbtn.setDisabled(false);}
						if(editmenu){editmenu.setDisabled(false);}
					} else if (adContentRecord.length > 1) {
						if(addbtn){addbtn.setDisabled(false);}
						if(updatebtn){updatebtn.setDisabled(true);}
						if(editbtn){editbtn.setDisabled(true);}
						if(editmenu){editmenu.setDisabled(true);}
					} else {
						if(addbtn){addbtn.setDisabled(false);}
						if(updatebtn){updatebtn.setDisabled(true);}
						if(editbtn){editbtn.setDisabled(true);}
						if(editmenu){editmenu.setDisabled(true);}
					}
				});

		grid.render();

		sysLogStore.load({
					params : {
						start : 0,
						limit : pageSize
					}
				});


		// 更新sysLog
		function viewSysLog(_record) {

			var form = new top.Ext.form.FormPanel({
				labelAlign : 'left',
				frame : true,
				fileUpload : true,
				reader : sysLogReader,
				bodyStyle : 'padding:5px 5px 0',
				width : 800,
				items : [{
							layout : 'column',
							items : [{
										columnWidth : .5,
										layout : 'form',
										items : [{
													fieldLabel : 'ID记录',
													name : "id",
													xtype : "textfield",
													readOnly : true,
													anchor : '95%'
												}, {
													name : "logTypeShow",
													hiddenName : "logType",
													fieldLabel : "日志类型",
													xtype : "combo",
													readOnly : true,
													blankText : "内容类型不能为空！",
													store : logTypeStore,
													valueField : "typeCode",
													displayField : "typeName",
													triggerAction : "all",
													anchor : '95%',
													width : '95%',
													mode : "local",
													
													listeners:{"render": function(thisObj){
														var pel = thisObj.el.parent();
														pel.mask();
													}}
												}, {
													name : "clientIp",
													xtype :  'textfield',
													fieldLabel : "客户端IP",
													readOnly : true,
													anchor : '95%'
												}, {
													name : "operStatus",
													xtype :  'textfield',
													readOnly : true,
													fieldLabel : "操作状态",
													anchor : '95%'
												}]
									}, {
										columnWidth : .5,
										layout : 'form',
										items : [{name : "moduleName",
												  xtype :  'textfield',
											      fieldLabel : "模块名称",
											      anchor : '95%',
											      readOnly : true
												}, {
													name : "operAccount",
													xtype :  'textfield',
													fieldLabel : "操作账号",
													readOnly : true,
													anchor : '95%'
												}, {
													name : "createdtime",
													xtype : 'textfield',
													fieldLabel : "操作时间",
													readOnly : true,
													anchor : '95%'
												}]
									}]
						}, {
							labelAlign : 'top',
							xtype : 'textarea',
							name : 'operDetail',
							fieldLabel : '操作明细',
							height : 300,
							readOnly : true,
							anchor : '95%'
						}]
			});
			
			form.load({
						url : path + "/sysLog!list.action?filter_EQL_id="
								+ _record.data.id,
						success : function(form, action) {
						}
					})

			var win = new top.Ext.Window({
				title : '查看系统日志信息',
				width : 800,
				closeAction : 'hide',
				plain : true,
				modal : true,
				items : form,
				buttonAlign : "center"
			});
			win.on('hide', function() {
						form.destroy();
					});
			win.show();
		}

		function search() {

			if (Ext.getCmp('comboCheckItems').getValue() == 'EQD_createdtime') {

				if(Ext.getCmp('startDate').getValue() == "") {
					top.Ext.Msg.alert("提示","开始时间不能为空！");
					return false;
				}
				if(Ext.getCmp('endDate').getValue() == "") {
					top.Ext.Msg.alert("提示","结束时间不能为空！");
					return false;
				}
				var start = Ext.getCmp('startDate').getValue().format("Y-m-d");
				var end = Ext.getCmp('endDate').getValue().format("Y-m-d") + " 23:59:59";
				Ext.apply(sysLogStore.baseParams, {
							filter_GED_createdtime : start,
							filter_LTD_createdtime : end,
							checkItems : '',
							itemsValue : ''
						});
			} else {
				Ext.apply(sysLogStore.baseParams,{
							checkItems : Ext.get("checkItems").dom.value,
							itemsValue : getCheckValue(),
							filter_GED_createdtime : '',
							filter_LTD_createdtime : ''
						});
			}
			
			Ext.apply(sysLogStore.baseParams, {
						checkItems : Ext.get("checkItems").dom.value,
						itemsValue : getCheckValue()
					});

			sysLogStore.reload({
						params : {
							start : 0,
							limit : pageSize
						}
					});
		}

		function getCheckValue() {
			var itemsValue = Ext.query("*[name=itemsValue]");
			var foo;
			for (var i = 0; i < itemsValue.length; i++) {
				if (Ext.get(itemsValue[i]).getValue()) {
					foo = Ext.get(itemsValue[i]).getValue();
					break;
				}
			}
			return foo;
		}

	});
	</script>
</head>
<body>
<div id="sysLogGrid"></div>
</body>
</html>


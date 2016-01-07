Ext.chart.Chart.CHART_URL = '../extjs3.1/resources/charts.swf';

Ext.QuickTips.init();


var myStore = new Ext.data.ArrayStore({
    fields: ['fullname', 'first'],
    idIndex: 0 // id for each record will be the first element
});

var myData = [
    ['张先生','18922788284','业务联系人','否','1988-02-02','篮球，排球','李先生','上下级','2011-09-09 18:18:18','男'],
    ['李小姐','18922782222','财务负责人','是','1988-02-02','篮球，排球','李先生','夫妻','2011-09-09 18:18:18','女'],
    ['李先生','18922788284','业务负责人','是','1988-02-02','篮球，排球','','','2011-09-09 18:18:18','男'],
    ['王小姐','18922788284','财务联系人','否','1988-02-02','篮球，排球','李先生','上下级','2011-09-09 18:18:18','女']
];

var store = new Ext.data.ArrayStore({
    // store configs
    autoDestroy: true,
    storeId: 'myStore',
    // reader configs
    idIndex: 0,  
    fields: [
       'f1','f2','f3','f4','f5','f6','f7','f8','f9','f10'
    ],
    date:myData
});

var requestStore = new Ext.data.ArrayStore({
    // store configs
    autoDestroy: true,
    storeId: 'myStore',
    // reader configs
    idIndex: 0,  
    fields: [
       'f1','f2','f3','f4','f5','f6','f7','f8','f9'
    ]
});

var requestData = [
    ['时效要求','务必在12点前送到','1988-02-02','张三','李四','未采纳',''],
    ['运作质量要求','货损一定通知','1988-02-02','张三','李四','采纳','送货实效.doc'],
    ['操作过程要求','每次操作必须短信通知','1988-02-02','张三','李四','采纳','异常处理.doc']
];


var data2 = [
    ['张三','18922788258','好','<a><font>12家代理</font></a>','直接指定',''],
    ['李四','18922788258','好','<a><font>13家代理</font></a>','影响力一般',''],
    ['王五','18922788258','好','<a><font>12家代理</font></a>','无影响力','']
];

var store2 = new Ext.data.ArrayStore({
    // store configs
    autoDestroy: true,
    storeId: 'myStore',
    // reader configs
    idIndex: 0,  
    fields: [
       'f1','f2','f3','f4','f5','f6','f7','f8','f9'
    ],
    date:myData
});


var data3 = [
    ['外资'],
    ['合资'],
    ['国营'],
    ['民营'],
    ['私营']
];

var store3 = new Ext.data.ArrayStore({
    // store configs
    autoDestroy: true,
    storeId: 'myStore',
    // reader configs
    idIndex: 0,  
    fields: [
       'f1'
    ]
});



var store4 = new Ext.data.JsonStore({
        fields:['name', 'visits', 'views'],
        autoLoad:true,
        data: [
            {name:'一月', visits: 245000, views: 3000000},
            {name:'二月', visits: 240000, views: 3500000},
            {name:'三月', visits: 355000, views: 4000000},
            {name:'四月', visits: 375000, views: 4200000},
            {name:'五月', visits: 490000, views: 4500000},
            {name:'六月', visits: 495000, views: 5800000},
            {name:'七月', visits: 520000, views: 6000000},
            {name:'八月', visits: 620000, views: 7500000},
            {name:'九月', visits: 375000, views: 4200000},
            {name:'十月', visits: 490000, views: 4500000},
            {name:'十一月', visits: 495000, views: 5800000},
            {name:'十二月', visits: 520000, views: 6000000}
        ]
    });
   
var store5 = new Ext.data.JsonStore({
        fields:['name', 'tel', 'add','distribution','daishou','daofu','yufu','chengben','lirun','lirunlv','daofushouyin','zhuanche','shineisonghuo','shineiziti'],
        autoLoad:true,
        data: [
            {name:'张三', tel: '18922788012', add: '广州市白云区黄埔大道北102号白云大厦903',daishou:25000,daofu:19000,yufu:10000,chengben:10000,lirun:9000,lirunlv:'60%',daofushouyin:5000,zhuanche:5000,shineisonghuo:20000,shineiziti:20000},
            {name:'张三', tel: '18922788012', add: '广州市白云区黄埔大道北102号白云大厦903',daishou:25000,daofu:19000,yufu:10000,chengben:10000,lirun:9000,lirunlv:'20%',daofushouyin:5000,zhuanche:5000,shineisonghuo:20000,shineiziti:20000},
            {name:'张三', tel: '18922788012', add: '广州市白云区黄埔大道北102号白云大厦903',daishou:25000,daofu:19000,yufu:10000,chengben:10000,lirun:9000,lirunlv:'5%',daofushouyin:5000,zhuanche:5000,shineisonghuo:20000,shineiziti:20000},
            {name:'张三', tel: '18922788012', add: '广州市白云区黄埔大道北102号白云大厦903',daishou:25000,daofu:19000,yufu:10000,chengben:10000,lirun:9000,lirunlv:'2%',daofushouyin:5000,zhuanche:5000,shineisonghuo:20000,shineiziti:20000}
        ]
    });
    
  var store6 = new Ext.data.ArrayStore({
        fields:['f1', 'f2', 'f3','f4','f5','f6','f7','f8','f9','f10','f11'],
        autoLoad:true,
        data: [
            ['电话拜访客户需求收集','电话沟通','潜在客户','经同事介绍此客户有大批空运货需要终端配送',0,'张三','2011-01-01 12:24:33','审核通过','2011-01-01 12:24:33','','张经理'],
            ['回复客户需求，并设定解决方案','电话沟通','潜在客户','客户有严格的时效要求，但货量较大，经部门会议，对客户所做要求做如下回复，详情见附表',0,'张三','2011-01-01 12:24:33','审核通过','2011-01-01 12:24:33','<a>客户需求方案.doc</a>','张经理'],
            ['客户来电咨询报价','电话沟通','潜在客户','客户在珠三角精品服务货量较大，故要求珠三角价格做相对优惠价格，详情见附表',0,'张三','2011-01-01 12:24:33','审核通过','2011-01-01 12:24:33','<a>客户报价.doc</a>','张经理'],
            ['邮寄报价合同','合同签订','潜在客户','合同由顺风投寄，投寄编码：123456，合同电子档见附件',10,'张三','2011-01-01 12:24:33','未审核','','','张经理'],
            ['中秋送月饼一盒','感情培养','合同客户','月饼由顺风投寄，投寄编码：123456',150,'张三','2011-01-01 12:24:33','审核通过','2011-09-12 12:30:00','','李总']
        ]
    }); 
    
   var store7 = new Ext.data.JsonStore({
        fields:[{name:"id",mapping:'id'},
	     		{name:'startDate',mapping:'startDate'},
	     		{name:'endDate',mapping:'endDate'},
	     		{name:'trafficMode',mapping:'trafficMode'},
	     		{name:'distributionMode',mapping:'distributionMode'},
	     		{name:'takeMode',mapping:'takeMode'},
	     		{name:'lowPrice',mapping:'lowPrice'},
	     		{name:'stage1Rate',mapping:'stage1Rate'},
	     		{name:'stage2Rate',mapping:'stage2Rate'},
	     		{name:'stage3Rate',mapping:'stage3Rate'},
	     		{name:'status',mapping:'status'},
	     		{name:'createName',mapping:'createName'},
	     		{name:'createTime',mapping:'createTime'},
	     		{name:'updateName',mapping:'updateName'},
	     		{name:'updateTime',mapping:'updateTime'},
	     		{name:'ts',mapping:'ts'},
	     		{name:'departId',mapping:'departId'},
	     		{name:'addressType',mapping:'addressType'},
	     		{name:'valuationType',mapping:'valuationType'},
	     		{name:'startAddr',mapping:'startAddr'},
	     		{name:'endAddr',mapping:'endAddr'}],
        autoLoad:true,
        root: 'result', totalProperty: 'totalCount',
        data: 
            {"autoCount":true,"first":0,"hasNext":true,"hasPre":false,"limit":20,"nextPage":2,"order":null,"orderBy":null,"orderBySetted":false,"pageNo":1,"pageSize":20,"prePage":1,"result":[{"addressType":"远郊","createName":"张三","createTime":"2011-09-26","departId":1,"distributionMode":"中转","endAddr":null,"endDate":"2013-02-05","id":14150,"lowPrice":70.0,"stage1Rate":0.5,"stage2Rate":0.45,"stage3Rate":0.4,"startAddr":"广州市","startDate":"2011-09-01","status":2,"takeMode":"市内自提","trafficMode":"空运","ts":"1317007982503","updateName":"张三","updateTime":"2011-09-26","valuationType":"重量"},{"addressType":"远郊","createName":"张三","createTime":"2011-09-24","departId":1,"distributionMode":"中转","endAddr":null,"endDate":null,"id":12053,"lowPrice":50.0,"stage1Rate":0.9,"stage2Rate":0.8,"stage3Rate":0.7,"startAddr":"广州市","startDate":"2011-09-24","status":2,"takeMode":"市内送货","trafficMode":"空运","ts":"1316855016834","updateName":"张三","updateTime":"2011-09-24","valuationType":"重量"},{"addressType":"远郊","createName":"张三","createTime":"2011-09-24","departId":1,"distributionMode":"新邦","endAddr":null,"endDate":null,"id":12050,"lowPrice":1000.0,"stage1Rate":0.5,"stage2Rate":0.1,"stage3Rate":0.2,"startAddr":"广州市","startDate":"2011-08-05","status":2,"takeMode":"市内送货","trafficMode":"空运","ts":"1316831391305","updateName":"张三","updateTime":"2011-09-24","valuationType":"件数"},{"addressType":"市内","createName":"曹智礼","createTime":"2011-09-23","departId":1,"distributionMode":"新邦","endAddr":null,"endDate":"2016-09-14","id":11103,"lowPrice":100.0,"stage1Rate":0.5,"stage2Rate":0.4,"stage3Rate":0.3,"startAddr":"广州市","startDate":"2011-09-22","status":2,"takeMode":"机场自提","trafficMode":"空运","ts":"1316749640149","updateName":"曹智礼","updateTime":"2011-09-23","valuationType":"体积"},{"addressType":"近郊","createName":"曹智礼","createTime":"2011-09-23","departId":1,"distributionMode":"外发","endAddr":null,"endDate":"2016-09-23","id":11102,"lowPrice":125.0,"stage1Rate":2.0,"stage2Rate":1.0,"stage3Rate":0.5,"startAddr":"广州市","startDate":"2011-09-16","status":2,"takeMode":"市内自提","trafficMode":"空运","ts":"1316748212460","updateName":"曹智礼","updateTime":"2011-09-23","valuationType":"重量"},{"addressType":"近郊","createName":"曹智礼","createTime":"2011-09-23","departId":1,"distributionMode":"新邦","endAddr":null,"endDate":"2012-12-21","id":11101,"lowPrice":100.0,"stage1Rate":0.5,"stage2Rate":0.4,"stage3Rate":0.35,"startAddr":"广州市","startDate":"2011-09-23","status":2,"takeMode":"市内自提","trafficMode":"空运","ts":"1316746033293","updateName":"曹智礼","updateTime":"2011-09-23","valuationType":"重量"},{"addressType":"市内","createName":"曹智礼","createTime":"2011-09-23","departId":1,"distributionMode":"新邦","endAddr":null,"endDate":null,"id":11100,"lowPrice":100.0,"stage1Rate":0.5,"stage2Rate":0.3,"stage3Rate":0.2,"startAddr":"深圳市","startDate":"2011-09-16","status":2,"takeMode":"机场自提","trafficMode":"空运","ts":"1316745043801","updateName":"曹智礼","updateTime":"2011-09-23","valuationType":"重量"},{"addressType":"远郊","createName":"张三","createTime":"2011-09-23","departId":1,"distributionMode":"新邦","endAddr":null,"endDate":"2016-09-23","id":11050,"lowPrice":50.0,"stage1Rate":0.7,"stage2Rate":0.65,"stage3Rate":0.6,"startAddr":"广州市","startDate":"2011-09-01","status":2,"takeMode":"市内自提","trafficMode":"空运","ts":"1316746710644","updateName":"曹智礼","updateTime":"2011-09-23","valuationType":"重量"},{"addressType":"市内","createName":"0353","createTime":"2011-09-22","departId":1,"distributionMode":"新邦","endAddr":null,"endDate":"2011-11-03","id":10050,"lowPrice":45.0,"stage1Rate":0.7,"stage2Rate":0.7,"stage3Rate":0.7,"startAddr":"广州市","startDate":"2011-09-01","status":2,"takeMode":"市内送货","trafficMode":"空运","ts":"1316672798875","updateName":"0353","updateTime":"2011-09-22","valuationType":"重量"},{"addressType":"市内","createName":"0353","createTime":"2011-09-20","departId":1,"distributionMode":"外发","endAddr":null,"endDate":"2011-10-18","id":9056,"lowPrice":120.0,"stage1Rate":1.2,"stage2Rate":1.2,"stage3Rate":1.2,"startAddr":"广州市","startDate":"2011-08-16","status":2,"takeMode":"市内送货","trafficMode":"空运","ts":"1316481817243","updateName":"0353","updateTime":"2011-09-20","valuationType":"重量"},{"addressType":"市内","createName":"0353","createTime":"2011-09-20","departId":1,"distributionMode":"外发","endAddr":null,"endDate":"2011-10-11","id":9054,"lowPrice":60.0,"stage1Rate":0.8,"stage2Rate":0.8,"stage3Rate":0.8,"startAddr":"广州市","startDate":"2011-08-23","status":2,"takeMode":"市内自提","trafficMode":"空运","ts":"1316481817254","updateName":"0353","updateTime":"2011-09-20","valuationType":"重量"},{"addressType":"市内","createName":"0353","createTime":"2011-09-20","departId":1,"distributionMode":"中转","endAddr":null,"endDate":"2011-10-01","id":9051,"lowPrice":80.0,"stage1Rate":0.9,"stage2Rate":0.9,"stage3Rate":0.9,"startAddr":"广州市","startDate":"2011-09-20","status":2,"takeMode":"市内送货","trafficMode":"空运","ts":"1316511748939","updateName":"张三","updateTime":"2011-09-20","valuationType":"重量"},{"addressType":"市内","createName":"0353","createTime":"2011-09-20","departId":1,"distributionMode":"中转","endAddr":null,"endDate":"2011-09-30","id":9050,"lowPrice":40.0,"stage1Rate":0.6,"stage2Rate":0.6,"stage3Rate":0.6,"startAddr":"广州市","startDate":"2011-09-20","status":2,"takeMode":"市内自提","trafficMode":"空运","ts":"1316481817297","updateName":"0353","updateTime":"2011-09-20","valuationType":"重量"},{"addressType":"市内","createName":"0353","createTime":"2011-09-16","departId":1,"distributionMode":"新邦","endAddr":null,"endDate":"2011-09-30","id":8050,"lowPrice":15.0,"stage1Rate":0.55,"stage2Rate":0.55,"stage3Rate":0.55,"startAddr":"广州市","startDate":"2011-09-16","status":2,"takeMode":"市内自提","trafficMode":"空运","ts":"1316672635780","updateName":"0353","updateTime":"2011-09-22","valuationType":"重量"}],"resultMap":[],"start":0,"success":true,"totalCount":14,"totalObject":{},"totalPages":2}
        
    }); 
    
  var store8 = new Ext.data.ArrayStore({
        fields:['f1', 'f2', 'f3','f4','f5','f6','f7','f8','f9','f10','f11','f12','f13'],
        autoLoad:true,
        data: [
            ['重量',1000,1250,3000,50,200,1000,3000,5000,5890,4570,4000,4300],
            ['票数',10,12,30,50,30,25,35,33,23,41,20,34]
        ]
    }); 
    
    var store9 = new Ext.data.ArrayStore({
        fields:['f1', 'f2', 'f3','f4','f5','f6','f7','f8','f9','f10','f11','f12','f13'],
        autoLoad:true,
        data: [
            ['市内送货','20%','30%','30%','50%','20%','10%','30%','50%','58%','45%','40%','43%'],
            ['市内自提','10%','20%','30%','50%','30%','25%','35%','33%','23%','41%','20%','34%'],
            ['专车','5%','8%','30%','50%','30%','25%','35%','33%','23%','41%','20%','34%'],
            ['中转','55%','40%','30%','50%','30%','25%','35%','33%','23%','41%','20%','34%'],
            ['外发','10%','2%','30%','50%','30%','25%','35%','33%','23%','41%','20%','34%']
        ]
    });
    
    var store10 = new Ext.data.ArrayStore({
        fields:['f1', 'f2', 'f3','f4','f5','f6','f7','f8','f9','f10','f11','f12','f13'],
        autoLoad:true,
        data: [
            ['盈利金额','20000','12222','122333','1222','12222','4343','344','34444','44444','4444','55555','6666'],
            ['盈利率','10%','20%','30%','50%','30%','25%','35%','33%','23%','41%','20%','34%']
        ]
    });
    
    
    var sm = new Ext.grid.CheckboxSelectionModel({});
	var rowNum = new Ext.grid.RowNumberer({
        header:'序号', width:20
    });
    
    
    
    CargoTrend = Ext.extend(Ext.Panel, {
    height: 300,
    layout: {
        type: 'fit'
    },
    title: '分析统计报表',

    initComponent: function() {
        this.items = [
            {
                xtype: 'tabpanel',
                activeTab: 0,
                height:300,
                items: [
                    {
                        xtype: 'panel',
                        layout: {
                            type: 'border'
                        },
                        title: '货量分析',
                        items: [
                            {
                                xtype: 'panel',
                                height: 150,
                                title: '',
                                region: 'center',
                                html:"<img src='huoliang.jpg' height='90' width='1024'/>"
                            },
                            {
                                xtype: 'grid',
                                height: 150,
                                title: '',
                                store: store8,
                                region: 'south',
                                columns: [
                                    {
                                        dataIndex: 'f1',
                                        header: '统计类型',
                                        sortable: true,
                                        width: 100
                                    },
                                    {
                                        dataIndex: 'f2',
                                        header: '一月',
                                        sortable: true,
                                        width: 100
                                    },
                                    {
                                        dataIndex: 'f3',
                                        header: '二月',
                                        sortable: true,
                                        width: 100
                                    },
                                    {
                                        dataIndex: 'f4',
                                        header: '三月',
                                        sortable: true,
                                        width: 100
                                    },
                                    {
                                        dataIndex: 'f5',
                                        header: '四月',
                                        sortable: true,
                                        width: 100
                                    },{
                                        dataIndex: 'f6',
                                        header: '五月',
                                        sortable: true,
                                        width: 100
                                    },{
                                        dataIndex: 'f7',
                                        header: '六月',
                                        sortable: true,
                                        width: 100
                                    },{
                                        dataIndex: 'f8',
                                        header: '七月',
                                        sortable: true,
                                        width: 100
                                    },{
                                        dataIndex: 'f9',
                                        header: '八月',
                                        sortable: true,
                                        width: 100
                                    },{
                                        dataIndex: 'f10',
                                        header: '九月',
                                        sortable: true,
                                        width: 100
                                    },{
                                        dataIndex: 'f11',
                                        header: '十月',
                                        sortable: true,
                                        width: 100
                                    },{
                                        dataIndex: 'f12',
                                        header: '十一月',
                                        sortable: true,
                                        width: 100
                                    },{
                                        dataIndex: 'f13',
                                        header: '十二月',
                                        sortable: true,
                                        width: 100
                                    }
                                    
                                ]
                            }
                        ],
                        tbar: {
                            xtype: 'toolbar',
                            items: [
                                {
                                    xtype: 'combo'
                                },
                                {
                                    xtype: 'tbseparator'
                                },
                                {
                                    xtype: 'textfield'
                                },
                                {
                                    xtype: 'tbseparator'
                                },
                                {
                                    xtype: 'button',
                                    text: '查询'
                                }
                            ]
                        }
                    },
                    {
                        xtype: 'panel',
                        layout: {
                            type: 'border'
                        },
                        title: '产品结构分析',
                        items: [
                            {
                                xtype: 'panel',
                                height: 94,
                                title: '',
                                region: 'center',
                                html:"<img src='jiegou.jpg' height='90' width='1024'/>"
                            },
                            {
                                xtype: 'grid',
                                height: 150,
                                title: '',
                                store: store9,
                                region: 'south',
                                columns: [
                                    {
                                        dataIndex: 'f1',
                                        header: '统计类型',
                                        sortable: true,
                                        width: 100
                                    },
                                    {
                                        dataIndex: 'f2',
                                        header: '一月',
                                        sortable: true,
                                        width: 100
                                    },
                                    {
                                        dataIndex: 'f3',
                                        header: '二月',
                                        sortable: true,
                                        width: 100
                                    },
                                    {
                                        dataIndex: 'f4',
                                        header: '三月',
                                        sortable: true,
                                        width: 100
                                    },
                                    {
                                        dataIndex: 'f5',
                                        header: '四月',
                                        sortable: true,
                                        width: 100
                                    },{
                                        dataIndex: 'f6',
                                        header: '五月',
                                        sortable: true,
                                        width: 100
                                    },{
                                        dataIndex: 'f7',
                                        header: '六月',
                                        sortable: true,
                                        width: 100
                                    },{
                                        dataIndex: 'f8',
                                        header: '七月',
                                        sortable: true,
                                        width: 100
                                    },{
                                        dataIndex: 'f9',
                                        header: '八月',
                                        sortable: true,
                                        width: 100
                                    },{
                                        dataIndex: 'f10',
                                        header: '九月',
                                        sortable: true,
                                        width: 100
                                    },{
                                        dataIndex: 'f11',
                                        header: '十月',
                                        sortable: true,
                                        width: 100
                                    },{
                                        dataIndex: 'f12',
                                        header: '十一月',
                                        sortable: true,
                                        width: 100
                                    },{
                                        dataIndex: 'f13',
                                        header: '十二月',
                                        sortable: true,
                                        width: 100
                                    }
                                ]
                            }
                        ],
                        tbar: {
                            xtype: 'toolbar',
                            width: 100,
                            region: 'west',
                            items: [
                                {
                                    xtype: 'combo'
                                },
                                {
                                    xtype: 'tbseparator'
                                },
                                {
                                    xtype: 'textfield'
                                },
                                {
                                    xtype: 'tbseparator'
                                },
                                {
                                    xtype: 'button',
                                    text: '查询'
                                }
                            ]
                        }
                    },
                    {
                        xtype: 'panel',
                        layout: {
                            type: 'border'
                        },
                        title: '盈利分析',
                        items: [
                            {
                                xtype: 'panel',
                                height: 94,
                                title: '',
                                region: 'center',
                                html:"<img src='huoliang.jpg' height='90' width='1024'/>"
                            },
                            {
                                xtype: 'grid',
                                height: 150,
                                title: '',
                                store: store10,
                                region: 'south',
                                columns: [
                                    {
                                        dataIndex: 'f1',
                                        header: '统计类型',
                                        sortable: true,
                                        width: 100
                                    },
                                    {
                                        dataIndex: 'f2',
                                        header: '一月',
                                        sortable: true,
                                        width: 100
                                    },
                                    {
                                        dataIndex: 'f3',
                                        header: '二月',
                                        sortable: true,
                                        width: 100
                                    },
                                    {
                                        dataIndex: 'f4',
                                        header: '三月',
                                        sortable: true,
                                        width: 100
                                    },
                                    {
                                        dataIndex: 'f5',
                                        header: '四月',
                                        sortable: true,
                                        width: 100
                                    },{
                                        dataIndex: 'f6',
                                        header: '五月',
                                        sortable: true,
                                        width: 100
                                    },{
                                        dataIndex: 'f7',
                                        header: '六月',
                                        sortable: true,
                                        width: 100
                                    },{
                                        dataIndex: 'f8',
                                        header: '七月',
                                        sortable: true,
                                        width: 100
                                    },{
                                        dataIndex: 'f9',
                                        header: '八月',
                                        sortable: true,
                                        width: 100
                                    },{
                                        dataIndex: 'f10',
                                        header: '九月',
                                        sortable: true,
                                        width: 100
                                    },{
                                        dataIndex: 'f11',
                                        header: '十月',
                                        sortable: true,
                                        width: 100
                                    },{
                                        dataIndex: 'f12',
                                        header: '十一月',
                                        sortable: true,
                                        width: 100
                                    },{
                                        dataIndex: 'f13',
                                        header: '十二月',
                                        sortable: true,
                                        width: 100
                                    }
                                ]
                            }
                        ],
                        tbar: {
                            xtype: 'toolbar',
                            width: 100,
                            region: 'west',
                            items: [
                                {
                                    xtype: 'combo'
                                },
                                {
                                    xtype: 'tbseparator'
                                },
                                {
                                    xtype: 'textfield'
                                },
                                {
                                    xtype: 'tbseparator'
                                },
                                {
                                    xtype: 'button',
                                    text: '查询'
                                }
                            ]
                        }
                    }
                ]
            }
        ];
        MyPanelUi.superclass.initComponent.call(this);
    }
});
    
 
    
    
 //报价管理   
 MyPanelUi = Ext.extend(Ext.Panel, {
    height: 371,
    layout: {
        type: 'fit'
    },
    title: '报价管理',

    initComponent: function() {
     	
        this.items = [
            {
                xtype: 'tabpanel',
                activeItem: 0,
                activeTab: 3,
                items: [
                    {
                        xtype: 'panel',
                        layout: {
                            type: 'fit'
                        },
                        title: '公司公布价',
                        items: [
                            {
                                xtype: 'grid',
                                title: '',
                                store: store7,
                                cm:new Ext.grid.ColumnModel([sm,
						        		{header: '序号ID', dataIndex: 'id',sortable:true,hidden:true},
						 				{header: '客商名称', dataIndex: 'cusName',sortable : true,hidden:true},
						       			{header: '开始日期', dataIndex: 'startDate',sortable : true},
						        		{header: '结束日期', dataIndex: 'endDate',sortable : true},
						        		{header: '地区类型', dataIndex: 'addressType',sortable : true},
						 				{header: '运输方式', dataIndex: 'trafficMode',sortable : true},
						 				{header: '配送方式', dataIndex: 'distributionMode',sortable : true},
						 				{header: '提货方式', dataIndex: 'takeMode',sortable : true},
						 				{header: '计价方式', dataIndex: 'valuationType',sortable : true},
						 				{header: '最低一票', dataIndex: 'lowPrice',sortable : true},
						 				{header: '500KG以下等级价', dataIndex: 'stage1Rate',sortable : true},
						 				{header: '1000KG等级', dataIndex: 'stage2Rate',sortable : true},
						 				{header: '1000KG以上等级', dataIndex: 'stage3Rate',sortable : true},
						 				{header: '开始地址', dataIndex: 'startAddr',sortable : true},
						 				{header: '结束地址', dataIndex: 'endAddr',sortable : true},
						 				{header: '状态', dataIndex: 'status',sortable : true
						 						,renderer:function(v){
						 									return v==1?'未审核':'审核';
									        		}
 										},
			 							{header: '折扣', dataIndex: 'discount',sortable : true}
			       			 ]),bbar: new Ext.PagingToolbar({
						            pageSize: 20, 
						            store: store7, 
						            displayInfo: true,
						            displayMsg: '显示第 {0} 条到 {1} 条记录，共 {2} 条', 
						            emptyMsg: "没有记录信息显示"
						        }),
                                tbar: {
                                    xtype: 'toolbar',
                                   
                                    items: [
                                        {
                                            xtype: 'button',
                                            text: '打折生成协议价'
                                        },
                                        {
                                            xtype: 'splitbutton',
                                            text: '导出报价单',
                                            menu: {
                                                xtype: 'menu',
                                                items: [
                                                    {
                                                        xtype: 'menuitem',
                                                        text: 'doc 导出'
                                                    },
                                                    {
                                                        xtype: 'menuitem',
                                                        text: 'xls 导出'
                                                    },
                                                    {
                                                        xtype: 'menuitem',
                                                        text: 'pdf导出'
                                                    },
                                                    {
                                                        xtype: 'menuitem',
                                                        text: '邮件放送报价单'
                                                    }
                                                ]
                                            }
                                        },
                                        {
                                            xtype: 'tbseparator'
                                        }
                                    ]
                                }
                            }
                        ]
                    },
                    {
                        xtype: 'panel',
                        layout: {
                            type: 'fit'
                        },
                        title: '代理协议价',
                        items: [
                            {
                                xtype: 'grid',
                                title: '',
                                store: myStore,
                                cm:new Ext.grid.ColumnModel([sm,
						        		{header: '序号ID', dataIndex: 'id',sortable:true,hidden:true},
						 				{header: '客商名称', dataIndex: 'cusName',sortable : true,hidden:true},
						       			{header: '开始日期', dataIndex: 'startDate',sortable : true},
						        		{header: '结束日期', dataIndex: 'endDate',sortable : true},
						        		{header: '地区类型', dataIndex: 'addressType',sortable : true},
						 				{header: '运输方式', dataIndex: 'trafficMode',sortable : true},
						 				{header: '配送方式', dataIndex: 'distributionMode',sortable : true},
						 				{header: '提货方式', dataIndex: 'takeMode',sortable : true},
						 				{header: '计价方式', dataIndex: 'valuationType',sortable : true},
						 				{header: '最低一票', dataIndex: 'lowPrice',sortable : true},
						 				{header: '500KG以下等级价', dataIndex: 'stage1Rate',sortable : true},
						 				{header: '1000KG等级', dataIndex: 'stage2Rate',sortable : true},
						 				{header: '1000KG以上等级', dataIndex: 'stage3Rate',sortable : true},
						 				{header: '开始地址', dataIndex: 'startAddr',sortable : true},
						 				{header: '结束地址', dataIndex: 'endAddr',sortable : true},
						 				{header: '状态', dataIndex: 'status',sortable : true
						 						,renderer:function(v){
						 									return v==1?'未审核':'审核';
									        		}
 										},
			 							{header: '折扣', dataIndex: 'discount',sortable : true}
			       				 ]),
                                tbar: {
                                    xtype: 'toolbar',
                                    items: [
                                        {
                                            xtype: 'button',
                                            text: '新建报价单'
                                        },
                                        {
                                            xtype: 'tbseparator'
                                        },
                                        {
                                            xtype: 'splitbutton',
                                            text: '导出标准报价单',
                                            menu: {
                                                xtype: 'menu',
                                                items: [
                                                    {
                                                        xtype: 'menuitem',
                                                        text: 'doc 导出'
                                                    },
                                                    {
                                                        xtype: 'menuitem',
                                                        text: 'xls 导出'
                                                    },
                                                    {
                                                        xtype: 'menuitem',
                                                        text: 'pdf导出'
                                                    },
                                                    {
                                                        xtype: 'menuitem',
                                                        text: '邮件放送报价单'
                                                    }
                                                ]
                                            }
                                        }
                                    ]
                                }
                            }
                        ]
                    },
                    {
                        xtype: 'panel',
                        layout: {
                            type: 'fit'
                        },
                        title: '专车标准协议价',
                        items: [
                            {
                                xtype: 'grid',
                                title: '',
                                store: myStore,
                                cm:new Ext.grid.ColumnModel([rowNum,sm,
						        		{header: '序号ID', dataIndex: 'id',sortable:true,hidden:true},
						 				{header: '线路', dataIndex: 'specialTrainLineName',sortable : true},
						 				//{header: '地区类型', dataIndex: 'addressType',sortable : true},
						       			{header: '面包车', dataIndex: 'van',sortable : true},
						        		{header: '金杯车', dataIndex: 'goldCupCar',sortable : true},
						 				{header: '2吨车', dataIndex: 'twoTonCar',sortable : true},
						 				{header: '3吨车', dataIndex: 'threeTonCar',sortable : true},
						 				{header: '5吨车', dataIndex: 'fiveTonCar',sortable : true},
						 				{header: '冷藏车', dataIndex: 'chillCar',sortable : true},
						 				{header: '客商名称', dataIndex: 'cusName',sortable : true},
						 				{header: '路型', dataIndex: 'roadType',sortable : true},
						 				{header: '状态', dataIndex: 'status',sortable : true
						 						,renderer:function(v){
						 									return v==1?'未审核':'审核';
									        		}
						 				}
						        ]),
                                tbar: {
                                    xtype: 'toolbar',
                                    items: [
                                        {
                                            xtype: 'button',
                                            text: '打折生成协议价'
                                        },
                                        {
                                            xtype: 'splitbutton',
                                            text: '导出报价单',
                                            menu: {
                                                xtype: 'menu',
                                                items: [
                                                    {
                                                        xtype: 'menuitem',
                                                        text: 'doc 导出'
                                                    },
                                                    {
                                                        xtype: 'menuitem',
                                                        text: 'xls 导出'
                                                    },
                                                    {
                                                        xtype: 'menuitem',
                                                        text: 'pdf导出'
                                                    },
                                                    {
                                                        xtype: 'menuitem',
                                                        text: '邮件放送报价单'
                                                    }
                                                ]
                                            }
                                        },
                                        {
                                            xtype: 'tbseparator'
                                        }
                                    ]
                                }
                            }
                        ]
                    },
                    {
                        xtype: 'panel',
                        layout: {
                            type: 'fit'
                        },
                        title: '专车协议价',
                        items: [
                            {
                                xtype: 'grid',
                                title: '',
                                store: myStore,
                                cm:new Ext.grid.ColumnModel([rowNum,sm,
						        		{header: '序号ID', dataIndex: 'id',sortable:true,hidden:true},
						 				{header: '线路', dataIndex: 'specialTrainLineName',sortable : true},
						 				//{header: '地区类型', dataIndex: 'addressType',sortable : true},
						       			{header: '面包车', dataIndex: 'van',sortable : true},
						        		{header: '金杯车', dataIndex: 'goldCupCar',sortable : true},
						 				{header: '2吨车', dataIndex: 'twoTonCar',sortable : true},
						 				{header: '3吨车', dataIndex: 'threeTonCar',sortable : true},
						 				{header: '5吨车', dataIndex: 'fiveTonCar',sortable : true},
						 				{header: '冷藏车', dataIndex: 'chillCar',sortable : true},
						 				{header: '客商名称', dataIndex: 'cusName',sortable : true},
						 				{header: '路型', dataIndex: 'roadType',sortable : true},
						 				{header: '状态', dataIndex: 'status',sortable : true
						 						,renderer:function(v){
						 									return v==1?'未审核':'审核';
									        		}
						 				}
						        ]),
                                tbar: {
                                    xtype: 'toolbar',
                                    items: [
                                        {
                                            xtype: 'button',
                                            text: '新建报价单'
                                        },
                                        {
                                            xtype: 'tbseparator'
                                        },
                                        {
                                            xtype: 'splitbutton',
                                            text: '导出标准报价单',
                                            menu: {
                                                xtype: 'menu',
                                                items: [
                                                    {
                                                        xtype: 'menuitem',
                                                        text: 'doc 导出'
                                                    },
                                                    {
                                                        xtype: 'menuitem',
                                                        text: 'xls 导出'
                                                    },
                                                    {
                                                        xtype: 'menuitem',
                                                        text: 'pdf导出'
                                                    },
                                                    {
                                                        xtype: 'menuitem',
                                                        text: '邮件放送报价单'
                                                    }
                                                ]
                                            }
                                        }
                                    ]
                                }
                            }
                        ]
                    }
                ]
            }
        ];
        this.tbar = {
            xtype: 'toolbar',
            width:300,
            items: [
                {
                    xtype: 'combo'
                },
                {
                    xtype: 'tbseparator'
                },
                {
                    xtype: 'tbtext',
                    width: 61,
                    text: '地    址：'
                },
                {
                    xtype: 'textfield'
                },
                 {
                     xtype: 'button',
                     text: '查 询'
                }
            ]
        };
        MyPanelUi.superclass.initComponent.call(this);
    }
});
 
    
    
     


MyViewportUi = Ext.extend(Ext.Panel, {
	renderTo:Ext.getBody(),
    initComponent: function() {
        this.items = [
            {
                xtype: 'panel',
               
                autoScroll: true,
                height: Ext.lib.Dom.getViewHeight(),
                title: '',
                items: [
                    {
                        xtype: 'tabpanel',
                        height: 370,
                        width: Ext.lib.Dom.getViewWidth()-20,
                        activeItem: 0,
                        activeTab: 2,
                        items: [
                            {
                                xtype: 'panel',
                                layout: {
                                    type: 'column'
                                },
                                title: '客户资料管理',
                                frame : true,
                                tbar: {
                                    xtype: 'toolbar',
                                    items: [
                                        {
                                            xtype: 'button',
                                            text: '新增客户'
                                        },
                                        {
                                            xtype: 'tbseparator'
                                        },{
                                            xtype: 'button',
                                            text: '修改客户'
                                        },
                                        {
                                            xtype: 'tbseparator'
                                        }
                                    ]
                                },
                                
                                items: [
                                    {
                                    	columnWidth : 0.33,
                                        xtype: 'container',
                                        layout: {
                                            type: 'form'
                                        },
                                        defaults : {
											xtype : "textfield",
											width : 150
										},
                                        frame :true,
                                        flex: 1,
                                        items: [
                                            {
                                                xtype: 'textfield',
                                                fieldLabel: '客商编号',
                                                value:'00002'
                                            },{
                                                xtype: 'textfield',
                                                fieldLabel: '客户名称',
                                                value:'广州电网'
                                            },{
                                                xtype: 'textfield',
                                                fieldLabel: '重要程度分类',
                                                value:'vip客户'
                                            },{
                                                xtype: 'textfield',
                                                fieldLabel: '近期关注分类',
                                                value:'热点关注'
                                            },{
                                                xtype: 'textfield',
                                                fieldLabel: '自定义类型',
                                                value:'高价值客户'
                                            },{
                                                xtype: 'textfield',
                                                fieldLabel: '地域',
                                                value:'华南地区'
                                            },{
                                                xtype: 'textfield',
                                                fieldLabel: '业务机场',
                                                value:'南航机场'
                                            },{
                                                xtype: 'textfield',
                                                fieldLabel: '业务联系电话',
                                                value:'18922788282'
                                            },{
                                                xtype: 'combo',
                                                fieldLabel: '是否发货代理',
                                                value:'是'
                                            },{
                                                xtype : "combo",
						    					fieldLabel: '开发阶段',
						    					value:'合作客户'
                                                
                                            },{
                                                xtype: 'textfield',
                                                fieldLabel: '最后发货时间',
                                                value:'2011-09-26'
                                                
                                            }
                                        ]
                                    },{
                                    	columnWidth : .33,
                                        xtype: 'container',
                                        layout: {
                                            type: 'form'
                                        },
                                        defaults : {
											xtype : "textfield",
											width : 150
										},
                                        frame :true,
                                        flex: 1,
                                        items: [{
                                                xtype: 'combo',
                                                fieldLabel: '主营业务'
                                                
                                            },{
                                                xtype: 'textfield',
                                                fieldLabel: '地址',
                                                value:'上海市XXX路XXXX街'
                                            },{
                                                xtype: 'combo',
                                                fieldLabel: '客户来源类型',
                                                value:'网上搜索开发'
                                            },{
                                                xtype: 'combo',
                                                fieldLabel: '客户开源'
                                            },
                                           {
                                                xtype: 'textfield',
                                                fieldLabel: '完整注册名',
                                                value:'广州市电信网络注册有限公司'
                                            },{
                                                xtype: 'combo',
                                                fieldLabel: '工商号'
                                            }, {
                                                xtype: 'textfield',
                                                fieldLabel: '注册时间',
                                                value:'2011-01-01'
                                            },{
                                                xtype: 'textfield',
                                                tabTip:'多个电话已，隔开',
                                                fieldLabel: '固定电话',
                                                value:'18922788282,18522222222'
                                            },{
                                                xtype: 'textfield',
                                                fieldLabel: '企业法人',
                                                value:'张三'
                                            },{
                                                xtype: 'textfield',
                                                fieldLabel: '最后沟通时间',
                                                value:'2011-09-23'
                                            }
                                        ]
                                    },{
                                    	columnWidth : .33,
                                        xtype: 'fieldset',
                                        title:'经营信息',
                                        layout: {
                                            type: 'form'
                                        },
                                        defaults : {
											xtype : "textfield",
											width : 150
										},
                                        frame :true,
                                        flex: 1,
                                        items: [
                                            {
                                                xtype: 'textfield',
                                                fieldLabel: '经营范围',
                                                value:'服装运输'
                                            },{
                                                xtype: 'textfield',
                                                fieldLabel: '预计月货量',
                                                value:'50000'
                                            },{
                                                xtype: 'textfield',
                                                fieldLabel: '预计月营业额',
                                                value:'2000000'
                                            }, {
                                                xtype: 'combo',
                                                fieldLabel: '人员规模',
                                                value:500
                                            },{
                                                xtype: 'combo',
                                                fieldLabel: '发货周期',
                                                value:'3天'
                                                
                                            },{
                                                xtype: 'textfield',
                                                fieldLabel: '行业资讯',
                                                value:'服装包，时效要求比较高'
                                            },{
                                                xtype: 'textfield',
                                                fieldLabel: '合作资料',
                                                value:'东方物流，西方物流'
                                            },{
                                                xtype: 'textfield',
                                                fieldLabel: '财务状况',
                                                value:'货量充沛，发展速度稳健，财务状况健康'
                                            },{
                                                xtype: 'textfield',
                                                fieldLabel: '开始合作时间',
                                                value:'2011-01-01'
                                            }
                                        ]
                                    }
                                    
                                   
                                ]
                            },
                            {
                                xtype: 'panel',
                                title: '应收应付往来',
                                frame : true,
                                html:'<iframe src="http://localhost:9999/ais2/fi/fiReceivablestatementAction!input.action" height=100% width=100%/>'
                                
                            },
                            
                            
                            
                            
                            {
                                xtype: 'panel',
                                title: '联系人信息',
                                frame : true,
                                tbar: {
                                    xtype: 'toolbar',
                                    items: [
                                        {
                                            xtype: 'button',
                                            text: '新增联系人'
                                        },
                                        {
                                            xtype: 'tbseparator'
                                        },
                                        {
                                            xtype: 'button',
                                            text: '修改联系人'
                                        },
                                        {
                                            xtype: 'tbseparator'
                                        },
                                        {
                                            xtype: 'button',
                                            text: '删除联系人'
                                        }
                                    ]
                                },
                                items: [
                                    {
                                        xtype: 'grid',
                                        height: 265,
                                        store:store,
                                        bbar : new Ext.PagingToolbar({
											store : store,
											pageSize : 20,
											displayInfo : true,
											displayMsg : '显示第 {0} 条到 {1} 条记录，共 {2} 条',
											emptyMsg : "没有记录信息显示"
										}),
                                        title: '',
                                        columns: [
                                            {
                                                xtype: 'gridcolumn',
                                                dataIndex: 'f1',
                                                header: '姓名',
                                                sortable: true,
                                                width: 100
                                            },
                                            {
                                                xtype: 'gridcolumn',
                                                dataIndex: 'f2',
                                                header: '电话',
                                                sortable: true,
                                                width: 100
                                            },{
                                                xtype: 'gridcolumn',
                                                dataIndex: 'f10',
                                                header: '性别',
                                                sortable: true,
                                                width: 100
                                            },
                                            {
                                                xtype: 'gridcolumn',
                                                dataIndex: 'f3',
                                                header: '所在企业职责',
                                                sortable: true,
                                                width: 100
                                            },
                                            {
                                                xtype: 'gridcolumn',
                                                dataIndex: 'f4',
                                                header: '是否关键人物',
                                                sortable: true,
                                                width: 100
                                            },
                                            {
                                                xtype: 'gridcolumn',
                                                header: '生日',
                                                dataIndex: 'f5',
                                                sortable: true,
                                                width: 100
                                            },
                                            {
                                                xtype: 'gridcolumn',
                                                header: '兴趣爱好',
                                                dataIndex: 'f6',
                                                sortable: true,
                                                width: 100
                                            },
                                            {
                                                xtype: 'gridcolumn',
                                                header: '关联联系人',
                                                dataIndex: 'f7',
                                                sortable: true,
                                                width: 100
                                            },
                                            {
                                                xtype: 'gridcolumn',
                                                header: '关系',
                                                dataIndex: 'f8',
                                                sortable: true,
                                                width: 100
                                            },
                                            {
                                                xtype: 'gridcolumn',
                                                header: '最近次拜访时间',
                                                dataIndex: 'f9',
                                                sortable: true,
                                                width: 100
                                            }
                                        ]
                                    }
                                ]
                            },
                            {
                                xtype: 'panel',
                                title: '客户需求',
                                frame : true,
                                items: [
                                    {
                                        xtype: 'grid',
                                        height: 275,
                                        store:requestStore,
                                        title: '',
                                        bbar : new Ext.PagingToolbar({
											store : requestStore,
											pageSize : 20,
											displayInfo : true,
											displayMsg : '显示第 {0} 条到 {1} 条记录，共 {2} 条',
											emptyMsg : "没有记录信息显示"
										}),
                                        columns: [
                                            {
                                                xtype: 'gridcolumn',
                                                dataIndex: 'f1',
                                                header: '要求类型',
                                                sortable: true,
                                                width: 100
                                            },
                                            {
                                                xtype: 'gridcolumn',
                                                dataIndex: 'f2',
                                                header: '要求内容',
                                                sortable: true,
                                                width: 100
                                            },{
                                                xtype: 'gridcolumn',
                                                dataIndex: 'f5',
                                                header: '要求对接人',
                                                sortable: true,
                                                width: 100
                                            },{
                                                xtype: 'gridcolumn',
                                                dataIndex: 'f6',
                                                header: '是否采纳',
                                                sortable: true,
                                                width: 100
                                            },{
                                                xtype: 'gridcolumn',
                                                dataIndex: 'f7',
                                                header: '采纳方案（附件）',
                                                sortable: true,
                                                width: 100
                                            },
                                            {
                                                xtype: 'gridcolumn',
                                                dataIndex: 'f4',
                                                header: '需求采集人',
                                                sortable: true,
                                                width: 100
                                            },
                                            {
                                                xtype: 'gridcolumn',
                                                dataIndex: 'f3',
                                                header: '需求采集时间',
                                                sortable: true,
                                                width: 100
                                            }
                                        ],
                                        tbar: {
                                            xtype: 'toolbar',
                                            items: [
                                                {
                                                    xtype: 'button',
                                                    text: '新增客户要求'
                                                },
                                                {
                                                    xtype: 'tbseparator'
                                                },
                                                {
                                                    xtype: 'button',
                                                    text: '修改客户要求'
                                                },
                                                {
                                                    xtype: 'tbseparator'
                                                },
                                                {
                                                    xtype: 'button',
                                                    text: '删除客户要求'
                                                }
                                            ]
                                        }
                                    }
                                ]
                            }
                        ]
                    },
                    {
                        xtype: 'panel',
                        height: 250,
                       
                        title: '收货人信息管理',
                      //  collapsed : true,
                      	collapseFirst:true ,
                        collapsible:true,
                        items: [
                            {
                                xtype: 'grid',
                                height: 221,
                                store:store5,
                               	viewConfig : {
									columnsText : "显示的列",
									sortAscText : "升序",
									sortDescText : "降序",
									forceFit : true
								},
								frame : true,
								loadMask : true,
								sm : new Ext.grid.CheckboxSelectionModel(),
                                title: '',
                                columns: [new Ext.grid.CheckboxSelectionModel(),
                                    {
                                        header: '收货人姓名',
                                         draggable:true,
                                         dataIndex: 'name',
                                        sortable: true,
                                        width: 100
                                    },
                                    {
                                        header: '电话',
                                        sortable: true,
                                        dataIndex: 'tel',
                                        width: 100
                                    },
                                    {
                                        header: '地址',
                                        sortable: true,
                                        dataIndex: 'add',
                                        width: 200
                                    },
                                    
                                    {
                                        header: '货量',
                                        sortable: true,
                                        dataIndex: 'yufu',
                                        width: 100
                                    },
                                    {
                                        header: '货量百分比',
                                        sortable: true,
                                        dataIndex: 'lirunlv',
                                        width: 100
                                    },{
                                        header: '到付总额',
                                        sortable: true,
                                        dataIndex: 'daofu',
                                        width: 100
                                    },{
                                        header: '预付总额',
                                        sortable: true,
                                        dataIndex: 'yufu',
                                        width: 100
                                    },
                                    {
                                        header: '成本总额',
                                        sortable: true,
                                        dataIndex: 'chengben',
                                        width: 100
                                    },{
                                        header: '利润总额',
                                        sortable: true,
                                        dataIndex: 'lirun',
                                        width: 100
                                    },{
                                        header: '利润率',
                                        sortable: true,
                                        dataIndex: 'lirunlv',
                                        width: 100
                                    },{
                                        header: '到付收银',
                                        sortable: true,
                                        dataIndex: 'daofushouyin',
                                        width: 100
                                    },{
                                        header: '专车',
                                        sortable: true,
                                        dataIndex: 'zhuanche',
                                        width: 100
                                    },{
                                        header: '市内送货',
                                        sortable: true,
                                        dataIndex: 'shineisonghuo',
                                        width: 100
                                    },{
                                        header: '市内自提',
                                        sortable: true,
                                        dataIndex: 'shineiziti',
                                        width: 100
                                    },
                                    
                                    {
                                        header: '代收总额',
                                        sortable: true,
                                        dataIndex: 'daishou',
                                        width: 100
                                    },
                                     {
                                         header: '查看收货人货量趋势图',
                                         dataIndex: 'tel',
                                         width: 200
                                         
                                     }
                                     
                                    
                                ],
                                tbar: {
                                    xtype: 'toolbar',
                                    items: [
                                        {
                                            xtype: 'button',
                                            text: '收货人转客户'
                                        },
                                        {
                                            xtype: 'tbseparator'
                                        },
                                       
                                       
                                        {
                                            xtype: 'button',
                                            text: '货量统计饼状图'
                                        },
                                        {
                                            xtype: 'tbseparator'
                                        },
                                        {
                                            xtype: 'combo',
                                            emptyText : '选择查询方式'
                                        },
                                        {
                                            xtype: 'tbseparator'
                                        },
                                        {
                                            xtype: 'textfield'
                                        },
                                        {
                                            xtype: 'button',
                                            text: '查 询'
                                        }
                                    ]
                                }
                            }
                        ]
                    },
                    {
                        xtype: 'panel',
                        title: '开发过程管理',
              //          collapsed : true,
              			frame : true,
                        collapsible:true,
                        items: [
                            {
                                xtype: 'grid',
                                height: 240,
                                store:store6,
                                title: '',
                                tbar: {
                                    xtype: 'toolbar',
                                    items: [
                                        {
                                            xtype: 'button',
                                            text: '新建开发过程'
                                        },
                                        {
                                            xtype: 'tbseparator'
                                        }, {
                                            xtype: 'button',
                                            text: '修改开发过程'
                                        },
                                        {
                                            xtype: 'tbseparator'
                                        } ,{
                                            xtype: 'button',
                                            text: '删除开发过程'
                                        },
                                        {
                                            xtype: 'tbseparator'
                                        }
                                    ]
                                },
                                bbar : new Ext.PagingToolbar({
											store:store6,
											pageSize : 20,
											displayInfo : true,
											displayMsg : '显示第 {0} 条到 {1} 条记录，共 {2} 条',
											emptyMsg : "没有记录信息显示"
										}),
                                columns: [
                                    {
                                        dataIndex: 'f1',
                                        header: '过程名称',
                                        sortable: true,
                                        width: 80
                                    },
                                    {
                                        dataIndex: 'f2',
                                        header: '过程类型',
                                        sortable: true,
                                        width: 80
                                    },
                                    {
                                        dataIndex: 'f3',
                                        header: '开发阶段',
                                        sortable: true,
                                        width: 100
                                    },
                                    {
                                        dataIndex: 'f4',
                                        header: '过程经过',
                                        sortable: true,
                                        width: 80
                                    },
                                    {
                                        dataIndex: 'f5',
                                        header: '开发成本',
                                        sortable: true,
                                        width: 80
                                    },{
                                        dataIndex: 'f10',
                                        header: '附件',
                                        sortable: true,
                                        width: 80
                                    },
                                    {
                                        dataIndex: 'f11',
                                        header: '活动对象',
                                        sortable: true,
                                        width: 80
                                    },
                                    {
                                        xtype: 'gridcolumn',
                                        header: '开发人',
                                        dataIndex: 'f6',
                                        sortable: true,
                                        width: 100
                                    },{
                                        xtype: 'gridcolumn',
                                        header: '开发时间',
                                        dataIndex: 'f7',
                                        sortable: true,
                                        width: 100
                                    },{
                                        xtype: 'gridcolumn',
                                        header: '是否审核',
                                        dataIndex: 'f8',
                                        sortable: true,
                                        width: 100
                                    },{
                                        xtype: 'gridcolumn',
                                        header: '审核时间',
                                        dataIndex: 'f9',
                                        sortable: true,
                                        width: 100
                                    },{
                                        xtype: 'gridcolumn',
                                        header: '备注',
                                        dataIndex: 'f4',
                                        sortable: true,
                                        width: 100
                                    }
                                ]
                                
                            }
                        ]
                    },
                    new MyPanelUi({
                    	collapseFirst:true ,
        				collapsible:true
                    }),
                    new CargoTrend({
                    	collapseFirst:true ,
        				collapsible:true
                    }),
                    {
                        xtype: 'panel',
                        height: 400,
                        title: '品质管理',
                         layout: {
				                            type: 'fit'
				                        },
                        collapsible:true,
                        items: [ {
		                        xtype: 'tabpanel',
		                        height: 320,
		                        activeItem: 0,
		                        activeTab: 0,
		                        
		                        items: [
		                        		{
		                        		xtype: 'panel',
		                                 layout: {
				                            type: 'fit'
				                        },
		                                title:'异常管理',
		                                html:'<iframe src="http://localhost:9999/ais2/exception/oprExceptionAction!input.action" height=100% width=100%/>'
		                                
			                        	
	                            },{
	                                title:'签收时效',
	                                 html:'<img src="shixiao.jpg" height=100% width=100%/>'
	                                },
	                                {
		                                height: 200,
		                                title:'原件签单时效',
		                                html:'<img src="return.jpg" height=100% width=100%/>'
		                             }
		                        ]
                        
                            
                   		 }]
                    },
                    {
                        xtype: 'panel',
                        height: 250,
                        collapsible:true,
                        frame:true,
                        title: '投诉与建议',
                         layout: {
                            type: 'fit'
                        },
                        items: [
                            {
                                xtype: 'grid',
                                autoScroll: true,
                                width: Ext.lib.Dom.getViewWidth()-30,
                                store:myStore,
                                title: '',
                                columns: [
                                    {
                                        dataIndex: 'f1',
                                        header: '投诉客户',
                                        sortable: true,
                                        width: 100
                                    },
                                    {
                                        dataIndex: 'f2',
                                        header: '投诉单号',
                                        sortable: true,
                                        width: 100
                                    },
                                    {
                                        dataIndex: 'f2',
                                        header: '投诉事件',
                                        sortable: true,
                                        width: 100
                                    },
                                    {
                                        dataIndex: 'f3',
                                        header: '投诉类型',
                                        sortable: true,
                                        width: 100
                                    },
                                    {
                                        dataIndex: 'f4',
                                        header: '投诉内容',
                                        sortable: true,
                                        width: 100
                                    },{
                                        dataIndex: 'f5',
                                        header: '受理人',
                                        sortable: true,
                                        width: 100
                                    },{
                                        dataIndex: 'f6',
                                        header: '受理时间',
                                        sortable: true,
                                        width: 100
                                    },
                                    {
                                        dataIndex: 'f7',
                                        header: '是否采纳',
                                        sortable: true,
                                        width: 100
                                    },{
                                        dataIndex: 'f8',
                                        header: '采纳人',
                                        sortable: true,
                                        width: 100
                                    },{
                                        dataIndex: 'f9',
                                        header: '采纳时间',
                                        sortable: true,
                                        width: 100
                                    },{
                                        dataIndex: 'f11',
                                        header: '附件',
                                        sortable: true,
                                        width: 100
                                    },{
                                        dataIndex: 'f13',
                                        header: '处理责任人',
                                        sortable: true,
                                        width: 100
                                    },{
                                        dataIndex: 'f14',
                                        header: '处理时间',
                                        sortable: true,
                                        width: 100
                                    },{
                                        dataIndex: 'f10',
                                        header: '处理结果',
                                        sortable: true,
                                        width: 100
                                    },{
                                        dataIndex: 'f12',
                                        header: '处理成本',
                                        sortable: true,
                                        width: 100
                                    },{
                                        dataIndex: 'f15',
                                        header: '成本审核',
                                        sortable: true,
                                        hidden:true,
                                        width: 100
                                    },{
                                        dataIndex: 'f16',
                                        header: '审核人',
                                        sortable: true,
                                        hidden:true,
                                        width: 100
                                    },{
                                        dataIndex: 'f17',
                                        header: '审核时间',
                                         hidden:true,
                                        sortable: true,
                                        width: 100
                                    }
                                ]
                            }
                        ],
                        tbar: {
                            xtype: 'toolbar',
                            items: [
                                {
                                    xtype: 'button',
                                    text: '新增投诉'
                                },
                                {
                                    xtype: 'tbseparator'
                                },
                                {
                                    xtype: 'button',
                                    text: '修改投诉'
                                },
                                {
                                    xtype: 'tbseparator'
                                },
                                {
                                    xtype: 'button',
                                    text: '删除投诉'
                                }
                            ]
                        }
                    },
                    {
                        xtype: 'panel',
                        height: 250,
                        title: '销售机会管理',
                         layout: {
                            type: 'fit'
                        },
                        collapsible:true,
                        items: [
                            {
                                xtype: 'grid',
                                store:myStore,
                                title: '',
                                columns: [
                                    {
                                        dataIndex: 'string',
                                        header: '指标值',
                                        sortable: true,
                                        width: 100
                                    },
                                     {
                                        dataIndex: 'string',
                                        header: '指标开始日期',
                                        sortable: true,
                                        width: 100
                                    },
                                    {
                                        dataIndex: 'string',
                                        header: '指标结束日期',
                                        sortable: true,
                                        width: 100
                                    },
                                    {
                                        dataIndex: 'number',
                                        header: '实际完成指标',
                                        sortable: true,
                                        width: 100
                                    },
                                    {
                                        dataIndex: 'date',
                                        header: '指标完成率',
                                        sortable: true,
                                        width: 100
                                    },
                                    {
                                        xtype: 'booleancolumn',
                                        dataIndex: 'bool',
                                        header: '指标时间使用率',
                                        sortable: true,
                                        width: 100
                                    }
                                ],
                                tbar: {
                                    xtype: 'toolbar',
                                    items: [
                                        {
                                            xtype: 'button',
                                            text: '新增销售机会'
                                        },
                                        {
                                            xtype: 'tbseparator'
                                        },
                                        {
                                            xtype: 'button',
                                            text: '修改销售机会'
                                        },
                                        {
                                            xtype: 'tbseparator'
                                        },
                                        {
                                            xtype: 'button',
                                            text: '删除销售机会'
                                        }
                                    ]
                                }
                            }
                        ]
                    }
                ],
                tbar: {
                    xtype: 'toolbar',
                    items: [
                        {
                            xtype: 'button',
                            text: '客户定位'
                        },
                        {
                            xtype: 'tbseparator'
                        }
                    ]
                }
            }
        ];
        MyViewportUi.superclass.initComponent.call(this);
    }
});
Ext.onReady(function(){
			store.loadData(myData);
			requestStore.loadData(requestData);
			store3.loadData(data3);
			new MyViewportUi();
		});
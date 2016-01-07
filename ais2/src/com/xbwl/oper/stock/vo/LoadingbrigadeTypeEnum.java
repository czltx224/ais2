package com.xbwl.oper.stock.vo;

/**
 * 货物统计类型
 * @author czl
 *
 */
public enum LoadingbrigadeTypeEnum {
	XIEHUO(0l),//卸货 0
	ZHUANGHUO(1l),//装货 1	
	TIHUO(2l),//提货 2	
	JIEHUO(4l);//接货 4
	
	private final Long value;
	
    public Long getValue() {
        return value;
    }
    //构造器默认也只能是private, 从而保证构造函数只能在内部使用
    private LoadingbrigadeTypeEnum(Long value) {
        this.value = value;
    }
}

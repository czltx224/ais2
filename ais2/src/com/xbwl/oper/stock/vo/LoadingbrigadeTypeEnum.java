package com.xbwl.oper.stock.vo;

/**
 * ����ͳ������
 * @author czl
 *
 */
public enum LoadingbrigadeTypeEnum {
	XIEHUO(0l),//ж�� 0
	ZHUANGHUO(1l),//װ�� 1	
	TIHUO(2l),//��� 2	
	JIEHUO(4l);//�ӻ� 4
	
	private final Long value;
	
    public Long getValue() {
        return value;
    }
    //������Ĭ��Ҳֻ����private, �Ӷ���֤���캯��ֻ�����ڲ�ʹ��
    private LoadingbrigadeTypeEnum(Long value) {
        this.value = value;
    }
}

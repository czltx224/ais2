package com.xbwl.cus.dto;

import java.util.Comparator;

/**
 * ����Ϊ�Ƚ�CusGoodsXmlDto��С����������࣬ʵ��Comparator�ӿ�
 *@author LiuHao
 *@time Oct 18, 2011 10:42:27 AM
 */
public class CompartorCusDto implements Comparator {
	//REVIEW-ANN ����ע�ͺͶԷ���ֵ��˵��
	//FIXED LIUH
	//�Ƚ�CusGoodsXmlDto ����Ĵ�С ��дcompare����
	public int compare(Object o1, Object o2) {
		CusGoodsXmlDto c1=(CusGoodsXmlDto)o1;
		CusGoodsXmlDto c2=(CusGoodsXmlDto)o2;
		int c1mon=Integer.valueOf(c1.getArrName().split("��")[0]);
		int c2mon=Integer.valueOf(c2.getArrName().split("��")[0]);
		//c1mon>c2mon �����c1>c2 ����1 ����֮
		if(c1mon>c2mon){
			return 1;
		}else{
			return 0;
		}
	}

}

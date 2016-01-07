package com.xbwl.cus.dto;

import java.util.Comparator;

/**
 * 此类为比较CusGoodsXmlDto大小用来排序的类，实现Comparator接口
 *@author LiuHao
 *@time Oct 18, 2011 10:42:27 AM
 */
public class CompartorCusDto implements Comparator {
	//REVIEW-ANN 增加注释和对返回值做说明
	//FIXED LIUH
	//比较CusGoodsXmlDto 对象的大小 重写compare方法
	public int compare(Object o1, Object o2) {
		CusGoodsXmlDto c1=(CusGoodsXmlDto)o1;
		CusGoodsXmlDto c2=(CusGoodsXmlDto)o2;
		int c1mon=Integer.valueOf(c1.getArrName().split("月")[0]);
		int c2mon=Integer.valueOf(c2.getArrName().split("月")[0]);
		//c1mon>c2mon 则对象c1>c2 返回1 ，反之
		if(c1mon>c2mon){
			return 1;
		}else{
			return 0;
		}
	}

}

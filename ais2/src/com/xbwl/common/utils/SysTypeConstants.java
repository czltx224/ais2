package com.xbwl.common.utils;

public class SysTypeConstants {
	
	
	public enum SysTypeEnum{
		// REVIEW ������Ч���룿
		ringType(20l,"��������"),languageType(5l,"��������"),musicType(2l,"��������"),musicFileType(3l,"�����ļ�����"),
		albumType(18l,"ר������"),videoType(10l,"��Ƶ����"),unit(25l,"������λ����");
		
		private Long typeCategoryid;
		private String typeCategoryName;
		
		SysTypeEnum(Long typeCategoryid,String typeCategoryName){
			this.typeCategoryid=typeCategoryid;
			this.typeCategoryName=typeCategoryName;
		}
		public Long getTypeCategoryid(){
			return this.typeCategoryid;
		}
		public String getTypeCategoryName(){
			return this.typeCategoryName;
		}
	}
	

}

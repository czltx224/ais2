package com.xbwl.common.utils;

public class SysTypeConstants {
	
	
	public enum SysTypeEnum{
		// REVIEW 疑似无效代码？
		ringType(20l,"铃音类型"),languageType(5l,"语言类型"),musicType(2l,"音乐类型"),musicFileType(3l,"音乐文件类型"),
		albumType(18l,"专辑类型"),videoType(10l,"视频类型"),unit(25l,"计量单位类型");
		
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

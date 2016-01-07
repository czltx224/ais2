package com.xbwl.common.utils;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Administrator
 * @createTime 6:30:35 PM
 * @updateName Administrator
 * @updateTime 6:30:35 PM
 * 
 */
@Documented  
@Retention(RetentionPolicy.RUNTIME)   
@Target({ElementType.METHOD}) 
public @interface OprHistory {
	String oprHistory() default "history";
}

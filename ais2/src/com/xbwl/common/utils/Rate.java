package com.xbwl.common.utils;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author CaoZhili
 * time Aug 11, 2011 10:01:09 AM
 */
@Documented  
@Retention(RetentionPolicy.RUNTIME)   
@Target({ElementType.FIELD,ElementType.TYPE})  
public @interface Rate {
	String mapping();

}

package com.xbwl.common.log.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.xbwl.common.utils.LogType;

/**
 * 日志记录Annotation,定义在方法上面，用于传递Serivce层具体的模块名称
 * @author yab
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ModuleName {
	String value();
	LogType logType();
	boolean isSearch() default false;
}

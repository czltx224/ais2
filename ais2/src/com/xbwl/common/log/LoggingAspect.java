package com.xbwl.common.log;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.Table;
import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.ralasafe.WebRalasafe;
import org.ralasafe.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionContext;
import com.xbwl.common.exception.ServiceException;
import com.xbwl.common.log.anno.ModuleName;
import com.xbwl.common.log.service.ISysLogService;
import com.xbwl.common.utils.AnnotationUtils;
import com.xbwl.common.utils.HibernateParter;
import com.xbwl.common.utils.LogType;
import com.xbwl.common.utils.XbwlInt;
import com.xbwl.common.web.struts2.Struts2Utils;
import com.xbwl.entity.SysLog;

/**
 * 日志记录Aspect
 * 
 * @author yab
 */
@Aspect
@Component
public class LoggingAspect {

	private static Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

	@Resource
	private ISysLogService sysLogService;

	@Value("${aspect.isOpen}")
	private boolean isOpen;

	@Value("${aspect.time}")
	private int aspectTime;
	
	@Value("${aspect.isCheckMemory}")
	private boolean isCheckMemory ;

	private SimpleJdbcTemplate jdbcTemplate;


	@Autowired
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new SimpleJdbcTemplate(dataSource);
	}

	/**用于监控前台数据
	 * @param joinPoint
	 * @return
	 * @throws Throwable
	 *             //TODO 保存监控
	 */

	@SuppressWarnings("unused")
	@Around("target(com.xbwl.common.service.impl.BaseServiceImpl) && args(auditableEntity)")

	private Object onSaveTestTs(ProceedingJoinPoint joinPoint,com.xbwl.common.orm.hibernate.pojo.AuditableEntity auditableEntity) throws Throwable {
		User user = null;
		try{
			user = WebRalasafe.getCurrentUser(Struts2Utils.getRequest());
		}catch (Exception e) {
			
		}
		
		String idColName=HibernateParter.getIDName(auditableEntity);
		
		Object id=HibernateParter.getIDValue(auditableEntity);
		
		if(id!=null){
			String tableName = AnnotationUtils.getValue(
					auditableEntity.getClass().getAnnotation(Table.class),
					"name").toString();

			Map map = jdbcTemplate.queryForMap(" select ts from " + tableName
					+ " where  " + idColName + "=?", id);

			MethodSignature methodSignature = (MethodSignature) joinPoint
			.getSignature();
			Method method = methodSignature.getMethod();
			
			Annotation annotation=AnnotationUtils.getAnnotation(method, XbwlInt.class);
			if (map.get("TS") != null ) {
				if(null!=annotation && !Boolean.getBoolean(AnnotationUtils.getValue(annotation,"isCheck").toString())){
					logger.debug("因为有注释，跳过时间戳检查！");
				}else if (!map.get("TS").equals(auditableEntity.getTs())) {
					throw new ServiceException("保存失败，可能别人已经修改了此条记录，请刷新后再试");
				}
			}
		}

		return joinPoint.proceed();
	}

	/**
	 * 定义切入点,匹配使用@ModuleName注解的方法
	 * 
	 * @param moduleName
	 * @return
	 * @throws Throwable
	 */
	@SuppressWarnings("unused")
	@Around("@annotation(moduleName)")
	private Object annotationMethod(ProceedingJoinPoint joinPoint,
			ModuleName moduleName) throws Throwable {
		if(isOpen){
			return this.saveAroundLog(joinPoint, LogType.getLogType(moduleName
				.logType()), moduleName.value());
		}else{
			return joinPoint.proceed();
		}
	}


	// REVIEW-ACCEPT 增加方法注释
	/**ASPECT，用于监控系统用户操作，保存操作日志
	 * @param joinPoint
	 * @param logType
	 * @param moduleName
	 * @param flag
	 * @return
	 * @throws Throwable
	 *
	 */
	//FIXED
	private Object saveAroundLog(ProceedingJoinPoint joinPoint, String logType,
			String moduleName) throws Throwable {

		Object returnValue = null; // 目标方法返回值
		String returnMessage = null;

		User user = null;
		// 构造日志对象
		try{
			user = WebRalasafe.getCurrentUser(Struts2Utils.getRequest());
		}catch (Exception e) {
			user = null;
		}

		SysLog sysLog = new SysLog();
		
		//检查方法执行时的内存
		if(isCheckMemory){
			Runtime lRuntime = Runtime.getRuntime();
			// REVIEW-ACCEPT 这部分代码在非windows平台可能会失去意义。根据部署方案，应该在非Windows平台上运行
			// REVIEW-ACCEPT 另外runtime消耗较大，用于跟踪用的信息，应当使用参数来决定是否打开跟踪，以避免性能消耗。
			//FIXED 加入配置文件判断
			sysLog.setStartMem(lRuntime.freeMemory() / 1024 / 1024);
			sysLog.setStartTotal(lRuntime.totalMemory() / 1024 / 1024);
		}

		sysLog.setCreatedtime(new Date());
		if (null != ActionContext.getContext()) {
			sysLog.setClientIp(Struts2Utils.getRequest().getRemoteAddr());
		} else {
			sysLog.setClientIp("未登录");
		}
		sysLog.setLogType(logType);
		sysLog.setModuleName(moduleName);

		if (null != user) {
			sysLog.setOperAccount(user.get("name").toString());
		} else {
			// REVIEW-ACCEPT user是否必须为实际用户，是则需要处理异常，否则将account记录为系统任务或默认任务，以区别用户操作
			//FIXED 用户在每次请求的时候都有filter进行拦截，因此不会存在用户不存在的现象，此等情况应该是系统在自动自行。不能做异常处理，因为定时调度会调用系统的方法
			sysLog.setOperAccount("系统任务");
		}

		MethodSignature methodSignature = (MethodSignature) joinPoint
				.getSignature();
		Method method = methodSignature.getMethod();

		Class<?>[] parameterTypes = method.getParameterTypes();

		StringBuffer parameter = new StringBuffer();
		parameter.append("调用方法：");
		parameter.append(joinPoint.getTarget().getClass().getName())
				.append(".").append(joinPoint.getSignature().getName()).append(
						"(");
		for (int i = 0; i < parameterTypes.length; i++) { // 记录目标方法形参列表
			parameter.append(parameterTypes[i].getName()).append(",");
		}
		parameter.deleteCharAt(parameter.length() - 1);
		parameter.append(")");
		parameter.append("<br>传递参数:");

		// 获取目标方法传入参数值
		Object[] args = joinPoint.getArgs();

		if (args != null && args.length != 0) {
			parameter.append("(");
			for (int i = 0; i < args.length; i++) {
				parameter.append(args[i] == null ? "null" : args[i])
						.append(",");
			}
			parameter.deleteCharAt(parameter.length() - 1);
			parameter.append(")");
		} else {
			parameter.append("none");
		}
		// REVIEW-ACCEPT 日志类由于调用较多，要使用到append方法，所以不需要过早进行转型
		//FIXED 转型放后

		// 计算方法执行时间
		Long startTime = System.currentTimeMillis();

		try {
			returnValue = joinPoint.proceed(); // 执行目标方法

			sysLog.setOperStatus("操作成功");
		} catch (Throwable e) {
			returnMessage = "<br>异常类型:" + e.getClass().getName() + "<br>异常消息："
					+ e.getMessage();

			sysLog.setOperStatus("<font color='red'>操作失败</font>");

			throw e;
		} finally {
			
			Long endTime = System.currentTimeMillis();

			if (null != returnMessage) {
					// REVIEW-ACCEPT 则获取0到（900-长度）的内容？有可能900 - returnMessage.length()为负
					// REVIEW-ACCEPT 日志类由于调用较多，文本追加不要用+，而是用append方法
					//FIXED 考虑到方法及参数 以及异常为系统监控的重要数据，因此不再做数据截取
					parameter.append(returnMessage);
			}
			// REVIEW-ACCEPT 上面的代码已经限制长度，这里再次追加内容是否会有溢出的风险？
			//FIXED 数据库字段为是LONG型，基本不会溢出,将返货值进行截取处理。
			parameter.append( "<br>返回值："+ (returnValue == null ? "void" :StringUtils.substring(returnValue.toString(), 1000)));

			// 设置日志的详细信息
			sysLog.setOperDetail(parameter.toString());
			// 设置方法的调用时间
			sysLog.setInvokeTime(endTime - startTime);

			// 保存日志记录
			// REVIEW-ACCEPT 这部分代码在非windows平台可能会失去意义。根据部署方案，应该在非Windows平台上运行
			//检查方法执行后的内存
			//FIXED 加入配置文件判断
			if(isCheckMemory){
				Runtime lRuntime = Runtime.getRuntime();
					sysLog.setEndTotal(lRuntime.totalMemory() / 1024 / 1024);
					sysLog.setEndMem(lRuntime.freeMemory() / 1024 / 1024);
			}

			if (aspectTime == -1 || sysLog.getInvokeTime() > aspectTime)
				sysLogService.save(sysLog);
				
		}
		return returnValue;
	}



}

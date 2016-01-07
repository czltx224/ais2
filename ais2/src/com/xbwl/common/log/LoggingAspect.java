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
 * ��־��¼Aspect
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

	/**���ڼ��ǰ̨����
	 * @param joinPoint
	 * @return
	 * @throws Throwable
	 *             //TODO ������
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
					logger.debug("��Ϊ��ע�ͣ�����ʱ�����飡");
				}else if (!map.get("TS").equals(auditableEntity.getTs())) {
					throw new ServiceException("����ʧ�ܣ����ܱ����Ѿ��޸��˴�����¼����ˢ�º�����");
				}
			}
		}

		return joinPoint.proceed();
	}

	/**
	 * ���������,ƥ��ʹ��@ModuleNameע��ķ���
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


	// REVIEW-ACCEPT ���ӷ���ע��
	/**ASPECT�����ڼ��ϵͳ�û����������������־
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

		Object returnValue = null; // Ŀ�귽������ֵ
		String returnMessage = null;

		User user = null;
		// ������־����
		try{
			user = WebRalasafe.getCurrentUser(Struts2Utils.getRequest());
		}catch (Exception e) {
			user = null;
		}

		SysLog sysLog = new SysLog();
		
		//��鷽��ִ��ʱ���ڴ�
		if(isCheckMemory){
			Runtime lRuntime = Runtime.getRuntime();
			// REVIEW-ACCEPT �ⲿ�ִ����ڷ�windowsƽ̨���ܻ�ʧȥ���塣���ݲ��𷽰���Ӧ���ڷ�Windowsƽ̨������
			// REVIEW-ACCEPT ����runtime���Ľϴ����ڸ����õ���Ϣ��Ӧ��ʹ�ò����������Ƿ�򿪸��٣��Ա����������ġ�
			//FIXED ���������ļ��ж�
			sysLog.setStartMem(lRuntime.freeMemory() / 1024 / 1024);
			sysLog.setStartTotal(lRuntime.totalMemory() / 1024 / 1024);
		}

		sysLog.setCreatedtime(new Date());
		if (null != ActionContext.getContext()) {
			sysLog.setClientIp(Struts2Utils.getRequest().getRemoteAddr());
		} else {
			sysLog.setClientIp("δ��¼");
		}
		sysLog.setLogType(logType);
		sysLog.setModuleName(moduleName);

		if (null != user) {
			sysLog.setOperAccount(user.get("name").toString());
		} else {
			// REVIEW-ACCEPT user�Ƿ����Ϊʵ���û���������Ҫ�����쳣������account��¼Ϊϵͳ�����Ĭ�������������û�����
			//FIXED �û���ÿ�������ʱ����filter�������أ���˲�������û������ڵ����󣬴˵����Ӧ����ϵͳ���Զ����С��������쳣������Ϊ��ʱ���Ȼ����ϵͳ�ķ���
			sysLog.setOperAccount("ϵͳ����");
		}

		MethodSignature methodSignature = (MethodSignature) joinPoint
				.getSignature();
		Method method = methodSignature.getMethod();

		Class<?>[] parameterTypes = method.getParameterTypes();

		StringBuffer parameter = new StringBuffer();
		parameter.append("���÷�����");
		parameter.append(joinPoint.getTarget().getClass().getName())
				.append(".").append(joinPoint.getSignature().getName()).append(
						"(");
		for (int i = 0; i < parameterTypes.length; i++) { // ��¼Ŀ�귽���β��б�
			parameter.append(parameterTypes[i].getName()).append(",");
		}
		parameter.deleteCharAt(parameter.length() - 1);
		parameter.append(")");
		parameter.append("<br>���ݲ���:");

		// ��ȡĿ�귽���������ֵ
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
		// REVIEW-ACCEPT ��־�����ڵ��ý϶࣬Ҫʹ�õ�append���������Բ���Ҫ�������ת��
		//FIXED ת�ͷź�

		// ���㷽��ִ��ʱ��
		Long startTime = System.currentTimeMillis();

		try {
			returnValue = joinPoint.proceed(); // ִ��Ŀ�귽��

			sysLog.setOperStatus("�����ɹ�");
		} catch (Throwable e) {
			returnMessage = "<br>�쳣����:" + e.getClass().getName() + "<br>�쳣��Ϣ��"
					+ e.getMessage();

			sysLog.setOperStatus("<font color='red'>����ʧ��</font>");

			throw e;
		} finally {
			
			Long endTime = System.currentTimeMillis();

			if (null != returnMessage) {
					// REVIEW-ACCEPT ���ȡ0����900-���ȣ������ݣ��п���900 - returnMessage.length()Ϊ��
					// REVIEW-ACCEPT ��־�����ڵ��ý϶࣬�ı�׷�Ӳ�Ҫ��+��������append����
					//FIXED ���ǵ����������� �Լ��쳣Ϊϵͳ��ص���Ҫ���ݣ���˲��������ݽ�ȡ
					parameter.append(returnMessage);
			}
			// REVIEW-ACCEPT ����Ĵ����Ѿ����Ƴ��ȣ������ٴ�׷�������Ƿ��������ķ��գ�
			//FIXED ���ݿ��ֶ�Ϊ��LONG�ͣ������������,������ֵ���н�ȡ����
			parameter.append( "<br>����ֵ��"+ (returnValue == null ? "void" :StringUtils.substring(returnValue.toString(), 1000)));

			// ������־����ϸ��Ϣ
			sysLog.setOperDetail(parameter.toString());
			// ���÷����ĵ���ʱ��
			sysLog.setInvokeTime(endTime - startTime);

			// ������־��¼
			// REVIEW-ACCEPT �ⲿ�ִ����ڷ�windowsƽ̨���ܻ�ʧȥ���塣���ݲ��𷽰���Ӧ���ڷ�Windowsƽ̨������
			//��鷽��ִ�к���ڴ�
			//FIXED ���������ļ��ж�
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

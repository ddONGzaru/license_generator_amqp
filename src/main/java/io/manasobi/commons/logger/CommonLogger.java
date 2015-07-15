package io.manasobi.commons.logger;

import org.apache.commons.lang3.ClassUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@SuppressWarnings("rawtypes") 
public final class CommonLogger {
	
	private CommonLogger() { }
	
	private static Logger logger = LoggerFactory.getLogger("manasobi");
	
	public static void debug(String msg, Class clazz) {
		
		if (logger.isDebugEnabled()) {
			logger.debug(parseLogPrifix(clazz) + msg);			
		}
	}
	
	public static void info(String msg, Class clazz) {
		if (logger.isInfoEnabled()) {
			logger.info(parseLogPrifix(clazz) + msg);			
		}
	}
	
	public static void warn(String msg, Class clazz) {
		
		if (logger.isWarnEnabled()) {
			logger.warn(parseLogPrifix(clazz) + msg);			
		}
	}
	
	public static void error(String msg, Class clazz) {
		
		if (logger.isErrorEnabled()) {
			logger.error(parseLogPrifix(clazz) + msg);
		}
	}

	public static void trace(String msg, Class clazz) {
		
		if (logger.isTraceEnabled()) {
			logger.trace(parseLogPrifix(clazz) + msg);			
		}
	}

	private static String parseLogPrifix(Class clazz) {
		
		String packageName = ClassUtils.getPackageName(clazz);
		
		if (packageName.startsWith("io.manasobi.commons")) {
			return "[commons] "; 
		} else {
			return "[license] ";
		}		
	}

}

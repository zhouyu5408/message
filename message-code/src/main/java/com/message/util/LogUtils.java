package com.message.util;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * 日志处理工具<br/>
 * 
 * @Date: 2017年11月27日 上午9:54:41 <br/>
 *
 * @author zhouyu
 * @version 1.0 Copyright (YLINK) , All Rights Reserved.
 * @since JDK 1.7
 */
public class LogUtils {

	private static final Level INFO = Level.INFO;

	private static final Level DEBUG = Level.DEBUG;

	private static final Level WARN = Level.WARN;

	private static final Level ERROR = Level.ERROR;

	private static final Level ALARM = Level.FATAL;

	public static Logger logger = Logger.getLogger("com.simpro.core.util");

	// public static Logger logger =
	// Logger.getLogger(sun.reflect.Reflection.getCallerClass(2));
	// static
	// {
	// logger = Logger.getLogger(LogUtils.class);
	// InputStream is = null;
	// try {
	// is = new FileInputStream(System.getProperty("log4jFile"));
	// Properties logProps = new Properties();
	// logProps.load(is);
	// PropertyConfigurator.configure(logProps);
	// }
	// catch (Exception e) {
	// System.err.println("初始化日志配置信息时发生以下错误：" + e.getMessage());
	// }finally{
	// try{
	// is.close();
	// }catch(Exception e){
	// System.err.println("日志流关闭失败：" + e.getMessage());
	// }
	// }
	// }

	/**
	 * 按照日志配置信息写日志
	 *
	 * @param logMessage
	 *            日志消息
	 * @param errorLevel
	 *            日志级别
	 * @param e
	 *            例外
	 */
	private static void printLog(String logMessage, Level errorLevel, Throwable e) {
		try {
			StringBuffer sbLog = new StringBuffer();

			sbLog.append("【线程编号：");
			sbLog.append(Thread.currentThread().getName());
			sbLog.append("】");
			if (errorLevel == null || errorLevel == INFO) {
				errorLevel = INFO;
				sbLog.append("【信息】");
			} else if (errorLevel == DEBUG) {
				sbLog.append("【调试】");
			} else if (errorLevel == WARN) {
				sbLog.append("【警告】");
			} else if (errorLevel == ERROR) {
				sbLog.append("【错误】");
			} else if (errorLevel == ALARM) {
				sbLog.append("【严重错误】");
			}
			sbLog.append(logMessage);
			logger.log(errorLevel, sbLog.toString(), e);
		} catch (Exception ex) {
			logger.error("记录日志时发生以下错误：" + ex.getMessage());
		}
	}

	/**
	 * 按照日志配置信息写日志
	 * 
	 * @param logMessage
	 *            日志消息
	 * @param errorLevel
	 *            日志级别
	 */
	private static void printLog(String logMessage, Level errorLevel) {
		printLog(logMessage, errorLevel, null);
	}

	/**
	 * 错误日志
	 * 
	 * @param object
	 */
	public static void error(Object object) {
		printLog(object.toString(), ERROR);
	}

	/**
	 * 错误日志
	 * 
	 * @param object
	 * @param t
	 */
	public static void error(Object object, Throwable t) {
		printLog(object.toString(), ERROR, t);
	}

	/**
	 * 调试错误日志
	 * 
	 * @param object
	 */
	public static void debug(Object object) {
		printLog(object.toString(), DEBUG);
	}

	/**
	 * 调试错误日志
	 * 
	 * @param object
	 * @param t
	 */
	public static void debug(Object object, Throwable t) {
		printLog(object.toString(), DEBUG, t);
	}

	/**
	 * 信息日志
	 * 
	 * @param object
	 */
	public static void info(Object object) {
		printLog(object.toString(), INFO);
	}

	/**
	 * 信息日志
	 * 
	 * @param object
	 * @param t
	 */
	public static void info(Object object, Throwable t) {
		printLog(object.toString(), INFO, t);
	}

	/**
	 * 警告日志
	 * 
	 * @param object
	 */
	public static void warn(Object object) {
		printLog(object.toString(), WARN);
	}

	/**
	 * 警告日志
	 * 
	 * @param object
	 * @param t
	 */
	public static void warn(Object object, Throwable t) {
		printLog(object.toString(), WARN, t);
	}

	/**
	 * 提醒日志
	 * 
	 * @param object
	 */
	public static void alerm(Object object) {
		printLog(object.toString(), ALARM);
	}

	/**
	 * 提醒日志
	 * 
	 * @param object
	 * @param t
	 */
	public static void alerm(Object object, Throwable t) {
		printLog(object.toString(), ALARM, t);
	}

	/**
	 * isInfoEnabled
	 * 
	 * @param object
	 */
	public static boolean isInfoEnabled() {
		return logger.isInfoEnabled();
	}

	/**
	 * isDebugEnabled
	 * 
	 * @param object
	 */
	public static boolean isDebugEnabled() {
		return logger.isDebugEnabled();
	}

	/**
	 * print StackTrace
	 * 
	 * @return
	 */
	// public static String stackTraceToString(Exception ex) {
	// StringBuffer buffOut = new StringBuffer();
	// StackTraceElement[] trace = ex.getStackTrace();
	//
	// for (int i = 0; i < trace.length; i++)
	// buffOut.append("\tat " + trace[i]+ "\n ");
	// Throwable ourCause = ex.getCause();
	// if (ourCause != null) {
	// buffOut.append("Caused by: " + ourCause);
	// StringWriter sw = new StringWriter();
	// try {
	// ourCause.printStackTrace(new PrintWriter(sw));
	// buffOut.append("\n ");
	// buffOut.append(sw.getBuffer());
	// sw.flush();
	// } finally {
	// try {
	// sw.close();
	// } catch (IOException e) {
	// }
	// }
	// }
	// return buffOut.toString();
	// }
	public static String stackTraceToString(Exception e) {
		StringBuffer buffOut = new StringBuffer();
		buffOut.append("\n").append(e.getMessage()).append("\n");
		StackTraceElement[] trace = e.getStackTrace();
		for (int i = 0; i < trace.length; i++)
			buffOut.append("\tat ").append(trace[i]).append("\n");
		Throwable ourCause = e.getCause();
		if (ourCause != null) {
			logStackTraceAsCause(trace, ourCause, buffOut);
		}
		return buffOut.toString();
	}

	private static void logStackTraceAsCause(StackTraceElement[] causedTrace, Throwable e, StringBuffer buffOut) {
		StackTraceElement[] trace = e.getStackTrace();
		buffOut.append("\nCaused by: ").append(e.getMessage()).append("\n");
		int m = trace.length - 1, n = causedTrace.length - 1;
		while (m >= 0 && n >= 0 && trace[m].equals(causedTrace[n])) {
			m--;
			n--;
		}
		int framesInCommon = trace.length - 1 - m;

		for (int i = 0; i <= m; i++)
			buffOut.append("\tat ").append(trace[i]).append("\n");
		if (framesInCommon != 0)
			buffOut.append("\t... ").append(framesInCommon).append(" more \n");
		// Recurse if we have a cause
		Throwable ourCause = e.getCause();
		if (ourCause != null)
			logStackTraceAsCause(trace, ourCause, buffOut);
	}

}

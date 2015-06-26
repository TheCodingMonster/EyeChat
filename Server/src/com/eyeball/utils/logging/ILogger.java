package com.eyeball.utils.logging;

/**
 * 
 * An interface for loggers. Implement and provide your own means of logging.
 * 
 * @author Eyeball
 *
 */
public interface ILogger {
	
	public void error(Object text);

	public String getName();

	public Thread getThread();

	public void info(Object text);

	public void warn(Object text);

}

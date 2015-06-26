package com.eyeball.utils.logging;

import java.util.ArrayList;

import com.eyeball.utils.misc.Utils;

/**
 * 
 * An implementation of ILogger that takes an infinite number of loggers and
 * calls the specified method on all.
 * 
 * Example:
 * <code>
 * ILogger [] loggers = new ILogger[] {new Logger("A"), new XYLogger("B"), new FileLogger("C://", "C")};
 * 
 * MultiLogger logger = new MultiLogger(loggers);
 * 
 * logger.info("Hello");
 * </code>
 * instead of
 * <code>
 * ILogger [] loggers = new ILogger[] {new Logger("A"), new XYLogger("B"), new FileLogger("C://", "C")};
 * for (ILogger logger : loggers) {
 *     logger.info("Hello");
 * }
 * </code>
 * 
 * @author Eyeball
 *
 */
public class MultiLogger implements ILogger {

	ArrayList<ILogger> loggers = new ArrayList<ILogger>();

	/**
	 * 
	 * @param loggers
	 *            All the loggers.
	 */
	@SuppressWarnings("unchecked")
	public MultiLogger(ILogger... loggers) {
		this.loggers = (ArrayList<ILogger>) Utils.arrayCopy(this.loggers,
				loggers);
	}

	/**
	 * 
	 * Adds a logger the this MultiLogger.
	 * 
	 * @param logger
	 *            The logger to be added.
	 */
	public void addLogger(ILogger logger) {
		loggers.add(logger);
	}

	/**
	 * @param text
	 * 
	 *            Logs the text as an error in all the loggers.
	 * 
	 */

	@Override
	public void error(Object text) {
		for (ILogger l : loggers)
			l.error(text);
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public Thread getThread() {
		return null;
	}

	/**
	 * @param text
	 * 
	 *            Logs the text information in all the loggers.
	 * 
	 */
	@Override
	public void info(Object text) {
		for (ILogger l : loggers)
			l.info(text);
	}

	/**
	 * @param text
	 * 
	 *            Logs the text a warning in all the loggers.
	 * 
	 */
	@Override
	public void warn(Object text) {
		for (ILogger l : loggers)
			l.warn(text);
	}

}

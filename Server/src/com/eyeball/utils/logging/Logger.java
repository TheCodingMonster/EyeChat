package com.eyeball.utils.logging;

import java.io.PrintStream;
import java.util.Date;

/**
 * 
 * Your everyday standard logger.
 * 
 * @author Eyeball
 *
 */
public class Logger implements ILogger {

	private PrintStream out = System.out;
	String name;
	Thread thread = null;

	/**
	 * @param name
	 *            Name of the logger.
	 */
	public Logger(String name) {
		if (name.equals(""))
			this.name = "Blank-Logger";
		else
			this.name = name;
	}

	/**
	 * @param name
	 *            Name of the logger.
	 * @param thread
	 *            A thread for multi-thread programs.
	 * 
	 */
	public Logger(String name, Thread thread) {
		this.thread = thread;
		if (name.equals("") && thread == null)
			this.name = "Blank-Logger";
		else
			this.name = thread.getName() + "/" + name;
	}

	/**
	 * @param text
	 * 
	 *            Logs the text as an error.
	 * 
	 */
	@Override
	public void error(Object text) {
		out.println("[" + getTime() + "] " + "[ERROR] " + " [" + name + "]: "
				+ text);
	}

	/**
	 * Gets the name of this logger.
	 * 
	 * @return The loggers name.
	 * 
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * Gets the thread of this logger.
	 * 
	 * @return The thread if it exists.
	 * 
	 */
	@Override
	public Thread getThread() {
		return null;
	}

	private String getTime() {
		String date = new Date().toString();
		return date.substring(11, 19);
	}

	/**
	 * @param text
	 * 
	 *            Logs the text information.
	 * 
	 */
	@Override
	public void info(Object text) {
		out.println("[" + getTime() + "] " + "[INFO]  " + " [" + name + "]: "
				+ text);
	}

	/**
	 * @param text
	 * 
	 *            Logs the text a warning.
	 * 
	 */
	@Override
	public void warn(Object text) {
		out.println("[" + getTime() + "] " + "[WARN]  " + " [" + name + "]: "
				+ text);
	}

}

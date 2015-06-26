package com.eyeball.utils.logging;

import java.io.File;
import java.io.PrintStream;
import java.util.Date;

import com.eyeball.utils.annotation.Private;

@Private(reason = "No fully implemented as of now")
public class RedirectableLogger extends File implements ILogger {

	private static final long serialVersionUID = 1L;

	private PrintStream out = System.out;

	private String name = "";

	@SuppressWarnings("unused")
	private Thread thread = null;

	public RedirectableLogger(String name) {
		super(System.getProperty("user.home"));
		if (name.equals(""))
			this.name = "Blank-Logger";
		else
			this.name = name;
	}

	public RedirectableLogger(String name, Thread thread) {
		super(System.getProperty("user.home"));
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
	 * @return null as it is unimplemented.
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

package com.eyeball.utils.logging;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

/**
 * 
 * An implementation of ILogger that logs to a file.
 * 
 * @author Eyeball
 *
 */
public class FileLogger implements ILogger {

	Logger LOGGER = new Logger("File-Logger");
	String path;
	String name;
	FileWriter out = null;

	/**
	 * Generates a new FileLogger object from a String
	 * 
	 * @param outputFile
	 *            Where to log to
	 * @param name
	 *            Name of the logger.
	 * @throws IOException If the file could not be made.
	 */

	public FileLogger(File outputFile, String name) throws IOException {
		out = new FileWriter(outputFile, true);
		this.name = name;
		path = outputFile.getCanonicalPath();
		if (!outputFile.exists()) {
			LOGGER.info("File: " + outputFile.getName() + " does not exist!");
			LOGGER.info("Generating it!");
			FileWriter fw = new FileWriter(outputFile);
			fw.close();
		} else
			LOGGER.info("Found log file " + outputFile.getName() + "!");

	}

	/**
	 * Generates a new FileLogger object from a String
	 * 
	 * @param outputFile
	 *            Where to log to
	 * @param name
	 *            Name of the logger.
	 * @throws IOException If the file could not be made.
	 */

	public FileLogger(String outputFile, String name) throws IOException {
		out = new FileWriter(new File(outputFile), true);

		this.name = name;
		path = outputFile;
		File file = new File(outputFile);
		if (!file.exists()) {
			LOGGER.info("File: " + file.getName() + " does not exist!");
			LOGGER.info("Generating it!");
			FileWriter fw = new FileWriter(outputFile);
			fw.close();
		} else
			LOGGER.info("Found log file " + file.getName() + "!");

	}

	/**
	 * @param text
	 * 
	 *            Logs the text as an error in the given file
	 * 
	 */
	@Override
	public void error(Object text) {
		try {
			FileWriter fileWriter = new FileWriter(new File(path), true);

			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write("[" + getTime() + "] " + "[ERROR]  " + " ["
					+ name + "]: " + text + "\n");
			bufferedWriter.close();

		} catch (Exception e) {
			LOGGER.error("Could not log to " + path);
		}
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
	 *            Logs the text as information in the given file
	 * 
	 */
	@Override
	public void info(Object text) {
		try {
			FileWriter fileWriter = new FileWriter(new File(path), true);

			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write("[" + getTime() + "] " + "[INFO]  " + " ["
					+ name + "]: " + text + "\n");
			bufferedWriter.close();

		} catch (Exception e) {
			LOGGER.error("Could not log to " + path);
		}
	}

	/**
	 * @param text
	 * 
	 *            Logs the text as a warning in the given file
	 * 
	 */
	@Override
	public void warn(Object text) {
		try {
			FileWriter fileWriter = new FileWriter(new File(path), true);

			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write("[" + getTime() + "] " + "[WARN]  " + " ["
					+ name + "]: " + text + "\n");
			bufferedWriter.close();

		} catch (Exception e) {
			LOGGER.error("Could not log to " + path);
		}
	}

}

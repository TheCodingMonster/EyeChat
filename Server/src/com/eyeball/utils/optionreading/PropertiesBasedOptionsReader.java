package com.eyeball.utils.optionreading;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import com.eyeball.utils.logging.Logger;

/**
 * 
 * An implementation of IOptionsReader to read regular files using
 * java.util.Properties.
 * 
 * @author Eyeball
 *
 */
public class PropertiesBasedOptionsReader implements IOptionsReader {

	static Logger LOGGER = new Logger("Options-Reader");

	private String path;

	private Properties properties;

	/**
	 * 
	 * Construct a new PropertiesBasedOptionsReader.
	 * 
	 * @param path Where to find the file.
	 * @throws IOException If the file could not be made.
	 */
	public PropertiesBasedOptionsReader(File path) throws IOException {
		this.path = path.getCanonicalPath();
		if (!path.exists()) {
			LOGGER.info("File: " + path.getName() + " does not exist!");
			LOGGER.info("Generating it!");
			FileWriter fw = new FileWriter(path);
			fw.close();
		} else
			LOGGER.info("Found options file " + path.getName() + "!");
		properties = new Properties();
		FileReader fReader = new FileReader(path);
		properties.load(fReader);
	}


	/**
	 * 
	 * Construct a new PropertiesBasedOptionsReader.
	 * 
	 * @param path Where to find the file.
	 * @throws IOException If the file could not be made.
	 */
	public PropertiesBasedOptionsReader(String path) throws IOException {
		this.path = path;
		File file = new File(path);
		if (!file.exists()) {
			LOGGER.info("File: " + file.getName() + " does not exist!");
			LOGGER.info("Generating it!");
			FileWriter fw = new FileWriter(path);
			fw.close();
		} else
			LOGGER.info("Found options file " + file.getName() + "!");
		properties = new Properties();
		FileReader fReader = new FileReader(path);
		properties.load(fReader);

	}

	/**
	 * 
	 * Unimplemented
	 * 
	 * @param text What to comment
	 */
	@Override
	public void comment(String text) {
	}

	public void name(String comment) throws IOException {
		properties.store(new FileWriter(new File(path)), comment);

	}

	/**
	 * Replaces the original varName property with the one in text.
	 * 
	 * @param text New value
	 * @param varName property name.
	 * @throws IOException
	 */
	public void put(Object text, String varName) throws IOException {
		properties.setProperty(varName, text.toString());
		properties.put(varName, text.toString());
	}

	@Override
	public boolean readBoolean(String varName, boolean defaultANS) {
		try {
			return Boolean.parseBoolean(properties.getProperty(varName, ""
					+ defaultANS));
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public float readFloat(String varName, float defaultANS) {
		try {
			return Float.parseFloat(properties.getProperty(varName, ""
					+ defaultANS));
		} catch (Exception e) {
			return Float.MAX_VALUE;
		}
	}

	@Override
	public int readInt(String varName, int defaultANS) {

		try {
			return Integer.parseInt(properties.getProperty(varName, ""
					+ defaultANS));
		} catch (Exception e) {
			return Integer.MAX_VALUE;
		}

	}

	@Override
	public long readLong(String varName, long defaultANS) {
		try {
			return Long.parseLong(properties.getProperty(varName, ""
					+ defaultANS));
		} catch (Exception e) {
			return Long.MAX_VALUE;
		}
	}

	@Override
	public String readString(String varName, String defaultANS) {

		try {
			return properties.getProperty(varName, "" + defaultANS);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public String[] readStringArray(String varName, String[] defaultANS) {
		return null;
	}

}

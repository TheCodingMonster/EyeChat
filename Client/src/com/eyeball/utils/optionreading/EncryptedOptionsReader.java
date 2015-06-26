package com.eyeball.utils.optionreading;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.ArrayList;

import com.eyeball.utils.annotation.Private;
import com.eyeball.utils.logging.Logger;

/**
 * 
 * An implementation of IOptionsReader to read encrypted files.
 * 
 * @author Eyeball
 *
 */
public class EncryptedOptionsReader implements IOptionsReader {

	static Logger LOGGER = new Logger("Options-Reader");

	private String path;

	/**
	 * Generates a new OptionsReader object from a File
	 * 
	 * @param fileToRead Where to find the file.
	 * @throws IOException If the file could not be made.
	 */
	public EncryptedOptionsReader(File fileToRead, String padding, String alg, String provider) throws IOException {
		path = fileToRead.getCanonicalPath();
		if (!fileToRead.exists()) {
			LOGGER.info("File: " + fileToRead.getName() + " does not exist!");
			LOGGER.info("Generating it!");
			FileWriter fw = new FileWriter(fileToRead);
			fw.close();
		} else
			LOGGER.info("Found encrypted options file " + fileToRead.getName() + "!");
	}

	/**
	 * Generates a new OptionsReader object from a String
	 * 
	 * @param fileToRead Where to find the file.
	 * @throws IOException If the file could not be made.
	 */

	public EncryptedOptionsReader(String fileToRead) throws IOException {
		path = fileToRead;
		File file = new File(fileToRead);
		if (!file.exists()) {
			LOGGER.info("File: " + file.getName() + " does not exist!");
			LOGGER.info("Generating it!");
			FileWriter fw = new FileWriter(fileToRead);
			fw.close();
		} else
			LOGGER.info("Found encrypted options file " + file.getName() + "!");

	}

	/**
	 * Puts a coment into the file
	 * 
	 * @param text The comment
	 */

	@Override
	public void comment(String text) {
		String write = "# " + text;
		LOGGER.info("I need " + write);
		String[] stuff = read();
		for (String c : stuff) {
			if (c.contains("#"))
				LOGGER.info(c);
			if (write.equals(c))
				break;
			else {
				write(write);
				break;
			}
		}
	}

	@Private(reason = "My Stuff!")
	public String[] read() {
		try {
			FileInputStream is;
			is = new FileInputStream(path);
			StringBuilder sb = new StringBuilder(2048);
			Reader r = new InputStreamReader(is, "UTF-8");
			int c = 0;
			while ((c = r.read()) != -1)
				sb.append((char) c);
			r.close();
			String text = sb.toString();
			return text.split("\\r?\\n");
		} catch (Exception e1) {
			LOGGER.error("Exception caught while reading options file!");
		}
		return new String[] {};
	}

	/**
	 * 
	 * @param varName
	 *            The name of the option
	 * @return The boolean read
	 */
	@Override
	public boolean readBoolean(String varName, boolean defaultANS) {
		try {
			String[] options = read();
			for (String c : options)
				if (c.trim().startsWith(varName))
					return Boolean.parseBoolean(c.trim().substring(
							varName.length() + 1));
			write(varName + "=" + defaultANS);
		} catch (Exception e) {
			return false;
		}
		return defaultANS;
	}

	/**
	 * 
	 * @param varName
	 *            The name of the option
	 * @return The float read
	 */
	@Override
	public float readFloat(String varName, float defaultANS) {
		try {
			String[] options = read();
			for (String c : options)
				if (c.trim().startsWith(varName))
					return Float.parseFloat(c.trim().substring(
							varName.length() + 1));
			write(varName + "=" + defaultANS);
		} catch (Exception e) {
			return 0.0F;
		}
		return defaultANS;
	}

	/**
	 * 
	 * @param varName
	 *            The name of the option
	 * @return The int read
	 */
	@Override
	public int readInt(String varName, int defaultANS) {
		try {
			String[] options = read();
			for (String c : options)
				if (c.trim().startsWith(varName))
					return Integer.parseInt(c.trim().substring(
							varName.length() + 1));
			write(varName + "=" + defaultANS);
		} catch (Exception e) {
			return 0;
		}
		return defaultANS;
	}

	/**
	 * 
	 * @param varName
	 *            The name of the option
	 * @return The long read
	 */
	@Override
	public long readLong(String varName, long defaultANS) {
		try {
			String[] options = read();
			for (String c : options)
				if (c.trim().startsWith(varName))
					return Long.parseLong(c.trim().substring(
							varName.length() + 1));
			write(varName + "=" + defaultANS);
		} catch (Exception e) {
			return 0;
		}
		return defaultANS;
	}

	/**
	 * 
	 * @param varName
	 *            The name of the option
	 * @return The String read
	 */
	@Override
	public String readString(String varName, String defaultANS) {
		try {
			String[] options = read();
			for (String c : options)
				if (c.trim().startsWith(varName))
					return c.trim().substring(varName.length() + 1);
			write(varName + "=" + defaultANS);
		} catch (Exception e) {
			return "";
		}
		return defaultANS;
	}

	/**
	 * 
	 * @param varName
	 *            The name of the option
	 * @return The String array read
	 */
	@Override
	public String[] readStringArray(String varName, String[] defaultANS) {
		try {
			boolean isIn = false;
			ArrayList<String> array = new ArrayList<String>();
			String[] options = read();
			for (String current : options) {
				if (isIn)
					array.add(current);
				if (current.trim().startsWith(varName)
						&& current.trim().equals(varName + "=\""))
					isIn = true;
				if (current.trim().equals("\"")) {
					isIn = false;
					return (String[]) array.toArray();
				}
				throw new EOFException(
						"Expected \" for end for String Array, unexpected end of File");
			}
			ArrayList<String> list = new ArrayList<String>();
			for (String current : defaultANS)
				list.add(current);
			writeArray(list, varName);
		} catch (Exception e) {
			return new String[] {};
		}
		return defaultANS;
	}

	@Private(reason = "My Stuff!")
	public void write(String text) {
		try {
			PrintWriter out = new PrintWriter(new FileWriter(new File(path),
					true));
			out.println(text);
			out.close();
		} catch (IOException e) {
			LOGGER.error("Exception caught while writing to options file!");
		}
	}

	/**
	 * 
	 * @param arrayToWrite The Array To write.
	 * @param name
	 *            Writes the given array to file;
	 */
	@Private(reason = "My Stuff!")
	public void writeArray(ArrayList<?> arrayToWrite, String name) {
		write(name + "=\"");
		Object[] stuff = arrayToWrite.toArray();
		for (Object current : stuff)
			write("" + current);
		write("\"");
	}

}

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

/**
 * 
 * An implementation of IOptionsReader to read regular files.
 * 
 * @author Eyeball
 * 
 */
public class OptionsReader implements IOptionsReader {

	private String path;

	/**
	 * Generates a new OptionsReader object from a File
	 * 
	 * @param fileToRead
	 *            Where to find the file
	 * @throws IOException
	 *             If the file could not be made.
	 */
	public OptionsReader(File fileToRead) throws IOException {
		path = fileToRead.getCanonicalPath();
		if (!fileToRead.exists()) {
			FileWriter fw = new FileWriter(fileToRead);
			fw.close();
		}
	}

	/**
	 * Generates a new OptionsReader object from a String
	 * 
	 * @param fileToRead
	 *            Where to find the file
	 * @throws IOException
	 *             If the file could not be made.
	 */

	public OptionsReader(String fileToRead) throws IOException {
		path = fileToRead;
		File file = new File(fileToRead);
		if (!file.exists()) {
			FileWriter fw = new FileWriter(fileToRead);
			fw.close();
		}
	}

	/**
	 * Puts a coment into the file
	 * 
	 * @param text
	 * @throws IOException
	 */

	@Override
	public void comment(String text) throws IOException {
		String write = "# " + text;
		String[] stuff = read();
		for (String c : stuff) {
			if (c.contains("#"))
				if (write.equals(c))
					break;
				else {
					write(write);
					break;
				}
		}
	}

	@Private(reason = "My Stuff!")
	public String[] read() throws IOException {
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
	}

	/**
	 * 
	 * @param varName
	 *            The name of the option
	 * @return The boolean read
	 * @throws IOException
	 */
	@Override
	public boolean readBoolean(String varName, boolean defaultANS)
			throws IOException {
		String[] options = read();
		for (String c : options)
			if (c.trim().startsWith(varName))
				return Boolean.parseBoolean(c.trim().substring(
						varName.length() + 1));
		write(varName + "=" + defaultANS);
		return defaultANS;
	}

	/**
	 * 
	 * @param varName
	 *            The name of the option
	 * @return The float read
	 * @throws IOException
	 */
	@Override
	public float readFloat(String varName, float defaultANS) throws IOException {
		String[] options = read();
		for (String c : options)
			if (c.trim().startsWith(varName))
				return Float.parseFloat(c.trim()
						.substring(varName.length() + 1));
		write(varName + "=" + defaultANS);
		return defaultANS;
	}

	/**
	 * 
	 * @param varName
	 *            The name of the option
	 * @return The int read
	 * @throws IOException
	 */
	@Override
	public int readInt(String varName, int defaultANS) throws IOException {
		String[] options = read();
		for (String c : options)
			if (c.trim().startsWith(varName))
				return Integer.parseInt(c.trim()
						.substring(varName.length() + 1));
		write(varName + "=" + defaultANS);
		return defaultANS;
	}

	/**
	 * 
	 * @param varName
	 *            The name of the option
	 * @return The long read
	 * @throws IOException
	 */
	@Override
	public long readLong(String varName, long defaultANS) throws IOException {
		String[] options = read();
		for (String c : options)
			if (c.trim().startsWith(varName))
				return Long.parseLong(c.trim().substring(varName.length() + 1));
		write(varName + "=" + defaultANS);
		return defaultANS;
	}

	/**
	 * 
	 * @param varName
	 *            The name of the option
	 * @return The String read
	 * @throws IOException
	 */
	@Override
	public String readString(String varName, String defaultANS)
			throws IOException {
		String[] options = read();
		for (String c : options)
			if (c.trim().startsWith(varName))
				return c.trim().substring(varName.length() + 1);
		write(varName + "=" + defaultANS);
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

	/**
	 * 
	 * Writes some text.
	 * 
	 * @param text
	 *            The text to be written.
	 * @throws IOException
	 */
	@Private(reason = "My Stuff!")
	public void write(String text) throws IOException {
		PrintWriter out = new PrintWriter(new FileWriter(new File(path), true));
		out.println(text);
		out.close();
	}

	/**
	 * 
	 * @param arrayToWrite
	 *            The array to write
	 * @param name
	 *            Writes the given array to file;
	 * @throws IOException
	 */
	@Private(reason = "My Stuff!")
	public void writeArray(ArrayList<?> arrayToWrite, String name)
			throws IOException {
		write(name + "=\"");
		Object[] stuff = arrayToWrite.toArray();
		for (Object current : stuff)
			write("" + current);
		write("\"");
	}

}

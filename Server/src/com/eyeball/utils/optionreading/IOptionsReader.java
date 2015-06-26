package com.eyeball.utils.optionreading;

import java.io.IOException;

/**
 * 
 * An interface for options reading. Implement this and provide your own means
 * of reading and parsing.
 * 
 * @author Eyeball
 *
 */
public interface IOptionsReader {
	/**
	 * Puts a coment into the file
	 * 
	 * @param text
	 *            The comment
	 * @throws IOException
	 */

	public void comment(String text) throws IOException;

	/**
	 * 
	 * @param varName
	 *            The name of the option
	 * @return The boolean read
	 * @throws IOException
	 */
	public boolean readBoolean(String varName, boolean defaultANS)
			throws IOException;

	/**
	 * 
	 * @param varName
	 *            The name of the option
	 * @return The float read
	 * @throws IOException
	 */
	public float readFloat(String varName, float defaultANS) throws IOException;

	/**
	 * 
	 * @param varName
	 *            The name of the option
	 * @return The int read
	 * @throws IOException
	 */
	public int readInt(String varName, int defaultANS) throws IOException;

	/**
	 * 
	 * @param varName
	 *            The name of the option
	 * @return The long read
	 * @throws IOException
	 */
	public long readLong(String varName, long defaultANS) throws IOException;

	/**
	 * 
	 * @param varName
	 *            The name of the option
	 * @return The String read
	 * @throws IOException
	 */
	public String readString(String varName, String defaultANS)
			throws IOException;

	/**
	 * 
	 * @param varName
	 *            The name of the option
	 * @return The String array read
	 */
	public String[] readStringArray(String varName, String[] defaultANS);

}

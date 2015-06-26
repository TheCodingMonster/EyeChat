package com.eyeball.utils.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.InflaterInputStream;

public class BufferedInflaterInputStream extends InflaterInputStream {

	/**
	 * 
	 * A buffered version of InflaterInputStream. <br>
	 * <br>
	 * <br>
	 * 
	 * @param in
	 *            The InputStream. <br>
	 * <br>
	 *           {@link java.util.zip.DeflaterInputStream}
	 */
	public BufferedInflaterInputStream(InputStream in) {
		super(in);
	}

	public String readLine() throws IOException {
		StringBuffer sb = new StringBuffer();
		int s = this.read();
		while (s != -1 ) {
			Character c = new Character((char) s);
			if (c.charValue() == '\n' | c.charValue() == '\r') {
				return sb.toString();
			}
			sb.append(c);
		}
		return sb.toString();
	}

}

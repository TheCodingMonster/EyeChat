package com.eyeball.utils.misc;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import com.eyeball.utils.annotation.Private;

/**
 * 
 * Simple translator
 * 
 * @author eyeballcode
 *
 */
public class Translator {

	File lang;

	public Translator(File langFile) {
		lang = langFile;
	}

	@Private(reason = "My Stuff!")
	public String[] read() throws IOException {
		FileInputStream is;
		is = new FileInputStream(lang);
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
	 * Translate a unlocalized string to a localized string
	 * 
	 * @param unlocalized
	 * @return The localized string, or unlocalized if the localized string
	 *         could not be found.
	 * @throws IOException
	 */
	public String translate(String unlocalized) throws IOException {
		String[] options = read();
		for (String c : options) {
			if (c.trim().startsWith("#"))
				continue;
			if (c.trim().startsWith(unlocalized))
				return c.trim().substring(unlocalized.length() + 1);
		}
		return unlocalized;
	}

}

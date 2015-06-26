package com.eyeball.utils.io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 
 * ArrayList containing all text in a file.
 * 
 * @author eyeballcode
 *
 */
public class FileTextList extends ArrayList<String> {

	File f;

	private static final long serialVersionUID = -5862823628442401048L;

	public FileTextList(File toRead) throws IOException {
		f = toRead;
		for (String string : FileReader.readText(toRead)) {
			add(string);
		}
	}

	public void reload() throws IOException {
		this.clear();
		for (String string : FileReader.readText(f)) {
			add(string);
		}
	}
}

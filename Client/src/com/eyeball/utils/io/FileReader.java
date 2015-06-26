package com.eyeball.utils.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 
 * Simple FileReading utils.
 * 
 * @author eyeballcode
 *
 */
public class FileReader {

	/**
	 * 
	 * Read all the text in a file.
	 * 
	 */
	public static ArrayList<String> readText(File f) throws IOException {
		BufferedReader reader = new BufferedReader(new java.io.FileReader(f));
		ArrayList<String> array = new ArrayList<>();
		for (String text = reader.readLine(); text != null; text = reader
				.readLine()) {
			array.add(text);
		}
		reader.close();
		return array;
	}

}

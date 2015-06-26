package com.eyeball.utils.fileutils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FileUtils {

	/**
	 * 
	 * Cleans a directory.
	 * 
	 * @param path
	 *            The directory to clean.
	 * @return If it was successfully cleaned.
	 * @throws IOException
	 *             If it could not be cleaned.
	 */
	public static boolean cleanDirectory(File path) throws IOException {
		File[] filesList = path.listFiles();

		ArrayList<File> directories = new ArrayList<File>();

		// Remove files first.
		for (File f : filesList)
			if (f.isDirectory())
				directories.add(f);
			else
				f.delete();

		// Keep calling cleanDirectory() until all folders are cleared.
		for (File f : directories) {
			File[] in = f.listFiles();
			for (File c : in) {
				if (c.isDirectory()) {
					FileUtils.cleanDirectory(c);
				} else {
					c.delete();
					c = null;
				}
				for (File z : in) {
					z.delete();
					z = null;
				}
			}
			in = null;
			f = null;
		}
		// Remove any directories left.
		for (File f : filesList)
			f.delete();

		// Set everything to null for GC.
		filesList = null;
		directories = null;

		return true;
	}

	/**
	 * 
	 * Checks if a directory is empty.
	 * 
	 * @param dir
	 *            The directory to be checked.
	 * @return If it is empty.
	 */
	public static boolean dirEmpty(File dir) {
		return dir.list().length == 0;
	}

	/**
	 * 
	 * Check if a file exists.
	 * 
	 * @param path
	 *            The file or folder to check.
	 * @return If it exists.
	 */
	public static boolean fileExists(File path) {
		return path.exists();
	}

	/**
	 * 
	 * Check if a file exists.
	 * 
	 * @param path
	 *            The file or folder to check.
	 * @return If it exists.
	 */
	public static boolean fileExists(String path) {
		return new File(path).exists();
	}

	/**
	 * 
	 * Force deletes a directory.
	 * 
	 * No commands or anything used. 100% pure coding stuff.
	 * 
	 * @param dir
	 *            The directory to delete.
	 * @return If it was successfully deleted,
	 * @throws IOException
	 *             If it could not be cleaned or deleted.
	 */
	public static boolean forceDeleteDir(File dir) throws IOException {
		FileUtils.cleanDirectory(dir);
		return dir.delete();

	}

	/**
	 * 
	 * Makes a directory.
	 * 
	 * @param path
	 *            The folder to make.
	 * @return If it was made successfully.
	 */
	public static boolean makeDir(File path) {
		return path.mkdirs();
	}

	/**
	 * 
	 * Makes a directory.
	 * 
	 * @param path
	 *            The folder to make.
	 * @return If it was made successfully.
	 */
	public static boolean makeDir(String path) {
		return new File(path).mkdirs();
	}

	/**
	 * 
	 * Makes a file.
	 * 
	 * @param path
	 *            The file or folder to make.
	 * @return If it was made successfully.
	 */
	public static boolean makeFile(File path) {
		try {
			FileWriter fw = new FileWriter(path, true);
			fw.close();
		} catch (IOException e) {
			return false;
		}
		return true;
	}

}

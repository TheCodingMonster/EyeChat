package com.eyeball.utils.misc;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class AWTUtils {

	/**
	 * Open a file with the default program.
	 * 
	 * @param toOpen
	 *            The file to open.
	 * @throws IOException
	 *             If the file is null, cannot be found or the system does not
	 *             support this.
	 */
	public static void openFile(File toOpen) throws IOException {
		Desktop d = Desktop.getDesktop();
		d.open(toOpen);
	}

	/**
	 * 
	 * Opens a URL in the web browser.
	 * 
	 * @param uri
	 *            The URI..
	 * @throws IOException
	 *             If the system does not support this.
	 */
	public static void openURI(URI uri) throws IOException {
		Desktop d = Desktop.getDesktop();
		d.browse(uri);
	}

	/**
	 * 
	 * Opens a URL in the web browser.
	 * 
	 * @param uri
	 *            The URL.
	 * @throws IOException
	 *             If the system does not support this.
	 */
	public static void openURI(URL url) throws IOException, URISyntaxException {
		URI uri = url.toURI();
		Desktop d = Desktop.getDesktop();
		d.browse(uri);
	}

}

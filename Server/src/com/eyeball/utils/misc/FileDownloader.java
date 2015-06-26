package com.eyeball.utils.misc;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

/**
 * 
 * A class for downloading files.
 * 
 * @author Eyeball
 *
 */
public class FileDownloader {

	String dest = null;

	URL url = null;

	/**
	 * 
	 * Downloads a file from the given String.
	 * 
	 * @param url
	 *            Where to download from.
	 * @param dest
	 *            Where the file will be downloaded to.
	 * @throws IOException
	 */
	public FileDownloader(String url, String dest) throws IOException {
		this.url = new URL(url);

		ReadableByteChannel rbc = Channels.newChannel(this.url.openStream());
		FileOutputStream fos = new FileOutputStream(dest);
		fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		fos.close();

		this.dest = dest;

	}

	/**
	 * 
	 * Downloads a file from the given URL.
	 * 
	 * @param url
	 *            Where to download from.
	 * @param dest
	 *            Where the file will be downloaded to.
	 * @throws IOException
	 */
	public FileDownloader(URL url, String dest) throws IOException {

		this.url = url;

		ReadableByteChannel rbc = Channels.newChannel(url.openStream());
		FileOutputStream fos = new FileOutputStream(dest);
		fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		fos.close();

		this.url = url;
		this.dest = dest;

	}

	/**
	 * 
	 * Gets the destination of the downloader.
	 * 
	 * @return The destination or null if the source destination is invalid.
	 */
	public String getDest() {
		return dest;
	}

	/**
	 * 
	 * Gets the source of the download
	 * 
	 * @return The source or null if the source provided is invalid.
	 */
	public URL getURL() {
		return url;
	}

}

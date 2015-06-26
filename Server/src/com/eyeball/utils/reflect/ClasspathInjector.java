package com.eyeball.utils.reflect;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ClasspathInjector {

	private final Vector<String> loadedThings = new Vector<String>();

	public ClasspathInjector() {
	}

	public Vector<String> getLoadedThings() {
		return loadedThings;
	}

	public void loadFileIntoClasspath(File fileLocation) throws IOException {
		URL url = fileLocation.toURI().toURL();
		try {
			final URLClassLoader classLoader = (URLClassLoader) ClassLoader
					.getSystemClassLoader();
			final Class<URLClassLoader> clazz = URLClassLoader.class;

			final Method method = clazz.getDeclaredMethod("addURL",
					new Class[] { URL.class });
			method.setAccessible(true);
			method.invoke(classLoader, new Object[] { url });
		} catch (ReflectiveOperationException e) {
		} catch (ClassCastException e) {
			throw new UnsupportedJREException("Your JRE's default classloader is not an instance of URLClassLoader.", e);
		}
		
		final File jar = new File(url.getPath());
		try {
			final ZipInputStream zip = new ZipInputStream(new FileInputStream(
					jar));
			for (ZipEntry entry = zip.getNextEntry(); entry != null; entry = zip
					.getNextEntry())
				if (!entry.isDirectory()) {
					final StringBuilder name = new StringBuilder();
					for (final String part : entry.getName().split("/")) {
						if (name.length() != 0)
							name.append(".");
						name.append(part);
						if (part.endsWith(".class"))
							name.setLength(name.length() - ".class".length());
					}
					loadedThings.add(name.toString());
				}
			zip.close();
		} catch (final Exception e) {
			e.printStackTrace();
		}

		String old = System.getProperty("java.class.path");

		StringBuilder sb = new StringBuilder(old);

		sb.append(url.getPath());

		System.setProperty("java.class.path", sb.toString());

	}
}

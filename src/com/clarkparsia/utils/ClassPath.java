// Copyright (c) 2005 - 2009, Clark & Parsia, LLC. <http://www.clarkparsia.com>

package com.clarkparsia.utils;

import com.clarkparsia.utils.collections.CollectionUtil;

import java.io.File;
import java.io.IOException;
import java.io.FileFilter;

import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Collection;
import java.util.Enumeration;

import java.util.jar.JarFile;
import java.util.jar.JarEntry;

import java.lang.reflect.Modifier;

import java.net.URLClassLoader;
import java.net.URL;
import java.net.URLConnection;
import java.net.JarURLConnection;
import java.net.MalformedURLException;

/**
 * Title: ClassPath<br/>
 * Description: Utility class for managing the system class path.  It will return all classes on the class path, and
 * can override the system class loader in order to provide the ability to add classes to the path at runtime and then
 * be able to find them (via Class.forName) and load/instantiate them in a running application.<br/>
 * Company: Clark & Parsia, LLC. <http://www.clarkparsia.com> <br/>
 * Created: Jun 22, 2009 8:26:25 AM <br/>
 *
 * @author Michael Grove <mike@clarkparsia.com>
 */
public class ClassPath {
	public static boolean QUIET = true;

	private static Collection<Class> mClasses;

	private static MutableURLClassLoader mLoader;

	static {
		mLoader = new MutableURLClassLoader(new URL[]{}, Thread.currentThread().getContextClassLoader());

		Thread.currentThread().setContextClassLoader(mLoader);
	}

	/**
	 * Return all the classes which implement/extend the given class and are instantiable (ie not abstract,
	 * not interfaces themselves)
	 * @param theClass the parent class/interface
	 * @return all matching classes
	 */
	public static Collection<Class> instantiableClasses(Class<?> theClass) {
		return CollectionUtil.filter(classes(theClass), new CollectionUtil.CollectionFilter<Class>() {
			public boolean accept(final Class theClass) {
				return !theClass.isInterface() && !Modifier.isAbstract(theClass.getModifiers());
			}
		});
	}

	/**
	 * Get all the classes which implement/extend the given class
	 * @param theInterface the parent class/interface
	 * @return all matching classes
	 */
	public static Collection<Class> classes(final Class<?> theInterface) {
		return CollectionUtil.filter(classes(), new CollectionUtil.CollectionFilter<Class>() {
			public boolean accept(final Class theObject) {
				return theInterface.isAssignableFrom(theObject);
			}
		});
	}

	/**
	 * Add a URL to a jar which contains classes that should be loaded into the application
	 * @param theURL the URL to a jar file to load
	 */
	public static void add(URL... theURL) {
		mLoader.addURL(theURL);

		// invalidate our cache
		mClasses = null;
	}

	/**
	 * Add Files on the local disk that the utility should look into for jars & classes in addition to just what is
	 * specified on the system class path.
	 * @param theFiles the files to add
	 */
	public static void add(File... theFiles) {
		for (File aFile : theFiles) {
			if (!aFile.exists() || !aFile.canRead()) {
				continue;
			}
			
			List<File> aJarList = FileUtil.listFiles(aFile, new JarFileFilter());
			for (File aJarFile : aJarList) {
				try {
					mLoader.addURL(aJarFile.toURL());
				}
				catch (MalformedURLException e) {
					// TODO: log me
				}
			}
		}

		// invalidate our cache
		mClasses = null;
	}

	/**
	 * Return the list of classes on the class path and otherwise associated with the class loader
	 * @return the classes
	 */
	public static Collection<Class> classes() {
		if (mClasses == null) {
			Collection<Class> aClassList = new HashSet<Class>();

			String aSystemPath = System.getProperty("java.class.path");

			if (aSystemPath != null) {
				List<File> aFileList = new ArrayList<File>();

				for (String aPath : aSystemPath.split(File.pathSeparator)) {
					aFileList.add(new File(aPath));
				}

				for (File aFile : aFileList) {
					if (!aFile.exists()) {
						// maybe a bum entry on the class path, so just skip it
						continue;
					}
					else if (aFile.isDirectory()) {
						// either a directory of jar files, or a dir of class files
						aClassList.addAll(listClassesFromDir(aFile));
					}
					else {
						// probably a jar file...
						aClassList.addAll(listClassesFromJar(aFile));
					}
				}
			}

			for (URL aURL : mLoader.getURLs()) {
				if (aURL.toString().endsWith(".jar")) {
					if (!aURL.toString().startsWith("jar:")) {
						try {
							aURL = new URL("jar:" + aURL.toString()+"!/");
						}
						catch (MalformedURLException e) {
							// no-op, this should happen, but we should TODO: log this
							e.printStackTrace();
						}
					}

					aClassList.addAll(listClassesFromJar(aURL));
				}
				else if (aURL.toString().endsWith(".class")) {
					// fuck, skip this.  i cant' imagine anyone pointing at a single class file.
				}
				else {
					// don't know what to do in this case, lets just skip it
				}
			}

			mClasses = aClassList;
		}

		return mClasses;
	}

	/**
	 * Return the list of classes in the given directory.  This will find any "loose" classes as well as classes packaged
	 * inside of jar files.  It will also recurse through subdirectories to find classes.
	 * @param theFile the directory to load from
	 * @return the list of classes in the directory
	 */
	public static Collection<? extends Class> listClassesFromDir(final File theFile) {
		// a dir with either jars or classes, or both

		Collection<Class> aClassList = new HashSet<Class>();

		// recurse the directory and file all the jar files and load the classes in them
		List<File> aJarList = FileUtil.listFiles(theFile, new JarFileFilter());
		for (File aJarFile : aJarList) {
			aClassList.addAll(listClassesFromJar(aJarFile));
		}

		// now find all the class files and load the classes specified by them
		List<File> aClassFileList = FileUtil.listFiles(theFile, new ClassFileFilter());
		for (File aClassFile : aClassFileList) {
			Class aClass = _class(classNameFromFileName(aClassFile.getAbsolutePath().substring(theFile.getAbsolutePath().length() + 1)));
			if (aClass != null) {
				aClassList.add(aClass);
			}
		}

		return aClassList;
	}

	/**
	 * Return the class with the given class name, filtering for system classes.
	 * @param theClassName the class name
	 * @return the class, or null if it cannot be loaded
	 */
	private static Class _class(String theClassName) {
		if (theClassName == null) {
			return null;
		}

		// skip system classes and known libraries, we don't really care if we include them,
		// this is mostly for user defined stuff.  this is hackish, but it should cut down on the overhead of
		// reading everything in, both in performance and in memory requirements.
		if (theClassName.startsWith("java.") || theClassName.startsWith("javax.") ||
			theClassName.startsWith("sun.") || theClassName.startsWith("com.sun.") ||
			theClassName.startsWith("apple.") || theClassName.startsWith("com.apple") ||
			theClassName.startsWith("org.jdesktop.")) {
			return null;
		}

		try {
			return get(theClassName);
		}
		catch (Throwable e) {
			if (!QUIET) {
				e.printStackTrace();
			}
		}

		return null;
	}

	/**
	 * Return the list of classes from a JarFile object
	 * @param theFile the JarFile to get classes from
	 * @return the list of classes in the jar file
	 */
	private static Collection<? extends Class> listClassesFromJarFile(JarFile theFile) {
		Collection<Class> aClassList = new HashSet<Class>();

		Enumeration<JarEntry> aEntries = theFile.entries();
		while (aEntries.hasMoreElements()) {
			JarEntry aEntry = aEntries.nextElement();
			if (aEntry.getName().endsWith(".class")) {
				// if it looks like a class file, and talks like a class file, then it must be...
				Class aClass = _class(classNameFromFileName(aEntry.getName()));

				if (aClass != null) {
					aClassList.add(aClass);
				}
			}
		}

		return aClassList;
	}

	/**
	 * Returns the list of classes at the specified jar URL
	 * @param theURL the jar URL
	 * @return returns the list of classes at the jar location, or an empty collection if there are no classes, it's not
	 * a Jar URL or the URL cannot be opened.
	 */
	public static Collection<? extends Class> listClassesFromJar(URL theURL) {
		try {
			URLConnection aConn = theURL.openConnection();

			if (!(aConn instanceof JarURLConnection)) {
				// TODo: log warning
				return new HashSet<Class>();
			}

			JarURLConnection aJarConn = (JarURLConnection) aConn;

			return listClassesFromJarFile(aJarConn.getJarFile());
		}
		catch (IOException e) {
			// TODO: log warning
			return new HashSet<Class>();
		}
	}

	/**
	 * Get a class by its class name.  This will use a custom class loader.
	 * @param theName the fully qualified name of the class to load
	 * @return the class, or null if it is not found or otherwise could not be loaded.
	 */
	public static Class get(String theName) {
		try {
			return Class.forName(theName, true, mLoader);
		}
		catch (ClassNotFoundException e) {
			return null;
		}
	}

	/**
	 * Returns the list of classes in the Jar file at the specified file location
	 * @param theFile the file location of the jar
	 * @return the list of classes in the jar file
	 */
	public static Collection<? extends Class> listClassesFromJar(File theFile) {
		try {
			if (theFile.canRead()) {
				return listClassesFromJarFile(new JarFile(theFile));
			}
		}
		catch (IOException e) {
			if (!QUIET) {
				e.printStackTrace();
			}
		}

		return new HashSet<Class>();
	}

	/**
	 * Given a file name for a .class file, return the class name of the class represented by the file name
	 * @param theName the path of the .class file
	 * @return the fully qualified class name of the class
	 */
	private static String classNameFromFileName(final String theName) {
		// chop off the extension (.class) and replace the dir separators with .
		return theName.substring(0, theName.length() - ".class".length()).replaceAll("/|\\\\", "\\.");
	}

	/**
	 * FileFilter implementation for filtering a list of files so only class files are included
	 */
	private static class ClassFileFilter implements FileFilter {
		/**
		 * @inheritDoc
		 */
		public boolean accept(final File thePathname) {
			return thePathname.getName().toLowerCase().endsWith(".class");
		}
	}

	/**
	 * FileFilter implementation for filtering a list of files to only include jar files
	 */
	private static class JarFileFilter implements FileFilter {
		/**
		 * @inheritDoc
		 */
		public boolean accept(final File thePathname) {
			return thePathname.getName().toLowerCase().endsWith(".jar");
		}
	}

	/**
	 * Extends URLClassLoader for the sole purpose of exposing the addURL method.
	 */
	public static class MutableURLClassLoader extends URLClassLoader {

		public MutableURLClassLoader(URL[] urls, ClassLoader parent) {
			super(urls, parent);
		}

		public void addURL(URL... theURLs) {
			for (URL aURL : theURLs) {
				super.addURL(aURL);
			}
		}
	}

	public static void main(String[] args) throws Exception {		
//		ClassPath.add(new URL("file:///Users/mhgrove/Desktop/vaadin-6.0.0.jar"));
//
//		System.err.println(Thread.currentThread().getContextClassLoader());
//		System.err.println(mLoader.loadClass("com.vaadin.Application"));
//
//
//		List<Class> aList = new ArrayList<Class>(classes());
//		Collections.sort(aList, new AlphaNumericComparator<Class>());
//
//		for (Class c : aList) {
//			System.err.println(c);
//		}
//
//		System.err.println(ClassPath.class.getClassLoader());
//		System.err.println(Class.forName("com.vaadin.Application"));
	}
}

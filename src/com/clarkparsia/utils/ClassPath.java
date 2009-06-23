package com.clarkparsia.utils;

import java.io.File;
import java.io.IOException;
import java.io.FileFilter;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Collections;
import java.util.jar.JarFile;
import java.util.jar.JarEntry;
import java.lang.reflect.Modifier;

/**
 * Title: <br/>
 * Description: <br/>
 * Company: Clark & Parsia, LLC. <http://www.clarkparsia.com> <br/>
 * Created: Jun 22, 2009 8:26:25 AM <br/>
 *
 * @author Michael Grove <mike@clarkparsia.com>
 */
public class ClassPath {
	public static boolean QUIET = true;

	private static Collection<Class> mClasses;

	public static Collection<Class> instantiableClasses(Class theClass) {
		return CollectionUtil.filter(classes(theClass), new CollectionUtil.CollectionFilter<Class>() {
			public boolean accept(final Class theClass) {
				return !theClass.isInterface() && !Modifier.isAbstract(theClass.getModifiers());
			}
		});
	}

	public static Collection<Class> classes(final Class<?> theInterface) {
		return CollectionUtil.filter(classes(), new CollectionUtil.CollectionFilter<Class>() {
			public boolean accept(final Class theObject) {
				return theInterface.isAssignableFrom(theObject);
			}
		});
	}

	public synchronized static Collection<Class> classes() {
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

			mClasses = aClassList;
		}

		return mClasses;
	}

	private static Collection<? extends Class> listClassesFromDir(final File theFile) {
		// a dir with either jars or classes, or both

		Collection<Class> aClassList = new HashSet<Class>();

		// recurse the directory and file all the jar files and load the classes in them
		List<File> aJarList = BasicUtils.listFiles(theFile, new JarFileFilter());
		for (File aJarFile : aJarList) {
			aClassList.addAll(listClassesFromJar(aJarFile));
		}

		// now find all the class files and load the classes specified by them
		List<File> aClassFileList = BasicUtils.listFiles(theFile, new ClassFileFilter());
		for (File aClassFile : aClassFileList) {
			Class aClass = _class(classNameFromFileName(aClassFile.getAbsolutePath().substring(theFile.getAbsolutePath().length() + 1)));
			if (aClass != null) {
				aClassList.add(aClass);
			}
		}

		return aClassList;
	}

	private static Class _class(String theClassName) {
		if (theClassName == null) {
			return null;
		}

		// skip system classes, we don't really care if we include them, this is mostly for user defined stuff
		if (theClassName.startsWith("java.") || theClassName.startsWith("javax.") ||
			theClassName.startsWith("sun.") || theClassName.startsWith("com.sun.") ||
			theClassName.startsWith("apple.") || theClassName.startsWith("com.apple")) {
			return null;
		}

		try {
			return Class.forName(theClassName);
		}
		catch (Throwable e) {
			if (!QUIET) {
				e.printStackTrace();
			}
		}

		return null;
	}

	private static Collection<? extends Class> listClassesFromJar(File theFile) {
		Collection<Class> aClassList = new HashSet<Class>();

		try {
			if (theFile.canRead()) {
				JarFile aFile = new JarFile(theFile);
				Enumeration<JarEntry> aEntries = aFile.entries();
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
			}
		}
		catch (IOException e) {
			if (!QUIET) {
				e.printStackTrace();
			}
		}

		return aClassList;
	}

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

	public static void main(String[] args) {
		List<Class> aList = new ArrayList<Class>(classes(java.util.Properties.class));
		Collections.sort(aList, new AlphaNumericComparator());

		for (Class c : aList) {
			System.err.println(c);
		}
	}
}

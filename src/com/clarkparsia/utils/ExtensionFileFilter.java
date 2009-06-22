package com.clarkparsia.utils;

import javax.swing.filechooser.FileFilter;

import java.io.File;
import java.util.Collection;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Title: ExtensionFileFilter<br/>
 * Description: Implementation of the JFileChooser's FileFilter class which provides a way to filter files accepted
 * by a JFileChooser based on extension.<br/>
 * Company: Clark & Parsia, LLC. <http://www.clarkparsia.com> <br/>
 * Created: Jun 22, 2009 12:40:44 PM <br/>
 *
 * @author Michael Grove <mike@clarkparsia.com>
 */
public class ExtensionFileFilter extends FileFilter {

	/**
	 * A description of this filter
	 */
	private String mDescription;

	/**
	 * The file name extensions to accept
	 */
	private Collection<String> mExtensions;

	/**
	 * Create a new ExtensionFileFilter
	 * @param theDesc the description of the filter
	 * @param theExts the extensions to accept
	 */
	public ExtensionFileFilter(String theDesc, String... theExts) {
		mDescription = theDesc;
		mExtensions = new HashSet<String>(Arrays.asList(theExts));
	}

	/**
	 * @inheritDoc
	 */
	public boolean accept(final File f) {
		for (String aExt : mExtensions) {
			if (f.getName().endsWith(aExt)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * @inheritDoc
	 */
	public String getDescription() {
		return mDescription;
	}
}

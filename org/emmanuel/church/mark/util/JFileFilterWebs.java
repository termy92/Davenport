package org.emmanuel.church.mark.util;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 */
public class JFileFilterWebs extends FileFilter {
	// Accept all directories and all gif, jpg, tiff, or png files.
	/**
	 * Method accept.
	 * 
	 * @param f
	 *            File
	 * @return boolean
	 */
	@Override
	public boolean accept(File f) {
		if (f.isDirectory()) {
			return true;
		}

		String extension = JFileFilterExecTypes.getExtension(f);
		if (extension != null) {
			if (extension.equals(JFileFilterWebTypes.HTM) || extension.equals(JFileFilterWebTypes.HTML)) {
				return true;
			} else {
				return false;
			}
		}

		return false;
	}

	// The description of this filter
	/**
	 * Method getDescription.
	 * 
	 * @return String
	 */
	@Override
	public String getDescription() {
		return "Web";
	}
}

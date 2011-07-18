package org.emmanuel.church.mark.util;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 */
public class JFileFilterXLS extends FileFilter {
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

		String extension = JFileFilterXLSTypes.getExtension(f);
		if (extension != null) {
			if (extension.equals(JFileFilterXLSTypes.XLS)) {
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
		return "Excel Spreadsheet";
	}

}

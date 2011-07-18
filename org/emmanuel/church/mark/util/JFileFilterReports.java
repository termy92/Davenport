package org.emmanuel.church.mark.util;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 */
public class JFileFilterReports extends FileFilter {

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

		String extension = JFileFilterReportTypes.getExtension(f);
		if (extension != null) {
			if (extension.equals(JFileFilterReportTypes.JRXML)) {
				return true;
			} else {
				return false;
			}
		}

		return false;
	}

	/**
	 * Method getDescription.
	 * 
	 * @return String
	 */
	@Override
	public String getDescription() {
		return "Jasper Reports";
	}
}

package org.emmanuel.church.mark.sys;

import org.apache.log4j.Logger;
import org.emmanuel.church.mark.db.JDBModule;
import org.emmanuel.church.mark.util.JUtility;

/**
 */
public class JLaunchExec {

	/**
	 * Field mod. Value: {@value mod}
	 */
	public static JDBModule mod;

	/**
	 * Method runExec.
	 * 
	 * @param optionName
	 *            String
	 */
	public static void runExec(String host, String session, String optionName) {
		final Logger logger = Logger.getLogger(JLaunchMenu.class);
		mod = new JDBModule(host, session);

		logger.debug("runForm :" + optionName);

		mod.setModuleId(optionName);
		if (mod.getModuleProperties() == true) {
			String command = mod.getExecFilename();
			String dir = mod.getExecDir();
			JUtility.exec(command, dir);

		}

	}
}

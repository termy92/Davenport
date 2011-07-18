package org.emmanuel.church.mark.sys;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;
import org.emmanuel.church.mark.util.JPrint;
import org.emmanuel.church.mark.util.JUtility;

/**
 * @author smhong
 * 
 */
public class StartUp {

	private static Logger logger = Logger.getLogger(StartUp.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Common.base_dir = System.getProperty("user.dir");
		Common.applicationMode = "SwingClient";

		JUtility.initLogging("");
		JPrint.init();

		if (JUtility.isValidJavaVersion(Common.requiredJavaVersion) == false) {
			JUtility.errorBeep();
			JOptionPane.showMessageDialog(null, "This application requires java version " + Common.requiredJavaVersion
					+ " or higher.\n Detected version is " + System.getProperty("java.version"), "Information",
					JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
		logger.info("Application starting");

		Common.mainForm = new JFrameMain();
		Common.mainForm.setIconImage(Common.imageIconloader.getImageIcon(Common.image_trophy).getImage());
		Common.mainForm.setVisible(true);
	}
}

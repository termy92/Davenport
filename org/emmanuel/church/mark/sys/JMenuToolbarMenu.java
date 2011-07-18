/*
 * Created on 05-Apr-2005
 *
 */
package org.emmanuel.church.mark.sys;

import javax.swing.JOptionPane;
import javax.swing.JToolBar;

import org.apache.log4j.Logger;

/**
 * @author David
 * 
 */
public class JMenuToolbarMenu {
	final Logger logger = Logger.getLogger(JMenuToolbarMenu.class);

	private String hostID;
	private String sessionID;

	public JMenuToolbarMenu(String host, String session) {
		setHostID(host);
		setSessionID(session);
	}

	private void setSessionID(String session) {
		sessionID = session;
	}

	private void setHostID(String host) {
		hostID = host;
	}

	private String getHostID() {
		return hostID;
	}

	private String getSessionID() {
		return sessionID;
	}

	public void buildMenu(JToolBar jtb) {

		try {
			// PreparedStatement stmt;
			// ResultSet rs;
			//
			// stmt = Common.hostList
			// .getHost(getHostID())
			// .getConnection(getSessionID())
			// .prepareStatement(
			// Common.hostList.getHost(getHostID()).getSqlstatements()
			// .getSQL("JMenuToolbarMenu.buildMenu"));
			// stmt.setString(1,
			// Common.userList.getUser(getSessionID()).getUserId());
			// stmt.setFetchSize(25);
			// rs = stmt.executeQuery();
			//
			// while (rs.next()) {

			// JMenuOption menuOption = new JMenuOption(getHostID(),
			// getSessionID());
			// menuOption.load(rs);
			//
			// JMenuToolbarMenuItem newmenuitem = new
			// JMenuToolbarMenuItem(menuOption);
			// newmenuitem.setSize(35, 35);
			// newmenuitem.setPreferredSize(new Dimension(35, 35));
			// newmenuitem.setMinimumSize(new Dimension(35, 35));
			// newmenuitem.addActionListener(new ActionListener() {
			// public void actionPerformed(ActionEvent evt) {
			// JMenuToolbarMenuItem tbMenuItem = (JMenuToolbarMenuItem)
			// evt.getSource();
			// if (tbMenuItem.getModuleType().equals("FORM")) {
			// JLaunchMenu.runForm(tbMenuItem.toString());
			// }
			// if (tbMenuItem.getModuleType().equals("REPORT")) {
			// JLaunchReport.runReport(tbMenuItem.toString(), null, "", null,
			// "");
			// }
			// }
			// });
			//
			// jtb.add(newmenuitem);
			// logger.debug("buildMenu :"+newmenuitem);

			// }
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Error in JMenuToolbarMenu " + ex.getMessage());
		}
	}
}

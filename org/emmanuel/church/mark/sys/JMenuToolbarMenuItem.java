/*
 * Created on 05-Apr-2005
 *
 */
package org.emmanuel.church.mark.sys;

import java.awt.Dimension;

import javax.swing.JButton;

import org.emmanuel.church.mark.db.JDBModule;

// import javax.swing.*;

/**
 * @author David
 * 
 */
public class JMenuToolbarMenuItem extends JButton {
	private static final long serialVersionUID = 1;

	// private JMenuOption menuoption = new JMenuOption(Common.selectedHostID,
	// Common.sessionID);
	private JMenuOption menuoption = new JMenuOption("host", "session");

	public JMenuToolbarMenuItem(JMenuOption mo) {
		super();

		menuoption = mo;

		this.setToolTipText(menuoption.hint);

		// JUtility.previewIcon(this,menuoption.Icon_Filename);
		this.setIcon(JDBModule.getModuleIcon(mo.iconFilename, mo.moduleType));
		this.setSize(30, 30);
		this.setMaximumSize(new Dimension(30, 30));
		this.setMinimumSize(new Dimension(30, 30));
		this.setPreferredSize(new Dimension(30, 30));

	}

	public String getModuleType() {
		return menuoption.moduleType;
	}

	@Override
	public String toString() {
		return menuoption.moduleID;
	}
}

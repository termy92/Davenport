package org.emmanuel.church.mark.sys;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultDesktopManager;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;

import org.emmanuel.church.mark.app.JVersion;
import org.emmanuel.church.mark.db.JDBListData;

/**
 * @author smhong
 */
public class JFrameMain extends JFrame implements ComponentListener {

	protected JInternalFrameMenuTree treeMenu;

	// Class added to reposition minimised windows.
	class AppDesktopManager extends DefaultDesktopManager {
		private static final long serialVersionUID = 1;

		public void reIconifyFrame(JInternalFrame jif) {
			super.deiconifyFrame(jif);
			Rectangle rect = getBoundsForIconOf(jif);
			super.iconifyFrame(jif);
			jif.getDesktopIcon().setBounds(rect);
		}
	}

	@Override
	public void componentHidden(ComponentEvent e) {
	}

	@Override
	public void componentMoved(ComponentEvent e) {
	}

	@Override
	public void componentResized(ComponentEvent e) {
		// Next Line added to resize menu
		setTreeSize();

		// Remaining lines added to reposition minimised windows.
		JDesktopPane jdpPane = (JDesktopPane) e.getComponent();
		AppDesktopManager dm = (AppDesktopManager) jdpPane.getDesktopManager();
		JInternalFrame[] jifs = jdpPane.getAllFrames();
		for (int i = 0; i < jifs.length; i++) {
			if (jifs[i].isIcon()) {
				dm.reIconifyFrame(jifs[i]);
			}
		}
	}

	@Override
	public void componentShown(ComponentEvent arg0) {
	}

	public void setTreeSize() {
		int Height;
		Height = this.getHeight() - 150;
		if (Height < 100)
			Height = 100;
		treeMenu.setBounds(0, 0, Common.menuTreeWidth + Common.LFTreeMenuAdjustWidth, Height);
	}

	private static void ConfirmExit() {
		int question = JOptionPane.showConfirmDialog(Common.mainForm, "Exit application ?", "Confirm",
				JOptionPane.YES_NO_OPTION);
		if (question == 0) {
			// Common.hostList.getHost(Common.selectedHostID).disconnect(Common.sessionID);
			System.exit(0);
		}
	}

	static class WindowListener extends WindowAdapter {
		@Override
		public void windowClosing(WindowEvent e) {
			ConfirmExit();
		}
	}

	private final JButton btnHome = new JButton(Common.icon_home);
	private final JButton btnExit = new JButton(Common.icon_close);
	private final JButton btnHelp = new JButton(Common.icon_help);
	private final JButton btnCascade = new JButton(Common.icon_cascade);
	private final JButton btnMinimize = new JButton(Common.icon_minimize);
	private final JButton btnRestore = new JButton(Common.icon_restore);
	private final JButton btnExecute = new JButton(Common.icon_execute);

	final JDesktopPane desktopPane = new JDesktopPane();
	private final Container contentPane = getContentPane();
	private final JMenuBar menuBar = new JMenuBar();
	private final JMenu mFile = new JMenu("File");
	private final JMenu mWindow = new JMenu("Window");
	private final JMenu mView = new JMenu("View");

	private final JMenuItem mMenu = new JMenuItem("Menu");
	private final JMenuItem mCascade = new JMenuItem("Cascade");
	private final JMenuItem mMinimize = new JMenuItem("Minimize");
	private final JMenuItem mRestore = new JMenuItem("Restore");

	private final JToolBar jtb = new JToolBar();
	// private final JMenuToolbarMenu tbm = new
	// JMenuToolbarMenu(Common.selectedHostID, Common.sessionID);
	private final JMenuToolbarMenu tbm = new JMenuToolbarMenu("SelectedHostID", "SessionID");
	private final JStatusBar jsb = new JStatusBar();
	private final JComboBox jcb = new JComboBox();
	private final DefaultComboBoxModel defComboBoxMod = new DefaultComboBoxModel();
	private final ComboBoxModel comboModel = defComboBoxMod;

	private final JMenuItem mExit = new JMenuItem("Exit");

	public JFrameMain() {
		super(Common.applicationName + " " + JVersion.getProgramVersion());
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(screenSize.width, screenSize.height - 50);
		setLocation(0, 0);
		setResizable(true);

		setExtendedState(Frame.MAXIMIZED_HORIZ);
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowListener());

		desktopPane.setDesktopManager(new AppDesktopManager());
		desktopPane.setBackground(Color.WHITE);
		contentPane.add(desktopPane, BorderLayout.CENTER);
		ButtonHandler buttonHandler = new ButtonHandler();

		// Menu Bar
		menuBar.setFont(Common.font_menu);
		setJMenuBar(menuBar);

		// File menu
		mFile.setFont(Common.font_menu);
		mFile.setMnemonic(java.awt.event.KeyEvent.VK_F);

		mExit.setFont(Common.font_menu);
		mExit.setIcon(Common.imageIconloader.getImageIcon(Common.image_close));
		mExit.addActionListener(buttonHandler);

		mFile.add(mExit);

		// View menu
		mView.setFont(Common.font_menu);
		mView.setMnemonic(java.awt.event.KeyEvent.VK_V);

		mMenu.setFont(Common.font_menu);
		mMenu.setIcon(Common.imageIconloader.getImageIcon(Common.image_home));
		mMenu.addActionListener(buttonHandler);

		mView.add(mMenu);

		// Window menu
		mWindow.setFont(Common.font_menu);
		mWindow.setMnemonic(java.awt.event.KeyEvent.VK_W);

		menuBar.add(mFile);
		menuBar.add(mView);
		menuBar.add(mWindow);

		btnHome.addActionListener(buttonHandler);
		btnHome.setToolTipText("Display Menu Tree");
		btnHome.setSize(30, 30);
		btnHome.setMaximumSize(new Dimension(30, 30));
		btnHome.setMinimumSize(new Dimension(30, 30));
		btnHome.setPreferredSize(new Dimension(30, 30));

		btnMinimize.addActionListener(buttonHandler);
		btnMinimize.setToolTipText("Minimize open windows.");
		btnHome.setSize(30, 30);
		btnMinimize.setMaximumSize(new Dimension(30, 30));
		btnMinimize.setMinimumSize(new Dimension(30, 30));
		btnMinimize.setPreferredSize(new Dimension(30, 30));

		btnCascade.addActionListener(buttonHandler);
		btnCascade.setToolTipText("Cascade open windows.");
		btnCascade.setSize(30, 30);
		btnCascade.setMaximumSize(new Dimension(30, 30));
		btnCascade.setMinimumSize(new Dimension(30, 30));
		btnCascade.setPreferredSize(new Dimension(30, 30));

		btnRestore.addActionListener(buttonHandler);
		btnRestore.setToolTipText("Restore iconified windows.");
		btnRestore.setSize(30, 30);
		btnRestore.setMaximumSize(new Dimension(30, 30));
		btnRestore.setMinimumSize(new Dimension(30, 30));
		btnRestore.setPreferredSize(new Dimension(30, 30));

		btnHelp.addActionListener(buttonHandler);
		btnHelp.setToolTipText("Help");
		btnHelp.setSize(30, 30);
		btnHelp.setMaximumSize(new Dimension(30, 30));
		btnHelp.setMinimumSize(new Dimension(30, 30));
		btnHelp.setPreferredSize(new Dimension(30, 30));

		btnExit.addActionListener(buttonHandler);
		btnExit.setToolTipText("Exit application");
		btnExit.setSize(30, 30);
		btnExit.setMaximumSize(new Dimension(30, 30));
		btnExit.setMinimumSize(new Dimension(30, 30));
		btnExit.setPreferredSize(new Dimension(30, 30));

		btnExecute.addActionListener(buttonHandler);
		btnExecute.setToolTipText("Execute Quick Menu Option.");
		btnExecute.setSize(30, 30);
		btnExecute.setMaximumSize(new Dimension(30, 30));
		btnExecute.setMinimumSize(new Dimension(30, 30));
		btnExecute.setPreferredSize(new Dimension(30, 30));

		// Tool bar
		jtb.setOrientation(0);
		jtb.setSize(jtb.getSize().width, 40);
		jtb.setPreferredSize(new Dimension(jtb.getSize().width, 40));
		jtb.setFloatable(false);

		jtb.add(btnHome);
		jtb.add(new JToolBar.Separator());
		tbm.buildMenu(this.jtb);
		jtb.add(new JToolBar.Separator());
		jtb.add(btnMinimize);
		jtb.add(btnCascade);
		jtb.add(btnRestore);
		jtb.add(btnHelp);
		jtb.add(new JToolBar.Separator());

		jcb.setMaximumSize(new Dimension(300, 26));
		jcb.setMaximumRowCount(30);
		jcb.setToolTipText("Quick Launch Menu");

		jcb.setModel(comboModel);
		// jcb.setRenderer(Common.renderer_list);

		jtb.add(jcb);
		jtb.add(btnExecute);
		jtb.add(new JToolBar.Separator());
		jtb.add(btnExit);

		contentPane.add(jtb, BorderLayout.NORTH);
		contentPane.add(jsb, BorderLayout.SOUTH);

		// Tree menu
		treeMenu = new JInternalFrameMenuTree("root", "Menu", true, false, false, true, menuBar, mView);
		treeMenu.setVisible(true);

		desktopPane.add(treeMenu);
		setTreeSize();
		desktopPane.addComponentListener(this);
	}

	public class ButtonHandler implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if (event.getSource() == btnHome | event.getSource() == mMenu) {
				if (treeMenu.isIcon()) {
					treeMenu.setVisible(true);

					try {
						treeMenu.setIcon(false);
					} catch (Exception ex) {
						System.out.println("Cannot setIcon on treeMenu");
					}

					try {
						treeMenu.setSelected(true);
					} catch (Exception ex) {
						System.out.println("Cannot setSelected on treeMenu");
					}
				} else {
					try {
						treeMenu.setIcon(true);
					} catch (Exception ex) {
						System.out.println("Cannot setIcon on treeMenu");
					}
				}
			}

			// if (event.getSource() == mHelpAbout) {
			// JLaunchMenu.runDialog("FRM_ABOUT");
			// }

			// if (event.getSource() == mHelpSystemProperties) {
			// JLaunchMenu.runDialog("FRM_SYSTEM_PROPERTIES");
			// }

			if (event.getSource() == mCascade) {
				JLaunchMenu.cascadeFrames();
			}
			if (event.getSource() == mMinimize) {
				JLaunchMenu.minimizeAll();
			}
			if (event.getSource() == mRestore) {
				JLaunchMenu.restoreAll();
			}
			if (event.getSource() == btnCascade) {
				JLaunchMenu.cascadeFrames();
			}
			if (event.getSource() == btnRestore) {
				JLaunchMenu.restoreAll();
			}
			if (event.getSource() == btnMinimize) {
				JLaunchMenu.minimizeAll();
			}
			if (event.getSource() == btnExecute) {
				try {
					JDBListData ld = (JDBListData) jcb.getSelectedItem();
					JMenuOption mo = ((JMenuOption) ld.getObject());
					String x = mo.moduleID;
					if (mo.moduleType.equals("FORM")) {
						JLaunchMenu.runForm(x);
					}
					if (mo.moduleType.equals("REPORT")) {
						JLaunchReport.runReport(x, null, "", null, "");
					}
					if (mo.moduleType.equals("EXEC")) {
						JLaunchExec.runExec(Common.selectedHostID, Common.sessionID, x);
					}
				} catch (Exception ex) {

				}
			}
			if (event.getSource() == btnExit | event.getSource() == mExit) {
				ConfirmExit();
			}
		}
	}
}

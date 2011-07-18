package org.emmanuel.church.mark.sys;

import java.awt.BorderLayout;

import javax.swing.JDesktopPane;
import javax.swing.JPanel;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JRViewer;

public class JInternalFrameReportViewer extends javax.swing.JInternalFrame {
	private static final long serialVersionUID = 1;
	private JDesktopPane jDesktopPane1;
	private JPanel pnlMain;

	/**
	 * Auto-generated main method to display this JInternalFrame inside a new
	 * JFrame.
	 */

	public JInternalFrameReportViewer() {
		super();
		initGUI();
	}

	public JInternalFrameReportViewer(JasperPrint jp, boolean isXML) {
		super();
		initGUI();
		try {
			{
				JRViewer viewer = new JRViewer(jp);
				pnlMain.add(viewer, BorderLayout.CENTER);
				viewer.setPreferredSize(new java.awt.Dimension(647, 451));
				viewer.setAutoscrolls(true);
			}
		} catch (Exception e) {

		}
	}

	private void initGUI() {
		try {
			this.setPreferredSize(new java.awt.Dimension(674, 531));
			this.setBounds(25, 25, 774, 531);

			this.setClosable(true);
			this.setTitle("Report Viewer");
			this.setIconifiable(true);
			this.setMaximizable(true);
			this.setResizable(true);
			{
				jDesktopPane1 = new JDesktopPane();
				BorderLayout jDesktopPane1Layout = new BorderLayout();
				jDesktopPane1.setLayout(jDesktopPane1Layout);
				this.getContentPane().add(jDesktopPane1, BorderLayout.CENTER);
				{
					pnlMain = new JPanel();
					BorderLayout pnlMainLayout = new BorderLayout();
					pnlMain.setLayout(pnlMainLayout);
					jDesktopPane1.add(pnlMain, BorderLayout.CENTER);
					pnlMain.setBounds(1, 2, 660, 496);

				}
			}
			// setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

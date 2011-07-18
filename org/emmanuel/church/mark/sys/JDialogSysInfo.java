package org.emmanuel.church.mark.sys;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import org.emmanuel.church.mark.util.JUtility;

public class JDialogSysInfo extends javax.swing.JDialog {
	private JTextField jTextFieldHosyUniqueRef;
	private JLabel jLabel102_3;
	private JTextField jTextFieldUserHostDescription;
	private JLabel jLabel102_2;
	private JTextField jTextFieldUserHostID;
	private JTextField jTextFieldUserSessionID;
	private JLabel jLabel102_1;
	private JTextField jTextFieldUserNameApp;
	private JLabel jLabel102;
	private JTextField jTextFieldClientName;
	private JLabel jLabel101;
	private static final long serialVersionUID = 1;
	private JDesktopPane jDesktopPane1;
	private JButton jButtonClose;
	private JLabel jLabel2;
	private JLabel jLabel4;
	private JTextField jTextFieldJDBCDriverName;
	private JTextField jTextFieldUserName;
	private JLabel jLabel11;
	private JLabel jLabel10;
	private JLabel jLabel8;
	private JLabel jLabel15;
	private JLabel jLabel14;
	private JTextField jTextFieldFreeMemory;
	private JTextField jTextFieldTotalMemory;
	private JLabel jLabel13;
	private JTextField jTextFieldJDBCDriverVersion;
	private JLabel jLabel12;
	private JLabel jLabel6;
	private JTextField jTextFieldDatabaseProductVersion;
	private JLabel jLabel5;
	private JTextField jTextFieldDatabaseProductName;
	private JTextField jTextFieldUserDir;
	private JTextField jTextFieldUserHome;
	private JTextField jTextFieldOSVersion;
	private JTextField jTextFieldOSArchitecture;
	private JTextField jTextFieldOSName;
	private JTextField jTextFieldJavaVMVersion;
	private JTextField jTextFieldJavaVersion;
	private JTextField jTextFieldJavaHome;
	private JLabel jLabel9;
	private JLabel jLabel7;
	private JLabel jLabel3;
	private JLabel jLabel1;
	private final JTextField textFieldUsedMemory;
	private final JTextField textFieldLocale;

	public JDialogSysInfo(JFrame frame) {
		super(frame);
		initGUI();

		jTextFieldUserDir.setText(System.getProperty("user.dir"));
		jTextFieldUserHome.setText(System.getProperty("user.home"));
		jTextFieldUserName.setText(System.getProperty("user.name"));
		jTextFieldUserSessionID.setText(Common.sessionID);
		jTextFieldUserHostID.setText(Common.selectedHostID);
		// jTextFieldUserHostDescription.setText(Common.hostList.getHost(Common.selectedHostID).getSiteDescription());
		// jTextFieldHosyUniqueRef.setText(Common.hostList.getHost(Common.selectedHostID).getUniqueID());
		jTextFieldOSVersion.setText(System.getProperty("os.version"));
		jTextFieldOSArchitecture.setText(System.getProperty("os.arch"));
		jTextFieldOSName.setText(System.getProperty("os.name"));
		jTextFieldJavaHome.setText(System.getProperty("java.home"));
		jTextFieldJavaVMVersion.setText(System.getProperty("java.vm.version"));
		jTextFieldJavaVersion.setText(System.getProperty("java.version"));

		// try {
		//
		// DatabaseMetaData dmd =
		// Common.hostList.getHost(Common.selectedHostID).getConnection(Common.sessionID)
		// .getMetaData();
		// jTextFieldDatabaseProductName.setText(dmd.getDatabaseProductName());
		// jTextFieldDatabaseProductVersion.setText(dmd.getDatabaseProductVersion());
		// jTextFieldJDBCDriverName.setText(dmd.getDriverName());
		// jTextFieldJDBCDriverVersion.setText(dmd.getDriverVersion());
		// } catch (SQLException E) {
		//
		// }
		jTextFieldTotalMemory.setText(String.valueOf(Runtime.getRuntime().totalMemory() / 1024) + "k");
		jTextFieldFreeMemory.setText(String.valueOf(Runtime.getRuntime().freeMemory() / 1024) + "k");

		JLabel lblUsedMemory = new JLabel();
		lblUsedMemory.setText("Used Memory :");
		lblUsedMemory.setHorizontalAlignment(SwingConstants.TRAILING);
		lblUsedMemory.setFont(new Font("Dialog", Font.PLAIN, 11));
		lblUsedMemory.setBounds(530, 352, 91, 21);
		jDesktopPane1.add(lblUsedMemory);

		textFieldUsedMemory = new JTextField();
		textFieldUsedMemory.setText(String.valueOf((Runtime.getRuntime().totalMemory() - Runtime.getRuntime()
				.freeMemory()) / 1024) + "k");
		textFieldUsedMemory.setHorizontalAlignment(SwingConstants.TRAILING);
		textFieldUsedMemory.setFont(new Font("Dialog", Font.PLAIN, 11));
		textFieldUsedMemory.setEnabled(false);
		textFieldUsedMemory.setEditable(false);
		textFieldUsedMemory.setDisabledTextColor(Color.BLACK);
		textFieldUsedMemory.setBounds(632, 353, 119, 21);
		jDesktopPane1.add(textFieldUsedMemory);

		JLabel lblLocale = new JLabel();
		lblLocale.setText("Locale :");
		lblLocale.setHorizontalAlignment(SwingConstants.TRAILING);
		lblLocale.setFont(new Font("Dialog", Font.PLAIN, 11));
		lblLocale.setBounds(476, 12, 58, 21);
		jDesktopPane1.add(lblLocale);

		textFieldLocale = new JTextField();
		textFieldLocale.setFont(new Font("Dialog", Font.PLAIN, 11));
		textFieldLocale.setEnabled(false);
		textFieldLocale.setEditable(false);
		textFieldLocale.setDisabledTextColor(Color.BLACK);
		textFieldLocale.setBounds(539, 11, 58, 20);
		jDesktopPane1.add(textFieldLocale);
		textFieldLocale.setText(Locale.getDefault().getCountry() + "," + Locale.getDefault().getLanguage());
	}

	private void initGUI() {
		try {
			{
				this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
				this.setModal(true);
				this.setTitle("System Properties");
				this.setResizable(false);
			}
			{
				jDesktopPane1 = new JDesktopPane();
				jDesktopPane1.setBackground(Color.WHITE);
				this.getContentPane().add(jDesktopPane1, BorderLayout.NORTH);
				jDesktopPane1.setPreferredSize(new java.awt.Dimension(770, 450));
				jDesktopPane1.setLayout(null);
				{
					jButtonClose = new JButton(Common.icon_close);
					jDesktopPane1.add(jButtonClose);
					jButtonClose.setText("Close");
					jButtonClose.setFont(Common.font_btn);
					jButtonClose.setBounds(330, 396, 105, 28);
					jButtonClose.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							dispose();
						}
					});
				}
				{
					jLabel1 = new JLabel();
					jDesktopPane1.add(jLabel1);
					jLabel1.setText("Version :");
					jLabel1.setBounds(59, 258, 58, 21);
					jLabel1.setHorizontalAlignment(SwingConstants.TRAILING);
					jLabel1.setFont(Common.font_std);
				}
				{
					jLabel2 = new JLabel();
					jDesktopPane1.add(jLabel2);
					jLabel2.setText("Java Version :");
					jLabel2.setHorizontalAlignment(SwingConstants.TRAILING);
					jLabel2.setFont(Common.font_std);
					jLabel2.setBounds(25, 10, 92, 21);
				}
				{
					jLabel3 = new JLabel();
					jDesktopPane1.add(jLabel3);
					jLabel3.setText("Java VM Version :");
					jLabel3.setHorizontalAlignment(SwingConstants.TRAILING);
					jLabel3.setFont(Common.font_std);
					jLabel3.setBounds(232, 10, 119, 21);
				}
				{
					jLabel4 = new JLabel();
					jDesktopPane1.add(jLabel4);
					jLabel4.setText("Java Home :");
					jLabel4.setHorizontalAlignment(SwingConstants.TRAILING);
					jLabel4.setFont(Common.font_std);
					jLabel4.setBounds(29, 40, 88, 21);
				}
				{
					jLabel7 = new JLabel();
					jDesktopPane1.add(jLabel7);
					jLabel7.setText("OS Name :");
					jLabel7.setHorizontalAlignment(SwingConstants.TRAILING);
					jLabel7.setFont(Common.font_std);
					jLabel7.setBounds(27, 72, 90, 21);
				}
				{
					jLabel8 = new JLabel();
					jDesktopPane1.add(jLabel8);
					jLabel8.setText("OS Architecture :");
					jLabel8.setHorizontalAlignment(SwingConstants.TRAILING);
					jLabel8.setFont(Common.font_std);
					jLabel8.setBounds(232, 72, 118, 21);
				}
				{
					jLabel9 = new JLabel();
					jDesktopPane1.add(jLabel9);
					jLabel9.setText("OS Version :");
					jLabel9.setHorizontalAlignment(SwingConstants.TRAILING);
					jLabel9.setFont(Common.font_std);
					jLabel9.setBounds(481, 72, 95, 21);
				}
				{
					jLabel10 = new JLabel();
					jDesktopPane1.add(jLabel10);
					jLabel10.setText("OS User Name :");
					jLabel10.setHorizontalAlignment(SwingConstants.TRAILING);
					jLabel10.setFont(Common.font_std);
					jLabel10.setBounds(24, 103, 93, 21);
				}
				{
					jLabel11 = new JLabel();
					jDesktopPane1.add(jLabel11);
					jLabel11.setText("User Home :");
					jLabel11.setHorizontalAlignment(SwingConstants.TRAILING);
					jLabel11.setFont(Common.font_std);
					jLabel11.setBounds(27, 165, 90, 21);
				}
				{
					jTextFieldJavaHome = new JTextField();
					jTextFieldJavaHome.setFont(Common.font_std);
					jDesktopPane1.add(jTextFieldJavaHome);
					jTextFieldJavaHome.setBounds(129, 42, 622, 20);
					jTextFieldJavaHome.setEditable(false);
					jTextFieldJavaHome.setEnabled(false);
					jTextFieldJavaHome.setDisabledTextColor(Common.color_textdisabled);
				}
				{
					jTextFieldJavaVersion = new JTextField();
					jTextFieldJavaVersion.setFont(Common.font_std);
					jDesktopPane1.add(jTextFieldJavaVersion);
					jTextFieldJavaVersion.setBounds(129, 11, 100, 20);
					jTextFieldJavaVersion.setEditable(false);
					jTextFieldJavaVersion.setEnabled(false);
					jTextFieldJavaVersion.setDisabledTextColor(Common.color_textdisabled);
				}
				{
					jTextFieldJavaVMVersion = new JTextField();
					jTextFieldJavaVMVersion.setFont(Common.font_std);
					jDesktopPane1.add(jTextFieldJavaVMVersion);
					jTextFieldJavaVMVersion.setBounds(360, 11, 100, 20);
					jTextFieldJavaVMVersion.setEditable(false);
					jTextFieldJavaVMVersion.setEnabled(false);
					jTextFieldJavaVMVersion.setDisabledTextColor(Common.color_textdisabled);
				}
				{
					jTextFieldOSName = new JTextField();
					jTextFieldOSName.setFont(Common.font_std);
					jDesktopPane1.add(jTextFieldOSName);
					jTextFieldOSName.setBounds(129, 73, 100, 20);
					jTextFieldOSName.setEditable(false);
					jTextFieldOSName.setEnabled(false);
					jTextFieldOSName.setDisabledTextColor(Common.color_textdisabled);
				}
				{
					jTextFieldOSArchitecture = new JTextField();
					jTextFieldOSArchitecture.setFont(Common.font_std);
					jDesktopPane1.add(jTextFieldOSArchitecture);
					jTextFieldOSArchitecture.setBounds(360, 73, 100, 20);
					jTextFieldOSArchitecture.setEditable(false);
					jTextFieldOSArchitecture.setEnabled(false);
					jTextFieldOSArchitecture.setDisabledTextColor(Common.color_textdisabled);
				}
				{
					jTextFieldOSVersion = new JTextField();
					jTextFieldOSVersion.setFont(Common.font_std);
					jDesktopPane1.add(jTextFieldOSVersion);
					jTextFieldOSVersion.setBounds(582, 73, 169, 20);
					jTextFieldOSVersion.setEditable(false);
					jTextFieldOSVersion.setEnabled(false);
					jTextFieldOSVersion.setDisabledTextColor(Common.color_textdisabled);
				}
				{
					jTextFieldUserName = new JTextField();
					jTextFieldUserName.setFont(Common.font_std);
					jDesktopPane1.add(jTextFieldUserName);
					jTextFieldUserName.setEditable(false);
					jTextFieldUserName.setBounds(129, 104, 231, 20);
					jTextFieldUserName.setEnabled(false);
					jTextFieldUserName.setDisabledTextColor(Common.color_textdisabled);
				}
				{
					jTextFieldUserHome = new JTextField();
					jTextFieldUserHome.setFont(Common.font_std);
					jDesktopPane1.add(jTextFieldUserHome);
					jTextFieldUserHome.setEditable(false);
					jTextFieldUserHome.setBounds(129, 166, 624, 20);
					jTextFieldUserHome.setEnabled(false);
					jTextFieldUserHome.setDisabledTextColor(Common.color_textdisabled);
				}
				{
					jTextFieldUserDir = new JTextField();
					jTextFieldUserDir.setFont(Common.font_std);
					jDesktopPane1.add(jTextFieldUserDir);
					jTextFieldUserDir.setEditable(false);
					jTextFieldUserDir.setBounds(129, 197, 624, 20);
					jTextFieldUserDir.setEnabled(false);
					jTextFieldUserDir.setDisabledTextColor(Common.color_textdisabled);
				}
				{
					jTextFieldDatabaseProductName = new JTextField();
					jTextFieldDatabaseProductName.setFont(Common.font_std);
					jDesktopPane1.add(jTextFieldDatabaseProductName);
					jTextFieldDatabaseProductName.setEditable(false);
					jTextFieldDatabaseProductName.setBounds(129, 228, 231, 20);
					jTextFieldDatabaseProductName.setEnabled(false);
					jTextFieldDatabaseProductName.setDisabledTextColor(Common.color_textdisabled);
				}
				{
					jLabel5 = new JLabel();
					jDesktopPane1.add(jLabel5);
					jLabel5.setText("Base Dir :");
					jLabel5.setHorizontalAlignment(SwingConstants.TRAILING);
					jLabel5.setFont(Common.font_std);
					jLabel5.setBounds(27, 196, 90, 21);
				}
				{
					jTextFieldDatabaseProductVersion = new JTextField();
					jTextFieldDatabaseProductVersion.setFont(Common.font_std);
					jDesktopPane1.add(jTextFieldDatabaseProductVersion);
					jTextFieldDatabaseProductVersion.setEditable(false);
					jTextFieldDatabaseProductVersion.setBounds(129, 259, 623, 20);
					jTextFieldDatabaseProductVersion.setEnabled(false);
					jTextFieldDatabaseProductVersion.setDisabledTextColor(Common.color_textdisabled);
				}
				{
					jLabel6 = new JLabel();
					jDesktopPane1.add(jLabel6);
					jLabel6.setText("Version :");
					jLabel6.setHorizontalAlignment(SwingConstants.TRAILING);
					jLabel6.setFont(Common.font_std);
					jLabel6.setBounds(66, 320, 51, 21);
				}
				{
					jLabel12 = new JLabel();
					jDesktopPane1.add(jLabel12);
					jLabel12.setText("Database Name :");
					jLabel12.setHorizontalAlignment(SwingConstants.TRAILING);
					jLabel12.setFont(Common.font_std);
					jLabel12.setBounds(17, 227, 100, 21);
				}
				{
					jTextFieldJDBCDriverName = new JTextField();
					jTextFieldJDBCDriverName.setFont(Common.font_std);
					jDesktopPane1.add(jTextFieldJDBCDriverName);
					jTextFieldJDBCDriverName.setEditable(false);
					jTextFieldJDBCDriverName.setBounds(129, 290, 231, 20);
					jTextFieldJDBCDriverName.setEnabled(false);
					jTextFieldJDBCDriverName.setDisabledTextColor(Common.color_textdisabled);
				}
				{
					jTextFieldJDBCDriverVersion = new JTextField();
					jTextFieldJDBCDriverVersion.setFont(Common.font_std);
					jDesktopPane1.add(jTextFieldJDBCDriverVersion);
					jTextFieldJDBCDriverVersion.setEditable(false);
					jTextFieldJDBCDriverVersion.setBounds(129, 321, 622, 20);
					jTextFieldJDBCDriverVersion.setEnabled(false);
					jTextFieldJDBCDriverVersion.setDisabledTextColor(Common.color_textdisabled);
				}
				{
					jLabel13 = new JLabel();
					jDesktopPane1.add(jLabel13);
					jLabel13.setText("Driver Name :");
					jLabel13.setHorizontalAlignment(SwingConstants.TRAILING);
					jLabel13.setFont(Common.font_std);
					jLabel13.setBounds(37, 289, 80, 21);
				}
				{
					jTextFieldTotalMemory = new JTextField();
					jDesktopPane1.add(jTextFieldTotalMemory);
					jTextFieldTotalMemory.setFont(Common.font_std);
					jTextFieldTotalMemory.setEditable(false);
					jTextFieldTotalMemory.setDisabledTextColor(Common.color_textdisabled);
					jTextFieldTotalMemory.setEnabled(false);
					jTextFieldTotalMemory.setBounds(129, 352, 119, 21);
					jTextFieldTotalMemory.setHorizontalAlignment(SwingConstants.TRAILING);
				}
				{
					jTextFieldFreeMemory = new JTextField();
					jDesktopPane1.add(jTextFieldFreeMemory);
					jTextFieldFreeMemory.setFont(Common.font_std);
					jTextFieldFreeMemory.setEditable(false);
					jTextFieldFreeMemory.setDisabledTextColor(Common.color_textdisabled);
					jTextFieldFreeMemory.setEnabled(false);
					jTextFieldFreeMemory.setBounds(359, 352, 119, 21);
					jTextFieldFreeMemory.setHorizontalAlignment(SwingConstants.TRAILING);
				}
				{
					jLabel14 = new JLabel();
					jDesktopPane1.add(jLabel14);
					jLabel14.setText("Total Memory :");
					jLabel14.setHorizontalAlignment(SwingConstants.TRAILING);
					jLabel14.setFont(Common.font_std);
					jLabel14.setBounds(12, 352, 105, 21);
				}
				{
					jLabel15 = new JLabel();
					jDesktopPane1.add(jLabel15);
					jLabel15.setText("Free Memory :");
					jLabel15.setHorizontalAlignment(SwingConstants.TRAILING);
					jLabel15.setFont(Common.font_std);
					jLabel15.setBounds(254, 352, 91, 21);
				}

				{
					jLabel101 = new JLabel();
					jLabel101.setHorizontalAlignment(SwingConstants.TRAILING);
					jLabel101.setFont(new Font("Dialog", Font.PLAIN, 11));
					jLabel101.setText("Client Name :");
					jLabel101.setBounds(24, 134, 93, 21);
					jDesktopPane1.add(jLabel101);
				}

				{
					jTextFieldClientName = new JTextField();
					jTextFieldClientName.setFont(new Font("Dialog", Font.PLAIN, 11));
					jTextFieldClientName.setEditable(false);
					jTextFieldClientName.setDisabledTextColor(Common.color_textdisabled);
					jTextFieldClientName.setBounds(129, 135, 231, 20);
					jTextFieldClientName.setText(JUtility.getClientName());
					jDesktopPane1.add(jTextFieldClientName);
				}

				{
					jLabel102 = new JLabel();
					jLabel102.setHorizontalAlignment(SwingConstants.TRAILING);
					jLabel102.setFont(new Font("Dialog", Font.PLAIN, 11));
					jLabel102.setText("Application User Name :");
					jLabel102.setBounds(355, 103, 146, 21);
					jDesktopPane1.add(jLabel102);
				}

				{
					// jTextFieldUserNameApp = new JTextField();
					// jTextFieldUserNameApp.setFont(new Font("Dialog",
					// Font.PLAIN, 11));
					// jTextFieldUserNameApp.setEditable(false);
					// jTextFieldUserNameApp.setDisabledTextColor(Common.color_textdisabled);
					// jTextFieldUserNameApp.setBounds(520, 104, 231, 20);
					// jTextFieldUserNameApp.setText(Common.userList.getUser(Common.sessionID).getUserId());
					// jDesktopPane1.add(jTextFieldUserNameApp);
				}

				{
					jLabel102_1 = new JLabel();
					jLabel102_1.setHorizontalAlignment(SwingConstants.TRAILING);
					jLabel102_1.setFont(new Font("Dialog", Font.PLAIN, 11));
					jLabel102_1.setText("Session ID :");
					jLabel102_1.setBounds(383, 134, 118, 21);
					jDesktopPane1.add(jLabel102_1);
				}

				{
					jTextFieldUserSessionID = new JTextField();
					jTextFieldUserSessionID.setFont(new Font("Dialog", Font.PLAIN, 11));
					jTextFieldUserSessionID.setEditable(false);
					jTextFieldUserSessionID.setDisabledTextColor(Common.color_textdisabled);
					jTextFieldUserSessionID.setBounds(520, 134, 231, 20);
					jDesktopPane1.add(jTextFieldUserSessionID);
				}

				{
					jTextFieldUserHostID = new JTextField();
					jTextFieldUserHostID.setFont(new Font("Dialog", Font.PLAIN, 11));
					jTextFieldUserHostID.setEditable(false);
					jTextFieldUserHostID.setDisabledTextColor(Common.color_textdisabled);
					jTextFieldUserHostID.setBounds(435, 228, 32, 20);
					jDesktopPane1.add(jTextFieldUserHostID);
				}

				{
					jLabel102_2 = new JLabel();
					jLabel102_2.setHorizontalAlignment(SwingConstants.TRAILING);
					jLabel102_2.setFont(new Font("Dialog", Font.PLAIN, 11));
					jLabel102_2.setText("Host ID :");
					jLabel102_2.setBounds(383, 227, 51, 21);
					jDesktopPane1.add(jLabel102_2);
				}

				{
					jTextFieldUserHostDescription = new JTextField();
					jTextFieldUserHostDescription.setBounds(481, 228, 270, 20);
					jTextFieldUserHostDescription.setEditable(false);
					jDesktopPane1.add(jTextFieldUserHostDescription);
				}

				{
					jLabel102_3 = new JLabel();
					jLabel102_3.setHorizontalAlignment(SwingConstants.TRAILING);
					jLabel102_3.setFont(new Font("Dialog", Font.PLAIN, 11));
					jLabel102_3.setText("Unique Host Ref :");
					jLabel102_3.setBounds(383, 289, 95, 21);
					jDesktopPane1.add(jLabel102_3);
				}

				{
					jTextFieldHosyUniqueRef = new JTextField();
					jTextFieldHosyUniqueRef.setEditable(false);
					jTextFieldHosyUniqueRef.setBounds(481, 289, 270, 20);
					jDesktopPane1.add(jTextFieldHosyUniqueRef);
				}
			}
			this.setSize(771, 458);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

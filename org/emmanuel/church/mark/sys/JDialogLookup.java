package org.emmanuel.church.mark.sys;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.ListModel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;

import org.emmanuel.church.mark.db.JDBQuery;
import org.emmanuel.church.mark.db.JDBTable;
import org.emmanuel.church.mark.util.JUtility;

public class JDialogLookup extends javax.swing.JDialog {
	private static final long serialVersionUID = 1;
	private JDesktopPane jDesktopPane1;
	private JButton jButtonSelect;
	private JButton jButtonCancel;
	private JLabel jLabel2;
	private JList jListData;
	private JLabel jTextFieldHeading;
	private JScrollPane jScrollPane1;
	private JLabel jLabel3;
	private JLabel jLabel1;
	private JTextField jTextFieldCriteria;
	private JComboBox jComboBoxOrderBy;
	private JComboBox jComboBoxCriteria;
	public static String dlg_title;
	public static boolean dlg_selected = true;
	public static String dlg_selected_var;
	private JButton jButtonHelp;

	private static String dlg_criteria_field_name;
	private static int dlg_criteria_field_size;
	private static String dlg_criteria_field_type;
	public static String dlg_criteria_field_name_default;
	private static int dlg_criteria_field_name_default_pos;

	private static String dlg_orderBy_field_name;
	private static int dlg_orderBy_field_size;
	private static String dlg_orderBy_field_type;
	public static String dlg_orderBy_name_default;
	public static int dlg_orderBy_name_default_pos;

	public static boolean dlg_sort_descending = false;

	public static JDBTable dlg_table;

	public static String dlg_key_field_name;
	public static String dlg_key_field_type;
	public static int dlg_key_field_size;

	private String errorMessage;
	private String dataResult;

	private JButton jButtonSearch;
	private JToggleButton jToggleButtonSequence;
	private final JDialogLookup me;
	private Dimension screen;
	private Rectangle window;
	private final Dimension startupSize;

	private PreparedStatement buildSQL() {
		final String ad;
		JDBQuery query = new JDBQuery(Common.selectedHostID, Common.sessionID);

		query.clear();

		if (dlg_table.getTableName().toUpperCase().endsWith("SYS_USERS")) {
			query.addText("select user_id,user_comment from " + dlg_table.getTableName());
		} else {
			query.addText("select * from " + dlg_table.getTableName());
		}

		if (((String) jComboBoxCriteria.getSelectedItem()).equals("") == false) {
			if (jTextFieldCriteria.getText().equals("") == false) {
				String type = "";
				type = dlg_table.getColumnTypeForField((String) jComboBoxCriteria.getSelectedItem());

				if (type.equals("java.math.BigDecimal")) {
					query.addParamtoSQL((String) jComboBoxCriteria.getSelectedItem() + " = ",
							JUtility.stringToBigDecimal(jTextFieldCriteria.getText().toString()));
				}
				if (type.equals("java.lang.String")) {
					query.addParamtoSQL((String) jComboBoxCriteria.getSelectedItem() + " LIKE ", "%"
							+ jTextFieldCriteria.getText() + "%");
				}
				if (type.equals("java.sql.Timestamp")) {
					query.addParamtoSQL((String) jComboBoxCriteria.getSelectedItem() + " LIKE ", "%"
							+ jTextFieldCriteria.getText() + "%");
				}

			}
		}

		if (jToggleButtonSequence.isSelected()) {
			ad = "desc";
		} else {
			ad = "asc";
		}

		query.addText(" order by " + jComboBoxOrderBy.getSelectedItem() + " " + ad);

		query.bindParams();

		return query.getPreparedStatement();
	}

	public Vector<JLaunchLookup> getData(PreparedStatement criteria) {

		ResultSet rs;
		Vector<JLaunchLookup> result = new Vector<JLaunchLookup>();

		// if
		// (Common.hostList.getHost(Common.selectedHostID).toString().equals(null))
		// {
		// result.addElement(new JLaunchLookup(dlg_key_field_name,
		// dlg_criteria_field_name, dlg_orderBy_field_name));
		// } else {
		try {
			rs = criteria.executeQuery();

			dlg_key_field_type = dlg_table.getColumnTypeForField(dlg_key_field_name);
			dlg_criteria_field_type = dlg_table.getColumnTypeForField(dlg_criteria_field_name);
			dlg_orderBy_field_type = dlg_table.getColumnTypeForField(dlg_orderBy_field_name);

			while (rs.next()) {
				JLaunchLookup jl = new JLaunchLookup();
				getFieldValueAsString(rs, dlg_key_field_name, dlg_key_field_type);
				jl.dlgKeyField = rs.getString(dlg_key_field_name);
				getFieldValueAsString(rs, dlg_criteria_field_name, dlg_criteria_field_type);
				jl.dlgCriteriaField = rs.getString(dlg_criteria_field_name);
				getFieldValueAsString(rs, dlg_orderBy_field_name, dlg_orderBy_field_type);
				jl.dlgOrderField = rs.getString(dlg_orderBy_field_name);
				result.addElement(jl);
			}
			rs.close();

		} catch (Exception e) {
			setErrorMessage(e.getMessage());
		}
		// }

		return result;
	}

	private String getFieldValueAsString(ResultSet rs, String fieldname, String fieldtype) {
		String result = "";

		try {
			if (fieldtype.equals("java.lang.String")) {
				result = rs.getString(fieldname);
			}
			if (fieldtype.equals("java.sql.Timestamp")) {
				result = rs.getTimestamp(fieldname).toString();
			}
		} catch (Exception ex) {
			result = fieldname;
		}

		return result;
	}

	private void setErrorMessage(String errormessage) {
		errorMessage = errormessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	private void populateList(PreparedStatement criteria) {
		String heading = "";
		heading = JUtility.padString(dlg_key_field_name, true, dlg_key_field_size, " ") + " ";
		heading = heading + JUtility.padString(dlg_criteria_field_name, true, dlg_criteria_field_size, " ") + " ";
		heading = heading + dlg_orderBy_field_name;
		heading = heading.replace("_", " ");
		heading = JUtility.capitaliseAll(heading);
		jTextFieldHeading.setText(heading);

		DefaultComboBoxModel defComboBoxMod = new DefaultComboBoxModel();

		Vector<JLaunchLookup> tempDataList = getData(criteria);
		for (int j = 0; j < tempDataList.size(); j++) {
			dataResult = "";
			dataResult = JUtility.padString(tempDataList.get(j).dlgKeyField, true, dlg_key_field_size, " ") + " ";
			dataResult = dataResult
					+ JUtility.padString(tempDataList.get(j).dlgCriteriaField, true, dlg_criteria_field_size, " ")
					+ " ";
			if (j == 0) {
				int adjust = dlg_orderBy_field_size;
				if (adjust < dlg_orderBy_field_name.length()) {
					adjust = dlg_orderBy_field_name.length();
				}
				dataResult = dataResult
						+ JUtility.padString(JUtility.replaceNullObjectwithBlank(tempDataList.get(j).dlgOrderField),
								true, adjust, " ");
			} else {
				dataResult = dataResult + JUtility.replaceNullObjectwithBlank(tempDataList.get(j).dlgOrderField) + "  ";
			}

			defComboBoxMod.addElement(dataResult);
		}
		me.setSize(startupSize);
		ListModel jList1Model = defComboBoxMod;
		jListData.setModel(jList1Model);
		jListData.setSelectedIndex(0);
		// jListData.setCellRenderer(Common.renderer_list);

		me.setSize(startupSize);
	}

	private void search() {
		dlg_key_field_size = dlg_table.getColumnSizeForField(dlg_key_field_name);
		dlg_criteria_field_name = (String) jComboBoxCriteria.getSelectedItem();
		dlg_criteria_field_size = dlg_table.getColumnSizeForField(dlg_criteria_field_name);
		dlg_orderBy_field_name = (String) jComboBoxOrderBy.getSelectedItem();
		dlg_orderBy_field_size = dlg_table.getColumnSizeForField(dlg_orderBy_field_name);
		populateList(buildSQL());
		// growToAccomodate();
	}

	private void setSequence(boolean descending) {
		jToggleButtonSequence.setSelected(descending);
		if (jToggleButtonSequence.isSelected() == true) {
			jToggleButtonSequence.setToolTipText("Descending");
			jToggleButtonSequence.setIcon(Common.icon_descending);
		} else {
			jToggleButtonSequence.setToolTipText("Ascending");
			jToggleButtonSequence.setIcon(Common.icon_ascending);
		}
	}

	public JDialogLookup(JFrame frame) {
		super(frame);
		dlg_selected = false;
		setTitle(dlg_title);
		// self.setFrameIcon();

		dlg_criteria_field_name_default_pos = 0;
		for (int x = 0; x < dlg_table.getFieldNames().size(); x++) {
			if (dlg_table.getColumnNameForField(x).toLowerCase().equals(dlg_criteria_field_name_default.toLowerCase())) {
				dlg_criteria_field_name_default_pos = x;
			}
		}

		dlg_orderBy_name_default_pos = 0;
		for (int x = 0; x < dlg_table.getFieldNames().size(); x++) {
			if (dlg_table.getColumnNameForField(x).toLowerCase().equals(dlg_orderBy_name_default)) {
				dlg_orderBy_name_default_pos = x;
			}
		}

		initGUI();

		me = this;

		jTextFieldCriteria.setText(JLaunchLookup.dlgCriteriaDefault);

		screen = Toolkit.getDefaultToolkit().getScreenSize();
		window = getBounds();
		setLocation((screen.width - window.width) / 2, (screen.height - window.height) / 2);
		startupSize = me.getSize();

		this.setModal(true);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setMinimumSize(new java.awt.Dimension(471, 544));
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent evt) {
				dlg_selected_var = "";
				dlg_selected = false;
			}
		});
		this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent evt) {
				jScrollPane1.setSize(jDesktopPane1.getSize().width - 13, jDesktopPane1.getSize().height - 159);
				jTextFieldHeading.setSize(jDesktopPane1.getSize().width - 13, jTextFieldHeading.getSize().height);
				jScrollPane1.validate();

			}
		});

		// final JHelp help = new JHelp();
		// help.enableHelpOnButton(jButtonHelp,
		// JUtility.getHelpSetIDforModule("FRM_LOOKUP"));

		setSequence(dlg_sort_descending);

		if (JLaunchLookup.dlgAutoExec) {
			search();
		}
	}

	private void initGUI() {
		try {
			{
				jDesktopPane1 = new JDesktopPane();
				jDesktopPane1.setBackground(Color.WHITE);
				getContentPane().add(jDesktopPane1, BorderLayout.SOUTH);
				jDesktopPane1.setPreferredSize(new java.awt.Dimension(357, 518));
				jDesktopPane1.setBorder(BorderFactory.createTitledBorder(""));
				jDesktopPane1.setLayout(null);
				{
					jButtonSelect = new JButton(Common.icon_ok);
					jDesktopPane1.add(jButtonSelect);
					jButtonSelect.setText("Select");
					jButtonSelect.setBounds(126, 98, 105, 28);
					jButtonSelect.setFont(Common.font_btn);
					jButtonSelect.setMnemonic(java.awt.event.KeyEvent.VK_L);
					jButtonSelect.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							if (jListData.isSelectionEmpty() == false) {
								dlg_selected_var = ((String) jListData.getSelectedValue()).substring(0,
										dlg_table.getColumnSizeForField(dlg_key_field_name));
								dlg_selected = true;
								dispose();
							}
						}
					});
				}
				{
					jButtonCancel = new JButton(Common.icon_cancel);
					jDesktopPane1.add(jButtonCancel);
					jButtonCancel.setText("Cancel");
					jButtonCancel.setFont(Common.font_btn);
					jButtonCancel.setBounds(238, 98, 105, 28);
					jButtonCancel.setMnemonic(java.awt.event.KeyEvent.VK_C);
					jButtonCancel.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							dlg_selected = false;
							dlg_selected_var = "";
							dispose();
						}
					});
				}
				{
					jTextFieldCriteria = new JTextField();
					jDesktopPane1.add(jTextFieldCriteria);
					jTextFieldCriteria.setBounds(70, 35, 385, 21);
					jTextFieldCriteria.setFont(Common.font_std);
				}
				{
					ComboBoxModel jComboBox1Model = new DefaultComboBoxModel(dlg_table.getFieldNames());
					jComboBoxCriteria = new JComboBox();
					jDesktopPane1.add(jComboBoxCriteria);
					jComboBoxCriteria.setModel(jComboBox1Model);
					jComboBoxCriteria.setBounds(70, 7, 182, 23);
					jComboBoxCriteria.setFont(Common.font_combo);
					jComboBoxCriteria.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							jTextFieldCriteria.setText("");
						}
					});
					jComboBoxCriteria.setSelectedIndex(dlg_criteria_field_name_default_pos);
				}
				{
					ComboBoxModel jComboBox2Model = new DefaultComboBoxModel(dlg_table.getFieldNames());
					jComboBoxOrderBy = new JComboBox();
					jDesktopPane1.add(jComboBoxOrderBy);
					jComboBoxOrderBy.setModel(jComboBox2Model);
					jComboBoxOrderBy.setBounds(70, 63, 182, 23);
					jComboBoxOrderBy.setFont(Common.font_combo);
					jComboBoxOrderBy.setSelectedIndex(dlg_orderBy_name_default_pos);
				}

				{
					jLabel1 = new JLabel();
					jDesktopPane1.add(jLabel1);
					jLabel1.setText("Value :");
					jLabel1.setBounds(0, 35, 63, 21);
					jLabel1.setHorizontalAlignment(SwingConstants.TRAILING);
					jLabel1.setFont(Common.font_std);
				}
				{
					jLabel2 = new JLabel();
					jDesktopPane1.add(jLabel2);
					jLabel2.setText("Criteria :");
					jLabel2.setBounds(0, 7, 63, 21);
					jLabel2.setHorizontalAlignment(SwingConstants.TRAILING);
					jLabel2.setFont(Common.font_std);
				}
				{
					jLabel3 = new JLabel();
					jDesktopPane1.add(jLabel3);
					jLabel3.setText("Order By :");
					jLabel3.setBounds(0, 63, 63, 21);
					jLabel3.setHorizontalAlignment(SwingConstants.TRAILING);
					jLabel3.setFont(Common.font_std);
				}
				{
					jScrollPane1 = new JScrollPane();
					jDesktopPane1.add(jScrollPane1);
					jScrollPane1.setBounds(7, 154, 448, 350);
					jScrollPane1.getHorizontalScrollBar().addComponentListener(new ComponentAdapter() {
						@Override
						public void componentResized(ComponentEvent evt) {
							if (jScrollPane1.getHorizontalScrollBar().isVisible() == true) {
								me.setSize(me.getSize().width + 50, (me.getSize().height));
								screen = Toolkit.getDefaultToolkit().getScreenSize();
								window = getBounds();
								setLocation((screen.width - window.width) / 2, (screen.height - window.height) / 2);
								me.validate();
							}
						}
					});
					{
						// ListModel jList1Model = new DefaultComboBoxModel();
						//
						// jListData = new JList();
						// jScrollPane1.setViewportView(jListData);
						// jListData.setModel(jList1Model);
						// jListData.setCellRenderer(Common.renderer_list);
						// jListData.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
						// jListData.setFont(Common.font_list);
						// jListData.addMouseListener(new MouseAdapter() {
						// @Override
						// public void mouseClicked(MouseEvent evt) {
						// if (evt.getClickCount() == 2) {
						// jButtonSelect.doClick();
						// }
						// }
						// });
					}
				}
				{
					jToggleButtonSequence = new JToggleButton(Common.icon_ascending);
					jDesktopPane1.add(jToggleButtonSequence);
					jToggleButtonSequence.setBounds(259, 63, 21, 21);
					jToggleButtonSequence.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							setSequence(jToggleButtonSequence.isSelected());
						}
					});
				}
				{

					jButtonSearch = new JButton(Common.icon_search);
					jButtonSearch.setFont(Common.font_btn);
					jDesktopPane1.add(jButtonSearch);
					jButtonSearch.setText("Search");
					jButtonSearch.setBounds(14, 98, 105, 28);
					jButtonSearch.setMnemonic(java.awt.event.KeyEvent.VK_S);
					jButtonSearch.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							search();
							// autoSize();
						}
					});
				}
				{
					jTextFieldHeading = new JLabel();
					jDesktopPane1.add(jTextFieldHeading);
					jTextFieldHeading.setFont(Common.font_list);
					jTextFieldHeading.setBounds(7, 133, 448, 21);
					jTextFieldHeading.setHorizontalAlignment(SwingConstants.LEFT);
					jTextFieldHeading.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
				}
				{
					jButtonHelp = new JButton(Common.icon_help);
					jDesktopPane1.add(jButtonHelp);
					jButtonHelp.setText("Help");
					jButtonHelp.setMnemonic(java.awt.event.KeyEvent.VK_H);
					jButtonHelp.setFont(Common.font_btn);
					jButtonHelp.setBounds(350, 98, 105, 28);
				}
			}
			this.setSize(471, 534);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

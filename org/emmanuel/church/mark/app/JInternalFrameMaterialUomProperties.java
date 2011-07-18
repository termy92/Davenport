package org.emmanuel.church.mark.app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.AbstractDocument;

import org.emmanuel.church.mark.db.JDBLanguage;
import org.emmanuel.church.mark.db.JDBMaterialUom;
import org.emmanuel.church.mark.sys.Common;
import org.emmanuel.church.mark.util.JFixedSizeFilter;

public class JInternalFrameMaterialUomProperties extends JInternalFrame {
	private static final long serialVersionUID = 1;
	private JDesktopPane jDesktopPane1;
	private JButton jButtonCancel;
	private JLabel jLabel2;
	private JLabel jLabel4;
	private JSpinner jSpinnerNumerator;
	private JSpinner jSpinnerDenominator;
	private JTextField jTextFieldVariant;
	private JTextField jTextFieldEAN;
	private JTextField jTextFieldUOM;
	private JTextField jTextFieldMaterial;
	private JLabel jLabel6;
	private JLabel jLabel5;
	private JLabel jLabel3;
	private JLabel jLabel1;
	private JButton jButtonHelp;
	private JButton jButtonUpdate;
	private final SpinnerNumberModel numeratornumbermodel = new SpinnerNumberModel();
	private final SpinnerNumberModel denominatornumbermodel = new SpinnerNumberModel();
	private String lmaterial;
	private String luom;
	private final JDBMaterialUom materialuom = new JDBMaterialUom(Common.selectedHostID, Common.sessionID);
	private final JDBLanguage lang = new JDBLanguage(Common.selectedHostID, Common.sessionID);

	public JInternalFrameMaterialUomProperties() {
		super();
		initGUI();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				jTextFieldEAN.requestFocus();
				jTextFieldEAN.setCaretPosition(jTextFieldEAN.getText().length());
			}
		});
	}

	public JInternalFrameMaterialUomProperties(String material, String uom) {
		this();
		lmaterial = material;
		luom = uom;
		jTextFieldMaterial.setText(lmaterial);
		jTextFieldUOM.setText(luom);
		materialuom.setMaterial(lmaterial);
		materialuom.setUom(luom);
		materialuom.getMaterialUomProperties();
		jTextFieldEAN.setText(materialuom.getEan());
		jTextFieldVariant.setText(materialuom.getVariant());
		jSpinnerNumerator.setValue(materialuom.getNumerator());
		jSpinnerDenominator.setValue(materialuom.getDenominator());
	}

	private void initGUI() {
		try {
			this.setPreferredSize(new java.awt.Dimension(376, 234));
			this.setBounds(25, 25, 387 + Common.LFAdjustWidth, 249 + Common.LFAdjustHeight);
			setVisible(true);
			this.setIconifiable(true);
			this.setClosable(true);

			{
				jDesktopPane1 = new JDesktopPane();
				jDesktopPane1.setBackground(Color.WHITE);
				this.getContentPane().add(jDesktopPane1, BorderLayout.CENTER);
				jDesktopPane1.setPreferredSize(new java.awt.Dimension(390, 208));
				{
					jButtonUpdate = new JButton(Common.icon_update);
					jDesktopPane1.add(jButtonUpdate);
					jButtonUpdate.setEnabled(false);
					jButtonUpdate.setText(lang.get("btn_Save"));
					jButtonUpdate.setMnemonic(lang.getMnemonicChar());
					jButtonUpdate.setFont(Common.font_btn);
					jButtonUpdate.setPreferredSize(new java.awt.Dimension(90, 30));
					jButtonUpdate.setBounds(15, 164, 110, 30);
					jButtonUpdate.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {

							boolean result = true;

							materialuom.setMaterial(lmaterial);
							materialuom.setUom(luom);
							materialuom.setEan(jTextFieldEAN.getText());
							materialuom.setVariant(jTextFieldVariant.getText());
							materialuom.setNumerator((Integer) jSpinnerNumerator.getValue());
							materialuom.setDenominator((Integer) jSpinnerDenominator.getValue());

							if (materialuom.isValidMaterialUom() == false) {
								result = materialuom.create();
							} else {
								result = materialuom.update();
							}
							if (result == false) {
								JOptionPane.showMessageDialog(Common.mainForm, materialuom.getErrorMessage(), "Error",
										JOptionPane.ERROR_MESSAGE);
							}

							jButtonUpdate.setEnabled(false);
						}
					});
				}
				{
					jButtonHelp = new JButton(Common.icon_help);
					jDesktopPane1.add(jButtonHelp);
					jButtonHelp.setText(lang.get("btn_Help"));
					jButtonHelp.setMnemonic(lang.getMnemonicChar());
					jButtonHelp.setFont(Common.font_btn);
					jButtonHelp.setBounds(129, 164, 110, 30);
				}
				{
					jButtonCancel = new JButton(Common.icon_close);
					jDesktopPane1.add(jButtonCancel);
					jButtonCancel.setText(lang.get("btn_Close"));
					jButtonCancel.setMnemonic(lang.getMnemonicChar());
					jButtonCancel.setFont(Common.font_btn);
					jButtonCancel.setPreferredSize(new java.awt.Dimension(90, 30));
					jButtonCancel.setBounds(242, 164, 110, 30);
					jButtonCancel.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							dispose();
						}
					});
				}
				{
					jLabel1 = new JLabel();
					jDesktopPane1.add(jLabel1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
							GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					jLabel1.setText(lang.get("lbl_Material"));
					jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
					jLabel1.setHorizontalTextPosition(SwingConstants.RIGHT);
					jLabel1.setFont(Common.font_std);
					jLabel1.setBounds(11, 9, 147, 21);
				}
				{
					jLabel2 = new JLabel();
					jDesktopPane1.add(jLabel2, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
							GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					jLabel2.setText(lang.get("lbl_Material_UOM_EAN"));
					jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
					jLabel2.setHorizontalTextPosition(SwingConstants.RIGHT);
					jLabel2.setFont(Common.font_std);
					jLabel2.setBounds(11, 59, 147, 21);
				}
				{
					jLabel3 = new JLabel();
					jDesktopPane1.add(jLabel3, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
							GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					jLabel3.setText(lang.get("lbl_Material_UOM_Variant"));
					jLabel3.setHorizontalAlignment(SwingConstants.RIGHT);
					jLabel3.setHorizontalTextPosition(SwingConstants.RIGHT);
					jLabel3.setFont(Common.font_std);
					jLabel3.setBounds(11, 83, 147, 21);
				}
				{
					jLabel4 = new JLabel();
					jDesktopPane1.add(jLabel4, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
							GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					jLabel4.setText(lang.get("lbl_Material_UOM_Numerator"));
					jLabel4.setHorizontalAlignment(SwingConstants.RIGHT);
					jLabel4.setHorizontalTextPosition(SwingConstants.RIGHT);
					jLabel4.setFont(Common.font_std);
					jLabel4.setBounds(11, 107, 147, 21);
				}
				{
					jLabel5 = new JLabel();
					jDesktopPane1.add(jLabel5, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
							GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					jLabel5.setText(lang.get("lbl_Material_UOM_Denominator"));
					jLabel5.setHorizontalAlignment(SwingConstants.RIGHT);
					jLabel5.setHorizontalTextPosition(SwingConstants.RIGHT);
					jLabel5.setFont(Common.font_std);
					jLabel5.setBounds(11, 129, 147, 21);
				}
				{
					jLabel6 = new JLabel();
					jDesktopPane1.add(jLabel6, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
							GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					jLabel6.setText(lang.get("lbl_Material_UOM"));
					jLabel6.setHorizontalAlignment(SwingConstants.RIGHT);
					jLabel6.setHorizontalTextPosition(SwingConstants.RIGHT);
					jLabel6.setFont(Common.font_std);
					jLabel6.setBounds(11, 34, 147, 21);
				}
				{
					jTextFieldMaterial = new JTextField();
					jDesktopPane1.add(jTextFieldMaterial, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					jTextFieldMaterial.setFont(Common.font_std);
					jTextFieldMaterial.setHorizontalAlignment(SwingConstants.LEFT);
					jTextFieldMaterial.setEditable(false);
					jTextFieldMaterial.setPreferredSize(new java.awt.Dimension(100, 20));
					jTextFieldMaterial.setBounds(165, 9, 113, 21);
					jTextFieldMaterial.setEnabled(false);
					jTextFieldMaterial.setDisabledTextColor(Common.color_textdisabled);
				}
				{
					jTextFieldUOM = new JTextField();
					jDesktopPane1.add(jTextFieldUOM, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					jTextFieldUOM.setFont(Common.font_std);
					jTextFieldUOM.setHorizontalAlignment(SwingConstants.LEFT);
					jTextFieldUOM.setEditable(false);
					jTextFieldUOM.setPreferredSize(new java.awt.Dimension(100, 20));
					jTextFieldUOM.setBounds(165, 34, 60, 21);
					jTextFieldUOM.setEnabled(false);
					jTextFieldUOM.setDisabledTextColor(Common.color_textdisabled);
				}
				{
					jTextFieldEAN = new JTextField();
					AbstractDocument doc = (AbstractDocument) jTextFieldEAN.getDocument();
					doc.setDocumentFilter(new JFixedSizeFilter(JDBMaterialUom.field_ean));
					jDesktopPane1.add(jTextFieldEAN, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					jTextFieldEAN.setFont(Common.font_std);
					jTextFieldEAN.setPreferredSize(new java.awt.Dimension(40, 20));
					jTextFieldEAN.setFocusCycleRoot(true);
					jTextFieldEAN.setBounds(165, 59, 175, 21);
					jTextFieldEAN.addKeyListener(new KeyAdapter() {
						@Override
						public void keyTyped(KeyEvent evt) {
							jButtonUpdate.setEnabled(true);
						}
					});
				}
				{
					jTextFieldVariant = new JTextField();
					AbstractDocument doc = (AbstractDocument) jTextFieldVariant.getDocument();
					doc.setDocumentFilter(new JFixedSizeFilter(JDBMaterialUom.field_variant));
					jDesktopPane1.add(jTextFieldVariant, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
							GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					jTextFieldVariant.setFont(Common.font_std);
					jTextFieldVariant.setPreferredSize(new java.awt.Dimension(40, 20));
					jTextFieldVariant.setFocusCycleRoot(true);
					jTextFieldVariant.setBounds(165, 83, 34, 21);
					jTextFieldVariant.addKeyListener(new KeyAdapter() {
						@Override
						public void keyTyped(KeyEvent evt) {
							jButtonUpdate.setEnabled(true);
						}
					});
				}
				{

					jSpinnerDenominator = new JSpinner();
					jDesktopPane1.add(jSpinnerDenominator);
					jSpinnerDenominator.setModel(numeratornumbermodel);
					jSpinnerDenominator.setFont(Common.font_std);
					jSpinnerDenominator.setBounds(165, 129, 75, 21);
					jSpinnerDenominator.getEditor().setPreferredSize(new java.awt.Dimension(42, 17));
					jSpinnerDenominator.addChangeListener(new ChangeListener() {
						public void stateChanged(ChangeEvent evt) {
							jButtonUpdate.setEnabled(true);
						}
					});
				}
				{

					jSpinnerNumerator = new JSpinner();
					jDesktopPane1.add(jSpinnerNumerator);
					jSpinnerNumerator.setModel(denominatornumbermodel);
					jSpinnerNumerator.setFont(Common.font_std);
					jSpinnerNumerator.setBounds(165, 107, 75, 21);
					jSpinnerNumerator.addChangeListener(new ChangeListener() {
						public void stateChanged(ChangeEvent evt) {
							jButtonUpdate.setEnabled(true);
						}
					});
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

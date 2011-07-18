// $codepro.audit.disable numericLiterals

package org.emmanuel.church.mark.table;

import java.awt.Color;
import java.awt.Component;
import java.math.BigDecimal;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import org.emmanuel.church.mark.sys.Common;
import org.emmanuel.church.mark.util.JUtility;

/**
 */
public class JDBTableCellRenderer extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 1;
	private JCheckBox checkbox;
	public static final DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {

		super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		Color foreground, background;

		try {
			if (value.getClass().getName().equals("java.lang.Boolean")) {
				checkbox = new JCheckBox();
				checkbox.setSelected((Boolean) value);
				checkbox.setText("");
				checkbox.setSize(getWidth(), getHeight());
				checkbox.setHorizontalAlignment(JCheckBox.CENTER);
				if (isSelected) {
					checkbox.setBackground(Common.color_listHighlighted);
				} else {
					if (row % 2 == 0) {
						checkbox.setBackground(Common.color_tablerow1);
					} else {
						checkbox.setBackground(Common.color_tablerow2);
					}
				}
				return checkbox;
			}
		} catch (Exception ex) {

		}

		if (isSelected) {
			foreground = Common.color_listFontSelected;
			background = Common.color_listHighlighted;
		} else {
			if (row % 2 == 0) {
				foreground = Common.color_listFontStandard;
				background = Common.color_tablerow1;
			} else {
				foreground = Common.color_listFontStandard;
				background = Common.color_tablerow2;
			}
		}

		setForeground(foreground);
		setBackground(background);

		setHorizontalAlignment(JLabel.LEFT);

		try {
			if (value.getClass().equals(BigDecimal.class)) {
				setHorizontalAlignment(JLabel.RIGHT);
				this.setText(JUtility.bigDecimaltoString((BigDecimal) value));
			}
		} catch (Exception ex) {

		}

		return this;
	}
}
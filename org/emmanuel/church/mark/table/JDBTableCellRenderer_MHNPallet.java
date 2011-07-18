// $codepro.audit.disable numericLiterals

package org.emmanuel.church.mark.table;

import java.awt.Color;
import java.awt.Component;
import java.math.BigDecimal;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import org.emmanuel.church.mark.db.JDBMHNDecisions;
import org.emmanuel.church.mark.sys.Common;
import org.emmanuel.church.mark.util.JColorPair;
import org.emmanuel.church.mark.util.JUtility;

/**
 */
public class JDBTableCellRenderer_MHNPallet extends DefaultTableCellRenderer {
	private static final long serialVersionUID = 1;

	public static final DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();
	private HashMap<String, JColorPair> lookup = new HashMap<String, JColorPair>();
	private final JDBMHNDecisions db = new JDBMHNDecisions(Common.selectedHostID, Common.sessionID);

	public JDBTableCellRenderer_MHNPallet() {
		super();
		lookup = db.getDecisionColors();
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		Color foreground, background;

		if (isSelected) {
			foreground = Common.color_listFontSelected;
			background = Common.color_listHighlighted;
		} else {
			if (row % 2 == 0) {
				foreground = Common.color_listFontStandard;
				background = new Color(204, 255, 204);
			} else {
				foreground = Common.color_listFontStandard;
				background = Common.color_tablerow2;
			}
		}

		if (column == JDBMHNPalletTableModelProperties.Decision_Col) {
			if (lookup.containsKey(value)) {
				background = lookup.get(value).background;
				foreground = lookup.get(value).foreground;
			} else {
				background = Color.black;
				foreground = Color.white;
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
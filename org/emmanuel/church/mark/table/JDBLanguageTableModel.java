package org.emmanuel.church.mark.table;

import java.sql.ResultSet;
import java.util.HashMap;

import javax.swing.table.AbstractTableModel;

import org.emmanuel.church.mark.db.JDBLanguage;
import org.emmanuel.church.mark.sys.Common;

/**
 */
public class JDBLanguageTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1;
	public static final int resource_key_col = 0;
	public static final int language_id_col = 1;
	public static final int text_col = 2;
	public static final int mnemonic_col = 3;

	private final String[] mcolNames = { "Resource Key", "Language ID", "Text", "Mnemonic" };
	private ResultSet mResultSet;

	private int prowCount = -1;
	private final HashMap<Integer, JDBLanguage> cache = new HashMap<Integer, JDBLanguage>();

	public JDBLanguageTableModel() {

	}

	public JDBLanguageTableModel(ResultSet rs) {
		super();
		prowCount = -1;
		mResultSet = rs;
	}

	public int getColumnCount() {
		return mcolNames.length;
	}

	public int getRowCount() {
		try {
			if (prowCount <= 0) {
				mResultSet.last();
				prowCount = mResultSet.getRow();
				mResultSet.beforeFirst();
			}
			return prowCount;

		} catch (Exception e) {
			return 0;
		}
	}

	@Override
	public void setValueAt(Object value, int row, int col) {

	}

	@Override
	public String getColumnName(int col) {
		return mcolNames[col];
	}

	public Object getValueAt(int row, int col) {
		try {
			if (cache.containsKey(row) == false) {
				mResultSet.absolute(row + 1);
				final JDBLanguage prow = new JDBLanguage(Common.selectedHostID, Common.sessionID);
				prow.getPropertiesfromResultSet(mResultSet);
				cache.put(row, prow);
			}
			switch (col) {
			case resource_key_col:
				return cache.get(row).getKey();
			case language_id_col:
				return cache.get(row).getLanguage();
			case text_col:
				return cache.get(row).getText();
			case mnemonic_col:
				return cache.get(row).getMnemonicString();
			}
		} catch (Exception ex) {
			return "Error";
		}

		return new String();
	}
}

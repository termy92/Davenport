package org.emmanuel.church.mark.table;

import java.sql.ResultSet;
import java.util.HashMap;

import javax.swing.table.AbstractTableModel;

import org.emmanuel.church.mark.db.JDBControl;
import org.emmanuel.church.mark.db.JDBMaterialBatch;
import org.emmanuel.church.mark.sys.Common;

/**
 */
public class JDBMaterialBatchTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1;
	public static final int Material_Col = 0;
	public static final int Batch_Col = 1;
	public static final int Status_Col = 2;
	public static final int Expiry_Col = 3;

	// Names of the columns
	private final String[] mcolNames = { "Material", "Batch", "Status", "Expiry Date" };
	private final ResultSet mResultSet;
	private final JDBControl ctrl = new JDBControl(Common.selectedHostID, Common.sessionID);
	private int prowCount = -1;
	private String expiryMode = "";
	private String result = "";
	private final HashMap<Integer, JDBMaterialBatch> cache = new HashMap<Integer, JDBMaterialBatch>();

	public JDBMaterialBatchTableModel(ResultSet rs) {
		super();
		expiryMode = ctrl.getKeyValue("EXPIRY DATE MODE");
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
				final JDBMaterialBatch prow = new JDBMaterialBatch(Common.selectedHostID, Common.sessionID);
				prow.getPropertiesfromResultSet(mResultSet);
				cache.put(row, prow);
			}
			switch (col) {
			case Material_Col:
				return cache.get(row).getMaterial();
			case Batch_Col:
				return cache.get(row).getBatch();
			case Status_Col:
				return cache.get(row).getStatus();
			case Expiry_Col:
				if (expiryMode.equals("BATCH")) {
					try {
						result = cache.get(row).getExpiryDate().toString().substring(0, 16);
					} catch (Exception ex) {
						result = "";
					}
				} else {
					result = "";
				}
				return result;

			}
		} catch (Exception ex) {
			return "Error";
		}

		return new String();
	}
}

package org.emmanuel.church.mark.table;

import java.sql.ResultSet;
import java.util.HashMap;

import javax.swing.table.AbstractTableModel;

import org.emmanuel.church.mark.db.JDBPallet;
import org.emmanuel.church.mark.sys.Common;

/**
 */
public class JDBMHNPalletTableModelProperties extends AbstractTableModel {

	private static final long serialVersionUID = 1;
	public static final int SSCC_Col = 0;
	public static final int Material_Col = 1;
	public static final int Batch_Col = 2;
	public static final int Process_Order_Col = 3;
	public static final int Quantity_Col = 4;
	public static final int Uom_Col = 5;
	public static final int Date_of_Manufacture_Col = 6;
	public static final int SSCC_Status_Col = 7;
	public static final int Batch_Status_Col = 8;
	public static final int Location_Col = 9;
	public static final int Decision_Col = 10;
	private final String[] mcolNames = { "SSCC", "Material", "Batch", "Process Order", "Quantity", "UOM",
			"Date of Manufacture", "SSCC Status", "Batch Status", "Location", "Decision" };
	private ResultSet mResultSet;

	private int prowCount = -1;
	private final HashMap<Integer, JDBPallet> cache = new HashMap<Integer, JDBPallet>();

	public JDBMHNPalletTableModelProperties() {

	}

	public JDBMHNPalletTableModelProperties(ResultSet rs) {
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
				final JDBPallet prow = new JDBPallet(Common.selectedHostID, Common.sessionID);
				prow.getPropertiesfromResultSet(mResultSet);
				cache.put(row, prow);
			}
			switch (col) {
			case SSCC_Col:
				return cache.get(row).getSSCC();
			case Material_Col:
				return cache.get(row).getMaterial();
			case Batch_Col:
				return cache.get(row).getBatchNumber();
			case Process_Order_Col:
				return cache.get(row).getProcessOrder();
			case Quantity_Col:
				return cache.get(row).getQuantity();
			case Uom_Col:
				return cache.get(row).getUom();
			case Date_of_Manufacture_Col:
				String result = "";
				try {
					result = cache.get(row).getDateOfManufacture().toString().substring(0, 16);
				} catch (Exception ex) {
					result = "";
				}
				return result;
			case SSCC_Status_Col:
				return cache.get(row).getStatus();
			case Batch_Status_Col:
				return cache.get(row).getMaterialBatchStatus();
			case Location_Col:
				return cache.get(row).getLocationID();
			case Decision_Col:
				return cache.get(row).getDecision();
			}

		} catch (Exception ex) {
			return "Error";
		}

		return new String();
	}
}

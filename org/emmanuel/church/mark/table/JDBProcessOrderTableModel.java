package org.emmanuel.church.mark.table;

import java.sql.ResultSet;
import java.util.HashMap;

import javax.swing.table.AbstractTableModel;

import org.emmanuel.church.mark.db.JDBProcessOrder;
import org.emmanuel.church.mark.sys.Common;

public class JDBProcessOrderTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1;
	public static final int Process_Order_Col = 0;
	public static final int Process_Order_Material_Col = 1;
	public static final int Process_Order_Description_Col = 2;
	public static final int Process_Order_Status_Col = 3;
	public static final int Process_Order_Location_Col = 4;
	public static final int Process_Order_Due_Date_Col = 5;
	public static final int Process_Order_Recipe_Col = 6;
	public static final int Process_Order_Required_Quantity_Col = 7;
	public static final int Process_Order_Required_Uom_Col = 8;
	public static final int Process_Order_DefaultBatchStatus_Col = 9;
	public static final int Process_Order_Required_Resource_Col = 10;

	private final String[] mcolNames = { "Process Order", "Material", "Description", "Status", "Location ID",
			"Due Date", "Recipe ID", "Quantity", "Uom", "Default Pallet Status", "Resource" };
	private ResultSet mResultSet;
	private int prowCount = -1;
	private final HashMap<Integer, JDBProcessOrder> cache = new HashMap<Integer, JDBProcessOrder>();

	public JDBProcessOrderTableModel() {

	}

	public JDBProcessOrderTableModel(ResultSet rs) {
		super();
		prowCount = -1;
		mResultSet = rs;
	}

	public int getColumnCount() {
		return mcolNames.length;
	}

	@Override
	public String getColumnName(int col) {
		return mcolNames[col];
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

	public Object getValueAt(int row, int col) {

		try {
			if (cache.containsKey(row) == false) {
				mResultSet.absolute(row + 1);
				final JDBProcessOrder prow = new JDBProcessOrder(Common.selectedHostID, Common.sessionID);
				prow.getPropertiesfromResultSet(mResultSet);
				cache.put(row, prow);
			}

			switch (col) {
			case Process_Order_Col:
				return cache.get(row).getProcessOrder();
			case Process_Order_Material_Col:
				return cache.get(row).getMaterial();
			case Process_Order_Description_Col:
				return cache.get(row).getDescription();
			case Process_Order_Status_Col:
				return cache.get(row).getStatus();
			case Process_Order_Location_Col:
				return cache.get(row).getLocation();
			case Process_Order_Due_Date_Col:
				return cache.get(row).getDueDate().toString().substring(0, 16);
			case Process_Order_Required_Quantity_Col:
				return cache.get(row).getRequiredQuantity();
			case Process_Order_Required_Uom_Col:
				return cache.get(row).getRequiredUom();
			case Process_Order_Recipe_Col:
				return cache.get(row).getRecipe();
			case Process_Order_DefaultBatchStatus_Col:
				return cache.get(row).getDefaultPalletStatus();
			case Process_Order_Required_Resource_Col:
				return cache.get(row).getRequiredResource();
			}
		} catch (Exception ex) {
			return "Error";
		}

		return new String();
	}

	@Override
	public void setValueAt(Object value, int row, int col) {

	}
}

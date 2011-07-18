package org.emmanuel.church.mark.table;

import java.sql.ResultSet;
import java.util.HashMap;

import javax.swing.table.AbstractTableModel;

import org.emmanuel.church.mark.db.JDBMaterial;
import org.emmanuel.church.mark.sys.Common;

/**
 */
public class JDBMaterialTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1;
	public static final int Material_Col = 0;
	public static final int Material_Description_Col = 1;
	public static final int Material_Type_Col = 2;
	public static final int Material_Base_Uom_Col = 3;
	public static final int Material_Shelf_Life_Col = 4;
	public static final int Material_Shelf_Life_Uom_Col = 5;
	public static final int Material_Shelf_Life_Rule_Col = 6;

	// Names of the columns
	private final String[] mcolNames = { "Material", "Description", "Type", "Base UOM", "Shelf Life", "Life UOM",
			"Rule" };

	// store the data
	private ResultSet mResultSet;
	private int prowCount = -1;
	private final HashMap<Integer, JDBMaterial> cache = new HashMap<Integer, JDBMaterial>();

	public JDBMaterialTableModel() {

	}

	public JDBMaterialTableModel(ResultSet rs) {
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
				final JDBMaterial prow = new JDBMaterial(Common.selectedHostID, Common.sessionID);
				prow.getPropertiesfromResultSet(mResultSet);
				cache.put(row, prow);
			}

			switch (col) {
			case Material_Col:
				return cache.get(row).getMaterial();
			case Material_Description_Col:
				return cache.get(row).getDescription();
			case Material_Type_Col:
				return cache.get(row).getMaterialType();
			case Material_Base_Uom_Col:
				return cache.get(row).getBaseUom();
			case Material_Shelf_Life_Col:
				return cache.get(row).getShelfLife();
			case Material_Shelf_Life_Uom_Col:
				return cache.get(row).getShelfLifeUom();
			case Material_Shelf_Life_Rule_Col:
				return cache.get(row).getShelfLifeRule();

			}

		} catch (Exception ex) {
			return "Error";
		}

		return new String();
	}
}

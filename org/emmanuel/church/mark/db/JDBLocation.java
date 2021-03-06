// $codepro.audit.disable numericLiterals
/*
 * Created on 30-Jul-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package org.emmanuel.church.mark.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Vector;

import org.emmanuel.church.mark.util.JUtility;

public class JDBLocation {
	private String dbDescription;
	private String dbErrorMessage;
	private String dbGLN;
	private String dbLocationId;
	private String dbPlant;
	private String dbStorageBin;
	private String dbStorageLocation;
	private String db_msg_despatch_confirm = "";
	private String db_msg_status_change = "";
	private String db_msg_despatch_preadvice = "";
	private String db_msg_despatch_equip_track = "";
	private String db_msg_prod_confirm = "";
	private String dbStorageSection;
	private String dbStorageType;
	private String dbWarehouse;
	private String dbEquipmentTrackingID;
	private String dbPermittedPalletStatus = "";
	private String dbPermittedBatchStatus = "";

	public static int field_location_id = 15;
	public static int field_plant = 10;
	public static int field_warehouse = 10;
	public static int field_gln = 14;
	public static int field_description = 45;
	public static int field_storage_location = 10;
	public static int field_storage_type = 10;
	public static int field_storage_section = 10;
	public static int field_storage_bin = 10;
	public static int field_equipment_tracking_id = 15;
	private String hostID;
	private String sessionID;

	public JDBLocation(ResultSet rs) {
		getPropertiesfromResultSet(rs);
	}

	public JDBLocation(String host, String session) {
		// Get number of days that a password lasts before it needs changing.
		setHostID(host);
		setSessionID(session);
		setLocationID("");
		clear();
	}

	public JDBLocation(String locationId, String plant, String warehouse, String gln, String description,
			String location, String type, String section, String bin, String equipmenttrackingid) {
		setLocationID(locationId);
		setPlant(plant);
		setWarehouse(warehouse);
		setGLN(gln);
		setDescription(description);
		setStorageLocation(location);
		setStorageType(type);
		setStorageSection(section);
		setStorageBin(bin);
		setEquipmentTrackingID(equipmenttrackingid);
	}

	public void clear() {
		setPlant("");
		setWarehouse("");
		setGLN("");
		setDescription("");
		setStorageLocation("");
		setStorageType("");
		setStorageSection("");
		setStorageBin("");
		setMsgDespatchConfirm("N");
		setMsgDespatchPreadvice("N");
		setMsgDespatchEquipTrack("N");
		setMsgProdConfirm("N");
		setPermittedPalletStatus("");
		setPermittedBatchStatus("");
	}

	public Boolean isPalletStatusValidforLocation(String status) {
		Boolean result = false;

		result = getPermittedPalletStatus().contains(status);

		return result;
	}

	public Boolean isBatchStatusValidforLocation(String status) {
		Boolean result = false;

		result = getPermittedBatchStatus().contains(status);

		return result;
	}

	public String getPermittedPalletStatus() {
		return JUtility.replaceNullStringwithBlank(dbPermittedPalletStatus);
	}

	public void setPermittedPalletStatus(String dbPermittedPalletStatus) {
		this.dbPermittedPalletStatus = dbPermittedPalletStatus;
	}

	public String getPermittedBatchStatus() {
		return JUtility.replaceNullStringwithBlank(dbPermittedBatchStatus);
	}

	public void setPermittedBatchStatus(String dbPermittedBatchStatus) {
		this.dbPermittedBatchStatus = dbPermittedBatchStatus;
	}

	public boolean create() {

		boolean result = false;

		if (isValid() == true) {

			// try {
			// PreparedStatement stmtupdate;
			// stmtupdate = Common.hostList
			// .getHost(getHostID())
			// .getConnection(getSessionID())
			// .prepareStatement(
			// Common.hostList.getHost(getHostID()).getSqlstatements().getSQL("JDBLocation.create"));
			// stmtupdate.setString(1, getLocationID());
			// stmtupdate.execute();
			// stmtupdate.clearParameters();
			// Common.hostList.getHost(getHostID()).getConnection(getSessionID()).commit();
			// update();
			// stmtupdate.close();
			// result = true;
			// } catch (SQLException e) {
			// setErrorMessage(e.getMessage());
			// }
		}

		return result;
	}

	public boolean create(String locationid) {
		boolean result = false;
		setLocationID(locationid);
		result = create();
		return result;
	}

	public boolean delete() {
		PreparedStatement stmtupdate;
		boolean result = false;
		setErrorMessage("");

		// try {
		// stmtupdate = Common.hostList
		// .getHost(getHostID())
		// .getConnection(getSessionID())
		// .prepareStatement(
		// Common.hostList.getHost(getHostID()).getSqlstatements().getSQL("JDBLocation.delete"));
		// stmtupdate.setString(1, getLocationID());
		// stmtupdate.execute();
		// stmtupdate.clearParameters();
		// Common.hostList.getHost(getHostID()).getConnection(getSessionID()).commit();
		//
		// stmtupdate.close();
		// result = true;
		//
		// } catch (SQLException e) {
		// setErrorMessage(e.getMessage());
		// }

		return result;
	}

	public boolean delete(String locationid) {
		boolean result = false;
		setLocationID(locationid);
		result = delete();
		return result;
	}

	public String getDescription() {
		return dbDescription;
	}

	public String getEquipmentTrackingID() {
		return dbEquipmentTrackingID;
	}

	public String getErrorMessage() {
		return dbErrorMessage;
	}

	public String getGLN() {
		return dbGLN;
	}

	private String getHostID() {
		return hostID;
	}

	public String getHTMLPullDownCombo(String itemName, String defaultValue) {
		String result = "";
		String selected = "";
		LinkedList<JDBLocation> locationList = new LinkedList<JDBLocation>();
		locationList.addAll(getLocationList());
		result = "<SELECT width=\"100%\" style=\"width: 100%\" ID=\"" + itemName + "\" NAME=\"" + itemName + "\">";
		result = result + "<OPTION>";
		if (locationList.size() > 0) {
			for (int x = 0; x < locationList.size(); x++) {
				if (locationList.get(x).getLocationID().equals(defaultValue)) {
					selected = " SELECTED";
				} else {
					selected = "";
				}
				result = result + "<OPTION" + selected + ">" + locationList.get(x).getLocationID();
			}
		}
		result = result + "</SELECT>";

		return result;
	}

	public Vector<JDBLocation> getLocationData(PreparedStatement criteria) {

		ResultSet rs;
		Vector<JDBLocation> result = new Vector<JDBLocation>();
		//
		// if (Common.hostList.getHost(getHostID()).toString().equals(null)) {
		// result.addElement(new JDBLocation("location_id", "plant",
		// "warehouse", "gln", "description", "location",
		// "type", "section", "bin", "equipment"));
		// } else {
		try {
			rs = criteria.executeQuery();

			while (rs.next()) {
				result.addElement(new JDBLocation(rs.getString("location_id"), rs.getString("plant"), rs
						.getString("warehouse"), rs.getString("gln"), rs.getString("description"), rs
						.getString("storage_location"), rs.getString("storage_type"), rs.getString("storage_section"),
						rs.getString("storage_bin"), rs.getString("equipment_tracking_id")));
			}
			rs.close();

		} catch (Exception e) {
			setErrorMessage(e.getMessage());
		}
		// }

		return result;
	}

	public ResultSet getLocationDataResultSet(PreparedStatement criteria) {

		ResultSet rs;

		try {
			rs = criteria.executeQuery();

		} catch (Exception e) {
			rs = null;
			setErrorMessage(e.getMessage());
		}

		return rs;
	}

	public String getLocationID() {
		return dbLocationId;
	}

	public LinkedList<JDBLocation> getLocationList() {
		LinkedList<JDBLocation> locationList = new LinkedList<JDBLocation>();
		PreparedStatement stmt;
		ResultSet rs;
		setErrorMessage("");

		// try {
		// stmt = Common.hostList
		// .getHost(getHostID())
		// .getConnection(getSessionID())
		// .prepareStatement(
		// Common.hostList.getHost(getHostID()).getSqlstatements()
		// .getSQL("JDBLocation.getLocationList"));
		// stmt.setFetchSize(25);
		// rs = stmt.executeQuery();
		//
		// while (rs.next()) {
		// JDBLocation location = new JDBLocation(getHostID(), getSessionID());
		// location.getPropertiesfromResultSet(rs);
		// locationList.add(location);
		// }
		// rs.close();
		// stmt.close();
		//
		// } catch (SQLException e) {
		// setErrorMessage(e.getMessage());
		// }

		return locationList;
	}

	public boolean getLocationProperties() {
		boolean result = false;

		PreparedStatement stmt;
		ResultSet rs;
		setErrorMessage("");

		clear();

		// try {
		// stmt = Common.hostList
		// .getHost(getHostID())
		// .getConnection(getSessionID())
		// .prepareStatement(
		// Common.hostList.getHost(getHostID()).getSqlstatements()
		// .getSQL("JDBLocation.getLocationProperties"));
		// stmt.setFetchSize(1);
		// stmt.setString(1, getLocationID());
		// rs = stmt.executeQuery();
		//
		// if (rs.next()) {
		// getPropertiesfromResultSet(rs);
		// result = true;
		// } else {
		// setErrorMessage("Invalid Location ID [" + getLocationID() + "]");
		// }
		// rs.close();
		// stmt.close();
		// } catch (SQLException e) {
		// setErrorMessage(e.getMessage());
		// }
		return result;
	}

	public boolean getLocationProperties(String loc) {
		setLocationID(loc);
		return getLocationProperties();
	}

	public String getMsgDespatchConfirm() {
		return JUtility.replaceNullStringwithBlank(db_msg_despatch_confirm);
	}

	public String getMsgStatusChange() {
		return JUtility.replaceNullStringwithBlank(db_msg_status_change);
	}

	public String getMsgDespatchEquipTrack() {
		return JUtility.replaceNullStringwithBlank(db_msg_despatch_equip_track);
	}

	public String getMsgDespatchPreAdvice() {
		return JUtility.replaceNullStringwithBlank(db_msg_despatch_preadvice);
	}

	public String getMsgProdConfirm() {
		return JUtility.replaceNullStringwithBlank(db_msg_prod_confirm);
	}

	public String getPlant() {
		return dbPlant;
	}

	public void getPropertiesfromResultSet(ResultSet rs) {
		try {
			clear();
			setLocationID(rs.getString("location_id"));
			setPlant(rs.getString("plant"));
			setWarehouse(rs.getString("warehouse"));
			setGLN(rs.getString("GLN"));
			setDescription(rs.getString("description"));
			setStorageLocation(rs.getString("storage_location"));
			setStorageType(rs.getString("storage_type"));
			setStorageSection(rs.getString("storage_section"));
			setStorageBin(rs.getString("storage_bin"));
			setEquipmentTrackingID(rs.getString("equipment_tracking_id"));
			setMsgDespatchConfirm(rs.getString("msg_despatch_confirm"));
			setMsgStatusChange(rs.getString("msg_pallet_status"));
			setMsgDespatchPreadvice(rs.getString("msg_despatch_preadvice"));
			setMsgDespatchEquipTrack(rs.getString("msg_despatch_equip_track"));
			setMsgProdConfirm(rs.getString("msg_prod_confirm"));
			setPermittedPalletStatus(rs.getString("permitted_pallet_status"));
			setPermittedBatchStatus(rs.getString("permitted_batch_status"));

		} catch (SQLException e) {
			setErrorMessage(e.getMessage());
		}
	}

	private String getSessionID() {
		return sessionID;
	}

	public String getStorageBin() {
		return dbStorageBin;
	}

	public String getStorageLocation() {
		return dbStorageLocation;
	}

	public String getStorageSection() {
		return dbStorageSection;
	}

	public String getStorageType() {
		return dbStorageType;
	}

	public String getWarehouse() {

		if (dbWarehouse == null) {
			dbWarehouse = "*";
		}

		if (dbWarehouse.equals("")) {
			dbWarehouse = "*";
		}
		return dbWarehouse;
	}

	public Boolean isDespatchConfirmationMessageRequired() {
		Boolean result = false;
		if (getMsgDespatchConfirm().equals("Y")) {
			result = true;
		}
		return result;
	}

	public Boolean isStatusChangeMessageRequired() {
		Boolean result = false;
		if (getMsgStatusChange().equals("Y")) {
			result = true;
		}
		return result;
	}

	public Boolean isDespatchEquipmentTrackingMessageRequired() {
		Boolean result = false;
		if (getMsgDespatchEquipTrack().equals("Y")) {
			result = true;
		}
		return result;
	}

	public Boolean isDespatchPreAdviceMessageRequired() {
		Boolean result = false;
		if (getMsgDespatchPreAdvice().equals("Y")) {
			result = true;
		}
		return result;
	}

	public Boolean isProductionConfirmationMessageRequired() {
		Boolean result = false;
		if (getMsgProdConfirm().equals("Y")) {
			result = true;
		}
		return result;
	}

	public boolean isValid() {
		boolean result = true;

		if (JUtility.isNullORBlank(dbLocationId) == true) {
			setErrorMessage("LOCATION ID cannot be null");
			result = false;
		}

		return result;
	}

	public boolean isValidLocation() {

		PreparedStatement stmt;
		ResultSet rs;
		boolean result = false;

		// try {
		// stmt = Common.hostList
		// .getHost(getHostID())
		// .getConnection(getSessionID())
		// .prepareStatement(
		// Common.hostList.getHost(getHostID()).getSqlstatements()
		// .getSQL("JDBLocation.isValidLocation"));
		// stmt.setString(1, getLocationID());
		// stmt.setFetchSize(1);
		// rs = stmt.executeQuery();
		//
		// if (rs.next()) {
		// result = true;
		// } else {
		// setErrorMessage("Invalid Location [" + getLocationID() + "]");
		// }
		// rs.close();
		// stmt.close();
		// } catch (SQLException e) {
		// setErrorMessage(e.getMessage());
		// }

		return result;

	}

	public boolean isValidLocation(String location) {
		setLocationID(location);
		return isValidLocation();
	}

	public void setDescription(String description) {
		dbDescription = description;
	}

	public void setEquipmentTrackingID(String equipmenttrackingid) {
		dbEquipmentTrackingID = equipmenttrackingid;
	}

	private void setErrorMessage(String errorMsg) {

		dbErrorMessage = errorMsg;
	}

	public void setGLN(String gln) {
		dbGLN = gln;
	}

	private void setHostID(String host) {
		hostID = host;
	}

	public void setLocationID(String locationid) {
		dbLocationId = locationid;
	}

	public void setMsgDespatchConfirm(String dbMsgDespatchConfirm) {
		db_msg_despatch_confirm = dbMsgDespatchConfirm;
	}

	public void setMsgStatusChange(String dbMsgStatusChange) {
		db_msg_status_change = dbMsgStatusChange;
	}

	public void setMsgDespatchConfirm(Boolean dbMsgDespatchConfirm) {
		if (dbMsgDespatchConfirm) {
			db_msg_despatch_confirm = "Y";
		} else {
			db_msg_despatch_confirm = "N";
		}
	}

	public void setMsgStatusChange(Boolean dbMsgStatusChange) {
		if (dbMsgStatusChange) {
			db_msg_status_change = "Y";
		} else {
			db_msg_status_change = "N";
		}
	}

	public void setMsgDespatchEquipTrack(String dbMsgDespatchEquipTrack) {
		db_msg_despatch_equip_track = dbMsgDespatchEquipTrack;
	}

	public void setMsgDespatchEquipTrack(Boolean dbMsgDespatchEquipTrack) {
		if (dbMsgDespatchEquipTrack) {
			db_msg_despatch_equip_track = "Y";
		} else {
			db_msg_despatch_equip_track = "N";
		}

	}

	public void setMsgDespatchPreadvice(String dbMsgDespatchPreadvice) {
		db_msg_despatch_preadvice = dbMsgDespatchPreadvice;
	}

	public void setMsgDespatchPreadvice(Boolean dbMsgDespatchPreadvice) {
		if (dbMsgDespatchPreadvice) {
			db_msg_despatch_preadvice = "Y";
		} else {
			db_msg_despatch_preadvice = "N";
		}

	}

	public void setMsgProdConfirm(String dbMsgProdConfirm) {
		db_msg_prod_confirm = dbMsgProdConfirm;
	}

	public void setMsgProdConfirm(Boolean dbMsgProdConfirm) {
		if (dbMsgProdConfirm) {
			db_msg_prod_confirm = "Y";
		} else {
			db_msg_prod_confirm = "N";
		}

	}

	public void setPlant(String plant) {
		dbPlant = plant;
	}

	private void setSessionID(String session) {
		sessionID = session;
	}

	public void setStorageBin(String storageBin) {
		dbStorageBin = storageBin;
	}

	public void setStorageLocation(String storageLocation) {
		dbStorageLocation = storageLocation;
	}

	public void setStorageSection(String storageSection) {
		dbStorageSection = storageSection;
	}

	public void setStorageType(String storageType) {
		dbStorageType = storageType;
	}

	public void setWarehouse(String warehouse) {

		dbWarehouse = warehouse;

		if (dbWarehouse == null) {
			dbWarehouse = "*";
		}

		if (dbWarehouse.equals("")) {
			dbWarehouse = "*";
		}

	}

	public boolean update() {
		boolean result = false;

		// if (isValid() == true) {
		// try {
		// PreparedStatement stmtupdate;
		// stmtupdate = Common.hostList
		// .getHost(getHostID())
		// .getConnection(getSessionID())
		// .prepareStatement(
		// Common.hostList.getHost(getHostID()).getSqlstatements().getSQL("JDBLocation.update"));
		//
		// stmtupdate.setString(1, getPlant());
		// stmtupdate.setString(2, getWarehouse());
		// stmtupdate.setString(3, getGLN());
		// stmtupdate.setString(4, getDescription());
		// stmtupdate.setString(5, getStorageLocation());
		// stmtupdate.setString(6, getStorageType());
		// stmtupdate.setString(7, getStorageSection());
		// stmtupdate.setString(8, getStorageBin());
		// stmtupdate.setString(9, getEquipmentTrackingID());
		//
		// stmtupdate.setString(10, getMsgDespatchConfirm());
		// stmtupdate.setString(11, getMsgDespatchPreAdvice());
		// stmtupdate.setString(12, getMsgDespatchEquipTrack());
		// stmtupdate.setString(13, getMsgProdConfirm());
		// stmtupdate.setString(14, getPermittedPalletStatus());
		// stmtupdate.setString(15, getPermittedBatchStatus());
		// stmtupdate.setString(16, getMsgStatusChange());
		//
		// stmtupdate.setString(17, getLocationID());
		// stmtupdate.execute();
		//
		// stmtupdate.clearParameters();
		// Common.hostList.getHost(getHostID()).getConnection(getSessionID()).commit();
		// stmtupdate.close();
		// result = true;
		//
		// } catch (SQLException e) {
		// setErrorMessage(e.getMessage());
		// }
		// }

		return result;
	}

}

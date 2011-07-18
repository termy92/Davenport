package org.emmanuel.church.mark.db;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.LinkedList;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.emmanuel.church.mark.util.JUtility;

public class JDBPallet {
	public static int field_sscc = 18;
	private String dbBatchNumber;
	private Timestamp dbDateOfManufacture;
	private String dbEAN;
	private String dbErrorMessage;
	private String dbLocationId;
	private String dbMaterial;
	private int dbLayers;
	private String dbProcessOrder;
	private BigDecimal dbQuantity;
	private BigDecimal dbBaseQuantity;
	private String dbSSCC;
	private String dbStatus;
	private String dbCustomerID;
	private String dbMHNNumber;
	private String dbDecision;
	private String dbUom;
	private String dbVariant;
	private String dbDespatchNo;
	private Boolean dbConfirmed;
	private final Logger logger = Logger.getLogger(JDBPallet.class);
	private JDBMaterialBatch matBatch;
	private JDBMaterial material;
	private JDBMaterialUom matUom;
	private JDBProcessOrder processOrder;
	private JDBLocation location;
	private JDBControl ctrl;
	private JDBControl tempCont;
	private String hostID;
	private String sessionID;
	private JDBMaterialUom materialuom;
	private String lastMaterialUomMaterial = "";
	private String lastMaterialUomUom = "";
	private String lastMaterialMaterial = "";
	// private OutgoingProductionDeclarationConfirmation opdc;
	private long transactionRef = 0;
	private Timestamp dbBatchExpiry;
	private String expiryMode = "";

	public JDBPallet(String host, String session) {
		setHostID(host);
		setSessionID(session);
		initObjects();
		expiryMode = ctrl.getKeyValue("EXPIRY DATE MODE");
		clear();
	}

	public JDBPallet(String sscc, String material, String batch, String processOrder, BigDecimal quantity, String uom,
			BigDecimal baseQuantity, String baseUom, Timestamp dom, String status, String location, String ean,
			String variant) {
		clear();
		setSSCC(sscc);
		setBatchNumber(batch);
		setProcessOrder(processOrder);
		setMaterial(material);
		setQuantity(quantity);
		setUom(uom);
		setEAN(ean);
		setVariant(variant);
		setStatus(status);
		setDateOfManufacture(dom);
		setLocationID(location);
	}

	private Boolean autoCreateMaterialBatch() {

		boolean result = matBatch.autoCreateMaterialBatch(getMaterial(), getBatchNumber(), getBatchExpiry(), "");

		setErrorMessage("");
		if (result == false) {
			setErrorMessage(matBatch.getErrorMessage());
		}

		return result;

	}

	public BigDecimal calcBaseUOMQuantity(String fromUom, BigDecimal quantity) {
		BigDecimal result = new BigDecimal(0);

		try {
			result = quantity;

			if ((lastMaterialUomMaterial.equals(getMaterial()) == false)
					|| (lastMaterialUomUom.equals(fromUom) == false)) {
				materialuom.getMaterialUomProperties(getMaterial(), fromUom);
				lastMaterialUomMaterial = getMaterial();
				lastMaterialUomUom = fromUom;
			}

			materialuom.getDenominator();
			result = result.multiply(BigDecimal.valueOf(materialuom.getNumerator()));
			result = result.divide(BigDecimal.valueOf(materialuom.getDenominator()));
		} catch (Exception e) {
			result = new BigDecimal(0);
		}

		calcLayersOnPallet(result);

		return result;
	}

	public int calcLayersOnPallet(BigDecimal baseQty) {
		int result = 0;
		BigDecimal temp;
		String LayerUOM = getQuantityPerLayerUOM();
		JDBMaterialUom matuom = new JDBMaterialUom(getHostID(), getSessionID());

		if (matuom.getMaterialUomProperties(getMaterial(), LayerUOM) == true) {
			BigDecimal denom = BigDecimal.valueOf(matuom.getDenominator());
			BigDecimal numerator = BigDecimal.valueOf(matuom.getNumerator());
			temp = baseQty.divide(numerator, 3, RoundingMode.HALF_UP);
			temp = temp.multiply(denom);
			temp = temp.setScale(0, RoundingMode.CEILING);
			result = temp.intValue();
		}

		setLayersOnPallet(result);

		return result;
	}

	public void clear() {
		/* db_sscc=""; */
		dbLocationId = "";
		dbMaterial = "";
		dbBatchNumber = "";
		dbProcessOrder = "";
		dbQuantity = new BigDecimal("0");
		dbUom = "";
		dbStatus = "";
		dbDespatchNo = "";
		dbConfirmed = false;
		dbEAN = "";
		dbVariant = "";
		dbCustomerID = "";
		dbMHNNumber = "";
		dbDecision = "";
		dbDateOfManufacture = JUtility.getSQLDateTime();
		transactionRef = 0;
		setConfirmed(false);
	}

	public Boolean confirm() {
		Boolean result = false;
		logger.debug("Confirmation status for SSCC " + getSSCC() + " is " + isConfirmed().toString());
		if (isConfirmed() == false) {
			if (getProcessOrderObj(true).getStatus().equals("Ready")
					|| getProcessOrderObj(true).getStatus().equals("Running")) {
				setConfirmed(true);

				if (update() == true) {
					result = true;
					setErrorMessage("");
					writePalletHistory(getTransactionRef(), "PROD DEC", "CONFIRM");

					logger.debug(getSSCC() + " confirmed.");

					if (getLocationObj().isProductionConfirmationMessageRequired()) {
						// opdc.submit(getTransactionRef());
					} else {
						logger.error("Location " + getLocationObj().getLocationID() + " does not require this message.");
					}

				} else {
					logger.error("Error confirming SSCC [" + getSSCC());
				}
			} else {
				setErrorMessage("Cannot confirm SSCC " + getSSCC() + " as Process Order " + getProcessOrder()
						+ " status is " + getProcessOrderObj(true).getStatus());
			}
		} else {
			setErrorMessage("SSCC " + getSSCC() + " already confirmed.");
		}

		return result;
	}

	public boolean create() {
		return create("", "");
	}

	public boolean create(String transactionType, String transactionSubtye) {

		logger.debug("create [" + getSSCC() + "]");

		boolean result = false;

		if (isValidSSCCFormat() == true) {
			if (isValidPallet(getSSCC()) == true) {
				setErrorMessage("Key violation - SSCC [" + getSSCC() + "] already exists !");
			} else {
				if (autoCreateMaterialBatch() == true) {
					// try {
					// PreparedStatement stmtupdate;
					// stmtupdate = Common.hostList
					// .getHost(getHostID())
					// .getConnection(getSessionID())
					// .prepareStatement(
					// Common.hostList.getHost(getHostID()).getSqlstatements()
					// .getSQL("JDBPallet.create"));
					// stmtupdate.setString(1, getSSCC());
					// stmtupdate.execute();
					// stmtupdate.clearParameters();
					// Common.hostList.getHost(getHostID()).getConnection(getSessionID()).commit();
					// stmtupdate.close();
					// result = update();
					//
					// if (result == true) {
					// setTransactionRef(writePalletHistory(0, transactionType,
					// transactionSubtye));
					// }
					// } catch (SQLException e) {
					// setErrorMessage(e.getMessage());
					// }
				}
			}
		}

		return result;
	}

	/**
	 * Method delete.
	 * 
	 * @return boolean
	 */
	public boolean delete() {

		PreparedStatement stmtupdate;
		boolean result = false;
		setErrorMessage("");
		logger.debug("delete");

		// try {
		// if (isValidPallet() == true) {
		// stmtupdate = Common.hostList
		// .getHost(getHostID())
		// .getConnection(getSessionID())
		// .prepareStatement(
		// Common.hostList.getHost(getHostID()).getSqlstatements().getSQL("JDBPallet.delete"));
		// stmtupdate.setString(1, getSSCC());
		// stmtupdate.execute();
		// stmtupdate.clearParameters();
		// Common.hostList.getHost(getHostID()).getConnection(getSessionID()).commit();
		// stmtupdate.close();
		// result = true;
		// }
		// } catch (SQLException e) {
		// setErrorMessage(e.getMessage());
		// }

		return result;
	}

	/**
	 * Method delete.
	 * 
	 * @param sscc
	 *            String
	 * @return boolean
	 */
	public boolean delete(String sscc) {
		setSSCC(sscc);

		return delete();
	}

	/**
	 * Method getBaseQuantity.
	 * 
	 * @return BigDecimal
	 */
	public BigDecimal getBaseQuantity() {
		dbBaseQuantity = calcBaseUOMQuantity(getUom(), getQuantity());

		return dbBaseQuantity;
	}

	public String getBaseQuantityAsString() {
		String result = "";

		try {
			NumberFormat formatter = new DecimalFormat("0.000");
			result = formatter.format(getBaseQuantity());
		} catch (Exception e) {
			result = "0.000";
		}

		return result;
	}

	public JDBMaterial getMaterialObj() {
		if (material.getMaterial().equals(getMaterial()) == false) {
			material.setMaterial(getMaterial());
			material.getMaterialProperties(getMaterial());
		}

		return material;
	}

	/**
	 * Method getBaseUom.
	 * 
	 * @return String
	 */
	public String getBaseUom() {
		if (lastMaterialMaterial.equals(getMaterial()) == false) {
			material.getMaterialProperties(getMaterial());
			lastMaterialMaterial = getMaterial();
		}

		return material.getBaseUom();
	}

	public Timestamp getBatchExpiry() {
		return dbBatchExpiry;
	}

	/**
	 * Method getBatchNumber.An internal error occurred during: "Java Search".
	 * Class file name must end with .class
	 * 
	 * @return String
	 */
	public String getBatchNumber() {
		return JUtility.replaceNullStringwithBlank(dbBatchNumber);
	}

	public String getConfirmed() {
		String result = "N";

		if (isConfirmed() == true) {
			result = "Y";
		}

		return result;
	}

	public String getCustomerID() {
		return dbCustomerID;
	}

	public Timestamp getDateOfManufacture() {
		Timestamp result = dbDateOfManufacture;

		if (isConfirmed() == false) {
			result = null;
		}

		return result;
	}

	public String getDecision() {
		return JUtility.replaceNullStringwithBlank(dbDecision);
	}

	/**
	 * Method getDespatchNo.
	 * 
	 * @return String
	 */
	public String getDespatchNo() {
		return JUtility.replaceNullStringwithBlank(dbDespatchNo);
	}

	/**
	 * Method getEAN.
	 * 
	 * @return String
	 */
	public String getEAN() {
		return dbEAN;
	}

	/**
	 * Method getErrorMessage.
	 * 
	 * @return String
	 */
	public String getErrorMessage() {
		return dbErrorMessage;
	}

	private String getHostID() {
		return hostID;
	}

	public int getLayersOnPallet() {
		return dbLayers;
	}

	/**
	 * Method getLocationID.
	 * 
	 * @return String
	 */
	public String getLocationID() {
		return dbLocationId;
	}

	public JDBLocation getLocationObj() {
		if (location.getLocationID().equals(getLocationID()) == false) {
			location.setLocationID(getLocationID());
			location.getLocationProperties();
		}

		return location;
	}

	/**
	 * Method getMaterial.
	 * 
	 * @return String
	 * @uml.property name="material"
	 */
	public String getMaterial() {
		return JUtility.replaceNullStringwithBlank(dbMaterial);
	}

	public Timestamp getMaterialBatchExpiryDate() {
		Timestamp result = JUtility.getTimestampFromDate(JUtility.getSQLDate());

		if (matBatch.getMaterialBatchProperties(getMaterial(), getBatchNumber()) == true) {
			result = matBatch.getExpiryDate();
		}

		return result;
	}

	/**
	 * Method getMaterialBatchStatus.
	 * 
	 * @return String
	 */
	public String getMaterialBatchStatus() {
		String result = "";

		if (matBatch.getMaterialBatchProperties(getMaterial(), getBatchNumber()) == true) {
			result = matBatch.getStatus();
		}

		return result;
	}

	public String getMHNNumber() {
		return dbMHNNumber;
	}

	/**
	 * Method getPalletData.
	 * 
	 * @param criteria
	 *            PreparedStatement
	 * @param calcBaseQty
	 *            boolean
	 * @return Vector<JDBPallet>
	 */
	public Vector<JDBPallet> getPalletData(PreparedStatement criteria, boolean calcBaseQty) {
		ResultSet rs;

		Vector<JDBPallet> result = new Vector<JDBPallet>();

		// if (Common.hostList.getHost(getHostID()).toString().equals(null)) {
		// result.addElement(new JDBPallet("sscc", "material", "batch",
		// "process_order", new BigDecimal("0"), "uom",
		// new BigDecimal("0"), "base uom", null, "status", "location_id",
		// "ean", "variant"));
		// } else {
		try {
			rs = criteria.executeQuery();

			while (rs.next()) {
				result.addElement(new JDBPallet(rs.getString("sscc"), rs.getString("material"), rs
						.getString("batch_number"), rs.getString("process_order"), rs.getBigDecimal("quantity"), rs
						.getString("uom"), rs.getBigDecimal("base_quantity"), rs.getString("base_uom"), rs
						.getTimestamp("date_of_manufacture"), rs.getString("status"), rs.getString("location_id"), rs
						.getString("ean"), rs.getString("variant")));
			}

			rs.close();
		} catch (Exception e) {
			setErrorMessage(e.getMessage());
		}
		// }

		return result;
	}

	public ResultSet getPalletDataResultSet(PreparedStatement criteria) {
		ResultSet rs;

		try {
			rs = criteria.executeQuery();
		} catch (Exception e) {
			rs = null;
			setErrorMessage(e.getMessage());
		}

		return rs;
	}

	/**
	 * Method getPalletList.
	 * 
	 * @param criteria
	 *            PreparedStatement
	 * @return LinkedList<String>
	 */
	public LinkedList<String> getPalletList(PreparedStatement criteria) {
		ResultSet rs;

		LinkedList<String> result = new LinkedList<String>();

		// if (Common.hostList.getHost(getHostID()).toString().equals(null)) {
		// result.addLast("sscc");
		// } else {
		try {
			rs = criteria.executeQuery();

			while (rs.next()) {
				result.addLast(rs.getString("sscc"));
			}

			rs.close();
		} catch (Exception e) {
			setErrorMessage(e.getMessage());
		}
		// }

		return result;
	}

	/**
	 * Method getPalletProperties.
	 * 
	 * @return boolean
	 */
	public boolean getPalletProperties() {
		boolean result = false;

		PreparedStatement stmt;
		ResultSet rs;
		setErrorMessage("");
		logger.debug("getPalletProperties [" + getSSCC() + "]");

		clear();

		// try {
		// stmt = Common.hostList
		// .getHost(getHostID())
		// .getConnection(getSessionID())
		// .prepareStatement(
		// Common.hostList.getHost(getHostID()).getSqlstatements()
		// .getSQL("JDBPallet.getPalletProperties"));
		// stmt.setFetchSize(1);
		// stmt.setString(1, getSSCC());
		// rs = stmt.executeQuery();
		//
		// if (rs.next()) {
		// getPropertiesfromResultSet(rs);
		// result = true;
		// } else {
		// setErrorMessage("Invalid SSCC [" + getSSCC() + "]");
		// }
		// rs.close();
		// stmt.close();
		//
		// } catch (SQLException e) {
		// setErrorMessage(e.getMessage());
		// }

		return result;
	}

	/**
	 * Method getPalletProperties.
	 * 
	 * @param sscc
	 *            String
	 * @return boolean
	 */
	public boolean getPalletProperties(String sscc) {
		boolean result;
		setSSCC(sscc);
		result = getPalletProperties();

		return result;
	}

	/**
	 * Method getProcessOrder.
	 * 
	 * @return String
	 * @uml.property name="processOrder"
	 */
	public String getProcessOrder() {
		return dbProcessOrder;
	}

	public JDBProcessOrder getProcessOrderObj(boolean refresh) {
		if ((processOrder.getProcessOrder().equals(getProcessOrder()) == false) | (refresh = true)) {
			processOrder.setProcessOrder(getProcessOrder());
			processOrder.getProcessOrderProperties();
		}

		return processOrder;
	}

	public void getPropertiesfromResultSet(ResultSet rs) {
		try {
			clear();

			setSSCC(rs.getString("sscc"));
			setLocationID(rs.getString("location_id"));
			setProcessOrder(rs.getString("process_order"));
			setMaterial(rs.getString("material"));
			setBatchNumber(rs.getString("batch_number"));
			setQuantity(rs.getBigDecimal("quantity"));
			setUom(rs.getString("uom"));
			setEAN(rs.getString("ean"));
			setVariant(rs.getString("variant"));
			setCustomerID(rs.getString("customer_id"));
			setMHNNumber(rs.getString("mhn_number"));
			setDecision(rs.getString("decision"));
			setDateOfManufacture(rs.getTimestamp("date_of_manufacture"));
			setStatus(rs.getString("status"));
			setDespatchNo(rs.getString("despatch_no"));
			setConfirmed(rs.getString("confirmed"));
			setLayersOnPallet(rs.getInt("layers"));
			try {
				if (expiryMode.equals("SSCC")) {
					setBatchExpiry(rs.getTimestamp("sscc_expiry_date"));
				} else {
					setBatchExpiry(getMaterialBatchExpiryDate());
				}
			} catch (Exception ex) {
				setBatchExpiry(new Timestamp(0));
			}
			setMHNNumber(rs.getString("mhn_number"));
			setDecision(rs.getString("decision"));
		} catch (SQLException e) {
			setErrorMessage(e.getMessage());
		}
	}

	/**
	 * Method getQuantity.
	 * 
	 * @return BigDecimal
	 */
	public BigDecimal getQuantity() {
		return dbQuantity;
	}

	public String getQuantityPerLayerUOM() {
		String result = "";
		tempCont.getProperties("UOM BASE QTY PER LAYER");
		result = tempCont.getKeyValueWithDefault("UOM BASE QTY PER LAYER", "LAG", "UOM Base Qty Per Layer");

		return result;
	}

	public String getQuantityPerPalletUOM() {
		String result = "";
		tempCont.getProperties("UOM BASE QTY PER PALLET");
		result = tempCont.getKeyValueWithDefault("UOM BASE QTY PER PALLET", "PAL", "UOM Base Qty Per Pallet");

		return result;
	}

	private String getSessionID() {
		return sessionID;
	}

	/**
	 * Method getSSCC.
	 * 
	 * @return String
	 */
	public String getSSCC() {
		return dbSSCC;
	}

	/**
	 * Method getStatus.
	 * 
	 * @return String
	 */
	public String getStatus() {
		return JUtility.replaceNullStringwithBlank(dbStatus);
	}

	public long getTransactionRef() {
		return transactionRef;
	}

	/**
	 * Method getUom.
	 * 
	 * @return String
	 */
	public String getUom() {
		return dbUom;
	}

	/**
	 * Method getVariant.
	 * 
	 * @return String
	 */
	public String getVariant() {
		return dbVariant;
	}

	private void initObjects() {
		materialuom = new JDBMaterialUom(getHostID(), getSessionID());
		// opdc = new OutgoingProductionDeclarationConfirmation(getHostID(),
		// getSessionID());
		ctrl = new JDBControl(getHostID(), getSessionID());
		matBatch = new JDBMaterialBatch(getHostID(), getSessionID());
		material = new JDBMaterial(getHostID(), getSessionID());
		matUom = new JDBMaterialUom(getHostID(), getSessionID());
		processOrder = new JDBProcessOrder(getHostID(), getSessionID());
		tempCont = new JDBControl(getHostID(), getSessionID());
		location = new JDBLocation(getHostID(), getSessionID());
	}

	public Boolean isConfirmed() {
		return dbConfirmed;
	}

	/**
	 * Method isValidPallet.
	 * 
	 * @return boolean
	 */
	public boolean isValidPallet() {
		PreparedStatement stmt;
		ResultSet rs;
		boolean result = false;

		// try {
		// stmt = Common.hostList
		// .getHost(getHostID())
		// .getConnection(getSessionID())
		// .prepareStatement(
		// Common.hostList.getHost(getHostID()).getSqlstatements().getSQL("JDBPallet.isValidPallet"));
		// stmt.setString(1, getSSCC());
		// stmt.setFetchSize(1);
		// rs = stmt.executeQuery();
		//
		// if (rs.next()) {
		// result = true;
		// } else {
		// setErrorMessage("Invalid SSCC");
		// }
		//
		// rs.close();
		// stmt.close();
		// } catch (SQLException e) {
		// setErrorMessage(e.getMessage());
		// }

		return result;
	}

	public boolean isValidPallet(String sscc) {
		setSSCC(sscc);

		return isValidPallet();
	}

	/**
	 * Method isValid.
	 * 
	 * @return boolean
	 */
	public boolean isValidSSCCFormat() {
		boolean result = true;

		if (JUtility.isNullORBlank(getSSCC())) {
			setErrorMessage("SSCC cannot be blank");
			result = false;
		} else {
			if (getSSCC().length() != 18) {
				setErrorMessage("SSCC is incorrect size");
				result = false;
			} else {
				// if (barcode.validateCheckdigit(getSSCC()) == false) {
				// setErrorMessage("Invalid SSCC check digit");
				result = false;
				// }
			}
		}

		return result;
	}

	/**
	 * Method populateFromProcessOrder.
	 * 
	 * @return boolean
	 */
	public boolean populateFromProcessOrder() {
		boolean result = true;

		if (processOrder.getProcessOrderProperties(getProcessOrder())) {
			/* Get Material */
			setMaterial(processOrder.getMaterial());
			setLocationID(processOrder.getLocation());

			setUom(processOrder.getRequiredUom());

			setStatus(processOrder.getDefaultPalletStatus());
			lastMaterialMaterial = getMaterial();

			if (matUom.getMaterialUomProperties(getMaterial(), getUom())) {
				setEAN(matUom.getEan());
				setVariant(matUom.getVariant());
			}

			setCustomerID(processOrder.getCustomerID());
		} else {
			result = false;
			setErrorMessage(processOrder.getErrorMessage());
		}

		return result;
	}

	public void setBatchExpiry(Timestamp ts) {
		ts.setNanos(0);
		dbBatchExpiry = ts;
	}

	/**
	 * Method setBatchNumber.
	 * 
	 * @param batch
	 *            String
	 */
	public void setBatchNumber(String batch) {
		dbBatchNumber = JUtility.replaceNullStringwithBlank(batch);
	}

	public void setConfirmed(Boolean yesNo) {
		dbConfirmed = yesNo;
	}

	public void setConfirmed(String yesNo) {

		try {
			if (yesNo.toUpperCase().equals("Y")) {
				dbConfirmed = true;
			} else {
				dbConfirmed = false;
			}
		} catch (Exception ex) {
			dbConfirmed = false;
		}
	}

	public void setCustomerID(String custId) {
		dbCustomerID = JUtility.replaceNullStringwithBlank(custId);
		if (dbCustomerID.equals("")) {
			dbCustomerID = "SELF";
		}
	}

	/**
	 * Method setDateOfManufacture.
	 * 
	 * @param dom
	 *            Timestamp
	 */
	public void setDateOfManufacture(Timestamp dom) {
		dbDateOfManufacture = dom;
	}

	public void setDecision(String decision) {
		dbDecision = JUtility.replaceNullStringwithBlank(decision);
	}

	/**
	 * Method setDespatchNo.
	 * 
	 * @param despatchNo
	 *            String
	 */
	public void setDespatchNo(String despatchNo) {
		dbDespatchNo = JUtility.replaceNullStringwithBlank(despatchNo);
	}

	/**
	 * Method setEAN.
	 * 
	 * @param ean
	 *            String
	 */
	public void setEAN(String ean) {
		dbEAN = ean;
	}

	/**
	 * Method setErrorMessage.
	 * 
	 * @param ErrorMsg
	 *            String
	 */
	private void setErrorMessage(String ErrorMsg) {
		if (ErrorMsg.isEmpty() == false) {
			logger.debug(ErrorMsg);
		}

		dbErrorMessage = ErrorMsg;
	}

	private void setHostID(String host) {
		hostID = host;
	}

	public void setLayersOnPallet(int layers) {
		dbLayers = layers;
	}

	/**
	 * Method setLocationID.
	 * 
	 * @param location_id
	 *            String
	 */
	public void setLocationID(String location_id) {
		dbLocationId = JUtility.replaceNullStringwithBlank(location_id);
	}

	/**
	 * Method setMaterial.
	 * 
	 * @param mat
	 *            String
	 */
	public void setMaterial(String mat) {
		dbMaterial = JUtility.replaceNullStringwithBlank(mat);
	}

	public void setMHNNumber(String mhn) {
		dbMHNNumber = JUtility.replaceNullStringwithBlank(mhn);
	}

	/**
	 * Method setProcessOrder.
	 * 
	 * @param process_order
	 *            String
	 */
	public void setProcessOrder(String process_order) {
		dbProcessOrder = JUtility.replaceNullStringwithBlank(process_order);
	}

	/**
	 * Method setQuantity.
	 * 
	 * @param quantity
	 *            BigDecimal
	 * @param recalc
	 *            boolean
	 */
	public void setQuantity(BigDecimal quantity) {
		if (quantity == null) {
			quantity = new BigDecimal("0");
		}

		dbQuantity = quantity;
	}

	private void setSessionID(String session) {
		sessionID = session;
	}

	/**
	 * Method setSSCC.
	 * 
	 * @param sscc
	 *            String
	 */
	public void setSSCC(String sscc) {
		dbSSCC = sscc;
	}

	/**
	 * Method setStatus.
	 * 
	 * @param status
	 *            String
	 */
	public void setStatus(String status) {
		dbStatus = JUtility.replaceNullStringwithBlank(status);
	}

	/**
	 * Method create.
	 * 
	 * @return boolean
	 */
	public void setTransactionRef(long txn) {
		transactionRef = txn;
	}

	/**
	 * Method setUom.
	 * 
	 * @param u
	 *            String
	 */
	public void setUom(String u) {
		dbUom = JUtility.replaceNullStringwithBlank(u);
	}

	/**
	 * Method setVariant.
	 * 
	 * @param variant
	 *            String
	 */
	public void setVariant(String variant) {
		dbVariant = JUtility.replaceNullStringwithBlank(variant);
	}

	/**
	 * Method update.
	 * 
	 * @return boolean
	 */
	public boolean update() {
		boolean result = false;

		logger.debug("update [" + getSSCC() + "]");

		// if (isValidSSCCFormat() == true) {
		// try {
		// if (autoCreateMaterialBatch() == true) {
		// PreparedStatement stmtupdate;
		// stmtupdate = Common.hostList
		// .getHost(getHostID())
		// .getConnection(getSessionID())
		// .prepareStatement(
		// Common.hostList.getHost(getHostID()).getSqlstatements().getSQL("JDBPallet.update"));
		// stmtupdate.setString(1, getDespatchNo());
		// stmtupdate.setString(2, getLocationID());
		// stmtupdate.setString(3, getMaterial());
		// stmtupdate.setString(4, getBatchNumber());
		// stmtupdate.setString(5, getProcessOrder());
		// stmtupdate.setBigDecimal(6, getQuantity());
		// stmtupdate.setString(7, getUom());
		// stmtupdate.setTimestamp(8, getDateOfManufacture());
		// stmtupdate.setString(9, getStatus());
		// stmtupdate.setString(10, getEAN());
		// stmtupdate.setString(11, getVariant());
		// stmtupdate.setString(12, getConfirmed());
		// calcBaseUOMQuantity(getUom(), getQuantity());
		// stmtupdate.setInt(13, getLayersOnPallet());
		// stmtupdate.setTimestamp(14, getBatchExpiry());
		// stmtupdate.setString(15, getCustomerID());
		// stmtupdate.setString(16,
		// JUtility.replaceNullStringwithBlank(getMHNNumber()));
		// stmtupdate.setString(17, getDecision());
		// stmtupdate.setString(18, getSSCC());
		// stmtupdate.execute();
		// stmtupdate.clearParameters();
		// stmtupdate.close();
		// Common.hostList.getHost(getHostID()).getConnection(getSessionID()).commit();
		//
		// result = true;
		// } else {
		// }
		// } catch (SQLException e) {
		// setErrorMessage(e.getMessage());
		// }
		// }

		return result;
	}

	public boolean updateDespatchNo() {
		boolean result = false;

		logger.debug("updateDespatchNo [" + getSSCC() + "] [" + getDespatchNo() + "]");

		// try {
		// PreparedStatement stmtupdate;
		// stmtupdate = Common.hostList
		// .getHost(getHostID())
		// .getConnection(getSessionID())
		// .prepareStatement(
		// Common.hostList.getHost(getHostID()).getSqlstatements()
		// .getSQL("JDBPallet.updateDespatchNo"));
		// stmtupdate.setString(1, getDespatchNo());
		// stmtupdate.setString(2, getSSCC());
		// stmtupdate.execute();
		// stmtupdate.clearParameters();
		// stmtupdate.close();
		// Common.hostList.getHost(getHostID()).getConnection(getSessionID()).commit();
		//
		// result = true;
		// } catch (SQLException e) {
		// setErrorMessage(e.getMessage());
		// }

		return result;
	}

	public boolean updateLocationID() {
		boolean result = false;

		logger.debug("updateLocationID [" + getSSCC() + "] [" + getLocationID() + "]");

		// try {
		// PreparedStatement stmtupdate;
		// stmtupdate = Common.hostList
		// .getHost(getHostID())
		// .getConnection(getSessionID())
		// .prepareStatement(
		// Common.hostList.getHost(getHostID()).getSqlstatements()
		// .getSQL("JDBPallet.updateLocationID"));
		// stmtupdate.setString(1, getLocationID());
		// stmtupdate.setString(2, getSSCC());
		// stmtupdate.execute();
		// stmtupdate.clearParameters();
		// stmtupdate.close();
		// Common.hostList.getHost(getHostID()).getConnection(getSessionID()).commit();
		//
		// result = true;
		// } catch (SQLException e) {
		// setErrorMessage(e.getMessage());
		// }

		return result;
	}

	public boolean updateMHNNumber(String mhn) {
		boolean result = false;

		setMHNNumber(mhn);
		logger.debug("updateMHNNumber [" + getSSCC() + "] [" + getMHNNumber() + "]");

		// try {
		// Long txn = (long) 0;
		// PreparedStatement stmtupdate;
		// stmtupdate = Common.hostList
		// .getHost(getHostID())
		// .getConnection(getSessionID())
		// .prepareStatement(
		// Common.hostList.getHost(getHostID()).getSqlstatements().getSQL("JDBPallet.updateMHNNumber"));
		// stmtupdate.setString(1, getMHNNumber());
		// stmtupdate.setString(2, getSSCC());
		// stmtupdate.execute();
		// stmtupdate.clearParameters();
		// stmtupdate.close();
		// Common.hostList.getHost(getHostID()).getConnection(getSessionID()).commit();
		// if (getMHNNumber().equals("")) {
		// writePalletHistory(txn, "MHN", "REMOVE");
		// } else {
		// writePalletHistory(txn, "MHN", "ADD");
		// }
		// result = true;
		// } catch (SQLException e) {
		// setErrorMessage(e.getMessage());
		// }

		return result;
	}

	public boolean updateMHNDecision(String decision) {
		boolean result = false;

		setDecision(decision);
		logger.debug("updateDecision [" + getSSCC() + "] [" + getDecision() + "]");
		// if (decision.equals("") == false) {
		// try {
		// Long txn = (long) 0;
		// PreparedStatement stmtupdate;
		// stmtupdate = Common.hostList
		// .getHost(getHostID())
		// .getConnection(getSessionID())
		// .prepareStatement(
		// Common.hostList.getHost(getHostID()).getSqlstatements()
		// .getSQL("JDBPallet.updateDecision"));
		// stmtupdate.setString(1, getDecision());
		// stmtupdate.setString(2, getSSCC());
		// stmtupdate.execute();
		// stmtupdate.clearParameters();
		// stmtupdate.close();
		// Common.hostList.getHost(getHostID()).getConnection(getSessionID()).commit();
		// writePalletHistory(txn, "MHN", "DECISION");
		// result = true;
		// } catch (SQLException e) {
		// setErrorMessage(e.getMessage());
		// }
		// } else {
		// result = true;
		// }

		return result;
	}

	public Long updateStatus(String newStatus) {
		Long result = (long) 0;

		logger.debug("updateStatus [" + getSSCC() + "] [" + newStatus + "]");

		// try {
		// if
		// (JUtility.replaceNullStringwithBlank(newStatus).equals(getStatus())
		// == false) {
		// Long txn = (long) 0;
		// txn = writePalletHistory(txn, "STATUS CHANGE", "FROM");
		// setStatus(newStatus);
		// PreparedStatement stmtupdate;
		// stmtupdate = Common.hostList
		// .getHost(getHostID())
		// .getConnection(getSessionID())
		// .prepareStatement(
		// Common.hostList.getHost(getHostID()).getSqlstatements()
		// .getSQL("JDBPallet.updateStatus"));
		// stmtupdate.setString(1, getStatus());
		// stmtupdate.setString(2, getSSCC());
		// stmtupdate.execute();
		// stmtupdate.clearParameters();
		// stmtupdate.close();
		// Common.hostList.getHost(getHostID()).getConnection(getSessionID()).commit();
		// writePalletHistory(txn, "STATUS CHANGE", "TO");
		//
		// if (txn > 0) {
		// if (getLocationObj().isStatusChangeMessageRequired() == true) {
		// OutgoingPalletStatusChange opsc = new
		// OutgoingPalletStatusChange(Common.selectedHostID,
		// Common.sessionID);
		// opsc.submit(txn);
		// } else {
		// logger.debug("Pallet Status Message Suppressed for Location "
		// + getLocationObj().getLocationID());
		// }
		// }
		// result = txn;
		// } else {
		// setErrorMessage("No update required.");
		// }
		//
		// } catch (SQLException e) {
		// setErrorMessage(e.getMessage());
		// }

		return result;
	}

	public long writePalletHistory(long txnRef, String transactionType, String transactionSubtye) {
		long txn = txnRef;

		if (transactionType.length() > 0) {
			JDBPalletHistory ph = new JDBPalletHistory(getHostID(), getSessionID());

			if (txn == 0) {
				txn = ph.generateNewTransactionRef();
			}
			setTransactionRef(txn);

			ph.setTransactionRef(txn);
			ph.setTransactionType(transactionType);
			ph.setTransactionSubtype(transactionSubtye);
			ph.setTransactionDate(JUtility.getSQLDateTime());
			ph.setPallet(this);
			ph.write();
		} else {
			logger.error("Invalid TransactionType");
		}

		return txn;
	}
}

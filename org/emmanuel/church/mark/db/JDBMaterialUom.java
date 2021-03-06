// $codepro.audit.disable numericLiterals
package org.emmanuel.church.mark.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.emmanuel.church.mark.util.JUtility;

/**
 * @author David
 */
public class JDBMaterialUom {
	/**
	 * @uml.property name="dbErrorMessage"
	 */
	private String dbErrorMessage;
	/**
	 * @uml.property name="dbMaterial"
	 */
	private String dbMaterial;
	/**
	 * @uml.property name="dbMaterialDenominator"
	 */
	private Integer dbMaterialDenominator;
	/**
	 * @uml.property name="dbMaterialEan"
	 */
	private String dbMaterialEan;
	/**
	 * @uml.property name="dbMaterialNumerator"
	 */
	private Integer dbMaterialNumerator;
	/**
	 * @uml.property name="dbMaterialUom"
	 */
	private String dbMaterialUom;
	/**
	 * @uml.property name="dbMaterialVariant"
	 */
	private String dbMaterialVariant;

	/* Material Uom */
	/**
	 * Field field_ean. Value: {@value field_ean}
	 */
	public static int field_ean = 14;
	/**
	 * Field field_variant. Value: {@value field_variant}
	 */
	public static int field_variant = 2;

	/**
	 * @uml.property name="logger"
	 * @uml.associationEnd multiplicity="(1 1)"
	 */
	private final Logger logger = Logger.getLogger(JDBMaterialBatch.class);
	private String hostID;
	private String sessionID;

	private void setSessionID(String session) {
		sessionID = session;
	}

	private void setHostID(String host) {
		hostID = host;
	}

	private String getSessionID() {
		return sessionID;
	}

	private String getHostID() {
		return hostID;
	}

	/**
	 * @uml.property name="uom"
	 * @uml.associationEnd multiplicity="(1 1)"
	 */
	private JDBUom uom;

	public JDBMaterialUom(String host, String session) {
		setHostID(host);
		setSessionID(session);
		uom = new JDBUom(getHostID(), getSessionID());
	}

	/**
	 * Constructor for JDBMaterialUom.
	 * 
	 * @param material
	 *            String
	 * @param uom
	 *            String
	 * @param ean
	 *            String
	 * @param variant
	 *            String
	 * @param numerator
	 *            Integer
	 * @param denominator
	 *            Integer
	 */
	public JDBMaterialUom(String host, String session, String material, String uom, String ean, String variant,
			Integer numerator, Integer denominator) {
		setHostID(host);
		setSessionID(session);
		setMaterial(material);
		setUom(uom);
		setEan(ean);
		setVariant(variant);
		setNumerator(numerator);
		setDenominator(denominator);
	}

	public JDBMaterialUom(ResultSet rs) {
		getPropertiesfromResultSet(rs);
	}

	public void getPropertiesfromResultSet(ResultSet rs) {
		try {
			clear();

			setMaterial(rs.getString("material"));
			setUom(rs.getString("uom"));
			setEan(rs.getString("ean"));
			setVariant(rs.getString("variant"));
			setNumerator(rs.getInt("numerator"));
			setDenominator(rs.getInt("denominator"));

		} catch (SQLException e) {
			setErrorMessage(e.getMessage());
		}
	}

	public ResultSet getMaterialDataResultSet(PreparedStatement criteria) {

		ResultSet rs;

		try {
			rs = criteria.executeQuery();

		} catch (Exception e) {
			rs = null;
			setErrorMessage(e.getMessage());
		}

		return rs;
	}

	public void clear() {
		// setMaterial("");
		// setUom("");
		setEan("");
		setVariant("");
		setNumerator(0);
		setDenominator(0);
	}

	/**
	 * Method create.
	 * 
	 * @return boolean
	 */
	public boolean create() {

		logger.debug("create [" + getMaterial() + "] [" + getUom() + "]");

		boolean result = false;

		// if (isValid() == true)
		// {
		//
		// if (isValidMaterialUom() == true)
		// {
		// setErrorMessage("Key violation - material [" + getMaterial() + " ] ["
		// + getUom() + "] already exists !");
		// }
		// else
		// {
		// try
		// {
		// PreparedStatement stmtupdate;
		// stmtupdate =
		// Common.hostList.getHost(getHostID()).getConnection(getSessionID()).prepareStatement(Common.hostList.getHost(getHostID()).getSqlstatements().getSQL("JDBMaterialUom.create"));
		// stmtupdate.setString(1, getMaterial());
		// stmtupdate.setString(2, getUom());
		// stmtupdate.execute();
		// stmtupdate.clearParameters();
		// Common.hostList.getHost(getHostID()).getConnection(getSessionID()).commit();
		// stmtupdate.close();
		// update();
		// result = true;
		// }
		// catch (SQLException e)
		// {
		// setErrorMessage(e.getMessage());
		// }
		// }
		// }

		return result;
	}

	/**
	 * Method create.
	 * 
	 * @param material
	 *            String
	 * @param uom
	 *            String
	 * @return boolean
	 */
	public boolean create(String material, String uom) {
		boolean result = false;
		setMaterial(material);
		setUom(uom);
		result = create();
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

		// try
		// {
		// stmtupdate =
		// Common.hostList.getHost(getHostID()).getConnection(getSessionID()).prepareStatement(Common.hostList.getHost(getHostID()).getSqlstatements().getSQL("JDBMaterialUom.delete"));
		// stmtupdate.setString(1, getMaterial());
		// stmtupdate.setString(2, getUom());
		// stmtupdate.execute();
		// stmtupdate.clearParameters();
		// Common.hostList.getHost(getHostID()).getConnection(getSessionID()).commit();
		//
		// stmtupdate.close();
		// result = true;
		//
		// }
		// catch (SQLException e)
		// {
		// setErrorMessage(e.getMessage());
		// }

		return result;
	}

	/**
	 * Method delete.
	 * 
	 * @param material
	 *            String
	 * @param uom
	 *            String
	 * @return boolean
	 */
	public boolean delete(String material, String uom) {
		boolean result = false;
		setMaterial(material);
		setUom(uom);
		result = delete();
		return result;
	}

	/**
	 * Method getDenominator.
	 * 
	 * @return Integer
	 */
	public Integer getDenominator() {
		return dbMaterialDenominator;
	}

	/**
	 * Method getEan.
	 * 
	 * @return String
	 */
	public String getEan() {
		return dbMaterialEan;
	}

	/**
	 * Method getErrorMessage.
	 * 
	 * @return String
	 */
	public String getErrorMessage() {
		return dbErrorMessage;
	}

	/**
	 * Method getMaterial.
	 * 
	 * @return String
	 */
	public String getMaterial() {
		return dbMaterial;
	}

	/**
	 * Method getMaterialUomData.
	 * 
	 * @param criteria
	 *            PreparedStatement
	 * @return Vector<JDBMaterialUom>
	 */
	public Vector<JDBMaterialUom> getMaterialUomData(PreparedStatement criteria) {
		ResultSet rs;
		Vector<JDBMaterialUom> result = new Vector<JDBMaterialUom>();

		// if (Common.hostList.getHost(getHostID()).toString().equals(null)) {
		// result.addElement(new JDBMaterialUom(getHostID(), getSessionID(),
		// "material", "uom", "variant", "ean", 0, 0));
		// } else {
		// try {
		// rs = criteria.executeQuery();
		//
		// while (rs.next()) {
		// result.addElement(new JDBMaterialUom(getHostID(), getSessionID(),
		// rs.getString("material"), rs
		// .getString("uom"), rs.getString("ean"), rs.getString("variant"),
		// rs.getInt("numerator"), rs
		// .getInt("denominator")));
		// }
		// rs.close();
		//
		// } catch (Exception e) {
		// setErrorMessage(e.getMessage());
		// }
		// }

		return result;
	}

	/**
	 * Method getMaterialUomProperties.
	 * 
	 * @return boolean
	 */
	public boolean getMaterialUomProperties() {
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
		// .getSQL("JDBMaterialUom.getMaterialUomProperties"));
		// stmt.setFetchSize(1);
		// stmt.setString(1, getMaterial());
		// stmt.setString(2, getUom());
		// rs = stmt.executeQuery();
		//
		// if (rs.next()) {
		// getPropertiesfromResultSet(rs);
		// result = true;
		// } else {
		// setErrorMessage("Invalid Material Uom");
		// }
		// rs.close();
		// stmt.close();
		// } catch (SQLException e) {
		// setErrorMessage(e.getMessage());
		// }
		return result;
	}

	/**
	 * Method getMaterialUomProperties.
	 * 
	 * @param mat
	 *            String
	 * @param uom
	 *            String
	 * @return boolean
	 */
	public boolean getMaterialUomProperties(String mat, String uom) {
		setMaterial(mat);
		setUom(uom);
		return getMaterialUomProperties();
	}

	/**
	 * Method getMaterialUoms.
	 * 
	 * @return Vector<JDBUom>
	 */
	public Vector<JDBUom> getMaterialUoms() {
		Vector<JDBUom> uomList = new Vector<JDBUom>();
		PreparedStatement stmt;
		ResultSet rs;
		setErrorMessage("");

		// try {
		//
		// stmt = Common.hostList
		// .getHost(getHostID())
		// .getConnection(getSessionID())
		// .prepareStatement(
		// Common.hostList.getHost(getHostID()).getSqlstatements()
		// .getSQL("JDBMaterialUom.getMaterialUoms"));
		// stmt.setFetchSize(250);
		// stmt.setString(1, getMaterial());
		// rs = stmt.executeQuery();
		//
		// while (rs.next()) {
		// JDBUom uom = new JDBUom(getHostID(), getSessionID());
		// uom.setInternalUom(rs.getString("uom"));
		// uom.getInternalUomProperties();
		// uomList.add(uom);
		// }
		// rs.close();
		// stmt.close();
		//
		// } catch (SQLException e) {
		// setErrorMessage(e.getMessage());
		// }

		return uomList;
	}

	/**
	 * Method getMaterialUoms.
	 * 
	 * @param material
	 *            String
	 * @return Vector<JDBUom>
	 */
	public Vector<JDBUom> getMaterialUoms(String material) {
		setMaterial(material);
		return getMaterialUoms();
	}

	/**
	 * Method getNumerator.
	 * 
	 * @return Integer
	 */
	public Integer getNumerator() {
		return dbMaterialNumerator;
	}

	/**
	 * Method getUom.
	 * 
	 * @return String
	 * @uml.property name="uom"
	 */
	public String getUom() {
		return dbMaterialUom;
	}

	/**
	 * Method getVariant.
	 * 
	 * @return String
	 */
	public String getVariant() {
		return dbMaterialVariant;
	}

	/**
	 * Method isValid.
	 * 
	 * @return boolean
	 */
	public boolean isValid() {
		boolean result = true;

		/* Check Material */
		if (JUtility.isNullORBlank(dbMaterial) == true) {
			setErrorMessage("MATERIAL code cannot be null");
			result = false;
		}

		/* Check Base UOM */
		if (result == true) {
			if (JUtility.isNullORBlank(dbMaterialUom) == true) {
				setErrorMessage("UOM code cannot be null");
				result = false;
			} else {
				uom.setInternalUom(dbMaterialUom);
				result = uom.isValidInternalUom();
				if (result == false)
					setErrorMessage(uom.getErrorMessage());
			}
		}

		if (result == false) {
			logger.debug("isValid [" + getMaterial() + "] " + getErrorMessage());
		}

		return result;
	}

	/**
	 * Method isValidMaterialUom.
	 * 
	 * @return boolean
	 */
	public boolean isValidMaterialUom() {

		PreparedStatement stmt;
		ResultSet rs;
		boolean result = false;

		// try {
		// stmt = Common.hostList
		// .getHost(getHostID())
		// .getConnection(getSessionID())
		// .prepareStatement(
		// Common.hostList.getHost(getHostID()).getSqlstatements()
		// .getSQL("JDBMaterialUom.isValidMaterialUom"));
		// stmt.setString(1, getMaterial());
		// stmt.setString(2, getUom());
		// stmt.setFetchSize(1);
		// rs = stmt.executeQuery();
		//
		// if (rs.next()) {
		// result = true;
		// } else {
		// setErrorMessage("Invalid Material / Uom");
		// }
		// rs.close();
		// stmt.close();
		// } catch (SQLException e) {
		// setErrorMessage(e.getMessage());
		// }

		logger.debug("isValidMaterialUom :" + result);

		return result;

	}

	/**
	 * Method isValidMaterialUom.
	 * 
	 * @param material
	 *            String
	 * @param uom
	 *            String
	 * @return boolean
	 */
	public boolean isValidMaterialUom(String material, String uom) {
		setMaterial(material);
		setUom(uom);
		return isValidMaterialUom();
	}

	/**
	 * Method setDenominator.
	 * 
	 * @param denominator
	 *            Integer
	 */
	public void setDenominator(Integer denominator) {
		dbMaterialDenominator = denominator;
	}

	/**
	 * Method setEan.
	 * 
	 * @param ean
	 *            String
	 */
	public void setEan(String ean) {
		dbMaterialEan = ean;
	}

	/**
	 * Method setErrorMessage.
	 * 
	 * @param errorMsg
	 *            String
	 */
	private void setErrorMessage(String errorMsg) {
		if (errorMsg.isEmpty() == false) {
			logger.error(errorMsg);
		}
		dbErrorMessage = errorMsg;
	}

	/**
	 * Method setMaterial.
	 * 
	 * @param material
	 *            String
	 */
	public void setMaterial(String material) {
		dbMaterial = material;
	}

	/**
	 * Method setNumerator.
	 * 
	 * @param numerator
	 *            Integer
	 */
	public void setNumerator(Integer numerator) {
		dbMaterialNumerator = numerator;
	}

	/**
	 * Method setUom.
	 * 
	 * @param uom
	 *            String
	 */
	public void setUom(String uom) {
		dbMaterialUom = uom;
	}

	/**
	 * Method setVariant.
	 * 
	 * @param variant
	 *            String
	 */
	public void setVariant(String variant) {
		dbMaterialVariant = variant;
	}

	/**
	 * Method update.
	 * 
	 * @return boolean
	 */
	public boolean update() {
		boolean result = false;

		logger.debug("update [" + getMaterial() + "] [" + getUom() + "]");

		// if (isValid() == true) {
		// try {
		// PreparedStatement stmtupdate;
		// stmtupdate = Common.hostList
		// .getHost(getHostID())
		// .getConnection(getSessionID())
		// .prepareStatement(
		// Common.hostList.getHost(getHostID()).getSqlstatements().getSQL("JDBMaterialUom.update"));
		//
		// stmtupdate.setString(1, getEan());
		// stmtupdate.setString(2, getVariant());
		// stmtupdate.setInt(3, getNumerator());
		// stmtupdate.setInt(4, getDenominator());
		// stmtupdate.setString(5, getMaterial());
		// stmtupdate.setString(6, getUom());
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

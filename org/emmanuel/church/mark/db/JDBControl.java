// $codepro.audit.disable numericLiterals
/*
 * Created on 15-Nov-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package org.emmanuel.church.mark.db;

/**
 * @author David Garratt
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.emmanuel.church.mark.util.JUtility;

/**
 * @author David
 * @version $Revision: 1.0 $
 */
public class JDBControl {
	/**
	 * Field field_description. Value: {@value field_description} Value:
	 * {@value field_description}
	 */
	public static int field_description = 255;
	/**
	 * Field field_key_value. Value: {@value field_key_value} Value: {@value
	 * field_key_value}
	 */
	public static int field_key_value = 80;
	/* Control */
	/**
	 * Field field_system_key. Value: {@value field_system_key} Value: {@value
	 * field_system_key}
	 */
	public static int field_system_key = 40;
	/**
	 * @uml.property name="dbDescription"
	 */
	private String dbDescription;

	/**
	 * @uml.property name="dbErrorMessage"
	 */
	private String dbErrorMessage;
	/**
	 * @uml.property name="dbKeyValue"
	 */
	private String dbKeyValue;
	/**
	 * @uml.property name="dbSystemKey"
	 */
	private String dbSystemKey;

	/**
	 * @uml.property name="logger"
	 * @uml.associationEnd multiplicity="(1 1)"
	 * 
	 */
	private String hostID;
	private String sessionID;

	private final Logger logger = Logger.getLogger(JDBControl.class);

	public JDBControl(String host, String session) {
		setHostID(host);
		setSessionID(session);
	}

	public JDBControl(String host, String session, ResultSet rs) {
		setHostID(host);
		setSessionID(session);
		getPropertiesfromResultSet(rs);
	}

	/**
	 * Constructor for JDBControl.
	 * 
	 * @param key
	 *            String
	 * @param value
	 *            String
	 * @param description
	 *            String
	 */
	public JDBControl(String host, String session, String key, String value, String description) {
		setHostID(host);
		setSessionID(session);
		setSystemKey(key);
		setKeyValue(value);
		setDescription(description);
	}

	public void clear() {
		setDescription("");
		setKeyValue("");
	}

	/**
	 * Method create.
	 * 
	 * @param systemKey
	 *            String
	 * @param keyValue
	 *            String
	 * @param description
	 *            String
	 * @return boolean
	 */
	public boolean create(String systemKey, String keyValue, String description) {
		boolean result = false;
		logger.debug("JDBControl.create");
		setSystemKey(systemKey);
		setKeyValue(keyValue);
		setDescription(description);

		// if (isValidSystemKey() == false) {
		// try {
		// PreparedStatement stmtupdate;
		//
		// stmtupdate = Common.hostList
		// .getHost(getHostID())
		// .getConnection(getSessionID())
		// .prepareStatement(
		// Common.hostList.getHost(getHostID()).getSqlstatements().getSQL("JDBControl.create"));
		// stmtupdate.setString(1, getSystemKey());
		// stmtupdate.setString(2, getKeyValue());
		// stmtupdate.setString(3, getDescription());
		// stmtupdate.execute();
		// stmtupdate.clearParameters();
		// if
		// (Common.hostList.getHost(getHostID()).getConnection(getSessionID()).getAutoCommit()
		// == false) {
		// Common.hostList.getHost(getHostID()).getConnection(getSessionID()).commit();
		// }
		// stmtupdate.close();
		// result = true;
		// } catch (Exception e) {
		// setErrorMessage(e.getMessage());
		// logger.error("JDBControl.create " + e.getMessage());
		// result = false;
		// }
		// } else {
		// setErrorMessage("System Key already exists");
		// }
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

		// try {
		// if (isValidSystemKey() == true) {
		// stmtupdate = Common.hostList
		// .getHost(getHostID())
		// .getConnection(getSessionID())
		// .prepareStatement(
		// Common.hostList.getHost(getHostID()).getSqlstatements().getSQL("JDBControl.delete"));
		// stmtupdate.setString(1, getSystemKey());
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
	 * Method getControlData.
	 * 
	 * @return Vector<JDBControl>
	 */
	public Vector<JDBControl> getControlData() {
		Statement stmt;
		ResultSet rs;
		Vector<JDBControl> result = new Vector<JDBControl>();

		// if (Common.hostList.getHost(getHostID()).toString().equals(null)) {
		// result.addElement(new JDBControl(getHostID(), getSessionID(),
		// "system_key", "key_value", "description"));
		// } else {
		// try {
		// stmt =
		// Common.hostList.getHost(getHostID()).getConnection(getSessionID()).createStatement();
		// stmt.setFetchSize(1);
		// rs =
		// stmt.executeQuery(Common.hostList.getHost(getHostID()).getSqlstatements()
		// .getSQL("JDBControl.getControlData"));
		//
		// while (rs.next()) {
		// result.addElement(new JDBControl(getHostID(), getSessionID(),
		// rs.getString("system_key"), rs
		// .getString("key_value"), rs.getString("description")));
		// }
		// rs.close();
		// stmt.close();
		//
		// } catch (Exception e) {
		// setErrorMessage(e.getMessage());
		// }
		// }

		return result;
	}

	public ResultSet getControlDataResultSet(PreparedStatement criteria) {

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
	 * Method getDescription.
	 * 
	 * @return String
	 */
	public String getDescription() {
		return dbDescription;
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

	/**
	 * Method getKeyValue.
	 * 
	 * @return String
	 */
	public String getKeyValue() {
		return dbKeyValue;
	}

	public String getKeyValue(String key) {
		getProperties(key);
		dbKeyValue = JUtility.replaceNullObjectwithBlank(getKeyValue());
		return dbKeyValue;
	}

	public String getKeyValueWithDefault(String key, String dflt, String desc) {
		logger.debug("JDBControl.getKeyValueWithDefault " + key);
		if (getProperties(key)) {
			dbKeyValue = JUtility.replaceNullObjectwithBlank(getKeyValue());
		} else {
			setKeyValue(dflt);
			setDescription(desc);
			create(key, dflt, desc);
			dbKeyValue = dflt;
		}
		return dbKeyValue;
	}

	/**
	 * Method getProperties.
	 * 
	 * @return boolean
	 */
	public boolean getProperties() {

		PreparedStatement stmt;
		ResultSet rs;
		boolean result = false;

		clear();

		// try {
		// stmt = Common.hostList
		// .getHost(getHostID())
		// .getConnection(getSessionID())
		// .prepareStatement(
		// Common.hostList.getHost(getHostID()).getSqlstatements().getSQL("JDBControl.getProperties"));
		// stmt.setFetchSize(1);
		// stmt.setString(1, getSystemKey());
		// rs = stmt.executeQuery();
		// if (rs.next()) {
		// setKeyValue(rs.getString("Key_Value"));
		// setDescription(rs.getString("Description"));
		// result = true;
		// }
		// rs.close();
		// stmt.close();
		// } catch (Exception e) {
		// setErrorMessage(e.getMessage());
		// }

		return result;
	}

	/**
	 * Method getProperties.
	 * 
	 * @param key
	 *            String
	 * @return boolean
	 */
	public boolean getProperties(String key) {
		setSystemKey(key);
		return getProperties();
	}

	public void getPropertiesfromResultSet(ResultSet rs) {
		try {
			clear();
			setSystemKey(rs.getString("system_key"));
			setKeyValue(rs.getString("key_value"));
			setDescription(rs.getString("Description"));

		} catch (SQLException e) {
			setErrorMessage(e.getMessage());
		}
	}

	private String getSessionID() {
		return sessionID;
	}

	/**
	 * Method getSystemKey.
	 * 
	 * @return String
	 */
	public String getSystemKey() {
		return dbSystemKey;
	}

	/**
	 * Method isValidSystemKey.
	 * 
	 * @return boolean
	 */
	public boolean isValidSystemKey() {
		PreparedStatement stmt;
		ResultSet rs;
		boolean result = false;
		// try {
		// stmt = Common.hostList
		// .getHost(getHostID())
		// .getConnection(getSessionID())
		// .prepareStatement(
		// Common.hostList.getHost(getHostID()).getSqlstatements()
		// .getSQL("JDBControl.isValidSystemKey"));
		// stmt.setFetchSize(1);
		// stmt.setString(1, getSystemKey());
		// rs = stmt.executeQuery();
		// if (rs.next()) {
		// result = true;
		// }
		// rs.close();
		// stmt.close();
		// } catch (Exception e) {
		// setErrorMessage(e.getMessage());
		// }

		return result;
	}

	/**
	 * Method lockRecord.
	 * 
	 * @param key
	 *            String
	 * @return boolean
	 */
	public boolean lockRecord(String key) {
		boolean result = false;
		int maxretries = 5;
		int retrycount = 1;
		int timeout = 5;
		setErrorMessage("");
		logger.debug("lockRecord [" + key + "]");
		setSystemKey(key);
		// do {
		// try {
		// PreparedStatement stmtupdate;
		// stmtupdate = Common.hostList
		// .getHost(getHostID())
		// .getConnection(getSessionID())
		// .prepareStatement(
		// Common.hostList.getHost(getHostID()).getSqlstatements().getSQL("JDBControl.lockRecord"));
		// stmtupdate.setString(1, getSystemKey());
		// stmtupdate.setQueryTimeout(timeout);
		// stmtupdate.execute();
		// stmtupdate.clearParameters();
		// stmtupdate.close();
		// result = true;
		// } catch (Exception e) {
		// logger.error("Lock retry.. [" + String.valueOf(retrycount) + " of " +
		// String.valueOf(maxretries) + "]");
		// retrycount++;
		// JWait.manySec(1);
		// }
		// } while ((result == false) & (retrycount <= maxretries));

		if (result == false) {
			logger.error("Cannot Lock System Key [" + key + "]");
			setErrorMessage("Cannot Lock System Key [" + key + "]");
		}

		return result;
	}

	/**
	 * Method setDescription.
	 * 
	 * @param description
	 *            String
	 */
	public void setDescription(String description) {
		dbDescription = description;
	}

	/**
	 * Method setErrorMessage.
	 * 
	 * @param errorMsg
	 *            String
	 */
	private void setErrorMessage(String errorMsg) {
		dbErrorMessage = errorMsg;
	}

	private void setHostID(String host) {
		hostID = host;
	}

	/**
	 * Method setKeyValue.
	 * 
	 * @param keyValue
	 *            String
	 */
	public void setKeyValue(String keyValue) {
		dbKeyValue = keyValue;
	}

	private void setSessionID(String session) {
		sessionID = session;
	}

	/**
	 * Method setSystemKey.
	 * 
	 * @param systemKey
	 *            String
	 */
	public void setSystemKey(String systemKey) {
		dbSystemKey = systemKey;
	}

	/**
	 * Method update.
	 * 
	 * @return boolean
	 */
	public boolean update() {
		boolean result = false;
		// try {
		// PreparedStatement stmtupdate;
		// stmtupdate = Common.hostList
		// .getHost(getHostID())
		// .getConnection(getSessionID())
		// .prepareStatement(
		// Common.hostList.getHost(getHostID()).getSqlstatements().getSQL("JDBControl.update"));
		// stmtupdate.setString(1, getKeyValue());
		// stmtupdate.setString(2, getDescription());
		// stmtupdate.setString(3, getSystemKey());
		// stmtupdate.execute();
		// stmtupdate.clearParameters();
		// if
		// (Common.hostList.getHost(getHostID()).getConnection(getSessionID()).getAutoCommit()
		// == false) {
		// Common.hostList.getHost(getHostID()).getConnection(getSessionID()).commit();
		// }
		// stmtupdate.close();
		// result = true;
		// } catch (Exception e) {
		// setErrorMessage(e.getMessage());
		// result = false;
		// }
		return result;
	}

}

// $codepro.audit.disable numericLiterals
package org.emmanuel.church.mark.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;

import org.apache.log4j.Logger;
import org.emmanuel.church.mark.util.JUtility;

/**
 */
public class JDBCustomer {
	private String dbErrorMessage;
	private String dbPrintOnLabel;
	private String dbCustomerName;
	private String dbCustomerID;
	public static int field_id = 15;
	public static int field_name = 50;
	public static int field_print = 1;
	private final Logger logger = Logger.getLogger(JDBCustomer.class);
	private String hostID;
	private String sessionID;

	public JDBCustomer(String host, String session) {
		setHostID(host);
		setSessionID(session);
	}

	public JDBCustomer(String host, String session, String id, String name, String label) {
		setHostID(host);
		setSessionID(session);
		setID(id);
		setName(name);
		setPrintOnLabel(label);
	}

	public void clear() {
		setName("");
		setPrintOnLabel("Y");
		setErrorMessage("");
	}

	public ResultSet getCustomerDataResultSet() {
		PreparedStatement stmt;
		ResultSet rs = null;
		setErrorMessage("");

		// try {
		// stmt = Common.hostList
		// .getHost(getHostID())
		// .getConnection(getSessionID())
		// .prepareStatement(
		// Common.hostList.getHost(getHostID()).getSqlstatements().getSQL("JDBCustomer.getCustomers"));
		// stmt.setFetchSize(100);
		// rs = stmt.executeQuery();
		//
		// } catch (SQLException e) {
		// setErrorMessage(e.getMessage());
		// }

		return rs;
	}

	public boolean create(String lid, String lname, String printonLabel) {
		boolean result = false;
		setErrorMessage("");

		// try {
		// setID(lid);
		// setName(lname);
		// setPrintOnLabel(printonLabel);
		//
		// if (isValidCustomer() == false) {
		// PreparedStatement stmtupdate;
		// stmtupdate = Common.hostList
		// .getHost(getHostID())
		// .getConnection(getSessionID())
		// .prepareStatement(
		// Common.hostList.getHost(getHostID()).getSqlstatements().getSQL("JDBCustomer.create"));
		// stmtupdate.setString(1, getID());
		// stmtupdate.setString(2, getName());
		// stmtupdate.setString(3, getPrintOnLabel());
		// stmtupdate.execute();
		// stmtupdate.clearParameters();
		// Common.hostList.getHost(getHostID()).getConnection(getSessionID()).commit();
		// stmtupdate.close();
		// result = true;
		// } else {
		// setErrorMessage("Customer already exists");
		// }
		// } catch (SQLException e) {
		// setErrorMessage(e.getMessage());
		// }

		return result;
	}

	public boolean delete() {
		PreparedStatement stmtupdate;
		boolean result = false;
		setErrorMessage("");

		// try {
		// if (getID().equals("SELF") == false) {
		// if (isValidCustomer() == true) {
		// stmtupdate = Common.hostList
		// .getHost(getHostID())
		// .getConnection(getSessionID())
		// .prepareStatement(
		// Common.hostList.getHost(getHostID()).getSqlstatements()
		// .getSQL("JDBCustomer.delete"));
		// stmtupdate.setString(1, getID());
		// stmtupdate.execute();
		// stmtupdate.clearParameters();
		// Common.hostList.getHost(getHostID()).getConnection(getSessionID()).commit();
		// stmtupdate.close();
		// result = true;
		// }
		// } else {
		// setErrorMessage("You cannot delete SELF");
		// }
		// } catch (Exception e) {
		// setErrorMessage(e.getMessage());
		// }

		return result;
	}

	public boolean getCustomerProperties(String cust) {
		setID(cust);
		return getCustomerProperties();
	}

	public boolean getCustomerProperties() {
		boolean result = false;

		PreparedStatement stmt;
		ResultSet rs;
		setErrorMessage("");
		logger.debug("getCustomerProperties");

		clear();

		// try {
		// stmt = Common.hostList
		// .getHost(getHostID())
		// .getConnection(getSessionID())
		// .prepareStatement(
		// Common.hostList.getHost(getHostID()).getSqlstatements()
		// .getSQL("JDBCustomer.getCustomerProperties"));
		// stmt.setString(1, getID());
		// stmt.setFetchSize(1);
		// rs = stmt.executeQuery();
		//
		// if (rs.next()) {
		// setName(rs.getString("customer_name"));
		// setPrintOnLabel(rs.getString("print_on_label"));
		// result = true;
		// rs.close();
		// stmt.close();
		// } else {
		// setErrorMessage("Invalid Customer ID");
		// }
		// } catch (SQLException e) {
		// setErrorMessage(e.getMessage());
		// }
		return result;
	}

	public LinkedList<JDBCustomer> getCustomers() {
		LinkedList<JDBCustomer> typeList = new LinkedList<JDBCustomer>();
		PreparedStatement stmt;
		ResultSet rs;
		setErrorMessage("");

		// try {
		// stmt = Common.hostList
		// .getHost(getHostID())
		// .getConnection(getSessionID())
		// .prepareStatement(
		// Common.hostList.getHost(getHostID()).getSqlstatements().getSQL("JDBCustomer.getCustomers"));
		// stmt.setFetchSize(100);
		// rs = stmt.executeQuery();
		//
		// while (rs.next()) {
		// JDBCustomer mt = new JDBCustomer(getHostID(), getSessionID());
		// mt.setID(rs.getString("customer_id"));
		// mt.setName(rs.getString("customer_name"));
		// mt.setPrintOnLabel(rs.getString("print_on_label"));
		// typeList.add(mt);
		// }
		// rs.close();
		// stmt.close();
		//
		// } catch (SQLException e) {
		// setErrorMessage(e.getMessage());
		// }

		return typeList;
	}

	public String getErrorMessage() {
		return dbErrorMessage;
	}

	private String getHostID() {
		return hostID;
	}

	public String getID() {
		String result = "";
		if (dbCustomerID != null)
			result = dbCustomerID;
		return result;
	}

	public String getName() {
		String result = "";
		if (dbCustomerName != null)
			result = dbCustomerName;
		return result;
	}

	public String getPrintOnLabel() {
		return dbPrintOnLabel;
	}

	private String getSessionID() {
		return sessionID;
	}

	public boolean isValidCustomer(String cust) {
		setID(cust);
		return isValidCustomer();
	}

	public boolean isValidCustomer() {
		PreparedStatement stmt;
		ResultSet rs;
		boolean result = false;

		// try {
		// stmt = Common.hostList
		// .getHost(getHostID())
		// .getConnection(getSessionID())
		// .prepareStatement(
		// Common.hostList.getHost(getHostID()).getSqlstatements()
		// .getSQL("JDBCustomer.isValidCustomer"));
		// stmt.setString(1, getID());
		// stmt.setFetchSize(1);
		// rs = stmt.executeQuery();
		//
		// if (rs.next()) {
		// result = true;
		// } else {
		// setErrorMessage("Invalid Customer [" + getID() + "]");
		// }
		// stmt.close();
		//
		// } catch (SQLException e) {
		// setErrorMessage(e.getMessage());
		// }

		return result;

	}

	public boolean renameTo(String newType) {
		PreparedStatement stmtupdate;
		boolean result = false;
		setErrorMessage("");
		// try {
		// if (isValidCustomer() == true) {
		// JDBCustomer mattype = new JDBCustomer(getHostID(), getSessionID());
		// mattype.setID(newType);
		// if (mattype.isValidCustomer() == false) {
		// stmtupdate = Common.hostList
		// .getHost(getHostID())
		// .getConnection(getSessionID())
		// .prepareStatement(
		// Common.hostList.getHost(getHostID()).getSqlstatements()
		// .getSQL("JDBCustomer.renameTo"));
		// stmtupdate.setString(1, newType);
		// stmtupdate.setString(2, getID());
		// stmtupdate.execute();
		// stmtupdate.clearParameters();
		//
		// Common.hostList.getHost(getHostID()).getConnection(getSessionID()).commit();
		// stmtupdate.close();
		//
		// setID(newType);
		// result = true;
		// } else {
		// setErrorMessage("New Customer ID is already in use.");
		// }
		// }
		// } catch (SQLException e) {
		// setErrorMessage(e.getMessage());
		// }

		return result;
	}

	private void setErrorMessage(String errorMsg) {
		if (errorMsg.isEmpty() == false) {
			logger.error(errorMsg);
		}
		dbErrorMessage = errorMsg;
	}

	private void setHostID(String host) {
		hostID = host;
	}

	public void setID(String type) {
		dbCustomerID = type;
	}

	public void setName(String description) {
		dbCustomerName = description;
	}

	public void setPrintOnLabel(String lprint) {
		dbPrintOnLabel = lprint;
	}

	private void setSessionID(String session) {
		sessionID = session;
	}

	@Override
	public String toString() {
		String result = "";
		if (getID().equals("") == false) {
			result = JUtility.padString(getID(), true, field_id, " ") + " - " + getName();
		} else {
			result = "";
		}

		return result;
	}

	public boolean update() {
		boolean result = false;
		setErrorMessage("");

		// try {
		// if (isValidCustomer() == true) {
		// PreparedStatement stmtupdate;
		// stmtupdate = Common.hostList
		// .getHost(getHostID())
		// .getConnection(getSessionID())
		// .prepareStatement(
		// Common.hostList.getHost(getHostID()).getSqlstatements().getSQL("JDBCustomer.update"));
		// stmtupdate.setString(1, getName());
		// stmtupdate.setString(2, getPrintOnLabel());
		// stmtupdate.setString(3, getID());
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
}

package org.emmanuel.church.mark.ui;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import org.emmanuel.church.mark.util.JUtility;
import org.emmanuel.church.mark.xml.JXMLDocument;
import org.w3c.dom.Document;

public class GenericMessageHeader {
	private String hostRef = "";
	private String hostID = "";
	private String messageRef = "";
	private String messageInformation = "";
	private String messageDate = "";
	private String interfaceType = "";
	private String interfaceDirection = "";
	private final Boolean validHostRef = false;
	private Boolean dbconnected = true;
	private JXMLDocument xmlMessage;
	public static String msgStatusSuccess = "Success";
	public static String msgStatusWarning = "Warning";
	public static String msgStatusError = "Error";

	public static ConcurrentHashMap<String, Integer> interfaceStats = new ConcurrentHashMap<String, Integer>();

	public static synchronized void updateStats(String direction, String type, String result) {
		String key = org.emmanuel.church.mark.util.JUtility.padString(direction, true, 10, " ");
		key = key + org.emmanuel.church.mark.util.JUtility.padString(type, true, 25, " ");
		key = key + org.emmanuel.church.mark.util.JUtility.padString(result, true, 10, " ");

		if (interfaceStats.containsKey(key) == false) {
			interfaceStats.put(key, 0);
		}

		int counter = interfaceStats.get(key);
		counter++;

		interfaceStats.replace(key, counter);
	}

	public static synchronized void clearStats() {
		interfaceStats.clear();
	}

	public static synchronized String getStats() {
		String results = "";

		Iterator<String> iterator = interfaceStats.keySet().iterator();
		String key = "";
		int counter = 0;

		results = "Interface Statistics\n";
		results = "====================\n\n";

		while (iterator.hasNext()) {
			key = iterator.next().toString();
			results = results + key + "   -   " + interfaceStats.get(key).toString() + "\n";
			counter++;
		}

		if (counter == 0)
			results = "No Messages Processed.\n";

		return results;
	}

	public void decodeHeader(JXMLDocument xmltest) {
		dbconnected = false;

		setHostRef(xmltest.findXPath("//message/hostRef"));
		setMessageRef(xmltest.findXPath("//message/messageRef"));
		setMessageInformation(xmltest.findXPath("//message/messageInformation"));
		setInterfaceType(xmltest.findXPath("//message/interfaceType").trim());
		setMessageDate(xmltest.findXPath("//message/messageDate").trim());
		setInterfaceDirection(xmltest.findXPath("//message/interfaceDirection").trim());
		// setHostID(Common.hostList.getHostIDforUniqueId(getHostRef()));

	}

	public String getHostID() {
		return hostID;
	}

	public Document getDocument() {
		return xmlMessage.getDocument();
	}

	public String getInterfaceDirection() {
		return interfaceDirection;
	}

	public String getInterfaceType() {
		return interfaceType;
	}

	public String getMessageDate() {
		return messageDate;
	}

	public Timestamp getMessageDateTimeStamp() {
		return JUtility.getTimeStampFromISOString(getMessageDate());
	}

	public String getMessageInformation() {
		return messageInformation;
	}

	public String getMessageRef() {
		return messageRef;
	}

	public JXMLDocument getXMLDocument() {
		return xmlMessage;
	}

	public Boolean isConnected() {
		return dbconnected;
	}

	public Boolean isValidHostRef() {
		return validHostRef;
	}

	public Boolean readAddressInfo(String filename, String sessionID) {
		Boolean result;
		try {
			xmlMessage = new JXMLDocument();
			result = xmlMessage.setDocument(filename);
			if (result) {
				decodeHeader(xmlMessage);
			}
		} catch (Exception ex) {
			result = false;
		}
		return result;
	}

	private void setHostID(String host) {
		hostID = host;
	}

	private void setHostRef(String hRef) {
		hostRef = hRef;
	}

	private String getHostRef() {
		return hostRef;
	}

	public void setInterfaceDirection(String direction) {
		interfaceDirection = direction;
	}

	public void setInterfaceType(String type) {
		interfaceType = type;
	}

	public void setMessageDate(String mDate) {
		messageDate = mDate;
	}

	public void setMessageInformation(String messageInfo) {
		messageInformation = messageInfo;
	}

	public void setMessageRef(String mRef) {
		messageRef = mRef;
	}

}

package org.emmanuel.church.mark.util;

import java.awt.print.PrinterJob;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.LinkedList;

import javax.print.PrintService;
import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

/**
 */
public class JPrint {

	private static String errorMessage = "";

	/**
	 * Field preferredPrinterQueueName. Value: {@value
	 * preferredPrinterQueueName}
	 */
	private static String preferredPrinterQueueName = new String();
	/**
	 * Field preferredPrinterService. Value: {@value preferredPrinterService}
	 */
	private static PrintService preferredPrinterService;
	/**
	 * Field printerCount. Value: {@value printerCount}
	 */
	private static int printerCount = 0;
	/**
	 * Field printernames. Value: {@value printernames}
	 */
	private static LinkedList<String> printernames = new LinkedList<String>();
	/**
	 * Field printers. Value: {@value printers}
	 */
	private static PrintService[] printers;
	/**
	 * Field printerservices. Value: {@value printerservices}
	 */
	private static LinkedList<PrintService> printerservices = new LinkedList<PrintService>();

	public static String getErrorMessage() {
		return errorMessage;
	}

	private static void setErrorMessage(String errorMsg) {
		errorMessage = errorMsg;
	}

	public static Boolean SocketPrint(String ip, int port, String data) {
		Boolean result = true;
		try {
			Socket clientSocket = new Socket(ip, port);
			DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
			outToServer.writeBytes(data);
			clientSocket.close();
		} catch (Exception ex) {
			result = false;
			setErrorMessage(ex.getMessage());
		}
		return result;
	}

	/**
	 * Method getDefaultPrinterQueueName.
	 * 
	 * @return String
	 */
	public static String getDefaultPrinterQueueName() {
		final Logger logger = Logger.getLogger(JPrint.class);
		String queuename = new String();
		PrintService printService = getDefaultPrinterService();

		if (printService != null) {
			queuename = getPrinterNamebyService(printService);
		}
		logger.debug("getDefaultPrinterQueueName :" + queuename);
		return queuename;
	}

	/**
	 * Method getPrinterShortName.
	 * 
	 * @param queuename
	 *            String
	 * @return String
	 */
	public static String getPrinterShortName(String queuename) {
		String result = queuename;
		String prefix1 = "Win32 Printer : ";
		result = queuename.replace(prefix1, "");
		return result;
	}

	/**
	 * Method getDefaultPrinterService.
	 * 
	 * @return PrintService
	 */
	public static PrintService getDefaultPrinterService() {
		PrinterJob printJob = PrinterJob.getPrinterJob();
		PrintService printService = printJob.getPrintService();

		return printService;
	}

	/**
	 * Method getNumberofPrinters.
	 * 
	 * @return int
	 */
	public static int getNumberofPrinters() {
		return printerCount;
	}

	/**
	 * Method getPreferredPrinterQueueName.
	 * 
	 * @return String
	 */
	public static String getPreferredPrinterQueueName() {
		if (preferredPrinterQueueName == null) {
			preferredPrinterQueueName = "";
		}

		if (preferredPrinterQueueName.equals("")) {
			preferredPrinterQueueName = getDefaultPrinterQueueName();
			preferredPrinterService = getDefaultPrinterService();
		}

		return preferredPrinterQueueName;
	}

	/**
	 * Method getPreferredPrinterService.
	 * 
	 * @return PrintService
	 */
	public static PrintService getPreferredPrinterService() {
		return preferredPrinterService;
	}

	/**
	 * Method getPrinterNamebyService.
	 * 
	 * @param printService
	 *            PrintService
	 * @return String
	 */
	public static String getPrinterNamebyService(PrintService printService) {
		String queuename = new String();
		if (printerCount > 0) {
			queuename = printService.toString();
			// + " : " +
			// printService.getAttribute(javax.print.attribute.standard.PrinterName.class).toString();
		} else {
			queuename = "";
		}
		return queuename;
	}

	/**
	 * Method getPrinterNames.
	 * 
	 * @return LinkedList<String>
	 */
	public static LinkedList<String> getPrinterNames() {
		return printernames;
	}

	/**
	 * Method getPrinterServicebyName.
	 * 
	 * @param queuename
	 *            String
	 * @return PrintService
	 */
	public static PrintService getPrinterServicebyName(String queuename) {
		PrintService printservice = null;

		if (printerCount > 0) {
			int idx = getPrintQueueIndex(queuename);

			if (idx >= 0) {
				printservice = printerservices.get(idx);
			}
		}

		return printservice;
	}

	/**
	 * Method getPrinterServices.
	 * 
	 * @return LinkedList<PrintService>
	 */
	public static LinkedList<PrintService> getPrinterServices() {
		return printerservices;
	}

	/**
	 * Method getPrintQueueIndex.
	 * 
	 * @param queuename
	 *            String
	 * @return int
	 */
	public static int getPrintQueueIndex(String queuename) {
		int result = -1;

		if (printerCount > 0) {
			result = printernames.indexOf(queuename);
		}

		return result;
	}

	public static void init() {
		refresh();
	}

	/**
	 * Method isValidPrintQueueName.
	 * 
	 * @param queuename
	 *            String
	 * @return boolean
	 */
	public static boolean isValidPrintQueueName(String queuename) {
		boolean result = false;

		if (printerCount > 0) {
			if (getPrintQueueIndex(queuename) >= 0) {
				result = true;
			}
		}

		return result;
	}

	/**
	 * Method isValidPrintService.
	 * 
	 * @param printService
	 *            PrintService
	 * @return boolean
	 */
	public static boolean isValidPrintService(PrintService printService) {
		boolean result = false;

		if (printerCount > 0) {
			int idx = printerservices.indexOf(printService);

			if (idx >= 0) {
				result = true;
			}
		}
		return result;
	}

	public static void refresh() {
		final Logger logger = Logger.getLogger(JPrint.class);
		printers = PrinterJob.lookupPrintServices();
		printerCount = printers.length;

		printernames.clear();
		printerservices.clear();

		if (printerCount > 0) {
			for (int i = printerCount - 1; i >= 0; i--)
			// for (int i = 0; i < printerCount; i++)
			{
				if (printers[i].toString().contains("Microsoft XPS Document Writer") == false) {
					printernames.addLast(printers[i].toString());
					logger.debug("refresh : " + printers[i].toString());
					printerservices.addLast(printers[i]);
				} else {
					printerCount--;
				}
			}
		}
	}

	/**
	 * Method setPreferredPrinterQueueName.
	 * 
	 * @param queuename
	 *            String
	 */
	public static void setPreferredPrinterQueueName(String queuename) {
		if (isValidPrintQueueName(queuename) == true) {
			preferredPrinterQueueName = queuename;
			preferredPrinterService = getPrinterServicebyName(queuename);
			setOSDefaultPrinter();
			for (int x = 0; x < 10; x++) {
				if (JPrint.getDefaultPrinterQueueName().equals(queuename)) {
					break;
				} else {
					JWait.oneSec();
				}
			}
		}
	}

	/**
	 * Method setPreferredPrinterService.
	 * 
	 * @param printService
	 *            PrintService
	 */
	public static void setPreferredPrinterService(PrintService printService) {
		if (isValidPrintService(printService) == true) {
			preferredPrinterService = printService;
			preferredPrinterQueueName = getPrinterNamebyService(printService);
		}
	}

	public static void setOSDefaultPrinter() {
		String param = new String();
		Integer start = 0;

		if (preferredPrinterQueueName.startsWith("Win32")) {
			try {
				start = preferredPrinterQueueName.indexOf(":");
				start = start + 2;
				param = preferredPrinterQueueName.substring(start);

				// Process ls_proc =
				Runtime.getRuntime().exec("RUNDLL32 PRINTUI.DLL,PrintUIEntry /y /n \"" + param + "\"");
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
			}
		}

		if (preferredPrinterQueueName.startsWith("IPP")) {
			try {
				start = preferredPrinterQueueName.indexOf(":");
				start = start + 2;
				param = preferredPrinterQueueName.substring(start);
				param = JCUPSPrinterAttributes.getName(param);

				// Process ls_proc =
				Runtime.getRuntime().exec("lpoptions -d " + param);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
			}
		}

		refresh();

	}

	public JPrint() {
		init();
	}
}

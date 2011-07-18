/*
 * Created on 12-Feb-2005
 *
 */
package org.emmanuel.church.mark.sys;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.print.attribute.PrintServiceAttributeSet;
import javax.swing.Icon;
import javax.swing.JOptionPane;

import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;

import org.emmanuel.church.mark.db.JDBControl;
import org.emmanuel.church.mark.db.JDBModule;
import org.emmanuel.church.mark.util.JPrint;
import org.emmanuel.church.mark.util.JUtility;

public class JLaunchReport {

	public static boolean silentExceptions = false;
	public static JDBModule mod = new JDBModule(Common.selectedHostID, Common.sessionID);
	public static Map<String, String> stdparams = new HashMap<String, String>();

	public static void init() {
		JDBControl ctrl = new JDBControl(Common.selectedHostID, Common.sessionID);
		ctrl.getProperties("COMPANY NAME");
		stdparams.put("COMPANY_NAME", ctrl.getKeyValue());
		ctrl.getProperties("PLANT");
		stdparams.put("PLANT", ctrl.getKeyValue());
		stdparams.put("SUBREPORT_DIR", System.getProperty("user.dir") + File.separator + "reports" + File.separator);
		System.getProperty("user.dir");
		ctrl.getProperties("LABEL_HEADER_COMMENT");

	}

	public static void runReport(String moduleId, HashMap<String, Object> parameterValues, String sql,
			PreparedStatement preparedstatement, String printQueue) {
		ResultSet resultset;
		Statement statement;
		JRResultSetDataSource jasperresultset;
		JasperPrint jasperPrint = new JasperPrint();
		JasperReport jasperReport;
		Connection connection;
		JInternalFrameReportViewer reportviewer;
		Icon reportIcon;
		JRPrintServiceExporter exporter;
		PrintServiceAttributeSet serviceAttributeSet;

		mod.setModuleId(moduleId);

		if (mod.getModuleProperties() == true) {

			if (parameterValues == null) {
				parameterValues = new HashMap<String, Object>();
			}
			parameterValues.putAll(stdparams);

			printQueue = JUtility.replaceNullStringwithBlank(printQueue);

			if (printQueue.equals("")) {
				printQueue = JPrint.getDefaultPrinterQueueName();
			} else {
				if (JPrint.getDefaultPrinterQueueName().equals(printQueue) == false) {
					JPrint.setPreferredPrinterQueueName(printQueue);
				}
			}

			// try {
			// jasperReport = (JasperReport)
			// JRLoader.loadObjectFromLocation(Common.report_path
			// + JUtility.removeExtensionFromFilename(mod.getReportFilename(),
			// ".jrxml") + ".jasper");
			// connection =
			// Common.hostList.getHost(Common.selectedHostID).getConnection(Common.sessionID);
			//
			// sql = JUtility.replaceNullStringwithBlank(sql);
			//
			// if (sql.isEmpty()) {
			// if (preparedstatement == null) {
			// parameterValues.put(JRParameter.REPORT_CONNECTION, connection);
			// jasperPrint = JasperFillManager.fillReport(jasperReport,
			// parameterValues, connection);
			// } else {
			// resultset = preparedstatement.executeQuery();
			// jasperresultset = new JRResultSetDataSource(resultset);
			// parameterValues.put(JRParameter.REPORT_DATA_SOURCE,
			// jasperresultset);
			// parameterValues.put(JRParameter.REPORT_CONNECTION, connection);
			// jasperPrint = JasperFillManager.fillReport(jasperReport,
			// parameterValues, jasperresultset);
			// }
			// } else {
			// statement =
			// Common.hostList.getHost(Common.selectedHostID).getConnection(Common.sessionID)
			// .createStatement();
			// statement.setFetchSize(1);
			// resultset = statement.executeQuery(sql);
			// jasperresultset = new JRResultSetDataSource(resultset);
			// parameterValues.put(JRParameter.REPORT_DATA_SOURCE,
			// jasperresultset);
			// parameterValues.put(JRParameter.REPORT_CONNECTION, connection);
			// jasperPrint = JasperFillManager.fillReport(jasperReport,
			// parameterValues, jasperresultset);
			// }
			//
			// if (mod.isPrintPreview() == true) {
			// reportviewer = new JInternalFrameReportViewer(jasperPrint,
			// false);
			// reportIcon =
			// Common.imageIconloader.getImageIcon(Common.image_report);
			// reportviewer.setFrameIcon(reportIcon);
			// reportviewer.setLocation(JLaunchMenu.getNextWindowPosition().x,
			// JLaunchMenu.getNextWindowPosition().y);
			// Common.mainForm.desktopPane.add(reportviewer);
			// reportviewer.setVisible(true);
			// reportviewer.setSelected(true);
			//
			// } else {
			// exporter = new JRPrintServiceExporter();
			// exporter.setParameter(JRPrintServiceExporterParameter.JASPER_PRINT,
			// jasperPrint);
			// serviceAttributeSet = new HashPrintServiceAttributeSet();
			// serviceAttributeSet.add(new
			// PrinterName(JPrint.getPrinterShortName(JPrint
			// .getPreferredPrinterQueueName()), null));
			// exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET,
			// serviceAttributeSet);
			// exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG,
			// Boolean.FALSE);
			// exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG,
			// mod.isPrintDialog());
			// PrintRequestAttributeSet printRequestAttributeSet = new
			// HashPrintRequestAttributeSet();
			// printRequestAttributeSet.add(new Copies(mod.getPrintCopies()));
			// exporter.setParameter(JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET,
			// printRequestAttributeSet);
			// exporter.exportReport();
			// }
			//
			// } catch (Exception e) {
			// reportError(e.getMessage());
			// }

			try {
				jasperresultset = null;
				preparedstatement = null;
				resultset = null;
				connection = null;
				reportviewer = null;
				jasperReport = null;
				jasperPrint = null;

			} catch (Exception e) {
			}

		} else {
			reportError("Cannot find module [" + moduleId + "]");
		}
	}

	public static void runReport(String ModuleId, PreparedStatement ps, boolean preview, String printQueue,
			int labelCopies, boolean incHeaderText) {
		mod.setModuleId(ModuleId);
		if (mod.getModuleProperties() == true) {
			if (mod.getReportType().equals("Standard")) {

				runReport(ModuleId, null, "", ps, printQueue);
			} else {
				// JLabelPrint lp = new JLabelPrint(printQueue,
				// mod.getReportFilename(), ps);
				// lp.print(labelCopies, incHeaderText);
			}

		} else {
			reportError("Cannot find module [" + ModuleId + "]");

		}
	}

	public static void reportError(String errorMessage) {
		if (silentExceptions == false) {
			JOptionPane.showMessageDialog(Common.mainForm, errorMessage, "Report Error", JOptionPane.ERROR_MESSAGE);
		}
	}

}

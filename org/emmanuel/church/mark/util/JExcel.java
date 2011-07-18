package org.emmanuel.church.mark.util;

import java.awt.Component;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.JFileChooser;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class JExcel {

	private String dbErrorMessage;
	private final Logger logger = Logger.getLogger(JExcel.class);

	public void saveAs(String defaultFilename, ResultSet rs, Component parent) {

		JFileChooser saveXLS = new JFileChooser();

		try {
			File f = new File(new File(System.getProperty("user.home")).getCanonicalPath());
			saveXLS.setCurrentDirectory(f);
			saveXLS.addChoosableFileFilter(new JFileFilterXLS());
			saveXLS.setSelectedFile(new File(defaultFilename));
		} catch (Exception ex) {
		}

		int result = saveXLS.showSaveDialog(parent);
		if (result == 0) {
			File selectedFile;
			selectedFile = saveXLS.getSelectedFile();
			if (selectedFile != null) {
				String filename = selectedFile.getAbsolutePath();
				JExcel export = new JExcel();
				export.exportToExcel(filename, rs);
			}
		}
	}

	public void exportToExcel(String filename, String sqlText, Connection connection) {
		PreparedStatement statement;
		try {
			statement = connection.prepareStatement(sqlText);
			exportToExcel(filename, statement);
			statement.close();
		} catch (SQLException e) {
			setErrorMessage(e.getMessage());
		}
	}

	public void exportToExcel(String filename, PreparedStatement statement) {
		ResultSet rs;
		try {
			rs = statement.executeQuery();
			exportToExcel(filename, rs);
			rs.close();
		} catch (SQLException e) {
			setErrorMessage(e.getMessage());
		}
	}

	@SuppressWarnings("deprecation")
	public void exportToExcel(String filename, ResultSet rs) {
		try {

			ResultSetMetaData rsmd = rs.getMetaData();
			int numberOfColumns = rsmd.getColumnCount();
			int columnType = 0;
			String columnTypeName = "";
			long recordNumber = 0;

			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet();

			HSSFCellStyle cellStyle_varchar = workbook.createCellStyle();
			cellStyle_varchar.setAlignment(HSSFCellStyle.ALIGN_LEFT);

			HSSFCellStyle cellStyle_title = workbook.createCellStyle();
			cellStyle_title.setAlignment(HSSFCellStyle.ALIGN_CENTER);

			HSSFCellStyle cellStyle_char = workbook.createCellStyle();
			cellStyle_char.setAlignment(HSSFCellStyle.ALIGN_LEFT);

			HSSFCellStyle cellStyle_date = workbook.createCellStyle();
			cellStyle_date.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			cellStyle_date.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));

			HSSFCellStyle cellStyle_timestamp = workbook.createCellStyle();
			cellStyle_timestamp.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			cellStyle_timestamp.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));

			HSSFCellStyle cellStyle_decimal = workbook.createCellStyle();
			cellStyle_decimal.setAlignment(HSSFCellStyle.ALIGN_RIGHT);

			HSSFFont font_title = workbook.createFont();
			font_title.setColor((short) 0xc);
			font_title.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			font_title.setItalic(true);
			font_title.setUnderline(HSSFFont.U_DOUBLE);
			cellStyle_title.setFont(font_title);

			HSSFCell cell;
			HSSFRow row;

			// rs.beforeFirst();

			while (rs.next()) {
				recordNumber++;

				if (recordNumber == 1) {
					row = sheet.createRow((short) 0);
					for (int column = 1; column <= numberOfColumns; column++) {
						cell = row.createCell((short) (column - 1));
						String columnName = rsmd.getColumnLabel(column);
						columnName = columnName.replace("_", " ");
						columnName = JUtility.capitalize(columnName);
						cell.setCellStyle(cellStyle_title);
						cell.setCellValue(columnName);
					}
				}

				row = sheet.createRow((short) recordNumber);

				for (int column = 1; column <= numberOfColumns; column++) {

					columnType = rsmd.getColumnType(column);
					columnTypeName = rsmd.getColumnTypeName(column);

					cell = row.createCell((short) (column - 1));

					try {
						switch (columnType) {
						case java.sql.Types.VARCHAR:
							HSSFRichTextString rtf_varchar = new HSSFRichTextString(rs.getString(column));
							cell.setCellStyle(cellStyle_varchar);
							cell.setCellValue(rtf_varchar);
							break;
						case java.sql.Types.CHAR:
							HSSFRichTextString rtf_char = new HSSFRichTextString(rs.getString(column));
							cell.setCellStyle(cellStyle_char);
							cell.setCellValue(rtf_char);
							break;
						case java.sql.Types.DATE:
							try {
								cell.setCellValue(rs.getTimestamp(column));
								cell.setCellStyle(cellStyle_date);
							} catch (Exception ex) {

							}
							break;
						case java.sql.Types.TIMESTAMP:
							try {
								cell.setCellValue(rs.getTimestamp(column));
								cell.setCellStyle(cellStyle_timestamp);
							} catch (Exception ex) {

							}
							break;
						case java.sql.Types.DECIMAL:
							HSSFRichTextString rtf_decimal = new HSSFRichTextString(rs.getBigDecimal(column).toString());
							cell.setCellStyle(cellStyle_decimal);
							cell.setCellValue(rtf_decimal);
							break;
						case java.sql.Types.NUMERIC:
							HSSFRichTextString rtf_decimaln = new HSSFRichTextString(rs.getBigDecimal(column)
									.toString());
							cell.setCellStyle(cellStyle_decimal);
							cell.setCellValue(rtf_decimaln);
							break;
						case java.sql.Types.INTEGER:
							HSSFRichTextString rtf_int = new HSSFRichTextString(String.valueOf(rs.getInt(column)));
							cell.setCellStyle(cellStyle_decimal);
							cell.setCellValue(rtf_int);
							break;
						case java.sql.Types.FLOAT:
							HSSFRichTextString rtf_float = new HSSFRichTextString(String.valueOf(rs.getFloat(column)));
							cell.setCellStyle(cellStyle_decimal);
							cell.setCellValue(rtf_float);
							break;
						case java.sql.Types.DOUBLE:
							HSSFRichTextString rtf_double = new HSSFRichTextString(String.valueOf(rs.getDouble(column)));
							cell.setCellStyle(cellStyle_decimal);
							cell.setCellValue(rtf_double);
							break;
						default:
							cell.setCellValue(new HSSFRichTextString(columnTypeName));
							break;
						}
					} catch (Exception ex) {
						String errormessage = ex.getLocalizedMessage();
						HSSFRichTextString rtf_exception = new HSSFRichTextString(errormessage);
						cell.setCellStyle(cellStyle_varchar);
						cell.setCellValue(rtf_exception);
						break;
					}
				}
			}

			for (int column = 1; column <= numberOfColumns; column++) {
				sheet.autoSizeColumn((short) (column - 1));
			}

			if (recordNumber > 0) {
				try {
					FileOutputStream fileOut = new FileOutputStream(filename.toLowerCase());
					workbook.write(fileOut);
					fileOut.close();
				} catch (Exception ex) {
					setErrorMessage(ex.getMessage());
				}
			}

		} catch (SQLException e) {
			setErrorMessage(e.getMessage());
		}
	}

	private void setErrorMessage(String errorMsg) {
		if (errorMsg.isEmpty() == false) {
			logger.error(errorMsg);
		}
		dbErrorMessage = errorMsg;
	}

	public String getErrorMessage() {
		return dbErrorMessage;
	}

}

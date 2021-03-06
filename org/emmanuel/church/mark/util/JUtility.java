/*
 * Created on 14-Aug-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package org.emmanuel.church.mark.util;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.plaf.metal.DefaultMetalTheme;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.OceanTheme;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.emmanuel.church.mark.sys.Common;

/**
 */
public class JUtility {
	/**
	 * Field field_timestamp. Value: {@value field_timestamp}
	 */
	public static int field_timestamp = 20;

	public static BigDecimal stringToBigDecimal(String str) {
		BigDecimal result;

		NumberFormat nf = NumberFormat.getInstance();

		try {
			Number myNumber = nf.parse(str);
			Double dbl = myNumber.doubleValue();
			str = String.valueOf(dbl);
		} catch (ParseException e) {
			final Logger logger = Logger.getLogger(JUtility.class);
			logger.error(e.getMessage());
			str = "";
		}

		result = new BigDecimal(str);
		return result;
	}

	public static String bigDecimaltoString(BigDecimal bd) {
		String result = "";
		NumberFormat nf1 = NumberFormat.getInstance();

		nf1 = NumberFormat.getInstance();
		nf1.setMinimumFractionDigits(3);
		nf1.setMaximumFractionDigits(3);
		result = nf1.format(bd);

		return result;
	}

	public static void runExternalProgram(String filename) {
		try {

			Runtime.getRuntime().exec(filename);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
		}

	}

	public static void scrolltoHomePosition(JScrollPane jScrollPane1) {
		JScrollBar verticalScrollBar = jScrollPane1.getVerticalScrollBar();
		JScrollBar horizontalScrollBar = jScrollPane1.getHorizontalScrollBar();
		verticalScrollBar.setValue(verticalScrollBar.getMinimum());
		horizontalScrollBar.setValue(horizontalScrollBar.getMinimum());
	}

	public static String conditionalHeading(String useParam1, String Param1, String Param2) {
		String result = "";
		if (useParam1.equals("Y")) {
			result = Param1;
		} else {
			result = Param2;
		}
		return result;
	}

	public static boolean isStringPatternValid(String regex, String input) {
		boolean result = true;
		String regex2 = JUtility.replaceNullStringwithBlank(regex);
		String input2 = JUtility.replaceNullStringwithBlank(input);

		if ((regex2.equals("") == false) && (input2.equals("") == false)) {
			Pattern pat = Pattern.compile(regex);

			Matcher mat = pat.matcher(input);

			result = mat.matches();
		}

		return result;
	}

	public static int countOccurrences(String arg1, String arg2) {
		int count = 0;
		int index = 0;

		while ((index = arg1.indexOf(arg2, index)) != -1) {
			++index;
			++count;
		}

		return count;
	}

	public static String addtoSQL(String sql, String field, String delimiter, String comparator, String value) {
		String result = sql;
		boolean first = false;

		if ((field != null) && (value != null)) {
			if ((field.equals("") == false) && (value.equals("") == false)) {
				if (sql.equals("")) {
					sql = "where ";
					first = true;
				} else {
					first = false;
				}

				if (first == false) {
					sql = sql + " and ";
				}

				sql = sql + field + " " + comparator + " " + delimiter + value + delimiter;
				result = sql;
			}
		}

		return result;
	}

	/**
	 * Method capitaliseAll.
	 * 
	 * @param str
	 *            String
	 * @return String
	 */
	public static String capitaliseAll(String str) {
		String result = "";
		char ch; // One of the characters in str.
		char prevCh; // The character that comes before ch in the string.
		int i; // A position in str, from 0 to str.length()-1.

		if (str != null) {
			prevCh = '.'; // Prime the loop with any non-letter character.

			for (i = 0; i < str.length(); i++) {
				ch = str.charAt(i);

				if (Character.isLetter(ch) && !Character.isLetter(prevCh)) {
					result = result + Character.toUpperCase(ch);
				} else {
					result = result + ch;
				}

				prevCh = ch; // prevCh for next iteration is ch.
			}
		}

		return result;
	}

	static String capitalize(String input) {
		String str = input.toLowerCase();
		String result = "";
		char ch;
		char prevCh;
		int i;
		prevCh = '.';

		for (i = 0; i < str.length(); i++) {
			ch = str.charAt(i);

			if (Character.isLetter(ch) && !Character.isLetter(prevCh)) {
				result = result + Character.toUpperCase(ch);
			} else {
				result = result + ch;
			}

			prevCh = ch;
		}

		return result;
	}

	/**
	 * Method differenceInDays.
	 * 
	 * @param date1
	 *            Calendar
	 * @param date2
	 *            Calendar
	 * @return long
	 */
	public static long differenceInDays(Calendar date1, Calendar date2) {
		final long msPerDay = 1000 * 60 * 60 * 24;

		final long date1Milliseconds = date1.getTime().getTime();
		final long date2Milliseconds = date2.getTime().getTime();
		final long result = (date1Milliseconds - date2Milliseconds) / msPerDay;

		return result;
	}

	public static void errorBeep() {
		JPlaySound s = new JPlaySound("audio/error.wav");

		if (s.equals(s)) {
			s = null;
		}
	}

	/**
	 * Method exec.
	 * 
	 * @param command
	 *            String
	 * @param dir
	 *            String
	 */
	public static void exec(String command, String dir) {
		try {
			if (dir != null) {
				final File working = new File(dir);
				Runtime.getRuntime().exec(command, null, working);
			} else {
				Runtime.getRuntime().exec(command);
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public static String formatNumber(String No, String fmt) {
		String result = "error";
		int SeqNumber = 0;
		int SeqStart = 0;
		int SeqEnd = 0;
		int SeqLength = 0;
		boolean retry = true;

		SeqStart = fmt.indexOf("{");
		SeqEnd = fmt.indexOf("}");
		SeqLength = SeqEnd - SeqStart;
		SeqLength--;

		do {
			SeqNumber = Integer.parseInt(No);
			SeqNumber++;

			No = JUtility.padString(No, false, SeqLength, "0");

			if (SeqStart > 0) {
				No = fmt.substring(0, SeqStart) + No;
			}

			if ((SeqEnd + 1) < fmt.length()) {
				No = No + fmt.substring(SeqEnd + 1, fmt.length());
			}

			retry = false;
			result = No;
		} while (retry);

		return result;
	}

	public static String getBasePath() {
		String basepath = "";

		try {
			basepath = new File("").getCanonicalPath();
		} catch (Exception ex) {
			basepath = "error";
		}

		return basepath;
	}

	/**
	 * Method getClientName.
	 * 
	 * @return String
	 */
	public static String getClientName() {
		String clientname = "";

		try {
			clientname = System.getenv("Clientname").toString();

			if (clientname.equals("Console")) {
				clientname = "unknown";
			}
		} catch (Exception e) {
			clientname = "unknown";
		}

		if (clientname.equals("unknown")) {
			try {
				clientname = InetAddress.getLocalHost().getHostName().toLowerCase();
			} catch (Exception e) {
				clientname = "unknown";
			}
		}

		return clientname;
	}

	public static String getDefaultValue(String newValue, String oldValue, String defaultValue) {
		String result = replaceNullStringwithBlank(newValue);

		if (newValue.length() == 0) {
			if (replaceNullStringwithBlank(oldValue).length() > 0) {
				result = replaceNullStringwithBlank(oldValue);
			} else {
				result = replaceNullStringwithBlank(defaultValue);
			}
		}

		return result;
	}

	/**
	 * Method getFormattedEAN.
	 * 
	 * @param ean
	 *            String
	 * @return String
	 */
	public static String getFormattedEAN(String ean) {
		String result = replaceNullObjectwithBlank(ean);

		result = padString(result, false, 14, "0");

		return result;
	}

	public static String getFormattedVariant(String variant) {
		String result = replaceNullObjectwithBlank(variant);

		result = padString(result, false, 2, "0");

		return result;
	}

	/**
	 * Method getFormattedQuantity.
	 * 
	 * @param qty
	 *            String
	 * @param len
	 *            int
	 * @param pad
	 *            String
	 * @return String
	 */
	public static String getFormattedQuantity(String qty, int len, String pad) {
		String result = replaceNullObjectwithBlank(qty);
		result = result.trim();

		if (result.length() > 0) {
			String temp = "";
			Boolean copy = false;

			for (int i = result.length() - 1; i >= 0; i--) {
				if ((result.charAt(i) != '0') & (result.charAt(i) != '.')) {
					copy = true;
				}

				if (copy) {
					temp = result.charAt(i) + temp;
				}
			}

			result = temp;
		}

		result = padString(result, false, len, pad);

		return result;
	}

	/**
	 * Method getFormattedSSCC.
	 * 
	 * @param sscc
	 *            String
	 * @return String
	 */
	public static String getFormattedSSCC(String sscc) {
		String result = replaceNullObjectwithBlank(sscc);

		if (result.length() == 18) {
			result = result.substring(0, 3) + " " + result.substring(3, 8) + " " + result.substring(8, 13) + " "
					+ result.substring(13, 18);
		}

		return result;
	}

	/**
	 * Method getTimeStampStringFormat.
	 * 
	 * @param ts
	 *            Timestamp
	 * @param fmt
	 *            String
	 * @return String
	 */
	public static String getISOTimeStampStringFormat(Timestamp ts) {
		String result = "";

		try {
			String temp = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(ts);
			// String temp = ts.toString(); 0123456789012345678
			result = temp.substring(0, 4);
			result = result + "-";
			result = result + temp.substring(5, 7);
			result = result + "-";
			result = result + temp.substring(8, 10);
			result = result + "T";
			result = result + temp.substring(11, 13);
			result = result + ":";
			result = result + temp.substring(14, 16);
			result = result + ":";
			result = result + temp.substring(17, 19);
		} catch (Exception ex) {
			result = "Error";
		}

		return result;
	}

	/**
	 * Method getJulianDay.
	 * 
	 * @param currentDate
	 *            Calendar
	 * @return long
	 */
	public static long getJulianDay(Calendar currentDate) {
		long result = 0;

		result = currentDate.get(Calendar.DAY_OF_YEAR);

		return result;
	}

	/**
	 * Method getSQLDate.
	 * 
	 * @return java.sql.Date
	 */
	public static java.sql.Date getSQLDate() {
		Calendar caldate = Calendar.getInstance();
		int day = caldate.get(Calendar.DATE);
		int month = caldate.get(Calendar.MONTH);
		int year = caldate.get(Calendar.YEAR);
		java.sql.Date sqldate = getSQLDate(year, month, day);

		return sqldate;
	}

	/**
	 * Method getSQLDate.
	 * 
	 * @param caldate
	 *            Calendar
	 * @return java.sql.Date
	 */
	public static java.sql.Date getSQLDate(Calendar caldate) {
		java.sql.Date sqldate = new java.sql.Date(caldate.getTimeInMillis());

		return sqldate;
	}

	/**
	 * Method getSQLDate.
	 * 
	 * @param date
	 *            Date
	 * @return java.sql.Date
	 */
	public static java.sql.Date getSQLDate(Date date) {
		Calendar caldate = Calendar.getInstance();
		caldate.setTime(date);

		java.sql.Date sqldate = new java.sql.Date(caldate.getTimeInMillis());

		return sqldate;
	}

	/**
	 * Method getSQLDate.
	 * 
	 * @param yyyy
	 *            int
	 * @param mm
	 *            int
	 * @param dd
	 *            int
	 * @return java.sql.Date
	 */
	public static java.sql.Date getSQLDate(int yyyy, int mm, int dd) {
		Calendar caldate = Calendar.getInstance();
		caldate.set(yyyy, mm, dd, 0, 0, 0);

		java.sql.Date sqldate = new java.sql.Date(caldate.getTimeInMillis());

		return sqldate;
	}

	/**
	 * Method getSQLDateTime.
	 * 
	 * @return Timestamp
	 */
	public static Timestamp getSQLDateTime() {
		Calendar caldate = Calendar.getInstance();
		Timestamp t = new Timestamp(caldate.getTimeInMillis());
		t.setTime(caldate.getTimeInMillis());

		return t;
	}

	/**
	 * Method getTimestampFromDate.
	 * 
	 * @param d
	 *            Date
	 * @return Timestamp
	 */
	public static Timestamp getTimestampFromDate(Date d) {
		Calendar caldate = Calendar.getInstance();
		caldate.setTime(d);

		Timestamp t = new Timestamp(caldate.getTimeInMillis());
		t.setTime(caldate.getTimeInMillis());

		return t;
	}

	public static Timestamp getTimeStampFromISOString(String isoString) {
		Timestamp result;

		// 2010-05-31T10:14:49
		// 0123456789111111111
		// 012345678
		try {
			Calendar caldate = Calendar.getInstance();

			int year = 0;
			int month = 0;
			int day = 0;
			int hour = 0;
			int min = 0;
			int second = 0;

			year = Integer.valueOf(isoString.substring(0, 4));
			month = Integer.valueOf(isoString.substring(5, 7));
			day = Integer.valueOf(isoString.substring(8, 10));
			hour = Integer.valueOf(isoString.substring(11, 13));
			min = Integer.valueOf(isoString.substring(14, 16));
			second = Integer.valueOf(isoString.substring(17, 19));

			caldate.set(year, month - 1, day, hour, min, second);

			result = new Timestamp(caldate.getTimeInMillis());

			result.setNanos(0); // or other value
		} catch (Exception ex) {
			result = null;
		}

		return result;
	}

	public static String getTimeStampStringFormat(Timestamp ts, String fmt) {
		String result = "";
		LinkedList<String> fmtList = new LinkedList<String>();
		LinkedList<String> valList = new LinkedList<String>();
		fmtList.clear();
		valList.clear();

		result = ts.toString();

		fmtList.add("yyyy");
		valList.add(result.substring(0, 4));

		fmtList.add("yy");
		valList.add(result.substring(2, 4));

		fmtList.add("mm");
		valList.add(result.substring(5, 7));

		fmtList.add("dd");
		valList.add(result.substring(8, 10));

		fmtList.add("hh");
		valList.add(result.substring(11, 13));

		fmtList.add("mi");
		valList.add(result.substring(14, 16));

		fmtList.add("ss");
		valList.add(result.substring(17, 19));

		fmtList.add("yymmdd");
		valList.add(result.substring(2, 4) + result.substring(5, 7) + result.substring(8, 10));

		int pos = fmtList.indexOf(fmt);

		if (pos >= 0) {
			result = valList.get(pos);
		} else {
			result = "";
		}

		return result;
	}

	/**
	 * Method initLogging.
	 * 
	 * @param filename
	 *            String
	 */
	public static void initLogging(String filename) {
		if (filename.isEmpty()) {
			// filename = System.getProperty("user.dir") + File.separator +
			// "xml" + File.separator + "log" + File.separator + "log4j.xml";
			filename = "." + File.separator + "xml" + File.separator + "log" + File.separator + "log4j.xml";
		}

		DOMConfigurator.configure(filename);

		// BasicConfigurator.configure();
	}

	/**
	 * Method isNullORBlank.
	 * 
	 * @param value
	 *            String
	 * @return boolean
	 */
	public static boolean isNullORBlank(String value) {
		boolean result = false;

		if (value == null) {
			result = true;
		} else {
			if (value.equals("") == true) {
				result = true;
			}
		}

		return result;
	}

	/**
	 * Method isValidJavaVersion.
	 * 
	 * @param minVersion
	 *            String
	 * @return boolean
	 */
	public static boolean isValidJavaVersion(String minVersion) {
		boolean result = false;

		String current = System.getProperty("java.version");

		int comp = current.compareTo(minVersion);

		if (comp < 0) {
			result = false;
		} else {
			result = true;
		}

		return result;
	}

	/**
	 * Method loadListFromTextFile.
	 * 
	 * @param filename
	 *            String
	 * @param defaultitem
	 *            String
	 * @param linePrefix
	 *            String
	 * @param lineSuffix
	 *            String
	 * @return String
	 */
	public synchronized static String loadListFromTextFile(String filename, String defaultitem, String linePrefix,
			String lineSuffix) {
		String result = "";

		try {
			BufferedReader in = new BufferedReader(new FileReader(filename));
			String str;

			if (defaultitem != null) {
				result = result + linePrefix + defaultitem + lineSuffix;
			}

			while ((str = in.readLine()) != null) {
				if (str.equalsIgnoreCase(defaultitem) == false) {
					result = result + linePrefix + str + lineSuffix;
				}
			}

			in.close();
		} catch (IOException e) {
		}

		return result;
	}

	public static String now() {
		final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);

		return sdf.format(cal.getTime());
	}

	/**
	 * Method padSpace.
	 * 
	 * @param size
	 *            int
	 * @return String
	 */
	public static String padSpace(int size) {
		String s = "";

		for (int i = 0; i < size; i++) {
			s = s + " ";
		}

		return s;
	}

	/**
	 * Method padString.
	 * 
	 * @param size
	 *            int
	 * @param character
	 *            String
	 * @return String
	 */
	public static String padString(int size, String character) {
		String s = "";

		for (int i = 0; i < size; i++) {
			s = s + character;
		}

		return s;
	}

	/**
	 * Method padString.
	 * 
	 * @param input
	 *            String
	 * @param right
	 *            boolean
	 * @param size
	 *            int
	 * @param character
	 *            String
	 * @return String
	 */
	public static String padString(String input, boolean right, int size, String character) {
		int inputlength = 0;
		String result = replaceNullStringwithBlank(input);

		inputlength = result.length();

		if (inputlength > size) {
			// result = result.substring(0,size-1);
			result = result.substring(0, size);
		} else {
			if (inputlength < size) {
				if (right == true) {
					result = result + padString(size - inputlength, character);
				} else {
					result = padString(size - inputlength, character) + result;
				}
			}
		}

		return result;
	}

	/**
	 * Method previewIcon.
	 * 
	 * @param btn
	 *            JButton
	 * @param filename
	 *            String
	 */
	public static void previewIcon(JButton btn, String filename) {
		try {
			if (filename == null) {
				filename = "";
			}

			if (filename.compareTo("") == 0) {
				Icon icon = Common.imageIconloader.getImageIcon(Common.image_blank_icon);
				btn.setIcon(icon);
			} else {
				File f;
				f = new File(filename);

				if (f.exists()) {
					Icon icon = Common.imageIconloader.getImageIcon(filename);
					btn.setIcon(icon);
				} else {
					Icon icon = Common.imageIconloader.getImageIcon(Common.image_error);
					btn.setIcon(icon);
				}
			}
		} catch (Exception e) {
		}
	}

	/**
	 * Method removeExtensionFromFilename.
	 * 
	 * @param filename
	 *            String
	 * @param extension
	 *            String
	 * @return String
	 */
	public static String removeExtensionFromFilename(String filename, String extension) {
		String result = "";

		if (filename == null) {
			filename = "";
		}

		if (extension == null) {
			extension = "";
		}

		result = filename;

		if (filename.indexOf(extension) > 0) {
			if (filename.length() > extension.length()) {
				int s1 = filename.length() - extension.length();

				if (s1 > 0) {
					result = filename.substring(0, s1);
				}
			}
		}

		return result;
	}

	/**
	 * Method removeTimefromDate.
	 * 
	 * @param inputDate
	 *            Date
	 * @return java.sql.Date
	 */
	public static java.sql.Date removeTimefromDate(Date inputDate) {
		Calendar caldate = Calendar.getInstance();
		caldate.setTime(inputDate);

		int day = caldate.get(Calendar.DATE);
		int month = caldate.get(Calendar.MONTH);
		int year = caldate.get(Calendar.YEAR);
		java.sql.Date sqldate = getSQLDate(year, month, day);

		return sqldate;
	}

	/**
	 * Method replaceNullObjectwithBlank.
	 * 
	 * @param value
	 *            Object
	 * @return String
	 */
	public static String replaceNullObjectwithBlank(Object value) {
		String result = "";

		if (value != null) {
			result = value.toString();
		}

		return result;
	}

	public static String replaceNullStringwithBlank(String value) {
		if (value == null) {
			value = "";
		}

		return value;
	}

	public static void adjustForLookandFeel() {
		LookAndFeel lf = UIManager.getLookAndFeel();
		if (lf.getName().equals("Mac OS X")) {
			Common.LFAdjustWidth = 0;
			Common.LFAdjustHeight = 0;
			Common.LFTreeMenuAdjustWidth = 13;
			Common.LFTreeMenuAdjustHeight = 13;
		} else {
			Common.LFAdjustWidth = -13;
			Common.LFAdjustHeight = -13;
			Common.LFTreeMenuAdjustWidth = 0;
			Common.LFTreeMenuAdjustHeight = 0;
		}
	}

	public static void setLookandFeel() {

		try {
			SetLookAndFeel("Metal", "Ocean");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method SetLookAndFeel.
	 * 
	 * @param laf
	 *            String
	 */
	public static void SetLookAndFeel(String LOOKANDFEEL, String THEME) {
		try {
			if (LOOKANDFEEL.equals("Metal")) {
				if (THEME.equals("DefaultMetal"))
					MetalLookAndFeel.setCurrentTheme(new DefaultMetalTheme());
				else if (THEME.equals("Ocean"))
					MetalLookAndFeel.setCurrentTheme(new OceanTheme());

				UIManager.setLookAndFeel(new MetalLookAndFeel());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void setResultRecordCountColour(JLabel label, boolean limitSet, Integer limitRecords,
			Integer ActualRecords) {
		String warning = "";

		if (ActualRecords > 0) {
			if (limitSet) {
				if (ActualRecords >= limitRecords) {
					label.setForeground(Color.RED);
					warning = " Number of records returned constrained by user defined limit.";
				} else {
					label.setForeground(Color.BLACK);
				}
			} else {
				label.setForeground(Color.BLACK);
			}

			label.setText(String.valueOf(ActualRecords) + " record(s) retrieved." + warning);
		} else {
			label.setForeground(Color.BLACK);
			label.setText("0 records shown.");
		}
	}

	/**
	 * Method sqlSelectNull.
	 * 
	 * @param value
	 *            String
	 * @return String
	 */
	public static String sqlSelectNull(String value) {
		if (value.equals("")) {
			return "is null";
		} else {
			return " = '" + value + "'";
		}
	}

	/**
	 * Method substSchemaName.
	 * 
	 * @param sql
	 *            String
	 * @return String
	 */
	public static String substSchemaName(String schemaName, String sql) {
		String result = "";

		result = sql.replace("{schema}", schemaName);

		return result;
	}

	/**
	 * Method writeToTextFile.
	 * 
	 * @param filename
	 *            String
	 * @param text
	 *            String
	 */
	public synchronized static void writeToTextFile(String filename, String text) {
		BufferedWriter bw = null;

		try {
			bw = new BufferedWriter(new FileWriter(filename, true));
			bw.write(text);
			bw.newLine();
			bw.flush();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally { // always close the file

			if (bw != null) {
				try {
					bw.close();
				} catch (IOException ioe2) {
				}
			}
		}
	}
}

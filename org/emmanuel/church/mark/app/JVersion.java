package org.emmanuel.church.mark.app;

public class JVersion {

	public static String getProgramVersion() {
		return "0.05";
	}

	public static Double getProgramVersionValue() {
		return Double.valueOf(getProgramVersion());
	}

	public static int getSchemaVersion() {
		return 46;
	}

}

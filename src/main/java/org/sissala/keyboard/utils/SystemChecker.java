/**
 * Copyright 2015 Yeongdeok Suh <skyducks111@gmail.com>
 */

package org.sissala.keyboard.utils;

public class SystemChecker {
	public static SYSTEM_INFO getSystemInfo() {
		String osInfo = System.getProperty("os.name");
		osInfo = osInfo.trim().toUpperCase().replace(" ", "_");
		
		return SYSTEM_INFO.valueOf(osInfo);
	}
}

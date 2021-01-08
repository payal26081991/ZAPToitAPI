package com.api.gracenote.utilities;

/**
 * @author kunal.ashar
 * @description Used to print testcase name in log file
 * 
 */

public class ConfigureTestCaseName {	

	/**
	 * @description to get current testcase name
	 * @return
	 */
	public static String currentTestCaseName() {
		String testCaseName = Thread.currentThread().getStackTrace()[2].getMethodName();
		return testCaseName;
	}

}

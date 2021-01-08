package com.api.gracenote.constants;

/**
 * @author kunal.ashar
 *
 */
public interface ConfigPropertiesConstants {

	// Execution Constants
	String	SETUP_NAME					= "setupName";
	String 	PROD_SERVER                 = "prodServerIp";
	String	SERVER_IP					= "serverIp";
	String	LANG_CODE					= "langCode";
	String	ADD_DAYS					= "addDays";
	String  TIME_SPAN					= "timeSpan";
	
	// TestNg Constants
	String	SUITE_TYPE					= "suiteType";
	String	THREAD_COUNT				= "threadCount";

	// API request constants
	String	TIMEOUT						= "timeout";
	String  DEFAULT_COUNTRY             = "defaultCountry";
	
	// DB Constants
	String	UAT_DB_HOST					= "uatDbHost";
	String	PREPROD_DB_HOST				= "preprodDbHost";
	String	DB_USERNAME					= "dbUserName";
	String	DB_PASSWORD					= "dbPassword";
	String	DB_SCHEMA					= "dbSchema";

	// TestCase Location
	String	TESTCASES_LOCATION			= "testCasesLocation";
	String	TESTCASE_LISTENER_LOCATION	= "testCaseListenerLocation";
	
	
}

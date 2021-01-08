/**
 * 
 */
package com.api.gracenote.constants;

/**
 * @author kunal.ashar
 *
 */
public interface StringConstants {

	// Setup Constants
	String	UAT_SETUP											= "Uat";
	String	PREPROD_SETUP										= "Preprod";
	String	PROD_SETUP										    = "Prod";

	// Encoding String
	String	ENCODING_TYPE										= "UTF-8";

	// boolean String constants
	String	TRUE_STRING											= "true";
	String	FALSE_STRING										= "false";

	// Database Constants
	String	DRIVER_REGISTER										= "com.mysql.cj.jdbc.Driver";

	// Header Constants
	String	CONTENT_TYPE_APPLICATION_JSON						= "application/json";

	// TestCase Excel Constants
	String	COLUMN_TESTCASE_NAME								= "TestCaseName";
	String	COLUMN_TESTCASE_DESCRIPTION							= "Description";

	// Time Constants
	String	TIMEZONE											= "GMT";
	String	DATE_FORMAT											= "yyyy-MM-dd'T'HH:mm:ss'Z'";

	// Affiliate Constants
	String	GAPZAP_AFFILIATE									= "gapzap";

	// Provider Type Constants
	String	SATELLITE											= "SATELLITE";
	String	CABLE												= "CABLE";
	String	OTA													= "OTA";

	// All Postal Code Constant
	String	ALL													= "ALL";

	// Response value constants
	String	LOCAL_OVER_THE_AIR_BROADCAST						= "Local Over the Air Broadcast";

	// file dump names
	String	PAYLOAD_JSON										= "payload.json";
	String	RESPONSE_JSON										= "response.json";
	String	REQUESTED_URI_TXT									= "requestedUri.txt";

	// Result String
	String	PASSED												= "Pass";
	String	FAILED												= "Fail";

	// report string constants
	String	URI_KEY												= "uri";
	String	PAYLOAD_KEY											= "payload";
	String	STATUS_CODE_KEY										= "statusCode";
	String	RESPONSE_KEY										= "response";
	String	RESULT_KEY											= "result";
	String	ASSERT_KEY											= "assert";

	// TestData excel sheet names
	String	FAVOURITE_CHANNEL_SHEET								= "favoriteChannel";
	String	FAVOURITE_CHANNEL_NEGATIVE_SCENARIOS_SHEET			= "favoriteChannelNegativeData";
	String	POSTUSER_SHEET										= "postUser";
	String	POSTUSER_NEGATIVE_SCENARIO_SHEET					= "postUserNegativeData";
	String	POST_VALIDATE_LOGIN_SHEET							= "postValidateLogin";
	String	POST_VALIDATE_LOGIN_NEGATIVE_SCENARIO_SHEET			= "postValidateLoginNegativeData";
	String	POST_EPISODE_GUIDE_NEGATIVE_SCENARIO_SHEET			= "postEpisodeGuideNegativeData";
	String	POSTAL_CODE_PROVIDERS_NEGATIVE_SCENARIO_SHEET		= "postalCodeProvidersNegativeData";
	String	POST_SCHEDULE_BY_LINEUP_ID_NEGATIVE_SCNARIO_SHEET	= "postScheduleByLineupIdNegative";
	String	SIMILAR_SHOWS_SHEET									= "similarShowsData";

	// Common Error Messages
	String	LOGIN_VALIDATION_FAILED_ERROR_MESSAGE				= "Invalid Username or Password";

	// timezone name constants
	String	EASTERN_DEFAULT_LINEUP								= "Eastern Default Lineup";
	String	PACIFIC_DEFAULT_LINEUP								= "Pacific Default Lineup";
	String	MOUNTAIN_DEFAULT_LINEUP								= "Mountain Default Lineup";
	String	CENTRAL_DEFAULT_LINEUP								= "Central Default Lineup";
	String	ALASKAN_DEFAULT_LINEUP								= "Alaskan Default Lineup";
	String	HAWAIIAN_DEFAULT_LINEUP								= "Hawaiian Default Lineup";
	String	EASTERN_DEFAULT_CANADIAN_LINEUP						= "Eastern Default Canadian Lineup";
	String	PACIFIC_DEFAULT_CANADIAN_LINEUP						= "Pacific Default Canadian Lineup";
	String	MOUNTAIN_DEFAULT_CANADIAN_LINEUP					= "Mountain Default Canadian Lineup";
	String	CENTRAL_DEFAULT_CANADIAN_LINEUP						= "Central Default Canadian Lineup";	
	
}

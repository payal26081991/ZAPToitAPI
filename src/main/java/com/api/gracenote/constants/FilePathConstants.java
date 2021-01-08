/**
 * 
 */
package com.api.gracenote.constants;

/**
 * @author kunal.ashar
 *
 */
public interface FilePathConstants {

	// Properties file names
	String	CONFIG_PROPERTIES_FILE_PATH							= "src/main/resources/config.properties";
	String	ERROR_PROPERTIES_FILE_PATH							= "src/main/resources/errorMessages.properties";

	// TestCase file name
	String	TESTCASE_FILE_PATH									= "src/main/resources/ApiTestCases.xlsx";

	// TestData file name
	String	FAVORITE_TESTDATA_FILE_PATH							= "testDataFiles/FavoriteApiTestData.xlsx";
	String	POSTUSER_TESTDATA_FILE_PATH							= "testDataFiles/PostUserAPITestData.xlsx";
	String	LOGIN_TESTDATA_FILE_PATH							= "testDataFiles/LoginApiTestData.xlsx";
	String	PROGRAM_TESTDATA_FILE_PATH							= "testDataFiles/ProgramApiTestData.xlsx";
	String	PROVIDERS_TESTDATA_FILE_PATH						= "testDataFiles/ProvidersApiTestData.xlsx";
	String	SCHEDULE_TESTDATA_FILE_PATH							= "testDataFiles/ScheduleApiTestData.xlsx";
	String	SIMILAR_SHOWS_TESTDATA_FILE_PATH					= "testDataFiles/SimilarShowsApiTestData.xlsx";

	// Report File Path
	String	OUTPUT_DIRECTORY_NAME								= "output";
	String	FILE_DUMP_PATH										= "output/fileDump/";
	String	REPORT_FILE_PATH									= "output/Report.html";

	// Payload File Path
	String	POST_PROGRAM_OVERVIEW_UPCOMING_DETAILS_FILE_PATH	= "jsonFiles/payload/program/postProgramOverviewUpcomingDetails.json";
	String	POST_SCHEDULE_BY_LINEUP_ID_FILE_PATH				= "jsonFiles/payload/schedule/postScheduleByLineupId.json";
	String	GET_CHANNEL_INFO_FILE_PATH							= "jsonFiles/payload/channel/getChannelInfo.json";
	String	POST_USER_FILE_PATH									= "jsonFiles/payload/user/postUser.json";
	String	POST_VALIDATE_LOGIN_FILE_PATH						= "jsonFiles/payload/login/postValidateLogin.json";
	String	POST_USER_PREFERENCE_FILE_PATH						= "jsonFiles/payload/user/postUserPreference.json";

	// Response Validations Schema File Path
	String	EPISODE_GUIDE_SCHEMA_FILE_PATH						= "jsonFiles/responseValidators/program/EpisodeGuideSchema.json";
	String	PROGRAM_OVERVIEW_SCHEMA_FILE_PATH					= "jsonFiles/responseValidators/program/ProgramOverviewSchema.json";
	String	FAVORITE_CHANNEL_SCHEMA_FILE_PATH					= "jsonFiles/responseValidators/favorite/FavoriteChannelSchema.json";
	String	USER_FAVORITE_SCHEMA_FILE_PATH						= "jsonFiles/responseValidators/favorite/UserFavoriteSchema.json";
	String	SIMILAR_SHOWS_SCHEMA_FILE_PATH						= "jsonFiles/responseValidators/similarShows/similarShows.json";
	String	BLANK_SIMILAR_SHOWS_SCHEMA_FILE_PATH				= "jsonFiles/responseValidators/similarShows/blankSimilarShows.json";

}

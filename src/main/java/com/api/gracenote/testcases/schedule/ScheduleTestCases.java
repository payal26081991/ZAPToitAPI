/**
 * 
 */
package com.api.gracenote.testcases.schedule;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.api.gracenote.bo.ScheduleBO;
import com.api.gracenote.constants.ConfigPropertiesConstants;
import com.api.gracenote.constants.EndPointUriConstants;
import com.api.gracenote.constants.ErrorMessagePropertiesConstants;
import com.api.gracenote.constants.FilePathConstants;
import com.api.gracenote.constants.ResponseKeyConstants;
import com.api.gracenote.constants.StatusCodeConstants;
import com.api.gracenote.constants.StringConstants;
import com.api.gracenote.coreResources.dataProviders.APIDataProvider;
import com.api.gracenote.coreResources.dataProviders.DBDataProvider;
import com.api.gracenote.coreResources.dataProviders.ExcelDataProviders;
import com.api.gracenote.coreResources.fileReaders.PropertyReader;
import com.api.gracenote.coreResources.testCaseManager.TestCaseResults;
import com.api.gracenote.utilities.Assertions;
import com.api.gracenote.utilities.CommonMethods;
import com.api.gracenote.utilities.ConfigureTestCaseName;
import com.api.gracenote.utilities.DateAndTime;
import com.api.gracenote.utilities.Headers;

/**
 * @author kunal.ashar
 *
 */
public class ScheduleTestCases {

	@Test(dataProvider = "getPostScheduleByLineupId", dataProviderClass = DBDataProvider.class)
	public void verifyTVGridTime(ScheduleBO schedule) {
		JSONObject		jsonObject		= null;
		HttpResponse	response		= null;
		JSONObject		responseObject	= null;
		DateAndTime		dateTime		= new DateAndTime();

		// to get current testcase name
		String			testCaseName	= ConfigureTestCaseName.currentTestCaseName();

		// to get jsonObject from payload file
		jsonObject = CommonMethods.createJsonObjectFromFile(testCaseName, FilePathConstants.POST_SCHEDULE_BY_LINEUP_ID_FILE_PATH);

		// updating json object as per BO values
		jsonObject.put("HeadendId", schedule.getHeadendId());
		jsonObject.put("CountryCode", schedule.getCountryName());
		jsonObject.put("Device", schedule.getDeviceType());
		jsonObject.put("Timespan", Integer.parseInt(schedule.getTimeSpan()));
		jsonObject.put("includechannels", "m,p");
		jsonObject.put("IsOverride", true);
		jsonObject.put("TimeStamp", schedule.getEpochTime());
		jsonObject.put("languagecode", PropertyReader.getConfigProperty(ConfigPropertiesConstants.LANG_CODE));

		// perform post call
		response = CommonMethods.post(testCaseName, EndPointUriConstants.POST_SCHEDULE_BY_LINEUP_ID, jsonObject.toString(),
				Headers.getCommonHeaders());

		// verify status code
		boolean flag = Assertions.verifyStatusCode(testCaseName, response.getStatusLine().getStatusCode(),
				StatusCodeConstants.STATUS_CODE_200);

		// fail testcase if false received
		if (!flag) {
			Assert.fail("Incorrect status code received.");
		}

		// get response as String
		String responseString = CommonMethods.getResponseAsString(testCaseName, response);

		// converting response to String and creating jsonObject
		responseObject = CommonMethods.createJsonObjectFromResposneString(testCaseName, responseString);

		// verify if response contains channels array
		if (Assertions.verifyContainsJsonKey(testCaseName, responseObject, ResponseKeyConstants.CHANNELS_JSON_KEY)) {
			JSONArray channelArray = CommonMethods.getJsonArrayFromJsonObject(testCaseName, responseObject,
					ResponseKeyConstants.CHANNELS_JSON_KEY);

			// to perform validation on channel array
			//channelArrayValidations(testCaseName, timeSpan, false, dateTime, channelArray);
			channelArrayValidations(testCaseName, schedule,false, dateTime, channelArray);

		} else {
			Assert.fail("Key :: " + ResponseKeyConstants.CHANNELS_JSON_KEY + " not present in jsonObject.");
		}
	}

	@Test(dataProvider = "getPostScheduleByLineupIdDataWithPostalCodes", dataProviderClass = DBDataProvider.class)
	public void verifyTVGridTimeWithPostalCodes(ScheduleBO schedule) {
		JSONObject		jsonObject		= null;
		HttpResponse	response		= null;
		JSONObject		responseObject	= null;
		DateAndTime		dateTime		= new DateAndTime();
		int				timeSpan		= 3;

		// to get current testcase name
		String			testCaseName	= ConfigureTestCaseName.currentTestCaseName();

		// to get jsonObject from payload file
		jsonObject = CommonMethods.createJsonObjectFromFile(testCaseName, FilePathConstants.POST_SCHEDULE_BY_LINEUP_ID_FILE_PATH);

		// updating json object as per BO values
		jsonObject.put("CountryCode", schedule.getCountryName());
		jsonObject.put("Postalcode", schedule.getPostalCode());
		jsonObject.put("PrgsvcId", schedule.getProgramSvcId());
		jsonObject.put("Timespan", schedule.getTimeSpan());
		jsonObject.put("TimeStamp", schedule.getEpochTime());
		jsonObject.put("languagecode", PropertyReader.getConfigProperty(ConfigPropertiesConstants.LANG_CODE));

		// perform post call
		response = CommonMethods.post(testCaseName, EndPointUriConstants.POST_SCHEDULE_BY_LINEUP_ID, jsonObject.toString(),
				Headers.getCommonHeaders());

		// verify status code
		boolean flag = Assertions.verifyStatusCode(testCaseName, response.getStatusLine().getStatusCode(),
				StatusCodeConstants.STATUS_CODE_200);

		// fail testcase if false received
		if (!flag) {
			Assert.fail("Incorrect status code received.");
		}

		// get response as String
		String responseString = CommonMethods.getResponseAsString(testCaseName, response);

		// converting response to String and creating jsonObject
		responseObject = CommonMethods.createJsonObjectFromResposneString(testCaseName, responseString);

		// verify if response contains channels array
		if (Assertions.verifyContainsJsonKey(testCaseName, responseObject, ResponseKeyConstants.CHANNELS_JSON_KEY)) {
			JSONArray channelArray = CommonMethods.getJsonArrayFromJsonObject(testCaseName, responseObject,
					ResponseKeyConstants.CHANNELS_JSON_KEY);

			// to perform validation on channel array
			channelArrayValidations(testCaseName, timeSpan, true, dateTime, channelArray);

		} else {
			Assert.fail("Key :: " + ResponseKeyConstants.CHANNELS_JSON_KEY + " not present in jsonObject.");
		}
	}

	@Test(dataProvider = "getPostScheduleByLineupId", dataProviderClass = DBDataProvider.class)
	public void verifyTVGridTimeForNDays(ScheduleBO schedule) {
		JSONObject		jsonObject		= null;
		HttpResponse	response		= null;
		JSONObject		responseObject	= null;
		DateAndTime		dateTime		= new DateAndTime(
				Integer.parseInt(PropertyReader.getConfigProperty(ConfigPropertiesConstants.ADD_DAYS)));

		// to get current testcase name
		String			testCaseName	= ConfigureTestCaseName.currentTestCaseName();

		// to get jsonObject from payload file
		jsonObject = CommonMethods.createJsonObjectFromFile(testCaseName, FilePathConstants.POST_SCHEDULE_BY_LINEUP_ID_FILE_PATH);

		// updating json object as per BO values
		jsonObject.put("HeadendId", schedule.getHeadendId());
		jsonObject.put("CountryCode", schedule.getCountryName());
		jsonObject.put("Device", schedule.getDeviceType());
		jsonObject.put("Timespan", Integer.parseInt(schedule.getTimeSpan()));
		jsonObject.put("includechannels", "m,p");
		jsonObject.put("IsOverride", true);
		jsonObject.put("TimeStamp", schedule.getEpochTime());
		jsonObject.put("languagecode", PropertyReader.getConfigProperty(ConfigPropertiesConstants.LANG_CODE));

		// perform post call
		response = CommonMethods.post(testCaseName, EndPointUriConstants.POST_SCHEDULE_BY_LINEUP_ID, jsonObject.toString(),
				Headers.getCommonHeaders());

		// verify status code
		boolean flag = Assertions.verifyStatusCode(testCaseName, response.getStatusLine().getStatusCode(),
				StatusCodeConstants.STATUS_CODE_200);

		// fail testcase if false received
		if (!flag) {
			Assert.fail("Incorrect status code received.");
		}

		// get response as String
		String responseString = CommonMethods.getResponseAsString(testCaseName, response);

		// converting response to String and creating jsonObject
		responseObject = CommonMethods.createJsonObjectFromResposneString(testCaseName, responseString);

		// verify if response contains channels array
		if (Assertions.verifyContainsJsonKey(testCaseName, responseObject, ResponseKeyConstants.CHANNELS_JSON_KEY)) {
			JSONArray channelArray = CommonMethods.getJsonArrayFromJsonObject(testCaseName, responseObject,
					ResponseKeyConstants.CHANNELS_JSON_KEY);

			// to perform validation on channel array
			//channelArrayValidations(testCaseName, timeSpan, false, dateTime, channelArray);
			channelArrayValidations(testCaseName, schedule,false, dateTime, channelArray);

		} else {
			Assert.fail("Key :: " + ResponseKeyConstants.CHANNELS_JSON_KEY + " not present in jsonObject.");
		}
	}

	@Test(dataProvider = "getPostScheduleByLineupIdDataWithPostalCodes", dataProviderClass = DBDataProvider.class)
	public void verifyTVGridTimeWithPostalCodesForNDays(ScheduleBO schedule) {
		JSONObject		jsonObject		= null;
		HttpResponse	response		= null;
		JSONObject		responseObject	= null;
		DateAndTime		dateTime		= new DateAndTime(
				Integer.parseInt(PropertyReader.getConfigProperty(ConfigPropertiesConstants.ADD_DAYS)));
		int				timeSpan		= 3;

		// to get current testcase name
		String			testCaseName	= ConfigureTestCaseName.currentTestCaseName();

		// to get jsonObject from payload file
		jsonObject = CommonMethods.createJsonObjectFromFile(testCaseName, FilePathConstants.POST_SCHEDULE_BY_LINEUP_ID_FILE_PATH);

		// updating json object as per BO values
		jsonObject.put("CountryCode", schedule.getCountryName());
		jsonObject.put("Postalcode", schedule.getPostalCode());
		jsonObject.put("PrgsvcId", schedule.getProgramSvcId());
		jsonObject.put("Timespan", Integer.parseInt(schedule.getTimeSpan()));
		jsonObject.put("TimeStamp", schedule.getEpochTime());
		jsonObject.put("languagecode", PropertyReader.getConfigProperty(ConfigPropertiesConstants.LANG_CODE));

		// perform post call
		response = CommonMethods.post(testCaseName, EndPointUriConstants.POST_SCHEDULE_BY_LINEUP_ID, jsonObject.toString(),
				Headers.getCommonHeaders());

		// verify status code
		boolean flag = Assertions.verifyStatusCode(testCaseName, response.getStatusLine().getStatusCode(),
				StatusCodeConstants.STATUS_CODE_200);

		// fail testcase if false received
		if (!flag) {
			Assert.fail("Incorrect status code received.");
		}

		// get response as String
		String responseString = CommonMethods.getResponseAsString(testCaseName, response);

		// converting response to String and creating jsonObject
		responseObject = CommonMethods.createJsonObjectFromResposneString(testCaseName, responseString);

		// verify if response contains channels array
		if (Assertions.verifyContainsJsonKey(testCaseName, responseObject, ResponseKeyConstants.CHANNELS_JSON_KEY)) {
			JSONArray channelArray = CommonMethods.getJsonArrayFromJsonObject(testCaseName, responseObject,
					ResponseKeyConstants.CHANNELS_JSON_KEY);

			// to perform validation on channel array
			//channelArrayValidations(testCaseName, timeSpan, true, dateTime, channelArray);
			channelArrayValidations(testCaseName, schedule, true, dateTime, channelArray);

		} else {
			Assert.fail("Key :: " + ResponseKeyConstants.CHANNELS_JSON_KEY + " not present in jsonObject.");
		}
	}

	@Test(dataProvider = "getPostScheduleByLineupIdDataWithOTALineups", dataProviderClass = DBDataProvider.class)
	public void verifyTVGridTimeWithPostalCodeForOTALineups(ScheduleBO schedule) {
		JSONObject		jsonObject		= null;
		HttpResponse	response		= null;
		JSONObject		responseObject	= null;
		DateAndTime		dateTime		= new DateAndTime();
		int				timeSpan		= 3;

		// to get current testcase name
		String			testCaseName	= ConfigureTestCaseName.currentTestCaseName();

		// to get jsonObject from payload file
		jsonObject = CommonMethods.createJsonObjectFromFile(testCaseName, FilePathConstants.POST_SCHEDULE_BY_LINEUP_ID_FILE_PATH);

		// updating json object as per BO values
		jsonObject.put("CountryCode", schedule.getCountryName());
		jsonObject.put("Postalcode", schedule.getPostalCode());
		jsonObject.put("Timespan", Integer.parseInt(schedule.getTimeSpan()));
		jsonObject.put("TimeStamp", schedule.getEpochTime());

		jsonObject.put("languagecode", PropertyReader.getConfigProperty(ConfigPropertiesConstants.LANG_CODE));

		// perform post call
		response = CommonMethods.post(testCaseName, EndPointUriConstants.POST_SCHEDULE_BY_LINEUP_ID, jsonObject.toString(),
				Headers.getCommonHeaders());

		// verify status code
		boolean flag = Assertions.verifyStatusCode(testCaseName, response.getStatusLine().getStatusCode(),
				StatusCodeConstants.STATUS_CODE_200);

		// fail testcase if false received
		if (!flag) {
			Assert.fail("Incorrect status code received.");
		}

		// get response as String
		String responseString = CommonMethods.getResponseAsString(testCaseName, response);

		// converting response to String and creating jsonObject
		responseObject = CommonMethods.createJsonObjectFromResposneString(testCaseName, responseString);

		// verify if response contains channels array
		if (Assertions.verifyContainsJsonKey(testCaseName, responseObject, ResponseKeyConstants.CHANNELS_JSON_KEY)) {
			JSONArray channelArray = CommonMethods.getJsonArrayFromJsonObject(testCaseName, responseObject,
					ResponseKeyConstants.CHANNELS_JSON_KEY);

			// to perform validation on channel array
			//channelArrayValidations(testCaseName, timeSpan, false, dateTime, channelArray);
			channelArrayValidations(testCaseName, schedule, false, dateTime, channelArray);

		} else {
			Assert.fail("Key :: " + ResponseKeyConstants.CHANNELS_JSON_KEY + " not present in jsonObject.");
		}
	}

	@Test(dataProvider = "getPostScheduleByLineupIdDataWithOTALineups", dataProviderClass = DBDataProvider.class)
	public void verifyTVGridTimeWithPostalCodeForOTALineupsForNDays(ScheduleBO schedule) {
		JSONObject		jsonObject		= null;
		HttpResponse	response		= null;
		JSONObject		responseObject	= null;
		DateAndTime		dateTime		= new DateAndTime(
				Integer.parseInt(PropertyReader.getConfigProperty(ConfigPropertiesConstants.ADD_DAYS)));

		// to get current testcase name
		String			testCaseName	= ConfigureTestCaseName.currentTestCaseName();

		// to get jsonObject from payload file
		jsonObject = CommonMethods.createJsonObjectFromFile(testCaseName, FilePathConstants.POST_SCHEDULE_BY_LINEUP_ID_FILE_PATH);

		// updating json object as per BO values
		jsonObject.put("CountryCode", schedule.getCountryName());
		jsonObject.put("Postalcode", schedule.getPostalCode());
		jsonObject.put("Timespan", Integer.parseInt(schedule.getTimeSpan()));
		jsonObject.put("TimeStamp", schedule.getEpochTime());
		jsonObject.put("languagecode", PropertyReader.getConfigProperty(ConfigPropertiesConstants.LANG_CODE));

		// perform post call
		response = CommonMethods.post(testCaseName, EndPointUriConstants.POST_SCHEDULE_BY_LINEUP_ID, jsonObject.toString(),
				Headers.getCommonHeaders());

		// verify status code
		boolean flag = Assertions.verifyStatusCode(testCaseName, response.getStatusLine().getStatusCode(),
				StatusCodeConstants.STATUS_CODE_200);

		// fail testcase if false received
		if (!flag) {
			Assert.fail("Incorrect status code received.");
		}

		// get response as String
		String responseString = CommonMethods.getResponseAsString(testCaseName, response);

		// converting response to String and creating jsonObject
		responseObject = CommonMethods.createJsonObjectFromResposneString(testCaseName, responseString);

		// verify if response contains channels array
		if (Assertions.verifyContainsJsonKey(testCaseName, responseObject, ResponseKeyConstants.CHANNELS_JSON_KEY)) {
			JSONArray channelArray = CommonMethods.getJsonArrayFromJsonObject(testCaseName, responseObject,
					ResponseKeyConstants.CHANNELS_JSON_KEY);

			// to perform validation on channel array
			//channelArrayValidations(testCaseName, timeSpan, false, dateTime, channelArray);
			channelArrayValidations(testCaseName, schedule, false, dateTime, channelArray);

		} else {
			Assert.fail("Key :: " + ResponseKeyConstants.CHANNELS_JSON_KEY + " not present in jsonObject.");
		}
	}

	@Test(dataProvider = "getPostScheduleSingleStationChannel", dataProviderClass = DBDataProvider.class)
	public void verifyTVGridTimeSingleStationChannel(ScheduleBO schedule) {
		JSONObject		jsonObject		= null;
		HttpResponse	response		= null;
		JSONObject		responseObject	= null;
		DateAndTime		dateTime		= new DateAndTime();
		int				timeSpan		= 3;

		// to get current testcase name
		String			testCaseName	= ConfigureTestCaseName.currentTestCaseName();

		// to get jsonObject from payload file
		jsonObject = CommonMethods.createJsonObjectFromFile(testCaseName, FilePathConstants.POST_SCHEDULE_BY_LINEUP_ID_FILE_PATH);

		// updating json object as per BO values
		jsonObject.put("HeadendId", schedule.getHeadendId());
		jsonObject.put("CountryCode", schedule.getCountryName());
		jsonObject.put("Device", schedule.getDeviceType());
		jsonObject.put("PrgsvcId", schedule.getProgramSvcId());
		jsonObject.put("Timespan", Integer.parseInt(schedule.getTimeSpan()));
		jsonObject.put("includechannels", "m,p");
		jsonObject.put("IsOverride", true);
		jsonObject.put("TimeStamp", schedule.getEpochTime());
		jsonObject.put("languagecode", PropertyReader.getConfigProperty(ConfigPropertiesConstants.LANG_CODE));

		// perform post call
		response = CommonMethods.post(testCaseName, EndPointUriConstants.POST_SCHEDULE_BY_LINEUP_ID, jsonObject.toString(),
				Headers.getCommonHeaders());

		// verify status code
		boolean flag = Assertions.verifyStatusCode(testCaseName, response.getStatusLine().getStatusCode(),
				StatusCodeConstants.STATUS_CODE_200);

		// fail testcase if false received
		if (!flag) {
			Assert.fail("Incorrect status code received.");
		}

		// get response as String
		String responseString = CommonMethods.getResponseAsString(testCaseName, response);

		// converting response to String and creating jsonObject
		responseObject = CommonMethods.createJsonObjectFromResposneString(testCaseName, responseString);

		// verify if response contains channels array
		if (Assertions.verifyContainsJsonKey(testCaseName, responseObject, ResponseKeyConstants.CHANNELS_JSON_KEY)) {
			JSONArray channelArray = CommonMethods.getJsonArrayFromJsonObject(testCaseName, responseObject,
					ResponseKeyConstants.CHANNELS_JSON_KEY);

			// to perform validation on channel array
			//channelArrayValidations(testCaseName, timeSpan, true, dateTime, channelArray);
			channelArrayValidations(testCaseName, schedule, true, dateTime, channelArray);

		} else {
			Assert.fail("Key :: " + ResponseKeyConstants.CHANNELS_JSON_KEY + " not present in jsonObject.");
		}
	}

	@Test(dataProvider = "getPostScheduleOTASingleStationChannel", dataProviderClass = DBDataProvider.class)
	public void verifyTVGridTimeOTASingleStationChannel(ScheduleBO schedule) {
		JSONObject		jsonObject		= null;
		HttpResponse	response		= null;
		JSONObject		responseObject	= null;
		DateAndTime		dateTime		= new DateAndTime();
		int				timeSpan		= 3;

		// to get current testcase name
		String			testCaseName	= ConfigureTestCaseName.currentTestCaseName();

		// to get jsonObject from payload file
		jsonObject = CommonMethods.createJsonObjectFromFile(testCaseName, FilePathConstants.POST_SCHEDULE_BY_LINEUP_ID_FILE_PATH);

		// updating json object as per BO values
		jsonObject.put("CountryCode", schedule.getCountryName());
		jsonObject.put("PrgsvcId", schedule.getProgramSvcId());
		jsonObject.put("Postalcode", schedule.getPostalCode());
		jsonObject.put("Timespan", Integer.parseInt(schedule.getTimeSpan()));
		jsonObject.put("includechannels", "m,p");
		jsonObject.put("IsOverride", true);
		jsonObject.put("TimeStamp", schedule.getEpochTime());
		jsonObject.put("languagecode", PropertyReader.getConfigProperty(ConfigPropertiesConstants.LANG_CODE));

		// perform post call
		response = CommonMethods.post(testCaseName, EndPointUriConstants.POST_SCHEDULE_BY_LINEUP_ID, jsonObject.toString(),
				Headers.getCommonHeaders());

		// verify status code
		boolean flag = Assertions.verifyStatusCode(testCaseName, response.getStatusLine().getStatusCode(),
				StatusCodeConstants.STATUS_CODE_200);

		// fail testcase if false received
		if (!flag) {
			Assert.fail("Incorrect status code received.");
		}

		// get response as String
		String responseString = CommonMethods.getResponseAsString(testCaseName, response);

		// converting response to String and creating jsonObject
		responseObject = CommonMethods.createJsonObjectFromResposneString(testCaseName, responseString);

		// verify if response contains channels array
		if (Assertions.verifyContainsJsonKey(testCaseName, responseObject, ResponseKeyConstants.CHANNELS_JSON_KEY)) {
			JSONArray channelArray = CommonMethods.getJsonArrayFromJsonObject(testCaseName, responseObject,
					ResponseKeyConstants.CHANNELS_JSON_KEY);

			// to perform validation on channel array
			// channelArrayValidations(testCaseName, timeSpan, true, dateTime, channelArray);
			channelArrayValidations(testCaseName, schedule, true, dateTime, channelArray);

		} else {
			Assert.fail("Key :: " + ResponseKeyConstants.CHANNELS_JSON_KEY + " not present in jsonObject.");
		}
	}

	@Test(dataProvider = "getPostScheduleSingleStationAffiliate", dataProviderClass = APIDataProvider.class)
	public void verifyTVGridTimeSingleStationAffiliate(ScheduleBO schedule) {
		JSONObject		jsonObject		= null;
		HttpResponse	response		= null;
		JSONObject		responseObject	= null;
		DateAndTime		dateTime		= new DateAndTime();
		int				timeSpan		= 3;

		// to get current testcase name
		String			testCaseName	= ConfigureTestCaseName.currentTestCaseName();

		// to get jsonObject from payload file
		jsonObject = CommonMethods.createJsonObjectFromFile(testCaseName, FilePathConstants.POST_SCHEDULE_BY_LINEUP_ID_FILE_PATH);

		// updating json object as per BO values
		jsonObject.put("aid", schedule.getAffiliateTag());
		jsonObject.put("CountryCode", schedule.getCountryName());
		jsonObject.put("PrgsvcId", schedule.getProgramSvcId());
		jsonObject.put("Postalcode", schedule.getPostalCode());
		jsonObject.put("HeadendId", schedule.getHeadendId());
		jsonObject.put("Device", schedule.getDeviceType());
		jsonObject.put("Timespan", timeSpan);
		jsonObject.put("includechannels", "m,p");
		jsonObject.put("IsOverride", true);
		jsonObject.put("TimeStamp", schedule.getEpochTime());
		jsonObject.put("languagecode", PropertyReader.getConfigProperty(ConfigPropertiesConstants.LANG_CODE));

		// perform post call
		response = CommonMethods.post(testCaseName, EndPointUriConstants.POST_SCHEDULE_BY_LINEUP_ID, jsonObject.toString(),
				Headers.getCommonHeaders());

		// verify status code
		boolean flag = Assertions.verifyStatusCode(testCaseName, response.getStatusLine().getStatusCode(),
				StatusCodeConstants.STATUS_CODE_200);

		// fail testcase if false received
		if (!flag) {
			Assert.fail("Incorrect status code received.");
		}

		// get response as String
		String responseString = CommonMethods.getResponseAsString(testCaseName, response);

		// converting response to String and creating jsonObject
		responseObject = CommonMethods.createJsonObjectFromResposneString(testCaseName, responseString);

		// verify if response contains channels array
		if (Assertions.verifyContainsJsonKey(testCaseName, responseObject, ResponseKeyConstants.CHANNELS_JSON_KEY)) {
			JSONArray channelArray = CommonMethods.getJsonArrayFromJsonObject(testCaseName, responseObject,
					ResponseKeyConstants.CHANNELS_JSON_KEY);

			// to perform validation on channel array
			//channelArrayValidations(testCaseName, timeSpan, true, dateTime, channelArray);
			channelArrayValidations(testCaseName, schedule, true, dateTime, channelArray);

		} else {
			Assert.fail("Key :: " + ResponseKeyConstants.CHANNELS_JSON_KEY + " not present in jsonObject.");
		}
	}

	@Test(dataProvider = "getPostScheduleSingleStation", dataProviderClass = DBDataProvider.class)
	public void verifyTVGridTimeSingleStation(ScheduleBO schedule) {
		JSONObject		jsonObject		= null;
		HttpResponse	response		= null;
		JSONObject		responseObject	= null;
		DateAndTime		dateTime		= new DateAndTime();
		int				timeSpan		= 3;

		// to get current testcase name
		String			testCaseName	= ConfigureTestCaseName.currentTestCaseName();

		// to get jsonObject from payload file
		jsonObject = CommonMethods.createJsonObjectFromFile(testCaseName, FilePathConstants.POST_SCHEDULE_BY_LINEUP_ID_FILE_PATH);

		// updating json object as per BO values
		jsonObject.put("CountryCode", schedule.getCountryName());
		jsonObject.put("PrgsvcId", schedule.getProgramSvcId());
		jsonObject.put("Device", schedule.getDeviceType());
		jsonObject.put("HeadendId", schedule.getHeadendId());
		jsonObject.put("Timespan", schedule.getTimeSpan());
		jsonObject.put("includechannels", "m,p");
		jsonObject.put("IsOverride", true);
		jsonObject.put("TimeStamp", schedule.getEpochTime());
		jsonObject.put("languagecode", PropertyReader.getConfigProperty(ConfigPropertiesConstants.LANG_CODE));

		// perform post call
		response = CommonMethods.post(testCaseName, EndPointUriConstants.POST_SCHEDULE_BY_LINEUP_ID, jsonObject.toString(),
				Headers.getCommonHeaders());

		// verify status code
		boolean flag = Assertions.verifyStatusCode(testCaseName, response.getStatusLine().getStatusCode(),
				StatusCodeConstants.STATUS_CODE_200);

		// fail testcase if false received
		if (!flag) {
			Assert.fail("Incorrect status code received.");
		}

		// get response as String
		String responseString = CommonMethods.getResponseAsString(testCaseName, response);

		// converting response to String and creating jsonObject
		responseObject = CommonMethods.createJsonObjectFromResposneString(testCaseName, responseString);

		// verify if response contains channels array
		if (Assertions.verifyContainsJsonKey(testCaseName, responseObject, ResponseKeyConstants.CHANNELS_JSON_KEY)) {
			JSONArray channelArray = CommonMethods.getJsonArrayFromJsonObject(testCaseName, responseObject,
					ResponseKeyConstants.CHANNELS_JSON_KEY);

			// to perform validation on channel array
			//channelArrayValidations(testCaseName, timeSpan, true, dateTime, channelArray);
			channelArrayValidations(testCaseName, schedule, true, dateTime, channelArray);

		} else {
			Assert.fail("Key :: " + ResponseKeyConstants.CHANNELS_JSON_KEY + " not present in jsonObject.");
		}
	}

	@Test(dataProvider = "getPostScheduleByLineupIdNegativeData", dataProviderClass = ExcelDataProviders.class)
	public void postScheduleByLineupIdNegativeScenarios(ScheduleBO schedule) {
		JSONObject		jsonObject		= null;
		HttpResponse	response		= null;
		DateAndTime		dateTime		= new DateAndTime();
		int				timeSpan		= Integer.parseInt(schedule.getTimeSpan());

		// to get current testcase name
		String			testCaseName	= ConfigureTestCaseName.currentTestCaseName();

		// to get jsonObject from payload file
		jsonObject = CommonMethods.createJsonObjectFromFile(testCaseName, FilePathConstants.POST_SCHEDULE_BY_LINEUP_ID_FILE_PATH);

		// updating json object as per BO values
		jsonObject.put("CountryCode", schedule.getCountryName());
		jsonObject.put("Postalcode", schedule.getPostalCode());
		jsonObject.put("HeadendId", schedule.getHeadendId());
		jsonObject.put("Timespan", schedule.getTimeSpan());
		jsonObject.put("includechannels", schedule.getIncludeChannels());
		jsonObject.put("Device", schedule.getDeviceType());
		jsonObject.put("aid", schedule.getAffiliateTag());
		jsonObject.put("TimeStamp", schedule.getEpochTime());
		jsonObject.put("languagecode", PropertyReader.getConfigProperty(ConfigPropertiesConstants.LANG_CODE));

		// perform post call
		response = CommonMethods.post(testCaseName, EndPointUriConstants.POST_SCHEDULE_BY_LINEUP_ID, jsonObject.toString(),
				Headers.getCommonHeaders());

		// verify status code
		boolean flag = Assertions.verifyStatusCode(testCaseName, response.getStatusLine().getStatusCode(),
				StatusCodeConstants.STATUS_CODE_400);

		// fail testcase if false received
		if (!flag) {
			Assert.fail("Incorrect status code received.");
		}
	}

	@Test
	public void postScheduleByLineupIdNoProviderFoundError() {
		JSONObject		jsonObject		= null;
		HttpResponse	response		= null;
		JSONObject		responseObject	= null;
		int				timeSpan		= 3;
		String			langCode		= PropertyReader.getConfigProperty(ConfigPropertiesConstants.LANG_CODE);

		// to get current testcase name
		String			testCaseName	= ConfigureTestCaseName.currentTestCaseName();

		// to get jsonObject from payload file
		jsonObject = CommonMethods.createJsonObjectFromFile(testCaseName, FilePathConstants.POST_SCHEDULE_BY_LINEUP_ID_FILE_PATH);

		// updating json object as per BO values
		jsonObject.put("Timespan", timeSpan);
		jsonObject.put("includechannels", "m,p");
		jsonObject.put("TimeStamp", 0);
		jsonObject.put("languagecode", langCode);

		// perform post call
		response = CommonMethods.post(testCaseName, EndPointUriConstants.POST_SCHEDULE_BY_LINEUP_ID, jsonObject.toString(),
				Headers.getCommonHeaders());

		// verify status code
		boolean flag = Assertions.verifyStatusCode(testCaseName, response.getStatusLine().getStatusCode(),
				StatusCodeConstants.STATUS_CODE_400);

		// fail testcase if false received
		if (!flag) {
			Assert.fail("Incorrect status code received.");
		}

		// get response as String
		String responseString = CommonMethods.getResponseAsString(testCaseName, response);

		// converting response to String and creating jsonObject
		responseObject = CommonMethods.createJsonObjectFromResposneString(testCaseName, responseString);

		// to fetch error message from response
		String errorMessage = Assertions.verifyNotNull(testCaseName, responseObject, ResponseKeyConstants.MESSAGE_JSON_KEY);

		// verify error message
		Assertions.verifyStringEquals(testCaseName, errorMessage,
				PropertyReader.getErrorMessageProperty(ErrorMessagePropertiesConstants.NO_PROVIDER_FOUND_ERROR + langCode));

	}

	/**
	 * @description to validate channel array for show start and end time
	 * @param dateTime
	 * @param gridStartTime
	 * @param gridEndTime
	 * @param channelArray
	 */
	private void channelArrayValidations(String testCaseName, int timeSpan, boolean isProgramSvcIdProvided, DateAndTime dateTime,
			JSONArray channelArray) {
		int		compareResult	= 0;

		String	gridStartTime	= dateTime.getGridStartTime();
		String	gridEndTime		= dateTime.getGridEndTime(timeSpan);

		// iterating over channels array
		for (int i = 0; i < channelArray.length(); i++) {
			List<String>	startEndTime		= new ArrayList<String>();
			String			scheduleCallSign	= null;

			// fetching schedule object from channels
			JSONObject		scheduleObject		= channelArray.getJSONObject(i);

			scheduleCallSign = Assertions.verifyNotNull(testCaseName, scheduleObject, ResponseKeyConstants.CALL_SIGN_JSON_KEY);

			// fetch events array from schedule object
			JSONArray eventsArray = CommonMethods.getJsonArrayFromJsonObject(testCaseName, scheduleObject,
					ResponseKeyConstants.EVENTS_JSON_KEY);

			if (eventsArray.length() > 0) {
				String callSign = null;

				// to fetch programs from events
				for (int j = 0; j < eventsArray.length(); j++) {
					JSONObject	eventObject			= eventsArray.getJSONObject(j);

					// fetching program start and end time from object
					String		programStartTime	= Assertions
							.verifyNotNull(testCaseName, eventObject, ResponseKeyConstants.START_TIME_JSON_KEY).trim();
					String		programEndTime		= Assertions
							.verifyNotNull(testCaseName, eventObject, ResponseKeyConstants.END_TIME_JSON_KEY).trim();

					// to verify channel number only if programSvcId is not provided in payload
					if (!isProgramSvcIdProvided) {

						callSign = Assertions.verifyNotNull(testCaseName, eventObject, ResponseKeyConstants.CALL_SIGN_JSON_KEY);

						String channelNumber = Assertions.verifyNotNull(testCaseName, eventObject,
								ResponseKeyConstants.CHANNEL_NUMBER_JSON_KEY);
						if (channelNumber.length() < 1) {
							TestCaseResults.addResult(testCaseName, StringConstants.ASSERT_KEY,
									"Channel number not available for CallSign :: " + callSign);
						}
					}

					// to verify program details
					JSONObject	programObject	= eventObject.getJSONObject(ResponseKeyConstants.PROGRAM_JSON_KEY);

					String		thumbnail		= programObject.get(ResponseKeyConstants.THUMBNAIL_JSON_KEY).toString();
					String		programTitle	= Assertions.verifyNotNull(testCaseName, programObject,
							ResponseKeyConstants.PROGRAM_TITLE_JSON_KEY);

					// to verify program object has thumbnail information.
					if (thumbnail.length() < 1) {
						TestCaseResults.addResult(testCaseName, StringConstants.ASSERT_KEY,
								"Thumbnail name not present for Program :: " + programTitle);
					}

					startEndTime.add(programStartTime);
					startEndTime.add(programEndTime);
				}

				for (int k = 0; k < startEndTime.size() - 1; k++) {

					// 1. compare start and end time of each program.
					compareResult = dateTime.compareDateTime(startEndTime.get(k), startEndTime.get(k + 1));

					if (!(compareResult <= 0)) {
						TestCaseResults.addResult(testCaseName, StringConstants.ASSERT_KEY, "Program Start Time :: " + startEndTime.get(k)
						+ " Program End Time :: " + startEndTime.get(k + 1) + " for callSign :: " + callSign);

						Assert.fail("Program end time is less than Program start time.");
					}

					// 2. to compare end time of first program with start time another program.
					// Fail if time difference found.
					if (!(k % 2 == 0)) {
						compareResult = dateTime.compareDateTime(startEndTime.get(k), startEndTime.get(k + 1));

						if (!(compareResult == 0)) {
							TestCaseResults.addResult(testCaseName, StringConstants.ASSERT_KEY,
									"First Program End Time :: " + startEndTime.get(k) + " Secong Program Start Time :: "
											+ startEndTime.get(k + 1) + " for callSign :: " + callSign);

							Assert.fail("First Program end time doesn't match with Second Program start time.");
						}
					}
				}

				// 3. start time of program shall start before requested time
				compareResult = dateTime.compareDateTime(startEndTime.get(0), gridStartTime);
				if (!(compareResult <= 0)) {
					TestCaseResults.addResult(testCaseName, StringConstants.ASSERT_KEY, "Program Start Time :: " + startEndTime.get(0)
					+ " Grid Start Time :: " + gridStartTime + " for callSign :: " + callSign);

					Assert.fail("Program start time is more than Grid start time.");
				}

				// 4. end time of program shall end after requested timespan (hours)
				compareResult = dateTime.compareDateTime(startEndTime.get(startEndTime.size() - 1), gridEndTime);
				if (!(compareResult >= 0)) {
					TestCaseResults.addResult(testCaseName, StringConstants.ASSERT_KEY,
							"Program End Time :: " + startEndTime.get(startEndTime.size() - 1) + " Grid End Time :: " + gridEndTime
							+ " for callSign :: " + callSign);

					Assert.fail("Program end time is less than Grid end time.");
				}

			} else {
				TestCaseResults.addResult(testCaseName, StringConstants.ASSERT_KEY,
						"Events array size received as 0 for callSign :: " + scheduleCallSign);

				Assert.fail("Events array size received as 0.");
			}
		}
	}

	private void channelArrayValidations(String testCaseName, ScheduleBO schedule, boolean isProgramSvcIdProvided, DateAndTime dateTime,
			JSONArray channelArray) {
		int		compareResult	= 0;

		String	gridStartTime	= dateTime.getGridStartTime(schedule.getEpochTime());
		String	gridEndTime		= dateTime.getGridEndTime(Integer.parseInt(schedule.getTimeSpan()),schedule.getEpochTime());

		// iterating over channels array
		for (int i = 0; i < channelArray.length(); i++) {
			List<String>	startEndTime		= new ArrayList<String>();
			String			scheduleCallSign	= null;

			// fetching schedule object from channels
			JSONObject		scheduleObject		= channelArray.getJSONObject(i);

			scheduleCallSign = Assertions.verifyNotNull(testCaseName, scheduleObject, ResponseKeyConstants.CALL_SIGN_JSON_KEY);

			// fetch events array from schedule object
			JSONArray eventsArray = CommonMethods.getJsonArrayFromJsonObject(testCaseName, scheduleObject,
					ResponseKeyConstants.EVENTS_JSON_KEY);

			if (eventsArray.length() > 0) {
				String callSign = null;

				// to fetch programs from events
				for (int j = 0; j < eventsArray.length(); j++) {
					JSONObject	eventObject			= eventsArray.getJSONObject(j);

					// fetching program start and end time from object
					String		programStartTime	= Assertions
							.verifyNotNull(testCaseName, eventObject, ResponseKeyConstants.START_TIME_JSON_KEY).trim();
					String		programEndTime		= Assertions
							.verifyNotNull(testCaseName, eventObject, ResponseKeyConstants.END_TIME_JSON_KEY).trim();

					// to verify channel number only if programSvcId is not provided in payload
					if (!isProgramSvcIdProvided) {

						callSign = Assertions.verifyNotNull(testCaseName, eventObject, ResponseKeyConstants.CALL_SIGN_JSON_KEY);

						String channelNumber = Assertions.verifyNotNull(testCaseName, eventObject,
								ResponseKeyConstants.CHANNEL_NUMBER_JSON_KEY);
						if (channelNumber.length() < 1) {
							TestCaseResults.addResult(testCaseName, StringConstants.ASSERT_KEY,
									"Channel number not available for CallSign :: " + callSign);
						}
					}

					// to verify program details
					JSONObject	programObject	= eventObject.getJSONObject(ResponseKeyConstants.PROGRAM_JSON_KEY);

					String		thumbnail		= programObject.get(ResponseKeyConstants.THUMBNAIL_JSON_KEY).toString();
					String		programTitle	= Assertions.verifyNotNull(testCaseName, programObject,
							ResponseKeyConstants.PROGRAM_TITLE_JSON_KEY);

					// to verify program object has thumbnail information.
					if (thumbnail.length() < 1) {
						TestCaseResults.addResult(testCaseName, StringConstants.ASSERT_KEY,
								"Thumbnail name not present for Program :: " + programTitle);
					}

					startEndTime.add(programStartTime);
					startEndTime.add(programEndTime);
				}

				for (int k = 0; k < startEndTime.size() - 1; k++) {

					// 1. compare start and end time of each program.
					compareResult = dateTime.compareDateTime(startEndTime.get(k), startEndTime.get(k + 1));

					if (!(compareResult <= 0)) {
						TestCaseResults.addResult(testCaseName, StringConstants.ASSERT_KEY, "Program Start Time :: " + startEndTime.get(k)
						+ " Program End Time :: " + startEndTime.get(k + 1) + " for callSign :: " + callSign);

						Assert.fail("Program end time is less than Program start time.");
					}

					// 2. to compare end time of first program with start time another program.
					// Fail if time difference found.
					if (!(k % 2 == 0)) {
						compareResult = dateTime.compareDateTime(startEndTime.get(k), startEndTime.get(k + 1));

						if (!(compareResult == 0)) {
							TestCaseResults.addResult(testCaseName, StringConstants.ASSERT_KEY,
									"First Program End Time :: " + startEndTime.get(k) + " Secong Program Start Time :: "
											+ startEndTime.get(k + 1) + " for callSign :: " + callSign);

							Assert.fail("First Program end time doesn't match with Second Program start time.");
						}
					}
				}

				// 3. start time of program shall start before requested time
				compareResult = dateTime.compareDateTime(startEndTime.get(0), gridStartTime);
				if (!(compareResult <= 0)) {
					TestCaseResults.addResult(testCaseName, StringConstants.ASSERT_KEY, "Program Start Time :: " + startEndTime.get(0)
					+ " Grid Start Time :: " + gridStartTime + " for callSign :: " + callSign);

					Assert.fail("Program start time is more than Grid start time.");
				}

				// 4. end time of program shall end after requested timespan (hours)
				compareResult = dateTime.compareDateTime(startEndTime.get(startEndTime.size() - 1), gridEndTime);
				if (!(compareResult >= 0)) {
					TestCaseResults.addResult(testCaseName, StringConstants.ASSERT_KEY,
							"Program End Time :: " + startEndTime.get(startEndTime.size() - 1) + " Grid End Time :: " + gridEndTime
							+ " for callSign :: " + callSign);

					Assert.fail("Program end time is less than Grid end time.");
				}

			} else {
				TestCaseResults.addResult(testCaseName, StringConstants.ASSERT_KEY,
						"Events array size received as 0 for callSign :: " + scheduleCallSign);

				Assert.fail("Events array size received as 0.");
			}
		}
	}


	@Test
	public void testPostScheduleByLineupIdAPI() {
		JSONObject		jsonObject		= null;
		HttpResponse	response		= null;
		JSONObject		responseObject	= null;
		DateAndTime		dateTime		= new DateAndTime(
				Integer.parseInt(PropertyReader.getConfigProperty(ConfigPropertiesConstants.ADD_DAYS)));
		int				timeSpan		= 3;

		// to get current testcase name
		String			testCaseName	= ConfigureTestCaseName.currentTestCaseName();

		long			epochTime		= dateTime.getEpochTime();
		String			payloadString	= "{\"Datasource\":null,\"Status\":null,\"ScheduleorChannel\":null,\"Timespan\":3,\"PrgsvcId\":null,\"Device\":\"-\",\"HeadendName\":null,\"clickstream\":{\"FromPage\":\"TVGrid1\",\"UserID\":0,\"Activity_ID\":\"1\",\"TMSID\":\"\",\"Device\":\"\",\"AffiliateID\":\"gapzap\",\"OVDID\":\"\",\"PostalCode\":\"\",\"CountryCode\":\"\",\"aid\":null,\"properties\":null,\"HeadendID\":\"\"},\"ToDateTime\":\"0001-01-01T00:00:00\",\"RefreshType\":null,\"TimeStamp\":"
				+ epochTime
				+ ",\"HeadendId\":\"0008022\",\"languagecode\":\"es-mx\",\"IsOverride\":true,\"IncludeChannels\":0,\"Postalcode\":\"\",\"includechannels\":\"m,p\",\"CountryCode\":\"CAN\",\"aid\":\"gapzap\",\"FromDateTime\":\"0001-01-01T00:00:00\",\"ActualTimeSpan\":0}";

		jsonObject	= CommonMethods.createJsonObjectFromResposneString(testCaseName, payloadString);

		// perform post call
		response	= CommonMethods.post(testCaseName, EndPointUriConstants.POST_SCHEDULE_BY_LINEUP_ID, jsonObject.toString(),
				Headers.getCommonHeaders());

		// verify status code
		boolean flag = Assertions.verifyStatusCode(testCaseName, response.getStatusLine().getStatusCode(),
				StatusCodeConstants.STATUS_CODE_200);

		// fail testcase if false received
		if (!flag) {
			Assert.fail("Incorrect status code received.");
		}

		// get response as String
		String responseString = CommonMethods.getResponseAsString(testCaseName, response);

		// converting response to String and creating jsonObject
		responseObject = CommonMethods.createJsonObjectFromResposneString(testCaseName, responseString);

		// verify if response contains channels array
		if (Assertions.verifyContainsJsonKey(testCaseName, responseObject, ResponseKeyConstants.CHANNELS_JSON_KEY)) {
			JSONArray channelArray = CommonMethods.getJsonArrayFromJsonObject(testCaseName, responseObject,
					ResponseKeyConstants.CHANNELS_JSON_KEY);

			// to perform validation on channel array
			channelArrayValidations(testCaseName, timeSpan, false, dateTime, channelArray);

		} else {
			Assert.fail("Key :: " + ResponseKeyConstants.CHANNELS_JSON_KEY + " not present in jsonObject.");
		}
	}




/******************************CACHE WARMING FUNCTION******************************************************/	
	@Test(dataProvider = "getHeadendData", dataProviderClass = DBDataProvider.class  ,invocationCount = 4)
	public void performCacheWarming(ScheduleBO schedule)
	{
		
		try {
			String			testCaseName	= ConfigureTestCaseName.currentTestCaseName();
			JSONObject		jsonObject		= null;
			HttpResponse	response		= null;

			// to get jsonObject from payload file
			jsonObject = CommonMethods.createJsonObjectFromFile(testCaseName, FilePathConstants.POST_SCHEDULE_BY_LINEUP_ID_FILE_PATH);
			jsonObject.put("HeadendId", schedule.getHeadendId());
			jsonObject.put("CountryCode", schedule.getCountryName());
			jsonObject.put("Device", schedule.getDeviceType());
			jsonObject.put("Timespan", Integer.parseInt(schedule.getTimeSpan()));
			jsonObject.put("includechannels", "m,p");
			jsonObject.put("IsOverride", true);
			jsonObject.put("TimeStamp", schedule.getEpochTime());
			jsonObject.put("languagecode", PropertyReader.getConfigProperty(ConfigPropertiesConstants.LANG_CODE));
			
			
			
			// perform post call
			
			response = CommonMethods.post(testCaseName, EndPointUriConstants.POST_SCHEDULE_BY_LINEUP_ID_CACHE_WARMING, jsonObject.toString(),Headers.getCommonHeaders());

			boolean flag = Assertions.verifyStatusCode(testCaseName, response.getStatusLine().getStatusCode(),
					StatusCodeConstants.STATUS_CODE_200);

			// fail testcase if false received
			if (!flag) {
				Assert.fail("Incorrect status code received.");
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
}

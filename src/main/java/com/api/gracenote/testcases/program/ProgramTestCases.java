/**
 * 
 */
package com.api.gracenote.testcases.program;

import org.apache.http.HttpResponse;
import org.json.JSONObject;
import org.testng.annotations.Test;

import com.api.gracenote.bo.ProgramBO;
import com.api.gracenote.constants.ConfigPropertiesConstants;
import com.api.gracenote.constants.EndPointUriConstants;
import com.api.gracenote.constants.FilePathConstants;
import com.api.gracenote.constants.StatusCodeConstants;
import com.api.gracenote.coreResources.dataProviders.DBDataProvider;
import com.api.gracenote.coreResources.dataProviders.ExcelDataProviders;
import com.api.gracenote.coreResources.fileReaders.PropertyReader;
import com.api.gracenote.utilities.Assertions;
import com.api.gracenote.utilities.CommonMethods;
import com.api.gracenote.utilities.ConfigureTestCaseName;
import com.api.gracenote.utilities.Headers;

import junit.framework.Assert;

/**
 * @author kunal.ashar
 *
 */
public class ProgramTestCases {

	@Test(dataProvider = "getpostProgramOverviewUpcomingDetailsData", dataProviderClass = DBDataProvider.class)
	public void postProgramOverviewUpcomingDetails(ProgramBO program) {
		JSONObject		jsonObject			= null;
		JSONObject		validationSchema	= null;
		HttpResponse	response			= null;

		// to get current testcase name
		String			testCaseName		= ConfigureTestCaseName.currentTestCaseName();

		// to get jsonObject from payload file
		jsonObject = CommonMethods.createJsonObjectFromFile(testCaseName,
		        FilePathConstants.POST_PROGRAM_OVERVIEW_UPCOMING_DETAILS_FILE_PATH);

		// updating jsonObject as per BO values
		jsonObject.put("languagecode", PropertyReader.getConfigProperty(ConfigPropertiesConstants.LANG_CODE));
		jsonObject.put("ProgramSeriesId", program.getProgramSeriesId());

		// perform post call
		response = CommonMethods.post(testCaseName, EndPointUriConstants.POST_PROGRAM_OVERVIEW_UPCOMING_DETAILS, jsonObject.toString(),
		        Headers.getCommonHeaders());

		// verify status code
		boolean flag = Assertions.verifyStatusCode(testCaseName, response.getStatusLine().getStatusCode(),
		        StatusCodeConstants.STATUS_CODE_200);

		// fail testcase if false received
		if (!flag) {
			Assert.fail("Incorrect status code received.");
		}

		// to get jsonObject from payload file
		validationSchema = CommonMethods.createJsonObjectFromFile(testCaseName, FilePathConstants.PROGRAM_OVERVIEW_SCHEMA_FILE_PATH);

		// to get response as String
		String		responseString	= CommonMethods.getResponseAsString(testCaseName, response);

		// to convert response string to json object
		JSONObject	responseObject	= CommonMethods.createJsonObjectFromResposneString(testCaseName, responseString);

		// validating response json object with expected response schema
		Assertions.validateJsonResponse(testCaseName, responseObject, validationSchema);
	}

	@Test(dataProvider = "getpostProgramOverviewUpcomingDetailsData", dataProviderClass = DBDataProvider.class)
	public void postEpisodeGuide(ProgramBO program) {
		JSONObject		jsonObject			= null;
		HttpResponse	response			= null;
		JSONObject		validationSchema	= null;

		// to get current testcase name
		String			testCaseName		= ConfigureTestCaseName.currentTestCaseName();

		// to get jsonObject from payload file
		jsonObject = CommonMethods.createJsonObjectFromFile(testCaseName,
		        FilePathConstants.POST_PROGRAM_OVERVIEW_UPCOMING_DETAILS_FILE_PATH);

		// updating jsonObject as per BO values
		jsonObject.put("languagecode", PropertyReader.getConfigProperty(ConfigPropertiesConstants.LANG_CODE));
		jsonObject.put("ProgramSeriesId", program.getProgramSeriesId());

		// perform post call
		response = CommonMethods.post(testCaseName, EndPointUriConstants.POST_EPISODE_GUIDE, jsonObject.toString(),
		        Headers.getCommonHeaders());

		// verify status code
		boolean flag = Assertions.verifyStatusCode(testCaseName, response.getStatusLine().getStatusCode(),
		        StatusCodeConstants.STATUS_CODE_200);

		// fail testcase if false received
		if (!flag) {
			Assert.fail("Incorrect status code received.");
		}

		// to get jsonObject from payload file
		validationSchema = CommonMethods.createJsonObjectFromFile(testCaseName, FilePathConstants.EPISODE_GUIDE_SCHEMA_FILE_PATH);

		// to get response as String
		String		responseString	= CommonMethods.getResponseAsString(testCaseName, response);

		// to convert response string to json object
		JSONObject	responseObject	= CommonMethods.createJsonObjectFromResposneString(testCaseName, responseString);

		// validating response json object with expected response schema
		Assertions.validateJsonResponse(testCaseName, responseObject, validationSchema);
	}

	@Test(dataProvider = "getPostEpisodeGuideNegativeData", dataProviderClass = ExcelDataProviders.class)
	public void postEpisodeGuideNegativeScenarios(ProgramBO program) {
		JSONObject		jsonObject		= null;
		HttpResponse	response		= null;

		// to get current testcase name
		String			testCaseName	= ConfigureTestCaseName.currentTestCaseName();

		// to get jsonObject from payload file
		jsonObject = CommonMethods.createJsonObjectFromFile(testCaseName,
		        FilePathConstants.POST_PROGRAM_OVERVIEW_UPCOMING_DETAILS_FILE_PATH);

		// updating jsonObject as per BO values
		jsonObject.put("languagecode", PropertyReader.getConfigProperty(ConfigPropertiesConstants.LANG_CODE));
		jsonObject.put("ProgramSeriesId", program.getProgramSeriesId());
		jsonObject.put("HeadendId", program.getHeadendId());
		jsonObject.put("CountryCode", program.getCountryCode());
		jsonObject.put("PostalCode", program.getPostalCode());
		jsonObject.put("Device", program.getDevice());
		jsonObject.put("aid", program.getAffiliateId());

		// perform post call
		response = CommonMethods.post(testCaseName, EndPointUriConstants.POST_EPISODE_GUIDE, jsonObject.toString(),
		        Headers.getCommonHeaders());

		// verify status code
		boolean flag = Assertions.verifyStatusCode(testCaseName, response.getStatusLine().getStatusCode(),
		        StatusCodeConstants.STATUS_CODE_400);

		// fail testcase if false received
		if (!flag) {
			Assert.fail("Incorrect status code received.");
		}
	}

	@Test(dataProvider = "getPostEpisodeGuideNegativeData", dataProviderClass = ExcelDataProviders.class)
	public void postProgramOverviewUpcomingDetailsNegativeScenarios(ProgramBO program) {
		JSONObject		jsonObject		= null;
		HttpResponse	response		= null;

		// to get current testcase name
		String			testCaseName	= ConfigureTestCaseName.currentTestCaseName();

		// to get jsonObject from payload file
		jsonObject = CommonMethods.createJsonObjectFromFile(testCaseName,
		        FilePathConstants.POST_PROGRAM_OVERVIEW_UPCOMING_DETAILS_FILE_PATH);

		// updating jsonObject as per BO values
		jsonObject.put("languagecode", PropertyReader.getConfigProperty(ConfigPropertiesConstants.LANG_CODE));
		jsonObject.put("ProgramSeriesId", program.getProgramSeriesId());
		jsonObject.put("HeadendId", program.getHeadendId());
		jsonObject.put("CountryCode", program.getCountryCode());
		jsonObject.put("PostalCode", program.getPostalCode());
		jsonObject.put("Device", program.getDevice());
		jsonObject.put("aid", program.getAffiliateId());

		// perform post call
		response = CommonMethods.post(testCaseName, EndPointUriConstants.POST_PROGRAM_OVERVIEW_UPCOMING_DETAILS, jsonObject.toString(),
		        Headers.getCommonHeaders());

		// verify status code
		boolean flag = Assertions.verifyStatusCode(testCaseName, response.getStatusLine().getStatusCode(),
		        StatusCodeConstants.STATUS_CODE_400);

		// fail testcase if false received
		if (!flag) {
			Assert.fail("Incorrect status code received.");
		}
	}
}

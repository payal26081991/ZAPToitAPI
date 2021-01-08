/**
 * 
 */
package com.api.gracenote.testcases.favorite;

import org.apache.http.HttpResponse;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.testng.annotations.Test;

import com.api.gracenote.bo.FavoriteBO;
import com.api.gracenote.constants.ConfigPropertiesConstants;
import com.api.gracenote.constants.EndPointUriConstants;
import com.api.gracenote.constants.FilePathConstants;
import com.api.gracenote.constants.ResponseKeyConstants;
import com.api.gracenote.constants.StatusCodeConstants;
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
public class FavoriteTestCases {

	Logger logger = Logger.getLogger(FavoriteTestCases.class);

	@Test(dataProvider = "getFavoriteChannelData", dataProviderClass = ExcelDataProviders.class)
	public void getFavoriteChannel(FavoriteBO favorite) {

		JSONObject		validationSchema	= null;

		// to get current testcase name
		String			testCaseName		= ConfigureTestCaseName.currentTestCaseName();

		// Create new uri as per affId provided by data provider
		String			uri					= EndPointUriConstants.GET_FAVORITE_CHANNEL + favorite.getUserId() + "/"
		        + favorite.getProgramSvcId() + "/" + favorite.getIsFavorite() + "/"
		        + PropertyReader.getConfigProperty(ConfigPropertiesConstants.LANG_CODE);

		// Perform GET call
		HttpResponse	response			= CommonMethods.get(testCaseName, uri, Headers.getCommonHeaders());

		// verify status code
		boolean			flag				= Assertions.verifyStatusCode(testCaseName, response.getStatusLine().getStatusCode(),
		        StatusCodeConstants.STATUS_CODE_200);

		// fail testcase if false received
		if (!flag) {
			Assert.fail("Incorrect status code received.");
		}

		// get response as String
		String		responseString	= CommonMethods.getResponseAsString(testCaseName, response);

		// converting response to String and creating responseObject
		JSONObject	responseObject	= CommonMethods.createJsonObjectFromResposneString(testCaseName, responseString);

		// Assertions
		Assertions.verifyContainsJsonKey(testCaseName, responseObject, ResponseKeyConstants.USER_FAVORITE_CHANNEL_JSON_KEY);

		// to get jsonObject from payload file
		validationSchema = CommonMethods.createJsonObjectFromFile(testCaseName, FilePathConstants.FAVORITE_CHANNEL_SCHEMA_FILE_PATH);

		// validating response json object with expected response schema
		Assertions.validateJsonResponse(testCaseName, responseObject, validationSchema);
	}

	@Test(dataProvider = "getFavoriteChannelNegativeData", dataProviderClass = ExcelDataProviders.class)
	public void getFavoriteChannelNegativeScenarios(FavoriteBO favorite) {

		// to get current testcase name
		String			testCaseName	= ConfigureTestCaseName.currentTestCaseName();

		// Create new uri as per affId provided by data provider
		String			uri				= EndPointUriConstants.GET_FAVORITE_CHANNEL + favorite.getUserId() + "/"
		        + favorite.getProgramSvcId() + "/" + favorite.getIsFavorite() + "/"
		        + PropertyReader.getConfigProperty(ConfigPropertiesConstants.LANG_CODE);

		// Perform GET call
		HttpResponse	response		= CommonMethods.get(testCaseName, uri, Headers.getCommonHeaders());

		// verify status code
		boolean			flag			= Assertions.verifyStatusCode(testCaseName, response.getStatusLine().getStatusCode(),
		        StatusCodeConstants.STATUS_CODE_400);

		// fail testcase if false received
		if (!flag) {
			Assert.fail("Incorrect status code received.");
		}
	}

	@Test(dataProvider = "getFavoriteChannelData", dataProviderClass = ExcelDataProviders.class)
	public void getUserFavorites(FavoriteBO favorite) {

		JSONObject		validationSchema	= null;

		// to get current testcase name
		String			testCaseName		= ConfigureTestCaseName.currentTestCaseName();

		// Create new uri as per affId provided by data provider
		String			uri					= EndPointUriConstants.GET_USER_FAVORITES + favorite.getUserId() + "/"
		        + PropertyReader.getConfigProperty(ConfigPropertiesConstants.LANG_CODE);

		// Perform GET call
		HttpResponse	response			= CommonMethods.get(testCaseName, uri, Headers.getCommonHeaders());

		// verify status code
		boolean			flag				= Assertions.verifyStatusCode(testCaseName, response.getStatusLine().getStatusCode(),
		        StatusCodeConstants.STATUS_CODE_200);

		// fail testcase if false received
		if (!flag) {
			Assert.fail("Incorrect status code received.");
		}

		// get response as String
		String		responseString	= CommonMethods.getResponseAsString(testCaseName, response);

		// converting response to String and creating responseObject
		JSONObject	responseObject	= CommonMethods.createJsonObjectFromResposneString(testCaseName, responseString);

		// Assertions
		Assertions.verifyContainsJsonKey(testCaseName, responseObject, ResponseKeyConstants.SHOWS_JSON_KEY);
		Assertions.verifyContainsJsonKey(testCaseName, responseObject, ResponseKeyConstants.CHANNELS_JSON_KEY);

		// to get jsonObject from payload file
		validationSchema = CommonMethods.createJsonObjectFromFile(testCaseName, FilePathConstants.USER_FAVORITE_SCHEMA_FILE_PATH);

		// validating response json object with expected response schema
		Assertions.validateJsonResponse(testCaseName, responseObject, validationSchema);
	}

	@Test(dataProvider = "getFavoriteChannelNegativeData", dataProviderClass = ExcelDataProviders.class)
	public void getUserFavoritesNegativeScenarios(FavoriteBO favorite) {

		// to get current testcase name
		String			testCaseName	= ConfigureTestCaseName.currentTestCaseName();

		// Create new uri as per affId provided by data provider
		String			uri				= EndPointUriConstants.GET_USER_FAVORITES + favorite.getUserId() + "/"
		        + PropertyReader.getConfigProperty(ConfigPropertiesConstants.LANG_CODE);

		// Perform GET call
		HttpResponse	response		= CommonMethods.get(testCaseName, uri, Headers.getCommonHeaders());

		// verify status code
		boolean			flag			= Assertions.verifyStatusCode(testCaseName, response.getStatusLine().getStatusCode(),
		        StatusCodeConstants.STATUS_CODE_400);

		// fail testcase if false received
		if (!flag) {
			Assert.fail("Incorrect status code received.");
		}
	}

}

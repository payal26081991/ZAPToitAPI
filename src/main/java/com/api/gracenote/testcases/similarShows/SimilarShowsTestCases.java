/**
 * 
 */
package com.api.gracenote.testcases.similarShows;

import org.apache.http.HttpResponse;
import org.json.JSONObject;
import org.testng.annotations.Test;

import com.api.gracenote.bo.SimilarShowsBO;
import com.api.gracenote.constants.EndPointUriConstants;
import com.api.gracenote.constants.FilePathConstants;
import com.api.gracenote.constants.ResponseKeyConstants;
import com.api.gracenote.constants.StatusCodeConstants;
import com.api.gracenote.constants.StringConstants;
import com.api.gracenote.coreResources.dataProviders.ExcelDataProviders;
import com.api.gracenote.utilities.Assertions;
import com.api.gracenote.utilities.CommonMethods;
import com.api.gracenote.utilities.ConfigureTestCaseName;
import com.api.gracenote.utilities.Headers;

import junit.framework.Assert;

/**
 * @author kunal.ashar
 *
 */
public class SimilarShowsTestCases {

	@Test(dataProvider = "getSimilarShowsData", dataProviderClass = ExcelDataProviders.class)
	public void getSimilarShows(SimilarShowsBO similarShows) {

		JSONObject		validationSchema	= null;

		// to get current testcase name
		String			testCaseName		= ConfigureTestCaseName.currentTestCaseName();

		// Create new uri as per affId provided by data provider
		String			uri					= EndPointUriConstants.GET_SIMILAR_SHOWS + similarShows.getTmsId() + "/"
		        + StringConstants.GAPZAP_AFFILIATE;

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
		if (Assertions.verifyContainsJsonKey(testCaseName, responseObject, ResponseKeyConstants.RECOMMENDATIONS_JSON_KEY)) {
			// to get jsonObject from payload file
			validationSchema = CommonMethods.createJsonObjectFromFile(testCaseName, FilePathConstants.SIMILAR_SHOWS_SCHEMA_FILE_PATH);
		} else {
			validationSchema = CommonMethods.createJsonObjectFromFile(testCaseName, FilePathConstants.BLANK_SIMILAR_SHOWS_SCHEMA_FILE_PATH);
		}

		// validating response json object with expected response schema
		Assertions.validateJsonResponse(testCaseName, responseObject, validationSchema);
	}
}

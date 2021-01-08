/**
 * 
 */
package com.api.gracenote.testcases.users;

import java.util.Map;

import org.apache.http.HttpResponse;
import org.json.JSONObject;
import org.testng.annotations.Test;

import com.api.gracenote.bo.UserBO;
import com.api.gracenote.constants.ConfigPropertiesConstants;
import com.api.gracenote.constants.EndPointUriConstants;
import com.api.gracenote.constants.FilePathConstants;
import com.api.gracenote.constants.ResponseKeyConstants;
import com.api.gracenote.constants.StatusCodeConstants;
import com.api.gracenote.constants.StringConstants;
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
public class UsersTestCases {

	@Test(dataProvider = "getPostUserData", dataProviderClass = ExcelDataProviders.class)
	public void createNewUser(UserBO user) {
		JSONObject		jsonObject			= null;
		JSONObject		validationSchema	= null;
		HttpResponse	response			= null;

		// to get current testcase name
		String			testCaseName		= ConfigureTestCaseName.currentTestCaseName();

		// to get jsonObject from payload file
		jsonObject = CommonMethods.createJsonObjectFromFile(testCaseName, FilePathConstants.POST_USER_FILE_PATH);

		// updating jsonObject as per BO values
		jsonObject.put("languagecode", PropertyReader.getConfigProperty(ConfigPropertiesConstants.LANG_CODE));
		jsonObject.put("password", user.getPassword());
		jsonObject.put("usertype", Integer.parseInt(user.getUserType()));
		jsonObject.put("emailid", user.getEmailId());

		// isfacebookuser property can have 0 or false as input
		if (user.getIsFacebookUser().equalsIgnoreCase(StringConstants.FALSE_STRING)) {
			jsonObject.put("isfacebookuser", user.getIsFacebookUser());
		} else {
			jsonObject.put("isfacebookuser", Integer.parseInt(user.getIsFacebookUser()));
		}

		// updating header Object
		Map<String, String> headers = Headers.getCommonHeaders();
		headers.put("context", "abc");

		// perform post call
		response = CommonMethods.post(testCaseName, EndPointUriConstants.POST_USER, jsonObject.toString(), headers);

		// verify status code
		boolean flag = Assertions.verifyStatusCode(testCaseName, response.getStatusLine().getStatusCode(),
		        StatusCodeConstants.STATUS_CODE_200);

		// fail testcase if false received
		if (!flag) {
			Assert.fail("Incorrect status code received.");
		}

		// to get response as String
		String		responseString	= CommonMethods.getResponseAsString(testCaseName, response);

		// to convert response string to json object
		JSONObject	responseObject	= CommonMethods.createJsonObjectFromResposneString(testCaseName, responseString);

		flag = Assertions.verifyContainsJsonKey(testCaseName, responseObject, ResponseKeyConstants.USER_DETAILS_JSON_KEY);

		// fail testcase if false received
		if (!flag) {
			Assert.fail("New User Not Created.");
		}

		// to get jsonObject from payload file
		validationSchema = CommonMethods.createJsonObjectFromFile(testCaseName, FilePathConstants.POST_USER_FILE_PATH);

		// validating response json object with expected response schema
		Assertions.validateJsonResponse(testCaseName, responseObject, validationSchema);
	}

	@Test(dataProvider = "getPostUserNegativeData", dataProviderClass = ExcelDataProviders.class)
	public void createNewUserNegativeScenarios(UserBO user) {
		JSONObject		jsonObject			= null;		
		HttpResponse	response			= null;

		// to get current testcase name
		String			testCaseName		= ConfigureTestCaseName.currentTestCaseName();

		// to get jsonObject from payload file
		jsonObject = CommonMethods.createJsonObjectFromFile(testCaseName, FilePathConstants.POST_USER_FILE_PATH);

		// updating jsonObject as per BO values
		jsonObject.put("languagecode", PropertyReader.getConfigProperty(ConfigPropertiesConstants.LANG_CODE));
		jsonObject.put("password", user.getPassword());
		jsonObject.put("usertype", Integer.parseInt(user.getUserType()));
		jsonObject.put("emailid", user.getEmailId());

		// isfacebookuser property can have 0 or true/false as input
		if (user.getIsFacebookUser().equalsIgnoreCase(StringConstants.FALSE_STRING)
		        || user.getIsFacebookUser().equals(StringConstants.TRUE_STRING)) {
			jsonObject.put("isfacebookuser", user.getIsFacebookUser());
		} else {
			jsonObject.put("isfacebookuser", Integer.parseInt(user.getIsFacebookUser()));
		}

		// updating header Object
		Map<String, String> headers = Headers.getCommonHeaders();
		headers.put("context", "abc");

		// perform post call
		response = CommonMethods.post(testCaseName, EndPointUriConstants.POST_USER, jsonObject.toString(), headers);

		// verify status code
		boolean flag = Assertions.verifyStatusCode(testCaseName, response.getStatusLine().getStatusCode(),
		        StatusCodeConstants.STATUS_CODE_400);

		// fail testcase if false received
		if (!flag) {
			Assert.fail("Incorrect status code received.");
		}
	}

}

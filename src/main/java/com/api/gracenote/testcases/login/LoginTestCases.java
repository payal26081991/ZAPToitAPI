/**
 * 
 */
package com.api.gracenote.testcases.login;

import com.api.gracenote.bo.LoginBO;
import com.api.gracenote.constants.*;
import com.api.gracenote.coreResources.dataProviders.ExcelDataProviders;
import com.api.gracenote.coreResources.fileReaders.PropertyReader;
import com.api.gracenote.utilities.Assertions;
import com.api.gracenote.utilities.CommonMethods;
import com.api.gracenote.utilities.ConfigureTestCaseName;
import com.api.gracenote.utilities.Headers;
import junit.framework.Assert;
import org.apache.http.HttpResponse;
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.util.Map;

/**
 * @author kunal.ashar
 *
 */
public class LoginTestCases {

	@Test(dataProvider = "getPostValidateLoginData", dataProviderClass = ExcelDataProviders.class)
	public void validateLogin(LoginBO login) {
		JSONObject		jsonObject			= null;
		JSONObject		validationSchema	= null;
		HttpResponse	response			= null;

		// to get current testcase name
		String			testCaseName		= ConfigureTestCaseName.currentTestCaseName();

		// to get jsonObject from payload file
		jsonObject = CommonMethods.createJsonObjectFromFile(testCaseName, FilePathConstants.POST_VALIDATE_LOGIN_FILE_PATH);

		// updating jsonObject as per BO values
		jsonObject.put("EmailID", login.getEmailId());
		jsonObject.put("Password", login.getPassword());
		jsonObject.put("UserType", Integer.parseInt(login.getUserType()));
		jsonObject.put("IsFacebookUser", Integer.parseInt(login.getIsFacebookUser()));
		jsonObject.put("ObjectID", login.getObjectId());
		jsonObject.put("aid", login.getAffiliateId());
		jsonObject.put("languagecode", PropertyReader.getConfigProperty(ConfigPropertiesConstants.LANG_CODE));

		// updating header Object
		Map<String, String> headers = Headers.getCommonHeaders();
		headers.put("context", "abc");

		// perform post call
		response = CommonMethods.post(testCaseName, EndPointUriConstants.POST_VALIDATE_LOGIN, jsonObject.toString(), headers);

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

		flag = Assertions.verifyContainsJsonKey(testCaseName, responseObject, ResponseKeyConstants.LOGIN_DETAILS_JSON_KEY);

		// fail testcase if false received
		if (!flag) {
			Assert.fail("Login Details Not Found.");
		}

		// validating error message
		if (Assertions.verifyContainsJsonKey(testCaseName, responseObject, ResponseKeyConstants.ERROR_MESSAGE_JSON_KEY)) {
			String errorMessage = Assertions.verifyNotNull(testCaseName, responseObject, ResponseKeyConstants.ERROR_MESSAGE_JSON_KEY);

			if (!(errorMessage.equalsIgnoreCase(""))) {

				// fetching error message for corresponding language from properties file
				String expectedErrorMessage = PropertyReader
				        .getErrorMessageProperty(ErrorMessagePropertiesConstants.USER_LOGIN_SINGLE_PROVIDER_ERROR
				                + PropertyReader.getConfigProperty(ConfigPropertiesConstants.LANG_CODE));

				// validate error message
				Assertions.verifyStringEquals(testCaseName, errorMessage, expectedErrorMessage);
			}
		}

		// to get jsonObject from payload file
		validationSchema = CommonMethods.createJsonObjectFromFile(testCaseName, FilePathConstants.POST_VALIDATE_LOGIN_FILE_PATH);

		// validating response json object with expected response schema
		Assertions.validateJsonResponse(testCaseName, responseObject, validationSchema);
	}

	@Test(dataProvider = "getPostValidateLoginNegativeData", dataProviderClass = ExcelDataProviders.class)
	public void validateLoginNegativeScenarios(LoginBO login) {
		JSONObject		jsonObject			= null;
		JSONObject		validationSchema	= null;
		HttpResponse	response			= null;

		// to get current testcase name
		String			testCaseName		= ConfigureTestCaseName.currentTestCaseName();

		// to get jsonObject from payload file
		jsonObject = CommonMethods.createJsonObjectFromFile(testCaseName, FilePathConstants.POST_VALIDATE_LOGIN_FILE_PATH);

		// updating jsonObject as per BO values
		jsonObject.put("EmailID", login.getEmailId());
		jsonObject.put("Password", login.getPassword());
		jsonObject.put("UserType", Integer.parseInt(login.getUserType()));
		jsonObject.put("IsFacebookUser", Integer.parseInt(login.getIsFacebookUser()));
		jsonObject.put("ObjectID", login.getObjectId());
		jsonObject.put("aid", login.getAffiliateId());
		jsonObject.put("languagecode", PropertyReader.getConfigProperty(ConfigPropertiesConstants.LANG_CODE));

		// updating header Object
		Map<String, String> headers = Headers.getCommonHeaders();
		headers.put("context", "abc");

		// perform post call
		response = CommonMethods.post(testCaseName, EndPointUriConstants.POST_VALIDATE_LOGIN, jsonObject.toString(), headers);

		// verify status code
		boolean flag = Assertions.verifyStatusCode(testCaseName, response.getStatusLine().getStatusCode(),
		        StatusCodeConstants.STATUS_CODE_401);

		// fail testcase if false received
		if (!flag) {
			Assert.fail("Incorrect status code received.");
		}

		// to get response as String
		String		responseString	= CommonMethods.getResponseAsString(testCaseName, response);

		// to convert response string to json object
		JSONObject	responseObject	= CommonMethods.createJsonObjectFromResposneString(testCaseName, responseString);

		flag = Assertions.verifyContainsJsonKey(testCaseName, responseObject, ResponseKeyConstants.LOGIN_DETAILS_JSON_KEY);

		// fail testcase if false received
		if (!flag) {
			Assert.fail("Login Details Not Found.");
		}

		// validating error message
		String errorMessage = Assertions.verifyNotNull(testCaseName, responseObject, ResponseKeyConstants.ERROR_MESSAGE_JSON_KEY);
		Assertions.verifyStringEquals(testCaseName, errorMessage, StringConstants.LOGIN_VALIDATION_FAILED_ERROR_MESSAGE);

		// to get jsonObject from payload file
		validationSchema = CommonMethods.createJsonObjectFromFile(testCaseName, FilePathConstants.POST_VALIDATE_LOGIN_FILE_PATH);

		// validating response json object with expected response schema
		Assertions.validateJsonResponse(testCaseName, responseObject, validationSchema);
	}

}

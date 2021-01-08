package com.api.gracenote.testcases.userPreferences;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;

import com.api.gracenote.bo.LoginBO;
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
import com.api.gracenote.utilities.UserPreferenceMapping;

import junit.framework.Assert;

public class userPreferenceTestCases {

	@Test(dataProvider = "getUserPreferenceData", dataProviderClass = ExcelDataProviders.class)
	public void validateUserPrefences(LoginBO login)
	{
		JSONObject		       jsonObject	   = null;
		HttpResponse		   response		   = null;
		UserPreferenceMapping  prefMapp 	   = new UserPreferenceMapping();
		
		// to get current testcase name
		String			testCaseName		= ConfigureTestCaseName.currentTestCaseName();
		jsonObject = CommonMethods.createJsonObjectFromFile(testCaseName, FilePathConstants.POST_USER_PREFERENCE_FILE_PATH);

		jsonObject.put("UserID", login.getUserId());
		jsonObject.put("IncludeChannels", login.getPreferneces());
		jsonObject.put("languagecode", "pt");

		// updating header Object
		Map<String, String> headers = Headers.getCommonHeaders();
		headers.put("context", "abc");

		response = CommonMethods.post(testCaseName, EndPointUriConstants.POST_USER_PREFERENCES, jsonObject.toString(), headers);

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

		Set<String>  keySet      = responseObject.keySet();
		List<String> preferences = Arrays.asList(login.getPreferneces().split(","));
		Map<String,String> preferenceMap = prefMapp.getPreferenceMap();
		
		//for true values
		for(String pref : preferences)
		{
			if(pref.contains("tz"))
			{
				String responseKey = (String) preferenceMap.get("tz");
				String actualResult = responseObject.get(responseKey).toString();
				Assertions.verifyStringEquals(testCaseName, actualResult, pref.split("=")[1]);
				keySet.remove(responseKey);
			}
			else
			{
				String responseKey = (String) preferenceMap.get(pref);
				String actualResult = responseObject.get(responseKey).toString();
				Assertions.verifyStringEquals(testCaseName, actualResult, "true");
				keySet.remove(responseKey);
			}
		}
		
		//for false values
		for(String key : keySet)
		{
			String actualResult = (String) responseObject.get(key).toString();
			Assertions.verifyStringEquals(testCaseName, actualResult, "false");	
		}
		
		//to check on validate login
		jsonObject = CommonMethods.createJsonObjectFromFile(testCaseName, FilePathConstants.POST_VALIDATE_LOGIN_FILE_PATH);
		
		jsonObject.put("EmailID", login.getEmailId());
		jsonObject.put("Password", login.getPassword());
		jsonObject.put("UserType", Integer.parseInt(login.getUserType()));
		jsonObject.put("IsFacebookUser", Integer.parseInt(login.getIsFacebookUser()));
		jsonObject.put("ObjectID", login.getObjectId());
		jsonObject.put("aid", login.getAffiliateId());
		jsonObject.put("languagecode", PropertyReader.getConfigProperty(ConfigPropertiesConstants.LANG_CODE));
	
		// perform post call
		response = CommonMethods.post(testCaseName, EndPointUriConstants.POST_VALIDATE_LOGIN, jsonObject.toString(), headers);

		// verify status code
		flag = Assertions.verifyStatusCode(testCaseName, response.getStatusLine().getStatusCode(),
		        StatusCodeConstants.STATUS_CODE_200);

		// fail testcase if false received
		if (!flag) {
			Assert.fail("Incorrect status code received.");
		}

		// to get response as String
		responseString	= CommonMethods.getResponseAsString(testCaseName, response);

		// to convert response string to json object
		responseObject	= CommonMethods.createJsonObjectFromResposneString(testCaseName, responseString);

		flag = Assertions.verifyContainsJsonKey(testCaseName, responseObject, ResponseKeyConstants.LOGIN_DETAILS_JSON_KEY);
		
		if(!flag)
		{
			Assert.fail("Login Details Not Found.");
		}
		else
		{
			JSONArray responseArray       = (JSONArray) responseObject.get(ResponseKeyConstants.LOGIN_DETAILS_JSON_KEY);
			JSONObject responseJsonObject = (JSONObject) responseArray.get(0);
			keySet 						  = preferenceMap.keySet();
			
			for(String key : keySet)
			{
				String value = responseJsonObject.get(preferenceMap.get(key)).toString();
				
				if(key.equalsIgnoreCase("tz"))
				{
					for(String pref : preferences)
					{
						if(pref.contains("tz"))
						{
							Assertions.verifyStringEquals(testCaseName, value, pref.split("=")[1]);
						}
					}
				}
				else
				{
					if(preferences.contains(key))
					{
						Assertions.verifyStringEquals(testCaseName, value, "true");	
					}
					else
					{
						Assertions.verifyStringEquals(testCaseName, value, "false");	
					}
				}
			}
		}
	}
}

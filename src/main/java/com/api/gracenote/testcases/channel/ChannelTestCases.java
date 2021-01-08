/**
 * 
 */
package com.api.gracenote.testcases.channel;

import org.apache.http.HttpResponse;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;

import com.api.gracenote.bo.ScheduleBO;
import com.api.gracenote.constants.ConfigPropertiesConstants;
import com.api.gracenote.constants.EndPointUriConstants;
import com.api.gracenote.constants.FilePathConstants;
import com.api.gracenote.constants.ResponseKeyConstants;
import com.api.gracenote.constants.StatusCodeConstants;
import com.api.gracenote.coreResources.dataProviders.DBDataProvider;
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
public class ChannelTestCases {

	Logger logger = Logger.getLogger(ChannelTestCases.class);

	@Test(dataProvider = "getPostScheduleSingleStation", dataProviderClass = DBDataProvider.class)
	public void verifyChannelInfo(ScheduleBO schedule) {
		JSONObject		jsonObject		= null;
		HttpResponse	response		= null;

		// to get current testcase name
		String			testCaseName	= ConfigureTestCaseName.currentTestCaseName();

		// to get jsonObject from payload file
		jsonObject = CommonMethods.createJsonObjectFromFile(testCaseName, FilePathConstants.GET_CHANNEL_INFO_FILE_PATH);

		// updating jsonObject as per BO values
		jsonObject.put("languagecode", PropertyReader.getConfigProperty(ConfigPropertiesConstants.LANG_CODE));
		jsonObject.put("progSvcIDs", schedule.getProgramSvcId());
		jsonObject.put("HeadendId", schedule.getHeadendId());
		jsonObject.put("countrycode", schedule.getCountryName());
		jsonObject.put("Devicetype", schedule.getDeviceType());

		// perform post call
		response = CommonMethods.post(testCaseName, EndPointUriConstants.GET_CHANNEL_INFO, jsonObject.toString(),
		        Headers.getCommonHeaders());

		// verify status code
		boolean flag = Assertions.verifyStatusCode(testCaseName, response.getStatusLine().getStatusCode(),
		        StatusCodeConstants.STATUS_CODE_200);

		// fail testcase if false received
		if (!flag) {
			Assert.fail("Incorrect status code received.");
		}

		// to get response as String
		String		responseString	= CommonMethods.getResponseAsString(testCaseName, response);

		// to fetch jsonArray from response
		JSONArray	jsonArray		= new JSONArray(responseString);

		// to convert response string to json object
		JSONObject	responseObject	= jsonArray.getJSONObject(0);

		// validate response Object
		String		programSvcID	= Assertions.verifyNotNull(testCaseName, responseObject, ResponseKeyConstants.PROGRAM_SVC_ID_JSON_KEY);

		Assertions.verifyStringEquals(testCaseName, programSvcID, schedule.getProgramSvcId());

		Assertions.verifyNotNull(testCaseName, responseObject, ResponseKeyConstants.CALL_SIGN_SMALL_JSON_KEY);

		Assertions.verifyNotNull(testCaseName, responseObject, ResponseKeyConstants.ASSET_ID_JSON_KEY);
	}
}

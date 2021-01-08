/**
 * 
 */
package com.api.gracenote.testcases.timezones;

import org.apache.http.HttpResponse;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;

import com.api.gracenote.bo.TimezoneBO;
import com.api.gracenote.constants.ConfigPropertiesConstants;
import com.api.gracenote.constants.EndPointUriConstants;
import com.api.gracenote.constants.ResponseKeyConstants;
import com.api.gracenote.constants.StatusCodeConstants;
import com.api.gracenote.constants.StringConstants;
import com.api.gracenote.coreResources.dataProviders.DBDataProvider;
import com.api.gracenote.coreResources.fileReaders.PropertyReader;
import com.api.gracenote.coreResources.testCaseManager.TestCaseResults;
import com.api.gracenote.utilities.Assertions;
import com.api.gracenote.utilities.CommonMethods;
import com.api.gracenote.utilities.ConfigureTestCaseName;
import com.api.gracenote.utilities.Headers;

import junit.framework.Assert;

/**
 * @author kunal.ashar
 *
 */
public class TimezoneTestCases {

	private static Logger logger = Logger.getLogger(TimezoneTestCases.class);

	@Test(dataProvider = "getTimezoneData", dataProviderClass = DBDataProvider.class)
	public void verifyTimezoneHeadends(TimezoneBO timeZone) {

		// to get current testcase name
		String			testCaseName	= ConfigureTestCaseName.currentTestCaseName();

		// Create new uri as per affId provided by data provider
		String			uri				= EndPointUriConstants.GET_TIMEZONE + timeZone.getCountry() + "/"
		        + PropertyReader.getConfigProperty(ConfigPropertiesConstants.LANG_CODE);

		// Perform GET call
		HttpResponse	response		= CommonMethods.get(testCaseName, uri, Headers.getCommonHeaders());

		// verify status code
		boolean			flag			= Assertions.verifyStatusCode(testCaseName, response.getStatusLine().getStatusCode(),
		        StatusCodeConstants.STATUS_CODE_200);

		// fail testcase if false received
		if (!flag) {
			Assert.fail("Incorrect status code received.");
		}

		// get response as String
		String		responseString	= CommonMethods.getResponseAsString(testCaseName, response);

		// to fetch jsonArray from response
		JSONArray	jsonArray		= new JSONArray(responseString);

		for (int i = 0; i < jsonArray.length(); i++) {

			// getting jsonObject from jsonArray
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			verifyTimeZoneData(testCaseName, jsonObject, timeZone);
		}
	}

	/**
	 * @description verifying timezone data
	 * @param testCaseName
	 * @param jsonObject
	 * @param timeZone
	 */
	private void verifyTimeZoneData(String testCaseName, JSONObject jsonObject, TimezoneBO timeZone) {

		String name = Assertions.verifyNotNull(testCaseName, jsonObject, ResponseKeyConstants.NAME_JSON_KEY);

		if (name.equalsIgnoreCase(timeZone.getTimeZoneName())) {

			String	country		= Assertions.verifyNotNull(testCaseName, jsonObject, ResponseKeyConstants.COUNTRY_JSON_KEY);
			String	headendId	= Assertions.verifyNotNull(testCaseName, jsonObject, ResponseKeyConstants.HEADEND_ID_SMALL_JSON_KEY);
			int		primeTime	= Integer
			        .parseInt(Assertions.verifyNotNull(testCaseName, jsonObject, ResponseKeyConstants.PRIME_TIME_JSON_KEY));
			int		utcOffSet	= Integer
			        .parseInt(Assertions.verifyNotNull(testCaseName, jsonObject, ResponseKeyConstants.STD_UTC_OFF_SET_JSON_KEY_CAPS));

			switch (name) {

				case StringConstants.EASTERN_DEFAULT_LINEUP:
					Assertions.verifyStringEquals(testCaseName, name, StringConstants.EASTERN_DEFAULT_LINEUP);
					Assertions.verifyStringEquals(testCaseName, country, timeZone.getCountry());
					Assertions.verifyStringEquals(testCaseName, headendId, timeZone.getHeadendId());
					Assertions.verifyIntEquals(testCaseName, primeTime, timeZone.getPrimeTime());
					Assertions.verifyIntEquals(testCaseName, utcOffSet, timeZone.getUtcOffSet());
					break;

				case StringConstants.PACIFIC_DEFAULT_LINEUP:
					Assertions.verifyStringEquals(testCaseName, name, StringConstants.PACIFIC_DEFAULT_LINEUP);
					Assertions.verifyStringEquals(testCaseName, country, timeZone.getCountry());
					Assertions.verifyStringEquals(testCaseName, headendId, timeZone.getHeadendId());
					Assertions.verifyIntEquals(testCaseName, primeTime, timeZone.getPrimeTime());
					Assertions.verifyIntEquals(testCaseName, utcOffSet, timeZone.getUtcOffSet());
					break;

				case StringConstants.MOUNTAIN_DEFAULT_LINEUP:
					Assertions.verifyStringEquals(testCaseName, name, StringConstants.MOUNTAIN_DEFAULT_LINEUP);
					Assertions.verifyStringEquals(testCaseName, country, timeZone.getCountry());
					Assertions.verifyStringEquals(testCaseName, headendId, timeZone.getHeadendId());
					Assertions.verifyIntEquals(testCaseName, primeTime, timeZone.getPrimeTime());
					Assertions.verifyIntEquals(testCaseName, utcOffSet, timeZone.getUtcOffSet());
					break;

				case StringConstants.CENTRAL_DEFAULT_LINEUP:
					Assertions.verifyStringEquals(testCaseName, name, StringConstants.CENTRAL_DEFAULT_LINEUP);
					Assertions.verifyStringEquals(testCaseName, country, timeZone.getCountry());
					Assertions.verifyStringEquals(testCaseName, headendId, timeZone.getHeadendId());
					Assertions.verifyIntEquals(testCaseName, primeTime, timeZone.getPrimeTime());
					Assertions.verifyIntEquals(testCaseName, utcOffSet, timeZone.getUtcOffSet());
					break;

				case StringConstants.ALASKAN_DEFAULT_LINEUP:
					Assertions.verifyStringEquals(testCaseName, name, StringConstants.ALASKAN_DEFAULT_LINEUP);
					Assertions.verifyStringEquals(testCaseName, country, timeZone.getCountry());
					Assertions.verifyStringEquals(testCaseName, headendId, timeZone.getHeadendId());
					Assertions.verifyIntEquals(testCaseName, primeTime, timeZone.getPrimeTime());
					Assertions.verifyIntEquals(testCaseName, utcOffSet, timeZone.getUtcOffSet());
					break;

				case StringConstants.HAWAIIAN_DEFAULT_LINEUP:
					Assertions.verifyStringEquals(testCaseName, name, StringConstants.HAWAIIAN_DEFAULT_LINEUP);
					Assertions.verifyStringEquals(testCaseName, country, timeZone.getCountry());
					Assertions.verifyStringEquals(testCaseName, headendId, timeZone.getHeadendId());
					Assertions.verifyIntEquals(testCaseName, primeTime, timeZone.getPrimeTime());
					Assertions.verifyIntEquals(testCaseName, utcOffSet, timeZone.getUtcOffSet());
					break;

				case StringConstants.EASTERN_DEFAULT_CANADIAN_LINEUP:
					Assertions.verifyStringEquals(testCaseName, name, StringConstants.EASTERN_DEFAULT_CANADIAN_LINEUP);
					Assertions.verifyStringEquals(testCaseName, country, timeZone.getCountry());
					Assertions.verifyStringEquals(testCaseName, headendId, timeZone.getHeadendId());
					Assertions.verifyIntEquals(testCaseName, primeTime, timeZone.getPrimeTime());
					Assertions.verifyIntEquals(testCaseName, utcOffSet, timeZone.getUtcOffSet());
					break;

				case StringConstants.PACIFIC_DEFAULT_CANADIAN_LINEUP:
					Assertions.verifyStringEquals(testCaseName, name, StringConstants.PACIFIC_DEFAULT_CANADIAN_LINEUP);
					Assertions.verifyStringEquals(testCaseName, country, timeZone.getCountry());
					Assertions.verifyStringEquals(testCaseName, headendId, timeZone.getHeadendId());
					Assertions.verifyIntEquals(testCaseName, primeTime, timeZone.getPrimeTime());
					Assertions.verifyIntEquals(testCaseName, utcOffSet, timeZone.getUtcOffSet());
					break;

				case StringConstants.MOUNTAIN_DEFAULT_CANADIAN_LINEUP:
					Assertions.verifyStringEquals(testCaseName, name, StringConstants.MOUNTAIN_DEFAULT_CANADIAN_LINEUP);
					Assertions.verifyStringEquals(testCaseName, country, timeZone.getCountry());
					Assertions.verifyStringEquals(testCaseName, headendId, timeZone.getHeadendId());
					Assertions.verifyIntEquals(testCaseName, primeTime, timeZone.getPrimeTime());
					Assertions.verifyIntEquals(testCaseName, utcOffSet, timeZone.getUtcOffSet());
					break;

				case StringConstants.CENTRAL_DEFAULT_CANADIAN_LINEUP:
					Assertions.verifyStringEquals(testCaseName, name, StringConstants.CENTRAL_DEFAULT_CANADIAN_LINEUP);
					Assertions.verifyStringEquals(testCaseName, country, timeZone.getCountry());
					Assertions.verifyStringEquals(testCaseName, headendId, timeZone.getHeadendId());
					Assertions.verifyIntEquals(testCaseName, primeTime, timeZone.getPrimeTime());
					Assertions.verifyIntEquals(testCaseName, utcOffSet, timeZone.getUtcOffSet());
					break;

				default:
					logger.error("Invalid timezone name received. Name :: " + name);
					TestCaseResults.addResult(testCaseName, StringConstants.ASSERT_KEY, "Invalid timezone name received. Name :: " + name);
					Assert.fail("Invalid timezone name received.");
					break;
			}
		}

	}
}

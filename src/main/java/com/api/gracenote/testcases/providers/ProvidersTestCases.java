/**
 * 
 */
package com.api.gracenote.testcases.providers;

import org.apache.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;

import com.api.gracenote.bo.ProvidersBO;
import com.api.gracenote.constants.ConfigPropertiesConstants;
import com.api.gracenote.constants.EndPointUriConstants;
import com.api.gracenote.constants.ResponseKeyConstants;
import com.api.gracenote.constants.StatusCodeConstants;
import com.api.gracenote.constants.StringConstants;
import com.api.gracenote.coreResources.dataProviders.DBDataProvider;
import com.api.gracenote.coreResources.dataProviders.ExcelDataProviders;
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
public class ProvidersTestCases {

	@Test(dataProvider = "getPostalCodeProvidersData", dataProviderClass = DBDataProvider.class)
	public void getPostalCodeProviders(ProvidersBO providers) {

		JSONArray		providersArray	= null;

		// to get current testcase name
		String			testCaseName	= ConfigureTestCaseName.currentTestCaseName();

		// Create new uri as per affId provided by data provider
		String			uri				= EndPointUriConstants.GET_POSTAL_CODES_PROVIDERS + providers.getCountry() + "/"
		        + providers.getPostalCode() + "/" + StringConstants.GAPZAP_AFFILIATE + "/"
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

		// to get response as String
		String		responseString	= CommonMethods.getResponseAsString(testCaseName, response);

		// to convert response string to json object
		JSONObject	responseObject	= CommonMethods.createJsonObjectFromResposneString(testCaseName, responseString);

		// validate responseObject
		Assertions.verifyNotNull(testCaseName, responseObject, ResponseKeyConstants.DST_UTC_OFF_SET_JSON_KEY_CAPS);
		Assertions.verifyNotNull(testCaseName, responseObject, ResponseKeyConstants.STD_UTC_OFF_SET_JSON_KEY_CAPS);
		Assertions.verifyNotNull(testCaseName, responseObject, ResponseKeyConstants.DST_START_JSON_KEY_CAPS);
		Assertions.verifyNotNull(testCaseName, responseObject, ResponseKeyConstants.DST_END_JSON_KEY_CAPS);
		Assertions.verifyNotNull(testCaseName, responseObject, ResponseKeyConstants.PROVIDERS_JSON_KEY);

		// to fetch providers array from json response
		providersArray = CommonMethods.getJsonArrayFromJsonObject(testCaseName, responseObject, ResponseKeyConstants.PROVIDERS_JSON_KEY);

		// iterating over all providers
		for (int i = 0; i < providersArray.length(); i++) {

			JSONObject	providerObject	= providersArray.getJSONObject(i);

			String		providerType	= Assertions.verifyNotNull(testCaseName, providerObject, ResponseKeyConstants.TYPE_JSON_KEY);

			String		postalCode		= Assertions.verifyNotNull(testCaseName, providerObject, ResponseKeyConstants.POSTAL_CODE_JSON_KEY);

			// verifying provider type and corresponding postal code
			switch (providerType) {

				case StringConstants.SATELLITE:

					if (postalCode.equalsIgnoreCase(StringConstants.ALL)) {
						Assertions.verifyStringEquals(testCaseName, postalCode, StringConstants.ALL);
					} else if (postalCode.equalsIgnoreCase(providers.getPostalCode())) {
						Assertions.verifyStringEquals(testCaseName, postalCode, providers.getPostalCode());
					} else {
						TestCaseResults.addResult(testCaseName, StringConstants.ASSERT_KEY,
						        "Incorrect value found for postal code. Response Value :: " + providers.getPostalCode());
						Assert.fail();
					}
					break;

				case StringConstants.CABLE:
					Assertions.verifyStringEquals(testCaseName, postalCode, providers.getPostalCode());
					break;

				case StringConstants.OTA:
					Assertions.verifyStringEquals(testCaseName, postalCode, providers.getPostalCode());
					break;

				default:
					TestCaseResults.addResult(testCaseName, StringConstants.ASSERT_KEY,
					        "Incorrect value found for provider Type. Response Value :: " + providerType);
					Assert.fail();
			}
		}
	}

	@Test(dataProvider = "getPostalCodeProvidersNegativeData", dataProviderClass = ExcelDataProviders.class)
	public void getPostalCodeProvidersNegativeScenarios(ProvidersBO providers) {
		// to get current testcase name
		String			testCaseName	= ConfigureTestCaseName.currentTestCaseName();

		// Create new uri as per affId provided by data provider
		String			uri				= EndPointUriConstants.GET_POSTAL_CODES_PROVIDERS + providers.getCountry() + "/"
		        + providers.getPostalCode() + "/" + StringConstants.GAPZAP_AFFILIATE + "/"
		        + PropertyReader.getConfigProperty(ConfigPropertiesConstants.LANG_CODE);

		// Perform GET call
		HttpResponse	response		= CommonMethods.get(testCaseName, uri, Headers.getCommonHeaders());

		// verify status code
		boolean			flag			= Assertions.verifyStatusCode(testCaseName, response.getStatusLine().getStatusCode(),
		        providers.getExpectedStatusCode());

		// fail testcase if false received
		if (!flag) {
			Assert.fail("Incorrect status code received.");
		}
	}

	@Test(dataProvider = "getDstOffsetForPostalCodeByCountryData", dataProviderClass = DBDataProvider.class)
	public void getDstOffsetForPostalCodeByCountry(ProvidersBO providers) {

		// to get current testcase name
		String			testCaseName	= ConfigureTestCaseName.currentTestCaseName();

		// Create new uri as per affId provided by data provider
		String			uri				= EndPointUriConstants.GET_DST_OFF_SET_FOR_POSTAL_CODE_BY_COUNTRY + providers.getPostalCode() + "/"
		        + providers.getCountry() + "/" + PropertyReader.getConfigProperty(ConfigPropertiesConstants.LANG_CODE);

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

		// converting response to String and creating responseObject
		JSONObject	responseObject	= CommonMethods.createJsonObjectFromResposneString(testCaseName, responseString);

		// Assertions for not null
		Assertions.verifyNotNull(testCaseName, responseObject, ResponseKeyConstants.DST_START_JSON_KEY_CAPS);
		Assertions.verifyNotNull(testCaseName, responseObject, ResponseKeyConstants.DST_END_JSON_KEY_CAPS);

		String	primeTime		= Assertions.verifyNotNull(testCaseName, responseObject, ResponseKeyConstants.PRIME_TIME_JSON_KEY);
		String	stdUtcOffSet	= Assertions.verifyNotNull(testCaseName, responseObject, ResponseKeyConstants.STD_UTC_OFF_SET_JSON_KEY_CAPS);

		// verifying correct data is displayed in response
		Assertions.verifyStringEquals(testCaseName, stdUtcOffSet, providers.getStdUtcOffSet());
		Assertions.verifyStringEquals(testCaseName, primeTime, providers.getPrimeTime());
	}
}

/**
 * 
 */
package com.api.gracenote.testcases.search;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;

import com.api.gracenote.bo.SearchBO;
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
public class SearchTestCases {

	private String	keywordKey		= "keyword=";
	private String	aidKey			= "aid=";
	private String	countryCodeKey	= "countrycode=";
	private String	programSvcIdKey	= "prgsvcids=";
	private String	isOvdEnabledKey	= "isovdenabled=true";
	private String	headendIdKey	= "headendid=";
	private String	languageCodeKey	= "languagecode=";
	private String	nextParamKey	= "&";

	@Test(dataProvider = "getAdultContentSearchData", dataProviderClass = DBDataProvider.class)
	public void verifyAdultContentSearch(SearchBO search) {

		// iterate over all english programs
		if (search.isEnglish()) {

			boolean	isAdult			= false;
			String	searchParams	= null;

			// to get current testcase name
			String	testCaseName	= ConfigureTestCaseName.currentTestCaseName();

			// Create new uri as per affId provided by data provider
			String	uri				= EndPointUriConstants.GET_SEARCH_AUTOCOMPLETE;

			// appending all query params
			try {
				searchParams = keywordKey + URLEncoder.encode(search.getProgramName(), StringConstants.ENCODING_TYPE) + nextParamKey
				        + aidKey + StringConstants.GAPZAP_AFFILIATE + nextParamKey + countryCodeKey + search.getCountryName() + nextParamKey
				        + programSvcIdKey + search.getProgramSeriesId() + nextParamKey + isOvdEnabledKey + nextParamKey + headendIdKey
				        + nextParamKey + languageCodeKey + PropertyReader.getConfigProperty(ConfigPropertiesConstants.LANG_CODE);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			// creating final uri
			String			finalUri	= uri + searchParams;

			// Perform GET call
			HttpResponse	response	= CommonMethods.get(testCaseName, finalUri, Headers.getCommonHeaders());

			// verify status code
			boolean			flag		= Assertions.verifyStatusCode(testCaseName, response.getStatusLine().getStatusCode(),
			        StatusCodeConstants.STATUS_CODE_200);

			// fail testcase if false received
			if (!flag) {
				Assert.fail("Incorrect status code received.");
			}

			// convert response to String
			String		responseString	= CommonMethods.getResponseAsString(testCaseName, response);

			// creating jsonObject from response String
			JSONObject	responseObject	= CommonMethods.createJsonObjectFromResposneString(testCaseName, responseString);

			// validate response to check adult flag
			isAdult = validateSearchResponse(testCaseName, search, responseObject);

			if (!isAdult) {
				TestCaseResults.addResult(testCaseName, StringConstants.ASSERT_KEY, "Adult flag returned false.");
				Assert.fail();
			}
		}
	}

	@Test(dataProvider = "getAdultContentSearchData", dataProviderClass = DBDataProvider.class)
	public void verifyAdultContentSearchPartialTitle(SearchBO search) {

		// iterate over all english programs
		if (search.isEnglish()) {

			String	searchParams	= null;

			// to get current testcase name
			String	testCaseName	= ConfigureTestCaseName.currentTestCaseName();

			// Create new uri as per affId provided by data provider
			String	uri				= EndPointUriConstants.GET_SEARCH_AUTOCOMPLETE;

			// appending all query params
			try {
				StringBuilder sb = new StringBuilder(search.getProgramName());

				searchParams = keywordKey + URLEncoder.encode(sb.substring(0, (search.getProgramName().length() - 3)), "UTF-8")
				        + nextParamKey + aidKey + StringConstants.GAPZAP_AFFILIATE + nextParamKey + countryCodeKey + search.getCountryName()
				        + nextParamKey + programSvcIdKey + search.getProgramSeriesId() + nextParamKey + isOvdEnabledKey + nextParamKey
				        + headendIdKey + nextParamKey + languageCodeKey
				        + PropertyReader.getConfigProperty(ConfigPropertiesConstants.LANG_CODE);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			// creating final uri
			String			finalUri	= uri + searchParams;

			// Perform GET call
			HttpResponse	response	= CommonMethods.get(testCaseName, finalUri, Headers.getCommonHeaders());

			// verify status code
			boolean			flag		= Assertions.verifyStatusCode(testCaseName, response.getStatusLine().getStatusCode(),
			        StatusCodeConstants.STATUS_CODE_200);

			// fail testcase if false received
			if (!flag) {
				Assert.fail("Incorrect status code received.");
			}

			// convert response to String
			CommonMethods.getResponseAsString(testCaseName, response);
		}
	}

	@Test(dataProvider = "getAdultContentSearchNonEnglishData", dataProviderClass = DBDataProvider.class)
	public void verifyAdultContentSearchNonEnglishTitle(SearchBO search) {

		// iterate over all non english programs
		if (!search.isEnglish()) {

			boolean	isAdult			= false;
			String	searchParams	= null;

			// to get current testcase name
			String	testCaseName	= ConfigureTestCaseName.currentTestCaseName();

			// Create new uri as per affId provided by data provider
			String	uri				= EndPointUriConstants.GET_SEARCH_AUTOCOMPLETE;

			// appending all query params
			try {
//				searchParams = keywordKey + URLEncoder.encode(search.getProgramName(), "UTF-8") + nextParamKey + aidKey
//				        + StringConstants.GAPZAP_AFFILIATE + nextParamKey + countryCodeKey + search.getCountryName() + nextParamKey
//				        + programSvcIdKey + search.getProgramSeriesId() + nextParamKey + isOvdEnabledKey + nextParamKey + headendIdKey
//				        + nextParamKey + languageCodeKey + PropertyReader.getConfigProperty(ConfigPropertiesConstants.LANG_CODE);
				
				searchParams = keywordKey + URLEncoder.encode(search.getProgramName(), "UTF-8") + nextParamKey + aidKey
				        + StringConstants.GAPZAP_AFFILIATE + nextParamKey + countryCodeKey + search.getCountryName() + nextParamKey
				        + programSvcIdKey + nextParamKey + isOvdEnabledKey + nextParamKey + headendIdKey
				        + nextParamKey + languageCodeKey + PropertyReader.getConfigProperty(ConfigPropertiesConstants.LANG_CODE);
				
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			// creating final uri
			String			finalUri	= uri + searchParams;

			// Perform GET call
			HttpResponse	response	= CommonMethods.get(testCaseName, finalUri, Headers.getCommonHeaders());

			// verify status code
			boolean			flag		= Assertions.verifyStatusCode(testCaseName, response.getStatusLine().getStatusCode(),
			        StatusCodeConstants.STATUS_CODE_200);

			// fail testcase if false received
			if (!flag) {
				Assert.fail("Incorrect status code received.");
			}

			// convert response to String
			String		responseString	= CommonMethods.getResponseAsString(testCaseName, response);

			// creating jsonObject from response String
			JSONObject	responseObject	= CommonMethods.createJsonObjectFromResposneString(testCaseName, responseString);

			// validate response to check adult flag
			isAdult = validateSearchResponse(testCaseName, search, responseObject);

			if (!isAdult) {
				TestCaseResults.addResult(testCaseName, StringConstants.ASSERT_KEY, "Adult flag returned false , Expected ProgramSeriesID "+search.getTmsId()+" is not found in reponse");
				Assert.fail();
			}
		}
	}

	/**
	 * @description iterating over all shows and verifying programseriesid
	 * @param search
	 * @param responseObject
	 */
	private boolean validateSearchResponse(String testCaseName, SearchBO search, JSONObject responseObject) {

		boolean isAdult = false;

		// TV Show = 1
		if (Assertions.verifyContainsJsonKey(testCaseName, responseObject, ResponseKeyConstants.TV_SHOWS_JSON_KEY)) {
			JSONArray tvShowArray = CommonMethods.getJsonArrayFromJsonObject(testCaseName, responseObject,
			        ResponseKeyConstants.TV_SHOWS_JSON_KEY);

			for (int i = 0; i < tvShowArray.length(); i++) {
				JSONObject	showObject		= tvShowArray.getJSONObject(i);
				String		programSeriesId	= Assertions.verifyNotNull(testCaseName, showObject,
				        ResponseKeyConstants.PROGRAM_SERIES_ID_JSON_KEY_LOWER);
				if (programSeriesId.equalsIgnoreCase(search.getTmsId())) {
					isAdult = true;
				}
				else
				{
					programSeriesId = programSeriesId+"0000";
					if (programSeriesId.equalsIgnoreCase(search.getTmsId())) {
						isAdult = true;
					}
				}
			}
		}
		// Movie = 2
		if (Assertions.verifyContainsJsonKey(testCaseName, responseObject, ResponseKeyConstants.MOVIE_JSON_KEY)) {
			JSONArray tvShowArray = CommonMethods.getJsonArrayFromJsonObject(testCaseName, responseObject,
			        ResponseKeyConstants.MOVIE_JSON_KEY);

			for (int i = 0; i < tvShowArray.length(); i++) {
				JSONObject	showObject		= tvShowArray.getJSONObject(i);
				String		programSeriesId	= Assertions.verifyNotNull(testCaseName, showObject,
				        ResponseKeyConstants.PROGRAM_SERIES_ID_JSON_KEY_LOWER);
				if (programSeriesId.equalsIgnoreCase(search.getTmsId())) {
					isAdult = true;
				}
				else
				{
					programSeriesId = programSeriesId+"0000";
					if (programSeriesId.equalsIgnoreCase(search.getTmsId())) {
						isAdult = true;
					}
				}
			}
		}

		return isAdult;
	}
}

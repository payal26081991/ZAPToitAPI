/**
 * 
 */
package com.api.gracenote.testcases.affiliateProperties;

import org.apache.http.HttpResponse;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.testng.annotations.Test;

import com.api.gracenote.bo.AffiliatePropertiesBO;
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
public class AffiliatePropertiesTestCases {
	Logger logger = Logger.getLogger(AffiliatePropertiesTestCases.class);

	@Test(dataProvider = "getAffiliatesPropertiesData", dataProviderClass = DBDataProvider.class)
	public void getAffiliatesProperties(AffiliatePropertiesBO affProp) {

		// to get current testcase name
		String			testCaseName	= ConfigureTestCaseName.currentTestCaseName();

		// Create new uri as per affId provided by data provider
		String			uri				= EndPointUriConstants.GET_AFFILIATE_URL + affProp.getAffiliateTag() + "/"
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

		// converting response to String and creating responseObject
		JSONObject	responseObject	= CommonMethods.createJsonObjectFromResposneString(testCaseName, responseString);

		// validate responseObject
		Assertions.verifyNotNull(testCaseName, responseObject, ResponseKeyConstants.PRODUCT_TYPE_JSON_KEY);
		Assertions.verifyNotNull(testCaseName, responseObject, ResponseKeyConstants.DEFAULT_POSTAL_CODE_JSON_KEY);
		Assertions.verifyNotNull(testCaseName, responseObject, ResponseKeyConstants.DEFAULT_HEADEND_JSON_KEY);
		Assertions.verifyNotNull(testCaseName, responseObject, ResponseKeyConstants.DEFAULT_TIMEZONE_JSON_KEY);
		Assertions.verifyNotNull(testCaseName, responseObject, ResponseKeyConstants.DEFAULT_LANGUAGE_JSON_KEY);
		Assertions.verifyNotNull(testCaseName, responseObject, ResponseKeyConstants.DEFAULT_COUNTRY_JSON_KEY);
		Assertions.verifyNotNull(testCaseName, responseObject, ResponseKeyConstants.OVD_JSON_KEY);
		Assertions.verifyNotNull(testCaseName, responseObject, ResponseKeyConstants.ADS_JSON_KEY);

		if (Assertions.verifyContainsJsonKey(testCaseName, responseObject, ResponseKeyConstants.DST_UTC_OFF_SET_JSON_KEY)) {
			Assertions.verifyNotNull(testCaseName, responseObject, ResponseKeyConstants.DST_UTC_OFF_SET_JSON_KEY);
		} else {
			TestCaseResults.addResult(testCaseName, StringConstants.ASSERT_KEY,
			        "jsonKey :: dstutcoffset not displayed for affilifte :: " + affProp.getAffiliateTag());
		}

		Assertions.verifyNotNull(testCaseName, responseObject, ResponseKeyConstants.HEADEND_NAME_JSON_KEY);
		Assertions.verifyNotNull(testCaseName, responseObject, ResponseKeyConstants.DEVICE_JSON_KEY);
		Assertions.verifyNotNull(testCaseName, responseObject, ResponseKeyConstants.STD_UTC_OFF_SET_JSON_KEY);
		Assertions.verifyNotNull(testCaseName, responseObject, ResponseKeyConstants.DST_END_JSON_KEY);
		Assertions.verifyNotNull(testCaseName, responseObject, ResponseKeyConstants.DST_START_JSON_KEY);
		Assertions.verifyNotNull(testCaseName, responseObject, ResponseKeyConstants.PROGRAM_SVC_IDS_JSON_KEY);
		Assertions.verifyNotNull(testCaseName, responseObject, ResponseKeyConstants.PRODUCT_ID_JSON_KEY);

		String	headendName		= Assertions.verifyNotNull(testCaseName, responseObject, ResponseKeyConstants.HEADEND_NAME_JSON_KEY);
		String	defaultHeadend	= Assertions.verifyNotNull(testCaseName, responseObject, ResponseKeyConstants.DEFAULT_HEADEND_JSON_KEY);

		if (headendName == null || headendName.equalsIgnoreCase("")) {
			TestCaseResults.addResult(testCaseName, StringConstants.ASSERT_KEY,
			        "HeadendName found null or blank. HeadendNameValue :: " + headendName);
			Assert.fail("HeadendName found null or blank.");
		}

		if (defaultHeadend.equals(ResponseKeyConstants.LINEUP_ID_JSON_KEY)) {
			Assertions.verifyStringEquals(testCaseName, headendName, StringConstants.LOCAL_OVER_THE_AIR_BROADCAST);
		}

		if (!(defaultHeadend.equals(ResponseKeyConstants.LINEUP_ID_JSON_KEY))) {
			Assertions.verifyBooleanEquals(testCaseName, headendName.length() > 0, true);
		}

		boolean displayeAds = Boolean.valueOf(Assertions.verifyNotNull(testCaseName, responseObject, ResponseKeyConstants.ADS_JSON_KEY));
		Assertions.verifyBooleanEquals(testCaseName, displayeAds, false);

		boolean displayOvd = Boolean.valueOf(Assertions.verifyNotNull(testCaseName, responseObject, ResponseKeyConstants.OVD_JSON_KEY));
		Assertions.verifyBooleanEquals(testCaseName, displayOvd, false);
	}
}

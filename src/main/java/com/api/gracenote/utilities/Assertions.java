package com.api.gracenote.utilities;

import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;

import com.api.gracenote.constants.StringConstants;
import com.api.gracenote.coreResources.testCaseManager.TestCaseResults;

/**
 * @author kunal.ashar
 *
 */
public class Assertions {

	/**
	 * 
	 * @description verify status code of received response
	 * @param actual
	 * @param expected
	 * @return boolean
	 */
	public static boolean verifyStatusCode(String testCaseName, int actual, int expected) {
		boolean flag = (actual == expected);
		if (flag) {
			TestCaseResults.addResult(testCaseName, StringConstants.STATUS_CODE_KEY, actual + "");
		} else {
			TestCaseResults.addResult(testCaseName, StringConstants.STATUS_CODE_KEY, actual + "");
			TestCaseResults.addResult(testCaseName, StringConstants.ASSERT_KEY,
			        "Status Code received as :: " + actual + " Expected Code :: " + expected);
		}
		return flag;
	}

	/**
	 * 
	 * @description verifies if null value received for any jsonKey
	 * @param jsonObject
	 * @param jsonKey
	 */
	public static String verifyNotNull(String testCaseName, JSONObject jsonObject, String jsonKey) {
		String s = null;
		if (jsonKey != null) {
			try {
				s = jsonObject.get(jsonKey).toString();

				if (s == null || s.equals("null")) {
					TestCaseResults.addResult(testCaseName, StringConstants.ASSERT_KEY,
					        "null OR Blank value received for jsonKey :: " + jsonKey);
					Assert.fail(jsonKey + " receivd as null");
				}
			} catch (JSONException e) {
				TestCaseResults.addResult(testCaseName, StringConstants.ASSERT_KEY, "Key :: " + jsonKey + " is not present in jsonObject");
				Assert.fail();
			}
		}
		return s;
	}

	/**
	 * 
	 * @description used to verify if jsonObject contains given jsonKey
	 * @param jsonObject
	 * @param jsonKey
	 * @return
	 */
	public static boolean verifyContainsJsonKey(String testCaseName, JSONObject jsonObject, String jsonKey) {
		boolean flag = false;
		if (jsonKey != null) {
			if (jsonObject.has(jsonKey)) {
				flag = true;
			} else {
				TestCaseResults.addResult(testCaseName, StringConstants.ASSERT_KEY, "Key :: " + jsonKey + " is not present in jsonObject");
			}
		}
		return flag;
	}

	/**
	 * 
	 * @description to compare two Strings
	 * @param actual
	 * @param expected
	 * @return
	 */
	public static boolean verifyStringEquals(String testCaseName, String actual, String expected) {
		boolean flag = false;
		if (actual != null && expected != null) {
			flag = actual.equalsIgnoreCase(expected);
		}

		// to mark testcase as fail if flag is false
		if (!flag) {
			TestCaseResults.addResult(testCaseName, StringConstants.ASSERT_KEY,
			        "Actual String received as :: " + actual + " & Expected String received as :: " + expected);

			Assert.fail("Actual String received as :: " + actual + " & Expected String received as :: " + expected);
		}
		return flag;
	}

	/**
	 * 
	 * @description to compare two boolean values
	 * @param actual
	 * @param expected
	 */
	public static void verifyBooleanEquals(String testCaseName, boolean actual, boolean expected) {

		if (!(actual == expected)) {
			TestCaseResults.addResult(testCaseName, StringConstants.ASSERT_KEY,
			        "Actual boolean received as :: " + actual + " & Expected boolean received as :: " + expected);

			Assert.fail("Actual boolean received as :: " + actual + " & Expected boolean received as :: " + expected);
		}
	}

	/**
	 * 
	 * @description validating json resposne with created json schema to verify all
	 *              field attributes.
	 * @param responseObject
	 * @param validationSchema
	 */
	public static void validateJsonResponse(String testCaseName, JSONObject responseObject, JSONObject validationSchema) {
		Schema schema = null;
		try {
			schema = SchemaLoader.load(validationSchema);
			schema.validate(responseObject);
		} catch (Exception e) {
			TestCaseResults.addResult(testCaseName, StringConstants.ASSERT_KEY, "Json Response not matched with Json Schema.");
			Assert.fail("Schema Violations");
		}
	}

	/**
	 * @description to verify number equals
	 * @param testCaseName
	 * @param actual
	 * @param expected
	 */
	public static void verifyIntEquals(String testCaseName, int actual, int expected) {
		boolean flag = (actual == expected);
		if (!flag) {
			TestCaseResults.addResult(testCaseName, StringConstants.ASSERT_KEY,
			        "Actual int as :: " + actual + " Expected int :: " + expected);
			Assert.fail("Numbers not equals");
		}
	}
}

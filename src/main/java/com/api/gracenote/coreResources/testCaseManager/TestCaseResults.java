/**
 * 
 */
package com.api.gracenote.coreResources.testCaseManager;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.api.gracenote.constants.StringConstants;
import com.api.gracenote.coreResources.listeners.TestNgListeners;

/**
 * @author kunal.ashar
 *
 */
public class TestCaseResults {

	private static Logger											logger		= Logger.getLogger(TestCaseResults.class);

	private static Map<String, Map<Integer, Map<String, String>>>	testCaseMap	= new HashMap<String, Map<Integer, Map<String, String>>>();

	/**
	 * @description to add results to testCaseMap object
	 * @param testCaseName
	 * @param key
	 * @param value
	 */
	public static void addResult(String testCaseName, String key, String value) {

		// to fetch current iteration count
		int									iterationCount	= TestNgListeners.getCurrentIterationCount(testCaseName);

		// creating map objects
		Map<String, String>					valueMap		= new HashMap<String, String>();
		Map<Integer, Map<String, String>>	iterationMap	= new HashMap<Integer, Map<String, String>>();

		if (testCaseMap.get(testCaseName) != null) {
			iterationMap = testCaseMap.get(testCaseName);
			if (iterationMap != null) {
				if (iterationMap.get(iterationCount) != null) {
					valueMap = iterationMap.get(iterationCount);
				}
			}
		}

		// to fetch assert value already present in map
		String oldValue = null;
		if (valueMap != null) {
			oldValue = valueMap.get(key);
		}

		if (oldValue != null) {
			if (value != null) {
				oldValue = oldValue + "<br/>" + value;
				valueMap.put(key, oldValue);
			}
		} else {
			// creating key value child map
			if (value != null) {
				valueMap.put(key, value);
			} else {
				valueMap.put(key, "");
			}
		}

		// adding iteration count values to map
		if (valueMap != null) {
			iterationMap.put(iterationCount, valueMap);
		}

		// adding values to parent map
		testCaseMap.put(testCaseName, iterationMap);

		// printing failure message to console
		if (key.equalsIgnoreCase(StringConstants.ASSERT_KEY)) {
			logger.error(testCaseName + " failed for iteration " + iterationCount + " with message :: " + value);
		}

	}

	public static Map<String, Map<Integer, Map<String, String>>> getTestCaseIterationsResults() {
		return testCaseMap;
	}
}

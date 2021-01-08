/**
 * 
 */
package com.api.gracenote.coreResources.listeners;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.api.gracenote.constants.FilePathConstants;
import com.api.gracenote.constants.StringConstants;
import com.api.gracenote.coreResources.directoryManager.Directory;
import com.api.gracenote.coreResources.testCaseManager.TestCaseResults;

/**
 * @author kunal.ashar
 *
 */
public class TestNgListeners implements ITestListener {

	private static Logger								logger					= Logger.getLogger(TestNgListeners.class);

	private static Map<String, Integer>					testCaseIterationCount	= new HashMap<String, Integer>();
	private static Map<String, Map<String, Integer>>	testCaseResultMap		= new HashMap<String, Map<String, Integer>>();

	public void onTestStart(ITestResult result) {

		String testCaseName = result.getName();

		// to get current iteration count of a testcase
		if (testCaseIterationCount.size() > 0) {

			if (testCaseIterationCount.get(testCaseName) != null) {

				testCaseIterationCount.put(testCaseName, testCaseIterationCount.get(testCaseName) + 1);
			} else {
				testCaseIterationCount.put(testCaseName, 1);
			}

		} else {
			testCaseIterationCount.put(testCaseName, 1);
		}

		// used to print testcase name in logs
		MDC.put("testCaseName", testCaseName);

		// creating iteration count wise testcase directory
		new Directory().createTestCaseDirectory(testCaseName, getCurrentIterationCount(testCaseName));

		logger.info(
		        "Testcase Execution Started for TC :: " + testCaseName + " Iteration Count :: " + testCaseIterationCount.get(testCaseName));
	}

	@SuppressWarnings("static-access")
	public void onTestSuccess(ITestResult result) {

		String	testCaseName	= result.getName();
		String	statusCode		= null;

		// fetching statusCode of current iteration
		try {
			statusCode = new TestCaseResults().getTestCaseIterationsResults().get(testCaseName).get(getCurrentIterationCount(testCaseName))
			        .get(StringConstants.STATUS_CODE_KEY);
		} catch (Exception e) {
			logger.error("Exception Occured while fetching statusCode of testcase :: " + testCaseName, e);
		}

		// to mark testcase as failed if statusCode is received as null
		if (statusCode == null || statusCode.equalsIgnoreCase("null")) {
			// adding fail count to map
			addResult(result.getName(), StringConstants.FAILED);

			// adding status to print in report
			TestCaseResults.addResult(result.getName(), StringConstants.RESULT_KEY, StringConstants.FAILED);
		} else {
			// adding pass count to map
			addResult(result.getName(), StringConstants.PASSED);

			// adding status to print in report
			TestCaseResults.addResult(result.getName(), StringConstants.RESULT_KEY, StringConstants.PASSED);

			logger.info("Testcase Execution Completed Successfully for TC :: " + result.getName());
		}

		// to remove dump files if testcase is passed
		removeDumpFiles(result.getName());
	}

	public void onTestFailure(ITestResult result) {
		// adding fail count to map
		addResult(result.getName(), StringConstants.FAILED);

		// adding status to print in report
		TestCaseResults.addResult(result.getName(), StringConstants.RESULT_KEY, StringConstants.FAILED);
	}

	public void onTestSkipped(ITestResult result) {
		logger.warn("Testcase Execution Skipped for TC :: " + result.getName());
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
	}

	public void onStart(ITestContext context) {
		logger.info("Executing All TestCases");
	}

	public void onFinish(ITestContext context) {
		logger.info("TestCases Execution Completed");
		logger.info("Report Generated at Path :: " + FilePathConstants.REPORT_FILE_PATH);
	}

	/**
	 * @description to fetch current iteration count to add testcase related values
	 * @param testCaseName
	 * @return
	 */
	public static int getCurrentIterationCount(String testCaseName) {
		int i = 0;
		i = testCaseIterationCount.get(testCaseName);
		return i;
	}

	/**
	 * @description updating testcase wise pass fail count
	 * @param testCaseName
	 * @param resultType
	 */
	private void addResult(String testCaseName, String resultType) {

		// fetching result of current testcase
		if (testCaseResultMap.size() > 0) {

			Map<String, Integer> resultCount = testCaseResultMap.get(testCaseName);

			// verify if resultCount is null
			if (resultCount != null) {

				// incrementing pass fail count value
				if (resultCount.size() > 0) {

					// to verify if pass fail entry exist for current testcase
					if (resultCount.get(resultType) != null) {
						int count = resultCount.get(resultType);
						resultCount.put(resultType, count + 1);
					} else {
						resultCount.put(resultType, 1);
					}
				} else {
					resultCount.put(resultType, 1);
				}
			} else {
				// creating new object of resultCount
				Map<String, Integer> resultCount1 = new HashMap<String, Integer>();
				resultCount1.put(resultType, 1);
				resultCount = resultCount1;
			}
			// updating testcase map with updated result
			testCaseResultMap.put(testCaseName, resultCount);
		} else {

			// creating new object of resultCount
			Map<String, Integer> resultCount = new HashMap<String, Integer>();
			resultCount.put(resultType, 1);

			// updating testcase map with updated result
			testCaseResultMap.put(testCaseName, resultCount);
		}
	}

	/**
	 * @description to return testcase pass fail count result
	 * @return
	 */
	public static Map<String, Map<String, Integer>> getTestCaseResult() {
		return testCaseResultMap;
	}

	/**
	 * @description to remove dump files once testcase is passed to save disk space.
	 * @param testCaseName
	 */
	private void removeDumpFiles(String testCaseName) {
		String path = FilePathConstants.FILE_DUMP_PATH + testCaseName + "/" + getCurrentIterationCount(testCaseName);
		logger.info("Removing dump files from path :: " + path);

		File		dir	= new File(path);

		Directory	d	= new Directory();
		d.removeFile(dir);
	}
}

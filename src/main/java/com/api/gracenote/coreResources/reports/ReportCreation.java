/**
 * 
 */
package com.api.gracenote.coreResources.reports;

import java.util.Map;

import org.apache.log4j.Logger;

import com.api.gracenote.constants.FilePathConstants;
import com.api.gracenote.constants.StringConstants;
import com.api.gracenote.coreResources.fileCreation.CreateHtmlFiles;
import com.api.gracenote.coreResources.listeners.TestNgListeners;
import com.api.gracenote.coreResources.testCaseManager.TestCaseResults;

/**
 * @author kunal.ashar
 *
 */
public class ReportCreation {

	Logger logger = Logger.getLogger(ReportCreation.class);

	/**
	 * @description initializes common html string to generate report
	 * @param testCaseName
	 */
	private StringBuilder initializeHtmlString(String testCaseName) {
		StringBuilder htmlStringBuilder = new StringBuilder();
		htmlStringBuilder.append("<html><head><h2 align=\"center\">TestCaseName :: " + testCaseName + "</h2>");
		htmlStringBuilder.append(
		        "<style>table, th, td { border: 1.2px solid black; border-collapse: collapse;font-family:calibri} th, td { padding: 5px;}th { text-align: center; background-color: #85C1E9;} h2 {font-family:calibri;}</style></head>");
		htmlStringBuilder.append("<body>");
		htmlStringBuilder.append("<table align='center' border=\"1\" bordercolor=\"#000000\" style=\"width:100%\">");
		htmlStringBuilder.append(
		        "<tr><th>Sr #</th><th>Requested URI</th><th>Payload Json</th><th>Status Code</th><th>Response Json</th><th>Status</th><th>Assertions</th></tr>");
		return htmlStringBuilder;
	}

	/**
	 * @description generates html report string for each testcase
	 * @param testCaseName
	 */
	private void generateTestCaseWiseReport(String testCaseName) {

		// initializing html report string
		StringBuilder						htmlStringBuilder	= initializeHtmlString(testCaseName);

		// testcase iterations results
		Map<Integer, Map<String, String>>	iterationResults	= TestCaseResults.getTestCaseIterationsResults().get(testCaseName);

		int									i					= 1;
		// iterating over testcase data and adding to table row
		for (int iterationCount : iterationResults.keySet()) {

			Map<String, String>	resultMap		= iterationResults.get(iterationCount);

			String				uri				= resultMap.get(StringConstants.URI_KEY);
			String				payload			= resultMap.get(StringConstants.PAYLOAD_KEY);
			String				statusCode		= resultMap.get(StringConstants.STATUS_CODE_KEY);
			String				response		= resultMap.get(StringConstants.RESPONSE_KEY);
			String				executionStatus	= resultMap.get(StringConstants.RESULT_KEY);
			String				assertions		= resultMap.get(StringConstants.ASSERT_KEY);

			// adding index
			htmlStringBuilder.append("<tr><td>" + i + ".</td>");

			// adding uri
			htmlStringBuilder.append("<td>" + uri + "</td>");

			// to ignore payload link if found null for get apis
			if (payload != null && executionStatus.equalsIgnoreCase(StringConstants.FAILED)) {
				payload = payload.replace(FilePathConstants.FILE_DUMP_PATH + testCaseName, ".");
				htmlStringBuilder.append("<td><a href=\"" + payload + "\" target=\"_blank\">payloadJson</a></td>");
			} else {
				htmlStringBuilder.append("<td align=\"center\">-</td>");
			}

			// adding status code
			htmlStringBuilder.append("<td>" + statusCode + "</td>");

			// adding response
			if (response != null && executionStatus.equalsIgnoreCase(StringConstants.FAILED)) {
				response = response.replace(FilePathConstants.FILE_DUMP_PATH + testCaseName, ".");
				htmlStringBuilder.append("<td><a href=\"" + response + "\" target=\"_blank\">responseJson</a></td>");
			} else if (response != null && executionStatus.equalsIgnoreCase(StringConstants.PASSED)) {
				htmlStringBuilder.append("<td align=\"center\">-</td>");
			} else {
				htmlStringBuilder.append("<td>Response file path received null.</td>");
			}

			// adding execution status
			if (executionStatus.equalsIgnoreCase(StringConstants.PASSED)) {

				// marking testcase as failed if status code found null
				if (statusCode == null || statusCode.equalsIgnoreCase("null")) {
					htmlStringBuilder.append("<td bgcolor=\"#FE2E2E\">" + StringConstants.FAILED + "</td>");
				} else {
					htmlStringBuilder.append("<td bgcolor=\"#58D68D\">" + executionStatus + "</td>");
				}
			} else {
				htmlStringBuilder.append("<td bgcolor=\"#FE2E2E\">" + StringConstants.FAILED + "</td>");
			}

			// adding assertion message
			if (assertions == null) {
				assertions = "";
			}
			htmlStringBuilder.append("<td>" + assertions + "</td></tr>");

			// incrementing row index
			i++;
		}

		// appending string to complete html file string
		htmlStringBuilder.append("</table></body></html>");

		// creating html file
		new CreateHtmlFiles().createTestCaseHtmlFile(testCaseName, testCaseName, htmlStringBuilder.toString());
	}

	/**
	 * @description generates html report string for failed scenario of each
	 *              testcase
	 * @param testCaseName
	 */
	@SuppressWarnings("static-access")
	private void generateTestCaseWiseFailedScenarioReport(String testCaseName) {

		// initializing html report string
		StringBuilder						htmlStringBuilder	= initializeHtmlString(testCaseName);

		// testcase iterations results
		Map<Integer, Map<String, String>>	iterationResults	= new TestCaseResults().getTestCaseIterationsResults().get(testCaseName);

		int									i					= 1;
		// iterating over testcase data and adding to table row
		for (int iterationCount : iterationResults.keySet()) {

			Map<String, String>	resultMap		= iterationResults.get(iterationCount);

			String				uri				= resultMap.get(StringConstants.URI_KEY);
			String				payload			= resultMap.get(StringConstants.PAYLOAD_KEY);
			String				statusCode		= resultMap.get(StringConstants.STATUS_CODE_KEY);
			String				response		= resultMap.get(StringConstants.RESPONSE_KEY);
			String				executionStatus	= resultMap.get(StringConstants.RESULT_KEY);
			String				assertions		= resultMap.get(StringConstants.ASSERT_KEY);

			if (executionStatus.equalsIgnoreCase(StringConstants.FAILED)) {

				// adding index
				htmlStringBuilder.append("<tr><td>" + i + ".</td>");

				// adding uri
				htmlStringBuilder.append("<td>" + uri + "</td>");

				// to ignore payload link if found null for get apis
				if (payload != null && executionStatus.equalsIgnoreCase(StringConstants.FAILED)) {
					payload = payload.replace(FilePathConstants.FILE_DUMP_PATH + testCaseName, ".");
					htmlStringBuilder.append("<td><a href=\"" + payload + "\" target=\"_blank\">payloadJson</a></td>");
				} else {
					htmlStringBuilder.append("<td align=\"center\">-</td>");
				}

				// adding status code
				htmlStringBuilder.append("<td>" + statusCode + "</td>");

				// adding response
				if (response != null && executionStatus.equalsIgnoreCase(StringConstants.FAILED)) {
					response = response.replace(FilePathConstants.FILE_DUMP_PATH + testCaseName, ".");
					htmlStringBuilder.append("<td><a href=\"" + response + "\" target=\"_blank\">responseJson</a></td>");
				} else if (response != null && executionStatus.equalsIgnoreCase(StringConstants.PASSED)) {
					htmlStringBuilder.append("<td align=\"center\">-</td>");
				} else {
					htmlStringBuilder.append("<td>Response file path received null.</td>");
				}

				// adding execution status
				htmlStringBuilder.append("<td bgcolor=\"#FE2E2E\">" + StringConstants.FAILED + "</td>");

				// adding assertion message
				if (assertions == null) {
					assertions = "";
				}
				htmlStringBuilder.append("<td>" + assertions + "</td></tr>");

				// incrementing row index
				i++;
			}
		}

		// appending string to complete html file string
		htmlStringBuilder.append("</table></body></html>");

		// creating html file
		new CreateHtmlFiles().createTestCaseHtmlFile(testCaseName, StringConstants.FAILED, htmlStringBuilder.toString());
	}

	/**
	 * @description to create main report with links for testcase report
	 */
	private void generateMainReport() {
		StringBuilder htmlStringBuilder = new StringBuilder();
		htmlStringBuilder.append("<html><head><h2 align=\"center\">GapZap API Automation Report</h2>");
		htmlStringBuilder.append(
		        "<style>table, th, td { border: 1.2px solid black; border-collapse: collapse;font-family:calibri} th, td { padding: 5px;}th { text-align: center; background-color: #85C1E9;}p{text-align:center;font-size:160%;font-family:calibri} h2 {font-family:calibri;}</style></head>");
		htmlStringBuilder.append("<body>");
		htmlStringBuilder.append("<table align='center' border=\"1\" bordercolor=\"#000000\" style=\"width:100%\">");
		htmlStringBuilder.append("<tr><th>Sr #</th><th>TestCaseName</th><th>Passed Scenarios</th><th>Failed Scenarios</th></tr>");

		// fetching testcase result map
		Map<String, Map<String, Integer>>	testCaseResults	= TestNgListeners.getTestCaseResult();

		// setting counters
		int									rowNum			= 1;
		int									pass			= 0;
		int									fail			= 0;

		// iterating over testcase results
		for (String testCaseName : testCaseResults.keySet()) {

			int						passCount	= 0;
			int						failCount	= 0;

			Map<String, Integer>	resultCount	= testCaseResults.get(testCaseName);

			// get count of passed scenarios
			try {
				if (resultCount.get(StringConstants.PASSED) != null) {
					passCount = resultCount.get(StringConstants.PASSED);
				}
			} catch (NullPointerException e) {
				logger.error("Exception occured while fetching pass count for testcase :: " + testCaseName);
			}

			// get count of failed scenarios
			try {

				if (resultCount.get(StringConstants.FAILED) != null) {
					failCount = resultCount.get(StringConstants.FAILED);
				}
			} catch (NullPointerException e) {
				logger.error("Exception occured while fetching fail count for testcase :: " + testCaseName);
			}

			// adding row num
			htmlStringBuilder.append("<tr><td>" + rowNum + ".</td>");

			// adding testcase name
			String path = "fileDump/" + testCaseName + "/" + testCaseName + ".html";

			htmlStringBuilder.append("<td><a href=\"" + path + "\" target=\"_blank\">" + testCaseName + "</a></td>");

			// adding pass count
			if (failCount == 0) {
				// marking cell with green background to indicate fully passed
				htmlStringBuilder.append("<td align=\"center\" bgcolor=\"#58D68D\">" + passCount + "</td>");
			} else {
				htmlStringBuilder.append("<td align=\"center\">" + passCount + "</td>");
			}

			// adding fail count
			String failedPath = "fileDump/" + testCaseName + "/" + StringConstants.FAILED + ".html";

			if (passCount == 0) {
				htmlStringBuilder.append("<td align=\"center\" bgcolor=\"#ff3333\"><a href=\"" + failedPath + "\" target=\"_blank\">"
				        + failCount + "</a></td></tr>");
			} else {
				htmlStringBuilder
				        .append("<td align=\"center\"><a href=\"" + failedPath + "\" target=\"_blank\">" + failCount + "</a></td></tr>");
			}

			// incrementing rowNum
			rowNum++;

			// adding pass fail count
			pass	= pass + passCount;
			fail	= fail + failCount;
		}

		// printing total scenarios passed/failed count
		htmlStringBuilder
		        .append("<tr bgcolor=\"#FFDCA4\"><td colspan=\"2\" align=\"center\"><B>Total Scenarios</B></td><td align=\"center\"><B>"
		                + pass + "</B></td><td align=\"center\"><B>" + fail + "</B></td>");

		// appending string to complete html file string
		htmlStringBuilder.append("</table><br/>");

		// calculate success rate
		int successRate = (pass * 100 / (pass + fail));

		if (successRate >= 95) {
			htmlStringBuilder.append("<p style=\"background-color:#58D68D\">Success Rate :: " + successRate + "%</p>");
		} else if (successRate < 95 && successRate >= 90) {
			htmlStringBuilder.append("<p style=\"background-color:#ffa64d\">Success Rate :: " + successRate + "%</p>");
		} else {
			htmlStringBuilder.append("<p style=\"background-color:#ff3333\">Success Rate :: " + successRate + "%</p>");
		}

		htmlStringBuilder.append("<br/></body></html>");

		// creating html file
		new CreateHtmlFiles().createMainReportFile(htmlStringBuilder.toString());
	}

	/**
	 * @description to create report
	 */
	public void createReport() {

		// generating testcase wise report
		Map<String, Map<Integer, Map<String, String>>> testCaseReport = TestCaseResults.getTestCaseIterationsResults();

		for (String testCaseName : testCaseReport.keySet()) {
			// generating report of all scenarios
			generateTestCaseWiseReport(testCaseName);

			// generating report of only failed scenarios
			generateTestCaseWiseFailedScenarioReport(testCaseName);
		}

		// generating main report
		generateMainReport();
	}
}

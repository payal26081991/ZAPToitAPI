package com.api.gracenote;

import com.api.gracenote.bo.TestCaseBO;
import com.api.gracenote.coreResources.directoryManager.Directory;
import com.api.gracenote.coreResources.fileReaders.PropertyReader;
import com.api.gracenote.coreResources.fileReaders.TestCaseExcelReader;
import com.api.gracenote.coreResources.reports.ReportCreation;
import com.api.gracenote.coreResources.testNg.GenerateTestNg;
import org.apache.log4j.Logger;
import org.testng.TestNG;

import java.util.List;


/**
 * 
 * @author kunal.ashar
 *
 */
public class StartUp {

	static TestNG testNg	= null;
	private static Logger logger	= Logger.getLogger(StartUp.class);

	/**
	 * @description Execution Start Point for this framework
	 * @param
	 */
	public static void main(String[] args) {
		
		//**********************    ***************************************
		logger.warn("Execution Started.");

		// to clear and create new output directory
		Directory d = new Directory();
		d.createOutputDirectory();

		// to load all properties before starting testcase execution
		PropertyReader pr = new PropertyReader();

		// loading all config properties
		pr.loadConfigProperties();

		// loading all error properties
		pr.loadErrorMessageProperties();

		// reading all testcases to be executed
		TestCaseExcelReader	tc				= new TestCaseExcelReader();
		List<TestCaseBO>	testCasesList	= tc.readTestCaseExcel();

		// creating dynamic testNg file and running it
		testNg = new GenerateTestNg().generateTestNgXmlFile(testCasesList);
		testNg.run();

		// create execution report
		ReportCreation rc = new ReportCreation();
		rc.createReport();

		logger.warn("Execution Completed.");
	}
}

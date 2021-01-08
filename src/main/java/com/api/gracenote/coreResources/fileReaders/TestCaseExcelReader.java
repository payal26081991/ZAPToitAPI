/**
 * 
 */
package com.api.gracenote.coreResources.fileReaders;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.api.gracenote.bo.TestCaseBO;
import com.api.gracenote.constants.ConfigPropertiesConstants;
import com.api.gracenote.constants.FilePathConstants;
import com.api.gracenote.constants.StringConstants;

/**
 * @author kunal.ashar
 *
 */
public class TestCaseExcelReader {

	Logger logger = Logger.getLogger(TestCaseExcelReader.class);

	public List<TestCaseBO> readTestCaseExcel() {

		XSSFWorkbook		wb			= null;
		List<TestCaseBO>	testCases	= new LinkedList<TestCaseBO>();

		try {
			wb = new XSSFWorkbook(FilePathConstants.TESTCASE_FILE_PATH);
		} catch (IOException e) {
			logger.error("Exception Occurred while reading Testcase file. FileName: " + FilePathConstants.TESTCASE_FILE_PATH);
		}

		XSSFSheet	sh		= wb.getSheetAt(0);

		int			rowNum	= sh.getPhysicalNumberOfRows();

		for (int i = 0; i < rowNum - 1; i++) {

			// creating map to store headers and value of each row
			Map<String, String>	testCaseMap	= new HashMap<String, String>();

			int					colNum		= sh.getRow(0).getLastCellNum();

			for (int j = 1; j < colNum; j++) {
				// fetch header row and assign as map key
				String	key		= sh.getRow(0).getCell(j).getStringCellValue().toLowerCase();
				String	value	= null;
				try {
					value = sh.getRow(i + 1).getCell(j).getStringCellValue();
				} catch (NullPointerException e) {
					// change value to blank if null received
					value = "";
				}

				// adding value to map
				testCaseMap.put(key, value);
			}

			// to fetch values from map and assign it to testcase object
			if (testCaseMap.get(PropertyReader.getConfigProperty(ConfigPropertiesConstants.SUITE_TYPE).toLowerCase())
			        .equalsIgnoreCase("Y")) {
				TestCaseBO testCase = new TestCaseBO();
				testCase.setTestCaseName(testCaseMap.get(StringConstants.COLUMN_TESTCASE_NAME.toLowerCase()));
				testCase.setTestCaseDescription(testCaseMap.get(StringConstants.COLUMN_TESTCASE_DESCRIPTION.toLowerCase()));

				// creating list of cases to be executed
				testCases.add(testCase);
			}
		}

		// closing workbook
		try {
			wb.close();
		} catch (IOException e) {
			logger.error("Exception Occurred while closing TestCaseExcel workbook", e);
		} catch (Exception e) {
			logger.error("Exception Occurred while closing TestCaseExcel workbook", e);
		}

		if (testCases.size() > 0) {
			logger.info("TestCase List :: " + testCases);
			return testCases;
		} else {
			logger.error("No Testcase Found for execution for Suite Type : "
			        + PropertyReader.getConfigProperty(ConfigPropertiesConstants.SUITE_TYPE));
			System.exit(0);
		}
		return null;
	}

	public static void main(String[] args) {

		PropertyReader pr = new PropertyReader();
		pr.loadConfigProperties();

		TestCaseExcelReader tc = new TestCaseExcelReader();
		tc.readTestCaseExcel();
	}
}

/**
 * 
 */
package com.api.gracenote.coreResources.fileCreation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.log4j.Logger;

import com.api.gracenote.constants.FilePathConstants;

/**
 * @author kunal.ashar
 *
 */
public class CreateHtmlFiles {

	Logger logger = Logger.getLogger(CreateHtmlFiles.class);

	/**
	 * @description creates testcase wise html file
	 * @param htmlStringBuilder
	 */
	public void createTestCaseHtmlFile(String testCaseName, String fileName, String htmlFileString) {
		FileWriter		fileWriter		= null;
		BufferedWriter	bufferedWriter	= null;
		String			filePath		= FilePathConstants.FILE_DUMP_PATH + testCaseName;
		File			file			= new File(filePath + "/" + fileName + ".html");

		try {
			fileWriter		= new FileWriter(file, false);
			bufferedWriter	= new BufferedWriter(fileWriter);
			bufferedWriter.write(htmlFileString);
			bufferedWriter.newLine();
			bufferedWriter.close();
		} catch (IOException e) {
			logger.error("Exception Occurred while creating testcase html file.", e);
		}
	}

	/**
	 * @description creates main report file
	 * @param htmlFileString
	 */
	public void createMainReportFile(String htmlFileString) {
		FileWriter		fileWriter		= null;
		BufferedWriter	bufferedWriter	= null;
		String			filePath		= FilePathConstants.OUTPUT_DIRECTORY_NAME;
		File			file			= new File(filePath + "/" + "Report.html");

		try {
			fileWriter		= new FileWriter(file, false);
			bufferedWriter	= new BufferedWriter(fileWriter);
			bufferedWriter.write(htmlFileString);
			bufferedWriter.newLine();
			bufferedWriter.close();
		} catch (IOException e) {
			logger.error("Exception Occurred while creating main report html file.", e);
		}
	}
}

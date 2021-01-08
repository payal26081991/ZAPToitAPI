/**
 * 
 */
package com.api.gracenote.coreResources.fileCreation;

import java.io.FileWriter;
import java.io.IOException;

import org.apache.log4j.Logger;

import com.api.gracenote.constants.FilePathConstants;
import com.api.gracenote.constants.StringConstants;
import com.api.gracenote.coreResources.listeners.TestNgListeners;

/**
 * @author kunal.ashar
 *
 */
public class CreateJsonFiles {

	private static Logger logger = Logger.getLogger(CreateJsonFiles.class);

	/**
	 * @description to create payload json file
	 * @param testCaseName
	 * @param payloadObject
	 */
	public static String createPayloadJsonFile(String testCaseName, String payloadString) {
		int			iterationCount	= TestNgListeners.getCurrentIterationCount(testCaseName);
		FileWriter	fileWriter		= null;
		String		filePath		= FilePathConstants.FILE_DUMP_PATH + testCaseName + "/" + iterationCount + "/"
		        + StringConstants.PAYLOAD_JSON;
		try {
			fileWriter = new FileWriter(filePath);
			fileWriter.write(payloadString);
		} catch (IOException e) {
			logger.error("Exception Occurred while creating jsonPayload file.", e);
		} finally {
			try {
				if (fileWriter != null) {
					fileWriter.flush();
					fileWriter.close();
				}
			} catch (IOException e) {
				logger.error("Exception Occurred while closing jsonPayload file.", e);
			}
		}

		return filePath;
	}

	/**
	 * @description to create response json file
	 * @param testCaseName
	 * @param responseString
	 */
	public static String createResponseJsonFile(String testCaseName, String responseString) {
		int			iterationCount	= TestNgListeners.getCurrentIterationCount(testCaseName);
		FileWriter	fileWriter		= null;
		String		filePath		= FilePathConstants.FILE_DUMP_PATH + testCaseName + "/" + iterationCount + "/"
		        + StringConstants.RESPONSE_JSON;
		try {
			fileWriter = new FileWriter(filePath);
			fileWriter.write(responseString);
		} catch (IOException e) {
			logger.error("Exception Occurred while creating jsonResponse file.", e);
		} finally {
			try {
				if (fileWriter != null) {
					fileWriter.flush();
					fileWriter.close();
				}
			} catch (IOException e) {
				logger.error("Exception Occurred while closing jsonResponse file.", e);
			}
		}

		return filePath;
	}
}

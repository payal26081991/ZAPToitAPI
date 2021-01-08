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
public class CreateTextFiles {

	private static Logger logger = Logger.getLogger(CreateTextFiles.class);

	/**
	 * @description creates a uri file
	 * @param testCaseName
	 * @param uri
	 */
	public static void createRequestedUriTextFile(String testCaseName, String uri) {
		int			iterationCount	= TestNgListeners.getCurrentIterationCount(testCaseName);
		FileWriter	fileWriter		= null;
		String		filePath		= FilePathConstants.FILE_DUMP_PATH + testCaseName + "/" + iterationCount + "/"
		        + StringConstants.REQUESTED_URI_TXT;

		try {
			fileWriter = new FileWriter(filePath);
			fileWriter.write(uri);
		} catch (IOException e) {
			logger.error("Exception Occurred while creating requestedUri file.", e);
		} finally {
			try {
				if (fileWriter != null) {
					fileWriter.flush();
					fileWriter.close();
				}
			} catch (IOException e) {
				logger.error("Exception Occurred while closing requestedUri file.", e);
			}
		}
	}

}

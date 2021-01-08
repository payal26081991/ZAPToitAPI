package com.api.gracenote.coreResources.directoryManager;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;

import com.api.gracenote.constants.FilePathConstants;

/**
 * @author kunal.ashar
 *
 */

public class Directory {

	private static Logger logger = Logger.getLogger(Directory.class);

	/**
	 * @description to clean output directory
	 * 
	 */
	private void cleanAllOutputDirectory() {

		File dir = new File(FilePathConstants.OUTPUT_DIRECTORY_NAME);
		removeFile(dir);
	}

	/**
	 * @description to remove all files
	 * 
	 */
	public void removeFile(File dir) {

		File[] files = dir.listFiles();
		
		if (files != null) {
			for (File file : files) {
				if (file.isDirectory()) {
					logger.warn("Cleaning Directory: " + file.getPath());
					removeFile(file);
					file.delete();
				} else {
					file.delete();
				}
			}
		}
		dir.delete();
	}

	/**
	 * @description to create output directory structure
	 * 
	 */
	public void createOutputDirectory() {

		// to clean output directory
		cleanAllOutputDirectory();

		// create new output folder
		File output = new File(FilePathConstants.OUTPUT_DIRECTORY_NAME);
		output.mkdirs();
		logger.info("Created Directory: " + output.getPath());

		// creating directory for fileDump
		File dumpPath = new File(FilePathConstants.FILE_DUMP_PATH);
		dumpPath.mkdirs();
		logger.info("Created Directory: " + dumpPath.getPath());
	}

	/**
	 * @description create iteration wise testcase dump directory
	 * @param testCaseName
	 * @param iterationCount
	 */
	public void createTestCaseDirectory(String testCaseName, int iterationCount) {

		String	path		= FilePathConstants.FILE_DUMP_PATH + testCaseName + "/" + iterationCount;
		File	dumpPath	= new File(path);
		dumpPath.mkdirs();
	}

	public static void main(String[] args) throws IOException {
		Directory d = new Directory();
		d.createOutputDirectory();
	}

}

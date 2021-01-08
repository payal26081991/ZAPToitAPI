package com.api.gracenote.coreResources.fileReaders;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.api.gracenote.constants.FilePathConstants;

/**
 * @author kunal.ashar
 *
 */
public class PropertyReader {

	Logger						logger				= Logger.getLogger(PropertyReader.class);
	private static Properties	configProperties	= new Properties();
	private static Properties	errorProperties		= new Properties();

	/**
	 * @description to load all values from config.properties file
	 * 
	 */
	public void loadConfigProperties() {

		File		file	= new File(FilePathConstants.CONFIG_PROPERTIES_FILE_PATH);
		FileReader	reader	= null;

		try {
			reader = new FileReader(file);
		} catch (FileNotFoundException e) {
			logger.error("Exception Occurred while reading config.properties file. ", e);
		}

		try {
			configProperties.load(reader);
			logger.info("Successfully loaded config.proeprties file");

			// getting values from jenkins environment properties.
			loadEnvironmentProperties();
			
		} catch (IOException e) {
			logger.error("Exception Occurred while loading config.properties file. ", e);
		}

	}

	/**
	 * @description to load all values from error.properties file
	 * 
	 */
	public void loadErrorMessageProperties() {

		FileInputStream		fis;
		InputStreamReader	isr	= null;

		try {
			fis	= new FileInputStream(FilePathConstants.ERROR_PROPERTIES_FILE_PATH);
			isr	= new InputStreamReader(fis, "UTF-8");
		} catch (FileNotFoundException e) {
			logger.error("Exception Occurred while reading error.properties file. ", e);
		} catch (UnsupportedEncodingException e) {
			logger.error("Exception Occurred while reading error.properties file. ", e);
		}

		try {
			errorProperties.load(isr);
		} catch (FileNotFoundException e) {
			logger.error("Exception Occurred while loading error.properties file. ", e);
		} catch (IOException e) {
			logger.error("Exception Occurred while loading error.properties file. ", e);
		}

	}

	/**
	 * @description fetches updated values from jenkins environment properties
	 */
	private void loadEnvironmentProperties() {

		// iterating over all keys to update config properties
		for (String key : System.getenv().keySet()) {
			String value = System.getenv(key);
			configProperties.put(key, value);
		}
	}

	/**
	 * 
	 * @param propertyName
	 * @return String propertyValue
	 */
	public static String getConfigProperty(String propertyName) {

		return configProperties.getProperty(propertyName);
	}

	/**
	 * 
	 * @param propertyName
	 * @return String propertyValue
	 */
	public static String getErrorMessageProperty(String propertyName) {

		return errorProperties.getProperty(propertyName);
	}

	public static void main(String[] args) throws IOException {
		/*
		 * PropertyReader pr = new PropertyReader(); pr.loadConfigProperties();
		 */
		FileInputStream		fis	= new FileInputStream(FilePathConstants.ERROR_PROPERTIES_FILE_PATH);
		InputStreamReader	isr	= new InputStreamReader(fis, "UTF-8");

		errorProperties.load(isr);
		System.out.println(errorProperties);
	}

}

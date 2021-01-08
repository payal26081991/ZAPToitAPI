/**
 * 
 */
package com.api.gracenote.coreResources.dbConnectors;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import org.apache.log4j.Logger;

import com.api.gracenote.constants.ConfigPropertiesConstants;
import com.api.gracenote.constants.StringConstants;
import com.api.gracenote.coreResources.fileReaders.PropertyReader;

/**
 * @author kunal.ashar
 *
 */
public class DBConnector {

	private static Logger logger = Logger.getLogger(DBConnector.class);

	/**
	 * 
	 * @description Obtains Connection with DB
	 * @return Connection
	 */
	public Connection getDBConnection() {
		Connection	connection	= null;
		String		hostName	= null;

		// fetching db connection as per setup name provided in config file
		String		setupName	= PropertyReader.getConfigProperty(ConfigPropertiesConstants.SETUP_NAME);

		if (setupName.equalsIgnoreCase(StringConstants.UAT_SETUP)) {
			hostName = PropertyReader.getConfigProperty(ConfigPropertiesConstants.UAT_DB_HOST);
		} else if (setupName.equalsIgnoreCase(StringConstants.PREPROD_SETUP)) {
			hostName = PropertyReader.getConfigProperty(ConfigPropertiesConstants.PREPROD_DB_HOST);
		} 
		else if (setupName.equalsIgnoreCase(StringConstants.PROD_SETUP)) {
			hostName = PropertyReader.getConfigProperty(ConfigPropertiesConstants.PREPROD_DB_HOST);
		}
		else {
			logger.error("Unable to fetch DB Connection as incorrect option provided for setupName :: " + setupName);
		}

		String	schemaName			= PropertyReader.getConfigProperty(ConfigPropertiesConstants.DB_SCHEMA);
		String	userName			= PropertyReader.getConfigProperty(ConfigPropertiesConstants.DB_USERNAME);
		String	password			= PropertyReader.getConfigProperty(ConfigPropertiesConstants.DB_PASSWORD);
		String	driverRegister		= StringConstants.DRIVER_REGISTER;
		String	finalHostAddress	= "jdbc:mysql://" + hostName + "/" + schemaName + "?allowMultiQueries=true";

		// to register db driver
		try {
			Class.forName(driverRegister);
		} catch (ClassNotFoundException e) {
			logger.error("Exception Occurred while registering JDBC Driver", e);
		}

		// Obtaining connection with DB
		try {
			connection = DriverManager.getConnection(finalHostAddress, userName, password);
			
		} catch (SQLException e) {
			logger.error("Exception Occurred while obtaining Driver Connection", e);
		}
		return connection;
	}

	/**
	 * 
	 * @description fetches data from DB
	 * @param query
	 * @return ResultSet
	 */
	public ResultSet getResult(Connection connection, String query) {

		Statement	statement	= null;
		ResultSet	resultSet	= null;

		if (connection != null) {

			// creating statement object
			try {
				statement = connection.createStatement();
				
			} catch (SQLException e) {
				logger.error("Exception Occurred while creating Statement Object", e);
			}

			try {
				logger.info("Executing Query :: " + query);

				// query execution start time
				Date startTime = new Date();

				resultSet = statement.executeQuery(query);
				
				

				// query execution end time
				Date	endTime		= new Date();

				// caluclating time difference
				long	diffTime	= endTime.getTime() - startTime.getTime();

				long	minutes		= (diffTime / 1000) / 60;
				long	seconds		= (diffTime / 1000) % 60;

				logger.info(minutes + " : " + seconds + " taken to execute query :: " + query);

				logger.info("DB Query Execution Completed.");
			} catch (SQLException e) {
				logger.error("Exception Occurred while fetching data from DB", e);
			}

		} else {
			logger.error("DB Connection object received as null.");
		}

		return resultSet;
	}

	/**
	 * @description closing db connection
	 * @param connection
	 */
	public void closeDBConnection(Connection connection) {
		try {
			connection.close();
		} catch (SQLException e) {
			logger.error("Exception Occurred while closing DB Connection.", e);
		}
	}
}

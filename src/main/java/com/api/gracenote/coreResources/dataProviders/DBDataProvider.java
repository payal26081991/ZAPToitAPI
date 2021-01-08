/**
 * 
 */
package com.api.gracenote.coreResources.dataProviders;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.print.attribute.standard.DateTimeAtCompleted;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.testng.annotations.DataProvider;

import com.api.gracenote.bo.AffiliatePropertiesBO;
import com.api.gracenote.bo.ProgramBO;
import com.api.gracenote.bo.ProvidersBO;
import com.api.gracenote.bo.ScheduleBO;
import com.api.gracenote.bo.SearchBO;
import com.api.gracenote.bo.TimezoneBO;
import com.api.gracenote.constants.ConfigPropertiesConstants;
import com.api.gracenote.constants.DBQueriesConstants;
import com.api.gracenote.coreResources.dbConnectors.DBConnector;
import com.api.gracenote.coreResources.fileReaders.PropertyReader;
import com.api.gracenote.utilities.DateAndTime;

/**
 * @author kunal.ashar
 *
 */
public class DBDataProvider {

	private static Logger			logger										= Logger.getLogger(DBDataProvider.class);
	private static List<Object[]>	affiliatePropertiesData						= new ArrayList<Object[]>();
	private static List<Object[]>	programData									= new ArrayList<Object[]>();
	private static List<Object[]>	postScheduleByLineupIdData					= new ArrayList<Object[]>();
	private static List<Object[]>	postScheduleByLineupIdWithPostalCodesData	= new ArrayList<Object[]>();
	private static List<Object[]>	postScheduleByLineupIdDataWithOTALineups	= new ArrayList<Object[]>();
	private static List<Object[]>	postScheduleSingleStationChannel			= new ArrayList<Object[]>();
	private static List<Object[]>	postScheduleOTASingleStationChannel			= new ArrayList<Object[]>();
	private static List<Object[]>	getPostalCodesProvidersData					= new ArrayList<Object[]>();
	private static List<Object[]>	getAdultContentSearchData					= new ArrayList<Object[]>();
	private static List<Object[]>	postScheduleSingleStation					= new ArrayList<Object[]>();
	private static List<Object[]>	getAdultContentSearchNonEnglishData			= new ArrayList<Object[]>();
	private static List<Object[]>	getTimezoneData								= new ArrayList<Object[]>();
	private static List<Object[]>	getDstOffSetData							= new ArrayList<Object[]>();
	private static List<Object[]>	headendsData							= new ArrayList<Object[]>();
	
	/**
	 * @description provide data for AffiliatesPropertiesAPI
	 * @return
	 */
	@DataProvider(name = "getAffiliatesPropertiesData")
	public static synchronized Iterator<Object[]> getAffiliatesPropertiesData() {

		if (affiliatePropertiesData.size() == 0) {
			setAffiliatePropertiesData();
		}
		logger.warn("Total Records Found for getAffiliatesPropertiesData :: " + affiliatePropertiesData.size());
		return affiliatePropertiesData.iterator();
	}

	/**
	 * @description sets DB value for affiliatesProperties
	 */
	private static void setAffiliatePropertiesData() {
		String		dbQuery		= DBQueriesConstants.AFFILIATE_PROPERTIES_QUERY;
		DBConnector	db			= new DBConnector();
		Connection	connection	= db.getDBConnection();
		ResultSet	rs			= db.getResult(connection, dbQuery);

		try {
			while (rs.next()) {
				AffiliatePropertiesBO affProperties = new AffiliatePropertiesBO();
				affProperties.setAffiliateTag(rs.getString("affl_tag"));
				affiliatePropertiesData.add(new Object[] { affProperties });
			}

			// closing connections
			rs.close();
			db.closeDBConnection(connection);
		} catch (SQLException e) {
			logger.error("Exception Occurred while iterating through ResultSet", e);
		}
	}

	/**
	 * @description provide data for Program API
	 * @return
	 */
	@DataProvider(name = "getpostProgramOverviewUpcomingDetailsData")
	public static synchronized Iterator<Object[]> getpostProgramOverviewUpcomingDetailsData() {

		if (programData.size() == 0) {
			setpostProgramOverviewUpcomingDetailsData();
		}
		logger.warn("Total Records Found for getProgramDetailsData :: " + programData.size());
		return programData.iterator();
	}

	/**
	 * @description sets DB value for Program Details
	 */
	private static void setpostProgramOverviewUpcomingDetailsData() {
		String		dbQuery		= DBQueriesConstants.PROGRAM_SHOWCARD_OVERVIEW_QUERY;
		DBConnector	db			= new DBConnector();
		Connection	connection	= db.getDBConnection();
		ResultSet	rs			= db.getResult(connection, dbQuery);

		try {
			while (rs.next()) {
				ProgramBO program = new ProgramBO();
				program.setProgramSeriesId(rs.getString("ProgramSeriesID"));
				programData.add(new Object[] { program });
			}

			// closing connections
			rs.close();
			db.closeDBConnection(connection);
		} catch (SQLException e) {
			logger.error("Exception Occurred while iterating through ResultSet", e);
		}
	}

	/**
	 * @description provide data for PostScheduleByLineupId API
	 * @return
	 */
	@DataProvider(name = "getPostScheduleByLineupId")
	public static synchronized Iterator<Object[]> getPostScheduleByLineupIdData() {

		if (postScheduleByLineupIdData.size() == 0) {
			setPostScheduleByLineupIdData();
		}
		logger.warn("Total Records Found for getPostScheduleByLineupId :: " + postScheduleByLineupIdData.size());
		return postScheduleByLineupIdData.iterator();
	}

	/**
	 * @description sets DB value for PostScheduleByLineupIdData
	 */
	private static void setPostScheduleByLineupIdData() {
		String		dbQuery		= DBQueriesConstants.POST_SCHEDULE_BY_LINEUP_ID_QUERY;
		DBConnector	db			= new DBConnector();
		Connection	connection	= db.getDBConnection();
		ResultSet	rs			= db.getResult(connection, dbQuery);
		DateAndTime dateTime    = new DateAndTime();

		try {
			while (rs.next()) {
				ScheduleBO schedule = new ScheduleBO();
				schedule.setHeadendId(rs.getString("HeadendId"));
				schedule.setDeviceType(rs.getString("DeviceType"));
				schedule.setCountryName(rs.getString("Country"));	
				schedule.setEpochTime(dateTime.getEpochTime());
				schedule.setTimeSpan(PropertyReader.getConfigProperty(ConfigPropertiesConstants.TIME_SPAN));
				postScheduleByLineupIdData.add(new Object[] { schedule });
			}

			// closing connections
			rs.close();
			db.closeDBConnection(connection);
		} catch (SQLException e) {
			logger.error("Exception Occurred while iterating through ResultSet", e);
		}
	}

	/**
	 * @description provide data for PostScheduleByLineupId API with Postal Codes
	 * @return
	 */
	@DataProvider(name = "getPostScheduleByLineupIdDataWithPostalCodes")
	public static synchronized Iterator<Object[]> getPostScheduleByLineupIdDataWithPostalCodes() {
		if (postScheduleByLineupIdWithPostalCodesData.size() == 0) {
			setPostScheduleByLineupIdDataWithPostalCodes();
		}

		logger.warn("Total Records Found for getPostScheduleByLineupIdDataWithPostalCodes :: "
				+ postScheduleByLineupIdWithPostalCodesData.size());
		return postScheduleByLineupIdWithPostalCodesData.iterator();
	}

	/**
	 * @description sets DB value for PostScheduleByLineupIdDataWithPostalCodes
	 */
	private static void setPostScheduleByLineupIdDataWithPostalCodes() {
		String		dbQuery		= DBQueriesConstants.POST_SCHEDULE_BY_LINEUP_ID_POSTAL_CODE_QUERY;
		DBConnector	db			= new DBConnector();
		Connection	connection	= db.getDBConnection();
		ResultSet	rs			= db.getResult(connection, dbQuery);
		DateAndTime dateTime    = new DateAndTime(); 
		try {
			while (rs.next()) {
				ScheduleBO schedule = new ScheduleBO();
				schedule.setProgramSvcId(rs.getString("progsvcid"));
				schedule.setPostalCode(rs.getString("postalcode"));
				schedule.setCountryName(rs.getString("country"));
				schedule.setEpochTime(dateTime.getEpochTime());
				schedule.setTimeSpan(PropertyReader.getConfigProperty(ConfigPropertiesConstants.TIME_SPAN));
				postScheduleByLineupIdWithPostalCodesData.add(new Object[] { schedule });
			}

			// closing connections
			rs.close();
			db.closeDBConnection(connection);
		} catch (SQLException e) {
			logger.error("Exception Occurred while iterating through ResultSet", e);
		}
	}

	/**
	 * @description provide data for GetPostalCodeProvidersData API
	 * @return
	 */
	@DataProvider(name = "getPostalCodeProvidersData")
	public static synchronized Iterator<Object[]> getPostalCodeProvidersData() {

		if (getPostalCodesProvidersData.size() == 0) {
			setPostalCodesProvidersData();
		}

		logger.warn("Total Records Found for getPostalCodeProvidersData :: " + getPostalCodesProvidersData.size());
		return getPostalCodesProvidersData.iterator();
	}

	/**
	 * @description sets DB value for GetPostalCodeProvidersData
	 */
	private static void setPostalCodesProvidersData() {
		String		dbQuery		= DBQueriesConstants.GET_POSTAL_CODE_PROVIDERS_QUERY;
		DBConnector	db			= new DBConnector();
		Connection	connection	= db.getDBConnection();
		ResultSet	rs			= db.getResult(connection, dbQuery);

		try {
			while (rs.next()) {
				ProvidersBO providers = new ProvidersBO();
				providers.setPostalCode(rs.getString("PostalCode"));
				providers.setCountry(rs.getString("Country"));
				getPostalCodesProvidersData.add(new Object[] { providers });
			}

			// closing connections
			rs.close();
			db.closeDBConnection(connection);
		} catch (SQLException e) {
			logger.error("Exception Occurred while iterating through ResultSet", e);
		}
	}

	/**
	 * @description provide data for GetAdultContentSearchData API
	 * @return
	 */
	@DataProvider(name = "getAdultContentSearchData")
	public static synchronized Iterator<Object[]> getAdultContentSearchData() {

		if (getAdultContentSearchData.size() == 0) {
			setAdultContentSearchData();
		}

		logger.warn("Total Records Found for getAdultContentSearchData :: " + getAdultContentSearchData.size());
		return getAdultContentSearchData.iterator();
	}

	/**
	 * @description sets DB value for GetAdultContentSearchData
	 */
	private static void setAdultContentSearchData() {
		String		dbQuery		= DBQueriesConstants.GET_ADULT_CONTENT_SEARCH_QUERY;
		DBConnector	db			= new DBConnector();
		Connection	connection	= db.getDBConnection();
		ResultSet	rs			= db.getResult(connection, dbQuery);

		try {
			while (rs.next()) {
				if (rs.getString("IsEnglish").equalsIgnoreCase("1")) {
					SearchBO search = new SearchBO();
					search.setCountryName(rs.getString("Country"));
					search.setProgramName(rs.getString("AttributeValue"));
					search.setProgramSeriesId(rs.getString("ProgramSeriesID"));
					search.setTmsId(rs.getString("ProgramSeriesID"));
					search.setEnglish(true);
					getAdultContentSearchData.add(new Object[] { search });
				}
			}

			// closing connections
			rs.close();
			db.closeDBConnection(connection);
		} catch (SQLException e) {
			logger.error("Exception Occurred while iterating through ResultSet", e);
		}
	}

	/**
	 * @description provide data for PostScheduleByLineupIdDataWithOTALineups API
	 * @return
	 */
	@DataProvider(name = "getPostScheduleByLineupIdDataWithOTALineups")
	public static synchronized Iterator<Object[]> getPostScheduleByLineupIdDataWithOTALineups() {
		if (postScheduleByLineupIdDataWithOTALineups.size() == 0) {
			setPostScheduleByLineupIdDataWithOTALineups();
		}

		logger.warn("Total Records Found for getPostScheduleByLineupIdDataWithOTALineups :: "
				+ postScheduleByLineupIdDataWithOTALineups.size());
		return postScheduleByLineupIdDataWithOTALineups.iterator();
	}

	/**
	 * @description sets DB value for PostScheduleByLineupIdDataWithOTALineups
	 */
	private static void setPostScheduleByLineupIdDataWithOTALineups() {
		String		dbQuery		= DBQueriesConstants.POST_SCHEDULE_BY_LINEUP_ID_OTA_LINEUPS_QUERY;
		DBConnector	db			= new DBConnector();
		Connection	connection	= db.getDBConnection();
		ResultSet	rs			= db.getResult(connection, dbQuery);
		DateAndTime dateTime    = new DateAndTime();
		
		try {
			while (rs.next()) {
				
				ScheduleBO schedule = new ScheduleBO();
				schedule.setPostalCode(rs.getString("Postalcode"));
				schedule.setCountryName(rs.getString("Country"));
				schedule.setEpochTime(dateTime.getEpochTime());
				schedule.setTimeSpan(PropertyReader.getConfigProperty(ConfigPropertiesConstants.TIME_SPAN));
				postScheduleByLineupIdDataWithOTALineups.add(new Object[] { schedule });
			}

			// closing connections
			rs.close();
			db.closeDBConnection(connection);
		} catch (SQLException e) {
			logger.error("Exception Occurred while iterating through ResultSet", e);
		}
	}

	/**
	 * @description provide data for PostScheduleSingleStationChannel API
	 * @return
	 */
	@DataProvider(name = "getPostScheduleSingleStationChannel")
	public static synchronized Iterator<Object[]> getPostScheduleSingleStationChannel() {
		if (postScheduleSingleStationChannel.size() == 0) {
			setPostScheduleSingleStationChannel();
		}

		logger.warn("Total Records Found for getPostScheduleSingleStationChannel :: " + postScheduleSingleStationChannel.size());
		return postScheduleSingleStationChannel.iterator();
	}

	/**
	 * @description sets DB value for PostScheduleSingleStationChannel
	 */
	private static void setPostScheduleSingleStationChannel() {
		String		dbQuery		= DBQueriesConstants.POST_SCHEDULE_BY_LINEUP_ID_POSTAL_CODE_QUERY;
		DBConnector	db			= new DBConnector();
		Connection	connection	= db.getDBConnection();
		ResultSet	rs			= db.getResult(connection, dbQuery);
		DateAndTime dt          = new DateAndTime();
		
		try {
			while (rs.next()) {
				ScheduleBO schedule = new ScheduleBO();
				schedule.setHeadendId(rs.getString("headendid"));
				schedule.setDeviceType(rs.getString("device"));
				schedule.setCountryName(rs.getString("country"));
				schedule.setProgramSvcId(rs.getString("progsvcid"));
				schedule.setEpochTime(dt.getEpochTime());
				schedule.setTimeSpan(PropertyReader.getConfigProperty(ConfigPropertiesConstants.TIME_SPAN));
				postScheduleSingleStationChannel.add(new Object[] { schedule });
			}

			// closing connections
			rs.close();
			db.closeDBConnection(connection);
		} catch (SQLException e) {
			logger.error("Exception Occurred while iterating through ResultSet", e);
		}
	}

	/**
	 * @description provide data for PostScheduleOTASingleStationChannel API
	 * @return
	 */
	@DataProvider(name = "getPostScheduleOTASingleStationChannel")
	public static synchronized Iterator<Object[]> getPostScheduleOTASingleStationChannel() {
		if (postScheduleOTASingleStationChannel.size() == 0) {
			setPostScheduleOTASingleStationChannel();
		}

		logger.warn("Total Records Found for getPostScheduleOTASingleStationChannel :: " + postScheduleOTASingleStationChannel.size());
		return postScheduleOTASingleStationChannel.iterator();
	}

	/**
	 * @description sets DB value for PostScheduleOTASingleStationChannel
	 */
	private static void setPostScheduleOTASingleStationChannel() {
		String		dbQuery		= DBQueriesConstants.POST_SCHEDULE_OTA_SINGLE_STATION_QUERY;
		DBConnector	db			= new DBConnector();
		Connection	connection	= db.getDBConnection();
		ResultSet	rs			= db.getResult(connection, dbQuery);
		DateAndTime dateTime    = new DateAndTime();
		
		try {
			while (rs.next()) {
				ScheduleBO schedule = new ScheduleBO();
				schedule.setPostalCode(rs.getString("postalcode"));
				schedule.setCountryName(rs.getString("country"));
				schedule.setProgramSvcId(rs.getString("PrgSvcID"));
				schedule.setEpochTime(dateTime.getEpochTime());
				schedule.setTimeSpan(PropertyReader.getConfigProperty(ConfigPropertiesConstants.TIME_SPAN));
				postScheduleOTASingleStationChannel.add(new Object[] { schedule });
			}

			// closing connections
			rs.close();
			db.closeDBConnection(connection);
		} catch (SQLException e) {
			logger.error("Exception Occurred while iterating through ResultSet", e);
		}
	}

	/**
	 * @description provide data for PostScheduleSingleStation API
	 * @return
	 */
	@DataProvider(name = "getPostScheduleSingleStation")
	public static synchronized Iterator<Object[]> getPostScheduleSingleStation() {
		if (postScheduleSingleStation.size() == 0) {
			setPostScheduleSingleStation();
		}

		logger.warn("Total Records Found for getPostScheduleSingleStation :: " + postScheduleSingleStation.size());
		return postScheduleSingleStation.iterator();
	}

	/**
	 * @description sets DB value for PostScheduleSingleStation
	 */
	private static void setPostScheduleSingleStation() {
		String		dbQuery		= DBQueriesConstants.POST_SCHEDULE_BY_LINEUP_ID_SINGLE_STATION_QUERY;
		DBConnector	db			= new DBConnector();
		Connection	connection	= db.getDBConnection();
		ResultSet	rs			= db.getResult(connection, dbQuery);
		DateAndTime dt          = new DateAndTime();

		try {
			while (rs.next()) {
				ScheduleBO schedule = new ScheduleBO();
				schedule.setHeadendId(rs.getString("HeadendId"));
				schedule.setDeviceType(rs.getString("Device"));
				schedule.setCountryName(rs.getString("Country"));
				schedule.setProgramSvcId(rs.getString("Prgsvcid"));
				schedule.setEpochTime(dt.getEpochTime());
				schedule.setTimeSpan(PropertyReader.getConfigProperty(ConfigPropertiesConstants.TIME_SPAN));
				postScheduleSingleStation.add(new Object[] { schedule });
			}

			// closing connections
			rs.close();
			db.closeDBConnection(connection);
		} catch (SQLException e) {
			logger.error("Exception Occurred while iterating through ResultSet", e);
		}
	}

	/**
	 * @description provide data for GetAdultContentSearchData API
	 * @return
	 */
	@DataProvider(name = "getAdultContentSearchNonEnglishData")
	public static synchronized Iterator<Object[]> getAdultContentSearchNonEnglishData() {

		if (getAdultContentSearchNonEnglishData.size() == 0) {
			setAdultContentSearchNonEnglishData();
		}

		logger.warn("Total Records Found for getAdultContentSearchNonEnglishData :: " + getAdultContentSearchNonEnglishData.size());
		return getAdultContentSearchNonEnglishData.iterator();
	}

	/**
	 * @description sets DB value for GetAdultContentSearchData
	 *//*
	private static void setAdultContentSearchNonEnglishData() {
		String		dbQuery		= DBQueriesConstants.GET_ADULT_CONTENT_SEARCH_QUERY;
		DBConnector	db			= new DBConnector();
		Connection	connection	= db.getDBConnection();
		ResultSet	rs			= db.getResult(connection, dbQuery);

		try {
			while (rs.next()) {
				if (rs.getString("IsEnglish").equalsIgnoreCase("0")) {
					SearchBO search = new SearchBO();
					search.setCountryName(rs.getString("Country"));
					search.setProgramName(rs.getString("AttributeValue"));
					search.setProgramSeriesId(rs.getString("ProgramSeriesID"));
					search.setEnglish(false);
					getAdultContentSearchNonEnglishData.add(new Object[] { search });
				}
			}

			// closing connections
			rs.close();
			db.closeDBConnection(connection);
		} catch (SQLException e) {
			logger.error("Exception Occurred while iterating through ResultSet", e);
		}
	}*/
	
	/**
	 * @description sets DB value for GetAdultContentSearchData
	 */
	private static void setAdultContentSearchNonEnglishData() {
		String		dbQuery		= DBQueriesConstants.ADULT_NON_ENGLISH_PROGRAM_LIST_QUERY;
		DBConnector	db			= new DBConnector();
		Connection	connection	= db.getDBConnection();
		ResultSet	rs			= db.getResult(connection, dbQuery);

		try {
			while (rs.next()) {
					SearchBO search = new SearchBO();
					search.setCountryName(PropertyReader.getConfigProperty(ConfigPropertiesConstants.DEFAULT_COUNTRY));
					search.setProgramName(rs.getString("attributevalue"));
					search.setTmsId(rs.getString("tmsid"));
					search.setEnglish(false);
					getAdultContentSearchNonEnglishData.add(new Object[] { search });
			}
			// closing connections
			rs.close();
			db.closeDBConnection(connection);
		} catch (SQLException e) {
			logger.error("Exception Occurred while iterating through ResultSet", e);
		}
	}
	
	
	/**
	 * @description provide data for GetDefaultlineupOffSetAPI
	 * @return
	 */
	@DataProvider(name = "getTimezoneData")
	public static synchronized Iterator<Object[]> getTimezoneData() {

		if (getTimezoneData.size() == 0) {
			setTimezoneData();
		}
		logger.warn("Total Records Found for getTimezoneData :: " + getTimezoneData.size());
		return getTimezoneData.iterator();
	}

	/**
	 * @description sets DB value for GetDefaultlineupOffSetAPI
	 */
	private static void setTimezoneData() {
		String		dbQuery		= DBQueriesConstants.GET_TIMEZONE_DATA_QUERY;
		DBConnector	db			= new DBConnector();
		Connection	connection	= db.getDBConnection();
		ResultSet	rs			= db.getResult(connection, dbQuery);

		try {
			while (rs.next()) {
				TimezoneBO timeZone = new TimezoneBO();
				timeZone.setTimeZoneName(rs.getString("name"));
				timeZone.setHeadendId(rs.getString("headendId"));
				timeZone.setCountry(rs.getString("country"));
				timeZone.setPrimeTime(rs.getInt("PrimeTime"));
				timeZone.setUtcOffSet(rs.getInt("utcoffset"));
				getTimezoneData.add(new Object[] { timeZone });
			}

			// closing connections
			rs.close();
			db.closeDBConnection(connection);
		} catch (SQLException e) {
			logger.error("Exception Occurred while iterating through ResultSet", e);
		}
	}

	/**
	 * @description provide data for GetDstOffsetForPostalByCountryAPI
	 * @return
	 */
	@DataProvider(name = "getDstOffsetForPostalCodeByCountryData")
	public static synchronized Iterator<Object[]> getDstOffsetForPostalCodeByCountryData() {

		if (getDstOffSetData.size() == 0) {
			setDstOffsetForPostalCodeByCountryData();
		}
		logger.warn("Total Records Found for getDstOffsetForPostalCodeByCountryData :: " + getDstOffSetData.size());
		return getDstOffSetData.iterator();
	}

	/**
	 * @description sets DB value for DstOffsetForPostalCodeByCountryData
	 */
	private static void setDstOffsetForPostalCodeByCountryData() {
		String		dbQuery		= DBQueriesConstants.GET_DST_OFFSET_FOR_POSTAL_CODE_BY_COUNTRY_QUERY;
		DBConnector	db			= new DBConnector();
		Connection	connection	= db.getDBConnection();
		ResultSet	rs			= db.getResult(connection, dbQuery);

		try {
			while (rs.next()) {
				ProvidersBO providers = new ProvidersBO();
				providers.setCountry(rs.getString("countrycode"));
				providers.setDstStart(rs.getString("DSTStart"));
				providers.setDstEnd(rs.getString("DSTEnd"));
				providers.setStdUtcOffSet(rs.getString("utcoffset"));
				providers.setPrimeTime(rs.getString("PrimeTime"));
				providers.setPostalCode(rs.getString("postalcode"));
				getDstOffSetData.add(new Object[] { providers });
			}

			// closing connections
			rs.close();
			db.closeDBConnection(connection);
		} catch (SQLException e) {
			logger.error("Exception Occurred while iterating through ResultSet", e);
		}
	}
	
	
/***************************************DATA PROVIDER FOR CACHE WARMING**************************************	
	 * @return 
	 * @description to provide the headends details required for cache warming purpose
	 * @return schedule BO
	 */
	@DataProvider(name = "getHeadendData")
	public static synchronized  Iterator<Object[]> getHeadendData() {
		if (headendsData.size() == 0) {
			setHeadendsData();
		}
		logger.warn("Total Records Found for getHeadendData :: " + headendsData.size());
		return headendsData.iterator();
	}
	
	/**
	 * @description sets DB values for getHeadendsData
	 */
	private static void setHeadendsData() {
		
		String		dbQuery		= DBQueriesConstants.GET_HEADEND_DATA_FOR_CACHE_WARMING;
		DBConnector	db			= new DBConnector();
		Connection	connection	= db.getDBConnection();
		ResultSet	rs			= db.getResult(connection, dbQuery);
		DateAndTime dateTime    = new DateAndTime();

		try
		{
			while (rs.next()) {
				ScheduleBO schedule = new ScheduleBO();
				schedule.setHeadendId(rs.getString("headendid"));
				schedule.setCountryName(rs.getString("country"));
				schedule.setDeviceType(rs.getString("device"));
				schedule.setEpochTime(dateTime.getEpochTime());
				schedule.setTimeSpan(PropertyReader.getConfigProperty(ConfigPropertiesConstants.TIME_SPAN));
				headendsData.add(new Object[] { schedule });
			}
		}
		catch(SQLException e)
		{
			logger.error("Exception Occurred while iterating through ResultSet", e);
		}
		
	}
	
	
	
}

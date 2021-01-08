/**
 * 
 */
package com.api.gracenote.coreResources.dataProviders;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.testng.annotations.DataProvider;

import com.api.gracenote.bo.ScheduleBO;
import com.api.gracenote.constants.ConfigPropertiesConstants;
import com.api.gracenote.constants.DBQueriesConstants;
import com.api.gracenote.constants.EndPointUriConstants;
import com.api.gracenote.constants.ResponseKeyConstants;
import com.api.gracenote.constants.StatusCodeConstants;
import com.api.gracenote.coreResources.dbConnectors.DBConnector;
import com.api.gracenote.coreResources.fileReaders.PropertyReader;
import com.api.gracenote.utilities.Assertions;
import com.api.gracenote.utilities.DateAndTime;
import com.api.gracenote.utilities.Headers;

import junit.framework.Assert;

/**
 * @author kunal.ashar
 *
 */
public class APIDataProvider {

	private static List<Object[]>	postScheduleSingleStationAffiliate	= new ArrayList<Object[]>();
	private static Logger			logger								= Logger.getLogger(APIDataProvider.class);

	/**
	 * @description provide data for PostScheduleSingleStationAffiliate API
	 * @return
	 */
	@DataProvider(name = "getPostScheduleSingleStationAffiliate")
	public static synchronized Iterator<Object[]> getPostScheduleSingleStationAffiliate() {
		if (postScheduleSingleStationAffiliate.size() == 0) {
			setPostScheduleSingleStationAffiliate();
		}

		logger.info("Total Records Found for getPostScheduleSingleStationAffiliate :: " + postScheduleSingleStationAffiliate.size());
		return postScheduleSingleStationAffiliate.iterator();
	}

	/**
	 * @description sets value for PostScheduleSingleStationAffiliate from
	 *              getAffiliatesProperties testcase
	 */
	public static void setPostScheduleSingleStationAffiliate() {

		String		dbQuery		= DBQueriesConstants.POST_SCHEDULE_SINGLE_STATION_AFFILIATES_QUERY;
		DBConnector	db			= new DBConnector();
		Connection	connection	= db.getDBConnection();
		ResultSet	rs			= db.getResult(connection, dbQuery);
		DateAndTime dateTime 	= new DateAndTime(); 

		try {
			while (rs.next()) {

				String		affiliateTag	= rs.getString("affl_tag");
				ScheduleBO	schedule		= getDataFromAffiliateAPIResponse(affiliateTag);
				schedule.setTimeSpan(PropertyReader.getConfigProperty(ConfigPropertiesConstants.TIME_SPAN));
				schedule.setEpochTime(dateTime.getEpochTime());
				postScheduleSingleStationAffiliate.add(new Object[] { schedule });
			}

			// closing connections
			rs.close();
			db.closeDBConnection(connection);
		} catch (SQLException e) {
			logger.error("Exception Occurred while iterating through ResultSet", e);
		}
	}

	/**
	 * 
	 * @description gets values from affiliates API response
	 * @param affiliateTag
	 * @return
	 */
	private static ScheduleBO getDataFromAffiliateAPIResponse(String affiliateTag) {
		String					uri			= EndPointUriConstants.GET_AFFILIATE_URL + affiliateTag + "/"
		        + PropertyReader.getConfigProperty(ConfigPropertiesConstants.LANG_CODE);

		// Perform GET call
		HttpGet					get			= new HttpGet(uri);
		CloseableHttpClient		client		= HttpClientBuilder.create().build();
		CloseableHttpResponse	response	= null;

		// adding headers
		for (Map.Entry<String, String> entry : Headers.getCommonHeaders().entrySet()) {
			get.addHeader(entry.getKey(), entry.getValue());
		}

		// performing get api call
		try {
			response = client.execute(get);
		} catch (ClientProtocolException e) {
			logger.error("Exception Occurred while performing API Get call.", e);
		} catch (IOException e) {
			logger.error("Exception Occurred while performing API Get call.", e);
		}

		// verify status code
		boolean flag = response.getStatusLine().getStatusCode() == StatusCodeConstants.STATUS_CODE_200;

		// fail testcase if false received
		if (!flag) {
			Assert.fail("Incorrect status code received.");
		}

		// get response as String
		String responseString = null;
		try {
			responseString = EntityUtils.toString(response.getEntity());
		} catch (ParseException e) {
			logger.error("Exception Occurred while converting Resposne to String.", e);
		} catch (IOException e) {
			logger.error("Exception Occurred while converting Resposne to String.", e);
		}

		// converting response to String and creating responseObject
		JSONObject	responseObject	= new JSONObject(responseString);

		// to set value for getPostScheduleSingleStationAffiliate dataprovider
		ScheduleBO	schedule		= new ScheduleBO();

		String		defaultCountry	= Assertions.verifyNotNull("", responseObject, ResponseKeyConstants.DEFAULT_COUNTRY_JSON_KEY);
		String		device			= Assertions.verifyNotNull("", responseObject, ResponseKeyConstants.DEVICE_JSON_KEY);
		String		programSvcID	= Assertions.verifyNotNull("", responseObject, ResponseKeyConstants.PROGRAM_SVC_IDS_JSON_KEY);
		String		postalCode		= Assertions.verifyNotNull("", responseObject, ResponseKeyConstants.DEFAULT_POSTAL_CODE_JSON_KEY);
		String		defaultHeadend	= Assertions.verifyNotNull("", responseObject, ResponseKeyConstants.DEFAULT_HEADEND_JSON_KEY);

		schedule.setAffiliateTag(affiliateTag);
		schedule.setCountryName(defaultCountry);
		schedule.setDeviceType(device);
		schedule.setHeadendId(defaultHeadend);
		schedule.setPostalCode(postalCode);
		schedule.setProgramSvcId(programSvcID);

		return schedule;
	}

}

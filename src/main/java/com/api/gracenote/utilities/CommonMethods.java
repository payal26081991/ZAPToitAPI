/**
 * 
 */
package com.api.gracenote.utilities;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.api.gracenote.constants.ConfigPropertiesConstants;
import com.api.gracenote.constants.StringConstants;
import com.api.gracenote.coreResources.fileCreation.CreateJsonFiles;
import com.api.gracenote.coreResources.fileCreation.CreateTextFiles;
import com.api.gracenote.coreResources.fileReaders.PropertyReader;
import com.api.gracenote.coreResources.testCaseManager.TestCaseResults;

import junit.framework.Assert;

/**
 * @author kunal.ashar
 *
 */
public class CommonMethods {

	private static Logger logger = Logger.getLogger(CommonMethods.class);

	/**
	 * 
	 * @description used to perform GET api calls
	 * @param uri
	 * @return
	 */
	public static CloseableHttpResponse get(String testCaseName, String uri, Map<String, String> headerMap) {

		// setting connection timeout to wait until response is received
		int						CONNECTION_TIMEOUT	= Integer.parseInt(PropertyReader.getConfigProperty(ConfigPropertiesConstants.TIMEOUT))
		        * 1000;

		//RequestConfig			requestConfig		= RequestConfig.custom().setConnectionRequestTimeout(CONNECTION_TIMEOUT)
		//       .setConnectTimeout(CONNECTION_TIMEOUT).setSocketTimeout(CONNECTION_TIMEOUT).build();

		// creating client objects
		HttpGet					get					= new HttpGet(uri);
		//CloseableHttpClient		client				= HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();
		CloseableHttpClient		client				= HttpClientBuilder.create().build();
		CloseableHttpResponse	response			= null;

		// creating requested uri text file.
		CreateTextFiles.createRequestedUriTextFile(testCaseName, uri);

		// adding data to report
		TestCaseResults.addResult(testCaseName, StringConstants.URI_KEY, uri);

		// adding headers
		for (Map.Entry<String, String> entry : headerMap.entrySet()) {
			get.addHeader(entry.getKey(), entry.getValue());
		}

		// performing get api call
		try {
			response = client.execute(get);
		} catch (ClientProtocolException e) {
			logger.error("Exception Occurred while performing API Get call.", e);
		} catch (SocketTimeoutException e) {
			logger.error("Exception Occurred while performing API Get call.", e);
			logger.error("**************URI: " + uri);
		} catch (IOException e) {
			logger.error("Exception Occurred while performing API Get call.", e);
		} catch (Exception e) {
			logger.error("Exception Occurred while performing API Get call.", e);
			logger.error("**************URI: " + uri);
		}

		// to fail if no response received
		if (response == null) {
			TestCaseResults.addResult(testCaseName, StringConstants.ASSERT_KEY, "Response received as null");
			Assert.fail();
		}

		return response;
	}

	/**
	 * 
	 * @description used to perform POST api calls
	 * @param uri
	 * @param entityString
	 * @param headerMap
	 * @return
	 */
	public static CloseableHttpResponse post(String testCaseName, String uri, String entityString, Map<String, String> headerMap) {

		// setting connection timeout to wait until response is received
		int						CONNECTION_TIMEOUT	= Integer.parseInt(PropertyReader.getConfigProperty(ConfigPropertiesConstants.TIMEOUT))
		        * 1000;

		RequestConfig			requestConfig		= RequestConfig.custom().setConnectionRequestTimeout(CONNECTION_TIMEOUT)
		        .setConnectTimeout(CONNECTION_TIMEOUT).setSocketTimeout(CONNECTION_TIMEOUT).build();

		// creating client objects
		CloseableHttpClient		client				= HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();
		CloseableHttpResponse	response			= null;
		HttpPost				post				= new HttpPost(uri);
		String filePath = null;

		// setting request config for post call
		post.setConfig(requestConfig);

		// adding data to report
		TestCaseResults.addResult(testCaseName, StringConstants.URI_KEY, uri);

		// adding entity to post
		try {
			post.setEntity(new StringEntity(entityString));

			// creating new json file for payload
			filePath = CreateJsonFiles.createPayloadJsonFile(testCaseName, entityString);

			// adding data to report
			TestCaseResults.addResult(testCaseName, StringConstants.PAYLOAD_KEY, filePath);

		} catch (UnsupportedEncodingException e) {
			logger.error("Exception Occurred while converting string to stringEntity.", e);
		}

		// adding headers
		for (Map.Entry<String, String> entry : headerMap.entrySet()) {
			post.addHeader(entry.getKey(), entry.getValue());
		}

		// performing post api call
		try {
			response = client.execute(post);
		} catch (ClientProtocolException e) {
			logger.error("Exception Occurred while performing API Post call.", e);
		} catch (SocketTimeoutException e) {
			logger.error("Exception Occurred while performing API Post call.", e);
			logger.error("***************URI: " + uri);
			logger.error("***************PAYLOAD: " + filePath);
		} catch (IOException e) {
			logger.error("Exception Occurred while performing API Post call.", e);
		} catch (Exception e) {
			logger.error("Exception Occurred while performing API Post call.", e);
			logger.error("***************URI: " + uri);
			logger.error("***************PAYLOAD: " + filePath);
		}

		// to fail if no response received
		if (response == null) {
			TestCaseResults.addResult(testCaseName, StringConstants.ASSERT_KEY, "Response received as null");
			Assert.fail();
		}

		return response;
	}

	/**
	 * 
	 * @description converts response to String
	 * @param response
	 * @return
	 * @note canno't call this method twice with same response. It'll throw
	 *       java.io.IOException: Stream closed Exception
	 */
	public static String getResponseAsString(String testCaseName, HttpResponse response) {

		String responseString = null;

		if (response != null) {
			try {
				responseString = EntityUtils.toString(response.getEntity());

				// creating new json file for response
				String filePath = CreateJsonFiles.createResponseJsonFile(testCaseName, responseString);

				// printing response in report file
				TestCaseResults.addResult(testCaseName, StringConstants.RESPONSE_KEY, filePath);

			} catch (ParseException e) {
				logger.error("Exception Occurred while converting Resposne to String.", e);
			} catch (IOException e) {
				logger.error("Exception Occurred while converting Resposne to String.", e);
			}
		}
		return responseString;
	}

	/**
	 * 
	 * @description creates JSONObject out of response string
	 * @param responseString
	 * @return
	 */
	public static JSONObject createJsonObjectFromResposneString(String testCaseName, String responseString) {
		JSONObject jsonObject = null;

		if (responseString != null) {
			jsonObject = new JSONObject(responseString);
		} else {
			logger.error("Resposne String received as null while creating JSONObject");
		}

		return jsonObject;
	}

	/**
	 * 
	 * @description to convert payload file to jsonObject for value manipulation
	 * @param filePath
	 * @return
	 */
	public static JSONObject createJsonObjectFromFile(String testCaseName, String filePath) {
		JSONObject	jsonObject	= null;
		String		str			= null;

		try {
			str			= new String(Files.readAllBytes(Paths.get(filePath)));
			jsonObject	= new JSONObject(str);

		} catch (IOException e) {
			logger.error("Exception Occurred while converting payload file to JsonObject", e);
		}
		return jsonObject;
	}

	/**
	 * 
	 * @description returns jsonArray from jsonObject
	 * @param jsonObject
	 * @param keyName
	 * @return
	 */
	public static JSONArray getJsonArrayFromJsonObject(String testCaseName, JSONObject jsonObject, String keyName) {
		JSONArray jsonArray = null;

		if (keyName != null) {
			try {
				jsonArray = jsonObject.getJSONArray(keyName);
			} catch (JSONException e) {
				TestCaseResults.addResult(testCaseName, StringConstants.ASSERT_KEY, "Key :: " + keyName + " not present in jsonObject.");
				throw new JSONException("Exception Occurred while fetching jsonArray from JsonObject.", e);
			}
		}
		return jsonArray;
	}
}

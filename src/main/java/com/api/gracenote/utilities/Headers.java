package com.api.gracenote.utilities;

import java.util.HashMap;
import java.util.Map;

import com.api.gracenote.constants.StringConstants;
import com.google.common.net.HttpHeaders;

public class Headers {

	/**
	 * @description common headers used in api calls
	 * @return
	 */
	public static Map<String, String> getCommonHeaders() {
		Map<String, String> headerMap = new HashMap<String, String>();

		// adding values to headers
		headerMap.put(HttpHeaders.CONTENT_TYPE, StringConstants.CONTENT_TYPE_APPLICATION_JSON);
		headerMap.put("Connection", "close, TE");

		return headerMap;
	}

}

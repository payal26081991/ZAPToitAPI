package com.api.gracenote.utilities;

import java.util.HashMap;
import java.util.Map;

public class UserPreferenceMapping {
	
	private Map<String,String> preferenceMap ;
	
	public UserPreferenceMapping()
	{
		preferenceMap = new HashMap<String,String>();
		preferenceMap.put("m", "isMusic");
		preferenceMap.put("p", "isPPV");
		preferenceMap.put("h", "isHD");
		preferenceMap.put("8", "isFC");
		preferenceMap.put("16", "isNow");
		preferenceMap.put("32", "isPrimetime");
		preferenceMap.put("64", "isDaytime");
		preferenceMap.put("128", "is3hr");
		preferenceMap.put("256", "is6hr");
		preferenceMap.put("tz", "timezone");
	}

	public Map<String, String> getPreferenceMap() {
		return preferenceMap;
	}


	public void setPreferenceMap(Map<String, String> preferenceMap) {
		this.preferenceMap = preferenceMap;
	}
	
}

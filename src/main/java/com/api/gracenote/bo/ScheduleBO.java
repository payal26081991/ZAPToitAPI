/**
 * 
 */
package com.api.gracenote.bo;

import com.api.gracenote.utilities.DateAndTime;

/**
 * @author kunal.ashar
 *
 */
public class ScheduleBO {

	private String	headendId;
	private String	deviceType;
	private String	countryName;
	private String	postalCode;
	private String	programSvcId;
	private String	affiliateTag;
	private String	timeSpan;
	private String	includeChannels;
	private long    epochTime;
	private boolean isProgSvc ; 
	
	public long getEpochTime() {
		return epochTime;
	}

	public void setEpochTime(long epochTime) {
		this.epochTime = epochTime;
	}

	/**
	 * @return the headendId
	 */
	public String getHeadendId() {
		return headendId;
	}

	/**
	 * @param headendId the headendId to set
	 */
	public void setHeadendId(String headendId) {
		this.headendId = headendId;
	}

	/**
	 * @return the deviceType
	 */
	public String getDeviceType() {
		return deviceType;
	}

	/**
	 * @param deviceType the deviceType to set
	 */
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	/**
	 * @return the countryName
	 */
	public String getCountryName() {
		return countryName;
	}

	/**
	 * @param countryName the countryName to set
	 */
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	/**
	 * @return the postalCode
	 */
	public String getPostalCode() {
		return postalCode;
	}

	/**
	 * @param postalCode the postalCode to set
	 */
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	/**
	 * @return the programSvcId
	 */
	public String getProgramSvcId() {
		return programSvcId;
	}

	/**
	 * @param programSvcId the programSvcId to set
	 */
	public void setProgramSvcId(String programSvcId) {
		this.programSvcId = programSvcId;
	}

	/**
	 * @return the affiliateTag
	 */
	public String getAffiliateTag() {
		return affiliateTag;
	}

	/**
	 * @param affiliateTag the affiliateTag to set
	 */
	public void setAffiliateTag(String affiliateTag) {
		this.affiliateTag = affiliateTag;
	}

	/**
	 * @return the timeSpan
	 */
	public String getTimeSpan() {
		return timeSpan;
	}

	/**
	 * @param timeSpan the timeSpan to set
	 */
	public void setTimeSpan(String timeSpan) {
		this.timeSpan = timeSpan;
	}

	/**
	 * @return the includeChannels
	 */
	public String getIncludeChannels() {
		return includeChannels;
	}

	/**
	 * @param includeChannels the includeChannels to set
	 */
	public void setIncludeChannels(String includeChannels) {
		this.includeChannels = includeChannels;
	}

	public boolean getIsProgSvc() {
		return isProgSvc;
	}

	public void setIsProgSvc(boolean isProgSvc) {
		this.isProgSvc = isProgSvc;
	}
}

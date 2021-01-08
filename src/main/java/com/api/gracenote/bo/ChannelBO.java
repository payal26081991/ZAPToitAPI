/**
 * 
 */
package com.api.gracenote.bo;

/**
 * @author kunal.ashar
 *
 */
public class ChannelBO {

	private String	programSvcID;
	private String	headendId;
	private String	countryCode;
	private String	postalCode;
	private String	deviceType;

	/**
	 * @return the programSvcID
	 */
	public String getProgramSvcID() {
		return programSvcID;
	}

	/**
	 * @param programSvcID the programSvcID to set
	 */
	public void setProgramSvcID(String programSvcID) {
		this.programSvcID = programSvcID;
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
	 * @return the countryCode
	 */
	public String getCountryCode() {
		return countryCode;
	}

	/**
	 * @param countryCode the countryCode to set
	 */
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
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

}

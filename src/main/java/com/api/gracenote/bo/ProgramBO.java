/**
 * 
 */
package com.api.gracenote.bo;

/**
 * @author kunal.ashar
 *
 */
public class ProgramBO {

	private String	programSeriesId;
	private String	headendId;
	private String	countryCode;
	private String	postalCode;
	private String	device;
	private String	affiliateId;

	public String getProgramSeriesId() {
		return programSeriesId;
	}

	public void setProgramSeriesId(String programSeriesId) {
		this.programSeriesId = programSeriesId;
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
	 * @return the device
	 */
	public String getDevice() {
		return device;
	}

	/**
	 * @param device the device to set
	 */
	public void setDevice(String device) {
		this.device = device;
	}

	/**
	 * @return the affiliateId
	 */
	public String getAffiliateId() {
		return affiliateId;
	}

	/**
	 * @param affiliateId the affiliateId to set
	 */
	public void setAffiliateId(String affiliateId) {
		this.affiliateId = affiliateId;
	}
}

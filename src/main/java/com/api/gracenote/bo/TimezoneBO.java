/**
 * 
 */
package com.api.gracenote.bo;

/**
 * @author kunal.ashar
 *
 */
public class TimezoneBO {

	private String	timeZoneName;
	private String	headendId;
	private String	country;
	private int		primeTime;
	private int		utcOffSet;

	/**
	 * @return the timeZoneName
	 */
	public String getTimeZoneName() {
		return timeZoneName;
	}

	/**
	 * @param timeZoneName the timeZoneName to set
	 */
	public void setTimeZoneName(String timeZoneName) {
		this.timeZoneName = timeZoneName;
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
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the primeTime
	 */
	public int getPrimeTime() {
		return primeTime;
	}

	/**
	 * @param primeTime the primeTime to set
	 */
	public void setPrimeTime(int primeTime) {
		this.primeTime = primeTime;
	}

	/**
	 * @return the utcOffSet
	 */
	public int getUtcOffSet() {
		return utcOffSet;
	}

	/**
	 * @param utcOffSet the utcOffSet to set
	 */
	public void setUtcOffSet(int utcOffSet) {
		this.utcOffSet = utcOffSet;
	}

}

/**
 * 
 */
package com.api.gracenote.bo;

/**
 * @author kunal.ashar
 *
 */
public class SearchBO {

	private String	programSeriesId;
	private String	programName;
	private String	countryName;
	private boolean	english;
	private String tmsId;

	/**
	 * @return the tmsId
	 */
	public String getTmsId() {
		return tmsId;
	}
	
	/**
	 * @param programSeriesId the tmsId to set
	 */
	public void setTmsId(String tmsId) {
		this.tmsId = tmsId;
	}

	/**
	 * @return the programSeriesId
	 */
	public String getProgramSeriesId() {
		return programSeriesId;
	}

	/**
	 * @param programSeriesId the programSeriesId to set
	 */
	public void setProgramSeriesId(String programSeriesId) {
		this.programSeriesId = programSeriesId;
	}

	/**
	 * @return the programName
	 */
	public String getProgramName() {
		return programName;
	}

	/**
	 * @param programName the programName to set
	 */
	public void setProgramName(String programName) {
		this.programName = programName;
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
	 * @return the english
	 */
	public boolean isEnglish() {
		return english;
	}

	/**
	 * @param english the english to set
	 */
	public void setEnglish(boolean english) {
		this.english = english;
	}
}

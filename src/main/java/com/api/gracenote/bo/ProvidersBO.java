/**
 * 
 */
package com.api.gracenote.bo;

/**
 * @author kunal.ashar
 *
 */
public class ProvidersBO {

	private String	postalCode;
	private String	country;
	private int		expectedStatusCode;
	private String	dstStart;
	private String	dstEnd;
	private String	stdUtcOffSet;
	private String	primeTime;

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
	 * @return the expectedStatusCode
	 */
	public int getExpectedStatusCode() {
		return expectedStatusCode;
	}

	/**
	 * @param expectedStatusCode the expectedStatusCode to set
	 */
	public void setExpectedStatusCode(int expectedStatusCode) {
		this.expectedStatusCode = expectedStatusCode;
	}

	/**
	 * @return the dstStart
	 */
	public String getDstStart() {
		return dstStart;
	}

	/**
	 * @param dstStart the dstStart to set
	 */
	public void setDstStart(String dstStart) {
		this.dstStart = dstStart;
	}

	/**
	 * @return the dstEnd
	 */
	public String getDstEnd() {
		return dstEnd;
	}

	/**
	 * @param dstEnd the dstEnd to set
	 */
	public void setDstEnd(String dstEnd) {
		this.dstEnd = dstEnd;
	}

	/**
	 * @return the stdUtcOffSet
	 */
	public String getStdUtcOffSet() {
		return stdUtcOffSet;
	}

	/**
	 * @param stdUtcOffSet the stdUtcOffSet to set
	 */
	public void setStdUtcOffSet(String stdUtcOffSet) {
		this.stdUtcOffSet = stdUtcOffSet;
	}

	/**
	 * @return the primeTime
	 */
	public String getPrimeTime() {
		return primeTime;
	}

	/**
	 * @param primeTime the primeTime to set
	 */
	public void setPrimeTime(String primeTime) {
		this.primeTime = primeTime;
	}

}

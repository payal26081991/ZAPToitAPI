/**
 * 
 */
package com.api.gracenote.bo;

/**
 * @author kunal.ashar
 *
 */
public class FavoriteBO {

	private String	userId;
	private String	programSvcId;
	private String	isFavorite;
	private String	isPresent;

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
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
	 * @return the isFavorite
	 */
	public String getIsFavorite() {
		return isFavorite;
	}

	/**
	 * @param isFavorite the isFavorite to set
	 */
	public void setIsFavorite(String isFavorite) {
		this.isFavorite = isFavorite;
	}

	/**
	 * @return the isPresent
	 */
	public String getIsPresent() {
		return isPresent;
	}

	/**
	 * @param isPresent the isPresent to set
	 */
	public void setIsPresent(String isPresent) {
		this.isPresent = isPresent;
	}

}

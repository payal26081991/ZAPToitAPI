/**
 * 
 */
package com.api.gracenote.bo;

/**
 * @author kunal.ashar
 *
 */
public class LoginBO {

	private String	emailId;
	private String	password;
	private String	userType;
	private String	isFacebookUser;
	private String	objectId;
	private String	userId;
	private String	affiliateId;
	private String  preferneces;
	
	public String getPreferneces() {
		return preferneces;
	}

	public void setPreferneces(String preferneces) {
		this.preferneces = preferneces;
	}

	/**
	 * @return the emailId
	 */
	public String getEmailId() {
		return emailId;
	}

	/**
	 * @param emailId the emailId to set
	 */
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the userType
	 */
	public String getUserType() {
		return userType;
	}

	/**
	 * @param userType the userType to set
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}

	/**
	 * @return the isFacebookUser
	 */
	public String getIsFacebookUser() {
		return isFacebookUser;
	}

	/**
	 * @param isFacebookUser the isFacebookUser to set
	 */
	public void setIsFacebookUser(String isFacebookUser) {
		this.isFacebookUser = isFacebookUser;
	}

	/**
	 * @return the objectId
	 */
	public String getObjectId() {
		return objectId;
	}

	/**
	 * @param objectId the objectId to set
	 */
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

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

/**
 * 
 */
package com.api.gracenote.bo;

/**
 * @author kunal.ashar
 *
 */
public class UserBO {

	private String	emailId;
	private String	password;
	private String	userType;
	private String	isFacebookUser;
	private String	isNewUser;

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
	 * @return the isNewUser
	 */
	public String getIsNewUser() {
		return isNewUser;
	}

	/**
	 * @param isNewUser the isNewUser to set
	 */
	public void setIsNewUser(String isNewUser) {
		this.isNewUser = isNewUser;
	}

}

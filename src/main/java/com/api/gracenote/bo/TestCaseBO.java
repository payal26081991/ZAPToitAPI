/**
 * 
 */
package com.api.gracenote.bo;

/**
 * @author kunal.ashar
 *
 */
public class TestCaseBO {

	private String	testCaseName		= null;
	private String	testCaseDescription	= null;

	/**
	 * @return the testCaseName
	 */
	public String getTestCaseName() {
		return testCaseName;
	}

	/**
	 * @param testCaseName the testCaseName to set
	 */
	public void setTestCaseName(String testCaseName) {
		this.testCaseName = testCaseName;
	}

	/**
	 * @return the testCaseDescription
	 */
	public String getTestCaseDescription() {
		return testCaseDescription;
	}

	/**
	 * @param testCaseDescription the testCaseDescription to set
	 */
	public void setTestCaseDescription(String testCaseDescription) {
		this.testCaseDescription = testCaseDescription;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TestCase [testCaseName=" + testCaseName + ", testCaseDescription=" + testCaseDescription + "]";
	}

}

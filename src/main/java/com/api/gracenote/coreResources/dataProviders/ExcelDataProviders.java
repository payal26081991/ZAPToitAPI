/**
 * 
 */
package com.api.gracenote.coreResources.dataProviders;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;

import com.api.gracenote.bo.FavoriteBO;
import com.api.gracenote.bo.LoginBO;
import com.api.gracenote.bo.ProgramBO;
import com.api.gracenote.bo.ProvidersBO;
import com.api.gracenote.bo.ScheduleBO;
import com.api.gracenote.bo.SimilarShowsBO;
import com.api.gracenote.bo.UserBO;
import com.api.gracenote.constants.FilePathConstants;
import com.api.gracenote.constants.StringConstants;

/**
 * @author kunal.ashar
 *
 */
public class ExcelDataProviders {

	static Logger					logger								= Logger.getLogger(ExcelDataProviders.class);

	private static List<Object[]>	favoriteChannelData					= new ArrayList<Object[]>();
	private static List<Object[]>	favoriteChannelNegativeData			= new ArrayList<Object[]>();
	private static List<Object[]>	postUserData						= new ArrayList<Object[]>();
	private static List<Object[]>	postUserNegativeData				= new ArrayList<Object[]>();
	private static List<Object[]>	postValidateLoginData				= new ArrayList<Object[]>();
	private static List<Object[]>	postValidateLoginNegativeData		= new ArrayList<Object[]>();
	private static List<Object[]>	postEpisodeGuideNegativeData		= new ArrayList<Object[]>();
	private static List<Object[]>	getPostalCodeProvidersNegativeData	= new ArrayList<Object[]>();
	private static List<Object[]>	postScheduleByLineupIdNegativeData	= new ArrayList<Object[]>();
	private static List<Object[]>	similarShowsData					= new ArrayList<Object[]>();

	/**
	 * @description provide data for favoriteChannelAPI
	 * @return
	 */
	@DataProvider(name = "getFavoriteChannelData")
	public static synchronized Iterator<Object[]> getFavoriteChannelData() {

		if (favoriteChannelData.size() == 0) {
			setFavoriteChannelData();
		}
		logger.warn("Total Records Found for getFavoriteChannelData :: " + favoriteChannelData.size());
		return favoriteChannelData.iterator();
	}

	/**
	 * @description sets excel values for favoriteChannelData
	 */
	private static void setFavoriteChannelData() {

		XSSFSheet	sh		= getSheet(FilePathConstants.FAVORITE_TESTDATA_FILE_PATH, StringConstants.FAVOURITE_CHANNEL_SHEET);

		int			rowNum	= sh.getPhysicalNumberOfRows();

		for (int i = 1; i < rowNum; i++) {

			FavoriteBO	favorite	= new FavoriteBO();

			int			colNum		= sh.getRow(0).getLastCellNum();

			for (int j = 0; j < colNum; j++) {

				switch (j) {

					case 0:
						String userId = sh.getRow(i).getCell(j).getStringCellValue();
						favorite.setUserId(userId);
						break;

					case 1:
						String programSvcId = sh.getRow(i).getCell(j).getStringCellValue();
						favorite.setProgramSvcId(programSvcId);
						break;

					case 2:
						String isFavorite = sh.getRow(i).getCell(j).getStringCellValue();
						favorite.setIsFavorite(isFavorite);
						break;

					case 3:
						String isPresent = sh.getRow(i).getCell(j).getStringCellValue();
						favorite.setIsPresent(isPresent);
						break;

					default:
						logger.error("Incorrect case received for setFavoriteChannelData. Case :: " + j);
						break;
				}
			}

			favoriteChannelData.add(new Object[] { favorite });
		}
	}

	/**
	 * @description provide negative data for favoriteChannelAPI
	 * @return
	 */
	@DataProvider(name = "getFavoriteChannelNegativeData")
	public static synchronized Iterator<Object[]> getFavoriteChannelNegativeData() {

		if (favoriteChannelNegativeData.size() == 0) {
			setFavoriteChannelNegativeData();
		}
		logger.warn("Total Records Found for getFavoriteChannelNegativeData :: " + favoriteChannelNegativeData.size());
		return favoriteChannelNegativeData.iterator();
	}

	/**
	 * @description sets excel values for favoriteChannelNegativeData
	 */
	private static void setFavoriteChannelNegativeData() {

		XSSFSheet	sh		= getSheet(FilePathConstants.FAVORITE_TESTDATA_FILE_PATH,
		        StringConstants.FAVOURITE_CHANNEL_NEGATIVE_SCENARIOS_SHEET);

		int			rowNum	= sh.getPhysicalNumberOfRows();

		for (int i = 1; i < rowNum; i++) {

			FavoriteBO	favorite	= new FavoriteBO();

			int			colNum		= sh.getRow(0).getLastCellNum();

			for (int j = 0; j < colNum; j++) {

				switch (j) {

					case 0:
						String userId = sh.getRow(i).getCell(j).getStringCellValue();
						favorite.setUserId(userId);
						break;

					case 1:
						String programSvcId = sh.getRow(i).getCell(j).getStringCellValue();
						favorite.setProgramSvcId(programSvcId);
						break;

					case 2:
						String isFavorite = sh.getRow(i).getCell(j).getStringCellValue();
						favorite.setIsFavorite(isFavorite);
						break;

					case 3:
						String isPresent = sh.getRow(i).getCell(j).getStringCellValue();
						favorite.setIsPresent(isPresent);
						break;

					default:
						logger.error("Incorrect case received for setFavoriteChannelNegativeData. Case :: " + j);
						break;
				}
			}

			favoriteChannelNegativeData.add(new Object[] { favorite });
		}
	}

	/**
	 * @description provide negative data for PostUserAPI
	 * @return
	 */
	@DataProvider(name = "getPostUserData")
	public static synchronized Iterator<Object[]> getPostUserData() {

		if (postUserData.size() == 0) {
			setPostUserData();
		}
		logger.warn("Total Records Found for getPostUserData :: " + postUserData.size());
		return postUserData.iterator();
	}

	/**
	 * @description sets excel values for PostUserData
	 */
	private static void setPostUserData() {

		XSSFSheet	sh		= getSheet(FilePathConstants.POSTUSER_TESTDATA_FILE_PATH, StringConstants.POSTUSER_SHEET);

		int			rowNum	= sh.getPhysicalNumberOfRows();

		for (int i = 1; i < rowNum; i++) {

			UserBO	user	= new UserBO();

			int		colNum	= sh.getRow(0).getLastCellNum();

			for (int j = 0; j < colNum; j++) {

				switch (j) {

					case 0:
						String emailId = sh.getRow(i).getCell(j).getStringCellValue();
						user.setEmailId(RandomStringUtils.randomAlphanumeric(8) + emailId);
						break;

					case 1:
						String password = sh.getRow(i).getCell(j).getStringCellValue();
						user.setPassword(password + RandomStringUtils.randomAlphanumeric(4));
						break;

					case 2:
						String userType = sh.getRow(i).getCell(j).getStringCellValue();
						user.setUserType(userType);
						break;

					case 3:
						String isFacebookUser = sh.getRow(i).getCell(j).getStringCellValue();
						user.setIsFacebookUser(isFacebookUser);
						break;

					case 4:
						String isNewUser = sh.getRow(i).getCell(j).getStringCellValue();
						user.setIsNewUser(isNewUser);
						break;

					default:
						logger.error("Incorrect case received for setPostUserData. Case :: " + j);
						break;
				}
			}

			postUserData.add(new Object[] { user });
		}
	}

	/**
	 * @description provide negative data for PostUserAPI
	 * @return
	 */
	@DataProvider(name = "getPostUserNegativeData")
	public static synchronized Iterator<Object[]> getPostUserNegativeData() {

		if (postUserNegativeData.size() == 0) {
			setPostUserNegativeData();
		}
		logger.warn("Total Records Found for getPostUserNegativeData :: " + postUserNegativeData.size());
		return postUserNegativeData.iterator();
	}

	/**
	 * @description sets excel values for PostUserData
	 */
	private static void setPostUserNegativeData() {

		XSSFSheet	sh		= getSheet(FilePathConstants.POSTUSER_TESTDATA_FILE_PATH, StringConstants.POSTUSER_NEGATIVE_SCENARIO_SHEET);

		int			rowNum	= sh.getPhysicalNumberOfRows();

		for (int i = 1; i < rowNum; i++) {

			UserBO	user	= new UserBO();

			int		colNum	= sh.getRow(0).getLastCellNum();

			for (int j = 0; j < colNum; j++) {

				switch (j) {

					case 0:
						String emailId = sh.getRow(i).getCell(j).getStringCellValue();
						if (emailId.length() == 0) {
							user.setEmailId(emailId);
						} else {
							user.setEmailId(RandomStringUtils.randomAlphanumeric(8) + emailId);
						}
						break;

					case 1:
						String password = sh.getRow(i).getCell(j).getStringCellValue();
						if (password.length() == 0) {
							user.setPassword(password);
						} else {
							user.setPassword(password + RandomStringUtils.randomAlphanumeric(4));
						}
						break;

					case 2:
						String userType = sh.getRow(i).getCell(j).getStringCellValue();
						user.setUserType(userType);
						break;

					case 3:
						String isFacebookUser = sh.getRow(i).getCell(j).getStringCellValue();
						user.setIsFacebookUser(isFacebookUser);
						break;

					default:
						logger.error("Incorrect case received for setPostUserNegativeData. Case :: " + j);
						break;
				}
			}

			postUserNegativeData.add(new Object[] { user });
		}
	}

	/**
	 * @description provide negative data for PostValidateLoginAPI
	 * @return
	 */
	@DataProvider(name = "getPostValidateLoginData")
	public static synchronized Iterator<Object[]> getPostValidateLoginData() {

		if (postValidateLoginData.size() == 0) {
			setPostValidateLoginData();
		}
		logger.warn("Total Records Found for getPostValidateLoginData :: " + postValidateLoginData.size());
		return postValidateLoginData.iterator();
	}
	
	/**
	 * @description provide negative data for PostValidateLoginAPI
	 * @return
	 */
	@DataProvider(name = "getUserPreferenceData")
	public static synchronized Iterator<Object[]> getUserPreferenceData() {

		if (postValidateLoginData.size() == 0) {
			setPostValidateLoginData();
		}
		logger.warn("Total Records Found for getPostValidateLoginData :: " + postValidateLoginData.size());
		return postValidateLoginData.iterator();
	}
	
	/**
	 * @description sets excel values for PostValidateLogin
	 */
	private static void setPostValidateLoginData() {

		XSSFSheet	sh		= getSheet(FilePathConstants.LOGIN_TESTDATA_FILE_PATH, StringConstants.POST_VALIDATE_LOGIN_SHEET);

		int			rowNum	= sh.getPhysicalNumberOfRows();

		for (int i = 1; i < rowNum; i++) {

			LoginBO	login	= new LoginBO();

			int		colNum	= sh.getRow(0).getLastCellNum();

			for (int j = 0; j < colNum; j++) {

				switch (j) {

					case 0:
						String emailId = sh.getRow(i).getCell(j).getStringCellValue();
						login.setEmailId(emailId);

						break;

					case 1:
						String password = null;
						try {
							password = sh.getRow(i).getCell(j).getStringCellValue();
							login.setPassword(password);
						} catch (NullPointerException e) {
							login.setPassword("");
						}

						break;

					case 2:
						String userType = sh.getRow(i).getCell(j).getStringCellValue();
						login.setUserType(userType);
						break;

					case 3:
						String isFacebookUser = sh.getRow(i).getCell(j).getStringCellValue();
						login.setIsFacebookUser(isFacebookUser);
						break;

					case 4:
						String objectId = sh.getRow(i).getCell(j).getStringCellValue();
						login.setObjectId(objectId);
						break;

					case 5:
						String userId = sh.getRow(i).getCell(j).getStringCellValue();
						login.setUserId(userId);
						break;

					case 6:
						String affiliateId = sh.getRow(i).getCell(j).getStringCellValue();
						login.setAffiliateId(affiliateId);
						break;
		
					case 7:
						String pref = sh.getRow(i).getCell(j).getStringCellValue();
						login.setPreferneces(pref);
						break;
						
					default:
						logger.error("Incorrect case received for setPostValidateLoginData. Case :: " + j);
						break;
				}
			}

			postValidateLoginData.add(new Object[] { login });
		}
	}

	/**
	 * @description provide negative data for PostValidateLoginAPI
	 * @return
	 */
	@DataProvider(name = "getPostValidateLoginNegativeData")
	public static synchronized Iterator<Object[]> getPostValidateLoginNegativeData() {

		if (postValidateLoginNegativeData.size() == 0) {
			setPostValidateLoginNegativeData();
		}
		logger.warn("Total Records Found for getPostValidateLoginNegativeData :: " + postValidateLoginNegativeData.size());
		return postValidateLoginNegativeData.iterator();
	}

	/**
	 * @description sets excel values for PostValidateLogin
	 */
	private static void setPostValidateLoginNegativeData() {

		XSSFSheet	sh		= getSheet(FilePathConstants.LOGIN_TESTDATA_FILE_PATH,
		        StringConstants.POST_VALIDATE_LOGIN_NEGATIVE_SCENARIO_SHEET);

		int			rowNum	= sh.getPhysicalNumberOfRows();

		for (int i = 1; i < rowNum; i++) {

			LoginBO	login	= new LoginBO();

			int		colNum	= sh.getRow(0).getLastCellNum();

			for (int j = 0; j < colNum; j++) {

				switch (j) {

					case 0:
						String emailId = null;
						try {
							emailId = sh.getRow(i).getCell(j).getStringCellValue();
							login.setEmailId(emailId);
						} catch (NullPointerException e) {
							login.setEmailId("");
						}

						break;

					case 1:
						String password = null;
						try {
							password = sh.getRow(i).getCell(j).getStringCellValue();
							login.setPassword(password);
						} catch (NullPointerException e) {
							login.setPassword("");
						}

						break;

					case 2:
						String userType = null;
						try {
							userType = sh.getRow(i).getCell(j).getStringCellValue();
							login.setUserType(userType);
						} catch (NullPointerException e) {
							login.setUserType("");
						}

						break;

					case 3:
						String isFacebookUser = sh.getRow(i).getCell(j).getStringCellValue();
						login.setIsFacebookUser(isFacebookUser);
						break;

					case 4:
						String objectId = null;
						try {
							objectId = sh.getRow(i).getCell(j).getStringCellValue();
							login.setObjectId(objectId);
						} catch (NullPointerException e) {
							login.setObjectId("");
						}

						break;

					default:
						logger.error("Incorrect case received for setPostValidateLoginNegativeData. Case :: " + j);
						break;
				}
			}

			postValidateLoginNegativeData.add(new Object[] { login });
		}
	}

	/**
	 * @description provide data for PostEpisodeGuide
	 * @return
	 */
	@DataProvider(name = "getPostEpisodeGuideNegativeData")
	public static synchronized Iterator<Object[]> getPostEpisodeGuideNegativeData() {

		if (postEpisodeGuideNegativeData.size() == 0) {
			setPostEpisodeGuideNegativeData();
		}
		logger.warn("Total Records Found for getPostEpisodeGuideNegativeData :: " + postEpisodeGuideNegativeData.size());
		return postEpisodeGuideNegativeData.iterator();
	}

	/**
	 * @description sets excel values for PostEpisodeGuideNegativeData
	 */
	private static void setPostEpisodeGuideNegativeData() {

		XSSFSheet	sh		= getSheet(FilePathConstants.PROGRAM_TESTDATA_FILE_PATH,
		        StringConstants.POST_EPISODE_GUIDE_NEGATIVE_SCENARIO_SHEET);

		int			rowNum	= sh.getPhysicalNumberOfRows();

		for (int i = 1; i < rowNum; i++) {

			ProgramBO	program	= new ProgramBO();

			int			colNum	= sh.getRow(0).getLastCellNum();

			for (int j = 0; j < colNum; j++) {

				switch (j) {

					case 0:
						String programSeriesId = sh.getRow(i).getCell(j).getStringCellValue();
						program.setProgramSeriesId(programSeriesId);
						break;

					case 1:
						String headendId = sh.getRow(i).getCell(j).getStringCellValue();
						program.setHeadendId(headendId);
						break;

					case 2:
						String countryCode = sh.getRow(i).getCell(j).getStringCellValue();
						program.setCountryCode(countryCode);
						break;

					case 3:
						String postalCode = sh.getRow(i).getCell(j).getStringCellValue();
						program.setPostalCode(postalCode);
						break;

					case 4:
						String device = sh.getRow(i).getCell(j).getStringCellValue();
						program.setDevice(device);
						break;

					case 5:
						String affiliateId = sh.getRow(i).getCell(j).getStringCellValue();
						program.setAffiliateId(affiliateId);
						break;

					default:
						logger.error("Incorrect case received for setPostEpisodeGuideNegativeData. Case :: " + j);
						break;
				}
			}

			postEpisodeGuideNegativeData.add(new Object[] { program });
		}
	}

	/**
	 * @description provide data for PostalCodeProviders
	 * @return
	 */
	@DataProvider(name = "getPostalCodeProvidersNegativeData")
	public static synchronized Iterator<Object[]> getPostalCodeProvidersNegativeData() {

		if (getPostalCodeProvidersNegativeData.size() == 0) {
			setPostalCodeProvidersNegativeData();
		}
		logger.warn("Total Records Found for getPostalCodeProvidersNegativeData :: " + getPostalCodeProvidersNegativeData.size());
		return getPostalCodeProvidersNegativeData.iterator();
	}

	/**
	 * @description sets excel values for PostalCodeProvidersNegativeData
	 */
	private static void setPostalCodeProvidersNegativeData() {

		XSSFSheet	sh		= getSheet(FilePathConstants.PROVIDERS_TESTDATA_FILE_PATH,
		        StringConstants.POSTAL_CODE_PROVIDERS_NEGATIVE_SCENARIO_SHEET);

		int			rowNum	= sh.getPhysicalNumberOfRows();

		for (int i = 1; i < rowNum; i++) {

			ProvidersBO	providers	= new ProvidersBO();

			int			colNum		= sh.getRow(0).getLastCellNum();

			for (int j = 0; j < colNum; j++) {

				switch (j) {

					case 0:
						String countryCode = null;

						try {
							countryCode = sh.getRow(i).getCell(j).getStringCellValue();
							providers.setCountry(countryCode);
						} catch (NullPointerException e) {
							providers.setCountry("");
						}
						break;

					case 1:
						String postalCode = null;

						try {
							postalCode = sh.getRow(i).getCell(j).getStringCellValue();
							providers.setPostalCode(postalCode);
						} catch (NullPointerException e) {
							providers.setPostalCode("");
						}
						break;

					case 2:
						String expectedStatusCode = sh.getRow(i).getCell(j).getStringCellValue();
						providers.setExpectedStatusCode(Integer.parseInt(expectedStatusCode));
						break;

					default:
						logger.error("Incorrect case received for setPostalCodeProvidersNegativeData. Case :: " + j);
						break;
				}
			}

			getPostalCodeProvidersNegativeData.add(new Object[] { providers });
		}
	}

	/**
	 * @description provide data for PostScheduleByLineupId
	 * @return
	 */
	@DataProvider(name = "getPostScheduleByLineupIdNegativeData")
	public static synchronized Iterator<Object[]> getPostScheduleByLineupIdNegativeData() {

		if (postScheduleByLineupIdNegativeData.size() == 0) {
			setPostScheduleByLineupIdNegativeData();
		}
		logger.warn("Total Records Found for getPostScheduleByLineupIdNegativeData :: " + postScheduleByLineupIdNegativeData.size());
		return postScheduleByLineupIdNegativeData.iterator();
	}

	/**
	 * @description sets excel values for PostScheduleByLineupIdNegativeData
	 */
	private static void setPostScheduleByLineupIdNegativeData() {

		XSSFSheet	sh		= getSheet(FilePathConstants.SCHEDULE_TESTDATA_FILE_PATH,
		        StringConstants.POST_SCHEDULE_BY_LINEUP_ID_NEGATIVE_SCNARIO_SHEET);

		int			rowNum	= sh.getPhysicalNumberOfRows();

		for (int i = 1; i < rowNum; i++) {

			ScheduleBO	schedule	= new ScheduleBO();

			int			colNum		= sh.getRow(0).getLastCellNum();

			for (int j = 0; j < colNum; j++) {

				switch (j) {

					case 0:
						String headendId = sh.getRow(i).getCell(j).getStringCellValue();
						schedule.setHeadendId(headendId);
						break;

					case 1:
						String timeSpan = sh.getRow(i).getCell(j).getStringCellValue();
						schedule.setTimeSpan(timeSpan);
						break;

					case 2:
						String postalCode = sh.getRow(i).getCell(j).getStringCellValue();
						schedule.setPostalCode(postalCode);
						break;

					case 3:
						String countryCode = sh.getRow(i).getCell(j).getStringCellValue();
						schedule.setCountryName(countryCode);
						break;

					case 4:
						String device = sh.getRow(i).getCell(j).getStringCellValue();
						schedule.setDeviceType(device);
						break;

					case 5:
						String includeChannels = sh.getRow(i).getCell(j).getStringCellValue();
						schedule.setIncludeChannels(includeChannels);
						break;

					case 6:
						String affiliateId = sh.getRow(i).getCell(j).getStringCellValue();
						schedule.setAffiliateTag(affiliateId);
						break;

					default:
						logger.error("Incorrect case received for setPostScheduleByLineupIdNegativeData. Case :: " + j);
						break;
				}
			}

			postScheduleByLineupIdNegativeData.add(new Object[] { schedule });
		}
	}

	/**
	 * @description provide data for getSimilarShowsAPI
	 * @return
	 */
	@DataProvider(name = "getSimilarShowsData")
	public static synchronized Iterator<Object[]> getSimilarShowsData() {

		if (similarShowsData.size() == 0) {
			setSimilarShowsData();
		}
		logger.warn("Total Records Found for getSimilarShowsData :: " + similarShowsData.size());
		return similarShowsData.iterator();
	}

	/**
	 * @description sets excel values for SimilarShowsData
	 */
	private static void setSimilarShowsData() {

		XSSFSheet	sh		= getSheet(FilePathConstants.SIMILAR_SHOWS_TESTDATA_FILE_PATH, StringConstants.SIMILAR_SHOWS_SHEET);

		int			rowNum	= sh.getPhysicalNumberOfRows();

		for (int i = 1; i < rowNum; i++) {

			SimilarShowsBO	similarShows	= new SimilarShowsBO();

			int				colNum			= sh.getRow(0).getLastCellNum();

			for (int j = 0; j < colNum; j++) {

				switch (j) {

					case 0:
						String tmsId = sh.getRow(i).getCell(j).getStringCellValue();
						similarShows.setTmsId(tmsId);
						break;

					default:
						logger.error("Incorrect case received for setSimilarShowsData. Case :: " + j);
						break;
				}
			}

			similarShowsData.add(new Object[] { similarShows });
		}
	}

	/**
	 * @description to get requested sheet object from workbook
	 * @param fileName
	 * @param sheetName
	 * @return
	 */
	private static XSSFSheet getSheet(String fileName, String sheetName) {
		XSSFWorkbook wb = null;

		// to create workbook instance
		try {
			wb = new XSSFWorkbook(fileName);
		} catch (IOException e) {
			logger.error("Exception Occurred while reading TestData file :: " + fileName);
		}

		// to fetch sheet from workboos
		XSSFSheet sh = wb.getSheet(sheetName);

		try {
			wb.close();
		} catch (IOException e) {
			logger.error("Exception Occurred while closing TestData file :: " + fileName);
		}

		return sh;
	}

	public static void main(String[] args) {
		getFavoriteChannelData();
	}

}

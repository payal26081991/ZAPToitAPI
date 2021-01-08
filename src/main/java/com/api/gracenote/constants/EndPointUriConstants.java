/**
 * 
 */
package com.api.gracenote.constants;

import com.api.gracenote.coreResources.fileReaders.PropertyReader;

/**
 * @author kunal.ashar
 *
 */
public interface EndPointUriConstants {

	String	SERVER_IP									= PropertyReader.getConfigProperty(ConfigPropertiesConstants.SERVER_IP).trim();
	String	PROD_SERVER_IP								= PropertyReader.getConfigProperty(ConfigPropertiesConstants.PROD_SERVER).trim();
	
	// Affiliate Properties Uri
	String	GET_AFFILIATE_URL							= "http://" + SERVER_IP + "/gapzap_Webapi/api/affiliates/getaffiliatesprop/";

	// Channels Uri
	String	GET_CHANNEL_INFO							= "http://" + SERVER_IP + "/gapzap_Webapi/api/Channels/GetChannelInfo";

	// Favorite Uri
	String	GET_USER_FAVORITES							= "http://" + SERVER_IP + "/gapzap_Webapi/api/favorites/UserFavorites/";

	String	GET_FAVORITE_CHANNEL						= "http://" + SERVER_IP + "/gapzap_Webapi/api/favorites/FavoriteChannel/";

	String	GET_SAVE_USER_PROPERTIES					= "http://" + SERVER_IP + "/gapzap_Webapi/api/Favorites/SaveUserProperties/";

	// Login Uri
	String	POST_VALIDATE_LOGIN							= "http://" + SERVER_IP + "/gapzap_Webapi/api/login/PostValidateLogin/";

	// Program Uri
	String	POST_EPISODE_GUIDE							= "http://" + SERVER_IP + "/gapzap_Webapi/api/program/PostEpisodeGuide/";

	String	POST_PROGRAM_OVERVIEW_UPCOMING_DETAILS		= "http://" + SERVER_IP
	        + "/gapzap_Webapi/api/program/PostProgramOverviewUpcomingDetails/";

	// Providers Uri
	String	GET_POSTAL_CODES_PROVIDERS					= "http://" + SERVER_IP + "/gapzap_Webapi/api/Providers/getPostalCodeProviders/";

	String	GET_DST_OFF_SET_FOR_POSTAL_CODE_BY_COUNTRY	= "http://" + SERVER_IP
	        + "/gapzap_Webapi/api/Providers/GetDstOffsetForPostalByCountry/";

	// Schedule Uri
	String	POST_SCHEDULE_BY_LINEUP_ID					= "http://" + SERVER_IP + "/gapzap_Webapi/api/schedules/PostScheduleByLineupId/";

	// Search Uri
	String	GET_SEARCH_AUTOCOMPLETE						= "http://" + SERVER_IP + "/gapzap_Webapi/api/Search/autocomplete?";

	// Similar Shows Uri
	String	GET_SIMILAR_SHOWS							= "http://" + SERVER_IP + "/gapzap_Webapi/api/SimilarShows/";

	// Timezone Headend Uri
	String	GET_TIMEZONE								= "http://" + SERVER_IP
	        + "/gapzap_Webapi/api/timezoneheadends/GetDefaultlineupOffSet/";

	// User Uri
	String	POST_USER									= "http://" + SERVER_IP + "/gapzap_Webapi/api/users/postuser/";
	
	// for setting user preferences
	String POST_USER_PREFERENCES						= "http://" + SERVER_IP + "/gapzap_webapi/api/Favorites/PostUpdatePreferences";
	
	// for cache warming job 
	String	POST_SCHEDULE_BY_LINEUP_ID_CACHE_WARMING    = "http://" + PROD_SERVER_IP + "/gapzap_Webapi/api/schedules/PostScheduleByLineupId/";

	

}

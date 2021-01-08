/**
 * 
 */
package com.api.gracenote.constants;

/**
 * @author kunal.ashar
 *
 */
public interface DBQueriesConstants {

	//*String	AFFILIATE_PROPERTIES_QUERY 						= "SELECT DISTINCT a.affl_tag FROM affiliates a JOIN affiliates_property_mapping p ON a.affl_id =p.affl_id WHERE product_id IN (1,2,3) AND affl_isactive=1";
	String	AFFILIATE_PROPERTIES_QUERY                      = "SELECT DISTINCT affl_tag FROM affiliates WHERE product_id IN (1,2,3) AND affl_isactive=1";
	
	String	PROGRAM_SHOWCARD_OVERVIEW_QUERY					= "SELECT DISTINCT p.ProgramSeriesID FROM program p JOIN (SELECT DISTINCT s.TMSID FROM schedules s WHERE (s.StartTime >= DATE_SUB(UTC_TIMESTAMP(), INTERVAL 2 DAY) AND s.StartTime < TIMESTAMP(DATE_ADD(UTC_DATE(), INTERVAL 13 DAY)))) AS psc ON p.TMSID = psc.TMSID";

	//*String	POST_SCHEDULE_BY_LINEUP_ID_QUERY			   = "SELECT DISTINCT he.HeadendId,IFNULL(hl.Device,'-') AS DeviceType,he.Country FROM headends he JOIN headendlineups hl ON he.headendid=hl.headendid AND he.Country=hl.Country WHERE (EffectiveDate <NOW() OR EffectiveDate IS NULL)AND(expirydate IS NULL OR EffectiveDate > NOW())";
	String	POST_SCHEDULE_BY_LINEUP_ID_QUERY                = "SELECT DISTINCT he.HeadendId,IFNULL(hl.Device,'-') AS DeviceType,he.Country FROM headends he JOIN headendlineups hl ON he.headendid=hl.headendid AND he.Country=hl.Country WHERE (EffectiveDate <NOW() OR EffectiveDate IS NULL)AND(expirydate IS NULL OR EffectiveDate > NOW()) AND LENGTH(he.HeadendId) >5";

	String	POST_SCHEDULE_BY_LINEUP_ID_POSTAL_CODE_QUERY	= "CALL POST_SCHEDULE_BY_LINEUP_ID_QA()";

	String	GET_POSTAL_CODE_PROVIDERS_QUERY					= "(SELECT DISTINCT hpc.PostalCode,h.Country FROM headendspostalcodes hpc JOIN headends h ON hpc.HeadendId=h.HeadendId AND hpc.Country=h.Country) UNION SELECT DISTINCT PostalCode,Country FROM otalineups";

	String	GET_ADULT_CONTENT_SEARCH_QUERY					= "CALL USP_SolrAutoComplete_QA()";

	//*String	POST_SCHEDULE_BY_LINEUP_ID_OTA_LINEUPS_QUERY	= "select distinct PostalCode,Country from otalineups";
	String	POST_SCHEDULE_BY_LINEUP_ID_OTA_LINEUPS_QUERY    = "select distinct PostalCode,Country from otalineups WHERE country IN ('USA','can')";
	
	String	POST_SCHEDULE_OTA_SINGLE_STATION_QUERY			= "SELECT * FROM (SELECT @row_number:=CASE WHEN @customer_no = ota.progsvcid THEN @row_number + 1 ELSE 1 END AS num, @customer_no:=ota.progsvcid AS PrgSvcID, ota.postalcode,ota.country FROM otaLineups  ota ,(SELECT @customer_no:=0,@row_number:=0) AS t ORDER BY ota.progsvcid) AS dd WHERE num=1";

	String	POST_SCHEDULE_SINGLE_STATION_AFFILIATES_QUERY	= "SELECT affl_tag FROM affiliates af WHERE af.product_id=3 AND affl_isactive=1";

	//*String	POST_SCHEDULE_BY_LINEUP_ID_SINGLE_STATION_QUERY	   = "SELECT Prgsvcid,he.HeadendId,abc.Device,he.Country FROM (SELECT @row_number:=CASE WHEN @progsvcid_no = progsvcid THEN @row_number + 1 ELSE 1 END AS num, hl.progsvcid, @progsvcid_no, @progsvcid_no:=hl.progsvcid AS PrgsvcId,    hl.HeadendId, IFNULL(hl.Device,'-') AS Device, hl.Country     FROM headendlineups hl,(SELECT @progsvcid_no:=0,@row_number:=0) AS t    WHERE hl.progsvcid NOT IN (SELECT DISTINCT progsvcid FROM otalineups) AND hl.ExpiryDate IS NULL OR hl.ExpiryDate>CURDATE()   ORDER BY hl.progsvcid)abc JOIN headends he ON he.headendid=abc.headendid AND he.Country=abc.Country WHERE abc.num=1";
	String	POST_SCHEDULE_BY_LINEUP_ID_SINGLE_STATION_QUERY	= "SELECT Prgsvcid,he.HeadendId,abc.Device,he.Country FROM (SELECT @row_number:=CASE WHEN @progsvcid_no = progsvcid THEN @row_number + 1 ELSE 1 END AS num, hl.progsvcid, @progsvcid_no,@progsvcid_no:=hl.progsvcid AS PrgsvcId,hl.HeadendId,IFNULL(hl.Device,'-') AS Device, hl.Country FROM headendlineups hl,(SELECT @progsvcid_no:=0,@row_number:=0) AS t WHERE hl.progsvcid NOT IN (SELECT DISTINCT progsvcid FROM otalineups) AND hl.country IN ('usa','can') AND hl.ExpiryDate IS NULL OR hl.ExpiryDate>CURDATE() ORDER BY hl.progsvcid)abc JOIN headends he ON he.headendid=abc.headendid AND he.Country=abc.Country WHERE abc.num=1";
	
	String	GET_DST_OFFSET_FOR_POSTAL_CODE_BY_COUNTRY_QUERY	= "SELECT DISTINCT A.countrycode, A.DSTStart,A.DSTEnd,B.utcoffset,IFNULL(B.PrimeTime,20) AS PrimeTime,B.postalcode FROM countrydstmaster AS A INNER JOIN countrytimezonedata AS B ON A.countrycode =B.countrycode WHERE DSTStart LIKE CONCAT(YEAR(CURDATE()),'%')" ; // updated query given by sheldon
	//*String	GET_DST_OFFSET_FOR_POSTAL_CODE_BY_COUNTRY_QUERY	   = "SELECT DISTINCT A.countrycode, A.DSTStart,A.DSTEnd,B.utcoffset,B.PrimeTime,B.postalcode FROM countrydstmaster AS A INNER JOIN countrytimezonedata AS B ON A.countrycode =B.countrycode WHERE DSTStart LIKE CONCAT(YEAR(CURDATE()),'%')";

	String	GET_TIMEZONE_DATA_QUERY							= "SELECT DISTINCT d.`name`,d.headendId, d.country, c.PrimeTime, c.utcoffset FROM defaultlineups d JOIN countrytimezonedata c ON c.postalcode = d.postalCode";

	String GET_HEADEND_DATA_FOR_CACHE_WARMING               = "SELECT DISTINCT hh.headendid,IFNULL(device,'-')AS device,country ,ct FROM `headendlineups` hh JOIN ( SELECT headendid,COUNT(progsvcid) AS ct FROM `headendlineups` WHERE country IN ('usa','can')GROUP BY headendid HAVING COUNT(1) >300 ) dd ON hh.headendid=dd.headendid ORDER BY ct DESC";

	String ADULT_NON_ENGLISH_PROGRAM_LIST_QUERY             = "CALL Adult_Content_Non_English_Titles()";
}

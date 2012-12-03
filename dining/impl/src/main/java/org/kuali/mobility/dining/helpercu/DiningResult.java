package org.kuali.mobility.dining.helpercu ;

import javax.persistence.NamedQuery;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import java.util.Date;

/**
 * Holds the elements we need from result set, from the query 
 * to the Cornell Dining database.
 */
/* ------------------------------------------------------------------------- */
@Entity
@Table(name = "menudetailweb", catalog = "dining")
@NamedQuery(name="lookupMealsForToday", query="Select meal, course, formal_name, eating_well_flag from DiningResult where service_unit = :service_unit and eventdate = :eventdate")
public class DiningResult implements Comparable <DiningResult> {

	//String sSql = "select meal, course, formal_name, eating_well_flag from dining.menudetailweb where service_unit = ? and eventdate = ?" ;


	@Column(name = "meal")
	private String meal = "" ;
	
	@Column(name = "course")
	private String station = "" ;
	
	@Column(name = "formal_name")
	private String entree = "" ;
	
	@Column(name = "eating_well_flag")
	private String eatingWellFlag = "" ;
	
	@Column(name= "service_unit")
	private String serviceUnit = "";
	
	
	private Date eventdate;

	private String mealRanking[] = {"breakfast", "brunch", "lunch", "dinner" } ;
	
	/**
	 * So we can sort the "meals" according to the mealRanking String 
	 * array above.
	 */
	public int compareTo (DiningResult dr) {

		int iReturn = 1 ;
		int iRankLocal = getMealRanking(meal) ;
		int iRankCompare = getMealRanking(dr.getMeal()) ;
		if (iRankLocal == iRankCompare)
			iReturn = 0 ;
		else if (iRankLocal < iRankCompare)
			iReturn = -1 ;

		return (iReturn) ;
	}

	/* ------------------------------------------------------------------------- */
	private int getMealRanking (String sMeal) {

		int iMealRankSize = mealRanking.length ;

		int iRank = iMealRankSize + 1 ;

		sMeal = sMeal.toLowerCase() ;
		for (int i=0; i<mealRanking.length; i++)
		{
			if (sMeal.contains(mealRanking[i]))
			{
				iRank = i ;
				break ;
			}
		}

		return (iRank) ;
	}

	/* ------------------------------------------------------------------------- */
	public String getMeal() {

		return meal;
	}

	/* ------------------------------------------------------------------------- */
	public void setMeal(String meal) {

		this.meal = noNullAndTrimAndUgh(meal);
	}

	/* ------------------------------------------------------------------------- */
	public String getStation() {

		return station;
	}

	/* ------------------------------------------------------------------------- */
	public void setStation(String station) {

		this.station = noNullAndTrimAndUgh(station);
	}

	/* ------------------------------------------------------------------------- */
	public String getEntree() {

		return entree;
	}

	/* ------------------------------------------------------------------------- */
	public void setEntree(String entree) {

		this.entree = noNullAndTrimAndUgh(entree);
	}

	/* ------------------------------------------------------------------------- */
	public String getEatingWellFlag() {

		return eatingWellFlag;
	}

	/* ------------------------------------------------------------------------- */
	public void setEatingWellFlag(String eatingWellFlag) {

		this.eatingWellFlag = noNullAndTrimAndUgh(eatingWellFlag);
	}
	
	public String getServiceUnit() {
		return serviceUnit;
	}
	
	public void setServiceUnit(String serviceUnit) {
		this.serviceUnit = serviceUnit;
	}
	
	public Date getEventdate() {
		return eventdate;
	}
	
	public void setEventdate(Date eventdate) {
		this.eventdate = eventdate;
	}

	/* ------------------------------------------------------------------------- */
	private String noNullAndTrimAndUgh (String sValue) {

		if (sValue == null)
			sValue = "" ;

		if (sValue.length() > 0)
			sValue = sValue.trim();

		if (sValue.contains("&nbsp;"))
			sValue = sValue.replace("&nbsp;", "") ;

		return (sValue) ;
	}
	
	

}

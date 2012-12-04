package org.kuali.mobility.dining.helpercu ;

/**
 * Holds the elements we need from result set, from the query 
 * to the Cornell Dining database.
 */
public class DiningResult implements Comparable <DiningResult> {

	private String meal = "" ;
	private String station = "" ;
	private String entree = "" ;
	private String eatingWellFlag = "" ;
	private String serviceUnit = "";
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

	public String getMeal() {

		return meal;
	}

	public void setMeal(String meal) {

		this.meal = noNullAndTrimAndUgh(meal);
	}

	public String getStation() {

		return station;
	}

	public void setStation(String station) {

		this.station = noNullAndTrimAndUgh(station);
	}

	public String getEntree() {

		return entree;
	}

	public void setEntree(String entree) {

		this.entree = noNullAndTrimAndUgh(entree);
	}

	public String getEatingWellFlag() {

		return eatingWellFlag;
	}

	public void setEatingWellFlag(String eatingWellFlag) {

		this.eatingWellFlag = noNullAndTrimAndUgh(eatingWellFlag);
	}
	
	public String getServiceUnit() {
		return serviceUnit;
	}
	
	public void setServiceUnit(String serviceUnit) {
		this.serviceUnit = serviceUnit;
	}
	
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

package org.kuali.mobility.dining.helpercu;

import java.util.ArrayList ;

/* ------------------------------------------------------------------------- */
public class Meals {

ArrayList<Meal> meal ;

/* ------------------------------------------------------------------------- */
public void appendMeal (DiningResult dr) {

String sMeal = dr.getMeal() ;
String sStation = dr.getStation() ;
String sEntree = dr.getEntree() ;

if (meal == null)
	meal = new ArrayList<Meal>() ;

boolean bFound = false ;
for (Meal ml : meal)
	{
	String sName = ml.getName() ;
	if (sName.equalsIgnoreCase(sMeal))
		{
		ml.appendStationEntreeCombo(sStation, sEntree) ;
		bFound = true ;
		}
	
	}

if (!bFound)
	{
	Meal mlNew = new Meal() ;
	mlNew.setName(sMeal) ;
	mlNew.appendStationEntreeCombo(sStation, sEntree) ;
	meal.add(mlNew) ;
	}
}

/* ------------------------------------------------------------------------- */
public ArrayList<Meal> getMeal() {

return meal;
}

/* ------------------------------------------------------------------------- */
public void setMeal(ArrayList<Meal> meals) {

this.meal = meals;
}

}

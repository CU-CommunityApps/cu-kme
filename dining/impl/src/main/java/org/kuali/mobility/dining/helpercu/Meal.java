package org.kuali.mobility.dining.helpercu;

import java.util.ArrayList ;

/* ------------------------------------------------------------------------- */
public class Meal {

String name ;
ArrayList<Station> categories ;

/* ------------------------------------------------------------------------- */
public ArrayList<Station> getCategories() {

return categories;
}

/* ------------------------------------------------------------------------- */
public void setCategories(ArrayList<Station> stations) {

this.categories = stations;
}

/* ------------------------------------------------------------------------- */
public String getName() {

return name;
}

/* ------------------------------------------------------------------------- */
public void setName(String name) {

this.name = name;
}

/* ------------------------------------------------------------------------- */
public void appendStationEntreeCombo (String sStation, String sEntree, 
		String sEatingWellFlag) {

if (categories == null)
	categories = new ArrayList<Station>() ;

Entree yummyFood = new Entree() ;
yummyFood.setTitle(sEntree) ;
if (sEatingWellFlag.length() > 0)
	yummyFood.designateHealthyEating() ;

boolean bFound = false ;
	for (Station st : categories)
		{
		String sName = st.getTitle() ;
		if (sName.equalsIgnoreCase(sStation))
			{
			st.appendEntree(yummyFood) ;
			bFound = true ;
			}
		}

if (!bFound)
	{
	Station stNew = new Station() ;
	stNew.setTitle(sStation) ;
	stNew.appendEntree(yummyFood) ;
	categories.add(stNew) ;
	}
}

}

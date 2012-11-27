package org.kuali.mobility.dining.helpercu ;

import java.sql.PreparedStatement ;
import java.sql.ResultSet;
import java.util.ArrayList ;
import java.util.Date ;
import java.text.SimpleDateFormat ;


/* ------------------------------------------------------------------------- */
public class DiningDb extends DatabaseConnection {

/**
 * This "one hit open/close" is just to get us going, and will be replaced with a
 * JPA query.
 * 
 * @param sDiningHall
 * @return An ArrayList of DiningResult objects
 * @throws Exception
 */
/* ------------------------------------------------------------------------- */
public ArrayList<DiningResult> lookupMealsForToday (String sDiningHall) throws Exception {

Date dt = new Date() ;
SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
String sDate = df.format(dt) ;

String sSql = "select meal, course, formal_name, eating_well_flag from dining.menudetailweb where service_unit = ? and eventdate = ?" ;

ArrayList<DiningResult> alDr = new ArrayList<DiningResult>() ;

PreparedStatement ps = null ;
ResultSet rs = null ;
try {
	connect() ;
	ps = con.prepareStatement(sSql) ;
	ps.setString(1, sDiningHall) ;
	ps.setString(2, sDate) ;
	rs = ps.executeQuery();
	while (rs.next())
		{
		DiningResult dr = new DiningResult() ;
		dr.setMeal(rs.getString(1)) ;
		dr.setStation(rs.getString(2)) ;
		dr.setEntree(rs.getString(3)) ;
		dr.setEatingWellFlag(rs.getString(4)); 
		alDr.add(dr) ;
		}
	}
finally
	{
	closeAllForSqlQuery(ps, rs) ;
	}

return (alDr) ;
}

}

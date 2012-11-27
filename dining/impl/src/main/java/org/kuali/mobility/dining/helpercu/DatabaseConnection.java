package org.kuali.mobility.dining.helpercu ;

import java.sql.Connection ;
import java.sql.DriverManager ;
import java.sql.PreparedStatement ;
import java.sql.ResultSet ;

/**
 * Am using this as a quick way to get a version of Dining going with Cornell 
 * data.  This will get replaced with a JPA query.
 */
/* -------------------------------------------------------------------------- */
abstract public class DatabaseConnection {

protected String url ;
protected String adminId ;
protected String adminPwd ;
protected Connection con = null ;

/* ------------------------------------------------------------------------- */
public void connect () throws Exception {

if (con == null)
	{
	Class.forName("oracle.jdbc.OracleDriver") ;
	con = DriverManager.getConnection(url, adminId, adminPwd) ;
	}
}

/* ------------------------------------------------------------------------- */
public void closeAllForSqlQuery (PreparedStatement ps, ResultSet rs) throws Exception {

try {
	if (ps != null)
		ps.close() ;
	}
finally 
	{
	try {
		if (rs != null)
			rs.close() ;
		}
	finally 	
		{
		close() ;
		}
	}
}

/* ------------------------------------------------------------------------- */
public void close () throws Exception {

if (con != null)
	{
	try {
		con.close() ;
		}
	finally 
		{
		con = null ;
		}
	}
}

/* ------------------------------------------------------------------------- */
public void setUrl (String sUrl) {

url = sUrl ;
}

/* ------------------------------------------------------------------------- */
public String getUrl () {

return (url) ;
}

/* ------------------------------------------------------------------------- */
public void setAdminId (String sId) {

adminId = sId ;
}

/* ------------------------------------------------------------------------- */
public String getAdminId () {

return (adminId) ;
}

/* ------------------------------------------------------------------------- */
public void setAdminPwd (String sPwd) {

adminPwd = sPwd ;
}

/* ------------------------------------------------------------------------- */
public String getAdminPwd () {

return (adminPwd) ;
}

}

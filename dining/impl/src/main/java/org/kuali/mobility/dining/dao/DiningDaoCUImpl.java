/**
 * Copyright 2011 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.mobility.dining.dao;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger ;
import org.kuali.mobility.dining.entity.Place ;
import org.kuali.mobility.dining.helpercu.DiningResult;
import org.kuali.mobility.dining.helpercu.Meals;
import org.kuali.mobility.util.mapper.DataMapper;

import flexjson.JSONSerializer ;

/**
 * We are kind of half-stepping it here, for now. The Cornell "place list" info is 
 * hard coded in the XML file, as we do not have a datasource for this 
 * handy right now. But this will be enough to get us launched.
 * 
 * The Cornell meal and menu data is live, and comes from an Oracle table.
 *
 */
public class DiningDaoCUImpl implements DiningDao {

	private static final Logger LOG = Logger.getLogger( DiningDaoCUImpl.class );

	private DataMapper mapper;
	private List<Place> placeList;

	private String placeSourceUrl;
	private String placeMappingFile;
	private DataSource diningDataSource;

	/*
	 * (non-Javadoc)
	 * @see org.kuali.mobility.dining.dao.DiningDao#getMenusJson(java.lang.String, java.lang.String)
	 * 
	 * We are not using the "location" parameter, as I did not want to change the interface.
	 */
	public String getMenusJson (final String name, String location) {

		String sSql = "select meal, course, formal_name, eating_well_flag from dining.menudetailweb where service_unit = ? and eventdate = ?" ;

		Date dt = new Date() ;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String sDate = df.format(dt) ;

		ArrayList<DiningResult> alDr = new ArrayList<DiningResult>() ;

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = diningDataSource.getConnection();
			stmt = conn.prepareStatement(sSql);
			stmt.setString(1, name) ;
			stmt.setString(2, sDate) ;
			rs = stmt.executeQuery();

			while(rs.next()) {
				DiningResult dr = new DiningResult() ;
				dr.setMeal(rs.getString(1)) ;
				dr.setStation(rs.getString(2)) ;
				dr.setEntree(rs.getString(3)) ;
				dr.setEatingWellFlag(rs.getString(4));
			
				alDr.add(dr) ;				
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try { rs.close(); } catch(Exception e) { }
			try { stmt.close(); } catch(Exception e) { }
			try { conn.close(); } catch(Exception e) { }
		}
				
		Collections.sort(alDr) ;	// this will make the meals display nicely
		Meals mls = new Meals() ;		
		for (DiningResult dr :alDr)
			{
			mls.appendMeal(dr) ;			
			}

		String sFlexJson = new JSONSerializer().exclude("*.class").deepSerialize(mls) ;
		return (sFlexJson) ;
	}

	private void initPlaceList () {

		if (placeList==null)
			placeList = new ArrayList<Place>();

		try {
			placeList = mapper.mapData(placeList, new URL(getPlaceSourceUrl()), getPlaceMappingFile());
		}
		catch (Exception e)
		{
			LOG.error(e.getMessage()) ;
		}
	}

	public String getPlaceSourceUrl() {

		return placeSourceUrl;
	}

	public void setPlaceSourceUrl(String placeSourceUrl) {

		this.placeSourceUrl = placeSourceUrl;
	}

	public String getPlaceMappingFile() {

		return placeMappingFile;
	}

	public void setPlaceMappingFile(String placeMappingFile) {

		this.placeMappingFile = placeMappingFile;
	}

	public List<Place> getPlaceList() {

		if (placeList==null || placeList.isEmpty()) 
			initPlaceList();

		return placeList;
	}

	public void setMapper(DataMapper mapper) {

		this.mapper = mapper;
	}

	public DataSource getDiningDataSource() {
	
		return this.diningDataSource;
	}

	public void setDiningDataSource(DataSource diningDataSource) {
	
		this.diningDataSource = diningDataSource;
	}
}

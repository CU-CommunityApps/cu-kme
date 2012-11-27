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

import org.apache.log4j.Logger ;
import org.kuali.mobility.dining.entity.Place ;
import org.kuali.mobility.dining.helpercu.DiningDb ;
import org.kuali.mobility.dining.helpercu.DiningResult;
import org.kuali.mobility.dining.helpercu.Meals;
import org.kuali.mobility.util.mapper.DataMapper;

import flexjson.JSONSerializer ;

/**
 * We are kind of half-stepping it here, for now. The Cornell "place list" info is 
 * hard coded in the XML file. as we do not have a datasource for this 
 * handy right now. But this will be enough to get us launched.
 * 
 * The Cornell meal and menu data is live, and comes from an Oracle table.
 *
 */
/* --------------------------------------------------------------------- */
public class DiningDaoCUImpl implements DiningDao {

private static final Logger LOG = Logger.getLogger( DiningDaoCUImpl.class );

private DataMapper mapper;
private List<Place> placeList;

private String placeSourceUrl;
private String placeMappingFile;

private String datasourceUrl ;
private String datasourceUsername ;
private String datasourcePassword ; 

/* --------------------------------------------------------------------- */
public String getMenusJson (final String name, final String location) {
	
Meals mls = new Meals() ;

/*
 * The DiningDb data access class is just to get us going.  This will be replaced with
 * a JPA query.
 */

DiningDb dDb = new DiningDb() ;
dDb.setUrl(datasourceUrl) ;
dDb.setAdminId(datasourceUsername) ;
dDb.setAdminPwd(datasourcePassword) ;
try {
	ArrayList<DiningResult> alDr = dDb.lookupMealsForToday(name) ; 
	Collections.sort(alDr) ;
	for (DiningResult dr : alDr)
		{
		mls.appendMeal(dr) ;
		}
	}
catch (Exception e)
	{
	LOG.info(e.getMessage()) ;
	}
	
String sFlexJson = new JSONSerializer().exclude("*.class").deepSerialize(mls) ;
return (sFlexJson) ;
}

/* --------------------------------------------------------------------- */
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
	
/* ------------------------------------------------------------------------- */
public String getDatasourceUrl() {

return datasourceUrl;
}

/* ------------------------------------------------------------------------- */
public void setDatasourceUrl(String datasourceUrl) {

this.datasourceUrl = datasourceUrl;
}

/* ------------------------------------------------------------------------- */
public String getDatasourceUsername() {

return datasourceUsername;
}

/* ------------------------------------------------------------------------- */
public void setDatasourceUsername(String datasourceUsername) {

this.datasourceUsername = datasourceUsername;
}

/* ------------------------------------------------------------------------- */
public String getDatasourcePassword() {

return datasourcePassword;
}

/* ------------------------------------------------------------------------- */
public void setDatasourcePassword(String datasourcePassword) {

this.datasourcePassword = datasourcePassword;
}

/* ------------------------------------------------------------------------- */

public String getPlaceSourceUrl() {

return placeSourceUrl;
}

/* ------------------------------------------------------------------------- */
public void setPlaceSourceUrl(String placeSourceUrl) {

this.placeSourceUrl = placeSourceUrl;
}

/* ------------------------------------------------------------------------- */
public String getPlaceMappingFile() {

return placeMappingFile;
}

/* ------------------------------------------------------------------------- */
public void setPlaceMappingFile(String placeMappingFile) {

this.placeMappingFile = placeMappingFile;
}

/* ------------------------------------------------------------------------- */
public List<Place> getPlaceList() {

if (placeList==null || placeList.isEmpty()) 
	initPlaceList();

return placeList;
}

/* ------------------------------------------------------------------------- */
public void setMapper(DataMapper mapper) {

this.mapper = mapper;
}

}

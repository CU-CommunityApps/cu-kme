package org.kuali.mobility.computerlabs.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.kuali.mobility.computerlabs.entity.Lab;
import org.kuali.mobility.computerlabs.entity.Location;

import org.kuali.mobility.computerlabs.helper.AllLabStatsLabs;
import org.kuali.mobility.computerlabs.helper.LabStatsLab;

import com.sun.jersey.api.client.Client ;
import com.sun.jersey.api.client.ClientResponse ;
import com.sun.jersey.api.client.WebResource ;

import flexjson.JSONDeserializer;

/**
 * Gets "Lab Stats" from the Cornell Information Technologies site, via a REST call which 
 * returns JSON.  Stuff the JSON into an array of LabStatsLab objects, using Flexjson (as that is what is used elsewhere
 * in KME), and sort it based on the name of the lab.  
 * 
 * For each LabStatsLab object, create a KME Lab object, and copy in just the few fields we need.  Take that resulting
 * array, and whip it into a single Location object.  That's just enough to display everything we need, under a single "heading",
 * which is actually a Location.name, of "Avaibility".
 */
public class ComputerLabsDaoCUImpl implements ComputerLabsDao {

	private static final Logger LOG = Logger.getLogger( ComputerLabsDaoCUImpl.class );
 	private String labStatsURL ;

 	/**
 	 * At Cornell, we do not have labs categorized by campus, so that parameter will 
 	 * go unused. 
 	 */
	public Collection<Location> findAllLabsByCampus(String campus) {

	ArrayList<Location> alLoc = new ArrayList<Location>() ;

	// Get the Lab Stats
	Client cl = Client.create();
	WebResource target = cl.resource(labStatsURL);
	ClientResponse resp = target.type("application/json").post(ClientResponse.class, "");
	int iStatus = resp.getStatus() ;
	if (iStatus == 200)
		{
		String sJsonLabStats = resp.getEntity(String.class) ;
		AllLabStatsLabs alsl = new JSONDeserializer<AllLabStatsLabs>().deserialize(sJsonLabStats, AllLabStatsLabs.class) ;
		List<LabStatsLab> lLab = alsl.getD() ;
	
		// sort now
		Collections.sort(lLab) ;
	
		// "Map" Lab Stats lab entires into what KME uses
		ArrayList<Lab> alKmeLab = mapToKmeLab(lLab) ;

		// Just use a single "Location" called "Availability".  The CIT lab stats are not 
		// grouped by a room or "location", so we'll use Location.name as a heading. 
		Location loc = new Location("Availability") ;
		loc.setLabs(alKmeLab) ;
		alLoc.add(loc) ;
		}
	else
		LOG.error(labStatsURL + ", returned: " + iStatus) ;

	return (alLoc) ;
	}

	/**
	 * @param lLsl A List of LabStatsLab objects, which were created from inhaling 
	 * (deserializing) the JSON returned from the REST call.
	 * 
	 * @return  ArrayList of KME Lab objects, with just a few needed fields filled in.
	 */
	private ArrayList<Lab> mapToKmeLab (List<LabStatsLab> lLsl) {

	ArrayList<Lab> alKmeLab = new ArrayList<Lab>() ;
	for (LabStatsLab lsl : lLsl)
		{
		String sName = lsl.getGroupName() ;
		if (sName != null)
			{
			Lab kmeLab = new Lab() ;			
			String sAvailability = lsl.getAvailableCount() + "/" + lsl.getTotalCount() ; 
			kmeLab.setLab(sName) ;
			kmeLab.setAvailability(sAvailability) ;
			alKmeLab.add(kmeLab) ;
			}
		}
	
	return (alKmeLab) ;
	}

	public String getLabStatsURL() {
	
	return labStatsURL;
	}
	
	public void setLabStatsURL(String labStatsURL) {
	
	this.labStatsURL = labStatsURL;
	}

	// These have to be here, due to the interface, but are not needed.
	public Map<String, List<String>> getLabUrls() {
	
	return null ;
	}

	public void setLabUrls(Map<String, List<String>> labUrls) {
	}

	public List<Lab> getLabs() {
	
	return null ;
	}

	public void setLabs(List<Lab> labs) {
	}

}

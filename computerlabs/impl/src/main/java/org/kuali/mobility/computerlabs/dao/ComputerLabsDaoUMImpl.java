package org.kuali.mobility.computerlabs.dao;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.kuali.mobility.computerlabs.entity.Lab;
import org.kuali.mobility.computerlabs.entity.Location;
import org.kuali.mobility.computerlabs.helper.Building;
import org.kuali.mobility.computerlabs.helper.Buildings;
import org.kuali.mobility.computerlabs.helper.LabNameSort;
import org.kuali.mobility.computerlabs.helper.Room;
import org.kuali.mobility.util.mapper.DataMapper;

import com.thoughtworks.xstream.XStream;

public class ComputerLabsDaoUMImpl implements ComputerLabsDao 
{
	private static final Logger LOG = Logger.getLogger( ComputerLabsDaoUMImpl.class );
    private String buildingsSourceURL;
	private Map<String, List<String>> labUrls;
	private Map<String, Location> locations;
	private List<Lab> labs;

	/**
	 * @return the labs
	 */
	public List<Lab> getLabs() {
		return labs;
	}

	/**
	 * @param labs the labs to set
	 */
	public void setLabs(List<Lab> labs) {
		this.labs = labs;
	}

	//@Autowired
	private DataMapper dataMapper;
	private String dataMappingUrl;
	
	/**
	 * @return the Collection<Location>
	 */
	@SuppressWarnings("unchecked")
	public Collection<Location> findAllLabsByCampus(String campus)
	{
		Map<String, Location> locations = null;
		if(campus.equals("ALL")	) //umich
		{
			 initData();
		}
		else //kme
		{
			for (String sourceUrl : labUrls.get(campus)) 
			{
				try
				{
					URL url = new URL(sourceUrl);
					LOG.debug("SourceUrl: " + sourceUrl );
					if (dataMappingUrl != null && !"".equals(dataMappingUrl.trim()))
					{
						setLabs(dataMapper.mapData(getLabs(), url, new URL(dataMappingUrl)));
					} 
					else
					{
						setLabs(dataMapper.mapData(getLabs(), url, "labMapping.xml"));
					}
				
				} 
				catch (Exception e)
				{
					LOG.error("errors", e);
				}
			}
		}
        locations = new LinkedHashMap<String,Location>();
        List<Lab> l = getLabs();
        if (l != null) {
            for (Lab lab : l)
    		{
    			Location location = null;
    			if (locations.get(lab.getBuildingCode()) != null) {
    				location = locations.get(lab.getBuildingCode());
    			} else {
    				location = new Location(lab.getBuilding());
    				locations.put(lab.getBuildingCode(), location);
    			}
    		/*	LOG.debug("location: " + location.getName() );
    			LOG.debug("lab: " + lab.getAvailability() );*/
    			location.getLabs().add(lab);
    		}
    	/*	for (Location curl : locations.values())
    		{
    			LOG.debug("Building " + curl.getName());
    		}*/
        }
		return locations.values();
	}
	
	/**
	 * Load Locations  and labs from umich webservice.
	 */
	private void initData()
	{
		XStream xstream = new XStream();
		xstream.processAnnotations(Buildings.class );	
		Buildings  buildings = null;
		try 
		{
			buildings = (Buildings) xstream.fromXML( new URL(getBuildingsSourceURL()));
	   } 
	   catch( MalformedURLException e ) 
	   {
			LOG.error( e );
	   }
	   List<Building>  bls = buildings.getBuildings();
	   if(bls == null || bls.isEmpty())
	   {
		   LOG.error("No Buildings found");
	   }
	   else
	   {
		 
		   List<Lab> curlabs = new ArrayList<Lab>();
			for (Building b : bls)
			{
				
				 for (Room r : b.getRooms() )
				 {
					 Lab l = new Lab();
					 l.setBuildingCode(b.getBuildingName());
					 l.setBuilding(b.getBuildingName());
					 l.setLab(r.getName());
					 
				    //  LOG.debug("Add %%%% l.getLab() : " + l.getLab() + ", linux use" + r.getLinux_inuse() +", "+ r.getMac_inuse() + ", " + r.getWindows_inuse());
					 // LOG.debug(" linux free " + r.getLinux_free() +", "+ r.getMac_free() + ", " + r.getWindows_free());
					 int in_use = r.getLinux_inuse()+ r.getMac_inuse() + r.getWindows_inuse();
					 int free = r.getLinux_free() + r.getMac_free() + r.getWindows_free();
					 int total = in_use + free;
					 l.setWindowsAvailability(r.getWindows_free() + "/" + (r.getWindows_inuse()+r.getWindows_free()));
					 l.setLinuxAvailability(r.getLinux_free() + "/" + (r.getLinux_inuse() + r.getLinux_free()));
					 l.setMacAvailability(r.getMac_free() + "/" + (r.getMac_inuse() + r.getMac_free()));
					 l.setAvailability(Integer.toString(free) + "/" + Integer.toString(total));
					//
					 curlabs.add(l);
				}//room 
			}
		     //LOG.debug("Building size " + bls.size());
		     Collections.sort(curlabs,new LabNameSort());
		     setLabs(curlabs);
			// LOG.debug("labs size " + curlabs.size());  
	   }
	}
	
	public String getDataMappingUrl() {
		return dataMappingUrl;
	}

	public void setDataMappingUrl(String dataMappingUrl) {
		this.dataMappingUrl = dataMappingUrl;
	}

	public Map<String, List<String>> getLabUrls() {
		return labUrls;
	}

	public void setLabUrls(Map<String, List<String>> labUrls) {
		this.labUrls = labUrls;
	}

	/**
	 * @return the buildingsSourceURL
	 */
	public String getBuildingsSourceURL() {
		return buildingsSourceURL;
	}

	/**
	 * @param buildingsSourceURL the buildingsSourceURL to set
	 */
	public void setBuildingsSourceURL(String buildingsSourceURL) {
		this.buildingsSourceURL = buildingsSourceURL;
	}

	
}

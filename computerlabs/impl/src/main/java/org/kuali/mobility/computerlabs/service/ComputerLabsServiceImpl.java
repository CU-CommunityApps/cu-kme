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

package org.kuali.mobility.computerlabs.service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.kuali.mobility.computerlabs.entity.Seat;
import org.kuali.mobility.maps.service.MapsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thoughtworks.xstream.XStream;

import flexjson.JSONSerializer;

@Service
public class ComputerLabsServiceImpl implements ComputerLabsService {

	private static org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(ComputerLabsServiceImpl.class);

    @Autowired
    private MapsService mapsService;
	
    /*
    @Transactional
	public List<ComputerLab> findAllComputerLabsByCampus(String campus) {
		ComputerLabsSeatParser parser = new ComputerLabsSeatParser();
		List<ComputerLab> labs = parser.parseSeats(campus);
		
		MapsGroup group = mapsService.getMapsGroupById(campus);
		Set<Location> locations = group.getMapsLocations();
		Map<String, Location> locationMap = new HashMap<String, Location>();
		for (Location loc : locations) {
			if (loc.getId() != null) {
				locationMap.put(loc.getId(), loc);	
			}
		}

		for (ComputerLab lab : labs) {
			if (lab.getBuildingCode() != null) {
				Location loc = locationMap.get(lab.getBuildingCode());
				if (loc != null) {
					lab.setBuildingCode(loc.getId());
				}
			}
		}
		
		return labs;
	}
    
    @Transactional
	public List<LabLocation> findAllLabLocationsByCampus(String campus) {
		List<ComputerLab> labs = this.findAllComputerLabsByCampus(campus);
		Map<String, LabLocation> labMap = new HashMap<String, LabLocation>();
		for (ComputerLab lab : labs) {
			LabLocation labLocation = labMap.get(lab.getBuildingNameOnly());
			if (labLocation == null) {
				labLocation = new LabLocation(lab.getBuildingNameOnly());
				labMap.put(lab.getBuildingNameOnly(), labLocation);
			}
			labLocation.getComputerLabs().add(lab);	
		}
		List<LabLocation> labLocations = new ArrayList<LabLocation>();
		labLocations.addAll(labMap.values());
		Collections.sort(labLocations);
		return labLocations;
	}
	*/
    
	@SuppressWarnings("unchecked")
	public List<Seat> findAllSeats() {
		String url = "http://ulib.iupui.edu/utility/seats.php?show=locations&type=data";
		
		XStream xs = new XStream();
		
		xs.processAnnotations(Seat.class);
		
		xs.alias("seats", List.class);
		xs.alias("seat", Seat.class);
		
		xs.aliasAttribute(Seat.class, "buildingCode", "building-code");
		xs.aliasAttribute(Seat.class, "windowsAvailability", "windows-availability");
		xs.aliasAttribute(Seat.class, "macAvailability", "mac-availability");
		xs.aliasAttribute(Seat.class, "softwareAvailability", "software-availability");
		
		List<Seat> seats = new ArrayList<Seat>();
		try {
			seats = (List<Seat>) xs.fromXML(new URL(url));
		} catch (MalformedURLException e) {
			LOG.error(e.getMessage(), e);
		}
		
		return seats;
	}

    public String toJson(Collection<Seat> seats) {
        return new JSONSerializer().exclude("*.class").serialize(seats);
    }
    	
}

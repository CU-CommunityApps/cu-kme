package org.kuali.mobility.computerlabs.helper;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("building")
public class Building implements Serializable 
{
	private static final long serialVersionUID = 1L;

	 @XStreamAlias("name")
	 private String buildingname;
	 @XStreamAlias("latitude")
	 private long latitude;
	@XStreamAlias("longitude")
	 private long longitude;
	@XStreamImplicit
	List<Room> rooms = new ArrayList<Room>();
	
	  public Building(){	    
	  }
		
	  public String getBuildingName() {
			return this.buildingname;
	  }

	  public void setBuildingName(String buildingname) {
			this.buildingname = buildingname;
		}

	  public long getLatitude() {
			return latitude;
		}


		public void setLatitude(long latitude) {
			this.latitude = latitude;
		}


		public long getLongitude() {
			return longitude;
		}


		public void setLongitude(long longitude) {
			this.longitude = longitude;
		}
	
	
		  /**
		 * @return the rooms
		 */
		public List<Room> getRooms() {
			return rooms;
		}

		/**
		 * @param rooms the rooms to set
		 */
		public void setRooms(List<Room> rooms) {
			this.rooms = rooms;
		}

			
}

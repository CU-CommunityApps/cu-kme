package org.kuali.mobility.computerlabs.helper;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("buildings")
public class Buildings {
	  
	
	 @XStreamImplicit
	  List<Building> buildings;
	 
	
	public List<Building> getBuildings() {
		return buildings;
	}

	public void setBuildings(List<Building> buildings) {
		this.buildings = buildings;
	}
	
/*	  Building b;
	  @XStreamImplicit
	  ArrayList buildings = new ArrayList();

	  Iterator it = buildings.iterator();

	  public void setBuilding (Building bl)
	  {
	    int n;
	    n = buildings.size();

	    buildings.add (n,bl);
	  }

	  public void add (int index, Building bl  )
	  {
		  buildings.add (bl);
	  }*/

	

	/*public Building get(int index)
	  {
	    return (Building)buildings.get(index);
	  }

	  public int count()
	  {

	    return buildings.size();
	  }*/
	  
	 
}
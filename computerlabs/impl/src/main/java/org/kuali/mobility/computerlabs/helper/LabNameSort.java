package org.kuali.mobility.computerlabs.helper;

import java.util.Comparator;

import org.kuali.mobility.computerlabs.entity.Lab;

public class LabNameSort implements Comparator<Lab> {
	
	
		public int compare(Lab o1, Lab o2) {
		//first by building name
		int comparison1 =	o1.getBuildingCode().compareTo(o2.getBuildingCode());
		 if (comparison1 != 0)
	     {
	            return comparison1;
	     }
		// Finally by room name
		 return o1.getLab().compareTo(o2.getLab());
	}
}



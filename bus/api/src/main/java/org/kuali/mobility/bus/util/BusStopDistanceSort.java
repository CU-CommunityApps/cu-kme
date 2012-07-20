package org.kuali.mobility.bus.util;

import java.util.Comparator;
import org.kuali.mobility.bus.entity.BusStop;



public class BusStopDistanceSort implements Comparator<BusStop> {


	public int compare(BusStop o1, BusStop o2) {
		 int comparison = this.compareDoubleValues(o1.getDistance(), o2.getDistance());
		 return comparison;
	}

	private int compareDoubleValues(Double d1, Double d2) {
		if (d1 == null) {
			d1 = 0.0;
		}
		if (d2 == null) {
			d2 = 0.0;
		}

		return d1.compareTo(d2);
	}


}



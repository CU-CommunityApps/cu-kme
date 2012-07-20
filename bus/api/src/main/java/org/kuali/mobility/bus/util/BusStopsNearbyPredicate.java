package org.kuali.mobility.bus.util;

import org.apache.commons.collections.Predicate;
//import org.apache.log4j.Logger;
import org.kuali.mobility.bus.entity.BusStop;


public class BusStopsNearbyPredicate implements Predicate{

	private double latitude;
	private double longitude;
	private double radius;
	private static double earthRadius = 6371; // km
	
	//private static Logger LOG = Logger.getLogger(BusStopsNearbyPredicate.class);

	
	public BusStopsNearbyPredicate(final double lat, final double lon,
			final double rad) {
		setLatitude(lat);
		setLongitude(lon);
		setRadius(rad);
	}

	@Override
	public boolean evaluate(Object object) {

		if (getRadius() < 0) {
			return false;
		}
		if (object instanceof BusStop) {
			BusStop busstop = (BusStop) object;

			double lat2 = Double.parseDouble(busstop.getLatitude());
			double lon2 = Double.parseDouble(busstop.getLongitude());

			double dlon = Math.toRadians(lon2 - getLongitude());
			double dlat = Math.toRadians(lat2 - getLatitude());

			double a = (Math.sin(dlat / 2)) * (Math.sin(dlat / 2))
					+ (Math.sin(dlon / 2)) * Math.sin(dlon / 2)
					* Math.cos(getLatitude()) * Math.cos(lat2);

			double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
			double dist = earthRadius * c;
			

			if (dist <= radius) 
			{
				busstop.setDistance(dist);
				busstop.setUnit(" km");
				return true; 
			}

		}
		return false;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}
	
	
	
}

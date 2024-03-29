/*
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
package org.kuali.mobility.bus.dao;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.kuali.mobility.bus.dao.helper.BusStopNameUtil;
import org.kuali.mobility.bus.entity.Bus;
import org.kuali.mobility.bus.entity.BusRoute;
import org.kuali.mobility.bus.entity.BusStop;
import org.kuali.mobility.bus.entity.helper.*;
import org.kuali.mobility.bus.util.BusStopDistanceSort;
import org.kuali.mobility.bus.util.BusStopsNearbyPredicate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.thoughtworks.xstream.XStream;

/**
 *
 * @author Joe Swanson <joseswan@umich.edu>
 */
public class BusDaoUMImpl implements BusDao, ApplicationContextAware {

	public static Logger LOG = Logger.getLogger(BusDaoUMImpl.class);

	private ApplicationContext applicationContext;

	private List<BusStop> busStops;
	private List<BusRoute> busRoutes;
	private List<Bus> buses;

	private String busStopUrl;
	private String busRouteUrl;
	private String busLocationUrl;

	private String busStopNamesMappingFile;

	public void loadRoutes() {

		XStream xstream = new XStream();
		xstream.processAnnotations(UMBusRouteReader.class);
		xstream.processAnnotations(UMBusRoute.class);
		xstream.processAnnotations(UMBusStop.class);
		xstream.addImplicitCollection(UMBusRouteReader.class, "routes");
		xstream.addImplicitCollection(UMBusRoute.class, "stops");

		UMBusRouteReader routeReader = null;
		try {
			routeReader = (UMBusRouteReader) xstream.fromXML(new URL(
					getBusRouteUrl()));
		} catch (MalformedURLException ex) {
			LOG.error(ex);
		}

		List<BusRoute> routes = new ArrayList<BusRoute>();
        List<BusStop>  stops  = new ArrayList<BusStop>();
		if (routeReader != null) {
			for (UMBusRoute r : routeReader.getRoutes()) {

				BusRoute route = (BusRoute) getApplicationContext().getBean(
						"busRoute");
				route.setId(Long.parseLong(r.getId()));
				route.setName(r.getName());
				route.setColor(r.getColor());
				LOG.debug("route color:" + route.getColor());
				if (null == getBusStops()) {
					setBusStops(new ArrayList<BusStop>());
				}

				for (UMBusStop s : r.getStops()) {
					BusStop stop = (BusStop) getApplicationContext().getBean(
							"busStop");
					//stop.setName(s.getName());
					//LOG.debug("bus stop ROUTE name: " + r.getName() + ", stopname -" + s.getName() + ", stopname2 -" + s.getName2() + ", stopname3 - " + s.getName3());
					//set busstopname loaded from xml
					stop.setName(BusStopNameUtil.updateBusStopName(r.getName(), s.getName()));
					stop.setId(s.getName().hashCode());
					stop.setLatitude(s.getLatitude());
					stop.setLongitude(s.getLongitude());

					Map<Double, String> schedule = new TreeMap<Double, String>();
					// TODO: Fix this to dynamically utilize the toacount
					// variable.
					// This is functional but potentially will break.
					if (s.getId1() != null) {
						LOG.debug("Looking up bus " + s.getId1());
						Bus tBus = getBus(Long.parseLong(s.getId1()));
						LOG.debug("Bus was " + (tBus == null ? "not " : "")
								+ "found.");
						schedule.put(new Double(s.getToa1()), tBus.getName());

					}
					if (s.getId2() != null) {
						LOG.debug("Looking up bus " + s.getId2());
						Bus tBus = getBus(Long.parseLong(s.getId2()));
						LOG.debug("Bus was " + (tBus == null ? "not " : "")
								+ "found.");
						schedule.put(new Double(s.getToa2()), tBus.getName());
					}
					if (s.getId3() != null) {
						LOG.debug("Looking up bus " + s.getId3());
						Bus tBus = getBus(Long.parseLong(s.getId3()));
						LOG.debug("Bus was " + (tBus == null ? "not " : "")
								+ "found.");
						schedule.put(new Double(s.getToa3()), tBus.getName());
					}
					if (s.getId4() != null) {
						LOG.debug("Looking up bus " + s.getId4());
						Bus tBus = getBus(Long.parseLong(s.getId4()));
						LOG.debug("Bus was " + (tBus == null ? "not " : "")
								+ "found.");
						schedule.put(new Double(s.getToa4()), tBus.getName());
					}
					if (s.getId5() != null) {
						LOG.debug("Looking up bus " + s.getId5());
						Bus tBus = getBus(Long.parseLong(s.getId5()));
						LOG.debug("Bus was " + (tBus == null ? "not " : "")
								+ "found.");
						schedule.put(new Double(s.getToa5()), tBus.getName());
					}
					if (s.getId6() != null) {
						LOG.debug("Looking up bus " + s.getId6());
						Bus tBus = getBus(Long.parseLong(s.getId6()));
						LOG.debug("Bus was " + (tBus == null ? "not " : "")
								+ "found.");
						schedule.put(new Double(s.getToa6()), tBus.getName());
					}
					if (s.getId7() != null) {
						LOG.debug("Looking up bus " + s.getId7());
						Bus tBus = getBus(Long.parseLong(s.getId7()));
						LOG.debug("Bus was " + (tBus == null ? "not " : "")
								+ "found.");
						schedule.put(new Double(s.getToa7()), tBus.getName());
					}
					if (s.getId8() != null) {
					    LOG.debug("Looking up bus " + s.getId8());
						Bus tBus = getBus(Long.parseLong(s.getId8()));
						LOG.debug("Bus was " + (tBus == null ? "not " : "")
								+ "found.");
						schedule.put(new Double(s.getToa8()), tBus.getName());
					}
					if (s.getId9() != null) {
					    LOG.debug("Looking up bus " + s.getId9());
						Bus tBus = getBus(Long.parseLong(s.getId9()));
					    LOG.debug("Bus was " + (tBus == null ? "not " : "")
								+ "found.");
						schedule.put(new Double(s.getToa9()), tBus.getName());
					}
					if (s.getId10() != null) {
					 	LOG.debug("Looking up bus " + s.getId10());
						Bus tBus = getBus(Long.parseLong(s.getId10()));
					    LOG.debug("Bus was " + (tBus == null ? "not " : "")
								+ "found.");
						schedule.put(new Double(s.getToa10()), tBus.getName());
					}

					stop.setSchedule(schedule);
					route.addStop(stop);

                    if( stops.contains(stop) ) {
                        LOG.debug( "Bus stop already exists in the list for: "+stop.getName() );
                        int i = stops.indexOf( stop );
                        BusStop oldStop = stops.get( i );

                        BusStop mergedStop = (BusStop) getApplicationContext().getBean(
							"busStop");
                        mergedStop.setId( oldStop.getId() );
                        mergedStop.setLatitude( oldStop.getLatitude() );
                        mergedStop.setLongitude( oldStop.getLongitude() );
                        mergedStop.setName( oldStop.getName() );

                        Map<Double, String> mergedSchedule = new TreeMap<Double, String>();
                        mergedSchedule.putAll( oldStop.getSchedule() );
                        mergedSchedule.putAll( stop.getSchedule() );

                        mergedStop.setSchedule( mergedSchedule );
                        stops.set(i, mergedStop);
                    } else {
                        LOG.debug( "Bus Stop is not found in master list for: "+stop.getName() );
                        stops.add(stop);
                    }
				}
				routes.add(route);
			}
			setBusRoutes(routes);
            setBusStops( stops );
		}

		LOG.debug((null == getBusRoutes() ? "Failed to load" : "Loaded")
				+ " routes.");
	}

	@Override
	public void loadStops() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void loadBusLocations() {
		XStream xstream = new XStream();
		xstream.processAnnotations(UMBusReader.class);
		xstream.processAnnotations(UMBus.class);
		xstream.addImplicitCollection(UMBusReader.class, "items");

		UMBusReader busReader = null;
		try {
			busReader = (UMBusReader) xstream.fromXML(new URL(
					getBusLocationUrl()));
		} catch (MalformedURLException ex) {
			LOG.error(ex);
		}

		List<Bus> busData = new ArrayList<Bus>();
		if (busReader != null) {
			for (UMBus b : busReader.getItems()) {
				Bus bus = (Bus) getApplicationContext().getBean("bus");
				bus.setName(b.getRouteName());
				bus.setId(b.getId());
				bus.setRouteId(b.getRouteId());
				bus.setRouteName(b.getRouteName());
				bus.setHeading(b.getHeading());
				bus.setColor(b.getColor());
				bus.setLatitude(b.getLatitude());
				bus.setLongitude(b.getLongitude());
				busData.add(bus);
			}
			this.setBuses(busData);
		}
	}

	/**
	 * @return the List<BusStop>
	 */
	@SuppressWarnings("unchecked")
	public List<BusStop> findNearByStops(double lat1, double lon1, double radius) {

		List<BusStop> stops = new ArrayList<BusStop>();
		double dist = 0.0;

		if (null == getBusStops()) {
			loadRoutes();
		}
		stops = (List<BusStop>) CollectionUtils.select(getBusStops(),
				new BusStopsNearbyPredicate(lat1, lon1, radius));
		
		//sorts km
		 Collections.sort(stops, new BusStopDistanceSort());
		 //conversion here to ft and miles
		 for (BusStop s : stops )
		 {
			
			 if (s.getDistance() <= 0.1524)
				{
					//convert to feet
					//1km= 3.28 * 1000 ft
					s.setDistance(roundToDecimals(s.getDistance() * 3.28 * 1000,2));
					s.setUnit(" ft");
					LOG.debug("distance in feet : " + s.getDistance());
				}
				else
				{
				   //convert to miles
					//1 km = 0.621371192 mi
				   s.setDistance(roundToDecimals(s.getDistance() * 0.621371192,2));
				   s.setUnit(" mi");
				   LOG.debug("distance in  miles:" + s.getDistance());
				}
			 
			 //LOG.debug("findNearByStops***" + s.getName() + " ," + s.getDistance());
			 
		 }
		return stops;

	}
	
	/* roundToDecimals for formatting stop distance to 2 decimals */
	private  double roundToDecimals(double d, int c) {
		int temp=(int)((d*Math.pow(10,c)));
		return (((double)temp)/Math.pow(10,c));
		}

	/**
	 * @return the applicationContext
	 */
	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * @param applicationContext
	 *            the applicationContext to set
	 */
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	/**
	 * @return the busStops
	 */
	public List<BusStop> getBusStops() {
		return busStops;
	}

	/**
	 * @param busStops
	 *            the busStops to set
	 */
	public void setBusStops(List<BusStop> busStops) {
		this.busStops = busStops;
	}

	/**
	 * @return the busRoutes
	 */
	public List<BusRoute> getBusRoutes() {
		return busRoutes;
	}

	/**
	 * @param busRoutes
	 *            the busRoutes to set
	 */
	public void setBusRoutes(List<BusRoute> busRoutes) {
		this.busRoutes = busRoutes;
	}

	/**
	 * @return the buses
	 */
	public List<Bus> getBuses() {
		return buses;
	}

	/**
	 * @param buses
	 *            the buses to set
	 */
	public void setBuses(List<Bus> buses) {
		this.buses = buses;
	}

	public Bus getBus(long id) {
		Bus bus = null;
		if (null == getBuses()) {
			loadBusLocations();
		}
		for (Bus b : getBuses()) {
			if (bus != null) {
				break;
			}
			if (b.getId() == id) {
				bus = b;
				LOG.debug("Found bus " + id + " in list.");
			}
		}
		return bus;
	}

	/**
	 * @return the busStopUrl
	 */
	public String getBusStopUrl() {
		return busStopUrl;
	}

	/**
	 * @param busStopUrl
	 *            the busStopUrl to set
	 */
	public void setBusStopUrl(String busStopUrl) {
		this.busStopUrl = busStopUrl;
	}

	/**
	 * @return the busRouteUrl
	 */
	public String getBusRouteUrl() {
		return busRouteUrl;
	}

	/**
	 * @param busRouteUrl
	 *            the busRouteUrl to set
	 */
	public void setBusRouteUrl(String busRouteUrl) {
		this.busRouteUrl = busRouteUrl;
	}

	/**
	 * @return the busLocationUrl
	 */
	public String getBusLocationUrl() {
		return busLocationUrl;
	}

	/**
	 * @param busLocationUrl
	 * the busLocationUrl to set
	 */
	public void setBusLocationUrl(String busLocationUrl) {
		this.busLocationUrl = busLocationUrl;
	}

	/**
	 * @return the busStopNamesMappingFile
	 */
	public String getStopNamesMappingFile() {
		return busStopNamesMappingFile;
	}

	/**
	 * @param busStopNamesMappingFile the stopNamesMappingFile to set
	 */
	public void setbusStopNamesMappingFile(String stopNamesMappingFile) {
		this.busStopNamesMappingFile = stopNamesMappingFile;
	}


}

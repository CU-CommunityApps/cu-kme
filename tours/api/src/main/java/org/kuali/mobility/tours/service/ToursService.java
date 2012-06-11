package org.kuali.mobility.tours.service;

import java.util.List;

import org.kuali.mobility.security.authn.entity.User;
import org.kuali.mobility.tours.entity.POI;
import org.kuali.mobility.tours.entity.Tour;

import de.micromata.opengis.kml.v_2_2_0.Kml;

public interface ToursService {
	
	public static final String PERMISSION_TYPE_EDIT = "E";
	public static final String PERMISSION_TYPE_VIEW = "V";
	
	public Tour findTourById(Long id);
    public Tour findTourByName(String name);
    public Long saveTour(Tour tour);
    public List<Tour> findAllTours();
    public void deleteTourById(Long id);
    public void duplicateTourById(Long id);
    
    public POI findPoiById(Long id);
    public POI findPoiByOrder(Long tourId, Integer order);
	public Long savePoi(POI poi);
	public void duplicatePoiById(Long id);
	public void deletePoiById(Long poiId);
	public List<POI> findAllCommonPOI();
	
	public Kml createTourKml(Tour tour);
	public boolean hasAccessToEditTour(User user, Tour tour);
	public boolean hasAccessToViewTour(User user, Tour tour);
	public boolean hasAccessToEditPOI(User user, POI poi);
	public boolean hasAccessToViewPOI(User user, POI poi);
	public boolean hasAccessToPublish(User user);
	
}

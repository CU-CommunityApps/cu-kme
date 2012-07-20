package org.kuali.mobility.maps.service;

import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kuali.mobility.maps.entity.Location;
import org.kuali.mobility.maps.entity.MapsGroup;
import org.kuali.mobility.util.mapper.DataMapperImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class MapsServiceUmichImplTest {
	
	private static final Logger LOG = Logger.getLogger(MapsServiceUmichImplTest.class);
	private static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public static void setApplicationContext(ApplicationContext applicationContext) {
		MapsServiceUmichImplTest.applicationContext = applicationContext;
	}

	@BeforeClass
    public static void createApplicationContext() {
    	MapsServiceUmichImplTest.setApplicationContext(new FileSystemXmlApplicationContext(getConfigLocations()));
    }

    private static String[] getConfigLocations() {
        return new String[] { "classpath:/KMESpringBeans.xml" };
    }
	@Test
	public void testGetMapsGroupByName() {
		MapsServiceUmichImpl test = (MapsServiceUmichImpl)getApplicationContext().getBean("mapsService");
		MapsGroup mapGroups = test.getMapsGroupByName("Hall");
		
		assertTrue( "Failed to find location.", mapGroups != null && mapGroups.getMapsLocations()!=null );
		if (mapGroups != null) {
			Set<Location> locations = mapGroups.getMapsLocations() ;
			LOG.info("find loactions: " + locations.size());
		}
	}

}

package org.kuali.mobility.bus.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kuali.mobility.bus.dao.BusDaoUMImplTest;
import org.kuali.mobility.bus.entity.BusStop;
import org.kuali.mobility.bus.util.BusStopDistanceSort;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class BusServiceImplTest {

    private static final Logger LOG = Logger.getLogger(BusDaoUMImplTest.class);
    private static ApplicationContext applicationContext;

    /**
     * @return the applicationContext
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * @param aApplicationContext the applicationContext to set
     */
    public static void setApplicationContext(ApplicationContext aApplicationContext) {
        applicationContext = aApplicationContext;
    }

    public BusServiceImplTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        BusServiceImplTest.setApplicationContext(new FileSystemXmlApplicationContext(getConfigLocations()));
    }

    private static String[] getConfigLocations() {
        return new String[]{"classpath:/BusSpringBeans.xml"};
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Test
    public void testGetNearbyStops() {
        BusServiceImpl service = (BusServiceImpl) getApplicationContext().getBean("busService");
        List<BusStop> stopstest = new ArrayList<BusStop>();
        //crislerarena
        //double lat1= 42.26439987447436;
        // double lon1=-83.74433755874634;
        //
        double lat1 = 42.2693773;
        double lon1 = -83.7460591;
        double radius = 0.9; //in km or 500 meters
        stopstest = service.getNearbyStops(lat1, lon1, radius);
        Collections.sort(stopstest, new BusStopDistanceSort());
        for (BusStop s : stopstest) {
            LOG.debug("BusStop ID: " + s.getId() + " ,name :" + s.getName() + ", distance: " + s.getDistance() + s.getUnit());
        }
        assertTrue("Failed to find nearby bus stops.", stopstest != null);

    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.mobility.events.dao;

import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author joseswan
 */
public class EventsDaoImplTest {

    private static ApplicationContext applicationContext;

    @BeforeClass
    public static void createApplicationContext() throws Exception
    {
        setApplicationContext(new ClassPathXmlApplicationContext( getConfigLocations() ));
    }

    private static String[] getConfigLocations() {
        return new String[] { "classpath:/SpringBeans.xml","classpath:/webmvc-config.xml" };
    }

    /**
     * Test of initData method, of class EventsDaoImpl.
     */
    @Test
    public void testInitData_3args() {
    }

    /**
     * Test of initData method, of class EventsDaoImpl.
     */
    @Test
    public void testInitData_String_String() {
        EventsDao dao = (EventsDao)getApplicationContext().getBean("eventDao");
//        dao.initData( null );

        int eventCount = 0;

//        dao.initData( null, "14" );
//
//        assertTrue( "Failed to load events.", dao.getEvents() != null && dao.getEvents().size() > 0 );
//
//        eventCount = dao.getEvents().size();
//        System.out.append( "Events list length: " + eventCount );
        dao.initData( null, "8" );

        assertTrue( "Failed to load events1.", dao.getEvents() != null && dao.getEvents().size() > eventCount );

        eventCount = dao.getEvents().size();
        System.out.println( "Events list length: " + eventCount );

        dao.initData( null, "8" );

        assertTrue( "Failed to load events2.", dao.getEvents() != null && dao.getEvents().size() > 0 );
        assertTrue( "Event list improperly removed duplicates.", dao.getEvents().size() == eventCount );

        eventCount = dao.getEvents().size();
        System.out.println( "Events list length: " + eventCount );
    }

    /**
     * Test of initData method, of class EventsDaoImpl.
     */
    @Test
    public void testInitData_String() {
        EventsDaoImpl dao = (EventsDaoImpl)getApplicationContext().getBean("eventDao");

        dao.initData( null );

        assertTrue( "Failed to load categories.", dao.getCategories() != null && dao.getCategories().size() > 0 );
    }

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

}

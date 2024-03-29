/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.mobility.events.service;

import java.util.Date;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.kuali.mobility.events.dao.EventsDaoImpl;
import org.kuali.mobility.events.entity.Category;
import org.kuali.mobility.events.entity.Event;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author swansje
 */
public class EventsServiceImplTest {

    private static ApplicationContext applicationContext;

    @BeforeClass
    public static void createApplicationContext() throws Exception
    {
        applicationContext = new ClassPathXmlApplicationContext( getConfigLocations() );
    }

    private static String[] getConfigLocations() {
        return new String[] { "/SpringBeans.xml","/webmvc-config.xml" };
    }

    /**
     * Test of getEvent method, of class EventsServiceImpl.
     */
//    @Test
//    public void testGetEvent() {
//        System.out.println("getEvent");
//        String campus = "";
//        String categoryId = "";
//        String eventId = "";
//        EventsServiceImpl instance = new EventsServiceImpl();
//        Event expResult = null;
//        Event result = instance.getEvent(campus, categoryId, eventId);
//        assertEquals(expResult, result);
//    }
//
//    /**
//     * Test of getAllEvents method, of class EventsServiceImpl.
//     */
//    @Test
//    public void testGetAllEvents() {
//        System.out.println("getAllEvents");
//        String campus = "";
//        String categoryId = "";
//        EventsServiceImpl instance = new EventsServiceImpl();
//        List expResult = null;
//        List result = instance.getAllEvents(campus, categoryId);
//        assertEquals(expResult, result);
//    }
//
//    /**
//     * Test of getCategoriesByCampus method, of class EventsServiceImpl.
//     */
    @Test
    public void testGetCategoriesByCampus() {

//        String campus = null;
//        EventsServiceImpl instance = (EventsServiceImpl)getApplicationContext().getBean("eventService");
//        List result = instance.getCategoriesByCampus(campus);
//        assertTrue( "Failed to load categories.", result != null && result.size() > 0 );
    }

    /**
     * @return the applicationContext
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * @param applicationContext the applicationContext to set
     */
    public static void setApplicationContext(ApplicationContext applicationContext) {
        EventsServiceImplTest.applicationContext = applicationContext;
    }

}

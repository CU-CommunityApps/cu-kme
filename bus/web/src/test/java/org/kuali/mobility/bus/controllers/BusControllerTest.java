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
package org.kuali.mobility.bus.controllers;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.ui.ExtendedModelMap;

/**
 *
 * @author Joe Swanson <joseswan@umich.edu>
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration( locations={"classpath:/BusSpringBeans.xml"} )
public class BusControllerTest {

    private static final Logger LOG = Logger.getLogger( BusControllerTest.class );

    @Autowired
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

    @BeforeClass
    public static void setUpClass() throws Exception {
    	BusControllerTest.setApplicationContext(new FileSystemXmlApplicationContext(getConfigLocations()));
    }

    private static String[] getConfigLocations() {
        return new String[] { "classpath:/BusSpringBeans.xml" };
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Test
    public void testViewRoute() {
        assertTrue( "Failed to find application context.", null != getApplicationContext() );
        BusController controller = (BusController)getApplicationContext().getBean("busController");
        String viewName = controller.viewRoute( null, new ExtendedModelMap(), "1", "ALL" );
        assertTrue( "Failed to find view.", "bus/viewRoute".equalsIgnoreCase(viewName));

        viewName = controller.viewRoute( null, new ExtendedModelMap(), "0", "ALL" );
        assertTrue( "Failed to find view.", "bus/viewRoute".equalsIgnoreCase(viewName));

    }

//    @Test
//    public void testViewStop() {
//        assertTrue( "Failed to find application context.", null != getApplicationContext() );
//        BusController controller = (BusController)getApplicationContext().getBean("busController");
//
//        String viewName = controller.viewStop( null, new ExtendedModelMap(), "1", "2063021881", "ALL" );
//        assertTrue( "Failed to find view.", "bus/viewStop".equalsIgnoreCase(viewName));
//    }
}

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
package org.kuali.mobility.dining.controllers;

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
public class DiningControllerTest {

    private static final Logger LOG = Logger.getLogger( DiningControllerTest.class );

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
    	DiningControllerTest.setApplicationContext(new FileSystemXmlApplicationContext(getConfigLocations()));
    }

    private static String[] getConfigLocations() {
        return new String[] { "classpath:/DiningSpringBeans.xml" };
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Test
    public void testGetJsonData() {
        assertTrue( "Failed to find application context.", null != getApplicationContext() );
        DiningController controller = (DiningController)getApplicationContext().getBean("diningController");

        String jsonData = controller.getMenusJson( "Bursley Dining Hall", null);

        LOG.debug( "JSON: "+jsonData );

        assertTrue( "Failed to find json data.", jsonData != null && !jsonData.isEmpty() );

        jsonData = controller.getMenusJson( "Bursley Dining Hall", "");

        LOG.debug( "JSON: "+jsonData );

        assertTrue( "Failed to find json data.", jsonData != null && !jsonData.isEmpty() );
    }
}

/**
 * Copyright 2011-2012 The Kuali Foundation Licensed under the
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

package org.kuali.mobility.weather.dao;

import static org.junit.Assert.*;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kuali.mobility.weather.dao.WeatherDaoImpl;
import org.kuali.mobility.weather.entity.Weather;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.io.*;

public class WeatherDaoImplTest {

	private static org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(WeatherDaoImplTest.class);

	private static ApplicationContext applicationContext;
	
    @BeforeClass
    public static void createApplicationContext() {
    	WeatherDaoImplTest.setApplicationContext(new FileSystemXmlApplicationContext(getConfigLocations()));
    }

    private static String[] getConfigLocations() {
        return new String[] { "classpath:/SpringBeans.xml" };
    }

    @Test
	public void testParseWeather() {
		WeatherDaoImpl dao = (WeatherDaoImpl)getApplicationContext().getBean("weatherDao");

		Resource resource = applicationContext.getResource("classpath:weather.xml");

		try {
			
			dao.setUrl(resource.getURL().toString());
			
			Weather weather = dao.parseWeather();

			assertTrue("Failed to parse weather data for pressure.", weather.getPressure() != null);
			
		} catch (Exception e) {
			
			LOG.debug("Failed to find weather.xml in classpath.");
			
			assertTrue("Failed to find weather.xml in classpath.", false);
			
		}
		
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public static void setApplicationContext(ApplicationContext applicationContext) {
		WeatherDaoImplTest.applicationContext = applicationContext;
	}

}

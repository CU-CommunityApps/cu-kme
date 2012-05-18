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

import org.kuali.mobility.weather.service.WeatherService;

public class WeatherInitBean {

	private static org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(WeatherInitBean.class);

	private WeatherService weatherService;
	private int minutesToRefresh;
	
	private static Thread backgroundThread = null;

	public void init() {
		backgroundThread = new Thread(new BackgroundThread());
		backgroundThread.setDaemon(true);
		backgroundThread.start();
	}

	public void cleanup() {
		LOG.info("Cleaning up weather.");
	}

	public WeatherService getWeatherService() {
		return weatherService;
	}

	public void setWeatherService(WeatherService weatherService) {
		this.weatherService = weatherService;
	}

	public int getMinutesToRefresh() {
		return minutesToRefresh;
	}

	public void setMinutesToRefresh(int minutesToRefresh) {
		this.minutesToRefresh = minutesToRefresh;
	}

	private class BackgroundThread implements Runnable {

		public void run() {
			while (true) {
				try {
					weatherService.refreshWeather();
					try {
						Thread.sleep(1000 * 60 * getMinutesToRefresh());
					} catch (InterruptedException e) {
						LOG.error(e.getMessage(), e);
					}
				} catch (Exception e) {
					LOG.error(e.getMessage(), e);
				}
			}
		}

	}

}

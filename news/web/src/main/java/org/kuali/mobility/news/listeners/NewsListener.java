/**
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

package org.kuali.mobility.news.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.kuali.mobility.news.service.NewsService;
import org.springframework.context.ApplicationContext;

/**
 * A listener to start the news cache when the application has started. It is configured in web.xml.
 * 
 * @author Kuali Mobility Team (moblitiy.collab@kuali.org)
 */
public class NewsListener implements ServletContextListener {
	
	private static org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(NewsListener.class);

	private NewsService newsService;

	/**
	 * An event that's fired when the application has started.  Starts the news cache.
	 */
	public void contextInitialized(final ServletContextEvent event) {
		ApplicationContext ctx = org.springframework.web.context.support.WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
		newsService = (NewsService) ctx.getBean("NewsService");
		
		LOG.info("Starting the News cache thread");
		newsService.startCache();
		LOG.info("News cache thread started");
	}

	/**
	 * An event that's fired when the application is shutting down.  Stops the news cache.
	 */
	public void contextDestroyed(final ServletContextEvent event) {
		if (newsService != null) {
			LOG.info("Stopping the News cache thread");
			newsService.stopCache();
			LOG.info("News cache thread should be completely dead");
		}
	}
	
}

package org.kuali.mobility.news.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.kuali.mobility.news.service.NewsService;
import org.springframework.context.ApplicationContext;

public class NewsListener implements ServletContextListener {
	
	private static org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(NewsListener.class);

	private NewsService newsService;

	public void contextInitialized(final ServletContextEvent event) {
		ApplicationContext ctx = org.springframework.web.context.support.WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
		newsService = (NewsService) ctx.getBean("NewsService");
		
		LOG.info("Starting the News cache thread");
		newsService.startCache();
		LOG.info("News cache thread started");
	}

	public void contextDestroyed(final ServletContextEvent event) {
		if (newsService != null) {
			LOG.info("Stopping the News cache thread");
			newsService.stopCache();
			LOG.info("News cache thread should be completely dead");
		}
	}
	
}

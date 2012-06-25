package org.kuali.mobility.events.dao;

import java.util.ArrayList;
import java.util.List;

import org.kuali.mobility.events.entity.Category;
import org.kuali.mobility.events.entity.Event;



public class EventInitBean {
	private static org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(EventInitBean.class);
	   
	private EventsDaoUMImpl dao;
	
	public EventsDaoUMImpl getDao() {
		return dao;
	}

	public void setDao(EventsDaoUMImpl dao) {
		this.dao = dao;
	}

	private static Thread backgroundThread = null;
	
	public void init() {
		backgroundThread = new Thread(new BackgroundThread());
    	backgroundThread.setDaemon(true);
    	backgroundThread.start();    	
	}

    public void cleanup() {
    	LOG.info("Cleaning up events.");
    }
    private class BackgroundThread implements Runnable {
    	
        public void run() {    
			LOG.info("Initializing events...");
			getDao().initData(null);
			LOG.info("Finished initializing events.");
        	while (true) {
        		try {
	    			LOG.info("Events sleeping...");
	                try {
	                    Thread.sleep(1000 * 60 * 15);
	                } catch (InterruptedException e) {
	                    LOG.error(e.getMessage(), e);
	                }
	                List<Event> events = new ArrayList<Event>();
	    			LOG.info("Refreshing Events...");
	    			for (Category c : getDao().getCategories()) {
	    				events.addAll(getDao().loadEventsForCategory(null, c.getCategoryId()));
	    			}
	    			getDao().setEvents(events);
	    			LOG.info("Finished refreshing Events.");	                
        		} catch (Exception e) {
                    LOG.error(e.getMessage(), e);
        		}
        	}
        }
        
    }

}

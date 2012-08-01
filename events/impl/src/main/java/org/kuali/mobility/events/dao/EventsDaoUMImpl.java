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
package org.kuali.mobility.events.dao;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.kuali.mobility.events.entity.Category;
import org.kuali.mobility.events.entity.Event;
import org.kuali.mobility.events.entity.EventContact;
import org.kuali.mobility.events.entity.UMEvent;
import org.kuali.mobility.events.entity.UMEventReader;
import org.kuali.mobility.events.entity.UMSponsor;
import org.kuali.mobility.events.util.CategoryPredicate;

import com.thoughtworks.xstream.XStream;

public class EventsDaoUMImpl extends EventsDaoImpl {

    private static org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger( EventsDaoUMImpl.class );
    
//    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    @Override
    public void initData( final String campus, final String categoryId ) {
    	this.addEvents(loadEventsForCategory (campus, categoryId));
    	
    }
    public List<Event> loadEventsForCategory( final String campus, final String categoryId ) {
    	
    	LOG.debug( "Loading event feed for category "+categoryId );
        if( null == getEvents() || getEvents().isEmpty() )
        {
        	LOG.debug( "Events list was empty, creating a new one." );
            //setEvents( new ArrayList<Event>() );
        }
        if( null == getCategories() || getCategories().isEmpty() )
        {
        	LOG.debug( "Category list was empty, initializing a new one." );
        	initData( campus );
        }

        List<Event> newEvents = new ArrayList<Event>();

		Category category = (Category) CollectionUtils.find ( getCategories(), new CategoryPredicate( campus, categoryId ) );;
		
		if ( category != null ) {
			LOG.debug( "Found category object for id "+categoryId );
			XStream xstream = new XStream();
			xstream.processAnnotations(UMEventReader.class);
			xstream.processAnnotations(UMEvent.class);
			xstream.processAnnotations(UMSponsor.class);
			UMEventReader eventReader = null;
			try {
				URL url = new URL(category.getUrlString()+"&_type=xml");
				LOG.debug("Mapping events from url: " + category.getUrlString());
				
				if (url != null) {
					eventReader = (UMEventReader) xstream.fromXML(url);
				}
			} catch (MalformedURLException mue) {
	        	LOG.error( mue.getLocalizedMessage() );
	        }
			LOG.debug("check eventReader " + (eventReader == null?"null":"mnot null"));
			LOG.debug("check eventReader.getEvents " + (eventReader.getEvents() == null?"null":"mnot null"));

			if ( eventReader != null && eventReader.getEvents() != null) {
			for ( UMEvent e : eventReader.getEvents()){
				LOG.debug("processing e " + e.getTitle());
				Event newEvent = (Event) getApplicationContext().getBean("event");
				newEvent.setEventId(e.getId());
				newEvent.setCategory(category);
				newEvent.setTitle(e.getTitle());
				newEvent.setDisplayStartTime(e.getTimeBegin());
                                //Saket's Addition
                                newEvent.setType(e.getType());                                
			    newEvent.setDisplayStartDate(e.getDateBegin());
			    newEvent.setLocation(e.getBuildingName());
			    newEvent.setLink(e.getUrl());
			    try {
			    	if ( e.getTsBegin() != null && e.getTsBegin().isEmpty() == false) {
					newEvent.setStartDate(sdf.parse(e.getTsBegin())); 
					}
			    	if ( e.getTsEnd() != null && e.getTsEnd().isEmpty() == false) {
					newEvent.setEndDate(sdf.parse(e.getTsEnd()));
			    	}
				} catch (ParseException e1) {
					LOG.error(e1.getLocalizedMessage());
				}
				newEvent.setDisplayEndTime(e.getTimeEnd());
				newEvent.setDisplayEndDate(e.getDateEnd());
				List<String> myDescription = new ArrayList<String>();
				myDescription.add(e.getDescription());
				newEvent.setDescription( myDescription );
				List<EventContact> myContacts = new ArrayList<EventContact>();
				for ( UMSponsor f : e.getSponsors()){
					EventContact newContact = (EventContact) getApplicationContext().getBean("eventContact");
					newContact.setName(f.getGroupName());
					newContact.setUrl(f.getWebsite());
					myContacts.add(newContact);	
				}
				newEvent.setContact(myContacts);
				LOG.debug("CONTACT " + newEvent.getContact());
				newEvents.add(newEvent);
			}
			}
		}
		return( newEvents );
	}

}

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

package org.kuali.mobility.events.service;

import java.util.Date;
import java.util.List;

import org.kuali.mobility.events.entity.Category;
import org.kuali.mobility.events.entity.Event;

public interface EventsService {

	public List<Event> getAllEvents(String campus, String categoryId);

	public List<Category> getCategoriesByCampus(String campus);

	public Event getEvent(String campus, String categoryId, String eventId);
	
	public Category getCategory(String campus, String categoryId);

	public String getEventJson(String eventId);
        
        //Saket's contribution
        //Not used currently. Reserved for future. Might have few bugs. 
        
        //Getting the list of all events taking place that date 
        public List<Event> getAllEventsByDateCurrent(String campus, String categoryId, Date dateCurrent);
        
        //Getting the list of the events from one date to the other date
        public List<Event> getAllEventsByDateFromTo(String campus, String categoryId, Date from, Date to);
        
        //Getting the list of all events on a particular date
        public List<Event> getAllEventsByDateSpecific(String campus, String categoryId, String specific);
        
        
}

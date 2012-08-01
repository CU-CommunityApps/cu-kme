/**
 * Copyright 2011 The Kuali Foundation Licensed under the Educational Community
 * License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.kuali.mobility.events.controllers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import org.kuali.mobility.events.dao.EventsDaoUMImpl;

import org.kuali.mobility.events.entity.Category;
import org.kuali.mobility.events.entity.CategoryImpl;
import org.kuali.mobility.events.entity.Event;
import org.kuali.mobility.events.service.EventsService;
import org.kuali.mobility.security.authn.entity.User;
import org.kuali.mobility.shared.Constants;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

//@Controller
@RequestMapping("/events")
public class EventsFilterController {

    private static org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(EventsFilterController.class);
    private EventsService eventsService;
    private EventsDaoUMImpl eventsUMService = new EventsDaoUMImpl();

    public void setEventsService(EventsService eventsService) {
        this.eventsService = eventsService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String homePage(HttpServletRequest request, Model uiModel) throws ParseException {
        User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);
        String campus = null;
        if (user.getViewCampus() == null) {
            return "redirect:/campus?toolName=events";
        } else {
            campus = user.getViewCampus();
        }

        List<Category> categories = eventsService.getCategoriesByCampus(campus);
        LOG.debug("Found " + categories.size() + " categories via local service for campus " + campus);
        uiModel.addAttribute("categories", categories);
        uiModel.addAttribute("campus", campus);

        //code for making the new home page of events
        HashMap<Category, List<Event>> mObj = new HashMap<Category, List<Event>>();
        uiModel.addAttribute("todayDate", "Tuesday Jul 31, 2012");
        List<Event> eventList = new ArrayList<Event>();
        List<Event> eventListR = new ArrayList<Event>();

        Iterator itc = categories.iterator();
        while (itc.hasNext()) {
            Category obj = (Category) itc.next();
            eventList = eventsService.getAllEventsByDateSpecific(campus, obj.getCategoryId(), "2012-07-31");
            eventListR.addAll(eventList);
        }

        //Grouping of events by category after getting the list of all the matched events
        List<Event> eventListByCat = new ArrayList<Event>();
        Iterator ite = eventListR.iterator();
        while (ite.hasNext()) {
            Event eObj = (Event) ite.next();
            //taking the current list of events from the map
            eventListByCat = (ArrayList<Event>) mObj.get(eObj.getCategory());
            //adding the current event to the above list
            if (eventListByCat == null) {
                eventListByCat = new ArrayList<Event>();
            }
            eventListByCat.add(eObj);
            //saving back to the map with the updated list
            mObj.put(eObj.getCategory(), eventListByCat);
        }
        uiModel.addAttribute("groupByCat", mObj);

//                uiModel.addAttribute("eventListController", eventListR);

        return "events/list";
    }

    @RequestMapping(value = "/viewEvents", method = RequestMethod.GET)
    public String viewEvents(HttpServletRequest request, Model uiModel, @RequestParam(required = true) String categoryId, @RequestParam(required = false) String campus) throws Exception {
        List<Event> eventList = eventsService.getAllEvents(campus, categoryId);
        uiModel.addAttribute("events", eventList);
        Category category;
        if (eventList != null && eventList.size() > 0) {
            category = eventList.get(0).getCategory();
        } else {
            category = eventsService.getCategory(campus, categoryId);
        }
        if (category == null) {
            LOG.error("Couldn't find category for categoryId - " + categoryId);
            category = new CategoryImpl();
            category.setCategoryId(categoryId);
            category.setTitle(categoryId);
        }
        uiModel.addAttribute("category", category);
        uiModel.addAttribute("campus", campus);
        return "events/eventsList";
    }

    @RequestMapping(value = "/viewEvent", method = RequestMethod.GET)
    public String viewEvent(HttpServletRequest request, Model uiModel, @RequestParam(required = true) String categoryId, @RequestParam(required = false) String campus, @RequestParam(required = true) String eventId) throws Exception {
        //Event event = eventsService.getEvent(campus, categoryId, eventId);
        //uiModel.addAttribute("event", event);
        uiModel.addAttribute("event", eventId);
        uiModel.addAttribute("categoryId", categoryId);
        uiModel.addAttribute("campus", campus);
        return "events/detail";
    }

    @RequestMapping(value = "/viewEvent", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public String getEventJson(@RequestParam(required = true) String eventId) {
        return eventsService.getEventJson(eventId);
    }

    //Saket's addition
    //Not used currently. Reserved for future. Might have few bugs. 
    @RequestMapping(value = "/viewEventsCurrentDate", method = RequestMethod.GET)
    public String viewEventsCurrentDate(HttpServletRequest request, Model uiModel, @RequestParam(required = true) String categoryId, @RequestParam(required = false) String campus, @RequestParam(required = true) Date dateCurrent) throws Exception {
        List<Event> eventList = eventsService.getAllEventsByDateCurrent(campus, categoryId, dateCurrent);

        uiModel.addAttribute("events", eventList);

        Category category;
        if (eventList != null && eventList.size() > 0) {
            category = eventList.get(0).getCategory();
        } else {
            category = eventsService.getCategory(campus, categoryId);
        }
        if (category == null) {
            LOG.error("Couldn't find category for categoryId - " + categoryId);
            category = new CategoryImpl();
            category.setCategoryId(categoryId);
            category.setTitle(categoryId);
        }
        uiModel.addAttribute("category", category);
        uiModel.addAttribute("campus", campus);
        return "events/eventsList";
    }

    //Not used currently. Reserved for future. Might have few bugs. 
    @RequestMapping(value = "/viewEventsByDateFromTo", method = RequestMethod.GET)
    public String viewEventsByDateFromTo(HttpServletRequest request, Model uiModel, @RequestParam(required = true) String categoryId, @RequestParam(required = false) String campus, @RequestParam(required = true) Date from, @RequestParam(required = true) Date to) throws Exception {
        List<Event> eventList = eventsService.getAllEventsByDateFromTo(campus, categoryId, from, to);
        uiModel.addAttribute("events", eventList);
        Category category;
        if (eventList != null && eventList.size() > 0) {
            category = eventList.get(0).getCategory();
        } else {
            category = eventsService.getCategory(campus, categoryId);
        }
        if (category == null) {
            LOG.error("Couldn't find category for categoryId - " + categoryId);
            category = new CategoryImpl();
            category.setCategoryId(categoryId);
            category.setTitle(categoryId);
        }
        uiModel.addAttribute("category", category);
        uiModel.addAttribute("campus", campus);
        return "events/eventsList";
    }

    //Not used currently. Reserved for future. Might have few bugs.  
    @RequestMapping(value = "/viewEventsDateSpecific", method = RequestMethod.GET)
    public String viewEventsDateSpecific(HttpServletRequest request, Model uiModel, @RequestParam(required = true) String categoryId, @RequestParam(required = false) String campus, @RequestParam(required = true) String dateSpecific) throws Exception {
        List<Event> eventList = eventsService.getAllEventsByDateSpecific(campus, categoryId, dateSpecific);
        uiModel.addAttribute("events", eventList);
        Category category;
        if (eventList != null && eventList.size() > 0) {
            category = eventList.get(0).getCategory();
        } else {
            category = eventsService.getCategory(campus, categoryId);
        }
        if (category == null) {
            LOG.error("Couldn't find category for categoryId - " + categoryId);
            category = new CategoryImpl();
            category.setCategoryId(categoryId);
            category.setTitle(categoryId);
        }
        uiModel.addAttribute("category", category);
        uiModel.addAttribute("campus", campus);

        return "events/eventsList";
    }
}

//
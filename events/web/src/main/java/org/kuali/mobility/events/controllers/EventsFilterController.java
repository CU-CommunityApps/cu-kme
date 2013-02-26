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

import java.text.ParseException;
import java.text.*;
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

// Disable for initial Cornell deployment
// @Controller
// @RequestMapping("/events")
public class EventsFilterController {

    private static org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(EventsFilterController.class);
    private EventsService eventsService;
    private EventsDaoUMImpl eventsUMService = new EventsDaoUMImpl();
    private List<Category> categories = new ArrayList<Category>();
    private String campus = null;

    public void setEventsService(EventsService eventsService) {
        this.eventsService = eventsService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String homePage(HttpServletRequest request, Model uiModel) throws ParseException {
        User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);

        if (user.getViewCampus() == null) {
            return "redirect:/campus?toolName=events";
        } else {
            campus = user.getViewCampus();
        }

        categories = eventsService.getCategoriesByCampus(campus);
        LOG.debug("Found " + categories.size() + " categories via local service for campus " + campus);
        uiModel.addAttribute("categories", categories);
        uiModel.addAttribute("campus", campus);

        //code for making the new home page of events
        HashMap<Category, List<Event>> mObj = new HashMap<Category, List<Event>>();


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d = new Date();
        String todayDate = sdf.format(new Date());

        uiModel.addAttribute("todayDate", todayDate);
        List<Event> eventList = new ArrayList<Event>();
        List<Event> eventListR = new ArrayList<Event>();

        Iterator itc = categories.iterator();
        while (itc.hasNext()) {
            Category obj = (Category) itc.next();
            eventList = eventsService.getAllEventsByDateSpecific(campus, obj.getCategoryId(), todayDate);
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
    
    @RequestMapping(value = "/byCategory", method = RequestMethod.GET)
    public String homeByCategory(HttpServletRequest request, Model uiModel) throws ParseException {
        LOG.debug("Found " + categories.size() + " categories via local service for campus " + campus);
        uiModel.addAttribute("categories", categories);
        uiModel.addAttribute("campus", campus);

        return "events/category";
    }
    
    @RequestMapping(value = "/byDateRange", method = RequestMethod.GET)
    public String homeByDateRange(HttpServletRequest request, Model uiModel) throws ParseException {
        
        return "events/dateRange";
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

    
    @RequestMapping(value = "/viewEventsByDateFromTo", method = RequestMethod.GET)
    public String viewEventsByDateFromTo(HttpServletRequest request, Model uiModel, @RequestParam(required = true) String dateFrom, @RequestParam(required = true) String dateTo) throws Exception {

        SortedMap<String, HashMap<Category, List<Event>>> mObjFT = new TreeMap<String, HashMap<Category, List<Event>>>();
        List<String> listOfDates = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateF = sdf.parse(dateFrom);
        Date dateT = sdf.parse(dateTo);
        //checking if the start date is smaller than the end date
        if (dateF.before(dateT)) {
            //getting the array list of dates from the range
            listOfDates = listOfDates(dateFrom, dateTo);
            Iterator ite = listOfDates.iterator();
            while (ite.hasNext()) {
                String fetchedDate = (String) ite.next();
                System.out.println(fetchedDate);
                //storing into map, each date as key and "list of events grouped by category" as value pair
                mObjFT.put(fetchedDate, supportViewEventsDateSpecific(fetchedDate, categories));
            }
            uiModel.addAttribute("eventDateFromTo", mObjFT);
        }
              
        return "events/eventsListDateRange";
    }

    @RequestMapping(value = "/viewEventsDateSpecific", method = RequestMethod.GET)
    public String viewEventsDateSpecific(HttpServletRequest request, Model uiModel, @RequestParam(required = true) String dateSpecific) throws Exception {

        //code for making the new home page of events
        HashMap<Category, List<Event>> mObj = new HashMap<Category, List<Event>>();
        uiModel.addAttribute("todayDate", dateSpecific);
        uiModel.addAttribute("groupByCat", supportViewEventsDateSpecific(dateSpecific, categories));

        return "events/list";
    }

    @RequestMapping(value = "/nextDate", method = RequestMethod.GET)
    public String getNextDate(HttpServletRequest request, Model uiModel, @RequestParam(required = true) String currentDate) throws Exception {
        int MILLIS_IN_DAY = 1000 * 60 * 60 * 24;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(currentDate);

        String nextDate = sdf.format(date.getTime() + MILLIS_IN_DAY);

        return "redirect:/campus?toolName=events/viewEventsDateSpecific?dateSpecific=" + nextDate;
    }

    @RequestMapping(value = "/previousDate", method = RequestMethod.GET)
    public String previousDate(HttpServletRequest request, Model uiModel, @RequestParam(required = true) String currentDate) throws Exception {
        int MILLIS_IN_DAY = 1000 * 60 * 60 * 24;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(currentDate);
        String previousDate = sdf.format(date.getTime() - MILLIS_IN_DAY);

        return "redirect:/campus?toolName=events/viewEventsDateSpecific?dateSpecific=" + previousDate;
    }

    //support for getting the list of all events grouped by Category
    public HashMap<Category, List<Event>> supportViewEventsDateSpecific(String dte, List<Category> categories) {
        HashMap<Category, List<Event>> mObj = new HashMap<Category, List<Event>>();

        List<Event> eventList = new ArrayList<Event>();
        List<Event> eventListR = new ArrayList<Event>();

        Iterator itc = categories.iterator();
        while (itc.hasNext()) {
            Category obj = (Category) itc.next();
            eventList = eventsService.getAllEventsByDateSpecific(campus, obj.getCategoryId(), dte);
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
        
        return mObj;
    }

    public List<String> listOfDates(String from, String to) throws Exception {
        List<String> lst = new ArrayList<String>();
        int MILLIS_IN_DAY = 1000 * 60 * 60 * 24;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date dateF = sdf.parse(from);
        Date dateT = sdf.parse(to);
        
        while (dateF.equals(dateT) || dateF.before(dateT)) {
            lst.add(from);
            from = sdf.format(dateF.getTime() + MILLIS_IN_DAY);
            dateF = sdf.parse(from);
        }

        return lst;
    }
}

//
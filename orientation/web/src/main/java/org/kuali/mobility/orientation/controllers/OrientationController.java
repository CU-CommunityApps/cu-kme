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

package org.kuali.mobility.conference.controllers;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.kuali.mobility.orientation.entity.Attendee;
import org.kuali.mobility.orientation.entity.ContentBlock;
import org.kuali.mobility.orientation.entity.MenuItem;
import org.kuali.mobility.orientation.entity.Session;
import org.kuali.mobility.orientation.entity.SessionFeedback;
import org.kuali.mobility.orientation.service.OrientationService;
import org.kuali.mobility.email.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import flexjson.JSONSerializer;

@Controller
@RequestMapping("/orientation")
public class OrientationController {

	@Autowired
	private OrientationService orientationService;

	@Autowired
	private EmailService emailService;

	public void setOrientationService(OrientationService orientationService) {
		this.orientationService = orientationService;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String index(Model uiModel) {
		Date d = new Date();
		DateFormat formatter = new SimpleDateFormat("MMddyy");
		String today = formatter.format(d);
		if ("073112".equals(today) || "080112".equals(today) || "093011".equals(today) || "100111".equals(today)) {
		} else { 
			today = "";
		}
		//List<MenuItem> menuItems = conferenceService.findAllMenuItems();
		//uiModel.addAttribute("menuItems", menuItems);
		//uiModel.addAttribute("today", today);
		return "orientation/index";
	}
	
    @RequestMapping(method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public String getMenuJson(@RequestParam(value="lang", required = false) String lang) {
    	List<MenuItem> menuItems = orientationService.findAllMenuItems(lang);
    	return new JSONSerializer().exclude("*.class").deepSerialize(menuItems);
    }

	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	public String welcome(Model uiModel) {
		List<ContentBlock> contentBlocks = orientationService.findAllContentBlocks();
		uiModel.addAttribute("contentBlocks", contentBlocks);
		return "orientation/welcome";
	}
	
//	@RequestMapping(value = "/featuredSpeakers", method = RequestMethod.GET)
//	public String featuredSpeakers(Model uiModel) {
//		List<ContentBlock> contentBlocks = conferenceService.findFeaturedSpeakers();
//		uiModel.addAttribute("contentBlocks", contentBlocks);
//		return "conference/featuredSpeakers";
//	}
	
	@RequestMapping(value = "/attendeeGroups", method = RequestMethod.GET)
	public String attendeeGroups(Model uiModel) {
		return attendees("A", "Z", uiModel);
		//return "conference/attendeeGroups";
	}

	@RequestMapping(value = "/attendees", method = RequestMethod.GET)
	public String attendees(@RequestParam(value="start", required=true) String start, @RequestParam(value="end", required=true) String end, Model uiModel) {
		List<Attendee> attendees = orientationService.findAllAttendees(start.charAt(0), end.charAt(0));
		uiModel.addAttribute("attendees", attendees);
		return "orientation/attendees";
	}

	@RequestMapping(value = "/attendeeDetails/{id}", method = RequestMethod.GET)
	public String attendeeDetails(@PathVariable("id") String id, Model uiModel) {
		Attendee attendee = orientationService.findAttendeeById(id);
		uiModel.addAttribute("attendee", attendee);
		return "orientation/attendeeDetails";
	}

	@RequestMapping(value = "/sessions", method = RequestMethod.GET)
	public String sessions(@RequestParam(value="date", required=false) String date, Model uiModel) {
		List<Session> sessions = orientationService.findAllSessions(date);
		uiModel.addAttribute("sessions", sessions);
		return "orientation/sessions";
	}

	@RequestMapping(value = "/sessionDetails/{id}", method = RequestMethod.GET)
	public String sessionDetails(@PathVariable("id") String id, Model uiModel) throws UnsupportedEncodingException {
		Session session = orientationService.findSessionById(id);
		uiModel.addAttribute("session", session);
		uiModel.addAttribute("sessionFeedback", new SessionFeedback());
		return "orientation/sessionDetails";
	}

	@RequestMapping(value = "/twitter", method = RequestMethod.GET)
	public String twitter(Model uiModel) {
		return "orientation/twitter";
	}

	@RequestMapping(value = "/sessionFeedback", method = RequestMethod.POST)
	public String submitFeedback(Model uiModel, @ModelAttribute("sessionFeedback") SessionFeedback sessionFeedback) {
		sessionFeedback.setTimePosted(new Timestamp(System.currentTimeMillis()));
	//	sendEmail(sessionFeedback);
		return "orientation/sessionFeedbackThanks";
	}

	@RequestMapping(value = "/sessionSpeakerDetails", method = RequestMethod.GET)
	public String sessionSpeakerDetails(Model uiModel, @RequestParam(required = true) int id) {
		List<Session> sessions = orientationService.findAllSessions("");
		uiModel.addAttribute("session", sessions.get(id));
		return "orientation/sessionSpeakerDetails";
	}

//	private void sendEmail(SessionFeedback f) {
//		try {
//			emailService.sendEmail(f.toString(), "SWITC Feedback; "+f.getSessionId()+":"+f.getRating(), conferenceService.getToEmailAddress(), conferenceService.getFromEmailAddress());
//		} catch (Exception e) {}
//	}

}

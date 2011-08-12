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
 
package org.kuali.mobility.mdot.controllers;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.kuali.mobility.admin.entity.HomeScreen;
import org.kuali.mobility.admin.entity.HomeTool;
import org.kuali.mobility.admin.entity.Tool;
import org.kuali.mobility.admin.service.AdminService;
import org.kuali.mobility.mdot.entity.Backdoor;
import org.kuali.mobility.shared.Constants;
import org.kuali.mobility.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller 
@RequestMapping("/")
public class HomeController {

	private static org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(HomeController.class);
	
	@Autowired
    private AdminService adminService;
    public void setAdminService(AdminService adminService) {
        this.adminService = adminService;
    }
	
    @RequestMapping(value = "home", method = RequestMethod.GET)
    public String getList(HttpServletRequest request, Model uiModel) {      
        uiModel.addAttribute("home", buildHomeScreen(request));    	
    	return "index";
    }

    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request, Model uiModel) {      
    	return "logout";
    }

    @RequestMapping(value = "yesLogout", method = RequestMethod.GET)
    public void fullLogout(HttpServletRequest request, HttpServletResponse response, Model uiModel) {      
    	request.getSession().setAttribute(Constants.KME_USER_KEY, null);
    	request.getSession().setAttribute(Constants.KME_BACKDOOR_USER_KEY, null);
    	request.getSession().removeAttribute("edu.iu.uis.cas.filter.CASAuthenticationMap");
    	try {
			response.sendRedirect("https://cas-test.iu.edu/cas/logout");
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		}
    }

    /*
    @RequestMapping(value = "home.json", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public String getHomeScreenJson() {
    	return new JSONSerializer().exclude("*.class").include("tools").serialize(buildHomeScreen());
    }
    */

    private HomeScreen buildHomeScreen(HttpServletRequest request) {
    	User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);
    	Backdoor backdoor = (Backdoor) request.getSession().getAttribute(Constants.KME_BACKDOOR_USER_KEY);
    	 
//    	List<Tool> tools = new ArrayList<Tool>();
//    	tools = adminService.getAllTools();
    	
    	HomeScreen home = new HomeScreen();
    	home = adminService.getHomeScreenByName("UA");
    	HomeScreen campusHome = adminService.getHomeScreenByName(user.getViewCampus());
    	if (campusHome != null) {
    		home.getHomeTools().addAll(campusHome.getHomeTools());
    	}
    	
//    	Tool tool = new Tool();
//    	tool.setBadgeCount("");
//    	tool.setDescription("Class information; Oncourse  forums, resources, and more!");
//    	tool.setIconUrl("images/service-icons/srvc-myclasses.png");
//    	tool.setTitle("My Classes");
//    	tool.setUrl("myclasses");
//    	tools.add(tool);
//
    	Tool tool = new Tool();
    	if (backdoor != null) {
    		tool.setBadgeCount(backdoor.getUserId());
    	} else {
    		tool.setBadgeCount("");
    	}
    	tool.setDescription("Impersonate a user.");
    	tool.setIconUrl("images/service-icons/srvc-backdoor.png");
    	tool.setTitle("Backdoor");
    	tool.setUrl("backdoor");
    	home.getHomeTools().add(new HomeTool(home, tool, home.getHomeTools().size()));
//    	
//    	/*
//    	tool = new Tool();
//    	tool.setBadgeCount("");
//    	tool.setDescription("Find furniture, books, an apartment, a job, and more.");
//    	tool.setIconUrl("images/service-icons/srvc-classifieds.png");
//    	tool.setTitle("Classifieds");
//    	tool.setUrl("https://onestart.iu.edu/ccf2-prd/ClassifiedsMb.do");
//    	tools.add(tool);
//		*/
//    	
//    	tool = new Tool();
//    	tool.setBadgeCount("");
//    	tool.setDescription("Never miss an IU Bloomington campus bus again.");
//    	tool.setIconUrl("images/service-icons/srvc-bus.png");
//    	tool.setTitle("Bus Schedules");
//    	tool.setUrl("http://iub.doublemap.com/map/mobile");
//    	tools.add(tool);
//
//    	tool = new Tool();
//    	tool.setBadgeCount("");
//    	tool.setDescription("Get from here to there. Search for buildings by name.");
//    	tool.setIconUrl("images/service-icons/srvc-maps.png");
//    	tool.setTitle("Maps");
//    	tool.setUrl("maps");
//    	tools.add(tool);
//
//    	tool = new Tool();
//    	tool.setBadgeCount("");
//    	tool.setDescription("Live scores, rosters, news and schedules for your IU teams.");
//    	tool.setIconUrl("images/service-icons/srvc-athletics.png");
//    	tool.setTitle("Athletics");
//    	tool.setUrl("athletics");
//    	tools.add(tool);
//
//    	tool = new Tool();
//    	tool.setBadgeCount("");
//    	tool.setDescription("Stay up to date with the IU social media.");
//    	tool.setIconUrl("images/service-icons/srvc-social.png");
//    	tool.setTitle("Social Media");
//    	tool.setUrl("socialmedia");
//    	tools.add(tool);
//
//    	tool = new Tool();
//    	tool.setBadgeCount("");
//    	tool.setDescription("Find contact information for IU students, faculty, and staff.");
//    	tool.setIconUrl("images/service-icons/srvc-people.png");
//    	tool.setTitle("People");
//    	tool.setUrl("people");
//    	tools.add(tool);
//
//    	tool = new Tool();
//    	tool.setBadgeCount("");
//    	tool.setDescription("See which campus STC labs have an open computer.");
//    	tool.setIconUrl("images/service-icons/srvc-stc.png");
//    	tool.setTitle("Computer Labs");
//    	tool.setUrl("computerlabs");
//    	tools.add(tool);
//
//    	tool = new Tool();
//    	tool.setBadgeCount("");
//    	tool.setDescription("Alerts and announcements affecting your technology.");
//    	tool.setIconUrl("images/service-icons/srvc-itnotice.png");
//    	tool.setTitle("IT Notices");
//    	tool.setUrl("itnotices");
//    	tools.add(tool);
//
//    	tool = new Tool();
//    	tool.setBadgeCount("");
//    	tool.setDescription("The latest buzz on IU's exciting events and achievements.");
//    	tool.setIconUrl("images/service-icons/srvc-news.png");
//    	tool.setTitle("News");
//    	tool.setUrl("news");
//    	tools.add(tool);
//
//    	tool = new Tool();
//    	tool.setBadgeCount("");
//    	tool.setDescription("See what's happening on campus today or your personal calendar.");
//    	tool.setIconUrl("images/service-icons/srvc-events.png");
//    	tool.setTitle("Calendar");
//    	tool.setUrl("events");
//    	tools.add(tool);
//
//    	tool = new Tool();
//    	tool.setBadgeCount("");
//    	tool.setDescription("Find answers to questions about IU information technology.");
//    	tool.setIconUrl("images/service-icons/srvc-kb.png");
//    	tool.setTitle("Knowledge Base");
//    	tool.setUrl("knowledgebase");
//    	tools.add(tool);
//
//    	tool = new Tool();
//    	tool.setBadgeCount("");
//    	tool.setDescription("Take IU's popular question & answer service with you on the go.");
//    	tool.setIconUrl("images/service-icons/srvc-askiu.png");
//    	tool.setTitle("Ask IU");
//    	tool.setUrl("askiu");
//    	tools.add(tool);
//
//    	tool = new Tool();
//    	tool.setBadgeCount("");
//    	tool.setDescription("Get up to date menus and prices for campus dining services.");
//    	tool.setIconUrl("images/service-icons/srvc-dining.png");
//    	tool.setTitle("Dining Services");
//    	tool.setUrl("dining");
//    	tools.add(tool);
//
//    	tool = new Tool();
//    	tool.setBadgeCount("");
//    	tool.setDescription("Police and medical phone numbers.");
//    	tool.setIconUrl("images/service-icons/srvc-emergency.png");
//    	tool.setTitle("Emergency Contacts");
//    	tool.setUrl("emergencycontacts");
//    	tools.add(tool);
//
//    	tool = new Tool();
//    	tool.setBadgeCount("2");
//    	tool.setDescription("See a list of active campus alert messages.");
//    	tool.setIconUrl("images/service-icons/srvc-alerts-green.png");
//    	tool.setTitle("Campus Alerts");
//    	tool.setUrl("alerts");
//    	tools.add(tool);
//
//    	tool = new Tool();
//    	tool.setBadgeCount("");
//    	tool.setDescription("Submit questions and comments about IU Mobile.");
//    	tool.setIconUrl("images/service-icons/srvc-feedback.png");
//    	tool.setTitle("Feedback");
//    	tool.setUrl("feedback");
//    	tools.add(tool);
//
//    	tool = new Tool();
//    	tool.setBadgeCount("");
//    	tool.setDescription("IUPUI Athletics information.");
//    	tool.setIconUrl("images/service-icons/srvc-jag.png");
//    	tool.setTitle("Jaguar Athletics");
//    	tool.setUrl("http://www.iupuijags.com");
//    	tools.add(tool);
    	
//		if (user.getViewCampus() != null && user.getViewCampus().equals("NW")) {
//			tool = new Tool();
//			tool.setBadgeCount("");
//			tool.setDescription("Class Cancellations");
//			tool.setIconUrl("images/service-icons/srvc-jag.png");
//			tool.setTitle("Class Cancellations");
//			tool.setUrl("events/viewEvents?categoryId=nw_cancel&campus=ZZ");
//			tools.add(tool);
//
//			tool = new Tool();
//			tool.setBadgeCount("");
//			tool.setDescription("RedHawk Shuttle");
//			tool.setIconUrl("images/service-icons/srvc-jag.png");
//			tool.setTitle("RedHawk Shuttle");
//			tool.setUrl("events/viewEvents?categoryId=nw_shuttle&campus=ZZ");
//			tools.add(tool);
//		}
    	tool = new Tool();
    	tool.setBadgeCount("");
    	if (user == null || user.isPublicUser()) {
	    	tool.setDescription("Log in to Mobile CAS.");
	    	tool.setIconUrl("images/service-icons/srvc-cas.png");
	    	tool.setTitle("Login");
	    	tool.setUrl("home?login=yes");
    	} else {
	    	tool.setDescription("Log out out of Mobile CAS.");
	    	tool.setIconUrl("images/service-icons/srvc-cas.png");
	    	tool.setTitle("Logout");
	    	tool.setUrl("logout");
    	}
    	home.getHomeTools().add(new HomeTool(home, tool, home.getHomeTools().size()));
    	
    	Collections.sort(home.getHomeTools());
    	
    	return home;
    }
    
}

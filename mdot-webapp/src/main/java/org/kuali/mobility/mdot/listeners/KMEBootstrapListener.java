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

package org.kuali.mobility.mdot.listeners;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContextEvent;

import org.kuali.mobility.admin.entity.HomeScreen;
import org.kuali.mobility.admin.entity.HomeTool;
import org.kuali.mobility.admin.entity.Tool;
import org.kuali.mobility.admin.service.AdminService;
import org.kuali.mobility.shared.Wrapper;
import org.kuali.mobility.shared.listeners.BootstrapListener;
import org.springframework.context.ApplicationContext;

public class KMEBootstrapListener extends BootstrapListener {
	
	@Override
	public HomeScreen bootstrapHomeScreenTools(ServletContextEvent event, AdminService adminService) {

		ApplicationContext ctx = org.springframework.web.context.support.WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
		Wrapper useBootstrapping = (Wrapper) ctx.getBean("useBootstrappingFlag");

		if (!"true".equals(useBootstrapping.getValue())) {
			return null;
		}
		
		HomeScreen home = new HomeScreen();
		home.setAlias("PUBLIC");
		home.setTitle("Cornell Mobile - Test Site");
		adminService.saveHomeScreen(home);

		List<HomeTool> tools = new ArrayList<HomeTool>();
        Tool tool = new Tool();
        tool = new Tool();
        HomeTool ht = null ;
        int iIndex = -1 ;

        tool = new Tool();
        tool.setAlias("    Cast    ");
        tool.setTitle("CornellCast");
        tool.setUrl("http://www.cornell.edu/video/mobile");
        tool.setDescription("Now playing on CornellCast.");
        tool.setIconUrl("images/service-icons/srvc-cornellcast.png");
        adminService.saveTool(tool);
        ht = new HomeTool(home, tool, iIndex++);
        tools.add(ht);      

        tool = new Tool();
        tool.setAlias("    NOW     ");
        tool.setTitle("Cornell NOW");
        tool.setUrl("http://now.cornell.edu/mobile");
        tool.setDescription("Cornell NOW 2015.");
        tool.setIconUrl("images/service-icons/srvc-cuathletics.png");
        adminService.saveTool(tool);
        ht = new HomeTool(home, tool, iIndex++);
        tools.add(ht);      

        tool = new Tool();
        tool.setAlias("   People   ");
        tool.setTitle("People");
        tool.setUrl("people");
        tool.setDescription("Search the Cornell Directory for People.");
        tool.setIconUrl("images/service-icons/srvc-people.png");
        adminService.saveTool(tool);
        ht = new HomeTool(home, tool, iIndex++);
        tools.add(ht);      

        tool = new Tool();
		tool.setAlias("    News    ");
		tool.setTitle("News");
		tool.setUrl("news");
		tool.setDescription("The latest buzz on Cornell's exciting events and achievements.");
		tool.setIconUrl("images/service-icons/srvc-news.png");
		adminService.saveTool(tool);
		ht = new HomeTool(home, tool, iIndex++);
		tools.add(ht);

        tool = new Tool();
        tool.setAlias("   Dining   ");
        tool.setTitle("Dining");
        tool.setUrl("dining");
        tool.setDescription("Menus for \"All You Care to Eat\" facilities.");
        tool.setIconUrl("images/service-icons/srvc-dining.png");
        adminService.saveTool(tool);
        ht = new HomeTool(home, tool, iIndex++);
        tools.add(ht);
  
        tool = new Tool();
        tool.setAlias("  Athletics ");
        tool.setTitle("Athletics");
        tool.setUrl("http://www.cornellbigred.com/mobile");
        tool.setDescription("Cornell University Athletics");
        tool.setIconUrl("images/service-icons/srvc-cuathletics.png");
        adminService.saveTool(tool);
        ht = new HomeTool(home, tool, iIndex++);
        tools.add(ht);

		tool = new Tool();
		tool.setAlias("    Labs    ");
		tool.setTitle("Computer Labs");
		tool.setUrl("computerlabs");
		tool.setDescription("See which computer labs have an available computer.");
		tool.setIconUrl("images/service-icons/srvc-stc.png");
		adminService.saveTool(tool);
		ht = new HomeTool(home, tool, iIndex++);
		tools.add(ht);

        tool = new Tool();
		tool.setAlias("    Maps    ");
		tool.setTitle("Maps");
		tool.setUrl("maps");
		tool.setDescription("Get from here to there. Search for buildings by name.");
		tool.setIconUrl("images/service-icons/srvc-maps.png");
		adminService.saveTool(tool);
		ht = new HomeTool(home, tool, iIndex++);
		tools.add(ht);

        tool = new Tool();
		tool.setAlias("  Emergency ");
		tool.setTitle("Emergency Contacts");
		tool.setUrl("emergencycontacts");
		tool.setDescription("Emergency and Police phone numbers.");
		tool.setIconUrl("images/service-icons/srvc-emergency.png");
		adminService.saveTool(tool);
		ht = new HomeTool(home, tool, iIndex++);
		tools.add(ht);

        tool = new Tool();
        tool.setAlias("   Weather  ");
        tool.setTitle("Weather");
        // tool.setUrl("weather");
        tool.setUrl("http://mobile.weather.gov/index.php?lat=42.435511&lon=-76.52570070000002");
        tool.setDescription("Weather in the Ithaca area.");
//        tool.setIconUrl("images/service-icons/srvc-weather.png");
        tool.setIconUrl("images/service-icons/srvc-weather.png");
        adminService.saveTool(tool);
        ht = new HomeTool(home, tool, iIndex++);
        tools.add(ht);

		/*
        tool = new Tool();
        tool.setAlias("orientation");
        tool.setTitle("Orientation");
        tool.setUrl("orientation");
        tool.setDescription("Fall 2012 New Student Orientation<br /><em>Let the Journey Begin!</em>");
		tool.setIconUrl("images/service-icons/books.png");
        adminService.saveTool(tool);
        ht = new HomeTool(home, tool, iIndex++);
        tools.add(ht);

        tool.setAlias("bus");
        tool.setTitle("Bus - Hard coded test data.");
        tool.setUrl("bus");
        tool.setDescription("Uses KME provided hard coded data, but can peruse features.");
        tool.setIconUrl("images/service-icons/srvc-bus.png");
        adminService.saveTool(tool);
        ht = new HomeTool(home, tool, iIndex++);
        tools.add(ht);

		tool = new Tool();
		tool.setAlias("conference");
		tool.setTitle("Conference");
		tool.setUrl("conference");
		tool.setDescription("2011 Conference: \"My digital life @ IU\"");
		tool.setIconUrl("images/service-icons/srvc-itstatewide.png");
		adminService.saveTool(tool);
		ht = new HomeTool(home, tool, iIndex++);
		tools.add(ht);

        tool = new Tool();
        tool.setAlias("events");
        tool.setTitle("Events");
        tool.setUrl("events");
        tool.setDescription("Campus events and calendars.");
        tool.setIconUrl("images/service-icons/srvc-events.png");
        adminService.saveTool(tool);
        ht = new HomeTool(home, tool, iIndex++);
        tools.add(ht);      

        tool = new Tool();
        tool.setAlias("alerts");
        tool.setTitle("Campus Alerts");
        tool.setUrl("alerts");
        tool.setDescription("See a list of active campus alert messages.");
        tool.setIconUrl("images/service-icons/srvc-alerts-green.png");
        adminService.saveTool(tool);
        ht = new HomeTool(home, tool, iIndex++);
        tools.add(ht);

        tool = new Tool();
        tool.setAlias("incident");
        tool.setTitle("Incident Reporting");
        tool.setUrl("reporting/incidentForm");
        tool.setDescription("Submit campus incidents.");
        tool.setIconUrl("images/service-icons/srvc-incident-green.png");
        adminService.saveTool(tool);
        ht = new HomeTool(home, tool, iIndex++);
        tools.add(ht);      

        tool = new Tool();
        tool.setAlias("reportingadmin");
        tool.setTitle("Reporting Admin");
        tool.setUrl("reporting/admin/index");
        tool.setDescription("Reporting administration.");
        tool.setIconUrl("images/service-icons/srvc-incident-admin.png");
        adminService.saveTool(tool);
        ht = new HomeTool(home, tool, iIndex++);
        tools.add(ht);      		
		
		tool = new Tool();
		tool.setAlias("push");
    	tool.setTitle("Push");
    	tool.setUrl("push");
    	tool.setDescription("Administrative push notifications tool.");
    	tool.setIconUrl("images/service-icons/srvc-push.png");
		adminService.saveTool(tool);
		ht = new HomeTool(home, tool, iIndex++);
		tools.add(ht);		

        tool = new Tool();
        tool.setAlias("publishing");
        tool.setTitle("Publishing");
        tool.setUrl("publishing/index");
        tool.setDescription("Administrative publishing tools.");
        tool.setIconUrl("images/service-icons/srvc-publishing.png");
        adminService.saveTool(tool);
        ht = new HomeTool(home, tool, iIndex++);
        tools.add(ht);      

        tool = new Tool();
        tool.setAlias("feedback");
        tool.setTitle("Feedback");
        tool.setUrl("feedback");
        tool.setDescription("Submit questions and comments about Kuali Mobile.");
        tool.setIconUrl("images/service-icons/srvc-feedback.png");
        adminService.saveTool(tool);
        ht = new HomeTool(home, tool, iIndex++);
        tools.add(ht);      

		tool = new Tool();
		tool.setAlias("backdoor");
    	tool.setTitle("Backdoor");
    	tool.setUrl("backdoor");
    	tool.setDescription("Impersonate a user.");
    	tool.setIconUrl("images/service-icons/srvc-backdoor.png");
		adminService.saveTool(tool);
		ht = new HomeTool(home, tool, 14);
		tools.add(ht);		
        */
		
		home.setHomeTools(tools);
		return home;
	}
	
}

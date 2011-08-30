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
import javax.servlet.ServletContextListener;

import org.kuali.mobility.admin.entity.HomeScreen;
import org.kuali.mobility.admin.entity.HomeTool;
import org.kuali.mobility.admin.entity.Tool;
import org.kuali.mobility.admin.service.AdminService;
import org.kuali.mobility.configparams.entity.ConfigParam;
import org.kuali.mobility.configparams.service.ConfigParamService;
import org.springframework.context.ApplicationContext;

public class BootstrapListener implements ServletContextListener {

	private static org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(BootstrapListener.class);

	public void contextInitialized(ServletContextEvent event) {
		LOG.info("BootstrapListener started initializing...");

		ApplicationContext ctx = org.springframework.web.context.support.WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());

		AdminService adminService = (AdminService) ctx.getBean("AdminService");

		HomeScreen home = new HomeScreen();
		home.setAlias("PUBLIC");
		home.setTitle("Kuali Mobile");
		adminService.saveHomeScreen(home);

		List<HomeTool> tools = new ArrayList<HomeTool>();
		
		Tool tool = new Tool();
		tool.setAlias("myclasses");
		tool.setTitle("My Classes");
		tool.setUrl("myclasses");
		tool.setDescription("Class information; Oncourse  forums, resources, and more!");
		tool.setIconUrl("images/service-icons/srvc-myclasses.png");
		adminService.saveTool(tool);
		HomeTool ht = new HomeTool(home, tool, 1);
		tools.add(ht);
		
		tool = new Tool();
		tool.setAlias("maps");
		tool.setTitle("Maps");
		tool.setUrl("maps");
		tool.setDescription("Get from here to there. Search for buildings by name.");
		tool.setIconUrl("images/service-icons/srvc-maps.png");
		adminService.saveTool(tool);
		ht = new HomeTool(home, tool, 2);
		tools.add(ht);

		tool = new Tool();
		tool.setAlias("labs");
		tool.setTitle("Computer Labs");
		tool.setUrl("computerlabs");
		tool.setDescription("See which campus computers labs have an open seat.");
		tool.setIconUrl("images/service-icons/srvc-stc.png");
		adminService.saveTool(tool);
		ht = new HomeTool(home, tool, 3);
		tools.add(ht);

		tool = new Tool();
		tool.setAlias("alerts");
		tool.setTitle("Campus Alerts");
		tool.setUrl("alerts");
		tool.setDescription("See a list of active campus alert messages.");
		tool.setIconUrl("images/service-icons/srvc-alerts-green.png");
		adminService.saveTool(tool);
		ht = new HomeTool(home, tool, 4);
		tools.add(ht);

		home.setHomeTools(tools);
		
		adminService.saveHomeScreen(home);

		ConfigParamService configParamService = (ConfigParamService) ctx.getBean("ConfigParamService");

		ConfigParam param = new ConfigParam();
		param.setName("Admin.Group.Name");
		param.setValue("MIU-ADMINISTRATORS");
		configParamService.saveConfigParam(param);

		param = new ConfigParam();
		param.setName("Backdoor.Group.Name");
		param.setValue("MIU-BACKDOOR-PRD");
		configParamService.saveConfigParam(param);

		param = new ConfigParam();
		param.setName("Sakai.Url.Base");
		param.setValue("https://regression.oncourse.iu.edu/oauthdirect/");
		configParamService.saveConfigParam(param);

		param = new ConfigParam();
		param.setName("Alerts.CacheUpdate.Minute");
		param.setValue("5");
		configParamService.saveConfigParam(param);

		param = new ConfigParam();
		param.setName("CAMPUS_STATUS_XML_URL");
		param.setValue("https://es-nd.ucs.indiana.edu:9000/my2-unt/DataExport.do?__p_dispatch__=campusStatus&campus=BL");
		configParamService.saveConfigParam(param);
		
		LOG.info("Count: " + adminService.getAllHomeScreens().size());
		
		LOG.info("BootstrapListener finished initializing.");
	}

	public void contextDestroyed(ServletContextEvent event) {}

}

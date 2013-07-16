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
 
package org.kuali.mobility.shared.controllers;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.spi.PersistenceUnitInfo;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.cfg.Configuration;
import org.hibernate.ejb.Ejb3Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.kuali.mobility.admin.entity.HomeScreen;
import org.kuali.mobility.admin.entity.HomeTool;
import org.kuali.mobility.admin.entity.Tool;
import org.kuali.mobility.admin.service.AdminService;
import org.kuali.mobility.alerts.service.AlertsService;
import org.kuali.mobility.campus.entity.Campus;
import org.kuali.mobility.campus.service.CampusService;
import org.kuali.mobility.configparams.service.ConfigParamService;
import org.kuali.mobility.security.authn.entity.User;
import org.kuali.mobility.shared.Constants;
import org.kuali.mobility.shared.CoreService;
import org.kuali.mobility.shared.entity.Backdoor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller 
@RequestMapping("/")
public class HomeController {

	private static org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(HomeController.class);
	
	@Resource(name="&entityManagerFactory")
	private LocalContainerEntityManagerFactoryBean entityManagerFactory;
	  
	@Autowired
    private AdminService adminService;

//  sedgar: commenting out the autowire, as we are not using Alerts right no in the CU implmentation.
//	@Autowired
    private AlertsService alertsService;

	@Autowired
	private ConfigParamService configParamService;
	
	@Autowired
	private CampusService campusService;
	
	@Autowired
	private CoreService coreService;
	
	private String homeScreenView = "index";  
    
	@RequestMapping(value = "home", method = RequestMethod.GET)
    public String home(HttpServletRequest request, Model uiModel) {      
    	buildHomeScreen(request, uiModel);
    	User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);
    	String ipAddress = "";
    	if (user.isMember(configParamService.findValueByName("Admin.Group.Name"))) {
    		try {
				ipAddress = "<p><i><small>Server: " + InetAddress.getLocalHost().getHostName() + "</small></i></p>";
			} catch (UnknownHostException e) {}
    	}
    	uiModel.addAttribute("ipAddress", ipAddress);

    	/*
    	String sIndexJsp = "index" ;
    	String sLayout = coreService.findLayout() ;
    	if (sLayout.equalsIgnoreCase("grid"))
    		sIndexJsp = "gridindex";
    	else if (sLayout.equalsIgnoreCase("dynamicgrid"))
    		sIndexJsp = "dynamicgridindex" ;
  
    	return sIndexJsp ;
    	*/

    return (getHomeScreenView()) ;
    }
/*
	@RequestMapping(value = "toggleView", method = RequestMethod.GET)
    public String toggleView(HttpServletRequest request, Model uiModel) {
    
    String sView = getHomeScreenView() ;
    if (sView.equalsIgnoreCase("index"))
    	sView = "dynamicgridindex" ;
    else
    	sView = "index" ;
    
   	setHomeScreenView(sView) ;
    return ("redirect:/home") ;
    }
*/

	// Disable for initial Cornell deployment
	/*
	@RequestMapping(value = "preferences", method = RequestMethod.GET)
    public String preferences(HttpServletRequest request, Model uiModel) {   
    	String homeToolName = "home";
		uiModel.addAttribute("toolName", homeToolName);
    	List<Campus> campuses = campusService.findCampusesByTool(homeToolName);
		uiModel.addAttribute("campuses", campuses);		
		List<HomeScreen> homeScreens = adminService.getAllHomeScreens();
		uiModel.addAttribute("homeScreens", homeScreens);
    	// return "preferences";
		return "preferences";
    }
    
    @RequestMapping(value = "kme.appcache", method = RequestMethod.GET)
    public String cachemanifest(HttpServletRequest request, HttpServletResponse response, Model uiModel) {      
    	//response.setContentType("text/cache-manifest");
    	return "cacheManifest";
    }
    
    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request, Model uiModel) {      
    	return "logout";
    }

    @RequestMapping(value = "qrcode", method = RequestMethod.GET)
    public String qrcode(HttpServletRequest request, Model uiModel) {      
    	return "qrcode";
    }    
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "ddl", method = RequestMethod.GET)
    public void exportDatabaseSchema(HttpServletRequest request, HttpServletResponse response, Model uiModel) {      
	    PersistenceUnitInfo persistenceUnitInfo = entityManagerFactory.getPersistenceUnitInfo();

	    Map jpaPropertyMap = entityManagerFactory.getJpaPropertyMap();	
	    jpaPropertyMap.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect"); 
	    Configuration configuration = new Ejb3Configuration().configure( persistenceUnitInfo, jpaPropertyMap ).getHibernateConfiguration();
	
	    SchemaExport schema = new SchemaExport(configuration);
	    schema.setFormat(true);
	    schema.setDelimiter(";");
	    schema.setOutputFile("/tmp/schema.sql");
  	    schema.create(false, false);
	}
    */

    private void buildHomeScreen(HttpServletRequest request, Model uiModel) {
    	User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);
    	Backdoor backdoor = (Backdoor) request.getSession().getAttribute(Constants.KME_BACKDOOR_USER_KEY);
    	 
    	// TODO: Add check for a different homescreen (multi-tenancy)
    	String alias = "PUBLIC";
    	HomeScreen home = new HomeScreen();
    	if (user != null && user.getViewCampus() != null) {
    		alias = user.getViewCampus();
    	} 
    	
    	home = adminService.getHomeScreenByAlias(alias);
    	if (home == null) {
    		home = adminService.getHomeScreenByAlias("PUBLIC");
    	}
    	    	
    	List<HomeTool> copy = new ArrayList<HomeTool>(home.getHomeTools());
    	Collections.sort(copy);
    	    	
    	for (HomeTool homeTool : copy) {
    		Tool tool = homeTool.getTool();
    		if ("alerts".equals(tool.getAlias())) {
    			int count = alertsService.findAlertCountByCampus(user.getViewCampus());
    			if (count > 0) {
    				tool.setBadgeCount(Integer.toString(count));
    				tool.setIconUrl("images/service-icons/srvc-alerts-red.png");
    			} else {
    				tool.setBadgeCount(null);
    				tool.setIconUrl("images/service-icons/srvc-alerts-green.png");
    			}
    		}
            if ("incident".equals(tool.getAlias()) || "reportingadmin".equals(tool.getAlias())) {
                tool.setBadgeText("beta");
            }
    		if ("backdoor".equals(tool.getAlias())) {
		    	if (backdoor != null) {
		    		tool.setBadgeCount(backdoor.getUserId());
		    	} else {
		    		tool.setBadgeCount("");
		    	}
    		}
    	}
    	
        uiModel.addAttribute("title", home.getTitle());    
    	uiModel.addAttribute("tools", copy);
    	//uiModel.addAttribute("layout", coreService.findLayout());
    }

	public String getHomeScreenView() {
	
	return homeScreenView;
	}

	public void setHomeScreenView(String homeScreenView) {
	
	this.homeScreenView = homeScreenView;
	}
}

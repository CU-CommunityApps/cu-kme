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

package org.kuali.mobility.computerlabs.controllers;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.kuali.mobility.computerlabs.entity.Location;
import org.kuali.mobility.computerlabs.service.ComputerLabsService;
import org.kuali.mobility.security.authn.entity.User;
import org.kuali.mobility.shared.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import flexjson.JSONSerializer;

@Controller
@RequestMapping("/computerlabs")
public class ComputerLabsController {

	private static org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(ComputerLabsController.class);

	@Autowired
	private ComputerLabsService computerLabService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String getList(Model uiModel, HttpServletRequest request) 
	{
		User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);
		//LOG.debug("uimodel user:"  + user);
		String campus = null;
		if (user.getViewCampus() == null) {
			//LOG.debug("campus null");
			return "redirect:/campus?toolName=computerlabs";
		} else {
			campus = user.getViewCampus();
			LOG.debug("Computerlabs campus different " + user.getViewCampus());
		}
		uiModel.addAttribute("campus", campus);
		return "computerlabs/list";
	
	}
	
	
	@RequestMapping(value = "/details", method = RequestMethod.GET)
	public String getViewSeatDetails(Model uiModel, HttpServletRequest request, @RequestParam(value = "buildingCode",required = true) String buildingCode,@RequestParam(required = false) String campus) {
		uiModel.addAttribute("buildingCode", buildingCode);
		uiModel.addAttribute("campus", campus);
		return "computerlabs/details";
	}
	
	@RequestMapping(value = "/details", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public String getViewSeatJson(@RequestParam(value = "buildingCode",required = true) String buildingCode,@RequestParam(value = "campus",required = false) String campus) {
		//LOG.debug("get view seat json" + campus + ", " + buildingCode );
		return  computerLabService.getViewSeatJson(buildingCode,campus);
	}
	

	@RequestMapping(method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public String findAllComputerLabsByCampus(@RequestParam(value = "campus", required = true) String campus, HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);
		//LOG.debug( "for campus "+campus );
		Collection<Location> labs = computerLabService.findAllLabsByCampus(user.getViewCampus());
		//LOG.debug( "Found JSON labs size "+labs.size());
		return new JSONSerializer().exclude("*.class").deepSerialize(labs);
	}
	

}

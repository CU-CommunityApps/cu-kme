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
 
package org.kuali.mobility.sakai.controllers;

import java.io.InputStream;
import java.util.List;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.kuali.mobility.configparams.service.ConfigParamService;
import org.kuali.mobility.sakai.entity.Announcement;
import org.kuali.mobility.sakai.entity.Assignment;
import org.kuali.mobility.sakai.entity.Home;
import org.kuali.mobility.sakai.entity.Resource;
import org.kuali.mobility.sakai.entity.Roster;
import org.kuali.mobility.sakai.entity.Site;
import org.kuali.mobility.sakai.service.SakaiSiteService;
import org.kuali.mobility.sakai.service.SakaiSessionService;
import org.kuali.mobility.shared.Constants;
import org.kuali.mobility.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import edu.iu.es.espd.oauth.OAuth2LegService;

@Controller
@RequestMapping("/myclasses")
public class SakaiController {

	private static org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(SakaiController.class);
	
	@Autowired
	private ConfigParamService configParamService;

	@Autowired
	private SakaiSessionService sakaiSessionService;

	@Autowired
	private OAuth2LegService oncourseOAuthService;
	
	@Autowired
	private SakaiSiteService sakaiSiteService;
	
	private static final MimetypesFileTypeMap mimeTypesMap;
	private static final String urlMimeType = "url";
	
	static {
		mimeTypesMap = new MimetypesFileTypeMap();
		
		mimeTypesMap.addMimeTypes("application/pdf pdf");
		mimeTypesMap.addMimeTypes("application/msword doc");
		mimeTypesMap.addMimeTypes("application/vnd.openxmlformats-officedocument.wordprocessingml.document docx");
		mimeTypesMap.addMimeTypes("application/vnd.ms-excel xla xls xlt xlw");
		mimeTypesMap.addMimeTypes("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet xlsx");
		mimeTypesMap.addMimeTypes("application/vnd.ms-powerpoint ppt pps");
		mimeTypesMap.addMimeTypes("pplication/vnd.openxmlformats-officedocument.presentationml.presentation pptx");
		mimeTypesMap.addMimeTypes(urlMimeType + " url URL Url");
	}

	@RequestMapping(method = RequestMethod.GET)
	public String getSites(HttpServletRequest request, Model uiModel) {
		User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);
		Home home = sakaiSiteService.findSakaiHome(user.getUserId());
		uiModel.addAttribute("home", home);
		return "sakai/home";
	}
	
	@RequestMapping(value="/{siteId}", method = RequestMethod.GET)
	public String getSite(HttpServletRequest request, @PathVariable("siteId") String siteId, Model uiModel) {
		try {
			User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);
			Site site = sakaiSiteService.findSite(siteId, user.getUserId());
			uiModel.addAttribute("site", site);
			
			String url = configParamService.findValueByName("Sakai.Url.Base") + "session.json";
			ResponseEntity<InputStream> is1 = oncourseOAuthService.oAuthGetRequest(user.getUserId(), url, "text/html");
			String jsonSession = IOUtils.toString(is1.getBody(), "UTF-8");
			String sessionId = sakaiSessionService.findSakaiSessionId(jsonSession);
			
			uiModel.addAttribute("sessionId", sessionId);
			uiModel.addAttribute("siteId", siteId);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}

		return "sakai/site";
	}
	
	@RequestMapping(value="/{siteId}/announcements", method = RequestMethod.GET)
	public String getAnnouncements(HttpServletRequest request, @PathVariable("siteId") String siteId, Model uiModel) {
		try {
			User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);
			List<Announcement> announcements = sakaiSiteService.findAllAnnouncements(siteId, user.getUserId());
			uiModel.addAttribute("announcements", announcements);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		uiModel.addAttribute("siteId", siteId);
		return "sakai/announcements";
	}
	
	@RequestMapping(value="/{siteId}/announcements/{annId}", method = RequestMethod.GET)
	public String getAnnouncement(HttpServletRequest request, @PathVariable("siteId") String siteId, @PathVariable("annId") String annId, Model uiModel) {
		try {
			User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);
			String url = configParamService.findValueByName("Sakai.Url.Base") + "announcement/message/" + siteId + "/" + annId + ".json";
			ResponseEntity<InputStream> is = oncourseOAuthService.oAuthGetRequest(user.getUserId(), url, "text/html");
			String json = IOUtils.toString(is.getBody(), "UTF-8");

			Announcement announcement = sakaiSiteService.findAnnouncementDetails(json);
			uiModel.addAttribute("announcement", announcement);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		uiModel.addAttribute("siteId", siteId);
		return "sakai/announcement";
	}
	
	@RequestMapping(value="/attachment/{siteId}/Announcements/{attachId}/{attachName}", method = RequestMethod.GET)
	public String getAnnouncementAttachment(HttpServletRequest request, HttpServletResponse response, @PathVariable("siteId") String siteId, @PathVariable("attachId") String attachmentId, @PathVariable("attachName") String attachmentName, Model uiModel) {
		try {
			User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);
			//doesn't work
			String url = configParamService.findValueByName("Sakai.Url.Base") + "announcement/attachment/" + siteId + "/Announcements/" + attachmentId;
			ResponseEntity<InputStream> is = oncourseOAuthService.oAuthGetRequest(user.getUserId(), url, "application/pdf");
			byte [] fileData = IOUtils.toByteArray(is.getBody());
			response.setContentType("application/pdf");
			response.setContentLength(fileData.length);
			response.getOutputStream().write(fileData, 0, fileData.length);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		return null;
	}

	@RequestMapping(value="/{siteId}/assignments", method = RequestMethod.GET)
	public String getAssignments(HttpServletRequest request, @PathVariable("siteId") String siteId, Model uiModel) {
		try {
			User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);

			List<Assignment> assignments = sakaiSiteService.findAllAssignments(siteId, user.getUserId());
			uiModel.addAttribute("sakaiassignments", assignments);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}

		return "sakai/assignments";
	}
	
	@RequestMapping(value="/{siteId}/assignments/{assId}", method = RequestMethod.GET)
	public String getAssignment(HttpServletRequest request, @PathVariable("siteId") String siteId, @PathVariable("assId") String assId, Model uiModel) {
		try {
			User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);
			String url = configParamService.findValueByName("Sakai.Url.Base") + "assignment/submissions/" + user.getUserId() + "/" + assId + ".json";
			ResponseEntity<InputStream> is = oncourseOAuthService.oAuthGetRequest(user.getUserId(), url, "text/html");
			String json = IOUtils.toString(is.getBody(), "UTF-8");

			List<Assignment> assignments = sakaiSiteService.findAssignmentDetails(json);
			uiModel.addAttribute("sakaiassignments", assignments);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}

		return "sakai/assignment";
	}
		
	@RequestMapping(value="/{siteId}/grades", method = RequestMethod.GET)
	public String getGrades(HttpServletRequest request, @PathVariable("siteId") String siteId, Model uiModel) {
		try {
			User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);
			String url = configParamService.findValueByName("Sakai.Url.Base") + "assignment/grades/" + siteId + "/" + user.getUserId() + ".json";
			ResponseEntity<InputStream> is = oncourseOAuthService.oAuthGetRequest(user.getUserId(), url, "text/html");
			String json = IOUtils.toString(is.getBody(), "UTF-8");

			List<Assignment> assignments = sakaiSiteService.findAssignmentDetails(json);
			
			url = configParamService.findValueByName("Sakai.Url.Base") + "gradebook/courseGrade/" + siteId + "/" + user.getUserId() + ".json";
			ResponseEntity<InputStream> is1 = oncourseOAuthService.oAuthGetRequest(user.getUserId(), url, "text/html");
			String gradeJson = IOUtils.toString(is1.getBody(), "UTF-8");
			String courseGrade = sakaiSiteService.findCourseGrade(gradeJson);
			
			uiModel.addAttribute("sakaigrades", assignments);
			uiModel.addAttribute("courseGrade", courseGrade);
			
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}

		return "sakai/grades";
	}
	
	@RequestMapping(value="/{siteId}/roster", method = RequestMethod.GET)
	public String getRoster(HttpServletRequest request, @PathVariable("siteId") String siteId, Model uiModel) {
		try {
			User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);
			String url = configParamService.findValueByName("Sakai.Url.Base") + "participant.json?siteId=" + siteId;
			ResponseEntity<InputStream> is = oncourseOAuthService.oAuthGetRequest(user.getUserId(), url, "text/html");
			String json = IOUtils.toString(is.getBody(), "UTF-8");

			List<Roster> roster = sakaiSiteService.findRoster(json);
			uiModel.addAttribute("roster", roster);
			
//			url = configParamService.findValueByName("Sakai.Url.Base") + "session.json";
//			ResponseEntity<InputStream> is1 = oncourseOAuthService.oAuthGetRequest(user.getUserId(), url, "text/html");
//			String jsonSession = IOUtils.toString(is1.getBody(), "UTF-8");
//			String sessionId = sakaiSessionService.findSakaiSessionId(jsonSession);
//			uiModel.addAttribute("sessionId", sessionId);
			uiModel.addAttribute("siteId", siteId);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}

		return "sakai/roster";
	}
	
	@RequestMapping(value="/{siteId}/roster/{displayId}", method = RequestMethod.GET)
	public String getRosterDetails(HttpServletRequest request, @PathVariable("siteId") String siteId, @PathVariable("displayId") String displayId, Model uiModel) {
		try {
			User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);
			String url = configParamService.findValueByName("Sakai.Url.Base") + "participant.json?siteId=" + siteId;
			ResponseEntity<InputStream> is = oncourseOAuthService.oAuthGetRequest(user.getUserId(), url, "text/html");
			String json = IOUtils.toString(is.getBody(), "UTF-8");

			List<Roster> roster = sakaiSiteService.findParticipantDetails(json, displayId);
			uiModel.addAttribute("roster", roster);
			
//			url = configParamService.findValueByName("Sakai.Url.Base") + "session.json";
//			ResponseEntity<InputStream> is1 = oncourseOAuthService.oAuthGetRequest(user.getUserId(), url, "text/html");
//			String jsonSession = IOUtils.toString(is1.getBody(), "UTF-8");
//			String sessionId = sakaiSessionService.findSakaiSessionId(jsonSession);
//			uiModel.addAttribute("sessionId", sessionId);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}

		return "sakai/rosterDetails";
	}
	
	@RequestMapping(value="/{siteId}/resources", method = RequestMethod.GET)
	public String getResources(HttpServletRequest request, HttpServletResponse response, @PathVariable("siteId") String siteId, @RequestParam(value="resId", required=false) String resId, Model uiModel) {
		try {
			User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);
			if (resId == null || resId.isEmpty()) {
				List<Resource> resources = sakaiSiteService.findSiteResources(siteId, user.getUserId(), null);
				uiModel.addAttribute("resources", resources);
			} else {
				//check if the last char is / that means it is a folder else its a file
				char lastChar = resId.charAt(resId.length()-1);
				
				if(lastChar == '/'){
					List<Resource> resources = sakaiSiteService.findSiteResources(siteId, user.getUserId(), resId);
					uiModel.addAttribute("resources", resources);
//					uiModel.addAttribute("sessionId", sessionId);
				}
				else {
					byte [] fileData = sakaiSiteService.getResource(resId, user.getUserId());
					String mimeType = mimeTypesMap.getContentType(resId);
					
					if (mimeType.equals(urlMimeType)) {
						String url = new String(fileData);
						response.sendRedirect(response.encodeRedirectURL(url));
					} else {
						response.setContentType(mimeType);
						response.setContentLength(fileData.length);
						response.setHeader("Content-Disposition", "attachment; filename=\"" + getFileName(resId) + "\"" );
						response.getOutputStream().write(fileData, 0, fileData.length);
						return null;
					}
				}
			}
			
//			url = configParamService.findValueByName("Sakai.Url.Base") + "session.json";
//			ResponseEntity<InputStream> is1 = oncourseOAuthService.oAuthGetRequest(user.getUserId(), url, "text/html");
//			String jsonSession = IOUtils.toString(is1.getBody(), "UTF-8");
//			String sessionId = sakaiSessionService.findSakaiSessionId(jsonSession);
//			uiModel.addAttribute("sessionId", sessionId);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		uiModel.addAttribute("siteId", siteId);
		return "sakai/resources";
	}
	
	private String getFileName(String resourceId) {
		int index = resourceId.lastIndexOf("/");
		if (index == -1) {
			return resourceId;
		} else {
			return resourceId.substring(index +1 );
		}
	}
	
	public void setSakaiSiteService(SakaiSiteService sakaiSiteService) {
		this.sakaiSiteService = sakaiSiteService;
	}
	
	public void setConfigParamService(ConfigParamService configParamService) {
		this.configParamService = configParamService;
	}
	
	public void setOncourseOAuthService(OAuth2LegService oncourseOAuthService) {
		this.oncourseOAuthService = oncourseOAuthService;
	}
}

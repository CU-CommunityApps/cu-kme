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

package org.kuali.mobility.athletics.controllers;

import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.kuali.mobility.athletics.entity.Athletics;
import org.kuali.mobility.athletics.entity.MatchData;
import org.kuali.mobility.athletics.entity.Player;
import org.kuali.mobility.athletics.entity.RosterData;
import org.kuali.mobility.athletics.entity.Sport;
import org.kuali.mobility.athletics.service.AthleticsService;
import org.kuali.mobility.news.entity.NewsArticle;
import org.kuali.mobility.news.entity.NewsStream;
import org.kuali.mobility.news.service.NewsService;
import org.kuali.mobility.shared.Constants;
import org.kuali.mobility.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/athletics")
public class AthleticsController {

	@Autowired
	private AthleticsService athleticsService;

	public void setAthleticsService(AthleticsService athleticsService) {
		this.athleticsService = athleticsService;
	}

	@Autowired
	private NewsService newsService;

	public void setNewsService(NewsService newsService) {
		this.newsService = newsService;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String getList(Model uiModel, HttpServletRequest request, @RequestParam(required = false) String selectedTab) throws Exception {
		User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);
		String selectedCampus = "";
		if (user.getViewCampus() == null) {
			return "redirect:campus?toolName=athletics";
		} else {
			selectedCampus = user.getViewCampus();
		}
		Athletics athletics = athleticsService.retrieveAthletics(selectedCampus);
		uiModel.addAttribute("athletics", athletics);
		uiModel.addAttribute("selectedTab", selectedTab);
		uiModel.addAttribute("today", Calendar.getInstance().get(Calendar.DATE));
		return "athletics/list";
	}

	@RequestMapping(value = "/viewSport", method = RequestMethod.GET)
	public String viewSport(HttpServletRequest request, Model uiModel, @RequestParam(required = true) Long sportId) throws Exception {
		Sport sport = athleticsService.retrieveSport(sportId);
		NewsStream newsStream = newsService.getNewsStream(sport.getLink(), null, false);
		uiModel.addAttribute("sport", sport);
		uiModel.addAttribute("newsStream", newsStream);
		return "athletics/newsList";
	}

	@RequestMapping(value = "/viewStory", method = RequestMethod.GET)
	public String viewStory(HttpServletRequest request, Model uiModel, @RequestParam(required = true) String link, @RequestParam(required = false) String selectedTab, @RequestParam(required = false) Long sportId) throws Exception {
		NewsArticle newsArticle = newsService.getNewsArticle(null, link, null);
		uiModel.addAttribute("newsArticle", newsArticle);
		uiModel.addAttribute("selectedTab", selectedTab);
		uiModel.addAttribute("sportId", sportId);
		return "athletics/news";
	}

	@RequestMapping(value = "/viewRoster", method = RequestMethod.GET)
	public String viewRoster(HttpServletRequest request, Model uiModel, @RequestParam(required = true) Long seasonId, @RequestParam(required = true) Long sportId) throws Exception {
		RosterData rosterData = athleticsService.retrieveRosterForSeason(sportId, seasonId);
		uiModel.addAttribute("rosterData", rosterData);
		return "athletics/roster";
	}

	@RequestMapping(value = "/viewSchedule", method = RequestMethod.GET)
	public String viewSchedule(HttpServletRequest request, Model uiModel, @RequestParam(required = true) Long seasonId, @RequestParam(required = true) Long sportId) throws Exception {
		MatchData matchData = athleticsService.retrieveScheduleForSeason(sportId, seasonId);
		uiModel.addAttribute("matchData", matchData);
		return "athletics/schedule";
	}

	@RequestMapping(value = "/viewPlayer", method = RequestMethod.GET)
	public String viewPlayer(HttpServletRequest request, Model uiModel, @RequestParam(required = true) Long seasonId, @RequestParam(required = true) Long sportId, @RequestParam(required = true) Long playerId) throws Exception {
		Player player = athleticsService.retrievePlayer(sportId, seasonId, playerId);
		uiModel.addAttribute("player", player);
		return "athletics/player";
	}

}

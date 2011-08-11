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

package org.kuali.mobility.socialmedia.controllers;

import java.util.ArrayList;
import java.util.List;

import org.kuali.mobility.socialmedia.entity.Tweet;
import org.kuali.mobility.socialmedia.service.TwitterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/socialmedia")
public class TwitterController {

	@Autowired
	private TwitterService twitterService;

	@RequestMapping(method = RequestMethod.GET)
	public String getList(Model uiModel) {

		List<String> feeds = new ArrayList<String>();
		feeds.add("IndianaUniv");
		feeds.add("citybloomington");
		feeds.add("idsnews");
		feeds.add("theheraldtimes");
		feeds.add("IUBloomington");
		feeds.add("IUPUI");
//		feeds.add("indiananews");
		feeds.add("IDS_Opinion");
		feeds.add("IUPUI_Prepared");
		feeds.add("IU_Health");
		feeds.add("IUBookstore");
		feeds.add("kinseyinstitute");
		feeds.add("UITSNEWS");

		List<Tweet> tweets = twitterService.retrieveCombinedFeeds(feeds);
		uiModel.addAttribute("tweets", tweets.subList(0, 25));
		return "socialmedia/list";
	}

	public void setEmergencyInfoService(TwitterService twitterService) {
		this.twitterService = twitterService;
	}
}

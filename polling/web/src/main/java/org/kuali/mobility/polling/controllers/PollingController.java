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

package org.kuali.mobility.polling.controllers;

import java.util.Date;
import java.util.List;

import org.kuali.mobility.polling.entity.Answer;
import org.kuali.mobility.polling.entity.Poll;
import org.kuali.mobility.polling.entity.Stats;
import org.kuali.mobility.polling.entity.Vote;
import org.kuali.mobility.polling.service.PollingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller 
@RequestMapping("/polling")
public class PollingController {

    @Autowired
    private PollingService service;
	    
    @RequestMapping(method = RequestMethod.GET)
    public String index(Model uiModel) {
    	List<Poll> polls = service.findAllPolls();
    	uiModel.addAttribute("polls", polls);
    	return "polling/index";
    }
    
    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String viewPoll(Model uiModel, @PathVariable("id") Long id) {
    	Poll poll = service.lookup(id);
    	uiModel.addAttribute("poll", poll);
    	uiModel.addAttribute("vote", new Vote());
    	return "polling/view";
    }
    
    @RequestMapping(value = "/stats/{id}", method = RequestMethod.GET)
    public String viewPollStats(Model uiModel, @PathVariable("id") Long id) {
    	Stats stats = service.findPollResults(id);
    	uiModel.addAttribute("stats", stats);
    	return "polling/results";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(Model uiModel) {
    	uiModel.addAttribute("poll", new Poll());
    	return "polling/form";
    }
    
    @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "add")
    public String addAnswer(Model uiModel, @ModelAttribute("poll") Poll poll, BindingResult result, @RequestParam("newAnswer") String newAnswer) {
    	Answer answer = new Answer();
    	answer.setAnswer(newAnswer);
    	answer.setPoll(poll);
    	poll.getAnswers().add(answer);
    	
    	return "polling/form";
    }
    
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String savePoll(Model uiModel, @ModelAttribute("poll") Poll poll, BindingResult result) {
    	if (poll.getQuestion() == null || poll.getQuestion().trim().length() == 0){
    		Errors errors = ((Errors) result);
    		errors.rejectValue("question", "", "Please provide a question.");
        	return "polling/form";
    	}
    	for (Answer answer : poll.getAnswers()){
    		answer.setPoll(poll);
    	}
    	service.save(poll);
    	return "polling/success";
    }
    
    @RequestMapping(value = "/vote", method = RequestMethod.POST)
    public String saveVote(Model uiModel, @ModelAttribute("vote") Vote vote) {
    	vote.setTimestamp(new Date().getTime());
    	service.saveVote(vote);
    	return "polling/success";
    }
    
    @RequestMapping(value = "/results", method = RequestMethod.GET)
    public String results(Model uiModel) {
    	return "polling/results";
    }
    
}

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
 
package org.kuali.mobility.polling.service;

import java.util.List;
import java.util.Map;

import org.kuali.mobility.polling.dao.PollingDao;
import org.kuali.mobility.polling.entity.Answer;
import org.kuali.mobility.polling.entity.Poll;
import org.kuali.mobility.polling.entity.Stats;
import org.kuali.mobility.polling.entity.Vote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value="PollingService")
public class PollingServiceImpl implements PollingService {

    @Autowired
    private PollingDao pollingDao;

    @Transactional
	@Override
	public Poll lookup(Long id) {
		return pollingDao.lookup(id);
	}

    @Transactional
	@Override
	public List<Poll> findAllPolls() {
		return pollingDao.findAllPolls();
	}

	@Override
	@Transactional
	public Poll save(Poll poll) {
		return pollingDao.save(poll);
	}

	@Override
	@Transactional
	public Poll delete(Poll poll) {
		return pollingDao.delete(poll);
	}
	
	@Override
	@Transactional
	public Vote saveVote(Vote vote) {
		return pollingDao.saveVote(vote);
	}
	
	@Override
	@Transactional
	public Stats findPollResults(Long pollId){
		Stats stats = new Stats();
		Poll poll = pollingDao.lookup(pollId);
		stats.setPoll(poll);
		Map<String, Integer> answerStats = stats.getAnswerStats();
		
		for (Answer answer : poll.getAnswers()){
			List<Vote> votes = pollingDao.getVotes(answer.getId());
			int numVotes = votes.size();
			answerStats.put(answer.getAnswer(), numVotes);
			stats.setTotalVotes(stats.getTotalVotes() + numVotes);
		}
		return stats;
	}
}

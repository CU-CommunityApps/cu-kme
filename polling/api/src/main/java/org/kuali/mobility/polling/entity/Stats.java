package org.kuali.mobility.polling.entity;

import java.util.HashMap;
import java.util.Map;

public class Stats {
	private Poll poll;
	private int totalVotes;
	private final Map<String, Integer> answerStats = new HashMap<String, Integer>();

	public Map<String, Integer> getAnswerStats() {
		return answerStats;
	}

	public Poll getPoll() {
		return poll;
	}

	public void setPoll(Poll poll) {
		this.poll = poll;
	}

	public int getTotalVotes() {
		return totalVotes;
	}

	public void setTotalVotes(int totalVotes) {
		this.totalVotes = totalVotes;
	}
}

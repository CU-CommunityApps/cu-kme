package org.kuali.mobility.news.entity;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("rss")
public class NewsSourceWrapper {
	private List<NewsSourceImpl> newsSources;
	
	public NewsSourceWrapper() {
		newsSources = new ArrayList<NewsSourceImpl>();
	}

	public List<NewsSourceImpl> getNewsSources() {
		return newsSources;
	}

	public void setNewsSources(List<NewsSourceImpl> newsSources) {
		this.newsSources = newsSources;
	}
	
	
}

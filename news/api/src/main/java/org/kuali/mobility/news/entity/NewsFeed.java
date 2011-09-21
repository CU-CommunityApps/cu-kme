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

package org.kuali.mobility.news.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NewsFeed implements Serializable, Comparable<NewsFeed> {

	private static final long serialVersionUID = 475441282491797666L;
	
	private String title;
	private String author;
	private String description;
	private long sourceId;
	private long lastUpdateTimestamp;
	private List<NewsArticle> articles;
	private int order;
	
	public NewsFeed copy() {
		NewsFeed copy = new NewsFeed();
		copy.setSourceId(sourceId);
		copy.setOrder(order);
		if (title != null) {
			copy.setTitle(new String(title));
		}
		if (author != null) {
			copy.setAuthor(new String(author));
		}
		if (description != null) {
			copy.setDescription(new String(description));
		}
		if (!articles.isEmpty()) {
			List<NewsArticle> articlesCopy = new ArrayList<NewsArticle>();
			for (NewsArticle article : articles) {
				articlesCopy.add(article.copy());
			}
			copy.setArticles(articlesCopy);
		}
		copy.lastUpdateTimestamp = lastUpdateTimestamp;
		return copy;
	}
    
	public NewsFeed() {
		articles = new ArrayList<NewsArticle>();
	}
	
	@Override
	public int compareTo(NewsFeed o) {
		return order - o.order;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<NewsArticle> getArticles() {
		return articles;
	}
	public void setArticles(List<NewsArticle> articles) {
		this.articles = articles;
	}

	public long getSourceId() {
		return sourceId;
	}

	public void setSourceId(long sourceId) {
		this.sourceId = sourceId;
	}

	/**
	 * @return the lastUpdateTimestamp
	 */
	public long getLastUpdateTimestamp() {
		return lastUpdateTimestamp;
	}

	/**
	 * @param lastUpdateTimestamp the lastUpdateTimestamp to set
	 */
	public void setLastUpdateTimestamp(long lastUpdateTimestamp) {
		this.lastUpdateTimestamp = lastUpdateTimestamp;
	}

	/**
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * @param author the author to set
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * @return the order
	 */
	public int getOrder() {
		return order;
	}

	/**
	 * @param order the order to set
	 */
	public void setOrder(int order) {
		this.order = order;
	}
}

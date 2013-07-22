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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.sun.jmx.snmp.Timestamp;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Represents a single article present in a news feed.
 * 
 * @author Kuali Mobility Team (moblitiy.collab@kuali.org)
 *
 */
//@XmlRootElement(name="article")
@XStreamAlias("item")
public class NewsArticleImpl implements Serializable, Comparable<NewsArticle>, NewsArticle {
	
	@XStreamAlias("cat")
	public class NewsArticleCategory {
		private String category;
		
		public String getCategory() {
			return this.category;
		}
		
		public void setCategory(String category) {
			this.category = category;
		}
	}
	
	private static final long serialVersionUID = -133725965130444787L;
	
	@XStreamAlias("title")
	private String title;
	@XStreamAlias("link")
	private String link;
	@XStreamAlias("description")
	private String description;
	@XStreamAlias("pubDate")
	private String publishDateString;
	private Date publishDate;
	private String articleId;
	private long sourceId;
	@XStreamAlias("thumbnail")
	private String imageUrl;
	@XStreamAlias("thumbnail_120x90")
	private String thumbnail_120x90;
	
	private List<NewsArticleCategory> categories;
	
	private final SimpleDateFormat format = new SimpleDateFormat("EEEE, MMMM dd, yyyy h:mm a");
	
	@Override
	public NewsArticle copy() {
		NewsArticle copy = new NewsArticleImpl();
		if (title != null) {
			copy.setTitle(new String(title));
		}
		if (link != null) {
			copy.setLink(new String(link));
		}
		if (description != null) {
			copy.setDescription(new String(description));
		}
		if (articleId != null) {
			copy.setArticleId(new String(articleId));
		}
		copy.setSourceId(sourceId);
		copy.setPublishDate(publishDate);
		if (imageUrl != null) {
			copy.setImageUrl(new String(imageUrl));
		}
		return copy;
	}
	
	/* (non-Javadoc)
	 * @see org.kuali.mobility.news.entity.NewsArticle#getPublishDateDisplay()
	 */
	@Override
	public String getPublishDateDisplay() {
		//return format.format(publishDate);
		return this.publishDateString;
	}
	
	@Override
	public int compareTo(NewsArticle arg0) {
		if (publishDate != null) {
			return publishDate.compareTo(arg0.getPublishDate());
		} else if (getPublishDateDisplay() != null) {
			return this.getPublishDateDisplay().compareTo(arg0.getPublishDateDisplay());
		} else {
			return this.getArticleId().compareTo(arg0.getArticleId());
		}
		
	}
	
	/* (non-Javadoc)
	 * @see org.kuali.mobility.news.entity.NewsArticle#getTitle()
	 */
	@Override
	public String getTitle() {
		return title;
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.news.entity.NewsArticle#setTitle(java.lang.String)
	 */
	@Override
	public void setTitle(String title) {
		this.title = title;
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.news.entity.NewsArticle#getLink()
	 */
	@Override
	public String getLink() {
		return link;
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.news.entity.NewsArticle#setLink(java.lang.String)
	 */
	@Override
	public void setLink(String link) {
		this.link = link;
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.news.entity.NewsArticle#getDescription()
	 */
	@Override
	public String getDescription() {
		return description;
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.news.entity.NewsArticle#setDescription(java.lang.String)
	 */
	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.news.entity.NewsArticle#getPublishDate()
	 */
	@Override
	public Date getPublishDate() {
		return publishDate;
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.news.entity.NewsArticle#setPublishDate(java.sql.Timestamp)
	 */
	@Override
	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.news.entity.NewsArticle#getArticleId()
	 */
	@Override
	public String getArticleId() {
		return articleId;
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.news.entity.NewsArticle#setArticleId(java.lang.String)
	 */
	@Override
	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.news.entity.NewsArticle#getSourceId()
	 */
	@Override
	public long getSourceId() {
		return sourceId;
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.news.entity.NewsArticle#setSourceId(long)
	 */
	@Override
	public void setSourceId(long sourceId) {
		this.sourceId = sourceId;
	}
	
	@Override
	public String getImageUrl() {
		return imageUrl;
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.news.entity.NewsArticle#setImageUrl(java.lang.String)
	 */
	@Override
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getPublishDateString() {
		return publishDateString;
	}

	public void setPublishDateString(String publishDateString) {
		this.publishDateString = publishDateString;
	}
	
	
}

package org.kuali.mobility.news.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class NewsArticle implements Serializable, Comparable<NewsArticle> {

	private static final long serialVersionUID = -133725965130444787L;
	
	private String title;
	private String link;
	private String description;
	private Timestamp publishDate;
	private String articleId;
	private long sourceId;
	
	private final SimpleDateFormat format = new SimpleDateFormat("EEEE, MMMM dd, yyyy h:mm a");
	
	public NewsArticle copy() {
		NewsArticle copy = new NewsArticle();
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
		copy.setPublishDate(new Timestamp(publishDate.getTime()));
		
		return copy;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getLink() {
		return link;
	}
	
	public void setLink(String link) {
		this.link = link;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Timestamp getPublishDate() {
		return publishDate;
	}
	
	public String getPublishDateDisplay() {
		return format.format(publishDate);
	}
	
	public void setPublishDate(Timestamp pubDate) {
		this.publishDate = pubDate;
	}
	
	public String getArticleId() {
		return articleId;
	}
	
	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}
	
	@Override
	public int compareTo(NewsArticle arg0) {
		return publishDate.compareTo(arg0.publishDate);
	}

	public long getSourceId() {
		return sourceId;
	}

	public void setSourceId(long id) {
		this.sourceId = id;
	}
}

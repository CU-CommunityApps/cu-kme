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

package org.kuali.mobility.news.dao;

import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.kuali.mobility.news.entity.NewsArticle;
import org.kuali.mobility.news.entity.NewsArticleImpl;
import org.kuali.mobility.news.entity.NewsArticleImpl.NewsArticleCategory;
import org.kuali.mobility.news.entity.NewsSource;
import org.kuali.mobility.news.entity.NewsSourceImpl;
import org.kuali.mobility.news.entity.NewsSourceWrapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.thoughtworks.xstream.XStream;

public class NewsCacheImpl implements NewsCache, ApplicationContextAware {

	private static final Logger LOG = Logger.getLogger( NewsCacheImpl.class );
	
	private ApplicationContext applicationContext;
	
	private Map<Long, NewsSource> newsSources = new HashMap<Long, NewsSource>();
		
	/**
	 * Update the cache for a single NewsSource. Called when a NewsSource is saved.
	 * 
	 * @param source the NewsSource that was saved
	 */
	public void updateCache(NewsSource source) {
		if (source.isActive()) {
			LOG.debug( "NewsSource "+source.getId()+" is active & will be refreshed." );
			if( !getNewsSources().containsKey( source.getId() ) )
			{
				getNewsSources().put(source.getId(), source);
			}
			updateSource(source);
		} else {
			LOG.debug( "NewsSource is inactive & being removed." );
			getNewsSources().remove(source.getId());
		}
	}
	
	/**
	 * Does the actual work of updating a news feed and its articles
	 * 
	 * @param feed the NewsFeed to update
	 * @param source the NewsSource that defines the feed to update
	 */
	@SuppressWarnings("unchecked")
	public void updateSource(NewsSource source) {
		if (source==null ) {
			LOG.error( "Not updating, source is null." );
			return;
		} else if( source.getUrl()==null )
		{
			LOG.debug( "Not updating source due to no URL for source "+source.getId() );
			source.setTitle( source.getName() );
			return;
		}
		
		URL feedUrl = null;
		try {
			feedUrl = new URL(source.getUrl());
		} catch (MalformedURLException e) {
			LOG.error("Bad feed url: " + source.getUrl(), e);
		}
		
		/**
		 * This XStream impl is a CU customization.
		 */
		
		try {
			XStream xstream = new XStream();
			xstream.processAnnotations(NewsSourceWrapper.class);
			xstream.processAnnotations(NewsSourceImpl.class);
			xstream.processAnnotations(NewsArticleImpl.class);
			xstream.processAnnotations(NewsArticleCategory.class);
			xstream.addImplicitCollection(NewsSourceWrapper.class, "newsSources");
			xstream.addImplicitCollection(NewsSourceImpl.class, "articles");
			xstream.addImplicitCollection(NewsArticleImpl.class, "categories");
			
			NewsSourceWrapper sourceWrapper = (NewsSourceWrapper) xstream.fromXML(feedUrl);
			NewsSource s = sourceWrapper.getNewsSources().get(0);
			source.setArticles(s.getArticles());
			source.setAuthor(s.getAuthor());
			source.setDescription(s.getDescription());
			source.setName(s.getName());
			source.setTitle(s.getTitle());
			
			for (NewsArticle article : source.getArticles()) {
				article.setSourceId(source.getId());
				/**
				 * Set a unique ID for the article.
				 */
				try {
					article.setArticleId(URLEncoder.encode(article.getLink(), "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					article.setArticleId(article.getLink());
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("Error in doing XStream work: ", e);
		}
		
		/**
		 * This is the original implementation.  It doesn't work for us concerning publication
		 * date and thumbnail/image URL.
		 */
		
		/*
		SyndFeedInput input = new SyndFeedInput();
		SyndFeed syndFeed = null;
		try {
			syndFeed = input.build(new InputStreamReader(feedUrl.openStream()));
		} catch (Exception e) {
			LOG.error("Error reading feed: " + source.getName(), e);
		}
		
		if (syndFeed != null) {
			LOG.debug( "Feed data retrieved, populating articles for: "+syndFeed.getTitle() );
			source.setTitle(syndFeed.getTitle());
			source.setAuthor(syndFeed.getAuthor());
			source.setDescription(syndFeed.getDescription());
			
			List<NewsArticle> articles = new ArrayList<NewsArticle>();
			for (SyndEntryImpl entry : (List<SyndEntryImpl>)syndFeed.getEntries()) {
				LOG.debug( "Processing article: "+entry.getTitle() );
				NewsArticle article = (NewsArticle)getApplicationContext().getBean("newsArticle");
				
				article.setTitle(entry.getTitle());
				article.setDescription(entry.getDescription().getValue());
				article.setLink(entry.getLink());
				try
				{
					article.setPublishDate(new Date(entry.getPublishedDate().getTime()));
				}
				catch( Exception e )
				{
					LOG.error( "Error creating timestamp for article: "+entry.getTitle() );
					LOG.error( e.getLocalizedMessage() );
				}
				article.setSourceId(source.getId());
				try {
					article.setArticleId(URLEncoder.encode(entry.getUri(), "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					article.setArticleId(entry.getUri());
				}
				
				MediaEntryModule mod = (MediaEntryModule) entry.getModule(MediaEntryModule.URI);
				UrlReference ref = (UrlReference) mod.getMediaContents()[0].getReference();
				URI url = ref.getUrl();
				article.setImageUrl(url.toString());
				
				articles.add(article);
			}
			source.setArticles(articles);
		} else {
			source.setTitle( source.getName() );
		}
		*/
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	public Map<Long, NewsSource> getNewsSources() {
		return newsSources;
	}

	public void setNewsSources(Map<Long, NewsSource> newsSources) {
		this.newsSources = newsSources;
	}

}

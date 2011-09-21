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

package org.kuali.mobility.news.service;

import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.kuali.mobility.configparams.service.ConfigParamService;
import org.kuali.mobility.news.dao.NewsDao;
import org.kuali.mobility.news.entity.NewsArticle;
import org.kuali.mobility.news.entity.NewsFeed;
import org.kuali.mobility.news.entity.NewsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;

/**
 * Service for actually doing the work of interacting with the nedws entity objects
 * 
 * @author Kuali Mobility Team (moblitiy.collab@kuali.org)
 * @see org.kuali.mobility.news.service.NewsService
 */
@Service(value = "NewsService")
public class NewsServiceImpl implements NewsService {

	private static org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(NewsServiceImpl.class);
	
	@Autowired
	private NewsDao newsDao;
	
	@Autowired
	ConfigParamService configParamService;
	public void setConfigParamService(ConfigParamService configParamService) {
		this.configParamService = configParamService;
	}
	
	private final ConcurrentMap<Long, NewsFeed> cachedFeeds = new ConcurrentHashMap<Long, NewsFeed>();
	private final ConcurrentMap<Long, NewsSource> cachedSources = new ConcurrentHashMap<Long, NewsSource>();
	
	private static Thread newsCacheReloaderThread = null;
	
	@Override
	public List<NewsSource> getAllNewsSources() {
		return newsDao.findAllNewsSources();
	}
	
	@Override
	public List<NewsSource> getAllActiveNewsSources() {
		return newsDao.findAllActiveNewsSources();
	}

	@Override
	public NewsSource getNewsSourceById(Long id) {
		return newsDao.lookup(id);
	}
	
	@Override
	public NewsSource deleteNewsSourcebyId(long id) {
		return newsDao.delete(newsDao.lookup(id));
	}

	@Override
	public NewsSource saveNewsSource(NewsSource newsSource) {
		newsDao.save(newsSource);
		updateCache(newsSource);
		return newsSource;
	}
	
	@Override
	@Transactional
	public void moveNewsSourceUp(long id) {
		List<NewsSource> sources = getAllNewsSources();
    	NewsSource sourceToMove = null;
    	NewsSource sourceToDisplace = null;
    	for (NewsSource source : sources) {
    		if (source.getId() == id) {
    			sourceToMove = source;
    		}
    	}
    	if (sourceToMove != null) {
    		if (sourceToMove.getOrder() > 0) {
    			int currentOrder = -1;
	    		for (NewsSource source : sources) {
	        		if (source.getOrder() > currentOrder  && source.getOrder() < sourceToMove.getOrder()) {
	        			currentOrder = source.getOrder();
	        			sourceToDisplace = source;
	        		}
	        	}
	    		if (sourceToDisplace != null) {
	    			int fromOrder = sourceToMove.getOrder();
		    		int toOrder = sourceToDisplace.getOrder();
		    		sourceToMove.setOrder(toOrder);
	    			sourceToDisplace.setOrder(fromOrder);
	    			saveNewsSource(sourceToMove);
	        		saveNewsSource(sourceToDisplace);
	    		}
    		}
    	}
	}

	@Override
	@Transactional
	public void moveNewsSourceDown(long id) {
		List<NewsSource> sources = getAllNewsSources();
    	NewsSource sourceToMove = null;
    	NewsSource sourceToDisplace = null;
    	for (NewsSource source : sources) {
    		if (source.getId() == id) {
    			sourceToMove = source;
    		}
    	}
    	if (sourceToMove != null) {
    		int currentOrder = -1;
    		for (NewsSource source : sources) {
        		if (source.getOrder() > sourceToMove.getOrder()) {
        			if (currentOrder < 0) {
        				currentOrder = source.getOrder();
            			sourceToDisplace = source;
        			} else {
        				if (source.getOrder() < currentOrder) {
        					currentOrder = source.getOrder();
                			sourceToDisplace = source;
        				}
        			}
        			
        		}
        	}
    		
    		if (sourceToDisplace != null) {
    			int fromOrder = sourceToMove.getOrder();
        		int toOrder = sourceToDisplace.getOrder();
        		sourceToMove.setOrder(toOrder);
    			sourceToDisplace.setOrder(fromOrder);
    			saveNewsSource(sourceToMove);
        		saveNewsSource(sourceToDisplace);
    		}
    	}
	}
	
	@Override
	public List<NewsFeed> getAllActiveNewsFeeds() {
		List<NewsFeed> feeds = new ArrayList<NewsFeed>(cachedFeeds.values());
		Collections.sort(feeds);
		return feeds;
	}

	@Override
	public NewsFeed getNewsFeed(long newsSourceId) {
		return cachedFeeds.get(newsSourceId);
	}
	
	@Override
	public NewsArticle getNewsArticle(String articleId, long sourceId) {
		NewsFeed feed = getNewsFeed(sourceId);
		for (NewsArticle article : feed.getArticles()) {
			String id;
			try {
				id = URLDecoder.decode(article.getArticleId(), "UTF-8");
				if (articleId.equals(id)) {
					return article.copy();
				}
			} catch (UnsupportedEncodingException e) {
			}
		}
		return null;
	}
	
	/**
	 * Update the cache for a single NewsSource. Called when a NewsSource is saved.
	 * 
	 * @param source the NewsSource that was saved
	 */
	private void updateCache(NewsSource source) {
		if (source.isActive()) {
			cachedSources.put(source.getId(), source);
			NewsFeed feed = cachedFeeds.get(source.getId());
			if (feed == null) {
				feed = new NewsFeed();
				cachedFeeds.put(source.getId(), feed);
			}
			updateFeed(feed, source);
		} else {
			cachedSources.remove(source.getId());
			cachedFeeds.remove(source.getId());
		}
	}
	
	/**
	 * Does the actual work of updating a news feed and its articles
	 * 
	 * @param feed the NewsFeed to update
	 * @param source the NewsSource that defines the feed to update
	 */
	@SuppressWarnings("unchecked")
	private void updateFeed(NewsFeed feed, NewsSource source) {
		feed.setOrder(source.getOrder());
		
		URL feedUrl = null;
		try {
			feedUrl = new URL(source.getUrl());
		} catch (MalformedURLException e) {
			LOG.error("Bad feed url: " + source.getUrl(), e);
		}
		SyndFeedInput input = new SyndFeedInput();
		SyndFeed syndFeed = null;
		try {
			syndFeed = input.build(new InputStreamReader(feedUrl.openStream()));
		} catch (Exception e) {
			LOG.error("Error reading feed: " + source.getName(), e);
		}
		
		if (syndFeed != null) {
			feed.setTitle(syndFeed.getTitle());
			feed.setAuthor(syndFeed.getAuthor());
			feed.setDescription(syndFeed.getDescription());
			feed.setSourceId(source.getId());
			
			List<NewsArticle> articles = new ArrayList<NewsArticle>();
			for (SyndEntryImpl entry : (List<SyndEntryImpl>)syndFeed.getEntries()) {
				NewsArticle article = new NewsArticle();
				
				article.setTitle(entry.getTitle());
				article.setDescription(entry.getDescription().getValue());
				article.setLink(entry.getLink());
				article.setPublishDate(new Timestamp(entry.getPublishedDate().getTime()));
				article.setSourceId(source.getId());
				try {
					article.setArticleId(URLEncoder.encode(entry.getUri(), "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					article.setArticleId(entry.getUri());
				}
				
				articles.add(article);
			}
			feed.setArticles(articles);
		}
	}
	
	@Override
	public void startCache() {
		newsCacheReloaderThread = new Thread(new NewsCacheReloader());
		newsCacheReloaderThread.setDaemon(true);
		newsCacheReloaderThread.start();
    }
    
	@Override
    public void stopCache() {
		newsCacheReloaderThread.interrupt();
		newsCacheReloaderThread = null;
    }
	
	/**
	 * A class to serve as our background thread for keeping the cache up-to-date
	 * 
	 * @author Kuali Mobility Team (moblitiy.collab@kuali.org)
	 */
	private class NewsCacheReloader implements Runnable {
        
		/**
		 * The main entry point for the cache reloader.  Continually calls reloadCache() on specified intervals.
		 */
        public void run() {    
            Calendar updateCalendar = Calendar.getInstance();
            Date nextCacheUpdate = new Date();
            
            int cacheUpdateInterval = 5;
			try {
				cacheUpdateInterval = Integer.parseInt(configParamService.findValueByName("News.CacheUpdate.Minute"));
			} catch (Exception e) {
				LOG.info("Could not retrieve config parameter: News.CacheUpdate.Minute. Using default value of 5.");
			}
                     
            // Cache loop
            while (true) {
                Date now = new Date();
                if (now.after(nextCacheUpdate)) {
                    try {
                    	reloadCache();	
                    } catch (Exception e) {
                    	LOG.error("Error reloading news cache.", e);
                    }
                    updateCalendar.add(Calendar.MINUTE, cacheUpdateInterval);
                    nextCacheUpdate = new Date(updateCalendar.getTimeInMillis());
                }
                try {
                    Thread.sleep(1000 * 60);
                } catch (InterruptedException e) {
                    LOG.error("Error:", e);
                }
            }
        }

        /**
         * Iterates through all the active NewsSource objects and updates their corresponding NewsFeed objects.  
         * Any NewsSources that have been deactivated have their NewsFeed objects removed from the cache.
         */
		private void reloadCache() {
			List<NewsSource> newsSources = newsDao.findAllActiveNewsSources();
			Set<Long> sourceIdsToRemove = new HashSet<Long>(cachedSources.keySet());
			
			for (NewsSource source : newsSources) {
				sourceIdsToRemove.remove(source.getId());
				cachedSources.put(source.getId(), source);
			}
			
			//remove any deleted or inactivated feeds from the cache
			for (Long id : sourceIdsToRemove) {
				cachedFeeds.remove(id);
				cachedSources.remove(id);
			}
			
			Set<Long> cachedSourceIds = cachedSources.keySet();
			
			for (Long id : cachedSourceIds) {
				NewsSource source = cachedSources.get(id);
				NewsFeed feed = cachedFeeds.get(id);
				if (feed == null) {
					feed = new NewsFeed();
					cachedFeeds.put(id, feed);
				}
				updateFeed(feed, source);
			}
		}
	}

	/**
	 * @return the newsDao
	 */
	public NewsDao getNewsDao() {
		return newsDao;
	}

	/**
	 * @param newsDao the newsDao to set
	 */
	public void setNewsDao(NewsDao newsDao) {
		this.newsDao = newsDao;
	}
}

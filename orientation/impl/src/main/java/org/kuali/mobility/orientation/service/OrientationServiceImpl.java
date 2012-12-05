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

package org.kuali.mobility.orientation.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Collections;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import org.kuali.mobility.orientation.entity.Attendee;
import org.kuali.mobility.orientation.entity.ContentBlock;
import org.kuali.mobility.orientation.entity.MenuItem;
import org.kuali.mobility.orientation.entity.Session;

public class OrientationServiceImpl implements OrientationService {

	private static org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(OrientationServiceImpl.class);
	
	private String menuFeedUrl;
	private String attendeesFeedUrl;
	private String sessionsFeedUrl;
	private String welcomeFeedUrl;
	private String toEmailAddress;
	private String fromEmailAddress;
	
	private String sessionJSON = null;
	
	private List<ContentBlock> contentBlocks = null;
	private List<MenuItem> menuItems = null;
	private List<Attendee> attendees = null;
	private List<Session> sessions = null;
	
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ContentBlock> findAllContentBlocks() {
		
		if (contentBlocks != null) {
			return contentBlocks;
		}
		
		contentBlocks = new ArrayList<ContentBlock>();
		try {
			String json = retrieveJSON(welcomeFeedUrl);

			JSONArray simpleContentArray = (JSONArray) JSONSerializer.toJSON(json);
			
			for (Iterator<JSONObject> iter = simpleContentArray.iterator(); iter.hasNext();) {
				try {
					JSONObject contentBlockObject = iter.next();
					
					ContentBlock contentBlock = new ContentBlock();
					contentBlock.setContentBlock(contentBlockObject.getString("welcome"));

					contentBlocks.add(contentBlock);
				} catch (Exception e) {
					LOG.error(e.getMessage(), e);
				}
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}

		return contentBlocks;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MenuItem> findAllMenuItems(String lang) {
		
		if (menuItems != null) {
			return menuItems;
		}
		
		menuItems = new ArrayList<MenuItem>();
		try {
			String json = retrieveJSON(menuFeedUrl);
			
			JSONArray menuItemArray = (JSONArray) JSONSerializer.toJSON(json);
			for (Iterator<JSONObject> iter = menuItemArray.iterator(); iter.hasNext();) {
				try {
					JSONObject menuItemObject = iter.next();
					
					MenuItem menuItem = new MenuItem();
					menuItem.setTitle(menuItemObject.getString("title"));
					menuItem.setDescription(menuItemObject.getString("description"));
					menuItem.setLinkURL(menuItemObject.getString("linkURL"));
					menuItem.setIconURL(menuItemObject.getString("iconURL"));
					menuItems.add(menuItem);
				} catch (Exception e) {
					LOG.error(e.getMessage(), e);
				}
				
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		return menuItems;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Attendee> findAllAttendees(char start, char end) {
		
		if (attendees != null) {
			return attendees;
		}
		
		attendees = new ArrayList<Attendee>();
		try {
			String json = retrieveJSON(attendeesFeedUrl);

			JSONArray attendeeArray = (JSONArray) JSONSerializer.toJSON(json);
			
			for (Iterator<JSONObject> iter = attendeeArray.iterator(); iter.hasNext();) {
				try {
					JSONObject attendeeObject = iter.next();
					
					Attendee attendee = new Attendee();
					attendee.setId(attendeeObject.getString("id"));
					attendee.setOfficeName(attendeeObject.getString("name"));
					attendee.setPhone(attendeeObject.getString("phone"));
					attendee.setWebsite(attendeeObject.getString("website"));
					attendee.setLocation(attendeeObject.getString("location"));
					
					
					char c = attendee.getOfficeName().toUpperCase().charAt(0);
					if (c >= start && c <= end) {						
						attendees.add(attendee);
					}
				} catch (Exception e) {
					LOG.error(e.getMessage(), e);
				}
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}

		return attendees;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Session> findAllSessions(String date) {
	
		if (sessions != null) {
			return sessions;
		}
		
		sessions = new ArrayList<Session>();
		try {
			String dateString = (null == date ? "" : date);
			
			if (sessionJSON == null) {
			
				sessionJSON = retrieveJSON(sessionsFeedUrl + "?d=" + dateString);

			}
			
			
			JSONArray sessionArray = (JSONArray) JSONSerializer.toJSON(sessionJSON);
			
			
			for (Iterator<JSONObject> iter = sessionArray.iterator(); iter.hasNext();) {
				try {
					JSONObject sessionObject = iter.next();
					
					Session session = new Session();
					
					// take out non alpha-numberic chars to get a tidy new id for linking
					//session.setId(URLEncoder.encode(sessionObject.getString("title").replaceAll("[^A-Za-z0-9 ]", ""), "UTF-8"));
					session.setTitle(sessionObject.getString("title").replace("\\",""));
					session.setDescription(sessionObject.getString("details"));
					session.setLocation(sessionObject.getString("location"));
					session.setTrack(sessionObject.getString("track"));
					// take out non alpha-numeric chars and spaces to make a simple css class name, prepend 'track-'
					session.setTrackCSSClass("track-" + sessionObject.getString("track").replaceAll("[^A-Za-z0-9]", ""));
					//session.setLevel(sessionObject.getString("level"));
					//session.setType(sessionObject.getString("type"));
					
					
					session.setLatitude(sessionObject.getString("latitude"));
					session.setLongitude(sessionObject.getString("longitude"));
				//	session.setLink("https://maps.google.com/maps?q="+session.getLatitude()+","+session.getLongitude()+"&z=18");
					
					
					
					String formattedStartDate, formattedEndDate = null, cssStartDate = null, cssStartDateTime = null;

					try {
						String str_startDate = sessionObject.getString("startTime");
						String str_endDate = sessionObject.getString("endTime");
						DateFormat parser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

						Date startDate = null;
						
						Date endDate = null;
						
						try {
							startDate = (Date)parser.parse(str_startDate);
						} catch (Exception e) {
							// Maybe startTime does not have a time.
							DateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd");
							startDate = (Date)dateParser.parse(str_startDate);

						}
						
						if (!str_endDate.trim().isEmpty()) {
							endDate = (Date)parser.parse(str_endDate);
							session.setdEndTime(endDate);

						}
						session.setdStartTime(startDate);
						session.setdEndTime(endDate);
						
						//DateFormat formatter = new SimpleDateFormat("E hh:mm a");
						DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						DateFormat formatterForDateCSS = new SimpleDateFormat("yyyyMMdd");
						DateFormat formatterForDateTimeCSS = new SimpleDateFormat("yyyyMMddkkmm");
						//DateFormat formatterForDateTimeCSS = new SimpleDateFormat("yyyyMMddkk");
						//DateFormat formatter = new SimpleDateFormat("HH:mm");

						
						formattedStartDate = formatter.format(startDate);
						if (endDate != null){
						   formattedEndDate = formatter.format(endDate);
						}
						cssStartDate = formatterForDateCSS.format(startDate);
						cssStartDateTime = formatterForDateTimeCSS.format(startDate);
						
						session.setStartTime(formattedStartDate);
						session.setEndTime(formattedEndDate);
						
						session.setDateCSSClass("date-" + cssStartDate);
						session.setDateTimeCSSClass("dateTime-" + cssStartDateTime);

					} catch (ParseException e) {
						e.printStackTrace();
					}
					session.setId(URLEncoder.encode((sessionObject.getString("title")+" "+cssStartDateTime).replaceAll("[^A-Za-z0-9 ]", ""), "UTF-8"));

				//	JSONArray tempSpeakers = sessionObject.getJSONArray("presenters");
					
					
					
					JSONArray f = sessionObject.getJSONArray("flags");
					
					String flagSet = "";
					
					for (int i = 0; i < f.size(); i++) {
						
						//System.out.println("flags:" + f.getString(i));
						flagSet = flagSet + "|" + f.getString(i);
						
					}
					
					if(!flagSet.equals("")) {
					
						flagSet = flagSet.substring(1,flagSet.length());
					
						session.setFlags(flagSet);
					
					}
					
					List<Attendee> speakers = new ArrayList<Attendee>();
					
					//JSONArray sessionArray = (JSONArray) JSONSerializer.toJSON(json);
					//for (Iterator<JSONObject> iter2 = tempSpeakers.iterator(); iter2.hasNext();) {
					
					//JSONObject speakersObject = iter2.next();
				
//					for (int i = 0; i < tempSpeakers.size(); i++) {
//		            	//JSONObject speakersObject = tempSpeakers.getJSONObject(i);
//
//						//System.out.println("tempSpeakers:" + tempSpeakers.getString(i));
//						
//		            	Attendee speaker = new Attendee();
//		            	//speaker.setFirstName(speakersObject);
//		            	
//		            	// flip the order of the person's name and skip the comma
//		            	String speakerName = tempSpeakers.getString(i);
//		            	int commaBetweenFirstAndLastName = speakerName.indexOf(",");
//		            	String lastName = speakerName.substring(0, commaBetweenFirstAndLastName);
//		            	String firstName = speakerName.substring(commaBetweenFirstAndLastName+1);
//		            	speaker.setFirstName(firstName + " " + lastName);
//		            	
//		            	
//		            	
//		            	//speaker.setFirstName(tempSpeakers.getString(i));
//		            	//System.out.println("Object:" + speakersObject);
//		            	/*speaker.setLastName(speakersObject.getString("lastName"));
//		            	speaker.setEmail(speakersObject.getString("email"));
//		            	speaker.setTitle(speakersObject.getString("title"));
//		            	speaker.setInstitution(speakersObject.getString("organization"));*/
//		            	speakers.add(speaker);
//		            }
					
					session.setSpeakers(speakers);
		
				sessions.add(session);
				} catch (Exception e) {
					LOG.error(e.getMessage(), e);
				}
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		Collections.sort(sessions);
		return sessions;
	}

	
	private String retrieveJSON(String feedUrl) throws MalformedURLException, IOException {
		
		System.out.println("Attempting to retrieve data from: "+ feedUrl);
	    URL url = new URL(feedUrl);
	    URLConnection conn = url.openConnection();
	    String encoding = conn.getContentEncoding();
	    if (encoding == null) {
	    	encoding = "UTF-8";
	    }

	    BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), encoding));
	    StringBuffer sb = new StringBuffer();
	    String line;
	    while ((line = rd.readLine()) != null) {
	    	sb.append(line);
	    }
	    rd.close();
	    String json = sb.toString();
	    return json;
    }

	@Override
    public Attendee findAttendeeById(String id) {
	    List<Attendee> attendees = findAllAttendees('A', 'Z');
	    for (Attendee attendee : attendees) {
	    	if (attendee.getId() != null && attendee.getId().equals(id)) {
	    		return attendee;
	    	}
        }
	    return null;
    }

	@Override
    public Session findSessionById(String id) {
		List<Session> sessions = findAllSessions("");
	    for (Session session : sessions) {
	     	if (session.getId() != null && session.getId().equals(id)) {
	    		return session;
	    	}
        }
	    return null;
    }
	
	public String getMenuFeedUrl() {
		return menuFeedUrl;
	}

	public void setMenuFeedUrl(String menuFeedUrl) {
		this.menuFeedUrl = menuFeedUrl;
	}
	
	public String getAttendeesFeedUrl() {
		return attendeesFeedUrl;
	}

	public void setAttendeesFeedUrl(String attendeesFeedUrl) {
		this.attendeesFeedUrl = attendeesFeedUrl;
	}

	public String getSessionsFeedUrl() {
		return sessionsFeedUrl;
	}

	public void setSessionsFeedUrl(String sessionsFeedUrl) {
		this.sessionsFeedUrl = sessionsFeedUrl;
	}

	public String getWelcomeFeedUrl() {
		return welcomeFeedUrl;
	}

	public void setWelcomeFeedUrl(String welcomeFeedUrl) {
		this.welcomeFeedUrl = welcomeFeedUrl;
	}

	public String getToEmailAddress() {
		return toEmailAddress;
	}

	public void setToEmailAddress(String toEmailAddress) {
		this.toEmailAddress = toEmailAddress;
	}

	public String getFromEmailAddress() {
		return fromEmailAddress;
	}

	public void setFromEmailAddress(String fromEmailAddress) {
		this.fromEmailAddress = fromEmailAddress;
	}
	
}

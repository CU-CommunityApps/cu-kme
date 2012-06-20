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

package org.kuali.mobility.push.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name="KME_PSH_MSG_T")
public class Push implements Serializable {

	private static final long serialVersionUID = -9158722924017383328L;


	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name="ID")
    private Long pushId;
	
	@Column(name="TTL")
	private String title;

	@Column(name="MSG")
	private String message;

    @Column(name="PST_TS")
    private Timestamp postedTimestamp;
	
    @Column(name="SNDR")
    private String sender;

    @Column(name="RCPT")
    private Long recipients;
    
    @Column(name="URL")
    private String url;

    @Column(name="EMR")
    private boolean emergency;
    
    @Version
    @Column(name="VER_NBR")
    protected Long versionNumber;
	
	
	public Push() {}
	
    public Timestamp getPostedTimestamp() {
        return postedTimestamp;
    }

    public void setPostedTimestamp(Timestamp postedTimestamp) {
        this.postedTimestamp = postedTimestamp;
    }

    public boolean getEmergency() {
        return emergency;
    }
    
    public void setEmergency(boolean emergency) {
        this.emergency = emergency;
    }    
    
    public Long getPushId() {
        return pushId;
    }
    public void setPushId(Long pushId) {
        this.pushId = pushId;
    }

    public Long getRecipients() {
        return recipients;
    }
    public void setRecipients(Long recipients) {
        this.recipients = recipients;
    }
    
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}    

	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}  	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
    public Long getVersionNumber() {
        return versionNumber;
    }
    public void setVersionNumber(Long versionNumber) {
        this.versionNumber = versionNumber;
    }	
	
	@Override
	public String toString() {
    	String newline = "\r\n";
    	String str = newline +"PushID:     " + this.getPushId();
    	str = str + newline + "Title:      " + this.getTitle();
    	str = str + newline + "Message:    " + this.getMessage(); 
    	str = str + newline + "Emergency:  " + this.getEmergency(); 
    	str = str + newline + "URL:        " + this.getUrl();  
    	str = str + newline + "Sender:     " + this.getSender();  
    	str = str + newline + "Recipients: " + this.getRecipients(); 
    	str = str + newline + "Timestamp:  " + this.getPostedTimestamp();    
    	return str;
    }
	
}

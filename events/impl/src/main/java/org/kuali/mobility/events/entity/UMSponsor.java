package org.kuali.mobility.events.entity;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;
@XStreamAlias("sponsor")
public class UMSponsor implements Serializable {
	private static final long serialVersionUID = -2819980803335054558L;
	private String groupId;
	private String groupName;
	private String website;
	
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	
}

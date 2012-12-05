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

package org.kuali.mobility.orientation.entity;

import java.io.Serializable;

public class Attendee implements Serializable, Comparable<Attendee> {

	private static final long serialVersionUID = -2826816981140315473L;

	private String id;
	private String officeName;
	private String website;
	private String phone;
	private String location;
	

	public String getId() {
    	return id;
    }

	public void setId(String id) {
    	this.id = id;
    }

	

	/**
	 * @return the officeName
	 */
	public String getOfficeName() {
		return officeName;
	}

	/**
	 * @param officeName the officeName to set
	 */
	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	/**
	 * @return the website
	 */
	public String getWebsite() {
		return website;
	}

	/**
	 * @param website the website to set
	 */
	public void setWebsite(String website) {
		this.website = website;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	@Override
	public int compareTo(Attendee that) {
		if (that == null) {
			return -1;
		}
		
		if (this.getOfficeName() == null && that.getOfficeName() == null) {
			return -1;
		}
		
		if (this.getOfficeName() != null && that.getOfficeName() == null) {
			return -1;
		}
		
		if (this.getOfficeName() == null && that.getOfficeName() != null) {
			return 1;
		}
		
		int lastNameCompare = this.getOfficeName().compareTo(getOfficeName());
		
		return lastNameCompare;
	}

}

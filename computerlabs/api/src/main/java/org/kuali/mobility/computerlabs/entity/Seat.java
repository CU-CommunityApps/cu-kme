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

package org.kuali.mobility.computerlabs.entity;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias(value = "seat")
public class Seat implements Serializable, Comparable<Seat> {
	
	private static final long serialVersionUID = -372599891985133670L;

	@XStreamAsAttribute
	private String lab;
	@XStreamAsAttribute
    private String floor;
	@XStreamAsAttribute
    private String building;
	@XStreamAsAttribute
    private String buildingCode;
	@XStreamAsAttribute
	private String campus;
	@XStreamAsAttribute
    private String availability;
	@XStreamAsAttribute
    private String windowsAvailability;
	@XStreamAsAttribute
    private String macAvailability;
	@XStreamAsAttribute
    private String softwareAvailability;
	@XStreamAsAttribute
    private String floorplan;
    
    public Seat() {}

	@Override
	public int compareTo(Seat that) {
		
		return 0;
	}

	public String getLab() {
		return lab;
	}

	public void setLab(String lab) {
		this.lab = lab;
	}

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public String getBuilding() {
		return building;
	}

	public void setBuilding(String building) {
		this.building = building;
	}

	public String getBuildingCode() {
		return buildingCode;
	}

	public void setBuildingCode(String buildingCode) {
		this.buildingCode = buildingCode;
	}

	public String getCampus() {
		return campus;
	}

	public void setCampus(String campus) {
		this.campus = campus;
	}

	public String getAvailability() {
		return availability;
	}

	public void setAvailability(String availability) {
		this.availability = availability;
	}

	public String getWindowsAvailability() {
		return windowsAvailability;
	}

	public void setWindowsAvailability(String windowsAvailability) {
		this.windowsAvailability = windowsAvailability;
	}

	public String getMacAvailability() {
		return macAvailability;
	}

	public void setMacAvailability(String macAvailability) {
		this.macAvailability = macAvailability;
	}

	public String getSoftwareAvailability() {
		return softwareAvailability;
	}

	public void setSoftwareAvailability(String softwareAvailability) {
		this.softwareAvailability = softwareAvailability;
	}

	public String getFloorplan() {
		return floorplan;
	}

	public void setFloorplan(String floorplan) {
		this.floorplan = floorplan;
	}
    
}

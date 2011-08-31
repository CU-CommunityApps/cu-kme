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
 
package org.kuali.mobility.maps.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import flexjson.JSONSerializer;

@Entity
@Table(name="KME_LOC_T")
public class Location implements Serializable {

	private static final long serialVersionUID = -2588912315204722978L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name="ID")
	private Long locationId;
    
    @Column(name="CD")
	private String code;
	
    @Column(name="SHRT_NM")
	private String shortName;
	
    @Column(name="LNG_NM")
	private String name;
	
    @Column(name="STRT")
	private String street;
	
    @Column(name="CTY")
	private String city;
	
    @Column(name="ST")
	private String state;
	
    @Column(name="ZIP")
	private String zip;
	
    @Column(name="LAT")
	private Double latitude;
	
    @Column(name="LONG")
	private Double longitude;
	
    @Column(name="ACTV")
	private boolean active;
	
    @Column(name="OVRRD")
	private boolean override;
    
    @Column(name="SHRT_CD")
    private String shortCode;
    
    @Column(name="BLDG_CD")
    private String buildingCode;
    
    @Column(name="NTE_INT")
    private String noteInternal;
    
    @Column(name="NTE_EXT")
    private String noteExternal;
	
    @Version
    @Column(name="VER_NBR")
    protected Long versionNumber;
    
    @ManyToMany(fetch=FetchType.LAZY, cascade={CascadeType.MERGE})
    @JoinTable(name="KME_LOC_GRP_T")
    private Set<MapsGroup> mapsGroups;

	public Location() {}
	
	public Location(String code, String shortname, String name, String street, String city, String state) {
		this.code = code;
		this.shortName = shortname;
		this.name = name;
		this.street = street;
		this.city = city;
		this.state = state;
	}
	
    public String toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortname) {
		this.shortName = shortname;
	}
	
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public Long getLocationId() {
		return locationId;
	}

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isOverride() {
		return override;
	}

	public void setOverride(boolean override) {
		this.override = override;
	}

	public Long getVersionNumber() {
		return versionNumber;
	}

	public void setVersionNumber(Long versionNumber) {
		this.versionNumber = versionNumber;
	}

	public String getShortCode() {
		return shortCode;
	}

	public void setShortCode(String shortCode) {
		this.shortCode = shortCode;
	}

	public Set<MapsGroup> getMapsGroups() {
		return mapsGroups;
	}

	public void setMapsGroups(Set<MapsGroup> mapsGroups) {
		this.mapsGroups = mapsGroups;
	}

	public String getBuildingCode() {
		return buildingCode;
	}

	public void setBuildingCode(String buildingCode) {
		this.buildingCode = buildingCode;
	}

	public String getNoteInternal() {
		return noteInternal;
	}

	public void setNoteInternal(String noteInternal) {
		this.noteInternal = noteInternal;
	}

	public String getNoteExternal() {
		return noteExternal;
	}

	public void setNoteExternal(String noteExternal) {
		this.noteExternal = noteExternal;
	}

	public Location copy() {
		Location location = new Location();
		location.setActive(this.active);
		location.setOverride(this.override);
		if (city != null) {
			location.setCity(new String(city));	
		}
		if (code != null) {
			location.setCode(new String(this.code));	
		}
		if (latitude != null) {
			location.setLatitude(new Double(this.latitude));	
		}
		if (longitude != null) {
			location.setLongitude(new Double(this.longitude));	
		}
		if (name != null) {
			location.setName(new String(this.name));	
		}
		if (shortCode != null) {
			location.setShortCode(new String(this.shortCode));	
		}
		if (shortName != null) {
			location.setShortName(new String(this.shortName));	
		}
		if (state != null) {
			location.setState(new String(this.state));	
		}
		if (street != null) {
			location.setStreet(new String(this.street));	
		}
		if (zip != null) {
			location.setZip(new String(this.zip));	
		}
		if (versionNumber != null) {
			location.setVersionNumber(new Long(this.versionNumber));	
		}
		if (buildingCode != null) {
			location.setBuildingCode(new String(this.buildingCode));	
		}
		if (noteInternal != null) {
			location.setNoteInternal(new String(this.noteInternal));	
		}
		if (noteExternal != null) {
			location.setNoteExternal(new String(this.noteExternal));	
		}
		return location;
	}
	
}

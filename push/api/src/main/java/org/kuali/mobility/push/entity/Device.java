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
@Table(name="KME_DVCS_T")
public class Device implements Serializable {

	private static final long serialVersionUID = -4157056311695870783L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name="ID")
    private Long id;	

	// iOS || Adroid || Windows (later)
    @Column(name="TYP")
    private String type;
	
    // Device Token for iOS
    // RegistrationID for Android
    @Column(name="REGID")
    private String regId;    
    
    // Device Name for iOS & Android.
    // 		on iOS this is the user set name.
    // 		on Android this is the model name for the device. 
    @Column(name="NM")
    private String deviceName;    
    
    @Column(name="USR")
    private String username;
    
    // deviceUUID for Android & iOS
    @Column(name="DVCID")
    private String deviceId;    

    @Column(name="PST_TS")
    private Timestamp postedTimestamp;    
    
    @Version
    @Column(name="VER_NBR")
    protected Long versionNumber;
	
    public Device(){}
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    
    public Timestamp getPostedTimestamp() {
        return postedTimestamp;
    }
    public void setPostedTimestamp(Timestamp postedTimestamp) {
        this.postedTimestamp = postedTimestamp;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    } 
    
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }    
    
    public String getRegId() {
        return regId;
    }
    public void setRegId(String regId) {
        this.regId = regId;
    }    

    public String getDeviceName() {
        return deviceName;
    }
    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }    
    
    public String getDeviceId() {
        return deviceId;
    }
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
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
    	String str = "";
    	str = str + newline + "ID:   " + this.getId();
    	str = str + newline + "DeviceID:   " + this.getDeviceId();
    	str = str + newline + "Username:   " + this.getUsername();
    	str = str + newline + "DeviceName: " + this.getDeviceName();
    	str = str + newline + "RegID:      " + this.getRegId();    
    	str = str + newline + "Type:       " + this.getType();    
    	str = str + newline + "Timestamp:  " + this.getPostedTimestamp();    
    	return str;
    }
}
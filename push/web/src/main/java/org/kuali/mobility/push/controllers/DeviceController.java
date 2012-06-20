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

package org.kuali.mobility.push.controllers;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.persistence.NoResultException;

import org.kuali.mobility.push.entity.Device;
import org.kuali.mobility.push.entity.Push;
import org.kuali.mobility.push.service.DeviceService;
import org.kuali.mobility.push.service.PushService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JSONException;

@Controller 
@RequestMapping("/device")
public class DeviceController {

	private static org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(DeviceController.class);		
	private String ME = this.getClass().getName();
	
    @Autowired
    private DeviceService deviceService;
    
    public void setDeviceService(DeviceService deviceService){
    	this.deviceService = deviceService;
    }
    
    
    @RequestMapping(method = RequestMethod.GET)
    public String index(Model uiModel) {
    	Push push = new Push();
   		uiModel.addAttribute("push", push);
    	return "push/index";
    }

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/detail/{deviceHash}", method = RequestMethod.GET)
	public String getUserDetails(Model uiModel, HttpServletRequest request, @PathVariable("deviceHash") String deviceHash) {
		if (deviceHash != null) {
			LOG.info("DeviceID is :" + deviceHash);
		}else{
			return "push";
		}
		Device device = deviceService.findDeviceByDeviceId(deviceHash);
		uiModel.addAttribute("device", device);
		uiModel.addAttribute("deviceHash", deviceHash);
		return "push/devicedetail";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/remove/{deviceHash}", method = RequestMethod.GET)
	public String removeDevice(Model uiModel, HttpServletRequest request, @PathVariable("deviceHash") String deviceHash) {
		Device deviceToDelete = deviceService.findDeviceByDeviceId(deviceHash);
		if(deviceToDelete != null){
			LOG.info("Will delete device with Id: " + deviceToDelete.getId());
			if(deviceService.removeDevice(deviceToDelete)){
				LOG.info("Did delete device.");
			}
		}
		
    	Long deviceCount = deviceService.countDevices();
    	uiModel.addAttribute("deviceCount", deviceCount);
		
    	List<Device> iosdevices = deviceService.findAlliOSDevices();
    	uiModel.addAttribute("iosdevices", iosdevices);    	

    	List<Device> androiddevices = deviceService.findAllAndroidDevices();
    	uiModel.addAttribute("androiddevices", androiddevices);    	

    	return "push/devices";		
	}	
	
    @RequestMapping(value = "/list", method = RequestMethod.GET)    
    public String devices(HttpServletRequest request, Model uiModel, @RequestParam(value="key", required=false) String key) { 
    	Long deviceCount = deviceService.countDevices();
    	uiModel.addAttribute("deviceCount", deviceCount);
    	
    	List<Device> iosdevices = deviceService.findAlliOSDevices();
    	List<Device> androiddevices = deviceService.findAllAndroidDevices();
  	
    	uiModel.addAttribute("iosdevices", iosdevices);    	
   		uiModel.addAttribute("androiddevices", androiddevices);    	

   		return "push/devices";
    }
        
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ResponseEntity<String> register(@RequestParam(value="data", required=true) String data) {
    	LOG.info("-----Register-----");
    	if(data == null){
    		return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
    	} 

    	JSONObject queryParams;
    	try{
    		queryParams = (JSONObject) JSONSerializer.toJSON(data);
    		LOG.info(queryParams.toString());
    	}catch(JSONException je){
    		LOG.error("JSONException in :" + data + " : " + je.getMessage());
    		return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
    	}
    		
		Device device = new Device();
		device.setDeviceName(queryParams.getString("name"));
		device.setDeviceId(queryParams.getString("deviceId"));
		device.setRegId(queryParams.getString("regId"));
		device.setType(queryParams.getString("type"));
		device.setUsername(queryParams.getString("username"));
		device.setPostedTimestamp(new Timestamp(System.currentTimeMillis()));
		LOG.info("\n-----New Device-----" + device.toString());

		Device temp = deviceService.findDeviceByDeviceId(device.getDeviceId());
			
		// If the device already exists, update. 
		if(temp != null){
			LOG.info("-----Device already exists." + temp.toString());
			temp.setDeviceName(queryParams.getString("name"));
			temp.setRegId(queryParams.getString("regId"));
			temp.setType(queryParams.getString("type"));
			temp.setUsername(queryParams.getString("username"));
			temp.setPostedTimestamp(new Timestamp(System.currentTimeMillis()));
			deviceService.registerDevice(temp);
		}else{
			LOG.info("-----Device Doesn't already exist." + device.toString());
			deviceService.registerDevice(device);
		}
		
		//TODO Delete this when finished. Only needed for testing. 
		List<Device> devices = deviceService.findAllDevices();
   		Iterator i = devices.iterator();
   		LOG.info("-----All Devices -----");
   		while(i.hasNext()){
   			LOG.info(i.next().toString());
   		}   		

   		devices = deviceService.findAlliOSDevices();
   		i = devices.iterator();
   		LOG.info("-----iOS Devices -----");
   		while(i.hasNext()){
   			LOG.info(i.next().toString());
   		}   		
   		
   		devices = deviceService.findAllAndroidDevices();
   		i = devices.iterator();
   		LOG.info("-----Android Devices -----");
   		while(i.hasNext()){
   			LOG.info(i.next().toString());
   		}   		
   		return new ResponseEntity<String>(HttpStatus.OK);
    }    
}

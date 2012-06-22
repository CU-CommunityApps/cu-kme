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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.kuali.mobility.push.entity.Device;
import org.kuali.mobility.push.entity.Push;
import org.kuali.mobility.push.service.DeviceService;
import org.kuali.mobility.push.service.PushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller 
@RequestMapping("/push")
public class PushController {

	private static org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(PushController.class);		
	private String ME = this.getClass().getName();
	
	
    private static final Map<String, String> stockPushMessages;
    
    static {
    	stockPushMessages = new LinkedHashMap<String, String>();
    	stockPushMessages.put("Pre-defined Push", "");
    	stockPushMessages.put("Test Message", "Test Message Detail");
    	stockPushMessages.put("Tornado Warning: Campus and Downtown", "A Tornado warning has been issued for the area near campus and downtown. Please report to a shelter until all-clear has been sounded.");
    	stockPushMessages.put("Crime: Local Car Theives", "Police have issued a warning for the downtown area regarding a series of car thefts. Take care when parking and locking your car.");
    }
	
	
    @Autowired
    private PushService pushService;

    @Autowired
    private DeviceService deviceService;
    

    public void setPushService(PushService pushService) {
        this.pushService = pushService;
    }

    public void setDeviceService(DeviceService deviceService){
    	this.deviceService = deviceService;
    }
    
    // Sending a push to a single selected device. 
    @RequestMapping(value = "/send", method = RequestMethod.GET)
    public String sendToChosenDevice(Model uiModel, HttpServletRequest request, @RequestParam(value="id", required=false) long deviceID) {
   		uiModel.addAttribute("stockPushMessages", stockPushMessages);
    	Push push = new Push();
    	Device device = deviceService.findDeviceById(deviceID);	
    	uiModel.addAttribute("push", push);
    	uiModel.addAttribute("device", device);
    	return "push/index";
    }	

    // Push form. Will send to all devices or single selected device. 
    @RequestMapping(method = RequestMethod.GET)
    public String index(Model uiModel) {
   		uiModel.addAttribute("stockPushMessages", stockPushMessages);
    	Push push = new Push();
    	
    	uiModel.addAttribute("push", push);
    	return "push/index";
    }
    
    // return a JSON object representing a push. Used in native apps to get more detail from the push object. 
    // Especially important in iOS where Pushes are limited to 256bytes in length. 
	@RequestMapping(value = "/get/{pushId}", method = RequestMethod.GET)
    @ResponseBody
	public String getUserDetails(Model uiModel, HttpServletRequest request, @PathVariable("pushId") Long pushId) {
    	Push push = new Push();
    	push = pushService.findPushById(pushId);
    	return new JSONSerializer().toJSON(push).toString();
    }    

	// Grab all Pushes and display them on a push history page. 
    @RequestMapping(value = "/history", method = RequestMethod.GET)    
    public String history(HttpServletRequest request, Model uiModel, @RequestParam(value="key", required=false) String key) { 

    	Long pushCount = pushService.countPushes();
    	uiModel.addAttribute("pushCount", pushCount);
    	
    	List<Push> pastPushes = pushService.findAllPush();
    	uiModel.addAttribute("pastPushes", pastPushes);    	
   		return "push/history";
    }  
    
    // Process the submited form, and submit the push. 
    @RequestMapping(method = RequestMethod.POST)
    public String submitPush(Model uiModel, @ModelAttribute("Push") Push push, BindingResult result, @RequestParam(value="id", required=false) Long id) {
    	push.setPostedTimestamp(new Timestamp(System.currentTimeMillis()));

    	List<Device> devices = new ArrayList<Device>();

    	// If there is no specific device selected, send push to all devices. 
    	if(id == null){
    		LOG.info("---- No specific ID");
    		devices = deviceService.findAllDevices();
    	}else{	// Otherwise send only to the selected device. 
    		LOG.info("---- Selected ID");
    		devices.add(deviceService.findDeviceById((long)id));		
    	}

    	LOG.info(devices.size() + " devices in list");
    	
    	if(isValidPush(push, result)){
    		pushService.savePush(push);
    		Long pushed = pushService.sendPush(push, devices);
    		LOG.info("Pushed Count: " + pushed);
    		push.setRecipients(pushed);
    		pushService.savePush(push);
    		LOG.info("Post Push:" + push.toString());
    		
        	Long pushCount = pushService.countPushes();
        	uiModel.addAttribute("pushCount", pushCount);
        	
        	List<Push> pastPushes = pushService.findAllPush();
        	uiModel.addAttribute("pastPushes", pastPushes);  
        	return "push/history";
    	}else{
    		return "push/index";
    	}
    }    
    
    private boolean isValidPush(Push p, BindingResult result){
    	Errors errors = ((Errors) result);
    	boolean isValid = true;
    	if(p.getMessage() == null || "".equals(p.getMessage().trim())){
    		errors.rejectValue("message", "Please type a Message for the Push notification.");
    		isValid = false;
    	}
    	if(p.getTitle() == null || "".equals(p.getTitle().trim())){
    		errors.rejectValue("title", "Please type a Title for the Push notification.");
    		isValid = false;
    	}
    	
    	
    	
    	return isValid;
    }
    
    @RequestMapping(value = "/service", method = RequestMethod.GET)
    public ResponseEntity<String> service(@RequestParam(value="data", required=true) String data) {
       	LOG.info("Service JSON: " + data);
    	JSONObject queryParams;
    	List<Device> devices = new ArrayList<Device>();
    	String title;
    	String message;
    	String url;
    	String recipient;
    	String sender;
    	JSONArray recipients;
    	
    	try{
       		queryParams = (JSONObject) JSONSerializer.toJSON(data);       		
       		title = queryParams.getString("title");
       		message = queryParams.getString("message");
       		url = queryParams.getString("url");
       		sender = queryParams.getString("sender");
       		
       		LOG.info(title);
       		LOG.info(message);
       		LOG.info(url);
       		LOG.info(sender);
       		
       		if(queryParams.get("recipients").getClass().getName() == "net.sf.json.JSONArray"){
       			recipients = queryParams.getJSONArray("recipients");
           		LOG.info(recipients.toString());
           		Iterator i = recipients.iterator();
           		while(i.hasNext()){
           			String username = i.next().toString();
           			LOG.info(username);
           			devices.addAll(deviceService.findDevicesByUsername(username));
           		}       		     		
       		}else{
       			recipient = queryParams.getString("recipients");
           		LOG.info(recipient);
       		}
    	}catch(JSONException je){
    		LOG.error("JSONException in :" + data + " : " + je.getMessage());
    		return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
       	}    	

    	Push push = new Push();
    	push.setEmergency(false);
    	push.setMessage(message);
    	push.setTitle(title);
    	push.setUrl(url);
    	push.setSender(sender);
    	push.setPostedTimestamp(new Timestamp(System.currentTimeMillis()));
    	pushService.savePush(push);    	
    	Long pushed = pushService.sendPush(push, devices);
		push.setRecipients(pushed);
		pushService.savePush(push);
    	LOG.info(push.toString());
    	
    	
   		return new ResponseEntity<String>(HttpStatus.OK);
    }
}

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
 
package org.kuali.mobility.push.service;

import java.util.List;

import org.kuali.mobility.push.dao.DeviceDao;
import org.kuali.mobility.push.entity.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeviceServiceImpl implements DeviceService {

	private static org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(DeviceServiceImpl.class);		
	private String ME = this.getClass().getName();
	
	@Autowired
	private DeviceDao deviceDao;
	
	@Override
	@Transactional
	public void registerDevice(Device device){
		LOG.info(ME + ":registerDevice");
		deviceDao.registerDevice(device);
	}

	@Transactional
	public List<Device> findAllDevices() {
		return deviceDao.findAllDevices();
	}	

	@Transactional
	public List<Device> findAlliOSDevices() {
		return deviceDao.findAlliOSDevices();
	}		
	
	@Transactional
	public List<Device> findAllAndroidDevices() {
		return deviceDao.findAllAndroidDevices();
	}		
	
	@Transactional
	public List<Device> findDevicesByUsername(String username) {
		return deviceDao.findDevicesByUsername(username);
	}		

	@Transactional
	public List<Device> findDevicesById(String id) {
		return deviceDao.findDevicesById(id);
	}	
	
	@Transactional
	public List<Device> findDevicesByRegId(String regid) {
		return deviceDao.findDevicesByRegId(regid);
	}	
	
	@Transactional
	public List<Device> findDevicesByDeviceId(String deviceid) {
		return deviceDao.findDevicesByDeviceId(deviceid);
	}	

	@Transactional
	public Device findDeviceByDeviceId(String deviceid) {
		return deviceDao.findDeviceByDeviceId(deviceid);
	}

	@Transactional
	public Device findDeviceById(Long id) {
		return deviceDao.findDeviceById(id);
	}
	
	@Transactional
	public boolean removeDevice(Device device){
		return deviceDao.removeDevice(device);
	}
	
	@Transactional
	public Long countDevices(){
		return deviceDao.countDevices();
	}
	
    @Autowired
    private DeviceDao dao;
    public void setDeviceDao(DeviceDao dao) {
        this.dao = dao;
    }
    public DeviceDao getDeviceDao() {
        return dao;
    }

}

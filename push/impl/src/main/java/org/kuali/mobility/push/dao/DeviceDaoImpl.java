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
 
package org.kuali.mobility.push.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.kuali.mobility.push.entity.Device;
import org.springframework.stereotype.Repository;

@Repository
public class DeviceDaoImpl implements DeviceDao {
	
	private static org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(DeviceDaoImpl.class);		
	private String ME = this.getClass().getName();	

	@PersistenceContext
    private EntityManager entityManager;
	
    public DeviceDaoImpl(){}

    @SuppressWarnings("unchecked")
	public List<Device> findAllDevices(){
        Query query = entityManager.createQuery("select d from Device d");
        return query.getResultList();
	}
    
    @SuppressWarnings("unchecked")
    public List<Device> findAlliOSDevices(){
        Query query = entityManager.createQuery("select d from Device d where d.type = 'iOS'");
        return query.getResultList();		
	}
    
    @SuppressWarnings("unchecked")    
	public List<Device> findAllAndroidDevices(){
        Query query = entityManager.createQuery("select d from Device d where d.type = 'Android'");
        return query.getResultList();		
	}

    @SuppressWarnings("unchecked")    
	public List<Device> findDevicesByUsername(String username){
        Query query = entityManager.createQuery("select d from Device d where d.username = '" + username + "'");
        return query.getResultList();		
	}    

    @SuppressWarnings("unchecked")    
	public List<Device> findDevicesById(String id){
        Query query = entityManager.createQuery("select d from Device d where d.id = '" + id + "'");
        return query.getResultList();		
	}  

    @SuppressWarnings("unchecked")    
	public List<Device> findDevicesByRegId(String regid){
        Query query = entityManager.createQuery("select d from Device d where d.regId = '" + regid + "'");
        return query.getResultList();		
	}  
    
    @SuppressWarnings("unchecked")    
	public List<Device> findDevicesByDeviceId(String deviceid){
        Query query = entityManager.createQuery("select d from Device d where d.deviceId = '" + deviceid + "'");
        return query.getResultList();		
	}  

    @SuppressWarnings("unchecked")    
	public Device findDeviceByDeviceId(String deviceid){
        Query query = entityManager.createQuery("select d from Device d where d.deviceId = '" + deviceid + "'");
        Device result; 
        try{
        	result = (Device) query.getSingleResult();
        }catch(Exception e){
        	LOG.info("Exception: " + e.getMessage());
        	result = null;
        }	
        return result;
    }  

    @SuppressWarnings("unchecked")    
	public Device findDeviceById(Long id){
        Query query = entityManager.createQuery("select d from Device d where d.id = " + id);
        Device result; 
        try{
        	result = (Device) query.getSingleResult();
        }catch(Exception e){
        	LOG.info("Exception: " + e.getMessage());
        	result = null;
        }	
        return result;
    }      
    
    @SuppressWarnings("unchecked")
    public boolean removeDevice(Device device){
    	boolean result = true;
    	if(device == null){
    		return false;
    	}
    	if(device.getId() != null){
    		try{
    			Device d = entityManager.find(Device.class, device.getId());
    			entityManager.remove(d);
    		}catch(Exception e){
    			LOG.info("Exception Caught: " + e.getMessage());
    			result = false;
    		}
    	}
    	return result;
    }
    
    public void registerDevice(Device device){
		LOG.info(ME + ":willRegisterDevice");
		LOG.info(device);

		
		
		if(device == null){
    		return;
    	}
    	if(device.getId() == null){
    		LOG.info("persist");
    		entityManager.persist(device);
    	}else{
    		LOG.info("merge");
    		entityManager.merge(device);
    	}
		LOG.info(ME + ":didRegisterDevice");
    }
    
    @SuppressWarnings("unchecked")
    public Long countDevices(){
        Query query = entityManager.createQuery("select count(d) from Device d");
        return (Long)query.getSingleResult();
    }
    
    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

}

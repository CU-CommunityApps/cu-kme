/**
 * Copyright 2011-2012 The Kuali Foundation Licensed under the
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

package org.kuali.mobility.emergencyinfo.service;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.kuali.mobility.emergencyinfo.dao.EmergencyInfoDao;
import org.kuali.mobility.emergencyinfo.entity.EmergencyInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

import org.kuali.mobility.util.mapper.DataMapper;

@Service(value="EmergencyInfoService")
public class EmergencyInfoServiceImpl implements EmergencyInfoService {
	
	private String emergencyinfoSourceUrl;
	
  
    public String getEmergencyinfoSourceUrl() {
		return emergencyinfoSourceUrl;
	}

	public void setEmergencyinfoSourceUrl(String emergencyinfoSourceUrl) {
		this.emergencyinfoSourceUrl = emergencyinfoSourceUrl;
	}

	@Autowired
    private EmergencyInfoDao emergencyInfoDao;
    
    

	private static org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(EmergencyInfoServiceImpl.class);

    @Autowired
	private DataMapper dataMapper;
    
    @Transactional
    public void deleteEmergencyInfoById(Long id) {
        emergencyInfoDao.deleteEmergencyInfoById(id);
    }

    @Transactional
    public List<EmergencyInfo> findAllEmergencyInfo() {
        return emergencyInfoDao.findAllEmergencyInfo();
    }

    @Transactional
    public EmergencyInfo findEmergencyInfoById(Long id) {
        return emergencyInfoDao.findEmergencyInfoById(id);
    }

    @Transactional
    public Long saveEmergencyInfo(EmergencyInfo emergencyInfo) {
        return emergencyInfoDao.saveEmergencyInfo(emergencyInfo);
    }
    
    @Transactional
    public void reorder(Long id, boolean up) {
        emergencyInfoDao.reorder(id, up); 
    }

    @Transactional
    public EmergencyInfoDao getEmergencyInfoDao() {
        return emergencyInfoDao;
    }

    @Transactional
    public List<EmergencyInfo> findAllEmergencyInfoByCampus(String campus) {
        //return emergencyInfoDao.findAllEmergencyInfoByCampus(campus);
    	
    	List<EmergencyInfo> eis = new ArrayList<EmergencyInfo>();
    	
    	String dao = emergencyInfoDao.getClass().getName();
    	
    	if(dao.equals("org.kuali.mobility.emergencyinfo.dao.EmergencyInfoWSDaoImpl")) {
    		
    		try {
				URL url = new URL(emergencyinfoSourceUrl);
				
				eis.addAll(dataMapper.mapData(eis,url,"emergencyinfoMapping.xml"));
				System.out.println("cudebug size: " + eis.size());
			} catch (Exception e) {
				LOG.error("errors", e);
			}
    		
    	} else {
    		
    		eis = emergencyInfoDao.findAllEmergencyInfoByCampus(campus);
    		
    	}
    		
    	return(eis);
    	
    }

    public EmergencyInfo fromJsonToEntity(String json) {
        return new JSONDeserializer<EmergencyInfo>().use(null, EmergencyInfo.class).deserialize(json);
    }

    public String toJson(Collection<EmergencyInfo> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }

    public Collection<EmergencyInfo> fromJsonToCollection(String json) {
        return new JSONDeserializer<List<EmergencyInfo>>().use(null, ArrayList.class).use("values", EmergencyInfo.class).deserialize(json);
    }

    
    
}

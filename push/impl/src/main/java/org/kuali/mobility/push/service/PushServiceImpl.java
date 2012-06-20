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

import javax.net.ssl.SSLSocket;

import org.kuali.mobility.push.dao.PushDao;
import org.kuali.mobility.push.entity.Device;
import org.kuali.mobility.push.entity.Push;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PushServiceImpl implements PushService {

	private static org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(PushServiceImpl.class);		
	private String ME = this.getClass().getName();
	
	@Autowired
	private PushDao pushDao;
	
	@Override
	@Transactional
	public void savePush(Push push){
		LOG.info(ME + ":savePush");
		pushDao.savePush(push);
	}

	@Transactional
	public Push findPushById(Long id){
		return pushDao.findPushById(id);
	}
	
	@Transactional
	public List<Push> findAllPush() {
		return pushDao.findAllPush();
	}	

	@Transactional
	public Long countPushes() {
		return pushDao.countPushes();
	}	
	
	@Transactional
	public Long sendPush(Push push, Device device){
		return pushDao.sendPush(push, device);
	}
	
	@Transactional
	public Long sendPush(Push push, List<Device> devices){		
		return pushDao.sendPush(push, devices);
	}
	
    @Autowired
    private PushDao dao;
    public void setPushDao(PushDao dao) {
        this.dao = dao;
    }
    public PushDao getPushDao() {
        return dao;
    }

}

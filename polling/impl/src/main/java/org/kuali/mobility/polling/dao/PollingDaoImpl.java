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
 
package org.kuali.mobility.polling.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.kuali.mobility.polling.entity.Poll;
import org.springframework.stereotype.Repository;

@Repository
public class PollingDaoImpl implements PollingDao {

    @PersistenceContext
    private EntityManager entityManager;

	@Override
	public Poll lookup(Long id) {
		Query query = entityManager.createQuery("select p from Poll p where p.id = :id");
        query.setParameter("id", id);
        try {
        	return (Poll) query.getSingleResult();
        } catch (Exception e) {
        	return null;
        }
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Poll> findAllPolls() {
    	Query query = entityManager.createQuery("select p from Poll p");
        try { 
        	return query.getResultList();
        } catch (Exception e) {        	
        	return new ArrayList<Poll>();
        }
	}

	@Override
	public Poll save(Poll poll) {
		if (poll == null) {
            return null;
        }
        try {
	        if (poll.getId() == null) {
	            entityManager.persist(poll);
	        } else {
	            entityManager.merge(poll);
	        }
        } catch (OptimisticLockException oe) {
            return null;
        }
        return poll;
	}

	@Override
	public Poll delete(Poll poll) {
		Query query = entityManager.createQuery("update Poll p set p.status = 1 where p.id = :id");
        query.setParameter("id", poll.getId());
        query.executeUpdate();
        return poll;
	}

}

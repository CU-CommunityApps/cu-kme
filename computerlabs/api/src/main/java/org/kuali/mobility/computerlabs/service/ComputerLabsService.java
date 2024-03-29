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

package org.kuali.mobility.computerlabs.service;

import java.util.Collection;

import org.kuali.mobility.computerlabs.entity.Lab;
import org.kuali.mobility.computerlabs.entity.Location;

/**
 * Provides service methods for retrieving <code>Location</code> and <code>Lab</code> instances.
 * 
 * @author Kuali Mobility Team 
 */
public interface ComputerLabsService {

	/**
	 * Finds all labs for the given campus.
	 * @param campus - campus to find labs on.
	 * @return <code>Collection&lt;Location&gt;</code> filtered by the campus or an empty 
	 * 			<code>Collection</code>. 
	 */
	public Collection<Location> findAllLabsByCampus(String campus);
	public Lab getLab(String campus, String buildingCode);
	public String getViewSeatJson( String buildingCode,String campus);	
}
